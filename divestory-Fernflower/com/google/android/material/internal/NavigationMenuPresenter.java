package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.google.android.material.R;
import java.util.ArrayList;

public class NavigationMenuPresenter implements MenuPresenter {
   private static final String STATE_ADAPTER = "android:menu:adapter";
   private static final String STATE_HEADER = "android:menu:header";
   private static final String STATE_HIERARCHY = "android:menu:list";
   NavigationMenuPresenter.NavigationMenuAdapter adapter;
   private MenuPresenter.Callback callback;
   boolean hasCustomItemIconSize;
   LinearLayout headerLayout;
   ColorStateList iconTintList;
   private int id;
   boolean isBehindStatusBar = true;
   Drawable itemBackground;
   int itemHorizontalPadding;
   int itemIconPadding;
   int itemIconSize;
   private int itemMaxLines;
   LayoutInflater layoutInflater;
   MenuBuilder menu;
   private NavigationMenuView menuView;
   final OnClickListener onClickListener = new OnClickListener() {
      public void onClick(View var1) {
         NavigationMenuItemView var2 = (NavigationMenuItemView)var1;
         NavigationMenuPresenter var5 = NavigationMenuPresenter.this;
         boolean var3 = true;
         var5.setUpdateSuspended(true);
         MenuItemImpl var6 = var2.getItemData();
         boolean var4 = NavigationMenuPresenter.this.menu.performItemAction(var6, NavigationMenuPresenter.this, 0);
         if (var6 != null && var6.isCheckable() && var4) {
            NavigationMenuPresenter.this.adapter.setCheckedItem(var6);
         } else {
            var3 = false;
         }

         NavigationMenuPresenter.this.setUpdateSuspended(false);
         if (var3) {
            NavigationMenuPresenter.this.updateMenuView(false);
         }

      }
   };
   private int overScrollMode = -1;
   int paddingSeparator;
   private int paddingTopDefault;
   int textAppearance;
   boolean textAppearanceSet;
   ColorStateList textColor;

   private void updateTopPadding() {
      int var1;
      if (this.headerLayout.getChildCount() == 0 && this.isBehindStatusBar) {
         var1 = this.paddingTopDefault;
      } else {
         var1 = 0;
      }

      NavigationMenuView var2 = this.menuView;
      var2.setPadding(0, var1, 0, var2.getPaddingBottom());
   }

   public void addHeaderView(View var1) {
      this.headerLayout.addView(var1);
      NavigationMenuView var2 = this.menuView;
      var2.setPadding(0, 0, 0, var2.getPaddingBottom());
   }

   public boolean collapseItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public void dispatchApplyWindowInsets(WindowInsetsCompat var1) {
      int var2 = var1.getSystemWindowInsetTop();
      if (this.paddingTopDefault != var2) {
         this.paddingTopDefault = var2;
         this.updateTopPadding();
      }

      NavigationMenuView var3 = this.menuView;
      var3.setPadding(0, var3.getPaddingTop(), 0, var1.getSystemWindowInsetBottom());
      ViewCompat.dispatchApplyWindowInsets(this.headerLayout, var1);
   }

   public boolean expandItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public boolean flagActionItems() {
      return false;
   }

   public MenuItemImpl getCheckedItem() {
      return this.adapter.getCheckedItem();
   }

   public int getHeaderCount() {
      return this.headerLayout.getChildCount();
   }

   public View getHeaderView(int var1) {
      return this.headerLayout.getChildAt(var1);
   }

   public int getId() {
      return this.id;
   }

   public Drawable getItemBackground() {
      return this.itemBackground;
   }

   public int getItemHorizontalPadding() {
      return this.itemHorizontalPadding;
   }

   public int getItemIconPadding() {
      return this.itemIconPadding;
   }

   public int getItemMaxLines() {
      return this.itemMaxLines;
   }

   public ColorStateList getItemTextColor() {
      return this.textColor;
   }

