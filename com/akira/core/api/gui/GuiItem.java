package com.akira.core.api.gui;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GuiItem {
    private final ItemStack item;

    public GuiItem(ItemStack item) {
        Validate.notNull(item);
        this.item = item;
    }

    protected final ItemStack copyItem() {
        return item.clone();
    }

    public abstract ItemStack createItemFor(Player player);

    public abstract void onClicked(Player player);
}
