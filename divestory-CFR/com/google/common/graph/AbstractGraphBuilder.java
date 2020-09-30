/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.graph.ElementOrder;

abstract class AbstractGraphBuilder<N> {
    boolean allowsSelfLoops = false;
    final boolean directed;
    Optional<Integer> expectedNodeCount = Optional.absent();
    ElementOrder<N> incidentEdgeOrder = ElementOrder.unordered();
    ElementOrder<N> nodeOrder = ElementOrder.insertion();

    AbstractGraphBuilder(boolean bl) {
        this.directed = bl;
    }
}

