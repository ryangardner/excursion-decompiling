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
package com.google.android.material.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.R;

public final class ThemeEnforcement {
    private static final int[] APPCOMPAT_CHECK_ATTRS = new int[]{R.attr.colorPrimary};
    private static final String APPCOMPAT_THEME_NAME = "Theme.AppCompat";
    private static final int[] MATERIAL_CHECK_ATTRS = new int[]{R.attr.colorPrimaryVariant};
    private static final String MATERIAL_THEME_NAME = "Theme.MaterialComponents";

    private ThemeEnforcement() {
    }

    public static void checkAppCompatTheme(Context context) {
        ThemeEnforcement.checkTheme(context, APPCOMPAT_CHECK_ATTRS, APPCOMPAT_THEME_NAME);
    }

    private static void checkCompatibleTheme(Context context, AttributeSet object, int n, int n2) {
        object = context.obtainStyledAttributes(object, R.styleable.ThemeEnforcement, n, n2);
        boolean bl = object.getBoolean(R.styleable.ThemeEnforcement_enforceMaterialTheme, false);
        object.recycle();
        if (bl) {
            object = new TypedValue();
            if (!context.getTheme().resolveAttribute(R.attr.isMaterialTheme, (TypedValue)object, true) || object.type == 18 && object.data == 0) {
                ThemeEnforcement.checkMaterialTheme(context);
            }
        }
        ThemeEnforcement.checkAppCompatTheme(context);
    }

    public static void checkMaterialTheme(Context context) {
        ThemeEnforcement.checkTheme(context, MATERIAL_CHECK_ATTRS, MATERIAL_THEME_NAME);
    }

    private static void checkTextAppearance(Context context, AttributeSet attributeSet, int[] arrn, int n, int n2, int ... arrn2) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ThemeEnforcement, n, n2);
        int n3 = R.styleable.ThemeEnforcement_enforceTextAppearance;
        boolean bl = false;
        if (!typedArray.getBoolean(n3, false)) {
            typedArray.recycle();
            return;
        }
        if (arrn2 != null && arrn2.length != 0) {
            bl = ThemeEnforcement.isCustomTextAppearanceValid(context, attributeSet, arrn, n, n2, arrn2);
        } else if (typedArray.getResourceId(R.styleable.ThemeEnforcement_android_textAppearance, -1) != -1) {
            bl = true;
        }
        typedArray.recycle();
        if (!bl) throw new IllegalArgumentException("This component requires that you specify a valid TextAppearance attribute. Update your app theme to inherit from Theme.MaterialComponents (or a descendant).");
    }

    private static void checkTheme(Context object, int[] arrn, String string2) {
        if (ThemeEnforcement.isTheme((Context)object, arrn)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The style on this component requires your app theme to be ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" (or a descendant).");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static boolean isAppCompatTheme(Context context) {
        return ThemeEnforcement.isTheme(context, APPCOMPAT_CHECK_ATTRS);
    }

    private static boolean isCustomTextAppearanceValid(Context context, AttributeSet attributeSet, int[] arrn, int n, int n2, int ... arrn2) {
        context = context.obtainStyledAttributes(attributeSet, arrn, n, n2);
        n2 = arrn2.length;
        n = 0;
        do {
            if (n >= n2) {
                context.recycle();
                return true;
            }
            if (context.getResourceId(arrn2[n], -1) == -1) {
                context.recycle();
                return false;
            }
            ++n;
        } while (true);
    }

    public static boolean isMaterialTheme(Context context) {
        return ThemeEnforcement.isTheme(context, MATERIAL_CHECK_ATTRS);
    }

    private static boolean isTheme(Context context, int[] arrn) {
        context = context.obtainStyledAttributes(arrn);
        int n = 0;
        do {
            if (n >= arrn.length) {
                context.recycle();
                return true;
            }
            if (!context.hasValue(n)) {
                context.recycle();
                return false;
            }
            ++n;
        } while (true);
    }

    public static TypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] arrn, int n, int n2, int ... arrn2) {
        ThemeEnforcement.checkCompatibleTheme(context, attributeSet, n, n2);
        ThemeEnforcement.checkTextAppearance(context, attributeSet, arrn, n, n2, arrn2);
        return context.obtainStyledAttributes(attributeSet, arrn, n, n2);
    }

    public static TintTypedArray obtainTintedStyledAttributes(Context context, AttributeSet attributeSet, int[] arrn, int n, int n2, int ... arrn2) {
        ThemeEnforcement.checkCompatibleTheme(context, attributeSet, n, n2);
        ThemeEnforcement.checkTextAppearance(context, attributeSet, arrn, n, n2, arrn2);
        return TintTypedArray.obtainStyledAttributes(context, attributeSet, arrn, n, n2);
    }
}

