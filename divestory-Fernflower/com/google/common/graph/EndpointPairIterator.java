package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;

abstract class EndpointPairIterator<N> extends AbstractIterator<EndpointPair<N>> {
   private final BaseGraph<N> graph;
   protected N node;
   private final Iterator<N> nodeIterator;
   protected Iterator<N> successorIterator;

   private EndpointPairIterator(BaseGraph<N> var1) {
      this.node = null;
      this.successorIterator = ImmutableSet.of().iterator();
      this.graph = var1;
      this.nodeIterator = var1.nodes().iterator();
   }

   // $FF: synthetic method
   EndpointPairIterator(BaseGraph var1, Object var2) {
      this(var1);
   }

   static <N> EndpointPairIterator<N> of(BaseGraph<N> var0) {
      Object var1;
      if (var0.isDirected()) {
         var1 = new EndpointPairIterator.Directed(var0);
      } else {
         var1 = new EndpointPairIterator.Undirected(var0);
      }

      return (EndpointPairIterator)var1;
   }

   protected final boolean advance() {
      Preconditions.checkState(this.successorIterator.hasNext() ^ true);
      if (!this.nodeIterator.hasNext()) {
         return false;
      } else {
         Object var1 = this.nodeIterator.next();
         this.node = var1;
         this.successorIterator = this.graph.successors(var1).iterator();
         return true;
      }
   }

   private static final class Directed<N> extends EndpointPairIterator<N> {
      private Directed(BaseGraph<N> var1) {
         super(var1, null);
      }

      // $FF: synthetic method
      Directed(BaseGraph var1, Object var2) {
         this(var1);
      }

      protected EndpointPair<N> computeNext() {
         do {
            if (this.successorIterator.hasNext()) {
               return EndpointPair.ordered(this.node, this.successorIterator.next());
            }
         } while(this.advance());

         return (EndpointPair)this.endOfData();
      }
   }

   private static final class Undirected<N> extends EndpointPairIterator<N> {
      private Set<N> visitedNodes;

      private Undirected(BaseGraph<N> var1) {
         super(var1, null);
         this.visitedNodes = Sets.newHashSetWithExpectedSize(var1.nodes().size());
      }

      // $FF: synthetic method
      Undirected(BaseGraph var1, Object var2) {
         this(var1);
      }

      protected EndpointPair<N> computeNext() {
         while(true) {
            if (this.successorIterator.hasNext()) {
               Object var1 = this.successorIterator.next();
               if (!this.visitedNodes.contains(var1)) {
                  return EndpointPair.unordered(this.node, var1);
               }
            } else {
               this.visitedNodes.add(this.node);
               if (!this.advance()) {
                  this.visitedNodes = null;
                  return (EndpointPair)this.endOfData();
               }
            }
         }
      }
   }
}
