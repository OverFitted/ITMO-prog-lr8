package exmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import exmp.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
        // TODO
    }

    private void loadData() {
        ObjectMapper yamlMapper = new yamlMapper();
        File file = new File(this.fileName);
        try {
            List<Product> loadedProducts = yamlMapper.readValue(file,
                    yamlMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

            this.products.addAll(loadedProducts);

            System.out.println("Данные успешно загружены из файла: " + this.fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных из файла: " + this.fileName);
            e.printStackTrace();
        }
    }
}
