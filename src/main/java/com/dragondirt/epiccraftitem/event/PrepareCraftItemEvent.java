package com.dragondirt.epiccraftitem.event;

import com.dragondirt.epiccraftitem.data.craft.craft.ItemCraft;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event fired when craft is prepared
 */
public class PrepareCraftItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    
    private final Player player;
    private final ItemCraft itemCraft;

    public PrepareCraftItemEvent(Player player, ItemCraft itemCraft) {
        this.player = player;
        this.itemCraft = itemCraft;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemCraft getItemCraft() {
        return itemCraft;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
