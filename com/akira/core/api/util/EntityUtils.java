package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

public class EntityUtils {
    public static double getMaxHealth(LivingEntity entity) {
        Validate.notNull(entity);

        AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        Validate.notNull(attribute);

        return attribute.getValue();
    }
}
