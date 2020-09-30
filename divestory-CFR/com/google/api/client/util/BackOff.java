/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import java.io.IOException;

public interface BackOff {
    public static final long STOP = -1L;
    public static final BackOff STOP_BACKOFF;
    public static final BackOff ZERO_BACKOFF;

    static {
        ZERO_BACKOFF = new BackOff(){

            @Override
            public long nextBackOffMillis() throws IOException {
                return 0L;
            }

            @Override
            public void reset() throws IOException {
            }
        };
        STOP_BACKOFF = new BackOff(){

            @Override
            public long nextBackOffMillis() throws IOException {
                return -1L;
            }

            @Override
            public void reset() throws IOException {
            }
        };
    }

    public long nextBackOffMillis() throws IOException;

    public void reset() throws IOException;

}

