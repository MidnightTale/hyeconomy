package xyz.hynse.hyeconomy.Process;

import xyz.hynse.hyeconomy.Util.HikariCPUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerRequest {
    private static final Logger logger = Logger.getLogger(PlayerRequest.class.getName());

    public static int getPlayerBalance(UUID playerUUID) {
        return getPlayerData(playerUUID);
    }

    public static void setPlayerBalance(UUID playerUUID, int newBalance) {
        setPlayerData(playerUUID, newBalance);
    }

    private static int getPlayerData(UUID playerUUID) {
        int data = 0;

        try (Connection conn = HikariCPUtil.dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT balance FROM player_balances WHERE player_uuid = ?")) {

            statement.setString(1, playerUUID.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    data = resultSet.getInt("balance");
                }

            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occurred while fetching player balance for UUID: " + playerUUID, e);
        }

        return data;
    }

    private static void setPlayerData(UUID playerUUID, int newData) {
        try (Connection conn = HikariCPUtil.dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("REPLACE INTO player_balances (player_uuid, balance) VALUES (?, ?)")) {

            statement.setString(1, playerUUID.toString());
            statement.setInt(2, newData);

            statement.executeUpdate();
        } catch (SQLException e) {
            // Log the exception with a specific logging level (e.g., Level.SEVERE)
            logger.log(Level.SEVERE, "An error occurred while setting player balance for UUID: " + playerUUID, e);
        }
    }
    public static List<PlayerBalanceEntry> getTopPlayers(int limit) {
        List<PlayerBalanceEntry> topPlayers = new ArrayList<>();

        try (Connection conn = HikariCPUtil.dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT player_uuid, balance FROM player_balances ORDER BY balance DESC LIMIT ?")) {

            statement.setInt(1, limit);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UUID playerUUID = UUID.fromString(resultSet.getString("player_uuid"));
                    int balance = resultSet.getInt("balance");
                    topPlayers.add(new PlayerBalanceEntry(playerUUID, balance));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occurred while fetching top players: " + e.getMessage(), e);
        }

        return topPlayers;
    }
    public static int getPlayerBalanceByName(String playerName) {
        int balance = 0;

        try (Connection conn = HikariCPUtil.dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT balance FROM player_balances WHERE player_name = ?")) {

            statement.setString(1, playerName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    balance = resultSet.getInt("balance");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occurred while fetching player balance for name: " + playerName, e);
        }

        return balance;
    }

}
