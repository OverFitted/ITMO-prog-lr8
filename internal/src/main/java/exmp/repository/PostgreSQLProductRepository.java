package exmp.repository;

import exmp.database.DatabaseConnection;
import exmp.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgreSQLProductRepository implements exmp.repository.ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(exmp.repository.PostgreSQLProductRepository.class);

    @Override
    public List<Product> findAll() {
        String query = "SELECT * FROM product";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product product = extractProductFromResultSet(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(long id) {
        String query = "SELECT * FROM product WHERE id = ?";
        Product product = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = extractProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public Product save(Product product) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Сохранение связанных сущностей и получение их идентификаторов
            int coordinatesId = saveCoordinates(connection, product.getCoordinates());
            int ownerId = savePerson(connection, product.getOwner());

            // Запрос на сохранение Product
            String query = "INSERT INTO product (name, coordinates_id, price, part_number, manufacture_cost, unit_of_measure_id, owner_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setInt(2, coordinatesId);
                preparedStatement.setDouble(3, product.getPrice());
                preparedStatement.setString(4, product.getPartNumber());
                preparedStatement.setFloat(5, product.getManufactureCost());
                preparedStatement.setInt(6, product.getUnitOfMeasure().ordinal() + 1);
                preparedStatement.setInt(7, ownerId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    long productId = resultSet.getLong(1);
                    product.setId(productId);
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new RuntimeException("Ошибка при сохранении продукта.");
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Ошибка при сохранении продукта: " + e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подключении к базе данных: " + e.getMessage());
        }

        return product;
    }

    private int saveCoordinates(Connection connection, exmp.models.Coordinates coordinates) {
        String query = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, coordinates.getX());
            preparedStatement.setFloat(2, coordinates.getY());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при сохранении координат.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении координат: " + e.getMessage());
        }
    }

    private int savePerson(Connection connection, exmp.models.Person person) {
        int locationId = saveLocation(connection, person.getLocation());

        String query = "INSERT INTO person (name, height, eye_color_id, hair_color_id, nationality_id, location_id) "
                + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setFloat(2, person.getHeight());
            preparedStatement.setInt(3, person.getEyeColor().ordinal() + 1);
            preparedStatement.setInt(4, person.getHairColor().ordinal() + 1);
            preparedStatement.setInt(5, person.getNationality().ordinal() + 1);
            preparedStatement.setInt(6, locationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при сохранении человека.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении человека: " + e.getMessage());
        }
    }

    private int saveLocation(Connection connection, exmp.models.Location location) {
        String query = "INSERT INTO location (x, y, z, name) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setFloat(1, location.getX());
            preparedStatement.setDouble(2, location.getY());
            preparedStatement.setDouble(3, location.getZ());
            preparedStatement.setString(4, location.getName());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при сохранении локации.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении локации: " + e.getMessage());
        }
    }

    @Override
    public void delete(Product product) {
        String query = "DELETE FROM product WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, product.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteById(long id) {
        String query = "DELETE FROM product WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void loadData(exmp.App app) {
        List<Product> loadedProducts = findAll();
        products.addAll(loadedProducts);
    }

    private Product extractProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                extractCoordinatesFromResultSet(resultSet),
                resultSet.getInt("price"),
                resultSet.getString("part_number"),
                resultSet.getFloat("manufacture_cost"),
                extractUnitOfMeasureFromResultSet(resultSet),
                extractPersonFromResultSet(resultSet)
        );
    }

    private exmp.models.Coordinates extractCoordinatesFromResultSet(ResultSet resultSet) throws SQLException {
        return new exmp.models.Coordinates(
                resultSet.getLong("coordinates_id"),
                resultSet.getDouble("x"),
                resultSet.getFloat("y")
        );
    }

    private exmp.enums.UnitOfMeasure extractUnitOfMeasureFromResultSet(ResultSet resultSet) throws SQLException {
        return exmp.enums.UnitOfMeasure.valueOf(resultSet.getString("unit_of_measure_name"));
    }

    private exmp.models.Person extractPersonFromResultSet(ResultSet resultSet) throws SQLException {
        return new exmp.models.Person(
                resultSet.getLong("owner_id"),
                resultSet.getString("person_name"),
                resultSet.getLong("height"),
                extractColorFromResultSet(resultSet, "eye_color_name"),
                extractColorFromResultSet(resultSet, "hair_color_name"),
                extractCountryFromResultSet(resultSet),
                extractLocationFromResultSet(resultSet)
        );
    }

    private exmp.enums.Color extractColorFromResultSet(ResultSet resultSet, String nameColumn) throws SQLException {
        return exmp.enums.Color.valueOf(resultSet.getString(nameColumn));
    }

    private exmp.enums.Country extractCountryFromResultSet(ResultSet resultSet) throws SQLException {
        return exmp.enums.Country.valueOf(resultSet.getString("country_name"));
    }

    private exmp.models.Location extractLocationFromResultSet(ResultSet resultSet) throws SQLException {
        return new exmp.models.Location(
                resultSet.getLong("location_id"),
                resultSet.getInt("x"),
                resultSet.getLong("y"),
                resultSet.getLong("z"),
                resultSet.getString("location_name")
        );
    }
}
