/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 */
package com.google.android.material.transition.platform;

import android.graphics.RectF;
import com.google.android.material.transition.platform.FitModeEvaluator;
import com.google.android.material.transition.platform.FitModeResult;
import com.google.android.material.transition.platform.TransitionUtils;

class FitModeEvaluators {
    private static final FitModeEvaluator HEIGHT;
    private static final FitModeEvaluator WIDTH;

    static {
        WIDTH = new FitModeEvaluator(){

            @Override
            public void applyMask(RectF rectF, float f, FitModeResult fitModeResult) {
                float f2 = Math.abs(fitModeResult.currentEndHeight - fitModeResult.currentStartHeight);
                rectF.bottom -= f2 * f;
            }

            @Override
            public FitModeResult evaluate(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
                f = TransitionUtils.lerp(f4, f6, f2, f3, f);
                f2 = f / f4;
                f3 = f / f6;
                return new FitModeResult(f2, f3, f, f5 * f2, f, f7 * f3);
            }

            @Override
            public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
                if (!(fitModeResult.currentStartHeight > fitModeResult.currentEndHeight)) return false;
                return true;
            }
        };
        HEIGHT = new FitModeEvaluator(){

            @Override
            public void applyMask(RectF rectF, float f, FitModeResult fitModeResult) {
                float f2 = Math.abs(fitModeResult.currentEndWidth - fitModeResult.currentStartWidth);
                float f3 = rectF.left;
                f = f2 / 2.0f * f;
                rectF.left = f3 + f;
                rectF.right -= f;
            }

            @Override
            public FitModeResult evaluate(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
                f = TransitionUtils.lerp(f5, f7, f2, f3, f);
                f2 = f / f5;
                f3 = f / f7;
                return new FitModeResult(f2, f3, f4 * f2, f, f6 * f3, f);
            }

            @Override
            public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
                if (!(fitModeResult.currentStartWidth > fitModeResult.currentEndWidth)) return false;
                return true;
            }
        };
    }

    private FitModeEvaluators() {
    }

    static FitModeEvaluator get(int n, boolean bl, RectF object, RectF rectF) {
        if (n != 0) {
            if (n == 1) return WIDTH;
            if (n == 2) {
                return HEIGHT;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid fit mode: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (!FitModeEvaluators.shouldAutoFitToWidth(bl, (RectF)object, rectF)) return HEIGHT;
        return WIDTH;
    }

    private static boolean shouldAutoFitToWidth(boolean bl, RectF rectF, RectF rectF2) {
        float f = rectF.width();
        float f2 = rectF.height();
        float f3 = rectF2.width();
        float f4 = rectF2.height();
        float f5 = f4 * f / f3;
        f = f3 * f2 / f;
        boolean bl2 = true;
        if (bl) {
            if (!(f5 >= f2)) return false;
            return bl2;
        }
        if (!(f >= f4)) return false;
        return bl2;
    }

}

