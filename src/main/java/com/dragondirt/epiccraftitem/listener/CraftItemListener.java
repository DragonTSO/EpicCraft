package com.dragondirt.epiccraftitem.listener;

import com.dragondirt.epiccraftitem.api.EpicCraftApi;
import com.dragondirt.epiccraftitem.data.craft.craft.ItemCraft;
import com.dragondirt.epiccraftitem.data.craft.detail.ItemDetailAbstract;
import com.dragondirt.epiccraftitem.data.gui.GuiItemDetail;
import com.dragondirt.epiccraftitem.event.CraftEvent;
import com.dragondirt.epiccraftitem.event.CraftMissingItemEvent;
import com.dragondirt.epiccraftitem.event.PrepareCraftItemEvent;
import com.dragondirt.epiccraftitem.util.EpicCraftMethod;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Craft item event listener
 */
public class CraftItemListener implements Listener {

    @EventHandler
    public void onPrepareCraftItem(PrepareCraftItemEvent e) {
        Player player = e.getPlayer();
        ItemCraft itemCraft = e.getItemCraft();
        HashMap<ItemStack, Integer> map = itemCraft.checkPlayerHasFullRequire(player);
        
        if (map.size() == 0) {
            List<ItemStack> itemResultClone = itemCraft.getItemResultMap().values().stream()
                    .map(ItemStack::clone)
                    .collect(Collectors.toList());
            Bukkit.getPluginManager().callEvent(new CraftEvent(player, itemCraft, itemResultClone));
        } else {
            Bukkit.getPluginManager().callEvent(new CraftMissingItemEvent(player, itemCraft, map));
        }
    }

    @EventHandler
    public void onCraft(CraftEvent e) {
        Player player = e.getPlayer();
        Inventory inventory = player.getInventory();
        List<ItemStack> itemStackRemoveList = e.getItemCraft().takeItemPlayerHasFullRequire(player);
        
        if (e.getItemCraft().getItemDetail() == null) {
            EpicCraftApi.sendDebug("CraftItemListener onCraftSuccess", "(" + player.getName() + ") ItemDetail bị null.");
            return;
        }

        double ran = EpicCraftMethod.randomDouble(0.0, 100.0);
        boolean isSuccess = e.getItemCraft().getItemDetail().getItemDetailAbstracts().stream()
                .filter(v -> v.getKey().equals(GuiItemDetail.Key.CHANCE_SUCCESS.name()))
                .map(ItemDetailAbstract::isEnable)
                .findAny()
                .orElse(false);

        if (isSuccess && e.getItemCraft().getItemDetail().getChanceSuccess() >= ran) {
            EpicCraftApi.getInstance().returnMMOGem(player, itemStackRemoveList);
            EpicCraftApi.getInstance().returnCEBook(player, itemStackRemoveList);
            
            e.getItemResult().forEach(item -> {
                if (inventory.firstEmpty() != -1) {
                    inventory.addItem(item);
                } else {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                }
            });

            e.getItemCraft().getItemDetail().getCommandSuccessList().forEach(command -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("<player>", player.getName()));
            });
            
            player.sendMessage(EpicCraftApi.getInstance().getMessageData().getCraftChanceSuccess());
            EpicCraftApi.sendDebug("CraftItemListener onCraftSuccess", "(" + player.getName() + ") chế tạo thành công " + e.getItemCraft().getItemCategory().getName());
        } else {
            if (EpicCraftApi.getInstance().getConfigData().isReturnMMOGemWhenFailed()) {
                EpicCraftApi.getInstance().returnMMOGem(player, itemStackRemoveList);
            }

            if (EpicCraftApi.getInstance().getConfigData().isReturnCeBookWhenFailed()) {
                EpicCraftApi.getInstance().returnCEBook(player, itemStackRemoveList);
            }

            e.getItemCraft().getItemDetail().getCommandFailedList().forEach(command -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("<player>", player.getName()));
            });
            
            player.sendMessage(EpicCraftApi.getInstance().getMessageData().getCraftChanceFailed());
            EpicCraftApi.sendDebug("CraftItemListener onCraftSuccess", "(" + player.getName() + ") chế tạo thất bại " + e.getItemCraft().getItemCategory().getName());
        }
    }

    @EventHandler
    public void onCraftMissingItem(CraftMissingItemEvent e) {
        Player player = e.getPlayer();
        String text = EpicCraftApi.getInstance().convertItemToString(e.getMissingItemsMap());
        player.sendMessage(EpicCraftApi.getInstance().getMessageData().getCraftFailed(text));
        EpicCraftApi.sendDebug("CraftItemListener onCraftFailed", "(" + player.getName() + ") thiếu vật phẩm để chế tạo " + e.getItemCraft().getItemCategory().getName());
    }
}
