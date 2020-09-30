/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SoundEffectConstants
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.Scroller
 */
package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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
import androidx.viewpager.widget.PagerAdapter;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager
extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    static final int[] LAYOUT_ATTRS;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator;
    private static final ViewPositionComparator sPositionComparator;
    private int mActivePointerId = -1;
    PagerAdapter mAdapter;
    private List<OnAdapterChangeListener> mAdapterChangeListeners;
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
    private final Runnable mEndScrollRunnable = new Runnable(){

        @Override
        public void run() {
            ViewPager.this.setScrollState(0);
            ViewPager.this.populate();
        }
    };
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private EdgeEffect mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit = 1;
    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private int mPageTransformerLayerType;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private EdgeEffect mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final ItemInfo mTempItem = new ItemInfo();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    static {
        LAYOUT_ATTRS = new int[]{16842931};
        COMPARATOR = new Comparator<ItemInfo>(){

            @Override
            public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
                return itemInfo.position - itemInfo2.position;
            }
        };
        sInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * f * f * f + 1.0f;
            }
        };
        sPositionComparator = new ViewPositionComparator();
    }

    public ViewPager(Context context) {
        super(context);
        this.initViewPager();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initViewPager();
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int n, ItemInfo itemInfo2) {
        int n2;
        float f;
        int n3;
        float f2;
        float f3;
        int n4;
        int n5;
        block12 : {
            block15 : {
                block14 : {
                    block13 : {
                        n2 = this.mAdapter.getCount();
                        n4 = this.getClientWidth();
                        f = n4 > 0 ? (float)this.mPageMargin / (float)n4 : 0.0f;
                        if (itemInfo2 == null) break block12;
                        n4 = itemInfo2.position;
                        if (n4 >= itemInfo.position) break block13;
                        f2 = itemInfo2.offset + itemInfo2.widthFactor + f;
                        ++n4;
                        n5 = 0;
                        break block14;
                    }
                    if (n4 <= itemInfo.position) break block12;
                    n5 = this.mItems.size() - 1;
                    f2 = itemInfo2.offset;
                    --n4;
                    break block15;
                }
                while (n4 <= itemInfo.position && n5 < this.mItems.size()) {
                    itemInfo2 = this.mItems.get(n5);
                    do {
                        n3 = n4;
                        f3 = f2;
                        if (n4 <= itemInfo2.position) break;
                        n3 = n4;
                        f3 = f2;
                        if (n5 >= this.mItems.size() - 1) break;
                        itemInfo2 = this.mItems.get(++n5);
                    } while (true);
                    while (n3 < itemInfo2.position) {
                        f3 += this.mAdapter.getPageWidth(n3) + f;
                        ++n3;
                    }
                    itemInfo2.offset = f3;
                    f2 = f3 + (itemInfo2.widthFactor + f);
                    n4 = n3 + 1;
                }
                break block12;
            }
            while (n4 >= itemInfo.position && n5 >= 0) {
                itemInfo2 = this.mItems.get(n5);
                do {
                    n3 = n4;
                    f3 = f2;
                    if (n4 >= itemInfo2.position) break;
                    n3 = n4;
                    f3 = f2;
                    if (n5 <= 0) break;
                    itemInfo2 = this.mItems.get(--n5);
                } while (true);
                while (n3 > itemInfo2.position) {
                    f3 -= this.mAdapter.getPageWidth(n3) + f;
                    --n3;
                }
                itemInfo2.offset = f2 = f3 - (itemInfo2.widthFactor + f);
                n4 = n3 - 1;
            }
        }
        n3 = this.mItems.size();
        f3 = itemInfo.offset;
        n4 = itemInfo.position - 1;
        f2 = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        this.mFirstOffset = f2;
        n5 = itemInfo.position;
        f2 = n5 == --n2 ? itemInfo.offset + itemInfo.widthFactor - 1.0f : Float.MAX_VALUE;
        this.mLastOffset = f2;
        f2 = f3;
        for (n5 = n - 1; n5 >= 0; --n5, --n4) {
            itemInfo2 = this.mItems.get(n5);
            while (n4 > itemInfo2.position) {
                f2 -= this.mAdapter.getPageWidth(n4) + f;
                --n4;
            }
            itemInfo2.offset = f2 -= itemInfo2.widthFactor + f;
            if (itemInfo2.position != 0) continue;
            this.mFirstOffset = f2;
        }
        f2 = itemInfo.offset + itemInfo.widthFactor + f;
        n5 = itemInfo.position + 1;
        n4 = n + 1;
        n = n5;
        do {
            if (n4 >= n3) {
                this.mNeedCalculatePageOffsets = false;
                return;
            }
            itemInfo = this.mItems.get(n4);
            while (n < itemInfo.position) {
                f2 += this.mAdapter.getPageWidth(n) + f;
                ++n;
            }
            if (itemInfo.position == n2) {
                this.mLastOffset = itemInfo.widthFactor + f2 - 1.0f;
            }
            itemInfo.offset = f2;
            f2 += itemInfo.widthFactor + f;
            ++n4;
            ++n;
        } while (true);
    }

    private void completeScroll(boolean bl) {
        int n;
        boolean bl2 = this.mScrollState == 2;
        if (bl2) {
            this.setScrollingCacheEnabled(false);
            if (this.mScroller.isFinished() ^ true) {
                this.mScroller.abortAnimation();
                n = this.getScrollX();
                int n2 = this.getScrollY();
                int n3 = this.mScroller.getCurrX();
                int n4 = this.mScroller.getCurrY();
                if (n != n3 || n2 != n4) {
                    this.scrollTo(n3, n4);
                    if (n3 != n) {
                        this.pageScrolled(n3);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for (n = 0; n < this.mItems.size(); ++n) {
            ItemInfo itemInfo = this.mItems.get(n);
            if (!itemInfo.scrolling) continue;
            itemInfo.scrolling = false;
            bl2 = true;
        }
        if (!bl2) return;
        if (bl) {
            ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
            return;
        }
        this.mEndScrollRunnable.run();
    }

    private int determineTargetPage(int n, float f, int n2, int n3) {
        if (Math.abs(n3) > this.mFlingDistance && Math.abs(n2) > this.mMinimumVelocity) {
            if (n2 <= 0) {
                ++n;
            }
        } else {
            float f2 = n >= this.mCurItem ? 0.4f : 0.6f;
            n += (int)(f + f2);
        }
        n2 = n;
        if (this.mItems.size() <= 0) return n2;
        ItemInfo itemInfo = this.mItems.get(0);
        Object object = this.mItems;
        object = ((ArrayList)object).get(((ArrayList)object).size() - 1);
        return Math.max(itemInfo.position, Math.min(n, ((ItemInfo)object).position));
    }

    private void dispatchOnPageScrolled(int n, float f, int n2) {
        Object object = this.mOnPageChangeListener;
        if (object != null) {
            object.onPageScrolled(n, f, n2);
        }
        if ((object = this.mOnPageChangeListeners) != null) {
            int n3 = object.size();
            for (int i = 0; i < n3; ++i) {
                object = this.mOnPageChangeListeners.get(i);
                if (object == null) continue;
                object.onPageScrolled(n, f, n2);
            }
        }
        if ((object = this.mInternalPageChangeListener) == null) return;
        object.onPageScrolled(n, f, n2);
    }

    private void dispatchOnPageSelected(int n) {
        Object object = this.mOnPageChangeListener;
        if (object != null) {
            object.onPageSelected(n);
        }
        if ((object = this.mOnPageChangeListeners) != null) {
            int n2 = object.size();
            for (int i = 0; i < n2; ++i) {
                object = this.mOnPageChangeListeners.get(i);
                if (object == null) continue;
                object.onPageSelected(n);
            }
        }
        if ((object = this.mInternalPageChangeListener) == null) return;
        object.onPageSelected(n);
    }

    private void dispatchOnScrollStateChanged(int n) {
        Object object = this.mOnPageChangeListener;
        if (object != null) {
            object.onPageScrollStateChanged(n);
        }
        if ((object = this.mOnPageChangeListeners) != null) {
            int n2 = object.size();
            for (int i = 0; i < n2; ++i) {
                object = this.mOnPageChangeListeners.get(i);
                if (object == null) continue;
                object.onPageScrollStateChanged(n);
            }
        }
        if ((object = this.mInternalPageChangeListener) == null) return;
        object.onPageScrollStateChanged(n);
    }

    private void enableLayers(boolean bl) {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = bl ? this.mPageTransformerLayerType : 0;
            this.getChildAt(n2).setLayerType(n3, null);
            ++n2;
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) return;
        velocityTracker.recycle();
        this.mVelocityTracker = null;
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        }
        rect2.left = view.getLeft();
        rect2.right = view.getRight();
        rect2.top = view.getTop();
        rect2.bottom = view.getBottom();
        rect = view.getParent();
        while (rect instanceof ViewGroup) {
            if (rect == this) return rect2;
            rect = (ViewGroup)rect;
            rect2.left += rect.getLeft();
            rect2.right += rect.getRight();
            rect2.top += rect.getTop();
            rect2.bottom += rect.getBottom();
            rect = rect.getParent();
        }
        return rect2;
    }

    private int getClientWidth() {
        return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    private ItemInfo infoForCurrentScrollPosition() {
        int n = this.getClientWidth();
        float f = 0.0f;
        float f2 = n > 0 ? (float)this.getScrollX() / (float)n : 0.0f;
        float f3 = n > 0 ? (float)this.mPageMargin / (float)n : 0.0f;
        ItemInfo itemInfo = null;
        float f4 = 0.0f;
        int n2 = -1;
        n = 0;
        boolean bl = true;
        while (n < this.mItems.size()) {
            ItemInfo itemInfo2 = this.mItems.get(n);
            int n3 = n;
            ItemInfo itemInfo3 = itemInfo2;
            if (!bl) {
                int n4 = itemInfo2.position;
                n3 = n;
                itemInfo3 = itemInfo2;
                if (n4 != ++n2) {
                    itemInfo3 = this.mTempItem;
                    itemInfo3.offset = f + f4 + f3;
                    itemInfo3.position = n2;
                    itemInfo3.widthFactor = this.mAdapter.getPageWidth(itemInfo3.position);
                    n3 = n - 1;
                }
            }
            f = itemInfo3.offset;
            f4 = itemInfo3.widthFactor;
            if (!bl) {
                if (!(f2 >= f)) return itemInfo;
            }
            if (f2 < f4 + f + f3) return itemInfo3;
            if (n3 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            n2 = itemInfo3.position;
            f4 = itemInfo3.widthFactor;
            n = n3 + 1;
            bl = false;
            itemInfo = itemInfo3;
        }
        return itemInfo;
    }

    private static boolean isDecorView(View view) {
        if (view.getClass().getAnnotation(DecorView.class) == null) return false;
        return true;
    }

    private boolean isGutterDrag(float f, float f2) {
        if (f < (float)this.mGutterSize) {
            if (f2 > 0.0f) return true;
        }
        if (!(f > (float)(this.getWidth() - this.mGutterSize))) return false;
        if (!(f2 < 0.0f)) return false;
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) != this.mActivePointerId) return;
        n = n == 0 ? 1 : 0;
        this.mLastMotionX = motionEvent.getX(n);
        this.mActivePointerId = motionEvent.getPointerId(n);
        motionEvent = this.mVelocityTracker;
        if (motionEvent == null) return;
        motionEvent.clear();
    }

    private boolean pageScrolled(int n) {
        if (this.mItems.size() != 0) {
            ItemInfo itemInfo = this.infoForCurrentScrollPosition();
            int n2 = this.getClientWidth();
            int n3 = this.mPageMargin;
            float f = n3;
            float f2 = n2;
            f /= f2;
            int n4 = itemInfo.position;
            f2 = ((float)n / f2 - itemInfo.offset) / (itemInfo.widthFactor + f);
            n = (int)((float)(n2 + n3) * f2);
            this.mCalledSuper = false;
            this.onPageScrolled(n4, f2, n);
            if (!this.mCalledSuper) throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            return true;
        }
        if (this.mFirstLayout) {
            return false;
        }
        this.mCalledSuper = false;
        this.onPageScrolled(0, 0.0f, 0);
        if (!this.mCalledSuper) throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        return false;
    }

    private boolean performDrag(float f) {
        int n;
        boolean bl;
        float f2 = this.mLastMotionX;
        this.mLastMotionX = f;
        float f3 = (float)this.getScrollX() + (f2 - f);
        float f4 = this.getClientWidth();
        f = this.mFirstOffset * f4;
        f2 = this.mLastOffset * f4;
        Object object = this.mItems;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        object = ((ArrayList)object).get(0);
        Object object2 = this.mItems;
        object2 = ((ArrayList)object2).get(((ArrayList)object2).size() - 1);
        if (((ItemInfo)object).position != 0) {
            f = ((ItemInfo)object).offset * f4;
            n = 0;
        } else {
            n = 1;
        }
        if (((ItemInfo)object2).position != this.mAdapter.getCount() - 1) {
            f2 = ((ItemInfo)object2).offset * f4;
            bl = false;
        } else {
            bl = true;
        }
        if (f3 < f) {
            if (n != 0) {
                this.mLeftEdge.onPull(Math.abs(f - f3) / f4);
                bl4 = true;
            }
        } else {
            bl4 = bl3;
            f = f3;
            if (f3 > f2) {
                bl4 = bl2;
                if (bl) {
                    this.mRightEdge.onPull(Math.abs(f3 - f2) / f4);
                    bl4 = true;
                }
                f = f2;
            }
        }
        f2 = this.mLastMotionX;
        n = (int)f;
        this.mLastMotionX = f2 + (f - (float)n);
        this.scrollTo(n, this.getScrollY());
        this.pageScrolled(n);
        return bl4;
    }

    private void recomputeScrollPosition(int n, int n2, int n3, int n4) {
        if (n2 > 0 && !this.mItems.isEmpty()) {
            if (!this.mScroller.isFinished()) {
                this.mScroller.setFinalX(this.getCurrentItem() * this.getClientWidth());
                return;
            }
            int n5 = this.getPaddingLeft();
            int n6 = this.getPaddingRight();
            int n7 = this.getPaddingLeft();
            int n8 = this.getPaddingRight();
            this.scrollTo((int)((float)this.getScrollX() / (float)(n2 - n7 - n8 + n4) * (float)(n - n5 - n6 + n3)), this.getScrollY());
            return;
        }
        ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
        float f = itemInfo != null ? Math.min(itemInfo.offset, this.mLastOffset) : 0.0f;
        n = (int)(f * (float)(n - this.getPaddingLeft() - this.getPaddingRight()));
        if (n == this.getScrollX()) return;
        this.completeScroll(false);
        this.scrollTo(n, this.getScrollY());
    }

    private void removeNonDecorViews() {
        int n = 0;
        while (n < this.getChildCount()) {
            int n2 = n;
            if (!((LayoutParams)this.getChildAt((int)n).getLayoutParams()).isDecor) {
                this.removeViewAt(n);
                n2 = n - 1;
            }
            n = n2 + 1;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean bl) {
        ViewParent viewParent = this.getParent();
        if (viewParent == null) return;
        viewParent.requestDisallowInterceptTouchEvent(bl);
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        this.endDrag();
        this.mLeftEdge.onRelease();
        this.mRightEdge.onRelease();
        if (this.mLeftEdge.isFinished()) return true;
        if (this.mRightEdge.isFinished()) return true;
        return false;
    }

    private void scrollToItem(int n, boolean bl, int n2, boolean bl2) {
        ItemInfo itemInfo = this.infoForPosition(n);
        int n3 = itemInfo != null ? (int)((float)this.getClientWidth() * Math.max(this.mFirstOffset, Math.min(itemInfo.offset, this.mLastOffset))) : 0;
        if (bl) {
            this.smoothScrollTo(n3, 0, n2);
            if (!bl2) return;
            this.dispatchOnPageSelected(n);
            return;
        }
        if (bl2) {
            this.dispatchOnPageSelected(n);
        }
        this.completeScroll(false);
        this.scrollTo(n3, 0);
        this.pageScrolled(n3);
    }

    private void setScrollingCacheEnabled(boolean bl) {
        if (this.mScrollingCacheEnabled == bl) return;
        this.mScrollingCacheEnabled = bl;
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder == 0) return;
        View view = this.mDrawingOrderedChildren;
        if (view == null) {
            this.mDrawingOrderedChildren = new ArrayList();
        } else {
            view.clear();
        }
        int n = this.getChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
                return;
            }
            view = this.getChildAt(n2);
            this.mDrawingOrderedChildren.add(view);
            ++n2;
        } while (true);
    }

    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        int n3 = arrayList.size();
        int n4 = this.getDescendantFocusability();
        if (n4 != 393216) {
            for (int i = 0; i < this.getChildCount(); ++i) {
                ItemInfo itemInfo;
                View view = this.getChildAt(i);
                if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
                view.addFocusables(arrayList, n, n2);
            }
        }
        if (n4 == 262144) {
            if (n3 != arrayList.size()) return;
        }
        if (!this.isFocusable()) {
            return;
        }
        if ((n2 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode()) {
            return;
        }
        if (arrayList == null) return;
        arrayList.add((View)this);
    }

    ItemInfo addNewItem(int n, int n2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = n;
        itemInfo.object = this.mAdapter.instantiateItem(this, n);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(n);
        if (n2 >= 0 && n2 < this.mItems.size()) {
            this.mItems.add(n2, itemInfo);
            return itemInfo;
        }
        this.mItems.add(itemInfo);
        return itemInfo;
    }

    public void addOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = new ArrayList<OnAdapterChangeListener>();
        }
        this.mAdapterChangeListeners.add(onAdapterChangeListener);
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList<OnPageChangeListener>();
        }
        this.mOnPageChangeListeners.add(onPageChangeListener);
    }

    public void addTouchables(ArrayList<View> arrayList) {
        int n = 0;
        while (n < this.getChildCount()) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem) {
                view.addTouchables(arrayList);
            }
            ++n;
        }
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (!this.checkLayoutParams(layoutParams)) {
            layoutParams2 = this.generateLayoutParams(layoutParams);
        }
        layoutParams = (LayoutParams)layoutParams2;
        layoutParams.isDecor |= ViewPager.isDecorView(view);
        if (!this.mInLayout) {
            super.addView(view, n, layoutParams2);
            return;
        }
        if (layoutParams != null) {
            if (layoutParams.isDecor) throw new IllegalStateException("Cannot add pager decor view during layout");
        }
        layoutParams.needsMeasure = true;
        this.addViewInLayout(view, n, layoutParams2);
    }

    /*
     * Unable to fully structure code
     */
    public boolean arrowScroll(int var1_1) {
        block13 : {
            block14 : {
                block16 : {
                    block15 : {
                        var2_2 = this.findFocus();
                        var3_3 = false;
                        if (var2_2 == this) break block15;
                        var4_4 = var2_2;
                        if (var2_2 == null) break block13;
                        var4_4 = var2_2.getParent();
                        break block16;
                    }
lbl9: // 2 sources:
                    do {
                        var4_4 = null;
                        break block13;
                        break;
                    } while (true);
                }
                while (var4_4 instanceof ViewGroup) {
                    if (var4_4 == this) {
                        var5_5 = 1;
                        break block14;
                    }
                    var4_4 = var4_4.getParent();
                }
                var5_5 = 0;
            }
            var4_4 = var2_2;
            if (var5_5 == 0) {
                var6_6 = new StringBuilder();
                var6_6.append(var2_2.getClass().getSimpleName());
                var4_4 = var2_2.getParent();
                while (var4_4 instanceof ViewGroup) {
                    var6_6.append(" => ");
                    var6_6.append(var4_4.getClass().getSimpleName());
                    var4_4 = var4_4.getParent();
                }
                var4_4 = new StringBuilder();
                var4_4.append("arrowScroll tried to find focus based on non-child current focused view ");
                var4_4.append(var6_6.toString());
                Log.e((String)"ViewPager", (String)var4_4.toString());
                ** continue;
            }
        }
        var2_2 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)var4_4, var1_1);
        if (var2_2 != null && var2_2 != var4_4) {
            if (var1_1 == 17) {
                var5_5 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var2_2).left;
                var7_7 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var4_4).left;
                var3_3 = var4_4 != null && var5_5 >= var7_7 ? this.pageLeft() : var2_2.requestFocus();
            } else if (var1_1 == 66) {
                var5_5 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var2_2).left;
                var7_8 = this.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var4_4).left;
                var3_3 = var4_4 != null && var5_5 <= var7_8 ? this.pageRight() : var2_2.requestFocus();
            }
        } else if (var1_1 != 17 && var1_1 != 1) {
            if (var1_1 == 66 || var1_1 == 2) {
                var3_3 = this.pageRight();
            }
        } else {
            var3_3 = this.pageLeft();
        }
        if (var3_3 == false) return var3_3;
        this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection((int)var1_1));
        return var3_3;
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        this.setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        long l = SystemClock.uptimeMillis();
        velocityTracker = MotionEvent.obtain((long)l, (long)l, (int)0, (float)0.0f, (float)0.0f, (int)0);
        this.mVelocityTracker.addMovement((MotionEvent)velocityTracker);
        velocityTracker.recycle();
        this.mFakeDragBeginTime = l;
        return true;
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3) {
        boolean bl2 = view instanceof ViewGroup;
        boolean bl3 = true;
        if (bl2) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                int n6;
                int n7 = n2 + n4;
                View view2 = viewGroup.getChildAt(i);
                if (n7 < view2.getLeft() || n7 >= view2.getRight() || (n6 = n3 + n5) < view2.getTop() || n6 >= view2.getBottom() || !this.canScroll(view2, true, n, n7 - view2.getLeft(), n6 - view2.getTop())) continue;
                return true;
            }
        }
        if (!bl) return false;
        if (!view.canScrollHorizontally(-n)) return false;
        return bl3;
    }

    public boolean canScrollHorizontally(int n) {
        PagerAdapter pagerAdapter = this.mAdapter;
        boolean bl = false;
        boolean bl2 = false;
        if (pagerAdapter == null) {
            return false;
        }
        int n2 = this.getClientWidth();
        int n3 = this.getScrollX();
        if (n < 0) {
            if (n3 <= (int)((float)n2 * this.mFirstOffset)) return bl2;
            return true;
        }
        bl2 = bl;
        if (n <= 0) return bl2;
        bl2 = bl;
        if (n3 >= (int)((float)n2 * this.mLastOffset)) return bl2;
        return true;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams)) return false;
        if (!super.checkLayoutParams(layoutParams)) return false;
        return true;
    }

    public void clearOnPageChangeListeners() {
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list == null) return;
        list.clear();
    }

    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                this.scrollTo(n3, n4);
                if (!this.pageScrolled(n3)) {
                    this.mScroller.abortAnimation();
                    this.scrollTo(0, n4);
                }
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.completeScroll(true);
    }

    void dataSetChanged() {
        int n;
        Object object;
        this.mExpectedAdapterCount = n = this.mAdapter.getCount();
        int n2 = this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < n ? 1 : 0;
        int n3 = this.mCurItem;
        int n4 = 0;
        int n5 = 0;
        int n6 = n2;
        while (n4 < this.mItems.size()) {
            int n7;
            int n8;
            block13 : {
                block15 : {
                    int n9;
                    block14 : {
                        block12 : {
                            object = this.mItems.get(n4);
                            n9 = this.mAdapter.getItemPosition(object.object);
                            if (n9 != -1) break block12;
                            n2 = n3;
                            n8 = n4;
                            n7 = n5;
                            break block13;
                        }
                        if (n9 != -2) break block14;
                        this.mItems.remove(n4);
                        n6 = n4 - 1;
                        n2 = n5;
                        if (n5 == 0) {
                            this.mAdapter.startUpdate(this);
                            n2 = 1;
                        }
                        this.mAdapter.destroyItem(this, object.position, object.object);
                        n4 = n6;
                        n5 = n2;
                        if (this.mCurItem == object.position) {
                            n3 = Math.max(0, Math.min(this.mCurItem, n - 1));
                            n5 = n2;
                            n4 = n6;
                        }
                        break block15;
                    }
                    n2 = n3;
                    n8 = n4;
                    n7 = n5;
                    if (object.position == n9) break block13;
                    if (object.position == this.mCurItem) {
                        n3 = n9;
                    }
                    object.position = n9;
                }
                n6 = 1;
                n2 = n3;
                n8 = n4;
                n7 = n5;
            }
            n4 = n8 + 1;
            n3 = n2;
            n5 = n7;
        }
        if (n5 != 0) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (n6 == 0) return;
        n4 = this.getChildCount();
        n5 = 0;
        do {
            if (n5 >= n4) {
                this.setCurrentItemInternal(n3, false, true);
                this.requestLayout();
                return;
            }
            object = (LayoutParams)this.getChildAt(n5).getLayoutParams();
            if (!((LayoutParams)object).isDecor) {
                ((LayoutParams)object).widthFactor = 0.0f;
            }
            ++n5;
        } while (true);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent)) return true;
        if (this.executeKeyEvent(keyEvent)) return true;
        return false;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n2);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem && view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((f - 0.5f) * 0.47123894f);
    }

    public void draw(Canvas canvas) {
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int n = this.getOverScrollMode();
        int n2 = 0;
        int n3 = 0;
        if (n != 0 && (n != 1 || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getCount() <= 1)) {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        } else {
            if (!this.mLeftEdge.isFinished()) {
                n2 = canvas.save();
                n = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                n3 = this.getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float)(-n + this.getPaddingTop()), this.mFirstOffset * (float)n3);
                this.mLeftEdge.setSize(n, n3);
                n3 = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(n2);
            }
            n2 = n3;
            if (!this.mRightEdge.isFinished()) {
                n = canvas.save();
                n2 = this.getWidth();
                int n4 = this.getHeight();
                int n5 = this.getPaddingTop();
                int n6 = this.getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float)(-this.getPaddingTop()), -(this.mLastOffset + 1.0f) * (float)n2);
                this.mRightEdge.setSize(n4 - n5 - n6, n2);
                n2 = n3 | this.mRightEdge.draw(canvas);
                canvas.restoreToCount(n);
            }
        }
        if (n2 == 0) return;
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mMarginDrawable;
        if (drawable2 == null) return;
        if (!drawable2.isStateful()) return;
        drawable2.setState(this.getDrawableState());
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        if (this.mAdapter != null) {
            Object object = this.mVelocityTracker;
            object.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
            int n = (int)object.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int n2 = this.getClientWidth();
            int n3 = this.getScrollX();
            object = this.infoForCurrentScrollPosition();
            this.setCurrentItemInternal(this.determineTargetPage(object.position, ((float)n3 / (float)n2 - object.offset) / object.widthFactor, n, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, n);
        }
        this.endDrag();
        this.mFakeDragging = false;
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) return false;
        int n = keyEvent.getKeyCode();
        if (n != 21) {
            if (n != 22) {
                if (n != 61) return false;
                if (keyEvent.hasNoModifiers()) {
                    return this.arrowScroll(2);
                }
                if (!keyEvent.hasModifiers(1)) return false;
                return this.arrowScroll(1);
            }
            if (!keyEvent.hasModifiers(2)) return this.arrowScroll(66);
            return this.pageRight();
        }
        if (!keyEvent.hasModifiers(2)) return this.arrowScroll(17);
        return this.pageLeft();
    }

    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        if (this.mAdapter == null) {
            return;
        }
        this.mLastMotionX += f;
        float f2 = (float)this.getScrollX() - f;
        float f3 = this.getClientWidth();
        f = this.mFirstOffset * f3;
        float f4 = this.mLastOffset * f3;
        ItemInfo itemInfo = this.mItems.get(0);
        Object object = this.mItems;
        object = ((ArrayList)object).get(((ArrayList)object).size() - 1);
        if (itemInfo.position != 0) {
            f = itemInfo.offset * f3;
        }
        if (((ItemInfo)object).position != this.mAdapter.getCount() - 1) {
            f4 = ((ItemInfo)object).offset * f3;
        }
        if (!(f2 < f)) {
            f = f2;
            if (f2 > f4) {
                f = f4;
            }
        }
        f4 = this.mLastMotionX;
        int n = (int)f;
        this.mLastMotionX = f4 + (f - (float)n);
        this.scrollTo(n, this.getScrollY());
        this.pageScrolled(n);
        long l = SystemClock.uptimeMillis();
        itemInfo = MotionEvent.obtain((long)this.mFakeDragBeginTime, (long)l, (int)2, (float)this.mLastMotionX, (float)0.0f, (int)0);
        this.mVelocityTracker.addMovement((MotionEvent)itemInfo);
        itemInfo.recycle();
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return this.generateDefaultLayoutParams();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    protected int getChildDrawingOrder(int n, int n2) {
        int n3 = n2;
        if (this.mDrawingOrder != 2) return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n3).getLayoutParams()).childIndex;
        n3 = n - 1 - n2;
        return ((LayoutParams)this.mDrawingOrderedChildren.get((int)n3).getLayoutParams()).childIndex;
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

    ItemInfo infoForAnyChild(View view) {
        ViewParent viewParent;
        while ((viewParent = view.getParent()) != this) {
            if (viewParent == null) return null;
            if (!(viewParent instanceof View)) {
                return null;
            }
            view = (View)viewParent;
        }
        return this.infoForChild(view);
    }

    ItemInfo infoForChild(View view) {
        int n = 0;
        while (n < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(n);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
            ++n;
        }
        return null;
    }

    ItemInfo infoForPosition(int n) {
        int n2 = 0;
        while (n2 < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(n2);
            if (itemInfo.position == n) {
                return itemInfo;
            }
            ++n2;
        }
        return null;
    }

    void initViewPager() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        Context context = this.getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int)(400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int)(25.0f * f);
        this.mCloseEnough = (int)(2.0f * f);
        this.mDefaultGutterSize = (int)(f * 16.0f);
        ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){
            private final Rect mTempRect = new Rect();

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View object, WindowInsetsCompat windowInsetsCompat) {
                if (((WindowInsetsCompat)(object = ViewCompat.onApplyWindowInsets((View)object, windowInsetsCompat))).isConsumed()) {
                    return object;
                }
                Rect rect = this.mTempRect;
                rect.left = ((WindowInsetsCompat)object).getSystemWindowInsetLeft();
                rect.top = ((WindowInsetsCompat)object).getSystemWindowInsetTop();
                rect.right = ((WindowInsetsCompat)object).getSystemWindowInsetRight();
                rect.bottom = ((WindowInsetsCompat)object).getSystemWindowInsetBottom();
                int n = 0;
                int n2 = ViewPager.this.getChildCount();
                while (n < n2) {
                    windowInsetsCompat = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(n), (WindowInsetsCompat)object);
                    rect.left = Math.min(windowInsetsCompat.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(windowInsetsCompat.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(windowInsetsCompat.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(windowInsetsCompat.getSystemWindowInsetBottom(), rect.bottom);
                    ++n;
                }
                return ((WindowInsetsCompat)object).replaceSystemWindowInsets(rect.left, rect.top, rect.right, rect.bottom);
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
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin <= 0) return;
        if (this.mMarginDrawable == null) return;
        if (this.mItems.size() <= 0) return;
        if (this.mAdapter == null) return;
        int n = this.getScrollX();
        int n2 = this.getWidth();
        float f = this.mPageMargin;
        float f2 = n2;
        float f3 = f / f2;
        Object object = this.mItems;
        int n3 = 0;
        object = ((ArrayList)object).get(0);
        f = ((ItemInfo)object).offset;
        int n4 = this.mItems.size();
        int n5 = ((ItemInfo)object).position;
        int n6 = this.mItems.get((int)(n4 - 1)).position;
        while (n5 < n6) {
            float f4;
            while (n5 > ((ItemInfo)object).position && n3 < n4) {
                object = this.mItems;
                object = ((ArrayList)object).get(++n3);
            }
            if (n5 == ((ItemInfo)object).position) {
                f4 = (((ItemInfo)object).offset + ((ItemInfo)object).widthFactor) * f2;
                f = ((ItemInfo)object).offset + ((ItemInfo)object).widthFactor + f3;
            } else {
                float f5 = this.mAdapter.getPageWidth(n5);
                f4 = f + (f5 + f3);
                f5 = (f + f5) * f2;
                f = f4;
                f4 = f5;
            }
            if ((float)this.mPageMargin + f4 > (float)n) {
                this.mMarginDrawable.setBounds(Math.round(f4), this.mTopPageBounds, Math.round((float)this.mPageMargin + f4), this.mBottomPageBounds);
                this.mMarginDrawable.draw(canvas);
            }
            if (f4 > (float)(n + n2)) {
                return;
            }
            ++n5;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction() & 255;
        if (n != 3 && n != 1) {
            if (n != 0) {
                if (this.mIsBeingDragged) {
                    return true;
                }
                if (this.mIsUnableToDrag) {
                    return false;
                }
            }
            if (n != 0) {
                if (n != 2) {
                    if (n == 6) {
                        this.onSecondaryPointerUp(motionEvent);
                    }
                } else {
                    n = this.mActivePointerId;
                    if (n != -1) {
                        n = motionEvent.findPointerIndex(n);
                        float f = motionEvent.getX(n);
                        float f2 = f - this.mLastMotionX;
                        float f3 = Math.abs(f2);
                        float f4 = motionEvent.getY(n);
                        float f5 = Math.abs(f4 - this.mInitialMotionY);
                        n = (int)(f2 FCMPL 0.0f);
                        if (n != 0 && !this.isGutterDrag(this.mLastMotionX, f2) && this.canScroll((View)this, false, (int)f2, (int)f, (int)f4)) {
                            this.mLastMotionX = f;
                            this.mLastMotionY = f4;
                            this.mIsUnableToDrag = true;
                            return false;
                        }
                        if (f3 > (float)this.mTouchSlop && f3 * 0.5f > f5) {
                            this.mIsBeingDragged = true;
                            this.requestParentDisallowInterceptTouchEvent(true);
                            this.setScrollState(1);
                            f5 = this.mInitialMotionX;
                            f2 = this.mTouchSlop;
                            f5 = n > 0 ? (f5 += f2) : (f5 -= f2);
                            this.mLastMotionX = f5;
                            this.mLastMotionY = f4;
                            this.setScrollingCacheEnabled(true);
                        } else if (f5 > (float)this.mTouchSlop) {
                            this.mIsUnableToDrag = true;
                        }
                        if (this.mIsBeingDragged && this.performDrag(f)) {
                            ViewCompat.postInvalidateOnAnimation((View)this);
                        }
                    }
                }
            } else {
                float f;
                this.mInitialMotionX = f = motionEvent.getX();
                this.mLastMotionX = f;
                this.mInitialMotionY = f = motionEvent.getY();
                this.mLastMotionY = f;
                this.mActivePointerId = motionEvent.getPointerId(0);
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
            this.mVelocityTracker.addMovement(motionEvent);
            return this.mIsBeingDragged;
        }
        this.resetTouch();
        return false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5;
        LayoutParams layoutParams;
        View view;
        int n6;
        int n7 = this.getChildCount();
        int n8 = n3 - n;
        int n9 = n4 - n2;
        n2 = this.getPaddingLeft();
        n = this.getPaddingTop();
        n4 = this.getPaddingRight();
        n3 = this.getPaddingBottom();
        int n10 = this.getScrollX();
        int n11 = 0;
        for (n5 = 0; n5 < n7; ++n5) {
            int n12;
            int n13;
            int n14;
            int n15;
            block10 : {
                block14 : {
                    block15 : {
                        block11 : {
                            block12 : {
                                block13 : {
                                    view = this.getChildAt(n5);
                                    n15 = n2;
                                    n12 = n;
                                    n13 = n4;
                                    n14 = n3;
                                    n6 = n11;
                                    if (view.getVisibility() == 8) break block10;
                                    layoutParams = (LayoutParams)view.getLayoutParams();
                                    n15 = n2;
                                    n12 = n;
                                    n13 = n4;
                                    n14 = n3;
                                    n6 = n11;
                                    if (!layoutParams.isDecor) break block10;
                                    n6 = layoutParams.gravity & 7;
                                    n13 = layoutParams.gravity & 112;
                                    if (n6 == 1) break block11;
                                    if (n6 == 3) break block12;
                                    if (n6 == 5) break block13;
                                    n6 = n2;
                                    n14 = n2;
                                    n2 = n6;
                                    break block14;
                                }
                                n6 = n8 - n4 - view.getMeasuredWidth();
                                n4 += view.getMeasuredWidth();
                                break block15;
                            }
                            n6 = view.getMeasuredWidth() + n2;
                            n14 = n2;
                            n2 = n6;
                            break block14;
                        }
                        n6 = Math.max((n8 - view.getMeasuredWidth()) / 2, n2);
                    }
                    n14 = n6;
                }
                if (n13 != 16) {
                    if (n13 != 48) {
                        if (n13 != 80) {
                            n13 = n;
                            n6 = n;
                            n = n13;
                        } else {
                            n6 = n9 - n3 - view.getMeasuredHeight();
                            n3 += view.getMeasuredHeight();
                        }
                    } else {
                        n13 = view.getMeasuredHeight() + n;
                        n6 = n;
                        n = n13;
                    }
                } else {
                    n6 = Math.max((n9 - view.getMeasuredHeight()) / 2, n);
                }
                view.layout(n14 += n10, n6, view.getMeasuredWidth() + n14, n6 + view.getMeasuredHeight());
                n6 = n11 + 1;
                n14 = n3;
                n13 = n4;
                n12 = n;
                n15 = n2;
            }
            n2 = n15;
            n = n12;
            n4 = n13;
            n3 = n14;
            n11 = n6;
        }
        for (n6 = 0; n6 < n7; ++n6) {
            ItemInfo itemInfo;
            view = this.getChildAt(n6);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isDecor || (itemInfo = this.infoForChild(view)) == null) continue;
            float f = n8 - n2 - n4;
            n5 = (int)(itemInfo.offset * f) + n2;
            if (layoutParams.needsMeasure) {
                layoutParams.needsMeasure = false;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)(f * layoutParams.widthFactor)), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n9 - n - n3), (int)1073741824));
            }
            view.layout(n5, n, view.getMeasuredWidth() + n5, view.getMeasuredHeight() + n);
        }
        this.mTopPageBounds = n;
        this.mBottomPageBounds = n9 - n3;
        this.mDecorChildCount = n11;
        if (this.mFirstLayout) {
            this.scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    /*
     * Unable to fully structure code
     */
    protected void onMeasure(int var1_1, int var2_2) {
        this.setMeasuredDimension(ViewPager.getDefaultSize((int)0, (int)var1_1), ViewPager.getDefaultSize((int)0, (int)var2_2));
        var1_1 = this.getMeasuredWidth();
        this.mGutterSize = Math.min(var1_1 / 10, this.mDefaultGutterSize);
        var1_1 = var1_1 - this.getPaddingLeft() - this.getPaddingRight();
        var2_2 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
        var3_3 = this.getChildCount();
        var4_4 = 0;
        do {
            block13 : {
                block14 : {
                    var5_5 = 1;
                    var6_6 = 1073741824;
                    if (var4_4 >= var3_3) break;
                    var7_7 = this.getChildAt(var4_4);
                    var8_10 = var1_1;
                    var9_11 = var2_2;
                    if (var7_7.getVisibility() == 8) break block13;
                    var10_12 = (LayoutParams)var7_7.getLayoutParams();
                    var8_10 = var1_1;
                    var9_11 = var2_2;
                    if (var10_12 == null) break block13;
                    var8_10 = var1_1;
                    var9_11 = var2_2;
                    if (!var10_12.isDecor) break block13;
                    var8_10 = var10_12.gravity & 7;
                    var9_11 = var10_12.gravity & 112;
                    var11_13 = var9_11 == 48 || var9_11 == 80;
                    var12_14 = var5_5;
                    if (var8_10 != 3) {
                        var12_14 = var8_10 == 5 ? var5_5 : 0;
                    }
                    var8_10 = Integer.MIN_VALUE;
                    if (!var11_13) break block14;
                    var9_11 = 1073741824;
                    ** GOTO lbl-1000
                }
                var9_11 = var8_10;
                if (var12_14 != 0) {
                    var5_5 = 1073741824;
                    var9_11 = var8_10;
                    var8_10 = var5_5;
                } else lbl-1000: // 2 sources:
                {
                    var8_10 = Integer.MIN_VALUE;
                }
                if (var10_12.width != -2) {
                    var9_11 = var10_12.width != -1 ? var10_12.width : var1_1;
                    var13_15 = 1073741824;
                    var5_5 = var9_11;
                    var9_11 = var13_15;
                } else {
                    var5_5 = var1_1;
                }
                if (var10_12.height != -2) {
                    var8_10 = var10_12.height != -1 ? var10_12.height : var2_2;
                } else {
                    var13_15 = var2_2;
                    var6_6 = var8_10;
                    var8_10 = var13_15;
                }
                var7_7.measure(View.MeasureSpec.makeMeasureSpec((int)var5_5, (int)var9_11), View.MeasureSpec.makeMeasureSpec((int)var8_10, (int)var6_6));
                if (var11_13) {
                    var9_11 = var2_2 - var7_7.getMeasuredHeight();
                    var8_10 = var1_1;
                } else {
                    var8_10 = var1_1;
                    var9_11 = var2_2;
                    if (var12_14 != 0) {
                        var8_10 = var1_1 - var7_7.getMeasuredWidth();
                        var9_11 = var2_2;
                    }
                }
            }
            ++var4_4;
            var1_1 = var8_10;
            var2_2 = var9_11;
        } while (true);
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)var1_1, (int)1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)var2_2, (int)1073741824);
        this.mInLayout = true;
        this.populate();
        var2_2 = 0;
        this.mInLayout = false;
        var8_10 = this.getChildCount();
        while (var2_2 < var8_10) {
            var10_12 = this.getChildAt(var2_2);
            if (!(var10_12.getVisibility() == 8 || (var7_9 = (LayoutParams)var10_12.getLayoutParams()) != null && var7_9.isDecor)) {
                var10_12.measure(View.MeasureSpec.makeMeasureSpec((int)((int)((float)var1_1 * var7_9.widthFactor)), (int)1073741824), this.mChildHeightMeasureSpec);
            }
            ++var2_2;
        }
    }

    protected void onPageScrolled(int n, float f, int n2) {
        Object object;
        int n3 = this.mDecorChildCount;
        int n4 = 0;
        if (n3 > 0) {
            int n5 = this.getScrollX();
            n3 = this.getPaddingLeft();
            int n6 = this.getPaddingRight();
            int n7 = this.getWidth();
            int n8 = this.getChildCount();
            for (int i = 0; i < n8; ++i) {
                View view = this.getChildAt(i);
                object = (LayoutParams)view.getLayoutParams();
                if (!object.isDecor) continue;
                int n9 = object.gravity & 7;
                if (n9 != 1) {
                    int n10;
                    if (n9 != 3) {
                        if (n9 != 5) {
                            n10 = n3;
                            n9 = n3;
                            n3 = n10;
                        } else {
                            n9 = n7 - n6 - view.getMeasuredWidth();
                            n6 += view.getMeasuredWidth();
                        }
                    } else {
                        n10 = view.getWidth() + n3;
                        n9 = n3;
                        n3 = n10;
                    }
                } else {
                    n9 = Math.max((n7 - view.getMeasuredWidth()) / 2, n3);
                }
                n9 = n9 + n5 - view.getLeft();
                if (n9 == 0) continue;
                view.offsetLeftAndRight(n9);
            }
        }
        this.dispatchOnPageScrolled(n, f, n2);
        if (this.mPageTransformer != null) {
            n3 = this.getScrollX();
            n2 = this.getChildCount();
            for (n = n4; n < n2; ++n) {
                object = this.getChildAt(n);
                if (((LayoutParams)object.getLayoutParams()).isDecor) continue;
                f = (float)(object.getLeft() - n3) / (float)this.getClientWidth();
                this.mPageTransformer.transformPage((View)object, f);
            }
        }
        this.mCalledSuper = true;
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        int n3 = this.getChildCount();
        int n4 = -1;
        if ((n & 2) != 0) {
            n4 = n3;
            n3 = 0;
            n2 = 1;
        } else {
            --n3;
            n2 = -1;
        }
        while (n3 != n4) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n3);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem && view.requestFocus(n, rect)) {
                return true;
            }
            n3 += n2;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.restoreState(parcelable.adapterState, parcelable.loader);
            this.setCurrentItemInternal(parcelable.position, false, true);
            return;
        }
        this.mRestoredCurItem = parcelable.position;
        this.mRestoredAdapterState = parcelable.adapterState;
        this.mRestoredClassLoader = parcelable.loader;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null) return savedState;
        savedState.adapterState = pagerAdapter.saveState();
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n == n3) return;
        n2 = this.mPageMargin;
        this.recomputeScrollPosition(n, n3, n2, n2);
    }

    /*
     * Unable to fully structure code
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block19 : {
            block16 : {
                block17 : {
                    block18 : {
                        if (this.mFakeDragging) {
                            return true;
                        }
                        var2_2 = var1_1.getAction();
                        var3_3 = false;
                        if (var2_2 == 0 && var1_1.getEdgeFlags() != 0) {
                            return false;
                        }
                        var4_4 = this.mAdapter;
                        if (var4_4 == null) return false;
                        if (var4_4.getCount() == 0) {
                            return false;
                        }
                        if (this.mVelocityTracker == null) {
                            this.mVelocityTracker = VelocityTracker.obtain();
                        }
                        this.mVelocityTracker.addMovement(var1_1);
                        var2_2 = var1_1.getAction() & 255;
                        if (var2_2 == 0) break block16;
                        if (var2_2 == 1) break block17;
                        if (var2_2 == 2) break block18;
                        if (var2_2 != 3) {
                            if (var2_2 != 5) {
                                if (var2_2 == 6) {
                                    this.onSecondaryPointerUp(var1_1);
                                    this.mLastMotionX = var1_1.getX(var1_1.findPointerIndex(this.mActivePointerId));
                                }
                            } else {
                                var2_2 = var1_1.getActionIndex();
                                this.mLastMotionX = var1_1.getX(var2_2);
                                this.mActivePointerId = var1_1.getPointerId(var2_2);
                            }
                        } else if (this.mIsBeingDragged) {
                            this.scrollToItem(this.mCurItem, true, 0, false);
                            var3_3 = this.resetTouch();
                        }
                        break block19;
                    }
                    if (this.mIsBeingDragged) ** GOTO lbl54
                    var2_2 = var1_1.findPointerIndex(this.mActivePointerId);
                    if (var2_2 == -1) {
                        var3_3 = this.resetTouch();
                    } else {
                        var5_5 = var1_1.getX(var2_2);
                        var6_8 = Math.abs(var5_5 - this.mLastMotionX);
                        var7_9 = var1_1.getY(var2_2);
                        var8_11 = Math.abs(var7_9 - this.mLastMotionY);
                        if (var6_8 > (float)this.mTouchSlop && var6_8 > var8_11) {
                            this.mIsBeingDragged = true;
                            this.requestParentDisallowInterceptTouchEvent(true);
                            var8_11 = this.mInitialMotionX;
                            var5_5 = var5_5 - var8_11 > 0.0f ? var8_11 + (float)this.mTouchSlop : var8_11 - (float)this.mTouchSlop;
                            this.mLastMotionX = var5_5;
                            this.mLastMotionY = var7_9;
                            this.setScrollState(1);
                            this.setScrollingCacheEnabled(true);
                            var4_4 = this.getParent();
                            if (var4_4 != null) {
                                var4_4.requestDisallowInterceptTouchEvent(true);
                            }
                        }
lbl54: // 6 sources:
                        if (this.mIsBeingDragged) {
                            var3_3 = false | this.performDrag(var1_1.getX(var1_1.findPointerIndex(this.mActivePointerId)));
                        }
                    }
                    break block19;
                }
                if (this.mIsBeingDragged) {
                    var4_4 = this.mVelocityTracker;
                    var4_4.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                    var2_2 = (int)var4_4.getXVelocity(this.mActivePointerId);
                    this.mPopulatePending = true;
                    var9_12 = this.getClientWidth();
                    var10_13 = this.getScrollX();
                    var4_4 = this.infoForCurrentScrollPosition();
                    var7_10 = this.mPageMargin;
                    var5_6 = var9_12;
                    this.setCurrentItemInternal(this.determineTargetPage(var4_4.position, ((float)var10_13 / var5_6 - var4_4.offset) / (var4_4.widthFactor + (var7_10 /= var5_6)), var2_2, (int)(var1_1.getX(var1_1.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, var2_2);
                    var3_3 = this.resetTouch();
                }
                break block19;
            }
            this.mScroller.abortAnimation();
            this.mPopulatePending = false;
            this.populate();
            this.mInitialMotionX = var5_7 = var1_1.getX();
            this.mLastMotionX = var5_7;
            this.mInitialMotionY = var5_7 = var1_1.getY();
            this.mLastMotionY = var5_7;
            this.mActivePointerId = var1_1.getPointerId(0);
        }
        if (var3_3 == false) return true;
        ViewCompat.postInvalidateOnAnimation((View)this);
        return true;
    }

    boolean pageLeft() {
        int n = this.mCurItem;
        if (n <= 0) return false;
        this.setCurrentItem(n - 1, true);
        return true;
    }

    boolean pageRight() {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null) return false;
        if (this.mCurItem >= pagerAdapter.getCount() - 1) return false;
        this.setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    void populate() {
        this.populate(this.mCurItem);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    void populate(int var1_1) {
        block29 : {
            block34 : {
                block28 : {
                    var2_2 = this.mCurItem;
                    if (var2_2 != var1_1) {
                        var3_3 = this.infoForPosition(var2_2);
                        this.mCurItem = var1_1;
                    } else {
                        var3_3 = null;
                    }
                    if (this.mAdapter == null) {
                        this.sortChildDrawingOrder();
                        return;
                    }
                    if (this.mPopulatePending) {
                        this.sortChildDrawingOrder();
                        return;
                    }
                    if (this.getWindowToken() == null) {
                        return;
                    }
                    this.mAdapter.startUpdate(this);
                    var1_1 = this.mOffscreenPageLimit;
                    var4_4 = Math.max(0, this.mCurItem - var1_1);
                    var5_5 = this.mAdapter.getCount();
                    var6_6 = Math.min(var5_5 - 1, this.mCurItem + var1_1);
                    if (var5_5 == this.mExpectedAdapterCount) {
                    } else {
                        try {
                            var7_44 = this.getResources().getResourceName(this.getId());
                        }
                        catch (Resources.NotFoundException var7_45) {
                            var7_46 = Integer.toHexString(this.getId());
                        }
                        var3_3 = new StringBuilder();
                        var3_3.append("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ");
                        var3_3.append(this.mExpectedAdapterCount);
                        var3_3.append(", found: ");
                        var3_3.append(var5_5);
                        var3_3.append(" Pager id: ");
                        var3_3.append((String)var7_47);
                        var3_3.append(" Pager class: ");
                        var3_3.append(this.getClass());
                        var3_3.append(" Problematic adapter: ");
                        var3_3.append(this.mAdapter.getClass());
                        throw new IllegalStateException(var3_3.toString());
                    }
                    for (var1_1 = 0; var1_1 < this.mItems.size(); ++var1_1) {
                        var7_7 = this.mItems.get(var1_1);
                        if (var7_7.position < this.mCurItem) continue;
                        if (var7_7.position != this.mCurItem) break;
                        break block28;
                    }
                    var7_8 = null;
                }
                var8_48 = var7_9;
                if (var7_9 == null) {
                    var8_48 = var7_9;
                    if (var5_5 > 0) {
                        var8_48 = this.addNewItem(this.mCurItem, var1_1);
                    }
                }
                if (var8_48 == null) break block29;
                var2_2 = var1_1 - 1;
                if (var2_2 >= 0) {
                    var7_10 = this.mItems.get(var2_2);
                } else {
                    var7_11 = null;
                }
                var9_49 = this.getClientWidth();
                var10_50 = var9_49 <= 0 ? 0.0f : 2.0f - var8_48.widthFactor + (float)this.getPaddingLeft() / (float)var9_49;
                var12_52 = 0.0f;
                for (var11_51 = this.mCurItem - 1; var11_51 >= 0; --var11_51) {
                    block31 : {
                        block32 : {
                            block33 : {
                                block30 : {
                                    if (!(var12_52 >= var10_50) || var11_51 >= var4_4) break block30;
                                    if (var7_13 == null) break;
                                    var13_53 = var1_1;
                                    var14_54 = var2_2;
                                    var15_56 = var7_13;
                                    var16_63 = var12_52;
                                    if (var11_51 != var7_13.position) break block31;
                                    var13_53 = var1_1--;
                                    var14_54 = var2_2;
                                    var15_57 = var7_13;
                                    var16_63 = var12_52;
                                    if (var7_13.scrolling) break block31;
                                    this.mItems.remove(var2_2);
                                    this.mAdapter.destroyItem(this, var11_51, var7_13.object);
                                    var14_54 = var1_1;
                                    var13_53 = --var2_2;
                                    var16_63 = var12_52;
                                    if (var2_2 < 0) ** GOTO lbl-1000
                                    var7_14 = this.mItems.get(var2_2);
                                    var16_63 = var12_52;
                                    break block32;
                                }
                                if (var7_13 == null || var11_51 != var7_13.position) break block33;
                                var14_54 = var1_1;
                                var13_53 = --var2_2;
                                var16_63 = var12_52 += var7_13.widthFactor;
                                if (var2_2 < 0) ** GOTO lbl-1000
                                var7_15 = this.mItems.get(var2_2);
                                var16_63 = var12_52;
                                break block32;
                            }
                            var14_54 = ++var1_1;
                            var13_53 = var2_2;
                            var16_63 = var12_52 += this.addNewItem((int)var11_51, (int)(var2_2 + 1)).widthFactor;
                            if (var2_2 >= 0) {
                                var7_16 = this.mItems.get(var2_2);
                                var16_63 = var12_52;
                            } else lbl-1000: // 3 sources:
                            {
                                var7_17 = null;
                                var2_2 = var13_53;
                                var1_1 = var14_54;
                            }
                        }
                        var15_58 = var7_18;
                        var14_54 = var2_2;
                        var13_53 = var1_1;
                    }
                    var1_1 = var13_53;
                    var2_2 = var14_54;
                    var7_20 = var15_55;
                    var12_52 = var16_63;
                }
                var12_52 = var8_48.widthFactor;
                var14_54 = var1_1 + 1;
                if (!(var12_52 < 2.0f)) break block34;
                if (var14_54 < this.mItems.size()) {
                    var7_21 = this.mItems.get(var14_54);
                } else {
                    var7_22 = null;
                }
                var10_50 = var9_49 <= 0 ? 0.0f : (float)this.getPaddingRight() / (float)var9_49 + 2.0f;
                var2_2 = this.mCurItem;
                var15_59 = var7_23;
                while ((var13_53 = var2_2 + 1) < var5_5) {
                    block36 : {
                        block37 : {
                            block35 : {
                                if (!(var12_52 >= var10_50) || var13_53 <= var6_6) break block35;
                                if (var15_60 == null) break;
                                var16_63 = var12_52;
                                var2_2 = var14_54;
                                var7_25 = var15_60;
                                if (var13_53 != var15_60.position) break block36;
                                var16_63 = var12_52;
                                var2_2 = var14_54;
                                var7_26 = var15_60;
                                if (var15_60.scrolling) break block36;
                                this.mItems.remove(var14_54);
                                this.mAdapter.destroyItem(this, var13_53, var15_60.object);
                                var16_63 = var12_52;
                                var2_2 = var14_54;
                                if (var14_54 >= this.mItems.size()) ** GOTO lbl-1000
                                var7_27 = this.mItems.get(var14_54);
                                var16_63 = var12_52;
                                var2_2 = var14_54;
                                break block36;
                            }
                            if (var15_60 == null || var13_53 != var15_60.position) break block37;
                            var16_63 = var12_52 += var15_60.widthFactor;
                            var2_2 = ++var14_54;
                            if (var14_54 >= this.mItems.size()) ** GOTO lbl-1000
                            var7_30 = this.mItems.get(var14_54);
                            var16_63 = var12_52;
                            var2_2 = var14_54;
                            break block36;
                        }
                        var7_31 = this.addNewItem(var13_53, var14_54);
                        var16_63 = var12_52 += var7_31.widthFactor;
                        var2_2 = ++var14_54;
                        if (var14_54 < this.mItems.size()) {
                            var7_32 = this.mItems.get(var14_54);
                            var2_2 = var14_54;
                            var16_63 = var12_52;
                        } else lbl-1000: // 3 sources:
                        {
                            var7_29 = null;
                        }
                    }
                    var12_52 = var16_63;
                    var14_54 = var2_2;
                    var15_61 = var7_33;
                    var2_2 = var13_53;
                }
            }
            this.calculatePageOffsets(var8_48, var1_1, (ItemInfo)var3_3);
            this.mAdapter.setPrimaryItem(this, this.mCurItem, var8_48.object);
        }
        this.mAdapter.finishUpdate(this);
        var2_2 = this.getChildCount();
        for (var1_1 = 0; var1_1 < var2_2; ++var1_1) {
            var3_3 = this.getChildAt(var1_1);
            var7_37 = (LayoutParams)var3_3.getLayoutParams();
            var7_37.childIndex = var1_1;
            if (var7_37.isDecor || var7_37.widthFactor != 0.0f || (var3_3 = this.infoForChild((View)var3_3)) == null) continue;
            var7_37.widthFactor = var3_3.widthFactor;
            var7_37.position = var3_3.position;
        }
        this.sortChildDrawingOrder();
        if (this.hasFocus() == false) return;
        var7_38 = this.findFocus();
        if (var7_38 != null) {
            var7_39 = this.infoForAnyChild(var7_38);
        } else {
            var7_40 = null;
        }
        if (var7_41 != null) {
            if (var7_41.position == this.mCurItem) return;
        }
        var1_1 = 0;
        while (var1_1 < this.getChildCount()) {
            var7_43 = this.getChildAt(var1_1);
            var3_3 = this.infoForChild(var7_43);
            if (var3_3 != null && var3_3.position == this.mCurItem && var7_43.requestFocus(2)) {
                return;
            }
            ++var1_1;
        }
    }

    public void removeOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
        if (list == null) return;
        list.remove(onAdapterChangeListener);
    }

    public void removeOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list == null) return;
        list.remove(onPageChangeListener);
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        int n;
        Object object = this.mAdapter;
        int n2 = 0;
        if (object != null) {
            ((PagerAdapter)object).setViewPagerObserver(null);
            this.mAdapter.startUpdate(this);
            for (n = 0; n < this.mItems.size(); ++n) {
                object = this.mItems.get(n);
                this.mAdapter.destroyItem(this, ((ItemInfo)object).position, ((ItemInfo)object).object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            this.removeNonDecorViews();
            this.mCurItem = 0;
            this.scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter2 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (pagerAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean bl = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!bl) {
                this.populate();
            } else {
                this.requestLayout();
            }
        }
        if ((object = this.mAdapterChangeListeners) == null) return;
        if (object.isEmpty()) return;
        int n3 = this.mAdapterChangeListeners.size();
        n = n2;
        while (n < n3) {
            this.mAdapterChangeListeners.get(n).onAdapterChanged(this, pagerAdapter2, pagerAdapter);
            ++n;
        }
    }

    public void setCurrentItem(int n) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, this.mFirstLayout ^ true, false);
    }

    public void setCurrentItem(int n, boolean bl) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, bl, false);
    }

    void setCurrentItemInternal(int n, boolean bl, boolean bl2) {
        this.setCurrentItemInternal(n, bl, bl2, 0);
    }

    void setCurrentItemInternal(int n, boolean bl, boolean bl2, int n2) {
        PagerAdapter pagerAdapter = this.mAdapter;
        boolean bl3 = false;
        if (pagerAdapter != null && pagerAdapter.getCount() > 0) {
            int n3;
            if (!bl2 && this.mCurItem == n && this.mItems.size() != 0) {
                this.setScrollingCacheEnabled(false);
                return;
            }
            if (n < 0) {
                n3 = 0;
            } else {
                n3 = n;
                if (n >= this.mAdapter.getCount()) {
                    n3 = this.mAdapter.getCount() - 1;
                }
            }
            int n4 = this.mOffscreenPageLimit;
            n = this.mCurItem;
            if (n3 > n + n4 || n3 < n - n4) {
                for (n = 0; n < this.mItems.size(); ++n) {
                    this.mItems.get((int)n).scrolling = true;
                }
            }
            bl2 = bl3;
            if (this.mCurItem != n3) {
                bl2 = true;
            }
            if (!this.mFirstLayout) {
                this.populate(n3);
                this.scrollToItem(n3, bl, n2, bl2);
                return;
            }
            this.mCurItem = n3;
            if (bl2) {
                this.dispatchOnPageSelected(n3);
            }
            this.requestLayout();
            return;
        }
        this.setScrollingCacheEnabled(false);
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int n) {
        int n2 = n;
        if (n < 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested offscreen page limit ");
            stringBuilder.append(n);
            stringBuilder.append(" too small; defaulting to ");
            stringBuilder.append(1);
            Log.w((String)TAG, (String)stringBuilder.toString());
            n2 = 1;
        }
        if (n2 == this.mOffscreenPageLimit) return;
        this.mOffscreenPageLimit = n2;
        this.populate();
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargin(int n) {
        int n2 = this.mPageMargin;
        this.mPageMargin = n;
        int n3 = this.getWidth();
        this.recomputeScrollPosition(n3, n3, n, n2);
        this.requestLayout();
    }

    public void setPageMarginDrawable(int n) {
        this.setPageMarginDrawable(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setPageMarginDrawable(Drawable drawable2) {
        this.mMarginDrawable = drawable2;
        if (drawable2 != null) {
            this.refreshDrawableState();
        }
        boolean bl = drawable2 == null;
        this.setWillNotDraw(bl);
        this.invalidate();
    }

    public void setPageTransformer(boolean bl, PageTransformer pageTransformer) {
        this.setPageTransformer(bl, pageTransformer, 2);
    }

    public void setPageTransformer(boolean bl, PageTransformer pageTransformer, int n) {
        int n2 = 1;
        boolean bl2 = pageTransformer != null;
        boolean bl3 = this.mPageTransformer != null;
        boolean bl4 = bl2 != bl3;
        this.mPageTransformer = pageTransformer;
        this.setChildrenDrawingOrderEnabled(bl2);
        if (bl2) {
            if (bl) {
                n2 = 2;
            }
            this.mDrawingOrder = n2;
            this.mPageTransformerLayerType = n;
        } else {
            this.mDrawingOrder = 0;
        }
        if (!bl4) return;
        this.populate();
    }

    void setScrollState(int n) {
        if (this.mScrollState == n) {
            return;
        }
        this.mScrollState = n;
        if (this.mPageTransformer != null) {
            boolean bl = n != 0;
            this.enableLayers(bl);
        }
        this.dispatchOnScrollStateChanged(n);
    }

    void smoothScrollTo(int n, int n2) {
        this.smoothScrollTo(n, n2, 0);
    }

    void smoothScrollTo(int n, int n2, int n3) {
        if (this.getChildCount() == 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        Scroller scroller = this.mScroller;
        int n4 = scroller != null && !scroller.isFinished() ? 1 : 0;
        if (n4 != 0) {
            n4 = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            this.setScrollingCacheEnabled(false);
        } else {
            n4 = this.getScrollX();
        }
        int n5 = this.getScrollY();
        int n6 = n - n4;
        if (n6 == 0 && (n2 -= n5) == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
            return;
        }
        this.setScrollingCacheEnabled(true);
        this.setScrollState(2);
        n = this.getClientWidth();
        int n7 = n / 2;
        float f = Math.abs(n6);
        float f2 = n;
        float f3 = Math.min(1.0f, f * 1.0f / f2);
        f = n7;
        f3 = this.distanceInfluenceForSnapDuration(f3);
        n = Math.abs(n3);
        if (n > 0) {
            n = Math.round(Math.abs((f + f3 * f) / (float)n) * 1000.0f) * 4;
        } else {
            f = this.mAdapter.getPageWidth(this.mCurItem);
            n = (int)(((float)Math.abs(n6) / (f2 * f + (float)this.mPageMargin) + 1.0f) * 100.0f);
        }
        n = Math.min(n, 600);
        this.mIsScrollStarted = false;
        this.mScroller.startScroll(n4, n5, n6, n2, n);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        if (super.verifyDrawable(drawable2)) return true;
        if (drawable2 == this.mMarginDrawable) return true;
        return false;
    }

    @Inherited
    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface DecorView {
    }

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = context.getInteger(0, 48);
            context.recycle();
        }
    }

    class MyAccessibilityDelegate
    extends AccessibilityDelegateCompat {
        MyAccessibilityDelegate() {
        }

        private boolean canScroll() {
            PagerAdapter pagerAdapter = ViewPager.this.mAdapter;
            boolean bl = true;
            if (pagerAdapter == null) return false;
            if (ViewPager.this.mAdapter.getCount() <= 1) return false;
            return bl;
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ViewPager.class.getName());
            accessibilityEvent.setScrollable(this.canScroll());
            if (accessibilityEvent.getEventType() != 4096) return;
            if (ViewPager.this.mAdapter == null) return;
            accessibilityEvent.setItemCount(ViewPager.this.mAdapter.getCount());
            accessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
            accessibilityEvent.setToIndex(ViewPager.this.mCurItem);
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(this.canScroll());
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (!ViewPager.this.canScrollHorizontally(-1)) return;
            accessibilityNodeInfoCompat.addAction(8192);
        }

        @Override
        public boolean performAccessibilityAction(View object, int n, Bundle bundle) {
            if (super.performAccessibilityAction((View)object, n, bundle)) {
                return true;
            }
            if (n == 4096) {
                if (!ViewPager.this.canScrollHorizontally(1)) return false;
                object = ViewPager.this;
                ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)object).mCurItem + 1);
                return true;
            }
            if (n != 8192) {
                return false;
            }
            if (!ViewPager.this.canScrollHorizontally(-1)) return false;
            object = ViewPager.this;
            ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)object).mCurItem - 1);
            return true;
        }
    }

    public static interface OnAdapterChangeListener {
        public void onAdapterChanged(ViewPager var1, PagerAdapter var2, PagerAdapter var3);
    }

    public static interface OnPageChangeListener {
        public void onPageScrollStateChanged(int var1);

        public void onPageScrolled(int var1, float var2, int var3);

        public void onPageSelected(int var1);
    }

    public static interface PageTransformer {
        public void transformPage(View var1, float var2);
    }

    private class PagerObserver
    extends DataSetObserver {
        PagerObserver() {
        }

        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
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
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = this.getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader2);
            this.loader = classLoader2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FragmentPager.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" position=");
            stringBuilder.append(this.position);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, n);
        }

    }

    public static class SimpleOnPageChangeListener
    implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int n) {
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
        }

        @Override
        public void onPageSelected(int n) {
        }
    }

    static class ViewPositionComparator
    implements Comparator<View> {
        ViewPositionComparator() {
        }

        @Override
        public int compare(View object, View object2) {
            object = (LayoutParams)object.getLayoutParams();
            object2 = (LayoutParams)object2.getLayoutParams();
            if (object.isDecor == object2.isDecor) return object.position - object2.position;
            if (!object.isDecor) return -1;
            return 1;
        }
    }

}

