/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.EANManufacturerOrgSupport;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.UPCEANExtensionSupport;
import java.util.Arrays;
import java.util.Map;

public abstract class UPCEANReader
extends OneDReader {
    static final int[][] L_AND_G_PATTERNS;
    static final int[][] L_PATTERNS;
    private static final float MAX_AVG_VARIANCE = 0.48f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;
    static final int[] MIDDLE_PATTERN;
    static final int[] START_END_PATTERN;
    private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
    private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();
    private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();

    static {
        START_END_PATTERN = new int[]{1, 1, 1};
        MIDDLE_PATTERN = new int[]{1, 1, 1, 1, 1};
        int n = 10;
        int[][] arrarrn = new int[][]{{3, 2, 1, 1}, {2, 2, 2, 1}, {2, 1, 2, 2}, {1, 4, 1, 1}, {1, 1, 3, 2}, {1, 2, 3, 1}, {1, 1, 1, 4}, {1, 3, 1, 2}, {1, 2, 1, 3}, {3, 1, 1, 2}};
        L_PATTERNS = arrarrn;
        int[][] arrarrn2 = new int[20][];
        L_AND_G_PATTERNS = arrarrn2;
        System.arraycopy(arrarrn, 0, arrarrn2, 0, 10);
        while (n < 20) {
            arrarrn2 = L_PATTERNS[n - 10];
            arrarrn = new int[arrarrn2.length];
            for (int i = 0; i < arrarrn2.length; ++i) {
                arrarrn[i] = arrarrn2[arrarrn2.length - i - 1];
            }
            UPCEANReader.L_AND_G_PATTERNS[n] = arrarrn;
            ++n;
        }
    }

    protected UPCEANReader() {
    }

    static boolean checkStandardUPCEANChecksum(CharSequence charSequence) throws FormatException {
        int n;
        int n2;
        int n3 = charSequence.length();
        boolean bl = false;
        if (n3 == 0) {
            return false;
        }
        int n4 = 0;
        for (n = n3 - 2; n >= 0; n4 += n2, n -= 2) {
            n2 = charSequence.charAt(n) - 48;
            if (n2 < 0) throw FormatException.getFormatInstance();
            if (n2 > 9) throw FormatException.getFormatInstance();
        }
        n4 *= 3;
        n = n3 - 1;
        do {
            if (n < 0) {
                if (n4 % 10 != 0) return bl;
                return true;
            }
            n3 = charSequence.charAt(n) - 48;
            if (n3 < 0) throw FormatException.getFormatInstance();
            if (n3 > 9) throw FormatException.getFormatInstance();
            n4 += n3;
            n -= 2;
        } while (true);
    }

    static int decodeDigit(BitArray bitArray, int[] arrn, int n, int[][] arrn2) throws NotFoundException {
        UPCEANReader.recordPattern(bitArray, n, arrn);
        int n2 = arrn2.length;
        float f = 0.48f;
        int n3 = -1;
        n = 0;
        do {
            if (n >= n2) {
                if (n3 < 0) throw NotFoundException.getNotFoundInstance();
                return n3;
            }
            float f2 = UPCEANReader.patternMatchVariance(arrn, arrn2[n], 0.7f);
            float f3 = f;
            if (f2 < f) {
                n3 = n;
                f3 = f2;
            }
            ++n;
            f = f3;
        } while (true);
    }

    static int[] findGuardPattern(BitArray bitArray, int n, boolean bl, int[] arrn) throws NotFoundException {
        return UPCEANReader.findGuardPattern(bitArray, n, bl, arrn, new int[arrn.length]);
    }

    private static int[] findGuardPattern(BitArray bitArray, int n, boolean bl, int[] arrn, int[] arrn2) throws NotFoundException {
        int n2 = arrn.length;
        int n3 = bitArray.getSize();
        n = bl ? bitArray.getNextUnset(n) : bitArray.getNextSet(n);
        int n4 = 0;
        int n5 = n;
        int n6 = n;
        n = n4;
        while (n6 < n3) {
            if (bitArray.get(n6) ^ bl) {
                arrn2[n] = arrn2[n] + 1;
            } else {
                n4 = n2 - 1;
                if (n == n4) {
                    if (UPCEANReader.patternMatchVariance(arrn2, arrn, 0.7f) < 0.48f) {
                        return new int[]{n5, n6};
                    }
                    n5 += arrn2[0] + arrn2[1];
                    int n7 = n2 - 2;
                    System.arraycopy(arrn2, 2, arrn2, 0, n7);
                    arrn2[n7] = 0;
                    arrn2[n4] = 0;
                    --n;
                } else {
                    ++n;
                }
                arrn2[n] = 1;
                bl ^= true;
            }
            ++n6;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    static int[] findStartGuardPattern(BitArray bitArray) throws NotFoundException {
        int[] arrn = new int[START_END_PATTERN.length];
        int[] arrn2 = null;
        boolean bl = false;
        int n = 0;
        while (!bl) {
            Arrays.fill(arrn, 0, START_END_PATTERN.length, 0);
            arrn2 = UPCEANReader.findGuardPattern(bitArray, n, false, START_END_PATTERN, arrn);
            int n2 = arrn2[0];
            n = arrn2[1];
            int n3 = n2 - (n - n2);
            if (n3 < 0) continue;
            bl = bitArray.isRange(n3, n2, false);
        }
        return arrn2;
    }

    boolean checkChecksum(String string2) throws FormatException {
        return UPCEANReader.checkStandardUPCEANChecksum(string2);
    }

    int[] decodeEnd(BitArray bitArray, int n) throws NotFoundException {
        return UPCEANReader.findGuardPattern(bitArray, n, false, START_END_PATTERN);
    }

    protected abstract int decodeMiddle(BitArray var1, int[] var2, StringBuilder var3) throws NotFoundException;

    @Override
    public Result decodeRow(int n, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        return this.decodeRow(n, bitArray, UPCEANReader.findStartGuardPattern(bitArray), map);
    }

    public Result decodeRow(int n, BitArray object, int[] object2, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int n2;
        Object var5_6 = null;
        Object object3 = map == null ? null : (ResultPointCallback)map.get((Object)((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK));
        int n3 = 1;
        if (object3 != null) {
            object3.foundPossibleResultPoint(new ResultPoint((float)(object2[0] + object2[1]) / 2.0f, n));
        }
        Object object4 = this.decodeRowStringBuffer;
        object4.setLength(0);
        int n4 = this.decodeMiddle((BitArray)object, (int[])object2, (StringBuilder)object4);
        if (object3 != null) {
            object3.foundPossibleResultPoint(new ResultPoint(n4, n));
        }
        int[] arrn = this.decodeEnd((BitArray)object, n4);
        if (object3 != null) {
            object3.foundPossibleResultPoint(new ResultPoint((float)(arrn[0] + arrn[1]) / 2.0f, n));
        }
        if ((n4 = (n2 = arrn[1]) - arrn[0] + n2) >= ((BitArray)object).getSize()) throw NotFoundException.getNotFoundInstance();
        if (!((BitArray)object).isRange(n2, n4, false)) throw NotFoundException.getNotFoundInstance();
        object3 = object4.toString();
        if (((String)object3).length() < 8) throw FormatException.getFormatInstance();
        if (!this.checkChecksum((String)object3)) throw ChecksumException.getChecksumInstance();
        float f = (float)(object2[1] + object2[0]) / 2.0f;
        float f2 = (float)(arrn[1] + arrn[0]) / 2.0f;
        object4 = this.getBarcodeFormat();
        float f3 = n;
        object2 = new Result((String)object3, null, new ResultPoint[]{new ResultPoint(f, f3), new ResultPoint(f2, f3)}, (BarcodeFormat)((Object)object4));
        try {
            object = this.extensionReader.decodeRow(n, (BitArray)object, arrn[1]);
            ((Result)object2).putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, ((Result)object).getText());
            ((Result)object2).putAllMetadata(((Result)object).getResultMetadata());
            ((Result)object2).addResultPoints(((Result)object).getResultPoints());
            n = ((Result)object).getText().length();
        }
        catch (ReaderException readerException) {
            n = 0;
        }
        object = map == null ? var5_6 : (int[])map.get((Object)((Object)DecodeHintType.ALLOWED_EAN_EXTENSIONS));
        if (object != null) {
            block8 : {
                n2 = ((int[])object).length;
                for (n4 = 0; n4 < n2; ++n4) {
                    if (n != object[n4]) continue;
                    n = n3;
                    break block8;
                }
                n = 0;
            }
            if (n == 0) throw NotFoundException.getNotFoundInstance();
        }
        if (object4 != BarcodeFormat.EAN_13) {
            if (object4 != BarcodeFormat.UPC_A) return object2;
        }
        if ((object = this.eanManSupport.lookupCountryIdentifier((String)object3)) == null) return object2;
        ((Result)object2).putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, object);
        return object2;
    }

    abstract BarcodeFormat getBarcodeFormat();
}

