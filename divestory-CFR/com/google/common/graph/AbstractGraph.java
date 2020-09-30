/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.AbstractBaseGraph;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractGraph<N>
extends AbstractBaseGraph<N>
implements Graph<N> {
    @Override
    public final boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Graph)) {
            return false;
        }
        object = (Graph)object;
        if (this.isDirected() != object.isDirected()) return false;
        if (!this.nodes().equals(object.nodes())) return false;
        if (!this.edges().equals(object.edges())) return false;
        return bl;
    }

    @Override
    public final int hashCode() {
        return this.edges().hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isDirected: ");
        stringBuilder.append(this.isDirected());
        stringBuilder.append(", allowsSelfLoops: ");
        stringBuilder.append(this.allowsSelfLoops());
        stringBuilder.append(", nodes: ");
        stringBuilder.append(this.nodes());
        stringBuilder.append(", edges: ");
        stringBuilder.append(this.edges());
        return stringBuilder.toString();
    }
}

