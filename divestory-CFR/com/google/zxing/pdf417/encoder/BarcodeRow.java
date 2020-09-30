/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.encoder;

final class BarcodeRow {
    private int currentLocation;
    private final byte[] row;

    BarcodeRow(int n) {
        this.row = new byte[n];
        this.currentLocation = 0;
    }

    void addBar(boolean bl, int n) {
        int n2 = 0;
        while (n2 < n) {
            int n3 = this.currentLocation;
            this.currentLocation = n3 + 1;
            this.set(n3, bl);
            ++n2;
        }
    }

    byte[] getScaledRow(int n) {
        int n2 = this.row.length * n;
        byte[] arrby = new byte[n2];
        int n3 = 0;
        while (n3 < n2) {
            arrby[n3] = this.row[n3 / n];
            ++n3;
        }
        return arrby;
    }

    void set(int n, byte by) {
        this.row[n] = by;
    }

    void set(int n, boolean bl) {
        this.row[n] = (byte)(bl ? 1 : 0);
    }
}

