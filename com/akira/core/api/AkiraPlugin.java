package com.akira.core.api;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AkiraPlugin extends JavaPlugin {
    public final void logInfo(String message) {
        log("INFO", ChatColor.GREEN, message);
    }

    public final void logWarn(String message) {
        log("WARN", ChatColor.YELLOW, message);
    }

    public final void logErr(String message) {
        log("ERR", ChatColor.RED, message);
    }

    protected final void log(String prefix, ChatColor color, String message) {
        Validate.notNull(prefix);
        Validate.notNull(color);
        Validate.notNull(message);

        String tag = color + "[" + this.getName() + "] [" + prefix + "] ";
        Bukkit.getConsoleSender().sendMessage(tag + message);
    }

    public void onLoad() {
        logInfo("欢迎使用 " + this.getName() + "，插件正在加载。");
    }

    public void onEnable() {
        PluginDescriptionFile desc = this.getDescription();
        String authors = String.join(",", desc.getAuthors());

        logInfo("插件 " + this.getName() + " 已成功启用。");
        logInfo("作者：" + authors + "，版本：" + desc.getVersion());
    }

    public void onDisable() {
        logInfo("插件 " + this.getName() + " 已卸载。");
        logInfo("感谢你的使用，期待我们下次见面！");
    }
}
