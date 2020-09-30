package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use ImmutableRangeMap or TreeRangeMap")
public interface RangeMap<K extends Comparable, V> {
   Map<Range<K>, V> asDescendingMapOfRanges();

   Map<Range<K>, V> asMapOfRanges();

   void clear();

   boolean equals(@NullableDecl Object var1);

   @NullableDecl
   V get(K var1);

   @NullableDecl
   Entry<Range<K>, V> getEntry(K var1);

   int hashCode();

   void put(Range<K> var1, V var2);

   void putAll(RangeMap<K, V> var1);

   void putCoalescing(Range<K> var1, V var2);

   void remove(Range<K> var1);

   Range<K> span();

   RangeMap<K, V> subRangeMap(Range<K> var1);

   String toString();
}
