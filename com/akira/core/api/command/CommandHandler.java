package com.akira.core.api.command;

import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    private final String root;
    private final List<CommandNode> nodes;

    public CommandHandler(String root) {
        Validate.notNull(root);

        this.root = root;
        this.nodes = new ArrayList<>();
    }

    public void register(CommandNode node) {
        Validate.notNull(node);
        Validate.isTrue(!nodes.contains(node), "CommandNode instance already registered.");

        nodes.add(node);
    }

    public void execute(CommandSender sender, String[] args) {
        Validate.notNull(sender);
        Validate.noNullElements(args);

        CommandNode node = this.getMatchingNode(args);
        if (node == null) {
            sender.sendMessage("§c未知指令，请使用 /" + root + " help 查看用法。");
            return;
        }

        SenderLimit limit = node.getLimit();
        if (!limit.allow(sender)) {
            sender.sendMessage("§c错误：" + limit.getDeniedMessage());
            return;
        }

        boolean success = node.execute(sender, args);
        if (!success) sender.sendMessage("§c用法：" + node.generateUsage());
    }

    public String getRoot() {
        return root;
    }

    public List<CommandNode> getNodes() {
        return new ArrayList<>(nodes);
    }

    private CommandNode getMatchingNode(String[] args) {
        Validate.notNull(args);
        return CommonUtils.singleMatch(nodes.stream(), n -> n.matches(args), false);
    }
}
