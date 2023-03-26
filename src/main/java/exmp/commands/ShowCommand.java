package exmp.commands;

public class ShowCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Вывести все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        app.getProducts().forEach(System.out::println);
    }
}
