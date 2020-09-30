/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.graph.ConfigurableNetwork;
import com.google.common.graph.DirectedMultiNetworkConnections;
import com.google.common.graph.DirectedNetworkConnections;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MapIteratorCache;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.google.common.graph.NetworkConnections;
import com.google.common.graph.UndirectedMultiNetworkConnections;
import com.google.common.graph.UndirectedNetworkConnections;
import java.util.Iterator;
import java.util.Set;

final class ConfigurableMutableNetwork<N, E>
extends ConfigurableNetwork<N, E>
implements MutableNetwork<N, E> {
    ConfigurableMutableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        super(networkBuilder);
    }

    private NetworkConnections<N, E> addNodeInternal(N n) {
        NetworkConnections<N, E> networkConnections = this.newConnections();
        boolean bl = this.nodeConnections.put(n, networkConnections) == null;
        Preconditions.checkState(bl);
        return networkConnections;
    }

    private NetworkConnections<N, E> newConnections() {
        if (this.isDirected()) {
            if (!this.allowsParallelEdges()) return DirectedNetworkConnections.of();
            return DirectedMultiNetworkConnections.of();
        }
        if (!this.allowsParallelEdges()) return UndirectedNetworkConnections.of();
        return UndirectedMultiNetworkConnections.of();
    }

    @Override
    public boolean addEdge(EndpointPair<N> endpointPair, E e) {
        this.validateEndpoints(endpointPair);
        return this.addEdge(endpointPair.nodeU(), endpointPair.nodeV(), e);
    }

    @Override
    public boolean addEdge(N object, N n, E e) {
        Preconditions.checkNotNull(object, "nodeU");
        Preconditions.checkNotNull(n, "nodeV");
        Preconditions.checkNotNull(e, "edge");
        boolean bl = this.containsEdge(e);
        boolean bl2 = false;
        if (bl) {
            EndpointPair endpointPair = this.incidentNodes(e);
            object = EndpointPair.of(this, object, n);
            Preconditions.checkArgument(endpointPair.equals(object), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", e, endpointPair, object);
            return false;
        }
        NetworkConnections<Object, E> networkConnections = (NetworkConnections<Object, E>)this.nodeConnections.get(object);
        if (!this.allowsParallelEdges()) {
            if (networkConnections == null || !networkConnections.successors().contains(n)) {
                bl2 = true;
            }
            Preconditions.checkArgument(bl2, "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", object, n);
        }
        bl2 = object.equals(n);
        if (!this.allowsSelfLoops()) {
            Preconditions.checkArgument(bl2 ^ true, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", object);
        }
        NetworkConnections<Object, E> networkConnections2 = networkConnections;
        if (networkConnections == null) {
            networkConnections2 = this.addNodeInternal(object);
        }
        networkConnections2.addOutEdge(e, n);
        networkConnections2 = networkConnections = (NetworkConnections<Object, E>)this.nodeConnections.get(n);
        if (networkConnections == null) {
            networkConnections2 = this.addNodeInternal(n);
        }
        networkConnections2.addInEdge(e, object, bl2);
        this.edgeToReferenceNode.put(e, object);
        return true;
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
    public boolean removeEdge(E e) {
        Preconditions.checkNotNull(e, "edge");
        Object v = this.edgeToReferenceNode.get(e);
        boolean bl = false;
        if (v == null) {
            return false;
        }
        NetworkConnections networkConnections = (NetworkConnections)this.nodeConnections.get(v);
        Object n = networkConnections.adjacentNode(e);
        NetworkConnections networkConnections2 = (NetworkConnections)this.nodeConnections.get(n);
        networkConnections.removeOutEdge(e);
        boolean bl2 = bl;
        if (this.allowsSelfLoops()) {
            bl2 = bl;
            if (v.equals(n)) {
                bl2 = true;
            }
        }
        networkConnections2.removeInEdge(e, bl2);
        this.edgeToReferenceNode.remove(e);
        return true;
    }

    @Override
    public boolean removeNode(N n) {
        Preconditions.checkNotNull(n, "node");
        Object object = (NetworkConnections)this.nodeConnections.get(n);
        if (object == null) {
            return false;
        }
        object = ImmutableList.copyOf(object.incidentEdges()).iterator();
        do {
            if (!object.hasNext()) {
                this.nodeConnections.remove(n);
                return true;
            }
            this.removeEdge(object.next());
        } while (true);
    }
}

