package exmp.commands;

import java.util.List;
import java.util.Vector;

/**
 * Интерфейс Command определяет структуру и методы для команд приложения.
 */
public interface Command {

    /**
     * Возвращает название команды.
     *
     * @return название команды.
     */
    String getName();

    /**
     * Возвращает необходимые аргументы команды.
     *
     * @return необходимые аргументы команды.
     */
    List<exmp.commands.ArgDescriptor> getArguments();

    /**
     * Возвращает описание команды.
     *
     * @return описание команды.
     */
    String getDescription();

    /**
     * Выполняет команду с указанными аргументами.
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
     boolean execute(exmp.App app, Object... args);
}
