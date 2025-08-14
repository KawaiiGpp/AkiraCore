package com.akira.core.api.config;

import com.akira.core.api.Manager;
import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;

public class ConfigManager extends Manager<ConfigFile> {
    public void initializeAll() {
        elements.forEach(ConfigFile::initialize);
    }

    public void saveAll() {
        elements.forEach(ConfigFile::save);
    }

    public ConfigFile fromString(String name) {
        Validate.notNull(name);
        return CommonUtils.singleMatch(elements.stream(),
                e -> name.equals(e.getName()));
    }
}
