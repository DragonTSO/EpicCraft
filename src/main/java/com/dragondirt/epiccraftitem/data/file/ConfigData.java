package com.dragondirt.epiccraftitem.data.file;

import com.dragondirt.epiccraftitem.EpicCraftItem;

/**
 * Configuration data handler
 */
public class ConfigData {
    
    private final EpicCraftItem plugin;
    
    public ConfigData() {
        this.plugin = EpicCraftItem.getInstance();
    }
    
    /**
     * Check if default debug is enabled
     */
    public boolean isDefaultDebug() {
        return plugin.getConfig().getBoolean("DefaultDebug", true);
    }
    
    /**
     * Check if return CE Book is enabled
     */
    public boolean isEnableReturnCeBook() {
        return plugin.getConfig().getBoolean("ReturnCeBook.Enable", true);
    }
    
    /**
     * Get success return CE Book percentage
     */
    public int getSuccessReturnCeBook() {
        return plugin.getConfig().getInt("ReturnCeBook.Success", 100);
    }
    
    /**
     * Get destroy return CE Book percentage
     */
    public int getDestroyReturnCeBook() {
        return plugin.getConfig().getInt("ReturnCeBook.Destroy", 0);
    }
    
    /**
     * Check if return MMO Gem is enabled
     */
    public boolean isEnableReturnMMOGem() {
        return plugin.getConfig().getBoolean("ReturnMMOGem", true);
    }
    
    /**
     * Check if return CE Book when failed
     */
    public boolean isReturnCeBookWhenFailed() {
        return plugin.getConfig().getBoolean("ReturnCeBookWhenFailed", true);
    }
    
    /**
     * Check if return MMO Gem when failed
     */
    public boolean isReturnMMOGemWhenFailed() {
        return plugin.getConfig().getBoolean("ReturnMMOGemWhenFailed", true);
    }
    
    /**
     * Get lore require list
     */
    public java.util.List<String> getLoreRequire() {
        return plugin.getConfig().getStringList("LoreRequire");
    }
    
    /**
     * Get custom item configuration
     */
    public String getCustomItemType(String key) {
        return plugin.getConfig().getString("ItemCustom." + key + ".Type", "STONE");
    }
    
    /**
     * Get custom item name
     */
    public String getCustomItemName(String key) {
        return plugin.getConfig().getString("ItemCustom." + key + ".Name", "&fItem");
    }
}
