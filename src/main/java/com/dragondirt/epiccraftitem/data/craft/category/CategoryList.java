package com.dragondirt.epiccraftitem.data.craft.category;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Category list manager
 */
public class CategoryList {
    private final Map<String, Category> categoryMap;
    private final List<Category> categoryList;

    public CategoryList() {
        this.categoryMap = new HashMap<>();
        this.categoryList = new ArrayList<>();
        this.loadCategories();
    }

    /**
     * Load categories from configuration
     */
    private void loadCategories() {
        ConfigurationSection categoriesSection = EpicCraftItem.getInstance().getConfig().getConfigurationSection("categories");
        if (categoriesSection == null) {
            EpicCraftItem.getInstance().getLogger().warning("No categories found in config!");
            return;
        }

        for (String key : categoriesSection.getKeys(false)) {
            ConfigurationSection categorySection = categoriesSection.getConfigurationSection(key);
            if (categorySection == null) continue;

            String name = categorySection.getString("name", key);
            int slot = categorySection.getInt("slot", -1);
            
            // Create item stack
            ItemStack itemStack = createCategoryItem(categorySection);
            
            Category category = new Category(key, itemStack, slot);
            this.categoryMap.put(key, category);
            this.categoryList.add(category);
        }
    }

    /**
     * Create category item from configuration
     */
    private ItemStack createCategoryItem(ConfigurationSection section) {
        String materialName = section.getString("item.type", "STONE");
        Material material = Material.getMaterial(materialName);
        if (material == null) {
            material = Material.STONE;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String displayName = section.getString("item.name", "&fCategory");
            meta.setDisplayName(EpicCraftMethod.formatColor(displayName));
            
            List<String> lore = section.getStringList("item.lore");
            if (!lore.isEmpty()) {
                List<String> coloredLore = new ArrayList<>();
                for (String line : lore) {
                    coloredLore.add(EpicCraftMethod.formatColor(line));
                }
                meta.setLore(coloredLore);
            }
            
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Get category by name
     */
    public Category getData(String name) {
        return categoryMap.get(name);
    }

    /**
     * Get all categories
     */
    public List<Category> getDataList() {
        return new ArrayList<>(categoryList);
    }

    /**
     * Add category
     */
    public void addCategory(Category category) {
        categoryMap.put(category.getName(), category);
        categoryList.add(category);
    }

    /**
     * Remove category
     */
    public void removeCategory(String name) {
        Category category = categoryMap.remove(name);
        if (category != null) {
            categoryList.remove(category);
        }
    }

    /**
     * Check if category exists
     */
    public boolean hasCategory(String name) {
        return categoryMap.containsKey(name);
    }
}
