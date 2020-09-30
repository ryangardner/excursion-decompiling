/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface FutureCallback<V> {
    public void onFailure(Throwable var1);

    public void onSuccess(@NullableDecl V var1);
}

