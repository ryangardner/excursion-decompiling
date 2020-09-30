/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.graph.AbstractGraph;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.Network;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractNetwork<N, E>
implements Network<N, E> {
    private Predicate<E> connectedPredicate(final N n, final N n2) {
        return new Predicate<E>(){

            @Override
            public boolean apply(E e) {
                return AbstractNetwork.this.incidentNodes(e).adjacentNode(n).equals(n2);
            }
        };
    }

    private static <N, E> Map<E, EndpointPair<N>> edgeIncidentNodesMap(final Network<N, E> network) {
        Function function = new Function<E, EndpointPair<N>>(){

            @Override
            public EndpointPair<N> apply(E e) {
                return network.incidentNodes(e);
            }
        };
        return Maps.asMap(network.edges(), function);
    }

    @Override
    public Set<E> adjacentEdges(E e) {
        EndpointPair endpointPair = this.incidentNodes(e);
        return Sets.difference(Sets.union(this.incidentEdges(endpointPair.nodeU()), this.incidentEdges(endpointPair.nodeV())), ImmutableSet.of(e));
    }

    @Override
    public Graph<N> asGraph() {
        return new AbstractGraph<N>(){

            @Override
            public Set<N> adjacentNodes(N n) {
                return AbstractNetwork.this.adjacentNodes(n);
            }

            @Override
            public boolean allowsSelfLoops() {
                return AbstractNetwork.this.allowsSelfLoops();
            }

            @Override
            public Set<EndpointPair<N>> edges() {
                if (!AbstractNetwork.this.allowsParallelEdges()) return new AbstractSet<EndpointPair<N>>(){

                    @Override
                    public boolean contains(@NullableDecl Object object) {
                        boolean bl = object instanceof EndpointPair;
                        boolean bl2 = false;
                        if (!bl) {
                            return false;
                        }
                        object = (EndpointPair)object;
                        bl = bl2;
                        if (!this.isOrderingCompatible((EndpointPair<?>)object)) return bl;
                        bl = bl2;
                        if (!this.nodes().contains(((EndpointPair)object).nodeU())) return bl;
                        bl = bl2;
                        if (!this.successors(((EndpointPair)object).nodeU()).contains(((EndpointPair)object).nodeV())) return bl;
                        return true;
                    }

                    @Override
                    public Iterator<EndpointPair<N>> iterator() {
                        return Iterators.transform(AbstractNetwork.this.edges().iterator(), new Function<E, EndpointPair<N>>(){

                            @Override
                            public EndpointPair<N> apply(E e) {
                                return AbstractNetwork.this.incidentNodes(e);
                            }
                        });
                    }

                    @Override
                    public int size() {
                        return AbstractNetwork.this.edges().size();
                    }

                };
                return super.edges();
            }

            @Override
            public boolean isDirected() {
                return AbstractNetwork.this.isDirected();
            }

            @Override
            public ElementOrder<N> nodeOrder() {
                return AbstractNetwork.this.nodeOrder();
            }

            @Override
            public Set<N> nodes() {
                return AbstractNetwork.this.nodes();
            }

            @Override
            public Set<N> predecessors(N n) {
                return AbstractNetwork.this.predecessors(n);
            }

            @Override
            public Set<N> successors(N n) {
                return AbstractNetwork.this.successors(n);
            }

        };
    }

    @Override
    public int degree(N n) {
        if (!this.isDirected()) return IntMath.saturatedAdd(this.incidentEdges(n).size(), this.edgesConnecting(n, n).size());
        return IntMath.saturatedAdd(this.inEdges(n).size(), this.outEdges(n).size());
    }

    @NullableDecl
    @Override
    public E edgeConnectingOrNull(EndpointPair<N> endpointPair) {
        this.validateEndpoints(endpointPair);
        return this.edgeConnectingOrNull(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @NullableDecl
    @Override
    public E edgeConnectingOrNull(N n, N n2) {
        Set<E> set = this.edgesConnecting(n, n2);
        int n3 = set.size();
        if (n3 == 0) return null;
        if (n3 != 1) throw new IllegalArgumentException(String.format("Cannot call edgeConnecting() when parallel edges exist between %s and %s. Consider calling edgesConnecting() instead.", n, n2));
        return set.iterator().next();
    }

    @Override
    public Set<E> edgesConnecting(EndpointPair<N> endpointPair) {
        this.validateEndpoints(endpointPair);
        return this.edgesConnecting(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override
    public Set<E> edgesConnecting(N object, N n) {
        Set set = this.outEdges(object);
        Set set2 = this.inEdges(n);
        if (set.size() > set2.size()) return Collections.unmodifiableSet(Sets.filter(set2, this.connectedPredicate(n, object)));
        return Collections.unmodifiableSet(Sets.filter(set, this.connectedPredicate(object, n)));
    }

    @Override
    public final boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Network)) {
            return false;
        }
        object = (Network)object;
        if (this.isDirected() != object.isDirected()) return false;
        if (!this.nodes().equals(object.nodes())) return false;
        if (!AbstractNetwork.edgeIncidentNodesMap(this).equals(AbstractNetwork.edgeIncidentNodesMap(object))) return false;
        return bl;
    }

    @Override
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        if (this.isOrderingCompatible(endpointPair)) return this.hasEdgeConnecting(endpointPair.nodeU(), endpointPair.nodeV());
        return false;
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
    public final int hashCode() {
        return AbstractNetwork.edgeIncidentNodesMap(this).hashCode();
    }

    @Override
    public int inDegree(N n) {
        if (!this.isDirected()) return this.degree(n);
        return this.inEdges(n).size();
    }

    protected final boolean isOrderingCompatible(EndpointPair<?> endpointPair) {
        if (endpointPair.isOrdered()) return true;
        if (!this.isDirected()) return true;
        return false;
    }

    @Override
    public int outDegree(N n) {
        if (!this.isDirected()) return this.degree(n);
        return this.outEdges(n).size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isDirected: ");
        stringBuilder.append(this.isDirected());
        stringBuilder.append(", allowsParallelEdges: ");
        stringBuilder.append(this.allowsParallelEdges());
        stringBuilder.append(", allowsSelfLoops: ");
        stringBuilder.append(this.allowsSelfLoops());
        stringBuilder.append(", nodes: ");
        stringBuilder.append(this.nodes());
        stringBuilder.append(", edges: ");
        stringBuilder.append(AbstractNetwork.edgeIncidentNodesMap(this));
        return stringBuilder.toString();
    }

    protected final void validateEndpoints(EndpointPair<?> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        Preconditions.checkArgument(this.isOrderingCompatible(endpointPair), "Mismatch: unordered endpoints cannot be used with directed graphs");
    }

}

