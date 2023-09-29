package xyz.hynse.hyeconomy.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.hynse.hyeconomy.Hyeconomy;
import xyz.hynse.hyeconomy.Process.PlayerRequest;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.getPlayerBalance;
import static xyz.hynse.hyeconomy.Process.PlayerRequest.setPlayerBalance;
import static xyz.hynse.hyeconomy.Util.InventoryUtil.countDiamondsInInventory;
import static xyz.hynse.hyeconomy.Util.InventoryUtil.hasInventorySpace;

public class CommandUtil implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command."));
            return true;
        }

        UUID playerUUID = player.getUniqueId();
        MiniMessage mm = MiniMessage.builder().build();

        switch (cmd.getName().toLowerCase()) {
            case "balance" -> {
                int balance = PlayerRequest.getPlayerBalance(playerUUID);
                player.sendMessage(mm.deserialize("Your balance: <#00FF00>" + balance + " <#FF0000>diamonds."));
            }
            case "deposit" -> handleDepositCommand(player, args, mm);
            case "send" -> handleSendCommand(player, args, mm);
            case "top" -> {
                // Implement top command logic here
            }
            case "withdraw" -> handleWithdrawCommand(player, args, mm);
            default -> player.sendMessage(mm.deserialize("Unknown command: <#FF0000>" + cmd.getName()));
        }
        return true;
    }

    public static void handleDepositCommand(Player player, String[] args, MiniMessage mm) {
        if (args.length != 1) {
            player.sendMessage(mm.deserialize("Usage: /deposit <#FF0000>[amount]"));
            return;
        }

        try {
            int amount = Integer.parseInt(args[0]);
            if (amount <= 0) {
                player.sendMessage(mm.deserialize("Amount must be positive."));
                return;
            }

            int currentBalance = getPlayerBalance(player.getUniqueId());
            int diamondsInInventory = countDiamondsInInventory(player);

            if (diamondsInInventory < amount) {
                player.sendMessage(mm.deserialize("You don't have enough diamonds in your inventory."));
                return;
            }

            int newBalance = currentBalance + amount;
            setPlayerBalance(player.getUniqueId(), newBalance);

            ItemStack diamondStack = new ItemStack(Material.DIAMOND, amount);
            player.getInventory().removeItem(diamondStack); // Remove diamonds from inventory
            if (Hyeconomy.instance.debugMode) {
                getLogger().info("[DEBUG] Player " + player.getName() + " deposited " + amount + " diamonds. New balance: " + newBalance);
            }

            player.sendMessage(mm.deserialize("Deposited <#00FF00>" + amount + " <#FF0000>diamonds. Your balance: <#00FF00>" + newBalance + " <#FF0000>diamonds."));
        } catch (NumberFormatException e) {
            player.sendMessage(mm.deserialize("Invalid amount."));
        }
    }

    public static void handleSendCommand(Player player, String[] args, MiniMessage mm) {
        if (args.length != 2) {
            player.sendMessage(mm.deserialize("Usage: /send <#FF0000>[player] [amount]"));
            return;
        }

        Player targetPlayer = getServer().getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(mm.deserialize("Player not found."));
            return;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount <= 0) {
                player.sendMessage(mm.deserialize("Amount must be positive."));
                return;
            }

            int senderBalance = getPlayerBalance(player.getUniqueId());
            if (senderBalance < amount) {
                player.sendMessage(mm.deserialize("You don't have enough diamonds."));
                return;
            }

            int targetBalance = getPlayerBalance(targetPlayer.getUniqueId());
            setPlayerBalance(player.getUniqueId(), senderBalance - amount);
            setPlayerBalance(targetPlayer.getUniqueId(), targetBalance + amount);

            if (Hyeconomy.instance.debugMode) {
                getLogger().info("[DEBUG] Player " + player.getName() + " sent " + amount + " diamonds to " + targetPlayer.getName());
            }

            player.sendMessage(mm.deserialize("Sent <#00FF00>" + amount + " <#FF0000>diamonds to " + targetPlayer.getName() + "."));
            targetPlayer.sendMessage(mm.deserialize(player.getName() + " sent you <#00FF00>" + amount + " <#FF0000>diamonds."));
        } catch (NumberFormatException e) {
            player.sendMessage(mm.deserialize("Invalid amount."));
        }
    }

    public static void handleWithdrawCommand(Player player, String[] args, MiniMessage mm) {
        if (args.length != 1) {
            player.sendMessage(mm.deserialize("Usage: /withdraw <#FF0000>[amount]"));
            return;
        }

        try {
            int amount = Integer.parseInt(args[0]);
            if (amount <= 0) {
                player.sendMessage(mm.deserialize("Amount must be positive."));
                return;
            }

            int currentBalance = getPlayerBalance(player.getUniqueId());
            if (currentBalance < amount) {
                player.sendMessage(mm.deserialize("You don't have enough diamonds in your balance."));
                return;
            }

            // Check if the player's inventory is full
            if (!hasInventorySpace(player, Material.DIAMOND, amount)) {
                player.sendMessage(mm.deserialize("Not enough inventory space to withdraw <#FF0000>" + amount + " <#FF0000>diamonds."));
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
                player.sendMessage(mm.deserialize("Not enough inventory space to withdraw <#FF0000>" + diamondback + " <#FF0000>diamonds."));
                return;
            }

            if (Hyeconomy.instance.debugMode) {
                getLogger().info("[DEBUG] Player " + player.getName() + " withdrew " + amount + " diamonds. New balance: " + newBalance);
            }

            player.sendMessage(mm.deserialize("Withdrew <#00FF00>" + amount + " <#FF0000>diamonds. Your balance: <#00FF00>" + newBalance + " <#FF0000>diamonds."));
        } catch (NumberFormatException e) {
            player.sendMessage(mm.deserialize("Invalid amount."));
        }
    }
}
