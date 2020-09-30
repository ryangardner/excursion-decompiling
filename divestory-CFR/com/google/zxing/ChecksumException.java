/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.ReaderException;

public final class ChecksumException
extends ReaderException {
    private static final ChecksumException INSTANCE;

    static {
        ChecksumException checksumException;
        INSTANCE = checksumException = new ChecksumException();
        checksumException.setStackTrace(NO_TRACE);
    }

    private ChecksumException() {
    }

    private ChecksumException(Throwable throwable) {
        super(throwable);
    }

    public static ChecksumException getChecksumInstance() {
        if (!isStackTrace) return INSTANCE;
        return new ChecksumException();
    }

    public static ChecksumException getChecksumInstance(Throwable throwable) {
        if (!isStackTrace) return INSTANCE;
        return new ChecksumException(throwable);
    }
}

