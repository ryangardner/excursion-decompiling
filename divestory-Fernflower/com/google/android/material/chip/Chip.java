package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView.BufferType;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable {
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
   private final TextAppearanceFontCallback fontCallback;
   private InsetDrawable insetBackgroundDrawable;
   private int lastLayoutDirection;
   private int minTouchTargetSize;
   private OnCheckedChangeListener onCheckedChangeListenerInternal;
   private OnClickListener onCloseIconClickListener;
   private final Rect rect;
   private final RectF rectF;
   private RippleDrawable ripple;
   private final Chip.ChipTouchHelper touchHelper;

   static {
      DEF_STYLE_RES = R.style.Widget_MaterialComponents_Chip_Action;
      EMPTY_BOUNDS = new Rect();
      SELECTED_STATE = new int[]{16842913};
      CHECKABLE_STATE_SET = new int[]{16842911};
   }

   public Chip(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public Chip(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.chipStyle);
   }

   public Chip(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.rect = new Rect();
      this.rectF = new RectF();
      this.fontCallback = new TextAppearanceFontCallback() {
         public void onFontRetrievalFailed(int var1) {
         }

         public void onFontRetrieved(Typeface var1, boolean var2) {
            Chip var3 = Chip.this;
            CharSequence var4;
            if (var3.chipDrawable.shouldDrawText()) {
               var4 = Chip.this.chipDrawable.getText();
            } else {
               var4 = Chip.this.getText();
            }

            var3.setText(var4);
            Chip.this.requestLayout();
            Chip.this.invalidate();
         }
      };
      Context var4 = this.getContext();
      this.validateAttributes(var2);
      ChipDrawable var6 = ChipDrawable.createFromAttributes(var4, var2, var3, DEF_STYLE_RES);
      this.initMinTouchTarget(var4, var2, var3);
      this.setChipDrawable(var6);
      var6.setElevation(ViewCompat.getElevation(this));
      TypedArray var7 = ThemeEnforcement.obtainStyledAttributes(var4, var2, R.styleable.Chip, var3, DEF_STYLE_RES);
      if (VERSION.SDK_INT < 23) {
         this.setTextColor(MaterialResources.getColorStateList(var4, var7, R.styleable.Chip_android_textColor));
      }

      boolean var5 = var7.hasValue(R.styleable.Chip_shapeAppearance);
      var7.recycle();
      this.touchHelper = new Chip.ChipTouchHelper(this);
      this.updateAccessibilityDelegate();
      if (!var5) {
         this.initOutlineProvider();
      }

      this.setChecked(this.deferredCheckedValue);
      this.setText(var6.getText());
      this.setEllipsize(var6.getEllipsize());
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

      this.lastLayoutDirection = ViewCompat.getLayoutDirection(this);
   }

   private void applyChipDrawable(ChipDrawable var1) {
      var1.setDelegate(this);
   }

   private int[] createCloseIconDrawableState() {
      boolean var1 = this.isEnabled();
      byte var2 = 0;
      byte var3;
      if (var1) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var4 = var3;
      if (this.closeIconFocused) {
         var4 = var3 + 1;
      }

      int var6 = var4;
      if (this.closeIconHovered) {
         var6 = var4 + 1;
      }

      var4 = var6;
      if (this.closeIconPressed) {
         var4 = var6 + 1;
      }

      var6 = var4;
      if (this.isChecked()) {
         var6 = var4 + 1;
      }

      int[] var5 = new int[var6];
      var3 = var2;
      if (this.isEnabled()) {
         var5[0] = 16842910;
         var3 = 1;
      }

      var4 = var3;
      if (this.closeIconFocused) {
         var5[var3] = 16842908;
         var4 = var3 + 1;
      }

      var6 = var4;
      if (this.closeIconHovered) {
         var5[var4] = 16843623;
         var6 = var4 + 1;
      }

      var4 = var6;
      if (this.closeIconPressed) {
         var5[var6] = 16842919;
         var4 = var6 + 1;
      }

      if (this.isChecked()) {
         var5[var4] = 16842913;
      }

      return var5;
   }

   private void ensureChipDrawableHasCallback() {
      if (this.getBackgroundDrawable() == this.insetBackgroundDrawable && this.chipDrawable.getCallback() == null) {
         this.chipDrawable.setCallback(this.insetBackgroundDrawable);
      }

   }

   private RectF getCloseIconTouchBounds() {
      this.rectF.setEmpty();
      if (this.hasCloseIcon()) {
         this.chipDrawable.getCloseIconTouchBounds(this.rectF);
      }

      return this.rectF;
   }

   private Rect getCloseIconTouchBoundsInt() {
      RectF var1 = this.getCloseIconTouchBounds();
      this.rect.set((int)var1.left, (int)var1.top, (int)var1.right, (int)var1.bottom);
      return this.rect;
   }

   private TextAppearance getTextAppearance() {
      ChipDrawable var1 = this.chipDrawable;
      TextAppearance var2;
      if (var1 != null) {
         var2 = var1.getTextAppearance();
      } else {
         var2 = null;
      }

      return var2;
   }

   private boolean handleAccessibilityExit(MotionEvent var1) {
      if (var1.getAction() == 10) {
         try {
            Field var6 = ExploreByTouchHelper.class.getDeclaredField("mHoveredVirtualViewId");
            var6.setAccessible(true);
            if ((Integer)var6.get(this.touchHelper) != Integer.MIN_VALUE) {
               Method var7 = ExploreByTouchHelper.class.getDeclaredMethod("updateHoveredVirtualView", Integer.TYPE);
               var7.setAccessible(true);
               var7.invoke(this.touchHelper, Integer.MIN_VALUE);
               return true;
            }
         } catch (NoSuchMethodException var2) {
            Log.e("Chip", "Unable to send Accessibility Exit event", var2);
         } catch (IllegalAccessException var3) {
            Log.e("Chip", "Unable to send Accessibility Exit event", var3);
         } catch (InvocationTargetException var4) {
            Log.e("Chip", "Unable to send Accessibility Exit event", var4);
         } catch (NoSuchFieldException var5) {
            Log.e("Chip", "Unable to send Accessibility Exit event", var5);
         }
      }

      return false;
   }

   private boolean hasCloseIcon() {
      ChipDrawable var1 = this.chipDrawable;
      boolean var2;
      if (var1 != null && var1.getCloseIcon() != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void initMinTouchTarget(Context var1, AttributeSet var2, int var3) {
      TypedArray var5 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.Chip, var3, DEF_STYLE_RES);
      this.ensureMinTouchTargetSize = var5.getBoolean(R.styleable.Chip_ensureMinTouchTargetSize, false);
      float var4 = (float)Math.ceil((double)ViewUtils.dpToPx(this.getContext(), 48));
      this.minTouchTargetSize = (int)Math.ceil((double)var5.getDimension(R.styleable.Chip_chipMinTouchTargetSize, var4));
      var5.recycle();
   }

   private void initOutlineProvider() {
      if (VERSION.SDK_INT >= 21) {
         this.setOutlineProvider(new ViewOutlineProvider() {
            public void getOutline(View var1, Outline var2) {
               if (Chip.this.chipDrawable != null) {
                  Chip.this.chipDrawable.getOutline(var2);
               } else {
                  var2.setAlpha(0.0F);
               }

            }
         });
      }

   }

   private void insetChipBackgroundDrawable(int var1, int var2, int var3, int var4) {
      this.insetBackgroundDrawable = new InsetDrawable(this.chipDrawable, var1, var2, var3, var4);
   }

   private void removeBackgroundInset() {
      if (this.insetBackgroundDrawable != null) {
         this.insetBackgroundDrawable = null;
         this.setMinWidth(0);
         this.setMinHeight((int)this.getChipMinHeight());
         this.updateBackgroundDrawable();
      }

   }

   private void setCloseIconHovered(boolean var1) {
      if (this.closeIconHovered != var1) {
         this.closeIconHovered = var1;
         this.refreshDrawableState();
      }

   }

   private void setCloseIconPressed(boolean var1) {
      if (this.closeIconPressed != var1) {
         this.closeIconPressed = var1;
         this.refreshDrawableState();
      }

   }

   private void unapplyChipDrawable(ChipDrawable var1) {
      if (var1 != null) {
         var1.setDelegate((ChipDrawable.Delegate)null);
      }

   }

   private void updateAccessibilityDelegate() {
      if (this.hasCloseIcon() && this.isCloseIconVisible() && this.onCloseIconClickListener != null) {
         ViewCompat.setAccessibilityDelegate(this, this.touchHelper);
      } else {
         ViewCompat.setAccessibilityDelegate(this, (AccessibilityDelegateCompat)null);
      }

   }

   private void updateBackgroundDrawable() {
      if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
         this.updateFrameworkRippleBackground();
      } else {
         this.chipDrawable.setUseCompatRipple(true);
         ViewCompat.setBackground(this, this.getBackgroundDrawable());
         this.updatePaddingInternal();
         this.ensureChipDrawableHasCallback();
      }

   }

   private void updateFrameworkRippleBackground() {
      this.ripple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.chipDrawable.getRippleColor()), this.getBackgroundDrawable(), (Drawable)null);
      this.chipDrawable.setUseCompatRipple(false);
      ViewCompat.setBackground(this, this.ripple);
      this.updatePaddingInternal();
   }

   private void updatePaddingInternal() {
      if (!TextUtils.isEmpty(this.getText())) {
         ChipDrawable var1 = this.chipDrawable;
         if (var1 != null) {
            int var2 = (int)(var1.getChipEndPadding() + this.chipDrawable.getTextEndPadding() + this.chipDrawable.calculateCloseIconWidth());
            int var3 = (int)(this.chipDrawable.getChipStartPadding() + this.chipDrawable.getTextStartPadding() + this.chipDrawable.calculateChipIconWidth());
            int var4 = var2;
            int var5 = var3;
            if (this.insetBackgroundDrawable != null) {
               Rect var6 = new Rect();
               this.insetBackgroundDrawable.getPadding(var6);
               var5 = var3 + var6.left;
               var4 = var2 + var6.right;
            }

            ViewCompat.setPaddingRelative(this, var5, this.getPaddingTop(), var4, this.getPaddingBottom());
         }
      }

   }

   private void updateTextPaintDrawState() {
      TextPaint var1 = this.getPaint();
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var1.drawableState = var2.getState();
      }

      TextAppearance var3 = this.getTextAppearance();
      if (var3 != null) {
         var3.updateDrawState(this.getContext(), var1, this.fontCallback);
      }

   }

   private void validateAttributes(AttributeSet var1) {
      if (var1 != null) {
         if (var1.getAttributeValue("http://schemas.android.com/apk/res/android", "background") != null) {
            Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
         }

         if (var1.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") == null) {
            if (var1.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") == null) {
               if (var1.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") == null) {
                  if (var1.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") == null) {
                     if (var1.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true) && var1.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) == 1 && var1.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) == 1 && var1.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) == 1) {
                        if (var1.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                           Log.w("Chip", "Chip text must be vertically center and start aligned");
                        }

                     } else {
                        throw new UnsupportedOperationException("Chip does not support multi-line text");
                     }
                  } else {
                     throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
                  }
               } else {
                  throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
               }
            } else {
               throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
            }
         } else {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
         }
      }
   }

   protected boolean dispatchHoverEvent(MotionEvent var1) {
      boolean var2;
      if (!this.handleAccessibilityExit(var1) && !this.touchHelper.dispatchHoverEvent(var1) && !super.dispatchHoverEvent(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      return this.touchHelper.dispatchKeyEvent(var1) && this.touchHelper.getKeyboardFocusedVirtualViewId() != Integer.MIN_VALUE ? true : super.dispatchKeyEvent(var1);
   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      ChipDrawable var1 = this.chipDrawable;
      boolean var2;
      if (var1 != null && var1.isCloseIconStateful()) {
         var2 = this.chipDrawable.setCloseIconState(this.createCloseIconDrawableState());
      } else {
         var2 = false;
      }

      if (var2) {
         this.invalidate();
      }

   }

   public boolean ensureAccessibleTouchTarget(int var1) {
      this.minTouchTargetSize = var1;
      boolean var2 = this.shouldEnsureMinTouchTargetSize();
      int var3 = 0;
      if (!var2) {
         if (this.insetBackgroundDrawable != null) {
            this.removeBackgroundInset();
         } else {
            this.updateBackgroundDrawable();
         }

         return false;
      } else {
         int var4 = Math.max(0, var1 - this.chipDrawable.getIntrinsicHeight());
         int var5 = Math.max(0, var1 - this.chipDrawable.getIntrinsicWidth());
         if (var5 <= 0 && var4 <= 0) {
            if (this.insetBackgroundDrawable != null) {
               this.removeBackgroundInset();
            } else {
               this.updateBackgroundDrawable();
            }

            return false;
         } else {
            if (var5 > 0) {
               var5 /= 2;
            } else {
               var5 = 0;
            }

            if (var4 > 0) {
               var3 = var4 / 2;
            }

            if (this.insetBackgroundDrawable != null) {
               Rect var6 = new Rect();
               this.insetBackgroundDrawable.getPadding(var6);
               if (var6.top == var3 && var6.bottom == var3 && var6.left == var5 && var6.right == var5) {
                  this.updateBackgroundDrawable();
                  return true;
               }
            }

            if (VERSION.SDK_INT >= 16) {
               if (this.getMinHeight() != var1) {
                  this.setMinHeight(var1);
               }

               if (this.getMinWidth() != var1) {
                  this.setMinWidth(var1);
               }
            } else {
               this.setMinHeight(var1);
               this.setMinWidth(var1);
            }

            this.insetChipBackgroundDrawable(var5, var3, var5, var3);
            this.updateBackgroundDrawable();
            return true;
         }
      }
   }

   public Drawable getBackgroundDrawable() {
      InsetDrawable var1 = this.insetBackgroundDrawable;
      Object var2 = var1;
      if (var1 == null) {
         var2 = this.chipDrawable;
      }

      return (Drawable)var2;
   }

   public Drawable getCheckedIcon() {
      ChipDrawable var1 = this.chipDrawable;
      Drawable var2;
      if (var1 != null) {
         var2 = var1.getCheckedIcon();
      } else {
         var2 = null;
      }

      return var2;
   }

   public ColorStateList getCheckedIconTint() {
      ChipDrawable var1 = this.chipDrawable;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getCheckedIconTint();
      } else {
         var2 = null;
      }

      return var2;
   }

   public ColorStateList getChipBackgroundColor() {
      ChipDrawable var1 = this.chipDrawable;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getChipBackgroundColor();
      } else {
         var2 = null;
      }

      return var2;
   }

   public float getChipCornerRadius() {
      ChipDrawable var1 = this.chipDrawable;
      float var2 = 0.0F;
      if (var1 != null) {
         var2 = Math.max(0.0F, var1.getChipCornerRadius());
      }

      return var2;
   }

   public Drawable getChipDrawable() {
      return this.chipDrawable;
   }

   public float getChipEndPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getChipEndPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public Drawable getChipIcon() {
      ChipDrawable var1 = this.chipDrawable;
      Drawable var2;
      if (var1 != null) {
         var2 = var1.getChipIcon();
      } else {
         var2 = null;
      }

      return var2;
   }

   public float getChipIconSize() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getChipIconSize();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public ColorStateList getChipIconTint() {
      ChipDrawable var1 = this.chipDrawable;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getChipIconTint();
      } else {
         var2 = null;
      }

      return var2;
   }

   public float getChipMinHeight() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getChipMinHeight();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public float getChipStartPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getChipStartPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public ColorStateList getChipStrokeColor() {
      ChipDrawable var1 = this.chipDrawable;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getChipStrokeColor();
      } else {
         var2 = null;
      }

      return var2;
   }

   public float getChipStrokeWidth() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getChipStrokeWidth();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   @Deprecated
   public CharSequence getChipText() {
      return this.getText();
   }

   public Drawable getCloseIcon() {
      ChipDrawable var1 = this.chipDrawable;
      Drawable var2;
      if (var1 != null) {
         var2 = var1.getCloseIcon();
      } else {
         var2 = null;
      }

      return var2;
   }

   public CharSequence getCloseIconContentDescription() {
      ChipDrawable var1 = this.chipDrawable;
      CharSequence var2;
      if (var1 != null) {
         var2 = var1.getCloseIconContentDescription();
      } else {
         var2 = null;
      }

      return var2;
   }

   public float getCloseIconEndPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getCloseIconEndPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public float getCloseIconSize() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getCloseIconSize();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public float getCloseIconStartPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getCloseIconStartPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public ColorStateList getCloseIconTint() {
      ChipDrawable var1 = this.chipDrawable;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getCloseIconTint();
      } else {
         var2 = null;
      }

      return var2;
   }

   public TruncateAt getEllipsize() {
      ChipDrawable var1 = this.chipDrawable;
      TruncateAt var2;
      if (var1 != null) {
         var2 = var1.getEllipsize();
      } else {
         var2 = null;
      }

      return var2;
   }

   public void getFocusedRect(Rect var1) {
      if (this.touchHelper.getKeyboardFocusedVirtualViewId() != 1 && this.touchHelper.getAccessibilityFocusedVirtualViewId() != 1) {
         super.getFocusedRect(var1);
      } else {
         var1.set(this.getCloseIconTouchBoundsInt());
      }

   }

   public MotionSpec getHideMotionSpec() {
      ChipDrawable var1 = this.chipDrawable;
      MotionSpec var2;
      if (var1 != null) {
         var2 = var1.getHideMotionSpec();
      } else {
         var2 = null;
      }

      return var2;
   }

   public float getIconEndPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getIconEndPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public float getIconStartPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getIconStartPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public ColorStateList getRippleColor() {
      ChipDrawable var1 = this.chipDrawable;
      ColorStateList var2;
      if (var1 != null) {
         var2 = var1.getRippleColor();
      } else {
         var2 = null;
      }

      return var2;
   }

   public ShapeAppearanceModel getShapeAppearanceModel() {
      return this.chipDrawable.getShapeAppearanceModel();
   }

   public MotionSpec getShowMotionSpec() {
      ChipDrawable var1 = this.chipDrawable;
      MotionSpec var2;
      if (var1 != null) {
         var2 = var1.getShowMotionSpec();
      } else {
         var2 = null;
      }

      return var2;
   }

   public float getTextEndPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getTextEndPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public float getTextStartPadding() {
      ChipDrawable var1 = this.chipDrawable;
      float var2;
      if (var1 != null) {
         var2 = var1.getTextStartPadding();
      } else {
         var2 = 0.0F;
      }

      return var2;
   }

   public boolean isCheckable() {
      ChipDrawable var1 = this.chipDrawable;
      boolean var2;
      if (var1 != null && var1.isCheckable()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated
   public boolean isCheckedIconEnabled() {
      return this.isCheckedIconVisible();
   }

   public boolean isCheckedIconVisible() {
      ChipDrawable var1 = this.chipDrawable;
      boolean var2;
      if (var1 != null && var1.isCheckedIconVisible()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated
   public boolean isChipIconEnabled() {
      return this.isChipIconVisible();
   }

   public boolean isChipIconVisible() {
      ChipDrawable var1 = this.chipDrawable;
      boolean var2;
      if (var1 != null && var1.isChipIconVisible()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated
   public boolean isCloseIconEnabled() {
      return this.isCloseIconVisible();
   }

   public boolean isCloseIconVisible() {
      ChipDrawable var1 = this.chipDrawable;
      boolean var2;
      if (var1 != null && var1.isCloseIconVisible()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      MaterialShapeUtils.setParentAbsoluteElevation(this, this.chipDrawable);
   }

   public void onChipDrawableSizeChange() {
      this.ensureAccessibleTouchTarget(this.minTouchTargetSize);
      this.requestLayout();
      if (VERSION.SDK_INT >= 21) {
         this.invalidateOutline();
      }

   }

   protected int[] onCreateDrawableState(int var1) {
      int[] var2 = super.onCreateDrawableState(var1 + 2);
      if (this.isChecked()) {
         mergeDrawableStates(var2, SELECTED_STATE);
      }

      if (this.isCheckable()) {
         mergeDrawableStates(var2, CHECKABLE_STATE_SET);
      }

      return var2;
   }

   protected void onFocusChanged(boolean var1, int var2, Rect var3) {
      super.onFocusChanged(var1, var2, var3);
      this.touchHelper.onFocusChanged(var1, var2, var3);
   }

   public boolean onHoverEvent(MotionEvent var1) {
      int var2 = var1.getActionMasked();
      if (var2 != 7) {
         if (var2 == 10) {
            this.setCloseIconHovered(false);
         }
      } else {
         this.setCloseIconHovered(this.getCloseIconTouchBounds().contains(var1.getX(), var1.getY()));
      }

      return super.onHoverEvent(var1);
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      if (!this.isCheckable() && !this.isClickable()) {
         var1.setClassName("android.view.View");
      } else {
         String var2;
         if (this.isCheckable()) {
            var2 = "android.widget.CompoundButton";
         } else {
            var2 = "android.widget.Button";
         }

         var1.setClassName(var2);
      }

      var1.setCheckable(this.isCheckable());
      var1.setClickable(this.isClickable());
      if (this.getParent() instanceof ChipGroup) {
         ChipGroup var5 = (ChipGroup)this.getParent();
         AccessibilityNodeInfoCompat var4 = AccessibilityNodeInfoCompat.wrap(var1);
         int var3;
         if (var5.isSingleLine()) {
            var3 = var5.getIndexOfChip(this);
         } else {
            var3 = -1;
         }

         var4.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(var5.getRowIndex(this), 1, var3, 1, false, this.isChecked()));
      }

   }

   public PointerIcon onResolvePointerIcon(MotionEvent var1, int var2) {
      return this.getCloseIconTouchBounds().contains(var1.getX(), var1.getY()) && this.isEnabled() ? PointerIcon.getSystemIcon(this.getContext(), 1002) : null;
   }

   public void onRtlPropertiesChanged(int var1) {
      super.onRtlPropertiesChanged(var1);
      if (this.lastLayoutDirection != var1) {
         this.lastLayoutDirection = var1;
         this.updatePaddingInternal();
      }

   }

   public boolean onTouchEvent(MotionEvent var1) {
      boolean var4;
      boolean var5;
      label48: {
         label47: {
            label53: {
               int var2 = var1.getActionMasked();
               boolean var3 = this.getCloseIconTouchBounds().contains(var1.getX(), var1.getY());
               var4 = false;
               if (var2 != 0) {
                  label43: {
                     if (var2 != 1) {
                        if (var2 == 2) {
                           if (!this.closeIconPressed) {
                              break label47;
                           }

                           if (!var3) {
                              this.setCloseIconPressed(false);
                           }
                           break label43;
                        }

                        if (var2 != 3) {
                           break label47;
                        }
                     } else if (this.closeIconPressed) {
                        this.performCloseIconClick();
                        var5 = true;
                        break label53;
                     }

                     var5 = false;
                     break label53;
                  }
               } else {
                  if (!var3) {
                     break label47;
                  }

                  this.setCloseIconPressed(true);
               }

               var5 = true;
               break label48;
            }

            this.setCloseIconPressed(false);
            break label48;
         }

         var5 = false;
      }

      if (var5 || super.onTouchEvent(var1)) {
         var4 = true;
      }

      return var4;
   }

   public boolean performCloseIconClick() {
      boolean var1 = false;
      this.playSoundEffect(0);
      OnClickListener var2 = this.onCloseIconClickListener;
      if (var2 != null) {
         var2.onClick(this);
         var1 = true;
      }

      this.touchHelper.sendEventForVirtualView(1, 1);
      return var1;
   }

   public void setBackground(Drawable var1) {
      if (var1 != this.getBackgroundDrawable() && var1 != this.ripple) {
         Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
      } else {
         super.setBackground(var1);
      }

   }

   public void setBackgroundColor(int var1) {
      Log.w("Chip", "Do not set the background color; Chip manages its own background drawable.");
   }

   public void setBackgroundDrawable(Drawable var1) {
      if (var1 != this.getBackgroundDrawable() && var1 != this.ripple) {
         Log.w("Chip", "Do not set the background drawable; Chip manages its own background drawable.");
      } else {
         super.setBackgroundDrawable(var1);
      }

   }

   public void setBackgroundResource(int var1) {
      Log.w("Chip", "Do not set the background resource; Chip manages its own background drawable.");
   }

   public void setBackgroundTintList(ColorStateList var1) {
      Log.w("Chip", "Do not set the background tint list; Chip manages its own background drawable.");
   }

   public void setBackgroundTintMode(Mode var1) {
      Log.w("Chip", "Do not set the background tint mode; Chip manages its own background drawable.");
   }

   public void setCheckable(boolean var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckable(var1);
      }

   }

   public void setCheckableResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckableResource(var1);
      }

   }

   public void setChecked(boolean var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 == null) {
         this.deferredCheckedValue = var1;
      } else if (var2.isCheckable()) {
         boolean var3 = this.isChecked();
         super.setChecked(var1);
         if (var3 != var1) {
            OnCheckedChangeListener var4 = this.onCheckedChangeListenerInternal;
            if (var4 != null) {
               var4.onCheckedChanged(this, var1);
            }
         }
      }

   }

   public void setCheckedIcon(Drawable var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckedIcon(var1);
      }

   }

   @Deprecated
   public void setCheckedIconEnabled(boolean var1) {
      this.setCheckedIconVisible(var1);
   }

   @Deprecated
   public void setCheckedIconEnabledResource(int var1) {
      this.setCheckedIconVisible(var1);
   }

   public void setCheckedIconResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckedIconResource(var1);
      }

   }

   public void setCheckedIconTint(ColorStateList var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckedIconTint(var1);
      }

   }

   public void setCheckedIconTintResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckedIconTintResource(var1);
      }

   }

   public void setCheckedIconVisible(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckedIconVisible(var1);
      }

   }

   public void setCheckedIconVisible(boolean var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCheckedIconVisible(var1);
      }

   }

   public void setChipBackgroundColor(ColorStateList var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipBackgroundColor(var1);
      }

   }

   public void setChipBackgroundColorResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipBackgroundColorResource(var1);
      }

   }

   @Deprecated
   public void setChipCornerRadius(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipCornerRadius(var1);
      }

   }

   @Deprecated
   public void setChipCornerRadiusResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipCornerRadiusResource(var1);
      }

   }

   public void setChipDrawable(ChipDrawable var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != var1) {
         this.unapplyChipDrawable(var2);
         this.chipDrawable = var1;
         var1.setShouldDrawText(false);
         this.applyChipDrawable(this.chipDrawable);
         this.ensureAccessibleTouchTarget(this.minTouchTargetSize);
      }

   }

   public void setChipEndPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipEndPadding(var1);
      }

   }

   public void setChipEndPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipEndPaddingResource(var1);
      }

   }

   public void setChipIcon(Drawable var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIcon(var1);
      }

   }

   @Deprecated
   public void setChipIconEnabled(boolean var1) {
      this.setChipIconVisible(var1);
   }

   @Deprecated
   public void setChipIconEnabledResource(int var1) {
      this.setChipIconVisible(var1);
   }

   public void setChipIconResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIconResource(var1);
      }

   }

   public void setChipIconSize(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIconSize(var1);
      }

   }

   public void setChipIconSizeResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIconSizeResource(var1);
      }

   }

   public void setChipIconTint(ColorStateList var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIconTint(var1);
      }

   }

   public void setChipIconTintResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIconTintResource(var1);
      }

   }

   public void setChipIconVisible(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIconVisible(var1);
      }

   }

   public void setChipIconVisible(boolean var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipIconVisible(var1);
      }

   }

   public void setChipMinHeight(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipMinHeight(var1);
      }

   }

   public void setChipMinHeightResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipMinHeightResource(var1);
      }

   }

   public void setChipStartPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipStartPadding(var1);
      }

   }

   public void setChipStartPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipStartPaddingResource(var1);
      }

   }

   public void setChipStrokeColor(ColorStateList var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipStrokeColor(var1);
      }

   }

   public void setChipStrokeColorResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipStrokeColorResource(var1);
      }

   }

   public void setChipStrokeWidth(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipStrokeWidth(var1);
      }

   }

   public void setChipStrokeWidthResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setChipStrokeWidthResource(var1);
      }

   }

   @Deprecated
   public void setChipText(CharSequence var1) {
      this.setText(var1);
   }

   @Deprecated
   public void setChipTextResource(int var1) {
      this.setText(this.getResources().getString(var1));
   }

   public void setCloseIcon(Drawable var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIcon(var1);
      }

      this.updateAccessibilityDelegate();
   }

   public void setCloseIconContentDescription(CharSequence var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconContentDescription(var1);
      }

   }

   @Deprecated
   public void setCloseIconEnabled(boolean var1) {
      this.setCloseIconVisible(var1);
   }

   @Deprecated
   public void setCloseIconEnabledResource(int var1) {
      this.setCloseIconVisible(var1);
   }

   public void setCloseIconEndPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconEndPadding(var1);
      }

   }

   public void setCloseIconEndPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconEndPaddingResource(var1);
      }

   }

   public void setCloseIconResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconResource(var1);
      }

      this.updateAccessibilityDelegate();
   }

   public void setCloseIconSize(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconSize(var1);
      }

   }

   public void setCloseIconSizeResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconSizeResource(var1);
      }

   }

   public void setCloseIconStartPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconStartPadding(var1);
      }

   }

   public void setCloseIconStartPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconStartPaddingResource(var1);
      }

   }

   public void setCloseIconTint(ColorStateList var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconTint(var1);
      }

   }

   public void setCloseIconTintResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconTintResource(var1);
      }

   }

   public void setCloseIconVisible(int var1) {
      this.setCloseIconVisible(this.getResources().getBoolean(var1));
   }

   public void setCloseIconVisible(boolean var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setCloseIconVisible(var1);
      }

      this.updateAccessibilityDelegate();
   }

   public void setCompoundDrawables(Drawable var1, Drawable var2, Drawable var3, Drawable var4) {
      if (var1 == null) {
         if (var3 == null) {
            super.setCompoundDrawables(var1, var2, var3, var4);
         } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
         }
      } else {
         throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
      }
   }

   public void setCompoundDrawablesRelative(Drawable var1, Drawable var2, Drawable var3, Drawable var4) {
      if (var1 == null) {
         if (var3 == null) {
            super.setCompoundDrawablesRelative(var1, var2, var3, var4);
         } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
         }
      } else {
         throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
      }
   }

   public void setCompoundDrawablesRelativeWithIntrinsicBounds(int var1, int var2, int var3, int var4) {
      if (var1 == 0) {
         if (var3 == 0) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(var1, var2, var3, var4);
         } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
         }
      } else {
         throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
      }
   }

   public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable var1, Drawable var2, Drawable var3, Drawable var4) {
      if (var1 == null) {
         if (var3 == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(var1, var2, var3, var4);
         } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
         }
      } else {
         throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
      }
   }

   public void setCompoundDrawablesWithIntrinsicBounds(int var1, int var2, int var3, int var4) {
      if (var1 == 0) {
         if (var3 == 0) {
            super.setCompoundDrawablesWithIntrinsicBounds(var1, var2, var3, var4);
         } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
         }
      } else {
         throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
      }
   }

   public void setCompoundDrawablesWithIntrinsicBounds(Drawable var1, Drawable var2, Drawable var3, Drawable var4) {
      if (var1 == null) {
         if (var3 == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(var1, var2, var3, var4);
         } else {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
         }
      } else {
         throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
      }
   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setElevation(var1);
      }

   }

   public void setEllipsize(TruncateAt var1) {
      if (this.chipDrawable != null) {
         if (var1 != TruncateAt.MARQUEE) {
            super.setEllipsize(var1);
            ChipDrawable var2 = this.chipDrawable;
            if (var2 != null) {
               var2.setEllipsize(var1);
            }

         } else {
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
         }
      }
   }

   public void setEnsureMinTouchTargetSize(boolean var1) {
      this.ensureMinTouchTargetSize = var1;
      this.ensureAccessibleTouchTarget(this.minTouchTargetSize);
   }

   public void setGravity(int var1) {
      if (var1 != 8388627) {
         Log.w("Chip", "Chip text must be vertically center and start aligned");
      } else {
         super.setGravity(var1);
      }

   }

   public void setHideMotionSpec(MotionSpec var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setHideMotionSpec(var1);
      }

   }

   public void setHideMotionSpecResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setHideMotionSpecResource(var1);
      }

   }

   public void setIconEndPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setIconEndPadding(var1);
      }

   }

   public void setIconEndPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setIconEndPaddingResource(var1);
      }

   }

   public void setIconStartPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setIconStartPadding(var1);
      }

   }

   public void setIconStartPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setIconStartPaddingResource(var1);
      }

   }

   public void setLayoutDirection(int var1) {
      if (this.chipDrawable != null) {
         if (VERSION.SDK_INT >= 17) {
            super.setLayoutDirection(var1);
         }

      }
   }

   public void setLines(int var1) {
      if (var1 <= 1) {
         super.setLines(var1);
      } else {
         throw new UnsupportedOperationException("Chip does not support multi-line text");
      }
   }

   public void setMaxLines(int var1) {
      if (var1 <= 1) {
         super.setMaxLines(var1);
      } else {
         throw new UnsupportedOperationException("Chip does not support multi-line text");
      }
   }

   public void setMaxWidth(int var1) {
      super.setMaxWidth(var1);
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setMaxWidth(var1);
      }

   }

   public void setMinLines(int var1) {
      if (var1 <= 1) {
         super.setMinLines(var1);
      } else {
         throw new UnsupportedOperationException("Chip does not support multi-line text");
      }
   }

   void setOnCheckedChangeListenerInternal(OnCheckedChangeListener var1) {
      this.onCheckedChangeListenerInternal = var1;
   }

   public void setOnCloseIconClickListener(OnClickListener var1) {
      this.onCloseIconClickListener = var1;
      this.updateAccessibilityDelegate();
   }

   public void setRippleColor(ColorStateList var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setRippleColor(var1);
      }

      if (!this.chipDrawable.getUseCompatRipple()) {
         this.updateFrameworkRippleBackground();
      }

   }

   public void setRippleColorResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setRippleColorResource(var1);
         if (!this.chipDrawable.getUseCompatRipple()) {
            this.updateFrameworkRippleBackground();
         }
      }

   }

   public void setShapeAppearanceModel(ShapeAppearanceModel var1) {
      this.chipDrawable.setShapeAppearanceModel(var1);
   }

   public void setShowMotionSpec(MotionSpec var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setShowMotionSpec(var1);
      }

   }

   public void setShowMotionSpecResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setShowMotionSpecResource(var1);
      }

   }

   public void setSingleLine(boolean var1) {
      if (var1) {
         super.setSingleLine(var1);
      } else {
         throw new UnsupportedOperationException("Chip does not support multi-line text");
      }
   }

   public void setText(CharSequence var1, BufferType var2) {
      if (this.chipDrawable != null) {
         Object var3 = var1;
         if (var1 == null) {
            var3 = "";
         }

         Object var4;
         if (this.chipDrawable.shouldDrawText()) {
            var4 = null;
         } else {
            var4 = var3;
         }

         super.setText((CharSequence)var4, var2);
         ChipDrawable var5 = this.chipDrawable;
         if (var5 != null) {
            var5.setText((CharSequence)var3);
         }

      }
   }

   public void setTextAppearance(int var1) {
      super.setTextAppearance(var1);
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setTextAppearanceResource(var1);
      }

      this.updateTextPaintDrawState();
   }

   public void setTextAppearance(Context var1, int var2) {
      super.setTextAppearance(var1, var2);
      ChipDrawable var3 = this.chipDrawable;
      if (var3 != null) {
         var3.setTextAppearanceResource(var2);
      }

      this.updateTextPaintDrawState();
   }

   public void setTextAppearance(TextAppearance var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setTextAppearance(var1);
      }

      this.updateTextPaintDrawState();
   }

   public void setTextAppearanceResource(int var1) {
      this.setTextAppearance(this.getContext(), var1);
   }

   public void setTextEndPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setTextEndPadding(var1);
      }

   }

   public void setTextEndPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setTextEndPaddingResource(var1);
      }

   }

   public void setTextStartPadding(float var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setTextStartPadding(var1);
      }

   }

   public void setTextStartPaddingResource(int var1) {
      ChipDrawable var2 = this.chipDrawable;
      if (var2 != null) {
         var2.setTextStartPaddingResource(var1);
      }

   }

   public boolean shouldEnsureMinTouchTargetSize() {
      return this.ensureMinTouchTargetSize;
   }

   private class ChipTouchHelper extends ExploreByTouchHelper {
      ChipTouchHelper(Chip var2) {
         super(var2);
      }

      protected int getVirtualViewAt(float var1, float var2) {
         byte var3;
         if (Chip.this.hasCloseIcon() && Chip.this.getCloseIconTouchBounds().contains(var1, var2)) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         return var3;
      }

      protected void getVisibleVirtualViews(List<Integer> var1) {
         var1.add(0);
         if (Chip.this.hasCloseIcon() && Chip.this.isCloseIconVisible() && Chip.this.onCloseIconClickListener != null) {
            var1.add(1);
         }

      }

      protected boolean onPerformActionForVirtualView(int var1, int var2, Bundle var3) {
         if (var2 == 16) {
            if (var1 == 0) {
               return Chip.this.performClick();
            }

            if (var1 == 1) {
               return Chip.this.performCloseIconClick();
            }
         }

         return false;
      }

      protected void onPopulateNodeForHost(AccessibilityNodeInfoCompat var1) {
         var1.setCheckable(Chip.this.isCheckable());
         var1.setClickable(Chip.this.isClickable());
         if (!Chip.this.isCheckable() && !Chip.this.isClickable()) {
            var1.setClassName("android.view.View");
         } else {
            String var2;
            if (Chip.this.isCheckable()) {
               var2 = "android.widget.CompoundButton";
            } else {
               var2 = "android.widget.Button";
            }

            var1.setClassName(var2);
         }

         CharSequence var3 = Chip.this.getText();
         if (VERSION.SDK_INT >= 23) {
            var1.setText(var3);
         } else {
            var1.setContentDescription(var3);
         }

      }

      protected void onPopulateNodeForVirtualView(int var1, AccessibilityNodeInfoCompat var2) {
         Object var3 = "";
         if (var1 == 1) {
            CharSequence var4 = Chip.this.getCloseIconContentDescription();
            if (var4 != null) {
               var2.setContentDescription(var4);
            } else {
               var4 = Chip.this.getText();
               Context var5 = Chip.this.getContext();
               var1 = R.string.mtrl_chip_close_icon_content_description;
               if (!TextUtils.isEmpty(var4)) {
                  var3 = var4;
               }

               var2.setContentDescription(var5.getString(var1, new Object[]{var3}).trim());
            }

            var2.setBoundsInParent(Chip.this.getCloseIconTouchBoundsInt());
            var2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            var2.setEnabled(Chip.this.isEnabled());
         } else {
            var2.setContentDescription("");
            var2.setBoundsInParent(Chip.EMPTY_BOUNDS);
         }

      }

      protected void onVirtualViewKeyboardFocusChanged(int var1, boolean var2) {
         if (var1 == 1) {
            Chip.this.closeIconFocused = var2;
            Chip.this.refreshDrawableState();
         }

      }
   }
}
