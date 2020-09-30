/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;
import io.opencensus.metrics.export.Point;
import io.opencensus.metrics.export.Value;

final class AutoValue_Point
extends Point {
    private final Timestamp timestamp;
    private final Value value;

    AutoValue_Point(Value value, Timestamp timestamp) {
        if (value == null) throw new NullPointerException("Null value");
        this.value = value;
        if (timestamp == null) throw new NullPointerException("Null timestamp");
        this.timestamp = timestamp;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Point)) return false;
        if (!this.value.equals(((Point)(object = (Point)object)).getValue())) return false;
        if (!this.timestamp.equals(((Point)object).getTimestamp())) return false;
        return bl;
    }

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    public int hashCode() {
        return (this.value.hashCode() ^ 1000003) * 1000003 ^ this.timestamp.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Point{value=");
        stringBuilder.append(this.value);
        stringBuilder.append(", timestamp=");
        stringBuilder.append(this.timestamp);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

