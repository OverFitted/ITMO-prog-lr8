package exmp.commands;

import exmp.enums.Color;
import exmp.enums.Country;
import exmp.enums.UnitOfMeasure;
import exmp.models.Product;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateIdCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "update_by_id";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        if (args.length != 1) {
            System.out.println("Использование: update_by_id {id}");
            return;
        }

        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат id: " + e.getMessage());
            return;
        }

        Product oldProduct = app.getProducts().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (oldProduct == null) {
            System.out.println("Элемент с id " + id + " не найден");
        } else {
            Scanner scanner = new Scanner(System.in);
            HashMap<String, Consumer<String>> fieldsToUpdate = new HashMap<>() {{
                put("name", oldProduct::setName);
                put("x", value -> oldProduct.getCoordinates().setX(Double.parseDouble(value)));
                put("y", value -> oldProduct.getCoordinates().setY(Float.parseFloat(value)));
                put("price", value -> oldProduct.setPrice(Integer.parseInt(value)));
                put("partNumber", oldProduct::setPartNumber);
                put("manufactureCost", value -> oldProduct.setManufactureCost(Float.parseFloat(value)));
                put("unitOfMeasure", value -> oldProduct.setUnitOfMeasure(UnitOfMeasure.valueOf(value)));
                put("ownerName", value -> oldProduct.getOwner().setName(value));
                put("ownerHeight", value -> oldProduct.getOwner().setHeight(Long.valueOf(value)));
                put("ownerEyeColor", value -> oldProduct.getOwner().setEyeColor(Color.valueOf(value)));
                put("ownerHairColor", value -> oldProduct.getOwner().setHairColor(Color.valueOf(value)));
                put("ownerNationality", value -> oldProduct.getOwner().setNationality(Country.valueOf(value)));
                put("ownerX", value -> oldProduct.getOwner().getLocation().setX(Float.parseFloat(value)));
                put("ownerY", value -> oldProduct.getOwner().getLocation().setY(Double.parseDouble(value)));
                put("ownerZ", value -> oldProduct.getOwner().getLocation().setZ(Double.parseDouble(value)));
                put("ownerLocationName", value -> oldProduct.getOwner().getLocation().setName(value));
            }};

            System.out.println("Выберите поле для обновления: " + String.join(", ", fieldsToUpdate.keySet()));
            String field;
            while (true) {
                field = scanner.nextLine();
                if (fieldsToUpdate.containsKey(field)) {
                    break;
                }
                System.out.println("Поле " + field + " не найдено, попробуйте еще раз");
            }

            System.out.println("Введите новое значение поля " + field);
            String newValue = scanner.nextLine();
            fieldsToUpdate.get(field).accept(newValue);
            System.out.println("Элемент с id " + id + " обновлен");
        }
    }
}
