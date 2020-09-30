/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.UPCEANReader;
import com.google.zxing.oned.UPCEANWriter;
import java.util.Map;

public final class EAN8Writer
extends UPCEANWriter {
    private static final int CODE_WIDTH = 67;

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_8) {
            return super.encode((String)charSequence, barcodeFormat, n, n2, map);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Can only encode EAN_8, but got ");
        ((StringBuilder)charSequence).append((Object)barcodeFormat);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public boolean[] encode(String string2) {
        int n;
        if (string2.length() != 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested contents should be 8 digits long, but got ");
            stringBuilder.append(string2.length());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        boolean[] arrbl = new boolean[67];
        int n2 = EAN8Writer.appendPattern(arrbl, 0, UPCEANReader.START_END_PATTERN, true) + 0;
        int n3 = 0;
        while (n3 <= 3) {
            n = n3 + 1;
            n3 = Integer.parseInt(string2.substring(n3, n));
            n2 += EAN8Writer.appendPattern(arrbl, n2, UPCEANReader.L_PATTERNS[n3], false);
            n3 = n;
        }
        n2 += EAN8Writer.appendPattern(arrbl, n2, UPCEANReader.MIDDLE_PATTERN, false);
        n3 = 4;
        do {
            if (n3 > 7) {
                EAN8Writer.appendPattern(arrbl, n2, UPCEANReader.START_END_PATTERN, true);
                return arrbl;
            }
            n = n3 + 1;
            n3 = Integer.parseInt(string2.substring(n3, n));
            n2 += EAN8Writer.appendPattern(arrbl, n2, UPCEANReader.L_PATTERNS[n3], true);
            n3 = n;
        } while (true);
    }
}

