package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
   static final ImmutableSortedMultiset<Comparable> NATURAL_EMPTY_MULTISET = new RegularImmutableSortedMultiset(Ordering.natural());
   private static final long[] ZERO_CUMULATIVE_COUNTS = new long[]{0L};
   private final transient long[] cumulativeCounts;
   final transient RegularImmutableSortedSet<E> elementSet;
   private final transient int length;
   private final transient int offset;

   RegularImmutableSortedMultiset(RegularImmutableSortedSet<E> var1, long[] var2, int var3, int var4) {
      this.elementSet = var1;
      this.cumulativeCounts = var2;
      this.offset = var3;
      this.length = var4;
   }

   RegularImmutableSortedMultiset(Comparator<? super E> var1) {
      this.elementSet = ImmutableSortedSet.emptySet(var1);
      this.cumulativeCounts = ZERO_CUMULATIVE_COUNTS;
      this.offset = 0;
      this.length = 0;
   }

   private int getCount(int var1) {
      long[] var2 = this.cumulativeCounts;
      int var3 = this.offset;
      return (int)(var2[var3 + var1 + 1] - var2[var3 + var1]);
   }

   public int count(@NullableDecl Object var1) {
      int var2 = this.elementSet.indexOf(var1);
      if (var2 >= 0) {
         var2 = this.getCount(var2);
      } else {
         var2 = 0;
      }

      return var2;
   }

   public ImmutableSortedSet<E> elementSet() {
      return this.elementSet;
   }

   public Multiset.Entry<E> firstEntry() {
      Multiset.Entry var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.getEntry(0);
      }

      return var1;
   }

   Multiset.Entry<E> getEntry(int var1) {
      return Multisets.immutableEntry(this.elementSet.asList().get(var1), this.getCount(var1));
   }

   ImmutableSortedMultiset<E> getSubMultiset(int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var2, this.length);
      if (var1 == var2) {
         return emptyMultiset(this.comparator());
      } else {
         return var1 == 0 && var2 == this.length ? this : new RegularImmutableSortedMultiset(this.elementSet.getSubSet(var1, var2), this.cumulativeCounts, this.offset + var1, var2 - var1);
      }
   }

   public ImmutableSortedMultiset<E> headMultiset(E var1, BoundType var2) {
      RegularImmutableSortedSet var3 = this.elementSet;
      boolean var4;
      if (Preconditions.checkNotNull(var2) == BoundType.CLOSED) {
         var4 = true;
      } else {
         var4 = false;
      }

      return this.getSubMultiset(0, var3.headIndex(var1, var4));
   }

   boolean isPartialView() {
      int var1 = this.offset;
      boolean var2 = true;
      boolean var3 = var2;
      if (var1 <= 0) {
         if (this.length < this.cumulativeCounts.length - 1) {
            var3 = var2;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public Multiset.Entry<E> lastEntry() {
      Multiset.Entry var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.getEntry(this.length - 1);
      }

      return var1;
   }

   public int size() {
      long[] var1 = this.cumulativeCounts;
      int var2 = this.offset;
      return Ints.saturatedCast(var1[this.length + var2] - var1[var2]);
   }

   public ImmutableSortedMultiset<E> tailMultiset(E var1, BoundType var2) {
      RegularImmutableSortedSet var3 = this.elementSet;
      boolean var4;
      if (Preconditions.checkNotNull(var2) == BoundType.CLOSED) {
         var4 = true;
      } else {
         var4 = false;
      }

      return this.getSubMultiset(var3.tailIndex(var1, var4), this.length);
   }
}
