package exmp.commands;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("productIndex", Integer.class));
        return args;
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
        int index = (Integer) args[0];
        if (index < 1 || index > app.getProductRepository().findAll().size()) {
            System.out.println("Некорректный индекс элемента для удаления");
            return;
        }
        app.getProductRepository().findAll().remove(index - 1);
        System.out.println("Элемент с порядковым номером " + index + " удален из коллекции");
    }
}
