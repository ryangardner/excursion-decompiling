package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use Maps.difference")
public interface MapDifference<K, V> {
   boolean areEqual();

   Map<K, MapDifference.ValueDifference<V>> entriesDiffering();

   Map<K, V> entriesInCommon();

   Map<K, V> entriesOnlyOnLeft();

   Map<K, V> entriesOnlyOnRight();

   boolean equals(@NullableDecl Object var1);

   int hashCode();

   @DoNotMock("Use Maps.difference")
   public interface ValueDifference<V> {
      boolean equals(@NullableDecl Object var1);

      int hashCode();

      V leftValue();

      V rightValue();
   }
}
