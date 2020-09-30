/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedLongs;
import java.io.Serializable;
import java.math.BigInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class UnsignedLong
extends Number
implements Comparable<UnsignedLong>,
Serializable {
    public static final UnsignedLong MAX_VALUE;
    public static final UnsignedLong ONE;
    private static final long UNSIGNED_MASK = Long.MAX_VALUE;
    public static final UnsignedLong ZERO;
    private final long value;

    static {
        ZERO = new UnsignedLong(0L);
        ONE = new UnsignedLong(1L);
        MAX_VALUE = new UnsignedLong(-1L);
    }

    private UnsignedLong(long l) {
        this.value = l;
    }

    public static UnsignedLong fromLongBits(long l) {
        return new UnsignedLong(l);
    }

    public static UnsignedLong valueOf(long l) {
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl, "value (%s) is outside the range for an unsigned long value", l);
        return UnsignedLong.fromLongBits(l);
    }

    public static UnsignedLong valueOf(String string2) {
        return UnsignedLong.valueOf(string2, 10);
    }

    public static UnsignedLong valueOf(String string2, int n) {
        return UnsignedLong.fromLongBits(UnsignedLongs.parseUnsignedLong(string2, n));
    }

    public static UnsignedLong valueOf(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        boolean bl = bigInteger.signum() >= 0 && bigInteger.bitLength() <= 64;
        Preconditions.checkArgument(bl, "value (%s) is outside the range for an unsigned long value", (Object)bigInteger);
        return UnsignedLong.fromLongBits(bigInteger.longValue());
    }

    public BigInteger bigIntegerValue() {
        BigInteger bigInteger;
        BigInteger bigInteger2 = bigInteger = BigInteger.valueOf(this.value & Long.MAX_VALUE);
        if (this.value >= 0L) return bigInteger2;
        return bigInteger.setBit(63);
    }

    @Override
    public int compareTo(UnsignedLong unsignedLong) {
        Preconditions.checkNotNull(unsignedLong);
        return UnsignedLongs.compare(this.value, unsignedLong.value);
    }

    public UnsignedLong dividedBy(UnsignedLong unsignedLong) {
        return UnsignedLong.fromLongBits(UnsignedLongs.divide(this.value, Preconditions.checkNotNull(unsignedLong).value));
    }

    @Override
    public double doubleValue() {
        double d;
        long l = this.value;
        double d2 = d = (double)(Long.MAX_VALUE & l);
        if (l >= 0L) return d2;
        return d + 9.223372036854776E18;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof UnsignedLong;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (UnsignedLong)object;
        bl3 = bl;
        if (this.value != ((UnsignedLong)object).value) return bl3;
        return true;
    }

    @Override
    public float floatValue() {
        float f;
        long l = this.value;
        float f2 = f = (float)(Long.MAX_VALUE & l);
        if (l >= 0L) return f2;
        return f + 9.223372E18f;
    }

    public int hashCode() {
        return Longs.hashCode(this.value);
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    public UnsignedLong minus(UnsignedLong unsignedLong) {
        return UnsignedLong.fromLongBits(this.value - Preconditions.checkNotNull(unsignedLong).value);
    }

    public UnsignedLong mod(UnsignedLong unsignedLong) {
        return UnsignedLong.fromLongBits(UnsignedLongs.remainder(this.value, Preconditions.checkNotNull(unsignedLong).value));
    }

    public UnsignedLong plus(UnsignedLong unsignedLong) {
        return UnsignedLong.fromLongBits(this.value + Preconditions.checkNotNull(unsignedLong).value);
    }

    public UnsignedLong times(UnsignedLong unsignedLong) {
        return UnsignedLong.fromLongBits(this.value * Preconditions.checkNotNull(unsignedLong).value);
    }

    public String toString() {
        return UnsignedLongs.toString(this.value);
    }

    public String toString(int n) {
        return UnsignedLongs.toString(this.value, n);
    }
}

