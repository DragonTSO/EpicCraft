package com.dragondirt.epiccraftitem.data.file;

import com.dragondirt.epiccraftitem.EpicCraftItem;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;

import java.util.List;

/**
 * Message data handler
 */
public class MessageData {
    
    private final EpicCraftItem plugin;
    
    public MessageData() {
        this.plugin = EpicCraftItem.getInstance();
    }
    
    /**
     * Get help messages
     */
    public List<String> getHelp() {
        return plugin.getConfig().getStringList("Help");
    }
    
    /**
     * Get craft chance success message
     */
    public String getCraftChanceSuccess() {
        return EpicCraftMethod.formatColor(plugin.getConfig().getString("CraftChanceSuccess", "&aChế tạo thành công"));
    }
    
    /**
     * Get craft chance failed message
     */
    public String getCraftChanceFailed() {
        return EpicCraftMethod.formatColor(plugin.getConfig().getString("CraftChanceFailed", "&cChế tạo thất bại"));
    }
    
    /**
     * Get craft failed message with missing items
     */
    public String getCraftFailed(String items) {
        String message = plugin.getConfig().getString("CraftFailed", "&cChế tạo thất bại, còn thiếu <items>");
        return EpicCraftMethod.formatColor(message.replace("<items>", items));
    }
    
    /**
     * Get reload success message
     */
    public String getReload() {
        return EpicCraftMethod.formatColor(plugin.getConfig().getString("Reload", "&aTải lại thành công"));
    }
    
    /**
     * Get command error message
     */
    public String getCmdError() {
        return EpicCraftMethod.formatColor(plugin.getConfig().getString("CmdError", "&cLệnh không chính xác"));
    }
}
