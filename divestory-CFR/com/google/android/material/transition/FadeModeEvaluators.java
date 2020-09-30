/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.transition;

import com.google.android.material.transition.FadeModeEvaluator;
import com.google.android.material.transition.FadeModeResult;
import com.google.android.material.transition.TransitionUtils;

class FadeModeEvaluators {
    private static final FadeModeEvaluator CROSS;
    private static final FadeModeEvaluator IN;
    private static final FadeModeEvaluator OUT;
    private static final FadeModeEvaluator THROUGH;

    static {
        IN = new FadeModeEvaluator(){

            @Override
            public FadeModeResult evaluate(float f, float f2, float f3) {
                return FadeModeResult.endOnTop(255, TransitionUtils.lerp(0, 255, f2, f3, f));
            }
        };
        OUT = new FadeModeEvaluator(){

            @Override
            public FadeModeResult evaluate(float f, float f2, float f3) {
                return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f2, f3, f), 255);
            }
        };
        CROSS = new FadeModeEvaluator(){

            @Override
            public FadeModeResult evaluate(float f, float f2, float f3) {
                return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f2, f3, f), TransitionUtils.lerp(0, 255, f2, f3, f));
            }
        };
        THROUGH = new FadeModeEvaluator(){

            @Override
            public FadeModeResult evaluate(float f, float f2, float f3) {
                float f4 = (f3 - f2) * 0.35f + f2;
                return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, f2, f4, f), TransitionUtils.lerp(0, 255, f4, f3, f));
            }
        };
    }

    private FadeModeEvaluators() {
    }

    static FadeModeEvaluator get(int n, boolean bl) {
        if (n != 0) {
            if (n != 1) {
                if (n == 2) return CROSS;
                if (n == 3) {
                    return THROUGH;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid fade mode: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            if (!bl) return IN;
            return OUT;
        }
        if (!bl) return OUT;
        return IN;
    }

}

