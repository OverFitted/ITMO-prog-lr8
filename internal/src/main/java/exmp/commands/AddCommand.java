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
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            Product product = (Product) args[0];
            product.setUserId((Long) args[1]);
            app.getProductRepository().save(product);

            return new exmp.commands.CommandResult(0, "Продукт успешно добавлен", null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
