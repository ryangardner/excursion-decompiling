package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class MinMaxPriorityQueue<E> extends AbstractQueue<E> {
   private static final int DEFAULT_CAPACITY = 11;
   private static final int EVEN_POWERS_OF_TWO = 1431655765;
   private static final int ODD_POWERS_OF_TWO = -1431655766;
   private final MinMaxPriorityQueue<E>.Heap maxHeap;
   final int maximumSize;
   private final MinMaxPriorityQueue<E>.Heap minHeap;
   private int modCount;
   private Object[] queue;
   private int size;

   private MinMaxPriorityQueue(MinMaxPriorityQueue.Builder<? super E> var1, int var2) {
      Ordering var3 = var1.ordering();
      this.minHeap = new MinMaxPriorityQueue.Heap(var3);
      MinMaxPriorityQueue.Heap var4 = new MinMaxPriorityQueue.Heap(var3.reverse());
      this.maxHeap = var4;
      this.minHeap.otherHeap = var4;
      this.maxHeap.otherHeap = this.minHeap;
      this.maximumSize = var1.maximumSize;
      this.queue = new Object[var2];
   }

   // $FF: synthetic method
   MinMaxPriorityQueue(MinMaxPriorityQueue.Builder var1, int var2, Object var3) {
      this(var1, var2);
   }

   private int calculateNewCapacity() {
      int var1 = this.queue.length;
      if (var1 < 64) {
         var1 = (var1 + 1) * 2;
      } else {
         var1 = IntMath.checkedMultiply(var1 / 2, 3);
      }

      return capAtMaximumSize(var1, this.maximumSize);
   }

   private static int capAtMaximumSize(int var0, int var1) {
      return Math.min(var0 - 1, var1) + 1;
   }

   public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
      return (new MinMaxPriorityQueue.Builder(Ordering.natural())).create();
   }

   public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> var0) {
      return (new MinMaxPriorityQueue.Builder(Ordering.natural())).create(var0);
   }

   public static MinMaxPriorityQueue.Builder<Comparable> expectedSize(int var0) {
      return (new MinMaxPriorityQueue.Builder(Ordering.natural())).expectedSize(var0);
   }

   private MinMaxPriorityQueue.MoveDesc<E> fillHole(int var1, E var2) {
      MinMaxPriorityQueue.Heap var3 = this.heapForIndex(var1);
      int var4 = var3.fillHoleAt(var1);
      int var5 = var3.bubbleUpAlternatingLevels(var4, var2);
      if (var5 == var4) {
         return var3.tryCrossOverAndBubbleUp(var1, var4, var2);
      } else {
         MinMaxPriorityQueue.MoveDesc var6;
         if (var5 < var1) {
            var6 = new MinMaxPriorityQueue.MoveDesc(var2, this.elementData(var1));
         } else {
            var6 = null;
         }

         return var6;
      }
   }

   private int getMaxElementIndex() {
      int var1 = this.size;
      byte var2 = 1;
      if (var1 != 1) {
         byte var3 = var2;
         if (var1 != 2) {
            if (this.maxHeap.compareElements(1, 2) <= 0) {
               var3 = var2;
            } else {
               var3 = 2;
            }
         }

         return var3;
      } else {
         return 0;
      }
   }

   private void growIfNeeded() {
      if (this.size > this.queue.length) {
         Object[] var1 = new Object[this.calculateNewCapacity()];
         Object[] var2 = this.queue;
         System.arraycopy(var2, 0, var1, 0, var2.length);
         this.queue = var1;
      }

   }

   private MinMaxPriorityQueue<E>.Heap heapForIndex(int var1) {
      MinMaxPriorityQueue.Heap var2;
      if (isEvenLevel(var1)) {
         var2 = this.minHeap;
      } else {
         var2 = this.maxHeap;
      }

      return var2;
   }

   static int initialQueueSize(int var0, int var1, Iterable<?> var2) {
      int var3 = var0;
      if (var0 == -1) {
         var3 = 11;
      }

      var0 = var3;
      if (var2 instanceof Collection) {
         var0 = Math.max(var3, ((Collection)var2).size());
      }

      return capAtMaximumSize(var0, var1);
   }

   static boolean isEvenLevel(int var0) {
      boolean var1 = true;
      ++var0;
      boolean var2;
      if (var0 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "negative index");
      if ((1431655765 & var0) > (var0 & -1431655766)) {
         var2 = var1;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static MinMaxPriorityQueue.Builder<Comparable> maximumSize(int var0) {
      return (new MinMaxPriorityQueue.Builder(Ordering.natural())).maximumSize(var0);
   }

   public static <B> MinMaxPriorityQueue.Builder<B> orderedBy(Comparator<B> var0) {
      return new MinMaxPriorityQueue.Builder(var0);
   }

   private E removeAndGet(int var1) {
      Object var2 = this.elementData(var1);
      this.removeAt(var1);
      return var2;
   }

   public boolean add(E var1) {
      this.offer(var1);
      return true;
   }

   public boolean addAll(Collection<? extends E> var1) {
      Iterator var3 = var1.iterator();

      boolean var2;
      for(var2 = false; var3.hasNext(); var2 = true) {
         this.offer(var3.next());
      }

      return var2;
   }

   int capacity() {
      return this.queue.length;
   }

   public void clear() {
      for(int var1 = 0; var1 < this.size; ++var1) {
         this.queue[var1] = null;
      }

      this.size = 0;
   }

   public Comparator<? super E> comparator() {
      return this.minHeap.ordering;
   }

   E elementData(int var1) {
      return this.queue[var1];
   }

   boolean isIntact() {
      for(int var1 = 1; var1 < this.size; ++var1) {
         if (!this.heapForIndex(var1).verifyIndex(var1)) {
            return false;
         }
      }

      return true;
   }

   public Iterator<E> iterator() {
      return new MinMaxPriorityQueue.QueueIterator();
   }

   public boolean offer(E var1) {
      Preconditions.checkNotNull(var1);
      int var2 = this.modCount;
      boolean var3 = true;
      this.modCount = var2 + 1;
      var2 = this.size++;
      this.growIfNeeded();
      this.heapForIndex(var2).bubbleUp(var2, var1);
      boolean var4 = var3;
      if (this.size > this.maximumSize) {
         if (this.pollLast() != var1) {
            var4 = var3;
         } else {
            var4 = false;
         }
      }

      return var4;
   }

   public E peek() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.elementData(0);
      }

      return var1;
   }

   public E peekFirst() {
      return this.peek();
   }

   public E peekLast() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.elementData(this.getMaxElementIndex());
      }

      return var1;
   }

   public E poll() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.removeAndGet(0);
      }

      return var1;
   }

   public E pollFirst() {
      return this.poll();
   }

   public E pollLast() {
      Object var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = this.removeAndGet(this.getMaxElementIndex());
      }

      return var1;
   }

   MinMaxPriorityQueue.MoveDesc<E> removeAt(int var1) {
      Preconditions.checkPositionIndex(var1, this.size);
      ++this.modCount;
      int var2 = this.size - 1;
      this.size = var2;
      if (var2 == var1) {
         this.queue[var2] = null;
         return null;
      } else {
         Object var3 = this.elementData(var2);
         var2 = this.heapForIndex(this.size).swapWithConceptuallyLastElement(var3);
         if (var2 == var1) {
            this.queue[this.size] = null;
            return null;
         } else {
            Object var4 = this.elementData(this.size);
            this.queue[this.size] = null;
            MinMaxPriorityQueue.MoveDesc var5 = this.fillHole(var1, var4);
            if (var2 < var1) {
               return var5 == null ? new MinMaxPriorityQueue.MoveDesc(var3, var4) : new MinMaxPriorityQueue.MoveDesc(var3, var5.replaced);
            } else {
               return var5;
            }
         }
      }
   }

   public E removeFirst() {
      return this.remove();
   }

   public E removeLast() {
      if (!this.isEmpty()) {
         return this.removeAndGet(this.getMaxElementIndex());
      } else {
         throw new NoSuchElementException();
      }
   }

   public int size() {
      return this.size;
   }

   public Object[] toArray() {
      int var1 = this.size;
      Object[] var2 = new Object[var1];
      System.arraycopy(this.queue, 0, var2, 0, var1);
      return var2;
   }

   public static final class Builder<B> {
      private static final int UNSET_EXPECTED_SIZE = -1;
      private final Comparator<B> comparator;
      private int expectedSize;
      private int maximumSize;

      private Builder(Comparator<B> var1) {
         this.expectedSize = -1;
         this.maximumSize = Integer.MAX_VALUE;
         this.comparator = (Comparator)Preconditions.checkNotNull(var1);
      }

      // $FF: synthetic method
      Builder(Comparator var1, Object var2) {
         this(var1);
      }

      private <T extends B> Ordering<T> ordering() {
         return Ordering.from(this.comparator);
      }

      public <T extends B> MinMaxPriorityQueue<T> create() {
         return this.create(Collections.emptySet());
      }

      public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> var1) {
         MinMaxPriorityQueue var2 = new MinMaxPriorityQueue(this, MinMaxPriorityQueue.initialQueueSize(this.expectedSize, this.maximumSize, var1));
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            var2.offer(var3.next());
         }

         return var2;
      }

      public MinMaxPriorityQueue.Builder<B> expectedSize(int var1) {
         boolean var2;
         if (var1 >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2);
         this.expectedSize = var1;
         return this;
      }

      public MinMaxPriorityQueue.Builder<B> maximumSize(int var1) {
         boolean var2;
         if (var1 > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2);
         this.maximumSize = var1;
         return this;
      }
   }

   private class Heap {
      final Ordering<E> ordering;
      @MonotonicNonNullDecl
      MinMaxPriorityQueue<E>.Heap otherHeap;

      Heap(Ordering<E> var2) {
         this.ordering = var2;
      }

      private int getGrandparentIndex(int var1) {
         return this.getParentIndex(this.getParentIndex(var1));
      }

      private int getLeftChildIndex(int var1) {
         return var1 * 2 + 1;
      }

      private int getParentIndex(int var1) {
         return (var1 - 1) / 2;
      }

      private int getRightChildIndex(int var1) {
         return var1 * 2 + 2;
      }

      private boolean verifyIndex(int var1) {
         if (this.getLeftChildIndex(var1) < MinMaxPriorityQueue.this.size && this.compareElements(var1, this.getLeftChildIndex(var1)) > 0) {
            return false;
         } else if (this.getRightChildIndex(var1) < MinMaxPriorityQueue.this.size && this.compareElements(var1, this.getRightChildIndex(var1)) > 0) {
            return false;
         } else if (var1 > 0 && this.compareElements(var1, this.getParentIndex(var1)) > 0) {
            return false;
         } else {
            return var1 <= 2 || this.compareElements(this.getGrandparentIndex(var1), var1) <= 0;
         }
      }

      void bubbleUp(int var1, E var2) {
         int var3 = this.crossOverUp(var1, var2);
         MinMaxPriorityQueue.Heap var4;
         if (var3 == var1) {
            var4 = this;
         } else {
            var4 = this.otherHeap;
            var1 = var3;
         }

         var4.bubbleUpAlternatingLevels(var1, var2);
      }

      int bubbleUpAlternatingLevels(int var1, E var2) {
         while(true) {
            if (var1 > 2) {
               int var3 = this.getGrandparentIndex(var1);
               Object var4 = MinMaxPriorityQueue.this.elementData(var3);
               if (this.ordering.compare(var4, var2) > 0) {
                  MinMaxPriorityQueue.this.queue[var1] = var4;
                  var1 = var3;
                  continue;
               }
            }

            MinMaxPriorityQueue.this.queue[var1] = var2;
            return var1;
         }
      }

      int compareElements(int var1, int var2) {
         return this.ordering.compare(MinMaxPriorityQueue.this.elementData(var1), MinMaxPriorityQueue.this.elementData(var2));
      }

      int crossOver(int var1, E var2) {
         int var3 = this.findMinChild(var1);
         if (var3 > 0 && this.ordering.compare(MinMaxPriorityQueue.this.elementData(var3), var2) < 0) {
            MinMaxPriorityQueue.this.queue[var1] = MinMaxPriorityQueue.this.elementData(var3);
            MinMaxPriorityQueue.this.queue[var3] = var2;
            return var3;
         } else {
            return this.crossOverUp(var1, var2);
         }
      }

      int crossOverUp(int var1, E var2) {
         if (var1 == 0) {
            MinMaxPriorityQueue.this.queue[0] = var2;
            return 0;
         } else {
            int var3 = this.getParentIndex(var1);
            Object var4 = MinMaxPriorityQueue.this.elementData(var3);
            int var5 = var3;
            Object var6 = var4;
            if (var3 != 0) {
               int var7 = this.getRightChildIndex(this.getParentIndex(var3));
               var5 = var3;
               var6 = var4;
               if (var7 != var3) {
                  var5 = var3;
                  var6 = var4;
                  if (this.getLeftChildIndex(var7) >= MinMaxPriorityQueue.this.size) {
                     Object var8 = MinMaxPriorityQueue.this.elementData(var7);
                     var5 = var3;
                     var6 = var4;
                     if (this.ordering.compare(var8, var4) < 0) {
                        var5 = var7;
                        var6 = var8;
                     }
                  }
               }
            }

            if (this.ordering.compare(var6, var2) < 0) {
               MinMaxPriorityQueue.this.queue[var1] = var6;
               MinMaxPriorityQueue.this.queue[var5] = var2;
               return var5;
            } else {
               MinMaxPriorityQueue.this.queue[var1] = var2;
               return var1;
            }
         }
      }

      int fillHoleAt(int var1) {
         while(true) {
            int var2 = this.findMinGrandChild(var1);
            if (var2 <= 0) {
               return var1;
            }

            MinMaxPriorityQueue.this.queue[var1] = MinMaxPriorityQueue.this.elementData(var2);
            var1 = var2;
         }
      }

      int findMin(int var1, int var2) {
         if (var1 >= MinMaxPriorityQueue.this.size) {
            return -1;
         } else {
            boolean var3;
            if (var1 > 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            Preconditions.checkState(var3);
            int var4 = Math.min(var1, MinMaxPriorityQueue.this.size - var2);
            int var5 = var1 + 1;

            int var6;
            for(var6 = var1; var5 < var4 + var2; var6 = var1) {
               var1 = var6;
               if (this.compareElements(var5, var6) < 0) {
                  var1 = var5;
               }

               ++var5;
            }

            return var6;
         }
      }

      int findMinChild(int var1) {
         return this.findMin(this.getLeftChildIndex(var1), 2);
      }

      int findMinGrandChild(int var1) {
         var1 = this.getLeftChildIndex(var1);
         return var1 < 0 ? -1 : this.findMin(this.getLeftChildIndex(var1), 4);
      }

      int swapWithConceptuallyLastElement(E var1) {
         int var2 = this.getParentIndex(MinMaxPriorityQueue.this.size);
         if (var2 != 0) {
            int var3 = this.getRightChildIndex(this.getParentIndex(var2));
            if (var3 != var2 && this.getLeftChildIndex(var3) >= MinMaxPriorityQueue.this.size) {
               Object var4 = MinMaxPriorityQueue.this.elementData(var3);
               if (this.ordering.compare(var4, var1) < 0) {
                  MinMaxPriorityQueue.this.queue[var3] = var1;
                  MinMaxPriorityQueue.this.queue[MinMaxPriorityQueue.this.size] = var4;
                  return var3;
               }
            }
         }

         return MinMaxPriorityQueue.this.size;
      }

      MinMaxPriorityQueue.MoveDesc<E> tryCrossOverAndBubbleUp(int var1, int var2, E var3) {
         int var4 = this.crossOver(var2, var3);
         if (var4 == var2) {
            return null;
         } else {
            Object var5;
            if (var4 < var1) {
               var5 = MinMaxPriorityQueue.this.elementData(var1);
            } else {
               var5 = MinMaxPriorityQueue.this.elementData(this.getParentIndex(var1));
            }

            return this.otherHeap.bubbleUpAlternatingLevels(var4, var3) < var1 ? new MinMaxPriorityQueue.MoveDesc(var3, var5) : null;
         }
      }
   }

   static class MoveDesc<E> {
      final E replaced;
      final E toTrickle;

      MoveDesc(E var1, E var2) {
         this.toTrickle = var1;
         this.replaced = var2;
      }
   }

   private class QueueIterator implements Iterator<E> {
      private boolean canRemove;
      private int cursor;
      private int expectedModCount;
      @MonotonicNonNullDecl
      private Queue<E> forgetMeNot;
      @NullableDecl
      private E lastFromForgetMeNot;
      private int nextCursor;
      @MonotonicNonNullDecl
      private List<E> skipMe;

      private QueueIterator() {
         this.cursor = -1;
         this.nextCursor = -1;
         this.expectedModCount = MinMaxPriorityQueue.this.modCount;
      }

      // $FF: synthetic method
      QueueIterator(Object var2) {
         this();
      }

      private void checkModCount() {
         if (MinMaxPriorityQueue.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      private boolean foundAndRemovedExactReference(Iterable<E> var1, E var2) {
         Iterator var3 = var1.iterator();

         do {
            if (!var3.hasNext()) {
               return false;
            }
         } while(var3.next() != var2);

         var3.remove();
         return true;
      }

      private void nextNotInSkipMe(int var1) {
         if (this.nextCursor < var1) {
            int var2 = var1;
            if (this.skipMe != null) {
               while(true) {
                  var2 = var1;
                  if (var1 >= MinMaxPriorityQueue.this.size()) {
                     break;
                  }

                  var2 = var1;
                  if (!this.foundAndRemovedExactReference(this.skipMe, MinMaxPriorityQueue.this.elementData(var1))) {
                     break;
                  }

                  ++var1;
               }
            }

            this.nextCursor = var2;
         }

      }

      private boolean removeExact(Object var1) {
         for(int var2 = 0; var2 < MinMaxPriorityQueue.this.size; ++var2) {
            if (MinMaxPriorityQueue.this.queue[var2] == var1) {
               MinMaxPriorityQueue.this.removeAt(var2);
               return true;
            }
         }

         return false;
      }

      public boolean hasNext() {
         this.checkModCount();
         int var1 = this.cursor;
         boolean var2 = true;
         this.nextNotInSkipMe(var1 + 1);
         boolean var3 = var2;
         if (this.nextCursor >= MinMaxPriorityQueue.this.size()) {
            Queue var4 = this.forgetMeNot;
            if (var4 != null && !var4.isEmpty()) {
               var3 = var2;
            } else {
               var3 = false;
            }
         }

         return var3;
      }

      public E next() {
         this.checkModCount();
         this.nextNotInSkipMe(this.cursor + 1);
         if (this.nextCursor < MinMaxPriorityQueue.this.size()) {
            int var1 = this.nextCursor;
            this.cursor = var1;
            this.canRemove = true;
            return MinMaxPriorityQueue.this.elementData(var1);
         } else {
            if (this.forgetMeNot != null) {
               this.cursor = MinMaxPriorityQueue.this.size();
               Object var2 = this.forgetMeNot.poll();
               this.lastFromForgetMeNot = var2;
               if (var2 != null) {
                  this.canRemove = true;
                  return var2;
               }
            }

            throw new NoSuchElementException("iterator moved past last element in queue.");
         }
      }

      public void remove() {
         CollectPreconditions.checkRemove(this.canRemove);
         this.checkModCount();
         this.canRemove = false;
         ++this.expectedModCount;
         if (this.cursor < MinMaxPriorityQueue.this.size()) {
            MinMaxPriorityQueue.MoveDesc var1 = MinMaxPriorityQueue.this.removeAt(this.cursor);
            if (var1 != null) {
               if (this.forgetMeNot == null) {
                  this.forgetMeNot = new ArrayDeque();
                  this.skipMe = new ArrayList(3);
               }

               if (!this.foundAndRemovedExactReference(this.skipMe, var1.toTrickle)) {
                  this.forgetMeNot.add(var1.toTrickle);
               }

               if (!this.foundAndRemovedExactReference(this.forgetMeNot, var1.replaced)) {
                  this.skipMe.add(var1.replaced);
               }
            }

            --this.cursor;
            --this.nextCursor;
         } else {
            Preconditions.checkState(this.removeExact(this.lastFromForgetMeNot));
            this.lastFromForgetMeNot = null;
         }

      }
   }
}
