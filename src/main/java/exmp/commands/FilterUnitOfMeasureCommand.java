package exmp.commands;

import exmp.enums.UnitOfMeasure;
import exmp.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда filter_by_unit_of_measure для применения фильтра по значению unitOfMeasure к элементам коллекции
 */
public class FilterUnitOfMeasureCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды filter_by_unit_of_measure.
     *
     * @return название команды filter_by_unit_of_measure.
     */
    @Override
    public String getName() {
        return "filter_by_unit_of_measure";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("unit_of_measure", UnitOfMeasure.class));
        return args;
    }

    /**
     * Возвращает описание команды filter_by_unit_of_measure.
     *
     * @return описание команды filter_by_unit_of_measure.
     */
    @Override
    public String getDescription() {
        return "вывести элементы, значение поля unitOfMeasure которых равно заданному";
    }

    /**
     * Выполняет команду применения фильтра
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            UnitOfMeasure unitOfMeasure = (UnitOfMeasure) args[0];
            List<Product> filteredProducts = app.getProductRepository().findAll().stream()
                    .filter(product -> product.getUnitOfMeasure() == unitOfMeasure)
                    .toList();

            if (filteredProducts.isEmpty()) {
                return new exmp.commands.CommandResult(1, null, "Продукты не найдены");
            }

            StringBuilder output = new StringBuilder();
            filteredProducts.forEach(product -> output.append(product.toString()).append("\n"));
            return new exmp.commands.CommandResult(0, output.toString(), null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
