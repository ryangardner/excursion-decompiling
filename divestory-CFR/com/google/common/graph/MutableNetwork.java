/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Network;

public interface MutableNetwork<N, E>
extends Network<N, E> {
    public boolean addEdge(EndpointPair<N> var1, E var2);

    public boolean addEdge(N var1, N var2, E var3);

    public boolean addNode(N var1);

    public boolean removeEdge(E var1);

    public boolean removeNode(N var1);
}

