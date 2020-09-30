/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.Scroller
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SnapHelper
extends RecyclerView.OnFlingListener {
    static final float MILLISECONDS_PER_INCH = 100.0f;
    private Scroller mGravityScroller;
    RecyclerView mRecyclerView;
    private final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener(){
        boolean mScrolled = false;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int n) {
            super.onScrollStateChanged(recyclerView, n);
            if (n != 0) return;
            if (!this.mScrolled) return;
            this.mScrolled = false;
            SnapHelper.this.snapToTargetExistingView();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int n, int n2) {
            if (n == 0) {
                if (n2 == 0) return;
            }
            this.mScrolled = true;
        }
    };

    private void destroyCallbacks() {
        this.mRecyclerView.removeOnScrollListener(this.mScrollListener);
        this.mRecyclerView.setOnFlingListener(null);
    }

    private void setupCallbacks() throws IllegalStateException {
        if (this.mRecyclerView.getOnFlingListener() != null) throw new IllegalStateException("An instance of OnFlingListener already set.");
        this.mRecyclerView.addOnScrollListener(this.mScrollListener);
        this.mRecyclerView.setOnFlingListener(this);
    }

    private boolean snapFromFling(RecyclerView.LayoutManager layoutManager, int n, int n2) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return false;
        }
        RecyclerView.SmoothScroller smoothScroller = this.createScroller(layoutManager);
        if (smoothScroller == null) {
            return false;
        }
        if ((n = this.findTargetSnapPosition(layoutManager, n, n2)) == -1) {
            return false;
        }
        smoothScroller.setTargetPosition(n);
        layoutManager.startSmoothScroll(smoothScroller);
        return true;
    }

    public void attachToRecyclerView(RecyclerView recyclerView) throws IllegalStateException {
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            this.destroyCallbacks();
        }
        this.mRecyclerView = recyclerView;
        if (recyclerView == null) return;
        this.setupCallbacks();
        this.mGravityScroller = new Scroller(this.mRecyclerView.getContext(), (Interpolator)new DecelerateInterpolator());
        this.snapToTargetExistingView();
    }

    public abstract int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager var1, View var2);

    public int[] calculateScrollDistance(int n, int n2) {
        this.mGravityScroller.fling(0, 0, n, n2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[]{this.mGravityScroller.getFinalX(), this.mGravityScroller.getFinalY()};
    }

    protected RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
        return this.createSnapScroller(layoutManager);
    }

    @Deprecated
    protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) return new LinearSmoothScroller(this.mRecyclerView.getContext()){

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 100.0f / (float)displayMetrics.densityDpi;
            }

            @Override
            protected void onTargetFound(View arrn, RecyclerView.State object, RecyclerView.SmoothScroller.Action action) {
                if (SnapHelper.this.mRecyclerView == null) {
                    return;
                }
                object = SnapHelper.this;
                arrn = ((SnapHelper)object).calculateDistanceToFinalSnap(((SnapHelper)object).mRecyclerView.getLayoutManager(), (View)arrn);
                int n = arrn[0];
                int n2 = arrn[1];
                int n3 = this.calculateTimeForDeceleration(Math.max(Math.abs(n), Math.abs(n2)));
                if (n3 <= 0) return;
                action.update(n, n2, n3, (Interpolator)this.mDecelerateInterpolator);
            }
        };
        return null;
    }

    public abstract View findSnapView(RecyclerView.LayoutManager var1);

    public abstract int findTargetSnapPosition(RecyclerView.LayoutManager var1, int var2, int var3);

    @Override
    public boolean onFling(int n, int n2) {
        boolean bl;
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        boolean bl2 = false;
        if (layoutManager == null) {
            return false;
        }
        if (this.mRecyclerView.getAdapter() == null) {
            return false;
        }
        int n3 = this.mRecyclerView.getMinFlingVelocity();
        if (Math.abs(n2) <= n3) {
            bl = bl2;
            if (Math.abs(n) <= n3) return bl;
        }
        bl = bl2;
        if (!this.snapFromFling(layoutManager, n, n2)) return bl;
        return true;
    }

    void snapToTargetExistingView() {
        Object object = this.mRecyclerView;
        if (object == null) {
            return;
        }
        if ((object = object.getLayoutManager()) == null) {
            return;
        }
        View view = this.findSnapView((RecyclerView.LayoutManager)object);
        if (view == null) {
            return;
        }
        if ((object = this.calculateDistanceToFinalSnap((RecyclerView.LayoutManager)object, view))[0] == 0) {
            if (object[1] == 0) return;
        }
        this.mRecyclerView.smoothScrollBy(object[0], object[1]);
    }

}

