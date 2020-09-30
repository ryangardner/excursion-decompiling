package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;

@Immutable(
   containerOf = {"N", "V"}
)
public final class ImmutableValueGraph<N, V> extends ConfigurableValueGraph<N, V> {
   private ImmutableValueGraph(ValueGraph<N, V> var1) {
      super(ValueGraphBuilder.from(var1), getNodeConnections(var1), (long)var1.edges().size());
   }

   private static <N, V> GraphConnections<N, V> connectionsOf(final ValueGraph<N, V> var0, final N var1) {
      Function var2 = new Function<N, V>() {
         public V apply(N var1x) {
            return var0.edgeValueOrDefault(var1, var1x, (Object)null);
         }
      };
      Object var3;
      if (var0.isDirected()) {
         var3 = DirectedGraphConnections.ofImmutable(var1, var0.incidentEdges(var1), var2);
      } else {
         var3 = UndirectedGraphConnections.ofImmutable(Maps.asMap(var0.adjacentNodes(var1), var2));
      }

      return (GraphConnections)var3;
   }

   @Deprecated
   public static <N, V> ImmutableValueGraph<N, V> copyOf(ImmutableValueGraph<N, V> var0) {
      return (ImmutableValueGraph)Preconditions.checkNotNull(var0);
   }

   public static <N, V> ImmutableValueGraph<N, V> copyOf(ValueGraph<N, V> var0) {
      ImmutableValueGraph var1;
      if (var0 instanceof ImmutableValueGraph) {
         var1 = (ImmutableValueGraph)var0;
      } else {
         var1 = new ImmutableValueGraph(var0);
      }

      return var1;
   }

   private static <N, V> ImmutableMap<N, GraphConnections<N, V>> getNodeConnections(ValueGraph<N, V> var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      Iterator var2 = var0.nodes().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.put(var3, connectionsOf(var0, var3));
      }

      return var1.build();
   }

   public ImmutableGraph<N> asGraph() {
      return new ImmutableGraph(this);
   }

   public static class Builder<N, V> {
      private final MutableValueGraph<N, V> mutableValueGraph;

      Builder(ValueGraphBuilder<N, V> var1) {
         this.mutableValueGraph = var1.build();
      }

      public ImmutableValueGraph.Builder<N, V> addNode(N var1) {
         this.mutableValueGraph.addNode(var1);
         return this;
      }

      public ImmutableValueGraph<N, V> build() {
         return ImmutableValueGraph.copyOf((ValueGraph)this.mutableValueGraph);
      }

      public ImmutableValueGraph.Builder<N, V> putEdgeValue(EndpointPair<N> var1, V var2) {
         this.mutableValueGraph.putEdgeValue(var1, var2);
         return this;
      }

      public ImmutableValueGraph.Builder<N, V> putEdgeValue(N var1, N var2, V var3) {
         this.mutableValueGraph.putEdgeValue(var1, var2, var3);
         return this;
      }
   }
}
