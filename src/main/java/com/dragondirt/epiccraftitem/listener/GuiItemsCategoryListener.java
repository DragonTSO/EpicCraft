package com.dragondirt.epiccraftitem.listener;

import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.gui.GuiItemsCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Items category GUI event listener
 */
public class GuiItemsCategoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        
        // Check if this is our items category GUI
        GuiItemsCategory guiItemsCategory = EpicCraftApi.getInstance().getGuiItemsCategoryPlayerMap().get(player.getName());
        if (guiItemsCategory == null || !inventory.equals(guiItemsCategory.getInventory())) {
            return;
        }

        e.setCancelled(true);
        
        // Handle click events
        int slot = e.getSlot();
        
        // Handle item clicks
        // This would be implemented based on your item handling logic
        EpicCraftApi.sendDebug("GuiItemsCategoryListener", "Player " + player.getName() + " clicked on slot " + slot + " in items category GUI");
    }
}
