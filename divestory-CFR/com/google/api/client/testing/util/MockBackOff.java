/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.util;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class MockBackOff
implements BackOff {
    private long backOffMillis;
    private int maxTries = 10;
    private int numTries;

    public final int getMaxTries() {
        return this.maxTries;
    }

    public final int getNumberOfTries() {
        return this.numTries;
    }

    @Override
    public long nextBackOffMillis() throws IOException {
        int n = this.numTries;
        if (n >= this.maxTries) return -1L;
        long l = this.backOffMillis;
        if (l == -1L) {
            return -1L;
        }
        this.numTries = n + 1;
        return l;
    }

    @Override
    public void reset() throws IOException {
        this.numTries = 0;
    }

    public MockBackOff setBackOffMillis(long l) {
        boolean bl = l == -1L || l >= 0L;
        Preconditions.checkArgument(bl);
        this.backOffMillis = l;
        return this;
    }

    public MockBackOff setMaxTries(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        this.maxTries = n;
        return this;
    }
}

