package exmp;

import java.util.Scanner;

public class Main {
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
