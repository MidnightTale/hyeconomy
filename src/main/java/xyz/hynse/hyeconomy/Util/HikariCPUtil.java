package xyz.hynse.hyeconomy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class HikariCP {
    public static HikariDataSource dataSource;

    public static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/test_hyeconomy");
        config.setUsername("mid");
        config.setPassword("2545");

        dataSource = new HikariDataSource(config);
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
                getLogger().info("[DEBUG] Created " + tableName + " table.");
            }
        } catch (SQLException e) {
            // Handle the exception and log it
            getLogger().log(Level.SEVERE, "An error occurred while creating a table: " + e.getMessage(), e);
        }
    }

    public static boolean tableExists(Connection conn, String tableName) {
        try {
            ResultSet resultSet = conn.getMetaData().getTables(null, null, tableName, null);
            return resultSet.next();
        } catch (SQLException e) {
            // Handle the exception and log it
            getLogger().log(Level.SEVERE, "An error occurred while checking if a table exists: " + e.getMessage(), e);
            return false;
        }
    }

    public static void downloadMariaDBDriver() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            getLogger().info("MariaDB JDBC driver loaded.");
        } catch (ClassNotFoundException e) {
            // Handle the exception and log it
            getLogger().log(Level.SEVERE, "Failed to load MariaDB JDBC driver: " + e.getMessage(), e);
        }
    }
}
