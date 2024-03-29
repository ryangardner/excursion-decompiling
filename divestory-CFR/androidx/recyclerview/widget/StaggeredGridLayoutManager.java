/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LayoutState;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ScrollbarHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager
extends RecyclerView.LayoutManager
implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    static final boolean DEBUG = false;
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "StaggeredGridLManager";
    public static final int VERTICAL = 1;
    private final AnchorInfo mAnchorInfo = new AnchorInfo();
    private final Runnable mCheckForGapsRunnable = new Runnable(){

        @Override
        public void run() {
            StaggeredGridLayoutManager.this.checkForGaps();
        }
    };
    private int mFullSizeSpec;
    private int mGapStrategy = 2;
    private boolean mLaidOutInvalidFullSpan = false;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;
    private final LayoutState mLayoutState;
    LazySpanLookup mLazySpanLookup = new LazySpanLookup();
    private int mOrientation;
    private SavedState mPendingSavedState;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    private int[] mPrefetchDistances;
    OrientationHelper mPrimaryOrientation;
    private BitSet mRemainingSpans;
    boolean mReverseLayout = false;
    OrientationHelper mSecondaryOrientation;
    boolean mShouldReverseLayout = false;
    private int mSizePerSpan;
    private boolean mSmoothScrollbarEnabled = true;
    private int mSpanCount = -1;
    Span[] mSpans;
    private final Rect mTmpRect = new Rect();

    public StaggeredGridLayoutManager(int n, int n2) {
        this.mOrientation = n2;
        this.setSpanCount(n);
        this.mLayoutState = new LayoutState();
        this.createOrientationHelpers();
    }

    public StaggeredGridLayoutManager(Context object, AttributeSet attributeSet, int n, int n2) {
        object = StaggeredGridLayoutManager.getProperties(object, attributeSet, n, n2);
        this.setOrientation(object.orientation);
        this.setSpanCount(object.spanCount);
        this.setReverseLayout(object.reverseLayout);
        this.mLayoutState = new LayoutState();
        this.createOrientationHelpers();
    }

    private void appendViewToAllSpans(View view) {
        int n = this.mSpanCount - 1;
        while (n >= 0) {
            this.mSpans[n].appendToSpan(view);
            --n;
        }
    }

    private void applyPendingSavedState(AnchorInfo anchorInfo) {
        if (this.mPendingSavedState.mSpanOffsetsSize > 0) {
            if (this.mPendingSavedState.mSpanOffsetsSize != this.mSpanCount) {
                this.mPendingSavedState.invalidateSpanInfo();
                SavedState savedState = this.mPendingSavedState;
                savedState.mAnchorPosition = savedState.mVisibleAnchorPosition;
            } else {
                for (int i = 0; i < this.mSpanCount; ++i) {
                    int n;
                    this.mSpans[i].clear();
                    int n2 = n = this.mPendingSavedState.mSpanOffsets[i];
                    if (n != Integer.MIN_VALUE) {
                        n2 = this.mPendingSavedState.mAnchorLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                        n2 = n + n2;
                    }
                    this.mSpans[i].setLine(n2);
                }
            }
        }
        this.mLastLayoutRTL = this.mPendingSavedState.mLastLayoutRTL;
        this.setReverseLayout(this.mPendingSavedState.mReverseLayout);
        this.resolveShouldLayoutReverse();
        if (this.mPendingSavedState.mAnchorPosition != -1) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
            anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
        } else {
            anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
        }
        if (this.mPendingSavedState.mSpanLookupSize <= 1) return;
        this.mLazySpanLookup.mData = this.mPendingSavedState.mSpanLookup;
        this.mLazySpanLookup.mFullSpanItems = this.mPendingSavedState.mFullSpanItems;
    }

    private void attachViewToSpans(View view, LayoutParams layoutParams, LayoutState layoutState) {
        if (layoutState.mLayoutDirection == 1) {
            if (layoutParams.mFullSpan) {
                this.appendViewToAllSpans(view);
                return;
            }
            layoutParams.mSpan.appendToSpan(view);
            return;
        }
        if (layoutParams.mFullSpan) {
            this.prependViewToAllSpans(view);
            return;
        }
        layoutParams.mSpan.prependToSpan(view);
    }

    private int calculateScrollDirectionForPosition(int n) {
        int n2 = this.getChildCount();
        int n3 = -1;
        if (n2 == 0) {
            if (!this.mShouldReverseLayout) return n3;
            return 1;
        }
        boolean bl = n < this.getFirstChildPosition();
        if (bl == this.mShouldReverseLayout) return 1;
        return n3;
    }

    private boolean checkSpanForGap(Span span) {
        if (this.mShouldReverseLayout) {
            if (span.getEndLine() >= this.mPrimaryOrientation.getEndAfterPadding()) return false;
            return span.getLayoutParams((View)span.mViews.get((int)(span.mViews.size() - 1))).mFullSpan ^ true;
        }
        if (span.getStartLine() <= this.mPrimaryOrientation.getStartAfterPadding()) return false;
        return span.getLayoutParams((View)span.mViews.get((int)0)).mFullSpan ^ true;
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (this.getChildCount() != 0) return ScrollbarHelper.computeScrollExtent(state, this.mPrimaryOrientation, this.findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), this.findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
        return 0;
    }

    private int computeScrollOffset(RecyclerView.State state) {
        if (this.getChildCount() != 0) return ScrollbarHelper.computeScrollOffset(state, this.mPrimaryOrientation, this.findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), this.findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
        return 0;
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (this.getChildCount() != 0) return ScrollbarHelper.computeScrollRange(state, this.mPrimaryOrientation, this.findFirstVisibleItemClosestToStart(this.mSmoothScrollbarEnabled ^ true), this.findFirstVisibleItemClosestToEnd(this.mSmoothScrollbarEnabled ^ true), this, this.mSmoothScrollbarEnabled);
        return 0;
    }

    private int convertFocusDirectionToLayoutDirection(int n) {
        int n2 = -1;
        int n3 = 1;
        int n4 = 1;
        if (n != 1) {
            if (n != 2) {
                if (n != 17) {
                    if (n != 33) {
                        if (n != 66) {
                            if (n != 130) {
                                return Integer.MIN_VALUE;
                            }
                            if (this.mOrientation != 1) return Integer.MIN_VALUE;
                            return n4;
                        }
                        if (this.mOrientation != 0) return Integer.MIN_VALUE;
                        return n3;
                    }
                    if (this.mOrientation != 1) return Integer.MIN_VALUE;
                    return n2;
                }
                if (this.mOrientation != 0) return Integer.MIN_VALUE;
                return n2;
            }
            if (this.mOrientation == 1) {
                return 1;
            }
            if (!this.isLayoutRTL()) return 1;
            return -1;
        }
        if (this.mOrientation == 1) {
            return -1;
        }
        if (!this.isLayoutRTL()) return -1;
        return 1;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromEnd(int n) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        int n2 = 0;
        while (n2 < this.mSpanCount) {
            fullSpanItem.mGapPerSpan[n2] = n - this.mSpans[n2].getEndLine(n);
            ++n2;
        }
        return fullSpanItem;
    }

    private LazySpanLookup.FullSpanItem createFullSpanItemFromStart(int n) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.mGapPerSpan = new int[this.mSpanCount];
        int n2 = 0;
        while (n2 < this.mSpanCount) {
            fullSpanItem.mGapPerSpan[n2] = this.mSpans[n2].getStartLine(n) - n;
            ++n2;
        }
        return fullSpanItem;
    }

    private void createOrientationHelpers() {
        this.mPrimaryOrientation = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        this.mSecondaryOrientation = OrientationHelper.createOrientationHelper(this, 1 - this.mOrientation);
    }

    private int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state) {
        this.mRemainingSpans.set(0, this.mSpanCount, true);
        int n = this.mLayoutState.mInfinite ? (layoutState.mLayoutDirection == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE) : (layoutState.mLayoutDirection == 1 ? layoutState.mEndLine + layoutState.mAvailable : layoutState.mStartLine - layoutState.mAvailable);
        this.updateAllRemainingSpans(layoutState.mLayoutDirection, n);
        int n2 = this.mShouldReverseLayout ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
        int n3 = 0;
        while (layoutState.hasMore(state) && (this.mLayoutState.mInfinite || !this.mRemainingSpans.isEmpty())) {
            int n4;
            int n5;
            LazySpanLookup.FullSpanItem fullSpanItem;
            Span span;
            View view = layoutState.next(recycler);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n6 = layoutParams.getViewLayoutPosition();
            n3 = this.mLazySpanLookup.getSpan(n6);
            int n7 = n3 == -1 ? 1 : 0;
            if (n7 != 0) {
                span = layoutParams.mFullSpan ? this.mSpans[0] : this.getNextSpan(layoutState);
                this.mLazySpanLookup.setSpan(n6, span);
            } else {
                span = this.mSpans[n3];
            }
            layoutParams.mSpan = span;
            if (layoutState.mLayoutDirection == 1) {
                this.addView(view);
            } else {
                this.addView(view, 0);
            }
            this.measureChildWithDecorationsAndMargin(view, layoutParams, false);
            if (layoutState.mLayoutDirection == 1) {
                n3 = layoutParams.mFullSpan ? this.getMaxEnd(n2) : span.getEndLine(n2);
                n4 = this.mPrimaryOrientation.getDecoratedMeasurement(view);
                if (n7 != 0 && layoutParams.mFullSpan) {
                    fullSpanItem = this.createFullSpanItemFromEnd(n3);
                    fullSpanItem.mGapDir = -1;
                    fullSpanItem.mPosition = n6;
                    this.mLazySpanLookup.addFullSpanItem(fullSpanItem);
                }
                n4 += n3;
                n5 = n3;
            } else {
                n3 = layoutParams.mFullSpan ? this.getMinStart(n2) : span.getStartLine(n2);
                n5 = n3 - this.mPrimaryOrientation.getDecoratedMeasurement(view);
                if (n7 != 0 && layoutParams.mFullSpan) {
                    fullSpanItem = this.createFullSpanItemFromStart(n3);
                    fullSpanItem.mGapDir = 1;
                    fullSpanItem.mPosition = n6;
                    this.mLazySpanLookup.addFullSpanItem(fullSpanItem);
                }
                n4 = n3;
            }
            if (layoutParams.mFullSpan && layoutState.mItemDirection == -1) {
                if (n7 != 0) {
                    this.mLaidOutInvalidFullSpan = true;
                } else {
                    boolean bl = layoutState.mLayoutDirection == 1 ? this.areAllEndsEqual() : this.areAllStartsEqual();
                    if (bl ^ true) {
                        fullSpanItem = this.mLazySpanLookup.getFullSpanItem(n6);
                        if (fullSpanItem != null) {
                            fullSpanItem.mHasUnwantedGapAfter = true;
                        }
                        this.mLaidOutInvalidFullSpan = true;
                    }
                }
            }
            this.attachViewToSpans(view, layoutParams, layoutState);
            if (this.isLayoutRTL() && this.mOrientation == 1) {
                n3 = layoutParams.mFullSpan ? this.mSecondaryOrientation.getEndAfterPadding() : this.mSecondaryOrientation.getEndAfterPadding() - (this.mSpanCount - 1 - span.mIndex) * this.mSizePerSpan;
                n6 = this.mSecondaryOrientation.getDecoratedMeasurement(view);
                n7 = n3;
                n3 -= n6;
                n6 = n7;
            } else {
                n3 = layoutParams.mFullSpan ? this.mSecondaryOrientation.getStartAfterPadding() : span.mIndex * this.mSizePerSpan + this.mSecondaryOrientation.getStartAfterPadding();
                n6 = this.mSecondaryOrientation.getDecoratedMeasurement(view);
                n7 = n3;
                n6 += n3;
                n3 = n7;
            }
            if (this.mOrientation == 1) {
                this.layoutDecoratedWithMargins(view, n3, n5, n6, n4);
            } else {
                this.layoutDecoratedWithMargins(view, n5, n3, n4, n6);
            }
            if (layoutParams.mFullSpan) {
                this.updateAllRemainingSpans(this.mLayoutState.mLayoutDirection, n);
            } else {
                this.updateRemainingSpans(span, this.mLayoutState.mLayoutDirection, n);
            }
            this.recycle(recycler, this.mLayoutState);
            if (this.mLayoutState.mStopInFocusable && view.hasFocusable()) {
                if (layoutParams.mFullSpan) {
                    this.mRemainingSpans.clear();
                } else {
                    this.mRemainingSpans.set(span.mIndex, false);
                }
            }
            n3 = 1;
        }
        if (n3 == 0) {
            this.recycle(recycler, this.mLayoutState);
        }
        if (this.mLayoutState.mLayoutDirection == -1) {
            n = this.getMinStart(this.mPrimaryOrientation.getStartAfterPadding());
            n = this.mPrimaryOrientation.getStartAfterPadding() - n;
        } else {
            n = this.getMaxEnd(this.mPrimaryOrientation.getEndAfterPadding()) - this.mPrimaryOrientation.getEndAfterPadding();
        }
        if (n <= 0) return 0;
        return Math.min(layoutState.mAvailable, n);
    }

    private int findFirstReferenceChildPosition(int n) {
        int n2 = this.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = this.getPosition(this.getChildAt(n3));
            if (n4 >= 0 && n4 < n) {
                return n4;
            }
            ++n3;
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int n) {
        int n2 = this.getChildCount() - 1;
        while (n2 >= 0) {
            int n3 = this.getPosition(this.getChildAt(n2));
            if (n3 >= 0 && n3 < n) {
                return n3;
            }
            --n2;
        }
        return 0;
    }

    private void fixEndGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n = this.getMaxEnd(Integer.MIN_VALUE);
        if (n == Integer.MIN_VALUE) {
            return;
        }
        n = this.mPrimaryOrientation.getEndAfterPadding() - n;
        if (n <= 0) return;
        n -= -this.scrollBy(-n, recycler, state);
        if (!bl) return;
        if (n <= 0) return;
        this.mPrimaryOrientation.offsetChildren(n);
    }

    private void fixStartGap(RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n = this.getMinStart(Integer.MAX_VALUE);
        if (n == Integer.MAX_VALUE) {
            return;
        }
        if ((n -= this.mPrimaryOrientation.getStartAfterPadding()) <= 0) return;
        n -= this.scrollBy(n, recycler, state);
        if (!bl) return;
        if (n <= 0) return;
        this.mPrimaryOrientation.offsetChildren(-n);
    }

    private int getMaxEnd(int n) {
        int n2 = this.mSpans[0].getEndLine(n);
        int n3 = 1;
        while (n3 < this.mSpanCount) {
            int n4 = this.mSpans[n3].getEndLine(n);
            int n5 = n2;
            if (n4 > n2) {
                n5 = n4;
            }
            ++n3;
            n2 = n5;
        }
        return n2;
    }

    private int getMaxStart(int n) {
        int n2 = this.mSpans[0].getStartLine(n);
        int n3 = 1;
        while (n3 < this.mSpanCount) {
            int n4 = this.mSpans[n3].getStartLine(n);
            int n5 = n2;
            if (n4 > n2) {
                n5 = n4;
            }
            ++n3;
            n2 = n5;
        }
        return n2;
    }

    private int getMinEnd(int n) {
        int n2 = this.mSpans[0].getEndLine(n);
        int n3 = 1;
        while (n3 < this.mSpanCount) {
            int n4 = this.mSpans[n3].getEndLine(n);
            int n5 = n2;
            if (n4 < n2) {
                n5 = n4;
            }
            ++n3;
            n2 = n5;
        }
        return n2;
    }

    private int getMinStart(int n) {
        int n2 = this.mSpans[0].getStartLine(n);
        int n3 = 1;
        while (n3 < this.mSpanCount) {
            int n4 = this.mSpans[n3].getStartLine(n);
            int n5 = n2;
            if (n4 < n2) {
                n5 = n4;
            }
            ++n3;
            n2 = n5;
        }
        return n2;
    }

    private Span getNextSpan(LayoutState object) {
        int n;
        int n2;
        boolean bl = this.preferLastSpan(((LayoutState)object).mLayoutDirection);
        int n3 = -1;
        if (bl) {
            n = this.mSpanCount - 1;
            n2 = -1;
        } else {
            n = 0;
            n3 = this.mSpanCount;
            n2 = 1;
        }
        int n4 = ((LayoutState)object).mLayoutDirection;
        Span span = null;
        object = null;
        if (n4 == 1) {
            n4 = Integer.MAX_VALUE;
            int n5 = this.mPrimaryOrientation.getStartAfterPadding();
            int n6 = n;
            while (n6 != n3) {
                span = this.mSpans[n6];
                int n7 = span.getEndLine(n5);
                n = n4;
                if (n7 < n4) {
                    object = span;
                    n = n7;
                }
                n6 += n2;
                n4 = n;
            }
            return object;
        }
        n4 = Integer.MIN_VALUE;
        int n8 = this.mPrimaryOrientation.getEndAfterPadding();
        object = span;
        while (n != n3) {
            span = this.mSpans[n];
            int n9 = span.getStartLine(n8);
            int n10 = n4;
            if (n9 > n4) {
                object = span;
                n10 = n9;
            }
            n += n2;
            n4 = n10;
        }
        return object;
    }

    private void handleUpdate(int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        block9 : {
            block8 : {
                block6 : {
                    block7 : {
                        n4 = this.mShouldReverseLayout ? this.getLastChildPosition() : this.getFirstChildPosition();
                        if (n3 != 8) break block6;
                        if (n >= n2) break block7;
                        n6 = n2 + 1;
                        break block8;
                    }
                    n6 = n + 1;
                    n5 = n2;
                    break block9;
                }
                n6 = n + n2;
            }
            n5 = n;
        }
        this.mLazySpanLookup.invalidateAfter(n5);
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 == 8) {
                    this.mLazySpanLookup.offsetForRemoval(n, 1);
                    this.mLazySpanLookup.offsetForAddition(n2, 1);
                }
            } else {
                this.mLazySpanLookup.offsetForRemoval(n, n2);
            }
        } else {
            this.mLazySpanLookup.offsetForAddition(n, n2);
        }
        if (n6 <= n4) {
            return;
        }
        n = this.mShouldReverseLayout ? this.getFirstChildPosition() : this.getLastChildPosition();
        if (n5 > n) return;
        this.requestLayout();
    }

    private void measureChildWithDecorationsAndMargin(View view, int n, int n2, boolean bl) {
        this.calculateItemDecorationsForChild(view, this.mTmpRect);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        n = this.updateSpecWithExtra(n, layoutParams.leftMargin + this.mTmpRect.left, layoutParams.rightMargin + this.mTmpRect.right);
        n2 = this.updateSpecWithExtra(n2, layoutParams.topMargin + this.mTmpRect.top, layoutParams.bottomMargin + this.mTmpRect.bottom);
        bl = bl ? this.shouldReMeasureChild(view, n, n2, layoutParams) : this.shouldMeasureChild(view, n, n2, layoutParams);
        if (!bl) return;
        view.measure(n, n2);
    }

    private void measureChildWithDecorationsAndMargin(View view, LayoutParams layoutParams, boolean bl) {
        if (layoutParams.mFullSpan) {
            if (this.mOrientation == 1) {
                this.measureChildWithDecorationsAndMargin(view, this.mFullSizeSpec, StaggeredGridLayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom(), layoutParams.height, true), bl);
                return;
            }
            this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight(), layoutParams.width, true), this.mFullSizeSpec, bl);
            return;
        }
        if (this.mOrientation == 1) {
            this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.getWidthMode(), 0, layoutParams.width, false), StaggeredGridLayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom(), layoutParams.height, true), bl);
            return;
        }
        this.measureChildWithDecorationsAndMargin(view, StaggeredGridLayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight(), layoutParams.width, true), StaggeredGridLayoutManager.getChildMeasureSpec(this.mSizePerSpan, this.getHeightMode(), 0, layoutParams.height, false), bl);
    }

    /*
     * Unable to fully structure code
     */
    private void onLayoutChildren(RecyclerView.Recycler var1_1, RecyclerView.State var2_2, boolean var3_3) {
        var4_4 = this.mAnchorInfo;
        if ((this.mPendingSavedState != null || this.mPendingScrollPosition != -1) && var2_2.getItemCount() == 0) {
            this.removeAndRecycleAllViews(var1_1);
            var4_4.reset();
            return;
        }
        var5_5 = var4_4.mValid;
        var6_6 = 1;
        var7_7 = var5_5 && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null ? 0 : 1;
        if (var7_7 != 0) {
            var4_4.reset();
            if (this.mPendingSavedState != null) {
                this.applyPendingSavedState(var4_4);
            } else {
                this.resolveShouldLayoutReverse();
                var4_4.mLayoutFromEnd = this.mShouldReverseLayout;
            }
            this.updateAnchorInfoForLayout(var2_2, var4_4);
            var4_4.mValid = true;
        }
        if (this.mPendingSavedState == null && this.mPendingScrollPosition == -1 && (var4_4.mLayoutFromEnd != this.mLastLayoutFromEnd || this.isLayoutRTL() != this.mLastLayoutRTL)) {
            this.mLazySpanLookup.clear();
            var4_4.mInvalidateOffsets = true;
        }
        if (this.getChildCount() > 0 && ((var8_8 = this.mPendingSavedState) == null || var8_8.mSpanOffsetsSize < 1)) {
            if (var4_4.mInvalidateOffsets) {
                for (var7_7 = 0; var7_7 < this.mSpanCount; ++var7_7) {
                    this.mSpans[var7_7].clear();
                    if (var4_4.mOffset == Integer.MIN_VALUE) continue;
                    this.mSpans[var7_7].setLine(var4_4.mOffset);
                }
            } else if (var7_7 == 0 && this.mAnchorInfo.mSpanReferenceLines != null) {
                for (var7_7 = 0; var7_7 < this.mSpanCount; ++var7_7) {
                    var8_8 = this.mSpans[var7_7];
                    var8_8.clear();
                    var8_8.setLine(this.mAnchorInfo.mSpanReferenceLines[var7_7]);
                }
            } else {
                for (var7_7 = 0; var7_7 < this.mSpanCount; ++var7_7) {
                    this.mSpans[var7_7].cacheReferenceLineAndClear(this.mShouldReverseLayout, var4_4.mOffset);
                }
                this.mAnchorInfo.saveSpanReferenceLines(this.mSpans);
            }
        }
        this.detachAndScrapAttachedViews(var1_1);
        this.mLayoutState.mRecycle = false;
        this.mLaidOutInvalidFullSpan = false;
        this.updateMeasureSpecs(this.mSecondaryOrientation.getTotalSpace());
        this.updateLayoutState(var4_4.mPosition, var2_2);
        if (var4_4.mLayoutFromEnd) {
            this.setLayoutStateDirection(-1);
            this.fill(var1_1, this.mLayoutState, var2_2);
            this.setLayoutStateDirection(1);
            this.mLayoutState.mCurrentPosition = var4_4.mPosition + this.mLayoutState.mItemDirection;
            this.fill(var1_1, this.mLayoutState, var2_2);
        } else {
            this.setLayoutStateDirection(1);
            this.fill(var1_1, this.mLayoutState, var2_2);
            this.setLayoutStateDirection(-1);
            this.mLayoutState.mCurrentPosition = var4_4.mPosition + this.mLayoutState.mItemDirection;
            this.fill(var1_1, this.mLayoutState, var2_2);
        }
        this.repositionToWrapContentIfNecessary();
        if (this.getChildCount() > 0) {
            if (this.mShouldReverseLayout) {
                this.fixEndGap(var1_1, var2_2, true);
                this.fixStartGap(var1_1, var2_2, false);
            } else {
                this.fixStartGap(var1_1, var2_2, true);
                this.fixEndGap(var1_1, var2_2, false);
            }
        }
        if (!var3_3 || var2_2.isPreLayout() || (var7_7 = this.mGapStrategy != 0 && this.getChildCount() > 0 && (this.mLaidOutInvalidFullSpan != false || this.hasGapsToFix() != null) ? 1 : 0) == 0) ** GOTO lbl-1000
        this.removeCallbacks(this.mCheckForGapsRunnable);
        if (this.checkForGaps()) {
            var7_7 = var6_6;
        } else lbl-1000: // 2 sources:
        {
            var7_7 = 0;
        }
        if (var2_2.isPreLayout()) {
            this.mAnchorInfo.reset();
        }
        this.mLastLayoutFromEnd = var4_4.mLayoutFromEnd;
        this.mLastLayoutRTL = this.isLayoutRTL();
        if (var7_7 == 0) return;
        this.mAnchorInfo.reset();
        this.onLayoutChildren(var1_1, var2_2, false);
    }

    private boolean preferLastSpan(int n) {
        int n2 = this.mOrientation;
        boolean bl = true;
        boolean bl2 = true;
        if (n2 == 0) {
            boolean bl3 = n == -1;
            if (bl3 == this.mShouldReverseLayout) return false;
            return bl2;
        }
        boolean bl4 = n == -1;
        bl4 = bl4 == this.mShouldReverseLayout;
        if (bl4 != this.isLayoutRTL()) return false;
        return bl;
    }

    private void prependViewToAllSpans(View view) {
        int n = this.mSpanCount - 1;
        while (n >= 0) {
            this.mSpans[n].prependToSpan(view);
            --n;
        }
    }

    private void recycle(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (!layoutState.mRecycle) return;
        if (layoutState.mInfinite) {
            return;
        }
        if (layoutState.mAvailable == 0) {
            if (layoutState.mLayoutDirection == -1) {
                this.recycleFromEnd(recycler, layoutState.mEndLine);
                return;
            }
            this.recycleFromStart(recycler, layoutState.mStartLine);
            return;
        }
        if (layoutState.mLayoutDirection == -1) {
            int n = layoutState.mStartLine - this.getMaxStart(layoutState.mStartLine);
            n = n < 0 ? layoutState.mEndLine : layoutState.mEndLine - Math.min(n, layoutState.mAvailable);
            this.recycleFromEnd(recycler, n);
            return;
        }
        int n = this.getMinEnd(layoutState.mEndLine) - layoutState.mEndLine;
        if (n < 0) {
            n = layoutState.mStartLine;
        } else {
            int n2 = layoutState.mStartLine;
            n = Math.min(n, layoutState.mAvailable) + n2;
        }
        this.recycleFromStart(recycler, n);
    }

    private void recycleFromEnd(RecyclerView.Recycler recycler, int n) {
        int n2 = this.getChildCount() - 1;
        while (n2 >= 0) {
            View view = this.getChildAt(n2);
            if (this.mPrimaryOrientation.getDecoratedStart(view) < n) return;
            if (this.mPrimaryOrientation.getTransformedStartWithDecoration(view) < n) return;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (!layoutParams.mFullSpan) {
                if (layoutParams.mSpan.mViews.size() == 1) {
                    return;
                }
                layoutParams.mSpan.popEnd();
            } else {
                int n3 = 0;
                int n4 = 0;
                do {
                    if (n4 >= this.mSpanCount) break;
                    if (this.mSpans[n4].mViews.size() == 1) {
                        return;
                    }
                    ++n4;
                } while (true);
                for (int i = n3; i < this.mSpanCount; ++i) {
                    this.mSpans[i].popEnd();
                }
            }
            this.removeAndRecycleView(view, recycler);
            --n2;
        }
    }

    private void recycleFromStart(RecyclerView.Recycler recycler, int n) {
        while (this.getChildCount() > 0) {
            int n2 = 0;
            View view = this.getChildAt(0);
            if (this.mPrimaryOrientation.getDecoratedEnd(view) > n) return;
            if (this.mPrimaryOrientation.getTransformedEndWithDecoration(view) > n) return;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (!layoutParams.mFullSpan) {
                if (layoutParams.mSpan.mViews.size() == 1) {
                    return;
                }
                layoutParams.mSpan.popStart();
            } else {
                int n3 = 0;
                do {
                    if (n3 >= this.mSpanCount) break;
                    if (this.mSpans[n3].mViews.size() == 1) {
                        return;
                    }
                    ++n3;
                } while (true);
                for (int i = n2; i < this.mSpanCount; ++i) {
                    this.mSpans[i].popStart();
                }
            }
            this.removeAndRecycleView(view, recycler);
        }
    }

    private void repositionToWrapContentIfNecessary() {
        int n;
        int n2;
        View view;
        if (this.mSecondaryOrientation.getMode() == 1073741824) {
            return;
        }
        float f = 0.0f;
        int n3 = this.getChildCount();
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            view = this.getChildAt(n);
            float f2 = this.mSecondaryOrientation.getDecoratedMeasurement(view);
            if (f2 < f) continue;
            float f3 = f2;
            if (((LayoutParams)view.getLayoutParams()).isFullSpan()) {
                f3 = f2 * 1.0f / (float)this.mSpanCount;
            }
            f = Math.max(f, f3);
        }
        int n5 = this.mSizePerSpan;
        n = n2 = Math.round(f * (float)this.mSpanCount);
        if (this.mSecondaryOrientation.getMode() == Integer.MIN_VALUE) {
            n = Math.min(n2, this.mSecondaryOrientation.getTotalSpace());
        }
        this.updateMeasureSpecs(n);
        n = n4;
        if (this.mSizePerSpan == n5) {
            return;
        }
        while (n < n3) {
            view = this.getChildAt(n);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (!layoutParams.mFullSpan) {
                if (this.isLayoutRTL() && this.mOrientation == 1) {
                    view.offsetLeftAndRight(-(this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * this.mSizePerSpan - -(this.mSpanCount - 1 - layoutParams.mSpan.mIndex) * n5);
                } else {
                    n2 = layoutParams.mSpan.mIndex * this.mSizePerSpan;
                    n4 = layoutParams.mSpan.mIndex * n5;
                    if (this.mOrientation == 1) {
                        view.offsetLeftAndRight(n2 - n4);
                    } else {
                        view.offsetTopAndBottom(n2 - n4);
                    }
                }
            }
            ++n;
        }
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation != 1 && this.isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout ^ true;
            return;
        }
        this.mShouldReverseLayout = this.mReverseLayout;
    }

    private void setLayoutStateDirection(int n) {
        this.mLayoutState.mLayoutDirection = n;
        LayoutState layoutState = this.mLayoutState;
        boolean bl = this.mShouldReverseLayout;
        int n2 = 1;
        boolean bl2 = n == -1;
        n = bl == bl2 ? n2 : -1;
        layoutState.mItemDirection = n;
    }

    private void updateAllRemainingSpans(int n, int n2) {
        int n3 = 0;
        while (n3 < this.mSpanCount) {
            if (!this.mSpans[n3].mViews.isEmpty()) {
                this.updateRemainingSpans(this.mSpans[n3], n, n2);
            }
            ++n3;
        }
    }

    private boolean updateAnchorFromChildren(RecyclerView.State state, AnchorInfo anchorInfo) {
        int n = this.mLastLayoutFromEnd ? this.findLastReferenceChildPosition(state.getItemCount()) : this.findFirstReferenceChildPosition(state.getItemCount());
        anchorInfo.mPosition = n;
        anchorInfo.mOffset = Integer.MIN_VALUE;
        return true;
    }

    private void updateLayoutState(int n, RecyclerView.State object) {
        boolean bl;
        boolean bl2;
        int n2;
        block7 : {
            block6 : {
                block4 : {
                    block5 : {
                        LayoutState layoutState = this.mLayoutState;
                        bl = false;
                        layoutState.mAvailable = 0;
                        this.mLayoutState.mCurrentPosition = n;
                        if (!this.isSmoothScrolling() || (n2 = ((RecyclerView.State)object).getTargetScrollPosition()) == -1) break block4;
                        boolean bl3 = this.mShouldReverseLayout;
                        bl2 = n2 < n;
                        if (bl3 != bl2) break block5;
                        n = this.mPrimaryOrientation.getTotalSpace();
                        break block6;
                    }
                    n2 = this.mPrimaryOrientation.getTotalSpace();
                    n = 0;
                    break block7;
                }
                n = 0;
            }
            n2 = 0;
        }
        if (this.getClipToPadding()) {
            this.mLayoutState.mStartLine = this.mPrimaryOrientation.getStartAfterPadding() - n2;
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEndAfterPadding() + n;
        } else {
            this.mLayoutState.mEndLine = this.mPrimaryOrientation.getEnd() + n;
            this.mLayoutState.mStartLine = -n2;
        }
        this.mLayoutState.mStopInFocusable = false;
        this.mLayoutState.mRecycle = true;
        object = this.mLayoutState;
        bl2 = bl;
        if (this.mPrimaryOrientation.getMode() == 0) {
            bl2 = bl;
            if (this.mPrimaryOrientation.getEnd() == 0) {
                bl2 = true;
            }
        }
        ((LayoutState)object).mInfinite = bl2;
    }

    private void updateRemainingSpans(Span span, int n, int n2) {
        int n3 = span.getDeletedSize();
        if (n == -1) {
            if (span.getStartLine() + n3 > n2) return;
            this.mRemainingSpans.set(span.mIndex, false);
            return;
        }
        if (span.getEndLine() - n3 < n2) return;
        this.mRemainingSpans.set(span.mIndex, false);
    }

    private int updateSpecWithExtra(int n, int n2, int n3) {
        if (n2 == 0 && n3 == 0) {
            return n;
        }
        int n4 = View.MeasureSpec.getMode((int)n);
        if (n4 == Integer.MIN_VALUE) return View.MeasureSpec.makeMeasureSpec((int)Math.max(0, View.MeasureSpec.getSize((int)n) - n2 - n3), (int)n4);
        if (n4 != 1073741824) return n;
        return View.MeasureSpec.makeMeasureSpec((int)Math.max(0, View.MeasureSpec.getSize((int)n) - n2 - n3), (int)n4);
    }

    boolean areAllEndsEqual() {
        int n = this.mSpans[0].getEndLine(Integer.MIN_VALUE);
        int n2 = 1;
        while (n2 < this.mSpanCount) {
            if (this.mSpans[n2].getEndLine(Integer.MIN_VALUE) != n) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    boolean areAllStartsEqual() {
        int n = this.mSpans[0].getStartLine(Integer.MIN_VALUE);
        int n2 = 1;
        while (n2 < this.mSpanCount) {
            if (this.mSpans[n2].getStartLine(Integer.MIN_VALUE) != n) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    public void assertNotInLayoutOrScroll(String string2) {
        if (this.mPendingSavedState != null) return;
        super.assertNotInLayoutOrScroll(string2);
    }

    @Override
    public boolean canScrollHorizontally() {
        if (this.mOrientation != 0) return false;
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        int n = this.mOrientation;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    boolean checkForGaps() {
        int n;
        int n2;
        if (this.getChildCount() == 0) return false;
        if (this.mGapStrategy == 0) return false;
        if (!this.isAttachedToWindow()) {
            return false;
        }
        if (this.mShouldReverseLayout) {
            n = this.getLastChildPosition();
            n2 = this.getFirstChildPosition();
        } else {
            n = this.getFirstChildPosition();
            n2 = this.getLastChildPosition();
        }
        if (n == 0 && this.hasGapsToFix() != null) {
            this.mLazySpanLookup.clear();
            this.requestSimpleAnimationsInNextLayout();
            this.requestLayout();
            return true;
        }
        if (!this.mLaidOutInvalidFullSpan) {
            return false;
        }
        int n3 = this.mShouldReverseLayout ? -1 : 1;
        Object object = this.mLazySpanLookup;
        if ((object = ((LazySpanLookup)object).getFirstFullSpanItemInRange(n, ++n2, n3, true)) == null) {
            this.mLaidOutInvalidFullSpan = false;
            this.mLazySpanLookup.forceInvalidateAfter(n2);
            return false;
        }
        LazySpanLookup.FullSpanItem fullSpanItem = this.mLazySpanLookup.getFirstFullSpanItemInRange(n, ((LazySpanLookup.FullSpanItem)object).mPosition, n3 * -1, true);
        if (fullSpanItem == null) {
            this.mLazySpanLookup.forceInvalidateAfter(((LazySpanLookup.FullSpanItem)object).mPosition);
        } else {
            this.mLazySpanLookup.forceInvalidateAfter(fullSpanItem.mPosition + 1);
        }
        this.requestSimpleAnimationsInNextLayout();
        this.requestLayout();
        return true;
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    public void collectAdjacentPrefetchPositions(int n, int n2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.mOrientation != 0) {
            n = n2;
        }
        if (this.getChildCount() == 0) return;
        if (n == 0) {
            return;
        }
        this.prepareLayoutStateForDelta(n, state);
        Object object = this.mPrefetchDistances;
        if (object == null || ((int[])object).length < this.mSpanCount) {
            this.mPrefetchDistances = new int[this.mSpanCount];
        }
        int n3 = 0;
        n = 0;
        for (n2 = 0; n2 < this.mSpanCount; ++n2) {
            int n4;
            int n5;
            if (this.mLayoutState.mItemDirection == -1) {
                n4 = this.mLayoutState.mStartLine;
                n5 = this.mSpans[n2].getStartLine(this.mLayoutState.mStartLine);
            } else {
                n4 = this.mSpans[n2].getEndLine(this.mLayoutState.mEndLine);
                n5 = this.mLayoutState.mEndLine;
            }
            n4 -= n5;
            n5 = n;
            if (n4 >= 0) {
                this.mPrefetchDistances[n] = n4;
                n5 = n + 1;
            }
            n = n5;
        }
        Arrays.sort(this.mPrefetchDistances, 0, n);
        n2 = n3;
        while (n2 < n) {
            if (!this.mLayoutState.hasMore(state)) return;
            layoutPrefetchRegistry.addPosition(this.mLayoutState.mCurrentPosition, this.mPrefetchDistances[n2]);
            object = this.mLayoutState;
            object.mCurrentPosition += this.mLayoutState.mItemDirection;
            ++n2;
        }
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    @Override
    public PointF computeScrollVectorForPosition(int n) {
        n = this.calculateScrollDirectionForPosition(n);
        PointF pointF = new PointF();
        if (n == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = n;
            pointF.y = 0.0f;
            return pointF;
        }
        pointF.x = 0.0f;
        pointF.y = n;
        return pointF;
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] arrn) {
        if (arrn == null) {
            arrn = new int[this.mSpanCount];
        } else if (arrn.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(arrn.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n = 0;
        while (n < this.mSpanCount) {
            arrn[n] = this.mSpans[n].findFirstCompletelyVisibleItemPosition();
            ++n;
        }
        return arrn;
    }

    View findFirstVisibleItemClosestToEnd(boolean bl) {
        int n = this.mPrimaryOrientation.getStartAfterPadding();
        int n2 = this.mPrimaryOrientation.getEndAfterPadding();
        int n3 = this.getChildCount() - 1;
        View view = null;
        while (n3 >= 0) {
            View view2 = this.getChildAt(n3);
            int n4 = this.mPrimaryOrientation.getDecoratedStart(view2);
            int n5 = this.mPrimaryOrientation.getDecoratedEnd(view2);
            View view3 = view;
            if (n5 > n) {
                if (n4 >= n2) {
                    view3 = view;
                } else {
                    if (n5 <= n2) return view2;
                    if (!bl) {
                        return view2;
                    }
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                }
            }
            --n3;
            view = view3;
        }
        return view;
    }

    View findFirstVisibleItemClosestToStart(boolean bl) {
        int n = this.mPrimaryOrientation.getStartAfterPadding();
        int n2 = this.mPrimaryOrientation.getEndAfterPadding();
        int n3 = this.getChildCount();
        View view = null;
        int n4 = 0;
        while (n4 < n3) {
            View view2 = this.getChildAt(n4);
            int n5 = this.mPrimaryOrientation.getDecoratedStart(view2);
            View view3 = view;
            if (this.mPrimaryOrientation.getDecoratedEnd(view2) > n) {
                if (n5 >= n2) {
                    view3 = view;
                } else {
                    if (n5 >= n) return view2;
                    if (!bl) {
                        return view2;
                    }
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                }
            }
            ++n4;
            view = view3;
        }
        return view;
    }

    int findFirstVisibleItemPositionInt() {
        View view = this.mShouldReverseLayout ? this.findFirstVisibleItemClosestToEnd(true) : this.findFirstVisibleItemClosestToStart(true);
        if (view != null) return this.getPosition(view);
        return -1;
    }

    public int[] findFirstVisibleItemPositions(int[] arrn) {
        if (arrn == null) {
            arrn = new int[this.mSpanCount];
        } else if (arrn.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(arrn.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n = 0;
        while (n < this.mSpanCount) {
            arrn[n] = this.mSpans[n].findFirstVisibleItemPosition();
            ++n;
        }
        return arrn;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] arrn) {
        if (arrn == null) {
            arrn = new int[this.mSpanCount];
        } else if (arrn.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(arrn.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n = 0;
        while (n < this.mSpanCount) {
            arrn[n] = this.mSpans[n].findLastCompletelyVisibleItemPosition();
            ++n;
        }
        return arrn;
    }

    public int[] findLastVisibleItemPositions(int[] arrn) {
        if (arrn == null) {
            arrn = new int[this.mSpanCount];
        } else if (arrn.length < this.mSpanCount) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provided int[]'s size must be more than or equal to span count. Expected:");
            stringBuilder.append(this.mSpanCount);
            stringBuilder.append(", array size:");
            stringBuilder.append(arrn.length);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n = 0;
        while (n < this.mSpanCount) {
            arrn[n] = this.mSpans[n].findLastVisibleItemPosition();
            ++n;
        }
        return arrn;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation != 0) return new LayoutParams(-1, -2);
        return new LayoutParams(-2, -1);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) return new LayoutParams(layoutParams);
        return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation != 1) return super.getColumnCountForAccessibility(recycler, state);
        return this.mSpanCount;
    }

    int getFirstChildPosition() {
        int n = this.getChildCount();
        int n2 = 0;
        if (n != 0) return this.getPosition(this.getChildAt(0));
        return n2;
    }

    public int getGapStrategy() {
        return this.mGapStrategy;
    }

    int getLastChildPosition() {
        int n = this.getChildCount();
        if (n != 0) return this.getPosition(this.getChildAt(n - 1));
        return 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation != 0) return super.getRowCountForAccessibility(recycler, state);
        return this.mSpanCount;
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    /*
     * Unable to fully structure code
     */
    View hasGapsToFix() {
        var1_1 = this.getChildCount() - 1;
        var2_2 = new BitSet(this.mSpanCount);
        var2_2.set(0, this.mSpanCount, true);
        var3_3 = this.mOrientation;
        var4_4 = -1;
        var3_3 = var3_3 == 1 && this.isLayoutRTL() != false ? 1 : -1;
        if (this.mShouldReverseLayout) {
            var5_5 = -1;
        } else {
            var5_5 = var1_1 + 1;
            var1_1 = 0;
        }
        var6_6 = var1_1;
        if (var1_1 < var5_5) {
            var4_4 = 1;
            var6_6 = var1_1;
        }
        while (var6_6 != var5_5) {
            block12 : {
                block13 : {
                    var7_7 = this.getChildAt(var6_6);
                    var8_8 = (LayoutParams)var7_7.getLayoutParams();
                    if (var2_2.get(var8_8.mSpan.mIndex)) {
                        if (this.checkSpanForGap(var8_8.mSpan)) {
                            return var7_7;
                        }
                        var2_2.clear(var8_8.mSpan.mIndex);
                    }
                    if (var8_8.mFullSpan || (var1_1 = var6_6 + var4_4) == var5_5) break block12;
                    var9_10 = this.getChildAt(var1_1);
                    if (!this.mShouldReverseLayout) break block13;
                    var1_1 = this.mPrimaryOrientation.getDecoratedEnd(var7_7);
                    if (var1_1 < (var10_12 = this.mPrimaryOrientation.getDecoratedEnd(var9_10))) {
                        return var7_7;
                    }
                    if (var1_1 != var10_12) ** GOTO lbl-1000
                    ** GOTO lbl-1000
                }
                var10_12 = this.mPrimaryOrientation.getDecoratedStart(var7_7);
                if (var10_12 > (var1_1 = this.mPrimaryOrientation.getDecoratedStart(var9_10))) {
                    return var7_7;
                }
                if (var10_12 == var1_1) lbl-1000: // 2 sources:
                {
                    var1_1 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var1_1 = 0;
                }
                if (var1_1 != 0) {
                    var9_11 = (LayoutParams)var9_10.getLayoutParams();
                    var1_1 = var8_8.mSpan.mIndex - var9_11.mSpan.mIndex < 0 ? 1 : 0;
                    if (var1_1 != (var10_12 = var3_3 < 0 ? 1 : 0)) {
                        return var7_7;
                    }
                }
            }
            var6_6 += var4_4;
        }
        return null;
    }

    public void invalidateSpanAssignments() {
        this.mLazySpanLookup.clear();
        this.requestLayout();
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        if (this.mGapStrategy == 0) return false;
        return true;
    }

    boolean isLayoutRTL() {
        int n = this.getLayoutDirection();
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    @Override
    public void offsetChildrenHorizontal(int n) {
        super.offsetChildrenHorizontal(n);
        int n2 = 0;
        while (n2 < this.mSpanCount) {
            this.mSpans[n2].onOffset(n);
            ++n2;
        }
    }

    @Override
    public void offsetChildrenVertical(int n) {
        super.offsetChildrenVertical(n);
        int n2 = 0;
        while (n2 < this.mSpanCount) {
            this.mSpans[n2].onOffset(n);
            ++n2;
        }
    }

    @Override
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        this.removeCallbacks(this.mCheckForGapsRunnable);
        int n = 0;
        do {
            if (n >= this.mSpanCount) {
                recyclerView.requestLayout();
                return;
            }
            this.mSpans[n].clear();
            ++n;
        } while (true);
    }

    @Override
    public View onFocusSearchFailed(View view, int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int n2;
        if (this.getChildCount() == 0) {
            return null;
        }
        if ((view = this.findContainingItemView(view)) == null) {
            return null;
        }
        this.resolveShouldLayoutReverse();
        int n3 = this.convertFocusDirectionToLayoutDirection(n);
        if (n3 == Integer.MIN_VALUE) {
            return null;
        }
        Object object = (LayoutParams)view.getLayoutParams();
        boolean bl = ((LayoutParams)object).mFullSpan;
        object = ((LayoutParams)object).mSpan;
        n = n3 == 1 ? this.getLastChildPosition() : this.getFirstChildPosition();
        this.updateLayoutState(n, state);
        this.setLayoutStateDirection(n3);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = layoutState.mItemDirection + n;
        this.mLayoutState.mAvailable = (int)((float)this.mPrimaryOrientation.getTotalSpace() * 0.33333334f);
        this.mLayoutState.mStopInFocusable = true;
        layoutState = this.mLayoutState;
        int n4 = 0;
        layoutState.mRecycle = false;
        this.fill(recycler, this.mLayoutState, state);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        if (!bl && (recycler = ((Span)object).getFocusableViewAfter(n, n3)) != null && recycler != view) {
            return recycler;
        }
        if (this.preferLastSpan(n3)) {
            for (n2 = this.mSpanCount - 1; n2 >= 0; --n2) {
                recycler = this.mSpans[n2].getFocusableViewAfter(n, n3);
                if (recycler == null || recycler == view) continue;
                return recycler;
            }
        } else {
            for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                recycler = this.mSpans[n2].getFocusableViewAfter(n, n3);
                if (recycler == null || recycler == view) continue;
                return recycler;
            }
        }
        boolean bl2 = this.mReverseLayout;
        n = n3 == -1 ? 1 : 0;
        n = (bl2 ^ true) == n ? 1 : 0;
        if (!bl && (recycler = this.findViewByPosition(n2 = n != 0 ? ((Span)object).findFirstPartiallyVisibleItemPosition() : ((Span)object).findLastPartiallyVisibleItemPosition())) != null && recycler != view) {
            return recycler;
        }
        n2 = n4;
        if (this.preferLastSpan(n3)) {
            n2 = this.mSpanCount - 1;
            while (n2 >= 0) {
                if (n2 != ((Span)object).mIndex && (recycler = this.findViewByPosition(n4 = n != 0 ? this.mSpans[n2].findFirstPartiallyVisibleItemPosition() : this.mSpans[n2].findLastPartiallyVisibleItemPosition())) != null && recycler != view) {
                    return recycler;
                }
                --n2;
            }
            return null;
        }
        while (n2 < this.mSpanCount) {
            n4 = n != 0 ? this.mSpans[n2].findFirstPartiallyVisibleItemPosition() : this.mSpans[n2].findLastPartiallyVisibleItemPosition();
            recycler = this.findViewByPosition(n4);
            if (recycler != null && recycler != view) {
                return recycler;
            }
            ++n2;
        }
        return null;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int n;
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.getChildCount() <= 0) return;
        View view = this.findFirstVisibleItemClosestToStart(false);
        View view2 = this.findFirstVisibleItemClosestToEnd(false);
        if (view == null) return;
        if (view2 == null) {
            return;
        }
        int n2 = this.getPosition(view);
        if (n2 < (n = this.getPosition(view2))) {
            accessibilityEvent.setFromIndex(n2);
            accessibilityEvent.setToIndex(n);
            return;
        }
        accessibilityEvent.setFromIndex(n);
        accessibilityEvent.setToIndex(n2);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler object, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        object = view.getLayoutParams();
        if (!(object instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        object = (LayoutParams)((Object)object);
        if (this.mOrientation == 0) {
            int n = ((LayoutParams)((Object)object)).getSpanIndex();
            int n2 = ((LayoutParams)object).mFullSpan ? this.mSpanCount : 1;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n, n2, -1, -1, false, false));
            return;
        }
        int n = ((LayoutParams)((Object)object)).getSpanIndex();
        int n3 = ((LayoutParams)object).mFullSpan ? this.mSpanCount : 1;
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, n, n3, false, false));
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        this.handleUpdate(n, n2, 1);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mLazySpanLookup.clear();
        this.requestLayout();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        this.handleUpdate(n, n2, 8);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        this.handleUpdate(n, n2, 2);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
        this.handleUpdate(n, n2, 4);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.onLayoutChildren(recycler, state, true);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo.reset();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) return;
        this.mPendingSavedState = (SavedState)parcelable;
        this.requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        savedState.mReverseLayout = this.mReverseLayout;
        savedState.mAnchorLayoutFromEnd = this.mLastLayoutFromEnd;
        savedState.mLastLayoutRTL = this.mLastLayoutRTL;
        LazySpanLookup lazySpanLookup = this.mLazySpanLookup;
        int n = 0;
        if (lazySpanLookup != null && lazySpanLookup.mData != null) {
            savedState.mSpanLookup = this.mLazySpanLookup.mData;
            savedState.mSpanLookupSize = savedState.mSpanLookup.length;
            savedState.mFullSpanItems = this.mLazySpanLookup.mFullSpanItems;
        } else {
            savedState.mSpanLookupSize = 0;
        }
        if (this.getChildCount() <= 0) {
            savedState.mAnchorPosition = -1;
            savedState.mVisibleAnchorPosition = -1;
            savedState.mSpanOffsetsSize = 0;
            return savedState;
        }
        int n2 = this.mLastLayoutFromEnd ? this.getLastChildPosition() : this.getFirstChildPosition();
        savedState.mAnchorPosition = n2;
        savedState.mVisibleAnchorPosition = this.findFirstVisibleItemPositionInt();
        savedState.mSpanOffsetsSize = this.mSpanCount;
        savedState.mSpanOffsets = new int[this.mSpanCount];
        while (n < this.mSpanCount) {
            block9 : {
                int n3;
                block10 : {
                    block8 : {
                        if (!this.mLastLayoutFromEnd) break block8;
                        n2 = n3 = this.mSpans[n].getEndLine(Integer.MIN_VALUE);
                        if (n3 == Integer.MIN_VALUE) break block9;
                        n2 = this.mPrimaryOrientation.getEndAfterPadding();
                        break block10;
                    }
                    n2 = n3 = this.mSpans[n].getStartLine(Integer.MIN_VALUE);
                    if (n3 == Integer.MIN_VALUE) break block9;
                    n2 = this.mPrimaryOrientation.getStartAfterPadding();
                }
                n2 = n3 - n2;
            }
            savedState.mSpanOffsets[n] = n2;
            ++n;
        }
        return savedState;
    }

    @Override
    public void onScrollStateChanged(int n) {
        if (n != 0) return;
        this.checkForGaps();
    }

    void prepareLayoutStateForDelta(int n, RecyclerView.State object) {
        int n2;
        int n3;
        if (n > 0) {
            n3 = this.getLastChildPosition();
            n2 = 1;
        } else {
            n3 = this.getFirstChildPosition();
            n2 = -1;
        }
        this.mLayoutState.mRecycle = true;
        this.updateLayoutState(n3, (RecyclerView.State)object);
        this.setLayoutStateDirection(n2);
        object = this.mLayoutState;
        ((LayoutState)object).mCurrentPosition = n3 + ((LayoutState)object).mItemDirection;
        this.mLayoutState.mAvailable = Math.abs(n);
    }

    int scrollBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.getChildCount() == 0) return 0;
        if (n == 0) {
            return 0;
        }
        this.prepareLayoutStateForDelta(n, state);
        int n2 = this.fill(recycler, this.mLayoutState, state);
        if (this.mLayoutState.mAvailable >= n2) {
            n = n < 0 ? -n2 : n2;
        }
        this.mPrimaryOrientation.offsetChildren(-n);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        this.mLayoutState.mAvailable = 0;
        this.recycle(recycler, this.mLayoutState);
        return n;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.scrollBy(n, recycler, state);
    }

    @Override
    public void scrollToPosition(int n) {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && savedState.mAnchorPosition != n) {
            this.mPendingSavedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.requestLayout();
    }

    public void scrollToPositionWithOffset(int n, int n2) {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.invalidateAnchorPositionInfo();
        }
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = n2;
        this.requestLayout();
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.scrollBy(n, recycler, state);
    }

    public void setGapStrategy(int n) {
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mGapStrategy) {
            return;
        }
        if (n != 0) {
            if (n != 2) throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
        this.mGapStrategy = n;
        this.requestLayout();
    }

    @Override
    public void setMeasuredDimension(Rect rect, int n, int n2) {
        int n3 = this.getPaddingLeft() + this.getPaddingRight();
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (this.mOrientation == 1) {
            n2 = StaggeredGridLayoutManager.chooseSize(n2, rect.height() + n4, this.getMinimumHeight());
            n = StaggeredGridLayoutManager.chooseSize(n, this.mSizePerSpan * this.mSpanCount + n3, this.getMinimumWidth());
        } else {
            n = StaggeredGridLayoutManager.chooseSize(n, rect.width() + n3, this.getMinimumWidth());
            n2 = StaggeredGridLayoutManager.chooseSize(n2, this.mSizePerSpan * this.mSpanCount + n4, this.getMinimumHeight());
        }
        this.setMeasuredDimension(n, n2);
    }

    public void setOrientation(int n) {
        if (n != 0) {
            if (n != 1) throw new IllegalArgumentException("invalid orientation.");
        }
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mOrientation) {
            return;
        }
        this.mOrientation = n;
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        this.mPrimaryOrientation = this.mSecondaryOrientation;
        this.mSecondaryOrientation = orientationHelper;
        this.requestLayout();
    }

    public void setReverseLayout(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && savedState.mReverseLayout != bl) {
            this.mPendingSavedState.mReverseLayout = bl;
        }
        this.mReverseLayout = bl;
        this.requestLayout();
    }

    public void setSpanCount(int n) {
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mSpanCount) return;
        this.invalidateSpanAssignments();
        this.mSpanCount = n;
        this.mRemainingSpans = new BitSet(this.mSpanCount);
        this.mSpans = new Span[this.mSpanCount];
        n = 0;
        do {
            if (n >= this.mSpanCount) {
                this.requestLayout();
                return;
            }
            this.mSpans[n] = new Span(n);
            ++n;
        } while (true);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView object, RecyclerView.State state, int n) {
        object = new LinearSmoothScroller(object.getContext());
        ((RecyclerView.SmoothScroller)object).setTargetPosition(n);
        this.startSmoothScroll((RecyclerView.SmoothScroller)object);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState != null) return false;
        return true;
    }

    boolean updateAnchorFromPendingData(RecyclerView.State object, AnchorInfo anchorInfo) {
        boolean bl = ((RecyclerView.State)object).isPreLayout();
        boolean bl2 = false;
        if (bl) return false;
        int n = this.mPendingScrollPosition;
        if (n == -1) {
            return false;
        }
        if (n >= 0 && n < ((RecyclerView.State)object).getItemCount()) {
            object = this.mPendingSavedState;
            if (object != null && ((SavedState)object).mAnchorPosition != -1 && this.mPendingSavedState.mSpanOffsetsSize >= 1) {
                anchorInfo.mOffset = Integer.MIN_VALUE;
                anchorInfo.mPosition = this.mPendingScrollPosition;
                return true;
            }
            object = this.findViewByPosition(this.mPendingScrollPosition);
            if (object != null) {
                n = this.mShouldReverseLayout ? this.getLastChildPosition() : this.getFirstChildPosition();
                anchorInfo.mPosition = n;
                if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                    if (anchorInfo.mLayoutFromEnd) {
                        anchorInfo.mOffset = this.mPrimaryOrientation.getEndAfterPadding() - this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedEnd((View)object);
                        return true;
                    }
                    anchorInfo.mOffset = this.mPrimaryOrientation.getStartAfterPadding() + this.mPendingScrollPositionOffset - this.mPrimaryOrientation.getDecoratedStart((View)object);
                    return true;
                }
                if (this.mPrimaryOrientation.getDecoratedMeasurement((View)object) > this.mPrimaryOrientation.getTotalSpace()) {
                    n = anchorInfo.mLayoutFromEnd ? this.mPrimaryOrientation.getEndAfterPadding() : this.mPrimaryOrientation.getStartAfterPadding();
                    anchorInfo.mOffset = n;
                    return true;
                }
                n = this.mPrimaryOrientation.getDecoratedStart((View)object) - this.mPrimaryOrientation.getStartAfterPadding();
                if (n < 0) {
                    anchorInfo.mOffset = -n;
                    return true;
                }
                n = this.mPrimaryOrientation.getEndAfterPadding() - this.mPrimaryOrientation.getDecoratedEnd((View)object);
                if (n < 0) {
                    anchorInfo.mOffset = n;
                    return true;
                }
                anchorInfo.mOffset = Integer.MIN_VALUE;
                return true;
            }
            anchorInfo.mPosition = this.mPendingScrollPosition;
            n = this.mPendingScrollPositionOffset;
            if (n == Integer.MIN_VALUE) {
                if (this.calculateScrollDirectionForPosition(anchorInfo.mPosition) == 1) {
                    bl2 = true;
                }
                anchorInfo.mLayoutFromEnd = bl2;
                anchorInfo.assignCoordinateFromPadding();
            } else {
                anchorInfo.assignCoordinateFromPadding(n);
            }
            anchorInfo.mInvalidateOffsets = true;
            return true;
        }
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        return false;
    }

    void updateAnchorInfoForLayout(RecyclerView.State state, AnchorInfo anchorInfo) {
        if (this.updateAnchorFromPendingData(state, anchorInfo)) {
            return;
        }
        if (this.updateAnchorFromChildren(state, anchorInfo)) {
            return;
        }
        anchorInfo.assignCoordinateFromPadding();
        anchorInfo.mPosition = 0;
    }

    void updateMeasureSpecs(int n) {
        this.mSizePerSpan = n / this.mSpanCount;
        this.mFullSizeSpec = View.MeasureSpec.makeMeasureSpec((int)n, (int)this.mSecondaryOrientation.getMode());
    }

    class AnchorInfo {
        boolean mInvalidateOffsets;
        boolean mLayoutFromEnd;
        int mOffset;
        int mPosition;
        int[] mSpanReferenceLines;
        boolean mValid;

        AnchorInfo() {
            this.reset();
        }

        void assignCoordinateFromPadding() {
            int n = this.mLayoutFromEnd ? StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() : StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            this.mOffset = n;
        }

        void assignCoordinateFromPadding(int n) {
            if (this.mLayoutFromEnd) {
                this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() - n;
                return;
            }
            this.mOffset = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding() + n;
        }

        void reset() {
            this.mPosition = -1;
            this.mOffset = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mInvalidateOffsets = false;
            this.mValid = false;
            int[] arrn = this.mSpanReferenceLines;
            if (arrn == null) return;
            Arrays.fill(arrn, -1);
        }

        void saveSpanReferenceLines(Span[] arrspan) {
            int n = arrspan.length;
            int[] arrn = this.mSpanReferenceLines;
            if (arrn == null || arrn.length < n) {
                this.mSpanReferenceLines = new int[StaggeredGridLayoutManager.this.mSpans.length];
            }
            int n2 = 0;
            while (n2 < n) {
                this.mSpanReferenceLines[n2] = arrspan[n2].getStartLine(Integer.MIN_VALUE);
                ++n2;
            }
        }
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        boolean mFullSpan;
        Span mSpan;

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

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public final int getSpanIndex() {
            Span span = this.mSpan;
            if (span != null) return span.mIndex;
            return -1;
        }

        public boolean isFullSpan() {
            return this.mFullSpan;
        }

        public void setFullSpan(boolean bl) {
            this.mFullSpan = bl;
        }
    }

    static class LazySpanLookup {
        private static final int MIN_SIZE = 10;
        int[] mData;
        List<FullSpanItem> mFullSpanItems;

        LazySpanLookup() {
        }

        private int invalidateFullSpansAfter(int n) {
            int n2;
            FullSpanItem fullSpanItem;
            block4 : {
                if (this.mFullSpanItems == null) {
                    return -1;
                }
                fullSpanItem = this.getFullSpanItem(n);
                if (fullSpanItem != null) {
                    this.mFullSpanItems.remove(fullSpanItem);
                }
                int n3 = this.mFullSpanItems.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    if (this.mFullSpanItems.get((int)n2).mPosition < n) {
                        continue;
                    }
                    break block4;
                }
                n2 = -1;
            }
            if (n2 == -1) return -1;
            fullSpanItem = this.mFullSpanItems.get(n2);
            this.mFullSpanItems.remove(n2);
            return fullSpanItem.mPosition;
        }

        private void offsetFullSpansForAddition(int n, int n2) {
            List<FullSpanItem> list = this.mFullSpanItems;
            if (list == null) {
                return;
            }
            int n3 = list.size() - 1;
            while (n3 >= 0) {
                list = this.mFullSpanItems.get(n3);
                if (((FullSpanItem)list).mPosition >= n) {
                    ((FullSpanItem)list).mPosition += n2;
                }
                --n3;
            }
        }

        private void offsetFullSpansForRemoval(int n, int n2) {
            List<FullSpanItem> list = this.mFullSpanItems;
            if (list == null) {
                return;
            }
            int n3 = list.size() - 1;
            while (n3 >= 0) {
                list = this.mFullSpanItems.get(n3);
                if (((FullSpanItem)list).mPosition >= n) {
                    if (((FullSpanItem)list).mPosition < n + n2) {
                        this.mFullSpanItems.remove(n3);
                    } else {
                        ((FullSpanItem)list).mPosition -= n2;
                    }
                }
                --n3;
            }
        }

        public void addFullSpanItem(FullSpanItem fullSpanItem) {
            if (this.mFullSpanItems == null) {
                this.mFullSpanItems = new ArrayList<FullSpanItem>();
            }
            int n = this.mFullSpanItems.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mFullSpanItems.add(fullSpanItem);
                    return;
                }
                FullSpanItem fullSpanItem2 = this.mFullSpanItems.get(n2);
                if (fullSpanItem2.mPosition == fullSpanItem.mPosition) {
                    this.mFullSpanItems.remove(n2);
                }
                if (fullSpanItem2.mPosition >= fullSpanItem.mPosition) {
                    this.mFullSpanItems.add(n2, fullSpanItem);
                    return;
                }
                ++n2;
            } while (true);
        }

        void clear() {
            int[] arrn = this.mData;
            if (arrn != null) {
                Arrays.fill(arrn, -1);
            }
            this.mFullSpanItems = null;
        }

        void ensureSize(int n) {
            int[] arrn = this.mData;
            if (arrn == null) {
                this.mData = arrn = new int[Math.max(n, 10) + 1];
                Arrays.fill(arrn, -1);
                return;
            }
            if (n < arrn.length) return;
            int[] arrn2 = new int[this.sizeForPosition(n)];
            this.mData = arrn2;
            System.arraycopy(arrn, 0, arrn2, 0, arrn.length);
            arrn2 = this.mData;
            Arrays.fill(arrn2, arrn.length, arrn2.length, -1);
        }

        int forceInvalidateAfter(int n) {
            List<FullSpanItem> list = this.mFullSpanItems;
            if (list == null) return this.invalidateAfter(n);
            int n2 = list.size() - 1;
            while (n2 >= 0) {
                if (this.mFullSpanItems.get((int)n2).mPosition >= n) {
                    this.mFullSpanItems.remove(n2);
                }
                --n2;
            }
            return this.invalidateAfter(n);
        }

        public FullSpanItem getFirstFullSpanItemInRange(int n, int n2, int n3, boolean bl) {
            List<FullSpanItem> list = this.mFullSpanItems;
            if (list == null) {
                return null;
            }
            int n4 = list.size();
            int n5 = 0;
            while (n5 < n4) {
                list = this.mFullSpanItems.get(n5);
                if (((FullSpanItem)list).mPosition >= n2) {
                    return null;
                }
                if (((FullSpanItem)list).mPosition >= n) {
                    if (n3 == 0) return list;
                    if (((FullSpanItem)list).mGapDir == n3) return list;
                    if (bl && ((FullSpanItem)list).mHasUnwantedGapAfter) {
                        return list;
                    }
                }
                ++n5;
            }
            return null;
        }

        public FullSpanItem getFullSpanItem(int n) {
            List<FullSpanItem> list = this.mFullSpanItems;
            if (list == null) {
                return null;
            }
            int n2 = list.size() - 1;
            while (n2 >= 0) {
                list = this.mFullSpanItems.get(n2);
                if (((FullSpanItem)list).mPosition == n) {
                    return list;
                }
                --n2;
            }
            return null;
        }

        int getSpan(int n) {
            int[] arrn = this.mData;
            if (arrn == null) return -1;
            if (n < arrn.length) return arrn[n];
            return -1;
        }

        int invalidateAfter(int n) {
            int[] arrn = this.mData;
            if (arrn == null) {
                return -1;
            }
            if (n >= arrn.length) {
                return -1;
            }
            int n2 = this.invalidateFullSpansAfter(n);
            if (n2 == -1) {
                arrn = this.mData;
                Arrays.fill(arrn, n, arrn.length, -1);
                return this.mData.length;
            }
            arrn = this.mData;
            Arrays.fill(arrn, n, ++n2, -1);
            return n2;
        }

        void offsetForAddition(int n, int n2) {
            int[] arrn = this.mData;
            if (arrn == null) return;
            if (n >= arrn.length) {
                return;
            }
            int n3 = n + n2;
            this.ensureSize(n3);
            arrn = this.mData;
            System.arraycopy(arrn, n, arrn, n3, arrn.length - n - n2);
            Arrays.fill(this.mData, n, n3, -1);
            this.offsetFullSpansForAddition(n, n2);
        }

        void offsetForRemoval(int n, int n2) {
            int[] arrn = this.mData;
            if (arrn == null) return;
            if (n >= arrn.length) {
                return;
            }
            int n3 = n + n2;
            this.ensureSize(n3);
            arrn = this.mData;
            System.arraycopy(arrn, n3, arrn, n, arrn.length - n - n2);
            arrn = this.mData;
            Arrays.fill(arrn, arrn.length - n2, arrn.length, -1);
            this.offsetFullSpansForRemoval(n, n2);
        }

        void setSpan(int n, Span span) {
            this.ensureSize(n);
            this.mData[n] = span.mIndex;
        }

        int sizeForPosition(int n) {
            int n2 = this.mData.length;
            while (n2 <= n) {
                n2 *= 2;
            }
            return n2;
        }

        static class FullSpanItem
        implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>(){

                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                public FullSpanItem[] newArray(int n) {
                    return new FullSpanItem[n];
                }
            };
            int mGapDir;
            int[] mGapPerSpan;
            boolean mHasUnwantedGapAfter;
            int mPosition;

            FullSpanItem() {
            }

            FullSpanItem(Parcel parcel) {
                this.mPosition = parcel.readInt();
                this.mGapDir = parcel.readInt();
                int n = parcel.readInt();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                this.mHasUnwantedGapAfter = bl;
                n = parcel.readInt();
                if (n <= 0) return;
                int[] arrn = new int[n];
                this.mGapPerSpan = arrn;
                parcel.readIntArray(arrn);
            }

            public int describeContents() {
                return 0;
            }

            int getGapForSpan(int n) {
                int[] arrn = this.mGapPerSpan;
                if (arrn != null) return arrn[n];
                return 0;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FullSpanItem{mPosition=");
                stringBuilder.append(this.mPosition);
                stringBuilder.append(", mGapDir=");
                stringBuilder.append(this.mGapDir);
                stringBuilder.append(", mHasUnwantedGapAfter=");
                stringBuilder.append(this.mHasUnwantedGapAfter);
                stringBuilder.append(", mGapPerSpan=");
                stringBuilder.append(Arrays.toString(this.mGapPerSpan));
                stringBuilder.append('}');
                return stringBuilder.toString();
            }

            public void writeToParcel(Parcel parcel, int n) {
                parcel.writeInt(this.mPosition);
                parcel.writeInt(this.mGapDir);
                parcel.writeInt((int)this.mHasUnwantedGapAfter);
                int[] arrn = this.mGapPerSpan;
                if (arrn != null && arrn.length > 0) {
                    parcel.writeInt(arrn.length);
                    parcel.writeIntArray(this.mGapPerSpan);
                    return;
                }
                parcel.writeInt(0);
            }

        }

    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorPosition;
        List<LazySpanLookup.FullSpanItem> mFullSpanItems;
        boolean mLastLayoutRTL;
        boolean mReverseLayout;
        int[] mSpanLookup;
        int mSpanLookupSize;
        int[] mSpanOffsets;
        int mSpanOffsetsSize;
        int mVisibleAnchorPosition;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            int n;
            int[] arrn;
            this.mAnchorPosition = parcel.readInt();
            this.mVisibleAnchorPosition = parcel.readInt();
            this.mSpanOffsetsSize = n = parcel.readInt();
            if (n > 0) {
                arrn = new int[n];
                this.mSpanOffsets = arrn;
                parcel.readIntArray(arrn);
            }
            this.mSpanLookupSize = n = parcel.readInt();
            if (n > 0) {
                arrn = new int[n];
                this.mSpanLookup = arrn;
                parcel.readIntArray(arrn);
            }
            n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n == 1;
            this.mReverseLayout = bl2;
            bl2 = parcel.readInt() == 1;
            this.mAnchorLayoutFromEnd = bl2;
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.mLastLayoutRTL = bl2;
            this.mFullSpanItems = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.mSpanOffsetsSize = savedState.mSpanOffsetsSize;
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mVisibleAnchorPosition = savedState.mVisibleAnchorPosition;
            this.mSpanOffsets = savedState.mSpanOffsets;
            this.mSpanLookupSize = savedState.mSpanLookupSize;
            this.mSpanLookup = savedState.mSpanLookup;
            this.mReverseLayout = savedState.mReverseLayout;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
            this.mLastLayoutRTL = savedState.mLastLayoutRTL;
            this.mFullSpanItems = savedState.mFullSpanItems;
        }

        public int describeContents() {
            return 0;
        }

        void invalidateAnchorPositionInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mAnchorPosition = -1;
            this.mVisibleAnchorPosition = -1;
        }

        void invalidateSpanInfo() {
            this.mSpanOffsets = null;
            this.mSpanOffsetsSize = 0;
            this.mSpanLookupSize = 0;
            this.mSpanLookup = null;
            this.mFullSpanItems = null;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mVisibleAnchorPosition);
            parcel.writeInt(this.mSpanOffsetsSize);
            if (this.mSpanOffsetsSize > 0) {
                parcel.writeIntArray(this.mSpanOffsets);
            }
            parcel.writeInt(this.mSpanLookupSize);
            if (this.mSpanLookupSize > 0) {
                parcel.writeIntArray(this.mSpanLookup);
            }
            parcel.writeInt((int)this.mReverseLayout);
            parcel.writeInt((int)this.mAnchorLayoutFromEnd);
            parcel.writeInt((int)this.mLastLayoutRTL);
            parcel.writeList(this.mFullSpanItems);
        }

    }

    class Span {
        static final int INVALID_LINE = Integer.MIN_VALUE;
        int mCachedEnd = Integer.MIN_VALUE;
        int mCachedStart = Integer.MIN_VALUE;
        int mDeletedSize = 0;
        final int mIndex;
        ArrayList<View> mViews = new ArrayList();

        Span(int n) {
            this.mIndex = n;
        }

        void appendToSpan(View view) {
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = this;
            this.mViews.add(view);
            this.mCachedEnd = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            if (!layoutParams.isItemRemoved()) {
                if (!layoutParams.isItemChanged()) return;
            }
            this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
        }

        void cacheReferenceLineAndClear(boolean bl, int n) {
            int n2 = bl ? this.getEndLine(Integer.MIN_VALUE) : this.getStartLine(Integer.MIN_VALUE);
            this.clear();
            if (n2 == Integer.MIN_VALUE) {
                return;
            }
            if (bl) {
                if (n2 < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding()) return;
            }
            if (!bl && n2 > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()) {
                return;
            }
            int n3 = n2;
            if (n != Integer.MIN_VALUE) {
                n3 = n2 + n;
            }
            this.mCachedEnd = n3;
            this.mCachedStart = n3;
        }

        void calculateCachedEnd() {
            Object object = this.mViews;
            object = ((ArrayList)object).get(((ArrayList)object).size() - 1);
            LayoutParams layoutParams = this.getLayoutParams((View)object);
            this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd((View)object);
            if (!layoutParams.mFullSpan) return;
            object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition());
            if (object == null) return;
            if (((LazySpanLookup.FullSpanItem)object).mGapDir != 1) return;
            this.mCachedEnd += ((LazySpanLookup.FullSpanItem)object).getGapForSpan(this.mIndex);
        }

        void calculateCachedStart() {
            View view = this.mViews.get(0);
            Object object = this.getLayoutParams(view);
            this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
            if (!((LayoutParams)object).mFullSpan) return;
            if ((object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(((RecyclerView.LayoutParams)((Object)object)).getViewLayoutPosition())) == null) return;
            if (((LazySpanLookup.FullSpanItem)object).mGapDir != -1) return;
            this.mCachedStart -= ((LazySpanLookup.FullSpanItem)object).getGapForSpan(this.mIndex);
        }

        void clear() {
            this.mViews.clear();
            this.invalidateCache();
            this.mDeletedSize = 0;
        }

        public int findFirstCompletelyVisibleItemPosition() {
            if (!StaggeredGridLayoutManager.this.mReverseLayout) return this.findOneVisibleChild(0, this.mViews.size(), true);
            return this.findOneVisibleChild(this.mViews.size() - 1, -1, true);
        }

        public int findFirstPartiallyVisibleItemPosition() {
            if (!StaggeredGridLayoutManager.this.mReverseLayout) return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
            return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
        }

        public int findFirstVisibleItemPosition() {
            if (!StaggeredGridLayoutManager.this.mReverseLayout) return this.findOneVisibleChild(0, this.mViews.size(), false);
            return this.findOneVisibleChild(this.mViews.size() - 1, -1, false);
        }

        public int findLastCompletelyVisibleItemPosition() {
            if (!StaggeredGridLayoutManager.this.mReverseLayout) return this.findOneVisibleChild(this.mViews.size() - 1, -1, true);
            return this.findOneVisibleChild(0, this.mViews.size(), true);
        }

        public int findLastPartiallyVisibleItemPosition() {
            if (!StaggeredGridLayoutManager.this.mReverseLayout) return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
            return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
        }

        public int findLastVisibleItemPosition() {
            if (!StaggeredGridLayoutManager.this.mReverseLayout) return this.findOneVisibleChild(this.mViews.size() - 1, -1, false);
            return this.findOneVisibleChild(0, this.mViews.size(), false);
        }

        int findOnePartiallyOrCompletelyVisibleChild(int n, int n2, boolean bl, boolean bl2, boolean bl3) {
            int n3 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
            int n4 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
            int n5 = n2 > n ? 1 : -1;
            while (n != n2) {
                View view = this.mViews.get(n);
                int n6 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
                int n7 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
                boolean bl4 = false;
                boolean bl5 = bl3 ? n6 <= n4 : n6 < n4;
                if (bl3 ? n7 >= n3 : n7 > n3) {
                    bl4 = true;
                }
                if (bl5 && bl4) {
                    if (bl && bl2) {
                        if (n6 >= n3 && n7 <= n4) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    } else {
                        if (bl2) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                        if (n6 < n3) return StaggeredGridLayoutManager.this.getPosition(view);
                        if (n7 > n4) {
                            return StaggeredGridLayoutManager.this.getPosition(view);
                        }
                    }
                }
                n += n5;
            }
            return -1;
        }

        int findOnePartiallyVisibleChild(int n, int n2, boolean bl) {
            return this.findOnePartiallyOrCompletelyVisibleChild(n, n2, false, false, bl);
        }

        int findOneVisibleChild(int n, int n2, boolean bl) {
            return this.findOnePartiallyOrCompletelyVisibleChild(n, n2, bl, true, false);
        }

        public int getDeletedSize() {
            return this.mDeletedSize;
        }

        int getEndLine() {
            int n = this.mCachedEnd;
            if (n != Integer.MIN_VALUE) {
                return n;
            }
            this.calculateCachedEnd();
            return this.mCachedEnd;
        }

        int getEndLine(int n) {
            int n2 = this.mCachedEnd;
            if (n2 != Integer.MIN_VALUE) {
                return n2;
            }
            if (this.mViews.size() == 0) {
                return n;
            }
            this.calculateCachedEnd();
            return this.mCachedEnd;
        }

        public View getFocusableViewAfter(int n, int n2) {
            View view = null;
            View view2 = null;
            if (n2 == -1) {
                int n3 = this.mViews.size();
                n2 = 0;
                do {
                    view = view2;
                    if (n2 >= n3) return view;
                    View view3 = this.mViews.get(n2);
                    if (StaggeredGridLayoutManager.this.mReverseLayout) {
                        view = view2;
                        if (StaggeredGridLayoutManager.this.getPosition(view3) <= n) return view;
                    }
                    if (!StaggeredGridLayoutManager.this.mReverseLayout && StaggeredGridLayoutManager.this.getPosition(view3) >= n) {
                        return view2;
                    }
                    view = view2;
                    if (!view3.hasFocusable()) return view;
                    ++n2;
                    view2 = view3;
                } while (true);
            }
            n2 = this.mViews.size() - 1;
            view2 = view;
            do {
                view = view2;
                if (n2 < 0) return view;
                View view4 = this.mViews.get(n2);
                if (StaggeredGridLayoutManager.this.mReverseLayout) {
                    view = view2;
                    if (StaggeredGridLayoutManager.this.getPosition(view4) >= n) return view;
                }
                if (!StaggeredGridLayoutManager.this.mReverseLayout && StaggeredGridLayoutManager.this.getPosition(view4) <= n) {
                    return view2;
                }
                view = view2;
                if (!view4.hasFocusable()) return view;
                --n2;
                view2 = view4;
            } while (true);
        }

        LayoutParams getLayoutParams(View view) {
            return (LayoutParams)view.getLayoutParams();
        }

        int getStartLine() {
            int n = this.mCachedStart;
            if (n != Integer.MIN_VALUE) {
                return n;
            }
            this.calculateCachedStart();
            return this.mCachedStart;
        }

        int getStartLine(int n) {
            int n2 = this.mCachedStart;
            if (n2 != Integer.MIN_VALUE) {
                return n2;
            }
            if (this.mViews.size() == 0) {
                return n;
            }
            this.calculateCachedStart();
            return this.mCachedStart;
        }

        void invalidateCache() {
            this.mCachedStart = Integer.MIN_VALUE;
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void onOffset(int n) {
            int n2 = this.mCachedStart;
            if (n2 != Integer.MIN_VALUE) {
                this.mCachedStart = n2 + n;
            }
            if ((n2 = this.mCachedEnd) == Integer.MIN_VALUE) return;
            this.mCachedEnd = n2 + n;
        }

        void popEnd() {
            int n = this.mViews.size();
            View view = this.mViews.remove(n - 1);
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = null;
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            if (n == 1) {
                this.mCachedStart = Integer.MIN_VALUE;
            }
            this.mCachedEnd = Integer.MIN_VALUE;
        }

        void popStart() {
            View view = this.mViews.remove(0);
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = null;
            if (this.mViews.size() == 0) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
            }
            this.mCachedStart = Integer.MIN_VALUE;
        }

        void prependToSpan(View view) {
            LayoutParams layoutParams = this.getLayoutParams(view);
            layoutParams.mSpan = this;
            this.mViews.add(0, view);
            this.mCachedStart = Integer.MIN_VALUE;
            if (this.mViews.size() == 1) {
                this.mCachedEnd = Integer.MIN_VALUE;
            }
            if (!layoutParams.isItemRemoved()) {
                if (!layoutParams.isItemChanged()) return;
            }
            this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
        }

        void setLine(int n) {
            this.mCachedStart = n;
            this.mCachedEnd = n;
        }
    }

}

