package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractNetwork<N, E> implements Network<N, E> {
   private Predicate<E> connectedPredicate(final N var1, final N var2) {
      return new Predicate<E>() {
         public boolean apply(E var1x) {
            return AbstractNetwork.this.incidentNodes(var1x).adjacentNode(var1).equals(var2);
         }
      };
   }

   private static <N, E> Map<E, EndpointPair<N>> edgeIncidentNodesMap(final Network<N, E> var0) {
      Function var1 = new Function<E, EndpointPair<N>>() {
         public EndpointPair<N> apply(E var1) {
            return var0.incidentNodes(var1);
         }
      };
      return Maps.asMap(var0.edges(), var1);
   }

   public Set<E> adjacentEdges(E var1) {
      EndpointPair var2 = this.incidentNodes(var1);
      return Sets.difference(Sets.union(this.incidentEdges(var2.nodeU()), this.incidentEdges(var2.nodeV())), ImmutableSet.of(var1));
   }

   public Graph<N> asGraph() {
      return new AbstractGraph<N>() {
         public Set<N> adjacentNodes(N var1) {
            return AbstractNetwork.this.adjacentNodes(var1);
         }

         public boolean allowsSelfLoops() {
            return AbstractNetwork.this.allowsSelfLoops();
         }

         public Set<EndpointPair<N>> edges() {
            return (Set)(AbstractNetwork.this.allowsParallelEdges() ? super.edges() : new AbstractSet<EndpointPair<N>>() {
               public boolean contains(@NullableDecl Object var1) {
                  boolean var2 = var1 instanceof EndpointPair;
                  boolean var3 = false;
                  if (!var2) {
                     return false;
                  } else {
                     EndpointPair var4 = (EndpointPair)var1;
                     var2 = var3;
                     if (isOrderingCompatible(var4)) {
                        var2 = var3;
                        if (nodes().contains(var4.nodeU())) {
                           var2 = var3;
                           if (successors(var4.nodeU()).contains(var4.nodeV())) {
                              var2 = true;
                           }
                        }
                     }

                     return var2;
                  }
               }

               public Iterator<EndpointPair<N>> iterator() {
                  return Iterators.transform(AbstractNetwork.this.edges().iterator(), new Function<E, EndpointPair<N>>() {
                     public EndpointPair<N> apply(E var1) {
                        return AbstractNetwork.this.incidentNodes(var1);
                     }
                  });
               }

               public int size() {
                  return AbstractNetwork.this.edges().size();
               }
            });
         }

         public boolean isDirected() {
            return AbstractNetwork.this.isDirected();
         }

         public ElementOrder<N> nodeOrder() {
            return AbstractNetwork.this.nodeOrder();
         }

         public Set<N> nodes() {
            return AbstractNetwork.this.nodes();
         }

         public Set<N> predecessors(N var1) {
            return AbstractNetwork.this.predecessors(var1);
         }

         public Set<N> successors(N var1) {
            return AbstractNetwork.this.successors(var1);
         }
      };
   }

   public int degree(N var1) {
      return this.isDirected() ? IntMath.saturatedAdd(this.inEdges(var1).size(), this.outEdges(var1).size()) : IntMath.saturatedAdd(this.incidentEdges(var1).size(), this.edgesConnecting(var1, var1).size());
   }

   @NullableDecl
   public E edgeConnectingOrNull(EndpointPair<N> var1) {
      this.validateEndpoints(var1);
      return this.edgeConnectingOrNull(var1.nodeU(), var1.nodeV());
   }

   @NullableDecl
   public E edgeConnectingOrNull(N var1, N var2) {
      Set var3 = this.edgesConnecting(var1, var2);
      int var4 = var3.size();
      if (var4 != 0) {
         if (var4 == 1) {
            return var3.iterator().next();
         } else {
            throw new IllegalArgumentException(String.format("Cannot call edgeConnecting() when parallel edges exist between %s and %s. Consider calling edgesConnecting() instead.", var1, var2));
         }
      } else {
         return null;
      }
   }

   public Set<E> edgesConnecting(EndpointPair<N> var1) {
      this.validateEndpoints(var1);
      return this.edgesConnecting(var1.nodeU(), var1.nodeV());
   }

   public Set<E> edgesConnecting(N var1, N var2) {
      Set var3 = this.outEdges(var1);
      Set var4 = this.inEdges(var2);
      Set var5;
      if (var3.size() <= var4.size()) {
         var5 = Collections.unmodifiableSet(Sets.filter(var3, this.connectedPredicate(var1, var2)));
      } else {
         var5 = Collections.unmodifiableSet(Sets.filter(var4, this.connectedPredicate(var2, var1)));
      }

      return var5;
   }

   public final boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Network)) {
         return false;
      } else {
         Network var3 = (Network)var1;
         if (this.isDirected() != var3.isDirected() || !this.nodes().equals(var3.nodes()) || !edgeIncidentNodesMap(this).equals(edgeIncidentNodesMap(var3))) {
            var2 = false;
         }

         return var2;
      }
   }

   public boolean hasEdgeConnecting(EndpointPair<N> var1) {
      Preconditions.checkNotNull(var1);
      return !this.isOrderingCompatible(var1) ? false : this.hasEdgeConnecting(var1.nodeU(), var1.nodeV());
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

   public final int hashCode() {
      return edgeIncidentNodesMap(this).hashCode();
   }

   public int inDegree(N var1) {
      int var2;
      if (this.isDirected()) {
         var2 = this.inEdges(var1).size();
      } else {
         var2 = this.degree(var1);
      }

      return var2;
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
         var2 = this.outEdges(var1).size();
      } else {
         var2 = this.degree(var1);
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("isDirected: ");
      var1.append(this.isDirected());
      var1.append(", allowsParallelEdges: ");
      var1.append(this.allowsParallelEdges());
      var1.append(", allowsSelfLoops: ");
      var1.append(this.allowsSelfLoops());
      var1.append(", nodes: ");
      var1.append(this.nodes());
      var1.append(", edges: ");
      var1.append(edgeIncidentNodesMap(this));
      return var1.toString();
   }

   protected final void validateEndpoints(EndpointPair<?> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(this.isOrderingCompatible(var1), "Mismatch: unordered endpoints cannot be used with directed graphs");
   }
}
