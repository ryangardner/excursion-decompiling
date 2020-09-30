/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.BaseGraph;
import com.google.common.graph.EndpointPair;
import java.util.AbstractSet;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class IncidentEdgeSet<N>
extends AbstractSet<EndpointPair<N>> {
    protected final BaseGraph<N> graph;
    protected final N node;

    IncidentEdgeSet(BaseGraph<N> baseGraph, N n) {
        this.graph = baseGraph;
        this.node = n;
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        boolean bl = object instanceof EndpointPair;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            return false;
        }
        object = (EndpointPair)object;
        if (this.graph.isDirected()) {
            if (!((EndpointPair)object).isOrdered()) {
                return false;
            }
            Object n = ((EndpointPair)object).source();
            object = ((EndpointPair)object).target();
            if (this.node.equals(n)) {
                if (this.graph.successors(this.node).contains(object)) return true;
            }
            bl = bl3;
            if (!this.node.equals(object)) return bl;
            bl = bl3;
            if (!this.graph.predecessors(this.node).contains(n)) return bl;
            return true;
        }
        if (((EndpointPair)object).isOrdered()) {
            return false;
        }
        Set<N> set = this.graph.adjacentNodes(this.node);
        Object n = ((EndpointPair)object).nodeU();
        if (this.node.equals(object = ((EndpointPair)object).nodeV())) {
            if (set.contains(n)) return true;
        }
        bl = bl2;
        if (!this.node.equals(n)) return bl;
        bl = bl2;
        if (!set.contains(object)) return bl;
        return true;
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        if (!this.graph.isDirected()) return this.graph.adjacentNodes(this.node).size();
        return this.graph.inDegree(this.node) + this.graph.outDegree(this.node) - this.graph.successors(this.node).contains(this.node);
    }
}

