/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class PagerSnapHelper
extends SnapHelper {
    private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;

    private int distanceToCenter(RecyclerView.LayoutManager layoutManager, View view, OrientationHelper orientationHelper) {
        return orientationHelper.getDecoratedStart(view) + orientationHelper.getDecoratedMeasurement(view) / 2 - (orientationHelper.getStartAfterPadding() + orientationHelper.getTotalSpace() / 2);
    }

    private View findCenterView(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int n = layoutManager.getChildCount();
        View view = null;
        if (n == 0) {
            return null;
        }
        int n2 = orientationHelper.getStartAfterPadding();
        int n3 = orientationHelper.getTotalSpace() / 2;
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        while (n5 < n) {
            View view2 = layoutManager.getChildAt(n5);
            int n6 = Math.abs(orientationHelper.getDecoratedStart(view2) + orientationHelper.getDecoratedMeasurement(view2) / 2 - (n2 + n3));
            int n7 = n4;
            if (n6 < n4) {
                view = view2;
                n7 = n6;
            }
            ++n5;
            n4 = n7;
        }
        return view;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mHorizontalHelper;
        if (orientationHelper != null) {
            if (orientationHelper.mLayoutManager == layoutManager) return this.mHorizontalHelper;
        }
        this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        return this.mHorizontalHelper;
    }

    private OrientationHelper getOrientationHelper(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return this.getVerticalHelper(layoutManager);
        }
        if (!layoutManager.canScrollHorizontally()) return null;
        return this.getHorizontalHelper(layoutManager);
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mVerticalHelper;
        if (orientationHelper != null) {
            if (orientationHelper.mLayoutManager == layoutManager) return this.mVerticalHelper;
        }
        this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        return this.mVerticalHelper;
    }

    private boolean isForwardFling(RecyclerView.LayoutManager layoutManager, int n, int n2) {
        boolean bl = layoutManager.canScrollHorizontally();
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (n <= 0) return false;
            return bl3;
        }
        if (n2 <= 0) return false;
        return bl2;
    }

    private boolean isReverseLayout(RecyclerView.LayoutManager layoutManager) {
        boolean bl;
        int n = layoutManager.getItemCount();
        boolean bl2 = layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        layoutManager = ((RecyclerView.SmoothScroller.ScrollVectorProvider)((Object)layoutManager)).computeScrollVectorForPosition(n - 1);
        bl3 = bl;
        if (layoutManager == null) return bl3;
        if (((PointF)layoutManager).x < 0.0f) return true;
        bl3 = bl;
        if (!(((PointF)layoutManager).y < 0.0f)) return bl3;
        return true;
    }

    @Override
    public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager layoutManager, View view) {
        int[] arrn = new int[2];
        arrn[0] = layoutManager.canScrollHorizontally() ? this.distanceToCenter(layoutManager, view, this.getHorizontalHelper(layoutManager)) : 0;
        if (layoutManager.canScrollVertically()) {
            arrn[1] = this.distanceToCenter(layoutManager, view, this.getVerticalHelper(layoutManager));
            return arrn;
        }
        arrn[1] = 0;
        return arrn;
    }

    @Override
    protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) return new LinearSmoothScroller(this.mRecyclerView.getContext()){

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / (float)displayMetrics.densityDpi;
            }

            @Override
            protected int calculateTimeForScrolling(int n) {
                return Math.min(100, super.calculateTimeForScrolling(n));
            }

            @Override
            protected void onTargetFound(View arrn, RecyclerView.State object, RecyclerView.SmoothScroller.Action action) {
                object = PagerSnapHelper.this;
                arrn = ((PagerSnapHelper)object).calculateDistanceToFinalSnap(((PagerSnapHelper)object).mRecyclerView.getLayoutManager(), (View)arrn);
                int n = arrn[0];
                int n2 = arrn[1];
                int n3 = this.calculateTimeForDeceleration(Math.max(Math.abs(n), Math.abs(n2)));
                if (n3 <= 0) return;
                action.update(n, n2, n3, (Interpolator)this.mDecelerateInterpolator);
            }
        };
        return null;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return this.findCenterView(layoutManager, this.getVerticalHelper(layoutManager));
        }
        if (!layoutManager.canScrollHorizontally()) return null;
        return this.findCenterView(layoutManager, this.getHorizontalHelper(layoutManager));
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int n, int n2) {
        int n3 = layoutManager.getItemCount();
        if (n3 == 0) {
            return -1;
        }
        OrientationHelper orientationHelper = this.getOrientationHelper(layoutManager);
        if (orientationHelper == null) {
            return -1;
        }
        int n4 = Integer.MIN_VALUE;
        int n5 = Integer.MAX_VALUE;
        int n6 = layoutManager.getChildCount();
        View view = null;
        View view2 = null;
        for (int i = 0; i < n6; ++i) {
            View view3;
            int n7;
            View view4 = layoutManager.getChildAt(i);
            if (view4 == null) {
                n7 = n5;
                view3 = view;
            } else {
                int n8 = this.distanceToCenter(layoutManager, view4, orientationHelper);
                int n9 = n4;
                View view5 = view2;
                if (n8 <= 0) {
                    n9 = n4;
                    view5 = view2;
                    if (n8 > n4) {
                        view5 = view4;
                        n9 = n8;
                    }
                }
                n4 = n9;
                n7 = n5;
                view3 = view;
                view2 = view5;
                if (n8 >= 0) {
                    n4 = n9;
                    n7 = n5;
                    view3 = view;
                    view2 = view5;
                    if (n8 < n5) {
                        n7 = n8;
                        view2 = view5;
                        view3 = view4;
                        n4 = n9;
                    }
                }
            }
            n5 = n7;
            view = view3;
        }
        boolean bl = this.isForwardFling(layoutManager, n, n2);
        if (bl && view != null) {
            return layoutManager.getPosition(view);
        }
        if (!bl && view2 != null) {
            return layoutManager.getPosition(view2);
        }
        if (bl) {
            view = view2;
        }
        if (view == null) {
            return -1;
        }
        n2 = layoutManager.getPosition(view);
        n = this.isReverseLayout(layoutManager) == bl ? -1 : 1;
        n = n2 + n;
        if (n < 0) return -1;
        if (n < n3) return n;
        return -1;
    }

}

