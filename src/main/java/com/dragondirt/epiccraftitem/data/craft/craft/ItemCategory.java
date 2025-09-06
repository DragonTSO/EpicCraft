package com.dragondirt.epiccraftitem.data.craft.craft;

import com.dragondirt.epiccraftitem.data.craft.category.Category;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Item category class
 */
public class ItemCategory {
    private final String name;
    private final Category category;
    @Nullable
    private ItemStack itemStack;
    private int slot;

    public ItemCategory(@Nonnull String name, @Nonnull Category category) {
        this.name = name;
        this.category = category;
        this.slot = -1;
    }

    public ItemCategory(@Nonnull String name, @Nonnull Category category, ItemStack itemStack, int slot) {
        this(name, category);
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
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
