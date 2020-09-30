/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Distribution;
import io.opencensus.metrics.export.Value;

final class AutoValue_Value_ValueDistribution
extends Value.ValueDistribution {
    private final Distribution value;

    AutoValue_Value_ValueDistribution(Distribution distribution) {
        if (distribution == null) throw new NullPointerException("Null value");
        this.value = distribution;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Value.ValueDistribution)) return false;
        object = (Value.ValueDistribution)object;
        return this.value.equals(((Value.ValueDistribution)object).getValue());
    }

    @Override
    Distribution getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ValueDistribution{value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

