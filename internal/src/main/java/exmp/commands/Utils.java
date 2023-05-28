package exmp.commands;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exmp.enums.Color;
import exmp.enums.Country;
import exmp.enums.UnitOfMeasure;
import exmp.models.Coordinates;
import exmp.models.Location;
import exmp.models.Person;
import exmp.models.Product;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Utils {
    public static Product ScanNewProduct(Scanner scanner) {
        String jsonInput = scanner.nextLine();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        objectMapper.setDateFormat(df);

        Product product = null;

        try {
            product = objectMapper.readValue(jsonInput, Product.class);
        } catch (IOException e) {
            System.err.println("Ошибка при разборе JSON, попробуйте еще раз.");
            e.printStackTrace();
        }

        return product;
    }
}
