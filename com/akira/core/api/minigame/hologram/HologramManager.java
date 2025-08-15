package com.akira.core.api.minigame.hologram;

import com.akira.core.api.Manager;
import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class HologramManager extends Manager<Hologram> {
    public void removeAllHolograms() {
        this.copySet().forEach(Hologram::remove);
    }

    public void spawnHologram(String name, Location location, String text) {
        Validate.notNull(name);
        Validate.notNull(location);
        Validate.notNull(text);
        Validate.isTrue(this.fromString(name) == null,
                "Hologram " + name + " is already registered.");

        Hologram hologram = new Hologram(name, location, text);
        this.register(hologram);
        hologram.spawn();
    }

    public void removeHologram(String name) {
        Validate.notNull(name);

        Hologram hologram = this.fromString(name);
        Validate.notNull(hologram, "Hologram " + name + " doesn't exist.");

        this.unregister(hologram);
        hologram.remove();
    }

    public Hologram fromString(String name) {
        Validate.notNull(name);
        return CommonUtils.singleMatch(copySet().stream(),
                e -> name.equals(e.getName()), false);
    }
}
