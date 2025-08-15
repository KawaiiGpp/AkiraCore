package com.akira.core.api.minigame.levelling;

import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;

public abstract class LevelData {
    protected int level;
    protected double exp;

    public final int getLevel() {
        return level;
    }

    public final double getExp() {
        return exp;
    }

    public final void setLevel(int level) {
        validateLegitLevel(level);
        this.level = level;
    }

    public final void setExp(double exp) {
        validateLegitExp(exp);
        this.exp = exp;
    }

    public abstract int getMaxLevel();

    public abstract double getMaxExp();

    protected final void validateLegitLevel(int level) {
        NumberUtils.ensurePositive(level);
        Validate.isTrue(exp <= this.getMaxLevel(), "Level must not be greater than Max Level.");
    }

    protected final void validateLegitExp(double exp) {
        NumberUtils.ensureLegit(exp);
        NumberUtils.ensureNonNegative(exp);
        Validate.isTrue(exp < this.getMaxExp(), "Exp must be lower than Max Exp.");
    }
}
