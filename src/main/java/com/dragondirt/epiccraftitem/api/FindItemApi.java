package com.dragondirt.epiccraftitem.api;

import org.bukkit.inventory.ItemStack;

/**
 * FindItem API wrapper
 */
public class FindItemApi {
    
    /**
     * Remove Find ID from item
     */
    public ItemStack removeFindIdItem(ItemStack itemStack) {
        // Implementation for removing Find ID from item
        // This would be implemented based on the actual FindItem API
        EpicCraftApi.sendDebug("FindItemApi", "Removing Find ID from item");
        return itemStack;
    }
}
