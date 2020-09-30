/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.common.graph.AbstractDirectedNetworkConnections;
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

final class DirectedMultiNetworkConnections<N, E>
extends AbstractDirectedNetworkConnections<N, E> {
    @LazyInit
    private transient Reference<Multiset<N>> predecessorsReference;
    @LazyInit
    private transient Reference<Multiset<N>> successorsReference;

    private DirectedMultiNetworkConnections(Map<E, N> map, Map<E, N> map2, int n) {
        super(map, map2, n);
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

    static <N, E> DirectedMultiNetworkConnections<N, E> of() {
        return new DirectedMultiNetworkConnections(new HashMap(2, 1.0f), new HashMap(2, 1.0f), 0);
    }

    static <N, E> DirectedMultiNetworkConnections<N, E> ofImmutable(Map<E, N> map, Map<E, N> map2, int n) {
        return new DirectedMultiNetworkConnections<N, E>(ImmutableMap.copyOf(map), ImmutableMap.copyOf(map2), n);
    }

    private Multiset<N> predecessorsMultiset() {
        Multiset<N> multiset;
        Multiset<N> multiset2 = multiset = DirectedMultiNetworkConnections.getReference(this.predecessorsReference);
        if (multiset != null) return multiset2;
        multiset2 = HashMultiset.create(this.inEdgeMap.values());
        this.predecessorsReference = new SoftReference<Multiset<N>>(multiset2);
        return multiset2;
    }

    private Multiset<N> successorsMultiset() {
        Multiset<N> multiset;
        Multiset<N> multiset2 = multiset = DirectedMultiNetworkConnections.getReference(this.successorsReference);
        if (multiset != null) return multiset2;
        multiset2 = HashMultiset.create(this.outEdgeMap.values());
        this.successorsReference = new SoftReference<Multiset<N>>(multiset2);
        return multiset2;
    }

    @Override
    public void addInEdge(E object, N n, boolean bl) {
        super.addInEdge(object, n, bl);
        object = DirectedMultiNetworkConnections.getReference(this.predecessorsReference);
        if (object == null) return;
        Preconditions.checkState(object.add(n));
    }

    @Override
    public void addOutEdge(E object, N n) {
        super.addOutEdge(object, n);
        object = DirectedMultiNetworkConnections.getReference(this.successorsReference);
        if (object == null) return;
        Preconditions.checkState(object.add(n));
    }

    @Override
    public Set<E> edgesConnecting(final N n) {
        return new MultiEdgesConnecting<E>(this.outEdgeMap, n){

            @Override
            public int size() {
                return DirectedMultiNetworkConnections.this.successorsMultiset().count(n);
            }
        };
    }

    @Override
    public Set<N> predecessors() {
        return Collections.unmodifiableSet(this.predecessorsMultiset().elementSet());
    }

    @Override
    public N removeInEdge(E object, boolean bl) {
        Object n = super.removeInEdge(object, bl);
        object = DirectedMultiNetworkConnections.getReference(this.predecessorsReference);
        if (object == null) return n;
        Preconditions.checkState(object.remove(n));
        return n;
    }

    @Override
    public N removeOutEdge(E object) {
        object = super.removeOutEdge(object);
        Multiset<N> multiset = DirectedMultiNetworkConnections.getReference(this.successorsReference);
        if (multiset == null) return (N)object;
        Preconditions.checkState(multiset.remove(object));
        return (N)object;
    }

    @Override
    public Set<N> successors() {
        return Collections.unmodifiableSet(this.successorsMultiset().elementSet());
    }

}

