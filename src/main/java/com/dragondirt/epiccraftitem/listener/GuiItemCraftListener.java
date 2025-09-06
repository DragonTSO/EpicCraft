package com.dragondirt.epiccraftitem.listener;

import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.gui.GuiItemCraft;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Item craft GUI event listener
 */
public class GuiItemCraftListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        
        // Check if this is our item craft GUI
        GuiItemCraft guiItemCraft = EpicCraftApi.getInstance().getGuiItemCraftPlayerMap().get(player.getName());
        if (guiItemCraft == null || !inventory.equals(guiItemCraft.getInventory())) {
            return;
        }

        e.setCancelled(true);
        
        // Handle click events
        int slot = e.getSlot();
        
        // Handle craft button clicks
        // This would be implemented based on your craft handling logic
        EpicCraftApi.sendDebug("GuiItemCraftListener", "Player " + player.getName() + " clicked on slot " + slot + " in item craft GUI");
    }
}
