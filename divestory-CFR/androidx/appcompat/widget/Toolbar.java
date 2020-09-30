/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Layout
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.RtlSpacingHelper;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.List;

public class Toolbar
extends ViewGroup {
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    int mButtonGravity;
    ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity = 8388627;
    private final ArrayList<View> mHiddenViews = new ArrayList();
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (Toolbar.this.mOnMenuItemClickListener == null) return false;
            return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }
    };
    private ImageButton mNavButtonView;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable = new Runnable(){

        @Override
        public void run() {
            Toolbar.this.showOverflowMenu();
        }
    };
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private ColorStateList mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins = new int[2];
    private final ArrayList<View> mTempViews = new ArrayList();
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private ColorStateList mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public Toolbar(Context object, AttributeSet attributeSet, int n) {
        super(object, attributeSet, n);
        int n2;
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(this.getContext(), attributeSet, R.styleable.Toolbar, n, 0);
        ViewCompat.saveAttributeDataForStyleable((View)this, object, R.styleable.Toolbar, attributeSet, tintTypedArray.getWrappedTypeArray(), n, 0);
        this.mTitleTextAppearance = tintTypedArray.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = tintTypedArray.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = tintTypedArray.getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = tintTypedArray.getInteger(R.styleable.Toolbar_buttonGravity, 48);
        n = n2 = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
        if (tintTypedArray.hasValue(R.styleable.Toolbar_titleMargins)) {
            n = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, n2);
        }
        this.mTitleMarginBottom = n;
        this.mTitleMarginTop = n;
        this.mTitleMarginEnd = n;
        this.mTitleMarginStart = n;
        n = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
        if (n >= 0) {
            this.mTitleMarginStart = n;
        }
        if ((n = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1)) >= 0) {
            this.mTitleMarginEnd = n;
        }
        if ((n = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1)) >= 0) {
            this.mTitleMarginTop = n;
        }
        if ((n = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1)) >= 0) {
            this.mTitleMarginBottom = n;
        }
        this.mMaxButtonHeight = tintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
        n2 = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int n3 = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int n4 = tintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
        n = tintTypedArray.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n4, n);
        if (n2 != Integer.MIN_VALUE || n3 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(n2, n3);
        }
        this.mContentInsetStartWithNavigation = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = tintTypedArray.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.mCollapseIcon = tintTypedArray.getDrawable(R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = tintTypedArray.getText(R.styleable.Toolbar_collapseContentDescription);
        object = tintTypedArray.getText(R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty((CharSequence)object)) {
            this.setTitle((CharSequence)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = tintTypedArray.getText(R.styleable.Toolbar_subtitle)))) {
            this.setSubtitle((CharSequence)object);
        }
        this.mPopupContext = this.getContext();
        this.setPopupTheme(tintTypedArray.getResourceId(R.styleable.Toolbar_popupTheme, 0));
        object = tintTypedArray.getDrawable(R.styleable.Toolbar_navigationIcon);
        if (object != null) {
            this.setNavigationIcon((Drawable)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = tintTypedArray.getText(R.styleable.Toolbar_navigationContentDescription)))) {
            this.setNavigationContentDescription((CharSequence)object);
        }
        if ((object = tintTypedArray.getDrawable(R.styleable.Toolbar_logo)) != null) {
            this.setLogo((Drawable)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = tintTypedArray.getText(R.styleable.Toolbar_logoDescription)))) {
            this.setLogoDescription((CharSequence)object);
        }
        if (tintTypedArray.hasValue(R.styleable.Toolbar_titleTextColor)) {
            this.setTitleTextColor(tintTypedArray.getColorStateList(R.styleable.Toolbar_titleTextColor));
        }
        if (tintTypedArray.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            this.setSubtitleTextColor(tintTypedArray.getColorStateList(R.styleable.Toolbar_subtitleTextColor));
        }
        if (tintTypedArray.hasValue(R.styleable.Toolbar_menu)) {
            this.inflateMenu(tintTypedArray.getResourceId(R.styleable.Toolbar_menu, 0));
        }
        tintTypedArray.recycle();
    }

    private void addCustomViewsWithGravity(List<View> list, int n) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        int n3 = 0;
        n2 = n2 == 1 ? 1 : 0;
        int n4 = this.getChildCount();
        int n5 = GravityCompat.getAbsoluteGravity(n, ViewCompat.getLayoutDirection((View)this));
        list.clear();
        n = n3;
        if (n2 != 0) {
            n = n4 - 1;
            while (n >= 0) {
                View view = this.getChildAt(n);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType == 0 && this.shouldLayout(view) && this.getChildHorizontalGravity(layoutParams.gravity) == n5) {
                    list.add(view);
                }
                --n;
            }
            return;
        }
        while (n < n4) {
            View view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.mViewType == 0 && this.shouldLayout(view) && this.getChildHorizontalGravity(layoutParams.gravity) == n5) {
                list.add(view);
            }
            ++n;
        }
    }

    private void addSystemView(View view, boolean bl) {
        Object object = view.getLayoutParams();
        object = object == null ? this.generateDefaultLayoutParams() : (!this.checkLayoutParams((ViewGroup.LayoutParams)object) ? this.generateLayoutParams((ViewGroup.LayoutParams)object) : (LayoutParams)((Object)object));
        object.mViewType = 1;
        if (bl && this.mExpandedActionView != null) {
            view.setLayoutParams(object);
            this.mHiddenViews.add(view);
            return;
        }
        this.addView(view, object);
    }

    private void ensureContentInsets() {
        if (this.mContentInsets != null) return;
        this.mContentInsets = new RtlSpacingHelper();
    }

    private void ensureLogoView() {
        if (this.mLogoView != null) return;
        this.mLogoView = new AppCompatImageView(this.getContext());
    }

    private void ensureMenu() {
        this.ensureMenuView();
        if (this.mMenuView.peekMenu() != null) return;
        MenuBuilder menuBuilder = (MenuBuilder)this.mMenuView.getMenu();
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        this.mMenuView.setExpandedActionViewsExclusive(true);
        menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
    }

    private void ensureMenuView() {
        Object object;
        if (this.mMenuView != null) return;
        this.mMenuView = object = new ActionMenuView(this.getContext());
        object.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
        this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
        object = this.generateDefaultLayoutParams();
        ((LayoutParams)object).gravity = 8388613 | this.mButtonGravity & 112;
        this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)object);
        this.addSystemView((View)this.mMenuView, false);
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView != null) return;
        this.mNavButtonView = new AppCompatImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
        LayoutParams layoutParams = this.generateDefaultLayoutParams();
        layoutParams.gravity = 8388611 | this.mButtonGravity & 112;
        this.mNavButtonView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    private int getChildHorizontalGravity(int n) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        int n3 = GravityCompat.getAbsoluteGravity(n, n2) & 7;
        if (n3 == 1) return n3;
        n = 3;
        if (n3 == 3) return n3;
        if (n3 == 5) return n3;
        if (n2 != 1) return n;
        return 5;
    }

    private int getChildTop(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n2 = view.getMeasuredHeight();
        n = n > 0 ? (n2 - n) / 2 : 0;
        int n3 = this.getChildVerticalGravity(layoutParams.gravity);
        if (n3 == 48) return this.getPaddingTop() - n;
        if (n3 == 80) return this.getHeight() - this.getPaddingBottom() - n2 - layoutParams.bottomMargin - n;
        int n4 = this.getPaddingTop();
        int n5 = this.getPaddingBottom();
        n = this.getHeight();
        n3 = (n - n4 - n5 - n2) / 2;
        if (n3 < layoutParams.topMargin) {
            n = layoutParams.topMargin;
            return n4 + n;
        }
        n2 = n - n5 - n2 - n3 - n4;
        n = n3;
        if (n2 >= layoutParams.bottomMargin) return n4 + n;
        n = Math.max(0, n3 - (layoutParams.bottomMargin - n2));
        return n4 + n;
    }

    private int getChildVerticalGravity(int n) {
        int n2;
        n = n2 = n & 112;
        if (n2 == 16) return n;
        n = n2;
        if (n2 == 48) return n;
        n = n2;
        if (n2 == 80) return n;
        return this.mGravity & 112;
    }

    private int getHorizontalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)view) + MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)view);
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.getContext());
    }

    private int getVerticalMargins(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return view.topMargin + view.bottomMargin;
    }

    private int getViewListMeasuredWidth(List<View> list, int[] object) {
        int n = object[0];
        int n2 = object[1];
        int n3 = list.size();
        int n4 = 0;
        int n5 = 0;
        while (n4 < n3) {
            View view = list.get(n4);
            object = (LayoutParams)view.getLayoutParams();
            n = object.leftMargin - n;
            n2 = object.rightMargin - n2;
            int n6 = Math.max(0, n);
            int n7 = Math.max(0, n2);
            n = Math.max(0, -n);
            n2 = Math.max(0, -n2);
            n5 += n6 + view.getMeasuredWidth() + n7;
            ++n4;
        }
        return n5;
    }

    private boolean isChildOrHidden(View view) {
        if (view.getParent() == this) return true;
        if (this.mHiddenViews.contains((Object)view)) return true;
        return false;
    }

    private int layoutChildLeft(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.leftMargin - arrn[0];
        n += Math.max(0, n3);
        arrn[0] = Math.max(0, -n3);
        n2 = this.getChildTop(view, n2);
        n3 = view.getMeasuredWidth();
        view.layout(n, n2, n + n3, view.getMeasuredHeight() + n2);
        return n + (n3 + layoutParams.rightMargin);
    }

    private int layoutChildRight(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.rightMargin - arrn[1];
        n -= Math.max(0, n3);
        arrn[1] = Math.max(0, -n3);
        n3 = this.getChildTop(view, n2);
        n2 = view.getMeasuredWidth();
        view.layout(n - n2, n3, n, view.getMeasuredHeight() + n3);
        return n - (n2 + layoutParams.leftMargin);
    }

    private int measureChildCollapseMargins(View view, int n, int n2, int n3, int n4, int[] arrn) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n5 = marginLayoutParams.leftMargin - arrn[0];
        int n6 = marginLayoutParams.rightMargin - arrn[1];
        int n7 = Math.max(0, n5) + Math.max(0, n6);
        arrn[0] = Math.max(0, -n5);
        arrn[1] = Math.max(0, -n6);
        view.measure(Toolbar.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + n7 + n2), (int)marginLayoutParams.width), Toolbar.getChildMeasureSpec((int)n3, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4), (int)marginLayoutParams.height));
        return view.getMeasuredWidth() + n7;
    }

    private void measureChildConstrained(View view, int n, int n2, int n3, int n4, int n5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n6 = Toolbar.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2), (int)marginLayoutParams.width);
        n2 = Toolbar.getChildMeasureSpec((int)n3, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4), (int)marginLayoutParams.height);
        n3 = View.MeasureSpec.getMode((int)n2);
        n = n2;
        if (n3 != 1073741824) {
            n = n2;
            if (n5 >= 0) {
                n = n5;
                if (n3 != 0) {
                    n = Math.min(View.MeasureSpec.getSize((int)n2), n5);
                }
                n = View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824);
            }
        }
        view.measure(n6, n);
    }

    private void postShowOverflowMenu() {
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
        this.post(this.mShowOverflowMenuRunnable);
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = this.getChildAt(n2);
            if (this.shouldLayout(view) && view.getMeasuredWidth() > 0 && view.getMeasuredHeight() > 0) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    private boolean shouldLayout(View view) {
        if (view == null) return false;
        if (view.getParent() != this) return false;
        if (view.getVisibility() == 8) return false;
        return true;
    }

    void addChildrenForExpandedActionView() {
        int n = this.mHiddenViews.size() - 1;
        do {
            if (n < 0) {
                this.mHiddenViews.clear();
                return;
            }
            this.addView(this.mHiddenViews.get(n));
            --n;
        } while (true);
    }

    public boolean canShowOverflowMenu() {
        if (this.getVisibility() != 0) return false;
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) return false;
        if (!actionMenuView.isOverflowReserved()) return false;
        return true;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!super.checkLayoutParams(layoutParams)) return false;
        if (!(layoutParams instanceof LayoutParams)) return false;
        return true;
    }

    public void collapseActionView() {
        Object object = this.mExpandedMenuPresenter;
        object = object == null ? null : ((ExpandedActionViewMenuPresenter)object).mCurrentExpandedItem;
        if (object == null) return;
        ((MenuItemImpl)object).collapseActionView();
    }

    public void dismissPopupMenus() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) return;
        actionMenuView.dismissPopupMenus();
    }

    void ensureCollapseButtonView() {
        if (this.mCollapseButtonView != null) return;
        Object object = new AppCompatImageButton(this.getContext(), null, R.attr.toolbarNavigationButtonStyle);
        this.mCollapseButtonView = object;
        object.setImageDrawable(this.mCollapseIcon);
        this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
        object = this.generateDefaultLayoutParams();
        ((LayoutParams)object).gravity = 8388611 | this.mButtonGravity & 112;
        ((LayoutParams)object).mViewType = 2;
        this.mCollapseButtonView.setLayoutParams((ViewGroup.LayoutParams)object);
        this.mCollapseButtonView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Toolbar.this.collapseActionView();
            }
        });
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams)layoutParams);
        }
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) return new LayoutParams(layoutParams);
        return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
    }

    public CharSequence getCollapseContentDescription() {
        Object object = this.mCollapseButtonView;
        if (object == null) return null;
        return object.getContentDescription();
    }

    public Drawable getCollapseIcon() {
        ImageButton imageButton = this.mCollapseButtonView;
        if (imageButton == null) return null;
        return imageButton.getDrawable();
    }

    public int getContentInsetEnd() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper == null) return 0;
        return rtlSpacingHelper.getEnd();
    }

    public int getContentInsetEndWithActions() {
        int n = this.mContentInsetEndWithActions;
        if (n == Integer.MIN_VALUE) return this.getContentInsetEnd();
        return n;
    }

    public int getContentInsetLeft() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper == null) return 0;
        return rtlSpacingHelper.getLeft();
    }

    public int getContentInsetRight() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper == null) return 0;
        return rtlSpacingHelper.getRight();
    }

    public int getContentInsetStart() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        if (rtlSpacingHelper == null) return 0;
        return rtlSpacingHelper.getStart();
    }

    public int getContentInsetStartWithNavigation() {
        int n = this.mContentInsetStartWithNavigation;
        if (n == Integer.MIN_VALUE) return this.getContentInsetStart();
        return n;
    }

    public int getCurrentContentInsetEnd() {
        Object object = this.mMenuView;
        int n = object != null && (object = ((ActionMenuView)object).peekMenu()) != null && ((MenuBuilder)object).hasVisibleItems() ? 1 : 0;
        if (n == 0) return this.getContentInsetEnd();
        return Math.max(this.getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
    }

    public int getCurrentContentInsetLeft() {
        if (ViewCompat.getLayoutDirection((View)this) != 1) return this.getCurrentContentInsetStart();
        return this.getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetRight() {
        if (ViewCompat.getLayoutDirection((View)this) != 1) return this.getCurrentContentInsetEnd();
        return this.getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetStart() {
        if (this.getNavigationIcon() == null) return this.getContentInsetStart();
        return Math.max(this.getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
    }

    public Drawable getLogo() {
        ImageView imageView = this.mLogoView;
        if (imageView == null) return null;
        return imageView.getDrawable();
    }

    public CharSequence getLogoDescription() {
        Object object = this.mLogoView;
        if (object == null) return null;
        return object.getContentDescription();
    }

    public Menu getMenu() {
        this.ensureMenu();
        return this.mMenuView.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        Object object = this.mNavButtonView;
        if (object == null) return null;
        return object.getContentDescription();
    }

    public Drawable getNavigationIcon() {
        ImageButton imageButton = this.mNavButtonView;
        if (imageButton == null) return null;
        return imageButton.getDrawable();
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    public Drawable getOverflowIcon() {
        this.ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    final TextView getSubtitleTextView() {
        return this.mSubtitleTextView;
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    final TextView getTitleTextView() {
        return this.mTitleTextView;
    }

    public DecorToolbar getWrapper() {
        if (this.mWrapper != null) return this.mWrapper;
        this.mWrapper = new ToolbarWidgetWrapper(this, true);
        return this.mWrapper;
    }

    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter == null) return false;
        if (expandedActionViewMenuPresenter.mCurrentExpandedItem == null) return false;
        return true;
    }

    public boolean hideOverflowMenu() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) return false;
        if (!actionMenuView.hideOverflowMenu()) return false;
        return true;
    }

    public void inflateMenu(int n) {
        this.getMenuInflater().inflate(n, this.getMenu());
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) return false;
        if (!actionMenuView.isOverflowMenuShowPending()) return false;
        return true;
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) return false;
        if (!actionMenuView.isOverflowMenuShowing()) return false;
        return true;
    }

    public boolean isTitleTruncated() {
        TextView textView = this.mTitleTextView;
        if (textView == null) {
            return false;
        }
        if ((textView = textView.getLayout()) == null) {
            return false;
        }
        int n = textView.getLineCount();
        int n2 = 0;
        while (n2 < n) {
            if (textView.getEllipsisCount(n2) > 0) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean bl = super.onHoverEvent(motionEvent);
            if (n == 9 && !bl) {
                this.mEatingHover = true;
            }
        }
        if (n != 10) {
            if (n != 3) return true;
        }
        this.mEatingHover = false;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    protected void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block43 : {
            block44 : {
                block41 : {
                    block42 : {
                        var6_6 = ViewCompat.getLayoutDirection((View)this) == 1 ? 1 : 0;
                        var7_7 = this.getWidth();
                        var8_8 = this.getHeight();
                        var4_4 = this.getPaddingLeft();
                        var9_9 = this.getPaddingRight();
                        var10_10 = this.getPaddingTop();
                        var11_11 = this.getPaddingBottom();
                        var12_12 = var7_7 - var9_9;
                        var13_13 = this.mTempMargins;
                        var13_13[1] = 0;
                        var13_13[0] = 0;
                        var2_2 = ViewCompat.getMinimumHeight((View)this);
                        var5_5 = var2_2 >= 0 ? Math.min(var2_2, var5_5 - var3_3) : 0;
                        if (!this.shouldLayout((View)this.mNavButtonView)) break block41;
                        if (var6_6 == 0) break block42;
                        var14_14 = this.layoutChildRight((View)this.mNavButtonView, var12_12, var13_13, var5_5);
                        var15_15 = var4_4;
                        break block43;
                    }
                    var15_15 = this.layoutChildLeft((View)this.mNavButtonView, var4_4, var13_13, var5_5);
                    break block44;
                }
                var15_15 = var4_4;
            }
            var14_14 = var12_12;
        }
        var3_3 = var15_15;
        var2_2 = var14_14;
        if (this.shouldLayout((View)this.mCollapseButtonView)) {
            if (var6_6 != 0) {
                var2_2 = this.layoutChildRight((View)this.mCollapseButtonView, var14_14, var13_13, var5_5);
                var3_3 = var15_15;
            } else {
                var3_3 = this.layoutChildLeft((View)this.mCollapseButtonView, var15_15, var13_13, var5_5);
                var2_2 = var14_14;
            }
        }
        var14_14 = var3_3;
        var15_15 = var2_2;
        if (this.shouldLayout((View)this.mMenuView)) {
            if (var6_6 != 0) {
                var14_14 = this.layoutChildLeft((View)this.mMenuView, var3_3, var13_13, var5_5);
                var15_15 = var2_2;
            } else {
                var15_15 = this.layoutChildRight((View)this.mMenuView, var2_2, var13_13, var5_5);
                var14_14 = var3_3;
            }
        }
        var3_3 = this.getCurrentContentInsetLeft();
        var2_2 = this.getCurrentContentInsetRight();
        var13_13[0] = Math.max(0, var3_3 - var14_14);
        var13_13[1] = Math.max(0, var2_2 - (var12_12 - var15_15));
        var3_3 = Math.max(var14_14, var3_3);
        var15_15 = Math.min(var15_15, var12_12 - var2_2);
        var2_2 = var3_3;
        var14_14 = var15_15;
        if (this.shouldLayout(this.mExpandedActionView)) {
            if (var6_6 != 0) {
                var14_14 = this.layoutChildRight(this.mExpandedActionView, var15_15, var13_13, var5_5);
                var2_2 = var3_3;
            } else {
                var2_2 = this.layoutChildLeft(this.mExpandedActionView, var3_3, var13_13, var5_5);
                var14_14 = var15_15;
            }
        }
        var15_15 = var2_2;
        var3_3 = var14_14;
        if (this.shouldLayout((View)this.mLogoView)) {
            if (var6_6 != 0) {
                var3_3 = this.layoutChildRight((View)this.mLogoView, var14_14, var13_13, var5_5);
                var15_15 = var2_2;
            } else {
                var15_15 = this.layoutChildLeft((View)this.mLogoView, var2_2, var13_13, var5_5);
                var3_3 = var14_14;
            }
        }
        var1_1 = this.shouldLayout((View)this.mTitleTextView);
        var16_16 = this.shouldLayout((View)this.mSubtitleTextView);
        if (var1_1) {
            var17_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
            var2_2 = var17_17.topMargin + this.mTitleTextView.getMeasuredHeight() + var17_17.bottomMargin + 0;
        } else {
            var2_2 = 0;
        }
        if (var16_16) {
            var17_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
            var2_2 += var17_17.topMargin + this.mSubtitleTextView.getMeasuredHeight() + var17_17.bottomMargin;
        }
        if (!var1_1 && !var16_16) ** GOTO lbl131
        var17_17 = var1_1 != false ? this.mTitleTextView : this.mSubtitleTextView;
        if (var16_16) {
            var18_19 = this.mSubtitleTextView;
        } else {
            var18_20 = this.mTitleTextView;
        }
        var17_17 = (LayoutParams)var17_17.getLayoutParams();
        var18_18 = (LayoutParams)var18_21.getLayoutParams();
        var14_14 = var1_1 != false && this.mTitleTextView.getMeasuredWidth() > 0 || var16_16 != false && this.mSubtitleTextView.getMeasuredWidth() > 0 ? 1 : 0;
        var12_12 = this.mGravity & 112;
        if (var12_12 != 48) {
            if (var12_12 != 80) {
                var12_12 = (var8_8 - var10_10 - var11_11 - var2_2) / 2;
                if (var12_12 < var17_17.topMargin + this.mTitleMarginTop) {
                    var2_2 = var17_17.topMargin + this.mTitleMarginTop;
                } else {
                    var11_11 = var8_8 - var11_11 - var2_2 - var12_12 - var10_10;
                    var2_2 = var12_12;
                    if (var11_11 < var17_17.bottomMargin + this.mTitleMarginBottom) {
                        var2_2 = Math.max(0, var12_12 - (var18_18.bottomMargin + this.mTitleMarginBottom - var11_11));
                    }
                }
                var2_2 = var10_10 + var2_2;
            } else {
                var2_2 = var8_8 - var11_11 - var18_18.bottomMargin - this.mTitleMarginBottom - var2_2;
            }
        } else {
            var2_2 = this.getPaddingTop() + var17_17.topMargin + this.mTitleMarginTop;
        }
        if (var6_6 != 0) {
            var6_6 = var14_14 != 0 ? this.mTitleMarginStart : 0;
            var3_3 -= Math.max(0, var6_6 -= var13_13[1]);
            var13_13[1] = Math.max(0, -var6_6);
            if (var1_1) {
                var17_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                var12_12 = var3_3 - this.mTitleTextView.getMeasuredWidth();
                var6_6 = this.mTitleTextView.getMeasuredHeight() + var2_2;
                this.mTitleTextView.layout(var12_12, var2_2, var3_3, var6_6);
                var2_2 = var12_12 - this.mTitleMarginEnd;
                var12_12 = var6_6 + var17_17.bottomMargin;
            } else {
                var6_6 = var3_3;
                var12_12 = var2_2;
                var2_2 = var6_6;
            }
            if (var16_16) {
                var17_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                var6_6 = var12_12 + var17_17.topMargin;
                var10_10 = this.mSubtitleTextView.getMeasuredWidth();
                var12_12 = this.mSubtitleTextView.getMeasuredHeight();
                this.mSubtitleTextView.layout(var3_3 - var10_10, var6_6, var3_3, var12_12 + var6_6);
                var6_6 = var3_3 - this.mTitleMarginEnd;
                var12_12 = var17_17.bottomMargin;
            } else {
                var6_6 = var3_3;
            }
            if (var14_14 != 0) {
                var3_3 = Math.min(var2_2, var6_6);
            }
lbl131: // 4 sources:
            var2_2 = var15_15;
            var15_15 = var3_3;
        } else {
            var6_6 = var14_14 != 0 ? this.mTitleMarginStart : 0;
            var15_15 += Math.max(0, var6_6 -= var13_13[0]);
            var13_13[0] = Math.max(0, -var6_6);
            if (var1_1) {
                var17_17 = (LayoutParams)this.mTitleTextView.getLayoutParams();
                var6_6 = this.mTitleTextView.getMeasuredWidth() + var15_15;
                var12_12 = this.mTitleTextView.getMeasuredHeight() + var2_2;
                this.mTitleTextView.layout(var15_15, var2_2, var6_6, var12_12);
                var6_6 += this.mTitleMarginEnd;
                var2_2 = var12_12 + var17_17.bottomMargin;
            } else {
                var6_6 = var15_15;
            }
            if (var16_16) {
                var17_17 = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                var10_10 = var2_2 + var17_17.topMargin;
                var2_2 = this.mSubtitleTextView.getMeasuredWidth() + var15_15;
                var12_12 = this.mSubtitleTextView.getMeasuredHeight();
                this.mSubtitleTextView.layout(var15_15, var10_10, var2_2, var12_12 + var10_10);
                var12_12 = var2_2 + this.mTitleMarginEnd;
                var2_2 = var17_17.bottomMargin;
            } else {
                var12_12 = var15_15;
            }
            var2_2 = var15_15;
            var15_15 = var3_3;
            if (var14_14 != 0) {
                var2_2 = Math.max(var6_6, var12_12);
                var15_15 = var3_3;
            }
        }
        var14_14 = var4_4;
        var4_4 = 0;
        this.addCustomViewsWithGravity(this.mTempViews, 3);
        var6_6 = this.mTempViews.size();
        for (var3_3 = 0; var3_3 < var6_6; ++var3_3) {
            var2_2 = this.layoutChildLeft(this.mTempViews.get(var3_3), var2_2, var13_13, var5_5);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 5);
        var6_6 = this.mTempViews.size();
        for (var3_3 = 0; var3_3 < var6_6; ++var3_3) {
            var15_15 = this.layoutChildRight(this.mTempViews.get(var3_3), var15_15, var13_13, var5_5);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 1);
        var6_6 = this.getViewListMeasuredWidth(this.mTempViews, var13_13);
        var3_3 = var14_14 + (var7_7 - var14_14 - var9_9) / 2 - var6_6 / 2;
        var14_14 = var6_6 + var3_3;
        if (var3_3 >= var2_2) {
            var2_2 = var14_14 > var15_15 ? var3_3 - (var14_14 - var15_15) : var3_3;
        }
        var15_15 = this.mTempViews.size();
        var3_3 = var4_4;
        do {
            if (var3_3 >= var15_15) {
                this.mTempViews.clear();
                return;
            }
            var2_2 = this.layoutChildLeft(this.mTempViews.get(var3_3), var2_2, var13_13, var5_5);
            ++var3_3;
        } while (true);
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int[] arrn = this.mTempMargins;
        boolean bl = ViewUtils.isLayoutRtl((View)this);
        int n8 = 0;
        if (bl) {
            n7 = 1;
            n4 = 0;
        } else {
            n7 = 0;
            n4 = 1;
        }
        if (this.shouldLayout((View)this.mNavButtonView)) {
            this.measureChildConstrained((View)this.mNavButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n6 = this.mNavButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mNavButtonView);
            n5 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mNavButtonView));
            n3 = View.combineMeasuredStates((int)0, (int)this.mNavButtonView.getMeasuredState());
        } else {
            n6 = 0;
            n5 = 0;
            n3 = 0;
        }
        int n9 = n6;
        int n10 = n5;
        n6 = n3;
        if (this.shouldLayout((View)this.mCollapseButtonView)) {
            this.measureChildConstrained((View)this.mCollapseButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n9 = this.mCollapseButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mCollapseButtonView);
            n10 = Math.max(n5, this.mCollapseButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mCollapseButtonView));
            n6 = View.combineMeasuredStates((int)n3, (int)this.mCollapseButtonView.getMeasuredState());
        }
        n3 = this.getCurrentContentInsetStart();
        n5 = 0 + Math.max(n3, n9);
        arrn[n7] = Math.max(0, n3 - n9);
        if (this.shouldLayout((View)this.mMenuView)) {
            this.measureChildConstrained((View)this.mMenuView, n, n5, n2, 0, this.mMaxButtonHeight);
            n3 = this.mMenuView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mMenuView);
            n10 = Math.max(n10, this.mMenuView.getMeasuredHeight() + this.getVerticalMargins((View)this.mMenuView));
            n6 = View.combineMeasuredStates((int)n6, (int)this.mMenuView.getMeasuredState());
        } else {
            n3 = 0;
        }
        n7 = this.getCurrentContentInsetEnd();
        n9 = n5 + Math.max(n7, n3);
        arrn[n4] = Math.max(0, n7 - n3);
        n7 = n10;
        n3 = n6;
        n5 = n9;
        if (this.shouldLayout(this.mExpandedActionView)) {
            n5 = n9 + this.measureChildCollapseMargins(this.mExpandedActionView, n, n9, n2, 0, arrn);
            n7 = Math.max(n10, this.mExpandedActionView.getMeasuredHeight() + this.getVerticalMargins(this.mExpandedActionView));
            n3 = View.combineMeasuredStates((int)n6, (int)this.mExpandedActionView.getMeasuredState());
        }
        n10 = n7;
        n6 = n3;
        n4 = n5;
        if (this.shouldLayout((View)this.mLogoView)) {
            n4 = n5 + this.measureChildCollapseMargins((View)this.mLogoView, n, n5, n2, 0, arrn);
            n10 = Math.max(n7, this.mLogoView.getMeasuredHeight() + this.getVerticalMargins((View)this.mLogoView));
            n6 = View.combineMeasuredStates((int)n3, (int)this.mLogoView.getMeasuredState());
        }
        int n11 = this.getChildCount();
        n3 = 0;
        n5 = n10;
        for (n10 = n3; n10 < n11; ++n10) {
            View view = this.getChildAt(n10);
            n9 = n5;
            n7 = n6;
            n3 = n4;
            if (((LayoutParams)view.getLayoutParams()).mViewType == 0) {
                if (!this.shouldLayout(view)) {
                    n9 = n5;
                    n7 = n6;
                    n3 = n4;
                } else {
                    n3 = n4 + this.measureChildCollapseMargins(view, n, n4, n2, 0, arrn);
                    n9 = Math.max(n5, view.getMeasuredHeight() + this.getVerticalMargins(view));
                    n7 = View.combineMeasuredStates((int)n6, (int)view.getMeasuredState());
                }
            }
            n5 = n9;
            n6 = n7;
            n4 = n3;
        }
        n7 = this.mTitleMarginTop + this.mTitleMarginBottom;
        n9 = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (this.shouldLayout((View)this.mTitleTextView)) {
            this.measureChildCollapseMargins((View)this.mTitleTextView, n, n4 + n9, n2, n7, arrn);
            n11 = this.mTitleTextView.getMeasuredWidth();
            n10 = this.getHorizontalMargins((View)this.mTitleTextView);
            int n12 = this.mTitleTextView.getMeasuredHeight();
            n3 = this.getVerticalMargins((View)this.mTitleTextView);
            n6 = View.combineMeasuredStates((int)n6, (int)this.mTitleTextView.getMeasuredState());
            n3 = n12 + n3;
            n10 = n11 + n10;
        } else {
            n10 = 0;
            n3 = 0;
        }
        if (this.shouldLayout((View)this.mSubtitleTextView)) {
            n10 = Math.max(n10, this.measureChildCollapseMargins((View)this.mSubtitleTextView, n, n4 + n9, n2, n3 + n7, arrn));
            n3 += this.mSubtitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mSubtitleTextView);
            n6 = View.combineMeasuredStates((int)n6, (int)this.mSubtitleTextView.getMeasuredState());
        }
        n7 = Math.max(n5, n3);
        n11 = this.getPaddingLeft();
        n9 = this.getPaddingRight();
        n5 = this.getPaddingTop();
        n3 = this.getPaddingBottom();
        n10 = View.resolveSizeAndState((int)Math.max(n4 + n10 + (n11 + n9), this.getSuggestedMinimumWidth()), (int)n, (int)(-16777216 & n6));
        n = View.resolveSizeAndState((int)Math.max(n7 + (n5 + n3), this.getSuggestedMinimumHeight()), (int)n2, (int)(n6 << 16));
        if (this.shouldCollapse()) {
            n = n8;
        }
        this.setMeasuredDimension(n10, n);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        }
        SavedState savedState = (SavedState)object;
        super.onRestoreInstanceState(savedState.getSuperState());
        object = this.mMenuView;
        object = object != null ? ((ActionMenuView)object).peekMenu() : null;
        if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && object != null && (object = object.findItem(savedState.expandedMenuItemId)) != null) {
            object.expandActionView();
        }
        if (!savedState.isOverflowOpen) return;
        this.postShowOverflowMenu();
    }

    public void onRtlPropertiesChanged(int n) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(n);
        }
        this.ensureContentInsets();
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        rtlSpacingHelper.setDirection(bl);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = this.isOverflowMenuShowing();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean bl = super.onTouchEvent(motionEvent);
            if (n == 0 && !bl) {
                this.mEatingTouch = true;
            }
        }
        if (n != 1) {
            if (n != 3) return true;
        }
        this.mEatingTouch = false;
        return true;
    }

    void removeChildrenForExpandedActionView() {
        int n = this.getChildCount() - 1;
        while (n >= 0) {
            View view = this.getChildAt(n);
            if (((LayoutParams)view.getLayoutParams()).mViewType != 2 && view != this.mMenuView) {
                this.removeViewAt(n);
                this.mHiddenViews.add(view);
            }
            --n;
        }
    }

    public void setCollapseContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getContext().getText(n) : null;
        this.setCollapseContentDescription(charSequence);
    }

    public void setCollapseContentDescription(CharSequence charSequence) {
        ImageButton imageButton;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureCollapseButtonView();
        }
        if ((imageButton = this.mCollapseButtonView) == null) return;
        imageButton.setContentDescription(charSequence);
    }

    public void setCollapseIcon(int n) {
        this.setCollapseIcon(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setCollapseIcon(Drawable drawable2) {
        if (drawable2 != null) {
            this.ensureCollapseButtonView();
            this.mCollapseButtonView.setImageDrawable(drawable2);
            return;
        }
        drawable2 = this.mCollapseButtonView;
        if (drawable2 == null) return;
        drawable2.setImageDrawable(this.mCollapseIcon);
    }

    public void setCollapsible(boolean bl) {
        this.mCollapsible = bl;
        this.requestLayout();
    }

    public void setContentInsetEndWithActions(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 == this.mContentInsetEndWithActions) return;
        this.mContentInsetEndWithActions = n2;
        if (this.getNavigationIcon() == null) return;
        this.requestLayout();
    }

    public void setContentInsetStartWithNavigation(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 == this.mContentInsetStartWithNavigation) return;
        this.mContentInsetStartWithNavigation = n2;
        if (this.getNavigationIcon() == null) return;
        this.requestLayout();
    }

    public void setContentInsetsAbsolute(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n, n2);
    }

    public void setContentInsetsRelative(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setRelative(n, n2);
    }

    public void setLogo(int n) {
        this.setLogo(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setLogo(Drawable drawable2) {
        ImageView imageView;
        if (drawable2 != null) {
            this.ensureLogoView();
            if (!this.isChildOrHidden((View)this.mLogoView)) {
                this.addSystemView((View)this.mLogoView, true);
            }
        } else {
            imageView = this.mLogoView;
            if (imageView != null && this.isChildOrHidden((View)imageView)) {
                this.removeView((View)this.mLogoView);
                this.mHiddenViews.remove((Object)this.mLogoView);
            }
        }
        if ((imageView = this.mLogoView) == null) return;
        imageView.setImageDrawable(drawable2);
    }

    public void setLogoDescription(int n) {
        this.setLogoDescription(this.getContext().getText(n));
    }

    public void setLogoDescription(CharSequence charSequence) {
        ImageView imageView;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureLogoView();
        }
        if ((imageView = this.mLogoView) == null) return;
        imageView.setContentDescription(charSequence);
    }

    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        if (menuBuilder == null && this.mMenuView == null) {
            return;
        }
        this.ensureMenuView();
        MenuBuilder menuBuilder2 = this.mMenuView.peekMenu();
        if (menuBuilder2 == menuBuilder) {
            return;
        }
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(this.mOuterActionMenuPresenter);
            menuBuilder2.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter.setExpandedActionViewsExclusive(true);
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            actionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            actionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(actionMenuPresenter);
        this.mOuterActionMenuPresenter = actionMenuPresenter;
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) return;
        actionMenuView.setMenuCallbacks(callback, callback2);
    }

    public void setNavigationContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getContext().getText(n) : null;
        this.setNavigationContentDescription(charSequence);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        ImageButton imageButton;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.ensureNavButtonView();
        }
        if ((imageButton = this.mNavButtonView) == null) return;
        imageButton.setContentDescription(charSequence);
    }

    public void setNavigationIcon(int n) {
        this.setNavigationIcon(AppCompatResources.getDrawable(this.getContext(), n));
    }

    public void setNavigationIcon(Drawable drawable2) {
        ImageButton imageButton;
        if (drawable2 != null) {
            this.ensureNavButtonView();
            if (!this.isChildOrHidden((View)this.mNavButtonView)) {
                this.addSystemView((View)this.mNavButtonView, true);
            }
        } else {
            imageButton = this.mNavButtonView;
            if (imageButton != null && this.isChildOrHidden((View)imageButton)) {
                this.removeView((View)this.mNavButtonView);
                this.mHiddenViews.remove((Object)this.mNavButtonView);
            }
        }
        if ((imageButton = this.mNavButtonView) == null) return;
        imageButton.setImageDrawable(drawable2);
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(Drawable drawable2) {
        this.ensureMenu();
        this.mMenuView.setOverflowIcon(drawable2);
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

    public void setSubtitle(int n) {
        this.setSubtitle(this.getContext().getText(n));
    }

    public void setSubtitle(CharSequence charSequence) {
        TextView textView;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mSubtitleTextView == null) {
                Context context = this.getContext();
                this.mSubtitleTextView = textView = new AppCompatTextView(context);
                textView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int n = this.mSubtitleTextAppearance;
                if (n != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, n);
                }
                if ((textView = this.mSubtitleTextColor) != null) {
                    this.mSubtitleTextView.setTextColor((ColorStateList)textView);
                }
            }
            if (!this.isChildOrHidden((View)this.mSubtitleTextView)) {
                this.addSystemView((View)this.mSubtitleTextView, true);
            }
        } else {
            textView = this.mSubtitleTextView;
            if (textView != null && this.isChildOrHidden((View)textView)) {
                this.removeView((View)this.mSubtitleTextView);
                this.mHiddenViews.remove((Object)this.mSubtitleTextView);
            }
        }
        if ((textView = this.mSubtitleTextView) != null) {
            textView.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setSubtitleTextAppearance(Context context, int n) {
        this.mSubtitleTextAppearance = n;
        TextView textView = this.mSubtitleTextView;
        if (textView == null) return;
        textView.setTextAppearance(context, n);
    }

    public void setSubtitleTextColor(int n) {
        this.setSubtitleTextColor(ColorStateList.valueOf((int)n));
    }

    public void setSubtitleTextColor(ColorStateList colorStateList) {
        this.mSubtitleTextColor = colorStateList;
        TextView textView = this.mSubtitleTextView;
        if (textView == null) return;
        textView.setTextColor(colorStateList);
    }

    public void setTitle(int n) {
        this.setTitle(this.getContext().getText(n));
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.mTitleTextView == null) {
                textView = this.getContext();
                AppCompatTextView appCompatTextView = new AppCompatTextView((Context)textView);
                this.mTitleTextView = appCompatTextView;
                appCompatTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int n = this.mTitleTextAppearance;
                if (n != 0) {
                    this.mTitleTextView.setTextAppearance((Context)textView, n);
                }
                if ((textView = this.mTitleTextColor) != null) {
                    this.mTitleTextView.setTextColor((ColorStateList)textView);
                }
            }
            if (!this.isChildOrHidden((View)this.mTitleTextView)) {
                this.addSystemView((View)this.mTitleTextView, true);
            }
        } else {
            textView = this.mTitleTextView;
            if (textView != null && this.isChildOrHidden((View)textView)) {
                this.removeView((View)this.mTitleTextView);
                this.mHiddenViews.remove((Object)this.mTitleTextView);
            }
        }
        if ((textView = this.mTitleTextView) != null) {
            textView.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public void setTitleMargin(int n, int n2, int n3, int n4) {
        this.mTitleMarginStart = n;
        this.mTitleMarginTop = n2;
        this.mTitleMarginEnd = n3;
        this.mTitleMarginBottom = n4;
        this.requestLayout();
    }

    public void setTitleMarginBottom(int n) {
        this.mTitleMarginBottom = n;
        this.requestLayout();
    }

    public void setTitleMarginEnd(int n) {
        this.mTitleMarginEnd = n;
        this.requestLayout();
    }

    public void setTitleMarginStart(int n) {
        this.mTitleMarginStart = n;
        this.requestLayout();
    }

    public void setTitleMarginTop(int n) {
        this.mTitleMarginTop = n;
        this.requestLayout();
    }

    public void setTitleTextAppearance(Context context, int n) {
        this.mTitleTextAppearance = n;
        TextView textView = this.mTitleTextView;
        if (textView == null) return;
        textView.setTextAppearance(context, n);
    }

    public void setTitleTextColor(int n) {
        this.setTitleTextColor(ColorStateList.valueOf((int)n));
    }

    public void setTitleTextColor(ColorStateList colorStateList) {
        this.mTitleTextColor = colorStateList;
        TextView textView = this.mTitleTextView;
        if (textView == null) return;
        textView.setTextColor(colorStateList);
    }

    public boolean showOverflowMenu() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView == null) return false;
        if (!actionMenuView.showOverflowMenu()) return false;
        return true;
    }

    private class ExpandedActionViewMenuPresenter
    implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        ExpandedActionViewMenuPresenter() {
        }

        @Override
        public boolean collapseItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            object = Toolbar.this;
            object.removeView(((Toolbar)object).mExpandedActionView);
            object = Toolbar.this;
            object.removeView((View)((Toolbar)object).mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override
        public boolean expandItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            Toolbar.this.ensureCollapseButtonView();
            Object object2 = Toolbar.this.mCollapseButtonView.getParent();
            object = Toolbar.this;
            if (object2 != object) {
                if (object2 instanceof ViewGroup) {
                    ((ViewGroup)object2).removeView((View)((Toolbar)object).mCollapseButtonView);
                }
                object = Toolbar.this;
                object.addView((View)((Toolbar)object).mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = menuItemImpl.getActionView();
            this.mCurrentExpandedItem = menuItemImpl;
            object = Toolbar.this.mExpandedActionView.getParent();
            if (object != (object2 = Toolbar.this)) {
                if (object instanceof ViewGroup) {
                    ((ViewGroup)object).removeView(object2.mExpandedActionView);
                }
                object = Toolbar.this.generateDefaultLayoutParams();
                ((LayoutParams)object).gravity = 8388611 | Toolbar.this.mButtonGravity & 112;
                ((LayoutParams)object).mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams((ViewGroup.LayoutParams)object);
                object = Toolbar.this;
                object.addView(((Toolbar)object).mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (!(Toolbar.this.mExpandedActionView instanceof CollapsibleActionView)) return true;
            ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded();
            return true;
        }

        @Override
        public boolean flagActionItems() {
            return false;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void initForMenu(Context object, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            object = this.mMenu;
            if (object != null && (menuItemImpl = this.mCurrentExpandedItem) != null) {
                ((MenuBuilder)object).collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
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
            return false;
        }

        @Override
        public void setCallback(MenuPresenter.Callback callback) {
        }

        @Override
        public void updateMenuView(boolean bl) {
            boolean bl2;
            if (this.mCurrentExpandedItem == null) return;
            MenuBuilder menuBuilder = this.mMenu;
            boolean bl3 = bl2 = false;
            if (menuBuilder != null) {
                int n = menuBuilder.size();
                int n2 = 0;
                do {
                    bl3 = bl2;
                    if (n2 >= n) break;
                    if (this.mMenu.getItem(n2) == this.mCurrentExpandedItem) {
                        bl3 = true;
                        break;
                    }
                    ++n2;
                } while (true);
            }
            if (bl3) return;
            this.collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
        }
    }

    public static class LayoutParams
    extends ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType = 0;

        public LayoutParams(int n) {
            this(-2, -1, n);
        }

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.gravity = 8388627;
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2);
            this.gravity = n3;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super((ViewGroup.LayoutParams)marginLayoutParams);
            this.copyMarginsFromCompat(marginLayoutParams);
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = layoutParams.mViewType;
        }

        void copyMarginsFromCompat(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
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
        int expandedMenuItemId;
        boolean isOverflowOpen;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.expandedMenuItemId = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.isOverflowOpen = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt((int)this.isOverflowOpen);
        }

    }

}

