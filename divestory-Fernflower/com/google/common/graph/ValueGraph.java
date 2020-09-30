package com.google.common.graph;

import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface ValueGraph<N, V> extends BaseGraph<N> {
   Set<N> adjacentNodes(N var1);

   boolean allowsSelfLoops();

   Graph<N> asGraph();

   int degree(N var1);

   @NullableDecl
   V edgeValueOrDefault(EndpointPair<N> var1, @NullableDecl V var2);

   @NullableDecl
   V edgeValueOrDefault(N var1, N var2, @NullableDecl V var3);

   Set<EndpointPair<N>> edges();

   boolean equals(@NullableDecl Object var1);

   boolean hasEdgeConnecting(EndpointPair<N> var1);

   boolean hasEdgeConnecting(N var1, N var2);

   int hashCode();

   int inDegree(N var1);

   Set<EndpointPair<N>> incidentEdges(N var1);

   boolean isDirected();

   ElementOrder<N> nodeOrder();

   Set<N> nodes();

   int outDegree(N var1);

   Set<N> predecessors(N var1);

   Set<N> successors(N var1);
}
