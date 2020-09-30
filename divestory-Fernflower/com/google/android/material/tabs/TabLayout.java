package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Pools;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.DecorView
public class TabLayout extends HorizontalScrollView {
   private static final int ANIMATION_DURATION = 300;
   static final int DEFAULT_GAP_TEXT_ICON = 8;
   private static final int DEFAULT_HEIGHT = 48;
   private static final int DEFAULT_HEIGHT_WITH_TEXT_ICON = 72;
   private static final int DEF_STYLE_RES;
   static final int FIXED_WRAP_GUTTER_MIN = 16;
   public static final int GRAVITY_CENTER = 1;
   public static final int GRAVITY_FILL = 0;
   public static final int GRAVITY_START = 2;
   public static final int INDICATOR_GRAVITY_BOTTOM = 0;
   public static final int INDICATOR_GRAVITY_CENTER = 1;
   public static final int INDICATOR_GRAVITY_STRETCH = 3;
   public static final int INDICATOR_GRAVITY_TOP = 2;
   private static final int INVALID_WIDTH = -1;
   private static final String LOG_TAG = "TabLayout";
   private static final int MIN_INDICATOR_WIDTH = 24;
   public static final int MODE_AUTO = 2;
   public static final int MODE_FIXED = 1;
   public static final int MODE_SCROLLABLE = 0;
   public static final int TAB_LABEL_VISIBILITY_LABELED = 1;
   public static final int TAB_LABEL_VISIBILITY_UNLABELED = 0;
   private static final int TAB_MIN_WIDTH_MARGIN = 56;
   private static final Pools.Pool<TabLayout.Tab> tabPool;
   private TabLayout.AdapterChangeListener adapterChangeListener;
   private int contentInsetStart;
   private TabLayout.BaseOnTabSelectedListener currentVpSelectedListener;
   boolean inlineLabel;
   int mode;
   private TabLayout.TabLayoutOnPageChangeListener pageChangeListener;
   private PagerAdapter pagerAdapter;
   private DataSetObserver pagerAdapterObserver;
   private final int requestedTabMaxWidth;
   private final int requestedTabMinWidth;
   private ValueAnimator scrollAnimator;
   private final int scrollableTabMinWidth;
   private TabLayout.BaseOnTabSelectedListener selectedListener;
   private final ArrayList<TabLayout.BaseOnTabSelectedListener> selectedListeners;
   private TabLayout.Tab selectedTab;
   private boolean setupViewPagerImplicitly;
   final TabLayout.SlidingTabIndicator slidingTabIndicator;
   final int tabBackgroundResId;
   int tabGravity;
   ColorStateList tabIconTint;
   android.graphics.PorterDuff.Mode tabIconTintMode;
   int tabIndicatorAnimationDuration;
   boolean tabIndicatorFullWidth;
   int tabIndicatorGravity;
   int tabMaxWidth;
   int tabPaddingBottom;
   int tabPaddingEnd;
   int tabPaddingStart;
   int tabPaddingTop;
   ColorStateList tabRippleColorStateList;
   Drawable tabSelectedIndicator;
   int tabTextAppearance;
   ColorStateList tabTextColors;
   float tabTextMultiLineSize;
   float tabTextSize;
   private final RectF tabViewContentBounds;
   private final Pools.Pool<TabLayout.TabView> tabViewPool;
   private final ArrayList<TabLayout.Tab> tabs;
   boolean unboundedRipple;
   ViewPager viewPager;

   static {
      DEF_STYLE_RES = R.style.Widget_Design_TabLayout;
      tabPool = new Pools.SynchronizedPool(16);
   }

