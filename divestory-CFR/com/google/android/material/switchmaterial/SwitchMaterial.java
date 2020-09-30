/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.switchmaterial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class SwitchMaterial
extends SwitchCompat {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CompoundButton_Switch;
    private static final int[][] ENABLED_CHECKED_STATES = new int[][]{{16842910, 16842912}, {16842910, -16842912}, {-16842910, 16842912}, {-16842910, -16842912}};
    private final ElevationOverlayProvider elevationOverlayProvider;
    private ColorStateList materialThemeColorsThumbTintList;
    private ColorStateList materialThemeColorsTrackTintList;
    private boolean useMaterialThemeColors;

    public SwitchMaterial(Context context) {
        this(context, null);
    }

    public SwitchMaterial(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchStyle);
    }

    public SwitchMaterial(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        context = this.getContext();
        this.elevationOverlayProvider = new ElevationOverlayProvider(context);
        context = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.SwitchMaterial, n, DEF_STYLE_RES, new int[0]);
        this.useMaterialThemeColors = context.getBoolean(R.styleable.SwitchMaterial_useMaterialThemeColors, false);
        context.recycle();
    }

    private ColorStateList getMaterialThemeColorsThumbTintList() {
        float f;
        if (this.materialThemeColorsThumbTintList != null) return this.materialThemeColorsThumbTintList;
        int n = MaterialColors.getColor((View)this, R.attr.colorSurface);
        int n2 = MaterialColors.getColor((View)this, R.attr.colorControlActivated);
        float f2 = f = this.getResources().getDimension(R.dimen.mtrl_switch_thumb_elevation);
        if (this.elevationOverlayProvider.isThemeElevationOverlayEnabled()) {
            f2 = f + ViewUtils.getParentAbsoluteElevation((View)this);
        }
        int n3 = this.elevationOverlayProvider.compositeOverlayIfNeeded(n, f2);
        int[] arrn = new int[ENABLED_CHECKED_STATES.length];
        arrn[0] = MaterialColors.layer(n, n2, 1.0f);
        arrn[1] = n3;
        arrn[2] = MaterialColors.layer(n, n2, 0.38f);
        arrn[3] = n3;
        this.materialThemeColorsThumbTintList = new ColorStateList(ENABLED_CHECKED_STATES, arrn);
        return this.materialThemeColorsThumbTintList;
    }

    private ColorStateList getMaterialThemeColorsTrackTintList() {
        if (this.materialThemeColorsTrackTintList != null) return this.materialThemeColorsTrackTintList;
        int[] arrn = new int[ENABLED_CHECKED_STATES.length];
        int n = MaterialColors.getColor((View)this, R.attr.colorSurface);
        int n2 = MaterialColors.getColor((View)this, R.attr.colorControlActivated);
        int n3 = MaterialColors.getColor((View)this, R.attr.colorOnSurface);
        arrn[0] = MaterialColors.layer(n, n2, 0.54f);
        arrn[1] = MaterialColors.layer(n, n3, 0.32f);
        arrn[2] = MaterialColors.layer(n, n2, 0.12f);
        arrn[3] = MaterialColors.layer(n, n3, 0.12f);
        this.materialThemeColorsTrackTintList = new ColorStateList(ENABLED_CHECKED_STATES, arrn);
        return this.materialThemeColorsTrackTintList;
    }

    public boolean isUseMaterialThemeColors() {
        return this.useMaterialThemeColors;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && this.getThumbTintList() == null) {
            this.setThumbTintList(this.getMaterialThemeColorsThumbTintList());
        }
        if (!this.useMaterialThemeColors) return;
        if (this.getTrackTintList() != null) return;
        this.setTrackTintList(this.getMaterialThemeColorsTrackTintList());
    }

    public void setUseMaterialThemeColors(boolean bl) {
        this.useMaterialThemeColors = bl;
        if (bl) {
            this.setThumbTintList(this.getMaterialThemeColorsThumbTintList());
            this.setTrackTintList(this.getMaterialThemeColorsTrackTintList());
            return;
        }
        this.setThumbTintList(null);
        this.setTrackTintList(null);
    }
}

