/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.Encoder;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.Charset;
import java.util.Map;

public final class AztecWriter
implements Writer {
    private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");

    private static BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Charset charset, int n3, int n4) {
        if (barcodeFormat == BarcodeFormat.AZTEC) {
            return AztecWriter.renderResult(Encoder.encode(((String)charSequence).getBytes(charset), n3, n4), n, n2);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Can only encode AZTEC, but got ");
        ((StringBuilder)charSequence).append((Object)barcodeFormat);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static BitMatrix renderResult(AztecCode object, int n, int n2) {
        BitMatrix bitMatrix = ((AztecCode)object).getMatrix();
        if (bitMatrix == null) throw new IllegalStateException();
        int n3 = bitMatrix.getWidth();
        int n4 = bitMatrix.getHeight();
        int n5 = Math.max(n, n3);
        n2 = Math.max(n2, n4);
        int n6 = Math.min(n5 / n3, n2 / n4);
        int n7 = (n5 - n3 * n6) / 2;
        n = (n2 - n4 * n6) / 2;
        object = new BitMatrix(n5, n2);
        n2 = 0;
        while (n2 < n4) {
            n5 = n7;
            for (int i = 0; i < n3; ++i, n5 += n6) {
                if (!bitMatrix.get(i, n2)) continue;
                ((BitMatrix)object).setRegion(n5, n, n6, n6);
            }
            ++n2;
            n += n6;
        }
        return object;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2) {
        return this.encode(string2, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> object) {
        int n3;
        Object var6_6 = null;
        Object object2 = object == null ? null : (String)object.get((Object)EncodeHintType.CHARACTER_SET);
        Number number = object == null ? (Number)null : (Number)((Number)object.get((Object)EncodeHintType.ERROR_CORRECTION));
        object = object == null ? var6_6 : (Number)object.get((Object)EncodeHintType.AZTEC_LAYERS);
        object2 = object2 == null ? DEFAULT_CHARSET : Charset.forName((String)object2);
        int n4 = number == null ? 33 : number.intValue();
        if (object == null) {
            n3 = 0;
            return AztecWriter.encode(string2, barcodeFormat, n, n2, (Charset)object2, n4, n3);
        }
        n3 = ((Number)object).intValue();
        return AztecWriter.encode(string2, barcodeFormat, n, n2, (Charset)object2, n4, n3);
    }
}

