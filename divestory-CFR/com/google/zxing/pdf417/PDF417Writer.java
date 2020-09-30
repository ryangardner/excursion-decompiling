/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import java.nio.charset.Charset;
import java.util.Map;

public final class PDF417Writer
implements Writer {
    static final int DEFAULT_ERROR_CORRECTION_LEVEL = 2;
    static final int WHITE_SPACE = 30;

    private static BitMatrix bitMatrixFromEncoder(PDF417 arrby, String arrby2, int n, int n2, int n3, int n4) throws WriterException {
        arrby.generateBarcodeLogic((String)arrby2, n);
        arrby2 = arrby.getBarcodeMatrix().getScaledMatrix(1, 4);
        n = n3 > n2 ? 1 : 0;
        int n5 = arrby2[0].length < arrby2.length ? 1 : 0;
        if ((n ^ n5) != 0) {
            arrby2 = PDF417Writer.rotateArray(arrby2);
            n = 1;
        } else {
            n = 0;
        }
        if ((n2 /= arrby2[0].length) >= (n3 /= arrby2.length)) {
            n2 = n3;
        }
        if (n2 <= 1) return PDF417Writer.bitMatrixFrombitArray(arrby2, n4);
        arrby = arrby2 = arrby.getBarcodeMatrix().getScaledMatrix(n2, n2 * 4);
        if (n == 0) return PDF417Writer.bitMatrixFrombitArray(arrby, n4);
        arrby = PDF417Writer.rotateArray(arrby2);
        return PDF417Writer.bitMatrixFrombitArray(arrby, n4);
    }

    private static BitMatrix bitMatrixFrombitArray(byte[][] arrby, int n) {
        int n2 = arrby[0].length;
        int n3 = n * 2;
        BitMatrix bitMatrix = new BitMatrix(n2 + n3, arrby.length + n3);
        bitMatrix.clear();
        n2 = bitMatrix.getHeight() - n - 1;
        n3 = 0;
        while (n3 < arrby.length) {
            for (int i = 0; i < arrby[0].length; ++i) {
                if (arrby[n3][i] != 1) continue;
                bitMatrix.set(i + n, n2);
            }
            ++n3;
            --n2;
        }
        return bitMatrix;
    }

    private static byte[][] rotateArray(byte[][] arrby) {
        byte[][] arrby2 = new byte[arrby[0].length][arrby.length];
        int n = 0;
        while (n < arrby.length) {
            int n2 = arrby.length;
            for (int i = 0; i < arrby[0].length; ++i) {
                arrby2[i][n2 - n - 1] = arrby[n][i];
            }
            ++n;
        }
        return arrby2;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string2, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat object, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (object != BarcodeFormat.PDF_417) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Can only encode PDF_417, but got ");
            ((StringBuilder)charSequence).append(object);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        object = new PDF417();
        int n3 = 30;
        int n4 = 2;
        if (map == null) {
            n4 = 2;
            n3 = 30;
            return PDF417Writer.bitMatrixFromEncoder((PDF417)object, (String)charSequence, n4, n, n2, n3);
        }
        if (map.containsKey((Object)EncodeHintType.PDF417_COMPACT)) {
            ((PDF417)object).setCompact((Boolean)map.get((Object)EncodeHintType.PDF417_COMPACT));
        }
        if (map.containsKey((Object)EncodeHintType.PDF417_COMPACTION)) {
            ((PDF417)object).setCompaction((Compaction)((Object)map.get((Object)EncodeHintType.PDF417_COMPACTION)));
        }
        if (map.containsKey((Object)EncodeHintType.PDF417_DIMENSIONS)) {
            Dimensions dimensions = (Dimensions)map.get((Object)EncodeHintType.PDF417_DIMENSIONS);
            ((PDF417)object).setDimensions(dimensions.getMaxCols(), dimensions.getMinCols(), dimensions.getMaxRows(), dimensions.getMinRows());
        }
        if (map.containsKey((Object)EncodeHintType.MARGIN)) {
            n3 = ((Number)map.get((Object)EncodeHintType.MARGIN)).intValue();
        }
        if (map.containsKey((Object)EncodeHintType.ERROR_CORRECTION)) {
            n4 = ((Number)map.get((Object)EncodeHintType.ERROR_CORRECTION)).intValue();
        }
        if (!map.containsKey((Object)EncodeHintType.CHARACTER_SET)) return PDF417Writer.bitMatrixFromEncoder((PDF417)object, (String)charSequence, n4, n, n2, n3);
        ((PDF417)object).setEncoding(Charset.forName((String)map.get((Object)EncodeHintType.CHARACTER_SET)));
        return PDF417Writer.bitMatrixFromEncoder((PDF417)object, (String)charSequence, n4, n, n2, n3);
    }
}

