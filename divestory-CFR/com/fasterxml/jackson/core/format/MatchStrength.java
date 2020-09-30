/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.format;

public final class MatchStrength
extends Enum<MatchStrength> {
    private static final /* synthetic */ MatchStrength[] $VALUES;
    public static final /* enum */ MatchStrength FULL_MATCH;
    public static final /* enum */ MatchStrength INCONCLUSIVE;
    public static final /* enum */ MatchStrength NO_MATCH;
    public static final /* enum */ MatchStrength SOLID_MATCH;
    public static final /* enum */ MatchStrength WEAK_MATCH;

    static {
        MatchStrength matchStrength;
        NO_MATCH = new MatchStrength();
        INCONCLUSIVE = new MatchStrength();
        WEAK_MATCH = new MatchStrength();
        SOLID_MATCH = new MatchStrength();
        FULL_MATCH = matchStrength = new MatchStrength();
        $VALUES = new MatchStrength[]{NO_MATCH, INCONCLUSIVE, WEAK_MATCH, SOLID_MATCH, matchStrength};
    }

    public static MatchStrength valueOf(String string2) {
        return Enum.valueOf(MatchStrength.class, string2);
    }

    public static MatchStrength[] values() {
        return (MatchStrength[])$VALUES.clone();
    }
}

