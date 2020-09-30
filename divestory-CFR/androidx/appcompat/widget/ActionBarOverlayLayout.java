/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.Menu
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.Window
 *  android.view.Window$Callback
 *  android.view.WindowInsets
 *  android.widget.OverScroller
 */
package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.OverScroller;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActionBarOverlayLayout
extends ViewGroup
implements DecorContentParent,
NestedScrollingParent,
NestedScrollingParent2,
NestedScrollingParent3 {
    private static final int ACTION_BAR_ANIMATE_DELAY = 600;
    static final int[] ATTRS = new int[]{R.attr.actionBarSize, 16842841};
    private static final String TAG = "ActionBarOverlayLayout";
    private int mActionBarHeight;
    ActionBarContainer mActionBarTop;
    private ActionBarVisibilityCallback mActionBarVisibilityCallback;
    private final Runnable mAddActionBarHideOffset = new Runnable(){

        @Override
        public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY((float)(-ActionBarOverlayLayout.this.mActionBarTop.getHeight())).setListener((Animator.AnimatorListener)ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    };
    boolean mAnimatingForFling;
    private final Rect mBaseContentInsets = new Rect();
    private WindowInsetsCompat mBaseInnerInsets = WindowInsetsCompat.CONSUMED;
    private final Rect mBaseInnerInsetsRect = new Rect();
    private ContentFrameLayout mContent;
    private final Rect mContentInsets = new Rect();
    ViewPropertyAnimator mCurrentActionBarTopAnimator;
    private DecorToolbar mDecorToolbar;
    private OverScroller mFlingEstimator;
    private boolean mHasNonEmbeddedTabs;
    private boolean mHideOnContentScroll;
    private int mHideOnContentScrollReference;
    private boolean mIgnoreWindowContentOverlay;
    private WindowInsetsCompat mInnerInsets = WindowInsetsCompat.CONSUMED;
    private final Rect mInnerInsetsRect = new Rect();
    private final Rect mLastBaseContentInsets = new Rect();
    private WindowInsetsCompat mLastBaseInnerInsets = WindowInsetsCompat.CONSUMED;
    private final Rect mLastBaseInnerInsetsRect = new Rect();
    private WindowInsetsCompat mLastInnerInsets = WindowInsetsCompat.CONSUMED;
    private final Rect mLastInnerInsetsRect = new Rect();
    private int mLastSystemUiVisibility;
    private boolean mOverlayMode;
    private final NestedScrollingParentHelper mParentHelper;
    private final Runnable mRemoveActionBarHideOffset = new Runnable(){

        @Override
        public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(0.0f).setListener((Animator.AnimatorListener)ActionBarOverlayLayout.this.mTopAnimatorListener);
        }
    };
    final AnimatorListenerAdapter mTopAnimatorListener = new AnimatorListenerAdapter(){

        public void onAnimationCancel(Animator animator2) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }

        public void onAnimationEnd(Animator animator2) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }
    };
    private Drawable mWindowContentOverlay;
    private int mWindowVisibility = 0;

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
        this.mParentHelper = new NestedScrollingParentHelper(this);
    }

    private void addActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.mAddActionBarHideOffset.run();
    }

    private boolean applyInsets(View object, Rect rect, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        boolean bl5;
        object = (LayoutParams)object.getLayoutParams();
        boolean bl6 = true;
        if (bl && object.leftMargin != rect.left) {
            object.leftMargin = rect.left;
            bl5 = true;
        } else {
            bl5 = false;
        }
        bl = bl5;
        if (bl2) {
            bl = bl5;
            if (object.topMargin != rect.top) {
                object.topMargin = rect.top;
                bl = true;
            }
        }
        bl2 = bl;
        if (bl4) {
            bl2 = bl;
            if (object.rightMargin != rect.right) {
                object.rightMargin = rect.right;
                bl2 = true;
            }
        }
        if (!bl3) return bl2;
        if (object.bottomMargin == rect.bottom) return bl2;
        object.bottomMargin = rect.bottom;
        return bl6;
    }

    private DecorToolbar getDecorToolbar(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar)view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar)view).getWrapper();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't make a decor toolbar out of ");
        stringBuilder.append(view.getClass().getSimpleName());
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void init(Context context) {
        Drawable drawable2;
        TypedArray typedArray = this.getContext().getTheme().obtainStyledAttributes(ATTRS);
        boolean bl = false;
        this.mActionBarHeight = typedArray.getDimensionPixelSize(0, 0);
        this.mWindowContentOverlay = drawable2 = typedArray.getDrawable(1);
        boolean bl2 = drawable2 == null;
        this.setWillNotDraw(bl2);
        typedArray.recycle();
        bl2 = bl;
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            bl2 = true;
        }
        this.mIgnoreWindowContentOverlay = bl2;
        this.mFlingEstimator = new OverScroller(context);
    }

    private void postAddActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.postDelayed(this.mAddActionBarHideOffset, 600L);
    }

    private void postRemoveActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.postDelayed(this.mRemoveActionBarHideOffset, 600L);
    }

    private void removeActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.mRemoveActionBarHideOffset.run();
    }

    private boolean shouldHideActionBarOnFling(float f) {
        this.mFlingEstimator.fling(0, 0, 0, (int)f, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (this.mFlingEstimator.getFinalY() <= this.mActionBarTop.getHeight()) return false;
        return true;
    }

    @Override
    public boolean canShowOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.canShowOverflowMenu();
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    public void dismissPopups() {
        this.pullChildren();
        this.mDecorToolbar.dismissPopupMenus();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mWindowContentOverlay == null) return;
        if (this.mIgnoreWindowContentOverlay) return;
        int n = this.mActionBarTop.getVisibility() == 0 ? (int)((float)this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5f) : 0;
        this.mWindowContentOverlay.setBounds(0, n, this.getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + n);
        this.mWindowContentOverlay.draw(canvas);
    }

    protected boolean fitSystemWindows(Rect rect) {
        if (Build.VERSION.SDK_INT >= 21) {
            return super.fitSystemWindows(rect);
        }
        this.pullChildren();
        boolean bl = this.applyInsets((View)this.mActionBarTop, rect, true, true, false, true);
        this.mBaseInnerInsetsRect.set(rect);
        ViewUtils.computeFitSystemWindows((View)this, this.mBaseInnerInsetsRect, this.mBaseContentInsets);
        if (!this.mLastBaseInnerInsetsRect.equals((Object)this.mBaseInnerInsetsRect)) {
            this.mLastBaseInnerInsetsRect.set(this.mBaseInnerInsetsRect);
            bl = true;
        }
        if (!this.mLastBaseContentInsets.equals((Object)this.mBaseContentInsets)) {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
            bl = true;
        }
        if (!bl) return true;
        this.requestLayout();
        return true;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        if (actionBarContainer == null) return 0;
        return -((int)actionBarContainer.getTranslationY());
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    @Override
    public CharSequence getTitle() {
        this.pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    void haltActionBarHideOffsetAnimations() {
        this.removeCallbacks(this.mRemoveActionBarHideOffset);
        this.removeCallbacks(this.mAddActionBarHideOffset);
        ViewPropertyAnimator viewPropertyAnimator = this.mCurrentActionBarTopAnimator;
        if (viewPropertyAnimator == null) return;
        viewPropertyAnimator.cancel();
    }

    @Override
    public boolean hasIcon() {
        this.pullChildren();
        return this.mDecorToolbar.hasIcon();
    }

    @Override
    public boolean hasLogo() {
        this.pullChildren();
        return this.mDecorToolbar.hasLogo();
    }

    @Override
    public boolean hideOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override
    public void initFeature(int n) {
        this.pullChildren();
        if (n == 2) {
            this.mDecorToolbar.initProgress();
            return;
        }
        if (n == 5) {
            this.mDecorToolbar.initIndeterminateProgress();
            return;
        }
        if (n != 109) {
            return;
        }
        this.setOverlayMode(true);
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll;
    }

    public boolean isInOverlayMode() {
        return this.mOverlayMode;
    }

    @Override
    public boolean isOverflowMenuShowPending() {
        this.pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowPending();
    }

    @Override
    public boolean isOverflowMenuShowing() {
        this.pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowing();
    }

    public WindowInsets onApplyWindowInsets(WindowInsets object) {
        this.pullChildren();
        object = WindowInsetsCompat.toWindowInsetsCompat((WindowInsets)object);
        Object object2 = new Rect(((WindowInsetsCompat)object).getSystemWindowInsetLeft(), ((WindowInsetsCompat)object).getSystemWindowInsetTop(), ((WindowInsetsCompat)object).getSystemWindowInsetRight(), ((WindowInsetsCompat)object).getSystemWindowInsetBottom());
        boolean bl = this.applyInsets((View)this.mActionBarTop, (Rect)object2, true, true, false, true);
        ViewCompat.computeSystemWindowInsets((View)this, (WindowInsetsCompat)object, this.mBaseContentInsets);
        object2 = ((WindowInsetsCompat)object).inset(this.mBaseContentInsets.left, this.mBaseContentInsets.top, this.mBaseContentInsets.right, this.mBaseContentInsets.bottom);
        this.mBaseInnerInsets = object2;
        boolean bl2 = this.mLastBaseInnerInsets.equals(object2);
        boolean bl3 = true;
        if (!bl2) {
            this.mLastBaseInnerInsets = this.mBaseInnerInsets;
            bl = true;
        }
        if (!this.mLastBaseContentInsets.equals((Object)this.mBaseContentInsets)) {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
            bl = bl3;
        }
        if (!bl) return ((WindowInsetsCompat)object).consumeDisplayCutout().consumeSystemWindowInsets().consumeStableInsets().toWindowInsets();
        this.requestLayout();
        return ((WindowInsetsCompat)object).consumeDisplayCutout().consumeSystemWindowInsets().consumeStableInsets().toWindowInsets();
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.init(this.getContext());
        ViewCompat.requestApplyInsets((View)this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.haltActionBarHideOffsetAnimations();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = this.getChildCount();
        n3 = this.getPaddingLeft();
        n4 = this.getPaddingTop();
        n = 0;
        while (n < n2) {
            View view = this.getChildAt(n);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                int n5 = view.getMeasuredWidth();
                int n6 = view.getMeasuredHeight();
                int n7 = layoutParams.leftMargin + n3;
                int n8 = layoutParams.topMargin + n4;
                view.layout(n7, n8, n5 + n7, n6 + n8);
            }
            ++n;
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        this.pullChildren();
        this.measureChildWithMargins((View)this.mActionBarTop, n, 0, n2, 0);
        Object object = (LayoutParams)this.mActionBarTop.getLayoutParams();
        int n5 = Math.max(0, this.mActionBarTop.getMeasuredWidth() + object.leftMargin + object.rightMargin);
        int n6 = Math.max(0, this.mActionBarTop.getMeasuredHeight() + object.topMargin + object.bottomMargin);
        int n7 = View.combineMeasuredStates((int)0, (int)this.mActionBarTop.getMeasuredState());
        int n8 = (ViewCompat.getWindowSystemUiVisibility((View)this) & 256) != 0 ? 1 : 0;
        if (n8 != 0) {
            n3 = n4 = this.mActionBarHeight;
            if (this.mHasNonEmbeddedTabs) {
                n3 = n4;
                if (this.mActionBarTop.getTabContainer() != null) {
                    n3 = n4 + this.mActionBarHeight;
                }
            }
        } else {
            n3 = this.mActionBarTop.getVisibility() != 8 ? this.mActionBarTop.getMeasuredHeight() : 0;
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInnerInsets = this.mBaseInnerInsets;
        } else {
            this.mInnerInsetsRect.set(this.mBaseInnerInsetsRect);
        }
        if (!this.mOverlayMode && n8 == 0) {
            object = this.mContentInsets;
            ((Rect)object).top += n3;
            object = this.mContentInsets;
            ((Rect)object).bottom += 0;
            if (Build.VERSION.SDK_INT >= 21) {
                this.mInnerInsets = this.mInnerInsets.inset(0, n3, 0, 0);
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            object = Insets.of(this.mInnerInsets.getSystemWindowInsetLeft(), this.mInnerInsets.getSystemWindowInsetTop() + n3, this.mInnerInsets.getSystemWindowInsetRight(), this.mInnerInsets.getSystemWindowInsetBottom() + 0);
            this.mInnerInsets = new WindowInsetsCompat.Builder(this.mInnerInsets).setSystemWindowInsets((Insets)object).build();
        } else {
            object = this.mInnerInsetsRect;
            ((Rect)object).top += n3;
            object = this.mInnerInsetsRect;
            ((Rect)object).bottom += 0;
        }
        this.applyInsets((View)this.mContent, this.mContentInsets, true, true, true, true);
        if (Build.VERSION.SDK_INT >= 21 && !this.mLastInnerInsets.equals(this.mInnerInsets)) {
            object = this.mInnerInsets;
            this.mLastInnerInsets = object;
            ViewCompat.dispatchApplyWindowInsets((View)this.mContent, (WindowInsetsCompat)object);
        } else if (Build.VERSION.SDK_INT < 21 && !this.mLastInnerInsetsRect.equals((Object)this.mInnerInsetsRect)) {
            this.mLastInnerInsetsRect.set(this.mInnerInsetsRect);
            this.mContent.dispatchFitSystemWindows(this.mInnerInsetsRect);
        }
        this.measureChildWithMargins((View)this.mContent, n, 0, n2, 0);
        object = (LayoutParams)this.mContent.getLayoutParams();
        n3 = Math.max(n5, this.mContent.getMeasuredWidth() + object.leftMargin + object.rightMargin);
        n8 = Math.max(n6, this.mContent.getMeasuredHeight() + object.topMargin + object.bottomMargin);
        n7 = View.combineMeasuredStates((int)n7, (int)this.mContent.getMeasuredState());
        n4 = this.getPaddingLeft();
        n6 = this.getPaddingRight();
        n8 = Math.max(n8 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(n3 + (n4 + n6), this.getSuggestedMinimumWidth()), (int)n, (int)n7), View.resolveSizeAndState((int)n8, (int)n2, (int)(n7 << 16)));
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        if (!this.mHideOnContentScroll) return false;
        if (!bl) {
            return false;
        }
        if (this.shouldHideActionBarOnFling(f2)) {
            this.addActionBarHideOffset();
        } else {
            this.removeActionBarHideOffset();
        }
        this.mAnimatingForFling = true;
        return true;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn, int n3) {
        if (n3 != 0) return;
        this.onNestedPreScroll(view, n, n2, arrn);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.mHideOnContentScrollReference = n = this.mHideOnContentScrollReference + n2;
        this.setActionBarHideOffset(n);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5) {
        if (n5 != 0) return;
        this.onNestedScroll(view, n, n2, n3, n4);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5, int[] arrn) {
        this.onNestedScroll(view, n, n2, n3, n4, n5);
    }

    @Override
    public void onNestedScrollAccepted(View object, View view, int n) {
        this.mParentHelper.onNestedScrollAccepted((View)object, view, n);
        this.mHideOnContentScrollReference = this.getActionBarHideOffset();
        this.haltActionBarHideOffsetAnimations();
        object = this.mActionBarVisibilityCallback;
        if (object == null) return;
        object.onContentScrollStarted();
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n, int n2) {
        if (n2 != 0) return;
        this.onNestedScrollAccepted(view, view2, n);
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        if ((n & 2) == 0) return false;
        if (this.mActionBarTop.getVisibility() == 0) return this.mHideOnContentScroll;
        return false;
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n, int n2) {
        if (n2 != 0) return false;
        if (!this.onStartNestedScroll(view, view2, n)) return false;
        return true;
    }

    @Override
    public void onStopNestedScroll(View object) {
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                this.postRemoveActionBarHideOffset();
            } else {
                this.postAddActionBarHideOffset();
            }
        }
        if ((object = this.mActionBarVisibilityCallback) == null) return;
        object.onContentScrollStopped();
    }

    @Override
    public void onStopNestedScroll(View view, int n) {
        if (n != 0) return;
        this.onStopNestedScroll(view);
    }

    public void onWindowSystemUiVisibilityChanged(int n) {
        ActionBarVisibilityCallback actionBarVisibilityCallback;
        if (Build.VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(n);
        }
        this.pullChildren();
        int n2 = this.mLastSystemUiVisibility;
        this.mLastSystemUiVisibility = n;
        boolean bl = false;
        boolean bl2 = (n & 4) == 0;
        if ((n & 256) != 0) {
            bl = true;
        }
        if ((actionBarVisibilityCallback = this.mActionBarVisibilityCallback) != null) {
            actionBarVisibilityCallback.enableContentAnimations(bl ^ true);
            if (!bl2 && bl) {
                this.mActionBarVisibilityCallback.hideForSystem();
            } else {
                this.mActionBarVisibilityCallback.showForSystem();
            }
        }
        if (((n2 ^ n) & 256) == 0) return;
        if (this.mActionBarVisibilityCallback == null) return;
        ViewCompat.requestApplyInsets((View)this);
    }

    protected void onWindowVisibilityChanged(int n) {
        super.onWindowVisibilityChanged(n);
        this.mWindowVisibility = n;
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback == null) return;
        actionBarVisibilityCallback.onWindowVisibilityChanged(n);
    }

    void pullChildren() {
        if (this.mContent != null) return;
        this.mContent = (ContentFrameLayout)this.findViewById(R.id.action_bar_activity_content);
        this.mActionBarTop = (ActionBarContainer)this.findViewById(R.id.action_bar_container);
        this.mDecorToolbar = this.getDecorToolbar(this.findViewById(R.id.action_bar));
    }

    @Override
    public void restoreToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.pullChildren();
        this.mDecorToolbar.restoreHierarchyState(sparseArray);
    }

    @Override
    public void saveToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.pullChildren();
        this.mDecorToolbar.saveHierarchyState(sparseArray);
    }

    public void setActionBarHideOffset(int n) {
        this.haltActionBarHideOffsetAnimations();
        n = Math.max(0, Math.min(n, this.mActionBarTop.getHeight()));
        this.mActionBarTop.setTranslationY((float)(-n));
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.mActionBarVisibilityCallback = actionBarVisibilityCallback;
        if (this.getWindowToken() == null) return;
        this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
        int n = this.mLastSystemUiVisibility;
        if (n == 0) return;
        this.onWindowSystemUiVisibilityChanged(n);
        ViewCompat.requestApplyInsets((View)this);
    }

    public void setHasNonEmbeddedTabs(boolean bl) {
        this.mHasNonEmbeddedTabs = bl;
    }

    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl == this.mHideOnContentScroll) return;
        this.mHideOnContentScroll = bl;
        if (bl) return;
        this.haltActionBarHideOffsetAnimations();
        this.setActionBarHideOffset(0);
    }

    @Override
    public void setIcon(int n) {
        this.pullChildren();
        this.mDecorToolbar.setIcon(n);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.pullChildren();
        this.mDecorToolbar.setIcon(drawable2);
    }

    @Override
    public void setLogo(int n) {
        this.pullChildren();
        this.mDecorToolbar.setLogo(n);
    }

    @Override
    public void setMenu(Menu menu2, MenuPresenter.Callback callback) {
        this.pullChildren();
        this.mDecorToolbar.setMenu(menu2, callback);
    }

    @Override
    public void setMenuPrepared() {
        this.pullChildren();
        this.mDecorToolbar.setMenuPrepared();
    }

    public void setOverlayMode(boolean bl) {
        this.mOverlayMode = bl;
        bl = bl && this.getContext().getApplicationInfo().targetSdkVersion < 19;
        this.mIgnoreWindowContentOverlay = bl;
    }

    public void setShowingForActionMode(boolean bl) {
    }

    @Override
    public void setUiOptions(int n) {
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.pullChildren();
        this.mDecorToolbar.setWindowCallback(callback);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.pullChildren();
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public boolean showOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.showOverflowMenu();
    }

    public static interface ActionBarVisibilityCallback {
        public void enableContentAnimations(boolean var1);

        public void hideForSystem();

        public void onContentScrollStarted();

        public void onContentScrollStopped();

        public void onWindowVisibilityChanged(int var1);

        public void showForSystem();
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

}

