/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.PropertyValuesHolder
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.AnimatorTracker;
import com.google.android.material.floatingactionbutton.BaseMotionStrategy;
import com.google.android.material.floatingactionbutton.MotionStrategy;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.Iterator;
import java.util.List;

public class ExtendedFloatingActionButton
extends MaterialButton
implements CoordinatorLayout.AttachedBehavior {
    private static final int ANIM_STATE_HIDING = 1;
    private static final int ANIM_STATE_NONE = 0;
    private static final int ANIM_STATE_SHOWING = 2;
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon;
    static final Property<View, Float> HEIGHT;
    static final Property<View, Float> WIDTH;
    private int animState = 0;
    private final CoordinatorLayout.Behavior<ExtendedFloatingActionButton> behavior;
    private final AnimatorTracker changeVisibilityTracker = new AnimatorTracker();
    private final MotionStrategy extendStrategy;
    private final MotionStrategy hideStrategy = new HideStrategy(this.changeVisibilityTracker);
    private boolean isExtended = true;
    private final MotionStrategy showStrategy = new ShowStrategy(this.changeVisibilityTracker);
    private final MotionStrategy shrinkStrategy;

    static {
        WIDTH = new Property<View, Float>(Float.class, "width"){

            public Float get(View view) {
                return Float.valueOf(view.getLayoutParams().width);
            }

            public void set(View view, Float f) {
                view.getLayoutParams().width = f.intValue();
                view.requestLayout();
            }
        };
        HEIGHT = new Property<View, Float>(Float.class, "height"){

            public Float get(View view) {
                return Float.valueOf(view.getLayoutParams().height);
            }

            public void set(View view, Float f) {
                view.getLayoutParams().height = f.intValue();
                view.requestLayout();
            }
        };
    }

    public ExtendedFloatingActionButton(Context context) {
        this(context, null);
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.extendedFloatingActionButtonStyle);
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        Context context2 = this.getContext();
        this.behavior = new ExtendedFloatingActionButtonBehavior<ExtendedFloatingActionButton>(context2, attributeSet);
        context = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.ExtendedFloatingActionButton, n, DEF_STYLE_RES, new int[0]);
        MotionSpec motionSpec = MotionSpec.createFromAttribute(context2, (TypedArray)context, R.styleable.ExtendedFloatingActionButton_showMotionSpec);
        MotionSpec motionSpec2 = MotionSpec.createFromAttribute(context2, (TypedArray)context, R.styleable.ExtendedFloatingActionButton_hideMotionSpec);
        MotionSpec motionSpec3 = MotionSpec.createFromAttribute(context2, (TypedArray)context, R.styleable.ExtendedFloatingActionButton_extendMotionSpec);
        MotionSpec motionSpec4 = MotionSpec.createFromAttribute(context2, (TypedArray)context, R.styleable.ExtendedFloatingActionButton_shrinkMotionSpec);
        AnimatorTracker animatorTracker = new AnimatorTracker();
        this.extendStrategy = new ChangeSizeStrategy(animatorTracker, new Size(){

            @Override
            public int getHeight() {
                return ExtendedFloatingActionButton.this.getMeasuredHeight();
            }

            @Override
            public ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(-2, -2);
            }

            @Override
            public int getWidth() {
                return ExtendedFloatingActionButton.this.getMeasuredWidth();
            }
        }, true);
        this.shrinkStrategy = new ChangeSizeStrategy(animatorTracker, new Size(){

            @Override
            public int getHeight() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }

            @Override
            public ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(this.getWidth(), this.getHeight());
            }

            @Override
            public int getWidth() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }
        }, false);
        this.showStrategy.setMotionSpec(motionSpec);
        this.hideStrategy.setMotionSpec(motionSpec2);
        this.extendStrategy.setMotionSpec(motionSpec3);
        this.shrinkStrategy.setMotionSpec(motionSpec4);
        context.recycle();
        this.setShapeAppearanceModel(ShapeAppearanceModel.builder(context2, attributeSet, n, DEF_STYLE_RES, ShapeAppearanceModel.PILL).build());
    }

    private boolean isOrWillBeHidden() {
        int n = this.getVisibility();
        boolean bl = false;
        boolean bl2 = false;
        if (n == 0) {
            if (this.animState != 1) return bl2;
            return true;
        }
        bl2 = bl;
        if (this.animState == 2) return bl2;
        return true;
    }

    private boolean isOrWillBeShown() {
        int n = this.getVisibility();
        boolean bl = false;
        boolean bl2 = false;
        if (n != 0) {
            if (this.animState != 2) return bl2;
            return true;
        }
        bl2 = bl;
        if (this.animState == 1) return bl2;
        return true;
    }

    private void performMotion(MotionStrategy object, OnChangedCallback onChangedCallback) {
        if (object.shouldCancel()) {
            return;
        }
        if (!this.shouldAnimateVisibilityChange()) {
            object.performNow();
            object.onChange(onChangedCallback);
            return;
        }
        this.measure(0, 0);
        AnimatorSet animatorSet = object.createAnimator();
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter((MotionStrategy)object, onChangedCallback){
            private boolean cancelled;
            final /* synthetic */ OnChangedCallback val$callback;
            final /* synthetic */ MotionStrategy val$strategy;
            {
                this.val$strategy = motionStrategy;
                this.val$callback = onChangedCallback;
            }

            public void onAnimationCancel(Animator animator2) {
                this.cancelled = true;
                this.val$strategy.onAnimationCancel();
            }

            public void onAnimationEnd(Animator animator2) {
                this.val$strategy.onAnimationEnd();
                if (this.cancelled) return;
                this.val$strategy.onChange(this.val$callback);
            }

            public void onAnimationStart(Animator animator2) {
                this.val$strategy.onAnimationStart(animator2);
                this.cancelled = false;
            }
        });
        object = object.getListeners().iterator();
        do {
            if (!object.hasNext()) {
                animatorSet.start();
                return;
            }
            animatorSet.addListener((Animator.AnimatorListener)object.next());
        } while (true);
    }

    private boolean shouldAnimateVisibilityChange() {
        if (!ViewCompat.isLaidOut((View)this)) return false;
        if (this.isInEditMode()) return false;
        return true;
    }

    public void addOnExtendAnimationListener(Animator.AnimatorListener animatorListener) {
        this.extendStrategy.addAnimationListener(animatorListener);
    }

    public void addOnHideAnimationListener(Animator.AnimatorListener animatorListener) {
        this.hideStrategy.addAnimationListener(animatorListener);
    }

    public void addOnShowAnimationListener(Animator.AnimatorListener animatorListener) {
        this.showStrategy.addAnimationListener(animatorListener);
    }

    public void addOnShrinkAnimationListener(Animator.AnimatorListener animatorListener) {
        this.shrinkStrategy.addAnimationListener(animatorListener);
    }

    public void extend() {
        this.performMotion(this.extendStrategy, null);
    }

    public void extend(OnChangedCallback onChangedCallback) {
        this.performMotion(this.extendStrategy, onChangedCallback);
    }

    @Override
    public CoordinatorLayout.Behavior<ExtendedFloatingActionButton> getBehavior() {
        return this.behavior;
    }

    int getCollapsedSize() {
        return Math.min(ViewCompat.getPaddingStart((View)this), ViewCompat.getPaddingEnd((View)this)) * 2 + this.getIconSize();
    }

    public MotionSpec getExtendMotionSpec() {
        return this.extendStrategy.getMotionSpec();
    }

    public MotionSpec getHideMotionSpec() {
        return this.hideStrategy.getMotionSpec();
    }

    public MotionSpec getShowMotionSpec() {
        return this.showStrategy.getMotionSpec();
    }

    public MotionSpec getShrinkMotionSpec() {
        return this.shrinkStrategy.getMotionSpec();
    }

    public void hide() {
        this.performMotion(this.hideStrategy, null);
    }

    public void hide(OnChangedCallback onChangedCallback) {
        this.performMotion(this.hideStrategy, onChangedCallback);
    }

    public final boolean isExtended() {
        return this.isExtended;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.isExtended) return;
        if (!TextUtils.isEmpty((CharSequence)this.getText())) return;
        if (this.getIcon() == null) return;
        this.isExtended = false;
        this.shrinkStrategy.performNow();
    }

    public void removeOnExtendAnimationListener(Animator.AnimatorListener animatorListener) {
        this.extendStrategy.removeAnimationListener(animatorListener);
    }

    public void removeOnHideAnimationListener(Animator.AnimatorListener animatorListener) {
        this.hideStrategy.removeAnimationListener(animatorListener);
    }

    public void removeOnShowAnimationListener(Animator.AnimatorListener animatorListener) {
        this.showStrategy.removeAnimationListener(animatorListener);
    }

    public void removeOnShrinkAnimationListener(Animator.AnimatorListener animatorListener) {
        this.shrinkStrategy.removeAnimationListener(animatorListener);
    }

    public void setExtendMotionSpec(MotionSpec motionSpec) {
        this.extendStrategy.setMotionSpec(motionSpec);
    }

    public void setExtendMotionSpecResource(int n) {
        this.setExtendMotionSpec(MotionSpec.createFromResource(this.getContext(), n));
    }

    public void setExtended(boolean bl) {
        if (this.isExtended == bl) {
            return;
        }
        MotionStrategy motionStrategy = bl ? this.extendStrategy : this.shrinkStrategy;
        if (motionStrategy.shouldCancel()) {
            return;
        }
        motionStrategy.performNow();
    }

    public void setHideMotionSpec(MotionSpec motionSpec) {
        this.hideStrategy.setMotionSpec(motionSpec);
    }

    public void setHideMotionSpecResource(int n) {
        this.setHideMotionSpec(MotionSpec.createFromResource(this.getContext(), n));
    }

    public void setShowMotionSpec(MotionSpec motionSpec) {
        this.showStrategy.setMotionSpec(motionSpec);
    }

    public void setShowMotionSpecResource(int n) {
        this.setShowMotionSpec(MotionSpec.createFromResource(this.getContext(), n));
    }

    public void setShrinkMotionSpec(MotionSpec motionSpec) {
        this.shrinkStrategy.setMotionSpec(motionSpec);
    }

    public void setShrinkMotionSpecResource(int n) {
        this.setShrinkMotionSpec(MotionSpec.createFromResource(this.getContext(), n));
    }

    public void show() {
        this.performMotion(this.showStrategy, null);
    }

    public void show(OnChangedCallback onChangedCallback) {
        this.performMotion(this.showStrategy, onChangedCallback);
    }

    public void shrink() {
        this.performMotion(this.shrinkStrategy, null);
    }

    public void shrink(OnChangedCallback onChangedCallback) {
        this.performMotion(this.shrinkStrategy, onChangedCallback);
    }

    class ChangeSizeStrategy
    extends BaseMotionStrategy {
        private final boolean extending;
        private final Size size;

        ChangeSizeStrategy(AnimatorTracker animatorTracker, Size size, boolean bl) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
            this.size = size;
            this.extending = bl;
        }

        @Override
        public AnimatorSet createAnimator() {
            PropertyValuesHolder[] arrpropertyValuesHolder;
            MotionSpec motionSpec = this.getCurrentMotionSpec();
            if (motionSpec.hasPropertyValues("width")) {
                arrpropertyValuesHolder = motionSpec.getPropertyValues("width");
                arrpropertyValuesHolder[0].setFloatValues(new float[]{ExtendedFloatingActionButton.this.getWidth(), this.size.getWidth()});
                motionSpec.setPropertyValues("width", arrpropertyValuesHolder);
            }
            if (!motionSpec.hasPropertyValues("height")) return super.createAnimator(motionSpec);
            arrpropertyValuesHolder = motionSpec.getPropertyValues("height");
            arrpropertyValuesHolder[0].setFloatValues(new float[]{ExtendedFloatingActionButton.this.getHeight(), this.size.getHeight()});
            motionSpec.setPropertyValues("height", arrpropertyValuesHolder);
            return super.createAnimator(motionSpec);
        }

        @Override
        public int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_change_size_motion_spec;
        }

        @Override
        public void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            layoutParams.width = this.size.getLayoutParams().width;
            layoutParams.height = this.size.getLayoutParams().height;
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            super.onAnimationStart(animator2);
            ExtendedFloatingActionButton.this.isExtended = this.extending;
            ExtendedFloatingActionButton.this.setHorizontallyScrolling(true);
        }

        @Override
        public void onChange(OnChangedCallback onChangedCallback) {
            if (onChangedCallback == null) {
                return;
            }
            if (this.extending) {
                onChangedCallback.onExtended(ExtendedFloatingActionButton.this);
                return;
            }
            onChangedCallback.onShrunken(ExtendedFloatingActionButton.this);
        }

        @Override
        public void performNow() {
            ExtendedFloatingActionButton.this.isExtended = this.extending;
            ViewGroup.LayoutParams layoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            layoutParams.width = this.size.getLayoutParams().width;
            layoutParams.height = this.size.getLayoutParams().height;
            ExtendedFloatingActionButton.this.requestLayout();
        }

        @Override
        public boolean shouldCancel() {
            if (this.extending == ExtendedFloatingActionButton.this.isExtended) return true;
            if (ExtendedFloatingActionButton.this.getIcon() == null) return true;
            if (TextUtils.isEmpty((CharSequence)ExtendedFloatingActionButton.this.getText())) return true;
            return false;
        }
    }

    protected static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton>
    extends CoordinatorLayout.Behavior<T> {
        private static final boolean AUTO_HIDE_DEFAULT = false;
        private static final boolean AUTO_SHRINK_DEFAULT = true;
        private boolean autoHideEnabled;
        private boolean autoShrinkEnabled;
        private OnChangedCallback internalAutoHideCallback;
        private OnChangedCallback internalAutoShrinkCallback;
        private Rect tmpRect;

        public ExtendedFloatingActionButtonBehavior() {
            this.autoHideEnabled = false;
            this.autoShrinkEnabled = true;
        }

        public ExtendedFloatingActionButtonBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.ExtendedFloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = context.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoHide, false);
            this.autoShrinkEnabled = context.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoShrink, true);
            context.recycle();
        }

        private static boolean isBottomSheet(View view) {
            if (!((view = view.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams)) return false;
            return ((CoordinatorLayout.LayoutParams)view).getBehavior() instanceof BottomSheetBehavior;
        }

        private boolean shouldUpdateVisibility(View view, ExtendedFloatingActionButton object) {
            object = (CoordinatorLayout.LayoutParams)object.getLayoutParams();
            if (!this.autoHideEnabled && !this.autoShrinkEnabled) {
                return false;
            }
            if (((CoordinatorLayout.LayoutParams)((Object)object)).getAnchorId() == view.getId()) return true;
            return false;
        }

        private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, ExtendedFloatingActionButton extendedFloatingActionButton) {
            if (!this.shouldUpdateVisibility((View)appBarLayout, extendedFloatingActionButton)) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(coordinatorLayout, (View)appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                this.shrinkOrHide(extendedFloatingActionButton);
                return true;
            }
            this.extendOrShow(extendedFloatingActionButton);
            return true;
        }

        private boolean updateFabVisibilityForBottomSheet(View view, ExtendedFloatingActionButton extendedFloatingActionButton) {
            if (!this.shouldUpdateVisibility(view, extendedFloatingActionButton)) {
                return false;
            }
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)extendedFloatingActionButton.getLayoutParams();
            if (view.getTop() < extendedFloatingActionButton.getHeight() / 2 + layoutParams.topMargin) {
                this.shrinkOrHide(extendedFloatingActionButton);
                return true;
            }
            this.extendOrShow(extendedFloatingActionButton);
            return true;
        }

        protected void extendOrShow(ExtendedFloatingActionButton extendedFloatingActionButton) {
            OnChangedCallback onChangedCallback = this.autoShrinkEnabled ? this.internalAutoShrinkCallback : this.internalAutoHideCallback;
            MotionStrategy motionStrategy = this.autoShrinkEnabled ? extendedFloatingActionButton.extendStrategy : extendedFloatingActionButton.showStrategy;
            extendedFloatingActionButton.performMotion(motionStrategy, onChangedCallback);
        }

        @Override
        public boolean getInsetDodgeRect(CoordinatorLayout coordinatorLayout, ExtendedFloatingActionButton extendedFloatingActionButton, Rect rect) {
            return super.getInsetDodgeRect(coordinatorLayout, extendedFloatingActionButton, rect);
        }

        public boolean isAutoHideEnabled() {
            return this.autoHideEnabled;
        }

        public boolean isAutoShrinkEnabled() {
            return this.autoShrinkEnabled;
        }

        @Override
        public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges != 0) return;
            layoutParams.dodgeInsetEdges = 80;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, ExtendedFloatingActionButton extendedFloatingActionButton, View view) {
            if (view instanceof AppBarLayout) {
                this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, extendedFloatingActionButton);
                return false;
            }
            if (!ExtendedFloatingActionButtonBehavior.isBottomSheet(view)) return false;
            this.updateFabVisibilityForBottomSheet(view, extendedFloatingActionButton);
            return false;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, ExtendedFloatingActionButton extendedFloatingActionButton, int n) {
            View view;
            List<View> list = coordinatorLayout.getDependencies((View)extendedFloatingActionButton);
            int n2 = list.size();
            for (int i = 0; i < n2 && !((view = list.get(i)) instanceof AppBarLayout ? this.updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout)view, extendedFloatingActionButton) : ExtendedFloatingActionButtonBehavior.isBottomSheet(view) && this.updateFabVisibilityForBottomSheet(view, extendedFloatingActionButton)); ++i) {
            }
            coordinatorLayout.onLayoutChild((View)extendedFloatingActionButton, n);
            return true;
        }

        public void setAutoHideEnabled(boolean bl) {
            this.autoHideEnabled = bl;
        }

        public void setAutoShrinkEnabled(boolean bl) {
            this.autoShrinkEnabled = bl;
        }

        void setInternalAutoHideCallback(OnChangedCallback onChangedCallback) {
            this.internalAutoHideCallback = onChangedCallback;
        }

        void setInternalAutoShrinkCallback(OnChangedCallback onChangedCallback) {
            this.internalAutoShrinkCallback = onChangedCallback;
        }

        protected void shrinkOrHide(ExtendedFloatingActionButton extendedFloatingActionButton) {
            OnChangedCallback onChangedCallback = this.autoShrinkEnabled ? this.internalAutoShrinkCallback : this.internalAutoHideCallback;
            MotionStrategy motionStrategy = this.autoShrinkEnabled ? extendedFloatingActionButton.shrinkStrategy : extendedFloatingActionButton.hideStrategy;
            extendedFloatingActionButton.performMotion(motionStrategy, onChangedCallback);
        }
    }

    class HideStrategy
    extends BaseMotionStrategy {
        private boolean isCancelled;

        public HideStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        @Override
        public int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_hide_motion_spec;
        }

        @Override
        public void onAnimationCancel() {
            super.onAnimationCancel();
            this.isCancelled = true;
        }

        @Override
        public void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton.this.animState = 0;
            if (this.isCancelled) return;
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            super.onAnimationStart(animator2);
            this.isCancelled = false;
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.animState = 1;
        }

        @Override
        public void onChange(OnChangedCallback onChangedCallback) {
            if (onChangedCallback == null) return;
            onChangedCallback.onHidden(ExtendedFloatingActionButton.this);
        }

        @Override
        public void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        @Override
        public boolean shouldCancel() {
            return ExtendedFloatingActionButton.this.isOrWillBeHidden();
        }
    }

    public static abstract class OnChangedCallback {
        public void onExtended(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }

        public void onHidden(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }

        public void onShown(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }

        public void onShrunken(ExtendedFloatingActionButton extendedFloatingActionButton) {
        }
    }

    class ShowStrategy
    extends BaseMotionStrategy {
        public ShowStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        @Override
        public int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_show_motion_spec;
        }

        @Override
        public void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton.this.animState = 0;
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            super.onAnimationStart(animator2);
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.animState = 2;
        }

        @Override
        public void onChange(OnChangedCallback onChangedCallback) {
            if (onChangedCallback == null) return;
            onChangedCallback.onShown(ExtendedFloatingActionButton.this);
        }

        @Override
        public void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(0);
            ExtendedFloatingActionButton.this.setAlpha(1.0f);
            ExtendedFloatingActionButton.this.setScaleY(1.0f);
            ExtendedFloatingActionButton.this.setScaleX(1.0f);
        }

        @Override
        public boolean shouldCancel() {
            return ExtendedFloatingActionButton.this.isOrWillBeShown();
        }
    }

    static interface Size {
        public int getHeight();

        public ViewGroup.LayoutParams getLayoutParams();

        public int getWidth();
    }

}

