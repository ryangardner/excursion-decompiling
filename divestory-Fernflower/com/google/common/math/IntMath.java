package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;

public final class IntMath {
   static final int FLOOR_SQRT_MAX_INT = 46340;
   static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;
   static final int MAX_SIGNED_POWER_OF_TWO = 1073741824;
   static int[] biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};
   private static final int[] factorials = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};
   static final int[] halfPowersOf10 = new int[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
   static final byte[] maxLog10ForLeadingZeros = new byte[]{9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};
   static final int[] powersOf10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};

   private IntMath() {
   }

   public static int binomial(int var0, int var1) {
      MathPreconditions.checkNonNegative("n", var0);
      MathPreconditions.checkNonNegative("k", var1);
      byte var2 = 0;
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

      int[] var5 = biggestBinomials;
      if (var4 < var5.length && var0 <= var5[var4]) {
         if (var4 == 0) {
            return 1;
         } else {
            var1 = var0;
            if (var4 != 1) {
               long var6 = 1L;

               long var8;
               for(var1 = var2; var1 < var4; var6 = var6 * var8 / (long)var1) {
                  var8 = (long)(var0 - var1);
                  ++var1;
               }

               var1 = (int)var6;
            }

            return var1;
         }
      } else {
         return Integer.MAX_VALUE;
      }
   }

   public static int ceilingPowerOfTwo(int var0) {
      MathPreconditions.checkPositive("x", var0);
      if (var0 <= 1073741824) {
         return 1 << -Integer.numberOfLeadingZeros(var0 - 1);
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("ceilingPowerOfTwo(");
         var1.append(var0);
         var1.append(") not representable as an int");
         throw new ArithmeticException(var1.toString());
      }
   }

   public static int checkedAdd(int var0, int var1) {
      long var2 = (long)var0 + (long)var1;
      int var4 = (int)var2;
      boolean var5;
      if (var2 == (long)var4) {
         var5 = true;
      } else {
         var5 = false;
      }

      MathPreconditions.checkNoOverflow(var5, "checkedAdd", var0, var1);
      return var4;
   }

   public static int checkedMultiply(int var0, int var1) {
      long var2 = (long)var0 * (long)var1;
      int var4 = (int)var2;
      boolean var5;
      if (var2 == (long)var4) {
         var5 = true;
      } else {
         var5 = false;
      }

      MathPreconditions.checkNoOverflow(var5, "checkedMultiply", var0, var1);
      return var4;
   }

   public static int checkedPow(int var0, int var1) {
      MathPreconditions.checkNonNegative("exponent", var1);
      byte var2 = -1;
      byte var3 = 0;
      boolean var4 = false;
      boolean var5 = false;
      if (var0 != -2) {
         if (var0 != -1) {
            if (var0 != 0) {
               if (var0 != 1) {
                  if (var0 != 2) {
                     int var11 = 1;
                     int var9 = var1;

                     while(var9 != 0) {
                        if (var9 == 1) {
                           return checkedMultiply(var11, var0);
                        }

                        var1 = var11;
                        if ((var9 & 1) != 0) {
                           var1 = checkedMultiply(var11, var0);
                        }

                        int var6 = var9 >> 1;
                        var11 = var1;
                        var9 = var6;
                        if (var6 > 0) {
                           boolean var10;
                           if (-46340 <= var0) {
                              var10 = true;
                           } else {
                              var10 = false;
                           }

                           boolean var12;
                           if (var0 <= 46340) {
                              var12 = true;
                           } else {
                              var12 = false;
                           }

                           MathPreconditions.checkNoOverflow(var10 & var12, "checkedPow", var0, var6);
                           var0 *= var0;
                           var11 = var1;
                           var9 = var6;
                        }
                     }

                     return var11;
                  } else {
                     if (var1 < 31) {
                        var5 = true;
                     }

                     MathPreconditions.checkNoOverflow(var5, "checkedPow", var0, var1);
                     return 1 << var1;
                  }
               } else {
                  return 1;
               }
            } else {
               byte var8 = var3;
               if (var1 == 0) {
                  var8 = 1;
               }

               return var8;
            }
         } else {
            byte var7 = var2;
            if ((var1 & 1) == 0) {
               var7 = 1;
            }

            return var7;
         }
      } else {
         var5 = var4;
         if (var1 < 32) {
            var5 = true;
         }

         MathPreconditions.checkNoOverflow(var5, "checkedPow", var0, var1);
         if ((var1 & 1) == 0) {
            var0 = 1 << var1;
         } else {
            var0 = -1 << var1;
         }

         return var0;
      }
   }

   public static int checkedSubtract(int var0, int var1) {
      long var2 = (long)var0 - (long)var1;
      int var4 = (int)var2;
      boolean var5;
      if (var2 == (long)var4) {
         var5 = true;
      } else {
         var5 = false;
      }

      MathPreconditions.checkNoOverflow(var5, "checkedSubtract", var0, var1);
      return var4;
   }

   public static int divide(int var0, int var1, RoundingMode var2) {
      Preconditions.checkNotNull(var2);
      if (var1 != 0) {
         int var3 = var0 / var1;
         int var4 = var0 - var1 * var3;
         if (var4 == 0) {
            return var3;
         } else {
            int var7;
            boolean var8;
            label57: {
               boolean var5 = true;
               boolean var6 = true;
               var7 = (var0 ^ var1) >> 31 | 1;
               var8 = var5;
               switch(null.$SwitchMap$java$math$RoundingMode[var2.ordinal()]) {
               case 1:
                  if (var4 != 0) {
                     var6 = false;
                  }

                  MathPreconditions.checkRoundingUnnecessary(var6);
               case 2:
                  break;
               case 3:
                  if (var7 < 0) {
                     var8 = var5;
                     break label57;
                  }
                  break;
               case 4:
                  break label57;
               case 5:
                  if (var7 > 0) {
                     var8 = var5;
                     break label57;
                  }
                  break;
               case 6:
               case 7:
               case 8:
                  var0 = Math.abs(var4);
                  var0 -= Math.abs(var1) - var0;
                  if (var0 == 0) {
                     var8 = var5;
                     if (var2 == RoundingMode.HALF_UP) {
                        break label57;
                     }

                     if (var2 == RoundingMode.HALF_EVEN) {
                        var8 = true;
                     } else {
                        var8 = false;
                     }

                     boolean var9;
                     if ((var3 & 1) != 0) {
                        var9 = true;
                     } else {
                        var9 = false;
                     }

                     if (var8 & var9) {
                        var8 = var5;
                        break label57;
                     }
                  } else if (var0 > 0) {
                     var8 = var5;
                     break label57;
                  }
                  break;
               default:
                  throw new AssertionError();
               }

               var8 = false;
            }

            var1 = var3;
            if (var8) {
               var1 = var3 + var7;
            }

            return var1;
         }
      } else {
         throw new ArithmeticException("/ by zero");
      }
   }

   public static int factorial(int var0) {
      MathPreconditions.checkNonNegative("n", var0);
      int[] var1 = factorials;
      if (var0 < var1.length) {
         var0 = var1[var0];
      } else {
         var0 = Integer.MAX_VALUE;
      }

      return var0;
   }

   public static int floorPowerOfTwo(int var0) {
      MathPreconditions.checkPositive("x", var0);
      return Integer.highestOneBit(var0);
   }

   public static int gcd(int var0, int var1) {
      MathPreconditions.checkNonNegative("a", var0);
      MathPreconditions.checkNonNegative("b", var1);
      if (var0 == 0) {
         return var1;
      } else if (var1 == 0) {
         return var0;
      } else {
         int var2 = Integer.numberOfTrailingZeros(var0);
         int var3 = var0 >> var2;
         int var4 = Integer.numberOfTrailingZeros(var1);
         var0 = var1 >> var4;

         for(var1 = var3; var1 != var0; var1 >>= Integer.numberOfTrailingZeros(var1)) {
            var1 -= var0;
            var3 = var1 >> 31 & var1;
            var1 = var1 - var3 - var3;
            var0 += var3;
         }

         return var1 << Math.min(var2, var4);
      }
   }

   public static boolean isPowerOfTwo(int var0) {
      boolean var1 = false;
      boolean var2;
      if (var0 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if ((var0 & var0 - 1) == 0) {
         var1 = true;
      }

      return var2 & var1;
   }

   public static boolean isPrime(int var0) {
      return LongMath.isPrime((long)var0);
   }

   static int lessThanBranchFree(int var0, int var1) {
      return var0 - var1 >>> 31;
   }

   public static int log10(int var0, RoundingMode var1) {
      MathPreconditions.checkPositive("x", var0);
      int var2 = log10Floor(var0);
      int var3 = powersOf10[var2];
      switch(null.$SwitchMap$java$math$RoundingMode[var1.ordinal()]) {
      case 1:
         boolean var4;
         if (var0 == var3) {
            var4 = true;
         } else {
            var4 = false;
         }

         MathPreconditions.checkRoundingUnnecessary(var4);
      case 2:
      case 3:
         return var2;
      case 4:
      case 5:
         var0 = lessThanBranchFree(var3, var0);
         break;
      case 6:
      case 7:
      case 8:
         var0 = lessThanBranchFree(halfPowersOf10[var2], var0);
         break;
      default:
         throw new AssertionError();
      }

      return var2 + var0;
   }

   private static int log10Floor(int var0) {
      byte var1 = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(var0)];
      return var1 - lessThanBranchFree(var0, powersOf10[var1]);
   }

   public static int log2(int var0, RoundingMode var1) {
      MathPreconditions.checkPositive("x", var0);
      switch(null.$SwitchMap$java$math$RoundingMode[var1.ordinal()]) {
      case 1:
         MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(var0));
      case 2:
      case 3:
         return 31 - Integer.numberOfLeadingZeros(var0);
      case 4:
      case 5:
         return 32 - Integer.numberOfLeadingZeros(var0 - 1);
      case 6:
      case 7:
      case 8:
         int var2 = Integer.numberOfLeadingZeros(var0);
         return 31 - var2 + lessThanBranchFree(-1257966797 >>> var2, var0);
      default:
         throw new AssertionError();
      }
   }

   public static int mean(int var0, int var1) {
      return (var0 & var1) + ((var0 ^ var1) >> 1);
   }

   public static int mod(int var0, int var1) {
      if (var1 > 0) {
         var0 %= var1;
         if (var0 < 0) {
            var0 += var1;
         }

         return var0;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Modulus ");
         var2.append(var1);
         var2.append(" must be > 0");
         throw new ArithmeticException(var2.toString());
      }
   }

   public static int pow(int var0, int var1) {
      MathPreconditions.checkNonNegative("exponent", var1);
      byte var2 = 0;
      byte var3 = 0;
      byte var4 = 1;
      if (var0 != -2) {
         if (var0 != -1) {
            if (var0 != 0) {
               if (var0 != 1) {
                  if (var0 != 2) {
                     int var8;
                     for(var8 = 1; var1 != 0; var1 >>= 1) {
                        if (var1 == 1) {
                           return var0 * var8;
                        }

                        int var7;
                        if ((var1 & 1) == 0) {
                           var7 = 1;
                        } else {
                           var7 = var0;
                        }

                        var8 *= var7;
                        var0 *= var0;
                     }

                     return var8;
                  } else {
                     var0 = var3;
                     if (var1 < 32) {
                        var0 = 1 << var1;
                     }

                     return var0;
                  }
               } else {
                  return 1;
               }
            } else {
               byte var6 = var2;
               if (var1 == 0) {
                  var6 = 1;
               }

               return var6;
            }
         } else {
            byte var5;
            if ((var1 & 1) == 0) {
               var5 = var4;
            } else {
               var5 = -1;
            }

            return var5;
         }
      } else if (var1 < 32) {
         if ((var1 & 1) == 0) {
            var0 = 1 << var1;
         } else {
            var0 = -(1 << var1);
         }

         return var0;
      } else {
         return 0;
      }
   }

   public static int saturatedAdd(int var0, int var1) {
      return Ints.saturatedCast((long)var0 + (long)var1);
   }

   public static int saturatedMultiply(int var0, int var1) {
      return Ints.saturatedCast((long)var0 * (long)var1);
   }

   public static int saturatedPow(int var0, int var1) {
      MathPreconditions.checkNonNegative("exponent", var1);
      byte var2 = -1;
      byte var3 = 1;
      if (var0 != -2) {
         if (var0 != -1) {
            if (var0 != 0) {
               if (var0 != 1) {
                  if (var0 != 2) {
                     int var4 = 1;
                     int var12 = var1;
                     int var10 = var0;

                     while(true) {
                        int var5 = var10;
                        if (var12 == 0) {
                           return var4;
                        }

                        if (var12 == 1) {
                           return saturatedMultiply(var4, var10);
                        }

                        int var6 = var4;
                        if ((var12 & 1) != 0) {
                           var6 = saturatedMultiply(var4, var10);
                        }

                        int var7 = var12 >> 1;
                        var4 = var6;
                        var10 = var10;
                        var12 = var7;
                        if (var7 > 0) {
                           boolean var11;
                           if (-46340 > var5) {
                              var11 = true;
                           } else {
                              var11 = false;
                           }

                           boolean var13;
                           if (var5 > 46340) {
                              var13 = true;
                           } else {
                              var13 = false;
                           }

                           if (var11 | var13) {
                              return (var0 >>> 31 & var1 & 1) + Integer.MAX_VALUE;
                           }

                           var10 = var5 * var5;
                           var4 = var6;
                           var12 = var7;
                        }
                     }
                  } else {
                     return var1 >= 31 ? Integer.MAX_VALUE : 1 << var1;
                  }
               } else {
                  return 1;
               }
            } else {
               byte var9;
               if (var1 == 0) {
                  var9 = var3;
               } else {
                  var9 = 0;
               }

               return var9;
            }
         } else {
            byte var8 = var2;
            if ((var1 & 1) == 0) {
               var8 = 1;
            }

            return var8;
         }
      } else if (var1 >= 32) {
         return (var1 & 1) + Integer.MAX_VALUE;
      } else {
         if ((var1 & 1) == 0) {
            var0 = 1 << var1;
         } else {
            var0 = -1 << var1;
         }

         return var0;
      }
   }

   public static int saturatedSubtract(int var0, int var1) {
      return Ints.saturatedCast((long)var0 - (long)var1);
   }

   public static int sqrt(int var0, RoundingMode var1) {
      MathPreconditions.checkNonNegative("x", var0);
      int var2 = sqrtFloor(var0);
      switch(null.$SwitchMap$java$math$RoundingMode[var1.ordinal()]) {
      case 1:
         boolean var3;
         if (var2 * var2 == var0) {
            var3 = true;
         } else {
            var3 = false;
         }

         MathPreconditions.checkRoundingUnnecessary(var3);
      case 2:
      case 3:
         return var2;
      case 4:
      case 5:
         var0 = lessThanBranchFree(var2 * var2, var0);
         break;
      case 6:
      case 7:
      case 8:
         var0 = lessThanBranchFree(var2 * var2 + var2, var0);
         break;
      default:
         throw new AssertionError();
      }

      return var2 + var0;
   }

   private static int sqrtFloor(int var0) {
      return (int)Math.sqrt((double)var0);
   }
}
