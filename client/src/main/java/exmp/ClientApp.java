package exmp;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        exmp.database.UserRepository userRepository = new exmp.database.UserRepository();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите логин:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (!userRepository.authenticateUser(username, password)) {
            System.out.println("Неверный логин или пароль. Хотите зарегистрироваться? (y/n)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                if (userRepository.registerUser(username, password)) {
                    System.out.println("Успешная регистрация!");
                } else {
                    System.out.println("Ошибка регистрации. Завершение работы.");
                    return;
                }
            } else {
                System.out.println("Завершение работы.");
                return;
            }
        }

        exmp.Client client = new exmp.Client("localhost", 38761);
        client.start();
    }
}
