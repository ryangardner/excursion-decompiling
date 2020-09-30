/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.LayoutTransition
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.Observable
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.FocusFinder
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.OverScroller
 */
package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.core.os.TraceCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.R;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.FastScroller;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import androidx.recyclerview.widget.ViewBoundsCheck;
import androidx.recyclerview.widget.ViewInfoStore;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView
extends ViewGroup
implements ScrollingView,
NestedScrollingChild2,
NestedScrollingChild3 {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
    static final boolean ALLOW_THREAD_GAP_WORK;
    static final boolean DEBUG = false;
    static final int DEFAULT_ORIENTATION = 1;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION;
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
    static final long FOREVER_NS = Long.MAX_VALUE;
    public static final int HORIZONTAL = 0;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
    static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS;
    public static final long NO_ID = -1L;
    public static final int NO_POSITION = -1;
    static final boolean POST_UPDATES_ON_ANIMATION;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    static final String TRACE_PREFETCH_TAG = "RV Prefetch";
    static final String TRACE_SCROLL_TAG = "RV Scroll";
    public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
    static final boolean VERBOSE_TRACING = false;
    public static final int VERTICAL = 1;
    static final Interpolator sQuinticInterpolator;
    RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    Adapter mAdapter;
    AdapterHelper mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private EdgeEffect mBottomGlow;
    private ChildDrawingOrderCallback mChildDrawingOrderCallback;
    ChildHelper mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout = false;
    boolean mDispatchItemsChangedEvent = false;
    private int mDispatchScrollCounter = 0;
    private int mEatenAccessibilityChangeFlags;
    private EdgeEffectFactory mEdgeEffectFactory = new EdgeEffectFactory();
    boolean mEnableFastScroller;
    boolean mFirstLayoutComplete;
    GapWorker mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mInterceptRequestLayoutDepth = 0;
    private OnItemTouchListener mInterceptingOnItemTouchListener;
    boolean mIsAttached;
    ItemAnimator mItemAnimator = new DefaultItemAnimator();
    private ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<ItemDecoration> mItemDecorations = new ArrayList();
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    LayoutManager mLayout;
    private int mLayoutOrScrollCounter = 0;
    boolean mLayoutSuppressed;
    boolean mLayoutWasDefered;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();
    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
    private OnFlingListener mOnFlingListener;
    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners = new ArrayList();
    final List<ViewHolder> mPendingAccessibilityImportanceChange;
    private SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout;
    final Recycler mRecycler = new Recycler();
    RecyclerListener mRecyclerListener;
    final int[] mReusableIntPair;
    private EdgeEffect mRightGlow;
    private float mScaledHorizontalScrollFactor = Float.MIN_VALUE;
    private float mScaledVerticalScrollFactor = Float.MIN_VALUE;
    private OnScrollListener mScrollListener;
    private List<OnScrollListener> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId = -1;
    private int mScrollState = 0;
    private NestedScrollingChildHelper mScrollingChildHelper;
    final State mState;
    final Rect mTempRect = new Rect();
    private final Rect mTempRect2 = new Rect();
    final RectF mTempRectF = new RectF();
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    final Runnable mUpdateChildViewsRunnable = new Runnable(){

        @Override
        public void run() {
            if (!RecyclerView.this.mFirstLayoutComplete) return;
            if (RecyclerView.this.isLayoutRequested()) {
                return;
            }
            if (!RecyclerView.this.mIsAttached) {
                RecyclerView.this.requestLayout();
                return;
            }
            if (RecyclerView.this.mLayoutSuppressed) {
                RecyclerView.this.mLayoutWasDefered = true;
                return;
            }
            RecyclerView.this.consumePendingUpdateOperations();
        }
    };
    private VelocityTracker mVelocityTracker;
    final ViewFlinger mViewFlinger;
    private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
    final ViewInfoStore mViewInfoStore = new ViewInfoStore();

    static {
        NESTED_SCROLLING_ATTRS = new int[]{16843830};
        boolean bl = Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20;
        FORCE_INVALIDATE_DISPLAY_LIST = bl;
        bl = Build.VERSION.SDK_INT >= 23;
        ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bl;
        bl = Build.VERSION.SDK_INT >= 16;
        POST_UPDATES_ON_ANIMATION = bl;
        bl = Build.VERSION.SDK_INT >= 21;
        ALLOW_THREAD_GAP_WORK = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        FORCE_ABS_FOCUS_SEARCH_DIRECTION = bl;
        bl = Build.VERSION.SDK_INT <= 15;
        IGNORE_DETACHED_FOCUSED_CHILD = bl;
        LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
        sQuinticInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * f * f * f + 1.0f;
            }
        };
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.recyclerViewStyle);
    }

    public RecyclerView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        boolean bl = true;
        this.mPreserveFocusAfterLayout = true;
        this.mViewFlinger = new ViewFlinger();
        GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = ALLOW_THREAD_GAP_WORK ? new GapWorker.LayoutPrefetchRegistryImpl() : null;
        this.mPrefetchRegistry = layoutPrefetchRegistryImpl;
        this.mState = new State();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mNestedOffsets = new int[2];
        this.mReusableIntPair = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList<ViewHolder>();
        this.mItemAnimatorRunner = new Runnable(){

            @Override
            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.runPendingAnimations();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback(){

            @Override
            public void processAppeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processDisappeared(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                RecyclerView.this.mRecycler.unscrapView(viewHolder);
                RecyclerView.this.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2);
            }

            @Override
            public void processPersistent(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
                viewHolder.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (!RecyclerView.this.mItemAnimator.animateChange(viewHolder, viewHolder, itemHolderInfo, itemHolderInfo2)) return;
                    RecyclerView.this.postAnimationRunner();
                    return;
                }
                if (!RecyclerView.this.mItemAnimator.animatePersistence(viewHolder, itemHolderInfo, itemHolderInfo2)) return;
                RecyclerView.this.postAnimationRunner();
            }

            @Override
            public void unused(ViewHolder viewHolder) {
                RecyclerView.this.mLayout.removeAndRecycleView(viewHolder.itemView, RecyclerView.this.mRecycler);
            }
        };
        this.setScrollContainer(true);
        this.setFocusableInTouchMode(true);
        layoutPrefetchRegistryImpl = ViewConfiguration.get((Context)context);
        this.mTouchSlop = layoutPrefetchRegistryImpl.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor((ViewConfiguration)layoutPrefetchRegistryImpl, context);
        this.mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor((ViewConfiguration)layoutPrefetchRegistryImpl, context);
        this.mMinFlingVelocity = layoutPrefetchRegistryImpl.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = layoutPrefetchRegistryImpl.getScaledMaximumFlingVelocity();
        boolean bl2 = this.getOverScrollMode() == 2;
        this.setWillNotDraw(bl2);
        this.mItemAnimator.setListener(this.mItemAnimatorListener);
        this.initAdapterManager();
        this.initChildrenHelper();
        this.initAutofill();
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        layoutPrefetchRegistryImpl = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, 0);
        if (Build.VERSION.SDK_INT >= 29) {
            this.saveAttributeDataForStyleable(context, R.styleable.RecyclerView, attributeSet, (TypedArray)layoutPrefetchRegistryImpl, n, 0);
        }
        String string2 = layoutPrefetchRegistryImpl.getString(R.styleable.RecyclerView_layoutManager);
        if (layoutPrefetchRegistryImpl.getInt(R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
            this.setDescendantFocusability(262144);
        }
        this.mClipToPadding = layoutPrefetchRegistryImpl.getBoolean(R.styleable.RecyclerView_android_clipToPadding, true);
        this.mEnableFastScroller = bl2 = layoutPrefetchRegistryImpl.getBoolean(R.styleable.RecyclerView_fastScrollEnabled, false);
        if (bl2) {
            this.initFastScroller((StateListDrawable)layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable)layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), layoutPrefetchRegistryImpl.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
        }
        layoutPrefetchRegistryImpl.recycle();
        this.createLayoutManager(context, string2, attributeSet, n, 0);
        bl2 = bl;
        if (Build.VERSION.SDK_INT >= 21) {
            layoutPrefetchRegistryImpl = context.obtainStyledAttributes(attributeSet, NESTED_SCROLLING_ATTRS, n, 0);
            if (Build.VERSION.SDK_INT >= 29) {
                this.saveAttributeDataForStyleable(context, NESTED_SCROLLING_ATTRS, attributeSet, (TypedArray)layoutPrefetchRegistryImpl, n, 0);
            }
            bl2 = layoutPrefetchRegistryImpl.getBoolean(0, true);
            layoutPrefetchRegistryImpl.recycle();
        }
        this.setNestedScrollingEnabled(bl2);
    }

    private void addAnimatingView(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        boolean bl = view.getParent() == this;
        this.mRecycler.unscrapView(this.getChildViewHolder(view));
        if (viewHolder.isTmpDetached()) {
            this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
            return;
        }
        if (!bl) {
            this.mChildHelper.addView(view, true);
            return;
        }
        this.mChildHelper.hide(view);
    }

    private void animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2, boolean bl, boolean bl2) {
        viewHolder.setIsRecyclable(false);
        if (bl) {
            this.addAnimatingView(viewHolder);
        }
        if (viewHolder != viewHolder2) {
            if (bl2) {
                this.addAnimatingView(viewHolder2);
            }
            viewHolder.mShadowedHolder = viewHolder2;
            this.addAnimatingView(viewHolder);
            this.mRecycler.unscrapView(viewHolder);
            viewHolder2.setIsRecyclable(false);
            viewHolder2.mShadowingHolder = viewHolder;
        }
        if (!this.mItemAnimator.animateChange(viewHolder, viewHolder2, itemHolderInfo, itemHolderInfo2)) return;
        this.postAnimationRunner();
    }

    private void cancelScroll() {
        this.resetScroll();
        this.setScrollState(0);
    }

    static void clearNestedRecyclerViewIfNotNested(ViewHolder viewHolder) {
        if (viewHolder.mNestedRecyclerView == null) return;
        View view = (View)viewHolder.mNestedRecyclerView.get();
        do {
            if (view == null) {
                viewHolder.mNestedRecyclerView = null;
                return;
            }
            if (view == viewHolder.itemView) {
                return;
            }
            if ((view = view.getParent()) instanceof View) continue;
            view = null;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    private void createLayoutManager(Context var1_1, String var2_7, AttributeSet var3_11, int var4_12, int var5_13) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean didChildRangeChange(int n, int n2) {
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        int[] arrn = this.mMinMaxLayoutPositions;
        boolean bl = false;
        if (arrn[0] != n) return true;
        if (arrn[1] == n2) return bl;
        return true;
    }

    private void dispatchContentChangedIfNecessary() {
        int n = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (n == 0) return;
        if (!this.isAccessibilityEnabled()) return;
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
        accessibilityEvent.setEventType(2048);
        AccessibilityEventCompat.setContentChangeTypes(accessibilityEvent, n);
        this.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    private void dispatchLayoutStep1() {
        int n;
        Object object;
        int n2;
        Object object2 = this.mState;
        boolean bl = true;
        ((State)object2).assertLayoutStep(1);
        this.fillRemainingScrollValues(this.mState);
        this.mState.mIsMeasuring = false;
        this.startInterceptRequestLayout();
        this.mViewInfoStore.clear();
        this.onEnterLayoutOrScroll();
        this.processAdapterUpdatesAndSetAnimationFlags();
        this.saveFocusInfo();
        object2 = this.mState;
        if (!((State)object2).mRunSimpleAnimations || !this.mItemsChanged) {
            bl = false;
        }
        ((State)object2).mTrackOldChangeHolders = bl;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        object2 = this.mState;
        ((State)object2).mInPreLayout = ((State)object2).mRunPredictiveAnimations;
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.mRunSimpleAnimations) {
            n2 = this.mChildHelper.getChildCount();
            for (n = 0; n < n2; ++n) {
                object2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n));
                if (((ViewHolder)object2).shouldIgnore() || ((ViewHolder)object2).isInvalid() && !this.mAdapter.hasStableIds()) continue;
                object = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object2, ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object2), ((ViewHolder)object2).getUnmodifiedPayloads());
                this.mViewInfoStore.addToPreLayout((ViewHolder)object2, (ItemAnimator.ItemHolderInfo)object);
                if (!this.mState.mTrackOldChangeHolders || !((ViewHolder)object2).isUpdated() || ((ViewHolder)object2).isRemoved() || ((ViewHolder)object2).shouldIgnore() || ((ViewHolder)object2).isInvalid()) continue;
                long l = this.getChangedHolderKey((ViewHolder)object2);
                this.mViewInfoStore.addToOldChangeHolders(l, (ViewHolder)object2);
            }
        }
        if (!this.mState.mRunPredictiveAnimations) {
            this.clearOldPositions();
        } else {
            this.saveOldPositions();
            bl = this.mState.mStructureChanged;
            this.mState.mStructureChanged = false;
            this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
            this.mState.mStructureChanged = bl;
            for (n = 0; n < this.mChildHelper.getChildCount(); ++n) {
                object = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n));
                if (((ViewHolder)object).shouldIgnore() || this.mViewInfoStore.isInPreLayout((ViewHolder)object)) continue;
                int n3 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)object);
                bl = ((ViewHolder)object).hasAnyOfTheFlags(8192);
                n2 = n3;
                if (!bl) {
                    n2 = n3 | 4096;
                }
                object2 = this.mItemAnimator.recordPreLayoutInformation(this.mState, (ViewHolder)object, n2, ((ViewHolder)object).getUnmodifiedPayloads());
                if (bl) {
                    this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)object, (ItemAnimator.ItemHolderInfo)object2);
                    continue;
                }
                this.mViewInfoStore.addToAppearedInPreLayoutHolders((ViewHolder)object, (ItemAnimator.ItemHolderInfo)object2);
            }
            this.clearOldPositions();
        }
        this.onExitLayoutOrScroll();
        this.stopInterceptRequestLayout(false);
        this.mState.mLayoutStep = 2;
    }

    private void dispatchLayoutStep2() {
        this.startInterceptRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.assertLayoutStep(6);
        this.mAdapterHelper.consumeUpdatesInOnePass();
        this.mState.mItemCount = this.mAdapter.getItemCount();
        this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
        this.mState.mInPreLayout = false;
        this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
        this.mState.mStructureChanged = false;
        this.mPendingSavedState = null;
        State state = this.mState;
        boolean bl = state.mRunSimpleAnimations && this.mItemAnimator != null;
        state.mRunSimpleAnimations = bl;
        this.mState.mLayoutStep = 4;
        this.onExitLayoutOrScroll();
        this.stopInterceptRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        Object object;
        this.mState.assertLayoutStep(4);
        this.startInterceptRequestLayout();
        this.onEnterLayoutOrScroll();
        this.mState.mLayoutStep = 1;
        if (this.mState.mRunSimpleAnimations) {
            for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; --i) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(i));
                if (viewHolder.shouldIgnore()) continue;
                long l = this.getChangedHolderKey(viewHolder);
                ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, viewHolder);
                ViewHolder viewHolder2 = this.mViewInfoStore.getFromOldChangeHolders(l);
                if (viewHolder2 != null && !viewHolder2.shouldIgnore()) {
                    boolean bl = this.mViewInfoStore.isDisappearing(viewHolder2);
                    boolean bl2 = this.mViewInfoStore.isDisappearing(viewHolder);
                    if (bl && viewHolder2 == viewHolder) {
                        this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                        continue;
                    }
                    object = this.mViewInfoStore.popFromPreLayout(viewHolder2);
                    this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
                    itemHolderInfo = this.mViewInfoStore.popFromPostLayout(viewHolder);
                    if (object == null) {
                        this.handleMissingPreInfoForChangeError(l, viewHolder, viewHolder2);
                        continue;
                    }
                    this.animateChange(viewHolder2, viewHolder, (ItemAnimator.ItemHolderInfo)object, itemHolderInfo, bl, bl2);
                    continue;
                }
                this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
            }
            this.mViewInfoStore.process(this.mViewInfoProcessCallback);
        }
        this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        object = this.mState;
        object.mPreviousLayoutItemCount = object.mItemCount;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        this.mState.mRunSimpleAnimations = false;
        this.mState.mRunPredictiveAnimations = false;
        this.mLayout.mRequestedSimpleAnimations = false;
        if (this.mRecycler.mChangedScrap != null) {
            this.mRecycler.mChangedScrap.clear();
        }
        if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
            this.mLayout.mPrefetchMaxCountObserved = 0;
            this.mLayout.mPrefetchMaxObservedInInitialPrefetch = false;
            this.mRecycler.updateViewCacheSize();
        }
        this.mLayout.onLayoutCompleted(this.mState);
        this.onExitLayoutOrScroll();
        this.stopInterceptRequestLayout(false);
        this.mViewInfoStore.clear();
        object = this.mMinMaxLayoutPositions;
        if (this.didChildRangeChange(object[0], object[1])) {
            this.dispatchOnScrolled(0, 0);
        }
        this.recoverFocusFromState();
        this.resetFocusInfo();
    }

    private boolean dispatchToOnItemTouchListeners(MotionEvent motionEvent) {
        OnItemTouchListener onItemTouchListener = this.mInterceptingOnItemTouchListener;
        if (onItemTouchListener == null) {
            if (motionEvent.getAction() != 0) return this.findInterceptingOnItemTouchListener(motionEvent);
            return false;
        }
        onItemTouchListener.onTouchEvent(this, motionEvent);
        int n = motionEvent.getAction();
        if (n != 3) {
            if (n != 1) return true;
        }
        this.mInterceptingOnItemTouchListener = null;
        return true;
    }

    private boolean findInterceptingOnItemTouchListener(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        int n2 = this.mOnItemTouchListeners.size();
        int n3 = 0;
        while (n3 < n2) {
            OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(n3);
            if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent) && n != 3) {
                this.mInterceptingOnItemTouchListener = onItemTouchListener;
                return true;
            }
            ++n3;
        }
        return false;
    }

    private void findMinMaxChildLayoutPositions(int[] arrn) {
        int n = this.mChildHelper.getChildCount();
        if (n == 0) {
            arrn[0] = -1;
            arrn[1] = -1;
            return;
        }
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MIN_VALUE;
        int n4 = 0;
        do {
            int n5;
            if (n4 >= n) {
                arrn[0] = n2;
                arrn[1] = n3;
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n4));
            if (viewHolder.shouldIgnore()) {
                n5 = n3;
            } else {
                int n6 = viewHolder.getLayoutPosition();
                int n7 = n2;
                if (n6 < n2) {
                    n7 = n6;
                }
                n2 = n7;
                n5 = n3;
                if (n6 > n3) {
                    n5 = n6;
                    n2 = n7;
                }
            }
            ++n4;
            n3 = n5;
        } while (true);
    }

    static RecyclerView findNestedRecyclerView(View object) {
        if (!(object instanceof ViewGroup)) {
            return null;
        }
        if (object instanceof RecyclerView) {
            return (RecyclerView)object;
        }
        ViewGroup viewGroup = (ViewGroup)object;
        int n = viewGroup.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            object = RecyclerView.findNestedRecyclerView(viewGroup.getChildAt(n2));
            if (object != null) {
                return object;
            }
            ++n2;
        }
        return null;
    }

    private View findNextViewToFocus() {
        ViewHolder viewHolder;
        int n = this.mState.mFocusedItemPosition != -1 ? this.mState.mFocusedItemPosition : 0;
        int n2 = this.mState.getItemCount();
        for (int i = n; i < n2 && (viewHolder = this.findViewHolderForAdapterPosition(i)) != null; ++i) {
            if (!viewHolder.itemView.hasFocusable()) continue;
            return viewHolder.itemView;
        }
        n = Math.min(n2, n) - 1;
        while (n >= 0) {
            viewHolder = this.findViewHolderForAdapterPosition(n);
            if (viewHolder == null) {
                return null;
            }
            if (viewHolder.itemView.hasFocusable()) {
                return viewHolder.itemView;
            }
            --n;
        }
        return null;
    }

    static ViewHolder getChildViewHolderInt(View view) {
        if (view != null) return ((LayoutParams)view.getLayoutParams()).mViewHolder;
        return null;
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect2 = layoutParams.mDecorInsets;
        rect.set(view.getLeft() - rect2.left - layoutParams.leftMargin, view.getTop() - rect2.top - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    private int getDeepestFocusedViewWithId(View view) {
        int n = view.getId();
        while (!view.isFocused()) {
            View view2;
            if (!(view instanceof ViewGroup)) return n;
            if (!view.hasFocus()) return n;
            view = view2 = ((ViewGroup)view).getFocusedChild();
            if (view2.getId() == -1) continue;
            n = view2.getId();
            view = view2;
        }
        return n;
    }

    private String getFullClassName(Context object, String string2) {
        if (string2.charAt(0) == '.') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.getPackageName());
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
        if (string2.contains(".")) {
            return string2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(RecyclerView.class.getPackage().getName());
        ((StringBuilder)object).append('.');
        ((StringBuilder)object).append(string2);
        return ((StringBuilder)object).toString();
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.mScrollingChildHelper != null) return this.mScrollingChildHelper;
        this.mScrollingChildHelper = new NestedScrollingChildHelper((View)this);
        return this.mScrollingChildHelper;
    }

    private void handleMissingPreInfoForChangeError(long l, ViewHolder viewHolder, ViewHolder object) {
        int n = this.mChildHelper.getChildCount();
        int n2 = 0;
        do {
            Object object2;
            if (n2 >= n) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" cannot be found but it is necessary for ");
                ((StringBuilder)object2).append(viewHolder);
                ((StringBuilder)object2).append(this.exceptionLabel());
                Log.e((String)TAG, (String)((StringBuilder)object2).toString());
                return;
            }
            object2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
            if (object2 != viewHolder && this.getChangedHolderKey((ViewHolder)object2) == l) {
                object = this.mAdapter;
                if (object != null && ((Adapter)object).hasStableIds()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
                    ((StringBuilder)object).append(object2);
                    ((StringBuilder)object).append(" \n View Holder 2:");
                    ((StringBuilder)object).append(viewHolder);
                    ((StringBuilder)object).append(this.exceptionLabel());
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(" \n View Holder 2:");
                ((StringBuilder)object).append(viewHolder);
                ((StringBuilder)object).append(this.exceptionLabel());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            ++n2;
        } while (true);
    }

    private boolean hasUpdatedView() {
        int n = this.mChildHelper.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getChildAt(n2));
            if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.isUpdated()) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void initAutofill() {
        if (ViewCompat.getImportantForAutofill((View)this) != 0) return;
        ViewCompat.setImportantForAutofill((View)this, 8);
    }

    private void initChildrenHelper() {
        this.mChildHelper = new ChildHelper(new ChildHelper.Callback(){

            @Override
            public void addView(View view, int n) {
                RecyclerView.this.addView(view, n);
                RecyclerView.this.dispatchChildAttached(view);
            }

            @Override
            public void attachViewToParent(View object, int n, ViewGroup.LayoutParams layoutParams) {
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
                if (viewHolder != null) {
                    if (!viewHolder.isTmpDetached() && !viewHolder.shouldIgnore()) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Called attach on a child which is not detached: ");
                        ((StringBuilder)object).append(viewHolder);
                        ((StringBuilder)object).append(RecyclerView.this.exceptionLabel());
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    viewHolder.clearTmpDetachFlag();
                }
                RecyclerView.this.attachViewToParent((View)object, n, layoutParams);
            }

            @Override
            public void detachViewFromParent(int n) {
                ViewHolder viewHolder;
                Object object = this.getChildAt(n);
                if (object != null && (viewHolder = RecyclerView.getChildViewHolderInt((View)object)) != null) {
                    if (viewHolder.isTmpDetached() && !viewHolder.shouldIgnore()) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("called detach on an already detached child ");
                        ((StringBuilder)object).append(viewHolder);
                        ((StringBuilder)object).append(RecyclerView.this.exceptionLabel());
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    viewHolder.addFlags(256);
                }
                RecyclerView.this.detachViewFromParent(n);
            }

            @Override
            public View getChildAt(int n) {
                return RecyclerView.this.getChildAt(n);
            }

            @Override
            public int getChildCount() {
                return RecyclerView.this.getChildCount();
            }

            @Override
            public ViewHolder getChildViewHolder(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            @Override
            public int indexOfChild(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            @Override
            public void onEnteredHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt((View)object)) == null) return;
                ((ViewHolder)object).onEnteredHiddenState(RecyclerView.this);
            }

            @Override
            public void onLeftHiddenState(View object) {
                if ((object = RecyclerView.getChildViewHolderInt((View)object)) == null) return;
                ((ViewHolder)object).onLeftHiddenState(RecyclerView.this);
            }

            @Override
            public void removeAllViews() {
                int n = this.getChildCount();
                int n2 = 0;
                do {
                    if (n2 >= n) {
                        RecyclerView.this.removeAllViews();
                        return;
                    }
                    View view = this.getChildAt(n2);
                    RecyclerView.this.dispatchChildDetached(view);
                    view.clearAnimation();
                    ++n2;
                } while (true);
            }

            @Override
            public void removeViewAt(int n) {
                View view = RecyclerView.this.getChildAt(n);
                if (view != null) {
                    RecyclerView.this.dispatchChildDetached(view);
                    view.clearAnimation();
                }
                RecyclerView.this.removeViewAt(n);
            }
        });
    }

    private boolean isPreferredNextFocus(View object, View view, int n) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = bl5;
        if (view == null) return bl7;
        if (view == this) {
            return bl5;
        }
        if (this.findContainingItemView(view) == null) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (this.findContainingItemView((View)object) == null) {
            return true;
        }
        this.mTempRect.set(0, 0, object.getWidth(), object.getHeight());
        this.mTempRect2.set(0, 0, view.getWidth(), view.getHeight());
        this.offsetDescendantRectToMyCoords((View)object, this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect2);
        int n2 = this.mLayout.getLayoutDirection();
        int n3 = -1;
        int n4 = n2 == 1 ? -1 : 1;
        n2 = (this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right ? 1 : ((this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left ? -1 : 0);
        if ((this.mTempRect.top < this.mTempRect2.top || this.mTempRect.bottom <= this.mTempRect2.top) && this.mTempRect.bottom < this.mTempRect2.bottom) {
            n3 = 1;
        } else if (this.mTempRect.bottom <= this.mTempRect2.bottom && this.mTempRect.top < this.mTempRect2.bottom || this.mTempRect.top <= this.mTempRect2.top) {
            n3 = 0;
        }
        if (n != 1) {
            if (n != 2) {
                if (n == 17) {
                    bl7 = bl3;
                    if (n2 >= 0) return bl7;
                    return true;
                }
                if (n == 33) {
                    bl7 = bl2;
                    if (n3 >= 0) return bl7;
                    return true;
                }
                if (n == 66) {
                    bl7 = bl;
                    if (n2 <= 0) return bl7;
                    return true;
                }
                if (n == 130) {
                    bl7 = bl6;
                    if (n3 <= 0) return bl7;
                    return true;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid direction: ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(this.exceptionLabel());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (n3 > 0) return true;
            bl7 = bl4;
            if (n3 != 0) return bl7;
            bl7 = bl4;
            if (n2 * n4 < 0) return bl7;
            return true;
        }
        if (n3 < 0) return true;
        bl7 = bl5;
        if (n3 != 0) return bl7;
        bl7 = bl5;
        if (n2 * n4 > 0) return bl7;
        return true;
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int n;
        int n2 = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n2) != this.mScrollPointerId) return;
        n2 = n2 == 0 ? 1 : 0;
        this.mScrollPointerId = motionEvent.getPointerId(n2);
        this.mLastTouchX = n = (int)(motionEvent.getX(n2) + 0.5f);
        this.mInitialTouchX = n;
        this.mLastTouchY = n2 = (int)(motionEvent.getY(n2) + 0.5f);
        this.mInitialTouchY = n2;
    }

    private boolean predictiveItemAnimationsEnabled() {
        if (this.mItemAnimator == null) return false;
        if (!this.mLayout.supportsPredictiveItemAnimations()) return false;
        return true;
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.reset();
            if (this.mDispatchItemsChangedEvent) {
                this.mLayout.onItemsChanged(this);
            }
        }
        if (this.predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.preProcess();
        } else {
            this.mAdapterHelper.consumeUpdatesInOnePass();
        }
        boolean bl = this.mItemsAddedOrRemoved;
        boolean bl2 = false;
        boolean bl3 = bl || this.mItemsChanged;
        State state = this.mState;
        bl = !(!this.mFirstLayoutComplete || this.mItemAnimator == null || !this.mDataSetHasChangedAfterLayout && !bl3 && !this.mLayout.mRequestedSimpleAnimations || this.mDataSetHasChangedAfterLayout && !this.mAdapter.hasStableIds());
        state.mRunSimpleAnimations = bl;
        state = this.mState;
        bl = bl2;
        if (state.mRunSimpleAnimations) {
            bl = bl2;
            if (bl3) {
                bl = bl2;
                if (!this.mDataSetHasChangedAfterLayout) {
                    bl = bl2;
                    if (this.predictiveItemAnimationsEnabled()) {
                        bl = true;
                    }
                }
            }
        }
        state.mRunPredictiveAnimations = bl;
    }

    /*
     * Unable to fully structure code
     */
    private void pullGlows(float var1_1, float var2_2, float var3_3, float var4_4) {
        block6 : {
            var5_5 = true;
            if (!(var2_2 < 0.0f)) break block6;
            this.ensureLeftGlow();
            EdgeEffectCompat.onPull(this.mLeftGlow, -var2_2 / (float)this.getWidth(), 1.0f - var3_3 / (float)this.getHeight());
            ** GOTO lbl10
        }
        if (var2_2 > 0.0f) {
            this.ensureRightGlow();
            EdgeEffectCompat.onPull(this.mRightGlow, var2_2 / (float)this.getWidth(), var3_3 / (float)this.getHeight());
lbl10: // 2 sources:
            var6_6 = true;
        } else {
            var6_6 = false;
        }
        if (var4_4 < 0.0f) {
            this.ensureTopGlow();
            EdgeEffectCompat.onPull(this.mTopGlow, -var4_4 / (float)this.getHeight(), var1_1 / (float)this.getWidth());
            var6_6 = var5_5;
        } else if (var4_4 > 0.0f) {
            this.ensureBottomGlow();
            EdgeEffectCompat.onPull(this.mBottomGlow, var4_4 / (float)this.getHeight(), 1.0f - var1_1 / (float)this.getWidth());
            var6_6 = var5_5;
        }
        if (!var6_6 && var2_2 == 0.0f) {
            if (var4_4 == 0.0f) return;
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    private void recoverFocusFromState() {
        Object object;
        if (!this.mPreserveFocusAfterLayout) return;
        if (this.mAdapter == null) return;
        if (!this.hasFocus()) return;
        if (this.getDescendantFocusability() == 393216) return;
        if (this.getDescendantFocusability() == 131072 && this.isFocused()) {
            return;
        }
        if (!this.isFocused()) {
            object = this.getFocusedChild();
            if (IGNORE_DETACHED_FOCUSED_CHILD && (object.getParent() == null || !object.hasFocus())) {
                if (this.mChildHelper.getChildCount() == 0) {
                    this.requestFocus();
                    return;
                }
            } else if (!this.mChildHelper.isHidden((View)object)) {
                return;
            }
        }
        long l = this.mState.mFocusedItemId;
        Object object2 = null;
        object = l != -1L && this.mAdapter.hasStableIds() ? this.findViewHolderForItemId(this.mState.mFocusedItemId) : null;
        if (object != null && !this.mChildHelper.isHidden(object.itemView) && object.itemView.hasFocusable()) {
            object = object.itemView;
        } else {
            object = object2;
            if (this.mChildHelper.getChildCount() > 0) {
                object = this.findNextViewToFocus();
            }
        }
        if (object == null) return;
        object2 = object;
        if ((long)this.mState.mFocusedSubChildId != -1L) {
            View view = object.findViewById(this.mState.mFocusedSubChildId);
            object2 = object;
            if (view != null) {
                object2 = object;
                if (view.isFocusable()) {
                    object2 = view;
                }
            }
        }
        object2.requestFocus();
    }

    private void releaseGlows() {
        boolean bl;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl = this.mLeftGlow.isFinished();
        } else {
            bl = false;
        }
        edgeEffect = this.mTopGlow;
        boolean bl2 = bl;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl2 = bl | this.mTopGlow.isFinished();
        }
        edgeEffect = this.mRightGlow;
        bl = bl2;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl = bl2 | this.mRightGlow.isFinished();
        }
        edgeEffect = this.mBottomGlow;
        bl2 = bl;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl2 = bl | this.mBottomGlow.isFinished();
        }
        if (!bl2) return;
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    private void requestChildOnScreen(View view, View view2) {
        LayoutManager layoutManager;
        Object object = view2 != null ? view2 : view;
        this.mTempRect.set(0, 0, object.getWidth(), object.getHeight());
        object = object.getLayoutParams();
        if (object instanceof LayoutParams) {
            object = (LayoutParams)((Object)object);
            if (!object.mInsetsDirty) {
                object = object.mDecorInsets;
                layoutManager = this.mTempRect;
                ((Rect)layoutManager).left -= object.left;
                layoutManager = this.mTempRect;
                ((Rect)layoutManager).right += object.right;
                layoutManager = this.mTempRect;
                ((Rect)layoutManager).top -= object.top;
                layoutManager = this.mTempRect;
                ((Rect)layoutManager).bottom += object.bottom;
            }
        }
        if (view2 != null) {
            this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
            this.offsetRectIntoDescendantCoords(view, this.mTempRect);
        }
        layoutManager = this.mLayout;
        object = this.mTempRect;
        boolean bl = this.mFirstLayoutComplete;
        boolean bl2 = view2 == null;
        layoutManager.requestChildRectangleOnScreen(this, view, (Rect)object, bl ^ true, bl2);
    }

    private void resetFocusInfo() {
        this.mState.mFocusedItemId = -1L;
        this.mState.mFocusedItemPosition = -1;
        this.mState.mFocusedSubChildId = -1;
    }

    private void resetScroll() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        this.stopNestedScroll(0);
        this.releaseGlows();
    }

    private void saveFocusInfo() {
        boolean bl = this.mPreserveFocusAfterLayout;
        Object object = null;
        Object object2 = bl && this.hasFocus() && this.mAdapter != null ? this.getFocusedChild() : null;
        if ((object2 = object2 == null ? object : this.findContainingViewHolder((View)object2)) == null) {
            this.resetFocusInfo();
            return;
        }
        object = this.mState;
        long l = this.mAdapter.hasStableIds() ? ((ViewHolder)object2).getItemId() : -1L;
        ((State)object).mFocusedItemId = l;
        object = this.mState;
        int n = this.mDataSetHasChangedAfterLayout ? -1 : (((ViewHolder)object2).isRemoved() ? ((ViewHolder)object2).mOldPosition : ((ViewHolder)object2).getAdapterPosition());
        ((State)object).mFocusedItemPosition = n;
        this.mState.mFocusedSubChildId = this.getDeepestFocusedViewWithId(((ViewHolder)object2).itemView);
    }

    private void setAdapterInternal(Adapter object, boolean bl, boolean bl2) {
        Adapter adapter = this.mAdapter;
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!bl || bl2) {
            this.removeAndRecycleViews();
        }
        this.mAdapterHelper.reset();
        adapter = this.mAdapter;
        this.mAdapter = object;
        if (object != null) {
            ((Adapter)object).registerAdapterDataObserver(this.mObserver);
            ((Adapter)object).onAttachedToRecyclerView(this);
        }
        if ((object = this.mLayout) != null) {
            ((LayoutManager)object).onAdapterChanged(adapter, this.mAdapter);
        }
        this.mRecycler.onAdapterChanged(adapter, this.mAdapter, bl);
        this.mState.mStructureChanged = true;
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.stop();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) return;
        layoutManager.stopSmoothScroller();
    }

    void absorbGlows(int n, int n2) {
        if (n < 0) {
            this.ensureLeftGlow();
            if (this.mLeftGlow.isFinished()) {
                this.mLeftGlow.onAbsorb(-n);
            }
        } else if (n > 0) {
            this.ensureRightGlow();
            if (this.mRightGlow.isFinished()) {
                this.mRightGlow.onAbsorb(n);
            }
        }
        if (n2 < 0) {
            this.ensureTopGlow();
            if (this.mTopGlow.isFinished()) {
                this.mTopGlow.onAbsorb(-n2);
            }
        } else if (n2 > 0) {
            this.ensureBottomGlow();
            if (this.mBottomGlow.isFinished()) {
                this.mBottomGlow.onAbsorb(n2);
            }
        }
        if (n == 0) {
            if (n2 == 0) return;
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            if (layoutManager.onAddFocusables(this, arrayList, n, n2)) return;
        }
        super.addFocusables(arrayList, n, n2);
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        this.addItemDecoration(itemDecoration, -1);
    }

    public void addItemDecoration(ItemDecoration itemDecoration, int n) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            this.setWillNotDraw(false);
        }
        if (n < 0) {
            this.mItemDecorations.add(itemDecoration);
        } else {
            this.mItemDecorations.add(n, itemDecoration);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList<OnChildAttachStateChangeListener>();
        }
        this.mOnChildAttachStateListeners.add(onChildAttachStateChangeListener);
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.add(onItemTouchListener);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList<OnScrollListener>();
        }
        this.mScrollListeners.add(onScrollListener);
    }

    void animateAppearance(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        viewHolder.setIsRecyclable(false);
        if (!this.mItemAnimator.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) return;
        this.postAnimationRunner();
    }

    void animateDisappearance(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        this.addAnimatingView(viewHolder);
        viewHolder.setIsRecyclable(false);
        if (!this.mItemAnimator.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) return;
        this.postAnimationRunner();
    }

    void assertInLayoutOrScroll(String charSequence) {
        if (this.isComputingLayout()) return;
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Cannot call this method unless RecyclerView is computing a layout or scrolling");
            ((StringBuilder)charSequence).append(this.exceptionLabel());
            throw new IllegalStateException(((StringBuilder)charSequence).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(this.exceptionLabel());
        throw new IllegalStateException(stringBuilder.toString());
    }

    void assertNotInLayoutOrScroll(String charSequence) {
        if (this.isComputingLayout()) {
            if (charSequence != null) throw new IllegalStateException((String)charSequence);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Cannot call this method while RecyclerView is computing a layout or scrolling");
            ((StringBuilder)charSequence).append(this.exceptionLabel());
            throw new IllegalStateException(((StringBuilder)charSequence).toString());
        }
        if (this.mDispatchScrollCounter <= 0) return;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("");
        ((StringBuilder)charSequence).append(this.exceptionLabel());
        Log.w((String)TAG, (String)"Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", (Throwable)new IllegalStateException(((StringBuilder)charSequence).toString()));
    }

    boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator == null) return true;
        if (itemAnimator.canReuseUpdatedViewHolder(viewHolder, viewHolder.getUnmodifiedPayloads())) return true;
        return false;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams)) return false;
        if (!this.mLayout.checkLayoutParams((LayoutParams)layoutParams)) return false;
        return true;
    }

    void clearOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mRecycler.clearOldPositions();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n2));
            if (!viewHolder.shouldIgnore()) {
                viewHolder.clearOldPosition();
            }
            ++n2;
        } while (true);
    }

    public void clearOnChildAttachStateChangeListeners() {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list == null) return;
        list.clear();
    }

    public void clearOnScrollListeners() {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list == null) return;
        list.clear();
    }

    @Override
    public int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (!layoutManager.canScrollHorizontally()) return n;
        return this.mLayout.computeHorizontalScrollExtent(this.mState);
    }

    @Override
    public int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (!layoutManager.canScrollHorizontally()) return n;
        return this.mLayout.computeHorizontalScrollOffset(this.mState);
    }

    @Override
    public int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (!layoutManager.canScrollHorizontally()) return n;
        return this.mLayout.computeHorizontalScrollRange(this.mState);
    }

    @Override
    public int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (!layoutManager.canScrollVertically()) return n;
        return this.mLayout.computeVerticalScrollExtent(this.mState);
    }

    @Override
    public int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (!layoutManager.canScrollVertically()) return n;
        return this.mLayout.computeVerticalScrollOffset(this.mState);
    }

    @Override
    public int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.mLayout;
        int n = 0;
        if (layoutManager == null) {
            return 0;
        }
        if (!layoutManager.canScrollVertically()) return n;
        return this.mLayout.computeVerticalScrollRange(this.mState);
    }

    void considerReleasingGlowsOnScroll(int n, int n2) {
        boolean bl;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null && !edgeEffect.isFinished() && n > 0) {
            this.mLeftGlow.onRelease();
            bl = this.mLeftGlow.isFinished();
        } else {
            bl = false;
        }
        edgeEffect = this.mRightGlow;
        boolean bl2 = bl;
        if (edgeEffect != null) {
            bl2 = bl;
            if (!edgeEffect.isFinished()) {
                bl2 = bl;
                if (n < 0) {
                    this.mRightGlow.onRelease();
                    bl2 = bl | this.mRightGlow.isFinished();
                }
            }
        }
        edgeEffect = this.mTopGlow;
        bl = bl2;
        if (edgeEffect != null) {
            bl = bl2;
            if (!edgeEffect.isFinished()) {
                bl = bl2;
                if (n2 > 0) {
                    this.mTopGlow.onRelease();
                    bl = bl2 | this.mTopGlow.isFinished();
                }
            }
        }
        edgeEffect = this.mBottomGlow;
        bl2 = bl;
        if (edgeEffect != null) {
            bl2 = bl;
            if (!edgeEffect.isFinished()) {
                bl2 = bl;
                if (n2 < 0) {
                    this.mBottomGlow.onRelease();
                    bl2 = bl | this.mBottomGlow.isFinished();
                }
            }
        }
        if (!bl2) return;
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    void consumePendingUpdateOperations() {
        if (this.mFirstLayoutComplete && !this.mDataSetHasChangedAfterLayout) {
            if (!this.mAdapterHelper.hasPendingUpdates()) {
                return;
            }
            if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
                TraceCompat.beginSection(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                this.startInterceptRequestLayout();
                this.onEnterLayoutOrScroll();
                this.mAdapterHelper.preProcess();
                if (!this.mLayoutWasDefered) {
                    if (this.hasUpdatedView()) {
                        this.dispatchLayout();
                    } else {
                        this.mAdapterHelper.consumePostponedUpdates();
                    }
                }
                this.stopInterceptRequestLayout(true);
                this.onExitLayoutOrScroll();
                TraceCompat.endSection();
                return;
            }
            if (!this.mAdapterHelper.hasPendingUpdates()) return;
            TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            this.dispatchLayout();
            TraceCompat.endSection();
            return;
        }
        TraceCompat.beginSection(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
        this.dispatchLayout();
        TraceCompat.endSection();
    }

    void defaultOnMeasure(int n, int n2) {
        this.setMeasuredDimension(LayoutManager.chooseSize(n, this.getPaddingLeft() + this.getPaddingRight(), ViewCompat.getMinimumWidth((View)this)), LayoutManager.chooseSize(n2, this.getPaddingTop() + this.getPaddingBottom(), ViewCompat.getMinimumHeight((View)this)));
    }

    void dispatchChildAttached(View view) {
        Object object = RecyclerView.getChildViewHolderInt(view);
        this.onChildAttachedToWindow(view);
        Adapter adapter = this.mAdapter;
        if (adapter != null && object != null) {
            adapter.onViewAttachedToWindow(object);
        }
        if ((object = this.mOnChildAttachStateListeners) == null) return;
        int n = object.size() - 1;
        while (n >= 0) {
            this.mOnChildAttachStateListeners.get(n).onChildViewAttachedToWindow(view);
            --n;
        }
    }

    void dispatchChildDetached(View view) {
        Object object = RecyclerView.getChildViewHolderInt(view);
        this.onChildDetachedFromWindow(view);
        Adapter adapter = this.mAdapter;
        if (adapter != null && object != null) {
            adapter.onViewDetachedFromWindow(object);
        }
        if ((object = this.mOnChildAttachStateListeners) == null) return;
        int n = object.size() - 1;
        while (n >= 0) {
            this.mOnChildAttachStateListeners.get(n).onChildViewDetachedFromWindow(view);
            --n;
        }
    }

    void dispatchLayout() {
        if (this.mAdapter == null) {
            Log.e((String)TAG, (String)"No adapter attached; skipping layout");
            return;
        }
        if (this.mLayout == null) {
            Log.e((String)TAG, (String)"No layout manager attached; skipping layout");
            return;
        }
        this.mState.mIsMeasuring = false;
        if (this.mState.mLayoutStep == 1) {
            this.dispatchLayoutStep1();
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        } else if (!this.mAdapterHelper.hasUpdates() && this.mLayout.getWidth() == this.getWidth() && this.mLayout.getHeight() == this.getHeight()) {
            this.mLayout.setExactMeasureSpecsFrom(this);
        } else {
            this.mLayout.setExactMeasureSpecsFrom(this);
            this.dispatchLayoutStep2();
        }
        this.dispatchLayoutStep3();
    }

    @Override
    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        return this.getScrollingChildHelper().dispatchNestedFling(f, f2, bl);
    }

    @Override
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.getScrollingChildHelper().dispatchNestedPreFling(f, f2);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.getScrollingChildHelper().dispatchNestedPreScroll(n, n2, arrn, arrn2);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2, int n3) {
        return this.getScrollingChildHelper().dispatchNestedPreScroll(n, n2, arrn, arrn2, n3);
    }

    @Override
    public final void dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5, int[] arrn2) {
        this.getScrollingChildHelper().dispatchNestedScroll(n, n2, n3, n4, arrn, n5, arrn2);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.getScrollingChildHelper().dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        return this.getScrollingChildHelper().dispatchNestedScroll(n, n2, n3, n4, arrn, n5);
    }

    void dispatchOnScrollStateChanged(int n) {
        Object object = this.mLayout;
        if (object != null) {
            ((LayoutManager)object).onScrollStateChanged(n);
        }
        this.onScrollStateChanged(n);
        object = this.mScrollListener;
        if (object != null) {
            ((OnScrollListener)object).onScrollStateChanged(this, n);
        }
        if ((object = this.mScrollListeners) == null) return;
        int n2 = object.size() - 1;
        while (n2 >= 0) {
            this.mScrollListeners.get(n2).onScrollStateChanged(this, n);
            --n2;
        }
    }

    void dispatchOnScrolled(int n, int n2) {
        ++this.mDispatchScrollCounter;
        int n3 = this.getScrollX();
        int n4 = this.getScrollY();
        this.onScrollChanged(n3, n4, n3 - n, n4 - n2);
        this.onScrolled(n, n2);
        Object object = this.mScrollListener;
        if (object != null) {
            ((OnScrollListener)object).onScrolled(this, n, n2);
        }
        if ((object = this.mScrollListeners) != null) {
            for (n4 = object.size() - 1; n4 >= 0; --n4) {
                this.mScrollListeners.get(n4).onScrolled(this, n, n2);
            }
        }
        --this.mDispatchScrollCounter;
    }

    void dispatchPendingImportantForAccessibilityChanges() {
        int n = this.mPendingAccessibilityImportanceChange.size() - 1;
        do {
            int n2;
            if (n < 0) {
                this.mPendingAccessibilityImportanceChange.clear();
                return;
            }
            ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(n);
            if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore() && (n2 = viewHolder.mPendingAccessibilityState) != -1) {
                ViewCompat.setImportantForAccessibility(viewHolder.itemView, n2);
                viewHolder.mPendingAccessibilityState = -1;
            }
            --n;
        } while (true);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    public void draw(Canvas canvas) {
        int n;
        int n2;
        super.draw(canvas);
        int n3 = this.mItemDecorations.size();
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            this.mItemDecorations.get(n).onDrawOver(canvas, this, this.mState);
        }
        EdgeEffect edgeEffect = this.mLeftGlow;
        int n5 = 1;
        if (edgeEffect != null && !edgeEffect.isFinished()) {
            n2 = canvas.save();
            n = this.mClipToPadding ? this.getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float)(-this.getHeight() + n), 0.0f);
            edgeEffect = this.mLeftGlow;
            n3 = edgeEffect != null && edgeEffect.draw(canvas) ? 1 : 0;
            canvas.restoreToCount(n2);
        } else {
            n3 = 0;
        }
        edgeEffect = this.mTopGlow;
        n = n3;
        if (edgeEffect != null) {
            n = n3;
            if (!edgeEffect.isFinished()) {
                n2 = canvas.save();
                if (this.mClipToPadding) {
                    canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
                }
                n = (edgeEffect = this.mTopGlow) != null && edgeEffect.draw(canvas) ? 1 : 0;
                n = n3 | n;
                canvas.restoreToCount(n2);
            }
        }
        edgeEffect = this.mRightGlow;
        n3 = n;
        if (edgeEffect != null) {
            n3 = n;
            if (!edgeEffect.isFinished()) {
                n2 = canvas.save();
                int n6 = this.getWidth();
                n3 = this.mClipToPadding ? this.getPaddingTop() : 0;
                canvas.rotate(90.0f);
                canvas.translate((float)(-n3), (float)(-n6));
                edgeEffect = this.mRightGlow;
                n3 = edgeEffect != null && edgeEffect.draw(canvas) ? 1 : 0;
                n3 = n | n3;
                canvas.restoreToCount(n2);
            }
        }
        edgeEffect = this.mBottomGlow;
        n = n3;
        if (edgeEffect != null) {
            n = n3;
            if (!edgeEffect.isFinished()) {
                n2 = canvas.save();
                canvas.rotate(180.0f);
                if (this.mClipToPadding) {
                    canvas.translate((float)(-this.getWidth() + this.getPaddingRight()), (float)(-this.getHeight() + this.getPaddingBottom()));
                } else {
                    canvas.translate((float)(-this.getWidth()), (float)(-this.getHeight()));
                }
                edgeEffect = this.mBottomGlow;
                n = n4;
                if (edgeEffect != null) {
                    n = n4;
                    if (edgeEffect.draw(canvas)) {
                        n = 1;
                    }
                }
                n = n3 | n;
                canvas.restoreToCount(n2);
            }
        }
        if (n == 0 && this.mItemAnimator != null && this.mItemDecorations.size() > 0 && this.mItemAnimator.isRunning()) {
            n = n5;
        }
        if (n == 0) return;
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public boolean drawChild(Canvas canvas, View view, long l) {
        return super.drawChild(canvas, view, l);
    }

    void ensureBottomGlow() {
        EdgeEffect edgeEffect;
        if (this.mBottomGlow != null) {
            return;
        }
        this.mBottomGlow = edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 3);
        if (this.mClipToPadding) {
            edgeEffect.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        edgeEffect.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    void ensureLeftGlow() {
        EdgeEffect edgeEffect;
        if (this.mLeftGlow != null) {
            return;
        }
        this.mLeftGlow = edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 0);
        if (this.mClipToPadding) {
            edgeEffect.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        edgeEffect.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureRightGlow() {
        EdgeEffect edgeEffect;
        if (this.mRightGlow != null) {
            return;
        }
        this.mRightGlow = edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 2);
        if (this.mClipToPadding) {
            edgeEffect.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        edgeEffect.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    void ensureTopGlow() {
        EdgeEffect edgeEffect;
        if (this.mTopGlow != null) {
            return;
        }
        this.mTopGlow = edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 1);
        if (this.mClipToPadding) {
            edgeEffect.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        edgeEffect.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    String exceptionLabel() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(super.toString());
        stringBuilder.append(", adapter:");
        stringBuilder.append(this.mAdapter);
        stringBuilder.append(", layout:");
        stringBuilder.append(this.mLayout);
        stringBuilder.append(", context:");
        stringBuilder.append((Object)this.getContext());
        return stringBuilder.toString();
    }

    final void fillRemainingScrollValues(State state) {
        if (this.getScrollState() == 2) {
            OverScroller overScroller = this.mViewFlinger.mOverScroller;
            state.mRemainingScrollHorizontal = overScroller.getFinalX() - overScroller.getCurrX();
            state.mRemainingScrollVertical = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        state.mRemainingScrollHorizontal = 0;
        state.mRemainingScrollVertical = 0;
    }

    public View findChildViewUnder(float f, float f2) {
        int n = this.mChildHelper.getChildCount() - 1;
        while (n >= 0) {
            View view = this.mChildHelper.getChildAt(n);
            float f3 = view.getTranslationX();
            float f4 = view.getTranslationY();
            if (f >= (float)view.getLeft() + f3 && f <= (float)view.getRight() + f3 && f2 >= (float)view.getTop() + f4 && f2 <= (float)view.getBottom() + f4) {
                return view;
            }
            --n;
        }
        return null;
    }

    public View findContainingItemView(View view) {
        ViewParent viewParent = view.getParent();
        while (viewParent != null && viewParent != this && viewParent instanceof View) {
            view = (View)viewParent;
            viewParent = view.getParent();
        }
        if (viewParent != this) return null;
        return view;
    }

    public ViewHolder findContainingViewHolder(View object) {
        if ((object = this.findContainingItemView((View)object)) != null) return this.getChildViewHolder((View)object);
        return null;
    }

    public ViewHolder findViewHolderForAdapterPosition(int n) {
        boolean bl = this.mDataSetHasChangedAfterLayout;
        ViewHolder viewHolder = null;
        if (bl) {
            return null;
        }
        int n2 = this.mChildHelper.getUnfilteredChildCount();
        int n3 = 0;
        while (n3 < n2) {
            ViewHolder viewHolder2 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n3));
            ViewHolder viewHolder3 = viewHolder;
            if (viewHolder2 != null) {
                viewHolder3 = viewHolder;
                if (!viewHolder2.isRemoved()) {
                    viewHolder3 = viewHolder;
                    if (this.getAdapterPositionFor(viewHolder2) == n) {
                        if (!this.mChildHelper.isHidden(viewHolder2.itemView)) return viewHolder2;
                        viewHolder3 = viewHolder2;
                    }
                }
            }
            ++n3;
            viewHolder = viewHolder3;
        }
        return viewHolder;
    }

    public ViewHolder findViewHolderForItemId(long l) {
        Adapter adapter = this.mAdapter;
        ViewHolder viewHolder = null;
        ViewHolder viewHolder2 = null;
        ViewHolder viewHolder3 = viewHolder;
        if (adapter == null) return viewHolder3;
        if (!adapter.hasStableIds()) {
            return viewHolder;
        }
        int n = this.mChildHelper.getUnfilteredChildCount();
        int n2 = 0;
        do {
            viewHolder3 = viewHolder2;
            if (n2 >= n) return viewHolder3;
            viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n2));
            viewHolder3 = viewHolder2;
            if (viewHolder != null) {
                viewHolder3 = viewHolder2;
                if (!viewHolder.isRemoved()) {
                    viewHolder3 = viewHolder2;
                    if (viewHolder.getItemId() == l) {
                        if (!this.mChildHelper.isHidden(viewHolder.itemView)) return viewHolder;
                        viewHolder3 = viewHolder;
                    }
                }
            }
            ++n2;
            viewHolder2 = viewHolder3;
        } while (true);
    }

    public ViewHolder findViewHolderForLayoutPosition(int n) {
        return this.findViewHolderForPosition(n, false);
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int n) {
        return this.findViewHolderForPosition(n, false);
    }

    /*
     * Unable to fully structure code
     */
    ViewHolder findViewHolderForPosition(int var1_1, boolean var2_2) {
        var3_3 = this.mChildHelper.getUnfilteredChildCount();
        var4_4 = null;
        var5_5 = 0;
        while (var5_5 < var3_3) {
            block3 : {
                block4 : {
                    var6_6 = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(var5_5));
                    var7_7 = var4_4;
                    if (var6_6 == null) break block3;
                    var7_7 = var4_4;
                    if (var6_6.isRemoved()) break block3;
                    if (!var2_2) break block4;
                    if (var6_6.mPosition == var1_1) ** GOTO lbl-1000
                    var7_7 = var4_4;
                    break block3;
                }
                if (var6_6.getLayoutPosition() != var1_1) {
                    var7_7 = var4_4;
                } else lbl-1000: // 2 sources:
                {
                    if (this.mChildHelper.isHidden(var6_6.itemView) == false) return var6_6;
                    var7_7 = var6_6;
                }
            }
            ++var5_5;
            var4_4 = var7_7;
        }
        return var4_4;
    }

    public boolean fling(int n, int n2) {
        int n3;
        int n4;
        boolean bl;
        boolean bl2;
        Object object;
        int n5;
        block15 : {
            block14 : {
                block13 : {
                    block12 : {
                        object = this.mLayout;
                        n3 = 0;
                        if (object == null) {
                            Log.e((String)TAG, (String)"Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                            return false;
                        }
                        if (this.mLayoutSuppressed) {
                            return false;
                        }
                        bl2 = ((LayoutManager)object).canScrollHorizontally();
                        bl = this.mLayout.canScrollVertically();
                        if (!bl2) break block12;
                        n4 = n;
                        if (Math.abs(n) >= this.mMinFlingVelocity) break block13;
                    }
                    n4 = 0;
                }
                if (!bl) break block14;
                n5 = n2;
                if (Math.abs(n2) >= this.mMinFlingVelocity) break block15;
            }
            n5 = 0;
        }
        if (n4 == 0 && n5 == 0) {
            return false;
        }
        float f = n4;
        float f2 = n5;
        if (this.dispatchNestedPreFling(f, f2)) return false;
        boolean bl3 = bl2 || bl;
        this.dispatchNestedFling(f, f2, bl3);
        object = this.mOnFlingListener;
        if (object != null && ((OnFlingListener)object).onFling(n4, n5)) {
            return true;
        }
        if (!bl3) return false;
        n = n3;
        if (bl2) {
            n = 1;
        }
        n2 = n;
        if (bl) {
            n2 = n | 2;
        }
        this.startNestedScroll(n2, 1);
        n = this.mMaxFlingVelocity;
        n = Math.max(-n, Math.min(n4, n));
        n2 = this.mMaxFlingVelocity;
        n2 = Math.max(-n2, Math.min(n5, n2));
        this.mViewFlinger.fling(n, n2);
        return true;
    }

    public View focusSearch(View view, int n) {
        Object object = this.mLayout.onInterceptFocusSearch(view, n);
        if (object != null) {
            return object;
        }
        object = this.mAdapter;
        int n2 = 1;
        int n3 = object != null && this.mLayout != null && !this.isComputingLayout() && !this.mLayoutSuppressed ? 1 : 0;
        object = FocusFinder.getInstance();
        if (n3 != 0 && (n == 2 || n == 1)) {
            int n4;
            int n5;
            if (this.mLayout.canScrollVertically()) {
                n5 = n == 2 ? 130 : 33;
                n4 = object.findNextFocus((ViewGroup)this, view, n5) == null ? 1 : 0;
                n3 = n4;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    n = n5;
                    n3 = n4;
                }
            } else {
                n3 = 0;
            }
            n4 = n3;
            n5 = n;
            if (n3 == 0) {
                n4 = n3;
                n5 = n;
                if (this.mLayout.canScrollHorizontally()) {
                    n3 = this.mLayout.getLayoutDirection() == 1 ? 1 : 0;
                    n5 = n == 2 ? 1 : 0;
                    n3 = (n3 ^ n5) != 0 ? 66 : 17;
                    n5 = object.findNextFocus((ViewGroup)this, view, n3) == null ? n2 : 0;
                    if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                        n = n3;
                    }
                    n4 = n5;
                    n5 = n;
                }
            }
            if (n4 != 0) {
                this.consumePendingUpdateOperations();
                if (this.findContainingItemView(view) == null) {
                    return null;
                }
                this.startInterceptRequestLayout();
                this.mLayout.onFocusSearchFailed(view, n5, this.mRecycler, this.mState);
                this.stopInterceptRequestLayout(false);
            }
            object = object.findNextFocus((ViewGroup)this, view, n5);
            n = n5;
        } else if ((object = object.findNextFocus((ViewGroup)this, view, n)) == null && n3 != 0) {
            this.consumePendingUpdateOperations();
            if (this.findContainingItemView(view) == null) {
                return null;
            }
            this.startInterceptRequestLayout();
            object = this.mLayout.onFocusSearchFailed(view, n, this.mRecycler, this.mState);
            this.stopInterceptRequestLayout(false);
        }
        if (object != null && !object.hasFocusable()) {
            if (this.getFocusedChild() == null) {
                return super.focusSearch(view, n);
            }
            this.requestChildOnScreen((View)object, null);
            return view;
        }
        if (!this.isPreferredNextFocus(view, (View)object, n)) return super.focusSearch(view, n);
        return object;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        Object object = this.mLayout;
        if (object != null) {
            return ((LayoutManager)object).generateDefaultLayoutParams();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("RecyclerView has no LayoutManager");
        ((StringBuilder)object).append(this.exceptionLabel());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet object) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(this.getContext(), (AttributeSet)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("RecyclerView has no LayoutManager");
        ((StringBuilder)object).append(this.exceptionLabel());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams((ViewGroup.LayoutParams)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("RecyclerView has no LayoutManager");
        ((StringBuilder)object).append(this.exceptionLabel());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    int getAdapterPositionFor(ViewHolder viewHolder) {
        if (viewHolder.hasAnyOfTheFlags(524)) return -1;
        if (viewHolder.isBound()) return this.mAdapterHelper.applyPendingUpdatesToPosition(viewHolder.mPosition);
        return -1;
    }

    public int getBaseline() {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) return super.getBaseline();
        return layoutManager.getBaseline();
    }

    long getChangedHolderKey(ViewHolder viewHolder) {
        if (!this.mAdapter.hasStableIds()) return viewHolder.mPosition;
        return viewHolder.getItemId();
    }

    public int getChildAdapterPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) == null) return -1;
        return ((ViewHolder)object).getAdapterPosition();
    }

    protected int getChildDrawingOrder(int n, int n2) {
        ChildDrawingOrderCallback childDrawingOrderCallback = this.mChildDrawingOrderCallback;
        if (childDrawingOrderCallback != null) return childDrawingOrderCallback.onGetChildDrawingOrder(n, n2);
        return super.getChildDrawingOrder(n, n2);
    }

    public long getChildItemId(View object) {
        long l;
        Adapter adapter = this.mAdapter;
        long l2 = l = -1L;
        if (adapter == null) return l2;
        if (!adapter.hasStableIds()) {
            return l;
        }
        object = RecyclerView.getChildViewHolderInt((View)object);
        l2 = l;
        if (object == null) return l2;
        return ((ViewHolder)object).getItemId();
    }

    public int getChildLayoutPosition(View object) {
        if ((object = RecyclerView.getChildViewHolderInt((View)object)) == null) return -1;
        return ((ViewHolder)object).getLayoutPosition();
    }

    @Deprecated
    public int getChildPosition(View view) {
        return this.getChildAdapterPosition(view);
    }

    public ViewHolder getChildViewHolder(View view) {
        Object object = view.getParent();
        if (object == null) return RecyclerView.getChildViewHolderInt(view);
        if (object == this) {
            return RecyclerView.getChildViewHolderInt(view);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("View ");
        ((StringBuilder)object).append((Object)view);
        ((StringBuilder)object).append(" is not a direct child of ");
        ((StringBuilder)object).append(this);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
    }

    public EdgeEffectFactory getEdgeEffectFactory() {
        return this.mEdgeEffectFactory;
    }

    public ItemAnimator getItemAnimator() {
        return this.mItemAnimator;
    }

    Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.mInsetsDirty) {
            return layoutParams.mDecorInsets;
        }
        if (this.mState.isPreLayout()) {
            if (layoutParams.isItemChanged()) return layoutParams.mDecorInsets;
            if (layoutParams.isViewInvalid()) {
                return layoutParams.mDecorInsets;
            }
        }
        Rect rect = layoutParams.mDecorInsets;
        rect.set(0, 0, 0, 0);
        int n = this.mItemDecorations.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                layoutParams.mInsetsDirty = false;
                return rect;
            }
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(n2).getItemOffsets(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
            ++n2;
        } while (true);
    }

    public ItemDecoration getItemDecorationAt(int n) {
        int n2 = this.getItemDecorationCount();
        if (n >= 0 && n < n2) {
            return this.mItemDecorations.get(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is an invalid index for size ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getItemDecorationCount() {
        return this.mItemDecorations.size();
    }

    public LayoutManager getLayoutManager() {
        return this.mLayout;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    long getNanoTime() {
        if (!ALLOW_THREAD_GAP_WORK) return 0L;
        return System.nanoTime();
    }

    public OnFlingListener getOnFlingListener() {
        return this.mOnFlingListener;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.mRecycler.getRecycledViewPool();
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return this.getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int n) {
        return this.getScrollingChildHelper().hasNestedScrollingParent(n);
    }

    public boolean hasPendingAdapterUpdates() {
        if (!this.mFirstLayoutComplete) return true;
        if (this.mDataSetHasChangedAfterLayout) return true;
        if (this.mAdapterHelper.hasPendingUpdates()) return true;
        return false;
    }

    void initAdapterManager() {
        this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback(){

            void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
                int n = updateOp.cmd;
                if (n == 1) {
                    RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                    return;
                }
                if (n == 2) {
                    RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                    return;
                }
                if (n == 4) {
                    RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                    return;
                }
                if (n != 8) {
                    return;
                }
                RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, 1);
            }

            @Override
            public ViewHolder findViewHolder(int n) {
                ViewHolder viewHolder = RecyclerView.this.findViewHolderForPosition(n, true);
                if (viewHolder == null) {
                    return null;
                }
                if (!RecyclerView.this.mChildHelper.isHidden(viewHolder.itemView)) return viewHolder;
                return null;
            }

            @Override
            public void markViewHoldersUpdated(int n, int n2, Object object) {
                RecyclerView.this.viewRangeUpdate(n, n2, object);
                RecyclerView.this.mItemsChanged = true;
            }

            @Override
            public void offsetPositionsForAdd(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForInsert(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForMove(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForMove(n, n2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void offsetPositionsForRemovingInvisible(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, true);
                RecyclerView.this.mItemsAddedOrRemoved = true;
                State state = RecyclerView.this.mState;
                state.mDeletedInvisibleItemCountSincePreviousLayout += n2;
            }

            @Override
            public void offsetPositionsForRemovingLaidOutOrNewView(int n, int n2) {
                RecyclerView.this.offsetPositionRecordsForRemove(n, n2, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override
            public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }

            @Override
            public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {
                this.dispatchUpdate(updateOp);
            }
        });
    }

    void initFastScroller(StateListDrawable object, Drawable drawable2, StateListDrawable stateListDrawable, Drawable drawable3) {
        if (object != null && drawable2 != null && stateListDrawable != null && drawable3 != null) {
            Resources resources = this.getContext().getResources();
            new FastScroller(this, (StateListDrawable)object, drawable2, stateListDrawable, drawable3, resources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(R.dimen.fastscroll_margin));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Trying to set fast scroller without both required drawables.");
        ((StringBuilder)object).append(this.exceptionLabel());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() == 0) {
            return;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    boolean isAccessibilityEnabled() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager == null) return false;
        if (!accessibilityManager.isEnabled()) return false;
        return true;
    }

    public boolean isAnimating() {
        ItemAnimator itemAnimator = this.mItemAnimator;
        if (itemAnimator == null) return false;
        if (!itemAnimator.isRunning()) return false;
        return true;
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    public boolean isComputingLayout() {
        if (this.mLayoutOrScrollCounter <= 0) return false;
        return true;
    }

    @Deprecated
    public boolean isLayoutFrozen() {
        return this.isLayoutSuppressed();
    }

    public final boolean isLayoutSuppressed() {
        return this.mLayoutSuppressed;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.getScrollingChildHelper().isNestedScrollingEnabled();
    }

    void jumpToPositionForSmoothScroller(int n) {
        if (this.mLayout == null) {
            return;
        }
        this.setScrollState(2);
        this.mLayout.scrollToPosition(n);
        this.awakenScrollBars();
    }

    void markItemDecorInsetsDirty() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mRecycler.markItemDecorInsetsDirty();
                return;
            }
            ((LayoutParams)this.mChildHelper.getUnfilteredChildAt((int)n2).getLayoutParams()).mInsetsDirty = true;
            ++n2;
        } while (true);
    }

    void markKnownViewsInvalid() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.markItemDecorInsetsDirty();
                this.mRecycler.markKnownViewsInvalid();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n2));
            if (viewHolder != null && !viewHolder.shouldIgnore()) {
                viewHolder.addFlags(6);
            }
            ++n2;
        } while (true);
    }

    public void offsetChildrenHorizontal(int n) {
        int n2 = this.mChildHelper.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            this.mChildHelper.getChildAt(n3).offsetLeftAndRight(n);
            ++n3;
        }
    }

    public void offsetChildrenVertical(int n) {
        int n2 = this.mChildHelper.getChildCount();
        int n3 = 0;
        while (n3 < n2) {
            this.mChildHelper.getChildAt(n3).offsetTopAndBottom(n);
            ++n3;
        }
    }

    void offsetPositionRecordsForInsert(int n, int n2) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        int n4 = 0;
        do {
            if (n4 >= n3) {
                this.mRecycler.offsetPositionRecordsForInsert(n, n2);
                this.requestLayout();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n4));
            if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.mPosition >= n) {
                viewHolder.offsetPosition(n2, false);
                this.mState.mStructureChanged = true;
            }
            ++n4;
        } while (true);
    }

    void offsetPositionRecordsForMove(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = this.mChildHelper.getUnfilteredChildCount();
        if (n < n2) {
            n3 = -1;
            n5 = n;
            n4 = n2;
        } else {
            n4 = n;
            n5 = n2;
            n3 = 1;
        }
        int n7 = 0;
        do {
            if (n7 >= n6) {
                this.mRecycler.offsetPositionRecordsForMove(n, n2);
                this.requestLayout();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n7));
            if (viewHolder != null && viewHolder.mPosition >= n5 && viewHolder.mPosition <= n4) {
                if (viewHolder.mPosition == n) {
                    viewHolder.offsetPosition(n2 - n, false);
                } else {
                    viewHolder.offsetPosition(n3, false);
                }
                this.mState.mStructureChanged = true;
            }
            ++n7;
        } while (true);
    }

    void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        int n4 = 0;
        do {
            if (n4 >= n3) {
                this.mRecycler.offsetPositionRecordsForRemove(n, n2, bl);
                this.requestLayout();
                return;
            }
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n4));
            if (viewHolder != null && !viewHolder.shouldIgnore()) {
                if (viewHolder.mPosition >= n + n2) {
                    viewHolder.offsetPosition(-n2, bl);
                    this.mState.mStructureChanged = true;
                } else if (viewHolder.mPosition >= n) {
                    viewHolder.flagRemovedAndOffsetPosition(n - 1, -n2, bl);
                    this.mState.mStructureChanged = true;
                }
            }
            ++n4;
        } while (true);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        boolean bl = true;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || this.isLayoutRequested()) {
            bl = false;
        }
        this.mFirstLayoutComplete = bl;
        Object object = this.mLayout;
        if (object != null) {
            ((LayoutManager)object).dispatchAttachedToWindow(this);
        }
        this.mPostedAnimatorRunner = false;
        if (!ALLOW_THREAD_GAP_WORK) return;
        this.mGapWorker = object = GapWorker.sGapWorker.get();
        if (object == null) {
            float f;
            this.mGapWorker = new GapWorker();
            object = ViewCompat.getDisplay((View)this);
            float f2 = f = 60.0f;
            if (!this.isInEditMode()) {
                f2 = f;
                if (object != null) {
                    float f3 = object.getRefreshRate();
                    f2 = f;
                    if (f3 >= 30.0f) {
                        f2 = f3;
                    }
                }
            }
            this.mGapWorker.mFrameIntervalNs = (long)(1.0E9f / f2);
            GapWorker.sGapWorker.set(this.mGapWorker);
        }
        this.mGapWorker.add(this);
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Object object = this.mItemAnimator;
        if (object != null) {
            ((ItemAnimator)object).endAnimations();
        }
        this.stopScroll();
        this.mIsAttached = false;
        object = this.mLayout;
        if (object != null) {
            ((LayoutManager)object).dispatchDetachedFromWindow(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        this.removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.onDetach();
        if (!ALLOW_THREAD_GAP_WORK) return;
        object = this.mGapWorker;
        if (object == null) return;
        ((GapWorker)object).remove(this);
        this.mGapWorker = null;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = this.mItemDecorations.size();
        int n2 = 0;
        while (n2 < n) {
            this.mItemDecorations.get(n2).onDraw(canvas, this, this.mState);
            ++n2;
        }
    }

    void onEnterLayoutOrScroll() {
        ++this.mLayoutOrScrollCounter;
    }

    void onExitLayoutOrScroll() {
        this.onExitLayoutOrScroll(true);
    }

    void onExitLayoutOrScroll(boolean bl) {
        int n;
        this.mLayoutOrScrollCounter = n = this.mLayoutOrScrollCounter - 1;
        if (n >= 1) return;
        this.mLayoutOrScrollCounter = 0;
        if (!bl) return;
        this.dispatchContentChangedIfNecessary();
        this.dispatchPendingImportantForAccessibilityChanges();
    }

    /*
     * Unable to fully structure code
     */
    public boolean onGenericMotionEvent(MotionEvent var1_1) {
        block6 : {
            block7 : {
                block5 : {
                    if (this.mLayout == null) {
                        return false;
                    }
                    if (this.mLayoutSuppressed) {
                        return false;
                    }
                    if (var1_1.getAction() != 8) return false;
                    if ((var1_1.getSource() & 2) == 0) break block5;
                    var2_2 = this.mLayout.canScrollVertically() != false ? -var1_1.getAxisValue(9) : 0.0f;
                    var3_3 = var2_2;
                    if (!this.mLayout.canScrollHorizontally()) ** GOTO lbl25
                    var4_4 = var1_1.getAxisValue(10);
                    var3_3 = var2_2;
                    var2_2 = var4_4;
                    break block6;
                }
                if ((var1_1.getSource() & 4194304) == 0) ** GOTO lbl-1000
                var2_2 = var1_1.getAxisValue(26);
                if (!this.mLayout.canScrollVertically()) break block7;
                var3_3 = -var2_2;
                ** GOTO lbl25
            }
            if (this.mLayout.canScrollHorizontally()) {
                var3_3 = 0.0f;
            } else lbl-1000: // 2 sources:
            {
                var3_3 = 0.0f;
lbl25: // 3 sources:
                var2_2 = 0.0f;
            }
        }
        if (var3_3 == 0.0f) {
            if (var2_2 == 0.0f) return false;
        }
        this.scrollByInternal((int)(var2_2 * this.mScaledHorizontalScrollFactor), (int)(var3_3 * this.mScaledVerticalScrollFactor), var1_1);
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent object) {
        boolean bl = this.mLayoutSuppressed;
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        this.mInterceptingOnItemTouchListener = null;
        if (this.findInterceptingOnItemTouchListener((MotionEvent)object)) {
            this.cancelScroll();
            return true;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            return false;
        }
        bl = layoutManager.canScrollHorizontally();
        boolean bl3 = this.mLayout.canScrollVertically();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement((MotionEvent)object);
        int n = object.getActionMasked();
        int n2 = object.getActionIndex();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 5) {
                            if (n == 6) {
                                this.onPointerUp((MotionEvent)object);
                            }
                        } else {
                            this.mScrollPointerId = object.getPointerId(n2);
                            this.mLastTouchX = n = (int)(object.getX(n2) + 0.5f);
                            this.mInitialTouchX = n;
                            this.mLastTouchY = n2 = (int)(object.getY(n2) + 0.5f);
                            this.mInitialTouchY = n2;
                        }
                    } else {
                        this.cancelScroll();
                    }
                } else {
                    n = object.findPointerIndex(this.mScrollPointerId);
                    if (n < 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Error processing scroll; pointer index for id ");
                        ((StringBuilder)object).append(this.mScrollPointerId);
                        ((StringBuilder)object).append(" not found. Did any MotionEvents get skipped?");
                        Log.e((String)TAG, (String)((StringBuilder)object).toString());
                        return false;
                    }
                    n2 = (int)(object.getX(n) + 0.5f);
                    int n3 = (int)(object.getY(n) + 0.5f);
                    if (this.mScrollState != 1) {
                        n = this.mInitialTouchX;
                        int n4 = this.mInitialTouchY;
                        if (bl && Math.abs(n2 - n) > this.mTouchSlop) {
                            this.mLastTouchX = n2;
                            n2 = 1;
                        } else {
                            n2 = 0;
                        }
                        n = n2;
                        if (bl3) {
                            n = n2;
                            if (Math.abs(n3 - n4) > this.mTouchSlop) {
                                this.mLastTouchY = n3;
                                n = 1;
                            }
                        }
                        if (n != 0) {
                            this.setScrollState(1);
                        }
                    }
                }
            } else {
                this.mVelocityTracker.clear();
                this.stopNestedScroll(0);
            }
        } else {
            if (this.mIgnoreMotionEventTillDown) {
                this.mIgnoreMotionEventTillDown = false;
            }
            this.mScrollPointerId = object.getPointerId(0);
            this.mLastTouchX = n2 = (int)(object.getX() + 0.5f);
            this.mInitialTouchX = n2;
            this.mLastTouchY = n2 = (int)(object.getY() + 0.5f);
            this.mInitialTouchY = n2;
            if (this.mScrollState == 2) {
                this.getParent().requestDisallowInterceptTouchEvent(true);
                this.setScrollState(1);
                this.stopNestedScroll(1);
            }
            object = this.mNestedOffsets;
            object[1] = false;
            object[0] = false;
            n2 = bl ? 1 : 0;
            n = n2;
            if (bl3) {
                n = n2 | 2;
            }
            this.startNestedScroll(n, 0);
        }
        if (this.mScrollState != 1) return bl2;
        return true;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        TraceCompat.beginSection(TRACE_ON_LAYOUT_TAG);
        this.dispatchLayout();
        TraceCompat.endSection();
        this.mFirstLayoutComplete = true;
    }

    protected void onMeasure(int n, int n2) {
        Object object = this.mLayout;
        if (object == null) {
            this.defaultOnMeasure(n, n2);
            return;
        }
        boolean bl = ((LayoutManager)object).isAutoMeasureEnabled();
        boolean bl2 = false;
        if (bl) {
            int n3 = View.MeasureSpec.getMode((int)n);
            int n4 = View.MeasureSpec.getMode((int)n2);
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            boolean bl3 = bl2;
            if (n3 == 1073741824) {
                bl3 = bl2;
                if (n4 == 1073741824) {
                    bl3 = true;
                }
            }
            if (bl3) return;
            if (this.mAdapter == null) {
                return;
            }
            if (this.mState.mLayoutStep == 1) {
                this.dispatchLayoutStep1();
            }
            this.mLayout.setMeasureSpecs(n, n2);
            this.mState.mIsMeasuring = true;
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n, n2);
            if (!this.mLayout.shouldMeasureTwice()) return;
            this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)1073741824));
            this.mState.mIsMeasuring = true;
            this.dispatchLayoutStep2();
            this.mLayout.setMeasuredDimensionFromChildren(n, n2);
            return;
        }
        if (this.mHasFixedSize) {
            this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
            return;
        }
        if (this.mAdapterUpdateDuringMeasure) {
            this.startInterceptRequestLayout();
            this.onEnterLayoutOrScroll();
            this.processAdapterUpdatesAndSetAnimationFlags();
            this.onExitLayoutOrScroll();
            if (this.mState.mRunPredictiveAnimations) {
                this.mState.mInPreLayout = true;
            } else {
                this.mAdapterHelper.consumeUpdatesInOnePass();
                this.mState.mInPreLayout = false;
            }
            this.mAdapterUpdateDuringMeasure = false;
            this.stopInterceptRequestLayout(false);
        } else if (this.mState.mRunPredictiveAnimations) {
            this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredHeight());
            return;
        }
        object = this.mAdapter;
        this.mState.mItemCount = object != null ? ((Adapter)object).getItemCount() : 0;
        this.startInterceptRequestLayout();
        this.mLayout.onMeasure(this.mRecycler, this.mState, n, n2);
        this.stopInterceptRequestLayout(false);
        this.mState.mInPreLayout = false;
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        if (!this.isComputingLayout()) return super.onRequestFocusInDescendants(n, rect);
        return false;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        this.mPendingSavedState = parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        if (this.mLayout == null) return;
        if (this.mPendingSavedState.mLayoutState == null) return;
        this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Object object = this.mPendingSavedState;
        if (object != null) {
            savedState.copyFrom((SavedState)object);
            return savedState;
        }
        object = this.mLayout;
        if (object != null) {
            savedState.mLayoutState = ((LayoutManager)object).onSaveInstanceState();
            return savedState;
        }
        savedState.mLayoutState = null;
        return savedState;
    }

    public void onScrollStateChanged(int n) {
    }

    public void onScrolled(int n, int n2) {
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n == n3) {
            if (n2 == n4) return;
        }
        this.invalidateGlows();
    }

    /*
     * Unable to fully structure code
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block27 : {
            block24 : {
                block25 : {
                    block29 : {
                        block28 : {
                            block26 : {
                                var2_2 = this.mLayoutSuppressed;
                                var3_3 = 0;
                                if (var2_2 != false) return false;
                                if (this.mIgnoreMotionEventTillDown) {
                                    return false;
                                }
                                if (this.dispatchToOnItemTouchListeners((MotionEvent)var1_1)) {
                                    this.cancelScroll();
                                    return true;
                                }
                                var4_4 = this.mLayout;
                                if (var4_4 == null) {
                                    return false;
                                }
                                var5_5 = var4_4.canScrollHorizontally();
                                var2_2 = this.mLayout.canScrollVertically();
                                if (this.mVelocityTracker == null) {
                                    this.mVelocityTracker = VelocityTracker.obtain();
                                }
                                var6_6 = var1_1.getActionMasked();
                                var7_7 = var1_1.getActionIndex();
                                if (var6_6 == 0) {
                                    var4_4 = this.mNestedOffsets;
                                    var4_4[1] = (MotionEvent)false;
                                    var4_4[0] = (MotionEvent)false;
                                }
                                var4_4 = MotionEvent.obtain((MotionEvent)var1_1);
                                var8_8 = this.mNestedOffsets;
                                var4_4.offsetLocation((float)var8_8[0], (float)var8_8[1]);
                                if (var6_6 == 0) break block24;
                                if (var6_6 == 1) break block25;
                                if (var6_6 == 2) break block26;
                                if (var6_6 != 3) {
                                    if (var6_6 != 5) {
                                        if (var6_6 != 6) {
                                            var9_9 = var3_3;
                                        } else {
                                            this.onPointerUp((MotionEvent)var1_1);
                                            var9_9 = var3_3;
                                        }
                                    } else {
                                        this.mScrollPointerId = var1_1.getPointerId(var7_7);
                                        this.mLastTouchX = var6_6 = (int)(var1_1.getX(var7_7) + 0.5f);
                                        this.mInitialTouchX = var6_6;
                                        this.mLastTouchY = var7_7 = (int)(var1_1.getY(var7_7) + 0.5f);
                                        this.mInitialTouchY = var7_7;
                                        var9_9 = var3_3;
                                    }
                                } else {
                                    this.cancelScroll();
                                    var9_9 = var3_3;
                                }
                                break block27;
                            }
                            var7_7 = var1_1.findPointerIndex(this.mScrollPointerId);
                            if (var7_7 < 0) {
                                var1_1 = new StringBuilder();
                                var1_1.append("Error processing scroll; pointer index for id ");
                                var1_1.append(this.mScrollPointerId);
                                var1_1.append(" not found. Did any MotionEvents get skipped?");
                                Log.e((String)"RecyclerView", (String)var1_1.toString());
                                return false;
                            }
                            var10_10 = (int)(var1_1.getX(var7_7) + 0.5f);
                            var11_11 = (int)(var1_1.getY(var7_7) + 0.5f);
                            var7_7 = this.mLastTouchX - var10_10;
                            var9_9 = this.mLastTouchY - var11_11;
                            var12_12 = var7_7;
                            var6_6 = var9_9;
                            if (this.mScrollState == 1) break block28;
                            var13_13 = var7_7;
                            if (!var5_5) ** GOTO lbl-1000
                            var7_7 = var7_7 > 0 ? Math.max(0, var7_7 - this.mTouchSlop) : Math.min(0, var7_7 + this.mTouchSlop);
                            var13_13 = var7_7;
                            if (var7_7 != 0) {
                                var6_6 = 1;
                            } else lbl-1000: // 2 sources:
                            {
                                var6_6 = 0;
                                var7_7 = var13_13;
                            }
                            var13_13 = var9_9;
                            var14_14 = var6_6;
                            if (var2_2) {
                                var12_12 = var9_9 > 0 ? Math.max(0, var9_9 - this.mTouchSlop) : Math.min(0, var9_9 + this.mTouchSlop);
                                var13_13 = var12_12;
                                var14_14 = var6_6;
                                if (var12_12 != 0) {
                                    var14_14 = 1;
                                    var13_13 = var12_12;
                                }
                            }
                            var12_12 = var7_7;
                            var6_6 = var13_13;
                            if (var14_14 != 0) {
                                this.setScrollState(1);
                                var6_6 = var13_13;
                                var12_12 = var7_7;
                            }
                        }
                        var13_13 = var6_6;
                        var9_9 = var3_3;
                        if (this.mScrollState != 1) break block27;
                        var8_8 = this.mReusableIntPair;
                        var8_8[0] = 0;
                        var8_8[1] = 0;
                        var9_9 = var5_5 != false ? var12_12 : 0;
                        var14_14 = var2_2 != false ? var13_13 : 0;
                        var6_6 = var12_12;
                        var7_7 = var13_13;
                        if (this.dispatchNestedPreScroll(var9_9, var14_14, this.mReusableIntPair, this.mScrollOffset, 0)) {
                            var8_8 = this.mReusableIntPair;
                            var6_6 = var12_12 - var8_8[0];
                            var7_7 = var13_13 - var8_8[1];
                            var8_8 = this.mNestedOffsets;
                            var13_13 = var8_8[0];
                            var15_15 = this.mScrollOffset;
                            var8_8[0] = var13_13 + var15_15[0];
                            var8_8[1] = var8_8[1] + var15_15[1];
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        var8_8 = this.mScrollOffset;
                        this.mLastTouchX = var10_10 - var8_8[0];
                        this.mLastTouchY = var11_11 - var8_8[1];
                        var13_13 = var5_5 != false ? var6_6 : 0;
                        if (this.scrollByInternal(var13_13, var12_12 = var2_2 != false ? var7_7 : 0, (MotionEvent)var1_1)) {
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        var9_9 = var3_3;
                        if (this.mGapWorker == null) break block27;
                        if (var6_6 != 0) break block29;
                        var9_9 = var3_3;
                        if (var7_7 == 0) break block27;
                    }
                    this.mGapWorker.postFromTraversal(this, var6_6, var7_7);
                    var9_9 = var3_3;
                    break block27;
                }
                this.mVelocityTracker.addMovement(var4_4);
                this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaxFlingVelocity);
                var16_16 = var5_5 != false ? -this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
                var17_17 = var2_2 != false ? -this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
                if (var16_16 == 0.0f && var17_17 == 0.0f || !this.fling((int)var16_16, (int)var17_17)) {
                    this.setScrollState(0);
                }
                this.resetScroll();
                var9_9 = 1;
                break block27;
            }
            this.mScrollPointerId = var1_1.getPointerId(0);
            this.mLastTouchX = var7_7 = (int)(var1_1.getX() + 0.5f);
            this.mInitialTouchX = var7_7;
            this.mLastTouchY = var7_7 = (int)(var1_1.getY() + 0.5f);
            this.mInitialTouchY = var7_7;
            var7_7 = var5_5 != false ? 1 : 0;
            var6_6 = var7_7;
            if (var2_2) {
                var6_6 = var7_7 | 2;
            }
            this.startNestedScroll(var6_6, 0);
            var9_9 = var3_3;
        }
        if (var9_9 == 0) {
            this.mVelocityTracker.addMovement(var4_4);
        }
        var4_4.recycle();
        return true;
    }

    void postAnimationRunner() {
        if (this.mPostedAnimatorRunner) return;
        if (!this.mIsAttached) return;
        ViewCompat.postOnAnimation((View)this, this.mItemAnimatorRunner);
        this.mPostedAnimatorRunner = true;
    }

    void processDataSetCompletelyChanged(boolean bl) {
        this.mDispatchItemsChangedEvent = bl | this.mDispatchItemsChangedEvent;
        this.mDataSetHasChangedAfterLayout = true;
        this.markKnownViewsInvalid();
    }

    void recordAnimationInfoIfBouncedHiddenView(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.setFlags(0, 8192);
        if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore()) {
            long l = this.getChangedHolderKey(viewHolder);
            this.mViewInfoStore.addToOldChangeHolders(l, viewHolder);
        }
        this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
    }

    void removeAndRecycleViews() {
        Object object = this.mItemAnimator;
        if (object != null) {
            ((ItemAnimator)object).endAnimations();
        }
        if ((object = this.mLayout) != null) {
            ((LayoutManager)object).removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
        }
        this.mRecycler.clear();
    }

    boolean removeAnimatingView(View object) {
        this.startInterceptRequestLayout();
        boolean bl = this.mChildHelper.removeViewIfHidden((View)object);
        if (bl) {
            object = RecyclerView.getChildViewHolderInt(object);
            this.mRecycler.unscrapView((ViewHolder)object);
            this.mRecycler.recycleViewHolderInternal((ViewHolder)object);
        }
        this.stopInterceptRequestLayout(bl ^ true);
        return bl;
    }

    protected void removeDetachedView(View object, boolean bl) {
        ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
        if (viewHolder != null) {
            if (viewHolder.isTmpDetached()) {
                viewHolder.clearTmpDetachFlag();
            } else if (!viewHolder.shouldIgnore()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Called removeDetachedView with a view which is not flagged as tmp detached.");
                ((StringBuilder)object).append(viewHolder);
                ((StringBuilder)object).append(this.exceptionLabel());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        object.clearAnimation();
        this.dispatchChildDetached((View)object);
        super.removeDetachedView((View)object, bl);
    }

    public void removeItemDecoration(ItemDecoration itemDecoration) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(itemDecoration);
        if (this.mItemDecorations.isEmpty()) {
            boolean bl = this.getOverScrollMode() == 2;
            this.setWillNotDraw(bl);
        }
        this.markItemDecorInsetsDirty();
        this.requestLayout();
    }

    public void removeItemDecorationAt(int n) {
        int n2 = this.getItemDecorationCount();
        if (n >= 0 && n < n2) {
            this.removeItemDecoration(this.getItemDecorationAt(n));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is an invalid index for size ");
        stringBuilder.append(n2);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
        if (list == null) {
            return;
        }
        list.remove(onChildAttachStateChangeListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListeners.remove(onItemTouchListener);
        if (this.mInterceptingOnItemTouchListener != onItemTouchListener) return;
        this.mInterceptingOnItemTouchListener = null;
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        List<OnScrollListener> list = this.mScrollListeners;
        if (list == null) return;
        list.remove(onScrollListener);
    }

    void repositionShadowingViews() {
        int n = this.mChildHelper.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = this.mChildHelper.getChildAt(n2);
            ViewHolder viewHolder = this.getChildViewHolder(view);
            if (viewHolder != null && viewHolder.mShadowingHolder != null) {
                viewHolder = viewHolder.mShadowingHolder.itemView;
                int n3 = view.getLeft();
                int n4 = view.getTop();
                if (n3 != viewHolder.getLeft() || n4 != viewHolder.getTop()) {
                    viewHolder.layout(n3, n4, viewHolder.getWidth() + n3, viewHolder.getHeight() + n4);
                }
            }
            ++n2;
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mLayout.onRequestChildFocus(this, this.mState, view, view2) && view2 != null) {
            this.requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        return this.mLayout.requestChildRectangleOnScreen(this, view, rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        int n = this.mOnItemTouchListeners.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                super.requestDisallowInterceptTouchEvent(bl);
                return;
            }
            this.mOnItemTouchListeners.get(n2).onRequestDisallowInterceptTouchEvent(bl);
            ++n2;
        } while (true);
    }

    public void requestLayout() {
        if (this.mInterceptRequestLayoutDepth == 0 && !this.mLayoutSuppressed) {
            super.requestLayout();
            return;
        }
        this.mLayoutWasDefered = true;
    }

    void saveOldPositions() {
        int n = this.mChildHelper.getUnfilteredChildCount();
        int n2 = 0;
        while (n2 < n) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(n2));
            if (!viewHolder.shouldIgnore()) {
                viewHolder.saveOldPosition();
            }
            ++n2;
        }
    }

    public void scrollBy(int n, int n2) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e((String)TAG, (String)"Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutSuppressed) {
            return;
        }
        boolean bl = layoutManager.canScrollHorizontally();
        boolean bl2 = this.mLayout.canScrollVertically();
        if (!bl) {
            if (!bl2) return;
        }
        if (!bl) {
            n = 0;
        }
        if (!bl2) {
            n2 = 0;
        }
        this.scrollByInternal(n, n2, null);
    }

    boolean scrollByInternal(int n, int n2, MotionEvent motionEvent) {
        int n3;
        int n4;
        int n5;
        int n6;
        this.consumePendingUpdateOperations();
        int[] arrn = this.mAdapter;
        boolean bl = true;
        if (arrn != null) {
            arrn = this.mReusableIntPair;
            arrn[0] = 0;
            arrn[1] = 0;
            this.scrollStep(n, n2, arrn);
            arrn = this.mReusableIntPair;
            n5 = arrn[0];
            n6 = n3 = arrn[1];
            n4 = n5;
            n5 = n - n5;
            n3 = n2 - n3;
        } else {
            n6 = 0;
            n4 = 0;
            n5 = 0;
            n3 = 0;
        }
        if (!this.mItemDecorations.isEmpty()) {
            this.invalidate();
        }
        arrn = this.mReusableIntPair;
        arrn[0] = 0;
        arrn[1] = 0;
        this.dispatchNestedScroll(n4, n6, n5, n3, this.mScrollOffset, 0, arrn);
        arrn = this.mReusableIntPair;
        int n7 = arrn[0];
        int n8 = arrn[1];
        boolean bl2 = arrn[0] != 0 || arrn[1] != 0;
        int n9 = this.mLastTouchX;
        arrn = this.mScrollOffset;
        this.mLastTouchX = n9 - arrn[0];
        this.mLastTouchY -= arrn[1];
        int[] arrn2 = this.mNestedOffsets;
        arrn2[0] = arrn2[0] + arrn[0];
        arrn2[1] = arrn2[1] + arrn[1];
        if (this.getOverScrollMode() != 2) {
            if (motionEvent != null && !MotionEventCompat.isFromSource(motionEvent, 8194)) {
                this.pullGlows(motionEvent.getX(), n5 - n7, motionEvent.getY(), n3 - n8);
            }
            this.considerReleasingGlowsOnScroll(n, n2);
        }
        if (n4 != 0 || n6 != 0) {
            this.dispatchOnScrolled(n4, n6);
        }
        if (!this.awakenScrollBars()) {
            this.invalidate();
        }
        boolean bl3 = bl;
        if (bl2) return bl3;
        bl3 = bl;
        if (n4 != 0) return bl3;
        if (n6 == 0) return false;
        return bl;
    }

    void scrollStep(int n, int n2, int[] arrn) {
        this.startInterceptRequestLayout();
        this.onEnterLayoutOrScroll();
        TraceCompat.beginSection(TRACE_SCROLL_TAG);
        this.fillRemainingScrollValues(this.mState);
        n = n != 0 ? this.mLayout.scrollHorizontallyBy(n, this.mRecycler, this.mState) : 0;
        n2 = n2 != 0 ? this.mLayout.scrollVerticallyBy(n2, this.mRecycler, this.mState) : 0;
        TraceCompat.endSection();
        this.repositionShadowingViews();
        this.onExitLayoutOrScroll();
        this.stopInterceptRequestLayout(false);
        if (arrn == null) return;
        arrn[0] = n;
        arrn[1] = n2;
    }

    public void scrollTo(int n, int n2) {
        Log.w((String)TAG, (String)"RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int n) {
        if (this.mLayoutSuppressed) {
            return;
        }
        this.stopScroll();
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e((String)TAG, (String)"Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        layoutManager.scrollToPosition(n);
        this.awakenScrollBars();
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (this.shouldDeferAccessibilityEvent(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.mAccessibilityDelegate = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate((View)this, recyclerViewAccessibilityDelegate);
    }

    public void setAdapter(Adapter adapter) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, false, true);
        this.processDataSetCompletelyChanged(false);
        this.requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.mChildDrawingOrderCallback) {
            return;
        }
        this.mChildDrawingOrderCallback = childDrawingOrderCallback;
        boolean bl = childDrawingOrderCallback != null;
        this.setChildrenDrawingOrderEnabled(bl);
    }

    boolean setChildImportantForAccessibilityInternal(ViewHolder viewHolder, int n) {
        if (this.isComputingLayout()) {
            viewHolder.mPendingAccessibilityState = n;
            this.mPendingAccessibilityImportanceChange.add(viewHolder);
            return false;
        }
        ViewCompat.setImportantForAccessibility(viewHolder.itemView, n);
        return true;
    }

    public void setClipToPadding(boolean bl) {
        if (bl != this.mClipToPadding) {
            this.invalidateGlows();
        }
        this.mClipToPadding = bl;
        super.setClipToPadding(bl);
        if (!this.mFirstLayoutComplete) return;
        this.requestLayout();
    }

    public void setEdgeEffectFactory(EdgeEffectFactory edgeEffectFactory) {
        Preconditions.checkNotNull(edgeEffectFactory);
        this.mEdgeEffectFactory = edgeEffectFactory;
        this.invalidateGlows();
    }

    public void setHasFixedSize(boolean bl) {
        this.mHasFixedSize = bl;
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        ItemAnimator itemAnimator2 = this.mItemAnimator;
        if (itemAnimator2 != null) {
            itemAnimator2.endAnimations();
            this.mItemAnimator.setListener(null);
        }
        this.mItemAnimator = itemAnimator;
        if (itemAnimator == null) return;
        itemAnimator.setListener(this.mItemAnimatorListener);
    }

    public void setItemViewCacheSize(int n) {
        this.mRecycler.setViewCacheSize(n);
    }

    @Deprecated
    public void setLayoutFrozen(boolean bl) {
        this.suppressLayout(bl);
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        Object object;
        if (layoutManager == this.mLayout) {
            return;
        }
        this.stopScroll();
        if (this.mLayout != null) {
            object = this.mItemAnimator;
            if (object != null) {
                ((ItemAnimator)object).endAnimations();
            }
            this.mLayout.removeAndRecycleAllViews(this.mRecycler);
            this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
            this.mRecycler.clear();
            if (this.mIsAttached) {
                this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler);
            }
            this.mLayout.setRecyclerView(null);
            this.mLayout = null;
        } else {
            this.mRecycler.clear();
        }
        this.mChildHelper.removeAllViewsUnfiltered();
        this.mLayout = layoutManager;
        if (layoutManager != null) {
            if (layoutManager.mRecyclerView != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("LayoutManager ");
                ((StringBuilder)object).append(layoutManager);
                ((StringBuilder)object).append(" is already attached to a RecyclerView:");
                ((StringBuilder)object).append(layoutManager.mRecyclerView.exceptionLabel());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.mLayout.setRecyclerView(this);
            if (this.mIsAttached) {
                this.mLayout.dispatchAttachedToWindow(this);
            }
        }
        this.mRecycler.updateViewCacheSize();
        this.requestLayout();
    }

    @Deprecated
    public void setLayoutTransition(LayoutTransition layoutTransition) {
        if (Build.VERSION.SDK_INT < 18) {
            if (layoutTransition == null) {
                this.suppressLayout(false);
                return;
            }
            if (layoutTransition.getAnimator(0) == null && layoutTransition.getAnimator(1) == null && layoutTransition.getAnimator(2) == null && layoutTransition.getAnimator(3) == null && layoutTransition.getAnimator(4) == null) {
                this.suppressLayout(true);
                return;
            }
        }
        if (layoutTransition != null) throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
        super.setLayoutTransition(null);
    }

    @Override
    public void setNestedScrollingEnabled(boolean bl) {
        this.getScrollingChildHelper().setNestedScrollingEnabled(bl);
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        this.mOnFlingListener = onFlingListener;
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mScrollListener = onScrollListener;
    }

    public void setPreserveFocusAfterLayout(boolean bl) {
        this.mPreserveFocusAfterLayout = bl;
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        this.mRecycler.setRecycledViewPool(recycledViewPool);
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.mRecyclerListener = recyclerListener;
    }

    void setScrollState(int n) {
        if (n == this.mScrollState) {
            return;
        }
        this.mScrollState = n;
        if (n != 2) {
            this.stopScrollersInternal();
        }
        this.dispatchOnScrollStateChanged(n);
    }

    public void setScrollingTouchSlop(int n) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        if (n != 0) {
            if (n == 1) {
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setScrollingTouchSlop(): bad argument constant ");
            stringBuilder.append(n);
            stringBuilder.append("; using default value");
            Log.w((String)TAG, (String)stringBuilder.toString());
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.mRecycler.setViewCacheExtension(viewCacheExtension);
    }

    boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        boolean bl = this.isComputingLayout();
        int n = 0;
        if (!bl) return false;
        int n2 = accessibilityEvent != null ? AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent) : 0;
        if (n2 == 0) {
            n2 = n;
        }
        this.mEatenAccessibilityChangeFlags |= n2;
        return true;
    }

    public void smoothScrollBy(int n, int n2) {
        this.smoothScrollBy(n, n2, null);
    }

    public void smoothScrollBy(int n, int n2, Interpolator interpolator2) {
        this.smoothScrollBy(n, n2, interpolator2, Integer.MIN_VALUE);
    }

    public void smoothScrollBy(int n, int n2, Interpolator interpolator2, int n3) {
        this.smoothScrollBy(n, n2, interpolator2, n3, false);
    }

    void smoothScrollBy(int n, int n2, Interpolator interpolator2, int n3, boolean bl) {
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e((String)TAG, (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.mLayoutSuppressed) {
            return;
        }
        boolean bl2 = layoutManager.canScrollHorizontally();
        int n4 = 0;
        int n5 = n;
        if (!bl2) {
            n5 = 0;
        }
        if (!this.mLayout.canScrollVertically()) {
            n2 = 0;
        }
        if (n5 == 0) {
            if (n2 == 0) return;
        }
        n = n3 != Integer.MIN_VALUE && n3 <= 0 ? 0 : 1;
        if (n == 0) {
            this.scrollBy(n5, n2);
            return;
        }
        if (bl) {
            n = n4;
            if (n5 != 0) {
                n = 1;
            }
            n4 = n;
            if (n2 != 0) {
                n4 = n | 2;
            }
            this.startNestedScroll(n4, 1);
        }
        this.mViewFlinger.smoothScrollBy(n5, n2, n3, interpolator2);
    }

    public void smoothScrollToPosition(int n) {
        if (this.mLayoutSuppressed) {
            return;
        }
        LayoutManager layoutManager = this.mLayout;
        if (layoutManager == null) {
            Log.e((String)TAG, (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        layoutManager.smoothScrollToPosition(this, this.mState, n);
    }

    void startInterceptRequestLayout() {
        int n;
        this.mInterceptRequestLayoutDepth = n = this.mInterceptRequestLayoutDepth + 1;
        if (n != 1) return;
        if (this.mLayoutSuppressed) return;
        this.mLayoutWasDefered = false;
    }

    @Override
    public boolean startNestedScroll(int n) {
        return this.getScrollingChildHelper().startNestedScroll(n);
    }

    @Override
    public boolean startNestedScroll(int n, int n2) {
        return this.getScrollingChildHelper().startNestedScroll(n, n2);
    }

    void stopInterceptRequestLayout(boolean bl) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!bl && !this.mLayoutSuppressed) {
            this.mLayoutWasDefered = false;
        }
        if (this.mInterceptRequestLayoutDepth == 1) {
            if (bl && this.mLayoutWasDefered && !this.mLayoutSuppressed && this.mLayout != null && this.mAdapter != null) {
                this.dispatchLayout();
            }
            if (!this.mLayoutSuppressed) {
                this.mLayoutWasDefered = false;
            }
        }
        --this.mInterceptRequestLayoutDepth;
    }

    @Override
    public void stopNestedScroll() {
        this.getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int n) {
        this.getScrollingChildHelper().stopNestedScroll(n);
    }

    public void stopScroll() {
        this.setScrollState(0);
        this.stopScrollersInternal();
    }

    public final void suppressLayout(boolean bl) {
        if (bl == this.mLayoutSuppressed) return;
        this.assertNotInLayoutOrScroll("Do not suppressLayout in layout or scroll");
        if (bl) {
            long l = SystemClock.uptimeMillis();
            this.onTouchEvent(MotionEvent.obtain((long)l, (long)l, (int)3, (float)0.0f, (float)0.0f, (int)0));
            this.mLayoutSuppressed = true;
            this.mIgnoreMotionEventTillDown = true;
            this.stopScroll();
            return;
        }
        this.mLayoutSuppressed = false;
        if (this.mLayoutWasDefered && this.mLayout != null && this.mAdapter != null) {
            this.requestLayout();
        }
        this.mLayoutWasDefered = false;
    }

    public void swapAdapter(Adapter adapter, boolean bl) {
        this.setLayoutFrozen(false);
        this.setAdapterInternal(adapter, true, bl);
        this.processDataSetCompletelyChanged(true);
        this.requestLayout();
    }

    void viewRangeUpdate(int n, int n2, Object object) {
        int n3 = this.mChildHelper.getUnfilteredChildCount();
        int n4 = 0;
        do {
            if (n4 >= n3) {
                this.mRecycler.viewRangeUpdate(n, n2);
                return;
            }
            View view = this.mChildHelper.getUnfilteredChildAt(n4);
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.mPosition >= n && viewHolder.mPosition < n + n2) {
                viewHolder.addFlags(2);
                viewHolder.addChangePayload(object);
                ((LayoutParams)view.getLayoutParams()).mInsetsDirty = true;
            }
            ++n4;
        } while (true);
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        private boolean mHasStableIds = false;
        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        public final void bindViewHolder(VH object, int n) {
            ((ViewHolder)object).mPosition = n;
            if (this.hasStableIds()) {
                ((ViewHolder)object).mItemId = this.getItemId(n);
            }
            ((ViewHolder)object).setFlags(1, 519);
            TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
            this.onBindViewHolder(object, n, ((ViewHolder)object).getUnmodifiedPayloads());
            ((ViewHolder)object).clearPayload();
            object = ((ViewHolder)object).itemView.getLayoutParams();
            if (object instanceof LayoutParams) {
                ((LayoutParams)object).mInsetsDirty = true;
            }
            TraceCompat.endSection();
        }

        public final VH createViewHolder(ViewGroup object, int n) {
            try {
                TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
                object = this.onCreateViewHolder((ViewGroup)object, n);
                if (object.itemView.getParent() == null) {
                    object.mItemViewType = n;
                    return (VH)object;
                }
                object = new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                throw object;
            }
            finally {
                TraceCompat.endSection();
            }
        }

        public abstract int getItemCount();

        public long getItemId(int n) {
            return -1L;
        }

        public int getItemViewType(int n) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.hasObservers();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.notifyChanged();
        }

        public final void notifyItemChanged(int n) {
            this.mObservable.notifyItemRangeChanged(n, 1);
        }

        public final void notifyItemChanged(int n, Object object) {
            this.mObservable.notifyItemRangeChanged(n, 1, object);
        }

        public final void notifyItemInserted(int n) {
            this.mObservable.notifyItemRangeInserted(n, 1);
        }

        public final void notifyItemMoved(int n, int n2) {
            this.mObservable.notifyItemMoved(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2) {
            this.mObservable.notifyItemRangeChanged(n, n2);
        }

        public final void notifyItemRangeChanged(int n, int n2, Object object) {
            this.mObservable.notifyItemRangeChanged(n, n2, object);
        }

        public final void notifyItemRangeInserted(int n, int n2) {
            this.mObservable.notifyItemRangeInserted(n, n2);
        }

        public final void notifyItemRangeRemoved(int n, int n2) {
            this.mObservable.notifyItemRangeRemoved(n, n2);
        }

        public final void notifyItemRemoved(int n) {
            this.mObservable.notifyItemRangeRemoved(n, 1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH var1, int var2);

        public void onBindViewHolder(VH VH, int n, List<Object> list) {
            this.onBindViewHolder(VH, n);
        }

        public abstract VH onCreateViewHolder(ViewGroup var1, int var2);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH VH) {
            return false;
        }

        public void onViewAttachedToWindow(VH VH) {
        }

        public void onViewDetachedFromWindow(VH VH) {
        }

        public void onViewRecycled(VH VH) {
        }

        public void registerAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.registerObserver((Object)adapterDataObserver);
        }

        public void setHasStableIds(boolean bl) {
            if (this.hasObservers()) throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            this.mHasStableIds = bl;
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver adapterDataObserver) {
            this.mObservable.unregisterObserver((Object)adapterDataObserver);
        }
    }

    static class AdapterDataObservable
    extends Observable<AdapterDataObserver> {
        AdapterDataObservable() {
        }

        public boolean hasObservers() {
            return this.mObservers.isEmpty() ^ true;
        }

        public void notifyChanged() {
            int n = this.mObservers.size() - 1;
            while (n >= 0) {
                ((AdapterDataObserver)this.mObservers.get(n)).onChanged();
                --n;
            }
        }

        public void notifyItemMoved(int n, int n2) {
            int n3 = this.mObservers.size() - 1;
            while (n3 >= 0) {
                ((AdapterDataObserver)this.mObservers.get(n3)).onItemRangeMoved(n, n2, 1);
                --n3;
            }
        }

        public void notifyItemRangeChanged(int n, int n2) {
            this.notifyItemRangeChanged(n, n2, null);
        }

        public void notifyItemRangeChanged(int n, int n2, Object object) {
            int n3 = this.mObservers.size() - 1;
            while (n3 >= 0) {
                ((AdapterDataObserver)this.mObservers.get(n3)).onItemRangeChanged(n, n2, object);
                --n3;
            }
        }

        public void notifyItemRangeInserted(int n, int n2) {
            int n3 = this.mObservers.size() - 1;
            while (n3 >= 0) {
                ((AdapterDataObserver)this.mObservers.get(n3)).onItemRangeInserted(n, n2);
                --n3;
            }
        }

        public void notifyItemRangeRemoved(int n, int n2) {
            int n3 = this.mObservers.size() - 1;
            while (n3 >= 0) {
                ((AdapterDataObserver)this.mObservers.get(n3)).onItemRangeRemoved(n, n2);
                --n3;
            }
        }
    }

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int n, int n2) {
        }

        public void onItemRangeChanged(int n, int n2, Object object) {
            this.onItemRangeChanged(n, n2);
        }

        public void onItemRangeInserted(int n, int n2) {
        }

        public void onItemRangeMoved(int n, int n2, int n3) {
        }

        public void onItemRangeRemoved(int n, int n2) {
        }
    }

    public static interface ChildDrawingOrderCallback {
        public int onGetChildDrawingOrder(int var1, int var2);
    }

    public static class EdgeEffectFactory {
        public static final int DIRECTION_BOTTOM = 3;
        public static final int DIRECTION_LEFT = 0;
        public static final int DIRECTION_RIGHT = 2;
        public static final int DIRECTION_TOP = 1;

        protected EdgeEffect createEdgeEffect(RecyclerView recyclerView, int n) {
            return new EdgeEffect(recyclerView.getContext());
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface EdgeDirection {
        }

    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        private long mAddDuration = 120L;
        private long mChangeDuration = 250L;
        private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList();
        private ItemAnimatorListener mListener = null;
        private long mMoveDuration = 250L;
        private long mRemoveDuration = 120L;

        static int buildAdapterChangeFlagsForAnimations(ViewHolder viewHolder) {
            int n = viewHolder.mFlags & 14;
            if (viewHolder.isInvalid()) {
                return 4;
            }
            int n2 = n;
            if ((n & 4) != 0) return n2;
            int n3 = viewHolder.getOldPosition();
            int n4 = viewHolder.getAdapterPosition();
            n2 = n;
            if (n3 == -1) return n2;
            n2 = n;
            if (n4 == -1) return n2;
            n2 = n;
            if (n3 == n4) return n2;
            return n | 2048;
        }

        public abstract boolean animateAppearance(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public abstract boolean animateChange(ViewHolder var1, ViewHolder var2, ItemHolderInfo var3, ItemHolderInfo var4);

        public abstract boolean animateDisappearance(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public abstract boolean animatePersistence(ViewHolder var1, ItemHolderInfo var2, ItemHolderInfo var3);

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(ViewHolder viewHolder, List<Object> list) {
            return this.canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            this.onAnimationFinished(viewHolder);
            ItemAnimatorListener itemAnimatorListener = this.mListener;
            if (itemAnimatorListener == null) return;
            itemAnimatorListener.onAnimationFinished(viewHolder);
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            this.onAnimationStarted(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int n = this.mFinishedListeners.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mFinishedListeners.clear();
                    return;
                }
                this.mFinishedListeners.get(n2).onAnimationsFinished();
                ++n2;
            } while (true);
        }

        public abstract void endAnimation(ViewHolder var1);

        public abstract void endAnimations();

        public long getAddDuration() {
            return this.mAddDuration;
        }

        public long getChangeDuration() {
            return this.mChangeDuration;
        }

        public long getMoveDuration() {
            return this.mMoveDuration;
        }

        public long getRemoveDuration() {
            return this.mRemoveDuration;
        }

        public abstract boolean isRunning();

        public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean bl = this.isRunning();
            if (itemAnimatorFinishedListener == null) return bl;
            if (!bl) {
                itemAnimatorFinishedListener.onAnimationsFinished();
                return bl;
            }
            this.mFinishedListeners.add(itemAnimatorFinishedListener);
            return bl;
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
        }

        public ItemHolderInfo recordPostLayoutInformation(State state, ViewHolder viewHolder) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public ItemHolderInfo recordPreLayoutInformation(State state, ViewHolder viewHolder, int n, List<Object> list) {
            return this.obtainHolderInfo().setFrom(viewHolder);
        }

        public abstract void runPendingAnimations();

        public void setAddDuration(long l) {
            this.mAddDuration = l;
        }

        public void setChangeDuration(long l) {
            this.mChangeDuration = l;
        }

        void setListener(ItemAnimatorListener itemAnimatorListener) {
            this.mListener = itemAnimatorListener;
        }

        public void setMoveDuration(long l) {
            this.mMoveDuration = l;
        }

        public void setRemoveDuration(long l) {
            this.mRemoveDuration = l;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface AdapterChanges {
        }

        public static interface ItemAnimatorFinishedListener {
            public void onAnimationsFinished();
        }

        static interface ItemAnimatorListener {
            public void onAnimationFinished(ViewHolder var1);
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                return this.setFrom(viewHolder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder, int n) {
                viewHolder = viewHolder.itemView;
                this.left = viewHolder.getLeft();
                this.top = viewHolder.getTop();
                this.right = viewHolder.getRight();
                this.bottom = viewHolder.getBottom();
                return this;
            }
        }

    }

    private class ItemAnimatorRestoreListener
    implements ItemAnimator.ItemAnimatorListener {
        ItemAnimatorRestoreListener() {
        }

        @Override
        public void onAnimationFinished(ViewHolder viewHolder) {
            viewHolder.setIsRecyclable(true);
            if (viewHolder.mShadowedHolder != null && viewHolder.mShadowingHolder == null) {
                viewHolder.mShadowedHolder = null;
            }
            viewHolder.mShadowingHolder = null;
            if (viewHolder.shouldBeKeptAsChild()) return;
            if (RecyclerView.this.removeAnimatingView(viewHolder.itemView)) return;
            if (!viewHolder.isTmpDetached()) return;
            RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(Rect rect, int n, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            this.getItemOffsets(rect, ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDraw(canvas, recyclerView);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            this.onDrawOver(canvas, recyclerView);
        }
    }

    public static abstract class LayoutManager {
        boolean mAutoMeasure = false;
        ChildHelper mChildHelper;
        private int mHeight;
        private int mHeightMode;
        ViewBoundsCheck mHorizontalBoundCheck = new ViewBoundsCheck(this.mHorizontalBoundCheckCallback);
        private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback = new ViewBoundsCheck.Callback(){

            @Override
            public View getChildAt(int n) {
                return LayoutManager.this.getChildAt(n);
            }

            @Override
            public int getChildEnd(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedRight(view) + layoutParams.rightMargin;
            }

            @Override
            public int getChildStart(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedLeft(view) - layoutParams.leftMargin;
            }

            @Override
            public int getParentEnd() {
                return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
            }

            @Override
            public int getParentStart() {
                return LayoutManager.this.getPaddingLeft();
            }
        };
        boolean mIsAttachedToWindow = false;
        private boolean mItemPrefetchEnabled = true;
        private boolean mMeasurementCacheEnabled = true;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations = false;
        SmoothScroller mSmoothScroller;
        ViewBoundsCheck mVerticalBoundCheck = new ViewBoundsCheck(this.mVerticalBoundCheckCallback);
        private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback = new ViewBoundsCheck.Callback(){

            @Override
            public View getChildAt(int n) {
                return LayoutManager.this.getChildAt(n);
            }

            @Override
            public int getChildEnd(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedBottom(view) + layoutParams.bottomMargin;
            }

            @Override
            public int getChildStart(View view) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                return LayoutManager.this.getDecoratedTop(view) - layoutParams.topMargin;
            }

            @Override
            public int getParentEnd() {
                return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
            }

            @Override
            public int getParentStart() {
                return LayoutManager.this.getPaddingTop();
            }
        };
        private int mWidth;
        private int mWidthMode;

        private void addViewInt(View view, int n, boolean bl) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (!bl && !viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            }
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (!viewHolder.wasReturnedFromScrap() && !viewHolder.isScrap()) {
                if (view.getParent() == this.mRecyclerView) {
                    int n2 = this.mChildHelper.indexOfChild(view);
                    int n3 = n;
                    if (n == -1) {
                        n3 = this.mChildHelper.getChildCount();
                    }
                    if (n2 == -1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                        stringBuilder.append(this.mRecyclerView.indexOfChild(view));
                        stringBuilder.append(this.mRecyclerView.exceptionLabel());
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                    if (n2 != n3) {
                        this.mRecyclerView.mLayout.moveView(n2, n3);
                    }
                } else {
                    this.mChildHelper.addView(view, n, false);
                    layoutParams.mInsetsDirty = true;
                    SmoothScroller smoothScroller = this.mSmoothScroller;
                    if (smoothScroller != null && smoothScroller.isRunning()) {
                        this.mSmoothScroller.onChildAttachedToWindow(view);
                    }
                }
            } else {
                if (viewHolder.isScrap()) {
                    viewHolder.unScrap();
                } else {
                    viewHolder.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.attachViewToParent(view, n, view.getLayoutParams(), false);
            }
            if (!layoutParams.mPendingInvalidate) return;
            viewHolder.itemView.invalidate();
            layoutParams.mPendingInvalidate = false;
        }

        public static int chooseSize(int n, int n2, int n3) {
            int n4 = View.MeasureSpec.getMode((int)n);
            n = View.MeasureSpec.getSize((int)n);
            if (n4 == Integer.MIN_VALUE) return Math.min(n, Math.max(n2, n3));
            if (n4 == 1073741824) return n;
            return Math.max(n2, n3);
        }

        private void detachViewInternal(int n, View view) {
            this.mChildHelper.detachViewFromParent(n);
        }

        public static int getChildMeasureSpec(int n, int n2, int n3, int n4, boolean bl) {
            block8 : {
                block9 : {
                    block11 : {
                        block10 : {
                            block7 : {
                                block6 : {
                                    n3 = Math.max(0, n - n3);
                                    if (!bl) break block6;
                                    if (n4 >= 0) break block7;
                                    if (n4 != -1) break block8;
                                    n = n2;
                                    if (n2 == Integer.MIN_VALUE) break block9;
                                    if (n2 == 0) break block8;
                                    n = n2;
                                    if (n2 == 1073741824) break block9;
                                    break block8;
                                }
                                if (n4 < 0) break block10;
                            }
                            n = 1073741824;
                            return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n);
                        }
                        if (n4 != -1) break block11;
                        n = n2;
                        break block9;
                    }
                    if (n4 == -2) {
                        n = n2 != Integer.MIN_VALUE && n2 != 1073741824 ? 0 : Integer.MIN_VALUE;
                    }
                    break block8;
                }
                n4 = n3;
                return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n);
            }
            n = 0;
            n4 = 0;
            return View.MeasureSpec.makeMeasureSpec((int)n4, (int)n);
        }

        @Deprecated
        public static int getChildMeasureSpec(int n, int n2, int n3, boolean bl) {
            block9 : {
                block10 : {
                    int n4;
                    block5 : {
                        block8 : {
                            block7 : {
                                block6 : {
                                    block4 : {
                                        n4 = 0;
                                        n2 = Math.max(0, n - n2);
                                        if (!bl) break block4;
                                        if (n3 < 0) break block5;
                                        break block6;
                                    }
                                    if (n3 < 0) break block7;
                                }
                                n = 1073741824;
                                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                            }
                            if (n3 != -1) break block8;
                            n = 1073741824;
                            break block9;
                        }
                        if (n3 == -2) break block10;
                    }
                    n3 = 0;
                    n = n4;
                    return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                }
                n = Integer.MIN_VALUE;
            }
            n3 = n2;
            return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
        }

        private int[] getChildRectangleOnScreenScrollAmount(View view, Rect rect) {
            int n = this.getPaddingLeft();
            int n2 = this.getPaddingTop();
            int n3 = this.getWidth();
            int n4 = this.getPaddingRight();
            int n5 = this.getHeight();
            int n6 = this.getPaddingBottom();
            int n7 = view.getLeft() + rect.left - view.getScrollX();
            int n8 = view.getTop() + rect.top - view.getScrollY();
            int n9 = rect.width();
            int n10 = rect.height();
            int n11 = n7 - n;
            n = Math.min(0, n11);
            int n12 = n8 - n2;
            n2 = Math.min(0, n12);
            n9 = n9 + n7 - (n3 - n4);
            n4 = Math.max(0, n9);
            n10 = Math.max(0, n10 + n8 - (n5 - n6));
            if (this.getLayoutDirection() == 1) {
                n = n4 != 0 ? n4 : Math.max(n, n9);
            } else if (n == 0) {
                n = Math.min(n11, n4);
            }
            if (n2 != 0) return new int[]{n, n2};
            n2 = Math.min(n12, n10);
            return new int[]{n, n2};
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int n, int n2) {
            Properties properties = new Properties();
            context = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, n2);
            properties.orientation = context.getInt(R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = context.getInt(R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = context.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = context.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
            context.recycle();
            return properties;
        }

        private boolean isFocusedChildVisibleAfterScrolling(RecyclerView recyclerView, int n, int n2) {
            if ((recyclerView = recyclerView.getFocusedChild()) == null) {
                return false;
            }
            int n3 = this.getPaddingLeft();
            int n4 = this.getPaddingTop();
            int n5 = this.getWidth();
            int n6 = this.getPaddingRight();
            int n7 = this.getHeight();
            int n8 = this.getPaddingBottom();
            Rect rect = this.mRecyclerView.mTempRect;
            this.getDecoratedBoundsWithMargins((View)recyclerView, rect);
            if (rect.left - n >= n5 - n6) return false;
            if (rect.right - n <= n3) return false;
            if (rect.top - n2 >= n7 - n8) return false;
            if (rect.bottom - n2 > n4) return true;
            return false;
        }

        private static boolean isMeasurementUpToDate(int n, int n2, int n3) {
            int n4 = View.MeasureSpec.getMode((int)n2);
            n2 = View.MeasureSpec.getSize((int)n2);
            boolean bl = false;
            boolean bl2 = false;
            if (n3 > 0 && n != n3) {
                return false;
            }
            if (n4 == Integer.MIN_VALUE) {
                bl2 = bl;
                if (n2 < n) return bl2;
                return true;
            }
            if (n4 == 0) return true;
            if (n4 != 1073741824) {
                return false;
            }
            if (n2 != n) return bl2;
            return true;
        }

        private void scrapOrRecycleView(Recycler recycler, int n, View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.shouldIgnore()) {
                return;
            }
            if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
                this.removeViewAt(n);
                recycler.recycleViewHolderInternal(viewHolder);
                return;
            }
            this.detachViewAt(n);
            recycler.scrapView(view);
            this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
        }

        public void addDisappearingView(View view) {
            this.addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int n) {
            this.addViewInt(view, n, true);
        }

        public void addView(View view) {
            this.addView(view, -1);
        }

        public void addView(View view, int n) {
            this.addViewInt(view, n, false);
        }

        public void assertInLayoutOrScroll(String string2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return;
            recyclerView.assertInLayoutOrScroll(string2);
        }

        public void assertNotInLayoutOrScroll(String string2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return;
            recyclerView.assertNotInLayoutOrScroll(string2);
        }

        public void attachView(View view) {
            this.attachView(view, -1);
        }

        public void attachView(View view, int n) {
            this.attachView(view, n, (LayoutParams)view.getLayoutParams());
        }

        public void attachView(View view, int n, LayoutParams layoutParams) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isRemoved()) {
                this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
            } else {
                this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
            }
            this.mChildHelper.attachViewToParent(view, n, (ViewGroup.LayoutParams)layoutParams, viewHolder.isRemoved());
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
                return;
            }
            rect.set(recyclerView.getItemDecorInsetsForChild(view));
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            if (layoutParams == null) return false;
            return true;
        }

        public void collectAdjacentPrefetchPositions(int n, int n2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int n, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            int n = this.getChildCount() - 1;
            while (n >= 0) {
                this.scrapOrRecycleView(recycler, n, this.getChildAt(n));
                --n;
            }
        }

        public void detachAndScrapView(View view, Recycler recycler) {
            this.scrapOrRecycleView(recycler, this.mChildHelper.indexOfChild(view), view);
        }

        public void detachAndScrapViewAt(int n, Recycler recycler) {
            this.scrapOrRecycleView(recycler, n, this.getChildAt(n));
        }

        public void detachView(View view) {
            int n = this.mChildHelper.indexOfChild(view);
            if (n < 0) return;
            this.detachViewInternal(n, view);
        }

        public void detachViewAt(int n) {
            this.detachViewInternal(n, this.getChildAt(n));
        }

        void dispatchAttachedToWindow(RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            this.onAttachedToWindow(recyclerView);
        }

        void dispatchDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.mIsAttachedToWindow = false;
            this.onDetachedFromWindow(recyclerView, recycler);
        }

        public void endAnimation(View view) {
            if (this.mRecyclerView.mItemAnimator == null) return;
            this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(view));
        }

        public View findContainingItemView(View view) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                return null;
            }
            if ((view = recyclerView.findContainingItemView(view)) == null) {
                return null;
            }
            if (!this.mChildHelper.isHidden(view)) return view;
            return null;
        }

        public View findViewByPosition(int n) {
            int n2 = this.getChildCount();
            int n3 = 0;
            while (n3 < n2) {
                View view = this.getChildAt(n3);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (viewHolder != null && viewHolder.getLayoutPosition() == n && !viewHolder.shouldIgnore()) {
                    if (this.mRecyclerView.mState.isPreLayout()) return view;
                    if (!viewHolder.isRemoved()) {
                        return view;
                    }
                }
                ++n3;
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) return new LayoutParams(layoutParams);
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.bottom;
        }

        public View getChildAt(int n) {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper == null) return null;
            return childHelper.getChildAt(n);
        }

        public int getChildCount() {
            ChildHelper childHelper = this.mChildHelper;
            if (childHelper == null) return 0;
            return childHelper.getChildCount();
        }

        public boolean getClipToPadding() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return false;
            if (!recyclerView.mClipToPadding) return false;
            return true;
        }

        public int getColumnCountForAccessibility(Recycler object, State state) {
            int n;
            object = this.mRecyclerView;
            int n2 = n = 1;
            if (object == null) return n2;
            if (((RecyclerView)object).mAdapter == null) {
                return n;
            }
            n2 = n;
            if (!this.canScrollHorizontally()) return n2;
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + this.getBottomDecorationHeight(view);
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - this.getLeftDecorationWidth(view);
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + this.getRightDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - this.getTopDecorationHeight(view);
        }

        public View getFocusedChild() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                return null;
            }
            if ((recyclerView = recyclerView.getFocusedChild()) == null) return null;
            if (!this.mChildHelper.isHidden((View)recyclerView)) return recyclerView;
            return null;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getItemCount() {
            Object object = this.mRecyclerView;
            if (object == null) return 0;
            object = ((RecyclerView)object).getAdapter();
            if (object == null) return 0;
            return ((Adapter)object).getItemCount();
        }

        public int getItemViewType(View view) {
            return RecyclerView.getChildViewHolderInt(view).getItemViewType();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection((View)this.mRecyclerView);
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.left;
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight((View)this.mRecyclerView);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth((View)this.mRecyclerView);
        }

        public int getPaddingBottom() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return 0;
            return recyclerView.getPaddingBottom();
        }

        public int getPaddingEnd() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return 0;
            return ViewCompat.getPaddingEnd((View)recyclerView);
        }

        public int getPaddingLeft() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return 0;
            return recyclerView.getPaddingLeft();
        }

        public int getPaddingRight() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return 0;
            return recyclerView.getPaddingRight();
        }

        public int getPaddingStart() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return 0;
            return ViewCompat.getPaddingStart((View)recyclerView);
        }

        public int getPaddingTop() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return 0;
            return recyclerView.getPaddingTop();
        }

        public int getPosition(View view) {
            return ((LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.right;
        }

        public int getRowCountForAccessibility(Recycler object, State state) {
            int n;
            object = this.mRecyclerView;
            int n2 = n = 1;
            if (object == null) return n2;
            if (((RecyclerView)object).mAdapter == null) {
                return n;
            }
            n2 = n;
            if (!this.canScrollVertically()) return n2;
            return this.mRecyclerView.mAdapter.getItemCount();
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams)view.getLayoutParams()).mDecorInsets.top;
        }

        public void getTransformedBoundingBox(View view, boolean bl, Rect rect) {
            Matrix matrix;
            Rect rect2;
            if (bl) {
                rect2 = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.mRecyclerView != null && (matrix = view.getMatrix()) != null && !matrix.isIdentity()) {
                rect2 = this.mRecyclerView.mTempRectF;
                rect2.set(rect);
                matrix.mapRect((RectF)rect2);
                rect.set((int)Math.floor(rect2.left), (int)Math.floor(rect2.top), (int)Math.ceil(rect2.right), (int)Math.ceil(rect2.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        boolean hasFlexibleChildInBothOrientations() {
            int n = this.getChildCount();
            int n2 = 0;
            while (n2 < n) {
                ViewGroup.LayoutParams layoutParams = this.getChildAt(n2).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
                ++n2;
            }
            return false;
        }

        public boolean hasFocus() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return false;
            if (!recyclerView.hasFocus()) return false;
            return true;
        }

        public void ignoreView(View object) {
            RecyclerView recyclerView;
            ViewParent viewParent = object.getParent();
            if (viewParent == (recyclerView = this.mRecyclerView) && recyclerView.indexOfChild((View)object) != -1) {
                object = RecyclerView.getChildViewHolderInt((View)object);
                ((ViewHolder)object).addFlags(128);
                this.mRecyclerView.mViewInfoStore.removeViewHolder((ViewHolder)object);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("View should be fully attached to be ignored");
            ((StringBuilder)object).append(this.mRecyclerView.exceptionLabel());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean isFocused() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return false;
            if (!recyclerView.isFocused()) return false;
            return true;
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public boolean isSmoothScrolling() {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            if (smoothScroller == null) return false;
            if (!smoothScroller.isRunning()) return false;
            return true;
        }

        public boolean isViewPartiallyVisible(View view, boolean bl, boolean bl2) {
            bl2 = this.mHorizontalBoundCheck.isViewWithinBoundFlags(view, 24579) && this.mVerticalBoundCheck.isViewWithinBoundFlags(view, 24579);
            if (!bl) return bl2 ^ true;
            return bl2;
        }

        public void layoutDecorated(View view, int n, int n2, int n3, int n4) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).mDecorInsets;
            view.layout(n + rect.left, n2 + rect.top, n3 - rect.right, n4 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(View view, int n, int n2, int n3, int n4) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = layoutParams.mDecorInsets;
            view.layout(n + rect.left + layoutParams.leftMargin, n2 + rect.top + layoutParams.topMargin, n3 - rect.right - layoutParams.rightMargin, n4 - rect.bottom - layoutParams.bottomMargin);
        }

        public void measureChild(View view, int n, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n3 = rect.left;
            int n4 = rect.right;
            int n5 = rect.top;
            int n6 = rect.bottom;
            n = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
            if (!this.shouldMeasureChild(view, n, n2 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) return;
            view.measure(n, n2);
        }

        public void measureChildWithMargins(View view, int n, int n2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
            int n3 = rect.left;
            int n4 = rect.right;
            int n5 = rect.top;
            int n6 = rect.bottom;
            n = LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
            if (!this.shouldMeasureChild(view, n, n2 = LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) return;
            view.measure(n, n2);
        }

        public void moveView(int n, int n2) {
            Object object = this.getChildAt(n);
            if (object != null) {
                this.detachViewAt(n);
                this.attachView((View)object, n2);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot move a child from non-existing index:");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(this.mRecyclerView.toString());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public void offsetChildrenHorizontal(int n) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return;
            recyclerView.offsetChildrenHorizontal(n);
        }

        public void offsetChildrenVertical(int n) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return;
            recyclerView.offsetChildrenVertical(n);
        }

        public void onAdapterChanged(Adapter adapter, Adapter adapter2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int n, int n2) {
            return false;
        }

        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            this.onDetachedFromWindow(recyclerView);
        }

        public View onFocusSearchFailed(View view, int n, Recycler recycler, State state) {
            return null;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            this.onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(Recycler object, State state, AccessibilityEvent accessibilityEvent) {
            boolean bl;
            object = this.mRecyclerView;
            if (object == null) return;
            if (accessibilityEvent == null) {
                return;
            }
            boolean bl2 = bl = true;
            if (!object.canScrollVertically(1)) {
                bl2 = bl;
                if (!this.mRecyclerView.canScrollVertically(-1)) {
                    bl2 = bl;
                    if (!this.mRecyclerView.canScrollHorizontally(-1)) {
                        bl2 = this.mRecyclerView.canScrollHorizontally(1) ? bl : false;
                    }
                }
            }
            accessibilityEvent.setScrollable(bl2);
            if (this.mRecyclerView.mAdapter == null) return;
            accessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
        }

        void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfoCompat);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(this.getRowCountForAccessibility(recycler, state), this.getColumnCountForAccessibility(recycler, state), this.isLayoutHierarchical(recycler, state), this.getSelectionModeForAccessibility(recycler, state)));
        }

        void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder == null) return;
            if (viewHolder.isRemoved()) return;
            if (this.mChildHelper.isHidden(viewHolder.itemView)) return;
            this.onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfoCompat);
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int n = this.canScrollVertically() ? this.getPosition(view) : 0;
            int n2 = this.canScrollHorizontally() ? this.getPosition(view) : 0;
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n, 1, n2, 1, false, false));
        }

        public View onInterceptFocusSearch(View view, int n) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
            this.onItemsUpdated(recyclerView, n, n2);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e((String)"RecyclerView", (String)"You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
        }

        public void onMeasure(Recycler recycler, State state, int n, int n2) {
            this.mRecyclerView.defaultOnMeasure(n, n2);
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            if (this.isSmoothScrolling()) return true;
            if (recyclerView.isComputingLayout()) return true;
            return false;
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view, View view2) {
            return this.onRequestChildFocus(recyclerView, view, view2);
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int n) {
        }

        void onSmoothScrollerStopped(SmoothScroller smoothScroller) {
            if (this.mSmoothScroller != smoothScroller) return;
            this.mSmoothScroller = null;
        }

        boolean performAccessibilityAction(int n, Bundle bundle) {
            return this.performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, n, bundle);
        }

        /*
         * Unable to fully structure code
         */
        public boolean performAccessibilityAction(Recycler var1_1, State var2_2, int var3_3, Bundle var4_4) {
            block5 : {
                var1_1 = this.mRecyclerView;
                if (var1_1 == null) {
                    return false;
                }
                if (var3_3 == 4096) break block5;
                if (var3_3 != 8192) {
                    return false;
                }
                var3_3 = var1_1.canScrollVertically(-1) != false ? -(this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()) : 0;
                var5_5 = var3_3;
                if (!this.mRecyclerView.canScrollHorizontally(-1)) ** GOTO lbl-1000
                var5_5 = -(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
                ** GOTO lbl17
            }
            var3_3 = var1_1.canScrollVertically(1) != false ? this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() : 0;
            var5_5 = var3_3;
            if (this.mRecyclerView.canScrollHorizontally(1)) {
                var5_5 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
lbl17: // 2 sources:
                var6_6 = var3_3;
                var3_3 = var5_5;
                var5_5 = var6_6;
            } else lbl-1000: // 2 sources:
            {
                var3_3 = 0;
            }
            if (var5_5 == 0 && var3_3 == 0) {
                return false;
            }
            this.mRecyclerView.smoothScrollBy(var3_3, var5_5, null, Integer.MIN_VALUE, true);
            return true;
        }

        boolean performAccessibilityActionForItem(View view, int n, Bundle bundle) {
            return this.performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, n, bundle);
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view, int n, Bundle bundle) {
            return false;
        }

        public void postOnAnimation(Runnable runnable2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return;
            ViewCompat.postOnAnimation((View)recyclerView, runnable2);
        }

        public void removeAllViews() {
            int n = this.getChildCount() - 1;
            while (n >= 0) {
                this.mChildHelper.removeViewAt(n);
                --n;
            }
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            int n = this.getChildCount() - 1;
            while (n >= 0) {
                if (!RecyclerView.getChildViewHolderInt(this.getChildAt(n)).shouldIgnore()) {
                    this.removeAndRecycleViewAt(n, recycler);
                }
                --n;
            }
        }

        void removeAndRecycleScrapInt(Recycler recycler) {
            int n = recycler.getScrapCount();
            int n2 = n - 1;
            do {
                if (n2 < 0) {
                    recycler.clearScrap();
                    if (n <= 0) return;
                    this.mRecyclerView.invalidate();
                    return;
                }
                View view = recycler.getScrapViewAt(n2);
                ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
                if (!viewHolder.shouldIgnore()) {
                    viewHolder.setIsRecyclable(false);
                    if (viewHolder.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(view, false);
                    }
                    if (this.mRecyclerView.mItemAnimator != null) {
                        this.mRecyclerView.mItemAnimator.endAnimation(viewHolder);
                    }
                    viewHolder.setIsRecyclable(true);
                    recycler.quickRecycleScrapView(view);
                }
                --n2;
            } while (true);
        }

        public void removeAndRecycleView(View view, Recycler recycler) {
            this.removeView(view);
            recycler.recycleView(view);
        }

        public void removeAndRecycleViewAt(int n, Recycler recycler) {
            View view = this.getChildAt(n);
            this.removeViewAt(n);
            recycler.recycleView(view);
        }

        public boolean removeCallbacks(Runnable runnable2) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return false;
            return recyclerView.removeCallbacks(runnable2);
        }

        public void removeDetachedView(View view) {
            this.mRecyclerView.removeDetachedView(view, false);
        }

        public void removeView(View view) {
            this.mChildHelper.removeView(view);
        }

        public void removeViewAt(int n) {
            if (this.getChildAt(n) == null) return;
            this.mChildHelper.removeViewAt(n);
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
            return this.requestChildRectangleOnScreen(recyclerView, view, rect, bl, false);
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View arrn, Rect rect, boolean bl, boolean bl2) {
            arrn = this.getChildRectangleOnScreenScrollAmount((View)arrn, rect);
            int n = arrn[0];
            int n2 = arrn[1];
            if (bl2) {
                if (!this.isFocusedChildVisibleAfterScrolling(recyclerView, n, n2)) return false;
            }
            if (n == 0) {
                if (n2 == 0) return false;
            }
            if (bl) {
                recyclerView.scrollBy(n, n2);
                return true;
            }
            recyclerView.smoothScrollBy(n, n2);
            return true;
        }

        public void requestLayout() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return;
            recyclerView.requestLayout();
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int scrollHorizontallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int n) {
        }

        public int scrollVerticallyBy(int n, Recycler recycler, State state) {
            return 0;
        }

        @Deprecated
        public void setAutoMeasureEnabled(boolean bl) {
            this.mAutoMeasure = bl;
        }

        void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            this.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)recyclerView.getWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)recyclerView.getHeight(), (int)1073741824));
        }

        public final void setItemPrefetchEnabled(boolean bl) {
            if (bl == this.mItemPrefetchEnabled) return;
            this.mItemPrefetchEnabled = bl;
            this.mPrefetchMaxCountObserved = 0;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) return;
            recyclerView.mRecycler.updateViewCacheSize();
        }

        void setMeasureSpecs(int n, int n2) {
            this.mWidth = View.MeasureSpec.getSize((int)n);
            this.mWidthMode = n = View.MeasureSpec.getMode((int)n);
            if (n == 0 && !ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize((int)n2);
            this.mHeightMode = n = View.MeasureSpec.getMode((int)n2);
            if (n != 0) return;
            if (ALLOW_SIZE_IN_UNSPECIFIED_SPEC) return;
            this.mHeight = 0;
        }

        public void setMeasuredDimension(int n, int n2) {
            this.mRecyclerView.setMeasuredDimension(n, n2);
        }

        public void setMeasuredDimension(Rect rect, int n, int n2) {
            int n3 = rect.width();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = rect.height();
            int n7 = this.getPaddingTop();
            int n8 = this.getPaddingBottom();
            this.setMeasuredDimension(LayoutManager.chooseSize(n, n3 + n4 + n5, this.getMinimumWidth()), LayoutManager.chooseSize(n2, n6 + n7 + n8, this.getMinimumHeight()));
        }

        void setMeasuredDimensionFromChildren(int n, int n2) {
            int n3 = this.getChildCount();
            if (n3 == 0) {
                this.mRecyclerView.defaultOnMeasure(n, n2);
                return;
            }
            int n4 = 0;
            int n5 = Integer.MIN_VALUE;
            int n6 = Integer.MIN_VALUE;
            int n7 = Integer.MAX_VALUE;
            int n8 = Integer.MAX_VALUE;
            do {
                if (n4 >= n3) {
                    this.mRecyclerView.mTempRect.set(n7, n8, n5, n6);
                    this.setMeasuredDimension(this.mRecyclerView.mTempRect, n, n2);
                    return;
                }
                View view = this.getChildAt(n4);
                Rect rect = this.mRecyclerView.mTempRect;
                this.getDecoratedBoundsWithMargins(view, rect);
                int n9 = n7;
                if (rect.left < n7) {
                    n9 = rect.left;
                }
                n7 = n5;
                if (rect.right > n5) {
                    n7 = rect.right;
                }
                int n10 = n8;
                if (rect.top < n8) {
                    n10 = rect.top;
                }
                n8 = n6;
                if (rect.bottom > n6) {
                    n8 = rect.bottom;
                }
                ++n4;
                n5 = n7;
                n6 = n8;
                n7 = n9;
                n8 = n10;
            } while (true);
        }

        public void setMeasurementCacheEnabled(boolean bl) {
            this.mMeasurementCacheEnabled = bl;
        }

        void setRecyclerView(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                this.mWidth = 0;
                this.mHeight = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.mChildHelper;
                this.mWidth = recyclerView.getWidth();
                this.mHeight = recyclerView.getHeight();
            }
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        boolean shouldMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            if (view.isLayoutRequested()) return true;
            if (!this.mMeasurementCacheEnabled) return true;
            if (!LayoutManager.isMeasurementUpToDate(view.getWidth(), n, layoutParams.width)) return true;
            if (!LayoutManager.isMeasurementUpToDate(view.getHeight(), n2, layoutParams.height)) return true;
            return false;
        }

        boolean shouldMeasureTwice() {
            return false;
        }

        boolean shouldReMeasureChild(View view, int n, int n2, LayoutParams layoutParams) {
            if (!this.mMeasurementCacheEnabled) return true;
            if (!LayoutManager.isMeasurementUpToDate(view.getMeasuredWidth(), n, layoutParams.width)) return true;
            if (!LayoutManager.isMeasurementUpToDate(view.getMeasuredHeight(), n2, layoutParams.height)) return true;
            return false;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int n) {
            Log.e((String)"RecyclerView", (String)"You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = this.mSmoothScroller;
            if (smoothScroller2 != null && smoothScroller != smoothScroller2 && smoothScroller2.isRunning()) {
                this.mSmoothScroller.stop();
            }
            this.mSmoothScroller = smoothScroller;
            smoothScroller.start(this.mRecyclerView, this);
        }

        public void stopIgnoringView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).stopIgnoring();
            ((ViewHolder)object).resetInternal();
            ((ViewHolder)object).addFlags(4);
        }

        void stopSmoothScroller() {
            SmoothScroller smoothScroller = this.mSmoothScroller;
            if (smoothScroller == null) return;
            smoothScroller.stop();
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public static interface LayoutPrefetchRegistry {
            public void addPosition(int var1, int var2);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        final Rect mDecorInsets = new Rect();
        boolean mInsetsDirty = true;
        boolean mPendingInvalidate = false;
        ViewHolder mViewHolder;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
        }

        public int getViewAdapterPosition() {
            return this.mViewHolder.getAdapterPosition();
        }

        public int getViewLayoutPosition() {
            return this.mViewHolder.getLayoutPosition();
        }

        @Deprecated
        public int getViewPosition() {
            return this.mViewHolder.getPosition();
        }

        public boolean isItemChanged() {
            return this.mViewHolder.isUpdated();
        }

        public boolean isItemRemoved() {
            return this.mViewHolder.isRemoved();
        }

        public boolean isViewInvalid() {
            return this.mViewHolder.isInvalid();
        }

        public boolean viewNeedsUpdate() {
            return this.mViewHolder.needsUpdate();
        }
    }

    public static interface OnChildAttachStateChangeListener {
        public void onChildViewAttachedToWindow(View var1);

        public void onChildViewDetachedFromWindow(View var1);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int var1, int var2);
    }

    public static interface OnItemTouchListener {
        public boolean onInterceptTouchEvent(RecyclerView var1, MotionEvent var2);

        public void onRequestDisallowInterceptTouchEvent(boolean var1);

        public void onTouchEvent(RecyclerView var1, MotionEvent var2);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int n) {
        }

        public void onScrolled(RecyclerView recyclerView, int n, int n2) {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Orientation {
    }

    public static class RecycledViewPool {
        private static final int DEFAULT_MAX_SCRAP = 5;
        private int mAttachCount = 0;
        SparseArray<ScrapData> mScrap = new SparseArray();

        private ScrapData getScrapDataForType(int n) {
            ScrapData scrapData;
            ScrapData scrapData2 = scrapData = (ScrapData)this.mScrap.get(n);
            if (scrapData != null) return scrapData2;
            scrapData2 = new ScrapData();
            this.mScrap.put(n, (Object)scrapData2);
            return scrapData2;
        }

        void attach() {
            ++this.mAttachCount;
        }

        public void clear() {
            int n = 0;
            while (n < this.mScrap.size()) {
                ((ScrapData)this.mScrap.valueAt((int)n)).mScrapHeap.clear();
                ++n;
            }
        }

        void detach() {
            --this.mAttachCount;
        }

        void factorInBindTime(int n, long l) {
            ScrapData scrapData = this.getScrapDataForType(n);
            scrapData.mBindRunningAverageNs = this.runningAverage(scrapData.mBindRunningAverageNs, l);
        }

        void factorInCreateTime(int n, long l) {
            ScrapData scrapData = this.getScrapDataForType(n);
            scrapData.mCreateRunningAverageNs = this.runningAverage(scrapData.mCreateRunningAverageNs, l);
        }

        public ViewHolder getRecycledView(int n) {
            Object object = (ScrapData)this.mScrap.get(n);
            if (object == null) return null;
            if (((ScrapData)object).mScrapHeap.isEmpty()) return null;
            object = ((ScrapData)object).mScrapHeap;
            n = ((ArrayList)object).size() - 1;
            while (n >= 0) {
                if (!((ViewHolder)((ArrayList)object).get(n)).isAttachedToTransitionOverlay()) {
                    return (ViewHolder)((ArrayList)object).remove(n);
                }
                --n;
            }
            return null;
        }

        public int getRecycledViewCount(int n) {
            return this.getScrapDataForType((int)n).mScrapHeap.size();
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            if (adapter != null) {
                this.detach();
            }
            if (!bl && this.mAttachCount == 0) {
                this.clear();
            }
            if (adapter2 == null) return;
            this.attach();
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int n = viewHolder.getItemViewType();
            ArrayList<ViewHolder> arrayList = this.getScrapDataForType((int)n).mScrapHeap;
            if (((ScrapData)this.mScrap.get((int)n)).mMaxScrap <= arrayList.size()) {
                return;
            }
            viewHolder.resetInternal();
            arrayList.add(viewHolder);
        }

        long runningAverage(long l, long l2) {
            if (l != 0L) return l / 4L * 3L + l2 / 4L;
            return l2;
        }

        public void setMaxRecycledViews(int n, int n2) {
            Object object = this.getScrapDataForType(n);
            ((ScrapData)object).mMaxScrap = n2;
            object = ((ScrapData)object).mScrapHeap;
            while (((ArrayList)object).size() > n2) {
                ((ArrayList)object).remove(((ArrayList)object).size() - 1);
            }
        }

        int size() {
            int n = 0;
            int n2 = 0;
            while (n < this.mScrap.size()) {
                ArrayList<ViewHolder> arrayList = ((ScrapData)this.mScrap.valueAt((int)n)).mScrapHeap;
                int n3 = n2;
                if (arrayList != null) {
                    n3 = n2 + arrayList.size();
                }
                ++n;
                n2 = n3;
            }
            return n2;
        }

        boolean willBindInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mBindRunningAverageNs;
            if (l3 == 0L) return true;
            if (l + l3 < l2) return true;
            return false;
        }

        boolean willCreateInTime(int n, long l, long l2) {
            long l3 = this.getScrapDataForType((int)n).mCreateRunningAverageNs;
            if (l3 == 0L) return true;
            if (l + l3 < l2) return true;
            return false;
        }

        static class ScrapData {
            long mBindRunningAverageNs = 0L;
            long mCreateRunningAverageNs = 0L;
            int mMaxScrap = 5;
            final ArrayList<ViewHolder> mScrapHeap = new ArrayList();

            ScrapData() {
            }
        }

    }

    public final class Recycler {
        static final int DEFAULT_CACHE_SIZE = 2;
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList();
        final ArrayList<ViewHolder> mCachedViews = new ArrayList();
        ArrayList<ViewHolder> mChangedScrap = null;
        RecycledViewPool mRecyclerPool;
        private int mRequestedCacheMax = 2;
        private final List<ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
        private ViewCacheExtension mViewCacheExtension;
        int mViewCacheMax = 2;

        private void attachAccessibilityDelegateOnBind(ViewHolder object) {
            if (!RecyclerView.this.isAccessibilityEnabled()) return;
            View view = ((ViewHolder)object).itemView;
            if (ViewCompat.getImportantForAccessibility(view) == 0) {
                ViewCompat.setImportantForAccessibility(view, 1);
            }
            if (RecyclerView.this.mAccessibilityDelegate == null) {
                return;
            }
            object = RecyclerView.this.mAccessibilityDelegate.getItemDelegate();
            if (object instanceof RecyclerViewAccessibilityDelegate.ItemDelegate) {
                ((RecyclerViewAccessibilityDelegate.ItemDelegate)object).saveOriginalDelegate(view);
            }
            ViewCompat.setAccessibilityDelegate(view, (AccessibilityDelegateCompat)object);
        }

        private void invalidateDisplayListInt(ViewGroup viewGroup, boolean bl) {
            int n;
            for (n = viewGroup.getChildCount() - 1; n >= 0; --n) {
                View view = viewGroup.getChildAt(n);
                if (!(view instanceof ViewGroup)) continue;
                this.invalidateDisplayListInt((ViewGroup)view, true);
            }
            if (!bl) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
                return;
            }
            n = viewGroup.getVisibility();
            viewGroup.setVisibility(4);
            viewGroup.setVisibility(n);
        }

        private void invalidateDisplayListInt(ViewHolder viewHolder) {
            if (!(viewHolder.itemView instanceof ViewGroup)) return;
            this.invalidateDisplayListInt((ViewGroup)viewHolder.itemView, false);
        }

        private boolean tryBindViewHolderByDeadline(ViewHolder viewHolder, int n, int n2, long l) {
            viewHolder.mOwnerRecyclerView = RecyclerView.this;
            int n3 = viewHolder.getItemViewType();
            long l2 = RecyclerView.this.getNanoTime();
            if (l != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(n3, l2, l)) {
                return false;
            }
            RecyclerView.this.mAdapter.bindViewHolder(viewHolder, n);
            l = RecyclerView.this.getNanoTime();
            this.mRecyclerPool.factorInBindTime(viewHolder.getItemViewType(), l - l2);
            this.attachAccessibilityDelegateOnBind(viewHolder);
            if (!RecyclerView.this.mState.isPreLayout()) return true;
            viewHolder.mPreLayoutPosition = n2;
            return true;
        }

        void addViewHolderToRecycledViewPool(ViewHolder viewHolder, boolean bl) {
            RecyclerView.clearNestedRecyclerViewIfNotNested(viewHolder);
            View view = viewHolder.itemView;
            if (RecyclerView.this.mAccessibilityDelegate != null) {
                AccessibilityDelegateCompat accessibilityDelegateCompat = RecyclerView.this.mAccessibilityDelegate.getItemDelegate();
                accessibilityDelegateCompat = accessibilityDelegateCompat instanceof RecyclerViewAccessibilityDelegate.ItemDelegate ? ((RecyclerViewAccessibilityDelegate.ItemDelegate)accessibilityDelegateCompat).getAndRemoveOriginalDelegateForItem(view) : null;
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegateCompat);
            }
            if (bl) {
                this.dispatchViewRecycled(viewHolder);
            }
            viewHolder.mOwnerRecyclerView = null;
            this.getRecycledViewPool().putRecycledView(viewHolder);
        }

        public void bindViewToPosition(View object, int n) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt((View)object);
            if (viewHolder == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
                ((StringBuilder)object).append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            int n2 = RecyclerView.this.mAdapterHelper.findPositionOffset(n);
            if (n2 >= 0 && n2 < RecyclerView.this.mAdapter.getItemCount()) {
                this.tryBindViewHolderByDeadline(viewHolder, n2, n, Long.MAX_VALUE);
                object = viewHolder.itemView.getLayoutParams();
                if (object == null) {
                    object = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                    viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)object)) {
                    object = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)object);
                    viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)object);
                } else {
                    object = (LayoutParams)((Object)object);
                }
                boolean bl = true;
                ((LayoutParams)object).mInsetsDirty = true;
                ((LayoutParams)object).mViewHolder = viewHolder;
                if (viewHolder.itemView.getParent() != null) {
                    bl = false;
                }
                ((LayoutParams)object).mPendingInvalidate = bl;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Inconsistency detected. Invalid item position ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("(offset:");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(").state:");
            ((StringBuilder)object).append(RecyclerView.this.mState.getItemCount());
            ((StringBuilder)object).append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
        }

        public void clear() {
            this.mAttachedScrap.clear();
            this.recycleAndClearCachedViews();
        }

        void clearOldPositions() {
            int n;
            int n2 = this.mCachedViews.size();
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                this.mCachedViews.get(n).clearOldPosition();
            }
            n2 = this.mAttachedScrap.size();
            for (n = 0; n < n2; ++n) {
                this.mAttachedScrap.get(n).clearOldPosition();
            }
            ArrayList<ViewHolder> arrayList = this.mChangedScrap;
            if (arrayList == null) return;
            n2 = arrayList.size();
            n = n3;
            while (n < n2) {
                this.mChangedScrap.get(n).clearOldPosition();
                ++n;
            }
        }

        void clearScrap() {
            this.mAttachedScrap.clear();
            ArrayList<ViewHolder> arrayList = this.mChangedScrap;
            if (arrayList == null) return;
            arrayList.clear();
        }

        public int convertPreLayoutPositionToPostLayout(int n) {
            if (n >= 0 && n < RecyclerView.this.mState.getItemCount()) {
                if (RecyclerView.this.mState.isPreLayout()) return RecyclerView.this.mAdapterHelper.findPositionOffset(n);
                return n;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid position ");
            stringBuilder.append(n);
            stringBuilder.append(". State item count is ");
            stringBuilder.append(RecyclerView.this.mState.getItemCount());
            stringBuilder.append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        void dispatchViewRecycled(ViewHolder viewHolder) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(viewHolder);
            }
            if (RecyclerView.this.mState == null) return;
            RecyclerView.this.mViewInfoStore.removeViewHolder(viewHolder);
        }

        ViewHolder getChangedScrapViewForPosition(int n) {
            Object object = this.mChangedScrap;
            if (object == null) return null;
            int n2 = ((ArrayList)object).size();
            if (n2 == 0) {
                return null;
            }
            int n3 = 0;
            for (int i = 0; i < n2; ++i) {
                object = this.mChangedScrap.get(i);
                if (((ViewHolder)object).wasReturnedFromScrap() || ((ViewHolder)object).getLayoutPosition() != n) continue;
                ((ViewHolder)object).addFlags(32);
                return object;
            }
            if (!RecyclerView.this.mAdapter.hasStableIds()) return null;
            if ((n = RecyclerView.this.mAdapterHelper.findPositionOffset(n)) <= 0) return null;
            if (n >= RecyclerView.this.mAdapter.getItemCount()) return null;
            long l = RecyclerView.this.mAdapter.getItemId(n);
            n = n3;
            while (n < n2) {
                object = this.mChangedScrap.get(n);
                if (!((ViewHolder)object).wasReturnedFromScrap() && ((ViewHolder)object).getItemId() == l) {
                    ((ViewHolder)object).addFlags(32);
                    return object;
                }
                ++n;
            }
            return null;
        }

        RecycledViewPool getRecycledViewPool() {
            if (this.mRecyclerPool != null) return this.mRecyclerPool;
            this.mRecyclerPool = new RecycledViewPool();
            return this.mRecyclerPool;
        }

        int getScrapCount() {
            return this.mAttachedScrap.size();
        }

        public List<ViewHolder> getScrapList() {
            return this.mUnmodifiableAttachedScrap;
        }

        ViewHolder getScrapOrCachedViewForId(long l, int n, boolean bl) {
            int n2;
            ViewHolder viewHolder;
            for (n2 = this.mAttachedScrap.size() - 1; n2 >= 0; --n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.getItemId() != l || viewHolder.wasReturnedFromScrap()) continue;
                if (n == viewHolder.getItemViewType()) {
                    viewHolder.addFlags(32);
                    if (!viewHolder.isRemoved()) return viewHolder;
                    if (RecyclerView.this.mState.isPreLayout()) return viewHolder;
                    viewHolder.setFlags(2, 14);
                    return viewHolder;
                }
                if (bl) continue;
                this.mAttachedScrap.remove(n2);
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
                this.quickRecycleScrapView(viewHolder.itemView);
            }
            n2 = this.mCachedViews.size() - 1;
            while (n2 >= 0) {
                viewHolder = this.mCachedViews.get(n2);
                if (viewHolder.getItemId() == l && !viewHolder.isAttachedToTransitionOverlay()) {
                    if (n == viewHolder.getItemViewType()) {
                        if (bl) return viewHolder;
                        this.mCachedViews.remove(n2);
                        return viewHolder;
                    }
                    if (!bl) {
                        this.recycleCachedViewAt(n2);
                        return null;
                    }
                }
                --n2;
            }
            return null;
        }

        ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int n, boolean bl) {
            Object object;
            int n2;
            ViewHolder viewHolder;
            int n3 = this.mAttachedScrap.size();
            int n4 = 0;
            for (n2 = 0; n2 < n3; ++n2) {
                viewHolder = this.mAttachedScrap.get(n2);
                if (viewHolder.wasReturnedFromScrap() || viewHolder.getLayoutPosition() != n || viewHolder.isInvalid() || !RecyclerView.this.mState.mInPreLayout && viewHolder.isRemoved()) continue;
                viewHolder.addFlags(32);
                return viewHolder;
            }
            if (!bl && (object = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(n)) != null) {
                viewHolder = RecyclerView.getChildViewHolderInt((View)object);
                RecyclerView.this.mChildHelper.unhide((View)object);
                n = RecyclerView.this.mChildHelper.indexOfChild((View)object);
                if (n != -1) {
                    RecyclerView.this.mChildHelper.detachViewFromParent(n);
                    this.scrapView((View)object);
                    viewHolder.addFlags(8224);
                    return viewHolder;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("layout index should not be -1 after unhiding a view:");
                ((StringBuilder)object).append(viewHolder);
                ((StringBuilder)object).append(RecyclerView.this.exceptionLabel());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            n3 = this.mCachedViews.size();
            n2 = n4;
            while (n2 < n3) {
                viewHolder = this.mCachedViews.get(n2);
                if (!viewHolder.isInvalid() && viewHolder.getLayoutPosition() == n && !viewHolder.isAttachedToTransitionOverlay()) {
                    if (bl) return viewHolder;
                    this.mCachedViews.remove(n2);
                    return viewHolder;
                }
                ++n2;
            }
            return null;
        }

        View getScrapViewAt(int n) {
            return this.mAttachedScrap.get((int)n).itemView;
        }

        public View getViewForPosition(int n) {
            return this.getViewForPosition(n, false);
        }

        View getViewForPosition(int n, boolean bl) {
            return this.tryGetViewHolderForPositionByDeadline((int)n, (boolean)bl, (long)Long.MAX_VALUE).itemView;
        }

        void markItemDecorInsetsDirty() {
            int n = this.mCachedViews.size();
            int n2 = 0;
            while (n2 < n) {
                LayoutParams layoutParams = (LayoutParams)this.mCachedViews.get((int)n2).itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.mInsetsDirty = true;
                }
                ++n2;
            }
        }

        void markKnownViewsInvalid() {
            int n = this.mCachedViews.size();
            for (int i = 0; i < n; ++i) {
                ViewHolder viewHolder = this.mCachedViews.get(i);
                if (viewHolder == null) continue;
                viewHolder.addFlags(6);
                viewHolder.addChangePayload(null);
            }
            if (RecyclerView.this.mAdapter != null) {
                if (RecyclerView.this.mAdapter.hasStableIds()) return;
            }
            this.recycleAndClearCachedViews();
        }

        void offsetPositionRecordsForInsert(int n, int n2) {
            int n3 = this.mCachedViews.size();
            int n4 = 0;
            while (n4 < n3) {
                ViewHolder viewHolder = this.mCachedViews.get(n4);
                if (viewHolder != null && viewHolder.mPosition >= n) {
                    viewHolder.offsetPosition(n2, true);
                }
                ++n4;
            }
        }

        void offsetPositionRecordsForMove(int n, int n2) {
            int n3;
            int n4;
            int n5;
            if (n < n2) {
                n5 = -1;
                n3 = n;
                n4 = n2;
            } else {
                n5 = 1;
                n4 = n;
                n3 = n2;
            }
            int n6 = this.mCachedViews.size();
            int n7 = 0;
            while (n7 < n6) {
                ViewHolder viewHolder = this.mCachedViews.get(n7);
                if (viewHolder != null && viewHolder.mPosition >= n3 && viewHolder.mPosition <= n4) {
                    if (viewHolder.mPosition == n) {
                        viewHolder.offsetPosition(n2 - n, false);
                    } else {
                        viewHolder.offsetPosition(n5, false);
                    }
                }
                ++n7;
            }
        }

        void offsetPositionRecordsForRemove(int n, int n2, boolean bl) {
            int n3 = this.mCachedViews.size() - 1;
            while (n3 >= 0) {
                ViewHolder viewHolder = this.mCachedViews.get(n3);
                if (viewHolder != null) {
                    if (viewHolder.mPosition >= n + n2) {
                        viewHolder.offsetPosition(-n2, bl);
                    } else if (viewHolder.mPosition >= n) {
                        viewHolder.addFlags(8);
                        this.recycleCachedViewAt(n3);
                    }
                }
                --n3;
            }
        }

        void onAdapterChanged(Adapter adapter, Adapter adapter2, boolean bl) {
            this.clear();
            this.getRecycledViewPool().onAdapterChanged(adapter, adapter2, bl);
        }

        void quickRecycleScrapView(View object) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            ((ViewHolder)object).mScrapContainer = null;
            ((ViewHolder)object).mInChangeScrap = false;
            ((ViewHolder)object).clearReturnedFromScrapFlag();
            this.recycleViewHolderInternal((ViewHolder)object);
        }

        void recycleAndClearCachedViews() {
            int n = this.mCachedViews.size() - 1;
            do {
                if (n < 0) {
                    this.mCachedViews.clear();
                    if (!ALLOW_THREAD_GAP_WORK) return;
                    RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
                    return;
                }
                this.recycleCachedViewAt(n);
                --n;
            } while (true);
        }

        void recycleCachedViewAt(int n) {
            this.addViewHolderToRecycledViewPool(this.mCachedViews.get(n), true);
            this.mCachedViews.remove(n);
        }

        public void recycleView(View view) {
            ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (viewHolder.isScrap()) {
                viewHolder.unScrap();
            } else if (viewHolder.wasReturnedFromScrap()) {
                viewHolder.clearReturnedFromScrapFlag();
            }
            this.recycleViewHolderInternal(viewHolder);
            if (RecyclerView.this.mItemAnimator == null) return;
            if (viewHolder.isRecyclable()) return;
            RecyclerView.this.mItemAnimator.endAnimation(viewHolder);
        }

        /*
         * Unable to fully structure code
         */
        void recycleViewHolderInternal(ViewHolder var1_1) {
            block7 : {
                block14 : {
                    block12 : {
                        block13 : {
                            block10 : {
                                block8 : {
                                    block11 : {
                                        block9 : {
                                            var2_2 = var1_1.isScrap();
                                            var3_3 = false;
                                            var4_4 = 0;
                                            var5_5 = 1;
                                            if (var2_2 || var1_1.itemView.getParent() != null) break block8;
                                            if (var1_1.isTmpDetached()) {
                                                var7_7 = new StringBuilder();
                                                var7_7.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
                                                var7_7.append(var1_1);
                                                var7_7.append(RecyclerView.this.exceptionLabel());
                                                throw new IllegalArgumentException(var7_7.toString());
                                            }
                                            if (var1_1.shouldIgnore()) {
                                                var1_1 = new StringBuilder();
                                                var1_1.append("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
                                                var1_1.append(RecyclerView.this.exceptionLabel());
                                                throw new IllegalArgumentException(var1_1.toString());
                                            }
                                            var3_3 = var1_1.doesTransientStatePreventRecycling();
                                            var6_6 = RecyclerView.this.mAdapter != null && var3_3 != false && RecyclerView.this.mAdapter.onFailedToRecycleView(var1_1) != false ? 1 : 0;
                                            if (var6_6 != 0) break block9;
                                            var6_6 = var4_4;
                                            if (!var1_1.isRecyclable()) break block10;
                                        }
                                        if (this.mViewCacheMax <= 0 || var1_1.hasAnyOfTheFlags(526)) break block11;
                                        var6_6 = var4_4 = this.mCachedViews.size();
                                        if (var4_4 >= this.mViewCacheMax) {
                                            var6_6 = var4_4;
                                            if (var4_4 > 0) {
                                                this.recycleCachedViewAt(0);
                                                var6_6 = var4_4 - 1;
                                            }
                                        }
                                        var4_4 = var6_6;
                                        if (!RecyclerView.ALLOW_THREAD_GAP_WORK) break block12;
                                        var4_4 = var6_6;
                                        if (var6_6 <= 0) break block12;
                                        var4_4 = var6_6--;
                                        if (RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(var1_1.mPosition)) break block12;
                                        break block13;
                                    }
                                    var6_6 = 0;
                                    break block14;
                                }
                                var7_8 = new StringBuilder();
                                var7_8.append("Scrapped or attached views may not be recycled. isScrap:");
                                var7_8.append(var1_1.isScrap());
                                var7_8.append(" isAttached:");
                                if (var1_1.itemView.getParent() != null) {
                                    var3_3 = true;
                                }
                                var7_8.append(var3_3);
                                var7_8.append(RecyclerView.this.exceptionLabel());
                                throw new IllegalArgumentException(var7_8.toString());
                            }
lbl61: // 2 sources:
                            do {
                                var4_4 = 0;
                                break block7;
                                break;
                            } while (true);
                        }
                        while (var6_6 >= 0 && RecyclerView.this.mPrefetchRegistry.lastPrefetchIncludedPosition(var4_4 = this.mCachedViews.get((int)var6_6).mPosition)) {
                            --var6_6;
                        }
                        var4_4 = var6_6 + 1;
                    }
                    this.mCachedViews.add(var4_4, (ViewHolder)var1_1);
                    var6_6 = 1;
                }
                ** while (var6_6 != 0)
lbl74: // 1 sources:
                this.addViewHolderToRecycledViewPool((ViewHolder)var1_1, true);
                var4_4 = var5_5;
            }
            RecyclerView.this.mViewInfoStore.removeViewHolder((ViewHolder)var1_1);
            if (var6_6 != 0) return;
            if (var4_4 != 0) return;
            if (var3_3 == false) return;
            var1_1.mOwnerRecyclerView = null;
        }

        void scrapView(View object) {
            if (!((ViewHolder)(object = RecyclerView.getChildViewHolderInt((View)object))).hasAnyOfTheFlags(12) && ((ViewHolder)object).isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder((ViewHolder)object)) {
                if (this.mChangedScrap == null) {
                    this.mChangedScrap = new ArrayList();
                }
                ((ViewHolder)object).setScrapContainer(this, true);
                this.mChangedScrap.add((ViewHolder)object);
                return;
            }
            if (((ViewHolder)object).isInvalid() && !((ViewHolder)object).isRemoved() && !RecyclerView.this.mAdapter.hasStableIds()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
                ((StringBuilder)object).append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            ((ViewHolder)object).setScrapContainer(this, false);
            this.mAttachedScrap.add((ViewHolder)object);
        }

        void setRecycledViewPool(RecycledViewPool recycledViewPool) {
            RecycledViewPool recycledViewPool2 = this.mRecyclerPool;
            if (recycledViewPool2 != null) {
                recycledViewPool2.detach();
            }
            this.mRecyclerPool = recycledViewPool;
            if (recycledViewPool == null) return;
            if (RecyclerView.this.getAdapter() == null) return;
            this.mRecyclerPool.attach();
        }

        void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
            this.mViewCacheExtension = viewCacheExtension;
        }

        public void setViewCacheSize(int n) {
            this.mRequestedCacheMax = n;
            this.updateViewCacheSize();
        }

        /*
         * Unable to fully structure code
         */
        ViewHolder tryGetViewHolderForPositionByDeadline(int var1_1, boolean var2_2, long var3_3) {
            block27 : {
                block34 : {
                    block33 : {
                        block31 : {
                            block32 : {
                                block30 : {
                                    block29 : {
                                        block28 : {
                                            if (var1_1 < 0 || var1_1 >= RecyclerView.this.mState.getItemCount()) break block27;
                                            var5_4 = RecyclerView.this.mState.isPreLayout();
                                            var6_5 = true;
                                            if (!var5_4) break block28;
                                            var8_8 = var7_6 = this.getChangedScrapViewForPosition(var1_1);
                                            if (var7_6 == null) break block29;
                                            var9_9 = 1;
                                            var8_8 = var7_6;
                                            break block30;
                                        }
                                        var8_8 = null;
                                    }
                                    var9_9 = 0;
                                }
                                var7_6 = var8_8;
                                var10_10 = var9_9;
                                if (var8_8 == null) {
                                    var7_6 = var8_8 = this.getScrapOrHiddenOrCachedHolderForPosition(var1_1, var2_2);
                                    var10_10 = var9_9;
                                    if (var8_8 != null) {
                                        if (!this.validateViewHolderForOffsetPosition((ViewHolder)var8_8)) {
                                            if (!var2_2) {
                                                var8_8.addFlags(4);
                                                if (var8_8.isScrap()) {
                                                    RecyclerView.this.removeDetachedView(var8_8.itemView, false);
                                                    var8_8.unScrap();
                                                } else if (var8_8.wasReturnedFromScrap()) {
                                                    var8_8.clearReturnedFromScrapFlag();
                                                }
                                                this.recycleViewHolderInternal((ViewHolder)var8_8);
                                            }
                                            var7_6 = null;
                                            var10_10 = var9_9;
                                        } else {
                                            var10_10 = 1;
                                            var7_6 = var8_8;
                                        }
                                    }
                                }
                                var8_8 = var7_6;
                                var11_11 = var10_10;
                                if (var7_6 != null) break block31;
                                var11_11 = RecyclerView.this.mAdapterHelper.findPositionOffset(var1_1);
                                if (var11_11 < 0 || var11_11 >= RecyclerView.this.mAdapter.getItemCount()) break block32;
                                var12_12 = RecyclerView.this.mAdapter.getItemViewType(var11_11);
                                var9_9 = var10_10;
                                if (RecyclerView.this.mAdapter.hasStableIds()) {
                                    var7_6 = var8_8 = this.getScrapOrCachedViewForId(RecyclerView.this.mAdapter.getItemId(var11_11), var12_12, var2_2);
                                    var9_9 = var10_10;
                                    if (var8_8 != null) {
                                        var8_8.mPosition = var11_11;
                                        var9_9 = 1;
                                        var7_6 = var8_8;
                                    }
                                }
                                var8_8 = var7_6;
                                if (var7_6 == null) {
                                    var13_13 = this.mViewCacheExtension;
                                    var8_8 = var7_6;
                                    if (var13_13 != null) {
                                        var13_13 = var13_13.getViewForPositionAndType(this, var1_1, var12_12);
                                        var8_8 = var7_6;
                                        if (var13_13 != null) {
                                            var8_8 = RecyclerView.this.getChildViewHolder((View)var13_13);
                                            if (var8_8 == null) {
                                                var7_6 = new StringBuilder();
                                                var7_6.append("getViewForPositionAndType returned a view which does not have a ViewHolder");
                                                var7_6.append(RecyclerView.this.exceptionLabel());
                                                throw new IllegalArgumentException(var7_6.toString());
                                            }
                                            if (var8_8.shouldIgnore()) {
                                                var7_6 = new StringBuilder();
                                                var7_6.append("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.");
                                                var7_6.append(RecyclerView.this.exceptionLabel());
                                                throw new IllegalArgumentException(var7_6.toString());
                                            }
                                        }
                                    }
                                }
                                var7_6 = var8_8;
                                if (var8_8 == null && (var7_6 = this.getRecycledViewPool().getRecycledView(var12_12)) != null) {
                                    var7_6.resetInternal();
                                    if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) {
                                        this.invalidateDisplayListInt((ViewHolder)var7_6);
                                    }
                                }
                                var8_8 = var7_6;
                                var11_11 = var9_9;
                                if (var7_6 != null) break block31;
                                var14_14 = RecyclerView.this.getNanoTime();
                                if (var3_3 != Long.MAX_VALUE && !this.mRecyclerPool.willCreateInTime(var12_12, var14_14, var3_3)) {
                                    return null;
                                }
                                var8_8 = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, var12_12);
                                if (RecyclerView.ALLOW_THREAD_GAP_WORK && (var7_6 = RecyclerView.findNestedRecyclerView(var8_8.itemView)) != null) {
                                    var8_8.mNestedRecyclerView = new WeakReference<Object>(var7_6);
                                }
                                var16_15 = RecyclerView.this.getNanoTime();
                                this.mRecyclerPool.factorInCreateTime(var12_12, var16_15 - var14_14);
                                break block33;
                            }
                            var7_6 = new StringBuilder();
                            var7_6.append("Inconsistency detected. Invalid item position ");
                            var7_6.append(var1_1);
                            var7_6.append("(offset:");
                            var7_6.append(var11_11);
                            var7_6.append(").state:");
                            var7_6.append(RecyclerView.this.mState.getItemCount());
                            var7_6.append(RecyclerView.this.exceptionLabel());
                            throw new IndexOutOfBoundsException(var7_6.toString());
                        }
                        var9_9 = var11_11;
                    }
                    if (var9_9 != 0 && !RecyclerView.this.mState.isPreLayout() && var8_8.hasAnyOfTheFlags(8192)) {
                        var8_8.setFlags(0, 8192);
                        if (RecyclerView.this.mState.mRunSimpleAnimations) {
                            var10_10 = ItemAnimator.buildAdapterChangeFlagsForAnimations((ViewHolder)var8_8);
                            var7_6 = RecyclerView.this.mItemAnimator.recordPreLayoutInformation(RecyclerView.this.mState, (ViewHolder)var8_8, var10_10 | 4096, var8_8.getUnmodifiedPayloads());
                            RecyclerView.this.recordAnimationInfoIfBouncedHiddenView((ViewHolder)var8_8, (ItemAnimator.ItemHolderInfo)var7_6);
                        }
                    }
                    if (!RecyclerView.this.mState.isPreLayout() || !var8_8.isBound()) break block34;
                    var8_8.mPreLayoutPosition = var1_1;
                    ** GOTO lbl-1000
                }
                if (var8_8.isBound() && !var8_8.needsUpdate() && !var8_8.isInvalid()) lbl-1000: // 2 sources:
                {
                    var2_2 = false;
                } else {
                    var2_2 = this.tryBindViewHolderByDeadline((ViewHolder)var8_8, RecyclerView.this.mAdapterHelper.findPositionOffset(var1_1), var1_1, var3_3);
                }
                var7_6 = var8_8.itemView.getLayoutParams();
                if (var7_6 == null) {
                    var7_6 = (LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
                    var8_8.itemView.setLayoutParams((ViewGroup.LayoutParams)var7_6);
                } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)var7_6)) {
                    var7_6 = (LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)var7_6);
                    var8_8.itemView.setLayoutParams((ViewGroup.LayoutParams)var7_6);
                } else {
                    var7_6 = (LayoutParams)var7_6;
                }
                var7_6.mViewHolder = var8_8;
                var2_2 = var9_9 != 0 && var2_2 != false ? var6_5 : false;
                var7_6.mPendingInvalidate = var2_2;
                return var8_8;
            }
            var7_7 = new StringBuilder();
            var7_7.append("Invalid item position ");
            var7_7.append(var1_1);
            var7_7.append("(");
            var7_7.append(var1_1);
            var7_7.append("). Item count:");
            var7_7.append(RecyclerView.this.mState.getItemCount());
            var7_7.append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(var7_7.toString());
        }

        void unscrapView(ViewHolder viewHolder) {
            if (viewHolder.mInChangeScrap) {
                this.mChangedScrap.remove(viewHolder);
            } else {
                this.mAttachedScrap.remove(viewHolder);
            }
            viewHolder.mScrapContainer = null;
            viewHolder.mInChangeScrap = false;
            viewHolder.clearReturnedFromScrapFlag();
        }

        void updateViewCacheSize() {
            int n = RecyclerView.this.mLayout != null ? RecyclerView.this.mLayout.mPrefetchMaxCountObserved : 0;
            this.mViewCacheMax = this.mRequestedCacheMax + n;
            n = this.mCachedViews.size() - 1;
            while (n >= 0) {
                if (this.mCachedViews.size() <= this.mViewCacheMax) return;
                this.recycleCachedViewAt(n);
                --n;
            }
        }

        boolean validateViewHolderForOffsetPosition(ViewHolder viewHolder) {
            if (viewHolder.isRemoved()) {
                return RecyclerView.this.mState.isPreLayout();
            }
            if (viewHolder.mPosition >= 0 && viewHolder.mPosition < RecyclerView.this.mAdapter.getItemCount()) {
                boolean bl = RecyclerView.this.mState.isPreLayout();
                boolean bl2 = false;
                if (!bl && RecyclerView.this.mAdapter.getItemViewType(viewHolder.mPosition) != viewHolder.getItemViewType()) {
                    return false;
                }
                if (!RecyclerView.this.mAdapter.hasStableIds()) return true;
                if (viewHolder.getItemId() != RecyclerView.this.mAdapter.getItemId(viewHolder.mPosition)) return bl2;
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
            stringBuilder.append(viewHolder);
            stringBuilder.append(RecyclerView.this.exceptionLabel());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        void viewRangeUpdate(int n, int n2) {
            int n3 = this.mCachedViews.size() - 1;
            while (n3 >= 0) {
                int n4;
                ViewHolder viewHolder = this.mCachedViews.get(n3);
                if (viewHolder != null && (n4 = viewHolder.mPosition) >= n && n4 < n2 + n) {
                    viewHolder.addFlags(2);
                    this.recycleCachedViewAt(n3);
                }
                --n3;
            }
        }
    }

    public static interface RecyclerListener {
        public void onViewRecycled(ViewHolder var1);
    }

    private class RecyclerViewDataObserver
    extends AdapterDataObserver {
        RecyclerViewDataObserver() {
        }

        @Override
        public void onChanged() {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            RecyclerView.this.mState.mStructureChanged = true;
            RecyclerView.this.processDataSetCompletelyChanged(true);
            if (RecyclerView.this.mAdapterHelper.hasPendingUpdates()) return;
            RecyclerView.this.requestLayout();
        }

        @Override
        public void onItemRangeChanged(int n, int n2, Object object) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (!RecyclerView.this.mAdapterHelper.onItemRangeChanged(n, n2, object)) return;
            this.triggerUpdateProcessor();
        }

        @Override
        public void onItemRangeInserted(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (!RecyclerView.this.mAdapterHelper.onItemRangeInserted(n, n2)) return;
            this.triggerUpdateProcessor();
        }

        @Override
        public void onItemRangeMoved(int n, int n2, int n3) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (!RecyclerView.this.mAdapterHelper.onItemRangeMoved(n, n2, n3)) return;
            this.triggerUpdateProcessor();
        }

        @Override
        public void onItemRangeRemoved(int n, int n2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (!RecyclerView.this.mAdapterHelper.onItemRangeRemoved(n, n2)) return;
            this.triggerUpdateProcessor();
        }

        void triggerUpdateProcessor() {
            if (POST_UPDATES_ON_ANIMATION && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
                RecyclerView recyclerView = RecyclerView.this;
                ViewCompat.postOnAnimation((View)recyclerView, recyclerView.mUpdateChildViewsRunnable);
                return;
            }
            RecyclerView.this.mAdapterUpdateDuringMeasure = true;
            RecyclerView.this.requestLayout();
        }
    }

    public static class SavedState
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
        Parcelable mLayoutState;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = LayoutManager.class.getClassLoader();
            }
            this.mLayoutState = parcel.readParcelable(classLoader);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        void copyFrom(SavedState savedState) {
            this.mLayoutState = savedState.mLayoutState;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeParcelable(this.mLayoutState, 0);
        }

    }

    public static class SimpleOnItemTouchListener
    implements OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean bl) {
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        private LayoutManager mLayoutManager;
        private boolean mPendingInitialRun;
        private RecyclerView mRecyclerView;
        private final Action mRecyclingAction = new Action(0, 0);
        private boolean mRunning;
        private boolean mStarted;
        private int mTargetPosition = -1;
        private View mTargetView;

        public PointF computeScrollVectorForPosition(int n) {
            Object object = this.getLayoutManager();
            if (object instanceof ScrollVectorProvider) {
                return ((ScrollVectorProvider)object).computeScrollVectorForPosition(n);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("You should override computeScrollVectorForPosition when the LayoutManager does not implement ");
            ((StringBuilder)object).append(ScrollVectorProvider.class.getCanonicalName());
            Log.w((String)RecyclerView.TAG, (String)((StringBuilder)object).toString());
            return null;
        }

        public View findViewByPosition(int n) {
            return this.mRecyclerView.mLayout.findViewByPosition(n);
        }

        public int getChildCount() {
            return this.mRecyclerView.mLayout.getChildCount();
        }

        public int getChildPosition(View view) {
            return this.mRecyclerView.getChildLayoutPosition(view);
        }

        public LayoutManager getLayoutManager() {
            return this.mLayoutManager;
        }

        public int getTargetPosition() {
            return this.mTargetPosition;
        }

        @Deprecated
        public void instantScrollToPosition(int n) {
            this.mRecyclerView.scrollToPosition(n);
        }

        public boolean isPendingInitialRun() {
            return this.mPendingInitialRun;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        protected void normalize(PointF pointF) {
            float f = (float)Math.sqrt(pointF.x * pointF.x + pointF.y * pointF.y);
            pointF.x /= f;
            pointF.y /= f;
        }

        void onAnimation(int n, int n2) {
            View view;
            RecyclerView recyclerView = this.mRecyclerView;
            if (this.mTargetPosition == -1 || recyclerView == null) {
                this.stop();
            }
            if (this.mPendingInitialRun && this.mTargetView == null && this.mLayoutManager != null && (view = this.computeScrollVectorForPosition(this.mTargetPosition)) != null && (view.x != 0.0f || view.y != 0.0f)) {
                recyclerView.scrollStep((int)Math.signum(view.x), (int)Math.signum(view.y), null);
            }
            this.mPendingInitialRun = false;
            view = this.mTargetView;
            if (view != null) {
                if (this.getChildPosition(view) == this.mTargetPosition) {
                    this.onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
                    this.mRecyclingAction.runIfNecessary(recyclerView);
                    this.stop();
                } else {
                    Log.e((String)RecyclerView.TAG, (String)"Passed over target position while smooth scrolling.");
                    this.mTargetView = null;
                }
            }
            if (!this.mRunning) return;
            this.onSeekTargetStep(n, n2, recyclerView.mState, this.mRecyclingAction);
            boolean bl = this.mRecyclingAction.hasJumpTarget();
            this.mRecyclingAction.runIfNecessary(recyclerView);
            if (!bl) return;
            if (!this.mRunning) return;
            this.mPendingInitialRun = true;
            recyclerView.mViewFlinger.postOnAnimation();
        }

        protected void onChildAttachedToWindow(View view) {
            if (this.getChildPosition(view) != this.getTargetPosition()) return;
            this.mTargetView = view;
        }

        protected abstract void onSeekTargetStep(int var1, int var2, State var3, Action var4);

        protected abstract void onStart();

        protected abstract void onStop();

        protected abstract void onTargetFound(View var1, State var2, Action var3);

        public void setTargetPosition(int n) {
            this.mTargetPosition = n;
        }

        void start(RecyclerView recyclerView, LayoutManager layoutManager) {
            recyclerView.mViewFlinger.stop();
            if (this.mStarted) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("An instance of ");
                stringBuilder.append(this.getClass().getSimpleName());
                stringBuilder.append(" was started more than once. Each instance of");
                stringBuilder.append(this.getClass().getSimpleName());
                stringBuilder.append(" is intended to only be used once. You should create a new instance for each use.");
                Log.w((String)"RecyclerView", (String)stringBuilder.toString());
            }
            this.mRecyclerView = recyclerView;
            this.mLayoutManager = layoutManager;
            if (this.mTargetPosition == -1) throw new IllegalArgumentException("Invalid target position");
            recyclerView.mState.mTargetPosition = this.mTargetPosition;
            this.mRunning = true;
            this.mPendingInitialRun = true;
            this.mTargetView = this.findViewByPosition(this.getTargetPosition());
            this.onStart();
            this.mRecyclerView.mViewFlinger.postOnAnimation();
            this.mStarted = true;
        }

        protected final void stop() {
            if (!this.mRunning) {
                return;
            }
            this.mRunning = false;
            this.onStop();
            this.mRecyclerView.mState.mTargetPosition = -1;
            this.mTargetView = null;
            this.mTargetPosition = -1;
            this.mPendingInitialRun = false;
            this.mLayoutManager.onSmoothScrollerStopped(this);
            this.mLayoutManager = null;
            this.mRecyclerView = null;
        }

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            private boolean mChanged = false;
            private int mConsecutiveUpdates = 0;
            private int mDuration;
            private int mDx;
            private int mDy;
            private Interpolator mInterpolator;
            private int mJumpToPosition = -1;

            public Action(int n, int n2) {
                this(n, n2, Integer.MIN_VALUE, null);
            }

            public Action(int n, int n2, int n3) {
                this(n, n2, n3, null);
            }

            public Action(int n, int n2, int n3, Interpolator interpolator2) {
                this.mDx = n;
                this.mDy = n2;
                this.mDuration = n3;
                this.mInterpolator = interpolator2;
            }

            private void validate() {
                if (this.mInterpolator != null) {
                    if (this.mDuration < 1) throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                if (this.mDuration < 1) throw new IllegalStateException("Scroll duration must be a positive number");
            }

            public int getDuration() {
                return this.mDuration;
            }

            public int getDx() {
                return this.mDx;
            }

            public int getDy() {
                return this.mDy;
            }

            public Interpolator getInterpolator() {
                return this.mInterpolator;
            }

            boolean hasJumpTarget() {
                if (this.mJumpToPosition < 0) return false;
                return true;
            }

            public void jumpTo(int n) {
                this.mJumpToPosition = n;
            }

            void runIfNecessary(RecyclerView recyclerView) {
                int n = this.mJumpToPosition;
                if (n >= 0) {
                    this.mJumpToPosition = -1;
                    recyclerView.jumpToPositionForSmoothScroller(n);
                    this.mChanged = false;
                    return;
                }
                if (!this.mChanged) {
                    this.mConsecutiveUpdates = 0;
                    return;
                }
                this.validate();
                recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
                this.mConsecutiveUpdates = n = this.mConsecutiveUpdates + 1;
                if (n > 10) {
                    Log.e((String)RecyclerView.TAG, (String)"Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                }
                this.mChanged = false;
            }

            public void setDuration(int n) {
                this.mChanged = true;
                this.mDuration = n;
            }

            public void setDx(int n) {
                this.mChanged = true;
                this.mDx = n;
            }

            public void setDy(int n) {
                this.mChanged = true;
                this.mDy = n;
            }

            public void setInterpolator(Interpolator interpolator2) {
                this.mChanged = true;
                this.mInterpolator = interpolator2;
            }

            public void update(int n, int n2, int n3, Interpolator interpolator2) {
                this.mDx = n;
                this.mDy = n2;
                this.mDuration = n3;
                this.mInterpolator = interpolator2;
                this.mChanged = true;
            }
        }

        public static interface ScrollVectorProvider {
            public PointF computeScrollVectorForPosition(int var1);
        }

    }

    public static class State {
        static final int STEP_ANIMATIONS = 4;
        static final int STEP_LAYOUT = 2;
        static final int STEP_START = 1;
        private SparseArray<Object> mData;
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;
        long mFocusedItemId;
        int mFocusedItemPosition;
        int mFocusedSubChildId;
        boolean mInPreLayout = false;
        boolean mIsMeasuring = false;
        int mItemCount = 0;
        int mLayoutStep = 1;
        int mPreviousLayoutItemCount = 0;
        int mRemainingScrollHorizontal;
        int mRemainingScrollVertical;
        boolean mRunPredictiveAnimations = false;
        boolean mRunSimpleAnimations = false;
        boolean mStructureChanged = false;
        int mTargetPosition = -1;
        boolean mTrackOldChangeHolders = false;

        void assertLayoutStep(int n) {
            if ((this.mLayoutStep & n) != 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Layout state should be one of ");
            stringBuilder.append(Integer.toBinaryString(n));
            stringBuilder.append(" but it is ");
            stringBuilder.append(Integer.toBinaryString(this.mLayoutStep));
            throw new IllegalStateException(stringBuilder.toString());
        }

        public boolean didStructureChange() {
            return this.mStructureChanged;
        }

        public <T> T get(int n) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray != null) return (T)sparseArray.get(n);
            return null;
        }

        public int getItemCount() {
            if (!this.mInPreLayout) return this.mItemCount;
            return this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
        }

        public int getRemainingScrollHorizontal() {
            return this.mRemainingScrollHorizontal;
        }

        public int getRemainingScrollVertical() {
            return this.mRemainingScrollVertical;
        }

        public int getTargetScrollPosition() {
            return this.mTargetPosition;
        }

        public boolean hasTargetScrollPosition() {
            if (this.mTargetPosition == -1) return false;
            return true;
        }

        public boolean isMeasuring() {
            return this.mIsMeasuring;
        }

        public boolean isPreLayout() {
            return this.mInPreLayout;
        }

        void prepareForNestedPrefetch(Adapter adapter) {
            this.mLayoutStep = 1;
            this.mItemCount = adapter.getItemCount();
            this.mInPreLayout = false;
            this.mTrackOldChangeHolders = false;
            this.mIsMeasuring = false;
        }

        public void put(int n, Object object) {
            if (this.mData == null) {
                this.mData = new SparseArray();
            }
            this.mData.put(n, object);
        }

        public void remove(int n) {
            SparseArray<Object> sparseArray = this.mData;
            if (sparseArray == null) {
                return;
            }
            sparseArray.remove(n);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("State{mTargetPosition=");
            stringBuilder.append(this.mTargetPosition);
            stringBuilder.append(", mData=");
            stringBuilder.append(this.mData);
            stringBuilder.append(", mItemCount=");
            stringBuilder.append(this.mItemCount);
            stringBuilder.append(", mIsMeasuring=");
            stringBuilder.append(this.mIsMeasuring);
            stringBuilder.append(", mPreviousLayoutItemCount=");
            stringBuilder.append(this.mPreviousLayoutItemCount);
            stringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            stringBuilder.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
            stringBuilder.append(", mStructureChanged=");
            stringBuilder.append(this.mStructureChanged);
            stringBuilder.append(", mInPreLayout=");
            stringBuilder.append(this.mInPreLayout);
            stringBuilder.append(", mRunSimpleAnimations=");
            stringBuilder.append(this.mRunSimpleAnimations);
            stringBuilder.append(", mRunPredictiveAnimations=");
            stringBuilder.append(this.mRunPredictiveAnimations);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        public boolean willRunPredictiveAnimations() {
            return this.mRunPredictiveAnimations;
        }

        public boolean willRunSimpleAnimations() {
            return this.mRunSimpleAnimations;
        }
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler var1, int var2, int var3);
    }

    class ViewFlinger
    implements Runnable {
        private boolean mEatRunOnAnimationRequest = false;
        Interpolator mInterpolator = sQuinticInterpolator;
        private int mLastFlingX;
        private int mLastFlingY;
        OverScroller mOverScroller;
        private boolean mReSchedulePostAnimationCallback = false;

        ViewFlinger() {
            this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), sQuinticInterpolator);
        }

        private int computeScrollDuration(int n, int n2, int n3, int n4) {
            int n5;
            int n6 = Math.abs(n);
            boolean bl = n6 > (n5 = Math.abs(n2));
            n3 = (int)Math.sqrt(n3 * n3 + n4 * n4);
            n2 = (int)Math.sqrt(n * n + n2 * n2);
            RecyclerView recyclerView = RecyclerView.this;
            n = bl ? recyclerView.getWidth() : recyclerView.getHeight();
            n4 = n / 2;
            float f = n2;
            float f2 = n;
            float f3 = Math.min(1.0f, f * 1.0f / f2);
            f = n4;
            f3 = this.distanceInfluenceForSnapDuration(f3);
            if (n3 > 0) {
                n = Math.round(Math.abs((f + f3 * f) / (float)n3) * 1000.0f) * 4;
                return Math.min(n, 2000);
            }
            n = bl ? n6 : n5;
            n = (int)(((float)n / f2 + 1.0f) * 300.0f);
            return Math.min(n, 2000);
        }

        private float distanceInfluenceForSnapDuration(float f) {
            return (float)Math.sin((f - 0.5f) * 0.47123894f);
        }

        private void internalPostOnAnimation() {
            RecyclerView.this.removeCallbacks((Runnable)this);
            ViewCompat.postOnAnimation((View)RecyclerView.this, this);
        }

        public void fling(int n, int n2) {
            RecyclerView.this.setScrollState(2);
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            if (this.mInterpolator != sQuinticInterpolator) {
                this.mInterpolator = sQuinticInterpolator;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), sQuinticInterpolator);
            }
            this.mOverScroller.fling(0, 0, n, n2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.postOnAnimation();
        }

        void postOnAnimation() {
            if (this.mEatRunOnAnimationRequest) {
                this.mReSchedulePostAnimationCallback = true;
                return;
            }
            this.internalPostOnAnimation();
        }

        @Override
        public void run() {
            if (RecyclerView.this.mLayout == null) {
                this.stop();
                return;
            }
            this.mReSchedulePostAnimationCallback = false;
            this.mEatRunOnAnimationRequest = true;
            RecyclerView.this.consumePendingUpdateOperations();
            Object object = this.mOverScroller;
            if (object.computeScrollOffset()) {
                int n;
                int n2;
                int n3 = object.getCurrX();
                int n4 = object.getCurrY();
                int n5 = n3 - this.mLastFlingX;
                int n6 = n4 - this.mLastFlingY;
                this.mLastFlingX = n3;
                this.mLastFlingY = n4;
                RecyclerView.this.mReusableIntPair[0] = 0;
                RecyclerView.this.mReusableIntPair[1] = 0;
                Object object2 = RecyclerView.this;
                n4 = n5;
                n3 = n6;
                if (((RecyclerView)object2).dispatchNestedPreScroll(n5, n6, ((RecyclerView)object2).mReusableIntPair, null, 1)) {
                    n4 = n5 - RecyclerView.this.mReusableIntPair[0];
                    n3 = n6 - RecyclerView.this.mReusableIntPair[1];
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(n4, n3);
                }
                if (RecyclerView.this.mAdapter != null) {
                    RecyclerView.this.mReusableIntPair[0] = 0;
                    RecyclerView.this.mReusableIntPair[1] = 0;
                    object2 = RecyclerView.this;
                    ((RecyclerView)object2).scrollStep(n4, n3, ((RecyclerView)object2).mReusableIntPair);
                    n = RecyclerView.this.mReusableIntPair[0];
                    n2 = RecyclerView.this.mReusableIntPair[1];
                    int n7 = n4 - n;
                    int n8 = n3 - n2;
                    object2 = RecyclerView.this.mLayout.mSmoothScroller;
                    n4 = n;
                    n5 = n7;
                    n3 = n2;
                    n6 = n8;
                    if (object2 != null) {
                        n4 = n;
                        n5 = n7;
                        n3 = n2;
                        n6 = n8;
                        if (!((SmoothScroller)object2).isPendingInitialRun()) {
                            n4 = n;
                            n5 = n7;
                            n3 = n2;
                            n6 = n8;
                            if (((SmoothScroller)object2).isRunning()) {
                                n3 = RecyclerView.this.mState.getItemCount();
                                if (n3 == 0) {
                                    ((SmoothScroller)object2).stop();
                                    n4 = n;
                                    n5 = n7;
                                    n3 = n2;
                                    n6 = n8;
                                } else if (((SmoothScroller)object2).getTargetPosition() >= n3) {
                                    ((SmoothScroller)object2).setTargetPosition(n3 - 1);
                                    ((SmoothScroller)object2).onAnimation(n, n2);
                                    n4 = n;
                                    n5 = n7;
                                    n3 = n2;
                                    n6 = n8;
                                } else {
                                    ((SmoothScroller)object2).onAnimation(n, n2);
                                    n4 = n;
                                    n5 = n7;
                                    n3 = n2;
                                    n6 = n8;
                                }
                            }
                        }
                    }
                } else {
                    n2 = 0;
                    n5 = 0;
                    n6 = n3;
                    n3 = n5;
                    n5 = n4;
                    n4 = n2;
                }
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView.this.mReusableIntPair[0] = 0;
                RecyclerView.this.mReusableIntPair[1] = 0;
                object2 = RecyclerView.this;
                ((RecyclerView)object2).dispatchNestedScroll(n4, n3, n5, n6, null, 1, ((RecyclerView)object2).mReusableIntPair);
                n = n5 - RecyclerView.this.mReusableIntPair[0];
                n2 = n6 - RecyclerView.this.mReusableIntPair[1];
                if (n4 != 0 || n3 != 0) {
                    RecyclerView.this.dispatchOnScrolled(n4, n3);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                n6 = object.getCurrX() == object.getFinalX() ? 1 : 0;
                n5 = object.getCurrY() == object.getFinalY() ? 1 : 0;
                n6 = !object.isFinished() && (n6 == 0 && n == 0 || n5 == 0 && n2 == 0) ? 0 : 1;
                object2 = RecyclerView.this.mLayout.mSmoothScroller;
                n5 = object2 != null && ((SmoothScroller)object2).isPendingInitialRun() ? 1 : 0;
                if (n5 == 0 && n6 != 0) {
                    if (RecyclerView.this.getOverScrollMode() != 2) {
                        n4 = (int)object.getCurrVelocity();
                        n3 = n < 0 ? -n4 : (n > 0 ? n4 : 0);
                        if (n2 < 0) {
                            n4 = -n4;
                        } else if (n2 <= 0) {
                            n4 = 0;
                        }
                        RecyclerView.this.absorbGlows(n3, n4);
                    }
                    if (ALLOW_THREAD_GAP_WORK) {
                        RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions();
                    }
                } else {
                    this.postOnAnimation();
                    if (RecyclerView.this.mGapWorker != null) {
                        RecyclerView.this.mGapWorker.postFromTraversal(RecyclerView.this, n4, n3);
                    }
                }
            }
            if ((object = RecyclerView.this.mLayout.mSmoothScroller) != null && ((SmoothScroller)object).isPendingInitialRun()) {
                ((SmoothScroller)object).onAnimation(0, 0);
            }
            this.mEatRunOnAnimationRequest = false;
            if (this.mReSchedulePostAnimationCallback) {
                this.internalPostOnAnimation();
                return;
            }
            RecyclerView.this.setScrollState(0);
            RecyclerView.this.stopNestedScroll(1);
        }

        public void smoothScrollBy(int n, int n2, int n3, Interpolator interpolator2) {
            int n4 = n3;
            if (n3 == Integer.MIN_VALUE) {
                n4 = this.computeScrollDuration(n, n2, 0, 0);
            }
            Interpolator interpolator3 = interpolator2;
            if (interpolator2 == null) {
                interpolator3 = sQuinticInterpolator;
            }
            if (this.mInterpolator != interpolator3) {
                this.mInterpolator = interpolator3;
                this.mOverScroller = new OverScroller(RecyclerView.this.getContext(), interpolator3);
            }
            this.mLastFlingY = 0;
            this.mLastFlingX = 0;
            RecyclerView.this.setScrollState(2);
            this.mOverScroller.startScroll(0, 0, n, n2, n4);
            if (Build.VERSION.SDK_INT < 23) {
                this.mOverScroller.computeScrollOffset();
            }
            this.postOnAnimation();
        }

        public void stop() {
            RecyclerView.this.removeCallbacks((Runnable)this);
            this.mOverScroller.abortAnimation();
        }
    }

    public static abstract class ViewHolder {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        int mFlags;
        boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1L;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        Recycler mScrapContainer = null;
        ViewHolder mShadowedHolder = null;
        ViewHolder mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public ViewHolder(View view) {
            if (view == null) throw new IllegalArgumentException("itemView may not be null");
            this.itemView = view;
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads != null) return;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            this.mPayloads = arrayList;
            this.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
        }

        void addChangePayload(Object object) {
            if (object == null) {
                this.addFlags(1024);
                return;
            }
            if ((1024 & this.mFlags) != 0) return;
            this.createPayloadsIfNeeded();
            this.mPayloads.add(object);
        }

        void addFlags(int n) {
            this.mFlags = n | this.mFlags;
        }

        void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        void clearPayload() {
            List<Object> list = this.mPayloads;
            if (list != null) {
                list.clear();
            }
            this.mFlags &= -1025;
        }

        void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        boolean doesTransientStatePreventRecycling() {
            if ((this.mFlags & 16) != 0) return false;
            if (!ViewCompat.hasTransientState(this.itemView)) return false;
            return true;
        }

        void flagRemovedAndOffsetPosition(int n, int n2, boolean bl) {
            this.addFlags(8);
            this.offsetPosition(n2, bl);
            this.mPosition = n;
        }

        public final int getAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView != null) return recyclerView.getAdapterPositionFor(this);
            return -1;
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            int n;
            int n2 = n = this.mPreLayoutPosition;
            if (n != -1) return n2;
            return this.mPosition;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            int n;
            int n2 = n = this.mPreLayoutPosition;
            if (n != -1) return n2;
            return this.mPosition;
        }

        List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) != 0) return FULLUPDATE_PAYLOADS;
            List<Object> list = this.mPayloads;
            if (list == null) return FULLUPDATE_PAYLOADS;
            if (list.size() != 0) return this.mUnmodifiedPayloads;
            return FULLUPDATE_PAYLOADS;
        }

        boolean hasAnyOfTheFlags(int n) {
            if ((n & this.mFlags) == 0) return false;
            return true;
        }

        boolean isAdapterPositionUnknown() {
            if ((this.mFlags & 512) != 0) return true;
            if (this.isInvalid()) return true;
            return false;
        }

        boolean isAttachedToTransitionOverlay() {
            if (this.itemView.getParent() == null) return false;
            if (this.itemView.getParent() == this.mOwnerRecyclerView) return false;
            return true;
        }

        boolean isBound() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) == 0) return false;
            return bl;
        }

        boolean isInvalid() {
            if ((this.mFlags & 4) == 0) return false;
            return true;
        }

        public final boolean isRecyclable() {
            if ((this.mFlags & 16) != 0) return false;
            if (ViewCompat.hasTransientState(this.itemView)) return false;
            return true;
        }

        boolean isRemoved() {
            if ((this.mFlags & 8) == 0) return false;
            return true;
        }

        boolean isScrap() {
            if (this.mScrapContainer == null) return false;
            return true;
        }

        boolean isTmpDetached() {
            if ((this.mFlags & 256) == 0) return false;
            return true;
        }

        boolean isUpdated() {
            if ((this.mFlags & 2) == 0) return false;
            return true;
        }

        boolean needsUpdate() {
            if ((this.mFlags & 2) == 0) return false;
            return true;
        }

        void offsetPosition(int n, boolean bl) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (bl) {
                this.mPreLayoutPosition += n;
            }
            this.mPosition += n;
            if (this.itemView.getLayoutParams() == null) return;
            ((LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true;
        }

        void onEnteredHiddenState(RecyclerView recyclerView) {
            int n = this.mPendingAccessibilityState;
            this.mWasImportantForAccessibilityBeforeHidden = n != -1 ? n : ViewCompat.getImportantForAccessibility(this.itemView);
            recyclerView.setChildImportantForAccessibilityInternal(this, 4);
        }

        void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1L;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            this.clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        void saveOldPosition() {
            if (this.mOldPosition != -1) return;
            this.mOldPosition = this.mPosition;
        }

        void setFlags(int n, int n2) {
            this.mFlags = n & n2 | this.mFlags & n2;
        }

        public final void setIsRecyclable(boolean bl) {
            int n = this.mIsRecyclableCount;
            n = bl ? --n : ++n;
            this.mIsRecyclableCount = n;
            if (n < 0) {
                this.mIsRecyclableCount = 0;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
                stringBuilder.append(this);
                Log.e((String)"View", (String)stringBuilder.toString());
                return;
            }
            if (!bl && n == 1) {
                this.mFlags |= 16;
                return;
            }
            if (!bl) return;
            if (this.mIsRecyclableCount != 0) return;
            this.mFlags &= -17;
        }

        void setScrapContainer(Recycler recycler, boolean bl) {
            this.mScrapContainer = recycler;
            this.mInChangeScrap = bl;
        }

        boolean shouldBeKeptAsChild() {
            if ((this.mFlags & 16) == 0) return false;
            return true;
        }

        boolean shouldIgnore() {
            if ((this.mFlags & 128) == 0) return false;
            return true;
        }

        void stopIgnoring() {
            this.mFlags &= -129;
        }

        public String toString() {
            CharSequence charSequence = this.getClass().isAnonymousClass() ? "ViewHolder" : this.getClass().getSimpleName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("{");
            stringBuilder.append(Integer.toHexString(this.hashCode()));
            stringBuilder.append(" position=");
            stringBuilder.append(this.mPosition);
            stringBuilder.append(" id=");
            stringBuilder.append(this.mItemId);
            stringBuilder.append(", oldPos=");
            stringBuilder.append(this.mOldPosition);
            stringBuilder.append(", pLpos:");
            stringBuilder.append(this.mPreLayoutPosition);
            stringBuilder = new StringBuilder(stringBuilder.toString());
            if (this.isScrap()) {
                stringBuilder.append(" scrap ");
                charSequence = this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]";
                stringBuilder.append((String)charSequence);
            }
            if (this.isInvalid()) {
                stringBuilder.append(" invalid");
            }
            if (!this.isBound()) {
                stringBuilder.append(" unbound");
            }
            if (this.needsUpdate()) {
                stringBuilder.append(" update");
            }
            if (this.isRemoved()) {
                stringBuilder.append(" removed");
            }
            if (this.shouldIgnore()) {
                stringBuilder.append(" ignored");
            }
            if (this.isTmpDetached()) {
                stringBuilder.append(" tmpDetached");
            }
            if (!this.isRecyclable()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" not recyclable(");
                ((StringBuilder)charSequence).append(this.mIsRecyclableCount);
                ((StringBuilder)charSequence).append(")");
                stringBuilder.append(((StringBuilder)charSequence).toString());
            }
            if (this.isAdapterPositionUnknown()) {
                stringBuilder.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                stringBuilder.append(" no parent");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void unScrap() {
            this.mScrapContainer.unscrapView(this);
        }

        boolean wasReturnedFromScrap() {
            if ((this.mFlags & 32) == 0) return false;
            return true;
        }
    }

}

