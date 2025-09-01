package com.akira.core.api.util;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtils {
    private static final DecimalFormat defaultFormatter = new DecimalFormat("#,##0.##");

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
            return requireLegit(Double.parseDouble(raw));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static DecimalFormat getDefaultFormatter() {
        return defaultFormatter;
    }

    public static String format(double d) {
        ensureLegit(d);
        return defaultFormatter.format(d);
    }

    public static double simplify(double d, int scale) {
        ensureLegit(d);
        ensurePositive(scale);

        return BigDecimal.valueOf(d)
                .setScale(scale, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static double simplify(double d) {
        return simplify(d, 2);
    }

    public static float simplify(float f, int scale) {
        ensureLegit(f);
        ensurePositive(scale);

        return BigDecimal.valueOf(f)
                .setScale(scale, RoundingMode.HALF_UP)
                .floatValue();
    }

    public static float simplify(float f) {
        return simplify(f, 2);
    }

    public static void ensureLegit(double d) {
        Validate.notNaN(d, "Argument must not be NaN.");
        Validate.isTrue(!Double.isInfinite(d), "Argument must not be Infinite.");
    }

    public static double requireLegit(double d) {
        ensureLegit(d);
        return d;
    }

    public static int clamp(int num, int min, int max) {
        Validate.isTrue(min < max);
        return Math.min(Math.max(min, num), max);
    }

    public static double clamp(double num, double min, double max) {
        ensureLegit(num);
        ensureLegit(min);
        ensureLegit(max);

        Validate.isTrue(min < max);
        return Math.min(Math.max(min, num), max);
    }

    public static void ensurePositive(double d) {
        ensureLegit(d);
        Validate.isTrue(d > 0, "Argument must be greater than 0.");
    }

    public static void ensureNonNegative(double d) {
        ensureLegit(d);
        Validate.isTrue(d >= 0, "Argument must not be lower than 0.");
    }
}
