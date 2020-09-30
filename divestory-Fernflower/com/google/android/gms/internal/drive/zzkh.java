package com.google.android.gms.internal.drive;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzkh extends zziw<Float> implements zzkp<Float>, zzmc, RandomAccess {
   private static final zzkh zzrm;
   private int size;
   private float[] zzrn;

   static {
      zzkh var0 = new zzkh(new float[0], 0);
      zzrm = var0;
      var0.zzbp();
   }

   zzkh() {
      this(new float[10], 0);
   }

   private zzkh(float[] var1, int var2) {
      this.zzrn = var1;
      this.size = var2;
   }

   private final void zzc(int var1, float var2) {
      this.zzbq();
      if (var1 >= 0) {
         int var3 = this.size;
         if (var1 <= var3) {
            float[] var4 = this.zzrn;
            if (var3 < var4.length) {
               System.arraycopy(var4, var1, var4, var1 + 1, var3 - var1);
            } else {
               float[] var5 = new float[var3 * 3 / 2 + 1];
               System.arraycopy(var4, 0, var5, 0, var1);
               System.arraycopy(this.zzrn, var1, var5, var1 + 1, this.size - var1);
               this.zzrn = var5;
            }

            this.zzrn[var1] = var2;
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
      this.zzc(var1, (Float)var2);
   }

   public final boolean addAll(Collection<? extends Float> var1) {
      this.zzbq();
      zzkm.checkNotNull(var1);
      if (!(var1 instanceof zzkh)) {
         return super.addAll(var1);
      } else {
         zzkh var2 = (zzkh)var1;
         int var3 = var2.size;
         if (var3 == 0) {
            return false;
         } else {
            int var4 = this.size;
            if (Integer.MAX_VALUE - var4 >= var3) {
               var4 += var3;
               float[] var5 = this.zzrn;
               if (var4 > var5.length) {
                  this.zzrn = Arrays.copyOf(var5, var4);
               }

               System.arraycopy(var2.zzrn, 0, this.zzrn, this.size, var2.size);
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
      } else if (!(var1 instanceof zzkh)) {
         return super.equals(var1);
      } else {
         zzkh var3 = (zzkh)var1;
         if (this.size != var3.size) {
            return false;
         } else {
            float[] var4 = var3.zzrn;

            for(int var2 = 0; var2 < this.size; ++var2) {
               if (Float.floatToIntBits(this.zzrn[var2]) != Float.floatToIntBits(var4[var2])) {
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
      return this.zzrn[var1];
   }

   public final int hashCode() {
      int var1 = 1;

      for(int var2 = 0; var2 < this.size; ++var2) {
         var1 = var1 * 31 + Float.floatToIntBits(this.zzrn[var2]);
      }

      return var1;
   }

   // $FF: synthetic method
   public final Object remove(int var1) {
      this.zzbq();
      this.zzp(var1);
      float[] var2 = this.zzrn;
      float var3 = var2[var1];
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
         if (var1.equals(this.zzrn[var2])) {
            float[] var3 = this.zzrn;
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
         float[] var3 = this.zzrn;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         ++this.modCount;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   // $FF: synthetic method
   public final Object set(int var1, Object var2) {
      float var3 = (Float)var2;
      this.zzbq();
      this.zzp(var1);
      float[] var5 = this.zzrn;
      float var4 = var5[var1];
      var5[var1] = var3;
      return var4;
   }

   public final int size() {
      return this.size;
   }

   public final void zzc(float var1) {
      this.zzc(this.size, var1);
   }

   // $FF: synthetic method
   public final zzkp zzr(int var1) {
      if (var1 >= this.size) {
         return new zzkh(Arrays.copyOf(this.zzrn, var1), this.size);
      } else {
         throw new IllegalArgumentException();
      }
   }
}
