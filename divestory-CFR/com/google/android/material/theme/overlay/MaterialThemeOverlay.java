/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 */
package com.google.android.material.theme.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.view.ContextThemeWrapper;
import com.google.android.material.R;

public class MaterialThemeOverlay {
    private static final int[] ANDROID_THEME_OVERLAY_ATTRS = new int[]{16842752, R.attr.theme};
    private static final int[] MATERIAL_THEME_OVERLAY_ATTR = new int[]{R.attr.materialThemeOverlay};

    private MaterialThemeOverlay() {
    }

    private static int obtainAndroidThemeOverlayId(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, ANDROID_THEME_OVERLAY_ATTRS);
        int n = context.getResourceId(0, 0);
        int n2 = context.getResourceId(1, 0);
        context.recycle();
        if (n == 0) return n2;
        return n;
    }

    private static int obtainMaterialThemeOverlayId(Context context, AttributeSet attributeSet, int n, int n2) {
        context = context.obtainStyledAttributes(attributeSet, MATERIAL_THEME_OVERLAY_ATTR, n, n2);
        n = context.getResourceId(0, 0);
        context.recycle();
        return n;
    }

    public static Context wrap(Context context, AttributeSet attributeSet, int n, int n2) {
        n2 = MaterialThemeOverlay.obtainMaterialThemeOverlayId(context, attributeSet, n, n2);
        n = context instanceof ContextThemeWrapper && ((ContextThemeWrapper)context).getThemeResId() == n2 ? 1 : 0;
        if (n2 == 0) return context;
        if (n != 0) {
            return context;
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, n2);
        n = MaterialThemeOverlay.obtainAndroidThemeOverlayId(context, attributeSet);
        if (n == 0) return contextThemeWrapper;
        contextThemeWrapper.getTheme().applyStyle(n, true);
        return contextThemeWrapper;
    }
}

