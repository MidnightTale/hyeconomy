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

    /**
     * Subtracts the specified amount from a player's balance.
     *
     * @param playerUUID The UUID of the player.
     * @param amount The amount to subtract from the player's balance.
     */
    static void subtractFromPlayerBalance(UUID playerUUID, int amount) {
        PlayerRequest.subtractFromPlayerBalance(playerUUID, amount);
    }

    /**
     * Adds the specified amount to a player's balance.
     *
     * @param playerUUID The UUID of the player.
     * @param amount The amount to add to the player's balance.
     */
    static void addToPlayerBalance(UUID playerUUID, int amount) {
        PlayerRequest.addToPlayerBalance(playerUUID, amount);
    }
}
