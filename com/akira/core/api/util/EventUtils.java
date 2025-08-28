package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.event.Cancellable;

public class EventUtils {
    public static boolean cancelUnless(boolean condition, Cancellable event, Runnable runnable) {
        Validate.notNull(event);

        if (condition)
            return false;

        if (runnable != null)
            runnable.run();
        event.setCancelled(true);
        return true;
    }

    public static boolean cancelUnless(boolean condition, Cancellable event) {
        return cancelUnless(condition, event, null);
    }

    public static boolean cancelIf(boolean condition, Cancellable event, Runnable runnable) {
        return cancelUnless(!condition, event, runnable);
    }

    public static boolean cancelIf(boolean condition, Cancellable event) {
        return cancelIf(condition, event, null);
    }

    public static boolean cancelIfNull(Object nullable, Cancellable event, Runnable runnable) {
        return cancelIf(nullable == null, event, runnable);
    }

    public static boolean cancelIfNull(Object nullable, Cancellable event) {
        return cancelIfNull(nullable, event, null);
    }
}
