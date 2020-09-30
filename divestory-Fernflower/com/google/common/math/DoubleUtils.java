package com.google.common.math;

import com.google.common.base.Preconditions;
import java.math.BigInteger;

final class DoubleUtils {
   static final int EXPONENT_BIAS = 1023;
   static final long EXPONENT_MASK = 9218868437227405312L;
   static final long IMPLICIT_BIT = 4503599627370496L;
   static final long ONE_BITS = 4607182418800017408L;
   static final int SIGNIFICAND_BITS = 52;
   static final long SIGNIFICAND_MASK = 4503599627370495L;
   static final long SIGN_MASK = Long.MIN_VALUE;

   private DoubleUtils() {
   }

   static double bigToDouble(BigInteger var0) {
      BigInteger var1 = var0.abs();
      int var2 = var1.bitLength();
      boolean var3 = true;
      int var4 = var2 - 1;
      if (var4 < 63) {
         return (double)var0.longValue();
      } else if (var4 > 1023) {
         return (double)var0.signum() * Double.POSITIVE_INFINITY;
      } else {
         long var6;
         long var8;
         boolean var10;
         label24: {
            int var5 = var4 - 52 - 1;
            var6 = var1.shiftRight(var5).longValue();
            var8 = var6 >> 1 & 4503599627370495L;
            if ((var6 & 1L) != 0L) {
               var10 = var3;
               if ((var8 & 1L) != 0L) {
                  break label24;
               }

               if (var1.getLowestSetBit() < var5) {
                  var10 = var3;
                  break label24;
               }
            }

            var10 = false;
         }

         var6 = var8;
         if (var10) {
            var6 = var8 + 1L;
         }

         return Double.longBitsToDouble(((long)(var4 + 1023) << 52) + var6 | (long)var0.signum() & Long.MIN_VALUE);
      }
   }

   static double ensureNonNegative(double var0) {
      Preconditions.checkArgument(Double.isNaN(var0) ^ true);
      return Math.max(var0, 0.0D);
   }

   static long getSignificand(double var0) {
      Preconditions.checkArgument(isFinite(var0), "not a normal value");
      int var2 = Math.getExponent(var0);
      long var3 = Double.doubleToRawLongBits(var0) & 4503599627370495L;
      if (var2 == -1023) {
         var3 <<= 1;
      } else {
         var3 |= 4503599627370496L;
      }

      return var3;
   }

   static boolean isFinite(double var0) {
      boolean var2;
      if (Math.getExponent(var0) <= 1023) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   static boolean isNormal(double var0) {
      boolean var2;
      if (Math.getExponent(var0) >= -1022) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   static double nextDown(double var0) {
      return -Math.nextUp(-var0);
   }

   static double scaleNormalize(double var0) {
      return Double.longBitsToDouble(Double.doubleToRawLongBits(var0) & 4503599627370495L | 4607182418800017408L);
   }
}
