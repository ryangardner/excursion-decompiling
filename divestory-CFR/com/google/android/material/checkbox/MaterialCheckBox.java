/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.CompoundButton
 */
package com.google.android.material.checkbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.widget.CompoundButtonCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialCheckBox
extends AppCompatCheckBox {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CompoundButton_CheckBox;
    private static final int[][] ENABLED_CHECKED_STATES = new int[][]{{16842910, 16842912}, {16842910, -16842912}, {-16842910, 16842912}, {-16842910, -16842912}};
    private ColorStateList materialThemeColorsTintList;
    private boolean useMaterialThemeColors;

    public MaterialCheckBox(Context context) {
        this(context, null);
    }

    public MaterialCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkboxStyle);
    }

    public MaterialCheckBox(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        context = this.getContext();
        attributeSet = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.MaterialCheckBox, n, DEF_STYLE_RES, new int[0]);
        if (attributeSet.hasValue(R.styleable.MaterialCheckBox_buttonTint)) {
            CompoundButtonCompat.setButtonTintList((CompoundButton)this, MaterialResources.getColorStateList(context, (TypedArray)attributeSet, R.styleable.MaterialCheckBox_buttonTint));
        }
        this.useMaterialThemeColors = attributeSet.getBoolean(R.styleable.MaterialCheckBox_useMaterialThemeColors, false);
        attributeSet.recycle();
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.materialThemeColorsTintList != null) return this.materialThemeColorsTintList;
        int[] arrn = new int[ENABLED_CHECKED_STATES.length];
        int n = MaterialColors.getColor((View)this, R.attr.colorControlActivated);
        int n2 = MaterialColors.getColor((View)this, R.attr.colorSurface);
        int n3 = MaterialColors.getColor((View)this, R.attr.colorOnSurface);
        arrn[0] = MaterialColors.layer(n2, n, 1.0f);
        arrn[1] = MaterialColors.layer(n2, n3, 0.54f);
        arrn[2] = MaterialColors.layer(n2, n3, 0.38f);
        arrn[3] = MaterialColors.layer(n2, n3, 0.38f);
        this.materialThemeColorsTintList = new ColorStateList(ENABLED_CHECKED_STATES, arrn);
        return this.materialThemeColorsTintList;
    }

    public boolean isUseMaterialThemeColors() {
        return this.useMaterialThemeColors;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.useMaterialThemeColors) return;
        if (CompoundButtonCompat.getButtonTintList((CompoundButton)this) != null) return;
        this.setUseMaterialThemeColors(true);
    }

    public void setUseMaterialThemeColors(boolean bl) {
        this.useMaterialThemeColors = bl;
        if (bl) {
            CompoundButtonCompat.setButtonTintList((CompoundButton)this, this.getMaterialThemeColorsTintList());
            return;
        }
        CompoundButtonCompat.setButtonTintList((CompoundButton)this, null);
    }
}

