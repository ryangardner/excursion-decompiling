package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.math.IntMath;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Collections2 {
   private Collections2() {
   }

   static <T> Collection<T> cast(Iterable<T> var0) {
      return (Collection)var0;
   }

   static boolean containsAllImpl(Collection<?> var0, Collection<?> var1) {
      Iterator var2 = var1.iterator();

      do {
         if (!var2.hasNext()) {
            return true;
         }
      } while(var0.contains(var2.next()));

      return false;
   }

   private static <E> ObjectCountHashMap<E> counts(Collection<E> var0) {
      ObjectCountHashMap var1 = new ObjectCountHashMap();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.put(var3, var1.get(var3) + 1);
      }

      return var1;
   }

   public static <E> Collection<E> filter(Collection<E> var0, Predicate<? super E> var1) {
      return var0 instanceof Collections2.FilteredCollection ? ((Collections2.FilteredCollection)var0).createCombined(var1) : new Collections2.FilteredCollection((Collection)Preconditions.checkNotNull(var0), (Predicate)Preconditions.checkNotNull(var1));
   }

   private static boolean isPermutation(List<?> var0, List<?> var1) {
      if (var0.size() != var1.size()) {
         return false;
      } else {
         ObjectCountHashMap var2 = counts(var0);
         ObjectCountHashMap var3 = counts(var1);
         if (var0.size() != var1.size()) {
            return false;
         } else {
            for(int var4 = 0; var4 < var0.size(); ++var4) {
               if (var2.getValue(var4) != var3.get(var2.getKey(var4))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   static StringBuilder newStringBuilderForCollection(int var0) {
      CollectPreconditions.checkNonnegative(var0, "size");
      return new StringBuilder((int)Math.min((long)var0 * 8L, 1073741824L));
   }

   public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> var0) {
      return orderedPermutations(var0, Ordering.natural());
   }

   public static <E> Collection<List<E>> orderedPermutations(Iterable<E> var0, Comparator<? super E> var1) {
      return new Collections2.OrderedPermutationCollection(var0, var1);
   }

   public static <E> Collection<List<E>> permutations(Collection<E> var0) {
      return new Collections2.PermutationCollection(ImmutableList.copyOf(var0));
   }

   static boolean safeContains(Collection<?> var0, @NullableDecl Object var1) {
      Preconditions.checkNotNull(var0);

      try {
         boolean var2 = var0.contains(var1);
         return var2;
      } catch (NullPointerException | ClassCastException var3) {
         return false;
      }
   }

   static boolean safeRemove(Collection<?> var0, @NullableDecl Object var1) {
      Preconditions.checkNotNull(var0);

      try {
         boolean var2 = var0.remove(var1);
         return var2;
      } catch (NullPointerException | ClassCastException var3) {
         return false;
      }
   }

   static String toStringImpl(Collection<?> var0) {
      StringBuilder var1 = newStringBuilderForCollection(var0.size());
      var1.append('[');
      Iterator var2 = var0.iterator();
      boolean var3 = true;

      while(var2.hasNext()) {
         Object var4 = var2.next();
         if (!var3) {
            var1.append(", ");
         }

         var3 = false;
         if (var4 == var0) {
            var1.append("(this Collection)");
         } else {
            var1.append(var4);
         }
      }

      var1.append(']');
      return var1.toString();
   }

   public static <F, T> Collection<T> transform(Collection<F> var0, Function<? super F, T> var1) {
      return new Collections2.TransformedCollection(var0, var1);
   }

   static class FilteredCollection<E> extends AbstractCollection<E> {
      final Predicate<? super E> predicate;
      final Collection<E> unfiltered;

      FilteredCollection(Collection<E> var1, Predicate<? super E> var2) {
         this.unfiltered = var1;
         this.predicate = var2;
      }

      public boolean add(E var1) {
         Preconditions.checkArgument(this.predicate.apply(var1));
         return this.unfiltered.add(var1);
      }

      public boolean addAll(Collection<? extends E> var1) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            Object var3 = var2.next();
            Preconditions.checkArgument(this.predicate.apply(var3));
         }

         return this.unfiltered.addAll(var1);
      }

      public void clear() {
         Iterables.removeIf(this.unfiltered, this.predicate);
      }

      public boolean contains(@NullableDecl Object var1) {
         return Collections2.safeContains(this.unfiltered, var1) ? this.predicate.apply(var1) : false;
      }

      public boolean containsAll(Collection<?> var1) {
         return Collections2.containsAllImpl(this, var1);
      }

      Collections2.FilteredCollection<E> createCombined(Predicate<? super E> var1) {
         return new Collections2.FilteredCollection(this.unfiltered, Predicates.and(this.predicate, var1));
      }

      public boolean isEmpty() {
         return Iterables.any(this.unfiltered, this.predicate) ^ true;
      }

      public Iterator<E> iterator() {
         return Iterators.filter(this.unfiltered.iterator(), this.predicate);
      }

      public boolean remove(Object var1) {
         boolean var2;
         if (this.contains(var1) && this.unfiltered.remove(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean removeAll(Collection<?> var1) {
         Iterator var2 = this.unfiltered.iterator();
         boolean var3 = false;

         while(var2.hasNext()) {
            Object var4 = var2.next();
            if (this.predicate.apply(var4) && var1.contains(var4)) {
               var2.remove();
               var3 = true;
            }
         }

         return var3;
      }

      public boolean retainAll(Collection<?> var1) {
         Iterator var2 = this.unfiltered.iterator();
         boolean var3 = false;

         while(var2.hasNext()) {
            Object var4 = var2.next();
            if (this.predicate.apply(var4) && !var1.contains(var4)) {
               var2.remove();
               var3 = true;
            }
         }

         return var3;
      }

      public int size() {
         Iterator var1 = this.unfiltered.iterator();
         int var2 = 0;

         while(var1.hasNext()) {
            Object var3 = var1.next();
            if (this.predicate.apply(var3)) {
               ++var2;
            }
         }

         return var2;
      }

      public Object[] toArray() {
         return Lists.newArrayList(this.iterator()).toArray();
      }

      public <T> T[] toArray(T[] var1) {
         return Lists.newArrayList(this.iterator()).toArray(var1);
      }
   }

   private static final class OrderedPermutationCollection<E> extends AbstractCollection<List<E>> {
      final Comparator<? super E> comparator;
      final ImmutableList<E> inputList;
      final int size;

      OrderedPermutationCollection(Iterable<E> var1, Comparator<? super E> var2) {
         ImmutableList var3 = ImmutableList.sortedCopyOf(var2, var1);
         this.inputList = var3;
         this.comparator = var2;
         this.size = calculateSize(var3, var2);
      }

      private static <E> int calculateSize(List<E> var0, Comparator<? super E> var1) {
         int var2 = 1;
         int var3 = 1;

         int var4;
         int var5;
         for(var4 = 1; var2 < var0.size(); var3 = var5) {
            var5 = var3;
            int var6 = var4;
            if (var1.compare(var0.get(var2 - 1), var0.get(var2)) < 0) {
               var4 = IntMath.saturatedMultiply(var3, IntMath.binomial(var2, var4));
               var6 = 0;
               var5 = var4;
               if (var4 == Integer.MAX_VALUE) {
                  return Integer.MAX_VALUE;
               }
            }

            ++var2;
            var4 = var6 + 1;
         }

         return IntMath.saturatedMultiply(var3, IntMath.binomial(var2, var4));
      }

      public boolean contains(@NullableDecl Object var1) {
         if (var1 instanceof List) {
            List var2 = (List)var1;
            return Collections2.isPermutation(this.inputList, var2);
         } else {
            return false;
         }
      }

      public boolean isEmpty() {
         return false;
      }

      public Iterator<List<E>> iterator() {
         return new Collections2.OrderedPermutationIterator(this.inputList, this.comparator);
      }

      public int size() {
         return this.size;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("orderedPermutationCollection(");
         var1.append(this.inputList);
         var1.append(")");
         return var1.toString();
      }
   }

   private static final class OrderedPermutationIterator<E> extends AbstractIterator<List<E>> {
      final Comparator<? super E> comparator;
      @NullableDecl
      List<E> nextPermutation;

      OrderedPermutationIterator(List<E> var1, Comparator<? super E> var2) {
         this.nextPermutation = Lists.newArrayList((Iterable)var1);
         this.comparator = var2;
      }

      void calculateNextPermutation() {
         int var1 = this.findNextJ();
         if (var1 == -1) {
            this.nextPermutation = null;
         } else {
            int var2 = this.findNextL(var1);
            Collections.swap(this.nextPermutation, var1, var2);
            var2 = this.nextPermutation.size();
            Collections.reverse(this.nextPermutation.subList(var1 + 1, var2));
         }
      }

      protected List<E> computeNext() {
         List var1 = this.nextPermutation;
         if (var1 == null) {
            return (List)this.endOfData();
         } else {
            ImmutableList var2 = ImmutableList.copyOf((Collection)var1);
            this.calculateNextPermutation();
            return var2;
         }
      }

      int findNextJ() {
         for(int var1 = this.nextPermutation.size() - 2; var1 >= 0; --var1) {
            if (this.comparator.compare(this.nextPermutation.get(var1), this.nextPermutation.get(var1 + 1)) < 0) {
               return var1;
            }
         }

         return -1;
      }

      int findNextL(int var1) {
         Object var2 = this.nextPermutation.get(var1);

         for(int var3 = this.nextPermutation.size() - 1; var3 > var1; --var3) {
            if (this.comparator.compare(var2, this.nextPermutation.get(var3)) < 0) {
               return var3;
            }
         }

         throw new AssertionError("this statement should be unreachable");
      }
   }

   private static final class PermutationCollection<E> extends AbstractCollection<List<E>> {
      final ImmutableList<E> inputList;

      PermutationCollection(ImmutableList<E> var1) {
         this.inputList = var1;
      }

      public boolean contains(@NullableDecl Object var1) {
         if (var1 instanceof List) {
            List var2 = (List)var1;
            return Collections2.isPermutation(this.inputList, var2);
         } else {
            return false;
         }
      }

      public boolean isEmpty() {
         return false;
      }

      public Iterator<List<E>> iterator() {
         return new Collections2.PermutationIterator(this.inputList);
      }

      public int size() {
         return IntMath.factorial(this.inputList.size());
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("permutations(");
         var1.append(this.inputList);
         var1.append(")");
         return var1.toString();
      }
   }

   private static class PermutationIterator<E> extends AbstractIterator<List<E>> {
      final int[] c;
      int j;
      final List<E> list;
      final int[] o;

      PermutationIterator(List<E> var1) {
         this.list = new ArrayList(var1);
         int var2 = var1.size();
         int[] var3 = new int[var2];
         this.c = var3;
         this.o = new int[var2];
         Arrays.fill(var3, 0);
         Arrays.fill(this.o, 1);
         this.j = Integer.MAX_VALUE;
      }

      void calculateNextPermutation() {
         int var1 = this.list.size() - 1;
         this.j = var1;
         if (var1 != -1) {
            var1 = 0;

            while(true) {
               while(true) {
                  int[] var2 = this.c;
                  int var3 = this.j;
                  int var4 = var2[var3] + this.o[var3];
                  if (var4 >= 0) {
                     if (var4 != var3 + 1) {
                        Collections.swap(this.list, var3 - var2[var3] + var1, var3 - var4 + var1);
                        this.c[this.j] = var4;
                        return;
                     }

                     if (var3 == 0) {
                        return;
                     }

                     ++var1;
                     this.switchDirection();
                  } else {
                     this.switchDirection();
                  }
               }
            }
         }
      }

      protected List<E> computeNext() {
         if (this.j <= 0) {
            return (List)this.endOfData();
         } else {
            ImmutableList var1 = ImmutableList.copyOf((Collection)this.list);
            this.calculateNextPermutation();
            return var1;
         }
      }

      void switchDirection() {
         int[] var1 = this.o;
         int var2 = this.j;
         var1[var2] = -var1[var2];
         this.j = var2 - 1;
      }
   }

   static class TransformedCollection<F, T> extends AbstractCollection<T> {
      final Collection<F> fromCollection;
      final Function<? super F, ? extends T> function;

      TransformedCollection(Collection<F> var1, Function<? super F, ? extends T> var2) {
         this.fromCollection = (Collection)Preconditions.checkNotNull(var1);
         this.function = (Function)Preconditions.checkNotNull(var2);
      }

      public void clear() {
         this.fromCollection.clear();
      }

      public boolean isEmpty() {
         return this.fromCollection.isEmpty();
      }

      public Iterator<T> iterator() {
         return Iterators.transform(this.fromCollection.iterator(), this.function);
      }

      public int size() {
         return this.fromCollection.size();
      }
   }
}
