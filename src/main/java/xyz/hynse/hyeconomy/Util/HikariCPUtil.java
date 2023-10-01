package xyz.hynse.hyeconomy.Util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static xyz.hynse.hyeconomy.Hyeconomy.dataSource;

public class HikariCPUtil {
    public static void initializeDataSource(FileConfiguration config) {
        try {
            String driver = config.getString("database.driver");
            String host = config.getString("database.host");
            String port = config.getString("database.port");
            String databaseName = config.getString("database.databaseName");
            String username = config.getString("database.username");
            String password = config.getString("database.password");

            String url = driver + host + ":" + port + "/" + databaseName;
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(url);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);

            dataSource = new HikariDataSource(hikariConfig);
            getLogger().info("Database connection pool initialized successfully.");
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to initialize database connection pool: " + e.getMessage(), e);
        }
    }

    public static void createTablesIfNotExists() {
        createTableIfNotExists("player_balances",
                "player_uuid VARCHAR(36) PRIMARY KEY," +
                        "balance INT");

        createTableIfNotExists("transaction_history",
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "sender_uuid VARCHAR(36)," +
                        "recipient_uuid VARCHAR(36)," +
                        "amount INT," +
                        "timestamp TIMESTAMP");
    }

    public static void createTableIfNotExists(String tableName, String tableDefinition) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            if (!tableExists(conn, tableName)) {
                stmt.executeUpdate("CREATE TABLE " + tableName + " (" + tableDefinition + ")");
            }
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, "An error occurred while creating a table: " + e.getMessage(), e);
        }
    }

    public static boolean tableExists(Connection conn, String tableName) {
        try {
            ResultSet resultSet = conn.getMetaData().getTables(null, null, tableName, null);
            return resultSet.next();
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, "An error occurred while checking if a table exists: " + e.getMessage(), e);
            return false;
        }
    }

    public static void downloadMariaDBDriver() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            getLogger().info("MariaDB JDBC driver loaded.");
        } catch (ClassNotFoundException e) {
            getLogger().log(Level.SEVERE, "Failed to load MariaDB JDBC driver: " + e.getMessage(), e);
        }
    }
}
