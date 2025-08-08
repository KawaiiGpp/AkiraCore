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
    protected final String description;

    public CommandNode(String root, SenderLimit limit, String rawArgs, String description) {
        Validate.notNull(root);
        Validate.notNull(limit);
        Validate.notNull(description);

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
        this.description = description;
    }

    public final boolean execute(CommandSender sender, String[] args) {
        Validate.isTrue(args.length == arguments.length);
        return this.onExecute(sender, this.filterNonLiteralArgs(args));
    }

    public final CommandArg[] getArguments() {
        return arguments.clone();
    }

    public final SenderLimit getLimit() {
        return limit;
    }

    public final String getRoot() {
        return root;
    }

    public final String getDescription() {
        return description;
    }

    public final String generateUsage() {
        StringBuilder builder = new StringBuilder();
        builder.append('/').append(root);

        for (CommandArg arg : arguments) {
            builder.append(' ');
            builder.append(arg.getFormattedText());
        }

        return builder.toString();
    }

    public final boolean matches(String[] args) {
        Validate.noNullElements(args);

        if (args.length != arguments.length)
            return false;

        return IntStream.range(0, arguments.length)
                .filter(i -> arguments[i].isLiteral())
                .allMatch(i -> arguments[i].getText().equals(args[i]));
    }

    public final boolean shouldSuggest(String[] args) {
        Validate.noNullElements(args);

        if (args.length > arguments.length)
            return false;

        for (int i = 0; i < args.length; i++) {
            CommandArg commandArg = arguments[i];
            if (!commandArg.isLiteral()) continue;

            String arg = args[i];
            String argument = commandArg.getText();

            if (i != args.length - 1) {
                if (!argument.equals(arg))
                    return false;
            } else {
                if (!argument.startsWith(arg))
                    return false;
            }
        }

        return true;
    }

    private Set<Integer> getNonLiteralIndexes() {
        return IntStream.range(0, arguments.length)
                .filter(i -> !arguments[i].isLiteral())
                .boxed()
                .collect(Collectors.toSet());
    }

    private String[] filterNonLiteralArgs(String[] args) {
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

    protected abstract boolean onExecute(CommandSender sender, String[] args);
}