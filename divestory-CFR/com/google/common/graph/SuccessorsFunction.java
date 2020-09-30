/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.errorprone.annotations.DoNotMock;

@DoNotMock(value="Implement with a lambda, or use GraphBuilder to build a Graph with the desired edges")
public interface SuccessorsFunction<N> {
    public Iterable<? extends N> successors(N var1);
}

