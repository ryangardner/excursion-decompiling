/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.Timestamp;

public abstract class Clock {
    public abstract Timestamp now();

    public abstract long nowNanos();
}

