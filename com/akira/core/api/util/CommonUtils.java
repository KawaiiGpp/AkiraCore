package com.akira.core.api.util;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.function.UnsafeRunnable;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CommonUtils {
    private static final Random random = new Random();

    public static void runUnsafe(AkiraPlugin plugin, UnsafeRunnable runnable) {
        Validate.notNull(plugin);
        Validate.notNull(runnable);

        try {
            runnable.run();
        } catch (Exception e) {
            plugin.logErr("捕获到意料之外的异常：");
            e.printStackTrace();
        }
    }

    public static void runUnsafe(UnsafeRunnable runnable) {
        Validate.notNull(runnable);

        try {
            runnable.run();
        } catch (Exception e) {
            BukkitUtils.logErr("捕获到意料之外的异常：");
            e.printStackTrace();
        }
    }

    public static <T> T singleMatch(Stream<T> stream, Predicate<T> predicate, boolean notNull) {
        Validate.notNull(stream);
        Validate.notNull(predicate);

        List<T> list = stream.filter(predicate).toList();
        Validate.isTrue(list.size() <= 1, "Expected a single matching element, but found multiple.");

        T result = list.isEmpty() ? null : list.get(0);
        if (notNull) Validate.notNull(result, "No matching elements found.");

        return result;
    }

    public static <T> T singleMatch(Stream<T> stream, Predicate<T> predicate) {
        return singleMatch(stream, predicate, true);
    }

    public static <T> T requireNonNull(T t) {
        return Objects.requireNonNull(t, "Argument must not be null.");
    }

    public static String generateLine(int length) {
        Validate.isTrue(length > 0, "Length must be greater than 0.");
        StringBuilder builder = new StringBuilder();
        IntStream.range(0, length).forEach(i -> builder.append('▬'));
        return builder.toString();
    }

    public static Random getRandom() {
        return random;
    }

    public static boolean rollChance(int chance) {
        Validate.isTrue(chance >= 0 && chance <= 100);

        if (chance == 0) return false;
        if (chance == 100) return true;

        return random.nextInt(100) < chance;
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
