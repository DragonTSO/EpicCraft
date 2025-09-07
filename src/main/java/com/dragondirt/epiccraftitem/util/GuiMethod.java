package com.dragondirt.epiccraftitem.util;

import com.dragondirt.epiccraftitem.data.gui.GuiMainMenu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * GUI utility methods
 */
public class GuiMethod {

    /**
     * Interface for GUI method keys
     */
    public interface GuiMethodKey {
        String getName();
    }

    /**
     * Load editing mode for GUI
     */
    public static void loadEditing(GuiMainMenu guiMainMenu) {
        // Implementation for loading editing mode
        // This would be implemented based on your editing functionality
    }

    /**
     * Set item to inventory with proper formatting
     */
    public static void setItemToInventory(org.bukkit.inventory.Inventory inventory, int slot, ItemStack item) {
        if (slot >= 0 && slot < inventory.getSize()) {
            inventory.setItem(slot, item);
        }
    }

    /**
     * Create item from configuration
     */
    public static ItemStack createItemFromConfig(FileConfiguration config, String path) {
        String materialName = config.getString(path + ".Type", "STONE");
        org.bukkit.Material material = org.bukkit.Material.getMaterial(materialName);
        if (material == null) {
            material = org.bukkit.Material.STONE;
        }

        ItemStack item = new ItemStack(material);
        org.bukkit.inventory.meta.ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String displayName = config.getString(path + ".Name", "&fItem");
            meta.setDisplayName(EpicCraftMethod.formatColor(displayName));
            
            java.util.List<String> lore = config.getStringList(path + ".Lore");
            if (!lore.isEmpty()) {
                java.util.List<String> coloredLore = new java.util.ArrayList<>();
                for (String line : lore) {
                    coloredLore.add(EpicCraftMethod.formatColor(line));
                }
                meta.setLore(coloredLore);
            }
            
            item.setItemMeta(meta);
        }

        return item;
    }
}
