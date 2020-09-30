/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.multi;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.multi.MultipleBarcodeReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader
implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int n, int n2, int n3) {
        Object object;
        ResultPoint[] arrresultPoint;
        int n4;
        block16 : {
            if (n3 > 4) {
                return;
            }
            try {
                object = this.delegate.decode(binaryBitmap, map);
                arrresultPoint = list.iterator();
            }
            catch (ReaderException readerException) {
                return;
            }
            while (arrresultPoint.hasNext()) {
                if (!arrresultPoint.next().getText().equals(((Result)object).getText())) continue;
                n4 = 1;
                break block16;
            }
            n4 = 0;
        }
        if (n4 == 0) {
            list.add(GenericMultipleBarcodeReader.translateResultPoints((Result)object, n, n2));
        }
        if ((arrresultPoint = ((Result)object).getResultPoints()) == null) return;
        if (arrresultPoint.length == 0) {
            return;
        }
        int n5 = binaryBitmap.getWidth();
        int n6 = binaryBitmap.getHeight();
        float f = n5;
        float f2 = n6;
        int n7 = arrresultPoint.length;
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (n4 = 0; n4 < n7; ++n4) {
            float f5;
            float f6;
            float f7;
            object = arrresultPoint[n4];
            if (object == null) {
                f7 = f;
                f5 = f2;
                f6 = f4;
            } else {
                f6 = ((ResultPoint)object).getX();
                float f8 = ((ResultPoint)object).getY();
                float f9 = f;
                if (f6 < f) {
                    f9 = f6;
                }
                f = f2;
                if (f8 < f2) {
                    f = f8;
                }
                f2 = f3;
                if (f6 > f3) {
                    f2 = f6;
                }
                f7 = f9;
                f3 = f2;
                f5 = f;
                f6 = f4;
                if (f8 > f4) {
                    f6 = f8;
                    f5 = f;
                    f3 = f2;
                    f7 = f9;
                }
            }
            f = f7;
            f2 = f5;
            f4 = f6;
        }
        if (f > 100.0f) {
            this.doDecodeMultiple(binaryBitmap.crop(0, 0, (int)f, n6), map, list, n, n2, n3 + 1);
        }
        if (f2 > 100.0f) {
            this.doDecodeMultiple(binaryBitmap.crop(0, 0, n5, (int)f2), map, list, n, n2, n3 + 1);
        }
        if (f3 < (float)(n5 - 100)) {
            n4 = (int)f3;
            this.doDecodeMultiple(binaryBitmap.crop(n4, 0, n5 - n4, n6), map, list, n + n4, n2, n3 + 1);
        }
        if (!(f4 < (float)(n6 - 100))) return;
        n4 = (int)f4;
        this.doDecodeMultiple(binaryBitmap.crop(0, n4, n5, n6 - n4), map, list, n, n2 + n4, n3 + 1);
    }

    private static Result translateResultPoints(Result result, int n, int n2) {
        ResultPoint[] arrresultPoint = result.getResultPoints();
        if (arrresultPoint == null) {
            return result;
        }
        ResultPoint[] arrresultPoint2 = new ResultPoint[arrresultPoint.length];
        int n3 = 0;
        do {
            Object object;
            if (n3 >= arrresultPoint.length) {
                object = new Result(result.getText(), result.getRawBytes(), arrresultPoint2, result.getBarcodeFormat());
                ((Result)object).putAllMetadata(result.getResultMetadata());
                return object;
            }
            object = arrresultPoint[n3];
            if (object != null) {
                arrresultPoint2[n3] = new ResultPoint(((ResultPoint)object).getX() + (float)n, ((ResultPoint)object).getY() + (float)n2);
            }
            ++n3;
        } while (true);
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList<Result> arrayList = new ArrayList<Result>();
        this.doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (arrayList.isEmpty()) throw NotFoundException.getNotFoundInstance();
        return arrayList.toArray(new Result[arrayList.size()]);
    }
}

