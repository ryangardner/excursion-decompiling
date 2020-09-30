/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.InvertedLuminanceSource;

public abstract class LuminanceSource {
    private final int height;
    private final int width;

    protected LuminanceSource(int n, int n2) {
        this.width = n;
        this.height = n2;
    }

    public LuminanceSource crop(int n, int n2, int n3, int n4) {
        throw new UnsupportedOperationException("This luminance source does not support cropping.");
    }

    public final int getHeight() {
        return this.height;
    }

    public abstract byte[] getMatrix();

    public abstract byte[] getRow(int var1, byte[] var2);

    public final int getWidth() {
        return this.width;
    }

    public LuminanceSource invert() {
        return new InvertedLuminanceSource(this);
    }

    public boolean isCropSupported() {
        return false;
    }

    public boolean isRotateSupported() {
        return false;
    }

    public LuminanceSource rotateCounterClockwise() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 90 degrees.");
    }

    public LuminanceSource rotateCounterClockwise45() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 45 degrees.");
    }

    public final String toString() {
        byte[] arrby = new byte[this.width];
        StringBuilder stringBuilder = new StringBuilder(this.height * (this.width + 1));
        int n = 0;
        while (n < this.height) {
            arrby = this.getRow(n, arrby);
            for (int i = 0; i < this.width; ++i) {
                int n2 = arrby[i] & 255;
                int n3 = n2 < 64 ? (n2 = 35) : (n2 < 128 ? (n2 = 43) : (n2 < 192 ? (n2 = 46) : (n2 = 32)));
                stringBuilder.append((char)n3);
            }
            stringBuilder.append('\n');
            ++n;
        }
        return stringBuilder.toString();
    }
}

