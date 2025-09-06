package com.dragondirt.epiccraftitem.command;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.craft.category.Category;
import com.dragondirt.epiccraftitem.data.gui.GuiItemsCategory;
import com.dragondirt.epiccraftitem.data.gui.GuiMainMenu;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main command handler for EpicCraftItem
 */
public class MainCommand implements CommandExecutor, TabCompleter {

    private final EpicCraftItem plugin;

    public MainCommand(EpicCraftItem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cChỉ người chơi mới có thể sử dụng lệnh này!");
                return true;
            }
            
            Player player = (Player) sender;
            if (!player.hasPermission("epiccraftitem.user")) {
                player.sendMessage("§cBạn không có quyền sử dụng lệnh này!");
                return true;
            }
            
            return openMainMenu(player);
        }

        switch (args[0].toLowerCase()) {
            case "help":
                return handleHelp(sender);
            case "reload":
                return handleReload(sender);
            case "debug":
                return handleDebug(sender);
            case "edit":
                return handleEdit(sender, args);
            case "open":
                return handleOpen(sender, args);
            default:
                sender.sendMessage(EpicCraftApi.getInstance().getMessageData().getCmdError());
                return true;
        }
    }

    /**
     * Open main menu
     */
    private boolean openMainMenu(Player player) {
        GuiMainMenu guiMainMenu = EpicCraftApi.getInstance().createGuiMainMenu(player);
        if (guiMainMenu == null) {
            EpicCraftApi.sendDebug(player.getName() + " MainCommand openMainMenu", "Không thể mở vì guiMainMenu bị null");
            return false;
        }
        
        EpicCraftMethod.openInventory(player, guiMainMenu.getInventory());
        return true;
    }

    /**
     * Handle help command
     */
    private boolean handleHelp(CommandSender sender) {
        if (!sender.hasPermission("epiccraftitem.user")) {
            sender.sendMessage("§cBạn không có quyền sử dụng lệnh này!");
            return true;
        }
        
        EpicCraftApi.getInstance().getMessageData().getHelp().forEach(sender::sendMessage);
        return true;
    }

    /**
     * Handle reload command
     */
    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("epiccraftitem.admin")) {
            sender.sendMessage("§cBạn không có quyền sử dụng lệnh này!");
            return true;
        }
        
        EpicCraftApi.getInstance().closeGuiCraft();
        EpicCraftApi.getInstance().load();
        sender.sendMessage(EpicCraftApi.getInstance().getMessageData().getReload());
        return true;
    }

    /**
     * Handle debug command
     */
    private boolean handleDebug(CommandSender sender) {
        if (!sender.hasPermission("epiccraftitem.admin")) {
            sender.sendMessage("§cBạn không có quyền sử dụng lệnh này!");
            return true;
        }
        
        EpicCraftApi epicCraftApi = EpicCraftApi.getInstance();
        if (epicCraftApi.isDebug()) {
            epicCraftApi.setDebug(false);
            sender.sendMessage("§cĐã tắt debug.");
        } else {
            epicCraftApi.setDebug(true);
            sender.sendMessage("§aĐã bật debug.");
        }
        return true;
    }

    /**
     * Handle edit command
     */
    private boolean handleEdit(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cChỉ người chơi mới có thể sử dụng lệnh này!");
            return true;
        }
        
        Player player = (Player) sender;
        if (!player.hasPermission("epiccraftitem.edit")) {
            player.sendMessage("§cBạn không có quyền sử dụng lệnh này!");
            return true;
        }
        
        if (args.length == 2 && args[1].equalsIgnoreCase("category")) {
            return handleEditCategory(player);
        } else if (args.length == 3 && args[1].equalsIgnoreCase("item")) {
            return handleEditItemCategory(player, args[2]);
        } else {
            player.sendMessage("§cSử dụng: /ecraft edit category hoặc /ecraft edit item <category>");
            return true;
        }
    }

    /**
     * Handle edit category command
     */
    private boolean handleEditCategory(Player player) {
        EpicCraftApi.getInstance().getPlayerEditingList().add(player.getName());
        GuiMainMenu guiMainMenu = EpicCraftApi.getInstance().createGuiMainMenu(player);
        if (guiMainMenu == null) {
            EpicCraftApi.sendDebug(player.getName() + " MainCommand handleEditCategory", "Không thể mở vì guiMainMenu bị null");
            return false;
        }
        
        EpicCraftMethod.openInventory(player, guiMainMenu.getInventory());
        return true;
    }

    /**
     * Handle edit item category command
     */
    private boolean handleEditItemCategory(Player player, String categoryName) {
        EpicCraftApi.getInstance().getPlayerEditingList().add(player.getName());
        Category category = EpicCraftApi.getInstance().getCategoryList().getData(categoryName);
        if (category == null) {
            EpicCraftApi.sendDebug(player.getName() + " MainCommand handleEditItemCategory", "Không thể mở vì category bị null");
            player.sendMessage("§cKhông tìm thấy danh mục: " + categoryName);
            return false;
        }
        
        GuiItemsCategory guiItemsCategory = EpicCraftApi.getInstance().createGuiItemsCategory(player, category);
        if (guiItemsCategory == null) {
            EpicCraftApi.sendDebug(player.getName() + " MainCommand handleEditItemCategory", "Không thể mở vì guiItemsCategory bị null");
            return false;
        }
        
        guiItemsCategory.renew(category);
        EpicCraftMethod.openInventory(player, guiItemsCategory.getInventory());
        return true;
    }

    /**
     * Handle open command
     */
    private boolean handleOpen(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cChỉ người chơi mới có thể sử dụng lệnh này!");
            return true;
        }
        
        Player player = (Player) sender;
        if (!player.hasPermission("epiccraftitem.user")) {
            player.sendMessage("§cBạn không có quyền sử dụng lệnh này!");
            return true;
        }
        
        if (args.length < 2) {
            player.sendMessage("§cSử dụng: /ecraft open <category>");
            return true;
        }
        
        Category category = EpicCraftApi.getInstance().getCategoryList().getData(args[1]);
        if (category == null) {
            EpicCraftApi.sendDebug(player.getName() + " MainCommand handleOpen", "Không thể mở vì category bị null");
            player.sendMessage("§cKhông tìm thấy danh mục: " + args[1]);
            return false;
        }
        
        GuiItemsCategory guiItemsCategory = EpicCraftApi.getInstance().createGuiItemsCategory(player, category);
        if (guiItemsCategory == null) {
            EpicCraftApi.sendDebug(player.getName() + " MainCommand handleOpen", "Không thể mở vì guiItemsCategory bị null");
            return false;
        }
        
        EpicCraftMethod.openInventory(player, guiItemsCategory.getInventory());
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("help", "reload", "debug", "edit", "open");
            return subCommands.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("edit")) {
                return Arrays.asList("category", "item");
            } else if (args[0].equalsIgnoreCase("open")) {
                return EpicCraftApi.getInstance().getCategoryList().getDataList().stream()
                        .map(Category::getName)
                        .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        
        if (args.length == 3 && args[0].equalsIgnoreCase("edit") && args[1].equalsIgnoreCase("item")) {
            return EpicCraftApi.getInstance().getCategoryList().getDataList().stream()
                    .map(Category::getName)
                    .filter(s -> s.toLowerCase().startsWith(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return completions;
    }
}
