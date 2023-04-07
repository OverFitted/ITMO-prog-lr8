package exmp.commands;

import exmp.models.Product;

/**
 * Команда remove_by_id для удаления продукта из колелкции по его id
 */
public class RemoveIdCommand implements exmp.commands.Command {
    /**
     * Возвращает название команды remove_by_id.
     *
     * @return название команды remove_by_id.
     */
    @Override
    public String getName() {
        return "remove_by_id";
    }

    /**
     * Возвращает описание команды remove_by_id.
     *
     * @return описание команды remove_by_id.
     */
    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }

    /**
     * Выполняет команду удаления элемента по заданному id
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
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
