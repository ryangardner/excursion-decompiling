/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;

public final class UPCEReader
extends UPCEANReader {
    private static final int[] MIDDLE_END_PATTERN = new int[]{1, 1, 1, 1, 1, 1};
    private static final int[][] NUMSYS_AND_CHECK_DIGIT_PATTERNS = new int[][]{{56, 52, 50, 49, 44, 38, 35, 42, 41, 37}, {7, 11, 13, 14, 19, 25, 28, 21, 22, 26}};
    private final int[] decodeMiddleCounters = new int[4];

    /*
     * Unable to fully structure code
     */
    public static String convertUPCEtoUPCA(String var0) {
        var1_1 = new char[6];
        var0.getChars(1, 7, var1_1, 0);
        var2_2 = new StringBuilder(12);
        var2_2.append(var0.charAt(0));
        var3_3 = var1_1[5];
        switch (var3_3) {
            default: {
                var2_2.append(var1_1, 0, 5);
                var2_2.append("0000");
                var2_2.append(var3_3);
                ** break;
            }
            case '4': {
                var2_2.append(var1_1, 0, 4);
                var2_2.append("00000");
                var2_2.append(var1_1[4]);
                ** break;
            }
            case '3': {
                var2_2.append(var1_1, 0, 3);
                var2_2.append("00000");
                var2_2.append(var1_1, 3, 2);
                ** break;
            }
            case '0': 
            case '1': 
            case '2': 
        }
        var2_2.append(var1_1, 0, 2);
        var2_2.append(var3_3);
        var2_2.append("0000");
        var2_2.append(var1_1, 2, 3);
lbl41: // 4 sources:
        var2_2.append(var0.charAt(7));
        return var2_2.toString();
    }

    private static void determineNumSysAndCheckDigit(StringBuilder stringBuilder, int n) throws NotFoundException {
        int n2 = 0;
        while (n2 <= 1) {
            for (int i = 0; i < 10; ++i) {
                if (n != NUMSYS_AND_CHECK_DIGIT_PATTERNS[n2][i]) continue;
                stringBuilder.insert(0, (char)(n2 + 48));
                stringBuilder.append((char)(i + 48));
                return;
            }
            ++n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    protected boolean checkChecksum(String string2) throws FormatException {
        return super.checkChecksum(UPCEReader.convertUPCEtoUPCA(string2));
    }

    @Override
    protected int[] decodeEnd(BitArray bitArray, int n) throws NotFoundException {
        return UPCEReader.findGuardPattern(bitArray, n, true, MIDDLE_END_PATTERN);
    }

    @Override
    protected int decodeMiddle(BitArray bitArray, int[] arrn, StringBuilder stringBuilder) throws NotFoundException {
        int[] arrn2 = this.decodeMiddleCounters;
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n = bitArray.getSize();
        int n2 = arrn[1];
        int n3 = 0;
        for (int i = 0; i < 6 && n2 < n; ++i) {
            int n4;
            int n5 = UPCEReader.decodeDigit(bitArray, arrn2, n2, L_AND_G_PATTERNS);
            stringBuilder.append((char)(n5 % 10 + 48));
            int n6 = arrn2.length;
            for (n4 = 0; n4 < n6; n2 += arrn2[n4], ++n4) {
            }
            n4 = n3;
            if (n5 >= 10) {
                n4 = n3 | 1 << 5 - i;
            }
            n3 = n4;
        }
        UPCEReader.determineNumSysAndCheckDigit(stringBuilder, n3);
        return n2;
    }

    @Override
    BarcodeFormat getBarcodeFormat() {
        return BarcodeFormat.UPC_E;
    }
}

