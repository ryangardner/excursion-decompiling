package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public abstract class ImmutableSortedMultiset<E> extends ImmutableSortedMultisetFauxverideShim<E> implements SortedMultiset<E> {
   @LazyInit
   transient ImmutableSortedMultiset<E> descendingMultiset;

   ImmutableSortedMultiset() {
   }

   public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> var0) {
      return copyOf(Ordering.natural(), (Iterable)var0);
   }

   public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> var0, Iterable<? extends E> var1) {
      if (var1 instanceof ImmutableSortedMultiset) {
         ImmutableSortedMultiset var2 = (ImmutableSortedMultiset)var1;
         if (var0.equals(var2.comparator())) {
            if (var2.isPartialView()) {
               return copyOfSortedEntries(var0, var2.entrySet().asList());
            }

            return var2;
         }
      }

      return (new ImmutableSortedMultiset.Builder(var0)).addAll(var1).build();
   }

   public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> var0, Iterator<? extends E> var1) {
      Preconditions.checkNotNull(var0);
      return (new ImmutableSortedMultiset.Builder(var0)).addAll(var1).build();
   }

   public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> var0) {
      return copyOf(Ordering.natural(), (Iterator)var0);
   }

   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(E[] var0) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(var0));
   }

   public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> var0) {
      return copyOfSortedEntries(var0.comparator(), Lists.newArrayList((Iterable)var0.entrySet()));
   }

   private static <E> ImmutableSortedMultiset<E> copyOfSortedEntries(Comparator<? super E> var0, Collection<Multiset.Entry<E>> var1) {
      if (var1.isEmpty()) {
         return emptyMultiset(var0);
      } else {
         ImmutableList.Builder var2 = new ImmutableList.Builder(var1.size());
         long[] var3 = new long[var1.size() + 1];
         Iterator var4 = var1.iterator();

         int var7;
         for(int var5 = 0; var4.hasNext(); var5 = var7) {
            Multiset.Entry var6 = (Multiset.Entry)var4.next();
            var2.add(var6.getElement());
            var7 = var5 + 1;
            var3[var7] = var3[var5] + (long)var6.getCount();
         }

         return new RegularImmutableSortedMultiset(new RegularImmutableSortedSet(var2.build(), var0), var3, 0, var1.size());
      }
   }

   static <E> ImmutableSortedMultiset<E> emptyMultiset(Comparator<? super E> var0) {
      return (ImmutableSortedMultiset)(Ordering.natural().equals(var0) ? RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET : new RegularImmutableSortedMultiset(var0));
   }

   public static <E extends Comparable<?>> ImmutableSortedMultiset.Builder<E> naturalOrder() {
      return new ImmutableSortedMultiset.Builder(Ordering.natural());
   }

   public static <E> ImmutableSortedMultiset<E> of() {
      return RegularImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
   }

   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E var0) {
      return new RegularImmutableSortedMultiset((RegularImmutableSortedSet)ImmutableSortedSet.of(var0), new long[]{0L, 1L}, 0, 1);
   }

   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E var0, E var1) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(var0, var1));
   }

   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E var0, E var1, E var2) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(var0, var1, var2));
   }

   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E var0, E var1, E var2, E var3) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(var0, var1, var2, var3));
   }

   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E var0, E var1, E var2, E var3, E var4) {
      return copyOf(Ordering.natural(), (Iterable)Arrays.asList(var0, var1, var2, var3, var4));
   }

   public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E... var6) {
      ArrayList var7 = Lists.newArrayListWithCapacity(var6.length + 6);
      Collections.addAll(var7, new Comparable[]{var0, var1, var2, var3, var4, var5});
      Collections.addAll(var7, var6);
      return copyOf(Ordering.natural(), (Iterable)var7);
   }

   public static <E> ImmutableSortedMultiset.Builder<E> orderedBy(Comparator<E> var0) {
      return new ImmutableSortedMultiset.Builder(var0);
   }

   public static <E extends Comparable<?>> ImmutableSortedMultiset.Builder<E> reverseOrder() {
      return new ImmutableSortedMultiset.Builder(Ordering.natural().reverse());
   }

   public final Comparator<? super E> comparator() {
      return this.elementSet().comparator();
   }

   public ImmutableSortedMultiset<E> descendingMultiset() {
      ImmutableSortedMultiset var1 = this.descendingMultiset;
      Object var2 = var1;
      if (var1 == null) {
         if (this.isEmpty()) {
            var2 = emptyMultiset(Ordering.from(this.comparator()).reverse());
         } else {
            var2 = new DescendingImmutableSortedMultiset(this);
         }

         this.descendingMultiset = (ImmutableSortedMultiset)var2;
      }

      return (ImmutableSortedMultiset)var2;
   }

   public abstract ImmutableSortedSet<E> elementSet();

   public abstract ImmutableSortedMultiset<E> headMultiset(E var1, BoundType var2);

   @Deprecated
   public final Multiset.Entry<E> pollFirstEntry() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final Multiset.Entry<E> pollLastEntry() {
      throw new UnsupportedOperationException();
   }

   public ImmutableSortedMultiset<E> subMultiset(E var1, BoundType var2, E var3, BoundType var4) {
      boolean var5;
      if (this.comparator().compare(var1, var3) <= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "Expected lowerBound <= upperBound but %s > %s", var1, var3);
      return this.tailMultiset(var1, var2).headMultiset(var3, var4);
   }

   public abstract ImmutableSortedMultiset<E> tailMultiset(E var1, BoundType var2);

   Object writeReplace() {
      return new ImmutableSortedMultiset.SerializedForm(this);
   }

   public static class Builder<E> extends ImmutableMultiset.Builder<E> {
      private final Comparator<? super E> comparator;
      private int[] counts;
      E[] elements;
      private boolean forceCopyElements;
      private int length;

      public Builder(Comparator<? super E> var1) {
         super(true);
         this.comparator = (Comparator)Preconditions.checkNotNull(var1);
         this.elements = (Object[])(new Object[4]);
         this.counts = new int[4];
      }

      private void dedupAndCoalesce(boolean var1) {
         int var2 = this.length;
         if (var2 != 0) {
            Object[] var3 = Arrays.copyOf(this.elements, var2);
            Arrays.sort(var3, this.comparator);
            int var4 = 1;

            int var5;
            for(var2 = 1; var4 < var3.length; var2 = var5) {
               var5 = var2;
               if (this.comparator.compare(var3[var2 - 1], var3[var4]) < 0) {
                  var3[var2] = var3[var4];
                  var5 = var2 + 1;
               }

               ++var4;
            }

            Arrays.fill(var3, var2, this.length, (Object)null);
            Object[] var6 = var3;
            if (var1) {
               var4 = this.length;
               var6 = var3;
               if (var2 * 4 > var4 * 3) {
                  var6 = Arrays.copyOf(var3, IntMath.saturatedAdd(var4, var4 / 2 + 1));
               }
            }

            int[] var8 = new int[var6.length];

            for(var4 = 0; var4 < this.length; ++var4) {
               var5 = Arrays.binarySearch(var6, 0, var2, this.elements[var4], this.comparator);
               int[] var7 = this.counts;
               if (var7[var4] >= 0) {
                  var8[var5] += var7[var4];
               } else {
                  var8[var5] = var7[var4];
               }
            }

            this.elements = var6;
            this.counts = var8;
            this.length = var2;
         }
      }

      private void dedupAndCoalesceAndDeleteEmpty() {
         this.dedupAndCoalesce(false);
         int var1 = 0;
         int var2 = 0;

         while(true) {
            int var3 = this.length;
            if (var1 >= var3) {
               Arrays.fill(this.elements, var2, var3, (Object)null);
               Arrays.fill(this.counts, var2, this.length, 0);
               this.length = var2;
               return;
            }

            int[] var4 = this.counts;
            var3 = var2;
            if (var4[var1] > 0) {
               Object[] var5 = this.elements;
               var5[var2] = var5[var1];
               var4[var2] = var4[var1];
               var3 = var2 + 1;
            }

            ++var1;
            var2 = var3;
         }
      }

      private void maintenance() {
         int var1 = this.length;
         Object[] var2 = this.elements;
         if (var1 == var2.length) {
            this.dedupAndCoalesce(true);
         } else if (this.forceCopyElements) {
            this.elements = Arrays.copyOf(var2, var2.length);
         }

         this.forceCopyElements = false;
      }

      public ImmutableSortedMultiset.Builder<E> add(E var1) {
         return this.addCopies(var1, 1);
      }

      public ImmutableSortedMultiset.Builder<E> add(E... var1) {
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            this.add(var1[var3]);
         }

         return this;
      }

      public ImmutableSortedMultiset.Builder<E> addAll(Iterable<? extends E> var1) {
         Iterator var3;
         if (var1 instanceof Multiset) {
            var3 = ((Multiset)var1).entrySet().iterator();

            while(var3.hasNext()) {
               Multiset.Entry var2 = (Multiset.Entry)var3.next();
               this.addCopies(var2.getElement(), var2.getCount());
            }
         } else {
            var3 = var1.iterator();

            while(var3.hasNext()) {
               this.add(var3.next());
            }
         }

         return this;
      }

      public ImmutableSortedMultiset.Builder<E> addAll(Iterator<? extends E> var1) {
         while(var1.hasNext()) {
            this.add(var1.next());
         }

         return this;
      }

      public ImmutableSortedMultiset.Builder<E> addCopies(E var1, int var2) {
         Preconditions.checkNotNull(var1);
         CollectPreconditions.checkNonnegative(var2, "occurrences");
         if (var2 == 0) {
            return this;
         } else {
            this.maintenance();
            Object[] var3 = this.elements;
            int var4 = this.length;
            var3[var4] = var1;
            this.counts[var4] = var2;
            this.length = var4 + 1;
            return this;
         }
      }

      public ImmutableSortedMultiset<E> build() {
         this.dedupAndCoalesceAndDeleteEmpty();
         int var1 = this.length;
         if (var1 == 0) {
            return ImmutableSortedMultiset.emptyMultiset(this.comparator);
         } else {
            RegularImmutableSortedSet var2 = (RegularImmutableSortedSet)ImmutableSortedSet.construct(this.comparator, var1, this.elements);
            long[] var3 = new long[this.length + 1];

            int var4;
            for(var1 = 0; var1 < this.length; var1 = var4) {
               var4 = var1 + 1;
               var3[var4] = var3[var1] + (long)this.counts[var1];
            }

            this.forceCopyElements = true;
            return new RegularImmutableSortedMultiset(var2, var3, 0, this.length);
         }
      }

      public ImmutableSortedMultiset.Builder<E> setCount(E var1, int var2) {
         Preconditions.checkNotNull(var1);
         CollectPreconditions.checkNonnegative(var2, "count");
         this.maintenance();
         Object[] var3 = this.elements;
         int var4 = this.length;
         var3[var4] = var1;
         this.counts[var4] = var2;
         this.length = var4 + 1;
         return this;
      }
   }

   private static final class SerializedForm<E> implements Serializable {
      final Comparator<? super E> comparator;
      final int[] counts;
      final E[] elements;

      SerializedForm(SortedMultiset<E> var1) {
         this.comparator = var1.comparator();
         int var2 = var1.entrySet().size();
         this.elements = (Object[])(new Object[var2]);
         this.counts = new int[var2];
         Iterator var4 = var1.entrySet().iterator();

         for(var2 = 0; var4.hasNext(); ++var2) {
            Multiset.Entry var3 = (Multiset.Entry)var4.next();
            this.elements[var2] = var3.getElement();
            this.counts[var2] = var3.getCount();
         }

      }

      Object readResolve() {
         int var1 = this.elements.length;
         ImmutableSortedMultiset.Builder var2 = new ImmutableSortedMultiset.Builder(this.comparator);

         for(int var3 = 0; var3 < var1; ++var3) {
            var2.addCopies(this.elements[var3], this.counts[var3]);
         }

         return var2.build();
      }
   }
}
