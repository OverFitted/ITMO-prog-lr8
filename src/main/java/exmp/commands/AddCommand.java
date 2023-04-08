package exmp.commands;

import exmp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class AddCommand implements exmp.commands.Command {
    exmp.commands.Utils utils = new exmp.commands.Utils();

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("Product", Product.class));
        return args;
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(exmp.App app, Object... args) {
        app.getProducts().add((Product) args[0]);
        System.out.println("Продукт успешно добавлен в коллекцию.");
    }
}
