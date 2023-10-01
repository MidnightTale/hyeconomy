package xyz.hynse.hyeconomy.API;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerBalanceEntry;
import xyz.hynse.hyeconomy.Process.PlayerRequest;

import java.util.List;
import java.util.UUID;

public class Placeholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return Hyeconomy.instance.getDescription().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return Hyeconomy.instance.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return Hyeconomy.instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("balance")) {
            int balance = PlayerRequest.getPlayerBalance(player.getUniqueId());
            return String.valueOf(balance);
        } else if (identifier.startsWith("balance_")) {
            String playerName = identifier.replace("balance_", "");
            Player targetPlayername = Bukkit.getPlayer(playerName);
            UUID targetplayeruuid = targetPlayername.getUniqueId();
            int balance = PlayerRequest.getPlayerBalance(targetplayeruuid);
            return String.valueOf(balance);
        }

        if (identifier.startsWith("balance_top_amount_") || identifier.startsWith("balance_top_username_")) {
            String[] parts = identifier.split("_");
            if (parts.length == 4) {
                int rank = Integer.parseInt(parts[3]);
                if (rank >= 1 && rank <= 10) {
                    int limit = 10;
                    List<PlayerBalanceEntry> topPlayers = PlayerRequest.getTopPlayers(limit);
                    if (rank <= topPlayers.size()) {
                        PlayerBalanceEntry topPlayer = topPlayers.get(rank - 1);

                        if (identifier.startsWith("balance_top_amount_")) {
                            return String.valueOf(topPlayer.balance());
                        } else if (identifier.startsWith("balance_top_username_")) {
                            UUID playerUUID = topPlayer.playerUUID();
                            Player playerName = Bukkit.getPlayer(playerUUID);

                            if (playerName != null) {
                                return playerName.getName();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
