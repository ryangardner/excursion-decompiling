package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.primitives.Ints;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Iterators {
   private Iterators() {
   }

   public static <T> boolean addAll(Collection<T> var0, Iterator<? extends T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);

      boolean var2;
      for(var2 = false; var1.hasNext(); var2 |= var0.add(var1.next())) {
      }

      return var2;
   }

   public static int advance(Iterator<?> var0, int var1) {
      Preconditions.checkNotNull(var0);
      int var2 = 0;
      boolean var3;
      if (var1 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "numberToAdvance must be nonnegative");

      while(var2 < var1 && var0.hasNext()) {
         var0.next();
         ++var2;
      }

      return var2;
   }

   public static <T> boolean all(Iterator<T> var0, Predicate<? super T> var1) {
      Preconditions.checkNotNull(var1);

      do {
         if (!var0.hasNext()) {
            return true;
         }
      } while(var1.apply(var0.next()));

      return false;
   }

   public static <T> boolean any(Iterator<T> var0, Predicate<? super T> var1) {
      boolean var2;
      if (indexOf(var0, var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static <T> Enumeration<T> asEnumeration(final Iterator<T> var0) {
      Preconditions.checkNotNull(var0);
      return new Enumeration<T>() {
         public boolean hasMoreElements() {
            return var0.hasNext();
         }

         public T nextElement() {
            return var0.next();
         }
      };
   }

   static <T> ListIterator<T> cast(Iterator<T> var0) {
      return (ListIterator)var0;
   }

   static void checkNonnegative(int var0) {
      if (var0 < 0) {
         StringBuilder var1 = new StringBuilder();
         var1.append("position (");
         var1.append(var0);
         var1.append(") must not be negative");
         throw new IndexOutOfBoundsException(var1.toString());
      }
   }

   static void clear(Iterator<?> var0) {
      Preconditions.checkNotNull(var0);

      while(var0.hasNext()) {
         var0.next();
         var0.remove();
      }

   }

   public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> var0) {
      return new Iterators.ConcatenatedIterator(var0);
   }

   public static <T> Iterator<T> concat(Iterator<? extends T> var0, Iterator<? extends T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return concat(consumingForArray(var0, var1));
   }

   public static <T> Iterator<T> concat(Iterator<? extends T> var0, Iterator<? extends T> var1, Iterator<? extends T> var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      return concat(consumingForArray(var0, var1, var2));
   }

   public static <T> Iterator<T> concat(Iterator<? extends T> var0, Iterator<? extends T> var1, Iterator<? extends T> var2, Iterator<? extends T> var3) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var3);
      return concat(consumingForArray(var0, var1, var2, var3));
   }

   public static <T> Iterator<T> concat(Iterator<? extends T>... var0) {
      return concatNoDefensiveCopy((Iterator[])Arrays.copyOf(var0, var0.length));
   }

   static <T> Iterator<T> concatNoDefensiveCopy(Iterator<? extends T>... var0) {
      Iterator[] var1 = (Iterator[])Preconditions.checkNotNull(var0);
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Preconditions.checkNotNull(var1[var3]);
      }

      return concat(consumingForArray(var0));
   }

   private static <T> Iterator<T> consumingForArray(final T... var0) {
      return new UnmodifiableIterator<T>() {
         int index = 0;

         public boolean hasNext() {
            boolean var1;
            if (this.index < var0.length) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public T next() {
            if (this.hasNext()) {
               Object[] var1 = var0;
               int var2 = this.index;
               Object var3 = var1[var2];
               var1[var2] = null;
               this.index = var2 + 1;
               return var3;
            } else {
               throw new NoSuchElementException();
            }
         }
      };
   }

   public static <T> Iterator<T> consumingIterator(final Iterator<T> var0) {
      Preconditions.checkNotNull(var0);
      return new UnmodifiableIterator<T>() {
         public boolean hasNext() {
            return var0.hasNext();
         }

         public T next() {
            Object var1 = var0.next();
            var0.remove();
            return var1;
         }

         public String toString() {
            return "Iterators.consumingIterator(...)";
         }
      };
   }

   public static boolean contains(Iterator<?> var0, @NullableDecl Object var1) {
      if (var1 == null) {
         while(var0.hasNext()) {
            if (var0.next() == null) {
               return true;
            }
         }
      } else {
         while(var0.hasNext()) {
            if (var1.equals(var0.next())) {
               return true;
            }
         }
      }

      return false;
   }

   public static <T> Iterator<T> cycle(final Iterable<T> var0) {
      Preconditions.checkNotNull(var0);
      return new Iterator<T>() {
         Iterator<T> iterator = Iterators.emptyModifiableIterator();

         public boolean hasNext() {
            boolean var1;
            if (!this.iterator.hasNext() && !var0.iterator().hasNext()) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public T next() {
            if (!this.iterator.hasNext()) {
               Iterator var1 = var0.iterator();
               this.iterator = var1;
               if (!var1.hasNext()) {
                  throw new NoSuchElementException();
               }
            }

            return this.iterator.next();
         }

         public void remove() {
            this.iterator.remove();
         }
      };
   }

   @SafeVarargs
   public static <T> Iterator<T> cycle(T... var0) {
      return cycle((Iterable)Lists.newArrayList(var0));
   }

   public static boolean elementsEqual(Iterator<?> var0, Iterator<?> var1) {
      while(true) {
         if (var0.hasNext()) {
            if (!var1.hasNext()) {
               return false;
            }

            if (Objects.equal(var0.next(), var1.next())) {
               continue;
            }

            return false;
         }

         return var1.hasNext() ^ true;
      }
   }

   static <T> UnmodifiableIterator<T> emptyIterator() {
      return emptyListIterator();
   }

   static <T> UnmodifiableListIterator<T> emptyListIterator() {
      return Iterators.ArrayItr.EMPTY;
   }

   static <T> Iterator<T> emptyModifiableIterator() {
      return Iterators.EmptyModifiableIterator.INSTANCE;
   }

   public static <T> UnmodifiableIterator<T> filter(final Iterator<T> var0, final Predicate<? super T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new AbstractIterator<T>() {
         protected T computeNext() {
            while(true) {
               if (var0.hasNext()) {
                  Object var1x = var0.next();
                  if (!var1.apply(var1x)) {
                     continue;
                  }

                  return var1x;
               }

               return this.endOfData();
            }
         }
      };
   }

   public static <T> UnmodifiableIterator<T> filter(Iterator<?> var0, Class<T> var1) {
      return filter(var0, Predicates.instanceOf(var1));
   }

   public static <T> T find(Iterator<T> var0, Predicate<? super T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);

      Object var2;
      do {
         if (!var0.hasNext()) {
            throw new NoSuchElementException();
         }

         var2 = var0.next();
      } while(!var1.apply(var2));

      return var2;
   }

   @NullableDecl
   public static <T> T find(Iterator<? extends T> var0, Predicate<? super T> var1, @NullableDecl T var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);

      Object var3;
      do {
         if (!var0.hasNext()) {
            return var2;
         }

         var3 = var0.next();
      } while(!var1.apply(var3));

      return var3;
   }

   @SafeVarargs
   public static <T> UnmodifiableIterator<T> forArray(T... var0) {
      return forArray(var0, 0, var0.length, 0);
   }

   static <T> UnmodifiableListIterator<T> forArray(T[] var0, int var1, int var2, int var3) {
      boolean var4;
      if (var2 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      Preconditions.checkPositionIndexes(var1, var1 + var2, var0.length);
      Preconditions.checkPositionIndex(var3, var2);
      return (UnmodifiableListIterator)(var2 == 0 ? emptyListIterator() : new Iterators.ArrayItr(var0, var1, var2, var3));
   }

   public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> var0) {
      Preconditions.checkNotNull(var0);
      return new UnmodifiableIterator<T>() {
         public boolean hasNext() {
            return var0.hasMoreElements();
         }

         public T next() {
            return var0.nextElement();
         }
      };
   }

   public static int frequency(Iterator<?> var0, @NullableDecl Object var1) {
      int var2;
      for(var2 = 0; contains(var0, var1); ++var2) {
      }

      return var2;
   }

   public static <T> T get(Iterator<T> var0, int var1) {
      checkNonnegative(var1);
      int var2 = advance(var0, var1);
      if (var0.hasNext()) {
         return var0.next();
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("position (");
         var3.append(var1);
         var3.append(") must be less than the number of elements that remained (");
         var3.append(var2);
         var3.append(")");
         throw new IndexOutOfBoundsException(var3.toString());
      }
   }

   @NullableDecl
   public static <T> T get(Iterator<? extends T> var0, int var1, @NullableDecl T var2) {
      checkNonnegative(var1);
      advance(var0, var1);
      return getNext(var0, var2);
   }

   public static <T> T getLast(Iterator<T> var0) {
      Object var1;
      do {
         var1 = var0.next();
      } while(var0.hasNext());

      return var1;
   }

   @NullableDecl
   public static <T> T getLast(Iterator<? extends T> var0, @NullableDecl T var1) {
      if (var0.hasNext()) {
         var1 = getLast(var0);
      }

      return var1;
   }

   @NullableDecl
   public static <T> T getNext(Iterator<? extends T> var0, @NullableDecl T var1) {
      if (var0.hasNext()) {
         var1 = var0.next();
      }

      return var1;
   }

   public static <T> T getOnlyElement(Iterator<T> var0) {
      Object var1 = var0.next();
      if (!var0.hasNext()) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("expected one element but was: <");
         var2.append(var1);

         for(int var3 = 0; var3 < 4 && var0.hasNext(); ++var3) {
            var2.append(", ");
            var2.append(var0.next());
         }

         if (var0.hasNext()) {
            var2.append(", ...");
         }

         var2.append('>');
         throw new IllegalArgumentException(var2.toString());
      }
   }

   @NullableDecl
   public static <T> T getOnlyElement(Iterator<? extends T> var0, @NullableDecl T var1) {
      if (var0.hasNext()) {
         var1 = getOnlyElement(var0);
      }

      return var1;
   }

   public static <T> int indexOf(Iterator<T> var0, Predicate<? super T> var1) {
      Preconditions.checkNotNull(var1, "predicate");

      for(int var2 = 0; var0.hasNext(); ++var2) {
         if (var1.apply(var0.next())) {
            return var2;
         }
      }

      return -1;
   }

   public static <T> Iterator<T> limit(final Iterator<T> var0, final int var1) {
      Preconditions.checkNotNull(var0);
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "limit is negative");
      return new Iterator<T>() {
         private int count;

         public boolean hasNext() {
            boolean var1x;
            if (this.count < var1 && var0.hasNext()) {
               var1x = true;
            } else {
               var1x = false;
            }

            return var1x;
         }

         public T next() {
            if (this.hasNext()) {
               ++this.count;
               return var0.next();
            } else {
               throw new NoSuchElementException();
            }
         }

         public void remove() {
            var0.remove();
         }
      };
   }

   public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> var0, Comparator<? super T> var1) {
      Preconditions.checkNotNull(var0, "iterators");
      Preconditions.checkNotNull(var1, "comparator");
      return new Iterators.MergingIterator(var0, var1);
   }

   public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> var0, int var1) {
      return partitionImpl(var0, var1, true);
   }

   public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> var0, int var1) {
      return partitionImpl(var0, var1, false);
   }

   private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> var0, final int var1, final boolean var2) {
      Preconditions.checkNotNull(var0);
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      return new UnmodifiableIterator<List<T>>() {
         public boolean hasNext() {
            return var0.hasNext();
         }

         public List<T> next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               Object[] var1x = new Object[var1];

               int var2x;
               for(var2x = 0; var2x < var1 && var0.hasNext(); ++var2x) {
                  var1x[var2x] = var0.next();
               }

               for(int var3 = var2x; var3 < var1; ++var3) {
                  var1x[var3] = null;
               }

               List var4 = Collections.unmodifiableList(Arrays.asList(var1x));
               List var5 = var4;
               if (!var2) {
                  if (var2x == var1) {
                     var5 = var4;
                  } else {
                     var5 = var4.subList(0, var2x);
                  }
               }

               return var5;
            }
         }
      };
   }

   @Deprecated
   public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> var0) {
      return (PeekingIterator)Preconditions.checkNotNull(var0);
   }

   public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> var0) {
      return var0 instanceof Iterators.PeekingImpl ? (Iterators.PeekingImpl)var0 : new Iterators.PeekingImpl(var0);
   }

   @NullableDecl
   static <T> T pollNext(Iterator<T> var0) {
      if (var0.hasNext()) {
         Object var1 = var0.next();
         var0.remove();
         return var1;
      } else {
         return null;
      }
   }

   public static boolean removeAll(Iterator<?> var0, Collection<?> var1) {
      Preconditions.checkNotNull(var1);
      boolean var2 = false;

      while(var0.hasNext()) {
         if (var1.contains(var0.next())) {
            var0.remove();
            var2 = true;
         }
      }

      return var2;
   }

   public static <T> boolean removeIf(Iterator<T> var0, Predicate<? super T> var1) {
      Preconditions.checkNotNull(var1);
      boolean var2 = false;

      while(var0.hasNext()) {
         if (var1.apply(var0.next())) {
            var0.remove();
            var2 = true;
         }
      }

      return var2;
   }

   public static boolean retainAll(Iterator<?> var0, Collection<?> var1) {
      Preconditions.checkNotNull(var1);
      boolean var2 = false;

      while(var0.hasNext()) {
         if (!var1.contains(var0.next())) {
            var0.remove();
            var2 = true;
         }
      }

      return var2;
   }

   public static <T> UnmodifiableIterator<T> singletonIterator(@NullableDecl final T var0) {
      return new UnmodifiableIterator<T>() {
         boolean done;

         public boolean hasNext() {
            return this.done ^ true;
         }

         public T next() {
            if (!this.done) {
               this.done = true;
               return var0;
            } else {
               throw new NoSuchElementException();
            }
         }
      };
   }

   public static int size(Iterator<?> var0) {
      long var1;
      for(var1 = 0L; var0.hasNext(); ++var1) {
         var0.next();
      }

      return Ints.saturatedCast(var1);
   }

   public static <T> T[] toArray(Iterator<? extends T> var0, Class<T> var1) {
      return Iterables.toArray(Lists.newArrayList(var0), (Class)var1);
   }

   public static String toString(Iterator<?> var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append('[');
      boolean var2 = true;

      while(var0.hasNext()) {
         if (!var2) {
            var1.append(", ");
         }

         var2 = false;
         var1.append(var0.next());
      }

      var1.append(']');
      return var1.toString();
   }

   public static <F, T> Iterator<T> transform(Iterator<F> var0, final Function<? super F, ? extends T> var1) {
      Preconditions.checkNotNull(var1);
      return new TransformedIterator<F, T>(var0) {
         T transform(F var1x) {
            return var1.apply(var1x);
         }
      };
   }

   public static <T> Optional<T> tryFind(Iterator<T> var0, Predicate<? super T> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);

      Object var2;
      do {
         if (!var0.hasNext()) {
            return Optional.absent();
         }

         var2 = var0.next();
      } while(!var1.apply(var2));

      return Optional.of(var2);
   }

   @Deprecated
   public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> var0) {
      return (UnmodifiableIterator)Preconditions.checkNotNull(var0);
   }

   public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<? extends T> var0) {
      Preconditions.checkNotNull(var0);
      return var0 instanceof UnmodifiableIterator ? (UnmodifiableIterator)var0 : new UnmodifiableIterator<T>() {
         public boolean hasNext() {
            return var0.hasNext();
         }

         public T next() {
            return var0.next();
         }
      };
   }

   private static final class ArrayItr<T> extends AbstractIndexedListIterator<T> {
      static final UnmodifiableListIterator<Object> EMPTY = new Iterators.ArrayItr(new Object[0], 0, 0, 0);
      private final T[] array;
      private final int offset;

      ArrayItr(T[] var1, int var2, int var3, int var4) {
         super(var3, var4);
         this.array = var1;
         this.offset = var2;
      }

      protected T get(int var1) {
         return this.array[this.offset + var1];
      }
   }

   private static class ConcatenatedIterator<T> implements Iterator<T> {
      private Iterator<? extends T> iterator = Iterators.emptyListIterator();
      @NullableDecl
      private Deque<Iterator<? extends Iterator<? extends T>>> metaIterators;
      @NullableDecl
      private Iterator<? extends T> toRemove;
      private Iterator<? extends Iterator<? extends T>> topMetaIterator;

      ConcatenatedIterator(Iterator<? extends Iterator<? extends T>> var1) {
         this.topMetaIterator = (Iterator)Preconditions.checkNotNull(var1);
      }

      @NullableDecl
      private Iterator<? extends Iterator<? extends T>> getTopMetaIterator() {
         while(true) {
            Iterator var1 = this.topMetaIterator;
            if (var1 != null && var1.hasNext()) {
               return this.topMetaIterator;
            }

            Deque var2 = this.metaIterators;
            if (var2 == null || var2.isEmpty()) {
               return null;
            }

            this.topMetaIterator = (Iterator)this.metaIterators.removeFirst();
         }
      }

      public boolean hasNext() {
         while(!((Iterator)Preconditions.checkNotNull(this.iterator)).hasNext()) {
            Iterator var1 = this.getTopMetaIterator();
            this.topMetaIterator = var1;
            if (var1 == null) {
               return false;
            }

            var1 = (Iterator)var1.next();
            this.iterator = var1;
            if (var1 instanceof Iterators.ConcatenatedIterator) {
               Iterators.ConcatenatedIterator var2 = (Iterators.ConcatenatedIterator)var1;
               this.iterator = var2.iterator;
               if (this.metaIterators == null) {
                  this.metaIterators = new ArrayDeque();
               }

               this.metaIterators.addFirst(this.topMetaIterator);
               if (var2.metaIterators != null) {
                  while(!var2.metaIterators.isEmpty()) {
                     this.metaIterators.addFirst(var2.metaIterators.removeLast());
                  }
               }

               this.topMetaIterator = var2.topMetaIterator;
            }
         }

         return true;
      }

      public T next() {
         if (this.hasNext()) {
            Iterator var1 = this.iterator;
            this.toRemove = var1;
            return var1.next();
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         boolean var1;
         if (this.toRemove != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         CollectPreconditions.checkRemove(var1);
         this.toRemove.remove();
         this.toRemove = null;
      }
   }

   private static enum EmptyModifiableIterator implements Iterator<Object> {
      INSTANCE;

      static {
         Iterators.EmptyModifiableIterator var0 = new Iterators.EmptyModifiableIterator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         CollectPreconditions.checkRemove(false);
      }
   }

   private static class MergingIterator<T> extends UnmodifiableIterator<T> {
      final Queue<PeekingIterator<T>> queue;

      public MergingIterator(Iterable<? extends Iterator<? extends T>> var1, final Comparator<? super T> var2) {
         this.queue = new PriorityQueue(2, new Comparator<PeekingIterator<T>>() {
            public int compare(PeekingIterator<T> var1, PeekingIterator<T> var2x) {
               return var2.compare(var1.peek(), var2x.peek());
            }
         });
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Iterator var4 = (Iterator)var3.next();
            if (var4.hasNext()) {
               this.queue.add(Iterators.peekingIterator(var4));
            }
         }

      }

      public boolean hasNext() {
         return this.queue.isEmpty() ^ true;
      }

      public T next() {
         PeekingIterator var1 = (PeekingIterator)this.queue.remove();
         Object var2 = var1.next();
         if (var1.hasNext()) {
            this.queue.add(var1);
         }

         return var2;
      }
   }

   private static class PeekingImpl<E> implements PeekingIterator<E> {
      private boolean hasPeeked;
      private final Iterator<? extends E> iterator;
      @NullableDecl
      private E peekedElement;

      public PeekingImpl(Iterator<? extends E> var1) {
         this.iterator = (Iterator)Preconditions.checkNotNull(var1);
      }

      public boolean hasNext() {
         boolean var1;
         if (!this.hasPeeked && !this.iterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public E next() {
         if (!this.hasPeeked) {
            return this.iterator.next();
         } else {
            Object var1 = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return var1;
         }
      }

      public E peek() {
         if (!this.hasPeeked) {
            this.peekedElement = this.iterator.next();
            this.hasPeeked = true;
         }

         return this.peekedElement;
      }

      public void remove() {
         Preconditions.checkState(this.hasPeeked ^ true, "Can't remove after you've peeked at next");
         this.iterator.remove();
      }
   }
}
