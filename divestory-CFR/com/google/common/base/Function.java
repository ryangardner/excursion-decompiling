/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface Function<F, T> {
    @NullableDecl
    public T apply(@NullableDecl F var1);

    public boolean equals(@NullableDecl Object var1);
}

