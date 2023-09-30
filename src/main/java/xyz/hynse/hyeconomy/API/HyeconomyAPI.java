package xyz.hynse.hyeconomy.API;

import xyz.hynse.hyeconomy.Process.PlayerRequest;

import java.util.UUID;

public interface HyeconomyAPI {
    static int getPlayerBalance(UUID playerUUID) {
        return PlayerRequest.getPlayerBalance(playerUUID);
    }

    static void setPlayerBalance(UUID playerUUID, int newBalance) {
        PlayerRequest.setPlayerBalance(playerUUID, newBalance);
    }
}
