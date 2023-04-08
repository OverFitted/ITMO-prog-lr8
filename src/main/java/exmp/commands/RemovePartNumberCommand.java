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
    public void execute(exmp.App app, Object... args) {
        if (args.length < 1) {
            System.out.println("Не задано значение поля partNumber");
            return;
        }

        String partNumber = (String) args[0];

        int count = app.getProductRepository().findAll().removeIf((Product product) -> product.getPartNumber()
                != null && product.getPartNumber().equals(partNumber)) ? 1 : 0;

        if (count == 0) {
            System.out.println("Не найдено ни одного элемента с заданным значением поля partNumber");
        } else {
            System.out.println("Удалено элементов: " + count);
        }
    }
}
