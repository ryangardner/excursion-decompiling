/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.ObjectCountLinkedHashMap;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class LinkedHashMultiset<E>
extends AbstractMapBasedMultiset<E> {
    LinkedHashMultiset(int n) {
        super(n);
    }

    public static <E> LinkedHashMultiset<E> create() {
        return LinkedHashMultiset.create(3);
    }

    public static <E> LinkedHashMultiset<E> create(int n) {
        return new LinkedHashMultiset<E>(n);
    }

    public static <E> LinkedHashMultiset<E> create(Iterable<? extends E> iterable) {
        LinkedHashMultiset<E> linkedHashMultiset = LinkedHashMultiset.create(Multisets.inferDistinctElements(iterable));
        Iterables.addAll(linkedHashMultiset, iterable);
        return linkedHashMultiset;
    }

    @Override
    void init(int n) {
        this.backingMap = new ObjectCountLinkedHashMap(n);
    }
}

