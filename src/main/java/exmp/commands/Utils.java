package exmp.commands;

import exmp.enums.Color;
import exmp.enums.Country;
import exmp.enums.UnitOfMeasure;
import exmp.models.Coordinates;
import exmp.models.Location;
import exmp.models.Person;
import exmp.models.Product;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
    public static Product ScanNewProduct(Scanner scanner) {
        String name = scanner.next().trim();

        double x;
        float y;
        try {
            x = scanner.nextDouble();
            y = scanner.nextFloat();
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }
        Coordinates coordinates = new Coordinates(x, y);

        int price;
        try {
            price = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }

        String partNumber = scanner.next().trim();

        float manufactureCost;
        try {
            manufactureCost = scanner.nextFloat();
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }

        String unitOfMeasureStr;
        unitOfMeasureStr = scanner.next().trim().toUpperCase();
        if (!UnitOfMeasure.getValues().contains(unitOfMeasureStr)) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(unitOfMeasureStr.toUpperCase());

        String ownerName = scanner.next().trim();

        long height;
        try {
            height = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }

        String eyeColorStr;
        eyeColorStr = scanner.next().trim().toUpperCase();
        if (!Color.getValues().contains(eyeColorStr)) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }
        Color eyeColor = Color.valueOf(eyeColorStr);

        String hairColorStr;
        hairColorStr = scanner.next().trim().toUpperCase();
        if (!Color.getValues().contains(hairColorStr)) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }
        Color hairColor = Color.valueOf(hairColorStr);

        String countryStr;
        countryStr = scanner.next().trim().toUpperCase();
        if (!Country.getValues().contains(countryStr)) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }
        Country country = Country.valueOf(countryStr);

        float loc_x;
        double loc_y;
        double loc_z;
        try {
            loc_x = scanner.nextFloat();
            loc_y = scanner.nextDouble();
            loc_z = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода, попробуйте еще раз.");
            return ScanNewProduct(scanner);
        }

        String loc_name = scanner.next().trim();
        Location location = new Location(loc_x, loc_y, loc_z, loc_name);

        Person owner = new Person(ownerName, height, eyeColor, hairColor, country, location);

        return new Product(name, coordinates, price, partNumber, manufactureCost, unitOfMeasure, owner);
    }
}
