package xyz.hynse.hyeconomy.Command.Admin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

public class GiveCommand {
    public static void execute(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("hyeconomy.admin.give")) {
            if (args.length < 2) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.give.usage"));
                return;
            }

            String targetPlayerName = args[0];
            int amount;

            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.give.invalidAmount"));
                return;
            }

            if (amount < 0) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.give.invalidAmount"));
                return;
            }

            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer == null) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.give.playerNotFound"));
                return;
            }

            int currentBalance = PlayerRequest.getPlayerBalance(targetPlayer.getUniqueId());
            int newBalance = currentBalance + amount;
            PlayerRequest.setPlayerBalance(targetPlayer.getUniqueId(), newBalance);

            sender.sendMessage((Component) MessageUtil.getMessage("admin.give.success", "%player%", targetPlayer.getName(), "%amount%", String.valueOf(amount)));
            targetPlayer.sendMessage((Component) MessageUtil.getMessage("admin.give.targetReceived", "%amount%", String.valueOf(amount)));
        } else {
            sender.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
        }
    }
}