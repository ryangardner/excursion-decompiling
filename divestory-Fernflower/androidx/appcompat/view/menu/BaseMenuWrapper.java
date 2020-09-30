package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;

abstract class BaseMenuWrapper {
   final Context mContext;
   private SimpleArrayMap<SupportMenuItem, MenuItem> mMenuItems;
   private SimpleArrayMap<SupportSubMenu, SubMenu> mSubMenus;

   BaseMenuWrapper(Context var1) {
      this.mContext = var1;
   }

   final MenuItem getMenuItemWrapper(MenuItem var1) {
      Object var2 = var1;
      if (var1 instanceof SupportMenuItem) {
         SupportMenuItem var3 = (SupportMenuItem)var1;
         if (this.mMenuItems == null) {
            this.mMenuItems = new SimpleArrayMap();
         }

         var1 = (MenuItem)this.mMenuItems.get(var1);
         var2 = var1;
         if (var1 == null) {
            var2 = new MenuItemWrapperICS(this.mContext, var3);
            this.mMenuItems.put(var3, var2);
         }
      }

      return (MenuItem)var2;
   }

   final SubMenu getSubMenuWrapper(SubMenu var1) {
      if (var1 instanceof SupportSubMenu) {
         SupportSubMenu var2 = (SupportSubMenu)var1;
         if (this.mSubMenus == null) {
            this.mSubMenus = new SimpleArrayMap();
         }

         SubMenu var3 = (SubMenu)this.mSubMenus.get(var2);
         Object var4 = var3;
         if (var3 == null) {
            var4 = new SubMenuWrapperICS(this.mContext, var2);
            this.mSubMenus.put(var2, var4);
         }

         return (SubMenu)var4;
      } else {
         return var1;
      }
   }

   final void internalClear() {
      SimpleArrayMap var1 = this.mMenuItems;
      if (var1 != null) {
         var1.clear();
      }

      var1 = this.mSubMenus;
      if (var1 != null) {
         var1.clear();
      }

   }

   final void internalRemoveGroup(int var1) {
      if (this.mMenuItems != null) {
         int var3;
         for(int var2 = 0; var2 < this.mMenuItems.size(); var2 = var3 + 1) {
            var3 = var2;
            if (((SupportMenuItem)this.mMenuItems.keyAt(var2)).getGroupId() == var1) {
               this.mMenuItems.removeAt(var2);
               var3 = var2 - 1;
            }
         }

      }
   }

   final void internalRemoveItem(int var1) {
      if (this.mMenuItems != null) {
         for(int var2 = 0; var2 < this.mMenuItems.size(); ++var2) {
            if (((SupportMenuItem)this.mMenuItems.keyAt(var2)).getItemId() == var1) {
               this.mMenuItems.removeAt(var2);
               break;
            }
         }

      }
   }
}
