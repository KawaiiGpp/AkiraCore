package com.akira.core.managers;

import com.akira.core.utils.base.ConfigFile;
import com.akira.core.utils.base.Manager;
import com.akira.core.utils.tool.CommonUtils;
import org.apache.commons.lang3.Validate;

public class ConfigManager extends Manager<ConfigFile> {
    public void initializeAll() {
        elements.forEach(ConfigFile::initialize);
    }

    public void saveAll() {
        elements.forEach(ConfigFile::save);
    }

    public ConfigFile fromString(String string) {
        Validate.notNull(string);
        return CommonUtils.singleMatch(elements.stream(),
                e -> string.equals(e.getName()));
    }
}
