/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.encoder;

public final class ByteMatrix {
    private final byte[][] bytes;
    private final int height;
    private final int width;

    public ByteMatrix(int n, int n2) {
        this.bytes = new byte[n2][n];
        this.width = n;
        this.height = n2;
    }

    public void clear(byte by) {
        int n = 0;
        while (n < this.height) {
            for (int i = 0; i < this.width; ++i) {
                this.bytes[n][i] = by;
            }
            ++n;
        }
    }

    public byte get(int n, int n2) {
        return this.bytes[n2][n];
    }

    public byte[][] getArray() {
        return this.bytes;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void set(int n, int n2, byte by) {
        this.bytes[n2][n] = by;
    }

    public void set(int n, int n2, int n3) {
        this.bytes[n2][n] = (byte)n3;
    }

    public void set(int n, int n2, boolean bl) {
        this.bytes[n2][n] = (byte)(bl ? 1 : 0);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.width * 2 * this.height + 2);
        int n = 0;
        while (n < this.height) {
            for (int i = 0; i < this.width; ++i) {
                byte by = this.bytes[n][i];
                if (by != 0) {
                    if (by != 1) {
                        stringBuilder.append("  ");
                        continue;
                    }
                    stringBuilder.append(" 1");
                    continue;
                }
                stringBuilder.append(" 0");
            }
            stringBuilder.append('\n');
            ++n;
        }
        return stringBuilder.toString();
    }
}

