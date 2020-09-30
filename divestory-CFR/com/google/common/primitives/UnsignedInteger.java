/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedInts;
import java.math.BigInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class UnsignedInteger
extends Number
implements Comparable<UnsignedInteger> {
    public static final UnsignedInteger MAX_VALUE;
    public static final UnsignedInteger ONE;
    public static final UnsignedInteger ZERO;
    private final int value;

    static {
        ZERO = UnsignedInteger.fromIntBits(0);
        ONE = UnsignedInteger.fromIntBits(1);
        MAX_VALUE = UnsignedInteger.fromIntBits(-1);
    }

    private UnsignedInteger(int n) {
        this.value = n & -1;
    }

    public static UnsignedInteger fromIntBits(int n) {
        return new UnsignedInteger(n);
    }

    public static UnsignedInteger valueOf(long l) {
        boolean bl = (0xFFFFFFFFL & l) == l;
        Preconditions.checkArgument(bl, "value (%s) is outside the range for an unsigned integer value", l);
        return UnsignedInteger.fromIntBits((int)l);
    }

    public static UnsignedInteger valueOf(String string2) {
        return UnsignedInteger.valueOf(string2, 10);
    }

    public static UnsignedInteger valueOf(String string2, int n) {
        return UnsignedInteger.fromIntBits(UnsignedInts.parseUnsignedInt(string2, n));
    }

    public static UnsignedInteger valueOf(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        boolean bl = bigInteger.signum() >= 0 && bigInteger.bitLength() <= 32;
        Preconditions.checkArgument(bl, "value (%s) is outside the range for an unsigned integer value", (Object)bigInteger);
        return UnsignedInteger.fromIntBits(bigInteger.intValue());
    }

    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(this.longValue());
    }

    @Override
    public int compareTo(UnsignedInteger unsignedInteger) {
        Preconditions.checkNotNull(unsignedInteger);
        return UnsignedInts.compare(this.value, unsignedInteger.value);
    }

    public UnsignedInteger dividedBy(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(UnsignedInts.divide(this.value, Preconditions.checkNotNull(unsignedInteger).value));
    }

    @Override
    public double doubleValue() {
        return this.longValue();
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof UnsignedInteger;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (UnsignedInteger)object;
        bl3 = bl;
        if (this.value != ((UnsignedInteger)object).value) return bl3;
        return true;
    }

    @Override
    public float floatValue() {
        return this.longValue();
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return UnsignedInts.toLong(this.value);
    }

    public UnsignedInteger minus(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(this.value - Preconditions.checkNotNull(unsignedInteger).value);
    }

    public UnsignedInteger mod(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(UnsignedInts.remainder(this.value, Preconditions.checkNotNull(unsignedInteger).value));
    }

    public UnsignedInteger plus(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(this.value + Preconditions.checkNotNull(unsignedInteger).value);
    }

    public UnsignedInteger times(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(this.value * Preconditions.checkNotNull(unsignedInteger).value);
    }

    public String toString() {
        return this.toString(10);
    }

    public String toString(int n) {
        return UnsignedInts.toString(this.value, n);
    }
}

