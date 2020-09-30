package com.google.common.collect;

import java.util.ListIterator;

public abstract class ForwardingListIterator<E> extends ForwardingIterator<E> implements ListIterator<E> {
   protected ForwardingListIterator() {
   }

   public void add(E var1) {
      this.delegate().add(var1);
   }

   protected abstract ListIterator<E> delegate();

   public boolean hasPrevious() {
      return this.delegate().hasPrevious();
   }

   public int nextIndex() {
      return this.delegate().nextIndex();
   }

   public E previous() {
      return this.delegate().previous();
   }

   public int previousIndex() {
      return this.delegate().previousIndex();
   }

   public void set(E var1) {
      this.delegate().set(var1);
   }
}
