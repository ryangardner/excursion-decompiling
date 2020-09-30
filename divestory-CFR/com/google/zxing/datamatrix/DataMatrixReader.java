/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.datamatrix.decoder.Decoder;
import com.google.zxing.datamatrix.detector.Detector;
import java.util.List;
import java.util.Map;

public final class DataMatrixReader
implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        int[] arrn = bitMatrix.getTopLeftOnBit();
        Object object = bitMatrix.getBottomRightOnBit();
        if (arrn == null) throw NotFoundException.getNotFoundInstance();
        if (object == null) throw NotFoundException.getNotFoundInstance();
        int n = DataMatrixReader.moduleSize(arrn, bitMatrix);
        int n2 = arrn[1];
        int n3 = object[1];
        int n4 = arrn[0];
        int n5 = (object[0] - n4 + 1) / n;
        int n6 = (n3 - n2 + 1) / n;
        if (n5 <= 0) throw NotFoundException.getNotFoundInstance();
        if (n6 <= 0) throw NotFoundException.getNotFoundInstance();
        int n7 = n / 2;
        object = new BitMatrix(n5, n6);
        n3 = 0;
        while (n3 < n6) {
            for (int i = 0; i < n5; ++i) {
                if (!bitMatrix.get(i * n + (n4 + n7), n3 * n + (n2 + n7))) continue;
                ((BitMatrix)object).set(i, n3);
            }
            ++n3;
        }
        return object;
    }

    private static int moduleSize(int[] arrn, BitMatrix bitMatrix) throws NotFoundException {
        int n;
        int n2 = bitMatrix.getWidth();
        int n3 = arrn[1];
        for (n = arrn[0]; n < n2 && bitMatrix.get(n, n3); ++n) {
        }
        if (n == n2) throw NotFoundException.getNotFoundInstance();
        if ((n -= arrn[0]) == 0) throw NotFoundException.getNotFoundInstance();
        return n;
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        if (object2 != null && object2.containsKey((Object)DecodeHintType.PURE_BARCODE)) {
            object = DataMatrixReader.extractPureBits(((BinaryBitmap)object).getBlackMatrix());
            object = this.decoder.decode((BitMatrix)object);
            object2 = NO_POINTS;
        } else {
            object2 = new Detector(((BinaryBitmap)object).getBlackMatrix()).detect();
            object = this.decoder.decode(((DetectorResult)object2).getBits());
            object2 = ((DetectorResult)object2).getPoints();
        }
        object2 = new Result(((DecoderResult)object).getText(), ((DecoderResult)object).getRawBytes(), (ResultPoint[])object2, BarcodeFormat.DATA_MATRIX);
        List<byte[]> list = ((DecoderResult)object).getByteSegments();
        if (list != null) {
            ((Result)object2).putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
        }
        if ((object = ((DecoderResult)object).getECLevel()) == null) return object2;
        ((Result)object2).putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, object);
        return object2;
    }

    @Override
    public void reset() {
    }
}

