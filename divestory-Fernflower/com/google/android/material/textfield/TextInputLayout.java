package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
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
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
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
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TextInputLayout extends LinearLayout {
   public static final int BOX_BACKGROUND_FILLED = 1;
   public static final int BOX_BACKGROUND_NONE = 0;
   public static final int BOX_BACKGROUND_OUTLINE = 2;
   private static final int DEF_STYLE_RES;
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
   final CollapsingTextHelper collapsingTextHelper;
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
   private final LinkedHashSet<TextInputLayout.OnEditTextAttachedListener> editTextAttachedListeners;
   private Drawable endDummyDrawable;
   private int endDummyDrawableWidth;
   private final LinkedHashSet<TextInputLayout.OnEndIconChangedListener> endIconChangedListeners;
   private final SparseArray<EndIconDelegate> endIconDelegates;
   private final FrameLayout endIconFrame;
   private int endIconMode;
   private OnLongClickListener endIconOnLongClickListener;
   private ColorStateList endIconTintList;
   private Mode endIconTintMode;
   private final CheckableImageButton endIconView;
   private final LinearLayout endLayout;
   private OnLongClickListener errorIconOnLongClickListener;
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
   private final IndicatorViewController indicatorViewController;
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
   private OnLongClickListener startIconOnLongClickListener;
   private ColorStateList startIconTintList;
   private Mode startIconTintMode;
   private final CheckableImageButton startIconView;
   private final LinearLayout startLayout;
   private ColorStateList strokeErrorColor;
   private CharSequence suffixText;
   private final TextView suffixTextView;
   private final Rect tmpBoundsRect;
   private final Rect tmpRect;
   private final RectF tmpRectF;
   private Typeface typeface;

   static {
      DEF_STYLE_RES = R.style.Widget_Design_TextInputLayout;
   }

   public TextInputLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public TextInputLayout(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.textInputStyle);
   }

   public TextInputLayout(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.indicatorViewController = new IndicatorViewController(this);
      this.tmpRect = new Rect();
      this.tmpBoundsRect = new Rect();
      this.tmpRectF = new RectF();
      this.editTextAttachedListeners = new LinkedHashSet();
      this.endIconMode = 0;
      this.endIconDelegates = new SparseArray();
      this.endIconChangedListeners = new LinkedHashSet();
      this.collapsingTextHelper = new CollapsingTextHelper(this);
      var1 = this.getContext();
      this.setOrientation(1);
      this.setWillNotDraw(false);
      this.setAddStatesFromChildren(true);
      FrameLayout var4 = new FrameLayout(var1);
      this.inputFrame = var4;
      var4.setAddStatesFromChildren(true);
      this.addView(this.inputFrame);
      LinearLayout var25 = new LinearLayout(var1);
      this.startLayout = var25;
      var25.setOrientation(0);
      this.startLayout.setLayoutParams(new LayoutParams(-2, -1, 8388611));
      this.inputFrame.addView(this.startLayout);
      var25 = new LinearLayout(var1);
      this.endLayout = var25;
      var25.setOrientation(0);
      this.endLayout.setLayoutParams(new LayoutParams(-2, -1, 8388613));
      this.inputFrame.addView(this.endLayout);
      var4 = new FrameLayout(var1);
      this.endIconFrame = var4;
      var4.setLayoutParams(new LayoutParams(-2, -1));
      this.collapsingTextHelper.setTextSizeInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
      this.collapsingTextHelper.setPositionInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
      this.collapsingTextHelper.setCollapsedTextGravity(8388659);
      TintTypedArray var26 = ThemeEnforcement.obtainTintedStyledAttributes(var1, var2, R.styleable.TextInputLayout, var3, DEF_STYLE_RES, R.styleable.TextInputLayout_counterTextAppearance, R.styleable.TextInputLayout_counterOverflowTextAppearance, R.styleable.TextInputLayout_errorTextAppearance, R.styleable.TextInputLayout_helperTextTextAppearance, R.styleable.TextInputLayout_hintTextAppearance);
      this.hintEnabled = var26.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
      this.setHint(var26.getText(R.styleable.TextInputLayout_android_hint));
      this.hintAnimationEnabled = var26.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
      this.shapeAppearanceModel = ShapeAppearanceModel.builder(var1, var2, var3, DEF_STYLE_RES).build();
      this.boxLabelCutoutPaddingPx = var1.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_box_label_cutout_padding);
      this.boxCollapsedPaddingTopPx = var26.getDimensionPixelOffset(R.styleable.TextInputLayout_boxCollapsedPaddingTop, 0);
      this.boxStrokeWidthDefaultPx = var26.getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidth, var1.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_default));
      this.boxStrokeWidthFocusedPx = var26.getDimensionPixelSize(R.styleable.TextInputLayout_boxStrokeWidthFocused, var1.getResources().getDimensionPixelSize(R.dimen.mtrl_textinput_box_stroke_width_focused));
      this.boxStrokeWidthPx = this.boxStrokeWidthDefaultPx;
      float var5 = var26.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopStart, -1.0F);
      float var6 = var26.getDimension(R.styleable.TextInputLayout_boxCornerRadiusTopEnd, -1.0F);
      float var7 = var26.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomEnd, -1.0F);
      float var8 = var26.getDimension(R.styleable.TextInputLayout_boxCornerRadiusBottomStart, -1.0F);
      ShapeAppearanceModel.Builder var22 = this.shapeAppearanceModel.toBuilder();
      if (var5 >= 0.0F) {
         var22.setTopLeftCornerSize(var5);
      }

      if (var6 >= 0.0F) {
         var22.setTopRightCornerSize(var6);
      }

      if (var7 >= 0.0F) {
         var22.setBottomRightCornerSize(var7);
      }

      if (var8 >= 0.0F) {
         var22.setBottomLeftCornerSize(var8);
      }

      this.shapeAppearanceModel = var22.build();
      ColorStateList var23 = MaterialResources.getColorStateList(var1, var26, R.styleable.TextInputLayout_boxBackgroundColor);
      if (var23 != null) {
         var3 = var23.getDefaultColor();
         this.defaultFilledBackgroundColor = var3;
         this.boxBackgroundColor = var3;
         if (var23.isStateful()) {
            this.disabledFilledBackgroundColor = var23.getColorForState(new int[]{-16842910}, -1);
            this.focusedFilledBackgroundColor = var23.getColorForState(new int[]{16842908, 16842910}, -1);
            this.hoveredFilledBackgroundColor = var23.getColorForState(new int[]{16843623, 16842910}, -1);
         } else {
            this.focusedFilledBackgroundColor = this.defaultFilledBackgroundColor;
            var23 = AppCompatResources.getColorStateList(var1, R.color.mtrl_filled_background_color);
            this.disabledFilledBackgroundColor = var23.getColorForState(new int[]{-16842910}, -1);
            this.hoveredFilledBackgroundColor = var23.getColorForState(new int[]{16843623}, -1);
         }
      } else {
         this.boxBackgroundColor = 0;
         this.defaultFilledBackgroundColor = 0;
         this.disabledFilledBackgroundColor = 0;
         this.focusedFilledBackgroundColor = 0;
         this.hoveredFilledBackgroundColor = 0;
      }

      if (var26.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
         var23 = var26.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
         this.focusedTextColor = var23;
         this.defaultHintTextColor = var23;
      }

      var23 = MaterialResources.getColorStateList(var1, var26, R.styleable.TextInputLayout_boxStrokeColor);
      this.focusedStrokeColor = var26.getColor(R.styleable.TextInputLayout_boxStrokeColor, 0);
      this.defaultStrokeColor = ContextCompat.getColor(var1, R.color.mtrl_textinput_default_box_stroke_color);
      this.disabledColor = ContextCompat.getColor(var1, R.color.mtrl_textinput_disabled_color);
      this.hoveredStrokeColor = ContextCompat.getColor(var1, R.color.mtrl_textinput_hovered_box_stroke_color);
      if (var23 != null) {
         this.setBoxStrokeColorStateList(var23);
      }

      if (var26.hasValue(R.styleable.TextInputLayout_boxStrokeErrorColor)) {
         this.setBoxStrokeErrorColor(MaterialResources.getColorStateList(var1, var26, R.styleable.TextInputLayout_boxStrokeErrorColor));
      }

      if (var26.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
         this.setHintTextAppearance(var26.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
      }

      int var9 = var26.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
      CharSequence var24 = var26.getText(R.styleable.TextInputLayout_errorContentDescription);
      boolean var10 = var26.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
      CheckableImageButton var11 = (CheckableImageButton)LayoutInflater.from(this.getContext()).inflate(R.layout.design_text_input_end_icon, this.endLayout, false);
      this.errorIconView = var11;
      var11.setVisibility(8);
      if (var26.hasValue(R.styleable.TextInputLayout_errorIconDrawable)) {
         this.setErrorIconDrawable(var26.getDrawable(R.styleable.TextInputLayout_errorIconDrawable));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_errorIconTint)) {
         this.setErrorIconTintList(MaterialResources.getColorStateList(var1, var26, R.styleable.TextInputLayout_errorIconTint));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_errorIconTintMode)) {
         this.setErrorIconTintMode(ViewUtils.parseTintMode(var26.getInt(R.styleable.TextInputLayout_errorIconTintMode, -1), (Mode)null));
      }

      this.errorIconView.setContentDescription(this.getResources().getText(R.string.error_icon_content_description));
      ViewCompat.setImportantForAccessibility(this.errorIconView, 2);
      this.errorIconView.setClickable(false);
      this.errorIconView.setPressable(false);
      this.errorIconView.setFocusable(false);
      int var12 = var26.getResourceId(R.styleable.TextInputLayout_helperTextTextAppearance, 0);
      boolean var13 = var26.getBoolean(R.styleable.TextInputLayout_helperTextEnabled, false);
      CharSequence var14 = var26.getText(R.styleable.TextInputLayout_helperText);
      var3 = var26.getResourceId(R.styleable.TextInputLayout_placeholderTextAppearance, 0);
      CharSequence var15 = var26.getText(R.styleable.TextInputLayout_placeholderText);
      int var16 = var26.getResourceId(R.styleable.TextInputLayout_prefixTextAppearance, 0);
      CharSequence var17 = var26.getText(R.styleable.TextInputLayout_prefixText);
      int var18 = var26.getResourceId(R.styleable.TextInputLayout_suffixTextAppearance, 0);
      CharSequence var27 = var26.getText(R.styleable.TextInputLayout_suffixText);
      boolean var19 = var26.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
      this.setCounterMaxLength(var26.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
      this.counterTextAppearance = var26.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
      this.counterOverflowTextAppearance = var26.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
      CheckableImageButton var20 = (CheckableImageButton)LayoutInflater.from(this.getContext()).inflate(R.layout.design_text_input_start_icon, this.startLayout, false);
      this.startIconView = var20;
      var20.setVisibility(8);
      this.setStartIconOnClickListener((OnClickListener)null);
      this.setStartIconOnLongClickListener((OnLongClickListener)null);
      if (var26.hasValue(R.styleable.TextInputLayout_startIconDrawable)) {
         this.setStartIconDrawable(var26.getDrawable(R.styleable.TextInputLayout_startIconDrawable));
         if (var26.hasValue(R.styleable.TextInputLayout_startIconContentDescription)) {
            this.setStartIconContentDescription(var26.getText(R.styleable.TextInputLayout_startIconContentDescription));
         }

         this.setStartIconCheckable(var26.getBoolean(R.styleable.TextInputLayout_startIconCheckable, true));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_startIconTint)) {
         this.setStartIconTintList(MaterialResources.getColorStateList(var1, var26, R.styleable.TextInputLayout_startIconTint));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_startIconTintMode)) {
         this.setStartIconTintMode(ViewUtils.parseTintMode(var26.getInt(R.styleable.TextInputLayout_startIconTintMode, -1), (Mode)null));
      }

      this.setBoxBackgroundMode(var26.getInt(R.styleable.TextInputLayout_boxBackgroundMode, 0));
      var20 = (CheckableImageButton)LayoutInflater.from(this.getContext()).inflate(R.layout.design_text_input_end_icon, this.endIconFrame, false);
      this.endIconView = var20;
      this.endIconFrame.addView(var20);
      this.endIconView.setVisibility(8);
      this.endIconDelegates.append(-1, new CustomEndIconDelegate(this));
      this.endIconDelegates.append(0, new NoEndIconDelegate(this));
      this.endIconDelegates.append(1, new PasswordToggleEndIconDelegate(this));
      this.endIconDelegates.append(2, new ClearTextEndIconDelegate(this));
      this.endIconDelegates.append(3, new DropdownMenuEndIconDelegate(this));
      if (var26.hasValue(R.styleable.TextInputLayout_endIconMode)) {
         this.setEndIconMode(var26.getInt(R.styleable.TextInputLayout_endIconMode, 0));
         if (var26.hasValue(R.styleable.TextInputLayout_endIconDrawable)) {
            this.setEndIconDrawable(var26.getDrawable(R.styleable.TextInputLayout_endIconDrawable));
         }

         if (var26.hasValue(R.styleable.TextInputLayout_endIconContentDescription)) {
            this.setEndIconContentDescription(var26.getText(R.styleable.TextInputLayout_endIconContentDescription));
         }

         this.setEndIconCheckable(var26.getBoolean(R.styleable.TextInputLayout_endIconCheckable, true));
      } else if (var26.hasValue(R.styleable.TextInputLayout_passwordToggleEnabled)) {
         this.setEndIconMode(var26.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false));
         this.setEndIconDrawable(var26.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable));
         this.setEndIconContentDescription(var26.getText(R.styleable.TextInputLayout_passwordToggleContentDescription));
         if (var26.hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
            this.setEndIconTintList(MaterialResources.getColorStateList(var1, var26, R.styleable.TextInputLayout_passwordToggleTint));
         }

         if (var26.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.setEndIconTintMode(ViewUtils.parseTintMode(var26.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), (Mode)null));
         }
      }

      if (!var26.hasValue(R.styleable.TextInputLayout_passwordToggleEnabled)) {
         if (var26.hasValue(R.styleable.TextInputLayout_endIconTint)) {
            this.setEndIconTintList(MaterialResources.getColorStateList(var1, var26, R.styleable.TextInputLayout_endIconTint));
         }

         if (var26.hasValue(R.styleable.TextInputLayout_endIconTintMode)) {
            this.setEndIconTintMode(ViewUtils.parseTintMode(var26.getInt(R.styleable.TextInputLayout_endIconTintMode, -1), (Mode)null));
         }
      }

      AppCompatTextView var28 = new AppCompatTextView(var1);
      this.prefixTextView = var28;
      var28.setId(R.id.textinput_prefix_text);
      this.prefixTextView.setLayoutParams(new LayoutParams(-2, -2));
      ViewCompat.setAccessibilityLiveRegion(this.prefixTextView, 1);
      this.startLayout.addView(this.startIconView);
      this.startLayout.addView(this.prefixTextView);
      AppCompatTextView var21 = new AppCompatTextView(var1);
      this.suffixTextView = var21;
      var21.setId(R.id.textinput_suffix_text);
      this.suffixTextView.setLayoutParams(new LayoutParams(-2, -2, 80));
      ViewCompat.setAccessibilityLiveRegion(this.suffixTextView, 1);
      this.endLayout.addView(this.suffixTextView);
      this.endLayout.addView(this.errorIconView);
      this.endLayout.addView(this.endIconFrame);
      this.setHelperTextEnabled(var13);
      this.setHelperText(var14);
      this.setHelperTextTextAppearance(var12);
      this.setErrorEnabled(var10);
      this.setErrorTextAppearance(var9);
      this.setErrorContentDescription(var24);
      this.setCounterTextAppearance(this.counterTextAppearance);
      this.setCounterOverflowTextAppearance(this.counterOverflowTextAppearance);
      this.setPlaceholderText(var15);
      this.setPlaceholderTextAppearance(var3);
      this.setPrefixText(var17);
      this.setPrefixTextAppearance(var16);
      this.setSuffixText(var27);
      this.setSuffixTextAppearance(var18);
      if (var26.hasValue(R.styleable.TextInputLayout_errorTextColor)) {
         this.setErrorTextColor(var26.getColorStateList(R.styleable.TextInputLayout_errorTextColor));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_helperTextTextColor)) {
         this.setHelperTextColor(var26.getColorStateList(R.styleable.TextInputLayout_helperTextTextColor));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_hintTextColor)) {
         this.setHintTextColor(var26.getColorStateList(R.styleable.TextInputLayout_hintTextColor));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_counterTextColor)) {
         this.setCounterTextColor(var26.getColorStateList(R.styleable.TextInputLayout_counterTextColor));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_counterOverflowTextColor)) {
         this.setCounterOverflowTextColor(var26.getColorStateList(R.styleable.TextInputLayout_counterOverflowTextColor));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_placeholderTextColor)) {
         this.setPlaceholderTextColor(var26.getColorStateList(R.styleable.TextInputLayout_placeholderTextColor));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_prefixTextColor)) {
         this.setPrefixTextColor(var26.getColorStateList(R.styleable.TextInputLayout_prefixTextColor));
      }

      if (var26.hasValue(R.styleable.TextInputLayout_suffixTextColor)) {
         this.setSuffixTextColor(var26.getColorStateList(R.styleable.TextInputLayout_suffixTextColor));
      }

      this.setCounterEnabled(var19);
      this.setEnabled(var26.getBoolean(R.styleable.TextInputLayout_android_enabled, true));
      var26.recycle();
      ViewCompat.setImportantForAccessibility(this, 2);
   }

   private void addPlaceholderTextView() {
      TextView var1 = this.placeholderTextView;
      if (var1 != null) {
         this.inputFrame.addView(var1);
         this.placeholderTextView.setVisibility(0);
      }

   }

   private void applyBoxAttributes() {
      MaterialShapeDrawable var1 = this.boxBackground;
      if (var1 != null) {
         var1.setShapeAppearanceModel(this.shapeAppearanceModel);
         if (this.canDrawOutlineStroke()) {
            this.boxBackground.setStroke((float)this.boxStrokeWidthPx, this.boxStrokeColor);
         }

         int var2 = this.calculateBoxBackgroundColor();
         this.boxBackgroundColor = var2;
         this.boxBackground.setFillColor(ColorStateList.valueOf(var2));
         if (this.endIconMode == 3) {
            this.editText.getBackground().invalidateSelf();
         }

         this.applyBoxUnderlineAttributes();
         this.invalidate();
      }
   }

   private void applyBoxUnderlineAttributes() {
      if (this.boxUnderline != null) {
         if (this.canDrawStroke()) {
            this.boxUnderline.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
         }

         this.invalidate();
      }
   }

   private void applyCutoutPadding(RectF var1) {
      var1.left -= (float)this.boxLabelCutoutPaddingPx;
      var1.top -= (float)this.boxLabelCutoutPaddingPx;
      var1.right += (float)this.boxLabelCutoutPaddingPx;
      var1.bottom += (float)this.boxLabelCutoutPaddingPx;
   }

   private void applyEndIconTint() {
      this.applyIconTint(this.endIconView, this.hasEndIconTintList, this.endIconTintList, this.hasEndIconTintMode, this.endIconTintMode);
   }

   private void applyIconTint(CheckableImageButton var1, boolean var2, ColorStateList var3, boolean var4, Mode var5) {
      Drawable var6 = var1.getDrawable();
      Drawable var7 = var6;
      if (var6 != null) {
         label26: {
            if (!var2) {
               var7 = var6;
               if (!var4) {
                  break label26;
               }
            }

            var6 = DrawableCompat.wrap(var6).mutate();
            if (var2) {
               DrawableCompat.setTintList(var6, var3);
            }

            var7 = var6;
            if (var4) {
               DrawableCompat.setTintMode(var6, var5);
               var7 = var6;
            }
         }
      }

      if (var1.getDrawable() != var7) {
         var1.setImageDrawable(var7);
      }

   }

   private void applyStartIconTint() {
      this.applyIconTint(this.startIconView, this.hasStartIconTintList, this.startIconTintList, this.hasStartIconTintMode, this.startIconTintMode);
   }

   private void assignBoxBackgroundByMode() {
      int var1 = this.boxBackgroundMode;
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               StringBuilder var2 = new StringBuilder();
               var2.append(this.boxBackgroundMode);
               var2.append(" is illegal; only @BoxBackgroundMode constants are supported.");
               throw new IllegalArgumentException(var2.toString());
            }

            if (this.hintEnabled && !(this.boxBackground instanceof CutoutDrawable)) {
               this.boxBackground = new CutoutDrawable(this.shapeAppearanceModel);
            } else {
               this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            }

            this.boxUnderline = null;
         } else {
            this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.boxUnderline = new MaterialShapeDrawable();
         }
      } else {
         this.boxBackground = null;
         this.boxUnderline = null;
      }

   }

   private int calculateBoxBackgroundColor() {
      int var1 = this.boxBackgroundColor;
      if (this.boxBackgroundMode == 1) {
         var1 = MaterialColors.layer(MaterialColors.getColor((View)this, R.attr.colorSurface, 0), this.boxBackgroundColor);
      }

      return var1;
   }

   private Rect calculateCollapsedTextBounds(Rect var1) {
      if (this.editText != null) {
         Rect var2 = this.tmpBoundsRect;
         boolean var3;
         if (ViewCompat.getLayoutDirection(this) == 1) {
            var3 = true;
         } else {
            var3 = false;
         }

         var2.bottom = var1.bottom;
         int var4 = this.boxBackgroundMode;
         if (var4 != 1) {
            if (var4 != 2) {
               var2.left = this.getLabelLeftBoundAlightWithPrefix(var1.left, var3);
               var2.top = this.getPaddingTop();
               var2.right = this.getLabelRightBoundAlignedWithSuffix(var1.right, var3);
               return var2;
            } else {
               var2.left = var1.left + this.editText.getPaddingLeft();
               var2.top = var1.top - this.calculateLabelMarginTop();
               var2.right = var1.right - this.editText.getPaddingRight();
               return var2;
            }
         } else {
            var2.left = this.getLabelLeftBoundAlightWithPrefix(var1.left, var3);
            var2.top = var1.top + this.boxCollapsedPaddingTopPx;
            var2.right = this.getLabelRightBoundAlignedWithSuffix(var1.right, var3);
            return var2;
         }
      } else {
         throw new IllegalStateException();
      }
   }

   private int calculateExpandedLabelBottom(Rect var1, Rect var2, float var3) {
      return this.isSingleLineFilledTextField() ? (int)((float)var2.top + var3) : var1.bottom - this.editText.getCompoundPaddingBottom();
   }

   private int calculateExpandedLabelTop(Rect var1, float var2) {
      return this.isSingleLineFilledTextField() ? (int)((float)var1.centerY() - var2 / 2.0F) : var1.top + this.editText.getCompoundPaddingTop();
   }

   private Rect calculateExpandedTextBounds(Rect var1) {
      if (this.editText != null) {
         Rect var2 = this.tmpBoundsRect;
         float var3 = this.collapsingTextHelper.getExpandedTextHeight();
         var2.left = var1.left + this.editText.getCompoundPaddingLeft();
         var2.top = this.calculateExpandedLabelTop(var1, var3);
         var2.right = var1.right - this.editText.getCompoundPaddingRight();
         var2.bottom = this.calculateExpandedLabelBottom(var1, var2, var3);
         return var2;
      } else {
         throw new IllegalStateException();
      }
   }

   private int calculateLabelMarginTop() {
      if (!this.hintEnabled) {
         return 0;
      } else {
         int var1 = this.boxBackgroundMode;
         float var2;
         if (var1 != 0 && var1 != 1) {
            if (var1 != 2) {
               return 0;
            }

            var2 = this.collapsingTextHelper.getCollapsedTextHeight() / 2.0F;
         } else {
            var2 = this.collapsingTextHelper.getCollapsedTextHeight();
         }

         return (int)var2;
      }
   }

   private boolean canDrawOutlineStroke() {
      boolean var1;
      if (this.boxBackgroundMode == 2 && this.canDrawStroke()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean canDrawStroke() {
      boolean var1;
      if (this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void closeCutout() {
      if (this.cutoutEnabled()) {
         ((CutoutDrawable)this.boxBackground).removeCutout();
      }

   }

   private void collapseHint(boolean var1) {
      ValueAnimator var2 = this.animator;
      if (var2 != null && var2.isRunning()) {
         this.animator.cancel();
      }

      if (var1 && this.hintAnimationEnabled) {
         this.animateToExpansionFraction(1.0F);
      } else {
         this.collapsingTextHelper.setExpansionFraction(1.0F);
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
      boolean var1;
      if (this.hintEnabled && !TextUtils.isEmpty(this.hint) && this.boxBackground instanceof CutoutDrawable) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void dispatchOnEditTextAttached() {
      Iterator var1 = this.editTextAttachedListeners.iterator();

      while(var1.hasNext()) {
         ((TextInputLayout.OnEditTextAttachedListener)var1.next()).onEditTextAttached(this);
      }

   }

   private void dispatchOnEndIconChanged(int var1) {
      Iterator var2 = this.endIconChangedListeners.iterator();

      while(var2.hasNext()) {
         ((TextInputLayout.OnEndIconChangedListener)var2.next()).onEndIconChanged(this, var1);
      }

   }

   private void drawBoxUnderline(Canvas var1) {
      MaterialShapeDrawable var2 = this.boxUnderline;
      if (var2 != null) {
         Rect var3 = var2.getBounds();
         var3.top = var3.bottom - this.boxStrokeWidthPx;
         this.boxUnderline.draw(var1);
      }

   }

   private void drawHint(Canvas var1) {
      if (this.hintEnabled) {
         this.collapsingTextHelper.draw(var1);
      }

   }

   private void expandHint(boolean var1) {
      ValueAnimator var2 = this.animator;
      if (var2 != null && var2.isRunning()) {
         this.animator.cancel();
      }

      if (var1 && this.hintAnimationEnabled) {
         this.animateToExpansionFraction(0.0F);
      } else {
         this.collapsingTextHelper.setExpansionFraction(0.0F);
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
      EndIconDelegate var1 = (EndIconDelegate)this.endIconDelegates.get(this.endIconMode);
      if (var1 == null) {
         var1 = (EndIconDelegate)this.endIconDelegates.get(0);
      }

      return var1;
   }

   private CheckableImageButton getEndIconToUpdateDummyDrawable() {
      if (this.errorIconView.getVisibility() == 0) {
         return this.errorIconView;
      } else {
         return this.hasEndIcon() && this.isEndIconVisible() ? this.endIconView : null;
      }
   }

   private int getLabelLeftBoundAlightWithPrefix(int var1, boolean var2) {
      int var3 = var1 + this.editText.getCompoundPaddingLeft();
      var1 = var3;
      if (this.prefixText != null) {
         var1 = var3;
         if (!var2) {
            var1 = var3 - this.prefixTextView.getMeasuredWidth() + this.prefixTextView.getPaddingLeft();
         }
      }

      return var1;
   }

   private int getLabelRightBoundAlignedWithSuffix(int var1, boolean var2) {
      int var3 = var1 - this.editText.getCompoundPaddingRight();
      var1 = var3;
      if (this.prefixText != null) {
         var1 = var3;
         if (var2) {
            var1 = var3 + (this.prefixTextView.getMeasuredWidth() - this.prefixTextView.getPaddingRight());
         }
      }

      return var1;
   }

   private boolean hasEndIcon() {
      boolean var1;
      if (this.endIconMode != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void hidePlaceholderText() {
      TextView var1 = this.placeholderTextView;
      if (var1 != null && this.placeholderEnabled) {
         var1.setText((CharSequence)null);
         this.placeholderTextView.setVisibility(4);
      }

   }

   private boolean isErrorIconVisible() {
      boolean var1;
      if (this.errorIconView.getVisibility() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isSingleLineFilledTextField() {
      int var1 = this.boxBackgroundMode;
      boolean var2 = true;
      boolean var3;
      if (var1 == 1) {
         var3 = var2;
         if (VERSION.SDK_INT < 16) {
            return var3;
         }

         if (this.editText.getMinLines() <= 1) {
            var3 = var2;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   private void onApplyBoxBackgroundMode() {
      this.assignBoxBackgroundByMode();
      this.setEditTextBoxBackground();
      this.updateTextInputBoxState();
      if (this.boxBackgroundMode != 0) {
         this.updateInputLayoutMargins();
      }

   }

   private void openCutout() {
      if (this.cutoutEnabled()) {
         RectF var1 = this.tmpRectF;
         this.collapsingTextHelper.getCollapsedTextActualBounds(var1, this.editText.getWidth(), this.editText.getGravity());
         this.applyCutoutPadding(var1);
         var1.offset((float)(-this.getPaddingLeft()), (float)(-this.getPaddingTop()));
         ((CutoutDrawable)this.boxBackground).setCutout(var1);
      }
   }

   private static void recursiveSetEnabled(ViewGroup var0, boolean var1) {
      int var2 = var0.getChildCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         View var4 = var0.getChildAt(var3);
         var4.setEnabled(var1);
         if (var4 instanceof ViewGroup) {
            recursiveSetEnabled((ViewGroup)var4, var1);
         }
      }

   }

   private void removePlaceholderTextView() {
      TextView var1 = this.placeholderTextView;
      if (var1 != null) {
         var1.setVisibility(8);
      }

   }

   private void setEditText(EditText var1) {
      if (this.editText == null) {
         if (this.endIconMode != 3 && !(var1 instanceof TextInputEditText)) {
            Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
         }

         this.editText = var1;
         this.onApplyBoxBackgroundMode();
         this.setTextInputAccessibilityDelegate(new TextInputLayout.AccessibilityDelegate(this));
         this.collapsingTextHelper.setTypefaces(this.editText.getTypeface());
         this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
         int var2 = this.editText.getGravity();
         this.collapsingTextHelper.setCollapsedTextGravity(var2 & -113 | 48);
         this.collapsingTextHelper.setExpandedTextGravity(var2);
         this.editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable var1) {
               TextInputLayout var2 = TextInputLayout.this;
               var2.updateLabelState(var2.restoringSavedState ^ true);
               if (TextInputLayout.this.counterEnabled) {
                  TextInputLayout.this.updateCounter(var1.length());
               }

               if (TextInputLayout.this.placeholderEnabled) {
                  TextInputLayout.this.updatePlaceholderText(var1.length());
               }

            }

            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }
         });
         if (this.defaultHintTextColor == null) {
            this.defaultHintTextColor = this.editText.getHintTextColors();
         }

         if (this.hintEnabled) {
            if (TextUtils.isEmpty(this.hint)) {
               CharSequence var3 = this.editText.getHint();
               this.originalHint = var3;
               this.setHint(var3);
               this.editText.setHint((CharSequence)null);
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
            var1.setEnabled(false);
         }

         this.updateLabelState(false, true);
      } else {
         throw new IllegalArgumentException("We already have an EditText, can only have one");
      }
   }

   private void setEditTextBoxBackground() {
      if (this.shouldUseEditTextBackgroundForBoxBackground()) {
         ViewCompat.setBackground(this.editText, this.boxBackground);
      }

   }

   private void setErrorIconVisible(boolean var1) {
      CheckableImageButton var2 = this.errorIconView;
      byte var3 = 0;
      byte var4;
      if (var1) {
         var4 = 0;
      } else {
         var4 = 8;
      }

      var2.setVisibility(var4);
      FrameLayout var5 = this.endIconFrame;
      var4 = var3;
      if (var1) {
         var4 = 8;
      }

      var5.setVisibility(var4);
      this.updateSuffixTextViewPadding();
      if (!this.hasEndIcon()) {
         this.updateDummyDrawables();
      }

   }

   private void setHintInternal(CharSequence var1) {
      if (!TextUtils.equals(var1, this.hint)) {
         this.hint = var1;
         this.collapsingTextHelper.setText(var1);
         if (!this.hintExpanded) {
            this.openCutout();
         }
      }

   }

   private static void setIconClickable(CheckableImageButton var0, OnLongClickListener var1) {
      boolean var2 = ViewCompat.hasOnClickListeners(var0);
      boolean var3 = false;
      byte var4 = 1;
      boolean var5;
      if (var1 != null) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var2 || var5) {
         var3 = true;
      }

      var0.setFocusable(var3);
      var0.setClickable(var2);
      var0.setPressable(var2);
      var0.setLongClickable(var5);
      if (!var3) {
         var4 = 2;
      }

      ViewCompat.setImportantForAccessibility(var0, var4);
   }

   private static void setIconOnClickListener(CheckableImageButton var0, OnClickListener var1, OnLongClickListener var2) {
      var0.setOnClickListener(var1);
      setIconClickable(var0, var2);
   }

   private static void setIconOnLongClickListener(CheckableImageButton var0, OnLongClickListener var1) {
      var0.setOnLongClickListener(var1);
      setIconClickable(var0, var1);
   }

   private void setPlaceholderTextEnabled(boolean var1) {
      if (this.placeholderEnabled != var1) {
         if (var1) {
            AppCompatTextView var2 = new AppCompatTextView(this.getContext());
            this.placeholderTextView = var2;
            var2.setId(R.id.textinput_placeholder);
            ViewCompat.setAccessibilityLiveRegion(this.placeholderTextView, 1);
            this.setPlaceholderTextAppearance(this.placeholderTextAppearance);
            this.setPlaceholderTextColor(this.placeholderTextColor);
            this.addPlaceholderTextView();
         } else {
            this.removePlaceholderTextView();
            this.placeholderTextView = null;
         }

         this.placeholderEnabled = var1;
      }
   }

   private boolean shouldUpdateEndDummyDrawable() {
      boolean var1;
      if ((this.errorIconView.getVisibility() == 0 || this.hasEndIcon() && this.isEndIconVisible() || this.suffixText != null) && this.endLayout.getMeasuredWidth() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean shouldUpdateStartDummyDrawable() {
      boolean var1;
      if ((this.getStartIconDrawable() != null || this.prefixText != null) && this.startLayout.getMeasuredWidth() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean shouldUseEditTextBackgroundForBoxBackground() {
      EditText var1 = this.editText;
      boolean var2;
      if (var1 != null && this.boxBackground != null && var1.getBackground() == null && this.boxBackgroundMode != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void showPlaceholderText() {
      TextView var1 = this.placeholderTextView;
      if (var1 != null && this.placeholderEnabled) {
         var1.setText(this.placeholderText);
         this.placeholderTextView.setVisibility(0);
         this.placeholderTextView.bringToFront();
      }

   }

   private void tintEndIconOnError(boolean var1) {
      if (var1 && this.getEndIconDrawable() != null) {
         Drawable var2 = DrawableCompat.wrap(this.getEndIconDrawable()).mutate();
         DrawableCompat.setTint(var2, this.indicatorViewController.getErrorViewCurrentTextColor());
         this.endIconView.setImageDrawable(var2);
      } else {
         this.applyEndIconTint();
      }

   }

   private void updateBoxUnderlineBounds(Rect var1) {
      if (this.boxUnderline != null) {
         int var2 = var1.bottom;
         int var3 = this.boxStrokeWidthFocusedPx;
         this.boxUnderline.setBounds(var1.left, var2 - var3, var1.right, var1.bottom);
      }

   }

   private void updateCounter() {
      if (this.counterView != null) {
         EditText var1 = this.editText;
         int var2;
         if (var1 == null) {
            var2 = 0;
         } else {
            var2 = var1.getText().length();
         }

         this.updateCounter(var2);
      }

   }

   private static void updateCounterContentDescription(Context var0, TextView var1, int var2, int var3, boolean var4) {
      int var5;
      if (var4) {
         var5 = R.string.character_counter_overflowed_content_description;
      } else {
         var5 = R.string.character_counter_content_description;
      }

      var1.setContentDescription(var0.getString(var5, new Object[]{var2, var3}));
   }

   private void updateCounterTextAppearanceAndColor() {
      TextView var1 = this.counterView;
      if (var1 != null) {
         int var2;
         if (this.counterOverflowed) {
            var2 = this.counterOverflowTextAppearance;
         } else {
            var2 = this.counterTextAppearance;
         }

         this.setTextAppearanceCompatWithErrorFallback(var1, var2);
         ColorStateList var3;
         if (!this.counterOverflowed) {
            var3 = this.counterTextColor;
            if (var3 != null) {
               this.counterView.setTextColor(var3);
            }
         }

         if (this.counterOverflowed) {
            var3 = this.counterOverflowTextColor;
            if (var3 != null) {
               this.counterView.setTextColor(var3);
            }
         }
      }

   }

   private boolean updateDummyDrawables() {
      if (this.editText == null) {
         return false;
      } else {
         boolean var1;
         boolean var2;
         boolean var3;
         int var4;
         Drawable var7;
         Drawable[] var10;
         label62: {
            label61: {
               var1 = this.shouldUpdateStartDummyDrawable();
               var2 = true;
               var3 = true;
               if (var1) {
                  var4 = this.startLayout.getMeasuredWidth() - this.editText.getPaddingLeft();
                  if (this.startDummyDrawable == null || this.startDummyDrawableWidth != var4) {
                     ColorDrawable var5 = new ColorDrawable();
                     this.startDummyDrawable = var5;
                     this.startDummyDrawableWidth = var4;
                     var5.setBounds(0, 0, var4, 1);
                  }

                  Drawable[] var6 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
                  var7 = var6[0];
                  Drawable var9 = this.startDummyDrawable;
                  if (var7 != var9) {
                     TextViewCompat.setCompoundDrawablesRelative(this.editText, var9, var6[1], var6[2], var6[3]);
                     break label61;
                  }
               } else if (this.startDummyDrawable != null) {
                  var10 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
                  TextViewCompat.setCompoundDrawablesRelative(this.editText, (Drawable)null, var10[1], var10[2], var10[3]);
                  this.startDummyDrawable = null;
                  break label61;
               }

               var1 = false;
               break label62;
            }

            var1 = true;
         }

         if (this.shouldUpdateEndDummyDrawable()) {
            int var8 = this.suffixTextView.getMeasuredWidth() - this.editText.getPaddingRight();
            CheckableImageButton var11 = this.getEndIconToUpdateDummyDrawable();
            var4 = var8;
            if (var11 != null) {
               var4 = var8 + var11.getMeasuredWidth() + MarginLayoutParamsCompat.getMarginStart((MarginLayoutParams)var11.getLayoutParams());
            }

            var10 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            Drawable var12 = this.endDummyDrawable;
            if (var12 != null && this.endDummyDrawableWidth != var4) {
               this.endDummyDrawableWidth = var4;
               var12.setBounds(0, 0, var4, 1);
               TextViewCompat.setCompoundDrawablesRelative(this.editText, var10[0], var10[1], this.endDummyDrawable, var10[3]);
               var1 = var2;
            } else {
               if (this.endDummyDrawable == null) {
                  ColorDrawable var13 = new ColorDrawable();
                  this.endDummyDrawable = var13;
                  this.endDummyDrawableWidth = var4;
                  var13.setBounds(0, 0, var4, 1);
               }

               var12 = var10[2];
               var7 = this.endDummyDrawable;
               if (var12 != var7) {
                  this.originalEditTextEndDrawable = var10[2];
                  TextViewCompat.setCompoundDrawablesRelative(this.editText, var10[0], var10[1], var7, var10[3]);
                  var1 = var2;
               }
            }
         } else {
            var2 = var1;
            if (this.endDummyDrawable == null) {
               return var2;
            }

            var10 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            if (var10[2] == this.endDummyDrawable) {
               TextViewCompat.setCompoundDrawablesRelative(this.editText, var10[0], var10[1], this.originalEditTextEndDrawable, var10[3]);
               var1 = var3;
            }

            this.endDummyDrawable = null;
         }

         var2 = var1;
         return var2;
      }
   }

   private boolean updateEditTextHeightBasedOnIcon() {
      if (this.editText == null) {
         return false;
      } else {
         int var1 = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight());
         if (this.editText.getMeasuredHeight() < var1) {
            this.editText.setMinimumHeight(var1);
            return true;
         } else {
            return false;
         }
      }
   }

   private void updateIconColorOnState(CheckableImageButton var1, ColorStateList var2) {
      Drawable var3 = var1.getDrawable();
      if (var1.getDrawable() != null && var2 != null && var2.isStateful()) {
         int var4 = var2.getColorForState(this.getDrawableState(), var2.getDefaultColor());
         Drawable var5 = DrawableCompat.wrap(var3).mutate();
         DrawableCompat.setTintList(var5, ColorStateList.valueOf(var4));
         var1.setImageDrawable(var5);
      }

   }

   private void updateInputLayoutMargins() {
      if (this.boxBackgroundMode != 1) {
         android.widget.LinearLayout.LayoutParams var1 = (android.widget.LinearLayout.LayoutParams)this.inputFrame.getLayoutParams();
         int var2 = this.calculateLabelMarginTop();
         if (var2 != var1.topMargin) {
            var1.topMargin = var2;
            this.inputFrame.requestLayout();
         }
      }

   }

   private void updateLabelState(boolean var1, boolean var2) {
      boolean var3 = this.isEnabled();
      EditText var4 = this.editText;
      boolean var5;
      if (var4 != null && !TextUtils.isEmpty(var4.getText())) {
         var5 = true;
      } else {
         var5 = false;
      }

      var4 = this.editText;
      boolean var6;
      if (var4 != null && var4.hasFocus()) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7 = this.indicatorViewController.errorShouldBeShown();
      ColorStateList var9 = this.defaultHintTextColor;
      if (var9 != null) {
         this.collapsingTextHelper.setCollapsedTextColor(var9);
         this.collapsingTextHelper.setExpandedTextColor(this.defaultHintTextColor);
      }

      if (!var3) {
         var9 = this.defaultHintTextColor;
         int var8;
         if (var9 != null) {
            var8 = this.disabledColor;
            var8 = var9.getColorForState(new int[]{-16842910}, var8);
         } else {
            var8 = this.disabledColor;
         }

         this.collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf(var8));
         this.collapsingTextHelper.setExpandedTextColor(ColorStateList.valueOf(var8));
      } else if (var7) {
         this.collapsingTextHelper.setCollapsedTextColor(this.indicatorViewController.getErrorViewTextColors());
      } else {
         label65: {
            if (this.counterOverflowed) {
               TextView var10 = this.counterView;
               if (var10 != null) {
                  this.collapsingTextHelper.setCollapsedTextColor(var10.getTextColors());
                  break label65;
               }
            }

            if (var6) {
               var9 = this.focusedTextColor;
               if (var9 != null) {
                  this.collapsingTextHelper.setCollapsedTextColor(var9);
               }
            }
         }
      }

      if (var5 || this.isEnabled() && (var6 || var7)) {
         if (var2 || this.hintExpanded) {
            this.collapseHint(var1);
         }
      } else if (var2 || !this.hintExpanded) {
         this.expandHint(var1);
      }

   }

   private void updatePlaceholderMeasurementsBasedOnEditText() {
      if (this.placeholderTextView != null) {
         EditText var1 = this.editText;
         if (var1 != null) {
            int var2 = var1.getGravity();
            this.placeholderTextView.setGravity(var2);
            this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
         }
      }

   }

   private void updatePlaceholderText() {
      EditText var1 = this.editText;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.getText().length();
      }

      this.updatePlaceholderText(var2);
   }

   private void updatePlaceholderText(int var1) {
      if (var1 == 0 && !this.hintExpanded) {
         this.showPlaceholderText();
      } else {
         this.hidePlaceholderText();
      }

   }

   private void updatePrefixTextViewPadding() {
      if (this.editText != null) {
         int var1;
         if (this.isStartIconVisible()) {
            var1 = 0;
         } else {
            var1 = ViewCompat.getPaddingStart(this.editText);
         }

         ViewCompat.setPaddingRelative(this.prefixTextView, var1, this.editText.getCompoundPaddingTop(), 0, this.editText.getCompoundPaddingBottom());
      }
   }

   private void updatePrefixTextVisibility() {
      TextView var1 = this.prefixTextView;
      byte var2;
      if (this.prefixText != null && !this.isHintExpanded()) {
         var2 = 0;
      } else {
         var2 = 8;
      }

      var1.setVisibility(var2);
      this.updateDummyDrawables();
   }

   private void updateStrokeErrorColor(boolean var1, boolean var2) {
      int var3 = this.strokeErrorColor.getDefaultColor();
      int var4 = this.strokeErrorColor.getColorForState(new int[]{16843623, 16842910}, var3);
      int var5 = this.strokeErrorColor.getColorForState(new int[]{16843518, 16842910}, var3);
      if (var1) {
         this.boxStrokeColor = var5;
      } else if (var2) {
         this.boxStrokeColor = var4;
      } else {
         this.boxStrokeColor = var3;
      }

   }

   private void updateSuffixTextViewPadding() {
      if (this.editText != null) {
         int var1;
         if (!this.isEndIconVisible() && !this.isErrorIconVisible()) {
            var1 = ViewCompat.getPaddingEnd(this.editText);
         } else {
            var1 = 0;
         }

         ViewCompat.setPaddingRelative(this.suffixTextView, 0, this.editText.getPaddingTop(), var1, this.editText.getPaddingBottom());
      }
   }

   private void updateSuffixTextVisibility() {
      int var1 = this.suffixTextView.getVisibility();
      CharSequence var2 = this.suffixText;
      byte var3 = 0;
      boolean var4;
      if (var2 != null && !this.isHintExpanded()) {
         var4 = true;
      } else {
         var4 = false;
      }

      TextView var5 = this.suffixTextView;
      if (!var4) {
         var3 = 8;
      }

      var5.setVisibility(var3);
      if (var1 != this.suffixTextView.getVisibility()) {
         this.getEndIconDelegate().onSuffixVisibilityChanged(var4);
      }

      this.updateDummyDrawables();
   }

   public void addOnEditTextAttachedListener(TextInputLayout.OnEditTextAttachedListener var1) {
      this.editTextAttachedListeners.add(var1);
      if (this.editText != null) {
         var1.onEditTextAttached(this);
      }

   }

   public void addOnEndIconChangedListener(TextInputLayout.OnEndIconChangedListener var1) {
      this.endIconChangedListeners.add(var1);
   }

   public void addView(View var1, int var2, android.view.ViewGroup.LayoutParams var3) {
      if (var1 instanceof EditText) {
         LayoutParams var4 = new LayoutParams(var3);
         var4.gravity = var4.gravity & -113 | 16;
         this.inputFrame.addView(var1, var4);
         this.inputFrame.setLayoutParams(var3);
         this.updateInputLayoutMargins();
         this.setEditText((EditText)var1);
      } else {
         super.addView(var1, var2, var3);
      }

   }

   void animateToExpansionFraction(float var1) {
      if (this.collapsingTextHelper.getExpansionFraction() != var1) {
         if (this.animator == null) {
            ValueAnimator var2 = new ValueAnimator();
            this.animator = var2;
            var2.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.animator.setDuration(167L);
            this.animator.addUpdateListener(new AnimatorUpdateListener() {
               public void onAnimationUpdate(ValueAnimator var1) {
                  TextInputLayout.this.collapsingTextHelper.setExpansionFraction((Float)var1.getAnimatedValue());
               }
            });
         }

         this.animator.setFloatValues(new float[]{this.collapsingTextHelper.getExpansionFraction(), var1});
         this.animator.start();
      }
   }

   public void clearOnEditTextAttachedListeners() {
      this.editTextAttachedListeners.clear();
   }

   public void clearOnEndIconChangedListeners() {
      this.endIconChangedListeners.clear();
   }

   boolean cutoutIsOpen() {
      boolean var1;
      if (this.cutoutEnabled() && ((CutoutDrawable)this.boxBackground).hasCutout()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void dispatchProvideAutofillStructure(ViewStructure var1, int var2) {
      if (this.originalHint != null) {
         EditText var3 = this.editText;
         if (var3 != null) {
            boolean var4 = this.isProvidingHint;
            this.isProvidingHint = false;
            CharSequence var7 = var3.getHint();
            this.editText.setHint(this.originalHint);

            try {
               super.dispatchProvideAutofillStructure(var1, var2);
            } finally {
               this.editText.setHint(var7);
               this.isProvidingHint = var4;
            }

            return;
         }
      }

      super.dispatchProvideAutofillStructure(var1, var2);
   }

   protected void dispatchRestoreInstanceState(SparseArray<Parcelable> var1) {
      this.restoringSavedState = true;
      super.dispatchRestoreInstanceState(var1);
      this.restoringSavedState = false;
   }

   public void draw(Canvas var1) {
      super.draw(var1);
      this.drawHint(var1);
      this.drawBoxUnderline(var1);
   }

   protected void drawableStateChanged() {
      if (!this.inDrawableStateChanged) {
         boolean var1 = true;
         this.inDrawableStateChanged = true;
         super.drawableStateChanged();
         int[] var2 = this.getDrawableState();
         CollapsingTextHelper var3 = this.collapsingTextHelper;
         boolean var4;
         if (var3 != null) {
            var4 = var3.setState(var2) | false;
         } else {
            var4 = false;
         }

         if (this.editText != null) {
            if (!ViewCompat.isLaidOut(this) || !this.isEnabled()) {
               var1 = false;
            }

            this.updateLabelState(var1);
         }

         this.updateEditTextBackground();
         this.updateTextInputBoxState();
         if (var4) {
            this.invalidate();
         }

         this.inDrawableStateChanged = false;
      }
   }

   public int getBaseline() {
      EditText var1 = this.editText;
      return var1 != null ? var1.getBaseline() + this.getPaddingTop() + this.calculateLabelMarginTop() : super.getBaseline();
   }

   MaterialShapeDrawable getBoxBackground() {
      int var1 = this.boxBackgroundMode;
      if (var1 != 1 && var1 != 2) {
         throw new IllegalStateException();
      } else {
         return this.boxBackground;
      }
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
      if (this.counterEnabled && this.counterOverflowed) {
         TextView var1 = this.counterView;
         if (var1 != null) {
            return var1.getContentDescription();
         }
      }

      return null;
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
      CharSequence var1;
      if (this.indicatorViewController.isErrorEnabled()) {
         var1 = this.indicatorViewController.getErrorText();
      } else {
         var1 = null;
      }

      return var1;
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
      CharSequence var1;
      if (this.indicatorViewController.isHelperTextEnabled()) {
         var1 = this.indicatorViewController.getHelperText();
      } else {
         var1 = null;
      }

      return var1;
   }

   public int getHelperTextCurrentTextColor() {
      return this.indicatorViewController.getHelperTextViewCurrentTextColor();
   }

   public CharSequence getHint() {
      CharSequence var1;
      if (this.hintEnabled) {
         var1 = this.hint;
      } else {
         var1 = null;
      }

      return var1;
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
      CharSequence var1;
      if (this.placeholderEnabled) {
         var1 = this.placeholderText;
      } else {
         var1 = null;
      }

      return var1;
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
      boolean var1;
      if (this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
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
      int var1 = this.endIconMode;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public boolean isProvidingHint() {
      return this.isProvidingHint;
   }

   public boolean isStartIconCheckable() {
      return this.startIconView.isCheckable();
   }

   public boolean isStartIconVisible() {
      boolean var1;
      if (this.startIconView.getVisibility() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      EditText var6 = this.editText;
      if (var6 != null) {
         Rect var7 = this.tmpRect;
         DescendantOffsetUtils.getDescendantRect(this, var6, var7);
         this.updateBoxUnderlineBounds(var7);
         if (this.hintEnabled) {
            this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
            var2 = this.editText.getGravity();
            this.collapsingTextHelper.setCollapsedTextGravity(var2 & -113 | 48);
            this.collapsingTextHelper.setExpandedTextGravity(var2);
            this.collapsingTextHelper.setCollapsedBounds(this.calculateCollapsedTextBounds(var7));
            this.collapsingTextHelper.setExpandedBounds(this.calculateExpandedTextBounds(var7));
            this.collapsingTextHelper.recalculate();
            if (this.cutoutEnabled() && !this.hintExpanded) {
               this.openCutout();
            }
         }
      }

   }

   protected void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      boolean var3 = this.updateEditTextHeightBasedOnIcon();
      boolean var4 = this.updateDummyDrawables();
      if (var3 || var4) {
         this.editText.post(new Runnable() {
            public void run() {
               TextInputLayout.this.editText.requestLayout();
            }
         });
      }

      this.updatePlaceholderMeasurementsBasedOnEditText();
      this.updatePrefixTextViewPadding();
      this.updateSuffixTextViewPadding();
   }

   protected void onRestoreInstanceState(Parcelable var1) {
      if (!(var1 instanceof TextInputLayout.SavedState)) {
         super.onRestoreInstanceState(var1);
      } else {
         TextInputLayout.SavedState var2 = (TextInputLayout.SavedState)var1;
         super.onRestoreInstanceState(var2.getSuperState());
         this.setError(var2.error);
         if (var2.isEndIconChecked) {
            this.endIconView.post(new Runnable() {
               public void run() {
                  TextInputLayout.this.endIconView.performClick();
                  TextInputLayout.this.endIconView.jumpDrawablesToCurrentState();
               }
            });
         }

         this.requestLayout();
      }
   }

   public Parcelable onSaveInstanceState() {
      TextInputLayout.SavedState var1 = new TextInputLayout.SavedState(super.onSaveInstanceState());
      if (this.indicatorViewController.errorShouldBeShown()) {
         var1.error = this.getError();
      }

      boolean var2;
      if (this.hasEndIcon() && this.endIconView.isChecked()) {
         var2 = true;
      } else {
         var2 = false;
      }

      var1.isEndIconChecked = var2;
      return var1;
   }

   @Deprecated
   public void passwordVisibilityToggleRequested(boolean var1) {
      if (this.endIconMode == 1) {
         this.endIconView.performClick();
         if (var1) {
            this.endIconView.jumpDrawablesToCurrentState();
         }
      }

   }

   public void removeOnEditTextAttachedListener(TextInputLayout.OnEditTextAttachedListener var1) {
      this.editTextAttachedListeners.remove(var1);
   }

   public void removeOnEndIconChangedListener(TextInputLayout.OnEndIconChangedListener var1) {
      this.endIconChangedListeners.remove(var1);
   }

   public void setBoxBackgroundColor(int var1) {
      if (this.boxBackgroundColor != var1) {
         this.boxBackgroundColor = var1;
         this.defaultFilledBackgroundColor = var1;
         this.focusedFilledBackgroundColor = var1;
         this.hoveredFilledBackgroundColor = var1;
         this.applyBoxAttributes();
      }

   }

   public void setBoxBackgroundColorResource(int var1) {
      this.setBoxBackgroundColor(ContextCompat.getColor(this.getContext(), var1));
   }

   public void setBoxBackgroundColorStateList(ColorStateList var1) {
      int var2 = var1.getDefaultColor();
      this.defaultFilledBackgroundColor = var2;
      this.boxBackgroundColor = var2;
      this.disabledFilledBackgroundColor = var1.getColorForState(new int[]{-16842910}, -1);
      this.focusedFilledBackgroundColor = var1.getColorForState(new int[]{16842908, 16842910}, -1);
      this.hoveredFilledBackgroundColor = var1.getColorForState(new int[]{16843623, 16842910}, -1);
      this.applyBoxAttributes();
   }

   public void setBoxBackgroundMode(int var1) {
      if (var1 != this.boxBackgroundMode) {
         this.boxBackgroundMode = var1;
         if (this.editText != null) {
            this.onApplyBoxBackgroundMode();
         }

      }
   }

   public void setBoxCornerRadii(float var1, float var2, float var3, float var4) {
      MaterialShapeDrawable var5 = this.boxBackground;
      if (var5 == null || var5.getTopLeftCornerResolvedSize() != var1 || this.boxBackground.getTopRightCornerResolvedSize() != var2 || this.boxBackground.getBottomRightCornerResolvedSize() != var4 || this.boxBackground.getBottomLeftCornerResolvedSize() != var3) {
         this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setTopLeftCornerSize(var1).setTopRightCornerSize(var2).setBottomRightCornerSize(var4).setBottomLeftCornerSize(var3).build();
         this.applyBoxAttributes();
      }

   }

   public void setBoxCornerRadiiResources(int var1, int var2, int var3, int var4) {
      this.setBoxCornerRadii(this.getContext().getResources().getDimension(var1), this.getContext().getResources().getDimension(var2), this.getContext().getResources().getDimension(var4), this.getContext().getResources().getDimension(var3));
   }

   public void setBoxStrokeColor(int var1) {
      if (this.focusedStrokeColor != var1) {
         this.focusedStrokeColor = var1;
         this.updateTextInputBoxState();
      }

   }

   public void setBoxStrokeColorStateList(ColorStateList var1) {
      if (var1.isStateful()) {
         this.defaultStrokeColor = var1.getDefaultColor();
         this.disabledColor = var1.getColorForState(new int[]{-16842910}, -1);
         this.hoveredStrokeColor = var1.getColorForState(new int[]{16843623, 16842910}, -1);
         this.focusedStrokeColor = var1.getColorForState(new int[]{16842908, 16842910}, -1);
      } else if (this.focusedStrokeColor != var1.getDefaultColor()) {
         this.focusedStrokeColor = var1.getDefaultColor();
      }

      this.updateTextInputBoxState();
   }

   public void setBoxStrokeErrorColor(ColorStateList var1) {
      if (this.strokeErrorColor != var1) {
         this.strokeErrorColor = var1;
         this.updateTextInputBoxState();
      }

   }

   public void setBoxStrokeWidth(int var1) {
      this.boxStrokeWidthDefaultPx = var1;
      this.updateTextInputBoxState();
   }

   public void setBoxStrokeWidthFocused(int var1) {
      this.boxStrokeWidthFocusedPx = var1;
      this.updateTextInputBoxState();
   }

   public void setBoxStrokeWidthFocusedResource(int var1) {
      this.setBoxStrokeWidthFocused(this.getResources().getDimensionPixelSize(var1));
   }

   public void setBoxStrokeWidthResource(int var1) {
      this.setBoxStrokeWidth(this.getResources().getDimensionPixelSize(var1));
   }

   public void setCounterEnabled(boolean var1) {
      if (this.counterEnabled != var1) {
         if (var1) {
            AppCompatTextView var2 = new AppCompatTextView(this.getContext());
            this.counterView = var2;
            var2.setId(R.id.textinput_counter);
            Typeface var3 = this.typeface;
            if (var3 != null) {
               this.counterView.setTypeface(var3);
            }

            this.counterView.setMaxLines(1);
            this.indicatorViewController.addIndicator(this.counterView, 2);
            MarginLayoutParamsCompat.setMarginStart((MarginLayoutParams)this.counterView.getLayoutParams(), this.getResources().getDimensionPixelOffset(R.dimen.mtrl_textinput_counter_margin_start));
            this.updateCounterTextAppearanceAndColor();
            this.updateCounter();
         } else {
            this.indicatorViewController.removeIndicator(this.counterView, 2);
            this.counterView = null;
         }

         this.counterEnabled = var1;
      }

   }

   public void setCounterMaxLength(int var1) {
      if (this.counterMaxLength != var1) {
         if (var1 > 0) {
            this.counterMaxLength = var1;
         } else {
            this.counterMaxLength = -1;
         }

         if (this.counterEnabled) {
            this.updateCounter();
         }
      }

   }

   public void setCounterOverflowTextAppearance(int var1) {
      if (this.counterOverflowTextAppearance != var1) {
         this.counterOverflowTextAppearance = var1;
         this.updateCounterTextAppearanceAndColor();
      }

   }

   public void setCounterOverflowTextColor(ColorStateList var1) {
      if (this.counterOverflowTextColor != var1) {
         this.counterOverflowTextColor = var1;
         this.updateCounterTextAppearanceAndColor();
      }

   }

   public void setCounterTextAppearance(int var1) {
      if (this.counterTextAppearance != var1) {
         this.counterTextAppearance = var1;
         this.updateCounterTextAppearanceAndColor();
      }

   }

   public void setCounterTextColor(ColorStateList var1) {
      if (this.counterTextColor != var1) {
         this.counterTextColor = var1;
         this.updateCounterTextAppearanceAndColor();
      }

   }

   public void setDefaultHintTextColor(ColorStateList var1) {
      this.defaultHintTextColor = var1;
      this.focusedTextColor = var1;
      if (this.editText != null) {
         this.updateLabelState(false);
      }

   }

   public void setEnabled(boolean var1) {
      recursiveSetEnabled(this, var1);
      super.setEnabled(var1);
   }

   public void setEndIconActivated(boolean var1) {
      this.endIconView.setActivated(var1);
   }

   public void setEndIconCheckable(boolean var1) {
      this.endIconView.setCheckable(var1);
   }

   public void setEndIconContentDescription(int var1) {
      CharSequence var2;
      if (var1 != 0) {
         var2 = this.getResources().getText(var1);
      } else {
         var2 = null;
      }

      this.setEndIconContentDescription(var2);
   }

   public void setEndIconContentDescription(CharSequence var1) {
      if (this.getEndIconContentDescription() != var1) {
         this.endIconView.setContentDescription(var1);
      }

   }

   public void setEndIconDrawable(int var1) {
      Drawable var2;
      if (var1 != 0) {
         var2 = AppCompatResources.getDrawable(this.getContext(), var1);
      } else {
         var2 = null;
      }

      this.setEndIconDrawable(var2);
   }

   public void setEndIconDrawable(Drawable var1) {
      this.endIconView.setImageDrawable(var1);
   }

   public void setEndIconMode(int var1) {
      int var2 = this.endIconMode;
      this.endIconMode = var1;
      this.dispatchOnEndIconChanged(var2);
      boolean var3;
      if (var1 != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.setEndIconVisible(var3);
      if (this.getEndIconDelegate().isBoxBackgroundModeSupported(this.boxBackgroundMode)) {
         this.getEndIconDelegate().initialize();
         this.applyEndIconTint();
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("The current box background mode ");
         var4.append(this.boxBackgroundMode);
         var4.append(" is not supported by the end icon mode ");
         var4.append(var1);
         throw new IllegalStateException(var4.toString());
      }
   }

   public void setEndIconOnClickListener(OnClickListener var1) {
      setIconOnClickListener(this.endIconView, var1, this.endIconOnLongClickListener);
   }

   public void setEndIconOnLongClickListener(OnLongClickListener var1) {
      this.endIconOnLongClickListener = var1;
      setIconOnLongClickListener(this.endIconView, var1);
   }

   public void setEndIconTintList(ColorStateList var1) {
      if (this.endIconTintList != var1) {
         this.endIconTintList = var1;
         this.hasEndIconTintList = true;
         this.applyEndIconTint();
      }

   }

   public void setEndIconTintMode(Mode var1) {
      if (this.endIconTintMode != var1) {
         this.endIconTintMode = var1;
         this.hasEndIconTintMode = true;
         this.applyEndIconTint();
      }

   }

   public void setEndIconVisible(boolean var1) {
      if (this.isEndIconVisible() != var1) {
         CheckableImageButton var2 = this.endIconView;
         byte var3;
         if (var1) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         var2.setVisibility(var3);
         this.updateSuffixTextViewPadding();
         this.updateDummyDrawables();
      }

   }

   public void setError(CharSequence var1) {
      if (!this.indicatorViewController.isErrorEnabled()) {
         if (TextUtils.isEmpty(var1)) {
            return;
         }

         this.setErrorEnabled(true);
      }

      if (!TextUtils.isEmpty(var1)) {
         this.indicatorViewController.showError(var1);
      } else {
         this.indicatorViewController.hideError();
      }

   }

   public void setErrorContentDescription(CharSequence var1) {
      this.indicatorViewController.setErrorContentDescription(var1);
   }

   public void setErrorEnabled(boolean var1) {
      this.indicatorViewController.setErrorEnabled(var1);
   }

   public void setErrorIconDrawable(int var1) {
      Drawable var2;
      if (var1 != 0) {
         var2 = AppCompatResources.getDrawable(this.getContext(), var1);
      } else {
         var2 = null;
      }

      this.setErrorIconDrawable(var2);
   }

   public void setErrorIconDrawable(Drawable var1) {
      this.errorIconView.setImageDrawable(var1);
      boolean var2;
      if (var1 != null && this.indicatorViewController.isErrorEnabled()) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.setErrorIconVisible(var2);
   }

   public void setErrorIconOnClickListener(OnClickListener var1) {
      setIconOnClickListener(this.errorIconView, var1, this.errorIconOnLongClickListener);
   }

   public void setErrorIconOnLongClickListener(OnLongClickListener var1) {
      this.errorIconOnLongClickListener = var1;
      setIconOnLongClickListener(this.errorIconView, var1);
   }

   public void setErrorIconTintList(ColorStateList var1) {
      this.errorIconTintList = var1;
      Drawable var2 = this.errorIconView.getDrawable();
      Drawable var3 = var2;
      if (var2 != null) {
         var3 = DrawableCompat.wrap(var2).mutate();
         DrawableCompat.setTintList(var3, var1);
      }

      if (this.errorIconView.getDrawable() != var3) {
         this.errorIconView.setImageDrawable(var3);
      }

   }

   public void setErrorIconTintMode(Mode var1) {
      Drawable var2 = this.errorIconView.getDrawable();
      Drawable var3 = var2;
      if (var2 != null) {
         var3 = DrawableCompat.wrap(var2).mutate();
         DrawableCompat.setTintMode(var3, var1);
      }

      if (this.errorIconView.getDrawable() != var3) {
         this.errorIconView.setImageDrawable(var3);
      }

   }

   public void setErrorTextAppearance(int var1) {
      this.indicatorViewController.setErrorTextAppearance(var1);
   }

   public void setErrorTextColor(ColorStateList var1) {
      this.indicatorViewController.setErrorViewTextColor(var1);
   }

   public void setHelperText(CharSequence var1) {
      if (TextUtils.isEmpty(var1)) {
         if (this.isHelperTextEnabled()) {
            this.setHelperTextEnabled(false);
         }
      } else {
         if (!this.isHelperTextEnabled()) {
            this.setHelperTextEnabled(true);
         }

         this.indicatorViewController.showHelper(var1);
      }

   }

   public void setHelperTextColor(ColorStateList var1) {
      this.indicatorViewController.setHelperTextViewTextColor(var1);
   }

   public void setHelperTextEnabled(boolean var1) {
      this.indicatorViewController.setHelperTextEnabled(var1);
   }

   public void setHelperTextTextAppearance(int var1) {
      this.indicatorViewController.setHelperTextAppearance(var1);
   }

   public void setHint(CharSequence var1) {
      if (this.hintEnabled) {
         this.setHintInternal(var1);
         this.sendAccessibilityEvent(2048);
      }

   }

   public void setHintAnimationEnabled(boolean var1) {
      this.hintAnimationEnabled = var1;
   }

   public void setHintEnabled(boolean var1) {
      if (var1 != this.hintEnabled) {
         this.hintEnabled = var1;
         if (!var1) {
            this.isProvidingHint = false;
            if (!TextUtils.isEmpty(this.hint) && TextUtils.isEmpty(this.editText.getHint())) {
               this.editText.setHint(this.hint);
            }

            this.setHintInternal((CharSequence)null);
         } else {
            CharSequence var2 = this.editText.getHint();
            if (!TextUtils.isEmpty(var2)) {
               if (TextUtils.isEmpty(this.hint)) {
                  this.setHint(var2);
               }

               this.editText.setHint((CharSequence)null);
            }

            this.isProvidingHint = true;
         }

         if (this.editText != null) {
            this.updateInputLayoutMargins();
         }
      }

   }

   public void setHintTextAppearance(int var1) {
      this.collapsingTextHelper.setCollapsedTextAppearance(var1);
      this.focusedTextColor = this.collapsingTextHelper.getCollapsedTextColor();
      if (this.editText != null) {
         this.updateLabelState(false);
         this.updateInputLayoutMargins();
      }

   }

   public void setHintTextColor(ColorStateList var1) {
      if (this.focusedTextColor != var1) {
         if (this.defaultHintTextColor == null) {
            this.collapsingTextHelper.setCollapsedTextColor(var1);
         }

         this.focusedTextColor = var1;
         if (this.editText != null) {
            this.updateLabelState(false);
         }
      }

   }

   @Deprecated
   public void setPasswordVisibilityToggleContentDescription(int var1) {
      CharSequence var2;
      if (var1 != 0) {
         var2 = this.getResources().getText(var1);
      } else {
         var2 = null;
      }

      this.setPasswordVisibilityToggleContentDescription(var2);
   }

   @Deprecated
   public void setPasswordVisibilityToggleContentDescription(CharSequence var1) {
      this.endIconView.setContentDescription(var1);
   }

   @Deprecated
   public void setPasswordVisibilityToggleDrawable(int var1) {
      Drawable var2;
      if (var1 != 0) {
         var2 = AppCompatResources.getDrawable(this.getContext(), var1);
      } else {
         var2 = null;
      }

      this.setPasswordVisibilityToggleDrawable(var2);
   }

   @Deprecated
   public void setPasswordVisibilityToggleDrawable(Drawable var1) {
      this.endIconView.setImageDrawable(var1);
   }

   @Deprecated
   public void setPasswordVisibilityToggleEnabled(boolean var1) {
      if (var1 && this.endIconMode != 1) {
         this.setEndIconMode(1);
      } else if (!var1) {
         this.setEndIconMode(0);
      }

   }

   @Deprecated
   public void setPasswordVisibilityToggleTintList(ColorStateList var1) {
      this.endIconTintList = var1;
      this.hasEndIconTintList = true;
      this.applyEndIconTint();
   }

   @Deprecated
   public void setPasswordVisibilityToggleTintMode(Mode var1) {
      this.endIconTintMode = var1;
      this.hasEndIconTintMode = true;
      this.applyEndIconTint();
   }

   public void setPlaceholderText(CharSequence var1) {
      if (this.placeholderEnabled && TextUtils.isEmpty(var1)) {
         this.setPlaceholderTextEnabled(false);
      } else {
         if (!this.placeholderEnabled) {
            this.setPlaceholderTextEnabled(true);
         }

         this.placeholderText = var1;
      }

      this.updatePlaceholderText();
   }

   public void setPlaceholderTextAppearance(int var1) {
      this.placeholderTextAppearance = var1;
      TextView var2 = this.placeholderTextView;
      if (var2 != null) {
         TextViewCompat.setTextAppearance(var2, var1);
      }

   }

   public void setPlaceholderTextColor(ColorStateList var1) {
      if (this.placeholderTextColor != var1) {
         this.placeholderTextColor = var1;
         TextView var2 = this.placeholderTextView;
         if (var2 != null && var1 != null) {
            var2.setTextColor(var1);
         }
      }

   }

   public void setPrefixText(CharSequence var1) {
      CharSequence var2;
      if (TextUtils.isEmpty(var1)) {
         var2 = null;
      } else {
         var2 = var1;
      }

      this.prefixText = var2;
      this.prefixTextView.setText(var1);
      this.updatePrefixTextVisibility();
   }

   public void setPrefixTextAppearance(int var1) {
      TextViewCompat.setTextAppearance(this.prefixTextView, var1);
   }

   public void setPrefixTextColor(ColorStateList var1) {
      this.prefixTextView.setTextColor(var1);
   }

   public void setStartIconCheckable(boolean var1) {
      this.startIconView.setCheckable(var1);
   }

   public void setStartIconContentDescription(int var1) {
      CharSequence var2;
      if (var1 != 0) {
         var2 = this.getResources().getText(var1);
      } else {
         var2 = null;
      }

      this.setStartIconContentDescription(var2);
   }

   public void setStartIconContentDescription(CharSequence var1) {
      if (this.getStartIconContentDescription() != var1) {
         this.startIconView.setContentDescription(var1);
      }

   }

   public void setStartIconDrawable(int var1) {
      Drawable var2;
      if (var1 != 0) {
         var2 = AppCompatResources.getDrawable(this.getContext(), var1);
      } else {
         var2 = null;
      }

      this.setStartIconDrawable(var2);
   }

   public void setStartIconDrawable(Drawable var1) {
      this.startIconView.setImageDrawable(var1);
      if (var1 != null) {
         this.setStartIconVisible(true);
         this.applyStartIconTint();
      } else {
         this.setStartIconVisible(false);
         this.setStartIconOnClickListener((OnClickListener)null);
         this.setStartIconOnLongClickListener((OnLongClickListener)null);
         this.setStartIconContentDescription((CharSequence)null);
      }

   }

   public void setStartIconOnClickListener(OnClickListener var1) {
      setIconOnClickListener(this.startIconView, var1, this.startIconOnLongClickListener);
   }

   public void setStartIconOnLongClickListener(OnLongClickListener var1) {
      this.startIconOnLongClickListener = var1;
      setIconOnLongClickListener(this.startIconView, var1);
   }

   public void setStartIconTintList(ColorStateList var1) {
      if (this.startIconTintList != var1) {
         this.startIconTintList = var1;
         this.hasStartIconTintList = true;
         this.applyStartIconTint();
      }

   }

   public void setStartIconTintMode(Mode var1) {
      if (this.startIconTintMode != var1) {
         this.startIconTintMode = var1;
         this.hasStartIconTintMode = true;
         this.applyStartIconTint();
      }

   }

   public void setStartIconVisible(boolean var1) {
      if (this.isStartIconVisible() != var1) {
         CheckableImageButton var2 = this.startIconView;
         byte var3;
         if (var1) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         var2.setVisibility(var3);
         this.updatePrefixTextViewPadding();
         this.updateDummyDrawables();
      }

   }

   public void setSuffixText(CharSequence var1) {
      CharSequence var2;
      if (TextUtils.isEmpty(var1)) {
         var2 = null;
      } else {
         var2 = var1;
      }

      this.suffixText = var2;
      this.suffixTextView.setText(var1);
      this.updateSuffixTextVisibility();
   }

   public void setSuffixTextAppearance(int var1) {
      TextViewCompat.setTextAppearance(this.suffixTextView, var1);
   }

   public void setSuffixTextColor(ColorStateList var1) {
      this.suffixTextView.setTextColor(var1);
   }

   void setTextAppearanceCompatWithErrorFallback(TextView var1, int var2) {
      boolean var3 = true;

      boolean var6;
      label25: {
         label24: {
            try {
               TextViewCompat.setTextAppearance(var1, var2);
               if (VERSION.SDK_INT < 23) {
                  break label24;
               }

               var2 = var1.getTextColors().getDefaultColor();
            } catch (Exception var5) {
               var6 = var3;
               break label25;
            }

            if (var2 == -65281) {
               var6 = var3;
               break label25;
            }
         }

         var6 = false;
      }

      if (var6) {
         TextViewCompat.setTextAppearance(var1, R.style.TextAppearance_AppCompat_Caption);
         var1.setTextColor(ContextCompat.getColor(this.getContext(), R.color.design_error));
      }

   }

   public void setTextInputAccessibilityDelegate(TextInputLayout.AccessibilityDelegate var1) {
      EditText var2 = this.editText;
      if (var2 != null) {
         ViewCompat.setAccessibilityDelegate(var2, var1);
      }

   }

   public void setTypeface(Typeface var1) {
      if (var1 != this.typeface) {
         this.typeface = var1;
         this.collapsingTextHelper.setTypefaces(var1);
         this.indicatorViewController.setTypefaces(var1);
         TextView var2 = this.counterView;
         if (var2 != null) {
            var2.setTypeface(var1);
         }
      }

   }

   void updateCounter(int var1) {
      boolean var2 = this.counterOverflowed;
      int var3 = this.counterMaxLength;
      if (var3 == -1) {
         this.counterView.setText(String.valueOf(var1));
         this.counterView.setContentDescription((CharSequence)null);
         this.counterOverflowed = false;
      } else {
         boolean var4;
         if (var1 > var3) {
            var4 = true;
         } else {
            var4 = false;
         }

         this.counterOverflowed = var4;
         updateCounterContentDescription(this.getContext(), this.counterView, var1, this.counterMaxLength, this.counterOverflowed);
         if (var2 != this.counterOverflowed) {
            this.updateCounterTextAppearanceAndColor();
         }

         BidiFormatter var5 = BidiFormatter.getInstance();
         this.counterView.setText(var5.unicodeWrap(this.getContext().getString(R.string.character_counter_pattern, new Object[]{var1, this.counterMaxLength})));
      }

      if (this.editText != null && var2 != this.counterOverflowed) {
         this.updateLabelState(false);
         this.updateTextInputBoxState();
         this.updateEditTextBackground();
      }

   }

   void updateEditTextBackground() {
      EditText var1 = this.editText;
      if (var1 != null && this.boxBackgroundMode == 0) {
         Drawable var2 = var1.getBackground();
         if (var2 == null) {
            return;
         }

         Drawable var3 = var2;
         if (DrawableUtils.canSafelyMutateDrawable(var2)) {
            var3 = var2.mutate();
         }

         if (this.indicatorViewController.errorShouldBeShown()) {
            var3.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(this.indicatorViewController.getErrorViewCurrentTextColor(), Mode.SRC_IN));
         } else {
            if (this.counterOverflowed) {
               TextView var4 = this.counterView;
               if (var4 != null) {
                  var3.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(var4.getCurrentTextColor(), Mode.SRC_IN));
                  return;
               }
            }

            DrawableCompat.clearColorFilter(var3);
            this.editText.refreshDrawableState();
         }
      }

   }

   void updateLabelState(boolean var1) {
      this.updateLabelState(var1, false);
   }

   void updateTextInputBoxState() {
      if (this.boxBackground != null && this.boxBackgroundMode != 0) {
         boolean var1;
         boolean var2;
         EditText var3;
         label105: {
            var1 = this.isFocused();
            var2 = false;
            if (!var1) {
               var3 = this.editText;
               if (var3 == null || !var3.hasFocus()) {
                  var1 = false;
                  break label105;
               }
            }

            var1 = true;
         }

         boolean var4;
         label96: {
            if (!this.isHovered()) {
               var3 = this.editText;
               if (var3 == null || !var3.isHovered()) {
                  var4 = false;
                  break label96;
               }
            }

            var4 = true;
         }

         if (!this.isEnabled()) {
            this.boxStrokeColor = this.disabledColor;
         } else if (this.indicatorViewController.errorShouldBeShown()) {
            if (this.strokeErrorColor != null) {
               this.updateStrokeErrorColor(var1, var4);
            } else {
               this.boxStrokeColor = this.indicatorViewController.getErrorViewCurrentTextColor();
            }
         } else {
            label115: {
               if (this.counterOverflowed) {
                  TextView var6 = this.counterView;
                  if (var6 != null) {
                     if (this.strokeErrorColor != null) {
                        this.updateStrokeErrorColor(var1, var4);
                     } else {
                        this.boxStrokeColor = var6.getCurrentTextColor();
                     }
                     break label115;
                  }
               }

               if (var1) {
                  this.boxStrokeColor = this.focusedStrokeColor;
               } else if (var4) {
                  this.boxStrokeColor = this.hoveredStrokeColor;
               } else {
                  this.boxStrokeColor = this.defaultStrokeColor;
               }
            }
         }

         boolean var5 = var2;
         if (this.getErrorIconDrawable() != null) {
            var5 = var2;
            if (this.indicatorViewController.isErrorEnabled()) {
               var5 = var2;
               if (this.indicatorViewController.errorShouldBeShown()) {
                  var5 = true;
               }
            }
         }

         this.setErrorIconVisible(var5);
         this.updateIconColorOnState(this.errorIconView, this.errorIconTintList);
         this.updateIconColorOnState(this.startIconView, this.startIconTintList);
         this.updateIconColorOnState(this.endIconView, this.endIconTintList);
         if (this.getEndIconDelegate().shouldTintIconOnError()) {
            this.tintEndIconOnError(this.indicatorViewController.errorShouldBeShown());
         }

         if (var1 && this.isEnabled()) {
            this.boxStrokeWidthPx = this.boxStrokeWidthFocusedPx;
         } else {
            this.boxStrokeWidthPx = this.boxStrokeWidthDefaultPx;
         }

         if (this.boxBackgroundMode == 1) {
            if (!this.isEnabled()) {
               this.boxBackgroundColor = this.disabledFilledBackgroundColor;
            } else if (var4 && !var1) {
               this.boxBackgroundColor = this.hoveredFilledBackgroundColor;
            } else if (var1) {
               this.boxBackgroundColor = this.focusedFilledBackgroundColor;
            } else {
               this.boxBackgroundColor = this.defaultFilledBackgroundColor;
            }
         }

         this.applyBoxAttributes();
      }

   }

   public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
      private final TextInputLayout layout;

      public AccessibilityDelegate(TextInputLayout var1) {
         this.layout = var1;
      }

      public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2) {
         super.onInitializeAccessibilityNodeInfo(var1, var2);
         EditText var14 = this.layout.getEditText();
         Editable var3;
         if (var14 != null) {
            var3 = var14.getText();
         } else {
            var3 = null;
         }

         CharSequence var15 = this.layout.getHint();
         CharSequence var4 = this.layout.getHelperText();
         CharSequence var5 = this.layout.getError();
         int var6 = this.layout.getCounterMaxLength();
         CharSequence var7 = this.layout.getCounterOverflowDescription();
         boolean var8 = TextUtils.isEmpty(var3) ^ true;
         boolean var9 = TextUtils.isEmpty(var15);
         boolean var10 = TextUtils.isEmpty(var4) ^ true;
         boolean var11 = TextUtils.isEmpty(var5) ^ true;
         boolean var12;
         if (!var11 && TextUtils.isEmpty(var7)) {
            var12 = false;
         } else {
            var12 = true;
         }

         String var16;
         if (var9 ^ true) {
            var16 = var15.toString();
         } else {
            var16 = "";
         }

         StringBuilder var13 = new StringBuilder();
         var13.append(var16);
         if ((var11 || var10) && !TextUtils.isEmpty(var16)) {
            var16 = ", ";
         } else {
            var16 = "";
         }

         var13.append(var16);
         var16 = var13.toString();
         var13 = new StringBuilder();
         var13.append(var16);
         Object var17;
         if (var11) {
            var17 = var5;
         } else if (var10) {
            var17 = var4;
         } else {
            var17 = "";
         }

         var13.append(var17);
         String var18 = var13.toString();
         if (var8) {
            var2.setText(var3);
         } else if (!TextUtils.isEmpty(var18)) {
            var2.setText(var18);
         }

         if (!TextUtils.isEmpty(var18)) {
            if (VERSION.SDK_INT >= 26) {
               var2.setHintText(var18);
            } else {
               var16 = var18;
               if (var8) {
                  StringBuilder var19 = new StringBuilder();
                  var19.append(var3);
                  var19.append(", ");
                  var19.append(var18);
                  var16 = var19.toString();
               }

               var2.setText(var16);
            }

            var2.setShowingHintText(var8 ^ true);
         }

         if (var3 == null || var3.length() != var6) {
            var6 = -1;
         }

         var2.setMaxTextLength(var6);
         if (var12) {
            if (!var11) {
               var5 = var7;
            }

            var2.setError(var5);
         }

      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface BoxBackgroundMode {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface EndIconMode {
   }

   public interface OnEditTextAttachedListener {
      void onEditTextAttached(TextInputLayout var1);
   }

   public interface OnEndIconChangedListener {
      void onEndIconChanged(TextInputLayout var1, int var2);
   }

   static class SavedState extends AbsSavedState {
      public static final Creator<TextInputLayout.SavedState> CREATOR = new ClassLoaderCreator<TextInputLayout.SavedState>() {
         public TextInputLayout.SavedState createFromParcel(Parcel var1) {
            return new TextInputLayout.SavedState(var1, (ClassLoader)null);
         }

         public TextInputLayout.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            return new TextInputLayout.SavedState(var1, var2);
         }

         public TextInputLayout.SavedState[] newArray(int var1) {
            return new TextInputLayout.SavedState[var1];
         }
      };
      CharSequence error;
      boolean isEndIconChecked;

      SavedState(Parcel var1, ClassLoader var2) {
         super(var1, var2);
         this.error = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(var1);
         int var3 = var1.readInt();
         boolean var4 = true;
         if (var3 != 1) {
            var4 = false;
         }

         this.isEndIconChecked = var4;
      }

      SavedState(Parcelable var1) {
         super(var1);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("TextInputLayout.SavedState{");
         var1.append(Integer.toHexString(System.identityHashCode(this)));
         var1.append(" error=");
         var1.append(this.error);
         var1.append("}");
         return var1.toString();
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         TextUtils.writeToParcel(this.error, var1, var2);
         var1.writeInt(this.isEndIconChecked);
      }
   }
}
