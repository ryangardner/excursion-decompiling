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

public final class Code93Reader
extends OneDReader {
    private static final char[] ALPHABET;
    private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
    private static final int ASTERISK_ENCODING;
    private static final int[] CHARACTER_ENCODINGS;
    private final int[] counters = new int[6];
    private final StringBuilder decodeRowResult = new StringBuilder(20);

    static {
        int[] arrn;
        ALPHABET = ALPHABET_STRING.toCharArray();
        int[] arrn2 = arrn = new int[48];
        arrn2[0] = 276;
        arrn2[1] = 328;
        arrn2[2] = 324;
        arrn2[3] = 322;
        arrn2[4] = 296;
        arrn2[5] = 292;
        arrn2[6] = 290;
        arrn2[7] = 336;
        arrn2[8] = 274;
        arrn2[9] = 266;
        arrn2[10] = 424;
        arrn2[11] = 420;
        arrn2[12] = 418;
        arrn2[13] = 404;
        arrn2[14] = 402;
        arrn2[15] = 394;
        arrn2[16] = 360;
        arrn2[17] = 356;
        arrn2[18] = 354;
        arrn2[19] = 308;
        arrn2[20] = 282;
        arrn2[21] = 344;
        arrn2[22] = 332;
        arrn2[23] = 326;
        arrn2[24] = 300;
        arrn2[25] = 278;
        arrn2[26] = 436;
        arrn2[27] = 434;
        arrn2[28] = 428;
        arrn2[29] = 422;
        arrn2[30] = 406;
        arrn2[31] = 410;
        arrn2[32] = 364;
        arrn2[33] = 358;
        arrn2[34] = 310;
        arrn2[35] = 314;
        arrn2[36] = 302;
        arrn2[37] = 468;
        arrn2[38] = 466;
        arrn2[39] = 458;
        arrn2[40] = 366;
        arrn2[41] = 374;
        arrn2[42] = 430;
        arrn2[43] = 294;
        arrn2[44] = 474;
        arrn2[45] = 470;
        arrn2[46] = 306;
        arrn2[47] = 350;
        CHARACTER_ENCODINGS = arrn;
        ASTERISK_ENCODING = arrn[47];
    }

    private static void checkChecksums(CharSequence charSequence) throws ChecksumException {
        int n = charSequence.length();
        Code93Reader.checkOneChecksum(charSequence, n - 2, 20);
        Code93Reader.checkOneChecksum(charSequence, n - 1, 15);
    }

    private static void checkOneChecksum(CharSequence charSequence, int n, int n2) throws ChecksumException {
        int n3 = n - 1;
        int n4 = 0;
        int n5 = 1;
        do {
            int n6;
            if (n3 < 0) {
                if (charSequence.charAt(n) != ALPHABET[n4 % 47]) throw ChecksumException.getChecksumInstance();
                return;
            }
            n4 += ALPHABET_STRING.indexOf(charSequence.charAt(n3)) * n5;
            n5 = n6 = n5 + 1;
            if (n6 > n2) {
                n5 = 1;
            }
            --n3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    private static String decodeExtended(CharSequence var0) throws FormatException {
        var1_1 = var0.length();
        var2_2 = new StringBuilder(var1_1);
        var3_3 = 0;
        while (var3_3 < var1_1) {
            var4_4 = var0.charAt(var3_3);
            if (var4_4 >= 97 && var4_4 <= 100) {
                if (var3_3 >= var1_1 - 1) throw FormatException.getFormatInstance();
                var5_5 = var3_3 + 1;
                var3_3 = var0.charAt(var5_5);
                switch (var4_4) {
                    default: {
                        var4_4 = var3_3 = 0;
                        break;
                    }
                    case 100: {
                        if (var3_3 < 65) throw FormatException.getFormatInstance();
                        if (var3_3 > 90) throw FormatException.getFormatInstance();
                        var3_3 += 32;
                        ** break;
                    }
                    case 99: {
                        if (var3_3 >= 65 && var3_3 <= 79) {
                            var3_3 -= 32;
                            ** break;
                        }
                        if (var3_3 != 90) throw FormatException.getFormatInstance();
                        var4_4 = var3_3 = 58;
                        break;
                    }
                    case 98: {
                        if (var3_3 >= 65 && var3_3 <= 69) {
                            var3_3 -= 38;
                            ** break;
                        }
                        if (var3_3 >= 70 && var3_3 <= 74) {
                            var3_3 -= 11;
                            ** break;
                        }
                        if (var3_3 >= 75 && var3_3 <= 79) {
                            var3_3 += 16;
                            ** break;
                        }
                        if (var3_3 >= 80 && var3_3 <= 83) {
                            var3_3 += 43;
                            ** break;
                        }
                        if (var3_3 < 84) throw FormatException.getFormatInstance();
                        if (var3_3 > 90) throw FormatException.getFormatInstance();
                        var4_4 = var3_3 = 127;
                        break;
                    }
                    case 97: {
                        if (var3_3 < 65) throw FormatException.getFormatInstance();
                        if (var3_3 > 90) throw FormatException.getFormatInstance();
                        var3_3 -= 64;
lbl47: // 7 sources:
                        var4_4 = var3_3 = (int)((char)var3_3);
                    }
                }
                var2_2.append((char)var4_4);
                var3_3 = var5_5;
            } else {
                var2_2.append((char)var4_4);
            }
            ++var3_3;
        }
        return var2_2.toString();
    }

    private int[] findAsteriskPattern(BitArray bitArray) throws NotFoundException {
        int n = bitArray.getSize();
        int n2 = bitArray.getNextSet(0);
        Arrays.fill(this.counters, 0);
        int[] arrn = this.counters;
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
                    if (Code93Reader.toPattern(arrn) == ASTERISK_ENCODING) {
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

    private static int toPattern(int[] arrn) {
        int n;
        int n2 = arrn.length;
        int n3 = arrn.length;
        int n4 = 0;
        for (n = 0; n < n3; n4 += arrn[n], ++n) {
        }
        int n5 = 0;
        n = 0;
        while (n5 < n2) {
            int n6 = Math.round((float)arrn[n5] * 9.0f / (float)n4);
            if (n6 < 1) return -1;
            if (n6 > 4) {
                return -1;
            }
            if ((n5 & 1) == 0) {
                int n7 = 0;
                do {
                    n3 = n;
                    if (n7 < n6) {
                        n = n << 1 | 1;
                        ++n7;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                n3 = n << n6;
            }
            ++n5;
            n = n3;
        }
        return n;
    }

    @Override
    public Result decodeRow(int n, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        object2 = this.findAsteriskPattern((BitArray)object);
        int n2 = ((BitArray)object).getNextSet(object2[1]);
        int n3 = ((BitArray)object).getSize();
        Object object3 = this.counters;
        Arrays.fill(object3, 0);
        Object object4 = this.decodeRowResult;
        ((StringBuilder)object4).setLength(0);
        do {
            Code93Reader.recordPattern((BitArray)object, n2, object3);
            int n4 = Code93Reader.toPattern(object3);
            if (n4 < 0) throw NotFoundException.getNotFoundInstance();
            char c = Code93Reader.patternToChar(n4);
            ((StringBuilder)object4).append(c);
            int n5 = ((int[])object3).length;
            int n6 = n2;
            for (n4 = 0; n4 < n5; n6 += object3[n4], ++n4) {
            }
            n5 = ((BitArray)object).getNextSet(n6);
            if (c == '*') {
                ((StringBuilder)object4).deleteCharAt(((StringBuilder)object4).length() - 1);
                int n7 = ((int[])object3).length;
                n4 = 0;
                n6 = 0;
                do {
                    if (n4 >= n7) {
                        if (n5 == n3) throw NotFoundException.getNotFoundInstance();
                        if (!((BitArray)object).get(n5)) throw NotFoundException.getNotFoundInstance();
                        if (((StringBuilder)object4).length() < 2) throw NotFoundException.getNotFoundInstance();
                        Code93Reader.checkChecksums((CharSequence)object4);
                        ((StringBuilder)object4).setLength(((StringBuilder)object4).length() - 2);
                        object = Code93Reader.decodeExtended((CharSequence)object4);
                        float f = (float)(object2[1] + object2[0]) / 2.0f;
                        float f2 = n2;
                        float f3 = (float)n6 / 2.0f;
                        float f4 = n;
                        object2 = new ResultPoint(f, f4);
                        object4 = new ResultPoint(f2 + f3, f4);
                        object3 = BarcodeFormat.CODE_93;
                        return new Result((String)object, null, new ResultPoint[]{object2, object4}, (BarcodeFormat)((Object)object3));
                    }
                    n6 += object3[n4];
                    ++n4;
                } while (true);
            }
            n2 = n5;
        } while (true);
    }
}

