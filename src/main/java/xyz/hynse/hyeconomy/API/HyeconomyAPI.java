package xyz.hynse.hyeconomy.API;

import xyz.hynse.hyeconomy.Process.PlayerRequest;

import java.util.UUID;

/**
 * The HyeconomyAPI interface provides methods to interact with the Hyeconomy plugin
 * and manage player balances.
 */
public interface HyeconomyAPI {

    /**
     * Retrieves the balance of a player identified by their UUID.
     *
     * @param playerUUID The UUID of the player whose balance is to be retrieved.
     * @return The current balance of the player.
     */
    static int getPlayerBalance(UUID playerUUID) {
        return PlayerRequest.getPlayerBalance(playerUUID);
    }

    /**
     * Sets the balance of a player identified by their UUID to a new value.
     *
     * @param playerUUID The UUID of the player whose balance is to be updated.
     * @param newBalance The new balance to set for the player.
     */
    static void setPlayerBalance(UUID playerUUID, int newBalance) {
        PlayerRequest.setPlayerBalance(playerUUID, newBalance);
    }
}
