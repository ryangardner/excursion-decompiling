/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.ObjectCountHashMap;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class HashMultiset<E>
extends AbstractMapBasedMultiset<E> {
    private static final long serialVersionUID = 0L;

    HashMultiset(int n) {
        super(n);
    }

    public static <E> HashMultiset<E> create() {
        return HashMultiset.create(3);
    }

    public static <E> HashMultiset<E> create(int n) {
        return new HashMultiset<E>(n);
    }

    public static <E> HashMultiset<E> create(Iterable<? extends E> iterable) {
        HashMultiset<E> hashMultiset = HashMultiset.create(Multisets.inferDistinctElements(iterable));
        Iterables.addAll(hashMultiset, iterable);
        return hashMultiset;
    }

    @Override
    void init(int n) {
        this.backingMap = new ObjectCountHashMap(n);
    }
}

