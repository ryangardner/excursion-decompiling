/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.DataMatrixSymbolInfo144;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;

public class SymbolInfo {
    static final SymbolInfo[] PROD_SYMBOLS;
    private static SymbolInfo[] symbols;
    private final int dataCapacity;
    private final int dataRegions;
    private final int errorCodewords;
    public final int matrixHeight;
    public final int matrixWidth;
    private final boolean rectangular;
    private final int rsBlockData;
    private final int rsBlockError;

    static {
        SymbolInfo[] arrsymbolInfo = new SymbolInfo[]{new SymbolInfo(false, 3, 5, 8, 8, 1), new SymbolInfo(false, 5, 7, 10, 10, 1), new SymbolInfo(true, 5, 7, 16, 6, 1), new SymbolInfo(false, 8, 10, 12, 12, 1), new SymbolInfo(true, 10, 11, 14, 6, 2), new SymbolInfo(false, 12, 12, 14, 14, 1), new SymbolInfo(true, 16, 14, 24, 10, 1), new SymbolInfo(false, 18, 14, 16, 16, 1), new SymbolInfo(false, 22, 18, 18, 18, 1), new SymbolInfo(true, 22, 18, 16, 10, 2), new SymbolInfo(false, 30, 20, 20, 20, 1), new SymbolInfo(true, 32, 24, 16, 14, 2), new SymbolInfo(false, 36, 24, 22, 22, 1), new SymbolInfo(false, 44, 28, 24, 24, 1), new SymbolInfo(true, 49, 28, 22, 14, 2), new SymbolInfo(false, 62, 36, 14, 14, 4), new SymbolInfo(false, 86, 42, 16, 16, 4), new SymbolInfo(false, 114, 48, 18, 18, 4), new SymbolInfo(false, 144, 56, 20, 20, 4), new SymbolInfo(false, 174, 68, 22, 22, 4), new SymbolInfo(false, 204, 84, 24, 24, 4, 102, 42), new SymbolInfo(false, 280, 112, 14, 14, 16, 140, 56), new SymbolInfo(false, 368, 144, 16, 16, 16, 92, 36), new SymbolInfo(false, 456, 192, 18, 18, 16, 114, 48), new SymbolInfo(false, 576, 224, 20, 20, 16, 144, 56), new SymbolInfo(false, 696, 272, 22, 22, 16, 174, 68), new SymbolInfo(false, 816, 336, 24, 24, 16, 136, 56), new SymbolInfo(false, 1050, 408, 18, 18, 36, 175, 68), new SymbolInfo(false, 1304, 496, 20, 20, 36, 163, 62), new DataMatrixSymbolInfo144()};
        PROD_SYMBOLS = arrsymbolInfo;
        symbols = arrsymbolInfo;
    }

    public SymbolInfo(boolean bl, int n, int n2, int n3, int n4, int n5) {
        this(bl, n, n2, n3, n4, n5, n, n2);
    }

    SymbolInfo(boolean bl, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.rectangular = bl;
        this.dataCapacity = n;
        this.errorCodewords = n2;
        this.matrixWidth = n3;
        this.matrixHeight = n4;
        this.dataRegions = n5;
        this.rsBlockData = n6;
        this.rsBlockError = n7;
    }

    public static SymbolInfo lookup(int n) {
        return SymbolInfo.lookup(n, SymbolShapeHint.FORCE_NONE, true);
    }

    public static SymbolInfo lookup(int n, SymbolShapeHint symbolShapeHint) {
        return SymbolInfo.lookup(n, symbolShapeHint, true);
    }

