/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.ReaderException;

public final class FormatException
extends ReaderException {
    private static final FormatException INSTANCE;

    static {
        FormatException formatException;
        INSTANCE = formatException = new FormatException();
        formatException.setStackTrace(NO_TRACE);
    }

    private FormatException() {
    }

    private FormatException(Throwable throwable) {
        super(throwable);
    }

    public static FormatException getFormatInstance() {
        if (!isStackTrace) return INSTANCE;
        return new FormatException();
    }

    public static FormatException getFormatInstance(Throwable throwable) {
        if (!isStackTrace) return INSTANCE;
        return new FormatException(throwable);
    }
}

