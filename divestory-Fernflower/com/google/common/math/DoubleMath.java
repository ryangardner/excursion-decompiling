package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

public final class DoubleMath {
   private static final double LN_2 = Math.log(2.0D);
   static final int MAX_FACTORIAL = 170;
   private static final double MAX_INT_AS_DOUBLE = 2.147483647E9D;
   private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18D;
   private static final double MIN_INT_AS_DOUBLE = -2.147483648E9D;
   private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18D;
   static final double[] everySixteenthFactorial = new double[]{1.0D, 2.0922789888E13D, 2.631308369336935E35D, 1.2413915592536073E61D, 1.2688693218588417E89D, 7.156945704626381E118D, 9.916779348709496E149D, 1.974506857221074E182D, 3.856204823625804E215D, 5.5502938327393044E249D, 4.7147236359920616E284D};

   private DoubleMath() {
   }

   private static double checkFinite(double var0) {
      Preconditions.checkArgument(DoubleUtils.isFinite(var0));
      return var0;
   }

   public static double factorial(int var0) {
      MathPreconditions.checkNonNegative("n", var0);
      if (var0 > 170) {
         return Double.POSITIVE_INFINITY;
      } else {
         double var1 = 1.0D;
         int var3 = var0 & -16;

         while(true) {
            ++var3;
            if (var3 > var0) {
               return var1 * everySixteenthFactorial[var0 >> 4];
            }

            var1 *= (double)var3;
         }
      }
   }

   public static int fuzzyCompare(double var0, double var2, double var4) {
      if (fuzzyEquals(var0, var2, var4)) {
         return 0;
      } else if (var0 < var2) {
         return -1;
      } else {
         return var0 > var2 ? 1 : Booleans.compare(Double.isNaN(var0), Double.isNaN(var2));
      }
   }

