package exmp.commands;

import exmp.models.Product;

import java.util.Vector;

public class ShowCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Вывести все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        Vector<Product> products = app.getProducts();
        if (products != null && !products.isEmpty()) {
            products.forEach(System.out::println);
        } else {
            System.out.println("Коллекция не содержит продуктов.");
        }
    }
}
