/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewPropertyAnimator
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.AbsListView
 *  android.widget.AbsListView$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.BaseAdapter
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.TooltipCompat;

public class ScrollingTabContainerView
extends HorizontalScrollView
implements AdapterView.OnItemSelectedListener {
    private static final int FADE_DURATION = 200;
    private static final String TAG = "ScrollingTabContainerView";
    private static final Interpolator sAlphaInterpolator = new DecelerateInterpolator();
    private boolean mAllowCollapse;
    private int mContentHeight;
    int mMaxTabWidth;
    private int mSelectedTabIndex;
    int mStackedTabMaxWidth;
    private TabClickListener mTabClickListener;
    LinearLayoutCompat mTabLayout;
    Runnable mTabSelector;
    private Spinner mTabSpinner;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected ViewPropertyAnimator mVisibilityAnim;

    public ScrollingTabContainerView(Context object) {
        super(object);
        this.setHorizontalScrollBarEnabled(false);
        object = ActionBarPolicy.get(object);
        this.setContentHeight(object.getTabContainerHeight());
        this.mStackedTabMaxWidth = object.getStackedTabMaxWidth();
        object = this.createTabLayout();
        this.mTabLayout = object;
        this.addView((View)object, new ViewGroup.LayoutParams(-2, -1));
    }

    private Spinner createSpinner() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(this.getContext(), null, R.attr.actionDropDownStyle);
        appCompatSpinner.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        appCompatSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);
        return appCompatSpinner;
    }

    private LinearLayoutCompat createTabLayout() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this.getContext(), null, R.attr.actionBarTabBarStyle);
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        linearLayoutCompat.setGravity(17);
        linearLayoutCompat.setLayoutParams((ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(-2, -1));
        return linearLayoutCompat;
    }

    private boolean isCollapsed() {
        Spinner spinner = this.mTabSpinner;
        if (spinner == null) return false;
        if (spinner.getParent() != this) return false;
        return true;
    }

    private void performCollapse() {
        Runnable runnable2;
        if (this.isCollapsed()) {
            return;
        }
        if (this.mTabSpinner == null) {
            this.mTabSpinner = this.createSpinner();
        }
        this.removeView((View)this.mTabLayout);
        this.addView((View)this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
        if (this.mTabSpinner.getAdapter() == null) {
            this.mTabSpinner.setAdapter((SpinnerAdapter)new TabAdapter());
        }
        if ((runnable2 = this.mTabSelector) != null) {
            this.removeCallbacks(runnable2);
            this.mTabSelector = null;
        }
        this.mTabSpinner.setSelection(this.mSelectedTabIndex);
    }

    private boolean performExpand() {
        if (!this.isCollapsed()) {
            return false;
        }
        this.removeView((View)this.mTabSpinner);
        this.addView((View)this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
        this.setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        return false;
    }

    public void addTab(ActionBar.Tab object, int n, boolean bl) {
        object = this.createTabView((ActionBar.Tab)object, false);
        this.mTabLayout.addView((View)object, n, (ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (bl) {
            ((TabView)((Object)object)).setSelected(true);
        }
        if (!this.mAllowCollapse) return;
        this.requestLayout();
    }

    public void addTab(ActionBar.Tab object, boolean bl) {
        object = this.createTabView((ActionBar.Tab)object, false);
        this.mTabLayout.addView((View)object, (ViewGroup.LayoutParams)new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (bl) {
            ((TabView)((Object)object)).setSelected(true);
        }
        if (!this.mAllowCollapse) return;
        this.requestLayout();
    }

    public void animateToTab(int n) {
        final View view = this.mTabLayout.getChildAt(n);
        Runnable runnable2 = this.mTabSelector;
        if (runnable2 != null) {
            this.removeCallbacks(runnable2);
        }
        this.mTabSelector = runnable2 = new Runnable(){

            @Override
            public void run() {
                int n = view.getLeft();
                int n2 = (ScrollingTabContainerView.this.getWidth() - view.getWidth()) / 2;
                ScrollingTabContainerView.this.smoothScrollTo(n - n2, 0);
                ScrollingTabContainerView.this.mTabSelector = null;
            }
        };
        this.post(runnable2);
    }

    public void animateToVisibility(int n) {
        ViewPropertyAnimator viewPropertyAnimator = this.mVisibilityAnim;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        if (n != 0) {
            viewPropertyAnimator = this.animate().alpha(0.0f);
            viewPropertyAnimator.setDuration(200L);
            viewPropertyAnimator.setInterpolator((TimeInterpolator)sAlphaInterpolator);
            viewPropertyAnimator.setListener((Animator.AnimatorListener)this.mVisAnimListener.withFinalVisibility(viewPropertyAnimator, n));
            viewPropertyAnimator.start();
            return;
        }
        if (this.getVisibility() != 0) {
            this.setAlpha(0.0f);
        }
        viewPropertyAnimator = this.animate().alpha(1.0f);
        viewPropertyAnimator.setDuration(200L);
        viewPropertyAnimator.setInterpolator((TimeInterpolator)sAlphaInterpolator);
        viewPropertyAnimator.setListener((Animator.AnimatorListener)this.mVisAnimListener.withFinalVisibility(viewPropertyAnimator, n));
        viewPropertyAnimator.start();
    }

    TabView createTabView(ActionBar.Tab object, boolean bl) {
        object = new TabView(this.getContext(), (ActionBar.Tab)object, bl);
        if (bl) {
            object.setBackgroundDrawable(null);
            object.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, this.mContentHeight));
            return object;
        }
        object.setFocusable(true);
        if (this.mTabClickListener == null) {
            this.mTabClickListener = new TabClickListener();
        }
        object.setOnClickListener((View.OnClickListener)this.mTabClickListener);
        return object;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable2 = this.mTabSelector;
        if (runnable2 == null) return;
        this.post(runnable2);
    }

    protected void onConfigurationChanged(Configuration object) {
        super.onConfigurationChanged((Configuration)object);
        object = ActionBarPolicy.get(this.getContext());
        this.setContentHeight(((ActionBarPolicy)object).getTabContainerHeight());
        this.mStackedTabMaxWidth = ((ActionBarPolicy)object).getStackedTabMaxWidth();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable2 = this.mTabSelector;
        if (runnable2 == null) return;
        this.removeCallbacks(runnable2);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
        ((TabView)view).getTab().select();
    }

    public void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode((int)n);
        n2 = 1;
        boolean bl = n3 == 1073741824;
        this.setFillViewport(bl);
        int n4 = this.mTabLayout.getChildCount();
        if (n4 > 1 && (n3 == 1073741824 || n3 == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = n4 > 2 ? (int)((float)View.MeasureSpec.getSize((int)n) * 0.4f) : View.MeasureSpec.getSize((int)n) / 2;
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, this.mStackedTabMaxWidth);
        } else {
            this.mMaxTabWidth = -1;
        }
        n3 = View.MeasureSpec.makeMeasureSpec((int)this.mContentHeight, (int)1073741824);
        if (bl || !this.mAllowCollapse) {
            n2 = 0;
        }
        if (n2 != 0) {
            this.mTabLayout.measure(0, n3);
            if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize((int)n)) {
                this.performCollapse();
            } else {
                this.performExpand();
            }
        } else {
            this.performExpand();
        }
        n2 = this.getMeasuredWidth();
        super.onMeasure(n, n3);
        n = this.getMeasuredWidth();
        if (!bl) return;
        if (n2 == n) return;
        this.setTabSelected(this.mSelectedTabIndex);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void removeAllTabs() {
        this.mTabLayout.removeAllViews();
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (!this.mAllowCollapse) return;
        this.requestLayout();
    }

    public void removeTabAt(int n) {
        this.mTabLayout.removeViewAt(n);
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (!this.mAllowCollapse) return;
        this.requestLayout();
    }

    public void setAllowCollapse(boolean bl) {
        this.mAllowCollapse = bl;
    }

    public void setContentHeight(int n) {
        this.mContentHeight = n;
        this.requestLayout();
    }

    public void setTabSelected(int n) {
        this.mSelectedTabIndex = n;
        int n2 = this.mTabLayout.getChildCount();
        int n3 = 0;
        do {
            Spinner spinner;
            if (n3 >= n2) {
                spinner = this.mTabSpinner;
                if (spinner == null) return;
                if (n < 0) return;
                spinner.setSelection(n);
                return;
            }
            spinner = this.mTabLayout.getChildAt(n3);
            boolean bl = n3 == n;
            spinner.setSelected(bl);
            if (bl) {
                this.animateToTab(n);
            }
            ++n3;
        } while (true);
    }

    public void updateTab(int n) {
        ((TabView)this.mTabLayout.getChildAt(n)).update();
        Spinner spinner = this.mTabSpinner;
        if (spinner != null) {
            ((TabAdapter)spinner.getAdapter()).notifyDataSetChanged();
        }
        if (!this.mAllowCollapse) return;
        this.requestLayout();
    }

    private class TabAdapter
    extends BaseAdapter {
        TabAdapter() {
        }

        public int getCount() {
            return ScrollingTabContainerView.this.mTabLayout.getChildCount();
        }

        public Object getItem(int n) {
            return ((TabView)ScrollingTabContainerView.this.mTabLayout.getChildAt(n)).getTab();
        }

        public long getItemId(int n) {
            return n;
        }

        public View getView(int n, View object, ViewGroup viewGroup) {
            if (object == null) {
                return ScrollingTabContainerView.this.createTabView((ActionBar.Tab)this.getItem(n), true);
            }
            ((TabView)((Object)object)).bindTab((ActionBar.Tab)this.getItem(n));
            return object;
        }
    }

    private class TabClickListener
    implements View.OnClickListener {
        TabClickListener() {
        }

        public void onClick(View view) {
            ((TabView)view).getTab().select();
            int n = ScrollingTabContainerView.this.mTabLayout.getChildCount();
            int n2 = 0;
            while (n2 < n) {
                View view2 = ScrollingTabContainerView.this.mTabLayout.getChildAt(n2);
                boolean bl = view2 == view;
                view2.setSelected(bl);
                ++n2;
            }
        }
    }

    private class TabView
    extends LinearLayout {
        private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.app.ActionBar$Tab";
        private final int[] BG_ATTRS;
        private View mCustomView;
        private ImageView mIconView;
        private ActionBar.Tab mTab;
        private TextView mTextView;

        public TabView(Context context, ActionBar.Tab tab, boolean bl) {
            super(context, null, R.attr.actionBarTabStyle);
            ScrollingTabContainerView.this = new int[]{16842964};
            this.BG_ATTRS = ScrollingTabContainerView.this;
            this.mTab = tab;
            ScrollingTabContainerView.this = TintTypedArray.obtainStyledAttributes(context, null, (int[])ScrollingTabContainerView.this, R.attr.actionBarTabStyle, 0);
            if (((TintTypedArray)ScrollingTabContainerView.this).hasValue(0)) {
                this.setBackgroundDrawable(((TintTypedArray)ScrollingTabContainerView.this).getDrawable(0));
            }
            ((TintTypedArray)ScrollingTabContainerView.this).recycle();
            if (bl) {
                this.setGravity(8388627);
            }
            this.update();
        }

        public void bindTab(ActionBar.Tab tab) {
            this.mTab = tab;
            this.update();
        }

        public ActionBar.Tab getTab() {
            return this.mTab;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ACCESSIBILITY_CLASS_NAME);
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName((CharSequence)ACCESSIBILITY_CLASS_NAME);
        }

        public void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            if (ScrollingTabContainerView.this.mMaxTabWidth <= 0) return;
            if (this.getMeasuredWidth() <= ScrollingTabContainerView.this.mMaxTabWidth) return;
            super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)ScrollingTabContainerView.this.mMaxTabWidth, (int)1073741824), n2);
        }

        public void setSelected(boolean bl) {
            boolean bl2 = this.isSelected() != bl;
            super.setSelected(bl);
            if (!bl2) return;
            if (!bl) return;
            this.sendAccessibilityEvent(4);
        }

        public void update() {
            ImageView imageView;
            ActionBar.Tab tab = this.mTab;
            Object object = tab.getCustomView();
            Object object2 = null;
            if (object != null) {
                object2 = object.getParent();
                if (object2 != this) {
                    if (object2 != null) {
                        ((ViewGroup)object2).removeView(object);
                    }
                    this.addView(object);
                }
                this.mCustomView = object;
                object2 = this.mTextView;
                if (object2 != null) {
                    object2.setVisibility(8);
                }
                if ((object2 = this.mIconView) == null) return;
                object2.setVisibility(8);
                this.mIconView.setImageDrawable(null);
                return;
            }
            object = this.mCustomView;
            if (object != null) {
                this.removeView(object);
                this.mCustomView = null;
            }
            Object object3 = tab.getIcon();
            object = tab.getText();
            if (object3 != null) {
                if (this.mIconView == null) {
                    AppCompatImageView appCompatImageView = new AppCompatImageView(this.getContext());
                    imageView = new LinearLayout.LayoutParams(-2, -2);
                    imageView.gravity = 16;
                    appCompatImageView.setLayoutParams((ViewGroup.LayoutParams)imageView);
                    this.addView((View)appCompatImageView, 0);
                    this.mIconView = appCompatImageView;
                }
                this.mIconView.setImageDrawable(object3);
                this.mIconView.setVisibility(0);
            } else {
                imageView = this.mIconView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
            }
            boolean bl = TextUtils.isEmpty((CharSequence)object) ^ true;
            if (bl) {
                if (this.mTextView == null) {
                    object3 = new AppCompatTextView(this.getContext(), null, R.attr.actionBarTabTextStyle);
                    object3.setEllipsize(TextUtils.TruncateAt.END);
                    imageView = new LinearLayout.LayoutParams(-2, -2);
                    imageView.gravity = 16;
                    object3.setLayoutParams((ViewGroup.LayoutParams)imageView);
                    this.addView((View)object3);
                    this.mTextView = object3;
                }
                this.mTextView.setText((CharSequence)object);
                this.mTextView.setVisibility(0);
            } else {
                object = this.mTextView;
                if (object != null) {
                    object.setVisibility(8);
                    this.mTextView.setText(null);
                }
            }
            object = this.mIconView;
            if (object != null) {
                object.setContentDescription(tab.getContentDescription());
            }
            if (!bl) {
                object2 = tab.getContentDescription();
            }
            TooltipCompat.setTooltipText((View)this, (CharSequence)object2);
        }
    }

    protected class VisibilityAnimListener
    extends AnimatorListenerAdapter {
        private boolean mCanceled = false;
        private int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        public void onAnimationEnd(Animator animator2) {
            if (this.mCanceled) {
                return;
            }
            ScrollingTabContainerView.this.mVisibilityAnim = null;
            ScrollingTabContainerView.this.setVisibility(this.mFinalVisibility);
        }

        public void onAnimationStart(Animator animator2) {
            ScrollingTabContainerView.this.setVisibility(0);
            this.mCanceled = false;
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimator viewPropertyAnimator, int n) {
            this.mFinalVisibility = n;
            ScrollingTabContainerView.this.mVisibilityAnim = viewPropertyAnimator;
            return this;
        }
    }

}

