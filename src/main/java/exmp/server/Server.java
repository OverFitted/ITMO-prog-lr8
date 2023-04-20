package exmp.server;

import exmp.App;
import exmp.commands.CommandData;
import exmp.commands.CommandResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private final int port;
    private final App app;
    private static final Logger logger = LogManager.getLogger(Server.class);

    public Server(int port, App app) {
        this.port = port;
        this.app = app;
    }

    public void start() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            logger.info("Сервер запущен и ожидает подключений...");

            ByteBuffer buffer = ByteBuffer.allocate(65536);

            while (app.getStatus()) {
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
                logger.info("Получен запрос: {} {}", commandName, commandInput);

                CommandResult result = app.executeCommand(commandName, commandInput);
                logger.info("Получен результат: {}", result.toString());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(result);
                byte[] data = byteArrayOutputStream.toByteArray();

                channel.send(ByteBuffer.wrap(data), clientAddress);
                logger.info("Отправлен ответ клиенту {}", clientAddress);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Ошибка при запуске сервера: ", e);
        }
    }
}
