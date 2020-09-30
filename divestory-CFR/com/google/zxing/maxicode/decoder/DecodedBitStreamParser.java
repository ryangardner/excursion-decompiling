/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.maxicode.decoder;

import com.google.zxing.common.DecoderResult;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

final class DecodedBitStreamParser {
    private static final char ECI = '\ufffa';
    private static final char FS = '\u001c';
    private static final char GS = '\u001d';
    private static final char LATCHA = '\ufff7';
    private static final char LATCHB = '\ufff8';
    private static final char LOCK = '\ufff9';
    private static final NumberFormat NINE_DIGITS = new DecimalFormat("000000000");
    private static final char NS = '\ufffb';
    private static final char PAD = '\ufffc';
    private static final char RS = '\u001e';
    private static final String[] SETS;
    private static final char SHIFTA = '\ufff0';
    private static final char SHIFTB = '\ufff1';
    private static final char SHIFTC = '\ufff2';
    private static final char SHIFTD = '\ufff3';
    private static final char SHIFTE = '\ufff4';
    private static final char THREESHIFTA = '\ufff6';
    private static final NumberFormat THREE_DIGITS;
    private static final char TWOSHIFTA = '\ufff5';

    static {
        THREE_DIGITS = new DecimalFormat("000");
        SETS = new String[]{"\nABCDEFGHIJKLMNOPQRSTUVWXYZ\ufffa\u001c\u001d\u001e\ufffb \ufffc\"#$%&'()*+,-./0123456789:\ufff1\ufff2\ufff3\ufff4\ufff8", "`abcdefghijklmnopqrstuvwxyz\ufffa\u001c\u001d\u001e\ufffb{\ufffc}~;<=>?[\\]^_ ,./:@!|\ufffc\ufff5\ufff6\ufffc\ufff0\ufff2\ufff3\ufff4\ufff7", "\u00c0\u00c1\u00c2\u00c3\u00c4\u00c5\u00c6\u00c7\u00c8\u00c9\u00ca\u00cb\u00cc\u00cd\u00ce\u00cf\u00d0\u00d1\u00d2\u00d3\u00d4\u00d5\u00d6\u00d7\u00d8\u00d9\u00da\ufffa\u001c\u001d\u001e\u00db\u00dc\u00dd\u00de\u00df\u00aa\u00ac\u00b1\u00b2\u00b3\u00b5\u00b9\u00ba\u00bc\u00bd\u00be\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\ufff7 \ufff9\ufff3\ufff4\ufff8", "\u00e0\u00e1\u00e2\u00e3\u00e4\u00e5\u00e6\u00e7\u00e8\u00e9\u00ea\u00eb\u00ec\u00ed\u00ee\u00ef\u00f0\u00f1\u00f2\u00f3\u00f4\u00f5\u00f6\u00f7\u00f8\u00f9\u00fa\ufffa\u001c\u001d\u001e\ufffb\u00fb\u00fc\u00fd\u00fe\u00ff\u00a1\u00a8\u00ab\u00af\u00b0\u00b4\u00b7\u00b8\u00bb\u00bf\u008a\u008b\u008c\u008d\u008e\u008f\u0090\u0091\u0092\u0093\u0094\ufff7 \ufff2\ufff9\ufff4\ufff8", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\ufffa\ufffc\ufffc\u001b\ufffb\u001c\u001d\u001e\u001f\u009f\u00a0\u00a2\u00a3\u00a4\u00a5\u00a6\u00a7\u00a9\u00ad\u00ae\u00b6\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\ufff7 \ufff2\ufff3\ufff9\ufff8", "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?"};
    }

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] arrby, int n) {
        String string2;
        StringBuilder stringBuilder = new StringBuilder(144);
        if (n != 2 && n != 3) {
            if (n == 4) {
                stringBuilder.append(DecodedBitStreamParser.getMessage(arrby, 1, 93));
                return new DecoderResult(arrby, stringBuilder.toString(), null, String.valueOf(n));
            }
            if (n != 5) {
                return new DecoderResult(arrby, stringBuilder.toString(), null, String.valueOf(n));
            }
            stringBuilder.append(DecodedBitStreamParser.getMessage(arrby, 1, 77));
            return new DecoderResult(arrby, stringBuilder.toString(), null, String.valueOf(n));
        }
        if (n == 2) {
            int n2 = DecodedBitStreamParser.getPostCode2(arrby);
            string2 = new DecimalFormat("0000000000".substring(0, DecodedBitStreamParser.getPostCode2Length(arrby))).format(n2);
        } else {
            string2 = DecodedBitStreamParser.getPostCode3(arrby);
        }
        String string3 = THREE_DIGITS.format(DecodedBitStreamParser.getCountry(arrby));
        String string4 = THREE_DIGITS.format(DecodedBitStreamParser.getServiceClass(arrby));
        stringBuilder.append(DecodedBitStreamParser.getMessage(arrby, 10, 84));
        if (stringBuilder.toString().startsWith("[)>\u001e01\u001d")) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(string2);
            stringBuilder2.append('\u001d');
            stringBuilder2.append(string3);
            stringBuilder2.append('\u001d');
            stringBuilder2.append(string4);
            stringBuilder2.append('\u001d');
            stringBuilder.insert(9, stringBuilder2.toString());
            return new DecoderResult(arrby, stringBuilder.toString(), null, String.valueOf(n));
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(string2);
        stringBuilder3.append('\u001d');
        stringBuilder3.append(string3);
        stringBuilder3.append('\u001d');
        stringBuilder3.append(string4);
        stringBuilder3.append('\u001d');
        stringBuilder.insert(0, stringBuilder3.toString());
        return new DecoderResult(arrby, stringBuilder.toString(), null, String.valueOf(n));
    }

    private static int getBit(int n, byte[] arrby) {
        int n2 = n - 1;
        byte by = arrby[n2 / 6];
        n = 1;
        if ((1 << 5 - n2 % 6 & by) != 0) return n;
        return 0;
    }

    private static int getCountry(byte[] arrby) {
        return DecodedBitStreamParser.getInt(arrby, new byte[]{53, 54, 43, 44, 45, 46, 47, 48, 37, 38});
    }

    private static int getInt(byte[] arrby, byte[] arrby2) {
        if (arrby2.length == 0) throw new IllegalArgumentException();
        int n = 0;
        int n2 = 0;
        while (n < arrby2.length) {
            n2 += DecodedBitStreamParser.getBit(arrby2[n], arrby) << arrby2.length - n - 1;
            ++n;
        }
        return n2;
    }

    /*
     * Unable to fully structure code
     */
    private static String getMessage(byte[] var0, int var1_1, int var2_2) {
        var3_3 = new StringBuilder();
        var4_4 = var1_1;
        var5_5 = 0;
        var6_6 = -1;
        var7_7 = 0;
        do {
            block13 : {
                if (var4_4 >= var1_1 + var2_2) {
                    while (var3_3.length() > 0) {
                        if (var3_3.charAt(var3_3.length() - 1) != '\ufffc') return var3_3.toString();
                        var3_3.setLength(var3_3.length() - 1);
                    }
                    return var3_3.toString();
                }
                var8_8 = DecodedBitStreamParser.SETS[var5_5].charAt(var0[var4_4]);
                var9_9 = var5_5;
                switch (var8_8) {
                    default: {
                        var3_3.append(var8_8);
                        var9_9 = var7_7;
                        break block13;
                    }
                    case '\ufffb': {
                        var9_9 = var0[++var4_4];
                        var10_10 = var0[++var4_4];
                        var11_11 = var0[++var4_4];
                        var12_12 = var0[++var4_4];
                        var13_13 = var0[++var4_4];
                        var3_3.append(DecodedBitStreamParser.NINE_DIGITS.format((var9_9 << 24) + (var10_10 << 18) + (var11_11 << 12) + (var12_12 << 6) + var13_13));
                        var9_9 = var7_7;
                        break block13;
                    }
                    case '\ufff8': {
                        var9_9 = 1;
                        ** GOTO lbl36
                    }
                    case '\ufff7': {
                        var9_9 = 0;
                    }
lbl36: // 3 sources:
                    case '\ufff9': {
                        var6_6 = -1;
                        var5_5 = var9_9;
                        var9_9 = var7_7;
                        break block13;
                    }
                    case '\ufff6': {
                        var6_6 = 3;
                        ** GOTO lbl46
                    }
                    case '\ufff5': {
                        var6_6 = 2;
lbl46: // 2 sources:
                        var9_9 = var5_5;
                        var5_5 = 0;
                        break block13;
                    }
                    case '\ufff0': 
                    case '\ufff1': 
                    case '\ufff2': 
                    case '\ufff3': 
                    case '\ufff4': 
                }
                var7_7 = var8_8 - 65520;
                var6_6 = 1;
                var9_9 = var5_5;
                var5_5 = var7_7;
            }
            if (var6_6 == 0) {
                var5_5 = var9_9;
            }
            ++var4_4;
            --var6_6;
            var7_7 = var9_9;
        } while (true);
    }

    private static int getPostCode2(byte[] arrby) {
        return DecodedBitStreamParser.getInt(arrby, new byte[]{33, 34, 35, 36, 25, 26, 27, 28, 29, 30, 19, 20, 21, 22, 23, 24, 13, 14, 15, 16, 17, 18, 7, 8, 9, 10, 11, 12, 1, 2});
    }

    private static int getPostCode2Length(byte[] arrby) {
        return DecodedBitStreamParser.getInt(arrby, new byte[]{39, 40, 41, 42, 31, 32});
    }

    private static String getPostCode3(byte[] arrby) {
        return String.valueOf(new char[]{SETS[0].charAt(DecodedBitStreamParser.getInt(arrby, new byte[]{39, 40, 41, 42, 31, 32})), SETS[0].charAt(DecodedBitStreamParser.getInt(arrby, new byte[]{33, 34, 35, 36, 25, 26})), SETS[0].charAt(DecodedBitStreamParser.getInt(arrby, new byte[]{27, 28, 29, 30, 19, 20})), SETS[0].charAt(DecodedBitStreamParser.getInt(arrby, new byte[]{21, 22, 23, 24, 13, 14})), SETS[0].charAt(DecodedBitStreamParser.getInt(arrby, new byte[]{15, 16, 17, 18, 7, 8})), SETS[0].charAt(DecodedBitStreamParser.getInt(arrby, new byte[]{9, 10, 11, 12, 1, 2}))});
    }

    private static int getServiceClass(byte[] arrby) {
        return DecodedBitStreamParser.getInt(arrby, new byte[]{55, 56, 57, 58, 59, 60, 49, 50, 51, 52});
    }
}

