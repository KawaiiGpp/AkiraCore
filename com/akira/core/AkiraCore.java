package com.akira.core;

import com.akira.core.utils.base.AkiraPlugin;
import org.apache.commons.lang3.Validate;

public class AkiraCore extends AkiraPlugin {
    private static AkiraCore instance;

    public AkiraCore() {
        instance = this;
    }

    public static AkiraCore getInstance() {
        Validate.notNull(instance, "Plugin instance is currently null.");
        return instance;
    }
}
