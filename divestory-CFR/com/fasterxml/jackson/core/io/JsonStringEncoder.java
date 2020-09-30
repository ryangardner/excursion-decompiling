/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.util.Arrays;

public final class JsonStringEncoder {
    private static final byte[] HB;
    private static final char[] HC;
    private static final int INITIAL_BYTE_BUFFER_SIZE = 200;
    private static final int INITIAL_CHAR_BUFFER_SIZE = 120;
    private static final int SURR1_FIRST = 55296;
    private static final int SURR1_LAST = 56319;
    private static final int SURR2_FIRST = 56320;
    private static final int SURR2_LAST = 57343;
    private static final JsonStringEncoder instance;

    static {
        HC = CharTypes.copyHexChars();
        HB = CharTypes.copyHexBytes();
        instance = new JsonStringEncoder();
    }

    private int _appendByte(int n, int n2, ByteArrayBuilder byteArrayBuilder, int n3) {
        byteArrayBuilder.setCurrentSegmentLength(n3);
        byteArrayBuilder.append(92);
        if (n2 >= 0) {
            byteArrayBuilder.append((byte)n2);
            return byteArrayBuilder.getCurrentSegmentLength();
        }
        byteArrayBuilder.append(117);
        if (n > 255) {
            n2 = n >> 8;
            byteArrayBuilder.append(HB[n2 >> 4]);
            byteArrayBuilder.append(HB[n2 & 15]);
            n &= 255;
        } else {
            byteArrayBuilder.append(48);
            byteArrayBuilder.append(48);
        }
        byteArrayBuilder.append(HB[n >> 4]);
        byteArrayBuilder.append(HB[n & 15]);
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    private int _appendNamed(int n, char[] arrc) {
        arrc[1] = (char)n;
        return 2;
    }

    private int _appendNumeric(int n, char[] arrc) {
        arrc[1] = (char)117;
        char[] arrc2 = HC;
        arrc[4] = arrc2[n >> 4];
        arrc[5] = arrc2[n & 15];
        return 6;
    }

    private static int _convert(int n, int n2) {
        if (n2 >= 56320 && n2 <= 57343) {
            return (n - 55296 << 10) + 65536 + (n2 - 56320);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Broken surrogate pair: first char 0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(", second 0x");
        stringBuilder.append(Integer.toHexString(n2));
        stringBuilder.append("; illegal combination");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void _illegal(int n) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(n));
    }

    private char[] _qbuf() {
        char[] arrc = new char[6];
        arrc[0] = (char)92;
        arrc[2] = (char)48;
        arrc[3] = (char)48;
        return arrc;
    }

    public static JsonStringEncoder getInstance() {
        return instance;
    }

    /*
     * Unable to fully structure code
     */
    public byte[] encodeAsUTF8(String var1_1) {
        var2_2 = var1_1.length();
        var3_3 = 200;
        var4_4 = new byte[200];
        var5_5 = null;
        var6_22 = 0;
        var7_23 = 0;
        block0 : do {
            block16 : {
                var8_24 = var4_4;
                var9_25 = var5_6;
                var10_26 = var7_23;
                if (var6_22 >= var2_2) break;
                var10_26 = var6_22 + 1;
                var11_27 = var1_1.charAt(var6_22);
                while (var11_27 <= 127) {
                    var6_22 = var3_3;
                    var8_24 = var4_4;
                    var9_25 = var5_7;
                    var12_28 = var7_23;
                    if (var7_23 >= var3_3) {
                        var9_25 = var5_7;
                        if (var5_7 == null) {
                            var9_25 = ByteArrayBuilder.fromInitial(var4_4, var7_23);
                        }
                        var8_24 = var9_25.finishCurrentSegment();
                        var6_22 = var8_24.length;
                        var12_28 = 0;
                    }
                    var7_23 = var12_28 + 1;
                    var8_24[var12_28] = (byte)var11_27;
                    if (var10_26 >= var2_2) {
                        var10_26 = var7_23;
                        break block0;
                    }
                    var11_27 = var1_1.charAt(var10_26);
                    ++var10_26;
                    var3_3 = var6_22;
                    var4_4 = var8_24;
                    var5_8 = var9_25;
                }
                var9_25 = var5_7;
                if (var5_7 == null) {
                    var9_25 = ByteArrayBuilder.fromInitial(var4_4, var7_23);
                }
                var6_22 = var3_3;
                var5_9 = var4_4;
                var12_28 = var7_23;
                if (var7_23 >= var3_3) {
                    var5_10 = var9_25.finishCurrentSegment();
                    var6_22 = var5_10.length;
                    var12_28 = 0;
                }
                if (var11_27 >= 2048) break block16;
                var3_3 = var12_28 + 1;
                var5_11[var12_28] = (byte)(var11_27 >> 6 | 192);
                ** GOTO lbl92
            }
            if (var11_27 >= 55296 && var11_27 <= 57343) {
                if (var11_27 > 56319) {
                    JsonStringEncoder._illegal(var11_27);
                }
                if (var10_26 >= var2_2) {
                    JsonStringEncoder._illegal(var11_27);
                }
                if ((var3_3 = JsonStringEncoder._convert(var11_27, var1_1.charAt(var10_26))) > 1114111) {
                    JsonStringEncoder._illegal(var3_3);
                }
                var13_29 = var12_28 + 1;
                var5_11[var12_28] = (byte)(var3_3 >> 18 | 240);
                var11_27 = var6_22;
                var7_23 = var13_29;
                if (var13_29 >= var6_22) {
                    var5_13 = var9_25.finishCurrentSegment();
                    var11_27 = var5_13.length;
                    var7_23 = 0;
                }
                var12_28 = var7_23 + 1;
                var5_14[var7_23] = (byte)(var3_3 >> 12 & 63 | 128);
                var7_23 = var11_27;
                var6_22 = var12_28;
                if (var12_28 >= var11_27) {
                    var5_15 = var9_25.finishCurrentSegment();
                    var7_23 = var5_15.length;
                    var6_22 = 0;
                }
                var5_16[var6_22] = (byte)(var3_3 >> 6 & 63 | 128);
                var11_27 = var3_3;
                var3_3 = var10_26 + 1;
                var10_26 = var6_22 + 1;
                var6_22 = var3_3;
            } else {
                var13_29 = var12_28 + 1;
                var5_11[var12_28] = (byte)(var11_27 >> 12 | 224);
                var7_23 = var6_22;
                var3_3 = var13_29;
                if (var13_29 >= var6_22) {
                    var5_17 = var9_25.finishCurrentSegment();
                    var7_23 = var5_17.length;
                    var3_3 = 0;
                }
                var5_12[var3_3] = (byte)(var11_27 >> 6 & 63 | 128);
                ++var3_3;
                var6_22 = var7_23;
lbl92: // 2 sources:
                var7_23 = var6_22;
                var6_22 = var10_26;
                var10_26 = var3_3;
            }
            var3_3 = var7_23;
            var12_28 = var10_26;
            if (var10_26 >= var7_23) {
                var5_19 = var9_25.finishCurrentSegment();
                var3_3 = var5_19.length;
                var12_28 = 0;
            }
            var5_20[var12_28] = (byte)(var11_27 & 63 | 128);
            var7_23 = var12_28 + 1;
            var4_4 = var5_20;
            var5_21 = var9_25;
        } while (true);
        if (var9_25 != null) return var9_25.completeAndCoalesce(var10_26);
        return Arrays.copyOfRange(var8_24, 0, var10_26);
    }

    public void quoteAsString(CharSequence charSequence, StringBuilder stringBuilder) {
        int[] arrn = CharTypes.get7BitOutputEscapes();
        int n = arrn.length;
        int n2 = charSequence.length();
        char[] arrc = null;
        int n3 = 0;
        block0 : do {
            int n4;
            if (n3 >= n2) return;
            do {
                char c;
                if ((c = charSequence.charAt(n3)) < n && arrn[c] != 0) {
                    int n5;
                    char[] arrc2 = arrc;
                    if (arrc == null) {
                        arrc2 = this._qbuf();
                    }
                    n4 = (n5 = arrn[n4 = (int)charSequence.charAt(n3)]) < 0 ? this._appendNumeric(n4, arrc2) : this._appendNamed(n5, arrc2);
                    stringBuilder.append(arrc2, 0, n4);
                    ++n3;
                    arrc = arrc2;
                    continue block0;
                }
                stringBuilder.append(c);
                n3 = n4 = n3 + 1;
            } while (n4 < n2);
            break;
        } while (true);
    }

    public char[] quoteAsString(CharSequence charSequence) {
        int n;
        char[] arrc;
        TextBuffer textBuffer;
        if (charSequence instanceof String) {
            return this.quoteAsString((String)charSequence);
        }
        char[] arrc2 = new char[120];
        int[] arrn = CharTypes.get7BitOutputEscapes();
        int n2 = arrn.length;
        int n3 = charSequence.length();
        TextBuffer textBuffer2 = null;
        char[] arrc3 = null;
        int n4 = 0;
        int n5 = 0;
        block0 : do {
            arrc = arrc2;
            textBuffer = textBuffer2;
            n = n5;
            if (n4 >= n3) break;
            do {
                int n6;
                if ((n6 = charSequence.charAt(n4)) < n2 && arrn[n6] != 0) {
                    arrc = arrc3;
                    if (arrc3 == null) {
                        arrc = this._qbuf();
                    }
                    n = (n6 = arrn[n = (int)charSequence.charAt(n4)]) < 0 ? this._appendNumeric(n, arrc) : this._appendNamed(n6, arrc);
                    n6 = n5 + n;
                    if (n6 > arrc2.length) {
                        n6 = arrc2.length - n5;
                        if (n6 > 0) {
                            System.arraycopy(arrc, 0, arrc2, n5, n6);
                        }
                        textBuffer = textBuffer2;
                        if (textBuffer2 == null) {
                            textBuffer = TextBuffer.fromInitial(arrc2);
                        }
                        arrc2 = textBuffer.finishCurrentSegment();
                        n5 = n - n6;
                        System.arraycopy(arrc, n6, arrc2, 0, n5);
                        textBuffer2 = textBuffer;
                    } else {
                        System.arraycopy(arrc, 0, arrc2, n5, n);
                        n5 = n6;
                    }
                    ++n4;
                    arrc3 = arrc;
                    continue block0;
                }
                arrc = arrc2;
                textBuffer = textBuffer2;
                n = n5;
                if (n5 >= arrc2.length) {
                    textBuffer = textBuffer2;
                    if (textBuffer2 == null) {
                        textBuffer = TextBuffer.fromInitial(arrc2);
                    }
                    arrc = textBuffer.finishCurrentSegment();
                    n = 0;
                }
                n5 = n + 1;
                arrc[n] = (char)n6;
                if (++n4 >= n3) {
                    n = n5;
                    break block0;
                }
                arrc2 = arrc;
                textBuffer2 = textBuffer;
            } while (true);
            break;
        } while (true);
        if (textBuffer == null) {
            return Arrays.copyOfRange(arrc, 0, n);
        }
        textBuffer.setCurrentLength(n);
        return textBuffer.contentsAsArray();
    }

    public char[] quoteAsString(String string2) {
        int n;
        char[] arrc;
        TextBuffer textBuffer;
        char[] arrc2 = new char[120];
        int[] arrn = CharTypes.get7BitOutputEscapes();
        int n2 = arrn.length;
        int n3 = string2.length();
        TextBuffer textBuffer2 = null;
        char[] arrc3 = null;
        int n4 = 0;
        int n5 = 0;
        block0 : do {
            arrc = arrc2;
            textBuffer = textBuffer2;
            n = n5;
            if (n4 >= n3) break;
            do {
                int n6;
                if ((n6 = string2.charAt(n4)) < n2 && arrn[n6] != 0) {
                    arrc = arrc3;
                    if (arrc3 == null) {
                        arrc = this._qbuf();
                    }
                    n = (n6 = arrn[n = (int)string2.charAt(n4)]) < 0 ? this._appendNumeric(n, arrc) : this._appendNamed(n6, arrc);
                    n6 = n5 + n;
                    if (n6 > arrc2.length) {
                        n6 = arrc2.length - n5;
                        if (n6 > 0) {
                            System.arraycopy(arrc, 0, arrc2, n5, n6);
                        }
                        textBuffer = textBuffer2;
                        if (textBuffer2 == null) {
                            textBuffer = TextBuffer.fromInitial(arrc2);
                        }
                        arrc2 = textBuffer.finishCurrentSegment();
                        n5 = n - n6;
                        System.arraycopy(arrc, n6, arrc2, 0, n5);
                        textBuffer2 = textBuffer;
                    } else {
                        System.arraycopy(arrc, 0, arrc2, n5, n);
                        n5 = n6;
                    }
                    ++n4;
                    arrc3 = arrc;
                    continue block0;
                }
                arrc = arrc2;
                textBuffer = textBuffer2;
                n = n5;
                if (n5 >= arrc2.length) {
                    textBuffer = textBuffer2;
                    if (textBuffer2 == null) {
                        textBuffer = TextBuffer.fromInitial(arrc2);
                    }
                    arrc = textBuffer.finishCurrentSegment();
                    n = 0;
                }
                n5 = n + 1;
                arrc[n] = (char)n6;
                if (++n4 >= n3) {
                    n = n5;
                    break block0;
                }
                arrc2 = arrc;
                textBuffer2 = textBuffer;
            } while (true);
            break;
        } while (true);
        if (textBuffer == null) {
            return Arrays.copyOfRange(arrc, 0, n);
        }
        textBuffer.setCurrentLength(n);
        return textBuffer.contentsAsArray();
    }

    /*
     * Unable to fully structure code
     */
    public byte[] quoteAsUTF8(String var1_1) {
        var2_2 = var1_1.length();
        var3_3 = new byte[200];
        var4_24 = null;
        var5_40 = 0;
        var6_41 = 0;
        block0 : do {
            block17 : {
                var7_42 = var3_4;
                var8_44 = var4_25;
                var9_49 = var6_41;
                if (var5_40 >= var2_2) break;
                var10_50 = CharTypes.get7BitOutputEscapes();
                var8_45 = var3_4;
                while ((var11_51 = var1_1.charAt(var5_40)) <= 127 && var10_50[var11_51] == 0) {
                    var7_42 = var8_43;
                    var3_6 = var4_26;
                    var9_49 = var6_41;
                    if (var6_41 >= ((void)var8_43).length) {
                        var3_7 = var4_26;
                        if (var4_26 == null) {
                            var3_8 = ByteArrayBuilder.fromInitial((byte[])var8_43, var6_41);
                        }
                        var7_42 = var3_9.finishCurrentSegment();
                        var9_49 = 0;
                    }
                    var6_41 = var9_49 + 1;
                    var7_42[var9_49] = (byte)var11_51;
                    if (++var5_40 >= var2_2) {
                        var8_46 = var3_10;
                        var9_49 = var6_41;
                        break block0;
                    }
                    var8_47 = var7_42;
                    var4_27 = var3_10;
                }
                var7_42 = var4_26;
                if (var4_26 == null) {
                    var7_42 = ByteArrayBuilder.fromInitial((byte[])var8_43, var6_41);
                }
                var3_11 = var8_43;
                var11_51 = var6_41;
                if (var6_41 >= ((void)var8_43).length) {
                    var3_12 = var7_42.finishCurrentSegment();
                    var11_51 = 0;
                }
                var9_49 = var5_40 + 1;
                var12_52 = var1_1.charAt(var5_40);
                if (var12_52 <= 127) {
                    var6_41 = this._appendByte(var12_52, var10_50[var12_52], (ByteArrayBuilder)var7_42, var11_51);
                    var3_14 = var7_42.getCurrentSegment();
                    var5_40 = var9_49;
                    var4_28 = var7_42;
                    continue;
                }
                if (var12_52 > 2047) break block17;
                var6_41 = var11_51 + 1;
                var3_13[var11_51] = (byte)(var12_52 >> 6 | 192);
                var5_40 = var12_52 & 63 | 128;
                ** GOTO lbl93
            }
            if (var12_52 >= 55296 && var12_52 <= 57343) {
                if (var12_52 > 56319) {
                    JsonStringEncoder._illegal(var12_52);
                }
                if (var9_49 >= var2_2) {
                    JsonStringEncoder._illegal(var12_52);
                }
                if ((var12_52 = JsonStringEncoder._convert(var12_52, var1_1.charAt(var9_49))) > 1114111) {
                    JsonStringEncoder._illegal(var12_52);
                }
                var5_40 = var11_51 + 1;
                var3_13[var11_51] = (byte)(var12_52 >> 18 | 240);
                var4_31 = var3_13;
                var6_41 = var5_40;
                if (var5_40 >= ((void)var3_13).length) {
                    var4_32 = var7_42.finishCurrentSegment();
                    var6_41 = 0;
                }
                var5_40 = var6_41 + 1;
                var4_33[var6_41] = (byte)(var12_52 >> 12 & 63 | 128);
                var3_16 = var4_33;
                var6_41 = var5_40;
                if (var5_40 >= ((void)var4_33).length) {
                    var3_17 = var7_42.finishCurrentSegment();
                    var6_41 = 0;
                }
                var3_18[var6_41] = (byte)(var12_52 >> 6 & 63 | 128);
                var11_51 = var12_52 & 63 | 128;
                var5_40 = var9_49 + 1;
                ++var6_41;
                var4_34 = var3_18;
                var9_49 = var11_51;
            } else {
                var5_40 = var11_51 + 1;
                var3_13[var11_51] = (byte)(var12_52 >> 12 | 224);
                var4_35 = var3_13;
                var6_41 = var5_40;
                if (var5_40 >= ((void)var3_13).length) {
                    var4_36 = var7_42.finishCurrentSegment();
                    var6_41 = 0;
                }
                var4_37[var6_41] = (byte)(var12_52 >> 6 & 63 | 128);
                var5_40 = var12_52 & 63 | 128;
                ++var6_41;
                var3_19 = var4_37;
lbl93: // 2 sources:
                var11_51 = var5_40;
                var5_40 = var9_49;
                var4_30 = var3_15;
                var9_49 = var11_51;
            }
            var3_21 = var4_38;
            var11_51 = var6_41;
            if (var6_41 >= ((void)var4_38).length) {
                var3_22 = var7_42.finishCurrentSegment();
                var11_51 = 0;
            }
            var3_23[var11_51] = (byte)var9_49;
            var6_41 = var11_51 + 1;
            var4_39 = var7_42;
        } while (true);
        if (var8_48 != null) return var8_48.completeAndCoalesce(var9_49);
        return Arrays.copyOfRange(var7_42, 0, var9_49);
    }
}

