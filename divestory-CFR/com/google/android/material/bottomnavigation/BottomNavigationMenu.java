/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;

public final class BottomNavigationMenu
extends MenuBuilder {
    public static final int MAX_ITEM_COUNT = 5;

    public BottomNavigationMenu(Context context) {
        super(context);
    }

    @Override
    protected MenuItem addInternal(int n, int n2, int n3, CharSequence charSequence) {
        if (this.size() + 1 > 5) throw new IllegalArgumentException("Maximum number of items supported by BottomNavigationView is 5. Limit can be checked with BottomNavigationView#getMaxItemCount()");
        this.stopDispatchingItemsChanged();
        charSequence = super.addInternal(n, n2, n3, charSequence);
        if (charSequence instanceof MenuItemImpl) {
            ((MenuItemImpl)((Object)charSequence)).setExclusiveCheckable(true);
        }
        this.startDispatchingItemsChanged();
        return charSequence;
    }

    @Override
    public SubMenu addSubMenu(int n, int n2, int n3, CharSequence charSequence) {
        throw new UnsupportedOperationException("BottomNavigationView does not support submenus");
    }
}

