package com.google.common.graph;

import java.util.Set;

interface NetworkConnections<N, E> {
   void addInEdge(E var1, N var2, boolean var3);

   void addOutEdge(E var1, N var2);

   N adjacentNode(E var1);

   Set<N> adjacentNodes();

   Set<E> edgesConnecting(N var1);

   Set<E> inEdges();

   Set<E> incidentEdges();

   Set<E> outEdges();

   Set<N> predecessors();

   N removeInEdge(E var1, boolean var2);

   N removeOutEdge(E var1);

   Set<N> successors();
}
