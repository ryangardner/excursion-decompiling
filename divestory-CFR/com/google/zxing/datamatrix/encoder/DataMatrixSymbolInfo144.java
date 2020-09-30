/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.SymbolInfo;

final class DataMatrixSymbolInfo144
extends SymbolInfo {
    DataMatrixSymbolInfo144() {
        super(false, 1558, 620, 22, 22, 36, -1, 62);
    }

    @Override
    public int getDataLengthForInterleavedBlock(int n) {
        if (n > 8) return 155;
        return 156;
    }

    @Override
    public int getInterleavedBlockCount() {
        return 10;
    }
}

