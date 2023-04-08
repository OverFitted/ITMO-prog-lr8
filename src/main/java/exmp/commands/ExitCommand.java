package exmp.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда exit для завершения работы приложения
 */
public class ExitCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды exit.
     *
     * @return название команды exit.
     */
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        return new ArrayList<>();
    }

    /**
     * Возвращает описание команды exit.
     *
     * @return описание команды exit.
     */
    @Override
    public String getDescription() {
        return "завершить программу";
    }

    /**
     * Выполняет команду завершения работы приложения
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(exmp.App app, Object... args) {
        app.switchOff();
    }
}
