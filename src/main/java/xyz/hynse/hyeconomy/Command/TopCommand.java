package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerBalanceEntry;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Process.Scheduler;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.List;

public class TopCommand {
    public static void execute(Player player) {
        if (!player.hasPermission("hyeconomy.top")) {
            player.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
            return;
        }
                    int limit = 10;
                    List<PlayerBalanceEntry> topPlayers = PlayerRequest.getTopPlayers(limit);
                    player.sendMessage((Component) MessageUtil.getMessage("top.header"));

                    int maxPlayersToShow = Math.min(limit, topPlayers.size());

                    for (int i = 0; i < maxPlayersToShow; i++) {
                        PlayerBalanceEntry entry = topPlayers.get(i);
                        String playerName = Bukkit.getOfflinePlayer(entry.playerUUID()).getName();
                        player.sendMessage((Component) MessageUtil.getMessage("top.format", "%position%", String.valueOf(i + 1), "%player%", playerName, "%balance%", String.valueOf(entry.balance())));
                    }
        player.sendMessage((Component) MessageUtil.getMessage("top.footer"));
    }
}
