/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.ConfigurableNetwork;
import com.google.common.graph.DirectedMultiNetworkConnections;
import com.google.common.graph.DirectedNetworkConnections;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.Network;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.NetworkConnections;
import com.google.common.graph.UndirectedMultiNetworkConnections;
import com.google.common.graph.UndirectedNetworkConnections;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Immutable(containerOf={"N", "E"})
public final class ImmutableNetwork<N, E>
extends ConfigurableNetwork<N, E> {
    private ImmutableNetwork(Network<N, E> network) {
        super(NetworkBuilder.from(network), ImmutableNetwork.getNodeConnections(network), ImmutableNetwork.getEdgeToReferenceNode(network));
    }

    private static <N, E> Function<E, N> adjacentNodeFn(final Network<N, E> network, final N n) {
        return new Function<E, N>(){

            @Override
            public N apply(E e) {
                return network.incidentNodes(e).adjacentNode(n);
            }
        };
    }

    private static <N, E> NetworkConnections<N, E> connectionsOf(Network<N, E> networkConnections, N object) {
        if (networkConnections.isDirected()) {
            Map<E, N> map = Maps.asMap(networkConnections.inEdges(object), ImmutableNetwork.sourceNodeFn(networkConnections));
            Map<E, N> map2 = Maps.asMap(networkConnections.outEdges(object), ImmutableNetwork.targetNodeFn(networkConnections));
            int n = networkConnections.edgesConnecting(object, object).size();
            if (!networkConnections.allowsParallelEdges()) return DirectedNetworkConnections.ofImmutable(map, map2, n);
            return DirectedMultiNetworkConnections.ofImmutable(map, map2, n);
        }
        object = Maps.asMap(networkConnections.incidentEdges(object), ImmutableNetwork.adjacentNodeFn(networkConnections, object));
        if (!networkConnections.allowsParallelEdges()) return UndirectedNetworkConnections.ofImmutable(object);
        return UndirectedMultiNetworkConnections.ofImmutable(object);
    }

    @Deprecated
    public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> immutableNetwork) {
        return Preconditions.checkNotNull(immutableNetwork);
    }

    public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> immutableNetwork) {
        if (!(immutableNetwork instanceof ImmutableNetwork)) return new ImmutableNetwork<N, E>(immutableNetwork);
        return immutableNetwork;
    }

    private static <N, E> Map<E, N> getEdgeToReferenceNode(Network<N, E> network) {
        ImmutableMap.Builder<E, N> builder = ImmutableMap.builder();
        Iterator<E> iterator2 = network.edges().iterator();
        while (iterator2.hasNext()) {
            E e = iterator2.next();
            builder.put(e, network.incidentNodes(e).nodeU());
        }
        return builder.build();
    }

    private static <N, E> Map<N, NetworkConnections<N, E>> getNodeConnections(Network<N, E> network) {
        ImmutableMap.Builder<N, NetworkConnections<N, E>> builder = ImmutableMap.builder();
        Iterator<N> iterator2 = network.nodes().iterator();
        while (iterator2.hasNext()) {
            N n = iterator2.next();
            builder.put(n, ImmutableNetwork.connectionsOf(network, n));
        }
        return builder.build();
    }

    private static <N, E> Function<E, N> sourceNodeFn(final Network<N, E> network) {
        return new Function<E, N>(){

            @Override
            public N apply(E e) {
                return network.incidentNodes(e).source();
            }
        };
    }

    private static <N, E> Function<E, N> targetNodeFn(final Network<N, E> network) {
        return new Function<E, N>(){

            @Override
            public N apply(E e) {
                return network.incidentNodes(e).target();
            }
        };
    }

    @Override
    public ImmutableGraph<N> asGraph() {
        return new ImmutableGraph(super.asGraph());
    }

    public static class Builder<N, E> {
        private final MutableNetwork<N, E> mutableNetwork;

        Builder(NetworkBuilder<N, E> networkBuilder) {
            this.mutableNetwork = networkBuilder.build();
        }

        public Builder<N, E> addEdge(EndpointPair<N> endpointPair, E e) {
            this.mutableNetwork.addEdge(endpointPair, e);
            return this;
        }

        public Builder<N, E> addEdge(N n, N n2, E e) {
            this.mutableNetwork.addEdge(n, n2, e);
            return this;
        }

        public Builder<N, E> addNode(N n) {
            this.mutableNetwork.addNode(n);
            return this;
        }

        public ImmutableNetwork<N, E> build() {
            return ImmutableNetwork.copyOf(this.mutableNetwork);
        }
    }

}

