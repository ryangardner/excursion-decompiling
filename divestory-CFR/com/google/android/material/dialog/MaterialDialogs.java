/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.material.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;

public class MaterialDialogs {
    private MaterialDialogs() {
    }

    public static Rect getDialogBackgroundInsets(Context context, int n, int n2) {
        TypedArray typedArray = ThemeEnforcement.obtainStyledAttributes(context, null, R.styleable.MaterialAlertDialog, n, n2, new int[0]);
        int n3 = typedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetStart, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_start));
        int n4 = typedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetTop, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_top));
        n2 = typedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetEnd, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_end));
        int n5 = typedArray.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetBottom, context.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_bottom));
        typedArray.recycle();
        int n6 = n2;
        n = n3;
        if (Build.VERSION.SDK_INT < 17) return new Rect(n, n4, n6, n5);
        n6 = n2;
        n = n3;
        if (context.getResources().getConfiguration().getLayoutDirection() != 1) return new Rect(n, n4, n6, n5);
        n = n2;
        n6 = n3;
        return new Rect(n, n4, n6, n5);
    }

    public static InsetDrawable insetDrawable(Drawable drawable2, Rect rect) {
        return new InsetDrawable(drawable2, rect.left, rect.top, rect.right, rect.bottom);
    }
}

