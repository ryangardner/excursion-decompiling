/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Iterator;

@DoNotMock(value="Use Iterators.peekingIterator")
public interface PeekingIterator<E>
extends Iterator<E> {
    @Override
    public E next();

    public E peek();

    @Override
    public void remove();
}

