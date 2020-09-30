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
import androidx.transition.ViewUtilsApi23;

class ViewUtilsApi29
extends ViewUtilsApi23 {
    ViewUtilsApi29() {
    }

    @Override
    public float getTransitionAlpha(View view) {
        return view.getTransitionAlpha();
    }

    @Override
    public void setAnimationMatrix(View view, Matrix matrix) {
        view.setAnimationMatrix(matrix);
    }

    @Override
    public void setLeftTopRightBottom(View view, int n, int n2, int n3, int n4) {
        view.setLeftTopRightBottom(n, n2, n3, n4);
    }

    @Override
    public void setTransitionAlpha(View view, float f) {
        view.setTransitionAlpha(f);
    }

    @Override
    public void setTransitionVisibility(View view, int n) {
        view.setTransitionVisibility(n);
    }

    @Override
    public void transformMatrixToGlobal(View view, Matrix matrix) {
        view.transformMatrixToGlobal(matrix);
    }

    @Override
    public void transformMatrixToLocal(View view, Matrix matrix) {
        view.transformMatrixToLocal(matrix);
    }
}

