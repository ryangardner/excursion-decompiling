/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.transition;

import android.view.View;
import androidx.transition.ViewUtilsBase;

class ViewUtilsApi19
extends ViewUtilsBase {
    private static boolean sTryHiddenTransitionAlpha = true;

    ViewUtilsApi19() {
    }

    @Override
    public void clearNonTransitionAlpha(View view) {
    }

    @Override
    public float getTransitionAlpha(View view) {
        if (!sTryHiddenTransitionAlpha) return view.getAlpha();
        try {
            return view.getTransitionAlpha();
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenTransitionAlpha = false;
        }
        return view.getAlpha();
    }

    @Override
    public void saveNonTransitionAlpha(View view) {
    }

    @Override
    public void setTransitionAlpha(View view, float f) {
        if (sTryHiddenTransitionAlpha) {
            try {
                view.setTransitionAlpha(f);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                sTryHiddenTransitionAlpha = false;
            }
        }
        view.setAlpha(f);
    }
}

