package com.dragondirt.epiccraftitem.data.gui;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.craft.craft.ItemCraft;
import com.dragondirt.epiccraftitem.data.craft.detail.ItemDetail;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

/**
 * Item detail GUI handler
 */
public class GuiItemDetail {
    private final FileConfiguration config;
    private final Player player;
    private Inventory inventory;
    private ItemCraft itemCraft;
    private ItemDetail itemDetail;

    public GuiItemDetail(@Nonnull FileConfiguration config, @Nonnull Player player) {
        this.config = config;
        this.player = player;
        this.inventory = createInventory();
    }

    /**
     * Create inventory
     */
    private Inventory createInventory() {
        String title = EpicCraftMethod.formatColor(config.getString("Title", "&e&l[Chi tiết] <item>"));
        if (itemCraft != null) {
            title = title.replace("<item>", itemCraft.getItemCategory().getName());
        }
        int size = 45; // 5 rows
        
        return Bukkit.createInventory(player, size, title);
    }

    /**
     * Renew GUI with item craft and detail
     */
    public boolean renew(ItemCraft itemCraft, ItemDetail itemDetail) {
        this.itemCraft = itemCraft;
        this.itemDetail = itemDetail;
        this.inventory = createInventory();
        this.loadDataItemDetail();
        return true;
    }

    /**
     * Load data for item detail
     */
    private void loadDataItemDetail() {
        Bukkit.getScheduler().runTaskAsynchronously(EpicCraftItem.getInstance(), () -> {
            if (this.inventory.getHolder() instanceof Player) {
                Player player = (Player) this.inventory.getHolder();
                if (player != null) {
                    EpicCraftApi.sendDebug("GuiItemDetail loadDataItemDetail", "(" + player.getName() + ") Gui Item Detail đã được mở cho " + itemCraft.getItemCategory().getName());
                }
            }

            // Load detail data for this item
            // This would be implemented based on your detail loading logic
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
     * Get item craft
     */
    public ItemCraft getItemCraft() {
        return this.itemCraft;
    }

    /**
     * Get item detail
     */
    public ItemDetail getItemDetail() {
        return this.itemDetail;
    }

    /**
     * GUI Keys enum
     */
    public enum Key {
        DETAIL,
        CHANCE_SUCCESS
    }
}
