package exmp.commands;

public interface Command {
    String getName();
    String getDescription();
    void execute(exmp.App app, String[] args);
}
