package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ConfigurableNetwork<N, E> extends AbstractNetwork<N, E> {
   private final boolean allowsParallelEdges;
   private final boolean allowsSelfLoops;
   private final ElementOrder<E> edgeOrder;
   protected final MapIteratorCache<E, N> edgeToReferenceNode;
   private final boolean isDirected;
   protected final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;
   private final ElementOrder<N> nodeOrder;

   ConfigurableNetwork(NetworkBuilder<? super N, ? super E> var1) {
      this(var1, var1.nodeOrder.createMap((Integer)var1.expectedNodeCount.or((int)10)), var1.edgeOrder.createMap((Integer)var1.expectedEdgeCount.or((int)20)));
   }

   ConfigurableNetwork(NetworkBuilder<? super N, ? super E> var1, Map<N, NetworkConnections<N, E>> var2, Map<E, N> var3) {
      this.isDirected = var1.directed;
      this.allowsParallelEdges = var1.allowsParallelEdges;
      this.allowsSelfLoops = var1.allowsSelfLoops;
      this.nodeOrder = var1.nodeOrder.cast();
      this.edgeOrder = var1.edgeOrder.cast();
      Object var4;
      if (var2 instanceof TreeMap) {
         var4 = new MapRetrievalCache(var2);
      } else {
         var4 = new MapIteratorCache(var2);
      }

      this.nodeConnections = (MapIteratorCache)var4;
      this.edgeToReferenceNode = new MapIteratorCache(var3);
   }

   public Set<N> adjacentNodes(N var1) {
      return this.checkedConnections(var1).adjacentNodes();
   }

   public boolean allowsParallelEdges() {
      return this.allowsParallelEdges;
   }

   public boolean allowsSelfLoops() {
      return this.allowsSelfLoops;
   }

   protected final NetworkConnections<N, E> checkedConnections(N var1) {
      NetworkConnections var2 = (NetworkConnections)this.nodeConnections.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         Preconditions.checkNotNull(var1);
         throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", var1));
      }
   }

   protected final N checkedReferenceNode(E var1) {
      Object var2 = this.edgeToReferenceNode.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         Preconditions.checkNotNull(var1);
         throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", var1));
      }
   }

   protected final boolean containsEdge(@NullableDecl E var1) {
      return this.edgeToReferenceNode.containsKey(var1);
   }

   protected final boolean containsNode(@NullableDecl N var1) {
      return this.nodeConnections.containsKey(var1);
   }

   public ElementOrder<E> edgeOrder() {
      return this.edgeOrder;
   }

   public Set<E> edges() {
      return this.edgeToReferenceNode.unmodifiableKeySet();
   }

   public Set<E> edgesConnecting(N var1, N var2) {
      NetworkConnections var3 = this.checkedConnections(var1);
      if (!this.allowsSelfLoops && var1 == var2) {
         return ImmutableSet.of();
      } else {
         Preconditions.checkArgument(this.containsNode(var2), "Node %s is not an element of this graph.", var2);
         return var3.edgesConnecting(var2);
      }
   }

   public Set<E> inEdges(N var1) {
      return this.checkedConnections(var1).inEdges();
   }

   public Set<E> incidentEdges(N var1) {
      return this.checkedConnections(var1).incidentEdges();
   }

   public EndpointPair<N> incidentNodes(E var1) {
      Object var2 = this.checkedReferenceNode(var1);
      return EndpointPair.of((Network)this, var2, ((NetworkConnections)this.nodeConnections.get(var2)).adjacentNode(var1));
   }

   public boolean isDirected() {
      return this.isDirected;
   }

   public ElementOrder<N> nodeOrder() {
      return this.nodeOrder;
   }

   public Set<N> nodes() {
      return this.nodeConnections.unmodifiableKeySet();
   }

   public Set<E> outEdges(N var1) {
      return this.checkedConnections(var1).outEdges();
   }

   public Set<N> predecessors(N var1) {
      return this.checkedConnections(var1).predecessors();
   }

   public Set<N> successors(N var1) {
      return this.checkedConnections(var1).successors();
   }
}
