package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.getPlayerBalance;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.setPlayerBalance;

public class SendCommand {
    public static void execute(Player player, String[] args, MiniMessage mm) {
        if (args.length != 2) {
            player.sendMessage((Component) MessageUtil.getMessage("sendUsage"));
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage((Component) MessageUtil.getMessage("sendPlayerNotFound"));
            return;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount <= 0) {
                player.sendMessage((Component) MessageUtil.getMessage("sendAmountPositive"));
                return;
            }

            int senderBalance = getPlayerBalance(player.getUniqueId());
            if (senderBalance < amount) {
                player.sendMessage((Component) MessageUtil.getMessage("sendNotEnoughDiamonds"));
                return;
            }

            int targetBalance = getPlayerBalance(targetPlayer.getUniqueId());
            setPlayerBalance(player.getUniqueId(), senderBalance - amount);
            setPlayerBalance(targetPlayer.getUniqueId(), targetBalance + amount);

            if (Hyeconomy.instance.debugMode) {
                getLogger().info("[DEBUG] Player " + player.getName() + " sent " + amount + " diamonds to " + targetPlayer.getName());
            }

            player.sendMessage((Component) MessageUtil.getMessage("sendSuccessSender", "%amount%", String.valueOf(amount), "%target%", targetPlayer.getName()));
            targetPlayer.sendMessage((Component) MessageUtil.getMessage("sendSuccessReceiver", "%sender%", player.getName(), "%amount%", String.valueOf(amount)));
        } catch (NumberFormatException e) {
            player.sendMessage(mm.deserialize("Invalid amount."));
        }
    }
}
