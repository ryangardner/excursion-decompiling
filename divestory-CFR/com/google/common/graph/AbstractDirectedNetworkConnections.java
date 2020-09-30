/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.Graphs;
import com.google.common.graph.NetworkConnections;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractDirectedNetworkConnections<N, E>
implements NetworkConnections<N, E> {
    protected final Map<E, N> inEdgeMap;
    protected final Map<E, N> outEdgeMap;
    private int selfLoopCount;

    protected AbstractDirectedNetworkConnections(Map<E, N> map, Map<E, N> map2, int n) {
        this.inEdgeMap = Preconditions.checkNotNull(map);
        this.outEdgeMap = Preconditions.checkNotNull(map2);
        this.selfLoopCount = Graphs.checkNonNegative(n);
        boolean bl = n <= map.size() && n <= map2.size();
        Preconditions.checkState(bl);
    }

    @Override
    public void addInEdge(E e, N n, boolean bl) {
        boolean bl2 = true;
        if (bl) {
            int n2;
            this.selfLoopCount = n2 = this.selfLoopCount + 1;
            Graphs.checkPositive(n2);
        }
        bl = this.inEdgeMap.put(e, n) == null ? bl2 : false;
        Preconditions.checkState(bl);
    }

    @Override
    public void addOutEdge(E e, N n) {
        boolean bl = this.outEdgeMap.put(e, n) == null;
        Preconditions.checkState(bl);
    }

    @Override
    public N adjacentNode(E e) {
        return Preconditions.checkNotNull(this.outEdgeMap.get(e));
    }

    @Override
    public Set<N> adjacentNodes() {
        return Sets.union(this.predecessors(), this.successors());
    }

    @Override
    public Set<E> inEdges() {
        return Collections.unmodifiableSet(this.inEdgeMap.keySet());
    }

    @Override
    public Set<E> incidentEdges() {
        return new AbstractSet<E>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                if (AbstractDirectedNetworkConnections.this.inEdgeMap.containsKey(object)) return true;
                if (AbstractDirectedNetworkConnections.this.outEdgeMap.containsKey(object)) return true;
                return false;
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                Iterable iterable;
                if (AbstractDirectedNetworkConnections.this.selfLoopCount == 0) {
                    iterable = Iterables.concat(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet());
                    return Iterators.unmodifiableIterator(iterable.iterator());
                }
                iterable = Sets.union(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet());
                return Iterators.unmodifiableIterator(iterable.iterator());
            }

            @Override
            public int size() {
                return IntMath.saturatedAdd(AbstractDirectedNetworkConnections.this.inEdgeMap.size(), AbstractDirectedNetworkConnections.this.outEdgeMap.size() - AbstractDirectedNetworkConnections.this.selfLoopCount);
            }
        };
    }

    @Override
    public Set<E> outEdges() {
        return Collections.unmodifiableSet(this.outEdgeMap.keySet());
    }

    @Override
    public N removeInEdge(E e, boolean bl) {
        int n;
        if (!bl) return Preconditions.checkNotNull(this.inEdgeMap.remove(e));
        this.selfLoopCount = n = this.selfLoopCount - 1;
        Graphs.checkNonNegative(n);
        return Preconditions.checkNotNull(this.inEdgeMap.remove(e));
    }

    @Override
    public N removeOutEdge(E e) {
        return Preconditions.checkNotNull(this.outEdgeMap.remove(e));
    }

}

