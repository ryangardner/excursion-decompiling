/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

public final class StatsCollectionState
extends Enum<StatsCollectionState> {
    private static final /* synthetic */ StatsCollectionState[] $VALUES;
    public static final /* enum */ StatsCollectionState DISABLED;
    public static final /* enum */ StatsCollectionState ENABLED;

    static {
        StatsCollectionState statsCollectionState;
        ENABLED = new StatsCollectionState();
        DISABLED = statsCollectionState = new StatsCollectionState();
        $VALUES = new StatsCollectionState[]{ENABLED, statsCollectionState};
    }

    public static StatsCollectionState valueOf(String string2) {
        return Enum.valueOf(StatsCollectionState.class, string2);
    }

    public static StatsCollectionState[] values() {
        return (StatsCollectionState[])$VALUES.clone();
    }
}

