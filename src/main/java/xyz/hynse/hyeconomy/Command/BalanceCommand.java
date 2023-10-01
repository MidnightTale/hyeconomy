package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.UUID;

public class BalanceCommand {
    public static void execute(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("hyeconomy.balance")) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    UUID playerUUID = player.getUniqueId();
                    int balance = PlayerRequest.getPlayerBalance(playerUUID);
                    sender.sendMessage((Component) MessageUtil.getMessage("balance.display", "%player%", player.getName(), "%balance%", String.valueOf(balance)));
                } else {
                    sender.sendMessage((Component) MessageUtil.getMessage("balance.consoleUsage"));
                }
            } else {
                sender.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
            }
        } else if (args.length == 1) {
            if (!sender.hasPermission("hyeconomy.balance.other")) {
                sender.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
                return;
            }
                String targetPlayerName = args[0];
                UUID targetPlayerUUID = PlayerRequest.getPlayerUUIDByName(targetPlayerName);

                if (targetPlayerUUID == null) {
                    sender.sendMessage((Component) MessageUtil.getMessage("general.playerNotFound"));
                    return;
                }

                int balance = PlayerRequest.getPlayerBalance(targetPlayerUUID);
                sender.sendMessage((Component) MessageUtil.getMessage("balance.display", "%player%", targetPlayerName, "%balance%", String.valueOf(balance)));
            } else {
                sender.sendMessage((Component) MessageUtil.getMessage("balance.usage"));
            }
    }
}
