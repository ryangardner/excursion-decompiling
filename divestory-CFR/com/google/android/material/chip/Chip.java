/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Outline
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.PointerIcon
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewOutlineProvider
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.TextView
 *  android.widget.TextView$BufferType
 */
package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Chip
extends AppCompatCheckBox
implements ChipDrawable.Delegate,
Shapeable {
    private static final String BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.Button";
    private static final int[] CHECKABLE_STATE_SET;
    private static final int CHIP_BODY_VIRTUAL_ID = 0;
    private static final int CLOSE_ICON_VIRTUAL_ID = 1;
    private static final String COMPOUND_BUTTON_ACCESSIBILITY_CLASS_NAME = "android.widget.CompoundButton";
    private static final int DEF_STYLE_RES;
    private static final Rect EMPTY_BOUNDS;
    private static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
    private static final int MIN_TOUCH_TARGET_DP = 48;
    private static final String NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";
    private static final int[] SELECTED_STATE;
    private static final String TAG = "Chip";
    private ChipDrawable chipDrawable;
    private boolean closeIconFocused;
    private boolean closeIconHovered;
    private boolean closeIconPressed;
    private boolean deferredCheckedValue;
    private boolean ensureMinTouchTargetSize;
    private final TextAppearanceFontCallback fontCallback = new TextAppearanceFontCallback(){

        @Override
        public void onFontRetrievalFailed(int n) {
        }

        @Override
        public void onFontRetrieved(Typeface object, boolean bl) {
            Chip chip = Chip.this;
            object = chip.chipDrawable.shouldDrawText() ? Chip.this.chipDrawable.getText() : Chip.this.getText();
            chip.setText((CharSequence)object);
            Chip.this.requestLayout();
            Chip.this.invalidate();
        }
    };
    private InsetDrawable insetBackgroundDrawable;
    private int lastLayoutDirection;
    private int minTouchTargetSize;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListenerInternal;
    private View.OnClickListener onCloseIconClickListener;
    private final Rect rect = new Rect();
    private final RectF rectF = new RectF();
    private RippleDrawable ripple;
    private final ChipTouchHelper touchHelper;

    static {
        DEF_STYLE_RES = R.style.Widget_MaterialComponents_Chip_Action;
        EMPTY_BOUNDS = new Rect();
        SELECTED_STATE = new int[]{16842913};
        CHECKABLE_STATE_SET = new int[]{16842911};
    }

    public Chip(Context context) {
        this(context, null);
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipStyle);
    }

    public Chip(Context object, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap((Context)object, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        Context context = this.getContext();
        this.validateAttributes(attributeSet);
        object = ChipDrawable.createFromAttributes(context, attributeSet, n, DEF_STYLE_RES);
        this.initMinTouchTarget(context, attributeSet, n);
        this.setChipDrawable((ChipDrawable)object);
        ((MaterialShapeDrawable)object).setElevation(ViewCompat.getElevation((View)this));
        attributeSet = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Chip, n, DEF_STYLE_RES, new int[0]);
        if (Build.VERSION.SDK_INT < 23) {
            this.setTextColor(MaterialResources.getColorStateList(context, (TypedArray)attributeSet, R.styleable.Chip_android_textColor));
        }
        boolean bl = attributeSet.hasValue(R.styleable.Chip_shapeAppearance);
        attributeSet.recycle();
        this.touchHelper = new ChipTouchHelper(this);
        this.updateAccessibilityDelegate();
        if (!bl) {
            this.initOutlineProvider();
        }
        this.setChecked(this.deferredCheckedValue);
        this.setText(((ChipDrawable)object).getText());
        this.setEllipsize(((ChipDrawable)object).getEllipsize());
        this.updateTextPaintDrawState();
        if (!this.chipDrawable.shouldDrawText()) {
            this.setLines(1);
            this.setHorizontallyScrolling(true);
        }
        this.setGravity(8388627);
        this.updatePaddingInternal();
        if (this.shouldEnsureMinTouchTargetSize()) {
            this.setMinHeight(this.minTouchTargetSize);
        }
        this.lastLayoutDirection = ViewCompat.getLayoutDirection((View)this);
    }

    private void applyChipDrawable(ChipDrawable chipDrawable) {
        chipDrawable.setDelegate(this);
    }

    private int[] createCloseIconDrawableState() {
        boolean bl = this.isEnabled();
        int n = 0;
        int n2 = bl ? 1 : 0;
        int n3 = n2;
        if (this.closeIconFocused) {
            n3 = n2 + 1;
        }
        n2 = n3;
        if (this.closeIconHovered) {
            n2 = n3 + 1;
        }
        n3 = n2;
        if (this.closeIconPressed) {
            n3 = n2 + 1;
        }
        n2 = n3;
        if (this.isChecked()) {
            n2 = n3 + 1;
        }
        int[] arrn = new int[n2];
        n2 = n;
        if (this.isEnabled()) {
            arrn[0] = 16842910;
            n2 = 1;
        }
        n3 = n2;
        if (this.closeIconFocused) {
            arrn[n2] = 16842908;
            n3 = n2 + 1;
        }
        n2 = n3;
        if (this.closeIconHovered) {
            arrn[n3] = 16843623;
            n2 = n3 + 1;
        }
        n3 = n2;
        if (this.closeIconPressed) {
            arrn[n2] = 16842919;
            n3 = n2 + 1;
        }
        if (!this.isChecked()) return arrn;
        arrn[n3] = 16842913;
        return arrn;
    }

    private void ensureChipDrawableHasCallback() {
        if (this.getBackgroundDrawable() != this.insetBackgroundDrawable) return;
        if (this.chipDrawable.getCallback() != null) return;
        this.chipDrawable.setCallback((Drawable.Callback)this.insetBackgroundDrawable);
    }

    private RectF getCloseIconTouchBounds() {
        this.rectF.setEmpty();
        if (!this.hasCloseIcon()) return this.rectF;
        this.chipDrawable.getCloseIconTouchBounds(this.rectF);
        return this.rectF;
    }

    private Rect getCloseIconTouchBoundsInt() {
        RectF rectF = this.getCloseIconTouchBounds();
        this.rect.set((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom);
        return this.rect;
    }

    private TextAppearance getTextAppearance() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getTextAppearance();
    }

    private boolean handleAccessibilityExit(MotionEvent object) {
        if (object.getAction() != 10) return false;
        try {
            object = ExploreByTouchHelper.class.getDeclaredField("mHoveredVirtualViewId");
            ((Field)object).setAccessible(true);
            if ((Integer)((Field)object).get(this.touchHelper) == Integer.MIN_VALUE) return false;
            object = ExploreByTouchHelper.class.getDeclaredMethod("updateHoveredVirtualView", Integer.TYPE);
            ((Method)object).setAccessible(true);
            ((Method)object).invoke(this.touchHelper, Integer.MIN_VALUE);
            return true;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            Log.e((String)TAG, (String)"Unable to send Accessibility Exit event", (Throwable)noSuchFieldException);
            return false;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)TAG, (String)"Unable to send Accessibility Exit event", (Throwable)invocationTargetException);
            return false;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Unable to send Accessibility Exit event", (Throwable)illegalAccessException);
            return false;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"Unable to send Accessibility Exit event", (Throwable)noSuchMethodException);
        }
        return false;
    }

    private boolean hasCloseIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return false;
        if (chipDrawable.getCloseIcon() == null) return false;
        return true;
    }

    private void initMinTouchTarget(Context context, AttributeSet attributeSet, int n) {
        context = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Chip, n, DEF_STYLE_RES, new int[0]);
        this.ensureMinTouchTargetSize = context.getBoolean(R.styleable.Chip_ensureMinTouchTargetSize, false);
        float f = (float)Math.ceil(ViewUtils.dpToPx(this.getContext(), 48));
        this.minTouchTargetSize = (int)Math.ceil(context.getDimension(R.styleable.Chip_chipMinTouchTargetSize, f));
        context.recycle();
    }

    private void initOutlineProvider() {
        if (Build.VERSION.SDK_INT < 21) return;
        this.setOutlineProvider(new ViewOutlineProvider(){

            public void getOutline(View view, Outline outline) {
                if (Chip.this.chipDrawable != null) {
                    Chip.this.chipDrawable.getOutline(outline);
                    return;
                }
                outline.setAlpha(0.0f);
            }
        });
    }

    private void insetChipBackgroundDrawable(int n, int n2, int n3, int n4) {
        this.insetBackgroundDrawable = new InsetDrawable((Drawable)this.chipDrawable, n, n2, n3, n4);
    }

    private void removeBackgroundInset() {
        if (this.insetBackgroundDrawable == null) return;
        this.insetBackgroundDrawable = null;
        this.setMinWidth(0);
        this.setMinHeight((int)this.getChipMinHeight());
        this.updateBackgroundDrawable();
    }

    private void setCloseIconHovered(boolean bl) {
        if (this.closeIconHovered == bl) return;
        this.closeIconHovered = bl;
        this.refreshDrawableState();
    }

    private void setCloseIconPressed(boolean bl) {
        if (this.closeIconPressed == bl) return;
        this.closeIconPressed = bl;
        this.refreshDrawableState();
    }

    private void unapplyChipDrawable(ChipDrawable chipDrawable) {
        if (chipDrawable == null) return;
        chipDrawable.setDelegate(null);
    }

    private void updateAccessibilityDelegate() {
        if (this.hasCloseIcon() && this.isCloseIconVisible() && this.onCloseIconClickListener != null) {
            ViewCompat.setAccessibilityDelegate((View)this, this.touchHelper);
            return;
        }
        ViewCompat.setAccessibilityDelegate((View)this, null);
    }

    private void updateBackgroundDrawable() {
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            this.updateFrameworkRippleBackground();
            return;
        }
        this.chipDrawable.setUseCompatRipple(true);
        ViewCompat.setBackground((View)this, this.getBackgroundDrawable());
        this.updatePaddingInternal();
        this.ensureChipDrawableHasCallback();
    }

    private void updateFrameworkRippleBackground() {
        this.ripple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.chipDrawable.getRippleColor()), this.getBackgroundDrawable(), null);
        this.chipDrawable.setUseCompatRipple(false);
        ViewCompat.setBackground((View)this, (Drawable)this.ripple);
        this.updatePaddingInternal();
    }

    private void updatePaddingInternal() {
        if (TextUtils.isEmpty((CharSequence)this.getText())) return;
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            return;
        }
        int n = (int)(chipDrawable.getChipEndPadding() + this.chipDrawable.getTextEndPadding() + this.chipDrawable.calculateCloseIconWidth());
        int n2 = (int)(this.chipDrawable.getChipStartPadding() + this.chipDrawable.getTextStartPadding() + this.chipDrawable.calculateChipIconWidth());
        int n3 = n;
        int n4 = n2;
        if (this.insetBackgroundDrawable != null) {
            chipDrawable = new Rect();
            this.insetBackgroundDrawable.getPadding((Rect)chipDrawable);
            n4 = n2 + ((Rect)chipDrawable).left;
            n3 = n + ((Rect)chipDrawable).right;
        }
        ViewCompat.setPaddingRelative((View)this, n4, this.getPaddingTop(), n3, this.getPaddingBottom());
    }

    private void updateTextPaintDrawState() {
        TextPaint textPaint = this.getPaint();
        Object object = this.chipDrawable;
        if (object != null) {
            textPaint.drawableState = object.getState();
        }
        if ((object = this.getTextAppearance()) == null) return;
        ((TextAppearance)object).updateDrawState(this.getContext(), textPaint, this.fontCallback);
    }

    private void validateAttributes(AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "background") != null) {
            Log.w((String)TAG, (String)"Do not set the background; Chip manages its own background drawable.");
        }
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableLeft") != null) throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableStart") != null) throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableEnd") != null) throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        if (attributeSet.getAttributeValue(NAMESPACE_ANDROID, "drawableRight") != null) throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        if (!attributeSet.getAttributeBooleanValue(NAMESPACE_ANDROID, "singleLine", true)) throw new UnsupportedOperationException("Chip does not support multi-line text");
        if (attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "lines", 1) != 1) throw new UnsupportedOperationException("Chip does not support multi-line text");
        if (attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "minLines", 1) != 1) throw new UnsupportedOperationException("Chip does not support multi-line text");
        if (attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "maxLines", 1) != 1) throw new UnsupportedOperationException("Chip does not support multi-line text");
        if (attributeSet.getAttributeIntValue(NAMESPACE_ANDROID, "gravity", 8388627) == 8388627) return;
        Log.w((String)TAG, (String)"Chip text must be vertically center and start aligned");
    }

    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.handleAccessibilityExit(motionEvent)) return true;
        if (this.touchHelper.dispatchHoverEvent(motionEvent)) return true;
        if (super.dispatchHoverEvent(motionEvent)) return true;
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.touchHelper.dispatchKeyEvent(keyEvent)) return super.dispatchKeyEvent(keyEvent);
        if (this.touchHelper.getKeyboardFocusedVirtualViewId() == Integer.MIN_VALUE) return super.dispatchKeyEvent(keyEvent);
        return true;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        ChipDrawable chipDrawable = this.chipDrawable;
        boolean bl = chipDrawable != null && chipDrawable.isCloseIconStateful() ? this.chipDrawable.setCloseIconState(this.createCloseIconDrawableState()) : false;
        if (!bl) return;
        this.invalidate();
    }

    public boolean ensureAccessibleTouchTarget(int n) {
        this.minTouchTargetSize = n;
        boolean bl = this.shouldEnsureMinTouchTargetSize();
        int n2 = 0;
        if (!bl) {
            if (this.insetBackgroundDrawable != null) {
                this.removeBackgroundInset();
                return false;
            }
            this.updateBackgroundDrawable();
            return false;
        }
        int n3 = Math.max(0, n - this.chipDrawable.getIntrinsicHeight());
        int n4 = Math.max(0, n - this.chipDrawable.getIntrinsicWidth());
        if (n4 <= 0 && n3 <= 0) {
            if (this.insetBackgroundDrawable != null) {
                this.removeBackgroundInset();
                return false;
            }
            this.updateBackgroundDrawable();
            return false;
        }
        n4 = n4 > 0 ? (n4 /= 2) : 0;
        if (n3 > 0) {
            n2 = n3 / 2;
        }
        if (this.insetBackgroundDrawable != null) {
            Rect rect = new Rect();
            this.insetBackgroundDrawable.getPadding(rect);
            if (rect.top == n2 && rect.bottom == n2 && rect.left == n4 && rect.right == n4) {
                this.updateBackgroundDrawable();
                return true;
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            if (this.getMinHeight() != n) {
                this.setMinHeight(n);
            }
            if (this.getMinWidth() != n) {
                this.setMinWidth(n);
            }
        } else {
            this.setMinHeight(n);
            this.setMinWidth(n);
        }
        this.insetChipBackgroundDrawable(n4, n2, n4, n2);
        this.updateBackgroundDrawable();
        return true;
    }

    public Drawable getBackgroundDrawable() {
        InsetDrawable insetDrawable;
        Object object = insetDrawable = this.insetBackgroundDrawable;
        if (insetDrawable != null) return object;
        return this.chipDrawable;
    }

    public Drawable getCheckedIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getCheckedIcon();
    }

    public ColorStateList getCheckedIconTint() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getCheckedIconTint();
    }

    public ColorStateList getChipBackgroundColor() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getChipBackgroundColor();
    }

    public float getChipCornerRadius() {
        ChipDrawable chipDrawable = this.chipDrawable;
        float f = 0.0f;
        if (chipDrawable == null) return f;
        return Math.max(0.0f, chipDrawable.getChipCornerRadius());
    }

    public Drawable getChipDrawable() {
        return this.chipDrawable;
    }

    public float getChipEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getChipEndPadding();
    }

    public Drawable getChipIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getChipIcon();
    }

    public float getChipIconSize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getChipIconSize();
    }

    public ColorStateList getChipIconTint() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getChipIconTint();
    }

    public float getChipMinHeight() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getChipMinHeight();
    }

    public float getChipStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getChipStartPadding();
    }

    public ColorStateList getChipStrokeColor() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getChipStrokeColor();
    }

    public float getChipStrokeWidth() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getChipStrokeWidth();
    }

    @Deprecated
    public CharSequence getChipText() {
        return this.getText();
    }

    public Drawable getCloseIcon() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getCloseIcon();
    }

    public CharSequence getCloseIconContentDescription() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getCloseIconContentDescription();
    }

    public float getCloseIconEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getCloseIconEndPadding();
    }

    public float getCloseIconSize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getCloseIconSize();
    }

    public float getCloseIconStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getCloseIconStartPadding();
    }

    public ColorStateList getCloseIconTint() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getCloseIconTint();
    }

    public TextUtils.TruncateAt getEllipsize() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getEllipsize();
    }

    public void getFocusedRect(Rect rect) {
        if (this.touchHelper.getKeyboardFocusedVirtualViewId() != 1 && this.touchHelper.getAccessibilityFocusedVirtualViewId() != 1) {
            super.getFocusedRect(rect);
            return;
        }
        rect.set(this.getCloseIconTouchBoundsInt());
    }

    public MotionSpec getHideMotionSpec() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getHideMotionSpec();
    }

    public float getIconEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getIconEndPadding();
    }

    public float getIconStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getIconStartPadding();
    }

    public ColorStateList getRippleColor() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getRippleColor();
    }

    @Override
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.chipDrawable.getShapeAppearanceModel();
    }

    public MotionSpec getShowMotionSpec() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return null;
        return chipDrawable.getShowMotionSpec();
    }

    public float getTextEndPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getTextEndPadding();
    }

    public float getTextStartPadding() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return 0.0f;
        return chipDrawable.getTextStartPadding();
    }

    public boolean isCheckable() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return false;
        if (!chipDrawable.isCheckable()) return false;
        return true;
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return this.isCheckedIconVisible();
    }

    public boolean isCheckedIconVisible() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return false;
        if (!chipDrawable.isCheckedIconVisible()) return false;
        return true;
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return this.isChipIconVisible();
    }

    public boolean isChipIconVisible() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return false;
        if (!chipDrawable.isChipIconVisible()) return false;
        return true;
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return this.isCloseIconVisible();
    }

    public boolean isCloseIconVisible() {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return false;
        if (!chipDrawable.isCloseIconVisible()) return false;
        return true;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation((View)this, this.chipDrawable);
    }

    @Override
    public void onChipDrawableSizeChange() {
        this.ensureAccessibleTouchTarget(this.minTouchTargetSize);
        this.requestLayout();
        if (Build.VERSION.SDK_INT < 21) return;
        this.invalidateOutline();
    }

    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 2);
        if (this.isChecked()) {
            Chip.mergeDrawableStates((int[])arrn, (int[])SELECTED_STATE);
        }
        if (!this.isCheckable()) return arrn;
        Chip.mergeDrawableStates((int[])arrn, (int[])CHECKABLE_STATE_SET);
        return arrn;
    }

    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        this.touchHelper.onFocusChanged(bl, n, rect);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 7) {
            this.setCloseIconHovered(this.getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()));
            return super.onHoverEvent(motionEvent);
        }
        if (n != 10) {
            return super.onHoverEvent(motionEvent);
        }
        this.setCloseIconHovered(false);
        return super.onHoverEvent(motionEvent);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        Object object2;
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        if (!this.isCheckable() && !this.isClickable()) {
            object.setClassName((CharSequence)GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
        } else {
            object2 = this.isCheckable() ? COMPOUND_BUTTON_ACCESSIBILITY_CLASS_NAME : BUTTON_ACCESSIBILITY_CLASS_NAME;
            object.setClassName((CharSequence)object2);
        }
        object.setCheckable(this.isCheckable());
        object.setClickable(this.isClickable());
        if (!(this.getParent() instanceof ChipGroup)) return;
        object2 = (ChipGroup)this.getParent();
        object = AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object);
        int n = ((ChipGroup)((Object)object2)).isSingleLine() ? ((ChipGroup)((Object)object2)).getIndexOfChip((View)this) : -1;
        ((AccessibilityNodeInfoCompat)object).setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(((FlowLayout)((Object)object2)).getRowIndex((View)this), 1, n, 1, false, this.isChecked()));
    }

    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (!this.getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY())) return null;
        if (!this.isEnabled()) return null;
        return PointerIcon.getSystemIcon((Context)this.getContext(), (int)1002);
    }

    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        if (this.lastLayoutDirection == n) return;
        this.lastLayoutDirection = n;
        this.updatePaddingInternal();
    }

    /*
     * Unable to fully structure code
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block10 : {
            block9 : {
                block6 : {
                    block7 : {
                        block8 : {
                            var2_2 = var1_1.getActionMasked();
                            var3_3 = this.getCloseIconTouchBounds().contains(var1_1.getX(), var1_1.getY());
                            var4_4 = false;
                            if (var2_2 == 0) break block6;
                            if (var2_2 == 1) break block7;
                            if (var2_2 == 2) break block8;
                            if (var2_2 == 3) ** GOTO lbl-1000
                            break block9;
                        }
                        if (this.closeIconPressed) {
                            if (var3_3 != false) return true;
                            this.setCloseIconPressed(false);
                            return true;
                        }
                        break block9;
                    }
                    if (this.closeIconPressed) {
                        this.performCloseIconClick();
                        var2_2 = 1;
                    } else lbl-1000: // 2 sources:
                    {
                        var2_2 = 0;
                    }
                    this.setCloseIconPressed(false);
                    break block10;
                }
                if (var3_3) {
                    this.setCloseIconPressed(true);
                    return true;
                }
            }
            var2_2 = 0;
        }
        if (var2_2 != 0) return true;
        if (super.onTouchEvent(var1_1) == false) return var4_4;
        return true;
    }

    public boolean performCloseIconClick() {
        boolean bl = false;
        this.playSoundEffect(0);
        View.OnClickListener onClickListener = this.onCloseIconClickListener;
        if (onClickListener != null) {
            onClickListener.onClick((View)this);
            bl = true;
        }
        this.touchHelper.sendEventForVirtualView(1, 1);
        return bl;
    }

    public void setBackground(Drawable drawable2) {
        if (drawable2 != this.getBackgroundDrawable() && drawable2 != this.ripple) {
            Log.w((String)TAG, (String)"Do not set the background; Chip manages its own background drawable.");
            return;
        }
        super.setBackground(drawable2);
    }

    public void setBackgroundColor(int n) {
        Log.w((String)TAG, (String)"Do not set the background color; Chip manages its own background drawable.");
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        if (drawable2 != this.getBackgroundDrawable() && drawable2 != this.ripple) {
            Log.w((String)TAG, (String)"Do not set the background drawable; Chip manages its own background drawable.");
            return;
        }
        super.setBackgroundDrawable(drawable2);
    }

    @Override
    public void setBackgroundResource(int n) {
        Log.w((String)TAG, (String)"Do not set the background resource; Chip manages its own background drawable.");
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        Log.w((String)TAG, (String)"Do not set the background tint list; Chip manages its own background drawable.");
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        Log.w((String)TAG, (String)"Do not set the background tint mode; Chip manages its own background drawable.");
    }

    public void setCheckable(boolean bl) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckable(bl);
    }

    public void setCheckableResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckableResource(n);
    }

    public void setChecked(boolean bl) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) {
            this.deferredCheckedValue = bl;
            return;
        }
        if (!chipDrawable.isCheckable()) return;
        boolean bl2 = this.isChecked();
        super.setChecked(bl);
        if (bl2 == bl) return;
        chipDrawable = this.onCheckedChangeListenerInternal;
        if (chipDrawable == null) return;
        chipDrawable.onCheckedChanged((CompoundButton)this, bl);
    }

    public void setCheckedIcon(Drawable drawable2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckedIcon(drawable2);
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean bl) {
        this.setCheckedIconVisible(bl);
    }

    @Deprecated
    public void setCheckedIconEnabledResource(int n) {
        this.setCheckedIconVisible(n);
    }

    public void setCheckedIconResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckedIconResource(n);
    }

    public void setCheckedIconTint(ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckedIconTint(colorStateList);
    }

    public void setCheckedIconTintResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckedIconTintResource(n);
    }

    public void setCheckedIconVisible(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckedIconVisible(n);
    }

    public void setCheckedIconVisible(boolean bl) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCheckedIconVisible(bl);
    }

    public void setChipBackgroundColor(ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipBackgroundColor(colorStateList);
    }

    public void setChipBackgroundColorResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipBackgroundColorResource(n);
    }

    @Deprecated
    public void setChipCornerRadius(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipCornerRadius(f);
    }

    @Deprecated
    public void setChipCornerRadiusResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipCornerRadiusResource(n);
    }

    public void setChipDrawable(ChipDrawable chipDrawable) {
        ChipDrawable chipDrawable2 = this.chipDrawable;
        if (chipDrawable2 == chipDrawable) return;
        this.unapplyChipDrawable(chipDrawable2);
        this.chipDrawable = chipDrawable;
        chipDrawable.setShouldDrawText(false);
        this.applyChipDrawable(this.chipDrawable);
        this.ensureAccessibleTouchTarget(this.minTouchTargetSize);
    }

    public void setChipEndPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipEndPadding(f);
    }

    public void setChipEndPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipEndPaddingResource(n);
    }

    public void setChipIcon(Drawable drawable2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIcon(drawable2);
    }

    @Deprecated
    public void setChipIconEnabled(boolean bl) {
        this.setChipIconVisible(bl);
    }

    @Deprecated
    public void setChipIconEnabledResource(int n) {
        this.setChipIconVisible(n);
    }

    public void setChipIconResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIconResource(n);
    }

    public void setChipIconSize(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIconSize(f);
    }

    public void setChipIconSizeResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIconSizeResource(n);
    }

    public void setChipIconTint(ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIconTint(colorStateList);
    }

    public void setChipIconTintResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIconTintResource(n);
    }

    public void setChipIconVisible(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIconVisible(n);
    }

    public void setChipIconVisible(boolean bl) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipIconVisible(bl);
    }

    public void setChipMinHeight(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipMinHeight(f);
    }

    public void setChipMinHeightResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipMinHeightResource(n);
    }

    public void setChipStartPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipStartPadding(f);
    }

    public void setChipStartPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipStartPaddingResource(n);
    }

    public void setChipStrokeColor(ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipStrokeColor(colorStateList);
    }

    public void setChipStrokeColorResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipStrokeColorResource(n);
    }

    public void setChipStrokeWidth(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipStrokeWidth(f);
    }

    public void setChipStrokeWidthResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setChipStrokeWidthResource(n);
    }

    @Deprecated
    public void setChipText(CharSequence charSequence) {
        this.setText(charSequence);
    }

    @Deprecated
    public void setChipTextResource(int n) {
        this.setText((CharSequence)this.getResources().getString(n));
    }

    public void setCloseIcon(Drawable drawable2) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIcon(drawable2);
        }
        this.updateAccessibilityDelegate();
    }

    public void setCloseIconContentDescription(CharSequence charSequence) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconContentDescription(charSequence);
    }

    @Deprecated
    public void setCloseIconEnabled(boolean bl) {
        this.setCloseIconVisible(bl);
    }

    @Deprecated
    public void setCloseIconEnabledResource(int n) {
        this.setCloseIconVisible(n);
    }

    public void setCloseIconEndPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconEndPadding(f);
    }

    public void setCloseIconEndPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconEndPaddingResource(n);
    }

    public void setCloseIconResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconResource(n);
        }
        this.updateAccessibilityDelegate();
    }

    public void setCloseIconSize(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconSize(f);
    }

    public void setCloseIconSizeResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconSizeResource(n);
    }

    public void setCloseIconStartPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconStartPadding(f);
    }

    public void setCloseIconStartPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconStartPaddingResource(n);
    }

    public void setCloseIconTint(ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconTint(colorStateList);
    }

    public void setCloseIconTintResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setCloseIconTintResource(n);
    }

    public void setCloseIconVisible(int n) {
        this.setCloseIconVisible(this.getResources().getBoolean(n));
    }

    public void setCloseIconVisible(boolean bl) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconVisible(bl);
        }
        this.updateAccessibilityDelegate();
    }

    public void setCompoundDrawables(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (drawable2 != null) throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        if (drawable4 != null) throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        super.setCompoundDrawables(drawable2, drawable3, drawable4, drawable5);
    }

    public void setCompoundDrawablesRelative(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (drawable2 != null) throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        if (drawable4 != null) throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        super.setCompoundDrawablesRelative(drawable2, drawable3, drawable4, drawable5);
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int n, int n2, int n3, int n4) {
        if (n != 0) throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        if (n3 != 0) throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(n, n2, n3, n4);
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (drawable2 != null) throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        if (drawable4 != null) throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int n, int n2, int n3, int n4) {
        if (n != 0) throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        if (n3 != 0) throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        super.setCompoundDrawablesWithIntrinsicBounds(n, n2, n3, n4);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (drawable2 != null) throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        if (drawable4 != null) throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        super.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
    }

    public void setElevation(float f) {
        super.setElevation(f);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setElevation(f);
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.chipDrawable == null) {
            return;
        }
        if (truncateAt == TextUtils.TruncateAt.MARQUEE) throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        super.setEllipsize(truncateAt);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setEllipsize(truncateAt);
    }

    public void setEnsureMinTouchTargetSize(boolean bl) {
        this.ensureMinTouchTargetSize = bl;
        this.ensureAccessibleTouchTarget(this.minTouchTargetSize);
    }

    public void setGravity(int n) {
        if (n != 8388627) {
            Log.w((String)TAG, (String)"Chip text must be vertically center and start aligned");
            return;
        }
        super.setGravity(n);
    }

    public void setHideMotionSpec(MotionSpec motionSpec) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setHideMotionSpec(motionSpec);
    }

    public void setHideMotionSpecResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setHideMotionSpecResource(n);
    }

    public void setIconEndPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setIconEndPadding(f);
    }

    public void setIconEndPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setIconEndPaddingResource(n);
    }

    public void setIconStartPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setIconStartPadding(f);
    }

    public void setIconStartPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setIconStartPaddingResource(n);
    }

    public void setLayoutDirection(int n) {
        if (this.chipDrawable == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 17) return;
        super.setLayoutDirection(n);
    }

    public void setLines(int n) {
        if (n > 1) throw new UnsupportedOperationException("Chip does not support multi-line text");
        super.setLines(n);
    }

    public void setMaxLines(int n) {
        if (n > 1) throw new UnsupportedOperationException("Chip does not support multi-line text");
        super.setMaxLines(n);
    }

    public void setMaxWidth(int n) {
        super.setMaxWidth(n);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setMaxWidth(n);
    }

    public void setMinLines(int n) {
        if (n > 1) throw new UnsupportedOperationException("Chip does not support multi-line text");
        super.setMinLines(n);
    }

    void setOnCheckedChangeListenerInternal(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListenerInternal = onCheckedChangeListener;
    }

    public void setOnCloseIconClickListener(View.OnClickListener onClickListener) {
        this.onCloseIconClickListener = onClickListener;
        this.updateAccessibilityDelegate();
    }

    public void setRippleColor(ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setRippleColor(colorStateList);
        }
        if (this.chipDrawable.getUseCompatRipple()) return;
        this.updateFrameworkRippleBackground();
    }

    public void setRippleColorResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setRippleColorResource(n);
        if (this.chipDrawable.getUseCompatRipple()) return;
        this.updateFrameworkRippleBackground();
    }

    @Override
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.chipDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    public void setShowMotionSpec(MotionSpec motionSpec) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setShowMotionSpec(motionSpec);
    }

    public void setShowMotionSpecResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setShowMotionSpecResource(n);
    }

    public void setSingleLine(boolean bl) {
        if (!bl) throw new UnsupportedOperationException("Chip does not support multi-line text");
        super.setSingleLine(bl);
    }

    public void setText(CharSequence object, TextView.BufferType bufferType) {
        if (this.chipDrawable == null) {
            return;
        }
        CharSequence charSequence = object;
        if (object == null) {
            charSequence = "";
        }
        object = this.chipDrawable.shouldDrawText() ? null : charSequence;
        super.setText((CharSequence)object, bufferType);
        object = this.chipDrawable;
        if (object == null) return;
        ((ChipDrawable)object).setText(charSequence);
    }

    public void setTextAppearance(int n) {
        super.setTextAppearance(n);
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearanceResource(n);
        }
        this.updateTextPaintDrawState();
    }

    public void setTextAppearance(Context object, int n) {
        super.setTextAppearance((Context)object, n);
        object = this.chipDrawable;
        if (object != null) {
            ((ChipDrawable)object).setTextAppearanceResource(n);
        }
        this.updateTextPaintDrawState();
    }

    public void setTextAppearance(TextAppearance textAppearance) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearance(textAppearance);
        }
        this.updateTextPaintDrawState();
    }

    public void setTextAppearanceResource(int n) {
        this.setTextAppearance(this.getContext(), n);
    }

    public void setTextEndPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setTextEndPadding(f);
    }

    public void setTextEndPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setTextEndPaddingResource(n);
    }

    public void setTextStartPadding(float f) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setTextStartPadding(f);
    }

    public void setTextStartPaddingResource(int n) {
        ChipDrawable chipDrawable = this.chipDrawable;
        if (chipDrawable == null) return;
        chipDrawable.setTextStartPaddingResource(n);
    }

    public boolean shouldEnsureMinTouchTargetSize() {
        return this.ensureMinTouchTargetSize;
    }

    private class ChipTouchHelper
    extends ExploreByTouchHelper {
        ChipTouchHelper(Chip chip2) {
            super((View)chip2);
        }

        @Override
        protected int getVirtualViewAt(float f, float f2) {
            if (!Chip.this.hasCloseIcon()) return 0;
            if (!Chip.this.getCloseIconTouchBounds().contains(f, f2)) return 0;
            return 1;
        }

        @Override
        protected void getVisibleVirtualViews(List<Integer> list) {
            list.add(0);
            if (!Chip.this.hasCloseIcon()) return;
            if (!Chip.this.isCloseIconVisible()) return;
            if (Chip.this.onCloseIconClickListener == null) return;
            list.add(1);
        }

        @Override
        protected boolean onPerformActionForVirtualView(int n, int n2, Bundle bundle) {
            if (n2 != 16) return false;
            if (n == 0) {
                return Chip.this.performClick();
            }
            if (n != 1) return false;
            return Chip.this.performCloseIconClick();
        }

        @Override
        protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            CharSequence charSequence;
            accessibilityNodeInfoCompat.setCheckable(Chip.this.isCheckable());
            accessibilityNodeInfoCompat.setClickable(Chip.this.isClickable());
            if (!Chip.this.isCheckable() && !Chip.this.isClickable()) {
                accessibilityNodeInfoCompat.setClassName(Chip.GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
            } else {
                charSequence = Chip.this.isCheckable() ? Chip.COMPOUND_BUTTON_ACCESSIBILITY_CLASS_NAME : Chip.BUTTON_ACCESSIBILITY_CLASS_NAME;
                accessibilityNodeInfoCompat.setClassName(charSequence);
            }
            charSequence = Chip.this.getText();
            if (Build.VERSION.SDK_INT >= 23) {
                accessibilityNodeInfoCompat.setText(charSequence);
                return;
            }
            accessibilityNodeInfoCompat.setContentDescription(charSequence);
        }

        @Override
        protected void onPopulateNodeForVirtualView(int n, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            CharSequence charSequence = "";
            if (n != 1) {
                accessibilityNodeInfoCompat.setContentDescription("");
                accessibilityNodeInfoCompat.setBoundsInParent(EMPTY_BOUNDS);
                return;
            }
            CharSequence charSequence2 = Chip.this.getCloseIconContentDescription();
            if (charSequence2 != null) {
                accessibilityNodeInfoCompat.setContentDescription(charSequence2);
            } else {
                charSequence2 = Chip.this.getText();
                Context context = Chip.this.getContext();
                n = R.string.mtrl_chip_close_icon_content_description;
                if (!TextUtils.isEmpty((CharSequence)charSequence2)) {
                    charSequence = charSequence2;
                }
                accessibilityNodeInfoCompat.setContentDescription(context.getString(n, new Object[]{charSequence}).trim());
            }
            accessibilityNodeInfoCompat.setBoundsInParent(Chip.this.getCloseIconTouchBoundsInt());
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            accessibilityNodeInfoCompat.setEnabled(Chip.this.isEnabled());
        }

        @Override
        protected void onVirtualViewKeyboardFocusChanged(int n, boolean bl) {
            if (n != 1) return;
            Chip.this.closeIconFocused = bl;
            Chip.this.refreshDrawableState();
        }
    }

}

