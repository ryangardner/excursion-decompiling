/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import java.util.Iterator;

public abstract class UnmodifiableIterator<E>
implements Iterator<E> {
    protected UnmodifiableIterator() {
    }

    @Deprecated
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}

