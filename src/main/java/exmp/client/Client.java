package exmp.client;

import exmp.commands.CommandResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Подключено к серверу: " + socket.getRemoteSocketAddress());

            while (true) {
                System.out.print("Введите команду: ");
                String commandName = scanner.next();
                String commandInput = scanner.nextLine().trim();

                if (commandName.equalsIgnoreCase("exit")) {
                    break;
                }

                output.writeUTF(commandName);
                output.writeUTF(commandInput);
                output.flush();

                CommandResult result = (CommandResult) input.readObject();
                System.out.println("Результат выполнения команды: " + result.getOutput());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при подключении к серверу: " + e.getMessage());
        }
    }
}
