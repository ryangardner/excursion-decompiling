/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.AbsSavedState
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.R;
import androidx.coordinatorlayout.widget.DirectedAcyclicGraph;
import androidx.coordinatorlayout.widget.ViewGroupUtils;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Pools;
import androidx.core.view.GravityCompat;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout
extends ViewGroup
implements NestedScrollingParent2,
NestedScrollingParent3 {
    static final Class<?>[] CONSTRUCTOR_PARAMS;
    static final int EVENT_NESTED_SCROLL = 1;
    static final int EVENT_PRE_DRAW = 0;
    static final int EVENT_VIEW_REMOVED = 2;
    static final String TAG = "CoordinatorLayout";
    static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
    private static final int TYPE_ON_INTERCEPT = 0;
    private static final int TYPE_ON_TOUCH = 1;
    static final String WIDGET_PACKAGE_NAME;
    static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors;
    private static final Pools.Pool<Rect> sRectPool;
    private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
    private final int[] mBehaviorConsumed = new int[2];
    private View mBehaviorTouchView;
    private final DirectedAcyclicGraph<View> mChildDag = new DirectedAcyclicGraph();
    private final List<View> mDependencySortedChildren = new ArrayList<View>();
    private boolean mDisallowInterceptReset;
    private boolean mDrawStatusBarBackground;
    private boolean mIsAttachedToWindow;
    private int[] mKeylines;
    private WindowInsetsCompat mLastInsets;
    private boolean mNeedsPreDrawListener;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    private View mNestedScrollingTarget;
    private final int[] mNestedScrollingV2ConsumedCompat = new int[2];
    ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    private OnPreDrawListener mOnPreDrawListener;
    private Paint mScrimPaint;
    private Drawable mStatusBarBackground;
    private final List<View> mTempDependenciesList = new ArrayList<View>();
    private final List<View> mTempList1 = new ArrayList<View>();

    static {
        Object object = CoordinatorLayout.class.getPackage();
        object = object != null ? ((Package)object).getName() : null;
        WIDGET_PACKAGE_NAME = object;
        TOP_SORTED_CHILDREN_COMPARATOR = Build.VERSION.SDK_INT >= 21 ? new ViewElevationComparator() : null;
        CONSTRUCTOR_PARAMS = new Class[]{Context.class, AttributeSet.class};
        sConstructors = new ThreadLocal();
        sRectPool = new Pools.SynchronizedPool<Rect>(12);
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.coordinatorLayoutStyle);
    }

    public CoordinatorLayout(Context arrn, AttributeSet attributeSet, int n) {
        super((Context)arrn, attributeSet, n);
        int n2 = 0;
        TypedArray typedArray = n == 0 ? arrn.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout) : arrn.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout, n, 0);
        if (Build.VERSION.SDK_INT >= 29) {
            if (n == 0) {
                this.saveAttributeDataForStyleable((Context)arrn, R.styleable.CoordinatorLayout, attributeSet, typedArray, 0, R.style.Widget_Support_CoordinatorLayout);
            } else {
                this.saveAttributeDataForStyleable((Context)arrn, R.styleable.CoordinatorLayout, attributeSet, typedArray, n, 0);
            }
        }
        if ((n = typedArray.getResourceId(R.styleable.CoordinatorLayout_keylines, 0)) != 0) {
            arrn = arrn.getResources();
            this.mKeylines = arrn.getIntArray(n);
            float f = arrn.getDisplayMetrics().density;
            int n3 = this.mKeylines.length;
            for (n = n2; n < n3; ++n) {
                arrn = this.mKeylines;
                arrn[n] = (int)((float)arrn[n] * f);
            }
        }
        this.mStatusBarBackground = typedArray.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
        typedArray.recycle();
        this.setupForInsets();
        super.setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)new HierarchyChangeListener());
        if (ViewCompat.getImportantForAccessibility((View)this) != 0) return;
        ViewCompat.setImportantForAccessibility((View)this, 1);
    }

    private static Rect acquireTempRect() {
        Rect rect;
        Rect rect2 = rect = sRectPool.acquire();
        if (rect != null) return rect2;
        return new Rect();
    }

    private static int clamp(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        if (n <= n3) return n;
        return n3;
    }

    private void constrainChildRect(LayoutParams layoutParams, Rect rect, int n, int n2) {
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        n3 = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(rect.left, n3 - this.getPaddingRight() - n - layoutParams.rightMargin));
        n4 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(rect.top, n4 - this.getPaddingBottom() - n2 - layoutParams.bottomMargin));
        rect.set(n3, n4, n + n3, n2 + n4);
    }

    private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat.isConsumed()) {
            return windowInsetsCompat;
        }
        int n = 0;
        int n2 = this.getChildCount();
        do {
            WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
            if (n >= n2) return windowInsetsCompat2;
            View view = this.getChildAt(n);
            windowInsetsCompat2 = windowInsetsCompat;
            if (ViewCompat.getFitsSystemWindows(view)) {
                Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
                windowInsetsCompat2 = windowInsetsCompat;
                if (behavior != null) {
                    windowInsetsCompat2 = windowInsetsCompat = behavior.onApplyWindowInsets(this, view, windowInsetsCompat);
                    if (windowInsetsCompat.isConsumed()) {
                        return windowInsetsCompat;
                    }
                }
            }
            ++n;
            windowInsetsCompat = windowInsetsCompat2;
        } while (true);
    }

    private void getDesiredAnchoredChildRectWithoutConstraints(View view, int n, Rect rect, Rect rect2, LayoutParams layoutParams, int n2, int n3) {
        int n4 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveAnchoredChildGravity(layoutParams.gravity), n);
        n = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveGravity(layoutParams.anchorGravity), n);
        int n5 = n4 & 7;
        int n6 = n4 & 112;
        int n7 = n & 7;
        n4 = n & 112;
        n = n7 != 1 ? (n7 != 5 ? rect.left : rect.right) : rect.left + rect.width() / 2;
        n4 = n4 != 16 ? (n4 != 80 ? rect.top : rect.bottom) : rect.top + rect.height() / 2;
        if (n5 != 1) {
            n7 = n;
            if (n5 != 5) {
                n7 = n - n2;
            }
        } else {
            n7 = n - n2 / 2;
        }
        if (n6 != 16) {
            n = n4;
            if (n6 != 80) {
                n = n4 - n3;
            }
        } else {
            n = n4 - n3 / 2;
        }
        rect2.set(n7, n, n2 + n7, n3 + n);
    }

    private int getKeyline(int n) {
        Object object = this.mKeylines;
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No keylines defined for ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" - attempted index lookup ");
            ((StringBuilder)object).append(n);
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
            return 0;
        }
        if (n >= 0) {
            if (n < ((int[])object).length) return object[n];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Keyline index ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" out of range for ");
        ((StringBuilder)object).append(this);
        Log.e((String)TAG, (String)((StringBuilder)object).toString());
        return 0;
    }

    private void getTopSortedChildren(List<View> list) {
        list.clear();
        boolean bl = this.isChildrenDrawingOrderEnabled();
        int n = this.getChildCount();
        int n2 = n - 1;
        do {
            if (n2 < 0) {
                Comparator<View> comparator = TOP_SORTED_CHILDREN_COMPARATOR;
                if (comparator == null) return;
                Collections.sort(list, comparator);
                return;
            }
            int n3 = bl ? this.getChildDrawingOrder(n, n2) : n2;
            list.add(this.getChildAt(n3));
            --n2;
        } while (true);
    }

    private boolean hasDependencies(View view) {
        return this.mChildDag.hasOutgoingEdges(view);
    }

    private void layoutChild(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = CoordinatorLayout.acquireTempRect();
        rect.set(this.getPaddingLeft() + layoutParams.leftMargin, this.getPaddingTop() + layoutParams.topMargin, this.getWidth() - this.getPaddingRight() - layoutParams.rightMargin, this.getHeight() - this.getPaddingBottom() - layoutParams.bottomMargin);
        if (this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) && !ViewCompat.getFitsSystemWindows(view)) {
            rect.left += this.mLastInsets.getSystemWindowInsetLeft();
            rect.top += this.mLastInsets.getSystemWindowInsetTop();
            rect.right -= this.mLastInsets.getSystemWindowInsetRight();
            rect.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
        }
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        GravityCompat.apply(CoordinatorLayout.resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, rect2, n);
        view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
        CoordinatorLayout.releaseTempRect(rect);
        CoordinatorLayout.releaseTempRect(rect2);
    }

    private void layoutChildWithAnchor(View view, View view2, int n) {
        Rect rect = CoordinatorLayout.acquireTempRect();
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        try {
            this.getDescendantRect(view2, rect);
            this.getDesiredAnchoredChildRect(view, n, rect, rect2);
            view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom);
            return;
        }
        finally {
            CoordinatorLayout.releaseTempRect(rect);
            CoordinatorLayout.releaseTempRect(rect2);
        }
    }

    private void layoutChildWithKeyline(View view, int n, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(layoutParams.gravity), n2);
        int n4 = n3 & 7;
        int n5 = n3 & 112;
        int n6 = this.getWidth();
        int n7 = this.getHeight();
        int n8 = view.getMeasuredWidth();
        int n9 = view.getMeasuredHeight();
        n3 = n;
        if (n2 == 1) {
            n3 = n6 - n;
        }
        n = this.getKeyline(n3) - n8;
        n2 = 0;
        if (n4 != 1) {
            if (n4 == 5) {
                n += n8;
            }
        } else {
            n += n8 / 2;
        }
        if (n5 != 16) {
            if (n5 == 80) {
                n2 = n9 + 0;
            }
        } else {
            n2 = 0 + n9 / 2;
        }
        n = Math.max(this.getPaddingLeft() + layoutParams.leftMargin, Math.min(n, n6 - this.getPaddingRight() - n8 - layoutParams.rightMargin));
        n2 = Math.max(this.getPaddingTop() + layoutParams.topMargin, Math.min(n2, n7 - this.getPaddingBottom() - n9 - layoutParams.bottomMargin));
        view.layout(n, n2, n8 + n, n9 + n2);
    }

    private void offsetChildByInset(View object, Rect rect, int n) {
        if (!ViewCompat.isLaidOut((View)object)) {
            return;
        }
        if (object.getWidth() <= 0) return;
        if (object.getHeight() <= 0) {
            return;
        }
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        Behavior behavior = layoutParams.getBehavior();
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        Rect rect3 = CoordinatorLayout.acquireTempRect();
        rect3.set(object.getLeft(), object.getTop(), object.getRight(), object.getBottom());
        if (behavior != null && behavior.getInsetDodgeRect(this, object, rect2)) {
            if (!rect3.contains(rect2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Rect should be within the child's bounds. Rect:");
                ((StringBuilder)object).append(rect2.toShortString());
                ((StringBuilder)object).append(" | Bounds:");
                ((StringBuilder)object).append(rect3.toShortString());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        } else {
            rect2.set(rect3);
        }
        CoordinatorLayout.releaseTempRect(rect3);
        if (rect2.isEmpty()) {
            CoordinatorLayout.releaseTempRect(rect2);
            return;
        }
        int n2 = GravityCompat.getAbsoluteGravity(layoutParams.dodgeInsetEdges, n);
        int n3 = 1;
        if ((n2 & 48) == 48 && (n = rect2.top - layoutParams.topMargin - layoutParams.mInsetOffsetY) < rect.top) {
            this.setInsetOffsetY((View)object, rect.top - n);
            n = 1;
        } else {
            n = 0;
        }
        int n4 = n;
        if ((n2 & 80) == 80) {
            int n5 = this.getHeight() - rect2.bottom - layoutParams.bottomMargin + layoutParams.mInsetOffsetY;
            n4 = n;
            if (n5 < rect.bottom) {
                this.setInsetOffsetY((View)object, n5 - rect.bottom);
                n4 = 1;
            }
        }
        if (n4 == 0) {
            this.setInsetOffsetY((View)object, 0);
        }
        if ((n2 & 3) == 3 && (n = rect2.left - layoutParams.leftMargin - layoutParams.mInsetOffsetX) < rect.left) {
            this.setInsetOffsetX((View)object, rect.left - n);
            n = 1;
        } else {
            n = 0;
        }
        if ((n2 & 5) == 5 && (n4 = this.getWidth() - rect2.right - layoutParams.rightMargin + layoutParams.mInsetOffsetX) < rect.right) {
            this.setInsetOffsetX((View)object, n4 - rect.right);
            n = n3;
        }
        if (n == 0) {
            this.setInsetOffsetX((View)object, 0);
        }
        CoordinatorLayout.releaseTempRect(rect2);
    }

    static Behavior parseBehavior(Context object, AttributeSet attributeSet, String hashMap) {
        CharSequence charSequence;
        if (TextUtils.isEmpty((CharSequence)((Object)hashMap))) {
            return null;
        }
        if (((String)((Object)hashMap)).startsWith(".")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(object.getPackageName());
            ((StringBuilder)charSequence).append((String)((Object)hashMap));
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (((String)((Object)hashMap)).indexOf(46) >= 0) {
            charSequence = hashMap;
        } else {
            charSequence = hashMap;
            if (!TextUtils.isEmpty((CharSequence)WIDGET_PACKAGE_NAME)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(WIDGET_PACKAGE_NAME);
                ((StringBuilder)charSequence).append('.');
                ((StringBuilder)charSequence).append((String)((Object)hashMap));
                charSequence = ((StringBuilder)charSequence).toString();
            }
        }
        try {
            Constructor<?> constructor = sConstructors.get();
            hashMap = constructor;
            if (constructor == null) {
                hashMap = new HashMap();
                sConstructors.set(hashMap);
            }
            Constructor constructor2 = (Constructor)hashMap.get(charSequence);
            constructor = constructor2;
            if (constructor2 != null) return (Behavior)constructor.newInstance(new Object[]{object, attributeSet});
            constructor = Class.forName((String)charSequence, false, object.getClassLoader()).getConstructor(CONSTRUCTOR_PARAMS);
            constructor.setAccessible(true);
            hashMap.put(charSequence, constructor);
            return (Behavior)constructor.newInstance(new Object[]{object, attributeSet});
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not inflate Behavior subclass ");
            ((StringBuilder)object).append((String)charSequence);
            throw new RuntimeException(((StringBuilder)object).toString(), exception);
        }
    }

    private boolean performIntercept(MotionEvent motionEvent, int n) {
        boolean bl;
        int n2 = motionEvent.getActionMasked();
        List<View> list = this.mTempList1;
        this.getTopSortedChildren(list);
        int n3 = list.size();
        LayoutParams layoutParams = null;
        int n4 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        do {
            boolean bl4;
            boolean bl5;
            bl = bl2;
            if (n4 >= n3) break;
            View view = list.get(n4);
            LayoutParams layoutParams2 = (LayoutParams)view.getLayoutParams();
            Behavior behavior = layoutParams2.getBehavior();
            if ((bl2 || bl3) && n2 != 0) {
                layoutParams2 = layoutParams;
                bl5 = bl2;
                bl4 = bl3;
                if (behavior != null) {
                    layoutParams2 = layoutParams;
                    if (layoutParams == null) {
                        long l = SystemClock.uptimeMillis();
                        layoutParams2 = MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0);
                    }
                    if (n != 0) {
                        if (n != 1) {
                            bl5 = bl2;
                            bl4 = bl3;
                        } else {
                            behavior.onTouchEvent(this, view, (MotionEvent)layoutParams2);
                            bl5 = bl2;
                            bl4 = bl3;
                        }
                    } else {
                        behavior.onInterceptTouchEvent(this, view, (MotionEvent)layoutParams2);
                        bl5 = bl2;
                        bl4 = bl3;
                    }
                }
            } else {
                bl = bl2;
                if (!bl2) {
                    bl = bl2;
                    if (behavior != null) {
                        if (n != 0) {
                            if (n == 1) {
                                bl2 = behavior.onTouchEvent(this, view, motionEvent);
                            }
                        } else {
                            bl2 = behavior.onInterceptTouchEvent(this, view, motionEvent);
                        }
                        bl = bl2;
                        if (bl2) {
                            this.mBehaviorTouchView = view;
                            bl = bl2;
                        }
                    }
                }
                bl5 = layoutParams2.didBlockInteraction();
                bl2 = layoutParams2.isBlockingInteractionBelow(this, view);
                bl3 = bl2 && !bl5;
                layoutParams2 = layoutParams;
                bl5 = bl;
                bl4 = bl3;
                if (bl2) {
                    layoutParams2 = layoutParams;
                    bl5 = bl;
                    bl4 = bl3;
                    if (!bl3) break;
                }
            }
            ++n4;
            layoutParams = layoutParams2;
            bl2 = bl5;
            bl3 = bl4;
        } while (true);
        list.clear();
        return bl;
    }

    private void prepareChildren() {
        this.mDependencySortedChildren.clear();
        this.mChildDag.clear();
        int n = this.getChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
                Collections.reverse(this.mDependencySortedChildren);
                return;
            }
            View view = this.getChildAt(n2);
            LayoutParams layoutParams = this.getResolvedLayoutParams(view);
            layoutParams.findAnchorView(this, view);
            this.mChildDag.addNode(view);
            for (int i = 0; i < n; ++i) {
                View view2;
                if (i == n2 || !layoutParams.dependsOn(this, view, view2 = this.getChildAt(i))) continue;
                if (!this.mChildDag.contains(view2)) {
                    this.mChildDag.addNode(view2);
                }
                this.mChildDag.addEdge(view2, view);
            }
            ++n2;
        } while (true);
    }

    private static void releaseTempRect(Rect rect) {
        rect.setEmpty();
        sRectPool.release(rect);
    }

    private void resetTouchBehaviors(boolean bl) {
        int n;
        int n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
            if (behavior == null) continue;
            long l = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0);
            if (bl) {
                behavior.onInterceptTouchEvent(this, view, motionEvent);
            } else {
                behavior.onTouchEvent(this, view, motionEvent);
            }
            motionEvent.recycle();
        }
        n = 0;
        do {
            if (n >= n2) {
                this.mBehaviorTouchView = null;
                this.mDisallowInterceptReset = false;
                return;
            }
            ((LayoutParams)this.getChildAt(n).getLayoutParams()).resetTouchBehaviorTracking();
            ++n;
        } while (true);
    }

    private static int resolveAnchoredChildGravity(int n) {
        int n2 = n;
        if (n != 0) return n2;
        return 17;
    }

    private static int resolveGravity(int n) {
        int n2 = n;
        if ((n & 7) == 0) {
            n2 = n | 8388611;
        }
        n = n2;
        if ((n2 & 112) != 0) return n;
        return n2 | 48;
    }

    private static int resolveKeylineGravity(int n) {
        int n2 = n;
        if (n != 0) return n2;
        return 8388661;
    }

    private void setInsetOffsetX(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mInsetOffsetX == n) return;
        ViewCompat.offsetLeftAndRight(view, n - layoutParams.mInsetOffsetX);
        layoutParams.mInsetOffsetX = n;
    }

    private void setInsetOffsetY(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mInsetOffsetY == n) return;
        ViewCompat.offsetTopAndBottom(view, n - layoutParams.mInsetOffsetY);
        layoutParams.mInsetOffsetY = n;
    }

    private void setupForInsets() {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (!ViewCompat.getFitsSystemWindows((View)this)) {
            ViewCompat.setOnApplyWindowInsetsListener((View)this, null);
            return;
        }
        if (this.mApplyWindowInsetsListener == null) {
            this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener(){

                @Override
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    return CoordinatorLayout.this.setWindowInsets(windowInsetsCompat);
                }
            };
        }
        ViewCompat.setOnApplyWindowInsetsListener((View)this, this.mApplyWindowInsetsListener);
        this.setSystemUiVisibility(1280);
    }

    void addPreDrawListener() {
        if (this.mIsAttachedToWindow) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = true;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams)) return false;
        if (!super.checkLayoutParams(layoutParams)) return false;
        return true;
    }

    public void dispatchDependentViewsChanged(View view) {
        List list = this.mChildDag.getIncomingEdges(view);
        if (list == null) return;
        if (list.isEmpty()) return;
        int n = 0;
        while (n < list.size()) {
            View view2 = (View)list.get(n);
            Behavior behavior = ((LayoutParams)view2.getLayoutParams()).getBehavior();
            if (behavior != null) {
                behavior.onDependentViewChanged(this, view2, view);
            }
            ++n;
        }
    }

    public boolean doViewsOverlap(View view, View view2) {
        int n = view.getVisibility();
        boolean bl = false;
        if (n != 0) return false;
        if (view2.getVisibility() != 0) return false;
        Rect rect = CoordinatorLayout.acquireTempRect();
        boolean bl2 = view.getParent() != this;
        this.getChildRect(view, bl2, rect);
        view = CoordinatorLayout.acquireTempRect();
        bl2 = view2.getParent() != this;
        this.getChildRect(view2, bl2, (Rect)view);
        bl2 = bl;
        try {
            if (rect.left > view.right) return bl2;
            bl2 = bl;
            if (rect.top > view.bottom) return bl2;
            bl2 = bl;
            if (rect.right < view.left) return bl2;
            int n2 = rect.bottom;
            n = view.top;
            bl2 = bl;
            if (n2 < n) return bl2;
            bl2 = true;
            return bl2;
        }
        finally {
            CoordinatorLayout.releaseTempRect(rect);
            CoordinatorLayout.releaseTempRect((Rect)view);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.mBehavior == null) return super.drawChild(canvas, view, l);
        float f = layoutParams.mBehavior.getScrimOpacity(this, view);
        if (!(f > 0.0f)) return super.drawChild(canvas, view, l);
        if (this.mScrimPaint == null) {
            this.mScrimPaint = new Paint();
        }
        this.mScrimPaint.setColor(layoutParams.mBehavior.getScrimColor(this, view));
        this.mScrimPaint.setAlpha(CoordinatorLayout.clamp(Math.round(f * 255.0f), 0, 255));
        int n = canvas.save();
        if (view.isOpaque()) {
            canvas.clipRect((float)view.getLeft(), (float)view.getTop(), (float)view.getRight(), (float)view.getBottom(), Region.Op.DIFFERENCE);
        }
        canvas.drawRect((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(this.getWidth() - this.getPaddingRight()), (float)(this.getHeight() - this.getPaddingBottom()), this.mScrimPaint);
        canvas.restoreToCount(n);
        return super.drawChild(canvas, view, l);
    }

    protected void drawableStateChanged() {
        boolean bl;
        super.drawableStateChanged();
        int[] arrn = this.getDrawableState();
        Drawable drawable2 = this.mStatusBarBackground;
        boolean bl2 = bl = false;
        if (drawable2 != null) {
            bl2 = bl;
            if (drawable2.isStateful()) {
                bl2 = false | drawable2.setState(arrn);
            }
        }
        if (!bl2) return;
        this.invalidate();
    }

    void ensurePreDrawListener() {
        boolean bl;
        int n = this.getChildCount();
        boolean bl2 = false;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            if (this.hasDependencies(this.getChildAt(n2))) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        if (bl == this.mNeedsPreDrawListener) return;
        if (bl) {
            this.addPreDrawListener();
            return;
        }
        this.removePreDrawListener();
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) return new LayoutParams(layoutParams);
        return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
    }

    void getChildRect(View view, boolean bl, Rect rect) {
        if (!view.isLayoutRequested() && view.getVisibility() != 8) {
            if (bl) {
                this.getDescendantRect(view, rect);
                return;
            }
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            return;
        }
        rect.setEmpty();
    }

    public List<View> getDependencies(View object) {
        object = this.mChildDag.getOutgoingEdges((View)object);
        this.mTempDependenciesList.clear();
        if (object == null) return this.mTempDependenciesList;
        this.mTempDependenciesList.addAll((Collection<View>)object);
        return this.mTempDependenciesList;
    }

    final List<View> getDependencySortedChildren() {
        this.prepareChildren();
        return Collections.unmodifiableList(this.mDependencySortedChildren);
    }

    public List<View> getDependents(View object) {
        object = this.mChildDag.getIncomingEdges((View)object);
        this.mTempDependenciesList.clear();
        if (object == null) return this.mTempDependenciesList;
        this.mTempDependenciesList.addAll((Collection<View>)object);
        return this.mTempDependenciesList;
    }

    void getDescendantRect(View view, Rect rect) {
        ViewGroupUtils.getDescendantRect(this, view, rect);
    }

    void getDesiredAnchoredChildRect(View view, int n, Rect rect, Rect rect2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n2 = view.getMeasuredWidth();
        int n3 = view.getMeasuredHeight();
        this.getDesiredAnchoredChildRectWithoutConstraints(view, n, rect, rect2, layoutParams, n2, n3);
        this.constrainChildRect(layoutParams, rect2, n2, n3);
    }

    void getLastChildRect(View view, Rect rect) {
        rect.set(((LayoutParams)view.getLayoutParams()).getLastChildRect());
    }

    public final WindowInsetsCompat getLastWindowInsets() {
        return this.mLastInsets;
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    LayoutParams getResolvedLayoutParams(View object) {
        Object object2;
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (layoutParams.mBehaviorResolved) return layoutParams;
        if (object instanceof AttachedBehavior) {
            if ((object = ((AttachedBehavior)object).getBehavior()) == null) {
                Log.e((String)TAG, (String)"Attached behavior class is null");
            }
            layoutParams.setBehavior((Behavior)object);
            layoutParams.mBehaviorResolved = true;
            return layoutParams;
        }
        Class<?> class_ = object.getClass();
        object = null;
        while (class_ != null) {
            object = object2 = class_.getAnnotation(DefaultBehavior.class);
            if (object2 != null) break;
            class_ = class_.getSuperclass();
            object = object2;
        }
        if (object != null) {
            try {
                layoutParams.setBehavior(object.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
            }
            catch (Exception exception) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Default behavior class ");
                ((StringBuilder)object2).append(object.value().getName());
                ((StringBuilder)object2).append(" could not be instantiated. Did you forget a default constructor?");
                Log.e((String)TAG, (String)((StringBuilder)object2).toString(), (Throwable)exception);
            }
        }
        layoutParams.mBehaviorResolved = true;
        return layoutParams;
    }

    public Drawable getStatusBarBackground() {
        return this.mStatusBarBackground;
    }

    protected int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), this.getPaddingTop() + this.getPaddingBottom());
    }

    protected int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), this.getPaddingLeft() + this.getPaddingRight());
    }

    public boolean isPointInChildBounds(View view, int n, int n2) {
        Rect rect = CoordinatorLayout.acquireTempRect();
        this.getDescendantRect(view, rect);
        try {
            boolean bl = rect.contains(n, n2);
            return bl;
        }
        finally {
            CoordinatorLayout.releaseTempRect(rect);
        }
    }

    void offsetChildToAnchor(View view, int n) {
        LayoutParams layoutParams;
        int n2;
        int n3;
        Rect rect;
        Behavior behavior;
        Rect rect2;
        Rect rect3;
        int n4;
        block7 : {
            block6 : {
                layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mAnchorView == null) return;
                rect3 = CoordinatorLayout.acquireTempRect();
                rect = CoordinatorLayout.acquireTempRect();
                rect2 = CoordinatorLayout.acquireTempRect();
                this.getDescendantRect(layoutParams.mAnchorView, rect3);
                n2 = 0;
                this.getChildRect(view, false, rect);
                n4 = view.getMeasuredWidth();
                n3 = view.getMeasuredHeight();
                this.getDesiredAnchoredChildRectWithoutConstraints(view, n, rect3, rect2, layoutParams, n4, n3);
                if (rect2.left != rect.left) break block6;
                n = n2;
                if (rect2.top == rect.top) break block7;
            }
            n = 1;
        }
        this.constrainChildRect(layoutParams, rect2, n4, n3);
        n3 = rect2.left - rect.left;
        n2 = rect2.top - rect.top;
        if (n3 != 0) {
            ViewCompat.offsetLeftAndRight(view, n3);
        }
        if (n2 != 0) {
            ViewCompat.offsetTopAndBottom(view, n2);
        }
        if (n != 0 && (behavior = layoutParams.getBehavior()) != null) {
            behavior.onDependentViewChanged(this, view, layoutParams.mAnchorView);
        }
        CoordinatorLayout.releaseTempRect(rect3);
        CoordinatorLayout.releaseTempRect(rect);
        CoordinatorLayout.releaseTempRect(rect2);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.resetTouchBehaviors(false);
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            this.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null && ViewCompat.getFitsSystemWindows((View)this)) {
            ViewCompat.requestApplyInsets((View)this);
        }
        this.mIsAttachedToWindow = true;
    }

    final void onChildViewsChanged(int n) {
        int n2 = ViewCompat.getLayoutDirection((View)this);
        int n3 = this.mDependencySortedChildren.size();
        Rect rect = CoordinatorLayout.acquireTempRect();
        Rect rect2 = CoordinatorLayout.acquireTempRect();
        Rect rect3 = CoordinatorLayout.acquireTempRect();
        int n4 = 0;
        do {
            block20 : {
                int n5;
                LayoutParams layoutParams;
                Object object;
                View view;
                block21 : {
                    if (n4 >= n3) {
                        CoordinatorLayout.releaseTempRect(rect);
                        CoordinatorLayout.releaseTempRect(rect2);
                        CoordinatorLayout.releaseTempRect(rect3);
                        return;
                    }
                    view = this.mDependencySortedChildren.get(n4);
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    if (n == 0 && view.getVisibility() == 8) break block20;
                    for (n5 = 0; n5 < n4; ++n5) {
                        object = this.mDependencySortedChildren.get(n5);
                        if (layoutParams.mAnchorDirectChild != object) continue;
                        this.offsetChildToAnchor(view, n2);
                    }
                    this.getChildRect(view, true, rect2);
                    if (layoutParams.insetEdge != 0 && !rect2.isEmpty()) {
                        int n6 = GravityCompat.getAbsoluteGravity(layoutParams.insetEdge, n2);
                        n5 = n6 & 112;
                        if (n5 != 48) {
                            if (n5 == 80) {
                                rect.bottom = Math.max(rect.bottom, this.getHeight() - rect2.top);
                            }
                        } else {
                            rect.top = Math.max(rect.top, rect2.bottom);
                        }
                        n5 = n6 & 7;
                        if (n5 != 3) {
                            if (n5 == 5) {
                                rect.right = Math.max(rect.right, this.getWidth() - rect2.left);
                            }
                        } else {
                            rect.left = Math.max(rect.left, rect2.right);
                        }
                    }
                    if (layoutParams.dodgeInsetEdges != 0 && view.getVisibility() == 0) {
                        this.offsetChildByInset(view, rect, n2);
                    }
                    if (n == 2) break block21;
                    this.getLastChildRect(view, rect3);
                    if (rect3.equals((Object)rect2)) break block20;
                    this.recordLastChildRect(view, rect2);
                }
                for (n5 = n4 + 1; n5 < n3; ++n5) {
                    boolean bl;
                    View view2 = this.mDependencySortedChildren.get(n5);
                    layoutParams = (LayoutParams)view2.getLayoutParams();
                    object = layoutParams.getBehavior();
                    if (object == null || !((Behavior)object).layoutDependsOn(this, view2, view)) continue;
                    if (n == 0 && layoutParams.getChangedAfterNestedScroll()) {
                        layoutParams.resetChangedAfterNestedScroll();
                        continue;
                    }
                    if (n != 2) {
                        bl = ((Behavior)object).onDependentViewChanged(this, view2, view);
                    } else {
                        ((Behavior)object).onDependentViewRemoved(this, view2, view);
                        bl = true;
                    }
                    if (n != 1) continue;
                    layoutParams.setChangedAfterNestedScroll(bl);
                }
            }
            ++n4;
        } while (true);
    }

    public void onDetachedFromWindow() {
        View view;
        super.onDetachedFromWindow();
        this.resetTouchBehaviors(false);
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        if ((view = this.mNestedScrollingTarget) != null) {
            this.onStopNestedScroll(view);
        }
        this.mIsAttachedToWindow = false;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mDrawStatusBarBackground) return;
        if (this.mStatusBarBackground == null) return;
        WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
        int n = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if (n <= 0) return;
        this.mStatusBarBackground.setBounds(0, 0, this.getWidth(), n);
        this.mStatusBarBackground.draw(canvas);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.resetTouchBehaviors(true);
        }
        boolean bl = this.performIntercept(motionEvent, 0);
        if (n != 1) {
            if (n != 3) return bl;
        }
        this.resetTouchBehaviors(true);
        return bl;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n3 = ViewCompat.getLayoutDirection((View)this);
        n2 = this.mDependencySortedChildren.size();
        n = 0;
        while (n < n2) {
            Behavior behavior;
            View view = this.mDependencySortedChildren.get(n);
            if (!(view.getVisibility() == 8 || (behavior = ((LayoutParams)view.getLayoutParams()).getBehavior()) != null && behavior.onLayoutChild(this, view, n3))) {
                this.onLayoutChild(view, n3);
            }
            ++n;
        }
    }

    public void onLayoutChild(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.checkAnchorChanged()) throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        if (layoutParams.mAnchorView != null) {
            this.layoutChildWithAnchor(view, layoutParams.mAnchorView, n);
            return;
        }
        if (layoutParams.keyline >= 0) {
            this.layoutChildWithKeyline(view, layoutParams.keyline, n);
            return;
        }
        this.layoutChild(view, n);
    }

    /*
     * Unable to fully structure code
     */
    protected void onMeasure(int var1_1, int var2_2) {
        this.prepareChildren();
        this.ensurePreDrawListener();
        var3_3 = this.getPaddingLeft();
        var4_4 = this.getPaddingTop();
        var5_5 = this.getPaddingRight();
        var6_6 = this.getPaddingBottom();
        var7_7 = ViewCompat.getLayoutDirection((View)this);
        var8_8 = var7_7 == 1;
        var9_9 = View.MeasureSpec.getMode((int)var1_1);
        var10_10 = View.MeasureSpec.getSize((int)var1_1);
        var11_11 = View.MeasureSpec.getMode((int)var2_2);
        var12_12 = View.MeasureSpec.getSize((int)var2_2);
        var13_13 = this.getSuggestedMinimumWidth();
        var14_14 = this.getSuggestedMinimumHeight();
        var15_15 = this.mLastInsets != null && ViewCompat.getFitsSystemWindows((View)this) != false;
        var16_16 = this.mDependencySortedChildren.size();
        var17_17 = 0;
        var18_18 = 0;
        var19_19 = var3_3;
        do {
            block9 : {
                var20_20 = var19_19;
                if (var18_18 >= var16_16) {
                    this.setMeasuredDimension(View.resolveSizeAndState((int)var13_13, (int)var1_1, (int)(-16777216 & var17_17)), View.resolveSizeAndState((int)var14_14, (int)var2_2, (int)(var17_17 << 16)));
                    return;
                }
                var21_21 = this.mDependencySortedChildren.get(var18_18);
                if (var21_21.getVisibility() == 8) break block9;
                var22_22 = (LayoutParams)var21_21.getLayoutParams();
                if (var22_22.keyline < 0 || var9_9 == 0) ** GOTO lbl-1000
                var19_19 = this.getKeyline(var22_22.keyline);
                var23_23 = GravityCompat.getAbsoluteGravity(CoordinatorLayout.resolveKeylineGravity(var22_22.gravity), var7_7) & 7;
                if (var23_23 == 3 && !var8_8 || var23_23 == 5 && var8_8) {
                    var19_19 = Math.max(0, var10_10 - var5_5 - var19_19);
                } else if (var23_23 == 5 && !var8_8 || var23_23 == 3 && var8_8) {
                    var19_19 = Math.max(0, var19_19 - var20_20);
                } else lbl-1000: // 2 sources:
                {
                    var19_19 = 0;
                }
                var24_24 = var17_17;
                if (var15_15 && !ViewCompat.getFitsSystemWindows(var21_21)) {
                    var25_25 = this.mLastInsets.getSystemWindowInsetLeft();
                    var23_23 = this.mLastInsets.getSystemWindowInsetRight();
                    var17_17 = this.mLastInsets.getSystemWindowInsetTop();
                    var26_26 = this.mLastInsets.getSystemWindowInsetBottom();
                    var23_23 = View.MeasureSpec.makeMeasureSpec((int)(var10_10 - (var25_25 + var23_23)), (int)var9_9);
                    var26_26 = var17_17 = View.MeasureSpec.makeMeasureSpec((int)(var12_12 - (var17_17 + var26_26)), (int)var11_11);
                } else {
                    var17_17 = var1_1;
                    var26_26 = var2_2;
                    var23_23 = var17_17;
                }
                var27_27 = var22_22.getBehavior();
                if (var27_27 == null || !var27_27.onMeasureChild(this, var21_21, var23_23, var19_19, var26_26, 0)) {
                    this.onMeasureChild(var21_21, var23_23, var19_19, var26_26, 0);
                }
                var13_13 = Math.max(var13_13, var3_3 + var5_5 + var21_21.getMeasuredWidth() + var22_22.leftMargin + var22_22.rightMargin);
                var14_14 = Math.max(var14_14, var4_4 + var6_6 + var21_21.getMeasuredHeight() + var22_22.topMargin + var22_22.bottomMargin);
                var17_17 = View.combineMeasuredStates((int)var24_24, (int)var21_21.getMeasuredState());
            }
            ++var18_18;
            var19_19 = var20_20;
        } while (true);
    }

    public void onMeasureChild(View view, int n, int n2, int n3, int n4) {
        this.measureChildWithMargins(view, n, n2, n3, n4);
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        int n = this.getChildCount();
        int n2 = 0;
        boolean bl2 = false;
        do {
            boolean bl3;
            if (n2 >= n) {
                if (!bl2) return bl2;
                this.onChildViewsChanged(1);
                return bl2;
            }
            View view2 = this.getChildAt(n2);
            if (view2.getVisibility() == 8) {
                bl3 = bl2;
            } else {
                Object object = (LayoutParams)view2.getLayoutParams();
                if (!object.isNestedScrollAccepted(0)) {
                    bl3 = bl2;
                } else {
                    object = object.getBehavior();
                    bl3 = bl2;
                    if (object != null) {
                        bl3 = bl2 | ((Behavior)object).onNestedFling(this, view2, view, f, f2, bl);
                    }
                }
            }
            ++n2;
            bl2 = bl3;
        } while (true);
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        int n = this.getChildCount();
        int n2 = 0;
        boolean bl = false;
        while (n2 < n) {
            boolean bl2;
            View view2 = this.getChildAt(n2);
            if (view2.getVisibility() == 8) {
                bl2 = bl;
            } else {
                Object object = (LayoutParams)view2.getLayoutParams();
                if (!object.isNestedScrollAccepted(0)) {
                    bl2 = bl;
                } else {
                    object = object.getBehavior();
                    bl2 = bl;
                    if (object != null) {
                        bl2 = bl | ((Behavior)object).onNestedPreFling(this, view2, view, f, f2);
                    }
                }
            }
            ++n2;
            bl = bl2;
        }
        return bl;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        this.onNestedPreScroll(view, n, n2, arrn, 0);
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn, int n3) {
        int n4 = this.getChildCount();
        boolean bl = false;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        do {
            int n8;
            int n9;
            if (n5 >= n4) {
                arrn[0] = n6;
                arrn[1] = n7;
                if (!bl) return;
                this.onChildViewsChanged(1);
                return;
            }
            int[] arrn2 = this.getChildAt(n5);
            if (arrn2.getVisibility() == 8) {
                n8 = n6;
                n9 = n7;
            } else {
                int[] arrn3 = (int[])arrn2.getLayoutParams();
                if (!arrn3.isNestedScrollAccepted(n3)) {
                    n8 = n6;
                    n9 = n7;
                } else {
                    Behavior behavior = arrn3.getBehavior();
                    n8 = n6;
                    n9 = n7;
                    if (behavior != null) {
                        arrn3 = this.mBehaviorConsumed;
                        arrn3[0] = 0;
                        arrn3[1] = 0;
                        behavior.onNestedPreScroll(this, arrn2, view, n, n2, arrn3, n3);
                        arrn2 = this.mBehaviorConsumed;
                        n9 = n > 0 ? Math.max(n6, arrn2[0]) : Math.min(n6, arrn2[0]);
                        n8 = n9;
                        arrn2 = this.mBehaviorConsumed;
                        n9 = n2 > 0 ? Math.max(n7, arrn2[1]) : Math.min(n7, arrn2[1]);
                        bl = true;
                    }
                }
            }
            ++n5;
            n6 = n8;
            n7 = n9;
        } while (true);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.onNestedScroll(view, n, n2, n3, n4, 0);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5) {
        this.onNestedScroll(view, n, n2, n3, n4, 0, this.mNestedScrollingV2ConsumedCompat);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5, int[] arrn) {
        int n6 = this.getChildCount();
        boolean bl = false;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        do {
            int n10;
            int n11;
            if (n7 >= n6) {
                arrn[0] = arrn[0] + n8;
                arrn[1] = arrn[1] + n9;
                if (!bl) return;
                this.onChildViewsChanged(1);
                return;
            }
            int[] arrn2 = this.getChildAt(n7);
            if (arrn2.getVisibility() == 8) {
                n10 = n8;
                n11 = n9;
            } else {
                Object object = (LayoutParams)arrn2.getLayoutParams();
                if (!object.isNestedScrollAccepted(n5)) {
                    n10 = n8;
                    n11 = n9;
                } else {
                    object = object.getBehavior();
                    n10 = n8;
                    n11 = n9;
                    if (object != null) {
                        int[] arrn3 = this.mBehaviorConsumed;
                        arrn3[0] = 0;
                        arrn3[1] = 0;
                        ((Behavior)object).onNestedScroll(this, arrn2, view, n, n2, n3, n4, n5, arrn3);
                        arrn2 = this.mBehaviorConsumed;
                        n11 = n3 > 0 ? Math.max(n8, arrn2[0]) : Math.min(n8, arrn2[0]);
                        n10 = n11;
                        n11 = n4 > 0 ? Math.max(n9, this.mBehaviorConsumed[1]) : Math.min(n9, this.mBehaviorConsumed[1]);
                        bl = true;
                    }
                }
            }
            ++n7;
            n8 = n10;
            n9 = n11;
        } while (true);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.onNestedScrollAccepted(view, view2, n, 0);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n, int n2) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, n, n2);
        this.mNestedScrollingTarget = view2;
        int n3 = this.getChildCount();
        int n4 = 0;
        while (n4 < n3) {
            View view3 = this.getChildAt(n4);
            Object object = (LayoutParams)view3.getLayoutParams();
            if (object.isNestedScrollAccepted(n2) && (object = object.getBehavior()) != null) {
                ((Behavior)object).onNestedScrollAccepted(this, view3, view, view2, n, n2);
            }
            ++n4;
        }
    }

    protected void onRestoreInstanceState(Parcelable sparseArray) {
        if (!(sparseArray instanceof SavedState)) {
            super.onRestoreInstanceState((Parcelable)sparseArray);
            return;
        }
        sparseArray = (SavedState)sparseArray;
        super.onRestoreInstanceState(sparseArray.getSuperState());
        sparseArray = sparseArray.behaviorStates;
        int n = 0;
        int n2 = this.getChildCount();
        while (n < n2) {
            Parcelable parcelable;
            View view = this.getChildAt(n);
            int n3 = view.getId();
            Behavior behavior = this.getResolvedLayoutParams(view).getBehavior();
            if (n3 != -1 && behavior != null && (parcelable = (Parcelable)sparseArray.get(n3)) != null) {
                behavior.onRestoreInstanceState(this, view, parcelable);
            }
            ++n;
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray sparseArray = new SparseArray();
        int n = this.getChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                savedState.behaviorStates = sparseArray;
                return savedState;
            }
            View view = this.getChildAt(n2);
            int n3 = view.getId();
            Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
            if (n3 != -1 && behavior != null && (view = behavior.onSaveInstanceState(this, view)) != null) {
                sparseArray.append(n3, (Object)view);
            }
            ++n2;
        } while (true);
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return this.onStartNestedScroll(view, view2, n, 0);
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n, int n2) {
        int n3 = this.getChildCount();
        int n4 = 0;
        boolean bl = false;
        while (n4 < n3) {
            View view3 = this.getChildAt(n4);
            if (view3.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view3.getLayoutParams();
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    boolean bl2 = behavior.onStartNestedScroll(this, view3, view, view2, n, n2);
                    bl |= bl2;
                    layoutParams.setNestedScrollAccepted(n2, bl2);
                } else {
                    layoutParams.setNestedScrollAccepted(n2, false);
                }
            }
            ++n4;
        }
        return bl;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.onStopNestedScroll(view, 0);
    }

    @Override
    public void onStopNestedScroll(View view, int n) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view, n);
        int n2 = this.getChildCount();
        int n3 = 0;
        do {
            if (n3 >= n2) {
                this.mNestedScrollingTarget = null;
                return;
            }
            View view2 = this.getChildAt(n3);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (layoutParams.isNestedScrollAccepted(n)) {
                Behavior behavior = layoutParams.getBehavior();
                if (behavior != null) {
                    behavior.onStopNestedScroll(this, view2, view, n);
                }
                layoutParams.resetNestedScroll(n);
                layoutParams.resetChangedAfterNestedScroll();
            }
            ++n3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block8 : {
            block7 : {
                var2_6 = var1_1 /* !! */ .getActionMasked();
                if (this.mBehaviorTouchView != null) break block7;
                var4_8 = var3_7 = this.performIntercept(var1_1 /* !! */ , 1);
                if (!var3_7) ** GOTO lbl-1000
                break block8;
            }
            var3_7 = false;
        }
        var5_9 = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
        var4_8 = var3_7;
        if (var5_9 != null) {
            var6_10 = var5_9.onTouchEvent(this, this.mBehaviorTouchView, var1_1 /* !! */ );
            var4_8 = var3_7;
            var3_7 = var6_10;
        } else lbl-1000: // 2 sources:
        {
            var3_7 = false;
        }
        var7_11 = this.mBehaviorTouchView;
        var5_9 = null;
        if (var7_11 == null) {
            var6_10 = var3_7 | super.onTouchEvent(var1_1 /* !! */ );
            var1_2 = var5_9;
        } else {
            var6_10 = var3_7;
            var1_3 = var5_9;
            if (var4_8) {
                var8_12 = SystemClock.uptimeMillis();
                var1_4 = MotionEvent.obtain((long)var8_12, (long)var8_12, (int)3, (float)0.0f, (float)0.0f, (int)0);
                super.onTouchEvent(var1_4);
                var6_10 = var3_7;
            }
        }
        if (var1_5 != null) {
            var1_5.recycle();
        }
        if (var2_6 != 1) {
            if (var2_6 != 3) return var6_10;
        }
        this.resetTouchBehaviors(false);
        return var6_10;
    }

    void recordLastChildRect(View view, Rect rect) {
        ((LayoutParams)view.getLayoutParams()).setLastChildRect(rect);
    }

    void removePreDrawListener() {
        if (this.mIsAttachedToWindow && this.mOnPreDrawListener != null) {
            this.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this.mOnPreDrawListener);
        }
        this.mNeedsPreDrawListener = false;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        Behavior behavior = ((LayoutParams)view.getLayoutParams()).getBehavior();
        if (behavior == null) return super.requestChildRectangleOnScreen(view, rect, bl);
        if (!behavior.onRequestChildRectangleOnScreen(this, view, rect, bl)) return super.requestChildRectangleOnScreen(view, rect, bl);
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        super.requestDisallowInterceptTouchEvent(bl);
        if (!bl) return;
        if (this.mDisallowInterceptReset) return;
        this.resetTouchBehaviors(false);
        this.mDisallowInterceptReset = true;
    }

    public void setFitsSystemWindows(boolean bl) {
        super.setFitsSystemWindows(bl);
        this.setupForInsets();
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    public void setStatusBarBackground(Drawable drawable2) {
        Drawable drawable3 = this.mStatusBarBackground;
        if (drawable3 == drawable2) return;
        Drawable drawable4 = null;
        if (drawable3 != null) {
            drawable3.setCallback(null);
        }
        if (drawable2 != null) {
            drawable4 = drawable2.mutate();
        }
        this.mStatusBarBackground = drawable4;
        if (drawable4 != null) {
            if (drawable4.isStateful()) {
                this.mStatusBarBackground.setState(this.getDrawableState());
            }
            DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection((View)this));
            drawable2 = this.mStatusBarBackground;
            boolean bl = this.getVisibility() == 0;
            drawable2.setVisible(bl, false);
            this.mStatusBarBackground.setCallback((Drawable.Callback)this);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setStatusBarBackgroundColor(int n) {
        this.setStatusBarBackground((Drawable)new ColorDrawable(n));
    }

    public void setStatusBarBackgroundResource(int n) {
        Drawable drawable2 = n != 0 ? ContextCompat.getDrawable(this.getContext(), n) : null;
        this.setStatusBarBackground(drawable2);
    }

    public void setVisibility(int n) {
        super.setVisibility(n);
        boolean bl = n == 0;
        Drawable drawable2 = this.mStatusBarBackground;
        if (drawable2 == null) return;
        if (drawable2.isVisible() == bl) return;
        this.mStatusBarBackground.setVisible(bl, false);
    }

    final WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
        if (ObjectsCompat.equals(this.mLastInsets, windowInsetsCompat)) return windowInsetsCompat2;
        this.mLastInsets = windowInsetsCompat;
        boolean bl = true;
        boolean bl2 = windowInsetsCompat != null && windowInsetsCompat.getSystemWindowInsetTop() > 0;
        this.mDrawStatusBarBackground = bl2;
        bl2 = !bl2 && this.getBackground() == null ? bl : false;
        this.setWillNotDraw(bl2);
        windowInsetsCompat2 = this.dispatchApplyWindowInsetsToBehaviors(windowInsetsCompat);
        this.requestLayout();
        return windowInsetsCompat2;
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        if (super.verifyDrawable(drawable2)) return true;
        if (drawable2 == this.mStatusBarBackground) return true;
        return false;
    }

    public static interface AttachedBehavior {
        public Behavior getBehavior();
    }

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public static Object getTag(View view) {
            return ((LayoutParams)view.getLayoutParams()).mBehaviorTag;
        }

        public static void setTag(View view, Object object) {
            ((LayoutParams)view.getLayoutParams()).mBehaviorTag = object;
        }

        public boolean blocksInteractionBelow(CoordinatorLayout coordinatorLayout, V v) {
            if (!(this.getScrimOpacity(coordinatorLayout, v) > 0.0f)) return false;
            return true;
        }

        public boolean getInsetDodgeRect(CoordinatorLayout coordinatorLayout, V v, Rect rect) {
            return false;
        }

        public int getScrimColor(CoordinatorLayout coordinatorLayout, V v) {
            return -16777216;
        }

        public float getScrimOpacity(CoordinatorLayout coordinatorLayout, V v) {
            return 0.0f;
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V v, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void onAttachedToLayoutParams(LayoutParams layoutParams) {
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public void onDetachedFromLayoutParams() {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3, int n4) {
            return false;
        }

        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, boolean bl) {
            return false;
        }

        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
            return false;
        }

        @Deprecated
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int[] arrn) {
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int[] arrn, int n3) {
            if (n3 != 0) return;
            this.onNestedPreScroll(coordinatorLayout, v, view, n, n2, arrn);
        }

        @Deprecated
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int n3, int n4) {
        }

        @Deprecated
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int n3, int n4, int n5) {
            if (n5 != 0) return;
            this.onNestedScroll(coordinatorLayout, v, view, n, n2, n3, n4);
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n, int n2, int n3, int n4, int n5, int[] arrn) {
            arrn[0] = arrn[0] + n3;
            arrn[1] = arrn[1] + n4;
            this.onNestedScroll(coordinatorLayout, v, view, n, n2, n3, n4, n5);
        }

        @Deprecated
        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n) {
        }

        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n, int n2) {
            if (n2 != 0) return;
            this.onNestedScrollAccepted(coordinatorLayout, v, view, view2, n);
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean bl) {
            return false;
        }

        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        @Deprecated
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n) {
            return false;
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int n, int n2) {
            if (n2 != 0) return false;
            return this.onStartNestedScroll(coordinatorLayout, v, view, view2, n);
        }

        @Deprecated
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int n) {
            if (n != 0) return;
            this.onStopNestedScroll(coordinatorLayout, v, view);
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }
    }

    @Deprecated
    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface DefaultBehavior {
        public Class<? extends Behavior> value();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DispatchChangeEvent {
    }

    private class HierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener {
        HierarchyChangeListener() {
        }

        public void onChildViewAdded(View view, View view2) {
            if (CoordinatorLayout.this.mOnHierarchyChangeListener == null) return;
            CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(view, view2);
        }

        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.onChildViewsChanged(2);
            if (CoordinatorLayout.this.mOnHierarchyChangeListener == null) return;
            CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2);
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int anchorGravity = 0;
        public int dodgeInsetEdges = 0;
        public int gravity = 0;
        public int insetEdge = 0;
        public int keyline = -1;
        View mAnchorDirectChild;
        int mAnchorId = -1;
        View mAnchorView;
        Behavior mBehavior;
        boolean mBehaviorResolved = false;
        Object mBehaviorTag;
        private boolean mDidAcceptNestedScrollNonTouch;
        private boolean mDidAcceptNestedScrollTouch;
        private boolean mDidBlockInteraction;
        private boolean mDidChangeAfterNestedScroll;
        int mInsetOffsetX;
        int mInsetOffsetY;
        final Rect mLastChildRect = new Rect();

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            boolean bl;
            TypedArray typedArray = object.obtainStyledAttributes(attributeSet, R.styleable.CoordinatorLayout_Layout);
            this.gravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.mAnchorId = typedArray.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
            this.anchorGravity = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.keyline = typedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
            this.insetEdge = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.dodgeInsetEdges = typedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            this.mBehaviorResolved = bl = typedArray.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
            if (bl) {
                this.mBehavior = CoordinatorLayout.parseBehavior((Context)object, attributeSet, typedArray.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior));
            }
            typedArray.recycle();
            object = this.mBehavior;
            if (object == null) return;
            ((Behavior)object).onAttachedToLayoutParams(this);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
        }

        private void resolveAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            View view2;
            this.mAnchorView = view2 = coordinatorLayout.findViewById(this.mAnchorId);
            if (view2 != null) {
                if (view2 == coordinatorLayout) {
                    if (!coordinatorLayout.isInEditMode()) throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                    this.mAnchorDirectChild = null;
                    this.mAnchorView = null;
                    return;
                }
            } else {
                if (coordinatorLayout.isInEditMode()) {
                    this.mAnchorDirectChild = null;
                    this.mAnchorView = null;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not find CoordinatorLayout descendant view with id ");
                stringBuilder.append(coordinatorLayout.getResources().getResourceName(this.mAnchorId));
                stringBuilder.append(" to anchor view ");
                stringBuilder.append((Object)view);
                throw new IllegalStateException(stringBuilder.toString());
            }
            for (ViewParent viewParent = view2.getParent(); viewParent != coordinatorLayout && viewParent != null; viewParent = viewParent.getParent()) {
                if (viewParent == view) {
                    if (!coordinatorLayout.isInEditMode()) throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                    this.mAnchorDirectChild = null;
                    this.mAnchorView = null;
                    return;
                }
                if (!(viewParent instanceof View)) continue;
                view2 = (View)viewParent;
            }
            this.mAnchorDirectChild = view2;
        }

        private boolean shouldDodge(View view, int n) {
            int n2 = GravityCompat.getAbsoluteGravity(((LayoutParams)view.getLayoutParams()).insetEdge, n);
            if (n2 == 0) return false;
            if ((GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, n) & n2) != n2) return false;
            return true;
        }

        private boolean verifyAnchorView(View view, CoordinatorLayout coordinatorLayout) {
            if (this.mAnchorView.getId() != this.mAnchorId) {
                return false;
            }
            View view2 = this.mAnchorView;
            ViewParent viewParent = view2.getParent();
            do {
                if (viewParent == coordinatorLayout) {
                    this.mAnchorDirectChild = view2;
                    return true;
                }
                if (viewParent == null || viewParent == view) break;
                if (viewParent instanceof View) {
                    view2 = (View)viewParent;
                }
                viewParent = viewParent.getParent();
            } while (true);
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return false;
        }

        boolean checkAnchorChanged() {
            if (this.mAnchorView != null) return false;
            if (this.mAnchorId == -1) return false;
            return true;
        }

        boolean dependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
            if (view2 == this.mAnchorDirectChild) return true;
            if (this.shouldDodge(view2, ViewCompat.getLayoutDirection((View)coordinatorLayout))) return true;
            Behavior behavior = this.mBehavior;
            if (behavior == null) return false;
            if (!behavior.layoutDependsOn(coordinatorLayout, view, view2)) return false;
            return true;
        }

        boolean didBlockInteraction() {
            if (this.mBehavior != null) return this.mDidBlockInteraction;
            this.mDidBlockInteraction = false;
            return this.mDidBlockInteraction;
        }

        View findAnchorView(CoordinatorLayout coordinatorLayout, View view) {
            if (this.mAnchorId == -1) {
                this.mAnchorDirectChild = null;
                this.mAnchorView = null;
                return null;
            }
            if (this.mAnchorView != null) {
                if (this.verifyAnchorView(view, coordinatorLayout)) return this.mAnchorView;
            }
            this.resolveAnchorView(view, coordinatorLayout);
            return this.mAnchorView;
        }

        public int getAnchorId() {
            return this.mAnchorId;
        }

        public Behavior getBehavior() {
            return this.mBehavior;
        }

        boolean getChangedAfterNestedScroll() {
            return this.mDidChangeAfterNestedScroll;
        }

        Rect getLastChildRect() {
            return this.mLastChildRect;
        }

        void invalidateAnchor() {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
        }

        boolean isBlockingInteractionBelow(CoordinatorLayout coordinatorLayout, View view) {
            boolean bl = this.mDidBlockInteraction;
            if (bl) {
                return true;
            }
            Behavior behavior = this.mBehavior;
            boolean bl2 = behavior != null ? behavior.blocksInteractionBelow(coordinatorLayout, view) : false;
            this.mDidBlockInteraction = bl2 |= bl;
            return bl2;
        }

        boolean isNestedScrollAccepted(int n) {
            if (n == 0) return this.mDidAcceptNestedScrollTouch;
            if (n == 1) return this.mDidAcceptNestedScrollNonTouch;
            return false;
        }

        void resetChangedAfterNestedScroll() {
            this.mDidChangeAfterNestedScroll = false;
        }

        void resetNestedScroll(int n) {
            this.setNestedScrollAccepted(n, false);
        }

        void resetTouchBehaviorTracking() {
            this.mDidBlockInteraction = false;
        }

        public void setAnchorId(int n) {
            this.invalidateAnchor();
            this.mAnchorId = n;
        }

        public void setBehavior(Behavior behavior) {
            Behavior behavior2 = this.mBehavior;
            if (behavior2 == behavior) return;
            if (behavior2 != null) {
                behavior2.onDetachedFromLayoutParams();
            }
            this.mBehavior = behavior;
            this.mBehaviorTag = null;
            this.mBehaviorResolved = true;
            if (behavior == null) return;
            behavior.onAttachedToLayoutParams(this);
        }

        void setChangedAfterNestedScroll(boolean bl) {
            this.mDidChangeAfterNestedScroll = bl;
        }

        void setLastChildRect(Rect rect) {
            this.mLastChildRect.set(rect);
        }

        void setNestedScrollAccepted(int n, boolean bl) {
            if (n == 0) {
                this.mDidAcceptNestedScrollTouch = bl;
                return;
            }
            if (n != 1) {
                return;
            }
            this.mDidAcceptNestedScrollNonTouch = bl;
        }
    }

    class OnPreDrawListener
    implements ViewTreeObserver.OnPreDrawListener {
        OnPreDrawListener() {
        }

        public boolean onPreDraw() {
            CoordinatorLayout.this.onChildViewsChanged(0);
            return true;
        }
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
        SparseArray<Parcelable> behaviorStates;

        public SavedState(Parcel arrparcelable, ClassLoader classLoader) {
            super((Parcel)arrparcelable, classLoader);
            int n = arrparcelable.readInt();
            int[] arrn = new int[n];
            arrparcelable.readIntArray(arrn);
            arrparcelable = arrparcelable.readParcelableArray(classLoader);
            this.behaviorStates = new SparseArray(n);
            int n2 = 0;
            while (n2 < n) {
                this.behaviorStates.append(arrn[n2], (Object)arrparcelable[n2]);
                ++n2;
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            Parcelable[] arrparcelable = this.behaviorStates;
            int n2 = 0;
            int n3 = arrparcelable != null ? arrparcelable.size() : 0;
            parcel.writeInt(n3);
            int[] arrn = new int[n3];
            arrparcelable = new Parcelable[n3];
            do {
                if (n2 >= n3) {
                    parcel.writeIntArray(arrn);
                    parcel.writeParcelableArray(arrparcelable, n);
                    return;
                }
                arrn[n2] = this.behaviorStates.keyAt(n2);
                arrparcelable[n2] = (Parcelable)this.behaviorStates.valueAt(n2);
                ++n2;
            } while (true);
        }

    }

    static class ViewElevationComparator
    implements Comparator<View> {
        ViewElevationComparator() {
        }

        @Override
        public int compare(View view, View view2) {
            float f;
            float f2 = ViewCompat.getZ(view);
            if (f2 > (f = ViewCompat.getZ(view2))) {
                return -1;
            }
            if (!(f2 < f)) return 0;
            return 1;
        }
    }

}

