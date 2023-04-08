package exmp.commands;

import exmp.App;
import exmp.models.Product;

/**
 * Команда add_if_max для добавления нового продукта в коллекцию, если его значение меньше минимального значения в коллекции
 */
public class AddMinCommand implements exmp.commands.Command {
    exmp.commands.Utils utils = new exmp.commands.Utils();

    /**
     * Возвращает название команды add_if_min.
     *
     * @return название команды add_if_min.
     */
    @Override
    public String getName() {
        return "add_if_min";
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
    public void execute(App app, String[] args) {
        Product newProduct = utils.ScanNewProduct();
        Product maxProduct = app.getProducts().stream().min(Product::compareTo).orElse(null);

        if (maxProduct == null || newProduct.compareTo(maxProduct) < 0) {
            app.getProducts().add(newProduct);
            System.out.println("Новый элемент успешно добавлен в коллекцию.");
        } else {
            System.out.println("Новый элемент не добавлен, так как его значение не меньше значения наименьшего элемента коллекции.");
        }
    }
}