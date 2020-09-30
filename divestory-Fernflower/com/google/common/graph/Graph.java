package com.google.common.graph;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use GraphBuilder to create a real instance")
public interface Graph<N> extends BaseGraph<N> {
   Set<N> adjacentNodes(N var1);

   boolean allowsSelfLoops();

   int degree(N var1);

   Set<EndpointPair<N>> edges();

   boolean equals(@NullableDecl Object var1);

   boolean hasEdgeConnecting(EndpointPair<N> var1);

   boolean hasEdgeConnecting(N var1, N var2);

   int hashCode();

   int inDegree(N var1);

   Set<EndpointPair<N>> incidentEdges(N var1);

   boolean isDirected();

   ElementOrder<N> nodeOrder();

   Set<N> nodes();

   int outDegree(N var1);

   Set<N> predecessors(N var1);

   Set<N> successors(N var1);
}
