package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.UUID;

public class BalanceCommand {
    public static void execute(@NotNull CommandSender sender, MiniMessage mm) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage((Component) MessageUtil.getMessage("playerOnly"));
            return;
        }

        UUID playerUUID = player.getUniqueId();
        int balance = PlayerRequest.getPlayerBalance(playerUUID);
        player.sendMessage((Component) MessageUtil.getMessage("balance", "%balance%", String.valueOf(balance)));
    }
}
