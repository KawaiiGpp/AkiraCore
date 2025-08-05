package com.akira.core.utils.tool;

import com.akira.core.utils.base.AkiraPlugin;
import com.akira.core.utils.func.UnsafeRunnable;
import org.apache.commons.lang3.Validate;

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
}
