/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.graph.Graph;
import com.google.common.graph.Network;
import com.google.errorprone.annotations.Immutable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf={"N"})
public abstract class EndpointPair<N>
implements Iterable<N> {
    private final N nodeU;
    private final N nodeV;

    private EndpointPair(N n, N n2) {
        this.nodeU = Preconditions.checkNotNull(n);
        this.nodeV = Preconditions.checkNotNull(n2);
    }

    static <N> EndpointPair<N> of(Graph<?> endpointPair, N n, N n2) {
        if (!endpointPair.isDirected()) return EndpointPair.unordered(n, n2);
        return EndpointPair.ordered(n, n2);
    }

    static <N> EndpointPair<N> of(Network<?, ?> endpointPair, N n, N n2) {
        if (!endpointPair.isDirected()) return EndpointPair.unordered(n, n2);
        return EndpointPair.ordered(n, n2);
    }

    public static <N> EndpointPair<N> ordered(N n, N n2) {
        return new Ordered(n, n2);
    }

    public static <N> EndpointPair<N> unordered(N n, N n2) {
        return new Unordered(n2, n);
    }

    public final N adjacentNode(Object object) {
        if (object.equals(this.nodeU)) {
            return this.nodeV;
        }
        if (object.equals(this.nodeV)) {
            return this.nodeU;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EndpointPair ");
        stringBuilder.append(this);
        stringBuilder.append(" does not contain node ");
        stringBuilder.append(object);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public abstract boolean equals(@NullableDecl Object var1);

    public abstract int hashCode();

    public abstract boolean isOrdered();

    @Override
    public final UnmodifiableIterator<N> iterator() {
        return Iterators.forArray(this.nodeU, this.nodeV);
    }

    public final N nodeU() {
        return this.nodeU;
    }

    public final N nodeV() {
        return this.nodeV;
    }

    public abstract N source();

    public abstract N target();

    private static final class Ordered<N>
    extends EndpointPair<N> {
        private Ordered(N n, N n2) {
            super(n, n2);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof EndpointPair)) {
                return false;
            }
            object = (EndpointPair)object;
            if (this.isOrdered() != ((EndpointPair)object).isOrdered()) {
                return false;
            }
            if (!this.source().equals(((EndpointPair)object).source())) return false;
            if (!this.target().equals(((EndpointPair)object).target())) return false;
            return bl;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.source(), this.target());
        }

        @Override
        public boolean isOrdered() {
            return true;
        }

        @Override
        public N source() {
            return this.nodeU();
        }

        @Override
        public N target() {
            return this.nodeV();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<");
            stringBuilder.append(this.source());
            stringBuilder.append(" -> ");
            stringBuilder.append(this.target());
            stringBuilder.append(">");
            return stringBuilder.toString();
        }
    }

    private static final class Unordered<N>
    extends EndpointPair<N> {
        private Unordered(N n, N n2) {
            super(n, n2);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof EndpointPair)) {
                return false;
            }
            object = (EndpointPair)object;
            if (this.isOrdered() != ((EndpointPair)object).isOrdered()) {
                return false;
            }
            if (this.nodeU().equals(((EndpointPair)object).nodeU())) {
                return this.nodeV().equals(((EndpointPair)object).nodeV());
            }
            if (!this.nodeU().equals(((EndpointPair)object).nodeV())) return false;
            if (!this.nodeV().equals(((EndpointPair)object).nodeU())) return false;
            return bl;
        }

        @Override
        public int hashCode() {
            return this.nodeU().hashCode() + this.nodeV().hashCode();
        }

        @Override
        public boolean isOrdered() {
            return false;
        }

        @Override
        public N source() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        @Override
        public N target() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(this.nodeU());
            stringBuilder.append(", ");
            stringBuilder.append(this.nodeV());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

}

