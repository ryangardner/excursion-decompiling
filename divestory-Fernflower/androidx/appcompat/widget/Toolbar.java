package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
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
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends ViewGroup {
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
   private Toolbar.ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
   private int mGravity;
   private final ArrayList<View> mHiddenViews;
   private ImageView mLogoView;
   private int mMaxButtonHeight;
   private MenuBuilder.Callback mMenuBuilderCallback;
   private ActionMenuView mMenuView;
   private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
   private ImageButton mNavButtonView;
   Toolbar.OnMenuItemClickListener mOnMenuItemClickListener;
   private ActionMenuPresenter mOuterActionMenuPresenter;
   private Context mPopupContext;
   private int mPopupTheme;
   private final Runnable mShowOverflowMenuRunnable;
   private CharSequence mSubtitleText;
   private int mSubtitleTextAppearance;
   private ColorStateList mSubtitleTextColor;
   private TextView mSubtitleTextView;
   private final int[] mTempMargins;
   private final ArrayList<View> mTempViews;
   private int mTitleMarginBottom;
   private int mTitleMarginEnd;
   private int mTitleMarginStart;
   private int mTitleMarginTop;
   private CharSequence mTitleText;
   private int mTitleTextAppearance;
   private ColorStateList mTitleTextColor;
   private TextView mTitleTextView;
   private ToolbarWidgetWrapper mWrapper;

   public Toolbar(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public Toolbar(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.toolbarStyle);
   }

   public Toolbar(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mGravity = 8388627;
      this.mTempViews = new ArrayList();
      this.mHiddenViews = new ArrayList();
      this.mTempMargins = new int[2];
      this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() {
         public boolean onMenuItemClick(MenuItem var1) {
            return Toolbar.this.mOnMenuItemClickListener != null ? Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(var1) : false;
         }
      };
      this.mShowOverflowMenuRunnable = new Runnable() {
         public void run() {
            Toolbar.this.showOverflowMenu();
         }
      };
      TintTypedArray var4 = TintTypedArray.obtainStyledAttributes(this.getContext(), var2, R.styleable.Toolbar, var3, 0);
      ViewCompat.saveAttributeDataForStyleable(this, var1, R.styleable.Toolbar, var2, var4.getWrappedTypeArray(), var3, 0);
      this.mTitleTextAppearance = var4.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
      this.mSubtitleTextAppearance = var4.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
      this.mGravity = var4.getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
      this.mButtonGravity = var4.getInteger(R.styleable.Toolbar_buttonGravity, 48);
      int var5 = var4.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
      var3 = var5;
      if (var4.hasValue(R.styleable.Toolbar_titleMargins)) {
         var3 = var4.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, var5);
      }

      this.mTitleMarginBottom = var3;
      this.mTitleMarginTop = var3;
      this.mTitleMarginEnd = var3;
      this.mTitleMarginStart = var3;
      var3 = var4.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
      if (var3 >= 0) {
         this.mTitleMarginStart = var3;
      }

      var3 = var4.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1);
      if (var3 >= 0) {
         this.mTitleMarginEnd = var3;
      }

      var3 = var4.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1);
      if (var3 >= 0) {
         this.mTitleMarginTop = var3;
      }

      var3 = var4.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1);
      if (var3 >= 0) {
         this.mTitleMarginBottom = var3;
      }

      this.mMaxButtonHeight = var4.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
      var5 = var4.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
      int var6 = var4.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
      int var7 = var4.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
      var3 = var4.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
      this.ensureContentInsets();
      this.mContentInsets.setAbsolute(var7, var3);
      if (var5 != Integer.MIN_VALUE || var6 != Integer.MIN_VALUE) {
         this.mContentInsets.setRelative(var5, var6);
      }

      this.mContentInsetStartWithNavigation = var4.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
      this.mContentInsetEndWithActions = var4.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
      this.mCollapseIcon = var4.getDrawable(R.styleable.Toolbar_collapseIcon);
      this.mCollapseDescription = var4.getText(R.styleable.Toolbar_collapseContentDescription);
      CharSequence var8 = var4.getText(R.styleable.Toolbar_title);
      if (!TextUtils.isEmpty(var8)) {
         this.setTitle(var8);
      }

      var8 = var4.getText(R.styleable.Toolbar_subtitle);
      if (!TextUtils.isEmpty(var8)) {
         this.setSubtitle(var8);
      }

      this.mPopupContext = this.getContext();
      this.setPopupTheme(var4.getResourceId(R.styleable.Toolbar_popupTheme, 0));
      Drawable var9 = var4.getDrawable(R.styleable.Toolbar_navigationIcon);
      if (var9 != null) {
         this.setNavigationIcon(var9);
      }

      var8 = var4.getText(R.styleable.Toolbar_navigationContentDescription);
      if (!TextUtils.isEmpty(var8)) {
         this.setNavigationContentDescription(var8);
      }

      var9 = var4.getDrawable(R.styleable.Toolbar_logo);
      if (var9 != null) {
         this.setLogo(var9);
      }

      var8 = var4.getText(R.styleable.Toolbar_logoDescription);
      if (!TextUtils.isEmpty(var8)) {
         this.setLogoDescription(var8);
      }

      if (var4.hasValue(R.styleable.Toolbar_titleTextColor)) {
         this.setTitleTextColor(var4.getColorStateList(R.styleable.Toolbar_titleTextColor));
      }

      if (var4.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
         this.setSubtitleTextColor(var4.getColorStateList(R.styleable.Toolbar_subtitleTextColor));
      }

      if (var4.hasValue(R.styleable.Toolbar_menu)) {
         this.inflateMenu(var4.getResourceId(R.styleable.Toolbar_menu, 0));
      }

      var4.recycle();
   }

   private void addCustomViewsWithGravity(List<View> var1, int var2) {
      int var3 = ViewCompat.getLayoutDirection(this);
      byte var4 = 0;
      boolean var9;
      if (var3 == 1) {
         var9 = true;
      } else {
         var9 = false;
      }

      int var5 = this.getChildCount();
      int var6 = GravityCompat.getAbsoluteGravity(var2, ViewCompat.getLayoutDirection(this));
      var1.clear();
      var2 = var4;
      View var7;
      Toolbar.LayoutParams var8;
      if (var9) {
         for(var2 = var5 - 1; var2 >= 0; --var2) {
            var7 = this.getChildAt(var2);
            var8 = (Toolbar.LayoutParams)var7.getLayoutParams();
            if (var8.mViewType == 0 && this.shouldLayout(var7) && this.getChildHorizontalGravity(var8.gravity) == var6) {
               var1.add(var7);
            }
         }
      } else {
         for(; var2 < var5; ++var2) {
            var7 = this.getChildAt(var2);
            var8 = (Toolbar.LayoutParams)var7.getLayoutParams();
            if (var8.mViewType == 0 && this.shouldLayout(var7) && this.getChildHorizontalGravity(var8.gravity) == var6) {
               var1.add(var7);
            }
         }
      }

   }

   private void addSystemView(View var1, boolean var2) {
      android.view.ViewGroup.LayoutParams var3 = var1.getLayoutParams();
      Toolbar.LayoutParams var4;
      if (var3 == null) {
         var4 = this.generateDefaultLayoutParams();
      } else if (!this.checkLayoutParams(var3)) {
         var4 = this.generateLayoutParams(var3);
      } else {
         var4 = (Toolbar.LayoutParams)var3;
      }

      var4.mViewType = 1;
      if (var2 && this.mExpandedActionView != null) {
         var1.setLayoutParams(var4);
         this.mHiddenViews.add(var1);
      } else {
         this.addView(var1, var4);
      }

   }

   private void ensureContentInsets() {
      if (this.mContentInsets == null) {
         this.mContentInsets = new RtlSpacingHelper();
      }

   }

   private void ensureLogoView() {
      if (this.mLogoView == null) {
         this.mLogoView = new AppCompatImageView(this.getContext());
      }

   }

   private void ensureMenu() {
      this.ensureMenuView();
      if (this.mMenuView.peekMenu() == null) {
         MenuBuilder var1 = (MenuBuilder)this.mMenuView.getMenu();
         if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new Toolbar.ExpandedActionViewMenuPresenter();
         }

         this.mMenuView.setExpandedActionViewsExclusive(true);
         var1.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
      }

   }

   private void ensureMenuView() {
      if (this.mMenuView == null) {
         ActionMenuView var1 = new ActionMenuView(this.getContext());
         this.mMenuView = var1;
         var1.setPopupTheme(this.mPopupTheme);
         this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
         this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
         Toolbar.LayoutParams var2 = this.generateDefaultLayoutParams();
         var2.gravity = 8388613 | this.mButtonGravity & 112;
         this.mMenuView.setLayoutParams(var2);
         this.addSystemView(this.mMenuView, false);
      }

   }

   private void ensureNavButtonView() {
      if (this.mNavButtonView == null) {
         this.mNavButtonView = new AppCompatImageButton(this.getContext(), (AttributeSet)null, R.attr.toolbarNavigationButtonStyle);
         Toolbar.LayoutParams var1 = this.generateDefaultLayoutParams();
         var1.gravity = 8388611 | this.mButtonGravity & 112;
         this.mNavButtonView.setLayoutParams(var1);
      }

   }

   private int getChildHorizontalGravity(int var1) {
      int var2 = ViewCompat.getLayoutDirection(this);
      int var3 = GravityCompat.getAbsoluteGravity(var1, var2) & 7;
      if (var3 != 1) {
         byte var4 = 3;
         if (var3 != 3 && var3 != 5) {
            if (var2 == 1) {
               var4 = 5;
            }

            return var4;
         }
      }

      return var3;
   }

   private int getChildTop(View var1, int var2) {
      Toolbar.LayoutParams var3 = (Toolbar.LayoutParams)var1.getLayoutParams();
      int var4 = var1.getMeasuredHeight();
      if (var2 > 0) {
         var2 = (var4 - var2) / 2;
      } else {
         var2 = 0;
      }

      int var5 = this.getChildVerticalGravity(var3.gravity);
      if (var5 != 48) {
         if (var5 != 80) {
            int var6 = this.getPaddingTop();
            int var7 = this.getPaddingBottom();
            var2 = this.getHeight();
            var5 = (var2 - var6 - var7 - var4) / 2;
            if (var5 < var3.topMargin) {
               var2 = var3.topMargin;
            } else {
               var4 = var2 - var7 - var4 - var5 - var6;
               var2 = var5;
               if (var4 < var3.bottomMargin) {
                  var2 = Math.max(0, var5 - (var3.bottomMargin - var4));
               }
            }

            return var6 + var2;
         } else {
            return this.getHeight() - this.getPaddingBottom() - var4 - var3.bottomMargin - var2;
         }
      } else {
         return this.getPaddingTop() - var2;
      }
   }

   private int getChildVerticalGravity(int var1) {
      int var2 = var1 & 112;
      var1 = var2;
      if (var2 != 16) {
         var1 = var2;
         if (var2 != 48) {
            var1 = var2;
            if (var2 != 80) {
               var1 = this.mGravity & 112;
            }
         }
      }

      return var1;
   }

   private int getHorizontalMargins(View var1) {
      MarginLayoutParams var2 = (MarginLayoutParams)var1.getLayoutParams();
      return MarginLayoutParamsCompat.getMarginStart(var2) + MarginLayoutParamsCompat.getMarginEnd(var2);
   }

   private MenuInflater getMenuInflater() {
      return new SupportMenuInflater(this.getContext());
   }

   private int getVerticalMargins(View var1) {
      MarginLayoutParams var2 = (MarginLayoutParams)var1.getLayoutParams();
      return var2.topMargin + var2.bottomMargin;
   }

   private int getViewListMeasuredWidth(List<View> var1, int[] var2) {
      int var3 = var2[0];
      int var4 = var2[1];
      int var5 = var1.size();
      int var6 = 0;

      int var7;
      for(var7 = 0; var6 < var5; ++var6) {
         View var8 = (View)var1.get(var6);
         Toolbar.LayoutParams var11 = (Toolbar.LayoutParams)var8.getLayoutParams();
         var3 = var11.leftMargin - var3;
         var4 = var11.rightMargin - var4;
         int var9 = Math.max(0, var3);
         int var10 = Math.max(0, var4);
         var3 = Math.max(0, -var3);
         var4 = Math.max(0, -var4);
         var7 += var9 + var8.getMeasuredWidth() + var10;
      }

      return var7;
   }

   private boolean isChildOrHidden(View var1) {
      boolean var2;
      if (var1.getParent() != this && !this.mHiddenViews.contains(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private int layoutChildLeft(View var1, int var2, int[] var3, int var4) {
      Toolbar.LayoutParams var5 = (Toolbar.LayoutParams)var1.getLayoutParams();
      int var6 = var5.leftMargin - var3[0];
      var2 += Math.max(0, var6);
      var3[0] = Math.max(0, -var6);
      var4 = this.getChildTop(var1, var4);
      var6 = var1.getMeasuredWidth();
      var1.layout(var2, var4, var2 + var6, var1.getMeasuredHeight() + var4);
      return var2 + var6 + var5.rightMargin;
   }

   private int layoutChildRight(View var1, int var2, int[] var3, int var4) {
      Toolbar.LayoutParams var5 = (Toolbar.LayoutParams)var1.getLayoutParams();
      int var6 = var5.rightMargin - var3[1];
      var2 -= Math.max(0, var6);
      var3[1] = Math.max(0, -var6);
      var6 = this.getChildTop(var1, var4);
      var4 = var1.getMeasuredWidth();
      var1.layout(var2 - var4, var6, var2, var1.getMeasuredHeight() + var6);
      return var2 - (var4 + var5.leftMargin);
   }

   private int measureChildCollapseMargins(View var1, int var2, int var3, int var4, int var5, int[] var6) {
      MarginLayoutParams var7 = (MarginLayoutParams)var1.getLayoutParams();
      int var8 = var7.leftMargin - var6[0];
      int var9 = var7.rightMargin - var6[1];
      int var10 = Math.max(0, var8) + Math.max(0, var9);
      var6[0] = Math.max(0, -var8);
      var6[1] = Math.max(0, -var9);
      var1.measure(getChildMeasureSpec(var2, this.getPaddingLeft() + this.getPaddingRight() + var10 + var3, var7.width), getChildMeasureSpec(var4, this.getPaddingTop() + this.getPaddingBottom() + var7.topMargin + var7.bottomMargin + var5, var7.height));
      return var1.getMeasuredWidth() + var10;
   }

   private void measureChildConstrained(View var1, int var2, int var3, int var4, int var5, int var6) {
      MarginLayoutParams var7 = (MarginLayoutParams)var1.getLayoutParams();
      int var8 = getChildMeasureSpec(var2, this.getPaddingLeft() + this.getPaddingRight() + var7.leftMargin + var7.rightMargin + var3, var7.width);
      var3 = getChildMeasureSpec(var4, this.getPaddingTop() + this.getPaddingBottom() + var7.topMargin + var7.bottomMargin + var5, var7.height);
      var4 = MeasureSpec.getMode(var3);
      var2 = var3;
      if (var4 != 1073741824) {
         var2 = var3;
         if (var6 >= 0) {
            var2 = var6;
            if (var4 != 0) {
               var2 = Math.min(MeasureSpec.getSize(var3), var6);
            }

            var2 = MeasureSpec.makeMeasureSpec(var2, 1073741824);
         }
      }

      var1.measure(var8, var2);
   }

   private void postShowOverflowMenu() {
      this.removeCallbacks(this.mShowOverflowMenuRunnable);
      this.post(this.mShowOverflowMenuRunnable);
   }

   private boolean shouldCollapse() {
      if (!this.mCollapsible) {
         return false;
      } else {
         int var1 = this.getChildCount();

         for(int var2 = 0; var2 < var1; ++var2) {
            View var3 = this.getChildAt(var2);
            if (this.shouldLayout(var3) && var3.getMeasuredWidth() > 0 && var3.getMeasuredHeight() > 0) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean shouldLayout(View var1) {
      boolean var2;
      if (var1 != null && var1.getParent() == this && var1.getVisibility() != 8) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   void addChildrenForExpandedActionView() {
      for(int var1 = this.mHiddenViews.size() - 1; var1 >= 0; --var1) {
         this.addView((View)this.mHiddenViews.get(var1));
      }

      this.mHiddenViews.clear();
   }

   public boolean canShowOverflowMenu() {
      boolean var2;
      if (this.getVisibility() == 0) {
         ActionMenuView var1 = this.mMenuView;
         if (var1 != null && var1.isOverflowReserved()) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      boolean var2;
      if (super.checkLayoutParams(var1) && var1 instanceof Toolbar.LayoutParams) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void collapseActionView() {
      Toolbar.ExpandedActionViewMenuPresenter var1 = this.mExpandedMenuPresenter;
      MenuItemImpl var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.mCurrentExpandedItem;
      }

      if (var2 != null) {
         var2.collapseActionView();
      }

   }

   public void dismissPopupMenus() {
      ActionMenuView var1 = this.mMenuView;
      if (var1 != null) {
         var1.dismissPopupMenus();
      }

   }

   void ensureCollapseButtonView() {
      if (this.mCollapseButtonView == null) {
         AppCompatImageButton var1 = new AppCompatImageButton(this.getContext(), (AttributeSet)null, R.attr.toolbarNavigationButtonStyle);
         this.mCollapseButtonView = var1;
         var1.setImageDrawable(this.mCollapseIcon);
         this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
         Toolbar.LayoutParams var2 = this.generateDefaultLayoutParams();
         var2.gravity = 8388611 | this.mButtonGravity & 112;
         var2.mViewType = 2;
         this.mCollapseButtonView.setLayoutParams(var2);
         this.mCollapseButtonView.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               Toolbar.this.collapseActionView();
            }
         });
      }

   }

   protected Toolbar.LayoutParams generateDefaultLayoutParams() {
      return new Toolbar.LayoutParams(-2, -2);
   }

   public Toolbar.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new Toolbar.LayoutParams(this.getContext(), var1);
   }

   protected Toolbar.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      if (var1 instanceof Toolbar.LayoutParams) {
         return new Toolbar.LayoutParams((Toolbar.LayoutParams)var1);
      } else if (var1 instanceof ActionBar.LayoutParams) {
         return new Toolbar.LayoutParams((ActionBar.LayoutParams)var1);
      } else {
         return var1 instanceof MarginLayoutParams ? new Toolbar.LayoutParams((MarginLayoutParams)var1) : new Toolbar.LayoutParams(var1);
      }
   }

   public CharSequence getCollapseContentDescription() {
      ImageButton var1 = this.mCollapseButtonView;
      CharSequence var2;
      if (var1 != null) {
         var2 = var1.getContentDescription();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Drawable getCollapseIcon() {
      ImageButton var1 = this.mCollapseButtonView;
      Drawable var2;
      if (var1 != null) {
         var2 = var1.getDrawable();
      } else {
         var2 = null;
      }

      return var2;
   }

   public int getContentInsetEnd() {
      RtlSpacingHelper var1 = this.mContentInsets;
      int var2;
      if (var1 != null) {
         var2 = var1.getEnd();
      } else {
         var2 = 0;
      }

      return var2;
   }

   public int getContentInsetEndWithActions() {
      int var1 = this.mContentInsetEndWithActions;
      if (var1 == Integer.MIN_VALUE) {
         var1 = this.getContentInsetEnd();
      }

      return var1;
   }

   public int getContentInsetLeft() {
      RtlSpacingHelper var1 = this.mContentInsets;
      int var2;
      if (var1 != null) {
         var2 = var1.getLeft();
      } else {
         var2 = 0;
      }

      return var2;
   }

   public int getContentInsetRight() {
      RtlSpacingHelper var1 = this.mContentInsets;
      int var2;
      if (var1 != null) {
         var2 = var1.getRight();
      } else {
         var2 = 0;
      }

      return var2;
   }

   public int getContentInsetStart() {
      RtlSpacingHelper var1 = this.mContentInsets;
      int var2;
      if (var1 != null) {
         var2 = var1.getStart();
      } else {
         var2 = 0;
      }

      return var2;
   }

   public int getContentInsetStartWithNavigation() {
      int var1 = this.mContentInsetStartWithNavigation;
      if (var1 == Integer.MIN_VALUE) {
         var1 = this.getContentInsetStart();
      }

      return var1;
   }

   public int getCurrentContentInsetEnd() {
      boolean var2;
      label19: {
         ActionMenuView var1 = this.mMenuView;
         if (var1 != null) {
            MenuBuilder var3 = var1.peekMenu();
            if (var3 != null && var3.hasVisibleItems()) {
               var2 = true;
               break label19;
            }
         }

         var2 = false;
      }

      int var4;
      if (var2) {
         var4 = Math.max(this.getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
      } else {
         var4 = this.getContentInsetEnd();
      }

      return var4;
   }

   public int getCurrentContentInsetLeft() {
      int var1;
      if (ViewCompat.getLayoutDirection(this) == 1) {
         var1 = this.getCurrentContentInsetEnd();
      } else {
         var1 = this.getCurrentContentInsetStart();
      }

      return var1;
   }

   public int getCurrentContentInsetRight() {
      int var1;
      if (ViewCompat.getLayoutDirection(this) == 1) {
         var1 = this.getCurrentContentInsetStart();
      } else {
         var1 = this.getCurrentContentInsetEnd();
      }

      return var1;
   }

   public int getCurrentContentInsetStart() {
      int var1;
      if (this.getNavigationIcon() != null) {
         var1 = Math.max(this.getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
      } else {
         var1 = this.getContentInsetStart();
      }

      return var1;
   }

   public Drawable getLogo() {
      ImageView var1 = this.mLogoView;
      Drawable var2;
      if (var1 != null) {
         var2 = var1.getDrawable();
      } else {
         var2 = null;
      }

      return var2;
   }

   public CharSequence getLogoDescription() {
      ImageView var1 = this.mLogoView;
      CharSequence var2;
      if (var1 != null) {
         var2 = var1.getContentDescription();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Menu getMenu() {
      this.ensureMenu();
      return this.mMenuView.getMenu();
   }

   public CharSequence getNavigationContentDescription() {
      ImageButton var1 = this.mNavButtonView;
      CharSequence var2;
      if (var1 != null) {
         var2 = var1.getContentDescription();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Drawable getNavigationIcon() {
      ImageButton var1 = this.mNavButtonView;
      Drawable var2;
      if (var1 != null) {
         var2 = var1.getDrawable();
      } else {
         var2 = null;
      }

      return var2;
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
      if (this.mWrapper == null) {
         this.mWrapper = new ToolbarWidgetWrapper(this, true);
      }

      return this.mWrapper;
   }

   public boolean hasExpandedActionView() {
      Toolbar.ExpandedActionViewMenuPresenter var1 = this.mExpandedMenuPresenter;
      boolean var2;
      if (var1 != null && var1.mCurrentExpandedItem != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean hideOverflowMenu() {
      ActionMenuView var1 = this.mMenuView;
      boolean var2;
      if (var1 != null && var1.hideOverflowMenu()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void inflateMenu(int var1) {
      this.getMenuInflater().inflate(var1, this.getMenu());
   }

   public boolean isOverflowMenuShowPending() {
      ActionMenuView var1 = this.mMenuView;
      boolean var2;
      if (var1 != null && var1.isOverflowMenuShowPending()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isOverflowMenuShowing() {
      ActionMenuView var1 = this.mMenuView;
      boolean var2;
      if (var1 != null && var1.isOverflowMenuShowing()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isTitleTruncated() {
      TextView var1 = this.mTitleTextView;
      if (var1 == null) {
         return false;
      } else {
         Layout var4 = var1.getLayout();
         if (var4 == null) {
            return false;
         } else {
            int var2 = var4.getLineCount();

            for(int var3 = 0; var3 < var2; ++var3) {
               if (var4.getEllipsisCount(var3) > 0) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.removeCallbacks(this.mShowOverflowMenuRunnable);
   }

   public boolean onHoverEvent(MotionEvent var1) {
      int var2 = var1.getActionMasked();
      if (var2 == 9) {
         this.mEatingHover = false;
      }

      if (!this.mEatingHover) {
         boolean var3 = super.onHoverEvent(var1);
         if (var2 == 9 && !var3) {
            this.mEatingHover = true;
         }
      }

      if (var2 == 10 || var2 == 3) {
         this.mEatingHover = false;
      }

      return true;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      boolean var6;
      if (ViewCompat.getLayoutDirection(this) == 1) {
         var6 = true;
      } else {
         var6 = false;
      }

      int var7 = this.getWidth();
      int var8 = this.getHeight();
      var4 = this.getPaddingLeft();
      int var9 = this.getPaddingRight();
      int var10 = this.getPaddingTop();
      int var11 = this.getPaddingBottom();
      int var12 = var7 - var9;
      int[] var13 = this.mTempMargins;
      var13[1] = 0;
      var13[0] = 0;
      var2 = ViewCompat.getMinimumHeight(this);
      if (var2 >= 0) {
         var5 = Math.min(var2, var5 - var3);
      } else {
         var5 = 0;
      }

      int var14;
      int var15;
      label187: {
         if (this.shouldLayout(this.mNavButtonView)) {
            if (var6) {
               var14 = this.layoutChildRight(this.mNavButtonView, var12, var13, var5);
               var15 = var4;
               break label187;
            }

            var15 = this.layoutChildLeft(this.mNavButtonView, var4, var13, var5);
         } else {
            var15 = var4;
         }

         var14 = var12;
      }

      var3 = var15;
      var2 = var14;
      if (this.shouldLayout(this.mCollapseButtonView)) {
         if (var6) {
            var2 = this.layoutChildRight(this.mCollapseButtonView, var14, var13, var5);
            var3 = var15;
         } else {
            var3 = this.layoutChildLeft(this.mCollapseButtonView, var15, var13, var5);
            var2 = var14;
         }
      }

      var14 = var3;
      var15 = var2;
      if (this.shouldLayout(this.mMenuView)) {
         if (var6) {
            var14 = this.layoutChildLeft(this.mMenuView, var3, var13, var5);
            var15 = var2;
         } else {
            var15 = this.layoutChildRight(this.mMenuView, var2, var13, var5);
            var14 = var3;
         }
      }

      var3 = this.getCurrentContentInsetLeft();
      var2 = this.getCurrentContentInsetRight();
      var13[0] = Math.max(0, var3 - var14);
      var13[1] = Math.max(0, var2 - (var12 - var15));
      var3 = Math.max(var14, var3);
      var15 = Math.min(var15, var12 - var2);
      var2 = var3;
      var14 = var15;
      if (this.shouldLayout(this.mExpandedActionView)) {
         if (var6) {
            var14 = this.layoutChildRight(this.mExpandedActionView, var15, var13, var5);
            var2 = var3;
         } else {
            var2 = this.layoutChildLeft(this.mExpandedActionView, var3, var13, var5);
            var14 = var15;
         }
      }

      var15 = var2;
      var3 = var14;
      if (this.shouldLayout(this.mLogoView)) {
         if (var6) {
            var3 = this.layoutChildRight(this.mLogoView, var14, var13, var5);
            var15 = var2;
         } else {
            var15 = this.layoutChildLeft(this.mLogoView, var2, var13, var5);
            var3 = var14;
         }
      }

      var1 = this.shouldLayout(this.mTitleTextView);
      boolean var16 = this.shouldLayout(this.mSubtitleTextView);
      Toolbar.LayoutParams var17;
      if (var1) {
         var17 = (Toolbar.LayoutParams)this.mTitleTextView.getLayoutParams();
         var2 = var17.topMargin + this.mTitleTextView.getMeasuredHeight() + var17.bottomMargin + 0;
      } else {
         var2 = 0;
      }

      if (var16) {
         var17 = (Toolbar.LayoutParams)this.mSubtitleTextView.getLayoutParams();
         var2 += var17.topMargin + this.mSubtitleTextView.getMeasuredHeight() + var17.bottomMargin;
      }

      int var20;
      label196: {
         if (var1 || var16) {
            TextView var21;
            if (var1) {
               var21 = this.mTitleTextView;
            } else {
               var21 = this.mSubtitleTextView;
            }

            TextView var18;
            if (var16) {
               var18 = this.mSubtitleTextView;
            } else {
               var18 = this.mTitleTextView;
            }

            var17 = (Toolbar.LayoutParams)var21.getLayoutParams();
            Toolbar.LayoutParams var23 = (Toolbar.LayoutParams)var18.getLayoutParams();
            boolean var22;
            if ((!var1 || this.mTitleTextView.getMeasuredWidth() <= 0) && (!var16 || this.mSubtitleTextView.getMeasuredWidth() <= 0)) {
               var22 = false;
            } else {
               var22 = true;
            }

            var12 = this.mGravity & 112;
            if (var12 != 48) {
               if (var12 != 80) {
                  var12 = (var8 - var10 - var11 - var2) / 2;
                  if (var12 < var17.topMargin + this.mTitleMarginTop) {
                     var2 = var17.topMargin + this.mTitleMarginTop;
                  } else {
                     var11 = var8 - var11 - var2 - var12 - var10;
                     var2 = var12;
                     if (var11 < var17.bottomMargin + this.mTitleMarginBottom) {
                        var2 = Math.max(0, var12 - (var23.bottomMargin + this.mTitleMarginBottom - var11));
                     }
                  }

                  var2 += var10;
               } else {
                  var2 = var8 - var11 - var23.bottomMargin - this.mTitleMarginBottom - var2;
               }
            } else {
               var2 = this.getPaddingTop() + var17.topMargin + this.mTitleMarginTop;
            }

            if (!var6) {
               if (var22) {
                  var20 = this.mTitleMarginStart;
               } else {
                  var20 = 0;
               }

               var20 -= var13[0];
               var15 += Math.max(0, var20);
               var13[0] = Math.max(0, -var20);
               if (var1) {
                  var17 = (Toolbar.LayoutParams)this.mTitleTextView.getLayoutParams();
                  var20 = this.mTitleTextView.getMeasuredWidth() + var15;
                  var12 = this.mTitleTextView.getMeasuredHeight() + var2;
                  this.mTitleTextView.layout(var15, var2, var20, var12);
                  var20 += this.mTitleMarginEnd;
                  var2 = var12 + var17.bottomMargin;
               } else {
                  var20 = var15;
               }

               if (var16) {
                  var17 = (Toolbar.LayoutParams)this.mSubtitleTextView.getLayoutParams();
                  var10 = var2 + var17.topMargin;
                  var2 = this.mSubtitleTextView.getMeasuredWidth() + var15;
                  var12 = this.mSubtitleTextView.getMeasuredHeight();
                  this.mSubtitleTextView.layout(var15, var10, var2, var12 + var10);
                  var12 = var2 + this.mTitleMarginEnd;
                  var2 = var17.bottomMargin;
               } else {
                  var12 = var15;
               }

               var2 = var15;
               var15 = var3;
               if (var22) {
                  var2 = Math.max(var20, var12);
                  var15 = var3;
               }
               break label196;
            }

            if (var22) {
               var20 = this.mTitleMarginStart;
            } else {
               var20 = 0;
            }

            var20 -= var13[1];
            var3 -= Math.max(0, var20);
            var13[1] = Math.max(0, -var20);
            if (var1) {
               var17 = (Toolbar.LayoutParams)this.mTitleTextView.getLayoutParams();
               var12 = var3 - this.mTitleTextView.getMeasuredWidth();
               var20 = this.mTitleTextView.getMeasuredHeight() + var2;
               this.mTitleTextView.layout(var12, var2, var3, var20);
               var2 = var12 - this.mTitleMarginEnd;
               var12 = var20 + var17.bottomMargin;
            } else {
               var12 = var2;
               var2 = var3;
            }

            if (var16) {
               var17 = (Toolbar.LayoutParams)this.mSubtitleTextView.getLayoutParams();
               var20 = var12 + var17.topMargin;
               var10 = this.mSubtitleTextView.getMeasuredWidth();
               var12 = this.mSubtitleTextView.getMeasuredHeight();
               this.mSubtitleTextView.layout(var3 - var10, var20, var3, var12 + var20);
               var20 = var3 - this.mTitleMarginEnd;
               var12 = var17.bottomMargin;
            } else {
               var20 = var3;
            }

            if (var22) {
               var3 = Math.min(var2, var20);
            }
         }

         var2 = var15;
         var15 = var3;
      }

      byte var19 = 0;
      this.addCustomViewsWithGravity(this.mTempViews, 3);
      var20 = this.mTempViews.size();

      for(var3 = 0; var3 < var20; ++var3) {
         var2 = this.layoutChildLeft((View)this.mTempViews.get(var3), var2, var13, var5);
      }

      this.addCustomViewsWithGravity(this.mTempViews, 5);
      var20 = this.mTempViews.size();

      for(var3 = 0; var3 < var20; ++var3) {
         var15 = this.layoutChildRight((View)this.mTempViews.get(var3), var15, var13, var5);
      }

      this.addCustomViewsWithGravity(this.mTempViews, 1);
      var20 = this.getViewListMeasuredWidth(this.mTempViews, var13);
      var3 = var4 + (var7 - var4 - var9) / 2 - var20 / 2;
      var14 = var20 + var3;
      if (var3 >= var2) {
         if (var14 > var15) {
            var2 = var3 - (var14 - var15);
         } else {
            var2 = var3;
         }
      }

      var15 = this.mTempViews.size();

      for(var3 = var19; var3 < var15; ++var3) {
         var2 = this.layoutChildLeft((View)this.mTempViews.get(var3), var2, var13, var5);
      }

      this.mTempViews.clear();
   }

   protected void onMeasure(int var1, int var2) {
      int[] var3 = this.mTempMargins;
      boolean var4 = ViewUtils.isLayoutRtl(this);
      byte var5 = 0;
      byte var6;
      byte var7;
      if (var4) {
         var6 = 1;
         var7 = 0;
      } else {
         var6 = 0;
         var7 = 1;
      }

      int var8;
      int var9;
      int var10;
      if (this.shouldLayout(this.mNavButtonView)) {
         this.measureChildConstrained(this.mNavButtonView, var1, 0, var2, 0, this.mMaxButtonHeight);
         var8 = this.mNavButtonView.getMeasuredWidth() + this.getHorizontalMargins(this.mNavButtonView);
         var9 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + this.getVerticalMargins(this.mNavButtonView));
         var10 = View.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
      } else {
         var8 = 0;
         var9 = 0;
         var10 = 0;
      }

      int var11 = var8;
      int var12 = var9;
      var8 = var10;
      if (this.shouldLayout(this.mCollapseButtonView)) {
         this.measureChildConstrained(this.mCollapseButtonView, var1, 0, var2, 0, this.mMaxButtonHeight);
         var11 = this.mCollapseButtonView.getMeasuredWidth() + this.getHorizontalMargins(this.mCollapseButtonView);
         var12 = Math.max(var9, this.mCollapseButtonView.getMeasuredHeight() + this.getVerticalMargins(this.mCollapseButtonView));
         var8 = View.combineMeasuredStates(var10, this.mCollapseButtonView.getMeasuredState());
      }

      var10 = this.getCurrentContentInsetStart();
      var9 = 0 + Math.max(var10, var11);
      var3[var6] = Math.max(0, var10 - var11);
      if (this.shouldLayout(this.mMenuView)) {
         this.measureChildConstrained(this.mMenuView, var1, var9, var2, 0, this.mMaxButtonHeight);
         var10 = this.mMenuView.getMeasuredWidth() + this.getHorizontalMargins(this.mMenuView);
         var12 = Math.max(var12, this.mMenuView.getMeasuredHeight() + this.getVerticalMargins(this.mMenuView));
         var8 = View.combineMeasuredStates(var8, this.mMenuView.getMeasuredState());
      } else {
         var10 = 0;
      }

      int var16 = this.getCurrentContentInsetEnd();
      var11 = var9 + Math.max(var16, var10);
      var3[var7] = Math.max(0, var16 - var10);
      var16 = var12;
      var10 = var8;
      var9 = var11;
      if (this.shouldLayout(this.mExpandedActionView)) {
         var9 = var11 + this.measureChildCollapseMargins(this.mExpandedActionView, var1, var11, var2, 0, var3);
         var16 = Math.max(var12, this.mExpandedActionView.getMeasuredHeight() + this.getVerticalMargins(this.mExpandedActionView));
         var10 = View.combineMeasuredStates(var8, this.mExpandedActionView.getMeasuredState());
      }

      var12 = var16;
      var8 = var10;
      int var17 = var9;
      if (this.shouldLayout(this.mLogoView)) {
         var17 = var9 + this.measureChildCollapseMargins(this.mLogoView, var1, var9, var2, 0, var3);
         var12 = Math.max(var16, this.mLogoView.getMeasuredHeight() + this.getVerticalMargins(this.mLogoView));
         var8 = View.combineMeasuredStates(var10, this.mLogoView.getMeasuredState());
      }

      int var13 = this.getChildCount();
      byte var18 = 0;
      var9 = var12;

      for(var12 = var18; var12 < var13; var17 = var10) {
         View var14 = this.getChildAt(var12);
         var11 = var9;
         var16 = var8;
         var10 = var17;
         if (((Toolbar.LayoutParams)var14.getLayoutParams()).mViewType == 0) {
            if (!this.shouldLayout(var14)) {
               var11 = var9;
               var16 = var8;
               var10 = var17;
            } else {
               var10 = var17 + this.measureChildCollapseMargins(var14, var1, var17, var2, 0, var3);
               var11 = Math.max(var9, var14.getMeasuredHeight() + this.getVerticalMargins(var14));
               var16 = View.combineMeasuredStates(var8, var14.getMeasuredState());
            }
         }

         ++var12;
         var9 = var11;
         var8 = var16;
      }

      var16 = this.mTitleMarginTop + this.mTitleMarginBottom;
      var11 = this.mTitleMarginStart + this.mTitleMarginEnd;
      if (this.shouldLayout(this.mTitleTextView)) {
         this.measureChildCollapseMargins(this.mTitleTextView, var1, var17 + var11, var2, var16, var3);
         var13 = this.mTitleTextView.getMeasuredWidth();
         var12 = this.getHorizontalMargins(this.mTitleTextView);
         int var15 = this.mTitleTextView.getMeasuredHeight();
         var10 = this.getVerticalMargins(this.mTitleTextView);
         var8 = View.combineMeasuredStates(var8, this.mTitleTextView.getMeasuredState());
         var10 += var15;
         var12 += var13;
      } else {
         var12 = 0;
         var10 = 0;
      }

      if (this.shouldLayout(this.mSubtitleTextView)) {
         var12 = Math.max(var12, this.measureChildCollapseMargins(this.mSubtitleTextView, var1, var17 + var11, var2, var10 + var16, var3));
         var10 += this.mSubtitleTextView.getMeasuredHeight() + this.getVerticalMargins(this.mSubtitleTextView);
         var8 = View.combineMeasuredStates(var8, this.mSubtitleTextView.getMeasuredState());
      }

      var16 = Math.max(var9, var10);
      var13 = this.getPaddingLeft();
      var11 = this.getPaddingRight();
      var9 = this.getPaddingTop();
      var10 = this.getPaddingBottom();
      var12 = View.resolveSizeAndState(Math.max(var17 + var12 + var13 + var11, this.getSuggestedMinimumWidth()), var1, -16777216 & var8);
      var1 = View.resolveSizeAndState(Math.max(var16 + var9 + var10, this.getSuggestedMinimumHeight()), var2, var8 << 16);
      if (this.shouldCollapse()) {
         var1 = var5;
      }

      this.setMeasuredDimension(var12, var1);
   }

   protected void onRestoreInstanceState(Parcelable var1) {
      if (!(var1 instanceof Toolbar.SavedState)) {
         super.onRestoreInstanceState(var1);
      } else {
         Toolbar.SavedState var2 = (Toolbar.SavedState)var1;
         super.onRestoreInstanceState(var2.getSuperState());
         ActionMenuView var3 = this.mMenuView;
         MenuBuilder var4;
         if (var3 != null) {
            var4 = var3.peekMenu();
         } else {
            var4 = null;
         }

         if (var2.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && var4 != null) {
            MenuItem var5 = var4.findItem(var2.expandedMenuItemId);
            if (var5 != null) {
               var5.expandActionView();
            }
         }

         if (var2.isOverflowOpen) {
            this.postShowOverflowMenu();
         }

      }
   }

   public void onRtlPropertiesChanged(int var1) {
      if (VERSION.SDK_INT >= 17) {
         super.onRtlPropertiesChanged(var1);
      }

      this.ensureContentInsets();
      RtlSpacingHelper var2 = this.mContentInsets;
      boolean var3 = true;
      if (var1 != 1) {
         var3 = false;
      }

      var2.setDirection(var3);
   }

   protected Parcelable onSaveInstanceState() {
      Toolbar.SavedState var1 = new Toolbar.SavedState(super.onSaveInstanceState());
      Toolbar.ExpandedActionViewMenuPresenter var2 = this.mExpandedMenuPresenter;
      if (var2 != null && var2.mCurrentExpandedItem != null) {
         var1.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
      }

      var1.isOverflowOpen = this.isOverflowMenuShowing();
      return var1;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      int var2 = var1.getActionMasked();
      if (var2 == 0) {
         this.mEatingTouch = false;
      }

      if (!this.mEatingTouch) {
         boolean var3 = super.onTouchEvent(var1);
         if (var2 == 0 && !var3) {
            this.mEatingTouch = true;
         }
      }

      if (var2 == 1 || var2 == 3) {
         this.mEatingTouch = false;
      }

      return true;
   }

   void removeChildrenForExpandedActionView() {
      for(int var1 = this.getChildCount() - 1; var1 >= 0; --var1) {
         View var2 = this.getChildAt(var1);
         if (((Toolbar.LayoutParams)var2.getLayoutParams()).mViewType != 2 && var2 != this.mMenuView) {
            this.removeViewAt(var1);
            this.mHiddenViews.add(var2);
         }
      }

   }

   public void setCollapseContentDescription(int var1) {
      CharSequence var2;
      if (var1 != 0) {
         var2 = this.getContext().getText(var1);
      } else {
         var2 = null;
      }

      this.setCollapseContentDescription(var2);
   }

   public void setCollapseContentDescription(CharSequence var1) {
      if (!TextUtils.isEmpty(var1)) {
         this.ensureCollapseButtonView();
      }

      ImageButton var2 = this.mCollapseButtonView;
      if (var2 != null) {
         var2.setContentDescription(var1);
      }

   }

   public void setCollapseIcon(int var1) {
      this.setCollapseIcon(AppCompatResources.getDrawable(this.getContext(), var1));
   }

   public void setCollapseIcon(Drawable var1) {
      if (var1 != null) {
         this.ensureCollapseButtonView();
         this.mCollapseButtonView.setImageDrawable(var1);
      } else {
         ImageButton var2 = this.mCollapseButtonView;
         if (var2 != null) {
            var2.setImageDrawable(this.mCollapseIcon);
         }
      }

   }

   public void setCollapsible(boolean var1) {
      this.mCollapsible = var1;
      this.requestLayout();
   }

   public void setContentInsetEndWithActions(int var1) {
      int var2 = var1;
      if (var1 < 0) {
         var2 = Integer.MIN_VALUE;
      }

      if (var2 != this.mContentInsetEndWithActions) {
         this.mContentInsetEndWithActions = var2;
         if (this.getNavigationIcon() != null) {
            this.requestLayout();
         }
      }

   }

   public void setContentInsetStartWithNavigation(int var1) {
      int var2 = var1;
      if (var1 < 0) {
         var2 = Integer.MIN_VALUE;
      }

      if (var2 != this.mContentInsetStartWithNavigation) {
         this.mContentInsetStartWithNavigation = var2;
         if (this.getNavigationIcon() != null) {
            this.requestLayout();
         }
      }

   }

   public void setContentInsetsAbsolute(int var1, int var2) {
      this.ensureContentInsets();
      this.mContentInsets.setAbsolute(var1, var2);
   }

   public void setContentInsetsRelative(int var1, int var2) {
      this.ensureContentInsets();
      this.mContentInsets.setRelative(var1, var2);
   }

   public void setLogo(int var1) {
      this.setLogo(AppCompatResources.getDrawable(this.getContext(), var1));
   }

   public void setLogo(Drawable var1) {
      ImageView var2;
      if (var1 != null) {
         this.ensureLogoView();
         if (!this.isChildOrHidden(this.mLogoView)) {
            this.addSystemView(this.mLogoView, true);
         }
      } else {
         var2 = this.mLogoView;
         if (var2 != null && this.isChildOrHidden(var2)) {
            this.removeView(this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
         }
      }

      var2 = this.mLogoView;
      if (var2 != null) {
         var2.setImageDrawable(var1);
      }

   }

   public void setLogoDescription(int var1) {
      this.setLogoDescription(this.getContext().getText(var1));
   }

   public void setLogoDescription(CharSequence var1) {
      if (!TextUtils.isEmpty(var1)) {
         this.ensureLogoView();
      }

      ImageView var2 = this.mLogoView;
      if (var2 != null) {
         var2.setContentDescription(var1);
      }

   }

   public void setMenu(MenuBuilder var1, ActionMenuPresenter var2) {
      if (var1 != null || this.mMenuView != null) {
         this.ensureMenuView();
         MenuBuilder var3 = this.mMenuView.peekMenu();
         if (var3 != var1) {
            if (var3 != null) {
               var3.removeMenuPresenter(this.mOuterActionMenuPresenter);
               var3.removeMenuPresenter(this.mExpandedMenuPresenter);
            }

            if (this.mExpandedMenuPresenter == null) {
               this.mExpandedMenuPresenter = new Toolbar.ExpandedActionViewMenuPresenter();
            }

            var2.setExpandedActionViewsExclusive(true);
            if (var1 != null) {
               var1.addMenuPresenter(var2, this.mPopupContext);
               var1.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
            } else {
               var2.initForMenu(this.mPopupContext, (MenuBuilder)null);
               this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, (MenuBuilder)null);
               var2.updateMenuView(true);
               this.mExpandedMenuPresenter.updateMenuView(true);
            }

            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setPresenter(var2);
            this.mOuterActionMenuPresenter = var2;
         }
      }
   }

   public void setMenuCallbacks(MenuPresenter.Callback var1, MenuBuilder.Callback var2) {
      this.mActionMenuPresenterCallback = var1;
      this.mMenuBuilderCallback = var2;
      ActionMenuView var3 = this.mMenuView;
      if (var3 != null) {
         var3.setMenuCallbacks(var1, var2);
      }

   }

   public void setNavigationContentDescription(int var1) {
      CharSequence var2;
      if (var1 != 0) {
         var2 = this.getContext().getText(var1);
      } else {
         var2 = null;
      }

      this.setNavigationContentDescription(var2);
   }

   public void setNavigationContentDescription(CharSequence var1) {
      if (!TextUtils.isEmpty(var1)) {
         this.ensureNavButtonView();
      }

      ImageButton var2 = this.mNavButtonView;
      if (var2 != null) {
         var2.setContentDescription(var1);
      }

   }

   public void setNavigationIcon(int var1) {
      this.setNavigationIcon(AppCompatResources.getDrawable(this.getContext(), var1));
   }

   public void setNavigationIcon(Drawable var1) {
      ImageButton var2;
      if (var1 != null) {
         this.ensureNavButtonView();
         if (!this.isChildOrHidden(this.mNavButtonView)) {
            this.addSystemView(this.mNavButtonView, true);
         }
      } else {
         var2 = this.mNavButtonView;
         if (var2 != null && this.isChildOrHidden(var2)) {
            this.removeView(this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
         }
      }

      var2 = this.mNavButtonView;
      if (var2 != null) {
         var2.setImageDrawable(var1);
      }

   }

   public void setNavigationOnClickListener(OnClickListener var1) {
      this.ensureNavButtonView();
      this.mNavButtonView.setOnClickListener(var1);
   }

   public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener var1) {
      this.mOnMenuItemClickListener = var1;
   }

   public void setOverflowIcon(Drawable var1) {
      this.ensureMenu();
      this.mMenuView.setOverflowIcon(var1);
   }

   public void setPopupTheme(int var1) {
      if (this.mPopupTheme != var1) {
         this.mPopupTheme = var1;
         if (var1 == 0) {
            this.mPopupContext = this.getContext();
         } else {
            this.mPopupContext = new ContextThemeWrapper(this.getContext(), var1);
         }
      }

   }

   public void setSubtitle(int var1) {
      this.setSubtitle(this.getContext().getText(var1));
   }

   public void setSubtitle(CharSequence var1) {
      TextView var6;
      if (!TextUtils.isEmpty(var1)) {
         if (this.mSubtitleTextView == null) {
            Context var2 = this.getContext();
            AppCompatTextView var3 = new AppCompatTextView(var2);
            this.mSubtitleTextView = var3;
            var3.setSingleLine();
            this.mSubtitleTextView.setEllipsize(TruncateAt.END);
            int var4 = this.mSubtitleTextAppearance;
            if (var4 != 0) {
               this.mSubtitleTextView.setTextAppearance(var2, var4);
            }

            ColorStateList var5 = this.mSubtitleTextColor;
            if (var5 != null) {
               this.mSubtitleTextView.setTextColor(var5);
            }
         }

         if (!this.isChildOrHidden(this.mSubtitleTextView)) {
            this.addSystemView(this.mSubtitleTextView, true);
         }
      } else {
         var6 = this.mSubtitleTextView;
         if (var6 != null && this.isChildOrHidden(var6)) {
            this.removeView(this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
         }
      }

      var6 = this.mSubtitleTextView;
      if (var6 != null) {
         var6.setText(var1);
      }

      this.mSubtitleText = var1;
   }

   public void setSubtitleTextAppearance(Context var1, int var2) {
      this.mSubtitleTextAppearance = var2;
      TextView var3 = this.mSubtitleTextView;
      if (var3 != null) {
         var3.setTextAppearance(var1, var2);
      }

   }

   public void setSubtitleTextColor(int var1) {
      this.setSubtitleTextColor(ColorStateList.valueOf(var1));
   }

   public void setSubtitleTextColor(ColorStateList var1) {
      this.mSubtitleTextColor = var1;
      TextView var2 = this.mSubtitleTextView;
      if (var2 != null) {
         var2.setTextColor(var1);
      }

   }

   public void setTitle(int var1) {
      this.setTitle(this.getContext().getText(var1));
   }

   public void setTitle(CharSequence var1) {
      TextView var6;
      if (!TextUtils.isEmpty(var1)) {
         if (this.mTitleTextView == null) {
            Context var2 = this.getContext();
            AppCompatTextView var3 = new AppCompatTextView(var2);
            this.mTitleTextView = var3;
            var3.setSingleLine();
            this.mTitleTextView.setEllipsize(TruncateAt.END);
            int var4 = this.mTitleTextAppearance;
            if (var4 != 0) {
               this.mTitleTextView.setTextAppearance(var2, var4);
            }

            ColorStateList var5 = this.mTitleTextColor;
            if (var5 != null) {
               this.mTitleTextView.setTextColor(var5);
            }
         }

         if (!this.isChildOrHidden(this.mTitleTextView)) {
            this.addSystemView(this.mTitleTextView, true);
         }
      } else {
         var6 = this.mTitleTextView;
         if (var6 != null && this.isChildOrHidden(var6)) {
            this.removeView(this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
         }
      }

      var6 = this.mTitleTextView;
      if (var6 != null) {
         var6.setText(var1);
      }

      this.mTitleText = var1;
   }

   public void setTitleMargin(int var1, int var2, int var3, int var4) {
      this.mTitleMarginStart = var1;
      this.mTitleMarginTop = var2;
      this.mTitleMarginEnd = var3;
      this.mTitleMarginBottom = var4;
      this.requestLayout();
   }

   public void setTitleMarginBottom(int var1) {
      this.mTitleMarginBottom = var1;
      this.requestLayout();
   }

   public void setTitleMarginEnd(int var1) {
      this.mTitleMarginEnd = var1;
      this.requestLayout();
   }

   public void setTitleMarginStart(int var1) {
      this.mTitleMarginStart = var1;
      this.requestLayout();
   }

   public void setTitleMarginTop(int var1) {
      this.mTitleMarginTop = var1;
      this.requestLayout();
   }

   public void setTitleTextAppearance(Context var1, int var2) {
      this.mTitleTextAppearance = var2;
      TextView var3 = this.mTitleTextView;
      if (var3 != null) {
         var3.setTextAppearance(var1, var2);
      }

   }

   public void setTitleTextColor(int var1) {
      this.setTitleTextColor(ColorStateList.valueOf(var1));
   }

   public void setTitleTextColor(ColorStateList var1) {
      this.mTitleTextColor = var1;
      TextView var2 = this.mTitleTextView;
      if (var2 != null) {
         var2.setTextColor(var1);
      }

   }

   public boolean showOverflowMenu() {
      ActionMenuView var1 = this.mMenuView;
      boolean var2;
      if (var1 != null && var1.showOverflowMenu()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private class ExpandedActionViewMenuPresenter implements MenuPresenter {
      MenuItemImpl mCurrentExpandedItem;
      MenuBuilder mMenu;

      ExpandedActionViewMenuPresenter() {
      }

      public boolean collapseItemActionView(MenuBuilder var1, MenuItemImpl var2) {
         if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
            ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewCollapsed();
         }

         Toolbar var3 = Toolbar.this;
         var3.removeView(var3.mExpandedActionView);
         var3 = Toolbar.this;
         var3.removeView(var3.mCollapseButtonView);
         Toolbar.this.mExpandedActionView = null;
         Toolbar.this.addChildrenForExpandedActionView();
         this.mCurrentExpandedItem = null;
         Toolbar.this.requestLayout();
         var2.setActionViewExpanded(false);
         return true;
      }

      public boolean expandItemActionView(MenuBuilder var1, MenuItemImpl var2) {
         Toolbar.this.ensureCollapseButtonView();
         ViewParent var3 = Toolbar.this.mCollapseButtonView.getParent();
         Toolbar var4 = Toolbar.this;
         if (var3 != var4) {
            if (var3 instanceof ViewGroup) {
               ((ViewGroup)var3).removeView(var4.mCollapseButtonView);
            }

            var4 = Toolbar.this;
            var4.addView(var4.mCollapseButtonView);
         }

         Toolbar.this.mExpandedActionView = var2.getActionView();
         this.mCurrentExpandedItem = var2;
         ViewParent var5 = Toolbar.this.mExpandedActionView.getParent();
         Toolbar var7 = Toolbar.this;
         if (var5 != var7) {
            if (var5 instanceof ViewGroup) {
               ((ViewGroup)var5).removeView(var7.mExpandedActionView);
            }

            Toolbar.LayoutParams var6 = Toolbar.this.generateDefaultLayoutParams();
            var6.gravity = 8388611 | Toolbar.this.mButtonGravity & 112;
            var6.mViewType = 2;
            Toolbar.this.mExpandedActionView.setLayoutParams(var6);
            var4 = Toolbar.this;
            var4.addView(var4.mExpandedActionView);
         }

         Toolbar.this.removeChildrenForExpandedActionView();
         Toolbar.this.requestLayout();
         var2.setActionViewExpanded(true);
         if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
            ((CollapsibleActionView)Toolbar.this.mExpandedActionView).onActionViewExpanded();
         }

         return true;
      }

      public boolean flagActionItems() {
         return false;
      }

      public int getId() {
         return 0;
      }

      public MenuView getMenuView(ViewGroup var1) {
         return null;
      }

      public void initForMenu(Context var1, MenuBuilder var2) {
         MenuBuilder var4 = this.mMenu;
         if (var4 != null) {
            MenuItemImpl var3 = this.mCurrentExpandedItem;
            if (var3 != null) {
               var4.collapseItemActionView(var3);
            }
         }

         this.mMenu = var2;
      }

      public void onCloseMenu(MenuBuilder var1, boolean var2) {
      }

      public void onRestoreInstanceState(Parcelable var1) {
      }

      public Parcelable onSaveInstanceState() {
         return null;
      }

      public boolean onSubMenuSelected(SubMenuBuilder var1) {
         return false;
      }

      public void setCallback(MenuPresenter.Callback var1) {
      }

      public void updateMenuView(boolean var1) {
         if (this.mCurrentExpandedItem != null) {
            MenuBuilder var2 = this.mMenu;
            boolean var3 = false;
            boolean var4 = var3;
            if (var2 != null) {
               int var5 = var2.size();
               int var6 = 0;

               while(true) {
                  var4 = var3;
                  if (var6 >= var5) {
                     break;
                  }

                  if (this.mMenu.getItem(var6) == this.mCurrentExpandedItem) {
                     var4 = true;
                     break;
                  }

                  ++var6;
               }
            }

            if (!var4) {
               this.collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
            }
         }

      }
   }

   public static class LayoutParams extends ActionBar.LayoutParams {
      static final int CUSTOM = 0;
      static final int EXPANDED = 2;
      static final int SYSTEM = 1;
      int mViewType;

      public LayoutParams(int var1) {
         this(-2, -1, var1);
      }

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
         this.mViewType = 0;
         this.gravity = 8388627;
      }

      public LayoutParams(int var1, int var2, int var3) {
         super(var1, var2);
         this.mViewType = 0;
         this.gravity = var3;
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         this.mViewType = 0;
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
         this.mViewType = 0;
      }

      public LayoutParams(MarginLayoutParams var1) {
         super((android.view.ViewGroup.LayoutParams)var1);
         this.mViewType = 0;
         this.copyMarginsFromCompat(var1);
      }

      public LayoutParams(ActionBar.LayoutParams var1) {
         super(var1);
         this.mViewType = 0;
      }

      public LayoutParams(Toolbar.LayoutParams var1) {
         super((ActionBar.LayoutParams)var1);
         this.mViewType = 0;
         this.mViewType = var1.mViewType;
      }

      void copyMarginsFromCompat(MarginLayoutParams var1) {
         this.leftMargin = var1.leftMargin;
         this.topMargin = var1.topMargin;
         this.rightMargin = var1.rightMargin;
         this.bottomMargin = var1.bottomMargin;
      }
   }

   public interface OnMenuItemClickListener {
      boolean onMenuItemClick(MenuItem var1);
   }

   public static class SavedState extends AbsSavedState {
      public static final Creator<Toolbar.SavedState> CREATOR = new ClassLoaderCreator<Toolbar.SavedState>() {
         public Toolbar.SavedState createFromParcel(Parcel var1) {
            return new Toolbar.SavedState(var1, (ClassLoader)null);
         }

         public Toolbar.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            return new Toolbar.SavedState(var1, var2);
         }

         public Toolbar.SavedState[] newArray(int var1) {
            return new Toolbar.SavedState[var1];
         }
      };
      int expandedMenuItemId;
      boolean isOverflowOpen;

      public SavedState(Parcel var1) {
         this(var1, (ClassLoader)null);
      }

      public SavedState(Parcel var1, ClassLoader var2) {
         super(var1, var2);
         this.expandedMenuItemId = var1.readInt();
         boolean var3;
         if (var1.readInt() != 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         this.isOverflowOpen = var3;
      }

      public SavedState(Parcelable var1) {
         super(var1);
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         var1.writeInt(this.expandedMenuItemId);
         var1.writeInt(this.isOverflowOpen);
      }
   }
}
