/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.graph.AbstractGraphBuilder;
import com.google.common.graph.ConfigurableMutableNetwork;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableNetwork;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.Network;

public final class NetworkBuilder<N, E>
extends AbstractGraphBuilder<N> {
    boolean allowsParallelEdges = false;
    ElementOrder<? super E> edgeOrder = ElementOrder.insertion();
    Optional<Integer> expectedEdgeCount = Optional.absent();

    private NetworkBuilder(boolean bl) {
        super(bl);
    }

    private <N1 extends N, E1 extends E> NetworkBuilder<N1, E1> cast() {
        return this;
    }

    public static NetworkBuilder<Object, Object> directed() {
        return new NetworkBuilder<Object, Object>(true);
    }

    public static <N, E> NetworkBuilder<N, E> from(Network<N, E> network) {
        return new NetworkBuilder<N, E>(network.isDirected()).allowsParallelEdges(network.allowsParallelEdges()).allowsSelfLoops(network.allowsSelfLoops()).nodeOrder(network.nodeOrder()).edgeOrder(network.edgeOrder());
    }

    public static NetworkBuilder<Object, Object> undirected() {
        return new NetworkBuilder<Object, Object>(false);
    }

    public NetworkBuilder<N, E> allowsParallelEdges(boolean bl) {
        this.allowsParallelEdges = bl;
        return this;
    }

    public NetworkBuilder<N, E> allowsSelfLoops(boolean bl) {
        this.allowsSelfLoops = bl;
        return this;
    }

    public <N1 extends N, E1 extends E> MutableNetwork<N1, E1> build() {
        return new ConfigurableMutableNetwork(this);
    }

    public <E1 extends E> NetworkBuilder<N, E1> edgeOrder(ElementOrder<E1> elementOrder) {
        NetworkBuilder<N1, E1> networkBuilder = this.cast();
        networkBuilder.edgeOrder = Preconditions.checkNotNull(elementOrder);
        return networkBuilder;
    }

    public NetworkBuilder<N, E> expectedEdgeCount(int n) {
        this.expectedEdgeCount = Optional.of(Graphs.checkNonNegative(n));
        return this;
    }

    public NetworkBuilder<N, E> expectedNodeCount(int n) {
        this.expectedNodeCount = Optional.of(Graphs.checkNonNegative(n));
        return this;
    }

    public <N1 extends N, E1 extends E> ImmutableNetwork.Builder<N1, E1> immutable() {
        return new ImmutableNetwork.Builder<N1, E1>(this.cast());
    }

    public <N1 extends N> NetworkBuilder<N1, E> nodeOrder(ElementOrder<N1> elementOrder) {
        NetworkBuilder<N1, E1> networkBuilder = this.cast();
        networkBuilder.nodeOrder = Preconditions.checkNotNull(elementOrder);
        return networkBuilder;
    }
}

