/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

public interface Clock {
    public static final Clock SYSTEM = new Clock(){

        @Override
        public long currentTimeMillis() {
            return System.currentTimeMillis();
        }
    };

    public long currentTimeMillis();

}

