package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.Scheduler;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import static org.bukkit.Bukkit.getLogger;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.getPlayerBalance;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.setPlayerBalance;

public class SendCommand {
    public static void execute(Player player, String[] args) {
        Scheduler.runAsyncSchedulerNow(Hyeconomy.instance, task -> {
            if (args.length != 2) {
                player.sendMessage((Component) MessageUtil.getMessage("send.Usage"));
                return;
            }

            String targetPlayerName = args[0];

            if (player.getName().equalsIgnoreCase(targetPlayerName)) {
                player.sendMessage((Component) MessageUtil.getMessage("send.SelfSend"));
                return;
            }

            Player targetPlayer = Bukkit.getPlayerExact(targetPlayerName);

            try {
                int amount = Integer.parseInt(args[1]);
                if (amount <= 0) {
                    player.sendMessage((Component) MessageUtil.getMessage("send.AmountPositive"));
                    return;
                }

                int senderBalance = getPlayerBalance(player.getUniqueId());
                if (senderBalance < amount) {
                    player.sendMessage((Component) MessageUtil.getMessage("send.NotEnoughDiamonds"));
                    return;
                }

                if (targetPlayer == null) {
                    targetPlayer = Bukkit.getOfflinePlayer(targetPlayerName).getPlayer();

                    if (targetPlayer == null) {
                        player.sendMessage((Component) MessageUtil.getMessage("send.PlayerNotFound"));
                        return;
                    }
                }

                int targetBalance = getPlayerBalance(targetPlayer.getUniqueId());

                setPlayerBalance(player.getUniqueId(), senderBalance - amount);
                setPlayerBalance(targetPlayer.getUniqueId(), targetBalance + amount);

                if (Hyeconomy.instance.debugMode) {
                    getLogger().info("[DEBUG] Player " + player.getName() + " sent " + amount + " diamonds to " + targetPlayer.getName());
                }

                player.sendMessage((Component) MessageUtil.getMessage("send.SuccessSender", "%amount%", String.valueOf(amount), "%target%", targetPlayer.getName()));
                targetPlayer.sendMessage((Component) MessageUtil.getMessage("send.SuccessReceiver", "%sender%", player.getName(), "%amount%", String.valueOf(amount)));
            } catch (NumberFormatException e) {
                player.sendMessage((Component) MessageUtil.getMessage("general.invalidAmount"));
            }
        });
    }
}
