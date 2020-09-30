/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.MaskUtil;
import com.google.zxing.qrcode.encoder.QRCode;

final class MatrixUtil {
    private static final int[][] POSITION_ADJUSTMENT_PATTERN;
    private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
    private static final int[][] POSITION_DETECTION_PATTERN;
    private static final int[][] TYPE_INFO_COORDINATES;
    private static final int TYPE_INFO_MASK_PATTERN = 21522;
    private static final int TYPE_INFO_POLY = 1335;
    private static final int VERSION_INFO_POLY = 7973;

    static {
        int[] arrn = new int[]{1, 0, 0, 0, 0, 0, 1};
        POSITION_DETECTION_PATTERN = new int[][]{{1, 1, 1, 1, 1, 1, 1}, arrn, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 1, 1, 1, 1, 1, 1}};
        POSITION_ADJUSTMENT_PATTERN = new int[][]{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}};
        arrn = new int[]{6, 32, 58, -1, -1, -1, -1};
        POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = new int[][]{{-1, -1, -1, -1, -1, -1, -1}, {6, 18, -1, -1, -1, -1, -1}, {6, 22, -1, -1, -1, -1, -1}, {6, 26, -1, -1, -1, -1, -1}, {6, 30, -1, -1, -1, -1, -1}, {6, 34, -1, -1, -1, -1, -1}, {6, 22, 38, -1, -1, -1, -1}, {6, 24, 42, -1, -1, -1, -1}, {6, 26, 46, -1, -1, -1, -1}, {6, 28, 50, -1, -1, -1, -1}, {6, 30, 54, -1, -1, -1, -1}, arrn, {6, 34, 62, -1, -1, -1, -1}, {6, 26, 46, 66, -1, -1, -1}, {6, 26, 48, 70, -1, -1, -1}, {6, 26, 50, 74, -1, -1, -1}, {6, 30, 54, 78, -1, -1, -1}, {6, 30, 56, 82, -1, -1, -1}, {6, 30, 58, 86, -1, -1, -1}, {6, 34, 62, 90, -1, -1, -1}, {6, 28, 50, 72, 94, -1, -1}, {6, 26, 50, 74, 98, -1, -1}, {6, 30, 54, 78, 102, -1, -1}, {6, 28, 54, 80, 106, -1, -1}, {6, 32, 58, 84, 110, -1, -1}, {6, 30, 58, 86, 114, -1, -1}, {6, 34, 62, 90, 118, -1, -1}, {6, 26, 50, 74, 98, 122, -1}, {6, 30, 54, 78, 102, 126, -1}, {6, 26, 52, 78, 104, 130, -1}, {6, 30, 56, 82, 108, 134, -1}, {6, 34, 60, 86, 112, 138, -1}, {6, 30, 58, 86, 114, 142, -1}, {6, 34, 62, 90, 118, 146, -1}, {6, 30, 54, 78, 102, 126, 150}, {6, 24, 50, 76, 102, 128, 154}, {6, 28, 54, 80, 106, 132, 158}, {6, 32, 58, 84, 110, 136, 162}, {6, 26, 54, 82, 110, 138, 166}, {6, 30, 58, 86, 114, 142, 170}};
        arrn = new int[]{8, 1};
        int[] arrn2 = new int[]{8, 2};
        int[] arrn3 = new int[]{8, 3};
        int[] arrn4 = new int[]{8, 7};
        int[] arrn5 = new int[]{7, 8};
        int[] arrn6 = new int[]{5, 8};
        int[] arrn7 = new int[]{4, 8};
        TYPE_INFO_COORDINATES = new int[][]{{8, 0}, arrn, arrn2, arrn3, {8, 4}, {8, 5}, arrn4, {8, 8}, arrn5, arrn6, arrn7, {3, 8}, {2, 8}, {1, 8}, {0, 8}};
    }

    private MatrixUtil() {
    }

    static void buildMatrix(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, int n, ByteMatrix byteMatrix) throws WriterException {
        MatrixUtil.clearMatrix(byteMatrix);
        MatrixUtil.embedBasicPatterns(version, byteMatrix);
        MatrixUtil.embedTypeInfo(errorCorrectionLevel, n, byteMatrix);
        MatrixUtil.maybeEmbedVersionInfo(version, byteMatrix);
        MatrixUtil.embedDataBits(bitArray, n, byteMatrix);
    }

    static int calculateBCHCode(int n, int n2) {
        if (n2 == 0) throw new IllegalArgumentException("0 polynomial");
        int n3 = MatrixUtil.findMSBSet(n2);
        n <<= n3 - 1;
        while (MatrixUtil.findMSBSet(n) >= n3) {
            n ^= n2 << MatrixUtil.findMSBSet(n) - n3;
        }
        return n;
    }

    static void clearMatrix(ByteMatrix byteMatrix) {
        byteMatrix.clear((byte)-1);
    }

    static void embedBasicPatterns(Version version, ByteMatrix byteMatrix) throws WriterException {
        MatrixUtil.embedPositionDetectionPatternsAndSeparators(byteMatrix);
        MatrixUtil.embedDarkDotAtLeftBottomCorner(byteMatrix);
        MatrixUtil.maybeEmbedPositionAdjustmentPatterns(version, byteMatrix);
        MatrixUtil.embedTimingPatterns(byteMatrix);
    }

    private static void embedDarkDotAtLeftBottomCorner(ByteMatrix byteMatrix) throws WriterException {
        if (byteMatrix.get(8, byteMatrix.getHeight() - 8) == 0) throw new WriterException();
        byteMatrix.set(8, byteMatrix.getHeight() - 8, 1);
    }

    static void embedDataBits(BitArray bitArray, int n, ByteMatrix object) throws WriterException {
        int n2 = ((ByteMatrix)object).getWidth() - 1;
        int n3 = ((ByteMatrix)object).getHeight() - 1;
        int n4 = 0;
        int n5 = -1;
        block0 : do {
            int n6;
            int n7;
            int n8;
            if (n2 > 0) {
                n6 = n2;
                n8 = n3;
                n7 = n4;
                if (n2 == 6) {
                    n6 = n2 - 1;
                    n7 = n4;
                    n8 = n3;
                }
            } else {
                if (n4 == bitArray.getSize()) {
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Not all bits consumed: ");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append('/');
                ((StringBuilder)object).append(bitArray.getSize());
                throw new WriterException(((StringBuilder)object).toString());
            }
            do {
                if (n8 >= 0 && n8 < ((ByteMatrix)object).getHeight()) {
                    n3 = n7;
                } else {
                    n5 = -n5;
                    n3 = n8 + n5;
                    n2 = n6 - 2;
                    n4 = n7;
                    continue block0;
                }
                for (n2 = 0; n2 < 2; ++n2) {
                    boolean bl;
                    n7 = n6 - n2;
                    if (!MatrixUtil.isEmpty(((ByteMatrix)object).get(n7, n8))) continue;
                    if (n3 < bitArray.getSize()) {
                        bl = bitArray.get(n3);
                        ++n3;
                    } else {
                        bl = false;
                    }
                    boolean bl2 = bl;
                    if (n != -1) {
                        bl2 = bl;
                        if (MaskUtil.getDataMaskBit(n, n7, n8)) {
                            bl2 = bl ^ true;
                        }
                    }
                    ((ByteMatrix)object).set(n7, n8, bl2);
                }
                n8 += n5;
                n7 = n3;
            } while (true);
            break;
        } while (true);
    }

    private static void embedHorizontalSeparationPattern(int n, int n2, ByteMatrix byteMatrix) throws WriterException {
        int n3 = 0;
        while (n3 < 8) {
            int n4 = n + n3;
            if (!MatrixUtil.isEmpty(byteMatrix.get(n4, n2))) throw new WriterException();
            byteMatrix.set(n4, n2, 0);
            ++n3;
        }
    }

    private static void embedPositionAdjustmentPattern(int n, int n2, ByteMatrix byteMatrix) {
        int n3 = 0;
        while (n3 < 5) {
            for (int i = 0; i < 5; ++i) {
                byteMatrix.set(n + i, n2 + n3, POSITION_ADJUSTMENT_PATTERN[n3][i]);
            }
            ++n3;
        }
    }

    private static void embedPositionDetectionPattern(int n, int n2, ByteMatrix byteMatrix) {
        int n3 = 0;
        while (n3 < 7) {
            for (int i = 0; i < 7; ++i) {
                byteMatrix.set(n + i, n2 + n3, POSITION_DETECTION_PATTERN[n3][i]);
            }
            ++n3;
        }
    }

    private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix byteMatrix) throws WriterException {
        int n = POSITION_DETECTION_PATTERN[0].length;
        MatrixUtil.embedPositionDetectionPattern(0, 0, byteMatrix);
        MatrixUtil.embedPositionDetectionPattern(byteMatrix.getWidth() - n, 0, byteMatrix);
        MatrixUtil.embedPositionDetectionPattern(0, byteMatrix.getWidth() - n, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(0, 7, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(byteMatrix.getWidth() - 8, 7, byteMatrix);
        MatrixUtil.embedHorizontalSeparationPattern(0, byteMatrix.getWidth() - 8, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(7, 0, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(byteMatrix.getHeight() - 7 - 1, 0, byteMatrix);
        MatrixUtil.embedVerticalSeparationPattern(7, byteMatrix.getHeight() - 7, byteMatrix);
    }

    private static void embedTimingPatterns(ByteMatrix byteMatrix) {
        int n = 8;
        while (n < byteMatrix.getWidth() - 8) {
            int n2 = n + 1;
            int n3 = n2 % 2;
            if (MatrixUtil.isEmpty(byteMatrix.get(n, 6))) {
                byteMatrix.set(n, 6, n3);
            }
            if (MatrixUtil.isEmpty(byteMatrix.get(6, n))) {
                byteMatrix.set(6, n, n3);
            }
            n = n2;
        }
    }

    static void embedTypeInfo(ErrorCorrectionLevel arrn, int n, ByteMatrix byteMatrix) throws WriterException {
        BitArray bitArray = new BitArray();
        MatrixUtil.makeTypeInfoBits((ErrorCorrectionLevel)arrn, n, bitArray);
        n = 0;
        while (n < bitArray.getSize()) {
            boolean bl = bitArray.get(bitArray.getSize() - 1 - n);
            arrn = TYPE_INFO_COORDINATES;
            byteMatrix.set(arrn[n][0], arrn[n][1], bl);
            if (n < 8) {
                byteMatrix.set(byteMatrix.getWidth() - n - 1, 8, bl);
            } else {
                byteMatrix.set(8, byteMatrix.getHeight() - 7 + (n - 8), bl);
            }
            ++n;
        }
    }

    private static void embedVerticalSeparationPattern(int n, int n2, ByteMatrix byteMatrix) throws WriterException {
        int n3 = 0;
        while (n3 < 7) {
            int n4 = n2 + n3;
            if (!MatrixUtil.isEmpty(byteMatrix.get(n, n4))) throw new WriterException();
            byteMatrix.set(n, n4, 0);
            ++n3;
        }
    }

    static int findMSBSet(int n) {
        int n2 = 0;
        while (n != 0) {
            n >>>= 1;
            ++n2;
        }
        return n2;
    }

    private static boolean isEmpty(int n) {
        if (n != -1) return false;
        return true;
    }

    static void makeTypeInfoBits(ErrorCorrectionLevel object, int n, BitArray bitArray) throws WriterException {
        if (!QRCode.isValidMaskPattern(n)) throw new WriterException("Invalid mask pattern");
        n = ((ErrorCorrectionLevel)((Object)object)).getBits() << 3 | n;
        bitArray.appendBits(n, 5);
        bitArray.appendBits(MatrixUtil.calculateBCHCode(n, 1335), 10);
        object = new BitArray();
        ((BitArray)object).appendBits(21522, 15);
        bitArray.xor((BitArray)object);
        if (bitArray.getSize() == 15) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("should not happen but we got: ");
        ((StringBuilder)object).append(bitArray.getSize());
        throw new WriterException(((StringBuilder)object).toString());
    }

    static void makeVersionInfoBits(Version object, BitArray bitArray) throws WriterException {
        bitArray.appendBits(((Version)object).getVersionNumber(), 6);
        bitArray.appendBits(MatrixUtil.calculateBCHCode(((Version)object).getVersionNumber(), 7973), 12);
        if (bitArray.getSize() == 18) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("should not happen but we got: ");
        ((StringBuilder)object).append(bitArray.getSize());
        throw new WriterException(((StringBuilder)object).toString());
    }

    private static void maybeEmbedPositionAdjustmentPatterns(Version arrn, ByteMatrix byteMatrix) {
        if (arrn.getVersionNumber() < 2) {
            return;
        }
        int n = arrn.getVersionNumber() - 1;
        arrn = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
        int[] arrn2 = arrn[n];
        int n2 = arrn[n].length;
        n = 0;
        while (n < n2) {
            for (int i = 0; i < n2; ++i) {
                int n3 = arrn2[n];
                int n4 = arrn2[i];
                if (n4 == -1 || n3 == -1 || !MatrixUtil.isEmpty(byteMatrix.get(n4, n3))) continue;
                MatrixUtil.embedPositionAdjustmentPattern(n4 - 2, n3 - 2, byteMatrix);
            }
            ++n;
        }
    }

    static void maybeEmbedVersionInfo(Version version, ByteMatrix byteMatrix) throws WriterException {
        if (version.getVersionNumber() < 7) {
            return;
        }
        BitArray bitArray = new BitArray();
        MatrixUtil.makeVersionInfoBits(version, bitArray);
        int n = 17;
        int n2 = 0;
        while (n2 < 6) {
            for (int i = 0; i < 3; --n, ++i) {
                boolean bl = bitArray.get(n);
                byteMatrix.set(n2, byteMatrix.getHeight() - 11 + i, bl);
                byteMatrix.set(byteMatrix.getHeight() - 11 + i, n2, bl);
            }
            ++n2;
        }
    }
}

