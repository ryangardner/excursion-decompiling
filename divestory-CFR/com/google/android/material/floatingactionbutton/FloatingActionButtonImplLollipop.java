/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.animation.AnimatorSet$Builder
 *  android.animation.ObjectAnimator
 *  android.animation.StateListAnimator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Property
 *  android.view.View
 */
package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Property;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.floatingactionbutton.BorderDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImpl;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;

class FloatingActionButtonImplLollipop
extends FloatingActionButtonImpl {
    FloatingActionButtonImplLollipop(FloatingActionButton floatingActionButton, ShadowViewDelegate shadowViewDelegate) {
        super(floatingActionButton, shadowViewDelegate);
    }

    private Animator createElevationAnimator(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play((Animator)ObjectAnimator.ofFloat((Object)this.view, (String)"elevation", (float[])new float[]{f}).setDuration(0L)).with((Animator)ObjectAnimator.ofFloat((Object)this.view, (Property)View.TRANSLATION_Z, (float[])new float[]{f2}).setDuration(100L));
        animatorSet.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        return animatorSet;
    }

    BorderDrawable createBorderDrawable(int n, ColorStateList colorStateList) {
        Context context = this.view.getContext();
        BorderDrawable borderDrawable = new BorderDrawable(Preconditions.checkNotNull(this.shapeAppearance));
        borderDrawable.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth(n);
        borderDrawable.setBorderTint(colorStateList);
        return borderDrawable;
    }

    @Override
    MaterialShapeDrawable createShapeDrawable() {
        return new AlwaysStatefulMaterialShapeDrawable(Preconditions.checkNotNull(this.shapeAppearance));
    }

    @Override
    public float getElevation() {
        return this.view.getElevation();
    }

    @Override
    void getPadding(Rect rect) {
        if (this.shadowViewDelegate.isCompatPaddingEnabled()) {
            super.getPadding(rect);
            return;
        }
        if (!this.shouldExpandBoundsForA11y()) {
            int n = (this.minTouchTargetSize - this.view.getSizeDimension()) / 2;
            rect.set(n, n, n, n);
            return;
        }
        rect.set(0, 0, 0, 0);
    }

    @Override
    void initializeBackgroundDrawable(ColorStateList object, PorterDuff.Mode mode, ColorStateList colorStateList, int n) {
        this.shapeDrawable = this.createShapeDrawable();
        this.shapeDrawable.setTintList((ColorStateList)object);
        if (mode != null) {
            this.shapeDrawable.setTintMode(mode);
        }
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        if (n > 0) {
            this.borderDrawable = this.createBorderDrawable(n, (ColorStateList)object);
            object = new LayerDrawable(new Drawable[]{Preconditions.checkNotNull(this.borderDrawable), Preconditions.checkNotNull(this.shapeDrawable)});
        } else {
            this.borderDrawable = null;
            object = this.shapeDrawable;
        }
        this.contentBackground = this.rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(colorStateList), (Drawable)object, null);
    }

    @Override
    void jumpDrawableToCurrentState() {
    }

    @Override
    void onCompatShadowChanged() {
        this.updatePadding();
    }

    @Override
    void onDrawableStateChanged(int[] arrn) {
        if (Build.VERSION.SDK_INT != 21) return;
        if (!this.view.isEnabled()) {
            this.view.setElevation(0.0f);
            this.view.setTranslationZ(0.0f);
            return;
        }
        this.view.setElevation(this.elevation);
        if (this.view.isPressed()) {
            this.view.setTranslationZ(this.pressedTranslationZ);
            return;
        }
        if (!this.view.isFocused() && !this.view.isHovered()) {
            this.view.setTranslationZ(0.0f);
            return;
        }
        this.view.setTranslationZ(this.hoveredFocusedTranslationZ);
    }

    @Override
    void onElevationsChanged(float f, float f2, float f3) {
        if (Build.VERSION.SDK_INT == 21) {
            this.view.refreshDrawableState();
        } else {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, this.createElevationAnimator(f, f3));
            stateListAnimator.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, this.createElevationAnimator(f, f2));
            stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, this.createElevationAnimator(f, f2));
            stateListAnimator.addState(HOVERED_ENABLED_STATE_SET, this.createElevationAnimator(f, f2));
            AnimatorSet animatorSet = new AnimatorSet();
            ArrayList<ObjectAnimator> arrayList = new ArrayList<ObjectAnimator>();
            arrayList.add(ObjectAnimator.ofFloat((Object)this.view, (String)"elevation", (float[])new float[]{f}).setDuration(0L));
            if (Build.VERSION.SDK_INT >= 22 && Build.VERSION.SDK_INT <= 24) {
                arrayList.add(ObjectAnimator.ofFloat((Object)this.view, (Property)View.TRANSLATION_Z, (float[])new float[]{this.view.getTranslationZ()}).setDuration(100L));
            }
            arrayList.add(ObjectAnimator.ofFloat((Object)this.view, (Property)View.TRANSLATION_Z, (float[])new float[]{0.0f}).setDuration(100L));
            animatorSet.playSequentially(arrayList.toArray((T[])new Animator[0]));
            animatorSet.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
            stateListAnimator.addState(ENABLED_STATE_SET, (Animator)animatorSet);
            stateListAnimator.addState(EMPTY_STATE_SET, this.createElevationAnimator(0.0f, 0.0f));
            this.view.setStateListAnimator(stateListAnimator);
        }
        if (!this.shouldAddPadding()) return;
        this.updatePadding();
    }

    @Override
    boolean requirePreDrawListener() {
        return false;
    }

    @Override
    void setRippleColor(ColorStateList colorStateList) {
        if (this.rippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable)this.rippleDrawable).setColor(RippleUtils.sanitizeRippleDrawableColor(colorStateList));
            return;
        }
        super.setRippleColor(colorStateList);
    }

    @Override
    boolean shouldAddPadding() {
        if (this.shadowViewDelegate.isCompatPaddingEnabled()) return true;
        if (!this.shouldExpandBoundsForA11y()) return true;
        return false;
    }

    @Override
    void updateFromViewRotation() {
    }

    static class AlwaysStatefulMaterialShapeDrawable
    extends MaterialShapeDrawable {
        AlwaysStatefulMaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        @Override
        public boolean isStateful() {
            return true;
        }
    }

}

