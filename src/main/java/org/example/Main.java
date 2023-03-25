package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        App app = new App("resources/input.yaml");
        Scanner keyboard = new Scanner(System.in);

        while (app.getStatus()) {
            String line = keyboard.nextLine();
            app.readLine(line);
        }

        keyboard.close();
    }
}
