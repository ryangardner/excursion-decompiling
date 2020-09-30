/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.SpinnerAdapter
 */
package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.NavItemSelectedListener;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar
extends ActionBar
implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    private static final long FADE_IN_DURATION_MS = 200L;
    private static final long FADE_OUT_DURATION_MS = 100L;
    private static final int INVALID_POSITION = -1;
    private static final String TAG = "WindowDecorActionBar";
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
    ActionModeImpl mActionMode;
    private Activity mActivity;
    ActionBarContainer mContainerView;
    boolean mContentAnimations = true;
    View mContentView;
    Context mContext;
    ActionBarContextView mContextView;
    private int mCurWindowVisibility = 0;
    ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    DecorToolbar mDecorToolbar;
    ActionMode mDeferredDestroyActionMode;
    ActionMode.Callback mDeferredModeDestroyCallback;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    boolean mHiddenByApp;
    boolean mHiddenBySystem;
    final ViewPropertyAnimatorListener mHideListener = new ViewPropertyAnimatorListenerAdapter(){

        @Override
        public void onAnimationEnd(View view) {
            if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
                WindowDecorActionBar.this.mContentView.setTranslationY(0.0f);
                WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f);
            }
            WindowDecorActionBar.this.mContainerView.setVisibility(8);
            WindowDecorActionBar.this.mContainerView.setTransitioning(false);
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.completeDeferredDestroyActionMode();
            if (WindowDecorActionBar.this.mOverlayLayout == null) return;
            ViewCompat.requestApplyInsets((View)WindowDecorActionBar.this.mOverlayLayout);
        }
    };
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList();
    private boolean mNowShowing = true;
    ActionBarOverlayLayout mOverlayLayout;
    private int mSavedTabPosition = -1;
    private TabImpl mSelectedTab;
    private boolean mShowHideAnimationEnabled;
    final ViewPropertyAnimatorListener mShowListener = new ViewPropertyAnimatorListenerAdapter(){

        @Override
        public void onAnimationEnd(View view) {
            WindowDecorActionBar.this.mCurrentShowAnim = null;
            WindowDecorActionBar.this.mContainerView.requestLayout();
        }
    };
    private boolean mShowingForMode;
    ScrollingTabContainerView mTabScrollView;
    private ArrayList<TabImpl> mTabs = new ArrayList();
    private Context mThemedContext;
    final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener(){

        @Override
        public void onAnimationUpdate(View view) {
            ((View)WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
        }
    };

    public WindowDecorActionBar(Activity activity, boolean bl) {
        this.mActivity = activity;
        activity = activity.getWindow().getDecorView();
        this.init((View)activity);
        if (bl) return;
        this.mContentView = activity.findViewById(16908290);
    }

    public WindowDecorActionBar(Dialog dialog) {
        this.init(dialog.getWindow().getDecorView());
    }

    public WindowDecorActionBar(View view) {
        this.init(view);
    }

    static boolean checkShowingFlags(boolean bl, boolean bl2, boolean bl3) {
        if (bl3) {
            return true;
        }
        if (bl) return false;
        if (!bl2) return true;
        return false;
    }

    private void cleanupTabs() {
        if (this.mSelectedTab != null) {
            this.selectTab(null);
        }
        this.mTabs.clear();
        ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
        if (scrollingTabContainerView != null) {
            scrollingTabContainerView.removeAllTabs();
        }
        this.mSavedTabPosition = -1;
    }

    private void configureTab(ActionBar.Tab tab, int n) {
        if (((TabImpl)(tab = (TabImpl)tab)).getCallback() == null) throw new IllegalStateException("Action Bar Tab must have a Callback");
        ((TabImpl)tab).setPosition(n);
        this.mTabs.add(n, (TabImpl)tab);
        int n2 = this.mTabs.size();
        while (++n < n2) {
            this.mTabs.get(n).setPosition(n);
        }
    }

    private void ensureTabsExist() {
        if (this.mTabScrollView != null) {
            return;
        }
        ScrollingTabContainerView scrollingTabContainerView = new ScrollingTabContainerView(this.mContext);
        if (this.mHasEmbeddedTabs) {
            scrollingTabContainerView.setVisibility(0);
            this.mDecorToolbar.setEmbeddedTabView(scrollingTabContainerView);
        } else {
            if (this.getNavigationMode() == 2) {
                scrollingTabContainerView.setVisibility(0);
                ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
                if (actionBarOverlayLayout != null) {
                    ViewCompat.requestApplyInsets((View)actionBarOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
            this.mContainerView.setTabContainer(scrollingTabContainerView);
        }
        this.mTabScrollView = scrollingTabContainerView;
    }

    private DecorToolbar getDecorToolbar(View object) {
        if (object instanceof DecorToolbar) {
            return (DecorToolbar)object;
        }
        if (object instanceof Toolbar) {
            return ((Toolbar)((Object)object)).getWrapper();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't make a decor toolbar out of ");
        object = object != null ? object.getClass().getSimpleName() : "null";
        stringBuilder.append((String)object);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void hideForActionMode() {
        if (!this.mShowingForMode) return;
        this.mShowingForMode = false;
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setShowingForActionMode(false);
        }
        this.updateVisibility(false);
    }

    private void init(View object) {
        Object object2;
        this.mOverlayLayout = object2 = (ActionBarOverlayLayout)object.findViewById(R.id.decor_content_parent);
        if (object2 != null) {
            object2.setActionBarVisibilityCallback(this);
        }
        this.mDecorToolbar = this.getDecorToolbar(object.findViewById(R.id.action_bar));
        this.mContextView = (ActionBarContextView)object.findViewById(R.id.action_context_bar);
        object2 = (ActionBarContainer)object.findViewById(R.id.action_bar_container);
        this.mContainerView = object2;
        object = this.mDecorToolbar;
        if (object != null && this.mContextView != null && object2 != null) {
            this.mContext = object.getContext();
            int n = (this.mDecorToolbar.getDisplayOptions() & 4) != 0 ? 1 : 0;
            if (n != 0) {
                this.mDisplayHomeAsUpSet = true;
            }
            boolean bl = ((ActionBarPolicy)(object = ActionBarPolicy.get(this.mContext))).enableHomeButtonByDefault() || n != 0;
            this.setHomeButtonEnabled(bl);
            this.setHasEmbeddedTabs(((ActionBarPolicy)object).hasEmbeddedTabs());
            object = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
            if (object.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
                this.setHideOnContentScrollEnabled(true);
            }
            if ((n = object.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0)) != 0) {
                this.setElevation(n);
            }
            object.recycle();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getClass().getSimpleName());
        ((StringBuilder)object).append(" can only be used with a compatible window decor layout");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private void setHasEmbeddedTabs(boolean bl) {
        this.mHasEmbeddedTabs = bl;
        if (!bl) {
            this.mDecorToolbar.setEmbeddedTabView(null);
            this.mContainerView.setTabContainer(this.mTabScrollView);
        } else {
            this.mContainerView.setTabContainer(null);
            this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
        }
        int n = this.getNavigationMode();
        boolean bl2 = true;
        n = n == 2 ? 1 : 0;
        Object object = this.mTabScrollView;
        if (object != null) {
            if (n != 0) {
                object.setVisibility(0);
                object = this.mOverlayLayout;
                if (object != null) {
                    ViewCompat.requestApplyInsets((View)object);
                }
            } else {
                object.setVisibility(8);
            }
        }
        object = this.mDecorToolbar;
        bl = !this.mHasEmbeddedTabs && n != 0;
        object.setCollapsible(bl);
        object = this.mOverlayLayout;
        bl = !this.mHasEmbeddedTabs && n != 0 ? bl2 : false;
        ((ActionBarOverlayLayout)object).setHasNonEmbeddedTabs(bl);
    }

    private boolean shouldAnimateContextView() {
        return ViewCompat.isLaidOut((View)this.mContainerView);
    }

    private void showForActionMode() {
        if (this.mShowingForMode) return;
        this.mShowingForMode = true;
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setShowingForActionMode(true);
        }
        this.updateVisibility(false);
    }

    private void updateVisibility(boolean bl) {
        if (WindowDecorActionBar.checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
            if (this.mNowShowing) return;
            this.mNowShowing = true;
            this.doShow(bl);
            return;
        }
        if (!this.mNowShowing) return;
        this.mNowShowing = false;
        this.doHide(bl);
    }

    @Override
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.add(onMenuVisibilityListener);
    }

    @Override
    public void addTab(ActionBar.Tab tab) {
        this.addTab(tab, this.mTabs.isEmpty());
    }

    @Override
    public void addTab(ActionBar.Tab tab, int n) {
        this.addTab(tab, n, this.mTabs.isEmpty());
    }

    @Override
    public void addTab(ActionBar.Tab tab, int n, boolean bl) {
        this.ensureTabsExist();
        this.mTabScrollView.addTab(tab, n, bl);
        this.configureTab(tab, n);
        if (!bl) return;
        this.selectTab(tab);
    }

    @Override
    public void addTab(ActionBar.Tab tab, boolean bl) {
        this.ensureTabsExist();
        this.mTabScrollView.addTab(tab, bl);
        this.configureTab(tab, this.mTabs.size());
        if (!bl) return;
        this.selectTab(tab);
    }

    public void animateToMode(boolean bl) {
        if (bl) {
            this.showForActionMode();
        } else {
            this.hideForActionMode();
        }
        if (this.shouldAnimateContextView()) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
            if (bl) {
                viewPropertyAnimatorCompat = this.mDecorToolbar.setupAnimatorToVisibility(4, 100L);
                viewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(0, 200L);
            } else {
                viewPropertyAnimatorCompat2 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200L);
                viewPropertyAnimatorCompat = this.mContextView.setupAnimatorToVisibility(8, 100L);
            }
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat, viewPropertyAnimatorCompat2);
            viewPropertyAnimatorCompatSet.start();
            return;
        }
        if (bl) {
            this.mDecorToolbar.setVisibility(4);
            this.mContextView.setVisibility(0);
            return;
        }
        this.mDecorToolbar.setVisibility(0);
        this.mContextView.setVisibility(8);
    }

    @Override
    public boolean collapseActionView() {
        DecorToolbar decorToolbar = this.mDecorToolbar;
        if (decorToolbar == null) return false;
        if (!decorToolbar.hasExpandedActionView()) return false;
        this.mDecorToolbar.collapseActionView();
        return true;
    }

    void completeDeferredDestroyActionMode() {
        ActionMode.Callback callback = this.mDeferredModeDestroyCallback;
        if (callback == null) return;
        callback.onDestroyActionMode(this.mDeferredDestroyActionMode);
        this.mDeferredDestroyActionMode = null;
        this.mDeferredModeDestroyCallback = null;
    }

    @Override
    public void dispatchMenuVisibilityChanged(boolean bl) {
        if (bl == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = bl;
        int n = this.mMenuVisibilityListeners.size();
        int n2 = 0;
        while (n2 < n) {
            this.mMenuVisibilityListeners.get(n2).onMenuVisibilityChanged(bl);
            ++n2;
        }
    }

    public void doHide(boolean bl) {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || bl)) {
            Object object;
            float f;
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTransitioning(true);
            viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            float f2 = f = (float)(-this.mContainerView.getHeight());
            if (bl) {
                object = new int[2];
                int[] arrn = object;
                arrn[0] = 0;
                arrn[1] = 0;
                this.mContainerView.getLocationInWindow((int[])object);
                f2 = f - (float)object[1];
            }
            object = ViewCompat.animate((View)this.mContainerView).translationY(f2);
            ((ViewPropertyAnimatorCompat)object).setUpdateListener(this.mUpdateListener);
            viewPropertyAnimatorCompatSet.play((ViewPropertyAnimatorCompat)object);
            if (this.mContentAnimations && (object = this.mContentView) != null) {
                viewPropertyAnimatorCompatSet.play(ViewCompat.animate((View)object).translationY(f2));
            }
            viewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator);
            viewPropertyAnimatorCompatSet.setDuration(250L);
            viewPropertyAnimatorCompatSet.setListener(this.mHideListener);
            this.mCurrentShowAnim = viewPropertyAnimatorCompatSet;
            viewPropertyAnimatorCompatSet.start();
            return;
        }
        this.mHideListener.onAnimationEnd(null);
    }

    public void doShow(boolean bl) {
        Object object = this.mCurrentShowAnim;
        if (object != null) {
            ((ViewPropertyAnimatorCompatSet)object).cancel();
        }
        this.mContainerView.setVisibility(0);
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || bl)) {
            float f;
            this.mContainerView.setTranslationY(0.0f);
            float f2 = f = (float)(-this.mContainerView.getHeight());
            if (bl) {
                object = new int[2];
                int[] arrn = object;
                arrn[0] = 0;
                arrn[1] = 0;
                this.mContainerView.getLocationInWindow((int[])object);
                f2 = f - (float)object[1];
            }
            this.mContainerView.setTranslationY(f2);
            object = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mContainerView).translationY(0.0f);
            viewPropertyAnimatorCompat.setUpdateListener(this.mUpdateListener);
            ((ViewPropertyAnimatorCompatSet)object).play(viewPropertyAnimatorCompat);
            if (this.mContentAnimations && (viewPropertyAnimatorCompat = this.mContentView) != null) {
                viewPropertyAnimatorCompat.setTranslationY(f2);
                ((ViewPropertyAnimatorCompatSet)object).play(ViewCompat.animate(this.mContentView).translationY(0.0f));
            }
            ((ViewPropertyAnimatorCompatSet)object).setInterpolator(sShowInterpolator);
            ((ViewPropertyAnimatorCompatSet)object).setDuration(250L);
            ((ViewPropertyAnimatorCompatSet)object).setListener(this.mShowListener);
            this.mCurrentShowAnim = object;
            ((ViewPropertyAnimatorCompatSet)object).start();
        } else {
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTranslationY(0.0f);
            if (this.mContentAnimations && (object = this.mContentView) != null) {
                object.setTranslationY(0.0f);
            }
            this.mShowListener.onAnimationEnd(null);
        }
        object = this.mOverlayLayout;
        if (object == null) return;
        ViewCompat.requestApplyInsets((View)object);
    }

    @Override
    public void enableContentAnimations(boolean bl) {
        this.mContentAnimations = bl;
    }

    @Override
    public View getCustomView() {
        return this.mDecorToolbar.getCustomView();
    }

    @Override
    public int getDisplayOptions() {
        return this.mDecorToolbar.getDisplayOptions();
    }

    @Override
    public float getElevation() {
        return ViewCompat.getElevation((View)this.mContainerView);
    }

    @Override
    public int getHeight() {
        return this.mContainerView.getHeight();
    }

    @Override
    public int getHideOffset() {
        return this.mOverlayLayout.getActionBarHideOffset();
    }

    @Override
    public int getNavigationItemCount() {
        int n = this.mDecorToolbar.getNavigationMode();
        if (n == 1) return this.mDecorToolbar.getDropdownItemCount();
        if (n == 2) return this.mTabs.size();
        return 0;
    }

    @Override
    public int getNavigationMode() {
        return this.mDecorToolbar.getNavigationMode();
    }

    @Override
    public int getSelectedNavigationIndex() {
        int n = this.mDecorToolbar.getNavigationMode();
        if (n == 1) return this.mDecorToolbar.getDropdownSelectedPosition();
        int n2 = -1;
        if (n != 2) {
            return -1;
        }
        TabImpl tabImpl = this.mSelectedTab;
        if (tabImpl == null) return n2;
        return tabImpl.getPosition();
    }

    @Override
    public ActionBar.Tab getSelectedTab() {
        return this.mSelectedTab;
    }

    @Override
    public CharSequence getSubtitle() {
        return this.mDecorToolbar.getSubtitle();
    }

    @Override
    public ActionBar.Tab getTabAt(int n) {
        return this.mTabs.get(n);
    }

    @Override
    public int getTabCount() {
        return this.mTabs.size();
    }

    @Override
    public Context getThemedContext() {
        if (this.mThemedContext != null) return this.mThemedContext;
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
        int n = typedValue.resourceId;
        if (n != 0) {
            this.mThemedContext = new ContextThemeWrapper(this.mContext, n);
            return this.mThemedContext;
        }
        this.mThemedContext = this.mContext;
        return this.mThemedContext;
    }

    @Override
    public CharSequence getTitle() {
        return this.mDecorToolbar.getTitle();
    }

    public boolean hasIcon() {
        return this.mDecorToolbar.hasIcon();
    }

    public boolean hasLogo() {
        return this.mDecorToolbar.hasLogo();
    }

    @Override
    public void hide() {
        if (this.mHiddenByApp) return;
        this.mHiddenByApp = true;
        this.updateVisibility(false);
    }

    @Override
    public void hideForSystem() {
        if (this.mHiddenBySystem) return;
        this.mHiddenBySystem = true;
        this.updateVisibility(true);
    }

    @Override
    public boolean isHideOnContentScrollEnabled() {
        return this.mOverlayLayout.isHideOnContentScrollEnabled();
    }

    @Override
    public boolean isShowing() {
        int n = this.getHeight();
        if (!this.mNowShowing) return false;
        if (n == 0) return true;
        if (this.getHideOffset() >= n) return false;
        return true;
    }

    @Override
    public boolean isTitleTruncated() {
        DecorToolbar decorToolbar = this.mDecorToolbar;
        if (decorToolbar == null) return false;
        if (!decorToolbar.isTitleTruncated()) return false;
        return true;
    }

    @Override
    public ActionBar.Tab newTab() {
        return new TabImpl();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    @Override
    public void onContentScrollStarted() {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet == null) return;
        viewPropertyAnimatorCompatSet.cancel();
        this.mCurrentShowAnim = null;
    }

    @Override
    public void onContentScrollStopped() {
    }

    @Override
    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        ActionModeImpl actionModeImpl = this.mActionMode;
        if (actionModeImpl == null) {
            return false;
        }
        if ((actionModeImpl = actionModeImpl.getMenu()) == null) return false;
        int n2 = keyEvent != null ? keyEvent.getDeviceId() : -1;
        n2 = KeyCharacterMap.load((int)n2).getKeyboardType();
        boolean bl = true;
        if (n2 == 1) {
            bl = false;
        }
        actionModeImpl.setQwertyMode(bl);
        return actionModeImpl.performShortcut(n, keyEvent, 0);
    }

    @Override
    public void onWindowVisibilityChanged(int n) {
        this.mCurWindowVisibility = n;
    }

    @Override
    public void removeAllTabs() {
        this.cleanupTabs();
    }

    @Override
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.remove(onMenuVisibilityListener);
    }

    @Override
    public void removeTab(ActionBar.Tab tab) {
        this.removeTabAt(tab.getPosition());
    }

    @Override
    public void removeTabAt(int n) {
        if (this.mTabScrollView == null) {
            return;
        }
        TabImpl tabImpl = this.mSelectedTab;
        int n2 = tabImpl != null ? tabImpl.getPosition() : this.mSavedTabPosition;
        this.mTabScrollView.removeTabAt(n);
        tabImpl = this.mTabs.remove(n);
        if (tabImpl != null) {
            tabImpl.setPosition(-1);
        }
        int n3 = this.mTabs.size();
        for (int i = n; i < n3; ++i) {
            this.mTabs.get(i).setPosition(i);
        }
        if (n2 != n) return;
        tabImpl = this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, n - 1));
        this.selectTab(tabImpl);
    }

    @Override
    public boolean requestFocus() {
        ViewGroup viewGroup = this.mDecorToolbar.getViewGroup();
        if (viewGroup == null) return false;
        if (viewGroup.hasFocus()) return false;
        viewGroup.requestFocus();
        return true;
    }

    @Override
    public void selectTab(ActionBar.Tab tab) {
        int n = this.getNavigationMode();
        int n2 = -1;
        if (n != 2) {
            if (tab != null) {
                n2 = tab.getPosition();
            }
            this.mSavedTabPosition = n2;
            return;
        }
        FragmentTransaction fragmentTransaction = this.mActivity instanceof FragmentActivity && !this.mDecorToolbar.getViewGroup().isInEditMode() ? ((FragmentActivity)this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack() : null;
        Object object = this.mSelectedTab;
        if (object == tab) {
            if (object != null) {
                ((TabImpl)object).getCallback().onTabReselected(this.mSelectedTab, fragmentTransaction);
                this.mTabScrollView.animateToTab(tab.getPosition());
            }
        } else {
            object = this.mTabScrollView;
            if (tab != null) {
                n2 = tab.getPosition();
            }
            ((ScrollingTabContainerView)((Object)object)).setTabSelected(n2);
            object = this.mSelectedTab;
            if (object != null) {
                ((TabImpl)object).getCallback().onTabUnselected(this.mSelectedTab, fragmentTransaction);
            }
            tab = (TabImpl)tab;
            this.mSelectedTab = tab;
            if (tab != null) {
                ((TabImpl)tab).getCallback().onTabSelected(this.mSelectedTab, fragmentTransaction);
            }
        }
        if (fragmentTransaction == null) return;
        if (fragmentTransaction.isEmpty()) return;
        fragmentTransaction.commit();
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        this.mContainerView.setPrimaryBackground(drawable2);
    }

    @Override
    public void setCustomView(int n) {
        this.setCustomView(LayoutInflater.from((Context)this.getThemedContext()).inflate(n, this.mDecorToolbar.getViewGroup(), false));
    }

    @Override
    public void setCustomView(View view) {
        this.mDecorToolbar.setCustomView(view);
    }

    @Override
    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.mDecorToolbar.setCustomView(view);
    }

    @Override
    public void setDefaultDisplayHomeAsUpEnabled(boolean bl) {
        if (this.mDisplayHomeAsUpSet) return;
        this.setDisplayHomeAsUpEnabled(bl);
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean bl) {
        int n = bl ? 4 : 0;
        this.setDisplayOptions(n, 4);
    }

    @Override
    public void setDisplayOptions(int n) {
        if ((n & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(n);
    }

    @Override
    public void setDisplayOptions(int n, int n2) {
        int n3 = this.mDecorToolbar.getDisplayOptions();
        if ((n2 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.mDecorToolbar.setDisplayOptions(n & n2 | n2 & n3);
    }

    @Override
    public void setDisplayShowCustomEnabled(boolean bl) {
        int n = bl ? 16 : 0;
        this.setDisplayOptions(n, 16);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean bl) {
        int n = bl ? 2 : 0;
        this.setDisplayOptions(n, 2);
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean bl) {
        int n = bl ? 8 : 0;
        this.setDisplayOptions(n, 8);
    }

    @Override
    public void setDisplayUseLogoEnabled(boolean bl) {
        this.setDisplayOptions((int)bl, 1);
    }

    @Override
    public void setElevation(float f) {
        ViewCompat.setElevation((View)this.mContainerView, f);
    }

    @Override
    public void setHideOffset(int n) {
        if (n != 0) {
            if (!this.mOverlayLayout.isInOverlayMode()) throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
        }
        this.mOverlayLayout.setActionBarHideOffset(n);
    }

    @Override
    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl) {
            if (!this.mOverlayLayout.isInOverlayMode()) throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.mHideOnContentScroll = bl;
        this.mOverlayLayout.setHideOnContentScrollEnabled(bl);
    }

    @Override
    public void setHomeActionContentDescription(int n) {
        this.mDecorToolbar.setNavigationContentDescription(n);
    }

    @Override
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.mDecorToolbar.setNavigationContentDescription(charSequence);
    }

    @Override
    public void setHomeAsUpIndicator(int n) {
        this.mDecorToolbar.setNavigationIcon(n);
    }

    @Override
    public void setHomeAsUpIndicator(Drawable drawable2) {
        this.mDecorToolbar.setNavigationIcon(drawable2);
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
        this.mDecorToolbar.setHomeButtonEnabled(bl);
    }

    @Override
    public void setIcon(int n) {
        this.mDecorToolbar.setIcon(n);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mDecorToolbar.setIcon(drawable2);
    }

    @Override
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.mDecorToolbar.setDropdownParams(spinnerAdapter, new NavItemSelectedListener(onNavigationListener));
    }

    @Override
    public void setLogo(int n) {
        this.mDecorToolbar.setLogo(n);
    }

    @Override
    public void setLogo(Drawable drawable2) {
        this.mDecorToolbar.setLogo(drawable2);
    }

    @Override
    public void setNavigationMode(int n) {
        Object object;
        int n2 = this.mDecorToolbar.getNavigationMode();
        if (n2 == 2) {
            this.mSavedTabPosition = this.getSelectedNavigationIndex();
            this.selectTab(null);
            this.mTabScrollView.setVisibility(8);
        }
        if (n2 != n && !this.mHasEmbeddedTabs && (object = this.mOverlayLayout) != null) {
            ViewCompat.requestApplyInsets((View)object);
        }
        this.mDecorToolbar.setNavigationMode(n);
        boolean bl = false;
        if (n == 2) {
            this.ensureTabsExist();
            this.mTabScrollView.setVisibility(0);
            n2 = this.mSavedTabPosition;
            if (n2 != -1) {
                this.setSelectedNavigationItem(n2);
                this.mSavedTabPosition = -1;
            }
        }
        object = this.mDecorToolbar;
        boolean bl2 = n == 2 && !this.mHasEmbeddedTabs;
        object.setCollapsible(bl2);
        object = this.mOverlayLayout;
        bl2 = bl;
        if (n == 2) {
            bl2 = bl;
            if (!this.mHasEmbeddedTabs) {
                bl2 = true;
            }
        }
        ((ActionBarOverlayLayout)object).setHasNonEmbeddedTabs(bl2);
    }

    @Override
    public void setSelectedNavigationItem(int n) {
        int n2 = this.mDecorToolbar.getNavigationMode();
        if (n2 != 1) {
            if (n2 != 2) throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
            this.selectTab(this.mTabs.get(n));
            return;
        }
        this.mDecorToolbar.setDropdownSelectedPosition(n);
    }

    @Override
    public void setShowHideAnimationEnabled(boolean bl) {
        this.mShowHideAnimationEnabled = bl;
        if (bl) return;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet == null) return;
        viewPropertyAnimatorCompatSet.cancel();
    }

    @Override
    public void setSplitBackgroundDrawable(Drawable drawable2) {
    }

    @Override
    public void setStackedBackgroundDrawable(Drawable drawable2) {
        this.mContainerView.setStackedBackground(drawable2);
    }

    @Override
    public void setSubtitle(int n) {
        this.setSubtitle(this.mContext.getString(n));
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        this.mDecorToolbar.setSubtitle(charSequence);
    }

    @Override
    public void setTitle(int n) {
        this.setTitle(this.mContext.getString(n));
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mDecorToolbar.setTitle(charSequence);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override
    public void show() {
        if (!this.mHiddenByApp) return;
        this.mHiddenByApp = false;
        this.updateVisibility(false);
    }

    @Override
    public void showForSystem() {
        if (!this.mHiddenBySystem) return;
        this.mHiddenBySystem = false;
        this.updateVisibility(true);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback object) {
        ActionModeImpl actionModeImpl = this.mActionMode;
        if (actionModeImpl != null) {
            actionModeImpl.finish();
        }
        this.mOverlayLayout.setHideOnContentScrollEnabled(false);
        this.mContextView.killMode();
        object = new ActionModeImpl(this.mContextView.getContext(), (ActionMode.Callback)object);
        if (!((ActionModeImpl)object).dispatchOnCreate()) return null;
        this.mActionMode = object;
        ((ActionModeImpl)object).invalidate();
        this.mContextView.initForMode((ActionMode)object);
        this.animateToMode(true);
        this.mContextView.sendAccessibilityEvent(32);
        return object;
    }

    public class ActionModeImpl
    extends ActionMode
    implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private ActionMode.Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.mActionModeContext = context;
            this.mCallback = callback;
            this.mMenu = WindowDecorActionBar.this = new MenuBuilder(context).setDefaultShowAsAction(1);
            ((MenuBuilder)WindowDecorActionBar.this).setCallback(this);
        }

        public boolean dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                boolean bl = this.mCallback.onCreateActionMode(this, this.mMenu);
                return bl;
            }
            finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override
        public void finish() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            if (!WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
                WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
                WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
            } else {
                this.mCallback.onDestroyActionMode(this);
            }
            this.mCallback = null;
            WindowDecorActionBar.this.animateToMode(false);
            WindowDecorActionBar.this.mContextView.closeMode();
            WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
            WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
            WindowDecorActionBar.this.mActionMode = null;
        }

        @Override
        public View getCustomView() {
            View view = this.mCustomView;
            if (view == null) return null;
            return (View)view.get();
        }

        @Override
        public Menu getMenu() {
            return this.mMenu;
        }

        @Override
        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        @Override
        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.mContextView.getSubtitle();
        }

        @Override
        public CharSequence getTitle() {
            return WindowDecorActionBar.this.mContextView.getTitle();
        }

        @Override
        public void invalidate() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            this.mMenu.stopDispatchingItemsChanged();
            try {
                this.mCallback.onPrepareActionMode(this, this.mMenu);
                return;
            }
            finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override
        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.mContextView.isTitleOptional();
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder object, MenuItem menuItem) {
            object = this.mCallback;
            if (object == null) return false;
            return object.onActionItemClicked(this, menuItem);
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.mCallback == null) {
                return;
            }
            this.invalidate();
            WindowDecorActionBar.this.mContextView.showOverflowMenu();
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            if (this.mCallback == null) {
                return false;
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
            return true;
        }

        @Override
        public void setCustomView(View view) {
            WindowDecorActionBar.this.mContextView.setCustomView(view);
            this.mCustomView = new WeakReference<View>(view);
        }

        @Override
        public void setSubtitle(int n) {
            this.setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
        }

        @Override
        public void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setSubtitle(charSequence);
        }

        @Override
        public void setTitle(int n) {
            this.setTitle(WindowDecorActionBar.this.mContext.getResources().getString(n));
        }

        @Override
        public void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setTitle(charSequence);
        }

        @Override
        public void setTitleOptionalHint(boolean bl) {
            super.setTitleOptionalHint(bl);
            WindowDecorActionBar.this.mContextView.setTitleOptional(bl);
        }
    }

    public class TabImpl
    extends ActionBar.Tab {
        private ActionBar.TabListener mCallback;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;

        public ActionBar.TabListener getCallback() {
            return this.mCallback;
        }

        @Override
        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        @Override
        public View getCustomView() {
            return this.mCustomView;
        }

        @Override
        public Drawable getIcon() {
            return this.mIcon;
        }

        @Override
        public int getPosition() {
            return this.mPosition;
        }

        @Override
        public Object getTag() {
            return this.mTag;
        }

        @Override
        public CharSequence getText() {
            return this.mText;
        }

        @Override
        public void select() {
            WindowDecorActionBar.this.selectTab(this);
        }

        @Override
        public ActionBar.Tab setContentDescription(int n) {
            return this.setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText(n));
        }

        @Override
        public ActionBar.Tab setContentDescription(CharSequence charSequence) {
            this.mContentDesc = charSequence;
            if (this.mPosition < 0) return this;
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }

        @Override
        public ActionBar.Tab setCustomView(int n) {
            return this.setCustomView(LayoutInflater.from((Context)WindowDecorActionBar.this.getThemedContext()).inflate(n, null));
        }

        @Override
        public ActionBar.Tab setCustomView(View view) {
            this.mCustomView = view;
            if (this.mPosition < 0) return this;
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }

        @Override
        public ActionBar.Tab setIcon(int n) {
            return this.setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.mContext, n));
        }

        @Override
        public ActionBar.Tab setIcon(Drawable drawable2) {
            this.mIcon = drawable2;
            if (this.mPosition < 0) return this;
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }

        public void setPosition(int n) {
            this.mPosition = n;
        }

        @Override
        public ActionBar.Tab setTabListener(ActionBar.TabListener tabListener) {
            this.mCallback = tabListener;
            return this;
        }

        @Override
        public ActionBar.Tab setTag(Object object) {
            this.mTag = object;
            return this;
        }

        @Override
        public ActionBar.Tab setText(int n) {
            return this.setText(WindowDecorActionBar.this.mContext.getResources().getText(n));
        }

        @Override
        public ActionBar.Tab setText(CharSequence charSequence) {
            this.mText = charSequence;
            if (this.mPosition < 0) return this;
            WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition);
            return this;
        }
    }

}

