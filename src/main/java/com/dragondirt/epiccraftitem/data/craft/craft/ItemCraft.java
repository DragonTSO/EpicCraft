package com.dragondirt.epiccraftitem.data.craft.craft;

import com.dragondirt.epiccraftitem.data.craft.detail.ItemDetail;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Item craft class
 */
public class ItemCraft {
    private final ItemCategory itemCategory;
    @Nullable
    private ItemDetail itemDetail;
    private final HashMap<Integer, ItemStack> itemRequireMap;
    private final HashMap<Integer, ItemStack> itemResultMap;

    public ItemCraft(@Nonnull ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
        this.itemRequireMap = new HashMap<>();
        this.itemResultMap = new HashMap<>();
    }

    public ItemCraft(@Nonnull ItemCategory itemCategory, @Nonnull ItemDetail itemDetail) {
        this(itemCategory);
        this.itemDetail = itemDetail;
    }

    @Nullable
    public ItemStack getItemRequireDetail(int slot) {
        return this.itemRequireMap.getOrDefault(slot, null);
    }

    /**
     * Check if player has all required items
     */
    public HashMap<ItemStack, Integer> checkPlayerHasFullRequire(@Nonnull Player player) {
        HashMap<Integer, ItemStack> itemRequireMap = new HashMap<>(this.getItemRequireMap());
        HashMap<ItemStack, Integer> itemRequireMissingMap = new HashMap<>();
        Inventory inventory = player.getInventory();
        
        if (this.getItemDetail() == null) {
            return itemRequireMissingMap;
        }

        itemRequireMap.forEach((slot, itemMain) -> {
            itemMain = itemMain.clone();
            int amount = itemMain.getAmount();

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack itemPlayer = inventory.getItem(i);
                if (itemPlayer != null) {
                    itemPlayer = itemPlayer.clone();
                    if (this.getItemDetail().compareItem(itemMain, itemPlayer, slot)) {
                        int amountPlayer = itemPlayer.getAmount();
                        amount -= amountPlayer;
                        if (amount <= 0) {
                            break;
                        }
                    }
                }
            }

            if (amount > 0) {
                int amountMissing = itemRequireMissingMap.getOrDefault(itemMain, 0);
                itemRequireMissingMap.put(itemMain, amountMissing + amount);
            }
        });
        
        return itemRequireMissingMap;
    }

    /**
     * Take required items from player inventory
     */
    public List<ItemStack> takeItemPlayerHasFullRequire(@Nonnull Player player) {
        HashMap<Integer, ItemStack> itemRequireMap = new HashMap<>(this.getItemRequireMap());
        List<ItemStack> itemStackRemoveList = new ArrayList<>();
        Inventory inventory = player.getInventory();
        
        if (this.getItemDetail() == null) {
            return itemStackRemoveList;
        }

        itemRequireMap.forEach((slot, itemMain) -> {
            itemMain = itemMain.clone();
            int amount = itemMain.getAmount();

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack itemPlayer = inventory.getItem(i);
                if (itemPlayer != null && this.getItemDetail().compareItem(itemMain, itemPlayer, slot)) {
                    int amountPlayer = itemPlayer.getAmount();
                    int cal = amount - amountPlayer;
                    if (cal <= 0) {
                        if (cal == 0) {
                            inventory.setItem(i, new ItemStack(Material.AIR));
                        } else {
                            itemPlayer.setAmount(amountPlayer - amount);
                        }
                        itemStackRemoveList.add(itemPlayer);
                        break;
                    }
                    inventory.setItem(i, new ItemStack(Material.AIR));
                    itemStackRemoveList.add(itemPlayer);
                    amount -= amountPlayer;
                }
            }
        });
        
        return itemStackRemoveList;
    }

    // Getters and Setters
    public ItemCategory getItemCategory() {
        return this.itemCategory;
    }

    @Nullable
    public ItemDetail getItemDetail() {
        return this.itemDetail;
    }

    public void setItemDetail(@Nullable ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public HashMap<Integer, ItemStack> getItemRequireMap() {
        return this.itemRequireMap;
    }

    public HashMap<Integer, ItemStack> getItemResultMap() {
        return this.itemResultMap;
    }
}
