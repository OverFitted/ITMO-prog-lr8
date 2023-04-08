package exmp.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Команда execute_script для загрузки и выполнения скриптов
 */
public class ExecuteCommand implements exmp.commands.Command {
    private static final int RECURSION_LIMIT = 3;
    private int recursionDepth = 0;

    /**
     * Возвращает название команды execute_script.
     *
     * @return название команды execute_script.
     */
    @Override
    public String getName() {
        return "execute_script";
    }

    /**
     * Возвращает описание команды execute_script.
     *
     * @return описание команды execute_script.
     */
    @Override
    public String getDescription() {
        return "Считать и исполнить скрипт из указанного файла.";
    }

    /**
     * Выполняет команду загрузки и выполнения скрипта
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(exmp.App app, Object... args) {
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