    public static SymbolInfo lookup(int n, SymbolShapeHint object, Dimension dimension, Dimension dimension2, boolean bl) {
        for (SymbolInfo symbolInfo : symbols) {
            if (object == SymbolShapeHint.FORCE_SQUARE && symbolInfo.rectangular || object == SymbolShapeHint.FORCE_RECTANGLE && !symbolInfo.rectangular || dimension != null && (symbolInfo.getSymbolWidth() < dimension.getWidth() || symbolInfo.getSymbolHeight() < dimension.getHeight()) || dimension2 != null && (symbolInfo.getSymbolWidth() > dimension2.getWidth() || symbolInfo.getSymbolHeight() > dimension2.getHeight()) || n > symbolInfo.dataCapacity) continue;
            return symbolInfo;
        }
        if (!bl) {
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Can't find a symbol arrangement that matches the message. Data codewords: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static SymbolInfo lookup(int n, SymbolShapeHint symbolShapeHint, boolean bl) {
        return SymbolInfo.lookup(n, symbolShapeHint, null, null, bl);
    }

    public static SymbolInfo lookup(int n, boolean bl, boolean bl2) {
        SymbolShapeHint symbolShapeHint;
        if (bl) {
            symbolShapeHint = SymbolShapeHint.FORCE_NONE;
            return SymbolInfo.lookup(n, symbolShapeHint, bl2);
        }
        symbolShapeHint = SymbolShapeHint.FORCE_SQUARE;
        return SymbolInfo.lookup(n, symbolShapeHint, bl2);
    }

    public static void overrideSymbolSet(SymbolInfo[] arrsymbolInfo) {
        symbols = arrsymbolInfo;
    }

    public int getCodewordCount() {
        return this.dataCapacity + this.errorCodewords;
    }

    public final int getDataCapacity() {
        return this.dataCapacity;
    }

    public int getDataLengthForInterleavedBlock(int n) {
        return this.rsBlockData;
    }

    public final int getErrorCodewords() {
        return this.errorCodewords;
    }

    public final int getErrorLengthForInterleavedBlock(int n) {
        return this.rsBlockError;
    }

    final int getHorizontalDataRegions() {
        int n;
        int n2 = this.dataRegions;
        int n3 = 1;
        if (n2 == 1) return n3;
        n3 = n = 2;
        if (n2 == 2) return n3;
        n3 = n;
        if (n2 == 4) return n3;
        if (n2 == 16) return 4;
        if (n2 != 36) throw new IllegalStateException("Cannot handle this number of data regions");
        return 6;
    }

    public int getInterleavedBlockCount() {
        return this.dataCapacity / this.rsBlockData;
    }

    public final int getSymbolDataHeight() {
        return this.getVerticalDataRegions() * this.matrixHeight;
    }

    public final int getSymbolDataWidth() {
        return this.getHorizontalDataRegions() * this.matrixWidth;
    }

    public final int getSymbolHeight() {
        return this.getSymbolDataHeight() + this.getVerticalDataRegions() * 2;
    }

    public final int getSymbolWidth() {
        return this.getSymbolDataWidth() + this.getHorizontalDataRegions() * 2;
    }

    final int getVerticalDataRegions() {
        int n = this.dataRegions;
        if (n == 1) return 1;
        if (n == 2) return 1;
        if (n == 4) return 2;
        if (n == 16) return 4;
        if (n != 36) throw new IllegalStateException("Cannot handle this number of data regions");
        return 6;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.rectangular ? "Rectangular Symbol:" : "Square Symbol:";
        stringBuilder.append(string2);
        stringBuilder.append(" data region ");
        stringBuilder.append(this.matrixWidth);
        stringBuilder.append('x');
        stringBuilder.append(this.matrixHeight);
        stringBuilder.append(", symbol size ");
        stringBuilder.append(this.getSymbolWidth());
        stringBuilder.append('x');
        stringBuilder.append(this.getSymbolHeight());
        stringBuilder.append(", symbol data size ");
        stringBuilder.append(this.getSymbolDataWidth());
        stringBuilder.append('x');
        stringBuilder.append(this.getSymbolDataHeight());
        stringBuilder.append(", codewords ");
        stringBuilder.append(this.dataCapacity);
        stringBuilder.append('+');
        stringBuilder.append(this.errorCodewords);
        return stringBuilder.toString();
    }
}

