package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialAutoCompleteTextView extends AppCompatAutoCompleteTextView {
   private static final int MAX_ITEMS_MEASURED = 15;
   private final AccessibilityManager accessibilityManager;
   private final ListPopupWindow modalListPopup;
   private final Rect tempRect;

   public MaterialAutoCompleteTextView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public MaterialAutoCompleteTextView(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.autoCompleteTextViewStyle);
   }

   public MaterialAutoCompleteTextView(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, 0), var2, var3);
      this.tempRect = new Rect();
      var1 = this.getContext();
      TypedArray var5 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.MaterialAutoCompleteTextView, var3, R.style.Widget_AppCompat_AutoCompleteTextView);
      if (var5.hasValue(R.styleable.MaterialAutoCompleteTextView_android_inputType) && var5.getInt(R.styleable.MaterialAutoCompleteTextView_android_inputType, 0) == 0) {
         this.setKeyListener((KeyListener)null);
      }

      this.accessibilityManager = (AccessibilityManager)var1.getSystemService("accessibility");
      ListPopupWindow var4 = new ListPopupWindow(var1);
      this.modalListPopup = var4;
      var4.setModal(true);
      this.modalListPopup.setAnchorView(this);
      this.modalListPopup.setInputMethodMode(2);
      this.modalListPopup.setAdapter(this.getAdapter());
      this.modalListPopup.setOnItemClickListener(new OnItemClickListener() {
         public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
            Object var7;
            if (var3 < 0) {
               var7 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItem();
            } else {
               var7 = MaterialAutoCompleteTextView.this.getAdapter().getItem(var3);
            }

            MaterialAutoCompleteTextView.this.updateText(var7);
            OnItemClickListener var8 = MaterialAutoCompleteTextView.this.getOnItemClickListener();
            if (var8 != null) {
               int var6;
               label17: {
                  if (var2 != null) {
                     var6 = var3;
                     if (var3 >= 0) {
                        break label17;
                     }
                  }

                  var2 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedView();
                  var6 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemPosition();
                  var4 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemId();
               }

               var8.onItemClick(MaterialAutoCompleteTextView.this.modalListPopup.getListView(), var2, var6, var4);
            }

            MaterialAutoCompleteTextView.this.modalListPopup.dismiss();
         }
      });
      var5.recycle();
   }

   private TextInputLayout findTextInputLayoutAncestor() {
      for(ViewParent var1 = this.getParent(); var1 != null; var1 = var1.getParent()) {
         if (var1 instanceof TextInputLayout) {
            return (TextInputLayout)var1;
         }
      }

      return null;
   }

   private int measureContentWidth() {
      ListAdapter var1 = this.getAdapter();
      TextInputLayout var2 = this.findTextInputLayoutAncestor();
      int var3 = 0;
      if (var1 != null && var2 != null) {
         int var4 = MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 0);
         int var5 = MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), 0);
         int var6 = Math.max(0, this.modalListPopup.getSelectedItemPosition());
         int var7 = Math.min(var1.getCount(), var6 + 15);
         int var8 = Math.max(0, var7 - 15);
         View var9 = null;

         int var11;
         for(var6 = 0; var8 < var7; var3 = var11) {
            int var10 = var1.getItemViewType(var8);
            var11 = var3;
            if (var10 != var3) {
               var9 = null;
               var11 = var10;
            }

            var9 = var1.getView(var8, var9, var2);
            if (var9.getLayoutParams() == null) {
               var9.setLayoutParams(new LayoutParams(-2, -2));
            }

            var9.measure(var4, var5);
            var6 = Math.max(var6, var9.getMeasuredWidth());
            ++var8;
         }

         Drawable var12 = this.modalListPopup.getBackground();
         var8 = var6;
         if (var12 != null) {
            var12.getPadding(this.tempRect);
            var8 = var6 + this.tempRect.left + this.tempRect.right;
         }

         return var8 + var2.getEndIconView().getMeasuredWidth();
      } else {
         return 0;
      }
   }

   private <T extends ListAdapter & Filterable> void updateText(Object var1) {
      if (VERSION.SDK_INT >= 17) {
         this.setText(this.convertSelectionToString(var1), false);
      } else {
         ListAdapter var2 = this.getAdapter();
         this.setAdapter((ListAdapter)null);
         this.setText(this.convertSelectionToString(var1));
         this.setAdapter(var2);
      }

   }

   public CharSequence getHint() {
      TextInputLayout var1 = this.findTextInputLayoutAncestor();
      return var1 != null && var1.isProvidingHint() ? var1.getHint() : super.getHint();
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      TextInputLayout var1 = this.findTextInputLayoutAncestor();
      if (var1 != null && var1.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
         this.setHint("");
      }

   }

   protected void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      if (MeasureSpec.getMode(var1) == Integer.MIN_VALUE) {
         this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.measureContentWidth()), MeasureSpec.getSize(var1)), this.getMeasuredHeight());
      }

   }

   public <T extends ListAdapter & Filterable> void setAdapter(T var1) {
      super.setAdapter(var1);
      this.modalListPopup.setAdapter(this.getAdapter());
   }

   public void showDropDown() {
      if (this.getInputType() == 0) {
         AccessibilityManager var1 = this.accessibilityManager;
         if (var1 != null && var1.isTouchExplorationEnabled()) {
            this.modalListPopup.show();
            return;
         }
      }

      super.showDropDown();
   }
}
