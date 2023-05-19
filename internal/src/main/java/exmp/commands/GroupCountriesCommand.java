import exmp.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Команда group_counting_by_countries для группировки элементов коллекции по их значению countries
 */
public class GroupCountriesCommand implements exmp.commands.Command {

    /**
     * Возвращает название команды group_counting_by_countries.
     *
     * @return название команды group_counting_by_countries.
     */
    @Override
    public String getName() {
        return "Группировать по country";
    }

    @Override
    public List<exmp.commands.ArgDescriptor> getArguments() { return new ArrayList<>(); }

    /**
     * Возвращает описание команды group_counting_by_countries.
     *
     * @return описание команды group_counting_by_countries.
     */
    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля country, вывести количество элементов в каждой группе";
    }

    /**
     * Выполняет команду группировки элементов коллекции
     *
     * @param app объект приложения, над которым выполняется команда.
     * @param args массив аргументов команды.
     */
    @Override
    public exmp.commands.CommandResult execute(App app, Object... args) {
        try {
            Map<String, Integer> countriesGroups = new HashMap<>();

            app.getProductRepository().findAll().forEach(product -> {
                exmp.enums.Country country = product.getOwner().getNationality();
                String countriesString = country.toString();

                countriesGroups.put(countriesString, countriesGroups.getOrDefault(countriesString, 0) + 1);
            });

            StringBuilder output = new StringBuilder("Группировка элементов коллекции по странам:\n");
            countriesGroups.forEach((countries, count) -> output.append("Страны: ")
                    .append(countries)
                    .append(" | Количество элементов: ")
                    .append(count)
                    .append("\n"));

            return new exmp.commands.CommandResult(0, output.toString(), null);
        } catch (Exception e) {
            return new exmp.commands.CommandResult(1, null, e.toString());
        }
    }
}
