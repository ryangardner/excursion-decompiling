package com.google.common.graph;

public interface MutableNetwork<N, E> extends Network<N, E> {
   boolean addEdge(EndpointPair<N> var1, E var2);

   boolean addEdge(N var1, N var2, E var3);

   boolean addNode(N var1);

   boolean removeEdge(E var1);

   boolean removeNode(N var1);
}
