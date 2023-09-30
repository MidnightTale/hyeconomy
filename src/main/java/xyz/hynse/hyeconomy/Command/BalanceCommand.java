package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Process.Scheduler;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.UUID;

public class BalanceCommand {
    public static void execute(@NotNull CommandSender sender) {
        if (sender instanceof Player player && !player.hasPermission("hyecomoney.balance")) {
            player.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
            return;
        }

            if (!(sender instanceof Player player)) {
                sender.sendMessage((Component) MessageUtil.getMessage("general.playerOnly"));
                return;
            }

            UUID playerUUID = player.getUniqueId();
            int balance = PlayerRequest.getPlayerBalance(playerUUID);
            player.sendMessage((Component) MessageUtil.getMessage("general.balance", "%balance%", String.valueOf(balance)));
    }
}