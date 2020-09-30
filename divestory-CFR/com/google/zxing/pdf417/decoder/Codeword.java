/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

final class Codeword {
    private static final int BARCODE_ROW_UNKNOWN = -1;
    private final int bucket;
    private final int endX;
    private int rowNumber = -1;
    private final int startX;
    private final int value;

    Codeword(int n, int n2, int n3, int n4) {
        this.startX = n;
        this.endX = n2;
        this.bucket = n3;
        this.value = n4;
    }

    int getBucket() {
        return this.bucket;
    }

    int getEndX() {
        return this.endX;
    }

    int getRowNumber() {
        return this.rowNumber;
    }

    int getStartX() {
        return this.startX;
    }

    int getValue() {
        return this.value;
    }

    int getWidth() {
        return this.endX - this.startX;
    }

    boolean hasValidRowNumber() {
        return this.isValidRowNumber(this.rowNumber);
    }

    boolean isValidRowNumber(int n) {
        if (n == -1) return false;
        if (this.bucket != n % 3 * 3) return false;
        return true;
    }

    void setRowNumber(int n) {
        this.rowNumber = n;
    }

    void setRowNumberAsRowIndicatorColumn() {
        this.rowNumber = this.value / 30 * 3 + this.bucket / 3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.rowNumber);
        stringBuilder.append("|");
        stringBuilder.append(this.value);
        return stringBuilder.toString();
    }
}

