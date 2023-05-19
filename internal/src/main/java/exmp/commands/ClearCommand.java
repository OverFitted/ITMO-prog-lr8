package exmp.commands;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "Очистить коллекцию";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию продуктов";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        return new ArrayList<>();
    }

    @Override
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            for (exmp.models.Product product: app.getProductRepository().findAll()) {
                app.getProductRepository().delete(product);
            }

            return new exmp.commands.CommandResult(0, "Коллекция успешно очищена", null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
