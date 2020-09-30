/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.ActionProvider
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnActionExpandListener
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewDebug
 *  android.view.ViewDebug$CapturedViewProperty
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 */
package androidx.appcompat.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.view.ActionProvider;

public final class MenuItemImpl
implements SupportMenuItem {
    private static final int CHECKABLE = 1;
    private static final int CHECKED = 2;
    private static final int ENABLED = 16;
    private static final int EXCLUSIVE = 4;
    private static final int HIDDEN = 8;
    private static final int IS_ACTION = 32;
    static final int NO_ICON = 0;
    private static final int SHOW_AS_ACTION_MASK = 3;
    private static final String TAG = "MenuItemImpl";
    private androidx.core.view.ActionProvider mActionProvider;
    private View mActionView;
    private final int mCategoryOrder;
    private MenuItem.OnMenuItemClickListener mClickListener;
    private CharSequence mContentDescription;
    private int mFlags = 16;
    private final int mGroup;
    private boolean mHasIconTint = false;
    private boolean mHasIconTintMode = false;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private ColorStateList mIconTintList = null;
    private PorterDuff.Mode mIconTintMode = null;
    private final int mId;
    private Intent mIntent;
    private boolean mIsActionViewExpanded = false;
    private Runnable mItemCallback;
    MenuBuilder mMenu;
    private ContextMenu.ContextMenuInfo mMenuInfo;
    private boolean mNeedToApplyIconTint = false;
    private MenuItem.OnActionExpandListener mOnActionExpandListener;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private int mShortcutAlphabeticModifiers = 4096;
    private char mShortcutNumericChar;
    private int mShortcutNumericModifiers = 4096;
    private int mShowAsAction = 0;
    private SubMenuBuilder mSubMenu;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;
    private CharSequence mTooltipText;

    MenuItemImpl(MenuBuilder menuBuilder, int n, int n2, int n3, int n4, CharSequence charSequence, int n5) {
        this.mMenu = menuBuilder;
        this.mId = n2;
        this.mGroup = n;
        this.mCategoryOrder = n3;
        this.mOrdering = n4;
        this.mTitle = charSequence;
        this.mShowAsAction = n5;
    }

    private static void appendModifier(StringBuilder stringBuilder, int n, int n2, String string2) {
        if ((n & n2) != n2) return;
        stringBuilder.append(string2);
    }

    private Drawable applyIconTintIfNecessary(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (drawable2 == null) return drawable3;
        drawable3 = drawable2;
        if (!this.mNeedToApplyIconTint) return drawable3;
        if (!this.mHasIconTint) {
            drawable3 = drawable2;
            if (!this.mHasIconTintMode) return drawable3;
        }
        drawable3 = DrawableCompat.wrap(drawable2).mutate();
        if (this.mHasIconTint) {
            DrawableCompat.setTintList(drawable3, this.mIconTintList);
        }
        if (this.mHasIconTintMode) {
            DrawableCompat.setTintMode(drawable3, this.mIconTintMode);
        }
        this.mNeedToApplyIconTint = false;
        return drawable3;
    }

    public void actionFormatChanged() {
        this.mMenu.onItemActionRequestChanged(this);
    }

    @Override
    public boolean collapseActionView() {
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null) {
            return true;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
        if (onActionExpandListener == null) return this.mMenu.collapseItemActionView(this);
        if (!onActionExpandListener.onMenuItemActionCollapse((MenuItem)this)) return false;
        return this.mMenu.collapseItemActionView(this);
    }

    @Override
    public boolean expandActionView() {
        if (!this.hasCollapsibleActionView()) {
            return false;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
        if (onActionExpandListener == null) return this.mMenu.expandItemActionView(this);
        if (!onActionExpandListener.onMenuItemActionExpand((MenuItem)this)) return false;
        return this.mMenu.expandItemActionView(this);
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    @Override
    public View getActionView() {
        Object object = this.mActionView;
        if (object != null) {
            return object;
        }
        object = this.mActionProvider;
        if (object == null) return null;
        object = ((androidx.core.view.ActionProvider)object).onCreateActionView(this);
        this.mActionView = object;
        return object;
    }

    @Override
    public int getAlphabeticModifiers() {
        return this.mShortcutAlphabeticModifiers;
    }

    public char getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar;
    }

    Runnable getCallback() {
        return this.mItemCallback;
    }

    @Override
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public int getGroupId() {
        return this.mGroup;
    }

    public Drawable getIcon() {
        Drawable drawable2 = this.mIconDrawable;
        if (drawable2 != null) {
            return this.applyIconTintIfNecessary(drawable2);
        }
        if (this.mIconResId == 0) return null;
        drawable2 = AppCompatResources.getDrawable(this.mMenu.getContext(), this.mIconResId);
        this.mIconResId = 0;
        this.mIconDrawable = drawable2;
        return this.applyIconTintIfNecessary(drawable2);
    }

    @Override
    public ColorStateList getIconTintList() {
        return this.mIconTintList;
    }

    @Override
    public PorterDuff.Mode getIconTintMode() {
        return this.mIconTintMode;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    @ViewDebug.CapturedViewProperty
    public int getItemId() {
        return this.mId;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.mMenuInfo;
    }

    @Override
    public int getNumericModifiers() {
        return this.mShortcutNumericModifiers;
    }

    public char getNumericShortcut() {
        return this.mShortcutNumericChar;
    }

    public int getOrder() {
        return this.mCategoryOrder;
    }

    public int getOrdering() {
        return this.mOrdering;
    }

    char getShortcut() {
        if (!this.mMenu.isQwertyMode()) char c;
        return c = this.mShortcutNumericChar;
        char c = this.mShortcutAlphabeticChar;
        return c;
    }

    String getShortcutLabel() {
        char c = this.getShortcut();
        if (c == '\u0000') {
            return "";
        }
        Resources resources = this.mMenu.getContext().getResources();
        StringBuilder stringBuilder = new StringBuilder();
        if (ViewConfiguration.get((Context)this.mMenu.getContext()).hasPermanentMenuKey()) {
            stringBuilder.append(resources.getString(R.string.abc_prepend_shortcut_label));
        }
        int n = this.mMenu.isQwertyMode() ? this.mShortcutAlphabeticModifiers : this.mShortcutNumericModifiers;
        MenuItemImpl.appendModifier(stringBuilder, n, 65536, resources.getString(R.string.abc_menu_meta_shortcut_label));
        MenuItemImpl.appendModifier(stringBuilder, n, 4096, resources.getString(R.string.abc_menu_ctrl_shortcut_label));
        MenuItemImpl.appendModifier(stringBuilder, n, 2, resources.getString(R.string.abc_menu_alt_shortcut_label));
        MenuItemImpl.appendModifier(stringBuilder, n, 1, resources.getString(R.string.abc_menu_shift_shortcut_label));
        MenuItemImpl.appendModifier(stringBuilder, n, 4, resources.getString(R.string.abc_menu_sym_shortcut_label));
        MenuItemImpl.appendModifier(stringBuilder, n, 8, resources.getString(R.string.abc_menu_function_shortcut_label));
        if (c == '\b') {
            stringBuilder.append(resources.getString(R.string.abc_menu_delete_shortcut_label));
            return stringBuilder.toString();
        }
        if (c == '\n') {
            stringBuilder.append(resources.getString(R.string.abc_menu_enter_shortcut_label));
            return stringBuilder.toString();
        }
        if (c != ' ') {
            stringBuilder.append(c);
            return stringBuilder.toString();
        }
        stringBuilder.append(resources.getString(R.string.abc_menu_space_shortcut_label));
        return stringBuilder.toString();
    }

    public SubMenu getSubMenu() {
        return this.mSubMenu;
    }

    @Override
    public androidx.core.view.ActionProvider getSupportActionProvider() {
        return this.mActionProvider;
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.mTitleCondensed;
        if (charSequence == null) {
            charSequence = this.mTitle;
        }
        CharSequence charSequence2 = charSequence;
        if (Build.VERSION.SDK_INT >= 18) return charSequence2;
        charSequence2 = charSequence;
        if (charSequence == null) return charSequence2;
        charSequence2 = charSequence;
        if (charSequence instanceof String) return charSequence2;
        return charSequence.toString();
    }

    CharSequence getTitleForItemView(MenuView.ItemView object) {
        if (object == null) return this.getTitle();
        if (!object.prefersCondensedTitle()) return this.getTitle();
        return this.getTitleCondensed();
    }

    @Override
    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public boolean hasCollapsibleActionView() {
        androidx.core.view.ActionProvider actionProvider;
        boolean bl;
        int n = this.mShowAsAction;
        boolean bl2 = bl = false;
        if ((n & 8) == 0) return bl2;
        if (this.mActionView == null && (actionProvider = this.mActionProvider) != null) {
            this.mActionView = actionProvider.onCreateActionView(this);
        }
        bl2 = bl;
        if (this.mActionView == null) return bl2;
        return true;
    }

    public boolean hasSubMenu() {
        if (this.mSubMenu == null) return false;
        return true;
    }

    public boolean invoke() {
        Object object = this.mClickListener;
        if (object != null && object.onMenuItemClick((MenuItem)this)) {
            return true;
        }
        object = this.mMenu;
        if (((MenuBuilder)object).dispatchMenuItemSelected((MenuBuilder)object, this)) {
            return true;
        }
        object = this.mItemCallback;
        if (object != null) {
            object.run();
            return true;
        }
        if (this.mIntent != null) {
            try {
                this.mMenu.getContext().startActivity(this.mIntent);
                return true;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                Log.e((String)TAG, (String)"Can't find activity to handle intent; ignoring", (Throwable)activityNotFoundException);
            }
        }
        if ((object = this.mActionProvider) == null) return false;
        if (!((androidx.core.view.ActionProvider)object).onPerformDefaultAction()) return false;
        return true;
    }

    public boolean isActionButton() {
        if ((this.mFlags & 32) != 32) return false;
        return true;
    }

    @Override
    public boolean isActionViewExpanded() {
        return this.mIsActionViewExpanded;
    }

    public boolean isCheckable() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) != 1) return false;
        return bl;
    }

    public boolean isChecked() {
        if ((this.mFlags & 2) != 2) return false;
        return true;
    }

    public boolean isEnabled() {
        if ((this.mFlags & 16) == 0) return false;
        return true;
    }

    public boolean isExclusiveCheckable() {
        if ((this.mFlags & 4) == 0) return false;
        return true;
    }

    public boolean isVisible() {
        androidx.core.view.ActionProvider actionProvider = this.mActionProvider;
        boolean bl = true;
        boolean bl2 = true;
        if (actionProvider != null && actionProvider.overridesItemVisibility()) {
            if ((this.mFlags & 8) != 0) return false;
            if (!this.mActionProvider.isVisible()) return false;
            return bl2;
        }
        if ((this.mFlags & 8) != 0) return false;
        return bl;
    }

    public boolean requestsActionButton() {
        int n = this.mShowAsAction;
        boolean bl = true;
        if ((n & 1) != 1) return false;
        return bl;
    }

    @Override
    public boolean requiresActionButton() {
        if ((this.mShowAsAction & 2) != 2) return false;
        return true;
    }

    @Override
    public boolean requiresOverflow() {
        if (this.requiresActionButton()) return false;
        if (this.requestsActionButton()) return false;
        return true;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    @Override
    public SupportMenuItem setActionView(int n) {
        Context context = this.mMenu.getContext();
        this.setActionView(LayoutInflater.from((Context)context).inflate(n, (ViewGroup)new LinearLayout(context), false));
        return this;
    }

    @Override
    public SupportMenuItem setActionView(View view) {
        int n;
        this.mActionView = view;
        this.mActionProvider = null;
        if (view != null && view.getId() == -1 && (n = this.mId) > 0) {
            view.setId(n);
        }
        this.mMenu.onItemActionRequestChanged(this);
        return this;
    }

    public void setActionViewExpanded(boolean bl) {
        this.mIsActionViewExpanded = bl;
        this.mMenu.onItemsChanged(false);
    }

    public MenuItem setAlphabeticShortcut(char c) {
        if (this.mShortcutAlphabeticChar == c) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c, int n) {
        if (this.mShortcutAlphabeticChar == c && this.mShortcutAlphabeticModifiers == n) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState((int)n);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setCallback(Runnable runnable2) {
        this.mItemCallback = runnable2;
        return this;
    }

    public MenuItem setCheckable(boolean bl) {
        int n;
        int n2 = this.mFlags;
        this.mFlags = n = bl | n2 & -2;
        if (n2 == n) return this;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setChecked(boolean bl) {
        if ((this.mFlags & 4) != 0) {
            this.mMenu.setExclusiveItemChecked(this);
            return this;
        }
        this.setCheckedInt(bl);
        return this;
    }

    void setCheckedInt(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 2 : 0;
        this.mFlags = n2 |= n & -3;
        if (n == n2) return;
        this.mMenu.onItemsChanged(false);
    }

    @Override
    public SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setEnabled(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 16) : (this.mFlags &= -17);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public void setExclusiveCheckable(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 4 : 0;
        this.mFlags = n2 | n & -5;
    }

    public MenuItem setIcon(int n) {
        this.mIconDrawable = null;
        this.mIconResId = n;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setIcon(Drawable drawable2) {
        this.mIconResId = 0;
        this.mIconDrawable = drawable2;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.mHasIconTint = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode;
        this.mHasIconTintMode = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.mIntent = intent;
        return this;
    }

    public void setIsActionButton(boolean bl) {
        if (bl) {
            this.mFlags |= 32;
            return;
        }
        this.mFlags &= -33;
    }

    void setMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mMenuInfo = contextMenuInfo;
    }

    public MenuItem setNumericShortcut(char c) {
        if (this.mShortcutNumericChar == c) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c, int n) {
        if (this.mShortcutNumericChar == c && this.mShortcutNumericModifiers == n) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState((int)n);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener;
        return this;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        this.mShortcutNumericChar = c;
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setShortcut(char c, char c2, int n, int n2) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState((int)n);
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState((int)n2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public void setShowAsAction(int n) {
        int n2 = n & 3;
        if (n2 != 0 && n2 != 1) {
            if (n2 != 2) throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
        this.mShowAsAction = n;
        this.mMenu.onItemActionRequestChanged(this);
    }

    @Override
    public SupportMenuItem setShowAsActionFlags(int n) {
        this.setShowAsAction(n);
        return this;
    }

    public void setSubMenu(SubMenuBuilder subMenuBuilder) {
        this.mSubMenu = subMenuBuilder;
        subMenuBuilder.setHeaderTitle(this.getTitle());
    }

    @Override
    public SupportMenuItem setSupportActionProvider(androidx.core.view.ActionProvider actionProvider) {
        androidx.core.view.ActionProvider actionProvider2 = this.mActionProvider;
        if (actionProvider2 != null) {
            actionProvider2.reset();
        }
        this.mActionView = null;
        this.mActionProvider = actionProvider;
        this.mMenu.onItemsChanged(true);
        actionProvider = this.mActionProvider;
        if (actionProvider == null) return this;
        actionProvider.setVisibilityListener(new ActionProvider.VisibilityListener(){

            @Override
            public void onActionProviderVisibilityChanged(boolean bl) {
                MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this);
            }
        });
        return this;
    }

    public MenuItem setTitle(int n) {
        return this.setTitle(this.mMenu.getContext().getString(n));
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.mMenu.onItemsChanged(false);
        SubMenuBuilder subMenuBuilder = this.mSubMenu;
        if (subMenuBuilder == null) return this;
        subMenuBuilder.setHeaderTitle(charSequence);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setVisible(boolean bl) {
        if (!this.setVisibleInt(bl)) return this;
        this.mMenu.onItemVisibleChanged(this);
        return this;
    }

    boolean setVisibleInt(boolean bl) {
        int n = this.mFlags;
        boolean bl2 = false;
        int n2 = bl ? 0 : 8;
        this.mFlags = n2 |= n & -9;
        bl = bl2;
        if (n == n2) return bl;
        return true;
    }

    public boolean shouldShowIcon() {
        return this.mMenu.getOptionalIconsVisible();
    }

    boolean shouldShowShortcut() {
        if (!this.mMenu.isShortcutsVisible()) return false;
        if (this.getShortcut() == '\u0000') return false;
        return true;
    }

    public boolean showsTextAsAction() {
        if ((this.mShowAsAction & 4) != 4) return false;
        return true;
    }

    public String toString() {
        CharSequence charSequence = this.mTitle;
        if (charSequence == null) return null;
        return charSequence.toString();
    }

}

