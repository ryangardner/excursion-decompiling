/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Deprecated
public class ComputationException
extends RuntimeException {
    private static final long serialVersionUID = 0L;

    public ComputationException(@NullableDecl Throwable throwable) {
        super(throwable);
    }
}

