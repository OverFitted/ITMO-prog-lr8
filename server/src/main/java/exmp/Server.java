package exmp;

import exmp.commands.CommandData;
import exmp.commands.CommandResult;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


public class Server {
    private final int port;
    private final exmp.App app;
    private static final Logger logger = LogManager.getLogger(Server.class);

    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public Server(int port, exmp.App app) {
        this.port = port;
        this.app = app;
    }

    private void notifyGateway(InetSocketAddress gatewayAddress) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            InetAddress localAddress = InetAddress.getLocalHost();
            InetSocketAddress serverAddress = new InetSocketAddress(localAddress, this.port);
            exmp.gateway.GatewayNotification notification = new exmp.gateway.GatewayNotification(serverAddress);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(notification);
            byte[] data = byteArrayOutputStream.toByteArray();

            channel.send(ByteBuffer.wrap(data), gatewayAddress);
        } catch (IOException e) {
            logger.error("Ошибка при уведомлении GatewayLBService: " + e);
        }
    }



    private exmp.database.UserCreds authUser(String username, String password) {
        long userId = isValidUser(username, password);
        if (userId != 0) {
            String jwtToken = Jwts.builder()
                    .setSubject(username)
                    .signWith(jwtSecretKey)
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
            logger.info("Сервер запущен и ожидает подключений...");

            notifyGateway(new InetSocketAddress("localhost", 38761));
            ByteBuffer buffer = ByteBuffer.allocate(65536);

            while (app.getState()) {
                buffer.clear();
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

                if (clientAddress == null) {
                    continue;
                }

                logger.info("Получено подключение от {}", clientAddress);

                buffer.flip();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                CommandData commandData = (CommandData) objectInputStream.readObject();
                String commandName = commandData.getCommandName();
                String commandInput = commandData.getArguments();

                CommandResult result;
                if (commandName.equalsIgnoreCase("login")) {
                    logger.debug("Получен запрос: {}", commandName);
                    String[] credentials = commandInput.split(" ");
                    if (credentials.length == 2) {
                        exmp.database.UserCreds userCreds = authUser(credentials[0], credentials[1]);
                        String token = userCreds.getToken();
                        if (token != null) {
                            result = new CommandResult(0, "Успешная авторизация", null);
                            result.setToken(token);
                            result.setUserId(userCreds.getUserId());
                        } else {
                            result = new CommandResult(1, null, "Неверные учетные данные");
                        }
                    } else {
                        result = new CommandResult(1, null, "Некорректный формат команды");
                    }
                } else if (commandName.equalsIgnoreCase("register")) {
                    logger.debug("Получен запрос: {}", commandName);
                    String[] credentials = commandInput.split(" ");
                    if (credentials.length == 2) {
                        if (registerUser(credentials[0], credentials[1])) {
                            result = new CommandResult(0, "Успешная регистрация", null);
                        } else {
                            result = new CommandResult(1, null, "Не удалось зарегистрировать пользователя");
                        }
                    } else {
                        result = new CommandResult(1, null, "Некорректный формат команды");
                    }
                } else {
                    if (commandData.getToken() == null || commandData.getToken().isEmpty()) {
                        result = new CommandResult(1, null, "Токен доступа не предоставлен. Сначала введите login/register");
                    } else {
                        try {
                            logger.debug("Получен запрос: {} {}", commandName, commandInput);
                            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(commandData.getToken());
                            result = app.executeCommand(commandName, commandInput, commandData.getUserId());
                        } catch (io.jsonwebtoken.ExpiredJwtException e) {
                            result = new CommandResult(1, null, "Токен доступа истек");
                        } catch (io.jsonwebtoken.JwtException e) {
                            result = new CommandResult(1, null, "Недействительный токен доступа");
                        }
                    }
                }

                logger.debug("Получен результат: {}", result.toString());
                exmp.gateway.GatewayMessage gatewayResult = new exmp.gateway.GatewayMessage(commandData.getClientAddress(), result);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(gatewayResult);
                byte[] data = byteArrayOutputStream.toByteArray();

                channel.send(ByteBuffer.wrap(data), clientAddress);
                logger.info("Отправлен ответ клиенту {}", clientAddress);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Ошибка при запуске сервера: ", e);
        }
    }
}
