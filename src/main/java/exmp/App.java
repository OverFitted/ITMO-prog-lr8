package exmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exmp.commands.HelpCommand;
import exmp.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class App {
    private boolean status;
    private LinkedList<Product> products;
    private final String fileName;
    private HashSet<Long> idList;
    private HashMap<String, Consumer<String>> commandHandlers;

    public App(String filename) {
        this.status = true;
        this.fileName = filename;
        this.commandHandlers = new HashMap<>();

        initCommands();
        loadData();
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
        String arguments = String.join(" ", Arrays.copyOfRange(parsedLine, 1, parsedLine.length));

        if (commandHandlers.containsKey(command)) {
            commandHandlers.get(command).accept(arguments);
        } else {
            System.out.println("Неизвестная команда. Попробуйте снова или введите 'help' для получения списка доступных команд.");
        }
    }

    private void helpCommandHandler(String arguments) {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute(this, arguments.split(" "));
    }

    private void initCommands() {
         this.commandHandlers.put("help", this::helpCommandHandler);
    }

    private void loadData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            File productsFile = new File(this.fileName);
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(LinkedList.class, Product.class);
            objectMapper.registerModule(new JavaTimeModule());
            this.products = objectMapper.readValue(productsFile, listType);
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
