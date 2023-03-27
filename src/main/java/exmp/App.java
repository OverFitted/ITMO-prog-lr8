package exmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import exmp.commands.*;
import exmp.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Класс приложения.
 */
public class App {
    private final Date initializationDate;
    private final String fileName;
    private boolean status;
    private final Vector<Product> products = new Vector<Product>();
    private HashSet<Long> idList;
    private HashMap<String, exmp.commands.Command> commandHandlers;

    /**
     * Конструктор класса App.
     *
     * @param filename - путь к файлу, содержащему данные.
     */
    public App(String filename) {
        this.status = true;
        this.fileName = filename;
        this.initializationDate = new Date();
        this.commandHandlers = new HashMap<>();

        initCommands();
        loadData();
    }

    /**
     * Выключает приложение.
     */
    public void switchOff() {
        this.status = false;
    }

    /**
     * Возвращает состояние приложения.
     *
     * @return true, если приложение работает, false - иначе.
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * Обрабатывает введенную пользователем команду.
     *
     * @param line - введенная пользователем команда.
     */
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

    /**
     * Возвращает обработчики команд.
     *
     * @return хэш-таблицу, содержащую обработчики команд.
     */
    public HashMap<String, exmp.commands.Command> getCommandHandler() {
        return this.commandHandlers;
    }

    /**
     * Инициализирует обработчики команд.
     */
    private void initCommands() {
        this.commandHandlers.put("help", new exmp.commands.HelpCommand());
        this.commandHandlers.put("info", new exmp.commands.InfoCommand());
        this.commandHandlers.put("show", new exmp.commands.ShowCommand());
        this.commandHandlers.put("add", new exmp.commands.AddCommand());
        this.commandHandlers.put("update", new exmp.commands.UpdateIdCommand());
        this.commandHandlers.put("remove_by_id", new exmp.commands.RemoveIdCommand());
        this.commandHandlers.put("clear", new exmp.commands.ClearCommand());
        this.commandHandlers.put("save", new exmp.commands.SaveCommand());
//        this.commandHandlers.put("execute_script", new exmp.commands.ExecuteCommand());
        this.commandHandlers.put("exit", new exmp.commands.ExitCommand());
        this.commandHandlers.put("remove_at", new exmp.commands.RemoveIndexCommand());
//        this.commandHandlers.put("add_if_max", new exmp.commands.AddMaxCommand());
//        this.commandHandlers.put("add_if_min", new exmp.commands.AddMinCommand());
        this.commandHandlers.put("remove_all_by_part_number", new exmp.commands.RemovePartNumberCommand());
//        this.commandHandlers.put("group_counting_by_coordinates", new exmp.commands.GroupCoordinatesCommand());
        this.commandHandlers.put("filter_by_unit_of_measure", new exmp.commands.FilterUnitOfMeasureCommand());
    }

    /**
     * Загружает данные из файла и инициализирует коллекцию {@code products}.
     * Если загрузка данных прошла успешно, выводит сообщение об успешной загрузке.
     * Если возникла ошибка при загрузке данных, выводит сообщение об ошибке.
     */
    private void loadData() {
        ObjectMapper xmlMapper = new XmlMapper();
        File file = new File(this.fileName);
        try {
            // Загрузка данных из файла
            List<Product> loadedProducts = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

            // Добавление загруженных данных в коллекцию products
            this.products.addAll(loadedProducts);

            System.out.println("Данные успешно загружены из файла: " + this.fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных из файла: " + this.fileName);
            e.printStackTrace();
        }
    }

    /**
     * Возвращает коллекцию объектов {@link Product}.
     *
     * @return коллекция объектов {@link Product}.
     */
    public Vector<Product> getProducts() {
        return this.products;
    }

    /**
     * Возвращает дату инициализации приложения.
     *
     * @return дата инициализации приложения
     */
    public Date getInitializationDate() {
        return this.initializationDate;
    }
}
