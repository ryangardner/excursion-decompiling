package com.google.common.graph;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.Immutable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(
   containerOf = {"N"}
)
public abstract class EndpointPair<N> implements Iterable<N> {
   private final N nodeU;
   private final N nodeV;

   private EndpointPair(N var1, N var2) {
      this.nodeU = Preconditions.checkNotNull(var1);
      this.nodeV = Preconditions.checkNotNull(var2);
   }

   // $FF: synthetic method
   EndpointPair(Object var1, Object var2, Object var3) {
      this(var1, var2);
   }

   static <N> EndpointPair<N> of(Graph<?> var0, N var1, N var2) {
      EndpointPair var3;
      if (var0.isDirected()) {
         var3 = ordered(var1, var2);
      } else {
         var3 = unordered(var1, var2);
      }

      return var3;
   }

   static <N> EndpointPair<N> of(Network<?, ?> var0, N var1, N var2) {
      EndpointPair var3;
      if (var0.isDirected()) {
         var3 = ordered(var1, var2);
      } else {
         var3 = unordered(var1, var2);
      }

      return var3;
   }

   public static <N> EndpointPair<N> ordered(N var0, N var1) {
      return new EndpointPair.Ordered(var0, var1);
   }

   public static <N> EndpointPair<N> unordered(N var0, N var1) {
      return new EndpointPair.Unordered(var1, var0);
   }

   public final N adjacentNode(Object var1) {
      if (var1.equals(this.nodeU)) {
         return this.nodeV;
      } else if (var1.equals(this.nodeV)) {
         return this.nodeU;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("EndpointPair ");
         var2.append(this);
         var2.append(" does not contain node ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public abstract boolean equals(@NullableDecl Object var1);

   public abstract int hashCode();

   public abstract boolean isOrdered();

   public final UnmodifiableIterator<N> iterator() {
      return Iterators.forArray(this.nodeU, this.nodeV);
   }

   public final N nodeU() {
      return this.nodeU;
   }

   public final N nodeV() {
      return this.nodeV;
   }

   public abstract N source();

   public abstract N target();

   private static final class Ordered<N> extends EndpointPair<N> {
      private Ordered(N var1, N var2) {
         super(var1, var2, null);
      }

      // $FF: synthetic method
      Ordered(Object var1, Object var2, Object var3) {
         this(var1, var2);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = true;
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof EndpointPair)) {
            return false;
         } else {
            EndpointPair var3 = (EndpointPair)var1;
            if (this.isOrdered() != var3.isOrdered()) {
               return false;
            } else {
               if (!this.source().equals(var3.source()) || !this.target().equals(var3.target())) {
                  var2 = false;
               }

               return var2;
            }
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.source(), this.target());
      }

      public boolean isOrdered() {
         return true;
      }

      public N source() {
         return this.nodeU();
      }

      public N target() {
         return this.nodeV();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("<");
         var1.append(this.source());
         var1.append(" -> ");
         var1.append(this.target());
         var1.append(">");
         return var1.toString();
      }
   }

   private static final class Unordered<N> extends EndpointPair<N> {
      private Unordered(N var1, N var2) {
         super(var1, var2, null);
      }

      // $FF: synthetic method
      Unordered(Object var1, Object var2, Object var3) {
         this(var1, var2);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = true;
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof EndpointPair)) {
            return false;
         } else {
            EndpointPair var3 = (EndpointPair)var1;
            if (this.isOrdered() != var3.isOrdered()) {
               return false;
            } else if (this.nodeU().equals(var3.nodeU())) {
               return this.nodeV().equals(var3.nodeV());
            } else {
               if (!this.nodeU().equals(var3.nodeV()) || !this.nodeV().equals(var3.nodeU())) {
                  var2 = false;
               }

               return var2;
            }
         }
      }

      public int hashCode() {
         return this.nodeU().hashCode() + this.nodeV().hashCode();
      }

      public boolean isOrdered() {
         return false;
      }

      public N source() {
         throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
      }

      public N target() {
         throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("[");
         var1.append(this.nodeU());
         var1.append(", ");
         var1.append(this.nodeV());
         var1.append("]");
         return var1.toString();
      }
   }
}
