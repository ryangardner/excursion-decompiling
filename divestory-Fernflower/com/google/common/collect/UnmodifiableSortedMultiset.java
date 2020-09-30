package com.google.common.collect;

import java.util.Comparator;
import java.util.NavigableSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

final class UnmodifiableSortedMultiset<E> extends Multisets.UnmodifiableMultiset<E> implements SortedMultiset<E> {
   private static final long serialVersionUID = 0L;
   @MonotonicNonNullDecl
   private transient UnmodifiableSortedMultiset<E> descendingMultiset;

   UnmodifiableSortedMultiset(SortedMultiset<E> var1) {
      super(var1);
   }

   public Comparator<? super E> comparator() {
      return this.delegate().comparator();
   }

   NavigableSet<E> createElementSet() {
      return Sets.unmodifiableNavigableSet(this.delegate().elementSet());
   }

   protected SortedMultiset<E> delegate() {
      return (SortedMultiset)super.delegate();
   }

   public SortedMultiset<E> descendingMultiset() {
      UnmodifiableSortedMultiset var1 = this.descendingMultiset;
      UnmodifiableSortedMultiset var2 = var1;
      if (var1 == null) {
         var2 = new UnmodifiableSortedMultiset(this.delegate().descendingMultiset());
         var2.descendingMultiset = this;
         this.descendingMultiset = var2;
      }

      return var2;
   }

   public NavigableSet<E> elementSet() {
      return (NavigableSet)super.elementSet();
   }

   public Multiset.Entry<E> firstEntry() {
      return this.delegate().firstEntry();
   }

   public SortedMultiset<E> headMultiset(E var1, BoundType var2) {
      return Multisets.unmodifiableSortedMultiset(this.delegate().headMultiset(var1, var2));
   }

   public Multiset.Entry<E> lastEntry() {
      return this.delegate().lastEntry();
   }

   public Multiset.Entry<E> pollFirstEntry() {
      throw new UnsupportedOperationException();
   }

   public Multiset.Entry<E> pollLastEntry() {
      throw new UnsupportedOperationException();
   }

   public SortedMultiset<E> subMultiset(E var1, BoundType var2, E var3, BoundType var4) {
      return Multisets.unmodifiableSortedMultiset(this.delegate().subMultiset(var1, var2, var3, var4));
   }

   public SortedMultiset<E> tailMultiset(E var1, BoundType var2) {
      return Multisets.unmodifiableSortedMultiset(this.delegate().tailMultiset(var1, var2));
   }
}
