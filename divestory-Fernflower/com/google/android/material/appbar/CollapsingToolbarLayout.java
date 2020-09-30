package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class CollapsingToolbarLayout extends FrameLayout {
   private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
   private static final int DEF_STYLE_RES;
   final CollapsingTextHelper collapsingTextHelper;
   private boolean collapsingTitleEnabled;
   private Drawable contentScrim;
   int currentOffset;
   private boolean drawCollapsingTitle;
   private View dummyView;
   private int expandedMarginBottom;
   private int expandedMarginEnd;
   private int expandedMarginStart;
   private int expandedMarginTop;
   WindowInsetsCompat lastInsets;
   private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
   private boolean refreshToolbar;
   private int scrimAlpha;
   private long scrimAnimationDuration;
   private ValueAnimator scrimAnimator;
   private int scrimVisibleHeightTrigger;
   private boolean scrimsAreShown;
   Drawable statusBarScrim;
   private final Rect tmpRect;
   private Toolbar toolbar;
   private View toolbarDirectChild;
   private int toolbarId;

   static {
      DEF_STYLE_RES = R.style.Widget_Design_CollapsingToolbar;
   }

   public CollapsingToolbarLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public CollapsingToolbarLayout(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public CollapsingToolbarLayout(Context var1, AttributeSet var2, int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.refreshToolbar = true;
      this.tmpRect = new Rect();
      this.scrimVisibleHeightTrigger = -1;
      var1 = this.getContext();
      CollapsingTextHelper var4 = new CollapsingTextHelper(this);
      this.collapsingTextHelper = var4;
      var4.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
      TypedArray var5 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.CollapsingToolbarLayout, var3, DEF_STYLE_RES);
      this.collapsingTextHelper.setExpandedTextGravity(var5.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
      this.collapsingTextHelper.setCollapsedTextGravity(var5.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
      var3 = var5.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
      this.expandedMarginBottom = var3;
      this.expandedMarginEnd = var3;
      this.expandedMarginTop = var3;
      this.expandedMarginStart = var3;
      if (var5.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
         this.expandedMarginStart = var5.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
      }

      if (var5.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
         this.expandedMarginEnd = var5.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
      }

      if (var5.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
         this.expandedMarginTop = var5.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
      }

      if (var5.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
         this.expandedMarginBottom = var5.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
      }

      this.collapsingTitleEnabled = var5.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
      this.setTitle(var5.getText(R.styleable.CollapsingToolbarLayout_title));
      this.collapsingTextHelper.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
      this.collapsingTextHelper.setCollapsedTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
      if (var5.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
         this.collapsingTextHelper.setExpandedTextAppearance(var5.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
      }

      if (var5.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
         this.collapsingTextHelper.setCollapsedTextAppearance(var5.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
      }

      this.scrimVisibleHeightTrigger = var5.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
      if (var5.hasValue(R.styleable.CollapsingToolbarLayout_maxLines)) {
         this.collapsingTextHelper.setMaxLines(var5.getInt(R.styleable.CollapsingToolbarLayout_maxLines, 1));
      }

      this.scrimAnimationDuration = (long)var5.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
      this.setContentScrim(var5.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
      this.setStatusBarScrim(var5.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
      this.toolbarId = var5.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
      var5.recycle();
      this.setWillNotDraw(false);
      ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
         public WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2) {
            return CollapsingToolbarLayout.this.onWindowInsetChanged(var2);
         }
      });
   }

   private void animateScrim(int var1) {
      this.ensureToolbar();
      ValueAnimator var2 = this.scrimAnimator;
      if (var2 == null) {
         var2 = new ValueAnimator();
         this.scrimAnimator = var2;
         var2.setDuration(this.scrimAnimationDuration);
         ValueAnimator var3 = this.scrimAnimator;
         TimeInterpolator var4;
         if (var1 > this.scrimAlpha) {
            var4 = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
         } else {
            var4 = AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
         }

         var3.setInterpolator(var4);
         this.scrimAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator var1) {
               CollapsingToolbarLayout.this.setScrimAlpha((Integer)var1.getAnimatedValue());
            }
         });
      } else if (var2.isRunning()) {
         this.scrimAnimator.cancel();
      }

      this.scrimAnimator.setIntValues(new int[]{this.scrimAlpha, var1});
      this.scrimAnimator.start();
   }

   private void ensureToolbar() {
      if (this.refreshToolbar) {
         Object var1 = null;
         this.toolbar = null;
         this.toolbarDirectChild = null;
         int var2 = this.toolbarId;
         Toolbar var3;
         if (var2 != -1) {
            var3 = (Toolbar)this.findViewById(var2);
            this.toolbar = var3;
            if (var3 != null) {
               this.toolbarDirectChild = this.findDirectChild(var3);
            }
         }

         if (this.toolbar == null) {
            int var4 = this.getChildCount();
            var2 = 0;

            while(true) {
               var3 = (Toolbar)var1;
               if (var2 >= var4) {
                  break;
               }

               View var5 = this.getChildAt(var2);
               if (var5 instanceof Toolbar) {
                  var3 = (Toolbar)var5;
                  break;
               }

               ++var2;
            }

            this.toolbar = var3;
         }

         this.updateDummyView();
         this.refreshToolbar = false;
      }
   }

   private View findDirectChild(View var1) {
      ViewParent var2 = var1.getParent();
      View var3 = var1;

      for(ViewParent var4 = var2; var4 != this && var4 != null; var4 = var4.getParent()) {
         if (var4 instanceof View) {
            var3 = (View)var4;
         }
      }

      return var3;
   }

   private static int getHeightWithMargins(View var0) {
      android.view.ViewGroup.LayoutParams var1 = var0.getLayoutParams();
      if (var1 instanceof MarginLayoutParams) {
         MarginLayoutParams var2 = (MarginLayoutParams)var1;
         return var0.getHeight() + var2.topMargin + var2.bottomMargin;
      } else {
         return var0.getHeight();
      }
   }

   static ViewOffsetHelper getViewOffsetHelper(View var0) {
      ViewOffsetHelper var1 = (ViewOffsetHelper)var0.getTag(R.id.view_offset_helper);
      ViewOffsetHelper var2 = var1;
      if (var1 == null) {
         var2 = new ViewOffsetHelper(var0);
         var0.setTag(R.id.view_offset_helper, var2);
      }

      return var2;
   }

   private boolean isToolbarChild(View var1) {
      View var2 = this.toolbarDirectChild;
      boolean var3 = true;
      if (var2 != null && var2 != this) {
         if (var1 == var2) {
            return var3;
         }
      } else if (var1 == this.toolbar) {
         return var3;
      }

      var3 = false;
      return var3;
   }

   private void updateContentDescriptionFromTitle() {
      this.setContentDescription(this.getTitle());
   }

   private void updateDummyView() {
      if (!this.collapsingTitleEnabled) {
         View var1 = this.dummyView;
         if (var1 != null) {
            ViewParent var2 = var1.getParent();
            if (var2 instanceof ViewGroup) {
               ((ViewGroup)var2).removeView(this.dummyView);
            }
         }
      }

      if (this.collapsingTitleEnabled && this.toolbar != null) {
         if (this.dummyView == null) {
            this.dummyView = new View(this.getContext());
         }

         if (this.dummyView.getParent() == null) {
            this.toolbar.addView(this.dummyView, -1, -1);
         }
      }

   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof CollapsingToolbarLayout.LayoutParams;
   }

   public void draw(Canvas var1) {
      super.draw(var1);
      this.ensureToolbar();
      if (this.toolbar == null) {
         Drawable var2 = this.contentScrim;
         if (var2 != null && this.scrimAlpha > 0) {
            var2.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(var1);
         }
      }

      if (this.collapsingTitleEnabled && this.drawCollapsingTitle) {
         this.collapsingTextHelper.draw(var1);
      }

      if (this.statusBarScrim != null && this.scrimAlpha > 0) {
         WindowInsetsCompat var4 = this.lastInsets;
         int var3;
         if (var4 != null) {
            var3 = var4.getSystemWindowInsetTop();
         } else {
            var3 = 0;
         }

         if (var3 > 0) {
            this.statusBarScrim.setBounds(0, -this.currentOffset, this.getWidth(), var3 - this.currentOffset);
            this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
            this.statusBarScrim.draw(var1);
         }
      }

   }

   protected boolean drawChild(Canvas var1, View var2, long var3) {
      Drawable var5 = this.contentScrim;
      boolean var6 = true;
      boolean var7;
      if (var5 != null && this.scrimAlpha > 0 && this.isToolbarChild(var2)) {
         this.contentScrim.mutate().setAlpha(this.scrimAlpha);
         this.contentScrim.draw(var1);
         var7 = true;
      } else {
         var7 = false;
      }

      boolean var8 = var6;
      if (!super.drawChild(var1, var2, var3)) {
         if (var7) {
            var8 = var6;
         } else {
            var8 = false;
         }
      }

      return var8;
   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      int[] var1 = this.getDrawableState();
      Drawable var2 = this.statusBarScrim;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2 != null) {
         var4 = var3;
         if (var2.isStateful()) {
            var4 = false | var2.setState(var1);
         }
      }

      var2 = this.contentScrim;
      var3 = var4;
      if (var2 != null) {
         var3 = var4;
         if (var2.isStateful()) {
            var3 = var4 | var2.setState(var1);
         }
      }

      CollapsingTextHelper var5 = this.collapsingTextHelper;
      var4 = var3;
      if (var5 != null) {
         var4 = var3 | var5.setState(var1);
      }

      if (var4) {
         this.invalidate();
      }

   }

   protected CollapsingToolbarLayout.LayoutParams generateDefaultLayoutParams() {
      return new CollapsingToolbarLayout.LayoutParams(-1, -1);
   }

   public android.widget.FrameLayout.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new CollapsingToolbarLayout.LayoutParams(this.getContext(), var1);
   }

   protected android.widget.FrameLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return new CollapsingToolbarLayout.LayoutParams(var1);
   }

   public int getCollapsedTitleGravity() {
      return this.collapsingTextHelper.getCollapsedTextGravity();
   }

   public Typeface getCollapsedTitleTypeface() {
      return this.collapsingTextHelper.getCollapsedTypeface();
   }

   public Drawable getContentScrim() {
      return this.contentScrim;
   }

   public int getExpandedTitleGravity() {
      return this.collapsingTextHelper.getExpandedTextGravity();
   }

   public int getExpandedTitleMarginBottom() {
      return this.expandedMarginBottom;
   }

   public int getExpandedTitleMarginEnd() {
      return this.expandedMarginEnd;
   }

   public int getExpandedTitleMarginStart() {
      return this.expandedMarginStart;
   }

   public int getExpandedTitleMarginTop() {
      return this.expandedMarginTop;
   }

   public Typeface getExpandedTitleTypeface() {
      return this.collapsingTextHelper.getExpandedTypeface();
   }

   public int getMaxLines() {
      return this.collapsingTextHelper.getMaxLines();
   }

   final int getMaxOffsetForPinChild(View var1) {
      ViewOffsetHelper var2 = getViewOffsetHelper(var1);
      CollapsingToolbarLayout.LayoutParams var3 = (CollapsingToolbarLayout.LayoutParams)var1.getLayoutParams();
      return this.getHeight() - var2.getLayoutTop() - var1.getHeight() - var3.bottomMargin;
   }

   int getScrimAlpha() {
      return this.scrimAlpha;
   }

   public long getScrimAnimationDuration() {
      return this.scrimAnimationDuration;
   }

   public int getScrimVisibleHeightTrigger() {
      int var1 = this.scrimVisibleHeightTrigger;
      if (var1 >= 0) {
         return var1;
      } else {
         WindowInsetsCompat var2 = this.lastInsets;
         if (var2 != null) {
            var1 = var2.getSystemWindowInsetTop();
         } else {
            var1 = 0;
         }

         int var3 = ViewCompat.getMinimumHeight(this);
         return var3 > 0 ? Math.min(var3 * 2 + var1, this.getHeight()) : this.getHeight() / 3;
      }
   }

   public Drawable getStatusBarScrim() {
      return this.statusBarScrim;
   }

   public CharSequence getTitle() {
      CharSequence var1;
      if (this.collapsingTitleEnabled) {
         var1 = this.collapsingTextHelper.getText();
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean isTitleEnabled() {
      return this.collapsingTitleEnabled;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      ViewParent var1 = this.getParent();
      if (var1 instanceof AppBarLayout) {
         ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View)var1));
         if (this.onOffsetChangedListener == null) {
            this.onOffsetChangedListener = new CollapsingToolbarLayout.OffsetUpdateListener();
         }

         ((AppBarLayout)var1).addOnOffsetChangedListener(this.onOffsetChangedListener);
         ViewCompat.requestApplyInsets(this);
      }

   }

   protected void onDetachedFromWindow() {
      ViewParent var1 = this.getParent();
      AppBarLayout.OnOffsetChangedListener var2 = this.onOffsetChangedListener;
      if (var2 != null && var1 instanceof AppBarLayout) {
         ((AppBarLayout)var1).removeOnOffsetChangedListener(var2);
      }

      super.onDetachedFromWindow();
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      WindowInsetsCompat var6 = this.lastInsets;
      byte var7 = 0;
      int var8;
      int var9;
      int var10;
      View var16;
      if (var6 != null) {
         var8 = var6.getSystemWindowInsetTop();
         var9 = this.getChildCount();

         for(var10 = 0; var10 < var9; ++var10) {
            var16 = this.getChildAt(var10);
            if (!ViewCompat.getFitsSystemWindows(var16) && var16.getTop() < var8) {
               ViewCompat.offsetTopAndBottom(var16, var8);
            }
         }
      }

      var9 = this.getChildCount();

      for(var10 = 0; var10 < var9; ++var10) {
         getViewOffsetHelper(this.getChildAt(var10)).onViewLayout();
      }

      if (this.collapsingTitleEnabled) {
         var16 = this.dummyView;
         if (var16 != null) {
            var1 = ViewCompat.isAttachedToWindow(var16);
            boolean var19 = true;
            if (var1 && this.dummyView.getVisibility() == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            this.drawCollapsingTitle = var1;
            if (var1) {
               if (ViewCompat.getLayoutDirection(this) != 1) {
                  var19 = false;
               }

               Object var17 = this.toolbarDirectChild;
               if (var17 == null) {
                  var17 = this.toolbar;
               }

               int var11 = this.getMaxOffsetForPinChild((View)var17);
               DescendantOffsetUtils.getDescendantRect(this, this.dummyView, this.tmpRect);
               CollapsingTextHelper var18 = this.collapsingTextHelper;
               int var12 = this.tmpRect.left;
               if (var19) {
                  var9 = this.toolbar.getTitleMarginEnd();
               } else {
                  var9 = this.toolbar.getTitleMarginStart();
               }

               int var13 = this.tmpRect.top;
               int var14 = this.toolbar.getTitleMarginTop();
               int var15 = this.tmpRect.right;
               if (var19) {
                  var8 = this.toolbar.getTitleMarginStart();
               } else {
                  var8 = this.toolbar.getTitleMarginEnd();
               }

               var18.setCollapsedBounds(var12 + var9, var13 + var11 + var14, var15 - var8, this.tmpRect.bottom + var11 - this.toolbar.getTitleMarginBottom());
               var18 = this.collapsingTextHelper;
               if (var19) {
                  var9 = this.expandedMarginEnd;
               } else {
                  var9 = this.expandedMarginStart;
               }

               var8 = this.tmpRect.top;
               var11 = this.expandedMarginTop;
               if (var19) {
                  var10 = this.expandedMarginStart;
               } else {
                  var10 = this.expandedMarginEnd;
               }

               var18.setExpandedBounds(var9, var8 + var11, var4 - var2 - var10, var5 - var3 - this.expandedMarginBottom);
               this.collapsingTextHelper.recalculate();
            }
         }
      }

      if (this.toolbar != null) {
         if (this.collapsingTitleEnabled && TextUtils.isEmpty(this.collapsingTextHelper.getText())) {
            this.setTitle(this.toolbar.getTitle());
         }

         var16 = this.toolbarDirectChild;
         if (var16 != null && var16 != this) {
            this.setMinimumHeight(getHeightWithMargins(var16));
         } else {
            this.setMinimumHeight(getHeightWithMargins(this.toolbar));
         }
      }

      this.updateScrimVisibility();
      var3 = this.getChildCount();

      for(var2 = var7; var2 < var3; ++var2) {
         getViewOffsetHelper(this.getChildAt(var2)).applyOffsets();
      }

   }

   protected void onMeasure(int var1, int var2) {
      this.ensureToolbar();
      super.onMeasure(var1, var2);
      int var3 = MeasureSpec.getMode(var2);
      WindowInsetsCompat var4 = this.lastInsets;
      if (var4 != null) {
         var2 = var4.getSystemWindowInsetTop();
      } else {
         var2 = 0;
      }

      if (var3 == 0 && var2 > 0) {
         super.onMeasure(var1, MeasureSpec.makeMeasureSpec(this.getMeasuredHeight() + var2, 1073741824));
      }

   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      Drawable var5 = this.contentScrim;
      if (var5 != null) {
         var5.setBounds(0, 0, var1, var2);
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
         this.requestLayout();
      }

      return var1.consumeSystemWindowInsets();
   }

   public void setCollapsedTitleGravity(int var1) {
      this.collapsingTextHelper.setCollapsedTextGravity(var1);
   }

   public void setCollapsedTitleTextAppearance(int var1) {
      this.collapsingTextHelper.setCollapsedTextAppearance(var1);
   }

   public void setCollapsedTitleTextColor(int var1) {
      this.setCollapsedTitleTextColor(ColorStateList.valueOf(var1));
   }

   public void setCollapsedTitleTextColor(ColorStateList var1) {
      this.collapsingTextHelper.setCollapsedTextColor(var1);
   }

   public void setCollapsedTitleTypeface(Typeface var1) {
      this.collapsingTextHelper.setCollapsedTypeface(var1);
   }

   public void setContentScrim(Drawable var1) {
      Drawable var2 = this.contentScrim;
      if (var2 != var1) {
         Drawable var3 = null;
         if (var2 != null) {
            var2.setCallback((Callback)null);
         }

         if (var1 != null) {
            var3 = var1.mutate();
         }

         this.contentScrim = var3;
         if (var3 != null) {
            var3.setBounds(0, 0, this.getWidth(), this.getHeight());
            this.contentScrim.setCallback(this);
            this.contentScrim.setAlpha(this.scrimAlpha);
         }

         ViewCompat.postInvalidateOnAnimation(this);
      }

   }

   public void setContentScrimColor(int var1) {
      this.setContentScrim(new ColorDrawable(var1));
   }

   public void setContentScrimResource(int var1) {
      this.setContentScrim(ContextCompat.getDrawable(this.getContext(), var1));
   }

   public void setExpandedTitleColor(int var1) {
      this.setExpandedTitleTextColor(ColorStateList.valueOf(var1));
   }

   public void setExpandedTitleGravity(int var1) {
      this.collapsingTextHelper.setExpandedTextGravity(var1);
   }

   public void setExpandedTitleMargin(int var1, int var2, int var3, int var4) {
      this.expandedMarginStart = var1;
      this.expandedMarginTop = var2;
      this.expandedMarginEnd = var3;
      this.expandedMarginBottom = var4;
      this.requestLayout();
   }

   public void setExpandedTitleMarginBottom(int var1) {
      this.expandedMarginBottom = var1;
      this.requestLayout();
   }

   public void setExpandedTitleMarginEnd(int var1) {
      this.expandedMarginEnd = var1;
      this.requestLayout();
   }

   public void setExpandedTitleMarginStart(int var1) {
      this.expandedMarginStart = var1;
      this.requestLayout();
   }

   public void setExpandedTitleMarginTop(int var1) {
      this.expandedMarginTop = var1;
      this.requestLayout();
   }

   public void setExpandedTitleTextAppearance(int var1) {
      this.collapsingTextHelper.setExpandedTextAppearance(var1);
   }

   public void setExpandedTitleTextColor(ColorStateList var1) {
      this.collapsingTextHelper.setExpandedTextColor(var1);
   }

   public void setExpandedTitleTypeface(Typeface var1) {
      this.collapsingTextHelper.setExpandedTypeface(var1);
   }

   public void setMaxLines(int var1) {
      this.collapsingTextHelper.setMaxLines(var1);
   }

   void setScrimAlpha(int var1) {
      if (var1 != this.scrimAlpha) {
         if (this.contentScrim != null) {
            Toolbar var2 = this.toolbar;
            if (var2 != null) {
               ViewCompat.postInvalidateOnAnimation(var2);
            }
         }

         this.scrimAlpha = var1;
         ViewCompat.postInvalidateOnAnimation(this);
      }

   }

   public void setScrimAnimationDuration(long var1) {
      this.scrimAnimationDuration = var1;
   }

   public void setScrimVisibleHeightTrigger(int var1) {
      if (this.scrimVisibleHeightTrigger != var1) {
         this.scrimVisibleHeightTrigger = var1;
         this.updateScrimVisibility();
      }

   }

   public void setScrimsShown(boolean var1) {
      boolean var2;
      if (ViewCompat.isLaidOut(this) && !this.isInEditMode()) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.setScrimsShown(var1, var2);
   }

   public void setScrimsShown(boolean var1, boolean var2) {
      if (this.scrimsAreShown != var1) {
         short var3 = 255;
         if (var2) {
            if (!var1) {
               var3 = 0;
            }

            this.animateScrim(var3);
         } else {
            if (!var1) {
               var3 = 0;
            }

            this.setScrimAlpha(var3);
         }

         this.scrimsAreShown = var1;
      }

   }

   public void setStatusBarScrim(Drawable var1) {
      Drawable var2 = this.statusBarScrim;
      if (var2 != var1) {
         Drawable var3 = null;
         if (var2 != null) {
            var2.setCallback((Callback)null);
         }

         if (var1 != null) {
            var3 = var1.mutate();
         }

         this.statusBarScrim = var3;
         if (var3 != null) {
            if (var3.isStateful()) {
               this.statusBarScrim.setState(this.getDrawableState());
            }

            DrawableCompat.setLayoutDirection(this.statusBarScrim, ViewCompat.getLayoutDirection(this));
            var1 = this.statusBarScrim;
            boolean var4;
            if (this.getVisibility() == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            var1.setVisible(var4, false);
            this.statusBarScrim.setCallback(this);
            this.statusBarScrim.setAlpha(this.scrimAlpha);
         }

         ViewCompat.postInvalidateOnAnimation(this);
      }

   }

   public void setStatusBarScrimColor(int var1) {
      this.setStatusBarScrim(new ColorDrawable(var1));
   }

   public void setStatusBarScrimResource(int var1) {
      this.setStatusBarScrim(ContextCompat.getDrawable(this.getContext(), var1));
   }

   public void setTitle(CharSequence var1) {
      this.collapsingTextHelper.setText(var1);
      this.updateContentDescriptionFromTitle();
   }

   public void setTitleEnabled(boolean var1) {
      if (var1 != this.collapsingTitleEnabled) {
         this.collapsingTitleEnabled = var1;
         this.updateContentDescriptionFromTitle();
         this.updateDummyView();
         this.requestLayout();
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

      Drawable var3 = this.statusBarScrim;
      if (var3 != null && var3.isVisible() != var2) {
         this.statusBarScrim.setVisible(var2, false);
      }

      var3 = this.contentScrim;
      if (var3 != null && var3.isVisible() != var2) {
         this.contentScrim.setVisible(var2, false);
      }

   }

   final void updateScrimVisibility() {
      if (this.contentScrim != null || this.statusBarScrim != null) {
         boolean var1;
         if (this.getHeight() + this.currentOffset < this.getScrimVisibleHeightTrigger()) {
            var1 = true;
         } else {
            var1 = false;
         }

         this.setScrimsShown(var1);
      }

   }

   protected boolean verifyDrawable(Drawable var1) {
      boolean var2;
      if (!super.verifyDrawable(var1) && var1 != this.contentScrim && var1 != this.statusBarScrim) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static class LayoutParams extends android.widget.FrameLayout.LayoutParams {
      public static final int COLLAPSE_MODE_OFF = 0;
      public static final int COLLAPSE_MODE_PARALLAX = 2;
      public static final int COLLAPSE_MODE_PIN = 1;
      private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5F;
      int collapseMode = 0;
      float parallaxMult = 0.5F;

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
      }

      public LayoutParams(int var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.CollapsingToolbarLayout_Layout);
         this.collapseMode = var3.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
         this.setParallaxMultiplier(var3.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5F));
         var3.recycle();
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }

      public LayoutParams(android.widget.FrameLayout.LayoutParams var1) {
         super(var1);
      }

      public int getCollapseMode() {
         return this.collapseMode;
      }

      public float getParallaxMultiplier() {
         return this.parallaxMult;
      }

      public void setCollapseMode(int var1) {
         this.collapseMode = var1;
      }

      public void setParallaxMultiplier(float var1) {
         this.parallaxMult = var1;
      }
   }

   private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
      OffsetUpdateListener() {
      }

      public void onOffsetChanged(AppBarLayout var1, int var2) {
         CollapsingToolbarLayout.this.currentOffset = var2;
         int var3;
         if (CollapsingToolbarLayout.this.lastInsets != null) {
            var3 = CollapsingToolbarLayout.this.lastInsets.getSystemWindowInsetTop();
         } else {
            var3 = 0;
         }

         int var4 = CollapsingToolbarLayout.this.getChildCount();

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            View var6 = CollapsingToolbarLayout.this.getChildAt(var5);
            CollapsingToolbarLayout.LayoutParams var9 = (CollapsingToolbarLayout.LayoutParams)var6.getLayoutParams();
            ViewOffsetHelper var7 = CollapsingToolbarLayout.getViewOffsetHelper(var6);
            int var8 = var9.collapseMode;
            if (var8 != 1) {
               if (var8 == 2) {
                  var7.setTopAndBottomOffset(Math.round((float)(-var2) * var9.parallaxMult));
               }
            } else {
               var7.setTopAndBottomOffset(MathUtils.clamp(-var2, 0, CollapsingToolbarLayout.this.getMaxOffsetForPinChild(var6)));
            }
         }

         CollapsingToolbarLayout.this.updateScrimVisibility();
         if (CollapsingToolbarLayout.this.statusBarScrim != null && var3 > 0) {
            ViewCompat.postInvalidateOnAnimation(CollapsingToolbarLayout.this);
         }

         var4 = CollapsingToolbarLayout.this.getHeight();
         var5 = ViewCompat.getMinimumHeight(CollapsingToolbarLayout.this);
         CollapsingToolbarLayout.this.collapsingTextHelper.setExpansionFraction((float)Math.abs(var2) / (float)(var4 - var5 - var3));
      }
   }
}
