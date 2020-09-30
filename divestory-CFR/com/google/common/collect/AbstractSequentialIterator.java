/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.UnmodifiableIterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractSequentialIterator<T>
extends UnmodifiableIterator<T> {
    @NullableDecl
    private T nextOrNull;

    protected AbstractSequentialIterator(@NullableDecl T t) {
        this.nextOrNull = t;
    }

    @NullableDecl
    protected abstract T computeNext(T var1);

    @Override
    public final boolean hasNext() {
        if (this.nextOrNull == null) return false;
        return true;
    }

    @Override
    public final T next() {
        if (!this.hasNext()) throw new NoSuchElementException();
        try {
            T t = this.nextOrNull;
            return t;
        }
        finally {
            this.nextOrNull = this.computeNext(this.nextOrNull);
        }
    }
}

