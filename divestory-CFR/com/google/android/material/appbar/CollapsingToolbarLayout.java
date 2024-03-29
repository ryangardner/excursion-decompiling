/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.appcompat.R;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.ViewOffsetHelper;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class CollapsingToolbarLayout
extends FrameLayout {
    private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_CollapsingToolbar;
    final CollapsingTextHelper collapsingTextHelper;
    private boolean collapsingTitleEnabled;
    private Drawable contentScrim;
    int currentOffset;
    private boolean drawCollapsingTitle;
    private View dummyView;
    private int expandedMarginBottom;
    private int expandedMarginEnd;
    private int expandedMarginStart;
    private int expandedMarginTop;
    WindowInsetsCompat lastInsets;
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
    private boolean refreshToolbar = true;
    private int scrimAlpha;
    private long scrimAnimationDuration;
    private ValueAnimator scrimAnimator;
    private int scrimVisibleHeightTrigger = -1;
    private boolean scrimsAreShown;
    Drawable statusBarScrim;
    private final Rect tmpRect = new Rect();
    private Toolbar toolbar;
    private View toolbarDirectChild;
    private int toolbarId;

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        CollapsingTextHelper collapsingTextHelper;
        context = this.getContext();
        this.collapsingTextHelper = collapsingTextHelper = new CollapsingTextHelper((View)this);
        collapsingTextHelper.setTextSizeInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
        context = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.CollapsingToolbarLayout, n, DEF_STYLE_RES, new int[0]);
        this.collapsingTextHelper.setExpandedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
        this.collapsingTextHelper.setCollapsedTextGravity(context.getInt(R.styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
        this.expandedMarginBottom = n = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.expandedMarginEnd = n;
        this.expandedMarginTop = n;
        this.expandedMarginStart = n;
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.expandedMarginStart = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.expandedMarginEnd = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.expandedMarginTop = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.expandedMarginBottom = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        this.collapsingTitleEnabled = context.getBoolean(R.styleable.CollapsingToolbarLayout_titleEnabled, true);
        this.setTitle(context.getText(R.styleable.CollapsingToolbarLayout_title));
        this.collapsingTextHelper.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.collapsingTextHelper.setCollapsedTextAppearance(R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.collapsingTextHelper.setExpandedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.collapsingTextHelper.setCollapsedTextAppearance(context.getResourceId(R.styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        this.scrimVisibleHeightTrigger = context.getDimensionPixelSize(R.styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        if (context.hasValue(R.styleable.CollapsingToolbarLayout_maxLines)) {
            this.collapsingTextHelper.setMaxLines(context.getInt(R.styleable.CollapsingToolbarLayout_maxLines, 1));
        }
        this.scrimAnimationDuration = context.getInt(R.styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
        this.setContentScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_contentScrim));
        this.setStatusBarScrim(context.getDrawable(R.styleable.CollapsingToolbarLayout_statusBarScrim));
        this.toolbarId = context.getResourceId(R.styleable.CollapsingToolbarLayout_toolbarId, -1);
        context.recycle();
        this.setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener((View)this, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return CollapsingToolbarLayout.this.onWindowInsetChanged(windowInsetsCompat);
            }
        });
    }

    private void animateScrim(int n) {
        this.ensureToolbar();
        ValueAnimator valueAnimator = this.scrimAnimator;
        if (valueAnimator == null) {
            this.scrimAnimator = valueAnimator = new ValueAnimator();
            valueAnimator.setDuration(this.scrimAnimationDuration);
            ValueAnimator valueAnimator2 = this.scrimAnimator;
            valueAnimator = n > this.scrimAlpha ? AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR : AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
            valueAnimator2.setInterpolator((TimeInterpolator)valueAnimator);
            this.scrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CollapsingToolbarLayout.this.setScrimAlpha((Integer)valueAnimator.getAnimatedValue());
                }
            });
        } else if (valueAnimator.isRunning()) {
            this.scrimAnimator.cancel();
        }
        this.scrimAnimator.setIntValues(new int[]{this.scrimAlpha, n});
        this.scrimAnimator.start();
    }

    private void ensureToolbar() {
        Toolbar toolbar;
        if (!this.refreshToolbar) {
            return;
        }
        Object var1_1 = null;
        this.toolbar = null;
        this.toolbarDirectChild = null;
        int n = this.toolbarId;
        if (n != -1) {
            this.toolbar = toolbar = (Toolbar)this.findViewById(n);
            if (toolbar != null) {
                this.toolbarDirectChild = this.findDirectChild((View)toolbar);
            }
        }
        if (this.toolbar == null) {
            int n2 = this.getChildCount();
            n = 0;
            do {
                toolbar = var1_1;
                if (n >= n2 || (toolbar = this.getChildAt(n)) instanceof Toolbar) break;
                ++n;
            } while (true);
            this.toolbar = toolbar;
        }
        this.updateDummyView();
        this.refreshToolbar = false;
    }

    private View findDirectChild(View view) {
        ViewParent viewParent = view.getParent();
        View view2 = view;
        view = viewParent;
        while (view != this) {
            if (view == null) return view2;
            if (view instanceof View) {
                view2 = view;
            }
            view = view.getParent();
        }
        return view2;
    }

    private static int getHeightWithMargins(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) return view.getHeight();
        layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
        return view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper viewOffsetHelper;
        ViewOffsetHelper viewOffsetHelper2 = viewOffsetHelper = (ViewOffsetHelper)view.getTag(R.id.view_offset_helper);
        if (viewOffsetHelper != null) return viewOffsetHelper2;
        viewOffsetHelper2 = new ViewOffsetHelper(view);
        view.setTag(R.id.view_offset_helper, (Object)viewOffsetHelper2);
        return viewOffsetHelper2;
    }

    private boolean isToolbarChild(View view) {
        View view2 = this.toolbarDirectChild;
        boolean bl = true;
        if (view2 != null && view2 != this) {
            if (view != view2) return false;
            return bl;
        }
        if (view != this.toolbar) return false;
        return bl;
    }

    private void updateContentDescriptionFromTitle() {
        this.setContentDescription(this.getTitle());
    }

    private void updateDummyView() {
        View view;
        if (!this.collapsingTitleEnabled && (view = this.dummyView) != null && (view = view.getParent()) instanceof ViewGroup) {
            ((ViewGroup)view).removeView(this.dummyView);
        }
        if (!this.collapsingTitleEnabled) return;
        if (this.toolbar == null) return;
        if (this.dummyView == null) {
            this.dummyView = new View(this.getContext());
        }
        if (this.dummyView.getParent() != null) return;
        this.toolbar.addView(this.dummyView, -1, -1);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void draw(Canvas canvas) {
        Object object;
        super.draw(canvas);
        this.ensureToolbar();
        if (this.toolbar == null && (object = this.contentScrim) != null && this.scrimAlpha > 0) {
            object.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
        }
        if (this.collapsingTitleEnabled && this.drawCollapsingTitle) {
            this.collapsingTextHelper.draw(canvas);
        }
        if (this.statusBarScrim == null) return;
        if (this.scrimAlpha <= 0) return;
        object = this.lastInsets;
        int n = object != null ? ((WindowInsetsCompat)object).getSystemWindowInsetTop() : 0;
        if (n <= 0) return;
        this.statusBarScrim.setBounds(0, -this.currentOffset, this.getWidth(), n - this.currentOffset);
        this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
        this.statusBarScrim.draw(canvas);
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        boolean bl;
        Drawable drawable2 = this.contentScrim;
        boolean bl2 = true;
        if (drawable2 != null && this.scrimAlpha > 0 && this.isToolbarChild(view)) {
            this.contentScrim.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
            bl = true;
        } else {
            bl = false;
        }
        boolean bl3 = bl2;
        if (super.drawChild(canvas, view, l)) return bl3;
        if (!bl) return false;
        return bl2;
    }

    protected void drawableStateChanged() {
        boolean bl;
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        Object object = this.statusBarScrim;
        boolean bl2 = bl = false;
        if (object != null) {
            bl2 = bl;
            if (object.isStateful()) {
                bl2 = false | object.setState(arrn);
            }
        }
        object = this.contentScrim;
        bl = bl2;
        if (object != null) {
            bl = bl2;
            if (object.isStateful()) {
                bl = bl2 | object.setState(arrn);
            }
        }
        object = this.collapsingTextHelper;
        bl2 = bl;
        if (object != null) {
            bl2 = bl | ((CollapsingTextHelper)object).setState(arrn);
        }
        if (!bl2) return;
        this.invalidate();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getCollapsedTitleGravity() {
        return this.collapsingTextHelper.getCollapsedTextGravity();
    }

    public Typeface getCollapsedTitleTypeface() {
        return this.collapsingTextHelper.getCollapsedTypeface();
    }

    public Drawable getContentScrim() {
        return this.contentScrim;
    }

    public int getExpandedTitleGravity() {
        return this.collapsingTextHelper.getExpandedTextGravity();
    }

    public int getExpandedTitleMarginBottom() {
        return this.expandedMarginBottom;
    }

    public int getExpandedTitleMarginEnd() {
        return this.expandedMarginEnd;
    }

    public int getExpandedTitleMarginStart() {
        return this.expandedMarginStart;
    }

    public int getExpandedTitleMarginTop() {
        return this.expandedMarginTop;
    }

    public Typeface getExpandedTitleTypeface() {
        return this.collapsingTextHelper.getExpandedTypeface();
    }

    public int getMaxLines() {
        return this.collapsingTextHelper.getMaxLines();
    }

    final int getMaxOffsetForPinChild(View view) {
        ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(view);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        return this.getHeight() - viewOffsetHelper.getLayoutTop() - view.getHeight() - layoutParams.bottomMargin;
    }

    int getScrimAlpha() {
        return this.scrimAlpha;
    }

    public long getScrimAnimationDuration() {
        return this.scrimAnimationDuration;
    }

    public int getScrimVisibleHeightTrigger() {
        int n = this.scrimVisibleHeightTrigger;
        if (n >= 0) {
            return n;
        }
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        n = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        int n2 = ViewCompat.getMinimumHeight((View)this);
        if (n2 <= 0) return this.getHeight() / 3;
        return Math.min(n2 * 2 + n, this.getHeight());
    }

    public Drawable getStatusBarScrim() {
        return this.statusBarScrim;
    }

    public CharSequence getTitle() {
        if (!this.collapsingTitleEnabled) return null;
        return this.collapsingTextHelper.getText();
    }

    public boolean isTitleEnabled() {
        return this.collapsingTitleEnabled;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent viewParent = this.getParent();
        if (!(viewParent instanceof AppBarLayout)) return;
        ViewCompat.setFitsSystemWindows((View)this, ViewCompat.getFitsSystemWindows((View)viewParent));
        if (this.onOffsetChangedListener == null) {
            this.onOffsetChangedListener = new OffsetUpdateListener();
        }
        ((AppBarLayout)viewParent).addOnOffsetChangedListener(this.onOffsetChangedListener);
        ViewCompat.requestApplyInsets((View)this);
    }

    protected void onDetachedFromWindow() {
        ViewParent viewParent = this.getParent();
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = this.onOffsetChangedListener;
        if (onOffsetChangedListener != null && viewParent instanceof AppBarLayout) {
            ((AppBarLayout)viewParent).removeOnOffsetChangedListener(onOffsetChangedListener);
        }
        super.onDetachedFromWindow();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        super.onLayout(bl, n, n2, n3, n4);
        Object object = this.lastInsets;
        int n8 = 0;
        if (object != null) {
            n7 = ((WindowInsetsCompat)object).getSystemWindowInsetTop();
            n6 = this.getChildCount();
            for (n5 = 0; n5 < n6; ++n5) {
                object = this.getChildAt(n5);
                if (ViewCompat.getFitsSystemWindows((View)object) || object.getTop() >= n7) continue;
                ViewCompat.offsetTopAndBottom((View)object, n7);
            }
        }
        n6 = this.getChildCount();
        for (n5 = 0; n5 < n6; ++n5) {
            CollapsingToolbarLayout.getViewOffsetHelper(this.getChildAt(n5)).onViewLayout();
        }
        if (this.collapsingTitleEnabled && (object = this.dummyView) != null) {
            bl = ViewCompat.isAttachedToWindow((View)object);
            n5 = 1;
            bl = bl && this.dummyView.getVisibility() == 0;
            this.drawCollapsingTitle = bl;
            if (bl) {
                if (ViewCompat.getLayoutDirection((View)this) != 1) {
                    n5 = 0;
                }
                object = this.toolbarDirectChild;
                if (object == null) {
                    object = this.toolbar;
                }
                int n9 = this.getMaxOffsetForPinChild((View)object);
                DescendantOffsetUtils.getDescendantRect((ViewGroup)this, this.dummyView, this.tmpRect);
                object = this.collapsingTextHelper;
                int n10 = this.tmpRect.left;
                n6 = n5 != 0 ? this.toolbar.getTitleMarginEnd() : this.toolbar.getTitleMarginStart();
                int n11 = this.tmpRect.top;
                int n12 = this.toolbar.getTitleMarginTop();
                int n13 = this.tmpRect.right;
                n7 = n5 != 0 ? this.toolbar.getTitleMarginStart() : this.toolbar.getTitleMarginEnd();
                ((CollapsingTextHelper)object).setCollapsedBounds(n10 + n6, n11 + n9 + n12, n13 - n7, this.tmpRect.bottom + n9 - this.toolbar.getTitleMarginBottom());
                object = this.collapsingTextHelper;
                n6 = n5 != 0 ? this.expandedMarginEnd : this.expandedMarginStart;
                n7 = this.tmpRect.top;
                n9 = this.expandedMarginTop;
                n5 = n5 != 0 ? this.expandedMarginStart : this.expandedMarginEnd;
                ((CollapsingTextHelper)object).setExpandedBounds(n6, n7 + n9, n3 - n - n5, n4 - n2 - this.expandedMarginBottom);
                this.collapsingTextHelper.recalculate();
            }
        }
        if (this.toolbar != null) {
            if (this.collapsingTitleEnabled && TextUtils.isEmpty((CharSequence)this.collapsingTextHelper.getText())) {
                this.setTitle(this.toolbar.getTitle());
            }
            if ((object = this.toolbarDirectChild) != null && object != this) {
                this.setMinimumHeight(CollapsingToolbarLayout.getHeightWithMargins((View)object));
            } else {
                this.setMinimumHeight(CollapsingToolbarLayout.getHeightWithMargins((View)this.toolbar));
            }
        }
        this.updateScrimVisibility();
        n2 = this.getChildCount();
        n = n8;
        while (n < n2) {
            CollapsingToolbarLayout.getViewOffsetHelper(this.getChildAt(n)).applyOffsets();
            ++n;
        }
    }

    protected void onMeasure(int n, int n2) {
        this.ensureToolbar();
        super.onMeasure(n, n2);
        int n3 = View.MeasureSpec.getMode((int)n2);
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        n2 = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if (n3 != 0) return;
        if (n2 <= 0) return;
        super.onMeasure(n, View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredHeight() + n2), (int)1073741824));
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        Drawable drawable2 = this.contentScrim;
        if (drawable2 == null) return;
        drawable2.setBounds(0, 0, n, n2);
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows((View)this) ? windowInsetsCompat : null;
        if (ObjectsCompat.equals(this.lastInsets, windowInsetsCompat2)) return windowInsetsCompat.consumeSystemWindowInsets();
        this.lastInsets = windowInsetsCompat2;
        this.requestLayout();
        return windowInsetsCompat.consumeSystemWindowInsets();
    }

    public void setCollapsedTitleGravity(int n) {
        this.collapsingTextHelper.setCollapsedTextGravity(n);
    }

    public void setCollapsedTitleTextAppearance(int n) {
        this.collapsingTextHelper.setCollapsedTextAppearance(n);
    }

    public void setCollapsedTitleTextColor(int n) {
        this.setCollapsedTitleTextColor(ColorStateList.valueOf((int)n));
    }

    public void setCollapsedTitleTextColor(ColorStateList colorStateList) {
        this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
    }

    public void setCollapsedTitleTypeface(Typeface typeface) {
        this.collapsingTextHelper.setCollapsedTypeface(typeface);
    }

    public void setContentScrim(Drawable drawable2) {
        Drawable drawable3 = this.contentScrim;
        if (drawable3 == drawable2) return;
        Drawable drawable4 = null;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        if (drawable2 != null) {
            drawable4 = drawable2.mutate();
        }
        this.contentScrim = drawable4;
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, this.getWidth(), this.getHeight());
            this.contentScrim.setCallback((Drawable.Callback)this);
            this.contentScrim.setAlpha(this.scrimAlpha);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setContentScrimColor(int n) {
        this.setContentScrim((Drawable)new ColorDrawable(n));
    }

    public void setContentScrimResource(int n) {
        this.setContentScrim(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setExpandedTitleColor(int n) {
        this.setExpandedTitleTextColor(ColorStateList.valueOf((int)n));
    }

    public void setExpandedTitleGravity(int n) {
        this.collapsingTextHelper.setExpandedTextGravity(n);
    }

    public void setExpandedTitleMargin(int n, int n2, int n3, int n4) {
        this.expandedMarginStart = n;
        this.expandedMarginTop = n2;
        this.expandedMarginEnd = n3;
        this.expandedMarginBottom = n4;
        this.requestLayout();
    }

    public void setExpandedTitleMarginBottom(int n) {
        this.expandedMarginBottom = n;
        this.requestLayout();
    }

    public void setExpandedTitleMarginEnd(int n) {
        this.expandedMarginEnd = n;
        this.requestLayout();
    }

    public void setExpandedTitleMarginStart(int n) {
        this.expandedMarginStart = n;
        this.requestLayout();
    }

    public void setExpandedTitleMarginTop(int n) {
        this.expandedMarginTop = n;
        this.requestLayout();
    }

    public void setExpandedTitleTextAppearance(int n) {
        this.collapsingTextHelper.setExpandedTextAppearance(n);
    }

    public void setExpandedTitleTextColor(ColorStateList colorStateList) {
        this.collapsingTextHelper.setExpandedTextColor(colorStateList);
    }

    public void setExpandedTitleTypeface(Typeface typeface) {
        this.collapsingTextHelper.setExpandedTypeface(typeface);
    }

    public void setMaxLines(int n) {
        this.collapsingTextHelper.setMaxLines(n);
    }

    void setScrimAlpha(int n) {
        Toolbar toolbar;
        if (n == this.scrimAlpha) return;
        if (this.contentScrim != null && (toolbar = this.toolbar) != null) {
            ViewCompat.postInvalidateOnAnimation((View)toolbar);
        }
        this.scrimAlpha = n;
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setScrimAnimationDuration(long l) {
        this.scrimAnimationDuration = l;
    }

    public void setScrimVisibleHeightTrigger(int n) {
        if (this.scrimVisibleHeightTrigger == n) return;
        this.scrimVisibleHeightTrigger = n;
        this.updateScrimVisibility();
    }

    public void setScrimsShown(boolean bl) {
        boolean bl2 = ViewCompat.isLaidOut((View)this) && !this.isInEditMode();
        this.setScrimsShown(bl, bl2);
    }

    public void setScrimsShown(boolean bl, boolean bl2) {
        if (this.scrimsAreShown == bl) return;
        int n = 255;
        if (bl2) {
            if (!bl) {
                n = 0;
            }
            this.animateScrim(n);
        } else {
            if (!bl) {
                n = 0;
            }
            this.setScrimAlpha(n);
        }
        this.scrimsAreShown = bl;
    }

    public void setStatusBarScrim(Drawable drawable2) {
        Drawable drawable3 = this.statusBarScrim;
        if (drawable3 == drawable2) return;
        Drawable drawable4 = null;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        if (drawable2 != null) {
            drawable4 = drawable2.mutate();
        }
        this.statusBarScrim = drawable4;
        if (drawable4 != null) {
            if (drawable4.isStateful()) {
                this.statusBarScrim.setState(this.getDrawableState());
            }
            DrawableCompat.setLayoutDirection(this.statusBarScrim, ViewCompat.getLayoutDirection((View)this));
            drawable2 = this.statusBarScrim;
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
            this.statusBarScrim.setCallback((Drawable.Callback)this);
            this.statusBarScrim.setAlpha(this.scrimAlpha);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setStatusBarScrimColor(int n) {
        this.setStatusBarScrim((Drawable)new ColorDrawable(n));
    }

    public void setStatusBarScrimResource(int n) {
        this.setStatusBarScrim(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setTitle(CharSequence charSequence) {
        this.collapsingTextHelper.setText(charSequence);
        this.updateContentDescriptionFromTitle();
    }

    public void setTitleEnabled(boolean bl) {
        if (bl == this.collapsingTitleEnabled) return;
        this.collapsingTitleEnabled = bl;
        this.updateContentDescriptionFromTitle();
        this.updateDummyView();
        this.requestLayout();
    }

    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = n == 0;
        Drawable drawable2 = this.statusBarScrim;
        if (drawable2 != null && drawable2.isVisible() != bl) {
            this.statusBarScrim.setVisible(bl, false);
        }
        if ((drawable2 = this.contentScrim) == null) return;
        if (drawable2.isVisible() == bl) return;
        this.contentScrim.setVisible(bl, false);
    }

    final void updateScrimVisibility() {
        if (this.contentScrim == null) {
            if (this.statusBarScrim == null) return;
        }
        boolean bl = this.getHeight() + this.currentOffset < this.getScrimVisibleHeightTrigger();
        this.setScrimsShown(bl);
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        if (super.verifyDrawable(drawable2)) return true;
        if (drawable2 == this.contentScrim) return true;
        if (drawable2 == this.statusBarScrim) return true;
        return false;
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5f;
        int collapseMode = 0;
        float parallaxMult = 0.5f;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2, n3);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout_Layout);
            this.collapseMode = context.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            this.setParallaxMultiplier(context.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(FrameLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public int getCollapseMode() {
            return this.collapseMode;
        }

        public float getParallaxMultiplier() {
            return this.parallaxMult;
        }

        public void setCollapseMode(int n) {
            this.collapseMode = n;
        }

        public void setParallaxMultiplier(float f) {
            this.parallaxMult = f;
        }
    }

    private class OffsetUpdateListener
    implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout object, int n) {
            int n2;
            CollapsingToolbarLayout.this.currentOffset = n;
            int n3 = CollapsingToolbarLayout.this.lastInsets != null ? CollapsingToolbarLayout.this.lastInsets.getSystemWindowInsetTop() : 0;
            int n4 = CollapsingToolbarLayout.this.getChildCount();
            for (n2 = 0; n2 < n4; ++n2) {
                View view = CollapsingToolbarLayout.this.getChildAt(n2);
                object = (LayoutParams)view.getLayoutParams();
                ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(view);
                int n5 = ((LayoutParams)object).collapseMode;
                if (n5 != 1) {
                    if (n5 != 2) continue;
                    viewOffsetHelper.setTopAndBottomOffset(Math.round((float)(-n) * ((LayoutParams)object).parallaxMult));
                    continue;
                }
                viewOffsetHelper.setTopAndBottomOffset(MathUtils.clamp(-n, 0, CollapsingToolbarLayout.this.getMaxOffsetForPinChild(view)));
            }
            CollapsingToolbarLayout.this.updateScrimVisibility();
            if (CollapsingToolbarLayout.this.statusBarScrim != null && n3 > 0) {
                ViewCompat.postInvalidateOnAnimation((View)CollapsingToolbarLayout.this);
            }
            n4 = CollapsingToolbarLayout.this.getHeight();
            n2 = ViewCompat.getMinimumHeight((View)CollapsingToolbarLayout.this);
            CollapsingToolbarLayout.this.collapsingTextHelper.setExpansionFraction((float)Math.abs(n) / (float)(n4 - n2 - n3));
        }
    }

}

