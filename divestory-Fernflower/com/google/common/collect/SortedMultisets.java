package com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class SortedMultisets {
   private SortedMultisets() {
   }

   private static <E> E getElementOrNull(@NullableDecl Multiset.Entry<E> var0) {
      Object var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = var0.getElement();
      }

      return var1;
   }

   private static <E> E getElementOrThrow(Multiset.Entry<E> var0) {
      if (var0 != null) {
         return var0.getElement();
      } else {
         throw new NoSuchElementException();
      }
   }

   static class ElementSet<E> extends Multisets.ElementSet<E> implements SortedSet<E> {
      private final SortedMultiset<E> multiset;

      ElementSet(SortedMultiset<E> var1) {
         this.multiset = var1;
      }

      public Comparator<? super E> comparator() {
         return this.multiset().comparator();
      }

      public E first() {
         return SortedMultisets.getElementOrThrow(this.multiset().firstEntry());
      }

      public SortedSet<E> headSet(E var1) {
         return this.multiset().headMultiset(var1, BoundType.OPEN).elementSet();
      }

      public Iterator<E> iterator() {
         return Multisets.elementIterator(this.multiset().entrySet().iterator());
      }

      public E last() {
         return SortedMultisets.getElementOrThrow(this.multiset().lastEntry());
      }

      final SortedMultiset<E> multiset() {
         return this.multiset;
      }

      public SortedSet<E> subSet(E var1, E var2) {
         return this.multiset().subMultiset(var1, BoundType.CLOSED, var2, BoundType.OPEN).elementSet();
      }

      public SortedSet<E> tailSet(E var1) {
         return this.multiset().tailMultiset(var1, BoundType.CLOSED).elementSet();
      }
   }

   static class NavigableElementSet<E> extends SortedMultisets.ElementSet<E> implements NavigableSet<E> {
      NavigableElementSet(SortedMultiset<E> var1) {
         super(var1);
      }

      public E ceiling(E var1) {
         return SortedMultisets.getElementOrNull(this.multiset().tailMultiset(var1, BoundType.CLOSED).firstEntry());
      }

      public Iterator<E> descendingIterator() {
         return this.descendingSet().iterator();
      }

      public NavigableSet<E> descendingSet() {
         return new SortedMultisets.NavigableElementSet(this.multiset().descendingMultiset());
      }

      public E floor(E var1) {
         return SortedMultisets.getElementOrNull(this.multiset().headMultiset(var1, BoundType.CLOSED).lastEntry());
      }

      public NavigableSet<E> headSet(E var1, boolean var2) {
         return new SortedMultisets.NavigableElementSet(this.multiset().headMultiset(var1, BoundType.forBoolean(var2)));
      }

      public E higher(E var1) {
         return SortedMultisets.getElementOrNull(this.multiset().tailMultiset(var1, BoundType.OPEN).firstEntry());
      }

      public E lower(E var1) {
         return SortedMultisets.getElementOrNull(this.multiset().headMultiset(var1, BoundType.OPEN).lastEntry());
      }

      public E pollFirst() {
         return SortedMultisets.getElementOrNull(this.multiset().pollFirstEntry());
      }

      public E pollLast() {
         return SortedMultisets.getElementOrNull(this.multiset().pollLastEntry());
      }

      public NavigableSet<E> subSet(E var1, boolean var2, E var3, boolean var4) {
         return new SortedMultisets.NavigableElementSet(this.multiset().subMultiset(var1, BoundType.forBoolean(var2), var3, BoundType.forBoolean(var4)));
      }

      public NavigableSet<E> tailSet(E var1, boolean var2) {
         return new SortedMultisets.NavigableElementSet(this.multiset().tailMultiset(var1, BoundType.forBoolean(var2)));
      }
   }
}
