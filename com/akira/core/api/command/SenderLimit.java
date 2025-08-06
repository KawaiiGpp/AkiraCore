package com.akira.core.api.command;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public enum SenderLimit {
    NONE(Object.class, "无限制", "该指令对发送者类型无限制。"),
    CONSOLE_ONLY(ConsoleCommandSender.class, "仅控制台", "该指令仅能通过控制台执行。"),
    PLAYER_ONLY(Player.class, "仅玩家", "该指令仅能通过玩家执行。");

    private final Class<?> senderType;
    private final String text;
    private final String deniedMessage;

    SenderLimit(Class<?> senderType, String text, String deniedMessage) {
        Validate.notNull(senderType);
        Validate.notNull(text);
        Validate.notNull(deniedMessage);

        this.senderType = senderType;
        this.text = text;
        this.deniedMessage = deniedMessage;
    }

    public boolean allow(CommandSender sender) {
        return senderType.isAssignableFrom(sender.getClass());
    }

    public Class<?> getSenderType() {
        return senderType;
    }

    public String getText() {
        return text;
    }

    public String getDeniedMessage() {
        return deniedMessage;
    }
}
