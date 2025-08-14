package com.akira.core.api.gui;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public abstract class Gui implements InventoryHolder {
    protected final String name;
    protected final String title;
    protected final int size;
    protected final Map<Integer, GuiItem> itemMap;

    public Gui(String name, String title, int rows) {
        Validate.notNull(name);
        Validate.notNull(title);
        Validate.isTrue(rows > 0 && rows <= 6);

        this.name = name;
        this.title = title;
        this.size = rows * 9;
        this.itemMap = new HashMap<>();
    }

    public final void setItem(int slot, GuiItem item) {
        this.checkSlot(slot);

        if (item == null) itemMap.remove(slot);
        else itemMap.put(slot, item);
    }

    public final GuiItem getItem(int slot) {
        this.checkSlot(slot);

        return itemMap.get(slot);
    }

    public final void open(Player target) {
        Validate.notNull(target);
        target.openInventory(this.createInventory(target));
    }

    public final void click(int slot, Player clicker) {
        this.checkSlot(slot);
        Validate.notNull(clicker);

        GuiItem item = this.getItem(slot);
        if (item == null) return;

        item.onClicked(clicker);
    }

    public final Inventory getInventory() {
        throw new UnsupportedOperationException("Failed getting an inventory from Gui.");
    }

    public final String getName() {
        return name;
    }

    public final String getTitle() {
        return title;
    }

    public final int getSize() {
        return size;
    }

    public final boolean matches(Inventory inventory) {
        Validate.notNull(inventory);
        return this.equals(inventory.getHolder());
    }

    private Inventory createInventory(Player player) {
        Validate.notNull(player);

        Inventory inventory = Bukkit.createInventory(this, size, title);
        itemMap.forEach((slot, item) -> inventory.setItem(slot, item.createItemFor(player)));
        return inventory;
    }

    private void checkSlot(int slot) {
        Validate.isTrue(slot >= 0 && slot < size);
    }
}
