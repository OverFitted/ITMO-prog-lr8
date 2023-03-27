package exmp.commands;

import exmp.models.Coordinates;
import exmp.models.Location;
import exmp.models.Person;
import exmp.models.Product;
import exmp.enums.UnitOfMeasure;
import exmp.enums.Color;
import exmp.enums.Country;

import java.util.Scanner;

public class AddCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название продукта:");
        String name = scanner.nextLine().trim();

        System.out.println("Введите координаты продукта (x, y):");
        double x = scanner.nextDouble();
        float y = scanner.nextFloat();
        scanner.nextLine();
        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Введите цену продукта:");
        int price = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите номер части продукта (необязательно):");
        String partNumber = scanner.nextLine().trim();

        System.out.println("Введите стоимость производства продукта:");
        float manufactureCost = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Введите единицу измерения продукта (" + UnitOfMeasure.getValues() + "):");
        String unitOfMeasureStr = scanner.nextLine().trim().toUpperCase();
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(unitOfMeasureStr);

        System.out.println("Введите информацию о владельце продукта (имя, рост, цвет глаз, цвет волос, страну):");
        String ownerName = scanner.nextLine().trim();
        long height = scanner.nextLong();
        scanner.nextLine();
        Color eyeColor = Color.valueOf(scanner.nextLine().trim());
        Color hairColor = Color.valueOf(scanner.nextLine().trim());
        Country country = Country.valueOf(scanner.nextLine().trim());

        System.out.println("Введите информацию о локации владельца продукта (координаты x, y, z и название местоположения):");
        float loc_x = scanner.nextFloat();
        double loc_y = scanner.nextDouble();
        double loc_z = scanner.nextDouble();
        String loc_name = scanner.nextLine().trim();
        Location location = new Location(loc_x, loc_y, loc_z, loc_name);

        Person owner = new Person(ownerName, height, eyeColor, hairColor, country, location);

        Product product = new Product(name, coordinates, price, partNumber, manufactureCost, unitOfMeasure, owner);
        app.getProducts().add(product);
        System.out.println("Продукт успешно добавлен в коллекцию.");
    }
}
