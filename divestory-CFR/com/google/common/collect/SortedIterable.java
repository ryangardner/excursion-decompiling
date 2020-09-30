/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;

interface SortedIterable<T>
extends Iterable<T> {
    public Comparator<? super T> comparator();

    @Override
    public Iterator<T> iterator();
}

