/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class UncheckedTimeoutException
extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public UncheckedTimeoutException() {
    }

    public UncheckedTimeoutException(@NullableDecl String string2) {
        super(string2);
    }

    public UncheckedTimeoutException(@NullableDecl String string2, @NullableDecl Throwable throwable) {
        super(string2, throwable);
    }

    public UncheckedTimeoutException(@NullableDecl Throwable throwable) {
        super(throwable);
    }
}