   public ColorStateList getItemTintList() {
      return this.iconTintList;
   }

   public MenuView getMenuView(ViewGroup var1) {
      if (this.menuView == null) {
         NavigationMenuView var3 = (NavigationMenuView)this.layoutInflater.inflate(R.layout.design_navigation_menu, var1, false);
         this.menuView = var3;
         var3.setAccessibilityDelegateCompat(new NavigationMenuPresenter.NavigationMenuViewAccessibilityDelegate(this.menuView));
         if (this.adapter == null) {
            this.adapter = new NavigationMenuPresenter.NavigationMenuAdapter();
         }

         int var2 = this.overScrollMode;
         if (var2 != -1) {
            this.menuView.setOverScrollMode(var2);
         }

         this.headerLayout = (LinearLayout)this.layoutInflater.inflate(R.layout.design_navigation_item_header, this.menuView, false);
         this.menuView.setAdapter(this.adapter);
      }

      return this.menuView;
   }

   public View inflateHeaderView(int var1) {
      View var2 = this.layoutInflater.inflate(var1, this.headerLayout, false);
      this.addHeaderView(var2);
      return var2;
   }

   public void initForMenu(Context var1, MenuBuilder var2) {
      this.layoutInflater = LayoutInflater.from(var1);
      this.menu = var2;
      this.paddingSeparator = var1.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
   }

   public boolean isBehindStatusBar() {
      return this.isBehindStatusBar;
   }

   public void onCloseMenu(MenuBuilder var1, boolean var2) {
      MenuPresenter.Callback var3 = this.callback;
      if (var3 != null) {
         var3.onCloseMenu(var1, var2);
      }

   }

   public void onRestoreInstanceState(Parcelable var1) {
      if (var1 instanceof Bundle) {
         Bundle var3 = (Bundle)var1;
         SparseArray var2 = var3.getSparseParcelableArray("android:menu:list");
         if (var2 != null) {
            this.menuView.restoreHierarchyState(var2);
         }

         Bundle var5 = var3.getBundle("android:menu:adapter");
         if (var5 != null) {
            this.adapter.restoreInstanceState(var5);
         }

         SparseArray var4 = var3.getSparseParcelableArray("android:menu:header");
         if (var4 != null) {
            this.headerLayout.restoreHierarchyState(var4);
         }
      }

   }

   public Parcelable onSaveInstanceState() {
      Bundle var1 = new Bundle();
      SparseArray var2;
      if (this.menuView != null) {
         var2 = new SparseArray();
         this.menuView.saveHierarchyState(var2);
         var1.putSparseParcelableArray("android:menu:list", var2);
      }

      NavigationMenuPresenter.NavigationMenuAdapter var3 = this.adapter;
      if (var3 != null) {
         var1.putBundle("android:menu:adapter", var3.createInstanceState());
      }

      if (this.headerLayout != null) {
         var2 = new SparseArray();
         this.headerLayout.saveHierarchyState(var2);
         var1.putSparseParcelableArray("android:menu:header", var2);
      }

      return var1;
   }

   public boolean onSubMenuSelected(SubMenuBuilder var1) {
      return false;
   }

   public void removeHeaderView(View var1) {
      this.headerLayout.removeView(var1);
      if (this.headerLayout.getChildCount() == 0) {
         NavigationMenuView var2 = this.menuView;
         var2.setPadding(0, this.paddingTopDefault, 0, var2.getPaddingBottom());
      }

   }

   public void setBehindStatusBar(boolean var1) {
      if (this.isBehindStatusBar != var1) {
         this.isBehindStatusBar = var1;
         this.updateTopPadding();
      }

   }

   public void setCallback(MenuPresenter.Callback var1) {
      this.callback = var1;
   }

