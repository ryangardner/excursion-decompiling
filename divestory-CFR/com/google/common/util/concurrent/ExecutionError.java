/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ExecutionError
extends Error {
    private static final long serialVersionUID = 0L;

    protected ExecutionError() {
    }

    public ExecutionError(@NullableDecl Error error) {
        super(error);
    }

    protected ExecutionError(@NullableDecl String string2) {
        super(string2);
    }

    public ExecutionError(@NullableDecl String string2, @NullableDecl Error error) {
        super(string2, error);
    }
}

