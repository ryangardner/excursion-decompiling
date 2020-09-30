/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.AnimatorUtils;
import androidx.transition.R;
import androidx.transition.Styleable;
import androidx.transition.Transition;
import androidx.transition.TransitionListenerAdapter;
import androidx.transition.TransitionUtils;
import androidx.transition.TransitionValues;
import androidx.transition.ViewGroupUtils;
import androidx.transition.ViewUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public abstract class Visibility
extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String[] sTransitionProperties = new String[]{"android:visibility:visibility", "android:visibility:parent"};
    private int mMode = 3;

    public Visibility() {
    }

    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.VISIBILITY_TRANSITION);
        int n = TypedArrayUtils.getNamedInt((TypedArray)context, (XmlResourceParser)attributeSet, "transitionVisibilityMode", 0, 0);
        context.recycle();
        if (n == 0) return;
        this.setMode(n);
    }

    private void captureValues(TransitionValues transitionValues) {
        int n = transitionValues.view.getVisibility();
        transitionValues.values.put(PROPNAME_VISIBILITY, n);
        transitionValues.values.put(PROPNAME_PARENT, (Object)transitionValues.view.getParent());
        int[] arrn = new int[2];
        transitionValues.view.getLocationOnScreen(arrn);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, arrn);
    }

    private VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.mVisibilityChange = false;
        visibilityInfo.mFadeIn = false;
        if (transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.mStartVisibility = (Integer)transitionValues.values.get(PROPNAME_VISIBILITY);
            visibilityInfo.mStartParent = (ViewGroup)transitionValues.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.mStartVisibility = -1;
            visibilityInfo.mStartParent = null;
        }
        if (transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.mEndVisibility = (Integer)transitionValues2.values.get(PROPNAME_VISIBILITY);
            visibilityInfo.mEndParent = (ViewGroup)transitionValues2.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.mEndVisibility = -1;
            visibilityInfo.mEndParent = null;
        }
        if (transitionValues != null && transitionValues2 != null) {
            if (visibilityInfo.mStartVisibility == visibilityInfo.mEndVisibility && visibilityInfo.mStartParent == visibilityInfo.mEndParent) {
                return visibilityInfo;
            }
            if (visibilityInfo.mStartVisibility != visibilityInfo.mEndVisibility) {
                if (visibilityInfo.mStartVisibility == 0) {
                    visibilityInfo.mFadeIn = false;
                    visibilityInfo.mVisibilityChange = true;
                    return visibilityInfo;
                }
                if (visibilityInfo.mEndVisibility != 0) return visibilityInfo;
                visibilityInfo.mFadeIn = true;
                visibilityInfo.mVisibilityChange = true;
                return visibilityInfo;
            }
            if (visibilityInfo.mEndParent == null) {
                visibilityInfo.mFadeIn = false;
                visibilityInfo.mVisibilityChange = true;
                return visibilityInfo;
            }
            if (visibilityInfo.mStartParent != null) return visibilityInfo;
            visibilityInfo.mFadeIn = true;
            visibilityInfo.mVisibilityChange = true;
            return visibilityInfo;
        }
        if (transitionValues == null && visibilityInfo.mEndVisibility == 0) {
            visibilityInfo.mFadeIn = true;
            visibilityInfo.mVisibilityChange = true;
            return visibilityInfo;
        }
        if (transitionValues2 != null) return visibilityInfo;
        if (visibilityInfo.mStartVisibility != 0) return visibilityInfo;
        visibilityInfo.mFadeIn = false;
        visibilityInfo.mVisibilityChange = true;
        return visibilityInfo;
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = this.getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (!visibilityInfo.mVisibilityChange) return null;
        if (visibilityInfo.mStartParent == null) {
            if (visibilityInfo.mEndParent == null) return null;
        }
        if (!visibilityInfo.mFadeIn) return this.onDisappear(viewGroup, transitionValues, visibilityInfo.mStartVisibility, transitionValues2, visibilityInfo.mEndVisibility);
        return this.onAppear(viewGroup, transitionValues, visibilityInfo.mStartVisibility, transitionValues2, visibilityInfo.mEndVisibility);
    }

    public int getMode() {
        return this.mMode;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public boolean isTransitionRequired(TransitionValues object, TransitionValues transitionValues) {
        boolean bl = false;
        if (object == null && transitionValues == null) {
            return false;
        }
        if (object != null && transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY) != ((TransitionValues)object).values.containsKey(PROPNAME_VISIBILITY)) {
            return false;
        }
        object = this.getVisibilityChangeInfo((TransitionValues)object, transitionValues);
        boolean bl2 = bl;
        if (!((VisibilityInfo)object).mVisibilityChange) return bl2;
        if (((VisibilityInfo)object).mStartVisibility == 0) return true;
        bl2 = bl;
        if (((VisibilityInfo)object).mEndVisibility != 0) return bl2;
        return true;
    }

    public boolean isVisible(TransitionValues transitionValues) {
        boolean bl = false;
        if (transitionValues == null) {
            return false;
        }
        int n = (Integer)transitionValues.values.get(PROPNAME_VISIBILITY);
        transitionValues = (View)transitionValues.values.get(PROPNAME_PARENT);
        boolean bl2 = bl;
        if (n != 0) return bl2;
        bl2 = bl;
        if (transitionValues == null) return bl2;
        return true;
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int n, TransitionValues transitionValues2, int n2) {
        if ((this.mMode & 1) != 1) return null;
        if (transitionValues2 == null) {
            return null;
        }
        if (transitionValues != null) return this.onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
        View view = (View)transitionValues2.view.getParent();
        if (!this.getVisibilityChangeInfo((TransitionValues)this.getMatchedTransitionValues((View)view, (boolean)false), (TransitionValues)this.getTransitionValues((View)view, (boolean)false)).mVisibilityChange) return this.onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public Animator onDisappear(final ViewGroup var1_1, TransitionValues var2_2, int var3_3, TransitionValues var4_4, int var5_5) {
        block17 : {
            block19 : {
                block18 : {
                    block16 : {
                        if ((this.mMode & 2) != 2) {
                            return null;
                        }
                        if (var2_2 == null) {
                            return null;
                        }
                        var6_6 = var2_2.view;
                        var7_7 = var4_4 != null ? var4_4.view : null;
                        var8_8 = (View)var6_6.getTag(R.id.save_overlay_view);
                        if (var8_8 == null) break block16;
                        var7_7 = null;
                        var3_3 = 1;
                        break block17;
                    }
                    if (var7_7 == null || var7_7.getParent() == null) break block18;
                    if (var5_5 /* !! */  != 4 && var6_6 != var7_7) ** GOTO lbl-1000
                    var8_8 = var7_7;
                    var3_3 = 0;
                    var7_7 = null;
                    break block19;
                }
                if (var7_7 != null) {
                    var8_8 = null;
                    var3_3 = 0;
                } else lbl-1000: // 2 sources:
                {
                    var8_8 = var7_7 = null;
                    var3_3 = 1;
                }
            }
            var9_9 = var7_7;
            if (var3_3 == 0) ** GOTO lbl-1000
            if (var6_6.getParent() == null) ** GOTO lbl-1000
            var9_9 = var7_7;
            if (var6_6.getParent() instanceof View) {
                var10_10 = (View)var6_6.getParent();
                if (!this.getVisibilityChangeInfo((TransitionValues)this.getTransitionValues((View)var10_10, (boolean)true), (TransitionValues)this.getMatchedTransitionValues((View)var10_10, (boolean)true)).mVisibilityChange) {
                    var9_9 = TransitionUtils.copyViewImage(var1_1, var6_6, var10_10);
                } else {
                    var3_3 = var10_10.getId();
                    var9_9 = var7_7;
                    if (var10_10.getParent() == null) {
                        var9_9 = var7_7;
                        if (var3_3 != -1) {
                            var9_9 = var7_7;
                            if (var1_1.findViewById(var3_3) != null) {
                                var9_9 = var7_7;
                                ** if (!this.mCanRemoveViews) goto lbl-1000
                            }
                        }
                    }
                }
            }
            ** GOTO lbl-1000
lbl-1000: // 2 sources:
            {
                var7_7 = var8_8;
                var3_3 = 0;
                var8_8 = var6_6;
                ** GOTO lbl54
            }
lbl-1000: // 4 sources:
            {
                var3_3 = 0;
                var7_7 = var8_8;
                var8_8 = var9_9;
            }
        }
        if (var8_8 != null) {
            if (var3_3 == 0) {
                var7_7 = (View)var2_2.values.get("android:visibility:screenLocation");
                var11_11 = var7_7[0];
                var5_5 /* !! */  = (int)var7_7[1];
                var7_7 = new int[2];
                var1_1.getLocationOnScreen((int[])var7_7);
                var8_8.offsetLeftAndRight((int)(var11_11 - var7_7[0] - var8_8.getLeft()));
                var8_8.offsetTopAndBottom(var5_5 /* !! */  - var7_7[1] - var8_8.getTop());
                ViewGroupUtils.getOverlay(var1_1).add(var8_8);
            }
            var2_2 = this.onDisappear(var1_1, var8_8, (TransitionValues)var2_2, var4_4);
            if (var3_3 != 0) return var2_2;
            if (var2_2 == null) {
                ViewGroupUtils.getOverlay(var1_1).remove(var8_8);
                return var2_2;
            }
            var6_6.setTag(R.id.save_overlay_view, (Object)var8_8);
            this.addListener(new TransitionListenerAdapter(){

                @Override
                public void onTransitionEnd(Transition transition) {
                    var6_6.setTag(R.id.save_overlay_view, null);
                    ViewGroupUtils.getOverlay(var1_1).remove(var8_8);
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    ViewGroupUtils.getOverlay(var1_1).remove(var8_8);
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    if (var8_8.getParent() == null) {
                        ViewGroupUtils.getOverlay(var1_1).add(var8_8);
                        return;
                    }
                    Visibility.this.cancel();
                }
            });
            return var2_2;
        }
        if (var7_7 == null) return null;
        var3_3 = var7_7.getVisibility();
        ViewUtils.setTransitionVisibility(var7_7, 0);
        var1_1 = this.onDisappear(var1_1, var7_7, (TransitionValues)var2_2, var4_4);
        if (var1_1 != null) {
            var2_2 = new DisappearListener(var7_7, var5_5 /* !! */ , true);
            var1_1.addListener((Animator.AnimatorListener)var2_2);
            AnimatorUtils.addPauseListener((Animator)var1_1, (AnimatorListenerAdapter)var2_2);
            this.addListener((Transition.TransitionListener)var2_2);
            return var1_1;
        }
        ViewUtils.setTransitionVisibility(var7_7, var3_3);
        return var1_1;
    }

    public void setMode(int n) {
        if ((n & -4) != 0) throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
        this.mMode = n;
    }

    private static class DisappearListener
    extends AnimatorListenerAdapter
    implements Transition.TransitionListener,
    AnimatorUtils.AnimatorPauseListenerCompat {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final View mView;

        DisappearListener(View view, int n, boolean bl) {
            this.mView = view;
            this.mFinalVisibility = n;
            this.mParent = (ViewGroup)view.getParent();
            this.mSuppressLayout = bl;
            this.suppressLayout(true);
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            this.suppressLayout(false);
        }

        private void suppressLayout(boolean bl) {
            if (!this.mSuppressLayout) return;
            if (this.mLayoutSuppressed == bl) return;
            ViewGroup viewGroup = this.mParent;
            if (viewGroup == null) return;
            this.mLayoutSuppressed = bl;
            ViewGroupUtils.suppressLayout(viewGroup, bl);
        }

        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        public void onAnimationEnd(Animator animator2) {
            this.hideViewWhenNotCanceled();
        }

        @Override
        public void onAnimationPause(Animator animator2) {
            if (this.mCanceled) return;
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
        }

        public void onAnimationRepeat(Animator animator2) {
        }

        @Override
        public void onAnimationResume(Animator animator2) {
            if (this.mCanceled) return;
            ViewUtils.setTransitionVisibility(this.mView, 0);
        }

        public void onAnimationStart(Animator animator2) {
        }

        @Override
        public void onTransitionCancel(Transition transition) {
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            this.hideViewWhenNotCanceled();
            transition.removeListener(this);
        }

        @Override
        public void onTransitionPause(Transition transition) {
            this.suppressLayout(false);
        }

        @Override
        public void onTransitionResume(Transition transition) {
            this.suppressLayout(true);
        }

        @Override
        public void onTransitionStart(Transition transition) {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Mode {
    }

    private static class VisibilityInfo {
        ViewGroup mEndParent;
        int mEndVisibility;
        boolean mFadeIn;
        ViewGroup mStartParent;
        int mStartVisibility;
        boolean mVisibilityChange;

        VisibilityInfo() {
        }
    }

}

