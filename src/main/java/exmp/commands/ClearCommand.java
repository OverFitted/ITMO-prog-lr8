package exmp.commands;

public class ClearCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        app.getProducts().clear();
        System.out.println("Коллекция очищена");
    }
}
