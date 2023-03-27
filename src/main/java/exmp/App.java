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
    private Vector<Product> products;
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
//        loadData();
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
        this.commandHandlers.put("help", new HelpCommand());
        this.commandHandlers.put("info", new InfoCommand());
        this.commandHandlers.put("show", new ShowCommand());
        this.commandHandlers.put("add", new AddCommand());
        this.commandHandlers.put("update_by_id", new UpdateIdCommand());
        this.commandHandlers.put("remove_by_id", new RemoveIdCommand());
//        this.commandHandlers.put("clear", new ClearCommand());
//        this.commandHandlers.put("save", new SaveCommand());
//        this.commandHandlers.put("execute_script", new ExecuteCommand());
//        this.commandHandlers.put("exit", new ExitCommand());
//        this.commandHandlers.put("remove_at_index", new RemoveIndexCommand());
//        this.commandHandlers.put("add_if_max", new AddMaxCommand());
//        this.commandHandlers.put("add_if_min", new AddMinCommand());
//        this.commandHandlers.put("remove_all_by_part_number", new RemovePartNumberCommand());
//        this.commandHandlers.put("group_counting_by_coordinates", new GroupCoordinatesCommand());
//        this.commandHandlers.put("filter_by_unit_of_measure", new FilterUnitOfMeasureCommand());
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
