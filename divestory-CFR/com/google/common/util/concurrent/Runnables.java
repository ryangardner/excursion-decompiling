/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

public final class Runnables {
    private static final Runnable EMPTY_RUNNABLE = new Runnable(){

        @Override
        public void run() {
        }
    };

    private Runnables() {
    }

    public static Runnable doNothing() {
        return EMPTY_RUNNABLE;
    }

}

