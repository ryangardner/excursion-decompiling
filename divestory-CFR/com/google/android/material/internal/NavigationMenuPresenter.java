/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.google.android.material.R;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.internal.ParcelableSparseArray;
import java.util.ArrayList;

public class NavigationMenuPresenter
implements MenuPresenter {
    private static final String STATE_ADAPTER = "android:menu:adapter";
    private static final String STATE_HEADER = "android:menu:header";
    private static final String STATE_HIERARCHY = "android:menu:list";
    NavigationMenuAdapter adapter;
    private MenuPresenter.Callback callback;
    boolean hasCustomItemIconSize;
    LinearLayout headerLayout;
    ColorStateList iconTintList;
    private int id;
    boolean isBehindStatusBar = true;
    Drawable itemBackground;
    int itemHorizontalPadding;
    int itemIconPadding;
    int itemIconSize;
    private int itemMaxLines;
    LayoutInflater layoutInflater;
    MenuBuilder menu;
    private NavigationMenuView menuView;
    final View.OnClickListener onClickListener = new View.OnClickListener(){

        public void onClick(View object) {
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)object;
            object = NavigationMenuPresenter.this;
            boolean bl = true;
            ((NavigationMenuPresenter)object).setUpdateSuspended(true);
            object = navigationMenuItemView.getItemData();
            boolean bl2 = NavigationMenuPresenter.this.menu.performItemAction((MenuItem)object, NavigationMenuPresenter.this, 0);
            if (object != null && ((MenuItemImpl)object).isCheckable() && bl2) {
                NavigationMenuPresenter.this.adapter.setCheckedItem((MenuItemImpl)object);
            } else {
                bl = false;
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            if (!bl) return;
            NavigationMenuPresenter.this.updateMenuView(false);
        }
    };
    private int overScrollMode = -1;
    int paddingSeparator;
    private int paddingTopDefault;
    int textAppearance;
    boolean textAppearanceSet;
    ColorStateList textColor;

    private void updateTopPadding() {
        int n = this.headerLayout.getChildCount() == 0 && this.isBehindStatusBar ? this.paddingTopDefault : 0;
        NavigationMenuView navigationMenuView = this.menuView;
        navigationMenuView.setPadding(0, n, 0, navigationMenuView.getPaddingBottom());
    }

    public void addHeaderView(View object) {
        this.headerLayout.addView(object);
        object = this.menuView;
        object.setPadding(0, 0, 0, object.getPaddingBottom());
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public void dispatchApplyWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        int n = windowInsetsCompat.getSystemWindowInsetTop();
        if (this.paddingTopDefault != n) {
            this.paddingTopDefault = n;
            this.updateTopPadding();
        }
        NavigationMenuView navigationMenuView = this.menuView;
        navigationMenuView.setPadding(0, navigationMenuView.getPaddingTop(), 0, windowInsetsCompat.getSystemWindowInsetBottom());
        ViewCompat.dispatchApplyWindowInsets((View)this.headerLayout, windowInsetsCompat);
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    public MenuItemImpl getCheckedItem() {
        return this.adapter.getCheckedItem();
    }

    public int getHeaderCount() {
        return this.headerLayout.getChildCount();
    }

    public View getHeaderView(int n) {
        return this.headerLayout.getChildAt(n);
    }

    @Override
    public int getId() {
        return this.id;
    }

    public Drawable getItemBackground() {
        return this.itemBackground;
    }

    public int getItemHorizontalPadding() {
        return this.itemHorizontalPadding;
    }

    public int getItemIconPadding() {
        return this.itemIconPadding;
    }

    public int getItemMaxLines() {
        return this.itemMaxLines;
    }

    public ColorStateList getItemTextColor() {
        return this.textColor;
    }

    public ColorStateList getItemTintList() {
        return this.iconTintList;
    }

    @Override
    public MenuView getMenuView(ViewGroup viewGroup) {
        int n;
        if (this.menuView != null) return this.menuView;
        viewGroup = (NavigationMenuView)this.layoutInflater.inflate(R.layout.design_navigation_menu, viewGroup, false);
        this.menuView = viewGroup;
        viewGroup.setAccessibilityDelegateCompat(new NavigationMenuViewAccessibilityDelegate(this.menuView));
        if (this.adapter == null) {
            this.adapter = new NavigationMenuAdapter();
        }
        if ((n = this.overScrollMode) != -1) {
            this.menuView.setOverScrollMode(n);
        }
        this.headerLayout = (LinearLayout)this.layoutInflater.inflate(R.layout.design_navigation_item_header, (ViewGroup)this.menuView, false);
        this.menuView.setAdapter(this.adapter);
        return this.menuView;
    }

    public View inflateHeaderView(int n) {
        View view = this.layoutInflater.inflate(n, (ViewGroup)this.headerLayout, false);
        this.addHeaderView(view);
        return view;
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.layoutInflater = LayoutInflater.from((Context)context);
        this.menu = menuBuilder;
        this.paddingSeparator = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    public boolean isBehindStatusBar() {
        return this.isBehindStatusBar;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        MenuPresenter.Callback callback = this.callback;
        if (callback == null) return;
        callback.onCloseMenu(menuBuilder, bl);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) return;
        SparseArray sparseArray = (parcelable = (Bundle)parcelable).getSparseParcelableArray(STATE_HIERARCHY);
        if (sparseArray != null) {
            this.menuView.restoreHierarchyState(sparseArray);
        }
        if ((sparseArray = parcelable.getBundle(STATE_ADAPTER)) != null) {
            this.adapter.restoreInstanceState((Bundle)sparseArray);
        }
        if ((parcelable = parcelable.getSparseParcelableArray(STATE_HEADER)) == null) return;
        this.headerLayout.restoreHierarchyState((SparseArray)parcelable);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        NavigationMenuAdapter navigationMenuAdapter;
        Bundle bundle = new Bundle();
        if (this.menuView != null) {
            navigationMenuAdapter = new SparseArray();
            this.menuView.saveHierarchyState((SparseArray)navigationMenuAdapter);
            bundle.putSparseParcelableArray(STATE_HIERARCHY, (SparseArray)navigationMenuAdapter);
        }
        if ((navigationMenuAdapter = this.adapter) != null) {
            bundle.putBundle(STATE_ADAPTER, navigationMenuAdapter.createInstanceState());
        }
        if (this.headerLayout == null) return bundle;
        navigationMenuAdapter = new SparseArray();
        this.headerLayout.saveHierarchyState((SparseArray)navigationMenuAdapter);
        bundle.putSparseParcelableArray(STATE_HEADER, (SparseArray)navigationMenuAdapter);
        return bundle;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    public void removeHeaderView(View object) {
        this.headerLayout.removeView(object);
        if (this.headerLayout.getChildCount() != 0) return;
        object = this.menuView;
        object.setPadding(0, this.paddingTopDefault, 0, object.getPaddingBottom());
    }

    public void setBehindStatusBar(boolean bl) {
        if (this.isBehindStatusBar == bl) return;
        this.isBehindStatusBar = bl;
        this.updateTopPadding();
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.callback = callback;
    }

    public void setCheckedItem(MenuItemImpl menuItemImpl) {
        this.adapter.setCheckedItem(menuItemImpl);
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setItemBackground(Drawable drawable2) {
        this.itemBackground = drawable2;
        this.updateMenuView(false);
    }

    public void setItemHorizontalPadding(int n) {
        this.itemHorizontalPadding = n;
        this.updateMenuView(false);
    }

    public void setItemIconPadding(int n) {
        this.itemIconPadding = n;
        this.updateMenuView(false);
    }

    public void setItemIconSize(int n) {
        if (this.itemIconSize == n) return;
        this.itemIconSize = n;
        this.hasCustomItemIconSize = true;
        this.updateMenuView(false);
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.iconTintList = colorStateList;
        this.updateMenuView(false);
    }

    public void setItemMaxLines(int n) {
        this.itemMaxLines = n;
        this.updateMenuView(false);
    }

    public void setItemTextAppearance(int n) {
        this.textAppearance = n;
        this.textAppearanceSet = true;
        this.updateMenuView(false);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.textColor = colorStateList;
        this.updateMenuView(false);
    }

    public void setOverScrollMode(int n) {
        this.overScrollMode = n;
        NavigationMenuView navigationMenuView = this.menuView;
        if (navigationMenuView == null) return;
        navigationMenuView.setOverScrollMode(n);
    }

    public void setUpdateSuspended(boolean bl) {
        NavigationMenuAdapter navigationMenuAdapter = this.adapter;
        if (navigationMenuAdapter == null) return;
        navigationMenuAdapter.setUpdateSuspended(bl);
    }

    @Override
    public void updateMenuView(boolean bl) {
        NavigationMenuAdapter navigationMenuAdapter = this.adapter;
        if (navigationMenuAdapter == null) return;
        navigationMenuAdapter.update();
    }

    private static class HeaderViewHolder
    extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private class NavigationMenuAdapter
    extends RecyclerView.Adapter<ViewHolder> {
        private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
        private static final String STATE_CHECKED_ITEM = "android:menu:checked";
        private static final int VIEW_TYPE_HEADER = 3;
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_SEPARATOR = 2;
        private static final int VIEW_TYPE_SUBHEADER = 1;
        private MenuItemImpl checkedItem;
        private final ArrayList<NavigationMenuItem> items = new ArrayList();
        private boolean updateSuspended;

        NavigationMenuAdapter() {
            this.prepareMenuItems();
        }

        private void appendTransparentIconIfMissing(int n, int n2) {
            while (n < n2) {
                ((NavigationMenuTextItem)this.items.get((int)n)).needsEmptyIcon = true;
                ++n;
            }
        }

        private void prepareMenuItems() {
            if (this.updateSuspended) {
                return;
            }
            this.updateSuspended = true;
            this.items.clear();
            this.items.add(new NavigationMenuHeaderItem());
            int n = -1;
            int n2 = NavigationMenuPresenter.this.menu.getVisibleItems().size();
            int n3 = 0;
            boolean bl = false;
            int n4 = 0;
            do {
                int n5;
                int n6;
                Object object;
                int n7;
                boolean bl2;
                if (n3 >= n2) {
                    this.updateSuspended = false;
                    return;
                }
                MenuItemImpl menuItemImpl = NavigationMenuPresenter.this.menu.getVisibleItems().get(n3);
                if (menuItemImpl.isChecked()) {
                    this.setCheckedItem(menuItemImpl);
                }
                if (menuItemImpl.isCheckable()) {
                    menuItemImpl.setExclusiveCheckable(false);
                }
                if (menuItemImpl.hasSubMenu()) {
                    object = menuItemImpl.getSubMenu();
                    n6 = n;
                    bl2 = bl;
                    n5 = n4;
                    if (object.hasVisibleItems()) {
                        if (n3 != 0) {
                            this.items.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, 0));
                        }
                        this.items.add(new NavigationMenuTextItem(menuItemImpl));
                        int n8 = this.items.size();
                        int n9 = object.size();
                        n7 = 0;
                        for (n6 = 0; n6 < n9; ++n6) {
                            MenuItemImpl menuItemImpl2 = (MenuItemImpl)object.getItem(n6);
                            n5 = n7;
                            if (menuItemImpl2.isVisible()) {
                                n5 = n7;
                                if (n7 == 0) {
                                    n5 = n7;
                                    if (menuItemImpl2.getIcon() != null) {
                                        n5 = 1;
                                    }
                                }
                                if (menuItemImpl2.isCheckable()) {
                                    menuItemImpl2.setExclusiveCheckable(false);
                                }
                                if (menuItemImpl.isChecked()) {
                                    this.setCheckedItem(menuItemImpl);
                                }
                                this.items.add(new NavigationMenuTextItem(menuItemImpl2));
                            }
                            n7 = n5;
                        }
                        n6 = n;
                        bl2 = bl;
                        n5 = n4;
                        if (n7 != 0) {
                            this.appendTransparentIconIfMissing(n8, this.items.size());
                            n6 = n;
                            bl2 = bl;
                            n5 = n4;
                        }
                    }
                } else {
                    n6 = menuItemImpl.getGroupId();
                    if (n6 != n) {
                        n4 = this.items.size();
                        bl = menuItemImpl.getIcon() != null;
                        bl2 = bl;
                        n7 = n4;
                        if (n3 != 0) {
                            n7 = n4 + 1;
                            this.items.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, NavigationMenuPresenter.this.paddingSeparator));
                            bl2 = bl;
                        }
                    } else {
                        bl2 = bl;
                        n7 = n4;
                        if (!bl) {
                            bl2 = bl;
                            n7 = n4;
                            if (menuItemImpl.getIcon() != null) {
                                this.appendTransparentIconIfMissing(n4, this.items.size());
                                bl2 = true;
                                n7 = n4;
                            }
                        }
                    }
                    object = new NavigationMenuTextItem(menuItemImpl);
                    object.needsEmptyIcon = bl2;
                    this.items.add((NavigationMenuItem)object);
                    n5 = n7;
                }
                ++n3;
                n = n6;
                bl = bl2;
                n4 = n5;
            } while (true);
        }

        public Bundle createInstanceState() {
            Bundle bundle = new Bundle();
            Object object = this.checkedItem;
            if (object != null) {
                bundle.putInt(STATE_CHECKED_ITEM, ((MenuItemImpl)object).getItemId());
            }
            SparseArray sparseArray = new SparseArray();
            int n = 0;
            int n2 = this.items.size();
            do {
                MenuItemImpl menuItemImpl;
                if (n >= n2) {
                    bundle.putSparseParcelableArray(STATE_ACTION_VIEWS, sparseArray);
                    return bundle;
                }
                object = this.items.get(n);
                if (object instanceof NavigationMenuTextItem && (object = (menuItemImpl = ((NavigationMenuTextItem)object).getMenuItem()) != null ? menuItemImpl.getActionView() : null) != null) {
                    ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                    object.saveHierarchyState((SparseArray)parcelableSparseArray);
                    sparseArray.put(menuItemImpl.getItemId(), (Object)parcelableSparseArray);
                }
                ++n;
            } while (true);
        }

        public MenuItemImpl getCheckedItem() {
            return this.checkedItem;
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public int getItemViewType(int n) {
            NavigationMenuItem navigationMenuItem = this.items.get(n);
            if (navigationMenuItem instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (navigationMenuItem instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (!(navigationMenuItem instanceof NavigationMenuTextItem)) throw new RuntimeException("Unknown item type.");
            if (!((NavigationMenuTextItem)navigationMenuItem).getMenuItem().hasSubMenu()) return 0;
            return 1;
        }

        int getRowCount() {
            int n = NavigationMenuPresenter.this.headerLayout.getChildCount();
            int n2 = 0;
            n = n == 0 ? 0 : 1;
            while (n2 < NavigationMenuPresenter.this.adapter.getItemCount()) {
                int n3 = n;
                if (NavigationMenuPresenter.this.adapter.getItemViewType(n2) == 0) {
                    n3 = n + 1;
                }
                ++n2;
                n = n3;
            }
            return n;
        }

        @Override
        public void onBindViewHolder(ViewHolder object, int n) {
            int n2 = this.getItemViewType(n);
            if (n2 != 0) {
                if (n2 == 1) {
                    ((TextView)((ViewHolder)object).itemView).setText(((NavigationMenuTextItem)this.items.get(n)).getMenuItem().getTitle());
                    return;
                }
                if (n2 != 2) {
                    return;
                }
                NavigationMenuSeparatorItem navigationMenuSeparatorItem = (NavigationMenuSeparatorItem)this.items.get(n);
                ((ViewHolder)object).itemView.setPadding(0, navigationMenuSeparatorItem.getPaddingTop(), 0, navigationMenuSeparatorItem.getPaddingBottom());
                return;
            }
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)((ViewHolder)object).itemView;
            navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.iconTintList);
            if (NavigationMenuPresenter.this.textAppearanceSet) {
                navigationMenuItemView.setTextAppearance(NavigationMenuPresenter.this.textAppearance);
            }
            if (NavigationMenuPresenter.this.textColor != null) {
                navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.textColor);
            }
            object = NavigationMenuPresenter.this.itemBackground != null ? NavigationMenuPresenter.this.itemBackground.getConstantState().newDrawable() : null;
            ViewCompat.setBackground((View)navigationMenuItemView, (Drawable)object);
            object = (NavigationMenuTextItem)this.items.get(n);
            navigationMenuItemView.setNeedsEmptyIcon(((NavigationMenuTextItem)object).needsEmptyIcon);
            navigationMenuItemView.setHorizontalPadding(NavigationMenuPresenter.this.itemHorizontalPadding);
            navigationMenuItemView.setIconPadding(NavigationMenuPresenter.this.itemIconPadding);
            if (NavigationMenuPresenter.this.hasCustomItemIconSize) {
                navigationMenuItemView.setIconSize(NavigationMenuPresenter.this.itemIconSize);
            }
            navigationMenuItemView.setMaxLines(NavigationMenuPresenter.this.itemMaxLines);
            navigationMenuItemView.initialize(((NavigationMenuTextItem)object).getMenuItem(), 0);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            if (n == 0) return new NormalViewHolder(NavigationMenuPresenter.this.layoutInflater, viewGroup, NavigationMenuPresenter.this.onClickListener);
            if (n == 1) return new SubheaderViewHolder(NavigationMenuPresenter.this.layoutInflater, viewGroup);
            if (n == 2) return new SeparatorViewHolder(NavigationMenuPresenter.this.layoutInflater, viewGroup);
            if (n == 3) return new HeaderViewHolder((View)NavigationMenuPresenter.this.headerLayout);
            return null;
        }

        @Override
        public void onViewRecycled(ViewHolder viewHolder) {
            if (!(viewHolder instanceof NormalViewHolder)) return;
            ((NavigationMenuItemView)viewHolder.itemView).recycle();
        }

        public void restoreInstanceState(Bundle bundle) {
            Object object;
            int n;
            int n2 = 0;
            int n3 = bundle.getInt(STATE_CHECKED_ITEM, 0);
            if (n3 != 0) {
                this.updateSuspended = true;
                int n4 = this.items.size();
                for (n = 0; n < n4; ++n) {
                    object = this.items.get(n);
                    if (!(object instanceof NavigationMenuTextItem) || (object = ((NavigationMenuTextItem)object).getMenuItem()) == null || ((MenuItemImpl)object).getItemId() != n3) continue;
                    this.setCheckedItem((MenuItemImpl)object);
                    break;
                }
                this.updateSuspended = false;
                this.prepareMenuItems();
            }
            if ((bundle = bundle.getSparseParcelableArray(STATE_ACTION_VIEWS)) == null) return;
            n3 = this.items.size();
            n = n2;
            while (n < n3) {
                Object object2;
                object = this.items.get(n);
                if (object instanceof NavigationMenuTextItem && (object2 = ((NavigationMenuTextItem)object).getMenuItem()) != null && (object = object2.getActionView()) != null && (object2 = (ParcelableSparseArray)((Object)bundle.get(object2.getItemId()))) != null) {
                    object.restoreHierarchyState((SparseArray)object2);
                }
                ++n;
            }
        }

        public void setCheckedItem(MenuItemImpl menuItemImpl) {
            if (this.checkedItem == menuItemImpl) return;
            if (!menuItemImpl.isCheckable()) {
                return;
            }
            MenuItemImpl menuItemImpl2 = this.checkedItem;
            if (menuItemImpl2 != null) {
                menuItemImpl2.setChecked(false);
            }
            this.checkedItem = menuItemImpl;
            menuItemImpl.setChecked(true);
        }

        public void setUpdateSuspended(boolean bl) {
            this.updateSuspended = bl;
        }

        public void update() {
            this.prepareMenuItems();
            this.notifyDataSetChanged();
        }
    }

    private static class NavigationMenuHeaderItem
    implements NavigationMenuItem {
        NavigationMenuHeaderItem() {
        }
    }

    private static interface NavigationMenuItem {
    }

    private static class NavigationMenuSeparatorItem
    implements NavigationMenuItem {
        private final int paddingBottom;
        private final int paddingTop;

        public NavigationMenuSeparatorItem(int n, int n2) {
            this.paddingTop = n;
            this.paddingBottom = n2;
        }

        public int getPaddingBottom() {
            return this.paddingBottom;
        }

        public int getPaddingTop() {
            return this.paddingTop;
        }
    }

    private static class NavigationMenuTextItem
    implements NavigationMenuItem {
        private final MenuItemImpl menuItem;
        boolean needsEmptyIcon;

        NavigationMenuTextItem(MenuItemImpl menuItemImpl) {
            this.menuItem = menuItemImpl;
        }

        public MenuItemImpl getMenuItem() {
            return this.menuItem;
        }
    }

    private class NavigationMenuViewAccessibilityDelegate
    extends RecyclerViewAccessibilityDelegate {
        NavigationMenuViewAccessibilityDelegate(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(NavigationMenuPresenter.this.adapter.getRowCount(), 0, false));
        }
    }

    private static class NormalViewHolder
    extends ViewHolder {
        public NormalViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    private static class SeparatorViewHolder
    extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    private static class SubheaderViewHolder
    extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    private static abstract class ViewHolder
    extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

}

