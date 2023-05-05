package exmp;

import exmp.commands.CommandResult;
import io.jsonwebtoken.JwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class GatewayLBService {
    private final int port;
    private final List<InetSocketAddress> serverAddresses;
    private final AtomicInteger currentServer;
    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final Logger logger = LogManager.getLogger(GatewayLBService.class);

    public GatewayLBService(int port) {
        this.port = port;
        this.serverAddresses = new ArrayList<>();
        this.currentServer = new AtomicInteger(0);
    }

    public void addServer(InetSocketAddress serverAddress) {
        serverAddresses.add(serverAddress);
    }

    private exmp.commands.CommandResult handleAuth(exmp.commands.CommandData commandData) {
        String commandName = commandData.getCommandName();
        String[] credentials = commandData.getArguments().split("\s");

        exmp.commands.CommandResult result = null;
        if (commandName.equalsIgnoreCase("login")) {
            logger.debug("Получен запрос: {}", commandName);
            if (credentials.length == 2) {
                exmp.database.UserCreds userCreds = authUser(credentials[0], credentials[1]);
                String token = userCreds.getToken();
                if (token != null) {
                    result = new exmp.commands.CommandResult(0, "Успешная авторизация", null);
                    result.setToken(token);
                    result.setUserId(userCreds.getUserId());
                } else {
                    result = new exmp.commands.CommandResult(1, null, "Неверные учетные данные");
                }
            } else {
                result = new exmp.commands.CommandResult(1, null, "Некорректный формат команды");
            }
        } else if (commandName.equalsIgnoreCase("register")) {
            logger.debug("Получен запрос: {}", commandName);
            if (credentials.length == 2) {
                if (registerUser(credentials[0], credentials[1])) {
                    result = new exmp.commands.CommandResult(0, "Успешная регистрация", null);
                } else {
                    result = new exmp.commands.CommandResult(1, null, "Не удалось зарегистрировать пользователя");
                }
            } else {
                result = new exmp.commands.CommandResult(1, null, "Некорректный формат команды");
            }
        }

        return result;
    }

    private exmp.database.UserCreds authUser(String username, String password) {
        long userId = isValidUser(username, password);
        if (userId != 0) {
            Date now = new Date();
            Date fiveMinutesLater = new Date(now.getTime() + (5 * 60 * 1000));
            String jwtToken = Jwts.builder()
                    .setSubject(username)
                    .signWith(jwtSecretKey)
                    .setExpiration(fiveMinutesLater)
                    .compact();
            return new exmp.database.UserCreds(jwtToken, userId);
        } else {
            return new exmp.database.UserCreds(null, userId);
        }
    }

    private long isValidUser(String username, String password) {
        try (Connection connection = exmp.database.DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке пользователя и пароля: " + e);
            return 0;
        }
    }

    private boolean registerUser(String username, String password) {
        try (Connection connection = exmp.database.DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при регистрации пользователя: " + e);
            return false;
        }
    }

    public void start() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            logger.info("GatewayLBService запущен и ожидает подключений...");

            ByteBuffer buffer = ByteBuffer.allocate(65536);

            while (true) {
                buffer.clear();
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

                if (clientAddress == null) {
                    continue;
                }

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Object receivedObject = objectInputStream.readObject();

                    if (receivedObject instanceof exmp.gateway.GatewayNotification notification) {
                        InetSocketAddress serverAddress = notification.getServerAddress();
                        addServer(serverAddress);
                        logger.info("Сервер зарегистрирован: " + serverAddress);
                    } else if (!serverAddresses.isEmpty()) {
                        if (receivedObject instanceof exmp.commands.CommandData commandData) {
                            if (Objects.equals(commandData.getCommandName(), "login") || Objects.equals(commandData.getCommandName(), "register")) {
                                logger.info("Авторизация клиента " + clientAddress);

                                CommandResult auth_res = handleAuth(commandData);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                                objectOutputStream.writeObject(auth_res);
                                byte[] data = byteArrayOutputStream.toByteArray();

                                channel.send(ByteBuffer.wrap(data), clientAddress);
                            } else {
                                if (validateToken(commandData)) {
                                    InetSocketAddress serverAddress = getNextServer();
                                    logger.info("Перенаправление запроса клиента " + clientAddress + " на сервер " + serverAddress);

                                    commandData.setClientAddress(clientAddress);
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                                    objectOutputStream.writeObject(commandData);
                                    byte[] data = byteArrayOutputStream.toByteArray();

                                    channel.send(ByteBuffer.wrap(data), serverAddress);
                                } else {
                                    logger.info("Получен запрос от неавторизованного клиента " + clientAddress);

                                    CommandResult result = new CommandResult(1, null, "Ошибка авторизации. Используйте login/register");

                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                                    objectOutputStream.writeObject(result);
                                    byte[] data = byteArrayOutputStream.toByteArray();

                                    channel.send(ByteBuffer.wrap(data), clientAddress);
                                }
                            }
                        } else if (receivedObject instanceof exmp.gateway.GatewayMessage gatewayData
                                && gatewayData.getPayload() instanceof exmp.commands.CommandResult result) {
                            InetSocketAddress serverAddress = clientAddress;
                            clientAddress = gatewayData.getClientAddress();
                            logger.info("Перенаправление запроса сервера " + serverAddress + " на клиент " + clientAddress);

                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            objectOutputStream.writeObject(result);
                            byte[] data = byteArrayOutputStream.toByteArray();

                            channel.send(ByteBuffer.wrap(data), clientAddress);
                        } else {
                            logger.error("Неизвестный тип объекта: " + receivedObject.getClass().getName());
                        }
                    } else {
                        logger.error("Нет доступных серверов");
                    }
                } catch (ClassNotFoundException e) {
                    logger.error("Ошибка при чтении объекта: " + e);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка при запуске GatewayLBService: " + e);
        }
    }

    private boolean validateToken(exmp.commands.CommandData commandData) {
        if (commandData.getToken() == null || commandData.getToken().isEmpty()) {
            return false;
        } else {
            try {
                Date now = new Date();
                Date fiveMinutesLater = new Date(now.getTime() + (5 * 60 * 1000));
                Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(commandData.getToken()).getBody().setExpiration(fiveMinutesLater);
                return true;
            } catch (JwtException e) {
                return false;
            }
        }
    }

    private InetSocketAddress getNextServer() {
        int index = currentServer.getAndIncrement() % serverAddresses.size();
        if (currentServer.get() >= serverAddresses.size()) {
            currentServer.set(0);
        }
        return serverAddresses.get(index);
    }
}
