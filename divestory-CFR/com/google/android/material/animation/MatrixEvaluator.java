/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 *  android.graphics.Matrix
 */
package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public class MatrixEvaluator
implements TypeEvaluator<Matrix> {
    private final float[] tempEndValues = new float[9];
    private final Matrix tempMatrix = new Matrix();
    private final float[] tempStartValues = new float[9];

    public Matrix evaluate(float f, Matrix arrf, Matrix arrf2) {
        arrf.getValues(this.tempStartValues);
        arrf2.getValues(this.tempEndValues);
        int n = 0;
        do {
            if (n >= 9) {
                this.tempMatrix.setValues(this.tempEndValues);
                return this.tempMatrix;
            }
            arrf2 = this.tempEndValues;
            float f2 = arrf2[n];
            arrf = this.tempStartValues;
            float f3 = arrf[n];
            arrf2[n] = arrf[n] + (f2 - f3) * f;
            ++n;
        } while (true);
    }
}

