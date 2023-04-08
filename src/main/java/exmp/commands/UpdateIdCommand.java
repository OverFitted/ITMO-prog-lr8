package exmp.commands;

import exmp.enums.Color;
import exmp.enums.Country;
import exmp.enums.UnitOfMeasure;
import exmp.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Команда update_by_id для изменения информации о продукте коллекции по заданному id
 */
public class UpdateIdCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды update_by_id.
     *
     * @return название команды update_by_id.
     */
    @Override
    public String getName() {
        return "update_by_id";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("productId", Long.class));
        args.add(new exmp.commands.ArgDescriptor("Product", Product.class));
        return args;
    }

    /**
     * Возвращает описание команды update_by_id.
     *
     * @return описание команды update_by_id.
     */
    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    /**
     * Выполняет команду обновления информации о продукте по id
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(exmp.App app, Object... args) {
        if (args.length != 2) {
            System.out.println("Использование: update_by_id {id} {element}");
            return;
        }

        long id = (Long) args[0];
        Product newProduct = (Product) args[1];
        app.getProductRepository().deleteById(id);
        app.getProductRepository().save(newProduct);

        System.out.println("Элемент с id " + id + " обновлен");
    }
}
