package xyz.hynse.hyeconomy.Command.Admin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

public class TakeCommand {
    public static void execute(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("hyeconomy.admin.take")) {
            if (args.length < 2) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.take.usage"));
                return;
            }

            String targetPlayerName = args[0];
            int amount;

            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.take.invalidAmount"));
                return;
            }

            if (amount < 0) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.take.invalidAmount"));
                return;
            }

            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer == null) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.take.playerNotFound"));
                return;
            }

            int currentBalance = PlayerRequest.getPlayerBalance(targetPlayer.getUniqueId());

            if (currentBalance < amount) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.take.insufficientBalance"));
                return;
            }

            int newBalance = currentBalance - amount;
            PlayerRequest.setPlayerBalance(targetPlayer.getUniqueId(), newBalance);

            sender.sendMessage((Component) MessageUtil.getMessage("admin.take.success", "%player%", targetPlayer.getName(), "%amount%", String.valueOf(amount)));
            targetPlayer.sendMessage((Component) MessageUtil.getMessage("admin.take.targetTaken", "%amount%", String.valueOf(amount)));
        } else {
            sender.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
        }
    }
}
