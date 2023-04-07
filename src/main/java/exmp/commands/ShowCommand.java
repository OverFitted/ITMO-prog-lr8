package exmp.commands;

import exmp.models.Product;

import java.util.Vector;

/**
 * Команда show для получения информации о всех элементах колелкции
 */
public class ShowCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды show.
     *
     * @return название команды show.
     */
    @Override
    public String getName() {
        return "show";
    }

    /**
     * Возвращает описание команды show.
     *
     * @return описание команды show.
     */
    @Override
    public String getDescription() {
        return "Вывести все элементы коллекции в строковом представлении";
    }

    /**
     * Выполняет команду show
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public void execute(exmp.App app, String[] args) {
        Vector<Product> products = app.getProducts();
        if (products != null && !products.isEmpty()) {
            products.forEach(System.out::println);
        } else {
            System.out.println("Коллекция не содержит продуктов.");
        }
    }
}
