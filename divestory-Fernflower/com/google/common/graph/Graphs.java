package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Graphs {
   private Graphs() {
   }

   private static boolean canTraverseWithoutReusingEdge(Graph<?> var0, Object var1, @NullableDecl Object var2) {
      return var0.isDirected() || !Objects.equal(var2, var1);
   }

   static int checkNonNegative(int var0) {
      boolean var1;
      if (var0 >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Not true that %s is non-negative.", var0);
      return var0;
   }

   static long checkNonNegative(long var0) {
      boolean var2;
      if (var0 >= 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "Not true that %s is non-negative.", var0);
      return var0;
   }

   static int checkPositive(int var0) {
      boolean var1;
      if (var0 > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Not true that %s is positive.", var0);
      return var0;
   }

   static long checkPositive(long var0) {
      boolean var2;
      if (var0 > 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "Not true that %s is positive.", var0);
      return var0;
   }

   public static <N> MutableGraph<N> copyOf(Graph<N> var0) {
      MutableGraph var1 = GraphBuilder.from(var0).expectedNodeCount(var0.nodes().size()).build();
      Iterator var2 = var0.nodes().iterator();

      while(var2.hasNext()) {
         var1.addNode(var2.next());
      }

      Iterator var3 = var0.edges().iterator();

      while(var3.hasNext()) {
         EndpointPair var4 = (EndpointPair)var3.next();
         var1.putEdge(var4.nodeU(), var4.nodeV());
      }

      return var1;
   }

   public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> var0) {
      MutableNetwork var1 = NetworkBuilder.from(var0).expectedNodeCount(var0.nodes().size()).expectedEdgeCount(var0.edges().size()).build();
      Iterator var2 = var0.nodes().iterator();

      while(var2.hasNext()) {
         var1.addNode(var2.next());
      }

      var2 = var0.edges().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         EndpointPair var4 = var0.incidentNodes(var3);
         var1.addEdge(var4.nodeU(), var4.nodeV(), var3);
      }

      return var1;
   }

   public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> var0) {
      MutableValueGraph var1 = ValueGraphBuilder.from(var0).expectedNodeCount(var0.nodes().size()).build();
      Iterator var2 = var0.nodes().iterator();

      while(var2.hasNext()) {
         var1.addNode(var2.next());
      }

      Iterator var3 = var0.edges().iterator();

      while(var3.hasNext()) {
         EndpointPair var4 = (EndpointPair)var3.next();
         var1.putEdgeValue(var4.nodeU(), var4.nodeV(), var0.edgeValueOrDefault(var4.nodeU(), var4.nodeV(), (Object)null));
      }

      return var1;
   }

   public static <N> boolean hasCycle(Graph<N> var0) {
      int var1 = var0.edges().size();
      if (var1 == 0) {
         return false;
      } else if (!var0.isDirected() && var1 >= var0.nodes().size()) {
         return true;
      } else {
         HashMap var2 = Maps.newHashMapWithExpectedSize(var0.nodes().size());
         Iterator var3 = var0.nodes().iterator();

         do {
            if (!var3.hasNext()) {
               return false;
            }
         } while(!subgraphHasCycle(var0, var2, var3.next(), (Object)null));

         return true;
      }
   }

   public static boolean hasCycle(Network<?, ?> var0) {
      return !var0.isDirected() && var0.allowsParallelEdges() && var0.edges().size() > var0.asGraph().edges().size() ? true : hasCycle(var0.asGraph());
   }

   public static <N> MutableGraph<N> inducedSubgraph(Graph<N> var0, Iterable<? extends N> var1) {
      MutableGraph var2;
      if (var1 instanceof Collection) {
         var2 = GraphBuilder.from(var0).expectedNodeCount(((Collection)var1).size()).build();
      } else {
         var2 = GraphBuilder.from(var0).build();
      }

      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         var2.addNode(var6.next());
      }

      Iterator var3 = var2.nodes().iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         Iterator var5 = var0.successors(var4).iterator();

         while(var5.hasNext()) {
            Object var7 = var5.next();
            if (var2.nodes().contains(var7)) {
               var2.putEdge(var4, var7);
            }
         }
      }

      return var2;
   }

   public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> var0, Iterable<? extends N> var1) {
      MutableNetwork var2;
      if (var1 instanceof Collection) {
         var2 = NetworkBuilder.from(var0).expectedNodeCount(((Collection)var1).size()).build();
      } else {
         var2 = NetworkBuilder.from(var0).build();
      }

      Iterator var7 = var1.iterator();

      while(var7.hasNext()) {
         var2.addNode(var7.next());
      }

      Iterator var3 = var2.nodes().iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         var7 = var0.outEdges(var4).iterator();

         while(var7.hasNext()) {
            Object var5 = var7.next();
            Object var6 = var0.incidentNodes(var5).adjacentNode(var4);
            if (var2.nodes().contains(var6)) {
               var2.addEdge(var4, var6, var5);
            }
         }
      }

      return var2;
   }

   public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> var0, Iterable<? extends N> var1) {
      MutableValueGraph var2;
      if (var1 instanceof Collection) {
         var2 = ValueGraphBuilder.from(var0).expectedNodeCount(((Collection)var1).size()).build();
      } else {
         var2 = ValueGraphBuilder.from(var0).build();
      }

      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         var2.addNode(var6.next());
      }

      Iterator var3 = var2.nodes().iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         var6 = var0.successors(var4).iterator();

         while(var6.hasNext()) {
            Object var5 = var6.next();
            if (var2.nodes().contains(var5)) {
               var2.putEdgeValue(var4, var5, var0.edgeValueOrDefault(var4, var5, (Object)null));
            }
         }
      }

      return var2;
   }

   public static <N> Set<N> reachableNodes(Graph<N> var0, N var1) {
      Preconditions.checkArgument(var0.nodes().contains(var1), "Node %s is not an element of this graph.", var1);
      return ImmutableSet.copyOf(Traverser.forGraph(var0).breadthFirst(var1));
   }

   private static <N> boolean subgraphHasCycle(Graph<N> var0, Map<Object, Graphs.NodeVisitState> var1, N var2, @NullableDecl N var3) {
      Graphs.NodeVisitState var4 = (Graphs.NodeVisitState)var1.get(var2);
      if (var4 == Graphs.NodeVisitState.COMPLETE) {
         return false;
      } else if (var4 == Graphs.NodeVisitState.PENDING) {
         return true;
      } else {
         var1.put(var2, Graphs.NodeVisitState.PENDING);
         Iterator var6 = var0.successors(var2).iterator();

         Object var5;
         do {
            if (!var6.hasNext()) {
               var1.put(var2, Graphs.NodeVisitState.COMPLETE);
               return false;
            }

            var5 = var6.next();
         } while(!canTraverseWithoutReusingEdge(var0, var5, var3) || !subgraphHasCycle(var0, var1, var5, var2));

         return true;
      }
   }

   public static <N> Graph<N> transitiveClosure(Graph<N> var0) {
      MutableGraph var1 = GraphBuilder.from(var0).allowsSelfLoops(true).build();
      Iterator var2;
      if (var0.isDirected()) {
         var2 = var0.nodes().iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            Iterator var4 = reachableNodes(var0, var3).iterator();

            while(var4.hasNext()) {
               var1.putEdge(var3, var4.next());
            }
         }

         return var1;
      } else {
         HashSet var11 = new HashSet();
         Iterator var10 = var0.nodes().iterator();

         while(true) {
            Object var9;
            do {
               if (!var10.hasNext()) {
                  return var1;
               }

               var9 = var10.next();
            } while(var11.contains(var9));

            Set var5 = reachableNodes(var0, var9);
            var11.addAll(var5);
            Iterator var6 = var5.iterator();

            for(int var7 = 1; var6.hasNext(); ++var7) {
               Object var8 = var6.next();
               var2 = Iterables.limit(var5, var7).iterator();

               while(var2.hasNext()) {
                  var1.putEdge(var8, var2.next());
               }
            }
         }
      }
   }

   static <N> EndpointPair<N> transpose(EndpointPair<N> var0) {
      EndpointPair var1 = var0;
      if (var0.isOrdered()) {
         var1 = EndpointPair.ordered(var0.target(), var0.source());
      }

      return var1;
   }

   public static <N> Graph<N> transpose(Graph<N> var0) {
      if (!var0.isDirected()) {
         return var0;
      } else {
         return (Graph)(var0 instanceof Graphs.TransposedGraph ? ((Graphs.TransposedGraph)var0).graph : new Graphs.TransposedGraph(var0));
      }
   }

   public static <N, E> Network<N, E> transpose(Network<N, E> var0) {
      if (!var0.isDirected()) {
         return var0;
      } else {
         return (Network)(var0 instanceof Graphs.TransposedNetwork ? ((Graphs.TransposedNetwork)var0).network : new Graphs.TransposedNetwork(var0));
      }
   }

   public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> var0) {
      if (!var0.isDirected()) {
         return var0;
      } else {
         return (ValueGraph)(var0 instanceof Graphs.TransposedValueGraph ? ((Graphs.TransposedValueGraph)var0).graph : new Graphs.TransposedValueGraph(var0));
      }
   }

   private static enum NodeVisitState {
      COMPLETE,
      PENDING;

      static {
         Graphs.NodeVisitState var0 = new Graphs.NodeVisitState("COMPLETE", 1);
         COMPLETE = var0;
      }
   }

   private static class TransposedGraph<N> extends ForwardingGraph<N> {
      private final Graph<N> graph;

      TransposedGraph(Graph<N> var1) {
         this.graph = var1;
      }

      protected Graph<N> delegate() {
         return this.graph;
      }

      public boolean hasEdgeConnecting(EndpointPair<N> var1) {
         return this.delegate().hasEdgeConnecting(Graphs.transpose(var1));
      }

      public boolean hasEdgeConnecting(N var1, N var2) {
         return this.delegate().hasEdgeConnecting(var2, var1);
      }

      public int inDegree(N var1) {
         return this.delegate().outDegree(var1);
      }

      public Set<EndpointPair<N>> incidentEdges(N var1) {
         return new IncidentEdgeSet<N>(this, var1) {
            public Iterator<EndpointPair<N>> iterator() {
               return Iterators.transform(TransposedGraph.this.delegate().incidentEdges(this.node).iterator(), new Function<EndpointPair<N>, EndpointPair<N>>() {
                  public EndpointPair<N> apply(EndpointPair<N> var1) {
                     return EndpointPair.of(TransposedGraph.this.delegate(), var1.nodeV(), var1.nodeU());
                  }
               });
            }
         };
      }

      public int outDegree(N var1) {
         return this.delegate().inDegree(var1);
      }

      public Set<N> predecessors(N var1) {
         return this.delegate().successors(var1);
      }

      public Set<N> successors(N var1) {
         return this.delegate().predecessors(var1);
      }
   }

   private static class TransposedNetwork<N, E> extends ForwardingNetwork<N, E> {
      private final Network<N, E> network;

      TransposedNetwork(Network<N, E> var1) {
         this.network = var1;
      }

      protected Network<N, E> delegate() {
         return this.network;
      }

      public E edgeConnectingOrNull(EndpointPair<N> var1) {
         return this.delegate().edgeConnectingOrNull(Graphs.transpose(var1));
      }

      public E edgeConnectingOrNull(N var1, N var2) {
         return this.delegate().edgeConnectingOrNull(var2, var1);
      }

      public Set<E> edgesConnecting(EndpointPair<N> var1) {
         return this.delegate().edgesConnecting(Graphs.transpose(var1));
      }

      public Set<E> edgesConnecting(N var1, N var2) {
         return this.delegate().edgesConnecting(var2, var1);
      }

      public boolean hasEdgeConnecting(EndpointPair<N> var1) {
         return this.delegate().hasEdgeConnecting(Graphs.transpose(var1));
      }

      public boolean hasEdgeConnecting(N var1, N var2) {
         return this.delegate().hasEdgeConnecting(var2, var1);
      }

      public int inDegree(N var1) {
         return this.delegate().outDegree(var1);
      }

      public Set<E> inEdges(N var1) {
         return this.delegate().outEdges(var1);
      }

      public EndpointPair<N> incidentNodes(E var1) {
         EndpointPair var2 = this.delegate().incidentNodes(var1);
         return EndpointPair.of(this.network, var2.nodeV(), var2.nodeU());
      }

      public int outDegree(N var1) {
         return this.delegate().inDegree(var1);
      }

      public Set<E> outEdges(N var1) {
         return this.delegate().inEdges(var1);
      }

      public Set<N> predecessors(N var1) {
         return this.delegate().successors(var1);
      }

      public Set<N> successors(N var1) {
         return this.delegate().predecessors(var1);
      }
   }

   private static class TransposedValueGraph<N, V> extends ForwardingValueGraph<N, V> {
      private final ValueGraph<N, V> graph;

      TransposedValueGraph(ValueGraph<N, V> var1) {
         this.graph = var1;
      }

      protected ValueGraph<N, V> delegate() {
         return this.graph;
      }

      @NullableDecl
      public V edgeValueOrDefault(EndpointPair<N> var1, @NullableDecl V var2) {
         return this.delegate().edgeValueOrDefault(Graphs.transpose(var1), var2);
      }

      @NullableDecl
      public V edgeValueOrDefault(N var1, N var2, @NullableDecl V var3) {
         return this.delegate().edgeValueOrDefault(var2, var1, var3);
      }

      public boolean hasEdgeConnecting(EndpointPair<N> var1) {
         return this.delegate().hasEdgeConnecting(Graphs.transpose(var1));
      }

      public boolean hasEdgeConnecting(N var1, N var2) {
         return this.delegate().hasEdgeConnecting(var2, var1);
      }

      public int inDegree(N var1) {
         return this.delegate().outDegree(var1);
      }

      public int outDegree(N var1) {
         return this.delegate().inDegree(var1);
      }

      public Set<N> predecessors(N var1) {
         return this.delegate().successors(var1);
      }

      public Set<N> successors(N var1) {
         return this.delegate().predecessors(var1);
      }
   }
}
