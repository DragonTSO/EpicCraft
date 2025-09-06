package com.dragondirt.epiccraftitem.event;

import com.dragondirt.epiccraftitem.data.craft.craft.ItemCraft;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Event fired when craft fails due to missing items
 */
public class CraftMissingItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    
    private final Player player;
    private final ItemCraft itemCraft;
    private final HashMap<ItemStack, Integer> missingItemsMap;

    public CraftMissingItemEvent(Player player, ItemCraft itemCraft, HashMap<ItemStack, Integer> missingItemsMap) {
        this.player = player;
        this.itemCraft = itemCraft;
        this.missingItemsMap = missingItemsMap;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemCraft getItemCraft() {
        return itemCraft;
    }

    public HashMap<ItemStack, Integer> getMissingItemsMap() {
        return missingItemsMap;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
