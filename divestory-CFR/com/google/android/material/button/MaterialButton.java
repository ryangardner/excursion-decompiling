/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Layout
 *  android.text.TextPaint
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Button
 *  android.widget.Checkable
 *  android.widget.CompoundButton
 *  android.widget.TextView
 */
package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialButton
extends AppCompatButton
implements Checkable,
Shapeable {
    private static final int[] CHECKABLE_STATE_SET = new int[]{16842911};
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Button;
    public static final int ICON_GRAVITY_END = 3;
    public static final int ICON_GRAVITY_START = 1;
    public static final int ICON_GRAVITY_TEXT_END = 4;
    public static final int ICON_GRAVITY_TEXT_START = 2;
    private static final String LOG_TAG = "MaterialButton";
    private boolean broadcasting;
    private boolean checked;
    private Drawable icon;
    private int iconGravity;
    private int iconLeft;
    private int iconPadding;
    private int iconSize;
    private ColorStateList iconTint;
    private PorterDuff.Mode iconTintMode;
    private final MaterialButtonHelper materialButtonHelper;
    private final LinkedHashSet<OnCheckedChangeListener> onCheckedChangeListeners = new LinkedHashSet();
    private OnPressedChangeListener onPressedChangeListenerInternal;

    public MaterialButton(Context context) {
        this(context, null);
    }

    public MaterialButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialButtonStyle);
    }

    public MaterialButton(Context context, AttributeSet object, int n) {
        super(MaterialThemeOverlay.wrap(context, (AttributeSet)object, n, DEF_STYLE_RES), (AttributeSet)object, n);
        boolean bl = false;
        this.checked = false;
        this.broadcasting = false;
        Context context2 = this.getContext();
        context = ThemeEnforcement.obtainStyledAttributes(context2, (AttributeSet)object, R.styleable.MaterialButton, n, DEF_STYLE_RES, new int[0]);
        this.iconPadding = context.getDimensionPixelSize(R.styleable.MaterialButton_iconPadding, 0);
        this.iconTintMode = ViewUtils.parseTintMode(context.getInt(R.styleable.MaterialButton_iconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.iconTint = MaterialResources.getColorStateList(this.getContext(), (TypedArray)context, R.styleable.MaterialButton_iconTint);
        this.icon = MaterialResources.getDrawable(this.getContext(), (TypedArray)context, R.styleable.MaterialButton_icon);
        this.iconGravity = context.getInteger(R.styleable.MaterialButton_iconGravity, 1);
        this.iconSize = context.getDimensionPixelSize(R.styleable.MaterialButton_iconSize, 0);
        object = new MaterialButtonHelper(this, ShapeAppearanceModel.builder(context2, (AttributeSet)object, n, DEF_STYLE_RES).build());
        this.materialButtonHelper = object;
        ((MaterialButtonHelper)object).loadFromAttributes((TypedArray)context);
        context.recycle();
        this.setCompoundDrawablePadding(this.iconPadding);
        if (this.icon != null) {
            bl = true;
        }
        this.updateIcon(bl);
    }

    private String getA11yClassName() {
        Class<CompoundButton> class_;
        if (this.isCheckable()) {
            class_ = CompoundButton.class;
            return class_.getName();
        }
        class_ = Button.class;
        return class_.getName();
    }

    private boolean isLayoutRTL() {
        int n = ViewCompat.getLayoutDirection((View)this);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    private boolean isUsingOriginalBackground() {
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if (materialButtonHelper == null) return false;
        if (materialButtonHelper.isBackgroundOverwritten()) return false;
        return true;
    }

    private void resetIconDrawable(boolean bl) {
        if (bl) {
            TextViewCompat.setCompoundDrawablesRelative((TextView)this, this.icon, null, null, null);
            return;
        }
        TextViewCompat.setCompoundDrawablesRelative((TextView)this, null, null, this.icon, null);
    }

    private void updateIcon(boolean bl) {
        int n;
        boolean bl2;
        block9 : {
            block8 : {
                Drawable drawable2 = this.icon;
                int n2 = 0;
                if (drawable2 != null) {
                    this.icon = drawable2 = DrawableCompat.wrap(drawable2).mutate();
                    DrawableCompat.setTintList(drawable2, this.iconTint);
                    drawable2 = this.iconTintMode;
                    if (drawable2 != null) {
                        DrawableCompat.setTintMode(this.icon, (PorterDuff.Mode)drawable2);
                    }
                    if ((n = this.iconSize) == 0) {
                        n = this.icon.getIntrinsicWidth();
                    }
                    int n3 = this.iconSize;
                    if (n3 == 0) {
                        n3 = this.icon.getIntrinsicHeight();
                    }
                    drawable2 = this.icon;
                    int n4 = this.iconLeft;
                    drawable2.setBounds(n4, 0, n + n4, n3);
                }
                bl2 = (n = this.iconGravity) == 1 || n == 2;
                if (bl) {
                    this.resetIconDrawable(bl2);
                    return;
                }
                Drawable drawable3 = TextViewCompat.getCompoundDrawablesRelative((TextView)this);
                drawable2 = drawable3[0];
                drawable3 = drawable3[2];
                if (bl2 && drawable2 != this.icon) break block8;
                n = n2;
                if (bl2) break block9;
                n = n2;
                if (drawable3 == this.icon) break block9;
            }
            n = 1;
        }
        if (n == 0) return;
        this.resetIconDrawable(bl2);
    }

    private void updateIconPosition() {
        if (this.icon == null) return;
        if (this.getLayout() == null) {
            return;
        }
        int n = this.iconGravity;
        boolean bl = true;
        if (n != 1 && n != 3) {
            int n2;
            String string2;
            TextPaint textPaint = this.getPaint();
            String string3 = string2 = this.getText().toString();
            if (this.getTransformationMethod() != null) {
                string3 = this.getTransformationMethod().getTransformation((CharSequence)string2, (View)this).toString();
            }
            int n3 = Math.min((int)textPaint.measureText(string3), this.getLayout().getEllipsizedWidth());
            n = n2 = this.iconSize;
            if (n2 == 0) {
                n = this.icon.getIntrinsicWidth();
            }
            n2 = (this.getMeasuredWidth() - n3 - ViewCompat.getPaddingEnd((View)this) - n - this.iconPadding - ViewCompat.getPaddingStart((View)this)) / 2;
            boolean bl2 = this.isLayoutRTL();
            if (this.iconGravity != 4) {
                bl = false;
            }
            n = n2;
            if (bl2 != bl) {
                n = -n2;
            }
            if (this.iconLeft == n) return;
            this.iconLeft = n;
            this.updateIcon(false);
            return;
        }
        this.iconLeft = 0;
        this.updateIcon(false);
    }

    public void addOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListeners.add(onCheckedChangeListener);
    }

    public void clearOnCheckedChangeListeners() {
        this.onCheckedChangeListeners.clear();
    }

    public ColorStateList getBackgroundTintList() {
        return this.getSupportBackgroundTintList();
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return this.getSupportBackgroundTintMode();
    }

    public int getCornerRadius() {
        if (!this.isUsingOriginalBackground()) return 0;
        return this.materialButtonHelper.getCornerRadius();
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public int getIconGravity() {
        return this.iconGravity;
    }

    public int getIconPadding() {
        return this.iconPadding;
    }

    public int getIconSize() {
        return this.iconSize;
    }

    public ColorStateList getIconTint() {
        return this.iconTint;
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.iconTintMode;
    }

    public ColorStateList getRippleColor() {
        if (!this.isUsingOriginalBackground()) return null;
        return this.materialButtonHelper.getRippleColor();
    }

    @Override
    public ShapeAppearanceModel getShapeAppearanceModel() {
        if (!this.isUsingOriginalBackground()) throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
        return this.materialButtonHelper.getShapeAppearanceModel();
    }

    public ColorStateList getStrokeColor() {
        if (!this.isUsingOriginalBackground()) return null;
        return this.materialButtonHelper.getStrokeColor();
    }

    public int getStrokeWidth() {
        if (!this.isUsingOriginalBackground()) return 0;
        return this.materialButtonHelper.getStrokeWidth();
    }

    @Override
    public ColorStateList getSupportBackgroundTintList() {
        if (!this.isUsingOriginalBackground()) return super.getSupportBackgroundTintList();
        return this.materialButtonHelper.getSupportBackgroundTintList();
    }

    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (!this.isUsingOriginalBackground()) return super.getSupportBackgroundTintMode();
        return this.materialButtonHelper.getSupportBackgroundTintMode();
    }

    public boolean isCheckable() {
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if (materialButtonHelper == null) return false;
        if (!materialButtonHelper.isCheckable()) return false;
        return true;
    }

    public boolean isChecked() {
        return this.checked;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.isUsingOriginalBackground()) return;
        MaterialShapeUtils.setParentAbsoluteElevation((View)this, this.materialButtonHelper.getMaterialShapeDrawable());
    }

    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 2);
        if (this.isCheckable()) {
            MaterialButton.mergeDrawableStates((int[])arrn, (int[])CHECKABLE_STATE_SET);
        }
        if (!this.isChecked()) return arrn;
        MaterialButton.mergeDrawableStates((int[])arrn, (int[])CHECKED_STATE_SET);
        return arrn;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)this.getA11yClassName());
        accessibilityEvent.setChecked(this.isChecked());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)this.getA11yClassName());
        accessibilityNodeInfo.setCheckable(this.isCheckable());
        accessibilityNodeInfo.setChecked(this.isChecked());
        accessibilityNodeInfo.setClickable(this.isClickable());
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (Build.VERSION.SDK_INT != 21) return;
        MaterialButtonHelper materialButtonHelper = this.materialButtonHelper;
        if (materialButtonHelper == null) return;
        materialButtonHelper.updateMaskBounds(n4 - n2, n3 - n);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.updateIconPosition();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.setChecked(parcelable.checked);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.checked;
        return savedState;
    }

    @Override
    protected void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        super.onTextChanged(charSequence, n, n2, n3);
        this.updateIconPosition();
    }

    public boolean performClick() {
        this.toggle();
        return super.performClick();
    }

    public void removeOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListeners.remove(onCheckedChangeListener);
    }

    public void setBackground(Drawable drawable2) {
        this.setBackgroundDrawable(drawable2);
    }

    public void setBackgroundColor(int n) {
        if (this.isUsingOriginalBackground()) {
            this.materialButtonHelper.setBackgroundColor(n);
            return;
        }
        super.setBackgroundColor(n);
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        if (!this.isUsingOriginalBackground()) {
            super.setBackgroundDrawable(drawable2);
            return;
        }
        if (drawable2 != this.getBackground()) {
            Log.w((String)LOG_TAG, (String)"Do not set the background; MaterialButton manages its own background drawable.");
            this.materialButtonHelper.setBackgroundOverwritten();
            super.setBackgroundDrawable(drawable2);
            return;
        }
        this.getBackground().setState(drawable2.getState());
    }

    @Override
    public void setBackgroundResource(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setBackgroundDrawable(drawable2);
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        this.setSupportBackgroundTintList(colorStateList);
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        this.setSupportBackgroundTintMode(mode);
    }

    public void setCheckable(boolean bl) {
        if (!this.isUsingOriginalBackground()) return;
        this.materialButtonHelper.setCheckable(bl);
    }

    public void setChecked(boolean bl) {
        if (!this.isCheckable()) return;
        if (!this.isEnabled()) return;
        if (this.checked == bl) return;
        this.checked = bl;
        this.refreshDrawableState();
        if (this.broadcasting) {
            return;
        }
        this.broadcasting = true;
        Iterator iterator2 = this.onCheckedChangeListeners.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.broadcasting = false;
                return;
            }
            ((OnCheckedChangeListener)iterator2.next()).onCheckedChanged(this, this.checked);
        } while (true);
    }

    public void setCornerRadius(int n) {
        if (!this.isUsingOriginalBackground()) return;
        this.materialButtonHelper.setCornerRadius(n);
    }

    public void setCornerRadiusResource(int n) {
        if (!this.isUsingOriginalBackground()) return;
        this.setCornerRadius(this.getResources().getDimensionPixelSize(n));
    }

    public void setElevation(float f) {
        super.setElevation(f);
        if (!this.isUsingOriginalBackground()) return;
        this.materialButtonHelper.getMaterialShapeDrawable().setElevation(f);
    }

    public void setIcon(Drawable drawable2) {
        if (this.icon == drawable2) return;
        this.icon = drawable2;
        this.updateIcon(true);
    }

    public void setIconGravity(int n) {
        if (this.iconGravity == n) return;
        this.iconGravity = n;
        this.updateIconPosition();
    }

    public void setIconPadding(int n) {
        if (this.iconPadding == n) return;
        this.iconPadding = n;
        this.setCompoundDrawablePadding(n);
    }

    public void setIconResource(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setIcon(drawable2);
    }

    public void setIconSize(int n) {
        if (n < 0) throw new IllegalArgumentException("iconSize cannot be less than 0");
        if (this.iconSize == n) return;
        this.iconSize = n;
        this.updateIcon(true);
    }

    public void setIconTint(ColorStateList colorStateList) {
        if (this.iconTint == colorStateList) return;
        this.iconTint = colorStateList;
        this.updateIcon(false);
    }

    public void setIconTintMode(PorterDuff.Mode mode) {
        if (this.iconTintMode == mode) return;
        this.iconTintMode = mode;
        this.updateIcon(false);
    }

    public void setIconTintResource(int n) {
        this.setIconTint(AppCompatResources.getColorStateList(this.getContext(), n));
    }

    void setInternalBackground(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
    }

    void setOnPressedChangeListenerInternal(OnPressedChangeListener onPressedChangeListener) {
        this.onPressedChangeListenerInternal = onPressedChangeListener;
    }

    public void setPressed(boolean bl) {
        OnPressedChangeListener onPressedChangeListener = this.onPressedChangeListenerInternal;
        if (onPressedChangeListener != null) {
            onPressedChangeListener.onPressedChanged(this, bl);
        }
        super.setPressed(bl);
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (!this.isUsingOriginalBackground()) return;
        this.materialButtonHelper.setRippleColor(colorStateList);
    }

    public void setRippleColorResource(int n) {
        if (!this.isUsingOriginalBackground()) return;
        this.setRippleColor(AppCompatResources.getColorStateList(this.getContext(), n));
    }

    @Override
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        if (!this.isUsingOriginalBackground()) throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
        this.materialButtonHelper.setShapeAppearanceModel(shapeAppearanceModel);
    }

    void setShouldDrawSurfaceColorStroke(boolean bl) {
        if (!this.isUsingOriginalBackground()) return;
        this.materialButtonHelper.setShouldDrawSurfaceColorStroke(bl);
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        if (!this.isUsingOriginalBackground()) return;
        this.materialButtonHelper.setStrokeColor(colorStateList);
    }

    public void setStrokeColorResource(int n) {
        if (!this.isUsingOriginalBackground()) return;
        this.setStrokeColor(AppCompatResources.getColorStateList(this.getContext(), n));
    }

    public void setStrokeWidth(int n) {
        if (!this.isUsingOriginalBackground()) return;
        this.materialButtonHelper.setStrokeWidth(n);
    }

    public void setStrokeWidthResource(int n) {
        if (!this.isUsingOriginalBackground()) return;
        this.setStrokeWidth(this.getResources().getDimensionPixelSize(n));
    }

    @Override
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintList(colorStateList);
            return;
        }
        super.setSupportBackgroundTintList(colorStateList);
    }

    @Override
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintMode(mode);
            return;
        }
        super.setSupportBackgroundTintMode(mode);
    }

    public void toggle() {
        this.setChecked(this.checked ^ true);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IconGravity {
    }

    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(MaterialButton var1, boolean var2);
    }

    static interface OnPressedChangeListener {
        public void onPressedChanged(MaterialButton var1, boolean var2);
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean checked;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                this.getClass().getClassLoader();
            }
            this.readFromParcel(parcel);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private void readFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.checked = bl;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt((int)this.checked);
        }

    }

}

