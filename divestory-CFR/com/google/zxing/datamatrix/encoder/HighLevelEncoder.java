/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.ASCIIEncoder;
import com.google.zxing.datamatrix.encoder.Base256Encoder;
import com.google.zxing.datamatrix.encoder.C40Encoder;
import com.google.zxing.datamatrix.encoder.EdifactEncoder;
import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.datamatrix.encoder.TextEncoder;
import com.google.zxing.datamatrix.encoder.X12Encoder;
import java.util.Arrays;

public final class HighLevelEncoder {
    static final int ASCII_ENCODATION = 0;
    static final int BASE256_ENCODATION = 5;
    static final int C40_ENCODATION = 1;
    static final char C40_UNLATCH = '\u00fe';
    static final int EDIFACT_ENCODATION = 4;
    static final char LATCH_TO_ANSIX12 = '\u00ee';
    static final char LATCH_TO_BASE256 = '\u00e7';
    static final char LATCH_TO_C40 = '\u00e6';
    static final char LATCH_TO_EDIFACT = '\u00f0';
    static final char LATCH_TO_TEXT = '\u00ef';
    private static final char MACRO_05 = '\u00ec';
    private static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
    private static final char MACRO_06 = '\u00ed';
    private static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
    private static final String MACRO_TRAILER = "\u001e\u0004";
    private static final char PAD = '\u0081';
    static final int TEXT_ENCODATION = 2;
    static final char UPPER_SHIFT = '\u00eb';
    static final int X12_ENCODATION = 3;
    static final char X12_UNLATCH = '\u00fe';

    private HighLevelEncoder() {
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        int n3 = 0;
        int n4 = 0;
        if (n >= n2) return n3;
        n3 = charSequence.charAt(n);
        int n5 = n;
        int n6 = n3;
        n = n4;
        do {
            n3 = n;
            if (!HighLevelEncoder.isDigit((char)n6)) return n3;
            n3 = n;
            if (n5 >= n2) return n3;
            n3 = n + 1;
            n4 = n5 + 1;
            n = n3;
            n5 = n4;
            if (n4 >= n2) continue;
            n5 = charSequence.charAt(n4);
            n = n3;
            n6 = n5;
            n5 = n4;
        } while (true);
    }

    public static String encodeHighLevel(String string2) {
        return HighLevelEncoder.encodeHighLevel(string2, SymbolShapeHint.FORCE_NONE, null, null);
    }

    public static String encodeHighLevel(String charSequence, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2) {
        int n;
        ASCIIEncoder aSCIIEncoder = new ASCIIEncoder();
        int n2 = 0;
        C40Encoder c40Encoder = new C40Encoder();
        TextEncoder textEncoder = new TextEncoder();
        X12Encoder x12Encoder = new X12Encoder();
        EdifactEncoder edifactEncoder = new EdifactEncoder();
        Base256Encoder base256Encoder = new Base256Encoder();
        EncoderContext encoderContext = new EncoderContext((String)charSequence);
        encoderContext.setSymbolShape(symbolShapeHint);
        encoderContext.setSizeConstraints(dimension, dimension2);
        if (((String)charSequence).startsWith(MACRO_05_HEADER) && ((String)charSequence).endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword('\u00ec');
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += 7;
            n = n2;
        } else {
            n = n2;
            if (((String)charSequence).startsWith(MACRO_06_HEADER)) {
                n = n2;
                if (((String)charSequence).endsWith(MACRO_TRAILER)) {
                    encoderContext.writeCodeword('\u00ed');
                    encoderContext.setSkipAtEnd(2);
                    encoderContext.pos += 7;
                    n = n2;
                }
            }
        }
        while (encoderContext.hasMoreCharacters()) {
            new Encoder[]{aSCIIEncoder, c40Encoder, textEncoder, x12Encoder, edifactEncoder, base256Encoder}[n].encode(encoderContext);
            if (encoderContext.getNewEncoding() < 0) continue;
            n = encoderContext.getNewEncoding();
            encoderContext.resetEncoderSignal();
        }
        int n3 = encoderContext.getCodewordCount();
        encoderContext.updateSymbolInfo();
        n2 = encoderContext.getSymbolInfo().getDataCapacity();
        if (n3 < n2 && n != 0 && n != 5) {
            encoderContext.writeCodeword('\u00fe');
        }
        if (((StringBuilder)(charSequence = encoderContext.getCodewords())).length() < n2) {
            ((StringBuilder)charSequence).append('\u0081');
        }
        while (((StringBuilder)charSequence).length() < n2) {
            ((StringBuilder)charSequence).append(HighLevelEncoder.randomize253State('\u0081', ((StringBuilder)charSequence).length() + 1));
        }
        return encoderContext.getCodewords().toString();
    }

