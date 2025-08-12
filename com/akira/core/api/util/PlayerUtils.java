package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static void playSound(Player player, Sound sound, float pitch) {
        NumberUtils.ensureLegit(pitch);
        Validate.notNull(player);
        Validate.notNull(sound);
        Validate.isTrue(pitch >= 0.0F && pitch <= 1.0F);

        World world = player.getLocation().getWorld();
        Validate.notNull(world, "World instance from player's location is null.");
        world.playSound(player, sound, 1.0F, pitch);
    }

    public static void playSound(Player player, Sound sound) {
        playSound(player, sound, 1.0F);
    }
}
