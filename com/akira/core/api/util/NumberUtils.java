package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;

import java.text.DecimalFormat;

public class NumberUtils {
    private static final DecimalFormat defaultFormatter = new DecimalFormat("0.##");

    public static Integer parseInteger(String raw) {
        Validate.notNull(raw);

        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseDouble(String raw) {
        Validate.notNull(raw);

        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static DecimalFormat getDefaultFormatter() {
        return defaultFormatter;
    }
}
