/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.ITFReader;
import com.google.zxing.oned.OneDimensionalCodeWriter;
import java.util.Map;

public final class ITFWriter
extends OneDimensionalCodeWriter {
    private static final int[] END_PATTERN;
    private static final int[] START_PATTERN;

    static {
        START_PATTERN = new int[]{1, 1, 1, 1};
        END_PATTERN = new int[]{3, 1, 1};
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat barcodeFormat, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.ITF) {
            return super.encode((String)charSequence, barcodeFormat, n, n2, map);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Can only encode ITF, but got ");
        ((StringBuilder)charSequence).append((Object)barcodeFormat);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public boolean[] encode(String charSequence) {
        int n = ((String)charSequence).length();
        if (n % 2 != 0) throw new IllegalArgumentException("The lenght of the input should be even");
        if (n > 80) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Requested contents should be less than 80 digits long, but got ");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        boolean[] arrbl = new boolean[n * 9 + 9];
        int n2 = ITFWriter.appendPattern(arrbl, 0, START_PATTERN, true);
        int n3 = 0;
        do {
            if (n3 >= n) {
                ITFWriter.appendPattern(arrbl, n2, END_PATTERN, true);
                return arrbl;
            }
            int n4 = Character.digit(((String)charSequence).charAt(n3), 10);
            int n5 = Character.digit(((String)charSequence).charAt(n3 + 1), 10);
            int[] arrn = new int[18];
            for (int i = 0; i < 5; ++i) {
                int n6 = i * 2;
                arrn[n6] = ITFReader.PATTERNS[n4][i];
                arrn[n6 + 1] = ITFReader.PATTERNS[n5][i];
            }
            n2 += ITFWriter.appendPattern(arrbl, n2, arrn, true);
            n3 += 2;
        } while (true);
    }
}

