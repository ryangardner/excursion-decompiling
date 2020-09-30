package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Iterator;

final class ConfigurableMutableValueGraph<N, V> extends ConfigurableValueGraph<N, V> implements MutableValueGraph<N, V> {
   private final ElementOrder<N> incidentEdgeOrder;

   ConfigurableMutableValueGraph(AbstractGraphBuilder<? super N> var1) {
      super(var1);
      this.incidentEdgeOrder = var1.incidentEdgeOrder.cast();
   }

   private GraphConnections<N, V> addNodeInternal(N var1) {
      GraphConnections var2 = this.newConnections();
      boolean var3;
      if (this.nodeConnections.put(var1, var2) == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3);
      return var2;
   }

   private GraphConnections<N, V> newConnections() {
      Object var1;
      if (this.isDirected()) {
         var1 = DirectedGraphConnections.of(this.incidentEdgeOrder);
      } else {
         var1 = UndirectedGraphConnections.of();
      }

      return (GraphConnections)var1;
   }

   public boolean addNode(N var1) {
      Preconditions.checkNotNull(var1, "node");
      if (this.containsNode(var1)) {
         return false;
      } else {
         this.addNodeInternal(var1);
         return true;
      }
   }

   public V putEdgeValue(EndpointPair<N> var1, V var2) {
      this.validateEndpoints(var1);
      return this.putEdgeValue(var1.nodeU(), var1.nodeV(), var2);
   }

   public V putEdgeValue(N var1, N var2, V var3) {
      Preconditions.checkNotNull(var1, "nodeU");
      Preconditions.checkNotNull(var2, "nodeV");
      Preconditions.checkNotNull(var3, "value");
      if (!this.allowsSelfLoops()) {
         Preconditions.checkArgument(var1.equals(var2) ^ true, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", var1);
      }

      GraphConnections var4 = (GraphConnections)this.nodeConnections.get(var1);
      GraphConnections var5 = var4;
      if (var4 == null) {
         var5 = this.addNodeInternal(var1);
      }

      Object var6 = var5.addSuccessor(var2, var3);
      var4 = (GraphConnections)this.nodeConnections.get(var2);
      var5 = var4;
      if (var4 == null) {
         var5 = this.addNodeInternal(var2);
      }

      var5.addPredecessor(var1, var3);
      if (var6 == null) {
         long var7 = this.edgeCount + 1L;
         this.edgeCount = var7;
         Graphs.checkPositive(var7);
      }

      return var6;
   }

   public V removeEdge(EndpointPair<N> var1) {
      this.validateEndpoints(var1);
      return this.removeEdge(var1.nodeU(), var1.nodeV());
   }

   public V removeEdge(N var1, N var2) {
      Preconditions.checkNotNull(var1, "nodeU");
      Preconditions.checkNotNull(var2, "nodeV");
      GraphConnections var3 = (GraphConnections)this.nodeConnections.get(var1);
      GraphConnections var4 = (GraphConnections)this.nodeConnections.get(var2);
      if (var3 != null && var4 != null) {
         var2 = var3.removeSuccessor(var2);
         if (var2 != null) {
            var4.removePredecessor(var1);
            long var5 = this.edgeCount - 1L;
            this.edgeCount = var5;
            Graphs.checkNonNegative(var5);
         }

         return var2;
      } else {
         return null;
      }
   }

   public boolean removeNode(N var1) {
      Preconditions.checkNotNull(var1, "node");
      GraphConnections var2 = (GraphConnections)this.nodeConnections.get(var1);
      if (var2 == null) {
         return false;
      } else {
         if (this.allowsSelfLoops() && var2.removeSuccessor(var1) != null) {
            var2.removePredecessor(var1);
            --this.edgeCount;
         }

         Iterator var3;
         Object var4;
         for(var3 = var2.successors().iterator(); var3.hasNext(); --this.edgeCount) {
            var4 = var3.next();
            ((GraphConnections)this.nodeConnections.getWithoutCaching(var4)).removePredecessor(var1);
         }

         if (this.isDirected()) {
            for(var3 = var2.predecessors().iterator(); var3.hasNext(); --this.edgeCount) {
               var4 = var3.next();
               boolean var5;
               if (((GraphConnections)this.nodeConnections.getWithoutCaching(var4)).removeSuccessor(var1) != null) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               Preconditions.checkState(var5);
            }
         }

         this.nodeConnections.remove(var1);
         Graphs.checkNonNegative(this.edgeCount);
         return true;
      }
   }
}