   public static boolean fuzzyEquals(double var0, double var2, double var4) {
      MathPreconditions.checkNonNegative("tolerance", var4);
      boolean var6;
      if (Math.copySign(var0 - var2, 1.0D) <= var4 || var0 == var2 || Double.isNaN(var0) && Double.isNaN(var2)) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   public static boolean isMathematicalInteger(double var0) {
      boolean var2;
      if (!DoubleUtils.isFinite(var0) || var0 != 0.0D && 52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(var0)) > Math.getExponent(var0)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static boolean isPowerOfTwo(double var0) {
      boolean var2 = false;
      boolean var3 = var2;
      if (var0 > 0.0D) {
         var3 = var2;
         if (DoubleUtils.isFinite(var0)) {
            long var4 = DoubleUtils.getSignificand(var0);
            var3 = var2;
            if ((var4 & var4 - 1L) == 0L) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   public static double log2(double var0) {
      return Math.log(var0) / LN_2;
   }

   public static int log2(double var0, RoundingMode var2) {
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6;
      if (var0 > 0.0D && DoubleUtils.isFinite(var0)) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "x must be positive and finite");
      int var7 = Math.getExponent(var0);
      if (!DoubleUtils.isNormal(var0)) {
         return log2(var0 * 4.503599627370496E15D, var2) - 52;
      } else {
         boolean var8;
         label42: {
            var8 = var4;
            switch(null.$SwitchMap$java$math$RoundingMode[var2.ordinal()]) {
            case 1:
               MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(var0));
               var8 = var4;
            case 2:
               break label42;
            case 3:
               var8 = isPowerOfTwo(var0) ^ true;
               break label42;
            case 4:
               var8 = var3;
               if (var7 < 0) {
                  var8 = true;
               }

               var6 = isPowerOfTwo(var0);
               break;
            case 5:
               var8 = var5;
               if (var7 >= 0) {
                  var8 = true;
               }

               var6 = isPowerOfTwo(var0);
               break;
            case 6:
            case 7:
            case 8:
               var0 = DoubleUtils.scaleNormalize(var0);
               var8 = var4;
               if (var0 * var0 > 2.0D) {
                  var8 = true;
               }
               break label42;
            default:
               throw new AssertionError();
            }

            var8 &= var6 ^ true;
         }

         int var9 = var7;
         if (var8) {
            var9 = var7 + 1;
         }

         return var9;
      }
   }

   @Deprecated
   public static double mean(Iterable<? extends Number> var0) {
      return mean(var0.iterator());
   }

   @Deprecated
   public static double mean(Iterator<? extends Number> var0) {
      Preconditions.checkArgument(var0.hasNext(), "Cannot take mean of 0 values");
      double var1 = checkFinite(((Number)var0.next()).doubleValue());

      double var5;
      for(long var3 = 1L; var0.hasNext(); var1 += (var5 - var1) / (double)var3) {
         var5 = checkFinite(((Number)var0.next()).doubleValue());
         ++var3;
      }

      return var1;
   }

   @Deprecated
   public static double mean(double... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Cannot take mean of 0 values");
      double var4 = checkFinite(var0[0]);

      for(long var6 = 1L; var2 < var0.length; ++var2) {
         checkFinite(var0[var2]);
         ++var6;
         var4 += (var0[var2] - var4) / (double)var6;
      }

      return var4;
   }

   @Deprecated
   public static double mean(int... var0) {
      int var1 = var0.length;
      int var2 = 0;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Cannot take mean of 0 values");

      long var4;
      for(var4 = 0L; var2 < var0.length; ++var2) {
         var4 += (long)var0[var2];
      }

      return (double)var4 / (double)var0.length;
   }

   @Deprecated
   public static double mean(long... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Cannot take mean of 0 values");
      double var4 = (double)var0[0];

      for(long var6 = 1L; var2 < var0.length; ++var2) {
         ++var6;
         var4 += ((double)var0[var2] - var4) / (double)var6;
      }

      return var4;
   }

   static double roundIntermediate(double var0, RoundingMode var2) {
      if (DoubleUtils.isFinite(var0)) {
         double var3 = var0;
         switch(null.$SwitchMap$java$math$RoundingMode[var2.ordinal()]) {
         case 1:
            MathPreconditions.checkRoundingUnnecessary(isMathematicalInteger(var0));
            return var0;
         case 2:
            var3 = var0;
            if (var0 < 0.0D) {
               if (isMathematicalInteger(var0)) {
                  var3 = var0;
               } else {
                  var3 = (double)((long)var0 - 1L);
               }
            }

            return var3;
         case 3:
            var3 = var0;
            if (var0 > 0.0D) {
               if (isMathematicalInteger(var0)) {
                  var3 = var0;
               } else {
                  var3 = (double)((long)var0 + 1L);
               }
            }

            return var3;
         case 5:
            if (isMathematicalInteger(var0)) {
               return var0;
            } else {
               long var5 = (long)var0;
               byte var7;
               if (var0 > 0.0D) {
                  var7 = 1;
               } else {
                  var7 = -1;
               }

               var3 = (double)(var5 + (long)var7);
            }
         case 4:
            return var3;
         case 6:
            return Math.rint(var0);
         case 7:
            var3 = Math.rint(var0);
            if (Math.abs(var0 - var3) == 0.5D) {
               return var0 + Math.copySign(0.5D, var0);
            }

            return var3;
         case 8:
            var3 = Math.rint(var0);
            if (Math.abs(var0 - var3) == 0.5D) {
               return var0;
            }

            return var3;
         default:
            throw new AssertionError();
         }
      } else {
         throw new ArithmeticException("input is infinite or NaN");
      }
   }

   public static BigInteger roundToBigInteger(double var0, RoundingMode var2) {
      var0 = roundIntermediate(var0, var2);
      boolean var3 = true;
      boolean var4;
      if (-9.223372036854776E18D - var0 < 1.0D) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var0 >= 9.223372036854776E18D) {
         var3 = false;
      }

      if (var3 & var4) {
         return BigInteger.valueOf((long)var0);
      } else {
         int var7 = Math.getExponent(var0);
         BigInteger var5 = BigInteger.valueOf(DoubleUtils.getSignificand(var0)).shiftLeft(var7 - 52);
         BigInteger var6 = var5;
         if (var0 < 0.0D) {
            var6 = var5.negate();
         }

         return var6;
      }
   }

   public static int roundToInt(double var0, RoundingMode var2) {
      double var3 = roundIntermediate(var0, var2);
      boolean var5 = true;
      boolean var6;
      if (var3 > -2.147483649E9D) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var3 >= 2.147483648E9D) {
         var5 = false;
      }

      MathPreconditions.checkInRangeForRoundingInputs(var5 & var6, var0, var2);
      return (int)var3;
   }

   public static long roundToLong(double var0, RoundingMode var2) {
      double var3 = roundIntermediate(var0, var2);
      boolean var5 = true;
      boolean var6;
      if (-9.223372036854776E18D - var3 < 1.0D) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var3 >= 9.223372036854776E18D) {
         var5 = false;
      }

      MathPreconditions.checkInRangeForRoundingInputs(var6 & var5, var0, var2);
      return (long)var3;
   }
}
