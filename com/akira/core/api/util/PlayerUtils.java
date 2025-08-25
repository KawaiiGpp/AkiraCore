package com.akira.core.api.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class PlayerUtils {
    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        NumberUtils.ensureLegit(pitch);
        NumberUtils.ensureNonNegative(volume);
        Validate.notNull(player);
        Validate.notNull(sound);
        Validate.isTrue(pitch >= 0.5F && pitch <= 2.0F);

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playSound(Player player, Sound sound, float pitch) {
        playSound(player, sound, 0.5F, pitch);
    }

    public static void playSound(Player player, Sound sound) {
        playSound(player, sound, 1.0F);
    }

    public static boolean isLegitName(String name) {
        Validate.notNull(name);
        return name.matches("^[A-Za-z0-9_]{3,16}$");
    }

    public static UUID getUniqueId(String name) {
        Validate.notNull(name);
        Validate.isTrue(isLegitName(name), "Invalid username.");

        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        return player.hasPlayedBefore() ? player.getUniqueId() : null;
    }

    public static void fetchUniqueId(JavaPlugin plugin, String name, Consumer<UUID> callback, Consumer<Exception> solver) {
        Validate.notNull(plugin);
        Validate.notNull(name);
        Validate.notNull(callback);
        Validate.notNull(solver);
        Validate.isTrue(isLegitName(name), "Invalid username.");

        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        BukkitScheduler scheduler = Bukkit.getScheduler();

        scheduler.runTaskAsynchronously(plugin, () -> {
            UUID result;

            try {
                result = player.getUniqueId();
            } catch (Exception exception) {
                solver.accept(exception);
                return;
            }

            scheduler.runTask(plugin, () -> callback.accept(result));
        });
    }

    public static void sendTitle(Player player, String title, String subTitle) {
        Validate.notNull(player);
        player.sendTitle(title, subTitle, 10, 80, 10);
    }

    public static void sendActionBarTitle(Player player, String title) {
        Validate.notNull(player);
        Validate.notNull(title);

        BaseComponent[] components = TextComponent.fromLegacyText(title);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
    }
}
