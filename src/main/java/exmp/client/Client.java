package exmp.client;

import exmp.commands.CommandData;
import exmp.commands.CommandResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(host, port));
            System.out.println("Подключено к серверу: " + host + ":" + port);

            Scanner scanner = new Scanner(System.in);
            ByteBuffer buffer = ByteBuffer.allocate(65536);

            while (true) {
                System.out.print("$ ");
                String line = scanner.nextLine();
                String[] inputParts = line.trim().split("\\s+", 2);
                String commandName = inputParts[0];
                String commandInput = inputParts.length > 1 ? inputParts[1] : "";

                if (commandName.equalsIgnoreCase("exit")) {
                    break;
                }

                CommandData outCommand = new CommandData(commandName, commandInput);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(outCommand);
                byte[] data = byteArrayOutputStream.toByteArray();

                DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress(host, port));
                DatagramSocket datagramSocket = new DatagramSocket();
                datagramSocket.send(sendPacket);

                buffer.clear();
                DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.capacity());
                datagramSocket.receive(receivePacket);
                buffer.position(receivePacket.getLength());

                buffer.flip();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                CommandResult result = (CommandResult) objectInputStream.readObject();

                if (result.getStatusCode() == 0)
                    System.out.println(result.getOutput());
                else
                    System.err.println("Ошибка выполнения команды: " + result.getErrorMessage());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при подключении к серверу: " + e.getMessage());
        }
    }
}
