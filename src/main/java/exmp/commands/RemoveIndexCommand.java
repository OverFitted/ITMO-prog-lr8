package exmp.commands;

public class RemoveIndexCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "remove_at";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его порядковому номеру";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        if (args.length == 0) {
            System.out.println("Необходимо указать порядковый номер элемента для удаления");
            return;
        }

        try {
            int index = Integer.parseInt(args[0]);
            if (index < 1 || index > app.getProducts().size()) {
                System.out.println("Некорректный индекс элемента для удаления");
                return;
            }
            app.getProducts().remove(index - 1);
            System.out.println("Элемент с порядковым номером " + index + " удален из коллекции");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат порядкового номера элемента для удаления");
        }
    }
}
