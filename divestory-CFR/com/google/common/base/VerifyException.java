/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class VerifyException
extends RuntimeException {
    public VerifyException() {
    }

    public VerifyException(@NullableDecl String string2) {
        super(string2);
    }

    public VerifyException(@NullableDecl String string2, @NullableDecl Throwable throwable) {
        super(string2, throwable);
    }

    public VerifyException(@NullableDecl Throwable throwable) {
        super(throwable);
    }
}

