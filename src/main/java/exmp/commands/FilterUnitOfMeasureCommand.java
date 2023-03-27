package exmp.commands;

import exmp.enums.UnitOfMeasure;
import exmp.models.Product;

import java.util.List;
import java.util.stream.Collectors;

public class FilterUnitOfMeasureCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "filter_by_unit_of_measure";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля unitOfMeasure которых равно заданному";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        if (args.length != 1) {
            System.out.println("Использование: filter_by_unit_of_measure {unit_of_measure}");
            return;
        }

        UnitOfMeasure unitOfMeasure;
        try {
            unitOfMeasure = UnitOfMeasure.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный аргумент: " + args[0]);
            return;
        }

        List<Product> filteredProducts = app.getProducts().stream()
                .filter(product -> product.getUnitOfMeasure() == unitOfMeasure)
                .toList();

        if (filteredProducts.isEmpty()) {
            System.out.println("Продукты с единицей измерения " + unitOfMeasure + " не найдены");
            return;
        }

        filteredProducts.forEach(System.out::println);
    }
}
