package exmp.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);
    private static final HikariDataSource ds;

    static {
        Dotenv dotenv = Dotenv.load();

        String URL = "jdbc:postgresql://" + dotenv.get("HOST") + "/" + dotenv.get("TABLE");
        String USER = dotenv.get("LOGIN");
        String PASSWORD = dotenv.get("PASSWORD");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            logger.error("Ошибка подключения к базе данных: " + e);
            return null;
        }
    }

    public static void closeDataSource() {
        ds.close();
    }
}
