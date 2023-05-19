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
        return "Завершить программу";
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
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            app.switchOff();

            return new exmp.commands.CommandResult(0, "Программа успешно завершена", null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
