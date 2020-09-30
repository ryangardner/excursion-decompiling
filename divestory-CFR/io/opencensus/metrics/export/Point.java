/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;
import io.opencensus.metrics.export.AutoValue_Point;
import io.opencensus.metrics.export.Value;

public abstract class Point {
    Point() {
    }

    public static Point create(Value value, Timestamp timestamp) {
        return new AutoValue_Point(value, timestamp);
    }

    public abstract Timestamp getTimestamp();

    public abstract Value getValue();
}

