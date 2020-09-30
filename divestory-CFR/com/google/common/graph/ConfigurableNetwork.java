/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.AbstractNetwork;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MapIteratorCache;
import com.google.common.graph.MapRetrievalCache;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.NetworkConnections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ConfigurableNetwork<N, E>
extends AbstractNetwork<N, E> {
    private final boolean allowsParallelEdges;
    private final boolean allowsSelfLoops;
    private final ElementOrder<E> edgeOrder;
    protected final MapIteratorCache<E, N> edgeToReferenceNode;
    private final boolean isDirected;
    protected final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        this(networkBuilder, networkBuilder.nodeOrder.createMap(networkBuilder.expectedNodeCount.or(10)), networkBuilder.edgeOrder.createMap(networkBuilder.expectedEdgeCount.or(20)));
    }

    ConfigurableNetwork(NetworkBuilder<? super N, ? super E> mapIteratorCache, Map<N, NetworkConnections<N, E>> map, Map<E, N> map2) {
        this.isDirected = ((NetworkBuilder)mapIteratorCache).directed;
        this.allowsParallelEdges = ((NetworkBuilder)mapIteratorCache).allowsParallelEdges;
        this.allowsSelfLoops = ((NetworkBuilder)mapIteratorCache).allowsSelfLoops;
        this.nodeOrder = ((NetworkBuilder)mapIteratorCache).nodeOrder.cast();
        this.edgeOrder = ((NetworkBuilder)mapIteratorCache).edgeOrder.cast();
        mapIteratorCache = map instanceof TreeMap ? new MapRetrievalCache<N, NetworkConnections<N, E>>(map) : new MapIteratorCache(map);
        this.nodeConnections = mapIteratorCache;
        this.edgeToReferenceNode = new MapIteratorCache<E, N>(map2);
    }

    @Override
    public Set<N> adjacentNodes(N n) {
        return this.checkedConnections(n).adjacentNodes();
    }

    @Override
    public boolean allowsParallelEdges() {
        return this.allowsParallelEdges;
    }

    @Override
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    protected final NetworkConnections<N, E> checkedConnections(N n) {
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(n);
        if (networkConnections != null) {
            return networkConnections;
        }
        Preconditions.checkNotNull(n);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", n));
    }

    protected final N checkedReferenceNode(E e) {
        N n = this.edgeToReferenceNode.get(e);
        if (n != null) {
            return n;
        }
        Preconditions.checkNotNull(e);
        throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", e));
    }

    protected final boolean containsEdge(@NullableDecl E e) {
        return this.edgeToReferenceNode.containsKey(e);
    }

    protected final boolean containsNode(@NullableDecl N n) {
        return this.nodeConnections.containsKey(n);
    }

    @Override
    public ElementOrder<E> edgeOrder() {
        return this.edgeOrder;
    }

    @Override
    public Set<E> edges() {
        return this.edgeToReferenceNode.unmodifiableKeySet();
    }

    @Override
    public Set<E> edgesConnecting(N n, N n2) {
        NetworkConnections<N, E> networkConnections = this.checkedConnections(n);
        if (!this.allowsSelfLoops && n == n2) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(this.containsNode(n2), "Node %s is not an element of this graph.", n2);
        return networkConnections.edgesConnecting(n2);
    }

    @Override
    public Set<E> inEdges(N n) {
        return this.checkedConnections(n).inEdges();
    }

    @Override
    public Set<E> incidentEdges(N n) {
        return this.checkedConnections(n).incidentEdges();
    }

    @Override
    public EndpointPair<N> incidentNodes(E e) {
        N n = this.checkedReferenceNode(e);
        return EndpointPair.of(this, n, this.nodeConnections.get(n).adjacentNode(e));
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
    public Set<E> outEdges(N n) {
        return this.checkedConnections(n).outEdges();
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

