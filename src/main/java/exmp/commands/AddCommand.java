package exmp.commands;

import exmp.models.Product;

/**
 * Команда add для добавления нового продукта в коллекцию
 */
public class AddCommand implements exmp.commands.Command {
    exmp.commands.Utils utils = new exmp.commands.Utils();

    /**
     * Возвращает название команды add.
     *
     * @return название команды add.
     */
    @Override
    public String getName() {
        return "add";
    }

    /**
     * Возвращает описание команды add.
     *
     * @return описание команды add.
     */
    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию";
    }

    /**
     * Выполняет команду добавления
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(exmp.App app, String[] args) {
        Product product = utils.ScanNewProduct();
        app.getProducts().add(product);
        System.out.println("Продукт успешно добавлен в коллекцию.");
    }
}
