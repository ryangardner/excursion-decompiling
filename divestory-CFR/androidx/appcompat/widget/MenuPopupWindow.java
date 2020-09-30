/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.transition.Transition
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.HeaderViewListAdapter
 *  android.widget.ListAdapter
 *  android.widget.PopupWindow
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.DropDownListView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.MenuItemHoverListener;
import java.lang.reflect.Method;

public class MenuPopupWindow
extends ListPopupWindow
implements MenuItemHoverListener {
    private static final String TAG = "MenuPopupWindow";
    private static Method sSetTouchModalMethod;
    private MenuItemHoverListener mHoverListener;

    static {
        try {
            if (Build.VERSION.SDK_INT > 28) return;
            sSetTouchModalMethod = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.i((String)TAG, (String)"Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    public MenuPopupWindow(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    DropDownListView createDropDownListView(Context object, boolean bl) {
        object = new MenuDropDownListView((Context)object, bl);
        ((MenuDropDownListView)((Object)object)).setHoverListener(this);
        return object;
    }

    @Override
    public void onItemHoverEnter(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
        if (menuItemHoverListener == null) return;
        menuItemHoverListener.onItemHoverEnter(menuBuilder, menuItem);
    }

    @Override
    public void onItemHoverExit(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
        if (menuItemHoverListener == null) return;
        menuItemHoverListener.onItemHoverExit(menuBuilder, menuItem);
    }

    public void setEnterTransition(Object object) {
        if (Build.VERSION.SDK_INT < 23) return;
        this.mPopup.setEnterTransition((Transition)object);
    }

    public void setExitTransition(Object object) {
        if (Build.VERSION.SDK_INT < 23) return;
        this.mPopup.setExitTransition((Transition)object);
    }

    public void setHoverListener(MenuItemHoverListener menuItemHoverListener) {
        this.mHoverListener = menuItemHoverListener;
    }

    public void setTouchModal(boolean bl) {
        if (Build.VERSION.SDK_INT > 28) {
            this.mPopup.setTouchModal(bl);
            return;
        }
        Method method = sSetTouchModalMethod;
        if (method == null) return;
        try {
            method.invoke((Object)this.mPopup, bl);
            return;
        }
        catch (Exception exception) {
            Log.i((String)TAG, (String)"Could not invoke setTouchModal() on PopupWindow. Oh well.");
            return;
        }
    }

    public static class MenuDropDownListView
    extends DropDownListView {
        final int mAdvanceKey;
        private MenuItemHoverListener mHoverListener;
        private MenuItem mHoveredMenuItem;
        final int mRetreatKey;

        public MenuDropDownListView(Context context, boolean bl) {
            super(context, bl);
            context = context.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= 17 && 1 == context.getLayoutDirection()) {
                this.mAdvanceKey = 21;
                this.mRetreatKey = 22;
                return;
            }
            this.mAdvanceKey = 22;
            this.mRetreatKey = 21;
        }

        public void clearSelection() {
            this.setSelection(-1);
        }

        @Override
        public boolean onHoverEvent(MotionEvent motionEvent) {
            Object object;
            int n;
            if (this.mHoverListener == null) return super.onHoverEvent(motionEvent);
            Object object2 = this.getAdapter();
            if (object2 instanceof HeaderViewListAdapter) {
                object2 = (HeaderViewListAdapter)object2;
                n = object2.getHeadersCount();
                object = (MenuAdapter)object2.getWrappedAdapter();
            } else {
                n = 0;
                object = (MenuAdapter)((Object)object2);
            }
            MenuItem menuItem = null;
            object2 = menuItem;
            if (motionEvent.getAction() != 10) {
                int n2 = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
                object2 = menuItem;
                if (n2 != -1) {
                    n = n2 - n;
                    object2 = menuItem;
                    if (n >= 0) {
                        object2 = menuItem;
                        if (n < ((MenuAdapter)((Object)object)).getCount()) {
                            object2 = ((MenuAdapter)((Object)object)).getItem(n);
                        }
                    }
                }
            }
            if ((menuItem = this.mHoveredMenuItem) == object2) return super.onHoverEvent(motionEvent);
            object = ((MenuAdapter)((Object)object)).getAdapterMenu();
            if (menuItem != null) {
                this.mHoverListener.onItemHoverExit((MenuBuilder)object, menuItem);
            }
            this.mHoveredMenuItem = object2;
            if (object2 == null) return super.onHoverEvent(motionEvent);
            this.mHoverListener.onItemHoverEnter((MenuBuilder)object, (MenuItem)object2);
            return super.onHoverEvent(motionEvent);
        }

        public boolean onKeyDown(int n, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView)this.getSelectedView();
            if (listMenuItemView != null && n == this.mAdvanceKey) {
                if (!listMenuItemView.isEnabled()) return true;
                if (!listMenuItemView.getItemData().hasSubMenu()) return true;
                this.performItemClick((View)listMenuItemView, this.getSelectedItemPosition(), this.getSelectedItemId());
                return true;
            }
            if (listMenuItemView == null) return super.onKeyDown(n, keyEvent);
            if (n != this.mRetreatKey) return super.onKeyDown(n, keyEvent);
            this.setSelection(-1);
            ((MenuAdapter)this.getAdapter()).getAdapterMenu().close(false);
            return true;
        }

        public void setHoverListener(MenuItemHoverListener menuItemHoverListener) {
            this.mHoverListener = menuItemHoverListener;
        }
    }

}

