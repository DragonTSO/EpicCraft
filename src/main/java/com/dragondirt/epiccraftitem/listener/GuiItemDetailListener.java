package com.dragondirt.epiccraftitem.listener;

import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.gui.GuiItemDetail;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Item detail GUI event listener
 */
public class GuiItemDetailListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        
        // Check if this is our item detail GUI
        GuiItemDetail guiItemDetail = EpicCraftApi.getInstance().getGuiItemDetailPlayerMap().get(player.getName());
        if (guiItemDetail == null || !inventory.equals(guiItemDetail.getInventory())) {
            return;
        }

        e.setCancelled(true);
        
        // Handle click events
        int slot = e.getSlot();
        
        // Handle detail button clicks
        // This would be implemented based on your detail handling logic
        EpicCraftApi.sendDebug("GuiItemDetailListener", "Player " + player.getName() + " clicked on slot " + slot + " in item detail GUI");
    }
}
