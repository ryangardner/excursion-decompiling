/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.common.graph.AbstractUndirectedNetworkConnections;
import com.google.common.graph.MultiEdgesConnecting;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class UndirectedMultiNetworkConnections<N, E>
extends AbstractUndirectedNetworkConnections<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> adjacentNodesReference;

    private UndirectedMultiNetworkConnections(Map<E, N> map) {
        super(map);
    }

    private Multiset<N> adjacentNodesMultiset() {
        Multiset<N> multiset;
        Multiset<N> multiset2 = multiset = UndirectedMultiNetworkConnections.getReference(this.adjacentNodesReference);
        if (multiset != null) return multiset2;
        multiset2 = HashMultiset.create(this.incidentEdgeMap.values());
        this.adjacentNodesReference = new SoftReference<Multiset<N>>(multiset2);
        return multiset2;
    }

    @NullableDecl
    private static <T> T getReference(@NullableDecl Reference<T> reference) {
        if (reference == null) {
            reference = null;
            return (T)reference;
        }
        reference = reference.get();
        return (T)reference;
    }

    static <N, E> UndirectedMultiNetworkConnections<N, E> of() {
        return new UndirectedMultiNetworkConnections(new HashMap(2, 1.0f));
    }

    static <N, E> UndirectedMultiNetworkConnections<N, E> ofImmutable(Map<E, N> map) {
        return new UndirectedMultiNetworkConnections<N, E>(ImmutableMap.copyOf(map));
    }

    @Override
    public void addInEdge(E e, N n, boolean bl) {
        if (bl) return;
        this.addOutEdge(e, n);
    }

    @Override
    public void addOutEdge(E object, N n) {
        super.addOutEdge(object, n);
        object = UndirectedMultiNetworkConnections.getReference(this.adjacentNodesReference);
        if (object == null) return;
        Preconditions.checkState(object.add(n));
    }

    @Override
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodesMultiset().elementSet());
    }

    @Override
    public Set<E> edgesConnecting(final N n) {
        return new MultiEdgesConnecting<E>(this.incidentEdgeMap, n){

            @Override
            public int size() {
                return UndirectedMultiNetworkConnections.this.adjacentNodesMultiset().count(n);
            }
        };
    }

    @Override
    public N removeInEdge(E e, boolean bl) {
        if (bl) return null;
        return this.removeOutEdge(e);
    }

    @Override
    public N removeOutEdge(E object) {
        Object n = super.removeOutEdge(object);
        object = UndirectedMultiNetworkConnections.getReference(this.adjacentNodesReference);
        if (object == null) return n;
        Preconditions.checkState(object.remove(n));
        return n;
    }

}

