/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package androidx.appcompat.view;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewPropertyAnimatorCompatSet {
    final ArrayList<ViewPropertyAnimatorCompat> mAnimators = new ArrayList();
    private long mDuration = -1L;
    private Interpolator mInterpolator;
    private boolean mIsStarted;
    ViewPropertyAnimatorListener mListener;
    private final ViewPropertyAnimatorListenerAdapter mProxyListener = new ViewPropertyAnimatorListenerAdapter(){
        private int mProxyEndCount = 0;
        private boolean mProxyStarted = false;

        @Override
        public void onAnimationEnd(View view) {
            int n;
            this.mProxyEndCount = n = this.mProxyEndCount + 1;
            if (n != ViewPropertyAnimatorCompatSet.this.mAnimators.size()) return;
            if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
                ViewPropertyAnimatorCompatSet.this.mListener.onAnimationEnd(null);
            }
            this.onEnd();
        }

        @Override
        public void onAnimationStart(View view) {
            if (this.mProxyStarted) {
                return;
            }
            this.mProxyStarted = true;
            if (ViewPropertyAnimatorCompatSet.this.mListener == null) return;
            ViewPropertyAnimatorCompatSet.this.mListener.onAnimationStart(null);
        }

        void onEnd() {
            this.mProxyEndCount = 0;
            this.mProxyStarted = false;
            ViewPropertyAnimatorCompatSet.this.onAnimationsEnded();
        }
    };

    public void cancel() {
        if (!this.mIsStarted) {
            return;
        }
        Iterator<ViewPropertyAnimatorCompat> iterator2 = this.mAnimators.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.mIsStarted = false;
                return;
            }
            iterator2.next().cancel();
        } while (true);
    }

    void onAnimationsEnded() {
        this.mIsStarted = false;
    }

    public ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (this.mIsStarted) return this;
        this.mAnimators.add(viewPropertyAnimatorCompat);
        return this;
    }

    public ViewPropertyAnimatorCompatSet playSequentially(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2) {
        this.mAnimators.add(viewPropertyAnimatorCompat);
        viewPropertyAnimatorCompat2.setStartDelay(viewPropertyAnimatorCompat.getDuration());
        this.mAnimators.add(viewPropertyAnimatorCompat2);
        return this;
    }

    public ViewPropertyAnimatorCompatSet setDuration(long l) {
        if (this.mIsStarted) return this;
        this.mDuration = l;
        return this;
    }

    public ViewPropertyAnimatorCompatSet setInterpolator(Interpolator interpolator2) {
        if (this.mIsStarted) return this;
        this.mInterpolator = interpolator2;
        return this;
    }

    public ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (this.mIsStarted) return this;
        this.mListener = viewPropertyAnimatorListener;
        return this;
    }

    public void start() {
        if (this.mIsStarted) {
            return;
        }
        Iterator<ViewPropertyAnimatorCompat> iterator2 = this.mAnimators.iterator();
        do {
            Interpolator interpolator2;
            if (!iterator2.hasNext()) {
                this.mIsStarted = true;
                return;
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = iterator2.next();
            long l = this.mDuration;
            if (l >= 0L) {
                viewPropertyAnimatorCompat.setDuration(l);
            }
            if ((interpolator2 = this.mInterpolator) != null) {
                viewPropertyAnimatorCompat.setInterpolator(interpolator2);
            }
            if (this.mListener != null) {
                viewPropertyAnimatorCompat.setListener(this.mProxyListener);
            }
            viewPropertyAnimatorCompat.start();
        } while (true);
    }

}