    private static int findMinimums(float[] arrf, int[] arrn, int n, byte[] arrby) {
        Arrays.fill(arrby, (byte)0);
        int n2 = 0;
        while (n2 < 6) {
            arrn[n2] = (int)Math.ceil(arrf[n2]);
            int n3 = arrn[n2];
            int n4 = n;
            if (n > n3) {
                Arrays.fill(arrby, (byte)0);
                n4 = n3;
            }
            if (n4 == n3) {
                arrby[n2] = (byte)(arrby[n2] + 1);
            }
            ++n2;
            n = n4;
        }
        return n;
    }

    private static int getMinimumCount(byte[] arrby) {
        int n = 0;
        int n2 = 0;
        while (n < 6) {
            n2 += arrby[n];
            ++n;
        }
        return n2;
    }

    static void illegalCharacter(char c) {
        CharSequence charSequence = Integer.toHexString(c);
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("0000".substring(0, 4 - ((String)charSequence).length()));
        charSequence2.append((String)charSequence);
        charSequence2 = charSequence2.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Illegal character: ");
        ((StringBuilder)charSequence).append(c);
        ((StringBuilder)charSequence).append(" (0x");
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(')');
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    static boolean isDigit(char c) {
        if (c < '0') return false;
        if (c > '9') return false;
        return true;
    }

    static boolean isExtendedASCII(char c) {
        if (c < '') return false;
        if (c > '\u00ff') return false;
        return true;
    }

    private static boolean isNativeC40(char c) {
        if (c == ' ') return true;
        if (c >= '0') {
            if (c <= '9') return true;
        }
        if (c < 'A') return false;
        if (c > 'Z') return false;
        return true;
    }

    private static boolean isNativeEDIFACT(char c) {
        if (c < ' ') return false;
        if (c > '^') return false;
        return true;
    }

    private static boolean isNativeText(char c) {
        if (c == ' ') return true;
        if (c >= '0') {
            if (c <= '9') return true;
        }
        if (c < 'a') return false;
        if (c > 'z') return false;
        return true;
    }

    private static boolean isNativeX12(char c) {
        if (HighLevelEncoder.isX12TermSep(c)) return true;
        if (c == ' ') return true;
        if (c >= '0') {
            if (c <= '9') return true;
        }
        if (c < 'A') return false;
        if (c > 'Z') return false;
        return true;
    }

    private static boolean isSpecialB256(char c) {
        return false;
    }

    private static boolean isX12TermSep(char c) {
        if (c == '\r') return true;
        if (c == '*') return true;
        if (c == '>') return true;
        return false;
    }

    /*
     * Unable to fully structure code
     */
    static int lookAheadTest(CharSequence var0, int var1_1, int var2_2) {
        if (var1_1 >= var0.length()) {
            return var2_2;
        }
        if (var2_2 == 0) {
            v0 = var3_3 = new float[6];
            v0[0] = 0.0f;
            v0[1] = 1.0f;
            v0[2] = 1.0f;
            v0[3] = 1.0f;
            v0[4] = 1.0f;
            v0[5] = 1.25f;
        } else {
            v1 = var3_3 = new float[6];
            v1[0] = 1.0f;
            v1[1] = 2.0f;
            v1[2] = 2.0f;
            v1[3] = 2.0f;
            v1[4] = 2.0f;
            v1[5] = 2.25f;
            var3_3[var2_2] = 0.0f;
        }
        var2_2 = 0;
        do lbl-1000: // 7 sources:
        {
            if ((var4_4 = var1_1 + var2_2) == var0.length()) {
                var5_5 = new byte[6];
                var0 = new int[6];
                var1_1 = HighLevelEncoder.findMinimums(var3_3, var0, Integer.MAX_VALUE, var5_5);
                var2_2 = HighLevelEncoder.getMinimumCount(var5_5);
                if (var0[0] == var1_1) {
                    return 0;
                }
                if (var2_2 == 1 && var5_5[5] > 0) {
                    return 5;
                }
                if (var2_2 == 1 && var5_5[4] > 0) {
                    return 4;
                }
                if (var2_2 == 1 && var5_5[2] > 0) {
                    return 2;
                }
                if (var2_2 != 1) return 1;
                if (var5_5[3] <= 0) return 1;
                return 3;
            }
            var6_6 = var0.charAt(var4_4);
            var4_4 = var2_2 + 1;
            if (HighLevelEncoder.isDigit(var6_6)) {
                var3_3[0] = (float)((double)var3_3[0] + 0.5);
            } else if (HighLevelEncoder.isExtendedASCII(var6_6)) {
                var3_3[0] = (int)Math.ceil(var3_3[0]);
                var3_3[0] = var3_3[0] + 2.0f;
            } else {
                var3_3[0] = (int)Math.ceil(var3_3[0]);
                var3_3[0] = var3_3[0] + 1.0f;
            }
            var3_3[1] = HighLevelEncoder.isNativeC40(var6_6) != false ? var3_3[1] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(var6_6) != false ? var3_3[1] + 2.6666667f : var3_3[1] + 1.3333334f);
            var3_3[2] = HighLevelEncoder.isNativeText(var6_6) != false ? var3_3[2] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(var6_6) != false ? var3_3[2] + 2.6666667f : var3_3[2] + 1.3333334f);
            var3_3[3] = HighLevelEncoder.isNativeX12(var6_6) != false ? var3_3[3] + 0.6666667f : (HighLevelEncoder.isExtendedASCII(var6_6) != false ? var3_3[3] + 4.3333335f : var3_3[3] + 3.3333333f);
            var3_3[4] = HighLevelEncoder.isNativeEDIFACT(var6_6) != false ? var3_3[4] + 0.75f : (HighLevelEncoder.isExtendedASCII(var6_6) != false ? var3_3[4] + 4.25f : var3_3[4] + 3.25f);
            var3_3[5] = HighLevelEncoder.isSpecialB256(var6_6) != false ? var3_3[5] + 4.0f : var3_3[5] + 1.0f;
            var2_2 = var4_4;
            if (var4_4 < 4) ** GOTO lbl-1000
            var5_5 = new int[6];
            var7_7 = new byte[6];
            HighLevelEncoder.findMinimums(var3_3, var5_5, Integer.MAX_VALUE, var7_7);
            var2_2 = HighLevelEncoder.getMinimumCount(var7_7);
            if (var5_5[0] < var5_5[5] && var5_5[0] < var5_5[1] && var5_5[0] < var5_5[2] && var5_5[0] < var5_5[3] && var5_5[0] < var5_5[4]) {
                return 0;
            }
            if (var5_5[5] < var5_5[0]) return 5;
            if (var7_7[1] + var7_7[2] + var7_7[3] + var7_7[4] == 0) {
                return 5;
            }
            if (var2_2 == 1 && var7_7[4] > 0) {
                return 4;
            }
            if (var2_2 == 1 && var7_7[2] > 0) {
                return 2;
            }
            if (var2_2 == 1 && var7_7[3] > 0) {
                return 3;
            }
            var2_2 = var4_4;
            if (var5_5[1] + 1 >= var5_5[0]) ** GOTO lbl-1000
            var2_2 = var4_4;
            if (var5_5[1] + 1 >= var5_5[5]) ** GOTO lbl-1000
            var2_2 = var4_4;
            if (var5_5[1] + 1 >= var5_5[4]) ** GOTO lbl-1000
            var2_2 = var4_4;
            if (var5_5[1] + 1 >= var5_5[2]) ** GOTO lbl-1000
            if (var5_5[1] < var5_5[3]) {
                return 1;
            }
            var2_2 = var4_4;
        } while (var5_5[1] != var5_5[3]);
        var1_1 = var1_1 + var4_4 + 1;
        while (var1_1 < var0.length()) {
            var6_6 = var0.charAt(var1_1);
            if (HighLevelEncoder.isX12TermSep(var6_6)) {
                return 3;
            }
            if (!HighLevelEncoder.isNativeX12(var6_6)) {
                return 1;
            }
            ++var1_1;
        }
        return 1;
    }

    private static char randomize253State(char c, int n) {
        if ((c = (char)(c + (n * 149 % 253 + 1))) <= '\u00fe') {
            return c;
        }
        c = (char)(c - 254);
        return c;
    }
}

