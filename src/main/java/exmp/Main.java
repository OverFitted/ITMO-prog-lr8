package exmp;

import java.util.Scanner;

/**
 * Главный класс приложения. Запускает приложение, и обрабатывает ввод пользователя.
 */
public class Main {

    /**
     * Точка входа в приложение.
     * Создает экземпляр класса App, читает ввод пользователя и передает его в метод readLine() этого объекта,
     * пока статус приложения активен.
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        exmp.App app = new exmp.App("src/main/resources/input.xml");
        Scanner scanner = new Scanner(System.in);

        while (app.getStatus()) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                app.readLine(line);
            } else {
                System.out.println("Ввод закончился. Завершение программы.");
                app.switchOff();
                scanner.close();
                break;
            }
        }
    }
}
