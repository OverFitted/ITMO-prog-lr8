package exmp.commands;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Команда save для сохранения коллекции в файл
 */
public class SaveCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды save.
     *
     * @return название команды save.
     */
    @Override
    public String getName() {
        return "Сохранить коллекцию";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() {
        List<exmp.commands.ArgDescriptor> args = new ArrayList<>();
        args.add(new exmp.commands.ArgDescriptor("fileName", String.class));
        return args;
    }

    /**
     * Возвращает описание команды save.
     *
     * @return описание команды save.
     */
    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    /**
     * Выполняет команду сохранения коллекции в файл
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public exmp.commands.CommandResult execute(exmp.App app, Object... args) {
        try {
            String savePath;
            if (args.length != 1) {
                savePath = "output.xml";
            } else {
                savePath = (String) args[0];
            }

            XmlMapper xmlMapper = new XmlMapper();
            try {
                xmlMapper.writeValue(new File(savePath), app.getProductRepository().findAll());
            } catch (IOException e) {
                return new exmp.commands.CommandResult(1, null, e.toString());
            }

            return new exmp.commands.CommandResult(0, "Коллекция сохранена", null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
