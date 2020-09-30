/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Arrays;
import java.util.Map;

public final class CodaBarReader
extends OneDReader {
    static final char[] ALPHABET = "0123456789-$:/.+ABCD".toCharArray();
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
    static final int[] CHARACTER_ENCODINGS = new int[]{3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final float MAX_ACCEPTABLE = 2.0f;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final float PADDING = 1.5f;
    private static final char[] STARTEND_ENCODING = new char[]{'A', 'B', 'C', 'D'};
    private int counterLength = 0;
    private int[] counters = new int[80];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static boolean arrayContains(char[] arrc, char c) {
        if (arrc == null) return false;
        int n = arrc.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrc[n2] == c) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void counterAppend(int n) {
        int[] arrn = this.counters;
        int n2 = this.counterLength;
        arrn[n2] = n;
        this.counterLength = n = n2 + 1;
        if (n < arrn.length) return;
        int[] arrn2 = new int[n * 2];
        System.arraycopy(arrn, 0, arrn2, 0, n);
        this.counters = arrn2;
    }

    private int findStartPattern() throws NotFoundException {
        int n = 1;
        while (n < this.counterLength) {
            int n2 = this.toNarrowWidePattern(n);
            if (n2 != -1 && CodaBarReader.arrayContains(STARTEND_ENCODING, ALPHABET[n2])) {
                int n3 = 0;
                for (n2 = n; n2 < n + 7; n3 += this.counters[n2], ++n2) {
                }
                if (n == 1) return n;
                if (this.counters[n - 1] >= n3 / 2) {
                    return n;
                }
            }
            n += 2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void setCounters(BitArray bitArray) throws NotFoundException {
        int n;
        int n2 = 0;
        this.counterLength = 0;
        int n3 = bitArray.getNextUnset(0);
        if (n3 >= (n = bitArray.getSize())) throw NotFoundException.getNotFoundInstance();
        boolean bl = true;
        do {
            if (n3 >= n) {
                this.counterAppend(n2);
                return;
            }
            if (bitArray.get(n3) ^ bl) {
                ++n2;
            } else {
                this.counterAppend(n2);
                bl ^= true;
                n2 = 1;
            }
            ++n3;
        } while (true);
    }

    private int toNarrowWidePattern(int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6 = n + 7;
        if (n6 >= this.counterLength) {
            return -1;
        }
        int[] arrn = this.counters;
        int n7 = Integer.MAX_VALUE;
        int n8 = 0;
        int n9 = Integer.MAX_VALUE;
        int n10 = 0;
        for (n5 = n; n5 < n6; n5 += 2) {
            n3 = arrn[n5];
            n2 = n9;
            if (n3 < n9) {
                n2 = n3;
            }
            n4 = n10;
            if (n3 > n10) {
                n4 = n3;
            }
            n9 = n2;
            n10 = n4;
        }
        int n11 = (n9 + n10) / 2;
        n10 = 0;
        n3 = n7;
        for (n2 = n + 1; n2 < n6; n2 += 2) {
            n9 = arrn[n2];
            n5 = n3;
            if (n9 < n3) {
                n5 = n9;
            }
            n4 = n10;
            if (n9 > n10) {
                n4 = n9;
            }
            n3 = n5;
            n10 = n4;
        }
        n9 = (n3 + n10) / 2;
        n5 = 128;
        n2 = 0;
        n10 = 0;
        do {
            n3 = n8;
            if (n2 >= 7) break;
            n4 = (n2 & 1) == 0 ? n11 : n9;
            n5 >>= 1;
            n3 = n10;
            if (arrn[n + n2] > n4) {
                n3 = n10 | n5;
            }
            ++n2;
            n10 = n3;
        } while (true);
        while (n3 < (arrn = CHARACTER_ENCODINGS).length) {
            if (arrn[n3] == n10) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException {
        int n2;
        int n3;
        Arrays.fill(this.counters, 0);
        this.setCounters((BitArray)object);
        int n4 = this.findStartPattern();
        this.decodeRowResult.setLength(0);
        int n5 = n4;
        do {
            if ((n3 = this.toNarrowWidePattern(n5)) == -1) throw NotFoundException.getNotFoundInstance();
            this.decodeRowResult.append((char)n3);
            n2 = n5 + 8;
            if (this.decodeRowResult.length() > 1 && CodaBarReader.arrayContains(STARTEND_ENCODING, ALPHABET[n3])) break;
            n5 = n2;
        } while (n2 < this.counterLength);
        object = this.counters;
        int n6 = n2 - 1;
        int n7 = object[n6];
        n3 = 0;
        for (n5 = -8; n5 < -1; n3 += this.counters[n2 + n5], ++n5) {
        }
        if (n2 < this.counterLength) {
            if (n7 < n3 / 2) throw NotFoundException.getNotFoundInstance();
        }
        this.validatePattern(n4);
        for (n5 = 0; n5 < this.decodeRowResult.length(); ++n5) {
            object = this.decodeRowResult;
            ((StringBuilder)object).setCharAt(n5, ALPHABET[((StringBuilder)object).charAt(n5)]);
        }
        char c = this.decodeRowResult.charAt(0);
        if (!CodaBarReader.arrayContains(STARTEND_ENCODING, c)) throw NotFoundException.getNotFoundInstance();
        object = this.decodeRowResult;
        c = ((StringBuilder)object).charAt(((StringBuilder)object).length() - 1);
        if (!CodaBarReader.arrayContains(STARTEND_ENCODING, c)) throw NotFoundException.getNotFoundInstance();
        if (this.decodeRowResult.length() <= 3) throw NotFoundException.getNotFoundInstance();
        if (object2 == null || !object2.containsKey((Object)DecodeHintType.RETURN_CODABAR_START_END)) {
            object = this.decodeRowResult;
            ((StringBuilder)object).deleteCharAt(((StringBuilder)object).length() - 1);
            this.decodeRowResult.deleteCharAt(0);
        }
        n5 = 0;
        for (n3 = 0; n3 < n4; n5 += this.counters[n3], ++n3) {
        }
        float f = n5;
        do {
            if (n4 >= n6) {
                float f2 = n5;
                String string2 = this.decodeRowResult.toString();
                float f3 = n;
                object = new ResultPoint(f, f3);
                ResultPoint resultPoint = new ResultPoint(f2, f3);
                object2 = BarcodeFormat.CODABAR;
                return new Result(string2, null, new ResultPoint[]{object, resultPoint}, (BarcodeFormat)((Object)object2));
            }
            n5 += this.counters[n4];
            ++n4;
        } while (true);
    }

    void validatePattern(int n) throws NotFoundException {
        int n2;
        int[] arrn;
        int n3;
        float[] arrf;
        float[] arrf2;
        int[] arrn2;
        int[] arrn3 = arrn = new int[4];
        arrn3[0] = 0;
        arrn3[1] = 0;
        arrn3[2] = 0;
        arrn3[3] = 0;
        int[] arrn4 = arrn2 = new int[4];
        arrn4[0] = 0;
        arrn4[1] = 0;
        arrn4[2] = 0;
        arrn4[3] = 0;
        int n4 = this.decodeRowResult.length() - 1;
        int n5 = 0;
        int n6 = n;
        int n7 = 0;
        block0 : do {
            n3 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(n7)];
            for (n2 = 6; n2 >= 0; n3 >>= 1, --n2) {
                int n8 = (n2 & 1) + (n3 & 1) * 2;
                arrn[n8] = arrn[n8] + this.counters[n6 + n2];
                arrn2[n8] = arrn2[n8] + 1;
            }
            if (n7 >= n4) {
                arrf = new float[4];
                arrf2 = new float[4];
                n2 = 0;
                do {
                    n7 = n5;
                    n6 = n;
                    if (n2 >= 2) break block0;
                    arrf2[n2] = 0.0f;
                    n7 = n2 + 2;
                    arrf2[n7] = ((float)arrn[n2] / (float)arrn2[n2] + (float)arrn[n7] / (float)arrn2[n7]) / 2.0f;
                    arrf[n2] = arrf2[n7];
                    arrf[n7] = ((float)arrn[n7] * 2.0f + 1.5f) / (float)arrn2[n7];
                    ++n2;
                } while (true);
            }
            n6 += 8;
            ++n7;
        } while (true);
        do {
            n2 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(n7)];
            for (n = 6; n >= 0; n2 >>= 1, --n) {
                float f = this.counters[n6 + n];
                n3 = (n & 1) + (n2 & 1) * 2;
                if (f < arrf2[n3]) throw NotFoundException.getNotFoundInstance();
                if (f > arrf[n3]) throw NotFoundException.getNotFoundInstance();
            }
            if (n7 >= n4) {
                return;
            }
            n6 += 8;
            ++n7;
        } while (true);
    }
}

