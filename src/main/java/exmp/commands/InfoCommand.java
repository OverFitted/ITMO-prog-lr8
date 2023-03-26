package exmp.commands;

public class InfoCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
    @Override
    public void execute(exmp.App app, String[] args) {
        System.out.println("Тип коллекции: " + app.getProducts().getClass().getName());
        System.out.println("Дата инициализации: " + app.getInitializationDate());
        System.out.println("Количество элементов: " + app.getProducts().size());
    }
}