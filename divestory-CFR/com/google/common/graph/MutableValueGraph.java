/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;

public interface MutableValueGraph<N, V>
extends ValueGraph<N, V> {
    public boolean addNode(N var1);

    public V putEdgeValue(EndpointPair<N> var1, V var2);

    public V putEdgeValue(N var1, N var2, V var3);

    public V removeEdge(EndpointPair<N> var1);

    public V removeEdge(N var1, N var2);

    public boolean removeNode(N var1);
}

