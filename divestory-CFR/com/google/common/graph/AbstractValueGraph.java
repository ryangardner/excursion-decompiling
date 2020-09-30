/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.graph.AbstractBaseGraph;
import com.google.common.graph.AbstractGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.ValueGraph;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractValueGraph<N, V>
extends AbstractBaseGraph<N>
implements ValueGraph<N, V> {
    private static <N, V> Map<EndpointPair<N>, V> edgeValueMap(final ValueGraph<N, V> valueGraph) {
        Function function = new Function<EndpointPair<N>, V>(){

            @Override
            public V apply(EndpointPair<N> endpointPair) {
                return valueGraph.edgeValueOrDefault(endpointPair.nodeU(), endpointPair.nodeV(), null);
            }
        };
        return Maps.asMap(valueGraph.edges(), function);
    }

    @Override
    public Graph<N> asGraph() {
        return new AbstractGraph<N>(){

            @Override
            public Set<N> adjacentNodes(N n) {
                return AbstractValueGraph.this.adjacentNodes(n);
            }

            @Override
            public boolean allowsSelfLoops() {
                return AbstractValueGraph.this.allowsSelfLoops();
            }

            @Override
            public int degree(N n) {
                return AbstractValueGraph.this.degree(n);
            }

            @Override
            public Set<EndpointPair<N>> edges() {
                return AbstractValueGraph.this.edges();
            }

            @Override
            public int inDegree(N n) {
                return AbstractValueGraph.this.inDegree(n);
            }

            @Override
            public boolean isDirected() {
                return AbstractValueGraph.this.isDirected();
            }

            @Override
            public ElementOrder<N> nodeOrder() {
                return AbstractValueGraph.this.nodeOrder();
            }

            @Override
            public Set<N> nodes() {
                return AbstractValueGraph.this.nodes();
            }

            @Override
            public int outDegree(N n) {
                return AbstractValueGraph.this.outDegree(n);
            }

            @Override
            public Set<N> predecessors(N n) {
                return AbstractValueGraph.this.predecessors(n);
            }

            @Override
            public Set<N> successors(N n) {
                return AbstractValueGraph.this.successors(n);
            }
        };
    }

    @Override
    public final boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ValueGraph)) {
            return false;
        }
        object = (ValueGraph)object;
        if (this.isDirected() != object.isDirected()) return false;
        if (!this.nodes().equals(object.nodes())) return false;
        if (!AbstractValueGraph.edgeValueMap(this).equals(AbstractValueGraph.edgeValueMap(object))) return false;
        return bl;
    }

    @Override
    public final int hashCode() {
        return AbstractValueGraph.edgeValueMap(this).hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isDirected: ");
        stringBuilder.append(this.isDirected());
        stringBuilder.append(", allowsSelfLoops: ");
        stringBuilder.append(this.allowsSelfLoops());
        stringBuilder.append(", nodes: ");
        stringBuilder.append(this.nodes());
        stringBuilder.append(", edges: ");
        stringBuilder.append(AbstractValueGraph.edgeValueMap(this));
        return stringBuilder.toString();
    }

}

