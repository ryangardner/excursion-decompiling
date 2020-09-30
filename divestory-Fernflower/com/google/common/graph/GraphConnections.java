package com.google.common.graph;

import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

interface GraphConnections<N, V> {
   void addPredecessor(N var1, V var2);

   V addSuccessor(N var1, V var2);

   Set<N> adjacentNodes();

   Iterator<EndpointPair<N>> incidentEdgeIterator(N var1);

   Set<N> predecessors();

   void removePredecessor(N var1);

   V removeSuccessor(N var1);

   Set<N> successors();

   @NullableDecl
   V value(N var1);
}
