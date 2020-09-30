package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractDirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {
   protected final Map<E, N> inEdgeMap;
   protected final Map<E, N> outEdgeMap;
   private int selfLoopCount;

   protected AbstractDirectedNetworkConnections(Map<E, N> var1, Map<E, N> var2, int var3) {
      this.inEdgeMap = (Map)Preconditions.checkNotNull(var1);
      this.outEdgeMap = (Map)Preconditions.checkNotNull(var2);
      this.selfLoopCount = Graphs.checkNonNegative(var3);
      boolean var4;
      if (var3 <= var1.size() && var3 <= var2.size()) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4);
   }

   public void addInEdge(E var1, N var2, boolean var3) {
      boolean var4 = true;
      if (var3) {
         int var5 = this.selfLoopCount + 1;
         this.selfLoopCount = var5;
         Graphs.checkPositive(var5);
      }

      if (this.inEdgeMap.put(var1, var2) == null) {
         var3 = var4;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3);
   }

   public void addOutEdge(E var1, N var2) {
      boolean var3;
      if (this.outEdgeMap.put(var1, var2) == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3);
   }

   public N adjacentNode(E var1) {
      return Preconditions.checkNotNull(this.outEdgeMap.get(var1));
   }

   public Set<N> adjacentNodes() {
      return Sets.union(this.predecessors(), this.successors());
   }

   public Set<E> inEdges() {
      return Collections.unmodifiableSet(this.inEdgeMap.keySet());
   }

   public Set<E> incidentEdges() {
      return new AbstractSet<E>() {
         public boolean contains(@NullableDecl Object var1) {
            boolean var2;
            if (!AbstractDirectedNetworkConnections.this.inEdgeMap.containsKey(var1) && !AbstractDirectedNetworkConnections.this.outEdgeMap.containsKey(var1)) {
               var2 = false;
            } else {
               var2 = true;
            }

            return var2;
         }

         public UnmodifiableIterator<E> iterator() {
            Object var1;
            if (AbstractDirectedNetworkConnections.this.selfLoopCount == 0) {
               var1 = Iterables.concat(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet());
            } else {
               var1 = Sets.union(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet());
            }

            return Iterators.unmodifiableIterator(((Iterable)var1).iterator());
         }

         public int size() {
            return IntMath.saturatedAdd(AbstractDirectedNetworkConnections.this.inEdgeMap.size(), AbstractDirectedNetworkConnections.this.outEdgeMap.size() - AbstractDirectedNetworkConnections.this.selfLoopCount);
         }
      };
   }

   public Set<E> outEdges() {
      return Collections.unmodifiableSet(this.outEdgeMap.keySet());
   }

   public N removeInEdge(E var1, boolean var2) {
      if (var2) {
         int var3 = this.selfLoopCount - 1;
         this.selfLoopCount = var3;
         Graphs.checkNonNegative(var3);
      }

      return Preconditions.checkNotNull(this.inEdgeMap.remove(var1));
   }

   public N removeOutEdge(E var1) {
      return Preconditions.checkNotNull(this.outEdgeMap.remove(var1));
   }
}
