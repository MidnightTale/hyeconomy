package xyz.hynse.hyeconomy.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class countdiamondinventory {
    private int countDiamondsInInventory(Player player) {
        int diamondCount = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.DIAMOND) {
                diamondCount += item.getAmount();
            }
        }
        return diamondCount;
    }
}
