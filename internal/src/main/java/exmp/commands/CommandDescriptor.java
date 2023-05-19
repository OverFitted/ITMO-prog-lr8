package exmp.commands;

import java.io.Serializable;
import java.util.List;

public class CommandDescriptor implements Serializable {
    String name;
    String command;

    List<exmp.commands.ArgDescriptor> argDescriptors;

    public CommandDescriptor(String name, String command, List<exmp.commands.ArgDescriptor> argDescriptors){
        setName(name);
        setCommand(command);
        setArgDescriptors(argDescriptors);
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<exmp.commands.ArgDescriptor> getArgDescriptors() {
        return argDescriptors;
    }

    public void setArgDescriptors(List<exmp.commands.ArgDescriptor> argDescriptors) {
        this.argDescriptors = argDescriptors;
    }
}
