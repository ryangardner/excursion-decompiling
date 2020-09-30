package com.google.common.collect;

import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E> {
   protected ForwardingQueue() {
   }

   protected abstract Queue<E> delegate();

   public E element() {
      return this.delegate().element();
   }

   public boolean offer(E var1) {
      return this.delegate().offer(var1);
   }

   public E peek() {
      return this.delegate().peek();
   }

   public E poll() {
      return this.delegate().poll();
   }

   public E remove() {
      return this.delegate().remove();
   }

   protected boolean standardOffer(E var1) {
      try {
         boolean var2 = this.add(var1);
         return var2;
      } catch (IllegalStateException var3) {
         return false;
      }
   }

   protected E standardPeek() {
      try {
         Object var1 = this.element();
         return var1;
      } catch (NoSuchElementException var2) {
         return null;
      }
   }

   protected E standardPoll() {
      try {
         Object var1 = this.remove();
         return var1;
      } catch (NoSuchElementException var2) {
         return null;
      }
   }
}
