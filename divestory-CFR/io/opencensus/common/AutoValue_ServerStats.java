/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.ServerStats;

final class AutoValue_ServerStats
extends ServerStats {
    private final long lbLatencyNs;
    private final long serviceLatencyNs;
    private final byte traceOption;

    AutoValue_ServerStats(long l, long l2, byte by) {
        this.lbLatencyNs = l;
        this.serviceLatencyNs = l2;
        this.traceOption = by;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ServerStats)) return false;
        if (this.lbLatencyNs != ((ServerStats)(object = (ServerStats)object)).getLbLatencyNs()) return false;
        if (this.serviceLatencyNs != ((ServerStats)object).getServiceLatencyNs()) return false;
        if (this.traceOption != ((ServerStats)object).getTraceOption()) return false;
        return bl;
    }

    @Override
    public long getLbLatencyNs() {
        return this.lbLatencyNs;
    }

    @Override
    public long getServiceLatencyNs() {
        return this.serviceLatencyNs;
    }

    @Override
    public byte getTraceOption() {
        return this.traceOption;
    }

    public int hashCode() {
        long l = 1000003;
        long l2 = this.lbLatencyNs;
        l = (int)(l ^ (l2 ^ l2 >>> 32)) * 1000003;
        l2 = this.serviceLatencyNs;
        int n = (int)(l ^ (l2 ^ l2 >>> 32));
        return this.traceOption ^ n * 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServerStats{lbLatencyNs=");
        stringBuilder.append(this.lbLatencyNs);
        stringBuilder.append(", serviceLatencyNs=");
        stringBuilder.append(this.serviceLatencyNs);
        stringBuilder.append(", traceOption=");
        stringBuilder.append(this.traceOption);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

