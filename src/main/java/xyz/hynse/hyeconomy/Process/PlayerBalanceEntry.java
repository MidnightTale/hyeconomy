package xyz.hynse.hyeconomy.Process;

import java.util.UUID;

public class PlayerBalanceEntry {
    private final UUID playerUUID;
    private final int balance;

    public PlayerBalanceEntry(UUID playerUUID, int balance) {
        this.playerUUID = playerUUID;
        this.balance = balance;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getBalance() {
        return balance;
    }
}