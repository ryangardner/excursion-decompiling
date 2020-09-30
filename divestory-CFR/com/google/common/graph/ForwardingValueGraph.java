/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.AbstractValueGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class ForwardingValueGraph<N, V>
extends AbstractValueGraph<N, V> {
    ForwardingValueGraph() {
    }

    @Override
    public Set<N> adjacentNodes(N n) {
        return this.delegate().adjacentNodes(n);
    }

    @Override
    public boolean allowsSelfLoops() {
        return this.delegate().allowsSelfLoops();
    }

    @Override
    public int degree(N n) {
        return this.delegate().degree(n);
    }

    protected abstract ValueGraph<N, V> delegate();

    @Override
    protected long edgeCount() {
        return this.delegate().edges().size();
    }

    @NullableDecl
    @Override
    public V edgeValueOrDefault(EndpointPair<N> endpointPair, @NullableDecl V v) {
        return this.delegate().edgeValueOrDefault(endpointPair, v);
    }

    @NullableDecl
    @Override
    public V edgeValueOrDefault(N n, N n2, @NullableDecl V v) {
        return this.delegate().edgeValueOrDefault(n, n2, v);
    }

    @Override
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        return this.delegate().hasEdgeConnecting(endpointPair);
    }

    @Override
    public boolean hasEdgeConnecting(N n, N n2) {
        return this.delegate().hasEdgeConnecting(n, n2);
    }

    @Override
    public int inDegree(N n) {
        return this.delegate().inDegree(n);
    }

    @Override
    public boolean isDirected() {
        return this.delegate().isDirected();
    }

    @Override
    public ElementOrder<N> nodeOrder() {
        return this.delegate().nodeOrder();
    }

    @Override
    public Set<N> nodes() {
        return this.delegate().nodes();
    }

    @Override
    public int outDegree(N n) {
        return this.delegate().outDegree(n);
    }

    @Override
    public Set<N> predecessors(N n) {
        return this.delegate().predecessors(n);
    }

    @Override
    public Set<N> successors(N n) {
        return this.delegate().successors(n);
    }
}

