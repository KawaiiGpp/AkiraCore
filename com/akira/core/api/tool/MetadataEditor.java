package com.akira.core.api.tool;

import org.apache.commons.lang3.Validate;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MetadataEditor {
    private final Plugin plugin;
    private final Metadatable owner;

    private MetadataEditor(Plugin plugin, Metadatable owner) {
        Validate.notNull(plugin);
        Validate.notNull(owner);

        this.plugin = plugin;
        this.owner = owner;
    }

    public void set(String key, Object o) {
        Validate.notNull(key);
        Validate.notNull(o);

        this.remove(key);
        owner.setMetadata(key, new FixedMetadataValue(plugin, o));
    }

    public void remove(String key) {
        Validate.notNull(key);

        owner.removeMetadata(key, plugin);
    }

    public MetadataValue get(String key) {
        Validate.notNull(key);

        List<MetadataValue> list = new ArrayList<>(owner.getMetadata(key));
        list.removeIf(value -> !plugin.equals(value.getOwningPlugin()));

        if (list.isEmpty()) return null;
        Validate.isTrue(list.size() == 1, "Multiple metadata values found for " + key);
        return list.get(0);
    }

    public boolean has(String key) {
        Validate.notNull(key);

        return owner.getMetadata(key)
                .stream()
                .anyMatch(value -> plugin.equals(value.getOwningPlugin()));
    }

    public static MetadataEditor create(Plugin plugin, Metadatable owner) {
        Validate.notNull(plugin);
        Validate.notNull(owner);

        return new MetadataEditor(plugin, owner);
    }
}
