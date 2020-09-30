/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.graphics.drawable.Drawable
 *  android.os.IBinder
 *  android.view.KeyEvent
 *  android.view.KeyEvent$DispatcherState
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.ListAdapter
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import androidx.appcompat.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;

class MenuDialogHelper
implements DialogInterface.OnKeyListener,
DialogInterface.OnClickListener,
DialogInterface.OnDismissListener,
MenuPresenter.Callback {
    private AlertDialog mDialog;
    private MenuBuilder mMenu;
    ListMenuPresenter mPresenter;
    private MenuPresenter.Callback mPresenterCallback;

    public MenuDialogHelper(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    public void dismiss() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog == null) return;
        alertDialog.dismiss();
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        this.mMenu.performItemAction((MenuItemImpl)this.mPresenter.getAdapter().getItem(n), 0);
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        MenuPresenter.Callback callback;
        if (bl || menuBuilder == this.mMenu) {
            this.dismiss();
        }
        if ((callback = this.mPresenterCallback) == null) return;
        callback.onCloseMenu(menuBuilder, bl);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        this.mPresenter.onCloseMenu(this.mMenu, true);
    }

    public boolean onKey(DialogInterface dialogInterface, int n, KeyEvent keyEvent) {
        if (n != 82) {
            if (n != 4) return this.mMenu.performShortcut(n, keyEvent, 0);
        }
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            dialogInterface = this.mDialog.getWindow();
            if (dialogInterface == null) return this.mMenu.performShortcut(n, keyEvent, 0);
            if ((dialogInterface = dialogInterface.getDecorView()) == null) return this.mMenu.performShortcut(n, keyEvent, 0);
            if ((dialogInterface = dialogInterface.getKeyDispatcherState()) == null) return this.mMenu.performShortcut(n, keyEvent, 0);
            dialogInterface.startTracking(keyEvent, (Object)this);
            return true;
        }
        if (keyEvent.getAction() != 1) return this.mMenu.performShortcut(n, keyEvent, 0);
        if (keyEvent.isCanceled()) return this.mMenu.performShortcut(n, keyEvent, 0);
        Window window = this.mDialog.getWindow();
        if (window == null) return this.mMenu.performShortcut(n, keyEvent, 0);
        if ((window = window.getDecorView()) == null) return this.mMenu.performShortcut(n, keyEvent, 0);
        if ((window = window.getKeyDispatcherState()) == null) return this.mMenu.performShortcut(n, keyEvent, 0);
        if (!window.isTracking(keyEvent)) return this.mMenu.performShortcut(n, keyEvent, 0);
        this.mMenu.close(true);
        dialogInterface.dismiss();
        return true;
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback == null) return false;
        return callback.onOpenSubMenu(menuBuilder);
    }

    public void setPresenterCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    public void show(IBinder iBinder) {
        ListMenuPresenter listMenuPresenter;
        MenuBuilder menuBuilder = this.mMenu;
        Object object = new AlertDialog.Builder(menuBuilder.getContext());
        this.mPresenter = listMenuPresenter = new ListMenuPresenter(((AlertDialog.Builder)object).getContext(), R.layout.abc_list_menu_item_layout);
        listMenuPresenter.setCallback(this);
        this.mMenu.addMenuPresenter(this.mPresenter);
        ((AlertDialog.Builder)object).setAdapter(this.mPresenter.getAdapter(), this);
        listMenuPresenter = menuBuilder.getHeaderView();
        if (listMenuPresenter != null) {
            ((AlertDialog.Builder)object).setCustomTitle((View)listMenuPresenter);
        } else {
            ((AlertDialog.Builder)object).setIcon(menuBuilder.getHeaderIcon()).setTitle(menuBuilder.getHeaderTitle());
        }
        ((AlertDialog.Builder)object).setOnKeyListener(this);
        this.mDialog = object = ((AlertDialog.Builder)object).create();
        object.setOnDismissListener((DialogInterface.OnDismissListener)this);
        object = this.mDialog.getWindow().getAttributes();
        ((WindowManager.LayoutParams)object).type = 1003;
        if (iBinder != null) {
            ((WindowManager.LayoutParams)object).token = iBinder;
        }
        ((WindowManager.LayoutParams)object).flags |= 131072;
        this.mDialog.show();
    }
}

