/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Range;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableRangeMap or TreeRangeMap")
public interface RangeMap<K extends Comparable, V> {
    public Map<Range<K>, V> asDescendingMapOfRanges();

    public Map<Range<K>, V> asMapOfRanges();

    public void clear();

    public boolean equals(@NullableDecl Object var1);

    @NullableDecl
    public V get(K var1);

    @NullableDecl
    public Map.Entry<Range<K>, V> getEntry(K var1);

    public int hashCode();

    public void put(Range<K> var1, V var2);

    public void putAll(RangeMap<K, V> var1);

    public void putCoalescing(Range<K> var1, V var2);

    public void remove(Range<K> var1);

    public Range<K> span();

    public RangeMap<K, V> subRangeMap(Range<K> var1);

    public String toString();
}

