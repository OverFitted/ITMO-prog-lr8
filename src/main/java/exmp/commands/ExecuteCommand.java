package exmp.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteCommand implements exmp.commands.Command {
    private static final int RECURSION_LIMIT = 3;
    private int recursionDepth = 0;

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "Считать и исполнить скрипт из указанного файла.";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        if (args.length < 1) {
            System.out.println("Не указан путь к файлу скрипта.");
            return;
        }
        String fileName = args[0];

        if (recursionDepth >= RECURSION_LIMIT) {
            System.out.println("Достигнут предел рекурсии для выполнения скриптов.");
            return;
        }

        recursionDepth++;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                app.readLine(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } finally {
            recursionDepth--;
        }
    }
}
