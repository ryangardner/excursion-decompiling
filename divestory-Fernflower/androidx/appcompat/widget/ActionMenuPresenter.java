package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import androidx.appcompat.R;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopup;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ActionProvider;
import java.util.ArrayList;

class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
   private static final String TAG = "ActionMenuPresenter";
   private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
   ActionMenuPresenter.ActionButtonSubmenu mActionButtonPopup;
   private int mActionItemWidthLimit;
   private boolean mExpandedActionViewsExclusive;
   private int mMaxItems;
   private boolean mMaxItemsSet;
   private int mMinCellSize;
   int mOpenSubMenuId;
   ActionMenuPresenter.OverflowMenuButton mOverflowButton;
   ActionMenuPresenter.OverflowPopup mOverflowPopup;
   private Drawable mPendingOverflowIcon;
   private boolean mPendingOverflowIconSet;
   private ActionMenuPresenter.ActionMenuPopupCallback mPopupCallback;
   final ActionMenuPresenter.PopupPresenterCallback mPopupPresenterCallback = new ActionMenuPresenter.PopupPresenterCallback();
   ActionMenuPresenter.OpenOverflowRunnable mPostedOpenRunnable;
   private boolean mReserveOverflow;
   private boolean mReserveOverflowSet;
   private boolean mStrictWidthLimit;
   private int mWidthLimit;
   private boolean mWidthLimitSet;

   public ActionMenuPresenter(Context var1) {
      super(var1, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
   }

   private View findViewForItem(MenuItem var1) {
      ViewGroup var2 = (ViewGroup)this.mMenuView;
      if (var2 == null) {
         return null;
      } else {
         int var3 = var2.getChildCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            View var5 = var2.getChildAt(var4);
            if (var5 instanceof MenuView.ItemView && ((MenuView.ItemView)var5).getItemData() == var1) {
               return var5;
            }
         }

         return null;
      }
   }

   public void bindItemView(MenuItemImpl var1, MenuView.ItemView var2) {
      var2.initialize(var1, 0);
      ActionMenuView var3 = (ActionMenuView)this.mMenuView;
      ActionMenuItemView var4 = (ActionMenuItemView)var2;
      var4.setItemInvoker(var3);
      if (this.mPopupCallback == null) {
         this.mPopupCallback = new ActionMenuPresenter.ActionMenuPopupCallback();
      }

      var4.setPopupCallback(this.mPopupCallback);
   }

   public boolean dismissPopupMenus() {
      return this.hideOverflowMenu() | this.hideSubMenus();
   }

   public boolean filterLeftoverView(ViewGroup var1, int var2) {
      return var1.getChildAt(var2) == this.mOverflowButton ? false : super.filterLeftoverView(var1, var2);
   }

   public boolean flagActionItems() {
      ActionMenuPresenter var1 = this;
      ArrayList var2;
      int var3;
      if (this.mMenu != null) {
         var2 = this.mMenu.getVisibleItems();
         var3 = var2.size();
      } else {
         var2 = null;
         var3 = 0;
      }

      int var4 = this.mMaxItems;
      int var5 = this.mActionItemWidthLimit;
      int var6 = MeasureSpec.makeMeasureSpec(0, 0);
      ViewGroup var7 = (ViewGroup)this.mMenuView;
      int var8 = 0;
      boolean var9 = false;
      int var10 = 0;

      int var11;
      int var13;
      for(var11 = 0; var8 < var3; var4 = var13) {
         MenuItemImpl var12 = (MenuItemImpl)var2.get(var8);
         if (var12.requiresActionButton()) {
            ++var10;
         } else if (var12.requestsActionButton()) {
            ++var11;
         } else {
            var9 = true;
         }

         var13 = var4;
         if (var1.mExpandedActionViewsExclusive) {
            var13 = var4;
            if (var12.isActionViewExpanded()) {
               var13 = 0;
            }
         }

         ++var8;
      }

      var8 = var4;
      if (var1.mReserveOverflow) {
         label152: {
            if (!var9) {
               var8 = var4;
               if (var11 + var10 <= var4) {
                  break label152;
               }
            }

            var8 = var4 - 1;
         }
      }

      var4 = var8 - var10;
      SparseBooleanArray var26 = var1.mActionButtonGroups;
      var26.clear();
      int var14;
      if (var1.mStrictWidthLimit) {
         var10 = var1.mMinCellSize;
         var11 = var5 / var10;
         var14 = var10 + var5 % var10 / var11;
      } else {
         var14 = 0;
         var11 = 0;
      }

      int var15 = 0;
      var10 = 0;
      int var24 = var5;

      for(var5 = var3; var15 < var5; ++var15) {
         MenuItemImpl var16 = (MenuItemImpl)var2.get(var15);
         View var17;
         if (var16.requiresActionButton()) {
            var17 = this.getItemView(var16, (View)null, var7);
            if (this.mStrictWidthLimit) {
               var11 -= ActionMenuView.measureChildForCells(var17, var14, var11, var6, 0);
            } else {
               var17.measure(var6, var6);
            }

            var8 = var17.getMeasuredWidth();
            var13 = var24 - var8;
            var3 = var10;
            if (var10 == 0) {
               var3 = var8;
            }

            var10 = var16.getGroupId();
            if (var10 != 0) {
               var26.put(var10, true);
            }

            var16.setIsActionButton(true);
         } else {
            if (!var16.requestsActionButton()) {
               var16.setIsActionButton(false);
               continue;
            }

            int var18 = var16.getGroupId();
            boolean var19 = var26.get(var18);
            boolean var20;
            if (var4 <= 0 && !var19 || var24 <= 0 || this.mStrictWidthLimit && var11 <= 0) {
               var20 = false;
            } else {
               var20 = true;
            }

            boolean var21 = var20;
            boolean var22 = var20;
            var13 = var24;
            var8 = var11;
            var3 = var10;
            if (var20) {
               var17 = this.getItemView(var16, (View)null, var7);
               if (this.mStrictWidthLimit) {
                  var8 = ActionMenuView.measureChildForCells(var17, var14, var11, var6, 0);
                  var3 = var11 - var8;
                  var11 = var3;
                  if (var8 == 0) {
                     var21 = false;
                     var11 = var3;
                  }
               } else {
                  var17.measure(var6, var6);
               }

               var8 = var17.getMeasuredWidth();
               var13 = var24 - var8;
               var3 = var10;
               if (var10 == 0) {
                  var3 = var8;
               }

               boolean var25;
               label125: {
                  label124: {
                     if (this.mStrictWidthLimit) {
                        if (var13 >= 0) {
                           break label124;
                        }
                     } else if (var13 + var3 > 0) {
                        break label124;
                     }

                     var25 = false;
                     break label125;
                  }

                  var25 = true;
               }

               var22 = var21 & var25;
               var8 = var11;
            }

            if (var22 && var18 != 0) {
               var26.put(var18, true);
               var10 = var4;
            } else {
               var10 = var4;
               if (var19) {
                  var26.put(var18, false);
                  var11 = 0;

                  while(true) {
                     var10 = var4;
                     if (var11 >= var15) {
                        break;
                     }

                     MenuItemImpl var23 = (MenuItemImpl)var2.get(var11);
                     var10 = var4;
                     if (var23.getGroupId() == var18) {
                        var10 = var4;
                        if (var23.isActionButton()) {
                           var10 = var4 + 1;
                        }

                        var23.setIsActionButton(false);
                     }

                     ++var11;
                     var4 = var10;
                  }
               }
            }

            var4 = var10;
            if (var22) {
               var4 = var10 - 1;
            }

            var16.setIsActionButton(var22);
            var11 = var8;
         }

         var24 = var13;
         var10 = var3;
      }

      return true;
   }

   public View getItemView(MenuItemImpl var1, View var2, ViewGroup var3) {
      View var4 = var1.getActionView();
      if (var4 == null || var1.hasCollapsibleActionView()) {
         var4 = super.getItemView(var1, var2, var3);
      }

      byte var5;
      if (var1.isActionViewExpanded()) {
         var5 = 8;
      } else {
         var5 = 0;
      }

      var4.setVisibility(var5);
      ActionMenuView var7 = (ActionMenuView)var3;
      LayoutParams var6 = var4.getLayoutParams();
      if (!var7.checkLayoutParams(var6)) {
         var4.setLayoutParams(var7.generateLayoutParams(var6));
      }

      return var4;
   }

   public MenuView getMenuView(ViewGroup var1) {
      MenuView var2 = this.mMenuView;
      MenuView var3 = super.getMenuView(var1);
      if (var2 != var3) {
         ((ActionMenuView)var3).setPresenter(this);
      }

      return var3;
   }

   public Drawable getOverflowIcon() {
      ActionMenuPresenter.OverflowMenuButton var1 = this.mOverflowButton;
      if (var1 != null) {
         return var1.getDrawable();
      } else {
         return this.mPendingOverflowIconSet ? this.mPendingOverflowIcon : null;
      }
   }

   public boolean hideOverflowMenu() {
      if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
         ((View)this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
         this.mPostedOpenRunnable = null;
         return true;
      } else {
         ActionMenuPresenter.OverflowPopup var1 = this.mOverflowPopup;
         if (var1 != null) {
            var1.dismiss();
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean hideSubMenus() {
      ActionMenuPresenter.ActionButtonSubmenu var1 = this.mActionButtonPopup;
      if (var1 != null) {
         var1.dismiss();
         return true;
      } else {
         return false;
      }
   }

   public void initForMenu(Context var1, MenuBuilder var2) {
      super.initForMenu(var1, var2);
      Resources var7 = var1.getResources();
      ActionBarPolicy var5 = ActionBarPolicy.get(var1);
      if (!this.mReserveOverflowSet) {
         this.mReserveOverflow = var5.showsOverflowMenuButton();
      }

      if (!this.mWidthLimitSet) {
         this.mWidthLimit = var5.getEmbeddedMenuWidthLimit();
      }

      if (!this.mMaxItemsSet) {
         this.mMaxItems = var5.getMaxActionButtons();
      }

      int var3 = this.mWidthLimit;
      if (this.mReserveOverflow) {
         if (this.mOverflowButton == null) {
            ActionMenuPresenter.OverflowMenuButton var6 = new ActionMenuPresenter.OverflowMenuButton(this.mSystemContext);
            this.mOverflowButton = var6;
            if (this.mPendingOverflowIconSet) {
               var6.setImageDrawable(this.mPendingOverflowIcon);
               this.mPendingOverflowIcon = null;
               this.mPendingOverflowIconSet = false;
            }

            int var4 = MeasureSpec.makeMeasureSpec(0, 0);
            this.mOverflowButton.measure(var4, var4);
         }

         var3 -= this.mOverflowButton.getMeasuredWidth();
      } else {
         this.mOverflowButton = null;
      }

      this.mActionItemWidthLimit = var3;
      this.mMinCellSize = (int)(var7.getDisplayMetrics().density * 56.0F);
   }

   public boolean isOverflowMenuShowPending() {
      boolean var1;
      if (this.mPostedOpenRunnable == null && !this.isOverflowMenuShowing()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isOverflowMenuShowing() {
      ActionMenuPresenter.OverflowPopup var1 = this.mOverflowPopup;
      boolean var2;
      if (var1 != null && var1.isShowing()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isOverflowReserved() {
      return this.mReserveOverflow;
   }

   public void onCloseMenu(MenuBuilder var1, boolean var2) {
      this.dismissPopupMenus();
      super.onCloseMenu(var1, var2);
   }

   public void onConfigurationChanged(Configuration var1) {
      if (!this.mMaxItemsSet) {
         this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
      }

      if (this.mMenu != null) {
         this.mMenu.onItemsChanged(true);
      }

   }

   public void onRestoreInstanceState(Parcelable var1) {
      if (var1 instanceof ActionMenuPresenter.SavedState) {
         ActionMenuPresenter.SavedState var2 = (ActionMenuPresenter.SavedState)var1;
         if (var2.openSubMenuId > 0) {
            MenuItem var3 = this.mMenu.findItem(var2.openSubMenuId);
            if (var3 != null) {
               this.onSubMenuSelected((SubMenuBuilder)var3.getSubMenu());
            }
         }

      }
   }

   public Parcelable onSaveInstanceState() {
      ActionMenuPresenter.SavedState var1 = new ActionMenuPresenter.SavedState();
      var1.openSubMenuId = this.mOpenSubMenuId;
      return var1;
   }

   public boolean onSubMenuSelected(SubMenuBuilder var1) {
      boolean var2 = var1.hasVisibleItems();
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         SubMenuBuilder var4;
         for(var4 = var1; var4.getParentMenu() != this.mMenu; var4 = (SubMenuBuilder)var4.getParentMenu()) {
         }

         View var5 = this.findViewForItem(var4.getItem());
         if (var5 == null) {
            return false;
         } else {
            this.mOpenSubMenuId = var1.getItem().getItemId();
            int var6 = var1.size();
            int var7 = 0;

            while(true) {
               var2 = var3;
               if (var7 >= var6) {
                  break;
               }

               MenuItem var8 = var1.getItem(var7);
               if (var8.isVisible() && var8.getIcon() != null) {
                  var2 = true;
                  break;
               }

               ++var7;
            }

            ActionMenuPresenter.ActionButtonSubmenu var9 = new ActionMenuPresenter.ActionButtonSubmenu(this.mContext, var1, var5);
            this.mActionButtonPopup = var9;
            var9.setForceShowIcon(var2);
            this.mActionButtonPopup.show();
            super.onSubMenuSelected(var1);
            return true;
         }
      }
   }

   public void onSubUiVisibilityChanged(boolean var1) {
      if (var1) {
         super.onSubMenuSelected((SubMenuBuilder)null);
      } else if (this.mMenu != null) {
         this.mMenu.close(false);
      }

   }

   public void setExpandedActionViewsExclusive(boolean var1) {
      this.mExpandedActionViewsExclusive = var1;
   }

   public void setItemLimit(int var1) {
      this.mMaxItems = var1;
      this.mMaxItemsSet = true;
   }

   public void setMenuView(ActionMenuView var1) {
      this.mMenuView = var1;
      var1.initialize(this.mMenu);
   }

   public void setOverflowIcon(Drawable var1) {
      ActionMenuPresenter.OverflowMenuButton var2 = this.mOverflowButton;
      if (var2 != null) {
         var2.setImageDrawable(var1);
      } else {
         this.mPendingOverflowIconSet = true;
         this.mPendingOverflowIcon = var1;
      }

   }

   public void setReserveOverflow(boolean var1) {
      this.mReserveOverflow = var1;
      this.mReserveOverflowSet = true;
   }

   public void setWidthLimit(int var1, boolean var2) {
      this.mWidthLimit = var1;
      this.mStrictWidthLimit = var2;
      this.mWidthLimitSet = true;
   }

   public boolean shouldIncludeItem(int var1, MenuItemImpl var2) {
      return var2.isActionButton();
   }

   public boolean showOverflowMenu() {
      if (this.mReserveOverflow && !this.isOverflowMenuShowing() && this.mMenu != null && this.mMenuView != null && this.mPostedOpenRunnable == null && !this.mMenu.getNonActionItems().isEmpty()) {
         this.mPostedOpenRunnable = new ActionMenuPresenter.OpenOverflowRunnable(new ActionMenuPresenter.OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
         ((View)this.mMenuView).post(this.mPostedOpenRunnable);
         return true;
      } else {
         return false;
      }
   }

   public void updateMenuView(boolean var1) {
      super.updateMenuView(var1);
      ((View)this.mMenuView).requestLayout();
      MenuBuilder var2 = this.mMenu;
      boolean var3 = false;
      int var5;
      if (var2 != null) {
         ArrayList var4 = this.mMenu.getActionItems();
         var5 = var4.size();

         for(int var6 = 0; var6 < var5; ++var6) {
            ActionProvider var7 = ((MenuItemImpl)var4.get(var6)).getSupportActionProvider();
            if (var7 != null) {
               var7.setSubUiVisibilityListener(this);
            }
         }
      }

      ArrayList var8;
      if (this.mMenu != null) {
         var8 = this.mMenu.getNonActionItems();
      } else {
         var8 = null;
      }

      boolean var12 = var3;
      if (this.mReserveOverflow) {
         var12 = var3;
         if (var8 != null) {
            var5 = var8.size();
            if (var5 == 1) {
               var12 = ((MenuItemImpl)var8.get(0)).isActionViewExpanded() ^ true;
            } else {
               var12 = var3;
               if (var5 > 0) {
                  var12 = true;
               }
            }
         }
      }

      if (var12) {
         if (this.mOverflowButton == null) {
            this.mOverflowButton = new ActionMenuPresenter.OverflowMenuButton(this.mSystemContext);
         }

         ViewGroup var9 = (ViewGroup)this.mOverflowButton.getParent();
         if (var9 != this.mMenuView) {
            if (var9 != null) {
               var9.removeView(this.mOverflowButton);
            }

            ActionMenuView var10 = (ActionMenuView)this.mMenuView;
            var10.addView(this.mOverflowButton, var10.generateOverflowButtonLayoutParams());
         }
      } else {
         ActionMenuPresenter.OverflowMenuButton var11 = this.mOverflowButton;
         if (var11 != null && var11.getParent() == this.mMenuView) {
            ((ViewGroup)this.mMenuView).removeView(this.mOverflowButton);
         }
      }

      ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
   }

   private class ActionButtonSubmenu extends MenuPopupHelper {
      public ActionButtonSubmenu(Context var2, SubMenuBuilder var3, View var4) {
         super(var2, var3, var4, false, R.attr.actionOverflowMenuStyle);
         if (!((MenuItemImpl)var3.getItem()).isActionButton()) {
            Object var5;
            if (ActionMenuPresenter.this.mOverflowButton == null) {
               var5 = (View)ActionMenuPresenter.this.mMenuView;
            } else {
               var5 = ActionMenuPresenter.this.mOverflowButton;
            }

            this.setAnchorView((View)var5);
         }

         this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
      }

      protected void onDismiss() {
         ActionMenuPresenter.this.mActionButtonPopup = null;
         ActionMenuPresenter.this.mOpenSubMenuId = 0;
         super.onDismiss();
      }
   }

   private class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
      ActionMenuPopupCallback() {
      }

      public ShowableListMenu getPopup() {
         MenuPopup var1;
         if (ActionMenuPresenter.this.mActionButtonPopup != null) {
            var1 = ActionMenuPresenter.this.mActionButtonPopup.getPopup();
         } else {
            var1 = null;
         }

         return var1;
      }
   }

   private class OpenOverflowRunnable implements Runnable {
      private ActionMenuPresenter.OverflowPopup mPopup;

      public OpenOverflowRunnable(ActionMenuPresenter.OverflowPopup var2) {
         this.mPopup = var2;
      }

      public void run() {
         if (ActionMenuPresenter.this.mMenu != null) {
            ActionMenuPresenter.this.mMenu.changeMenuMode();
         }

         View var1 = (View)ActionMenuPresenter.this.mMenuView;
         if (var1 != null && var1.getWindowToken() != null && this.mPopup.tryShow()) {
            ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
         }

         ActionMenuPresenter.this.mPostedOpenRunnable = null;
      }
   }

   private class OverflowMenuButton extends AppCompatImageView implements ActionMenuView.ActionMenuChildView {
      public OverflowMenuButton(Context var2) {
         super(var2, (AttributeSet)null, R.attr.actionOverflowButtonStyle);
         this.setClickable(true);
         this.setFocusable(true);
         this.setVisibility(0);
         this.setEnabled(true);
         TooltipCompat.setTooltipText(this, this.getContentDescription());
         this.setOnTouchListener(new ForwardingListener(this) {
            public ShowableListMenu getPopup() {
               return ActionMenuPresenter.this.mOverflowPopup == null ? null : ActionMenuPresenter.this.mOverflowPopup.getPopup();
            }

            public boolean onForwardingStarted() {
               ActionMenuPresenter.this.showOverflowMenu();
               return true;
            }

            public boolean onForwardingStopped() {
               if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                  return false;
               } else {
                  ActionMenuPresenter.this.hideOverflowMenu();
                  return true;
               }
            }
         });
      }

      public boolean needsDividerAfter() {
         return false;
      }

      public boolean needsDividerBefore() {
         return false;
      }

      public boolean performClick() {
         if (super.performClick()) {
            return true;
         } else {
            this.playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
         }
      }

      protected boolean setFrame(int var1, int var2, int var3, int var4) {
         boolean var5 = super.setFrame(var1, var2, var3, var4);
         Drawable var6 = this.getDrawable();
         Drawable var7 = this.getBackground();
         if (var6 != null && var7 != null) {
            int var8 = this.getWidth();
            var4 = this.getHeight();
            var1 = Math.max(var8, var4) / 2;
            int var9 = this.getPaddingLeft();
            int var10 = this.getPaddingRight();
            var3 = this.getPaddingTop();
            var2 = this.getPaddingBottom();
            var10 = (var8 + (var9 - var10)) / 2;
            var2 = (var4 + (var3 - var2)) / 2;
            DrawableCompat.setHotspotBounds(var7, var10 - var1, var2 - var1, var10 + var1, var2 + var1);
         }

         return var5;
      }
   }

   private class OverflowPopup extends MenuPopupHelper {
      public OverflowPopup(Context var2, MenuBuilder var3, View var4, boolean var5) {
         super(var2, var3, var4, var5, R.attr.actionOverflowMenuStyle);
         this.setGravity(8388613);
         this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
      }

      protected void onDismiss() {
         if (ActionMenuPresenter.this.mMenu != null) {
            ActionMenuPresenter.this.mMenu.close();
         }

         ActionMenuPresenter.this.mOverflowPopup = null;
         super.onDismiss();
      }
   }

   private class PopupPresenterCallback implements MenuPresenter.Callback {
      PopupPresenterCallback() {
      }

      public void onCloseMenu(MenuBuilder var1, boolean var2) {
         if (var1 instanceof SubMenuBuilder) {
            var1.getRootMenu().close(false);
         }

         MenuPresenter.Callback var3 = ActionMenuPresenter.this.getCallback();
         if (var3 != null) {
            var3.onCloseMenu(var1, var2);
         }

      }

      public boolean onOpenSubMenu(MenuBuilder var1) {
         MenuBuilder var2 = ActionMenuPresenter.this.mMenu;
         boolean var3 = false;
         if (var1 == var2) {
            return false;
         } else {
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)var1).getItem().getItemId();
            MenuPresenter.Callback var4 = ActionMenuPresenter.this.getCallback();
            if (var4 != null) {
               var3 = var4.onOpenSubMenu(var1);
            }

            return var3;
         }
      }
   }

   private static class SavedState implements Parcelable {
      public static final Creator<ActionMenuPresenter.SavedState> CREATOR = new Creator<ActionMenuPresenter.SavedState>() {
         public ActionMenuPresenter.SavedState createFromParcel(Parcel var1) {
            return new ActionMenuPresenter.SavedState(var1);
         }

         public ActionMenuPresenter.SavedState[] newArray(int var1) {
            return new ActionMenuPresenter.SavedState[var1];
         }
      };
      public int openSubMenuId;

      SavedState() {
      }

      SavedState(Parcel var1) {
         this.openSubMenuId = var1.readInt();
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         var1.writeInt(this.openSubMenuId);
      }
   }
}
