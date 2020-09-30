package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedLongs;
import java.math.RoundingMode;

public final class LongMath {
   static final long FLOOR_SQRT_MAX_LONG = 3037000499L;
   static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;
   static final long MAX_SIGNED_POWER_OF_TWO = 4611686018427387904L;
   private static final int SIEVE_30 = -545925251;
   static final int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
   static final int[] biggestSimpleBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, 169, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
   static final long[] factorials = new long[]{1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
   static final long[] halfPowersOf10 = new long[]{3L, 31L, 316L, 3162L, 31622L, 316227L, 3162277L, 31622776L, 316227766L, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
   static final byte[] maxLog10ForLeadingZeros = new byte[]{19, 18, 18, 18, 18, 17, 17, 17, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 13, 13, 13, 12, 12, 12, 12, 11, 11, 11, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0};
   private static final long[][] millerRabinBaseSets;
   static final long[] powersOf10 = new long[]{1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};

   static {
      long[] var0 = new long[]{47636622961200L, 2L, 2570940L, 211991001L, 3749873356L};
      long[] var1 = new long[]{7999252175582850L, 2L, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L};
      long[] var2 = new long[]{585226005592931976L, 2L, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L};
      millerRabinBaseSets = new long[][]{{291830L, 126401071349994536L}, {885594168L, 725270293939359937L, 3569819667048198375L}, {273919523040L, 15L, 7363882082L, 992620450144556L}, var0, var1, var2, {Long.MAX_VALUE, 2L, 325L, 9375L, 28178L, 450775L, 9780504L, 1795265022L}};
   }

   private LongMath() {
   }

   public static long binomial(int var0, int var1) {
      MathPreconditions.checkNonNegative("n", var0);
      MathPreconditions.checkNonNegative("k", var1);
      boolean var2;
      if (var1 <= var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "k (%s) > n (%s)", var1, var0);
      int var3 = var1;
      if (var1 > var0 >> 1) {
         var3 = var0 - var1;
      }

      long var4 = 1L;
      if (var3 == 0) {
         return 1L;
      } else if (var3 != 1) {
         long[] var6 = factorials;
         if (var0 < var6.length) {
            return var6[var0] / (var6[var3] * var6[var0 - var3]);
         } else {
            int[] var14 = biggestBinomials;
            if (var3 < var14.length && var0 <= var14[var3]) {
               var14 = biggestSimpleBinomials;
               int var7 = var14.length;
               byte var13 = 2;
               long var8;
               if (var3 < var7 && var0 <= var14[var3]) {
                  var7 = var0 - 1;
                  var8 = (long)var0;
                  var0 = var13;

                  for(var1 = var7; var0 <= var3; ++var0) {
                     var8 = var8 * (long)var1 / (long)var0;
                     --var1;
                  }

                  return var8;
               } else {
                  long var10 = (long)var0;
                  var1 = log2(var10, RoundingMode.CEILING);
                  var7 = var0 - 1;
                  var0 = var1;
                  int var12 = 2;

                  for(var8 = 1L; var12 <= var3; --var7) {
                     var0 += var1;
                     if (var0 < 63) {
                        var10 *= (long)var7;
                        var8 *= (long)var12;
                     } else {
                        var4 = multiplyFraction(var4, var10, var8);
                        var10 = (long)var7;
                        var8 = (long)var12;
                        var0 = var1;
                     }

                     ++var12;
                  }

                  return multiplyFraction(var4, var10, var8);
               }
            } else {
               return Long.MAX_VALUE;
            }
         }
      } else {
         return (long)var0;
      }
   }

   public static long ceilingPowerOfTwo(long var0) {
      MathPreconditions.checkPositive("x", var0);
      if (var0 <= 4611686018427387904L) {
         return 1L << -Long.numberOfLeadingZeros(var0 - 1L);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("ceilingPowerOfTwo(");
         var2.append(var0);
         var2.append(") is not representable as a long");
         throw new ArithmeticException(var2.toString());
      }
   }

   public static long checkedAdd(long var0, long var2) {
      long var4 = var0 + var2;
      boolean var6 = true;
      boolean var7;
      if ((var0 ^ var2) < 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      if ((var0 ^ var4) < 0L) {
         var6 = false;
      }

      MathPreconditions.checkNoOverflow(var7 | var6, "checkedAdd", var0, var2);
      return var4;
   }

   public static long checkedMultiply(long var0, long var2) {
      int var4 = Long.numberOfLeadingZeros(var0) + Long.numberOfLeadingZeros(var0) + Long.numberOfLeadingZeros(var2) + Long.numberOfLeadingZeros(var2);
      if (var4 > 65) {
         return var0 * var2;
      } else {
         boolean var5;
         if (var4 >= 64) {
            var5 = true;
         } else {
            var5 = false;
         }

         MathPreconditions.checkNoOverflow(var5, "checkedMultiply", var0, var2);
         long var11;
         int var6 = (var11 = var0 - 0L) == 0L ? 0 : (var11 < 0L ? -1 : 1);
         boolean var10;
         if (var6 >= 0) {
            var10 = true;
         } else {
            var10 = false;
         }

         boolean var7;
         if (var2 != Long.MIN_VALUE) {
            var7 = true;
         } else {
            var7 = false;
         }

         MathPreconditions.checkNoOverflow(var10 | var7, "checkedMultiply", var0, var2);
         long var8 = var0 * var2;
         if (var6 != 0 && var8 / var0 != var2) {
            var5 = false;
         } else {
            var5 = true;
         }

         MathPreconditions.checkNoOverflow(var5, "checkedMultiply", var0, var2);
         return var8;
      }
   }

   public static long checkedPow(long var0, int var2) {
      MathPreconditions.checkNonNegative("exponent", var2);
      boolean var3 = false;
      boolean var4 = false;
      boolean var5;
      if (var0 >= -2L) {
         var5 = true;
      } else {
         var5 = false;
      }

      boolean var6;
      if (var0 <= 2L) {
         var6 = true;
      } else {
         var6 = false;
      }

      long var7 = 1L;
      if (var5 & var6) {
         int var13 = (int)var0;
         if (var13 != -2) {
            if (var13 != -1) {
               if (var13 != 0) {
                  if (var13 != 1) {
                     if (var13 == 2) {
                        if (var2 < 63) {
                           var4 = true;
                        }

                        MathPreconditions.checkNoOverflow(var4, "checkedPow", var0, (long)var2);
                        return 1L << var2;
                     } else {
                        throw new AssertionError();
                     }
                  } else {
                     return 1L;
                  }
               } else {
                  if (var2 != 0) {
                     var7 = 0L;
                  }

                  return var7;
               }
            } else {
               if ((var2 & 1) != 0) {
                  var7 = -1L;
               }

               return var7;
            }
         } else {
            var4 = var3;
            if (var2 < 64) {
               var4 = true;
            }

            MathPreconditions.checkNoOverflow(var4, "checkedPow", var0, (long)var2);
            if ((var2 & 1) == 0) {
               var0 = 1L << var2;
            } else {
               var0 = -1L << var2;
            }

            return var0;
         }
      } else {
         var7 = 1L;

         long var11;
         for(long var9 = var0; var2 != 0; var9 = var11) {
            if (var2 == 1) {
               return checkedMultiply(var7, var9);
            }

            var0 = var7;
            if ((var2 & 1) != 0) {
               var0 = checkedMultiply(var7, var9);
            }

            var2 >>= 1;
            var11 = var9;
            if (var2 > 0) {
               if (-3037000499L <= var9 && var9 <= 3037000499L) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               MathPreconditions.checkNoOverflow(var4, "checkedPow", var9, (long)var2);
               var11 = var9 * var9;
            }

            var7 = var0;
         }

         return var7;
      }
   }

   public static long checkedSubtract(long var0, long var2) {
      long var4 = var0 - var2;
      boolean var6 = true;
      boolean var7;
      if ((var0 ^ var2) >= 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      if ((var0 ^ var4) < 0L) {
         var6 = false;
      }

      MathPreconditions.checkNoOverflow(var7 | var6, "checkedSubtract", var0, var2);
      return var4;
   }

   public static long divide(long var0, long var2, RoundingMode var4) {
      Preconditions.checkNotNull(var4);
      long var5 = var0 / var2;
      long var7 = var0 - var2 * var5;
      long var16;
      int var9 = (var16 = var7 - 0L) == 0L ? 0 : (var16 < 0L ? -1 : 1);
      if (var9 == 0) {
         return var5;
      } else {
         int var14;
         boolean var15;
         label56: {
            int var10 = (int)((var0 ^ var2) >> 63);
            boolean var11 = true;
            boolean var12 = true;
            boolean var13 = true;
            var14 = var10 | 1;
            var15 = var12;
            switch(null.$SwitchMap$java$math$RoundingMode[var4.ordinal()]) {
            case 1:
               if (var9 != 0) {
                  var11 = false;
               }

               MathPreconditions.checkRoundingUnnecessary(var11);
            case 2:
               break;
            case 3:
               if (var14 < 0) {
                  var15 = var12;
                  break label56;
               }
               break;
            case 4:
               break label56;
            case 5:
               if (var14 > 0) {
                  var15 = var12;
                  break label56;
               }
               break;
            case 6:
            case 7:
            case 8:
               var0 = Math.abs(var7);
               long var17;
               var10 = (var17 = var0 - (Math.abs(var2) - var0) - 0L) == 0L ? 0 : (var17 < 0L ? -1 : 1);
               if (var10 == 0) {
                  if (var4 == RoundingMode.HALF_UP) {
                     var15 = true;
                  } else {
                     var15 = false;
                  }

                  if (var4 == RoundingMode.HALF_EVEN) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  if ((1L & var5) == 0L) {
                     var13 = false;
                  }

                  var15 |= var13 & var12;
                  break label56;
               }

               if (var10 > 0) {
                  var15 = var12;
                  break label56;
               }
               break;
            default:
               throw new AssertionError();
            }

            var15 = false;
         }

         var0 = var5;
         if (var15) {
            var0 = var5 + (long)var14;
         }

         return var0;
      }
   }

   public static long factorial(int var0) {
      MathPreconditions.checkNonNegative("n", var0);
      long[] var1 = factorials;
      long var2;
      if (var0 < var1.length) {
         var2 = var1[var0];
      } else {
         var2 = Long.MAX_VALUE;
      }

      return var2;
   }

   static boolean fitsInInt(long var0) {
      boolean var2;
      if ((long)((int)var0) == var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static long floorPowerOfTwo(long var0) {
      MathPreconditions.checkPositive("x", var0);
      return 1L << 63 - Long.numberOfLeadingZeros(var0);
   }

   public static long gcd(long var0, long var2) {
      MathPreconditions.checkNonNegative("a", var0);
      MathPreconditions.checkNonNegative("b", var2);
      if (var0 == 0L) {
         return var2;
      } else if (var2 == 0L) {
         return var0;
      } else {
         int var4 = Long.numberOfTrailingZeros(var0);
         long var5 = var0 >> var4;
         int var7 = Long.numberOfTrailingZeros(var2);
         var0 = var2 >> var7;

         for(var2 = var5; var2 != var0; var2 = var5 >> Long.numberOfTrailingZeros(var5)) {
            var5 = var2 - var0;
            var2 = var5 >> 63 & var5;
            var5 = var5 - var2 - var2;
            var0 += var2;
         }

         return var2 << Math.min(var4, var7);
      }
   }

   public static boolean isPowerOfTwo(long var0) {
      boolean var2 = true;
      boolean var3;
      if (var0 > 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      if ((var0 & var0 - 1L) != 0L) {
         var2 = false;
      }

      return var3 & var2;
   }

   public static boolean isPrime(long var0) {
      long var6;
      int var2 = (var6 = var0 - 2L) == 0L ? 0 : (var6 < 0L ? -1 : 1);
      if (var2 < 0) {
         MathPreconditions.checkNonNegative("n", var0);
         return false;
      } else if (var2 != 0 && var0 != 3L && var0 != 5L && var0 != 7L && var0 != 11L && var0 != 13L) {
         if ((-545925251 & 1 << (int)(var0 % 30L)) != 0) {
            return false;
         } else if (var0 % 7L != 0L && var0 % 11L != 0L && var0 % 13L != 0L) {
            if (var0 < 289L) {
               return true;
            } else {
               long[][] var3 = millerRabinBaseSets;
               int var4 = var3.length;

               for(var2 = 0; var2 < var4; ++var2) {
                  long[] var5 = var3[var2];
                  if (var0 <= var5[0]) {
                     for(var2 = 1; var2 < var5.length; ++var2) {
                        if (!LongMath.MillerRabinTester.test(var5[var2], var0)) {
                           return false;
                        }
                     }

                     return true;
                  }
               }

               throw new AssertionError();
            }
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   static int lessThanBranchFree(long var0, long var2) {
      return (int)(var0 - var2 >>> 63);
   }

   public static int log10(long var0, RoundingMode var2) {
      MathPreconditions.checkPositive("x", var0);
      int var3 = log10Floor(var0);
      long var4 = powersOf10[var3];
      int var6;
      switch(null.$SwitchMap$java$math$RoundingMode[var2.ordinal()]) {
      case 1:
         boolean var7;
         if (var0 == var4) {
            var7 = true;
         } else {
            var7 = false;
         }

         MathPreconditions.checkRoundingUnnecessary(var7);
      case 2:
      case 3:
         return var3;
      case 4:
      case 5:
         var6 = lessThanBranchFree(var4, var0);
         break;
      case 6:
      case 7:
      case 8:
         var6 = lessThanBranchFree(halfPowersOf10[var3], var0);
         break;
      default:
         throw new AssertionError();
      }

      return var3 + var6;
   }

   static int log10Floor(long var0) {
      byte var2 = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(var0)];
      return var2 - lessThanBranchFree(var0, powersOf10[var2]);
   }

   public static int log2(long var0, RoundingMode var2) {
      MathPreconditions.checkPositive("x", var0);
      switch(null.$SwitchMap$java$math$RoundingMode[var2.ordinal()]) {
      case 1:
         MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(var0));
      case 2:
      case 3:
         return 63 - Long.numberOfLeadingZeros(var0);
      case 4:
      case 5:
         return 64 - Long.numberOfLeadingZeros(var0 - 1L);
      case 6:
      case 7:
      case 8:
         int var3 = Long.numberOfLeadingZeros(var0);
         return 63 - var3 + lessThanBranchFree(-5402926248376769404L >>> var3, var0);
      default:
         throw new AssertionError("impossible");
      }
   }

   public static long mean(long var0, long var2) {
      return (var0 & var2) + ((var0 ^ var2) >> 1);
   }

   public static int mod(long var0, int var2) {
      return (int)mod(var0, (long)var2);
   }

   public static long mod(long var0, long var2) {
      if (var2 > 0L) {
         var0 %= var2;
         if (var0 < 0L) {
            var0 += var2;
         }

         return var0;
      } else {
         throw new ArithmeticException("Modulus must be positive");
      }
   }

   static long multiplyFraction(long var0, long var2, long var4) {
      if (var0 == 1L) {
         return var2 / var4;
      } else {
         long var6 = gcd(var0, var4);
         return var0 / var6 * (var2 / (var4 / var6));
      }
   }

   public static long pow(long var0, int var2) {
      MathPreconditions.checkNonNegative("exponent", var2);
      long var3 = 1L;
      if (-2L <= var0 && var0 <= 2L) {
         int var5 = (int)var0;
         var0 = 0L;
         if (var5 != -2) {
            if (var5 != -1) {
               if (var5 != 0) {
                  if (var5 != 1) {
                     if (var5 == 2) {
                        if (var2 < 64) {
                           var0 = 1L << var2;
                        }

                        return var0;
                     } else {
                        throw new AssertionError();
                     }
                  } else {
                     return 1L;
                  }
               } else {
                  if (var2 != 0) {
                     var3 = 0L;
                  }

                  return var3;
               }
            } else {
               if ((var2 & 1) != 0) {
                  var3 = -1L;
               }

               return var3;
            }
         } else if (var2 < 64) {
            if ((var2 & 1) == 0) {
               var0 = 1L << var2;
            } else {
               var0 = -(1L << var2);
            }

            return var0;
         } else {
            return 0L;
         }
      } else {
         var3 = 1L;

         long var6;
         while(true) {
            var6 = var3;
            if (var2 == 0) {
               break;
            }

            if (var2 == 1) {
               var6 = var3 * var0;
               break;
            }

            if ((var2 & 1) == 0) {
               var6 = 1L;
            } else {
               var6 = var0;
            }

            var3 *= var6;
            var0 *= var0;
            var2 >>= 1;
         }

         return var6;
      }
   }

   public static long saturatedAdd(long var0, long var2) {
      long var4 = var0 + var2;
      boolean var6 = true;
      boolean var7;
      if ((var2 ^ var0) < 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      if ((var0 ^ var4) < 0L) {
         var6 = false;
      }

      return var7 | var6 ? var4 : (var4 >>> 63 ^ 1L) + Long.MAX_VALUE;
   }

   public static long saturatedMultiply(long var0, long var2) {
      int var4 = Long.numberOfLeadingZeros(var0) + Long.numberOfLeadingZeros(var0) + Long.numberOfLeadingZeros(var2) + Long.numberOfLeadingZeros(var2);
      if (var4 > 65) {
         return var0 * var2;
      } else {
         long var5 = ((var0 ^ var2) >>> 63) + Long.MAX_VALUE;
         boolean var7 = true;
         boolean var12;
         if (var4 < 64) {
            var12 = true;
         } else {
            var12 = false;
         }

         long var13;
         int var8 = (var13 = var0 - 0L) == 0L ? 0 : (var13 < 0L ? -1 : 1);
         boolean var9;
         if (var8 < 0) {
            var9 = true;
         } else {
            var9 = false;
         }

         if (var2 != Long.MIN_VALUE) {
            var7 = false;
         }

         if (var12 | var7 & var9) {
            return var5;
         } else {
            long var10 = var0 * var2;
            return var8 != 0 && var10 / var0 != var2 ? var5 : var10;
         }
      }
   }

   public static long saturatedPow(long var0, int var2) {
      MathPreconditions.checkNonNegative("exponent", var2);
      boolean var3;
      if (var0 >= -2L) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var4;
      if (var0 <= 2L) {
         var4 = true;
      } else {
         var4 = false;
      }

      long var5 = 1L;
      if (var3 & var4) {
         int var16 = (int)var0;
         if (var16 != -2) {
            if (var16 != -1) {
               if (var16 != 0) {
                  if (var16 != 1) {
                     if (var16 == 2) {
                        return var2 >= 63 ? Long.MAX_VALUE : 1L << var2;
                     } else {
                        throw new AssertionError();
                     }
                  } else {
                     return 1L;
                  }
               } else {
                  if (var2 != 0) {
                     var5 = 0L;
                  }

                  return var5;
               }
            } else {
               if ((var2 & 1) != 0) {
                  var5 = -1L;
               }

               return var5;
            }
         } else if (var2 >= 64) {
            return (long)(var2 & 1) + Long.MAX_VALUE;
         } else {
            if ((var2 & 1) == 0) {
               var0 = 1L << var2;
            } else {
               var0 = -1L << var2;
            }

            return var0;
         }
      } else {
         long var7 = (long)(var2 & 1);
         long var9 = var0;

         while(true) {
            long var11 = var9;
            if (var2 == 0) {
               return var5;
            }

            if (var2 == 1) {
               return saturatedMultiply(var5, var9);
            }

            long var13 = var5;
            if ((var2 & 1) != 0) {
               var13 = saturatedMultiply(var5, var9);
            }

            int var17 = var2 >> 1;
            var5 = var13;
            var9 = var9;
            var2 = var17;
            if (var17 > 0) {
               boolean var15;
               if (-3037000499L > var11) {
                  var15 = true;
               } else {
                  var15 = false;
               }

               if (var11 > 3037000499L) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var15 | var3) {
                  return (var0 >>> 63 & var7) + Long.MAX_VALUE;
               }

               var9 = var11 * var11;
               var5 = var13;
               var2 = var17;
            }
         }
      }
   }

   public static long saturatedSubtract(long var0, long var2) {
      long var4 = var0 - var2;
      boolean var6 = true;
      boolean var7;
      if ((var2 ^ var0) >= 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      if ((var0 ^ var4) < 0L) {
         var6 = false;
      }

      return var7 | var6 ? var4 : (var4 >>> 63 ^ 1L) + Long.MAX_VALUE;
   }

   public static long sqrt(long var0, RoundingMode var2) {
      MathPreconditions.checkNonNegative("x", var0);
      if (fitsInInt(var0)) {
         return (long)IntMath.sqrt((int)var0, var2);
      } else {
         long var3 = (long)Math.sqrt((double)var0);
         long var5 = var3 * var3;
         int var7 = null.$SwitchMap$java$math$RoundingMode[var2.ordinal()];
         boolean var8 = true;
         byte var9 = 1;
         long var10;
         switch(var7) {
         case 1:
            if (var5 != var0) {
               var8 = false;
            }

            MathPreconditions.checkRoundingUnnecessary(var8);
            return var3;
         case 2:
         case 3:
            var10 = var3;
            if (var0 < var5) {
               var10 = var3 - 1L;
            }

            return var10;
         case 4:
         case 5:
            var10 = var3;
            if (var0 > var5) {
               var10 = var3 + 1L;
            }

            return var10;
         case 6:
         case 7:
         case 8:
            if (var0 >= var5) {
               var9 = 0;
            }

            var3 -= (long)var9;
            return var3 + (long)lessThanBranchFree(var3 * var3 + var3, var0);
         default:
            throw new AssertionError();
         }
      }
   }

   private static enum MillerRabinTester {
      LARGE,
      SMALL {
         long mulMod(long var1, long var3, long var5) {
            return var1 * var3 % var5;
         }

         long squareMod(long var1, long var3) {
            return var1 * var1 % var3;
         }
      };

      static {
         LongMath.MillerRabinTester var0 = new LongMath.MillerRabinTester("LARGE", 1) {
            private long plusMod(long var1, long var3, long var5) {
               long var7 = var1 + var3;
               long var9 = var7;
               if (var1 >= var5 - var3) {
                  var9 = var7 - var5;
               }

               return var9;
            }

            private long times2ToThe32Mod(long var1, long var3) {
               int var5 = 32;

               int var6;
               long var7;
               do {
                  var6 = Math.min(var5, Long.numberOfLeadingZeros(var1));
                  var7 = UnsignedLongs.remainder(var1 << var6, var3);
                  var6 = var5 - var6;
                  var5 = var6;
                  var1 = var7;
               } while(var6 > 0);

               return var7;
            }

            long mulMod(long var1, long var3, long var5) {
               long var7 = var1 >>> 32;
               long var9 = var3 >>> 32;
               long var11 = var1 & 4294967295L;
               long var13 = var3 & 4294967295L;
               var3 = this.times2ToThe32Mod(var7 * var9, var5) + var7 * var13;
               var1 = var3;
               if (var3 < 0L) {
                  var1 = UnsignedLongs.remainder(var3, var5);
               }

               Long.signum(var11);
               return this.plusMod(this.times2ToThe32Mod(var1 + var9 * var11, var5), UnsignedLongs.remainder(var11 * var13, var5), var5);
            }

            long squareMod(long var1, long var3) {
               long var5 = var1 >>> 32;
               long var7 = var1 & 4294967295L;
               long var9 = this.times2ToThe32Mod(var5 * var5, var3);
               var5 = var5 * var7 * 2L;
               var1 = var5;
               if (var5 < 0L) {
                  var1 = UnsignedLongs.remainder(var5, var3);
               }

               return this.plusMod(this.times2ToThe32Mod(var9 + var1, var3), UnsignedLongs.remainder(var7 * var7, var3), var3);
            }
         };
         LARGE = var0;
      }

      private MillerRabinTester() {
      }

      // $FF: synthetic method
      MillerRabinTester(Object var3) {
         this();
      }

      private long powMod(long var1, long var3, long var5) {
         long var7;
         long var9;
         for(var7 = 1L; var3 != 0L; var7 = var9) {
            var9 = var7;
            if ((var3 & 1L) != 0L) {
               var9 = this.mulMod(var7, var1, var5);
            }

            var1 = this.squareMod(var1, var5);
            var3 >>= 1;
         }

         return var7;
      }

      static boolean test(long var0, long var2) {
         LongMath.MillerRabinTester var4;
         if (var2 <= 3037000499L) {
            var4 = SMALL;
         } else {
            var4 = LARGE;
         }

         return var4.testWitness(var0, var2);
      }

      private boolean testWitness(long var1, long var3) {
         long var5 = var3 - 1L;
         int var7 = Long.numberOfTrailingZeros(var5);
         var1 %= var3;
         if (var1 == 0L) {
            return true;
         } else {
            var1 = this.powMod(var1, var5 >> var7, var3);
            if (var1 == 1L) {
               return true;
            } else {
               for(int var8 = 0; var1 != var5; var1 = this.squareMod(var1, var3)) {
                  ++var8;
                  if (var8 == var7) {
                     return false;
                  }
               }

               return true;
            }
         }
      }

      abstract long mulMod(long var1, long var3, long var5);

      abstract long squareMod(long var1, long var3);
   }
}
