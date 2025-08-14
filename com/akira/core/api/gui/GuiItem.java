package com.akira.core.api.gui;

import com.akira.core.api.item.ItemBuilder;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
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

    public static GuiItem create(ItemStack item) {
        return new GuiItem(item) {
            public ItemStack createItemFor(Player player) {
                return this.copyItem();
            }

            public void onClicked(Player player) {}
        };
    }

    public static GuiItem create(Material material, String displayName, int amount, String... lore) {
        return create(ItemBuilder.buildMenuItem(material, displayName, amount, lore));
    }

    public static GuiItem create(Material material, String displayName, String... lore) {
        return create(material, displayName, 1, lore);
    }
}
