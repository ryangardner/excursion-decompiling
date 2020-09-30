package androidx.viewpager2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.BaseSavedState;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.R;
import androidx.viewpager2.adapter.StatefulAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ViewPager2 extends ViewGroup {
   public static final int OFFSCREEN_PAGE_LIMIT_DEFAULT = -1;
   public static final int ORIENTATION_HORIZONTAL = 0;
   public static final int ORIENTATION_VERTICAL = 1;
   public static final int SCROLL_STATE_DRAGGING = 1;
   public static final int SCROLL_STATE_IDLE = 0;
   public static final int SCROLL_STATE_SETTLING = 2;
   static boolean sFeatureEnhancedA11yEnabled;
   ViewPager2.AccessibilityProvider mAccessibilityProvider;
   int mCurrentItem;
   private RecyclerView.AdapterDataObserver mCurrentItemDataSetChangeObserver = new ViewPager2.DataSetChangeObserver() {
      public void onChanged() {
         ViewPager2.this.mCurrentItemDirty = true;
         ViewPager2.this.mScrollEventAdapter.notifyDataSetChangeHappened();
      }
   };
   boolean mCurrentItemDirty = false;
   private CompositeOnPageChangeCallback mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
   private FakeDrag mFakeDragger;
   private LinearLayoutManager mLayoutManager;
   private int mOffscreenPageLimit = -1;
   private CompositeOnPageChangeCallback mPageChangeEventDispatcher;
   private PageTransformerAdapter mPageTransformerAdapter;
   private PagerSnapHelper mPagerSnapHelper;
   private Parcelable mPendingAdapterState;
   private int mPendingCurrentItem = -1;
   RecyclerView mRecyclerView;
   private RecyclerView.ItemAnimator mSavedItemAnimator = null;
   private boolean mSavedItemAnimatorPresent = false;
   ScrollEventAdapter mScrollEventAdapter;
   private final Rect mTmpChildRect = new Rect();
   private final Rect mTmpContainerRect = new Rect();
   private boolean mUserInputEnabled = true;

   public ViewPager2(Context var1) {
      super(var1);
      this.initialize(var1, (AttributeSet)null);
   }

   public ViewPager2(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.initialize(var1, var2);
   }

   public ViewPager2(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.initialize(var1, var2);
   }

   public ViewPager2(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.initialize(var1, var2);
   }

   private RecyclerView.OnChildAttachStateChangeListener enforceChildFillListener() {
      return new RecyclerView.OnChildAttachStateChangeListener() {
         public void onChildViewAttachedToWindow(View var1) {
            RecyclerView.LayoutParams var2 = (RecyclerView.LayoutParams)var1.getLayoutParams();
            if (var2.width != -1 || var2.height != -1) {
               throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
            }
         }

         public void onChildViewDetachedFromWindow(View var1) {
         }
      };
   }

   private void initialize(Context var1, AttributeSet var2) {
      Object var3;
      if (sFeatureEnhancedA11yEnabled) {
         var3 = new ViewPager2.PageAwareAccessibilityProvider();
      } else {
         var3 = new ViewPager2.BasicAccessibilityProvider();
      }

      this.mAccessibilityProvider = (ViewPager2.AccessibilityProvider)var3;
      ViewPager2.RecyclerViewImpl var10 = new ViewPager2.RecyclerViewImpl(var1);
      this.mRecyclerView = var10;
      var10.setId(ViewCompat.generateViewId());
      this.mRecyclerView.setDescendantFocusability(131072);
      ViewPager2.LinearLayoutManagerImpl var11 = new ViewPager2.LinearLayoutManagerImpl(var1);
      this.mLayoutManager = var11;
      this.mRecyclerView.setLayoutManager(var11);
      this.mRecyclerView.setScrollingTouchSlop(1);
      this.setOrientation(var1, var2);
      this.mRecyclerView.setLayoutParams(new LayoutParams(-1, -1));
      this.mRecyclerView.addOnChildAttachStateChangeListener(this.enforceChildFillListener());
      this.mScrollEventAdapter = new ScrollEventAdapter(this);
      this.mFakeDragger = new FakeDrag(this, this.mScrollEventAdapter, this.mRecyclerView);
      ViewPager2.PagerSnapHelperImpl var4 = new ViewPager2.PagerSnapHelperImpl();
      this.mPagerSnapHelper = var4;
      var4.attachToRecyclerView(this.mRecyclerView);
      this.mRecyclerView.addOnScrollListener(this.mScrollEventAdapter);
      CompositeOnPageChangeCallback var5 = new CompositeOnPageChangeCallback(3);
      this.mPageChangeEventDispatcher = var5;
      this.mScrollEventAdapter.setOnPageChangeCallback(var5);
      ViewPager2.OnPageChangeCallback var6 = new ViewPager2.OnPageChangeCallback() {
         public void onPageScrollStateChanged(int var1) {
            if (var1 == 0) {
               ViewPager2.this.updateCurrentItem();
            }

         }

         public void onPageSelected(int var1) {
            if (ViewPager2.this.mCurrentItem != var1) {
               ViewPager2.this.mCurrentItem = var1;
               ViewPager2.this.mAccessibilityProvider.onSetNewCurrentItem();
            }

         }
      };
      ViewPager2.OnPageChangeCallback var7 = new ViewPager2.OnPageChangeCallback() {
         public void onPageSelected(int var1) {
            ViewPager2.this.clearFocus();
            if (ViewPager2.this.hasFocus()) {
               ViewPager2.this.mRecyclerView.requestFocus(2);
            }

         }
      };
      this.mPageChangeEventDispatcher.addOnPageChangeCallback(var6);
      this.mPageChangeEventDispatcher.addOnPageChangeCallback(var7);
      this.mAccessibilityProvider.onInitialize(this.mPageChangeEventDispatcher, this.mRecyclerView);
      this.mPageChangeEventDispatcher.addOnPageChangeCallback(this.mExternalPageChangeCallbacks);
      PageTransformerAdapter var8 = new PageTransformerAdapter(this.mLayoutManager);
      this.mPageTransformerAdapter = var8;
      this.mPageChangeEventDispatcher.addOnPageChangeCallback(var8);
      RecyclerView var9 = this.mRecyclerView;
      this.attachViewToParent(var9, 0, var9.getLayoutParams());
   }

   private void registerCurrentItemDataSetTracker(RecyclerView.Adapter<?> var1) {
      if (var1 != null) {
         var1.registerAdapterDataObserver(this.mCurrentItemDataSetChangeObserver);
      }

   }

   private void restorePendingState() {
      if (this.mPendingCurrentItem != -1) {
         RecyclerView.Adapter var1 = this.getAdapter();
         if (var1 != null) {
            Parcelable var2 = this.mPendingAdapterState;
            if (var2 != null) {
               if (var1 instanceof StatefulAdapter) {
                  ((StatefulAdapter)var1).restoreState(var2);
               }

               this.mPendingAdapterState = null;
            }

            int var3 = Math.max(0, Math.min(this.mPendingCurrentItem, var1.getItemCount() - 1));
            this.mCurrentItem = var3;
            this.mPendingCurrentItem = -1;
            this.mRecyclerView.scrollToPosition(var3);
            this.mAccessibilityProvider.onRestorePendingState();
         }
      }
   }

   private void setOrientation(Context var1, AttributeSet var2) {
      TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.ViewPager2);
      if (VERSION.SDK_INT >= 29) {
         this.saveAttributeDataForStyleable(var1, R.styleable.ViewPager2, var2, var3, 0, 0);
      }

      try {
         this.setOrientation(var3.getInt(R.styleable.ViewPager2_android_orientation, 0));
      } finally {
         var3.recycle();
      }

   }

   private void unregisterCurrentItemDataSetTracker(RecyclerView.Adapter<?> var1) {
      if (var1 != null) {
         var1.unregisterAdapterDataObserver(this.mCurrentItemDataSetChangeObserver);
      }

   }

   public void addItemDecoration(RecyclerView.ItemDecoration var1) {
      this.mRecyclerView.addItemDecoration(var1);
   }

   public void addItemDecoration(RecyclerView.ItemDecoration var1, int var2) {
      this.mRecyclerView.addItemDecoration(var1, var2);
   }

   public boolean beginFakeDrag() {
      return this.mFakeDragger.beginFakeDrag();
   }

   public boolean canScrollHorizontally(int var1) {
      return this.mRecyclerView.canScrollHorizontally(var1);
   }

   public boolean canScrollVertically(int var1) {
      return this.mRecyclerView.canScrollVertically(var1);
   }

   protected void dispatchRestoreInstanceState(SparseArray<Parcelable> var1) {
      Parcelable var2 = (Parcelable)var1.get(this.getId());
      if (var2 instanceof ViewPager2.SavedState) {
         int var3 = ((ViewPager2.SavedState)var2).mRecyclerViewId;
         var1.put(this.mRecyclerView.getId(), var1.get(var3));
         var1.remove(var3);
      }

      super.dispatchRestoreInstanceState(var1);
      this.restorePendingState();
   }

   public boolean endFakeDrag() {
      return this.mFakeDragger.endFakeDrag();
   }

   public boolean fakeDragBy(float var1) {
      return this.mFakeDragger.fakeDragBy(var1);
   }

   public CharSequence getAccessibilityClassName() {
      return (CharSequence)(this.mAccessibilityProvider.handlesGetAccessibilityClassName() ? this.mAccessibilityProvider.onGetAccessibilityClassName() : super.getAccessibilityClassName());
   }

   public RecyclerView.Adapter getAdapter() {
      return this.mRecyclerView.getAdapter();
   }

   public int getCurrentItem() {
      return this.mCurrentItem;
   }

   public RecyclerView.ItemDecoration getItemDecorationAt(int var1) {
      return this.mRecyclerView.getItemDecorationAt(var1);
   }

   public int getItemDecorationCount() {
      return this.mRecyclerView.getItemDecorationCount();
   }

   public int getOffscreenPageLimit() {
      return this.mOffscreenPageLimit;
   }

   public int getOrientation() {
      return this.mLayoutManager.getOrientation();
   }

   int getPageSize() {
      RecyclerView var1 = this.mRecyclerView;
      int var2;
      int var3;
      if (this.getOrientation() == 0) {
         var2 = var1.getWidth() - var1.getPaddingLeft();
         var3 = var1.getPaddingRight();
      } else {
         var2 = var1.getHeight() - var1.getPaddingTop();
         var3 = var1.getPaddingBottom();
      }

      return var2 - var3;
   }

   public int getScrollState() {
      return this.mScrollEventAdapter.getScrollState();
   }

   public void invalidateItemDecorations() {
      this.mRecyclerView.invalidateItemDecorations();
   }

   public boolean isFakeDragging() {
      return this.mFakeDragger.isFakeDragging();
   }

   boolean isRtl() {
      int var1 = this.mLayoutManager.getLayoutDirection();
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public boolean isUserInputEnabled() {
      return this.mUserInputEnabled;
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      this.mAccessibilityProvider.onInitializeAccessibilityNodeInfo(var1);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = this.mRecyclerView.getMeasuredWidth();
      int var7 = this.mRecyclerView.getMeasuredHeight();
      this.mTmpContainerRect.left = this.getPaddingLeft();
      this.mTmpContainerRect.right = var4 - var2 - this.getPaddingRight();
      this.mTmpContainerRect.top = this.getPaddingTop();
      this.mTmpContainerRect.bottom = var5 - var3 - this.getPaddingBottom();
      Gravity.apply(8388659, var6, var7, this.mTmpContainerRect, this.mTmpChildRect);
      this.mRecyclerView.layout(this.mTmpChildRect.left, this.mTmpChildRect.top, this.mTmpChildRect.right, this.mTmpChildRect.bottom);
      if (this.mCurrentItemDirty) {
         this.updateCurrentItem();
      }

   }

   protected void onMeasure(int var1, int var2) {
      this.measureChild(this.mRecyclerView, var1, var2);
      int var3 = this.mRecyclerView.getMeasuredWidth();
      int var4 = this.mRecyclerView.getMeasuredHeight();
      int var5 = this.mRecyclerView.getMeasuredState();
      int var6 = this.getPaddingLeft();
      int var7 = this.getPaddingRight();
      int var8 = this.getPaddingTop();
      int var9 = this.getPaddingBottom();
      var7 = Math.max(var3 + var6 + var7, this.getSuggestedMinimumWidth());
      var8 = Math.max(var4 + var8 + var9, this.getSuggestedMinimumHeight());
      this.setMeasuredDimension(resolveSizeAndState(var7, var1, var5), resolveSizeAndState(var8, var2, var5 << 16));
   }

   protected void onRestoreInstanceState(Parcelable var1) {
      if (!(var1 instanceof ViewPager2.SavedState)) {
         super.onRestoreInstanceState(var1);
      } else {
         ViewPager2.SavedState var2 = (ViewPager2.SavedState)var1;
         super.onRestoreInstanceState(var2.getSuperState());
         this.mPendingCurrentItem = var2.mCurrentItem;
         this.mPendingAdapterState = var2.mAdapterState;
      }
   }

   protected Parcelable onSaveInstanceState() {
      ViewPager2.SavedState var1 = new ViewPager2.SavedState(super.onSaveInstanceState());
      var1.mRecyclerViewId = this.mRecyclerView.getId();
      int var2 = this.mPendingCurrentItem;
      int var3 = var2;
      if (var2 == -1) {
         var3 = this.mCurrentItem;
      }

      var1.mCurrentItem = var3;
      Parcelable var4 = this.mPendingAdapterState;
      if (var4 != null) {
         var1.mAdapterState = var4;
      } else {
         RecyclerView.Adapter var5 = this.mRecyclerView.getAdapter();
         if (var5 instanceof StatefulAdapter) {
            var1.mAdapterState = ((StatefulAdapter)var5).saveState();
         }
      }

      return var1;
   }

   public void onViewAdded(View var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.getClass().getSimpleName());
      var2.append(" does not support direct child views");
      throw new IllegalStateException(var2.toString());
   }

   public boolean performAccessibilityAction(int var1, Bundle var2) {
      return this.mAccessibilityProvider.handlesPerformAccessibilityAction(var1, var2) ? this.mAccessibilityProvider.onPerformAccessibilityAction(var1, var2) : super.performAccessibilityAction(var1, var2);
   }

   public void registerOnPageChangeCallback(ViewPager2.OnPageChangeCallback var1) {
      this.mExternalPageChangeCallbacks.addOnPageChangeCallback(var1);
   }

   public void removeItemDecoration(RecyclerView.ItemDecoration var1) {
      this.mRecyclerView.removeItemDecoration(var1);
   }

   public void removeItemDecorationAt(int var1) {
      this.mRecyclerView.removeItemDecorationAt(var1);
   }

   public void requestTransform() {
      if (this.mPageTransformerAdapter.getPageTransformer() != null) {
         double var1 = this.mScrollEventAdapter.getRelativeScrollPosition();
         int var3 = (int)var1;
         float var4 = (float)(var1 - (double)var3);
         int var5 = Math.round((float)this.getPageSize() * var4);
         this.mPageTransformerAdapter.onPageScrolled(var3, var4, var5);
      }
   }

   public void setAdapter(RecyclerView.Adapter var1) {
      RecyclerView.Adapter var2 = this.mRecyclerView.getAdapter();
      this.mAccessibilityProvider.onDetachAdapter(var2);
      this.unregisterCurrentItemDataSetTracker(var2);
      this.mRecyclerView.setAdapter(var1);
      this.mCurrentItem = 0;
      this.restorePendingState();
      this.mAccessibilityProvider.onAttachAdapter(var1);
      this.registerCurrentItemDataSetTracker(var1);
   }

   public void setCurrentItem(int var1) {
      this.setCurrentItem(var1, true);
   }

   public void setCurrentItem(int var1, boolean var2) {
      if (!this.isFakeDragging()) {
         this.setCurrentItemInternal(var1, var2);
      } else {
         throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
      }
   }

   void setCurrentItemInternal(int var1, boolean var2) {
      RecyclerView.Adapter var3 = this.getAdapter();
      if (var3 == null) {
         if (this.mPendingCurrentItem != -1) {
            this.mPendingCurrentItem = Math.max(var1, 0);
         }

      } else if (var3.getItemCount() > 0) {
         int var4 = Math.min(Math.max(var1, 0), var3.getItemCount() - 1);
         if (var4 != this.mCurrentItem || !this.mScrollEventAdapter.isIdle()) {
            if (var4 != this.mCurrentItem || !var2) {
               double var5 = (double)this.mCurrentItem;
               this.mCurrentItem = var4;
               this.mAccessibilityProvider.onSetNewCurrentItem();
               if (!this.mScrollEventAdapter.isIdle()) {
                  var5 = this.mScrollEventAdapter.getRelativeScrollPosition();
               }

               this.mScrollEventAdapter.notifyProgrammaticScroll(var4, var2);
               if (!var2) {
                  this.mRecyclerView.scrollToPosition(var4);
               } else {
                  double var7 = (double)var4;
                  if (Math.abs(var7 - var5) > 3.0D) {
                     RecyclerView var9 = this.mRecyclerView;
                     if (var7 > var5) {
                        var1 = var4 - 3;
                     } else {
                        var1 = var4 + 3;
                     }

                     var9.scrollToPosition(var1);
                     this.mRecyclerView.post(new ViewPager2.SmoothScrollToPosition(var4, this.mRecyclerView));
                  } else {
                     this.mRecyclerView.smoothScrollToPosition(var4);
                  }

               }
            }
         }
      }
   }

   public void setLayoutDirection(int var1) {
      super.setLayoutDirection(var1);
      this.mAccessibilityProvider.onSetLayoutDirection();
   }

   public void setOffscreenPageLimit(int var1) {
      if (var1 < 1 && var1 != -1) {
         throw new IllegalArgumentException("Offscreen page limit must be OFFSCREEN_PAGE_LIMIT_DEFAULT or a number > 0");
      } else {
         this.mOffscreenPageLimit = var1;
         this.mRecyclerView.requestLayout();
      }
   }

   public void setOrientation(int var1) {
      this.mLayoutManager.setOrientation(var1);
      this.mAccessibilityProvider.onSetOrientation();
   }

   public void setPageTransformer(ViewPager2.PageTransformer var1) {
      if (var1 != null) {
         if (!this.mSavedItemAnimatorPresent) {
            this.mSavedItemAnimator = this.mRecyclerView.getItemAnimator();
            this.mSavedItemAnimatorPresent = true;
         }

         this.mRecyclerView.setItemAnimator((RecyclerView.ItemAnimator)null);
      } else if (this.mSavedItemAnimatorPresent) {
         this.mRecyclerView.setItemAnimator(this.mSavedItemAnimator);
         this.mSavedItemAnimator = null;
         this.mSavedItemAnimatorPresent = false;
      }

      if (var1 != this.mPageTransformerAdapter.getPageTransformer()) {
         this.mPageTransformerAdapter.setPageTransformer(var1);
         this.requestTransform();
      }
   }

   public void setUserInputEnabled(boolean var1) {
      this.mUserInputEnabled = var1;
      this.mAccessibilityProvider.onSetUserInputEnabled();
   }

   void snapToPage() {
      View var1 = this.mPagerSnapHelper.findSnapView(this.mLayoutManager);
      if (var1 != null) {
         int[] var2 = this.mPagerSnapHelper.calculateDistanceToFinalSnap(this.mLayoutManager, var1);
         if (var2[0] != 0 || var2[1] != 0) {
            this.mRecyclerView.smoothScrollBy(var2[0], var2[1]);
         }

      }
   }

   public void unregisterOnPageChangeCallback(ViewPager2.OnPageChangeCallback var1) {
      this.mExternalPageChangeCallbacks.removeOnPageChangeCallback(var1);
   }

   void updateCurrentItem() {
      PagerSnapHelper var1 = this.mPagerSnapHelper;
      if (var1 != null) {
         View var3 = var1.findSnapView(this.mLayoutManager);
         if (var3 != null) {
            int var2 = this.mLayoutManager.getPosition(var3);
            if (var2 != this.mCurrentItem && this.getScrollState() == 0) {
               this.mPageChangeEventDispatcher.onPageSelected(var2);
            }

            this.mCurrentItemDirty = false;
         }
      } else {
         throw new IllegalStateException("Design assumption violated.");
      }
   }

   private abstract class AccessibilityProvider {
      private AccessibilityProvider() {
      }

      // $FF: synthetic method
      AccessibilityProvider(Object var2) {
         this();
      }

      boolean handlesGetAccessibilityClassName() {
         return false;
      }

      boolean handlesLmPerformAccessibilityAction(int var1) {
         return false;
      }

      boolean handlesPerformAccessibilityAction(int var1, Bundle var2) {
         return false;
      }

      boolean handlesRvGetAccessibilityClassName() {
         return false;
      }

      void onAttachAdapter(RecyclerView.Adapter<?> var1) {
      }

      void onDetachAdapter(RecyclerView.Adapter<?> var1) {
      }

      String onGetAccessibilityClassName() {
         throw new IllegalStateException("Not implemented.");
      }

      void onInitialize(CompositeOnPageChangeCallback var1, RecyclerView var2) {
      }

      void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      }

      void onLmInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat var1) {
      }

      boolean onLmPerformAccessibilityAction(int var1) {
         throw new IllegalStateException("Not implemented.");
      }

      boolean onPerformAccessibilityAction(int var1, Bundle var2) {
         throw new IllegalStateException("Not implemented.");
      }

      void onRestorePendingState() {
      }

      CharSequence onRvGetAccessibilityClassName() {
         throw new IllegalStateException("Not implemented.");
      }

      void onRvInitializeAccessibilityEvent(AccessibilityEvent var1) {
      }

      void onSetLayoutDirection() {
      }

      void onSetNewCurrentItem() {
      }

      void onSetOrientation() {
      }

      void onSetUserInputEnabled() {
      }
   }

   class BasicAccessibilityProvider extends ViewPager2.AccessibilityProvider {
      BasicAccessibilityProvider() {
         super(null);
      }

      public boolean handlesLmPerformAccessibilityAction(int var1) {
         boolean var2;
         if ((var1 == 8192 || var1 == 4096) && !ViewPager2.this.isUserInputEnabled()) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean handlesRvGetAccessibilityClassName() {
         return true;
      }

      public void onLmInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat var1) {
         if (!ViewPager2.this.isUserInputEnabled()) {
            var1.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
            var1.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
            var1.setScrollable(false);
         }

      }

      public boolean onLmPerformAccessibilityAction(int var1) {
         if (this.handlesLmPerformAccessibilityAction(var1)) {
            return false;
         } else {
            throw new IllegalStateException();
         }
      }

      public CharSequence onRvGetAccessibilityClassName() {
         if (this.handlesRvGetAccessibilityClassName()) {
            return "androidx.viewpager.widget.ViewPager";
         } else {
            throw new IllegalStateException();
         }
      }
   }

   private abstract static class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
      private DataSetChangeObserver() {
      }

      // $FF: synthetic method
      DataSetChangeObserver(Object var1) {
         this();
      }

      public abstract void onChanged();

      public final void onItemRangeChanged(int var1, int var2) {
         this.onChanged();
      }

      public final void onItemRangeChanged(int var1, int var2, Object var3) {
         this.onChanged();
      }

      public final void onItemRangeInserted(int var1, int var2) {
         this.onChanged();
      }

      public final void onItemRangeMoved(int var1, int var2, int var3) {
         this.onChanged();
      }

      public final void onItemRangeRemoved(int var1, int var2) {
         this.onChanged();
      }
   }

   private class LinearLayoutManagerImpl extends LinearLayoutManager {
      LinearLayoutManagerImpl(Context var2) {
         super(var2);
      }

      protected void calculateExtraLayoutSpace(RecyclerView.State var1, int[] var2) {
         int var3 = ViewPager2.this.getOffscreenPageLimit();
         if (var3 == -1) {
            super.calculateExtraLayoutSpace(var1, var2);
         } else {
            var3 = ViewPager2.this.getPageSize() * var3;
            var2[0] = var3;
            var2[1] = var3;
         }
      }

      public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler var1, RecyclerView.State var2, AccessibilityNodeInfoCompat var3) {
         super.onInitializeAccessibilityNodeInfo(var1, var2, var3);
         ViewPager2.this.mAccessibilityProvider.onLmInitializeAccessibilityNodeInfo(var3);
      }

      public boolean performAccessibilityAction(RecyclerView.Recycler var1, RecyclerView.State var2, int var3, Bundle var4) {
         return ViewPager2.this.mAccessibilityProvider.handlesLmPerformAccessibilityAction(var3) ? ViewPager2.this.mAccessibilityProvider.onLmPerformAccessibilityAction(var3) : super.performAccessibilityAction(var1, var2, var3, var4);
      }

      public boolean requestChildRectangleOnScreen(RecyclerView var1, View var2, Rect var3, boolean var4, boolean var5) {
         return false;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface OffscreenPageLimit {
   }

   public abstract static class OnPageChangeCallback {
      public void onPageScrollStateChanged(int var1) {
      }

      public void onPageScrolled(int var1, float var2, int var3) {
      }

      public void onPageSelected(int var1) {
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface Orientation {
   }

   class PageAwareAccessibilityProvider extends ViewPager2.AccessibilityProvider {
      private final AccessibilityViewCommand mActionPageBackward = new AccessibilityViewCommand() {
         public boolean perform(View var1, AccessibilityViewCommand.CommandArguments var2) {
            ViewPager2 var3 = (ViewPager2)var1;
            PageAwareAccessibilityProvider.this.setCurrentItemFromAccessibilityCommand(var3.getCurrentItem() - 1);
            return true;
         }
      };
      private final AccessibilityViewCommand mActionPageForward = new AccessibilityViewCommand() {
         public boolean perform(View var1, AccessibilityViewCommand.CommandArguments var2) {
            ViewPager2 var3 = (ViewPager2)var1;
            PageAwareAccessibilityProvider.this.setCurrentItemFromAccessibilityCommand(var3.getCurrentItem() + 1);
            return true;
         }
      };
      private RecyclerView.AdapterDataObserver mAdapterDataObserver;

      PageAwareAccessibilityProvider() {
         super(null);
      }

      private void addCollectionInfo(AccessibilityNodeInfo var1) {
         int var2;
         int var3;
         label15: {
            if (ViewPager2.this.getAdapter() != null) {
               if (ViewPager2.this.getOrientation() != 1) {
                  var3 = ViewPager2.this.getAdapter().getItemCount();
                  var2 = 0;
                  break label15;
               }

               var2 = ViewPager2.this.getAdapter().getItemCount();
            } else {
               var2 = 0;
            }

            var3 = 0;
         }

         AccessibilityNodeInfoCompat.wrap(var1).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(var2, var3, false, 0));
      }

      private void addScrollActions(AccessibilityNodeInfo var1) {
         RecyclerView.Adapter var2 = ViewPager2.this.getAdapter();
         if (var2 != null) {
            int var3 = var2.getItemCount();
            if (var3 != 0 && ViewPager2.this.isUserInputEnabled()) {
               if (ViewPager2.this.mCurrentItem > 0) {
                  var1.addAction(8192);
               }

               if (ViewPager2.this.mCurrentItem < var3 - 1) {
                  var1.addAction(4096);
               }

               var1.setScrollable(true);
            }

         }
      }

      public boolean handlesGetAccessibilityClassName() {
         return true;
      }

      public boolean handlesPerformAccessibilityAction(int var1, Bundle var2) {
         boolean var3;
         if (var1 != 8192 && var1 != 4096) {
            var3 = false;
         } else {
            var3 = true;
         }

         return var3;
      }

      public void onAttachAdapter(RecyclerView.Adapter<?> var1) {
         this.updatePageAccessibilityActions();
         if (var1 != null) {
            var1.registerAdapterDataObserver(this.mAdapterDataObserver);
         }

      }

      public void onDetachAdapter(RecyclerView.Adapter<?> var1) {
         if (var1 != null) {
            var1.unregisterAdapterDataObserver(this.mAdapterDataObserver);
         }

      }

      public String onGetAccessibilityClassName() {
         if (this.handlesGetAccessibilityClassName()) {
            return "androidx.viewpager.widget.ViewPager";
         } else {
            throw new IllegalStateException();
         }
      }

      public void onInitialize(CompositeOnPageChangeCallback var1, RecyclerView var2) {
         ViewCompat.setImportantForAccessibility(var2, 2);
         this.mAdapterDataObserver = new ViewPager2.DataSetChangeObserver() {
            public void onChanged() {
               PageAwareAccessibilityProvider.this.updatePageAccessibilityActions();
            }
         };
         if (ViewCompat.getImportantForAccessibility(ViewPager2.this) == 0) {
            ViewCompat.setImportantForAccessibility(ViewPager2.this, 1);
         }

      }

      public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
         this.addCollectionInfo(var1);
         if (VERSION.SDK_INT >= 16) {
            this.addScrollActions(var1);
         }

      }

      public boolean onPerformAccessibilityAction(int var1, Bundle var2) {
         if (this.handlesPerformAccessibilityAction(var1, var2)) {
            if (var1 == 8192) {
               var1 = ViewPager2.this.getCurrentItem() - 1;
            } else {
               var1 = ViewPager2.this.getCurrentItem() + 1;
            }

            this.setCurrentItemFromAccessibilityCommand(var1);
            return true;
         } else {
            throw new IllegalStateException();
         }
      }

      public void onRestorePendingState() {
         this.updatePageAccessibilityActions();
      }

      public void onRvInitializeAccessibilityEvent(AccessibilityEvent var1) {
         var1.setSource(ViewPager2.this);
         var1.setClassName(this.onGetAccessibilityClassName());
      }

      public void onSetLayoutDirection() {
         this.updatePageAccessibilityActions();
      }

      public void onSetNewCurrentItem() {
         this.updatePageAccessibilityActions();
      }

      public void onSetOrientation() {
         this.updatePageAccessibilityActions();
      }

      public void onSetUserInputEnabled() {
         this.updatePageAccessibilityActions();
         if (VERSION.SDK_INT < 21) {
            ViewPager2.this.sendAccessibilityEvent(2048);
         }

      }

      void setCurrentItemFromAccessibilityCommand(int var1) {
         if (ViewPager2.this.isUserInputEnabled()) {
            ViewPager2.this.setCurrentItemInternal(var1, true);
         }

      }

      void updatePageAccessibilityActions() {
         ViewPager2 var1 = ViewPager2.this;
         int var2 = 16908360;
         ViewCompat.removeAccessibilityAction(var1, 16908360);
         ViewCompat.removeAccessibilityAction(var1, 16908361);
         ViewCompat.removeAccessibilityAction(var1, 16908358);
         ViewCompat.removeAccessibilityAction(var1, 16908359);
         if (ViewPager2.this.getAdapter() != null) {
            int var3 = ViewPager2.this.getAdapter().getItemCount();
            if (var3 != 0) {
               if (ViewPager2.this.isUserInputEnabled()) {
                  if (ViewPager2.this.getOrientation() == 0) {
                     boolean var4 = ViewPager2.this.isRtl();
                     int var5;
                     if (var4) {
                        var5 = 16908360;
                     } else {
                        var5 = 16908361;
                     }

                     if (var4) {
                        var2 = 16908361;
                     }

                     if (ViewPager2.this.mCurrentItem < var3 - 1) {
                        ViewCompat.replaceAccessibilityAction(var1, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var5, (CharSequence)null), (CharSequence)null, this.mActionPageForward);
                     }

                     if (ViewPager2.this.mCurrentItem > 0) {
                        ViewCompat.replaceAccessibilityAction(var1, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(var2, (CharSequence)null), (CharSequence)null, this.mActionPageBackward);
                     }
                  } else {
                     if (ViewPager2.this.mCurrentItem < var3 - 1) {
                        ViewCompat.replaceAccessibilityAction(var1, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908359, (CharSequence)null), (CharSequence)null, this.mActionPageForward);
                     }

                     if (ViewPager2.this.mCurrentItem > 0) {
                        ViewCompat.replaceAccessibilityAction(var1, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908358, (CharSequence)null), (CharSequence)null, this.mActionPageBackward);
                     }
                  }

               }
            }
         }
      }
   }

   public interface PageTransformer {
      void transformPage(View var1, float var2);
   }

   private class PagerSnapHelperImpl extends PagerSnapHelper {
      PagerSnapHelperImpl() {
      }

      public View findSnapView(RecyclerView.LayoutManager var1) {
         View var2;
         if (ViewPager2.this.isFakeDragging()) {
            var2 = null;
         } else {
            var2 = super.findSnapView(var1);
         }

         return var2;
      }
   }

   private class RecyclerViewImpl extends RecyclerView {
      RecyclerViewImpl(Context var2) {
         super(var2);
      }

      public CharSequence getAccessibilityClassName() {
         return ViewPager2.this.mAccessibilityProvider.handlesRvGetAccessibilityClassName() ? ViewPager2.this.mAccessibilityProvider.onRvGetAccessibilityClassName() : super.getAccessibilityClassName();
      }

      public void onInitializeAccessibilityEvent(AccessibilityEvent var1) {
         super.onInitializeAccessibilityEvent(var1);
         var1.setFromIndex(ViewPager2.this.mCurrentItem);
         var1.setToIndex(ViewPager2.this.mCurrentItem);
         ViewPager2.this.mAccessibilityProvider.onRvInitializeAccessibilityEvent(var1);
      }

      public boolean onInterceptTouchEvent(MotionEvent var1) {
         boolean var2;
         if (ViewPager2.this.isUserInputEnabled() && super.onInterceptTouchEvent(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean onTouchEvent(MotionEvent var1) {
         boolean var2;
         if (ViewPager2.this.isUserInputEnabled() && super.onTouchEvent(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   static class SavedState extends BaseSavedState {
      public static final Creator<ViewPager2.SavedState> CREATOR = new ClassLoaderCreator<ViewPager2.SavedState>() {
         public ViewPager2.SavedState createFromParcel(Parcel var1) {
            return this.createFromParcel(var1, (ClassLoader)null);
         }

         public ViewPager2.SavedState createFromParcel(Parcel var1, ClassLoader var2) {
            ViewPager2.SavedState var3;
            if (VERSION.SDK_INT >= 24) {
               var3 = new ViewPager2.SavedState(var1, var2);
            } else {
               var3 = new ViewPager2.SavedState(var1);
            }

            return var3;
         }

         public ViewPager2.SavedState[] newArray(int var1) {
            return new ViewPager2.SavedState[var1];
         }
      };
      Parcelable mAdapterState;
      int mCurrentItem;
      int mRecyclerViewId;

      SavedState(Parcel var1) {
         super(var1);
         this.readValues(var1, (ClassLoader)null);
      }

      SavedState(Parcel var1, ClassLoader var2) {
         super(var1, var2);
         this.readValues(var1, var2);
      }

      SavedState(Parcelable var1) {
         super(var1);
      }

      private void readValues(Parcel var1, ClassLoader var2) {
         this.mRecyclerViewId = var1.readInt();
         this.mCurrentItem = var1.readInt();
         this.mAdapterState = var1.readParcelable(var2);
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         var1.writeInt(this.mRecyclerViewId);
         var1.writeInt(this.mCurrentItem);
         var1.writeParcelable(this.mAdapterState, var2);
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ScrollState {
   }

   private static class SmoothScrollToPosition implements Runnable {
      private final int mPosition;
      private final RecyclerView mRecyclerView;

      SmoothScrollToPosition(int var1, RecyclerView var2) {
         this.mPosition = var1;
         this.mRecyclerView = var2;
      }

      public void run() {
         this.mRecyclerView.smoothScrollToPosition(this.mPosition);
      }
   }
}
