/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Arrays;
import java.util.Map;

public final class Code39Reader
extends OneDReader {
    private static final char[] ALPHABET;
    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
    private static final int ASTERISK_ENCODING;
    static final int[] CHARACTER_ENCODINGS;
    private final int[] counters;
    private final StringBuilder decodeRowResult;
    private final boolean extendedMode;
    private final boolean usingCheckDigit;

    static {
        int[] arrn;
        ALPHABET = ALPHABET_STRING.toCharArray();
        int[] arrn2 = arrn = new int[44];
        arrn2[0] = 52;
        arrn2[1] = 289;
        arrn2[2] = 97;
        arrn2[3] = 352;
        arrn2[4] = 49;
        arrn2[5] = 304;
        arrn2[6] = 112;
        arrn2[7] = 37;
        arrn2[8] = 292;
        arrn2[9] = 100;
        arrn2[10] = 265;
        arrn2[11] = 73;
        arrn2[12] = 328;
        arrn2[13] = 25;
        arrn2[14] = 280;
        arrn2[15] = 88;
        arrn2[16] = 13;
        arrn2[17] = 268;
        arrn2[18] = 76;
        arrn2[19] = 28;
        arrn2[20] = 259;
        arrn2[21] = 67;
        arrn2[22] = 322;
        arrn2[23] = 19;
        arrn2[24] = 274;
        arrn2[25] = 82;
        arrn2[26] = 7;
        arrn2[27] = 262;
        arrn2[28] = 70;
        arrn2[29] = 22;
        arrn2[30] = 385;
        arrn2[31] = 193;
        arrn2[32] = 448;
        arrn2[33] = 145;
        arrn2[34] = 400;
        arrn2[35] = 208;
        arrn2[36] = 133;
        arrn2[37] = 388;
        arrn2[38] = 196;
        arrn2[39] = 148;
        arrn2[40] = 168;
        arrn2[41] = 162;
        arrn2[42] = 138;
        arrn2[43] = 42;
        CHARACTER_ENCODINGS = arrn;
        ASTERISK_ENCODING = arrn[39];
    }

    public Code39Reader() {
        this(false);
    }

    public Code39Reader(boolean bl) {
        this(bl, false);
    }

    public Code39Reader(boolean bl, boolean bl2) {
        this.usingCheckDigit = bl;
        this.extendedMode = bl2;
        this.decodeRowResult = new StringBuilder(20);
        this.counters = new int[9];
    }

    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int n = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        int n2 = 0;
        while (n2 < n) {
            block4 : {
                int n3;
                int n4;
                block9 : {
                    block11 : {
                        block5 : {
                            block6 : {
                                block7 : {
                                    block10 : {
                                        block8 : {
                                            block3 : {
                                                n3 = charSequence.charAt(n2);
                                                if (n3 == 43 || n3 == 36 || n3 == 37 || n3 == 47) break block3;
                                                stringBuilder.append((char)n3);
                                                break block4;
                                            }
                                            n4 = n2 + 1;
                                            n2 = charSequence.charAt(n4);
                                            if (n3 == 36) break block5;
                                            if (n3 == 37) break block6;
                                            if (n3 == 43) break block7;
                                            if (n3 == 47) break block8;
                                            n3 = n2 = 0;
                                            break block9;
                                        }
                                        if (n2 < 65 || n2 > 79) break block10;
                                        n2 -= 32;
                                        break block11;
                                    }
                                    if (n2 != 90) throw FormatException.getFormatInstance();
                                    n3 = n2 = 58;
                                    break block9;
                                }
                                if (n2 < 65) throw FormatException.getFormatInstance();
                                if (n2 > 90) throw FormatException.getFormatInstance();
                                n2 += 32;
                                break block11;
                            }
                            if (n2 >= 65 && n2 <= 69) {
                                n2 -= 38;
                            } else {
                                if (n2 < 70) throw FormatException.getFormatInstance();
                                if (n2 > 87) throw FormatException.getFormatInstance();
                                n2 -= 11;
                            }
                            break block11;
                        }
                        if (n2 < 65) throw FormatException.getFormatInstance();
                        if (n2 > 90) throw FormatException.getFormatInstance();
                        n2 -= 64;
                    }
                    n3 = n2 = (int)((char)n2);
                }
                stringBuilder.append((char)n3);
                n2 = n4;
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    private static int[] findAsteriskPattern(BitArray bitArray, int[] arrn) throws NotFoundException {
        int n = bitArray.getSize();
        int n2 = bitArray.getNextSet(0);
        int n3 = arrn.length;
        int n4 = n2;
        boolean bl = false;
        int n5 = 0;
        while (n2 < n) {
            if (bitArray.get(n2) ^ bl) {
                arrn[n5] = arrn[n5] + 1;
            } else {
                int n6 = n3 - 1;
                if (n5 == n6) {
                    if (Code39Reader.toNarrowWidePattern(arrn) == ASTERISK_ENCODING && bitArray.isRange(Math.max(0, n4 - (n2 - n4) / 2), n4, false)) {
                        return new int[]{n4, n2};
                    }
                    n4 += arrn[0] + arrn[1];
                    int n7 = n3 - 2;
                    System.arraycopy(arrn, 2, arrn, 0, n7);
                    arrn[n7] = 0;
                    arrn[n6] = 0;
                    --n5;
                } else {
                    ++n5;
                }
                arrn[n5] = 1;
                bl ^= true;
            }
            ++n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static char patternToChar(int n) throws NotFoundException {
        int[] arrn;
        int n2 = 0;
        while (n2 < (arrn = CHARACTER_ENCODINGS).length) {
            if (arrn[n2] == n) {
                return ALPHABET[n2];
            }
            ++n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int toNarrowWidePattern(int[] arrn) {
        int n;
        int n22;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = arrn.length;
        int n8 = 0;
        int n9 = 0;
        do {
            n = Integer.MAX_VALUE;
            for (int n22 : arrn) {
                n6 = n;
                if (n22 < n) {
                    n6 = n;
                    if (n22 > n9) {
                        n6 = n22;
                    }
                }
                n = n6;
            }
            n9 = 0;
            n6 = 0;
            n4 = 0;
            for (n22 = 0; n22 < n7; ++n22) {
                int n10 = arrn[n22];
                int n11 = n9;
                n3 = n6;
                n5 = n4;
                if (n10 > n) {
                    n3 = n6 | 1 << n7 - 1 - n22;
                    n11 = n9 + 1;
                    n5 = n4 + n10;
                }
                n9 = n11;
                n6 = n3;
                n4 = n5;
            }
            if (n9 == 3) break;
            if (n9 <= 3) {
                return -1;
            }
            n9 = n;
        } while (true);
        n5 = n8;
        while (n5 < n7) {
            if (n9 <= 0) return n6;
            n3 = arrn[n5];
            n22 = n9;
            if (n3 > n) {
                n22 = n9 - 1;
                if (n3 * 2 >= n4) {
                    return -1;
                }
            }
            ++n5;
            n9 = n22;
        }
        return n6;
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        Object object3 = this.counters;
        Arrays.fill(object3, 0);
        Object object4 = this.decodeRowResult;
        ((StringBuilder)object4).setLength(0);
        object2 = Code39Reader.findAsteriskPattern((BitArray)object, object3);
        int n2 = ((BitArray)object).getNextSet(object2[1]);
        int n3 = ((BitArray)object).getSize();
        do {
            Code39Reader.recordPattern((BitArray)object, n2, object3);
            int n4 = Code39Reader.toNarrowWidePattern(object3);
            if (n4 < 0) throw NotFoundException.getNotFoundInstance();
            char c = Code39Reader.patternToChar(n4);
            ((StringBuilder)object4).append(c);
            int n5 = ((int[])object3).length;
            int n6 = n2;
            for (n4 = 0; n4 < n5; n6 += object3[n4], ++n4) {
            }
            n5 = ((BitArray)object).getNextSet(n6);
            if (c == '*') {
                ((StringBuilder)object4).setLength(((StringBuilder)object4).length() - 1);
                int n7 = ((int[])object3).length;
                n4 = 0;
                for (n6 = 0; n6 < n7; n4 += object3[n6], ++n6) {
                }
                if (n5 != n3) {
                    if ((n5 - n2 - n4) * 2 < n4) throw NotFoundException.getNotFoundInstance();
                }
                if (this.usingCheckDigit) {
                    n3 = ((StringBuilder)object4).length() - 1;
                    n5 = 0;
                    for (n6 = 0; n6 < n3; n5 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf((int)this.decodeRowResult.charAt((int)n6)), ++n6) {
                    }
                    if (((StringBuilder)object4).charAt(n3) != ALPHABET[n5 % 43]) throw ChecksumException.getChecksumInstance();
                    ((StringBuilder)object4).setLength(n3);
                }
                if (((StringBuilder)object4).length() == 0) throw NotFoundException.getNotFoundInstance();
                object = this.extendedMode ? Code39Reader.decodeExtended((CharSequence)object4) : ((StringBuilder)object4).toString();
                float f = (float)(object2[1] + object2[0]) / 2.0f;
                float f2 = n2;
                float f3 = (float)n4 / 2.0f;
                float f4 = n;
                object2 = new ResultPoint(f, f4);
                object4 = new ResultPoint(f2 + f3, f4);
                object3 = BarcodeFormat.CODE_39;
                return new Result((String)object, null, new ResultPoint[]{object2, object4}, (BarcodeFormat)((Object)object3));
            }
            n2 = n5;
        } while (true);
    }
}

