package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;
import java.util.function.Predicate;

public class EntityUtils {
    public static double getMaxHealth(LivingEntity entity) {
        Validate.notNull(entity);

        return getNonNullAttribute(entity, Attribute.GENERIC_MAX_HEALTH).getValue();
    }

    public static void setMaxHealth(LivingEntity entity, double maxHealth, boolean heal) {
        Validate.notNull(entity);
        NumberUtils.ensurePositive(maxHealth);

        getNonNullAttribute(entity, Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        if (heal) entity.setHealth(maxHealth);
    }

    public static void setMaxHealth(LivingEntity entity, double maxHealth) {
        setMaxHealth(entity, maxHealth, true);
    }

    public static AttributeInstance getNonNullAttribute(LivingEntity entity, Attribute type) {
        Validate.notNull(entity);
        Validate.notNull(type);

        AttributeInstance attribute = entity.getAttribute(type);
        Validate.notNull(attribute, "Attribute " + type.name() + " is not available for " + entity.getType().name());

        return attribute;
    }

    public static AttributeModifier createModifier(String name, double value, Operation operation, EquipmentSlot slot) {
        Validate.notNull(name);
        Validate.notNull(operation);

        return new AttributeModifier(CommonUtils.createUniqueId(name), name, value, operation, slot);
    }

    public static AttributeModifier createModifier(String name, double value, Operation operation) {
        return createModifier(name, value, operation, null);
    }

    public static AttributeModifier createModifier(String name, double value) {
        return createModifier(name, value, Operation.ADD_NUMBER);
    }

    public static void removeModifier(LivingEntity entity, Attribute type, String name) {
        Validate.notNull(entity);
        Validate.notNull(type);
        Validate.notNull(name);

        AttributeModifier modifier = getModifier(entity, type, name);
        Validate.notNull(modifier, "Cannot find modifier " + name + " for " + type.name());
        removeModifier(entity, type, modifier);
    }

    public static void removeModifier(LivingEntity entity, Attribute type, UUID uniqueId) {
        Validate.notNull(entity);
        Validate.notNull(type);
        Validate.notNull(uniqueId);

        AttributeModifier modifier = getModifier(entity, type, uniqueId);
        Validate.notNull(modifier, "Cannot find modifier " + uniqueId + " for " + type.name());
        removeModifier(entity, type, modifier);
    }

    public static void removeModifier(LivingEntity entity, Attribute type, AttributeModifier modifier) {
        Validate.notNull(entity);
        Validate.notNull(type);
        Validate.notNull(modifier);

        getNonNullAttribute(entity, type).removeModifier(modifier);
    }

    public static void addModifier(LivingEntity entity, Attribute type, AttributeModifier modifier) {
        Validate.notNull(entity);
        Validate.notNull(type);
        Validate.notNull(modifier);

        getNonNullAttribute(entity, type).addModifier(modifier);
    }

    public static boolean hasModifier(LivingEntity entity, Attribute type, String name) {
        return getModifier(entity, type, name) != null;
    }

    public static boolean hasModifier(LivingEntity entity, Attribute type, UUID uniqueId) {
        return getModifier(entity, type, uniqueId) != null;
    }

    public static AttributeModifier getModifier(LivingEntity entity, Attribute type, String name) {
        Validate.notNull(name);

        return getModifier(entity, type, CommonUtils.createUniqueId(name));
    }

    public static AttributeModifier getModifier(LivingEntity entity, Attribute type, UUID uniqueId) {
        Validate.notNull(entity);
        Validate.notNull(type);
        Validate.notNull(uniqueId);

        return CommonUtils.singleMatch(getNonNullAttribute(entity, type).getModifiers().stream(),
                modifier -> uniqueId.equals(modifier.getUniqueId()), false);
    }

    public static boolean removeIf(Entity entity, Predicate<Entity> predicate) {
        Validate.notNull(entity);
        Validate.notNull(predicate);

        if (!predicate.test(entity))
            return false;

        entity.remove();
        return true;
    }

    public static boolean removeIfValid(Entity entity) {
        return removeIf(entity, Entity::isValid);
    }

    public static boolean removeIfDead(Entity entity) {
        return removeIf(entity, e -> !e.isValid() || e.isDead());
    }
}
