package com.google.common.graph;

import java.util.Set;

interface BaseGraph<N> extends SuccessorsFunction<N>, PredecessorsFunction<N> {
   Set<N> adjacentNodes(N var1);

   boolean allowsSelfLoops();

   int degree(N var1);

   Set<EndpointPair<N>> edges();

   boolean hasEdgeConnecting(EndpointPair<N> var1);

   boolean hasEdgeConnecting(N var1, N var2);

   int inDegree(N var1);

   Set<EndpointPair<N>> incidentEdges(N var1);

   boolean isDirected();

   ElementOrder<N> nodeOrder();

   Set<N> nodes();

   int outDegree(N var1);

   Set<N> predecessors(N var1);

   Set<N> successors(N var1);
}
