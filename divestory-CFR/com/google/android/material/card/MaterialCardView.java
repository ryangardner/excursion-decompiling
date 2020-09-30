/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Checkable
 */
package com.google.android.material.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import com.google.android.material.R;
import com.google.android.material.card.MaterialCardViewHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialCardView
extends CardView
implements Checkable,
Shapeable {
    private static final String ACCESSIBILITY_CLASS_NAME = "androidx.cardview.widget.CardView";
    private static final int[] CHECKABLE_STATE_SET = new int[]{16842911};
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int DEF_STYLE_RES;
    private static final int[] DRAGGED_STATE_SET;
    private static final String LOG_TAG = "MaterialCardView";
    private final MaterialCardViewHelper cardViewHelper;
    private boolean checked = false;
    private boolean dragged = false;
    private boolean isParentCardViewDoneInitializing = true;
    private OnCheckedChangeListener onCheckedChangeListener;

    static {
        DRAGGED_STATE_SET = new int[]{R.attr.state_dragged};
        DEF_STYLE_RES = R.style.Widget_MaterialComponents_CardView;
    }

    public MaterialCardView(Context context) {
        this(context, null);
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialCardViewStyle);
    }

    public MaterialCardView(Context context, AttributeSet object, int n) {
        super(MaterialThemeOverlay.wrap(context, (AttributeSet)object, n, DEF_STYLE_RES), (AttributeSet)object, n);
        context = ThemeEnforcement.obtainStyledAttributes(this.getContext(), (AttributeSet)object, R.styleable.MaterialCardView, n, DEF_STYLE_RES, new int[0]);
        object = new MaterialCardViewHelper(this, (AttributeSet)object, n, DEF_STYLE_RES);
        this.cardViewHelper = object;
        ((MaterialCardViewHelper)object).setCardBackgroundColor(super.getCardBackgroundColor());
        this.cardViewHelper.setUserContentPadding(super.getContentPaddingLeft(), super.getContentPaddingTop(), super.getContentPaddingRight(), super.getContentPaddingBottom());
        this.cardViewHelper.loadFromAttributes((TypedArray)context);
        context.recycle();
    }

    private void forceRippleRedrawIfNeeded() {
        if (Build.VERSION.SDK_INT <= 26) return;
        this.cardViewHelper.forceRippleRedraw();
    }

    private RectF getBoundsAsRectF() {
        RectF rectF = new RectF();
        rectF.set(this.cardViewHelper.getBackground().getBounds());
        return rectF;
    }

    @Override
    public ColorStateList getCardBackgroundColor() {
        return this.cardViewHelper.getCardBackgroundColor();
    }

    public ColorStateList getCardForegroundColor() {
        return this.cardViewHelper.getCardForegroundColor();
    }

    float getCardViewRadius() {
        return MaterialCardView.super.getRadius();
    }

    public Drawable getCheckedIcon() {
        return this.cardViewHelper.getCheckedIcon();
    }

    public ColorStateList getCheckedIconTint() {
        return this.cardViewHelper.getCheckedIconTint();
    }

    @Override
    public int getContentPaddingBottom() {
        return this.cardViewHelper.getUserContentPadding().bottom;
    }

    @Override
    public int getContentPaddingLeft() {
        return this.cardViewHelper.getUserContentPadding().left;
    }

    @Override
    public int getContentPaddingRight() {
        return this.cardViewHelper.getUserContentPadding().right;
    }

    @Override
    public int getContentPaddingTop() {
        return this.cardViewHelper.getUserContentPadding().top;
    }

    public float getProgress() {
        return this.cardViewHelper.getProgress();
    }

    @Override
    public float getRadius() {
        return this.cardViewHelper.getCornerRadius();
    }

    public ColorStateList getRippleColor() {
        return this.cardViewHelper.getRippleColor();
    }

    @Override
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.cardViewHelper.getShapeAppearanceModel();
    }

    @Deprecated
    public int getStrokeColor() {
        return this.cardViewHelper.getStrokeColor();
    }

    public ColorStateList getStrokeColorStateList() {
        return this.cardViewHelper.getStrokeColorStateList();
    }

    public int getStrokeWidth() {
        return this.cardViewHelper.getStrokeWidth();
    }

    public boolean isCheckable() {
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper == null) return false;
        if (!materialCardViewHelper.isCheckable()) return false;
        return true;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public boolean isDragged() {
        return this.dragged;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation((View)this, this.cardViewHelper.getBackground());
    }

    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 3);
        if (this.isCheckable()) {
            MaterialCardView.mergeDrawableStates((int[])arrn, (int[])CHECKABLE_STATE_SET);
        }
        if (this.isChecked()) {
            MaterialCardView.mergeDrawableStates((int[])arrn, (int[])CHECKED_STATE_SET);
        }
        if (!this.isDragged()) return arrn;
        MaterialCardView.mergeDrawableStates((int[])arrn, (int[])DRAGGED_STATE_SET);
        return arrn;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)ACCESSIBILITY_CLASS_NAME);
        accessibilityEvent.setChecked(this.isChecked());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)ACCESSIBILITY_CLASS_NAME);
        accessibilityNodeInfo.setCheckable(this.isCheckable());
        accessibilityNodeInfo.setClickable(this.isClickable());
        accessibilityNodeInfo.setChecked(this.isChecked());
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.cardViewHelper.onMeasure(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    void setAncestorContentPadding(int n, int n2, int n3, int n4) {
        super.setContentPadding(n, n2, n3, n4);
    }

    public void setBackground(Drawable drawable2) {
        this.setBackgroundDrawable(drawable2);
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        if (!this.isParentCardViewDoneInitializing) return;
        if (!this.cardViewHelper.isBackgroundOverwritten()) {
            Log.i((String)LOG_TAG, (String)"Setting a custom background is not supported.");
            this.cardViewHelper.setBackgroundOverwritten(true);
        }
        super.setBackgroundDrawable(drawable2);
    }

    void setBackgroundInternal(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
    }

    @Override
    public void setCardBackgroundColor(int n) {
        this.cardViewHelper.setCardBackgroundColor(ColorStateList.valueOf((int)n));
    }

    @Override
    public void setCardBackgroundColor(ColorStateList colorStateList) {
        this.cardViewHelper.setCardBackgroundColor(colorStateList);
    }

    @Override
    public void setCardElevation(float f) {
        super.setCardElevation(f);
        this.cardViewHelper.updateElevation();
    }

    public void setCardForegroundColor(ColorStateList colorStateList) {
        this.cardViewHelper.setCardForegroundColor(colorStateList);
    }

    public void setCheckable(boolean bl) {
        this.cardViewHelper.setCheckable(bl);
    }

    public void setChecked(boolean bl) {
        if (this.checked == bl) return;
        this.toggle();
    }

    public void setCheckedIcon(Drawable drawable2) {
        this.cardViewHelper.setCheckedIcon(drawable2);
    }

    public void setCheckedIconResource(int n) {
        this.cardViewHelper.setCheckedIcon(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setCheckedIconTint(ColorStateList colorStateList) {
        this.cardViewHelper.setCheckedIconTint(colorStateList);
    }

    public void setClickable(boolean bl) {
        super.setClickable(bl);
        MaterialCardViewHelper materialCardViewHelper = this.cardViewHelper;
        if (materialCardViewHelper == null) return;
        materialCardViewHelper.updateClickable();
    }

    @Override
    public void setContentPadding(int n, int n2, int n3, int n4) {
        this.cardViewHelper.setUserContentPadding(n, n2, n3, n4);
    }

    public void setDragged(boolean bl) {
        if (this.dragged == bl) return;
        this.dragged = bl;
        this.refreshDrawableState();
        this.forceRippleRedrawIfNeeded();
        this.invalidate();
    }

    @Override
    public void setMaxCardElevation(float f) {
        super.setMaxCardElevation(f);
        this.cardViewHelper.updateInsets();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void setPreventCornerOverlap(boolean bl) {
        super.setPreventCornerOverlap(bl);
        this.cardViewHelper.updateInsets();
        this.cardViewHelper.updateContentPadding();
    }

    public void setProgress(float f) {
        this.cardViewHelper.setProgress(f);
    }

    @Override
    public void setRadius(float f) {
        super.setRadius(f);
        this.cardViewHelper.setCornerRadius(f);
    }

    public void setRippleColor(ColorStateList colorStateList) {
        this.cardViewHelper.setRippleColor(colorStateList);
    }

    public void setRippleColorResource(int n) {
        this.cardViewHelper.setRippleColor(AppCompatResources.getColorStateList(this.getContext(), n));
    }

    @Override
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.setClipToOutline(shapeAppearanceModel.isRoundRect(this.getBoundsAsRectF()));
        }
        this.cardViewHelper.setShapeAppearanceModel(shapeAppearanceModel);
    }

    public void setStrokeColor(int n) {
        this.cardViewHelper.setStrokeColor(ColorStateList.valueOf((int)n));
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        this.cardViewHelper.setStrokeColor(colorStateList);
    }

    public void setStrokeWidth(int n) {
        this.cardViewHelper.setStrokeWidth(n);
    }

    @Override
    public void setUseCompatPadding(boolean bl) {
        super.setUseCompatPadding(bl);
        this.cardViewHelper.updateInsets();
        this.cardViewHelper.updateContentPadding();
    }

    public void toggle() {
        if (!this.isCheckable()) return;
        if (!this.isEnabled()) return;
        this.checked ^= true;
        this.refreshDrawableState();
        this.forceRippleRedrawIfNeeded();
        OnCheckedChangeListener onCheckedChangeListener = this.onCheckedChangeListener;
        if (onCheckedChangeListener == null) return;
        onCheckedChangeListener.onCheckedChanged(this, this.checked);
    }

    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(MaterialCardView var1, boolean var2);
    }

}

