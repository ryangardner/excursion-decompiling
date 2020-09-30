/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ByFunctionOrdering<F, T>
extends Ordering<F>
implements Serializable {
    private static final long serialVersionUID = 0L;
    final Function<F, ? extends T> function;
    final Ordering<T> ordering;

    ByFunctionOrdering(Function<F, ? extends T> function, Ordering<T> ordering) {
        this.function = Preconditions.checkNotNull(function);
        this.ordering = Preconditions.checkNotNull(ordering);
    }

    @Override
    public int compare(F f, F f2) {
        return this.ordering.compare(this.function.apply(f), this.function.apply(f2));
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ByFunctionOrdering)) return false;
        object = (ByFunctionOrdering)object;
        if (!this.function.equals(((ByFunctionOrdering)object).function)) return false;
        if (!this.ordering.equals(((ByFunctionOrdering)object).ordering)) return false;
        return bl;
    }

    public int hashCode() {
        return Objects.hashCode(this.function, this.ordering);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.ordering);
        stringBuilder.append(".onResultOf(");
        stringBuilder.append(this.function);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

