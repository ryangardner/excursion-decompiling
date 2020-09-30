/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

public interface Sleeper {
    public static final Sleeper DEFAULT = new Sleeper(){

        @Override
        public void sleep(long l) throws InterruptedException {
            Thread.sleep(l);
        }
    };

    public void sleep(long var1) throws InterruptedException;

}

