package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingCollection<E> extends ForwardingObject implements Collection<E> {
   protected ForwardingCollection() {
   }

   public boolean add(E var1) {
      return this.delegate().add(var1);
   }

   public boolean addAll(Collection<? extends E> var1) {
      return this.delegate().addAll(var1);
   }

   public void clear() {
      this.delegate().clear();
   }

   public boolean contains(Object var1) {
      return this.delegate().contains(var1);
   }

   public boolean containsAll(Collection<?> var1) {
      return this.delegate().containsAll(var1);
   }

   protected abstract Collection<E> delegate();

   public boolean isEmpty() {
      return this.delegate().isEmpty();
   }

   public Iterator<E> iterator() {
      return this.delegate().iterator();
   }

   public boolean remove(Object var1) {
      return this.delegate().remove(var1);
   }

   public boolean removeAll(Collection<?> var1) {
      return this.delegate().removeAll(var1);
   }

   public boolean retainAll(Collection<?> var1) {
      return this.delegate().retainAll(var1);
   }

   public int size() {
      return this.delegate().size();
   }

   protected boolean standardAddAll(Collection<? extends E> var1) {
      return Iterators.addAll(this, var1.iterator());
   }

   protected void standardClear() {
      Iterators.clear(this.iterator());
   }

   protected boolean standardContains(@NullableDecl Object var1) {
      return Iterators.contains(this.iterator(), var1);
   }

   protected boolean standardContainsAll(Collection<?> var1) {
      return Collections2.containsAllImpl(this, var1);
   }

   protected boolean standardIsEmpty() {
      return this.iterator().hasNext() ^ true;
   }

   protected boolean standardRemove(@NullableDecl Object var1) {
      Iterator var2 = this.iterator();

      do {
         if (!var2.hasNext()) {
            return false;
         }
      } while(!Objects.equal(var2.next(), var1));

      var2.remove();
      return true;
   }

   protected boolean standardRemoveAll(Collection<?> var1) {
      return Iterators.removeAll(this.iterator(), var1);
   }

   protected boolean standardRetainAll(Collection<?> var1) {
      return Iterators.retainAll(this.iterator(), var1);
   }

   protected Object[] standardToArray() {
      return this.toArray(new Object[this.size()]);
   }

   protected <T> T[] standardToArray(T[] var1) {
      return ObjectArrays.toArrayImpl(this, var1);
   }

   protected String standardToString() {
      return Collections2.toStringImpl(this);
   }

   public Object[] toArray() {
      return this.delegate().toArray();
   }

   public <T> T[] toArray(T[] var1) {
      return this.delegate().toArray(var1);
   }
}
