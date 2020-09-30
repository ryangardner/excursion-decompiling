/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.text.method.KeyListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnTouchListener
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.widget.AutoCompleteTextView
 *  android.widget.AutoCompleteTextView$OnDismissListener
 *  android.widget.EditText
 *  android.widget.Spinner
 */
package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.EndIconDelegate;
import com.google.android.material.textfield.TextInputLayout;

class DropdownMenuEndIconDelegate
extends EndIconDelegate {
    private static final int ANIMATION_FADE_IN_DURATION = 67;
    private static final int ANIMATION_FADE_OUT_DURATION = 50;
    private static final boolean IS_LOLLIPOP;
    private final TextInputLayout.AccessibilityDelegate accessibilityDelegate = new TextInputLayout.AccessibilityDelegate(this.textInputLayout){

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (DropdownMenuEndIconDelegate.this.textInputLayout.getEditText().getKeyListener() == null) {
                accessibilityNodeInfoCompat.setClassName(Spinner.class.getName());
            }
            if (!accessibilityNodeInfoCompat.isShowingHintText()) return;
            accessibilityNodeInfoCompat.setHintText(null);
        }

        @Override
        public void onPopulateAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent((View)object, accessibilityEvent);
            object = DropdownMenuEndIconDelegate.this;
            object = ((DropdownMenuEndIconDelegate)object).castAutoCompleteTextViewOrThrow(object.textInputLayout.getEditText());
            if (accessibilityEvent.getEventType() != 1) return;
            if (!DropdownMenuEndIconDelegate.this.accessibilityManager.isTouchExplorationEnabled()) return;
            DropdownMenuEndIconDelegate.this.showHideDropdown((AutoCompleteTextView)object);
        }
    };
    private AccessibilityManager accessibilityManager;
    private final TextInputLayout.OnEditTextAttachedListener dropdownMenuOnEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener(){

        @Override
        public void onEditTextAttached(TextInputLayout textInputLayout) {
            AutoCompleteTextView autoCompleteTextView = DropdownMenuEndIconDelegate.this.castAutoCompleteTextViewOrThrow(textInputLayout.getEditText());
            DropdownMenuEndIconDelegate.this.setPopupBackground(autoCompleteTextView);
            DropdownMenuEndIconDelegate.this.addRippleEffect(autoCompleteTextView);
            DropdownMenuEndIconDelegate.this.setUpDropdownShowHideBehavior(autoCompleteTextView);
            autoCompleteTextView.setThreshold(0);
            autoCompleteTextView.removeTextChangedListener(DropdownMenuEndIconDelegate.this.exposedDropdownEndIconTextWatcher);
            autoCompleteTextView.addTextChangedListener(DropdownMenuEndIconDelegate.this.exposedDropdownEndIconTextWatcher);
            textInputLayout.setEndIconCheckable(true);
            textInputLayout.setErrorIconDrawable(null);
            textInputLayout.setTextInputAccessibilityDelegate(DropdownMenuEndIconDelegate.this.accessibilityDelegate);
            textInputLayout.setEndIconVisible(true);
        }
    };
    private long dropdownPopupActivatedAt = Long.MAX_VALUE;
    private boolean dropdownPopupDirty = false;
    private final TextInputLayout.OnEndIconChangedListener endIconChangedListener = new TextInputLayout.OnEndIconChangedListener(){

        @Override
        public void onEndIconChanged(TextInputLayout textInputLayout, int n) {
            if ((textInputLayout = (AutoCompleteTextView)textInputLayout.getEditText()) == null) return;
            if (n != 3) return;
            textInputLayout.removeTextChangedListener(DropdownMenuEndIconDelegate.this.exposedDropdownEndIconTextWatcher);
            if (textInputLayout.getOnFocusChangeListener() == DropdownMenuEndIconDelegate.this.onFocusChangeListener) {
                textInputLayout.setOnFocusChangeListener(null);
            }
            textInputLayout.setOnTouchListener(null);
            if (!IS_LOLLIPOP) return;
            textInputLayout.setOnDismissListener(null);
        }
    };
    private final TextWatcher exposedDropdownEndIconTextWatcher = new TextWatcher(){

        public void afterTextChanged(Editable object) {
            object = DropdownMenuEndIconDelegate.this;
            object = ((DropdownMenuEndIconDelegate)object).castAutoCompleteTextViewOrThrow(object.textInputLayout.getEditText());
            object.post(new Runnable((AutoCompleteTextView)object){
                final /* synthetic */ AutoCompleteTextView val$editText;
                {
                    this.val$editText = autoCompleteTextView;
                }

                @Override
                public void run() {
                    boolean bl = this.val$editText.isPopupShowing();
                    DropdownMenuEndIconDelegate.this.setEndIconChecked(bl);
                    DropdownMenuEndIconDelegate.this.dropdownPopupDirty = bl;
                }
            });
        }

        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

    };
    private ValueAnimator fadeInAnim;
    private ValueAnimator fadeOutAnim;
    private StateListDrawable filledPopupBackground;
    private boolean isEndIconChecked = false;
    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener(){

        public void onFocusChange(View view, boolean bl) {
            DropdownMenuEndIconDelegate.this.textInputLayout.setEndIconActivated(bl);
            if (bl) return;
            DropdownMenuEndIconDelegate.this.setEndIconChecked(false);
            DropdownMenuEndIconDelegate.this.dropdownPopupDirty = false;
        }
    };
    private MaterialShapeDrawable outlinedPopupBackground;

    static {
        boolean bl = Build.VERSION.SDK_INT >= 21;
        IS_LOLLIPOP = bl;
    }

    DropdownMenuEndIconDelegate(TextInputLayout textInputLayout) {
        super(textInputLayout);
    }

    private void addRippleEffect(AutoCompleteTextView autoCompleteTextView) {
        if (autoCompleteTextView.getKeyListener() != null) {
            return;
        }
        int n = this.textInputLayout.getBoxBackgroundMode();
        MaterialShapeDrawable materialShapeDrawable = this.textInputLayout.getBoxBackground();
        int n2 = MaterialColors.getColor((View)autoCompleteTextView, R.attr.colorControlHighlight);
        int[][] arrarrn = new int[][]{{16842919}, new int[0]};
        if (n == 2) {
            this.addRippleEffectOnOutlinedLayout(autoCompleteTextView, n2, arrarrn, materialShapeDrawable);
            return;
        }
        if (n != 1) return;
        this.addRippleEffectOnFilledLayout(autoCompleteTextView, n2, arrarrn, materialShapeDrawable);
    }

    private void addRippleEffectOnFilledLayout(AutoCompleteTextView autoCompleteTextView, int n, int[][] layerDrawable, MaterialShapeDrawable materialShapeDrawable) {
        int n2 = this.textInputLayout.getBoxBackgroundColor();
        n = MaterialColors.layer(n, n2, 0.1f);
        int[] arrn = new int[]{n, n2};
        if (IS_LOLLIPOP) {
            ViewCompat.setBackground((View)autoCompleteTextView, (Drawable)new RippleDrawable(new ColorStateList((int[][])layerDrawable, arrn), (Drawable)materialShapeDrawable, (Drawable)materialShapeDrawable));
            return;
        }
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
        materialShapeDrawable2.setFillColor(new ColorStateList((int[][])layerDrawable, arrn));
        layerDrawable = new LayerDrawable(new Drawable[]{materialShapeDrawable, materialShapeDrawable2});
        int n3 = ViewCompat.getPaddingStart((View)autoCompleteTextView);
        int n4 = autoCompleteTextView.getPaddingTop();
        n2 = ViewCompat.getPaddingEnd((View)autoCompleteTextView);
        n = autoCompleteTextView.getPaddingBottom();
        ViewCompat.setBackground((View)autoCompleteTextView, (Drawable)layerDrawable);
        ViewCompat.setPaddingRelative((View)autoCompleteTextView, n3, n4, n2, n);
    }

    private void addRippleEffectOnOutlinedLayout(AutoCompleteTextView autoCompleteTextView, int n, int[][] layerDrawable, MaterialShapeDrawable materialShapeDrawable) {
        int n2 = MaterialColors.getColor((View)autoCompleteTextView, R.attr.colorSurface);
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
        n = MaterialColors.layer(n, n2, 0.1f);
        materialShapeDrawable2.setFillColor(new ColorStateList((int[][])layerDrawable, new int[]{n, 0}));
        if (IS_LOLLIPOP) {
            materialShapeDrawable2.setTint(n2);
            layerDrawable = new ColorStateList((int[][])layerDrawable, new int[]{n, n2});
            MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
            materialShapeDrawable3.setTint(-1);
            layerDrawable = new LayerDrawable(new Drawable[]{new RippleDrawable((ColorStateList)layerDrawable, (Drawable)materialShapeDrawable2, (Drawable)materialShapeDrawable3), materialShapeDrawable});
        } else {
            layerDrawable = new LayerDrawable(new Drawable[]{materialShapeDrawable2, materialShapeDrawable});
        }
        ViewCompat.setBackground((View)autoCompleteTextView, (Drawable)layerDrawable);
    }

    private AutoCompleteTextView castAutoCompleteTextViewOrThrow(EditText editText) {
        if (!(editText instanceof AutoCompleteTextView)) throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
        return (AutoCompleteTextView)editText;
    }

    private ValueAnimator getAlphaAnimator(int n, float ... valueAnimator) {
        valueAnimator = ValueAnimator.ofFloat((float[])valueAnimator);
        valueAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        valueAnimator.setDuration((long)n);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                DropdownMenuEndIconDelegate.this.endIconView.setAlpha(f);
            }
        });
        return valueAnimator;
    }

    private MaterialShapeDrawable getPopUpMaterialShapeDrawable(float f, float f2, float f3, int n) {
        ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder().setTopLeftCornerSize(f).setTopRightCornerSize(f).setBottomLeftCornerSize(f2).setBottomRightCornerSize(f2).build();
        MaterialShapeDrawable materialShapeDrawable = MaterialShapeDrawable.createWithElevationOverlay(this.context, f3);
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.setPadding(0, n, 0, n);
        return materialShapeDrawable;
    }

    private void initAnimators() {
        ValueAnimator valueAnimator;
        this.fadeInAnim = this.getAlphaAnimator(67, 0.0f, 1.0f);
        this.fadeOutAnim = valueAnimator = this.getAlphaAnimator(50, 1.0f, 0.0f);
        valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                DropdownMenuEndIconDelegate.this.endIconView.setChecked(DropdownMenuEndIconDelegate.this.isEndIconChecked);
                DropdownMenuEndIconDelegate.this.fadeInAnim.start();
            }
        });
    }

    private boolean isDropdownPopupActive() {
        long l = System.currentTimeMillis() - this.dropdownPopupActivatedAt;
        if (l < 0L) return true;
        if (l > 300L) return true;
        return false;
    }

    private void setEndIconChecked(boolean bl) {
        if (this.isEndIconChecked == bl) return;
        this.isEndIconChecked = bl;
        this.fadeInAnim.cancel();
        this.fadeOutAnim.start();
    }

    private void setPopupBackground(AutoCompleteTextView autoCompleteTextView) {
        if (!IS_LOLLIPOP) return;
        int n = this.textInputLayout.getBoxBackgroundMode();
        if (n == 2) {
            autoCompleteTextView.setDropDownBackgroundDrawable((Drawable)this.outlinedPopupBackground);
            return;
        }
        if (n != 1) return;
        autoCompleteTextView.setDropDownBackgroundDrawable((Drawable)this.filledPopupBackground);
    }

    private void setUpDropdownShowHideBehavior(final AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 1) return false;
                if (DropdownMenuEndIconDelegate.this.isDropdownPopupActive()) {
                    DropdownMenuEndIconDelegate.this.dropdownPopupDirty = false;
                }
                DropdownMenuEndIconDelegate.this.showHideDropdown(autoCompleteTextView);
                return false;
            }
        });
        autoCompleteTextView.setOnFocusChangeListener(this.onFocusChangeListener);
        if (!IS_LOLLIPOP) return;
        autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener(){

            public void onDismiss() {
                DropdownMenuEndIconDelegate.this.dropdownPopupDirty = true;
                DropdownMenuEndIconDelegate.this.dropdownPopupActivatedAt = System.currentTimeMillis();
                DropdownMenuEndIconDelegate.this.setEndIconChecked(false);
            }
        });
    }

    private void showHideDropdown(AutoCompleteTextView autoCompleteTextView) {
        if (autoCompleteTextView == null) {
            return;
        }
        if (this.isDropdownPopupActive()) {
            this.dropdownPopupDirty = false;
        }
        if (this.dropdownPopupDirty) {
            this.dropdownPopupDirty = false;
            return;
        }
        if (IS_LOLLIPOP) {
            this.setEndIconChecked(this.isEndIconChecked ^ true);
        } else {
            this.isEndIconChecked ^= true;
            this.endIconView.toggle();
        }
        if (this.isEndIconChecked) {
            autoCompleteTextView.requestFocus();
            autoCompleteTextView.showDropDown();
            return;
        }
        autoCompleteTextView.dismissDropDown();
    }

    @Override
    void initialize() {
        StateListDrawable stateListDrawable;
        float f = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_shape_corner_size_small_component);
        float f2 = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
        int n = this.context.getResources().getDimensionPixelOffset(R.dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        MaterialShapeDrawable materialShapeDrawable = this.getPopUpMaterialShapeDrawable(f, f, f2, n);
        MaterialShapeDrawable materialShapeDrawable2 = this.getPopUpMaterialShapeDrawable(0.0f, f, f2, n);
        this.outlinedPopupBackground = materialShapeDrawable;
        this.filledPopupBackground = stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842922}, (Drawable)materialShapeDrawable);
        this.filledPopupBackground.addState(new int[0], (Drawable)materialShapeDrawable2);
        n = IS_LOLLIPOP ? R.drawable.mtrl_dropdown_arrow : R.drawable.mtrl_ic_arrow_drop_down;
        this.textInputLayout.setEndIconDrawable(AppCompatResources.getDrawable(this.context, n));
        this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.exposed_dropdown_menu_content_description));
        this.textInputLayout.setEndIconOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = (AutoCompleteTextView)DropdownMenuEndIconDelegate.this.textInputLayout.getEditText();
                DropdownMenuEndIconDelegate.this.showHideDropdown((AutoCompleteTextView)view);
            }
        });
        this.textInputLayout.addOnEditTextAttachedListener(this.dropdownMenuOnEditTextAttachedListener);
        this.textInputLayout.addOnEndIconChangedListener(this.endIconChangedListener);
        this.initAnimators();
        ViewCompat.setImportantForAccessibility((View)this.endIconView, 2);
        this.accessibilityManager = (AccessibilityManager)this.context.getSystemService("accessibility");
    }

    @Override
    boolean isBoxBackgroundModeSupported(int n) {
        if (n == 0) return false;
        return true;
    }

    @Override
    boolean shouldTintIconOnError() {
        return true;
    }

}

