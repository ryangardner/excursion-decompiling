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

final class Present<T>
extends Optional<T> {
    private static final long serialVersionUID = 0L;
    private final T reference;

    Present(T t) {
        this.reference = t;
    }

    @Override
    public Set<T> asSet() {
        return Collections.singleton(this.reference);
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof Present)) return false;
        object = (Present)object;
        return this.reference.equals(((Present)object).reference);
    }

    @Override
    public T get() {
        return this.reference;
    }

    @Override
    public int hashCode() {
        return this.reference.hashCode() + 1502476572;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public Optional<T> or(Optional<? extends T> optional) {
        Preconditions.checkNotNull(optional);
        return this;
    }

    @Override
    public T or(Supplier<? extends T> supplier) {
        Preconditions.checkNotNull(supplier);
        return this.reference;
    }

    @Override
    public T or(T t) {
        Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)");
        return this.reference;
    }

    @Override
    public T orNull() {
        return this.reference;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Optional.of(");
        stringBuilder.append(this.reference);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public <V> Optional<V> transform(Function<? super T, V> function) {
        return new Present<V>(Preconditions.checkNotNull(function.apply(this.reference), "the Function passed to Optional.transform() must not return null."));
    }
}

