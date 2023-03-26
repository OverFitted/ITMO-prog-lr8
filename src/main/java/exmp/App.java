package exmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import exmp.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedList;

public class App {
    private boolean status;
    private LinkedList<Product> products;
    private final String fileName;
    private File productsFile;
    private HashSet<Long> idList;

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
        // TODO
    }

    private void loadData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            this.productsFile = new File(this.fileName);
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(LinkedList.class, Product.class);
            objectMapper.registerModule(new JavaTimeModule());
            this.products = objectMapper.readValue(this.productsFile, listType);
            System.out.println("Файл успешно считан, коллекция заполнена данными с файла.");
            for (Product product : products) {
                this.idList.add(product.getId());
            }
        } catch (IOException e) {
            this.products = new LinkedList<Product>();
            System.out.println("Файл не найден.");
        }
    }
}
