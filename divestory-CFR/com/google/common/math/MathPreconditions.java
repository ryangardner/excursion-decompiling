/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import java.math.BigInteger;
import java.math.RoundingMode;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class MathPreconditions {
    private MathPreconditions() {
    }

    static void checkInRangeForRoundingInputs(boolean bl, double d, RoundingMode roundingMode) {
        if (bl) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rounded value is out of range for input ");
        stringBuilder.append(d);
        stringBuilder.append(" and rounding mode ");
        stringBuilder.append((Object)roundingMode);
        throw new ArithmeticException(stringBuilder.toString());
    }

    static void checkNoOverflow(boolean bl, String string2, int n, int n2) {
        if (bl) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("overflow: ");
        stringBuilder.append(string2);
        stringBuilder.append("(");
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        throw new ArithmeticException(stringBuilder.toString());
    }

    static void checkNoOverflow(boolean bl, String string2, long l, long l2) {
        if (bl) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("overflow: ");
        stringBuilder.append(string2);
        stringBuilder.append("(");
        stringBuilder.append(l);
        stringBuilder.append(", ");
        stringBuilder.append(l2);
        stringBuilder.append(")");
        throw new ArithmeticException(stringBuilder.toString());
    }

    static double checkNonNegative(@NullableDecl String string2, double d) {
        if (d >= 0.0) {
            return d;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(d);
        stringBuilder.append(") must be >= 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static int checkNonNegative(@NullableDecl String string2, int n) {
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(n);
        stringBuilder.append(") must be >= 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static long checkNonNegative(@NullableDecl String string2, long l) {
        if (l >= 0L) {
            return l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(l);
        stringBuilder.append(") must be >= 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static BigInteger checkNonNegative(@NullableDecl String string2, BigInteger bigInteger) {
        if (bigInteger.signum() >= 0) {
            return bigInteger;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(bigInteger);
        stringBuilder.append(") must be >= 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static int checkPositive(@NullableDecl String string2, int n) {
        if (n > 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(n);
        stringBuilder.append(") must be > 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static long checkPositive(@NullableDecl String string2, long l) {
        if (l > 0L) {
            return l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(l);
        stringBuilder.append(") must be > 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static BigInteger checkPositive(@NullableDecl String string2, BigInteger bigInteger) {
        if (bigInteger.signum() > 0) {
            return bigInteger;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" (");
        stringBuilder.append(bigInteger);
        stringBuilder.append(") must be > 0");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static void checkRoundingUnnecessary(boolean bl) {
        if (!bl) throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
    }
}

