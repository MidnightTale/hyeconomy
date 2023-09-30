package xyz.hynse.hyeconomy.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
     public static int countDiamondsInInventory(Player player) {
             int diamondCount = 0;
             for (ItemStack item : player.getInventory().getContents()) {
                 if (item != null && item.getType() == Material.DIAMOND) {
                     diamondCount += item.getAmount();
                 }
             }
             return diamondCount;
    }
    public static boolean hasInventorySpace(Player player, Material material, int amount) {
        int spaceAvailable = 0;
        ItemStack[] contents = player.getInventory().getContents();

        for (ItemStack item : contents) {
            if (item == null) {
                spaceAvailable += material.getMaxStackSize();
            } else if (item.getType() == material) {
                spaceAvailable += (material.getMaxStackSize() - item.getAmount());
            }
        }

        return spaceAvailable >= amount;
    }

}
