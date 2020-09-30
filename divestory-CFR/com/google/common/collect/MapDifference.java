/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use Maps.difference")
public interface MapDifference<K, V> {
    public boolean areEqual();

    public Map<K, ValueDifference<V>> entriesDiffering();

    public Map<K, V> entriesInCommon();

    public Map<K, V> entriesOnlyOnLeft();

    public Map<K, V> entriesOnlyOnRight();

    public boolean equals(@NullableDecl Object var1);

    public int hashCode();

    @DoNotMock(value="Use Maps.difference")
    public static interface ValueDifference<V> {
        public boolean equals(@NullableDecl Object var1);

        public int hashCode();

        public V leftValue();

        public V rightValue();
    }

}

