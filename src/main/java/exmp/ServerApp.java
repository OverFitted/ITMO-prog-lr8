package exmp;

import exmp.server.Server;

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
        Server server = new Server(5555, new exmp.App("src/main/resources/input.xml"));
        server.start();
    }
}
