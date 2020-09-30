package com.google.common.collect;

import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMultiset<E> extends AbstractCollection<E> implements Multiset<E> {
   @LazyInit
   @MonotonicNonNullDecl
   private transient Set<E> elementSet;
   @LazyInit
   @MonotonicNonNullDecl
   private transient Set<Multiset.Entry<E>> entrySet;

   public int add(@NullableDecl E var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public final boolean add(@NullableDecl E var1) {
      this.add(var1, 1);
      return true;
   }

   public final boolean addAll(Collection<? extends E> var1) {
      return Multisets.addAllImpl(this, (Collection)var1);
   }

   public abstract void clear();

   public boolean contains(@NullableDecl Object var1) {
      boolean var2;
      if (this.count(var1) > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   Set<E> createElementSet() {
      return new AbstractMultiset.ElementSet();
   }

   Set<Multiset.Entry<E>> createEntrySet() {
      return new AbstractMultiset.EntrySet();
   }

   abstract int distinctElements();

   abstract Iterator<E> elementIterator();

   public Set<E> elementSet() {
      Set var1 = this.elementSet;
      Set var2 = var1;
      if (var1 == null) {
         var2 = this.createElementSet();
         this.elementSet = var2;
      }

      return var2;
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

   public final boolean equals(@NullableDecl Object var1) {
      return Multisets.equalsImpl(this, var1);
   }

   public final int hashCode() {
      return this.entrySet().hashCode();
   }

   public boolean isEmpty() {
      return this.entrySet().isEmpty();
   }

   public int remove(@NullableDecl Object var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public final boolean remove(@NullableDecl Object var1) {
      boolean var2 = true;
      if (this.remove(var1, 1) <= 0) {
         var2 = false;
      }

      return var2;
   }

   public final boolean removeAll(Collection<?> var1) {
      return Multisets.removeAllImpl(this, var1);
   }

   public final boolean retainAll(Collection<?> var1) {
      return Multisets.retainAllImpl(this, var1);
   }

   public int setCount(@NullableDecl E var1, int var2) {
      return Multisets.setCountImpl(this, var1, var2);
   }

   public boolean setCount(@NullableDecl E var1, int var2, int var3) {
      return Multisets.setCountImpl(this, var1, var2, var3);
   }

   public final String toString() {
      return this.entrySet().toString();
   }

   class ElementSet extends Multisets.ElementSet<E> {
      public Iterator<E> iterator() {
         return AbstractMultiset.this.elementIterator();
      }

      Multiset<E> multiset() {
         return AbstractMultiset.this;
      }
   }

   class EntrySet extends Multisets.EntrySet<E> {
      public Iterator<Multiset.Entry<E>> iterator() {
         return AbstractMultiset.this.entryIterator();
      }

      Multiset<E> multiset() {
         return AbstractMultiset.this;
      }

      public int size() {
         return AbstractMultiset.this.distinctElements();
      }
   }
}
