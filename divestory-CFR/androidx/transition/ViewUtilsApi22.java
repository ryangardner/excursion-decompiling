/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.transition;

import android.view.View;
import androidx.transition.ViewUtilsApi21;

class ViewUtilsApi22
extends ViewUtilsApi21 {
    private static boolean sTryHiddenSetLeftTopRightBottom = true;

    ViewUtilsApi22() {
    }

    @Override
    public void setLeftTopRightBottom(View view, int n, int n2, int n3, int n4) {
        if (!sTryHiddenSetLeftTopRightBottom) return;
        try {
            view.setLeftTopRightBottom(n, n2, n3, n4);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenSetLeftTopRightBottom = false;
        }
    }
}

