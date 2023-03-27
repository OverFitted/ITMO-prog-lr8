package exmp.commands;

import exmp.models.Product;

public class RemovePartNumberCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "remove_all_by_part_number";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение поля partNumber которого эквивалентно заданному";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        if (args.length < 1) {
            System.out.println("Не задано значение поля partNumber");
            return;
        }

        String partNumber = args[0];

        int count = app.getProducts().removeIf((Product product) -> product.getPartNumber() != null && product.getPartNumber().equals(partNumber)) ? 1 : 0;

        if (count == 0) {
            System.out.println("Не найдено ни одного элемента с заданным значением поля partNumber");
        } else {
            System.out.println("Удалено элементов: " + count);
        }
    }
}
