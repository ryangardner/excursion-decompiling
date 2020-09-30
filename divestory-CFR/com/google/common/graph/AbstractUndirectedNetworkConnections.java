/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.graph.NetworkConnections;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

abstract class AbstractUndirectedNetworkConnections<N, E>
implements NetworkConnections<N, E> {
    protected final Map<E, N> incidentEdgeMap;

    protected AbstractUndirectedNetworkConnections(Map<E, N> map) {
        this.incidentEdgeMap = Preconditions.checkNotNull(map);
    }

    @Override
    public void addInEdge(E e, N n, boolean bl) {
        if (bl) return;
        this.addOutEdge(e, n);
    }

    @Override
    public void addOutEdge(E e, N n) {
        boolean bl = this.incidentEdgeMap.put(e, n) == null;
        Preconditions.checkState(bl);
    }

    @Override
    public N adjacentNode(E e) {
        return Preconditions.checkNotNull(this.incidentEdgeMap.get(e));
    }

    @Override
    public Set<E> inEdges() {
        return this.incidentEdges();
    }

    @Override
    public Set<E> incidentEdges() {
        return Collections.unmodifiableSet(this.incidentEdgeMap.keySet());
    }

    @Override
    public Set<E> outEdges() {
        return this.incidentEdges();
    }

    @Override
    public Set<N> predecessors() {
        return this.adjacentNodes();
    }

    @Override
    public N removeInEdge(E e, boolean bl) {
        if (bl) return null;
        return this.removeOutEdge(e);
    }

    @Override
    public N removeOutEdge(E e) {
        return Preconditions.checkNotNull(this.incidentEdgeMap.remove(e));
    }

    @Override
    public Set<N> successors() {
        return this.adjacentNodes();
    }
}

