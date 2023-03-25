package org.example;

import org.example.models.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class App {
    private boolean status;
    private Vector<Product> products;
    private final String fileName;

    public App(String filename) {
        this.status = true;
        this.fileName = filename;
        loadData();
        status = true;
    }

    public void switchOff() {
        this.status = false;
    }

    public Boolean getStatus() {
        return status;
    }

    public void readLine(String line) {
//        TODO
    }

    private void loadData() {
        // Реализуйте чтение данных из файла с использованием InputStreamReader
        try {
            File file = new File(fileName);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            // YAML -> Product -> this.products
        } catch (Exception e) {
            System.out.println("Не удалось загрузить данные из файла: " + e.getMessage());
        }
    }
}
