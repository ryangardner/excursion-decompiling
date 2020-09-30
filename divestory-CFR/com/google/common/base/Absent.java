/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.Collections;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Absent<T>
extends Optional<T> {
    static final Absent<Object> INSTANCE = new Absent<T>();
    private static final long serialVersionUID = 0L;

    private Absent() {
    }

    private Object readResolve() {
        return INSTANCE;
    }

    static <T> Optional<T> withType() {
        return INSTANCE;
    }

    @Override
    public Set<T> asSet() {
        return Collections.emptySet();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object != this) return false;
        return true;
    }

    @Override
    public T get() {
        throw new IllegalStateException("Optional.get() cannot be called on an absent value");
    }

    @Override
    public int hashCode() {
        return 2040732332;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public Optional<T> or(Optional<? extends T> optional) {
        return Preconditions.checkNotNull(optional);
    }

    @Override
    public T or(Supplier<? extends T> supplier) {
        return Preconditions.checkNotNull(supplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
    }

    @Override
    public T or(T t) {
        return Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)");
    }

    @NullableDecl
    @Override
    public T orNull() {
        return null;
    }

    @Override
    public String toString() {
        return "Optional.absent()";
    }

    @Override
    public <V> Optional<V> transform(Function<? super T, V> function) {
        Preconditions.checkNotNull(function);
        return Optional.absent();
    }
}

