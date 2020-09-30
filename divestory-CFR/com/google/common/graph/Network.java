/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.PredecessorsFunction;
import com.google.common.graph.SuccessorsFunction;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use NetworkBuilder to create a real instance")
public interface Network<N, E>
extends SuccessorsFunction<N>,
PredecessorsFunction<N> {
    public Set<E> adjacentEdges(E var1);

    public Set<N> adjacentNodes(N var1);

    public boolean allowsParallelEdges();

    public boolean allowsSelfLoops();

    public Graph<N> asGraph();

    public int degree(N var1);

    @NullableDecl
    public E edgeConnectingOrNull(EndpointPair<N> var1);

    @NullableDecl
    public E edgeConnectingOrNull(N var1, N var2);

    public ElementOrder<E> edgeOrder();

    public Set<E> edges();

    public Set<E> edgesConnecting(EndpointPair<N> var1);

    public Set<E> edgesConnecting(N var1, N var2);

    public boolean equals(@NullableDecl Object var1);

    public boolean hasEdgeConnecting(EndpointPair<N> var1);

    public boolean hasEdgeConnecting(N var1, N var2);

    public int hashCode();

    public int inDegree(N var1);

    public Set<E> inEdges(N var1);

    public Set<E> incidentEdges(N var1);

    public EndpointPair<N> incidentNodes(E var1);

    public boolean isDirected();

    public ElementOrder<N> nodeOrder();

    public Set<N> nodes();

    public int outDegree(N var1);

    public Set<E> outEdges(N var1);

    @Override
    public Set<N> predecessors(N var1);

    @Override
    public Set<N> successors(N var1);
}

