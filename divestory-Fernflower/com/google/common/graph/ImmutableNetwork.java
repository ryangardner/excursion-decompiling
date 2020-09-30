package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;
import java.util.Map;

@Immutable(
   containerOf = {"N", "E"}
)
public final class ImmutableNetwork<N, E> extends ConfigurableNetwork<N, E> {
   private ImmutableNetwork(Network<N, E> var1) {
      super(NetworkBuilder.from(var1), getNodeConnections(var1), getEdgeToReferenceNode(var1));
   }

   private static <N, E> Function<E, N> adjacentNodeFn(final Network<N, E> var0, final N var1) {
      return new Function<E, N>() {
         public N apply(E var1x) {
            return var0.incidentNodes(var1x).adjacentNode(var1);
         }
      };
   }

   private static <N, E> NetworkConnections<N, E> connectionsOf(Network<N, E> var0, N var1) {
      Object var5;
      if (var0.isDirected()) {
         Map var2 = Maps.asMap(var0.inEdges(var1), sourceNodeFn(var0));
         Map var3 = Maps.asMap(var0.outEdges(var1), targetNodeFn(var0));
         int var4 = var0.edgesConnecting(var1, var1).size();
         if (var0.allowsParallelEdges()) {
            var5 = DirectedMultiNetworkConnections.ofImmutable(var2, var3, var4);
         } else {
            var5 = DirectedNetworkConnections.ofImmutable(var2, var3, var4);
         }

         return (NetworkConnections)var5;
      } else {
         Map var6 = Maps.asMap(var0.incidentEdges(var1), adjacentNodeFn(var0, var1));
         if (var0.allowsParallelEdges()) {
            var5 = UndirectedMultiNetworkConnections.ofImmutable(var6);
         } else {
            var5 = UndirectedNetworkConnections.ofImmutable(var6);
         }

         return (NetworkConnections)var5;
      }
   }

   @Deprecated
   public static <N, E> ImmutableNetwork<N, E> copyOf(ImmutableNetwork<N, E> var0) {
      return (ImmutableNetwork)Preconditions.checkNotNull(var0);
   }

   public static <N, E> ImmutableNetwork<N, E> copyOf(Network<N, E> var0) {
      ImmutableNetwork var1;
      if (var0 instanceof ImmutableNetwork) {
         var1 = (ImmutableNetwork)var0;
      } else {
         var1 = new ImmutableNetwork(var0);
      }

      return var1;
   }

   private static <N, E> Map<E, N> getEdgeToReferenceNode(Network<N, E> var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      Iterator var2 = var0.edges().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.put(var3, var0.incidentNodes(var3).nodeU());
      }

      return var1.build();
   }

   private static <N, E> Map<N, NetworkConnections<N, E>> getNodeConnections(Network<N, E> var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      Iterator var2 = var0.nodes().iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.put(var3, connectionsOf(var0, var3));
      }

      return var1.build();
   }

   private static <N, E> Function<E, N> sourceNodeFn(final Network<N, E> var0) {
      return new Function<E, N>() {
         public N apply(E var1) {
            return var0.incidentNodes(var1).source();
         }
      };
   }

   private static <N, E> Function<E, N> targetNodeFn(final Network<N, E> var0) {
      return new Function<E, N>() {
         public N apply(E var1) {
            return var0.incidentNodes(var1).target();
         }
      };
   }

   public ImmutableGraph<N> asGraph() {
      return new ImmutableGraph(super.asGraph());
   }

   public static class Builder<N, E> {
      private final MutableNetwork<N, E> mutableNetwork;

      Builder(NetworkBuilder<N, E> var1) {
         this.mutableNetwork = var1.build();
      }

      public ImmutableNetwork.Builder<N, E> addEdge(EndpointPair<N> var1, E var2) {
         this.mutableNetwork.addEdge(var1, var2);
         return this;
      }

      public ImmutableNetwork.Builder<N, E> addEdge(N var1, N var2, E var3) {
         this.mutableNetwork.addEdge(var1, var2, var3);
         return this;
      }

      public ImmutableNetwork.Builder<N, E> addNode(N var1) {
         this.mutableNetwork.addNode(var1);
         return this;
      }

      public ImmutableNetwork<N, E> build() {
         return ImmutableNetwork.copyOf((Network)this.mutableNetwork);
      }
   }
}
