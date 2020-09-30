/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.ConfigurableValueGraph;
import com.google.common.graph.DirectedGraphConnections;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.Graphs;
import com.google.common.graph.MapIteratorCache;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.UndirectedGraphConnections;
import java.util.Set;

final class ConfigurableMutableValueGraph<N, V>
extends ConfigurableValueGraph<N, V>
implements MutableValueGraph<N, V> {
    private final ElementOrder<N> incidentEdgeOrder;

    ConfigurableMutableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        super(abstractGraphBuilder);
        this.incidentEdgeOrder = abstractGraphBuilder.incidentEdgeOrder.cast();
    }

    private GraphConnections<N, V> addNodeInternal(N n) {
        GraphConnections<N, V> graphConnections = this.newConnections();
        boolean bl = this.nodeConnections.put(n, graphConnections) == null;
        Preconditions.checkState(bl);
        return graphConnections;
    }

    private GraphConnections<N, V> newConnections() {
        if (!this.isDirected()) return UndirectedGraphConnections.of();
        return DirectedGraphConnections.of(this.incidentEdgeOrder);
    }

    @Override
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (this.containsNode(n)) {
            return false;
        }
        this.addNodeInternal(n);
        return true;
    }

    @Override
    public V putEdgeValue(EndpointPair<N> endpointPair, V v) {
        this.validateEndpoints(endpointPair);
        return this.putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), v);
    }

    @Override
    public V putEdgeValue(N n, N n2, V v) {
        GraphConnections<N, V> graphConnections;
        long l;
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(v, "value");
        if (!this.allowsSelfLoops()) {
            Preconditions.checkArgument(n.equals(n2) ^ true, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n);
        }
        GraphConnections<N, V> graphConnections2 = graphConnections = (GraphConnections<N, V>)this.nodeConnections.get(n);
        if (graphConnections == null) {
            graphConnections2 = this.addNodeInternal(n);
        }
        V v2 = graphConnections2.addSuccessor(n2, v);
        graphConnections2 = graphConnections = (GraphConnections<N, V>)this.nodeConnections.get(n2);
        if (graphConnections == null) {
            graphConnections2 = this.addNodeInternal(n2);
        }
        graphConnections2.addPredecessor(n, v);
        if (v2 != null) return v2;
        this.edgeCount = l = this.edgeCount + 1L;
        Graphs.checkPositive(l);
        return v2;
    }

    @Override
    public V removeEdge(EndpointPair<N> endpointPair) {
        this.validateEndpoints(endpointPair);
        return this.removeEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override
    public V removeEdge(N n, N object) {
        long l;
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(object, "nodeV");
        GraphConnections graphConnections = (GraphConnections)this.nodeConnections.get(n);
        GraphConnections graphConnections2 = (GraphConnections)this.nodeConnections.get(object);
        if (graphConnections == null) return null;
        if (graphConnections2 == null) {
            return null;
        }
        if ((object = graphConnections.removeSuccessor(object)) == null) return (V)object;
        graphConnections2.removePredecessor(n);
        this.edgeCount = l = this.edgeCount - 1L;
        Graphs.checkNonNegative(l);
        return (V)object;
    }

    @Override
    public boolean removeNode(N n) {
        Preconditions.checkNotNull(n, "node");
        GraphConnections graphConnections = (GraphConnections)this.nodeConnections.get(n);
        if (graphConnections == null) {
            return false;
        }
        if (this.allowsSelfLoops() && graphConnections.removeSuccessor(n) != null) {
            graphConnections.removePredecessor(n);
            --this.edgeCount;
        }
        for (Object n2 : graphConnections.successors()) {
            ((GraphConnections)this.nodeConnections.getWithoutCaching(n2)).removePredecessor(n);
            --this.edgeCount;
        }
        if (this.isDirected()) {
            for (Object n2 : graphConnections.predecessors()) {
                boolean bl = ((GraphConnections)this.nodeConnections.getWithoutCaching(n2)).removeSuccessor(n) != null;
                Preconditions.checkState(bl);
                --this.edgeCount;
            }
        }
        this.nodeConnections.remove(n);
        Graphs.checkNonNegative(this.edgeCount);
        return true;
    }
}

