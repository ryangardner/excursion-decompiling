/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.AbstractGraph;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import java.util.Set;

abstract class ForwardingGraph<N>
extends AbstractGraph<N> {
    ForwardingGraph() {
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

    protected abstract BaseGraph<N> delegate();

    @Override
    protected long edgeCount() {
        return this.delegate().edges().size();
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
    public Set<EndpointPair<N>> incidentEdges(N n) {
        return this.delegate().incidentEdges(n);
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

