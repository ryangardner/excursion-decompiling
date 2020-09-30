package com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingSortedSet<E> extends ForwardingSet<E> implements SortedSet<E> {
   protected ForwardingSortedSet() {
   }

   private int unsafeCompare(@NullableDecl Object var1, @NullableDecl Object var2) {
      Comparator var3 = this.comparator();
      int var4;
      if (var3 == null) {
         var4 = ((Comparable)var1).compareTo(var2);
      } else {
         var4 = var3.compare(var1, var2);
      }

      return var4;
   }

   public Comparator<? super E> comparator() {
      return this.delegate().comparator();
   }

   protected abstract SortedSet<E> delegate();

   public E first() {
      return this.delegate().first();
   }

   public SortedSet<E> headSet(E var1) {
      return this.delegate().headSet(var1);
   }

   public E last() {
      return this.delegate().last();
   }

   protected boolean standardContains(@NullableDecl Object var1) {
      boolean var2 = false;

      int var3;
      try {
         var3 = this.unsafeCompare(this.tailSet(var1).first(), var1);
      } catch (NoSuchElementException | NullPointerException | ClassCastException var4) {
         return var2;
      }

      if (var3 == 0) {
         var2 = true;
      }

      return var2;
   }

   protected boolean standardRemove(@NullableDecl Object var1) {
      try {
         Iterator var2 = this.tailSet(var1).iterator();
         if (var2.hasNext() && this.unsafeCompare(var2.next(), var1) == 0) {
            var2.remove();
            return true;
         }
      } catch (NullPointerException | ClassCastException var3) {
      }

      return false;
   }

   protected SortedSet<E> standardSubSet(E var1, E var2) {
      return this.tailSet(var1).headSet(var2);
   }

   public SortedSet<E> subSet(E var1, E var2) {
      return this.delegate().subSet(var1, var2);
   }

   public SortedSet<E> tailSet(E var1) {
      return this.delegate().tailSet(var1);
   }
}
