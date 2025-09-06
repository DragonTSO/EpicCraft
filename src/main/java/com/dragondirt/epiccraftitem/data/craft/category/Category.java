package com.dragondirt.epiccraftitem.data.craft.category;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Category class for craft items
 */
public class Category {
    private final String name;
    @Nullable
    private ItemStack itemStack;
    private int slot;

    public Category(@Nonnull String name) {
        this.name = name;
        this.slot = -1;
    }

    public Category(@Nonnull String name, ItemStack itemStack, int slot) {
        this(name);
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setItemStack(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
