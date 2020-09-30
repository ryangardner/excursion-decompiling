/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 */
package androidx.transition;

import android.graphics.Matrix;
import android.view.View;
import androidx.transition.ViewUtilsApi19;

class ViewUtilsApi21
extends ViewUtilsApi19 {
    private static boolean sTryHiddenSetAnimationMatrix = true;
    private static boolean sTryHiddenTransformMatrixToGlobal = true;
    private static boolean sTryHiddenTransformMatrixToLocal = true;

    ViewUtilsApi21() {
    }

    @Override
    public void setAnimationMatrix(View view, Matrix matrix) {
        if (!sTryHiddenSetAnimationMatrix) return;
        try {
            view.setAnimationMatrix(matrix);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenSetAnimationMatrix = false;
        }
    }

    @Override
    public void transformMatrixToGlobal(View view, Matrix matrix) {
        if (!sTryHiddenTransformMatrixToGlobal) return;
        try {
            view.transformMatrixToGlobal(matrix);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenTransformMatrixToGlobal = false;
        }
    }

    @Override
    public void transformMatrixToLocal(View view, Matrix matrix) {
        if (!sTryHiddenTransformMatrixToLocal) return;
        try {
            view.transformMatrixToLocal(matrix);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenTransformMatrixToLocal = false;
        }
    }
}

