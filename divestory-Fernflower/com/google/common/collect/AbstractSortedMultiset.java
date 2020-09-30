package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {
   @GwtTransient
   final Comparator<? super E> comparator;
   @MonotonicNonNullDecl
   private transient SortedMultiset<E> descendingMultiset;

   AbstractSortedMultiset() {
      this(Ordering.natural());
   }

   AbstractSortedMultiset(Comparator<? super E> var1) {
      this.comparator = (Comparator)Preconditions.checkNotNull(var1);
   }

   public Comparator<? super E> comparator() {
      return this.comparator;
   }

   SortedMultiset<E> createDescendingMultiset() {
      return new AbstractSortedMultiset$1DescendingMultisetImpl(this);
   }

   NavigableSet<E> createElementSet() {
      return new SortedMultisets.NavigableElementSet(this);
   }

   abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();

   Iterator<E> descendingIterator() {
      return Multisets.iteratorImpl(this.descendingMultiset());
   }

   public SortedMultiset<E> descendingMultiset() {
      SortedMultiset var1 = this.descendingMultiset;
      SortedMultiset var2 = var1;
      if (var1 == null) {
         var2 = this.createDescendingMultiset();
         this.descendingMultiset = var2;
      }

      return var2;
   }

   public NavigableSet<E> elementSet() {
      return (NavigableSet)super.elementSet();
   }

   public Multiset.Entry<E> firstEntry() {
      Iterator var1 = this.entryIterator();
      Multiset.Entry var2;
      if (var1.hasNext()) {
         var2 = (Multiset.Entry)var1.next();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Multiset.Entry<E> lastEntry() {
      Iterator var1 = this.descendingEntryIterator();
      Multiset.Entry var2;
      if (var1.hasNext()) {
         var2 = (Multiset.Entry)var1.next();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Multiset.Entry<E> pollFirstEntry() {
      Iterator var1 = this.entryIterator();
      if (var1.hasNext()) {
         Multiset.Entry var2 = (Multiset.Entry)var1.next();
         var2 = Multisets.immutableEntry(var2.getElement(), var2.getCount());
         var1.remove();
         return var2;
      } else {
         return null;
      }
   }

   public Multiset.Entry<E> pollLastEntry() {
      Iterator var1 = this.descendingEntryIterator();
      if (var1.hasNext()) {
         Multiset.Entry var2 = (Multiset.Entry)var1.next();
         var2 = Multisets.immutableEntry(var2.getElement(), var2.getCount());
         var1.remove();
         return var2;
      } else {
         return null;
      }
   }

   public SortedMultiset<E> subMultiset(@NullableDecl E var1, BoundType var2, @NullableDecl E var3, BoundType var4) {
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var4);
      return this.tailMultiset(var1, var2).headMultiset(var3, var4);
   }
}
