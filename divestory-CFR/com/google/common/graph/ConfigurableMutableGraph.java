/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.ConfigurableMutableValueGraph;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ForwardingGraph;
import com.google.common.graph.GraphConstants;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableValueGraph;

final class ConfigurableMutableGraph<N>
extends ForwardingGraph<N>
implements MutableGraph<N> {
    private final MutableValueGraph<N, GraphConstants.Presence> backingValueGraph;

    ConfigurableMutableGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this.backingValueGraph = new ConfigurableMutableValueGraph<N, GraphConstants.Presence>(abstractGraphBuilder);
    }

    @Override
    public boolean addNode(N n) {
        return this.backingValueGraph.addNode(n);
    }

    @Override
    protected BaseGraph<N> delegate() {
        return this.backingValueGraph;
    }

    @Override
    public boolean putEdge(EndpointPair<N> endpointPair) {
        this.validateEndpoints(endpointPair);
        return this.putEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override
    public boolean putEdge(N n, N n2) {
        if (this.backingValueGraph.putEdgeValue(n, n2, GraphConstants.Presence.EDGE_EXISTS) != null) return false;
        return true;
    }

    @Override
    public boolean removeEdge(EndpointPair<N> endpointPair) {
        this.validateEndpoints(endpointPair);
        return this.removeEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override
    public boolean removeEdge(N n, N n2) {
        if (this.backingValueGraph.removeEdge(n, n2) == null) return false;
        return true;
    }

    @Override
    public boolean removeNode(N n) {
        return this.backingValueGraph.removeNode(n);
    }
}

