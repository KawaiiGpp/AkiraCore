package com.akira.core.command;

import com.akira.core.AkiraCore;
import com.akira.core.api.command.CommandNode;
import com.akira.core.api.command.EnhancedExecutor;
import com.akira.core.api.command.SenderLimit;
import com.akira.core.api.util.CommonUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class AkiraCoreCommand extends EnhancedExecutor {
    public AkiraCoreCommand() {
        super("akiracore");
        this.registerNode(new About());
    }

    private class About extends CommandNode {
        private About() {
            super(name, SenderLimit.NONE, null, "关于插件的版本、作者");
        }

        protected boolean onExecute(CommandSender sender, String[] args) {
            String line = CommonUtils.generateLine(25);
            PluginDescriptionFile desc = AkiraCore.getInstance().getDescription();

            String name = desc.getName();
            String authors = String.join(", ", desc.getAuthors());
            String ver = desc.getVersion();

            sender.sendMessage("§8" + line);
            sender.sendMessage("§f插件名：§d" + name);
            sender.sendMessage("§f作者：§a" + authors);
            sender.sendMessage("§f版本：§a" + ver);
            sender.sendMessage("§8" + line);
            return true;
        }
    }
}
