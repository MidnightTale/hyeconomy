package xyz.hynse.hyeconomy.API;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerBalanceEntry;
import xyz.hynse.hyeconomy.Process.PlayerRequest;

import java.util.List;

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
            int balance = PlayerRequest.getPlayerBalanceByName(playerName);
            return String.valueOf(balance);
        } else if (identifier.startsWith("balance_top_")) {
            String[] parts = identifier.split("_");
            if (parts.length == 3) {
                int rank = Integer.parseInt(parts[2]);
                if (rank >= 1 && rank <= 10) {
                    List<PlayerBalanceEntry> topPlayers = PlayerRequest.getTopPlayers(rank);
                    if (rank <= topPlayers.size()) {
                        PlayerBalanceEntry topPlayer = topPlayers.get(rank - 1);
                        return String.valueOf(topPlayer.balance());
                    }
                }
            }
        }

        return null;
    }
}