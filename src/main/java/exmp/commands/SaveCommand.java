package exmp.commands;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class SaveCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        String savePath;
        if (args.length != 1) {
            System.out.println("Путь не предоставлен, коллекция будет сохранена в output.xml");
            savePath = "output.xml";
        } else {
            savePath = args[0];
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(savePath), app.getProducts());
            System.out.println("Коллекция сохранена в файл " + savePath);
        } catch (IOException e) {
            System.err.println("Не удалось сохранить коллекцию в файл: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
