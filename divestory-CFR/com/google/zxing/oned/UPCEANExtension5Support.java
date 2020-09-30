/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.UPCEANReader;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtension5Support {
    private static final int[] CHECK_DIGIT_ENCODINGS = new int[]{24, 20, 18, 17, 12, 6, 3, 10, 9, 5};
    private final int[] decodeMiddleCounters = new int[4];
    private final StringBuilder decodeRowStringBuffer = new StringBuilder();

    UPCEANExtension5Support() {
    }

    private static int determineCheckDigit(int n) throws NotFoundException {
        int n2 = 0;
        while (n2 < 10) {
            if (n == CHECK_DIGIT_ENCODINGS[n2]) {
                return n2;
            }
            ++n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int extensionChecksum(CharSequence charSequence) {
        int n;
        int n2 = charSequence.length();
        int n3 = 0;
        for (n = n2 - 2; n >= 0; n3 += charSequence.charAt((int)n) - 48, n -= 2) {
        }
        n3 *= 3;
        n = n2 - 1;
        while (n >= 0) {
            n3 += charSequence.charAt(n) - 48;
            n -= 2;
        }
        return n3 * 3 % 10;
    }

    private static String parseExtension5String(String charSequence) {
        int n = ((String)charSequence).charAt(0);
        String string2 = "";
        if (n != 48) {
            if (n != 53) {
                if (n == 57) {
                    if ("90000".equals(charSequence)) {
                        return null;
                    }
                    if ("99991".equals(charSequence)) {
                        return "0.00";
                    }
                    if ("99990".equals(charSequence)) {
                        return "Used";
                    }
                }
            } else {
                string2 = "$";
            }
        } else {
            string2 = "\u00a3";
        }
        int n2 = Integer.parseInt(((String)charSequence).substring(1));
        n = n2 / 100;
        if ((n2 %= 100) < 10) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("0");
            ((StringBuilder)charSequence).append(n2);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = String.valueOf(n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(String.valueOf(n));
        stringBuilder.append('.');
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
    }

    private static Map<ResultMetadataType, Object> parseExtensionString(String string2) {
        if (string2.length() != 5) {
            return null;
        }
        if ((string2 = UPCEANExtension5Support.parseExtension5String(string2)) == null) {
            return null;
        }
        EnumMap<ResultMetadataType, Object> enumMap = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        enumMap.put(ResultMetadataType.SUGGESTED_PRICE, string2);
        return enumMap;
    }

    int decodeMiddle(BitArray bitArray, int[] arrn, StringBuilder stringBuilder) throws NotFoundException {
        int n;
        int[] arrn2 = this.decodeMiddleCounters;
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n2 = bitArray.getSize();
        int n3 = arrn[1];
        int n4 = 0;
        for (int i = 0; i < 5 && n3 < n2; ++i) {
            int n5 = UPCEANReader.decodeDigit(bitArray, arrn2, n3, UPCEANReader.L_AND_G_PATTERNS);
            stringBuilder.append((char)(n5 % 10 + 48));
            int n6 = arrn2.length;
            for (n = 0; n < n6; n3 += arrn2[n], ++n) {
            }
            n6 = n4;
            if (n5 >= 10) {
                n6 = n4 | 1 << 4 - i;
            }
            n = n3;
            if (i != 4) {
                n = bitArray.getNextUnset(bitArray.getNextSet(n3));
            }
            n4 = n6;
            n3 = n;
        }
        if (stringBuilder.length() != 5) throw NotFoundException.getNotFoundInstance();
        n = UPCEANExtension5Support.determineCheckDigit(n4);
        if (UPCEANExtension5Support.extensionChecksum(stringBuilder.toString()) != n) throw NotFoundException.getNotFoundInstance();
        return n3;
    }

    Result decodeRow(int n, BitArray object, int[] object2) throws NotFoundException {
        CharSequence charSequence = this.decodeRowStringBuffer;
        charSequence.setLength(0);
        int n2 = this.decodeMiddle((BitArray)object, (int[])object2, (StringBuilder)charSequence);
        charSequence = charSequence.toString();
        object = UPCEANExtension5Support.parseExtensionString((String)charSequence);
        float f = (float)(object2[0] + object2[1]) / 2.0f;
        float f2 = n;
        ResultPoint resultPoint = new ResultPoint(f, f2);
        object2 = new ResultPoint(n2, f2);
        BarcodeFormat barcodeFormat = BarcodeFormat.UPC_EAN_EXTENSION;
        object2 = new Result((String)charSequence, null, new ResultPoint[]{resultPoint, object2}, barcodeFormat);
        if (object == null) return object2;
        ((Result)object2).putAllMetadata((Map<ResultMetadataType, Object>)object);
        return object2;
    }
}

