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
package com.google.android.material.textview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialTextView
extends AppCompatTextView {
    public MaterialTextView(Context context) {
        this(context, null);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, n2), attributeSet, n);
        context = this.getContext();
        if (!MaterialTextView.canApplyTextAppearanceLineHeight(context)) return;
        Resources.Theme theme = context.getTheme();
        if (MaterialTextView.viewAttrsHasLineHeight(context, theme, attributeSet, n, n2)) return;
        if ((n = MaterialTextView.findViewAppearanceResourceId(theme, attributeSet, n, n2)) == -1) return;
        this.applyLineHeightFromViewAppearance(theme, n);
    }

    private void applyLineHeightFromViewAppearance(Resources.Theme theme, int n) {
        theme = theme.obtainStyledAttributes(n, R.styleable.MaterialTextAppearance);
        n = MaterialTextView.readFirstAvailableDimension(this.getContext(), (TypedArray)theme, R.styleable.MaterialTextAppearance_android_lineHeight, R.styleable.MaterialTextAppearance_lineHeight);
        theme.recycle();
        if (n < 0) return;
        this.setLineHeight(n);
    }

    private static boolean canApplyTextAppearanceLineHeight(Context context) {
        return MaterialAttributes.resolveBoolean(context, R.attr.textAppearanceLineHeightEnabled, true);
    }

    private static int findViewAppearanceResourceId(Resources.Theme theme, AttributeSet attributeSet, int n, int n2) {
        theme = theme.obtainStyledAttributes(attributeSet, R.styleable.MaterialTextView, n, n2);
        n = theme.getResourceId(R.styleable.MaterialTextView_android_textAppearance, -1);
        theme.recycle();
        return n;
    }

    private static int readFirstAvailableDimension(Context context, TypedArray typedArray, int ... arrn) {
        int n = 0;
        int n2 = -1;
        while (n < arrn.length) {
            if (n2 >= 0) return n2;
            n2 = MaterialResources.getDimensionPixelSize(context, typedArray, arrn[n], -1);
            ++n;
        }
        return n2;
    }

    private static boolean viewAttrsHasLineHeight(Context context, Resources.Theme theme, AttributeSet attributeSet, int n, int n2) {
        theme = theme.obtainStyledAttributes(attributeSet, R.styleable.MaterialTextView, n, n2);
        n = R.styleable.MaterialTextView_android_lineHeight;
        boolean bl = false;
        n = MaterialTextView.readFirstAvailableDimension(context, (TypedArray)theme, n, R.styleable.MaterialTextView_lineHeight);
        theme.recycle();
        if (n == -1) return bl;
        return true;
    }

    @Override
    public void setTextAppearance(Context context, int n) {
        super.setTextAppearance(context, n);
        if (!MaterialTextView.canApplyTextAppearanceLineHeight(context)) return;
        this.applyLineHeightFromViewAppearance(context.getTheme(), n);
    }
}

