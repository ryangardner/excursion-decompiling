/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.View
 *  android.view.WindowManager
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.CascadingMenuPopup;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuHelper;
import androidx.appcompat.view.menu.MenuPopup;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.StandardMenuPopup;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

public class MenuPopupHelper
implements MenuHelper {
    private static final int TOUCH_EPICENTER_SIZE_DP = 48;
    private View mAnchorView;
    private final Context mContext;
    private int mDropDownGravity = 8388611;
    private boolean mForceShowIcon;
    private final PopupWindow.OnDismissListener mInternalOnDismissListener = new PopupWindow.OnDismissListener(){

        public void onDismiss() {
            MenuPopupHelper.this.onDismiss();
        }
    };
    private final MenuBuilder mMenu;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private MenuPopup mPopup;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view) {
        this(context, menuBuilder, view, false, R.attr.popupMenuStyle, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean bl, int n) {
        this(context, menuBuilder, view, bl, n, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean bl, int n, int n2) {
        this.mContext = context;
        this.mMenu = menuBuilder;
        this.mAnchorView = view;
        this.mOverflowOnly = bl;
        this.mPopupStyleAttr = n;
        this.mPopupStyleRes = n2;
    }

    private MenuPopup createPopup() {
        Object object = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            object.getRealSize(point);
        } else {
            object.getSize(point);
        }
        boolean bl = Math.min(point.x, point.y) >= this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width);
        object = bl ? new CascadingMenuPopup(this.mContext, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly) : new StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
        ((MenuPopup)object).addMenu(this.mMenu);
        ((MenuPopup)object).setOnDismissListener(this.mInternalOnDismissListener);
        ((MenuPopup)object).setAnchorView(this.mAnchorView);
        object.setCallback(this.mPresenterCallback);
        ((MenuPopup)object).setForceShowIcon(this.mForceShowIcon);
        ((MenuPopup)object).setGravity(this.mDropDownGravity);
        return object;
    }

    private void showPopup(int n, int n2, boolean bl, boolean bl2) {
        MenuPopup menuPopup = this.getPopup();
        menuPopup.setShowTitle(bl2);
        if (bl) {
            int n3 = n;
            if ((GravityCompat.getAbsoluteGravity(this.mDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView)) & 7) == 5) {
                n3 = n - this.mAnchorView.getWidth();
            }
            menuPopup.setHorizontalOffset(n3);
            menuPopup.setVerticalOffset(n2);
            n = (int)(this.mContext.getResources().getDisplayMetrics().density * 48.0f / 2.0f);
            menuPopup.setEpicenterBounds(new Rect(n3 - n, n2 - n, n3 + n, n2 + n));
        }
        menuPopup.show();
    }

    @Override
    public void dismiss() {
        if (!this.isShowing()) return;
        this.mPopup.dismiss();
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    public ListView getListView() {
        return this.getPopup().getListView();
    }

    public MenuPopup getPopup() {
        if (this.mPopup != null) return this.mPopup;
        this.mPopup = this.createPopup();
        return this.mPopup;
    }

    public boolean isShowing() {
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup == null) return false;
        if (!menuPopup.isShowing()) return false;
        return true;
    }

    protected void onDismiss() {
        this.mPopup = null;
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener == null) return;
        onDismissListener.onDismiss();
    }

    public void setAnchorView(View view) {
        this.mAnchorView = view;
    }

    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup == null) return;
        menuPopup.setForceShowIcon(bl);
    }

    public void setGravity(int n) {
        this.mDropDownGravity = n;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override
    public void setPresenterCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup == null) return;
        menuPopup.setCallback(callback);
    }

    public void show() {
        if (!this.tryShow()) throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
    }

    public void show(int n, int n2) {
        if (!this.tryShow(n, n2)) throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
    }

    public boolean tryShow() {
        if (this.isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        this.showPopup(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int n, int n2) {
        if (this.isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        this.showPopup(n, n2, true, true);
        return true;
    }

}

