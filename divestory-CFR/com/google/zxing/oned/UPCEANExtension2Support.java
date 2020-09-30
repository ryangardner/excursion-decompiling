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

final class UPCEANExtension2Support {
    private final int[] decodeMiddleCounters = new int[4];
    private final StringBuilder decodeRowStringBuffer = new StringBuilder();

    UPCEANExtension2Support() {
    }

    private static Map<ResultMetadataType, Object> parseExtensionString(String string2) {
        if (string2.length() != 2) {
            return null;
        }
        EnumMap<ResultMetadataType, Object> enumMap = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        enumMap.put(ResultMetadataType.ISSUE_NUMBER, Integer.valueOf(string2));
        return enumMap;
    }

    int decodeMiddle(BitArray bitArray, int[] arrn, StringBuilder stringBuilder) throws NotFoundException {
        int[] arrn2 = this.decodeMiddleCounters;
        arrn2[0] = 0;
        arrn2[1] = 0;
        arrn2[2] = 0;
        arrn2[3] = 0;
        int n = bitArray.getSize();
        int n2 = arrn[1];
        int n3 = 0;
        for (int i = 0; i < 2 && n2 < n; ++i) {
            int n4;
            int n5 = UPCEANReader.decodeDigit(bitArray, arrn2, n2, UPCEANReader.L_AND_G_PATTERNS);
            stringBuilder.append((char)(n5 % 10 + 48));
            int n6 = arrn2.length;
            for (n4 = 0; n4 < n6; n2 += arrn2[n4], ++n4) {
            }
            n6 = n3;
            if (n5 >= 10) {
                n6 = n3 | 1 << 1 - i;
            }
            n4 = n2;
            if (i != 1) {
                n4 = bitArray.getNextUnset(bitArray.getNextSet(n2));
            }
            n3 = n6;
            n2 = n4;
        }
        if (stringBuilder.length() != 2) throw NotFoundException.getNotFoundInstance();
        if (Integer.parseInt(stringBuilder.toString()) % 4 != n3) throw NotFoundException.getNotFoundInstance();
        return n2;
    }

    Result decodeRow(int n, BitArray object, int[] object2) throws NotFoundException {
        CharSequence charSequence = this.decodeRowStringBuffer;
        charSequence.setLength(0);
        int n2 = this.decodeMiddle((BitArray)object, (int[])object2, (StringBuilder)charSequence);
        charSequence = charSequence.toString();
        object = UPCEANExtension2Support.parseExtensionString((String)charSequence);
        float f = (float)(object2[0] + object2[1]) / 2.0f;
        float f2 = n;
        ResultPoint resultPoint = new ResultPoint(f, f2);
        ResultPoint resultPoint2 = new ResultPoint(n2, f2);
        object2 = BarcodeFormat.UPC_EAN_EXTENSION;
        object2 = new Result((String)charSequence, null, new ResultPoint[]{resultPoint, resultPoint2}, (BarcodeFormat)((Object)object2));
        if (object == null) return object2;
        ((Result)object2).putAllMetadata((Map<ResultMetadataType, Object>)object);
        return object2;
    }
}

