/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.EndpointPair;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

interface GraphConnections<N, V> {
    public void addPredecessor(N var1, V var2);

    public V addSuccessor(N var1, V var2);

    public Set<N> adjacentNodes();

    public Iterator<EndpointPair<N>> incidentEdgeIterator(N var1);

    public Set<N> predecessors();

    public void removePredecessor(N var1);

    public V removeSuccessor(N var1);

    public Set<N> successors();

    @NullableDecl
    public V value(N var1);
}

