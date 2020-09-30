/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AggregateFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class CollectionFuture<V, C>
extends AggregateFuture<V, C> {
    private List<Optional<V>> values;

    CollectionFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean bl) {
        super(immutableCollection, bl, true);
        AbstractCollection abstractCollection = immutableCollection.isEmpty() ? ImmutableList.of() : Lists.newArrayListWithCapacity(immutableCollection.size());
        this.values = abstractCollection;
        int n = 0;
        while (n < immutableCollection.size()) {
            this.values.add(null);
            ++n;
        }
    }

    @Override
    final void collectOneValue(int n, @NullableDecl V v) {
        List<Optional<V>> list = this.values;
        if (list == null) return;
        list.set(n, Optional.fromNullable(v));
    }

    abstract C combine(List<Optional<V>> var1);

    @Override
    final void handleAllCompleted() {
        List<Optional<V>> list = this.values;
        if (list == null) return;
        this.set(this.combine(list));
    }

    @Override
    void releaseResources(AggregateFuture.ReleaseResourcesReason releaseResourcesReason) {
        super.releaseResources(releaseResourcesReason);
        this.values = null;
    }

    static final class ListFuture<V>
    extends CollectionFuture<V, List<V>> {
        ListFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean bl) {
            super(immutableCollection, bl);
            this.init();
        }

        @Override
        public List<V> combine(List<Optional<V>> optional) {
            ArrayList<Object> arrayList = Lists.newArrayListWithCapacity(optional.size());
            Iterator<Optional<V>> iterator2 = optional.iterator();
            while (iterator2.hasNext()) {
                optional = iterator2.next();
                optional = optional != null ? optional.orNull() : null;
                arrayList.add(optional);
            }
            return Collections.unmodifiableList(arrayList);
        }
    }

}

