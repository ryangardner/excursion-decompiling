/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class EdgesConnecting<E>
extends AbstractSet<E> {
    private final Map<?, E> nodeToOutEdge;
    private final Object targetNode;

    EdgesConnecting(Map<?, E> map, Object object) {
        this.nodeToOutEdge = Preconditions.checkNotNull(map);
        this.targetNode = Preconditions.checkNotNull(object);
    }

    @NullableDecl
    private E getConnectingEdge() {
        return this.nodeToOutEdge.get(this.targetNode);
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        E e = this.getConnectingEdge();
        if (e == null) return false;
        if (!e.equals(object)) return false;
        return true;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        Object object = this.getConnectingEdge();
        if (object != null) return Iterators.singletonIterator(object);
        return ImmutableSet.of().iterator();
    }

    @Override
    public int size() {
        if (this.getConnectingEdge() != null) return 1;
        return 0;
    }
}

