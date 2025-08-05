package com.akira.core.api.util;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.api.function.UnsafeRunnable;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CommonUtils {
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
}
