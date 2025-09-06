package com.dragondirt.epiccraftitem.data.gui;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.craft.craft.ItemCategory;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * Item craft GUI handler
 */
public class GuiItemCraft {
    private final FileConfiguration config;
    private final Player player;
    private Inventory inventory;
    private ItemCategory itemCategory;

    public GuiItemCraft(@Nonnull FileConfiguration config, @Nonnull Player player) {
        this.config = config;
        this.player = player;
        this.inventory = createInventory();
    }

    /**
     * Create inventory
     */
    private Inventory createInventory() {
        String title = EpicCraftMethod.formatColor(config.getString("Title", "&e&l[Chế tạo] <item>"));
        if (itemCategory != null) {
            title = title.replace("<item>", itemCategory.getName());
        }
        int size = 45; // 5 rows
        
        return Bukkit.createInventory(player, size, title);
    }

    /**
     * Renew GUI with item category
     */
    public boolean renew(ItemCategory itemCategory, Player player) {
        this.itemCategory = itemCategory;
        this.inventory = createInventory();
        this.loadDataItemCraft();
        return true;
    }

    /**
     * Load data for item craft
     */
    private void loadDataItemCraft() {
        Bukkit.getScheduler().runTaskAsynchronously(EpicCraftItem.getInstance(), () -> {
            if (this.inventory.getHolder() instanceof Player) {
                Player player = (Player) this.inventory.getHolder();
                if (player != null) {
                    EpicCraftApi.sendDebug("GuiItemCraft loadDataItemCraft", "(" + player.getName() + ") Gui Item Craft đã được mở cho " + itemCategory.getName());
                }
            }

            // Load craft data for this item
            // This would be implemented based on your craft loading logic
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
     * Get item category
     */
    public ItemCategory getItemCategory() {
        return this.itemCategory;
    }

    /**
     * GUI Keys enum
     */
    public enum Key {
        CRAFT
    }
}
