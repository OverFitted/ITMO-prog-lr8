package exmp;

import exmp.commands.ArgDescriptor;
import exmp.commands.Command;
import exmp.commands.CommandResult;
import exmp.commands.Utils;
import exmp.models.Product;

import java.lang.reflect.Executable;
import java.util.*;
import java.util.function.Consumer;

/**
 * Класс приложения.
 */
public class App {
    private final Date initializationDate;
    private final String fileName;
    private boolean status;
    private final exmp.repository.ProductRepository productRepository = new exmp.repository.InMemoryProductRepository();
    private HashSet<Long> idList;
    private final HashMap<String, exmp.commands.Command> commandHandlers;
    private HashMap<Class<?>, Consumer<Scanner>> argHandlers;

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
        this.argHandlers = new HashMap<>();

        initCommands();
        productRepository.loadData(fileName);
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

    public void executeCommand(String commandName, String input) {
        Command command = commandHandlers.get(commandName);
        if (command == null) {
            System.err.println("Неизвестная команда: " + commandName);
            return;
        }

        List<ArgDescriptor> argDescriptors = command.getArguments();
        List<Object> args = new ArrayList<>();
        initArgs(args);

        Scanner scanner = new Scanner(input);
        for (ArgDescriptor argDescriptor : argDescriptors) {
            this.argHandlers.get(argDescriptor.type()).accept(scanner);
        }

        CommandResult result = command.execute(this, args.toArray());
        if (result.isSuccess()) {
            System.out.println(result.getOutput());
        } else {
            System.err.println("Команда завершилась с ошибкой: " + result.getErrorMessage());
        }
    }

    /**
     * Обрабатывает введенную пользователем команду.
     *
     * @param line - введенная пользователем команда.
     */
    public void readLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        String[] inputParts = line.trim().split("\\s+", 2);
        String commandName = inputParts[0];
        String arguments = inputParts.length > 1 ? inputParts[1] : "";

        executeCommand(commandName, arguments);
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
        this.commandHandlers.put("execute_script", new exmp.commands.ExecuteCommand());
        this.commandHandlers.put("exit", new exmp.commands.ExitCommand());
        this.commandHandlers.put("remove_at", new exmp.commands.RemoveIndexCommand());
        this.commandHandlers.put("add_if_max", new exmp.commands.AddMaxCommand());
        this.commandHandlers.put("add_if_min", new exmp.commands.AddMinCommand());
        this.commandHandlers.put("remove_all_by_part_number", new exmp.commands.RemovePartNumberCommand());
        this.commandHandlers.put("group_counting_by_coordinates", new exmp.commands.GroupCoordinatesCommand());
        this.commandHandlers.put("filter_by_unit_of_measure", new exmp.commands.FilterUnitOfMeasureCommand());
    }

    private void initArgs(List<Object> args) {
        this.argHandlers.put(Product.class, scanner -> args.add(Utils.ScanNewProduct(scanner)));
        this.argHandlers.put(exmp.enums.Color.class, scanner -> args.add(exmp.enums.Color.valueOf(scanner.next())));
        this.argHandlers.put(exmp.enums.Country.class, scanner -> args.add(exmp.enums.Country.valueOf(scanner.next())));
        this.argHandlers.put(exmp.enums.UnitOfMeasure.class, scanner -> args.add(exmp.enums.UnitOfMeasure.valueOf(scanner.next())));
        this.argHandlers.put(Integer.class, scanner -> args.add(scanner.nextInt()));
        this.argHandlers.put(Long.class, scanner -> args.add(scanner.nextLong()));
        this.argHandlers.put(Float.class, scanner -> args.add(scanner.nextFloat()));
        this.argHandlers.put(Double.class, scanner -> args.add(scanner.nextDouble()));
        this.argHandlers.put(String.class, scanner -> args.add(scanner.next()));
    }

    public exmp.repository.ProductRepository getProductRepository() {
        return productRepository;
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
