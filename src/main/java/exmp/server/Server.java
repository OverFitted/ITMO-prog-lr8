package exmp.server;

import exmp.App;
import exmp.commands.CommandData;
import exmp.commands.CommandResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {
    private final int port;
    private final App app;

    public Server(int port, App app) {
        this.port = port;
        this.app = app;
    }

    public void start() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            System.out.println("Сервер запущен и ожидает подключений...");

            ByteBuffer buffer = ByteBuffer.allocate(65536);

            while (app.getStatus()) {
                buffer.clear();
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

                if (clientAddress == null) {
                    continue;
                }

                buffer.flip();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                CommandData commandData = (CommandData) objectInputStream.readObject();
                String commandName = commandData.getCommandName();
                String commandInput = commandData.getArguments();
                CommandResult result = app.executeCommand(commandName, commandInput);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(result);
                byte[] data = byteArrayOutputStream.toByteArray();

                channel.send(ByteBuffer.wrap(data), clientAddress);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
