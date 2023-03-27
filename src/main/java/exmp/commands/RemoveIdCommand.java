package exmp.commands;

import exmp.models.Product;

public class RemoveIdCommand implements exmp.commands.Command {
    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(exmp.App app, String[] args) {
        if (args.length != 1) {
            System.out.println("Команда должна принимать только один аргумент - id элемента");
            return;
        }

        try {
            Long id = Long.parseLong(args[0]);
            Product productToRemove = null;

            // Find the product to remove based on its ID
            for (Product product : app.getProducts()) {
                if (product.getId().equals(id)) {
                    productToRemove = product;
                    break;
                }
            }

            if (productToRemove == null) {
                System.out.println("Продукт с указанным id не найден");
                return;
            }

            // Remove the product from the collection
            app.getProducts().remove(productToRemove);
            System.out.println("Продукт с id " + id + " удален из коллекции");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный аргумент - id должен быть числом");
        }
    }
}
