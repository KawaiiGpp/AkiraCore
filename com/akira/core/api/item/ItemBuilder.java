package com.akira.core.api.item;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        Validate.notNull(material);
        Validate.isTrue(material.isItem(), "Unobtainable material: " + material);

        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setAmount(int amount) {
        Validate.isTrue(amount > 0);
        Validate.isTrue(amount <= item.getMaxStackSize(), "Max stack size limited: " + amount);

        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        Validate.notNull(name);

        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        Validate.noNullElements(lore);

        meta.setLore(new ArrayList<>(Arrays.asList(lore)));
        return this;
    }

    public ItemBuilder addLore(String line) {
        Validate.notNull(line);

        List<String> original = meta.getLore();
        List<String> lore = original == null ? new ArrayList<>() : original;

        lore.add(line);
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        Validate.noNullElements(flags);

        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        Validate.notNull(enchantment);
        Validate.isTrue(level > 0);

        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemStack getResult() {
        ItemStack result = item.clone();

        result.setItemMeta(meta);
        return result;
    }
}
