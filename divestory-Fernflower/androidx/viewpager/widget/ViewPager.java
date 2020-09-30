package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.Resources.NotFoundException;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager extends ViewGroup {
   private static final int CLOSE_ENOUGH = 2;
   private static final Comparator<ViewPager.ItemInfo> COMPARATOR = new Comparator<ViewPager.ItemInfo>() {
      public int compare(ViewPager.ItemInfo var1, ViewPager.ItemInfo var2) {
         return var1.position - var2.position;
      }
   };
   private static final boolean DEBUG = false;
   private static final int DEFAULT_GUTTER_SIZE = 16;
   private static final int DEFAULT_OFFSCREEN_PAGES = 1;
   private static final int DRAW_ORDER_DEFAULT = 0;
   private static final int DRAW_ORDER_FORWARD = 1;
   private static final int DRAW_ORDER_REVERSE = 2;
   private static final int INVALID_POINTER = -1;
   static final int[] LAYOUT_ATTRS = new int[]{16842931};
   private static final int MAX_SETTLE_DURATION = 600;
   private static final int MIN_DISTANCE_FOR_FLING = 25;
   private static final int MIN_FLING_VELOCITY = 400;
   public static final int SCROLL_STATE_DRAGGING = 1;
   public static final int SCROLL_STATE_IDLE = 0;
   public static final int SCROLL_STATE_SETTLING = 2;
   private static final String TAG = "ViewPager";
   private static final boolean USE_CACHE = false;
   private static final Interpolator sInterpolator = new Interpolator() {
      public float getInterpolation(float var1) {
         --var1;
         return var1 * var1 * var1 * var1 * var1 + 1.0F;
      }
   };
   private static final ViewPager.ViewPositionComparator sPositionComparator = new ViewPager.ViewPositionComparator();
   private int mActivePointerId = -1;
   PagerAdapter mAdapter;
   private List<ViewPager.OnAdapterChangeListener> mAdapterChangeListeners;
   private int mBottomPageBounds;
   private boolean mCalledSuper;
   private int mChildHeightMeasureSpec;
   private int mChildWidthMeasureSpec;
   private int mCloseEnough;
   int mCurItem;
   private int mDecorChildCount;
   private int mDefaultGutterSize;
   private int mDrawingOrder;
   private ArrayList<View> mDrawingOrderedChildren;
   private final Runnable mEndScrollRunnable = new Runnable() {
      public void run() {
         ViewPager.this.setScrollState(0);
         ViewPager.this.populate();
      }
   };
   private int mExpectedAdapterCount;
   private long mFakeDragBeginTime;
   private boolean mFakeDragging;
   private boolean mFirstLayout = true;
   private float mFirstOffset = -3.4028235E38F;
   private int mFlingDistance;
   private int mGutterSize;
   private boolean mInLayout;
   private float mInitialMotionX;
   private float mInitialMotionY;
   private ViewPager.OnPageChangeListener mInternalPageChangeListener;
   private boolean mIsBeingDragged;
   private boolean mIsScrollStarted;
   private boolean mIsUnableToDrag;
   private final ArrayList<ViewPager.ItemInfo> mItems = new ArrayList();
   private float mLastMotionX;
   private float mLastMotionY;
   private float mLastOffset = Float.MAX_VALUE;
   private EdgeEffect mLeftEdge;
   private Drawable mMarginDrawable;
   private int mMaximumVelocity;
   private int mMinimumVelocity;
   private boolean mNeedCalculatePageOffsets = false;
   private ViewPager.PagerObserver mObserver;
   private int mOffscreenPageLimit = 1;
   private ViewPager.OnPageChangeListener mOnPageChangeListener;
   private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners;
   private int mPageMargin;
   private ViewPager.PageTransformer mPageTransformer;
   private int mPageTransformerLayerType;
   private boolean mPopulatePending;
   private Parcelable mRestoredAdapterState = null;
   private ClassLoader mRestoredClassLoader = null;
   private int mRestoredCurItem = -1;
   private EdgeEffect mRightEdge;
   private int mScrollState = 0;
   private Scroller mScroller;
   private boolean mScrollingCacheEnabled;
   private final ViewPager.ItemInfo mTempItem = new ViewPager.ItemInfo();
   private final Rect mTempRect = new Rect();
   private int mTopPageBounds;
   private int mTouchSlop;
   private VelocityTracker mVelocityTracker;

   public ViewPager(Context var1) {
      super(var1);
      this.initViewPager();
   }

   public ViewPager(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.initViewPager();
   }

   private void calculatePageOffsets(ViewPager.ItemInfo var1, int var2, ViewPager.ItemInfo var3) {
      int var4 = this.mAdapter.getCount();
      int var5 = this.getClientWidth();
      float var6;
      if (var5 > 0) {
         var6 = (float)this.mPageMargin / (float)var5;
      } else {
         var6 = 0.0F;
      }

      float var7;
      int var8;
      int var9;
      float var10;
      if (var3 != null) {
         var5 = var3.position;
         if (var5 < var1.position) {
            var7 = var3.offset + var3.widthFactor + var6;
            ++var5;

            for(var8 = 0; var5 <= var1.position && var8 < this.mItems.size(); var5 = var9 + 1) {
               var3 = (ViewPager.ItemInfo)this.mItems.get(var8);

               while(true) {
                  var9 = var5;
                  var10 = var7;
                  if (var5 <= var3.position) {
                     break;
                  }

                  var9 = var5;
                  var10 = var7;
                  if (var8 >= this.mItems.size() - 1) {
                     break;
                  }

                  ++var8;
                  var3 = (ViewPager.ItemInfo)this.mItems.get(var8);
               }

               while(var9 < var3.position) {
                  var10 += this.mAdapter.getPageWidth(var9) + var6;
                  ++var9;
               }

               var3.offset = var10;
               var7 = var10 + var3.widthFactor + var6;
            }
         } else if (var5 > var1.position) {
            var8 = this.mItems.size() - 1;
            var7 = var3.offset;
            --var5;

            while(var5 >= var1.position && var8 >= 0) {
               var3 = (ViewPager.ItemInfo)this.mItems.get(var8);

               while(true) {
                  var9 = var5;
                  var10 = var7;
                  if (var5 >= var3.position) {
                     break;
                  }

                  var9 = var5;
                  var10 = var7;
                  if (var8 <= 0) {
                     break;
                  }

                  --var8;
                  var3 = (ViewPager.ItemInfo)this.mItems.get(var8);
               }

               while(var9 > var3.position) {
                  var10 -= this.mAdapter.getPageWidth(var9) + var6;
                  --var9;
               }

               var7 = var10 - (var3.widthFactor + var6);
               var3.offset = var7;
               var5 = var9 - 1;
            }
         }
      }

      var9 = this.mItems.size();
      var10 = var1.offset;
      var5 = var1.position - 1;
      if (var1.position == 0) {
         var7 = var1.offset;
      } else {
         var7 = -3.4028235E38F;
      }

      this.mFirstOffset = var7;
      var8 = var1.position;
      --var4;
      if (var8 == var4) {
         var7 = var1.offset + var1.widthFactor - 1.0F;
      } else {
         var7 = Float.MAX_VALUE;
      }

      this.mLastOffset = var7;
      var8 = var2 - 1;

      for(var7 = var10; var8 >= 0; --var5) {
         for(var3 = (ViewPager.ItemInfo)this.mItems.get(var8); var5 > var3.position; --var5) {
            var7 -= this.mAdapter.getPageWidth(var5) + var6;
         }

         var7 -= var3.widthFactor + var6;
         var3.offset = var7;
         if (var3.position == 0) {
            this.mFirstOffset = var7;
         }

         --var8;
      }

      var7 = var1.offset + var1.widthFactor + var6;
      var8 = var1.position + 1;
      var5 = var2 + 1;

      for(var2 = var8; var5 < var9; ++var2) {
         for(var1 = (ViewPager.ItemInfo)this.mItems.get(var5); var2 < var1.position; ++var2) {
            var7 += this.mAdapter.getPageWidth(var2) + var6;
         }

         if (var1.position == var4) {
            this.mLastOffset = var1.widthFactor + var7 - 1.0F;
         }

         var1.offset = var7;
         var7 += var1.widthFactor + var6;
         ++var5;
      }

      this.mNeedCalculatePageOffsets = false;
   }

   private void completeScroll(boolean var1) {
      boolean var2;
      if (this.mScrollState == 2) {
         var2 = true;
      } else {
         var2 = false;
      }

      int var3;
      if (var2) {
         this.setScrollingCacheEnabled(false);
         if (this.mScroller.isFinished() ^ true) {
            this.mScroller.abortAnimation();
            var3 = this.getScrollX();
            int var4 = this.getScrollY();
            int var5 = this.mScroller.getCurrX();
            int var6 = this.mScroller.getCurrY();
            if (var3 != var5 || var4 != var6) {
               this.scrollTo(var5, var6);
               if (var5 != var3) {
                  this.pageScrolled(var5);
               }
            }
         }
      }

      this.mPopulatePending = false;

      for(var3 = 0; var3 < this.mItems.size(); ++var3) {
         ViewPager.ItemInfo var7 = (ViewPager.ItemInfo)this.mItems.get(var3);
         if (var7.scrolling) {
            var7.scrolling = false;
            var2 = true;
         }
      }

      if (var2) {
         if (var1) {
            ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
         } else {
            this.mEndScrollRunnable.run();
         }
      }

   }

   private int determineTargetPage(int var1, float var2, int var3, int var4) {
      if (Math.abs(var4) > this.mFlingDistance && Math.abs(var3) > this.mMinimumVelocity) {
         if (var3 <= 0) {
            ++var1;
         }
      } else {
         float var5;
         if (var1 >= this.mCurItem) {
            var5 = 0.4F;
         } else {
            var5 = 0.6F;
         }

         var1 += (int)(var2 + var5);
      }

      var3 = var1;
      if (this.mItems.size() > 0) {
         ViewPager.ItemInfo var6 = (ViewPager.ItemInfo)this.mItems.get(0);
         ArrayList var7 = this.mItems;
         ViewPager.ItemInfo var8 = (ViewPager.ItemInfo)var7.get(var7.size() - 1);
         var3 = Math.max(var6.position, Math.min(var1, var8.position));
      }

      return var3;
   }

   private void dispatchOnPageScrolled(int var1, float var2, int var3) {
      ViewPager.OnPageChangeListener var4 = this.mOnPageChangeListener;
      if (var4 != null) {
         var4.onPageScrolled(var1, var2, var3);
      }

      List var7 = this.mOnPageChangeListeners;
      if (var7 != null) {
         int var5 = 0;

         for(int var6 = var7.size(); var5 < var6; ++var5) {
            var4 = (ViewPager.OnPageChangeListener)this.mOnPageChangeListeners.get(var5);
            if (var4 != null) {
               var4.onPageScrolled(var1, var2, var3);
            }
         }
      }

      var4 = this.mInternalPageChangeListener;
      if (var4 != null) {
         var4.onPageScrolled(var1, var2, var3);
      }

   }

   private void dispatchOnPageSelected(int var1) {
      ViewPager.OnPageChangeListener var2 = this.mOnPageChangeListener;
      if (var2 != null) {
         var2.onPageSelected(var1);
      }

      List var5 = this.mOnPageChangeListeners;
      if (var5 != null) {
         int var3 = 0;

         for(int var4 = var5.size(); var3 < var4; ++var3) {
            var2 = (ViewPager.OnPageChangeListener)this.mOnPageChangeListeners.get(var3);
            if (var2 != null) {
               var2.onPageSelected(var1);
            }
         }
      }

      var2 = this.mInternalPageChangeListener;
      if (var2 != null) {
         var2.onPageSelected(var1);
      }

   }

   private void dispatchOnScrollStateChanged(int var1) {
      ViewPager.OnPageChangeListener var2 = this.mOnPageChangeListener;
      if (var2 != null) {
         var2.onPageScrollStateChanged(var1);
      }

      List var5 = this.mOnPageChangeListeners;
      if (var5 != null) {
         int var3 = 0;

         for(int var4 = var5.size(); var3 < var4; ++var3) {
            var2 = (ViewPager.OnPageChangeListener)this.mOnPageChangeListeners.get(var3);
            if (var2 != null) {
               var2.onPageScrollStateChanged(var1);
            }
         }
      }

      var2 = this.mInternalPageChangeListener;
      if (var2 != null) {
         var2.onPageScrollStateChanged(var1);
      }

   }

   private void enableLayers(boolean var1) {
      int var2 = this.getChildCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4;
         if (var1) {
            var4 = this.mPageTransformerLayerType;
         } else {
            var4 = 0;
         }

         this.getChildAt(var3).setLayerType(var4, (Paint)null);
      }

   }

   private void endDrag() {
      this.mIsBeingDragged = false;
      this.mIsUnableToDrag = false;
      VelocityTracker var1 = this.mVelocityTracker;
      if (var1 != null) {
         var1.recycle();
         this.mVelocityTracker = null;
      }

   }

   private Rect getChildRectInPagerCoordinates(Rect var1, View var2) {
      Rect var3 = var1;
      if (var1 == null) {
         var3 = new Rect();
      }

      if (var2 == null) {
         var3.set(0, 0, 0, 0);
         return var3;
      } else {
         var3.left = var2.getLeft();
         var3.right = var2.getRight();
         var3.top = var2.getTop();
         var3.bottom = var2.getBottom();

         ViewGroup var5;
         for(ViewParent var4 = var2.getParent(); var4 instanceof ViewGroup && var4 != this; var4 = var5.getParent()) {
            var5 = (ViewGroup)var4;
            var3.left += var5.getLeft();
            var3.right += var5.getRight();
            var3.top += var5.getTop();
            var3.bottom += var5.getBottom();
         }

         return var3;
      }
   }

   private int getClientWidth() {
      return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
   }

   private ViewPager.ItemInfo infoForCurrentScrollPosition() {
      int var1 = this.getClientWidth();
      float var2 = 0.0F;
      float var3;
      if (var1 > 0) {
         var3 = (float)this.getScrollX() / (float)var1;
      } else {
         var3 = 0.0F;
      }

      float var4;
      if (var1 > 0) {
         var4 = (float)this.mPageMargin / (float)var1;
      } else {
         var4 = 0.0F;
      }

      ViewPager.ItemInfo var5 = null;
      float var6 = 0.0F;
      int var7 = -1;
      var1 = 0;

      ViewPager.ItemInfo var11;
      for(boolean var8 = true; var1 < this.mItems.size(); var5 = var11) {
         ViewPager.ItemInfo var9 = (ViewPager.ItemInfo)this.mItems.get(var1);
         int var10 = var1;
         var11 = var9;
         if (!var8) {
            int var12 = var9.position;
            ++var7;
            var10 = var1;
            var11 = var9;
            if (var12 != var7) {
               var11 = this.mTempItem;
               var11.offset = var2 + var6 + var4;
               var11.position = var7;
               var11.widthFactor = this.mAdapter.getPageWidth(var11.position);
               var10 = var1 - 1;
            }
         }

         var2 = var11.offset;
         var6 = var11.widthFactor;
         if (!var8 && var3 < var2) {
            return var5;
         }

         if (var3 < var6 + var2 + var4 || var10 == this.mItems.size() - 1) {
            return var11;
         }

         var7 = var11.position;
         var6 = var11.widthFactor;
         var1 = var10 + 1;
         var8 = false;
      }

      return var5;
   }

   private static boolean isDecorView(View var0) {
      boolean var1;
      if (var0.getClass().getAnnotation(ViewPager.DecorView.class) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isGutterDrag(float var1, float var2) {
      boolean var3;
      if ((var1 >= (float)this.mGutterSize || var2 <= 0.0F) && (var1 <= (float)(this.getWidth() - this.mGutterSize) || var2 >= 0.0F)) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   private void onSecondaryPointerUp(MotionEvent var1) {
      int var2 = var1.getActionIndex();
      if (var1.getPointerId(var2) == this.mActivePointerId) {
         byte var4;
         if (var2 == 0) {
            var4 = 1;
         } else {
            var4 = 0;
         }

         this.mLastMotionX = var1.getX(var4);
         this.mActivePointerId = var1.getPointerId(var4);
         VelocityTracker var3 = this.mVelocityTracker;
         if (var3 != null) {
            var3.clear();
         }
      }

   }

   private boolean pageScrolled(int var1) {
      if (this.mItems.size() == 0) {
         if (this.mFirstLayout) {
            return false;
         } else {
            this.mCalledSuper = false;
            this.onPageScrolled(0, 0.0F, 0);
            if (this.mCalledSuper) {
               return false;
            } else {
               throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
         }
      } else {
         ViewPager.ItemInfo var2 = this.infoForCurrentScrollPosition();
         int var3 = this.getClientWidth();
         int var4 = this.mPageMargin;
         float var5 = (float)var4;
         float var6 = (float)var3;
         var5 /= var6;
         int var7 = var2.position;
         var6 = ((float)var1 / var6 - var2.offset) / (var2.widthFactor + var5);
         var1 = (int)((float)(var3 + var4) * var6);
         this.mCalledSuper = false;
         this.onPageScrolled(var7, var6, var1);
         if (this.mCalledSuper) {
            return true;
         } else {
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
         }
      }
   }

   private boolean performDrag(float var1) {
      float var2 = this.mLastMotionX;
      this.mLastMotionX = var1;
      float var3 = (float)this.getScrollX() + (var2 - var1);
      float var4 = (float)this.getClientWidth();
      var1 = this.mFirstOffset * var4;
      var2 = this.mLastOffset * var4;
      ArrayList var5 = this.mItems;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;
      ViewPager.ItemInfo var12 = (ViewPager.ItemInfo)var5.get(0);
      ArrayList var9 = this.mItems;
      ViewPager.ItemInfo var13 = (ViewPager.ItemInfo)var9.get(var9.size() - 1);
      boolean var10;
      if (var12.position != 0) {
         var1 = var12.offset * var4;
         var10 = false;
      } else {
         var10 = true;
      }

      boolean var11;
      if (var13.position != this.mAdapter.getCount() - 1) {
         var2 = var13.offset * var4;
         var11 = false;
      } else {
         var11 = true;
      }

      if (var3 < var1) {
         if (var10) {
            this.mLeftEdge.onPull(Math.abs(var1 - var3) / var4);
            var8 = true;
         }
      } else {
         var8 = var7;
         var1 = var3;
         if (var3 > var2) {
            var8 = var6;
            if (var11) {
               this.mRightEdge.onPull(Math.abs(var3 - var2) / var4);
               var8 = true;
            }

            var1 = var2;
         }
      }

      var2 = this.mLastMotionX;
      int var14 = (int)var1;
      this.mLastMotionX = var2 + (var1 - (float)var14);
      this.scrollTo(var14, this.getScrollY());
      this.pageScrolled(var14);
      return var8;
   }

   private void recomputeScrollPosition(int var1, int var2, int var3, int var4) {
      if (var2 > 0 && !this.mItems.isEmpty()) {
         if (!this.mScroller.isFinished()) {
            this.mScroller.setFinalX(this.getCurrentItem() * this.getClientWidth());
         } else {
            int var5 = this.getPaddingLeft();
            int var6 = this.getPaddingRight();
            int var7 = this.getPaddingLeft();
            int var8 = this.getPaddingRight();
            this.scrollTo((int)((float)this.getScrollX() / (float)(var2 - var7 - var8 + var4) * (float)(var1 - var5 - var6 + var3)), this.getScrollY());
         }
      } else {
         ViewPager.ItemInfo var9 = this.infoForPosition(this.mCurItem);
         float var10;
         if (var9 != null) {
            var10 = Math.min(var9.offset, this.mLastOffset);
         } else {
            var10 = 0.0F;
         }

         var1 = (int)(var10 * (float)(var1 - this.getPaddingLeft() - this.getPaddingRight()));
         if (var1 != this.getScrollX()) {
            this.completeScroll(false);
            this.scrollTo(var1, this.getScrollY());
         }
      }

   }

   private void removeNonDecorViews() {
      int var2;
      for(int var1 = 0; var1 < this.getChildCount(); var1 = var2 + 1) {
         var2 = var1;
         if (!((ViewPager.LayoutParams)this.getChildAt(var1).getLayoutParams()).isDecor) {
            this.removeViewAt(var1);
            var2 = var1 - 1;
         }
      }

   }

   private void requestParentDisallowInterceptTouchEvent(boolean var1) {
      ViewParent var2 = this.getParent();
      if (var2 != null) {
         var2.requestDisallowInterceptTouchEvent(var1);
      }

   }

   private boolean resetTouch() {
      this.mActivePointerId = -1;
      this.endDrag();
      this.mLeftEdge.onRelease();
      this.mRightEdge.onRelease();
      boolean var1;
      if (!this.mLeftEdge.isFinished() && !this.mRightEdge.isFinished()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private void scrollToItem(int var1, boolean var2, int var3, boolean var4) {
      ViewPager.ItemInfo var5 = this.infoForPosition(var1);
      int var6;
      if (var5 != null) {
         var6 = (int)((float)this.getClientWidth() * Math.max(this.mFirstOffset, Math.min(var5.offset, this.mLastOffset)));
      } else {
         var6 = 0;
      }

      if (var2) {
         this.smoothScrollTo(var6, 0, var3);
         if (var4) {
            this.dispatchOnPageSelected(var1);
         }
      } else {
         if (var4) {
            this.dispatchOnPageSelected(var1);
         }

         this.completeScroll(false);
         this.scrollTo(var6, 0);
         this.pageScrolled(var6);
      }

   }

   private void setScrollingCacheEnabled(boolean var1) {
      if (this.mScrollingCacheEnabled != var1) {
         this.mScrollingCacheEnabled = var1;
      }

   }

   private void sortChildDrawingOrder() {
      if (this.mDrawingOrder != 0) {
         ArrayList var1 = this.mDrawingOrderedChildren;
         if (var1 == null) {
            this.mDrawingOrderedChildren = new ArrayList();
         } else {
            var1.clear();
         }

         int var2 = this.getChildCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = this.getChildAt(var3);
            this.mDrawingOrderedChildren.add(var4);
         }

         Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
      }

   }

   public void addFocusables(ArrayList<View> var1, int var2, int var3) {
      int var4 = var1.size();
      int var5 = this.getDescendantFocusability();
      if (var5 != 393216) {
         for(int var6 = 0; var6 < this.getChildCount(); ++var6) {
            View var7 = this.getChildAt(var6);
            if (var7.getVisibility() == 0) {
               ViewPager.ItemInfo var8 = this.infoForChild(var7);
               if (var8 != null && var8.position == this.mCurItem) {
                  var7.addFocusables(var1, var2, var3);
               }
            }
         }
      }

      if (var5 != 262144 || var4 == var1.size()) {
         if (!this.isFocusable()) {
            return;
         }

         if ((var3 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode()) {
            return;
         }

         if (var1 != null) {
            var1.add(this);
         }
      }

   }

   ViewPager.ItemInfo addNewItem(int var1, int var2) {
      ViewPager.ItemInfo var3 = new ViewPager.ItemInfo();
      var3.position = var1;
      var3.object = this.mAdapter.instantiateItem((ViewGroup)this, var1);
      var3.widthFactor = this.mAdapter.getPageWidth(var1);
      if (var2 >= 0 && var2 < this.mItems.size()) {
         this.mItems.add(var2, var3);
      } else {
         this.mItems.add(var3);
      }

      return var3;
   }

   public void addOnAdapterChangeListener(ViewPager.OnAdapterChangeListener var1) {
      if (this.mAdapterChangeListeners == null) {
         this.mAdapterChangeListeners = new ArrayList();
      }

      this.mAdapterChangeListeners.add(var1);
   }

   public void addOnPageChangeListener(ViewPager.OnPageChangeListener var1) {
      if (this.mOnPageChangeListeners == null) {
         this.mOnPageChangeListeners = new ArrayList();
      }

      this.mOnPageChangeListeners.add(var1);
   }

   public void addTouchables(ArrayList<View> var1) {
      for(int var2 = 0; var2 < this.getChildCount(); ++var2) {
         View var3 = this.getChildAt(var2);
         if (var3.getVisibility() == 0) {
            ViewPager.ItemInfo var4 = this.infoForChild(var3);
            if (var4 != null && var4.position == this.mCurItem) {
               var3.addTouchables(var1);
            }
         }
      }

   }

   public void addView(View var1, int var2, android.view.ViewGroup.LayoutParams var3) {
      android.view.ViewGroup.LayoutParams var4 = var3;
      if (!this.checkLayoutParams(var3)) {
         var4 = this.generateLayoutParams(var3);
      }

      ViewPager.LayoutParams var5 = (ViewPager.LayoutParams)var4;
      var5.isDecor |= isDecorView(var1);
      if (this.mInLayout) {
         if (var5 != null && var5.isDecor) {
            throw new IllegalStateException("Cannot add pager decor view during layout");
         }

         var5.needsMeasure = true;
         this.addViewInLayout(var1, var2, var4);
      } else {
         super.addView(var1, var2, var4);
      }

   }

   public boolean arrowScroll(int var1) {
      View var2;
      boolean var3;
      View var4;
      label86: {
         var2 = this.findFocus();
         var3 = false;
         if (var2 != this) {
            var4 = var2;
            if (var2 == null) {
               break label86;
            }

            ViewParent var8 = var2.getParent();

            boolean var5;
            while(true) {
               if (!(var8 instanceof ViewGroup)) {
                  var5 = false;
                  break;
               }

               if (var8 == this) {
                  var5 = true;
                  break;
               }

               var8 = var8.getParent();
            }

            var4 = var2;
            if (var5) {
               break label86;
            }

            StringBuilder var6 = new StringBuilder();
            var6.append(var2.getClass().getSimpleName());

            for(var8 = var2.getParent(); var8 instanceof ViewGroup; var8 = var8.getParent()) {
               var6.append(" => ");
               var6.append(var8.getClass().getSimpleName());
            }

            StringBuilder var10 = new StringBuilder();
            var10.append("arrowScroll tried to find focus based on non-child current focused view ");
            var10.append(var6.toString());
            Log.e("ViewPager", var10.toString());
         }

         var4 = null;
      }

      var2 = FocusFinder.getInstance().findNextFocus(this, var4, var1);
      if (var2 != null && var2 != var4) {
         int var7;
         int var9;
         if (var1 == 17) {
            var9 = this.getChildRectInPagerCoordinates(this.mTempRect, var2).left;
            var7 = this.getChildRectInPagerCoordinates(this.mTempRect, var4).left;
            if (var4 != null && var9 >= var7) {
               var3 = this.pageLeft();
            } else {
               var3 = var2.requestFocus();
            }
         } else if (var1 == 66) {
            var9 = this.getChildRectInPagerCoordinates(this.mTempRect, var2).left;
            var7 = this.getChildRectInPagerCoordinates(this.mTempRect, var4).left;
            if (var4 != null && var9 <= var7) {
               var3 = this.pageRight();
            } else {
               var3 = var2.requestFocus();
            }
         }
      } else if (var1 != 17 && var1 != 1) {
         if (var1 == 66 || var1 == 2) {
            var3 = this.pageRight();
         }
      } else {
         var3 = this.pageLeft();
      }

      if (var3) {
         this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(var1));
      }

      return var3;
   }

   public boolean beginFakeDrag() {
      if (this.mIsBeingDragged) {
         return false;
      } else {
         this.mFakeDragging = true;
         this.setScrollState(1);
         this.mLastMotionX = 0.0F;
         this.mInitialMotionX = 0.0F;
         VelocityTracker var1 = this.mVelocityTracker;
         if (var1 == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
         } else {
            var1.clear();
         }

         long var2 = SystemClock.uptimeMillis();
         MotionEvent var4 = MotionEvent.obtain(var2, var2, 0, 0.0F, 0.0F, 0);
         this.mVelocityTracker.addMovement(var4);
         var4.recycle();
         this.mFakeDragBeginTime = var2;
         return true;
      }
   }

   protected boolean canScroll(View var1, boolean var2, int var3, int var4, int var5) {
      boolean var6 = var1 instanceof ViewGroup;
      boolean var7 = true;
      if (var6) {
         ViewGroup var8 = (ViewGroup)var1;
         int var9 = var1.getScrollX();
         int var10 = var1.getScrollY();

         for(int var11 = var8.getChildCount() - 1; var11 >= 0; --var11) {
            View var12 = var8.getChildAt(var11);
            int var13 = var4 + var9;
            if (var13 >= var12.getLeft() && var13 < var12.getRight()) {
               int var14 = var5 + var10;
               if (var14 >= var12.getTop() && var14 < var12.getBottom() && this.canScroll(var12, true, var3, var13 - var12.getLeft(), var14 - var12.getTop())) {
                  return true;
               }
            }
         }
      }

      if (var2 && var1.canScrollHorizontally(-var3)) {
         var2 = var7;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean canScrollHorizontally(int var1) {
      PagerAdapter var2 = this.mAdapter;
      boolean var3 = false;
      boolean var4 = false;
      if (var2 == null) {
         return false;
      } else {
         int var5 = this.getClientWidth();
         int var6 = this.getScrollX();
         if (var1 < 0) {
            if (var6 > (int)((float)var5 * this.mFirstOffset)) {
               var4 = true;
            }

            return var4;
         } else {
            var4 = var3;
            if (var1 > 0) {
               var4 = var3;
               if (var6 < (int)((float)var5 * this.mLastOffset)) {
                  var4 = true;
               }
            }

            return var4;
         }
      }
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      boolean var2;
      if (var1 instanceof ViewPager.LayoutParams && super.checkLayoutParams(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void clearOnPageChangeListeners() {
      List var1 = this.mOnPageChangeListeners;
      if (var1 != null) {
         var1.clear();
      }

   }

   public void computeScroll() {
      this.mIsScrollStarted = true;
      if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
         int var1 = this.getScrollX();
         int var2 = this.getScrollY();
         int var3 = this.mScroller.getCurrX();
         int var4 = this.mScroller.getCurrY();
         if (var1 != var3 || var2 != var4) {
            this.scrollTo(var3, var4);
            if (!this.pageScrolled(var3)) {
               this.mScroller.abortAnimation();
               this.scrollTo(0, var4);
            }
         }

         ViewCompat.postInvalidateOnAnimation(this);
      } else {
         this.completeScroll(true);
      }
   }

   void dataSetChanged() {
      int var1 = this.mAdapter.getCount();
      this.mExpectedAdapterCount = var1;
      boolean var2;
      if (this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      int var3 = this.mCurItem;
      int var4 = 0;
      boolean var5 = false;

      boolean var6;
      boolean var10;
      for(var6 = var2; var4 < this.mItems.size(); var5 = var10) {
         ViewPager.ItemInfo var7 = (ViewPager.ItemInfo)this.mItems.get(var4);
         int var8 = this.mAdapter.getItemPosition(var7.object);
         int var9;
         int var11;
         if (var8 == -1) {
            var11 = var3;
            var9 = var4;
            var10 = var5;
         } else {
            label74: {
               if (var8 == -2) {
                  this.mItems.remove(var4);
                  int var12 = var4 - 1;
                  var2 = var5;
                  if (!var5) {
                     this.mAdapter.startUpdate((ViewGroup)this);
                     var2 = true;
                  }

                  this.mAdapter.destroyItem((ViewGroup)this, var7.position, var7.object);
                  var4 = var12;
                  var5 = var2;
                  if (this.mCurItem == var7.position) {
                     var3 = Math.max(0, Math.min(this.mCurItem, var1 - 1));
                     var5 = var2;
                     var4 = var12;
                  }
               } else {
                  var11 = var3;
                  var9 = var4;
                  var10 = var5;
                  if (var7.position == var8) {
                     break label74;
                  }

                  if (var7.position == this.mCurItem) {
                     var3 = var8;
                  }

                  var7.position = var8;
               }

               var6 = true;
               var11 = var3;
               var9 = var4;
               var10 = var5;
            }
         }

         var4 = var9 + 1;
         var3 = var11;
      }

      if (var5) {
         this.mAdapter.finishUpdate((ViewGroup)this);
      }

      Collections.sort(this.mItems, COMPARATOR);
      if (var6) {
         var4 = this.getChildCount();

         for(int var13 = 0; var13 < var4; ++var13) {
            ViewPager.LayoutParams var14 = (ViewPager.LayoutParams)this.getChildAt(var13).getLayoutParams();
            if (!var14.isDecor) {
               var14.widthFactor = 0.0F;
            }
         }

         this.setCurrentItemInternal(var3, false, true);
         this.requestLayout();
      }

   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      boolean var2;
      if (!super.dispatchKeyEvent(var1) && !this.executeKeyEvent(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      if (var1.getEventType() == 4096) {
         return super.dispatchPopulateAccessibilityEvent(var1);
      } else {
         int var2 = this.getChildCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = this.getChildAt(var3);
            if (var4.getVisibility() == 0) {
               ViewPager.ItemInfo var5 = this.infoForChild(var4);
               if (var5 != null && var5.position == this.mCurItem && var4.dispatchPopulateAccessibilityEvent(var1)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   float distanceInfluenceForSnapDuration(float var1) {
      return (float)Math.sin((double)((var1 - 0.5F) * 0.47123894F));
   }

   public void draw(Canvas var1) {
      boolean var3;
      label36: {
         super.draw(var1);
         int var2 = this.getOverScrollMode();
         var3 = false;
         boolean var4 = false;
         if (var2 != 0) {
            label35: {
               if (var2 == 1) {
                  PagerAdapter var5 = this.mAdapter;
                  if (var5 != null && var5.getCount() > 1) {
                     break label35;
                  }
               }

               this.mLeftEdge.finish();
               this.mRightEdge.finish();
               break label36;
            }
         }

         int var9;
         if (!this.mLeftEdge.isFinished()) {
            var9 = var1.save();
            var2 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
            int var10 = this.getWidth();
            var1.rotate(270.0F);
            var1.translate((float)(-var2 + this.getPaddingTop()), this.mFirstOffset * (float)var10);
            this.mLeftEdge.setSize(var2, var10);
            var4 = false | this.mLeftEdge.draw(var1);
            var1.restoreToCount(var9);
         }

         var3 = var4;
         if (!this.mRightEdge.isFinished()) {
            var2 = var1.save();
            var9 = this.getWidth();
            int var6 = this.getHeight();
            int var7 = this.getPaddingTop();
            int var8 = this.getPaddingBottom();
            var1.rotate(90.0F);
            var1.translate((float)(-this.getPaddingTop()), -(this.mLastOffset + 1.0F) * (float)var9);
            this.mRightEdge.setSize(var6 - var7 - var8, var9);
            var3 = var4 | this.mRightEdge.draw(var1);
            var1.restoreToCount(var2);
         }
      }

      if (var3) {
         ViewCompat.postInvalidateOnAnimation(this);
      }

   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      Drawable var1 = this.mMarginDrawable;
      if (var1 != null && var1.isStateful()) {
         var1.setState(this.getDrawableState());
      }

   }

   public void endFakeDrag() {
      if (this.mFakeDragging) {
         if (this.mAdapter != null) {
            VelocityTracker var1 = this.mVelocityTracker;
            var1.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
            int var2 = (int)var1.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int var3 = this.getClientWidth();
            int var4 = this.getScrollX();
            ViewPager.ItemInfo var5 = this.infoForCurrentScrollPosition();
            this.setCurrentItemInternal(this.determineTargetPage(var5.position, ((float)var4 / (float)var3 - var5.offset) / var5.widthFactor, var2, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, var2);
         }

         this.endDrag();
         this.mFakeDragging = false;
      } else {
         throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
      }
   }

   public boolean executeKeyEvent(KeyEvent var1) {
      boolean var3;
      if (var1.getAction() == 0) {
         int var2 = var1.getKeyCode();
         if (var2 == 21) {
            if (var1.hasModifiers(2)) {
               var3 = this.pageLeft();
            } else {
               var3 = this.arrowScroll(17);
            }

            return var3;
         }

         if (var2 == 22) {
            if (var1.hasModifiers(2)) {
               var3 = this.pageRight();
            } else {
               var3 = this.arrowScroll(66);
            }

            return var3;
         }

         if (var2 == 61) {
            if (var1.hasNoModifiers()) {
               var3 = this.arrowScroll(2);
               return var3;
            }

            if (var1.hasModifiers(1)) {
               var3 = this.arrowScroll(1);
               return var3;
            }
         }
      }

      var3 = false;
      return var3;
   }

   public void fakeDragBy(float var1) {
      if (this.mFakeDragging) {
         if (this.mAdapter != null) {
            this.mLastMotionX += var1;
            float var2 = (float)this.getScrollX() - var1;
            float var3 = (float)this.getClientWidth();
            var1 = this.mFirstOffset * var3;
            float var4 = this.mLastOffset * var3;
            ViewPager.ItemInfo var5 = (ViewPager.ItemInfo)this.mItems.get(0);
            ArrayList var6 = this.mItems;
            ViewPager.ItemInfo var11 = (ViewPager.ItemInfo)var6.get(var6.size() - 1);
            if (var5.position != 0) {
               var1 = var5.offset * var3;
            }

            if (var11.position != this.mAdapter.getCount() - 1) {
               var4 = var11.offset * var3;
            }

            if (var2 >= var1) {
               var1 = var2;
               if (var2 > var4) {
                  var1 = var4;
               }
            }

            var4 = this.mLastMotionX;
            int var7 = (int)var1;
            this.mLastMotionX = var4 + (var1 - (float)var7);
            this.scrollTo(var7, this.getScrollY());
            this.pageScrolled(var7);
            long var8 = SystemClock.uptimeMillis();
            MotionEvent var10 = MotionEvent.obtain(this.mFakeDragBeginTime, var8, 2, this.mLastMotionX, 0.0F, 0);
            this.mVelocityTracker.addMovement(var10);
            var10.recycle();
         }
      } else {
         throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
      }
   }

   protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
      return new ViewPager.LayoutParams();
   }

   public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new ViewPager.LayoutParams(this.getContext(), var1);
   }

   protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return this.generateDefaultLayoutParams();
   }

   public PagerAdapter getAdapter() {
      return this.mAdapter;
   }

   protected int getChildDrawingOrder(int var1, int var2) {
      int var3 = var2;
      if (this.mDrawingOrder == 2) {
         var3 = var1 - 1 - var2;
      }

      return ((ViewPager.LayoutParams)((View)this.mDrawingOrderedChildren.get(var3)).getLayoutParams()).childIndex;
   }

   public int getCurrentItem() {
      return this.mCurItem;
   }

   public int getOffscreenPageLimit() {
      return this.mOffscreenPageLimit;
   }

   public int getPageMargin() {
      return this.mPageMargin;
   }

   ViewPager.ItemInfo infoForAnyChild(View var1) {
      while(true) {
         ViewParent var2 = var1.getParent();
         if (var2 != this) {
            if (var2 != null && var2 instanceof View) {
               var1 = (View)var2;
               continue;
            }

            return null;
         }

         return this.infoForChild(var1);
      }
   }

   ViewPager.ItemInfo infoForChild(View var1) {
      for(int var2 = 0; var2 < this.mItems.size(); ++var2) {
         ViewPager.ItemInfo var3 = (ViewPager.ItemInfo)this.mItems.get(var2);
         if (this.mAdapter.isViewFromObject(var1, var3.object)) {
            return var3;
         }
      }

      return null;
   }

   ViewPager.ItemInfo infoForPosition(int var1) {
      for(int var2 = 0; var2 < this.mItems.size(); ++var2) {
         ViewPager.ItemInfo var3 = (ViewPager.ItemInfo)this.mItems.get(var2);
         if (var3.position == var1) {
            return var3;
         }
      }

      return null;
   }

   void initViewPager() {
      this.setWillNotDraw(false);
      this.setDescendantFocusability(262144);
      this.setFocusable(true);
      Context var1 = this.getContext();
      this.mScroller = new Scroller(var1, sInterpolator);
      ViewConfiguration var2 = ViewConfiguration.get(var1);
      float var3 = var1.getResources().getDisplayMetrics().density;
      this.mTouchSlop = var2.getScaledPagingTouchSlop();
      this.mMinimumVelocity = (int)(400.0F * var3);
      this.mMaximumVelocity = var2.getScaledMaximumFlingVelocity();
      this.mLeftEdge = new EdgeEffect(var1);
      this.mRightEdge = new EdgeEffect(var1);
      this.mFlingDistance = (int)(25.0F * var3);
      this.mCloseEnough = (int)(2.0F * var3);
      this.mDefaultGutterSize = (int)(var3 * 16.0F);
      ViewCompat.setAccessibilityDelegate(this, new ViewPager.MyAccessibilityDelegate());
      if (ViewCompat.getImportantForAccessibility(this) == 0) {
         ViewCompat.setImportantForAccessibility(this, 1);
      }

      ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
         private final Rect mTempRect = new Rect();

         public WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2) {
            WindowInsetsCompat var6 = ViewCompat.onApplyWindowInsets(var1, var2);
            if (var6.isConsumed()) {
               return var6;
            } else {
               Rect var3 = this.mTempRect;
               var3.left = var6.getSystemWindowInsetLeft();
               var3.top = var6.getSystemWindowInsetTop();
               var3.right = var6.getSystemWindowInsetRight();
               var3.bottom = var6.getSystemWindowInsetBottom();
               int var4 = 0;

               for(int var5 = ViewPager.this.getChildCount(); var4 < var5; ++var4) {
                  var2 = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(var4), var6);
                  var3.left = Math.min(var2.getSystemWindowInsetLeft(), var3.left);
                  var3.top = Math.min(var2.getSystemWindowInsetTop(), var3.top);
                  var3.right = Math.min(var2.getSystemWindowInsetRight(), var3.right);
                  var3.bottom = Math.min(var2.getSystemWindowInsetBottom(), var3.bottom);
               }

               return var6.replaceSystemWindowInsets(var3.left, var3.top, var3.right, var3.bottom);
            }
         }
      });
   }

   public boolean isFakeDragging() {
      return this.mFakeDragging;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.mFirstLayout = true;
   }

   protected void onDetachedFromWindow() {
      this.removeCallbacks(this.mEndScrollRunnable);
      Scroller var1 = this.mScroller;
      if (var1 != null && !var1.isFinished()) {
         this.mScroller.abortAnimation();
      }

      super.onDetachedFromWindow();
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
         int var2 = this.getScrollX();
         int var3 = this.getWidth();
         float var4 = (float)this.mPageMargin;
         float var5 = (float)var3;
         float var6 = var4 / var5;
         ArrayList var7 = this.mItems;
         int var8 = 0;
         ViewPager.ItemInfo var14 = (ViewPager.ItemInfo)var7.get(0);
         var4 = var14.offset;
         int var9 = this.mItems.size();
         int var10 = var14.position;

         for(int var11 = ((ViewPager.ItemInfo)this.mItems.get(var9 - 1)).position; var10 < var11; ++var10) {
            while(var10 > var14.position && var8 < var9) {
               var7 = this.mItems;
               ++var8;
               var14 = (ViewPager.ItemInfo)var7.get(var8);
            }

            float var12;
            if (var10 == var14.position) {
               var12 = (var14.offset + var14.widthFactor) * var5;
               var4 = var14.offset + var14.widthFactor + var6;
            } else {
               float var13 = this.mAdapter.getPageWidth(var10);
               var12 = var4 + var13 + var6;
               var13 = (var4 + var13) * var5;
               var4 = var12;
               var12 = var13;
            }

            if ((float)this.mPageMargin + var12 > (float)var2) {
               this.mMarginDrawable.setBounds(Math.round(var12), this.mTopPageBounds, Math.round((float)this.mPageMargin + var12), this.mBottomPageBounds);
               this.mMarginDrawable.draw(var1);
            }

            if (var12 > (float)(var2 + var3)) {
               break;
            }
         }
      }

   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      int var2 = var1.getAction() & 255;
      if (var2 != 3 && var2 != 1) {
         if (var2 != 0) {
            if (this.mIsBeingDragged) {
               return true;
            }

            if (this.mIsUnableToDrag) {
               return false;
            }
         }

         float var7;
         if (var2 != 0) {
            if (var2 != 2) {
               if (var2 == 6) {
                  this.onSecondaryPointerUp(var1);
               }
            } else {
               var2 = this.mActivePointerId;
               if (var2 != -1) {
                  var2 = var1.findPointerIndex(var2);
                  float var3 = var1.getX(var2);
                  float var4 = var3 - this.mLastMotionX;
                  float var5 = Math.abs(var4);
                  float var6 = var1.getY(var2);
                  var7 = Math.abs(var6 - this.mInitialMotionY);
                  float var8;
                  var2 = (var8 = var4 - 0.0F) == 0.0F ? 0 : (var8 < 0.0F ? -1 : 1);
                  if (var2 != 0 && !this.isGutterDrag(this.mLastMotionX, var4) && this.canScroll(this, false, (int)var4, (int)var3, (int)var6)) {
                     this.mLastMotionX = var3;
                     this.mLastMotionY = var6;
                     this.mIsUnableToDrag = true;
                     return false;
                  }

                  if (var5 > (float)this.mTouchSlop && var5 * 0.5F > var7) {
                     this.mIsBeingDragged = true;
                     this.requestParentDisallowInterceptTouchEvent(true);
                     this.setScrollState(1);
                     var7 = this.mInitialMotionX;
                     var4 = (float)this.mTouchSlop;
                     if (var2 > 0) {
                        var7 += var4;
                     } else {
                        var7 -= var4;
                     }

                     this.mLastMotionX = var7;
                     this.mLastMotionY = var6;
                     this.setScrollingCacheEnabled(true);
                  } else if (var7 > (float)this.mTouchSlop) {
                     this.mIsUnableToDrag = true;
                  }

                  if (this.mIsBeingDragged && this.performDrag(var3)) {
                     ViewCompat.postInvalidateOnAnimation(this);
                  }
               }
            }
         } else {
            var7 = var1.getX();
            this.mInitialMotionX = var7;
            this.mLastMotionX = var7;
            var7 = var1.getY();
            this.mInitialMotionY = var7;
            this.mLastMotionY = var7;
            this.mActivePointerId = var1.getPointerId(0);
            this.mIsUnableToDrag = false;
            this.mIsScrollStarted = true;
            this.mScroller.computeScrollOffset();
            if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
               this.mScroller.abortAnimation();
               this.mPopulatePending = false;
               this.populate();
               this.mIsBeingDragged = true;
               this.requestParentDisallowInterceptTouchEvent(true);
               this.setScrollState(1);
            } else {
               this.completeScroll(false);
               this.mIsBeingDragged = false;
            }
         }

         if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
         }

         this.mVelocityTracker.addMovement(var1);
         return this.mIsBeingDragged;
      } else {
         this.resetTouch();
         return false;
      }
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = this.getChildCount();
      int var7 = var4 - var2;
      int var8 = var5 - var3;
      var3 = this.getPaddingLeft();
      var2 = this.getPaddingTop();
      var5 = this.getPaddingRight();
      var4 = this.getPaddingBottom();
      int var9 = this.getScrollX();
      int var10 = 0;

      int var11;
      View var12;
      int var17;
      ViewPager.LayoutParams var18;
      for(var11 = 0; var10 < var6; var11 = var17) {
         var12 = this.getChildAt(var10);
         int var13 = var3;
         int var14 = var2;
         int var15 = var5;
         int var16 = var4;
         var17 = var11;
         if (var12.getVisibility() != 8) {
            var18 = (ViewPager.LayoutParams)var12.getLayoutParams();
            var13 = var3;
            var14 = var2;
            var15 = var5;
            var16 = var4;
            var17 = var11;
            if (var18.isDecor) {
               label65: {
                  var17 = var18.gravity & 7;
                  var15 = var18.gravity & 112;
                  if (var17 != 1) {
                     if (var17 == 3) {
                        var17 = var12.getMeasuredWidth() + var3;
                        var16 = var3;
                        var3 = var17;
                        break label65;
                     }

                     if (var17 != 5) {
                        var16 = var3;
                        var3 = var3;
                        break label65;
                     }

                     var17 = var7 - var5 - var12.getMeasuredWidth();
                     var5 += var12.getMeasuredWidth();
                  } else {
                     var17 = Math.max((var7 - var12.getMeasuredWidth()) / 2, var3);
                  }

                  var16 = var17;
               }

               if (var15 != 16) {
                  if (var15 != 48) {
                     if (var15 != 80) {
                        var17 = var2;
                        var2 = var2;
                     } else {
                        var17 = var8 - var4 - var12.getMeasuredHeight();
                        var4 += var12.getMeasuredHeight();
                     }
                  } else {
                     var15 = var12.getMeasuredHeight() + var2;
                     var17 = var2;
                     var2 = var15;
                  }
               } else {
                  var17 = Math.max((var8 - var12.getMeasuredHeight()) / 2, var2);
               }

               var16 += var9;
               var12.layout(var16, var17, var12.getMeasuredWidth() + var16, var17 + var12.getMeasuredHeight());
               var17 = var11 + 1;
               var16 = var4;
               var15 = var5;
               var14 = var2;
               var13 = var3;
            }
         }

         ++var10;
         var3 = var13;
         var2 = var14;
         var5 = var15;
         var4 = var16;
      }

      for(var17 = 0; var17 < var6; ++var17) {
         var12 = this.getChildAt(var17);
         if (var12.getVisibility() != 8) {
            var18 = (ViewPager.LayoutParams)var12.getLayoutParams();
            if (!var18.isDecor) {
               ViewPager.ItemInfo var19 = this.infoForChild(var12);
               if (var19 != null) {
                  float var20 = (float)(var7 - var3 - var5);
                  var10 = (int)(var19.offset * var20) + var3;
                  if (var18.needsMeasure) {
                     var18.needsMeasure = false;
                     var12.measure(MeasureSpec.makeMeasureSpec((int)(var20 * var18.widthFactor), 1073741824), MeasureSpec.makeMeasureSpec(var8 - var2 - var4, 1073741824));
                  }

                  var12.layout(var10, var2, var12.getMeasuredWidth() + var10, var12.getMeasuredHeight() + var2);
               }
            }
         }
      }

      this.mTopPageBounds = var2;
      this.mBottomPageBounds = var8 - var4;
      this.mDecorChildCount = var11;
      if (this.mFirstLayout) {
         this.scrollToItem(this.mCurItem, false, 0, false);
      }

      this.mFirstLayout = false;
   }

   protected void onMeasure(int var1, int var2) {
      this.setMeasuredDimension(getDefaultSize(0, var1), getDefaultSize(0, var2));
      var1 = this.getMeasuredWidth();
      this.mGutterSize = Math.min(var1 / 10, this.mDefaultGutterSize);
      var1 = var1 - this.getPaddingLeft() - this.getPaddingRight();
      var2 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
      int var3 = this.getChildCount();
      int var4 = 0;

      while(true) {
         boolean var5 = true;
         int var6 = 1073741824;
         int var8;
         if (var4 >= var3) {
            this.mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(var1, 1073741824);
            this.mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(var2, 1073741824);
            this.mInLayout = true;
            this.populate();
            var2 = 0;
            this.mInLayout = false;

            for(var8 = this.getChildCount(); var2 < var8; ++var2) {
               View var16 = this.getChildAt(var2);
               if (var16.getVisibility() != 8) {
                  ViewPager.LayoutParams var15 = (ViewPager.LayoutParams)var16.getLayoutParams();
                  if (var15 == null || !var15.isDecor) {
                     var16.measure(MeasureSpec.makeMeasureSpec((int)((float)var1 * var15.widthFactor), 1073741824), this.mChildHeightMeasureSpec);
                  }
               }
            }

            return;
         }

         View var7 = this.getChildAt(var4);
         var8 = var1;
         int var9 = var2;
         if (var7.getVisibility() != 8) {
            ViewPager.LayoutParams var10 = (ViewPager.LayoutParams)var7.getLayoutParams();
            var8 = var1;
            var9 = var2;
            if (var10 != null) {
               var8 = var1;
               var9 = var2;
               if (var10.isDecor) {
                  var8 = var10.gravity & 7;
                  var9 = var10.gravity & 112;
                  boolean var11;
                  if (var9 != 48 && var9 != 80) {
                     var11 = false;
                  } else {
                     var11 = true;
                  }

                  boolean var12 = var5;
                  if (var8 != 3) {
                     if (var8 == 5) {
                        var12 = var5;
                     } else {
                        var12 = false;
                     }
                  }

                  int var14;
                  label80: {
                     var8 = Integer.MIN_VALUE;
                     if (var11) {
                        var9 = 1073741824;
                     } else {
                        var9 = var8;
                        if (var12) {
                           var14 = 1073741824;
                           var9 = var8;
                           var8 = var14;
                           break label80;
                        }
                     }

                     var8 = Integer.MIN_VALUE;
                  }

                  if (var10.width != -2) {
                     if (var10.width != -1) {
                        var9 = var10.width;
                     } else {
                        var9 = var1;
                     }

                     int var13 = 1073741824;
                     var14 = var9;
                     var9 = var13;
                  } else {
                     var14 = var1;
                  }

                  if (var10.height != -2) {
                     if (var10.height != -1) {
                        var8 = var10.height;
                     } else {
                        var8 = var2;
                     }
                  } else {
                     var6 = var8;
                     var8 = var2;
                  }

                  var7.measure(MeasureSpec.makeMeasureSpec(var14, var9), MeasureSpec.makeMeasureSpec(var8, var6));
                  if (var11) {
                     var9 = var2 - var7.getMeasuredHeight();
                     var8 = var1;
                  } else {
                     var8 = var1;
                     var9 = var2;
                     if (var12) {
                        var8 = var1 - var7.getMeasuredWidth();
                        var9 = var2;
                     }
                  }
               }
            }
         }

         ++var4;
         var1 = var8;
         var2 = var9;
      }
   }

   protected void onPageScrolled(int var1, float var2, int var3) {
      int var4 = this.mDecorChildCount;
      byte var5 = 0;
      if (var4 > 0) {
         int var6 = this.getScrollX();
         var4 = this.getPaddingLeft();
         int var7 = this.getPaddingRight();
         int var8 = this.getWidth();
         int var9 = this.getChildCount();

         for(int var10 = 0; var10 < var9; ++var10) {
            View var11 = this.getChildAt(var10);
            ViewPager.LayoutParams var12 = (ViewPager.LayoutParams)var11.getLayoutParams();
            if (var12.isDecor) {
               int var13 = var12.gravity & 7;
               if (var13 != 1) {
                  if (var13 != 3) {
                     if (var13 != 5) {
                        var13 = var4;
                        var4 = var4;
                     } else {
                        var13 = var8 - var7 - var11.getMeasuredWidth();
                        var7 += var11.getMeasuredWidth();
                     }
                  } else {
                     int var14 = var11.getWidth() + var4;
                     var13 = var4;
                     var4 = var14;
                  }
               } else {
                  var13 = Math.max((var8 - var11.getMeasuredWidth()) / 2, var4);
               }

               var13 = var13 + var6 - var11.getLeft();
               if (var13 != 0) {
                  var11.offsetLeftAndRight(var13);
               }
            }
         }
      }

      this.dispatchOnPageScrolled(var1, var2, var3);
      if (this.mPageTransformer != null) {
         var4 = this.getScrollX();
         var3 = this.getChildCount();

         for(var1 = var5; var1 < var3; ++var1) {
            View var15 = this.getChildAt(var1);
            if (!((ViewPager.LayoutParams)var15.getLayoutParams()).isDecor) {
               var2 = (float)(var15.getLeft() - var4) / (float)this.getClientWidth();
               this.mPageTransformer.transformPage(var15, var2);
            }
         }
      }

      this.mCalledSuper = true;
   }

   protected boolean onRequestFocusInDescendants(int var1, Rect var2) {
      int var3 = this.getChildCount();
      int var4 = -1;
      byte var5;
      if ((var1 & 2) != 0) {
         var4 = var3;
         var3 = 0;
         var5 = 1;
      } else {
         --var3;
         var5 = -1;
      }

      for(; var3 != var4; var3 += var5) {
         View var6 = this.getChildAt(var3);
         if (var6.getVisibility() == 0) {
            ViewPager.ItemInfo var7 = this.infoForChild(var6);
            if (var7 != null && var7.position == this.mCurItem && var6.requestFocus(var1, var2)) {
               return true;
            }
         }
      }

      return false;
   }

   public void onRestoreInstanceState(Parcelable var1) {
      if (!(var1 instanceof ViewPager.SavedState)) {
         super.onRestoreInstanceState(var1);
      } else {
         ViewPager.SavedState var3 = (ViewPager.SavedState)var1;
         super.onRestoreInstanceState(var3.getSuperState());
         PagerAdapter var2 = this.mAdapter;
         if (var2 != null) {
            var2.restoreState(var3.adapterState, var3.loader);
            this.setCurrentItemInternal(var3.position, false, true);
         } else {
            this.mRestoredCurItem = var3.position;
            this.mRestoredAdapterState = var3.adapterState;
            this.mRestoredClassLoader = var3.loader;
         }

      }
   }

   public Parcelable onSaveInstanceState() {
      ViewPager.SavedState var1 = new ViewPager.SavedState(super.onSaveInstanceState());
      var1.position = this.mCurItem;
      PagerAdapter var2 = this.mAdapter;
      if (var2 != null) {
         var1.adapterState = var2.saveState();
      }

      return var1;
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      if (var1 != var3) {
         var2 = this.mPageMargin;
         this.recomputeScrollPosition(var1, var3, var2, var2);
      }

   }

   public boolean onTouchEvent(MotionEvent var1) {
      if (this.mFakeDragging) {
         return true;
      } else {
         int var2 = var1.getAction();
         boolean var3 = false;
         if (var2 == 0 && var1.getEdgeFlags() != 0) {
            return false;
         } else {
            PagerAdapter var4 = this.mAdapter;
            if (var4 != null && var4.getCount() != 0) {
               if (this.mVelocityTracker == null) {
                  this.mVelocityTracker = VelocityTracker.obtain();
               }

               this.mVelocityTracker.addMovement(var1);
               var2 = var1.getAction() & 255;
               float var5;
               if (var2 != 0) {
                  float var7;
                  if (var2 != 1) {
                     if (var2 != 2) {
                        if (var2 != 3) {
                           if (var2 != 5) {
                              if (var2 == 6) {
                                 this.onSecondaryPointerUp(var1);
                                 this.mLastMotionX = var1.getX(var1.findPointerIndex(this.mActivePointerId));
                              }
                           } else {
                              var2 = var1.getActionIndex();
                              this.mLastMotionX = var1.getX(var2);
                              this.mActivePointerId = var1.getPointerId(var2);
                           }
                        } else if (this.mIsBeingDragged) {
                           this.scrollToItem(this.mCurItem, true, 0, false);
                           var3 = this.resetTouch();
                        }
                     } else {
                        label64: {
                           if (!this.mIsBeingDragged) {
                              var2 = var1.findPointerIndex(this.mActivePointerId);
                              if (var2 == -1) {
                                 var3 = this.resetTouch();
                                 break label64;
                              }

                              var5 = var1.getX(var2);
                              float var6 = Math.abs(var5 - this.mLastMotionX);
                              var7 = var1.getY(var2);
                              float var8 = Math.abs(var7 - this.mLastMotionY);
                              if (var6 > (float)this.mTouchSlop && var6 > var8) {
                                 this.mIsBeingDragged = true;
                                 this.requestParentDisallowInterceptTouchEvent(true);
                                 var8 = this.mInitialMotionX;
                                 if (var5 - var8 > 0.0F) {
                                    var5 = var8 + (float)this.mTouchSlop;
                                 } else {
                                    var5 = var8 - (float)this.mTouchSlop;
                                 }

                                 this.mLastMotionX = var5;
                                 this.mLastMotionY = var7;
                                 this.setScrollState(1);
                                 this.setScrollingCacheEnabled(true);
                                 ViewParent var11 = this.getParent();
                                 if (var11 != null) {
                                    var11.requestDisallowInterceptTouchEvent(true);
                                 }
                              }
                           }

                           if (this.mIsBeingDragged) {
                              var3 = false | this.performDrag(var1.getX(var1.findPointerIndex(this.mActivePointerId)));
                           }
                        }
                     }
                  } else if (this.mIsBeingDragged) {
                     VelocityTracker var12 = this.mVelocityTracker;
                     var12.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                     var2 = (int)var12.getXVelocity(this.mActivePointerId);
                     this.mPopulatePending = true;
                     int var9 = this.getClientWidth();
                     int var10 = this.getScrollX();
                     ViewPager.ItemInfo var13 = this.infoForCurrentScrollPosition();
                     var7 = (float)this.mPageMargin;
                     var5 = (float)var9;
                     var7 /= var5;
                     this.setCurrentItemInternal(this.determineTargetPage(var13.position, ((float)var10 / var5 - var13.offset) / (var13.widthFactor + var7), var2, (int)(var1.getX(var1.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, var2);
                     var3 = this.resetTouch();
                  }
               } else {
                  this.mScroller.abortAnimation();
                  this.mPopulatePending = false;
                  this.populate();
                  var5 = var1.getX();
                  this.mInitialMotionX = var5;
                  this.mLastMotionX = var5;
                  var5 = var1.getY();
                  this.mInitialMotionY = var5;
                  this.mLastMotionY = var5;
                  this.mActivePointerId = var1.getPointerId(0);
               }

               if (var3) {
                  ViewCompat.postInvalidateOnAnimation(this);
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   boolean pageLeft() {
      int var1 = this.mCurItem;
      if (var1 > 0) {
         this.setCurrentItem(var1 - 1, true);
         return true;
      } else {
         return false;
      }
   }

   boolean pageRight() {
      PagerAdapter var1 = this.mAdapter;
      if (var1 != null && this.mCurItem < var1.getCount() - 1) {
         this.setCurrentItem(this.mCurItem + 1, true);
         return true;
      } else {
         return false;
      }
   }

   void populate() {
      this.populate(this.mCurItem);
   }

   void populate(int var1) {
      int var2 = this.mCurItem;
      ViewPager.ItemInfo var3;
      if (var2 != var1) {
         var3 = this.infoForPosition(var2);
         this.mCurItem = var1;
      } else {
         var3 = null;
      }

      if (this.mAdapter == null) {
         this.sortChildDrawingOrder();
      } else if (this.mPopulatePending) {
         this.sortChildDrawingOrder();
      } else if (this.getWindowToken() != null) {
         this.mAdapter.startUpdate((ViewGroup)this);
         var1 = this.mOffscreenPageLimit;
         int var4 = Math.max(0, this.mCurItem - var1);
         int var5 = this.mAdapter.getCount();
         int var6 = Math.min(var5 - 1, this.mCurItem + var1);
         if (var5 != this.mExpectedAdapterCount) {
            String var22;
            try {
               var22 = this.getResources().getResourceName(this.getId());
            } catch (NotFoundException var17) {
               var22 = Integer.toHexString(this.getId());
            }

            StringBuilder var19 = new StringBuilder();
            var19.append("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
            var19.append(this.mExpectedAdapterCount);
            var19.append(", found: ");
            var19.append(var5);
            var19.append(" Pager id: ");
            var19.append(var22);
            var19.append(" Pager class: ");
            var19.append(this.getClass());
            var19.append(" Problematic adapter: ");
            var19.append(this.mAdapter.getClass());
            throw new IllegalStateException(var19.toString());
         } else {
            var1 = 0;

            ViewPager.ItemInfo var7;
            while(true) {
               if (var1 < this.mItems.size()) {
                  var7 = (ViewPager.ItemInfo)this.mItems.get(var1);
                  if (var7.position < this.mCurItem) {
                     ++var1;
                     continue;
                  }

                  if (var7.position == this.mCurItem) {
                     break;
                  }
               }

               var7 = null;
               break;
            }

            ViewPager.ItemInfo var8 = var7;
            if (var7 == null) {
               var8 = var7;
               if (var5 > 0) {
                  var8 = this.addNewItem(this.mCurItem, var1);
               }
            }

            if (var8 != null) {
               var2 = var1 - 1;
               if (var2 >= 0) {
                  var7 = (ViewPager.ItemInfo)this.mItems.get(var2);
               } else {
                  var7 = null;
               }

               int var9 = this.getClientWidth();
               float var10;
               if (var9 <= 0) {
                  var10 = 0.0F;
               } else {
                  var10 = 2.0F - var8.widthFactor + (float)this.getPaddingLeft() / (float)var9;
               }

               int var11 = this.mCurItem - 1;

               float var12;
               int var13;
               int var14;
               ViewPager.ItemInfo var15;
               float var16;
               for(var12 = 0.0F; var11 >= 0; var12 = var16) {
                  label202: {
                     label241: {
                        if (var12 >= var10 && var11 < var4) {
                           if (var7 == null) {
                              break;
                           }

                           var13 = var1;
                           var14 = var2;
                           var15 = var7;
                           var16 = var12;
                           if (var11 != var7.position) {
                              break label202;
                           }

                           var13 = var1;
                           var14 = var2;
                           var15 = var7;
                           var16 = var12;
                           if (var7.scrolling) {
                              break label202;
                           }

                           this.mItems.remove(var2);
                           this.mAdapter.destroyItem((ViewGroup)this, var11, var7.object);
                           --var2;
                           --var1;
                           var14 = var1;
                           var13 = var2;
                           var16 = var12;
                           if (var2 >= 0) {
                              var7 = (ViewPager.ItemInfo)this.mItems.get(var2);
                              var16 = var12;
                              break label241;
                           }
                        } else if (var7 != null && var11 == var7.position) {
                           var12 += var7.widthFactor;
                           --var2;
                           var14 = var1;
                           var13 = var2;
                           var16 = var12;
                           if (var2 >= 0) {
                              var7 = (ViewPager.ItemInfo)this.mItems.get(var2);
                              var16 = var12;
                              break label241;
                           }
                        } else {
                           var12 += this.addNewItem(var11, var2 + 1).widthFactor;
                           ++var1;
                           var14 = var1;
                           var13 = var2;
                           var16 = var12;
                           if (var2 >= 0) {
                              var7 = (ViewPager.ItemInfo)this.mItems.get(var2);
                              var16 = var12;
                              break label241;
                           }
                        }

                        var7 = null;
                        var2 = var13;
                        var1 = var14;
                     }

                     var15 = var7;
                     var14 = var2;
                     var13 = var1;
                  }

                  --var11;
                  var1 = var13;
                  var2 = var14;
                  var7 = var15;
               }

               var12 = var8.widthFactor;
               var14 = var1 + 1;
               if (var12 < 2.0F) {
                  if (var14 < this.mItems.size()) {
                     var7 = (ViewPager.ItemInfo)this.mItems.get(var14);
                  } else {
                     var7 = null;
                  }

                  if (var9 <= 0) {
                     var10 = 0.0F;
                  } else {
                     var10 = (float)this.getPaddingRight() / (float)var9 + 2.0F;
                  }

                  var2 = this.mCurItem;
                  var15 = var7;

                  while(true) {
                     var13 = var2 + 1;
                     if (var13 >= var5) {
                        break;
                     }

                     label242: {
                        if (var12 >= var10 && var13 > var6) {
                           if (var15 == null) {
                              break;
                           }

                           var16 = var12;
                           var2 = var14;
                           var7 = var15;
                           if (var13 != var15.position) {
                              break label242;
                           }

                           var16 = var12;
                           var2 = var14;
                           var7 = var15;
                           if (var15.scrolling) {
                              break label242;
                           }

                           this.mItems.remove(var14);
                           this.mAdapter.destroyItem((ViewGroup)this, var13, var15.object);
                           var16 = var12;
                           var2 = var14;
                           if (var14 < this.mItems.size()) {
                              var7 = (ViewPager.ItemInfo)this.mItems.get(var14);
                              var16 = var12;
                              var2 = var14;
                              break label242;
                           }
                        } else if (var15 != null && var13 == var15.position) {
                           var12 += var15.widthFactor;
                           ++var14;
                           var16 = var12;
                           var2 = var14;
                           if (var14 < this.mItems.size()) {
                              var7 = (ViewPager.ItemInfo)this.mItems.get(var14);
                              var16 = var12;
                              var2 = var14;
                              break label242;
                           }
                        } else {
                           var7 = this.addNewItem(var13, var14);
                           ++var14;
                           var12 += var7.widthFactor;
                           var16 = var12;
                           var2 = var14;
                           if (var14 < this.mItems.size()) {
                              var7 = (ViewPager.ItemInfo)this.mItems.get(var14);
                              var2 = var14;
                              var16 = var12;
                              break label242;
                           }
                        }

                        var7 = null;
                     }

                     var12 = var16;
                     var14 = var2;
                     var15 = var7;
                     var2 = var13;
                  }
               }

               this.calculatePageOffsets(var8, var1, var3);
               this.mAdapter.setPrimaryItem((ViewGroup)this, this.mCurItem, var8.object);
            }

            this.mAdapter.finishUpdate((ViewGroup)this);
            var2 = this.getChildCount();

            for(var1 = 0; var1 < var2; ++var1) {
               View var18 = this.getChildAt(var1);
               ViewPager.LayoutParams var20 = (ViewPager.LayoutParams)var18.getLayoutParams();
               var20.childIndex = var1;
               if (!var20.isDecor && var20.widthFactor == 0.0F) {
                  var3 = this.infoForChild(var18);
                  if (var3 != null) {
                     var20.widthFactor = var3.widthFactor;
                     var20.position = var3.position;
                  }
               }
            }

            this.sortChildDrawingOrder();
            if (this.hasFocus()) {
               View var21 = this.findFocus();
               if (var21 != null) {
                  var7 = this.infoForAnyChild(var21);
               } else {
                  var7 = null;
               }

               if (var7 == null || var7.position != this.mCurItem) {
                  for(var1 = 0; var1 < this.getChildCount(); ++var1) {
                     var21 = this.getChildAt(var1);
                     var3 = this.infoForChild(var21);
                     if (var3 != null && var3.position == this.mCurItem && var21.requestFocus(2)) {
                        break;
                     }
                  }
               }
            }

         }
      }
   }

   public void removeOnAdapterChangeListener(ViewPager.OnAdapterChangeListener var1) {
      List var2 = this.mAdapterChangeListeners;
      if (var2 != null) {
         var2.remove(var1);
      }

   }

   public void removeOnPageChangeListener(ViewPager.OnPageChangeListener var1) {
      List var2 = this.mOnPageChangeListeners;
      if (var2 != null) {
         var2.remove(var1);
      }

   }

   public void removeView(View var1) {
      if (this.mInLayout) {
         this.removeViewInLayout(var1);
      } else {
         super.removeView(var1);
      }

   }

   public void setAdapter(PagerAdapter var1) {
      PagerAdapter var2 = this.mAdapter;
      byte var3 = 0;
      int var4;
      if (var2 != null) {
         var2.setViewPagerObserver((DataSetObserver)null);
         this.mAdapter.startUpdate((ViewGroup)this);

         for(var4 = 0; var4 < this.mItems.size(); ++var4) {
            ViewPager.ItemInfo var8 = (ViewPager.ItemInfo)this.mItems.get(var4);
            this.mAdapter.destroyItem((ViewGroup)this, var8.position, var8.object);
         }

         this.mAdapter.finishUpdate((ViewGroup)this);
         this.mItems.clear();
         this.removeNonDecorViews();
         this.mCurItem = 0;
         this.scrollTo(0, 0);
      }

      PagerAdapter var5 = this.mAdapter;
      this.mAdapter = var1;
      this.mExpectedAdapterCount = 0;
      if (var1 != null) {
         if (this.mObserver == null) {
            this.mObserver = new ViewPager.PagerObserver();
         }

         this.mAdapter.setViewPagerObserver(this.mObserver);
         this.mPopulatePending = false;
         boolean var6 = this.mFirstLayout;
         this.mFirstLayout = true;
         this.mExpectedAdapterCount = this.mAdapter.getCount();
         if (this.mRestoredCurItem >= 0) {
            this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
            this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
            this.mRestoredCurItem = -1;
            this.mRestoredAdapterState = null;
            this.mRestoredClassLoader = null;
         } else if (!var6) {
            this.populate();
         } else {
            this.requestLayout();
         }
      }

      List var9 = this.mAdapterChangeListeners;
      if (var9 != null && !var9.isEmpty()) {
         int var7 = this.mAdapterChangeListeners.size();

         for(var4 = var3; var4 < var7; ++var4) {
            ((ViewPager.OnAdapterChangeListener)this.mAdapterChangeListeners.get(var4)).onAdapterChanged(this, var5, var1);
         }
      }

   }

   public void setCurrentItem(int var1) {
      this.mPopulatePending = false;
      this.setCurrentItemInternal(var1, this.mFirstLayout ^ true, false);
   }

   public void setCurrentItem(int var1, boolean var2) {
      this.mPopulatePending = false;
      this.setCurrentItemInternal(var1, var2, false);
   }

   void setCurrentItemInternal(int var1, boolean var2, boolean var3) {
      this.setCurrentItemInternal(var1, var2, var3, 0);
   }

   void setCurrentItemInternal(int var1, boolean var2, boolean var3, int var4) {
      PagerAdapter var5 = this.mAdapter;
      boolean var6 = false;
      if (var5 != null && var5.getCount() > 0) {
         if (!var3 && this.mCurItem == var1 && this.mItems.size() != 0) {
            this.setScrollingCacheEnabled(false);
         } else {
            int var7;
            if (var1 < 0) {
               var7 = 0;
            } else {
               var7 = var1;
               if (var1 >= this.mAdapter.getCount()) {
                  var7 = this.mAdapter.getCount() - 1;
               }
            }

            int var8 = this.mOffscreenPageLimit;
            var1 = this.mCurItem;
            if (var7 > var1 + var8 || var7 < var1 - var8) {
               for(var1 = 0; var1 < this.mItems.size(); ++var1) {
                  ((ViewPager.ItemInfo)this.mItems.get(var1)).scrolling = true;
               }
            }

            var3 = var6;
            if (this.mCurItem != var7) {
               var3 = true;
            }

            if (this.mFirstLayout) {
               this.mCurItem = var7;
               if (var3) {
                  this.dispatchOnPageSelected(var7);
               }

               this.requestLayout();
            } else {
               this.populate(var7);
               this.scrollToItem(var7, var2, var4, var3);
            }

         }
      } else {
         this.setScrollingCacheEnabled(false);
      }
   }

   ViewPager.OnPageChangeListener setInternalPageChangeListener(ViewPager.OnPageChangeListener var1) {
      ViewPager.OnPageChangeListener var2 = this.mInternalPageChangeListener;
      this.mInternalPageChangeListener = var1;
      return var2;
   }

   public void setOffscreenPageLimit(int var1) {
      int var2 = var1;
      if (var1 < 1) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Requested offscreen page limit ");
         var3.append(var1);
         var3.append(" too small; defaulting to ");
         var3.append(1);
         Log.w("ViewPager", var3.toString());
         var2 = 1;
      }

      if (var2 != this.mOffscreenPageLimit) {
         this.mOffscreenPageLimit = var2;
         this.populate();
      }

   }

   @Deprecated
   public void setOnPageChangeListener(ViewPager.OnPageChangeListener var1) {
      this.mOnPageChangeListener = var1;
   }

   public void setPageMargin(int var1) {
      int var2 = this.mPageMargin;
      this.mPageMargin = var1;
      int var3 = this.getWidth();
      this.recomputeScrollPosition(var3, var3, var1, var2);
      this.requestLayout();
   }

   public void setPageMarginDrawable(int var1) {
      this.setPageMarginDrawable(ContextCompat.getDrawable(this.getContext(), var1));
   }

   public void setPageMarginDrawable(Drawable var1) {
      this.mMarginDrawable = var1;
      if (var1 != null) {
         this.refreshDrawableState();
      }

      boolean var2;
      if (var1 == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.setWillNotDraw(var2);
      this.invalidate();
   }

   public void setPageTransformer(boolean var1, ViewPager.PageTransformer var2) {
      this.setPageTransformer(var1, var2, 2);
   }

   public void setPageTransformer(boolean var1, ViewPager.PageTransformer var2, int var3) {
      byte var4 = 1;
      boolean var5;
      if (var2 != null) {
         var5 = true;
      } else {
         var5 = false;
      }

      boolean var6;
      if (this.mPageTransformer != null) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7;
      if (var5 != var6) {
         var7 = true;
      } else {
         var7 = false;
      }

      this.mPageTransformer = var2;
      this.setChildrenDrawingOrderEnabled(var5);
      if (var5) {
         if (var1) {
            var4 = 2;
         }

         this.mDrawingOrder = var4;
         this.mPageTransformerLayerType = var3;
      } else {
         this.mDrawingOrder = 0;
      }

      if (var7) {
         this.populate();
      }

   }

   void setScrollState(int var1) {
      if (this.mScrollState != var1) {
         this.mScrollState = var1;
         if (this.mPageTransformer != null) {
            boolean var2;
            if (var1 != 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            this.enableLayers(var2);
         }

         this.dispatchOnScrollStateChanged(var1);
      }
   }

   void smoothScrollTo(int var1, int var2) {
      this.smoothScrollTo(var1, var2, 0);
   }

   void smoothScrollTo(int var1, int var2, int var3) {
      if (this.getChildCount() == 0) {
         this.setScrollingCacheEnabled(false);
      } else {
         Scroller var4 = this.mScroller;
         boolean var5;
         if (var4 != null && !var4.isFinished()) {
            var5 = true;
         } else {
            var5 = false;
         }

         int var12;
         if (var5) {
            if (this.mIsScrollStarted) {
               var12 = this.mScroller.getCurrX();
            } else {
               var12 = this.mScroller.getStartX();
            }

            this.mScroller.abortAnimation();
            this.setScrollingCacheEnabled(false);
         } else {
            var12 = this.getScrollX();
         }

         int var6 = this.getScrollY();
         int var7 = var1 - var12;
         var2 -= var6;
         if (var7 == 0 && var2 == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
         } else {
            this.setScrollingCacheEnabled(true);
            this.setScrollState(2);
            var1 = this.getClientWidth();
            int var8 = var1 / 2;
            float var9 = (float)Math.abs(var7);
            float var10 = (float)var1;
            float var11 = Math.min(1.0F, var9 * 1.0F / var10);
            var9 = (float)var8;
            var11 = this.distanceInfluenceForSnapDuration(var11);
            var1 = Math.abs(var3);
            if (var1 > 0) {
               var1 = Math.round(Math.abs((var9 + var11 * var9) / (float)var1) * 1000.0F) * 4;
            } else {
               var9 = this.mAdapter.getPageWidth(this.mCurItem);
               var1 = (int)(((float)Math.abs(var7) / (var10 * var9 + (float)this.mPageMargin) + 1.0F) * 100.0F);
            }

            var1 = Math.min(var1, 600);
            this.mIsScrollStarted = false;
            this.mScroller.startScroll(var12, var6, var7, var2, var1);
            ViewCompat.postInvalidateOnAnimation(this);
         }
      }
   }

   protected boolean verifyDrawable(Drawable var1) {
      boolean var2;
      if (!super.verifyDrawable(var1) && var1 != this.mMarginDrawable) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   @Inherited
   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.TYPE})
   public @interface DecorView {
   }

   static class ItemInfo {
      Object object;
      float offset;
      int position;
      boolean scrolling;
      float widthFactor;
   }

   public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
      int childIndex;
      public int gravity;
      public boolean isDecor;
      boolean needsMeasure;
      int position;
      float widthFactor = 0.0F;

      public LayoutParams() {
         super(-1, -1);
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var3 = var1.obtainStyledAttributes(var2, ViewPager.LAYOUT_ATTRS);
         this.gravity = var3.getInteger(0, 48);
         var3.recycle();
      }
   }

   class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
      private boolean canScroll() {
         PagerAdapter var1 = ViewPager.this.mAdapter;
         boolean var2 = true;
         if (var1 == null || ViewPager.this.mAdapter.getCount() <= 1) {
            var2 = false;
         }

         return var2;
      }

      public void onInitializeAccessibilityEvent(View var1, AccessibilityEvent var2) {
         super.onInitializeAccessibilityEvent(var1, var2);
         var2.setClassName(ViewPager.class.getName());
         var2.setScrollable(this.canScroll());
         if (var2.getEventType() == 4096 && ViewPager.this.mAdapter != null) {
            var2.setItemCount(ViewPager.this.mAdapter.getCount());
            var2.setFromIndex(ViewPager.this.mCurItem);
            var2.setToIndex(ViewPager.this.mCurItem);
         }

      }

      public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfoCompat var2) {
         super.onInitializeAccessibilityNodeInfo(var1, var2);
         var2.setClassName(ViewPager.class.getName());
         var2.setScrollable(this.canScroll());
         if (ViewPager.this.canScrollHorizontally(1)) {
            var2.addAction(4096);
         }

         if (ViewPager.this.canScrollHorizontally(-1)) {
            var2.addAction(8192);
         }

      }

      public boolean performAccessibilityAction(View var1, int var2, Bundle var3) {
         if (super.performAccessibilityAction(var1, var2, var3)) {
            return true;
         } else {
            ViewPager var4;
            if (var2 != 4096) {
               if (var2 != 8192) {
                  return false;
               } else if (ViewPager.this.canScrollHorizontally(-1)) {
                  var4 = ViewPager.this;
                  var4.setCurrentItem(var4.mCurItem - 1);
                  return true;
               } else {
                  return false;
               }
            } else if (ViewPager.this.canScrollHorizontally(1)) {
               var4 = ViewPager.this;
               var4.setCurrentItem(var4.mCurItem + 1);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public interface OnAdapterChangeListener {
      void onAdapterChanged(ViewPager var1, PagerAdapter var2, PagerAdapter var3);
   }

   public interface OnPageChangeListener {
      void onPageScrollStateChanged(int var1);

      void onPageScrolled(int var1, float var2, int var3);

      void onPageSelected(int var1);
   }

   public interface PageTransformer {
      void transformPage(View var1, float var2);
   }

   private class PagerObserver extends DataSetObserver {
      PagerObserver() {
      }

      public void onChanged() {
         ViewPager.this.dataSetChanged();
      }

      public void onInvalidated() {
         ViewPager.this.dataSetChanged();
      }
   }

   public static class SavedState extends AbsSavedState {
      public static final Creator<ViewPager.SavedState> CREATOR = new ClassLoaderCreator<ViewPager.SavedState>() {
         public ViewPager.SavedState createFromParcel(Parcel var1) {
            return new ViewPager.SavedState(var1, (ClassLoader)null);
         }

         public ViewPager.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            return new ViewPager.SavedState(var1, var2);
         }

         public ViewPager.SavedState[] newArray(int var1) {
            return new ViewPager.SavedState[var1];
         }
      };
      Parcelable adapterState;
      ClassLoader loader;
      int position;

      SavedState(Parcel var1, ClassLoader var2) {
         super(var1, var2);
         ClassLoader var3 = var2;
         if (var2 == null) {
            var3 = this.getClass().getClassLoader();
         }

         this.position = var1.readInt();
         this.adapterState = var1.readParcelable(var3);
         this.loader = var3;
      }

      public SavedState(Parcelable var1) {
         super(var1);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("FragmentPager.SavedState{");
         var1.append(Integer.toHexString(System.identityHashCode(this)));
         var1.append(" position=");
         var1.append(this.position);
         var1.append("}");
         return var1.toString();
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         var1.writeInt(this.position);
         var1.writeParcelable(this.adapterState, var2);
      }
   }

   public static class SimpleOnPageChangeListener implements ViewPager.OnPageChangeListener {
      public void onPageScrollStateChanged(int var1) {
      }

      public void onPageScrolled(int var1, float var2, int var3) {
      }

      public void onPageSelected(int var1) {
      }
   }

   static class ViewPositionComparator implements Comparator<View> {
      public int compare(View var1, View var2) {
         ViewPager.LayoutParams var4 = (ViewPager.LayoutParams)var1.getLayoutParams();
         ViewPager.LayoutParams var5 = (ViewPager.LayoutParams)var2.getLayoutParams();
         if (var4.isDecor != var5.isDecor) {
            byte var3;
            if (var4.isDecor) {
               var3 = 1;
            } else {
               var3 = -1;
            }

            return var3;
         } else {
            return var4.position - var5.position;
         }
      }
   }
}
