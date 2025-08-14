package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
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

    public static <T> void playEffect(Location location, Effect effect, T data) {
        playEffect(location, effect, data, 64);
    }

    public static <T> void playEffect(Location location, Effect effect, T data, int radius) {
        Validate.notNull(location);
        Validate.notNull(effect);
        Validate.isTrue(radius >= 0, "Radius cannot be lower than 0.");

        World world = location.getWorld();
        Validate.notNull(world, "World from the location instance is null.");
        world.playEffect(location, effect, data, radius);
    }

    public static <T> void playEffect(Player player, Effect effect, T data, int radius) {
        playEffect(player.getLocation(), effect, data, radius);
    }

    public static <T> void playEffect(Player player, Effect effect, T data) {
        playEffect(player.getLocation(), effect, data);
    }
}
