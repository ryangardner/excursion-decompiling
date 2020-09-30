/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.slidingpanelayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout
extends ViewGroup {
    private static final int DEFAULT_FADE_COLOR = -858993460;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = "SlidingPaneLayout";
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    private boolean mDisplayListReflectionLoaded;
    final ViewDragHelper mDragHelper;
    private boolean mFirstLayout = true;
    private Method mGetDisplayList;
    private float mInitialMotionX;
    private float mInitialMotionY;
    boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    final ArrayList<DisableLayerRunnable> mPostedRunnables = new ArrayList();
    boolean mPreservedOpenState;
    private Field mRecreateDisplayList;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    float mSlideOffset;
    int mSlideRange;
    View mSlideableView;
    private int mSliderFadeColor = -858993460;
    private final Rect mTmpRect = new Rect();

    public SlidingPaneLayout(Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(Context object, AttributeSet attributeSet, int n) {
        super((Context)object, attributeSet, n);
        float f = object.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int)(32.0f * f + 0.5f);
        this.setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
        ViewCompat.setImportantForAccessibility((View)this, 1);
        object = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.mDragHelper = object;
        ((ViewDragHelper)object).setMinVelocity(f * 400.0f);
    }

    private boolean closePane(View view, int n) {
        if (!this.mFirstLayout) {
            if (!this.smoothSlideTo(0.0f, n)) return false;
        }
        this.mPreservedOpenState = false;
        return true;
    }

    private void dimChildView(View object, float f, int n) {
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (f > 0.0f && n != 0) {
            int n2 = (int)((float)((-16777216 & n) >>> 24) * f);
            if (layoutParams.dimPaint == null) {
                layoutParams.dimPaint = new Paint();
            }
            layoutParams.dimPaint.setColorFilter((ColorFilter)new PorterDuffColorFilter(n2 << 24 | n & 16777215, PorterDuff.Mode.SRC_OVER));
            if (object.getLayerType() != 2) {
                object.setLayerType(2, layoutParams.dimPaint);
            }
            this.invalidateChildRegion((View)object);
            return;
        }
        if (object.getLayerType() == 0) return;
        if (layoutParams.dimPaint != null) {
            layoutParams.dimPaint.setColorFilter(null);
        }
        object = new DisableLayerRunnable((View)object);
        this.mPostedRunnables.add((DisableLayerRunnable)object);
        ViewCompat.postOnAnimation((View)this, (Runnable)object);
    }

    private boolean openPane(View view, int n) {
        if (!this.mFirstLayout) {
            if (!this.smoothSlideTo(1.0f, n)) return false;
        }
        this.mPreservedOpenState = true;
        return true;
    }

    private void parallaxOtherViews(float f) {
        int n;
        boolean bl = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        boolean bl2 = layoutParams.dimWhenOffset;
        int n2 = 0;
        n = bl2 && (n = bl ? layoutParams.rightMargin : layoutParams.leftMargin) <= 0 ? 1 : 0;
        int n3 = this.getChildCount();
        while (n2 < n3) {
            layoutParams = this.getChildAt(n2);
            if (layoutParams != this.mSlideableView) {
                float f2 = this.mParallaxOffset;
                int n4 = this.mParallaxBy;
                int n5 = (int)((1.0f - f2) * (float)n4);
                this.mParallaxOffset = f;
                n5 = n4 = n5 - (int)((1.0f - f) * (float)n4);
                if (bl) {
                    n5 = -n4;
                }
                layoutParams.offsetLeftAndRight(n5);
                if (n != 0) {
                    f2 = this.mParallaxOffset;
                    f2 = bl ? (f2 -= 1.0f) : 1.0f - f2;
                    this.dimChildView((View)layoutParams, f2, this.mCoveredFadeColor);
                }
            }
            ++n2;
        }
    }

    private static boolean viewIsOpaque(View view) {
        boolean bl = view.isOpaque();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            return false;
        }
        if ((view = view.getBackground()) == null) return false;
        if (view.getOpacity() != -1) return false;
        return bl2;
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3) {
        boolean bl2 = view instanceof ViewGroup;
        boolean bl3 = true;
        if (bl2) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                int n6;
                int n7 = n2 + n4;
                View view2 = viewGroup.getChildAt(i);
                if (n7 < view2.getLeft() || n7 >= view2.getRight() || (n6 = n3 + n5) < view2.getTop() || n6 >= view2.getBottom() || !this.canScroll(view2, true, n, n7 - view2.getLeft(), n6 - view2.getTop())) continue;
                return true;
            }
        }
        if (!bl) return false;
        if (!this.isLayoutRtlSupport()) {
            n = -n;
        }
        if (!view.canScrollHorizontally(n)) return false;
        return bl3;
    }

    @Deprecated
    public boolean canSlide() {
        return this.mCanSlide;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams)) return false;
        if (!super.checkLayoutParams(layoutParams)) return false;
        return true;
    }

    public boolean closePane() {
        return this.closePane(this.mSlideableView, 0);
    }

    public void computeScroll() {
        if (!this.mDragHelper.continueSettling(true)) return;
        if (!this.mCanSlide) {
            this.mDragHelper.abort();
            return;
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    void dispatchOnPanelClosed(View view) {
        PanelSlideListener panelSlideListener = this.mPanelSlideListener;
        if (panelSlideListener != null) {
            panelSlideListener.onPanelClosed(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelOpened(View view) {
        PanelSlideListener panelSlideListener = this.mPanelSlideListener;
        if (panelSlideListener != null) {
            panelSlideListener.onPanelOpened(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelSlide(View view) {
        PanelSlideListener panelSlideListener = this.mPanelSlideListener;
        if (panelSlideListener == null) return;
        panelSlideListener.onPanelSlide(view, this.mSlideOffset);
    }

    public void draw(Canvas canvas) {
        int n;
        int n2;
        super.draw(canvas);
        Drawable drawable2 = this.isLayoutRtlSupport() ? this.mShadowDrawableRight : this.mShadowDrawableLeft;
        View view = this.getChildCount() > 1 ? this.getChildAt(1) : null;
        if (view == null) return;
        if (drawable2 == null) {
            return;
        }
        int n3 = view.getTop();
        int n4 = view.getBottom();
        int n5 = drawable2.getIntrinsicWidth();
        if (this.isLayoutRtlSupport()) {
            n = view.getRight();
            n2 = n5 + n;
        } else {
            n = n2 = view.getLeft();
            n5 = n2 - n5;
            n2 = n;
            n = n5;
        }
        drawable2.setBounds(n, n3, n2, n4);
        drawable2.draw(canvas);
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n = canvas.save();
        if (this.mCanSlide && !layoutParams.slideable && this.mSlideableView != null) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.isLayoutRtlSupport()) {
                layoutParams = this.mTmpRect;
                ((Rect)layoutParams).left = Math.max(((Rect)layoutParams).left, this.mSlideableView.getRight());
            } else {
                layoutParams = this.mTmpRect;
                ((Rect)layoutParams).right = Math.min(((Rect)layoutParams).right, this.mSlideableView.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean bl = super.drawChild(canvas, view, l);
        canvas.restoreToCount(n);
        return bl;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        if (!(object instanceof ViewGroup.MarginLayoutParams)) return new LayoutParams((ViewGroup.LayoutParams)object);
        return new LayoutParams((ViewGroup.MarginLayoutParams)object);
    }

    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public int getParallaxDistance() {
        return this.mParallaxBy;
    }

    public int getSliderFadeColor() {
        return this.mSliderFadeColor;
    }

    void invalidateChildRegion(View view) {
        if (Build.VERSION.SDK_INT >= 17) {
            ViewCompat.setLayerPaint(view, ((LayoutParams)view.getLayoutParams()).dimPaint);
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            Field field;
            if (!this.mDisplayListReflectionLoaded) {
                try {
                    this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Log.e((String)TAG, (String)"Couldn't fetch getDisplayList method; dimming won't work right.", (Throwable)noSuchMethodException);
                }
                try {
                    this.mRecreateDisplayList = field = View.class.getDeclaredField("mRecreateDisplayList");
                    field.setAccessible(true);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    Log.e((String)TAG, (String)"Couldn't fetch mRecreateDisplayList field; dimming will be slow.", (Throwable)noSuchFieldException);
                }
                this.mDisplayListReflectionLoaded = true;
            }
            if (this.mGetDisplayList != null && (field = this.mRecreateDisplayList) != null) {
                try {
                    field.setBoolean((Object)view, true);
                    this.mGetDisplayList.invoke((Object)view, null);
                }
                catch (Exception exception) {
                    Log.e((String)TAG, (String)"Error refreshing display list state", (Throwable)exception);
                }
            } else {
                view.invalidate();
                return;
            }
        }
        ViewCompat.postInvalidateOnAnimation((View)this, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    boolean isDimmed(View object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        object = (LayoutParams)object.getLayoutParams();
        boolean bl2 = bl;
        if (!this.mCanSlide) return bl2;
        bl2 = bl;
        if (!object.dimWhenOffset) return bl2;
        bl2 = bl;
        if (!(this.mSlideOffset > 0.0f)) return bl2;
        return true;
    }

    boolean isLayoutRtlSupport() {
        int n = ViewCompat.getLayoutDirection((View)this);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public boolean isOpen() {
        if (!this.mCanSlide) return true;
        if (this.mSlideOffset == 1.0f) return true;
        return false;
    }

    public boolean isSlideable() {
        return this.mCanSlide;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int n = this.mPostedRunnables.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mPostedRunnables.clear();
                return;
            }
            this.mPostedRunnables.get(n2).run();
            ++n2;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    public boolean onInterceptTouchEvent(MotionEvent var1_1) {
        block5 : {
            block6 : {
                block7 : {
                    var2_2 = var1_1.getActionMasked();
                    var3_3 = this.mCanSlide;
                    var4_4 = true;
                    if (!var3_3 && var2_2 == 0 && this.getChildCount() > 1 && (var5_5 = this.getChildAt(1)) != null) {
                        this.mPreservedOpenState = this.mDragHelper.isViewUnder(var5_5, (int)var1_1.getX(), (int)var1_1.getY()) ^ true;
                    }
                    if (!this.mCanSlide || this.mIsUnableToDrag && var2_2 != 0) break block5;
                    if (var2_2 == 3 || var2_2 == 1) break block6;
                    if (var2_2 == 0) break block7;
                    if (var2_2 == 2) {
                        var6_6 = var1_1.getX();
                        var7_8 = var1_1.getY();
                        var6_6 = Math.abs(var6_6 - this.mInitialMotionX);
                        var7_8 = Math.abs(var7_8 - this.mInitialMotionY);
                        if (var6_6 > (float)this.mDragHelper.getTouchSlop() && var7_8 > var6_6) {
                            this.mDragHelper.cancel();
                            this.mIsUnableToDrag = true;
                            return false;
                        }
                    }
                    ** GOTO lbl-1000
                }
                this.mIsUnableToDrag = false;
                var7_9 = var1_1.getX();
                var6_7 = var1_1.getY();
                this.mInitialMotionX = var7_9;
                this.mInitialMotionY = var6_7;
                if (this.mDragHelper.isViewUnder(this.mSlideableView, (int)var7_9, (int)var6_7) && this.isDimmed(this.mSlideableView)) {
                    var2_2 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var2_2 = 0;
                }
                var3_3 = var4_4;
                if (this.mDragHelper.shouldInterceptTouchEvent(var1_1) != false) return var3_3;
                if (var2_2 == 0) return false;
                return var4_4;
            }
            this.mDragHelper.cancel();
            return false;
        }
        this.mDragHelper.cancel();
        return super.onInterceptTouchEvent(var1_1);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        boolean bl2 = this.isLayoutRtlSupport();
        if (bl2) {
            this.mDragHelper.setEdgeTrackingEnabled(2);
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(1);
        }
        int n5 = n3 - n;
        n = bl2 ? this.getPaddingRight() : this.getPaddingLeft();
        n4 = bl2 ? this.getPaddingLeft() : this.getPaddingRight();
        int n6 = this.getPaddingTop();
        int n7 = this.getChildCount();
        if (this.mFirstLayout) {
            float f = this.mCanSlide && this.mPreservedOpenState ? 1.0f : 0.0f;
            this.mSlideOffset = f;
        }
        n2 = n;
        for (int i = 0; i < n7; ++i) {
            int n8;
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n9 = view.getMeasuredWidth();
            if (layoutParams.slideable) {
                n3 = layoutParams.leftMargin;
                int n10 = layoutParams.rightMargin;
                n8 = n5 - n4;
                this.mSlideRange = n10 = Math.min(n, n8 - this.mOverhangSize) - n2 - (n3 + n10);
                n3 = bl2 ? layoutParams.rightMargin : layoutParams.leftMargin;
                bl = n2 + n3 + n10 + n9 / 2 > n8;
                layoutParams.dimWhenOffset = bl;
                n8 = (int)((float)n10 * this.mSlideOffset);
                n2 += n3 + n8;
                this.mSlideOffset = (float)n8 / (float)this.mSlideRange;
                n3 = 0;
            } else if (this.mCanSlide && (n2 = this.mParallaxBy) != 0) {
                n3 = (int)((1.0f - this.mSlideOffset) * (float)n2);
                n2 = n;
            } else {
                n2 = n;
                n3 = 0;
            }
            if (bl2) {
                n8 = n5 - n2 + n3;
                n3 = n8 - n9;
            } else {
                n3 = n2 - n3;
                n8 = n3 + n9;
            }
            view.layout(n3, n6, n8, view.getMeasuredHeight() + n6);
            n += view.getWidth();
        }
        if (this.mFirstLayout) {
            if (this.mCanSlide) {
                if (this.mParallaxBy != 0) {
                    this.parallaxOtherViews(this.mSlideOffset);
                }
                if (((LayoutParams)this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                }
            } else {
                for (n = 0; n < n7; ++n) {
                    this.dimChildView(this.getChildAt(n), 0.0f, this.mSliderFadeColor);
                }
            }
            this.updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        View view;
        int n6;
        LayoutParams layoutParams;
        int n7 = View.MeasureSpec.getMode((int)n);
        int n8 = View.MeasureSpec.getSize((int)n);
        int n9 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        if (n7 != 1073741824) {
            if (!this.isInEditMode()) throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            if (n7 == Integer.MIN_VALUE) {
                n6 = n8;
                n4 = n9;
                n = n2;
            } else {
                n6 = n8;
                n4 = n9;
                n = n2;
                if (n7 == 0) {
                    n6 = 300;
                    n4 = n9;
                    n = n2;
                }
            }
        } else {
            n6 = n8;
            n4 = n9;
            n = n2;
            if (n9 == 0) {
                if (!this.isInEditMode()) throw new IllegalStateException("Height must not be UNSPECIFIED");
                n6 = n8;
                n4 = n9;
                n = n2;
                if (n9 == 0) {
                    n4 = Integer.MIN_VALUE;
                    n = 300;
                    n6 = n8;
                }
            }
        }
        if (n4 != Integer.MIN_VALUE) {
            if (n4 != 1073741824) {
                n = 0;
                n2 = 0;
            } else {
                n2 = n = n - this.getPaddingTop() - this.getPaddingBottom();
            }
        } else {
            n2 = n - this.getPaddingTop() - this.getPaddingBottom();
            n = 0;
        }
        int n10 = n6 - this.getPaddingLeft() - this.getPaddingRight();
        int n11 = this.getChildCount();
        if (n11 > 2) {
            Log.e((String)TAG, (String)"onMeasure: More than two child views are not supported.");
        }
        this.mSlideableView = null;
        n7 = n10;
        boolean bl = false;
        float f = 0.0f;
        n8 = n;
        for (n3 = 0; n3 < n11; ++n3) {
            view = this.getChildAt(n3);
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (view.getVisibility() == 8) {
                layoutParams.dimWhenOffset = false;
                continue;
            }
            float f2 = f;
            if (layoutParams.weight > 0.0f) {
                f2 = f += layoutParams.weight;
                if (layoutParams.width == 0) continue;
            }
            n = layoutParams.leftMargin + layoutParams.rightMargin;
            n = layoutParams.width == -2 ? View.MeasureSpec.makeMeasureSpec((int)(n10 - n), (int)Integer.MIN_VALUE) : (layoutParams.width == -1 ? View.MeasureSpec.makeMeasureSpec((int)(n10 - n), (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.width, (int)1073741824));
            n9 = layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824));
            view.measure(n, n9);
            n9 = view.getMeasuredWidth();
            n5 = view.getMeasuredHeight();
            n = n8;
            if (n4 == Integer.MIN_VALUE) {
                n = n8;
                if (n5 > n8) {
                    n = Math.min(n5, n2);
                }
            }
            boolean bl2 = (n9 = n7 - n9) < 0;
            layoutParams.slideable = bl2;
            bl2 = bl | bl2;
            n8 = n;
            bl = bl2;
            f = f2;
            n7 = n9;
            if (!layoutParams.slideable) continue;
            this.mSlideableView = view;
            n7 = n9;
            f = f2;
            bl = bl2;
            n8 = n;
        }
        if (bl || f > 0.0f) {
            n9 = n10 - this.mOverhangSize;
            for (n4 = 0; n4 < n11; ++n4) {
                view = this.getChildAt(n4);
                if (view.getVisibility() == 8) continue;
                layoutParams = (LayoutParams)view.getLayoutParams();
                if (view.getVisibility() == 8) continue;
                n = layoutParams.width == 0 && layoutParams.weight > 0.0f ? 1 : 0;
                n3 = n != 0 ? 0 : view.getMeasuredWidth();
                if (bl && view != this.mSlideableView) {
                    if (layoutParams.width >= 0 || n3 <= n9 && !(layoutParams.weight > 0.0f)) continue;
                    n = n != 0 ? (layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824))) : View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824);
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)n9, (int)1073741824), n);
                    continue;
                }
                if (!(layoutParams.weight > 0.0f)) continue;
                n = layoutParams.width == 0 ? (layoutParams.height == -2 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)Integer.MIN_VALUE) : (layoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)layoutParams.height, (int)1073741824))) : View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824);
                if (bl) {
                    int n12 = n10 - (layoutParams.leftMargin + layoutParams.rightMargin);
                    n5 = View.MeasureSpec.makeMeasureSpec((int)n12, (int)1073741824);
                    if (n3 == n12) continue;
                    view.measure(n5, n);
                    continue;
                }
                n5 = Math.max(0, n7);
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(n3 + (int)(layoutParams.weight * (float)n5 / f)), (int)1073741824), n);
            }
        }
        this.setMeasuredDimension(n6, n8 + this.getPaddingTop() + this.getPaddingBottom());
        this.mCanSlide = bl;
        if (this.mDragHelper.getViewDragState() == 0) return;
        if (bl) return;
        this.mDragHelper.abort();
    }

    void onPanelDragged(int n) {
        float f;
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        boolean bl = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        int n2 = this.mSlideableView.getWidth();
        int n3 = n;
        if (bl) {
            n3 = this.getWidth() - n - n2;
        }
        n = bl ? this.getPaddingRight() : this.getPaddingLeft();
        n2 = bl ? layoutParams.rightMargin : layoutParams.leftMargin;
        this.mSlideOffset = f = (float)(n3 - (n + n2)) / (float)this.mSlideRange;
        if (this.mParallaxBy != 0) {
            this.parallaxOtherViews(f);
        }
        if (layoutParams.dimWhenOffset) {
            this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
        this.dispatchOnPanelSlide(this.mSlideableView);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        if (parcelable.isOpen) {
            this.openPane();
        } else {
            this.closePane();
        }
        this.mPreservedOpenState = parcelable.isOpen;
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        boolean bl = this.isSlideable() ? this.isOpen() : this.mPreservedOpenState;
        savedState.isOpen = bl;
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n == n3) return;
        this.mFirstLayout = true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        if (!this.mCanSlide) {
            return super.onTouchEvent(motionEvent);
        }
        this.mDragHelper.processTouchEvent(motionEvent);
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            float f3 = motionEvent.getX();
            float f4 = motionEvent.getY();
            this.mInitialMotionX = f3;
            this.mInitialMotionY = f4;
            return true;
        }
        if (n != 1) {
            return true;
        }
        if (!this.isDimmed(this.mSlideableView)) return true;
        float f5 = motionEvent.getX();
        float f6 = f5 - this.mInitialMotionX;
        if (!(f6 * f6 + (f = (f2 = motionEvent.getY()) - this.mInitialMotionY) * f < (float)((n = this.mDragHelper.getTouchSlop()) * n))) return true;
        if (!this.mDragHelper.isViewUnder(this.mSlideableView, (int)f5, (int)f2)) return true;
        this.closePane(this.mSlideableView, 0);
        return true;
    }

    public boolean openPane() {
        return this.openPane(this.mSlideableView, 0);
    }

    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (this.isInTouchMode()) return;
        if (this.mCanSlide) return;
        boolean bl = view == this.mSlideableView;
        this.mPreservedOpenState = bl;
    }

    void setAllChildrenVisible() {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = this.getChildAt(n2);
            if (view.getVisibility() == 4) {
                view.setVisibility(0);
            }
            ++n2;
        }
    }

    public void setCoveredFadeColor(int n) {
        this.mCoveredFadeColor = n;
    }

    public void setPanelSlideListener(PanelSlideListener panelSlideListener) {
        this.mPanelSlideListener = panelSlideListener;
    }

    public void setParallaxDistance(int n) {
        this.mParallaxBy = n;
        this.requestLayout();
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable2) {
        this.setShadowDrawableLeft(drawable2);
    }

    public void setShadowDrawableLeft(Drawable drawable2) {
        this.mShadowDrawableLeft = drawable2;
    }

    public void setShadowDrawableRight(Drawable drawable2) {
        this.mShadowDrawableRight = drawable2;
    }

    @Deprecated
    public void setShadowResource(int n) {
        this.setShadowDrawable(this.getResources().getDrawable(n));
    }

    public void setShadowResourceLeft(int n) {
        this.setShadowDrawableLeft(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setShadowResourceRight(int n) {
        this.setShadowDrawableRight(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setSliderFadeColor(int n) {
        this.mSliderFadeColor = n;
    }

    @Deprecated
    public void smoothSlideClosed() {
        this.closePane();
    }

    @Deprecated
    public void smoothSlideOpen() {
        this.openPane();
    }

    boolean smoothSlideTo(float f, int n) {
        if (!this.mCanSlide) {
            return false;
        }
        boolean bl = this.isLayoutRtlSupport();
        Object object = (LayoutParams)this.mSlideableView.getLayoutParams();
        if (bl) {
            int n2 = this.getPaddingRight();
            int n3 = ((LayoutParams)object).rightMargin;
            n = this.mSlideableView.getWidth();
            n = (int)((float)this.getWidth() - ((float)(n2 + n3) + f * (float)this.mSlideRange + (float)n));
        } else {
            n = (int)((float)(this.getPaddingLeft() + ((LayoutParams)object).leftMargin) + f * (float)this.mSlideRange);
        }
        object = this.mDragHelper;
        View view = this.mSlideableView;
        if (!((ViewDragHelper)object).smoothSlideViewTo(view, n, view.getTop())) return false;
        this.setAllChildrenVisible();
        ViewCompat.postInvalidateOnAnimation((View)this);
        return true;
    }

    void updateObscuredViewsVisibility(View view) {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl = this.isLayoutRtlSupport();
        int n5 = bl ? this.getWidth() - this.getPaddingRight() : this.getPaddingLeft();
        int n6 = bl ? this.getPaddingLeft() : this.getWidth() - this.getPaddingRight();
        int n7 = this.getPaddingTop();
        int n8 = this.getHeight();
        int n9 = this.getPaddingBottom();
        if (view != null && SlidingPaneLayout.viewIsOpaque(view)) {
            n3 = view.getLeft();
            n2 = view.getRight();
            n = view.getTop();
            n4 = view.getBottom();
        } else {
            n3 = 0;
            n2 = 0;
            n = 0;
            n4 = 0;
        }
        int n10 = this.getChildCount();
        int n11 = 0;
        while (n11 < n10) {
            View view2 = this.getChildAt(n11);
            if (view2 == view) {
                return;
            }
            if (view2.getVisibility() != 8) {
                int n12 = bl ? n6 : n5;
                int n13 = Math.max(n12, view2.getLeft());
                int n14 = Math.max(n7, view2.getTop());
                n12 = bl ? n5 : n6;
                n12 = Math.min(n12, view2.getRight());
                int n15 = Math.min(n8 - n9, view2.getBottom());
                n12 = n13 >= n3 && n14 >= n && n12 <= n2 && n15 <= n4 ? 4 : 0;
                view2.setVisibility(n12);
            }
            ++n11;
        }
    }

    class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() {
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities());
        }

        public boolean filter(View view) {
            return SlidingPaneLayout.this.isDimmed(view);
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)SlidingPaneLayout.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat2);
            this.copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompat2);
            accessibilityNodeInfoCompat2.recycle();
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view);
            view = ViewCompat.getParentForAccessibility(view);
            if (view instanceof View) {
                accessibilityNodeInfoCompat.setParent(view);
            }
            int n = SlidingPaneLayout.this.getChildCount();
            int n2 = 0;
            while (n2 < n) {
                view = SlidingPaneLayout.this.getChildAt(n2);
                if (!this.filter(view) && view.getVisibility() == 0) {
                    ViewCompat.setImportantForAccessibility(view, 1);
                    accessibilityNodeInfoCompat.addChild(view);
                }
                ++n2;
            }
        }

        @Override
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (this.filter(view)) return false;
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }
    }

    private class DisableLayerRunnable
    implements Runnable {
        final View mChildView;

        DisableLayerRunnable(View view) {
            this.mChildView = view;
        }

        @Override
        public void run() {
            if (this.mChildView.getParent() == SlidingPaneLayout.this) {
                this.mChildView.setLayerType(0, null);
                SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
            }
            SlidingPaneLayout.this.mPostedRunnables.remove(this);
        }
    }

    private class DragHelperCallback
    extends ViewDragHelper.Callback {
        DragHelperCallback() {
        }

        @Override
        public int clampViewPositionHorizontal(View object, int n, int n2) {
            object = (LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
            if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
                n2 = SlidingPaneLayout.this.getWidth() - (SlidingPaneLayout.this.getPaddingRight() + object.rightMargin + SlidingPaneLayout.this.mSlideableView.getWidth());
                int n3 = SlidingPaneLayout.this.mSlideRange;
                return Math.max(Math.min(n, n2), n2 - n3);
            }
            int n4 = SlidingPaneLayout.this.getPaddingLeft() + object.leftMargin;
            n2 = SlidingPaneLayout.this.mSlideRange;
            return Math.min(Math.max(n, n4), n2 + n4);
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.mSlideRange;
        }

        @Override
        public void onEdgeDragStarted(int n, int n2) {
            SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, n2);
        }

        @Override
        public void onViewCaptured(View view, int n) {
            SlidingPaneLayout.this.setAllChildrenVisible();
        }

        @Override
        public void onViewDragStateChanged(int n) {
            if (SlidingPaneLayout.this.mDragHelper.getViewDragState() != 0) return;
            if (SlidingPaneLayout.this.mSlideOffset == 0.0f) {
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                slidingPaneLayout.updateObscuredViewsVisibility(slidingPaneLayout.mSlideableView);
                slidingPaneLayout = SlidingPaneLayout.this;
                slidingPaneLayout.dispatchOnPanelClosed(slidingPaneLayout.mSlideableView);
                SlidingPaneLayout.this.mPreservedOpenState = false;
                return;
            }
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            slidingPaneLayout.dispatchOnPanelOpened(slidingPaneLayout.mSlideableView);
            SlidingPaneLayout.this.mPreservedOpenState = true;
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            SlidingPaneLayout.this.onPanelDragged(n);
            SlidingPaneLayout.this.invalidate();
        }

        @Override
        public void onViewReleased(View view, float f, float f2) {
            int n;
            block7 : {
                int n2;
                block8 : {
                    LayoutParams layoutParams;
                    block4 : {
                        int n3;
                        block6 : {
                            block5 : {
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                if (!SlidingPaneLayout.this.isLayoutRtlSupport()) break block4;
                                n3 = SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin;
                                if (f < 0.0f) break block5;
                                n = n3;
                                if (f != 0.0f) break block6;
                                n = n3;
                                if (!(SlidingPaneLayout.this.mSlideOffset > 0.5f)) break block6;
                            }
                            n = n3 + SlidingPaneLayout.this.mSlideRange;
                        }
                        n3 = SlidingPaneLayout.this.mSlideableView.getWidth();
                        n = SlidingPaneLayout.this.getWidth() - n - n3;
                        break block7;
                    }
                    n = SlidingPaneLayout.this.getPaddingLeft();
                    n2 = layoutParams.leftMargin + n;
                    float f3 = f FCMPL 0.0f;
                    if (f3 > 0) break block8;
                    n = n2;
                    if (f3 != false) break block7;
                    n = n2;
                    if (!(SlidingPaneLayout.this.mSlideOffset > 0.5f)) break block7;
                }
                n = n2 + SlidingPaneLayout.this.mSlideRange;
            }
            SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(n, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            if (!SlidingPaneLayout.this.mIsUnableToDrag) return ((LayoutParams)view.getLayoutParams()).slideable;
            return false;
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        private static final int[] ATTRS = new int[]{16843137};
        Paint dimPaint;
        boolean dimWhenOffset;
        boolean slideable;
        public float weight = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, ATTRS);
            this.weight = context.getFloat(0, 0.0f);
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.weight = layoutParams.weight;
        }
    }

    public static interface PanelSlideListener {
        public void onPanelClosed(View var1);

        public void onPanelOpened(View var1);

        public void onPanelSlide(View var1, float var2);
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, null);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean isOpen;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            boolean bl = parcel.readInt() != 0;
            this.isOpen = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt((int)this.isOpen);
        }

    }

    public static class SimplePanelSlideListener
    implements PanelSlideListener {
        @Override
        public void onPanelClosed(View view) {
        }

        @Override
        public void onPanelOpened(View view) {
        }

        @Override
        public void onPanelSlide(View view, float f) {
        }
    }

}

