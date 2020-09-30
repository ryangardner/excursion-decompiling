package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

public abstract class ContiguousSet<C extends Comparable> extends ImmutableSortedSet<C> {
   final DiscreteDomain<C> domain;

   ContiguousSet(DiscreteDomain<C> var1) {
      super(Ordering.natural());
      this.domain = var1;
   }

   @Deprecated
   public static <E> ImmutableSortedSet.Builder<E> builder() {
      throw new UnsupportedOperationException();
   }

   public static ContiguousSet<Integer> closed(int var0, int var1) {
      return create(Range.closed(var0, var1), DiscreteDomain.integers());
   }

   public static ContiguousSet<Long> closed(long var0, long var2) {
      return create(Range.closed(var0, var2), DiscreteDomain.longs());
   }

   public static ContiguousSet<Integer> closedOpen(int var0, int var1) {
      return create(Range.closedOpen(var0, var1), DiscreteDomain.integers());
   }

   public static ContiguousSet<Long> closedOpen(long var0, long var2) {
      return create(Range.closedOpen(var0, var2), DiscreteDomain.longs());
   }

   public static <C extends Comparable> ContiguousSet<C> create(Range<C> var0, DiscreteDomain<C> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);

      Range var3;
      label47: {
         NoSuchElementException var10000;
         label51: {
            boolean var10001;
            Range var2;
            label45: {
               try {
                  if (!var0.hasLowerBound()) {
                     var2 = var0.intersection(Range.atLeast(var1.minValue()));
                     break label45;
                  }
               } catch (NoSuchElementException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break label51;
               }

               var2 = var0;
            }

            var3 = var2;

            try {
               if (!var0.hasUpperBound()) {
                  var3 = var2.intersection(Range.atMost(var1.maxValue()));
               }
               break label47;
            } catch (NoSuchElementException var5) {
               var10000 = var5;
               var10001 = false;
            }
         }

         NoSuchElementException var7 = var10000;
         throw new IllegalArgumentException(var7);
      }

      boolean var4;
      if (!var3.isEmpty() && Range.compareOrThrow(var0.lowerBound.leastValueAbove(var1), var0.upperBound.greatestValueBelow(var1)) <= 0) {
         var4 = false;
      } else {
         var4 = true;
      }

      Object var8;
      if (var4) {
         var8 = new EmptyContiguousSet(var1);
      } else {
         var8 = new RegularContiguousSet(var3, var1);
      }

      return (ContiguousSet)var8;
   }

   ImmutableSortedSet<C> createDescendingSet() {
      return new DescendingImmutableSortedSet(this);
   }

   public ContiguousSet<C> headSet(C var1) {
      return this.headSetImpl((Comparable)Preconditions.checkNotNull(var1), false);
   }

   public ContiguousSet<C> headSet(C var1, boolean var2) {
      return this.headSetImpl((Comparable)Preconditions.checkNotNull(var1), var2);
   }

   abstract ContiguousSet<C> headSetImpl(C var1, boolean var2);

   public abstract ContiguousSet<C> intersection(ContiguousSet<C> var1);

   public abstract Range<C> range();

   public abstract Range<C> range(BoundType var1, BoundType var2);

   public ContiguousSet<C> subSet(C var1, C var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      boolean var3;
      if (this.comparator().compare(var1, var2) <= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      return this.subSetImpl(var1, true, var2, false);
   }

   public ContiguousSet<C> subSet(C var1, boolean var2, C var3, boolean var4) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var3);
      boolean var5;
      if (this.comparator().compare(var1, var3) <= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      return this.subSetImpl(var1, var2, var3, var4);
   }

   abstract ContiguousSet<C> subSetImpl(C var1, boolean var2, C var3, boolean var4);

   public ContiguousSet<C> tailSet(C var1) {
      return this.tailSetImpl((Comparable)Preconditions.checkNotNull(var1), true);
   }

   public ContiguousSet<C> tailSet(C var1, boolean var2) {
      return this.tailSetImpl((Comparable)Preconditions.checkNotNull(var1), var2);
   }

   abstract ContiguousSet<C> tailSetImpl(C var1, boolean var2);

   public String toString() {
      return this.range().toString();
   }
}
