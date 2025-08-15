package com.akira.core.api.minigame.hologram;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class Hologram {
    private final String name;
    private final String text;
    private final Location location;
    private final World world;
    private UUID uniqueId;

    public Hologram(String name, Location location, String text) {
        Validate.notNull(name);
        Validate.notNull(location);
        Validate.notNull(text);
        Validate.notNull(location.getWorld());

        this.name = name;
        this.location = location;
        this.world = location.getWorld();
        this.text = text;
    }

    public void spawn() {
        Validate.isTrue(!this.isSpawned(), "Hologram is already spawned.");
        ArmorStand entity = world.spawn(location, ArmorStand.class);

        entity.setVisible(false);
        entity.setGravity(false);
        entity.setCustomName(text);
        entity.setCustomNameVisible(true);
        entity.setSmall(true);
        entity.setMarker(true);
        entity.setInvulnerable(true);
        entity.setCollidable(false);
        entity.setSilent(true);
        entity.setRemoveWhenFarAway(false);

        this.uniqueId = entity.getUniqueId();
    }

    public void remove() {
        Validate.isTrue(this.isSpawned(), "Hologram was not spawned.");

        LivingEntity entity = world.getLivingEntities().stream()
                .filter(e -> uniqueId.equals(e.getUniqueId()))
                .findFirst()
                .orElse(null);
        Validate.notNull(entity, "Cannot find the entity using its unique id.");
        Validate.isTrue(!entity.isDead(), "Entity for the hologram is dead.");

        entity.remove();
        this.uniqueId = null;
    }

    public boolean isSpawned() {
        return uniqueId != null;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getText() {
        return text;
    }

    public Location getLocation() {
        return location;
    }

    public World getWorld() {
        return world;
    }
}
