/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.multi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class QRCodeMultiReader
extends QRCodeReader
implements MultipleBarcodeReader {
    private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];

    private static List<Result> processStructuredAppend(List<Result> object) {
        Object object2;
        int n;
        int n2;
        Object object3;
        int n3;
        block8 : {
            object2 = object.iterator();
            while (object2.hasNext()) {
                if (!object2.next().getResultMetadata().containsKey((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) continue;
                n2 = 1;
                break block8;
            }
            n2 = 0;
        }
        if (n2 == 0) {
            return object;
        }
        object2 = new ArrayList();
        ArrayList<Iterator<Result>> arrayList = new ArrayList<Object>();
        Iterator<Result> iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            object2.add(object);
            if (!((Result)object).getResultMetadata().containsKey((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) continue;
            arrayList.add((Iterator<Result>)object);
        }
        Collections.sort(arrayList, new SAComparator());
        object = new StringBuilder();
        iterator2 = arrayList.iterator();
        int n4 = 0;
        n2 = 0;
        block2 : while (iterator2.hasNext()) {
            object3 = iterator2.next();
            ((StringBuilder)object).append(((Result)object3).getText());
            n4 = n = n4 + ((Result)object3).getRawBytes().length;
            if (!((Result)object3).getResultMetadata().containsKey((Object)ResultMetadataType.BYTE_SEGMENTS)) continue;
            object3 = ((Iterable)((Result)object3).getResultMetadata().get((Object)ResultMetadataType.BYTE_SEGMENTS)).iterator();
            n3 = n2;
            do {
                n4 = n;
                n2 = n3;
                if (!object3.hasNext()) continue block2;
                n3 += ((byte[])object3.next()).length;
            } while (true);
        }
        object3 = new byte[n4];
        iterator2 = new byte[n2];
        arrayList = arrayList.iterator();
        n = 0;
        n3 = 0;
        block4 : while (arrayList.hasNext()) {
            int n5;
            Object object4 = (Result)arrayList.next();
            System.arraycopy(((Result)object4).getRawBytes(), 0, object3, n, ((Result)object4).getRawBytes().length);
            n = n5 = n + ((Result)object4).getRawBytes().length;
            if (!((Result)object4).getResultMetadata().containsKey((Object)ResultMetadataType.BYTE_SEGMENTS)) continue;
            object4 = ((Iterable)((Result)object4).getResultMetadata().get((Object)ResultMetadataType.BYTE_SEGMENTS)).iterator();
            n4 = n3;
            do {
                n = n5;
                n3 = n4;
                if (!object4.hasNext()) continue block4;
                byte[] arrby = (byte[])object4.next();
                System.arraycopy(arrby, 0, iterator2, n4, arrby.length);
                n4 += arrby.length;
            } while (true);
        }
        object = new Result(((StringBuilder)object).toString(), (byte[])object3, NO_POINTS, BarcodeFormat.QR_CODE);
        if (n2 > 0) {
            arrayList = new ArrayList<Iterator<Result>>();
            arrayList.add(iterator2);
            ((Result)object).putMetadata(ResultMetadataType.BYTE_SEGMENTS, arrayList);
        }
        object2.add(object);
        return object2;
    }

    @Override
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return this.decodeMultiple(binaryBitmap, null);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public Result[] decodeMultiple(BinaryBitmap var1_1, Map<DecodeHintType, ?> var2_2) throws NotFoundException {
        var3_3 = new ArrayList<Result>();
        var1_1 = new MultiDetector(var1_1.getBlackMatrix()).detectMulti(var2_2);
        var4_4 = ((Object)var1_1).length;
        var5_5 = 0;
        block2 : do {
            if (var5_5 >= var4_4) ** GOTO lbl25
            var6_6 = var1_1[var5_5];
            try {
                block9 : {
                    var7_7 = this.getDecoder().decode(var6_6.getBits(), var2_2);
                    var8_10 = var6_6.getPoints();
                    if (var7_7.getOther() instanceof QRCodeDecoderMetaData) {
                        ((QRCodeDecoderMetaData)var7_7.getOther()).applyMirroredCorrection(var8_10);
                    }
                    var6_6 = new Result(var7_7.getText(), var7_7.getRawBytes(), var8_10, BarcodeFormat.QR_CODE);
                    var8_11 = var7_7.getByteSegments();
                    if (var8_11 != null) {
                        var6_6.putMetadata(ResultMetadataType.BYTE_SEGMENTS, var8_11);
                    }
                    if ((var8_9 = var7_7.getECLevel()) != null) {
                        var6_6.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, var8_9);
                    }
                    if (var7_7.hasStructuredAppend()) {
                        var6_6.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, var7_7.getStructuredAppendSequenceNumber());
                        var6_6.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, var7_7.getStructuredAppendParity());
                    }
                    var3_3.add((Result)var6_6);
                    break block9;
lbl25: // 1 sources:
                    if (var3_3.isEmpty()) {
                        return QRCodeMultiReader.EMPTY_RESULT_ARRAY;
                    }
                    var1_1 = QRCodeMultiReader.processStructuredAppend(var3_3);
                    return var1_1.toArray(new Result[var1_1.size()]);
                }
lbl30: // 2 sources:
                do {
                    ++var5_5;
                    continue block2;
                    break;
                } while (true);
            }
            catch (ReaderException var7_8) {
                ** continue;
            }
        } while (true);
    }

    private static final class SAComparator
    implements Comparator<Result>,
    Serializable {
        private SAComparator() {
        }

        @Override
        public int compare(Result result, Result result2) {
            int n;
            int n2 = (Integer)result.getResultMetadata().get((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE);
            if (n2 < (n = ((Integer)result2.getResultMetadata().get((Object)ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)).intValue())) {
                return -1;
            }
            if (n2 <= n) return 0;
            return 1;
        }
    }

}

