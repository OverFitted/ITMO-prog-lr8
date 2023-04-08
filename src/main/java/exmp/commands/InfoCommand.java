package exmp.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Команда help для получения информации о коллекции продуктов
 */
public class InfoCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды info.
     *
     * @return название команды info.
     */
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        return new ArrayList<>();
    }

    /**
     * Возвращает описание команды info.
     *
     * @return описание команды info.
     */
    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    /**
     * Выполняет команду info
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public boolean execute(exmp.App app, Object... args) {
        try {
            System.out.println("Дата инициализации: " + app.getInitializationDate());

            List<exmp.models.Product> products = app.getProductRepository().findAll();
            if (products != null) {
                System.out.println("Тип коллекции: " + products.getClass().getName());
                System.out.println("Количество элементов: " + products.size());
            } else {
                System.out.println("Коллекция не содержит продуктов.");
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}