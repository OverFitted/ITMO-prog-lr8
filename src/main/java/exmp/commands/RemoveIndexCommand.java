package exmp.commands;

/**
 * Команда remove_at для удаления продукта из колелкции по его индексу
 */
public class RemoveIndexCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды remove_at.
     *
     * @return название команды remove_at.
     */
    @Override
    public String getName() {
        return "remove_at";
    }

    /**
     * Возвращает описание команды remove_at.
     *
     * @return описание команды remove_at.
     */
    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его порядковому номеру";
    }

    /**
     * Выполняет команду удаления элемента по заданному индексу
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(exmp.App app, Object... args) {
        if (args.length == 0) {
            System.out.println("Необходимо указать порядковый номер элемента для удаления");
            return;
        }

        try {
            int index = Integer.parseInt(args[0]);
            if (index < 1 || index > app.getProducts().size()) {
                System.out.println("Некорректный индекс элемента для удаления");
                return;
            }
            app.getProducts().remove(index - 1);
            System.out.println("Элемент с порядковым номером " + index + " удален из коллекции");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат порядкового номера элемента для удаления");
        }
    }
}
