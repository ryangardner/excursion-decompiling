/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.BaseGraph;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.EndpointPairIterator;
import com.google.common.graph.IncidentEdgeSet;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractBaseGraph<N>
implements BaseGraph<N> {
    AbstractBaseGraph() {
    }

    @Override
    public int degree(N n) {
        int n2;
        if (this.isDirected()) {
            return IntMath.saturatedAdd(this.predecessors(n).size(), this.successors(n).size());
        }
        Set<N> set = this.adjacentNodes(n);
        if (this.allowsSelfLoops() && set.contains(n)) {
            n2 = 1;
            return IntMath.saturatedAdd(set.size(), n2);
        }
        n2 = 0;
        return IntMath.saturatedAdd(set.size(), n2);
    }

    protected long edgeCount() {
        Iterator iterator2 = this.nodes().iterator();
        long l = 0L;
        while (iterator2.hasNext()) {
            l += (long)this.degree(iterator2.next());
        }
        boolean bl = (1L & l) == 0L;
        Preconditions.checkState(bl);
        return l >>> 1;
    }

    @Override
    public Set<EndpointPair<N>> edges() {
        return new AbstractSet<EndpointPair<N>>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                boolean bl = object instanceof EndpointPair;
                boolean bl2 = false;
                if (!bl) {
                    return false;
                }
                object = (EndpointPair)object;
                bl = bl2;
                if (!AbstractBaseGraph.this.isOrderingCompatible((EndpointPair<?>)object)) return bl;
                bl = bl2;
                if (!AbstractBaseGraph.this.nodes().contains(((EndpointPair)object).nodeU())) return bl;
                bl = bl2;
                if (!AbstractBaseGraph.this.successors(((EndpointPair)object).nodeU()).contains(((EndpointPair)object).nodeV())) return bl;
                return true;
            }

            @Override
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return EndpointPairIterator.of(AbstractBaseGraph.this);
            }

            @Override
            public boolean remove(Object object) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int size() {
                return Ints.saturatedCast(AbstractBaseGraph.this.edgeCount());
            }
        };
    }

    @Override
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        boolean bl = this.isOrderingCompatible(endpointPair);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        N n = endpointPair.nodeU();
        endpointPair = endpointPair.nodeV();
        bl = bl2;
        if (!this.nodes().contains(n)) return bl;
        bl = bl2;
        if (!this.successors(n).contains(endpointPair)) return bl;
        return true;
    }

    @Override
    public boolean hasEdgeConnecting(N n, N n2) {
        Preconditions.checkNotNull(n);
        Preconditions.checkNotNull(n2);
        if (!this.nodes().contains(n)) return false;
        if (!this.successors(n).contains(n2)) return false;
        return true;
    }

    @Override
    public int inDegree(N n) {
        if (!this.isDirected()) return this.degree(n);
        return this.predecessors(n).size();
    }

    @Override
    public Set<EndpointPair<N>> incidentEdges(N n) {
        Preconditions.checkNotNull(n);
        Preconditions.checkArgument(this.nodes().contains(n), "Node %s is not an element of this graph.", n);
        return new IncidentEdgeSet<N>(this, n){

            @Override
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                if (!this.graph.isDirected()) return Iterators.unmodifiableIterator(Iterators.transform(this.graph.adjacentNodes(this.node).iterator(), new Function<N, EndpointPair<N>>(){

                    @Override
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.unordered(node, n);
                    }
                }));
                return Iterators.unmodifiableIterator(Iterators.concat(Iterators.transform(this.graph.predecessors(this.node).iterator(), new Function<N, EndpointPair<N>>(){

                    @Override
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.ordered(n, node);
                    }
                }), Iterators.transform(Sets.difference(this.graph.successors(this.node), ImmutableSet.of(this.node)).iterator(), new Function<N, EndpointPair<N>>(){

                    @Override
                    public EndpointPair<N> apply(N n) {
                        return EndpointPair.ordered(node, n);
                    }
                })));
            }

        };
    }

    protected final boolean isOrderingCompatible(EndpointPair<?> endpointPair) {
        if (endpointPair.isOrdered()) return true;
        if (!this.isDirected()) return true;
        return false;
    }

    @Override
    public int outDegree(N n) {
        if (!this.isDirected()) return this.degree(n);
        return this.successors(n).size();
    }

    protected final void validateEndpoints(EndpointPair<?> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        Preconditions.checkArgument(this.isOrderingCompatible(endpointPair), "Mismatch: unordered endpoints cannot be used with directed graphs");
    }

}

