package com.akira.core.api.gui;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class GuiListener implements Listener {
    protected final GuiManager manager;

    public GuiListener(GuiManager manager) {
        Validate.notNull(manager);
        this.manager = manager;
    }

    @EventHandler
    public final void onClick(InventoryClickEvent e) {
        if (!this.shouldHandleEvent(e)) return;
        if (!(e.getWhoClicked() instanceof Player player)) return;

        Inventory inventory = e.getInventory();
        Gui gui = manager.fromInventory(inventory);
        if (gui == null) return;

        e.setCancelled(true);
        if (!inventory.equals(e.getClickedInventory())) return;
        if (!this.shouldCallGui(e)) return;

        try {
            gui.click(e.getSlot(), player);
        } catch (Exception exception) {
            this.onExceptionCaught(exception);
        }
    }

    protected abstract void onExceptionCaught(Exception exception);

    protected abstract boolean shouldHandleEvent(InventoryClickEvent event);

    protected abstract boolean shouldCallGui(InventoryClickEvent event);
}
