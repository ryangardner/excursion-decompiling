/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.Map;

public final class Code39Writer
extends OneDimensionalCodeWriter {
    private static void toIntArray(int n, int[] arrn) {
        int n2 = 0;
        while (n2 < 9) {
            int n3 = 1;
            if ((1 << 8 - n2 & n) != 0) {
                n3 = 2;
            }
            arrn[n2] = n3;
            ++n2;
        }
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_39) {
            return super.encode((String)charSequence, barcodeFormat, n, n2, map);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Can only encode CODE_39, but got ");
        ((StringBuilder)charSequence).append((Object)barcodeFormat);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public boolean[] encode(String charSequence) {
        int n;
        int n2;
        int n3 = ((String)charSequence).length();
        if (n3 > 80) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Requested contents should be less than 80 digits long, but got ");
            ((StringBuilder)charSequence).append(n3);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        Object object = new int[9];
        int n4 = n3 + 25;
        for (n2 = 0; n2 < n3; ++n2) {
            n = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(((String)charSequence).charAt(n2));
            if (n < 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Bad contents: ");
                ((StringBuilder)object).append((String)charSequence);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[n], (int[])object);
            for (n = 0; n < 9; n4 += object[n], ++n) {
            }
        }
        boolean[] arrbl = new boolean[n4];
        Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], (int[])object);
        n2 = Code39Writer.appendPattern(arrbl, 0, (int[])object, true);
        int[] arrn = new int[]{1};
        n4 = n2 + Code39Writer.appendPattern(arrbl, n2, arrn, false);
        n2 = 0;
        do {
            if (n2 >= n3) {
                Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], (int[])object);
                Code39Writer.appendPattern(arrbl, n4, (int[])object, true);
                return arrbl;
            }
            n = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(((String)charSequence).charAt(n2));
            Code39Writer.toIntArray(Code39Reader.CHARACTER_ENCODINGS[n], (int[])object);
            n4 += Code39Writer.appendPattern(arrbl, n4, (int[])object, true);
            n4 += Code39Writer.appendPattern(arrbl, n4, arrn, false);
            ++n2;
        } while (true);
    }
}

