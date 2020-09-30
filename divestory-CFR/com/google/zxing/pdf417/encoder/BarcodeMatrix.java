/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.encoder;

import com.google.zxing.pdf417.encoder.BarcodeRow;

public final class BarcodeMatrix {
    private int currentRow;
    private final int height;
    private final BarcodeRow[] matrix;
    private final int width;

    BarcodeMatrix(int n, int n2) {
        BarcodeRow[] arrbarcodeRow = new BarcodeRow[n];
        this.matrix = arrbarcodeRow;
        int n3 = arrbarcodeRow.length;
        int n4 = 0;
        do {
            if (n4 >= n3) {
                this.width = n2 * 17;
                this.height = n;
                this.currentRow = -1;
                return;
            }
            this.matrix[n4] = new BarcodeRow((n2 + 4) * 17 + 1);
            ++n4;
        } while (true);
    }

    BarcodeRow getCurrentRow() {
        return this.matrix[this.currentRow];
    }

    public byte[][] getMatrix() {
        return this.getScaledMatrix(1, 1);
    }

    public byte[][] getScaledMatrix(int n, int n2) {
        int n3 = this.height;
        int n4 = this.width;
        int n5 = 0;
        byte[][] arrby = new byte[n3 * n2][n4 * n];
        n4 = this.height * n2;
        while (n5 < n4) {
            arrby[n4 - n5 - 1] = this.matrix[n5 / n2].getScaledRow(n);
            ++n5;
        }
        return arrby;
    }

    void set(int n, int n2, byte by) {
        this.matrix[n2].set(n, by);
    }

    void startRow() {
        ++this.currentRow;
    }
}

