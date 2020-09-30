/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.math.MathPreconditions;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;

public final class IntMath {
    static final int FLOOR_SQRT_MAX_INT = 46340;
    static final int MAX_POWER_OF_SQRT2_UNSIGNED = -1257966797;
    static final int MAX_SIGNED_POWER_OF_TWO = 1073741824;
    static int[] biggestBinomials;
    private static final int[] factorials;
    static final int[] halfPowersOf10;
    static final byte[] maxLog10ForLeadingZeros;
    static final int[] powersOf10;

    static {
        maxLog10ForLeadingZeros = new byte[]{9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0, 0};
        powersOf10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
        halfPowersOf10 = new int[]{3, 31, 316, 3162, 31622, 316227, 3162277, 31622776, 316227766, Integer.MAX_VALUE};
        factorials = new int[]{1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};
        biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 65536, 2345, 477, 193, 110, 75, 58, 49, 43, 39, 37, 35, 34, 34, 33};
    }

    private IntMath() {
    }

    public static int binomial(int n, int n2) {
        int[] arrn;
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        int n3 = 0;
        boolean bl = n2 <= n;
        Preconditions.checkArgument(bl, "k (%s) > n (%s)", n2, n);
        int n4 = n2;
        if (n2 > n >> 1) {
            n4 = n - n2;
        }
        if (n4 >= (arrn = biggestBinomials).length) return Integer.MAX_VALUE;
        if (n > arrn[n4]) {
            return Integer.MAX_VALUE;
        }
        if (n4 == 0) return 1;
        n2 = n;
        if (n4 == 1) return n2;
        long l = 1L;
        n2 = n3;
        while (n2 < n4) {
            long l2 = n - n2;
            l = l * l2 / (long)(++n2);
        }
        return (int)l;
    }

    public static int ceilingPowerOfTwo(int n) {
        MathPreconditions.checkPositive("x", n);
        if (n <= 1073741824) {
            return 1 << -Integer.numberOfLeadingZeros(n - 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ceilingPowerOfTwo(");
        stringBuilder.append(n);
        stringBuilder.append(") not representable as an int");
        throw new ArithmeticException(stringBuilder.toString());
    }

    public static int checkedAdd(int n, int n2) {
        long l = (long)n + (long)n2;
        int n3 = (int)l;
        boolean bl = l == (long)n3;
        MathPreconditions.checkNoOverflow(bl, "checkedAdd", n, n2);
        return n3;
    }

    public static int checkedMultiply(int n, int n2) {
        long l = (long)n * (long)n2;
        int n3 = (int)l;
        boolean bl = l == (long)n3;
        MathPreconditions.checkNoOverflow(bl, "checkedMultiply", n, n2);
        return n3;
    }

    public static int checkedPow(int n, int n2) {
        MathPreconditions.checkNonNegative("exponent", n2);
        int n3 = -1;
        int n4 = 0;
        boolean bl = false;
        boolean bl2 = false;
        if (n != -2) {
            if (n == -1) {
                n = n3;
                if ((n2 & 1) != 0) return n;
                return 1;
            }
            if (n == 0) {
                n = n4;
                if (n2 != 0) return n;
                return 1;
            }
            if (n == 1) return 1;
            if (n == 2) {
                if (n2 < 31) {
                    bl2 = true;
                }
                MathPreconditions.checkNoOverflow(bl2, "checkedPow", n, n2);
                return 1 << n2;
            }
        } else {
            bl2 = bl;
            if (n2 < 32) {
                bl2 = true;
            }
            MathPreconditions.checkNoOverflow(bl2, "checkedPow", n, n2);
            if ((n2 & 1) != 0) return -1 << n2;
            return 1 << n2;
        }
        n4 = 1;
        n3 = n2;
        while (n3 != 0) {
            if (n3 == 1) return IntMath.checkedMultiply(n4, n);
            n2 = n4;
            if ((n3 & 1) != 0) {
                n2 = IntMath.checkedMultiply(n4, n);
            }
            int n5 = n3 >> 1;
            n4 = n2;
            n3 = n5;
            if (n5 <= 0) continue;
            n3 = -46340 <= n ? 1 : 0;
            n4 = n <= 46340 ? 1 : 0;
            MathPreconditions.checkNoOverflow((n3 & n4) != 0, "checkedPow", n, n5);
            n *= n;
            n4 = n2;
            n3 = n5;
        }
        return n4;
    }

    public static int checkedSubtract(int n, int n2) {
        long l = (long)n - (long)n2;
        int n3 = (int)l;
        boolean bl = l == (long)n3;
        MathPreconditions.checkNoOverflow(bl, "checkedSubtract", n, n2);
        return n3;
    }

    /*
     * Unable to fully structure code
     */
    public static int divide(int var0, int var1_1, RoundingMode var2_2) {
        Preconditions.checkNotNull(var2_2);
        if (var1_1 == 0) throw new ArithmeticException("/ by zero");
        var3_3 = var0 / var1_1;
        var4_4 = var0 - var1_1 * var3_3;
        if (var4_4 == 0) {
            return var3_3;
        }
        var5_5 = 1;
        var6_6 = true;
        var7_7 = (var0 ^ var1_1) >> 31 | 1;
        var0 = var5_5;
        switch (1.$SwitchMap$java$math$RoundingMode[var2_2.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                var0 = Math.abs(var4_4);
                var0 -= Math.abs(var1_1) - var0;
                if (var0 == 0) {
                    var0 = var5_5;
                    if (var2_2 == RoundingMode.HALF_UP) break;
                    var0 = var2_2 == RoundingMode.HALF_EVEN ? 1 : 0;
                    if ((var0 & (var1_1 = (var3_3 & 1) != 0 ? 1 : 0)) != 0) {
                        var0 = var5_5;
                        break;
                    }
                } else if (var0 > 0) {
                    var0 = var5_5;
                    break;
                }
                ** GOTO lbl44
            }
            case 5: {
                if (var7_7 > 0) {
                    var0 = var5_5;
                    break;
                }
                ** GOTO lbl44
            }
            case 3: {
                if (var7_7 < 0) {
                    var0 = var5_5;
                    break;
                }
                ** GOTO lbl44
            }
            case 1: {
                if (var4_4 != 0) {
                    var6_6 = false;
                }
                MathPreconditions.checkRoundingUnnecessary(var6_6);
            }
lbl44: // 6 sources:
            case 2: {
                var0 = 0;
            }
            case 4: 
        }
        var1_1 = var3_3;
        if (var0 == 0) return var1_1;
        return var3_3 + var7_7;
    }

    public static int factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        int[] arrn = factorials;
        if (n >= arrn.length) return Integer.MAX_VALUE;
        return arrn[n];
    }

    public static int floorPowerOfTwo(int n) {
        MathPreconditions.checkPositive("x", n);
        return Integer.highestOneBit(n);
    }

    public static int gcd(int n, int n2) {
        MathPreconditions.checkNonNegative("a", n);
        MathPreconditions.checkNonNegative("b", n2);
        if (n == 0) {
            return n2;
        }
        if (n2 == 0) {
            return n;
        }
        int n3 = Integer.numberOfTrailingZeros(n);
        int n4 = n >> n3;
        int n5 = Integer.numberOfTrailingZeros(n2);
        n = n2 >> n5;
        n2 = n4;
        while (n2 != n) {
            n4 = (n2 -= n) >> 31 & n2;
            n2 = n2 - n4 - n4;
            n += n4;
            n2 >>= Integer.numberOfTrailingZeros(n2);
        }
        return n2 << Math.min(n3, n5);
    }

    public static boolean isPowerOfTwo(int n) {
        boolean bl = false;
        boolean bl2 = n > 0;
        if ((n & n - 1) != 0) return bl2 & bl;
        bl = true;
        return bl2 & bl;
    }

    public static boolean isPrime(int n) {
        return LongMath.isPrime(n);
    }

    static int lessThanBranchFree(int n, int n2) {
        return n - n2 >>> 31;
    }

    public static int log10(int n, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", n);
        int n2 = IntMath.log10Floor(n);
        int n3 = powersOf10[n2];
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                n = IntMath.lessThanBranchFree(halfPowersOf10[n2], n);
                return n2 + n;
            }
            case 4: 
            case 5: {
                n = IntMath.lessThanBranchFree(n3, n);
                return n2 + n;
            }
            case 1: {
                boolean bl = n == n3;
                MathPreconditions.checkRoundingUnnecessary(bl);
            }
            case 2: 
            case 3: 
        }
        return n2;
    }

    private static int log10Floor(int n) {
        byte by = maxLog10ForLeadingZeros[Integer.numberOfLeadingZeros(n)];
        return by - IntMath.lessThanBranchFree(n, powersOf10[by]);
    }

    public static int log2(int n, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", n);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                int n2 = Integer.numberOfLeadingZeros(n);
                return 31 - n2 + IntMath.lessThanBranchFree(-1257966797 >>> n2, n);
            }
            case 4: 
            case 5: {
                return 32 - Integer.numberOfLeadingZeros(n - 1);
            }
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(IntMath.isPowerOfTwo(n));
            }
            case 2: 
            case 3: 
        }
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    public static int mean(int n, int n2) {
        return (n & n2) + ((n ^ n2) >> 1);
    }

    public static int mod(int n, int n2) {
        if (n2 <= 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Modulus ");
            stringBuilder.append(n2);
            stringBuilder.append(" must be > 0");
            throw new ArithmeticException(stringBuilder.toString());
        }
        if ((n %= n2) >= 0) {
            return n;
        }
        n += n2;
        return n;
    }

    /*
     * Unable to fully structure code
     */
    public static int pow(int var0, int var1_1) {
        MathPreconditions.checkNonNegative("exponent", var1_1);
        var2_2 = 0;
        var3_3 = 0;
        var4_4 = 1;
        if (var0 == -2) ** GOTO lbl20
        if (var0 != -1) {
            if (var0 == 0) {
                var0 = var2_2;
                if (var1_1 != 0) return var0;
                return 1;
            }
            if (var0 == 1) return 1;
            if (var0 == 2) {
                var0 = var3_3;
                if (var1_1 >= 32) return var0;
                return 1 << var1_1;
            }
        } else {
            if ((var1_1 & 1) != 0) return -1;
            return var4_4;
lbl20: // 1 sources:
            if (var1_1 >= 32) return 0;
            if ((var1_1 & 1) != 0) return -(1 << var1_1);
            return 1 << var1_1;
        }
        var4_4 = 1;
        while (var1_1 != 0) {
            if (var1_1 == 1) return var0 * var4_4;
            var3_3 = (var1_1 & 1) == 0 ? 1 : var0;
            var4_4 *= var3_3;
            var0 *= var0;
            var1_1 >>= 1;
        }
        return var4_4;
    }

    public static int saturatedAdd(int n, int n2) {
        return Ints.saturatedCast((long)n + (long)n2);
    }

    public static int saturatedMultiply(int n, int n2) {
        return Ints.saturatedCast((long)n * (long)n2);
    }

    /*
     * Unable to fully structure code
     */
    public static int saturatedPow(int var0, int var1_1) {
        MathPreconditions.checkNonNegative("exponent", var1_1);
        var2_2 = -1;
        var3_3 = 1;
        if (var0 == -2) ** GOTO lbl18
        if (var0 == -1) {
            var0 = var2_2;
            if ((var1_1 & 1) != 0) return var0;
            return 1;
        }
        if (var0 != 0) {
            if (var0 == 1) return 1;
            if (var0 == 2) {
                if (var1_1 < 31) return 1 << var1_1;
                return Integer.MAX_VALUE;
            }
        } else {
            if (var1_1 != 0) return 0;
            return var3_3;
lbl18: // 1 sources:
            if (var1_1 >= 32) {
                return (var1_1 & 1) + Integer.MAX_VALUE;
            }
            if ((var1_1 & 1) != 0) return -1 << var1_1;
            return 1 << var1_1;
        }
        var4_4 = 1;
        var3_3 = var1_1;
        var2_2 = var0;
        do {
            var5_5 = var2_2;
            if (var3_3 == 0) return var4_4;
            if (var3_3 == 1) return IntMath.saturatedMultiply(var4_4, var5_5);
            var6_6 = var4_4;
            if ((var3_3 & 1) != 0) {
                var6_6 = IntMath.saturatedMultiply(var4_4, var5_5);
            }
            var7_7 = var3_3 >> 1;
            var4_4 = var6_6;
            var2_2 = var5_5;
            var3_3 = var7_7;
            if (var7_7 <= 0) continue;
            var2_2 = -46340 > var5_5 ? 1 : 0;
            if ((var2_2 | (var3_3 = var5_5 > 46340 ? 1 : 0)) != 0) {
                return (var0 >>> 31 & (var1_1 & 1)) + Integer.MAX_VALUE;
            }
            var2_2 = var5_5 * var5_5;
            var4_4 = var6_6;
            var3_3 = var7_7;
        } while (true);
    }

    public static int saturatedSubtract(int n, int n2) {
        return Ints.saturatedCast((long)n - (long)n2);
    }

    public static int sqrt(int n, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", n);
        int n2 = IntMath.sqrtFloor(n);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                n = IntMath.lessThanBranchFree(n2 * n2 + n2, n);
                return n2 + n;
            }
            case 4: 
            case 5: {
                n = IntMath.lessThanBranchFree(n2 * n2, n);
                return n2 + n;
            }
            case 1: {
                boolean bl = n2 * n2 == n;
                MathPreconditions.checkRoundingUnnecessary(bl);
            }
            case 2: 
            case 3: 
        }
        return n2;
    }

    private static int sqrtFloor(int n) {
        return (int)Math.sqrt(n);
    }

}

