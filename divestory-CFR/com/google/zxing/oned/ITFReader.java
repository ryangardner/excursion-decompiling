/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Map;

public final class ITFReader
extends OneDReader {
    private static final int[] DEFAULT_ALLOWED_LENGTHS = new int[]{6, 8, 10, 12, 14};
    private static final int[] END_PATTERN_REVERSED;
    private static final float MAX_AVG_VARIANCE = 0.38f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.78f;
    private static final int N = 1;
    static final int[][] PATTERNS;
    private static final int[] START_PATTERN;
    private static final int W = 3;
    private int narrowLineWidth = -1;

    static {
        START_PATTERN = new int[]{1, 1, 1, 1};
        END_PATTERN_REVERSED = new int[]{1, 1, 3};
        int[] arrn = new int[]{1, 1, 3, 3, 1};
        int[] arrn2 = new int[]{3, 3, 1, 1, 1};
        int[] arrn3 = new int[]{1, 1, 3, 1, 3};
        int[] arrn4 = new int[]{3, 1, 3, 1, 1};
        int[] arrn5 = new int[]{1, 3, 3, 1, 1};
        int[] arrn6 = new int[]{3, 1, 1, 3, 1};
        PATTERNS = new int[][]{arrn, {3, 1, 1, 1, 3}, {1, 3, 1, 1, 3}, arrn2, arrn3, arrn4, arrn5, {1, 1, 1, 3, 3}, arrn6, {1, 3, 1, 3, 1}};
    }

    private static int decodeDigit(int[] arrn) throws NotFoundException {
        int n = PATTERNS.length;
        float f = 0.38f;
        int n2 = -1;
        int n3 = 0;
        do {
            if (n3 >= n) {
                if (n2 < 0) throw NotFoundException.getNotFoundInstance();
                return n2;
            }
            float f2 = ITFReader.patternMatchVariance(arrn, PATTERNS[n3], 0.78f);
            float f3 = f;
            if (f2 < f) {
                n2 = n3;
                f3 = f2;
            }
            ++n3;
            f = f3;
        } while (true);
    }

    private static void decodeMiddle(BitArray bitArray, int n, int n2, StringBuilder stringBuilder) throws NotFoundException {
        int[] arrn = new int[10];
        int[] arrn2 = new int[5];
        int[] arrn3 = new int[5];
        block0 : while (n < n2) {
            int n3;
            int n4;
            ITFReader.recordPattern(bitArray, n, arrn);
            int n5 = 0;
            for (n4 = 0; n4 < 5; ++n4) {
                n3 = n4 * 2;
                arrn2[n4] = arrn[n3];
                arrn3[n4] = arrn[n3 + 1];
            }
            stringBuilder.append((char)(ITFReader.decodeDigit(arrn2) + 48));
            stringBuilder.append((char)(ITFReader.decodeDigit(arrn3) + 48));
            n3 = n;
            n4 = n5;
            do {
                n = n3;
                if (n4 >= 10) continue block0;
                n3 += arrn[n4];
                ++n4;
            } while (true);
            break;
        }
        return;
    }

    private static int[] findGuardPattern(BitArray bitArray, int n, int[] arrn) throws NotFoundException {
        int n2 = arrn.length;
        int[] arrn2 = new int[n2];
        int n3 = bitArray.getSize();
        int n4 = n;
        boolean bl = false;
        int n5 = 0;
        int n6 = n;
        n = n4;
        while (n6 < n3) {
            if (bitArray.get(n6) ^ bl) {
                arrn2[n5] = arrn2[n5] + 1;
            } else {
                n4 = n2 - 1;
                if (n5 == n4) {
                    if (ITFReader.patternMatchVariance(arrn2, arrn, 0.78f) < 0.38f) {
                        return new int[]{n, n6};
                    }
                    n += arrn2[0] + arrn2[1];
                    int n7 = n2 - 2;
                    System.arraycopy(arrn2, 2, arrn2, 0, n7);
                    arrn2[n7] = 0;
                    arrn2[n4] = 0;
                    --n5;
                } else {
                    ++n5;
                }
                arrn2[n5] = 1;
                bl ^= true;
            }
            ++n6;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int skipWhiteSpace(BitArray bitArray) throws NotFoundException {
        int n = bitArray.getSize();
        int n2 = bitArray.getNextSet(0);
        if (n2 == n) throw NotFoundException.getNotFoundInstance();
        return n2;
    }

    private void validateQuietZone(BitArray bitArray, int n) throws NotFoundException {
        int n2 = this.narrowLineWidth * 10;
        if (n2 >= n) {
            n2 = n;
        }
        --n;
        while (n2 > 0 && n >= 0 && !bitArray.get(n)) {
            --n2;
            --n;
        }
        if (n2 != 0) throw NotFoundException.getNotFoundInstance();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    int[] decodeEnd(BitArray bitArray) throws NotFoundException {
        bitArray.reverse();
        try {
            int[] arrn = ITFReader.findGuardPattern(bitArray, ITFReader.skipWhiteSpace(bitArray), END_PATTERN_REVERSED);
            this.validateQuietZone(bitArray, arrn[0]);
            int n = arrn[0];
            arrn[0] = bitArray.getSize() - arrn[1];
            arrn[1] = bitArray.getSize() - n;
            return arrn;
        }
        finally {
            bitArray.reverse();
        }
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws FormatException, NotFoundException {
        int n2;
        int n3;
        int n4;
        int n5;
        int[] arrn;
        CharSequence charSequence;
        Object object3;
        block6 : {
            arrn = this.decodeStart((BitArray)object);
            object3 = this.decodeEnd((BitArray)object);
            charSequence = new StringBuilder(20);
            ITFReader.decodeMiddle((BitArray)object, arrn[1], object3[0], (StringBuilder)charSequence);
            charSequence = ((StringBuilder)charSequence).toString();
            object = object2 != null ? (int[])object2.get((Object)DecodeHintType.ALLOWED_LENGTHS) : null;
            object2 = object;
            if (object == null) {
                object2 = DEFAULT_ALLOWED_LENGTHS;
            }
            n4 = ((String)charSequence).length();
            int n6 = ((int[])object2).length;
            n3 = 0;
            for (n5 = 0; n5 < n6; ++n5) {
                int n7 = object2[n5];
                if (n4 == n7) {
                    n2 = 1;
                    break block6;
                }
                n2 = n3;
                if (n7 > n3) {
                    n2 = n7;
                }
                n3 = n2;
            }
            n2 = 0;
        }
        n5 = n2;
        if (n2 == 0) {
            n5 = n2;
            if (n4 > n3) {
                n5 = 1;
            }
        }
        if (n5 == 0) throw FormatException.getFormatInstance();
        float f = arrn[1];
        float f2 = n;
        object = new ResultPoint(f, f2);
        object3 = new ResultPoint(object3[0], f2);
        object2 = BarcodeFormat.ITF;
        return new Result((String)charSequence, null, new ResultPoint[]{object, object3}, (BarcodeFormat)((Object)object2));
    }

    int[] decodeStart(BitArray bitArray) throws NotFoundException {
        int[] arrn = ITFReader.findGuardPattern(bitArray, ITFReader.skipWhiteSpace(bitArray), START_PATTERN);
        this.narrowLineWidth = (arrn[1] - arrn[0]) / 4;
        this.validateQuietZone(bitArray, arrn[0]);
        return arrn;
    }
}

