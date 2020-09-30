/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class GridLayoutManager
extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets = new Rect();
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();
    private boolean mUsingSpansToEstimateScrollBarDimensions;

    public GridLayoutManager(Context context, int n) {
        super(context);
        this.setSpanCount(n);
    }

    public GridLayoutManager(Context context, int n, int n2, boolean bl) {
        super(context, n2, bl);
        this.setSpanCount(n);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.setSpanCount(GridLayoutManager.getProperties((Context)context, (AttributeSet)attributeSet, (int)n, (int)n2).spanCount);
    }

    private void assignSpans(RecyclerView.Recycler recycler, RecyclerView.State state, int n, boolean bl) {
        int n2;
        int n3 = 0;
        int n4 = -1;
        if (bl) {
            int n5 = 0;
            n2 = 1;
            n4 = n;
            n = n5;
        } else {
            --n;
            n2 = -1;
        }
        while (n != n4) {
            View view = this.mSet[n];
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            layoutParams.mSpanSize = this.getSpanSize(recycler, state, this.getPosition(view));
            layoutParams.mSpanIndex = n3;
            n3 += layoutParams.mSpanSize;
            n += n2;
        }
    }

    private void cachePreLayoutSpanMapping() {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(n2).getLayoutParams();
            int n3 = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(n3, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(n3, layoutParams.getSpanIndex());
            ++n2;
        }
    }

    private void calculateItemBorders(int n) {
        this.mCachedBorders = GridLayoutManager.calculateItemBorders(this.mCachedBorders, this.mSpanCount, n);
    }

    static int[] calculateItemBorders(int[] arrn, int n, int n2) {
        int[] arrn2;
        int n3;
        block7 : {
            block6 : {
                n3 = 1;
                if (arrn == null || arrn.length != n + 1) break block6;
                arrn2 = arrn;
                if (arrn[arrn.length - 1] == n2) break block7;
            }
            arrn2 = new int[n + 1];
        }
        int n4 = 0;
        arrn2[0] = 0;
        int n5 = n2 / n;
        int n6 = n2 % n;
        int n7 = 0;
        n2 = n4;
        while (n3 <= n) {
            if ((n2 += n6) > 0 && n - n2 < n6) {
                n4 = n5 + 1;
                n2 -= n;
            } else {
                n4 = n5;
            }
            arrn2[n3] = n7 += n4;
            ++n3;
        }
        return arrn2;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private int computeScrollOffsetWithSpanInfo(RecyclerView.State state) {
        if (this.getChildCount() == 0) return 0;
        if (state.getItemCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        boolean bl = this.isSmoothScrollbarEnabled();
        View view = this.findFirstVisibleChildClosestToStart(bl ^ true, true);
        View view2 = this.findFirstVisibleChildClosestToEnd(bl ^ true, true);
        if (view == null) return 0;
        if (view2 == null) {
            return 0;
        }
        int n = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(view), this.mSpanCount);
        int n2 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(view2), this.mSpanCount);
        int n3 = Math.min(n, n2);
        n = Math.max(n, n2);
        n2 = this.mSpanSizeLookup.getCachedSpanGroupIndex(state.getItemCount() - 1, this.mSpanCount);
        n3 = this.mShouldReverseLayout ? Math.max(0, n2 + 1 - n - 1) : Math.max(0, n3);
        if (!bl) {
            return n3;
        }
        n2 = Math.abs(this.mOrientationHelper.getDecoratedEnd(view2) - this.mOrientationHelper.getDecoratedStart(view));
        n = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(view), this.mSpanCount);
        int n4 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(view2), this.mSpanCount);
        float f = (float)n2 / (float)(n4 - n + 1);
        return Math.round((float)n3 * f + (float)(this.mOrientationHelper.getStartAfterPadding() - this.mOrientationHelper.getDecoratedStart(view)));
    }

    private int computeScrollRangeWithSpanInfo(RecyclerView.State state) {
        if (this.getChildCount() == 0) return 0;
        if (state.getItemCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        View view = this.findFirstVisibleChildClosestToStart(this.isSmoothScrollbarEnabled() ^ true, true);
        View view2 = this.findFirstVisibleChildClosestToEnd(this.isSmoothScrollbarEnabled() ^ true, true);
        if (view == null) return 0;
        if (view2 == null) {
            return 0;
        }
        if (!this.isSmoothScrollbarEnabled()) {
            return this.mSpanSizeLookup.getCachedSpanGroupIndex(state.getItemCount() - 1, this.mSpanCount) + 1;
        }
        int n = this.mOrientationHelper.getDecoratedEnd(view2);
        int n2 = this.mOrientationHelper.getDecoratedStart(view);
        int n3 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(view), this.mSpanCount);
        int n4 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(view2), this.mSpanCount);
        int n5 = this.mSpanSizeLookup.getCachedSpanGroupIndex(state.getItemCount() - 1, this.mSpanCount);
        return (int)((float)(n - n2) / (float)(n4 - n3 + 1) * (float)(n5 + 1));
    }

    private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n) {
        int n2;
        int n3;
        int n4 = n == 1 ? 1 : 0;
        n = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (n4 != 0) {
            while (n > 0) {
                if (anchorInfo.mPosition <= 0) return;
                --anchorInfo.mPosition;
                n = this.getSpanIndex(recycler, state, anchorInfo.mPosition);
            }
            return;
        }
        int n5 = state.getItemCount();
        n4 = anchorInfo.mPosition;
        while (n4 < n5 - 1 && (n3 = this.getSpanIndex(recycler, state, n2 = n4 + 1)) > n) {
            n4 = n2;
            n = n3;
        }
        anchorInfo.mPosition = n4;
    }

    private void ensureViewSet() {
        View[] arrview = this.mSet;
        if (arrview != null) {
            if (arrview.length == this.mSpanCount) return;
        }
        this.mSet = new View[this.mSpanCount];
    }

    private int getSpanGroupIndex(RecyclerView.Recycler object, RecyclerView.State state, int n) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanGroupIndex(n, this.mSpanCount);
        }
        int n2 = ((RecyclerView.Recycler)object).convertPreLayoutPositionToPostLayout(n);
        if (n2 != -1) return this.mSpanSizeLookup.getCachedSpanGroupIndex(n2, this.mSpanCount);
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot find span size for pre layout position. ");
        ((StringBuilder)object).append(n);
        Log.w((String)TAG, (String)((StringBuilder)object).toString());
        return 0;
    }

    private int getSpanIndex(RecyclerView.Recycler object, RecyclerView.State state, int n) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(n, this.mSpanCount);
        }
        int n2 = this.mPreLayoutSpanIndexCache.get(n, -1);
        if (n2 != -1) {
            return n2;
        }
        n2 = ((RecyclerView.Recycler)object).convertPreLayoutPositionToPostLayout(n);
        if (n2 != -1) return this.mSpanSizeLookup.getCachedSpanIndex(n2, this.mSpanCount);
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
        ((StringBuilder)object).append(n);
        Log.w((String)TAG, (String)((StringBuilder)object).toString());
        return 0;
    }

    private int getSpanSize(RecyclerView.Recycler object, RecyclerView.State state, int n) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(n);
        }
        int n2 = this.mPreLayoutSpanSizeCache.get(n, -1);
        if (n2 != -1) {
            return n2;
        }
        n2 = ((RecyclerView.Recycler)object).convertPreLayoutPositionToPostLayout(n);
        if (n2 != -1) return this.mSpanSizeLookup.getSpanSize(n2);
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
        ((StringBuilder)object).append(n);
        Log.w((String)TAG, (String)((StringBuilder)object).toString());
        return 1;
    }

    private void guessMeasurement(float f, int n) {
        this.calculateItemBorders(Math.max(Math.round(f * (float)this.mSpanCount), n));
    }

    private void measureChild(View view, int n, boolean bl) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int n2 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int n3 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int n4 = this.getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            n3 = GridLayoutManager.getChildMeasureSpec(n4, n, n3, layoutParams.width, false);
            n = GridLayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getHeightMode(), n2, layoutParams.height, true);
        } else {
            n = GridLayoutManager.getChildMeasureSpec(n4, n, n2, layoutParams.height, false);
            n3 = GridLayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getWidthMode(), n3, layoutParams.width, true);
        }
        this.measureChildWithDecorationsAndMargin(view, n3, n, bl);
    }

    private void measureChildWithDecorationsAndMargin(View view, int n, int n2, boolean bl) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        bl = bl ? this.shouldReMeasureChild(view, n, n2, layoutParams) : this.shouldMeasureChild(view, n, n2, layoutParams);
        if (!bl) return;
        view.measure(n, n2);
    }

    private void updateMeasurements() {
        int n;
        int n2;
        if (this.getOrientation() == 1) {
            n = this.getWidth() - this.getPaddingRight();
            n2 = this.getPaddingLeft();
        } else {
            n = this.getHeight() - this.getPaddingBottom();
            n2 = this.getPaddingTop();
        }
        this.calculateItemBorders(n - n2);
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int n = this.mSpanCount;
        int n2 = 0;
        while (n2 < this.mSpanCount) {
            if (!layoutState.hasMore(state)) return;
            if (n <= 0) return;
            int n3 = layoutState.mCurrentPosition;
            layoutPrefetchRegistry.addPosition(n3, Math.max(0, layoutState.mScrollingOffset));
            n -= this.mSpanSizeLookup.getSpanSize(n3);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
            ++n2;
        }
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        if (!this.mUsingSpansToEstimateScrollBarDimensions) return super.computeHorizontalScrollOffset(state);
        return this.computeScrollOffsetWithSpanInfo(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        if (!this.mUsingSpansToEstimateScrollBarDimensions) return super.computeHorizontalScrollRange(state);
        return this.computeScrollRangeWithSpanInfo(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        if (!this.mUsingSpansToEstimateScrollBarDimensions) return super.computeVerticalScrollOffset(state);
        return this.computeScrollOffsetWithSpanInfo(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        if (!this.mUsingSpansToEstimateScrollBarDimensions) return super.computeVerticalScrollRange(state);
        return this.computeScrollRangeWithSpanInfo(state);
    }

    @Override
    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2, int n3) {
        this.ensureLayoutState();
        int n4 = this.mOrientationHelper.getStartAfterPadding();
        int n5 = this.mOrientationHelper.getEndAfterPadding();
        int n6 = n2 > n ? 1 : -1;
        View view = null;
        View view2 = null;
        do {
            if (n == n2) {
                if (view == null) return view2;
                return view;
            }
            View view3 = this.getChildAt(n);
            int n7 = this.getPosition(view3);
            View view4 = view;
            View view5 = view2;
            if (n7 >= 0) {
                view4 = view;
                view5 = view2;
                if (n7 < n3) {
                    if (this.getSpanIndex(recycler, state, n7) != 0) {
                        view4 = view;
                        view5 = view2;
                    } else if (((RecyclerView.LayoutParams)view3.getLayoutParams()).isItemRemoved()) {
                        view4 = view;
                        view5 = view2;
                        if (view2 == null) {
                            view5 = view3;
                            view4 = view;
                        }
                    } else {
                        if (this.mOrientationHelper.getDecoratedStart(view3) < n5) {
                            if (this.mOrientationHelper.getDecoratedEnd(view3) >= n4) return view3;
                        }
                        view4 = view;
                        view5 = view2;
                        if (view == null) {
                            view4 = view3;
                            view5 = view2;
                        }
                    }
                }
            }
            n += n6;
            view = view4;
            view2 = view5;
        } while (true);
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
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if (state.getItemCount() >= 1) return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
        return 0;
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() >= 1) return this.getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
        return 0;
    }

    int getSpaceForSpanRange(int n, int n2) {
        if (this.mOrientation == 1 && this.isLayoutRTL()) {
            int[] arrn = this.mCachedBorders;
            int n3 = this.mSpanCount;
            return arrn[n3 - n] - arrn[n3 - n - n2];
        }
        int[] arrn = this.mCachedBorders;
        return arrn[n2 + n] - arrn[n];
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    public boolean isUsingSpansToEstimateScrollbarDimensions() {
        return this.mUsingSpansToEstimateScrollBarDimensions;
    }

    @Override
    void layoutChunk(RecyclerView.Recycler object, RecyclerView.State object2, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        int n;
        int n2;
        int n3;
        View view;
        int n4;
        int n5 = this.mOrientationHelper.getModeInOther();
        int n6 = n5 != 1073741824 ? 1 : 0;
        int n7 = this.getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (n6 != 0) {
            this.updateMeasurements();
        }
        boolean bl = layoutState.mItemDirection == 1;
        int n8 = this.mSpanCount;
        if (!bl) {
            n8 = this.getSpanIndex((RecyclerView.Recycler)object, (RecyclerView.State)object2, layoutState.mCurrentPosition) + this.getSpanSize((RecyclerView.Recycler)object, (RecyclerView.State)object2, layoutState.mCurrentPosition);
        }
        for (n = 0; n < this.mSpanCount && layoutState.hasMore((RecyclerView.State)object2) && n8 > 0; ++n) {
            n4 = layoutState.mCurrentPosition;
            n3 = this.getSpanSize((RecyclerView.Recycler)object, (RecyclerView.State)object2, n4);
            if (n3 > this.mSpanCount) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Item at position ");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append(" requires ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(" spans but GridLayoutManager has only ");
                ((StringBuilder)object).append(this.mSpanCount);
                ((StringBuilder)object).append(" spans.");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if ((n8 -= n3) < 0 || (view = layoutState.next((RecyclerView.Recycler)object)) == null) break;
            this.mSet[n] = view;
        }
        if (n == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        float f = 0.0f;
        this.assignSpans((RecyclerView.Recycler)object, (RecyclerView.State)object2, n, bl);
        n8 = 0;
        for (n3 = 0; n3 < n; ++n3) {
            object = this.mSet[n3];
            if (layoutState.mScrapList == null) {
                if (bl) {
                    this.addView((View)object);
                } else {
                    this.addView((View)object, 0);
                }
            } else if (bl) {
                this.addDisappearingView((View)object);
            } else {
                this.addDisappearingView((View)object, 0);
            }
            this.calculateItemDecorationsForChild((View)object, this.mDecorInsets);
            this.measureChild((View)object, n5, false);
            n2 = this.mOrientationHelper.getDecoratedMeasurement((View)object);
            n4 = n8;
            if (n2 > n8) {
                n4 = n2;
            }
            object2 = (LayoutParams)object.getLayoutParams();
            float f2 = (float)this.mOrientationHelper.getDecoratedMeasurementInOther((View)object) * 1.0f / (float)((LayoutParams)object2).mSpanSize;
            float f3 = f;
            if (f2 > f) {
                f3 = f2;
            }
            n8 = n4;
            f = f3;
        }
        n4 = n8;
        if (n6 != 0) {
            this.guessMeasurement(f, n7);
            n6 = 0;
            n8 = 0;
            do {
                n4 = n8;
                if (n6 >= n) break;
                object = this.mSet[n6];
                this.measureChild((View)object, 1073741824, true);
                n7 = this.mOrientationHelper.getDecoratedMeasurement((View)object);
                n4 = n8;
                if (n7 > n8) {
                    n4 = n7;
                }
                ++n6;
                n8 = n4;
            } while (true);
        }
        for (n8 = 0; n8 < n; ++n8) {
            view = this.mSet[n8];
            if (this.mOrientationHelper.getDecoratedMeasurement(view) == n4) continue;
            object = (LayoutParams)view.getLayoutParams();
            object2 = ((LayoutParams)object).mDecorInsets;
            n6 = ((Rect)object2).top + ((Rect)object2).bottom + ((LayoutParams)object).topMargin + ((LayoutParams)object).bottomMargin;
            n7 = ((Rect)object2).left + ((Rect)object2).right + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin;
            n3 = this.getSpaceForSpanRange(((LayoutParams)object).mSpanIndex, ((LayoutParams)object).mSpanSize);
            if (this.mOrientation == 1) {
                n7 = GridLayoutManager.getChildMeasureSpec(n3, 1073741824, n7, ((LayoutParams)object).width, false);
                n6 = View.MeasureSpec.makeMeasureSpec((int)(n4 - n6), (int)1073741824);
            } else {
                n7 = View.MeasureSpec.makeMeasureSpec((int)(n4 - n7), (int)1073741824);
                n6 = GridLayoutManager.getChildMeasureSpec(n3, 1073741824, n6, ((LayoutParams)object).height, false);
            }
            this.measureChildWithDecorationsAndMargin(view, n7, n6, true);
        }
        n2 = 0;
        layoutChunkResult.mConsumed = n4;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                n8 = layoutState.mOffset;
                n6 = n8 - n4;
                n4 = n8;
                n8 = n6;
            } else {
                n8 = n6 = layoutState.mOffset;
                n4 += n6;
            }
            n7 = 0;
            n6 = 0;
        } else if (layoutState.mLayoutDirection == -1) {
            n7 = layoutState.mOffset;
            n6 = n7 - n4;
            n8 = 0;
            n4 = 0;
        } else {
            n6 = layoutState.mOffset;
            n7 = n4 + n6;
            n4 = 0;
            n8 = 0;
        }
        do {
            block32 : {
                block31 : {
                    block29 : {
                        block30 : {
                            if (n2 >= n) {
                                Arrays.fill((Object[])this.mSet, null);
                                return;
                            }
                            object = this.mSet[n2];
                            object2 = (LayoutParams)object.getLayoutParams();
                            if (this.mOrientation != 1) break block29;
                            if (!this.isLayoutRTL()) break block30;
                            n7 = this.getPaddingLeft() + this.mCachedBorders[this.mSpanCount - ((LayoutParams)object2).mSpanIndex];
                            n6 = n7 - this.mOrientationHelper.getDecoratedMeasurementInOther((View)object);
                            n3 = n8;
                            break block31;
                        }
                        n6 = this.getPaddingLeft() + this.mCachedBorders[((LayoutParams)object2).mSpanIndex];
                        n7 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)object);
                        n3 = n6;
                        n7 += n6;
                        n6 = n8;
                        n8 = n3;
                        break block32;
                    }
                    n8 = this.getPaddingTop() + this.mCachedBorders[((LayoutParams)object2).mSpanIndex];
                    n4 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)object);
                    n3 = n8;
                    n4 += n8;
                }
                n8 = n6;
                n6 = n3;
            }
            this.layoutDecoratedWithMargins((View)object, n8, n6, n7, n4);
            if (((RecyclerView.LayoutParams)((Object)object2)).isItemRemoved() || ((RecyclerView.LayoutParams)((Object)object2)).isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= object.hasFocusable();
            ++n2;
            n3 = n6;
            n6 = n8;
            n8 = n3;
        } while (true);
    }

    @Override
    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int n) {
        super.onAnchorReady(recycler, state, anchorInfo, n);
        this.updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            this.ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, n);
        }
        this.ensureViewSet();
    }

    /*
     * Exception decompiling
     */
    @Override
    public View onFocusSearchFailed(View var1_1, int var2_2, RecyclerView.Recycler var3_3, RecyclerView.State var4_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = object.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem((View)object, accessibilityNodeInfoCompat);
            return;
        }
        object = (LayoutParams)layoutParams;
        int n = this.getSpanGroupIndex(recycler, state, ((RecyclerView.LayoutParams)((Object)object)).getViewLayoutPosition());
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(((LayoutParams)((Object)object)).getSpanIndex(), ((LayoutParams)((Object)object)).getSpanSize(), n, 1, false, false));
            return;
        }
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n, 1, ((LayoutParams)((Object)object)).getSpanIndex(), ((LayoutParams)((Object)object)).getSpanSize(), false, false));
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            this.cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        this.clearPreLayoutSpanMappingCache();
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollHorizontallyBy(n, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.updateMeasurements();
        this.ensureViewSet();
        return super.scrollVerticallyBy(n, recycler, state);
    }

    @Override
    public void setMeasuredDimension(Rect arrn, int n, int n2) {
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension((Rect)arrn, n, n2);
        }
        int n3 = this.getPaddingLeft() + this.getPaddingRight();
        int n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (this.mOrientation == 1) {
            n2 = GridLayoutManager.chooseSize(n2, arrn.height() + n4, this.getMinimumHeight());
            arrn = this.mCachedBorders;
            n = GridLayoutManager.chooseSize(n, arrn[arrn.length - 1] + n3, this.getMinimumWidth());
        } else {
            n = GridLayoutManager.chooseSize(n, arrn.width() + n3, this.getMinimumWidth());
            arrn = this.mCachedBorders;
            n2 = GridLayoutManager.chooseSize(n2, arrn[arrn.length - 1] + n4, this.getMinimumHeight());
        }
        this.setMeasuredDimension(n, n2);
    }

    public void setSpanCount(int n) {
        if (n == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (n >= 1) {
            this.mSpanCount = n;
            this.mSpanSizeLookup.invalidateSpanIndexCache();
            this.requestLayout();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Span count should be at least 1. Provided ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    @Override
    public void setStackFromEnd(boolean bl) {
        if (bl) throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        super.setStackFromEnd(false);
    }

    public void setUsingSpansToEstimateScrollbarDimensions(boolean bl) {
        this.mUsingSpansToEstimateScrollBarDimensions = bl;
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState != null) return false;
        if (this.mPendingSpanCountChange) return false;
        return true;
    }

    public static final class DefaultSpanSizeLookup
    extends SpanSizeLookup {
        @Override
        public int getSpanIndex(int n, int n2) {
            return n % n2;
        }

        @Override
        public int getSpanSize(int n) {
            return 1;
        }
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        int mSpanIndex = -1;
        int mSpanSize = 0;

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

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanGroupIndices = false;
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        static int findFirstKeyLessThan(SparseIntArray sparseIntArray, int n) {
            int n2 = sparseIntArray.size() - 1;
            int n3 = 0;
            do {
                if (n3 > n2) {
                    n = n3 - 1;
                    if (n < 0) return -1;
                    if (n >= sparseIntArray.size()) return -1;
                    return sparseIntArray.keyAt(n);
                }
                int n4 = n3 + n2 >>> 1;
                if (sparseIntArray.keyAt(n4) < n) {
                    n3 = n4 + 1;
                    continue;
                }
                n2 = n4 - 1;
            } while (true);
        }

        int getCachedSpanGroupIndex(int n, int n2) {
            if (!this.mCacheSpanGroupIndices) {
                return this.getSpanGroupIndex(n, n2);
            }
            int n3 = this.mSpanGroupIndexCache.get(n, -1);
            if (n3 != -1) {
                return n3;
            }
            n2 = this.getSpanGroupIndex(n, n2);
            this.mSpanGroupIndexCache.put(n, n2);
            return n2;
        }

        int getCachedSpanIndex(int n, int n2) {
            if (!this.mCacheSpanIndices) {
                return this.getSpanIndex(n, n2);
            }
            int n3 = this.mSpanIndexCache.get(n, -1);
            if (n3 != -1) {
                return n3;
            }
            n2 = this.getSpanIndex(n, n2);
            this.mSpanIndexCache.put(n, n2);
            return n2;
        }

        public int getSpanGroupIndex(int n, int n2) {
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            block6 : {
                block7 : {
                    block5 : {
                        if (!this.mCacheSpanGroupIndices || (n7 = SpanSizeLookup.findFirstKeyLessThan(this.mSpanGroupIndexCache, n)) == -1) break block5;
                        n3 = this.mSpanGroupIndexCache.get(n7);
                        n6 = n7 + 1;
                        n5 = this.getCachedSpanIndex(n7, n2) + this.getSpanSize(n7);
                        n4 = n3;
                        n7 = n6;
                        n8 = n5;
                        if (n5 != n2) break block6;
                        n4 = n3 + 1;
                        n7 = n6;
                        break block7;
                    }
                    n4 = 0;
                    n7 = 0;
                }
                n8 = 0;
            }
            int n9 = this.getSpanSize(n);
            n6 = n7;
            do {
                if (n6 >= n) {
                    n = n4;
                    if (n8 + n9 <= n2) return n;
                    return n4 + 1;
                }
                n3 = this.getSpanSize(n6);
                n5 = n8 + n3;
                if (n5 == n2) {
                    n8 = n4 + 1;
                    n7 = 0;
                } else {
                    n8 = n4;
                    n7 = n5;
                    if (n5 > n2) {
                        n8 = n4 + 1;
                        n7 = n3;
                    }
                }
                ++n6;
                n4 = n8;
                n8 = n7;
            } while (true);
        }

        /*
         * Unable to fully structure code
         */
        public int getSpanIndex(int var1_1, int var2_2) {
            block6 : {
                var3_3 = this.getSpanSize(var1_1);
                if (var3_3 == var2_2) {
                    return 0;
                }
                if (!this.mCacheSpanIndices || (var4_4 = SpanSizeLookup.findFirstKeyLessThan(this.mSpanIndexCache, var1_1)) < 0) break block6;
                var5_5 = this.mSpanIndexCache.get(var4_4) + this.getSpanSize(var4_4);
                ** GOTO lbl25
            }
            var6_6 = 0;
            var5_5 = 0;
            do {
                if (var6_6 >= var1_1) {
                    if (var3_3 + var5_5 > var2_2) return 0;
                    return var5_5;
                }
                var7_7 = this.getSpanSize(var6_6);
                var8_8 = var5_5 + var7_7;
                if (var8_8 == var2_2) {
                    var5_5 = 0;
                    var4_4 = var6_6;
                } else {
                    var4_4 = var6_6;
                    var5_5 = var8_8;
                    if (var8_8 > var2_2) {
                        var5_5 = var7_7;
                        var4_4 = var6_6;
                    }
                }
lbl25: // 5 sources:
                var6_6 = var4_4 + 1;
            } while (true);
        }

        public abstract int getSpanSize(int var1);

        public void invalidateSpanGroupIndexCache() {
            this.mSpanGroupIndexCache.clear();
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanGroupIndexCacheEnabled() {
            return this.mCacheSpanGroupIndices;
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        public void setSpanGroupIndexCacheEnabled(boolean bl) {
            if (!bl) {
                this.mSpanGroupIndexCache.clear();
            }
            this.mCacheSpanGroupIndices = bl;
        }

        public void setSpanIndexCacheEnabled(boolean bl) {
            if (!bl) {
                this.mSpanGroupIndexCache.clear();
            }
            this.mCacheSpanIndices = bl;
        }
    }

}

