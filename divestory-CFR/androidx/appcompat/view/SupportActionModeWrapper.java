/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 */
package androidx.appcompat.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;
import java.util.ArrayList;

public class SupportActionModeWrapper
extends android.view.ActionMode {
    final Context mContext;
    final ActionMode mWrappedObject;

    public SupportActionModeWrapper(Context context, ActionMode actionMode) {
        this.mContext = context;
        this.mWrappedObject = actionMode;
    }

    public void finish() {
        this.mWrappedObject.finish();
    }

    public View getCustomView() {
        return this.mWrappedObject.getCustomView();
    }

    public Menu getMenu() {
        return new MenuWrapperICS(this.mContext, (SupportMenu)this.mWrappedObject.getMenu());
    }

    public MenuInflater getMenuInflater() {
        return this.mWrappedObject.getMenuInflater();
    }

    public CharSequence getSubtitle() {
        return this.mWrappedObject.getSubtitle();
    }

    public Object getTag() {
        return this.mWrappedObject.getTag();
    }

    public CharSequence getTitle() {
        return this.mWrappedObject.getTitle();
    }

    public boolean getTitleOptionalHint() {
        return this.mWrappedObject.getTitleOptionalHint();
    }

    public void invalidate() {
        this.mWrappedObject.invalidate();
    }

    public boolean isTitleOptional() {
        return this.mWrappedObject.isTitleOptional();
    }

    public void setCustomView(View view) {
        this.mWrappedObject.setCustomView(view);
    }

    public void setSubtitle(int n) {
        this.mWrappedObject.setSubtitle(n);
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mWrappedObject.setSubtitle(charSequence);
    }

    public void setTag(Object object) {
        this.mWrappedObject.setTag(object);
    }

    public void setTitle(int n) {
        this.mWrappedObject.setTitle(n);
    }

    public void setTitle(CharSequence charSequence) {
        this.mWrappedObject.setTitle(charSequence);
    }

    public void setTitleOptionalHint(boolean bl) {
        this.mWrappedObject.setTitleOptionalHint(bl);
    }

    public static class CallbackWrapper
    implements ActionMode.Callback {
        final ArrayList<SupportActionModeWrapper> mActionModes;
        final Context mContext;
        final SimpleArrayMap<Menu, Menu> mMenus;
        final ActionMode.Callback mWrappedCallback;

        public CallbackWrapper(Context context, ActionMode.Callback callback) {
            this.mContext = context;
            this.mWrappedCallback = callback;
            this.mActionModes = new ArrayList();
            this.mMenus = new SimpleArrayMap();
        }

        private Menu getMenuWrapper(Menu menu2) {
            Menu menu3;
            Menu menu4 = menu3 = this.mMenus.get((Object)menu2);
            if (menu3 != null) return menu4;
            menu4 = new MenuWrapperICS(this.mContext, (SupportMenu)menu2);
            this.mMenus.put(menu2, menu4);
            return menu4;
        }

        public android.view.ActionMode getActionModeWrapper(ActionMode object) {
            int n = this.mActionModes.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    object = new SupportActionModeWrapper(this.mContext, (ActionMode)object);
                    this.mActionModes.add((SupportActionModeWrapper)((Object)object));
                    return object;
                }
                SupportActionModeWrapper supportActionModeWrapper = this.mActionModes.get(n2);
                if (supportActionModeWrapper != null && supportActionModeWrapper.mWrappedObject == object) {
                    return supportActionModeWrapper;
                }
                ++n2;
            } while (true);
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrappedCallback.onActionItemClicked(this.getActionModeWrapper(actionMode), (MenuItem)new MenuItemWrapperICS(this.mContext, (SupportMenuItem)menuItem));
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
            return this.mWrappedCallback.onCreateActionMode(this.getActionModeWrapper(actionMode), this.getMenuWrapper(menu2));
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrappedCallback.onDestroyActionMode(this.getActionModeWrapper(actionMode));
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
            return this.mWrappedCallback.onPrepareActionMode(this.getActionModeWrapper(actionMode), this.getMenuWrapper(menu2));
        }
    }

}

