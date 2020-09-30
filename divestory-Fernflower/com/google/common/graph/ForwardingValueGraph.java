package com.google.common.graph;

import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class ForwardingValueGraph<N, V> extends AbstractValueGraph<N, V> {
   public Set<N> adjacentNodes(N var1) {
      return this.delegate().adjacentNodes(var1);
   }

   public boolean allowsSelfLoops() {
      return this.delegate().allowsSelfLoops();
   }

   public int degree(N var1) {
      return this.delegate().degree(var1);
   }

   protected abstract ValueGraph<N, V> delegate();

   protected long edgeCount() {
      return (long)this.delegate().edges().size();
   }

   @NullableDecl
   public V edgeValueOrDefault(EndpointPair<N> var1, @NullableDecl V var2) {
      return this.delegate().edgeValueOrDefault(var1, var2);
   }

   @NullableDecl
   public V edgeValueOrDefault(N var1, N var2, @NullableDecl V var3) {
      return this.delegate().edgeValueOrDefault(var1, var2, var3);
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
