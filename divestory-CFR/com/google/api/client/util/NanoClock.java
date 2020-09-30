/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

public interface NanoClock {
    public static final NanoClock SYSTEM = new NanoClock(){

        @Override
        public long nanoTime() {
            return System.nanoTime();
        }
    };

    public long nanoTime();

}

