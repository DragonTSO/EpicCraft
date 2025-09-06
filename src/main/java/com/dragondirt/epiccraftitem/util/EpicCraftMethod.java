package com.dragondirt.epiccraftitem.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Utility methods for EpicCraftItem
 */
public class EpicCraftMethod {

    /**
     * Format color codes in string
     */
    public static String formatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Open inventory for player
     */
    public static void openInventory(Player player, Inventory inventory) {
        player.openInventory(inventory);
    }

    /**
     * Check if string is numeric
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Get random double between min and max
     */
    public static double randomDouble(double min, double max) {
        return min + (Math.random() * (max - min));
    }

    /**
     * Get random integer between min and max
     */
    public static int randomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
}
