package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;

@Immutable(
   containerOf = {"N"}
)
public class ImmutableGraph<N> extends ForwardingGraph<N> {
   private final BaseGraph<N> backingGraph;

   ImmutableGraph(BaseGraph<N> var1) {
      this.backingGraph = var1;
   }

   private static <N> GraphConnections<N, GraphConstants.Presence> connectionsOf(Graph<N> var0, N var1) {
      Function var2 = Functions.constant(GraphConstants.Presence.EDGE_EXISTS);
      Object var3;
      if (var0.isDirected()) {
         var3 = DirectedGraphConnections.ofImmutable(var1, var0.incidentEdges(var1), var2);
      } else {
         var3 = UndirectedGraphConnections.ofImmutable(Maps.asMap(var0.adjacentNodes(var1), var2));
      }

      return (GraphConnections)var3;
   }

   public static <N> ImmutableGraph<N> copyOf(Graph<N> var0) {
      ImmutableGraph var1;
      if (var0 instanceof ImmutableGraph) {
         var1 = (ImmutableGraph)var0;
      } else {
         var1 = new ImmutableGraph(new ConfigurableValueGraph(GraphBuilder.from(var0), getNodeConnections(var0), (long)var0.edges().size()));
      }

      return var1;
   }

   @Deprecated
   public static <N> ImmutableGraph<N> copyOf(ImmutableGraph<N> var0) {
      return (ImmutableGraph)Preconditions.checkNotNull(var0);
   }

   private static <N> ImmutableMap<N, GraphConnections<N, GraphConstants.Presence>> getNodeConnections(Graph<N> var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      Iterator var2 = var0.nodes().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.put(var3, connectionsOf(var0, var3));
      }

      return var1.build();
   }

   protected BaseGraph<N> delegate() {
      return this.backingGraph;
   }

   public static class Builder<N> {
      private final MutableGraph<N> mutableGraph;

      Builder(GraphBuilder<N> var1) {
         this.mutableGraph = var1.copy().incidentEdgeOrder(ElementOrder.stable()).build();
      }

      public ImmutableGraph.Builder<N> addNode(N var1) {
         this.mutableGraph.addNode(var1);
         return this;
      }

      public ImmutableGraph<N> build() {
         return ImmutableGraph.copyOf((Graph)this.mutableGraph);
      }

      public ImmutableGraph.Builder<N> putEdge(EndpointPair<N> var1) {
         this.mutableGraph.putEdge(var1);
         return this;
      }

      public ImmutableGraph.Builder<N> putEdge(N var1, N var2) {
         this.mutableGraph.putEdge(var1, var2);
         return this;
      }
   }
}
