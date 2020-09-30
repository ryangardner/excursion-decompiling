/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.ConfigurableValueGraph;
import com.google.common.graph.DirectedGraphConnections;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ForwardingGraph;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.GraphConstants;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.UndirectedGraphConnections;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Immutable(containerOf={"N"})
public class ImmutableGraph<N>
extends ForwardingGraph<N> {
    private final BaseGraph<N> backingGraph;

    ImmutableGraph(BaseGraph<N> baseGraph) {
        this.backingGraph = baseGraph;
    }

    private static <N> GraphConnections<N, GraphConstants.Presence> connectionsOf(Graph<N> undirectedGraphConnections, N n) {
        Function<Object, GraphConstants.Presence> function = Functions.constant(GraphConstants.Presence.EDGE_EXISTS);
        if (!undirectedGraphConnections.isDirected()) return UndirectedGraphConnections.ofImmutable(Maps.asMap(undirectedGraphConnections.adjacentNodes(n), function));
        return DirectedGraphConnections.ofImmutable(n, undirectedGraphConnections.incidentEdges(n), function);
    }

    public static <N> ImmutableGraph<N> copyOf(Graph<N> immutableGraph) {
        if (!(immutableGraph instanceof ImmutableGraph)) return new ImmutableGraph<N>(new ConfigurableValueGraph(GraphBuilder.from(immutableGraph), ImmutableGraph.getNodeConnections(immutableGraph), immutableGraph.edges().size()));
        return immutableGraph;
    }

    @Deprecated
    public static <N> ImmutableGraph<N> copyOf(ImmutableGraph<N> immutableGraph) {
        return Preconditions.checkNotNull(immutableGraph);
    }

    private static <N> ImmutableMap<N, GraphConnections<N, GraphConstants.Presence>> getNodeConnections(Graph<N> graph) {
        ImmutableMap.Builder<N, GraphConnections<N, GraphConstants.Presence>> builder = ImmutableMap.builder();
        Iterator<N> iterator2 = graph.nodes().iterator();
        while (iterator2.hasNext()) {
            N n = iterator2.next();
            builder.put(n, ImmutableGraph.connectionsOf(graph, n));
        }
        return builder.build();
    }

    @Override
    protected BaseGraph<N> delegate() {
        return this.backingGraph;
    }

    public static class Builder<N> {
        private final MutableGraph<N> mutableGraph;

        Builder(GraphBuilder<N> graphBuilder) {
            this.mutableGraph = graphBuilder.copy().incidentEdgeOrder(ElementOrder.stable()).build();
        }

        public Builder<N> addNode(N n) {
            this.mutableGraph.addNode(n);
            return this;
        }

        public ImmutableGraph<N> build() {
            return ImmutableGraph.copyOf(this.mutableGraph);
        }

        public Builder<N> putEdge(EndpointPair<N> endpointPair) {
            this.mutableGraph.putEdge(endpointPair);
            return this;
        }

        public Builder<N> putEdge(N n, N n2) {
            this.mutableGraph.putEdge(n, n2);
            return this;
        }
    }

}

