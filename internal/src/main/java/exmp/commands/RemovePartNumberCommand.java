package exmp.commands;

import exmp.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * Команда remove_all_by_part_number для удаления все продуктов из колелкции по данному partNumber
 */
public class RemovePartNumberCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды remove_all_by_part_number.
     *
     * @return название команды remove_all_by_part_number.
     */
    @Override
    public String getName() {
        return "remove_all_by_part_number";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("partNumber", String.class));
        return args;
    }

    /**
     * Возвращает описание команды remove_all_by_part_number.
     *
     * @return описание команды remove_all_by_part_number.
     */
    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение поля partNumber которого эквивалентно заданному";
    }

    /**
     * Выполняет команду удаления элементов по заданному partNumber
     *
     * @param app  объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            String partNumber = (String) args[0];
            app.getProductRepository().findAll().removeIf((Product product) -> product.getPartNumber() != null
                    && product.getPartNumber().equals(partNumber));

            return new exmp.commands.CommandResult(0, "Продукт(ы) успешно удален(ы)", null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
