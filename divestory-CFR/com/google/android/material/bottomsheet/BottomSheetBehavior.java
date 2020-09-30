/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    private static final int CORNER_ANIMATION_DURATION = 500;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    private static final int SIGNIFICANT_VEL_THRESHOLD = 500;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";
    int activePointerId;
    private final ArrayList<BottomSheetCallback> callbacks = new ArrayList();
    private int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback(){

        private boolean releasedLow(View view) {
            if (view.getTop() <= (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2) return false;
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            return view.getLeft();
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            int n3 = BottomSheetBehavior.this.getExpandedOffset();
            if (BottomSheetBehavior.this.hideable) {
                n2 = BottomSheetBehavior.this.parentHeight;
                return MathUtils.clamp(n, n3, n2);
            }
            n2 = BottomSheetBehavior.this.collapsedOffset;
            return MathUtils.clamp(n, n3, n2);
        }

        @Override
        public int getViewVerticalDragRange(View view) {
            if (!BottomSheetBehavior.this.hideable) return BottomSheetBehavior.this.collapsedOffset;
            return BottomSheetBehavior.this.parentHeight;
        }

        @Override
        public void onViewDragStateChanged(int n) {
            if (n != 1) return;
            if (!BottomSheetBehavior.this.draggable) return;
            BottomSheetBehavior.this.setStateInternal(1);
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            BottomSheetBehavior.this.dispatchOnSlide(n2);
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public void onViewReleased(View var1_1, float var2_2, float var3_3) {
            block8 : {
                block16 : {
                    block17 : {
                        block4 : {
                            block14 : {
                                block15 : {
                                    block11 : {
                                        block13 : {
                                            block12 : {
                                                block6 : {
                                                    block10 : {
                                                        block9 : {
                                                            block7 : {
                                                                block2 : {
                                                                    block5 : {
                                                                        block3 : {
                                                                            var4_4 = 4;
                                                                            if (!(var3_3 < 0.0f)) break block2;
                                                                            if (!BottomSheetBehavior.access$500(BottomSheetBehavior.this)) break block3;
                                                                            var5_5 = BottomSheetBehavior.this.fitToContentsOffset;
                                                                            break block4;
                                                                        }
                                                                        if (var1_1.getTop() <= BottomSheetBehavior.this.halfExpandedOffset) break block5;
                                                                        var5_5 = BottomSheetBehavior.this.halfExpandedOffset;
                                                                        ** GOTO lbl65
                                                                    }
                                                                    var5_5 = BottomSheetBehavior.this.expandedOffset;
                                                                    break block4;
                                                                }
                                                                if (!BottomSheetBehavior.this.hideable || !BottomSheetBehavior.this.shouldHide(var1_1, var3_3)) break block6;
                                                                if (!(Math.abs(var2_2) < Math.abs(var3_3) && var3_3 > 500.0f) && !this.releasedLow(var1_1)) break block7;
                                                                var5_5 = BottomSheetBehavior.this.parentHeight;
                                                                var4_4 = 5;
                                                                break block8;
                                                            }
                                                            if (!BottomSheetBehavior.access$500(BottomSheetBehavior.this)) break block9;
                                                            var5_5 = BottomSheetBehavior.this.fitToContentsOffset;
                                                            break block4;
                                                        }
                                                        if (Math.abs(var1_1.getTop() - BottomSheetBehavior.this.expandedOffset) >= Math.abs(var1_1.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) break block10;
                                                        var5_5 = BottomSheetBehavior.this.expandedOffset;
                                                        break block4;
                                                    }
                                                    var5_5 = BottomSheetBehavior.this.halfExpandedOffset;
                                                    ** GOTO lbl65
                                                }
                                                if (var3_3 == 0.0f || Math.abs(var2_2) > Math.abs(var3_3)) break block11;
                                                if (!BottomSheetBehavior.access$500(BottomSheetBehavior.this)) break block12;
                                                var5_5 = BottomSheetBehavior.this.collapsedOffset;
                                                break block8;
                                            }
                                            var5_5 = var1_1.getTop();
                                            if (Math.abs(var5_5 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(var5_5 - BottomSheetBehavior.this.collapsedOffset)) break block13;
                                            var5_5 = BottomSheetBehavior.this.halfExpandedOffset;
                                            ** GOTO lbl65
                                        }
                                        var5_5 = BottomSheetBehavior.this.collapsedOffset;
                                        break block8;
                                    }
                                    var5_5 = var1_1.getTop();
                                    if (!BottomSheetBehavior.access$500(BottomSheetBehavior.this)) break block14;
                                    if (Math.abs(var5_5 - BottomSheetBehavior.this.fitToContentsOffset) >= Math.abs(var5_5 - BottomSheetBehavior.this.collapsedOffset)) break block15;
                                    var5_5 = BottomSheetBehavior.this.fitToContentsOffset;
                                    break block4;
                                }
                                var5_5 = BottomSheetBehavior.this.collapsedOffset;
                                break block8;
                            }
                            if (var5_5 >= BottomSheetBehavior.this.halfExpandedOffset) break block16;
                            if (var5_5 >= Math.abs(var5_5 - BottomSheetBehavior.this.collapsedOffset)) break block17;
                            var5_5 = BottomSheetBehavior.this.expandedOffset;
                        }
                        var4_4 = 3;
                        break block8;
                    }
                    var5_5 = BottomSheetBehavior.this.halfExpandedOffset;
                    ** GOTO lbl65
                }
                if (Math.abs(var5_5 - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(var5_5 - BottomSheetBehavior.this.collapsedOffset)) {
                    var5_5 = BottomSheetBehavior.this.halfExpandedOffset;
lbl65: // 5 sources:
                    var4_4 = 6;
                } else {
                    var5_5 = BottomSheetBehavior.this.collapsedOffset;
                }
            }
            BottomSheetBehavior.this.startSettlingAnimation(var1_1, var4_4, var5_5, true);
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            View view2;
            int n2 = BottomSheetBehavior.this.state;
            boolean bl = true;
            if (n2 == 1) {
                return false;
            }
            if (BottomSheetBehavior.this.touchingScrollingChild) {
                return false;
            }
            if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == n && (view2 = BottomSheetBehavior.this.nestedScrollingChildRef != null ? (View)BottomSheetBehavior.this.nestedScrollingChildRef.get() : null) != null && view2.canScrollVertically(-1)) {
                return false;
            }
            if (BottomSheetBehavior.this.viewRef == null) return false;
            if (BottomSheetBehavior.this.viewRef.get() != view) return false;
            return bl;
        }
    };
    private boolean draggable = true;
    float elevation = -1.0f;
    int expandedOffset;
    private boolean fitToContents = true;
    int fitToContentsOffset;
    private int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio = 0.5f;
    boolean hideable;
    private boolean ignoreEvents;
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;
    private ValueAnimator interpolatorAnimator;
    private boolean isShapeExpanded;
    private int lastNestedScrollDy;
    private MaterialShapeDrawable materialShapeDrawable;
    private float maximumVelocity;
    private boolean nestedScrolled;
    WeakReference<View> nestedScrollingChildRef;
    int parentHeight;
    int parentWidth;
    private int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    private int peekHeightMin;
    private int saveFlags = 0;
    private BottomSheetBehavior<V> settleRunnable = null;
    private ShapeAppearanceModel shapeAppearanceModelDefault;
    private boolean shapeThemingEnabled;
    private boolean skipCollapsed;
    int state = 4;
    boolean touchingScrollingChild;
    private boolean updateImportantForAccessibilityOnSiblings = false;
    private VelocityTracker velocityTracker;
    ViewDragHelper viewDragHelper;
    WeakReference<V> viewRef;

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet object) {
        super(context, (AttributeSet)object);
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray typedArray = context.obtainStyledAttributes(object, R.styleable.BottomSheetBehavior_Layout);
        this.shapeThemingEnabled = typedArray.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance);
        boolean bl = typedArray.hasValue(R.styleable.BottomSheetBehavior_Layout_backgroundTint);
        if (bl) {
            this.createMaterialShapeDrawable(context, (AttributeSet)object, bl, MaterialResources.getColorStateList(context, typedArray, R.styleable.BottomSheetBehavior_Layout_backgroundTint));
        } else {
            this.createMaterialShapeDrawable(context, (AttributeSet)object, bl);
        }
        this.createShapeValueAnimator();
        if (Build.VERSION.SDK_INT >= 21) {
            this.elevation = typedArray.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        }
        if ((object = typedArray.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight)) != null && object.data == -1) {
            this.setPeekHeight(object.data);
        } else {
            this.setPeekHeight(typedArray.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }
        this.setHideable(typedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        this.setGestureInsetBottomIgnored(typedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        this.setFitToContents(typedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        this.setSkipCollapsed(typedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        this.setDraggable(typedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        this.setSaveFlags(typedArray.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        this.setHalfExpandedRatio(typedArray.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        object = typedArray.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
        if (object != null && object.type == 16) {
            this.setExpandedOffset(object.data);
        } else {
            this.setExpandedOffset(typedArray.getDimensionPixelOffset(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0));
        }
        typedArray.recycle();
        this.maximumVelocity = ViewConfiguration.get((Context)context).getScaledMaximumFlingVelocity();
    }

    private void addAccessibilityActionForState(V v, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, final int n) {
        ViewCompat.replaceAccessibilityAction(v, accessibilityActionCompat, null, new AccessibilityViewCommand(){

            @Override
            public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                BottomSheetBehavior.this.setState(n);
                return true;
            }
        });
    }

    private void calculateCollapsedOffset() {
        int n = this.calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - n, this.fitToContentsOffset);
            return;
        }
        this.collapsedOffset = this.parentHeight - n;
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int)((float)this.parentHeight * (1.0f - this.halfExpandedRatio));
    }

    private int calculatePeekHeight() {
        if (this.peekHeightAuto) {
            return Math.min(Math.max(this.peekHeightMin, this.parentHeight - this.parentWidth * 9 / 16), this.childHeight);
        }
        if (this.gestureInsetBottomIgnored) return this.peekHeight;
        int n = this.gestureInsetBottom;
        if (n <= 0) return this.peekHeight;
        return Math.max(this.peekHeight, n + this.peekHeightGestureInsetBuffer);
    }

    private void createMaterialShapeDrawable(Context context, AttributeSet attributeSet, boolean bl) {
        this.createMaterialShapeDrawable(context, attributeSet, bl, null);
    }

    private void createMaterialShapeDrawable(Context context, AttributeSet object, boolean bl, ColorStateList colorStateList) {
        if (!this.shapeThemingEnabled) return;
        this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, (AttributeSet)object, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
        object = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
        this.materialShapeDrawable = object;
        ((MaterialShapeDrawable)object).initializeElevationOverlay(context);
        if (bl && colorStateList != null) {
            this.materialShapeDrawable.setFillColor(colorStateList);
            return;
        }
        object = new TypedValue();
        context.getTheme().resolveAttribute(16842801, (TypedValue)object, true);
        this.materialShapeDrawable.setTint(((TypedValue)object).data);
    }

    private void createShapeValueAnimator() {
        ValueAnimator valueAnimator;
        this.interpolatorAnimator = valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        valueAnimator.setDuration(500L);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.materialShapeDrawable == null) return;
                BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(f);
            }
        });
    }

    public static <V extends View> BottomSheetBehavior<V> from(V object) {
        if (!((object = object.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams)) throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        if (!((object = ((CoordinatorLayout.LayoutParams)((Object)object)).getBehavior()) instanceof BottomSheetBehavior)) throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        return (BottomSheetBehavior)object;
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    private void reset() {
        this.activePointerId = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) return;
        velocityTracker.recycle();
        this.velocityTracker = null;
    }

    private void restoreOptionalState(SavedState savedState) {
        int n = this.saveFlags;
        if (n == 0) {
            return;
        }
        if (n == -1 || (n & 1) == 1) {
            this.peekHeight = savedState.peekHeight;
        }
        if ((n = this.saveFlags) == -1 || (n & 2) == 2) {
            this.fitToContents = savedState.fitToContents;
        }
        if ((n = this.saveFlags) == -1 || (n & 4) == 4) {
            this.hideable = savedState.hideable;
        }
        if ((n = this.saveFlags) != -1) {
            if ((n & 8) != 8) return;
        }
        this.skipCollapsed = savedState.skipCollapsed;
    }

    private void setSystemGestureInsets(View view) {
        if (Build.VERSION.SDK_INT < 29) return;
        if (this.isGestureInsetBottomIgnored()) return;
        if (this.peekHeightAuto) return;
        ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                BottomSheetBehavior.this.gestureInsetBottom = windowInsetsCompat.getMandatorySystemGestureInsets().bottom;
                BottomSheetBehavior.this.updatePeekHeight(false);
                return windowInsetsCompat;
            }
        });
    }

    private void settleToStatePendingLayout(final int n) {
        final View view = (View)this.viewRef.get();
        if (view == null) {
            return;
        }
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent.isLayoutRequested() && ViewCompat.isAttachedToWindow(view)) {
            view.post(new Runnable(){

                @Override
                public void run() {
                    BottomSheetBehavior.this.settleToState(view, n);
                }
            });
            return;
        }
        this.settleToState(view, n);
    }

    private void updateAccessibilityActions() {
        View view = this.viewRef;
        if (view == null) {
            return;
        }
        if ((view = (View)view.get()) == null) {
            return;
        }
        ViewCompat.removeAccessibilityAction(view, 524288);
        ViewCompat.removeAccessibilityAction(view, 262144);
        ViewCompat.removeAccessibilityAction(view, 1048576);
        if (this.hideable && this.state != 5) {
            this.addAccessibilityActionForState((V)view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
        }
        int n = this.state;
        int n2 = 6;
        if (n != 3) {
            if (n != 4) {
                if (n != 6) {
                    return;
                }
                this.addAccessibilityActionForState((V)view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                this.addAccessibilityActionForState((V)view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
                return;
            }
            if (this.fitToContents) {
                n2 = 3;
            }
            this.addAccessibilityActionForState((V)view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, n2);
            return;
        }
        if (this.fitToContents) {
            n2 = 4;
        }
        this.addAccessibilityActionForState((V)view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, n2);
    }

    private void updateDrawableForTargetState(int n) {
        if (n == 2) {
            return;
        }
        boolean bl = n == 3;
        if (this.isShapeExpanded == bl) return;
        this.isShapeExpanded = bl;
        if (this.materialShapeDrawable == null) return;
        ValueAnimator valueAnimator = this.interpolatorAnimator;
        if (valueAnimator == null) return;
        if (valueAnimator.isRunning()) {
            this.interpolatorAnimator.reverse();
            return;
        }
        float f = bl ? 0.0f : 1.0f;
        this.interpolatorAnimator.setFloatValues(new float[]{1.0f - f, f});
        this.interpolatorAnimator.start();
    }

    private void updateImportantForAccessibility(boolean bl) {
        ViewParent viewParent = this.viewRef;
        if (viewParent == null) {
            return;
        }
        if (!((viewParent = ((View)viewParent.get()).getParent()) instanceof CoordinatorLayout)) {
            return;
        }
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)viewParent;
        int n = coordinatorLayout.getChildCount();
        if (Build.VERSION.SDK_INT >= 16 && bl) {
            if (this.importantForAccessibilityMap != null) return;
            this.importantForAccessibilityMap = new HashMap<View, Integer>(n);
        }
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (bl) return;
                this.importantForAccessibilityMap = null;
                return;
            }
            viewParent = coordinatorLayout.getChildAt(n2);
            if (viewParent != this.viewRef.get()) {
                Map<View, Integer> map;
                if (bl) {
                    if (Build.VERSION.SDK_INT >= 16) {
                        this.importantForAccessibilityMap.put((View)viewParent, viewParent.getImportantForAccessibility());
                    }
                    if (this.updateImportantForAccessibilityOnSiblings) {
                        ViewCompat.setImportantForAccessibility((View)viewParent, 4);
                    }
                } else if (this.updateImportantForAccessibilityOnSiblings && (map = this.importantForAccessibilityMap) != null && map.containsKey((Object)viewParent)) {
                    ViewCompat.setImportantForAccessibility((View)viewParent, this.importantForAccessibilityMap.get((Object)viewParent));
                }
            }
            ++n2;
        } while (true);
    }

    private void updatePeekHeight(boolean bl) {
        if (this.viewRef == null) return;
        this.calculateCollapsedOffset();
        if (this.state != 4) return;
        View view = (View)this.viewRef.get();
        if (view == null) return;
        if (bl) {
            this.settleToStatePendingLayout(this.state);
            return;
        }
        view.requestLayout();
    }

    public void addBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        if (this.callbacks.contains(bottomSheetCallback)) return;
        this.callbacks.add(bottomSheetCallback);
    }

    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    void dispatchOnSlide(int n) {
        float f;
        float f2;
        View view = (View)this.viewRef.get();
        if (view == null) return;
        if (this.callbacks.isEmpty()) return;
        int n2 = this.collapsedOffset;
        if (n <= n2 && n2 != this.getExpandedOffset()) {
            n2 = this.collapsedOffset;
            f = n2 - n;
            f2 = n2 - this.getExpandedOffset();
        } else {
            n2 = this.collapsedOffset;
            f = n2 - n;
            f2 = this.parentHeight - n2;
        }
        f2 = f / f2;
        n = 0;
        while (n < this.callbacks.size()) {
            this.callbacks.get(n).onSlide(view, f2);
            ++n;
        }
    }

    View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) return null;
        view = (ViewGroup)view;
        int n = 0;
        int n2 = view.getChildCount();
        while (n < n2) {
            View view2 = this.findScrollingChild(view.getChildAt(n));
            if (view2 != null) {
                return view2;
            }
            ++n;
        }
        return null;
    }

    public int getExpandedOffset() {
        if (!this.fitToContents) return this.expandedOffset;
        return this.fitToContentsOffset;
    }

    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    public int getPeekHeight() {
        if (!this.peekHeightAuto) return this.peekHeight;
        return -1;
    }

    int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public int getState() {
        return this.state;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public boolean isHideable() {
        return this.hideable;
    }

    @Override
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V object, MotionEvent motionEvent) {
        boolean bl = object.isShown();
        boolean bl2 = false;
        if (bl && this.draggable) {
            Object object2;
            int n = motionEvent.getActionMasked();
            if (n == 0) {
                this.reset();
            }
            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            }
            this.velocityTracker.addMovement(motionEvent);
            Object var7_7 = null;
            if (n != 0) {
                if (n == 1 || n == 3) {
                    this.touchingScrollingChild = false;
                    this.activePointerId = -1;
                    if (this.ignoreEvents) {
                        this.ignoreEvents = false;
                        return false;
                    }
                }
            } else {
                int n2 = (int)motionEvent.getX();
                this.initialY = (int)motionEvent.getY();
                if (this.state != 2 && (object2 = (object2 = this.nestedScrollingChildRef) != null ? (View)object2.get() : null) != null && coordinatorLayout.isPointInChildBounds((View)object2, n2, this.initialY)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
                bl = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds((View)object, n2, this.initialY);
                this.ignoreEvents = bl;
            }
            if (!this.ignoreEvents && (object = this.viewDragHelper) != null && ((ViewDragHelper)object).shouldInterceptTouchEvent(motionEvent)) {
                return true;
            }
            object2 = this.nestedScrollingChildRef;
            object = var7_7;
            if (object2 != null) {
                object = (View)object2.get();
            }
            bl = bl2;
            if (n != 2) return bl;
            bl = bl2;
            if (object == null) return bl;
            bl = bl2;
            if (this.ignoreEvents) return bl;
            bl = bl2;
            if (this.state == 1) return bl;
            bl = bl2;
            if (coordinatorLayout.isPointInChildBounds((View)object, (int)motionEvent.getX(), (int)motionEvent.getY())) return bl;
            bl = bl2;
            if (this.viewDragHelper == null) return bl;
            bl = bl2;
            if (!(Math.abs((float)this.initialY - motionEvent.getY()) > (float)this.viewDragHelper.getTouchSlop())) return bl;
            return true;
        }
        this.ignoreEvents = true;
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
        if (ViewCompat.getFitsSystemWindows((View)coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v)) {
            v.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            MaterialShapeDrawable materialShapeDrawable;
            this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            this.setSystemGestureInsets((View)v);
            this.viewRef = new WeakReference<V>(v);
            if (this.shapeThemingEnabled && (materialShapeDrawable = this.materialShapeDrawable) != null) {
                ViewCompat.setBackground(v, materialShapeDrawable);
            }
            if ((materialShapeDrawable = this.materialShapeDrawable) != null) {
                float f;
                float f2 = f = this.elevation;
                if (f == -1.0f) {
                    f2 = ViewCompat.getElevation(v);
                }
                materialShapeDrawable.setElevation(f2);
                boolean bl = this.state == 3;
                this.isShapeExpanded = bl;
                materialShapeDrawable = this.materialShapeDrawable;
                f2 = bl ? 0.0f : 1.0f;
                materialShapeDrawable.setInterpolation(f2);
            }
            this.updateAccessibilityActions();
            if (ViewCompat.getImportantForAccessibility(v) == 0) {
                ViewCompat.setImportantForAccessibility(v, 1);
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int n2 = v.getTop();
        coordinatorLayout.onLayoutChild((View)v, n);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentHeight = coordinatorLayout.getHeight();
        this.childHeight = n = v.getHeight();
        this.fitToContentsOffset = Math.max(0, this.parentHeight - n);
        this.calculateHalfExpandedOffset();
        this.calculateCollapsedOffset();
        n = this.state;
        if (n == 3) {
            ViewCompat.offsetTopAndBottom(v, this.getExpandedOffset());
        } else if (n == 6) {
            ViewCompat.offsetTopAndBottom(v, this.halfExpandedOffset);
        } else if (this.hideable && n == 5) {
            ViewCompat.offsetTopAndBottom(v, this.parentHeight);
        } else {
            n = this.state;
            if (n == 4) {
                ViewCompat.offsetTopAndBottom(v, this.collapsedOffset);
            } else if (n == 1 || n == 2) {
                ViewCompat.offsetTopAndBottom(v, n2 - v.getTop());
            }
        }
        this.nestedScrollingChildRef = new WeakReference<View>(this.findScrollingChild((View)v));
        return true;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
        boolean bl;
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        boolean bl2 = bl = false;
        if (weakReference == null) return bl2;
        bl2 = bl;
        if (view != weakReference.get()) return bl2;
        if (this.state != 3) return true;
        bl2 = bl;
        if (!super.onNestedPreFling(coordinatorLayout, v, view, f, f2)) return bl2;
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout weakReference, V v, View view, int n, int n2, int[] arrn, int n3) {
        if (n3 == 1) {
            return;
        }
        weakReference = this.nestedScrollingChildRef;
        weakReference = weakReference != null ? (View)weakReference.get() : null;
        if (view != weakReference) {
            return;
        }
        n = v.getTop();
        int n4 = n - n2;
        if (n2 > 0) {
            if (n4 < this.getExpandedOffset()) {
                arrn[1] = n - this.getExpandedOffset();
                ViewCompat.offsetTopAndBottom(v, -arrn[1]);
                this.setStateInternal(3);
            } else {
                if (!this.draggable) {
                    return;
                }
                arrn[1] = n2;
                ViewCompat.offsetTopAndBottom(v, -n2);
                this.setStateInternal(1);
            }
        } else if (n2 < 0 && !view.canScrollVertically(-1)) {
            n3 = this.collapsedOffset;
            if (n4 > n3 && !this.hideable) {
                arrn[1] = n - n3;
                ViewCompat.offsetTopAndBottom(v, -arrn[1]);
                this.setStateInternal(4);
            } else {
                if (!this.draggable) {
                    return;
                }
                arrn[1] = n2;
                ViewCompat.offsetTopAndBottom(v, -n2);
                this.setStateInternal(1);
            }
        }
        this.dispatchOnSlide(v.getTop());
        this.lastNestedScrollDy = n2;
        this.nestedScrolled = true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int n3, int n4, int n5, int[] arrn) {
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, parcelable.getSuperState());
        this.restoreOptionalState((SavedState)parcelable);
        if (parcelable.state != 1 && parcelable.state != 2) {
            this.state = parcelable.state;
            return;
        }
        this.state = 4;
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), this);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n, int n2) {
        boolean bl = false;
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        if ((n & 2) == 0) return bl;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout var1_1, V var2_2, View var3_3, int var4_4) {
        block6 : {
            block14 : {
                block8 : {
                    block13 : {
                        block11 : {
                            block12 : {
                                block9 : {
                                    block10 : {
                                        block7 : {
                                            block4 : {
                                                block5 : {
                                                    var4_4 = var2_2.getTop();
                                                    var5_5 = this.getExpandedOffset();
                                                    var6_6 = 3;
                                                    if (var4_4 == var5_5) {
                                                        this.setStateInternal(3);
                                                        return;
                                                    }
                                                    var1_1 = this.nestedScrollingChildRef;
                                                    if (var1_1 == null) return;
                                                    if (var3_3 != var1_1.get()) return;
                                                    if (!this.nestedScrolled) {
                                                        return;
                                                    }
                                                    if (this.lastNestedScrollDy <= 0) break block4;
                                                    if (!this.fitToContents) break block5;
                                                    var4_4 = this.fitToContentsOffset;
                                                    break block6;
                                                }
                                                var5_5 = var2_2.getTop();
                                                if (var5_5 > (var4_4 = this.halfExpandedOffset)) ** GOTO lbl60
                                                var4_4 = this.expandedOffset;
                                                break block6;
                                            }
                                            if (!this.hideable || !this.shouldHide((View)var2_2, this.getYVelocity())) break block7;
                                            var4_4 = this.parentHeight;
                                            var6_6 = 5;
                                            break block6;
                                        }
                                        if (this.lastNestedScrollDy != 0) break block8;
                                        var5_5 = var2_2.getTop();
                                        if (!this.fitToContents) break block9;
                                        if (Math.abs(var5_5 - this.fitToContentsOffset) >= Math.abs(var5_5 - this.collapsedOffset)) break block10;
                                        var4_4 = this.fitToContentsOffset;
                                        break block6;
                                    }
                                    var4_4 = this.collapsedOffset;
                                    ** GOTO lbl63
                                }
                                var4_4 = this.halfExpandedOffset;
                                if (var5_5 >= var4_4) break block11;
                                if (var5_5 >= Math.abs(var5_5 - this.collapsedOffset)) break block12;
                                var4_4 = this.expandedOffset;
                                break block6;
                            }
                            var4_4 = this.halfExpandedOffset;
                            ** GOTO lbl60
                        }
                        if (Math.abs(var5_5 - var4_4) >= Math.abs(var5_5 - this.collapsedOffset)) break block13;
                        var4_4 = this.halfExpandedOffset;
                        ** GOTO lbl60
                    }
                    var4_4 = this.collapsedOffset;
                    ** GOTO lbl63
                }
                if (!this.fitToContents) break block14;
                var4_4 = this.collapsedOffset;
                ** GOTO lbl63
            }
            var4_4 = var2_2.getTop();
            if (Math.abs(var4_4 - this.halfExpandedOffset) < Math.abs(var4_4 - this.collapsedOffset)) {
                var4_4 = this.halfExpandedOffset;
lbl60: // 4 sources:
                var6_6 = 6;
            } else {
                var4_4 = this.collapsedOffset;
lbl63: // 4 sources:
                var6_6 = 4;
            }
        }
        this.startSettlingAnimation((View)var2_2, var6_6, var4_4, false);
        this.nestedScrolled = false;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout object, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int n = motionEvent.getActionMasked();
        if (this.state == 1 && n == 0) {
            return true;
        }
        object = this.viewDragHelper;
        if (object != null) {
            ((ViewDragHelper)object).processTouchEvent(motionEvent);
        }
        if (n == 0) {
            this.reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (this.viewDragHelper == null) return this.ignoreEvents ^ true;
        if (n != 2) return this.ignoreEvents ^ true;
        if (this.ignoreEvents) return this.ignoreEvents ^ true;
        if (!(Math.abs((float)this.initialY - motionEvent.getY()) > (float)this.viewDragHelper.getTouchSlop())) return this.ignoreEvents ^ true;
        this.viewDragHelper.captureChildView((View)v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        return this.ignoreEvents ^ true;
    }

    public void removeBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.callbacks.remove(bottomSheetCallback);
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        Log.w((String)TAG, (String)"BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.callbacks.clear();
        if (bottomSheetCallback == null) return;
        this.callbacks.add(bottomSheetCallback);
    }

    public void setDraggable(boolean bl) {
        this.draggable = bl;
    }

    public void setExpandedOffset(int n) {
        if (n < 0) throw new IllegalArgumentException("offset must be greater than or equal to 0");
        this.expandedOffset = n;
    }

    public void setFitToContents(boolean bl) {
        if (this.fitToContents == bl) {
            return;
        }
        this.fitToContents = bl;
        if (this.viewRef != null) {
            this.calculateCollapsedOffset();
        }
        int n = this.fitToContents && this.state == 6 ? 3 : this.state;
        this.setStateInternal(n);
        this.updateAccessibilityActions();
    }

    public void setGestureInsetBottomIgnored(boolean bl) {
        this.gestureInsetBottomIgnored = bl;
    }

    public void setHalfExpandedRatio(float f) {
        if (f <= 0.0f) throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        if (f >= 1.0f) throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        this.halfExpandedRatio = f;
        if (this.viewRef == null) return;
        this.calculateHalfExpandedOffset();
    }

    public void setHideable(boolean bl) {
        if (this.hideable == bl) return;
        this.hideable = bl;
        if (!bl && this.state == 5) {
            this.setState(4);
        }
        this.updateAccessibilityActions();
    }

    public void setPeekHeight(int n) {
        this.setPeekHeight(n, false);
    }

    /*
     * Unable to fully structure code
     */
    public final void setPeekHeight(int var1_1, boolean var2_2) {
        block3 : {
            block2 : {
                var3_3 = 1;
                if (var1_1 != -1) break block2;
                if (this.peekHeightAuto) ** GOTO lbl-1000
                this.peekHeightAuto = true;
                var1_1 = var3_3;
                break block3;
            }
            if (!this.peekHeightAuto && this.peekHeight == var1_1) lbl-1000: // 2 sources:
            {
                var1_1 = 0;
            } else {
                this.peekHeightAuto = false;
                this.peekHeight = Math.max(0, var1_1);
                var1_1 = var3_3;
            }
        }
        if (var1_1 == 0) return;
        this.updatePeekHeight(var2_2);
    }

    public void setSaveFlags(int n) {
        this.saveFlags = n;
    }

    public void setSkipCollapsed(boolean bl) {
        this.skipCollapsed = bl;
    }

    public void setState(int n) {
        if (n == this.state) {
            return;
        }
        if (this.viewRef != null) {
            this.settleToStatePendingLayout(n);
            return;
        }
        if (n != 4 && n != 3 && n != 6) {
            if (!this.hideable) return;
            if (n != 5) return;
        }
        this.state = n;
    }

    void setStateInternal(int n) {
        if (this.state == n) {
            return;
        }
        this.state = n;
        View view = this.viewRef;
        if (view == null) {
            return;
        }
        if ((view = (View)view.get()) == null) {
            return;
        }
        int n2 = 0;
        if (n == 3) {
            this.updateImportantForAccessibility(true);
        } else if (n == 6 || n == 5 || n == 4) {
            this.updateImportantForAccessibility(false);
        }
        this.updateDrawableForTargetState(n);
        do {
            if (n2 >= this.callbacks.size()) {
                this.updateAccessibilityActions();
                return;
            }
            this.callbacks.get(n2).onStateChanged(view, n);
            ++n2;
        } while (true);
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean bl) {
        this.updateImportantForAccessibilityOnSiblings = bl;
    }

    void settleToState(View object, int n) {
        block9 : {
            int n2;
            block6 : {
                block8 : {
                    block7 : {
                        block5 : {
                            if (n != 4) break block5;
                            n2 = this.collapsedOffset;
                            break block6;
                        }
                        if (n != 6) break block7;
                        int n3 = this.halfExpandedOffset;
                        if (this.fitToContents && n3 <= (n2 = this.fitToContentsOffset)) {
                            n = 3;
                        } else {
                            n2 = n3;
                        }
                        break block6;
                    }
                    if (n != 3) break block8;
                    n2 = this.getExpandedOffset();
                    break block6;
                }
                if (!this.hideable || n != 5) break block9;
                n2 = this.parentHeight;
            }
            this.startSettlingAnimation((View)object, n, n2, false);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal state argument: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    boolean shouldHide(View view, float f) {
        boolean bl = this.skipCollapsed;
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (view.getTop() < this.collapsedOffset) {
            return false;
        }
        int n = this.calculatePeekHeight();
        if (!(Math.abs((float)view.getTop() + f * 0.1f - (float)this.collapsedOffset) / (float)n > 0.5f)) return false;
        return bl2;
    }

    void startSettlingAnimation(View view, int n, int n2, boolean bl) {
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        n2 = viewDragHelper != null && (bl ? viewDragHelper.settleCapturedViewAt(view.getLeft(), n2) : viewDragHelper.smoothSlideViewTo(view, view.getLeft(), n2)) ? 1 : 0;
        if (n2 == 0) {
            this.setStateInternal(n);
            return;
        }
        this.setStateInternal(2);
        this.updateDrawableForTargetState(n);
        if (this.settleRunnable == null) {
            this.settleRunnable = new SettleRunnable(view, n);
        }
        if (!this.settleRunnable.isPosted) {
            ((SettleRunnable)this.settleRunnable).targetState = n;
            ViewCompat.postOnAnimation(view, this.settleRunnable);
            this.settleRunnable.isPosted = true;
            return;
        }
        ((SettleRunnable)this.settleRunnable).targetState = n;
    }

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(View var1, float var2);

        public abstract void onStateChanged(View var1, int var2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SaveFlags {
    }

    protected static class SavedState
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
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
            this.peekHeight = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n == 1;
            this.fitToContents = bl2;
            bl2 = parcel.readInt() == 1;
            this.hideable = bl2;
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.skipCollapsed = bl2;
        }

        @Deprecated
        public SavedState(Parcelable parcelable, int n) {
            super(parcelable);
            this.state = n;
        }

        public SavedState(Parcelable parcelable, BottomSheetBehavior<?> bottomSheetBehavior) {
            super(parcelable);
            this.state = bottomSheetBehavior.state;
            this.peekHeight = bottomSheetBehavior.peekHeight;
            this.fitToContents = bottomSheetBehavior.fitToContents;
            this.hideable = bottomSheetBehavior.hideable;
            this.skipCollapsed = bottomSheetBehavior.skipCollapsed;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.state);
            parcel.writeInt(this.peekHeight);
            parcel.writeInt((int)this.fitToContents);
            parcel.writeInt((int)this.hideable);
            parcel.writeInt((int)this.skipCollapsed);
        }

    }

    private class SettleRunnable
    implements Runnable {
        private boolean isPosted;
        int targetState;
        private final View view;

        SettleRunnable(View view, int n) {
            this.view = view;
            this.targetState = n;
        }

        @Override
        public void run() {
            if (BottomSheetBehavior.this.viewDragHelper != null && BottomSheetBehavior.this.viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                BottomSheetBehavior.this.setStateInternal(this.targetState);
            }
            this.isPosted = false;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

}

