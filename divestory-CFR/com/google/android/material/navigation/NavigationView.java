/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.Color
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.Window
 */
package com.google.android.material.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class NavigationView
extends ScrimInsetsFrameLayout {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private static final int DEF_STYLE_RES;
    private static final int[] DISABLED_STATE_SET;
    private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
    OnNavigationItemSelectedListener listener;
    private final int maxWidth;
    private final NavigationMenu menu;
    private MenuInflater menuInflater;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private final NavigationMenuPresenter presenter = new NavigationMenuPresenter();
    private final int[] tmpLocation = new int[2];

    static {
        DISABLED_STATE_SET = new int[]{-16842910};
        DEF_STYLE_RES = R.style.Widget_Design_NavigationView;
    }

    public NavigationView(Context context) {
        this(context, null);
    }

    public NavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.navigationViewStyle);
    }

    public NavigationView(Context object, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap((Context)object, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        int n2;
        boolean bl;
        Context context = this.getContext();
        this.menu = new NavigationMenu(context);
        TintTypedArray tintTypedArray = ThemeEnforcement.obtainTintedStyledAttributes(context, attributeSet, R.styleable.NavigationView, n, DEF_STYLE_RES, new int[0]);
        if (tintTypedArray.hasValue(R.styleable.NavigationView_android_background)) {
            ViewCompat.setBackground((View)this, tintTypedArray.getDrawable(R.styleable.NavigationView_android_background));
        }
        if (this.getBackground() == null || this.getBackground() instanceof ColorDrawable) {
            attributeSet = this.getBackground();
            object = new MaterialShapeDrawable();
            if (attributeSet instanceof ColorDrawable) {
                ((MaterialShapeDrawable)object).setFillColor(ColorStateList.valueOf((int)((ColorDrawable)attributeSet).getColor()));
            }
            ((MaterialShapeDrawable)object).initializeElevationOverlay(context);
            ViewCompat.setBackground((View)this, (Drawable)object);
        }
        if (tintTypedArray.hasValue(R.styleable.NavigationView_elevation)) {
            this.setElevation(tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_elevation, 0));
        }
        this.setFitsSystemWindows(tintTypedArray.getBoolean(R.styleable.NavigationView_android_fitsSystemWindows, false));
        this.maxWidth = tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_android_maxWidth, 0);
        attributeSet = tintTypedArray.hasValue(R.styleable.NavigationView_itemIconTint) ? tintTypedArray.getColorStateList(R.styleable.NavigationView_itemIconTint) : this.createDefaultColorStateList(16842808);
        if (tintTypedArray.hasValue(R.styleable.NavigationView_itemTextAppearance)) {
            n = tintTypedArray.getResourceId(R.styleable.NavigationView_itemTextAppearance, 0);
            bl = true;
        } else {
            n = 0;
            bl = false;
        }
        if (tintTypedArray.hasValue(R.styleable.NavigationView_itemIconSize)) {
            this.setItemIconSize(tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemIconSize, 0));
        }
        object = null;
        if (tintTypedArray.hasValue(R.styleable.NavigationView_itemTextColor)) {
            object = tintTypedArray.getColorStateList(R.styleable.NavigationView_itemTextColor);
        }
        Object object2 = object;
        if (!bl) {
            object2 = object;
            if (object == null) {
                object2 = this.createDefaultColorStateList(16842806);
            }
        }
        Drawable drawable2 = tintTypedArray.getDrawable(R.styleable.NavigationView_itemBackground);
        object = drawable2;
        if (drawable2 == null) {
            object = drawable2;
            if (this.hasShapeAppearance(tintTypedArray)) {
                object = this.createDefaultItemBackground(tintTypedArray);
            }
        }
        if (tintTypedArray.hasValue(R.styleable.NavigationView_itemHorizontalPadding)) {
            n2 = tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemHorizontalPadding, 0);
            this.presenter.setItemHorizontalPadding(n2);
        }
        n2 = tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemIconPadding, 0);
        this.setItemMaxLines(tintTypedArray.getInt(R.styleable.NavigationView_itemMaxLines, 1));
        this.menu.setCallback(new MenuBuilder.Callback(){

            @Override
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                if (NavigationView.this.listener == null) return false;
                if (!NavigationView.this.listener.onNavigationItemSelected(menuItem)) return false;
                return true;
            }

            @Override
            public void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        });
        this.presenter.setId(1);
        this.presenter.initForMenu(context, this.menu);
        this.presenter.setItemIconTintList((ColorStateList)attributeSet);
        this.presenter.setOverScrollMode(this.getOverScrollMode());
        if (bl) {
            this.presenter.setItemTextAppearance(n);
        }
        this.presenter.setItemTextColor((ColorStateList)object2);
        this.presenter.setItemBackground((Drawable)object);
        this.presenter.setItemIconPadding(n2);
        this.menu.addMenuPresenter(this.presenter);
        this.addView((View)this.presenter.getMenuView((ViewGroup)this));
        if (tintTypedArray.hasValue(R.styleable.NavigationView_menu)) {
            this.inflateMenu(tintTypedArray.getResourceId(R.styleable.NavigationView_menu, 0));
        }
        if (tintTypedArray.hasValue(R.styleable.NavigationView_headerLayout)) {
            this.inflateHeaderView(tintTypedArray.getResourceId(R.styleable.NavigationView_headerLayout, 0));
        }
        tintTypedArray.recycle();
        this.setupInsetScrimsListener();
    }

    private ColorStateList createDefaultColorStateList(int n) {
        int[] arrn = new TypedValue();
        if (!this.getContext().getTheme().resolveAttribute(n, (TypedValue)arrn, true)) {
            return null;
        }
        ColorStateList colorStateList = AppCompatResources.getColorStateList(this.getContext(), arrn.resourceId);
        if (!this.getContext().getTheme().resolveAttribute(R.attr.colorPrimary, (TypedValue)arrn, true)) {
            return null;
        }
        int n2 = arrn.data;
        int n3 = colorStateList.getDefaultColor();
        int[] arrn2 = DISABLED_STATE_SET;
        arrn = CHECKED_STATE_SET;
        int[] arrn3 = EMPTY_STATE_SET;
        n = colorStateList.getColorForState(DISABLED_STATE_SET, n3);
        return new ColorStateList((int[][])new int[][]{arrn2, arrn, arrn3}, new int[]{n, n2, n3});
    }

    private final Drawable createDefaultItemBackground(TintTypedArray tintTypedArray) {
        int n = tintTypedArray.getResourceId(R.styleable.NavigationView_itemShapeAppearance, 0);
        int n2 = tintTypedArray.getResourceId(R.styleable.NavigationView_itemShapeAppearanceOverlay, 0);
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(this.getContext(), n, n2).build());
        materialShapeDrawable.setFillColor(MaterialResources.getColorStateList(this.getContext(), tintTypedArray, R.styleable.NavigationView_itemShapeFillColor));
        return new InsetDrawable((Drawable)materialShapeDrawable, tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetStart, 0), tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetTop, 0), tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetEnd, 0), tintTypedArray.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetBottom, 0));
    }

    private MenuInflater getMenuInflater() {
        if (this.menuInflater != null) return this.menuInflater;
        this.menuInflater = new SupportMenuInflater(this.getContext());
        return this.menuInflater;
    }

    private boolean hasShapeAppearance(TintTypedArray tintTypedArray) {
        if (tintTypedArray.hasValue(R.styleable.NavigationView_itemShapeAppearance)) return true;
        if (tintTypedArray.hasValue(R.styleable.NavigationView_itemShapeAppearanceOverlay)) return true;
        return false;
    }

    private void setupInsetScrimsListener() {
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

            public void onGlobalLayout() {
                Object object = NavigationView.this;
                object.getLocationOnScreen(((NavigationView)((Object)object)).tmpLocation);
                object = NavigationView.this.tmpLocation;
                boolean bl = true;
                boolean bl2 = object[1] == 0;
                NavigationView.this.presenter.setBehindStatusBar(bl2);
                NavigationView.this.setDrawTopInsetForeground(bl2);
                object = ContextUtils.getActivity(NavigationView.this.getContext());
                if (object == null) return;
                if (Build.VERSION.SDK_INT < 21) return;
                boolean bl3 = object.findViewById(16908290).getHeight() == NavigationView.this.getHeight();
                boolean bl4 = Color.alpha((int)object.getWindow().getNavigationBarColor()) != 0;
                object = NavigationView.this;
                bl2 = bl3 && bl4 ? bl : false;
                ((ScrimInsetsFrameLayout)((Object)object)).setDrawBottomInsetForeground(bl2);
            }
        };
        this.getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    public void addHeaderView(View view) {
        this.presenter.addHeaderView(view);
    }

    public MenuItem getCheckedItem() {
        return this.presenter.getCheckedItem();
    }

    public int getHeaderCount() {
        return this.presenter.getHeaderCount();
    }

    public View getHeaderView(int n) {
        return this.presenter.getHeaderView(n);
    }

    public Drawable getItemBackground() {
        return this.presenter.getItemBackground();
    }

    public int getItemHorizontalPadding() {
        return this.presenter.getItemHorizontalPadding();
    }

    public int getItemIconPadding() {
        return this.presenter.getItemIconPadding();
    }

    public ColorStateList getItemIconTintList() {
        return this.presenter.getItemTintList();
    }

    public int getItemMaxLines() {
        return this.presenter.getItemMaxLines();
    }

    public ColorStateList getItemTextColor() {
        return this.presenter.getItemTextColor();
    }

    public Menu getMenu() {
        return this.menu;
    }

    public View inflateHeaderView(int n) {
        return this.presenter.inflateHeaderView(n);
    }

    public void inflateMenu(int n) {
        this.presenter.setUpdateSuspended(true);
        this.getMenuInflater().inflate(n, (Menu)this.menu);
        this.presenter.setUpdateSuspended(false);
        this.presenter.updateMenuView(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation((View)this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT < 16) {
            this.getViewTreeObserver().removeGlobalOnLayoutListener(this.onGlobalLayoutListener);
            return;
        }
        this.getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    @Override
    protected void onInsetsChanged(WindowInsetsCompat windowInsetsCompat) {
        this.presenter.dispatchApplyWindowInsets(windowInsetsCompat);
    }

    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode((int)n);
        if (n3 != Integer.MIN_VALUE) {
            if (n3 == 0) {
                n = View.MeasureSpec.makeMeasureSpec((int)this.maxWidth, (int)1073741824);
            }
        } else {
            n = View.MeasureSpec.makeMeasureSpec((int)Math.min(View.MeasureSpec.getSize((int)n), this.maxWidth), (int)1073741824);
        }
        super.onMeasure(n, n2);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.menu.restorePresenterStates(parcelable.menuState);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.menuState = new Bundle();
        this.menu.savePresenterStates(savedState.menuState);
        return savedState;
    }

    public void removeHeaderView(View view) {
        this.presenter.removeHeaderView(view);
    }

    public void setCheckedItem(int n) {
        MenuItem menuItem = this.menu.findItem(n);
        if (menuItem == null) return;
        this.presenter.setCheckedItem((MenuItemImpl)menuItem);
    }

    public void setCheckedItem(MenuItem menuItem) {
        if ((menuItem = this.menu.findItem(menuItem.getItemId())) == null) throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
        this.presenter.setCheckedItem((MenuItemImpl)menuItem);
    }

    public void setElevation(float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.setElevation(f);
        }
        MaterialShapeUtils.setElevation((View)this, f);
    }

    public void setItemBackground(Drawable drawable2) {
        this.presenter.setItemBackground(drawable2);
    }

    public void setItemBackgroundResource(int n) {
        this.setItemBackground(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setItemHorizontalPadding(int n) {
        this.presenter.setItemHorizontalPadding(n);
    }

    public void setItemHorizontalPaddingResource(int n) {
        this.presenter.setItemHorizontalPadding(this.getResources().getDimensionPixelSize(n));
    }

    public void setItemIconPadding(int n) {
        this.presenter.setItemIconPadding(n);
    }

    public void setItemIconPaddingResource(int n) {
        this.presenter.setItemIconPadding(this.getResources().getDimensionPixelSize(n));
    }

    public void setItemIconSize(int n) {
        this.presenter.setItemIconSize(n);
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.presenter.setItemIconTintList(colorStateList);
    }

    public void setItemMaxLines(int n) {
        this.presenter.setItemMaxLines(n);
    }

    public void setItemTextAppearance(int n) {
        this.presenter.setItemTextAppearance(n);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.presenter.setItemTextColor(colorStateList);
    }

    public void setNavigationItemSelectedListener(OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        this.listener = onNavigationItemSelectedListener;
    }

    public void setOverScrollMode(int n) {
        super.setOverScrollMode(n);
        NavigationMenuPresenter navigationMenuPresenter = this.presenter;
        if (navigationMenuPresenter == null) return;
        navigationMenuPresenter.setOverScrollMode(n);
    }

    public static interface OnNavigationItemSelectedListener {
        public boolean onNavigationItemSelected(MenuItem var1);
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public Bundle menuState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.menuState = parcel.readBundle(classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeBundle(this.menuState);
        }

    }

}

