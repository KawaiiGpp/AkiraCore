package com.akira.core.api.command;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class CommandNode {
    protected final String root;
    protected final CommandArg[] arguments;
    protected final SenderLimit limit;

    public CommandNode(String root, SenderLimit limit, String rawArgs) {
        Validate.notNull(root);
        Validate.notNull(limit);

        String[] args;
        if (rawArgs != null && !rawArgs.isBlank()) {
            Validate.isTrue(!rawArgs.contains(".."));
            Validate.isTrue(!rawArgs.startsWith(".") && !rawArgs.endsWith("."));

            args = rawArgs.split("\\.");
        } else args = new String[0];

        this.root = root;
        this.arguments = Arrays.stream(args)
                .map(CommandArg::new)
                .toArray(CommandArg[]::new);
        this.limit = limit;
    }

    public void execute(CommandSender sender, String[] args) {
        Validate.isTrue(args.length == arguments.length);
        this.onExecute(sender, this.filterNonLiteralArgs(args));
    }

    public CommandArg[] getArguments() {
        return arguments.clone();
    }

    public SenderLimit getLimit() {
        return limit;
    }

    public String getRoot() {
        return root;
    }

    public String generateUsage() {
        StringBuilder builder = new StringBuilder();
        builder.append("用法：").append('/').append(root);

        for (CommandArg arg : arguments) {
            builder.append(' ');

            if (arg.isLiteral()) builder.append(arg.getText());
            else builder.append('<').append(arg.getText()).append('>');
        }

        return builder.toString();
    }

    private Set<Integer> getNonLiteralIndexes() {
        return IntStream.range(0, arguments.length)
                .filter(i -> !arguments[i].isLiteral())
                .boxed()
                .collect(Collectors.toSet());
    }

    private String[] filterNonLiteralArgs(String[] args) {
        Validate.notNull(args);
        Validate.noNullElements(args);

        Set<Integer> indexes = this.getNonLiteralIndexes();
        List<String> result = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            if (!indexes.contains(i))
                continue;

            result.add(args[i]);
        }

        return result.toArray(new String[0]);
    }

    protected abstract void onExecute(CommandSender sender, String[] args);
}