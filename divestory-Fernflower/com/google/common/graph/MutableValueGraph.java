package com.google.common.graph;

public interface MutableValueGraph<N, V> extends ValueGraph<N, V> {
   boolean addNode(N var1);

   V putEdgeValue(EndpointPair<N> var1, V var2);

   V putEdgeValue(N var1, N var2, V var3);

   V removeEdge(EndpointPair<N> var1);

   V removeEdge(N var1, N var2);

   boolean removeNode(N var1);
}
