/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package com.google.android.material.bottomnavigation;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationPresenter;
import com.google.android.material.internal.TextScale;
import java.util.ArrayList;
import java.util.HashSet;

public class BottomNavigationMenuView
extends ViewGroup
implements MenuView {
    private static final long ACTIVE_ANIMATION_DURATION_MS = 115L;
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int[] DISABLED_STATE_SET = new int[]{-16842910};
    private static final int ITEM_POOL_SIZE = 5;
    private final int activeItemMaxWidth;
    private final int activeItemMinWidth;
    private SparseArray<BadgeDrawable> badgeDrawables = new SparseArray(5);
    private BottomNavigationItemView[] buttons;
    private final int inactiveItemMaxWidth;
    private final int inactiveItemMinWidth;
    private Drawable itemBackground;
    private int itemBackgroundRes;
    private final int itemHeight;
    private boolean itemHorizontalTranslationEnabled;
    private int itemIconSize;
    private ColorStateList itemIconTint;
    private final Pools.Pool<BottomNavigationItemView> itemPool = new Pools.SynchronizedPool<BottomNavigationItemView>(5);
    private int itemTextAppearanceActive;
    private int itemTextAppearanceInactive;
    private final ColorStateList itemTextColorDefault;
    private ColorStateList itemTextColorFromUser;
    private int labelVisibilityMode;
    private MenuBuilder menu;
    private final View.OnClickListener onClickListener;
    private BottomNavigationPresenter presenter;
    private int selectedItemId = 0;
    private int selectedItemPosition = 0;
    private final TransitionSet set;
    private int[] tempChildWidths;

    public BottomNavigationMenuView(Context context) {
        this(context, null);
    }

    public BottomNavigationMenuView(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = this.getResources();
        this.inactiveItemMaxWidth = object.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
        this.inactiveItemMinWidth = object.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
        this.activeItemMaxWidth = object.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
        this.activeItemMinWidth = object.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_min_width);
        this.itemHeight = object.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
        this.itemTextColorDefault = this.createDefaultColorStateList(16842808);
        object = new AutoTransition();
        this.set = object;
        ((TransitionSet)object).setOrdering(0);
        this.set.setDuration(115L);
        this.set.setInterpolator((TimeInterpolator)new FastOutSlowInInterpolator());
        this.set.addTransition(new TextScale());
        this.onClickListener = new View.OnClickListener(){

            public void onClick(View object) {
                object = ((BottomNavigationItemView)object).getItemData();
                if (BottomNavigationMenuView.this.menu.performItemAction((MenuItem)object, BottomNavigationMenuView.this.presenter, 0)) return;
                object.setChecked(true);
            }
        };
        this.tempChildWidths = new int[5];
        ViewCompat.setImportantForAccessibility((View)this, 1);
    }

    private BottomNavigationItemView getNewItem() {
        BottomNavigationItemView bottomNavigationItemView;
        BottomNavigationItemView bottomNavigationItemView2 = bottomNavigationItemView = this.itemPool.acquire();
        if (bottomNavigationItemView != null) return bottomNavigationItemView2;
        return new BottomNavigationItemView(this.getContext());
    }

    private boolean isShifting(int n, int n2) {
        boolean bl = true;
        if (n == -1) {
            if (n2 <= 3) return false;
            return bl;
        }
        if (n != 0) return false;
        return bl;
    }

    private boolean isValidId(int n) {
        if (n == -1) return false;
        return true;
    }

    private void removeUnusedBadges() {
        int n;
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n2 = 0;
        int n3 = 0;
        do {
            n = n2;
            if (n3 >= this.menu.size()) break;
            hashSet.add(this.menu.getItem(n3).getItemId());
            ++n3;
        } while (true);
        while (n < this.badgeDrawables.size()) {
            n3 = this.badgeDrawables.keyAt(n);
            if (!hashSet.contains(n3)) {
                this.badgeDrawables.delete(n3);
            }
            ++n;
        }
    }

    private void setBadgeIfNeeded(BottomNavigationItemView bottomNavigationItemView) {
        int n = bottomNavigationItemView.getId();
        if (!this.isValidId(n)) {
            return;
        }
        BadgeDrawable badgeDrawable = (BadgeDrawable)this.badgeDrawables.get(n);
        if (badgeDrawable == null) return;
        bottomNavigationItemView.setBadge(badgeDrawable);
    }

    private void validateMenuItemId(int n) {
        if (this.isValidId(n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is not a valid view id");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void buildMenuView() {
        this.removeAllViews();
        Drawable drawable2 = this.buttons;
        if (drawable2 != null) {
            for (BottomNavigationItemView bottomNavigationItemView : drawable2) {
                if (bottomNavigationItemView == null) continue;
                this.itemPool.release(bottomNavigationItemView);
                bottomNavigationItemView.removeBadge();
            }
        }
        if (this.menu.size() == 0) {
            this.selectedItemId = 0;
            this.selectedItemPosition = 0;
            this.buttons = null;
            return;
        }
        this.removeUnusedBadges();
        this.buttons = new BottomNavigationItemView[this.menu.size()];
        boolean bl = this.isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
        int n = 0;
        do {
            BottomNavigationItemView bottomNavigationItemView;
            if (n >= this.menu.size()) {
                this.selectedItemPosition = n = Math.min(this.menu.size() - 1, this.selectedItemPosition);
                this.menu.getItem(n).setChecked(true);
                return;
            }
            this.presenter.setUpdateSuspended(true);
            this.menu.getItem(n).setCheckable(true);
            this.presenter.setUpdateSuspended(false);
            this.buttons[n] = bottomNavigationItemView = this.getNewItem();
            bottomNavigationItemView.setIconTintList(this.itemIconTint);
            bottomNavigationItemView.setIconSize(this.itemIconSize);
            bottomNavigationItemView.setTextColor(this.itemTextColorDefault);
            bottomNavigationItemView.setTextAppearanceInactive(this.itemTextAppearanceInactive);
            bottomNavigationItemView.setTextAppearanceActive(this.itemTextAppearanceActive);
            bottomNavigationItemView.setTextColor(this.itemTextColorFromUser);
            drawable2 = this.itemBackground;
            if (drawable2 != null) {
                bottomNavigationItemView.setItemBackground(drawable2);
            } else {
                bottomNavigationItemView.setItemBackground(this.itemBackgroundRes);
            }
            bottomNavigationItemView.setShifting(bl);
            bottomNavigationItemView.setLabelVisibilityMode(this.labelVisibilityMode);
            bottomNavigationItemView.initialize((MenuItemImpl)this.menu.getItem(n), 0);
            bottomNavigationItemView.setItemPosition(n);
            bottomNavigationItemView.setOnClickListener(this.onClickListener);
            if (this.selectedItemId != 0 && this.menu.getItem(n).getItemId() == this.selectedItemId) {
                this.selectedItemPosition = n;
            }
            this.setBadgeIfNeeded(bottomNavigationItemView);
            this.addView((View)bottomNavigationItemView);
            ++n;
        } while (true);
    }

    public ColorStateList createDefaultColorStateList(int n) {
        int[] arrn = new TypedValue();
        if (!this.getContext().getTheme().resolveAttribute(n, (TypedValue)arrn, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(this.getContext(), arrn.resourceId);
        if (!this.getContext().getTheme().resolveAttribute(R.attr.colorPrimary, (TypedValue)arrn, true)) {
            return null;
        }
        n = arrn.data;
        int n2 = colorStateList.getDefaultColor();
        int[] arrn2 = DISABLED_STATE_SET;
        arrn = CHECKED_STATE_SET;
        int[] arrn3 = EMPTY_STATE_SET;
        int n3 = colorStateList.getColorForState(DISABLED_STATE_SET, n2);
        return new ColorStateList((int[][])new int[][]{arrn2, arrn, arrn3}, new int[]{n3, n, n2});
    }

    BottomNavigationItemView findItemView(int n) {
        this.validateMenuItemId(n);
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return null;
        int n2 = arrbottomNavigationItemView.length;
        int n3 = 0;
        while (n3 < n2) {
            BottomNavigationItemView bottomNavigationItemView = arrbottomNavigationItemView[n3];
            if (bottomNavigationItemView.getId() == n) {
                return bottomNavigationItemView;
            }
            ++n3;
        }
        return null;
    }

    BadgeDrawable getBadge(int n) {
        return (BadgeDrawable)this.badgeDrawables.get(n);
    }

    SparseArray<BadgeDrawable> getBadgeDrawables() {
        return this.badgeDrawables;
    }

    public ColorStateList getIconTintList() {
        return this.itemIconTint;
    }

    public Drawable getItemBackground() {
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return this.itemBackground;
        if (arrbottomNavigationItemView.length <= 0) return this.itemBackground;
        return arrbottomNavigationItemView[0].getBackground();
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.itemBackgroundRes;
    }

    public int getItemIconSize() {
        return this.itemIconSize;
    }

    public int getItemTextAppearanceActive() {
        return this.itemTextAppearanceActive;
    }

    public int getItemTextAppearanceInactive() {
        return this.itemTextAppearanceInactive;
    }

    public ColorStateList getItemTextColor() {
        return this.itemTextColorFromUser;
    }

    public int getLabelVisibilityMode() {
        return this.labelVisibilityMode;
    }

    BadgeDrawable getOrCreateBadge(int n) {
        this.validateMenuItemId(n);
        Object object = (BadgeDrawable)this.badgeDrawables.get(n);
        BadgeDrawable badgeDrawable = object;
        if (object == null) {
            badgeDrawable = BadgeDrawable.create(this.getContext());
            this.badgeDrawables.put(n, (Object)badgeDrawable);
        }
        if ((object = this.findItemView(n)) == null) return badgeDrawable;
        ((BottomNavigationItemView)object).setBadge(badgeDrawable);
        return badgeDrawable;
    }

    public int getSelectedItemId() {
        return this.selectedItemId;
    }

    @Override
    public int getWindowAnimations() {
        return 0;
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.menu = menuBuilder;
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return this.itemHorizontalTranslationEnabled;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.menu.getVisibleItems().size(), false, 1));
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        int n6 = n4 - n2;
        n2 = 0;
        n4 = 0;
        while (n2 < n5) {
            View view = this.getChildAt(n2);
            if (view.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection((View)this) == 1) {
                    int n7 = n3 - n - n4;
                    view.layout(n7 - view.getMeasuredWidth(), 0, n7, n6);
                } else {
                    view.layout(n4, 0, view.getMeasuredWidth() + n4, n6);
                }
                n4 += view.getMeasuredWidth();
            }
            ++n2;
        }
    }

    protected void onMeasure(int n, int n2) {
        int[] arrn;
        int n3;
        int n4;
        block14 : {
            int n5;
            int n6;
            block13 : {
                int n7;
                block12 : {
                    n7 = View.MeasureSpec.getSize((int)n);
                    n5 = this.menu.getVisibleItems().size();
                    n4 = this.getChildCount();
                    n3 = View.MeasureSpec.makeMeasureSpec((int)this.itemHeight, (int)1073741824);
                    if (this.isShifting(this.labelVisibilityMode, n5) && this.itemHorizontalTranslationEnabled) break block12;
                    n = n5 == 0 ? 1 : n5;
                    n6 = Math.min(n7 / n, this.activeItemMaxWidth);
                    n2 = n7 - n5 * n6;
                    break block13;
                }
                arrn = this.getChildAt(this.selectedItemPosition);
                n = n2 = this.activeItemMinWidth;
                if (arrn.getVisibility() != 8) {
                    arrn.measure(View.MeasureSpec.makeMeasureSpec((int)this.activeItemMaxWidth, (int)Integer.MIN_VALUE), n3);
                    n = Math.max(n2, arrn.getMeasuredWidth());
                }
                n2 = arrn.getVisibility() != 8 ? 1 : 0;
                n2 = n5 - n2;
                n6 = Math.min(n7 - this.inactiveItemMinWidth * n2, Math.min(n, this.activeItemMaxWidth));
                n5 = n7 - n6;
                n = n2 == 0 ? 1 : n2;
                n7 = Math.min(n5 / n, this.inactiveItemMaxWidth);
                n2 = n5 - n2 * n7;
                for (n = 0; n < n4; ++n) {
                    if (this.getChildAt(n).getVisibility() != 8) {
                        arrn = this.tempChildWidths;
                        n5 = n == this.selectedItemPosition ? n6 : n7;
                        arrn[n] = n5;
                        n5 = n2;
                        if (n2 > 0) {
                            arrn = this.tempChildWidths;
                            arrn[n] = arrn[n] + 1;
                            n5 = n2 - 1;
                        }
                    } else {
                        this.tempChildWidths[n] = 0;
                        n5 = n2;
                    }
                    n2 = n5;
                }
                break block14;
            }
            for (n = 0; n < n4; ++n) {
                if (this.getChildAt(n).getVisibility() != 8) {
                    arrn = this.tempChildWidths;
                    arrn[n] = (View)n6;
                    n5 = n2;
                    if (n2 > 0) {
                        arrn[n] = arrn[n] + true;
                        n5 = n2 - 1;
                    }
                } else {
                    this.tempChildWidths[n] = 0;
                    n5 = n2;
                }
                n2 = n5;
            }
        }
        n = 0;
        n2 = 0;
        do {
            if (n >= n4) {
                this.setMeasuredDimension(View.resolveSizeAndState((int)n2, (int)View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824), (int)0), View.resolveSizeAndState((int)this.itemHeight, (int)n3, (int)0));
                return;
            }
            arrn = this.getChildAt(n);
            if (arrn.getVisibility() != 8) {
                arrn.measure(View.MeasureSpec.makeMeasureSpec((int)this.tempChildWidths[n], (int)1073741824), n3);
                arrn.getLayoutParams().width = arrn.getMeasuredWidth();
                n2 += arrn.getMeasuredWidth();
            }
            ++n;
        } while (true);
    }

    void removeBadge(int n) {
        this.validateMenuItemId(n);
        BadgeDrawable badgeDrawable = (BadgeDrawable)this.badgeDrawables.get(n);
        BottomNavigationItemView bottomNavigationItemView = this.findItemView(n);
        if (bottomNavigationItemView != null) {
            bottomNavigationItemView.removeBadge();
        }
        if (badgeDrawable == null) return;
        this.badgeDrawables.remove(n);
    }

    void setBadgeDrawables(SparseArray<BadgeDrawable> sparseArray) {
        this.badgeDrawables = sparseArray;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n = arrbottomNavigationItemView.length;
        int n2 = 0;
        while (n2 < n) {
            BottomNavigationItemView bottomNavigationItemView = arrbottomNavigationItemView[n2];
            bottomNavigationItemView.setBadge((BadgeDrawable)sparseArray.get(bottomNavigationItemView.getId()));
            ++n2;
        }
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.itemIconTint = colorStateList;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n = arrbottomNavigationItemView.length;
        int n2 = 0;
        while (n2 < n) {
            arrbottomNavigationItemView[n2].setIconTintList(colorStateList);
            ++n2;
        }
    }

    public void setItemBackground(Drawable drawable2) {
        this.itemBackground = drawable2;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n = arrbottomNavigationItemView.length;
        int n2 = 0;
        while (n2 < n) {
            arrbottomNavigationItemView[n2].setItemBackground(drawable2);
            ++n2;
        }
    }

    public void setItemBackgroundRes(int n) {
        this.itemBackgroundRes = n;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n2 = arrbottomNavigationItemView.length;
        int n3 = 0;
        while (n3 < n2) {
            arrbottomNavigationItemView[n3].setItemBackground(n);
            ++n3;
        }
    }

    public void setItemHorizontalTranslationEnabled(boolean bl) {
        this.itemHorizontalTranslationEnabled = bl;
    }

    public void setItemIconSize(int n) {
        this.itemIconSize = n;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n2 = arrbottomNavigationItemView.length;
        int n3 = 0;
        while (n3 < n2) {
            arrbottomNavigationItemView[n3].setIconSize(n);
            ++n3;
        }
    }

    public void setItemTextAppearanceActive(int n) {
        this.itemTextAppearanceActive = n;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n2 = arrbottomNavigationItemView.length;
        int n3 = 0;
        while (n3 < n2) {
            BottomNavigationItemView bottomNavigationItemView = arrbottomNavigationItemView[n3];
            bottomNavigationItemView.setTextAppearanceActive(n);
            ColorStateList colorStateList = this.itemTextColorFromUser;
            if (colorStateList != null) {
                bottomNavigationItemView.setTextColor(colorStateList);
            }
            ++n3;
        }
    }

    public void setItemTextAppearanceInactive(int n) {
        this.itemTextAppearanceInactive = n;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n2 = arrbottomNavigationItemView.length;
        int n3 = 0;
        while (n3 < n2) {
            BottomNavigationItemView bottomNavigationItemView = arrbottomNavigationItemView[n3];
            bottomNavigationItemView.setTextAppearanceInactive(n);
            ColorStateList colorStateList = this.itemTextColorFromUser;
            if (colorStateList != null) {
                bottomNavigationItemView.setTextColor(colorStateList);
            }
            ++n3;
        }
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.itemTextColorFromUser = colorStateList;
        BottomNavigationItemView[] arrbottomNavigationItemView = this.buttons;
        if (arrbottomNavigationItemView == null) return;
        int n = arrbottomNavigationItemView.length;
        int n2 = 0;
        while (n2 < n) {
            arrbottomNavigationItemView[n2].setTextColor(colorStateList);
            ++n2;
        }
    }

    public void setLabelVisibilityMode(int n) {
        this.labelVisibilityMode = n;
    }

    public void setPresenter(BottomNavigationPresenter bottomNavigationPresenter) {
        this.presenter = bottomNavigationPresenter;
    }

    void tryRestoreSelectedItemId(int n) {
        int n2 = this.menu.size();
        int n3 = 0;
        while (n3 < n2) {
            MenuItem menuItem = this.menu.getItem(n3);
            if (n == menuItem.getItemId()) {
                this.selectedItemId = n;
                this.selectedItemPosition = n3;
                menuItem.setChecked(true);
                return;
            }
            ++n3;
        }
    }

    public void updateMenuView() {
        int n;
        MenuBuilder menuBuilder = this.menu;
        if (menuBuilder == null) return;
        if (this.buttons == null) {
            return;
        }
        int n2 = menuBuilder.size();
        if (n2 != this.buttons.length) {
            this.buildMenuView();
            return;
        }
        int n3 = this.selectedItemId;
        for (n = 0; n < n2; ++n) {
            menuBuilder = this.menu.getItem(n);
            if (!menuBuilder.isChecked()) continue;
            this.selectedItemId = menuBuilder.getItemId();
            this.selectedItemPosition = n;
        }
        if (n3 != this.selectedItemId) {
            TransitionManager.beginDelayedTransition(this, this.set);
        }
        boolean bl = this.isShifting(this.labelVisibilityMode, this.menu.getVisibleItems().size());
        n = 0;
        while (n < n2) {
            this.presenter.setUpdateSuspended(true);
            this.buttons[n].setLabelVisibilityMode(this.labelVisibilityMode);
            this.buttons[n].setShifting(bl);
            this.buttons[n].initialize((MenuItemImpl)this.menu.getItem(n), 0);
            this.presenter.setUpdateSuspended(false);
            ++n;
        }
    }

}

