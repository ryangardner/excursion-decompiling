/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.SparseBooleanArray
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.R;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopup;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.ForwardingListener;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ActionProvider;
import java.util.ArrayList;

class ActionMenuPresenter
extends BaseMenuPresenter
implements ActionProvider.SubUiVisibilityListener {
    private static final String TAG = "ActionMenuPresenter";
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private boolean mExpandedActionViewsExclusive;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    OverflowMenuButton mOverflowButton;
    OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    OpenOverflowRunnable mPostedOpenRunnable;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    public ActionMenuPresenter(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
    }

    private View findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup)this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int n = viewGroup.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = viewGroup.getChildAt(n2);
            if (view instanceof MenuView.ItemView && ((MenuView.ItemView)view).getItemData() == menuItem) {
                return view;
            }
            ++n2;
        }
        return null;
    }

    @Override
    public void bindItemView(MenuItemImpl object, MenuView.ItemView itemView) {
        itemView.initialize((MenuItemImpl)object, 0);
        object = (ActionMenuView)this.mMenuView;
        itemView = (ActionMenuItemView)itemView;
        ((ActionMenuItemView)itemView).setItemInvoker((MenuBuilder.ItemInvoker)object);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        ((ActionMenuItemView)itemView).setPopupCallback(this.mPopupCallback);
    }

    public boolean dismissPopupMenus() {
        return this.hideOverflowMenu() | this.hideSubMenus();
    }

    @Override
    public boolean filterLeftoverView(ViewGroup viewGroup, int n) {
        if (viewGroup.getChildAt(n) != this.mOverflowButton) return super.filterLeftoverView(viewGroup, n);
        return false;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean flagActionItems() {
        block34 : {
            block35 : {
                var1_1 = this;
                if (var1_1.mMenu != null) {
                    var2_2 = var1_1.mMenu.getVisibleItems();
                    var3_3 = var2_2.size();
                } else {
                    var2_2 = null;
                    var3_3 = 0;
                }
                var4_4 = var1_1.mMaxItems;
                var5_5 = var1_1.mActionItemWidthLimit;
                var6_6 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                var7_7 = (ViewGroup)var1_1.mMenuView;
                var9_9 = 0;
                var10_10 = 0;
                var11_11 = 0;
                for (var8_8 = 0; var8_8 < var3_3; ++var8_8) {
                    var12_12 = var2_2.get(var8_8);
                    if (var12_12.requiresActionButton()) {
                        ++var10_10;
                    } else if (var12_12.requestsActionButton()) {
                        ++var11_11;
                    } else {
                        var9_9 = 1;
                    }
                    var13_13 = var4_4;
                    if (var1_1.mExpandedActionViewsExclusive) {
                        var13_13 = var4_4;
                        if (var12_12.isActionViewExpanded()) {
                            var13_13 = 0;
                        }
                    }
                    var4_4 = var13_13;
                }
                var8_8 = var4_4;
                if (!var1_1.mReserveOverflow) break block34;
                if (var9_9 != 0) break block35;
                var8_8 = var4_4;
                if (var11_11 + var10_10 <= var4_4) break block34;
            }
            var8_8 = var4_4 - 1;
        }
        var4_4 = var8_8 - var10_10;
        var12_12 = var1_1.mActionButtonGroups;
        var12_12.clear();
        if (var1_1.mStrictWidthLimit) {
            var10_10 = var1_1.mMinCellSize;
            var11_11 = var5_5 / var10_10;
            var14_14 = var10_10 + var5_5 % var10_10 / var11_11;
        } else {
            var14_14 = 0;
            var11_11 = 0;
        }
        var15_15 = 0;
        var10_10 = 0;
        var9_9 = var5_5;
        var5_5 = var3_3;
        do {
            block36 : {
                var1_1 = this;
                if (var15_15 >= var5_5) return true;
                var16_16 = var2_2.get(var15_15);
                if (!var16_16.requiresActionButton()) break block36;
                var17_17 = var1_1.getItemView(var16_16, null, var7_7);
                if (var1_1.mStrictWidthLimit) {
                    var11_11 -= ActionMenuView.measureChildForCells(var17_17, var14_14, var11_11, var6_6, 0);
                } else {
                    var17_17.measure(var6_6, var6_6);
                }
                var8_8 = var17_17.getMeasuredWidth();
                var13_13 = var9_9 - var8_8;
                var3_3 = var10_10;
                if (var10_10 == 0) {
                    var3_3 = var8_8;
                }
                if ((var10_10 = var16_16.getGroupId()) != 0) {
                    var12_12.put(var10_10, true);
                }
                var16_16.setIsActionButton(true);
                ** GOTO lbl126
            }
            if (var16_16.requestsActionButton()) {
                var18_18 = var16_16.getGroupId();
                var19_19 = var12_12.get(var18_18);
                var20_20 = !(var4_4 <= 0 && var19_19 == false || var9_9 <= 0 || var1_1.mStrictWidthLimit != false && var11_11 <= 0) ? 1 : 0;
                var21_21 = var20_20;
                var22_22 = var20_20;
                var13_13 = var9_9;
                var8_8 = var11_11;
                var3_3 = var10_10;
                if (var20_20 != 0) {
                    var17_17 = var1_1.getItemView(var16_16, null, var7_7);
                    if (var1_1.mStrictWidthLimit) {
                        var8_8 = ActionMenuView.measureChildForCells(var17_17, var14_14, var11_11, var6_6, 0);
                        var11_11 = var3_3 = var11_11 - var8_8;
                        if (var8_8 == 0) {
                            var21_21 = 0;
                            var11_11 = var3_3;
                        }
                    } else {
                        var17_17.measure(var6_6, var6_6);
                    }
                    var8_8 = var17_17.getMeasuredWidth();
                    var13_13 = var9_9 - var8_8;
                    var3_3 = var10_10;
                    if (var10_10 == 0) {
                        var3_3 = var8_8;
                    }
                    var10_10 = (var1_1.mStrictWidthLimit != false ? var13_13 >= 0 : var13_13 + var3_3 > 0) ? 1 : 0;
                    var22_22 = var21_21 & var10_10;
                    var8_8 = var11_11;
                }
                if (var22_22 != 0 && var18_18 != 0) {
                    var12_12.put(var18_18, true);
                    var10_10 = var4_4;
                } else {
                    var10_10 = var4_4;
                    if (var19_19) {
                        var12_12.put(var18_18, false);
                        var11_11 = 0;
                        do {
                            var10_10 = var4_4;
                            if (var11_11 >= var15_15) break;
                            var1_1 = var2_2.get(var11_11);
                            var10_10 = var4_4;
                            if (var1_1.getGroupId() == var18_18) {
                                var10_10 = var4_4;
                                if (var1_1.isActionButton()) {
                                    var10_10 = var4_4 + 1;
                                }
                                var1_1.setIsActionButton(false);
                            }
                            ++var11_11;
                            var4_4 = var10_10;
                        } while (true);
                    }
                }
                var4_4 = var10_10;
                if (var22_22 != 0) {
                    var4_4 = var10_10 - 1;
                }
                var16_16.setIsActionButton((boolean)var22_22);
                var11_11 = var8_8;
lbl126: // 2 sources:
                var9_9 = var13_13;
                var10_10 = var3_3;
            } else {
                var16_16.setIsActionButton(false);
            }
            ++var15_15;
        } while (true);
    }

    @Override
    public View getItemView(MenuItemImpl menuItemImpl, View object, ViewGroup viewGroup) {
        View view = menuItemImpl.getActionView();
        if (view == null || menuItemImpl.hasCollapsibleActionView()) {
            view = super.getItemView(menuItemImpl, (View)object, viewGroup);
        }
        int n = menuItemImpl.isActionViewExpanded() ? 8 : 0;
        view.setVisibility(n);
        object = (ActionMenuView)viewGroup;
        menuItemImpl = view.getLayoutParams();
        if (((ActionMenuView)object).checkLayoutParams((ViewGroup.LayoutParams)menuItemImpl)) return view;
        view.setLayoutParams((ViewGroup.LayoutParams)((ActionMenuView)object).generateLayoutParams((ViewGroup.LayoutParams)menuItemImpl));
        return view;
    }

    @Override
    public MenuView getMenuView(ViewGroup object) {
        MenuView menuView = this.mMenuView;
        if (menuView == (object = super.getMenuView((ViewGroup)object))) return object;
        ((ActionMenuView)object).setPresenter(this);
        return object;
    }

    public Drawable getOverflowIcon() {
        OverflowMenuButton overflowMenuButton = this.mOverflowButton;
        if (overflowMenuButton != null) {
            return overflowMenuButton.getDrawable();
        }
        if (!this.mPendingOverflowIconSet) return null;
        return this.mPendingOverflowIcon;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
            ((View)this.mMenuView).removeCallbacks((Runnable)this.mPostedOpenRunnable);
            this.mPostedOpenRunnable = null;
            return true;
        }
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup == null) return false;
        overflowPopup.dismiss();
        return true;
    }

    public boolean hideSubMenus() {
        ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
        if (actionButtonSubmenu == null) return false;
        actionButtonSubmenu.dismiss();
        return true;
    }

    @Override
    public void initForMenu(Context object, MenuBuilder menuBuilder) {
        super.initForMenu((Context)object, menuBuilder);
        menuBuilder = object.getResources();
        object = ActionBarPolicy.get((Context)object);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = ((ActionBarPolicy)object).showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = ((ActionBarPolicy)object).getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ((ActionBarPolicy)object).getMaxActionButtons();
        }
        int n = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = object = new OverflowMenuButton(this.mSystemContext);
                if (this.mPendingOverflowIconSet) {
                    ((AppCompatImageView)object).setImageDrawable(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int n2 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                this.mOverflowButton.measure(n2, n2);
            }
            n -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = n;
        this.mMinCellSize = (int)(menuBuilder.getDisplayMetrics().density * 56.0f);
    }

    public boolean isOverflowMenuShowPending() {
        if (this.mPostedOpenRunnable != null) return true;
        if (this.isOverflowMenuShowing()) return true;
        return false;
    }

    public boolean isOverflowMenuShowing() {
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup == null) return false;
        if (!overflowPopup.isShowing()) return false;
        return true;
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        this.dismissPopupMenus();
        super.onCloseMenu(menuBuilder, bl);
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu == null) return;
        this.mMenu.onItemsChanged(true);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            return;
        }
        parcelable = (SavedState)parcelable;
        if (parcelable.openSubMenuId <= 0) return;
        parcelable = this.mMenu.findItem(parcelable.openSubMenuId);
        if (parcelable == null) return;
        this.onSubMenuSelected((SubMenuBuilder)parcelable.getSubMenu());
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        boolean bl = subMenuBuilder.hasVisibleItems();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        Object object = subMenuBuilder;
        while (((SubMenuBuilder)object).getParentMenu() != this.mMenu) {
            object = (SubMenuBuilder)((SubMenuBuilder)object).getParentMenu();
        }
        View view = this.findViewForItem(((SubMenuBuilder)object).getItem());
        if (view == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        int n = subMenuBuilder.size();
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            object = subMenuBuilder.getItem(n2);
            if (object.isVisible() && object.getIcon() != null) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        this.mActionButtonPopup = object = new ActionButtonSubmenu(this.mContext, subMenuBuilder, view);
        ((MenuPopupHelper)object).setForceShowIcon(bl);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    @Override
    public void onSubUiVisibilityChanged(boolean bl) {
        if (bl) {
            super.onSubMenuSelected(null);
            return;
        }
        if (this.mMenu == null) return;
        this.mMenu.close(false);
    }

    public void setExpandedActionViewsExclusive(boolean bl) {
        this.mExpandedActionViewsExclusive = bl;
    }

    public void setItemLimit(int n) {
        this.mMaxItems = n;
        this.mMaxItemsSet = true;
    }

    public void setMenuView(ActionMenuView actionMenuView) {
        this.mMenuView = actionMenuView;
        actionMenuView.initialize(this.mMenu);
    }

    public void setOverflowIcon(Drawable drawable2) {
        OverflowMenuButton overflowMenuButton = this.mOverflowButton;
        if (overflowMenuButton != null) {
            overflowMenuButton.setImageDrawable(drawable2);
            return;
        }
        this.mPendingOverflowIconSet = true;
        this.mPendingOverflowIcon = drawable2;
    }

    public void setReserveOverflow(boolean bl) {
        this.mReserveOverflow = bl;
        this.mReserveOverflowSet = true;
    }

    public void setWidthLimit(int n, boolean bl) {
        this.mWidthLimit = n;
        this.mStrictWidthLimit = bl;
        this.mWidthLimitSet = true;
    }

    @Override
    public boolean shouldIncludeItem(int n, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    public boolean showOverflowMenu() {
        if (!this.mReserveOverflow) return false;
        if (this.isOverflowMenuShowing()) return false;
        if (this.mMenu == null) return false;
        if (this.mMenuView == null) return false;
        if (this.mPostedOpenRunnable != null) return false;
        if (this.mMenu.getNonActionItems().isEmpty()) return false;
        this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, (View)this.mOverflowButton, true));
        ((View)this.mMenuView).post((Runnable)this.mPostedOpenRunnable);
        return true;
    }

    @Override
    public void updateMenuView(boolean bl) {
        int n;
        int n2;
        super.updateMenuView(bl);
        ((View)this.mMenuView).requestLayout();
        Object object = this.mMenu;
        int n3 = 0;
        if (object != null) {
            ArrayList<MenuItemImpl> arrayList = this.mMenu.getActionItems();
            n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                object = arrayList.get(n).getSupportActionProvider();
                if (object == null) continue;
                ((ActionProvider)object).setSubUiVisibilityListener(this);
            }
        }
        object = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        n = n3;
        if (this.mReserveOverflow) {
            n = n3;
            if (object != null) {
                n2 = ((ArrayList)object).size();
                if (n2 == 1) {
                    n = ((MenuItemImpl)((ArrayList)object).get(0)).isActionViewExpanded() ^ true;
                } else {
                    n = n3;
                    if (n2 > 0) {
                        n = 1;
                    }
                }
            }
        }
        if (n != 0) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
            }
            if ((object = (ViewGroup)this.mOverflowButton.getParent()) != this.mMenuView) {
                if (object != null) {
                    object.removeView((View)this.mOverflowButton);
                }
                object = (ActionMenuView)this.mMenuView;
                object.addView((View)this.mOverflowButton, (ViewGroup.LayoutParams)((ActionMenuView)object).generateOverflowButtonLayoutParams());
            }
        } else {
            object = this.mOverflowButton;
            if (object != null && object.getParent() == this.mMenuView) {
                ((ViewGroup)this.mMenuView).removeView((View)this.mOverflowButton);
            }
        }
        ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    private class ActionButtonSubmenu
    extends MenuPopupHelper {
        public ActionButtonSubmenu(Context object, SubMenuBuilder subMenuBuilder, View view) {
            super((Context)object, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle);
            if (!((MenuItemImpl)subMenuBuilder.getItem()).isActionButton()) {
                object = ActionMenuPresenter.this.mOverflowButton == null ? (View)ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.mOverflowButton;
                this.setAnchorView((View)object);
            }
            this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override
        protected void onDismiss() {
            ActionMenuPresenter.this.mActionButtonPopup = null;
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    private class ActionMenuPopupCallback
    extends ActionMenuItemView.PopupCallback {
        ActionMenuPopupCallback() {
        }

        @Override
        public ShowableListMenu getPopup() {
            if (ActionMenuPresenter.this.mActionButtonPopup == null) return null;
            return ActionMenuPresenter.this.mActionButtonPopup.getPopup();
        }
    }

    private class OpenOverflowRunnable
    implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override
        public void run() {
            View view;
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            if ((view = (View)ActionMenuPresenter.this.mMenuView) != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private class OverflowMenuButton
    extends AppCompatImageView
    implements ActionMenuView.ActionMenuChildView {
        public OverflowMenuButton(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            this.setClickable(true);
            this.setFocusable(true);
            this.setVisibility(0);
            this.setEnabled(true);
            TooltipCompat.setTooltipText((View)this, this.getContentDescription());
            this.setOnTouchListener((View.OnTouchListener)new ForwardingListener((View)this){

                @Override
                public ShowableListMenu getPopup() {
                    if (ActionMenuPresenter.this.mOverflowPopup != null) return ActionMenuPresenter.this.mOverflowPopup.getPopup();
                    return null;
                }

                @Override
                public boolean onForwardingStarted() {
                    ActionMenuPresenter.this.showOverflowMenu();
                    return true;
                }

                @Override
                public boolean onForwardingStopped() {
                    if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false;
                    }
                    ActionMenuPresenter.this.hideOverflowMenu();
                    return true;
                }
            });
        }

        @Override
        public boolean needsDividerAfter() {
            return false;
        }

        @Override
        public boolean needsDividerBefore() {
            return false;
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            this.playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        protected boolean setFrame(int n, int n2, int n3, int n4) {
            boolean bl = super.setFrame(n, n2, n3, n4);
            Drawable drawable2 = this.getDrawable();
            Drawable drawable3 = this.getBackground();
            if (drawable2 == null) return bl;
            if (drawable3 == null) return bl;
            int n5 = this.getWidth();
            n4 = this.getHeight();
            n = Math.max(n5, n4) / 2;
            int n6 = this.getPaddingLeft();
            int n7 = this.getPaddingRight();
            n3 = this.getPaddingTop();
            n2 = this.getPaddingBottom();
            n7 = (n5 + (n6 - n7)) / 2;
            n2 = (n4 + (n3 - n2)) / 2;
            DrawableCompat.setHotspotBounds(drawable3, n7 - n, n2 - n, n7 + n, n2 + n);
            return bl;
        }

    }

    private class OverflowPopup
    extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean bl) {
            super(context, menuBuilder, view, bl, R.attr.actionOverflowMenuStyle);
            this.setGravity(8388613);
            this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override
        protected void onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    private class PopupPresenterCallback
    implements MenuPresenter.Callback {
        PopupPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
            MenuPresenter.Callback callback;
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            if ((callback = ActionMenuPresenter.this.getCallback()) == null) return;
            callback.onCloseMenu(menuBuilder, bl);
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Object object = ActionMenuPresenter.this.mMenu;
            boolean bl = false;
            if (menuBuilder == object) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)menuBuilder).getItem().getItemId();
            object = ActionMenuPresenter.this.getCallback();
            if (object == null) return bl;
            return object.onOpenSubMenu(menuBuilder);
        }
    }

    private static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public int openSubMenuId;

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.openSubMenuId);
        }

    }

}

