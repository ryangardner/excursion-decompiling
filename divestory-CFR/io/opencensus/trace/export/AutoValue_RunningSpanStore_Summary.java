/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.export.RunningSpanStore;
import java.util.Map;

final class AutoValue_RunningSpanStore_Summary
extends RunningSpanStore.Summary {
    private final Map<String, RunningSpanStore.PerSpanNameSummary> perSpanNameSummary;

    AutoValue_RunningSpanStore_Summary(Map<String, RunningSpanStore.PerSpanNameSummary> map) {
        if (map == null) throw new NullPointerException("Null perSpanNameSummary");
        this.perSpanNameSummary = map;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RunningSpanStore.Summary)) return false;
        object = (RunningSpanStore.Summary)object;
        return this.perSpanNameSummary.equals(((RunningSpanStore.Summary)object).getPerSpanNameSummary());
    }

    @Override
    public Map<String, RunningSpanStore.PerSpanNameSummary> getPerSpanNameSummary() {
        return this.perSpanNameSummary;
    }

    public int hashCode() {
        return this.perSpanNameSummary.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Summary{perSpanNameSummary=");
        stringBuilder.append(this.perSpanNameSummary);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

