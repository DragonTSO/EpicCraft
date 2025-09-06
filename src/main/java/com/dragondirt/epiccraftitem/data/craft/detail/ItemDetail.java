package com.dragondirt.epiccraftitem.data.craft.detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Item detail class
 */
public class ItemDetail {
    private final List<ItemDetailAbstract> itemDetailAbstracts;
    private double chanceSuccess;
    private List<String> commandSuccessList;
    private List<String> commandFailedList;

    public ItemDetail() {
        this.itemDetailAbstracts = new ArrayList<>();
        this.chanceSuccess = 100.0;
        this.commandSuccessList = new ArrayList<>();
        this.commandFailedList = new ArrayList<>();
    }

    /**
     * Compare two items
     */
    public boolean compareItem(org.bukkit.inventory.ItemStack item1, org.bukkit.inventory.ItemStack item2, int slot) {
        if (item1 == null || item2 == null) {
            return false;
        }
        
        // Basic comparison - can be extended for more complex item comparison
        return item1.getType() == item2.getType() && 
               item1.getAmount() >= item2.getAmount();
    }

    // Getters and Setters
    public List<ItemDetailAbstract> getItemDetailAbstracts() {
        return itemDetailAbstracts;
    }

    public double getChanceSuccess() {
        return chanceSuccess;
    }

    public void setChanceSuccess(double chanceSuccess) {
        this.chanceSuccess = chanceSuccess;
    }

    public List<String> getCommandSuccessList() {
        return commandSuccessList;
    }

    public void setCommandSuccessList(List<String> commandSuccessList) {
        this.commandSuccessList = commandSuccessList;
    }

    public List<String> getCommandFailedList() {
        return commandFailedList;
    }

    public void setCommandFailedList(List<String> commandFailedList) {
        this.commandFailedList = commandFailedList;
    }
}
