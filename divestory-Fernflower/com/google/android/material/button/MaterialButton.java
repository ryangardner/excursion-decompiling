package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialButton extends AppCompatButton implements Checkable, Shapeable {
   private static final int[] CHECKABLE_STATE_SET = new int[]{16842911};
   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   private static final int DEF_STYLE_RES;
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
   private Mode iconTintMode;
   private final MaterialButtonHelper materialButtonHelper;
   private final LinkedHashSet<MaterialButton.OnCheckedChangeListener> onCheckedChangeListeners;
   private MaterialButton.OnPressedChangeListener onPressedChangeListenerInternal;

   static {
      DEF_STYLE_RES = R.style.Widget_MaterialComponents_Button;
   }

   public MaterialButton(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public MaterialButton(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.materialButtonStyle);
   }

   public MaterialButton(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.onCheckedChangeListeners = new LinkedHashSet();
      boolean var4 = false;
      this.checked = false;
      this.broadcasting = false;
      Context var5 = this.getContext();
      TypedArray var6 = ThemeEnforcement.obtainStyledAttributes(var5, var2, R.styleable.MaterialButton, var3, DEF_STYLE_RES);
      this.iconPadding = var6.getDimensionPixelSize(R.styleable.MaterialButton_iconPadding, 0);
      this.iconTintMode = ViewUtils.parseTintMode(var6.getInt(R.styleable.MaterialButton_iconTintMode, -1), Mode.SRC_IN);
      this.iconTint = MaterialResources.getColorStateList(this.getContext(), var6, R.styleable.MaterialButton_iconTint);
      this.icon = MaterialResources.getDrawable(this.getContext(), var6, R.styleable.MaterialButton_icon);
      this.iconGravity = var6.getInteger(R.styleable.MaterialButton_iconGravity, 1);
      this.iconSize = var6.getDimensionPixelSize(R.styleable.MaterialButton_iconSize, 0);
      MaterialButtonHelper var7 = new MaterialButtonHelper(this, ShapeAppearanceModel.builder(var5, var2, var3, DEF_STYLE_RES).build());
      this.materialButtonHelper = var7;
      var7.loadFromAttributes(var6);
      var6.recycle();
      this.setCompoundDrawablePadding(this.iconPadding);
      if (this.icon != null) {
         var4 = true;
      }

      this.updateIcon(var4);
   }

   private String getA11yClassName() {
      Class var1;
      if (this.isCheckable()) {
         var1 = CompoundButton.class;
      } else {
         var1 = Button.class;
      }

      return var1.getName();
   }

   private boolean isLayoutRTL() {
      int var1 = ViewCompat.getLayoutDirection(this);
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   private boolean isUsingOriginalBackground() {
      MaterialButtonHelper var1 = this.materialButtonHelper;
      boolean var2;
      if (var1 != null && !var1.isBackgroundOverwritten()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void resetIconDrawable(boolean var1) {
      if (var1) {
         TextViewCompat.setCompoundDrawablesRelative(this, this.icon, (Drawable)null, (Drawable)null, (Drawable)null);
      } else {
         TextViewCompat.setCompoundDrawablesRelative(this, (Drawable)null, (Drawable)null, this.icon, (Drawable)null);
      }

   }

   private void updateIcon(boolean var1) {
      Drawable var2 = this.icon;
      boolean var3 = false;
      int var4;
      if (var2 != null) {
         var2 = DrawableCompat.wrap(var2).mutate();
         this.icon = var2;
         DrawableCompat.setTintList(var2, this.iconTint);
         Mode var9 = this.iconTintMode;
         if (var9 != null) {
            DrawableCompat.setTintMode(this.icon, var9);
         }

         var4 = this.iconSize;
         if (var4 == 0) {
            var4 = this.icon.getIntrinsicWidth();
         }

         int var5 = this.iconSize;
         if (var5 == 0) {
            var5 = this.icon.getIntrinsicHeight();
         }

         var2 = this.icon;
         int var6 = this.iconLeft;
         var2.setBounds(var6, 0, var4 + var6, var5);
      }

      var4 = this.iconGravity;
      boolean var7;
      if (var4 != 1 && var4 != 2) {
         var7 = false;
      } else {
         var7 = true;
      }

      if (var1) {
         this.resetIconDrawable(var7);
      } else {
         boolean var10;
         label62: {
            Drawable[] var8 = TextViewCompat.getCompoundDrawablesRelative(this);
            var2 = var8[0];
            Drawable var11 = var8[2];
            if (!var7 || var2 == this.icon) {
               var10 = var3;
               if (var7) {
                  break label62;
               }

               var10 = var3;
               if (var11 == this.icon) {
                  break label62;
               }
            }

            var10 = true;
         }

         if (var10) {
            this.resetIconDrawable(var7);
         }

      }
   }

   private void updateIconPosition() {
      if (this.icon != null && this.getLayout() != null) {
         int var1 = this.iconGravity;
         boolean var2 = true;
         if (var1 != 1 && var1 != 3) {
            TextPaint var3 = this.getPaint();
            String var4 = this.getText().toString();
            String var5 = var4;
            if (this.getTransformationMethod() != null) {
               var5 = this.getTransformationMethod().getTransformation(var4, this).toString();
            }

            int var6 = Math.min((int)var3.measureText(var5), this.getLayout().getEllipsizedWidth());
            int var7 = this.iconSize;
            var1 = var7;
            if (var7 == 0) {
               var1 = this.icon.getIntrinsicWidth();
            }

            var7 = (this.getMeasuredWidth() - var6 - ViewCompat.getPaddingEnd(this) - var1 - this.iconPadding - ViewCompat.getPaddingStart(this)) / 2;
            boolean var8 = this.isLayoutRTL();
            if (this.iconGravity != 4) {
               var2 = false;
            }

            var1 = var7;
            if (var8 != var2) {
               var1 = -var7;
            }

            if (this.iconLeft != var1) {
               this.iconLeft = var1;
               this.updateIcon(false);
            }

            return;
         }

         this.iconLeft = 0;
         this.updateIcon(false);
      }

   }

   public void addOnCheckedChangeListener(MaterialButton.OnCheckedChangeListener var1) {
      this.onCheckedChangeListeners.add(var1);
   }

   public void clearOnCheckedChangeListeners() {
      this.onCheckedChangeListeners.clear();
   }

   public ColorStateList getBackgroundTintList() {
      return this.getSupportBackgroundTintList();
   }

   public Mode getBackgroundTintMode() {
      return this.getSupportBackgroundTintMode();
   }

   public int getCornerRadius() {
      int var1;
      if (this.isUsingOriginalBackground()) {
         var1 = this.materialButtonHelper.getCornerRadius();
      } else {
         var1 = 0;
      }

      return var1;
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

   public Mode getIconTintMode() {
      return this.iconTintMode;
   }

   public ColorStateList getRippleColor() {
      ColorStateList var1;
      if (this.isUsingOriginalBackground()) {
         var1 = this.materialButtonHelper.getRippleColor();
      } else {
         var1 = null;
      }

      return var1;
   }

   public ShapeAppearanceModel getShapeAppearanceModel() {
      if (this.isUsingOriginalBackground()) {
         return this.materialButtonHelper.getShapeAppearanceModel();
      } else {
         throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
      }
   }

   public ColorStateList getStrokeColor() {
      ColorStateList var1;
      if (this.isUsingOriginalBackground()) {
         var1 = this.materialButtonHelper.getStrokeColor();
      } else {
         var1 = null;
      }

      return var1;
   }

   public int getStrokeWidth() {
      int var1;
      if (this.isUsingOriginalBackground()) {
         var1 = this.materialButtonHelper.getStrokeWidth();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public ColorStateList getSupportBackgroundTintList() {
      return this.isUsingOriginalBackground() ? this.materialButtonHelper.getSupportBackgroundTintList() : super.getSupportBackgroundTintList();
   }

   public Mode getSupportBackgroundTintMode() {
      return this.isUsingOriginalBackground() ? this.materialButtonHelper.getSupportBackgroundTintMode() : super.getSupportBackgroundTintMode();
   }

   public boolean isCheckable() {
      MaterialButtonHelper var1 = this.materialButtonHelper;
      boolean var2;
      if (var1 != null && var1.isCheckable()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isChecked() {
      return this.checked;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      if (this.isUsingOriginalBackground()) {
         MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialButtonHelper.getMaterialShapeDrawable());
      }

   }

   protected int[] onCreateDrawableState(int var1) {
      int[] var2 = super.onCreateDrawableState(var1 + 2);
      if (this.isCheckable()) {
         mergeDrawableStates(var2, CHECKABLE_STATE_SET);
      }

      if (this.isChecked()) {
         mergeDrawableStates(var2, CHECKED_STATE_SET);
      }

      return var2;
   }

   public void onInitializeAccessibilityEvent(AccessibilityEvent var1) {
      super.onInitializeAccessibilityEvent(var1);
      var1.setClassName(this.getA11yClassName());
      var1.setChecked(this.isChecked());
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      var1.setClassName(this.getA11yClassName());
      var1.setCheckable(this.isCheckable());
      var1.setChecked(this.isChecked());
      var1.setClickable(this.isClickable());
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      if (VERSION.SDK_INT == 21) {
         MaterialButtonHelper var6 = this.materialButtonHelper;
         if (var6 != null) {
            var6.updateMaskBounds(var5 - var3, var4 - var2);
         }
      }

   }

   protected void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      this.updateIconPosition();
   }

   public void onRestoreInstanceState(Parcelable var1) {
      if (!(var1 instanceof MaterialButton.SavedState)) {
         super.onRestoreInstanceState(var1);
      } else {
         MaterialButton.SavedState var2 = (MaterialButton.SavedState)var1;
         super.onRestoreInstanceState(var2.getSuperState());
         this.setChecked(var2.checked);
      }
   }

   public Parcelable onSaveInstanceState() {
      MaterialButton.SavedState var1 = new MaterialButton.SavedState(super.onSaveInstanceState());
      var1.checked = this.checked;
      return var1;
   }

   protected void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
      super.onTextChanged(var1, var2, var3, var4);
      this.updateIconPosition();
   }

   public boolean performClick() {
      this.toggle();
      return super.performClick();
   }

   public void removeOnCheckedChangeListener(MaterialButton.OnCheckedChangeListener var1) {
      this.onCheckedChangeListeners.remove(var1);
   }

   public void setBackground(Drawable var1) {
      this.setBackgroundDrawable(var1);
   }

   public void setBackgroundColor(int var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setBackgroundColor(var1);
      } else {
         super.setBackgroundColor(var1);
      }

   }

   public void setBackgroundDrawable(Drawable var1) {
      if (this.isUsingOriginalBackground()) {
         if (var1 != this.getBackground()) {
            Log.w("MaterialButton", "Do not set the background; MaterialButton manages its own background drawable.");
            this.materialButtonHelper.setBackgroundOverwritten();
            super.setBackgroundDrawable(var1);
         } else {
            this.getBackground().setState(var1.getState());
         }
      } else {
         super.setBackgroundDrawable(var1);
      }

   }

   public void setBackgroundResource(int var1) {
      Drawable var2;
      if (var1 != 0) {
         var2 = AppCompatResources.getDrawable(this.getContext(), var1);
      } else {
         var2 = null;
      }

      this.setBackgroundDrawable(var2);
   }

   public void setBackgroundTintList(ColorStateList var1) {
      this.setSupportBackgroundTintList(var1);
   }

   public void setBackgroundTintMode(Mode var1) {
      this.setSupportBackgroundTintMode(var1);
   }

   public void setCheckable(boolean var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setCheckable(var1);
      }

   }

   public void setChecked(boolean var1) {
      if (this.isCheckable() && this.isEnabled() && this.checked != var1) {
         this.checked = var1;
         this.refreshDrawableState();
         if (this.broadcasting) {
            return;
         }

         this.broadcasting = true;
         Iterator var2 = this.onCheckedChangeListeners.iterator();

         while(var2.hasNext()) {
            ((MaterialButton.OnCheckedChangeListener)var2.next()).onCheckedChanged(this, this.checked);
         }

         this.broadcasting = false;
      }

   }

   public void setCornerRadius(int var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setCornerRadius(var1);
      }

   }

   public void setCornerRadiusResource(int var1) {
      if (this.isUsingOriginalBackground()) {
         this.setCornerRadius(this.getResources().getDimensionPixelSize(var1));
      }

   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.getMaterialShapeDrawable().setElevation(var1);
      }

   }

   public void setIcon(Drawable var1) {
      if (this.icon != var1) {
         this.icon = var1;
         this.updateIcon(true);
      }

   }

   public void setIconGravity(int var1) {
      if (this.iconGravity != var1) {
         this.iconGravity = var1;
         this.updateIconPosition();
      }

   }

   public void setIconPadding(int var1) {
      if (this.iconPadding != var1) {
         this.iconPadding = var1;
         this.setCompoundDrawablePadding(var1);
      }

   }

   public void setIconResource(int var1) {
      Drawable var2;
      if (var1 != 0) {
         var2 = AppCompatResources.getDrawable(this.getContext(), var1);
      } else {
         var2 = null;
      }

      this.setIcon(var2);
   }

   public void setIconSize(int var1) {
      if (var1 >= 0) {
         if (this.iconSize != var1) {
            this.iconSize = var1;
            this.updateIcon(true);
         }

      } else {
         throw new IllegalArgumentException("iconSize cannot be less than 0");
      }
   }

   public void setIconTint(ColorStateList var1) {
      if (this.iconTint != var1) {
         this.iconTint = var1;
         this.updateIcon(false);
      }

   }

   public void setIconTintMode(Mode var1) {
      if (this.iconTintMode != var1) {
         this.iconTintMode = var1;
         this.updateIcon(false);
      }

   }

   public void setIconTintResource(int var1) {
      this.setIconTint(AppCompatResources.getColorStateList(this.getContext(), var1));
   }

   void setInternalBackground(Drawable var1) {
      super.setBackgroundDrawable(var1);
   }

   void setOnPressedChangeListenerInternal(MaterialButton.OnPressedChangeListener var1) {
      this.onPressedChangeListenerInternal = var1;
   }

   public void setPressed(boolean var1) {
      MaterialButton.OnPressedChangeListener var2 = this.onPressedChangeListenerInternal;
      if (var2 != null) {
         var2.onPressedChanged(this, var1);
      }

      super.setPressed(var1);
   }

   public void setRippleColor(ColorStateList var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setRippleColor(var1);
      }

   }

   public void setRippleColorResource(int var1) {
      if (this.isUsingOriginalBackground()) {
         this.setRippleColor(AppCompatResources.getColorStateList(this.getContext(), var1));
      }

   }

   public void setShapeAppearanceModel(ShapeAppearanceModel var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setShapeAppearanceModel(var1);
      } else {
         throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
      }
   }

   void setShouldDrawSurfaceColorStroke(boolean var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setShouldDrawSurfaceColorStroke(var1);
      }

   }

   public void setStrokeColor(ColorStateList var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setStrokeColor(var1);
      }

   }

   public void setStrokeColorResource(int var1) {
      if (this.isUsingOriginalBackground()) {
         this.setStrokeColor(AppCompatResources.getColorStateList(this.getContext(), var1));
      }

   }

   public void setStrokeWidth(int var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setStrokeWidth(var1);
      }

   }

   public void setStrokeWidthResource(int var1) {
      if (this.isUsingOriginalBackground()) {
         this.setStrokeWidth(this.getResources().getDimensionPixelSize(var1));
      }

   }

   public void setSupportBackgroundTintList(ColorStateList var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setSupportBackgroundTintList(var1);
      } else {
         super.setSupportBackgroundTintList(var1);
      }

   }

   public void setSupportBackgroundTintMode(Mode var1) {
      if (this.isUsingOriginalBackground()) {
         this.materialButtonHelper.setSupportBackgroundTintMode(var1);
      } else {
         super.setSupportBackgroundTintMode(var1);
      }

   }

   public void toggle() {
      this.setChecked(this.checked ^ true);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface IconGravity {
   }

   public interface OnCheckedChangeListener {
      void onCheckedChanged(MaterialButton var1, boolean var2);
   }

   interface OnPressedChangeListener {
      void onPressedChanged(MaterialButton var1, boolean var2);
   }

   static class SavedState extends AbsSavedState {
      public static final Creator<MaterialButton.SavedState> CREATOR = new ClassLoaderCreator<MaterialButton.SavedState>() {
         public MaterialButton.SavedState createFromParcel(Parcel var1) {
            return new MaterialButton.SavedState(var1, (ClassLoader)null);
         }

         public MaterialButton.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            return new MaterialButton.SavedState(var1, var2);
         }

         public MaterialButton.SavedState[] newArray(int var1) {
            return new MaterialButton.SavedState[var1];
         }
      };
      boolean checked;

      public SavedState(Parcel var1, ClassLoader var2) {
         super(var1, var2);
         if (var2 == null) {
            this.getClass().getClassLoader();
         }

         this.readFromParcel(var1);
      }

      public SavedState(Parcelable var1) {
         super(var1);
      }

      private void readFromParcel(Parcel var1) {
         int var2 = var1.readInt();
         boolean var3 = true;
         if (var2 != 1) {
            var3 = false;
         }

         this.checked = var3;
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         var1.writeInt(this.checked);
      }
   }
}
