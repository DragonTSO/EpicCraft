package com.dragondirt.epiccraftitem.api;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.data.craft.category.Category;
import com.dragondirt.epiccraftitem.data.craft.category.CategoryList;
import com.dragondirt.epiccraftitem.data.craft.craft.ItemCategory;
import com.dragondirt.epiccraftitem.data.craft.craft.ItemCategoryList;
import com.dragondirt.epiccraftitem.data.craft.craft.ItemCraft;
import com.dragondirt.epiccraftitem.data.file.ConfigData;
import com.dragondirt.epiccraftitem.data.file.MessageData;
import com.dragondirt.epiccraftitem.data.gui.GuiItemCraft;
import com.dragondirt.epiccraftitem.data.gui.GuiItemDetail;
import com.dragondirt.epiccraftitem.data.gui.GuiItemsCategory;
import com.dragondirt.epiccraftitem.data.gui.GuiMainMenu;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import com.dragondirt.epiccraftitem.util.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main API class for EpicCraftItem
 * Handles all core functionality and data management
 */
public class EpicCraftApi {
    
    private static EpicCraftApi plugin;
    private boolean debug;
    
    // File managers
    private FileManager configFile;
    private FileManager messageFile;
    private FileManager categoriesCraftFile;
    private FileManager mainMenuGuiFile;
    private FileManager itemsCategoryGuiFile;
    private FileManager itemCraftGuiFile;
    private FileManager itemDetailGuiFile;
    
    // Data classes
    private ConfigData configData;
    private MessageData messageData;
    
    // Player management
    private List<String> playerEditingList;
    private List<String> playerHoldEditList;
    
    // GUI management
    private HashMap<String, GuiMainMenu> guiMainMenuPlayerMap;
    private HashMap<String, GuiItemsCategory> guiItemsCategoryPlayerMap;
    private HashMap<String, GuiItemCraft> guiItemCraftPlayerMap;
    private HashMap<String, GuiItemDetail> guiItemDetailPlayerMap;
    
    // Craft data
    private CategoryList categoryList;
    private ItemCategoryList itemCategoryList;

    public EpicCraftApi() {
        plugin = this;
    }

    /**
     * Load all data and initialize the API
     */
    public void load() {
        this.playerEditingList = new ArrayList<>();
        this.playerHoldEditList = new ArrayList<>();
        this.guiMainMenuPlayerMap = new HashMap<>();
        this.guiItemsCategoryPlayerMap = new HashMap<>();
        this.guiItemCraftPlayerMap = new HashMap<>();
        this.guiItemDetailPlayerMap = new HashMap<>();
        
        this.loadFiles();
        this.debug = this.getConfigData().isDefaultDebug();
        this.loadCraft();
    }

    /**
     * Close all open GUIs for all players
     */
    public void closeGuiCraft() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Inventory inventory = player.getOpenInventory().getTopInventory();
            
            if (this.guiMainMenuPlayerMap == null) {
                this.guiMainMenuPlayerMap = new HashMap<>();
            }

