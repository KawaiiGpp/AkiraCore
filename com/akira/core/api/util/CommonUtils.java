package com.akira.core.api.util;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.function.UnsafeRunnable;
import org.apache.commons.lang3.Validate;

import java.nio.charset.StandardCharsets;
import java.util.*;
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

    public static <T extends Enum<T>> T getEnumSafely(Class<T> enumClazz, String name) {
        Validate.notNull(enumClazz);
        Validate.notNull(name);

        try {
            return Enum.valueOf(enumClazz, name);
        } catch (IllegalArgumentException e) {
            return null;
        }
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

    public static <T> List<T> getRandomElement(Collection<T> collection, int amount) {
        Validate.notNull(collection);
        NumberUtils.ensurePositive(amount);

        List<T> arrayList = new ArrayList<>(collection);
        Collections.shuffle(arrayList);

        return arrayList.subList(0, Math.min(amount, arrayList.size()));
    }

    public static <T> List<T> getRandomElement(T[] array, int amount) {
        Validate.notNull(array);
        NumberUtils.ensureLegit(amount);

        return getRandomElement(Arrays.asList(array), amount);
    }

    public static UUID createUniqueId(String string) {
        Validate.notNull(string);
        return UUID.nameUUIDFromBytes(string.getBytes(StandardCharsets.UTF_8));
    }
}
