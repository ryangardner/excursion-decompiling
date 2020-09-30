/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.ConfigurableValueGraph;
import com.google.common.graph.DirectedGraphConnections;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphConnections;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.UndirectedGraphConnections;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf={"N", "V"})
public final class ImmutableValueGraph<N, V>
extends ConfigurableValueGraph<N, V> {
    private ImmutableValueGraph(ValueGraph<N, V> valueGraph) {
        super(ValueGraphBuilder.from(valueGraph), ImmutableValueGraph.getNodeConnections(valueGraph), valueGraph.edges().size());
    }

    private static <N, V> GraphConnections<N, V> connectionsOf(ValueGraph<N, V> undirectedGraphConnections, N n) {
        Function function = new Function<N, V>((ValueGraph)undirectedGraphConnections, n){
            final /* synthetic */ ValueGraph val$graph;
            final /* synthetic */ Object val$node;
            {
                this.val$graph = valueGraph;
                this.val$node = object;
            }

            @Override
            public V apply(N n) {
                return this.val$graph.edgeValueOrDefault(this.val$node, n, null);
            }
        };
        if (!undirectedGraphConnections.isDirected()) return UndirectedGraphConnections.ofImmutable(Maps.asMap(undirectedGraphConnections.adjacentNodes(n), function));
        return DirectedGraphConnections.ofImmutable(n, undirectedGraphConnections.incidentEdges(n), function);
    }

    @Deprecated
    public static <N, V> ImmutableValueGraph<N, V> copyOf(ImmutableValueGraph<N, V> immutableValueGraph) {
        return Preconditions.checkNotNull(immutableValueGraph);
    }

    public static <N, V> ImmutableValueGraph<N, V> copyOf(ValueGraph<N, V> immutableValueGraph) {
        if (!(immutableValueGraph instanceof ImmutableValueGraph)) return new ImmutableValueGraph<N, V>(immutableValueGraph);
        return immutableValueGraph;
    }

    private static <N, V> ImmutableMap<N, GraphConnections<N, V>> getNodeConnections(ValueGraph<N, V> valueGraph) {
        ImmutableMap.Builder<N, GraphConnections<N, V>> builder = ImmutableMap.builder();
        Iterator<N> iterator2 = valueGraph.nodes().iterator();
        while (iterator2.hasNext()) {
            N n = iterator2.next();
            builder.put(n, ImmutableValueGraph.connectionsOf(valueGraph, n));
        }
        return builder.build();
    }

    @Override
    public ImmutableGraph<N> asGraph() {
        return new ImmutableGraph(this);
    }

    public static class Builder<N, V> {
        private final MutableValueGraph<N, V> mutableValueGraph;

        Builder(ValueGraphBuilder<N, V> valueGraphBuilder) {
            this.mutableValueGraph = valueGraphBuilder.build();
        }

        public Builder<N, V> addNode(N n) {
            this.mutableValueGraph.addNode(n);
            return this;
        }

        public ImmutableValueGraph<N, V> build() {
            return ImmutableValueGraph.copyOf(this.mutableValueGraph);
        }

        public Builder<N, V> putEdgeValue(EndpointPair<N> endpointPair, V v) {
            this.mutableValueGraph.putEdgeValue(endpointPair, v);
            return this;
        }

        public Builder<N, V> putEdgeValue(N n, N n2, V v) {
            this.mutableValueGraph.putEdgeValue(n, n2, v);
            return this;
        }
    }

}

