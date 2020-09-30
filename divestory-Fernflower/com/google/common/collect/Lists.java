package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Lists {
   private Lists() {
   }

   static <E> boolean addAllImpl(List<E> var0, int var1, Iterable<? extends E> var2) {
      ListIterator var4 = var0.listIterator(var1);
      Iterator var5 = var2.iterator();

      boolean var3;
      for(var3 = false; var5.hasNext(); var3 = true) {
         var4.add(var5.next());
      }

      return var3;
   }

   public static <E> List<E> asList(@NullableDecl E var0, @NullableDecl E var1, E[] var2) {
      return new Lists.TwoPlusArrayList(var0, var1, var2);
   }

   public static <E> List<E> asList(@NullableDecl E var0, E[] var1) {
      return new Lists.OnePlusArrayList(var0, var1);
   }

   public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> var0) {
      return CartesianList.create(var0);
   }

   @SafeVarargs
   public static <B> List<List<B>> cartesianProduct(List<? extends B>... var0) {
      return cartesianProduct(Arrays.asList(var0));
   }

   static <T> List<T> cast(Iterable<T> var0) {
      return (List)var0;
   }

   public static ImmutableList<Character> charactersOf(String var0) {
      return new Lists.StringAsImmutableList((String)Preconditions.checkNotNull(var0));
   }

   public static List<Character> charactersOf(CharSequence var0) {
      return new Lists.CharSequenceAsList((CharSequence)Preconditions.checkNotNull(var0));
   }

   static int computeArrayListCapacity(int var0) {
      CollectPreconditions.checkNonnegative(var0, "arraySize");
      return Ints.saturatedCast((long)var0 + 5L + (long)(var0 / 10));
   }

   static boolean equalsImpl(List<?> var0, @NullableDecl Object var1) {
      if (var1 == Preconditions.checkNotNull(var0)) {
         return true;
      } else if (!(var1 instanceof List)) {
         return false;
      } else {
         List var4 = (List)var1;
         int var2 = var0.size();
         if (var2 != var4.size()) {
            return false;
         } else if (var0 instanceof RandomAccess && var4 instanceof RandomAccess) {
            for(int var3 = 0; var3 < var2; ++var3) {
               if (!Objects.equal(var0.get(var3), var4.get(var3))) {
                  return false;
               }
            }

            return true;
         } else {
            return Iterators.elementsEqual(var0.iterator(), var4.iterator());
         }
      }
   }

   static int hashCodeImpl(List<?> var0) {
      Iterator var4 = var0.iterator();

      int var1;
      int var3;
      for(var1 = 1; var4.hasNext(); var1 = var1 * 31 + var3) {
         Object var2 = var4.next();
         if (var2 == null) {
            var3 = 0;
         } else {
            var3 = var2.hashCode();
         }
      }

      return var1;
   }

   static int indexOfImpl(List<?> var0, @NullableDecl Object var1) {
      if (var0 instanceof RandomAccess) {
         return indexOfRandomAccess(var0, var1);
      } else {
         ListIterator var2 = var0.listIterator();

         do {
            if (!var2.hasNext()) {
               return -1;
            }
         } while(!Objects.equal(var1, var2.next()));

         return var2.previousIndex();
      }
   }

   private static int indexOfRandomAccess(List<?> var0, @NullableDecl Object var1) {
      int var2 = var0.size();
      int var3 = 0;
      byte var4 = 0;
      if (var1 == null) {
         for(var3 = var4; var3 < var2; ++var3) {
            if (var0.get(var3) == null) {
               return var3;
            }
         }
      } else {
         while(var3 < var2) {
            if (var1.equals(var0.get(var3))) {
               return var3;
            }

            ++var3;
         }
      }

      return -1;
   }

   static int lastIndexOfImpl(List<?> var0, @NullableDecl Object var1) {
      if (var0 instanceof RandomAccess) {
         return lastIndexOfRandomAccess(var0, var1);
      } else {
         ListIterator var2 = var0.listIterator(var0.size());

         do {
            if (!var2.hasPrevious()) {
               return -1;
            }
         } while(!Objects.equal(var1, var2.previous()));

         return var2.nextIndex();
      }
   }

   private static int lastIndexOfRandomAccess(List<?> var0, @NullableDecl Object var1) {
      int var2;
      if (var1 == null) {
         for(var2 = var0.size() - 1; var2 >= 0; --var2) {
            if (var0.get(var2) == null) {
               return var2;
            }
         }
      } else {
         for(var2 = var0.size() - 1; var2 >= 0; --var2) {
            if (var1.equals(var0.get(var2))) {
               return var2;
            }
         }
      }

      return -1;
   }

   static <E> ListIterator<E> listIteratorImpl(List<E> var0, int var1) {
      return (new Lists.AbstractListWrapper(var0)).listIterator(var1);
   }

   public static <E> ArrayList<E> newArrayList() {
      return new ArrayList();
   }

   public static <E> ArrayList<E> newArrayList(Iterable<? extends E> var0) {
      Preconditions.checkNotNull(var0);
      ArrayList var1;
      if (var0 instanceof Collection) {
         var1 = new ArrayList(Collections2.cast(var0));
      } else {
         var1 = newArrayList(var0.iterator());
      }

      return var1;
   }

   public static <E> ArrayList<E> newArrayList(Iterator<? extends E> var0) {
      ArrayList var1 = newArrayList();
      Iterators.addAll(var1, var0);
      return var1;
   }

   @SafeVarargs
   public static <E> ArrayList<E> newArrayList(E... var0) {
      Preconditions.checkNotNull(var0);
      ArrayList var1 = new ArrayList(computeArrayListCapacity(var0.length));
      Collections.addAll(var1, var0);
      return var1;
   }

   public static <E> ArrayList<E> newArrayListWithCapacity(int var0) {
      CollectPreconditions.checkNonnegative(var0, "initialArraySize");
      return new ArrayList(var0);
   }

   public static <E> ArrayList<E> newArrayListWithExpectedSize(int var0) {
      return new ArrayList(computeArrayListCapacity(var0));
   }

   public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
      return new CopyOnWriteArrayList();
   }

   public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> var0) {
      Object var1;
      if (var0 instanceof Collection) {
         var1 = Collections2.cast(var0);
      } else {
         var1 = newArrayList(var0);
      }

      return new CopyOnWriteArrayList((Collection)var1);
   }

   public static <E> LinkedList<E> newLinkedList() {
      return new LinkedList();
   }

   public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> var0) {
      LinkedList var1 = newLinkedList();
      Iterables.addAll(var1, var0);
      return var1;
   }

   public static <T> List<List<T>> partition(List<T> var0, int var1) {
      Preconditions.checkNotNull(var0);
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      Object var3;
      if (var0 instanceof RandomAccess) {
         var3 = new Lists.RandomAccessPartition(var0, var1);
      } else {
         var3 = new Lists.Partition(var0, var1);
      }

      return (List)var3;
   }

   public static <T> List<T> reverse(List<T> var0) {
      if (var0 instanceof ImmutableList) {
         return ((ImmutableList)var0).reverse();
      } else if (var0 instanceof Lists.ReverseList) {
         return ((Lists.ReverseList)var0).getForwardList();
      } else {
         return (List)(var0 instanceof RandomAccess ? new Lists.RandomAccessReverseList(var0) : new Lists.ReverseList(var0));
      }
   }

   static <E> List<E> subListImpl(List<E> var0, int var1, int var2) {
      Object var3;
      if (var0 instanceof RandomAccess) {
         var3 = new Lists.RandomAccessListWrapper<E>(var0) {
            private static final long serialVersionUID = 0L;

            public ListIterator<E> listIterator(int var1) {
               return this.backingList.listIterator(var1);
            }
         };
      } else {
         var3 = new Lists.AbstractListWrapper<E>(var0) {
            private static final long serialVersionUID = 0L;

            public ListIterator<E> listIterator(int var1) {
               return this.backingList.listIterator(var1);
            }
         };
      }

      return ((List)var3).subList(var1, var2);
   }

   public static <F, T> List<T> transform(List<F> var0, Function<? super F, ? extends T> var1) {
      Object var2;
      if (var0 instanceof RandomAccess) {
         var2 = new Lists.TransformingRandomAccessList(var0, var1);
      } else {
         var2 = new Lists.TransformingSequentialList(var0, var1);
      }

      return (List)var2;
   }

   private static class AbstractListWrapper<E> extends AbstractList<E> {
      final List<E> backingList;

      AbstractListWrapper(List<E> var1) {
         this.backingList = (List)Preconditions.checkNotNull(var1);
      }

      public void add(int var1, E var2) {
         this.backingList.add(var1, var2);
      }

      public boolean addAll(int var1, Collection<? extends E> var2) {
         return this.backingList.addAll(var1, var2);
      }

      public boolean contains(Object var1) {
         return this.backingList.contains(var1);
      }

      public E get(int var1) {
         return this.backingList.get(var1);
      }

      public E remove(int var1) {
         return this.backingList.remove(var1);
      }

      public E set(int var1, E var2) {
         return this.backingList.set(var1, var2);
      }

      public int size() {
         return this.backingList.size();
      }
   }

   private static final class CharSequenceAsList extends AbstractList<Character> {
      private final CharSequence sequence;

      CharSequenceAsList(CharSequence var1) {
         this.sequence = var1;
      }

      public Character get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.sequence.charAt(var1);
      }

      public int size() {
         return this.sequence.length();
      }
   }

   private static class OnePlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
      private static final long serialVersionUID = 0L;
      @NullableDecl
      final E first;
      final E[] rest;

      OnePlusArrayList(@NullableDecl E var1, E[] var2) {
         this.first = var1;
         this.rest = (Object[])Preconditions.checkNotNull(var2);
      }

      public E get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         Object var2;
         if (var1 == 0) {
            var2 = this.first;
         } else {
            var2 = this.rest[var1 - 1];
         }

         return var2;
      }

      public int size() {
         return IntMath.saturatedAdd(this.rest.length, 1);
      }
   }

   private static class Partition<T> extends AbstractList<List<T>> {
      final List<T> list;
      final int size;

      Partition(List<T> var1, int var2) {
         this.list = var1;
         this.size = var2;
      }

      public List<T> get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         int var2 = this.size;
         var1 *= var2;
         var2 = Math.min(var2 + var1, this.list.size());
         return this.list.subList(var1, var2);
      }

      public boolean isEmpty() {
         return this.list.isEmpty();
      }

      public int size() {
         return IntMath.divide(this.list.size(), this.size, RoundingMode.CEILING);
      }
   }

   private static class RandomAccessListWrapper<E> extends Lists.AbstractListWrapper<E> implements RandomAccess {
      RandomAccessListWrapper(List<E> var1) {
         super(var1);
      }
   }

   private static class RandomAccessPartition<T> extends Lists.Partition<T> implements RandomAccess {
      RandomAccessPartition(List<T> var1, int var2) {
         super(var1, var2);
      }
   }

   private static class RandomAccessReverseList<T> extends Lists.ReverseList<T> implements RandomAccess {
      RandomAccessReverseList(List<T> var1) {
         super(var1);
      }
   }

   private static class ReverseList<T> extends AbstractList<T> {
      private final List<T> forwardList;

      ReverseList(List<T> var1) {
         this.forwardList = (List)Preconditions.checkNotNull(var1);
      }

      private int reverseIndex(int var1) {
         int var2 = this.size();
         Preconditions.checkElementIndex(var1, var2);
         return var2 - 1 - var1;
      }

      private int reversePosition(int var1) {
         int var2 = this.size();
         Preconditions.checkPositionIndex(var1, var2);
         return var2 - var1;
      }

      public void add(int var1, @NullableDecl T var2) {
         this.forwardList.add(this.reversePosition(var1), var2);
      }

      public void clear() {
         this.forwardList.clear();
      }

      public T get(int var1) {
         return this.forwardList.get(this.reverseIndex(var1));
      }

      List<T> getForwardList() {
         return this.forwardList;
      }

      public Iterator<T> iterator() {
         return this.listIterator();
      }

      public ListIterator<T> listIterator(int var1) {
         var1 = this.reversePosition(var1);
         return new ListIterator<T>(this.forwardList.listIterator(var1)) {
            boolean canRemoveOrSet;
            // $FF: synthetic field
            final ListIterator val$forwardIterator;

            {
               this.val$forwardIterator = var2;
            }

            public void add(T var1) {
               this.val$forwardIterator.add(var1);
               this.val$forwardIterator.previous();
               this.canRemoveOrSet = false;
            }

            public boolean hasNext() {
               return this.val$forwardIterator.hasPrevious();
            }

            public boolean hasPrevious() {
               return this.val$forwardIterator.hasNext();
            }

            public T next() {
               if (this.hasNext()) {
                  this.canRemoveOrSet = true;
                  return this.val$forwardIterator.previous();
               } else {
                  throw new NoSuchElementException();
               }
            }

            public int nextIndex() {
               return ReverseList.this.reversePosition(this.val$forwardIterator.nextIndex());
            }

            public T previous() {
               if (this.hasPrevious()) {
                  this.canRemoveOrSet = true;
                  return this.val$forwardIterator.next();
               } else {
                  throw new NoSuchElementException();
               }
            }

            public int previousIndex() {
               return this.nextIndex() - 1;
            }

            public void remove() {
               CollectPreconditions.checkRemove(this.canRemoveOrSet);
               this.val$forwardIterator.remove();
               this.canRemoveOrSet = false;
            }

            public void set(T var1) {
               Preconditions.checkState(this.canRemoveOrSet);
               this.val$forwardIterator.set(var1);
            }
         };
      }

      public T remove(int var1) {
         return this.forwardList.remove(this.reverseIndex(var1));
      }

      protected void removeRange(int var1, int var2) {
         this.subList(var1, var2).clear();
      }

      public T set(int var1, @NullableDecl T var2) {
         return this.forwardList.set(this.reverseIndex(var1), var2);
      }

      public int size() {
         return this.forwardList.size();
      }

      public List<T> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         return Lists.reverse(this.forwardList.subList(this.reversePosition(var2), this.reversePosition(var1)));
      }
   }

   private static final class StringAsImmutableList extends ImmutableList<Character> {
      private final String string;

      StringAsImmutableList(String var1) {
         this.string = var1;
      }

      public Character get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.string.charAt(var1);
      }

      public int indexOf(@NullableDecl Object var1) {
         int var2;
         if (var1 instanceof Character) {
            var2 = this.string.indexOf((Character)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      boolean isPartialView() {
         return false;
      }

      public int lastIndexOf(@NullableDecl Object var1) {
         int var2;
         if (var1 instanceof Character) {
            var2 = this.string.lastIndexOf((Character)var1);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public int size() {
         return this.string.length();
      }

      public ImmutableList<Character> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         return Lists.charactersOf(this.string.substring(var1, var2));
      }
   }

   private static class TransformingRandomAccessList<F, T> extends AbstractList<T> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final List<F> fromList;
      final Function<? super F, ? extends T> function;

      TransformingRandomAccessList(List<F> var1, Function<? super F, ? extends T> var2) {
         this.fromList = (List)Preconditions.checkNotNull(var1);
         this.function = (Function)Preconditions.checkNotNull(var2);
      }

      public void clear() {
         this.fromList.clear();
      }

      public T get(int var1) {
         return this.function.apply(this.fromList.get(var1));
      }

      public boolean isEmpty() {
         return this.fromList.isEmpty();
      }

      public Iterator<T> iterator() {
         return this.listIterator();
      }

      public ListIterator<T> listIterator(int var1) {
         return new TransformedListIterator<F, T>(this.fromList.listIterator(var1)) {
            T transform(F var1) {
               return TransformingRandomAccessList.this.function.apply(var1);
            }
         };
      }

      public T remove(int var1) {
         return this.function.apply(this.fromList.remove(var1));
      }

      public int size() {
         return this.fromList.size();
      }
   }

   private static class TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable {
      private static final long serialVersionUID = 0L;
      final List<F> fromList;
      final Function<? super F, ? extends T> function;

      TransformingSequentialList(List<F> var1, Function<? super F, ? extends T> var2) {
         this.fromList = (List)Preconditions.checkNotNull(var1);
         this.function = (Function)Preconditions.checkNotNull(var2);
      }

      public void clear() {
         this.fromList.clear();
      }

      public ListIterator<T> listIterator(int var1) {
         return new TransformedListIterator<F, T>(this.fromList.listIterator(var1)) {
            T transform(F var1) {
               return TransformingSequentialList.this.function.apply(var1);
            }
         };
      }

      public int size() {
         return this.fromList.size();
      }
   }

   private static class TwoPlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
      private static final long serialVersionUID = 0L;
      @NullableDecl
      final E first;
      final E[] rest;
      @NullableDecl
      final E second;

      TwoPlusArrayList(@NullableDecl E var1, @NullableDecl E var2, E[] var3) {
         this.first = var1;
         this.second = var2;
         this.rest = (Object[])Preconditions.checkNotNull(var3);
      }

      public E get(int var1) {
         if (var1 != 0) {
            if (var1 != 1) {
               Preconditions.checkElementIndex(var1, this.size());
               return this.rest[var1 - 2];
            } else {
               return this.second;
            }
         } else {
            return this.first;
         }
      }

      public int size() {
         return IntMath.saturatedAdd(this.rest.length, 2);
      }
   }
}
