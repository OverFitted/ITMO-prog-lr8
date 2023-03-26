package exmp.commands;

import exmp.commands.Command;

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
        
    }
}