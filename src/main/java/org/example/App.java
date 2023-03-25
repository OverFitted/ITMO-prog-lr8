package org.example;

import org.example.models.Product;

import java.util.Vector;

public class App {
    private boolean status;
    private Vector<Product> products;
    private String fileName;

    public App(String[] args) {
        this.status = true;
        if (args.length == 0) {
            System.out.println("Пожалуйста, укажите имя файла с данными в качестве аргумента командной строки.");
            System.exit(1);
        }
        fileName = args[0];
        products = new Vector<>();
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

    public void loadData() {
//        TODO
    }
}
