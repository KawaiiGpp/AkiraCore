package com.akira.core.api.command;

import org.apache.commons.lang3.Validate;

public class CommandArg {
    private final String text;
    private final boolean literal;

    public CommandArg(String text, boolean literal) {
        Validate.notNull(text);
        Validate.isTrue(!text.isBlank());

        this.text = text;
        this.literal = literal;
    }

    public CommandArg(String functionalText) {
        Validate.notNull(functionalText);
        Validate.isTrue(!functionalText.isBlank());

        if (functionalText.startsWith("#")) {
            String text = functionalText.substring(1);
            Validate.isTrue(!text.isBlank());

            this.text = text;
            this.literal = false;
        } else {
            this.text = functionalText;
            this.literal = true;
        }
    }

    public String getText() {
        return text;
    }

    public boolean isLiteral() {
        return literal;
    }
}
