package xyz.hynse.hyeconomy.Command.Admin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import static xyz.hynse.hyeconomy.Process.PlayerRequest.logTransaction;

public class ResetCommand {
    public static void execute(@NotNull CommandSender sender, String[] args) {
        if (sender.hasPermission("hyeconomy.admin.reset")) {
            if (args.length < 1) {
                sender.sendMessage((Component) MessageUtil.getMessage("admin.reset.usage"));
                return;
            }

            String targetPlayerName = args[0];
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if (targetPlayer == null) {
                sender.sendMessage((Component) MessageUtil.getMessage("general.playerNotFound"));
                return;
            }

            PlayerRequest.setPlayerBalance(targetPlayer.getUniqueId(), 0);

            sender.sendMessage((Component) MessageUtil.getMessage("admin.reset.success", "%player%", targetPlayer.getName()));
            logTransaction(targetPlayer.getUniqueId(), null, 0);
            if (Hyeconomy.instance.adminFeedback) {
                targetPlayer.sendMessage((Component) MessageUtil.getMessage("admin.reset.targetReset"));
            }

        } else {
            sender.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
        }
    }
}
