/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.AbstractNetwork;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Network;
import java.util.Set;

abstract class ForwardingNetwork<N, E>
extends AbstractNetwork<N, E> {
    ForwardingNetwork() {
    }

    @Override
    public Set<E> adjacentEdges(E e) {
        return this.delegate().adjacentEdges(e);
    }

    @Override
    public Set<N> adjacentNodes(N n) {
        return this.delegate().adjacentNodes(n);
    }

    @Override
    public boolean allowsParallelEdges() {
        return this.delegate().allowsParallelEdges();
    }

    @Override
    public boolean allowsSelfLoops() {
        return this.delegate().allowsSelfLoops();
    }

    @Override
    public int degree(N n) {
        return this.delegate().degree(n);
    }

    protected abstract Network<N, E> delegate();

    @Override
    public E edgeConnectingOrNull(EndpointPair<N> endpointPair) {
        return this.delegate().edgeConnectingOrNull(endpointPair);
    }

    @Override
    public E edgeConnectingOrNull(N n, N n2) {
        return this.delegate().edgeConnectingOrNull(n, n2);
    }

    @Override
    public ElementOrder<E> edgeOrder() {
        return this.delegate().edgeOrder();
    }

    @Override
    public Set<E> edges() {
        return this.delegate().edges();
    }

    @Override
    public Set<E> edgesConnecting(EndpointPair<N> endpointPair) {
        return this.delegate().edgesConnecting(endpointPair);
    }

    @Override
    public Set<E> edgesConnecting(N n, N n2) {
        return this.delegate().edgesConnecting(n, n2);
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
    public Set<E> inEdges(N n) {
        return this.delegate().inEdges(n);
    }

    @Override
    public Set<E> incidentEdges(N n) {
        return this.delegate().incidentEdges(n);
    }

    @Override
    public EndpointPair<N> incidentNodes(E e) {
        return this.delegate().incidentNodes(e);
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
    public Set<E> outEdges(N n) {
        return this.delegate().outEdges(n);
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

