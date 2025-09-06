package com.dragondirt.epiccraftitem.data.craft.craft;

import com.dragondirt.epiccraftitem.data.craft.category.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Item category list manager
 */
public class ItemCategoryList {
    private final Map<String, ItemCategory> itemCategoryMap;
    private final List<ItemCategory> itemCategoryList;

    public ItemCategoryList() {
        this.itemCategoryMap = new HashMap<>();
        this.itemCategoryList = new ArrayList<>();
    }

    /**
     * Get item category by name
     */
    public ItemCategory getData(String name) {
        return itemCategoryMap.get(name);
    }

    /**
     * Get all item categories
     */
    public List<ItemCategory> getDataList() {
        return new ArrayList<>(itemCategoryList);
    }

    /**
     * Get item categories by category
     */
    public List<ItemCategory> getDataListByCategory(Category category) {
        return itemCategoryList.stream()
                .filter(itemCategory -> itemCategory.getCategory().equals(category))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Add item category
     */
    public void addItemCategory(ItemCategory itemCategory) {
        itemCategoryMap.put(itemCategory.getName(), itemCategory);
        itemCategoryList.add(itemCategory);
    }

    /**
     * Remove item category
     */
    public void removeItemCategory(String name) {
        ItemCategory itemCategory = itemCategoryMap.remove(name);
        if (itemCategory != null) {
            itemCategoryList.remove(itemCategory);
        }
    }

    /**
     * Check if item category exists
     */
    public boolean hasItemCategory(String name) {
        return itemCategoryMap.containsKey(name);
    }
}
