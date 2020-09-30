package com.google.common.collect;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

public abstract class ForwardingNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E> {
   protected ForwardingNavigableSet() {
   }

   public E ceiling(E var1) {
      return this.delegate().ceiling(var1);
   }

   protected abstract NavigableSet<E> delegate();

   public Iterator<E> descendingIterator() {
      return this.delegate().descendingIterator();
   }

   public NavigableSet<E> descendingSet() {
      return this.delegate().descendingSet();
   }

   public E floor(E var1) {
      return this.delegate().floor(var1);
   }

   public NavigableSet<E> headSet(E var1, boolean var2) {
      return this.delegate().headSet(var1, var2);
   }

   public E higher(E var1) {
      return this.delegate().higher(var1);
   }

   public E lower(E var1) {
      return this.delegate().lower(var1);
   }

   public E pollFirst() {
      return this.delegate().pollFirst();
   }

   public E pollLast() {
      return this.delegate().pollLast();
   }

   protected E standardCeiling(E var1) {
      return Iterators.getNext(this.tailSet(var1, true).iterator(), (Object)null);
   }

   protected E standardFirst() {
      return this.iterator().next();
   }

   protected E standardFloor(E var1) {
      return Iterators.getNext(this.headSet(var1, true).descendingIterator(), (Object)null);
   }

   protected SortedSet<E> standardHeadSet(E var1) {
      return this.headSet(var1, false);
   }

   protected E standardHigher(E var1) {
      return Iterators.getNext(this.tailSet(var1, false).iterator(), (Object)null);
   }

   protected E standardLast() {
      return this.descendingIterator().next();
   }

   protected E standardLower(E var1) {
      return Iterators.getNext(this.headSet(var1, false).descendingIterator(), (Object)null);
   }

   protected E standardPollFirst() {
      return Iterators.pollNext(this.iterator());
   }

   protected E standardPollLast() {
      return Iterators.pollNext(this.descendingIterator());
   }

   protected NavigableSet<E> standardSubSet(E var1, boolean var2, E var3, boolean var4) {
      return this.tailSet(var1, var2).headSet(var3, var4);
   }

   protected SortedSet<E> standardSubSet(E var1, E var2) {
      return this.subSet(var1, true, var2, false);
   }

   protected SortedSet<E> standardTailSet(E var1) {
      return this.tailSet(var1, true);
   }

   public NavigableSet<E> subSet(E var1, boolean var2, E var3, boolean var4) {
      return this.delegate().subSet(var1, var2, var3, var4);
   }

   public NavigableSet<E> tailSet(E var1, boolean var2) {
      return this.delegate().tailSet(var1, var2);
   }

   protected class StandardDescendingSet extends Sets.DescendingSet<E> {
      public StandardDescendingSet() {
         super(ForwardingNavigableSet.this);
      }
   }
}
