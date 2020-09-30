/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewDebug
 *  android.view.ViewDebug$ExportedProperty
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.ViewUtils;

public class ActionMenuView
extends LinearLayoutCompat
implements MenuBuilder.ItemInvoker,
MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int)(56.0f * f);
        this.mGeneratedItemPadding = (int)(f * 4.0f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    static int measureChildForCells(View view, int n, int n2, int n3, int n4) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n5 = View.MeasureSpec.makeMeasureSpec((int)(View.MeasureSpec.getSize((int)n3) - n4), (int)View.MeasureSpec.getMode((int)n3));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView)view : null;
        boolean bl = true;
        n3 = actionMenuItemView != null && actionMenuItemView.hasText() ? 1 : 0;
        n4 = 2;
        if (n2 > 0 && (n3 == 0 || n2 >= 2)) {
            int n6;
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n2 * n), (int)Integer.MIN_VALUE), n5);
            int n7 = view.getMeasuredWidth();
            n2 = n6 = n7 / n;
            if (n7 % n != 0) {
                n2 = n6 + 1;
            }
            if (n3 != 0 && n2 < 2) {
                n2 = n4;
            }
        } else {
            n2 = 0;
        }
        if (layoutParams.isOverflowButton || n3 == 0) {
            bl = false;
        }
        layoutParams.expandable = bl;
        layoutParams.cellsUsed = n2;
        view.measure(View.MeasureSpec.makeMeasureSpec((int)(n * n2), (int)1073741824), n5);
        return n2;
    }

    /*
     * Unable to fully structure code
     */
    private void onMeasureExactFormat(int var1_1, int var2_2) {
        block36 : {
            var3_3 = View.MeasureSpec.getMode((int)var2_2);
            var4_4 = View.MeasureSpec.getSize((int)var1_1);
            var5_5 = View.MeasureSpec.getSize((int)var2_2);
            var6_6 = this.getPaddingLeft();
            var1_1 = this.getPaddingRight();
            var7_7 = this.getPaddingTop() + this.getPaddingBottom();
            var8_8 = ActionMenuView.getChildMeasureSpec((int)var2_2, (int)var7_7, (int)-2);
            var9_9 = var4_4 - (var6_6 + var1_1);
            var2_2 = this.mMinCellSize;
            var1_1 = var9_9 / var2_2;
            if (var1_1 == 0) {
                this.setMeasuredDimension(var9_9, 0);
                return;
            }
            var10_10 = var2_2 + var9_9 % var2_2 / var1_1;
            var11_11 = this.getChildCount();
            var6_6 = 0;
            var4_4 = 0;
            var13_13 = 0;
            var14_14 = 0;
            var15_15 = 0;
            var16_16 = 0L;
            for (var12_12 = 0; var12_12 < var11_11; ++var12_12) {
                var18_17 = this.getChildAt(var12_12);
                if (var18_17.getVisibility() == 8) {
                    var2_2 = var15_15;
                } else {
                    var19_18 = var18_17 instanceof ActionMenuItemView;
                    ++var13_13;
                    if (var19_18) {
                        var2_2 = this.mGeneratedItemPadding;
                        var18_17.setPadding(var2_2, 0, var2_2, 0);
                    }
                    var20_19 = (LayoutParams)var18_17.getLayoutParams();
                    var20_19.expanded = false;
                    var20_19.extraPixels = 0;
                    var20_19.cellsUsed = 0;
                    var20_19.expandable = false;
                    var20_19.leftMargin = 0;
                    var20_19.rightMargin = 0;
                    var19_18 = var19_18 != false && ((ActionMenuItemView)var18_17).hasText() != false;
                    var20_19.preventEdgeOffset = var19_18;
                    var2_2 = var20_19.isOverflowButton != false ? 1 : var1_1;
                    var21_20 = ActionMenuView.measureChildForCells(var18_17, var10_10, var2_2, var8_8, var7_7);
                    var14_14 = Math.max(var14_14, var21_20);
                    var2_2 = var15_15;
                    if (var20_19.expandable) {
                        var2_2 = var15_15 + 1;
                    }
                    if (var20_19.isOverflowButton) {
                        var4_4 = 1;
                    }
                    var1_1 -= var21_20;
                    var6_6 = Math.max(var6_6, var18_17.getMeasuredHeight());
                    if (var21_20 == 1) {
                        var16_16 |= (long)(1 << var12_12);
                    }
                }
                var15_15 = var2_2;
            }
            var12_12 = var4_4 != 0 && var13_13 == 2 ? 1 : 0;
            var2_2 = 0;
            var7_7 = var1_1;
            var21_20 = var12_12;
            var12_12 = var9_9;
            do {
                block37 : {
                    if (var15_15 > 0 && var7_7 > 0) break block37;
                    var1_1 = var2_2;
                    var2_2 = var6_6;
                    ** GOTO lbl-1000
                }
                var22_21 = 0;
                var9_9 = Integer.MAX_VALUE;
                var24_23 = 0L;
                for (var23_22 = 0; var23_22 < var11_11; ++var23_22) {
                    var20_19 = (LayoutParams)this.getChildAt(var23_22).getLayoutParams();
                    if (!var20_19.expandable) {
                        var1_1 = var22_21;
                        var26_24 = var9_9;
                        var27_25 = var24_23;
                    } else if (var20_19.cellsUsed < var9_9) {
                        var26_24 = var20_19.cellsUsed;
                        var27_25 = 1L << var23_22;
                        var1_1 = 1;
                    } else {
                        var1_1 = var22_21;
                        var26_24 = var9_9;
                        var27_25 = var24_23;
                        if (var20_19.cellsUsed == var9_9) {
                            var1_1 = var22_21 + 1;
                            var27_25 = var24_23 | 1L << var23_22;
                            var26_24 = var9_9;
                        }
                    }
                    var22_21 = var1_1;
                    var9_9 = var26_24;
                    var24_23 = var27_25;
                }
                var1_1 = var2_2;
                var2_2 = var6_6;
                var16_16 |= var24_23;
                if (var22_21 <= var7_7) {
                } else lbl-1000: // 2 sources:
                {
                    var6_6 = var4_4 == 0 && var13_13 == 1 ? 1 : 0;
                    if (var7_7 > 0 && var16_16 != 0L && (var7_7 < var13_13 - 1 || var6_6 != 0 || var14_14 > 1)) {
                        var31_27 = Long.bitCount(var16_16);
                        if (var6_6 == 0) {
                            var32_28 = var31_27;
                            if ((var16_16 & 1L) != 0L) {
                                var32_28 = var31_27;
                                if (!((LayoutParams)this.getChildAt((int)0).getLayoutParams()).preventEdgeOffset) {
                                    var32_28 = var31_27 - 0.5f;
                                }
                            }
                            var6_6 = var11_11 - 1;
                            var31_27 = var32_28;
                            if ((var16_16 & (long)(1 << var6_6)) != 0L) {
                                var31_27 = var32_28;
                                if (!((LayoutParams)this.getChildAt((int)var6_6).getLayoutParams()).preventEdgeOffset) {
                                    var31_27 = var32_28 - 0.5f;
                                }
                            }
                        }
                        break;
                    }
                    var15_15 = var1_1;
                    break block36;
                }
                for (var1_1 = 0; var1_1 < var11_11; ++var1_1) {
                    var18_17 = this.getChildAt(var1_1);
                    var20_19 = (LayoutParams)var18_17.getLayoutParams();
                    var29_26 = 1 << var1_1;
                    if ((var24_23 & var29_26) == 0L) {
                        var27_25 = var16_16;
                        if (var20_19.cellsUsed == var9_9 + 1) {
                            var27_25 = var16_16 | var29_26;
                        }
                        var16_16 = var27_25;
                        continue;
                    }
                    if (var21_20 != 0 && var20_19.preventEdgeOffset && var7_7 == 1) {
                        var6_6 = this.mGeneratedItemPadding;
                        var18_17.setPadding(var6_6 + var10_10, 0, var6_6, 0);
                    }
                    ++var20_19.cellsUsed;
                    var20_19.expanded = true;
                    --var7_7;
                }
                var6_6 = var2_2;
                var2_2 = 1;
            } while (true);
            var6_6 = var31_27 > 0.0f ? (int)((float)(var7_7 * var10_10) / var31_27) : 0;
            var4_4 = 0;
            do {
                block39 : {
                    block40 : {
                        block38 : {
                            var15_15 = var1_1;
                            if (var4_4 >= var11_11) break;
                            if ((var16_16 & (long)(1 << var4_4)) != 0L) break block38;
                            var15_15 = var1_1;
                            break block39;
                        }
                        var18_17 = this.getChildAt(var4_4);
                        var20_19 = (LayoutParams)var18_17.getLayoutParams();
                        if (!(var18_17 instanceof ActionMenuItemView)) break block40;
                        var20_19.extraPixels = var6_6;
                        var20_19.expanded = true;
                        if (var4_4 == 0 && !var20_19.preventEdgeOffset) {
                            var20_19.leftMargin = -var6_6 / 2;
                        }
                        ** GOTO lbl158
                    }
                    if (var20_19.isOverflowButton) {
                        var20_19.extraPixels = var6_6;
                        var20_19.expanded = true;
                        var20_19.rightMargin = -var6_6 / 2;
lbl158: // 2 sources:
                        var15_15 = 1;
                    } else {
                        if (var4_4 != 0) {
                            var20_19.leftMargin = var6_6 / 2;
                        }
                        var15_15 = var1_1;
                        if (var4_4 != var11_11 - 1) {
                            var20_19.rightMargin = var6_6 / 2;
                            var15_15 = var1_1;
                        }
                    }
                }
                ++var4_4;
                var1_1 = var15_15;
            } while (true);
        }
        if (var15_15 != 0) {
            for (var1_1 = 0; var1_1 < var11_11; ++var1_1) {
                var18_17 = this.getChildAt(var1_1);
                var20_19 = (LayoutParams)var18_17.getLayoutParams();
                if (!var20_19.expanded) continue;
                var18_17.measure(View.MeasureSpec.makeMeasureSpec((int)(var20_19.cellsUsed * var10_10 + var20_19.extraPixels), (int)1073741824), var8_8);
            }
        }
        if (var3_3 == 1073741824) {
            var2_2 = var5_5;
        }
        this.setMeasuredDimension(var12_12, var2_2);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void dismissPopupMenus() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter == null) return;
        actionMenuPresenter.dismissPopupMenus();
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        if (object == null) return this.generateDefaultLayoutParams();
        object = object instanceof LayoutParams ? new LayoutParams((LayoutParams)((Object)object)) : new LayoutParams((ViewGroup.LayoutParams)object);
        if (object.gravity > 0) return object;
        object.gravity = 16;
        return object;
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams layoutParams = this.generateDefaultLayoutParams();
        layoutParams.isOverflowButton = true;
        return layoutParams;
    }

    public Menu getMenu() {
        if (this.mMenu != null) return this.mMenu;
        Object object = this.getContext();
        Object object2 = new MenuBuilder((Context)object);
        this.mMenu = object2;
        ((MenuBuilder)object2).setCallback(new MenuBuilderCallback());
        object = new ActionMenuPresenter((Context)object);
        this.mPresenter = object;
        ((ActionMenuPresenter)object).setReserveOverflow(true);
        object2 = this.mPresenter;
        object = this.mActionMenuPresenterCallback;
        if (object == null) {
            object = new ActionMenuPresenterCallback();
        }
        ((BaseMenuPresenter)object2).setCallback((MenuPresenter.Callback)object);
        this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
        this.mPresenter.setMenuView(this);
        return this.mMenu;
    }

    public Drawable getOverflowIcon() {
        this.getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @Override
    public int getWindowAnimations() {
        return 0;
    }

    protected boolean hasSupportDividerBeforeChildAt(int n) {
        boolean bl = false;
        if (n == 0) {
            return false;
        }
        View view = this.getChildAt(n - 1);
        View view2 = this.getChildAt(n);
        boolean bl2 = bl;
        if (n < this.getChildCount()) {
            bl2 = bl;
            if (view instanceof ActionMenuChildView) {
                bl2 = false | ((ActionMenuChildView)view).needsDividerAfter();
            }
        }
        bl = bl2;
        if (n <= 0) return bl;
        bl = bl2;
        if (!(view2 instanceof ActionMenuChildView)) return bl;
        return bl2 | ((ActionMenuChildView)view2).needsDividerBefore();
    }

    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter == null) return false;
        if (!actionMenuPresenter.hideOverflowMenu()) return false;
        return true;
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter == null) return false;
        if (!actionMenuPresenter.isOverflowMenuShowPending()) return false;
        return true;
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter == null) return false;
        if (!actionMenuPresenter.isOverflowMenuShowing()) return false;
        return true;
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void onConfigurationChanged(Configuration object) {
        super.onConfigurationChanged((Configuration)object);
        object = this.mPresenter;
        if (object == null) return;
        ((ActionMenuPresenter)object).updateMenuView(false);
        if (!this.mPresenter.isOverflowMenuShowing()) return;
        this.mPresenter.hideOverflowMenu();
        this.mPresenter.showOverflowMenu();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dismissPopupMenus();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5;
        Object object;
        int n6;
        LayoutParams layoutParams;
        if (!this.mFormatItems) {
            super.onLayout(bl, n, n2, n3, n4);
            return;
        }
        int n7 = this.getChildCount();
        int n8 = (n4 - n2) / 2;
        int n9 = this.getDividerWidth();
        int n10 = n3 - n;
        n = n10 - this.getPaddingRight() - this.getPaddingLeft();
        bl = ViewUtils.isLayoutRtl((View)this);
        n4 = 0;
        n2 = 0;
        for (n3 = 0; n3 < n7; ++n3) {
            object = this.getChildAt(n3);
            if (object.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)object.getLayoutParams();
            if (layoutParams.isOverflowButton) {
                n4 = n5 = object.getMeasuredWidth();
                if (this.hasSupportDividerBeforeChildAt(n3)) {
                    n4 = n5 + n9;
                }
                int n11 = object.getMeasuredHeight();
                if (bl) {
                    n6 = this.getPaddingLeft() + layoutParams.leftMargin;
                    n5 = n6 + n4;
                } else {
                    n5 = this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin;
                    n6 = n5 - n4;
                }
                int n12 = n8 - n11 / 2;
                object.layout(n6, n12, n5, n11 + n12);
                n -= n4;
                n4 = 1;
                continue;
            }
            n -= object.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            this.hasSupportDividerBeforeChildAt(n3);
            ++n2;
        }
        if (n7 == 1 && n4 == 0) {
            object = this.getChildAt(0);
            n2 = object.getMeasuredWidth();
            n = object.getMeasuredHeight();
            n3 = n10 / 2 - n2 / 2;
            n4 = n8 - n / 2;
            object.layout(n3, n4, n2 + n3, n + n4);
            return;
        }
        n = (n2 -= n4 ^ 1) > 0 ? (n /= n2) : 0;
        n4 = Math.max(0, n);
        if (bl) {
            n2 = this.getWidth() - this.getPaddingRight();
            n = 0;
            while (n < n7) {
                layoutParams = this.getChildAt(n);
                object = (LayoutParams)layoutParams.getLayoutParams();
                n3 = n2;
                if (layoutParams.getVisibility() != 8) {
                    if (object.isOverflowButton) {
                        n3 = n2;
                    } else {
                        n6 = n2 - object.rightMargin;
                        n2 = layoutParams.getMeasuredWidth();
                        n3 = layoutParams.getMeasuredHeight();
                        n5 = n8 - n3 / 2;
                        layoutParams.layout(n6 - n2, n5, n6, n3 + n5);
                        n3 = n6 - (n2 + object.leftMargin + n4);
                    }
                }
                ++n;
                n2 = n3;
            }
            return;
        }
        n3 = this.getPaddingLeft();
        n = 0;
        while (n < n7) {
            layoutParams = this.getChildAt(n);
            object = (LayoutParams)layoutParams.getLayoutParams();
            n2 = n3;
            if (layoutParams.getVisibility() != 8) {
                if (object.isOverflowButton) {
                    n2 = n3;
                } else {
                    n6 = n3 + object.leftMargin;
                    n2 = layoutParams.getMeasuredWidth();
                    n3 = layoutParams.getMeasuredHeight();
                    n5 = n8 - n3 / 2;
                    layoutParams.layout(n6, n5, n6 + n2, n3 + n5);
                    n2 = n6 + (n2 + object.rightMargin + n4);
                }
            }
            ++n;
            n3 = n2;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        Object object;
        boolean bl = this.mFormatItems;
        boolean bl2 = View.MeasureSpec.getMode((int)n) == 1073741824;
        this.mFormatItems = bl2;
        if (bl != bl2) {
            this.mFormatItemsWidth = 0;
        }
        int n3 = View.MeasureSpec.getSize((int)n);
        if (this.mFormatItems && (object = this.mMenu) != null && n3 != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = n3;
            object.onItemsChanged(true);
        }
        int n4 = this.getChildCount();
        if (this.mFormatItems && n4 > 0) {
            this.onMeasureExactFormat(n, n2);
            return;
        }
        n3 = 0;
        do {
            if (n3 >= n4) {
                super.onMeasure(n, n2);
                return;
            }
            object = (LayoutParams)this.getChildAt(n3).getLayoutParams();
            ((LayoutParams)object).rightMargin = 0;
            ((LayoutParams)object).leftMargin = 0;
            ++n3;
        } while (true);
    }

    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public void setExpandedActionViewsExclusive(boolean bl) {
        this.mPresenter.setExpandedActionViewsExclusive(bl);
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(Drawable drawable2) {
        this.getMenu();
        this.mPresenter.setOverflowIcon(drawable2);
    }

    public void setOverflowReserved(boolean bl) {
        this.mReserveOverflow = bl;
    }

    public void setPopupTheme(int n) {
        if (this.mPopupTheme == n) return;
        this.mPopupTheme = n;
        if (n == 0) {
            this.mPopupContext = this.getContext();
            return;
        }
        this.mPopupContext = new ContextThemeWrapper(this.getContext(), n);
    }

    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        actionMenuPresenter.setMenuView(this);
    }

    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter == null) return false;
        if (!actionMenuPresenter.showOverflowMenu()) return false;
        return true;
    }

    public static interface ActionMenuChildView {
        public boolean needsDividerAfter();

        public boolean needsDividerBefore();
    }

    private static class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams
    extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.isOverflowButton = false;
        }

        LayoutParams(int n, int n2, boolean bl) {
            super(n, n2);
            this.isOverflowButton = bl;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }
    }

    private class MenuBuilderCallback
    implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            if (ActionMenuView.this.mOnMenuItemClickListener == null) return false;
            if (!ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem)) return false;
            return true;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.mMenuBuilderCallback == null) return;
            ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder);
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

}

