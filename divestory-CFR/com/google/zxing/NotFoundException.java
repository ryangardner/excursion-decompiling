/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.ReaderException;

public final class NotFoundException
extends ReaderException {
    private static final NotFoundException INSTANCE;

    static {
        NotFoundException notFoundException;
        INSTANCE = notFoundException = new NotFoundException();
        notFoundException.setStackTrace(NO_TRACE);
    }

    private NotFoundException() {
    }

    public static NotFoundException getNotFoundInstance() {
        return INSTANCE;
    }
}

