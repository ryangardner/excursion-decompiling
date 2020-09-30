package com.google.common.graph;

final class ConfigurableMutableGraph<N> extends ForwardingGraph<N> implements MutableGraph<N> {
   private final MutableValueGraph<N, GraphConstants.Presence> backingValueGraph;

   ConfigurableMutableGraph(AbstractGraphBuilder<? super N> var1) {
      this.backingValueGraph = new ConfigurableMutableValueGraph(var1);
   }

   public boolean addNode(N var1) {
      return this.backingValueGraph.addNode(var1);
   }

   protected BaseGraph<N> delegate() {
      return this.backingValueGraph;
   }

   public boolean putEdge(EndpointPair<N> var1) {
      this.validateEndpoints(var1);
      return this.putEdge(var1.nodeU(), var1.nodeV());
   }

   public boolean putEdge(N var1, N var2) {
      boolean var3;
      if (this.backingValueGraph.putEdgeValue(var1, var2, GraphConstants.Presence.EDGE_EXISTS) == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean removeEdge(EndpointPair<N> var1) {
      this.validateEndpoints(var1);
      return this.removeEdge(var1.nodeU(), var1.nodeV());
   }

   public boolean removeEdge(N var1, N var2) {
      boolean var3;
      if (this.backingValueGraph.removeEdge(var1, var2) != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean removeNode(N var1) {
      return this.backingValueGraph.removeNode(var1);
   }
}
