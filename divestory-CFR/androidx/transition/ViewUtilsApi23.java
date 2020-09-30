/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 */
package androidx.transition;

import android.os.Build;
import android.view.View;
import androidx.transition.ViewUtilsApi22;

class ViewUtilsApi23
extends ViewUtilsApi22 {
    private static boolean sTryHiddenSetTransitionVisibility = true;

    ViewUtilsApi23() {
    }

    @Override
    public void setTransitionVisibility(View view, int n) {
        if (Build.VERSION.SDK_INT == 28) {
            super.setTransitionVisibility(view, n);
            return;
        }
        if (!sTryHiddenSetTransitionVisibility) return;
        try {
            view.setTransitionVisibility(n);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenSetTransitionVisibility = false;
        }
    }
}

