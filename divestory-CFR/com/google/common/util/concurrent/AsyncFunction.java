/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface AsyncFunction<I, O> {
    public ListenableFuture<O> apply(@NullableDecl I var1) throws Exception;
}

