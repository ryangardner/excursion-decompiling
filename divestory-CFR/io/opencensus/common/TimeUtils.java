/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import java.math.BigInteger;

final class TimeUtils {
    private static final BigInteger MAX_LONG_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    static final int MAX_NANOS = 999999999;
    static final long MAX_SECONDS = 315576000000L;
    static final long MILLIS_PER_SECOND = 1000L;
    private static final BigInteger MIN_LONG_VALUE = BigInteger.valueOf(Long.MIN_VALUE);
    static final long NANOS_PER_MILLI = 1000000L;
    static final long NANOS_PER_SECOND = 1000000000L;

    private TimeUtils() {
    }

    static long checkedAdd(long l, long l2) {
        Comparable<BigInteger> comparable = BigInteger.valueOf(l).add(BigInteger.valueOf(l2));
        if (((BigInteger)comparable).compareTo(MAX_LONG_VALUE) <= 0 && ((BigInteger)comparable).compareTo(MIN_LONG_VALUE) >= 0) {
            return l + l2;
        }
        comparable = new StringBuilder();
        ((StringBuilder)comparable).append("Long sum overflow: x=");
        ((StringBuilder)comparable).append(l);
        ((StringBuilder)comparable).append(", y=");
        ((StringBuilder)comparable).append(l2);
        throw new ArithmeticException(((StringBuilder)comparable).toString());
    }

    static int compareLongs(long l, long l2) {
        long l3 = l LCMP l2;
        if (l3 < 0) {
            return -1;
        }
        if (l3 != false) return 1;
        return 0;
    }
}

