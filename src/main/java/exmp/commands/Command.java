package exmp.commands;

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
    void execute(exmp.App app, String[] args);
}
