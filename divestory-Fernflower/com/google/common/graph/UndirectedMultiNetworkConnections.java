package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class UndirectedMultiNetworkConnections<N, E> extends AbstractUndirectedNetworkConnections<N, E> {
   @LazyInit
   private transient Reference<Multiset<N>> adjacentNodesReference;

   private UndirectedMultiNetworkConnections(Map<E, N> var1) {
      super(var1);
   }

   private Multiset<N> adjacentNodesMultiset() {
      Multiset var1 = (Multiset)getReference(this.adjacentNodesReference);
      Object var2 = var1;
      if (var1 == null) {
         var2 = HashMultiset.create(this.incidentEdgeMap.values());
         this.adjacentNodesReference = new SoftReference(var2);
      }

      return (Multiset)var2;
   }

   @NullableDecl
   private static <T> T getReference(@NullableDecl Reference<T> var0) {
      Object var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = var0.get();
      }

      return var1;
   }

   static <N, E> UndirectedMultiNetworkConnections<N, E> of() {
      return new UndirectedMultiNetworkConnections(new HashMap(2, 1.0F));
   }

   static <N, E> UndirectedMultiNetworkConnections<N, E> ofImmutable(Map<E, N> var0) {
      return new UndirectedMultiNetworkConnections(ImmutableMap.copyOf(var0));
   }

   public void addInEdge(E var1, N var2, boolean var3) {
      if (!var3) {
         this.addOutEdge(var1, var2);
      }

   }

   public void addOutEdge(E var1, N var2) {
      super.addOutEdge(var1, var2);
      Multiset var3 = (Multiset)getReference(this.adjacentNodesReference);
      if (var3 != null) {
         Preconditions.checkState(var3.add(var2));
      }

   }

   public Set<N> adjacentNodes() {
      return Collections.unmodifiableSet(this.adjacentNodesMultiset().elementSet());
   }

   public Set<E> edgesConnecting(final N var1) {
      return new MultiEdgesConnecting<E>(this.incidentEdgeMap, var1) {
         public int size() {
            return UndirectedMultiNetworkConnections.this.adjacentNodesMultiset().count(var1);
         }
      };
   }

   public N removeInEdge(E var1, boolean var2) {
      return !var2 ? this.removeOutEdge(var1) : null;
   }

   public N removeOutEdge(E var1) {
      Object var2 = super.removeOutEdge(var1);
      Multiset var3 = (Multiset)getReference(this.adjacentNodesReference);
      if (var3 != null) {
         Preconditions.checkState(var3.remove(var2));
      }

      return var2;
   }
}
