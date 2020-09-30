/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatImageHelper;
import androidx.collection.SimpleArrayMap;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.TintableBackgroundView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TintableImageSourceView;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.expandable.ExpandableTransformationWidget;
import com.google.android.material.expandable.ExpandableWidget;
import com.google.android.material.expandable.ExpandableWidgetHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButtonImplLollipop;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.internal.VisibilityAwareImageButton;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.stateful.ExtendableSavedState;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class FloatingActionButton
extends VisibilityAwareImageButton
implements TintableBackgroundView,
TintableImageSourceView,
ExpandableTransformationWidget,
Shapeable,
CoordinatorLayout.AttachedBehavior {
    private static final int AUTO_MINI_LARGEST_SCREEN_WIDTH = 470;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_FloatingActionButton;
    private static final String EXPANDABLE_WIDGET_HELPER_KEY = "expandableWidgetHelper";
    private static final String LOG_TAG = "FloatingActionButton";
    public static final int NO_CUSTOM_SIZE = 0;
    public static final int SIZE_AUTO = -1;
    public static final int SIZE_MINI = 1;
    public static final int SIZE_NORMAL = 0;
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    private int borderWidth;
    boolean compatPadding;
    private int customSize;
    private final ExpandableWidgetHelper expandableWidgetHelper;
    private final AppCompatImageHelper imageHelper;
    private PorterDuff.Mode imageMode;
    private int imagePadding;
    private ColorStateList imageTint;
    private FloatingActionButtonImpl impl;
    private int maxImageSize;
    private ColorStateList rippleColor;
    final Rect shadowPadding = new Rect();
    private int size;
    private final Rect touchArea = new Rect();

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.floatingActionButtonStyle);
    }

    public FloatingActionButton(Context object, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(object, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        Object object2 = this.getContext();
        Object object3 = ThemeEnforcement.obtainStyledAttributes(object2, attributeSet, R.styleable.FloatingActionButton, n, DEF_STYLE_RES, new int[0]);
        this.backgroundTint = MaterialResources.getColorStateList(object2, (TypedArray)object3, R.styleable.FloatingActionButton_backgroundTint);
        this.backgroundTintMode = ViewUtils.parseTintMode(object3.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.rippleColor = MaterialResources.getColorStateList(object2, (TypedArray)object3, R.styleable.FloatingActionButton_rippleColor);
        this.size = object3.getInt(R.styleable.FloatingActionButton_fabSize, -1);
        this.customSize = object3.getDimensionPixelSize(R.styleable.FloatingActionButton_fabCustomSize, 0);
        this.borderWidth = object3.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float f = object3.getDimension(R.styleable.FloatingActionButton_elevation, 0.0f);
        float f2 = object3.getDimension(R.styleable.FloatingActionButton_hoveredFocusedTranslationZ, 0.0f);
        float f3 = object3.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        this.compatPadding = object3.getBoolean(R.styleable.FloatingActionButton_useCompatPadding, false);
        int n2 = this.getResources().getDimensionPixelSize(R.dimen.mtrl_fab_min_touch_target);
        this.maxImageSize = object3.getDimensionPixelSize(R.styleable.FloatingActionButton_maxImageSize, 0);
        MotionSpec motionSpec = MotionSpec.createFromAttribute(object2, (TypedArray)object3, R.styleable.FloatingActionButton_showMotionSpec);
        object = MotionSpec.createFromAttribute(object2, (TypedArray)object3, R.styleable.FloatingActionButton_hideMotionSpec);
        object2 = ShapeAppearanceModel.builder(object2, attributeSet, n, DEF_STYLE_RES, ShapeAppearanceModel.PILL).build();
        boolean bl = object3.getBoolean(R.styleable.FloatingActionButton_ensureMinTouchTargetSize, false);
        this.setEnabled(object3.getBoolean(R.styleable.FloatingActionButton_android_enabled, true));
        object3.recycle();
        object3 = new AppCompatImageHelper((ImageView)this);
        this.imageHelper = object3;
        ((AppCompatImageHelper)object3).loadFromAttributes(attributeSet, n);
        this.expandableWidgetHelper = new ExpandableWidgetHelper(this);
        this.getImpl().setShapeAppearance((ShapeAppearanceModel)object2);
        this.getImpl().initializeBackgroundDrawable(this.backgroundTint, this.backgroundTintMode, this.rippleColor, this.borderWidth);
        this.getImpl().setMinTouchTargetSize(n2);
        this.getImpl().setElevation(f);
        this.getImpl().setHoveredFocusedTranslationZ(f2);
        this.getImpl().setPressedTranslationZ(f3);
        this.getImpl().setMaxImageSize(this.maxImageSize);
        this.getImpl().setShowMotionSpec(motionSpec);
        this.getImpl().setHideMotionSpec((MotionSpec)object);
        this.getImpl().setEnsureMinTouchTargetSize(bl);
        this.setScaleType(ImageView.ScaleType.MATRIX);
    }

    private FloatingActionButtonImpl createImpl() {
        if (Build.VERSION.SDK_INT < 21) return new FloatingActionButtonImpl(this, new ShadowDelegateImpl());
        return new FloatingActionButtonImplLollipop(this, new ShadowDelegateImpl());
    }

    private FloatingActionButtonImpl getImpl() {
        if (this.impl != null) return this.impl;
        this.impl = this.createImpl();
        return this.impl;
    }

    private int getSizeDimension(int n) {
        int n2 = this.customSize;
        if (n2 != 0) {
            return n2;
        }
        Resources resources = this.getResources();
        if (n != -1) {
            if (n == 1) return resources.getDimensionPixelSize(R.dimen.design_fab_size_mini);
            return resources.getDimensionPixelSize(R.dimen.design_fab_size_normal);
        }
        if (Math.max(resources.getConfiguration().screenWidthDp, resources.getConfiguration().screenHeightDp) >= 470) return this.getSizeDimension(0);
        return this.getSizeDimension(1);
    }

    private void offsetRectWithShadow(Rect rect) {
        rect.left += this.shadowPadding.left;
        rect.top += this.shadowPadding.top;
        rect.right -= this.shadowPadding.right;
        rect.bottom -= this.shadowPadding.bottom;
    }

    private void onApplySupportImageTint() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 == null) {
            return;
        }
        ColorStateList colorStateList = this.imageTint;
        if (colorStateList == null) {
            DrawableCompat.clearColorFilter(drawable2);
            return;
        }
        int n = colorStateList.getColorForState(this.getDrawableState(), 0);
        PorterDuff.Mode mode = this.imageMode;
        colorStateList = mode;
        if (mode == null) {
            colorStateList = PorterDuff.Mode.SRC_IN;
        }
        drawable2.mutate().setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(n, (PorterDuff.Mode)colorStateList));
    }

    private static int resolveAdjustedSize(int n, int n2) {
        int n3 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        if (n3 == Integer.MIN_VALUE) return Math.min(n, n2);
        if (n3 == 0) return n;
        if (n3 != 1073741824) throw new IllegalArgumentException();
        return n2;
    }

    private FloatingActionButtonImpl.InternalVisibilityChangedListener wrapOnVisibilityChangedListener(final OnVisibilityChangedListener onVisibilityChangedListener) {
        if (onVisibilityChangedListener != null) return new FloatingActionButtonImpl.InternalVisibilityChangedListener(){

            @Override
            public void onHidden() {
                onVisibilityChangedListener.onHidden(FloatingActionButton.this);
            }

            @Override
            public void onShown() {
                onVisibilityChangedListener.onShown(FloatingActionButton.this);
            }
        };
        return null;
    }

    public void addOnHideAnimationListener(Animator.AnimatorListener animatorListener) {
        this.getImpl().addOnHideAnimationListener(animatorListener);
    }

    public void addOnShowAnimationListener(Animator.AnimatorListener animatorListener) {
        this.getImpl().addOnShowAnimationListener(animatorListener);
    }

    public void addTransformationCallback(TransformationCallback<? extends FloatingActionButton> transformationCallback) {
        this.getImpl().addTransformationCallback(new TransformationCallbackWrapper<FloatingActionButton>(transformationCallback));
    }

    public void clearCustomSize() {
        this.setCustomSize(0);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.getImpl().onDrawableStateChanged(this.getDrawableState());
    }

    public ColorStateList getBackgroundTintList() {
        return this.backgroundTint;
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return this.backgroundTintMode;
    }

    @Override
    public CoordinatorLayout.Behavior<FloatingActionButton> getBehavior() {
        return new Behavior();
    }

    public float getCompatElevation() {
        return this.getImpl().getElevation();
    }

    public float getCompatHoveredFocusedTranslationZ() {
        return this.getImpl().getHoveredFocusedTranslationZ();
    }

    public float getCompatPressedTranslationZ() {
        return this.getImpl().getPressedTranslationZ();
    }

    public Drawable getContentBackground() {
        return this.getImpl().getContentBackground();
    }

    @Deprecated
    public boolean getContentRect(Rect rect) {
        if (!ViewCompat.isLaidOut((View)this)) return false;
        rect.set(0, 0, this.getWidth(), this.getHeight());
        this.offsetRectWithShadow(rect);
        return true;
    }

    public int getCustomSize() {
        return this.customSize;
    }

    @Override
    public int getExpandedComponentIdHint() {
        return this.expandableWidgetHelper.getExpandedComponentIdHint();
    }

    public MotionSpec getHideMotionSpec() {
        return this.getImpl().getHideMotionSpec();
    }

    public void getMeasuredContentRect(Rect rect) {
        rect.set(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
        this.offsetRectWithShadow(rect);
    }

    @Deprecated
    public int getRippleColor() {
        ColorStateList colorStateList = this.rippleColor;
        if (colorStateList == null) return 0;
        return colorStateList.getDefaultColor();
    }

    public ColorStateList getRippleColorStateList() {
        return this.rippleColor;
    }

    @Override
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return Preconditions.checkNotNull(this.getImpl().getShapeAppearance());
    }

    public MotionSpec getShowMotionSpec() {
        return this.getImpl().getShowMotionSpec();
    }

    public int getSize() {
        return this.size;
    }

    int getSizeDimension() {
        return this.getSizeDimension(this.size);
    }

    @Override
    public ColorStateList getSupportBackgroundTintList() {
        return this.getBackgroundTintList();
    }

    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return this.getBackgroundTintMode();
    }

    @Override
    public ColorStateList getSupportImageTintList() {
        return this.imageTint;
    }

    @Override
    public PorterDuff.Mode getSupportImageTintMode() {
        return this.imageMode;
    }

    public boolean getUseCompatPadding() {
        return this.compatPadding;
    }

    public void hide() {
        this.hide(null);
    }

    public void hide(OnVisibilityChangedListener onVisibilityChangedListener) {
        this.hide(onVisibilityChangedListener, true);
    }

    void hide(OnVisibilityChangedListener onVisibilityChangedListener, boolean bl) {
        this.getImpl().hide(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl);
    }

    @Override
    public boolean isExpanded() {
        return this.expandableWidgetHelper.isExpanded();
    }

    public boolean isOrWillBeHidden() {
        return this.getImpl().isOrWillBeHidden();
    }

    public boolean isOrWillBeShown() {
        return this.getImpl().isOrWillBeShown();
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.getImpl().jumpDrawableToCurrentState();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getImpl().onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getImpl().onDetachedFromWindow();
    }

    protected void onMeasure(int n, int n2) {
        int n3 = this.getSizeDimension();
        this.imagePadding = (n3 - this.maxImageSize) / 2;
        this.getImpl().updatePadding();
        n = Math.min(FloatingActionButton.resolveAdjustedSize(n3, n), FloatingActionButton.resolveAdjustedSize(n3, n2));
        this.setMeasuredDimension(this.shadowPadding.left + n + this.shadowPadding.right, n + this.shadowPadding.top + this.shadowPadding.bottom);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof ExtendableSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (ExtendableSavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.expandableWidgetHelper.onRestoreInstanceState(Preconditions.checkNotNull(parcelable.extendableStates.get(EXPANDABLE_WIDGET_HELPER_KEY)));
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable;
        Parcelable parcelable2 = parcelable = super.onSaveInstanceState();
        if (parcelable == null) {
            parcelable2 = new Bundle();
        }
        parcelable2 = new ExtendableSavedState(parcelable2);
        parcelable2.extendableStates.put(EXPANDABLE_WIDGET_HELPER_KEY, this.expandableWidgetHelper.onSaveInstanceState());
        return parcelable2;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) return super.onTouchEvent(motionEvent);
        if (!this.getContentRect(this.touchArea)) return super.onTouchEvent(motionEvent);
        if (this.touchArea.contains((int)motionEvent.getX(), (int)motionEvent.getY())) return super.onTouchEvent(motionEvent);
        return false;
    }

    public void removeOnHideAnimationListener(Animator.AnimatorListener animatorListener) {
        this.getImpl().removeOnHideAnimationListener(animatorListener);
    }

    public void removeOnShowAnimationListener(Animator.AnimatorListener animatorListener) {
        this.getImpl().removeOnShowAnimationListener(animatorListener);
    }

    public void removeTransformationCallback(TransformationCallback<? extends FloatingActionButton> transformationCallback) {
        this.getImpl().removeTransformationCallback(new TransformationCallbackWrapper<FloatingActionButton>(transformationCallback));
    }

    public void setBackgroundColor(int n) {
        Log.i((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        Log.i((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundResource(int n) {
        Log.i((String)LOG_TAG, (String)"Setting a custom background is not supported.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.backgroundTint == colorStateList) return;
        this.backgroundTint = colorStateList;
        this.getImpl().setBackgroundTintList(colorStateList);
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.backgroundTintMode == mode) return;
        this.backgroundTintMode = mode;
        this.getImpl().setBackgroundTintMode(mode);
    }

    public void setCompatElevation(float f) {
        this.getImpl().setElevation(f);
    }

    public void setCompatElevationResource(int n) {
        this.setCompatElevation(this.getResources().getDimension(n));
    }

    public void setCompatHoveredFocusedTranslationZ(float f) {
        this.getImpl().setHoveredFocusedTranslationZ(f);
    }

    public void setCompatHoveredFocusedTranslationZResource(int n) {
        this.setCompatHoveredFocusedTranslationZ(this.getResources().getDimension(n));
    }

    public void setCompatPressedTranslationZ(float f) {
        this.getImpl().setPressedTranslationZ(f);
    }

    public void setCompatPressedTranslationZResource(int n) {
        this.setCompatPressedTranslationZ(this.getResources().getDimension(n));
    }

    public void setCustomSize(int n) {
        if (n < 0) throw new IllegalArgumentException("Custom size must be non-negative");
        if (n == this.customSize) return;
        this.customSize = n;
        this.requestLayout();
    }

    public void setElevation(float f) {
        super.setElevation(f);
        this.getImpl().updateShapeElevation(f);
    }

    public void setEnsureMinTouchTargetSize(boolean bl) {
        if (bl == this.getImpl().getEnsureMinTouchTargetSize()) return;
        this.getImpl().setEnsureMinTouchTargetSize(bl);
        this.requestLayout();
    }

    @Override
    public boolean setExpanded(boolean bl) {
        return this.expandableWidgetHelper.setExpanded(bl);
    }

    @Override
    public void setExpandedComponentIdHint(int n) {
        this.expandableWidgetHelper.setExpandedComponentIdHint(n);
    }

    public void setHideMotionSpec(MotionSpec motionSpec) {
        this.getImpl().setHideMotionSpec(motionSpec);
    }

    public void setHideMotionSpecResource(int n) {
        this.setHideMotionSpec(MotionSpec.createFromResource(this.getContext(), n));
    }

    public void setImageDrawable(Drawable drawable2) {
        if (this.getDrawable() == drawable2) return;
        super.setImageDrawable(drawable2);
        this.getImpl().updateImageMatrixScale();
        if (this.imageTint == null) return;
        this.onApplySupportImageTint();
    }

    public void setImageResource(int n) {
        this.imageHelper.setImageResource(n);
        this.onApplySupportImageTint();
    }

    public void setRippleColor(int n) {
        this.setRippleColor(ColorStateList.valueOf((int)n));
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (this.rippleColor == colorStateList) return;
        this.rippleColor = colorStateList;
        this.getImpl().setRippleColor(this.rippleColor);
    }

    public void setScaleX(float f) {
        super.setScaleX(f);
        this.getImpl().onScaleChanged();
    }

    public void setScaleY(float f) {
        super.setScaleY(f);
        this.getImpl().onScaleChanged();
    }

    public void setShadowPaddingEnabled(boolean bl) {
        this.getImpl().setShadowPaddingEnabled(bl);
    }

    @Override
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.getImpl().setShapeAppearance(shapeAppearanceModel);
    }

    public void setShowMotionSpec(MotionSpec motionSpec) {
        this.getImpl().setShowMotionSpec(motionSpec);
    }

    public void setShowMotionSpecResource(int n) {
        this.setShowMotionSpec(MotionSpec.createFromResource(this.getContext(), n));
    }

    public void setSize(int n) {
        this.customSize = 0;
        if (n == this.size) return;
        this.size = n;
        this.requestLayout();
    }

    @Override
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        this.setBackgroundTintList(colorStateList);
    }

    @Override
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        this.setBackgroundTintMode(mode);
    }

    @Override
    public void setSupportImageTintList(ColorStateList colorStateList) {
        if (this.imageTint == colorStateList) return;
        this.imageTint = colorStateList;
        this.onApplySupportImageTint();
    }

    @Override
    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (this.imageMode == mode) return;
        this.imageMode = mode;
        this.onApplySupportImageTint();
    }

    public void setTranslationX(float f) {
        super.setTranslationX(f);
        this.getImpl().onTranslationChanged();
    }

    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.getImpl().onTranslationChanged();
    }

    public void setTranslationZ(float f) {
        super.setTranslationZ(f);
        this.getImpl().onTranslationChanged();
    }

    public void setUseCompatPadding(boolean bl) {
        if (this.compatPadding == bl) return;
        this.compatPadding = bl;
        this.getImpl().onCompatShadowChanged();
    }

    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
    }

    public boolean shouldEnsureMinTouchTargetSize() {
        return this.getImpl().getEnsureMinTouchTargetSize();
    }

    public void show() {
        this.show(null);
    }

    public void show(OnVisibilityChangedListener onVisibilityChangedListener) {
        this.show(onVisibilityChangedListener, true);
    }

    void show(OnVisibilityChangedListener onVisibilityChangedListener, boolean bl) {
        this.getImpl().show(this.wrapOnVisibilityChangedListener(onVisibilityChangedListener), bl);
    }

    protected static class BaseBehavior<T extends FloatingActionButton>
    extends CoordinatorLayout.Behavior<T> {
        private static final boolean AUTO_HIDE_DEFAULT = true;
        private boolean autoHideEnabled;
        private OnVisibilityChangedListener internalAutoHideListener;
        private Rect tmpRect;

        public BaseBehavior() {
            this.autoHideEnabled = true;
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = context.getBoolean(R.styleable.FloatingActionButton_Behavior_Layout_behavior_autoHide, true);
            context.recycle();
        }

        private static boolean isBottomSheet(View view) {
            if (!((view = view.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams)) return false;
            return ((CoordinatorLayout.LayoutParams)view).getBehavior() instanceof BottomSheetBehavior;
        }

        private void offsetIfNeeded(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            Rect rect = floatingActionButton.shadowPadding;
            if (rect == null) return;
            if (rect.centerX() <= 0) return;
            if (rect.centerY() <= 0) return;
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
            int n = floatingActionButton.getRight();
            int n2 = coordinatorLayout.getWidth();
            int n3 = layoutParams.rightMargin;
            int n4 = 0;
            n2 = n >= n2 - n3 ? rect.right : (floatingActionButton.getLeft() <= layoutParams.leftMargin ? -rect.left : 0);
            if (floatingActionButton.getBottom() >= coordinatorLayout.getHeight() - layoutParams.bottomMargin) {
                n4 = rect.bottom;
            } else if (floatingActionButton.getTop() <= layoutParams.topMargin) {
                n4 = -rect.top;
            }
            if (n4 != 0) {
                ViewCompat.offsetTopAndBottom((View)floatingActionButton, n4);
            }
            if (n2 == 0) return;
            ViewCompat.offsetLeftAndRight((View)floatingActionButton, n2);
        }

        private boolean shouldUpdateVisibility(View view, FloatingActionButton floatingActionButton) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
            if (!this.autoHideEnabled) {
                return false;
            }
            if (layoutParams.getAnchorId() != view.getId()) {
                return false;
            }
            if (floatingActionButton.getUserSetVisibility() == 0) return true;
            return false;
        }

        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, FloatingActionButton floatingActionButton) {
            if (!this.shouldUpdateVisibility((View)appBarLayout, floatingActionButton)) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(coordinatorLayout, (View)appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                floatingActionButton.hide(this.internalAutoHideListener, false);
                return true;
            }
            floatingActionButton.show(this.internalAutoHideListener, false);
            return true;
        }

        private boolean updateFabVisibilityForBottomSheet(View view, FloatingActionButton floatingActionButton) {
            if (!this.shouldUpdateVisibility(view, floatingActionButton)) {
                return false;
            }
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)floatingActionButton.getLayoutParams();
            if (view.getTop() < floatingActionButton.getHeight() / 2 + layoutParams.topMargin) {
                floatingActionButton.hide(this.internalAutoHideListener, false);
                return true;
            }
            floatingActionButton.show(this.internalAutoHideListener, false);
            return true;
        }

        @Override
        public boolean getInsetDodgeRect(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, Rect rect) {
            coordinatorLayout = floatingActionButton.shadowPadding;
            rect.set(floatingActionButton.getLeft() + ((Rect)coordinatorLayout).left, floatingActionButton.getTop() + ((Rect)coordinatorLayout).top, floatingActionButton.getRight() - ((Rect)coordinatorLayout).right, floatingActionButton.getBottom() - ((Rect)coordinatorLayout).bottom);
            return true;
        }

        public boolean isAutoHideEnabled() {
            return this.autoHideEnabled;
        }

        @Override
        public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges != 0) return;
            layoutParams.dodgeInsetEdges = 80;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, floatingActionButton);
                return false;
            }
            if (!BaseBehavior.isBottomSheet(view)) return false;
            this.updateFabVisibilityForBottomSheet(view, floatingActionButton);
            return false;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int n) {
            View view;
            List<View> list = coordinatorLayout.getDependencies((View)floatingActionButton);
            int n2 = list.size();
            for (int i = 0; i < n2 && !((view = list.get(i)) instanceof AppBarLayout ? this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, floatingActionButton) : BaseBehavior.isBottomSheet(view) && this.updateFabVisibilityForBottomSheet(view, floatingActionButton)); ++i) {
            }
            coordinatorLayout.onLayoutChild((View)floatingActionButton, n);
            this.offsetIfNeeded(coordinatorLayout, floatingActionButton);
            return true;
        }

        public void setAutoHideEnabled(boolean bl) {
            this.autoHideEnabled = bl;
        }

        public void setInternalAutoHideListener(OnVisibilityChangedListener onVisibilityChangedListener) {
            this.internalAutoHideListener = onVisibilityChangedListener;
        }
    }

    public static class Behavior
    extends BaseBehavior<FloatingActionButton> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static abstract class OnVisibilityChangedListener {
        public void onHidden(FloatingActionButton floatingActionButton) {
        }

        public void onShown(FloatingActionButton floatingActionButton) {
        }
    }

    private class ShadowDelegateImpl
    implements ShadowViewDelegate {
        ShadowDelegateImpl() {
        }

        @Override
        public float getRadius() {
            return (float)FloatingActionButton.this.getSizeDimension() / 2.0f;
        }

        @Override
        public boolean isCompatPaddingEnabled() {
            return FloatingActionButton.this.compatPadding;
        }

        @Override
        public void setBackgroundDrawable(Drawable drawable2) {
            if (drawable2 == null) return;
            FloatingActionButton.super.setBackgroundDrawable(drawable2);
        }

        @Override
        public void setShadowPadding(int n, int n2, int n3, int n4) {
            FloatingActionButton.this.shadowPadding.set(n, n2, n3, n4);
            FloatingActionButton floatingActionButton = FloatingActionButton.this;
            floatingActionButton.setPadding(n + floatingActionButton.imagePadding, n2 + FloatingActionButton.this.imagePadding, n3 + FloatingActionButton.this.imagePadding, n4 + FloatingActionButton.this.imagePadding);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Size {
    }

    class TransformationCallbackWrapper<T extends FloatingActionButton>
    implements FloatingActionButtonImpl.InternalTransformationCallback {
        private final TransformationCallback<T> listener;

        TransformationCallbackWrapper(TransformationCallback<T> transformationCallback) {
            this.listener = transformationCallback;
        }

        public boolean equals(Object object) {
            if (!(object instanceof TransformationCallbackWrapper)) return false;
            if (!((TransformationCallbackWrapper)object).listener.equals(this.listener)) return false;
            return true;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }

        @Override
        public void onScaleChanged() {
            this.listener.onScaleChanged(FloatingActionButton.this);
        }

        @Override
        public void onTranslationChanged() {
            this.listener.onTranslationChanged(FloatingActionButton.this);
        }
    }

}

