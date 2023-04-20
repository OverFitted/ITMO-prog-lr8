package exmp.commands;

import java.io.Serializable;

public class CommandData implements Serializable {
    private String commandName;
    private String arguments;

    public CommandData(String commandName, String arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArguments() {
        return arguments;
    }
}
