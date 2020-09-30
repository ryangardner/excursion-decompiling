package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Ordering<T> implements Comparator<T> {
   static final int LEFT_IS_GREATER = 1;
   static final int RIGHT_IS_GREATER = -1;

   protected Ordering() {
   }

   public static Ordering<Object> allEqual() {
      return AllEqualOrdering.INSTANCE;
   }

   public static Ordering<Object> arbitrary() {
      return Ordering.ArbitraryOrderingHolder.ARBITRARY_ORDERING;
   }

   public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> var0) {
      return new CompoundOrdering(var0);
   }

   public static <T> Ordering<T> explicit(T var0, T... var1) {
      return explicit(Lists.asList(var0, var1));
   }

   public static <T> Ordering<T> explicit(List<T> var0) {
      return new ExplicitOrdering(var0);
   }

   @Deprecated
   public static <T> Ordering<T> from(Ordering<T> var0) {
      return (Ordering)Preconditions.checkNotNull(var0);
   }

   public static <T> Ordering<T> from(Comparator<T> var0) {
      Object var1;
      if (var0 instanceof Ordering) {
         var1 = (Ordering)var0;
      } else {
         var1 = new ComparatorOrdering(var0);
      }

      return (Ordering)var1;
   }

   public static <C extends Comparable> Ordering<C> natural() {
      return NaturalOrdering.INSTANCE;
   }

   public static Ordering<Object> usingToString() {
      return UsingToStringOrdering.INSTANCE;
   }

   @Deprecated
   public int binarySearch(List<? extends T> var1, @NullableDecl T var2) {
      return Collections.binarySearch(var1, var2, this);
   }

   public abstract int compare(@NullableDecl T var1, @NullableDecl T var2);

   public <U extends T> Ordering<U> compound(Comparator<? super U> var1) {
      return new CompoundOrdering(this, (Comparator)Preconditions.checkNotNull(var1));
   }

   public <E extends T> List<E> greatestOf(Iterable<E> var1, int var2) {
      return this.reverse().leastOf(var1, var2);
   }

   public <E extends T> List<E> greatestOf(Iterator<E> var1, int var2) {
      return this.reverse().leastOf(var1, var2);
   }

   public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> var1) {
      return ImmutableList.sortedCopyOf(this, var1);
   }

   public boolean isOrdered(Iterable<? extends T> var1) {
      Iterator var2 = var1.iterator();
      Object var3;
      if (var2.hasNext()) {
         for(Object var4 = var2.next(); var2.hasNext(); var4 = var3) {
            var3 = var2.next();
            if (this.compare(var4, var3) > 0) {
               return false;
            }
         }
      }

      return true;
   }

   public boolean isStrictlyOrdered(Iterable<? extends T> var1) {
      Iterator var2 = var1.iterator();
      Object var3;
      if (var2.hasNext()) {
         for(Object var4 = var2.next(); var2.hasNext(); var4 = var3) {
            var3 = var2.next();
            if (this.compare(var4, var3) >= 0) {
               return false;
            }
         }
      }

      return true;
   }

   public <E extends T> List<E> leastOf(Iterable<E> var1, int var2) {
      if (var1 instanceof Collection) {
         Collection var3 = (Collection)var1;
         if ((long)var3.size() <= (long)var2 * 2L) {
            Object[] var5 = (Object[])var3.toArray();
            Arrays.sort(var5, this);
            Object[] var4 = var5;
            if (var5.length > var2) {
               var4 = Arrays.copyOf(var5, var2);
            }

            return Collections.unmodifiableList(Arrays.asList(var4));
         }
      }

      return this.leastOf(var1.iterator(), var2);
   }

   public <E extends T> List<E> leastOf(Iterator<E> var1, int var2) {
      Preconditions.checkNotNull(var1);
      CollectPreconditions.checkNonnegative(var2, "k");
      if (var2 != 0 && var1.hasNext()) {
         if (var2 >= 1073741823) {
            ArrayList var4 = Lists.newArrayList(var1);
            Collections.sort(var4, this);
            if (var4.size() > var2) {
               var4.subList(var2, var4.size()).clear();
            }

            var4.trimToSize();
            return Collections.unmodifiableList(var4);
         } else {
            TopKSelector var3 = TopKSelector.least(var2, this);
            var3.offerAll(var1);
            return var3.topK();
         }
      } else {
         return Collections.emptyList();
      }
   }

   public <S extends T> Ordering<Iterable<S>> lexicographical() {
      return new LexicographicalOrdering(this);
   }

   public <E extends T> E max(Iterable<E> var1) {
      return this.max(var1.iterator());
   }

   public <E extends T> E max(@NullableDecl E var1, @NullableDecl E var2) {
      if (this.compare(var1, var2) < 0) {
         var1 = var2;
      }

      return var1;
   }

   public <E extends T> E max(@NullableDecl E var1, @NullableDecl E var2, @NullableDecl E var3, E... var4) {
      var1 = this.max(this.max(var1, var2), var3);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         var1 = this.max(var1, var4[var6]);
      }

      return var1;
   }

   public <E extends T> E max(Iterator<E> var1) {
      Object var2;
      for(var2 = var1.next(); var1.hasNext(); var2 = this.max(var2, var1.next())) {
      }

      return var2;
   }

   public <E extends T> E min(Iterable<E> var1) {
      return this.min(var1.iterator());
   }

   public <E extends T> E min(@NullableDecl E var1, @NullableDecl E var2) {
      if (this.compare(var1, var2) > 0) {
         var1 = var2;
      }

      return var1;
   }

   public <E extends T> E min(@NullableDecl E var1, @NullableDecl E var2, @NullableDecl E var3, E... var4) {
      var1 = this.min(this.min(var1, var2), var3);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         var1 = this.min(var1, var4[var6]);
      }

      return var1;
   }

   public <E extends T> E min(Iterator<E> var1) {
      Object var2;
      for(var2 = var1.next(); var1.hasNext(); var2 = this.min(var2, var1.next())) {
      }

      return var2;
   }

   public <S extends T> Ordering<S> nullsFirst() {
      return new NullsFirstOrdering(this);
   }

   public <S extends T> Ordering<S> nullsLast() {
      return new NullsLastOrdering(this);
   }

   <T2 extends T> Ordering<Entry<T2, ?>> onKeys() {
      return this.onResultOf(Maps.keyFunction());
   }

   public <F> Ordering<F> onResultOf(Function<F, ? extends T> var1) {
      return new ByFunctionOrdering(var1, this);
   }

   public <S extends T> Ordering<S> reverse() {
      return new ReverseOrdering(this);
   }

   public <E extends T> List<E> sortedCopy(Iterable<E> var1) {
      Object[] var2 = (Object[])Iterables.toArray(var1);
      Arrays.sort(var2, this);
      return Lists.newArrayList((Iterable)Arrays.asList(var2));
   }

   static class ArbitraryOrdering extends Ordering<Object> {
      private final AtomicInteger counter = new AtomicInteger(0);
      private final ConcurrentMap<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeMap();

      private Integer getUid(Object var1) {
         Integer var2 = (Integer)this.uids.get(var1);
         Integer var3 = var2;
         if (var2 == null) {
            var3 = this.counter.getAndIncrement();
            Integer var4 = (Integer)this.uids.putIfAbsent(var1, var3);
            if (var4 != null) {
               var3 = var4;
            }
         }

         return var3;
      }

      public int compare(Object var1, Object var2) {
         if (var1 == var2) {
            return 0;
         } else {
            byte var3 = -1;
            if (var1 == null) {
               return -1;
            } else if (var2 == null) {
               return 1;
            } else {
               int var4 = this.identityHashCode(var1);
               int var5 = this.identityHashCode(var2);
               if (var4 != var5) {
                  if (var4 >= var5) {
                     var3 = 1;
                  }

                  return var3;
               } else {
                  int var6 = this.getUid(var1).compareTo(this.getUid(var2));
                  if (var6 != 0) {
                     return var6;
                  } else {
                     throw new AssertionError();
                  }
               }
            }
         }
      }

      int identityHashCode(Object var1) {
         return System.identityHashCode(var1);
      }

      public String toString() {
         return "Ordering.arbitrary()";
      }
   }

   private static class ArbitraryOrderingHolder {
      static final Ordering<Object> ARBITRARY_ORDERING = new Ordering.ArbitraryOrdering();
   }

   static class IncomparableValueException extends ClassCastException {
      private static final long serialVersionUID = 0L;
      final Object value;

      IncomparableValueException(Object var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Cannot compare value: ");
         var2.append(var1);
         super(var2.toString());
         this.value = var1;
      }
   }
}
