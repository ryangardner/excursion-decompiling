/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.encoder;

public final class Dimensions {
    private final int maxCols;
    private final int maxRows;
    private final int minCols;
    private final int minRows;

    public Dimensions(int n, int n2, int n3, int n4) {
        this.minCols = n;
        this.maxCols = n2;
        this.minRows = n3;
        this.maxRows = n4;
    }

    public int getMaxCols() {
        return this.maxCols;
    }

    public int getMaxRows() {
        return this.maxRows;
    }

    public int getMinCols() {
        return this.minCols;
    }

    public int getMinRows() {
        return this.minRows;
    }
}

