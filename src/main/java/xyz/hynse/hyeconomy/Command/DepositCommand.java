package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.Scheduler;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import static org.bukkit.Bukkit.getLogger;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.getPlayerBalance;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.setPlayerBalance;
import static xyz.hynse.hyeconomy.Util.InventoryUtil.countDiamondsInInventory;

public class DepositCommand {
    public static void execute(Player player, String[] args) {
        Scheduler.runAsyncSchedulerNow(Hyeconomy.instance, task -> {
            if (!player.hasPermission("hyeconomy.deposit")) {
                player.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
                return;
            }

            if (args.length != 1) {
                player.sendMessage((Component) MessageUtil.getMessage("deposit.Usage"));
                return;
            }

            try {
                int amount = Integer.parseInt(args[0]);
                if (amount <= 0) {
                    player.sendMessage((Component) MessageUtil.getMessage("deposit.AmountPositive"));
                    return;
                }

                int currentBalance = getPlayerBalance(player.getUniqueId());
                int diamondsInInventory = countDiamondsInInventory(player);

                if (diamondsInInventory < amount) {
                    player.sendMessage((Component) MessageUtil.getMessage("deposit.NotEnoughDiamonds"));
                    return;
                }

                int newBalance = currentBalance + amount;
                setPlayerBalance(player.getUniqueId(), newBalance);

                ItemStack diamondStack = new ItemStack(Material.DIAMOND, amount);
                player.getInventory().removeItem(diamondStack); // Remove diamonds from inventory
                if (Hyeconomy.instance.debugMode) {
                    getLogger().info("[DEBUG] Player " + player.getName() + " deposited " + amount + " diamonds. New balance: " + newBalance);
                }

                player.sendMessage((Component) MessageUtil.getMessage("deposit.Success", "%amount%", String.valueOf(amount), "%new_balance%", String.valueOf(newBalance)));
            } catch (NumberFormatException e) {
                player.sendMessage((Component) MessageUtil.getMessage("general.invalidAmount"));
            }
        });
    }
}
