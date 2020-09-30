package com.google.common.graph;

public interface MutableGraph<N> extends Graph<N> {
   boolean addNode(N var1);

   boolean putEdge(EndpointPair<N> var1);

   boolean putEdge(N var1, N var2);

   boolean removeEdge(EndpointPair<N> var1);

   boolean removeEdge(N var1, N var2);

   boolean removeNode(N var1);
}
