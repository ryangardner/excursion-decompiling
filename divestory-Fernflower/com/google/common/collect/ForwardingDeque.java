package com.google.common.collect;

import java.util.Deque;
import java.util.Iterator;

public abstract class ForwardingDeque<E> extends ForwardingQueue<E> implements Deque<E> {
   protected ForwardingDeque() {
   }

   public void addFirst(E var1) {
      this.delegate().addFirst(var1);
   }

   public void addLast(E var1) {
      this.delegate().addLast(var1);
   }

   protected abstract Deque<E> delegate();

   public Iterator<E> descendingIterator() {
      return this.delegate().descendingIterator();
   }

   public E getFirst() {
      return this.delegate().getFirst();
   }

   public E getLast() {
      return this.delegate().getLast();
   }

   public boolean offerFirst(E var1) {
      return this.delegate().offerFirst(var1);
   }

   public boolean offerLast(E var1) {
      return this.delegate().offerLast(var1);
   }

   public E peekFirst() {
      return this.delegate().peekFirst();
   }

   public E peekLast() {
      return this.delegate().peekLast();
   }

   public E pollFirst() {
      return this.delegate().pollFirst();
   }

   public E pollLast() {
      return this.delegate().pollLast();
   }

   public E pop() {
      return this.delegate().pop();
   }

   public void push(E var1) {
      this.delegate().push(var1);
   }

   public E removeFirst() {
      return this.delegate().removeFirst();
   }

   public boolean removeFirstOccurrence(Object var1) {
      return this.delegate().removeFirstOccurrence(var1);
   }

   public E removeLast() {
      return this.delegate().removeLast();
   }

   public boolean removeLastOccurrence(Object var1) {
      return this.delegate().removeLastOccurrence(var1);
   }
}
