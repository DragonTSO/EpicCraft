package com.dragondirt.epiccraftitem.event;

import com.dragondirt.epiccraftitem.data.craft.craft.ItemCraft;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Event fired when craft is successful
 */
public class CraftEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    
    private final Player player;
    private final ItemCraft itemCraft;
    private final List<ItemStack> itemResult;

    public CraftEvent(Player player, ItemCraft itemCraft, List<ItemStack> itemResult) {
        this.player = player;
        this.itemCraft = itemCraft;
        this.itemResult = itemResult;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemCraft getItemCraft() {
        return itemCraft;
    }

    public List<ItemStack> getItemResult() {
        return itemResult;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
