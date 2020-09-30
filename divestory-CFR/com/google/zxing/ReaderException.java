/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

public abstract class ReaderException
extends Exception {
    protected static final StackTraceElement[] NO_TRACE;
    protected static final boolean isStackTrace;

    static {
        boolean bl = System.getProperty("surefire.test.class.path") != null;
        isStackTrace = bl;
        NO_TRACE = new StackTraceElement[0];
    }

    ReaderException() {
    }

    ReaderException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public final Throwable fillInStackTrace() {
        return null;
    }
}

