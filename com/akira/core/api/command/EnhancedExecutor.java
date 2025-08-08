package com.akira.core.api.command;

import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public abstract class EnhancedExecutor implements TabExecutor {
    protected final String name;
    private final CommandHandler handler;

    public EnhancedExecutor(String name) {
        Validate.notNull(name);

        this.name = name;
        this.handler = new CommandHandler(name);
    }

    public final boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        handler.execute(sender, args);
        return true;
    }

    public final List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return handler.complete(sender, args);
    }

    public final String getName() {
        return name;
    }

    public final CommandHandler getHandler() {
        return handler;
    }

    protected final void registerNode(CommandNode node) {
        handler.register(CommonUtils.requireNonNull(node));
    }
}
