/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.aztec.decoder.Decoder;
import com.google.zxing.aztec.detector.Detector;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import java.util.List;
import java.util.Map;

public final class AztecReader
implements Reader {
    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    @Override
    public Result decode(BinaryBitmap object, Map<DecodeHintType, ?> list) throws NotFoundException, FormatException {
        Object object2;
        int n;
        block16 : {
            Object object5;
            Object object3;
            Object object4;
            Detector detector;
            block13 : {
                block15 : {
                    block14 : {
                        detector = new Detector(((BinaryBitmap)object).getBlackMatrix());
                        n = 0;
                        object3 = null;
                        object5 = detector.detect(false);
                        object = ((DetectorResult)object5).getPoints();
                        try {
                            object4 = new Decoder();
                            object5 = ((Decoder)object4).decode((AztecDetectorResult)object5);
                            object4 = null;
                            object3 = object5;
                            object5 = object4;
                            break block13;
                        }
                        catch (FormatException formatException) {
                            break block14;
                        }
                        catch (NotFoundException notFoundException) {
                            break block15;
                        }
                        catch (FormatException formatException) {
                            object = null;
                        }
                    }
                    object4 = object5;
                    object5 = null;
                    break block13;
                    catch (NotFoundException notFoundException) {
                        object = null;
                    }
                }
                object4 = null;
            }
            object2 = object3;
            if (object3 == null) {
                try {
                    object2 = detector.detect(true);
                    object = ((DetectorResult)object2).getPoints();
                    object3 = new Decoder();
                    object2 = ((Decoder)object3).decode((AztecDetectorResult)object2);
                    break block16;
                }
                catch (FormatException formatException) {
                }
                catch (NotFoundException notFoundException) {
                    // empty catch block
                }
                if (object5 != null) throw object5;
                if (object4 == null) void var1_4;
                throw var1_4;
                throw object4;
            }
        }
        if (list != null && (list = (ResultPointCallback)list.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK)) != null) {
            int n2 = ((Object)object).length;
            while (n < n2) {
                list.foundPossibleResultPoint((ResultPoint)object[n]);
                ++n;
            }
        }
        object = new Result(((DecoderResult)object2).getText(), ((DecoderResult)object2).getRawBytes(), (ResultPoint[])object, BarcodeFormat.AZTEC);
        list = ((DecoderResult)object2).getByteSegments();
        if (list != null) {
            ((Result)object).putMetadata(ResultMetadataType.BYTE_SEGMENTS, list);
        }
        if ((list = ((DecoderResult)object2).getECLevel()) == null) return object;
        ((Result)object).putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, list);
        return object;
    }

    @Override
    public void reset() {
    }
}

