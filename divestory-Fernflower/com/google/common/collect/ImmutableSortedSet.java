package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E> implements NavigableSet<E>, SortedIterable<E> {
   final transient Comparator<? super E> comparator;
   @LazyInit
   transient ImmutableSortedSet<E> descendingSet;

   ImmutableSortedSet(Comparator<? super E> var1) {
      this.comparator = var1;
   }

   static <E> ImmutableSortedSet<E> construct(Comparator<? super E> var0, int var1, E... var2) {
      if (var1 == 0) {
         return emptySet(var0);
      } else {
         ObjectArrays.checkElementsNotNull(var2, var1);
         Arrays.sort(var2, 0, var1, var0);
         int var3 = 1;

         int var4;
         int var6;
         for(var4 = 1; var3 < var1; var4 = var6) {
            Object var5 = var2[var3];
            var6 = var4;
            if (var0.compare(var5, var2[var4 - 1]) != 0) {
               var2[var4] = var5;
               var6 = var4 + 1;
            }

            ++var3;
         }

         Arrays.fill(var2, var4, var1, (Object)null);
         Object[] var7 = var2;
         if (var4 < var2.length / 2) {
            var7 = Arrays.copyOf(var2, var4);
         }

         return new RegularImmutableSortedSet(ImmutableList.asImmutableList(var7, var4), var0);
      }
   }

   public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> var0) {
      return copyOf(Ordering.natural(), (Iterable)var0);
   }

   public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> var0) {
      return copyOf(Ordering.natural(), (Collection)var0);
   }

   public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> var0, Iterable<? extends E> var1) {
      Preconditions.checkNotNull(var0);
      if (SortedIterables.hasSameComparator(var0, var1) && var1 instanceof ImmutableSortedSet) {
         ImmutableSortedSet var2 = (ImmutableSortedSet)var1;
         if (!var2.isPartialView()) {
            return var2;
         }
      }

      Object[] var3 = (Object[])Iterables.toArray(var1);
      return construct(var0, var3.length, var3);
   }

   public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> var0, Collection<? extends E> var1) {
      return copyOf(var0, (Iterable)var1);
   }

   public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> var0, Iterator<? extends E> var1) {
      return (new ImmutableSortedSet.Builder(var0)).addAll(var1).build();
   }

   public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> var0) {
      return copyOf(Ordering.natural(), (Iterator)var0);
   }

   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(E[] var0) {
      return construct(Ordering.natural(), var0.length, (Object[])var0.clone());
   }

   public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> var0) {
      Comparator var1 = SortedIterables.comparator(var0);
      ImmutableList var2 = ImmutableList.copyOf((Collection)var0);
      return var2.isEmpty() ? emptySet(var1) : new RegularImmutableSortedSet(var2, var1);
   }

   static <E> RegularImmutableSortedSet<E> emptySet(Comparator<? super E> var0) {
      return Ordering.natural().equals(var0) ? RegularImmutableSortedSet.NATURAL_EMPTY_SET : new RegularImmutableSortedSet(ImmutableList.of(), var0);
   }

   public static <E extends Comparable<?>> ImmutableSortedSet.Builder<E> naturalOrder() {
      return new ImmutableSortedSet.Builder(Ordering.natural());
   }

   public static <E> ImmutableSortedSet<E> of() {
      return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
   }

   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E var0) {
      return new RegularImmutableSortedSet(ImmutableList.of(var0), Ordering.natural());
   }

   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1) {
      return construct(Ordering.natural(), 2, var0, var1);
   }

   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1, E var2) {
      return construct(Ordering.natural(), 3, var0, var1, var2);
   }

   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1, E var2, E var3) {
      return construct(Ordering.natural(), 4, var0, var1, var2, var3);
   }

   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1, E var2, E var3, E var4) {
      return construct(Ordering.natural(), 5, var0, var1, var2, var3, var4);
   }

   public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E... var6) {
      int var7 = var6.length + 6;
      Comparable[] var8 = new Comparable[var7];
      var8[0] = var0;
      var8[1] = var1;
      var8[2] = var2;
      var8[3] = var3;
      var8[4] = var4;
      var8[5] = var5;
      System.arraycopy(var6, 0, var8, 6, var6.length);
      return construct(Ordering.natural(), var7, (Comparable[])var8);
   }

   public static <E> ImmutableSortedSet.Builder<E> orderedBy(Comparator<E> var0) {
      return new ImmutableSortedSet.Builder(var0);
   }

   private void readObject(ObjectInputStream var1) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   public static <E extends Comparable<?>> ImmutableSortedSet.Builder<E> reverseOrder() {
      return new ImmutableSortedSet.Builder(Collections.reverseOrder());
   }

   static int unsafeCompare(Comparator<?> var0, Object var1, Object var2) {
      return var0.compare(var1, var2);
   }

   public E ceiling(E var1) {
      return Iterables.getFirst(this.tailSet(var1, true), (Object)null);
   }

   public Comparator<? super E> comparator() {
      return this.comparator;
   }

   abstract ImmutableSortedSet<E> createDescendingSet();

   public abstract UnmodifiableIterator<E> descendingIterator();

   public ImmutableSortedSet<E> descendingSet() {
      ImmutableSortedSet var1 = this.descendingSet;
      ImmutableSortedSet var2 = var1;
      if (var1 == null) {
         var2 = this.createDescendingSet();
         this.descendingSet = var2;
         var2.descendingSet = this;
      }

      return var2;
   }

   public E first() {
      return this.iterator().next();
   }

   public E floor(E var1) {
      return Iterators.getNext(this.headSet(var1, true).descendingIterator(), (Object)null);
   }

   public ImmutableSortedSet<E> headSet(E var1) {
      return this.headSet(var1, false);
   }

   public ImmutableSortedSet<E> headSet(E var1, boolean var2) {
      return this.headSetImpl(Preconditions.checkNotNull(var1), var2);
   }

   abstract ImmutableSortedSet<E> headSetImpl(E var1, boolean var2);

   public E higher(E var1) {
      return Iterables.getFirst(this.tailSet(var1, false), (Object)null);
   }

   abstract int indexOf(@NullableDecl Object var1);

   public abstract UnmodifiableIterator<E> iterator();

   public E last() {
      return this.descendingIterator().next();
   }

   public E lower(E var1) {
      return Iterators.getNext(this.headSet(var1, false).descendingIterator(), (Object)null);
   }

   @Deprecated
   public final E pollFirst() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final E pollLast() {
      throw new UnsupportedOperationException();
   }

   public ImmutableSortedSet<E> subSet(E var1, E var2) {
      return this.subSet(var1, true, var2, false);
   }

   public ImmutableSortedSet<E> subSet(E var1, boolean var2, E var3, boolean var4) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var3);
      boolean var5;
      if (this.comparator.compare(var1, var3) <= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5);
      return this.subSetImpl(var1, var2, var3, var4);
   }

   abstract ImmutableSortedSet<E> subSetImpl(E var1, boolean var2, E var3, boolean var4);

   public ImmutableSortedSet<E> tailSet(E var1) {
      return this.tailSet(var1, true);
   }

   public ImmutableSortedSet<E> tailSet(E var1, boolean var2) {
      return this.tailSetImpl(Preconditions.checkNotNull(var1), var2);
   }

   abstract ImmutableSortedSet<E> tailSetImpl(E var1, boolean var2);

   int unsafeCompare(Object var1, Object var2) {
      return unsafeCompare(this.comparator, var1, var2);
   }

   Object writeReplace() {
      return new ImmutableSortedSet.SerializedForm(this.comparator, this.toArray());
   }

   public static final class Builder<E> extends ImmutableSet.Builder<E> {
      private final Comparator<? super E> comparator;

      public Builder(Comparator<? super E> var1) {
         this.comparator = (Comparator)Preconditions.checkNotNull(var1);
      }

      public ImmutableSortedSet.Builder<E> add(E var1) {
         super.add(var1);
         return this;
      }

      public ImmutableSortedSet.Builder<E> add(E... var1) {
         super.add(var1);
         return this;
      }

      public ImmutableSortedSet.Builder<E> addAll(Iterable<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableSortedSet.Builder<E> addAll(Iterator<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableSortedSet<E> build() {
         Object[] var1 = (Object[])this.contents;
         ImmutableSortedSet var2 = ImmutableSortedSet.construct(this.comparator, this.size, var1);
         this.size = var2.size();
         this.forceCopy = true;
         return var2;
      }
   }

   private static class SerializedForm<E> implements Serializable {
      private static final long serialVersionUID = 0L;
      final Comparator<? super E> comparator;
      final Object[] elements;

      public SerializedForm(Comparator<? super E> var1, Object[] var2) {
         this.comparator = var1;
         this.elements = var2;
      }

      Object readResolve() {
         return (new ImmutableSortedSet.Builder(this.comparator)).add((Object[])this.elements).build();
      }
   }
}
