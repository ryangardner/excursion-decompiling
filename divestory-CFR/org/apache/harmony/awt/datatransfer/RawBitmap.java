/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.harmony.awt.datatransfer;

public final class RawBitmap {
    public final int bMask;
    public final int bits;
    public final Object buffer;
    public final int gMask;
    public final int height;
    public final int rMask;
    public final int stride;
    public final int width;

    public RawBitmap(int n, int n2, int n3, int n4, int n5, int n6, int n7, Object object) {
        this.width = n;
        this.height = n2;
        this.stride = n3;
        this.bits = n4;
        this.rMask = n5;
        this.gMask = n6;
        this.bMask = n7;
        this.buffer = object;
    }

    public RawBitmap(int[] arrn, Object object) {
        this.width = arrn[0];
        this.height = arrn[1];
        this.stride = arrn[2];
        this.bits = arrn[3];
        this.rMask = arrn[4];
        this.gMask = arrn[5];
        this.bMask = arrn[6];
        this.buffer = object;
    }

    public int[] getHeader() {
        return new int[]{this.width, this.height, this.stride, this.bits, this.rMask, this.gMask, this.bMask};
    }
}

