package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior {
   private static final int DEF_STYLE_RES;
   private static final int INVALID_SCROLL_RANGE = -1;
   static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
   static final int PENDING_ACTION_COLLAPSED = 2;
   static final int PENDING_ACTION_EXPANDED = 1;
   static final int PENDING_ACTION_FORCE = 8;
   static final int PENDING_ACTION_NONE = 0;
   private int currentOffset;
   private int downPreScrollRange;
   private int downScrollRange;
   private ValueAnimator elevationOverlayAnimator;
   private boolean haveChildWithInterpolator;
   private WindowInsetsCompat lastInsets;
   private boolean liftOnScroll;
   private WeakReference<View> liftOnScrollTargetView;
   private int liftOnScrollTargetViewId;
   private boolean liftable;
   private boolean liftableOverride;
   private boolean lifted;
   private List<AppBarLayout.BaseOnOffsetChangedListener> listeners;
   private int pendingAction;
   private Drawable statusBarForeground;
   private int[] tmpStatesArray;
   private int totalScrollRange;

   static {
      DEF_STYLE_RES = R.style.Widget_Design_AppBarLayout;
   }

   public AppBarLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public AppBarLayout(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.appBarLayoutStyle);
   }

   public AppBarLayout(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.totalScrollRange = -1;
      this.downPreScrollRange = -1;
      this.downScrollRange = -1;
      this.pendingAction = 0;
      var1 = this.getContext();
      this.setOrientation(1);
      if (VERSION.SDK_INT >= 21) {
         ViewUtilsLollipop.setBoundsViewOutlineProvider(this);
         ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, var2, var3, DEF_STYLE_RES);
      }

      TypedArray var4 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.AppBarLayout, var3, DEF_STYLE_RES);
      ViewCompat.setBackground(this, var4.getDrawable(R.styleable.AppBarLayout_android_background));
      if (this.getBackground() instanceof ColorDrawable) {
         ColorDrawable var5 = (ColorDrawable)this.getBackground();
         MaterialShapeDrawable var6 = new MaterialShapeDrawable();
         var6.setFillColor(ColorStateList.valueOf(var5.getColor()));
         var6.initializeElevationOverlay(var1);
         ViewCompat.setBackground(this, var6);
      }

      if (var4.hasValue(R.styleable.AppBarLayout_expanded)) {
         this.setExpanded(var4.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
      }

      if (VERSION.SDK_INT >= 21 && var4.hasValue(R.styleable.AppBarLayout_elevation)) {
         ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, (float)var4.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
      }

      if (VERSION.SDK_INT >= 26) {
         if (var4.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
            this.setKeyboardNavigationCluster(var4.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
         }

         if (var4.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
            this.setTouchscreenBlocksFocus(var4.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
         }
      }

      this.liftOnScroll = var4.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
      this.liftOnScrollTargetViewId = var4.getResourceId(R.styleable.AppBarLayout_liftOnScrollTargetViewId, -1);
      this.setStatusBarForeground(var4.getDrawable(R.styleable.AppBarLayout_statusBarForeground));
      var4.recycle();
      ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
         public WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2) {
            return AppBarLayout.this.onWindowInsetChanged(var2);
         }
      });
   }

   private void clearLiftOnScrollTargetView() {
      WeakReference var1 = this.liftOnScrollTargetView;
      if (var1 != null) {
         var1.clear();
      }

      this.liftOnScrollTargetView = null;
   }

   private View findLiftOnScrollTargetView(View var1) {
      WeakReference var2 = this.liftOnScrollTargetView;
      Object var3 = null;
      if (var2 == null) {
         int var4 = this.liftOnScrollTargetViewId;
         if (var4 != -1) {
            if (var1 != null) {
               var1 = var1.findViewById(var4);
            } else {
               var1 = null;
            }

            View var5 = var1;
            if (var1 == null) {
               var5 = var1;
               if (this.getParent() instanceof ViewGroup) {
                  var5 = ((ViewGroup)this.getParent()).findViewById(this.liftOnScrollTargetViewId);
               }
            }

            if (var5 != null) {
               this.liftOnScrollTargetView = new WeakReference(var5);
            }
         }
      }

      var2 = this.liftOnScrollTargetView;
      var1 = (View)var3;
      if (var2 != null) {
         var1 = (View)var2.get();
      }

      return var1;
   }

   private boolean hasCollapsibleChild() {
      int var1 = this.getChildCount();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (((AppBarLayout.LayoutParams)this.getChildAt(var2).getLayoutParams()).isCollapsible()) {
            return true;
         }
      }

      return false;
   }

   private void invalidateScrollRanges() {
      this.totalScrollRange = -1;
      this.downPreScrollRange = -1;
      this.downScrollRange = -1;
   }

   private void setExpanded(boolean var1, boolean var2, boolean var3) {
      byte var4;
      if (var1) {
         var4 = 1;
      } else {
         var4 = 2;
      }

      byte var5 = 0;
      byte var6;
      if (var2) {
         var6 = 4;
      } else {
         var6 = 0;
      }

      if (var3) {
         var5 = 8;
      }

      this.pendingAction = var4 | var6 | var5;
      this.requestLayout();
   }

   private boolean setLiftableState(boolean var1) {
      if (this.liftable != var1) {
         this.liftable = var1;
         this.refreshDrawableState();
         return true;
      } else {
         return false;
      }
   }

   private boolean shouldDrawStatusBarForeground() {
      boolean var1;
      if (this.statusBarForeground != null && this.getTopInset() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean shouldOffsetFirstChild() {
      int var1 = this.getChildCount();
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 > 0) {
         View var4 = this.getChildAt(0);
         var3 = var2;
         if (var4.getVisibility() != 8) {
            var3 = var2;
            if (!ViewCompat.getFitsSystemWindows(var4)) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   private void startLiftOnScrollElevationOverlayAnimation(final MaterialShapeDrawable var1, boolean var2) {
      float var3 = this.getResources().getDimension(R.dimen.design_appbar_elevation);
      float var4;
      if (var2) {
         var4 = 0.0F;
      } else {
         var4 = var3;
      }

      if (!var2) {
         var3 = 0.0F;
      }

      ValueAnimator var5 = this.elevationOverlayAnimator;
      if (var5 != null) {
         var5.cancel();
      }

      var5 = ValueAnimator.ofFloat(new float[]{var4, var3});
      this.elevationOverlayAnimator = var5;
      var5.setDuration((long)this.getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
      this.elevationOverlayAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
      this.elevationOverlayAnimator.addUpdateListener(new AnimatorUpdateListener() {
         public void onAnimationUpdate(ValueAnimator var1x) {
            var1.setElevation((Float)var1x.getAnimatedValue());
         }
      });
      this.elevationOverlayAnimator.start();
   }

   private void updateWillNotDraw() {
      this.setWillNotDraw(this.shouldDrawStatusBarForeground() ^ true);
   }

   public void addOnOffsetChangedListener(AppBarLayout.BaseOnOffsetChangedListener var1) {
      if (this.listeners == null) {
         this.listeners = new ArrayList();
      }

      if (var1 != null && !this.listeners.contains(var1)) {
         this.listeners.add(var1);
      }

   }

   public void addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener var1) {
      this.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener)var1);
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof AppBarLayout.LayoutParams;
   }

   public void draw(Canvas var1) {
      super.draw(var1);
      if (this.shouldDrawStatusBarForeground()) {
         int var2 = var1.save();
         var1.translate(0.0F, (float)(-this.currentOffset));
         this.statusBarForeground.draw(var1);
         var1.restoreToCount(var2);
      }

   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      int[] var1 = this.getDrawableState();
      Drawable var2 = this.statusBarForeground;
      if (var2 != null && var2.isStateful() && var2.setState(var1)) {
         this.invalidateDrawable(var2);
      }

   }

   protected AppBarLayout.LayoutParams generateDefaultLayoutParams() {
      return new AppBarLayout.LayoutParams(-1, -2);
   }

   public AppBarLayout.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new AppBarLayout.LayoutParams(this.getContext(), var1);
   }

   protected AppBarLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      if (VERSION.SDK_INT >= 19 && var1 instanceof android.widget.LinearLayout.LayoutParams) {
         return new AppBarLayout.LayoutParams((android.widget.LinearLayout.LayoutParams)var1);
      } else {
         return var1 instanceof MarginLayoutParams ? new AppBarLayout.LayoutParams((MarginLayoutParams)var1) : new AppBarLayout.LayoutParams(var1);
      }
   }

   public CoordinatorLayout.Behavior<AppBarLayout> getBehavior() {
      return new AppBarLayout.Behavior();
   }

   int getDownNestedPreScrollRange() {
      int var1 = this.downPreScrollRange;
      if (var1 != -1) {
         return var1;
      } else {
         int var2 = this.getChildCount() - 1;

         int var3;
         for(var3 = 0; var2 >= 0; var3 = var1) {
            View var4 = this.getChildAt(var2);
            AppBarLayout.LayoutParams var5 = (AppBarLayout.LayoutParams)var4.getLayoutParams();
            int var6 = var4.getMeasuredHeight();
            var1 = var5.scrollFlags;
            if ((var1 & 5) == 5) {
               int var7;
               label35: {
                  var7 = var5.topMargin + var5.bottomMargin;
                  if ((var1 & 8) != 0) {
                     var1 = ViewCompat.getMinimumHeight(var4);
                  } else {
                     if ((var1 & 2) == 0) {
                        var1 = var7 + var6;
                        break label35;
                     }

                     var1 = var6 - ViewCompat.getMinimumHeight(var4);
                  }

                  var1 += var7;
               }

               var7 = var1;
               if (var2 == 0) {
                  var7 = var1;
                  if (ViewCompat.getFitsSystemWindows(var4)) {
                     var7 = Math.min(var1, var6 - this.getTopInset());
                  }
               }

               var1 = var3 + var7;
            } else {
               var1 = var3;
               if (var3 > 0) {
                  break;
               }
            }

            --var2;
         }

         var1 = Math.max(0, var3);
         this.downPreScrollRange = var1;
         return var1;
      }
   }

   int getDownNestedScrollRange() {
      int var1 = this.downScrollRange;
      if (var1 != -1) {
         return var1;
      } else {
         int var2 = this.getChildCount();
         int var3 = 0;
         var1 = 0;

         int var4;
         while(true) {
            var4 = var1;
            if (var3 >= var2) {
               break;
            }

            View var5 = this.getChildAt(var3);
            AppBarLayout.LayoutParams var6 = (AppBarLayout.LayoutParams)var5.getLayoutParams();
            int var7 = var5.getMeasuredHeight();
            int var8 = var6.topMargin;
            int var9 = var6.bottomMargin;
            int var10 = var6.scrollFlags;
            var4 = var1;
            if ((var10 & 1) == 0) {
               break;
            }

            var1 += var7 + var8 + var9;
            if ((var10 & 2) != 0) {
               var4 = var1 - ViewCompat.getMinimumHeight(var5);
               break;
            }

            ++var3;
         }

         var1 = Math.max(0, var4);
         this.downScrollRange = var1;
         return var1;
      }
   }

   public int getLiftOnScrollTargetViewId() {
      return this.liftOnScrollTargetViewId;
   }

   public final int getMinimumHeightForVisibleOverlappingContent() {
      int var1 = this.getTopInset();
      int var2 = ViewCompat.getMinimumHeight(this);
      if (var2 == 0) {
         var2 = this.getChildCount();
         if (var2 >= 1) {
            var2 = ViewCompat.getMinimumHeight(this.getChildAt(var2 - 1));
         } else {
            var2 = 0;
         }

         if (var2 == 0) {
            return this.getHeight() / 3;
         }
      }

      return var2 * 2 + var1;
   }

   int getPendingAction() {
      return this.pendingAction;
   }

   public Drawable getStatusBarForeground() {
      return this.statusBarForeground;
   }

   @Deprecated
   public float getTargetElevation() {
      return 0.0F;
   }

   final int getTopInset() {
      WindowInsetsCompat var1 = this.lastInsets;
      int var2;
      if (var1 != null) {
         var2 = var1.getSystemWindowInsetTop();
      } else {
         var2 = 0;
      }

      return var2;
   }

   public final int getTotalScrollRange() {
      int var1 = this.totalScrollRange;
      if (var1 != -1) {
         return var1;
      } else {
         int var2 = this.getChildCount();
         int var3 = 0;
         var1 = 0;

         int var4;
         while(true) {
            var4 = var1;
            if (var3 >= var2) {
               break;
            }

            View var5 = this.getChildAt(var3);
            AppBarLayout.LayoutParams var6 = (AppBarLayout.LayoutParams)var5.getLayoutParams();
            int var7 = var5.getMeasuredHeight();
            int var8 = var6.scrollFlags;
            var4 = var1;
            if ((var8 & 1) == 0) {
               break;
            }

            var4 = var1 + var7 + var6.topMargin + var6.bottomMargin;
            var1 = var4;
            if (var3 == 0) {
               var1 = var4;
               if (ViewCompat.getFitsSystemWindows(var5)) {
                  var1 = var4 - this.getTopInset();
               }
            }

            if ((var8 & 2) != 0) {
               var4 = var1 - ViewCompat.getMinimumHeight(var5);
               break;
            }

            ++var3;
         }

         var1 = Math.max(0, var4);
         this.totalScrollRange = var1;
         return var1;
      }
   }

   int getUpNestedPreScrollRange() {
      return this.getTotalScrollRange();
   }

   boolean hasChildWithInterpolator() {
      return this.haveChildWithInterpolator;
   }

   boolean hasScrollableChildren() {
      boolean var1;
      if (this.getTotalScrollRange() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isLiftOnScroll() {
      return this.liftOnScroll;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      MaterialShapeUtils.setParentAbsoluteElevation(this);
   }

   protected int[] onCreateDrawableState(int var1) {
      if (this.tmpStatesArray == null) {
         this.tmpStatesArray = new int[4];
      }

      int[] var2 = this.tmpStatesArray;
      int[] var3 = super.onCreateDrawableState(var1 + var2.length);
      if (this.liftable) {
         var1 = R.attr.state_liftable;
      } else {
         var1 = -R.attr.state_liftable;
      }

      var2[0] = var1;
      if (this.liftable && this.lifted) {
         var1 = R.attr.state_lifted;
      } else {
         var1 = -R.attr.state_lifted;
      }

      var2[1] = var1;
      if (this.liftable) {
         var1 = R.attr.state_collapsible;
      } else {
         var1 = -R.attr.state_collapsible;
      }

      var2[2] = var1;
      if (this.liftable && this.lifted) {
         var1 = R.attr.state_collapsed;
      } else {
         var1 = -R.attr.state_collapsed;
      }

      var2[3] = var1;
      return mergeDrawableStates(var3, var2);
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.clearLiftOnScrollTargetView();
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      var1 = ViewCompat.getFitsSystemWindows(this);
      boolean var6 = true;
      if (var1 && this.shouldOffsetFirstChild()) {
         var3 = this.getTopInset();

         for(var2 = this.getChildCount() - 1; var2 >= 0; --var2) {
            ViewCompat.offsetTopAndBottom(this.getChildAt(var2), var3);
         }
      }

      this.invalidateScrollRanges();
      this.haveChildWithInterpolator = false;
      var3 = this.getChildCount();

      for(var2 = 0; var2 < var3; ++var2) {
         if (((AppBarLayout.LayoutParams)this.getChildAt(var2).getLayoutParams()).getScrollInterpolator() != null) {
            this.haveChildWithInterpolator = true;
            break;
         }
      }

      Drawable var7 = this.statusBarForeground;
      if (var7 != null) {
         var7.setBounds(0, 0, this.getWidth(), this.getTopInset());
      }

      if (!this.liftableOverride) {
         var1 = var6;
         if (!this.liftOnScroll) {
            if (this.hasCollapsibleChild()) {
               var1 = var6;
            } else {
               var1 = false;
            }
         }

         this.setLiftableState(var1);
      }

   }

   protected void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      int var3 = MeasureSpec.getMode(var2);
      if (var3 != 1073741824 && ViewCompat.getFitsSystemWindows(this) && this.shouldOffsetFirstChild()) {
         var1 = this.getMeasuredHeight();
         if (var3 != Integer.MIN_VALUE) {
            if (var3 == 0) {
               var1 += this.getTopInset();
            }
         } else {
            var1 = MathUtils.clamp(this.getMeasuredHeight() + this.getTopInset(), 0, MeasureSpec.getSize(var2));
         }

         this.setMeasuredDimension(this.getMeasuredWidth(), var1);
      }

      this.invalidateScrollRanges();
   }

   void onOffsetChanged(int var1) {
      this.currentOffset = var1;
      if (!this.willNotDraw()) {
         ViewCompat.postInvalidateOnAnimation(this);
      }

      List var2 = this.listeners;
      if (var2 != null) {
         int var3 = 0;

         for(int var4 = var2.size(); var3 < var4; ++var3) {
            AppBarLayout.BaseOnOffsetChangedListener var5 = (AppBarLayout.BaseOnOffsetChangedListener)this.listeners.get(var3);
            if (var5 != null) {
               var5.onOffsetChanged(this, var1);
            }
         }
      }

   }

   WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat var1) {
      WindowInsetsCompat var2;
      if (ViewCompat.getFitsSystemWindows(this)) {
         var2 = var1;
      } else {
         var2 = null;
      }

      if (!ObjectsCompat.equals(this.lastInsets, var2)) {
         this.lastInsets = var2;
         this.updateWillNotDraw();
         this.requestLayout();
      }

      return var1;
   }

   public void removeOnOffsetChangedListener(AppBarLayout.BaseOnOffsetChangedListener var1) {
      List var2 = this.listeners;
      if (var2 != null && var1 != null) {
         var2.remove(var1);
      }

   }

   public void removeOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener var1) {
      this.removeOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener)var1);
   }

   void resetPendingAction() {
      this.pendingAction = 0;
   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      MaterialShapeUtils.setElevation(this, var1);
   }

   public void setExpanded(boolean var1) {
      this.setExpanded(var1, ViewCompat.isLaidOut(this));
   }

   public void setExpanded(boolean var1, boolean var2) {
      this.setExpanded(var1, var2, true);
   }

   public void setLiftOnScroll(boolean var1) {
      this.liftOnScroll = var1;
   }

   public void setLiftOnScrollTargetViewId(int var1) {
      this.liftOnScrollTargetViewId = var1;
      this.clearLiftOnScrollTargetView();
   }

   public boolean setLiftable(boolean var1) {
      this.liftableOverride = true;
      return this.setLiftableState(var1);
   }

   public boolean setLifted(boolean var1) {
      return this.setLiftedState(var1);
   }

   boolean setLiftedState(boolean var1) {
      if (this.lifted != var1) {
         this.lifted = var1;
         this.refreshDrawableState();
         if (this.liftOnScroll && this.getBackground() instanceof MaterialShapeDrawable) {
            this.startLiftOnScrollElevationOverlayAnimation((MaterialShapeDrawable)this.getBackground(), var1);
         }

         return true;
      } else {
         return false;
      }
   }

   public void setOrientation(int var1) {
      if (var1 == 1) {
         super.setOrientation(var1);
      } else {
         throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
      }
   }

   public void setStatusBarForeground(Drawable var1) {
      Drawable var2 = this.statusBarForeground;
      if (var2 != var1) {
         Drawable var3 = null;
         if (var2 != null) {
            var2.setCallback((Callback)null);
         }

         if (var1 != null) {
            var3 = var1.mutate();
         }

         this.statusBarForeground = var3;
         if (var3 != null) {
            if (var3.isStateful()) {
               this.statusBarForeground.setState(this.getDrawableState());
            }

            DrawableCompat.setLayoutDirection(this.statusBarForeground, ViewCompat.getLayoutDirection(this));
            var1 = this.statusBarForeground;
            boolean var4;
            if (this.getVisibility() == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            var1.setVisible(var4, false);
            this.statusBarForeground.setCallback(this);
         }

         this.updateWillNotDraw();
         ViewCompat.postInvalidateOnAnimation(this);
      }

   }

   public void setStatusBarForegroundColor(int var1) {
      this.setStatusBarForeground(new ColorDrawable(var1));
   }

   public void setStatusBarForegroundResource(int var1) {
      this.setStatusBarForeground(AppCompatResources.getDrawable(this.getContext(), var1));
   }

   @Deprecated
   public void setTargetElevation(float var1) {
      if (VERSION.SDK_INT >= 21) {
         ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, var1);
      }

   }

   public void setVisibility(int var1) {
      super.setVisibility(var1);
      boolean var2;
      if (var1 == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Drawable var3 = this.statusBarForeground;
      if (var3 != null) {
         var3.setVisible(var2, false);
      }

   }

   boolean shouldLift(View var1) {
      View var2 = this.findLiftOnScrollTargetView(var1);
      if (var2 != null) {
         var1 = var2;
      }

      boolean var3;
      if (var1 == null || !var1.canScrollVertically(-1) && var1.getScrollY() <= 0) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   protected boolean verifyDrawable(Drawable var1) {
      boolean var2;
      if (!super.verifyDrawable(var1) && var1 != this.statusBarForeground) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   protected static class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior<T> {
      private static final int INVALID_POSITION = -1;
      private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
      private WeakReference<View> lastNestedScrollingChildRef;
      private int lastStartedType;
      private ValueAnimator offsetAnimator;
      private int offsetDelta;
      private int offsetToChildIndexOnLayout = -1;
      private boolean offsetToChildIndexOnLayoutIsMinHeight;
      private float offsetToChildIndexOnLayoutPerc;
      private AppBarLayout.BaseBehavior.BaseDragCallback onDragCallback;

      public BaseBehavior() {
      }

      public BaseBehavior(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      private void addAccessibilityScrollActions(final CoordinatorLayout var1, final T var2, final View var3) {
         if (this.getTopBottomOffsetForScrollingSibling() != -var2.getTotalScrollRange() && var3.canScrollVertically(1)) {
            this.addActionToExpand(var1, var2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, false);
         }

         if (this.getTopBottomOffsetForScrollingSibling() != 0) {
            if (var3.canScrollVertically(-1)) {
               final int var4 = -var2.getDownNestedPreScrollRange();
               if (var4 != 0) {
                  ViewCompat.replaceAccessibilityAction(var1, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, (CharSequence)null, new AccessibilityViewCommand() {
                     public boolean perform(View var1x, AccessibilityViewCommand.CommandArguments var2x) {
                        BaseBehavior.this.onNestedPreScroll(var1, (AppBarLayout)var2, var3, 0, var4, new int[]{0, 0}, 1);
                        return true;
                     }
                  });
               }
            } else {
               this.addActionToExpand(var1, var2, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, true);
            }
         }

      }

      private void addActionToExpand(CoordinatorLayout var1, final T var2, AccessibilityNodeInfoCompat.AccessibilityActionCompat var3, final boolean var4) {
         ViewCompat.replaceAccessibilityAction(var1, var3, (CharSequence)null, new AccessibilityViewCommand() {
            public boolean perform(View var1, AccessibilityViewCommand.CommandArguments var2x) {
               var2.setExpanded(var4);
               return true;
            }
         });
      }

      private void animateOffsetTo(CoordinatorLayout var1, T var2, int var3, float var4) {
         int var5 = Math.abs(this.getTopBottomOffsetForScrollingSibling() - var3);
         var4 = Math.abs(var4);
         if (var4 > 0.0F) {
            var5 = Math.round((float)var5 / var4 * 1000.0F) * 3;
         } else {
            var5 = (int)(((float)var5 / (float)var2.getHeight() + 1.0F) * 150.0F);
         }

         this.animateOffsetWithDuration(var1, var2, var3, var5);
      }

      private void animateOffsetWithDuration(final CoordinatorLayout var1, final T var2, int var3, int var4) {
         int var5 = this.getTopBottomOffsetForScrollingSibling();
         if (var5 == var3) {
            ValueAnimator var7 = this.offsetAnimator;
            if (var7 != null && var7.isRunning()) {
               this.offsetAnimator.cancel();
            }

         } else {
            ValueAnimator var6 = this.offsetAnimator;
            if (var6 == null) {
               var6 = new ValueAnimator();
               this.offsetAnimator = var6;
               var6.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
               this.offsetAnimator.addUpdateListener(new AnimatorUpdateListener() {
                  public void onAnimationUpdate(ValueAnimator var1x) {
                     BaseBehavior.this.setHeaderTopBottomOffset(var1, var2, (Integer)var1x.getAnimatedValue());
                  }
               });
            } else {
               var6.cancel();
            }

            this.offsetAnimator.setDuration((long)Math.min(var4, 600));
            this.offsetAnimator.setIntValues(new int[]{var5, var3});
            this.offsetAnimator.start();
         }
      }

      private boolean canScrollChildren(CoordinatorLayout var1, T var2, View var3) {
         boolean var4;
         if (var2.hasScrollableChildren() && var1.getHeight() - var3.getHeight() <= var2.getHeight()) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      private static boolean checkFlag(int var0, int var1) {
         boolean var2;
         if ((var0 & var1) == var1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      private View findFirstScrollingChild(CoordinatorLayout var1) {
         int var2 = var1.getChildCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = var1.getChildAt(var3);
            if (var4 instanceof NestedScrollingChild || var4 instanceof ListView || var4 instanceof ScrollView) {
               return var4;
            }
         }

         return null;
      }

      private static View getAppBarChildOnOffset(AppBarLayout var0, int var1) {
         int var2 = Math.abs(var1);
         int var3 = var0.getChildCount();

         for(var1 = 0; var1 < var3; ++var1) {
            View var4 = var0.getChildAt(var1);
            if (var2 >= var4.getTop() && var2 <= var4.getBottom()) {
               return var4;
            }
         }

         return null;
      }

      private int getChildIndexOnOffset(T var1, int var2) {
         int var3 = var1.getChildCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            View var5 = var1.getChildAt(var4);
            int var6 = var5.getTop();
            int var7 = var5.getBottom();
            AppBarLayout.LayoutParams var10 = (AppBarLayout.LayoutParams)var5.getLayoutParams();
            int var8 = var6;
            int var9 = var7;
            if (checkFlag(var10.getScrollFlags(), 32)) {
               var8 = var6 - var10.topMargin;
               var9 = var7 + var10.bottomMargin;
            }

            var7 = -var2;
            if (var8 <= var7 && var9 >= var7) {
               return var4;
            }
         }

         return -1;
      }

      private int interpolateOffset(T var1, int var2) {
         int var3 = Math.abs(var2);
         int var4 = var1.getChildCount();
         byte var5 = 0;

         for(int var6 = 0; var6 < var4; ++var6) {
            View var7 = var1.getChildAt(var6);
            AppBarLayout.LayoutParams var8 = (AppBarLayout.LayoutParams)var7.getLayoutParams();
            Interpolator var9 = var8.getScrollInterpolator();
            if (var3 >= var7.getTop() && var3 <= var7.getBottom()) {
               if (var9 != null) {
                  var4 = var8.getScrollFlags();
                  var6 = var5;
                  int var11;
                  if ((var4 & 1) != 0) {
                     var11 = 0 + var7.getHeight() + var8.topMargin + var8.bottomMargin;
                     var6 = var11;
                     if ((var4 & 2) != 0) {
                        var6 = var11 - ViewCompat.getMinimumHeight(var7);
                     }
                  }

                  var11 = var6;
                  if (ViewCompat.getFitsSystemWindows(var7)) {
                     var11 = var6 - var1.getTopInset();
                  }

                  if (var11 > 0) {
                     var6 = var7.getTop();
                     float var10 = (float)var11;
                     var6 = Math.round(var10 * var9.getInterpolation((float)(var3 - var6) / var10));
                     return Integer.signum(var2) * (var7.getTop() + var6);
                  }
               }
               break;
            }
         }

         return var2;
      }

      private boolean shouldJumpElevationState(CoordinatorLayout var1, T var2) {
         List var7 = var1.getDependents(var2);
         int var3 = var7.size();
         boolean var4 = false;

         for(int var5 = 0; var5 < var3; ++var5) {
            CoordinatorLayout.Behavior var6 = ((CoordinatorLayout.LayoutParams)((View)var7.get(var5)).getLayoutParams()).getBehavior();
            if (var6 instanceof AppBarLayout.ScrollingViewBehavior) {
               if (((AppBarLayout.ScrollingViewBehavior)var6).getOverlayTop() != 0) {
                  var4 = true;
               }

               return var4;
            }
         }

         return false;
      }

      private void snapToChildIfNeeded(CoordinatorLayout var1, T var2) {
         int var3 = this.getTopBottomOffsetForScrollingSibling();
         int var4 = this.getChildIndexOnOffset(var2, var3);
         if (var4 >= 0) {
            View var5 = var2.getChildAt(var4);
            AppBarLayout.LayoutParams var6 = (AppBarLayout.LayoutParams)var5.getLayoutParams();
            int var7 = var6.getScrollFlags();
            if ((var7 & 17) == 17) {
               int var8 = -var5.getTop();
               int var9 = -var5.getBottom();
               int var10 = var9;
               if (var4 == var2.getChildCount() - 1) {
                  var10 = var9 + var2.getTopInset();
               }

               if (checkFlag(var7, 2)) {
                  var9 = var10 + ViewCompat.getMinimumHeight(var5);
                  var4 = var8;
               } else {
                  var4 = var8;
                  var9 = var10;
                  if (checkFlag(var7, 5)) {
                     var9 = ViewCompat.getMinimumHeight(var5) + var10;
                     if (var3 < var9) {
                        var4 = var9;
                        var9 = var10;
                     } else {
                        var4 = var8;
                     }
                  }
               }

               var8 = var4;
               var10 = var9;
               if (checkFlag(var7, 32)) {
                  var8 = var4 + var6.topMargin;
                  var10 = var9 - var6.bottomMargin;
               }

               var9 = var8;
               if (var3 < (var10 + var8) / 2) {
                  var9 = var10;
               }

               this.animateOffsetTo(var1, var2, MathUtils.clamp(var9, -var2.getTotalScrollRange(), 0), 0.0F);
            }
         }

      }

      private void updateAccessibilityActions(CoordinatorLayout var1, T var2) {
         ViewCompat.removeAccessibilityAction(var1, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
         ViewCompat.removeAccessibilityAction(var1, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
         View var3 = this.findFirstScrollingChild(var1);
         if (var3 != null && var2.getTotalScrollRange() != 0) {
            if (!(((CoordinatorLayout.LayoutParams)var3.getLayoutParams()).getBehavior() instanceof AppBarLayout.ScrollingViewBehavior)) {
               return;
            }

            this.addAccessibilityScrollActions(var1, var2, var3);
         }

      }

      private void updateAppBarLayoutDrawableState(CoordinatorLayout var1, T var2, int var3, int var4, boolean var5) {
         View var6 = getAppBarChildOnOffset(var2, var3);
         if (var6 != null) {
            int var7 = ((AppBarLayout.LayoutParams)var6.getLayoutParams()).getScrollFlags();
            boolean var8 = false;
            boolean var9 = var8;
            if ((var7 & 1) != 0) {
               label49: {
                  int var10 = ViewCompat.getMinimumHeight(var6);
                  if (var4 > 0 && (var7 & 12) != 0) {
                     var9 = var8;
                     if (-var3 < var6.getBottom() - var10 - var2.getTopInset()) {
                        break label49;
                     }
                  } else {
                     var9 = var8;
                     if ((var7 & 2) == 0) {
                        break label49;
                     }

                     var9 = var8;
                     if (-var3 < var6.getBottom() - var10 - var2.getTopInset()) {
                        break label49;
                     }
                  }

                  var9 = true;
               }
            }

            if (var2.isLiftOnScroll()) {
               var9 = var2.shouldLift(this.findFirstScrollingChild(var1));
            }

            var9 = var2.setLiftedState(var9);
            if (var5 || var9 && this.shouldJumpElevationState(var1, var2)) {
               var2.jumpDrawablesToCurrentState();
            }
         }

      }

      boolean canDragView(T var1) {
         AppBarLayout.BaseBehavior.BaseDragCallback var2 = this.onDragCallback;
         if (var2 != null) {
            return var2.canDrag(var1);
         } else {
            WeakReference var5 = this.lastNestedScrollingChildRef;
            boolean var3 = true;
            boolean var4 = var3;
            if (var5 != null) {
               View var6 = (View)var5.get();
               if (var6 != null && var6.isShown() && !var6.canScrollVertically(-1)) {
                  var4 = var3;
               } else {
                  var4 = false;
               }
            }

            return var4;
         }
      }

      int getMaxDragOffset(T var1) {
         return -var1.getDownNestedScrollRange();
      }

      int getScrollRangeForDragFling(T var1) {
         return var1.getTotalScrollRange();
      }

      int getTopBottomOffsetForScrollingSibling() {
         return this.getTopAndBottomOffset() + this.offsetDelta;
      }

      boolean isOffsetAnimatorRunning() {
         ValueAnimator var1 = this.offsetAnimator;
         boolean var2;
         if (var1 != null && var1.isRunning()) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      void onFlingFinished(CoordinatorLayout var1, T var2) {
         this.snapToChildIfNeeded(var1, var2);
         if (var2.isLiftOnScroll()) {
            var2.setLiftedState(var2.shouldLift(this.findFirstScrollingChild(var1)));
         }

      }

      public boolean onLayoutChild(CoordinatorLayout var1, T var2, int var3) {
         boolean var4 = super.onLayoutChild(var1, var2, var3);
         int var5 = var2.getPendingAction();
         var3 = this.offsetToChildIndexOnLayout;
         if (var3 >= 0 && (var5 & 8) == 0) {
            View var6 = var2.getChildAt(var3);
            var5 = -var6.getBottom();
            if (this.offsetToChildIndexOnLayoutIsMinHeight) {
               var3 = ViewCompat.getMinimumHeight(var6) + var2.getTopInset();
            } else {
               var3 = Math.round((float)var6.getHeight() * this.offsetToChildIndexOnLayoutPerc);
            }

            this.setHeaderTopBottomOffset(var1, var2, var5 + var3);
         } else if (var5 != 0) {
            boolean var7;
            if ((var5 & 4) != 0) {
               var7 = true;
            } else {
               var7 = false;
            }

            if ((var5 & 2) != 0) {
               var5 = -var2.getUpNestedPreScrollRange();
               if (var7) {
                  this.animateOffsetTo(var1, var2, var5, 0.0F);
               } else {
                  this.setHeaderTopBottomOffset(var1, var2, var5);
               }
            } else if ((var5 & 1) != 0) {
               if (var7) {
                  this.animateOffsetTo(var1, var2, 0, 0.0F);
               } else {
                  this.setHeaderTopBottomOffset(var1, var2, 0);
               }
            }
         }

         var2.resetPendingAction();
         this.offsetToChildIndexOnLayout = -1;
         this.setTopAndBottomOffset(MathUtils.clamp(this.getTopAndBottomOffset(), -var2.getTotalScrollRange(), 0));
         this.updateAppBarLayoutDrawableState(var1, var2, this.getTopAndBottomOffset(), 0, true);
         var2.onOffsetChanged(this.getTopAndBottomOffset());
         this.updateAccessibilityActions(var1, var2);
         return var4;
      }

      public boolean onMeasureChild(CoordinatorLayout var1, T var2, int var3, int var4, int var5, int var6) {
         if (((CoordinatorLayout.LayoutParams)var2.getLayoutParams()).height == -2) {
            var1.onMeasureChild(var2, var3, var4, MeasureSpec.makeMeasureSpec(0, 0), var6);
            return true;
         } else {
            return super.onMeasureChild(var1, var2, var3, var4, var5, var6);
         }
      }

      public void onNestedPreScroll(CoordinatorLayout var1, T var2, View var3, int var4, int var5, int[] var6, int var7) {
         if (var5 != 0) {
            if (var5 < 0) {
               var7 = -var2.getTotalScrollRange();
               int var8 = var2.getDownNestedPreScrollRange();
               var4 = var7;
               var7 += var8;
            } else {
               var4 = -var2.getUpNestedPreScrollRange();
               var7 = 0;
            }

            if (var4 != var7) {
               var6[1] = this.scroll(var1, var2, var5, var4, var7);
            }
         }

         if (var2.isLiftOnScroll()) {
            var2.setLiftedState(var2.shouldLift(var3));
         }

      }

      public void onNestedScroll(CoordinatorLayout var1, T var2, View var3, int var4, int var5, int var6, int var7, int var8, int[] var9) {
         if (var7 < 0) {
            var9[1] = this.scroll(var1, var2, var7, -var2.getDownNestedScrollRange(), 0);
         }

         if (var7 == 0) {
            this.updateAccessibilityActions(var1, var2);
         }

      }

      public void onRestoreInstanceState(CoordinatorLayout var1, T var2, Parcelable var3) {
         if (var3 instanceof AppBarLayout.BaseBehavior.SavedState) {
            AppBarLayout.BaseBehavior.SavedState var4 = (AppBarLayout.BaseBehavior.SavedState)var3;
            super.onRestoreInstanceState(var1, var2, var4.getSuperState());
            this.offsetToChildIndexOnLayout = var4.firstVisibleChildIndex;
            this.offsetToChildIndexOnLayoutPerc = var4.firstVisibleChildPercentageShown;
            this.offsetToChildIndexOnLayoutIsMinHeight = var4.firstVisibleChildAtMinimumHeight;
         } else {
            super.onRestoreInstanceState(var1, var2, var3);
            this.offsetToChildIndexOnLayout = -1;
         }

      }

      public Parcelable onSaveInstanceState(CoordinatorLayout var1, T var2) {
         Parcelable var3 = super.onSaveInstanceState(var1, var2);
         int var4 = this.getTopAndBottomOffset();
         int var5 = var2.getChildCount();
         boolean var6 = false;

         for(int var7 = 0; var7 < var5; ++var7) {
            View var9 = var2.getChildAt(var7);
            int var8 = var9.getBottom() + var4;
            if (var9.getTop() + var4 <= 0 && var8 >= 0) {
               AppBarLayout.BaseBehavior.SavedState var10 = new AppBarLayout.BaseBehavior.SavedState(var3);
               var10.firstVisibleChildIndex = var7;
               if (var8 == ViewCompat.getMinimumHeight(var9) + var2.getTopInset()) {
                  var6 = true;
               }

               var10.firstVisibleChildAtMinimumHeight = var6;
               var10.firstVisibleChildPercentageShown = (float)var8 / (float)var9.getHeight();
               return var10;
            }
         }

         return var3;
      }

      public boolean onStartNestedScroll(CoordinatorLayout var1, T var2, View var3, View var4, int var5, int var6) {
         boolean var7;
         if ((var5 & 2) == 0 || !var2.isLiftOnScroll() && !this.canScrollChildren(var1, var2, var3)) {
            var7 = false;
         } else {
            var7 = true;
         }

         if (var7) {
            ValueAnimator var8 = this.offsetAnimator;
            if (var8 != null) {
               var8.cancel();
            }
         }

         this.lastNestedScrollingChildRef = null;
         this.lastStartedType = var6;
         return var7;
      }

      public void onStopNestedScroll(CoordinatorLayout var1, T var2, View var3, int var4) {
         if (this.lastStartedType == 0 || var4 == 1) {
            this.snapToChildIfNeeded(var1, var2);
            if (var2.isLiftOnScroll()) {
               var2.setLiftedState(var2.shouldLift(var3));
            }
         }

         this.lastNestedScrollingChildRef = new WeakReference(var3);
      }

      public void setDragCallback(AppBarLayout.BaseBehavior.BaseDragCallback var1) {
         this.onDragCallback = var1;
      }

      int setHeaderTopBottomOffset(CoordinatorLayout var1, T var2, int var3, int var4, int var5) {
         int var6 = this.getTopBottomOffsetForScrollingSibling();
         byte var7 = 0;
         if (var4 != 0 && var6 >= var4 && var6 <= var5) {
            var4 = MathUtils.clamp(var3, var4, var5);
            var3 = var7;
            if (var6 != var4) {
               if (var2.hasChildWithInterpolator()) {
                  var3 = this.interpolateOffset(var2, var4);
               } else {
                  var3 = var4;
               }

               boolean var8 = this.setTopAndBottomOffset(var3);
               var5 = var6 - var4;
               this.offsetDelta = var4 - var3;
               if (!var8 && var2.hasChildWithInterpolator()) {
                  var1.dispatchDependentViewsChanged(var2);
               }

               var2.onOffsetChanged(this.getTopAndBottomOffset());
               byte var9;
               if (var4 < var6) {
                  var9 = -1;
               } else {
                  var9 = 1;
               }

               this.updateAppBarLayoutDrawableState(var1, var2, var4, var9, false);
               var3 = var5;
            }
         } else {
            this.offsetDelta = 0;
            var3 = var7;
         }

         this.updateAccessibilityActions(var1, var2);
         return var3;
      }

      public abstract static class BaseDragCallback<T extends AppBarLayout> {
         public abstract boolean canDrag(T var1);
      }

      protected static class SavedState extends AbsSavedState {
         public static final Creator<AppBarLayout.BaseBehavior.SavedState> CREATOR = new ClassLoaderCreator<AppBarLayout.BaseBehavior.SavedState>() {
            public AppBarLayout.BaseBehavior.SavedState createFromParcel(Parcel var1) {
               return new AppBarLayout.BaseBehavior.SavedState(var1, (ClassLoader)null);
            }

            public AppBarLayout.BaseBehavior.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
               return new AppBarLayout.BaseBehavior.SavedState(var1, var2);
            }

            public AppBarLayout.BaseBehavior.SavedState[] newArray(int var1) {
               return new AppBarLayout.BaseBehavior.SavedState[var1];
            }
         };
         boolean firstVisibleChildAtMinimumHeight;
         int firstVisibleChildIndex;
         float firstVisibleChildPercentageShown;

         public SavedState(Parcel var1, ClassLoader var2) {
            super(var1, var2);
            this.firstVisibleChildIndex = var1.readInt();
            this.firstVisibleChildPercentageShown = var1.readFloat();
            boolean var3;
            if (var1.readByte() != 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            this.firstVisibleChildAtMinimumHeight = var3;
         }

         public SavedState(Parcelable var1) {
            super(var1);
         }

         public void writeToParcel(Parcel var1, int var2) {
            super.writeToParcel(var1, var2);
            var1.writeInt(this.firstVisibleChildIndex);
            var1.writeFloat(this.firstVisibleChildPercentageShown);
            var1.writeByte((byte)this.firstVisibleChildAtMinimumHeight);
         }
      }
   }

   public interface BaseOnOffsetChangedListener<T extends AppBarLayout> {
      void onOffsetChanged(T var1, int var2);
   }

   public static class Behavior extends AppBarLayout.BaseBehavior<AppBarLayout> {
      public Behavior() {
      }

      public Behavior(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      public abstract static class DragCallback extends AppBarLayout.BaseBehavior.BaseDragCallback<AppBarLayout> {
      }
   }

   public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {
      static final int COLLAPSIBLE_FLAGS = 10;
      static final int FLAG_QUICK_RETURN = 5;
      static final int FLAG_SNAP = 17;
      public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
      public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
      public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
      public static final int SCROLL_FLAG_NO_SCROLL = 0;
      public static final int SCROLL_FLAG_SCROLL = 1;
      public static final int SCROLL_FLAG_SNAP = 16;
      public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
      int scrollFlags = 1;
      Interpolator scrollInterpolator;

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
      }

      public LayoutParams(int var1, int var2, float var3) {
         super(var1, var2, var3);
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.AppBarLayout_Layout);
         this.scrollFlags = var3.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
         if (var3.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
            this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(var1, var3.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
         }

         var3.recycle();
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }

      public LayoutParams(android.widget.LinearLayout.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(AppBarLayout.LayoutParams var1) {
         super(var1);
         this.scrollFlags = var1.scrollFlags;
         this.scrollInterpolator = var1.scrollInterpolator;
      }

      public int getScrollFlags() {
         return this.scrollFlags;
      }

      public Interpolator getScrollInterpolator() {
         return this.scrollInterpolator;
      }

      boolean isCollapsible() {
         int var1 = this.scrollFlags;
         boolean var2 = true;
         if ((var1 & 1) != 1 || (var1 & 10) == 0) {
            var2 = false;
         }

         return var2;
      }

      public void setScrollFlags(int var1) {
         this.scrollFlags = var1;
      }

      public void setScrollInterpolator(Interpolator var1) {
         this.scrollInterpolator = var1;
      }

      @Retention(RetentionPolicy.SOURCE)
      public @interface ScrollFlags {
      }
   }

   public interface OnOffsetChangedListener extends AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout> {
      void onOffsetChanged(AppBarLayout var1, int var2);
   }

   public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
      public ScrollingViewBehavior() {
      }

      public ScrollingViewBehavior(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.ScrollingViewBehavior_Layout);
         this.setOverlayTop(var3.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
         var3.recycle();
      }

      private static int getAppBarLayoutOffset(AppBarLayout var0) {
         CoordinatorLayout.Behavior var1 = ((CoordinatorLayout.LayoutParams)var0.getLayoutParams()).getBehavior();
         return var1 instanceof AppBarLayout.BaseBehavior ? ((AppBarLayout.BaseBehavior)var1).getTopBottomOffsetForScrollingSibling() : 0;
      }

      private void offsetChildAsNeeded(View var1, View var2) {
         CoordinatorLayout.Behavior var3 = ((CoordinatorLayout.LayoutParams)var2.getLayoutParams()).getBehavior();
         if (var3 instanceof AppBarLayout.BaseBehavior) {
            AppBarLayout.BaseBehavior var4 = (AppBarLayout.BaseBehavior)var3;
            ViewCompat.offsetTopAndBottom(var1, var2.getBottom() - var1.getTop() + var4.offsetDelta + this.getVerticalLayoutGap() - this.getOverlapPixelsForOffset(var2));
         }

      }

      private void updateLiftedStateIfNeeded(View var1, View var2) {
         if (var2 instanceof AppBarLayout) {
            AppBarLayout var3 = (AppBarLayout)var2;
            if (var3.isLiftOnScroll()) {
               var3.setLiftedState(var3.shouldLift(var1));
            }
         }

      }

      AppBarLayout findFirstDependency(List<View> var1) {
         int var2 = var1.size();

         for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = (View)var1.get(var3);
            if (var4 instanceof AppBarLayout) {
               return (AppBarLayout)var4;
            }
         }

         return null;
      }

      float getOverlapRatioForOffset(View var1) {
         if (var1 instanceof AppBarLayout) {
            AppBarLayout var5 = (AppBarLayout)var1;
            int var2 = var5.getTotalScrollRange();
            int var3 = var5.getDownNestedPreScrollRange();
            int var4 = getAppBarLayoutOffset(var5);
            if (var3 != 0 && var2 + var4 <= var3) {
               return 0.0F;
            }

            var3 = var2 - var3;
            if (var3 != 0) {
               return (float)var4 / (float)var3 + 1.0F;
            }
         }

         return 0.0F;
      }

      int getScrollRange(View var1) {
         return var1 instanceof AppBarLayout ? ((AppBarLayout)var1).getTotalScrollRange() : super.getScrollRange(var1);
      }

      public boolean layoutDependsOn(CoordinatorLayout var1, View var2, View var3) {
         return var3 instanceof AppBarLayout;
      }

      public boolean onDependentViewChanged(CoordinatorLayout var1, View var2, View var3) {
         this.offsetChildAsNeeded(var2, var3);
         this.updateLiftedStateIfNeeded(var2, var3);
         return false;
      }

      public void onDependentViewRemoved(CoordinatorLayout var1, View var2, View var3) {
         if (var3 instanceof AppBarLayout) {
            ViewCompat.removeAccessibilityAction(var1, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            ViewCompat.removeAccessibilityAction(var1, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
         }

      }

      public boolean onRequestChildRectangleOnScreen(CoordinatorLayout var1, View var2, Rect var3, boolean var4) {
         AppBarLayout var5 = this.findFirstDependency(var1.getDependencies(var2));
         if (var5 != null) {
            var3.offset(var2.getLeft(), var2.getTop());
            Rect var6 = this.tempRect1;
            var6.set(0, 0, var1.getWidth(), var1.getHeight());
            if (!var6.contains(var3)) {
               var5.setExpanded(false, var4 ^ true);
               return true;
            }
         }

         return false;
      }
   }
}
