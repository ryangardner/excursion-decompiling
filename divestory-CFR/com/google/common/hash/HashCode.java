/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedInts;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class HashCode {
    private static final char[] hexDigits = "0123456789abcdef".toCharArray();

    HashCode() {
    }

    private static int decode(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal hexadecimal character: ");
        stringBuilder.append(c);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static HashCode fromBytes(byte[] arrby) {
        int n = arrby.length;
        boolean bl = true;
        if (n < 1) {
            bl = false;
        }
        Preconditions.checkArgument(bl, "A HashCode must contain at least 1 byte.");
        return HashCode.fromBytesNoCopy((byte[])arrby.clone());
    }

    static HashCode fromBytesNoCopy(byte[] arrby) {
        return new BytesHashCode(arrby);
    }

    public static HashCode fromInt(int n) {
        return new IntHashCode(n);
    }

    public static HashCode fromLong(long l) {
        return new LongHashCode(l);
    }

    public static HashCode fromString(String string2) {
        int n = string2.length();
        boolean bl = true;
        int n2 = 0;
        boolean bl2 = n >= 2;
        Preconditions.checkArgument(bl2, "input string (%s) must have at least 2 characters", (Object)string2);
        bl2 = string2.length() % 2 == 0 ? bl : false;
        Preconditions.checkArgument(bl2, "input string (%s) must have an even number of characters", (Object)string2);
        byte[] arrby = new byte[string2.length() / 2];
        while (n2 < string2.length()) {
            int n3 = HashCode.decode(string2.charAt(n2));
            n = HashCode.decode(string2.charAt(n2 + 1));
            arrby[n2 / 2] = (byte)((n3 << 4) + n);
            n2 += 2;
        }
        return HashCode.fromBytesNoCopy(arrby);
    }

    public abstract byte[] asBytes();

    public abstract int asInt();

    public abstract long asLong();

    public abstract int bits();

    public final boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof HashCode;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (HashCode)object;
        bl3 = bl;
        if (this.bits() != ((HashCode)object).bits()) return bl3;
        bl3 = bl;
        if (!this.equalsSameBits((HashCode)object)) return bl3;
        return true;
    }

    abstract boolean equalsSameBits(HashCode var1);

    byte[] getBytesInternal() {
        return this.asBytes();
    }

    public final int hashCode() {
        if (this.bits() >= 32) {
            return this.asInt();
        }
        byte[] arrby = this.getBytesInternal();
        int n = arrby[0] & 255;
        int n2 = 1;
        while (n2 < arrby.length) {
            n |= (arrby[n2] & 255) << n2 * 8;
            ++n2;
        }
        return n;
    }

    public abstract long padToLong();

    public final String toString() {
        byte[] arrby = this.getBytesInternal();
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 2);
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            byte by = arrby[n2];
            stringBuilder.append(hexDigits[by >> 4 & 15]);
            stringBuilder.append(hexDigits[by & 15]);
            ++n2;
        }
        return stringBuilder.toString();
    }

    public int writeBytesTo(byte[] arrby, int n, int n2) {
        n2 = Ints.min(n2, this.bits() / 8);
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        this.writeBytesToImpl(arrby, n, n2);
        return n2;
    }

    abstract void writeBytesToImpl(byte[] var1, int var2, int var3);

    private static final class BytesHashCode
    extends HashCode
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final byte[] bytes;

        BytesHashCode(byte[] arrby) {
            this.bytes = Preconditions.checkNotNull(arrby);
        }

        @Override
        public byte[] asBytes() {
            return (byte[])this.bytes.clone();
        }

        @Override
        public int asInt() {
            boolean bl = this.bytes.length >= 4;
            Preconditions.checkState(bl, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", this.bytes.length);
            byte[] arrby = this.bytes;
            byte by = arrby[0];
            byte by2 = arrby[1];
            byte by3 = arrby[2];
            return (arrby[3] & 255) << 24 | ((by2 & 255) << 8 | by & 255 | (by3 & 255) << 16);
        }

        @Override
        public long asLong() {
            boolean bl = this.bytes.length >= 8;
            Preconditions.checkState(bl, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", this.bytes.length);
            return this.padToLong();
        }

        @Override
        public int bits() {
            return this.bytes.length * 8;
        }

        @Override
        boolean equalsSameBits(HashCode hashCode) {
            byte[] arrby;
            if (this.bytes.length != hashCode.getBytesInternal().length) {
                return false;
            }
            int n = 0;
            boolean bl = true;
            while (n < (arrby = this.bytes).length) {
                boolean bl2 = arrby[n] == hashCode.getBytesInternal()[n];
                bl &= bl2;
                ++n;
            }
            return bl;
        }

        @Override
        byte[] getBytesInternal() {
            return this.bytes;
        }

        @Override
        public long padToLong() {
            long l = this.bytes[0] & 255;
            int n = 1;
            while (n < Math.min(this.bytes.length, 8)) {
                l |= ((long)this.bytes[n] & 255L) << n * 8;
                ++n;
            }
            return l;
        }

        @Override
        void writeBytesToImpl(byte[] arrby, int n, int n2) {
            System.arraycopy(this.bytes, 0, arrby, n, n2);
        }
    }

    private static final class IntHashCode
    extends HashCode
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final int hash;

        IntHashCode(int n) {
            this.hash = n;
        }

        @Override
        public byte[] asBytes() {
            int n = this.hash;
            return new byte[]{(byte)n, (byte)(n >> 8), (byte)(n >> 16), (byte)(n >> 24)};
        }

        @Override
        public int asInt() {
            return this.hash;
        }

        @Override
        public long asLong() {
            throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
        }

        @Override
        public int bits() {
            return 32;
        }

        @Override
        boolean equalsSameBits(HashCode hashCode) {
            if (this.hash != hashCode.asInt()) return false;
            return true;
        }

        @Override
        public long padToLong() {
            return UnsignedInts.toLong(this.hash);
        }

        @Override
        void writeBytesToImpl(byte[] arrby, int n, int n2) {
            int n3 = 0;
            while (n3 < n2) {
                arrby[n + n3] = (byte)(this.hash >> n3 * 8);
                ++n3;
            }
        }
    }

    private static final class LongHashCode
    extends HashCode
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final long hash;

        LongHashCode(long l) {
            this.hash = l;
        }

        @Override
        public byte[] asBytes() {
            long l = this.hash;
            return new byte[]{(byte)l, (byte)(l >> 8), (byte)(l >> 16), (byte)(l >> 24), (byte)(l >> 32), (byte)(l >> 40), (byte)(l >> 48), (byte)(l >> 56)};
        }

        @Override
        public int asInt() {
            return (int)this.hash;
        }

        @Override
        public long asLong() {
            return this.hash;
        }

        @Override
        public int bits() {
            return 64;
        }

        @Override
        boolean equalsSameBits(HashCode hashCode) {
            if (this.hash != hashCode.asLong()) return false;
            return true;
        }

        @Override
        public long padToLong() {
            return this.hash;
        }

        @Override
        void writeBytesToImpl(byte[] arrby, int n, int n2) {
            int n3 = 0;
            while (n3 < n2) {
                arrby[n + n3] = (byte)(this.hash >> n3 * 8);
                ++n3;
            }
        }
    }

}

