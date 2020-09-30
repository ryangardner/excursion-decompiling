package com.google.common.graph;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractGraph<N> extends AbstractBaseGraph<N> implements Graph<N> {
   public final boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Graph)) {
         return false;
      } else {
         Graph var3 = (Graph)var1;
         if (this.isDirected() != var3.isDirected() || !this.nodes().equals(var3.nodes()) || !this.edges().equals(var3.edges())) {
            var2 = false;
         }

         return var2;
      }
   }

   public final int hashCode() {
      return this.edges().hashCode();
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
      var1.append(this.edges());
      return var1.toString();
   }
}
