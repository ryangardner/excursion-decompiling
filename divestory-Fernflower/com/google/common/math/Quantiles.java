package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Quantiles {
   private static void checkIndex(int var0, int var1) {
      if (var0 < 0 || var0 > var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Quantile indexes must be between 0 and the scale, which is ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private static int chooseNextSelection(int[] var0, int var1, int var2, int var3, int var4) {
      if (var1 == var2) {
         return var1;
      } else {
         var4 += var3;
         int var5 = var4 >>> 1;

         while(var2 > var1 + 1) {
            var3 = var1 + var2 >>> 1;
            if (var0[var3] > var5) {
               var2 = var3;
            } else {
               if (var0[var3] >= var5) {
                  return var3;
               }

               var1 = var3;
            }
         }

         if (var4 - var0[var1] - var0[var2] > 0) {
            return var2;
         } else {
            return var1;
         }
      }
   }

   private static boolean containsNaN(double... var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (Double.isNaN(var0[var2])) {
            return true;
         }
      }

      return false;
   }

   private static double interpolate(double var0, double var2, double var4, double var6) {
      if (var0 == Double.NEGATIVE_INFINITY) {
         return var2 == Double.POSITIVE_INFINITY ? Double.NaN : Double.NEGATIVE_INFINITY;
      } else {
         return var2 == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : var0 + (var2 - var0) * var4 / var6;
      }
   }

   private static double[] intsToDoubles(int[] var0) {
      int var1 = var0.length;
      double[] var2 = new double[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = (double)var0[var3];
      }

      return var2;
   }

   private static double[] longsToDoubles(long[] var0) {
      int var1 = var0.length;
      double[] var2 = new double[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = (double)var0[var3];
      }

      return var2;
   }

   public static Quantiles.ScaleAndIndex median() {
      return scale(2).index(1);
   }

   private static void movePivotToStartOfSlice(double[] var0, int var1, int var2) {
      boolean var3 = true;
      int var4 = var1 + var2 >>> 1;
      boolean var5;
      if (var0[var2] < var0[var4]) {
         var5 = true;
      } else {
         var5 = false;
      }

      boolean var6;
      if (var0[var4] < var0[var1]) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var0[var2] >= var0[var1]) {
         var3 = false;
      }

      if (var5 == var6) {
         swap(var0, var4, var1);
      } else if (var5 != var3) {
         swap(var0, var1, var2);
      }

   }

   private static int partition(double[] var0, int var1, int var2) {
      movePivotToStartOfSlice(var0, var1, var2);
      double var3 = var0[var1];

      int var5;
      int var6;
      for(var5 = var2; var2 > var1; var5 = var6) {
         var6 = var5;
         if (var0[var2] > var3) {
            swap(var0, var5, var2);
            var6 = var5 - 1;
         }

         --var2;
      }

      swap(var0, var1, var5);
      return var5;
   }

   public static Quantiles.Scale percentiles() {
      return scale(100);
   }

   public static Quantiles.Scale quartiles() {
      return scale(4);
   }

   public static Quantiles.Scale scale(int var0) {
      return new Quantiles.Scale(var0);
   }

   private static void selectAllInPlace(int[] var0, int var1, int var2, double[] var3, int var4, int var5) {
      int var6 = chooseNextSelection(var0, var1, var2, var4, var5);
      int var7 = var0[var6];
      selectInPlace(var7, var3, var4, var5);

      int var8;
      for(var8 = var6 - 1; var8 >= var1 && var0[var8] == var7; --var8) {
      }

      if (var8 >= var1) {
         selectAllInPlace(var0, var1, var8, var3, var4, var7 - 1);
      }

      for(var1 = var6 + 1; var1 <= var2 && var0[var1] == var7; ++var1) {
      }

      if (var1 <= var2) {
         selectAllInPlace(var0, var1, var2, var3, var7 + 1, var5);
      }

   }

   private static void selectInPlace(int var0, double[] var1, int var2, int var3) {
      int var4 = var2;
      int var5 = var3;
      if (var0 == var2) {
         var0 = var2 + 1;

         for(var4 = var2; var0 <= var3; var4 = var5) {
            var5 = var4;
            if (var1[var4] > var1[var0]) {
               var5 = var0;
            }

            ++var0;
         }

         if (var4 != var2) {
            swap(var1, var4, var2);
         }

      } else {
         while(var5 > var4) {
            var3 = partition(var1, var4, var5);
            var2 = var5;
            if (var3 >= var0) {
               var2 = var3 - 1;
            }

            var5 = var2;
            if (var3 <= var0) {
               var4 = var3 + 1;
               var5 = var2;
            }
         }

      }
   }

   private static void swap(double[] var0, int var1, int var2) {
      double var3 = var0[var1];
      var0[var1] = var0[var2];
      var0[var2] = var3;
   }

   public static final class Scale {
      private final int scale;

      private Scale(int var1) {
         boolean var2;
         if (var1 > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "Quantile scale must be positive");
         this.scale = var1;
      }

      // $FF: synthetic method
      Scale(int var1, Object var2) {
         this(var1);
      }

      public Quantiles.ScaleAndIndex index(int var1) {
         return new Quantiles.ScaleAndIndex(this.scale, var1);
      }

      public Quantiles.ScaleAndIndexes indexes(Collection<Integer> var1) {
         return new Quantiles.ScaleAndIndexes(this.scale, Ints.toArray(var1));
      }

      public Quantiles.ScaleAndIndexes indexes(int... var1) {
         return new Quantiles.ScaleAndIndexes(this.scale, (int[])var1.clone());
      }
   }

   public static final class ScaleAndIndex {
      private final int index;
      private final int scale;

      private ScaleAndIndex(int var1, int var2) {
         Quantiles.checkIndex(var2, var1);
         this.scale = var1;
         this.index = var2;
      }

      // $FF: synthetic method
      ScaleAndIndex(int var1, int var2, Object var3) {
         this(var1, var2);
      }

      public double compute(Collection<? extends Number> var1) {
         return this.computeInPlace(Doubles.toArray(var1));
      }

      public double compute(double... var1) {
         return this.computeInPlace((double[])var1.clone());
      }

      public double compute(int... var1) {
         return this.computeInPlace(Quantiles.intsToDoubles(var1));
      }

      public double compute(long... var1) {
         return this.computeInPlace(Quantiles.longsToDoubles(var1));
      }

      public double computeInPlace(double... var1) {
         boolean var2;
         if (var1.length > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "Cannot calculate quantiles of an empty dataset");
         if (Quantiles.containsNaN(var1)) {
            return Double.NaN;
         } else {
            long var3 = (long)this.index * (long)(var1.length - 1);
            int var5 = (int)LongMath.divide(var3, (long)this.scale, RoundingMode.DOWN);
            int var6 = (int)(var3 - (long)var5 * (long)this.scale);
            Quantiles.selectInPlace(var5, var1, 0, var1.length - 1);
            if (var6 == 0) {
               return var1[var5];
            } else {
               int var7 = var5 + 1;
               Quantiles.selectInPlace(var7, var1, var7, var1.length - 1);
               return Quantiles.interpolate(var1[var5], var1[var7], (double)var6, (double)this.scale);
            }
         }
      }
   }

   public static final class ScaleAndIndexes {
      private final int[] indexes;
      private final int scale;

      private ScaleAndIndexes(int var1, int[] var2) {
         int var3 = var2.length;
         boolean var4 = false;

         for(int var5 = 0; var5 < var3; ++var5) {
            Quantiles.checkIndex(var2[var5], var1);
         }

         if (var2.length > 0) {
            var4 = true;
         }

         Preconditions.checkArgument(var4, "Indexes must be a non empty array");
         this.scale = var1;
         this.indexes = var2;
      }

      // $FF: synthetic method
      ScaleAndIndexes(int var1, int[] var2, Object var3) {
         this(var1, var2);
      }

      public Map<Integer, Double> compute(Collection<? extends Number> var1) {
         return this.computeInPlace(Doubles.toArray(var1));
      }

      public Map<Integer, Double> compute(double... var1) {
         return this.computeInPlace((double[])var1.clone());
      }

      public Map<Integer, Double> compute(int... var1) {
         return this.computeInPlace(Quantiles.intsToDoubles(var1));
      }

      public Map<Integer, Double> compute(long... var1) {
         return this.computeInPlace(Quantiles.longsToDoubles(var1));
      }

      public Map<Integer, Double> computeInPlace(double... var1) {
         int var2 = var1.length;
         byte var3 = 0;
         int var4 = 0;
         boolean var5;
         if (var2 > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "Cannot calculate quantiles of an empty dataset");
         int[] var6;
         if (!Quantiles.containsNaN(var1)) {
            int[] var7 = this.indexes;
            int[] var8 = new int[var7.length];
            var6 = new int[var7.length];
            int[] var9 = new int[var7.length * 2];
            var2 = 0;
            var4 = 0;

            while(true) {
               var7 = this.indexes;
               if (var2 >= var7.length) {
                  Arrays.sort(var9, 0, var4);
                  Quantiles.selectAllInPlace(var9, 0, var4 - 1, var1, 0, var1.length - 1);
                  LinkedHashMap var17 = new LinkedHashMap();
                  var4 = var3;

                  while(true) {
                     var9 = this.indexes;
                     if (var4 >= var9.length) {
                        return Collections.unmodifiableMap(var17);
                     }

                     var2 = var8[var4];
                     int var16 = var6[var4];
                     if (var16 == 0) {
                        var17.put(var9[var4], var1[var2]);
                     } else {
                        var17.put(var9[var4], Quantiles.interpolate(var1[var2], var1[var2 + 1], (double)var16, (double)this.scale));
                     }

                     ++var4;
                  }
               }

               long var10 = (long)var7[var2] * (long)(var1.length - 1);
               int var12 = (int)LongMath.divide(var10, (long)this.scale, RoundingMode.DOWN);
               int var13 = (int)(var10 - (long)var12 * (long)this.scale);
               var8[var2] = var12;
               var6[var2] = var13;
               var9[var4] = var12;
               int var14 = var4 + 1;
               var4 = var14;
               if (var13 != 0) {
                  var9[var14] = var12 + 1;
                  var4 = var14 + 1;
               }

               ++var2;
            }
         } else {
            LinkedHashMap var15 = new LinkedHashMap();
            var6 = this.indexes;

            for(var2 = var6.length; var4 < var2; ++var4) {
               var15.put(var6[var4], Double.NaN);
            }

            return Collections.unmodifiableMap(var15);
         }
      }
   }
}
