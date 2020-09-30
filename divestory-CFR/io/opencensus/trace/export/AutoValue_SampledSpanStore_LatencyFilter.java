/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.export.SampledSpanStore;

final class AutoValue_SampledSpanStore_LatencyFilter
extends SampledSpanStore.LatencyFilter {
    private final long latencyLowerNs;
    private final long latencyUpperNs;
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_SampledSpanStore_LatencyFilter(String string2, long l, long l2, int n) {
        if (string2 == null) throw new NullPointerException("Null spanName");
        this.spanName = string2;
        this.latencyLowerNs = l;
        this.latencyUpperNs = l2;
        this.maxSpansToReturn = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SampledSpanStore.LatencyFilter)) return false;
        if (!this.spanName.equals(((SampledSpanStore.LatencyFilter)(object = (SampledSpanStore.LatencyFilter)object)).getSpanName())) return false;
        if (this.latencyLowerNs != ((SampledSpanStore.LatencyFilter)object).getLatencyLowerNs()) return false;
        if (this.latencyUpperNs != ((SampledSpanStore.LatencyFilter)object).getLatencyUpperNs()) return false;
        if (this.maxSpansToReturn != ((SampledSpanStore.LatencyFilter)object).getMaxSpansToReturn()) return false;
        return bl;
    }

    @Override
    public long getLatencyLowerNs() {
        return this.latencyLowerNs;
    }

    @Override
    public long getLatencyUpperNs() {
        return this.latencyUpperNs;
    }

    @Override
    public int getMaxSpansToReturn() {
        return this.maxSpansToReturn;
    }

    @Override
    public String getSpanName() {
        return this.spanName;
    }

    public int hashCode() {
        long l = (this.spanName.hashCode() ^ 1000003) * 1000003;
        long l2 = this.latencyLowerNs;
        l = (int)(l ^ (l2 ^ l2 >>> 32)) * 1000003;
        l2 = this.latencyUpperNs;
        return (int)(l ^ (l2 ^ l2 >>> 32)) * 1000003 ^ this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LatencyFilter{spanName=");
        stringBuilder.append(this.spanName);
        stringBuilder.append(", latencyLowerNs=");
        stringBuilder.append(this.latencyLowerNs);
        stringBuilder.append(", latencyUpperNs=");
        stringBuilder.append(this.latencyUpperNs);
        stringBuilder.append(", maxSpansToReturn=");
        stringBuilder.append(this.maxSpansToReturn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

