/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphConnections;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class UndirectedGraphConnections<N, V>
implements GraphConnections<N, V> {
    private final Map<N, V> adjacentNodeValues;

    private UndirectedGraphConnections(Map<N, V> map) {
        this.adjacentNodeValues = Preconditions.checkNotNull(map);
    }

    static <N, V> UndirectedGraphConnections<N, V> of() {
        return new UndirectedGraphConnections(new HashMap(2, 1.0f));
    }

    static <N, V> UndirectedGraphConnections<N, V> ofImmutable(Map<N, V> map) {
        return new UndirectedGraphConnections<N, V>(ImmutableMap.copyOf(map));
    }

    @Override
    public void addPredecessor(N n, V v) {
        this.addSuccessor(n, v);
    }

    @Override
    public V addSuccessor(N n, V v) {
        return this.adjacentNodeValues.put(n, v);
    }

    @Override
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    @Override
    public Iterator<EndpointPair<N>> incidentEdgeIterator(final N n) {
        return Iterators.transform(this.adjacentNodeValues.keySet().iterator(), new Function<N, EndpointPair<N>>(){

            @Override
            public EndpointPair<N> apply(N n2) {
                return EndpointPair.unordered(n, n2);
            }
        });
    }

    @Override
    public Set<N> predecessors() {
        return this.adjacentNodes();
    }

    @Override
    public void removePredecessor(N n) {
        this.removeSuccessor(n);
    }

    @Override
    public V removeSuccessor(N n) {
        return this.adjacentNodeValues.remove(n);
    }

    @Override
    public Set<N> successors() {
        return this.adjacentNodes();
    }

    @Override
    public V value(N n) {
        return this.adjacentNodeValues.get(n);
    }

}

