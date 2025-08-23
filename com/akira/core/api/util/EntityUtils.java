package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityUtils {
    public static double getMaxHealth(LivingEntity entity) {
        Validate.notNull(entity);

        return getNonNullAttribute(entity, Attribute.GENERIC_MAX_HEALTH).getValue();
    }

    public static void setMaxHealth(LivingEntity entity, double maxHealth) {
        Validate.notNull(entity);
        NumberUtils.ensurePositive(maxHealth);

        getNonNullAttribute(entity, Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
    }

    public static AttributeInstance getNonNullAttribute(LivingEntity entity, Attribute type) {
        Validate.notNull(entity);
        Validate.notNull(type);

        AttributeInstance attribute = entity.getAttribute(type);
        Validate.notNull(attribute, "Attribute " + type.name() + " is not available for " + entity.getType().name());

        return attribute;
    }

    public static void playParticle(Location location, Particle particle, int amount) {
        Validate.notNull(location);
        Validate.notNull(particle);
        NumberUtils.ensurePositive(amount);

        World world = CommonUtils.requireNonNull(location.getWorld());
        world.spawnParticle(particle, location, amount);
    }

    public static void playParticle(Location location, Particle particle) {
        playParticle(location, particle, 1);
    }

    public static void playParticle(Entity entity, Particle particle, int amount) {
        Validate.notNull(entity);

        playParticle(entity.getLocation(), particle, amount);
    }

    public static void playParticle(Entity entity, Particle particle) {
        Validate.notNull(entity);

        playParticle(entity.getLocation(), particle);
    }
}
