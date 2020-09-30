package com.google.android.gms.internal.drive;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzle extends zziw<Long> implements zzkp<Long>, zzmc, RandomAccess {
   private static final zzle zztp;
   private int size;
   private long[] zztq;

   static {
      zzle var0 = new zzle(new long[0], 0);
      zztp = var0;
      var0.zzbp();
   }

   zzle() {
      this(new long[10], 0);
   }

   private zzle(long[] var1, int var2) {
      this.zztq = var1;
      this.size = var2;
   }

   private final void zzk(int var1, long var2) {
      this.zzbq();
      if (var1 >= 0) {
         int var4 = this.size;
         if (var1 <= var4) {
            long[] var5 = this.zztq;
            if (var4 < var5.length) {
               System.arraycopy(var5, var1, var5, var1 + 1, var4 - var1);
            } else {
               long[] var6 = new long[var4 * 3 / 2 + 1];
               System.arraycopy(var5, 0, var6, 0, var1);
               System.arraycopy(this.zztq, var1, var6, var1 + 1, this.size - var1);
               this.zztq = var6;
            }

            this.zztq[var1] = var2;
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
      this.zzk(var1, (Long)var2);
   }

   public final boolean addAll(Collection<? extends Long> var1) {
      this.zzbq();
      zzkm.checkNotNull(var1);
      if (!(var1 instanceof zzle)) {
         return super.addAll(var1);
      } else {
         zzle var2 = (zzle)var1;
         int var3 = var2.size;
         if (var3 == 0) {
            return false;
         } else {
            int var4 = this.size;
            if (Integer.MAX_VALUE - var4 >= var3) {
               var4 += var3;
               long[] var5 = this.zztq;
               if (var4 > var5.length) {
                  this.zztq = Arrays.copyOf(var5, var4);
               }

               System.arraycopy(var2.zztq, 0, this.zztq, this.size, var2.size);
               this.size = var4;
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
      } else if (!(var1 instanceof zzle)) {
         return super.equals(var1);
      } else {
         zzle var3 = (zzle)var1;
         if (this.size != var3.size) {
            return false;
         } else {
            long[] var4 = var3.zztq;

            for(int var2 = 0; var2 < this.size; ++var2) {
               if (this.zztq[var2] != var4[var2]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   // $FF: synthetic method
   public final Object get(int var1) {
      return this.getLong(var1);
   }

   public final long getLong(int var1) {
      this.zzp(var1);
      return this.zztq[var1];
   }

   public final int hashCode() {
      int var1 = 1;

      for(int var2 = 0; var2 < this.size; ++var2) {
         var1 = var1 * 31 + zzkm.zzu(this.zztq[var2]);
      }

      return var1;
   }

   // $FF: synthetic method
   public final Object remove(int var1) {
      this.zzbq();
      this.zzp(var1);
      long[] var2 = this.zztq;
      long var3 = var2[var1];
      int var5 = this.size;
      if (var1 < var5 - 1) {
         System.arraycopy(var2, var1 + 1, var2, var1, var5 - var1 - 1);
      }

      --this.size;
      ++this.modCount;
      return var3;
   }

   public final boolean remove(Object var1) {
      this.zzbq();

      for(int var2 = 0; var2 < this.size; ++var2) {
         if (var1.equals(this.zztq[var2])) {
            long[] var3 = this.zztq;
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
         long[] var3 = this.zztq;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         ++this.modCount;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   // $FF: synthetic method
   public final Object set(int var1, Object var2) {
      long var3 = (Long)var2;
      this.zzbq();
      this.zzp(var1);
      long[] var7 = this.zztq;
      long var5 = var7[var1];
      var7[var1] = var3;
      return var5;
   }

   public final int size() {
      return this.size;
   }

   // $FF: synthetic method
   public final zzkp zzr(int var1) {
      if (var1 >= this.size) {
         return new zzle(Arrays.copyOf(this.zztq, var1), this.size);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final void zzv(long var1) {
      this.zzk(this.size, var1);
   }
}
