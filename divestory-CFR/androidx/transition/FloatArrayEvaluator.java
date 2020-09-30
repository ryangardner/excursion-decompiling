/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 */
package androidx.transition;

import android.animation.TypeEvaluator;

class FloatArrayEvaluator
implements TypeEvaluator<float[]> {
    private float[] mArray;

    FloatArrayEvaluator(float[] arrf) {
        this.mArray = arrf;
    }

    public float[] evaluate(float f, float[] arrf, float[] arrf2) {
        float[] arrf3;
        float[] arrf4 = arrf3 = this.mArray;
        if (arrf3 == null) {
            arrf4 = new float[arrf.length];
        }
        int n = 0;
        while (n < arrf4.length) {
            float f2 = arrf[n];
            arrf4[n] = f2 + (arrf2[n] - f2) * f;
            ++n;
        }
        return arrf4;
    }
}

