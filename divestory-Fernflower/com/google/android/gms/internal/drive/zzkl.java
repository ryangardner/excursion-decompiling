package com.google.android.gms.internal.drive;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzkl extends zziw<Integer> implements zzkp<Integer>, zzmc, RandomAccess {
   private static final zzkl zzsl;
   private int size;
   private int[] zzsm;

   static {
      zzkl var0 = new zzkl(new int[0], 0);
      zzsl = var0;
      var0.zzbp();
   }

   zzkl() {
      this(new int[10], 0);
   }

   private zzkl(int[] var1, int var2) {
      this.zzsm = var1;
      this.size = var2;
   }

   private final void zzo(int var1, int var2) {
      this.zzbq();
      if (var1 >= 0) {
         int var3 = this.size;
         if (var1 <= var3) {
            int[] var4 = this.zzsm;
            if (var3 < var4.length) {
               System.arraycopy(var4, var1, var4, var1 + 1, var3 - var1);
            } else {
               int[] var5 = new int[var3 * 3 / 2 + 1];
               System.arraycopy(var4, 0, var5, 0, var1);
               System.arraycopy(this.zzsm, var1, var5, var1 + 1, this.size - var1);
               this.zzsm = var5;
            }

            this.zzsm[var1] = var2;
            ++this.size;
            ++this.modCount;
            return;
         }
      }

      throw new IndexOutOfBoundsException(this.zzq(var1));
   }

   private final void zzp(int var1) {
      if (var1 < 0 || var1 >= this.size) {
         throw new IndexOutOfBoundsException(this.zzq(var1));
      }
   }

   private final String zzq(int var1) {
      int var2 = this.size;
      StringBuilder var3 = new StringBuilder(35);
      var3.append("Index:");
      var3.append(var1);
      var3.append(", Size:");
      var3.append(var2);
      return var3.toString();
   }

   // $FF: synthetic method
   public final void add(int var1, Object var2) {
      this.zzo(var1, (Integer)var2);
   }

   public final boolean addAll(Collection<? extends Integer> var1) {
      this.zzbq();
      zzkm.checkNotNull(var1);
      if (!(var1 instanceof zzkl)) {
         return super.addAll(var1);
      } else {
         zzkl var5 = (zzkl)var1;
         int var2 = var5.size;
         if (var2 == 0) {
            return false;
         } else {
            int var3 = this.size;
            if (Integer.MAX_VALUE - var3 >= var2) {
               var2 += var3;
               int[] var4 = this.zzsm;
               if (var2 > var4.length) {
                  this.zzsm = Arrays.copyOf(var4, var2);
               }

               System.arraycopy(var5.zzsm, 0, this.zzsm, this.size, var5.size);
               this.size = var2;
               ++this.modCount;
               return true;
            } else {
               throw new OutOfMemoryError();
            }
         }
      }
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zzkl)) {
         return super.equals(var1);
      } else {
         zzkl var3 = (zzkl)var1;
         if (this.size != var3.size) {
            return false;
         } else {
            int[] var4 = var3.zzsm;

            for(int var2 = 0; var2 < this.size; ++var2) {
               if (this.zzsm[var2] != var4[var2]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   // $FF: synthetic method
   public final Object get(int var1) {
      return this.getInt(var1);
   }

   public final int getInt(int var1) {
      this.zzp(var1);
      return this.zzsm[var1];
   }

   public final int hashCode() {
      int var1 = 1;

      for(int var2 = 0; var2 < this.size; ++var2) {
         var1 = var1 * 31 + this.zzsm[var2];
      }

      return var1;
   }

   // $FF: synthetic method
   public final Object remove(int var1) {
      this.zzbq();
      this.zzp(var1);
      int[] var2 = this.zzsm;
      int var3 = var2[var1];
      int var4 = this.size;
      if (var1 < var4 - 1) {
         System.arraycopy(var2, var1 + 1, var2, var1, var4 - var1 - 1);
      }

      --this.size;
      ++this.modCount;
      return var3;
   }

   public final boolean remove(Object var1) {
      this.zzbq();

      for(int var2 = 0; var2 < this.size; ++var2) {
         if (var1.equals(this.zzsm[var2])) {
            int[] var3 = this.zzsm;
            System.arraycopy(var3, var2 + 1, var3, var2, this.size - var2 - 1);
            --this.size;
            ++this.modCount;
            return true;
         }
      }

      return false;
   }

   protected final void removeRange(int var1, int var2) {
      this.zzbq();
      if (var2 >= var1) {
         int[] var3 = this.zzsm;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         ++this.modCount;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   // $FF: synthetic method
   public final Object set(int var1, Object var2) {
      int var3 = (Integer)var2;
      this.zzbq();
      this.zzp(var1);
      int[] var5 = this.zzsm;
      int var4 = var5[var1];
      var5[var1] = var3;
      return var4;
   }

   public final int size() {
      return this.size;
   }

   public final void zzam(int var1) {
      this.zzo(this.size, var1);
   }

   // $FF: synthetic method
   public final zzkp zzr(int var1) {
      if (var1 >= this.size) {
         return new zzkl(Arrays.copyOf(this.zzsm, var1), this.size);
      } else {
         throw new IllegalArgumentException();
      }
   }
}
