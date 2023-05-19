package exmp.commands;

import java.util.ArrayList;
import java.util.List;

public class FilterCountryCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "Отфильтровать по country";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("country", exmp.enums.Country.class));
        return args;
    }

    @Override
    public String getDescription() {
        return "получить элементы коллекции по значению поля country";
    }

    @Override
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            exmp.enums.Country country = (exmp.enums.Country) args[0];
            List<exmp.models.Product> output = app.getProductRepository().findByCountry(country);

            return new exmp.commands.CommandResult(0, output.toString(), output, null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
