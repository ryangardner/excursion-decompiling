/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Value;

final class AutoValue_Value_ValueLong
extends Value.ValueLong {
    private final long value;

    AutoValue_Value_ValueLong(long l) {
        this.value = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Value.ValueLong)) return false;
        if (this.value != ((Value.ValueLong)(object = (Value.ValueLong)object)).getValue()) return false;
        return bl;
    }

    @Override
    long getValue() {
        return this.value;
    }

    public int hashCode() {
        long l = 1000003;
        long l2 = this.value;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ValueLong{value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

