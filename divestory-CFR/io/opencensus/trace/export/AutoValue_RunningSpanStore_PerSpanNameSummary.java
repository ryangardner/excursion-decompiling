/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.export.RunningSpanStore;

final class AutoValue_RunningSpanStore_PerSpanNameSummary
extends RunningSpanStore.PerSpanNameSummary {
    private final int numRunningSpans;

    AutoValue_RunningSpanStore_PerSpanNameSummary(int n) {
        this.numRunningSpans = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof RunningSpanStore.PerSpanNameSummary)) return false;
        if (this.numRunningSpans != ((RunningSpanStore.PerSpanNameSummary)(object = (RunningSpanStore.PerSpanNameSummary)object)).getNumRunningSpans()) return false;
        return bl;
    }

    @Override
    public int getNumRunningSpans() {
        return this.numRunningSpans;
    }

    public int hashCode() {
        return this.numRunningSpans ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PerSpanNameSummary{numRunningSpans=");
        stringBuilder.append(this.numRunningSpans);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

