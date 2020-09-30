package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotMock;

@DoNotMock
public final class GraphBuilder<N> extends AbstractGraphBuilder<N> {
   private GraphBuilder(boolean var1) {
      super(var1);
   }

   private <N1 extends N> GraphBuilder<N1> cast() {
      return this;
   }

   public static GraphBuilder<Object> directed() {
      return new GraphBuilder(true);
   }

   public static <N> GraphBuilder<N> from(Graph<N> var0) {
      return (new GraphBuilder(var0.isDirected())).allowsSelfLoops(var0.allowsSelfLoops()).nodeOrder(var0.nodeOrder());
   }

   public static GraphBuilder<Object> undirected() {
      return new GraphBuilder(false);
   }

   public GraphBuilder<N> allowsSelfLoops(boolean var1) {
      this.allowsSelfLoops = var1;
      return this;
   }

   public <N1 extends N> MutableGraph<N1> build() {
      return new ConfigurableMutableGraph(this);
   }

   GraphBuilder<N> copy() {
      GraphBuilder var1 = new GraphBuilder(this.directed);
      var1.allowsSelfLoops = this.allowsSelfLoops;
      var1.nodeOrder = this.nodeOrder;
      var1.expectedNodeCount = this.expectedNodeCount;
      var1.incidentEdgeOrder = this.incidentEdgeOrder;
      return var1;
   }

   public GraphBuilder<N> expectedNodeCount(int var1) {
      this.expectedNodeCount = Optional.of(Graphs.checkNonNegative(var1));
      return this;
   }

   public <N1 extends N> ImmutableGraph.Builder<N1> immutable() {
      return new ImmutableGraph.Builder(this.cast());
   }

   <N1 extends N> GraphBuilder<N1> incidentEdgeOrder(ElementOrder<N1> var1) {
      boolean var2;
      if (var1.type() != ElementOrder.Type.UNORDERED && var1.type() != ElementOrder.Type.STABLE) {
         var2 = false;
      } else {
         var2 = true;
      }

      Preconditions.checkArgument(var2, "The given elementOrder (%s) is unsupported. incidentEdgeOrder() only supports ElementOrder.unordered() and ElementOrder.stable().", (Object)var1);
      GraphBuilder var3 = this.cast();
      var3.incidentEdgeOrder = (ElementOrder)Preconditions.checkNotNull(var1);
      return var3;
   }

   public <N1 extends N> GraphBuilder<N1> nodeOrder(ElementOrder<N1> var1) {
      GraphBuilder var2 = this.cast();
      var2.nodeOrder = (ElementOrder)Preconditions.checkNotNull(var1);
      return var2;
   }
}
