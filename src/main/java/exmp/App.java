package exmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import exmp.commands.HelpCommand;
import exmp.commands.InfoCommand;
import exmp.commands.ShowCommand;
import exmp.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    private final String fileName;
    private boolean status;
    private Vector<Product> products;
    private HashSet<Long> idList;
    private HashMap<String, exmp.commands.Command> commandHandlers;

    public App(String filename) {
        this.status = true;
        this.fileName = filename;
        this.commandHandlers = new HashMap<>();

        initCommands();
//        loadData();
    }

    public void switchOff() {
        this.status = false;
    }

    public Boolean getStatus() {
        return status;
    }

    public void readLine(String line) {
        String[] parsedLine = line.split(" ");
        String command = parsedLine[0];
        String[] arguments = Arrays.copyOfRange(parsedLine, 1, parsedLine.length);

        if (commandHandlers.containsKey(command)) {
            commandHandlers.get(command).execute(this, arguments);
        } else {
            System.out.println("Неизвестная команда. Попробуйте снова или введите 'help' для получения списка доступных команд.");
        }
    }

    public HashMap<String, exmp.commands.Command> getCommandHandler() {
        return this.commandHandlers;
    }

    private void initCommands() {
        this.commandHandlers.put("help", new HelpCommand());
        this.commandHandlers.put("info", new InfoCommand());
        this.commandHandlers.put("show", new ShowCommand());
    }

    private void loadData() {
        ObjectMapper xmlMapper = new XmlMapper();
        File file = new File(this.fileName);
        try {
            // Загрузка данных из файла
            List<Product> loadedProducts = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

            // Добавление загруженных данных в коллекцию products
            products.addAll(loadedProducts);

            System.out.println("Данные успешно загружены из файла: " + this.fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных из файла: " + this.fileName);
            e.printStackTrace();
        }
    }

    public Vector<Product> getProducts() {
        return products;
    }

    public Date getInitializationDate() {
        return new Date();
    }
}
