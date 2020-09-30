package com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

abstract class DescendingMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
   @MonotonicNonNullDecl
   private transient Comparator<? super E> comparator;
   @MonotonicNonNullDecl
   private transient NavigableSet<E> elementSet;
   @MonotonicNonNullDecl
   private transient Set<Multiset.Entry<E>> entrySet;

   public Comparator<? super E> comparator() {
      Comparator var1 = this.comparator;
      Object var2 = var1;
      if (var1 == null) {
         var2 = Ordering.from(this.forwardMultiset().comparator()).reverse();
         this.comparator = (Comparator)var2;
      }

      return (Comparator)var2;
   }

   Set<Multiset.Entry<E>> createEntrySet() {
      return new DescendingMultiset$1EntrySetImpl(this);
   }

   protected Multiset<E> delegate() {
      return this.forwardMultiset();
   }

   public SortedMultiset<E> descendingMultiset() {
      return this.forwardMultiset();
   }

   public NavigableSet<E> elementSet() {
      NavigableSet var1 = this.elementSet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new SortedMultisets.NavigableElementSet(this);
         this.elementSet = (NavigableSet)var2;
      }

      return (NavigableSet)var2;
   }

   abstract Iterator<Multiset.Entry<E>> entryIterator();

   public Set<Multiset.Entry<E>> entrySet() {
      Set var1 = this.entrySet;
      Set var2 = var1;
      if (var1 == null) {
         var2 = this.createEntrySet();
         this.entrySet = var2;
      }

      return var2;
   }

   public Multiset.Entry<E> firstEntry() {
      return this.forwardMultiset().lastEntry();
   }

   abstract SortedMultiset<E> forwardMultiset();

   public SortedMultiset<E> headMultiset(E var1, BoundType var2) {
      return this.forwardMultiset().tailMultiset(var1, var2).descendingMultiset();
   }

   public Iterator<E> iterator() {
      return Multisets.iteratorImpl(this);
   }

   public Multiset.Entry<E> lastEntry() {
      return this.forwardMultiset().firstEntry();
   }

   public Multiset.Entry<E> pollFirstEntry() {
      return this.forwardMultiset().pollLastEntry();
   }

   public Multiset.Entry<E> pollLastEntry() {
      return this.forwardMultiset().pollFirstEntry();
   }

   public SortedMultiset<E> subMultiset(E var1, BoundType var2, E var3, BoundType var4) {
      return this.forwardMultiset().subMultiset(var3, var4, var1, var2).descendingMultiset();
   }

   public SortedMultiset<E> tailMultiset(E var1, BoundType var2) {
      return this.forwardMultiset().headMultiset(var1, var2).descendingMultiset();
   }

   public Object[] toArray() {
      return this.standardToArray();
   }

   public <T> T[] toArray(T[] var1) {
      return this.standardToArray(var1);
   }

   public String toString() {
      return this.entrySet().toString();
   }
}
