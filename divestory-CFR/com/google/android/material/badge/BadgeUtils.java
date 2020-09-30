/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewOverlay
 *  android.widget.FrameLayout
 */
package com.google.android.material.badge;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.widget.FrameLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.ParcelableSparseArray;

public class BadgeUtils {
    public static final boolean USE_COMPAT_PARENT;

    static {
        boolean bl = Build.VERSION.SDK_INT < 18;
        USE_COMPAT_PARENT = bl;
    }

    private BadgeUtils() {
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, View view, FrameLayout frameLayout) {
        BadgeUtils.setBadgeDrawableBounds(badgeDrawable, view, frameLayout);
        if (USE_COMPAT_PARENT) {
            frameLayout.setForeground((Drawable)badgeDrawable);
            return;
        }
        view.getOverlay().add((Drawable)badgeDrawable);
    }

    public static SparseArray<BadgeDrawable> createBadgeDrawablesFromSavedStates(Context context, ParcelableSparseArray parcelableSparseArray) {
        SparseArray sparseArray = new SparseArray(parcelableSparseArray.size());
        int n = 0;
        while (n < parcelableSparseArray.size()) {
            int n2 = parcelableSparseArray.keyAt(n);
            BadgeDrawable.SavedState savedState = (BadgeDrawable.SavedState)parcelableSparseArray.valueAt(n);
            if (savedState == null) throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
            sparseArray.put(n2, (Object)BadgeDrawable.createFromSavedState(context, savedState));
            ++n;
        }
        return sparseArray;
    }

    public static ParcelableSparseArray createParcelableBadgeStates(SparseArray<BadgeDrawable> sparseArray) {
        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
        int n = 0;
        while (n < sparseArray.size()) {
            int n2 = sparseArray.keyAt(n);
            BadgeDrawable badgeDrawable = (BadgeDrawable)sparseArray.valueAt(n);
            if (badgeDrawable == null) throw new IllegalArgumentException("badgeDrawable cannot be null");
            parcelableSparseArray.put(n2, (Object)badgeDrawable.getSavedState());
            ++n;
        }
        return parcelableSparseArray;
    }

    public static void detachBadgeDrawable(BadgeDrawable badgeDrawable, View view, FrameLayout frameLayout) {
        if (badgeDrawable == null) {
            return;
        }
        if (USE_COMPAT_PARENT) {
            frameLayout.setForeground(null);
            return;
        }
        view.getOverlay().remove((Drawable)badgeDrawable);
    }

    public static void setBadgeDrawableBounds(BadgeDrawable badgeDrawable, View view, FrameLayout frameLayout) {
        Rect rect = new Rect();
        Object object = USE_COMPAT_PARENT ? frameLayout : view;
        object.getDrawingRect(rect);
        badgeDrawable.setBounds(rect);
        badgeDrawable.updateBadgeCoordinates(view, (ViewGroup)frameLayout);
    }

    public static void updateBadgeBounds(Rect rect, float f, float f2, float f3, float f4) {
        rect.set((int)(f - f3), (int)(f2 - f4), (int)(f + f3), (int)(f2 + f4));
    }
}

