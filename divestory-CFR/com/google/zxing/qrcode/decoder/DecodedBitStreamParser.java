/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

final class DecodedBitStreamParser {
    private static final char[] ALPHANUMERIC_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '$', '%', '*', '+', '-', '.', '/', ':'};
    private static final int GB2312_SUBSET = 1;

    private DecodedBitStreamParser() {
    }

    static DecoderResult decode(byte[] arrby, Version object, ErrorCorrectionLevel object2, Map<DecodeHintType, ?> object3) throws FormatException {
        BitSource bitSource = new BitSource(arrby);
        StringBuilder stringBuilder = new StringBuilder(50);
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>(1);
        Mode mode = null;
        boolean bl = false;
        int n = -1;
        int n2 = -1;
        try {
            Mode mode2;
            Enum enum_;
            do {
                block11 : {
                    int n3;
                    block16 : {
                        int n4;
                        block10 : {
                            block15 : {
                                block14 : {
                                    block13 : {
                                        block12 : {
                                            mode2 = bitSource.available() < 4 ? Mode.TERMINATOR : Mode.forBits(bitSource.readBits(4));
                                            n4 = n;
                                            n3 = n2;
                                            enum_ = mode;
                                            if (mode2 == Mode.TERMINATOR) break block10;
                                            if (mode2 == Mode.FNC1_FIRST_POSITION || mode2 == Mode.FNC1_SECOND_POSITION) break block11;
                                            if (mode2 != Mode.STRUCTURED_APPEND) break block12;
                                            if (bitSource.available() < 16) throw FormatException.getFormatInstance();
                                            n4 = bitSource.readBits(8);
                                            n3 = bitSource.readBits(8);
                                            enum_ = mode;
                                            break block10;
                                        }
                                        if (mode2 != Mode.ECI) break block13;
                                        enum_ = CharacterSetECI.getCharacterSetECIByValue(DecodedBitStreamParser.parseECIValue(bitSource));
                                        if (enum_ == null) throw FormatException.getFormatInstance();
                                        n4 = n;
                                        n3 = n2;
                                        break block10;
                                    }
                                    if (mode2 != Mode.HANZI) break block14;
                                    int n5 = bitSource.readBits(4);
                                    int n6 = bitSource.readBits(mode2.getCharacterCountBits((Version)object));
                                    n4 = n;
                                    n3 = n2;
                                    enum_ = mode;
                                    if (n5 == 1) {
                                        DecodedBitStreamParser.decodeHanziSegment(bitSource, stringBuilder, n6);
                                        n4 = n;
                                        n3 = n2;
                                        enum_ = mode;
                                    }
                                    break block10;
                                }
                                n3 = bitSource.readBits(mode2.getCharacterCountBits((Version)object));
                                if (mode2 != Mode.NUMERIC) break block15;
                                DecodedBitStreamParser.decodeNumericSegment(bitSource, stringBuilder, n3);
                                n4 = n;
                                n3 = n2;
                                enum_ = mode;
                                break block10;
                            }
                            if (mode2 != Mode.ALPHANUMERIC) break block16;
                            DecodedBitStreamParser.decodeAlphanumericSegment(bitSource, stringBuilder, n3, bl);
                            n4 = n;
                            n3 = n2;
                            enum_ = mode;
                        }
                        n = n4;
                        n2 = n3;
                        mode = enum_;
                        continue;
                    }
                    if (mode2 == Mode.BYTE) {
                        DecodedBitStreamParser.decodeByteSegment(bitSource, stringBuilder, n3, (CharacterSetECI)((Object)mode), arrayList, object3);
                        continue;
                    }
                    if (mode2 != Mode.KANJI) throw FormatException.getFormatInstance();
                    DecodedBitStreamParser.decodeKanjiSegment(bitSource, stringBuilder, n3);
                    continue;
                }
                bl = true;
            } while (mode2 != (enum_ = Mode.TERMINATOR));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw FormatException.getFormatInstance();
        }
        object3 = stringBuilder.toString();
        object = arrayList.isEmpty() ? null : arrayList;
        if (object2 == null) {
            object2 = null;
            return new DecoderResult(arrby, (String)object3, (List<byte[]>)object, (String)object2, n, n2);
        }
        object2 = object2.toString();
        return new DecoderResult(arrby, (String)object3, (List<byte[]>)object, (String)object2, n, n2);
    }

    private static void decodeAlphanumericSegment(BitSource bitSource, StringBuilder stringBuilder, int n, boolean bl) throws FormatException {
        int n2 = stringBuilder.length();
        while (n > 1) {
            if (bitSource.available() < 11) throw FormatException.getFormatInstance();
            int n3 = bitSource.readBits(11);
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n3 / 45));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n3 % 45));
            n -= 2;
        }
        if (n == 1) {
            if (bitSource.available() < 6) throw FormatException.getFormatInstance();
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(bitSource.readBits(6)));
        }
        if (!bl) return;
        n = n2;
        while (n < stringBuilder.length()) {
            if (stringBuilder.charAt(n) == '%') {
                if (n < stringBuilder.length() - 1 && stringBuilder.charAt(n2 = n + 1) == '%') {
                    stringBuilder.deleteCharAt(n2);
                } else {
                    stringBuilder.setCharAt(n, '\u001d');
                }
            }
            ++n;
        }
    }

    private static void decodeByteSegment(BitSource object, StringBuilder stringBuilder, int n, CharacterSetECI object2, Collection<byte[]> collection, Map<DecodeHintType, ?> map) throws FormatException {
        if (n * 8 > ((BitSource)object).available()) throw FormatException.getFormatInstance();
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)((BitSource)object).readBits(8);
        }
        object = object2 == null ? StringUtils.guessEncoding(arrby, map) : object2.name();
        try {
            object2 = new String(arrby, (String)object);
            stringBuilder.append((String)object2);
            collection.add(arrby);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeHanziSegment(BitSource object, StringBuilder stringBuilder, int n) throws FormatException {
        if (n * 13 > ((BitSource)object).available()) throw FormatException.getFormatInstance();
        byte[] arrby = new byte[n * 2];
        int n2 = 0;
        while (n > 0) {
            int n3 = ((BitSource)object).readBits(13);
            int n4 = n3 % 96 | n3 / 96 << 8;
            n3 = n4 < 959 ? 41377 : 42657;
            n3 = n4 + n3;
            arrby[n2] = (byte)(n3 >> 8 & 255);
            arrby[n2 + 1] = (byte)(n3 & 255);
            n2 += 2;
            --n;
        }
        try {
            object = new String(arrby, "GB2312");
            stringBuilder.append((String)object);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeKanjiSegment(BitSource object, StringBuilder stringBuilder, int n) throws FormatException {
        if (n * 13 > ((BitSource)object).available()) throw FormatException.getFormatInstance();
        byte[] arrby = new byte[n * 2];
        int n2 = 0;
        while (n > 0) {
            int n3 = ((BitSource)object).readBits(13);
            int n4 = n3 % 192 | n3 / 192 << 8;
            n3 = n4 < 7936 ? 33088 : 49472;
            n3 = n4 + n3;
            arrby[n2] = (byte)(n3 >> 8);
            arrby[n2 + 1] = (byte)n3;
            n2 += 2;
            --n;
        }
        try {
            object = new String(arrby, "SJIS");
            stringBuilder.append((String)object);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    private static void decodeNumericSegment(BitSource bitSource, StringBuilder stringBuilder, int n) throws FormatException {
        while (n >= 3) {
            if (bitSource.available() < 10) throw FormatException.getFormatInstance();
            int n2 = bitSource.readBits(10);
            if (n2 >= 1000) throw FormatException.getFormatInstance();
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 / 100));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 / 10 % 10));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 % 10));
            n -= 3;
        }
        if (n == 2) {
            if (bitSource.available() < 7) throw FormatException.getFormatInstance();
            n = bitSource.readBits(7);
            if (n >= 100) throw FormatException.getFormatInstance();
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n / 10));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n % 10));
            return;
        }
        if (n != 1) return;
        if (bitSource.available() < 4) throw FormatException.getFormatInstance();
        n = bitSource.readBits(4);
        if (n >= 10) throw FormatException.getFormatInstance();
        stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n));
    }

    private static int parseECIValue(BitSource bitSource) throws FormatException {
        int n = bitSource.readBits(8);
        if ((n & 128) == 0) {
            return n & 127;
        }
        if ((n & 192) == 128) {
            return bitSource.readBits(8) | (n & 63) << 8;
        }
        if ((n & 224) != 192) throw FormatException.getFormatInstance();
        return bitSource.readBits(16) | (n & 31) << 16;
    }

    private static char toAlphaNumericChar(int n) throws FormatException {
        char[] arrc = ALPHANUMERIC_CHARS;
        if (n >= arrc.length) throw FormatException.getFormatInstance();
        return arrc[n];
    }
}

