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
        this.registerHelpCommand();
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

    public List<String> complete(CommandSender sender, String[] args) {
        Validate.notNull(sender);
        Validate.noNullElements(args);
        Validate.isTrue(args.length > 0, "Illegal length of command args.");

        int lastIndex = args.length - 1;
        return this.getSuggestedNode(sender, args).stream()
                .map(n -> n.getArguments()[lastIndex])
                .filter(CommandArg::isLiteral)
                .map(CommandArg::getText)
                .toList();
    }

    public String getRoot() {
        return root;
    }

    public List<CommandNode> getNodes() {
        return new ArrayList<>(nodes);
    }

    private CommandNode getMatchingNode(String[] args) {
        Validate.noNullElements(args);
        return CommonUtils.singleMatch(nodes.stream(), n -> n.matches(args), false);
    }

    private List<CommandNode> getSuggestedNode(CommandSender sender, String[] args) {
        Validate.notNull(sender);
        Validate.noNullElements(args);

        return nodes.stream()
                .filter(n -> n.getLimit().allow(sender))
                .filter(n -> n.shouldSuggest(args))
                .toList();
    }

    private void registerHelpCommand() {
        CommandNode node = new CommandNode(
                root,
                SenderLimit.NONE,
                "help",
                "列出所有可用的子指令"
        ) {
            protected boolean onExecute(CommandSender sender, String[] args) {
                String line = "§8" + CommonUtils.generateLine(55);

                sender.sendMessage(line);
                sender.sendMessage("§f关于 §e/" + root + " §f的所有可用指令：");
                sender.sendMessage("");

                for (CommandNode node : nodes) {
                    if (!node.getLimit().allow(sender))
                        continue;

                    sender.sendMessage("§8- §2" + node.generateUsage());
                    sender.sendMessage("§8- >> §7" + node.getDescription());
                }

                sender.sendMessage(line);
                return true;
            }
        };

        this.register(node);
    }
}
