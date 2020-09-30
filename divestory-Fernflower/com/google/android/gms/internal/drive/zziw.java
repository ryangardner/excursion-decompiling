package com.google.android.gms.internal.drive;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

abstract class zziw<E> extends AbstractList<E> implements zzkp<E> {
   private boolean zznh = true;

   public void add(int var1, E var2) {
      this.zzbq();
      super.add(var1, var2);
   }

   public boolean add(E var1) {
      this.zzbq();
      return super.add(var1);
   }

   public boolean addAll(int var1, Collection<? extends E> var2) {
      this.zzbq();
      return super.addAll(var1, var2);
   }

   public boolean addAll(Collection<? extends E> var1) {
      this.zzbq();
      return super.addAll(var1);
   }

   public void clear() {
      this.zzbq();
      super.clear();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof List)) {
         return false;
      } else if (!(var1 instanceof RandomAccess)) {
         return super.equals(var1);
      } else {
         List var4 = (List)var1;
         int var2 = this.size();
         if (var2 != var4.size()) {
            return false;
         } else {
            for(int var3 = 0; var3 < var2; ++var3) {
               if (!this.get(var3).equals(var4.get(var3))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int var1 = this.size();
      int var2 = 1;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2 = var2 * 31 + this.get(var3).hashCode();
      }

      return var2;
   }

   public E remove(int var1) {
      this.zzbq();
      return super.remove(var1);
   }

   public boolean remove(Object var1) {
      this.zzbq();
      return super.remove(var1);
   }

   public boolean removeAll(Collection<?> var1) {
      this.zzbq();
      return super.removeAll(var1);
   }

   public boolean retainAll(Collection<?> var1) {
      this.zzbq();
      return super.retainAll(var1);
   }

   public E set(int var1, E var2) {
      this.zzbq();
      return super.set(var1, var2);
   }

   public boolean zzbo() {
      return this.zznh;
   }

   public final void zzbp() {
      this.zznh = false;
   }

   protected final void zzbq() {
      if (!this.zznh) {
         throw new UnsupportedOperationException();
      }
   }
}
