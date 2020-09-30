package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import java.util.HashSet;

public class BottomNavigationMenuView extends ViewGroup implements MenuView {
   private static final long ACTIVE_ANIMATION_DURATION_MS = 115L;
   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   private static final int[] DISABLED_STATE_SET = new int[]{-16842910};
   private static final int ITEM_POOL_SIZE = 5;
   private final int activeItemMaxWidth;
   private final int activeItemMinWidth;
   private SparseArray<BadgeDrawable> badgeDrawables;
   private BottomNavigationItemView[] buttons;
   private final int inactiveItemMaxWidth;
   private final int inactiveItemMinWidth;
   private Drawable itemBackground;
   private int itemBackgroundRes;
   private final int itemHeight;
   private boolean itemHorizontalTranslationEnabled;
   private int itemIconSize;
   private ColorStateList itemIconTint;
   private final Pools.Pool<BottomNavigationItemView> itemPool;
   private int itemTextAppearanceActive;
   private int itemTextAppearanceInactive;
   private final ColorStateList itemTextColorDefault;
   private ColorStateList itemTextColorFromUser;
   private int labelVisibilityMode;
   private MenuBuilder menu;
   private final OnClickListener onClickListener;
   private BottomNavigationPresenter presenter;
   private int selectedItemId;
   private int selectedItemPosition;
   private final TransitionSet set;
   private int[] tempChildWidths;

