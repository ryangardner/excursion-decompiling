/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.maxicode;

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
import com.google.zxing.maxicode.decoder.Decoder;
import java.util.Map;

public final class MaxiCodeReader
implements Reader {
    private static final int MATRIX_HEIGHT = 33;
    private static final int MATRIX_WIDTH = 30;
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        Object object = bitMatrix.getEnclosingRectangle();
        if (object == null) throw NotFoundException.getNotFoundInstance();
        int n = object[0];
        int n2 = object[1];
        int n3 = object[2];
        int n4 = object[3];
        object = new BitMatrix(30, 33);
        int n5 = 0;
        while (n5 < 33) {
            int n6 = (n5 * n4 + n4 / 2) / 33;
            for (int i = 0; i < 30; ++i) {
                if (!bitMatrix.get((i * n3 + n3 / 2 + (n5 & 1) * n3 / 2) / 30 + n, n6 + n2)) continue;
                ((BitMatrix)object).set(i, n5);
            }
            ++n5;
        }
        return object;
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> object2) throws NotFoundException, ChecksumException, FormatException {
        if (object2 == null) throw NotFoundException.getNotFoundInstance();
        if (!object2.containsKey((Object)DecodeHintType.PURE_BARCODE)) throw NotFoundException.getNotFoundInstance();
        object = MaxiCodeReader.extractPureBits(((BinaryBitmap)object).getBlackMatrix());
        object2 = this.decoder.decode((BitMatrix)object, (Map<DecodeHintType, ?>)object2);
        object = NO_POINTS;
        object = new Result(((DecoderResult)object2).getText(), ((DecoderResult)object2).getRawBytes(), (ResultPoint[])object, BarcodeFormat.MAXICODE);
        if ((object2 = ((DecoderResult)object2).getECLevel()) == null) return object;
        ((Result)object).putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, object2);
        return object;
    }

    @Override
    public void reset() {
    }
}

