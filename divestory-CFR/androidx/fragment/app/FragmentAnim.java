/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.LayoutTransition
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.view.animation.AnimationSet
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Transformation
 */
package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentTransition;

class FragmentAnim {
    private FragmentAnim() {
    }

    static void animateRemoveFragment(final Fragment fragment, AnimationOrAnimator object, final FragmentTransition.Callback callback) {
        final View view = fragment.mView;
        final ViewGroup viewGroup = fragment.mContainer;
        viewGroup.startViewTransition(view);
        final CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){

            @Override
            public void onCancel() {
                if (fragment.getAnimatingAway() != null) {
                    View view = fragment.getAnimatingAway();
                    fragment.setAnimatingAway(null);
                    view.clearAnimation();
                }
                fragment.setAnimator(null);
            }
        });
        callback.onStart(fragment, cancellationSignal);
        if (((AnimationOrAnimator)object).animation != null) {
            object = new EndViewTransitionAnimation(((AnimationOrAnimator)object).animation, viewGroup, view);
            fragment.setAnimatingAway(fragment.mView);
            object.setAnimationListener(new Animation.AnimationListener(){

                public void onAnimationEnd(Animation animation) {
                    viewGroup.post(new Runnable(){

                        @Override
                        public void run() {
                            if (fragment.getAnimatingAway() == null) return;
                            fragment.setAnimatingAway(null);
                            callback.onComplete(fragment, cancellationSignal);
                        }
                    });
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

            });
            fragment.mView.startAnimation((Animation)object);
            return;
        }
        Animator animator2 = ((AnimationOrAnimator)object).animator;
        fragment.setAnimator(((AnimationOrAnimator)object).animator);
        animator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                viewGroup.endViewTransition(view);
                animator2 = fragment.getAnimator();
                fragment.setAnimator(null);
                if (animator2 == null) return;
                if (viewGroup.indexOfChild(view) >= 0) return;
                callback.onComplete(fragment, cancellationSignal);
            }
        });
        animator2.setTarget((Object)fragment.mView);
        animator2.start();
    }

    static AnimationOrAnimator loadAnimation(Context context, FragmentContainer object, Fragment fragment, boolean bl) {
        int n;
        int n2;
        block15 : {
            n2 = fragment.getNextTransition();
            int n3 = fragment.getNextAnim();
            int n4 = 0;
            fragment.setNextAnim(0);
            object = ((FragmentContainer)object).onFindViewById(fragment.mContainerId);
            if (object != null && object.getTag(R.id.visible_removing_fragment_view_tag) != null) {
                object.setTag(R.id.visible_removing_fragment_view_tag, null);
            }
            if (fragment.mContainer != null && fragment.mContainer.getLayoutTransition() != null) {
                return null;
            }
            object = fragment.onCreateAnimation(n2, bl, n3);
            if (object != null) {
                return new AnimationOrAnimator((Animation)object);
            }
            object = fragment.onCreateAnimator(n2, bl, n3);
            if (object != null) {
                return new AnimationOrAnimator((Animator)object);
            }
            if (n3 != 0) {
                boolean bl2 = "anim".equals(context.getResources().getResourceTypeName(n3));
                n = n4;
                if (bl2) {
                    try {
                        object = AnimationUtils.loadAnimation((Context)context, (int)n3);
                        if (object != null) {
                            return new AnimationOrAnimator((Animation)object);
                        }
                        n = 1;
                    }
                    catch (RuntimeException runtimeException) {
                        n = n4;
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        throw notFoundException;
                    }
                }
                if (n == 0) {
                    try {
                        object = AnimatorInflater.loadAnimator((Context)context, (int)n3);
                        if (object != null) {
                            return new AnimationOrAnimator((Animator)object);
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        if (bl2) throw runtimeException;
                        Animation animation = AnimationUtils.loadAnimation((Context)context, (int)n3);
                        if (animation == null) break block15;
                        return new AnimationOrAnimator(animation);
                    }
                }
            }
        }
        if (n2 == 0) {
            return null;
        }
        n = FragmentAnim.transitToAnimResourceId(n2, bl);
        if (n >= 0) return new AnimationOrAnimator(AnimationUtils.loadAnimation((Context)context, (int)n));
        return null;
    }

    private static int transitToAnimResourceId(int n, boolean bl) {
        if (n != 4097) {
            if (n != 4099) {
                if (n != 8194) {
                    return -1;
                }
                if (!bl) return R.anim.fragment_close_exit;
                return R.anim.fragment_close_enter;
            }
            if (!bl) return R.anim.fragment_fade_exit;
            return R.anim.fragment_fade_enter;
        }
        if (!bl) return R.anim.fragment_open_exit;
        return R.anim.fragment_open_enter;
    }

    static class AnimationOrAnimator {
        public final Animation animation;
        public final Animator animator;

        AnimationOrAnimator(Animator animator2) {
            this.animation = null;
            this.animator = animator2;
            if (animator2 == null) throw new IllegalStateException("Animator cannot be null");
        }

        AnimationOrAnimator(Animation animation) {
            this.animation = animation;
            this.animator = null;
            if (animation == null) throw new IllegalStateException("Animation cannot be null");
        }
    }

    private static class EndViewTransitionAnimation
    extends AnimationSet
    implements Runnable {
        private boolean mAnimating = true;
        private final View mChild;
        private boolean mEnded;
        private final ViewGroup mParent;
        private boolean mTransitionEnded;

        EndViewTransitionAnimation(Animation animation, ViewGroup viewGroup, View view) {
            super(false);
            this.mParent = viewGroup;
            this.mChild = view;
            this.addAnimation(animation);
            this.mParent.post((Runnable)this);
        }

        public boolean getTransformation(long l, Transformation transformation) {
            this.mAnimating = true;
            if (this.mEnded) {
                return this.mTransitionEnded ^ true;
            }
            if (super.getTransformation(l, transformation)) return true;
            this.mEnded = true;
            OneShotPreDrawListener.add((View)this.mParent, this);
            return true;
        }

        public boolean getTransformation(long l, Transformation transformation, float f) {
            this.mAnimating = true;
            if (this.mEnded) {
                return this.mTransitionEnded ^ true;
            }
            if (super.getTransformation(l, transformation, f)) return true;
            this.mEnded = true;
            OneShotPreDrawListener.add((View)this.mParent, this);
            return true;
        }

        @Override
        public void run() {
            if (!this.mEnded && this.mAnimating) {
                this.mAnimating = false;
                this.mParent.post((Runnable)this);
                return;
            }
            this.mParent.endViewTransition(this.mChild);
            this.mTransitionEnded = true;
        }
    }

}

