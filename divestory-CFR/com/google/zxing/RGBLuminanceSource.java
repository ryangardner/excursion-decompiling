/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class RGBLuminanceSource
extends LuminanceSource {
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final byte[] luminances;
    private final int top;

    public RGBLuminanceSource(int n, int n2, int[] arrn) {
        super(n, n2);
        this.dataWidth = n;
        this.dataHeight = n2;
        this.left = 0;
        this.top = 0;
        this.luminances = new byte[n * n2];
        int n3 = 0;
        while (n3 < n2) {
            for (int i = 0; i < n; ++i) {
                int n4 = n3 * n + i;
                int n5 = arrn[n4];
                int n6 = n5 >> 16 & 255;
                int n7 = n5 >> 8 & 255;
                this.luminances[n4] = n6 == n7 && n7 == n5 ? (byte)((byte)n6) : (byte)((byte)((n6 + n7 * 2 + (n5 &= 255)) / 4));
            }
            ++n3;
        }
    }

    private RGBLuminanceSource(byte[] arrby, int n, int n2, int n3, int n4, int n5, int n6) {
        super(n5, n6);
        if (n5 + n3 > n) throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        if (n6 + n4 > n2) throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        this.luminances = arrby;
        this.dataWidth = n;
        this.dataHeight = n2;
        this.left = n3;
        this.top = n4;
    }

    @Override
    public LuminanceSource crop(int n, int n2, int n3, int n4) {
        return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + n, this.top + n2, n3, n4);
    }

    @Override
    public byte[] getMatrix() {
        int n = this.getWidth();
        int n2 = this.getHeight();
        if (n == this.dataWidth && n2 == this.dataHeight) {
            return this.luminances;
        }
        int n3 = n * n2;
        byte[] arrby = new byte[n3];
        int n4 = this.top;
        int n5 = this.dataWidth;
        int n6 = n4 * n5 + this.left;
        n4 = 0;
        if (n == n5) {
            System.arraycopy(this.luminances, n6, arrby, 0, n3);
            return arrby;
        }
        byte[] arrby2 = this.luminances;
        while (n4 < n2) {
            System.arraycopy(arrby2, n6, arrby, n4 * n, n);
            n6 += this.dataWidth;
            ++n4;
        }
        return arrby;
    }

    @Override
    public byte[] getRow(int n, byte[] object) {
        block2 : {
            byte[] arrby;
            int n2;
            block4 : {
                block3 : {
                    if (n < 0 || n >= this.getHeight()) break block2;
                    n2 = this.getWidth();
                    if (object == null) break block3;
                    arrby = object;
                    if (((Object)object).length >= n2) break block4;
                }
                arrby = new byte[n2];
            }
            int n3 = this.top;
            int n4 = this.dataWidth;
            int n5 = this.left;
            System.arraycopy(this.luminances, (n + n3) * n4 + n5, arrby, 0, n2);
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Requested row is outside the image: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }
}

