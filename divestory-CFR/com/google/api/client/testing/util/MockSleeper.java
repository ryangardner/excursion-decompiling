/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.util;

import com.google.api.client.util.Sleeper;

public class MockSleeper
implements Sleeper {
    private int count;
    private long lastMillis;

    public final int getCount() {
        return this.count;
    }

    public final long getLastMillis() {
        return this.lastMillis;
    }

    @Override
    public void sleep(long l) throws InterruptedException {
        ++this.count;
        this.lastMillis = l;
    }
}