            GuiMainMenu guiMainMenu = this.guiMainMenuPlayerMap.getOrDefault(player.getName(), null);
            if (guiMainMenu != null && inventory.equals(guiMainMenu.getInventory())) {
                player.closeInventory();
            } else {
                if (this.guiItemsCategoryPlayerMap == null) {
                    this.guiItemsCategoryPlayerMap = new HashMap<>();
                }

                GuiItemsCategory guiItemsCategory = this.guiItemsCategoryPlayerMap.getOrDefault(player.getName(), null);
                if (guiItemsCategory != null && inventory.equals(guiItemsCategory.getInventory())) {
                    player.closeInventory();
                } else {
                    if (this.guiItemCraftPlayerMap == null) {
                        this.guiItemCraftPlayerMap = new HashMap<>();
                    }

                    GuiItemCraft guiItemCraft = this.guiItemCraftPlayerMap.getOrDefault(player.getName(), null);
                    if (guiItemCraft != null && inventory.equals(guiItemCraft.getInventory())) {
                        player.closeInventory();
                    } else {
                        if (this.guiItemDetailPlayerMap == null) {
                            this.guiItemDetailPlayerMap = new HashMap<>();
                        }

                        GuiItemDetail guiItemDetail = this.guiItemDetailPlayerMap.getOrDefault(player.getName(), null);
                        if (guiItemDetail != null && inventory.equals(guiItemDetail.getInventory())) {
                            player.closeInventory();
                        }
                    }
                }
            }
        });
    }

    /**
     * Load all configuration files
     */
    private void loadFiles() {
        this.configFile = createDefaultFile("config");
        this.messageFile = createDefaultFile("message");
        this.categoriesCraftFile = createDefaultFile("categories", "craft");
        this.mainMenuGuiFile = createDefaultFile("mainMenu", "gui");
        this.itemsCategoryGuiFile = createDefaultFile("itemsCategory", "gui");
        this.itemCraftGuiFile = createDefaultFile("itemCraft", "gui");
        this.itemDetailGuiFile = createDefaultFile("itemDetail", "gui");
        
        this.configData = new ConfigData();
        this.messageData = new MessageData();
    }

    /**
     * Create default file
     */
    public FileManager createDefaultFile(@Nonnull String name, String... folders) {
        FileManager fileManager = new FileManager(name + ".yml", EpicCraftItem.getInstance());
        if (folders.length > 0) {
            fileManager.createFolder(folders);
        }
        fileManager.copyDefault();
        return fileManager;
    }

    /**
     * Load craft data
     */
    private void loadCraft() {
        this.categoryList = new CategoryList();
        this.itemCategoryList = new ItemCategoryList();
    }

    /**
     * Create main menu GUI for player
     */
    public GuiMainMenu createGuiMainMenu(@Nonnull Player player) {
        GuiMainMenu guiMainMenu = new GuiMainMenu(this.getMainMenuGuiFile(), player);
        guiMainMenu.renew();
        this.guiMainMenuPlayerMap.put(player.getName(), guiMainMenu);
        return guiMainMenu;
    }

    /**
     * Create items category GUI for player
     */
    public GuiItemsCategory createGuiItemsCategory(@Nonnull Player player, @Nonnull Category category) {
        GuiItemsCategory guiItemsCategory = new GuiItemsCategory(this.getItemsCategoryGuiFile(), player);
        guiItemsCategory.renew(category);
        this.guiItemsCategoryPlayerMap.put(player.getName(), guiItemsCategory);
        return guiItemsCategory;
    }

    /**
     * Create item craft GUI for player
     */
    public GuiItemCraft createGuiItemCraft(@Nonnull Player player, @Nonnull ItemCategory itemCategory) {
        GuiItemCraft guiItemCraft = new GuiItemCraft(this.getItemCraftGuiFile(), player);
        guiItemCraft.renew(itemCategory, player);
        this.guiItemCraftPlayerMap.put(player.getName(), guiItemCraft);
        return guiItemCraft;
    }

    /**
     * Create item detail GUI for player
     */
    public GuiItemDetail createGuiItemDetail(@Nonnull Player player, @Nonnull ItemCraft itemCraft) {
        GuiItemDetail guiItemDetail = new GuiItemDetail(this.getItemDetailGuiFile(), player);
        guiItemDetail.renew(itemCraft, itemCraft.getItemDetail());
        this.guiItemDetailPlayerMap.put(player.getName(), guiItemDetail);
        return guiItemDetail;
    }

    /**
     * Get AdvancedEnchantments API
     */
    @Nullable
    public AEAPI getAeapi() {
        return !Bukkit.getPluginManager().isPluginEnabled("AdvancedEnchantments") ? null : new AEAPI();
    }

    /**
     * Get MMOItems API
     */
    @Nullable
    public MMOItemsAPI getMmoItemsAPI() {
        return !Bukkit.getPluginManager().isPluginEnabled("MMOItems") ? null : new MMOItemsAPI();
    }

    /**
     * Get FindItem API
     */
    @Nullable
    public FindItemApi getFindItemApi() {
        return !Bukkit.getPluginManager().isPluginEnabled("FindItem") ? null : new FindItemApi();
    }

    /**
     * Return CE Book to player
     */
    public void returnCEBook(@Nonnull Player player, @Nonnull List<ItemStack> itemStackRemoveList) {
        if (this.getConfigData().isEnableReturnCeBook()) {
            AEAPI aeapi = this.getAeapi();
            if (aeapi != null) {
                aeapi.returnCEBook(player, itemStackRemoveList, 
                    this.getConfigData().getSuccessReturnCeBook(), 
                    this.getConfigData().getDestroyReturnCeBook());
            }
        }
    }

    /**
     * Return MMO Gem to player
     */
    public void returnMMOGem(@Nonnull Player player, @Nonnull List<ItemStack> itemStackRemoveList) {
        if (this.getConfigData().isEnableReturnMMOGem()) {
            MMOItemsAPI mmoItemsAPI = this.getMmoItemsAPI();
            if (mmoItemsAPI != null) {
                mmoItemsAPI.returnMMOItemsGem(player, itemStackRemoveList);
            }
        }
    }

    /**
     * Remove Find ID from item
     */
    public ItemStack removeFindIdItem(@Nonnull ItemStack itemStack) {
        FindItemApi findItemApi = this.getFindItemApi();
        return findItemApi == null ? itemStack : findItemApi.removeFindIdItem(itemStack);
    }

    /**
     * Get MMO Items tags
     */
    @Nullable
    public String[] getTagMMOItems(@Nonnull ItemStack itemStack) {
        MMOItemsAPI mmoItemsAPI = this.getMmoItemsAPI();
        if (mmoItemsAPI == null) {
            return null;
        }
        
        String[] arr = new String[2];
        String type = mmoItemsAPI.getTypeMMOItem(itemStack);
        if (type.isEmpty()) {
            return null;
        }
        
        String id = mmoItemsAPI.getIdMMOItem(itemStack);
        if (id.isEmpty()) {
            return null;
        }
        
        arr[0] = type;
        arr[1] = id;
        return arr;
    }

    /**
     * Convert item map to string
     */
    public String convertItemToString(@Nonnull HashMap<ItemStack, Integer> itemMap) {
        StringBuilder text = new StringBuilder();
        itemMap.forEach((k, v) -> {
            ItemMeta itemMeta = k.getItemMeta();
            String nameText = null;
            if (itemMeta != null) {
                if (itemMeta.hasDisplayName() && itemMeta.getDisplayName() != null) {
                    nameText = ChatColor.stripColor(itemMeta.getDisplayName());
                } else if (itemMeta.getLocalizedName() != null) {
                    nameText = ChatColor.stripColor(itemMeta.getLocalizedName());
                }
            }

            if (nameText != null) {
                text.append(nameText);
            } else {
                text.append(k.getType().name());
                if (k.getData() != null) {
                    text.append(":").append(k.getData().getData());
                }
            }

            text.append(" (x").append(v).append(")  ");
        });
        return text.toString();
    }

    /**
     * Convert item map to string list for lore
     */
    public List<String> convertItemToStringList(@Nonnull HashMap<ItemStack, Integer> itemMap) {
        List<String> lore = new ArrayList<>();
        itemMap.forEach((k, v) -> {
            StringBuilder text = new StringBuilder();
            ItemMeta itemMeta = k.getItemMeta();
            text.append("&c - Còn thiếu ");
            String nameText = null;
            if (itemMeta != null) {
                if (itemMeta.hasDisplayName() && itemMeta.getDisplayName() != null) {
                    nameText = ChatColor.stripColor(itemMeta.getDisplayName());
                } else if (itemMeta.getLocalizedName() != null) {
                    nameText = ChatColor.stripColor(itemMeta.getLocalizedName());
                }
            }

            if (nameText != null) {
                text.append(nameText);
            } else {
                text.append(k.getType().name());
                if (k.getData() != null) {
                    text.append(":").append(k.getData().getData());
                }
            }

            text.append(" (x").append(v).append(")");
            lore.add(EpicCraftMethod.formatColor(text.toString()));
        });
        return lore;
    }

    /**
     * Send debug message
     */
    public static void sendDebug(@Nonnull String title, @Nonnull String message) {
        if (getInstance().isDebug()) {
            Bukkit.getConsoleSender().sendMessage(EpicCraftMethod.formatColor("&a[EpicCraftItem] &e(" + title + ") &f" + message));
        }
    }

    // Getters and Setters
    public static EpicCraftApi getInstance() {
        return plugin;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public FileConfiguration getConfigFile() {
        return this.configFile.getConfig();
    }

    public FileConfiguration getMessageFile() {
        return this.messageFile.getConfig();
    }

    public FileConfiguration getCategoriesCraftFile() {
        return this.categoriesCraftFile.getConfig();
    }

    public FileConfiguration getMainMenuGuiFile() {
        return this.mainMenuGuiFile.getConfig();
    }

    public FileConfiguration getItemsCategoryGuiFile() {
        return this.itemsCategoryGuiFile.getConfig();
    }

    public FileConfiguration getItemCraftGuiFile() {
        return this.itemCraftGuiFile.getConfig();
    }

    public FileConfiguration getItemDetailGuiFile() {
        return this.itemDetailGuiFile.getConfig();
    }

    public ConfigData getConfigData() {
        return this.configData;
    }

    public MessageData getMessageData() {
        return this.messageData;
    }

    public List<String> getPlayerEditingList() {
        return this.playerEditingList;
    }

    public List<String> getPlayerHoldEditList() {
        return this.playerHoldEditList;
    }

    public HashMap<String, GuiMainMenu> getGuiMainMenuPlayerMap() {
        return this.guiMainMenuPlayerMap;
    }

    public HashMap<String, GuiItemsCategory> getGuiItemsCategoryPlayerMap() {
        return this.guiItemsCategoryPlayerMap;
    }

    public HashMap<String, GuiItemCraft> getGuiItemCraftPlayerMap() {
        return this.guiItemCraftPlayerMap;
    }

    public HashMap<String, GuiItemDetail> getGuiItemDetailPlayerMap() {
        return this.guiItemDetailPlayerMap;
    }

    public CategoryList getCategoryList() {
        return this.categoryList;
    }

    public ItemCategoryList getItemCategoryList() {
        return this.itemCategoryList;
    }
}
