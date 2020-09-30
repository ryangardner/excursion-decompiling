/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.samplers;

import io.opencensus.trace.samplers.ProbabilitySampler;

final class AutoValue_ProbabilitySampler
extends ProbabilitySampler {
    private final long idUpperBound;
    private final double probability;

    AutoValue_ProbabilitySampler(double d, long l) {
        this.probability = d;
        this.idUpperBound = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ProbabilitySampler)) return false;
        object = (ProbabilitySampler)object;
        if (Double.doubleToLongBits(this.probability) != Double.doubleToLongBits(((ProbabilitySampler)object).getProbability())) return false;
        if (this.idUpperBound != ((ProbabilitySampler)object).getIdUpperBound()) return false;
        return bl;
    }

    @Override
    long getIdUpperBound() {
        return this.idUpperBound;
    }

    @Override
    double getProbability() {
        return this.probability;
    }

    public int hashCode() {
        long l = (int)((long)1000003 ^ (Double.doubleToLongBits(this.probability) >>> 32 ^ Double.doubleToLongBits(this.probability))) * 1000003;
        long l2 = this.idUpperBound;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProbabilitySampler{probability=");
        stringBuilder.append(this.probability);
        stringBuilder.append(", idUpperBound=");
        stringBuilder.append(this.idUpperBound);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

