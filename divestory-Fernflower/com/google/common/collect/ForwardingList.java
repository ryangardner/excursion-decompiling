package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingList<E> extends ForwardingCollection<E> implements List<E> {
   protected ForwardingList() {
   }

   public void add(int var1, E var2) {
      this.delegate().add(var1, var2);
   }

   public boolean addAll(int var1, Collection<? extends E> var2) {
      return this.delegate().addAll(var1, var2);
   }

   protected abstract List<E> delegate();

   public boolean equals(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public E get(int var1) {
      return this.delegate().get(var1);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public int indexOf(Object var1) {
      return this.delegate().indexOf(var1);
   }

   public int lastIndexOf(Object var1) {
      return this.delegate().lastIndexOf(var1);
   }

   public ListIterator<E> listIterator() {
      return this.delegate().listIterator();
   }

   public ListIterator<E> listIterator(int var1) {
      return this.delegate().listIterator(var1);
   }

   public E remove(int var1) {
      return this.delegate().remove(var1);
   }

   public E set(int var1, E var2) {
      return this.delegate().set(var1, var2);
   }

   protected boolean standardAdd(E var1) {
      this.add(this.size(), var1);
      return true;
   }

   protected boolean standardAddAll(int var1, Iterable<? extends E> var2) {
      return Lists.addAllImpl(this, var1, var2);
   }

   protected boolean standardEquals(@NullableDecl Object var1) {
      return Lists.equalsImpl(this, var1);
   }

   protected int standardHashCode() {
      return Lists.hashCodeImpl(this);
   }

   protected int standardIndexOf(@NullableDecl Object var1) {
      return Lists.indexOfImpl(this, var1);
   }

   protected Iterator<E> standardIterator() {
      return this.listIterator();
   }

   protected int standardLastIndexOf(@NullableDecl Object var1) {
      return Lists.lastIndexOfImpl(this, var1);
   }

   protected ListIterator<E> standardListIterator() {
      return this.listIterator(0);
   }

   protected ListIterator<E> standardListIterator(int var1) {
      return Lists.listIteratorImpl(this, var1);
   }

   protected List<E> standardSubList(int var1, int var2) {
      return Lists.subListImpl(this, var1, var2);
   }

   public List<E> subList(int var1, int var2) {
      return this.delegate().subList(var1, var2);
   }
}
