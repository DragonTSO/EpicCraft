package com.dragondirt.epiccraftitem.data.gui;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.craft.category.Category;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * Items category GUI handler
 */
public class GuiItemsCategory {
    private final FileConfiguration config;
    private final Player player;
    private Inventory inventory;
    private Category category;

    public GuiItemsCategory(@Nonnull FileConfiguration config, @Nonnull Player player) {
        this.config = config;
        this.player = player;
        this.inventory = createInventory();
    }

    /**
     * Create inventory
     */
    private Inventory createInventory() {
        String title = EpicCraftMethod.formatColor(config.getString("Title", "&e&l[Danh mục] <category>"));
        if (category != null) {
            title = title.replace("<category>", category.getName());
        }
        int size = 45; // 5 rows
        
        return Bukkit.createInventory(player, size, title);
    }

    /**
     * Renew GUI with category
     */
    public boolean renew(Category category) {
        this.category = category;
        this.inventory = createInventory();
        this.loadDataItemsCategory();
        return true;
    }

    /**
     * Load data for items category
     */
    private void loadDataItemsCategory() {
        Bukkit.getScheduler().runTaskAsynchronously(EpicCraftItem.getInstance(), () -> {
            if (this.inventory.getHolder() instanceof Player) {
                Player player = (Player) this.inventory.getHolder();
                if (player != null) {
                    EpicCraftApi.sendDebug("GuiItemsCategory loadDataItemsCategory", "(" + player.getName() + ") Gui Items Category đã được mở cho " + category.getName());
                }
            }

            // Load items for this category
            // This would be implemented based on your item loading logic
        });
    }

    /**
     * Set item to inventory
     */
    public void setItemToInventory(int slot, org.bukkit.inventory.ItemStack item) {
        if (slot >= 0 && slot < inventory.getSize()) {
            inventory.setItem(slot, item);
        }
    }

    /**
     * Get inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Get player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get category
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * GUI Keys enum
     */
    public enum Key {
        ITEM
    }
}
