/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.SymbolInfo;

public final class ErrorCorrection {
    private static final int[] ALOG;
    private static final int[][] FACTORS;
    private static final int[] FACTOR_SETS;
    private static final int[] LOG;
    private static final int MODULO_VALUE = 301;

    static {
        FACTOR_SETS = new int[]{5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
        int n = 0;
        int n2 = 1;
        int[] arrn = new int[]{23, 68, 144, 134, 240, 92, 254};
        int[] arrn2 = new int[]{83, 195, 100, 39, 188, 75, 66, 61, 241, 213, 109, 129, 94, 254, 225, 48, 90, 188};
        int[] arrn3 = new int[]{15, 195, 244, 9, 233, 71, 168, 2, 188, 160, 153, 145, 253, 79, 108, 82, 27, 174, 186, 172};
        int[] arrn4 = new int[]{245, 132, 172, 223, 96, 32, 117, 22, 238, 133, 238, 231, 205, 188, 237, 87, 191, 106, 16, 147, 118, 23, 37, 90, 170, 205, 131, 88, 120, 100, 66, 138, 186, 240, 82, 44, 176, 87, 187, 147, 160, 175, 69, 213, 92, 253, 225, 19};
        FACTORS = new int[][]{{228, 48, 15, 111, 62}, arrn, {28, 24, 185, 166, 223, 248, 116, 255, 110, 61}, {175, 138, 205, 12, 194, 168, 39, 245, 60, 97, 120}, {41, 153, 158, 91, 61, 42, 142, 213, 97, 178, 100, 242}, {156, 97, 192, 252, 95, 9, 157, 119, 138, 45, 18, 186, 83, 185}, arrn2, arrn3, {52, 190, 88, 205, 109, 39, 176, 21, 155, 197, 251, 223, 155, 21, 5, 172, 254, 124, 12, 181, 184, 96, 50, 193}, {211, 231, 43, 97, 71, 96, 103, 174, 37, 151, 170, 53, 75, 34, 249, 121, 17, 138, 110, 213, 141, 136, 120, 151, 233, 168, 93, 255}, {245, 127, 242, 218, 130, 250, 162, 181, 102, 120, 84, 179, 220, 251, 80, 182, 229, 18, 2, 4, 68, 33, 101, 137, 95, 119, 115, 44, 175, 184, 59, 25, 225, 98, 81, 112}, {77, 193, 137, 31, 19, 38, 22, 153, 247, 105, 122, 2, 245, 133, 242, 8, 175, 95, 100, 9, 167, 105, 214, 111, 57, 121, 21, 1, 253, 57, 54, 101, 248, 202, 69, 50, 150, 177, 226, 5, 9, 5}, arrn4, {175, 9, 223, 238, 12, 17, 220, 208, 100, 29, 175, 170, 230, 192, 215, 235, 150, 159, 36, 223, 38, 200, 132, 54, 228, 146, 218, 234, 117, 203, 29, 232, 144, 238, 22, 150, 201, 117, 62, 207, 164, 13, 137, 245, 127, 67, 247, 28, 155, 43, 203, 107, 233, 53, 143, 46}, {242, 93, 169, 50, 144, 210, 39, 118, 202, 188, 201, 189, 143, 108, 196, 37, 185, 112, 134, 230, 245, 63, 197, 190, 250, 106, 185, 221, 175, 64, 114, 71, 161, 44, 147, 6, 27, 218, 51, 63, 87, 10, 40, 130, 188, 17, 163, 31, 176, 170, 4, 107, 232, 7, 94, 166, 224, 124, 86, 47, 11, 204}, {220, 228, 173, 89, 251, 149, 159, 56, 89, 33, 147, 244, 154, 36, 73, 127, 213, 136, 248, 180, 234, 197, 158, 177, 68, 122, 93, 213, 15, 160, 227, 236, 66, 139, 153, 185, 202, 167, 179, 25, 220, 232, 96, 210, 231, 136, 223, 239, 181, 241, 59, 52, 172, 25, 49, 232, 211, 189, 64, 54, 108, 153, 132, 63, 96, 103, 82, 186}};
        LOG = new int[256];
        ALOG = new int[255];
        while (n < 255) {
            int n3;
            ErrorCorrection.ALOG[n] = n2;
            ErrorCorrection.LOG[n2] = n;
            n2 = n3 = n2 * 2;
            if (n3 >= 256) {
                n2 = n3 ^ 301;
            }
            ++n;
        }
    }

    private ErrorCorrection() {
    }

    private static String createECCBlock(CharSequence charSequence, int n) {
        return ErrorCorrection.createECCBlock(charSequence, 0, charSequence.length(), n);
    }

    private static String createECCBlock(CharSequence arrc, int n, int n2, int n3) {
        int n4;
        int[] arrn;
        int n5;
        block9 : {
            n4 = 0;
            for (n5 = 0; n5 < (arrn = FACTOR_SETS).length; ++n5) {
                if (arrn[n5] != n3) {
                    continue;
                }
                break block9;
            }
            n5 = -1;
        }
        if (n5 < 0) {
            arrc = new StringBuilder();
            arrc.append("Illegal number of error correction codewords specified: ");
            arrc.append(n3);
            throw new IllegalArgumentException(arrc.toString());
        }
        int[] arrn2 = FACTORS[n5];
        arrn = new char[n3];
        for (n5 = 0; n5 < n3; ++n5) {
            arrn[n5] = (char)(false ? 1 : 0);
        }
        for (n5 = n; n5 < n + n2; ++n5) {
            int[] arrn3;
            int[] arrn4;
            int n6;
            int n7 = arrn[n6] ^ arrc.charAt(n5);
            for (n6 = n3 - 1; n6 > 0; --n6) {
                if (n7 != 0 && arrn2[n6] != 0) {
                    int n8 = arrn[n6 - 1];
                    arrn4 = ALOG;
                    arrn3 = LOG;
                    arrn[n6] = (char)(n8 ^ arrn4[(arrn3[n7] + arrn3[arrn2[n6]]) % 255]);
                    continue;
                }
                arrn[n6] = (char)arrn[n6 - 1];
            }
            if (n7 != 0 && arrn2[0] != 0) {
                arrn3 = ALOG;
                arrn4 = LOG;
                arrn[0] = (char)arrn3[(arrn4[n7] + arrn4[arrn2[0]]) % 255];
                continue;
            }
            arrn[0] = (char)(false ? 1 : 0);
        }
        arrc = new char[n3];
        n = n4;
        while (n < n3) {
            arrc[n] = (char)arrn[n3 - n - 1];
            ++n;
        }
        return String.valueOf(arrc);
    }

    public static String encodeECC200(String string2, SymbolInfo symbolInfo) {
        int n;
        if (string2.length() != symbolInfo.getDataCapacity()) throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        StringBuilder stringBuilder = new StringBuilder(symbolInfo.getDataCapacity() + symbolInfo.getErrorCodewords());
        stringBuilder.append(string2);
        int n2 = symbolInfo.getInterleavedBlockCount();
        if (n2 == 1) {
            stringBuilder.append(ErrorCorrection.createECCBlock(string2, symbolInfo.getErrorCodewords()));
            return stringBuilder.toString();
        }
        stringBuilder.setLength(stringBuilder.capacity());
        int[] arrn = new int[n2];
        int[] arrn2 = new int[n2];
        Object object = new int[n2];
        int n3 = 0;
        while (n3 < n2) {
            n = n3 + 1;
            arrn[n3] = symbolInfo.getDataLengthForInterleavedBlock(n);
            arrn2[n3] = symbolInfo.getErrorLengthForInterleavedBlock(n);
            object[n3] = 0;
            if (n3 > 0) {
                object[n3] = object[n3 - 1] + arrn[n3];
            }
            n3 = n;
        }
        n3 = 0;
        while (n3 < n2) {
            object = new StringBuilder(arrn[n3]);
            for (n = n3; n < symbolInfo.getDataCapacity(); n += n2) {
                ((StringBuilder)object).append(string2.charAt(n));
            }
            object = ErrorCorrection.createECCBlock(((StringBuilder)object).toString(), arrn2[n3]);
            n = 0;
            for (int i = n3; i < arrn2[n3] * n2; i += n2, ++n) {
                stringBuilder.setCharAt(symbolInfo.getDataCapacity() + i, ((String)object).charAt(n));
            }
            ++n3;
        }
        return stringBuilder.toString();
    }
}

