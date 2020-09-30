package com.google.common.collect;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class DescendingImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
   private final transient ImmutableSortedMultiset<E> forward;

   DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> var1) {
      this.forward = var1;
   }

   public int count(@NullableDecl Object var1) {
      return this.forward.count(var1);
   }

   public ImmutableSortedMultiset<E> descendingMultiset() {
      return this.forward;
   }

   public ImmutableSortedSet<E> elementSet() {
      return this.forward.elementSet().descendingSet();
   }

   public Multiset.Entry<E> firstEntry() {
      return this.forward.lastEntry();
   }

   Multiset.Entry<E> getEntry(int var1) {
      return (Multiset.Entry)this.forward.entrySet().asList().reverse().get(var1);
   }

   public ImmutableSortedMultiset<E> headMultiset(E var1, BoundType var2) {
      return this.forward.tailMultiset(var1, var2).descendingMultiset();
   }

   boolean isPartialView() {
      return this.forward.isPartialView();
   }

   public Multiset.Entry<E> lastEntry() {
      return this.forward.firstEntry();
   }

   public int size() {
      return this.forward.size();
   }

   public ImmutableSortedMultiset<E> tailMultiset(E var1, BoundType var2) {
      return this.forward.headMultiset(var1, var2).descendingMultiset();
   }
}
