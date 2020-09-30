package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowInsets;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window.Callback;
import android.widget.OverScroller;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.core.graphics.Insets;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActionBarOverlayLayout extends ViewGroup implements DecorContentParent, NestedScrollingParent, NestedScrollingParent2, NestedScrollingParent3 {
   private static final int ACTION_BAR_ANIMATE_DELAY = 600;
   static final int[] ATTRS;
   private static final String TAG = "ActionBarOverlayLayout";
   private int mActionBarHeight;
   ActionBarContainer mActionBarTop;
   private ActionBarOverlayLayout.ActionBarVisibilityCallback mActionBarVisibilityCallback;
   private final Runnable mAddActionBarHideOffset;
   boolean mAnimatingForFling;
   private final Rect mBaseContentInsets;
   private WindowInsetsCompat mBaseInnerInsets;
   private final Rect mBaseInnerInsetsRect;
   private ContentFrameLayout mContent;
   private final Rect mContentInsets;
   ViewPropertyAnimator mCurrentActionBarTopAnimator;
   private DecorToolbar mDecorToolbar;
   private OverScroller mFlingEstimator;
   private boolean mHasNonEmbeddedTabs;
   private boolean mHideOnContentScroll;
   private int mHideOnContentScrollReference;
   private boolean mIgnoreWindowContentOverlay;
   private WindowInsetsCompat mInnerInsets;
   private final Rect mInnerInsetsRect;
   private final Rect mLastBaseContentInsets;
   private WindowInsetsCompat mLastBaseInnerInsets;
   private final Rect mLastBaseInnerInsetsRect;
   private WindowInsetsCompat mLastInnerInsets;
   private final Rect mLastInnerInsetsRect;
   private int mLastSystemUiVisibility;
   private boolean mOverlayMode;
   private final NestedScrollingParentHelper mParentHelper;
   private final Runnable mRemoveActionBarHideOffset;
   final AnimatorListenerAdapter mTopAnimatorListener;
   private Drawable mWindowContentOverlay;
   private int mWindowVisibility;

   static {
      ATTRS = new int[]{R.attr.actionBarSize, 16842841};
   }

   public ActionBarOverlayLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public ActionBarOverlayLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mWindowVisibility = 0;
      this.mBaseContentInsets = new Rect();
      this.mLastBaseContentInsets = new Rect();
      this.mContentInsets = new Rect();
      this.mBaseInnerInsetsRect = new Rect();
      this.mLastBaseInnerInsetsRect = new Rect();
      this.mInnerInsetsRect = new Rect();
      this.mLastInnerInsetsRect = new Rect();
      this.mBaseInnerInsets = WindowInsetsCompat.CONSUMED;
      this.mLastBaseInnerInsets = WindowInsetsCompat.CONSUMED;
      this.mInnerInsets = WindowInsetsCompat.CONSUMED;
      this.mLastInnerInsets = WindowInsetsCompat.CONSUMED;
      this.mTopAnimatorListener = new AnimatorListenerAdapter() {
         public void onAnimationCancel(Animator var1) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
         }

         public void onAnimationEnd(Animator var1) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
         }
      };
      this.mRemoveActionBarHideOffset = new Runnable() {
         public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout var1 = ActionBarOverlayLayout.this;
            var1.mCurrentActionBarTopAnimator = var1.mActionBarTop.animate().translationY(0.0F).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
         }
      };
      this.mAddActionBarHideOffset = new Runnable() {
         public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout var1 = ActionBarOverlayLayout.this;
            var1.mCurrentActionBarTopAnimator = var1.mActionBarTop.animate().translationY((float)(-ActionBarOverlayLayout.this.mActionBarTop.getHeight())).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
         }
      };
      this.init(var1);
      this.mParentHelper = new NestedScrollingParentHelper(this);
   }

   private void addActionBarHideOffset() {
      this.haltActionBarHideOffsetAnimations();
      this.mAddActionBarHideOffset.run();
   }

   private boolean applyInsets(View var1, Rect var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      ActionBarOverlayLayout.LayoutParams var9 = (ActionBarOverlayLayout.LayoutParams)var1.getLayoutParams();
      boolean var7 = true;
      boolean var8;
      if (var3 && var9.leftMargin != var2.left) {
         var9.leftMargin = var2.left;
         var8 = true;
      } else {
         var8 = false;
      }

      var3 = var8;
      if (var4) {
         var3 = var8;
         if (var9.topMargin != var2.top) {
            var9.topMargin = var2.top;
            var3 = true;
         }
      }

      var4 = var3;
      if (var6) {
         var4 = var3;
         if (var9.rightMargin != var2.right) {
            var9.rightMargin = var2.right;
            var4 = true;
         }
      }

      if (var5 && var9.bottomMargin != var2.bottom) {
         var9.bottomMargin = var2.bottom;
         var4 = var7;
      }

      return var4;
   }

   private DecorToolbar getDecorToolbar(View var1) {
      if (var1 instanceof DecorToolbar) {
         return (DecorToolbar)var1;
      } else if (var1 instanceof Toolbar) {
         return ((Toolbar)var1).getWrapper();
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Can't make a decor toolbar out of ");
         var2.append(var1.getClass().getSimpleName());
         throw new IllegalStateException(var2.toString());
      }
   }

   private void init(Context var1) {
      TypedArray var2 = this.getContext().getTheme().obtainStyledAttributes(ATTRS);
      boolean var3 = false;
      this.mActionBarHeight = var2.getDimensionPixelSize(0, 0);
      Drawable var4 = var2.getDrawable(1);
      this.mWindowContentOverlay = var4;
      boolean var5;
      if (var4 == null) {
         var5 = true;
      } else {
         var5 = false;
      }

      this.setWillNotDraw(var5);
      var2.recycle();
      var5 = var3;
      if (var1.getApplicationInfo().targetSdkVersion < 19) {
         var5 = true;
      }

      this.mIgnoreWindowContentOverlay = var5;
      this.mFlingEstimator = new OverScroller(var1);
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

   private boolean shouldHideActionBarOnFling(float var1) {
      this.mFlingEstimator.fling(0, 0, 0, (int)var1, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
      boolean var2;
      if (this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean canShowOverflowMenu() {
      this.pullChildren();
      return this.mDecorToolbar.canShowOverflowMenu();
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof ActionBarOverlayLayout.LayoutParams;
   }

   public void dismissPopups() {
      this.pullChildren();
      this.mDecorToolbar.dismissPopupMenus();
   }

   public void draw(Canvas var1) {
      super.draw(var1);
      if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
         int var2;
         if (this.mActionBarTop.getVisibility() == 0) {
            var2 = (int)((float)this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5F);
         } else {
            var2 = 0;
         }

         this.mWindowContentOverlay.setBounds(0, var2, this.getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + var2);
         this.mWindowContentOverlay.draw(var1);
      }

   }

   protected boolean fitSystemWindows(Rect var1) {
      if (VERSION.SDK_INT >= 21) {
         return super.fitSystemWindows(var1);
      } else {
         this.pullChildren();
         boolean var2 = this.applyInsets(this.mActionBarTop, var1, true, true, false, true);
         this.mBaseInnerInsetsRect.set(var1);
         ViewUtils.computeFitSystemWindows(this, this.mBaseInnerInsetsRect, this.mBaseContentInsets);
         if (!this.mLastBaseInnerInsetsRect.equals(this.mBaseInnerInsetsRect)) {
            this.mLastBaseInnerInsetsRect.set(this.mBaseInnerInsetsRect);
            var2 = true;
         }

         if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
            var2 = true;
         }

         if (var2) {
            this.requestLayout();
         }

         return true;
      }
   }

   protected ActionBarOverlayLayout.LayoutParams generateDefaultLayoutParams() {
      return new ActionBarOverlayLayout.LayoutParams(-1, -1);
   }

   protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return new ActionBarOverlayLayout.LayoutParams(var1);
   }

   public ActionBarOverlayLayout.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new ActionBarOverlayLayout.LayoutParams(this.getContext(), var1);
   }

   public int getActionBarHideOffset() {
      ActionBarContainer var1 = this.mActionBarTop;
      int var2;
      if (var1 != null) {
         var2 = -((int)var1.getTranslationY());
      } else {
         var2 = 0;
      }

      return var2;
   }

   public int getNestedScrollAxes() {
      return this.mParentHelper.getNestedScrollAxes();
   }

   public CharSequence getTitle() {
      this.pullChildren();
      return this.mDecorToolbar.getTitle();
   }

   void haltActionBarHideOffsetAnimations() {
      this.removeCallbacks(this.mRemoveActionBarHideOffset);
      this.removeCallbacks(this.mAddActionBarHideOffset);
      ViewPropertyAnimator var1 = this.mCurrentActionBarTopAnimator;
      if (var1 != null) {
         var1.cancel();
      }

   }

   public boolean hasIcon() {
      this.pullChildren();
      return this.mDecorToolbar.hasIcon();
   }

   public boolean hasLogo() {
      this.pullChildren();
      return this.mDecorToolbar.hasLogo();
   }

   public boolean hideOverflowMenu() {
      this.pullChildren();
      return this.mDecorToolbar.hideOverflowMenu();
   }

   public void initFeature(int var1) {
      this.pullChildren();
      if (var1 != 2) {
         if (var1 != 5) {
            if (var1 == 109) {
               this.setOverlayMode(true);
            }
         } else {
            this.mDecorToolbar.initIndeterminateProgress();
         }
      } else {
         this.mDecorToolbar.initProgress();
      }

   }

   public boolean isHideOnContentScrollEnabled() {
      return this.mHideOnContentScroll;
   }

   public boolean isInOverlayMode() {
      return this.mOverlayMode;
   }

   public boolean isOverflowMenuShowPending() {
      this.pullChildren();
      return this.mDecorToolbar.isOverflowMenuShowPending();
   }

   public boolean isOverflowMenuShowing() {
      this.pullChildren();
      return this.mDecorToolbar.isOverflowMenuShowing();
   }

   public WindowInsets onApplyWindowInsets(WindowInsets var1) {
      this.pullChildren();
      WindowInsetsCompat var6 = WindowInsetsCompat.toWindowInsetsCompat(var1);
      Rect var2 = new Rect(var6.getSystemWindowInsetLeft(), var6.getSystemWindowInsetTop(), var6.getSystemWindowInsetRight(), var6.getSystemWindowInsetBottom());
      boolean var3 = this.applyInsets(this.mActionBarTop, var2, true, true, false, true);
      ViewCompat.computeSystemWindowInsets(this, var6, this.mBaseContentInsets);
      WindowInsetsCompat var7 = var6.inset(this.mBaseContentInsets.left, this.mBaseContentInsets.top, this.mBaseContentInsets.right, this.mBaseContentInsets.bottom);
      this.mBaseInnerInsets = var7;
      boolean var4 = this.mLastBaseInnerInsets.equals(var7);
      boolean var5 = true;
      if (!var4) {
         this.mLastBaseInnerInsets = this.mBaseInnerInsets;
         var3 = true;
      }

      if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
         this.mLastBaseContentInsets.set(this.mBaseContentInsets);
         var3 = var5;
      }

      if (var3) {
         this.requestLayout();
      }

      return var6.consumeDisplayCutout().consumeSystemWindowInsets().consumeStableInsets().toWindowInsets();
   }

   protected void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.init(this.getContext());
      ViewCompat.requestApplyInsets(this);
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.haltActionBarHideOffsetAnimations();
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      var3 = this.getChildCount();
      var4 = this.getPaddingLeft();
      var5 = this.getPaddingTop();

      for(var2 = 0; var2 < var3; ++var2) {
         View var6 = this.getChildAt(var2);
         if (var6.getVisibility() != 8) {
            ActionBarOverlayLayout.LayoutParams var7 = (ActionBarOverlayLayout.LayoutParams)var6.getLayoutParams();
            int var8 = var6.getMeasuredWidth();
            int var9 = var6.getMeasuredHeight();
            int var10 = var7.leftMargin + var4;
            int var11 = var7.topMargin + var5;
            var6.layout(var10, var11, var8 + var10, var9 + var11);
         }
      }

   }

   protected void onMeasure(int var1, int var2) {
      this.pullChildren();
      this.measureChildWithMargins(this.mActionBarTop, var1, 0, var2, 0);
      ActionBarOverlayLayout.LayoutParams var3 = (ActionBarOverlayLayout.LayoutParams)this.mActionBarTop.getLayoutParams();
      int var4 = Math.max(0, this.mActionBarTop.getMeasuredWidth() + var3.leftMargin + var3.rightMargin);
      int var5 = Math.max(0, this.mActionBarTop.getMeasuredHeight() + var3.topMargin + var3.bottomMargin);
      int var6 = View.combineMeasuredStates(0, this.mActionBarTop.getMeasuredState());
      boolean var7;
      if ((ViewCompat.getWindowSystemUiVisibility(this) & 256) != 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      int var8;
      int var9;
      if (var7) {
         var8 = this.mActionBarHeight;
         var9 = var8;
         if (this.mHasNonEmbeddedTabs) {
            var9 = var8;
            if (this.mActionBarTop.getTabContainer() != null) {
               var9 = var8 + this.mActionBarHeight;
            }
         }
      } else if (this.mActionBarTop.getVisibility() != 8) {
         var9 = this.mActionBarTop.getMeasuredHeight();
      } else {
         var9 = 0;
      }

      this.mContentInsets.set(this.mBaseContentInsets);
      if (VERSION.SDK_INT >= 21) {
         this.mInnerInsets = this.mBaseInnerInsets;
      } else {
         this.mInnerInsetsRect.set(this.mBaseInnerInsetsRect);
      }

      Rect var11;
      if (!this.mOverlayMode && !var7) {
         var11 = this.mContentInsets;
         var11.top += var9;
         var11 = this.mContentInsets;
         var11.bottom += 0;
         if (VERSION.SDK_INT >= 21) {
            this.mInnerInsets = this.mInnerInsets.inset(0, var9, 0, 0);
         }
      } else if (VERSION.SDK_INT >= 21) {
         Insets var10 = Insets.of(this.mInnerInsets.getSystemWindowInsetLeft(), this.mInnerInsets.getSystemWindowInsetTop() + var9, this.mInnerInsets.getSystemWindowInsetRight(), this.mInnerInsets.getSystemWindowInsetBottom() + 0);
         this.mInnerInsets = (new WindowInsetsCompat.Builder(this.mInnerInsets)).setSystemWindowInsets(var10).build();
      } else {
         var11 = this.mInnerInsetsRect;
         var11.top += var9;
         var11 = this.mInnerInsetsRect;
         var11.bottom += 0;
      }

      this.applyInsets(this.mContent, this.mContentInsets, true, true, true, true);
      if (VERSION.SDK_INT >= 21 && !this.mLastInnerInsets.equals(this.mInnerInsets)) {
         WindowInsetsCompat var12 = this.mInnerInsets;
         this.mLastInnerInsets = var12;
         ViewCompat.dispatchApplyWindowInsets(this.mContent, var12);
      } else if (VERSION.SDK_INT < 21 && !this.mLastInnerInsetsRect.equals(this.mInnerInsetsRect)) {
         this.mLastInnerInsetsRect.set(this.mInnerInsetsRect);
         this.mContent.dispatchFitSystemWindows(this.mInnerInsetsRect);
      }

      this.measureChildWithMargins(this.mContent, var1, 0, var2, 0);
      var3 = (ActionBarOverlayLayout.LayoutParams)this.mContent.getLayoutParams();
      var9 = Math.max(var4, this.mContent.getMeasuredWidth() + var3.leftMargin + var3.rightMargin);
      int var13 = Math.max(var5, this.mContent.getMeasuredHeight() + var3.topMargin + var3.bottomMargin);
      var6 = View.combineMeasuredStates(var6, this.mContent.getMeasuredState());
      var8 = this.getPaddingLeft();
      var5 = this.getPaddingRight();
      var13 = Math.max(var13 + this.getPaddingTop() + this.getPaddingBottom(), this.getSuggestedMinimumHeight());
      this.setMeasuredDimension(View.resolveSizeAndState(Math.max(var9 + var8 + var5, this.getSuggestedMinimumWidth()), var1, var6), View.resolveSizeAndState(var13, var2, var6 << 16));
   }

   public boolean onNestedFling(View var1, float var2, float var3, boolean var4) {
      if (this.mHideOnContentScroll && var4) {
         if (this.shouldHideActionBarOnFling(var3)) {
            this.addActionBarHideOffset();
         } else {
            this.removeActionBarHideOffset();
         }

         this.mAnimatingForFling = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean onNestedPreFling(View var1, float var2, float var3) {
      return false;
   }

   public void onNestedPreScroll(View var1, int var2, int var3, int[] var4) {
   }

   public void onNestedPreScroll(View var1, int var2, int var3, int[] var4, int var5) {
      if (var5 == 0) {
         this.onNestedPreScroll(var1, var2, var3, var4);
      }

   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5) {
      var2 = this.mHideOnContentScrollReference + var3;
      this.mHideOnContentScrollReference = var2;
      this.setActionBarHideOffset(var2);
   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5, int var6) {
      if (var6 == 0) {
         this.onNestedScroll(var1, var2, var3, var4, var5);
      }

   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      this.onNestedScroll(var1, var2, var3, var4, var5, var6);
   }

   public void onNestedScrollAccepted(View var1, View var2, int var3) {
      this.mParentHelper.onNestedScrollAccepted(var1, var2, var3);
      this.mHideOnContentScrollReference = this.getActionBarHideOffset();
      this.haltActionBarHideOffsetAnimations();
      ActionBarOverlayLayout.ActionBarVisibilityCallback var4 = this.mActionBarVisibilityCallback;
      if (var4 != null) {
         var4.onContentScrollStarted();
      }

   }

   public void onNestedScrollAccepted(View var1, View var2, int var3, int var4) {
      if (var4 == 0) {
         this.onNestedScrollAccepted(var1, var2, var3);
      }

   }

   public boolean onStartNestedScroll(View var1, View var2, int var3) {
      return (var3 & 2) != 0 && this.mActionBarTop.getVisibility() == 0 ? this.mHideOnContentScroll : false;
   }

   public boolean onStartNestedScroll(View var1, View var2, int var3, int var4) {
      boolean var5;
      if (var4 == 0 && this.onStartNestedScroll(var1, var2, var3)) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public void onStopNestedScroll(View var1) {
      if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
         if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
            this.postRemoveActionBarHideOffset();
         } else {
            this.postAddActionBarHideOffset();
         }
      }

      ActionBarOverlayLayout.ActionBarVisibilityCallback var2 = this.mActionBarVisibilityCallback;
      if (var2 != null) {
         var2.onContentScrollStopped();
      }

   }

   public void onStopNestedScroll(View var1, int var2) {
      if (var2 == 0) {
         this.onStopNestedScroll(var1);
      }

   }

   public void onWindowSystemUiVisibilityChanged(int var1) {
      if (VERSION.SDK_INT >= 16) {
         super.onWindowSystemUiVisibilityChanged(var1);
      }

      this.pullChildren();
      int var2 = this.mLastSystemUiVisibility;
      this.mLastSystemUiVisibility = var1;
      boolean var3 = false;
      boolean var4;
      if ((var1 & 4) == 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if ((var1 & 256) != 0) {
         var3 = true;
      }

      ActionBarOverlayLayout.ActionBarVisibilityCallback var5 = this.mActionBarVisibilityCallback;
      if (var5 != null) {
         var5.enableContentAnimations(var3 ^ true);
         if (!var4 && var3) {
            this.mActionBarVisibilityCallback.hideForSystem();
         } else {
            this.mActionBarVisibilityCallback.showForSystem();
         }
      }

      if (((var2 ^ var1) & 256) != 0 && this.mActionBarVisibilityCallback != null) {
         ViewCompat.requestApplyInsets(this);
      }

   }

   protected void onWindowVisibilityChanged(int var1) {
      super.onWindowVisibilityChanged(var1);
      this.mWindowVisibility = var1;
      ActionBarOverlayLayout.ActionBarVisibilityCallback var2 = this.mActionBarVisibilityCallback;
      if (var2 != null) {
         var2.onWindowVisibilityChanged(var1);
      }

   }

   void pullChildren() {
      if (this.mContent == null) {
         this.mContent = (ContentFrameLayout)this.findViewById(R.id.action_bar_activity_content);
         this.mActionBarTop = (ActionBarContainer)this.findViewById(R.id.action_bar_container);
         this.mDecorToolbar = this.getDecorToolbar(this.findViewById(R.id.action_bar));
      }

   }

   public void restoreToolbarHierarchyState(SparseArray<Parcelable> var1) {
      this.pullChildren();
      this.mDecorToolbar.restoreHierarchyState(var1);
   }

   public void saveToolbarHierarchyState(SparseArray<Parcelable> var1) {
      this.pullChildren();
      this.mDecorToolbar.saveHierarchyState(var1);
   }

   public void setActionBarHideOffset(int var1) {
      this.haltActionBarHideOffsetAnimations();
      var1 = Math.max(0, Math.min(var1, this.mActionBarTop.getHeight()));
      this.mActionBarTop.setTranslationY((float)(-var1));
   }

   public void setActionBarVisibilityCallback(ActionBarOverlayLayout.ActionBarVisibilityCallback var1) {
      this.mActionBarVisibilityCallback = var1;
      if (this.getWindowToken() != null) {
         this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
         int var2 = this.mLastSystemUiVisibility;
         if (var2 != 0) {
            this.onWindowSystemUiVisibilityChanged(var2);
            ViewCompat.requestApplyInsets(this);
         }
      }

   }

   public void setHasNonEmbeddedTabs(boolean var1) {
      this.mHasNonEmbeddedTabs = var1;
   }

   public void setHideOnContentScrollEnabled(boolean var1) {
      if (var1 != this.mHideOnContentScroll) {
         this.mHideOnContentScroll = var1;
         if (!var1) {
            this.haltActionBarHideOffsetAnimations();
            this.setActionBarHideOffset(0);
         }
      }

   }

   public void setIcon(int var1) {
      this.pullChildren();
      this.mDecorToolbar.setIcon(var1);
   }

   public void setIcon(Drawable var1) {
      this.pullChildren();
      this.mDecorToolbar.setIcon(var1);
   }

   public void setLogo(int var1) {
      this.pullChildren();
      this.mDecorToolbar.setLogo(var1);
   }

   public void setMenu(Menu var1, MenuPresenter.Callback var2) {
      this.pullChildren();
      this.mDecorToolbar.setMenu(var1, var2);
   }

   public void setMenuPrepared() {
      this.pullChildren();
      this.mDecorToolbar.setMenuPrepared();
   }

   public void setOverlayMode(boolean var1) {
      this.mOverlayMode = var1;
      if (var1 && this.getContext().getApplicationInfo().targetSdkVersion < 19) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.mIgnoreWindowContentOverlay = var1;
   }

   public void setShowingForActionMode(boolean var1) {
   }

   public void setUiOptions(int var1) {
   }

   public void setWindowCallback(Callback var1) {
      this.pullChildren();
      this.mDecorToolbar.setWindowCallback(var1);
   }

   public void setWindowTitle(CharSequence var1) {
      this.pullChildren();
      this.mDecorToolbar.setWindowTitle(var1);
   }

   public boolean shouldDelayChildPressedState() {
      return false;
   }

   public boolean showOverflowMenu() {
      this.pullChildren();
      return this.mDecorToolbar.showOverflowMenu();
   }

   public interface ActionBarVisibilityCallback {
      void enableContentAnimations(boolean var1);

      void hideForSystem();

      void onContentScrollStarted();

      void onContentScrollStopped();

      void onWindowVisibilityChanged(int var1);

      void showForSystem();
   }

   public static class LayoutParams extends MarginLayoutParams {
      public LayoutParams(int var1, int var2) {
         super(var1, var2);
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }
   }
}