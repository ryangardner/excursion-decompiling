package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class FluentIterable<E> implements Iterable<E> {
   private final Optional<Iterable<E>> iterableDelegate;

   protected FluentIterable() {
      this.iterableDelegate = Optional.absent();
   }

   FluentIterable(Iterable<E> var1) {
      Preconditions.checkNotNull(var1);
      if (this == var1) {
         var1 = null;
      }

      this.iterableDelegate = Optional.fromNullable(var1);
   }

   public static <T> FluentIterable<T> concat(final Iterable<? extends Iterable<? extends T>> var0) {
      Preconditions.checkNotNull(var0);
      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            return Iterators.concat(Iterators.transform(var0.iterator(), Iterables.toIterator()));
         }
      };
   }

   public static <T> FluentIterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1) {
      return concatNoDefensiveCopy(var0, var1);
   }

   public static <T> FluentIterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1, Iterable<? extends T> var2) {
      return concatNoDefensiveCopy(var0, var1, var2);
   }

   public static <T> FluentIterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1, Iterable<? extends T> var2, Iterable<? extends T> var3) {
      return concatNoDefensiveCopy(var0, var1, var2, var3);
   }

   public static <T> FluentIterable<T> concat(Iterable<? extends T>... var0) {
      return concatNoDefensiveCopy((Iterable[])Arrays.copyOf(var0, var0.length));
   }

   private static <T> FluentIterable<T> concatNoDefensiveCopy(final Iterable<? extends T>... var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Preconditions.checkNotNull(var0[var2]);
      }

      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            return Iterators.concat((Iterator)(new AbstractIndexedListIterator<Iterator<? extends T>>(var0.length) {
               public Iterator<? extends T> get(int var1) {
                  return var0[var1].iterator();
               }
            }));
         }
      };
   }

   @Deprecated
   public static <E> FluentIterable<E> from(FluentIterable<E> var0) {
      return (FluentIterable)Preconditions.checkNotNull(var0);
   }

   public static <E> FluentIterable<E> from(final Iterable<E> var0) {
      FluentIterable var1;
      if (var0 instanceof FluentIterable) {
         var1 = (FluentIterable)var0;
      } else {
         var1 = new FluentIterable<E>(var0) {
            public Iterator<E> iterator() {
               return var0.iterator();
            }
         };
      }

      return var1;
   }

   public static <E> FluentIterable<E> from(E[] var0) {
      return from((Iterable)Arrays.asList(var0));
   }

   private Iterable<E> getDelegate() {
      return (Iterable)this.iterableDelegate.or((Object)this);
   }

   public static <E> FluentIterable<E> of() {
      return from((Iterable)ImmutableList.of());
   }

   public static <E> FluentIterable<E> of(@NullableDecl E var0, E... var1) {
      return from((Iterable)Lists.asList(var0, var1));
   }

   public final boolean allMatch(Predicate<? super E> var1) {
      return Iterables.all(this.getDelegate(), var1);
   }

   public final boolean anyMatch(Predicate<? super E> var1) {
      return Iterables.any(this.getDelegate(), var1);
   }

   public final FluentIterable<E> append(Iterable<? extends E> var1) {
      return concat(this.getDelegate(), var1);
   }

   public final FluentIterable<E> append(E... var1) {
      return concat(this.getDelegate(), Arrays.asList(var1));
   }

   public final boolean contains(@NullableDecl Object var1) {
      return Iterables.contains(this.getDelegate(), var1);
   }

   public final <C extends Collection<? super E>> C copyInto(C var1) {
      Preconditions.checkNotNull(var1);
      Iterable var2 = this.getDelegate();
      if (var2 instanceof Collection) {
         var1.addAll(Collections2.cast(var2));
      } else {
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            var1.add(var3.next());
         }
      }

      return var1;
   }

   public final FluentIterable<E> cycle() {
      return from(Iterables.cycle(this.getDelegate()));
   }

   public final FluentIterable<E> filter(Predicate<? super E> var1) {
      return from(Iterables.filter(this.getDelegate(), var1));
   }

   public final <T> FluentIterable<T> filter(Class<T> var1) {
      return from(Iterables.filter(this.getDelegate(), var1));
   }

   public final Optional<E> first() {
      Iterator var1 = this.getDelegate().iterator();
      Optional var2;
      if (var1.hasNext()) {
         var2 = Optional.of(var1.next());
      } else {
         var2 = Optional.absent();
      }

      return var2;
   }

   public final Optional<E> firstMatch(Predicate<? super E> var1) {
      return Iterables.tryFind(this.getDelegate(), var1);
   }

   public final E get(int var1) {
      return Iterables.get(this.getDelegate(), var1);
   }

   public final <K> ImmutableListMultimap<K, E> index(Function<? super E, K> var1) {
      return Multimaps.index(this.getDelegate(), var1);
   }

   public final boolean isEmpty() {
      return this.getDelegate().iterator().hasNext() ^ true;
   }

   public final String join(Joiner var1) {
      return var1.join((Iterable)this);
   }

   public final Optional<E> last() {
      Iterable var1 = this.getDelegate();
      if (var1 instanceof List) {
         List var4 = (List)var1;
         return var4.isEmpty() ? Optional.absent() : Optional.of(var4.get(var4.size() - 1));
      } else {
         Iterator var2 = var1.iterator();
         if (!var2.hasNext()) {
            return Optional.absent();
         } else if (var1 instanceof SortedSet) {
            return Optional.of(((SortedSet)var1).last());
         } else {
            Object var3;
            do {
               var3 = var2.next();
            } while(var2.hasNext());

            return Optional.of(var3);
         }
      }
   }

   public final FluentIterable<E> limit(int var1) {
      return from(Iterables.limit(this.getDelegate(), var1));
   }

   public final int size() {
      return Iterables.size(this.getDelegate());
   }

   public final FluentIterable<E> skip(int var1) {
      return from(Iterables.skip(this.getDelegate(), var1));
   }

   public final E[] toArray(Class<E> var1) {
      return Iterables.toArray(this.getDelegate(), var1);
   }

   public final ImmutableList<E> toList() {
      return ImmutableList.copyOf(this.getDelegate());
   }

   public final <V> ImmutableMap<E, V> toMap(Function<? super E, V> var1) {
      return Maps.toMap(this.getDelegate(), var1);
   }

   public final ImmutableMultiset<E> toMultiset() {
      return ImmutableMultiset.copyOf(this.getDelegate());
   }

   public final ImmutableSet<E> toSet() {
      return ImmutableSet.copyOf(this.getDelegate());
   }

   public final ImmutableList<E> toSortedList(Comparator<? super E> var1) {
      return Ordering.from(var1).immutableSortedCopy(this.getDelegate());
   }

   public final ImmutableSortedSet<E> toSortedSet(Comparator<? super E> var1) {
      return ImmutableSortedSet.copyOf(var1, this.getDelegate());
   }

   public String toString() {
      return Iterables.toString(this.getDelegate());
   }

   public final <T> FluentIterable<T> transform(Function<? super E, T> var1) {
      return from(Iterables.transform(this.getDelegate(), var1));
   }

   public <T> FluentIterable<T> transformAndConcat(Function<? super E, ? extends Iterable<? extends T>> var1) {
      return concat((Iterable)this.transform(var1));
   }

   public final <K> ImmutableMap<K, E> uniqueIndex(Function<? super E, K> var1) {
      return Maps.uniqueIndex(this.getDelegate(), var1);
   }

   private static class FromIterableFunction<E> implements Function<Iterable<E>, FluentIterable<E>> {
      public FluentIterable<E> apply(Iterable<E> var1) {
         return FluentIterable.from(var1);
      }
   }
}
