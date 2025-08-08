package com.akira.core;

import com.akira.core.api.AkiraPlugin;
import com.akira.core.command.AkiraCoreCommand;
import org.apache.commons.lang3.Validate;

public class AkiraCore extends AkiraPlugin {
    private static AkiraCore instance;

    public AkiraCore() {
        instance = this;
    }

    public void onEnable() {
        super.onEnable();

        registerCommand(new AkiraCoreCommand());
    }

    public static AkiraCore getInstance() {
        Validate.notNull(instance, "Plugin instance is currently null.");
        return instance;
    }
}
