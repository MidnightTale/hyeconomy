package xyz.hynse.hyeconomy.Process;

import java.util.UUID;

public record PlayerBalanceEntry(UUID playerUUID, int balance) {
}