/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import java.util.Set;

interface NetworkConnections<N, E> {
    public void addInEdge(E var1, N var2, boolean var3);

    public void addOutEdge(E var1, N var2);

    public N adjacentNode(E var1);

    public Set<N> adjacentNodes();

    public Set<E> edgesConnecting(N var1);

    public Set<E> inEdges();

    public Set<E> incidentEdges();

    public Set<E> outEdges();

    public Set<N> predecessors();

    public N removeInEdge(E var1, boolean var2);

    public N removeOutEdge(E var1);

    public Set<N> successors();
}

