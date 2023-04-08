package exmp.commands;

import exmp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class AddCommand implements exmp.commands.Command {
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
    public boolean execute(exmp.App app, Object... args) {
        try {
            app.getProductRepository().save((Product) args[0]);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
