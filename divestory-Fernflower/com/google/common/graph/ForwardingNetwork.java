package com.google.common.graph;

import java.util.Set;

abstract class ForwardingNetwork<N, E> extends AbstractNetwork<N, E> {
   public Set<E> adjacentEdges(E var1) {
      return this.delegate().adjacentEdges(var1);
   }

   public Set<N> adjacentNodes(N var1) {
      return this.delegate().adjacentNodes(var1);
   }

   public boolean allowsParallelEdges() {
      return this.delegate().allowsParallelEdges();
   }

   public boolean allowsSelfLoops() {
      return this.delegate().allowsSelfLoops();
   }

   public int degree(N var1) {
      return this.delegate().degree(var1);
   }

   protected abstract Network<N, E> delegate();

   public E edgeConnectingOrNull(EndpointPair<N> var1) {
      return this.delegate().edgeConnectingOrNull(var1);
   }

   public E edgeConnectingOrNull(N var1, N var2) {
      return this.delegate().edgeConnectingOrNull(var1, var2);
   }

   public ElementOrder<E> edgeOrder() {
      return this.delegate().edgeOrder();
   }

   public Set<E> edges() {
      return this.delegate().edges();
   }

   public Set<E> edgesConnecting(EndpointPair<N> var1) {
      return this.delegate().edgesConnecting(var1);
   }

   public Set<E> edgesConnecting(N var1, N var2) {
      return this.delegate().edgesConnecting(var1, var2);
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

   public Set<E> inEdges(N var1) {
      return this.delegate().inEdges(var1);
   }

   public Set<E> incidentEdges(N var1) {
      return this.delegate().incidentEdges(var1);
   }

   public EndpointPair<N> incidentNodes(E var1) {
      return this.delegate().incidentNodes(var1);
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

   public Set<E> outEdges(N var1) {
      return this.delegate().outEdges(var1);
   }

   public Set<N> predecessors(N var1) {
      return this.delegate().predecessors(var1);
   }

   public Set<N> successors(N var1) {
      return this.delegate().successors(var1);
   }
}
