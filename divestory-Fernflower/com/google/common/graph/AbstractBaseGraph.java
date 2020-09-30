package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractBaseGraph<N> implements BaseGraph<N> {
   public int degree(N var1) {
      if (this.isDirected()) {
         return IntMath.saturatedAdd(this.predecessors(var1).size(), this.successors(var1).size());
      } else {
         Set var2 = this.adjacentNodes(var1);
         byte var3;
         if (this.allowsSelfLoops() && var2.contains(var1)) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         return IntMath.saturatedAdd(var2.size(), var3);
      }
   }

   protected long edgeCount() {
      Iterator var1 = this.nodes().iterator();

      long var2;
      for(var2 = 0L; var1.hasNext(); var2 += (long)this.degree(var1.next())) {
      }

      boolean var4;
      if ((1L & var2) == 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4);
      return var2 >>> 1;
   }

   public Set<EndpointPair<N>> edges() {
      return new AbstractSet<EndpointPair<N>>() {
         public boolean contains(@NullableDecl Object var1) {
            boolean var2 = var1 instanceof EndpointPair;
            boolean var3 = false;
            if (!var2) {
               return false;
            } else {
               EndpointPair var4 = (EndpointPair)var1;
               var2 = var3;
               if (AbstractBaseGraph.this.isOrderingCompatible(var4)) {
                  var2 = var3;
                  if (AbstractBaseGraph.this.nodes().contains(var4.nodeU())) {
                     var2 = var3;
                     if (AbstractBaseGraph.this.successors(var4.nodeU()).contains(var4.nodeV())) {
                        var2 = true;
                     }
                  }
               }

               return var2;
            }
         }

         public UnmodifiableIterator<EndpointPair<N>> iterator() {
            return EndpointPairIterator.of(AbstractBaseGraph.this);
         }

         public boolean remove(Object var1) {
            throw new UnsupportedOperationException();
         }

         public int size() {
            return Ints.saturatedCast(AbstractBaseGraph.this.edgeCount());
         }
      };
   }

   public boolean hasEdgeConnecting(EndpointPair<N> var1) {
      Preconditions.checkNotNull(var1);
      boolean var2 = this.isOrderingCompatible(var1);
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         Object var4 = var1.nodeU();
         Object var5 = var1.nodeV();
         var2 = var3;
         if (this.nodes().contains(var4)) {
            var2 = var3;
            if (this.successors(var4).contains(var5)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public boolean hasEdgeConnecting(N var1, N var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      boolean var3;
      if (this.nodes().contains(var1) && this.successors(var1).contains(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public int inDegree(N var1) {
      int var2;
      if (this.isDirected()) {
         var2 = this.predecessors(var1).size();
      } else {
         var2 = this.degree(var1);
      }

      return var2;
   }

   public Set<EndpointPair<N>> incidentEdges(N var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(this.nodes().contains(var1), "Node %s is not an element of this graph.", var1);
      return new IncidentEdgeSet<N>(this, var1) {
         public UnmodifiableIterator<EndpointPair<N>> iterator() {
            return this.graph.isDirected() ? Iterators.unmodifiableIterator(Iterators.concat(Iterators.transform(this.graph.predecessors(this.node).iterator(), new Function<N, EndpointPair<N>>() {
               public EndpointPair<N> apply(N var1) {
                  return EndpointPair.ordered(var1, node);
               }
            }), Iterators.transform(Sets.difference(this.graph.successors(this.node), ImmutableSet.of(this.node)).iterator(), new Function<N, EndpointPair<N>>() {
               public EndpointPair<N> apply(N var1) {
                  return EndpointPair.ordered(node, var1);
               }
            }))) : Iterators.unmodifiableIterator(Iterators.transform(this.graph.adjacentNodes(this.node).iterator(), new Function<N, EndpointPair<N>>() {
               public EndpointPair<N> apply(N var1) {
                  return EndpointPair.unordered(node, var1);
               }
            }));
         }
      };
   }

   protected final boolean isOrderingCompatible(EndpointPair<?> var1) {
      boolean var2;
      if (!var1.isOrdered() && this.isDirected()) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public int outDegree(N var1) {
      int var2;
      if (this.isDirected()) {
         var2 = this.successors(var1).size();
      } else {
         var2 = this.degree(var1);
      }

      return var2;
   }

   protected final void validateEndpoints(EndpointPair<?> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(this.isOrderingCompatible(var1), "Mismatch: unordered endpoints cannot be used with directed graphs");
   }
}
