package exmp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        App app = new App("src/main/resources/input.yaml");

        while (app.getStatus()) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            app.readLine(line);
            scanner.close();
        }
    }
}
