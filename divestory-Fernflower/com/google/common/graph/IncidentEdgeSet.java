package com.google.common.graph;

import java.util.AbstractSet;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class IncidentEdgeSet<N> extends AbstractSet<EndpointPair<N>> {
   protected final BaseGraph<N> graph;
   protected final N node;

   IncidentEdgeSet(BaseGraph<N> var1, N var2) {
      this.graph = var1;
      this.node = var2;
   }

   public boolean contains(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof EndpointPair;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         return false;
      } else {
         EndpointPair var7 = (EndpointPair)var1;
         if (this.graph.isDirected()) {
            if (!var7.isOrdered()) {
               return false;
            } else {
               Object var8 = var7.source();
               var1 = var7.target();
               if (!this.node.equals(var8) || !this.graph.successors(this.node).contains(var1)) {
                  var2 = var4;
                  if (!this.node.equals(var1)) {
                     return var2;
                  }

                  var2 = var4;
                  if (!this.graph.predecessors(this.node).contains(var8)) {
                     return var2;
                  }
               }

               var2 = true;
               return var2;
            }
         } else if (var7.isOrdered()) {
            return false;
         } else {
            Set var5 = this.graph.adjacentNodes(this.node);
            Object var6 = var7.nodeU();
            var1 = var7.nodeV();
            if (!this.node.equals(var1) || !var5.contains(var6)) {
               var2 = var3;
               if (!this.node.equals(var6)) {
                  return var2;
               }

               var2 = var3;
               if (!var5.contains(var1)) {
                  return var2;
               }
            }

            var2 = true;
            return var2;
         }
      }
   }

   public boolean remove(Object var1) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.graph.isDirected() ? this.graph.inDegree(this.node) + this.graph.outDegree(this.node) - this.graph.successors(this.node).contains(this.node) : this.graph.adjacentNodes(this.node).size();
   }
}
