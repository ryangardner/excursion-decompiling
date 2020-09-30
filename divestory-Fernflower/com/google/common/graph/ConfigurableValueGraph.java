package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ConfigurableValueGraph<N, V> extends AbstractValueGraph<N, V> {
   private final boolean allowsSelfLoops;
   protected long edgeCount;
   private final boolean isDirected;
   protected final MapIteratorCache<N, GraphConnections<N, V>> nodeConnections;
   private final ElementOrder<N> nodeOrder;

   ConfigurableValueGraph(AbstractGraphBuilder<? super N> var1) {
      this(var1, var1.nodeOrder.createMap((Integer)var1.expectedNodeCount.or((int)10)), 0L);
   }

   ConfigurableValueGraph(AbstractGraphBuilder<? super N> var1, Map<N, GraphConnections<N, V>> var2, long var3) {
      this.isDirected = var1.directed;
      this.allowsSelfLoops = var1.allowsSelfLoops;
      this.nodeOrder = var1.nodeOrder.cast();
      Object var5;
      if (var2 instanceof TreeMap) {
         var5 = new MapRetrievalCache(var2);
      } else {
         var5 = new MapIteratorCache(var2);
      }

      this.nodeConnections = (MapIteratorCache)var5;
      this.edgeCount = Graphs.checkNonNegative(var3);
   }

   public Set<N> adjacentNodes(N var1) {
      return this.checkedConnections(var1).adjacentNodes();
   }

   public boolean allowsSelfLoops() {
      return this.allowsSelfLoops;
   }

   protected final GraphConnections<N, V> checkedConnections(N var1) {
      GraphConnections var2 = (GraphConnections)this.nodeConnections.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         Preconditions.checkNotNull(var1);
         StringBuilder var3 = new StringBuilder();
         var3.append("Node ");
         var3.append(var1);
         var3.append(" is not an element of this graph.");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   protected final boolean containsNode(@NullableDecl N var1) {
      return this.nodeConnections.containsKey(var1);
   }

   protected long edgeCount() {
      return this.edgeCount;
   }

   @NullableDecl
   public V edgeValueOrDefault(EndpointPair<N> var1, @NullableDecl V var2) {
      this.validateEndpoints(var1);
      return this.edgeValueOrDefault_internal(var1.nodeU(), var1.nodeV(), var2);
   }

   @NullableDecl
   public V edgeValueOrDefault(N var1, N var2, @NullableDecl V var3) {
      return this.edgeValueOrDefault_internal(Preconditions.checkNotNull(var1), Preconditions.checkNotNull(var2), var3);
   }

   protected final V edgeValueOrDefault_internal(N var1, N var2, V var3) {
      GraphConnections var4 = (GraphConnections)this.nodeConnections.get(var1);
      if (var4 == null) {
         var1 = null;
      } else {
         var1 = var4.value(var2);
      }

      if (var1 != null) {
         var3 = var1;
      }

      return var3;
   }

   public boolean hasEdgeConnecting(EndpointPair<N> var1) {
      Preconditions.checkNotNull(var1);
      boolean var2;
      if (this.isOrderingCompatible(var1) && this.hasEdgeConnecting_internal(var1.nodeU(), var1.nodeV())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean hasEdgeConnecting(N var1, N var2) {
      return this.hasEdgeConnecting_internal(Preconditions.checkNotNull(var1), Preconditions.checkNotNull(var2));
   }

   protected final boolean hasEdgeConnecting_internal(N var1, N var2) {
      GraphConnections var4 = (GraphConnections)this.nodeConnections.get(var1);
      boolean var3;
      if (var4 != null && var4.successors().contains(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public Set<EndpointPair<N>> incidentEdges(N var1) {
      return new IncidentEdgeSet<N>(this, var1, this.checkedConnections(var1)) {
         // $FF: synthetic field
         final GraphConnections val$connections;

         {
            this.val$connections = var4;
         }

         public Iterator<EndpointPair<N>> iterator() {
            return this.val$connections.incidentEdgeIterator(this.node);
         }
      };
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

   public Set<N> predecessors(N var1) {
      return this.checkedConnections(var1).predecessors();
   }

   public Set<N> successors(N var1) {
      return this.checkedConnections(var1).successors();
   }
}
