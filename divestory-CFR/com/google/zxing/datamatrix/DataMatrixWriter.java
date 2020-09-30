/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Dimension;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.DefaultPlacement;
import com.google.zxing.datamatrix.encoder.ErrorCorrection;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import java.util.Map;

public final class DataMatrixWriter
implements Writer {
    private static BitMatrix convertByteMatrixToBitMatrix(ByteMatrix byteMatrix) {
        int n = byteMatrix.getWidth();
        int n2 = byteMatrix.getHeight();
        BitMatrix bitMatrix = new BitMatrix(n, n2);
        bitMatrix.clear();
        int n3 = 0;
        while (n3 < n) {
            for (int i = 0; i < n2; ++i) {
                if (byteMatrix.get(n3, i) != 1) continue;
                bitMatrix.set(n3, i);
            }
            ++n3;
        }
        return bitMatrix;
    }

    private static BitMatrix encodeLowLevel(DefaultPlacement defaultPlacement, SymbolInfo symbolInfo) {
        int n = symbolInfo.getSymbolDataWidth();
        int n2 = symbolInfo.getSymbolDataHeight();
        ByteMatrix byteMatrix = new ByteMatrix(symbolInfo.getSymbolWidth(), symbolInfo.getSymbolHeight());
        int n3 = 0;
        int n4 = 0;
        while (n3 < n2) {
            boolean bl;
            int n5;
            int n6 = n4;
            if (n3 % symbolInfo.matrixHeight == 0) {
                n5 = 0;
                for (n6 = 0; n6 < symbolInfo.getSymbolWidth(); ++n5, ++n6) {
                    bl = n6 % 2 == 0;
                    byteMatrix.set(n5, n4, bl);
                }
                n6 = n4 + 1;
            }
            n4 = 0;
            for (n5 = 0; n5 < n; ++n5) {
                int n7 = n4;
                if (n5 % symbolInfo.matrixWidth == 0) {
                    byteMatrix.set(n4, n6, true);
                    n7 = n4 + 1;
                }
                byteMatrix.set(n7, n6, defaultPlacement.getBit(n5, n3));
                n4 = ++n7;
                if (n5 % symbolInfo.matrixWidth != symbolInfo.matrixWidth - 1) continue;
                bl = n3 % 2 == 0;
                byteMatrix.set(n7, n6, bl);
                n4 = n7 + 1;
            }
            n4 = n5 = n6 + 1;
            if (n3 % symbolInfo.matrixHeight == symbolInfo.matrixHeight - 1) {
                n4 = 0;
                for (n6 = 0; n6 < symbolInfo.getSymbolWidth(); ++n4, ++n6) {
                    byteMatrix.set(n4, n5, true);
                }
                n4 = n5 + 1;
            }
            ++n3;
        }
        return DataMatrixWriter.convertByteMatrixToBitMatrix(byteMatrix);
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2) {
        return this.encode(string2, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String object, BarcodeFormat object2, int n, int n2, Map<EncodeHintType, ?> object3) {
        if (((String)object).isEmpty()) throw new IllegalArgumentException("Found empty contents");
        if (object2 != BarcodeFormat.DATA_MATRIX) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Can only encode DATA_MATRIX, but got ");
            ((StringBuilder)object).append(object2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (n >= 0 && n2 >= 0) {
            object2 = SymbolShapeHint.FORCE_NONE;
            Object object4 = null;
            if (object3 != null) {
                object4 = (SymbolShapeHint)((Object)object3.get((Object)EncodeHintType.DATA_MATRIX_SHAPE));
                if (object4 != null) {
                    object2 = object4;
                }
                if ((object4 = (Dimension)object3.get((Object)EncodeHintType.MIN_SIZE)) == null) {
                    object4 = null;
                }
                object3 = (Dimension)object3.get((Object)EncodeHintType.MAX_SIZE);
                if (object3 == null) {
                    object3 = null;
                }
            } else {
                object3 = null;
            }
            object = HighLevelEncoder.encodeHighLevel((String)object, (SymbolShapeHint)((Object)object2), (Dimension)object4, object3);
            object2 = SymbolInfo.lookup(((String)object).length(), (SymbolShapeHint)((Object)object2), (Dimension)object4, object3, true);
            object = new DefaultPlacement(ErrorCorrection.encodeECC200((String)object, (SymbolInfo)object2), ((SymbolInfo)object2).getSymbolDataWidth(), ((SymbolInfo)object2).getSymbolDataHeight());
            ((DefaultPlacement)object).place();
            return DataMatrixWriter.encodeLowLevel((DefaultPlacement)object, (SymbolInfo)object2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Requested dimensions are too small: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append('x');
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }
}