   public void setCheckedItem(MenuItemImpl var1) {
      this.adapter.setCheckedItem(var1);
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public void setItemBackground(Drawable var1) {
      this.itemBackground = var1;
      this.updateMenuView(false);
   }

   public void setItemHorizontalPadding(int var1) {
      this.itemHorizontalPadding = var1;
      this.updateMenuView(false);
   }

   public void setItemIconPadding(int var1) {
      this.itemIconPadding = var1;
      this.updateMenuView(false);
   }

   public void setItemIconSize(int var1) {
      if (this.itemIconSize != var1) {
         this.itemIconSize = var1;
         this.hasCustomItemIconSize = true;
         this.updateMenuView(false);
      }

   }

   public void setItemIconTintList(ColorStateList var1) {
      this.iconTintList = var1;
      this.updateMenuView(false);
   }

   public void setItemMaxLines(int var1) {
      this.itemMaxLines = var1;
      this.updateMenuView(false);
   }

   public void setItemTextAppearance(int var1) {
      this.textAppearance = var1;
      this.textAppearanceSet = true;
      this.updateMenuView(false);
   }

   public void setItemTextColor(ColorStateList var1) {
      this.textColor = var1;
      this.updateMenuView(false);
   }

   public void setOverScrollMode(int var1) {
      this.overScrollMode = var1;
      NavigationMenuView var2 = this.menuView;
      if (var2 != null) {
         var2.setOverScrollMode(var1);
      }

   }

   public void setUpdateSuspended(boolean var1) {
      NavigationMenuPresenter.NavigationMenuAdapter var2 = this.adapter;
      if (var2 != null) {
         var2.setUpdateSuspended(var1);
      }

   }

   public void updateMenuView(boolean var1) {
      NavigationMenuPresenter.NavigationMenuAdapter var2 = this.adapter;
      if (var2 != null) {
         var2.update();
      }

   }

   private static class HeaderViewHolder extends NavigationMenuPresenter.ViewHolder {
      public HeaderViewHolder(View var1) {
         super(var1);
      }
   }

   private class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuPresenter.ViewHolder> {
      private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
      private static final String STATE_CHECKED_ITEM = "android:menu:checked";
      private static final int VIEW_TYPE_HEADER = 3;
      private static final int VIEW_TYPE_NORMAL = 0;
      private static final int VIEW_TYPE_SEPARATOR = 2;
      private static final int VIEW_TYPE_SUBHEADER = 1;
      private MenuItemImpl checkedItem;
      private final ArrayList<NavigationMenuPresenter.NavigationMenuItem> items = new ArrayList();
      private boolean updateSuspended;

      NavigationMenuAdapter() {
         this.prepareMenuItems();
      }

      private void appendTransparentIconIfMissing(int var1, int var2) {
         while(var1 < var2) {
            ((NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(var1)).needsEmptyIcon = true;
            ++var1;
         }

      }

      private void prepareMenuItems() {
         if (!this.updateSuspended) {
            this.updateSuspended = true;
            this.items.clear();
            this.items.add(new NavigationMenuPresenter.NavigationMenuHeaderItem());
            int var1 = -1;
            int var2 = NavigationMenuPresenter.this.menu.getVisibleItems().size();
            int var3 = 0;
            boolean var4 = false;

            int var10;
            for(int var5 = 0; var3 < var2; var5 = var10) {
               MenuItemImpl var6 = (MenuItemImpl)NavigationMenuPresenter.this.menu.getVisibleItems().get(var3);
               if (var6.isChecked()) {
                  this.setCheckedItem(var6);
               }

               if (var6.isCheckable()) {
                  var6.setExclusiveCheckable(false);
               }

               int var8;
               boolean var9;
               if (!var6.hasSubMenu()) {
                  var8 = var6.getGroupId();
                  int var17;
                  if (var8 != var1) {
                     var5 = this.items.size();
                     if (var6.getIcon() != null) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     var9 = var4;
                     var17 = var5;
                     if (var3 != 0) {
                        var17 = var5 + 1;
                        this.items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, NavigationMenuPresenter.this.paddingSeparator));
                        var9 = var4;
                     }
                  } else {
                     var9 = var4;
                     var17 = var5;
                     if (!var4) {
                        var9 = var4;
                        var17 = var5;
                        if (var6.getIcon() != null) {
                           this.appendTransparentIconIfMissing(var5, this.items.size());
                           var9 = true;
                           var17 = var5;
                        }
                     }
                  }

                  NavigationMenuPresenter.NavigationMenuTextItem var15 = new NavigationMenuPresenter.NavigationMenuTextItem(var6);
                  var15.needsEmptyIcon = var9;
                  this.items.add(var15);
                  var10 = var17;
               } else {
                  SubMenu var7 = var6.getSubMenu();
                  var8 = var1;
                  var9 = var4;
                  var10 = var5;
                  if (var7.hasVisibleItems()) {
                     if (var3 != 0) {
                        this.items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, 0));
                     }

                     this.items.add(new NavigationMenuPresenter.NavigationMenuTextItem(var6));
                     int var11 = this.items.size();
                     int var12 = var7.size();
                     var8 = 0;

                     boolean var13;
                     boolean var16;
                     for(var13 = false; var8 < var12; var13 = var16) {
                        MenuItemImpl var14 = (MenuItemImpl)var7.getItem(var8);
                        var16 = var13;
                        if (var14.isVisible()) {
                           var16 = var13;
                           if (!var13) {
                              var16 = var13;
                              if (var14.getIcon() != null) {
                                 var16 = true;
                              }
                           }

                           if (var14.isCheckable()) {
                              var14.setExclusiveCheckable(false);
                           }

                           if (var6.isChecked()) {
                              this.setCheckedItem(var6);
                           }

                           this.items.add(new NavigationMenuPresenter.NavigationMenuTextItem(var14));
                        }

                        ++var8;
                     }

                     var8 = var1;
                     var9 = var4;
                     var10 = var5;
                     if (var13) {
                        this.appendTransparentIconIfMissing(var11, this.items.size());
                        var8 = var1;
                        var9 = var4;
                        var10 = var5;
                     }
                  }
               }

               ++var3;
               var1 = var8;
               var4 = var9;
            }

            this.updateSuspended = false;
         }
      }

      public Bundle createInstanceState() {
         Bundle var1 = new Bundle();
         MenuItemImpl var2 = this.checkedItem;
         if (var2 != null) {
            var1.putInt("android:menu:checked", var2.getItemId());
         }

         SparseArray var3 = new SparseArray();
         int var4 = 0;

         for(int var5 = this.items.size(); var4 < var5; ++var4) {
            NavigationMenuPresenter.NavigationMenuItem var8 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var4);
            if (var8 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
               MenuItemImpl var6 = ((NavigationMenuPresenter.NavigationMenuTextItem)var8).getMenuItem();
               View var9;
               if (var6 != null) {
                  var9 = var6.getActionView();
               } else {
                  var9 = null;
               }

               if (var9 != null) {
                  ParcelableSparseArray var7 = new ParcelableSparseArray();
                  var9.saveHierarchyState(var7);
                  var3.put(var6.getItemId(), var7);
               }
            }
         }

         var1.putSparseParcelableArray("android:menu:action_views", var3);
         return var1;
      }

      public MenuItemImpl getCheckedItem() {
         return this.checkedItem;
      }

      public int getItemCount() {
         return this.items.size();
      }

      public long getItemId(int var1) {
         return (long)var1;
      }

      public int getItemViewType(int var1) {
         NavigationMenuPresenter.NavigationMenuItem var2 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var1);
         if (var2 instanceof NavigationMenuPresenter.NavigationMenuSeparatorItem) {
            return 2;
         } else if (var2 instanceof NavigationMenuPresenter.NavigationMenuHeaderItem) {
            return 3;
         } else if (var2 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
            return ((NavigationMenuPresenter.NavigationMenuTextItem)var2).getMenuItem().hasSubMenu() ? 1 : 0;
         } else {
            throw new RuntimeException("Unknown item type.");
         }
      }

      int getRowCount() {
         int var1 = NavigationMenuPresenter.this.headerLayout.getChildCount();
         int var2 = 0;
         if (var1 == 0) {
            var1 = 0;
         } else {
            var1 = 1;
         }

         while(var2 < NavigationMenuPresenter.this.adapter.getItemCount()) {
            int var3 = var1;
            if (NavigationMenuPresenter.this.adapter.getItemViewType(var2) == 0) {
               var3 = var1 + 1;
            }

            ++var2;
            var1 = var3;
         }

         return var1;
      }

      public void onBindViewHolder(NavigationMenuPresenter.ViewHolder var1, int var2) {
         int var3 = this.getItemViewType(var2);
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 == 2) {
                  NavigationMenuPresenter.NavigationMenuSeparatorItem var4 = (NavigationMenuPresenter.NavigationMenuSeparatorItem)this.items.get(var2);
                  var1.itemView.setPadding(0, var4.getPaddingTop(), 0, var4.getPaddingBottom());
               }
            } else {
               ((TextView)var1.itemView).setText(((NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(var2)).getMenuItem().getTitle());
            }
         } else {
            NavigationMenuItemView var7 = (NavigationMenuItemView)var1.itemView;
            var7.setIconTintList(NavigationMenuPresenter.this.iconTintList);
            if (NavigationMenuPresenter.this.textAppearanceSet) {
               var7.setTextAppearance(NavigationMenuPresenter.this.textAppearance);
            }

            if (NavigationMenuPresenter.this.textColor != null) {
               var7.setTextColor(NavigationMenuPresenter.this.textColor);
            }

            Drawable var5;
            if (NavigationMenuPresenter.this.itemBackground != null) {
               var5 = NavigationMenuPresenter.this.itemBackground.getConstantState().newDrawable();
            } else {
               var5 = null;
            }

            ViewCompat.setBackground(var7, var5);
            NavigationMenuPresenter.NavigationMenuTextItem var6 = (NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(var2);
            var7.setNeedsEmptyIcon(var6.needsEmptyIcon);
            var7.setHorizontalPadding(NavigationMenuPresenter.this.itemHorizontalPadding);
            var7.setIconPadding(NavigationMenuPresenter.this.itemIconPadding);
            if (NavigationMenuPresenter.this.hasCustomItemIconSize) {
               var7.setIconSize(NavigationMenuPresenter.this.itemIconSize);
            }

            var7.setMaxLines(NavigationMenuPresenter.this.itemMaxLines);
            var7.initialize(var6.getMenuItem(), 0);
         }

      }

      public NavigationMenuPresenter.ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  return var2 != 3 ? null : new NavigationMenuPresenter.HeaderViewHolder(NavigationMenuPresenter.this.headerLayout);
               } else {
                  return new NavigationMenuPresenter.SeparatorViewHolder(NavigationMenuPresenter.this.layoutInflater, var1);
               }
            } else {
               return new NavigationMenuPresenter.SubheaderViewHolder(NavigationMenuPresenter.this.layoutInflater, var1);
            }
         } else {
            return new NavigationMenuPresenter.NormalViewHolder(NavigationMenuPresenter.this.layoutInflater, var1, NavigationMenuPresenter.this.onClickListener);
         }
      }

      public void onViewRecycled(NavigationMenuPresenter.ViewHolder var1) {
         if (var1 instanceof NavigationMenuPresenter.NormalViewHolder) {
            ((NavigationMenuItemView)var1.itemView).recycle();
         }

      }

      public void restoreInstanceState(Bundle var1) {
         byte var2 = 0;
         int var3 = var1.getInt("android:menu:checked", 0);
         int var5;
         NavigationMenuPresenter.NavigationMenuItem var6;
         if (var3 != 0) {
            this.updateSuspended = true;
            int var4 = this.items.size();

            for(var5 = 0; var5 < var4; ++var5) {
               var6 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var5);
               if (var6 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
                  MenuItemImpl var9 = ((NavigationMenuPresenter.NavigationMenuTextItem)var6).getMenuItem();
                  if (var9 != null && var9.getItemId() == var3) {
                     this.setCheckedItem(var9);
                     break;
                  }
               }
            }

            this.updateSuspended = false;
            this.prepareMenuItems();
         }

         SparseArray var8 = var1.getSparseParcelableArray("android:menu:action_views");
         if (var8 != null) {
            var3 = this.items.size();

            for(var5 = var2; var5 < var3; ++var5) {
               var6 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var5);
               if (var6 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
                  MenuItemImpl var7 = ((NavigationMenuPresenter.NavigationMenuTextItem)var6).getMenuItem();
                  if (var7 != null) {
                     View var10 = var7.getActionView();
                     if (var10 != null) {
                        ParcelableSparseArray var11 = (ParcelableSparseArray)var8.get(var7.getItemId());
                        if (var11 != null) {
                           var10.restoreHierarchyState(var11);
                        }
                     }
                  }
               }
            }
         }

      }

      public void setCheckedItem(MenuItemImpl var1) {
         if (this.checkedItem != var1 && var1.isCheckable()) {
            MenuItemImpl var2 = this.checkedItem;
            if (var2 != null) {
               var2.setChecked(false);
            }

            this.checkedItem = var1;
            var1.setChecked(true);
         }

      }

      public void setUpdateSuspended(boolean var1) {
         this.updateSuspended = var1;
      }

      public void update() {
         this.prepareMenuItems();
         this.notifyDataSetChanged();
      }
   }

   private static class NavigationMenuHeaderItem implements NavigationMenuPresenter.NavigationMenuItem {
      NavigationMenuHeaderItem() {
      }
   }

   private interface NavigationMenuItem {
   }

   private static class NavigationMenuSeparatorItem implements NavigationMenuPresenter.NavigationMenuItem {
      private final int paddingBottom;
      private final int paddingTop;

      public NavigationMenuSeparatorItem(int var1, int var2) {
         this.paddingTop = var1;
         this.paddingBottom = var2;
      }

      public int getPaddingBottom() {
         return this.paddingBottom;
      }

      public int getPaddingTop() {
         return this.paddingTop;
      }
   }

   private static class NavigationMenuTextItem implements NavigationMenuPresenter.NavigationMenuItem {
      private final MenuItemImpl menuItem;
      boolean needsEmptyIcon;

      NavigationMenuTextItem(MenuItemImpl var1) {
         this.menuItem = var1;
      }

      public MenuItemImpl getMenuItem() {
         return this.menuItem;
      }
   }

   private class NavigationMenuViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
      NavigationMenuViewAccessibilityDelegate(RecyclerView var2) {
         super(var2);
      }

      public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2) {
         super.onInitializeAccessibilityNodeInfo(var1, var2);
         var2.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(NavigationMenuPresenter.this.adapter.getRowCount(), 0, false));
      }
   }

   private static class NormalViewHolder extends NavigationMenuPresenter.ViewHolder {
      public NormalViewHolder(LayoutInflater var1, ViewGroup var2, OnClickListener var3) {
         super(var1.inflate(R.layout.design_navigation_item, var2, false));
         this.itemView.setOnClickListener(var3);
      }
   }

   private static class SeparatorViewHolder extends NavigationMenuPresenter.ViewHolder {
      public SeparatorViewHolder(LayoutInflater var1, ViewGroup var2) {
         super(var1.inflate(R.layout.design_navigation_item_separator, var2, false));
      }
   }

   private static class SubheaderViewHolder extends NavigationMenuPresenter.ViewHolder {
      public SubheaderViewHolder(LayoutInflater var1, ViewGroup var2) {
         super(var1.inflate(R.layout.design_navigation_item_subheader, var2, false));
      }
   }

   private abstract static class ViewHolder extends RecyclerView.ViewHolder {
      public ViewHolder(View var1) {
         super(var1);
      }
   }
}
