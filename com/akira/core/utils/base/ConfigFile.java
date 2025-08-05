package com.akira.core.utils.base;

import com.akira.core.utils.tool.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@SuppressWarnings("ResultOfMethodCallIgnored")
public abstract class ConfigFile {
    protected final AkiraPlugin plugin;
    protected final String name;
    protected final File file;

    private YamlConfiguration config;
    private String templatePath;

    public ConfigFile(AkiraPlugin plugin, String name) {
        Validate.notNull(plugin);
        Validate.notNull(name);

        File folder = plugin.getDataFolder();
        this.file = new File(folder, name + ".yml");
        this.name = name;
        this.plugin = plugin;
    }

    public ConfigFile(AkiraPlugin plugin, String name, String templatePath) {
        this(plugin, name);

        Validate.notNull(templatePath);
        this.templatePath = templatePath;
    }

    public final void load() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public final void save() {
        CommonUtils.runUnsafe(plugin, () -> config.save(file));
    }

    public final void initialize() {
        File folder = plugin.getDataFolder();

        if (!folder.exists())
            folder.mkdirs();
        if (!file.exists())
            CommonUtils.runUnsafe(file::createNewFile);

        this.load();
        if (templatePath == null) return;
        this.applyTemplate();
    }

    public final String getName() {
        return name;
    }

    protected final YamlConfiguration getConfig() {
        Validate.notNull(config, "Config instance is not initialized.");
        return config;
    }

    public final boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ConfigFile that = (ConfigFile) object;
        return Objects.equals(plugin, that.plugin) && Objects.equals(name, that.name);
    }

    public final int hashCode() {
        return Objects.hash(plugin, name);
    }

    private void applyTemplate() {
        Validate.notNull(templatePath);
        Validate.notNull(config);

        String fullPath = templatePath + "/" + name + ".yml";
        InputStream input = plugin.getResource(fullPath);
        Validate.notNull(input, "Cannot find the template file at " + fullPath);

        InputStreamReader reader = new InputStreamReader(input);
        config.addDefaults(YamlConfiguration.loadConfiguration(reader));
        config.options().copyDefaults(true);

        CommonUtils.runUnsafe(reader::close);
    }
}
