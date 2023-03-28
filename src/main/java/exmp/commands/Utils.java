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
    public Product ScanNewProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название продукта:");
        String name = scanner.nextLine().trim();

        double x = 0;
        float y = 0;
        boolean coordinatesInputOk = false;
        while (!coordinatesInputOk) {
            System.out.println("Введите координаты продукта (x, y):");
            try {
                x = scanner.nextDouble();
                y = scanner.nextFloat();
                coordinatesInputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        Coordinates coordinates = new Coordinates(x, y);

        int price = 0;
        boolean priceInputOk = false;
        while (!priceInputOk) {
            System.out.println("Введите цену продукта:");
            try {
                price = scanner.nextInt();
                priceInputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        System.out.println("Введите номер части продукта (необязательно):");
        String partNumber = scanner.nextLine().trim();

        float manufactureCost = 0;
        boolean manufactureInputOk = false;
        while (!manufactureInputOk) {
            System.out.println("Введите стоимость производства продукта:");
            try {
                manufactureCost = scanner.nextFloat();
                manufactureInputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        String unitOfMeasureStr = "";
        boolean unitsInputOk = false;
        while (!unitsInputOk) {
            System.out.println("Введите единицу измерения продукта (" + UnitOfMeasure.getValues() + "):");
            unitOfMeasureStr = scanner.nextLine().trim().toUpperCase();
            if (UnitOfMeasure.getValues().contains(unitOfMeasureStr)) {
                unitsInputOk = true;
            } else {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
            }
        }
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(unitOfMeasureStr.toUpperCase());

        System.out.println("Введите информацию о владельце продукта (имя, рост, цвет глаз, цвет волос, страну):");
        String ownerName = scanner.nextLine().trim();

        long height = 0;
        boolean heightInputOk = false;
        while (!heightInputOk) {
            try {
                height = scanner.nextLong();
                heightInputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        String eyeColorStr = "";
        boolean eyeColorInputOk = false;
        while (!eyeColorInputOk) {
            System.out.println("Введите цвет глаз владельца продукта (" + Color.getValues() + "):");
            eyeColorStr = scanner.nextLine().trim().toUpperCase();
            if (Color.getValues().contains(eyeColorStr)) {
                eyeColorInputOk = true;
            } else {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
            }
        }
        Color eyeColor = Color.valueOf(eyeColorStr);

        String hairColorStr = "";
        boolean hairColorInputOk = false;
        while (!hairColorInputOk) {
            System.out.println("Введите цвет волос владельца продукта (" + Color.getValues() + "):");
            hairColorStr = scanner.nextLine().trim().toUpperCase();
            if (Color.getValues().contains(hairColorStr)) {
                hairColorInputOk = true;
            } else {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
            }
        }
        Color hairColor = Color.valueOf(hairColorStr);

        String countryStr = "";
        boolean countryInputOk = false;
        while (!countryInputOk) {
            System.out.println("Введите страну владельца продукта (" + Country.getValues() + "):");
            countryStr = scanner.nextLine().trim().toUpperCase();
            if (Country.getValues().contains(countryStr)) {
                countryInputOk = true;
            } else {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
            }
        }
        Country country = Country.valueOf(countryStr);

        float loc_x = 0;
        double loc_y = 0;
        double loc_z = 0;
        boolean locationInputOk = false;
        while (!locationInputOk) {
            System.out.println("Введите информацию о локации владельца продукта (координаты x, y, z и название местоположения):");
            try {
                loc_x = scanner.nextFloat();
                loc_y = scanner.nextDouble();
                loc_z = scanner.nextDouble();
                locationInputOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Ошибка ввода, попробуйте еще раз.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        String loc_name = scanner.nextLine().trim();
        Location location = new Location(loc_x, loc_y, loc_z, loc_name);

        Person owner = new Person(ownerName, height, eyeColor, hairColor, country, location);

        return new Product(name, coordinates, price, partNumber, manufactureCost, unitOfMeasure, owner);
    }
}
