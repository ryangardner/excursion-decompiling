package com.google.android.gms.internal.drive;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzju extends zziw<Double> implements zzkp<Double>, zzmc, RandomAccess {
   private static final zzju zzoi;
   private int size;
   private double[] zzoj;

   static {
      zzju var0 = new zzju(new double[0], 0);
      zzoi = var0;
      var0.zzbp();
   }

   zzju() {
      this(new double[10], 0);
   }

   private zzju(double[] var1, int var2) {
      this.zzoj = var1;
      this.size = var2;
   }

   private final void zzc(int var1, double var2) {
      this.zzbq();
      if (var1 >= 0) {
         int var4 = this.size;
         if (var1 <= var4) {
            double[] var5 = this.zzoj;
            if (var4 < var5.length) {
               System.arraycopy(var5, var1, var5, var1 + 1, var4 - var1);
            } else {
               double[] var6 = new double[var4 * 3 / 2 + 1];
               System.arraycopy(var5, 0, var6, 0, var1);
               System.arraycopy(this.zzoj, var1, var6, var1 + 1, this.size - var1);
               this.zzoj = var6;
            }

            this.zzoj[var1] = var2;
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
      this.zzc(var1, (Double)var2);
   }

   public final boolean addAll(Collection<? extends Double> var1) {
      this.zzbq();
      zzkm.checkNotNull(var1);
      if (!(var1 instanceof zzju)) {
         return super.addAll(var1);
      } else {
         zzju var2 = (zzju)var1;
         int var3 = var2.size;
         if (var3 == 0) {
            return false;
         } else {
            int var4 = this.size;
            if (Integer.MAX_VALUE - var4 >= var3) {
               var3 += var4;
               double[] var5 = this.zzoj;
               if (var3 > var5.length) {
                  this.zzoj = Arrays.copyOf(var5, var3);
               }

               System.arraycopy(var2.zzoj, 0, this.zzoj, this.size, var2.size);
               this.size = var3;
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
      } else if (!(var1 instanceof zzju)) {
         return super.equals(var1);
      } else {
         zzju var3 = (zzju)var1;
         if (this.size != var3.size) {
            return false;
         } else {
            double[] var4 = var3.zzoj;

            for(int var2 = 0; var2 < this.size; ++var2) {
               if (Double.doubleToLongBits(this.zzoj[var2]) != Double.doubleToLongBits(var4[var2])) {
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
      return this.zzoj[var1];
   }

   public final int hashCode() {
      int var1 = 1;

      for(int var2 = 0; var2 < this.size; ++var2) {
         var1 = var1 * 31 + zzkm.zzu(Double.doubleToLongBits(this.zzoj[var2]));
      }

      return var1;
   }

   // $FF: synthetic method
   public final Object remove(int var1) {
      this.zzbq();
      this.zzp(var1);
      double[] var2 = this.zzoj;
      double var3 = var2[var1];
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
         if (var1.equals(this.zzoj[var2])) {
            double[] var3 = this.zzoj;
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
         double[] var3 = this.zzoj;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         ++this.modCount;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   // $FF: synthetic method
   public final Object set(int var1, Object var2) {
      double var3 = (Double)var2;
      this.zzbq();
      this.zzp(var1);
      double[] var7 = this.zzoj;
      double var5 = var7[var1];
      var7[var1] = var3;
      return var5;
   }

   public final int size() {
      return this.size;
   }

   public final void zzc(double var1) {
      this.zzc(this.size, var1);
   }

   // $FF: synthetic method
   public final zzkp zzr(int var1) {
      if (var1 >= this.size) {
         return new zzju(Arrays.copyOf(this.zzoj, var1), this.size);
      } else {
         throw new IllegalArgumentException();
      }
   }
}
