package com.google.common.graph;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use NetworkBuilder to create a real instance")
public interface Network<N, E> extends SuccessorsFunction<N>, PredecessorsFunction<N> {
   Set<E> adjacentEdges(E var1);

   Set<N> adjacentNodes(N var1);

   boolean allowsParallelEdges();

   boolean allowsSelfLoops();

   Graph<N> asGraph();

   int degree(N var1);

   @NullableDecl
   E edgeConnectingOrNull(EndpointPair<N> var1);

   @NullableDecl
   E edgeConnectingOrNull(N var1, N var2);

   ElementOrder<E> edgeOrder();

   Set<E> edges();

   Set<E> edgesConnecting(EndpointPair<N> var1);

   Set<E> edgesConnecting(N var1, N var2);

   boolean equals(@NullableDecl Object var1);

   boolean hasEdgeConnecting(EndpointPair<N> var1);

   boolean hasEdgeConnecting(N var1, N var2);

   int hashCode();

   int inDegree(N var1);

   Set<E> inEdges(N var1);

   Set<E> incidentEdges(N var1);

   EndpointPair<N> incidentNodes(E var1);

   boolean isDirected();

   ElementOrder<N> nodeOrder();

   Set<N> nodes();

   int outDegree(N var1);

   Set<E> outEdges(N var1);

   Set<N> predecessors(N var1);

   Set<N> successors(N var1);
}
