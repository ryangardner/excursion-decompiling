package com.google.android.material.chip;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.List;

public class ChipGroup extends FlowLayout {
   private static final int DEF_STYLE_RES;
   private int checkedId;
   private final ChipGroup.CheckedStateTracker checkedStateTracker;
   private int chipSpacingHorizontal;
   private int chipSpacingVertical;
   private ChipGroup.OnCheckedChangeListener onCheckedChangeListener;
   private ChipGroup.PassThroughHierarchyChangeListener passThroughListener;
   private boolean protectFromCheckedChange;
   private boolean selectionRequired;
   private boolean singleSelection;

   static {
      DEF_STYLE_RES = R.style.Widget_MaterialComponents_ChipGroup;
   }

   public ChipGroup(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public ChipGroup(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.chipGroupStyle);
   }

   public ChipGroup(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.checkedStateTracker = new ChipGroup.CheckedStateTracker();
      this.passThroughListener = new ChipGroup.PassThroughHierarchyChangeListener();
      this.checkedId = -1;
      this.protectFromCheckedChange = false;
      TypedArray var4 = ThemeEnforcement.obtainStyledAttributes(this.getContext(), var2, R.styleable.ChipGroup, var3, DEF_STYLE_RES);
      var3 = var4.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacing, 0);
      this.setChipSpacingHorizontal(var4.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingHorizontal, var3));
      this.setChipSpacingVertical(var4.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingVertical, var3));
      this.setSingleLine(var4.getBoolean(R.styleable.ChipGroup_singleLine, false));
      this.setSingleSelection(var4.getBoolean(R.styleable.ChipGroup_singleSelection, false));
      this.setSelectionRequired(var4.getBoolean(R.styleable.ChipGroup_selectionRequired, false));
      var3 = var4.getResourceId(R.styleable.ChipGroup_checkedChip, -1);
      if (var3 != -1) {
         this.checkedId = var3;
      }

      var4.recycle();
      super.setOnHierarchyChangeListener(this.passThroughListener);
      ViewCompat.setImportantForAccessibility(this, 1);
   }

   private int getChipCount() {
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < this.getChildCount(); var2 = var3) {
         var3 = var2;
         if (this.getChildAt(var1) instanceof Chip) {
            var3 = var2 + 1;
         }

         ++var1;
      }

      return var2;
   }

   private void setCheckedId(int var1) {
      this.setCheckedId(var1, true);
   }

   private void setCheckedId(int var1, boolean var2) {
      this.checkedId = var1;
      ChipGroup.OnCheckedChangeListener var3 = this.onCheckedChangeListener;
      if (var3 != null && this.singleSelection && var2) {
         var3.onCheckedChanged(this, var1);
      }

   }

   private void setCheckedStateForView(int var1, boolean var2) {
      View var3 = this.findViewById(var1);
      if (var3 instanceof Chip) {
         this.protectFromCheckedChange = true;
         ((Chip)var3).setChecked(var2);
         this.protectFromCheckedChange = false;
      }

   }

   public void addView(View var1, int var2, android.view.ViewGroup.LayoutParams var3) {
      if (var1 instanceof Chip) {
         Chip var4 = (Chip)var1;
         if (var4.isChecked()) {
            int var5 = this.checkedId;
            if (var5 != -1 && this.singleSelection) {
               this.setCheckedStateForView(var5, false);
            }

            this.setCheckedId(var4.getId());
         }
      }

      super.addView(var1, var2, var3);
   }

   public void check(int var1) {
      int var2 = this.checkedId;
      if (var1 != var2) {
         if (var2 != -1 && this.singleSelection) {
            this.setCheckedStateForView(var2, false);
         }

         if (var1 != -1) {
            this.setCheckedStateForView(var1, true);
         }

         this.setCheckedId(var1);
      }
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      boolean var2;
      if (super.checkLayoutParams(var1) && var1 instanceof ChipGroup.LayoutParams) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void clearCheck() {
      this.protectFromCheckedChange = true;

      for(int var1 = 0; var1 < this.getChildCount(); ++var1) {
         View var2 = this.getChildAt(var1);
         if (var2 instanceof Chip) {
            ((Chip)var2).setChecked(false);
         }
      }

      this.protectFromCheckedChange = false;
      this.setCheckedId(-1);
   }

   protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
      return new ChipGroup.LayoutParams(-2, -2);
   }

   public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new ChipGroup.LayoutParams(this.getContext(), var1);
   }

   protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return new ChipGroup.LayoutParams(var1);
   }

   public int getCheckedChipId() {
      int var1;
      if (this.singleSelection) {
         var1 = this.checkedId;
      } else {
         var1 = -1;
      }

      return var1;
   }

   public List<Integer> getCheckedChipIds() {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < this.getChildCount(); ++var2) {
         View var3 = this.getChildAt(var2);
         if (var3 instanceof Chip && ((Chip)var3).isChecked()) {
            var1.add(var3.getId());
            if (this.singleSelection) {
               return var1;
            }
         }
      }

      return var1;
   }

   public int getChipSpacingHorizontal() {
      return this.chipSpacingHorizontal;
   }

   public int getChipSpacingVertical() {
      return this.chipSpacingVertical;
   }

   int getIndexOfChip(View var1) {
      if (!(var1 instanceof Chip)) {
         return -1;
      } else {
         int var2 = 0;

         int var4;
         for(int var3 = 0; var2 < this.getChildCount(); var3 = var4) {
            var4 = var3;
            if (this.getChildAt(var2) instanceof Chip) {
               if ((Chip)this.getChildAt(var2) == var1) {
                  return var3;
               }

               var4 = var3 + 1;
            }

            ++var2;
         }

         return -1;
      }
   }

   public boolean isSelectionRequired() {
      return this.selectionRequired;
   }

   public boolean isSingleLine() {
      return super.isSingleLine();
   }

   public boolean isSingleSelection() {
      return this.singleSelection;
   }

   protected void onFinishInflate() {
      super.onFinishInflate();
      int var1 = this.checkedId;
      if (var1 != -1) {
         this.setCheckedStateForView(var1, true);
         this.setCheckedId(this.checkedId);
      }

   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      AccessibilityNodeInfoCompat var5 = AccessibilityNodeInfoCompat.wrap(var1);
      int var2;
      if (this.isSingleLine()) {
         var2 = this.getChipCount();
      } else {
         var2 = -1;
      }

      int var3 = this.getRowCount();
      byte var4;
      if (this.isSingleSelection()) {
         var4 = 1;
      } else {
         var4 = 2;
      }

      var5.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(var3, var2, false, var4));
   }

   public void setChipSpacing(int var1) {
      this.setChipSpacingHorizontal(var1);
      this.setChipSpacingVertical(var1);
   }

   public void setChipSpacingHorizontal(int var1) {
      if (this.chipSpacingHorizontal != var1) {
         this.chipSpacingHorizontal = var1;
         this.setItemSpacing(var1);
         this.requestLayout();
      }

   }

   public void setChipSpacingHorizontalResource(int var1) {
      this.setChipSpacingHorizontal(this.getResources().getDimensionPixelOffset(var1));
   }

   public void setChipSpacingResource(int var1) {
      this.setChipSpacing(this.getResources().getDimensionPixelOffset(var1));
   }

   public void setChipSpacingVertical(int var1) {
      if (this.chipSpacingVertical != var1) {
         this.chipSpacingVertical = var1;
         this.setLineSpacing(var1);
         this.requestLayout();
      }

   }

   public void setChipSpacingVerticalResource(int var1) {
      this.setChipSpacingVertical(this.getResources().getDimensionPixelOffset(var1));
   }

   @Deprecated
   public void setDividerDrawableHorizontal(Drawable var1) {
      throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
   }

   @Deprecated
   public void setDividerDrawableVertical(Drawable var1) {
      throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
   }

   @Deprecated
   public void setFlexWrap(int var1) {
      throw new UnsupportedOperationException("Changing flex wrap not allowed. ChipGroup exposes a singleLine attribute instead.");
   }

   public void setOnCheckedChangeListener(ChipGroup.OnCheckedChangeListener var1) {
      this.onCheckedChangeListener = var1;
   }

   public void setOnHierarchyChangeListener(OnHierarchyChangeListener var1) {
      this.passThroughListener.onHierarchyChangeListener = var1;
   }

   public void setSelectionRequired(boolean var1) {
      this.selectionRequired = var1;
   }

   @Deprecated
   public void setShowDividerHorizontal(int var1) {
      throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
   }

   @Deprecated
   public void setShowDividerVertical(int var1) {
      throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
   }

   public void setSingleLine(int var1) {
      this.setSingleLine(this.getResources().getBoolean(var1));
   }

   public void setSingleLine(boolean var1) {
      super.setSingleLine(var1);
   }

   public void setSingleSelection(int var1) {
      this.setSingleSelection(this.getResources().getBoolean(var1));
   }

   public void setSingleSelection(boolean var1) {
      if (this.singleSelection != var1) {
         this.singleSelection = var1;
         this.clearCheck();
      }

   }

   private class CheckedStateTracker implements android.widget.CompoundButton.OnCheckedChangeListener {
      private CheckedStateTracker() {
      }

      // $FF: synthetic method
      CheckedStateTracker(Object var2) {
         this();
      }

      public void onCheckedChanged(CompoundButton var1, boolean var2) {
         if (!ChipGroup.this.protectFromCheckedChange) {
            if (ChipGroup.this.getCheckedChipIds().isEmpty() && ChipGroup.this.selectionRequired) {
               ChipGroup.this.setCheckedStateForView(var1.getId(), true);
               ChipGroup.this.setCheckedId(var1.getId(), false);
            } else {
               int var3 = var1.getId();
               if (var2) {
                  if (ChipGroup.this.checkedId != -1 && ChipGroup.this.checkedId != var3 && ChipGroup.this.singleSelection) {
                     ChipGroup var4 = ChipGroup.this;
                     var4.setCheckedStateForView(var4.checkedId, false);
                  }

                  ChipGroup.this.setCheckedId(var3);
               } else if (ChipGroup.this.checkedId == var3) {
                  ChipGroup.this.setCheckedId(-1);
               }

            }
         }
      }
   }

   public static class LayoutParams extends MarginLayoutParams {
      public LayoutParams(int var1, int var2) {
         super(var1, var2);
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }
   }

   public interface OnCheckedChangeListener {
      void onCheckedChanged(ChipGroup var1, int var2);
   }

   private class PassThroughHierarchyChangeListener implements OnHierarchyChangeListener {
      private OnHierarchyChangeListener onHierarchyChangeListener;

      private PassThroughHierarchyChangeListener() {
      }

      // $FF: synthetic method
      PassThroughHierarchyChangeListener(Object var2) {
         this();
      }

      public void onChildViewAdded(View var1, View var2) {
         if (var1 == ChipGroup.this && var2 instanceof Chip) {
            if (var2.getId() == -1) {
               int var3;
               if (VERSION.SDK_INT >= 17) {
                  var3 = View.generateViewId();
               } else {
                  var3 = var2.hashCode();
               }

               var2.setId(var3);
            }

            ((Chip)var2).setOnCheckedChangeListenerInternal(ChipGroup.this.checkedStateTracker);
         }

         OnHierarchyChangeListener var4 = this.onHierarchyChangeListener;
         if (var4 != null) {
            var4.onChildViewAdded(var1, var2);
         }

      }

      public void onChildViewRemoved(View var1, View var2) {
         if (var1 == ChipGroup.this && var2 instanceof Chip) {
            ((Chip)var2).setOnCheckedChangeListenerInternal((android.widget.CompoundButton.OnCheckedChangeListener)null);
         }

         OnHierarchyChangeListener var3 = this.onHierarchyChangeListener;
         if (var3 != null) {
            var3.onChildViewRemoved(var1, var2);
         }

      }
   }
}
