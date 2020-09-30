/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Absent;
import com.google.common.base.AbstractIterator;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Present;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use Optional.of(value) or Optional.absent()")
public abstract class Optional<T>
implements Serializable {
    private static final long serialVersionUID = 0L;

    Optional() {
    }

    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    public static <T> Optional<T> fromNullable(@NullableDecl T object) {
        if (object != null) return new Present<T>(object);
        return Optional.absent();
    }

    public static <T> Optional<T> of(T t) {
        return new Present<T>(Preconditions.checkNotNull(t));
    }

    public static <T> Iterable<T> presentInstances(final Iterable<? extends Optional<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return new AbstractIterator<T>(){
                    private final Iterator<? extends Optional<? extends T>> iterator;
                    {
                        this.iterator = Preconditions.checkNotNull(iterable.iterator());
                    }

                    @Override
                    protected T computeNext() {
                        Optional<T> optional;
                        do {
                            if (!this.iterator.hasNext()) return this.endOfData();
                        } while (!(optional = this.iterator.next()).isPresent());
                        return optional.get();
                    }
                };
            }

        };
    }

    public abstract Set<T> asSet();

    public abstract boolean equals(@NullableDecl Object var1);

    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    public abstract Optional<T> or(Optional<? extends T> var1);

    public abstract T or(Supplier<? extends T> var1);

    public abstract T or(T var1);

    @NullableDecl
    public abstract T orNull();

    public abstract String toString();

    public abstract <V> Optional<V> transform(Function<? super T, V> var1);

}

