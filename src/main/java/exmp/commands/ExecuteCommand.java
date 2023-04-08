package exmp.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("fileName", String.class));
        return args;
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
    public boolean execute(exmp.App app, Object... args) {
        try {
            String fileName = (String) args[0];

            if (recursionDepth >= RECURSION_LIMIT) {
                System.out.println("Достигнут предел рекурсии для выполнения скриптов.");
                return true;
            }

            recursionDepth++;

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    app.readLine(line);
                }
            } catch (IOException e) {
                return false;
            } finally {
                recursionDepth--;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
