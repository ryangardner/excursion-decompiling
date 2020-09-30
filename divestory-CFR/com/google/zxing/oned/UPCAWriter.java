/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import java.util.Map;

public final class UPCAWriter
implements Writer {
    private final EAN13Writer subWriter = new EAN13Writer();

    private static String preencode(String string2) {
        StringBuilder stringBuilder;
        int n = string2.length();
        if (n != 11) {
            if (n != 12) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Requested contents should be 11 or 12 digits long, but got ");
                stringBuilder2.append(string2.length());
                throw new IllegalArgumentException(stringBuilder2.toString());
            }
        } else {
            int n2;
            char c;
            n = 0;
            for (int i = 0; i < 11; n += (c - 48) * n2, ++i) {
                c = string2.charAt(i);
                n2 = i % 2 == 0 ? 3 : 1;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append((1000 - n) % 10);
            string2 = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append('0');
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string2, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.UPC_A) {
            return this.subWriter.encode(UPCAWriter.preencode((String)charSequence), BarcodeFormat.EAN_13, n, n2, map);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Can only encode UPC-A, but got ");
        ((StringBuilder)charSequence).append((Object)barcodeFormat);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }
}

