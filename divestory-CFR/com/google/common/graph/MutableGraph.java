/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;

public interface MutableGraph<N>
extends Graph<N> {
    public boolean addNode(N var1);

    public boolean putEdge(EndpointPair<N> var1);

    public boolean putEdge(N var1, N var2);

    public boolean removeEdge(EndpointPair<N> var1);

    public boolean removeEdge(N var1, N var2);

    public boolean removeNode(N var1);
}

