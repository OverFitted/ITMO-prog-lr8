package exmp;

import exmp.commands.CommandData;
import exmp.commands.CommandResult;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;
    private static String jwtToken = null;
    private static Long userId = null;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            connectToServer(channel);
            interactWithServer();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    private void connectToServer(DatagramChannel channel) throws IOException {
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(host, port));
        System.out.println("Connected to server: " + host + ":" + port);
    }

    public CommandResult<List<?>> sendCommand(String[] inputParts) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(65536);
            String commandName = inputParts[0];
            String commandInput = inputParts.length > 1 ? inputParts[1] : "";

            CommandData outCommand = new CommandData(commandName, commandInput);
            adjustCommandData(commandName, outCommand);
            byte[] data = serializeCommand(outCommand);

            DatagramPacket receivePacket = sendAndReceivePacket(data, buffer);
            buffer.position(receivePacket.getLength());
            CommandResult<List<?>> result = deserializeResult(buffer);
            processAuth(commandName, result);
            return result;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
        return null;
    }

    private void interactWithServer() {
        try {
            Scanner scanner = new Scanner(System.in);
            ByteBuffer buffer = ByteBuffer.allocate(65536);

            while (true) {
                String[] inputParts = getInput(scanner);
                String commandName = inputParts[0];
                String commandInput = inputParts.length > 1 ? inputParts[1] : "";

                if (commandName.equalsIgnoreCase("exit")) {
                    break;
                }

                CommandData outCommand = new CommandData(commandName, commandInput);
                adjustCommandData(commandName, outCommand);
                byte[] data = serializeCommand(outCommand);

                DatagramPacket receivePacket = sendAndReceivePacket(data, buffer);
                buffer.position(receivePacket.getLength());

                CommandResult<? extends List<?>> result = deserializeResult(buffer);
                processResult(commandName, result);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }

    public String[] getInputParts(String line){
        return line.trim().split("\\s+", 2);
    }

    private String[] getInput(Scanner scanner) {
        System.out.print("$ ");
        String line = scanner.nextLine();
        return getInputParts(line);
    }

    private void adjustCommandData(String commandName, CommandData outCommand) {
        if (commandName.equalsIgnoreCase("login") || commandName.equalsIgnoreCase("register")) {
            outCommand.setToken(null);
        } else {
            outCommand.setToken(jwtToken);
            outCommand.setUserId(userId);
        }
    }

    private byte[] serializeCommand(CommandData outCommand) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(outCommand);
        return byteArrayOutputStream.toByteArray();
    }

    private DatagramPacket sendAndReceivePacket(byte[] data, ByteBuffer buffer) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress(host, port));
        DatagramPacket receivePacket;

        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.send(sendPacket);

            buffer.clear();
            receivePacket = new DatagramPacket(buffer.array(), buffer.capacity());
            datagramSocket.receive(receivePacket);
        }

        return receivePacket;
    }

    private CommandResult<List<?>> deserializeResult(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        buffer.flip();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (CommandResult<List<?>>) objectInputStream.readObject();
    }

    private void processAuth(String commandName, CommandResult<? extends List<?>> result) {
        if (commandName.equalsIgnoreCase("login")) {
            jwtToken = result.getToken();
            userId = result.getUserId();
        }
    }

    private void processResult(String commandName, CommandResult<? extends List<?>> result) {
        if (result.getStatusCode() == 0) {
            System.out.println(result.getOutput());

            processAuth(commandName, result);
        } else {
            System.err.println("Command execution error: " + result.getErrorMessage());
        }
    }

    public Long getUserId() {
        return userId;
    }
}
