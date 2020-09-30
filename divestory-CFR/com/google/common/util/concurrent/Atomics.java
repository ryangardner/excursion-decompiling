/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Atomics {
    private Atomics() {
    }

    public static <V> AtomicReference<V> newReference() {
        return new AtomicReference();
    }

    public static <V> AtomicReference<V> newReference(@NullableDecl V v) {
        return new AtomicReference<V>(v);
    }

    public static <E> AtomicReferenceArray<E> newReferenceArray(int n) {
        return new AtomicReferenceArray(n);
    }

    public static <E> AtomicReferenceArray<E> newReferenceArray(E[] arrE) {
        return new AtomicReferenceArray<E>(arrE);
    }
}

