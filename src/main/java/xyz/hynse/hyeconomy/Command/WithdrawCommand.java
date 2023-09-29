package xyz.hynse.hyeconomy.Command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerRequest;
import xyz.hynse.hyeconomy.Util.MessageUtil;

import java.util.HashMap;

import static org.bukkit.Bukkit.getLogger;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.getPlayerBalance;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.setPlayerBalance;
import static xyz.hynse.hyeconomy.Util.InventoryUtil.countDiamondsInInventory;
import static xyz.hynse.hyeconomy.Util.InventoryUtil.hasInventorySpace;

public class WithdrawCommand {
    public static void execute(Player player, String[] args, MiniMessage mm) {
        if (args.length != 1) {
            player.sendMessage((Component) MessageUtil.getMessage("withdrawUsage"));
            return;
        }

        try {
            int amount = Integer.parseInt(args[0]);
            if (amount <= 0) {
                player.sendMessage((Component) MessageUtil.getMessage("withdrawAmountPositive"));
                return;
            }

            int currentBalance = getPlayerBalance(player.getUniqueId());
            if (currentBalance < amount) {
                player.sendMessage((Component) MessageUtil.getMessage("withdrawNotEnoughBalance"));
                return;
            }

            // Check if the player's inventory is full
            if (!hasInventorySpace(player, Material.DIAMOND, amount)) {
                player.sendMessage((Component) MessageUtil.getMessage("withdrawNotEnoughSpace", "%amount%", String.valueOf(amount)));
                return;
            }

            int newBalance = currentBalance - amount;
            setPlayerBalance(player.getUniqueId(), newBalance);

            ItemStack diamondStack = new ItemStack(Material.DIAMOND, amount);

            // Attempt to add diamonds to the player's inventory
            HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(diamondStack);

            if (!leftOver.isEmpty()) {
                // Some items couldn't be added due to a full inventory
                // Calculate the total amount of diamonds that couldn't be added
                int diamondsNotAdded = leftOver.values().stream().mapToInt(ItemStack::getAmount).sum();

                // Add the diamonds that couldn't be added back to the player's balance
                setPlayerBalance(player.getUniqueId(), newBalance + diamondsNotAdded);

                int diamondback = amount - diamondsNotAdded;
                player.sendMessage((Component) MessageUtil.getMessage("withdrawNotEnoughSpacePartial", "%amount%", String.valueOf(amount), "%diamonds_not_added%", String.valueOf(diamondsNotAdded)));
                return;
            }

            if (Hyeconomy.instance.debugMode) {
                getLogger().info("[DEBUG] Player " + player.getName() + " withdrew " + amount + " diamonds. New balance: " + newBalance);
            }

            player.sendMessage((Component) MessageUtil.getMessage("withdrawSuccess", "%amount%", String.valueOf(amount), "%new_balance%", String.valueOf(newBalance)));
        } catch (NumberFormatException e) {
            player.sendMessage(mm.deserialize("Invalid amount."));
        }
    }
}
