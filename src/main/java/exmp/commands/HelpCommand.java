package exmp.commands;

import exmp.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Команда help для получения информации по всем доступным командам
 */
public class HelpCommand implements Command {
    /**
     * Возвращает название команды help.
     *
     * @return название команды help.
     */
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        return new ArrayList<>();
    }

    /**
     * Возвращает описание команды help.
     *
     * @return описание команды help.
     */
    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    /**
     * Выполняет команду help
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(exmp.App app, Object... args) {
        System.out.println("Список доступных команд:");
        for (Map.Entry<String, Command> entry : app.getCommandHandler().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getDescription());
        }
    }
}