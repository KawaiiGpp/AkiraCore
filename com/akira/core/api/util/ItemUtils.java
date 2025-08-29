package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemUtils {
    public static ItemStack createDyedArmor(Material material, Color color) {
        Validate.notNull(material);
        Validate.notNull(color);

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        Validate.notNull(itemMeta);
        Validate.isTrue(itemMeta instanceof LeatherArmorMeta,
                "Material must be a piece of leather armors: " + material.name());
        LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;

        meta.setColor(color);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createDyedHelmet(Color color) {
        return createDyedArmor(Material.LEATHER_HELMET, color);
    }

    public static ItemStack createDyedChestplate(Color color) {
        return createDyedArmor(Material.LEATHER_CHESTPLATE, color);
    }

    public static ItemStack createDyedLeggings(Color color) {
        return createDyedArmor(Material.LEATHER_LEGGINGS, color);
    }

    public static ItemStack createDyedBoots(Color color) {
        return createDyedArmor(Material.LEATHER_BOOTS, color);
    }
}
