/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.export.Value;

final class AutoValue_Value_ValueDouble
extends Value.ValueDouble {
    private final double value;

    AutoValue_Value_ValueDouble(double d) {
        this.value = d;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Value.ValueDouble)) return false;
        object = (Value.ValueDouble)object;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(((Value.ValueDouble)object).getValue())) return false;
        return bl;
    }

    @Override
    double getValue() {
        return this.value;
    }

    public int hashCode() {
        return (int)((long)1000003 ^ (Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value)));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ValueDouble{value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

