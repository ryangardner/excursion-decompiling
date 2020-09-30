/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.Status;
import io.opencensus.trace.export.SampledSpanStore;
import java.util.Map;

final class AutoValue_SampledSpanStore_PerSpanNameSummary
extends SampledSpanStore.PerSpanNameSummary {
    private final Map<Status.CanonicalCode, Integer> numbersOfErrorSampledSpans;
    private final Map<SampledSpanStore.LatencyBucketBoundaries, Integer> numbersOfLatencySampledSpans;

    AutoValue_SampledSpanStore_PerSpanNameSummary(Map<SampledSpanStore.LatencyBucketBoundaries, Integer> map, Map<Status.CanonicalCode, Integer> map2) {
        if (map == null) throw new NullPointerException("Null numbersOfLatencySampledSpans");
        this.numbersOfLatencySampledSpans = map;
        if (map2 == null) throw new NullPointerException("Null numbersOfErrorSampledSpans");
        this.numbersOfErrorSampledSpans = map2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SampledSpanStore.PerSpanNameSummary)) return false;
        if (!this.numbersOfLatencySampledSpans.equals(((SampledSpanStore.PerSpanNameSummary)(object = (SampledSpanStore.PerSpanNameSummary)object)).getNumbersOfLatencySampledSpans())) return false;
        if (!this.numbersOfErrorSampledSpans.equals(((SampledSpanStore.PerSpanNameSummary)object).getNumbersOfErrorSampledSpans())) return false;
        return bl;
    }

    @Override
    public Map<Status.CanonicalCode, Integer> getNumbersOfErrorSampledSpans() {
        return this.numbersOfErrorSampledSpans;
    }

    @Override
    public Map<SampledSpanStore.LatencyBucketBoundaries, Integer> getNumbersOfLatencySampledSpans() {
        return this.numbersOfLatencySampledSpans;
    }

    public int hashCode() {
        return (this.numbersOfLatencySampledSpans.hashCode() ^ 1000003) * 1000003 ^ this.numbersOfErrorSampledSpans.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PerSpanNameSummary{numbersOfLatencySampledSpans=");
        stringBuilder.append(this.numbersOfLatencySampledSpans);
        stringBuilder.append(", numbersOfErrorSampledSpans=");
        stringBuilder.append(this.numbersOfErrorSampledSpans);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

