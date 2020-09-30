/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

public final class BitSource {
    private int bitOffset;
    private int byteOffset;
    private final byte[] bytes;

    public BitSource(byte[] arrby) {
        this.bytes = arrby;
    }

    public int available() {
        return (this.bytes.length - this.byteOffset) * 8 - this.bitOffset;
    }

    public int getBitOffset() {
        return this.bitOffset;
    }

    public int getByteOffset() {
        return this.byteOffset;
    }

    public int readBits(int n) {
        byte[] arrby;
        if (n < 1) throw new IllegalArgumentException(String.valueOf(n));
        if (n > 32) throw new IllegalArgumentException(String.valueOf(n));
        if (n > this.available()) throw new IllegalArgumentException(String.valueOf(n));
        int n2 = this.bitOffset;
        int n3 = 0;
        int n4 = n;
        if (n2 > 0) {
            n4 = 8 - n2;
            n3 = n < n4 ? n : n4;
            int n5 = n4 - n3;
            arrby = this.bytes;
            int n6 = this.byteOffset;
            n2 = arrby[n6];
            n4 = n - n3;
            this.bitOffset = n = this.bitOffset + n3;
            if (n == 8) {
                this.bitOffset = 0;
                this.byteOffset = n6 + 1;
            }
            n3 = (255 >> 8 - n3 << n5 & n2) >> n5;
        }
        n = n3;
        if (n4 <= 0) return n;
        do {
            if (n4 < 8) {
                n = n3;
                if (n4 <= 0) return n;
                n = 8 - n4;
                n = n3 << n4 | (255 >> n << n & this.bytes[this.byteOffset]) >> n;
                this.bitOffset += n4;
                return n;
            }
            arrby = this.bytes;
            n = this.byteOffset;
            n3 = arrby[n] & 255 | n3 << 8;
            this.byteOffset = n + 1;
            n4 -= 8;
        } while (true);
    }
}

