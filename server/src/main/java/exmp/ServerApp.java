package exmp;

import java.util.Random;

/**
 * Главный класс приложения. Запускает приложение, и обрабатывает ввод пользователя.
 */
public class ServerApp {
    /**
     * Точка входа в приложение.
     * Создает экземпляр класса App, читает ввод пользователя и передает его в метод readLine() этого объекта,
     * пока статус приложения активен.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Random rand = new Random();
        exmp.Server server = new exmp.Server(rand.nextInt(65535 - 10000) + 10000, new exmp.App("src/main/resources/input.xml"));
        server.start();
    }
}
