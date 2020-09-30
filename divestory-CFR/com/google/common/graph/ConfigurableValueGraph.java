/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.AbstractValueGraph;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.Graphs;
import com.google.common.graph.IncidentEdgeSet;
import com.google.common.graph.MapIteratorCache;
import com.google.common.graph.MapRetrievalCache;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ConfigurableValueGraph<N, V>
extends AbstractValueGraph<N, V> {
    private final boolean allowsSelfLoops;
    protected long edgeCount;
    private final boolean isDirected;
    protected final MapIteratorCache<N, GraphConnections<N, V>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this(abstractGraphBuilder, abstractGraphBuilder.nodeOrder.createMap(abstractGraphBuilder.expectedNodeCount.or(10)), 0L);
    }

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> mapIteratorCache, Map<N, GraphConnections<N, V>> map, long l) {
        this.isDirected = ((AbstractGraphBuilder)mapIteratorCache).directed;
        this.allowsSelfLoops = ((AbstractGraphBuilder)mapIteratorCache).allowsSelfLoops;
        this.nodeOrder = ((AbstractGraphBuilder)mapIteratorCache).nodeOrder.cast();
        mapIteratorCache = map instanceof TreeMap ? new MapRetrievalCache<N, GraphConnections<N, V>>(map) : new MapIteratorCache(map);
        this.nodeConnections = mapIteratorCache;
        this.edgeCount = Graphs.checkNonNegative(l);
    }

    @Override
    public Set<N> adjacentNodes(N n) {
        return this.checkedConnections(n).adjacentNodes();
    }

    @Override
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    protected final GraphConnections<N, V> checkedConnections(N n) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n);
        if (graphConnections != null) {
            return graphConnections;
        }
        Preconditions.checkNotNull(n);
        graphConnections = new StringBuilder();
        ((StringBuilder)((Object)graphConnections)).append("Node ");
        ((StringBuilder)((Object)graphConnections)).append(n);
        ((StringBuilder)((Object)graphConnections)).append(" is not an element of this graph.");
        throw new IllegalArgumentException(((StringBuilder)((Object)graphConnections)).toString());
    }

    protected final boolean containsNode(@NullableDecl N n) {
        return this.nodeConnections.containsKey(n);
    }

    @Override
    protected long edgeCount() {
        return this.edgeCount;
    }

    @NullableDecl
    @Override
    public V edgeValueOrDefault(EndpointPair<N> endpointPair, @NullableDecl V v) {
        this.validateEndpoints(endpointPair);
        return this.edgeValueOrDefault_internal(endpointPair.nodeU(), endpointPair.nodeV(), v);
    }

    @NullableDecl
    @Override
    public V edgeValueOrDefault(N n, N n2, @NullableDecl V v) {
        return this.edgeValueOrDefault_internal(Preconditions.checkNotNull(n), Preconditions.checkNotNull(n2), v);
    }

    protected final V edgeValueOrDefault_internal(N object, N n, V object2) {
        if ((object = (object = this.nodeConnections.get(object)) == null ? null : object.value(n)) == null) {
            return object2;
        }
        object2 = object;
        return object2;
    }

    @Override
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        if (!this.isOrderingCompatible(endpointPair)) return false;
        if (!this.hasEdgeConnecting_internal(endpointPair.nodeU(), endpointPair.nodeV())) return false;
        return true;
    }

    @Override
    public boolean hasEdgeConnecting(N n, N n2) {
        return this.hasEdgeConnecting_internal(Preconditions.checkNotNull(n), Preconditions.checkNotNull(n2));
    }

    protected final boolean hasEdgeConnecting_internal(N object, N n) {
        if ((object = this.nodeConnections.get(object)) == null) return false;
        if (!object.successors().contains(n)) return false;
        return true;
    }

    @Override
    public Set<EndpointPair<N>> incidentEdges(N n) {
        return new IncidentEdgeSet<N>(this, n, this.checkedConnections(n)){
            final /* synthetic */ GraphConnections val$connections;
            {
                this.val$connections = graphConnections;
                super(baseGraph, object);
            }

            @Override
            public Iterator<EndpointPair<N>> iterator() {
                return this.val$connections.incidentEdgeIterator(this.node);
            }
        };
    }

    @Override
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    @Override
    public Set<N> predecessors(N n) {
        return this.checkedConnections(n).predecessors();
    }

    @Override
    public Set<N> successors(N n) {
        return this.checkedConnections(n).successors();
    }

}

