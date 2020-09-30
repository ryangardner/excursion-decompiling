/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.KeyCharacterMap
 *  android.view.KeyCharacterMap$KeyData
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.ViewConfiguration
 */
package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ActionProvider;
import androidx.core.view.ViewConfigurationCompat;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuBuilder
implements SupportMenu {
    private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
    private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
    private static final String PRESENTER_KEY = "android:menu:presenters";
    private static final String TAG = "MenuBuilder";
    private static final int[] sCategoryToOrder = new int[]{1, 4, 5, 3, 2, 0};
    private ArrayList<MenuItemImpl> mActionItems;
    private Callback mCallback;
    private final Context mContext;
    private ContextMenu.ContextMenuInfo mCurrentMenuInfo;
    private int mDefaultShowAsAction = 0;
    private MenuItemImpl mExpandedItem;
    private boolean mGroupDividerEnabled = false;
    Drawable mHeaderIcon;
    CharSequence mHeaderTitle;
    View mHeaderView;
    private boolean mIsActionItemsStale;
    private boolean mIsClosing = false;
    private boolean mIsVisibleItemsStale;
    private ArrayList<MenuItemImpl> mItems;
    private boolean mItemsChangedWhileDispatchPrevented = false;
    private ArrayList<MenuItemImpl> mNonActionItems;
    private boolean mOptionalIconsVisible = false;
    private boolean mOverrideVisibleItems;
    private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList();
    private boolean mPreventDispatchingItemsChanged = false;
    private boolean mQwertyMode;
    private final Resources mResources;
    private boolean mShortcutsVisible;
    private boolean mStructureChangedWhileDispatchPrevented = false;
    private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList();
    private ArrayList<MenuItemImpl> mVisibleItems;

    public MenuBuilder(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
        this.mItems = new ArrayList();
        this.mVisibleItems = new ArrayList();
        this.mIsVisibleItemsStale = true;
        this.mActionItems = new ArrayList();
        this.mNonActionItems = new ArrayList();
        this.mIsActionItemsStale = true;
        this.setShortcutsVisibleInner(true);
    }

    private MenuItemImpl createNewMenuItem(int n, int n2, int n3, int n4, CharSequence charSequence, int n5) {
        return new MenuItemImpl(this, n, n2, n3, n4, charSequence, n5);
    }

    private void dispatchPresenterUpdate(boolean bl) {
        if (this.mPresenters.isEmpty()) {
            return;
        }
        this.stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.startDispatchingItemsChanged();
                return;
            }
            WeakReference<MenuPresenter> weakReference = iterator2.next();
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            menuPresenter.updateMenuView(bl);
        } while (true);
    }

    private void dispatchRestoreInstanceState(Bundle object) {
        SparseArray sparseArray = object.getSparseParcelableArray(PRESENTER_KEY);
        if (sparseArray == null) return;
        if (this.mPresenters.isEmpty()) {
            return;
        }
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        while (iterator2.hasNext()) {
            Parcelable parcelable = iterator2.next();
            object = (MenuPresenter)parcelable.get();
            if (object == null) {
                this.mPresenters.remove((Object)parcelable);
                continue;
            }
            int n = object.getId();
            if (n <= 0 || (parcelable = (Parcelable)sparseArray.get(n)) == null) continue;
            object.onRestoreInstanceState(parcelable);
        }
    }

    private void dispatchSaveInstanceState(Bundle bundle) {
        if (this.mPresenters.isEmpty()) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        do {
            if (!iterator2.hasNext()) {
                bundle.putSparseParcelableArray(PRESENTER_KEY, sparseArray);
                return;
            }
            WeakReference<MenuPresenter> weakReference = iterator2.next();
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            int n = menuPresenter.getId();
            if (n <= 0 || (menuPresenter = menuPresenter.onSaveInstanceState()) == null) continue;
            sparseArray.put(n, (Object)menuPresenter);
        } while (true);
    }

    private boolean dispatchSubMenuSelected(SubMenuBuilder subMenuBuilder, MenuPresenter menuPresenter) {
        boolean bl = this.mPresenters.isEmpty();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (menuPresenter != null) {
            bl2 = menuPresenter.onSubMenuSelected(subMenuBuilder);
        }
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        while (iterator2.hasNext()) {
            WeakReference<MenuPresenter> weakReference = iterator2.next();
            menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            if (bl2) continue;
            bl2 = menuPresenter.onSubMenuSelected(subMenuBuilder);
        }
        return bl2;
    }

    private static int findInsertIndex(ArrayList<MenuItemImpl> arrayList, int n) {
        int n2 = arrayList.size() - 1;
        while (n2 >= 0) {
            if (arrayList.get(n2).getOrdering() <= n) {
                return n2 + 1;
            }
            --n2;
        }
        return 0;
    }

    private static int getOrdering(int n) {
        int n2 = (-65536 & n) >> 16;
        if (n2 < 0) throw new IllegalArgumentException("order does not contain a valid category.");
        int[] arrn = sCategoryToOrder;
        if (n2 >= arrn.length) throw new IllegalArgumentException("order does not contain a valid category.");
        return n & 65535 | arrn[n2] << 16;
    }

    private void removeItemAtInt(int n, boolean bl) {
        if (n < 0) return;
        if (n >= this.mItems.size()) {
            return;
        }
        this.mItems.remove(n);
        if (!bl) return;
        this.onItemsChanged(true);
    }

    private void setHeaderInternal(int n, CharSequence charSequence, int n2, Drawable drawable2, View view) {
        Resources resources = this.getResources();
        if (view != null) {
            this.mHeaderView = view;
            this.mHeaderTitle = null;
            this.mHeaderIcon = null;
        } else {
            if (n > 0) {
                this.mHeaderTitle = resources.getText(n);
            } else if (charSequence != null) {
                this.mHeaderTitle = charSequence;
            }
            if (n2 > 0) {
                this.mHeaderIcon = ContextCompat.getDrawable(this.getContext(), n2);
            } else if (drawable2 != null) {
                this.mHeaderIcon = drawable2;
            }
            this.mHeaderView = null;
        }
        this.onItemsChanged(false);
    }

    private void setShortcutsVisibleInner(boolean bl) {
        boolean bl2 = true;
        bl = bl && this.mResources.getConfiguration().keyboard != 1 && ViewConfigurationCompat.shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration.get((Context)this.mContext), this.mContext) ? bl2 : false;
        this.mShortcutsVisible = bl;
    }

    public MenuItem add(int n) {
        return this.addInternal(0, 0, 0, this.mResources.getString(n));
    }

    public MenuItem add(int n, int n2, int n3, int n4) {
        return this.addInternal(n, n2, n3, this.mResources.getString(n4));
    }

    public MenuItem add(int n, int n2, int n3, CharSequence charSequence) {
        return this.addInternal(n, n2, n3, charSequence);
    }

    public MenuItem add(CharSequence charSequence) {
        return this.addInternal(0, 0, 0, charSequence);
    }

    public int addIntentOptions(int n, int n2, int n3, ComponentName componentName, Intent[] arrintent, Intent intent, int n4, MenuItem[] arrmenuItem) {
        PackageManager packageManager = this.mContext.getPackageManager();
        int n5 = 0;
        List list = packageManager.queryIntentActivityOptions(componentName, arrintent, intent, 0);
        int n6 = list != null ? list.size() : 0;
        int n7 = n5;
        if ((n4 & 1) == 0) {
            this.removeGroup(n);
            n7 = n5;
        }
        while (n7 < n6) {
            ResolveInfo resolveInfo = (ResolveInfo)list.get(n7);
            componentName = resolveInfo.specificIndex < 0 ? intent : arrintent[resolveInfo.specificIndex];
            componentName = new Intent((Intent)componentName);
            componentName.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            componentName = this.add(n, n2, n3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent((Intent)componentName);
            if (arrmenuItem != null && resolveInfo.specificIndex >= 0) {
                arrmenuItem[resolveInfo.specificIndex] = componentName;
            }
            ++n7;
        }
        return n6;
    }

    protected MenuItem addInternal(int n, int n2, int n3, CharSequence object) {
        int n4 = MenuBuilder.getOrdering(n3);
        object = this.createNewMenuItem(n, n2, n3, n4, (CharSequence)object, this.mDefaultShowAsAction);
        Object object2 = this.mCurrentMenuInfo;
        if (object2 != null) {
            ((MenuItemImpl)object).setMenuInfo((ContextMenu.ContextMenuInfo)object2);
        }
        object2 = this.mItems;
        ((ArrayList)object2).add(MenuBuilder.findInsertIndex((ArrayList<MenuItemImpl>)object2, n4), object);
        this.onItemsChanged(true);
        return object;
    }

    public void addMenuPresenter(MenuPresenter menuPresenter) {
        this.addMenuPresenter(menuPresenter, this.mContext);
    }

    public void addMenuPresenter(MenuPresenter menuPresenter, Context context) {
        this.mPresenters.add(new WeakReference<MenuPresenter>(menuPresenter));
        menuPresenter.initForMenu(context, this);
        this.mIsActionItemsStale = true;
    }

    public SubMenu addSubMenu(int n) {
        return this.addSubMenu(0, 0, 0, this.mResources.getString(n));
    }

    public SubMenu addSubMenu(int n, int n2, int n3, int n4) {
        return this.addSubMenu(n, n2, n3, this.mResources.getString(n4));
    }

    public SubMenu addSubMenu(int n, int n2, int n3, CharSequence object) {
        object = (MenuItemImpl)this.addInternal(n, n2, n3, (CharSequence)object);
        SubMenuBuilder subMenuBuilder = new SubMenuBuilder(this.mContext, this, (MenuItemImpl)object);
        ((MenuItemImpl)object).setSubMenu(subMenuBuilder);
        return subMenuBuilder;
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return this.addSubMenu(0, 0, 0, charSequence);
    }

    public void changeMenuMode() {
        Callback callback = this.mCallback;
        if (callback == null) return;
        callback.onMenuModeChange(this);
    }

    public void clear() {
        MenuItemImpl menuItemImpl = this.mExpandedItem;
        if (menuItemImpl != null) {
            this.collapseItemActionView(menuItemImpl);
        }
        this.mItems.clear();
        this.onItemsChanged(true);
    }

    public void clearAll() {
        this.mPreventDispatchingItemsChanged = true;
        this.clear();
        this.clearHeader();
        this.mPresenters.clear();
        this.mPreventDispatchingItemsChanged = false;
        this.mItemsChangedWhileDispatchPrevented = false;
        this.mStructureChangedWhileDispatchPrevented = false;
        this.onItemsChanged(true);
    }

    public void clearHeader() {
        this.mHeaderIcon = null;
        this.mHeaderTitle = null;
        this.mHeaderView = null;
        this.onItemsChanged(false);
    }

    public void close() {
        this.close(true);
    }

    public final void close(boolean bl) {
        if (this.mIsClosing) {
            return;
        }
        this.mIsClosing = true;
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.mIsClosing = false;
                return;
            }
            WeakReference<MenuPresenter> weakReference = iterator2.next();
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            menuPresenter.onCloseMenu(this, bl);
        } while (true);
    }

    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        boolean bl = this.mPresenters.isEmpty();
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (bl) return bl4;
        if (this.mExpandedItem != menuItemImpl) {
            return bl2;
        }
        this.stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        bl4 = bl3;
        do {
            bl3 = bl4;
            if (!iterator2.hasNext()) break;
            WeakReference<MenuPresenter> weakReference = iterator2.next();
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            bl4 = bl3 = menuPresenter.collapseItemActionView(this, menuItemImpl);
            if (bl3) break;
        } while (true);
        this.startDispatchingItemsChanged();
        bl4 = bl3;
        if (!bl3) return bl4;
        this.mExpandedItem = null;
        return bl3;
    }

    boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        Callback callback = this.mCallback;
        if (callback == null) return false;
        if (!callback.onMenuItemSelected(menuBuilder, menuItem)) return false;
        return true;
    }

    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        boolean bl = this.mPresenters.isEmpty();
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        this.stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        do {
            bl = bl2;
            if (!iterator2.hasNext()) break;
            WeakReference<MenuPresenter> weakReference = iterator2.next();
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            bl2 = bl = menuPresenter.expandItemActionView(this, menuItemImpl);
            if (bl) break;
        } while (true);
        this.startDispatchingItemsChanged();
        if (!bl) return bl;
        this.mExpandedItem = menuItemImpl;
        return bl;
    }

    public int findGroupIndex(int n) {
        return this.findGroupIndex(n, 0);
    }

    public int findGroupIndex(int n, int n2) {
        int n3 = this.size();
        int n4 = n2;
        if (n2 < 0) {
            n4 = 0;
        }
        while (n4 < n3) {
            if (this.mItems.get(n4).getGroupId() == n) {
                return n4;
            }
            ++n4;
        }
        return -1;
    }

    public MenuItem findItem(int n) {
        int n2 = this.size();
        int n3 = 0;
        while (n3 < n2) {
            MenuItemImpl menuItemImpl = this.mItems.get(n3);
            if (menuItemImpl.getItemId() == n) {
                return menuItemImpl;
            }
            if (menuItemImpl.hasSubMenu() && (menuItemImpl = menuItemImpl.getSubMenu().findItem(n)) != null) {
                return menuItemImpl;
            }
            ++n3;
        }
        return null;
    }

    public int findItemIndex(int n) {
        int n2 = this.size();
        int n3 = 0;
        while (n3 < n2) {
            if (this.mItems.get(n3).getItemId() == n) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    MenuItemImpl findItemWithShortcutForKey(int n, KeyEvent object) {
        ArrayList<MenuItemImpl> arrayList = this.mTempShortcutItemList;
        arrayList.clear();
        this.findItemsWithShortcutForKey(arrayList, n, (KeyEvent)object);
        if (arrayList.isEmpty()) {
            return null;
        }
        int n2 = object.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        object.getKeyData(keyData);
        int n3 = arrayList.size();
        if (n3 == 1) {
            return arrayList.get(0);
        }
        boolean bl = this.isQwertyMode();
        int n4 = 0;
        while (n4 < n3) {
            object = arrayList.get(n4);
            char c = bl ? ((MenuItemImpl)object).getAlphabeticShortcut() : ((MenuItemImpl)object).getNumericShortcut();
            if (c == keyData.meta[0]) {
                if ((n2 & 2) == 0) return object;
            }
            if (c == keyData.meta[2]) {
                if ((n2 & 2) != 0) return object;
            }
            if (bl && c == '\b' && n == 67) {
                return object;
            }
            ++n4;
        }
        return null;
    }

    void findItemsWithShortcutForKey(List<MenuItemImpl> list, int n, KeyEvent keyEvent) {
        boolean bl = this.isQwertyMode();
        int n2 = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (!keyEvent.getKeyData(keyData) && n != 67) {
            return;
        }
        int n3 = this.mItems.size();
        int n4 = 0;
        while (n4 < n3) {
            MenuItemImpl menuItemImpl = this.mItems.get(n4);
            if (menuItemImpl.hasSubMenu()) {
                ((MenuBuilder)menuItemImpl.getSubMenu()).findItemsWithShortcutForKey(list, n, keyEvent);
            }
            char c = bl ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut();
            int n5 = bl ? menuItemImpl.getAlphabeticModifiers() : menuItemImpl.getNumericModifiers();
            if ((n5 = (n2 & 69647) == (n5 & 69647) ? 1 : 0) != 0 && c != '\u0000' && (c == keyData.meta[0] || c == keyData.meta[2] || bl && c == '\b' && n == 67) && menuItemImpl.isEnabled()) {
                list.add(menuItemImpl);
            }
            ++n4;
        }
    }

    public void flagActionItems() {
        Object object;
        ArrayList<MenuItemImpl> arrayList = this.getVisibleItems();
        if (!this.mIsActionItemsStale) {
            return;
        }
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            object = iterator2.next();
            MenuPresenter menuPresenter = (MenuPresenter)((Reference)object).get();
            if (menuPresenter == null) {
                this.mPresenters.remove(object);
                continue;
            }
            n |= menuPresenter.flagActionItems();
        }
        if (n == 0) {
            this.mActionItems.clear();
            this.mNonActionItems.clear();
            this.mNonActionItems.addAll(this.getVisibleItems());
        } else {
            this.mActionItems.clear();
            this.mNonActionItems.clear();
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                object = arrayList.get(n);
                if (((MenuItemImpl)object).isActionButton()) {
                    this.mActionItems.add((MenuItemImpl)object);
                    continue;
                }
                this.mNonActionItems.add((MenuItemImpl)object);
            }
        }
        this.mIsActionItemsStale = false;
    }

    public ArrayList<MenuItemImpl> getActionItems() {
        this.flagActionItems();
        return this.mActionItems;
    }

    protected String getActionViewStatesKey() {
        return ACTION_VIEW_STATES_KEY;
    }

    public Context getContext() {
        return this.mContext;
    }

    public MenuItemImpl getExpandedItem() {
        return this.mExpandedItem;
    }

    public Drawable getHeaderIcon() {
        return this.mHeaderIcon;
    }

    public CharSequence getHeaderTitle() {
        return this.mHeaderTitle;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    public MenuItem getItem(int n) {
        return this.mItems.get(n);
    }

    public ArrayList<MenuItemImpl> getNonActionItems() {
        this.flagActionItems();
        return this.mNonActionItems;
    }

    boolean getOptionalIconsVisible() {
        return this.mOptionalIconsVisible;
    }

    Resources getResources() {
        return this.mResources;
    }

    public MenuBuilder getRootMenu() {
        return this;
    }

    public ArrayList<MenuItemImpl> getVisibleItems() {
        if (!this.mIsVisibleItemsStale) {
            return this.mVisibleItems;
        }
        this.mVisibleItems.clear();
        int n = this.mItems.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mIsVisibleItemsStale = false;
                this.mIsActionItemsStale = true;
                return this.mVisibleItems;
            }
            MenuItemImpl menuItemImpl = this.mItems.get(n2);
            if (menuItemImpl.isVisible()) {
                this.mVisibleItems.add(menuItemImpl);
            }
            ++n2;
        } while (true);
    }

    public boolean hasVisibleItems() {
        if (this.mOverrideVisibleItems) {
            return true;
        }
        int n = this.size();
        int n2 = 0;
        while (n2 < n) {
            if (this.mItems.get(n2).isVisible()) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public boolean isGroupDividerEnabled() {
        return this.mGroupDividerEnabled;
    }

    boolean isQwertyMode() {
        return this.mQwertyMode;
    }

    public boolean isShortcutKey(int n, KeyEvent keyEvent) {
        if (this.findItemWithShortcutForKey(n, keyEvent) == null) return false;
        return true;
    }

    public boolean isShortcutsVisible() {
        return this.mShortcutsVisible;
    }

    void onItemActionRequestChanged(MenuItemImpl menuItemImpl) {
        this.mIsActionItemsStale = true;
        this.onItemsChanged(true);
    }

    void onItemVisibleChanged(MenuItemImpl menuItemImpl) {
        this.mIsVisibleItemsStale = true;
        this.onItemsChanged(true);
    }

    public void onItemsChanged(boolean bl) {
        if (this.mPreventDispatchingItemsChanged) {
            this.mItemsChangedWhileDispatchPrevented = true;
            if (!bl) return;
            this.mStructureChangedWhileDispatchPrevented = true;
            return;
        }
        if (bl) {
            this.mIsVisibleItemsStale = true;
            this.mIsActionItemsStale = true;
        }
        this.dispatchPresenterUpdate(bl);
    }

    public boolean performIdentifierAction(int n, int n2) {
        return this.performItemAction(this.findItem(n), n2);
    }

    public boolean performItemAction(MenuItem menuItem, int n) {
        return this.performItemAction(menuItem, null, n);
    }

    public boolean performItemAction(MenuItem object, MenuPresenter menuPresenter, int n) {
        boolean bl;
        Object object2 = (MenuItemImpl)object;
        if (object2 == null) return false;
        if (!((MenuItemImpl)object2).isEnabled()) {
            return false;
        }
        boolean bl2 = ((MenuItemImpl)object2).invoke();
        object = ((MenuItemImpl)object2).getSupportActionProvider();
        boolean bl3 = object != null && ((ActionProvider)object).hasSubMenu();
        if (((MenuItemImpl)object2).hasCollapsibleActionView()) {
            bl = bl2 |= ((MenuItemImpl)object2).expandActionView();
            if (!bl2) return bl;
            this.close(true);
            return bl2;
        }
        if (!((MenuItemImpl)object2).hasSubMenu() && !bl3) {
            bl = bl2;
            if ((n & 1) != 0) return bl;
            this.close(true);
            return bl2;
        }
        if ((n & 4) == 0) {
            this.close(false);
        }
        if (!((MenuItemImpl)object2).hasSubMenu()) {
            ((MenuItemImpl)object2).setSubMenu(new SubMenuBuilder(this.getContext(), this, (MenuItemImpl)object2));
        }
        object2 = (SubMenuBuilder)((MenuItemImpl)object2).getSubMenu();
        if (bl3) {
            ((ActionProvider)object).onPrepareSubMenu((SubMenu)object2);
        }
        bl = bl2 |= this.dispatchSubMenuSelected((SubMenuBuilder)object2, menuPresenter);
        if (bl2) return bl;
        this.close(true);
        return bl2;
    }

    public boolean performShortcut(int n, KeyEvent object, int n2) {
        boolean bl = (object = this.findItemWithShortcutForKey(n, (KeyEvent)object)) != null ? this.performItemAction((MenuItem)object, n2) : false;
        if ((n2 & 2) == 0) return bl;
        this.close(true);
        return bl;
    }

    public void removeGroup(int n) {
        int n2 = this.findGroupIndex(n);
        if (n2 < 0) return;
        int n3 = this.mItems.size();
        for (int i = 0; i < n3 - n2 && this.mItems.get(n2).getGroupId() == n; ++i) {
            this.removeItemAtInt(n2, false);
        }
        this.onItemsChanged(true);
    }

    public void removeItem(int n) {
        this.removeItemAtInt(this.findItemIndex(n), true);
    }

    public void removeItemAt(int n) {
        this.removeItemAtInt(n, true);
    }

    public void removeMenuPresenter(MenuPresenter menuPresenter) {
        Iterator<WeakReference<MenuPresenter>> iterator2 = this.mPresenters.iterator();
        while (iterator2.hasNext()) {
            WeakReference<MenuPresenter> weakReference = iterator2.next();
            MenuPresenter menuPresenter2 = (MenuPresenter)weakReference.get();
            if (menuPresenter2 != null && menuPresenter2 != menuPresenter) continue;
            this.mPresenters.remove(weakReference);
        }
    }

    public void restoreActionViewStates(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        SparseArray sparseArray = bundle.getSparseParcelableArray(this.getActionViewStatesKey());
        int n = this.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                n2 = bundle.getInt(EXPANDED_ACTION_VIEW_ID);
                if (n2 <= 0) return;
                bundle = this.findItem(n2);
                if (bundle == null) return;
                bundle.expandActionView();
                return;
            }
            MenuItem menuItem = this.getItem(n2);
            View view = menuItem.getActionView();
            if (view != null && view.getId() != -1) {
                view.restoreHierarchyState(sparseArray);
            }
            if (menuItem.hasSubMenu()) {
                ((SubMenuBuilder)menuItem.getSubMenu()).restoreActionViewStates(bundle);
            }
            ++n2;
        } while (true);
    }

    public void restorePresenterStates(Bundle bundle) {
        this.dispatchRestoreInstanceState(bundle);
    }

    public void saveActionViewStates(Bundle bundle) {
        int n = this.size();
        SparseArray sparseArray = null;
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (sparseArray == null) return;
                bundle.putSparseParcelableArray(this.getActionViewStatesKey(), sparseArray);
                return;
            }
            MenuItem menuItem = this.getItem(n2);
            View view = menuItem.getActionView();
            SparseArray sparseArray2 = sparseArray;
            if (view != null) {
                sparseArray2 = sparseArray;
                if (view.getId() != -1) {
                    SparseArray sparseArray3 = sparseArray;
                    if (sparseArray == null) {
                        sparseArray3 = new SparseArray();
                    }
                    view.saveHierarchyState(sparseArray3);
                    sparseArray2 = sparseArray3;
                    if (menuItem.isActionViewExpanded()) {
                        bundle.putInt(EXPANDED_ACTION_VIEW_ID, menuItem.getItemId());
                        sparseArray2 = sparseArray3;
                    }
                }
            }
            if (menuItem.hasSubMenu()) {
                ((SubMenuBuilder)menuItem.getSubMenu()).saveActionViewStates(bundle);
            }
            ++n2;
            sparseArray = sparseArray2;
        } while (true);
    }

    public void savePresenterStates(Bundle bundle) {
        this.dispatchSaveInstanceState(bundle);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setCurrentMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mCurrentMenuInfo = contextMenuInfo;
    }

    public MenuBuilder setDefaultShowAsAction(int n) {
        this.mDefaultShowAsAction = n;
        return this;
    }

    void setExclusiveItemChecked(MenuItem menuItem) {
        int n = menuItem.getGroupId();
        int n2 = this.mItems.size();
        this.stopDispatchingItemsChanged();
        int n3 = 0;
        do {
            if (n3 >= n2) {
                this.startDispatchingItemsChanged();
                return;
            }
            MenuItemImpl menuItemImpl = this.mItems.get(n3);
            if (menuItemImpl.getGroupId() == n && menuItemImpl.isExclusiveCheckable() && menuItemImpl.isCheckable()) {
                boolean bl = menuItemImpl == menuItem;
                menuItemImpl.setCheckedInt(bl);
            }
            ++n3;
        } while (true);
    }

    public void setGroupCheckable(int n, boolean bl, boolean bl2) {
        int n2 = this.mItems.size();
        int n3 = 0;
        while (n3 < n2) {
            MenuItemImpl menuItemImpl = this.mItems.get(n3);
            if (menuItemImpl.getGroupId() == n) {
                menuItemImpl.setExclusiveCheckable(bl2);
                menuItemImpl.setCheckable(bl);
            }
            ++n3;
        }
    }

    @Override
    public void setGroupDividerEnabled(boolean bl) {
        this.mGroupDividerEnabled = bl;
    }

    public void setGroupEnabled(int n, boolean bl) {
        int n2 = this.mItems.size();
        int n3 = 0;
        while (n3 < n2) {
            MenuItemImpl menuItemImpl = this.mItems.get(n3);
            if (menuItemImpl.getGroupId() == n) {
                menuItemImpl.setEnabled(bl);
            }
            ++n3;
        }
    }

    public void setGroupVisible(int n, boolean bl) {
        int n2 = this.mItems.size();
        int n3 = 0;
        boolean bl2 = false;
        do {
            if (n3 >= n2) {
                if (!bl2) return;
                this.onItemsChanged(true);
                return;
            }
            MenuItemImpl menuItemImpl = this.mItems.get(n3);
            boolean bl3 = bl2;
            if (menuItemImpl.getGroupId() == n) {
                bl3 = bl2;
                if (menuItemImpl.setVisibleInt(bl)) {
                    bl3 = true;
                }
            }
            ++n3;
            bl2 = bl3;
        } while (true);
    }

    protected MenuBuilder setHeaderIconInt(int n) {
        this.setHeaderInternal(0, null, n, null, null);
        return this;
    }

    protected MenuBuilder setHeaderIconInt(Drawable drawable2) {
        this.setHeaderInternal(0, null, 0, drawable2, null);
        return this;
    }

    protected MenuBuilder setHeaderTitleInt(int n) {
        this.setHeaderInternal(n, null, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderTitleInt(CharSequence charSequence) {
        this.setHeaderInternal(0, charSequence, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderViewInt(View view) {
        this.setHeaderInternal(0, null, 0, null, view);
        return this;
    }

    public void setOptionalIconsVisible(boolean bl) {
        this.mOptionalIconsVisible = bl;
    }

    public void setOverrideVisibleItems(boolean bl) {
        this.mOverrideVisibleItems = bl;
    }

    public void setQwertyMode(boolean bl) {
        this.mQwertyMode = bl;
        this.onItemsChanged(false);
    }

    public void setShortcutsVisible(boolean bl) {
        if (this.mShortcutsVisible == bl) {
            return;
        }
        this.setShortcutsVisibleInner(bl);
        this.onItemsChanged(false);
    }

    public int size() {
        return this.mItems.size();
    }

    public void startDispatchingItemsChanged() {
        this.mPreventDispatchingItemsChanged = false;
        if (!this.mItemsChangedWhileDispatchPrevented) return;
        this.mItemsChangedWhileDispatchPrevented = false;
        this.onItemsChanged(this.mStructureChangedWhileDispatchPrevented);
    }

    public void stopDispatchingItemsChanged() {
        if (this.mPreventDispatchingItemsChanged) return;
        this.mPreventDispatchingItemsChanged = true;
        this.mItemsChangedWhileDispatchPrevented = false;
        this.mStructureChangedWhileDispatchPrevented = false;
    }

    public static interface Callback {
        public boolean onMenuItemSelected(MenuBuilder var1, MenuItem var2);

        public void onMenuModeChange(MenuBuilder var1);
    }

    public static interface ItemInvoker {
        public boolean invokeItem(MenuItemImpl var1);
    }

}

