package exmp.commands;

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
    public void execute(exmp.App app, String[] args) {
        app.switchOff();
    }
}
