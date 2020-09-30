/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.BlockPair;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.MaskUtil;
import com.google.zxing.qrcode.encoder.MatrixUtil;
import com.google.zxing.qrcode.encoder.QRCode;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class Encoder {
    private static final int[] ALPHANUMERIC_TABLE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    private Encoder() {
    }

    static void append8BitBytes(String arrby, BitArray bitArray, String string2) throws WriterException {
        try {
            arrby = arrby.getBytes(string2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new WriterException(unsupportedEncodingException);
        }
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            bitArray.appendBits(arrby[n2], 8);
            ++n2;
        }
        return;
    }

    static void appendAlphanumericBytes(CharSequence charSequence, BitArray bitArray) throws WriterException {
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            int n3 = Encoder.getAlphanumericCode(charSequence.charAt(n2));
            if (n3 == -1) throw new WriterException();
            int n4 = n2 + 1;
            if (n4 < n) {
                if ((n4 = Encoder.getAlphanumericCode(charSequence.charAt(n4))) == -1) throw new WriterException();
                bitArray.appendBits(n3 * 45 + n4, 11);
                n2 += 2;
                continue;
            }
            bitArray.appendBits(n3, 6);
            n2 = n4;
        }
    }

    static void appendBytes(String charSequence, Mode mode, BitArray bitArray, String string2) throws WriterException {
        int n = 1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
        if (n == 1) {
            Encoder.appendNumericBytes(charSequence, bitArray);
            return;
        }
        if (n == 2) {
            Encoder.appendAlphanumericBytes(charSequence, bitArray);
            return;
        }
        if (n == 3) {
            Encoder.append8BitBytes((String)charSequence, bitArray, string2);
            return;
        }
        if (n == 4) {
            Encoder.appendKanjiBytes((String)charSequence, bitArray);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Invalid mode: ");
        ((StringBuilder)charSequence).append((Object)mode);
        throw new WriterException(((StringBuilder)charSequence).toString());
    }

    private static void appendECI(CharacterSetECI characterSetECI, BitArray bitArray) {
        bitArray.appendBits(Mode.ECI.getBits(), 4);
        bitArray.appendBits(characterSetECI.getValue(), 8);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static void appendKanjiBytes(String var0, BitArray var1_2) throws WriterException {
        try {
            var0 = var0.getBytes("Shift_JIS");
        }
        catch (UnsupportedEncodingException var0_1) {
            throw new WriterException(var0_1);
        }
        var2_3 = var0.length;
        var3_4 = 0;
        while (var3_4 < var2_3) {
            var4_5 = (var0[var3_4] & 255) << 8 | var0[var3_4 + 1] & 255;
            var5_6 = 33088;
            if (var4_5 >= 33088 && var4_5 <= 40956) ** GOTO lbl14
            if (var4_5 >= 57408 && var4_5 <= 60351) {
                var5_6 = 49472;
lbl14: // 2 sources:
                var5_6 = var4_5 - var5_6;
            } else {
                var5_6 = -1;
            }
            if (var5_6 == -1) throw new WriterException("Invalid byte sequence");
            var1_2.appendBits((var5_6 >> 8) * 192 + (var5_6 & 255), 13);
            var3_4 += 2;
        }
    }

    static void appendLengthInfo(int n, Version object, Mode mode, BitArray bitArray) throws WriterException {
        int n2 = mode.getCharacterCountBits((Version)object);
        int n3 = 1 << n2;
        if (n < n3) {
            bitArray.appendBits(n, n2);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is bigger than ");
        ((StringBuilder)object).append(n3 - 1);
        throw new WriterException(((StringBuilder)object).toString());
    }

    static void appendModeInfo(Mode mode, BitArray bitArray) {
        bitArray.appendBits(mode.getBits(), 4);
    }

    static void appendNumericBytes(CharSequence charSequence, BitArray bitArray) {
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            int n3 = charSequence.charAt(n2) - 48;
            int n4 = n2 + 2;
            if (n4 < n) {
                bitArray.appendBits(n3 * 100 + (charSequence.charAt(n2 + 1) - 48) * 10 + (charSequence.charAt(n4) - 48), 10);
                n2 += 3;
                continue;
            }
            if (++n2 < n) {
                bitArray.appendBits(n3 * 10 + (charSequence.charAt(n2) - 48), 7);
                n2 = n4;
                continue;
            }
            bitArray.appendBits(n3, 4);
        }
    }

    private static int calculateMaskPenalty(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1(byteMatrix) + MaskUtil.applyMaskPenaltyRule2(byteMatrix) + MaskUtil.applyMaskPenaltyRule3(byteMatrix) + MaskUtil.applyMaskPenaltyRule4(byteMatrix);
    }

    private static int chooseMaskPattern(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, ByteMatrix byteMatrix) throws WriterException {
        int n = Integer.MAX_VALUE;
        int n2 = -1;
        int n3 = 0;
        while (n3 < 8) {
            MatrixUtil.buildMatrix(bitArray, errorCorrectionLevel, version, n3, byteMatrix);
            int n4 = Encoder.calculateMaskPenalty(byteMatrix);
            int n5 = n;
            if (n4 < n) {
                n2 = n3;
                n5 = n4;
            }
            ++n3;
            n = n5;
        }
        return n2;
    }

    public static Mode chooseMode(String string2) {
        return Encoder.chooseMode(string2, null);
    }

    private static Mode chooseMode(String object, String string2) {
        if ("Shift_JIS".equals(string2)) {
            if (!Encoder.isOnlyDoubleByteKanji((String)object)) return Mode.BYTE;
            return Mode.KANJI;
        }
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; i < ((String)object).length(); ++i) {
            char c = ((String)object).charAt(i);
            if (c >= '0' && c <= '9') {
                bl2 = true;
                continue;
            }
            if (Encoder.getAlphanumericCode(c) == -1) return Mode.BYTE;
            bl = true;
        }
        if (bl) {
            return Mode.ALPHANUMERIC;
        }
        if (!bl2) return Mode.BYTE;
        return Mode.NUMERIC;
    }

    private static Version chooseVersion(int n, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        int n2 = 1;
        while (n2 <= 40) {
            Version version = Version.getVersionForNumber(n2);
            if (version.getTotalCodewords() - version.getECBlocksForLevel(errorCorrectionLevel).getTotalECCodewords() >= (n + 7) / 8) {
                return version;
            }
            ++n2;
        }
        throw new WriterException("Data too big");
    }

    public static QRCode encode(String string2, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        return Encoder.encode(string2, errorCorrectionLevel, null);
    }

    public static QRCode encode(String object, ErrorCorrectionLevel errorCorrectionLevel, Map<EncodeHintType, ?> object2) throws WriterException {
        Object object3;
        object2 = object2 == null ? null : (String)object2.get((Object)EncodeHintType.CHARACTER_SET);
        Object object4 = object2;
        if (object2 == null) {
            object4 = DEFAULT_BYTE_MODE_ENCODING;
        }
        object2 = Encoder.chooseMode((String)object, (String)object4);
        BitArray bitArray = new BitArray();
        if (object2 == Mode.BYTE && !DEFAULT_BYTE_MODE_ENCODING.equals(object4) && (object3 = CharacterSetECI.getCharacterSetECIByName((String)object4)) != null) {
            Encoder.appendECI((CharacterSetECI)((Object)object3), bitArray);
        }
        Encoder.appendModeInfo((Mode)((Object)object2), bitArray);
        object3 = new BitArray();
        Encoder.appendBytes((String)object, (Mode)((Object)object2), (BitArray)object3, (String)object4);
        object4 = Encoder.chooseVersion(bitArray.getSize() + ((Mode)((Object)object2)).getCharacterCountBits(Version.getVersionForNumber(1)) + ((BitArray)object3).getSize(), errorCorrectionLevel);
        object4 = Encoder.chooseVersion(bitArray.getSize() + ((Mode)((Object)object2)).getCharacterCountBits((Version)object4) + ((BitArray)object3).getSize(), errorCorrectionLevel);
        BitArray bitArray2 = new BitArray();
        bitArray2.appendBitArray(bitArray);
        int n = object2 == Mode.BYTE ? ((BitArray)object3).getSizeInBytes() : ((String)object).length();
        Encoder.appendLengthInfo(n, (Version)object4, (Mode)((Object)object2), bitArray2);
        bitArray2.appendBitArray((BitArray)object3);
        object = ((Version)object4).getECBlocksForLevel(errorCorrectionLevel);
        n = ((Version)object4).getTotalCodewords() - ((Version.ECBlocks)object).getTotalECCodewords();
        Encoder.terminateBits(n, bitArray2);
        bitArray = Encoder.interleaveWithECBytes(bitArray2, ((Version)object4).getTotalCodewords(), n, ((Version.ECBlocks)object).getNumBlocks());
        object = new QRCode();
        ((QRCode)object).setECLevel(errorCorrectionLevel);
        ((QRCode)object).setMode((Mode)((Object)object2));
        ((QRCode)object).setVersion((Version)object4);
        n = ((Version)object4).getDimensionForVersion();
        object2 = new ByteMatrix(n, n);
        n = Encoder.chooseMaskPattern(bitArray, errorCorrectionLevel, (Version)object4, (ByteMatrix)object2);
        ((QRCode)object).setMaskPattern(n);
        MatrixUtil.buildMatrix(bitArray, errorCorrectionLevel, (Version)object4, n, (ByteMatrix)object2);
        ((QRCode)object).setMatrix((ByteMatrix)object2);
        return object;
    }

    static byte[] generateECBytes(byte[] arrby, int n) {
        int n2;
        int n3 = arrby.length;
        int[] arrn = new int[n3 + n];
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            arrn[n2] = arrby[n2] & 255;
        }
        new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(arrn, n);
        arrby = new byte[n];
        n2 = n4;
        while (n2 < n) {
            arrby[n2] = (byte)arrn[n3 + n2];
            ++n2;
        }
        return arrby;
    }

    static int getAlphanumericCode(int n) {
        int[] arrn = ALPHANUMERIC_TABLE;
        if (n >= arrn.length) return -1;
        return arrn[n];
    }

    static void getNumDataBytesAndNumECBytesForBlockID(int n, int n2, int n3, int n4, int[] arrn, int[] arrn2) throws WriterException {
        if (n4 >= n3) throw new WriterException("Block ID too large");
        int n5 = n % n3;
        int n6 = n3 - n5;
        int n7 = n / n3;
        int n8 = (n2 /= n3) + 1;
        int n9 = n7 - n2;
        if (n9 != (n7 = n7 + 1 - n8)) throw new WriterException("EC bytes mismatch");
        if (n3 != n6 + n5) throw new WriterException("RS blocks mismatch");
        if (n != (n2 + n9) * n6 + (n8 + n7) * n5) throw new WriterException("Total bytes mismatch");
        if (n4 < n6) {
            arrn[0] = n2;
            arrn2[0] = n9;
            return;
        }
        arrn[0] = n8;
        arrn2[0] = n7;
    }

    static BitArray interleaveWithECBytes(BitArray bitArray, int n, int n2, int n3) throws WriterException {
        Object object;
        byte[] arrby;
        if (bitArray.getSizeInBytes() != n2) throw new WriterException("Number of bits and data bytes does not match");
        Serializable serializable = new ArrayList(n3);
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        for (int i = 0; i < n3; n5 += object[0], ++i) {
            object = new int[1];
            int[] arrn = new int[1];
            Encoder.getNumDataBytesAndNumECBytesForBlockID(n, n2, n3, i, (int[])object, arrn);
            int n8 = object[0];
            arrby = new byte[n8];
            bitArray.toBytes(n5 * 8, arrby, 0, n8);
            arrn = Encoder.generateECBytes(arrby, arrn[0]);
            serializable.add(new BlockPair(arrby, (byte[])arrn));
            n6 = Math.max(n6, n8);
            n7 = Math.max(n7, arrn.length);
        }
        if (n2 != n5) throw new WriterException("Data bytes does not match offset");
        bitArray = new BitArray();
        n3 = 0;
        do {
            if (n3 >= n6) break;
            object = serializable.iterator();
            while (object.hasNext()) {
                arrby = ((BlockPair)object.next()).getDataBytes();
                if (n3 >= arrby.length) continue;
                bitArray.appendBits(arrby[n3], 8);
            }
            ++n3;
        } while (true);
        for (n2 = n4; n2 < n7; ++n2) {
            object = serializable.iterator();
            while (object.hasNext()) {
                arrby = ((BlockPair)object.next()).getErrorCorrectionBytes();
                if (n2 >= arrby.length) continue;
                bitArray.appendBits(arrby[n2], 8);
            }
        }
        if (n == bitArray.getSizeInBytes()) {
            return bitArray;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Interleaving error: ");
        ((StringBuilder)serializable).append(n);
        ((StringBuilder)serializable).append(" and ");
        ((StringBuilder)serializable).append(bitArray.getSizeInBytes());
        ((StringBuilder)serializable).append(" differ.");
        throw new WriterException(((StringBuilder)serializable).toString());
    }

    private static boolean isOnlyDoubleByteKanji(String arrby) {
        try {
            arrby = arrby.getBytes("Shift_JIS");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return false;
        }
        int n = arrby.length;
        if (n % 2 != 0) {
            return false;
        }
        int n2 = 0;
        while (n2 < n) {
            int n3 = arrby[n2] & 255;
            if (n3 < 129 || n3 > 159) {
                if (n3 < 224) return false;
                if (n3 > 235) {
                    return false;
                }
            }
            n2 += 2;
        }
        return true;
    }

    static void terminateBits(int n, BitArray bitArray) throws WriterException {
        int n2;
        int n3 = n * 8;
        if (bitArray.getSize() > n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("data bits cannot fit in the QR Code");
            stringBuilder.append(bitArray.getSize());
            stringBuilder.append(" > ");
            stringBuilder.append(n3);
            throw new WriterException(stringBuilder.toString());
        }
        int n4 = 0;
        for (n2 = 0; n2 < 4 && bitArray.getSize() < n3; ++n2) {
            bitArray.appendBit(false);
        }
        n2 = bitArray.getSize() & 7;
        if (n2 > 0) {
            while (n2 < 8) {
                bitArray.appendBit(false);
                ++n2;
            }
        }
        int n5 = bitArray.getSizeInBytes();
        n2 = n4;
        do {
            if (n2 >= n - n5) {
                if (bitArray.getSize() != n3) throw new WriterException("Bits size does not equal capacity");
                return;
            }
            n4 = (n2 & 1) == 0 ? 236 : 17;
            bitArray.appendBits(n4, 8);
            ++n2;
        } while (true);
    }

}

