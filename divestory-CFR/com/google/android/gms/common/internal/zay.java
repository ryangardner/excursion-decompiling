/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.widget.Button
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.gms.base.R;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DeviceProperties;

public final class zay
extends Button {
    public zay(Context context) {
        this(context, null);
    }

    private zay(Context context, AttributeSet attributeSet) {
        super(context, null, 16842824);
    }

    private static int zaa(int n, int n2, int n3, int n4) {
        if (n == 0) return n2;
        if (n == 1) return n3;
        if (n == 2) {
            return n4;
        }
        StringBuilder stringBuilder = new StringBuilder(33);
        stringBuilder.append("Unknown color scheme: ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public final void zaa(Resources object, int n, int n2) {
        this.setTypeface(Typeface.DEFAULT_BOLD);
        this.setTextSize(14.0f);
        int n3 = (int)(object.getDisplayMetrics().density * 48.0f + 0.5f);
        this.setMinHeight(n3);
        this.setMinWidth(n3);
        int n4 = zay.zaa(n2, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light);
        n3 = zay.zaa(n2, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light);
        if (n != 0 && n != 1) {
            if (n != 2) {
                object = new StringBuilder(32);
                ((StringBuilder)object).append("Unknown button size: ");
                ((StringBuilder)object).append(n);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            n3 = n4;
        }
        Drawable drawable2 = DrawableCompat.wrap(object.getDrawable(n3));
        DrawableCompat.setTintList(drawable2, object.getColorStateList(R.color.common_google_signin_btn_tint));
        DrawableCompat.setTintMode(drawable2, PorterDuff.Mode.SRC_ATOP);
        this.setBackgroundDrawable(drawable2);
        this.setTextColor(Preconditions.checkNotNull(object.getColorStateList(zay.zaa(n2, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light))));
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    object = new StringBuilder(32);
                    ((StringBuilder)object).append("Unknown button size: ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                this.setText(null);
            } else {
                this.setText((CharSequence)object.getString(R.string.common_signin_button_text_long));
            }
        } else {
            this.setText((CharSequence)object.getString(R.string.common_signin_button_text));
        }
        this.setTransformationMethod(null);
        if (!DeviceProperties.isWearable(this.getContext())) return;
        this.setGravity(19);
    }
}

