/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.DoubleMath;
import com.google.common.math.DoubleUtils;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import com.google.common.math.MathPreconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class BigIntegerMath {
    private static final double LN_10;
    private static final double LN_2;
    static final BigInteger SQRT2_PRECOMPUTED_BITS;
    static final int SQRT2_PRECOMPUTE_THRESHOLD = 256;

    static {
        SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
        LN_10 = Math.log(10.0);
        LN_2 = Math.log(2.0);
    }

    private BigIntegerMath() {
    }

    public static BigInteger binomial(int n, int n2) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", n2);
        int n3 = 1;
        boolean bl = n2 <= n;
        Preconditions.checkArgument(bl, "k (%s) > n (%s)", n2, n);
        int n4 = n2;
        if (n2 > n >> 1) {
            n4 = n - n2;
        }
        if (n4 < LongMath.biggestBinomials.length && n <= LongMath.biggestBinomials[n4]) {
            return BigInteger.valueOf(LongMath.binomial(n, n4));
        }
        BigInteger bigInteger = BigInteger.ONE;
        long l = n;
        long l2 = 1L;
        int n5 = LongMath.log2(l, RoundingMode.CEILING);
        n2 = n3;
        block0 : do {
            n3 = n5;
            while (n2 < n4) {
                int n6 = n - n2;
                ++n2;
                if ((n3 += n5) >= 63) {
                    bigInteger = bigInteger.multiply(BigInteger.valueOf(l)).divide(BigInteger.valueOf(l2));
                    l = n6;
                    l2 = n2;
                    continue block0;
                }
                l *= (long)n6;
                l2 *= (long)n2;
            }
            return bigInteger.multiply(BigInteger.valueOf(l)).divide(BigInteger.valueOf(l2));
            break;
        } while (true);
    }

    public static BigInteger ceilingPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(BigIntegerMath.log2(bigInteger, RoundingMode.CEILING));
    }

    public static BigInteger divide(BigInteger bigInteger, BigInteger bigInteger2, RoundingMode roundingMode) {
        return new BigDecimal(bigInteger).divide(new BigDecimal(bigInteger2), 0, roundingMode).toBigIntegerExact();
    }

    public static BigInteger factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n < LongMath.factorials.length) {
            return BigInteger.valueOf(LongMath.factorials[n]);
        }
        ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>(IntMath.divide(IntMath.log2(n, RoundingMode.CEILING) * n, 64, RoundingMode.CEILING));
        int n2 = LongMath.factorials.length;
        long l = LongMath.factorials[n2 - 1];
        int n3 = Long.numberOfTrailingZeros(l);
        int n4 = LongMath.log2(l >>= n3, RoundingMode.FLOOR) + 1;
        long l2 = n2;
        int n5 = LongMath.log2(l2, RoundingMode.FLOOR) + 1;
        int n6 = 1 << n5 - 1;
        do {
            if (l2 > (long)n) {
                if (l <= 1L) return BigIntegerMath.listProduct(arrayList).shiftLeft(n3);
                arrayList.add(BigInteger.valueOf(l));
                return BigIntegerMath.listProduct(arrayList).shiftLeft(n3);
            }
            int n7 = n5;
            n2 = n6;
            if (((long)n6 & l2) != 0L) {
                n2 = n6 << 1;
                n7 = n5 + 1;
            }
            n6 = Long.numberOfTrailingZeros(l2);
            n3 += n6;
            long l3 = l;
            if (n7 - n6 + n4 >= 64) {
                arrayList.add(BigInteger.valueOf(l));
                l3 = 1L;
            }
            l = l3 * (l2 >> n6);
            n4 = LongMath.log2(l, RoundingMode.FLOOR) + 1;
            ++l2;
            n5 = n7;
            n6 = n2;
        } while (true);
    }

    static boolean fitsInLong(BigInteger bigInteger) {
        if (bigInteger.bitLength() > 63) return false;
        return true;
    }

    public static BigInteger floorPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(BigIntegerMath.log2(bigInteger, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        int n = bigInteger.signum();
        boolean bl = true;
        if (n <= 0) return false;
        if (bigInteger.getLowestSetBit() != bigInteger.bitLength() - 1) return false;
        return bl;
    }

    static BigInteger listProduct(List<BigInteger> list) {
        return BigIntegerMath.listProduct(list, 0, list.size());
    }

    static BigInteger listProduct(List<BigInteger> list, int n, int n2) {
        int n3 = n2 - n;
        if (n3 == 0) return BigInteger.ONE;
        if (n3 == 1) return list.get(n);
        if (n3 == 2) return list.get(n).multiply(list.get(n + 1));
        if (n3 == 3) return list.get(n).multiply(list.get(n + 1)).multiply(list.get(n + 2));
        n3 = n2 + n >>> 1;
        return BigIntegerMath.listProduct(list, n, n3).multiply(BigIntegerMath.listProduct(list, n3, n2));
    }

    public static int log10(BigInteger bigInteger, RoundingMode roundingMode) {
        int n;
        MathPreconditions.checkPositive("x", bigInteger);
        if (BigIntegerMath.fitsInLong(bigInteger)) {
            return LongMath.log10(bigInteger.longValue(), roundingMode);
        }
        int n2 = (int)((double)BigIntegerMath.log2(bigInteger, RoundingMode.FLOOR) * LN_2 / LN_10);
        BigInteger bigInteger2 = BigInteger.TEN.pow(n2);
        int n3 = bigInteger2.compareTo(bigInteger);
        if (n3 > 0) {
            BigInteger bigInteger3;
            do {
                n = n2 - 1;
                bigInteger3 = bigInteger2.divide(BigInteger.TEN);
                n3 = bigInteger3.compareTo(bigInteger);
                n2 = n;
                bigInteger2 = bigInteger3;
            } while (n3 > 0);
            bigInteger2 = bigInteger3;
        } else {
            BigInteger bigInteger4 = BigInteger.TEN.multiply(bigInteger2);
            n = bigInteger4.compareTo(bigInteger);
            do {
                BigInteger bigInteger5 = bigInteger4;
                if (n > 0) break;
                ++n2;
                bigInteger4 = BigInteger.TEN.multiply(bigInteger5);
                int n4 = bigInteger4.compareTo(bigInteger);
                bigInteger2 = bigInteger5;
                n3 = n;
                n = n4;
            } while (true);
            n = n2;
        }
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                if (bigInteger.pow(2).compareTo(bigInteger2.pow(2).multiply(BigInteger.TEN)) <= 0) {
                    return n;
                }
                ++n;
                return n;
            }
            case 4: 
            case 5: {
                if (bigInteger2.equals(bigInteger)) {
                    return n;
                }
                ++n;
                return n;
            }
            case 1: {
                boolean bl = n3 == 0;
                MathPreconditions.checkRoundingUnnecessary(bl);
            }
            case 2: 
            case 3: 
        }
        return n;
    }

    public static int log2(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.checkPositive("x", Preconditions.checkNotNull(bigInteger));
        int n = bigInteger.bitLength() - 1;
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                if (n < 256) {
                    if (bigInteger.compareTo(SQRT2_PRECOMPUTED_BITS.shiftRight(256 - n)) > 0) return n + 1;
                    return n;
                }
                if (bigInteger.pow(2).bitLength() - 1 < n * 2 + 1) {
                    return n;
                }
                ++n;
                return n;
            }
            case 4: 
            case 5: {
                if (BigIntegerMath.isPowerOfTwo(bigInteger)) {
                    return n;
                }
                ++n;
                return n;
            }
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(BigIntegerMath.isPowerOfTwo(bigInteger));
            }
            case 2: 
            case 3: 
        }
        return n;
    }

    public static BigInteger sqrt(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.checkNonNegative("x", bigInteger);
        if (BigIntegerMath.fitsInLong(bigInteger)) {
            return BigInteger.valueOf(LongMath.sqrt(bigInteger.longValue(), roundingMode));
        }
        BigInteger bigInteger2 = BigIntegerMath.sqrtFloor(bigInteger);
        switch (1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 6: 
            case 7: 
            case 8: {
                if (bigInteger2.pow(2).add(bigInteger2).compareTo(bigInteger) < 0) return bigInteger2.add(BigInteger.ONE);
                return bigInteger2;
            }
            case 4: 
            case 5: {
                int n = bigInteger2.intValue();
                n = n * n == bigInteger.intValue() && bigInteger2.pow(2).equals(bigInteger) ? 1 : 0;
                if (n == 0) return bigInteger2.add(BigInteger.ONE);
                return bigInteger2;
            }
            case 1: {
                MathPreconditions.checkRoundingUnnecessary(bigInteger2.pow(2).equals(bigInteger));
            }
            case 2: 
            case 3: 
        }
        return bigInteger2;
    }

    private static BigInteger sqrtApproxWithDoubles(BigInteger bigInteger) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(bigInteger)), RoundingMode.HALF_EVEN);
    }

    private static BigInteger sqrtFloor(BigInteger bigInteger) {
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        int n = BigIntegerMath.log2(bigInteger, RoundingMode.FLOOR);
        if (n < 1023) {
            bigInteger3 = BigIntegerMath.sqrtApproxWithDoubles(bigInteger);
        } else {
            n = n - 52 & -2;
            bigInteger3 = BigIntegerMath.sqrtApproxWithDoubles(bigInteger.shiftRight(n)).shiftLeft(n >> 1);
        }
        BigInteger bigInteger4 = bigInteger2 = bigInteger3.add(bigInteger.divide(bigInteger3)).shiftRight(1);
        if (bigInteger3.equals(bigInteger2)) {
            return bigInteger3;
        }
        while ((bigInteger3 = bigInteger4.add(bigInteger.divide(bigInteger4)).shiftRight(1)).compareTo(bigInteger4) < 0) {
            bigInteger4 = bigInteger3;
        }
        return bigInteger4;
    }

}

