package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;

final class ConfigurableMutableNetwork<N, E> extends ConfigurableNetwork<N, E> implements MutableNetwork<N, E> {
   ConfigurableMutableNetwork(NetworkBuilder<? super N, ? super E> var1) {
      super(var1);
   }

   private NetworkConnections<N, E> addNodeInternal(N var1) {
      NetworkConnections var2 = this.newConnections();
      boolean var3;
      if (this.nodeConnections.put(var1, var2) == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3);
      return var2;
   }

   private NetworkConnections<N, E> newConnections() {
      Object var1;
      if (this.isDirected()) {
         if (this.allowsParallelEdges()) {
            var1 = DirectedMultiNetworkConnections.of();
         } else {
            var1 = DirectedNetworkConnections.of();
         }
      } else if (this.allowsParallelEdges()) {
         var1 = UndirectedMultiNetworkConnections.of();
      } else {
         var1 = UndirectedNetworkConnections.of();
      }

      return (NetworkConnections)var1;
   }

   public boolean addEdge(EndpointPair<N> var1, E var2) {
      this.validateEndpoints(var1);
      return this.addEdge(var1.nodeU(), var1.nodeV(), var2);
   }

   public boolean addEdge(N var1, N var2, E var3) {
      Preconditions.checkNotNull(var1, "nodeU");
      Preconditions.checkNotNull(var2, "nodeV");
      Preconditions.checkNotNull(var3, "edge");
      boolean var4 = this.containsEdge(var3);
      boolean var5 = false;
      if (var4) {
         EndpointPair var9 = this.incidentNodes(var3);
         EndpointPair var8 = EndpointPair.of((Network)this, var1, var2);
         Preconditions.checkArgument(var9.equals(var8), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", var3, var9, var8);
         return false;
      } else {
         NetworkConnections var7 = (NetworkConnections)this.nodeConnections.get(var1);
         if (!this.allowsParallelEdges()) {
            if (var7 == null || !var7.successors().contains(var2)) {
               var5 = true;
            }

            Preconditions.checkArgument(var5, "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", var1, var2);
         }

         var5 = var1.equals(var2);
         if (!this.allowsSelfLoops()) {
            Preconditions.checkArgument(var5 ^ true, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", var1);
         }

         NetworkConnections var6 = var7;
         if (var7 == null) {
            var6 = this.addNodeInternal(var1);
         }

         var6.addOutEdge(var3, var2);
         var7 = (NetworkConnections)this.nodeConnections.get(var2);
         var6 = var7;
         if (var7 == null) {
            var6 = this.addNodeInternal(var2);
         }

         var6.addInEdge(var3, var1, var5);
         this.edgeToReferenceNode.put(var3, var1);
         return true;
      }
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

   public boolean removeEdge(E var1) {
      Preconditions.checkNotNull(var1, "edge");
      Object var2 = this.edgeToReferenceNode.get(var1);
      boolean var3 = false;
      if (var2 == null) {
         return false;
      } else {
         NetworkConnections var4 = (NetworkConnections)this.nodeConnections.get(var2);
         Object var5 = var4.adjacentNode(var1);
         NetworkConnections var6 = (NetworkConnections)this.nodeConnections.get(var5);
         var4.removeOutEdge(var1);
         boolean var7 = var3;
         if (this.allowsSelfLoops()) {
            var7 = var3;
            if (var2.equals(var5)) {
               var7 = true;
            }
         }

         var6.removeInEdge(var1, var7);
         this.edgeToReferenceNode.remove(var1);
         return true;
      }
   }

   public boolean removeNode(N var1) {
      Preconditions.checkNotNull(var1, "node");
      NetworkConnections var2 = (NetworkConnections)this.nodeConnections.get(var1);
      if (var2 == null) {
         return false;
      } else {
         UnmodifiableIterator var3 = ImmutableList.copyOf((Collection)var2.incidentEdges()).iterator();

         while(var3.hasNext()) {
            this.removeEdge(var3.next());
         }

         this.nodeConnections.remove(var1);
         return true;
      }
   }
}
