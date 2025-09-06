package com.dragondirt.epiccraftitem;

import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.command.MainCommand;
import com.dragondirt.epiccraftitem.listener.CraftItemListener;
import com.dragondirt.epiccraftitem.listener.GuiItemCraftListener;
import com.dragondirt.epiccraftitem.listener.GuiItemDetailListener;
import com.dragondirt.epiccraftitem.listener.GuiItemsCategoryListener;
import com.dragondirt.epiccraftitem.listener.GuiMainMenuListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for EpicCraftItem
 * Advanced crafting system for Minecraft 1.20.4
 */
public final class EpicCraftItem extends JavaPlugin {
    
    private static EpicCraftItem plugin;
    private EpicCraftApi epicCraftApi;

    @Override
    public void onEnable() {
        plugin = this;
        
        // Initialize API
        this.epicCraftApi = new EpicCraftApi();
        this.epicCraftApi.load();
        
        // Register listeners
        this.loadListeners();
        
        // Register commands
        this.getCommand("EpicCraftItem").setExecutor(new MainCommand(this));
        this.getCommand("EpicCraft").setExecutor(new MainCommand(this));
        this.getCommand("ECraft").setExecutor(new MainCommand(this));
        
        getLogger().info("EpicCraftItem v" + getDescription().getVersion() + " has been enabled!");
    }

    @Override
    public void onDisable() {
        if (epicCraftApi != null) {
            epicCraftApi.closeGuiCraft();
        }
        getLogger().info("EpicCraftItem has been disabled!");
    }

    /**
     * Load all event listeners
     */
    private void loadListeners() {
        this.getServer().getPluginManager().registerEvents(new GuiMainMenuListener(), this);
        this.getServer().getPluginManager().registerEvents(new GuiItemsCategoryListener(), this);
        this.getServer().getPluginManager().registerEvents(new GuiItemCraftListener(), this);
        this.getServer().getPluginManager().registerEvents(new GuiItemDetailListener(), this);
        this.getServer().getPluginManager().registerEvents(new CraftItemListener(), this);
    }

    /**
     * Get plugin instance
     * @return EpicCraftItem instance
     */
    public static EpicCraftItem getInstance() {
        return plugin;
    }

    /**
     * Get EpicCraftApi instance
     * @return EpicCraftApi instance
     */
    public EpicCraftApi getEpicCraftApi() {
        return this.epicCraftApi;
    }
}
