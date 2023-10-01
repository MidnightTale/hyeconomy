package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.Objects;
import java.util.UUID;

public class BalanceCommand {
    public static void execute(@NotNull CommandSender sender, String[] args) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    UUID playerUUID = player.getUniqueId();
                    int balance = PlayerRequest.getPlayerBalance(playerUUID);
                    sender.sendMessage((Component) MessageUtil.getMessage("balance.display", "%player%", player.getName(), "%balance%", String.valueOf(balance)));
                } else {
                    sender.sendMessage((Component) MessageUtil.getMessage("balance.consoleUsage"));
                }
            } else if (args.length == 1) {
                String targetPlayerName = args[0];
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
                if (targetPlayer == null) {
                    sender.sendMessage((Component) MessageUtil.getMessage("balance.playerNotFound"));
                    return;
                }
                UUID targetPlayerUUID = Objects.requireNonNull(targetPlayer).getUniqueId();

                int balance = PlayerRequest.getPlayerBalance(targetPlayerUUID);
                sender.sendMessage((Component) MessageUtil.getMessage("balance.displayother", "%player%", targetPlayerName, "%balance%", String.valueOf(balance)));
            } else {
                sender.sendMessage((Component) MessageUtil.getMessage("balance.usage"));
            }
        }
}
