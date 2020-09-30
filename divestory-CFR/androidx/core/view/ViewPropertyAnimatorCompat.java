/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Paint
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 *  android.view.animation.Interpolator
 */
package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import java.lang.ref.WeakReference;

public final class ViewPropertyAnimatorCompat {
    static final int LISTENER_TAG_ID = 2113929216;
    Runnable mEndAction = null;
    int mOldLayerType = -1;
    Runnable mStartAction = null;
    private WeakReference<View> mView;

    ViewPropertyAnimatorCompat(View view) {
        this.mView = new WeakReference<View>(view);
    }

    private void setListenerInternal(final View view, final ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (viewPropertyAnimatorListener != null) {
            view.animate().setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationCancel(Animator animator2) {
                    viewPropertyAnimatorListener.onAnimationCancel(view);
                }

                public void onAnimationEnd(Animator animator2) {
                    viewPropertyAnimatorListener.onAnimationEnd(view);
                }

                public void onAnimationStart(Animator animator2) {
                    viewPropertyAnimatorListener.onAnimationStart(view);
                }
            });
            return;
        }
        view.animate().setListener(null);
    }

    public ViewPropertyAnimatorCompat alpha(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().alpha(f);
        return this;
    }

    public ViewPropertyAnimatorCompat alphaBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().alphaBy(f);
        return this;
    }

    public void cancel() {
        View view = (View)this.mView.get();
        if (view == null) return;
        view.animate().cancel();
    }

    public long getDuration() {
        View view = (View)this.mView.get();
        if (view == null) return 0L;
        return view.animate().getDuration();
    }

    public Interpolator getInterpolator() {
        View view = (View)this.mView.get();
        if (view == null) return null;
        if (Build.VERSION.SDK_INT < 18) return null;
        return (Interpolator)view.animate().getInterpolator();
    }

    public long getStartDelay() {
        View view = (View)this.mView.get();
        if (view == null) return 0L;
        return view.animate().getStartDelay();
    }

    public ViewPropertyAnimatorCompat rotation(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().rotation(f);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().rotationBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationX(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().rotationX(f);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationXBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().rotationXBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationY(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().rotationY(f);
        return this;
    }

    public ViewPropertyAnimatorCompat rotationYBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().rotationYBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleX(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().scaleX(f);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleXBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().scaleXBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleY(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().scaleY(f);
        return this;
    }

    public ViewPropertyAnimatorCompat scaleYBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().scaleYBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat setDuration(long l) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().setDuration(l);
        return this;
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator interpolator2) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().setInterpolator((TimeInterpolator)interpolator2);
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT >= 16) {
            this.setListenerInternal(view, viewPropertyAnimatorListener);
            return this;
        }
        view.setTag(2113929216, (Object)viewPropertyAnimatorListener);
        this.setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
        return this;
    }

    public ViewPropertyAnimatorCompat setStartDelay(long l) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().setStartDelay(l);
        return this;
    }

    public ViewPropertyAnimatorCompat setUpdateListener(final ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        final View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT < 19) return this;
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = null;
        if (viewPropertyAnimatorUpdateListener != null) {
            animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    viewPropertyAnimatorUpdateListener.onAnimationUpdate(view);
                }
            };
        }
        view.animate().setUpdateListener(animatorUpdateListener);
        return this;
    }

    public void start() {
        View view = (View)this.mView.get();
        if (view == null) return;
        view.animate().start();
    }

    public ViewPropertyAnimatorCompat translationX(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().translationX(f);
        return this;
    }

    public ViewPropertyAnimatorCompat translationXBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().translationXBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat translationY(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().translationY(f);
        return this;
    }

    public ViewPropertyAnimatorCompat translationYBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().translationYBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat translationZ(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT < 21) return this;
        view.animate().translationZ(f);
        return this;
    }

    public ViewPropertyAnimatorCompat translationZBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT < 21) return this;
        view.animate().translationZBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat withEndAction(Runnable runnable2) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT >= 16) {
            view.animate().withEndAction(runnable2);
            return this;
        }
        this.setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
        this.mEndAction = runnable2;
        return this;
    }

    public ViewPropertyAnimatorCompat withLayer() {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT >= 16) {
            view.animate().withLayer();
            return this;
        }
        this.mOldLayerType = view.getLayerType();
        this.setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
        return this;
    }

    public ViewPropertyAnimatorCompat withStartAction(Runnable runnable2) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT >= 16) {
            view.animate().withStartAction(runnable2);
            return this;
        }
        this.setListenerInternal(view, new ViewPropertyAnimatorListenerApi14(this));
        this.mStartAction = runnable2;
        return this;
    }

    public ViewPropertyAnimatorCompat x(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().x(f);
        return this;
    }

    public ViewPropertyAnimatorCompat xBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().xBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat y(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().y(f);
        return this;
    }

    public ViewPropertyAnimatorCompat yBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        view.animate().yBy(f);
        return this;
    }

    public ViewPropertyAnimatorCompat z(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT < 21) return this;
        view.animate().z(f);
        return this;
    }

    public ViewPropertyAnimatorCompat zBy(float f) {
        View view = (View)this.mView.get();
        if (view == null) return this;
        if (Build.VERSION.SDK_INT < 21) return this;
        view.animate().zBy(f);
        return this;
    }

    static class ViewPropertyAnimatorListenerApi14
    implements ViewPropertyAnimatorListener {
        boolean mAnimEndCalled;
        ViewPropertyAnimatorCompat mVpa;

        ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.mVpa = viewPropertyAnimatorCompat;
        }

        @Override
        public void onAnimationCancel(View view) {
            Object object = view.getTag(2113929216);
            object = object instanceof ViewPropertyAnimatorListener ? (ViewPropertyAnimatorListener)object : null;
            if (object == null) return;
            object.onAnimationCancel(view);
        }

        @Override
        public void onAnimationEnd(View view) {
            Object object;
            int n = this.mVpa.mOldLayerType;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
            if (n > -1) {
                view.setLayerType(this.mVpa.mOldLayerType, null);
                this.mVpa.mOldLayerType = -1;
            }
            if (Build.VERSION.SDK_INT < 16) {
                if (this.mAnimEndCalled) return;
            }
            if (this.mVpa.mEndAction != null) {
                object = this.mVpa.mEndAction;
                this.mVpa.mEndAction = null;
                object.run();
            }
            if ((object = view.getTag(2113929216)) instanceof ViewPropertyAnimatorListener) {
                viewPropertyAnimatorListener = (ViewPropertyAnimatorListener)object;
            }
            if (viewPropertyAnimatorListener != null) {
                viewPropertyAnimatorListener.onAnimationEnd(view);
            }
            this.mAnimEndCalled = true;
        }

        @Override
        public void onAnimationStart(View view) {
            Object object;
            this.mAnimEndCalled = false;
            int n = this.mVpa.mOldLayerType;
            ViewPropertyAnimatorListener viewPropertyAnimatorListener = null;
            if (n > -1) {
                view.setLayerType(2, null);
            }
            if (this.mVpa.mStartAction != null) {
                object = this.mVpa.mStartAction;
                this.mVpa.mStartAction = null;
                object.run();
            }
            if ((object = view.getTag(2113929216)) instanceof ViewPropertyAnimatorListener) {
                viewPropertyAnimatorListener = (ViewPropertyAnimatorListener)object;
            }
            if (viewPropertyAnimatorListener == null) return;
            viewPropertyAnimatorListener.onAnimationStart(view);
        }
    }

}

