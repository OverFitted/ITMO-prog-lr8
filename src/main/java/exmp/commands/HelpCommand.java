package exmp.commands;

import exmp.commands.Command;

import java.util.Map;
import java.util.function.Consumer;

public class HelpCommand implements Command {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        System.out.println("Список доступных команд:");
        for (Map.Entry<String, Command> entry : app.getCommandHandler().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getDescription());
        }
    }
}