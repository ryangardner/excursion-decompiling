/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.animation.TypeEvaluator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Pair
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewAnimationUtils
 *  android.view.ViewGroup
 *  android.widget.ImageView
 */
package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.animation.ChildrenAlphaProperty;
import com.google.android.material.animation.DrawableAlphaProperty;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.circularreveal.CircularRevealCompat;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.math.MathUtils;
import com.google.android.material.transformation.ExpandableTransformationBehavior;
import com.google.android.material.transformation.TransformationChildCard;
import com.google.android.material.transformation.TransformationChildLayout;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class FabTransformationBehavior
extends ExpandableTransformationBehavior {
    private float dependencyOriginalTranslationX;
    private float dependencyOriginalTranslationY;
    private final int[] tmpArray = new int[2];
    private final Rect tmpRect = new Rect();
    private final RectF tmpRectF1 = new RectF();
    private final RectF tmpRectF2 = new RectF();

    public FabTransformationBehavior() {
    }

    public FabTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private ViewGroup calculateChildContentContainer(View view) {
        View view2 = view.findViewById(R.id.mtrl_child_content_container);
        if (view2 != null) {
            return this.toViewGroupOrNull(view2);
        }
        if (view instanceof TransformationChildLayout) return this.toViewGroupOrNull(((ViewGroup)view).getChildAt(0));
        if (!(view instanceof TransformationChildCard)) return this.toViewGroupOrNull(view);
        return this.toViewGroupOrNull(((ViewGroup)view).getChildAt(0));
    }

    private void calculateChildVisibleBoundsAtEndOfExpansion(View view, FabTransformationSpec fabTransformationSpec, MotionTiming motionTiming, MotionTiming motionTiming2, float f, float f2, float f3, float f4, RectF rectF) {
        f = this.calculateValueOfAnimationAtEndOfExpansion(fabTransformationSpec, motionTiming, f, f3);
        f2 = this.calculateValueOfAnimationAtEndOfExpansion(fabTransformationSpec, motionTiming2, f2, f4);
        motionTiming = this.tmpRect;
        view.getWindowVisibleDisplayFrame((Rect)motionTiming);
        fabTransformationSpec = this.tmpRectF1;
        fabTransformationSpec.set((Rect)motionTiming);
        motionTiming = this.tmpRectF2;
        this.calculateWindowBounds(view, (RectF)motionTiming);
        motionTiming.offset(f, f2);
        motionTiming.intersect((RectF)fabTransformationSpec);
        rectF.set((RectF)motionTiming);
    }

    private void calculateDependencyWindowBounds(View view, RectF rectF) {
        this.calculateWindowBounds(view, rectF);
        rectF.offset(this.dependencyOriginalTranslationX, this.dependencyOriginalTranslationY);
    }

    private Pair<MotionTiming, MotionTiming> calculateMotionTiming(float f, float f2, boolean bl, FabTransformationSpec object) {
        float f3;
        MotionTiming motionTiming;
        if (f != 0.0f && (f3 = (f2 FCMPL 0.0f)) != false) {
            if (bl && f2 < 0.0f || !bl && f3 > 0) {
                MotionTiming motionTiming2 = ((FabTransformationSpec)object).timings.getTiming("translationXCurveUpwards");
                motionTiming = ((FabTransformationSpec)object).timings.getTiming("translationYCurveUpwards");
                object = motionTiming2;
                return new Pair(object, (Object)motionTiming);
            }
            motionTiming = ((FabTransformationSpec)object).timings.getTiming("translationXCurveDownwards");
            MotionTiming motionTiming3 = ((FabTransformationSpec)object).timings.getTiming("translationYCurveDownwards");
            object = motionTiming;
            motionTiming = motionTiming3;
            return new Pair(object, (Object)motionTiming);
        }
        MotionTiming motionTiming4 = ((FabTransformationSpec)object).timings.getTiming("translationXLinear");
        motionTiming = ((FabTransformationSpec)object).timings.getTiming("translationYLinear");
        object = motionTiming4;
        return new Pair(object, (Object)motionTiming);
    }

    private float calculateRevealCenterX(View view, View view2, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        this.calculateDependencyWindowBounds(view, rectF);
        this.calculateWindowBounds(view2, rectF2);
        rectF2.offset(-this.calculateTranslationX(view, view2, positioning), 0.0f);
        return rectF.centerX() - rectF2.left;
    }

    private float calculateRevealCenterY(View view, View view2, Positioning positioning) {
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        this.calculateDependencyWindowBounds(view, rectF);
        this.calculateWindowBounds(view2, rectF2);
        rectF2.offset(0.0f, -this.calculateTranslationY(view, view2, positioning));
        return rectF.centerY() - rectF2.top;
    }

    private float calculateTranslationX(View view, View view2, Positioning positioning) {
        float f;
        float f2;
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        this.calculateDependencyWindowBounds(view, rectF);
        this.calculateWindowBounds(view2, rectF2);
        int n = positioning.gravity & 7;
        if (n != 1) {
            if (n != 3) {
                if (n != 5) {
                    f = 0.0f;
                    return f + positioning.xAdjustment;
                }
                f = rectF2.right;
                f2 = rectF.right;
            } else {
                f = rectF2.left;
                f2 = rectF.left;
            }
        } else {
            f = rectF2.centerX();
            f2 = rectF.centerX();
        }
        f -= f2;
        return f + positioning.xAdjustment;
    }

    private float calculateTranslationY(View view, View view2, Positioning positioning) {
        float f;
        float f2;
        RectF rectF = this.tmpRectF1;
        RectF rectF2 = this.tmpRectF2;
        this.calculateDependencyWindowBounds(view, rectF);
        this.calculateWindowBounds(view2, rectF2);
        int n = positioning.gravity & 112;
        if (n != 16) {
            if (n != 48) {
                if (n != 80) {
                    f = 0.0f;
                    return f + positioning.yAdjustment;
                }
                f = rectF2.bottom;
                f2 = rectF.bottom;
            } else {
                f = rectF2.top;
                f2 = rectF.top;
            }
        } else {
            f = rectF2.centerY();
            f2 = rectF.centerY();
        }
        f -= f2;
        return f + positioning.yAdjustment;
    }

    private float calculateValueOfAnimationAtEndOfExpansion(FabTransformationSpec object, MotionTiming motionTiming, float f, float f2) {
        long l = motionTiming.getDelay();
        long l2 = motionTiming.getDuration();
        object = ((FabTransformationSpec)object).timings.getTiming("expansion");
        float f3 = (float)(((MotionTiming)object).getDelay() + ((MotionTiming)object).getDuration() + 17L - l) / (float)l2;
        return AnimationUtils.lerp(f, f2, motionTiming.getInterpolator().getInterpolation(f3));
    }

    private void calculateWindowBounds(View view, RectF rectF) {
        rectF.set(0.0f, 0.0f, (float)view.getWidth(), (float)view.getHeight());
        int[] arrn = this.tmpArray;
        view.getLocationInWindow(arrn);
        rectF.offsetTo((float)arrn[0], (float)arrn[1]);
        rectF.offset((float)((int)(-view.getTranslationX())), (float)((int)(-view.getTranslationY())));
    }

    private void createChildrenFadeAnimation(View view, View view2, boolean bl, boolean bl2, FabTransformationSpec fabTransformationSpec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        if (!(view2 instanceof ViewGroup)) {
            return;
        }
        if (view2 instanceof CircularRevealWidget && CircularRevealHelper.STRATEGY == 0) {
            return;
        }
        view = this.calculateChildContentContainer(view2);
        if (view == null) {
            return;
        }
        if (bl) {
            if (!bl2) {
                ChildrenAlphaProperty.CHILDREN_ALPHA.set((Object)view, (Object)Float.valueOf(0.0f));
            }
            view = ObjectAnimator.ofFloat((Object)view, ChildrenAlphaProperty.CHILDREN_ALPHA, (float[])new float[]{1.0f});
        } else {
            view = ObjectAnimator.ofFloat((Object)view, ChildrenAlphaProperty.CHILDREN_ALPHA, (float[])new float[]{0.0f});
        }
        fabTransformationSpec.timings.getTiming("contentFade").apply((Animator)view);
        list.add((Animator)view);
    }

    private void createColorAnimation(View view, View object, boolean bl, boolean bl2, FabTransformationSpec fabTransformationSpec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        if (!(object instanceof CircularRevealWidget)) {
            return;
        }
        object = (CircularRevealWidget)object;
        int n = this.getBackgroundTint(view);
        if (bl) {
            if (!bl2) {
                object.setCircularRevealScrimColor(n);
            }
            view = ObjectAnimator.ofInt((Object)object, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, (int[])new int[]{16777215 & n});
        } else {
            view = ObjectAnimator.ofInt((Object)object, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, (int[])new int[]{n});
        }
        view.setEvaluator((TypeEvaluator)ArgbEvaluatorCompat.getInstance());
        fabTransformationSpec.timings.getTiming("color").apply((Animator)view);
        list.add((Animator)view);
    }

    private void createDependencyTranslationAnimation(View view, View object, boolean bl, FabTransformationSpec object2, List<Animator> list) {
        float f = this.calculateTranslationX(view, (View)object, ((FabTransformationSpec)object2).positioning);
        float f2 = this.calculateTranslationY(view, (View)object, ((FabTransformationSpec)object2).positioning);
        object2 = this.calculateMotionTiming(f, f2, bl, (FabTransformationSpec)object2);
        object = (MotionTiming)((Pair)object2).first;
        object2 = (MotionTiming)((Pair)object2).second;
        Property property = View.TRANSLATION_X;
        if (!bl) {
            f = this.dependencyOriginalTranslationX;
        }
        property = ObjectAnimator.ofFloat((Object)view, (Property)property, (float[])new float[]{f});
        Property property2 = View.TRANSLATION_Y;
        f = bl ? f2 : this.dependencyOriginalTranslationY;
        view = ObjectAnimator.ofFloat((Object)view, (Property)property2, (float[])new float[]{f});
        ((MotionTiming)object).apply((Animator)property);
        ((MotionTiming)object2).apply((Animator)view);
        list.add((Animator)property);
        list.add((Animator)view);
    }

    private void createElevationAnimation(View view, View view2, boolean bl, boolean bl2, FabTransformationSpec fabTransformationSpec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        float f = ViewCompat.getElevation(view2) - ViewCompat.getElevation(view);
        if (bl) {
            if (!bl2) {
                view2.setTranslationZ(-f);
            }
            view = ObjectAnimator.ofFloat((Object)view2, (Property)View.TRANSLATION_Z, (float[])new float[]{0.0f});
        } else {
            view = ObjectAnimator.ofFloat((Object)view2, (Property)View.TRANSLATION_Z, (float[])new float[]{-f});
        }
        fabTransformationSpec.timings.getTiming("elevation").apply((Animator)view);
        list.add((Animator)view);
    }

    private void createExpansionAnimation(View view, View view2, boolean bl, boolean bl2, FabTransformationSpec fabTransformationSpec, float f, float f2, List<Animator> list, List<Animator.AnimatorListener> list2) {
        if (!(view2 instanceof CircularRevealWidget)) {
            return;
        }
        final CircularRevealWidget circularRevealWidget = (CircularRevealWidget)view2;
        float f3 = this.calculateRevealCenterX(view, view2, fabTransformationSpec.positioning);
        float f4 = this.calculateRevealCenterY(view, view2, fabTransformationSpec.positioning);
        ((FloatingActionButton)view).getContentRect(this.tmpRect);
        float f5 = (float)this.tmpRect.width() / 2.0f;
        MotionTiming motionTiming = fabTransformationSpec.timings.getTiming("expansion");
        if (bl) {
            if (!bl2) {
                circularRevealWidget.setRevealInfo(new CircularRevealWidget.RevealInfo(f3, f4, f5));
            }
            if (bl2) {
                f5 = circularRevealWidget.getRevealInfo().radius;
            }
            view = CircularRevealCompat.createCircularReveal(circularRevealWidget, f3, f4, MathUtils.distanceToFurthestCorner(f3, f4, 0.0f, 0.0f, f, f2));
            view.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                public void onAnimationEnd(Animator object) {
                    object = circularRevealWidget.getRevealInfo();
                    object.radius = Float.MAX_VALUE;
                    circularRevealWidget.setRevealInfo((CircularRevealWidget.RevealInfo)object);
                }
            });
            this.createPreFillRadialExpansion(view2, motionTiming.getDelay(), (int)f3, (int)f4, f5, list);
        } else {
            f = circularRevealWidget.getRevealInfo().radius;
            view = CircularRevealCompat.createCircularReveal(circularRevealWidget, f3, f4, f5);
            long l = motionTiming.getDelay();
            int n = (int)f3;
            int n2 = (int)f4;
            this.createPreFillRadialExpansion(view2, l, n, n2, f, list);
            this.createPostFillRadialExpansion(view2, motionTiming.getDelay(), motionTiming.getDuration(), fabTransformationSpec.timings.getTotalDuration(), n, n2, f5, list);
        }
        motionTiming.apply((Animator)view);
        list.add((Animator)view);
        list2.add(CircularRevealCompat.createCircularRevealListener(circularRevealWidget));
    }

    private void createIconFadeAnimation(View view, final View view2, boolean bl, boolean bl2, FabTransformationSpec fabTransformationSpec, List<Animator> list, List<Animator.AnimatorListener> list2) {
        if (!(view2 instanceof CircularRevealWidget)) return;
        if (!(view instanceof ImageView)) {
            return;
        }
        final CircularRevealWidget circularRevealWidget = (CircularRevealWidget)view2;
        final Drawable drawable2 = ((ImageView)view).getDrawable();
        if (drawable2 == null) {
            return;
        }
        drawable2.mutate();
        if (bl) {
            if (!bl2) {
                drawable2.setAlpha(255);
            }
            view = ObjectAnimator.ofInt((Object)drawable2, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, (int[])new int[]{0});
        } else {
            view = ObjectAnimator.ofInt((Object)drawable2, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, (int[])new int[]{255});
        }
        view.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view2.invalidate();
            }
        });
        fabTransformationSpec.timings.getTiming("iconFade").apply((Animator)view);
        list.add((Animator)view);
        list2.add((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                circularRevealWidget.setCircularRevealOverlayDrawable(null);
            }

            public void onAnimationStart(Animator animator2) {
                circularRevealWidget.setCircularRevealOverlayDrawable(drawable2);
            }
        });
    }

    private void createPostFillRadialExpansion(View view, long l, long l2, long l3, int n, int n2, float f, List<Animator> list) {
        if (Build.VERSION.SDK_INT < 21) return;
        if ((l += l2) >= l3) return;
        view = ViewAnimationUtils.createCircularReveal((View)view, (int)n, (int)n2, (float)f, (float)f);
        view.setStartDelay(l);
        view.setDuration(l3 - l);
        list.add((Animator)view);
    }

    private void createPreFillRadialExpansion(View view, long l, int n, int n2, float f, List<Animator> list) {
        if (Build.VERSION.SDK_INT < 21) return;
        if (l <= 0L) return;
        view = ViewAnimationUtils.createCircularReveal((View)view, (int)n, (int)n2, (float)f, (float)f);
        view.setStartDelay(0L);
        view.setDuration(l);
        list.add((Animator)view);
    }

    private void createTranslationAnimation(View objectAnimator, View view, boolean bl, boolean bl2, FabTransformationSpec fabTransformationSpec, List<Animator> list, List<Animator.AnimatorListener> objectAnimator2, RectF rectF) {
        float f = this.calculateTranslationX((View)objectAnimator, view, fabTransformationSpec.positioning);
        float f2 = this.calculateTranslationY((View)objectAnimator, view, fabTransformationSpec.positioning);
        objectAnimator = this.calculateMotionTiming(f, f2, bl, fabTransformationSpec);
        MotionTiming motionTiming = (MotionTiming)objectAnimator.first;
        MotionTiming motionTiming2 = (MotionTiming)objectAnimator.second;
        if (bl) {
            if (!bl2) {
                view.setTranslationX(-f);
                view.setTranslationY(-f2);
            }
            objectAnimator = ObjectAnimator.ofFloat((Object)view, (Property)View.TRANSLATION_X, (float[])new float[]{0.0f});
            objectAnimator2 = ObjectAnimator.ofFloat((Object)view, (Property)View.TRANSLATION_Y, (float[])new float[]{0.0f});
            this.calculateChildVisibleBoundsAtEndOfExpansion(view, fabTransformationSpec, motionTiming, motionTiming2, -f, -f2, 0.0f, 0.0f, rectF);
            view = objectAnimator2;
        } else {
            objectAnimator = ObjectAnimator.ofFloat((Object)view, (Property)View.TRANSLATION_X, (float[])new float[]{-f});
            view = ObjectAnimator.ofFloat((Object)view, (Property)View.TRANSLATION_Y, (float[])new float[]{-f2});
        }
        motionTiming.apply((Animator)objectAnimator);
        motionTiming2.apply((Animator)view);
        list.add((Animator)objectAnimator);
        list.add((Animator)view);
    }

    private int getBackgroundTint(View view) {
        ColorStateList colorStateList = ViewCompat.getBackgroundTintList(view);
        if (colorStateList == null) return 0;
        return colorStateList.getColorForState(view.getDrawableState(), colorStateList.getDefaultColor());
    }

    private ViewGroup toViewGroupOrNull(View view) {
        if (!(view instanceof ViewGroup)) return null;
        return (ViewGroup)view;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
        boolean bl;
        if (view.getVisibility() == 8) throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        boolean bl2 = view2 instanceof FloatingActionButton;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        int n = ((FloatingActionButton)view2).getExpandedComponentIdHint();
        if (n == 0) return true;
        bl3 = bl;
        if (n != view.getId()) return bl3;
        return true;
    }

    @Override
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        if (layoutParams.dodgeInsetEdges != 0) return;
        layoutParams.dodgeInsetEdges = 80;
    }

    @Override
    protected AnimatorSet onCreateExpandedStateChangeAnimation(final View view, final View view2, final boolean bl, boolean bl2) {
        FabTransformationSpec fabTransformationSpec = this.onCreateMotionSpec(view2.getContext(), bl);
        if (bl) {
            this.dependencyOriginalTranslationX = view.getTranslationX();
            this.dependencyOriginalTranslationY = view.getTranslationY();
        }
        ArrayList<Animator> arrayList = new ArrayList<Animator>();
        ArrayList<Animator.AnimatorListener> arrayList2 = new ArrayList<Animator.AnimatorListener>();
        if (Build.VERSION.SDK_INT >= 21) {
            this.createElevationAnimation(view, view2, bl, bl2, fabTransformationSpec, arrayList, arrayList2);
        }
        RectF rectF = this.tmpRectF1;
        this.createTranslationAnimation(view, view2, bl, bl2, fabTransformationSpec, arrayList, arrayList2, rectF);
        float f = rectF.width();
        float f2 = rectF.height();
        this.createDependencyTranslationAnimation(view, view2, bl, fabTransformationSpec, arrayList);
        this.createIconFadeAnimation(view, view2, bl, bl2, fabTransformationSpec, arrayList, arrayList2);
        this.createExpansionAnimation(view, view2, bl, bl2, fabTransformationSpec, f, f2, arrayList, arrayList2);
        this.createColorAnimation(view, view2, bl, bl2, fabTransformationSpec, arrayList, arrayList2);
        this.createChildrenFadeAnimation(view, view2, bl, bl2, fabTransformationSpec, arrayList, arrayList2);
        rectF = new AnimatorSet();
        AnimatorSetCompat.playTogether((AnimatorSet)rectF, arrayList);
        rectF.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                if (bl) return;
                view2.setVisibility(4);
                view.setAlpha(1.0f);
                view.setVisibility(0);
            }

            public void onAnimationStart(Animator animator2) {
                if (!bl) return;
                view2.setVisibility(0);
                view.setAlpha(0.0f);
                view.setVisibility(4);
            }
        });
        int n = 0;
        int n2 = arrayList2.size();
        while (n < n2) {
            rectF.addListener((Animator.AnimatorListener)arrayList2.get(n));
            ++n;
        }
        return rectF;
    }

    protected abstract FabTransformationSpec onCreateMotionSpec(Context var1, boolean var2);

    protected static class FabTransformationSpec {
        public Positioning positioning;
        public MotionSpec timings;

        protected FabTransformationSpec() {
        }
    }

}

