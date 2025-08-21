package com.akira.core.api.item;

import org.apache.commons.lang3.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemTagEditor {
    private final JavaPlugin plugin;
    private final ItemMeta meta;

    private ItemTagEditor(JavaPlugin plugin, ItemMeta meta) {
        Validate.notNull(plugin);
        Validate.notNull(meta);

        this.plugin = plugin;
        this.meta = meta;
    }

    public boolean hasKey(String key, PersistentDataType<?, ?> dataType) {
        Validate.notNull(key);
        Validate.notNull(dataType);

        return meta.getPersistentDataContainer().has(generateKey(key), dataType);
    }

    public void removeKey(String key) {
        Validate.notNull(key);
        meta.getPersistentDataContainer().remove(generateKey(key));
    }

    public <T> void set(String key, PersistentDataType<?, T> dataType, T value) {
        Validate.notNull(key);
        Validate.notNull(dataType);
        Validate.notNull(value);

        meta.getPersistentDataContainer().set(generateKey(key), dataType, value);
    }

    public <T> T get(String key, PersistentDataType<?, T> dataType) {
        Validate.notNull(key);
        Validate.notNull(dataType);

        return meta.getPersistentDataContainer().get(generateKey(key), dataType);
    }

    public void apply(ItemStack item) {
        Validate.notNull(item);
        item.setItemMeta(meta);
    }

    private NamespacedKey generateKey(String key) {
        Validate.notNull(key);
        return new NamespacedKey(plugin, key);
    }

    public static ItemTagEditor forItemMeta(JavaPlugin plugin, ItemStack item) {
        Validate.notNull(plugin);
        Validate.notNull(item);

        return new ItemTagEditor(plugin, item.getItemMeta());
    }
}
