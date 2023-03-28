package exmp.commands;

import exmp.App;
import exmp.models.Product;

public class AddMaxCommand implements exmp.commands.Command {
    exmp.commands.Utils utils = new exmp.commands.Utils();

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

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