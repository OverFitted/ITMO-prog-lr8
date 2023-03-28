package exmp.commands;

import exmp.App;
import exmp.models.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class GroupCoordinatesCommand implements exmp.commands.Command {

    @Override
    public String getName() {
        return "group_counting_by_coordinates";
    }

    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля coordinates, вывести количество элементов в каждой группе";
    }

    @Override
    public void execute(App app, String[] args) {
        Map<String, Integer> coordinatesGroups = new HashMap<>();

        app.getProducts().forEach(product -> {
            Coordinates coordinates = product.getCoordinates();
            String coordinatesString = coordinates.toString();

            coordinatesGroups.put(coordinatesString, coordinatesGroups.getOrDefault(coordinatesString, 0) + 1);
        });

        System.out.println("Группировка элементов коллекции по координатам:");
        coordinatesGroups.forEach((coordinates, count) -> System.out.println("Координаты: " + coordinates + " | Количество элементов: " + count));
    }
}
