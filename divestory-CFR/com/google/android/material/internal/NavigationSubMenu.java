/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Menu
 */
package com.google.android.material.internal;

import android.content.Context;
import android.view.Menu;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;
import com.google.android.material.internal.NavigationMenu;

public class NavigationSubMenu
extends SubMenuBuilder {
    public NavigationSubMenu(Context context, NavigationMenu navigationMenu, MenuItemImpl menuItemImpl) {
        super(context, navigationMenu, menuItemImpl);
    }

    @Override
    public void onItemsChanged(boolean bl) {
        super.onItemsChanged(bl);
        ((MenuBuilder)this.getParentMenu()).onItemsChanged(bl);
    }
}

