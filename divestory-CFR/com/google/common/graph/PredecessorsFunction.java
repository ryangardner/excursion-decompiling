/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.errorprone.annotations.DoNotMock;

@DoNotMock(value="Implement with a lambda, or use GraphBuilder to build a Graph with the desired edges")
public interface PredecessorsFunction<N> {
    public Iterable<? extends N> predecessors(N var1);
}

