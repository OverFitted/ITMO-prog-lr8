package exmp.commands;

import exmp.models.Product;

public class AddCommand implements exmp.commands.Command {
    exmp.commands.Utils utils = new exmp.commands.Utils();

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        Product product = utils.ScanNewProduct();
        app.getProducts().add(product);
        System.out.println("Продукт успешно добавлен в коллекцию.");
    }
}
