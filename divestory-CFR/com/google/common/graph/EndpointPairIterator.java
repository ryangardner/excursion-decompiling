/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.EndpointPair;
import java.util.Iterator;
import java.util.Set;

abstract class EndpointPairIterator<N>
extends AbstractIterator<EndpointPair<N>> {
    private final BaseGraph<N> graph;
    protected N node = null;
    private final Iterator<N> nodeIterator;
    protected Iterator<N> successorIterator = ImmutableSet.of().iterator();

    private EndpointPairIterator(BaseGraph<N> baseGraph) {
        this.graph = baseGraph;
        this.nodeIterator = baseGraph.nodes().iterator();
    }

    static <N> EndpointPairIterator<N> of(BaseGraph<N> undirected) {
        if (!undirected.isDirected()) return new Undirected((BaseGraph)((Object)undirected));
        return new Directed((BaseGraph)((Object)undirected));
    }

    protected final boolean advance() {
        Preconditions.checkState(this.successorIterator.hasNext() ^ true);
        if (!this.nodeIterator.hasNext()) {
            return false;
        }
        N n = this.nodeIterator.next();
        this.node = n;
        this.successorIterator = this.graph.successors(n).iterator();
        return true;
    }

    private static final class Directed<N>
    extends EndpointPairIterator<N> {
        private Directed(BaseGraph<N> baseGraph) {
            super(baseGraph);
        }

        @Override
        protected EndpointPair<N> computeNext() {
            do {
                if (!this.successorIterator.hasNext()) continue;
                return EndpointPair.ordered(this.node, this.successorIterator.next());
            } while (this.advance());
            return (EndpointPair)this.endOfData();
        }
    }

    private static final class Undirected<N>
    extends EndpointPairIterator<N> {
        private Set<N> visitedNodes;

        private Undirected(BaseGraph<N> baseGraph) {
            super(baseGraph);
            this.visitedNodes = Sets.newHashSetWithExpectedSize(baseGraph.nodes().size());
        }

        /*
         * Unable to fully structure code
         */
        @Override
        protected EndpointPair<N> computeNext() {
            do lbl-1000: // 3 sources:
            {
                block1 : {
                    if (!this.successorIterator.hasNext()) break block1;
                    var1_1 = this.successorIterator.next();
                    if (this.visitedNodes.contains(var1_1)) ** GOTO lbl-1000
                    return EndpointPair.unordered(this.node, var1_1);
                }
                this.visitedNodes.add(this.node);
            } while (this.advance());
            this.visitedNodes = null;
            return (EndpointPair)this.endOfData();
        }
    }

}

