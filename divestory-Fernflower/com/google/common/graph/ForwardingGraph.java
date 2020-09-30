package com.google.common.graph;

import java.util.Set;

abstract class ForwardingGraph<N> extends AbstractGraph<N> {
   public Set<N> adjacentNodes(N var1) {
      return this.delegate().adjacentNodes(var1);
   }

   public boolean allowsSelfLoops() {
      return this.delegate().allowsSelfLoops();
   }

   public int degree(N var1) {
      return this.delegate().degree(var1);
   }

   protected abstract BaseGraph<N> delegate();

   protected long edgeCount() {
      return (long)this.delegate().edges().size();
   }

   public boolean hasEdgeConnecting(EndpointPair<N> var1) {
      return this.delegate().hasEdgeConnecting(var1);
   }

   public boolean hasEdgeConnecting(N var1, N var2) {
      return this.delegate().hasEdgeConnecting(var1, var2);
   }

   public int inDegree(N var1) {
      return this.delegate().inDegree(var1);
   }

   public Set<EndpointPair<N>> incidentEdges(N var1) {
      return this.delegate().incidentEdges(var1);
   }

   public boolean isDirected() {
      return this.delegate().isDirected();
   }

   public ElementOrder<N> nodeOrder() {
      return this.delegate().nodeOrder();
   }

   public Set<N> nodes() {
      return this.delegate().nodes();
   }

   public int outDegree(N var1) {
      return this.delegate().outDegree(var1);
   }

   public Set<N> predecessors(N var1) {
      return this.delegate().predecessors(var1);
   }

   public Set<N> successors(N var1) {
      return this.delegate().successors(var1);
   }
}
