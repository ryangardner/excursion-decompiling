package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E> {
   protected ForwardingMultiset() {
   }

   public int add(E var1, int var2) {
      return this.delegate().add(var1, var2);
   }

   public int count(Object var1) {
      return this.delegate().count(var1);
   }

   protected abstract Multiset<E> delegate();

   public Set<E> elementSet() {
      return this.delegate().elementSet();
   }

   public Set<Multiset.Entry<E>> entrySet() {
      return this.delegate().entrySet();
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public int remove(Object var1, int var2) {
      return this.delegate().remove(var1, var2);
   }

   public int setCount(E var1, int var2) {
      return this.delegate().setCount(var1, var2);
   }

   public boolean setCount(E var1, int var2, int var3) {
      return this.delegate().setCount(var1, var2, var3);
   }

   protected boolean standardAdd(E var1) {
      this.add(var1, 1);
      return true;
   }

   protected boolean standardAddAll(Collection<? extends E> var1) {
      return Multisets.addAllImpl(this, (Collection)var1);
   }

   protected void standardClear() {
      Iterators.clear(this.entrySet().iterator());
   }

   protected boolean standardContains(@NullableDecl Object var1) {
      boolean var2;
      if (this.count(var1) > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected int standardCount(@NullableDecl Object var1) {
      Iterator var2 = this.entrySet().iterator();

      Multiset.Entry var3;
      do {
         if (!var2.hasNext()) {
            return 0;
         }

         var3 = (Multiset.Entry)var2.next();
      } while(!Objects.equal(var3.getElement(), var1));

      return var3.getCount();
   }

   protected boolean standardEquals(@NullableDecl Object var1) {
      return Multisets.equalsImpl(this, var1);
   }

   protected int standardHashCode() {
      return this.entrySet().hashCode();
   }

   protected Iterator<E> standardIterator() {
      return Multisets.iteratorImpl(this);
   }

   protected boolean standardRemove(Object var1) {
      boolean var2 = true;
      if (this.remove(var1, 1) <= 0) {
         var2 = false;
      }

      return var2;
   }

   protected boolean standardRemoveAll(Collection<?> var1) {
      return Multisets.removeAllImpl(this, var1);
   }

   protected boolean standardRetainAll(Collection<?> var1) {
      return Multisets.retainAllImpl(this, var1);
   }

   protected int standardSetCount(E var1, int var2) {
      return Multisets.setCountImpl(this, var1, var2);
   }

   protected boolean standardSetCount(E var1, int var2, int var3) {
      return Multisets.setCountImpl(this, var1, var2, var3);
   }

   protected int standardSize() {
      return Multisets.linearTimeSizeImpl(this);
   }

   protected String standardToString() {
      return this.entrySet().toString();
   }

   protected class StandardElementSet extends Multisets.ElementSet<E> {
      public StandardElementSet() {
      }

      public Iterator<E> iterator() {
         return Multisets.elementIterator(this.multiset().entrySet().iterator());
      }

      Multiset<E> multiset() {
         return ForwardingMultiset.this;
      }
   }
}
