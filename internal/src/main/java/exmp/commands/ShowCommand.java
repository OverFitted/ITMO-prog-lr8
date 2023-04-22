package exmp.commands;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import exmp.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        return new ArrayList<>();
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
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            List<Product> products = app.getProductRepository().findAll();
            StringBuilder output = new StringBuilder();

            if (products != null && !products.isEmpty()) {
                products.forEach(product -> output.append(product.toString()).append("\n"));
            } else {
                return new exmp.commands.CommandResult(1, null, "Коллекция не содержит продуктов.");
            }

            return new exmp.commands.CommandResult(0, output.toString(), null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
