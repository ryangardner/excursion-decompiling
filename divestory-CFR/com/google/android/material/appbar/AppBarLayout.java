/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListView
 *  android.widget.ScrollView
 */
package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.HeaderBehavior;
import com.google.android.material.appbar.HeaderScrollingViewBehavior;
import com.google.android.material.appbar.ViewUtilsLollipop;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AppBarLayout
extends LinearLayout
implements CoordinatorLayout.AttachedBehavior {
    private static final int DEF_STYLE_RES = R.style.Widget_Design_AppBarLayout;
    private static final int INVALID_SCROLL_RANGE = -1;
    static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    static final int PENDING_ACTION_COLLAPSED = 2;
    static final int PENDING_ACTION_EXPANDED = 1;
    static final int PENDING_ACTION_FORCE = 8;
    static final int PENDING_ACTION_NONE = 0;
    private int currentOffset;
    private int downPreScrollRange = -1;
    private int downScrollRange = -1;
    private ValueAnimator elevationOverlayAnimator;
    private boolean haveChildWithInterpolator;
    private WindowInsetsCompat lastInsets;
    private boolean liftOnScroll;
    private WeakReference<View> liftOnScrollTargetView;
    private int liftOnScrollTargetViewId;
    private boolean liftable;
    private boolean liftableOverride;
    private boolean lifted;
    private List<BaseOnOffsetChangedListener> listeners;
    private int pendingAction = 0;
    private Drawable statusBarForeground;
    private int[] tmpStatesArray;
    private int totalScrollRange = -1;

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.appBarLayoutStyle);
    }

    public AppBarLayout(Context context, AttributeSet object, int n) {
        super(MaterialThemeOverlay.wrap(context, (AttributeSet)object, n, DEF_STYLE_RES), (AttributeSet)object, n);
        context = this.getContext();
        this.setOrientation(1);
        if (Build.VERSION.SDK_INT >= 21) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider((View)this);
            ViewUtilsLollipop.setStateListAnimatorFromAttrs((View)this, (AttributeSet)object, n, DEF_STYLE_RES);
        }
        TypedArray typedArray = ThemeEnforcement.obtainStyledAttributes(context, (AttributeSet)object, R.styleable.AppBarLayout, n, DEF_STYLE_RES, new int[0]);
        ViewCompat.setBackground((View)this, typedArray.getDrawable(R.styleable.AppBarLayout_android_background));
        if (this.getBackground() instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable)this.getBackground();
            object = new MaterialShapeDrawable();
            ((MaterialShapeDrawable)object).setFillColor(ColorStateList.valueOf((int)colorDrawable.getColor()));
            ((MaterialShapeDrawable)object).initializeElevationOverlay(context);
            ViewCompat.setBackground((View)this, (Drawable)object);
        }
        if (typedArray.hasValue(R.styleable.AppBarLayout_expanded)) {
            this.setExpanded(typedArray.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
        }
        if (Build.VERSION.SDK_INT >= 21 && typedArray.hasValue(R.styleable.AppBarLayout_elevation)) {
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator((View)this, typedArray.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (typedArray.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                this.setKeyboardNavigationCluster(typedArray.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (typedArray.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                this.setTouchscreenBlocksFocus(typedArray.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        this.liftOnScroll = typedArray.getBoolean(R.styleable.AppBarLayout_liftOnScroll, false);
        this.liftOnScrollTargetViewId = typedArray.getResourceId(R.styleable.AppBarLayout_liftOnScrollTargetViewId, -1);
        this.setStatusBarForeground(typedArray.getDrawable(R.styleable.AppBarLayout_statusBarForeground));
        typedArray.recycle();
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return AppBarLayout.this.onWindowInsetChanged(windowInsetsCompat);
            }
        });
    }

    private void clearLiftOnScrollTargetView() {
        WeakReference<View> weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.liftOnScrollTargetView = null;
    }

    private View findLiftOnScrollTargetView(View object) {
        int n;
        View view = this.liftOnScrollTargetView;
        Object var3_3 = null;
        if (view == null && (n = this.liftOnScrollTargetViewId) != -1) {
            object = object != null ? object.findViewById(n) : null;
            view = object;
            if (object == null) {
                view = object;
                if (this.getParent() instanceof ViewGroup) {
                    view = ((ViewGroup)this.getParent()).findViewById(this.liftOnScrollTargetViewId);
                }
            }
            if (view != null) {
                this.liftOnScrollTargetView = new WeakReference<View>(view);
            }
        }
        view = this.liftOnScrollTargetView;
        object = var3_3;
        if (view == null) return object;
        return (View)view.get();
    }

    private boolean hasCollapsibleChild() {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            if (((LayoutParams)this.getChildAt(n2).getLayoutParams()).isCollapsible()) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void invalidateScrollRanges() {
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
    }

    private void setExpanded(boolean bl, boolean bl2, boolean bl3) {
        int n = bl ? 1 : 2;
        int n2 = 0;
        int n3 = bl2 ? 4 : 0;
        if (bl3) {
            n2 = 8;
        }
        this.pendingAction = n | n3 | n2;
        this.requestLayout();
    }

    private boolean setLiftableState(boolean bl) {
        if (this.liftable == bl) return false;
        this.liftable = bl;
        this.refreshDrawableState();
        return true;
    }

    private boolean shouldDrawStatusBarForeground() {
        if (this.statusBarForeground == null) return false;
        if (this.getTopInset() <= 0) return false;
        return true;
    }

    private boolean shouldOffsetFirstChild() {
        boolean bl;
        int n = this.getChildCount();
        boolean bl2 = bl = false;
        if (n <= 0) return bl2;
        View view = this.getChildAt(0);
        bl2 = bl;
        if (view.getVisibility() == 8) return bl2;
        bl2 = bl;
        if (ViewCompat.getFitsSystemWindows(view)) return bl2;
        return true;
    }

    private void startLiftOnScrollElevationOverlayAnimation(final MaterialShapeDrawable materialShapeDrawable, boolean bl) {
        float f = this.getResources().getDimension(R.dimen.design_appbar_elevation);
        float f2 = bl ? 0.0f : f;
        if (!bl) {
            f = 0.0f;
        }
        ValueAnimator valueAnimator = this.elevationOverlayAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.elevationOverlayAnimator = valueAnimator = ValueAnimator.ofFloat((float[])new float[]{f2, f});
        valueAnimator.setDuration((long)this.getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
        this.elevationOverlayAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        this.elevationOverlayAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                materialShapeDrawable.setElevation(((Float)valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.elevationOverlayAnimator.start();
    }

    private void updateWillNotDraw() {
        this.setWillNotDraw(this.shouldDrawStatusBarForeground() ^ true);
    }

    public void addOnOffsetChangedListener(BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList<BaseOnOffsetChangedListener>();
        }
        if (baseOnOffsetChangedListener == null) return;
        if (this.listeners.contains(baseOnOffsetChangedListener)) return;
        this.listeners.add(baseOnOffsetChangedListener);
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        this.addOnOffsetChangedListener((BaseOnOffsetChangedListener)onOffsetChangedListener);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!this.shouldDrawStatusBarForeground()) return;
        int n = canvas.save();
        canvas.translate(0.0f, (float)(-this.currentOffset));
        this.statusBarForeground.draw(canvas);
        canvas.restoreToCount(n);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        Drawable drawable2 = this.statusBarForeground;
        if (drawable2 == null) return;
        if (!drawable2.isStateful()) return;
        if (!drawable2.setState(arrn)) return;
        this.invalidateDrawable(drawable2);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT >= 19 && layoutParams instanceof LinearLayout.LayoutParams) {
            return new LayoutParams((LinearLayout.LayoutParams)layoutParams);
        }
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) return new LayoutParams(layoutParams);
        return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
    }

    @Override
    public CoordinatorLayout.Behavior<AppBarLayout> getBehavior() {
        return new Behavior();
    }

    /*
     * Unable to fully structure code
     */
    int getDownNestedPreScrollRange() {
        var1_1 = this.downPreScrollRange;
        if (var1_1 != -1) {
            return var1_1;
        }
        var3_3 = 0;
        for (var2_2 = this.getChildCount() - 1; var2_2 >= 0; --var2_2) {
            block8 : {
                block6 : {
                    block7 : {
                        var4_4 = this.getChildAt(var2_2);
                        var5_5 = (LayoutParams)var4_4.getLayoutParams();
                        var6_6 = var4_4.getMeasuredHeight();
                        var1_1 = var5_5.scrollFlags;
                        if ((var1_1 & 5) != 5) break block6;
                        var7_7 = var5_5.topMargin + var5_5.bottomMargin;
                        if ((var1_1 & 8) == 0) break block7;
                        var1_1 = ViewCompat.getMinimumHeight(var4_4);
                        ** GOTO lbl18
                    }
                    if ((var1_1 & 2) != 0) {
                        var1_1 = var6_6 - ViewCompat.getMinimumHeight(var4_4);
lbl18: // 2 sources:
                        var1_1 = var7_7 + var1_1;
                    } else {
                        var1_1 = var7_7 + var6_6;
                    }
                    var7_7 = var1_1;
                    if (var2_2 == 0) {
                        var7_7 = var1_1;
                        if (ViewCompat.getFitsSystemWindows(var4_4)) {
                            var7_7 = Math.min(var1_1, var6_6 - this.getTopInset());
                        }
                    }
                    var1_1 = var3_3 + var7_7;
                    break block8;
                }
                var1_1 = var3_3;
                if (var3_3 > 0) break;
            }
            var3_3 = var1_1;
        }
        this.downPreScrollRange = var1_1 = Math.max(0, var3_3);
        return var1_1;
    }

    int getDownNestedScrollRange() {
        int n;
        int n2 = this.downScrollRange;
        if (n2 != -1) {
            return n2;
        }
        int n3 = this.getChildCount();
        int n4 = 0;
        n2 = 0;
        do {
            n = n2;
            if (n4 >= n3) break;
            View view = this.getChildAt(n4);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n5 = view.getMeasuredHeight();
            int n6 = layoutParams.topMargin;
            int n7 = layoutParams.bottomMargin;
            int n8 = layoutParams.scrollFlags;
            n = n2;
            if ((n8 & 1) == 0) break;
            n2 += n5 + (n6 + n7);
            if ((n8 & 2) != 0) {
                n = n2 - ViewCompat.getMinimumHeight(view);
                break;
            }
            ++n4;
        } while (true);
        this.downScrollRange = n2 = Math.max(0, n);
        return n2;
    }

    public int getLiftOnScrollTargetViewId() {
        return this.liftOnScrollTargetViewId;
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int n = this.getTopInset();
        int n2 = ViewCompat.getMinimumHeight((View)this);
        if (n2 != 0) {
            return n2 * 2 + n;
        }
        n2 = this.getChildCount();
        n2 = n2 >= 1 ? ViewCompat.getMinimumHeight(this.getChildAt(n2 - 1)) : 0;
        if (n2 == 0) return this.getHeight() / 3;
        return n2 * 2 + n;
    }

    int getPendingAction() {
        return this.pendingAction;
    }

    public Drawable getStatusBarForeground() {
        return this.statusBarForeground;
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat == null) return 0;
        return windowInsetsCompat.getSystemWindowInsetTop();
    }

    public final int getTotalScrollRange() {
        int n;
        int n2 = this.totalScrollRange;
        if (n2 != -1) {
            return n2;
        }
        int n3 = this.getChildCount();
        int n4 = 0;
        n2 = 0;
        do {
            n = n2;
            if (n4 >= n3) break;
            View view = this.getChildAt(n4);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n5 = view.getMeasuredHeight();
            int n6 = layoutParams.scrollFlags;
            n = n2;
            if ((n6 & 1) == 0) break;
            n2 = n = n2 + (n5 + layoutParams.topMargin + layoutParams.bottomMargin);
            if (n4 == 0) {
                n2 = n;
                if (ViewCompat.getFitsSystemWindows(view)) {
                    n2 = n - this.getTopInset();
                }
            }
            if ((n6 & 2) != 0) {
                n = n2 - ViewCompat.getMinimumHeight(view);
                break;
            }
            ++n4;
        } while (true);
        this.totalScrollRange = n2 = Math.max(0, n);
        return n2;
    }

    int getUpNestedPreScrollRange() {
        return this.getTotalScrollRange();
    }

    boolean hasChildWithInterpolator() {
        return this.haveChildWithInterpolator;
    }

    boolean hasScrollableChildren() {
        if (this.getTotalScrollRange() == 0) return false;
        return true;
    }

    public boolean isLiftOnScroll() {
        return this.liftOnScroll;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation((View)this);
    }

    protected int[] onCreateDrawableState(int n) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] arrn = this.tmpStatesArray;
        int[] arrn2 = super.onCreateDrawableState(n + arrn.length);
        n = this.liftable ? R.attr.state_liftable : -R.attr.state_liftable;
        arrn[0] = n;
        n = this.liftable && this.lifted ? R.attr.state_lifted : -R.attr.state_lifted;
        arrn[1] = n;
        n = this.liftable ? R.attr.state_collapsible : -R.attr.state_collapsible;
        arrn[2] = n;
        n = this.liftable && this.lifted ? R.attr.state_collapsed : -R.attr.state_collapsed;
        arrn[3] = n;
        return AppBarLayout.mergeDrawableStates((int[])arrn2, (int[])arrn);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.clearLiftOnScrollTargetView();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Drawable drawable2;
        super.onLayout(bl, n, n2, n3, n4);
        bl = ViewCompat.getFitsSystemWindows((View)this);
        boolean bl2 = true;
        if (bl && this.shouldOffsetFirstChild()) {
            n2 = this.getTopInset();
            for (n = this.getChildCount() - 1; n >= 0; --n) {
                ViewCompat.offsetTopAndBottom(this.getChildAt(n), n2);
            }
        }
        this.invalidateScrollRanges();
        this.haveChildWithInterpolator = false;
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            if (((LayoutParams)this.getChildAt(n).getLayoutParams()).getScrollInterpolator() == null) continue;
            this.haveChildWithInterpolator = true;
            break;
        }
        if ((drawable2 = this.statusBarForeground) != null) {
            drawable2.setBounds(0, 0, this.getWidth(), this.getTopInset());
        }
        if (this.liftableOverride) return;
        bl = bl2;
        if (!this.liftOnScroll) {
            bl = this.hasCollapsibleChild() ? bl2 : false;
        }
        this.setLiftableState(bl);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = View.MeasureSpec.getMode((int)n2);
        if (n3 != 1073741824 && ViewCompat.getFitsSystemWindows((View)this) && this.shouldOffsetFirstChild()) {
            n = this.getMeasuredHeight();
            if (n3 != Integer.MIN_VALUE) {
                if (n3 == 0) {
                    n += this.getTopInset();
                }
            } else {
                n = MathUtils.clamp(this.getMeasuredHeight() + this.getTopInset(), 0, View.MeasureSpec.getSize((int)n2));
            }
            this.setMeasuredDimension(this.getMeasuredWidth(), n);
        }
        this.invalidateScrollRanges();
    }

    void onOffsetChanged(int n) {
        Object object;
        this.currentOffset = n;
        if (!this.willNotDraw()) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
        if ((object = this.listeners) == null) return;
        int n2 = 0;
        int n3 = object.size();
        while (n2 < n3) {
            object = this.listeners.get(n2);
            if (object != null) {
                object.onOffsetChanged(this, n);
            }
            ++n2;
        }
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows((View)this) ? windowInsetsCompat : null;
        if (ObjectsCompat.equals(this.lastInsets, windowInsetsCompat2)) return windowInsetsCompat;
        this.lastInsets = windowInsetsCompat2;
        this.updateWillNotDraw();
        this.requestLayout();
        return windowInsetsCompat;
    }

    public void removeOnOffsetChangedListener(BaseOnOffsetChangedListener baseOnOffsetChangedListener) {
        List<BaseOnOffsetChangedListener> list = this.listeners;
        if (list == null) return;
        if (baseOnOffsetChangedListener == null) return;
        list.remove(baseOnOffsetChangedListener);
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        this.removeOnOffsetChangedListener((BaseOnOffsetChangedListener)onOffsetChangedListener);
    }

    void resetPendingAction() {
        this.pendingAction = 0;
    }

    public void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation((View)this, f);
    }

    public void setExpanded(boolean bl) {
        this.setExpanded(bl, ViewCompat.isLaidOut((View)this));
    }

    public void setExpanded(boolean bl, boolean bl2) {
        this.setExpanded(bl, bl2, true);
    }

    public void setLiftOnScroll(boolean bl) {
        this.liftOnScroll = bl;
    }

    public void setLiftOnScrollTargetViewId(int n) {
        this.liftOnScrollTargetViewId = n;
        this.clearLiftOnScrollTargetView();
    }

    public boolean setLiftable(boolean bl) {
        this.liftableOverride = true;
        return this.setLiftableState(bl);
    }

    public boolean setLifted(boolean bl) {
        return this.setLiftedState(bl);
    }

    boolean setLiftedState(boolean bl) {
        if (this.lifted == bl) return false;
        this.lifted = bl;
        this.refreshDrawableState();
        if (!this.liftOnScroll) return true;
        if (!(this.getBackground() instanceof MaterialShapeDrawable)) return true;
        this.startLiftOnScrollElevationOverlayAnimation((MaterialShapeDrawable)this.getBackground(), bl);
        return true;
    }

    public void setOrientation(int n) {
        if (n != 1) throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        super.setOrientation(n);
    }

    public void setStatusBarForeground(Drawable drawable2) {
        Drawable drawable3 = this.statusBarForeground;
        if (drawable3 == drawable2) return;
        Drawable drawable4 = null;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        if (drawable2 != null) {
            drawable4 = drawable2.mutate();
        }
        this.statusBarForeground = drawable4;
        if (drawable4 != null) {
            if (drawable4.isStateful()) {
                this.statusBarForeground.setState(this.getDrawableState());
            }
            DrawableCompat.setLayoutDirection(this.statusBarForeground, ViewCompat.getLayoutDirection((View)this));
            drawable2 = this.statusBarForeground;
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
            this.statusBarForeground.setCallback((Drawable.Callback)this);
        }
        this.updateWillNotDraw();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setStatusBarForegroundColor(int n) {
        this.setStatusBarForeground((Drawable)new ColorDrawable(n));
    }

    public void setStatusBarForegroundResource(int n) {
        this.setStatusBarForeground(AppCompatResources.getDrawable(this.getContext(), n));
    }

    @Deprecated
    public void setTargetElevation(float f) {
        if (Build.VERSION.SDK_INT < 21) return;
        ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator((View)this, f);
    }

    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = n == 0;
        Drawable drawable2 = this.statusBarForeground;
        if (drawable2 == null) return;
        drawable2.setVisible(bl, false);
    }

    boolean shouldLift(View view) {
        View view2 = this.findLiftOnScrollTargetView(view);
        if (view2 != null) {
            view = view2;
        }
        if (view == null) return false;
        if (view.canScrollVertically(-1)) return true;
        if (view.getScrollY() <= 0) return false;
        return true;
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        if (super.verifyDrawable(drawable2)) return true;
        if (drawable2 == this.statusBarForeground) return true;
        return false;
    }

    protected static class BaseBehavior<T extends AppBarLayout>
    extends HeaderBehavior<T> {
        private static final int INVALID_POSITION = -1;
        private static final int MAX_OFFSET_ANIMATION_DURATION = 600;
        private WeakReference<View> lastNestedScrollingChildRef;
        private int lastStartedType;
        private ValueAnimator offsetAnimator;
        private int offsetDelta;
        private int offsetToChildIndexOnLayout = -1;
        private boolean offsetToChildIndexOnLayoutIsMinHeight;
        private float offsetToChildIndexOnLayoutPerc;
        private BaseDragCallback onDragCallback;

        public BaseBehavior() {
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        private void addAccessibilityScrollActions(final CoordinatorLayout coordinatorLayout, T t, View view) {
            if (this.getTopBottomOffsetForScrollingSibling() != -((AppBarLayout)t).getTotalScrollRange() && view.canScrollVertically(1)) {
                this.addActionToExpand(coordinatorLayout, t, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, false);
            }
            if (this.getTopBottomOffsetForScrollingSibling() == 0) return;
            if (view.canScrollVertically(-1)) {
                int n = -((AppBarLayout)t).getDownNestedPreScrollRange();
                if (n == 0) return;
                ViewCompat.replaceAccessibilityAction((View)coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, null, new AccessibilityViewCommand((AppBarLayout)t, view, n){
                    final /* synthetic */ AppBarLayout val$appBarLayout;
                    final /* synthetic */ int val$dy;
                    final /* synthetic */ View val$scrollingView;
                    {
                        this.val$appBarLayout = appBarLayout;
                        this.val$scrollingView = view;
                        this.val$dy = n;
                    }

                    @Override
                    public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                        BaseBehavior.this.onNestedPreScroll(coordinatorLayout, this.val$appBarLayout, this.val$scrollingView, 0, this.val$dy, new int[]{0, 0}, 1);
                        return true;
                    }
                });
                return;
            }
            this.addActionToExpand(coordinatorLayout, t, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, true);
        }

        private void addActionToExpand(CoordinatorLayout coordinatorLayout, T t, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, boolean bl) {
            ViewCompat.replaceAccessibilityAction((View)coordinatorLayout, accessibilityActionCompat, null, new AccessibilityViewCommand((AppBarLayout)t, bl){
                final /* synthetic */ AppBarLayout val$appBarLayout;
                final /* synthetic */ boolean val$expand;
                {
                    this.val$appBarLayout = appBarLayout;
                    this.val$expand = bl;
                }

                @Override
                public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                    this.val$appBarLayout.setExpanded(this.val$expand);
                    return true;
                }
            });
        }

        private void animateOffsetTo(CoordinatorLayout coordinatorLayout, T t, int n, float f) {
            int n2 = Math.abs(this.getTopBottomOffsetForScrollingSibling() - n);
            n2 = (f = Math.abs(f)) > 0.0f ? Math.round((float)n2 / f * 1000.0f) * 3 : (int)(((float)n2 / (float)t.getHeight() + 1.0f) * 150.0f);
            this.animateOffsetWithDuration(coordinatorLayout, t, n, n2);
        }

        private void animateOffsetWithDuration(final CoordinatorLayout coordinatorLayout, T t, int n, int n2) {
            int n3 = this.getTopBottomOffsetForScrollingSibling();
            if (n3 == n) {
                coordinatorLayout = this.offsetAnimator;
                if (coordinatorLayout == null) return;
                if (!coordinatorLayout.isRunning()) return;
                this.offsetAnimator.cancel();
                return;
            }
            ValueAnimator valueAnimator = this.offsetAnimator;
            if (valueAnimator == null) {
                this.offsetAnimator = valueAnimator = new ValueAnimator();
                valueAnimator.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener((AppBarLayout)t){
                    final /* synthetic */ AppBarLayout val$child;
                    {
                        this.val$child = appBarLayout;
                    }

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        BaseBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, this.val$child, (Integer)valueAnimator.getAnimatedValue());
                    }
                });
            } else {
                valueAnimator.cancel();
            }
            this.offsetAnimator.setDuration((long)Math.min(n2, 600));
            this.offsetAnimator.setIntValues(new int[]{n3, n});
            this.offsetAnimator.start();
        }

        private boolean canScrollChildren(CoordinatorLayout coordinatorLayout, T t, View view) {
            if (!((AppBarLayout)t).hasScrollableChildren()) return false;
            if (coordinatorLayout.getHeight() - view.getHeight() > t.getHeight()) return false;
            return true;
        }

        private static boolean checkFlag(int n, int n2) {
            if ((n & n2) != n2) return false;
            return true;
        }

        private View findFirstScrollingChild(CoordinatorLayout coordinatorLayout) {
            int n = coordinatorLayout.getChildCount();
            int n2 = 0;
            while (n2 < n) {
                View view = coordinatorLayout.getChildAt(n2);
                if (view instanceof NestedScrollingChild) return view;
                if (view instanceof ListView) return view;
                if (view instanceof ScrollView) {
                    return view;
                }
                ++n2;
            }
            return null;
        }

        private static View getAppBarChildOnOffset(AppBarLayout appBarLayout, int n) {
            int n2 = Math.abs(n);
            int n3 = appBarLayout.getChildCount();
            n = 0;
            while (n < n3) {
                View view = appBarLayout.getChildAt(n);
                if (n2 >= view.getTop() && n2 <= view.getBottom()) {
                    return view;
                }
                ++n;
            }
            return null;
        }

        private int getChildIndexOnOffset(T t, int n) {
            int n2 = t.getChildCount();
            int n3 = 0;
            while (n3 < n2) {
                Object object = t.getChildAt(n3);
                int n4 = object.getTop();
                int n5 = object.getBottom();
                object = (LayoutParams)object.getLayoutParams();
                int n6 = n4;
                int n7 = n5;
                if (BaseBehavior.checkFlag(((LayoutParams)((Object)object)).getScrollFlags(), 32)) {
                    n6 = n4 - ((LayoutParams)object).topMargin;
                    n7 = n5 + ((LayoutParams)object).bottomMargin;
                }
                if (n6 <= (n5 = -n) && n7 >= n5) {
                    return n3;
                }
                ++n3;
            }
            return -1;
        }

        private int interpolateOffset(T t, int n) {
            int n2 = Math.abs(n);
            int n3 = t.getChildCount();
            int n4 = 0;
            int n5 = 0;
            while (n5 < n3) {
                View view = t.getChildAt(n5);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                Interpolator interpolator2 = layoutParams.getScrollInterpolator();
                if (n2 >= view.getTop() && n2 <= view.getBottom()) {
                    if (interpolator2 == null) return n;
                    n3 = layoutParams.getScrollFlags();
                    n5 = n4;
                    if ((n3 & 1) != 0) {
                        n5 = n4 = 0 + (view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                        if ((n3 & 2) != 0) {
                            n5 = n4 - ViewCompat.getMinimumHeight(view);
                        }
                    }
                    n4 = n5;
                    if (ViewCompat.getFitsSystemWindows(view)) {
                        n4 = n5 - ((AppBarLayout)t).getTopInset();
                    }
                    if (n4 <= 0) return n;
                    n5 = view.getTop();
                    float f = n4;
                    n5 = Math.round(f * interpolator2.getInterpolation((float)(n2 - n5) / f));
                    return Integer.signum(n) * (view.getTop() + n5);
                }
                ++n5;
            }
            return n;
        }

        private boolean shouldJumpElevationState(CoordinatorLayout object, T object2) {
            object2 = ((CoordinatorLayout)object).getDependents((View)object2);
            int n = object2.size();
            boolean bl = false;
            int n2 = 0;
            while (n2 < n) {
                object = ((CoordinatorLayout.LayoutParams)((View)object2.get(n2)).getLayoutParams()).getBehavior();
                if (object instanceof ScrollingViewBehavior) {
                    if (((ScrollingViewBehavior)object).getOverlayTop() == 0) return bl;
                    return true;
                }
                ++n2;
            }
            return false;
        }

        private void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, T t) {
            int n;
            int n2 = this.getTopBottomOffsetForScrollingSibling();
            int n3 = this.getChildIndexOnOffset(t, n2);
            if (n3 < 0) return;
            View view = t.getChildAt(n3);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n4 = layoutParams.getScrollFlags();
            if ((n4 & 17) != 17) return;
            int n5 = -view.getTop();
            int n6 = n = -view.getBottom();
            if (n3 == t.getChildCount() - 1) {
                n6 = n + ((AppBarLayout)t).getTopInset();
            }
            if (BaseBehavior.checkFlag(n4, 2)) {
                n = n6 + ViewCompat.getMinimumHeight(view);
                n3 = n5;
            } else {
                n3 = n5;
                n = n6;
                if (BaseBehavior.checkFlag(n4, 5)) {
                    n = ViewCompat.getMinimumHeight(view) + n6;
                    if (n2 < n) {
                        n3 = n;
                        n = n6;
                    } else {
                        n3 = n5;
                    }
                }
            }
            n5 = n3;
            n6 = n;
            if (BaseBehavior.checkFlag(n4, 32)) {
                n5 = n3 + layoutParams.topMargin;
                n6 = n - layoutParams.bottomMargin;
            }
            n = n5;
            if (n2 < (n6 + n5) / 2) {
                n = n6;
            }
            this.animateOffsetTo(coordinatorLayout, t, MathUtils.clamp(n, -((AppBarLayout)t).getTotalScrollRange(), 0), 0.0f);
        }

        private void updateAccessibilityActions(CoordinatorLayout coordinatorLayout, T t) {
            ViewCompat.removeAccessibilityAction((View)coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            ViewCompat.removeAccessibilityAction((View)coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            View view = this.findFirstScrollingChild(coordinatorLayout);
            if (view == null) return;
            if (((AppBarLayout)t).getTotalScrollRange() == 0) {
                return;
            }
            if (!(((CoordinatorLayout.LayoutParams)view.getLayoutParams()).getBehavior() instanceof ScrollingViewBehavior)) {
                return;
            }
            this.addAccessibilityScrollActions(coordinatorLayout, t, view);
        }

        private void updateAppBarLayoutDrawableState(CoordinatorLayout coordinatorLayout, T t, int n, int n2, boolean bl) {
            boolean bl2;
            block5 : {
                block7 : {
                    int n3;
                    boolean bl3;
                    View view;
                    int n4;
                    block6 : {
                        view = BaseBehavior.getAppBarChildOnOffset(t, n);
                        if (view == null) return;
                        n4 = ((LayoutParams)view.getLayoutParams()).getScrollFlags();
                        bl2 = bl3 = false;
                        if ((n4 & 1) == 0) break block5;
                        n3 = ViewCompat.getMinimumHeight(view);
                        if (n2 <= 0 || (n4 & 12) == 0) break block6;
                        bl2 = bl3;
                        if (-n < view.getBottom() - n3 - ((AppBarLayout)t).getTopInset()) break block5;
                        break block7;
                    }
                    bl2 = bl3;
                    if ((n4 & 2) == 0) break block5;
                    bl2 = bl3;
                    if (-n < view.getBottom() - n3 - ((AppBarLayout)t).getTopInset()) break block5;
                }
                bl2 = true;
            }
            if (((AppBarLayout)t).isLiftOnScroll()) {
                bl2 = ((AppBarLayout)t).shouldLift(this.findFirstScrollingChild(coordinatorLayout));
            }
            bl2 = ((AppBarLayout)t).setLiftedState(bl2);
            if (!bl) {
                if (!bl2) return;
                if (!this.shouldJumpElevationState(coordinatorLayout, t)) return;
            }
            t.jumpDrawablesToCurrentState();
        }

        @Override
        boolean canDragView(T object) {
            boolean bl;
            BaseDragCallback baseDragCallback = this.onDragCallback;
            if (baseDragCallback != null) {
                return baseDragCallback.canDrag(object);
            }
            object = this.lastNestedScrollingChildRef;
            boolean bl2 = bl = true;
            if (object == null) return bl2;
            if ((object = (View)((Reference)object).get()) == null) return false;
            if (!object.isShown()) return false;
            if (object.canScrollVertically(-1)) return false;
            return bl;
        }

        @Override
        int getMaxDragOffset(T t) {
            return -((AppBarLayout)t).getDownNestedScrollRange();
        }

        @Override
        int getScrollRangeForDragFling(T t) {
            return ((AppBarLayout)t).getTotalScrollRange();
        }

        @Override
        int getTopBottomOffsetForScrollingSibling() {
            return this.getTopAndBottomOffset() + this.offsetDelta;
        }

        boolean isOffsetAnimatorRunning() {
            ValueAnimator valueAnimator = this.offsetAnimator;
            if (valueAnimator == null) return false;
            if (!valueAnimator.isRunning()) return false;
            return true;
        }

        @Override
        void onFlingFinished(CoordinatorLayout coordinatorLayout, T t) {
            this.snapToChildIfNeeded(coordinatorLayout, t);
            if (!((AppBarLayout)t).isLiftOnScroll()) return;
            ((AppBarLayout)t).setLiftedState(((AppBarLayout)t).shouldLift(this.findFirstScrollingChild(coordinatorLayout)));
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, T t, int n) {
            boolean bl = super.onLayoutChild(coordinatorLayout, t, n);
            int n2 = ((AppBarLayout)t).getPendingAction();
            n = this.offsetToChildIndexOnLayout;
            if (n >= 0 && (n2 & 8) == 0) {
                View view = t.getChildAt(n);
                n2 = -view.getBottom();
                n = this.offsetToChildIndexOnLayoutIsMinHeight ? ViewCompat.getMinimumHeight(view) + ((AppBarLayout)t).getTopInset() : Math.round((float)view.getHeight() * this.offsetToChildIndexOnLayoutPerc);
                this.setHeaderTopBottomOffset(coordinatorLayout, t, n2 + n);
            } else if (n2 != 0) {
                n = (n2 & 4) != 0 ? 1 : 0;
                if ((n2 & 2) != 0) {
                    n2 = -((AppBarLayout)t).getUpNestedPreScrollRange();
                    if (n != 0) {
                        this.animateOffsetTo(coordinatorLayout, t, n2, 0.0f);
                    } else {
                        this.setHeaderTopBottomOffset(coordinatorLayout, t, n2);
                    }
                } else if ((n2 & 1) != 0) {
                    if (n != 0) {
                        this.animateOffsetTo(coordinatorLayout, t, 0, 0.0f);
                    } else {
                        this.setHeaderTopBottomOffset(coordinatorLayout, t, 0);
                    }
                }
            }
            ((AppBarLayout)t).resetPendingAction();
            this.offsetToChildIndexOnLayout = -1;
            this.setTopAndBottomOffset(MathUtils.clamp(this.getTopAndBottomOffset(), -((AppBarLayout)t).getTotalScrollRange(), 0));
            this.updateAppBarLayoutDrawableState(coordinatorLayout, t, this.getTopAndBottomOffset(), 0, true);
            ((AppBarLayout)t).onOffsetChanged(this.getTopAndBottomOffset());
            this.updateAccessibilityActions(coordinatorLayout, t);
            return bl;
        }

        @Override
        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, T t, int n, int n2, int n3, int n4) {
            if (((CoordinatorLayout.LayoutParams)t.getLayoutParams()).height != -2) return super.onMeasureChild(coordinatorLayout, t, n, n2, n3, n4);
            coordinatorLayout.onMeasureChild((View)t, n, n2, View.MeasureSpec.makeMeasureSpec((int)0, (int)0), n4);
            return true;
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, T t, View view, int n, int n2, int[] arrn, int n3) {
            if (n2 != 0) {
                if (n2 < 0) {
                    n3 = -((AppBarLayout)t).getTotalScrollRange();
                    int n4 = ((AppBarLayout)t).getDownNestedPreScrollRange();
                    n = n3;
                    n3 = n4 + n3;
                } else {
                    n = -((AppBarLayout)t).getUpNestedPreScrollRange();
                    n3 = 0;
                }
                if (n != n3) {
                    arrn[1] = this.scroll(coordinatorLayout, t, n2, n, n3);
                }
            }
            if (!((AppBarLayout)t).isLiftOnScroll()) return;
            ((AppBarLayout)t).setLiftedState(((AppBarLayout)t).shouldLift(view));
        }

        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, T t, View view, int n, int n2, int n3, int n4, int n5, int[] arrn) {
            if (n4 < 0) {
                arrn[1] = this.scroll(coordinatorLayout, t, n4, -((AppBarLayout)t).getDownNestedScrollRange(), 0);
            }
            if (n4 != 0) return;
            this.updateAccessibilityActions(coordinatorLayout, t);
        }

        @Override
        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, T t, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                parcelable = (SavedState)parcelable;
                super.onRestoreInstanceState(coordinatorLayout, t, parcelable.getSuperState());
                this.offsetToChildIndexOnLayout = parcelable.firstVisibleChildIndex;
                this.offsetToChildIndexOnLayoutPerc = parcelable.firstVisibleChildPercentageShown;
                this.offsetToChildIndexOnLayoutIsMinHeight = parcelable.firstVisibleChildAtMinimumHeight;
                return;
            }
            super.onRestoreInstanceState(coordinatorLayout, t, parcelable);
            this.offsetToChildIndexOnLayout = -1;
        }

        @Override
        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, T t) {
            Parcelable parcelable = super.onSaveInstanceState(coordinatorLayout, t);
            int n = this.getTopAndBottomOffset();
            int n2 = t.getChildCount();
            boolean bl = false;
            int n3 = 0;
            while (n3 < n2) {
                coordinatorLayout = t.getChildAt(n3);
                int n4 = coordinatorLayout.getBottom() + n;
                if (coordinatorLayout.getTop() + n <= 0 && n4 >= 0) {
                    parcelable = new SavedState(parcelable);
                    parcelable.firstVisibleChildIndex = n3;
                    if (n4 == ViewCompat.getMinimumHeight((View)coordinatorLayout) + ((AppBarLayout)t).getTopInset()) {
                        bl = true;
                    }
                    parcelable.firstVisibleChildAtMinimumHeight = bl;
                    parcelable.firstVisibleChildPercentageShown = (float)n4 / (float)coordinatorLayout.getHeight();
                    return parcelable;
                }
                ++n3;
            }
            return parcelable;
        }

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, T t, View view, View view2, int n, int n2) {
            boolean bl = (n & 2) != 0 && (((AppBarLayout)t).isLiftOnScroll() || this.canScrollChildren(coordinatorLayout, t, view));
            if (bl && (coordinatorLayout = this.offsetAnimator) != null) {
                coordinatorLayout.cancel();
            }
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = n2;
            return bl;
        }

        @Override
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, T t, View view, int n) {
            if (this.lastStartedType == 0 || n == 1) {
                this.snapToChildIfNeeded(coordinatorLayout, t);
                if (((AppBarLayout)t).isLiftOnScroll()) {
                    ((AppBarLayout)t).setLiftedState(((AppBarLayout)t).shouldLift(view));
                }
            }
            this.lastNestedScrollingChildRef = new WeakReference<View>(view);
        }

        public void setDragCallback(BaseDragCallback baseDragCallback) {
            this.onDragCallback = baseDragCallback;
        }

        @Override
        int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, T t, int n, int n2, int n3) {
            int n4 = this.getTopBottomOffsetForScrollingSibling();
            int n5 = 0;
            if (n2 != 0 && n4 >= n2 && n4 <= n3) {
                n2 = MathUtils.clamp(n, n2, n3);
                n = n5;
                if (n4 != n2) {
                    n = ((AppBarLayout)t).hasChildWithInterpolator() ? this.interpolateOffset(t, n2) : n2;
                    boolean bl = this.setTopAndBottomOffset(n);
                    n3 = n4 - n2;
                    this.offsetDelta = n2 - n;
                    if (!bl && ((AppBarLayout)t).hasChildWithInterpolator()) {
                        coordinatorLayout.dispatchDependentViewsChanged((View)t);
                    }
                    ((AppBarLayout)t).onOffsetChanged(this.getTopAndBottomOffset());
                    n = n2 < n4 ? -1 : 1;
                    this.updateAppBarLayoutDrawableState(coordinatorLayout, t, n2, n, false);
                    n = n3;
                }
            } else {
                this.offsetDelta = 0;
                n = n5;
            }
            this.updateAccessibilityActions(coordinatorLayout, t);
            return n;
        }

        public static abstract class BaseDragCallback<T extends AppBarLayout> {
            public abstract boolean canDrag(T var1);
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
            boolean firstVisibleChildAtMinimumHeight;
            int firstVisibleChildIndex;
            float firstVisibleChildPercentageShown;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.firstVisibleChildIndex = parcel.readInt();
                this.firstVisibleChildPercentageShown = parcel.readFloat();
                boolean bl = parcel.readByte() != 0;
                this.firstVisibleChildAtMinimumHeight = bl;
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }

            @Override
            public void writeToParcel(Parcel parcel, int n) {
                super.writeToParcel(parcel, n);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibleChildPercentageShown);
                parcel.writeByte((byte)(this.firstVisibleChildAtMinimumHeight ? 1 : 0));
            }

        }

    }

    public static interface BaseOnOffsetChangedListener<T extends AppBarLayout> {
        public void onOffsetChanged(T var1, int var2);
    }

    public static class Behavior
    extends BaseBehavior<AppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public static abstract class DragCallback
        extends BaseBehavior.BaseDragCallback<AppBarLayout> {
        }

    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        static final int COLLAPSIBLE_FLAGS = 10;
        static final int FLAG_QUICK_RETURN = 5;
        static final int FLAG_SNAP = 17;
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_NO_SCROLL = 0;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
        int scrollFlags = 1;
        Interpolator scrollInterpolator;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(int n, int n2, float f) {
            super(n, n2, f);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
            this.scrollFlags = attributeSet.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (attributeSet.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            attributeSet.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams)layoutParams);
            this.scrollFlags = layoutParams.scrollFlags;
            this.scrollInterpolator = layoutParams.scrollInterpolator;
        }

        public int getScrollFlags() {
            return this.scrollFlags;
        }

        public Interpolator getScrollInterpolator() {
            return this.scrollInterpolator;
        }

        boolean isCollapsible() {
            int n = this.scrollFlags;
            boolean bl = true;
            if ((n & 1) != 1) return false;
            if ((n & 10) == 0) return false;
            return bl;
        }

        public void setScrollFlags(int n) {
            this.scrollFlags = n;
        }

        public void setScrollInterpolator(Interpolator interpolator2) {
            this.scrollInterpolator = interpolator2;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface ScrollFlags {
        }

    }

    public static interface OnOffsetChangedListener
    extends BaseOnOffsetChangedListener<AppBarLayout> {
        @Override
        public void onOffsetChanged(AppBarLayout var1, int var2);
    }

    public static class ScrollingViewBehavior
    extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
            this.setOverlayTop(context.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            context.recycle();
        }

        private static int getAppBarLayoutOffset(AppBarLayout object) {
            if (!((object = ((CoordinatorLayout.LayoutParams)object.getLayoutParams()).getBehavior()) instanceof BaseBehavior)) return 0;
            return ((BaseBehavior)object).getTopBottomOffsetForScrollingSibling();
        }

        private void offsetChildAsNeeded(View view, View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams)view2.getLayoutParams()).getBehavior();
            if (!(behavior instanceof BaseBehavior)) return;
            behavior = (BaseBehavior)behavior;
            ViewCompat.offsetTopAndBottom(view, view2.getBottom() - view.getTop() + ((BaseBehavior)behavior).offsetDelta + this.getVerticalLayoutGap() - this.getOverlapPixelsForOffset(view2));
        }

        private void updateLiftedStateIfNeeded(View view, View object) {
            if (!(object instanceof AppBarLayout)) return;
            if (!((AppBarLayout)(object = (AppBarLayout)object)).isLiftOnScroll()) return;
            ((AppBarLayout)object).setLiftedState(((AppBarLayout)object).shouldLift(view));
        }

        AppBarLayout findFirstDependency(List<View> list) {
            int n = list.size();
            int n2 = 0;
            while (n2 < n) {
                View view = list.get(n2);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout)view;
                }
                ++n2;
            }
            return null;
        }

        @Override
        float getOverlapRatioForOffset(View object) {
            if (!(object instanceof AppBarLayout)) return 0.0f;
            object = (AppBarLayout)object;
            int n = ((AppBarLayout)object).getTotalScrollRange();
            int n2 = ((AppBarLayout)object).getDownNestedPreScrollRange();
            int n3 = ScrollingViewBehavior.getAppBarLayoutOffset((AppBarLayout)object);
            if (n2 != 0 && n + n3 <= n2) {
                return 0.0f;
            }
            if ((n2 = n - n2) == 0) return 0.0f;
            return (float)n3 / (float)n2 + 1.0f;
        }

        @Override
        int getScrollRange(View view) {
            if (!(view instanceof AppBarLayout)) return super.getScrollRange(view);
            return ((AppBarLayout)view).getTotalScrollRange();
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            this.offsetChildAsNeeded(view, view2);
            this.updateLiftedStateIfNeeded(view, view2);
            return false;
        }

        @Override
        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, View view, View view2) {
            if (!(view2 instanceof AppBarLayout)) return;
            ViewCompat.removeAccessibilityAction((View)coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            ViewCompat.removeAccessibilityAction((View)coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
        }

        @Override
        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean bl) {
            AppBarLayout appBarLayout = this.findFirstDependency(coordinatorLayout.getDependencies(view));
            if (appBarLayout == null) return false;
            rect.offset(view.getLeft(), view.getTop());
            view = this.tempRect1;
            view.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
            if (view.contains(rect)) return false;
            appBarLayout.setExpanded(false, bl ^ true);
            return true;
        }
    }

}

