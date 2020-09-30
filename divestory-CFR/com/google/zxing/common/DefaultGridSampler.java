/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;

public final class DefaultGridSampler
extends GridSampler {
    @Override
    public BitMatrix sampleGrid(BitMatrix bitMatrix, int n, int n2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) throws NotFoundException {
        return this.sampleGrid(bitMatrix, n, n2, PerspectiveTransform.quadrilateralToQuadrilateral(f, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16));
    }

    @Override
    public BitMatrix sampleGrid(BitMatrix bitMatrix, int n, int n2, PerspectiveTransform perspectiveTransform) throws NotFoundException {
        if (n <= 0) throw NotFoundException.getNotFoundInstance();
        if (n2 <= 0) throw NotFoundException.getNotFoundInstance();
        BitMatrix bitMatrix2 = new BitMatrix(n, n2);
        int n3 = n * 2;
        float[] arrf = new float[n3];
        n = 0;
        while (n < n2) {
            int n4;
            float f = n;
            for (n4 = 0; n4 < n3; n4 += 2) {
                arrf[n4] = (float)(n4 / 2) + 0.5f;
                arrf[n4 + 1] = f + 0.5f;
            }
            perspectiveTransform.transformPoints(arrf);
            DefaultGridSampler.checkAndNudgePoints(bitMatrix, arrf);
            for (n4 = 0; n4 < n3; n4 += 2) {
                try {
                    if (!bitMatrix.get((int)arrf[n4], (int)arrf[n4 + 1])) continue;
                    bitMatrix2.set(n4 / 2, n);
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    throw NotFoundException.getNotFoundInstance();
                }
            }
            ++n;
        }
        return bitMatrix2;
    }
}

