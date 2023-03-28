package exmp.commands;

import exmp.App;
import exmp.models.Product;

public class AddMinCommand implements exmp.commands.Command {
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
        Product minProduct = app.getProducts().stream().min(Product::compareTo).orElse(null);

        if (minProduct == null || newProduct.compareTo(minProduct) < 0) {
            app.getProducts().add(newProduct);
            System.out.println("Новый элемент успешно добавлен в коллекцию.");
        } else {
            System.out.println("Новый элемент не добавлен, так как его значение не превышает значение наибольшего элемента коллекции.");
        }
    }
}