package com.google.android.gms.internal.drive;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzja extends zziw<Boolean> implements zzkp<Boolean>, zzmc, RandomAccess {
   private static final zzja zzno;
   private int size;
   private boolean[] zznp;

   static {
      zzja var0 = new zzja(new boolean[0], 0);
      zzno = var0;
      var0.zzbp();
   }

   zzja() {
      this(new boolean[10], 0);
   }

   private zzja(boolean[] var1, int var2) {
      this.zznp = var1;
      this.size = var2;
   }

   private final void zza(int var1, boolean var2) {
      this.zzbq();
      if (var1 >= 0) {
         int var3 = this.size;
         if (var1 <= var3) {
            boolean[] var4 = this.zznp;
            if (var3 < var4.length) {
               System.arraycopy(var4, var1, var4, var1 + 1, var3 - var1);
            } else {
               boolean[] var5 = new boolean[var3 * 3 / 2 + 1];
               System.arraycopy(var4, 0, var5, 0, var1);
               System.arraycopy(this.zznp, var1, var5, var1 + 1, this.size - var1);
               this.zznp = var5;
            }

            this.zznp[var1] = var2;
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
      this.zza(var1, (Boolean)var2);
   }

   public final boolean addAll(Collection<? extends Boolean> var1) {
      this.zzbq();
      zzkm.checkNotNull(var1);
      if (!(var1 instanceof zzja)) {
         return super.addAll(var1);
      } else {
         zzja var2 = (zzja)var1;
         int var3 = var2.size;
         if (var3 == 0) {
            return false;
         } else {
            int var4 = this.size;
            if (Integer.MAX_VALUE - var4 >= var3) {
               var3 += var4;
               boolean[] var5 = this.zznp;
               if (var3 > var5.length) {
                  this.zznp = Arrays.copyOf(var5, var3);
               }

               System.arraycopy(var2.zznp, 0, this.zznp, this.size, var2.size);
               this.size = var3;
               ++this.modCount;
               return true;
            } else {
               throw new OutOfMemoryError();
            }
         }
      }
   }

   public final void addBoolean(boolean var1) {
      this.zza(this.size, var1);
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zzja)) {
         return super.equals(var1);
      } else {
         zzja var3 = (zzja)var1;
         if (this.size != var3.size) {
            return false;
         } else {
            boolean[] var4 = var3.zznp;

            for(int var2 = 0; var2 < this.size; ++var2) {
               if (this.zznp[var2] != var4[var2]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   // $FF: synthetic method
   public final Object get(int var1) {
      this.zzp(var1);
      return this.zznp[var1];
   }

   public final int hashCode() {
      int var1 = 1;

      for(int var2 = 0; var2 < this.size; ++var2) {
         var1 = var1 * 31 + zzkm.zze(this.zznp[var2]);
      }

      return var1;
   }

   // $FF: synthetic method
   public final Object remove(int var1) {
      this.zzbq();
      this.zzp(var1);
      boolean[] var2 = this.zznp;
      boolean var3 = var2[var1];
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
         if (var1.equals(this.zznp[var2])) {
            boolean[] var3 = this.zznp;
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
         boolean[] var3 = this.zznp;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         ++this.modCount;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   // $FF: synthetic method
   public final Object set(int var1, Object var2) {
      boolean var3 = (Boolean)var2;
      this.zzbq();
      this.zzp(var1);
      boolean[] var5 = this.zznp;
      boolean var4 = var5[var1];
      var5[var1] = var3;
      return var4;
   }

   public final int size() {
      return this.size;
   }

   // $FF: synthetic method
   public final zzkp zzr(int var1) {
      if (var1 >= this.size) {
         return new zzja(Arrays.copyOf(this.zznp, var1), this.size);
      } else {
         throw new IllegalArgumentException();
      }
   }
}
