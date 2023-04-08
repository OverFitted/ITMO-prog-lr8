package exmp.commands;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        return new ArrayList<>();
    }

    @Override
    public void execute(exmp.App app, Object... args) {
        for (exmp.models.Product product: app.getProductRepository().findAll()) {
            app.getProductRepository().delete(product);
        }
        System.out.println("Коллекция очищена");
    }
}
