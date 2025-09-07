package com.dragondirt.epiccraftitem.data.gui;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.craft.category.CategoryList;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import com.dragondirt.epiccraftitem.util.GuiMethod;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Main menu GUI handler
 */
public class GuiMainMenu {
    private final FileConfiguration config;
    private final Player player;
    private Inventory inventory;

    public GuiMainMenu(@Nonnull FileConfiguration config, @Nonnull Player player) {
        this.config = config;
        this.player = player;
        this.inventory = createInventory();
    }

    /**
     * Create inventory
     */
    private Inventory createInventory() {
        String title = EpicCraftMethod.formatColor(config.getString("Title", "&e&l[Siêu rèn]"));
        int size = 45; // 5 rows
        
        return Bukkit.createInventory(player, size, title);
    }

    /**
     * Renew GUI
     */
    public boolean renew() {
        this.inventory = createInventory();
        this.loadDataMainMenu();
        GuiMethod.loadEditing(this);
        return true;
    }

    /**
     * Load data for main menu
     */
    private void loadDataMainMenu() {
        Bukkit.getScheduler().runTaskAsynchronously(EpicCraftItem.getInstance(), () -> {
            if (this.inventory.getHolder() instanceof Player) {
                Player player = (Player) this.inventory.getHolder();
                if (player != null) {
                    EpicCraftApi.sendDebug("GuiMainMenu loadDataMainMenu", "(" + player.getName() + ") Gui Main Menu đã được mở.");
                }
            }

            CategoryList categoryList = EpicCraftApi.getInstance().getCategoryList();
            categoryList.getDataList().forEach(category -> {
                if (category.getSlot() != -1 && category.getItemStack() != null) {
                    if (category.getSlot() < inventory.getSize()) {
                        inventory.setItem(category.getSlot(), category.getItemStack());
                    } else {
                        EpicCraftApi.sendDebug("GuiMainMenu loadDataMainMenu", "Item " + category.getName() + " ở vị trí " + category.getSlot() + " không hợp lệ.");
                    }
                } else {
                    EpicCraftApi.sendDebug("GuiMainMenu loadDataMainMenu", "categories.yml bị lỗi ở " + category.getName());
                }
            });
        });
    }

    /**
     * Set item to inventory
     */
    public void setItemToInventory(int slot, ItemStack item) {
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
     * GUI Keys enum
     */
    public enum Key {
        CATEGORY,
        EDIT
    }
}
