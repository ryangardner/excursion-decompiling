/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.Window
 *  android.view.Window$Callback
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;

public class ToolbarWidgetWrapper
implements DecorToolbar {
    private static final int AFFECTS_LOGO_MASK = 3;
    private static final long DEFAULT_FADE_DURATION_MS = 200L;
    private static final String TAG = "ToolbarWidgetWrapper";
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription = 0;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    boolean mMenuPrepared;
    private Drawable mNavIcon;
    private int mNavigationMode = 0;
    private Spinner mSpinner;
    private CharSequence mSubtitle;
    private View mTabView;
    CharSequence mTitle;
    private boolean mTitleSet;
    Toolbar mToolbar;
    Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean bl) {
        this(toolbar, bl, R.string.abc_action_bar_up_description, R.drawable.abc_ic_ab_back_material);
    }

    public ToolbarWidgetWrapper(Toolbar object, boolean bl, int n, int n2) {
        this.mToolbar = object;
        this.mTitle = ((Toolbar)((Object)object)).getTitle();
        this.mSubtitle = ((Toolbar)((Object)object)).getSubtitle();
        boolean bl2 = this.mTitle != null;
        this.mTitleSet = bl2;
        this.mNavIcon = ((Toolbar)((Object)object)).getNavigationIcon();
        object = TintTypedArray.obtainStyledAttributes(object.getContext(), null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        this.mDefaultNavigationIcon = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_homeAsUpIndicator);
        if (bl) {
            Object object2 = ((TintTypedArray)object).getText(R.styleable.ActionBar_title);
            if (!TextUtils.isEmpty((CharSequence)object2)) {
                this.setTitle((CharSequence)object2);
            }
            if (!TextUtils.isEmpty((CharSequence)(object2 = ((TintTypedArray)object).getText(R.styleable.ActionBar_subtitle)))) {
                this.setSubtitle((CharSequence)object2);
            }
            if ((object2 = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_logo)) != null) {
                this.setLogo((Drawable)object2);
            }
            if ((object2 = ((TintTypedArray)object).getDrawable(R.styleable.ActionBar_icon)) != null) {
                this.setIcon((Drawable)object2);
            }
            if (this.mNavIcon == null && (object2 = this.mDefaultNavigationIcon) != null) {
                this.setNavigationIcon((Drawable)object2);
            }
            this.setDisplayOptions(((TintTypedArray)object).getInt(R.styleable.ActionBar_displayOptions, 0));
            n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_customNavigationLayout, 0);
            if (n2 != 0) {
                this.setCustomView(LayoutInflater.from((Context)this.mToolbar.getContext()).inflate(n2, (ViewGroup)this.mToolbar, false));
                this.setDisplayOptions(this.mDisplayOpts | 16);
            }
            if ((n2 = ((TintTypedArray)object).getLayoutDimension(R.styleable.ActionBar_height, 0)) > 0) {
                object2 = this.mToolbar.getLayoutParams();
                ((ViewGroup.LayoutParams)object2).height = n2;
                this.mToolbar.setLayoutParams((ViewGroup.LayoutParams)object2);
            }
            n2 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.ActionBar_contentInsetStart, -1);
            int n3 = ((TintTypedArray)object).getDimensionPixelOffset(R.styleable.ActionBar_contentInsetEnd, -1);
            if (n2 >= 0 || n3 >= 0) {
                this.mToolbar.setContentInsetsRelative(Math.max(n2, 0), Math.max(n3, 0));
            }
            if ((n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_titleTextStyle, 0)) != 0) {
                object2 = this.mToolbar;
                ((Toolbar)((Object)object2)).setTitleTextAppearance(object2.getContext(), n2);
            }
            if ((n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_subtitleTextStyle, 0)) != 0) {
                object2 = this.mToolbar;
                ((Toolbar)((Object)object2)).setSubtitleTextAppearance(object2.getContext(), n2);
            }
            if ((n2 = ((TintTypedArray)object).getResourceId(R.styleable.ActionBar_popupTheme, 0)) != 0) {
                this.mToolbar.setPopupTheme(n2);
            }
        } else {
            this.mDisplayOpts = this.detectDisplayOptions();
        }
        ((TintTypedArray)object).recycle();
        this.setDefaultNavigationContentDescription(n);
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            final ActionMenuItem mNavItem;
            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, 16908332, 0, 0, ToolbarWidgetWrapper.this.mTitle);
            }

            public void onClick(View view) {
                if (ToolbarWidgetWrapper.this.mWindowCallback == null) return;
                if (!ToolbarWidgetWrapper.this.mMenuPrepared) return;
                ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, (MenuItem)this.mNavItem);
            }
        });
    }

    private int detectDisplayOptions() {
        if (this.mToolbar.getNavigationIcon() == null) return 11;
        int n = 15;
        this.mDefaultNavigationIcon = this.mToolbar.getNavigationIcon();
        return n;
    }

    private void ensureSpinner() {
        if (this.mSpinner != null) return;
        this.mSpinner = new AppCompatSpinner(this.getContext(), null, R.attr.actionDropDownStyle);
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -2, 8388627);
        this.mSpinner.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    private void setTitleInt(CharSequence charSequence) {
        this.mTitle = charSequence;
        if ((this.mDisplayOpts & 8) == 0) return;
        this.mToolbar.setTitle(charSequence);
    }

    private void updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) == 0) return;
        if (TextUtils.isEmpty((CharSequence)this.mHomeDescription)) {
            this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            return;
        }
        this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
    }

    private void updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) == 0) {
            this.mToolbar.setNavigationIcon(null);
            return;
        }
        Toolbar toolbar = this.mToolbar;
        Drawable drawable2 = this.mNavIcon;
        if (drawable2 == null) {
            drawable2 = this.mDefaultNavigationIcon;
        }
        toolbar.setNavigationIcon(drawable2);
    }

    private void updateToolbarLogo() {
        Drawable drawable2;
        int n = this.mDisplayOpts;
        if ((n & 2) != 0) {
            if ((n & 1) != 0) {
                drawable2 = this.mLogo;
                if (drawable2 == null) {
                    drawable2 = this.mIcon;
                }
            } else {
                drawable2 = this.mIcon;
            }
        } else {
            drawable2 = null;
        }
        this.mToolbar.setLogo(drawable2);
    }

    @Override
    public void animateToVisibility(int n) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.setupAnimatorToVisibility(n, 200L);
        if (viewPropertyAnimatorCompat == null) return;
        viewPropertyAnimatorCompat.start();
    }

    @Override
    public boolean canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu();
    }

    @Override
    public void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    @Override
    public void dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus();
    }

    @Override
    public Context getContext() {
        return this.mToolbar.getContext();
    }

    @Override
    public View getCustomView() {
        return this.mCustomView;
    }

    @Override
    public int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    @Override
    public int getDropdownItemCount() {
        Spinner spinner = this.mSpinner;
        if (spinner == null) return 0;
        return spinner.getCount();
    }

    @Override
    public int getDropdownSelectedPosition() {
        Spinner spinner = this.mSpinner;
        if (spinner == null) return 0;
        return spinner.getSelectedItemPosition();
    }

    @Override
    public int getHeight() {
        return this.mToolbar.getHeight();
    }

    @Override
    public Menu getMenu() {
        return this.mToolbar.getMenu();
    }

    @Override
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override
    public CharSequence getSubtitle() {
        return this.mToolbar.getSubtitle();
    }

    @Override
    public CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    @Override
    public ViewGroup getViewGroup() {
        return this.mToolbar;
    }

    @Override
    public int getVisibility() {
        return this.mToolbar.getVisibility();
    }

    @Override
    public boolean hasEmbeddedTabs() {
        if (this.mTabView == null) return false;
        return true;
    }

    @Override
    public boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    @Override
    public boolean hasIcon() {
        if (this.mIcon == null) return false;
        return true;
    }

    @Override
    public boolean hasLogo() {
        if (this.mLogo == null) return false;
        return true;
    }

    @Override
    public boolean hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu();
    }

    @Override
    public void initIndeterminateProgress() {
        Log.i((String)TAG, (String)"Progress display unsupported");
    }

    @Override
    public void initProgress() {
        Log.i((String)TAG, (String)"Progress display unsupported");
    }

    @Override
    public boolean isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    @Override
    public boolean isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing();
    }

    @Override
    public boolean isTitleTruncated() {
        return this.mToolbar.isTitleTruncated();
    }

    @Override
    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.restoreHierarchyState(sparseArray);
    }

    @Override
    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.saveHierarchyState(sparseArray);
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        ViewCompat.setBackground((View)this.mToolbar, drawable2);
    }

    @Override
    public void setCollapsible(boolean bl) {
        this.mToolbar.setCollapsible(bl);
    }

    @Override
    public void setCustomView(View view) {
        View view2 = this.mCustomView;
        if (view2 != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.removeView(view2);
        }
        this.mCustomView = view;
        if (view == null) return;
        if ((this.mDisplayOpts & 16) == 0) return;
        this.mToolbar.addView(view);
    }

    @Override
    public void setDefaultNavigationContentDescription(int n) {
        if (n == this.mDefaultNavigationContentDescription) {
            return;
        }
        this.mDefaultNavigationContentDescription = n;
        if (!TextUtils.isEmpty((CharSequence)this.mToolbar.getNavigationContentDescription())) return;
        this.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
    }

    @Override
    public void setDefaultNavigationIcon(Drawable drawable2) {
        if (this.mDefaultNavigationIcon == drawable2) return;
        this.mDefaultNavigationIcon = drawable2;
        this.updateNavigationIcon();
    }

    @Override
    public void setDisplayOptions(int n) {
        int n2 = this.mDisplayOpts ^ n;
        this.mDisplayOpts = n;
        if (n2 == 0) return;
        if ((n2 & 4) != 0) {
            if ((n & 4) != 0) {
                this.updateHomeAccessibility();
            }
            this.updateNavigationIcon();
        }
        if ((n2 & 3) != 0) {
            this.updateToolbarLogo();
        }
        if ((n2 & 8) != 0) {
            if ((n & 8) != 0) {
                this.mToolbar.setTitle(this.mTitle);
                this.mToolbar.setSubtitle(this.mSubtitle);
            } else {
                this.mToolbar.setTitle(null);
                this.mToolbar.setSubtitle(null);
            }
        }
        if ((n2 & 16) == 0) return;
        View view = this.mCustomView;
        if (view == null) return;
        if ((n & 16) != 0) {
            this.mToolbar.addView(view);
            return;
        }
        this.mToolbar.removeView(view);
    }

    @Override
    public void setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.ensureSpinner();
        this.mSpinner.setAdapter(spinnerAdapter);
        this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override
    public void setDropdownSelectedPosition(int n) {
        Spinner spinner = this.mSpinner;
        if (spinner == null) throw new IllegalStateException("Can't set dropdown selected position without an adapter");
        spinner.setSelection(n);
    }

    @Override
    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        Toolbar toolbar;
        Object object = this.mTabView;
        if (object != null && (object = object.getParent()) == (toolbar = this.mToolbar)) {
            toolbar.removeView(this.mTabView);
        }
        this.mTabView = scrollingTabContainerView;
        if (scrollingTabContainerView == null) return;
        if (this.mNavigationMode != 2) return;
        this.mToolbar.addView((View)scrollingTabContainerView, 0);
        object = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
        object.width = -2;
        object.height = -2;
        object.gravity = 8388691;
        scrollingTabContainerView.setAllowCollapse(true);
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
    }

    @Override
    public void setIcon(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setIcon(drawable2);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        this.updateToolbarLogo();
    }

    @Override
    public void setLogo(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setLogo(drawable2);
    }

    @Override
    public void setLogo(Drawable drawable2) {
        this.mLogo = drawable2;
        this.updateToolbarLogo();
    }

    @Override
    public void setMenu(Menu menu2, MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            ActionMenuPresenter actionMenuPresenter;
            this.mActionMenuPresenter = actionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
            actionMenuPresenter.setId(R.id.action_menu_presenter);
        }
        this.mActionMenuPresenter.setCallback(callback);
        this.mToolbar.setMenu((MenuBuilder)menu2, this.mActionMenuPresenter);
    }

    @Override
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mToolbar.setMenuCallbacks(callback, callback2);
    }

    @Override
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override
    public void setNavigationContentDescription(int n) {
        String string2 = n == 0 ? null : this.getContext().getString(n);
        this.setNavigationContentDescription(string2);
    }

    @Override
    public void setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        this.updateHomeAccessibility();
    }

    @Override
    public void setNavigationIcon(int n) {
        Drawable drawable2 = n != 0 ? AppCompatResources.getDrawable(this.getContext(), n) : null;
        this.setNavigationIcon(drawable2);
    }

    @Override
    public void setNavigationIcon(Drawable drawable2) {
        this.mNavIcon = drawable2;
        this.updateNavigationIcon();
    }

    @Override
    public void setNavigationMode(int n) {
        Object object;
        int n2 = this.mNavigationMode;
        if (n == n2) return;
        if (n2 != 1) {
            if (n2 == 2 && (object = this.mTabView) != null) {
                ViewParent viewParent = object.getParent();
                object = this.mToolbar;
                if (viewParent == object) {
                    object.removeView(this.mTabView);
                }
            }
        } else {
            Toolbar toolbar;
            object = this.mSpinner;
            if (object != null && (object = object.getParent()) == (toolbar = this.mToolbar)) {
                toolbar.removeView((View)this.mSpinner);
            }
        }
        this.mNavigationMode = n;
        if (n == 0) return;
        if (n == 1) {
            this.ensureSpinner();
            this.mToolbar.addView((View)this.mSpinner, 0);
            return;
        }
        if (n == 2) {
            object = this.mTabView;
            if (object == null) return;
            this.mToolbar.addView((View)object, 0);
            object = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
            object.width = -2;
            object.height = -2;
            object.gravity = 8388691;
            return;
        }
        object = new StringBuilder();
        object.append("Invalid navigation mode ");
        object.append(n);
        throw new IllegalArgumentException(object.toString());
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        if ((this.mDisplayOpts & 8) == 0) return;
        this.mToolbar.setSubtitle(charSequence);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mTitleSet = true;
        this.setTitleInt(charSequence);
    }

    @Override
    public void setVisibility(int n) {
        this.mToolbar.setVisibility(n);
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        if (this.mTitleSet) return;
        this.setTitleInt(charSequence);
    }

    @Override
    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(final int n, long l) {
        float f;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mToolbar);
        if (n == 0) {
            f = 1.0f;
            return viewPropertyAnimatorCompat.alpha(f).setDuration(l).setListener(new ViewPropertyAnimatorListenerAdapter(){
                private boolean mCanceled = false;

                @Override
                public void onAnimationCancel(View view) {
                    this.mCanceled = true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    if (this.mCanceled) return;
                    ToolbarWidgetWrapper.this.mToolbar.setVisibility(n);
                }

                @Override
                public void onAnimationStart(View view) {
                    ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
                }
            });
        }
        f = 0.0f;
        return viewPropertyAnimatorCompat.alpha(f).setDuration(l).setListener(new /* invalid duplicate definition of identical inner class */);
    }

    @Override
    public boolean showOverflowMenu() {
        return this.mToolbar.showOverflowMenu();
    }

}

