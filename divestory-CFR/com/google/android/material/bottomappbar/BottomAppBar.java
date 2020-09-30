/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.Menu
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BottomAppBar
extends Toolbar
implements CoordinatorLayout.AttachedBehavior {
    private static final long ANIMATION_DURATION = 300L;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_BottomAppBar;
    public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
    public static final int FAB_ALIGNMENT_MODE_END = 1;
    public static final int FAB_ANIMATION_MODE_SCALE = 0;
    public static final int FAB_ANIMATION_MODE_SLIDE = 1;
    private int animatingModeChangeCounter = 0;
    private ArrayList<AnimationListener> animationListeners;
    private Behavior behavior;
    private int bottomInset;
    private int fabAlignmentMode;
    AnimatorListenerAdapter fabAnimationListener = new AnimatorListenerAdapter(){

        public void onAnimationStart(Animator object) {
            object = BottomAppBar.this;
            ((BottomAppBar)object).maybeAnimateMenuView(((BottomAppBar)object).fabAlignmentMode, BottomAppBar.this.fabAttached);
        }
    };
    private int fabAnimationMode;
    private boolean fabAttached = true;
    private final int fabOffsetEndMode;
    TransformationCallback<FloatingActionButton> fabTransformationCallback = new TransformationCallback<FloatingActionButton>(){

        @Override
        public void onScaleChanged(FloatingActionButton floatingActionButton) {
            MaterialShapeDrawable materialShapeDrawable = BottomAppBar.this.materialShapeDrawable;
            float f = floatingActionButton.getVisibility() == 0 ? floatingActionButton.getScaleY() : 0.0f;
            materialShapeDrawable.setInterpolation(f);
        }

        @Override
        public void onTranslationChanged(FloatingActionButton floatingActionButton) {
            float f = floatingActionButton.getTranslationX();
            if (BottomAppBar.this.getTopEdgeTreatment().getHorizontalOffset() != f) {
                BottomAppBar.this.getTopEdgeTreatment().setHorizontalOffset(f);
                BottomAppBar.this.materialShapeDrawable.invalidateSelf();
            }
            float f2 = -floatingActionButton.getTranslationY();
            f = 0.0f;
            f2 = Math.max(0.0f, f2);
            if (BottomAppBar.this.getTopEdgeTreatment().getCradleVerticalOffset() != f2) {
                BottomAppBar.this.getTopEdgeTreatment().setCradleVerticalOffset(f2);
                BottomAppBar.this.materialShapeDrawable.invalidateSelf();
            }
            MaterialShapeDrawable materialShapeDrawable = BottomAppBar.this.materialShapeDrawable;
            if (floatingActionButton.getVisibility() == 0) {
                f = floatingActionButton.getScaleY();
            }
            materialShapeDrawable.setInterpolation(f);
        }
    };
    private boolean hideOnScroll;
    private int leftInset;
    private final MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
    private Animator menuAnimator;
    private Animator modeAnimator;
    private final boolean paddingBottomSystemWindowInsets;
    private final boolean paddingLeftSystemWindowInsets;
    private final boolean paddingRightSystemWindowInsets;
    private int rightInset;

    public BottomAppBar(Context context) {
        this(context, null, 0);
    }

    public BottomAppBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomAppBarStyle);
    }

    public BottomAppBar(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        context = this.getContext();
        Object object = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.BottomAppBar, n, DEF_STYLE_RES, new int[0]);
        ColorStateList colorStateList = MaterialResources.getColorStateList(context, object, R.styleable.BottomAppBar_backgroundTint);
        int n2 = object.getDimensionPixelSize(R.styleable.BottomAppBar_elevation, 0);
        float f = object.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleMargin, 0);
        float f2 = object.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0);
        float f3 = object.getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleVerticalOffset, 0);
        this.fabAlignmentMode = object.getInt(R.styleable.BottomAppBar_fabAlignmentMode, 0);
        this.fabAnimationMode = object.getInt(R.styleable.BottomAppBar_fabAnimationMode, 0);
        this.hideOnScroll = object.getBoolean(R.styleable.BottomAppBar_hideOnScroll, false);
        this.paddingBottomSystemWindowInsets = object.getBoolean(R.styleable.BottomAppBar_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = object.getBoolean(R.styleable.BottomAppBar_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = object.getBoolean(R.styleable.BottomAppBar_paddingRightSystemWindowInsets, false);
        object.recycle();
        this.fabOffsetEndMode = this.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
        object = new BottomAppBarTopEdgeTreatment(f, f2, f3);
        object = ShapeAppearanceModel.builder().setTopEdge((EdgeTreatment)object).build();
        this.materialShapeDrawable.setShapeAppearanceModel((ShapeAppearanceModel)object);
        this.materialShapeDrawable.setShadowCompatibilityMode(2);
        this.materialShapeDrawable.setPaintStyle(Paint.Style.FILL);
        this.materialShapeDrawable.initializeElevationOverlay(context);
        this.setElevation(n2);
        DrawableCompat.setTintList(this.materialShapeDrawable, colorStateList);
        ViewCompat.setBackground((View)this, this.materialShapeDrawable);
        ViewUtils.doOnApplyWindowInsets((View)this, attributeSet, n, DEF_STYLE_RES, new ViewUtils.OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                boolean bl;
                boolean bl2;
                if (BottomAppBar.this.paddingBottomSystemWindowInsets) {
                    BottomAppBar.this.bottomInset = windowInsetsCompat.getSystemWindowInsetBottom();
                }
                boolean bl3 = BottomAppBar.this.paddingLeftSystemWindowInsets;
                boolean bl4 = true;
                boolean bl5 = false;
                if (bl3) {
                    bl2 = BottomAppBar.this.leftInset != windowInsetsCompat.getSystemWindowInsetLeft();
                    BottomAppBar.this.leftInset = windowInsetsCompat.getSystemWindowInsetLeft();
                    bl = bl2;
                } else {
                    bl = false;
                }
                bl2 = bl5;
                if (BottomAppBar.this.paddingRightSystemWindowInsets) {
                    bl2 = BottomAppBar.this.rightInset != windowInsetsCompat.getSystemWindowInsetRight() ? bl4 : false;
                    BottomAppBar.this.rightInset = windowInsetsCompat.getSystemWindowInsetRight();
                }
                if (!bl) {
                    if (!bl2) return windowInsetsCompat;
                }
                BottomAppBar.this.cancelAnimations();
                BottomAppBar.this.setCutoutState();
                BottomAppBar.this.setActionMenuViewPosition();
                return windowInsetsCompat;
            }
        });
    }

    private void addFabAnimationListeners(FloatingActionButton floatingActionButton) {
        floatingActionButton.addOnHideAnimationListener((Animator.AnimatorListener)this.fabAnimationListener);
        floatingActionButton.addOnShowAnimationListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationStart(Animator object) {
                BottomAppBar.this.fabAnimationListener.onAnimationStart((Animator)object);
                object = BottomAppBar.this.findDependentFab();
                if (object == null) return;
                ((FloatingActionButton)object).setTranslationX(BottomAppBar.this.getFabTranslationX());
            }
        });
        floatingActionButton.addTransformationCallback(this.fabTransformationCallback);
    }

    private void cancelAnimations() {
        Animator animator2 = this.menuAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
        if ((animator2 = this.modeAnimator) == null) return;
        animator2.cancel();
    }

    private void createFabTranslationXAnimation(int n, List<Animator> list) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)this.findDependentFab(), (String)"translationX", (float[])new float[]{this.getFabTranslationX(n)});
        objectAnimator.setDuration(300L);
        list.add((Animator)objectAnimator);
    }

    private void createMenuViewTranslationAnimation(final int n, final boolean bl, List<Animator> list) {
        final ActionMenuView actionMenuView = this.getActionMenuView();
        if (actionMenuView == null) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)actionMenuView, (String)"alpha", (float[])new float[]{1.0f});
        if (Math.abs(actionMenuView.getTranslationX() - (float)this.getActionMenuViewTranslationX(actionMenuView, n, bl)) > 1.0f) {
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat((Object)actionMenuView, (String)"alpha", (float[])new float[]{0.0f});
            objectAnimator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){
                public boolean cancelled;

                public void onAnimationCancel(Animator animator2) {
                    this.cancelled = true;
                }

                public void onAnimationEnd(Animator animator2) {
                    if (this.cancelled) return;
                    BottomAppBar.this.translateActionMenuView(actionMenuView, n, bl);
                }
            });
            actionMenuView = new AnimatorSet();
            actionMenuView.setDuration(150L);
            actionMenuView.playSequentially(new Animator[]{objectAnimator2, objectAnimator});
            list.add((Animator)actionMenuView);
            return;
        }
        if (!(actionMenuView.getAlpha() < 1.0f)) return;
        list.add((Animator)objectAnimator);
    }

    private void dispatchAnimationEnd() {
        int n;
        this.animatingModeChangeCounter = n = this.animatingModeChangeCounter - 1;
        if (n != 0) return;
        ArrayList<AnimationListener> arrayList = this.animationListeners;
        if (arrayList == null) return;
        arrayList = arrayList.iterator();
        while (arrayList.hasNext()) {
            ((AnimationListener)arrayList.next()).onAnimationEnd(this);
        }
    }

    private void dispatchAnimationStart() {
        int n = this.animatingModeChangeCounter;
        this.animatingModeChangeCounter = n + 1;
        if (n != 0) return;
        ArrayList<AnimationListener> arrayList = this.animationListeners;
        if (arrayList == null) return;
        arrayList = arrayList.iterator();
        while (arrayList.hasNext()) {
            ((AnimationListener)arrayList.next()).onAnimationStart(this);
        }
    }

    private FloatingActionButton findDependentFab() {
        Object object = this.findDependentView();
        if (!(object instanceof FloatingActionButton)) return null;
        return (FloatingActionButton)object;
    }

    private View findDependentView() {
        View view;
        if (!(this.getParent() instanceof CoordinatorLayout)) {
            return null;
        }
        Iterator<View> iterator2 = ((CoordinatorLayout)this.getParent()).getDependents((View)this).iterator();
        do {
            if (!iterator2.hasNext()) return null;
            view = iterator2.next();
            if (view instanceof FloatingActionButton) return view;
        } while (!(view instanceof ExtendedFloatingActionButton));
        return view;
    }

    private ActionMenuView getActionMenuView() {
        int n = 0;
        while (n < this.getChildCount()) {
            View view = this.getChildAt(n);
            if (view instanceof ActionMenuView) {
                return (ActionMenuView)view;
            }
            ++n;
        }
        return null;
    }

    private int getBottomInset() {
        return this.bottomInset;
    }

    private float getFabTranslationX() {
        return this.getFabTranslationX(this.fabAlignmentMode);
    }

    private float getFabTranslationX(int n) {
        boolean bl = ViewUtils.isLayoutRtl((View)this);
        int n2 = 1;
        if (n != 1) return 0.0f;
        n = bl ? this.leftInset : this.rightInset;
        int n3 = this.fabOffsetEndMode;
        int n4 = this.getMeasuredWidth() / 2;
        if (!bl) return (n4 - (n3 + n)) * n2;
        n2 = -1;
        return (n4 - (n3 + n)) * n2;
    }

    private float getFabTranslationY() {
        return -this.getTopEdgeTreatment().getCradleVerticalOffset();
    }

    private int getLeftInset() {
        return this.leftInset;
    }

    private int getRightInset() {
        return this.rightInset;
    }

    private BottomAppBarTopEdgeTreatment getTopEdgeTreatment() {
        return (BottomAppBarTopEdgeTreatment)this.materialShapeDrawable.getShapeAppearanceModel().getTopEdge();
    }

    private boolean isFabVisibleOrWillBeShown() {
        FloatingActionButton floatingActionButton = this.findDependentFab();
        if (floatingActionButton == null) return false;
        if (!floatingActionButton.isOrWillBeShown()) return false;
        return true;
    }

    private void maybeAnimateMenuView(int n, boolean bl) {
        if (!ViewCompat.isLaidOut((View)this)) {
            return;
        }
        Object object = this.menuAnimator;
        if (object != null) {
            object.cancel();
        }
        object = new ArrayList();
        if (!this.isFabVisibleOrWillBeShown()) {
            n = 0;
            bl = false;
        }
        this.createMenuViewTranslationAnimation(n, bl, (List<Animator>)object);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether((Collection)object);
        this.menuAnimator = animatorSet;
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                BottomAppBar.this.dispatchAnimationEnd();
                BottomAppBar.this.menuAnimator = null;
            }

            public void onAnimationStart(Animator animator2) {
                BottomAppBar.this.dispatchAnimationStart();
            }
        });
        this.menuAnimator.start();
    }

    private void maybeAnimateModeChange(int n) {
        if (this.fabAlignmentMode == n) return;
        if (!ViewCompat.isLaidOut((View)this)) {
            return;
        }
        Object object = this.modeAnimator;
        if (object != null) {
            object.cancel();
        }
        object = new ArrayList();
        if (this.fabAnimationMode == 1) {
            this.createFabTranslationXAnimation(n, (List<Animator>)object);
        } else {
            this.createFabDefaultXAnimation(n, (List<Animator>)object);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether((Collection)object);
        this.modeAnimator = animatorSet;
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                BottomAppBar.this.dispatchAnimationEnd();
            }

            public void onAnimationStart(Animator animator2) {
                BottomAppBar.this.dispatchAnimationStart();
            }
        });
        this.modeAnimator.start();
    }

    private void setActionMenuViewPosition() {
        ActionMenuView actionMenuView = this.getActionMenuView();
        if (actionMenuView == null) return;
        actionMenuView.setAlpha(1.0f);
        if (!this.isFabVisibleOrWillBeShown()) {
            this.translateActionMenuView(actionMenuView, 0, false);
            return;
        }
        this.translateActionMenuView(actionMenuView, this.fabAlignmentMode, this.fabAttached);
    }

    private void setCutoutState() {
        this.getTopEdgeTreatment().setHorizontalOffset(this.getFabTranslationX());
        View view = this.findDependentView();
        MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
        float f = this.fabAttached && this.isFabVisibleOrWillBeShown() ? 1.0f : 0.0f;
        materialShapeDrawable.setInterpolation(f);
        if (view == null) return;
        view.setTranslationY(this.getFabTranslationY());
        view.setTranslationX(this.getFabTranslationX());
    }

    private void translateActionMenuView(ActionMenuView actionMenuView, int n, boolean bl) {
        actionMenuView.setTranslationX((float)this.getActionMenuViewTranslationX(actionMenuView, n, bl));
    }

    void addAnimationListener(AnimationListener animationListener) {
        if (this.animationListeners == null) {
            this.animationListeners = new ArrayList();
        }
        this.animationListeners.add(animationListener);
    }

    protected void createFabDefaultXAnimation(final int n, List<Animator> object) {
        object = this.findDependentFab();
        if (object == null) return;
        if (((FloatingActionButton)object).isOrWillBeHidden()) {
            return;
        }
        this.dispatchAnimationStart();
        ((FloatingActionButton)object).hide(new FloatingActionButton.OnVisibilityChangedListener(){

            @Override
            public void onHidden(FloatingActionButton floatingActionButton) {
                floatingActionButton.setTranslationX(BottomAppBar.this.getFabTranslationX(n));
                floatingActionButton.show(new FloatingActionButton.OnVisibilityChangedListener(){

                    @Override
                    public void onShown(FloatingActionButton floatingActionButton) {
                        BottomAppBar.this.dispatchAnimationEnd();
                    }
                });
            }

        });
    }

    protected int getActionMenuViewTranslationX(ActionMenuView actionMenuView, int n, boolean bl) {
        int n2;
        if (n != 1) return 0;
        if (!bl) {
            return 0;
        }
        bl = ViewUtils.isLayoutRtl((View)this);
        n = bl ? this.getMeasuredWidth() : 0;
        int n3 = n;
        for (n2 = 0; n2 < this.getChildCount(); ++n2) {
            View view = this.getChildAt(n2);
            boolean bl2 = view.getLayoutParams() instanceof Toolbar.LayoutParams && (((Toolbar.LayoutParams)view.getLayoutParams()).gravity & 8388615) == 8388611;
            n = n3;
            if (bl2) {
                n = bl ? Math.min(n3, view.getLeft()) : Math.max(n3, view.getRight());
            }
            n3 = n;
        }
        n = bl ? actionMenuView.getRight() : actionMenuView.getLeft();
        if (bl) {
            n2 = this.rightInset;
            return n3 - (n + n2);
        }
        n2 = -this.leftInset;
        return n3 - (n + n2);
    }

    public ColorStateList getBackgroundTint() {
        return this.materialShapeDrawable.getTintList();
    }

    @Override
    public Behavior getBehavior() {
        if (this.behavior != null) return this.behavior;
        this.behavior = new Behavior();
        return this.behavior;
    }

    public float getCradleVerticalOffset() {
        return this.getTopEdgeTreatment().getCradleVerticalOffset();
    }

    public int getFabAlignmentMode() {
        return this.fabAlignmentMode;
    }

    public int getFabAnimationMode() {
        return this.fabAnimationMode;
    }

    public float getFabCradleMargin() {
        return this.getTopEdgeTreatment().getFabCradleMargin();
    }

    public float getFabCradleRoundedCornerRadius() {
        return this.getTopEdgeTreatment().getFabCradleRoundedCornerRadius();
    }

    public boolean getHideOnScroll() {
        return this.hideOnScroll;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation((View)this, this.materialShapeDrawable);
        if (!(this.getParent() instanceof ViewGroup)) return;
        ((ViewGroup)this.getParent()).setClipChildren(false);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (bl) {
            this.cancelAnimations();
            this.setCutoutState();
        }
        this.setActionMenuViewPosition();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.fabAlignmentMode = parcelable.fabAlignmentMode;
        this.fabAttached = parcelable.fabAttached;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.fabAlignmentMode = this.fabAlignmentMode;
        savedState.fabAttached = this.fabAttached;
        return savedState;
    }

    public void performHide() {
        ((HideBottomViewOnScrollBehavior)this.getBehavior()).slideDown(this);
    }

    public void performShow() {
        ((HideBottomViewOnScrollBehavior)this.getBehavior()).slideUp(this);
    }

    void removeAnimationListener(AnimationListener animationListener) {
        ArrayList<AnimationListener> arrayList = this.animationListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animationListener);
    }

    public void replaceMenu(int n) {
        this.getMenu().clear();
        this.inflateMenu(n);
    }

    public void setBackgroundTint(ColorStateList colorStateList) {
        DrawableCompat.setTintList(this.materialShapeDrawable, colorStateList);
    }

    public void setCradleVerticalOffset(float f) {
        if (f == this.getCradleVerticalOffset()) return;
        this.getTopEdgeTreatment().setCradleVerticalOffset(f);
        this.materialShapeDrawable.invalidateSelf();
        this.setCutoutState();
    }

    public void setElevation(float f) {
        this.materialShapeDrawable.setElevation(f);
        int n = this.materialShapeDrawable.getShadowRadius();
        int n2 = this.materialShapeDrawable.getShadowOffsetY();
        ((HideBottomViewOnScrollBehavior)this.getBehavior()).setAdditionalHiddenOffsetY(this, n - n2);
    }

    public void setFabAlignmentMode(int n) {
        this.maybeAnimateModeChange(n);
        this.maybeAnimateMenuView(n, this.fabAttached);
        this.fabAlignmentMode = n;
    }

    public void setFabAnimationMode(int n) {
        this.fabAnimationMode = n;
    }

    public void setFabCradleMargin(float f) {
        if (f == this.getFabCradleMargin()) return;
        this.getTopEdgeTreatment().setFabCradleMargin(f);
        this.materialShapeDrawable.invalidateSelf();
    }

    public void setFabCradleRoundedCornerRadius(float f) {
        if (f == this.getFabCradleRoundedCornerRadius()) return;
        this.getTopEdgeTreatment().setFabCradleRoundedCornerRadius(f);
        this.materialShapeDrawable.invalidateSelf();
    }

    boolean setFabDiameter(int n) {
        float f = n;
        if (f == this.getTopEdgeTreatment().getFabDiameter()) return false;
        this.getTopEdgeTreatment().setFabDiameter(f);
        this.materialShapeDrawable.invalidateSelf();
        return true;
    }

    public void setHideOnScroll(boolean bl) {
        this.hideOnScroll = bl;
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override
    public void setTitle(CharSequence charSequence) {
    }

    static interface AnimationListener {
        public void onAnimationEnd(BottomAppBar var1);

        public void onAnimationStart(BottomAppBar var1);
    }

    public static class Behavior
    extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        private final Rect fabContentRect = new Rect();
        private final View.OnLayoutChangeListener fabLayoutListener = new View.OnLayoutChangeListener(){

            public void onLayoutChange(View object, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                BottomAppBar bottomAppBar = (BottomAppBar)Behavior.this.viewRef.get();
                if (bottomAppBar != null && object instanceof FloatingActionButton) {
                    FloatingActionButton floatingActionButton = (FloatingActionButton)object;
                    floatingActionButton.getMeasuredContentRect(Behavior.this.fabContentRect);
                    n = Behavior.this.fabContentRect.height();
                    bottomAppBar.setFabDiameter(n);
                    object = (CoordinatorLayout.LayoutParams)object.getLayoutParams();
                    if (Behavior.this.originalBottomMargin != 0) return;
                    n = (floatingActionButton.getMeasuredHeight() - n) / 2;
                    n2 = bottomAppBar.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin);
                    object.bottomMargin = bottomAppBar.getBottomInset() + (n2 - n);
                    object.leftMargin = bottomAppBar.getLeftInset();
                    object.rightMargin = bottomAppBar.getRightInset();
                    if (ViewUtils.isLayoutRtl((View)floatingActionButton)) {
                        object.leftMargin += bottomAppBar.fabOffsetEndMode;
                        return;
                    }
                    object.rightMargin += bottomAppBar.fabOffsetEndMode;
                    return;
                }
                object.removeOnLayoutChangeListener((View.OnLayoutChangeListener)this);
            }
        };
        private int originalBottomMargin;
        private WeakReference<BottomAppBar> viewRef;

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, int n) {
            this.viewRef = new WeakReference<BottomAppBar>(bottomAppBar);
            View view = bottomAppBar.findDependentView();
            if (view != null && !ViewCompat.isLaidOut(view)) {
                Object object = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
                object.anchorGravity = 49;
                this.originalBottomMargin = object.bottomMargin;
                if (view instanceof FloatingActionButton) {
                    object = (FloatingActionButton)view;
                    object.addOnLayoutChangeListener(this.fabLayoutListener);
                    bottomAppBar.addFabAnimationListeners((FloatingActionButton)object);
                }
                bottomAppBar.setCutoutState();
            }
            coordinatorLayout.onLayoutChild((View)bottomAppBar, n);
            return super.onLayoutChild(coordinatorLayout, bottomAppBar, n);
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, View view, View view2, int n, int n2) {
            if (!bottomAppBar.getHideOnScroll()) return false;
            if (!super.onStartNestedScroll(coordinatorLayout, bottomAppBar, view, view2, n, n2)) return false;
            return true;
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FabAlignmentMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FabAnimationMode {
    }

    static class SavedState
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
        int fabAlignmentMode;
        boolean fabAttached;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.fabAlignmentMode = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.fabAttached = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.fabAlignmentMode);
            parcel.writeInt((int)this.fabAttached);
        }

    }

}

