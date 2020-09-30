/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Summary;
import io.opencensus.metrics.export.Value;

final class AutoValue_Value_ValueSummary
extends Value.ValueSummary {
    private final Summary value;

    AutoValue_Value_ValueSummary(Summary summary) {
        if (summary == null) throw new NullPointerException("Null value");
        this.value = summary;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Value.ValueSummary)) return false;
        object = (Value.ValueSummary)object;
        return this.value.equals(((Value.ValueSummary)object).getValue());
    }

    @Override
    Summary getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ValueSummary{value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

