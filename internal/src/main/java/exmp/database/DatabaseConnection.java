package exmp.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final Logger logger = LogManager.getLogger(exmp.database.DatabaseConnection.class);

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Ошибка подключения к базе данных: " + e);
        }
        return connection;
    }
}
