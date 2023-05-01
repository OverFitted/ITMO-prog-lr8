package exmp;

import exmp.commands.CommandData;
import exmp.commands.CommandResult;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private final int port;
    private final exmp.App app;
    private static final Logger logger = LogManager.getLogger(Server.class);

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

                CommandResult result = app.executeCommand(commandName, commandInput, commandData.getUserId());
                exmp.gateway.GatewayMessage gatewayResult = new exmp.gateway.GatewayMessage(commandData.getClientAddress(), result);
                logger.debug("Получен результат: {}", result.toString());

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
