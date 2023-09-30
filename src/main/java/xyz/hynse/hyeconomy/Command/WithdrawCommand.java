package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.Scheduler;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.HashMap;

import static org.bukkit.Bukkit.getLogger;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.getPlayerBalance;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.setPlayerBalance;
import static xyz.hynse.hyeconomy.Util.InventoryUtil.hasInventorySpace;

public class WithdrawCommand {
    public static void execute(Player player, String[] args) {
            if (!player.hasPermission("hyeconomy.withdraw")) {
                player.sendMessage((Component) MessageUtil.getMessage("general.noPermission"));
                return;
            }

            if (args.length != 1) {
                player.sendMessage((Component) MessageUtil.getMessage("withdraw.Usage"));
                return;
            }

            try {
                int amount = Integer.parseInt(args[0]);
                if (amount <= 0) {
                    player.sendMessage((Component) MessageUtil.getMessage("withdraw.AmountPositive"));
                    return;
                }

                int currentBalance = getPlayerBalance(player.getUniqueId());
                if (currentBalance < amount) {
                    player.sendMessage((Component) MessageUtil.getMessage("withdraw.NotEnoughBalance"));
                    return;
                }

                if (!hasInventorySpace(player, Material.DIAMOND, amount)) {
                    player.sendMessage((Component) MessageUtil.getMessage("withdraw.NotEnoughSpace", "%amount%", String.valueOf(amount)));
                    return;
                }

                int newBalance = currentBalance - amount;
                setPlayerBalance(player.getUniqueId(), newBalance);
                ItemStack diamondStack = new ItemStack(Material.DIAMOND, amount);
                HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(diamondStack);

                if (!leftOver.isEmpty()) {
                    int diamondsNotAdded = leftOver.values().stream().mapToInt(ItemStack::getAmount).sum();
                    setPlayerBalance(player.getUniqueId(), newBalance + diamondsNotAdded);
                    int diamondback = amount - diamondsNotAdded;
                    player.sendMessage((Component) MessageUtil.getMessage("withdraw.NotEnoughSpacePartial", "%amount%", String.valueOf(amount), "%diamondback%", String.valueOf(diamondback)));
                    leftOver.clear();
                    return;
                }

                if (Hyeconomy.instance.debugMode) {
                    getLogger().info("[DEBUG] Player " + player.getName() + " withdrew " + amount + " diamonds. New balance: " + newBalance);
                }

                player.sendMessage((Component) MessageUtil.getMessage("withdraw.Success", "%amount%", String.valueOf(amount), "%new_balance%", String.valueOf(newBalance)));
            } catch (NumberFormatException e) {
                player.sendMessage((Component) MessageUtil.getMessage("general.invalidAmount"));
            }
    }
}
