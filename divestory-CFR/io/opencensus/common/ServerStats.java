/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.AutoValue_ServerStats;

public abstract class ServerStats {
    ServerStats() {
    }

    public static ServerStats create(long l, long l2, byte by) {
        if (l < 0L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'getLbLatencyNs' is less than zero: ");
            stringBuilder.append(l);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (l2 >= 0L) {
            return new AutoValue_ServerStats(l, l2, by);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'getServiceLatencyNs' is less than zero: ");
        stringBuilder.append(l2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public abstract long getLbLatencyNs();

    public abstract long getServiceLatencyNs();

    public abstract byte getTraceOption();
}

