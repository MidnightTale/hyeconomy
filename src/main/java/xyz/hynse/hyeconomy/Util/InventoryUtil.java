package xyz.hynse.hyeconomy.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getServer;

public class InventoryUtil {
     public static int countDiamondsInInventory(Player player) {
        int diamondCount = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.DIAMOND) {
                diamondCount += item.getAmount();
                getServer().getLogger().info("countDiamondsInInventory | Diamond" + diamondCount);
            }
        }
        return diamondCount;

    }
    public static boolean hasInventorySpace(Player player, Material material, int amount) {
        int spaceAvailable = 0;
        ItemStack[] contents = player.getInventory().getContents();

        for (ItemStack item : contents) {
            if (item == null) {
                // Empty slot, so we can add as much as needed
                spaceAvailable += material.getMaxStackSize();
                getServer().getLogger().info("hasInventorySpace | null" + spaceAvailable);
            } else if (item.getType() == material) {
                // Same material, calculate available space
                spaceAvailable += (material.getMaxStackSize() - item.getAmount());
                getServer().getLogger().info("hasInventorySpace | material" + spaceAvailable);
            }
        }

        return spaceAvailable >= amount;
    }

}
