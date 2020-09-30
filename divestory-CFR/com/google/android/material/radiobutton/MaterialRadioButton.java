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
package com.google.android.material.radiobutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.widget.CompoundButtonCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialRadioButton
extends AppCompatRadioButton {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CompoundButton_RadioButton;
    private static final int[][] ENABLED_CHECKED_STATES;
    private ColorStateList materialThemeColorsTintList;
    private boolean useMaterialThemeColors;

    static {
        int[] arrn = new int[]{16842910, -16842912};
        int[] arrn2 = new int[]{-16842910, 16842912};
        ENABLED_CHECKED_STATES = new int[][]{{16842910, 16842912}, arrn, arrn2, {-16842910, -16842912}};
    }

    public MaterialRadioButton(Context context) {
        this(context, null);
    }

    public MaterialRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.radioButtonStyle);
    }

    public MaterialRadioButton(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        context = this.getContext();
        attributeSet = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.MaterialRadioButton, n, DEF_STYLE_RES, new int[0]);
        if (attributeSet.hasValue(R.styleable.MaterialRadioButton_buttonTint)) {
            CompoundButtonCompat.setButtonTintList((CompoundButton)this, MaterialResources.getColorStateList(context, (TypedArray)attributeSet, R.styleable.MaterialRadioButton_buttonTint));
        }
        this.useMaterialThemeColors = attributeSet.getBoolean(R.styleable.MaterialRadioButton_useMaterialThemeColors, false);
        attributeSet.recycle();
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.materialThemeColorsTintList != null) return this.materialThemeColorsTintList;
        int n = MaterialColors.getColor((View)this, R.attr.colorControlActivated);
        int n2 = MaterialColors.getColor((View)this, R.attr.colorOnSurface);
        int n3 = MaterialColors.getColor((View)this, R.attr.colorSurface);
        int[] arrn = new int[ENABLED_CHECKED_STATES.length];
        arrn[0] = MaterialColors.layer(n3, n, 1.0f);
        arrn[1] = MaterialColors.layer(n3, n2, 0.54f);
        arrn[2] = MaterialColors.layer(n3, n2, 0.38f);
        arrn[3] = MaterialColors.layer(n3, n2, 0.38f);
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

