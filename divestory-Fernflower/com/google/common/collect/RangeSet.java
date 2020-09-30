package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use ImmutableRangeSet or TreeRangeSet")
public interface RangeSet<C extends Comparable> {
   void add(Range<C> var1);

   void addAll(RangeSet<C> var1);

   void addAll(Iterable<Range<C>> var1);

   Set<Range<C>> asDescendingSetOfRanges();

   Set<Range<C>> asRanges();

   void clear();

   RangeSet<C> complement();

   boolean contains(C var1);

   boolean encloses(Range<C> var1);

   boolean enclosesAll(RangeSet<C> var1);

   boolean enclosesAll(Iterable<Range<C>> var1);

   boolean equals(@NullableDecl Object var1);

   int hashCode();

   boolean intersects(Range<C> var1);

   boolean isEmpty();

   Range<C> rangeContaining(C var1);

   void remove(Range<C> var1);

   void removeAll(RangeSet<C> var1);

   void removeAll(Iterable<Range<C>> var1);

   Range<C> span();

   RangeSet<C> subRangeSet(Range<C> var1);

   String toString();
}
