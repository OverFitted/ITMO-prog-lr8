package exmp.commands;

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
    public void execute(exmp.App app, String[] args) {
        System.out.println("Дата инициализации: " + app.getInitializationDate());

        Vector< exmp.models.Product > products = app.getProducts();
        if (products != null) {
            System.out.println("Тип коллекции: " + products.getClass().getName());
            System.out.println("Количество элементов: " + products.size());
        } else {
            System.out.println("Коллекция не содержит продуктов.");
        }
    }
}