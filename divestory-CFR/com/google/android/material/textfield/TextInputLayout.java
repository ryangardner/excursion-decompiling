/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewStructure
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.textfield;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.TintableBackgroundView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.ClearTextEndIconDelegate;
import com.google.android.material.textfield.CustomEndIconDelegate;
import com.google.android.material.textfield.CutoutDrawable;
import com.google.android.material.textfield.DropdownMenuEndIconDelegate;
import com.google.android.material.textfield.EndIconDelegate;
import com.google.android.material.textfield.IndicatorViewController;
import com.google.android.material.textfield.NoEndIconDelegate;
import com.google.android.material.textfield.PasswordToggleEndIconDelegate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TextInputLayout
extends LinearLayout {
    public static final int BOX_BACKGROUND_FILLED = 1;
    public static final int BOX_BACKGROUND_NONE = 0;
    public static final int BOX_BACKGROUND_OUTLINE = 2;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_TextInputLayout;
    public static final int END_ICON_CLEAR_TEXT = 2;
    public static final int END_ICON_CUSTOM = -1;
    public static final int END_ICON_DROPDOWN_MENU = 3;
    public static final int END_ICON_NONE = 0;
    public static final int END_ICON_PASSWORD_TOGGLE = 1;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final int LABEL_SCALE_ANIMATION_DURATION = 167;
    private static final String LOG_TAG = "TextInputLayout";
    private ValueAnimator animator;
    private MaterialShapeDrawable boxBackground;
    private int boxBackgroundColor;
    private int boxBackgroundMode;
    private final int boxCollapsedPaddingTopPx;
    private final int boxLabelCutoutPaddingPx;
    private int boxStrokeColor;
    private int boxStrokeWidthDefaultPx;
    private int boxStrokeWidthFocusedPx;
    private int boxStrokeWidthPx;
    private MaterialShapeDrawable boxUnderline;
    final CollapsingTextHelper collapsingTextHelper = new CollapsingTextHelper((View)this);
    boolean counterEnabled;
    private int counterMaxLength;
    private int counterOverflowTextAppearance;
    private ColorStateList counterOverflowTextColor;
    private boolean counterOverflowed;
    private int counterTextAppearance;
    private ColorStateList counterTextColor;
    private TextView counterView;
    private int defaultFilledBackgroundColor;
    private ColorStateList defaultHintTextColor;
    private int defaultStrokeColor;
    private int disabledColor;
    private int disabledFilledBackgroundColor;
    EditText editText;
    private final LinkedHashSet<OnEditTextAttachedListener> editTextAttachedListeners = new LinkedHashSet();
    private Drawable endDummyDrawable;
    private int endDummyDrawableWidth;
    private final LinkedHashSet<OnEndIconChangedListener> endIconChangedListeners = new LinkedHashSet();
    private final SparseArray<EndIconDelegate> endIconDelegates = new SparseArray();
    private final FrameLayout endIconFrame;
    private int endIconMode = 0;
    private View.OnLongClickListener endIconOnLongClickListener;
    private ColorStateList endIconTintList;
    private PorterDuff.Mode endIconTintMode;
    private final CheckableImageButton endIconView;
    private final LinearLayout endLayout;
    private View.OnLongClickListener errorIconOnLongClickListener;
    private ColorStateList errorIconTintList;
    private final CheckableImageButton errorIconView;
    private int focusedFilledBackgroundColor;
    private int focusedStrokeColor;
    private ColorStateList focusedTextColor;
    private boolean hasEndIconTintList;
    private boolean hasEndIconTintMode;
    private boolean hasStartIconTintList;
    private boolean hasStartIconTintMode;
    private CharSequence hint;
    private boolean hintAnimationEnabled;
    private boolean hintEnabled;
    private boolean hintExpanded;
    private int hoveredFilledBackgroundColor;
    private int hoveredStrokeColor;
    private boolean inDrawableStateChanged;
    private final IndicatorViewController indicatorViewController = new IndicatorViewController(this);
    private final FrameLayout inputFrame;
    private boolean isProvidingHint;
    private Drawable originalEditTextEndDrawable;
    private CharSequence originalHint;
    private boolean placeholderEnabled;
    private CharSequence placeholderText;
    private int placeholderTextAppearance;
    private ColorStateList placeholderTextColor;
    private TextView placeholderTextView;
    private CharSequence prefixText;
    private final TextView prefixTextView;
    private boolean restoringSavedState;
    private ShapeAppearanceModel shapeAppearanceModel;
    private Drawable startDummyDrawable;
    private int startDummyDrawableWidth;
    private View.OnLongClickListener startIconOnLongClickListener;
    private ColorStateList startIconTintList;
    private PorterDuff.Mode startIconTintMode;
    private final CheckableImageButton startIconView;
    private final LinearLayout startLayout;
    private ColorStateList strokeErrorColor;
    private CharSequence suffixText;
    private final TextView suffixTextView;
    private final Rect tmpBoundsRect = new Rect();
    private final Rect tmpRect = new Rect();
    private final RectF tmpRectF = new RectF();
    private Typeface typeface;

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textInputStyle);
    }

    public TextInputLayout(Context object, AttributeSet object2, int n) {
        super(MaterialThemeOverlay.wrap(object, (AttributeSet)object2, n, DEF_STYLE_RES), (AttributeSet)object2, n);
        TintableBackgroundView tintableBackgroundView;
        object = this.getContext();
        this.setOrientation(1);
        this.setWillNotDraw(false);
        this.setAddStatesFromChildren(true);
        Object object3 = new FrameLayout(object);
        this.inputFrame = object3;
        object3.setAddStatesFromChildren(true);
        this.addView((View)this.inputFrame);
        object3 = new LinearLayout(object);
        this.startLayout = object3;
        object3.setOrientation(0);
        this.startLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1, 8388611));
        this.inputFrame.addView((View)this.startLayout);
        object3 = new LinearLayout(object);
        this.endLayout = object3;
        object3.setOrientation(0);
        this.endLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1, 8388613));
        this.inputFrame.addView((View)this.endLayout);
        object3 = new FrameLayout(object);
        this.endIconFrame = object3;
        object3.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1));
        this.collapsingTextHelper.setTextSizeInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.collapsingTextHelper.setPositionInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.collapsingTextHelper.setCollapsedTextGravity(8388659);
        object3 = ThemeEnforcement.obtainTintedStyledAttributes(object, (AttributeSet)object2, R.styleable.TextInputLayout, n, DEF_STYLE_RES, new int[]{R.styleable.TextInputLayout_counterTextAppearance, R.styleable.TextInputLayout_counterOverflowTextAppearance, R.styleable.TextInputLayout_errorTextAppearance, R.styleable.TextInputLayout_helperTextTextAppearance, R.styleable.TextInputLayout_hintTextAppearance});
        this.hintEnabled = ((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        this.setHint(((TintTypedArray)object3).getText(R.styleable.TextInputLayout_android_hint));
        this.hintAnimationEnabled = ((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        this.shapeAppearanceModel = ShapeAppearanceModel.builder(object, (AttributeSet)object2, n, DEF_STYLE_RES).build();
        this.boxLabelCutoutPaddingPx = object.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_label_cutout_padding);
        this.boxCollapsedPaddingTopPx = ((TintTypedArray)object3).getDimensionPixelOffset(R.styleable.TextInputLayout_boxCollapsedPaddingTop, 0);
        this.boxStrokeWidthDefaultPx = ((TintTypedArray)object3).getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidth, object.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_default));
        this.boxStrokeWidthFocusedPx = ((TintTypedArray)object3).getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidthFocused, object.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_focused));
        this.boxStrokeWidthPx = this.boxStrokeWidthDefaultPx;
        float f = ((TintTypedArray)object3).getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopStart, -1.0f);
        float f2 = ((TintTypedArray)object3).getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopEnd, -1.0f);
        float f3 = ((TintTypedArray)object3).getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomEnd, -1.0f);
        float f4 = ((TintTypedArray)object3).getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomStart, -1.0f);
        object2 = this.shapeAppearanceModel.toBuilder();
        if (f >= 0.0f) {
            ((ShapeAppearanceModel.Builder)object2).setTopLeftCornerSize(f);
        }
        if (f2 >= 0.0f) {
            ((ShapeAppearanceModel.Builder)object2).setTopRightCornerSize(f2);
        }
        if (f3 >= 0.0f) {
            ((ShapeAppearanceModel.Builder)object2).setBottomRightCornerSize(f3);
        }
        if (f4 >= 0.0f) {
            ((ShapeAppearanceModel.Builder)object2).setBottomLeftCornerSize(f4);
        }
        this.shapeAppearanceModel = ((ShapeAppearanceModel.Builder)object2).build();
        object2 = MaterialResources.getColorStateList(object, (TintTypedArray)object3, R.styleable.TextInputLayout_boxBackgroundColor);
        if (object2 != null) {
            this.defaultFilledBackgroundColor = n = object2.getDefaultColor();
            this.boxBackgroundColor = n;
            if (object2.isStateful()) {
                this.disabledFilledBackgroundColor = object2.getColorForState(new int[]{-16842910}, -1);
                this.focusedFilledBackgroundColor = object2.getColorForState(new int[]{16842908, 16842910}, -1);
                this.hoveredFilledBackgroundColor = object2.getColorForState(new int[]{16843623, 16842910}, -1);
            } else {
                this.focusedFilledBackgroundColor = this.defaultFilledBackgroundColor;
                object2 = AppCompatResources.getColorStateList(object, R.color.mtrl_filled_background_color);
                this.disabledFilledBackgroundColor = object2.getColorForState(new int[]{-16842910}, -1);
                this.hoveredFilledBackgroundColor = object2.getColorForState(new int[]{16843623}, -1);
            }
        } else {
            this.boxBackgroundColor = 0;
            this.defaultFilledBackgroundColor = 0;
            this.disabledFilledBackgroundColor = 0;
            this.focusedFilledBackgroundColor = 0;
            this.hoveredFilledBackgroundColor = 0;
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            object2 = ((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.focusedTextColor = object2;
            this.defaultHintTextColor = object2;
        }
        object2 = MaterialResources.getColorStateList(object, (TintTypedArray)object3, R.styleable.TextInputLayout_boxStrokeColor);
        this.focusedStrokeColor = ((TintTypedArray)object3).getColor(R.styleable.TextInputLayout_boxStrokeColor, 0);
        this.defaultStrokeColor = ContextCompat.getColor(object, R.color.mtrl_textinput_default_box_stroke_color);
        this.disabledColor = ContextCompat.getColor(object, R.color.mtrl_textinput_disabled_color);
        this.hoveredStrokeColor = ContextCompat.getColor(object, R.color.mtrl_textinput_hovered_box_stroke_color);
        if (object2 != null) {
            this.setBoxStrokeColorStateList((ColorStateList)object2);
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_boxStrokeErrorColor)) {
            this.setBoxStrokeErrorColor(MaterialResources.getColorStateList(object, (TintTypedArray)object3, R.styleable.TextInputLayout_boxStrokeErrorColor));
        }
        if (((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            this.setHintTextAppearance(((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        int n2 = ((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        object2 = ((TintTypedArray)object3).getText(R.styleable.TextInputLayout_errorContentDescription);
        boolean bl = ((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        Object object4 = (CheckableImageButton)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_text_input_end_icon, (ViewGroup)this.endLayout, false);
        this.errorIconView = object4;
        object4.setVisibility(8);
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_errorIconDrawable)) {
            this.setErrorIconDrawable(((TintTypedArray)object3).getDrawable(R.styleable.TextInputLayout_errorIconDrawable));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_errorIconTint)) {
            this.setErrorIconTintList(MaterialResources.getColorStateList(object, (TintTypedArray)object3, R.styleable.TextInputLayout_errorIconTint));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_errorIconTintMode)) {
            this.setErrorIconTintMode(ViewUtils.parseTintMode(((TintTypedArray)object3).getInt(R.styleable.TextInputLayout_errorIconTintMode, -1), null));
        }
        this.errorIconView.setContentDescription(this.getResources().getText(R.string.error_icon_content_description));
        ViewCompat.setImportantForAccessibility((View)this.errorIconView, 2);
        this.errorIconView.setClickable(false);
        this.errorIconView.setPressable(false);
        this.errorIconView.setFocusable(false);
        int n3 = ((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_helperTextTextAppearance, 0);
        boolean bl2 = ((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_helperTextEnabled, false);
        CharSequence charSequence = ((TintTypedArray)object3).getText(R.styleable.TextInputLayout_helperText);
        n = ((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_placeholderTextAppearance, 0);
        CharSequence charSequence2 = ((TintTypedArray)object3).getText(R.styleable.TextInputLayout_placeholderText);
        int n4 = ((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_prefixTextAppearance, 0);
        CharSequence charSequence3 = ((TintTypedArray)object3).getText(R.styleable.TextInputLayout_prefixText);
        int n5 = ((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_suffixTextAppearance, 0);
        object4 = ((TintTypedArray)object3).getText(R.styleable.TextInputLayout_suffixText);
        boolean bl3 = ((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        this.setCounterMaxLength(((TintTypedArray)object3).getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.counterTextAppearance = ((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.counterOverflowTextAppearance = ((TintTypedArray)object3).getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.startIconView = tintableBackgroundView = (CheckableImageButton)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_text_input_start_icon, (ViewGroup)this.startLayout, false);
        tintableBackgroundView.setVisibility(8);
        this.setStartIconOnClickListener(null);
        this.setStartIconOnLongClickListener(null);
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_startIconDrawable)) {
            this.setStartIconDrawable(((TintTypedArray)object3).getDrawable(R.styleable.TextInputLayout_startIconDrawable));
            if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_startIconContentDescription)) {
                this.setStartIconContentDescription(((TintTypedArray)object3).getText(R.styleable.TextInputLayout_startIconContentDescription));
            }
            this.setStartIconCheckable(((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_startIconCheckable, true));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_startIconTint)) {
            this.setStartIconTintList(MaterialResources.getColorStateList(object, (TintTypedArray)object3, R.styleable.TextInputLayout_startIconTint));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_startIconTintMode)) {
            this.setStartIconTintMode(ViewUtils.parseTintMode(((TintTypedArray)object3).getInt(R.styleable.TextInputLayout_startIconTintMode, -1), null));
        }
        this.setBoxBackgroundMode(((TintTypedArray)object3).getInt(R.styleable.TextInputLayout_boxBackgroundMode, 0));
        this.endIconView = tintableBackgroundView = (CheckableImageButton)LayoutInflater.from((Context)this.getContext()).inflate(R.layout.design_text_input_end_icon, (ViewGroup)this.endIconFrame, false);
        this.endIconFrame.addView((View)tintableBackgroundView);
        this.endIconView.setVisibility(8);
        this.endIconDelegates.append(-1, (Object)new CustomEndIconDelegate(this));
        this.endIconDelegates.append(0, (Object)new NoEndIconDelegate(this));
        this.endIconDelegates.append(1, (Object)new PasswordToggleEndIconDelegate(this));
        this.endIconDelegates.append(2, (Object)new ClearTextEndIconDelegate(this));
        this.endIconDelegates.append(3, (Object)new DropdownMenuEndIconDelegate(this));
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_endIconMode)) {
            this.setEndIconMode(((TintTypedArray)object3).getInt(R.styleable.TextInputLayout_endIconMode, 0));
            if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_endIconDrawable)) {
                this.setEndIconDrawable(((TintTypedArray)object3).getDrawable(R.styleable.TextInputLayout_endIconDrawable));
            }
            if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_endIconContentDescription)) {
                this.setEndIconContentDescription(((TintTypedArray)object3).getText(R.styleable.TextInputLayout_endIconContentDescription));
            }
            this.setEndIconCheckable(((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_endIconCheckable, true));
        } else if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_passwordToggleEnabled)) {
            this.setEndIconMode((int)((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false));
            this.setEndIconDrawable(((TintTypedArray)object3).getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable));
            this.setEndIconContentDescription(((TintTypedArray)object3).getText(R.styleable.TextInputLayout_passwordToggleContentDescription));
            if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
                this.setEndIconTintList(MaterialResources.getColorStateList(object, (TintTypedArray)object3, R.styleable.TextInputLayout_passwordToggleTint));
            }
            if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
                this.setEndIconTintMode(ViewUtils.parseTintMode(((TintTypedArray)object3).getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null));
            }
        }
        if (!((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_passwordToggleEnabled)) {
            if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_endIconTint)) {
                this.setEndIconTintList(MaterialResources.getColorStateList(object, (TintTypedArray)object3, R.styleable.TextInputLayout_endIconTint));
            }
            if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_endIconTintMode)) {
                this.setEndIconTintMode(ViewUtils.parseTintMode(((TintTypedArray)object3).getInt(R.styleable.TextInputLayout_endIconTintMode, -1), null));
            }
        }
        tintableBackgroundView = new AppCompatTextView((Context)object);
        this.prefixTextView = tintableBackgroundView;
        tintableBackgroundView.setId(R.id.textinput_prefix_text);
        this.prefixTextView.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        ViewCompat.setAccessibilityLiveRegion((View)this.prefixTextView, 1);
        this.startLayout.addView((View)this.startIconView);
        this.startLayout.addView((View)this.prefixTextView);
        object = new AppCompatTextView((Context)object);
        this.suffixTextView = object;
        object.setId(R.id.textinput_suffix_text);
        this.suffixTextView.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2, 80));
        ViewCompat.setAccessibilityLiveRegion((View)this.suffixTextView, 1);
        this.endLayout.addView((View)this.suffixTextView);
        this.endLayout.addView((View)this.errorIconView);
        this.endLayout.addView((View)this.endIconFrame);
        this.setHelperTextEnabled(bl2);
        this.setHelperText(charSequence);
        this.setHelperTextTextAppearance(n3);
        this.setErrorEnabled(bl);
        this.setErrorTextAppearance(n2);
        this.setErrorContentDescription((CharSequence)object2);
        this.setCounterTextAppearance(this.counterTextAppearance);
        this.setCounterOverflowTextAppearance(this.counterOverflowTextAppearance);
        this.setPlaceholderText(charSequence2);
        this.setPlaceholderTextAppearance(n);
        this.setPrefixText(charSequence3);
        this.setPrefixTextAppearance(n4);
        this.setSuffixText((CharSequence)object4);
        this.setSuffixTextAppearance(n5);
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_errorTextColor)) {
            this.setErrorTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_errorTextColor));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_helperTextTextColor)) {
            this.setHelperTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_helperTextTextColor));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_hintTextColor)) {
            this.setHintTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_hintTextColor));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_counterTextColor)) {
            this.setCounterTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_counterTextColor));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_counterOverflowTextColor)) {
            this.setCounterOverflowTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_counterOverflowTextColor));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_placeholderTextColor)) {
            this.setPlaceholderTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_placeholderTextColor));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_prefixTextColor)) {
            this.setPrefixTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_prefixTextColor));
        }
        if (((TintTypedArray)object3).hasValue(R.styleable.TextInputLayout_suffixTextColor)) {
            this.setSuffixTextColor(((TintTypedArray)object3).getColorStateList(R.styleable.TextInputLayout_suffixTextColor));
        }
        this.setCounterEnabled(bl3);
        this.setEnabled(((TintTypedArray)object3).getBoolean(R.styleable.TextInputLayout_android_enabled, true));
        ((TintTypedArray)object3).recycle();
        ViewCompat.setImportantForAccessibility((View)this, 2);
    }

    private void addPlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView == null) return;
        this.inputFrame.addView((View)textView);
        this.placeholderTextView.setVisibility(0);
    }

    private void applyBoxAttributes() {
        int n;
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable == null) {
            return;
        }
        materialShapeDrawable.setShapeAppearanceModel(this.shapeAppearanceModel);
        if (this.canDrawOutlineStroke()) {
            this.boxBackground.setStroke((float)this.boxStrokeWidthPx, this.boxStrokeColor);
        }
        this.boxBackgroundColor = n = this.calculateBoxBackgroundColor();
        this.boxBackground.setFillColor(ColorStateList.valueOf((int)n));
        if (this.endIconMode == 3) {
            this.editText.getBackground().invalidateSelf();
        }
        this.applyBoxUnderlineAttributes();
        this.invalidate();
    }

    private void applyBoxUnderlineAttributes() {
        if (this.boxUnderline == null) {
            return;
        }
        if (this.canDrawStroke()) {
            this.boxUnderline.setFillColor(ColorStateList.valueOf((int)this.boxStrokeColor));
        }
        this.invalidate();
    }

    private void applyCutoutPadding(RectF rectF) {
        rectF.left -= (float)this.boxLabelCutoutPaddingPx;
        rectF.top -= (float)this.boxLabelCutoutPaddingPx;
        rectF.right += (float)this.boxLabelCutoutPaddingPx;
        rectF.bottom += (float)this.boxLabelCutoutPaddingPx;
    }

    private void applyEndIconTint() {
        this.applyIconTint(this.endIconView, this.hasEndIconTintList, this.endIconTintList, this.hasEndIconTintMode, this.endIconTintMode);
    }

    private void applyIconTint(CheckableImageButton checkableImageButton, boolean bl, ColorStateList colorStateList, boolean bl2, PorterDuff.Mode mode) {
        Drawable drawable2;
        block5 : {
            Drawable drawable3;
            block6 : {
                drawable2 = drawable3 = checkableImageButton.getDrawable();
                if (drawable3 == null) break block5;
                if (bl) break block6;
                drawable2 = drawable3;
                if (!bl2) break block5;
            }
            drawable3 = DrawableCompat.wrap(drawable3).mutate();
            if (bl) {
                DrawableCompat.setTintList(drawable3, colorStateList);
            }
            drawable2 = drawable3;
            if (bl2) {
                DrawableCompat.setTintMode(drawable3, mode);
                drawable2 = drawable3;
            }
        }
        if (checkableImageButton.getDrawable() == drawable2) return;
        checkableImageButton.setImageDrawable(drawable2);
    }

    private void applyStartIconTint() {
        this.applyIconTint(this.startIconView, this.hasStartIconTintList, this.startIconTintList, this.hasStartIconTintMode, this.startIconTintMode);
    }

    private void assignBoxBackgroundByMode() {
        int n = this.boxBackgroundMode;
        if (n == 0) {
            this.boxBackground = null;
            this.boxUnderline = null;
            return;
        }
        if (n == 1) {
            this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.boxUnderline = new MaterialShapeDrawable();
            return;
        }
        if (n != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.boxBackgroundMode);
            stringBuilder.append(" is illegal; only @BoxBackgroundMode constants are supported.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.boxBackground = this.hintEnabled && !(this.boxBackground instanceof CutoutDrawable) ? new CutoutDrawable(this.shapeAppearanceModel) : new MaterialShapeDrawable(this.shapeAppearanceModel);
        this.boxUnderline = null;
    }

    private int calculateBoxBackgroundColor() {
        int n = this.boxBackgroundColor;
        if (this.boxBackgroundMode != 1) return n;
        return MaterialColors.layer(MaterialColors.getColor((View)this, R.attr.colorSurface, 0), this.boxBackgroundColor);
    }

    private Rect calculateCollapsedTextBounds(Rect rect) {
        if (this.editText == null) throw new IllegalStateException();
        Rect rect2 = this.tmpBoundsRect;
        boolean bl = ViewCompat.getLayoutDirection((View)this) == 1;
        rect2.bottom = rect.bottom;
        int n = this.boxBackgroundMode;
        if (n == 1) {
            rect2.left = this.getLabelLeftBoundAlightWithPrefix(rect.left, bl);
            rect2.top = rect.top + this.boxCollapsedPaddingTopPx;
            rect2.right = this.getLabelRightBoundAlignedWithSuffix(rect.right, bl);
            return rect2;
        }
        if (n != 2) {
            rect2.left = this.getLabelLeftBoundAlightWithPrefix(rect.left, bl);
            rect2.top = this.getPaddingTop();
            rect2.right = this.getLabelRightBoundAlignedWithSuffix(rect.right, bl);
            return rect2;
        }
        rect2.left = rect.left + this.editText.getPaddingLeft();
        rect2.top = rect.top - this.calculateLabelMarginTop();
        rect2.right = rect.right - this.editText.getPaddingRight();
        return rect2;
    }

    private int calculateExpandedLabelBottom(Rect rect, Rect rect2, float f) {
        if (!this.isSingleLineFilledTextField()) return rect.bottom - this.editText.getCompoundPaddingBottom();
        return (int)((float)rect2.top + f);
    }

    private int calculateExpandedLabelTop(Rect rect, float f) {
        if (!this.isSingleLineFilledTextField()) return rect.top + this.editText.getCompoundPaddingTop();
        return (int)((float)rect.centerY() - f / 2.0f);
    }

    private Rect calculateExpandedTextBounds(Rect rect) {
        if (this.editText == null) throw new IllegalStateException();
        Rect rect2 = this.tmpBoundsRect;
        float f = this.collapsingTextHelper.getExpandedTextHeight();
        rect2.left = rect.left + this.editText.getCompoundPaddingLeft();
        rect2.top = this.calculateExpandedLabelTop(rect, f);
        rect2.right = rect.right - this.editText.getCompoundPaddingRight();
        rect2.bottom = this.calculateExpandedLabelBottom(rect, rect2, f);
        return rect2;
    }

    private int calculateLabelMarginTop() {
        float f;
        if (!this.hintEnabled) {
            return 0;
        }
        int n = this.boxBackgroundMode;
        if (n != 0 && n != 1) {
            if (n != 2) {
                return 0;
            }
            f = this.collapsingTextHelper.getCollapsedTextHeight() / 2.0f;
            return (int)f;
        }
        f = this.collapsingTextHelper.getCollapsedTextHeight();
        return (int)f;
    }

    private boolean canDrawOutlineStroke() {
        if (this.boxBackgroundMode != 2) return false;
        if (!this.canDrawStroke()) return false;
        return true;
    }

    private boolean canDrawStroke() {
        if (this.boxStrokeWidthPx <= -1) return false;
        if (this.boxStrokeColor == 0) return false;
        return true;
    }

    private void closeCutout() {
        if (!this.cutoutEnabled()) return;
        ((CutoutDrawable)this.boxBackground).removeCutout();
    }

    private void collapseHint(boolean bl) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (bl && this.hintAnimationEnabled) {
            this.animateToExpansionFraction(1.0f);
        } else {
            this.collapsingTextHelper.setExpansionFraction(1.0f);
        }
        this.hintExpanded = false;
        if (this.cutoutEnabled()) {
            this.openCutout();
        }
        this.updatePlaceholderText();
        this.updatePrefixTextVisibility();
        this.updateSuffixTextVisibility();
    }

    private boolean cutoutEnabled() {
        if (!this.hintEnabled) return false;
        if (TextUtils.isEmpty((CharSequence)this.hint)) return false;
        if (!(this.boxBackground instanceof CutoutDrawable)) return false;
        return true;
    }

    private void dispatchOnEditTextAttached() {
        Iterator iterator2 = this.editTextAttachedListeners.iterator();
        while (iterator2.hasNext()) {
            ((OnEditTextAttachedListener)iterator2.next()).onEditTextAttached(this);
        }
    }

    private void dispatchOnEndIconChanged(int n) {
        Iterator iterator2 = this.endIconChangedListeners.iterator();
        while (iterator2.hasNext()) {
            ((OnEndIconChangedListener)iterator2.next()).onEndIconChanged(this, n);
        }
    }

    private void drawBoxUnderline(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable = this.boxUnderline;
        if (materialShapeDrawable == null) return;
        materialShapeDrawable = materialShapeDrawable.getBounds();
        ((Rect)materialShapeDrawable).top = ((Rect)materialShapeDrawable).bottom - this.boxStrokeWidthPx;
        this.boxUnderline.draw(canvas);
    }

    private void drawHint(Canvas canvas) {
        if (!this.hintEnabled) return;
        this.collapsingTextHelper.draw(canvas);
    }

    private void expandHint(boolean bl) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (bl && this.hintAnimationEnabled) {
            this.animateToExpansionFraction(0.0f);
        } else {
            this.collapsingTextHelper.setExpansionFraction(0.0f);
        }
        if (this.cutoutEnabled() && ((CutoutDrawable)this.boxBackground).hasCutout()) {
            this.closeCutout();
        }
        this.hintExpanded = true;
        this.hidePlaceholderText();
        this.updatePrefixTextVisibility();
        this.updateSuffixTextVisibility();
    }

    private EndIconDelegate getEndIconDelegate() {
        EndIconDelegate endIconDelegate = (EndIconDelegate)this.endIconDelegates.get(this.endIconMode);
        if (endIconDelegate == null) return (EndIconDelegate)this.endIconDelegates.get(0);
        return endIconDelegate;
    }

    private CheckableImageButton getEndIconToUpdateDummyDrawable() {
        if (this.errorIconView.getVisibility() == 0) {
            return this.errorIconView;
        }
        if (!this.hasEndIcon()) return null;
        if (!this.isEndIconVisible()) return null;
        return this.endIconView;
    }

    private int getLabelLeftBoundAlightWithPrefix(int n, boolean bl) {
        int n2;
        n = n2 = n + this.editText.getCompoundPaddingLeft();
        if (this.prefixText == null) return n;
        n = n2;
        if (bl) return n;
        return n2 - this.prefixTextView.getMeasuredWidth() + this.prefixTextView.getPaddingLeft();
    }

    private int getLabelRightBoundAlignedWithSuffix(int n, boolean bl) {
        int n2;
        n = n2 = n - this.editText.getCompoundPaddingRight();
        if (this.prefixText == null) return n;
        n = n2;
        if (!bl) return n;
        return n2 + (this.prefixTextView.getMeasuredWidth() - this.prefixTextView.getPaddingRight());
    }

    private boolean hasEndIcon() {
        if (this.endIconMode == 0) return false;
        return true;
    }

    private void hidePlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView == null) return;
        if (!this.placeholderEnabled) return;
        textView.setText(null);
        this.placeholderTextView.setVisibility(4);
    }

    private boolean isErrorIconVisible() {
        if (this.errorIconView.getVisibility() != 0) return false;
        return true;
    }

    private boolean isSingleLineFilledTextField() {
        int n = this.boxBackgroundMode;
        boolean bl = true;
        if (n != 1) return false;
        boolean bl2 = bl;
        if (Build.VERSION.SDK_INT < 16) return bl2;
        if (this.editText.getMinLines() > 1) return false;
        return bl;
    }

    private void onApplyBoxBackgroundMode() {
        this.assignBoxBackgroundByMode();
        this.setEditTextBoxBackground();
        this.updateTextInputBoxState();
        if (this.boxBackgroundMode == 0) return;
        this.updateInputLayoutMargins();
    }

    private void openCutout() {
        if (!this.cutoutEnabled()) {
            return;
        }
        RectF rectF = this.tmpRectF;
        this.collapsingTextHelper.getCollapsedTextActualBounds(rectF, this.editText.getWidth(), this.editText.getGravity());
        this.applyCutoutPadding(rectF);
        rectF.offset((float)(-this.getPaddingLeft()), (float)(-this.getPaddingTop()));
        ((CutoutDrawable)this.boxBackground).setCutout(rectF);
    }

    private static void recursiveSetEnabled(ViewGroup viewGroup, boolean bl) {
        int n = viewGroup.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = viewGroup.getChildAt(n2);
            view.setEnabled(bl);
            if (view instanceof ViewGroup) {
                TextInputLayout.recursiveSetEnabled((ViewGroup)view, bl);
            }
            ++n2;
        }
    }

    private void removePlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView == null) return;
        textView.setVisibility(8);
    }

    private void setEditText(EditText editText) {
        if (this.editText != null) throw new IllegalArgumentException("We already have an EditText, can only have one");
        if (this.endIconMode != 3 && !(editText instanceof TextInputEditText)) {
            Log.i((String)LOG_TAG, (String)"EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.editText = editText;
        this.onApplyBoxBackgroundMode();
        this.setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
        this.collapsingTextHelper.setTypefaces(this.editText.getTypeface());
        this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
        int n = this.editText.getGravity();
        this.collapsingTextHelper.setCollapsedTextGravity(n & -113 | 48);
        this.collapsingTextHelper.setExpandedTextGravity(n);
        this.editText.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                TextInputLayout textInputLayout = TextInputLayout.this;
                textInputLayout.updateLabelState(textInputLayout.restoringSavedState ^ true);
                if (TextInputLayout.this.counterEnabled) {
                    TextInputLayout.this.updateCounter(editable.length());
                }
                if (!TextInputLayout.this.placeholderEnabled) return;
                TextInputLayout.this.updatePlaceholderText(editable.length());
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        if (this.defaultHintTextColor == null) {
            this.defaultHintTextColor = this.editText.getHintTextColors();
        }
        if (this.hintEnabled) {
            if (TextUtils.isEmpty((CharSequence)this.hint)) {
                CharSequence charSequence;
                this.originalHint = charSequence = this.editText.getHint();
                this.setHint(charSequence);
                this.editText.setHint(null);
            }
            this.isProvidingHint = true;
        }
        if (this.counterView != null) {
            this.updateCounter(this.editText.getText().length());
        }
        this.updateEditTextBackground();
        this.indicatorViewController.adjustIndicatorPadding();
        this.startLayout.bringToFront();
        this.endLayout.bringToFront();
        this.endIconFrame.bringToFront();
        this.errorIconView.bringToFront();
        this.dispatchOnEditTextAttached();
        this.updatePrefixTextViewPadding();
        this.updateSuffixTextViewPadding();
        if (!this.isEnabled()) {
            editText.setEnabled(false);
        }
        this.updateLabelState(false, true);
    }

    private void setEditTextBoxBackground() {
        if (!this.shouldUseEditTextBackgroundForBoxBackground()) return;
        ViewCompat.setBackground((View)this.editText, this.boxBackground);
    }

    private void setErrorIconVisible(boolean bl) {
        CheckableImageButton checkableImageButton = this.errorIconView;
        int n = 0;
        int n2 = bl ? 0 : 8;
        checkableImageButton.setVisibility(n2);
        checkableImageButton = this.endIconFrame;
        n2 = n;
        if (bl) {
            n2 = 8;
        }
        checkableImageButton.setVisibility(n2);
        this.updateSuffixTextViewPadding();
        if (this.hasEndIcon()) return;
        this.updateDummyDrawables();
    }

    private void setHintInternal(CharSequence charSequence) {
        if (TextUtils.equals((CharSequence)charSequence, (CharSequence)this.hint)) return;
        this.hint = charSequence;
        this.collapsingTextHelper.setText(charSequence);
        if (this.hintExpanded) return;
        this.openCutout();
    }

    private static void setIconClickable(CheckableImageButton checkableImageButton, View.OnLongClickListener onLongClickListener) {
        boolean bl = ViewCompat.hasOnClickListeners((View)checkableImageButton);
        boolean bl2 = false;
        int n = 1;
        boolean bl3 = onLongClickListener != null;
        if (bl || bl3) {
            bl2 = true;
        }
        checkableImageButton.setFocusable(bl2);
        checkableImageButton.setClickable(bl);
        checkableImageButton.setPressable(bl);
        checkableImageButton.setLongClickable(bl3);
        if (!bl2) {
            n = 2;
        }
        ViewCompat.setImportantForAccessibility((View)checkableImageButton, n);
    }

    private static void setIconOnClickListener(CheckableImageButton checkableImageButton, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnClickListener(onClickListener);
        TextInputLayout.setIconClickable(checkableImageButton, onLongClickListener);
    }

    private static void setIconOnLongClickListener(CheckableImageButton checkableImageButton, View.OnLongClickListener onLongClickListener) {
        checkableImageButton.setOnLongClickListener(onLongClickListener);
        TextInputLayout.setIconClickable(checkableImageButton, onLongClickListener);
    }

    private void setPlaceholderTextEnabled(boolean bl) {
        if (this.placeholderEnabled == bl) {
            return;
        }
        if (bl) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.getContext());
            this.placeholderTextView = appCompatTextView;
            appCompatTextView.setId(R.id.textinput_placeholder);
            ViewCompat.setAccessibilityLiveRegion((View)this.placeholderTextView, 1);
            this.setPlaceholderTextAppearance(this.placeholderTextAppearance);
            this.setPlaceholderTextColor(this.placeholderTextColor);
            this.addPlaceholderTextView();
        } else {
            this.removePlaceholderTextView();
            this.placeholderTextView = null;
        }
        this.placeholderEnabled = bl;
    }

    private boolean shouldUpdateEndDummyDrawable() {
        if (!(this.errorIconView.getVisibility() == 0 || this.hasEndIcon() && this.isEndIconVisible())) {
            if (this.suffixText == null) return false;
        }
        if (this.endLayout.getMeasuredWidth() <= 0) return false;
        return true;
    }

    private boolean shouldUpdateStartDummyDrawable() {
        if (this.getStartIconDrawable() == null) {
            if (this.prefixText == null) return false;
        }
        if (this.startLayout.getMeasuredWidth() <= 0) return false;
        return true;
    }

    private boolean shouldUseEditTextBackgroundForBoxBackground() {
        EditText editText = this.editText;
        if (editText == null) return false;
        if (this.boxBackground == null) return false;
        if (editText.getBackground() != null) return false;
        if (this.boxBackgroundMode == 0) return false;
        return true;
    }

    private void showPlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView == null) return;
        if (!this.placeholderEnabled) return;
        textView.setText(this.placeholderText);
        this.placeholderTextView.setVisibility(0);
        this.placeholderTextView.bringToFront();
    }

    private void tintEndIconOnError(boolean bl) {
        if (bl && this.getEndIconDrawable() != null) {
            Drawable drawable2 = DrawableCompat.wrap(this.getEndIconDrawable()).mutate();
            DrawableCompat.setTint(drawable2, this.indicatorViewController.getErrorViewCurrentTextColor());
            this.endIconView.setImageDrawable(drawable2);
            return;
        }
        this.applyEndIconTint();
    }

    private void updateBoxUnderlineBounds(Rect rect) {
        if (this.boxUnderline == null) return;
        int n = rect.bottom;
        int n2 = this.boxStrokeWidthFocusedPx;
        this.boxUnderline.setBounds(rect.left, n - n2, rect.right, rect.bottom);
    }

    private void updateCounter() {
        if (this.counterView == null) return;
        EditText editText = this.editText;
        int n = editText == null ? 0 : editText.getText().length();
        this.updateCounter(n);
    }

    private static void updateCounterContentDescription(Context context, TextView textView, int n, int n2, boolean bl) {
        int n3 = bl ? R.string.character_counter_overflowed_content_description : R.string.character_counter_content_description;
        textView.setContentDescription((CharSequence)context.getString(n3, new Object[]{n, n2}));
    }

    private void updateCounterTextAppearanceAndColor() {
        TextView textView = this.counterView;
        if (textView == null) return;
        int n = this.counterOverflowed ? this.counterOverflowTextAppearance : this.counterTextAppearance;
        this.setTextAppearanceCompatWithErrorFallback(textView, n);
        if (!this.counterOverflowed && (textView = this.counterTextColor) != null) {
            this.counterView.setTextColor((ColorStateList)textView);
        }
        if (!this.counterOverflowed) return;
        textView = this.counterOverflowTextColor;
        if (textView == null) return;
        this.counterView.setTextColor((ColorStateList)textView);
    }

    /*
     * Unable to fully structure code
     */
    private boolean updateDummyDrawables() {
        block11 : {
            if (this.editText == null) {
                return false;
            }
            var1_1 = this.shouldUpdateStartDummyDrawable();
            var2_2 = true;
            var3_3 = true;
            if (!var1_1) break block11;
            var4_4 = this.startLayout.getMeasuredWidth() - this.editText.getPaddingLeft();
            if (this.startDummyDrawable == null || this.startDummyDrawableWidth != var4_4) {
                this.startDummyDrawable = var5_5 = new ColorDrawable();
                this.startDummyDrawableWidth = var4_4;
                var5_5.setBounds(0, 0, var4_4, 1);
            }
            if ((var7_7 = (var6_6 = TextViewCompat.getCompoundDrawablesRelative((TextView)this.editText))[0]) == (var5_5 = this.startDummyDrawable)) ** GOTO lbl-1000
            TextViewCompat.setCompoundDrawablesRelative((TextView)this.editText, (Drawable)var5_5, var6_6[1], var6_6[2], var6_6[3]);
            ** GOTO lbl20
        }
        if (this.startDummyDrawable != null) {
            var5_5 = TextViewCompat.getCompoundDrawablesRelative((TextView)this.editText);
            TextViewCompat.setCompoundDrawablesRelative((TextView)this.editText, null, var5_5[1], (Drawable)var5_5[2], var5_5[3]);
            this.startDummyDrawable = null;
lbl20: // 2 sources:
            var1_1 = true;
        } else lbl-1000: // 2 sources:
        {
            var1_1 = false;
        }
        if (this.shouldUpdateEndDummyDrawable()) {
            var8_8 = this.suffixTextView.getMeasuredWidth() - this.editText.getPaddingRight();
            var5_5 = this.getEndIconToUpdateDummyDrawable();
            var4_4 = var8_8;
            if (var5_5 != null) {
                var4_4 = var8_8 + var5_5.getMeasuredWidth() + MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)var5_5.getLayoutParams());
            }
            var5_5 = TextViewCompat.getCompoundDrawablesRelative((TextView)this.editText);
            var6_6 = this.endDummyDrawable;
            if (var6_6 != null && this.endDummyDrawableWidth != var4_4) {
                this.endDummyDrawableWidth = var4_4;
                var6_6.setBounds(0, 0, var4_4, 1);
                TextViewCompat.setCompoundDrawablesRelative((TextView)this.editText, var5_5[0], var5_5[1], this.endDummyDrawable, var5_5[3]);
                var1_1 = var2_2;
                return var1_1;
            } else {
                if (this.endDummyDrawable == null) {
                    this.endDummyDrawable = var6_6 = new ColorDrawable();
                    this.endDummyDrawableWidth = var4_4;
                    var6_6.setBounds(0, 0, var4_4, 1);
                }
                if ((var6_6 = var5_5[2]) == (var7_7 = this.endDummyDrawable)) return var1_1;
                this.originalEditTextEndDrawable = var5_5[2];
                TextViewCompat.setCompoundDrawablesRelative((TextView)this.editText, var5_5[0], var5_5[1], var7_7, var5_5[3]);
                var1_1 = var2_2;
            }
            return var1_1;
        } else {
            var2_2 = var1_1;
            if (this.endDummyDrawable == null) return var2_2;
            var5_5 = TextViewCompat.getCompoundDrawablesRelative((TextView)this.editText);
            if (var5_5[2] == this.endDummyDrawable) {
                TextViewCompat.setCompoundDrawablesRelative((TextView)this.editText, var5_5[0], var5_5[1], this.originalEditTextEndDrawable, var5_5[3]);
                var1_1 = var3_3;
            }
            this.endDummyDrawable = null;
        }
        return var1_1;
    }

    private boolean updateEditTextHeightBasedOnIcon() {
        if (this.editText == null) {
            return false;
        }
        int n = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight());
        if (this.editText.getMeasuredHeight() >= n) return false;
        this.editText.setMinimumHeight(n);
        return true;
    }

    private void updateIconColorOnState(CheckableImageButton checkableImageButton, ColorStateList colorStateList) {
        Drawable drawable2 = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() == null) return;
        if (colorStateList == null) return;
        if (!colorStateList.isStateful()) {
            return;
        }
        int n = colorStateList.getColorForState(this.getDrawableState(), colorStateList.getDefaultColor());
        colorStateList = DrawableCompat.wrap(drawable2).mutate();
        DrawableCompat.setTintList((Drawable)colorStateList, ColorStateList.valueOf((int)n));
        checkableImageButton.setImageDrawable((Drawable)colorStateList);
    }

    private void updateInputLayoutMargins() {
        if (this.boxBackgroundMode == 1) return;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.inputFrame.getLayoutParams();
        int n = this.calculateLabelMarginTop();
        if (n == layoutParams.topMargin) return;
        layoutParams.topMargin = n;
        this.inputFrame.requestLayout();
    }

    private void updateLabelState(boolean bl, boolean bl2) {
        boolean bl3 = this.isEnabled();
        EditText editText = this.editText;
        boolean bl4 = editText != null && !TextUtils.isEmpty((CharSequence)editText.getText());
        editText = this.editText;
        boolean bl5 = editText != null && editText.hasFocus();
        boolean bl6 = this.indicatorViewController.errorShouldBeShown();
        editText = this.defaultHintTextColor;
        if (editText != null) {
            this.collapsingTextHelper.setCollapsedTextColor((ColorStateList)editText);
            this.collapsingTextHelper.setExpandedTextColor(this.defaultHintTextColor);
        }
        if (!bl3) {
            int n;
            editText = this.defaultHintTextColor;
            if (editText != null) {
                n = this.disabledColor;
                n = editText.getColorForState(new int[]{-16842910}, n);
            } else {
                n = this.disabledColor;
            }
            this.collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf((int)n));
            this.collapsingTextHelper.setExpandedTextColor(ColorStateList.valueOf((int)n));
        } else if (bl6) {
            this.collapsingTextHelper.setCollapsedTextColor(this.indicatorViewController.getErrorViewTextColors());
        } else if (this.counterOverflowed && (editText = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(editText.getTextColors());
        } else if (bl5 && (editText = this.focusedTextColor) != null) {
            this.collapsingTextHelper.setCollapsedTextColor((ColorStateList)editText);
        }
        if (!(bl4 || this.isEnabled() && (bl5 || bl6))) {
            if (!bl2) {
                if (this.hintExpanded) return;
            }
            this.expandHint(bl);
            return;
        }
        if (!bl2) {
            if (!this.hintExpanded) return;
        }
        this.collapseHint(bl);
    }

    private void updatePlaceholderMeasurementsBasedOnEditText() {
        if (this.placeholderTextView == null) return;
        EditText editText = this.editText;
        if (editText == null) return;
        int n = editText.getGravity();
        this.placeholderTextView.setGravity(n);
        this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
    }

    private void updatePlaceholderText() {
        EditText editText = this.editText;
        int n = editText == null ? 0 : editText.getText().length();
        this.updatePlaceholderText(n);
    }

    private void updatePlaceholderText(int n) {
        if (n == 0 && !this.hintExpanded) {
            this.showPlaceholderText();
            return;
        }
        this.hidePlaceholderText();
    }

    private void updatePrefixTextViewPadding() {
        if (this.editText == null) {
            return;
        }
        int n = this.isStartIconVisible() ? 0 : ViewCompat.getPaddingStart((View)this.editText);
        ViewCompat.setPaddingRelative((View)this.prefixTextView, n, this.editText.getCompoundPaddingTop(), 0, this.editText.getCompoundPaddingBottom());
    }

    private void updatePrefixTextVisibility() {
        TextView textView = this.prefixTextView;
        int n = this.prefixText != null && !this.isHintExpanded() ? 0 : 8;
        textView.setVisibility(n);
        this.updateDummyDrawables();
    }

    private void updateStrokeErrorColor(boolean bl, boolean bl2) {
        int n = this.strokeErrorColor.getDefaultColor();
        int n2 = this.strokeErrorColor.getColorForState(new int[]{16843623, 16842910}, n);
        int n3 = this.strokeErrorColor.getColorForState(new int[]{16843518, 16842910}, n);
        if (bl) {
            this.boxStrokeColor = n3;
            return;
        }
        if (bl2) {
            this.boxStrokeColor = n2;
            return;
        }
        this.boxStrokeColor = n;
    }

    private void updateSuffixTextViewPadding() {
        if (this.editText == null) {
            return;
        }
        int n = !this.isEndIconVisible() && !this.isErrorIconVisible() ? ViewCompat.getPaddingEnd((View)this.editText) : 0;
        ViewCompat.setPaddingRelative((View)this.suffixTextView, 0, this.editText.getPaddingTop(), n, this.editText.getPaddingBottom());
    }

    private void updateSuffixTextVisibility() {
        int n = this.suffixTextView.getVisibility();
        CharSequence charSequence = this.suffixText;
        int n2 = 0;
        boolean bl = charSequence != null && !this.isHintExpanded();
        charSequence = this.suffixTextView;
        if (!bl) {
            n2 = 8;
        }
        charSequence.setVisibility(n2);
        if (n != this.suffixTextView.getVisibility()) {
            this.getEndIconDelegate().onSuffixVisibilityChanged(bl);
        }
        this.updateDummyDrawables();
    }

    public void addOnEditTextAttachedListener(OnEditTextAttachedListener onEditTextAttachedListener) {
        this.editTextAttachedListeners.add(onEditTextAttachedListener);
        if (this.editText == null) return;
        onEditTextAttachedListener.onEditTextAttached(this);
    }

    public void addOnEndIconChangedListener(OnEndIconChangedListener onEndIconChangedListener) {
        this.endIconChangedListeners.add(onEndIconChangedListener);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = layoutParams2.gravity & -113 | 16;
            this.inputFrame.addView(view, (ViewGroup.LayoutParams)layoutParams2);
            this.inputFrame.setLayoutParams(layoutParams);
            this.updateInputLayoutMargins();
            this.setEditText((EditText)view);
            return;
        }
        super.addView(view, n, layoutParams);
    }

    void animateToExpansionFraction(float f) {
        if (this.collapsingTextHelper.getExpansionFraction() == f) {
            return;
        }
        if (this.animator == null) {
            ValueAnimator valueAnimator;
            this.animator = valueAnimator = new ValueAnimator();
            valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.animator.setDuration(167L);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float)valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }
        this.animator.setFloatValues(new float[]{this.collapsingTextHelper.getExpansionFraction(), f});
        this.animator.start();
    }

    public void clearOnEditTextAttachedListeners() {
        this.editTextAttachedListeners.clear();
    }

    public void clearOnEndIconChangedListeners() {
        this.endIconChangedListeners.clear();
    }

    boolean cutoutIsOpen() {
        if (!this.cutoutEnabled()) return false;
        if (!((CutoutDrawable)this.boxBackground).hasCutout()) return false;
        return true;
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int n) {
        Object object;
        if (this.originalHint != null && (object = this.editText) != null) {
            boolean bl = this.isProvidingHint;
            this.isProvidingHint = false;
            object = object.getHint();
            this.editText.setHint(this.originalHint);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, n);
                return;
            }
            finally {
                this.editText.setHint((CharSequence)object);
                this.isProvidingHint = bl;
            }
        }
        super.dispatchProvideAutofillStructure(viewStructure, n);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.restoringSavedState = false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.drawHint(canvas);
        this.drawBoxUnderline(canvas);
    }

    protected void drawableStateChanged() {
        if (this.inDrawableStateChanged) {
            return;
        }
        boolean bl = true;
        this.inDrawableStateChanged = true;
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
        boolean bl2 = collapsingTextHelper != null ? collapsingTextHelper.setState(arrn) | false : false;
        if (this.editText != null) {
            if (!ViewCompat.isLaidOut((View)this) || !this.isEnabled()) {
                bl = false;
            }
            this.updateLabelState(bl);
        }
        this.updateEditTextBackground();
        this.updateTextInputBoxState();
        if (bl2) {
            this.invalidate();
        }
        this.inDrawableStateChanged = false;
    }

    public int getBaseline() {
        EditText editText = this.editText;
        if (editText == null) return super.getBaseline();
        return editText.getBaseline() + this.getPaddingTop() + this.calculateLabelMarginTop();
    }

    MaterialShapeDrawable getBoxBackground() {
        int n = this.boxBackgroundMode;
        if (n == 1) return this.boxBackground;
        if (n != 2) throw new IllegalStateException();
        return this.boxBackground;
    }

    public int getBoxBackgroundColor() {
        return this.boxBackgroundColor;
    }

    public int getBoxBackgroundMode() {
        return this.boxBackgroundMode;
    }

    public float getBoxCornerRadiusBottomEnd() {
        return this.boxBackground.getBottomLeftCornerResolvedSize();
    }

    public float getBoxCornerRadiusBottomStart() {
        return this.boxBackground.getBottomRightCornerResolvedSize();
    }

    public float getBoxCornerRadiusTopEnd() {
        return this.boxBackground.getTopRightCornerResolvedSize();
    }

    public float getBoxCornerRadiusTopStart() {
        return this.boxBackground.getTopLeftCornerResolvedSize();
    }

    public int getBoxStrokeColor() {
        return this.focusedStrokeColor;
    }

    public ColorStateList getBoxStrokeErrorColor() {
        return this.strokeErrorColor;
    }

    public int getBoxStrokeWidth() {
        return this.boxStrokeWidthDefaultPx;
    }

    public int getBoxStrokeWidthFocused() {
        return this.boxStrokeWidthFocusedPx;
    }

    public int getCounterMaxLength() {
        return this.counterMaxLength;
    }

    CharSequence getCounterOverflowDescription() {
        if (!this.counterEnabled) return null;
        if (!this.counterOverflowed) return null;
        TextView textView = this.counterView;
        if (textView == null) return null;
        return textView.getContentDescription();
    }

    public ColorStateList getCounterOverflowTextColor() {
        return this.counterTextColor;
    }

    public ColorStateList getCounterTextColor() {
        return this.counterTextColor;
    }

    public ColorStateList getDefaultHintTextColor() {
        return this.defaultHintTextColor;
    }

    public EditText getEditText() {
        return this.editText;
    }

    public CharSequence getEndIconContentDescription() {
        return this.endIconView.getContentDescription();
    }

    public Drawable getEndIconDrawable() {
        return this.endIconView.getDrawable();
    }

    public int getEndIconMode() {
        return this.endIconMode;
    }

    CheckableImageButton getEndIconView() {
        return this.endIconView;
    }

    public CharSequence getError() {
        if (!this.indicatorViewController.isErrorEnabled()) return null;
        return this.indicatorViewController.getErrorText();
    }

    public CharSequence getErrorContentDescription() {
        return this.indicatorViewController.getErrorContentDescription();
    }

    public int getErrorCurrentTextColors() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public Drawable getErrorIconDrawable() {
        return this.errorIconView.getDrawable();
    }

    final int getErrorTextCurrentColor() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public CharSequence getHelperText() {
        if (!this.indicatorViewController.isHelperTextEnabled()) return null;
        return this.indicatorViewController.getHelperText();
    }

    public int getHelperTextCurrentTextColor() {
        return this.indicatorViewController.getHelperTextViewCurrentTextColor();
    }

    public CharSequence getHint() {
        if (!this.hintEnabled) return null;
        return this.hint;
    }

    final float getHintCollapsedTextHeight() {
        return this.collapsingTextHelper.getCollapsedTextHeight();
    }

    final int getHintCurrentCollapsedTextColor() {
        return this.collapsingTextHelper.getCurrentCollapsedTextColor();
    }

    public ColorStateList getHintTextColor() {
        return this.focusedTextColor;
    }

    @Deprecated
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.endIconView.getContentDescription();
    }

    @Deprecated
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.endIconView.getDrawable();
    }

    public CharSequence getPlaceholderText() {
        if (!this.placeholderEnabled) return null;
        return this.placeholderText;
    }

    public int getPlaceholderTextAppearance() {
        return this.placeholderTextAppearance;
    }

    public ColorStateList getPlaceholderTextColor() {
        return this.placeholderTextColor;
    }

    public CharSequence getPrefixText() {
        return this.prefixText;
    }

    public ColorStateList getPrefixTextColor() {
        return this.prefixTextView.getTextColors();
    }

    public TextView getPrefixTextView() {
        return this.prefixTextView;
    }

    public CharSequence getStartIconContentDescription() {
        return this.startIconView.getContentDescription();
    }

    public Drawable getStartIconDrawable() {
        return this.startIconView.getDrawable();
    }

    public CharSequence getSuffixText() {
        return this.suffixText;
    }

    public ColorStateList getSuffixTextColor() {
        return this.suffixTextView.getTextColors();
    }

    public TextView getSuffixTextView() {
        return this.suffixTextView;
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public boolean isCounterEnabled() {
        return this.counterEnabled;
    }

    public boolean isEndIconCheckable() {
        return this.endIconView.isCheckable();
    }

    public boolean isEndIconVisible() {
        if (this.endIconFrame.getVisibility() != 0) return false;
        if (this.endIconView.getVisibility() != 0) return false;
        return true;
    }

    public boolean isErrorEnabled() {
        return this.indicatorViewController.isErrorEnabled();
    }

    final boolean isHelperTextDisplayed() {
        return this.indicatorViewController.helperTextIsDisplayed();
    }

    public boolean isHelperTextEnabled() {
        return this.indicatorViewController.isHelperTextEnabled();
    }

    public boolean isHintAnimationEnabled() {
        return this.hintAnimationEnabled;
    }

    public boolean isHintEnabled() {
        return this.hintEnabled;
    }

    final boolean isHintExpanded() {
        return this.hintExpanded;
    }

    @Deprecated
    public boolean isPasswordVisibilityToggleEnabled() {
        int n = this.endIconMode;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public boolean isProvidingHint() {
        return this.isProvidingHint;
    }

    public boolean isStartIconCheckable() {
        return this.startIconView.isCheckable();
    }

    public boolean isStartIconVisible() {
        if (this.startIconView.getVisibility() != 0) return false;
        return true;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        EditText editText = this.editText;
        if (editText == null) return;
        Rect rect = this.tmpRect;
        DescendantOffsetUtils.getDescendantRect((ViewGroup)this, (View)editText, rect);
        this.updateBoxUnderlineBounds(rect);
        if (!this.hintEnabled) return;
        this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
        n = this.editText.getGravity();
        this.collapsingTextHelper.setCollapsedTextGravity(n & -113 | 48);
        this.collapsingTextHelper.setExpandedTextGravity(n);
        this.collapsingTextHelper.setCollapsedBounds(this.calculateCollapsedTextBounds(rect));
        this.collapsingTextHelper.setExpandedBounds(this.calculateExpandedTextBounds(rect));
        this.collapsingTextHelper.recalculate();
        if (!this.cutoutEnabled()) return;
        if (this.hintExpanded) return;
        this.openCutout();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        boolean bl = this.updateEditTextHeightBasedOnIcon();
        boolean bl2 = this.updateDummyDrawables();
        if (bl || bl2) {
            this.editText.post(new Runnable(){

                @Override
                public void run() {
                    TextInputLayout.this.editText.requestLayout();
                }
            });
        }
        this.updatePlaceholderMeasurementsBasedOnEditText();
        this.updatePrefixTextViewPadding();
        this.updateSuffixTextViewPadding();
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.setError(parcelable.error);
        if (parcelable.isEndIconChecked) {
            this.endIconView.post(new Runnable(){

                @Override
                public void run() {
                    TextInputLayout.this.endIconView.performClick();
                    TextInputLayout.this.endIconView.jumpDrawablesToCurrentState();
                }
            });
        }
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.indicatorViewController.errorShouldBeShown()) {
            savedState.error = this.getError();
        }
        boolean bl = this.hasEndIcon() && this.endIconView.isChecked();
        savedState.isEndIconChecked = bl;
        return savedState;
    }

    @Deprecated
    public void passwordVisibilityToggleRequested(boolean bl) {
        if (this.endIconMode != 1) return;
        this.endIconView.performClick();
        if (!bl) return;
        this.endIconView.jumpDrawablesToCurrentState();
    }

    public void removeOnEditTextAttachedListener(OnEditTextAttachedListener onEditTextAttachedListener) {
        this.editTextAttachedListeners.remove(onEditTextAttachedListener);
    }

    public void removeOnEndIconChangedListener(OnEndIconChangedListener onEndIconChangedListener) {
        this.endIconChangedListeners.remove(onEndIconChangedListener);
    }

    public void setBoxBackgroundColor(int n) {
        if (this.boxBackgroundColor == n) return;
        this.boxBackgroundColor = n;
        this.defaultFilledBackgroundColor = n;
        this.focusedFilledBackgroundColor = n;
        this.hoveredFilledBackgroundColor = n;
        this.applyBoxAttributes();
    }

    public void setBoxBackgroundColorResource(int n) {
        this.setBoxBackgroundColor(ContextCompat.getColor(this.getContext(), n));
    }

    public void setBoxBackgroundColorStateList(ColorStateList colorStateList) {
        int n;
        this.defaultFilledBackgroundColor = n = colorStateList.getDefaultColor();
        this.boxBackgroundColor = n;
        this.disabledFilledBackgroundColor = colorStateList.getColorForState(new int[]{-16842910}, -1);
        this.focusedFilledBackgroundColor = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        this.hoveredFilledBackgroundColor = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
        this.applyBoxAttributes();
    }

    public void setBoxBackgroundMode(int n) {
        if (n == this.boxBackgroundMode) {
            return;
        }
        this.boxBackgroundMode = n;
        if (this.editText == null) return;
        this.onApplyBoxBackgroundMode();
    }

    public void setBoxCornerRadii(float f, float f2, float f3, float f4) {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable != null && materialShapeDrawable.getTopLeftCornerResolvedSize() == f && this.boxBackground.getTopRightCornerResolvedSize() == f2 && this.boxBackground.getBottomRightCornerResolvedSize() == f4) {
            if (this.boxBackground.getBottomLeftCornerResolvedSize() == f3) return;
        }
        this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setTopLeftCornerSize(f).setTopRightCornerSize(f2).setBottomRightCornerSize(f4).setBottomLeftCornerSize(f3).build();
        this.applyBoxAttributes();
    }

    public void setBoxCornerRadiiResources(int n, int n2, int n3, int n4) {
        this.setBoxCornerRadii(this.getContext().getResources().getDimension(n), this.getContext().getResources().getDimension(n2), this.getContext().getResources().getDimension(n4), this.getContext().getResources().getDimension(n3));
    }

    public void setBoxStrokeColor(int n) {
        if (this.focusedStrokeColor == n) return;
        this.focusedStrokeColor = n;
        this.updateTextInputBoxState();
    }

    public void setBoxStrokeColorStateList(ColorStateList colorStateList) {
        if (colorStateList.isStateful()) {
            this.defaultStrokeColor = colorStateList.getDefaultColor();
            this.disabledColor = colorStateList.getColorForState(new int[]{-16842910}, -1);
            this.hoveredStrokeColor = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            this.focusedStrokeColor = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        } else if (this.focusedStrokeColor != colorStateList.getDefaultColor()) {
            this.focusedStrokeColor = colorStateList.getDefaultColor();
        }
        this.updateTextInputBoxState();
    }

    public void setBoxStrokeErrorColor(ColorStateList colorStateList) {
        if (this.strokeErrorColor == colorStateList) return;
        this.strokeErrorColor = colorStateList;
        this.updateTextInputBoxState();
    }

    public void setBoxStrokeWidth(int n) {
        this.boxStrokeWidthDefaultPx = n;
        this.updateTextInputBoxState();
    }

    public void setBoxStrokeWidthFocused(int n) {
        this.boxStrokeWidthFocusedPx = n;
        this.updateTextInputBoxState();
    }

    public void setBoxStrokeWidthFocusedResource(int n) {
        this.setBoxStrokeWidthFocused(this.getResources().getDimensionPixelSize(n));
    }

    public void setBoxStrokeWidthResource(int n) {
        this.setBoxStrokeWidth(this.getResources().getDimensionPixelSize(n));
    }

    public void setCounterEnabled(boolean bl) {
        if (this.counterEnabled == bl) return;
        if (bl) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.getContext());
            this.counterView = appCompatTextView;
            appCompatTextView.setId(R.id.textinput_counter);
            appCompatTextView = this.typeface;
            if (appCompatTextView != null) {
                this.counterView.setTypeface((Typeface)appCompatTextView);
            }
            this.counterView.setMaxLines(1);
            this.indicatorViewController.addIndicator(this.counterView, 2);
            MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)this.counterView.getLayoutParams(), this.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_counter_margin_start));
            this.updateCounterTextAppearanceAndColor();
            this.updateCounter();
        } else {
            this.indicatorViewController.removeIndicator(this.counterView, 2);
            this.counterView = null;
        }
        this.counterEnabled = bl;
    }

    public void setCounterMaxLength(int n) {
        if (this.counterMaxLength == n) return;
        this.counterMaxLength = n > 0 ? n : -1;
        if (!this.counterEnabled) return;
        this.updateCounter();
    }

    public void setCounterOverflowTextAppearance(int n) {
        if (this.counterOverflowTextAppearance == n) return;
        this.counterOverflowTextAppearance = n;
        this.updateCounterTextAppearanceAndColor();
    }

    public void setCounterOverflowTextColor(ColorStateList colorStateList) {
        if (this.counterOverflowTextColor == colorStateList) return;
        this.counterOverflowTextColor = colorStateList;
        this.updateCounterTextAppearanceAndColor();
    }

    public void setCounterTextAppearance(int n) {
        if (this.counterTextAppearance == n) return;
        this.counterTextAppearance = n;
        this.updateCounterTextAppearanceAndColor();
    }

    public void setCounterTextColor(ColorStateList colorStateList) {
        if (this.counterTextColor == colorStateList) return;
        this.counterTextColor = colorStateList;
        this.updateCounterTextAppearanceAndColor();
    }

    public void setDefaultHintTextColor(ColorStateList colorStateList) {
        this.defaultHintTextColor = colorStateList;
        this.focusedTextColor = colorStateList;
        if (this.editText == null) return;
        this.updateLabelState(false);
    }

    public void setEnabled(boolean bl) {
        TextInputLayout.recursiveSetEnabled((ViewGroup)this, bl);
        super.setEnabled(bl);
    }

    public void setEndIconActivated(boolean bl) {
        this.endIconView.setActivated(bl);
    }

    public void setEndIconCheckable(boolean bl) {
        this.endIconView.setCheckable(bl);
    }

    public void setEndIconContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getResources().getText(n) : null;
        this.setEndIconContentDescription(charSequence);
    }

    public void setEndIconContentDescription(CharSequence charSequence) {
        if (this.getEndIconContentDescription() == charSequence) return;
        this.endIconView.setContentDescription(charSequence);
    }

    public void setEndIconDrawable(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setEndIconDrawable(drawable2);
    }

    public void setEndIconDrawable(Drawable drawable2) {
        this.endIconView.setImageDrawable(drawable2);
    }

    public void setEndIconMode(int n) {
        int n2 = this.endIconMode;
        this.endIconMode = n;
        this.dispatchOnEndIconChanged(n2);
        boolean bl = n != 0;
        this.setEndIconVisible(bl);
        if (this.getEndIconDelegate().isBoxBackgroundModeSupported(this.boxBackgroundMode)) {
            this.getEndIconDelegate().initialize();
            this.applyEndIconTint();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The current box background mode ");
        stringBuilder.append(this.boxBackgroundMode);
        stringBuilder.append(" is not supported by the end icon mode ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void setEndIconOnClickListener(View.OnClickListener onClickListener) {
        TextInputLayout.setIconOnClickListener(this.endIconView, onClickListener, this.endIconOnLongClickListener);
    }

    public void setEndIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.endIconOnLongClickListener = onLongClickListener;
        TextInputLayout.setIconOnLongClickListener(this.endIconView, onLongClickListener);
    }

    public void setEndIconTintList(ColorStateList colorStateList) {
        if (this.endIconTintList == colorStateList) return;
        this.endIconTintList = colorStateList;
        this.hasEndIconTintList = true;
        this.applyEndIconTint();
    }

    public void setEndIconTintMode(PorterDuff.Mode mode) {
        if (this.endIconTintMode == mode) return;
        this.endIconTintMode = mode;
        this.hasEndIconTintMode = true;
        this.applyEndIconTint();
    }

    public void setEndIconVisible(boolean bl) {
        if (this.isEndIconVisible() == bl) return;
        CheckableImageButton checkableImageButton = this.endIconView;
        int n = bl ? 0 : 8;
        checkableImageButton.setVisibility(n);
        this.updateSuffixTextViewPadding();
        this.updateDummyDrawables();
    }

    public void setError(CharSequence charSequence) {
        if (!this.indicatorViewController.isErrorEnabled()) {
            if (TextUtils.isEmpty((CharSequence)charSequence)) {
                return;
            }
            this.setErrorEnabled(true);
        }
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.indicatorViewController.showError(charSequence);
            return;
        }
        this.indicatorViewController.hideError();
    }

    public void setErrorContentDescription(CharSequence charSequence) {
        this.indicatorViewController.setErrorContentDescription(charSequence);
    }

    public void setErrorEnabled(boolean bl) {
        this.indicatorViewController.setErrorEnabled(bl);
    }

    public void setErrorIconDrawable(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setErrorIconDrawable(drawable2);
    }

    public void setErrorIconDrawable(Drawable drawable2) {
        this.errorIconView.setImageDrawable(drawable2);
        boolean bl = drawable2 != null && this.indicatorViewController.isErrorEnabled();
        this.setErrorIconVisible(bl);
    }

    public void setErrorIconOnClickListener(View.OnClickListener onClickListener) {
        TextInputLayout.setIconOnClickListener(this.errorIconView, onClickListener, this.errorIconOnLongClickListener);
    }

    public void setErrorIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.errorIconOnLongClickListener = onLongClickListener;
        TextInputLayout.setIconOnLongClickListener(this.errorIconView, onLongClickListener);
    }

    public void setErrorIconTintList(ColorStateList colorStateList) {
        Drawable drawable2;
        this.errorIconTintList = colorStateList;
        Drawable drawable3 = drawable2 = this.errorIconView.getDrawable();
        if (drawable2 != null) {
            drawable3 = DrawableCompat.wrap(drawable2).mutate();
            DrawableCompat.setTintList(drawable3, colorStateList);
        }
        if (this.errorIconView.getDrawable() == drawable3) return;
        this.errorIconView.setImageDrawable(drawable3);
    }

    public void setErrorIconTintMode(PorterDuff.Mode mode) {
        Drawable drawable2;
        Drawable drawable3 = drawable2 = this.errorIconView.getDrawable();
        if (drawable2 != null) {
            drawable3 = DrawableCompat.wrap(drawable2).mutate();
            DrawableCompat.setTintMode(drawable3, mode);
        }
        if (this.errorIconView.getDrawable() == drawable3) return;
        this.errorIconView.setImageDrawable(drawable3);
    }

    public void setErrorTextAppearance(int n) {
        this.indicatorViewController.setErrorTextAppearance(n);
    }

    public void setErrorTextColor(ColorStateList colorStateList) {
        this.indicatorViewController.setErrorViewTextColor(colorStateList);
    }

    public void setHelperText(CharSequence charSequence) {
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            if (!this.isHelperTextEnabled()) return;
            this.setHelperTextEnabled(false);
            return;
        }
        if (!this.isHelperTextEnabled()) {
            this.setHelperTextEnabled(true);
        }
        this.indicatorViewController.showHelper(charSequence);
    }

    public void setHelperTextColor(ColorStateList colorStateList) {
        this.indicatorViewController.setHelperTextViewTextColor(colorStateList);
    }

    public void setHelperTextEnabled(boolean bl) {
        this.indicatorViewController.setHelperTextEnabled(bl);
    }

    public void setHelperTextTextAppearance(int n) {
        this.indicatorViewController.setHelperTextAppearance(n);
    }

    public void setHint(CharSequence charSequence) {
        if (!this.hintEnabled) return;
        this.setHintInternal(charSequence);
        this.sendAccessibilityEvent(2048);
    }

    public void setHintAnimationEnabled(boolean bl) {
        this.hintAnimationEnabled = bl;
    }

    public void setHintEnabled(boolean bl) {
        if (bl == this.hintEnabled) return;
        this.hintEnabled = bl;
        if (!bl) {
            this.isProvidingHint = false;
            if (!TextUtils.isEmpty((CharSequence)this.hint) && TextUtils.isEmpty((CharSequence)this.editText.getHint())) {
                this.editText.setHint(this.hint);
            }
            this.setHintInternal(null);
        } else {
            CharSequence charSequence = this.editText.getHint();
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                if (TextUtils.isEmpty((CharSequence)this.hint)) {
                    this.setHint(charSequence);
                }
                this.editText.setHint(null);
            }
            this.isProvidingHint = true;
        }
        if (this.editText == null) return;
        this.updateInputLayoutMargins();
    }

    public void setHintTextAppearance(int n) {
        this.collapsingTextHelper.setCollapsedTextAppearance(n);
        this.focusedTextColor = this.collapsingTextHelper.getCollapsedTextColor();
        if (this.editText == null) return;
        this.updateLabelState(false);
        this.updateInputLayoutMargins();
    }

    public void setHintTextColor(ColorStateList colorStateList) {
        if (this.focusedTextColor == colorStateList) return;
        if (this.defaultHintTextColor == null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        this.focusedTextColor = colorStateList;
        if (this.editText == null) return;
        this.updateLabelState(false);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getResources().getText(n) : null;
        this.setPasswordVisibilityToggleContentDescription(charSequence);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(CharSequence charSequence) {
        this.endIconView.setContentDescription(charSequence);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setPasswordVisibilityToggleDrawable(drawable2);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(Drawable drawable2) {
        this.endIconView.setImageDrawable(drawable2);
    }

    @Deprecated
    public void setPasswordVisibilityToggleEnabled(boolean bl) {
        if (bl && this.endIconMode != 1) {
            this.setEndIconMode(1);
            return;
        }
        if (bl) return;
        this.setEndIconMode(0);
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintList(ColorStateList colorStateList) {
        this.endIconTintList = colorStateList;
        this.hasEndIconTintList = true;
        this.applyEndIconTint();
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.endIconTintMode = mode;
        this.hasEndIconTintMode = true;
        this.applyEndIconTint();
    }

    public void setPlaceholderText(CharSequence charSequence) {
        if (this.placeholderEnabled && TextUtils.isEmpty((CharSequence)charSequence)) {
            this.setPlaceholderTextEnabled(false);
        } else {
            if (!this.placeholderEnabled) {
                this.setPlaceholderTextEnabled(true);
            }
            this.placeholderText = charSequence;
        }
        this.updatePlaceholderText();
    }

    public void setPlaceholderTextAppearance(int n) {
        this.placeholderTextAppearance = n;
        TextView textView = this.placeholderTextView;
        if (textView == null) return;
        TextViewCompat.setTextAppearance(textView, n);
    }

    public void setPlaceholderTextColor(ColorStateList colorStateList) {
        if (this.placeholderTextColor == colorStateList) return;
        this.placeholderTextColor = colorStateList;
        TextView textView = this.placeholderTextView;
        if (textView == null) return;
        if (colorStateList == null) return;
        textView.setTextColor(colorStateList);
    }

    public void setPrefixText(CharSequence charSequence) {
        CharSequence charSequence2 = TextUtils.isEmpty((CharSequence)charSequence) ? null : charSequence;
        this.prefixText = charSequence2;
        this.prefixTextView.setText(charSequence);
        this.updatePrefixTextVisibility();
    }

    public void setPrefixTextAppearance(int n) {
        TextViewCompat.setTextAppearance(this.prefixTextView, n);
    }

    public void setPrefixTextColor(ColorStateList colorStateList) {
        this.prefixTextView.setTextColor(colorStateList);
    }

    public void setStartIconCheckable(boolean bl) {
        this.startIconView.setCheckable(bl);
    }

    public void setStartIconContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getResources().getText(n) : null;
        this.setStartIconContentDescription(charSequence);
    }

    public void setStartIconContentDescription(CharSequence charSequence) {
        if (this.getStartIconContentDescription() == charSequence) return;
        this.startIconView.setContentDescription(charSequence);
    }

    public void setStartIconDrawable(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setStartIconDrawable(drawable2);
    }

    public void setStartIconDrawable(Drawable drawable2) {
        this.startIconView.setImageDrawable(drawable2);
        if (drawable2 != null) {
            this.setStartIconVisible(true);
            this.applyStartIconTint();
            return;
        }
        this.setStartIconVisible(false);
        this.setStartIconOnClickListener(null);
        this.setStartIconOnLongClickListener(null);
        this.setStartIconContentDescription(null);
    }

    public void setStartIconOnClickListener(View.OnClickListener onClickListener) {
        TextInputLayout.setIconOnClickListener(this.startIconView, onClickListener, this.startIconOnLongClickListener);
    }

    public void setStartIconOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.startIconOnLongClickListener = onLongClickListener;
        TextInputLayout.setIconOnLongClickListener(this.startIconView, onLongClickListener);
    }

    public void setStartIconTintList(ColorStateList colorStateList) {
        if (this.startIconTintList == colorStateList) return;
        this.startIconTintList = colorStateList;
        this.hasStartIconTintList = true;
        this.applyStartIconTint();
    }

    public void setStartIconTintMode(PorterDuff.Mode mode) {
        if (this.startIconTintMode == mode) return;
        this.startIconTintMode = mode;
        this.hasStartIconTintMode = true;
        this.applyStartIconTint();
    }

    public void setStartIconVisible(boolean bl) {
        if (this.isStartIconVisible() == bl) return;
        CheckableImageButton checkableImageButton = this.startIconView;
        int n = bl ? 0 : 8;
        checkableImageButton.setVisibility(n);
        this.updatePrefixTextViewPadding();
        this.updateDummyDrawables();
    }

    public void setSuffixText(CharSequence charSequence) {
        CharSequence charSequence2 = TextUtils.isEmpty((CharSequence)charSequence) ? null : charSequence;
        this.suffixText = charSequence2;
        this.suffixTextView.setText(charSequence);
        this.updateSuffixTextVisibility();
    }

    public void setSuffixTextAppearance(int n) {
        TextViewCompat.setTextAppearance(this.suffixTextView, n);
    }

    public void setSuffixTextColor(ColorStateList colorStateList) {
        this.suffixTextView.setTextColor(colorStateList);
    }

    void setTextAppearanceCompatWithErrorFallback(TextView textView, int n) {
        int n2 = 1;
        try {
            TextViewCompat.setTextAppearance(textView, n);
            n = Build.VERSION.SDK_INT >= 23 && (n = textView.getTextColors().getDefaultColor()) == -65281 ? n2 : 0;
        }
        catch (Exception exception) {
            n = n2;
        }
        if (n == 0) return;
        TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_AppCompat_Caption);
        textView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.design_error));
    }

    public void setTextInputAccessibilityDelegate(AccessibilityDelegate accessibilityDelegate) {
        EditText editText = this.editText;
        if (editText == null) return;
        ViewCompat.setAccessibilityDelegate((View)editText, accessibilityDelegate);
    }

    public void setTypeface(Typeface typeface) {
        if (typeface == this.typeface) return;
        this.typeface = typeface;
        this.collapsingTextHelper.setTypefaces(typeface);
        this.indicatorViewController.setTypefaces(typeface);
        TextView textView = this.counterView;
        if (textView == null) return;
        textView.setTypeface(typeface);
    }

    void updateCounter(int n) {
        boolean bl = this.counterOverflowed;
        int n2 = this.counterMaxLength;
        if (n2 == -1) {
            this.counterView.setText((CharSequence)String.valueOf(n));
            this.counterView.setContentDescription(null);
            this.counterOverflowed = false;
        } else {
            boolean bl2 = n > n2;
            this.counterOverflowed = bl2;
            TextInputLayout.updateCounterContentDescription(this.getContext(), this.counterView, n, this.counterMaxLength, this.counterOverflowed);
            if (bl != this.counterOverflowed) {
                this.updateCounterTextAppearanceAndColor();
            }
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            this.counterView.setText((CharSequence)bidiFormatter.unicodeWrap(this.getContext().getString(R.string.character_counter_pattern, new Object[]{n, this.counterMaxLength})));
        }
        if (this.editText == null) return;
        if (bl == this.counterOverflowed) return;
        this.updateLabelState(false);
        this.updateTextInputBoxState();
        this.updateEditTextBackground();
    }

    void updateEditTextBackground() {
        EditText editText = this.editText;
        if (editText == null) return;
        if (this.boxBackgroundMode != 0) {
            return;
        }
        Drawable drawable2 = editText.getBackground();
        if (drawable2 == null) {
            return;
        }
        editText = drawable2;
        if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            editText = drawable2.mutate();
        }
        if (this.indicatorViewController.errorShouldBeShown()) {
            editText.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(this.indicatorViewController.getErrorViewCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        if (this.counterOverflowed && (drawable2 = this.counterView) != null) {
            editText.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(drawable2.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            return;
        }
        DrawableCompat.clearColorFilter((Drawable)editText);
        this.editText.refreshDrawableState();
    }

    void updateLabelState(boolean bl) {
        this.updateLabelState(bl, false);
    }

    void updateTextInputBoxState() {
        EditText editText;
        if (this.boxBackground == null) return;
        if (this.boxBackgroundMode == 0) {
            return;
        }
        boolean bl = this.isFocused();
        boolean bl2 = false;
        bl = bl || (editText = this.editText) != null && editText.hasFocus();
        boolean bl3 = this.isHovered() || (editText = this.editText) != null && editText.isHovered();
        if (!this.isEnabled()) {
            this.boxStrokeColor = this.disabledColor;
        } else if (this.indicatorViewController.errorShouldBeShown()) {
            if (this.strokeErrorColor != null) {
                this.updateStrokeErrorColor(bl, bl3);
            } else {
                this.boxStrokeColor = this.indicatorViewController.getErrorViewCurrentTextColor();
            }
        } else if (this.counterOverflowed && (editText = this.counterView) != null) {
            if (this.strokeErrorColor != null) {
                this.updateStrokeErrorColor(bl, bl3);
            } else {
                this.boxStrokeColor = editText.getCurrentTextColor();
            }
        } else {
            this.boxStrokeColor = bl ? this.focusedStrokeColor : (bl3 ? this.hoveredStrokeColor : this.defaultStrokeColor);
        }
        boolean bl4 = bl2;
        if (this.getErrorIconDrawable() != null) {
            bl4 = bl2;
            if (this.indicatorViewController.isErrorEnabled()) {
                bl4 = bl2;
                if (this.indicatorViewController.errorShouldBeShown()) {
                    bl4 = true;
                }
            }
        }
        this.setErrorIconVisible(bl4);
        this.updateIconColorOnState(this.errorIconView, this.errorIconTintList);
        this.updateIconColorOnState(this.startIconView, this.startIconTintList);
        this.updateIconColorOnState(this.endIconView, this.endIconTintList);
        if (this.getEndIconDelegate().shouldTintIconOnError()) {
            this.tintEndIconOnError(this.indicatorViewController.errorShouldBeShown());
        }
        this.boxStrokeWidthPx = bl && this.isEnabled() ? this.boxStrokeWidthFocusedPx : this.boxStrokeWidthDefaultPx;
        if (this.boxBackgroundMode == 1) {
            this.boxBackgroundColor = !this.isEnabled() ? this.disabledFilledBackgroundColor : (bl3 && !bl ? this.hoveredFilledBackgroundColor : (bl ? this.focusedFilledBackgroundColor : this.defaultFilledBackgroundColor));
        }
        this.applyBoxAttributes();
    }

    public static class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout textInputLayout) {
            this.layout = textInputLayout;
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
            object = this.layout.getEditText();
            Editable editable = object != null ? object.getText() : null;
            object = this.layout.getHint();
            CharSequence charSequence = this.layout.getHelperText();
            CharSequence charSequence2 = this.layout.getError();
            int n = this.layout.getCounterMaxLength();
            CharSequence charSequence3 = this.layout.getCounterOverflowDescription();
            boolean bl = TextUtils.isEmpty((CharSequence)editable) ^ true;
            boolean bl2 = TextUtils.isEmpty((CharSequence)object);
            boolean bl3 = TextUtils.isEmpty((CharSequence)charSequence) ^ true;
            boolean bl4 = TextUtils.isEmpty((CharSequence)charSequence2) ^ true;
            boolean bl5 = bl4 || !TextUtils.isEmpty((CharSequence)charSequence3);
            object = bl2 ^ true ? object.toString() : "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            object = (bl4 || bl3) && !TextUtils.isEmpty((CharSequence)object) ? ", " : "";
            stringBuilder.append((String)object);
            object = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            object = bl4 ? charSequence2 : (bl3 ? charSequence : "");
            stringBuilder.append(object);
            charSequence = stringBuilder.toString();
            if (bl) {
                accessibilityNodeInfoCompat.setText((CharSequence)editable);
            } else if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                accessibilityNodeInfoCompat.setText(charSequence);
            }
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                if (Build.VERSION.SDK_INT >= 26) {
                    accessibilityNodeInfoCompat.setHintText(charSequence);
                } else {
                    object = charSequence;
                    if (bl) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append((Object)editable);
                        ((StringBuilder)object).append(", ");
                        ((StringBuilder)object).append((String)charSequence);
                        object = ((StringBuilder)object).toString();
                    }
                    accessibilityNodeInfoCompat.setText((CharSequence)object);
                }
                accessibilityNodeInfoCompat.setShowingHintText(bl ^ true);
            }
            if (editable == null || editable.length() != n) {
                n = -1;
            }
            accessibilityNodeInfoCompat.setMaxTextLength(n);
            if (!bl5) return;
            if (!bl4) {
                charSequence2 = charSequence3;
            }
            accessibilityNodeInfoCompat.setError(charSequence2);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BoxBackgroundMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EndIconMode {
    }

    public static interface OnEditTextAttachedListener {
        public void onEditTextAttached(TextInputLayout var1);
    }

    public static interface OnEndIconChangedListener {
        public void onEndIconChanged(TextInputLayout var1, int var2);
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
        CharSequence error;
        boolean isEndIconChecked;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.error = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.isEndIconChecked = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TextInputLayout.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" error=");
            stringBuilder.append((Object)this.error);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            TextUtils.writeToParcel((CharSequence)this.error, (Parcel)parcel, (int)n);
            parcel.writeInt((int)this.isEndIconChecked);
        }

    }

}

