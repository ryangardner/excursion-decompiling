/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.appcompat.view.menu.BaseMenuWrapper;
import androidx.core.internal.view.SupportMenu;

public class MenuWrapperICS
extends BaseMenuWrapper
implements Menu {
    private final SupportMenu mWrappedObject;

    public MenuWrapperICS(Context context, SupportMenu supportMenu) {
        super(context);
        if (supportMenu == null) throw new IllegalArgumentException("Wrapped Object can not be null.");
        this.mWrappedObject = supportMenu;
    }

    public MenuItem add(int n) {
        return this.getMenuItemWrapper(this.mWrappedObject.add(n));
    }

    public MenuItem add(int n, int n2, int n3, int n4) {
        return this.getMenuItemWrapper(this.mWrappedObject.add(n, n2, n3, n4));
    }

    public MenuItem add(int n, int n2, int n3, CharSequence charSequence) {
        return this.getMenuItemWrapper(this.mWrappedObject.add(n, n2, n3, charSequence));
    }

    public MenuItem add(CharSequence charSequence) {
        return this.getMenuItemWrapper(this.mWrappedObject.add(charSequence));
    }

    public int addIntentOptions(int n, int n2, int n3, ComponentName componentName, Intent[] arrintent, Intent intent, int n4, MenuItem[] arrmenuItem) {
        MenuItem[] arrmenuItem2 = arrmenuItem != null ? new MenuItem[arrmenuItem.length] : null;
        n2 = this.mWrappedObject.addIntentOptions(n, n2, n3, componentName, arrintent, intent, n4, arrmenuItem2);
        if (arrmenuItem2 == null) return n2;
        n = 0;
        n3 = arrmenuItem2.length;
        while (n < n3) {
            arrmenuItem[n] = this.getMenuItemWrapper(arrmenuItem2[n]);
            ++n;
        }
        return n2;
    }

    public SubMenu addSubMenu(int n) {
        return this.getSubMenuWrapper(this.mWrappedObject.addSubMenu(n));
    }

    public SubMenu addSubMenu(int n, int n2, int n3, int n4) {
        return this.getSubMenuWrapper(this.mWrappedObject.addSubMenu(n, n2, n3, n4));
    }

    public SubMenu addSubMenu(int n, int n2, int n3, CharSequence charSequence) {
        return this.getSubMenuWrapper(this.mWrappedObject.addSubMenu(n, n2, n3, charSequence));
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return this.getSubMenuWrapper(this.mWrappedObject.addSubMenu(charSequence));
    }

    public void clear() {
        this.internalClear();
        this.mWrappedObject.clear();
    }

    public void close() {
        this.mWrappedObject.close();
    }

    public MenuItem findItem(int n) {
        return this.getMenuItemWrapper(this.mWrappedObject.findItem(n));
    }

    public MenuItem getItem(int n) {
        return this.getMenuItemWrapper(this.mWrappedObject.getItem(n));
    }

    public boolean hasVisibleItems() {
        return this.mWrappedObject.hasVisibleItems();
    }

    public boolean isShortcutKey(int n, KeyEvent keyEvent) {
        return this.mWrappedObject.isShortcutKey(n, keyEvent);
    }

    public boolean performIdentifierAction(int n, int n2) {
        return this.mWrappedObject.performIdentifierAction(n, n2);
    }

    public boolean performShortcut(int n, KeyEvent keyEvent, int n2) {
        return this.mWrappedObject.performShortcut(n, keyEvent, n2);
    }

    public void removeGroup(int n) {
        this.internalRemoveGroup(n);
        this.mWrappedObject.removeGroup(n);
    }

    public void removeItem(int n) {
        this.internalRemoveItem(n);
        this.mWrappedObject.removeItem(n);
    }

    public void setGroupCheckable(int n, boolean bl, boolean bl2) {
        this.mWrappedObject.setGroupCheckable(n, bl, bl2);
    }

    public void setGroupEnabled(int n, boolean bl) {
        this.mWrappedObject.setGroupEnabled(n, bl);
    }

    public void setGroupVisible(int n, boolean bl) {
        this.mWrappedObject.setGroupVisible(n, bl);
    }

    public void setQwertyMode(boolean bl) {
        this.mWrappedObject.setQwertyMode(bl);
    }

    public int size() {
        return this.mWrappedObject.size();
    }
}

