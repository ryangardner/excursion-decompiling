package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

public class MenuPopupHelper implements MenuHelper {
   private static final int TOUCH_EPICENTER_SIZE_DP = 48;
   private View mAnchorView;
   private final Context mContext;
   private int mDropDownGravity;
   private boolean mForceShowIcon;
   private final OnDismissListener mInternalOnDismissListener;
   private final MenuBuilder mMenu;
   private OnDismissListener mOnDismissListener;
   private final boolean mOverflowOnly;
   private MenuPopup mPopup;
   private final int mPopupStyleAttr;
   private final int mPopupStyleRes;
   private MenuPresenter.Callback mPresenterCallback;

   public MenuPopupHelper(Context var1, MenuBuilder var2) {
      this(var1, var2, (View)null, false, R.attr.popupMenuStyle, 0);
   }

   public MenuPopupHelper(Context var1, MenuBuilder var2, View var3) {
      this(var1, var2, var3, false, R.attr.popupMenuStyle, 0);
   }

   public MenuPopupHelper(Context var1, MenuBuilder var2, View var3, boolean var4, int var5) {
      this(var1, var2, var3, var4, var5, 0);
   }

   public MenuPopupHelper(Context var1, MenuBuilder var2, View var3, boolean var4, int var5, int var6) {
      this.mDropDownGravity = 8388611;
      this.mInternalOnDismissListener = new OnDismissListener() {
         public void onDismiss() {
            MenuPopupHelper.this.onDismiss();
         }
      };
      this.mContext = var1;
      this.mMenu = var2;
      this.mAnchorView = var3;
      this.mOverflowOnly = var4;
      this.mPopupStyleAttr = var5;
      this.mPopupStyleRes = var6;
   }

   private MenuPopup createPopup() {
      Display var1 = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
      Point var2 = new Point();
      if (VERSION.SDK_INT >= 17) {
         var1.getRealSize(var2);
      } else {
         var1.getSize(var2);
      }

      boolean var3;
      if (Math.min(var2.x, var2.y) >= this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width)) {
         var3 = true;
      } else {
         var3 = false;
      }

      Object var4;
      if (var3) {
         var4 = new CascadingMenuPopup(this.mContext, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
      } else {
         var4 = new StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
      }

      ((MenuPopup)var4).addMenu(this.mMenu);
      ((MenuPopup)var4).setOnDismissListener(this.mInternalOnDismissListener);
      ((MenuPopup)var4).setAnchorView(this.mAnchorView);
      ((MenuPopup)var4).setCallback(this.mPresenterCallback);
      ((MenuPopup)var4).setForceShowIcon(this.mForceShowIcon);
      ((MenuPopup)var4).setGravity(this.mDropDownGravity);
      return (MenuPopup)var4;
   }

   private void showPopup(int var1, int var2, boolean var3, boolean var4) {
      MenuPopup var5 = this.getPopup();
      var5.setShowTitle(var4);
      if (var3) {
         int var6 = var1;
         if ((GravityCompat.getAbsoluteGravity(this.mDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView)) & 7) == 5) {
            var6 = var1 - this.mAnchorView.getWidth();
         }

         var5.setHorizontalOffset(var6);
         var5.setVerticalOffset(var2);
         var1 = (int)(this.mContext.getResources().getDisplayMetrics().density * 48.0F / 2.0F);
         var5.setEpicenterBounds(new Rect(var6 - var1, var2 - var1, var6 + var1, var2 + var1));
      }

      var5.show();
   }

   public void dismiss() {
      if (this.isShowing()) {
         this.mPopup.dismiss();
      }

   }

   public int getGravity() {
      return this.mDropDownGravity;
   }

   public ListView getListView() {
      return this.getPopup().getListView();
   }

   public MenuPopup getPopup() {
      if (this.mPopup == null) {
         this.mPopup = this.createPopup();
      }

      return this.mPopup;
   }

   public boolean isShowing() {
      MenuPopup var1 = this.mPopup;
      boolean var2;
      if (var1 != null && var1.isShowing()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected void onDismiss() {
      this.mPopup = null;
      OnDismissListener var1 = this.mOnDismissListener;
      if (var1 != null) {
         var1.onDismiss();
      }

   }

   public void setAnchorView(View var1) {
      this.mAnchorView = var1;
   }

   public void setForceShowIcon(boolean var1) {
      this.mForceShowIcon = var1;
      MenuPopup var2 = this.mPopup;
      if (var2 != null) {
         var2.setForceShowIcon(var1);
      }

   }

   public void setGravity(int var1) {
      this.mDropDownGravity = var1;
   }

   public void setOnDismissListener(OnDismissListener var1) {
      this.mOnDismissListener = var1;
   }

   public void setPresenterCallback(MenuPresenter.Callback var1) {
      this.mPresenterCallback = var1;
      MenuPopup var2 = this.mPopup;
      if (var2 != null) {
         var2.setCallback(var1);
      }

   }

   public void show() {
      if (!this.tryShow()) {
         throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
      }
   }

   public void show(int var1, int var2) {
      if (!this.tryShow(var1, var2)) {
         throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
      }
   }

   public boolean tryShow() {
      if (this.isShowing()) {
         return true;
      } else if (this.mAnchorView == null) {
         return false;
      } else {
         this.showPopup(0, 0, false, false);
         return true;
      }
   }

   public boolean tryShow(int var1, int var2) {
      if (this.isShowing()) {
         return true;
      } else if (this.mAnchorView == null) {
         return false;
      } else {
         this.showPopup(var1, var2, true, true);
         return true;
      }
   }
}
