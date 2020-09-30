package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class TextInputEditText extends AppCompatEditText {
   private final Rect parentRect;
   private boolean textInputLayoutFocusedRectEnabled;

   public TextInputEditText(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public TextInputEditText(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.editTextStyle);
   }

   public TextInputEditText(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, 0), var2, var3);
      this.parentRect = new Rect();
      TypedArray var4 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.TextInputEditText, var3, R.style.Widget_Design_TextInputEditText);
      this.setTextInputLayoutFocusedRectEnabled(var4.getBoolean(R.styleable.TextInputEditText_textInputLayoutFocusedRectEnabled, false));
      var4.recycle();
   }

   private String getAccessibilityNodeInfoText(TextInputLayout var1) {
      Editable var2 = this.getText();
      CharSequence var3 = var1.getHint();
      CharSequence var4 = var1.getHelperText();
      CharSequence var5 = var1.getError();
      boolean var6 = TextUtils.isEmpty(var2);
      boolean var7 = TextUtils.isEmpty(var3);
      boolean var8 = TextUtils.isEmpty(var4) ^ true;
      boolean var9 = TextUtils.isEmpty(var5) ^ true;
      String var10 = "";
      String var11;
      if (var7 ^ true) {
         var11 = var3.toString();
      } else {
         var11 = "";
      }

      StringBuilder var12 = new StringBuilder();
      var12.append(var11);
      if ((var9 || var8) && !TextUtils.isEmpty(var11)) {
         var11 = ", ";
      } else {
         var11 = "";
      }

      var12.append(var11);
      var11 = var12.toString();
      var12 = new StringBuilder();
      var12.append(var11);
      Object var13;
      if (var9) {
         var13 = var5;
      } else if (var8) {
         var13 = var4;
      } else {
         var13 = "";
      }

      var12.append(var13);
      String var15 = var12.toString();
      if (var6 ^ true) {
         StringBuilder var14 = new StringBuilder();
         var14.append(var2);
         var11 = var10;
         if (!TextUtils.isEmpty(var15)) {
            StringBuilder var16 = new StringBuilder();
            var16.append(", ");
            var16.append(var15);
            var11 = var16.toString();
         }

         var14.append(var11);
         return var14.toString();
      } else {
         return !TextUtils.isEmpty(var15) ? var15 : "";
      }
   }

   private CharSequence getHintFromLayout() {
      TextInputLayout var1 = this.getTextInputLayout();
      CharSequence var2;
      if (var1 != null) {
         var2 = var1.getHint();
      } else {
         var2 = null;
      }

      return var2;
   }

   private TextInputLayout getTextInputLayout() {
      for(ViewParent var1 = this.getParent(); var1 instanceof View; var1 = var1.getParent()) {
         if (var1 instanceof TextInputLayout) {
            return (TextInputLayout)var1;
         }
      }

      return null;
   }

   public void getFocusedRect(Rect var1) {
      super.getFocusedRect(var1);
      TextInputLayout var2 = this.getTextInputLayout();
      if (var2 != null && this.textInputLayoutFocusedRectEnabled && var1 != null) {
         var2.getFocusedRect(this.parentRect);
         var1.bottom = this.parentRect.bottom;
      }

   }

   public boolean getGlobalVisibleRect(Rect var1, Point var2) {
      boolean var3 = super.getGlobalVisibleRect(var1, var2);
      TextInputLayout var4 = this.getTextInputLayout();
      if (var4 != null && this.textInputLayoutFocusedRectEnabled && var1 != null) {
         var4.getGlobalVisibleRect(this.parentRect, var2);
         var1.bottom = this.parentRect.bottom;
      }

      return var3;
   }

   public CharSequence getHint() {
      TextInputLayout var1 = this.getTextInputLayout();
      return var1 != null && var1.isProvidingHint() ? var1.getHint() : super.getHint();
   }

   public boolean isTextInputLayoutFocusedRectEnabled() {
      return this.textInputLayoutFocusedRectEnabled;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      TextInputLayout var1 = this.getTextInputLayout();
      if (var1 != null && var1.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
         this.setHint("");
      }

   }

   public InputConnection onCreateInputConnection(EditorInfo var1) {
      InputConnection var2 = super.onCreateInputConnection(var1);
      if (var2 != null && var1.hintText == null) {
         var1.hintText = this.getHintFromLayout();
      }

      return var2;
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      TextInputLayout var2 = this.getTextInputLayout();
      if (VERSION.SDK_INT < 23 && var2 != null) {
         var1.setText(this.getAccessibilityNodeInfoText(var2));
      }

   }

   public boolean requestRectangleOnScreen(Rect var1) {
      boolean var2 = super.requestRectangleOnScreen(var1);
      TextInputLayout var3 = this.getTextInputLayout();
      if (var3 != null && this.textInputLayoutFocusedRectEnabled) {
         this.parentRect.set(0, var3.getHeight() - this.getResources().getDimensionPixelOffset(R.dimen.mtrl_edittext_rectangle_top_offset), var3.getWidth(), var3.getHeight());
         var3.requestRectangleOnScreen(this.parentRect, true);
      }

      return var2;
   }

   public void setTextInputLayoutFocusedRectEnabled(boolean var1) {
      this.textInputLayoutFocusedRectEnabled = var1;
   }
}
