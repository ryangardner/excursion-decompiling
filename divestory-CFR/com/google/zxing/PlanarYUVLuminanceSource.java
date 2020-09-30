/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

import com.google.zxing.LuminanceSource;

public final class PlanarYUVLuminanceSource
extends LuminanceSource {
    private static final int THUMBNAIL_SCALE_FACTOR = 2;
    private final int dataHeight;
    private final int dataWidth;
    private final int left;
    private final int top;
    private final byte[] yuvData;

    public PlanarYUVLuminanceSource(byte[] arrby, int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        super(n5, n6);
        if (n3 + n5 > n) throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        if (n4 + n6 > n2) throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        this.yuvData = arrby;
        this.dataWidth = n;
        this.dataHeight = n2;
        this.left = n3;
        this.top = n4;
        if (!bl) return;
        this.reverseHorizontal(n5, n6);
    }

    private void reverseHorizontal(int n, int n2) {
        byte[] arrby = this.yuvData;
        int n3 = this.top * this.dataWidth + this.left;
        int n4 = 0;
        while (n4 < n2) {
            int n5 = n / 2;
            int n6 = n3 + n - 1;
            for (int i = n3; i < n5 + n3; ++i, --n6) {
                byte by = arrby[i];
                arrby[i] = arrby[n6];
                arrby[n6] = by;
            }
            ++n4;
            n3 += this.dataWidth;
        }
    }

    @Override
    public LuminanceSource crop(int n, int n2, int n3, int n4) {
        return new PlanarYUVLuminanceSource(this.yuvData, this.dataWidth, this.dataHeight, this.left + n, this.top + n2, n3, n4, false);
    }

    @Override
    public byte[] getMatrix() {
        int n = this.getWidth();
        int n2 = this.getHeight();
        if (n == this.dataWidth && n2 == this.dataHeight) {
            return this.yuvData;
        }
        int n3 = n * n2;
        byte[] arrby = new byte[n3];
        int n4 = this.top;
        int n5 = this.dataWidth;
        int n6 = n4 * n5 + this.left;
        n4 = 0;
        if (n == n5) {
            System.arraycopy(this.yuvData, n6, arrby, 0, n3);
            return arrby;
        }
        byte[] arrby2 = this.yuvData;
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
            System.arraycopy(this.yuvData, (n + n3) * n4 + n5, arrby, 0, n2);
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Requested row is outside the image: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public int getThumbnailHeight() {
        return this.getHeight() / 2;
    }

    public int getThumbnailWidth() {
        return this.getWidth() / 2;
    }

    @Override
    public boolean isCropSupported() {
        return true;
    }

    public int[] renderThumbnail() {
        int n = this.getWidth() / 2;
        int n2 = this.getHeight() / 2;
        int[] arrn = new int[n * n2];
        byte[] arrby = this.yuvData;
        int n3 = this.top * this.dataWidth + this.left;
        int n4 = 0;
        while (n4 < n2) {
            for (int i = 0; i < n; ++i) {
                arrn[n4 * n + i] = (arrby[i * 2 + n3] & 255) * 65793 | -16777216;
            }
            n3 += this.dataWidth * 2;
            ++n4;
        }
        return arrn;
    }
}

