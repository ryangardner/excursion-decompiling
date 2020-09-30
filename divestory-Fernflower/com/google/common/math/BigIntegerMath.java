package com.google.common.math;

import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class BigIntegerMath {
   private static final double LN_10 = Math.log(10.0D);
   private static final double LN_2 = Math.log(2.0D);
   static final BigInteger SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
   static final int SQRT2_PRECOMPUTE_THRESHOLD = 256;

   private BigIntegerMath() {
   }

   public static BigInteger binomial(int var0, int var1) {
      MathPreconditions.checkNonNegative("n", var0);
      MathPreconditions.checkNonNegative("k", var1);
      byte var2 = 1;
      boolean var3;
      if (var1 <= var0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "k (%s) > n (%s)", var1, var0);
      int var4 = var1;
      if (var1 > var0 >> 1) {
         var4 = var0 - var1;
      }

      if (var4 < LongMath.biggestBinomials.length && var0 <= LongMath.biggestBinomials[var4]) {
         return BigInteger.valueOf(LongMath.binomial(var0, var4));
      } else {
         BigInteger var5 = BigInteger.ONE;
         long var6 = (long)var0;
         long var8 = 1L;
         int var10 = LongMath.log2(var6, RoundingMode.CEILING);
         var1 = var2;

         label34:
         while(true) {
            for(int var12 = var10; var1 < var4; var8 *= (long)var1) {
               int var11 = var0 - var1;
               ++var1;
               var12 += var10;
               if (var12 >= 63) {
                  var5 = var5.multiply(BigInteger.valueOf(var6)).divide(BigInteger.valueOf(var8));
                  var6 = (long)var11;
                  var8 = (long)var1;
                  continue label34;
               }

               var6 *= (long)var11;
            }

            return var5.multiply(BigInteger.valueOf(var6)).divide(BigInteger.valueOf(var8));
         }
      }
   }

   public static BigInteger ceilingPowerOfTwo(BigInteger var0) {
      return BigInteger.ZERO.setBit(log2(var0, RoundingMode.CEILING));
   }

   public static BigInteger divide(BigInteger var0, BigInteger var1, RoundingMode var2) {
      return (new BigDecimal(var0)).divide(new BigDecimal(var1), 0, var2).toBigIntegerExact();
   }

   public static BigInteger factorial(int var0) {
      MathPreconditions.checkNonNegative("n", var0);
      if (var0 < LongMath.factorials.length) {
         return BigInteger.valueOf(LongMath.factorials[var0]);
      } else {
         ArrayList var1 = new ArrayList(IntMath.divide(IntMath.log2(var0, RoundingMode.CEILING) * var0, 64, RoundingMode.CEILING));
         int var2 = LongMath.factorials.length;
         long var3 = LongMath.factorials[var2 - 1];
         int var5 = Long.numberOfTrailingZeros(var3);
         var3 >>= var5;
         int var6 = LongMath.log2(var3, RoundingMode.FLOOR) + 1;
         long var7 = (long)var2;
         int var9 = LongMath.log2(var7, RoundingMode.FLOOR) + 1;

         for(int var10 = 1 << var9 - 1; var7 <= (long)var0; var10 = var2) {
            int var11 = var9;
            var2 = var10;
            if (((long)var10 & var7) != 0L) {
               var2 = var10 << 1;
               var11 = var9 + 1;
            }

            var10 = Long.numberOfTrailingZeros(var7);
            var5 += var10;
            long var12 = var3;
            if (var11 - var10 + var6 >= 64) {
               var1.add(BigInteger.valueOf(var3));
               var12 = 1L;
            }

            var3 = var12 * (var7 >> var10);
            var6 = LongMath.log2(var3, RoundingMode.FLOOR) + 1;
            ++var7;
            var9 = var11;
         }

         if (var3 > 1L) {
            var1.add(BigInteger.valueOf(var3));
         }

         return listProduct(var1).shiftLeft(var5);
      }
   }

   static boolean fitsInLong(BigInteger var0) {
      boolean var1;
      if (var0.bitLength() <= 63) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static BigInteger floorPowerOfTwo(BigInteger var0) {
      return BigInteger.ZERO.setBit(log2(var0, RoundingMode.FLOOR));
   }

   public static boolean isPowerOfTwo(BigInteger var0) {
      Preconditions.checkNotNull(var0);
      int var1 = var0.signum();
      boolean var2 = true;
      if (var1 <= 0 || var0.getLowestSetBit() != var0.bitLength() - 1) {
         var2 = false;
      }

      return var2;
   }

   static BigInteger listProduct(List<BigInteger> var0) {
      return listProduct(var0, 0, var0.size());
   }

   static BigInteger listProduct(List<BigInteger> var0, int var1, int var2) {
      int var3 = var2 - var1;
      if (var3 != 0) {
         if (var3 != 1) {
            if (var3 != 2) {
               if (var3 != 3) {
                  var3 = var2 + var1 >>> 1;
                  return listProduct(var0, var1, var3).multiply(listProduct(var0, var3, var2));
               } else {
                  return ((BigInteger)var0.get(var1)).multiply((BigInteger)var0.get(var1 + 1)).multiply((BigInteger)var0.get(var1 + 2));
               }
            } else {
               return ((BigInteger)var0.get(var1)).multiply((BigInteger)var0.get(var1 + 1));
            }
         } else {
            return (BigInteger)var0.get(var1);
         }
      } else {
         return BigInteger.ONE;
      }
   }

   public static int log10(BigInteger var0, RoundingMode var1) {
      MathPreconditions.checkPositive("x", var0);
      if (fitsInLong(var0)) {
         return LongMath.log10(var0.longValue(), var1);
      } else {
         int var2 = (int)((double)log2(var0, RoundingMode.FLOOR) * LN_2 / LN_10);
         BigInteger var3 = BigInteger.TEN.pow(var2);
         int var4 = var3.compareTo(var0);
         int var5;
         BigInteger var6;
         if (var4 > 0) {
            do {
               var5 = var2 - 1;
               var6 = var3.divide(BigInteger.TEN);
               var4 = var6.compareTo(var0);
               var2 = var5;
               var3 = var6;
            } while(var4 > 0);

            var3 = var6;
         } else {
            var6 = BigInteger.TEN.multiply(var3);
            var5 = var6.compareTo(var0);

            while(true) {
               BigInteger var7 = var6;
               if (var5 > 0) {
                  var5 = var2;
                  break;
               }

               ++var2;
               var6 = BigInteger.TEN.multiply(var6);
               int var8 = var6.compareTo(var0);
               var3 = var7;
               var4 = var5;
               var5 = var8;
            }
         }

         switch(null.$SwitchMap$java$math$RoundingMode[var1.ordinal()]) {
         case 1:
            boolean var9;
            if (var4 == 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            MathPreconditions.checkRoundingUnnecessary(var9);
         case 2:
         case 3:
            return var5;
         case 4:
         case 5:
            if (!var3.equals(var0)) {
               ++var5;
            }

            return var5;
         case 6:
         case 7:
         case 8:
            if (var0.pow(2).compareTo(var3.pow(2).multiply(BigInteger.TEN)) > 0) {
               ++var5;
            }

            return var5;
         default:
            throw new AssertionError();
         }
      }
   }

   public static int log2(BigInteger var0, RoundingMode var1) {
      MathPreconditions.checkPositive("x", (BigInteger)Preconditions.checkNotNull(var0));
      int var2 = var0.bitLength() - 1;
      switch(null.$SwitchMap$java$math$RoundingMode[var1.ordinal()]) {
      case 1:
         MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(var0));
      case 2:
      case 3:
         return var2;
      case 4:
      case 5:
         if (!isPowerOfTwo(var0)) {
            ++var2;
         }

         return var2;
      case 6:
      case 7:
      case 8:
         if (var2 < 256) {
            if (var0.compareTo(SQRT2_PRECOMPUTED_BITS.shiftRight(256 - var2)) <= 0) {
               return var2;
            }

            return var2 + 1;
         }

         if (var0.pow(2).bitLength() - 1 >= var2 * 2 + 1) {
            ++var2;
         }

         return var2;
      default:
         throw new AssertionError();
      }
   }

   public static BigInteger sqrt(BigInteger var0, RoundingMode var1) {
      MathPreconditions.checkNonNegative("x", var0);
      if (fitsInLong(var0)) {
         return BigInteger.valueOf(LongMath.sqrt(var0.longValue(), var1));
      } else {
         BigInteger var2 = sqrtFloor(var0);
         switch(null.$SwitchMap$java$math$RoundingMode[var1.ordinal()]) {
         case 1:
            MathPreconditions.checkRoundingUnnecessary(var2.pow(2).equals(var0));
         case 2:
         case 3:
            return var2;
         case 4:
         case 5:
            int var3 = var2.intValue();
            boolean var4;
            if (var3 * var3 == var0.intValue() && var2.pow(2).equals(var0)) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (!var4) {
               var2 = var2.add(BigInteger.ONE);
            }

            return var2;
         case 6:
         case 7:
         case 8:
            if (var2.pow(2).add(var2).compareTo(var0) < 0) {
               var2 = var2.add(BigInteger.ONE);
            }

            return var2;
         default:
            throw new AssertionError();
         }
      }
   }

   private static BigInteger sqrtApproxWithDoubles(BigInteger var0) {
      return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(var0)), RoundingMode.HALF_EVEN);
   }

   private static BigInteger sqrtFloor(BigInteger var0) {
      int var1 = log2(var0, RoundingMode.FLOOR);
      BigInteger var2;
      if (var1 < 1023) {
         var2 = sqrtApproxWithDoubles(var0);
      } else {
         var1 = var1 - 52 & -2;
         var2 = sqrtApproxWithDoubles(var0.shiftRight(var1)).shiftLeft(var1 >> 1);
      }

      BigInteger var3 = var2.add(var0.divide(var2)).shiftRight(1);
      BigInteger var4 = var3;
      if (var2.equals(var3)) {
         return var2;
      } else {
         while(true) {
            var2 = var4.add(var0.divide(var4)).shiftRight(1);
            if (var2.compareTo(var4) >= 0) {
               return var4;
            }

            var4 = var2;
         }
      }
   }
}
