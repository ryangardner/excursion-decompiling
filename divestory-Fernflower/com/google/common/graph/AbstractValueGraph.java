package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractValueGraph<N, V> extends AbstractBaseGraph<N> implements ValueGraph<N, V> {
   private static <N, V> Map<EndpointPair<N>, V> edgeValueMap(final ValueGraph<N, V> var0) {
      Function var1 = new Function<EndpointPair<N>, V>() {
         public V apply(EndpointPair<N> var1) {
            return var0.edgeValueOrDefault(var1.nodeU(), var1.nodeV(), (Object)null);
         }
      };
      return Maps.asMap(var0.edges(), var1);
   }

   public Graph<N> asGraph() {
      return new AbstractGraph<N>() {
         public Set<N> adjacentNodes(N var1) {
            return AbstractValueGraph.this.adjacentNodes(var1);
         }

         public boolean allowsSelfLoops() {
            return AbstractValueGraph.this.allowsSelfLoops();
         }

         public int degree(N var1) {
            return AbstractValueGraph.this.degree(var1);
         }

         public Set<EndpointPair<N>> edges() {
            return AbstractValueGraph.this.edges();
         }

         public int inDegree(N var1) {
            return AbstractValueGraph.this.inDegree(var1);
         }

         public boolean isDirected() {
            return AbstractValueGraph.this.isDirected();
         }

         public ElementOrder<N> nodeOrder() {
            return AbstractValueGraph.this.nodeOrder();
         }

         public Set<N> nodes() {
            return AbstractValueGraph.this.nodes();
         }

         public int outDegree(N var1) {
            return AbstractValueGraph.this.outDegree(var1);
         }

         public Set<N> predecessors(N var1) {
            return AbstractValueGraph.this.predecessors(var1);
         }

         public Set<N> successors(N var1) {
            return AbstractValueGraph.this.successors(var1);
         }
      };
   }

   public final boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ValueGraph)) {
         return false;
      } else {
         ValueGraph var3 = (ValueGraph)var1;
         if (this.isDirected() != var3.isDirected() || !this.nodes().equals(var3.nodes()) || !edgeValueMap(this).equals(edgeValueMap(var3))) {
            var2 = false;
         }

         return var2;
      }
   }

   public final int hashCode() {
      return edgeValueMap(this).hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("isDirected: ");
      var1.append(this.isDirected());
      var1.append(", allowsSelfLoops: ");
      var1.append(this.allowsSelfLoops());
      var1.append(", nodes: ");
      var1.append(this.nodes());
      var1.append(", edges: ");
      var1.append(edgeValueMap(this));
      return var1.toString();
   }
}
