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
import static org.bukkit.Bukkit.getServer;

public class HikariCPUtil {
    public static HikariDataSource dataSource;
    public static void initializeDataSource(FileConfiguration config) {
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
        getServer().getLogger().info("--------- initializeDataSource ---------");
        getServer().getLogger().info("JdbcUrl [" + url + "]");
        getServer().getLogger().info("Username [" + username + "]");
        getServer().getLogger().info("Password [REDACTED]");
        getServer().getLogger().info("dataSource [" + dataSource + "]");
        getServer().getLogger().info("----------------------------------------");
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
