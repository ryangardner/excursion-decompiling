/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class UncheckedExecutionException
extends RuntimeException {
    private static final long serialVersionUID = 0L;

    protected UncheckedExecutionException() {
    }

    protected UncheckedExecutionException(@NullableDecl String string2) {
        super(string2);
    }

    public UncheckedExecutionException(@NullableDecl String string2, @NullableDecl Throwable throwable) {
        super(string2, throwable);
    }

    public UncheckedExecutionException(@NullableDecl Throwable throwable) {
        super(throwable);
    }
}

