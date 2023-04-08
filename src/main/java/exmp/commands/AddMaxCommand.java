package exmp.commands;

import exmp.App;
import exmp.models.Product;

/**
 * Команда add_if_max для добавления нового продукта в коллекцию, если его значение превышает максимальное значение в коллекции
 */
public class AddMaxCommand implements exmp.commands.Command {
    exmp.commands.Utils utils = new exmp.commands.Utils();

    /**
     * Возвращает название команды add_if_max.
     *
     * @return название команды add_if_max.
     */
    @Override
    public String getName() {
        return "add_if_max";
    }

    /**
     * Возвращает описание команды add_if_max.
     *
     * @return описание команды add_if_max.
     */
    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    /**
     * Выполняет команду добавления элемента в коллекцию, если его значение превышает максимальное
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(App app, String[] args) {
        Product newProduct = utils.ScanNewProduct();
        Product maxProduct = app.getProducts().stream().max(Product::compareTo).orElse(null);

        if (maxProduct == null || newProduct.compareTo(maxProduct) > 0) {
            app.getProducts().add(newProduct);
            System.out.println("Новый элемент успешно добавлен в коллекцию.");
        } else {
            System.out.println("Новый элемент не добавлен, так как его значение не превышает значение наибольшего элемента коллекции.");
        }
    }
}