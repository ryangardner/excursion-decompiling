/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.math.MathPreconditions;
import com.google.common.primitives.UnsignedLongs;
import java.math.RoundingMode;

public final class LongMath {
    static final long FLOOR_SQRT_MAX_LONG = 3037000499L;
    static final long MAX_POWER_OF_SQRT2_UNSIGNED = -5402926248376769404L;
    static final long MAX_SIGNED_POWER_OF_TWO = 0x4000000000000000L;
    private static final int SIEVE_30 = -545925251;
    static final int[] biggestBinomials;
    static final int[] biggestSimpleBinomials;
    static final long[] factorials;
    static final long[] halfPowersOf10;
    static final byte[] maxLog10ForLeadingZeros;
    private static final long[][] millerRabinBaseSets;
    static final long[] powersOf10;

    static {
        maxLog10ForLeadingZeros = new byte[]{19, 18, 18, 18, 18, 17, 17, 17, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 13, 13, 13, 12, 12, 12, 12, 11, 11, 11, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0};
        powersOf10 = new long[]{1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L};
        halfPowersOf10 = new long[]{3L, 31L, 316L, 3162L, 31622L, 316227L, 3162277L, 31622776L, 316227766L, 3162277660L, 31622776601L, 316227766016L, 3162277660168L, 31622776601683L, 316227766016837L, 3162277660168379L, 31622776601683793L, 316227766016837933L, 3162277660168379331L};
        factorials = new long[]{1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
        biggestBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 3810779, 121977, 16175, 4337, 1733, 887, 534, 361, 265, 206, 169, 143, 125, 111, 101, 94, 88, 83, 79, 76, 74, 72, 70, 69, 68, 67, 67, 66, 66, 66, 66};
        biggestSimpleBinomials = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 2642246, 86251, 11724, 3218, 1313, 684, 419, 287, 214, 169, 139, 119, 105, 95, 87, 81, 76, 73, 70, 68, 66, 64, 63, 62, 62, 61, 61, 61};
        long[] arrl = new long[]{47636622961200L, 2L, 2570940L, 211991001L, 3749873356L};
        long[] arrl2 = new long[]{7999252175582850L, 2L, 4130806001517L, 149795463772692060L, 186635894390467037L, 3967304179347715805L};
        long[] arrl3 = new long[]{585226005592931976L, 2L, 123635709730000L, 9233062284813009L, 43835965440333360L, 761179012939631437L, 1263739024124850375L};
        millerRabinBaseSets = new long[][]{{291830L, 126401071349994536L}, {885594168L, 725270293939359937L, 3569819667048198375L}, {273919523040L, 15L, 7363882082L, 992620450144556L}, arrl, arrl2, arrl3, {Long.MAX_VALUE, 2L, 325L, 9375L, 28178L, 450775L, 9780504L, 1795265022L}};
    }

    private LongMath() {
    }

    public static long binomial(int n, int n2) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        boolean bl = n2 <= n;
        Preconditions.checkArgument(bl, "k (%s) > n (%s)", n2, n);
        int n3 = n2;
        if (n2 > n >> 1) {
            n3 = n - n2;
        }
        long l = 1L;
        if (n3 == 0) return 1L;
        if (n3 == 1) return n;
        long[] arrl = factorials;
        if (n < arrl.length) {
            return arrl[n] / (arrl[n3] * arrl[n - n3]);
        }
        arrl = biggestBinomials;
        if (n3 >= arrl.length) return Long.MAX_VALUE;
        if (n > arrl[n3]) {
            return Long.MAX_VALUE;
        }
        arrl = biggestSimpleBinomials;
        int n4 = arrl.length;
        n2 = 2;
        if (n3 < n4 && n <= arrl[n3]) {
            n4 = n - 1;
            long l2 = n;
            n = n2;
            n2 = n4;
            while (n <= n3) {
                l2 = l2 * (long)n2 / (long)n;
                --n2;
                ++n;
            }
            return l2;
        }
        long l3 = n;
        n2 = LongMath.log2(l3, RoundingMode.CEILING);
        n4 = n - 1;
        n = n2;
        int n5 = 2;
        long l4 = 1L;
        while (n5 <= n3) {
            if ((n += n2) < 63) {
                l3 *= (long)n4;
                l4 *= (long)n5;
            } else {
                l = LongMath.multiplyFraction(l, l3, l4);
                l3 = n4;
                l4 = n5;
                n = n2;
            }
            ++n5;
            --n4;
        }
        return LongMath.multiplyFraction(l, l3, l4);
    }

    public static long ceilingPowerOfTwo(long l) {
        MathPreconditions.checkPositive("x", l);
        if (l <= 0x4000000000000000L) {
            return 1L << -Long.numberOfLeadingZeros(l - 1L);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ceilingPowerOfTwo(");
        stringBuilder.append(l);
        stringBuilder.append(") is not representable as a long");
        throw new ArithmeticException(stringBuilder.toString());
    }

    public static long checkedAdd(long l, long l2) {
        long l3 = l + l2;
        boolean bl = true;
        boolean bl2 = (l ^ l2) < 0L;
        if ((l ^ l3) < 0L) {
            bl = false;
        }
        MathPreconditions.checkNoOverflow(bl2 | bl, "checkedAdd", l, l2);
        return l3;
    }

    public static long checkedMultiply(long l, long l2) {
        int n = Long.numberOfLeadingZeros(l) + Long.numberOfLeadingZeros(l) + Long.numberOfLeadingZeros(l2) + Long.numberOfLeadingZeros(l2);
        if (n > 65) {
            return l * l2;
        }
        boolean bl = n >= 64;
        MathPreconditions.checkNoOverflow(bl, "checkedMultiply", l, l2);
        long l3 = l LCMP 0L;
        n = l3 >= 0 ? 1 : 0;
        int n2 = l2 != Long.MIN_VALUE ? 1 : 0;
        MathPreconditions.checkNoOverflow((n | n2) != 0, "checkedMultiply", l, l2);
        long l4 = l * l2;
        bl = l3 == false || l4 / l == l2;
        MathPreconditions.checkNoOverflow(bl, "checkedMultiply", l, l2);
        return l4;
    }

    public static long checkedPow(long l, int n) {
        MathPreconditions.checkNonNegative("exponent", n);
        boolean bl = false;
        boolean bl2 = false;
        int n2 = l >= -2L ? 1 : 0;
        boolean bl3 = l <= 2L;
        long l2 = 1L;
        if (n2 & bl3) {
            n2 = (int)l;
            if (n2 != -2) {
                if (n2 != -1) {
                    if (n2 != 0) {
                        if (n2 == 1) return 1L;
                        if (n2 != 2) throw new AssertionError();
                        if (n < 63) {
                            bl2 = true;
                        }
                        MathPreconditions.checkNoOverflow(bl2, "checkedPow", l, (long)n);
                        return 1L << n;
                    }
                    if (n != 0) return 0L;
                    return l2;
                }
                if ((n & 1) != 0) return -1L;
                return l2;
            }
            bl2 = bl;
            if (n < 64) {
                bl2 = true;
            }
            MathPreconditions.checkNoOverflow(bl2, "checkedPow", l, (long)n);
            if ((n & 1) != 0) return -1L << n;
            return 1L << n;
        }
        l2 = 1L;
        long l3 = l;
        while (n != 0) {
            if (n == 1) return LongMath.checkedMultiply(l2, l3);
            l = l2;
            if ((n & 1) != 0) {
                l = LongMath.checkedMultiply(l2, l3);
            }
            long l4 = l3;
            if ((n >>= 1) > 0) {
                bl2 = -3037000499L <= l3 && l3 <= 3037000499L;
                MathPreconditions.checkNoOverflow(bl2, "checkedPow", l3, (long)n);
                l4 = l3 * l3;
            }
            l2 = l;
            l3 = l4;
        }
        return l2;
    }

    public static long checkedSubtract(long l, long l2) {
        long l3 = l - l2;
        boolean bl = true;
        boolean bl2 = (l ^ l2) >= 0L;
        if ((l ^ l3) < 0L) {
            bl = false;
        }
        MathPreconditions.checkNoOverflow(bl2 | bl, "checkedSubtract", l, l2);
        return l3;
    }

    /*
     * Unable to fully structure code
     */
    public static long divide(long var0, long var2_1, RoundingMode var4_2) {
        Preconditions.checkNotNull(var4_2);
        var5_3 = var0 / var2_1;
        var7_4 = var0 - var2_1 * var5_3;
        var9_5 = var7_4 LCMP 0L;
        if (var9_5 == false) {
            return var5_3;
        }
        var10_6 = (int)((var0 ^ var2_1) >> 63);
        var11_7 = true;
        var12_8 = 1;
        var13_9 = 1;
        var14_10 = var10_6 | 1;
        var10_6 = var12_8;
        switch (1.$SwitchMap$java$math$RoundingMode[var4_2.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                var0 = Math.abs(var7_4);
                var10_6 = (int)(var0 - (Math.abs(var2_1) - var0) LCMP 0L);
                if (var10_6 == 0) {
                    var10_6 = var4_2 == RoundingMode.HALF_UP ? 1 : 0;
                    var12_8 = var4_2 == RoundingMode.HALF_EVEN ? 1 : 0;
                    if ((1L & var5_3) == 0L) {
                        var13_9 = 0;
                    }
                    var10_6 = var13_9 & var12_8 | var10_6;
                    break;
                }
                if (var10_6 > 0) {
                    var10_6 = var12_8;
                    break;
                }
                ** GOTO lbl45
            }
            case 5: {
                if (var14_10 > 0) {
                    var10_6 = var12_8;
                    break;
                }
                ** GOTO lbl45
            }
            case 3: {
                if (var14_10 < 0) {
                    var10_6 = var12_8;
                    break;
                }
                ** GOTO lbl45
            }
            case 1: {
                if (var9_5 != false) {
                    var11_7 = false;
                }
                MathPreconditions.checkRoundingUnnecessary(var11_7);
            }
lbl45: // 5 sources:
            case 2: {
                var10_6 = 0;
            }
            case 4: 
        }
        var0 = var5_3;
        if (var10_6 == 0) return var0;
        return var5_3 + (long)var14_10;
    }

    public static long factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        long[] arrl = factorials;
        if (n >= arrl.length) return Long.MAX_VALUE;
        return arrl[n];
    }

    static boolean fitsInInt(long l) {
        if ((long)((int)l) != l) return false;
        return true;
    }

    public static long floorPowerOfTwo(long l) {
        MathPreconditions.checkPositive("x", l);
        return 1L << 63 - Long.numberOfLeadingZeros(l);
    }

    public static long gcd(long l, long l2) {
        MathPreconditions.checkNonNegative("a", l);
        MathPreconditions.checkNonNegative("b", l2);
        if (l == 0L) {
            return l2;
        }
        if (l2 == 0L) {
            return l;
        }
        int n = Long.numberOfTrailingZeros(l);
        long l3 = l >> n;
        int n2 = Long.numberOfTrailingZeros(l2);
        l = l2 >> n2;
        l2 = l3;
        while (l2 != l) {
            l3 = l2 - l;
            l2 = l3 >> 63 & l3;
            l3 = l3 - l2 - l2;
            l += l2;
            l2 = l3 >> Long.numberOfTrailingZeros(l3);
        }
        return l2 << Math.min(n, n2);
    }

    public static boolean isPowerOfTwo(long l) {
        boolean bl = true;
        boolean bl2 = l > 0L;
        if ((l & l - 1L) == 0L) {
            return bl2 & bl;
        }
        bl = false;
        return bl2 & bl;
    }

    public static boolean isPrime(long l) {
        long[] arrl;
        long l2;
        block9 : {
            l2 = l LCMP 2L;
            if (l2 < 0) {
                MathPreconditions.checkNonNegative("n", l);
                return false;
            }
            if (l2 == false) return true;
            if (l == 3L) return true;
            if (l == 5L) return true;
            if (l == 7L) return true;
            if (l == 11L) return true;
            if (l == 13L) {
                return true;
            }
            if ((-545925251 & 1 << (int)(l % 30L)) != 0) {
                return false;
            }
            if (l % 7L == 0L) return false;
            if (l % 11L == 0L) return false;
            if (l % 13L == 0L) {
                return false;
            }
            if (l < 289L) {
                return true;
            }
            long[][] arrl2 = millerRabinBaseSets;
            int n = arrl2.length;
            l2 = 0;
            while (l2 < n) {
                arrl = arrl2[l2];
                if (l > arrl[0]) {
                    ++l2;
                    continue;
                }
                break block9;
            }
            throw new AssertionError();
        }
        l2 = 1;
        while (l2 < arrl.length) {
            if (!MillerRabinTester.test(arrl[l2], l)) {
                return false;
            }
            ++l2;
        }
        return true;
    }

    static int lessThanBranchFree(long l, long l2) {
        return (int)(l - l2 >>> 63);
    }

    public static int log10(long l, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", l);
        int n = LongMath.log10Floor(l);
        long l2 = powersOf10[n];
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                int n2 = LongMath.lessThanBranchFree(halfPowersOf10[n], l);
                return n + n2;
            }
            case 4: 
            case 5: {
                int n2 = LongMath.lessThanBranchFree(l2, l);
                return n + n2;
            }
            case 1: {
                boolean bl = l == l2;
                MathPreconditions.checkRoundingUnnecessary(bl);
            }
            case 2: 
            case 3: 
        }
        return n;
    }

    static int log10Floor(long l) {
        byte by = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(l)];
        return by - LongMath.lessThanBranchFree(l, powersOf10[by]);
    }

    public static int log2(long l, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", l);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError((Object)"impossible");
            }
            case 6: 
            case 7: 
            case 8: {
                int n = Long.numberOfLeadingZeros(l);
                return 63 - n + LongMath.lessThanBranchFree(-5402926248376769404L >>> n, l);
            }
            case 4: 
            case 5: {
                return 64 - Long.numberOfLeadingZeros(l - 1L);
            }
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(LongMath.isPowerOfTwo(l));
            }
            case 2: 
            case 3: 
        }
        return 63 - Long.numberOfLeadingZeros(l);
    }

    public static long mean(long l, long l2) {
        return (l & l2) + ((l ^ l2) >> 1);
    }

    public static int mod(long l, int n) {
        return (int)LongMath.mod(l, (long)n);
    }

    public static long mod(long l, long l2) {
        if (l2 <= 0L) throw new ArithmeticException("Modulus must be positive");
        if ((l %= l2) >= 0L) {
            return l;
        }
        l += l2;
        return l;
    }

    static long multiplyFraction(long l, long l2, long l3) {
        if (l == 1L) {
            return l2 / l3;
        }
        long l4 = LongMath.gcd(l, l3);
        return l / l4 * (l2 / (l3 / l4));
    }

    public static long pow(long l, int n) {
        MathPreconditions.checkNonNegative("exponent", n);
        long l2 = 1L;
        if (-2L <= l && l <= 2L) {
            int n2 = (int)l;
            l = 0L;
            if (n2 != -2) {
                if (n2 != -1) {
                    if (n2 != 0) {
                        if (n2 == 1) return 1L;
                        if (n2 != 2) throw new AssertionError();
                        if (n >= 64) return l;
                        return 1L << n;
                    }
                    if (n != 0) return 0L;
                    return l2;
                }
                if ((n & 1) != 0) return -1L;
                return l2;
            }
            if (n >= 64) return 0L;
            if ((n & 1) != 0) return -(1L << n);
            return 1L << n;
        }
        l2 = 1L;
        do {
            long l3 = l2;
            if (n == 0) return l3;
            if (n == 1) {
                return l2 * l;
            }
            l3 = (n & 1) == 0 ? 1L : l;
            l2 *= l3;
            l *= l;
            n >>= 1;
        } while (true);
    }

    public static long saturatedAdd(long l, long l2) {
        long l3 = l + l2;
        boolean bl = true;
        boolean bl2 = (l2 ^ l) < 0L;
        if ((l ^ l3) < 0L) {
            bl = false;
        }
        if (!(bl2 | bl)) return (l3 >>> 63 ^ 1L) + Long.MAX_VALUE;
        return l3;
    }

    public static long saturatedMultiply(long l, long l2) {
        int n = Long.numberOfLeadingZeros(l) + Long.numberOfLeadingZeros(l) + Long.numberOfLeadingZeros(l2) + Long.numberOfLeadingZeros(l2);
        if (n > 65) {
            return l * l2;
        }
        long l3 = ((l ^ l2) >>> 63) + Long.MAX_VALUE;
        boolean bl = true;
        n = n < 64 ? 1 : 0;
        long l4 = l LCMP 0L;
        boolean bl2 = l4 < 0;
        if (l2 != Long.MIN_VALUE) {
            bl = false;
        }
        if ((n | bl & bl2) != 0) {
            return l3;
        }
        long l5 = l * l2;
        if (l4 == false) return l5;
        if (l5 / l != l2) return l3;
        return l5;
    }

    public static long saturatedPow(long l, int n) {
        MathPreconditions.checkNonNegative("exponent", n);
        int n2 = l >= -2L ? 1 : 0;
        int n3 = l <= 2L ? 1 : 0;
        long l2 = 1L;
        if (n2 & n3) {
            n2 = (int)l;
            if (n2 != -2) {
                if (n2 != -1) {
                    if (n2 != 0) {
                        if (n2 == 1) return 1L;
                        if (n2 != 2) throw new AssertionError();
                        if (n < 63) return 1L << n;
                        return Long.MAX_VALUE;
                    }
                    if (n != 0) return 0L;
                    return l2;
                }
                if ((n & 1) != 0) return -1L;
                return l2;
            }
            if (n >= 64) {
                return (long)(n & 1) + Long.MAX_VALUE;
            }
            if ((n & 1) != 0) return -1L << n;
            return 1L << n;
        }
        long l3 = n & 1;
        long l4 = l;
        do {
            long l5 = l4;
            if (n == 0) return l2;
            if (n == 1) return LongMath.saturatedMultiply(l2, l5);
            long l6 = l2;
            if ((n & 1) != 0) {
                l6 = LongMath.saturatedMultiply(l2, l5);
            }
            n3 = n >> 1;
            l2 = l6;
            l4 = l5;
            n = n3;
            if (n3 <= 0) continue;
            n = -3037000499L > l5 ? 1 : 0;
            if ((n | (n2 = l5 > 3037000499L ? 1 : 0)) != 0) {
                return (l >>> 63 & l3) + Long.MAX_VALUE;
            }
            l4 = l5 * l5;
            l2 = l6;
            n = n3;
        } while (true);
    }

    public static long saturatedSubtract(long l, long l2) {
        long l3 = l - l2;
        boolean bl = true;
        boolean bl2 = (l2 ^ l) >= 0L;
        if ((l ^ l3) < 0L) {
            bl = false;
        }
        if (!(bl2 | bl)) return (l3 >>> 63 ^ 1L) + Long.MAX_VALUE;
        return l3;
    }

    public static long sqrt(long l, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", l);
        if (LongMath.fitsInInt(l)) {
            return IntMath.sqrt((int)l, roundingMode);
        }
        long l2 = (long)Math.sqrt(l);
        long l3 = l2 * l2;
        int n = 1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()];
        boolean bl = true;
        int n2 = 1;
        switch (n) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                if (l < l3) {
                    return (l2 -= (long)n2) + (long)LongMath.lessThanBranchFree(l2 * l2 + l2, l);
                }
                n2 = 0;
                return (l2 -= (long)n2) + (long)LongMath.lessThanBranchFree(l2 * l2 + l2, l);
            }
            case 4: 
            case 5: {
                long l4 = l2;
                if (l <= l3) return l4;
                return l2 + 1L;
            }
            case 2: 
            case 3: {
                long l5 = l2;
                if (l >= l3) return l5;
                return l2 - 1L;
            }
            case 1: 
        }
        if (l3 != l) {
            bl = false;
        }
        MathPreconditions.checkRoundingUnnecessary(bl);
        return l2;
    }

    private static abstract class MillerRabinTester
    extends Enum<MillerRabinTester> {
        private static final /* synthetic */ MillerRabinTester[] $VALUES;
        public static final /* enum */ MillerRabinTester LARGE;
        public static final /* enum */ MillerRabinTester SMALL;

        static {
            MillerRabinTester millerRabinTester;
            SMALL = new MillerRabinTester(){

                @Override
                long mulMod(long l, long l2, long l3) {
                    return l * l2 % l3;
                }

                @Override
                long squareMod(long l, long l2) {
                    return l * l % l2;
                }
            };
            LARGE = millerRabinTester = new MillerRabinTester(){

                private long plusMod(long l, long l2, long l3) {
                    long l4;
                    long l5 = l4 = l + l2;
                    if (l < l3 - l2) return l5;
                    return l4 - l3;
                }

                private long times2ToThe32Mod(long l, long l2) {
                    long l3;
                    int n;
                    int n2 = 32;
                    do {
                        n = Math.min(n2, Long.numberOfLeadingZeros(l));
                        l3 = UnsignedLongs.remainder(l << n, l2);
                        n2 = n = n2 - n;
                        l = l3;
                    } while (n > 0);
                    return l3;
                }

                @Override
                long mulMod(long l, long l2, long l3) {
                    long l4 = l >>> 32;
                    long l5 = l2 >>> 32;
                    long l6 = l & 0xFFFFFFFFL;
                    long l7 = l2 & 0xFFFFFFFFL;
                    l = l2 = this.times2ToThe32Mod(l4 * l5, l3) + l4 * l7;
                    if (l2 < 0L) {
                        l = UnsignedLongs.remainder(l2, l3);
                    }
                    Long.signum(l6);
                    return this.plusMod(this.times2ToThe32Mod(l + l5 * l6, l3), UnsignedLongs.remainder(l6 * l7, l3), l3);
                }

                @Override
                long squareMod(long l, long l2) {
                    long l3 = l >>> 32;
                    long l4 = l & 0xFFFFFFFFL;
                    long l5 = this.times2ToThe32Mod(l3 * l3, l2);
                    l = l3 = l3 * l4 * 2L;
                    if (l3 >= 0L) return this.plusMod(this.times2ToThe32Mod(l5 + l, l2), UnsignedLongs.remainder(l4 * l4, l2), l2);
                    l = UnsignedLongs.remainder(l3, l2);
                    return this.plusMod(this.times2ToThe32Mod(l5 + l, l2), UnsignedLongs.remainder(l4 * l4, l2), l2);
                }
            };
            $VALUES = new MillerRabinTester[]{SMALL, millerRabinTester};
        }

        private long powMod(long l, long l2, long l3) {
            long l4 = 1L;
            while (l2 != 0L) {
                long l5 = l4;
                if ((l2 & 1L) != 0L) {
                    l5 = this.mulMod(l4, l, l3);
                }
                l = this.squareMod(l, l3);
                l2 >>= 1;
                l4 = l5;
            }
            return l4;
        }

        static boolean test(long l, long l2) {
            MillerRabinTester millerRabinTester;
            if (l2 <= 3037000499L) {
                millerRabinTester = SMALL;
                return millerRabinTester.testWitness(l, l2);
            }
            millerRabinTester = LARGE;
            return millerRabinTester.testWitness(l, l2);
        }

        private boolean testWitness(long l, long l2) {
            long l3 = l2 - 1L;
            int n = Long.numberOfTrailingZeros(l3);
            if ((l %= l2) == 0L) {
                return true;
            }
            if ((l = this.powMod(l, l3 >> n, l2)) == 1L) {
                return true;
            }
            int n2 = 0;
            while (l != l3) {
                if (++n2 == n) {
                    return false;
                }
                l = this.squareMod(l, l2);
            }
            return true;
        }

        public static MillerRabinTester valueOf(String string2) {
            return Enum.valueOf(MillerRabinTester.class, string2);
        }

        public static MillerRabinTester[] values() {
            return (MillerRabinTester[])$VALUES.clone();
        }

        abstract long mulMod(long var1, long var3, long var5);

        abstract long squareMod(long var1, long var3);

    }

}

