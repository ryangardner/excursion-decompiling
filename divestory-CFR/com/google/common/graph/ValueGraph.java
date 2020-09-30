/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.BaseGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface ValueGraph<N, V>
extends BaseGraph<N> {
    @Override
    public Set<N> adjacentNodes(N var1);

    @Override
    public boolean allowsSelfLoops();

    public Graph<N> asGraph();

    @Override
    public int degree(N var1);

    @NullableDecl
    public V edgeValueOrDefault(EndpointPair<N> var1, @NullableDecl V var2);

    @NullableDecl
    public V edgeValueOrDefault(N var1, N var2, @NullableDecl V var3);

    @Override
    public Set<EndpointPair<N>> edges();

    public boolean equals(@NullableDecl Object var1);

    @Override
    public boolean hasEdgeConnecting(EndpointPair<N> var1);

    @Override
    public boolean hasEdgeConnecting(N var1, N var2);

    public int hashCode();

    @Override
    public int inDegree(N var1);

    @Override
    public Set<EndpointPair<N>> incidentEdges(N var1);

    @Override
    public boolean isDirected();

    @Override
    public ElementOrder<N> nodeOrder();

    @Override
    public Set<N> nodes();

    @Override
    public int outDegree(N var1);

    @Override
    public Set<N> predecessors(N var1);

    @Override
    public Set<N> successors(N var1);
}

