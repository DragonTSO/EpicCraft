package com.dragondirt.epiccraftitem.listener;

import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.gui.GuiMainMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Main menu GUI event listener
 */
public class GuiMainMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        
        // Check if this is our main menu GUI
        GuiMainMenu guiMainMenu = EpicCraftApi.getInstance().getGuiMainMenuPlayerMap().get(player.getName());
        if (guiMainMenu == null || !inventory.equals(guiMainMenu.getInventory())) {
            return;
        }

        e.setCancelled(true);
        
        // Handle click events
        int slot = e.getSlot();
        
        // Check if clicked on category item
        if (EpicCraftApi.getInstance().getCategoryList().getDataList().stream()
                .anyMatch(category -> category.getSlot() == slot)) {
            
            // Find the category that was clicked
            EpicCraftApi.getInstance().getCategoryList().getDataList().stream()
                    .filter(category -> category.getSlot() == slot)
                    .findFirst()
                    .ifPresent(category -> {
                        // Open items category GUI
                        EpicCraftApi.getInstance().createGuiItemsCategory(player, category);
                        EpicCraftApi.sendDebug("GuiMainMenuListener", "Player " + player.getName() + " clicked on category " + category.getName());
                    });
        }
        
        // Handle edit button click
        // This would be implemented based on your edit functionality
    }
}
