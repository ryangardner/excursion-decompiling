/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Interner;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MapMakerInternalMap;

public final class Interners {
    private Interners() {
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction<E>(Preconditions.checkNotNull(interner));
    }

    public static InternerBuilder newBuilder() {
        return new InternerBuilder();
    }

    public static <E> Interner<E> newStrongInterner() {
        return Interners.newBuilder().strong().build();
    }

    public static <E> Interner<E> newWeakInterner() {
        return Interners.newBuilder().weak().build();
    }

    public static class InternerBuilder {
        private final MapMaker mapMaker = new MapMaker();
        private boolean strong = true;

        private InternerBuilder() {
        }

        public <E> Interner<E> build() {
            if (this.strong) return new InternerImpl(this.mapMaker);
            this.mapMaker.weakKeys();
            return new InternerImpl(this.mapMaker);
        }

        public InternerBuilder concurrencyLevel(int n) {
            this.mapMaker.concurrencyLevel(n);
            return this;
        }

        public InternerBuilder strong() {
            this.strong = true;
            return this;
        }

        public InternerBuilder weak() {
            this.strong = false;
            return this;
        }
    }

    private static class InternerFunction<E>
    implements Function<E, E> {
        private final Interner<E> interner;

        public InternerFunction(Interner<E> interner) {
            this.interner = interner;
        }

        @Override
        public E apply(E e) {
            return this.interner.intern(e);
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof InternerFunction)) return false;
            object = (InternerFunction)object;
            return this.interner.equals(((InternerFunction)object).interner);
        }

        public int hashCode() {
            return this.interner.hashCode();
        }
    }

    static final class InternerImpl<E>
    implements Interner<E> {
        final MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> map;

        private InternerImpl(MapMaker mapMaker) {
            this.map = MapMakerInternalMap.createWithDummyValues(mapMaker.keyEquivalence(Equivalence.equals()));
        }

        @Override
        public E intern(E e) {
            do {
                Object object;
                if ((object = this.map.getEntry(e)) == null || (object = object.getKey()) == null) continue;
                return (E)object;
            } while (this.map.putIfAbsent(e, MapMaker.Dummy.VALUE) != null);
            return e;
        }
    }

}

