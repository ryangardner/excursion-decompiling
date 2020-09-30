/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.FrameLayout
 *  android.widget.HeaderViewListAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopup;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class CascadingMenuPopup
extends MenuPopup
implements MenuPresenter,
View.OnKeyListener,
PopupWindow.OnDismissListener {
    static final int HORIZ_POSITION_LEFT = 0;
    static final int HORIZ_POSITION_RIGHT = 1;
    private static final int ITEM_LAYOUT = R.layout.abc_cascading_menu_item_layout;
    static final int SUBMENU_TIMEOUT_MS = 200;
    private View mAnchorView;
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener(){

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            if (CascadingMenuPopup.this.mTreeObserver != null) {
                if (!CascadingMenuPopup.this.mTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        }
    };
    private final Context mContext;
    private int mDropDownGravity = 0;
    private boolean mForceShowIcon;
    final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        public void onGlobalLayout() {
            if (!CascadingMenuPopup.this.isShowing()) return;
            if (CascadingMenuPopup.this.mShowingMenus.size() <= 0) return;
            if (CascadingMenuPopup.this.mShowingMenus.get((int)0).window.isModal()) return;
            Object object = CascadingMenuPopup.this.mShownAnchorView;
            if (object != null && object.isShown()) {
                object = CascadingMenuPopup.this.mShowingMenus.iterator();
                while (object.hasNext()) {
                    ((CascadingMenuInfo)object.next()).window.show();
                }
                return;
            }
            CascadingMenuPopup.this.dismiss();
        }
    };
    private boolean mHasXOffset;
    private boolean mHasYOffset;
    private int mLastPosition;
    private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener(){

        @Override
        public void onItemHoverEnter(MenuBuilder menuBuilder, MenuItem object) {
            CascadingMenuInfo cascadingMenuInfo;
            int n;
            block4 : {
                Handler handler = CascadingMenuPopup.this.mSubMenuHoverHandler;
                cascadingMenuInfo = null;
                handler.removeCallbacksAndMessages(null);
                int n2 = CascadingMenuPopup.this.mShowingMenus.size();
                n = 0;
                while (n < n2) {
                    if (menuBuilder != CascadingMenuPopup.this.mShowingMenus.get((int)n).menu) {
                        ++n;
                        continue;
                    }
                    break block4;
                }
                return;
            }
            if (n == -1) {
                return;
            }
            if (++n < CascadingMenuPopup.this.mShowingMenus.size()) {
                cascadingMenuInfo = CascadingMenuPopup.this.mShowingMenus.get(n);
            }
            object = new Runnable((MenuItem)object, menuBuilder){
                final /* synthetic */ MenuItem val$item;
                final /* synthetic */ MenuBuilder val$menu;
                {
                    this.val$item = menuItem;
                    this.val$menu = menuBuilder;
                }

                @Override
                public void run() {
                    if (cascadingMenuInfo != null) {
                        CascadingMenuPopup.this.mShouldCloseImmediately = true;
                        cascadingMenuInfo.menu.close(false);
                        CascadingMenuPopup.this.mShouldCloseImmediately = false;
                    }
                    if (!this.val$item.isEnabled()) return;
                    if (!this.val$item.hasSubMenu()) return;
                    this.val$menu.performItemAction(this.val$item, 4);
                }
            };
            long l = SystemClock.uptimeMillis();
            CascadingMenuPopup.this.mSubMenuHoverHandler.postAtTime((Runnable)object, (Object)menuBuilder, l + 200L);
        }

        @Override
        public void onItemHoverExit(MenuBuilder menuBuilder, MenuItem menuItem) {
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages((Object)menuBuilder);
        }

    };
    private final int mMenuMaxWidth;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final List<MenuBuilder> mPendingMenus = new ArrayList<MenuBuilder>();
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;
    private int mRawDropDownGravity = 0;
    boolean mShouldCloseImmediately;
    private boolean mShowTitle;
    final List<CascadingMenuInfo> mShowingMenus = new ArrayList<CascadingMenuInfo>();
    View mShownAnchorView;
    final Handler mSubMenuHoverHandler;
    ViewTreeObserver mTreeObserver;
    private int mXOffset;
    private int mYOffset;

    public CascadingMenuPopup(Context context, View view, int n, int n2, boolean bl) {
        this.mContext = context;
        this.mAnchorView = view;
        this.mPopupStyleAttr = n;
        this.mPopupStyleRes = n2;
        this.mOverflowOnly = bl;
        this.mForceShowIcon = false;
        this.mLastPosition = this.getInitialMenuPosition();
        context = context.getResources();
        this.mMenuMaxWidth = Math.max(context.getDisplayMetrics().widthPixels / 2, context.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.mSubMenuHoverHandler = new Handler();
    }

    private MenuPopupWindow createPopupWindow() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.mAnchorView);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        menuPopupWindow.setModal(true);
        menuPopupWindow.setInputMethodMode(2);
        return menuPopupWindow;
    }

    private int findIndexOfAddedMenu(MenuBuilder menuBuilder) {
        int n = this.mShowingMenus.size();
        int n2 = 0;
        while (n2 < n) {
            if (menuBuilder == this.mShowingMenus.get((int)n2).menu) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    private MenuItem findMenuItemForSubmenu(MenuBuilder menuBuilder, MenuBuilder menuBuilder2) {
        int n = menuBuilder.size();
        int n2 = 0;
        while (n2 < n) {
            MenuItem menuItem = menuBuilder.getItem(n2);
            if (menuItem.hasSubMenu() && menuBuilder2 == menuItem.getSubMenu()) {
                return menuItem;
            }
            ++n2;
        }
        return null;
    }

    private View findParentViewForSubmenu(CascadingMenuInfo object, MenuBuilder menuBuilder) {
        int n;
        int n2;
        ListView listView;
        block6 : {
            if ((menuBuilder = this.findMenuItemForSubmenu(((CascadingMenuInfo)object).menu, menuBuilder)) == null) {
                return null;
            }
            listView = ((CascadingMenuInfo)object).getListView();
            object = listView.getAdapter();
            boolean bl = object instanceof HeaderViewListAdapter;
            n2 = 0;
            if (bl) {
                object = (HeaderViewListAdapter)object;
                n = object.getHeadersCount();
                object = (MenuAdapter)object.getWrappedAdapter();
            } else {
                object = (MenuAdapter)((Object)object);
                n = 0;
            }
            int n3 = ((MenuAdapter)((Object)object)).getCount();
            while (n2 < n3) {
                if (menuBuilder != ((MenuAdapter)((Object)object)).getItem(n2)) {
                    ++n2;
                    continue;
                }
                break block6;
            }
            return null;
        }
        if (n2 == -1) {
            return null;
        }
        if ((n2 = n2 + n - listView.getFirstVisiblePosition()) < 0) return null;
        if (n2 < listView.getChildCount()) return listView.getChildAt(n2);
        return null;
    }

    private int getInitialMenuPosition() {
        int n = ViewCompat.getLayoutDirection(this.mAnchorView);
        int n2 = 1;
        if (n != 1) return n2;
        return 0;
    }

    private int getNextMenuPosition(int n) {
        ListView listView = this.mShowingMenus;
        listView = listView.get(listView.size() - 1).getListView();
        int[] arrn = new int[2];
        listView.getLocationOnScreen(arrn);
        Rect rect = new Rect();
        this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
        if (this.mLastPosition == 1) {
            if (arrn[0] + listView.getWidth() + n <= rect.right) return 1;
            return 0;
        }
        if (arrn[0] - n >= 0) return 0;
        return 1;
    }

    /*
     * Unable to fully structure code
     */
    private void showMenu(MenuBuilder var1_1) {
        block14 : {
            block12 : {
                block13 : {
                    var2_2 = LayoutInflater.from((Context)this.mContext);
                    var3_3 = new MenuAdapter(var1_1, var2_2, this.mOverflowOnly, CascadingMenuPopup.ITEM_LAYOUT);
                    if (!this.isShowing() && this.mForceShowIcon) {
                        var3_3.setForceShowIcon(true);
                    } else if (this.isShowing()) {
                        var3_3.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(var1_1));
                    }
                    var4_4 = CascadingMenuPopup.measureIndividualMenuWidth((ListAdapter)var3_3, null, this.mContext, this.mMenuMaxWidth);
                    var5_5 = this.createPopupWindow();
                    var5_5.setAdapter((ListAdapter)var3_3);
                    var5_5.setContentWidth(var4_4);
                    var5_5.setDropDownGravity(this.mDropDownGravity);
                    if (this.mShowingMenus.size() > 0) {
                        var3_3 = this.mShowingMenus;
                        var3_3 = (CascadingMenuInfo)var3_3.get(var3_3.size() - 1);
                        var6_6 = this.findParentViewForSubmenu((CascadingMenuInfo)var3_3, var1_1);
                    } else {
                        var6_6 = var3_3 = null;
                    }
                    if (var6_6 == null) break block12;
                    var5_5.setTouchModal(false);
                    var5_5.setEnterTransition(null);
                    var7_7 = this.getNextMenuPosition(var4_4);
                    var8_8 = var7_7 == 1;
                    this.mLastPosition = var7_7;
                    if (Build.VERSION.SDK_INT >= 26) {
                        var5_5.setAnchorView((View)var6_6);
                        var7_7 = 0;
                        var9_9 = 0;
                    } else {
                        var10_10 = new int[2];
                        this.mAnchorView.getLocationOnScreen(var10_10);
                        var11_11 = new int[2];
                        var6_6.getLocationOnScreen(var11_11);
                        if ((this.mDropDownGravity & 7) == 5) {
                            var10_10[0] = var10_10[0] + this.mAnchorView.getWidth();
                            var11_11[0] = var11_11[0] + var6_6.getWidth();
                        }
                        var9_9 = var11_11[0] - var10_10[0];
                        var7_7 = var11_11[1] - var10_10[1];
                    }
                    if ((this.mDropDownGravity & 5) != 5) break block13;
                    if (var8_8) ** GOTO lbl46
                    var4_4 = var6_6.getWidth();
                    ** GOTO lbl-1000
                }
                if (var8_8) {
                    var4_4 = var6_6.getWidth();
lbl46: // 2 sources:
                    var4_4 = var9_9 + var4_4;
                } else lbl-1000: // 2 sources:
                {
                    var4_4 = var9_9 - var4_4;
                }
                var5_5.setHorizontalOffset(var4_4);
                var5_5.setOverlapAnchor(true);
                var5_5.setVerticalOffset(var7_7);
                break block14;
            }
            if (this.mHasXOffset) {
                var5_5.setHorizontalOffset(this.mXOffset);
            }
            if (this.mHasYOffset) {
                var5_5.setVerticalOffset(this.mYOffset);
            }
            var5_5.setEpicenterBounds(this.getEpicenterBounds());
        }
        var6_6 = new CascadingMenuInfo(var5_5, var1_1, this.mLastPosition);
        this.mShowingMenus.add((CascadingMenuInfo)var6_6);
        var5_5.show();
        var6_6 = var5_5.getListView();
        var6_6.setOnKeyListener((View.OnKeyListener)this);
        if (var3_3 != null) return;
        if (this.mShowTitle == false) return;
        if (var1_1.getHeaderTitle() == null) return;
        var2_2 = (FrameLayout)var2_2.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup)var6_6, false);
        var3_3 = (TextView)var2_2.findViewById(16908310);
        var2_2.setEnabled(false);
        var3_3.setText(var1_1.getHeaderTitle());
        var6_6.addHeaderView((View)var2_2, null, false);
        var5_5.show();
    }

    @Override
    public void addMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.mContext);
        if (this.isShowing()) {
            this.showMenu(menuBuilder);
            return;
        }
        this.mPendingMenus.add(menuBuilder);
    }

    @Override
    protected boolean closeMenuOnSubMenuOpened() {
        return false;
    }

    @Override
    public void dismiss() {
        int n = this.mShowingMenus.size();
        if (n <= 0) return;
        CascadingMenuInfo[] arrcascadingMenuInfo = this.mShowingMenus.toArray(new CascadingMenuInfo[n]);
        --n;
        while (n >= 0) {
            CascadingMenuInfo cascadingMenuInfo = arrcascadingMenuInfo[n];
            if (cascadingMenuInfo.window.isShowing()) {
                cascadingMenuInfo.window.dismiss();
            }
            --n;
        }
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    @Override
    public ListView getListView() {
        if (this.mShowingMenus.isEmpty()) {
            return null;
        }
        ListView listView = this.mShowingMenus;
        return listView.get(listView.size() - 1).getListView();
    }

    @Override
    public boolean isShowing() {
        boolean bl;
        int n = this.mShowingMenus.size();
        boolean bl2 = bl = false;
        if (n <= 0) return bl2;
        bl2 = bl;
        if (!this.mShowingMenus.get((int)0).window.isShowing()) return bl2;
        return true;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        int n = this.findIndexOfAddedMenu(menuBuilder);
        if (n < 0) {
            return;
        }
        int n2 = n + 1;
        if (n2 < this.mShowingMenus.size()) {
            this.mShowingMenus.get((int)n2).menu.close(false);
        }
        Object object = this.mShowingMenus.remove(n);
        ((CascadingMenuInfo)object).menu.removeMenuPresenter(this);
        if (this.mShouldCloseImmediately) {
            ((CascadingMenuInfo)object).window.setExitTransition(null);
            ((CascadingMenuInfo)object).window.setAnimationStyle(0);
        }
        ((CascadingMenuInfo)object).window.dismiss();
        n2 = this.mShowingMenus.size();
        this.mLastPosition = n2 > 0 ? this.mShowingMenus.get((int)(n2 - 1)).position : this.getInitialMenuPosition();
        if (n2 != 0) {
            if (!bl) return;
            this.mShowingMenus.get((int)0).menu.close(false);
            return;
        }
        this.dismiss();
        object = this.mPresenterCallback;
        if (object != null) {
            object.onCloseMenu(menuBuilder, true);
        }
        if ((menuBuilder = this.mTreeObserver) != null) {
            if (menuBuilder.isAlive()) {
                this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            }
            this.mTreeObserver = null;
        }
        this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
        this.mOnDismissListener.onDismiss();
    }

    public void onDismiss() {
        CascadingMenuInfo cascadingMenuInfo;
        block2 : {
            int n = this.mShowingMenus.size();
            for (int i = 0; i < n; ++i) {
                cascadingMenuInfo = this.mShowingMenus.get(i);
                if (cascadingMenuInfo.window.isShowing()) {
                    continue;
                }
                break block2;
            }
            cascadingMenuInfo = null;
        }
        if (cascadingMenuInfo == null) return;
        cascadingMenuInfo.menu.close(false);
    }

    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1) return false;
        if (n != 82) return false;
        this.dismiss();
        return true;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return null;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        Object object;
        Iterator<CascadingMenuInfo> iterator2 = this.mShowingMenus.iterator();
        do {
            if (!iterator2.hasNext()) {
                if (!subMenuBuilder.hasVisibleItems()) return false;
                this.addMenu(subMenuBuilder);
                object = this.mPresenterCallback;
                if (object == null) return true;
                object.onOpenSubMenu(subMenuBuilder);
                return true;
            }
            object = iterator2.next();
        } while (subMenuBuilder != ((CascadingMenuInfo)object).menu);
        ((CascadingMenuInfo)object).getListView().requestFocus();
        return true;
    }

    @Override
    public void setAnchorView(View view) {
        if (this.mAnchorView == view) return;
        this.mAnchorView = view;
        this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(view));
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override
    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
    }

    @Override
    public void setGravity(int n) {
        if (this.mRawDropDownGravity == n) return;
        this.mRawDropDownGravity = n;
        this.mDropDownGravity = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection(this.mAnchorView));
    }

    @Override
    public void setHorizontalOffset(int n) {
        this.mHasXOffset = true;
        this.mXOffset = n;
    }

    @Override
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override
    public void setShowTitle(boolean bl) {
        this.mShowTitle = bl;
    }

    @Override
    public void setVerticalOffset(int n) {
        this.mHasYOffset = true;
        this.mYOffset = n;
    }

    @Override
    public void show() {
        if (this.isShowing()) {
            return;
        }
        View view = this.mPendingMenus.iterator();
        while (view.hasNext()) {
            this.showMenu(view.next());
        }
        this.mPendingMenus.clear();
        this.mShownAnchorView = view = this.mAnchorView;
        if (view == null) return;
        boolean bl = this.mTreeObserver == null;
        view = this.mShownAnchorView.getViewTreeObserver();
        this.mTreeObserver = view;
        if (bl) {
            view.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
    }

    @Override
    public void updateMenuView(boolean bl) {
        Iterator<CascadingMenuInfo> iterator2 = this.mShowingMenus.iterator();
        while (iterator2.hasNext()) {
            CascadingMenuPopup.toMenuAdapter(iterator2.next().getListView().getAdapter()).notifyDataSetChanged();
        }
    }

    private static class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(MenuPopupWindow menuPopupWindow, MenuBuilder menuBuilder, int n) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = n;
        }

        public ListView getListView() {
            return this.window.getListView();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HorizPosition {
    }

}