   public TabLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public TabLayout(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.tabStyle);
   }

   public TabLayout(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.tabs = new ArrayList();
      this.tabViewContentBounds = new RectF();
      this.tabMaxWidth = Integer.MAX_VALUE;
      this.selectedListeners = new ArrayList();
      this.tabViewPool = new Pools.SimplePool(12);
      var1 = this.getContext();
      this.setHorizontalScrollBarEnabled(false);
      TabLayout.SlidingTabIndicator var4 = new TabLayout.SlidingTabIndicator(var1);
      this.slidingTabIndicator = var4;
      super.addView(var4, 0, new LayoutParams(-2, -1));
      TypedArray var9 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.TabLayout, var3, DEF_STYLE_RES, R.styleable.TabLayout_tabTextAppearance);
      if (this.getBackground() instanceof ColorDrawable) {
         ColorDrawable var5 = (ColorDrawable)this.getBackground();
         MaterialShapeDrawable var10 = new MaterialShapeDrawable();
         var10.setFillColor(ColorStateList.valueOf(var5.getColor()));
         var10.initializeElevationOverlay(var1);
         var10.setElevation(ViewCompat.getElevation(this));
         ViewCompat.setBackground(this, var10);
      }

      this.slidingTabIndicator.setSelectedIndicatorHeight(var9.getDimensionPixelSize(R.styleable.TabLayout_tabIndicatorHeight, -1));
      this.slidingTabIndicator.setSelectedIndicatorColor(var9.getColor(R.styleable.TabLayout_tabIndicatorColor, 0));
      this.setSelectedTabIndicator(MaterialResources.getDrawable(var1, var9, R.styleable.TabLayout_tabIndicator));
      this.setSelectedTabIndicatorGravity(var9.getInt(R.styleable.TabLayout_tabIndicatorGravity, 0));
      this.setTabIndicatorFullWidth(var9.getBoolean(R.styleable.TabLayout_tabIndicatorFullWidth, true));
      var3 = var9.getDimensionPixelSize(R.styleable.TabLayout_tabPadding, 0);
      this.tabPaddingBottom = var3;
      this.tabPaddingEnd = var3;
      this.tabPaddingTop = var3;
      this.tabPaddingStart = var3;
      this.tabPaddingStart = var9.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingStart, this.tabPaddingStart);
      this.tabPaddingTop = var9.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.tabPaddingTop);
      this.tabPaddingEnd = var9.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingEnd, this.tabPaddingEnd);
      this.tabPaddingBottom = var9.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.tabPaddingBottom);
      var3 = var9.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
      this.tabTextAppearance = var3;
      TypedArray var11 = var1.obtainStyledAttributes(var3, androidx.appcompat.R.styleable.TextAppearance);

      try {
         this.tabTextSize = (float)var11.getDimensionPixelSize(androidx.appcompat.R.styleable.TextAppearance_android_textSize, 0);
         this.tabTextColors = MaterialResources.getColorStateList(var1, var11, androidx.appcompat.R.styleable.TextAppearance_android_textColor);
      } finally {
         var11.recycle();
      }

      if (var9.hasValue(R.styleable.TabLayout_tabTextColor)) {
         this.tabTextColors = MaterialResources.getColorStateList(var1, var9, R.styleable.TabLayout_tabTextColor);
      }

      if (var9.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
         var3 = var9.getColor(R.styleable.TabLayout_tabSelectedTextColor, 0);
         this.tabTextColors = createColorStateList(this.tabTextColors.getDefaultColor(), var3);
      }

      this.tabIconTint = MaterialResources.getColorStateList(var1, var9, R.styleable.TabLayout_tabIconTint);
      this.tabIconTintMode = ViewUtils.parseTintMode(var9.getInt(R.styleable.TabLayout_tabIconTintMode, -1), (android.graphics.PorterDuff.Mode)null);
      this.tabRippleColorStateList = MaterialResources.getColorStateList(var1, var9, R.styleable.TabLayout_tabRippleColor);
      this.tabIndicatorAnimationDuration = var9.getInt(R.styleable.TabLayout_tabIndicatorAnimationDuration, 300);
      this.requestedTabMinWidth = var9.getDimensionPixelSize(R.styleable.TabLayout_tabMinWidth, -1);
      this.requestedTabMaxWidth = var9.getDimensionPixelSize(R.styleable.TabLayout_tabMaxWidth, -1);
      this.tabBackgroundResId = var9.getResourceId(R.styleable.TabLayout_tabBackground, 0);
      this.contentInsetStart = var9.getDimensionPixelSize(R.styleable.TabLayout_tabContentStart, 0);
      this.mode = var9.getInt(R.styleable.TabLayout_tabMode, 1);
      this.tabGravity = var9.getInt(R.styleable.TabLayout_tabGravity, 0);
      this.inlineLabel = var9.getBoolean(R.styleable.TabLayout_tabInlineLabel, false);
      this.unboundedRipple = var9.getBoolean(R.styleable.TabLayout_tabUnboundedRipple, false);
      var9.recycle();
      Resources var8 = this.getResources();
      this.tabTextMultiLineSize = (float)var8.getDimensionPixelSize(R.dimen.design_tab_text_size_2line);
      this.scrollableTabMinWidth = var8.getDimensionPixelSize(R.dimen.design_tab_scrollable_min_width);
      this.applyModeAndGravity();
   }

   private void addTabFromItemView(TabItem var1) {
      TabLayout.Tab var2 = this.newTab();
      if (var1.text != null) {
         var2.setText(var1.text);
      }

      if (var1.icon != null) {
         var2.setIcon(var1.icon);
      }

      if (var1.customLayout != 0) {
         var2.setCustomView(var1.customLayout);
      }

      if (!TextUtils.isEmpty(var1.getContentDescription())) {
         var2.setContentDescription(var1.getContentDescription());
      }

      this.addTab(var2);
   }

   private void addTabView(TabLayout.Tab var1) {
      TabLayout.TabView var2 = var1.view;
      var2.setSelected(false);
      var2.setActivated(false);
      this.slidingTabIndicator.addView(var2, var1.getPosition(), this.createLayoutParamsForTabs());
   }

   private void addViewInternal(View var1) {
      if (var1 instanceof TabItem) {
         this.addTabFromItemView((TabItem)var1);
      } else {
         throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
      }
   }

   private void animateToTab(int var1) {
      if (var1 != -1) {
         if (this.getWindowToken() != null && ViewCompat.isLaidOut(this) && !this.slidingTabIndicator.childrenNeedLayout()) {
            int var2 = this.getScrollX();
            int var3 = this.calculateScrollXForTab(var1, 0.0F);
            if (var2 != var3) {
               this.ensureScrollAnimator();
               this.scrollAnimator.setIntValues(new int[]{var2, var3});
               this.scrollAnimator.start();
            }

            this.slidingTabIndicator.animateIndicatorToPosition(var1, this.tabIndicatorAnimationDuration);
         } else {
            this.setScrollPosition(var1, 0.0F, true);
         }
      }
   }

   private void applyGravityForModeScrollable(int var1) {
      if (var1 != 0) {
         if (var1 == 1) {
            this.slidingTabIndicator.setGravity(1);
            return;
         }

         if (var1 != 2) {
            return;
         }
      } else {
         Log.w("TabLayout", "MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead");
      }

      this.slidingTabIndicator.setGravity(8388611);
   }

   private void applyModeAndGravity() {
      int var1 = this.mode;
      if (var1 != 0 && var1 != 2) {
         var1 = 0;
      } else {
         var1 = Math.max(0, this.contentInsetStart - this.tabPaddingStart);
      }

      ViewCompat.setPaddingRelative(this.slidingTabIndicator, var1, 0, 0, 0);
      var1 = this.mode;
      if (var1 != 0) {
         if (var1 == 1 || var1 == 2) {
            if (this.tabGravity == 2) {
               Log.w("TabLayout", "GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead");
            }

            this.slidingTabIndicator.setGravity(1);
         }
      } else {
         this.applyGravityForModeScrollable(this.tabGravity);
      }

      this.updateTabViews(true);
   }

   private int calculateScrollXForTab(int var1, float var2) {
      int var3 = this.mode;
      int var4 = 0;
      if (var3 != 0 && var3 != 2) {
         return 0;
      } else {
         View var5 = this.slidingTabIndicator.getChildAt(var1);
         ++var1;
         View var6;
         if (var1 < this.slidingTabIndicator.getChildCount()) {
            var6 = this.slidingTabIndicator.getChildAt(var1);
         } else {
            var6 = null;
         }

         if (var5 != null) {
            var1 = var5.getWidth();
         } else {
            var1 = 0;
         }

         if (var6 != null) {
            var4 = var6.getWidth();
         }

         var3 = var5.getLeft() + var1 / 2 - this.getWidth() / 2;
         var1 = (int)((float)(var1 + var4) * 0.5F * var2);
         if (ViewCompat.getLayoutDirection(this) == 0) {
            var1 += var3;
         } else {
            var1 = var3 - var1;
         }

         return var1;
      }
   }

   private void configureTab(TabLayout.Tab var1, int var2) {
      var1.setPosition(var2);
      this.tabs.add(var2, var1);
      int var3 = this.tabs.size();

      while(true) {
         ++var2;
         if (var2 >= var3) {
            return;
         }

         ((TabLayout.Tab)this.tabs.get(var2)).setPosition(var2);
      }
   }

   private static ColorStateList createColorStateList(int var0, int var1) {
      return new ColorStateList(new int[][]{SELECTED_STATE_SET, EMPTY_STATE_SET}, new int[]{var1, var0});
   }

   private android.widget.LinearLayout.LayoutParams createLayoutParamsForTabs() {
      android.widget.LinearLayout.LayoutParams var1 = new android.widget.LinearLayout.LayoutParams(-2, -1);
      this.updateTabViewLayoutParams(var1);
      return var1;
   }

   private TabLayout.TabView createTabView(TabLayout.Tab var1) {
      Pools.Pool var2 = this.tabViewPool;
      TabLayout.TabView var4;
      if (var2 != null) {
         var4 = (TabLayout.TabView)var2.acquire();
      } else {
         var4 = null;
      }

      TabLayout.TabView var3 = var4;
      if (var4 == null) {
         var3 = new TabLayout.TabView(this.getContext());
      }

      var3.setTab(var1);
      var3.setFocusable(true);
      var3.setMinimumWidth(this.getTabMinWidth());
      if (TextUtils.isEmpty(var1.contentDesc)) {
         var3.setContentDescription(var1.text);
      } else {
         var3.setContentDescription(var1.contentDesc);
      }

      return var3;
   }

   private void dispatchTabReselected(TabLayout.Tab var1) {
      for(int var2 = this.selectedListeners.size() - 1; var2 >= 0; --var2) {
         ((TabLayout.BaseOnTabSelectedListener)this.selectedListeners.get(var2)).onTabReselected(var1);
      }

   }

   private void dispatchTabSelected(TabLayout.Tab var1) {
      for(int var2 = this.selectedListeners.size() - 1; var2 >= 0; --var2) {
         ((TabLayout.BaseOnTabSelectedListener)this.selectedListeners.get(var2)).onTabSelected(var1);
      }

   }

   private void dispatchTabUnselected(TabLayout.Tab var1) {
      for(int var2 = this.selectedListeners.size() - 1; var2 >= 0; --var2) {
         ((TabLayout.BaseOnTabSelectedListener)this.selectedListeners.get(var2)).onTabUnselected(var1);
      }

   }

   private void ensureScrollAnimator() {
      if (this.scrollAnimator == null) {
         ValueAnimator var1 = new ValueAnimator();
         this.scrollAnimator = var1;
         var1.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
         this.scrollAnimator.setDuration((long)this.tabIndicatorAnimationDuration);
         this.scrollAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator var1) {
               TabLayout.this.scrollTo((Integer)var1.getAnimatedValue(), 0);
            }
         });
      }

   }

   private int getDefaultHeight() {
      int var1 = this.tabs.size();
      boolean var2 = false;
      int var3 = 0;

      boolean var4;
      while(true) {
         var4 = var2;
         if (var3 >= var1) {
            break;
         }

         TabLayout.Tab var5 = (TabLayout.Tab)this.tabs.get(var3);
         if (var5 != null && var5.getIcon() != null && !TextUtils.isEmpty(var5.getText())) {
            var4 = true;
            break;
         }

         ++var3;
      }

      byte var6;
      if (var4 && !this.inlineLabel) {
         var6 = 72;
      } else {
         var6 = 48;
      }

      return var6;
   }

   private int getTabMinWidth() {
      int var1 = this.requestedTabMinWidth;
      if (var1 != -1) {
         return var1;
      } else {
         var1 = this.mode;
         if (var1 != 0 && var1 != 2) {
            var1 = 0;
         } else {
            var1 = this.scrollableTabMinWidth;
         }

         return var1;
      }
   }

   private int getTabScrollRange() {
      return Math.max(0, this.slidingTabIndicator.getWidth() - this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
   }

   private void removeTabViewAt(int var1) {
      TabLayout.TabView var2 = (TabLayout.TabView)this.slidingTabIndicator.getChildAt(var1);
      this.slidingTabIndicator.removeViewAt(var1);
      if (var2 != null) {
         var2.reset();
         this.tabViewPool.release(var2);
      }

      this.requestLayout();
   }

   private void setSelectedTabView(int var1) {
      int var2 = this.slidingTabIndicator.getChildCount();
      if (var1 < var2) {
         for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = this.slidingTabIndicator.getChildAt(var3);
            boolean var5 = true;
            boolean var6;
            if (var3 == var1) {
               var6 = true;
            } else {
               var6 = false;
            }

            var4.setSelected(var6);
            if (var3 == var1) {
               var6 = var5;
            } else {
               var6 = false;
            }

            var4.setActivated(var6);
         }
      }

   }

   private void setupWithViewPager(ViewPager var1, boolean var2, boolean var3) {
      ViewPager var4 = this.viewPager;
      if (var4 != null) {
         TabLayout.TabLayoutOnPageChangeListener var5 = this.pageChangeListener;
         if (var5 != null) {
            var4.removeOnPageChangeListener(var5);
         }

         TabLayout.AdapterChangeListener var6 = this.adapterChangeListener;
         if (var6 != null) {
            this.viewPager.removeOnAdapterChangeListener(var6);
         }
      }

      TabLayout.BaseOnTabSelectedListener var7 = this.currentVpSelectedListener;
      if (var7 != null) {
         this.removeOnTabSelectedListener(var7);
         this.currentVpSelectedListener = null;
      }

      if (var1 != null) {
         this.viewPager = var1;
         if (this.pageChangeListener == null) {
            this.pageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(this);
         }

         this.pageChangeListener.reset();
         var1.addOnPageChangeListener(this.pageChangeListener);
         TabLayout.ViewPagerOnTabSelectedListener var8 = new TabLayout.ViewPagerOnTabSelectedListener(var1);
         this.currentVpSelectedListener = var8;
         this.addOnTabSelectedListener((TabLayout.BaseOnTabSelectedListener)var8);
         PagerAdapter var9 = var1.getAdapter();
         if (var9 != null) {
            this.setPagerAdapter(var9, var2);
         }

         if (this.adapterChangeListener == null) {
            this.adapterChangeListener = new TabLayout.AdapterChangeListener();
         }

         this.adapterChangeListener.setAutoRefresh(var2);
         var1.addOnAdapterChangeListener(this.adapterChangeListener);
         this.setScrollPosition(var1.getCurrentItem(), 0.0F, true);
      } else {
         this.viewPager = null;
         this.setPagerAdapter((PagerAdapter)null, false);
      }

      this.setupViewPagerImplicitly = var3;
   }

   private void updateAllTabs() {
      int var1 = this.tabs.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         ((TabLayout.Tab)this.tabs.get(var2)).updateView();
      }

   }

   private void updateTabViewLayoutParams(android.widget.LinearLayout.LayoutParams var1) {
      if (this.mode == 1 && this.tabGravity == 0) {
         var1.width = 0;
         var1.weight = 1.0F;
      } else {
         var1.width = -2;
         var1.weight = 0.0F;
      }

   }

   @Deprecated
   public void addOnTabSelectedListener(TabLayout.BaseOnTabSelectedListener var1) {
      if (!this.selectedListeners.contains(var1)) {
         this.selectedListeners.add(var1);
      }

   }

   public void addOnTabSelectedListener(TabLayout.OnTabSelectedListener var1) {
      this.addOnTabSelectedListener((TabLayout.BaseOnTabSelectedListener)var1);
   }

   public void addTab(TabLayout.Tab var1) {
      this.addTab(var1, this.tabs.isEmpty());
   }

   public void addTab(TabLayout.Tab var1, int var2) {
      this.addTab(var1, var2, this.tabs.isEmpty());
   }

   public void addTab(TabLayout.Tab var1, int var2, boolean var3) {
      if (var1.parent == this) {
         this.configureTab(var1, var2);
         this.addTabView(var1);
         if (var3) {
            var1.select();
         }

      } else {
         throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
      }
   }

   public void addTab(TabLayout.Tab var1, boolean var2) {
      this.addTab(var1, this.tabs.size(), var2);
   }

   public void addView(View var1) {
      this.addViewInternal(var1);
   }

   public void addView(View var1, int var2) {
      this.addViewInternal(var1);
   }

   public void addView(View var1, int var2, android.view.ViewGroup.LayoutParams var3) {
      this.addViewInternal(var1);
   }

   public void addView(View var1, android.view.ViewGroup.LayoutParams var2) {
      this.addViewInternal(var1);
   }

   public void clearOnTabSelectedListeners() {
      this.selectedListeners.clear();
   }

   protected TabLayout.Tab createTabFromPool() {
      TabLayout.Tab var1 = (TabLayout.Tab)tabPool.acquire();
      TabLayout.Tab var2 = var1;
      if (var1 == null) {
         var2 = new TabLayout.Tab();
      }

      return var2;
   }

   public LayoutParams generateLayoutParams(AttributeSet var1) {
      return this.generateDefaultLayoutParams();
   }

   public int getSelectedTabPosition() {
      TabLayout.Tab var1 = this.selectedTab;
      int var2;
      if (var1 != null) {
         var2 = var1.getPosition();
      } else {
         var2 = -1;
      }

      return var2;
   }

   public TabLayout.Tab getTabAt(int var1) {
      TabLayout.Tab var2;
      if (var1 >= 0 && var1 < this.getTabCount()) {
         var2 = (TabLayout.Tab)this.tabs.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public int getTabCount() {
      return this.tabs.size();
   }

   public int getTabGravity() {
      return this.tabGravity;
   }

   public ColorStateList getTabIconTint() {
      return this.tabIconTint;
   }

   public int getTabIndicatorGravity() {
      return this.tabIndicatorGravity;
   }

   int getTabMaxWidth() {
      return this.tabMaxWidth;
   }

   public int getTabMode() {
      return this.mode;
   }

   public ColorStateList getTabRippleColor() {
      return this.tabRippleColorStateList;
   }

   public Drawable getTabSelectedIndicator() {
      return this.tabSelectedIndicator;
   }

   public ColorStateList getTabTextColors() {
      return this.tabTextColors;
   }

   public boolean hasUnboundedRipple() {
      return this.unboundedRipple;
   }

   public boolean isInlineLabel() {
      return this.inlineLabel;
   }

   public boolean isTabIndicatorFullWidth() {
      return this.tabIndicatorFullWidth;
   }

   public TabLayout.Tab newTab() {
      TabLayout.Tab var1 = this.createTabFromPool();
      var1.parent = this;
      var1.view = this.createTabView(var1);
      return var1;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      MaterialShapeUtils.setParentAbsoluteElevation(this);
      if (this.viewPager == null) {
         ViewParent var1 = this.getParent();
         if (var1 instanceof ViewPager) {
            this.setupWithViewPager((ViewPager)var1, true, true);
         }
      }

   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      if (this.setupViewPagerImplicitly) {
         this.setupWithViewPager((ViewPager)null);
         this.setupViewPagerImplicitly = false;
      }

   }

   protected void onDraw(Canvas var1) {
      for(int var2 = 0; var2 < this.slidingTabIndicator.getChildCount(); ++var2) {
         View var3 = this.slidingTabIndicator.getChildAt(var2);
         if (var3 instanceof TabLayout.TabView) {
            ((TabLayout.TabView)var3).drawBackground(var1);
         }
      }

      super.onDraw(var1);
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      AccessibilityNodeInfoCompat.wrap(var1).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.getTabCount(), false, 1));
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = Math.round(ViewUtils.dpToPx(this.getContext(), this.getDefaultHeight()));
      int var4 = MeasureSpec.getMode(var2);
      boolean var5 = false;
      if (var4 != Integer.MIN_VALUE) {
         if (var4 != 0) {
            var4 = var2;
         } else {
            var4 = MeasureSpec.makeMeasureSpec(var3 + this.getPaddingTop() + this.getPaddingBottom(), 1073741824);
         }
      } else {
         var4 = var2;
         if (this.getChildCount() == 1) {
            var4 = var2;
            if (MeasureSpec.getSize(var2) >= var3) {
               this.getChildAt(0).setMinimumHeight(var3);
               var4 = var2;
            }
         }
      }

      var3 = MeasureSpec.getSize(var1);
      if (MeasureSpec.getMode(var1) != 0) {
         var2 = this.requestedTabMaxWidth;
         if (var2 <= 0) {
            var2 = (int)((float)var3 - ViewUtils.dpToPx(this.getContext(), 56));
         }

         this.tabMaxWidth = var2;
      }

      super.onMeasure(var1, var4);
      if (this.getChildCount() == 1) {
         View var6;
         boolean var7;
         label46: {
            label45: {
               var6 = this.getChildAt(0);
               var1 = this.mode;
               if (var1 != 0) {
                  if (var1 == 1) {
                     var7 = var5;
                     if (var6.getMeasuredWidth() == this.getMeasuredWidth()) {
                        break label46;
                     }
                     break label45;
                  }

                  if (var1 != 2) {
                     var7 = var5;
                     break label46;
                  }
               }

               var7 = var5;
               if (var6.getMeasuredWidth() >= this.getMeasuredWidth()) {
                  break label46;
               }
            }

            var7 = true;
         }

         if (var7) {
            var1 = getChildMeasureSpec(var4, this.getPaddingTop() + this.getPaddingBottom(), var6.getLayoutParams().height);
            var6.measure(MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824), var1);
         }
      }

   }

   void populateFromPagerAdapter() {
      this.removeAllTabs();
      PagerAdapter var1 = this.pagerAdapter;
      if (var1 != null) {
         int var2 = var1.getCount();

         int var3;
         for(var3 = 0; var3 < var2; ++var3) {
            this.addTab(this.newTab().setText(this.pagerAdapter.getPageTitle(var3)), false);
         }

         ViewPager var4 = this.viewPager;
         if (var4 != null && var2 > 0) {
            var3 = var4.getCurrentItem();
            if (var3 != this.getSelectedTabPosition() && var3 < this.getTabCount()) {
               this.selectTab(this.getTabAt(var3));
            }
         }
      }

   }

   protected boolean releaseFromTabPool(TabLayout.Tab var1) {
      return tabPool.release(var1);
   }

   public void removeAllTabs() {
      for(int var1 = this.slidingTabIndicator.getChildCount() - 1; var1 >= 0; --var1) {
         this.removeTabViewAt(var1);
      }

      Iterator var2 = this.tabs.iterator();

      while(var2.hasNext()) {
         TabLayout.Tab var3 = (TabLayout.Tab)var2.next();
         var2.remove();
         var3.reset();
         this.releaseFromTabPool(var3);
      }

      this.selectedTab = null;
   }

   @Deprecated
   public void removeOnTabSelectedListener(TabLayout.BaseOnTabSelectedListener var1) {
      this.selectedListeners.remove(var1);
   }

   public void removeOnTabSelectedListener(TabLayout.OnTabSelectedListener var1) {
      this.removeOnTabSelectedListener((TabLayout.BaseOnTabSelectedListener)var1);
   }

   public void removeTab(TabLayout.Tab var1) {
      if (var1.parent == this) {
         this.removeTabAt(var1.getPosition());
      } else {
         throw new IllegalArgumentException("Tab does not belong to this TabLayout.");
      }
   }

   public void removeTabAt(int var1) {
      TabLayout.Tab var2 = this.selectedTab;
      int var3;
      if (var2 != null) {
         var3 = var2.getPosition();
      } else {
         var3 = 0;
      }

      this.removeTabViewAt(var1);
      var2 = (TabLayout.Tab)this.tabs.remove(var1);
      if (var2 != null) {
         var2.reset();
         this.releaseFromTabPool(var2);
      }

      int var4 = this.tabs.size();

      for(int var5 = var1; var5 < var4; ++var5) {
         ((TabLayout.Tab)this.tabs.get(var5)).setPosition(var5);
      }

      if (var3 == var1) {
         if (this.tabs.isEmpty()) {
            var2 = null;
         } else {
            var2 = (TabLayout.Tab)this.tabs.get(Math.max(0, var1 - 1));
         }

         this.selectTab(var2);
      }

   }

   public void selectTab(TabLayout.Tab var1) {
      this.selectTab(var1, true);
   }

   public void selectTab(TabLayout.Tab var1, boolean var2) {
      TabLayout.Tab var3 = this.selectedTab;
      if (var3 == var1) {
         if (var3 != null) {
            this.dispatchTabReselected(var1);
            this.animateToTab(var1.getPosition());
         }
      } else {
         int var4;
         if (var1 != null) {
            var4 = var1.getPosition();
         } else {
            var4 = -1;
         }

         if (var2) {
            if ((var3 == null || var3.getPosition() == -1) && var4 != -1) {
               this.setScrollPosition(var4, 0.0F, true);
            } else {
               this.animateToTab(var4);
            }

            if (var4 != -1) {
               this.setSelectedTabView(var4);
            }
         }

         this.selectedTab = var1;
         if (var3 != null) {
            this.dispatchTabUnselected(var3);
         }

         if (var1 != null) {
            this.dispatchTabSelected(var1);
         }
      }

   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      MaterialShapeUtils.setElevation(this, var1);
   }

   public void setInlineLabel(boolean var1) {
      if (this.inlineLabel != var1) {
         this.inlineLabel = var1;

         for(int var2 = 0; var2 < this.slidingTabIndicator.getChildCount(); ++var2) {
            View var3 = this.slidingTabIndicator.getChildAt(var2);
            if (var3 instanceof TabLayout.TabView) {
               ((TabLayout.TabView)var3).updateOrientation();
            }
         }

         this.applyModeAndGravity();
      }

   }

   public void setInlineLabelResource(int var1) {
      this.setInlineLabel(this.getResources().getBoolean(var1));
   }

   @Deprecated
   public void setOnTabSelectedListener(TabLayout.BaseOnTabSelectedListener var1) {
      TabLayout.BaseOnTabSelectedListener var2 = this.selectedListener;
      if (var2 != null) {
         this.removeOnTabSelectedListener(var2);
      }

      this.selectedListener = var1;
      if (var1 != null) {
         this.addOnTabSelectedListener(var1);
      }

   }

   @Deprecated
   public void setOnTabSelectedListener(TabLayout.OnTabSelectedListener var1) {
      this.setOnTabSelectedListener((TabLayout.BaseOnTabSelectedListener)var1);
   }

   void setPagerAdapter(PagerAdapter var1, boolean var2) {
      PagerAdapter var3 = this.pagerAdapter;
      if (var3 != null) {
         DataSetObserver var4 = this.pagerAdapterObserver;
         if (var4 != null) {
            var3.unregisterDataSetObserver(var4);
         }
      }

      this.pagerAdapter = var1;
      if (var2 && var1 != null) {
         if (this.pagerAdapterObserver == null) {
            this.pagerAdapterObserver = new TabLayout.PagerAdapterObserver();
         }

         var1.registerDataSetObserver(this.pagerAdapterObserver);
      }

      this.populateFromPagerAdapter();
   }

   void setScrollAnimatorListener(AnimatorListener var1) {
      this.ensureScrollAnimator();
      this.scrollAnimator.addListener(var1);
   }

   public void setScrollPosition(int var1, float var2, boolean var3) {
      this.setScrollPosition(var1, var2, var3, true);
   }

   public void setScrollPosition(int var1, float var2, boolean var3, boolean var4) {
      int var5 = Math.round((float)var1 + var2);
      if (var5 >= 0 && var5 < this.slidingTabIndicator.getChildCount()) {
         if (var4) {
            this.slidingTabIndicator.setIndicatorPositionFromTabPosition(var1, var2);
         }

         ValueAnimator var6 = this.scrollAnimator;
         if (var6 != null && var6.isRunning()) {
            this.scrollAnimator.cancel();
         }

         this.scrollTo(this.calculateScrollXForTab(var1, var2), 0);
         if (var3) {
            this.setSelectedTabView(var5);
         }
      }

   }

   public void setSelectedTabIndicator(int var1) {
      if (var1 != 0) {
         this.setSelectedTabIndicator(AppCompatResources.getDrawable(this.getContext(), var1));
      } else {
         this.setSelectedTabIndicator((Drawable)null);
      }

   }

   public void setSelectedTabIndicator(Drawable var1) {
      if (this.tabSelectedIndicator != var1) {
         this.tabSelectedIndicator = var1;
         ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
      }

   }

   public void setSelectedTabIndicatorColor(int var1) {
      this.slidingTabIndicator.setSelectedIndicatorColor(var1);
   }

   public void setSelectedTabIndicatorGravity(int var1) {
      if (this.tabIndicatorGravity != var1) {
         this.tabIndicatorGravity = var1;
         ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
      }

   }

   @Deprecated
   public void setSelectedTabIndicatorHeight(int var1) {
      this.slidingTabIndicator.setSelectedIndicatorHeight(var1);
   }

   public void setTabGravity(int var1) {
      if (this.tabGravity != var1) {
         this.tabGravity = var1;
         this.applyModeAndGravity();
      }

   }

   public void setTabIconTint(ColorStateList var1) {
      if (this.tabIconTint != var1) {
         this.tabIconTint = var1;
         this.updateAllTabs();
      }

   }

   public void setTabIconTintResource(int var1) {
      this.setTabIconTint(AppCompatResources.getColorStateList(this.getContext(), var1));
   }

   public void setTabIndicatorFullWidth(boolean var1) {
      this.tabIndicatorFullWidth = var1;
      ViewCompat.postInvalidateOnAnimation(this.slidingTabIndicator);
   }

   public void setTabMode(int var1) {
      if (var1 != this.mode) {
         this.mode = var1;
         this.applyModeAndGravity();
      }

   }

   public void setTabRippleColor(ColorStateList var1) {
      if (this.tabRippleColorStateList != var1) {
         this.tabRippleColorStateList = var1;

         for(int var2 = 0; var2 < this.slidingTabIndicator.getChildCount(); ++var2) {
            View var3 = this.slidingTabIndicator.getChildAt(var2);
            if (var3 instanceof TabLayout.TabView) {
               ((TabLayout.TabView)var3).updateBackgroundDrawable(this.getContext());
            }
         }
      }

   }

   public void setTabRippleColorResource(int var1) {
      this.setTabRippleColor(AppCompatResources.getColorStateList(this.getContext(), var1));
   }

   public void setTabTextColors(int var1, int var2) {
      this.setTabTextColors(createColorStateList(var1, var2));
   }

   public void setTabTextColors(ColorStateList var1) {
      if (this.tabTextColors != var1) {
         this.tabTextColors = var1;
         this.updateAllTabs();
      }

   }

   @Deprecated
   public void setTabsFromPagerAdapter(PagerAdapter var1) {
      this.setPagerAdapter(var1, false);
   }

   public void setUnboundedRipple(boolean var1) {
      if (this.unboundedRipple != var1) {
         this.unboundedRipple = var1;

         for(int var2 = 0; var2 < this.slidingTabIndicator.getChildCount(); ++var2) {
            View var3 = this.slidingTabIndicator.getChildAt(var2);
            if (var3 instanceof TabLayout.TabView) {
               ((TabLayout.TabView)var3).updateBackgroundDrawable(this.getContext());
            }
         }
      }

   }

   public void setUnboundedRippleResource(int var1) {
      this.setUnboundedRipple(this.getResources().getBoolean(var1));
   }

   public void setupWithViewPager(ViewPager var1) {
      this.setupWithViewPager(var1, true);
   }

   public void setupWithViewPager(ViewPager var1, boolean var2) {
      this.setupWithViewPager(var1, var2, false);
   }

   public boolean shouldDelayChildPressedState() {
      boolean var1;
      if (this.getTabScrollRange() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void updateTabViews(boolean var1) {
      for(int var2 = 0; var2 < this.slidingTabIndicator.getChildCount(); ++var2) {
         View var3 = this.slidingTabIndicator.getChildAt(var2);
         var3.setMinimumWidth(this.getTabMinWidth());
         this.updateTabViewLayoutParams((android.widget.LinearLayout.LayoutParams)var3.getLayoutParams());
         if (var1) {
            var3.requestLayout();
         }
      }

   }

   private class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
      private boolean autoRefresh;

      AdapterChangeListener() {
      }

      public void onAdapterChanged(ViewPager var1, PagerAdapter var2, PagerAdapter var3) {
         if (TabLayout.this.viewPager == var1) {
            TabLayout.this.setPagerAdapter(var3, this.autoRefresh);
         }

      }

      void setAutoRefresh(boolean var1) {
         this.autoRefresh = var1;
      }
   }

   @Deprecated
   public interface BaseOnTabSelectedListener<T extends TabLayout.Tab> {
      void onTabReselected(T var1);

      void onTabSelected(T var1);

      void onTabUnselected(T var1);
   }

   public @interface LabelVisibility {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface Mode {
   }

   public interface OnTabSelectedListener extends TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
   }

   private class PagerAdapterObserver extends DataSetObserver {
      PagerAdapterObserver() {
      }

      public void onChanged() {
         TabLayout.this.populateFromPagerAdapter();
      }

      public void onInvalidated() {
         TabLayout.this.populateFromPagerAdapter();
      }
   }

   class SlidingTabIndicator extends LinearLayout {
      private int animationStartLeft = -1;
      private int animationStartRight = -1;
      private final GradientDrawable defaultSelectionIndicator;
      ValueAnimator indicatorAnimator;
      int indicatorLeft = -1;
      int indicatorRight = -1;
      private int layoutDirection = -1;
      private int selectedIndicatorHeight;
      private final Paint selectedIndicatorPaint;
      int selectedPosition = -1;
      float selectionOffset;

      SlidingTabIndicator(Context var2) {
         super(var2);
         this.setWillNotDraw(false);
         this.selectedIndicatorPaint = new Paint();
         this.defaultSelectionIndicator = new GradientDrawable();
      }

      private void calculateTabViewContentBounds(TabLayout.TabView var1, RectF var2) {
         int var3 = var1.getContentWidth();
         int var4 = (int)ViewUtils.dpToPx(this.getContext(), 24);
         int var5 = var3;
         if (var3 < var4) {
            var5 = var4;
         }

         var4 = (var1.getLeft() + var1.getRight()) / 2;
         var5 /= 2;
         var2.set((float)(var4 - var5), 0.0F, (float)(var4 + var5), 0.0F);
      }

      private void updateIndicatorPosition() {
         View var1 = this.getChildAt(this.selectedPosition);
         int var2;
         int var3;
         if (var1 != null && var1.getWidth() > 0) {
            var2 = var1.getLeft();
            var3 = var1.getRight();
            int var4 = var2;
            int var5 = var3;
            if (!TabLayout.this.tabIndicatorFullWidth) {
               var4 = var2;
               var5 = var3;
               if (var1 instanceof TabLayout.TabView) {
                  this.calculateTabViewContentBounds((TabLayout.TabView)var1, TabLayout.this.tabViewContentBounds);
                  var4 = (int)TabLayout.this.tabViewContentBounds.left;
                  var5 = (int)TabLayout.this.tabViewContentBounds.right;
               }
            }

            var3 = var4;
            var2 = var5;
            if (this.selectionOffset > 0.0F) {
               var3 = var4;
               var2 = var5;
               if (this.selectedPosition < this.getChildCount() - 1) {
                  var1 = this.getChildAt(this.selectedPosition + 1);
                  int var6 = var1.getLeft();
                  int var7 = var1.getRight();
                  var3 = var6;
                  var2 = var7;
                  if (!TabLayout.this.tabIndicatorFullWidth) {
                     var3 = var6;
                     var2 = var7;
                     if (var1 instanceof TabLayout.TabView) {
                        this.calculateTabViewContentBounds((TabLayout.TabView)var1, TabLayout.this.tabViewContentBounds);
                        var3 = (int)TabLayout.this.tabViewContentBounds.left;
                        var2 = (int)TabLayout.this.tabViewContentBounds.right;
                     }
                  }

                  float var8 = this.selectionOffset;
                  var3 = (int)((float)var3 * var8 + (1.0F - var8) * (float)var4);
                  var2 = (int)((float)var2 * var8 + (1.0F - var8) * (float)var5);
               }
            }
         } else {
            var3 = -1;
            var2 = -1;
         }

         this.setIndicatorPosition(var3, var2);
      }

      private void updateOrRecreateIndicatorAnimation(boolean var1, final int var2, int var3) {
         View var4 = this.getChildAt(var2);
         if (var4 == null) {
            this.updateIndicatorPosition();
         } else {
            int var5 = var4.getLeft();
            int var6 = var4.getRight();
            final int var7 = var5;
            final int var8 = var6;
            if (!TabLayout.this.tabIndicatorFullWidth) {
               var7 = var5;
               var8 = var6;
               if (var4 instanceof TabLayout.TabView) {
                  this.calculateTabViewContentBounds((TabLayout.TabView)var4, TabLayout.this.tabViewContentBounds);
                  var7 = (int)TabLayout.this.tabViewContentBounds.left;
                  var8 = (int)TabLayout.this.tabViewContentBounds.right;
               }
            }

            var5 = this.indicatorLeft;
            var6 = this.indicatorRight;
            if (var5 != var7 || var6 != var8) {
               if (var1) {
                  this.animationStartLeft = var5;
                  this.animationStartRight = var6;
               }

               AnimatorUpdateListener var9 = new AnimatorUpdateListener() {
                  public void onAnimationUpdate(ValueAnimator var1) {
                     float var2 = var1.getAnimatedFraction();
                     TabLayout.SlidingTabIndicator var3 = SlidingTabIndicator.this;
                     var3.setIndicatorPosition(AnimationUtils.lerp(var3.animationStartLeft, var7, var2), AnimationUtils.lerp(SlidingTabIndicator.this.animationStartRight, var8, var2));
                  }
               };
               if (var1) {
                  ValueAnimator var10 = new ValueAnimator();
                  this.indicatorAnimator = var10;
                  var10.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                  var10.setDuration((long)var3);
                  var10.setFloatValues(new float[]{0.0F, 1.0F});
                  var10.addUpdateListener(var9);
                  var10.addListener(new AnimatorListenerAdapter() {
                     public void onAnimationEnd(Animator var1) {
                        SlidingTabIndicator.this.selectedPosition = var2;
                        SlidingTabIndicator.this.selectionOffset = 0.0F;
                     }

                     public void onAnimationStart(Animator var1) {
                        SlidingTabIndicator.this.selectedPosition = var2;
                     }
                  });
                  var10.start();
               } else {
                  this.indicatorAnimator.removeAllUpdateListeners();
                  this.indicatorAnimator.addUpdateListener(var9);
               }

            }
         }
      }

      void animateIndicatorToPosition(int var1, int var2) {
         ValueAnimator var3 = this.indicatorAnimator;
         if (var3 != null && var3.isRunning()) {
            this.indicatorAnimator.cancel();
         }

         this.updateOrRecreateIndicatorAnimation(true, var1, var2);
      }

      boolean childrenNeedLayout() {
         int var1 = this.getChildCount();

         for(int var2 = 0; var2 < var1; ++var2) {
            if (this.getChildAt(var2).getWidth() <= 0) {
               return true;
            }
         }

         return false;
      }

      public void draw(Canvas var1) {
         Drawable var2 = TabLayout.this.tabSelectedIndicator;
         byte var3 = 0;
         int var4;
         if (var2 != null) {
            var4 = TabLayout.this.tabSelectedIndicator.getIntrinsicHeight();
         } else {
            var4 = 0;
         }

         int var5 = this.selectedIndicatorHeight;
         if (var5 >= 0) {
            var4 = var5;
         }

         int var6 = TabLayout.this.tabIndicatorGravity;
         if (var6 != 0) {
            if (var6 != 1) {
               var5 = var3;
               if (var6 != 2) {
                  if (var6 != 3) {
                     var4 = 0;
                     var5 = var3;
                  } else {
                     var4 = this.getHeight();
                     var5 = var3;
                  }
               }
            } else {
               var5 = (this.getHeight() - var4) / 2;
               var4 = (this.getHeight() + var4) / 2;
            }
         } else {
            var5 = this.getHeight() - var4;
            var4 = this.getHeight();
         }

         int var8 = this.indicatorLeft;
         if (var8 >= 0 && this.indicatorRight > var8) {
            Object var7;
            if (TabLayout.this.tabSelectedIndicator != null) {
               var7 = TabLayout.this.tabSelectedIndicator;
            } else {
               var7 = this.defaultSelectionIndicator;
            }

            var2 = DrawableCompat.wrap((Drawable)var7).mutate();
            var2.setBounds(this.indicatorLeft, var5, this.indicatorRight, var4);
            if (this.selectedIndicatorPaint != null) {
               if (VERSION.SDK_INT == 21) {
                  var2.setColorFilter(this.selectedIndicatorPaint.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
               } else {
                  DrawableCompat.setTint(var2, this.selectedIndicatorPaint.getColor());
               }
            }

            var2.draw(var1);
         }

         super.draw(var1);
      }

      float getIndicatorPosition() {
         return (float)this.selectedPosition + this.selectionOffset;
      }

      protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
         super.onLayout(var1, var2, var3, var4, var5);
         ValueAnimator var6 = this.indicatorAnimator;
         if (var6 != null && var6.isRunning()) {
            this.updateOrRecreateIndicatorAnimation(false, this.selectedPosition, -1);
         } else {
            this.updateIndicatorPosition();
         }

      }

      protected void onMeasure(int var1, int var2) {
         super.onMeasure(var1, var2);
         if (MeasureSpec.getMode(var1) == 1073741824) {
            int var3 = TabLayout.this.tabGravity;
            boolean var4 = true;
            if (var3 == 1 || TabLayout.this.mode == 2) {
               int var5 = this.getChildCount();
               byte var6 = 0;
               var3 = 0;

               int var7;
               int var9;
               for(var7 = 0; var3 < var5; var7 = var9) {
                  View var8 = this.getChildAt(var3);
                  var9 = var7;
                  if (var8.getVisibility() == 0) {
                     var9 = Math.max(var7, var8.getMeasuredWidth());
                  }

                  ++var3;
               }

               if (var7 <= 0) {
                  return;
               }

               var3 = (int)ViewUtils.dpToPx(this.getContext(), 16);
               boolean var10;
               if (var7 * var5 > this.getMeasuredWidth() - var3 * 2) {
                  TabLayout.this.tabGravity = 0;
                  TabLayout.this.updateTabViews(false);
                  var10 = var4;
               } else {
                  var10 = false;

                  for(var9 = var6; var9 < var5; ++var9) {
                     android.widget.LinearLayout.LayoutParams var11 = (android.widget.LinearLayout.LayoutParams)this.getChildAt(var9).getLayoutParams();
                     if (var11.width != var7 || var11.weight != 0.0F) {
                        var11.width = var7;
                        var11.weight = 0.0F;
                        var10 = true;
                     }
                  }
               }

               if (var10) {
                  super.onMeasure(var1, var2);
               }
            }

         }
      }

      public void onRtlPropertiesChanged(int var1) {
         super.onRtlPropertiesChanged(var1);
         if (VERSION.SDK_INT < 23 && this.layoutDirection != var1) {
            this.requestLayout();
            this.layoutDirection = var1;
         }

      }

      void setIndicatorPosition(int var1, int var2) {
         if (var1 != this.indicatorLeft || var2 != this.indicatorRight) {
            this.indicatorLeft = var1;
            this.indicatorRight = var2;
            ViewCompat.postInvalidateOnAnimation(this);
         }

      }

      void setIndicatorPositionFromTabPosition(int var1, float var2) {
         ValueAnimator var3 = this.indicatorAnimator;
         if (var3 != null && var3.isRunning()) {
            this.indicatorAnimator.cancel();
         }

         this.selectedPosition = var1;
         this.selectionOffset = var2;
         this.updateIndicatorPosition();
      }

      void setSelectedIndicatorColor(int var1) {
         if (this.selectedIndicatorPaint.getColor() != var1) {
            this.selectedIndicatorPaint.setColor(var1);
            ViewCompat.postInvalidateOnAnimation(this);
         }

      }

      void setSelectedIndicatorHeight(int var1) {
         if (this.selectedIndicatorHeight != var1) {
            this.selectedIndicatorHeight = var1;
            ViewCompat.postInvalidateOnAnimation(this);
         }

      }
   }

   public static class Tab {
      public static final int INVALID_POSITION = -1;
      private CharSequence contentDesc;
      private View customView;
      private Drawable icon;
      private int labelVisibilityMode = 1;
      public TabLayout parent;
      private int position = -1;
      private Object tag;
      private CharSequence text;
      public TabLayout.TabView view;

      public BadgeDrawable getBadge() {
         return this.view.getBadge();
      }

      public CharSequence getContentDescription() {
         TabLayout.TabView var1 = this.view;
         CharSequence var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = var1.getContentDescription();
         }

         return var2;
      }

      public View getCustomView() {
         return this.customView;
      }

      public Drawable getIcon() {
         return this.icon;
      }

      public BadgeDrawable getOrCreateBadge() {
         return this.view.getOrCreateBadge();
      }

      public int getPosition() {
         return this.position;
      }

      public int getTabLabelVisibility() {
         return this.labelVisibilityMode;
      }

      public Object getTag() {
         return this.tag;
      }

      public CharSequence getText() {
         return this.text;
      }

      public boolean isSelected() {
         TabLayout var1 = this.parent;
         if (var1 != null) {
            boolean var2;
            if (var1.getSelectedTabPosition() == this.position) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         } else {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
         }
      }

      public void removeBadge() {
         this.view.removeBadge();
      }

      void reset() {
         this.parent = null;
         this.view = null;
         this.tag = null;
         this.icon = null;
         this.text = null;
         this.contentDesc = null;
         this.position = -1;
         this.customView = null;
      }

      public void select() {
         TabLayout var1 = this.parent;
         if (var1 != null) {
            var1.selectTab(this);
         } else {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
         }
      }

      public TabLayout.Tab setContentDescription(int var1) {
         TabLayout var2 = this.parent;
         if (var2 != null) {
            return this.setContentDescription(var2.getResources().getText(var1));
         } else {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
         }
      }

      public TabLayout.Tab setContentDescription(CharSequence var1) {
         this.contentDesc = var1;
         this.updateView();
         return this;
      }

      public TabLayout.Tab setCustomView(int var1) {
         return this.setCustomView(LayoutInflater.from(this.view.getContext()).inflate(var1, this.view, false));
      }

      public TabLayout.Tab setCustomView(View var1) {
         this.customView = var1;
         this.updateView();
         return this;
      }

      public TabLayout.Tab setIcon(int var1) {
         TabLayout var2 = this.parent;
         if (var2 != null) {
            return this.setIcon(AppCompatResources.getDrawable(var2.getContext(), var1));
         } else {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
         }
      }

      public TabLayout.Tab setIcon(Drawable var1) {
         this.icon = var1;
         if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
            this.parent.updateTabViews(true);
         }

         this.updateView();
         if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
            this.view.invalidate();
         }

         return this;
      }

      void setPosition(int var1) {
         this.position = var1;
      }

      public TabLayout.Tab setTabLabelVisibility(int var1) {
         this.labelVisibilityMode = var1;
         if (this.parent.tabGravity == 1 || this.parent.mode == 2) {
            this.parent.updateTabViews(true);
         }

         this.updateView();
         if (BadgeUtils.USE_COMPAT_PARENT && this.view.hasBadgeDrawable() && this.view.badgeDrawable.isVisible()) {
            this.view.invalidate();
         }

         return this;
      }

      public TabLayout.Tab setTag(Object var1) {
         this.tag = var1;
         return this;
      }

      public TabLayout.Tab setText(int var1) {
         TabLayout var2 = this.parent;
         if (var2 != null) {
            return this.setText(var2.getResources().getText(var1));
         } else {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
         }
      }

      public TabLayout.Tab setText(CharSequence var1) {
         if (TextUtils.isEmpty(this.contentDesc) && !TextUtils.isEmpty(var1)) {
            this.view.setContentDescription(var1);
         }

         this.text = var1;
         this.updateView();
         return this;
      }

      void updateView() {
         TabLayout.TabView var1 = this.view;
         if (var1 != null) {
            var1.update();
         }

      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface TabGravity {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface TabIndicatorGravity {
   }

   public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
      private int previousScrollState;
      private int scrollState;
      private final WeakReference<TabLayout> tabLayoutRef;

      public TabLayoutOnPageChangeListener(TabLayout var1) {
         this.tabLayoutRef = new WeakReference(var1);
      }

      public void onPageScrollStateChanged(int var1) {
         this.previousScrollState = this.scrollState;
         this.scrollState = var1;
      }

      public void onPageScrolled(int var1, float var2, int var3) {
         TabLayout var4 = (TabLayout)this.tabLayoutRef.get();
         if (var4 != null) {
            var3 = this.scrollState;
            boolean var5 = false;
            boolean var6;
            if (var3 == 2 && this.previousScrollState != 1) {
               var6 = false;
            } else {
               var6 = true;
            }

            if (this.scrollState != 2 || this.previousScrollState != 0) {
               var5 = true;
            }

            var4.setScrollPosition(var1, var2, var6, var5);
         }

      }

      public void onPageSelected(int var1) {
         TabLayout var2 = (TabLayout)this.tabLayoutRef.get();
         if (var2 != null && var2.getSelectedTabPosition() != var1 && var1 < var2.getTabCount()) {
            int var3 = this.scrollState;
            boolean var4;
            if (var3 == 0 || var3 == 2 && this.previousScrollState == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            var2.selectTab(var2.getTabAt(var1), var4);
         }

      }

      void reset() {
         this.scrollState = 0;
         this.previousScrollState = 0;
      }
   }

   public final class TabView extends LinearLayout {
      private View badgeAnchorView;
      private BadgeDrawable badgeDrawable;
      private Drawable baseBackgroundDrawable;
      private ImageView customIconView;
      private TextView customTextView;
      private View customView;
      private int defaultMaxLines = 2;
      private ImageView iconView;
      private TabLayout.Tab tab;
      private TextView textView;

      public TabView(Context var2) {
         super(var2);
         this.updateBackgroundDrawable(var2);
         ViewCompat.setPaddingRelative(this, TabLayout.this.tabPaddingStart, TabLayout.this.tabPaddingTop, TabLayout.this.tabPaddingEnd, TabLayout.this.tabPaddingBottom);
         this.setGravity(17);
         this.setOrientation(TabLayout.this.inlineLabel ^ 1);
         this.setClickable(true);
         ViewCompat.setPointerIcon(this, PointerIconCompat.getSystemIcon(this.getContext(), 1002));
      }

      private void addOnLayoutChangeListener(final View var1) {
         if (var1 != null) {
            var1.addOnLayoutChangeListener(new OnLayoutChangeListener() {
               public void onLayoutChange(View var1x, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
                  if (var1.getVisibility() == 0) {
                     TabView.this.tryUpdateBadgeDrawableBounds(var1);
                  }

               }
            });
         }
      }

      private float approximateLineWidth(Layout var1, int var2, float var3) {
         return var1.getLineWidth(var2) * (var3 / var1.getPaint().getTextSize());
      }

      private void clipViewToPaddingForBadge(boolean var1) {
         this.setClipChildren(var1);
         this.setClipToPadding(var1);
         ViewGroup var2 = (ViewGroup)this.getParent();
         if (var2 != null) {
            var2.setClipChildren(var1);
            var2.setClipToPadding(var1);
         }

      }

      private FrameLayout createPreApi18BadgeAnchorRoot() {
         FrameLayout var1 = new FrameLayout(this.getContext());
         var1.setLayoutParams(new LayoutParams(-2, -2));
         return var1;
      }

      private void drawBackground(Canvas var1) {
         Drawable var2 = this.baseBackgroundDrawable;
         if (var2 != null) {
            var2.setBounds(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
            this.baseBackgroundDrawable.draw(var1);
         }

      }

      private BadgeDrawable getBadge() {
         return this.badgeDrawable;
      }

      private int getContentWidth() {
         TextView var1 = this.textView;
         int var2 = 0;
         ImageView var3 = this.iconView;
         View var4 = this.customView;
         int var5 = 0;
         int var6 = 0;

         boolean var11;
         for(boolean var7 = false; var2 < 3; var7 = var11) {
            View var8 = (new View[]{var1, var3, var4})[var2];
            int var9 = var5;
            int var10 = var6;
            var11 = var7;
            if (var8 != null) {
               var9 = var5;
               var10 = var6;
               var11 = var7;
               if (var8.getVisibility() == 0) {
                  if (var7) {
                     var6 = Math.min(var6, var8.getLeft());
                  } else {
                     var6 = var8.getLeft();
                  }

                  int var12;
                  if (var7) {
                     var12 = Math.max(var5, var8.getRight());
                  } else {
                     var12 = var8.getRight();
                  }

                  var11 = true;
                  var10 = var6;
                  var9 = var12;
               }
            }

            ++var2;
            var5 = var9;
            var6 = var10;
         }

         return var5 - var6;
      }

      private FrameLayout getCustomParentForBadge(View var1) {
         ImageView var2 = this.iconView;
         FrameLayout var3 = null;
         if (var1 != var2 && var1 != this.textView) {
            return null;
         } else {
            if (BadgeUtils.USE_COMPAT_PARENT) {
               var3 = (FrameLayout)var1.getParent();
            }

            return var3;
         }
      }

      private BadgeDrawable getOrCreateBadge() {
         if (this.badgeDrawable == null) {
            this.badgeDrawable = BadgeDrawable.create(this.getContext());
         }

         this.tryUpdateBadgeAnchor();
         BadgeDrawable var1 = this.badgeDrawable;
         if (var1 != null) {
            return var1;
         } else {
            throw new IllegalStateException("Unable to create badge");
         }
      }

      private boolean hasBadgeDrawable() {
         boolean var1;
         if (this.badgeDrawable != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private void inflateAndAddDefaultIconView() {
         Object var1;
         if (BadgeUtils.USE_COMPAT_PARENT) {
            var1 = this.createPreApi18BadgeAnchorRoot();
            this.addView((View)var1, 0);
         } else {
            var1 = this;
         }

         ImageView var2 = (ImageView)LayoutInflater.from(this.getContext()).inflate(R.layout.design_layout_tab_icon, (ViewGroup)var1, false);
         this.iconView = var2;
         ((ViewGroup)var1).addView(var2, 0);
      }

      private void inflateAndAddDefaultTextView() {
         Object var1;
         if (BadgeUtils.USE_COMPAT_PARENT) {
            var1 = this.createPreApi18BadgeAnchorRoot();
            this.addView((View)var1);
         } else {
            var1 = this;
         }

         TextView var2 = (TextView)LayoutInflater.from(this.getContext()).inflate(R.layout.design_layout_tab_text, (ViewGroup)var1, false);
         this.textView = var2;
         ((ViewGroup)var1).addView(var2);
      }

      private void removeBadge() {
         if (this.badgeAnchorView != null) {
            this.tryRemoveBadgeFromAnchor();
         }

         this.badgeDrawable = null;
      }

      private void tryAttachBadgeToAnchor(View var1) {
         if (this.hasBadgeDrawable()) {
            if (var1 != null) {
               this.clipViewToPaddingForBadge(false);
               BadgeUtils.attachBadgeDrawable(this.badgeDrawable, var1, this.getCustomParentForBadge(var1));
               this.badgeAnchorView = var1;
            }

         }
      }

      private void tryRemoveBadgeFromAnchor() {
         if (this.hasBadgeDrawable()) {
            this.clipViewToPaddingForBadge(true);
            View var1 = this.badgeAnchorView;
            if (var1 != null) {
               BadgeUtils.detachBadgeDrawable(this.badgeDrawable, var1, this.getCustomParentForBadge(var1));
               this.badgeAnchorView = null;
            }

         }
      }

      private void tryUpdateBadgeAnchor() {
         if (this.hasBadgeDrawable()) {
            if (this.customView != null) {
               this.tryRemoveBadgeFromAnchor();
            } else {
               TabLayout.Tab var1;
               View var3;
               if (this.iconView != null) {
                  var1 = this.tab;
                  if (var1 != null && var1.getIcon() != null) {
                     var3 = this.badgeAnchorView;
                     ImageView var4 = this.iconView;
                     if (var3 != var4) {
                        this.tryRemoveBadgeFromAnchor();
                        this.tryAttachBadgeToAnchor(this.iconView);
                     } else {
                        this.tryUpdateBadgeDrawableBounds(var4);
                     }

                     return;
                  }
               }

               if (this.textView != null) {
                  var1 = this.tab;
                  if (var1 != null && var1.getTabLabelVisibility() == 1) {
                     var3 = this.badgeAnchorView;
                     TextView var2 = this.textView;
                     if (var3 != var2) {
                        this.tryRemoveBadgeFromAnchor();
                        this.tryAttachBadgeToAnchor(this.textView);
                     } else {
                        this.tryUpdateBadgeDrawableBounds(var2);
                     }

                     return;
                  }
               }

               this.tryRemoveBadgeFromAnchor();
            }

         }
      }

      private void tryUpdateBadgeDrawableBounds(View var1) {
         if (this.hasBadgeDrawable() && var1 == this.badgeAnchorView) {
            BadgeUtils.setBadgeDrawableBounds(this.badgeDrawable, var1, this.getCustomParentForBadge(var1));
         }

      }

      private void updateBackgroundDrawable(Context var1) {
         int var2 = TabLayout.this.tabBackgroundResId;
         Object var3 = null;
         Drawable var6;
         if (var2 != 0) {
            var6 = AppCompatResources.getDrawable(var1, TabLayout.this.tabBackgroundResId);
            this.baseBackgroundDrawable = var6;
            if (var6 != null && var6.isStateful()) {
               this.baseBackgroundDrawable.setState(this.getDrawableState());
            }
         } else {
            this.baseBackgroundDrawable = null;
         }

         GradientDrawable var4 = new GradientDrawable();
         ((GradientDrawable)var4).setColor(0);
         Object var7 = var4;
         if (TabLayout.this.tabRippleColorStateList != null) {
            GradientDrawable var8 = new GradientDrawable();
            var8.setCornerRadius(1.0E-5F);
            var8.setColor(-1);
            ColorStateList var5 = RippleUtils.convertToRippleDrawableColor(TabLayout.this.tabRippleColorStateList);
            if (VERSION.SDK_INT >= 21) {
               if (TabLayout.this.unboundedRipple) {
                  var4 = null;
               }

               if (TabLayout.this.unboundedRipple) {
                  var8 = (GradientDrawable)var3;
               }

               var7 = new RippleDrawable(var5, var4, var8);
            } else {
               var6 = DrawableCompat.wrap(var8);
               DrawableCompat.setTintList(var6, var5);
               var7 = new LayerDrawable(new Drawable[]{var4, var6});
            }
         }

         ViewCompat.setBackground(this, (Drawable)var7);
         TabLayout.this.invalidate();
      }

      private void updateTextAndIcon(TextView var1, ImageView var2) {
         TabLayout.Tab var3 = this.tab;
         Object var4 = null;
         Drawable var11;
         if (var3 != null && var3.getIcon() != null) {
            var11 = DrawableCompat.wrap(this.tab.getIcon()).mutate();
         } else {
            var11 = null;
         }

         TabLayout.Tab var5 = this.tab;
         CharSequence var12;
         if (var5 != null) {
            var12 = var5.getText();
         } else {
            var12 = null;
         }

         if (var2 != null) {
            if (var11 != null) {
               var2.setImageDrawable(var11);
               var2.setVisibility(0);
               this.setVisibility(0);
            } else {
               var2.setVisibility(8);
               var2.setImageDrawable((Drawable)null);
            }
         }

         boolean var6 = TextUtils.isEmpty(var12) ^ true;
         if (var1 != null) {
            if (var6) {
               var1.setText(var12);
               if (this.tab.labelVisibilityMode == 1) {
                  var1.setVisibility(0);
               } else {
                  var1.setVisibility(8);
               }

               this.setVisibility(0);
            } else {
               var1.setVisibility(8);
               var1.setText((CharSequence)null);
            }
         }

         if (var2 != null) {
            MarginLayoutParams var8 = (MarginLayoutParams)var2.getLayoutParams();
            int var7;
            if (var6 && var2.getVisibility() == 0) {
               var7 = (int)ViewUtils.dpToPx(this.getContext(), 8);
            } else {
               var7 = 0;
            }

            if (TabLayout.this.inlineLabel) {
               if (var7 != MarginLayoutParamsCompat.getMarginEnd(var8)) {
                  MarginLayoutParamsCompat.setMarginEnd(var8, var7);
                  var8.bottomMargin = 0;
                  var2.setLayoutParams(var8);
                  var2.requestLayout();
               }
            } else if (var7 != var8.bottomMargin) {
               var8.bottomMargin = var7;
               MarginLayoutParamsCompat.setMarginEnd(var8, 0);
               var2.setLayoutParams(var8);
               var2.requestLayout();
            }
         }

         TabLayout.Tab var9 = this.tab;
         CharSequence var10;
         if (var9 != null) {
            var10 = var9.contentDesc;
         } else {
            var10 = null;
         }

         if (var6) {
            var10 = (CharSequence)var4;
         }

         TooltipCompat.setTooltipText(this, var10);
      }

      protected void drawableStateChanged() {
         super.drawableStateChanged();
         int[] var1 = this.getDrawableState();
         Drawable var2 = this.baseBackgroundDrawable;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2 != null) {
            var4 = var3;
            if (var2.isStateful()) {
               var4 = false | this.baseBackgroundDrawable.setState(var1);
            }
         }

         if (var4) {
            this.invalidate();
            TabLayout.this.invalidate();
         }

      }

      public TabLayout.Tab getTab() {
         return this.tab;
      }

      public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
         super.onInitializeAccessibilityNodeInfo(var1);
         BadgeDrawable var2 = this.badgeDrawable;
         if (var2 != null && var2.isVisible()) {
            CharSequence var5 = this.getContentDescription();
            StringBuilder var3 = new StringBuilder();
            var3.append(var5);
            var3.append(", ");
            var3.append(this.badgeDrawable.getContentDescription());
            var1.setContentDescription(var3.toString());
         }

         AccessibilityNodeInfoCompat var4 = AccessibilityNodeInfoCompat.wrap(var1);
         var4.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, this.tab.getPosition(), 1, false, this.isSelected()));
         if (this.isSelected()) {
            var4.setClickable(false);
            var4.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
         }

         var4.setRoleDescription("Tab");
      }

      public void onMeasure(int var1, int var2) {
         int var3 = MeasureSpec.getSize(var1);
         int var4 = MeasureSpec.getMode(var1);
         int var5 = TabLayout.this.getTabMaxWidth();
         int var6 = var1;
         if (var5 > 0) {
            label57: {
               if (var4 != 0) {
                  var6 = var1;
                  if (var3 <= var5) {
                     break label57;
                  }
               }

               var6 = MeasureSpec.makeMeasureSpec(TabLayout.this.tabMaxWidth, Integer.MIN_VALUE);
            }
         }

         super.onMeasure(var6, var2);
         if (this.textView != null) {
            float var7 = TabLayout.this.tabTextSize;
            var4 = this.defaultMaxLines;
            ImageView var8 = this.iconView;
            boolean var12 = true;
            float var9;
            if (var8 != null && var8.getVisibility() == 0) {
               var1 = 1;
               var9 = var7;
            } else {
               TextView var13 = this.textView;
               var9 = var7;
               var1 = var4;
               if (var13 != null) {
                  var9 = var7;
                  var1 = var4;
                  if (var13.getLineCount() > 1) {
                     var9 = TabLayout.this.tabTextMultiLineSize;
                     var1 = var4;
                  }
               }
            }

            var7 = this.textView.getTextSize();
            var3 = this.textView.getLineCount();
            var4 = TextViewCompat.getMaxLines(this.textView);
            float var15;
            int var10 = (var15 = var9 - var7) == 0.0F ? 0 : (var15 < 0.0F ? -1 : 1);
            if (var10 != 0 || var4 >= 0 && var1 != var4) {
               boolean var11 = var12;
               if (TabLayout.this.mode == 1) {
                  var11 = var12;
                  if (var10 > 0) {
                     var11 = var12;
                     if (var3 == 1) {
                        label65: {
                           Layout var14 = this.textView.getLayout();
                           if (var14 != null) {
                              var11 = var12;
                              if (this.approximateLineWidth(var14, 0, var9) <= (float)(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight())) {
                                 break label65;
                              }
                           }

                           var11 = false;
                        }
                     }
                  }
               }

               if (var11) {
                  this.textView.setTextSize(0, var9);
                  this.textView.setMaxLines(var1);
                  super.onMeasure(var6, var2);
               }
            }
         }

      }

      public boolean performClick() {
         boolean var1 = super.performClick();
         boolean var2 = var1;
         if (this.tab != null) {
            if (!var1) {
               this.playSoundEffect(0);
            }

            this.tab.select();
            var2 = true;
         }

         return var2;
      }

      void reset() {
         this.setTab((TabLayout.Tab)null);
         this.setSelected(false);
      }

      public void setSelected(boolean var1) {
         boolean var2;
         if (this.isSelected() != var1) {
            var2 = true;
         } else {
            var2 = false;
         }

         super.setSelected(var1);
         if (var2 && var1 && VERSION.SDK_INT < 16) {
            this.sendAccessibilityEvent(4);
         }

         TextView var3 = this.textView;
         if (var3 != null) {
            var3.setSelected(var1);
         }

         ImageView var4 = this.iconView;
         if (var4 != null) {
            var4.setSelected(var1);
         }

         View var5 = this.customView;
         if (var5 != null) {
            var5.setSelected(var1);
         }

      }

      void setTab(TabLayout.Tab var1) {
         if (var1 != this.tab) {
            this.tab = var1;
            this.update();
         }

      }

      final void update() {
         TabLayout.Tab var1 = this.tab;
         Object var2 = null;
         View var3;
         if (var1 != null) {
            var3 = var1.getCustomView();
         } else {
            var3 = null;
         }

         if (var3 != null) {
            ViewParent var4 = var3.getParent();
            if (var4 != this) {
               if (var4 != null) {
                  ((ViewGroup)var4).removeView(var3);
               }

               this.addView(var3);
            }

            this.customView = var3;
            TextView var6 = this.textView;
            if (var6 != null) {
               var6.setVisibility(8);
            }

            ImageView var8 = this.iconView;
            if (var8 != null) {
               var8.setVisibility(8);
               this.iconView.setImageDrawable((Drawable)null);
            }

            var6 = (TextView)var3.findViewById(16908308);
            this.customTextView = var6;
            if (var6 != null) {
               this.defaultMaxLines = TextViewCompat.getMaxLines(var6);
            }

            this.customIconView = (ImageView)var3.findViewById(16908294);
         } else {
            var3 = this.customView;
            if (var3 != null) {
               this.removeView(var3);
               this.customView = null;
            }

            this.customTextView = null;
            this.customIconView = null;
         }

         if (this.customView == null) {
            if (this.iconView == null) {
               this.inflateAndAddDefaultIconView();
            }

            Drawable var7 = (Drawable)var2;
            if (var1 != null) {
               var7 = (Drawable)var2;
               if (var1.getIcon() != null) {
                  var7 = DrawableCompat.wrap(var1.getIcon()).mutate();
               }
            }

            if (var7 != null) {
               DrawableCompat.setTintList(var7, TabLayout.this.tabIconTint);
               if (TabLayout.this.tabIconTintMode != null) {
                  DrawableCompat.setTintMode(var7, TabLayout.this.tabIconTintMode);
               }
            }

            if (this.textView == null) {
               this.inflateAndAddDefaultTextView();
               this.defaultMaxLines = TextViewCompat.getMaxLines(this.textView);
            }

            TextViewCompat.setTextAppearance(this.textView, TabLayout.this.tabTextAppearance);
            if (TabLayout.this.tabTextColors != null) {
               this.textView.setTextColor(TabLayout.this.tabTextColors);
            }

            this.updateTextAndIcon(this.textView, this.iconView);
            this.tryUpdateBadgeAnchor();
            this.addOnLayoutChangeListener(this.iconView);
            this.addOnLayoutChangeListener(this.textView);
         } else if (this.customTextView != null || this.customIconView != null) {
            this.updateTextAndIcon(this.customTextView, this.customIconView);
         }

         if (var1 != null && !TextUtils.isEmpty(var1.contentDesc)) {
            this.setContentDescription(var1.contentDesc);
         }

         boolean var5;
         if (var1 != null && var1.isSelected()) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.setSelected(var5);
      }

      final void updateOrientation() {
         this.setOrientation(TabLayout.this.inlineLabel ^ 1);
         if (this.customTextView == null && this.customIconView == null) {
            this.updateTextAndIcon(this.textView, this.iconView);
         } else {
            this.updateTextAndIcon(this.customTextView, this.customIconView);
         }

      }
   }

   public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
      private final ViewPager viewPager;

      public ViewPagerOnTabSelectedListener(ViewPager var1) {
         this.viewPager = var1;
      }

      public void onTabReselected(TabLayout.Tab var1) {
      }

      public void onTabSelected(TabLayout.Tab var1) {
         this.viewPager.setCurrentItem(var1.getPosition());
      }

      public void onTabUnselected(TabLayout.Tab var1) {
      }
   }
}
