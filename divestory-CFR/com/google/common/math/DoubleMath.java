/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.common.math.MathPreconditions;
import com.google.common.primitives.Booleans;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

public final class DoubleMath {
    private static final double LN_2 = Math.log(2.0);
    static final int MAX_FACTORIAL = 170;
    private static final double MAX_INT_AS_DOUBLE = 2.147483647E9;
    private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18;
    private static final double MIN_INT_AS_DOUBLE = -2.147483648E9;
    private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18;
    static final double[] everySixteenthFactorial = new double[]{1.0, 2.0922789888E13, 2.631308369336935E35, 1.2413915592536073E61, 1.2688693218588417E89, 7.156945704626381E118, 9.916779348709496E149, 1.974506857221074E182, 3.856204823625804E215, 5.5502938327393044E249, 4.7147236359920616E284};

    private DoubleMath() {
    }

    private static double checkFinite(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return d;
    }

    public static double factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n > 170) {
            return Double.POSITIVE_INFINITY;
        }
        double d = 1.0;
        int n2 = n & -16;
        while (++n2 <= n) {
            d *= (double)n2;
        }
        return d * everySixteenthFactorial[n >> 4];
    }

    public static int fuzzyCompare(double d, double d2, double d3) {
        if (DoubleMath.fuzzyEquals(d, d2, d3)) {
            return 0;
        }
        if (d < d2) {
            return -1;
        }
        if (!(d > d2)) return Booleans.compare(Double.isNaN(d), Double.isNaN(d2));
        return 1;
    }

    public static boolean fuzzyEquals(double d, double d2, double d3) {
        MathPreconditions.checkNonNegative("tolerance", d3);
        if (Math.copySign(d - d2, 1.0) <= d3) return true;
        if (d == d2) return true;
        if (!Double.isNaN(d)) return false;
        if (!Double.isNaN(d2)) return false;
        return true;
    }

    public static boolean isMathematicalInteger(double d) {
        if (!DoubleUtils.isFinite(d)) return false;
        if (d == 0.0) return true;
        if (52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(d)) > Math.getExponent(d)) return false;
        return true;
    }

    public static boolean isPowerOfTwo(double d) {
        boolean bl;
        boolean bl2 = bl = false;
        if (!(d > 0.0)) return bl2;
        bl2 = bl;
        if (!DoubleUtils.isFinite(d)) return bl2;
        long l = DoubleUtils.getSignificand(d);
        bl2 = bl;
        if ((l & l - 1L) != 0L) return bl2;
        return true;
    }

    public static double log2(double d) {
        return Math.log(d) / LN_2;
    }

    /*
     * Unable to fully structure code
     */
    public static int log2(double var0, RoundingMode var2_1) {
        var3_2 = 0;
        var4_3 = 0;
        var5_4 = 0;
        var6_5 = var0 > 0.0 && DoubleUtils.isFinite(var0) != false;
        Preconditions.checkArgument(var6_5, "x must be positive and finite");
        var7_6 = Math.getExponent(var0);
        if (!DoubleUtils.isNormal(var0)) {
            return DoubleMath.log2(var0 * 4.503599627370496E15, var2_1) - 52;
        }
        var8_7 = var4_3;
        switch (1.$SwitchMap$java$math$RoundingMode[var2_1.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                var0 = DoubleUtils.scaleNormalize(var0);
                var8_7 = var4_3;
                if (!(var0 * var0 > 2.0)) break;
                var8_7 = 1;
                break;
            }
            case 5: {
                var8_7 = var5_4;
                if (var7_6 >= 0) {
                    var8_7 = 1;
                }
                var6_5 = DoubleMath.isPowerOfTwo(var0);
                ** GOTO lbl30
            }
            case 4: {
                var8_7 = var3_2;
                if (var7_6 < 0) {
                    var8_7 = 1;
                }
                var6_5 = DoubleMath.isPowerOfTwo(var0);
lbl30: // 2 sources:
                var8_7 &= var6_5 ^ true;
                break;
            }
            case 3: {
                var8_7 = DoubleMath.isPowerOfTwo(var0) ^ true;
                break;
            }
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(DoubleMath.isPowerOfTwo(var0));
                var8_7 = var4_3;
            }
            case 2: 
        }
        var5_4 = var7_6;
        if (var8_7 == 0) return var5_4;
        return var7_6 + 1;
    }

    @Deprecated
    public static double mean(Iterable<? extends Number> iterable) {
        return DoubleMath.mean(iterable.iterator());
    }

    @Deprecated
    public static double mean(Iterator<? extends Number> iterator2) {
        Preconditions.checkArgument(iterator2.hasNext(), "Cannot take mean of 0 values");
        double d = DoubleMath.checkFinite(iterator2.next().doubleValue());
        long l = 1L;
        while (iterator2.hasNext()) {
            double d2 = DoubleMath.checkFinite(iterator2.next().doubleValue());
            d += (d2 - d) / (double)(++l);
        }
        return d;
    }

    @Deprecated
    public static double mean(double ... arrd) {
        int n = arrd.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "Cannot take mean of 0 values");
        double d = DoubleMath.checkFinite(arrd[0]);
        long l = 1L;
        while (n2 < arrd.length) {
            DoubleMath.checkFinite(arrd[n2]);
            d += (arrd[n2] - d) / (double)(++l);
            ++n2;
        }
        return d;
    }

    @Deprecated
    public static double mean(int ... arrn) {
        int n = arrn.length;
        int n2 = 0;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "Cannot take mean of 0 values");
        long l = 0L;
        while (n2 < arrn.length) {
            l += (long)arrn[n2];
            ++n2;
        }
        return (double)l / (double)arrn.length;
    }

    @Deprecated
    public static double mean(long ... arrl) {
        int n = arrl.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "Cannot take mean of 0 values");
        double d = arrl[0];
        long l = 1L;
        while (n2 < arrl.length) {
            d += ((double)arrl[n2] - d) / (double)(++l);
            ++n2;
        }
        return d;
    }

    static double roundIntermediate(double d, RoundingMode roundingMode) {
        if (!DoubleUtils.isFinite(d)) throw new ArithmeticException("input is infinite or NaN");
        double d2 = d;
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 8: {
                d2 = Math.rint(d);
                if (Math.abs(d - d2) != 0.5) return d2;
                return d;
            }
            case 7: {
                d2 = Math.rint(d);
                if (Math.abs(d - d2) != 0.5) return d2;
                return d + Math.copySign(0.5, d);
            }
            case 6: {
                return Math.rint(d);
            }
            case 5: {
                if (DoubleMath.isMathematicalInteger(d)) {
                    return d;
                }
                long l = (long)d;
                int n = d > 0.0 ? 1 : -1;
                d2 = l + (long)n;
            }
            case 4: {
                return d2;
            }
            case 3: {
                d2 = d;
                if (d <= 0.0) return d2;
                if (!DoubleMath.isMathematicalInteger(d)) return (long)d + 1L;
                return d;
            }
            case 2: {
                d2 = d;
                if (d >= 0.0) return d2;
                if (!DoubleMath.isMathematicalInteger(d)) return (long)d - 1L;
                return d;
            }
            case 1: 
        }
        MathPreconditions.checkRoundingUnnecessary(DoubleMath.isMathematicalInteger(d));
        return d;
    }

    public static BigInteger roundToBigInteger(double d, RoundingMode object) {
        d = DoubleMath.roundIntermediate(d, object);
        boolean bl = true;
        int n = -9.223372036854776E18 - d < 1.0 ? 1 : 0;
        if (!(d < 9.223372036854776E18)) {
            bl = false;
        }
        if (bl & n) {
            return BigInteger.valueOf((long)d);
        }
        n = Math.getExponent(d);
        BigInteger bigInteger = BigInteger.valueOf(DoubleUtils.getSignificand(d)).shiftLeft(n - 52);
        object = bigInteger;
        if (!(d < 0.0)) return object;
        return bigInteger.negate();
    }

    public static int roundToInt(double d, RoundingMode roundingMode) {
        double d2 = DoubleMath.roundIntermediate(d, roundingMode);
        boolean bl = true;
        boolean bl2 = d2 > -2.147483649E9;
        if (!(d2 < 2.147483648E9)) {
            bl = false;
        }
        MathPreconditions.checkInRangeForRoundingInputs(bl & bl2, d, roundingMode);
        return (int)d2;
    }

    public static long roundToLong(double d, RoundingMode roundingMode) {
        double d2 = DoubleMath.roundIntermediate(d, roundingMode);
        boolean bl = true;
        boolean bl2 = -9.223372036854776E18 - d2 < 1.0;
        if (!(d2 < 9.223372036854776E18)) {
            bl = false;
        }
        MathPreconditions.checkInRangeForRoundingInputs(bl2 & bl, d, roundingMode);
        return (long)d2;
    }

}

