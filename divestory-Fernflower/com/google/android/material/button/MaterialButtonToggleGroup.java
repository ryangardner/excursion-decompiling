package com.google.android.material.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeMap;

public class MaterialButtonToggleGroup extends LinearLayout {
   private static final int DEF_STYLE_RES;
   private static final String LOG_TAG = MaterialButtonToggleGroup.class.getSimpleName();
   private int checkedId;
   private final MaterialButtonToggleGroup.CheckedStateTracker checkedStateTracker;
   private Integer[] childOrder;
   private final Comparator<MaterialButton> childOrderComparator;
   private final LinkedHashSet<MaterialButtonToggleGroup.OnButtonCheckedListener> onButtonCheckedListeners;
   private final List<MaterialButtonToggleGroup.CornerData> originalCornerData;
   private final MaterialButtonToggleGroup.PressedStateTracker pressedStateTracker;
   private boolean selectionRequired;
   private boolean singleSelection;
   private boolean skipCheckedStateTracker;

   static {
      DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialButtonToggleGroup;
   }

   public MaterialButtonToggleGroup(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public MaterialButtonToggleGroup(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.materialButtonToggleGroupStyle);
   }

   public MaterialButtonToggleGroup(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.originalCornerData = new ArrayList();
      this.checkedStateTracker = new MaterialButtonToggleGroup.CheckedStateTracker();
      this.pressedStateTracker = new MaterialButtonToggleGroup.PressedStateTracker();
      this.onButtonCheckedListeners = new LinkedHashSet();
      this.childOrderComparator = new Comparator<MaterialButton>() {
         public int compare(MaterialButton var1, MaterialButton var2) {
            int var3 = Boolean.valueOf(var1.isChecked()).compareTo(var2.isChecked());
            if (var3 != 0) {
               return var3;
            } else {
               var3 = Boolean.valueOf(var1.isPressed()).compareTo(var2.isPressed());
               return var3 != 0 ? var3 : Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild(var1)).compareTo(MaterialButtonToggleGroup.this.indexOfChild(var2));
            }
         }
      };
      this.skipCheckedStateTracker = false;
      TypedArray var4 = ThemeEnforcement.obtainStyledAttributes(this.getContext(), var2, R.styleable.MaterialButtonToggleGroup, var3, DEF_STYLE_RES);
      this.setSingleSelection(var4.getBoolean(R.styleable.MaterialButtonToggleGroup_singleSelection, false));
      this.checkedId = var4.getResourceId(R.styleable.MaterialButtonToggleGroup_checkedButton, -1);
      this.selectionRequired = var4.getBoolean(R.styleable.MaterialButtonToggleGroup_selectionRequired, false);
      this.setChildrenDrawingOrderEnabled(true);
      var4.recycle();
      ViewCompat.setImportantForAccessibility(this, 1);
   }

   private void adjustChildMarginsAndUpdateLayout() {
      int var1 = this.getFirstVisibleChildIndex();
      if (var1 != -1) {
         for(int var2 = var1 + 1; var2 < this.getChildCount(); ++var2) {
            MaterialButton var3 = this.getChildButton(var2);
            MaterialButton var4 = this.getChildButton(var2 - 1);
            int var5 = Math.min(var3.getStrokeWidth(), var4.getStrokeWidth());
            LayoutParams var6 = this.buildLayoutParams(var3);
            if (this.getOrientation() == 0) {
               MarginLayoutParamsCompat.setMarginEnd(var6, 0);
               MarginLayoutParamsCompat.setMarginStart(var6, -var5);
            } else {
               var6.bottomMargin = 0;
               var6.topMargin = -var5;
            }

            var3.setLayoutParams(var6);
         }

         this.resetChildMargins(var1);
      }
   }

   private LayoutParams buildLayoutParams(View var1) {
      android.view.ViewGroup.LayoutParams var2 = var1.getLayoutParams();
      return var2 instanceof LayoutParams ? (LayoutParams)var2 : new LayoutParams(var2.width, var2.height);
   }

   private void checkForced(int var1) {
      this.setCheckedStateForView(var1, true);
      this.updateCheckedStates(var1, true);
      this.setCheckedId(var1);
   }

   private void dispatchOnButtonChecked(int var1, boolean var2) {
      Iterator var3 = this.onButtonCheckedListeners.iterator();

      while(var3.hasNext()) {
         ((MaterialButtonToggleGroup.OnButtonCheckedListener)var3.next()).onButtonChecked(this, var1, var2);
      }

   }

   private MaterialButton getChildButton(int var1) {
      return (MaterialButton)this.getChildAt(var1);
   }

   private int getFirstVisibleChildIndex() {
      int var1 = this.getChildCount();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (this.isChildVisible(var2)) {
            return var2;
         }
      }

      return -1;
   }

   private int getIndexWithinVisibleButtons(View var1) {
      if (!(var1 instanceof MaterialButton)) {
         return -1;
      } else {
         int var2 = 0;

         int var4;
         for(int var3 = 0; var2 < this.getChildCount(); var3 = var4) {
            if (this.getChildAt(var2) == var1) {
               return var3;
            }

            var4 = var3;
            if (this.getChildAt(var2) instanceof MaterialButton) {
               var4 = var3;
               if (this.isChildVisible(var2)) {
                  var4 = var3 + 1;
               }
            }

            ++var2;
         }

         return -1;
      }
   }

   private int getLastVisibleChildIndex() {
      for(int var1 = this.getChildCount() - 1; var1 >= 0; --var1) {
         if (this.isChildVisible(var1)) {
            return var1;
         }
      }

      return -1;
   }

   private MaterialButtonToggleGroup.CornerData getNewCornerData(int var1, int var2, int var3) {
      MaterialButtonToggleGroup.CornerData var4 = (MaterialButtonToggleGroup.CornerData)this.originalCornerData.get(var1);
      if (var2 == var3) {
         return var4;
      } else {
         boolean var5;
         if (this.getOrientation() == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var1 == var2) {
            if (var5) {
               var4 = MaterialButtonToggleGroup.CornerData.start(var4, this);
            } else {
               var4 = MaterialButtonToggleGroup.CornerData.top(var4);
            }

            return var4;
         } else if (var1 == var3) {
            if (var5) {
               var4 = MaterialButtonToggleGroup.CornerData.end(var4, this);
            } else {
               var4 = MaterialButtonToggleGroup.CornerData.bottom(var4);
            }

            return var4;
         } else {
            return null;
         }
      }
   }

   private int getVisibleButtonCount() {
      int var1 = 0;

      int var2;
      int var3;
      for(var2 = 0; var1 < this.getChildCount(); var2 = var3) {
         var3 = var2;
         if (this.getChildAt(var1) instanceof MaterialButton) {
            var3 = var2;
            if (this.isChildVisible(var1)) {
               var3 = var2 + 1;
            }
         }

         ++var1;
      }

      return var2;
   }

   private boolean isChildVisible(int var1) {
      boolean var2;
      if (this.getChildAt(var1).getVisibility() != 8) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void resetChildMargins(int var1) {
      if (this.getChildCount() != 0 && var1 != -1) {
         LayoutParams var2 = (LayoutParams)this.getChildButton(var1).getLayoutParams();
         if (this.getOrientation() == 1) {
            var2.topMargin = 0;
            var2.bottomMargin = 0;
            return;
         }

         MarginLayoutParamsCompat.setMarginEnd(var2, 0);
         MarginLayoutParamsCompat.setMarginStart(var2, 0);
         var2.leftMargin = 0;
         var2.rightMargin = 0;
      }

   }

   private void setCheckedId(int var1) {
      this.checkedId = var1;
      this.dispatchOnButtonChecked(var1, true);
   }

   private void setCheckedStateForView(int var1, boolean var2) {
      View var3 = this.findViewById(var1);
      if (var3 instanceof MaterialButton) {
         this.skipCheckedStateTracker = true;
         ((MaterialButton)var3).setChecked(var2);
         this.skipCheckedStateTracker = false;
      }

   }

   private void setGeneratedIdIfNeeded(MaterialButton var1) {
      if (var1.getId() == -1) {
         var1.setId(ViewCompat.generateViewId());
      }

   }

   private void setupButtonChild(MaterialButton var1) {
      var1.setMaxLines(1);
      var1.setEllipsize(TruncateAt.END);
      var1.setCheckable(true);
      var1.addOnCheckedChangeListener(this.checkedStateTracker);
      var1.setOnPressedChangeListenerInternal(this.pressedStateTracker);
      var1.setShouldDrawSurfaceColorStroke(true);
   }

   private static void updateBuilderWithCornerData(ShapeAppearanceModel.Builder var0, MaterialButtonToggleGroup.CornerData var1) {
      if (var1 == null) {
         var0.setAllCornerSizes(0.0F);
      } else {
         var0.setTopLeftCornerSize(var1.topLeft).setBottomLeftCornerSize(var1.bottomLeft).setTopRightCornerSize(var1.topRight).setBottomRightCornerSize(var1.bottomRight);
      }
   }

   private boolean updateCheckedStates(int var1, boolean var2) {
      List var3 = this.getCheckedButtonIds();
      if (this.selectionRequired && var3.isEmpty()) {
         this.setCheckedStateForView(var1, true);
         this.checkedId = var1;
         return false;
      } else {
         if (var2 && this.singleSelection) {
            var3.remove(var1);
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
               var1 = (Integer)var4.next();
               this.setCheckedStateForView(var1, false);
               this.dispatchOnButtonChecked(var1, false);
            }
         }

         return true;
      }
   }

   private void updateChildOrder() {
      TreeMap var1 = new TreeMap(this.childOrderComparator);
      int var2 = this.getChildCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.put(this.getChildButton(var3), var3);
      }

      this.childOrder = (Integer[])var1.values().toArray(new Integer[0]);
   }

   public void addOnButtonCheckedListener(MaterialButtonToggleGroup.OnButtonCheckedListener var1) {
      this.onButtonCheckedListeners.add(var1);
   }

   public void addView(View var1, int var2, android.view.ViewGroup.LayoutParams var3) {
      if (!(var1 instanceof MaterialButton)) {
         Log.e(LOG_TAG, "Child views must be of type MaterialButton.");
      } else {
         super.addView(var1, var2, var3);
         MaterialButton var4 = (MaterialButton)var1;
         this.setGeneratedIdIfNeeded(var4);
         this.setupButtonChild(var4);
         if (var4.isChecked()) {
            this.updateCheckedStates(var4.getId(), true);
            this.setCheckedId(var4.getId());
         }

         ShapeAppearanceModel var5 = var4.getShapeAppearanceModel();
         this.originalCornerData.add(new MaterialButtonToggleGroup.CornerData(var5.getTopLeftCornerSize(), var5.getBottomLeftCornerSize(), var5.getTopRightCornerSize(), var5.getBottomRightCornerSize()));
         ViewCompat.setAccessibilityDelegate(var4, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2) {
               super.onInitializeAccessibilityNodeInfo(var1, var2);
               var2.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, MaterialButtonToggleGroup.this.getIndexWithinVisibleButtons(var1), 1, false, ((MaterialButton)var1).isChecked()));
            }
         });
      }
   }

   public void check(int var1) {
      if (var1 != this.checkedId) {
         this.checkForced(var1);
      }
   }

   public void clearChecked() {
      this.skipCheckedStateTracker = true;

      for(int var1 = 0; var1 < this.getChildCount(); ++var1) {
         MaterialButton var2 = this.getChildButton(var1);
         var2.setChecked(false);
         this.dispatchOnButtonChecked(var2.getId(), false);
      }

      this.skipCheckedStateTracker = false;
      this.setCheckedId(-1);
   }

   public void clearOnButtonCheckedListeners() {
      this.onButtonCheckedListeners.clear();
   }

   protected void dispatchDraw(Canvas var1) {
      this.updateChildOrder();
      super.dispatchDraw(var1);
   }

   public CharSequence getAccessibilityClassName() {
      return MaterialButtonToggleGroup.class.getName();
   }

   public int getCheckedButtonId() {
      int var1;
      if (this.singleSelection) {
         var1 = this.checkedId;
      } else {
         var1 = -1;
      }

      return var1;
   }

   public List<Integer> getCheckedButtonIds() {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < this.getChildCount(); ++var2) {
         MaterialButton var3 = this.getChildButton(var2);
         if (var3.isChecked()) {
            var1.add(var3.getId());
         }
      }

      return var1;
   }

   protected int getChildDrawingOrder(int var1, int var2) {
      Integer[] var3 = this.childOrder;
      if (var3 != null && var2 < var3.length) {
         return var3[var2];
      } else {
         Log.w(LOG_TAG, "Child order wasn't updated");
         return var2;
      }
   }

   public boolean isSelectionRequired() {
      return this.selectionRequired;
   }

   public boolean isSingleSelection() {
      return this.singleSelection;
   }

   protected void onFinishInflate() {
      super.onFinishInflate();
      int var1 = this.checkedId;
      if (var1 != -1) {
         this.checkForced(var1);
      }

   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      AccessibilityNodeInfoCompat var4 = AccessibilityNodeInfoCompat.wrap(var1);
      int var2 = this.getVisibleButtonCount();
      byte var3;
      if (this.isSingleSelection()) {
         var3 = 1;
      } else {
         var3 = 2;
      }

      var4.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, var2, false, var3));
   }

   protected void onMeasure(int var1, int var2) {
      this.updateChildShapes();
      this.adjustChildMarginsAndUpdateLayout();
      super.onMeasure(var1, var2);
   }

   public void onViewRemoved(View var1) {
      super.onViewRemoved(var1);
      if (var1 instanceof MaterialButton) {
         MaterialButton var2 = (MaterialButton)var1;
         var2.removeOnCheckedChangeListener(this.checkedStateTracker);
         var2.setOnPressedChangeListenerInternal((MaterialButton.OnPressedChangeListener)null);
      }

      int var3 = this.indexOfChild(var1);
      if (var3 >= 0) {
         this.originalCornerData.remove(var3);
      }

      this.updateChildShapes();
      this.adjustChildMarginsAndUpdateLayout();
   }

   public void removeOnButtonCheckedListener(MaterialButtonToggleGroup.OnButtonCheckedListener var1) {
      this.onButtonCheckedListeners.remove(var1);
   }

   public void setSelectionRequired(boolean var1) {
      this.selectionRequired = var1;
   }

   public void setSingleSelection(int var1) {
      this.setSingleSelection(this.getResources().getBoolean(var1));
   }

   public void setSingleSelection(boolean var1) {
      if (this.singleSelection != var1) {
         this.singleSelection = var1;
         this.clearChecked();
      }

   }

   public void uncheck(int var1) {
      this.setCheckedStateForView(var1, false);
      this.updateCheckedStates(var1, false);
      this.checkedId = -1;
      this.dispatchOnButtonChecked(var1, false);
   }

   void updateChildShapes() {
      int var1 = this.getChildCount();
      int var2 = this.getFirstVisibleChildIndex();
      int var3 = this.getLastVisibleChildIndex();

      for(int var4 = 0; var4 < var1; ++var4) {
         MaterialButton var5 = this.getChildButton(var4);
         if (var5.getVisibility() != 8) {
            ShapeAppearanceModel.Builder var6 = var5.getShapeAppearanceModel().toBuilder();
            updateBuilderWithCornerData(var6, this.getNewCornerData(var4, var2, var3));
            var5.setShapeAppearanceModel(var6.build());
         }
      }

   }

   private class CheckedStateTracker implements MaterialButton.OnCheckedChangeListener {
      private CheckedStateTracker() {
      }

      // $FF: synthetic method
      CheckedStateTracker(Object var2) {
         this();
      }

      public void onCheckedChanged(MaterialButton var1, boolean var2) {
         if (!MaterialButtonToggleGroup.this.skipCheckedStateTracker) {
            if (MaterialButtonToggleGroup.this.singleSelection) {
               MaterialButtonToggleGroup var3 = MaterialButtonToggleGroup.this;
               int var4;
               if (var2) {
                  var4 = var1.getId();
               } else {
                  var4 = -1;
               }

               var3.checkedId = var4;
            }

            if (MaterialButtonToggleGroup.this.updateCheckedStates(var1.getId(), var2)) {
               MaterialButtonToggleGroup.this.dispatchOnButtonChecked(var1.getId(), var1.isChecked());
            }

            MaterialButtonToggleGroup.this.invalidate();
         }
      }
   }

   private static class CornerData {
      private static final CornerSize noCorner = new AbsoluteCornerSize(0.0F);
      CornerSize bottomLeft;
      CornerSize bottomRight;
      CornerSize topLeft;
      CornerSize topRight;

      CornerData(CornerSize var1, CornerSize var2, CornerSize var3, CornerSize var4) {
         this.topLeft = var1;
         this.topRight = var3;
         this.bottomRight = var4;
         this.bottomLeft = var2;
      }

      public static MaterialButtonToggleGroup.CornerData bottom(MaterialButtonToggleGroup.CornerData var0) {
         CornerSize var1 = noCorner;
         return new MaterialButtonToggleGroup.CornerData(var1, var0.bottomLeft, var1, var0.bottomRight);
      }

      public static MaterialButtonToggleGroup.CornerData end(MaterialButtonToggleGroup.CornerData var0, View var1) {
         if (ViewUtils.isLayoutRtl(var1)) {
            var0 = left(var0);
         } else {
            var0 = right(var0);
         }

         return var0;
      }

      public static MaterialButtonToggleGroup.CornerData left(MaterialButtonToggleGroup.CornerData var0) {
         CornerSize var1 = var0.topLeft;
         CornerSize var2 = var0.bottomLeft;
         CornerSize var3 = noCorner;
         return new MaterialButtonToggleGroup.CornerData(var1, var2, var3, var3);
      }

      public static MaterialButtonToggleGroup.CornerData right(MaterialButtonToggleGroup.CornerData var0) {
         CornerSize var1 = noCorner;
         return new MaterialButtonToggleGroup.CornerData(var1, var1, var0.topRight, var0.bottomRight);
      }

      public static MaterialButtonToggleGroup.CornerData start(MaterialButtonToggleGroup.CornerData var0, View var1) {
         if (ViewUtils.isLayoutRtl(var1)) {
            var0 = right(var0);
         } else {
            var0 = left(var0);
         }

         return var0;
      }

      public static MaterialButtonToggleGroup.CornerData top(MaterialButtonToggleGroup.CornerData var0) {
         CornerSize var1 = var0.topLeft;
         CornerSize var2 = noCorner;
         return new MaterialButtonToggleGroup.CornerData(var1, var2, var0.topRight, var2);
      }
   }

   public interface OnButtonCheckedListener {
      void onButtonChecked(MaterialButtonToggleGroup var1, int var2, boolean var3);
   }

   private class PressedStateTracker implements MaterialButton.OnPressedChangeListener {
      private PressedStateTracker() {
      }

      // $FF: synthetic method
      PressedStateTracker(Object var2) {
         this();
      }

      public void onPressedChanged(MaterialButton var1, boolean var2) {
         MaterialButtonToggleGroup.this.invalidate();
      }
   }
}
