package exmp.commands;

import java.util.Vector;

public class InfoCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
    @Override
    public void execute(exmp.App app, String[] args) {
        System.out.println("Дата инициализации: " + app.getInitializationDate());

        Vector< exmp.models.Product > products = app.getProducts();
        if (products != null) {
            System.out.println("Тип коллекции: " + products.getClass().getName());
            System.out.println("Количество элементов: " + products.size());
        } else {
            System.out.println("Коллекция не содержит продуктов.");
        }
    }
}