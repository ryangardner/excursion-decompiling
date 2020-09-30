/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class MultiEdgesConnecting<E>
extends AbstractSet<E> {
    private final Map<E, ?> outEdgeToNode;
    private final Object targetNode;

    MultiEdgesConnecting(Map<E, ?> map, Object object) {
        this.outEdgeToNode = Preconditions.checkNotNull(map);
        this.targetNode = Preconditions.checkNotNull(object);
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        return this.targetNode.equals(this.outEdgeToNode.get(object));
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return new AbstractIterator<E>(this.outEdgeToNode.entrySet().iterator()){
            final /* synthetic */ Iterator val$entries;
            {
                this.val$entries = iterator2;
            }

            @Override
            protected E computeNext() {
                Map.Entry entry;
                do {
                    if (!this.val$entries.hasNext()) return (E)this.endOfData();
                    entry = (Map.Entry)this.val$entries.next();
                } while (!MultiEdgesConnecting.this.targetNode.equals(entry.getValue()));
                return (E)entry.getKey();
            }
        };
    }

}

