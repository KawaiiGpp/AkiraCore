package com.akira.core.api.gui;

import com.akira.core.api.Manager;
import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.inventory.Inventory;

public class GuiManager extends Manager<Gui> {
    public Gui fromString(String name) {
        Validate.notNull(name);
        return CommonUtils.singleMatch(copySet().stream(),
                e -> name.equals(e.getName()));
    }

    public Gui fromInventory(Inventory inventory) {
        Validate.notNull(inventory);
        return CommonUtils.singleMatch(copySet().stream(),
                e -> e.matches(inventory), false);
    }
}
