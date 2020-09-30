package com.google.common.collect;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class EmptyContiguousSet<C extends Comparable> extends ContiguousSet<C> {
   EmptyContiguousSet(DiscreteDomain<C> var1) {
      super(var1);
   }

   public ImmutableList<C> asList() {
      return ImmutableList.of();
   }

   public boolean contains(Object var1) {
      return false;
   }

   ImmutableSortedSet<C> createDescendingSet() {
      return ImmutableSortedSet.emptySet(Ordering.natural().reverse());
   }

   public UnmodifiableIterator<C> descendingIterator() {
      return Iterators.emptyIterator();
   }

   public boolean equals(@NullableDecl Object var1) {
      return var1 instanceof Set ? ((Set)var1).isEmpty() : false;
   }

   public C first() {
      throw new NoSuchElementException();
   }

   public int hashCode() {
      return 0;
   }

   ContiguousSet<C> headSetImpl(C var1, boolean var2) {
      return this;
   }

   int indexOf(Object var1) {
      return -1;
   }

   public ContiguousSet<C> intersection(ContiguousSet<C> var1) {
      return this;
   }

   public boolean isEmpty() {
      return true;
   }

   boolean isHashCodeFast() {
      return true;
   }

   boolean isPartialView() {
      return false;
   }

   public UnmodifiableIterator<C> iterator() {
      return Iterators.emptyIterator();
   }

   public C last() {
      throw new NoSuchElementException();
   }

   public Range<C> range() {
      throw new NoSuchElementException();
   }

   public Range<C> range(BoundType var1, BoundType var2) {
      throw new NoSuchElementException();
   }

   public int size() {
      return 0;
   }

   ContiguousSet<C> subSetImpl(C var1, boolean var2, C var3, boolean var4) {
      return this;
   }

   ContiguousSet<C> tailSetImpl(C var1, boolean var2) {
      return this;
   }

   public String toString() {
      return "[]";
   }

   Object writeReplace() {
      return new EmptyContiguousSet.SerializedForm(this.domain);
   }

   private static final class SerializedForm<C extends Comparable> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final DiscreteDomain<C> domain;

      private SerializedForm(DiscreteDomain<C> var1) {
         this.domain = var1;
      }

      // $FF: synthetic method
      SerializedForm(DiscreteDomain var1, Object var2) {
         this(var1);
      }

      private Object readResolve() {
         return new EmptyContiguousSet(this.domain);
      }
   }
}
