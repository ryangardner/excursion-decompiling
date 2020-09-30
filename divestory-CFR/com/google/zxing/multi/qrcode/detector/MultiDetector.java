/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.qrcode.detector.MultiFinderPatternFinder;
import com.google.zxing.qrcode.detector.Detector;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.ArrayList;
import java.util.Map;

public final class MultiDetector
extends Detector {
    private static final DetectorResult[] EMPTY_DETECTOR_RESULTS = new DetectorResult[0];

    public MultiDetector(BitMatrix bitMatrix) {
        super(bitMatrix);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public DetectorResult[] detectMulti(Map<DecodeHintType, ?> var1_1) throws NotFoundException {
        var2_2 = this.getImage();
        var3_4 = var1_1 == null ? null : (FinderPatternInfo[])var1_1.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        var3_4 = new MultiFinderPatternFinder((BitMatrix)var2_2, (ResultPointCallback)var3_4).findMulti((Map<DecodeHintType, ?>)var1_1);
        if (var3_4.length == 0) throw NotFoundException.getNotFoundInstance();
        var1_1 = new ArrayList<E>();
        var4_5 = var3_4.length;
        var5_6 = 0;
        block2 : do {
            if (var5_6 >= var4_5) {
                if (var1_1.isEmpty() == false) return var1_1.toArray(new DetectorResult[var1_1.size()]);
                return MultiDetector.EMPTY_DETECTOR_RESULTS;
            }
            var2_2 = var3_4[var5_6];
            try {
                var1_1.add(this.processFinderPatternInfo((FinderPatternInfo)var2_2));
lbl16: // 2 sources:
                do {
                    ++var5_6;
                    continue block2;
                    break;
                } while (true);
            }
            catch (ReaderException var2_3) {
                ** continue;
            }
        } while (true);
    }
}

