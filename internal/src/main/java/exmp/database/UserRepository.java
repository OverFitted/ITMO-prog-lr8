package exmp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public boolean registerUser(String username, String password) {
        try (Connection connection = exmp.database.DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при регистрации пользователя: " + e.getMessage());
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) {
        try (Connection connection = exmp.database.DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Ошибка при аутентификации пользователя: " + e.getMessage());
            return false;
        }
    }
}
