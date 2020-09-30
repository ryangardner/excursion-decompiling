/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.http;

import com.google.api.client.util.Clock;
import java.util.concurrent.atomic.AtomicLong;

public class FixedClock
implements Clock {
    private AtomicLong currentTime;

    public FixedClock() {
        this(0L);
    }

    public FixedClock(long l) {
        this.currentTime = new AtomicLong(l);
    }

    @Override
    public long currentTimeMillis() {
        return this.currentTime.get();
    }

    public FixedClock setTime(long l) {
        this.currentTime.set(l);
        return this;
    }
}

