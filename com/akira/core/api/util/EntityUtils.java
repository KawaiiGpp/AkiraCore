package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;
import java.util.stream.Stream;

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

    public static boolean removeModifier(LivingEntity entity, Attribute type, String name) {
        Validate.notNull(entity);
        Validate.notNull(type);
        Validate.notNull(name);

        UUID uniqueId = CommonUtils.createUniqueId(name);
        AttributeInstance instance = getNonNullAttribute(entity, type);

        Stream<AttributeModifier> stream = instance.getModifiers().stream();
        AttributeModifier modifier = CommonUtils.singleMatch(stream,
                m -> uniqueId.equals(m.getUniqueId()), false);

        if (modifier != null) {
            instance.removeModifier(modifier);
            return true;
        } else return false;
    }


}
