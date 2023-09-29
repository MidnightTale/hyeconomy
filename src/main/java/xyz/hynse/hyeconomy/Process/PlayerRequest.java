package xyz.hynse.hyeconomy.Process;

import xyz.hynse.hyeconomy.Util.HikariCPUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
