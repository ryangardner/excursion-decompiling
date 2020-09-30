/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417;

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
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import com.google.zxing.pdf417.decoder.PDF417ScanningDecoder;
import com.google.zxing.pdf417.detector.Detector;
import com.google.zxing.pdf417.detector.PDF417DetectorResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class PDF417Reader
implements Reader,
MultipleBarcodeReader {
    private static Result[] decode(BinaryBitmap object, Map<DecodeHintType, ?> object2, boolean bl) throws NotFoundException, FormatException, ChecksumException {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        object2 = Detector.detect((BinaryBitmap)object, object2, bl);
        object = ((PDF417DetectorResult)object2).getPoints().iterator();
        while (object.hasNext()) {
            Object object3 = (ResultPoint[])object.next();
            Object object4 = PDF417ScanningDecoder.decode(((PDF417DetectorResult)object2).getBits(), object3[4], object3[5], object3[6], object3[7], PDF417Reader.getMinCodewordWidth((ResultPoint[])object3), PDF417Reader.getMaxCodewordWidth((ResultPoint[])object3));
            object3 = new Result(((DecoderResult)object4).getText(), ((DecoderResult)object4).getRawBytes(), (ResultPoint[])object3, BarcodeFormat.PDF_417);
            ((Result)object3).putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ((DecoderResult)object4).getECLevel());
            object4 = (PDF417ResultMetadata)((DecoderResult)object4).getOther();
            if (object4 != null) {
                ((Result)object3).putMetadata(ResultMetadataType.PDF417_EXTRA_METADATA, object4);
            }
            arrayList.add(object3);
        }
        return arrayList.toArray(new Result[arrayList.size()]);
    }

    private static int getMaxCodewordWidth(ResultPoint[] arrresultPoint) {
        return Math.max(Math.max(PDF417Reader.getMaxWidth(arrresultPoint[0], arrresultPoint[4]), PDF417Reader.getMaxWidth(arrresultPoint[6], arrresultPoint[2]) * 17 / 18), Math.max(PDF417Reader.getMaxWidth(arrresultPoint[1], arrresultPoint[5]), PDF417Reader.getMaxWidth(arrresultPoint[7], arrresultPoint[3]) * 17 / 18));
    }

    private static int getMaxWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint == null) return 0;
        if (resultPoint2 != null) return (int)Math.abs(resultPoint.getX() - resultPoint2.getX());
        return 0;
    }

    private static int getMinCodewordWidth(ResultPoint[] arrresultPoint) {
        return Math.min(Math.min(PDF417Reader.getMinWidth(arrresultPoint[0], arrresultPoint[4]), PDF417Reader.getMinWidth(arrresultPoint[6], arrresultPoint[2]) * 17 / 18), Math.min(PDF417Reader.getMinWidth(arrresultPoint[1], arrresultPoint[5]), PDF417Reader.getMinWidth(arrresultPoint[7], arrresultPoint[3]) * 17 / 18));
    }

    private static int getMinWidth(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint == null) return Integer.MAX_VALUE;
        if (resultPoint2 != null) return (int)Math.abs(resultPoint.getX() - resultPoint2.getX());
        return Integer.MAX_VALUE;
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException, ChecksumException {
        return this.decode(binaryBitmap, null);
    }

    @Override
    public Result decode(BinaryBitmap arrresult, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        if ((arrresult = PDF417Reader.decode((BinaryBitmap)arrresult, map, false)) == null) throw NotFoundException.getNotFoundInstance();
        if (arrresult.length == 0) throw NotFoundException.getNotFoundInstance();
        if (arrresult[0] == null) throw NotFoundException.getNotFoundInstance();
        return arrresult[0];
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap arrresult, Map<DecodeHintType, ?> map) throws NotFoundException {
        try {
            return PDF417Reader.decode((BinaryBitmap)arrresult, map, true);
        }
        catch (ChecksumException | FormatException readerException) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    @Override
    public void reset() {
    }
}

