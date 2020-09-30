/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.export.SampledSpanStore;
import java.util.Map;

final class AutoValue_SampledSpanStore_Summary
extends SampledSpanStore.Summary {
    private final Map<String, SampledSpanStore.PerSpanNameSummary> perSpanNameSummary;

    AutoValue_SampledSpanStore_Summary(Map<String, SampledSpanStore.PerSpanNameSummary> map) {
        if (map == null) throw new NullPointerException("Null perSpanNameSummary");
        this.perSpanNameSummary = map;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SampledSpanStore.Summary)) return false;
        object = (SampledSpanStore.Summary)object;
        return this.perSpanNameSummary.equals(((SampledSpanStore.Summary)object).getPerSpanNameSummary());
    }

    @Override
    public Map<String, SampledSpanStore.PerSpanNameSummary> getPerSpanNameSummary() {
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

