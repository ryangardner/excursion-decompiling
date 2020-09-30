/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.Menu
 *  android.view.MenuItem
 */
package androidx.core.view;

import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.internal.view.SupportMenu;

public final class MenuCompat {
    private MenuCompat() {
    }

    public static void setGroupDividerEnabled(Menu menu2, boolean bl) {
        if (menu2 instanceof SupportMenu) {
            ((SupportMenu)menu2).setGroupDividerEnabled(bl);
            return;
        }
        if (Build.VERSION.SDK_INT < 28) return;
        menu2.setGroupDividerEnabled(bl);
    }

    @Deprecated
    public static void setShowAsAction(MenuItem menuItem, int n) {
        menuItem.setShowAsAction(n);
    }
}

