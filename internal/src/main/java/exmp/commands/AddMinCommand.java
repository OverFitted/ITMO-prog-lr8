package exmp.commands;

import exmp.App;
import exmp.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда add_if_max для добавления нового продукта в коллекцию, если его значение меньше минимального значения в коллекции
 */
public class AddMinCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды add_if_min.
     *
     * @return название команды add_if_min.
     */
    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("Product", Product.class));
        return args;
    }

    /**
     * Возвращает описание команды add_if_min.
     *
     * @return описание команды add_if_min.
     */
    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию, если его значение меньше значения наименьшего элемента этой коллекции";
    }

    /**
     * Выполняет команду добавления элемента в коллекцию, если его значение меньше минимального
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public exmp.commands.CommandResult execute(App app, Object... args) {
        try {
            Product newProduct = (Product) args[0];
            newProduct.setUserId((Long) args[1]);
            Product maxProduct = app.getProductRepository().findAll().stream().min(Product::compareTo).orElse(null);

            if (maxProduct == null || newProduct.compareTo(maxProduct) < 0) {
                app.getProductRepository().save(newProduct);
            } else {
                return new exmp.commands.CommandResult(1, null, "Значение не меньше значения наименьшего элемента коллекции");
            }

            return new exmp.commands.CommandResult(0, "Продукт успешно добавлен", null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}