   public BottomNavigationMenuView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public BottomNavigationMenuView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.itemPool = new Pools.SynchronizedPool(5);
      this.selectedItemId = 0;
      this.selectedItemPosition = 0;
      this.badgeDrawables = new SparseArray(5);
      Resources var3 = this.getResources();
      this.inactiveItemMaxWidth = var3.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
      this.inactiveItemMinWidth = var3.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
      this.activeItemMaxWidth = var3.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
      this.activeItemMinWidth = var3.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_min_width);
      this.itemHeight = var3.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
      this.itemTextColorDefault = this.createDefaultColorStateList(16842808);
      AutoTransition var4 = new AutoTransition();
      this.set = var4;
      var4.setOrdering(0);
      this.set.setDuration(115L);
      this.set.setInterpolator(new FastOutSlowInInterpolator());
      this.set.addTransition(new TextScale());
      this.onClickListener = new OnClickListener() {
         public void onClick(View var1) {
            MenuItemImpl var2 = ((BottomNavigationItemView)var1).getItemData();
            if (!BottomNavigationMenuView.this.menu.performItemAction(var2, BottomNavigationMenuView.this.presenter, 0)) {
               var2.setChecked(true);
            }

         }
      };
      this.tempChildWidths = new int[5];
      ViewCompat.setImportantForAccessibility(this, 1);
   }

   private BottomNavigationItemView getNewItem() {
      BottomNavigationItemView var1 = (BottomNavigationItemView)this.itemPool.acquire();
      BottomNavigationItemView var2 = var1;
      if (var1 == null) {
         var2 = new BottomNavigationItemView(this.getContext());
      }

      return var2;
   }

   private boolean isShifting(int var1, int var2) {
      boolean var3 = true;
      if (var1 == -1) {
         if (var2 > 3) {
            return var3;
         }
      } else if (var1 == 0) {
         return var3;
      }

      var3 = false;
      return var3;
   }

   private boolean isValidId(int var1) {
      boolean var2;
      if (var1 != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void removeUnusedBadges() {
      HashSet var1 = new HashSet();
      byte var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var2;
         if (var3 >= this.menu.size()) {
            for(; var4 < this.badgeDrawables.size(); ++var4) {
               var3 = this.badgeDrawables.keyAt(var4);
               if (!var1.contains(var3)) {
                  this.badgeDrawables.delete(var3);
               }
            }

            return;
         }

         var1.add(this.menu.getItem(var3).getItemId());
         ++var3;
      }
   }

   private void setBadgeIfNeeded(BottomNavigationItemView var1) {
      int var2 = var1.getId();
      if (this.isValidId(var2)) {
         BadgeDrawable var3 = (BadgeDrawable)this.badgeDrawables.get(var2);
         if (var3 != null) {
            var1.setBadge(var3);
         }

      }
   }

   private void validateMenuItemId(int var1) {
      if (!this.isValidId(var1)) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" is not a valid view id");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void buildMenuView() {
      this.removeAllViews();
      BottomNavigationItemView[] var1 = this.buttons;
      int var3;
      BottomNavigationItemView var4;
      if (var1 != null) {
         int var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            var4 = var1[var3];
            if (var4 != null) {
               this.itemPool.release(var4);
               var4.removeBadge();
            }
         }
      }

      if (this.menu.size() == 0) {
         this.selectedItemId = 0;
         this.selectedItemPosition = 0;
         this.buttons = null;
      } else {
         this.removeUnusedBadges();
         this.buttons = new BottomNavigationItemView[this.menu.size()];
         boolean var5 = this.isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());

         for(var3 = 0; var3 < this.menu.size(); ++var3) {
            this.presenter.setUpdateSuspended(true);
            this.menu.getItem(var3).setCheckable(true);
            this.presenter.setUpdateSuspended(false);
            var4 = this.getNewItem();
            this.buttons[var3] = var4;
            var4.setIconTintList(this.itemIconTint);
            var4.setIconSize(this.itemIconSize);
            var4.setTextColor(this.itemTextColorDefault);
            var4.setTextAppearanceInactive(this.itemTextAppearanceInactive);
            var4.setTextAppearanceActive(this.itemTextAppearanceActive);
            var4.setTextColor(this.itemTextColorFromUser);
            Drawable var6 = this.itemBackground;
            if (var6 != null) {
               var4.setItemBackground(var6);
            } else {
               var4.setItemBackground(this.itemBackgroundRes);
            }

            var4.setShifting(var5);
            var4.setLabelVisibilityMode(this.labelVisibilityMode);
            var4.initialize((MenuItemImpl)this.menu.getItem(var3), 0);
            var4.setItemPosition(var3);
            var4.setOnClickListener(this.onClickListener);
            if (this.selectedItemId != 0 && this.menu.getItem(var3).getItemId() == this.selectedItemId) {
               this.selectedItemPosition = var3;
            }

            this.setBadgeIfNeeded(var4);
            this.addView(var4);
         }

         var3 = Math.min(this.menu.size() - 1, this.selectedItemPosition);
         this.selectedItemPosition = var3;
         this.menu.getItem(var3).setChecked(true);
      }
   }

   public ColorStateList createDefaultColorStateList(int var1) {
      TypedValue var2 = new TypedValue();
      if (!this.getContext().getTheme().resolveAttribute(var1, var2, true)) {
         return null;
      } else {
         ColorStateList var3 = AppCompatResources.getColorStateList(this.getContext(), var2.resourceId);
         if (!this.getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, var2, true)) {
            return null;
         } else {
            var1 = var2.data;
            int var4 = var3.getDefaultColor();
            int[] var5 = DISABLED_STATE_SET;
            int[] var8 = CHECKED_STATE_SET;
            int[] var6 = EMPTY_STATE_SET;
            int var7 = var3.getColorForState(DISABLED_STATE_SET, var4);
            return new ColorStateList(new int[][]{var5, var8, var6}, new int[]{var7, var1, var4});
         }
      }
   }

   BottomNavigationItemView findItemView(int var1) {
      this.validateMenuItemId(var1);
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            BottomNavigationItemView var5 = var2[var4];
            if (var5.getId() == var1) {
               return var5;
            }
         }
      }

      return null;
   }

   BadgeDrawable getBadge(int var1) {
      return (BadgeDrawable)this.badgeDrawables.get(var1);
   }

   SparseArray<BadgeDrawable> getBadgeDrawables() {
      return this.badgeDrawables;
   }

   public ColorStateList getIconTintList() {
      return this.itemIconTint;
   }

   public Drawable getItemBackground() {
      BottomNavigationItemView[] var1 = this.buttons;
      return var1 != null && var1.length > 0 ? var1[0].getBackground() : this.itemBackground;
   }

   @Deprecated
   public int getItemBackgroundRes() {
      return this.itemBackgroundRes;
   }

   public int getItemIconSize() {
      return this.itemIconSize;
   }

   public int getItemTextAppearanceActive() {
      return this.itemTextAppearanceActive;
   }

   public int getItemTextAppearanceInactive() {
      return this.itemTextAppearanceInactive;
   }

   public ColorStateList getItemTextColor() {
      return this.itemTextColorFromUser;
   }

   public int getLabelVisibilityMode() {
      return this.labelVisibilityMode;
   }

   BadgeDrawable getOrCreateBadge(int var1) {
      this.validateMenuItemId(var1);
      BadgeDrawable var2 = (BadgeDrawable)this.badgeDrawables.get(var1);
      BadgeDrawable var3 = var2;
      if (var2 == null) {
         var3 = BadgeDrawable.create(this.getContext());
         this.badgeDrawables.put(var1, var3);
      }

      BottomNavigationItemView var4 = this.findItemView(var1);
      if (var4 != null) {
         var4.setBadge(var3);
      }

      return var3;
   }

   public int getSelectedItemId() {
      return this.selectedItemId;
   }

   public int getWindowAnimations() {
      return 0;
   }

   public void initialize(MenuBuilder var1) {
      this.menu = var1;
   }

   public boolean isItemHorizontalTranslationEnabled() {
      return this.itemHorizontalTranslationEnabled;
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      AccessibilityNodeInfoCompat.wrap(var1).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.menu.getVisibleItems().size(), false, 1));
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = this.getChildCount();
      int var7 = var5 - var3;
      var3 = 0;

      for(var5 = 0; var3 < var6; ++var3) {
         View var8 = this.getChildAt(var3);
         if (var8.getVisibility() != 8) {
            if (ViewCompat.getLayoutDirection(this) == 1) {
               int var9 = var4 - var2 - var5;
               var8.layout(var9 - var8.getMeasuredWidth(), 0, var9, var7);
            } else {
               var8.layout(var5, 0, var8.getMeasuredWidth() + var5, var7);
            }

            var5 += var8.getMeasuredWidth();
         }
      }

   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      int var4 = this.menu.getVisibleItems().size();
      int var5 = this.getChildCount();
      int var6 = MeasureSpec.makeMeasureSpec(this.itemHeight, 1073741824);
      int var10002;
      int[] var7;
      int var8;
      View var10;
      if (this.isShifting(this.labelVisibilityMode, var4) && this.itemHorizontalTranslationEnabled) {
         var10 = this.getChildAt(this.selectedItemPosition);
         var2 = this.activeItemMinWidth;
         var1 = var2;
         if (var10.getVisibility() != 8) {
            var10.measure(MeasureSpec.makeMeasureSpec(this.activeItemMaxWidth, Integer.MIN_VALUE), var6);
            var1 = Math.max(var2, var10.getMeasuredWidth());
         }

         byte var9;
         if (var10.getVisibility() != 8) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         var2 = var4 - var9;
         var8 = Math.min(var3 - this.inactiveItemMinWidth * var2, Math.min(var1, this.activeItemMaxWidth));
         var4 = var3 - var8;
         if (var2 == 0) {
            var1 = 1;
         } else {
            var1 = var2;
         }

         var3 = Math.min(var4 / var1, this.inactiveItemMaxWidth);
         var2 = var4 - var2 * var3;

         for(var1 = 0; var1 < var5; var2 = var4) {
            if (this.getChildAt(var1).getVisibility() != 8) {
               var7 = this.tempChildWidths;
               if (var1 == this.selectedItemPosition) {
                  var4 = var8;
               } else {
                  var4 = var3;
               }

               var7[var1] = var4;
               var4 = var2;
               if (var2 > 0) {
                  var7 = this.tempChildWidths;
                  var10002 = var7[var1]++;
                  var4 = var2 - 1;
               }
            } else {
               this.tempChildWidths[var1] = 0;
               var4 = var2;
            }

            ++var1;
         }
      } else {
         if (var4 == 0) {
            var1 = 1;
         } else {
            var1 = var4;
         }

         var8 = Math.min(var3 / var1, this.activeItemMaxWidth);
         var2 = var3 - var4 * var8;

         for(var1 = 0; var1 < var5; var2 = var4) {
            if (this.getChildAt(var1).getVisibility() != 8) {
               var7 = this.tempChildWidths;
               var7[var1] = var8;
               var4 = var2;
               if (var2 > 0) {
                  var10002 = var7[var1]++;
                  var4 = var2 - 1;
               }
            } else {
               this.tempChildWidths[var1] = 0;
               var4 = var2;
            }

            ++var1;
         }
      }

      var1 = 0;

      for(var2 = 0; var1 < var5; ++var1) {
         var10 = this.getChildAt(var1);
         if (var10.getVisibility() != 8) {
            var10.measure(MeasureSpec.makeMeasureSpec(this.tempChildWidths[var1], 1073741824), var6);
            var10.getLayoutParams().width = var10.getMeasuredWidth();
            var2 += var10.getMeasuredWidth();
         }
      }

      this.setMeasuredDimension(View.resolveSizeAndState(var2, MeasureSpec.makeMeasureSpec(var2, 1073741824), 0), View.resolveSizeAndState(this.itemHeight, var6, 0));
   }

   void removeBadge(int var1) {
      this.validateMenuItemId(var1);
      BadgeDrawable var2 = (BadgeDrawable)this.badgeDrawables.get(var1);
      BottomNavigationItemView var3 = this.findItemView(var1);
      if (var3 != null) {
         var3.removeBadge();
      }

      if (var2 != null) {
         this.badgeDrawables.remove(var1);
      }

   }

   void setBadgeDrawables(SparseArray<BadgeDrawable> var1) {
      this.badgeDrawables = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            BottomNavigationItemView var5 = var2[var4];
            var5.setBadge((BadgeDrawable)var1.get(var5.getId()));
         }
      }

   }

   public void setIconTintList(ColorStateList var1) {
      this.itemIconTint = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2[var4].setIconTintList(var1);
         }
      }

   }

   public void setItemBackground(Drawable var1) {
      this.itemBackground = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2[var4].setItemBackground(var1);
         }
      }

   }

   public void setItemBackgroundRes(int var1) {
      this.itemBackgroundRes = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2[var4].setItemBackground(var1);
         }
      }

   }

   public void setItemHorizontalTranslationEnabled(boolean var1) {
      this.itemHorizontalTranslationEnabled = var1;
   }

   public void setItemIconSize(int var1) {
      this.itemIconSize = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2[var4].setIconSize(var1);
         }
      }

   }

   public void setItemTextAppearanceActive(int var1) {
      this.itemTextAppearanceActive = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            BottomNavigationItemView var5 = var2[var4];
            var5.setTextAppearanceActive(var1);
            ColorStateList var6 = this.itemTextColorFromUser;
            if (var6 != null) {
               var5.setTextColor(var6);
            }
         }
      }

   }

   public void setItemTextAppearanceInactive(int var1) {
      this.itemTextAppearanceInactive = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            BottomNavigationItemView var5 = var2[var4];
            var5.setTextAppearanceInactive(var1);
            ColorStateList var6 = this.itemTextColorFromUser;
            if (var6 != null) {
               var5.setTextColor(var6);
            }
         }
      }

   }

   public void setItemTextColor(ColorStateList var1) {
      this.itemTextColorFromUser = var1;
      BottomNavigationItemView[] var2 = this.buttons;
      if (var2 != null) {
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var2[var4].setTextColor(var1);
         }
      }

   }

   public void setLabelVisibilityMode(int var1) {
      this.labelVisibilityMode = var1;
   }

   public void setPresenter(BottomNavigationPresenter var1) {
      this.presenter = var1;
   }

   void tryRestoreSelectedItemId(int var1) {
      int var2 = this.menu.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         MenuItem var4 = this.menu.getItem(var3);
         if (var1 == var4.getItemId()) {
            this.selectedItemId = var1;
            this.selectedItemPosition = var3;
            var4.setChecked(true);
            break;
         }
      }

   }

   public void updateMenuView() {
      MenuBuilder var1 = this.menu;
      if (var1 != null && this.buttons != null) {
         int var2 = var1.size();
         if (var2 != this.buttons.length) {
            this.buildMenuView();
            return;
         }

         int var3 = this.selectedItemId;

         int var4;
         for(var4 = 0; var4 < var2; ++var4) {
            MenuItem var6 = this.menu.getItem(var4);
            if (var6.isChecked()) {
               this.selectedItemId = var6.getItemId();
               this.selectedItemPosition = var4;
            }
         }

         if (var3 != this.selectedItemId) {
            TransitionManager.beginDelayedTransition(this, this.set);
         }

         boolean var5 = this.isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());

         for(var4 = 0; var4 < var2; ++var4) {
            this.presenter.setUpdateSuspended(true);
            this.buttons[var4].setLabelVisibilityMode(this.labelVisibilityMode);
            this.buttons[var4].setShifting(var5);
            this.buttons[var4].initialize((MenuItemImpl)this.menu.getItem(var4), 0);
            this.presenter.setUpdateSuspended(false);
         }
      }

   }
}
