package com.akira.core.api.util;

import com.akira.core.api.command.EnhancedExecutor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;

public class BukkitUtils {
    public static void debug(Object object) {
        debug(object, true);
    }

    public static void debug(Object object, boolean bc) {
        log(ChatColor.AQUA, "DEBUG", String.valueOf(object), bc);
    }

    public static void logInfo(String message) {
        log(ChatColor.GREEN, "INFO", message);
    }

    public static void logWarn(String message) {
        log(ChatColor.YELLOW, "WARN", message);
    }

    public static void logErr(String message) {
        log(ChatColor.RED, "ERR", message);
    }

    public static void log(ChatColor color, String prefix, String message, boolean bc) {
        Validate.notNull(color);
        Validate.notNull(prefix);
        Validate.notNull(message);

        String result = color + "[Akira] [" + prefix + "] " + message;

        if (bc) bc(result);
        else Bukkit.getConsoleSender().sendMessage(result);
    }

    public static void log(ChatColor color, String prefix, String message) {
        log(color, prefix, message, false);
    }

    public static void bc(String message) {
        Validate.notNull(message);
        Bukkit.broadcastMessage(message);
    }

    public static void registerCommand(EnhancedExecutor executor) {
        Validate.notNull(executor);

        String name = executor.getName();
        PluginCommand command = Bukkit.getPluginCommand(name);
        Validate.notNull(command, "Command " + name + " doesn't exist.");

        command.setExecutor(executor);
    }
}
