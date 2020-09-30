/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

final class BarcodeMetadata {
    private final int columnCount;
    private final int errorCorrectionLevel;
    private final int rowCount;
    private final int rowCountLowerPart;
    private final int rowCountUpperPart;

    BarcodeMetadata(int n, int n2, int n3, int n4) {
        this.columnCount = n;
        this.errorCorrectionLevel = n4;
        this.rowCountUpperPart = n2;
        this.rowCountLowerPart = n3;
        this.rowCount = n2 + n3;
    }

    int getColumnCount() {
        return this.columnCount;
    }

    int getErrorCorrectionLevel() {
        return this.errorCorrectionLevel;
    }

    int getRowCount() {
        return this.rowCount;
    }

    int getRowCountLowerPart() {
        return this.rowCountLowerPart;
    }

    int getRowCountUpperPart() {
        return this.rowCountUpperPart;
    }
}

