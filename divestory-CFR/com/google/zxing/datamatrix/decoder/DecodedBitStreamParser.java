/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class DecodedBitStreamParser {
    private static final char[] C40_BASIC_SET_CHARS;
    private static final char[] C40_SHIFT2_SET_CHARS;
    private static final char[] TEXT_BASIC_SET_CHARS;
    private static final char[] TEXT_SHIFT2_SET_CHARS;
    private static final char[] TEXT_SHIFT3_SET_CHARS;

    static {
        char[] arrc;
        C40_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] arrc2 = arrc = new char[27];
        arrc2[0] = 33;
        arrc2[1] = 34;
        arrc2[2] = 35;
        arrc2[3] = 36;
        arrc2[4] = 37;
        arrc2[5] = 38;
        arrc2[6] = 39;
        arrc2[7] = 40;
        arrc2[8] = 41;
        arrc2[9] = 42;
        arrc2[10] = 43;
        arrc2[11] = 44;
        arrc2[12] = 45;
        arrc2[13] = 46;
        arrc2[14] = 47;
        arrc2[15] = 58;
        arrc2[16] = 59;
        arrc2[17] = 60;
        arrc2[18] = 61;
        arrc2[19] = 62;
        arrc2[20] = 63;
        arrc2[21] = 64;
        arrc2[22] = 91;
        arrc2[23] = 92;
        arrc2[24] = 93;
        arrc2[25] = 94;
        arrc2[26] = 95;
        C40_SHIFT2_SET_CHARS = arrc;
        TEXT_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        TEXT_SHIFT2_SET_CHARS = arrc;
        TEXT_SHIFT3_SET_CHARS = new char[]{'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', ''};
    }

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] arrby) throws FormatException {
        BitSource bitSource = new BitSource(arrby);
        CharSequence charSequence = new StringBuilder(100);
        StringBuilder stringBuilder = new StringBuilder(0);
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>(1);
        Object object = Mode.ASCII_ENCODE;
        do {
            if (object == Mode.ASCII_ENCODE) {
                object = DecodedBitStreamParser.decodeAsciiSegment(bitSource, charSequence, stringBuilder);
                continue;
            }
            int n = 1.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[object.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) throw FormatException.getFormatInstance();
                            DecodedBitStreamParser.decodeBase256Segment(bitSource, charSequence, arrayList);
                        } else {
                            DecodedBitStreamParser.decodeEdifactSegment(bitSource, charSequence);
                        }
                    } else {
                        DecodedBitStreamParser.decodeAnsiX12Segment(bitSource, charSequence);
                    }
                } else {
                    DecodedBitStreamParser.decodeTextSegment(bitSource, charSequence);
                }
            } else {
                DecodedBitStreamParser.decodeC40Segment(bitSource, charSequence);
            }
            object = Mode.ASCII_ENCODE;
        } while (object != Mode.PAD_ENCODE && bitSource.available() > 0);
        if (stringBuilder.length() > 0) {
            charSequence.append(stringBuilder);
        }
        charSequence = charSequence.toString();
        object = arrayList;
        if (!arrayList.isEmpty()) return new DecoderResult(arrby, (String)charSequence, (List<byte[]>)object, null);
        object = null;
        return new DecoderResult(arrby, (String)charSequence, (List<byte[]>)object, null);
    }

    private static void decodeAnsiX12Segment(BitSource bitSource, StringBuilder stringBuilder) throws FormatException {
        int[] arrn = new int[3];
        do {
            if (bitSource.available() == 8) {
                return;
            }
            int n = bitSource.readBits(8);
            if (n == 254) {
                return;
            }
            DecodedBitStreamParser.parseTwoBytes(n, bitSource.readBits(8), arrn);
            for (n = 0; n < 3; ++n) {
                int n2 = arrn[n];
                if (n2 == 0) {
                    stringBuilder.append('\r');
                    continue;
                }
                if (n2 == 1) {
                    stringBuilder.append('*');
                    continue;
                }
                if (n2 == 2) {
                    stringBuilder.append('>');
                    continue;
                }
                if (n2 == 3) {
                    stringBuilder.append(' ');
                    continue;
                }
                if (n2 < 14) {
                    stringBuilder.append((char)(n2 + 44));
                    continue;
                }
                if (n2 >= 40) throw FormatException.getFormatInstance();
                stringBuilder.append((char)(n2 + 51));
            }
        } while (bitSource.available() > 0);
    }

    private static Mode decodeAsciiSegment(BitSource bitSource, StringBuilder stringBuilder, StringBuilder stringBuilder2) throws FormatException {
        int n = 0;
        do {
            int n2;
            int n3;
            if ((n2 = bitSource.readBits(8)) == 0) throw FormatException.getFormatInstance();
            if (n2 <= 128) {
                n3 = n2;
                if (n != 0) {
                    n3 = n2 + 128;
                }
                stringBuilder.append((char)(n3 - 1));
                return Mode.ASCII_ENCODE;
            }
            if (n2 == 129) {
                return Mode.PAD_ENCODE;
            }
            if (n2 <= 229) {
                n3 = n2 - 130;
                if (n3 < 10) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(n3);
                n3 = n;
            } else {
                if (n2 == 230) {
                    return Mode.C40_ENCODE;
                }
                if (n2 == 231) {
                    return Mode.BASE256_ENCODE;
                }
                if (n2 == 232) {
                    stringBuilder.append('\u001d');
                    n3 = n;
                } else {
                    n3 = n;
                    if (n2 != 233) {
                        if (n2 == 234) {
                            n3 = n;
                        } else if (n2 == 235) {
                            n3 = 1;
                        } else if (n2 == 236) {
                            stringBuilder.append("[)>\u001e05\u001d");
                            stringBuilder2.insert(0, "\u001e\u0004");
                            n3 = n;
                        } else if (n2 == 237) {
                            stringBuilder.append("[)>\u001e06\u001d");
                            stringBuilder2.insert(0, "\u001e\u0004");
                            n3 = n;
                        } else {
                            if (n2 == 238) {
                                return Mode.ANSIX12_ENCODE;
                            }
                            if (n2 == 239) {
                                return Mode.TEXT_ENCODE;
                            }
                            if (n2 == 240) {
                                return Mode.EDIFACT_ENCODE;
                            }
                            if (n2 == 241) {
                                n3 = n;
                            } else {
                                n3 = n;
                                if (n2 >= 242) {
                                    if (n2 != 254) throw FormatException.getFormatInstance();
                                    if (bitSource.available() != 0) throw FormatException.getFormatInstance();
                                    n3 = n;
                                }
                            }
                        }
                    }
                }
            }
            n = n3;
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    private static void decodeBase256Segment(BitSource object, StringBuilder stringBuilder, Collection<byte[]> collection) throws FormatException {
        int n = ((BitSource)object).getByteOffset() + 1;
        int n2 = ((BitSource)object).readBits(8);
        int n3 = n + 1;
        if ((n = DecodedBitStreamParser.unrandomize255State(n2, n)) == 0) {
            n = ((BitSource)object).available() / 8;
        } else if (n >= 250) {
            n = (n - 249) * 250 + DecodedBitStreamParser.unrandomize255State(((BitSource)object).readBits(8), n3);
            ++n3;
        }
        if (n < 0) throw FormatException.getFormatInstance();
        byte[] arrby = new byte[n];
        for (n2 = 0; n2 < n; ++n2, ++n3) {
            if (((BitSource)object).available() < 8) throw FormatException.getFormatInstance();
            arrby[n2] = (byte)DecodedBitStreamParser.unrandomize255State(((BitSource)object).readBits(8), n3);
        }
        collection.add(arrby);
        try {
            object = new String(arrby, "ISO8859_1");
            stringBuilder.append((String)object);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Platform does not support required encoding: ");
            stringBuilder.append(unsupportedEncodingException);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    /*
     * Unable to fully structure code
     */
    private static void decodeC40Segment(BitSource var0, StringBuilder var1_1) throws FormatException {
        var2_2 = new int[3];
        var3_3 = false;
        var4_4 = 0;
        do {
            if (var0.available() == 8) {
                return;
            }
            var5_5 = var0.readBits(8);
            if (var5_5 == 254) {
                return;
            }
            DecodedBitStreamParser.parseTwoBytes(var5_5, var0.readBits(8), var2_2);
            for (var5_5 = 0; var5_5 < 3; ++var5_5) {
                block10 : {
                    block14 : {
                        block11 : {
                            block15 : {
                                block16 : {
                                    block12 : {
                                        block13 : {
                                            var6_6 = var2_2[var5_5];
                                            if (var4_4 == 0) break block10;
                                            if (var4_4 == true) break block11;
                                            if (var4_4 == 2) break block12;
                                            if (var4_4 != 3) throw FormatException.getFormatInstance();
                                            if (!var3_3) break block13;
                                            var1_1.append((char)(var6_6 + 224));
                                            ** GOTO lbl49
                                        }
                                        var1_1.append((char)(var6_6 + 96));
                                        break block14;
                                    }
                                    var7_7 = DecodedBitStreamParser.C40_SHIFT2_SET_CHARS;
                                    if (var6_6 >= var7_7.length) break block15;
                                    var8_8 = var7_7[var6_6];
                                    if (!var3_3) break block16;
                                    var1_1.append((char)(var8_8 + 128));
                                    ** GOTO lbl49
                                }
                                var1_1.append(var8_8);
                                break block14;
                            }
                            if (var6_6 == 27) {
                                var1_1.append('\u001d');
                            } else {
                                if (var6_6 != 30) throw FormatException.getFormatInstance();
                                var3_3 = true;
                            }
                            break block14;
                        }
                        if (var3_3) {
                            var1_1.append((char)(var6_6 + 128));
lbl49: // 3 sources:
                            var3_3 = false;
                        } else {
                            var1_1.append((char)var6_6);
                        }
                    }
                    var4_4 = 0;
                    continue;
                }
                if (var6_6 < 3) {
                    var4_4 = var6_6 + 1;
                    continue;
                }
                var7_7 = DecodedBitStreamParser.C40_BASIC_SET_CHARS;
                if (var6_6 >= var7_7.length) throw FormatException.getFormatInstance();
                var8_8 = var7_7[var6_6];
                if (var3_3) {
                    var1_1.append((char)(var8_8 + 128));
                    var3_3 = false;
                    continue;
                }
                var1_1.append(var8_8);
            }
        } while (var0.available() > 0);
    }

    private static void decodeEdifactSegment(BitSource bitSource, StringBuilder stringBuilder) {
        do {
            if (bitSource.available() <= 16) {
                return;
            }
            for (int i = 0; i < 4; ++i) {
                int n = bitSource.readBits(6);
                if (n == 31) {
                    i = 8 - bitSource.getBitOffset();
                    if (i == 8) return;
                    bitSource.readBits(i);
                    return;
                }
                int n2 = n;
                if ((n & 32) == 0) {
                    n2 = n | 64;
                }
                stringBuilder.append((char)n2);
            }
        } while (bitSource.available() > 0);
    }

    /*
     * Unable to fully structure code
     */
    private static void decodeTextSegment(BitSource var0, StringBuilder var1_1) throws FormatException {
        var2_2 = new int[3];
        var3_3 = false;
        var4_4 = 0;
        do {
            if (var0.available() == 8) {
                return;
            }
            var5_5 = var0.readBits(8);
            if (var5_5 == 254) {
                return;
            }
            DecodedBitStreamParser.parseTwoBytes(var5_5, var0.readBits(8), var2_2);
            for (var5_5 = 0; var5_5 < 3; ++var5_5) {
                block10 : {
                    block14 : {
                        block11 : {
                            block15 : {
                                block16 : {
                                    block12 : {
                                        block13 : {
                                            var6_6 = var2_2[var5_5];
                                            if (var4_4 == 0) break block10;
                                            if (var4_4 == true) break block11;
                                            if (var4_4 == 2) break block12;
                                            if (var4_4 != 3) throw FormatException.getFormatInstance();
                                            var7_7 = DecodedBitStreamParser.TEXT_SHIFT3_SET_CHARS;
                                            if (var6_6 >= var7_7.length) throw FormatException.getFormatInstance();
                                            var8_8 = var7_7[var6_6];
                                            if (!var3_3) break block13;
                                            var1_1.append((char)(var8_8 + 128));
                                            ** GOTO lbl52
                                        }
                                        var1_1.append(var8_8);
                                        break block14;
                                    }
                                    var7_7 = DecodedBitStreamParser.TEXT_SHIFT2_SET_CHARS;
                                    if (var6_6 >= var7_7.length) break block15;
                                    var8_8 = var7_7[var6_6];
                                    if (!var3_3) break block16;
                                    var1_1.append((char)(var8_8 + 128));
                                    ** GOTO lbl52
                                }
                                var1_1.append(var8_8);
                                break block14;
                            }
                            if (var6_6 == 27) {
                                var1_1.append('\u001d');
                            } else {
                                if (var6_6 != 30) throw FormatException.getFormatInstance();
                                var3_3 = true;
                            }
                            break block14;
                        }
                        if (var3_3) {
                            var1_1.append((char)(var6_6 + 128));
lbl52: // 3 sources:
                            var3_3 = false;
                        } else {
                            var1_1.append((char)var6_6);
                        }
                    }
                    var4_4 = 0;
                    continue;
                }
                if (var6_6 < 3) {
                    var4_4 = var6_6 + 1;
                    continue;
                }
                var7_7 = DecodedBitStreamParser.TEXT_BASIC_SET_CHARS;
                if (var6_6 >= var7_7.length) throw FormatException.getFormatInstance();
                var8_8 = var7_7[var6_6];
                if (var3_3) {
                    var1_1.append((char)(var8_8 + 128));
                    var3_3 = false;
                    continue;
                }
                var1_1.append(var8_8);
            }
        } while (var0.available() > 0);
    }

    private static void parseTwoBytes(int n, int n2, int[] arrn) {
        n = (n << 8) + n2 - 1;
        arrn[0] = n2 = n / 1600;
        n2 = n - n2 * 1600;
        arrn[1] = n = n2 / 40;
        arrn[2] = n2 - n * 40;
    }

    private static int unrandomize255State(int n, int n2) {
        if ((n -= n2 * 149 % 255 + 1) >= 0) {
            return n;
        }
        n += 256;
        return n;
    }

    private static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* enum */ Mode ANSIX12_ENCODE;
        public static final /* enum */ Mode ASCII_ENCODE;
        public static final /* enum */ Mode BASE256_ENCODE;
        public static final /* enum */ Mode C40_ENCODE;
        public static final /* enum */ Mode EDIFACT_ENCODE;
        public static final /* enum */ Mode PAD_ENCODE;
        public static final /* enum */ Mode TEXT_ENCODE;

        static {
            Mode mode;
            PAD_ENCODE = new Mode();
            ASCII_ENCODE = new Mode();
            C40_ENCODE = new Mode();
            TEXT_ENCODE = new Mode();
            ANSIX12_ENCODE = new Mode();
            EDIFACT_ENCODE = new Mode();
            BASE256_ENCODE = mode = new Mode();
            $VALUES = new Mode[]{PAD_ENCODE, ASCII_ENCODE, C40_ENCODE, TEXT_ENCODE, ANSIX12_ENCODE, EDIFACT_ENCODE, mode};
        }

        public static Mode valueOf(String string2) {
            return Enum.valueOf(Mode.class, string2);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

}

