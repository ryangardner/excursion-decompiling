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

final class DirectedMultiNetworkConnections<N, E> extends AbstractDirectedNetworkConnections<N, E> {
   @LazyInit
   private transient Reference<Multiset<N>> predecessorsReference;
   @LazyInit
   private transient Reference<Multiset<N>> successorsReference;

   private DirectedMultiNetworkConnections(Map<E, N> var1, Map<E, N> var2, int var3) {
      super(var1, var2, var3);
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

   static <N, E> DirectedMultiNetworkConnections<N, E> of() {
      return new DirectedMultiNetworkConnections(new HashMap(2, 1.0F), new HashMap(2, 1.0F), 0);
   }

   static <N, E> DirectedMultiNetworkConnections<N, E> ofImmutable(Map<E, N> var0, Map<E, N> var1, int var2) {
      return new DirectedMultiNetworkConnections(ImmutableMap.copyOf(var0), ImmutableMap.copyOf(var1), var2);
   }

   private Multiset<N> predecessorsMultiset() {
      Multiset var1 = (Multiset)getReference(this.predecessorsReference);
      Object var2 = var1;
      if (var1 == null) {
         var2 = HashMultiset.create(this.inEdgeMap.values());
         this.predecessorsReference = new SoftReference(var2);
      }

      return (Multiset)var2;
   }

   private Multiset<N> successorsMultiset() {
      Multiset var1 = (Multiset)getReference(this.successorsReference);
      Object var2 = var1;
      if (var1 == null) {
         var2 = HashMultiset.create(this.outEdgeMap.values());
         this.successorsReference = new SoftReference(var2);
      }

      return (Multiset)var2;
   }

   public void addInEdge(E var1, N var2, boolean var3) {
      super.addInEdge(var1, var2, var3);
      Multiset var4 = (Multiset)getReference(this.predecessorsReference);
      if (var4 != null) {
         Preconditions.checkState(var4.add(var2));
      }

   }

   public void addOutEdge(E var1, N var2) {
      super.addOutEdge(var1, var2);
      Multiset var3 = (Multiset)getReference(this.successorsReference);
      if (var3 != null) {
         Preconditions.checkState(var3.add(var2));
      }

   }

   public Set<E> edgesConnecting(final N var1) {
      return new MultiEdgesConnecting<E>(this.outEdgeMap, var1) {
         public int size() {
            return DirectedMultiNetworkConnections.this.successorsMultiset().count(var1);
         }
      };
   }

   public Set<N> predecessors() {
      return Collections.unmodifiableSet(this.predecessorsMultiset().elementSet());
   }

   public N removeInEdge(E var1, boolean var2) {
      Object var3 = super.removeInEdge(var1, var2);
      Multiset var4 = (Multiset)getReference(this.predecessorsReference);
      if (var4 != null) {
         Preconditions.checkState(var4.remove(var3));
      }

      return var3;
   }

   public N removeOutEdge(E var1) {
      var1 = super.removeOutEdge(var1);
      Multiset var2 = (Multiset)getReference(this.successorsReference);
      if (var2 != null) {
         Preconditions.checkState(var2.remove(var1));
      }

      return var1;
   }

   public Set<N> successors() {
      return Collections.unmodifiableSet(this.successorsMultiset().elementSet());
   }
}
