package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Iterables {
   private Iterables() {
   }

   public static <T> boolean addAll(Collection<T> var0, Iterable<? extends T> var1) {
      return var1 instanceof Collection ? var0.addAll(Collections2.cast(var1)) : Iterators.addAll(var0, ((Iterable)Preconditions.checkNotNull(var1)).iterator());
   }

   public static <T> boolean all(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.all(var0.iterator(), var1);
   }

   public static <T> boolean any(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.any(var0.iterator(), var1);
   }

   private static <E> Collection<E> castOrCopyToCollection(Iterable<E> var0) {
      Object var1;
      if (var0 instanceof Collection) {
         var1 = (Collection)var0;
      } else {
         var1 = Lists.newArrayList(var0.iterator());
      }

      return (Collection)var1;
   }

   public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> var0) {
      return FluentIterable.concat(var0);
   }

   public static <T> Iterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1) {
      return FluentIterable.concat(var0, var1);
   }

   public static <T> Iterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1, Iterable<? extends T> var2) {
      return FluentIterable.concat(var0, var1, var2);
   }

   public static <T> Iterable<T> concat(Iterable<? extends T> var0, Iterable<? extends T> var1, Iterable<? extends T> var2, Iterable<? extends T> var3) {
      return FluentIterable.concat(var0, var1, var2, var3);
   }

   @SafeVarargs
   public static <T> Iterable<T> concat(Iterable<? extends T>... var0) {
      return FluentIterable.concat(var0);
   }

   public static <T> Iterable<T> consumingIterable(final Iterable<T> var0) {
      Preconditions.checkNotNull(var0);
      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            Iterable var1 = var0;
            Object var2;
            if (var1 instanceof Queue) {
               var2 = new ConsumingQueueIterator((Queue)var0);
            } else {
               var2 = Iterators.consumingIterator(var1.iterator());
            }

            return (Iterator)var2;
         }

         public String toString() {
            return "Iterables.consumingIterable(...)";
         }
      };
   }

   public static boolean contains(Iterable<?> var0, @NullableDecl Object var1) {
      return var0 instanceof Collection ? Collections2.safeContains((Collection)var0, var1) : Iterators.contains(var0.iterator(), var1);
   }

   public static <T> Iterable<T> cycle(final Iterable<T> var0) {
      Preconditions.checkNotNull(var0);
      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            return Iterators.cycle(var0);
         }

         public String toString() {
            StringBuilder var1 = new StringBuilder();
            var1.append(var0.toString());
            var1.append(" (cycled)");
            return var1.toString();
         }
      };
   }

   @SafeVarargs
   public static <T> Iterable<T> cycle(T... var0) {
      return cycle((Iterable)Lists.newArrayList(var0));
   }

   public static boolean elementsEqual(Iterable<?> var0, Iterable<?> var1) {
      if (var0 instanceof Collection && var1 instanceof Collection) {
         Collection var2 = (Collection)var0;
         Collection var3 = (Collection)var1;
         if (var2.size() != var3.size()) {
            return false;
         }
      }

      return Iterators.elementsEqual(var0.iterator(), var1.iterator());
   }

   public static <T> Iterable<T> filter(final Iterable<T> var0, final Predicate<? super T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            return Iterators.filter(var0.iterator(), var1);
         }
      };
   }

   public static <T> Iterable<T> filter(Iterable<?> var0, Class<T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return filter(var0, Predicates.instanceOf(var1));
   }

   public static <T> T find(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.find(var0.iterator(), var1);
   }

   @NullableDecl
   public static <T> T find(Iterable<? extends T> var0, Predicate<? super T> var1, @NullableDecl T var2) {
      return Iterators.find(var0.iterator(), var1, var2);
   }

   public static int frequency(Iterable<?> var0, @NullableDecl Object var1) {
      if (var0 instanceof Multiset) {
         return ((Multiset)var0).count(var1);
      } else {
         return var0 instanceof Set ? ((Set)var0).contains(var1) : Iterators.frequency(var0.iterator(), var1);
      }
   }

   public static <T> T get(Iterable<T> var0, int var1) {
      Preconditions.checkNotNull(var0);
      Object var2;
      if (var0 instanceof List) {
         var2 = ((List)var0).get(var1);
      } else {
         var2 = Iterators.get(var0.iterator(), var1);
      }

      return var2;
   }

   @NullableDecl
   public static <T> T get(Iterable<? extends T> var0, int var1, @NullableDecl T var2) {
      Preconditions.checkNotNull(var0);
      Iterators.checkNonnegative(var1);
      if (var0 instanceof List) {
         List var4 = Lists.cast(var0);
         if (var1 < var4.size()) {
            var2 = var4.get(var1);
         }

         return var2;
      } else {
         Iterator var3 = var0.iterator();
         Iterators.advance(var3, var1);
         return Iterators.getNext(var3, var2);
      }
   }

   @NullableDecl
   public static <T> T getFirst(Iterable<? extends T> var0, @NullableDecl T var1) {
      return Iterators.getNext(var0.iterator(), var1);
   }

   public static <T> T getLast(Iterable<T> var0) {
      if (var0 instanceof List) {
         List var1 = (List)var0;
         if (!var1.isEmpty()) {
            return getLastInNonemptyList(var1);
         } else {
            throw new NoSuchElementException();
         }
      } else {
         return Iterators.getLast(var0.iterator());
      }
   }

   @NullableDecl
   public static <T> T getLast(Iterable<? extends T> var0, @NullableDecl T var1) {
      if (var0 instanceof Collection) {
         if (Collections2.cast(var0).isEmpty()) {
            return var1;
         }

         if (var0 instanceof List) {
            return getLastInNonemptyList(Lists.cast(var0));
         }
      }

      return Iterators.getLast(var0.iterator(), var1);
   }

   private static <T> T getLastInNonemptyList(List<T> var0) {
      return var0.get(var0.size() - 1);
   }

   public static <T> T getOnlyElement(Iterable<T> var0) {
      return Iterators.getOnlyElement(var0.iterator());
   }

   @NullableDecl
   public static <T> T getOnlyElement(Iterable<? extends T> var0, @NullableDecl T var1) {
      return Iterators.getOnlyElement(var0.iterator(), var1);
   }

   public static <T> int indexOf(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.indexOf(var0.iterator(), var1);
   }

   public static boolean isEmpty(Iterable<?> var0) {
      return var0 instanceof Collection ? ((Collection)var0).isEmpty() : var0.iterator().hasNext() ^ true;
   }

   public static <T> Iterable<T> limit(final Iterable<T> var0, final int var1) {
      Preconditions.checkNotNull(var0);
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "limit is negative");
      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            return Iterators.limit(var0.iterator(), var1);
         }
      };
   }

   public static <T> Iterable<T> mergeSorted(final Iterable<? extends Iterable<? extends T>> var0, final Comparator<? super T> var1) {
      Preconditions.checkNotNull(var0, "iterables");
      Preconditions.checkNotNull(var1, "comparator");
      return new Iterables.UnmodifiableIterable(new FluentIterable<T>() {
         public Iterator<T> iterator() {
            return Iterators.mergeSorted(Iterables.transform(var0, Iterables.toIterator()), var1);
         }
      });
   }

   public static <T> Iterable<List<T>> paddedPartition(final Iterable<T> var0, final int var1) {
      Preconditions.checkNotNull(var0);
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      return new FluentIterable<List<T>>() {
         public Iterator<List<T>> iterator() {
            return Iterators.paddedPartition(var0.iterator(), var1);
         }
      };
   }

   public static <T> Iterable<List<T>> partition(final Iterable<T> var0, final int var1) {
      Preconditions.checkNotNull(var0);
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      return new FluentIterable<List<T>>() {
         public Iterator<List<T>> iterator() {
            return Iterators.partition(var0.iterator(), var1);
         }
      };
   }

   public static boolean removeAll(Iterable<?> var0, Collection<?> var1) {
      boolean var2;
      if (var0 instanceof Collection) {
         var2 = ((Collection)var0).removeAll((Collection)Preconditions.checkNotNull(var1));
      } else {
         var2 = Iterators.removeAll(var0.iterator(), var1);
      }

      return var2;
   }

   @NullableDecl
   static <T> T removeFirstMatching(Iterable<T> var0, Predicate<? super T> var1) {
      Preconditions.checkNotNull(var1);
      Iterator var2 = var0.iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = var2.next();
      } while(!var1.apply(var3));

      var2.remove();
      return var3;
   }

   public static <T> boolean removeIf(Iterable<T> var0, Predicate<? super T> var1) {
      return var0 instanceof RandomAccess && var0 instanceof List ? removeIfFromRandomAccessList((List)var0, (Predicate)Preconditions.checkNotNull(var1)) : Iterators.removeIf(var0.iterator(), var1);
   }

   private static <T> boolean removeIfFromRandomAccessList(List<T> var0, Predicate<? super T> var1) {
      boolean var2 = false;
      int var3 = 0;

      int var4;
      int var6;
      for(var4 = 0; var3 < var0.size(); var4 = var6) {
         Object var5 = var0.get(var3);
         var6 = var4;
         if (!var1.apply(var5)) {
            if (var3 > var4) {
               try {
                  var0.set(var4, var5);
               } catch (UnsupportedOperationException var7) {
                  slowRemoveIfForRemainingElements(var0, var1, var4, var3);
                  return true;
               } catch (IllegalArgumentException var8) {
                  slowRemoveIfForRemainingElements(var0, var1, var4, var3);
                  return true;
               }
            }

            var6 = var4 + 1;
         }

         ++var3;
      }

      var0.subList(var4, var0.size()).clear();
      if (var3 != var4) {
         var2 = true;
      }

      return var2;
   }

   public static boolean retainAll(Iterable<?> var0, Collection<?> var1) {
      boolean var2;
      if (var0 instanceof Collection) {
         var2 = ((Collection)var0).retainAll((Collection)Preconditions.checkNotNull(var1));
      } else {
         var2 = Iterators.retainAll(var0.iterator(), var1);
      }

      return var2;
   }

   public static int size(Iterable<?> var0) {
      int var1;
      if (var0 instanceof Collection) {
         var1 = ((Collection)var0).size();
      } else {
         var1 = Iterators.size(var0.iterator());
      }

      return var1;
   }

   public static <T> Iterable<T> skip(final Iterable<T> var0, final int var1) {
      Preconditions.checkNotNull(var0);
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "number to skip cannot be negative");
      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            Iterable var1x = var0;
            if (var1x instanceof List) {
               List var3 = (List)var1x;
               return var3.subList(Math.min(var3.size(), var1), var3.size()).iterator();
            } else {
               final Iterator var2 = var1x.iterator();
               Iterators.advance(var2, var1);
               return new Iterator<T>() {
                  boolean atStart = true;

                  public boolean hasNext() {
                     return var2.hasNext();
                  }

                  public T next() {
                     Object var1x = var2.next();
                     this.atStart = false;
                     return var1x;
                  }

                  public void remove() {
                     CollectPreconditions.checkRemove(this.atStart ^ true);
                     var2.remove();
                  }
               };
            }
         }
      };
   }

   private static <T> void slowRemoveIfForRemainingElements(List<T> var0, Predicate<? super T> var1, int var2, int var3) {
      for(int var4 = var0.size() - 1; var4 > var3; --var4) {
         if (var1.apply(var0.get(var4))) {
            var0.remove(var4);
         }
      }

      --var3;

      while(var3 >= var2) {
         var0.remove(var3);
         --var3;
      }

   }

   static Object[] toArray(Iterable<?> var0) {
      return castOrCopyToCollection(var0).toArray();
   }

   public static <T> T[] toArray(Iterable<? extends T> var0, Class<T> var1) {
      return toArray(var0, ObjectArrays.newArray((Class)var1, 0));
   }

   static <T> T[] toArray(Iterable<? extends T> var0, T[] var1) {
      return castOrCopyToCollection(var0).toArray(var1);
   }

   static <T> Function<Iterable<? extends T>, Iterator<? extends T>> toIterator() {
      return new Function<Iterable<? extends T>, Iterator<? extends T>>() {
         public Iterator<? extends T> apply(Iterable<? extends T> var1) {
            return var1.iterator();
         }
      };
   }

   public static String toString(Iterable<?> var0) {
      return Iterators.toString(var0.iterator());
   }

   public static <F, T> Iterable<T> transform(final Iterable<F> var0, final Function<? super F, ? extends T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new FluentIterable<T>() {
         public Iterator<T> iterator() {
            return Iterators.transform(var0.iterator(), var1);
         }
      };
   }

   public static <T> Optional<T> tryFind(Iterable<T> var0, Predicate<? super T> var1) {
      return Iterators.tryFind(var0.iterator(), var1);
   }

   @Deprecated
   public static <E> Iterable<E> unmodifiableIterable(ImmutableCollection<E> var0) {
      return (Iterable)Preconditions.checkNotNull(var0);
   }

   public static <T> Iterable<T> unmodifiableIterable(Iterable<? extends T> var0) {
      Preconditions.checkNotNull(var0);
      return (Iterable)(!(var0 instanceof Iterables.UnmodifiableIterable) && !(var0 instanceof ImmutableCollection) ? new Iterables.UnmodifiableIterable(var0) : var0);
   }

   private static final class UnmodifiableIterable<T> extends FluentIterable<T> {
      private final Iterable<? extends T> iterable;

      private UnmodifiableIterable(Iterable<? extends T> var1) {
         this.iterable = var1;
      }

      // $FF: synthetic method
      UnmodifiableIterable(Iterable var1, Object var2) {
         this(var1);
      }

      public Iterator<T> iterator() {
         return Iterators.unmodifiableIterator(this.iterable.iterator());
      }

      public String toString() {
         return this.iterable.toString();
      }
   }
}
