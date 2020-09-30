package com.google.common.collect;

import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
   static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset(ObjectCountHashMap.create());
   final transient ObjectCountHashMap<E> contents;
   @LazyInit
   private transient ImmutableSet<E> elementSet;
   private final transient int size;

   RegularImmutableMultiset(ObjectCountHashMap<E> var1) {
      this.contents = var1;
      long var2 = 0L;

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         var2 += (long)var1.getValue(var4);
      }

      this.size = Ints.saturatedCast(var2);
   }

   public int count(@NullableDecl Object var1) {
      return this.contents.get(var1);
   }

   public ImmutableSet<E> elementSet() {
      ImmutableSet var1 = this.elementSet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new RegularImmutableMultiset.ElementSet();
         this.elementSet = (ImmutableSet)var2;
      }

      return (ImmutableSet)var2;
   }

   Multiset.Entry<E> getEntry(int var1) {
      return this.contents.getEntry(var1);
   }

   boolean isPartialView() {
      return false;
   }

   public int size() {
      return this.size;
   }

   Object writeReplace() {
      return new RegularImmutableMultiset.SerializedForm(this);
   }

   private final class ElementSet extends IndexedImmutableSet<E> {
      private ElementSet() {
      }

      // $FF: synthetic method
      ElementSet(Object var2) {
         this();
      }

      public boolean contains(@NullableDecl Object var1) {
         return RegularImmutableMultiset.this.contains(var1);
      }

      E get(int var1) {
         return RegularImmutableMultiset.this.contents.getKey(var1);
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return RegularImmutableMultiset.this.contents.size();
      }
   }

   private static class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      final int[] counts;
      final Object[] elements;

      SerializedForm(Multiset<?> var1) {
         int var2 = var1.entrySet().size();
         this.elements = new Object[var2];
         this.counts = new int[var2];
         Iterator var3 = var1.entrySet().iterator();

         for(var2 = 0; var3.hasNext(); ++var2) {
            Multiset.Entry var4 = (Multiset.Entry)var3.next();
            this.elements[var2] = var4.getElement();
            this.counts[var2] = var4.getCount();
         }

      }

      Object readResolve() {
         ImmutableMultiset.Builder var1 = new ImmutableMultiset.Builder(this.elements.length);
         int var2 = 0;

         while(true) {
            Object[] var3 = this.elements;
            if (var2 >= var3.length) {
               return var1.build();
            }

            var1.addCopies(var3[var2], this.counts[var2]);
            ++var2;
         }
      }
   }
}
