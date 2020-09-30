/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.ViewGroup
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.core.view;

import android.os.Build;
import android.view.ViewGroup;

public final class MarginLayoutParamsCompat {
    private MarginLayoutParamsCompat() {
    }

    public static int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams) {
        int n = Build.VERSION.SDK_INT;
        int n2 = 0;
        n = n >= 17 ? marginLayoutParams.getLayoutDirection() : 0;
        if (n == 0) return n;
        if (n == 1) return n;
        return n2;
    }

    public static int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams) {
        if (Build.VERSION.SDK_INT < 17) return marginLayoutParams.rightMargin;
        return marginLayoutParams.getMarginEnd();
    }

    public static int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams) {
        if (Build.VERSION.SDK_INT < 17) return marginLayoutParams.leftMargin;
        return marginLayoutParams.getMarginStart();
    }

    public static boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams) {
        if (Build.VERSION.SDK_INT < 17) return false;
        return marginLayoutParams.isMarginRelative();
    }

    public static void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int n) {
        if (Build.VERSION.SDK_INT < 17) return;
        marginLayoutParams.resolveLayoutDirection(n);
    }

    public static void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int n) {
        if (Build.VERSION.SDK_INT < 17) return;
        marginLayoutParams.setLayoutDirection(n);
    }

    public static void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int n) {
        if (Build.VERSION.SDK_INT >= 17) {
            marginLayoutParams.setMarginEnd(n);
            return;
        }
        marginLayoutParams.rightMargin = n;
    }

    public static void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int n) {
        if (Build.VERSION.SDK_INT >= 17) {
            marginLayoutParams.setMarginStart(n);
            return;
        }
        marginLayoutParams.leftMargin = n;
    }
}

