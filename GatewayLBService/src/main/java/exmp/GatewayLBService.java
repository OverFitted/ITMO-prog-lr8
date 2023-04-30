package exmp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GatewayLBService {
    private final int port;
    private final List<InetSocketAddress> serverAddresses;
    private final AtomicInteger currentServer;
    private static final Logger logger = LogManager.getLogger(GatewayLBService.class);

    public GatewayLBService(int port) {
        this.port = port;
        this.serverAddresses = new ArrayList<>();
        this.currentServer = new AtomicInteger(0);
    }

    public void addServer(InetSocketAddress serverAddress) {
        serverAddresses.add(serverAddress);
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
                        serverAddresses.add(serverAddress);
                        logger.info("Сервер зарегистрирован: " + serverAddress);
                    } else if (!serverAddresses.isEmpty()) {
                        if (receivedObject instanceof exmp.commands.CommandData commandData) {
                            InetSocketAddress serverAddress = getNextServer();
                            logger.info("Перенаправление запроса клиента " + clientAddress + " на сервер " + serverAddress);

                            commandData.setClientAddress(clientAddress);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            objectOutputStream.writeObject(commandData);
                            byte[] data = byteArrayOutputStream.toByteArray();

                            channel.send(ByteBuffer.wrap(data), serverAddress);
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

    private InetSocketAddress getNextServer() {
        int index = currentServer.getAndIncrement() % serverAddresses.size();
        if (currentServer.get() >= serverAddresses.size()) {
            currentServer.set(0);
        }
        return serverAddresses.get(index);
    }
}
