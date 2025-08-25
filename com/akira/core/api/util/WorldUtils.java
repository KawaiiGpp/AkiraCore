package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.*;

public class WorldUtils {
    public static void playSound(Location location, Sound sound, float volume, float pitch) {
        NumberUtils.ensureLegit(pitch);
        NumberUtils.ensureNonNegative(volume);
        Validate.notNull(location);
        Validate.notNull(sound);
        Validate.isTrue(pitch >= 0.5F && pitch <= 2.0F);

        World world = CommonUtils.requireNonNull(location.getWorld());
        world.playSound(location, sound, volume, pitch);
    }

    public static void playSound(Location location, Sound sound, float pitch) {
        playSound(location, sound, 1.0F, pitch);
    }

    public static void playSound(Location location, Sound sound) {
        playSound(location, sound, 1.0F);
    }

    public static void playParticle(Location location, Particle particle, int amount, double oX, double oY, double oZ, Object data) {
        Validate.notNull(location);
        Validate.notNull(particle);
        NumberUtils.ensurePositive(amount);
        NumberUtils.ensureLegit(oX);
        NumberUtils.ensureLegit(oY);
        NumberUtils.ensureLegit(oZ);

        World world = CommonUtils.requireNonNull(location.getWorld());
        world.spawnParticle(particle, location, amount, oX, oY, oZ, data);
    }

    public static void playParticle(Location location, Particle particle, int amount, double offset, Object data) {
        playParticle(location, particle, amount, offset, offset, offset, data);
    }

    public static void playParticle(Location location, Particle particle, int amount, double offset) {
        playParticle(location, particle, amount, offset, null);
    }

    public static void playParticle(Location location, Particle particle, int amount) {
        playParticle(location, particle, amount, 0);
    }

    public static void playParticle(Location location, Particle particle) {
        playParticle(location, particle, 1);
    }

    public static void ensureLegitLocation(Location location) {
        Validate.notNull(location);
        Validate.notNull(location.getWorld());

        NumberUtils.ensureLegit(location.getX());
        NumberUtils.ensureLegit(location.getY());
        NumberUtils.ensureLegit(location.getZ());
        NumberUtils.ensureLegit(location.getYaw());
        NumberUtils.ensureLegit(location.getPitch());
    }

    public static String serializeLocation(Location location) {
        Validate.notNull(location);
        ensureLegitLocation(location);

        StringBuilder builder = new StringBuilder();
        World world = CommonUtils.requireNonNull(location.getWorld());

        builder.append(world.getName()).append(',')
                .append(location.getX()).append(',')
                .append(location.getY()).append(',')
                .append(location.getZ()).append(',')
                .append(location.getYaw()).append(',')
                .append(location.getPitch());

        return builder.toString();
    }

    public static Location deserializeLocation(String raw) {
        Validate.notNull(raw);

        String[] rawArray = raw.split(",");
        Validate.isTrue(rawArray.length == 6, "Incorrect format: " + raw);

        try {
            World world = Bukkit.getWorld(rawArray[0]);
            double x = Double.parseDouble(rawArray[1]);
            double y = Double.parseDouble(rawArray[2]);
            double z = Double.parseDouble(rawArray[3]);
            float yaw = Float.parseFloat(rawArray[4]);
            float pitch = Float.parseFloat(rawArray[5]);

            Validate.notNull(world);
            NumberUtils.ensureLegit(x);
            NumberUtils.ensureLegit(y);
            NumberUtils.ensureLegit(z);
            NumberUtils.ensureLegit(yaw);
            NumberUtils.ensureLegit(pitch);

            return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed parsing location: " + raw);
        }
    }
}
