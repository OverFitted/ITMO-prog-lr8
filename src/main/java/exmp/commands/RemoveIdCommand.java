package exmp.commands;

import exmp.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Команда remove_by_id для удаления продукта из колелкции по его id
 */
public class RemoveIdCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды remove_by_id.
     *
     * @return название команды remove_by_id.
     */
    @Override
    public String getName() {
        return "remove_by_id";
    }

    /**
     * Возвращает описание команды remove_by_id.
     *
     * @return описание команды remove_by_id.
     */
    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("productId", Long.class));
        return args;
    }

    /**
     * Выполняет команду удаления элемента по заданному id
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public boolean execute(exmp.App app, Object... args) {
        try {
            Long id = (Long) args[0];
            app.getProductRepository().deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
