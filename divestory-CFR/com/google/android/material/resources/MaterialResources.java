/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.resources.TextAppearance;

public class MaterialResources {
    private MaterialResources() {
    }

    public static ColorStateList getColorStateList(Context context, TypedArray typedArray, int n) {
        int n2;
        if (typedArray.hasValue(n) && (n2 = typedArray.getResourceId(n, 0)) != 0 && (context = AppCompatResources.getColorStateList(context, n2)) != null) {
            return context;
        }
        if (Build.VERSION.SDK_INT > 15) return typedArray.getColorStateList(n);
        n2 = typedArray.getColor(n, -1);
        if (n2 == -1) return typedArray.getColorStateList(n);
        return ColorStateList.valueOf((int)n2);
    }

    public static ColorStateList getColorStateList(Context context, TintTypedArray tintTypedArray, int n) {
        int n2;
        if (tintTypedArray.hasValue(n) && (n2 = tintTypedArray.getResourceId(n, 0)) != 0 && (context = AppCompatResources.getColorStateList(context, n2)) != null) {
            return context;
        }
        if (Build.VERSION.SDK_INT > 15) return tintTypedArray.getColorStateList(n);
        n2 = tintTypedArray.getColor(n, -1);
        if (n2 == -1) return tintTypedArray.getColorStateList(n);
        return ColorStateList.valueOf((int)n2);
    }

    public static int getDimensionPixelSize(Context context, TypedArray typedArray, int n, int n2) {
        TypedValue typedValue = new TypedValue();
        if (!typedArray.getValue(n, typedValue)) return typedArray.getDimensionPixelSize(n, n2);
        if (typedValue.type != 2) {
            return typedArray.getDimensionPixelSize(n, n2);
        }
        context = context.getTheme().obtainStyledAttributes(new int[]{typedValue.data});
        n = context.getDimensionPixelSize(0, n2);
        context.recycle();
        return n;
    }

    public static Drawable getDrawable(Context context, TypedArray typedArray, int n) {
        if (!typedArray.hasValue(n)) return typedArray.getDrawable(n);
        int n2 = typedArray.getResourceId(n, 0);
        if (n2 == 0) return typedArray.getDrawable(n);
        if ((context = AppCompatResources.getDrawable(context, n2)) == null) return typedArray.getDrawable(n);
        return context;
    }

    static int getIndexWithValue(TypedArray typedArray, int n, int n2) {
        if (!typedArray.hasValue(n)) return n2;
        return n;
    }

    public static TextAppearance getTextAppearance(Context context, TypedArray typedArray, int n) {
        if (!typedArray.hasValue(n)) return null;
        if ((n = typedArray.getResourceId(n, 0)) == 0) return null;
        return new TextAppearance(context, n);
    }
}

