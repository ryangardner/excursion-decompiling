/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.animation.Interpolator
 *  android.widget.TextView
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.TextView;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.DesignTool;
import androidx.constraintlayout.motion.widget.KeyCache;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionInterpolator;
import androidx.constraintlayout.motion.widget.MotionPaths;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.motion.widget.TouchResponse;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Flow;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import androidx.core.view.NestedScrollingParent3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MotionLayout
extends ConstraintLayout
implements NestedScrollingParent3 {
    private static final boolean DEBUG = false;
    public static final int DEBUG_SHOW_NONE = 0;
    public static final int DEBUG_SHOW_PATH = 2;
    public static final int DEBUG_SHOW_PROGRESS = 1;
    private static final float EPSILON = 1.0E-5f;
    public static boolean IS_IN_EDIT_MODE = false;
    static final int MAX_KEY_FRAMES = 50;
    static final String TAG = "MotionLayout";
    public static final int TOUCH_UP_COMPLETE = 0;
    public static final int TOUCH_UP_COMPLETE_TO_END = 2;
    public static final int TOUCH_UP_COMPLETE_TO_START = 1;
    public static final int TOUCH_UP_DECELERATE = 4;
    public static final int TOUCH_UP_DECELERATE_AND_COMPLETE = 5;
    public static final int TOUCH_UP_STOP = 3;
    public static final int VELOCITY_LAYOUT = 1;
    public static final int VELOCITY_POST_LAYOUT = 0;
    public static final int VELOCITY_STATIC_LAYOUT = 3;
    public static final int VELOCITY_STATIC_POST_LAYOUT = 2;
    boolean firstDown = true;
    private float lastPos;
    private float lastY;
    private long mAnimationStartTime = 0L;
    private int mBeginState = -1;
    private RectF mBoundsCheck = new RectF();
    int mCurrentState = -1;
    int mDebugPath = 0;
    private DecelerateInterpolator mDecelerateLogic = new DecelerateInterpolator();
    private DesignTool mDesignTool;
    DevModeDraw mDevModeDraw;
    private int mEndState = -1;
    int mEndWrapHeight;
    int mEndWrapWidth;
    HashMap<View, MotionController> mFrameArrayList = new HashMap();
    private int mFrames = 0;
    int mHeightMeasureMode;
    private boolean mInLayout = false;
    boolean mInTransition = false;
    boolean mIndirectTransition = false;
    private boolean mInteractionEnabled = true;
    Interpolator mInterpolator;
    boolean mIsAnimating = false;
    private boolean mKeepAnimating = false;
    private KeyCache mKeyCache = new KeyCache();
    private long mLastDrawTime = -1L;
    private float mLastFps = 0.0f;
    private int mLastHeightMeasureSpec = 0;
    int mLastLayoutHeight;
    int mLastLayoutWidth;
    float mLastVelocity = 0.0f;
    private int mLastWidthMeasureSpec = 0;
    private float mListenerPosition = 0.0f;
    private int mListenerState = 0;
    protected boolean mMeasureDuringTransition = false;
    Model mModel = new Model();
    private boolean mNeedsFireTransitionCompleted = false;
    int mOldHeight;
    int mOldWidth;
    private ArrayList<MotionHelper> mOnHideHelpers = null;
    private ArrayList<MotionHelper> mOnShowHelpers = null;
    float mPostInterpolationPosition;
    private View mRegionView = null;
    MotionScene mScene;
    float mScrollTargetDT;
    float mScrollTargetDX;
    float mScrollTargetDY;
    long mScrollTargetTime;
    int mStartWrapHeight;
    int mStartWrapWidth;
    private StateCache mStateCache;
    private StopLogic mStopLogic = new StopLogic();
    private boolean mTemporalInterpolator = false;
    ArrayList<Integer> mTransitionCompleted = new ArrayList();
    private float mTransitionDuration = 1.0f;
    float mTransitionGoalPosition = 0.0f;
    private boolean mTransitionInstantly;
    float mTransitionLastPosition = 0.0f;
    private long mTransitionLastTime;
    private TransitionListener mTransitionListener;
    private ArrayList<TransitionListener> mTransitionListeners = null;
    float mTransitionPosition = 0.0f;
    TransitionState mTransitionState = TransitionState.UNDEFINED;
    boolean mUndergoingMotion = false;
    int mWidthMeasureMode;

    public MotionLayout(Context context) {
        super(context);
        this.init(null);
    }

    public MotionLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(attributeSet);
    }

    public MotionLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(attributeSet);
    }

    private void checkStructure() {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            Log.e((String)TAG, (String)"CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
            return;
        }
        int n = motionScene.getStartId();
        motionScene = this.mScene;
        this.checkStructure(n, motionScene.getConstraintSet(motionScene.getStartId()));
        motionScene = new SparseIntArray();
        SparseIntArray sparseIntArray = new SparseIntArray();
        Iterator<MotionScene.Transition> iterator2 = this.mScene.getDefinedTransitions().iterator();
        while (iterator2.hasNext()) {
            StringBuilder stringBuilder;
            Object object = iterator2.next();
            if (object == this.mScene.mCurrentTransition) {
                Log.v((String)TAG, (String)"CHECK: CURRENT");
            }
            this.checkStructure((MotionScene.Transition)object);
            n = ((MotionScene.Transition)object).getStartConstraintSetId();
            int n2 = ((MotionScene.Transition)object).getEndConstraintSetId();
            object = Debug.getName(this.getContext(), n);
            CharSequence charSequence = Debug.getName(this.getContext(), n2);
            if (motionScene.get(n) == n2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: two transitions with the same start and end ");
                stringBuilder.append((String)object);
                stringBuilder.append("->");
                stringBuilder.append((String)charSequence);
                Log.e((String)TAG, (String)stringBuilder.toString());
            }
            if (sparseIntArray.get(n2) == n) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: you can't have reverse transitions");
                stringBuilder.append((String)object);
                stringBuilder.append("->");
                stringBuilder.append((String)charSequence);
                Log.e((String)TAG, (String)stringBuilder.toString());
            }
            motionScene.put(n, n2);
            sparseIntArray.put(n2, n);
            if (this.mScene.getConstraintSet(n) == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" no such constraintSetStart ");
                ((StringBuilder)charSequence).append((String)object);
                Log.e((String)TAG, (String)((StringBuilder)charSequence).toString());
            }
            if (this.mScene.getConstraintSet(n2) != null) continue;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" no such constraintSetEnd ");
            ((StringBuilder)charSequence).append((String)object);
            Log.e((String)TAG, (String)((StringBuilder)charSequence).toString());
        }
    }

    private void checkStructure(int n, ConstraintSet constraintSet) {
        CharSequence charSequence;
        View view;
        String string2 = Debug.getName(this.getContext(), n);
        int n2 = this.getChildCount();
        Object object = 0;
        for (n = 0; n < n2; ++n) {
            view = this.getChildAt(n);
            int n3 = view.getId();
            if (n3 == -1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("CHECK: ");
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(" ALL VIEWS SHOULD HAVE ID's ");
                ((StringBuilder)charSequence).append(view.getClass().getName());
                ((StringBuilder)charSequence).append(" does not!");
                Log.w((String)TAG, (String)((StringBuilder)charSequence).toString());
            }
            if (constraintSet.getConstraint(n3) != null) continue;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("CHECK: ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" NO CONSTRAINTS for ");
            ((StringBuilder)charSequence).append(Debug.getName(view));
            Log.w((String)TAG, (String)((StringBuilder)charSequence).toString());
        }
        view = constraintSet.getKnownIds();
        n = object;
        while (n < ((View)view).length) {
            StringBuilder stringBuilder;
            object = (Object)view[n];
            charSequence = Debug.getName(this.getContext(), object);
            if (this.findViewById((int)view[n]) == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: ");
                stringBuilder.append(string2);
                stringBuilder.append(" NO View matches id ");
                stringBuilder.append((String)charSequence);
                Log.w((String)TAG, (String)stringBuilder.toString());
            }
            if (constraintSet.getHeight((int)object) == -1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: ");
                stringBuilder.append(string2);
                stringBuilder.append("(");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(") no LAYOUT_HEIGHT");
                Log.w((String)TAG, (String)stringBuilder.toString());
            }
            if (constraintSet.getWidth((int)object) == -1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("CHECK: ");
                stringBuilder.append(string2);
                stringBuilder.append("(");
                stringBuilder.append((String)charSequence);
                stringBuilder.append(") no LAYOUT_HEIGHT");
                Log.w((String)TAG, (String)stringBuilder.toString());
            }
            ++n;
        }
    }

    private void checkStructure(MotionScene.Transition transition) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: transition = ");
        stringBuilder.append(transition.debugString(this.getContext()));
        Log.v((String)TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("CHECK: transition.setDuration = ");
        stringBuilder.append(transition.getDuration());
        Log.v((String)TAG, (String)stringBuilder.toString());
        if (transition.getStartConstraintSetId() != transition.getEndConstraintSetId()) return;
        Log.e((String)TAG, (String)"CHECK: start and end constraint set should not be the same!");
    }

    private void computeCurrentPositions() {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = this.getChildAt(n2);
            MotionController motionController = this.mFrameArrayList.get((Object)view);
            if (motionController != null) {
                motionController.setStartCurrentState(view);
            }
            ++n2;
        }
    }

    private void debugPos() {
        int n = 0;
        while (n < this.getChildCount()) {
            View view = this.getChildAt(n);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" ");
            stringBuilder.append(Debug.getLocation());
            stringBuilder.append(" ");
            stringBuilder.append(Debug.getName((View)this));
            stringBuilder.append(" ");
            stringBuilder.append(Debug.getName(this.getContext(), this.mCurrentState));
            stringBuilder.append(" ");
            stringBuilder.append(Debug.getName(view));
            stringBuilder.append(view.getLeft());
            stringBuilder.append(" ");
            stringBuilder.append(view.getTop());
            Log.v((String)TAG, (String)stringBuilder.toString());
            ++n;
        }
    }

    private void evaluateLayout() {
        float f;
        float f2;
        long l;
        Object object;
        int n;
        int n2;
        block12 : {
            block11 : {
                float f3 = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
                l = this.getNanoTime();
                float f4 = !(this.mInterpolator instanceof StopLogic) ? (float)(l - this.mTransitionLastTime) * f3 * 1.0E-9f / this.mTransitionDuration : 0.0f;
                f = this.mTransitionLastPosition + f4;
                if (this.mTransitionInstantly) {
                    f = this.mTransitionGoalPosition;
                }
                n2 = 0;
                f2 = f3 FCMPL 0.0f;
                if (f2 > 0 && f >= this.mTransitionGoalPosition || f3 <= 0.0f && f <= this.mTransitionGoalPosition) {
                    f = this.mTransitionGoalPosition;
                    n = 1;
                } else {
                    n = 0;
                }
                object = this.mInterpolator;
                f4 = f;
                if (object != null) {
                    f4 = f;
                    if (n == 0) {
                        f4 = this.mTemporalInterpolator ? object.getInterpolation((float)(l - this.mAnimationStartTime) * 1.0E-9f) : object.getInterpolation(f);
                    }
                }
                if (f2 > 0 && f4 >= this.mTransitionGoalPosition) break block11;
                f = f4;
                if (!(f3 <= 0.0f)) break block12;
                f = f4;
                if (!(f4 <= this.mTransitionGoalPosition)) break block12;
            }
            f = this.mTransitionGoalPosition;
        }
        this.mPostInterpolationPosition = f;
        f2 = this.getChildCount();
        l = this.getNanoTime();
        n = n2;
        do {
            if (n >= f2) {
                if (!this.mMeasureDuringTransition) return;
                this.requestLayout();
                return;
            }
            View view = this.getChildAt(n);
            object = this.mFrameArrayList.get((Object)view);
            if (object != null) {
                ((MotionController)object).interpolate(view, f, l, this.mKeyCache);
            }
            ++n;
        } while (true);
    }

    private void fireTransitionChange() {
        Object object;
        float f;
        if (this.mTransitionListener == null) {
            object = this.mTransitionListeners;
            if (object == null) return;
            if (((ArrayList)object).isEmpty()) return;
        }
        if (this.mListenerPosition == this.mTransitionPosition) return;
        if (this.mListenerState != -1) {
            object = this.mTransitionListener;
            if (object != null) {
                object.onTransitionStarted(this, this.mBeginState, this.mEndState);
            }
            if ((object = this.mTransitionListeners) != null) {
                object = ((ArrayList)object).iterator();
                while (object.hasNext()) {
                    object.next().onTransitionStarted(this, this.mBeginState, this.mEndState);
                }
            }
            this.mIsAnimating = true;
        }
        this.mListenerState = -1;
        this.mListenerPosition = f = this.mTransitionPosition;
        object = this.mTransitionListener;
        if (object != null) {
            object.onTransitionChange(this, this.mBeginState, this.mEndState, f);
        }
        if ((object = this.mTransitionListeners) != null) {
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                ((TransitionListener)object.next()).onTransitionChange(this, this.mBeginState, this.mEndState, this.mTransitionPosition);
            }
        }
        this.mIsAnimating = true;
    }

    private void fireTransitionStarted(MotionLayout motionLayout, int n, int n2) {
        Object object = this.mTransitionListener;
        if (object != null) {
            object.onTransitionStarted(this, n, n2);
        }
        if ((object = this.mTransitionListeners) == null) return;
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            ((TransitionListener)object.next()).onTransitionStarted(motionLayout, n, n2);
        }
    }

    private boolean handlesTouchEvent(float f, float f2, View view, MotionEvent motionEvent) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n = viewGroup.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view2 = viewGroup.getChildAt(i);
                if (!this.handlesTouchEvent((float)view.getLeft() + f, (float)view.getTop() + f2, view2, motionEvent)) continue;
                return true;
            }
        }
        this.mBoundsCheck.set((float)view.getLeft() + f, (float)view.getTop() + f2, f + (float)view.getRight(), f2 + (float)view.getBottom());
        if (motionEvent.getAction() == 0) {
            if (!this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY())) return false;
            if (!view.onTouchEvent(motionEvent)) return false;
            return true;
        }
        if (!view.onTouchEvent(motionEvent)) return false;
        return true;
    }

    private void init(AttributeSet object) {
        IS_IN_EDIT_MODE = this.isInEditMode();
        if (object != null) {
            object = this.getContext().obtainStyledAttributes((AttributeSet)object, R.styleable.MotionLayout);
            int n = object.getIndexCount();
            boolean bl = true;
            for (int i = 0; i < n; ++i) {
                boolean bl2;
                int n2 = object.getIndex(i);
                if (n2 == R.styleable.MotionLayout_layoutDescription) {
                    n2 = object.getResourceId(n2, -1);
                    this.mScene = new MotionScene(this.getContext(), this, n2);
                    bl2 = bl;
                } else if (n2 == R.styleable.MotionLayout_currentState) {
                    this.mCurrentState = object.getResourceId(n2, -1);
                    bl2 = bl;
                } else if (n2 == R.styleable.MotionLayout_motionProgress) {
                    this.mTransitionGoalPosition = object.getFloat(n2, 0.0f);
                    this.mInTransition = true;
                    bl2 = bl;
                } else if (n2 == R.styleable.MotionLayout_applyMotionScene) {
                    bl2 = object.getBoolean(n2, bl);
                } else if (n2 == R.styleable.MotionLayout_showPaths) {
                    bl2 = bl;
                    if (this.mDebugPath == 0) {
                        n2 = object.getBoolean(n2, false) ? 2 : 0;
                        this.mDebugPath = n2;
                        bl2 = bl;
                    }
                } else {
                    bl2 = bl;
                    if (n2 == R.styleable.MotionLayout_motionDebug) {
                        this.mDebugPath = object.getInt(n2, 0);
                        bl2 = bl;
                    }
                }
                bl = bl2;
            }
            object.recycle();
            if (this.mScene == null) {
                Log.e((String)TAG, (String)"WARNING NO app:layoutDescription tag");
            }
            if (!bl) {
                this.mScene = null;
            }
        }
        if (this.mDebugPath != 0) {
            this.checkStructure();
        }
        if (this.mCurrentState != -1) return;
        object = this.mScene;
        if (object == null) return;
        this.mCurrentState = ((MotionScene)object).getStartId();
        this.mBeginState = this.mScene.getStartId();
        this.mEndState = this.mScene.getEndId();
    }

    private void onNewStateAttachHandlers() {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            return;
        }
        if (motionScene.autoTransition(this, this.mCurrentState)) {
            this.requestLayout();
            return;
        }
        int n = this.mCurrentState;
        if (n != -1) {
            this.mScene.addOnClickListeners(this, n);
        }
        if (!this.mScene.supportTouch()) return;
        this.mScene.setupTouch();
    }

    /*
     * Unable to fully structure code
     */
    private void processTransitionCompleted() {
        if (this.mTransitionListener == null) {
            var1_1 = this.mTransitionListeners;
            if (var1_1 == null) return;
            if (var1_1.isEmpty()) {
                return;
            }
        }
        this.mIsAnimating = false;
        var1_1 = this.mTransitionCompleted.iterator();
        block0 : do lbl-1000: // 3 sources:
        {
            if (!var1_1.hasNext()) {
                this.mTransitionCompleted.clear();
                return;
            }
            var2_2 = (Integer)var1_1.next();
            var3_3 = this.mTransitionListener;
            if (var3_3 != null) {
                var3_3.onTransitionCompleted(this, var2_2);
            }
            if ((var3_3 = this.mTransitionListeners) == null) ** GOTO lbl-1000
            var3_3 = var3_3.iterator();
            do {
                if (!var3_3.hasNext()) continue block0;
                ((TransitionListener)var3_3.next()).onTransitionCompleted(this, var2_2);
            } while (true);
            break;
        } while (true);
    }

    private void setupMotionViews() {
        int n;
        MotionController motionController;
        int n2;
        float f;
        float f2;
        int n3;
        float f3;
        float f4;
        int n4;
        float f5;
        int n5;
        float f6;
        block11 : {
            n2 = this.getChildCount();
            this.mModel.build();
            int n6 = 1;
            this.mInTransition = true;
            int n7 = this.getWidth();
            n = this.getHeight();
            int n8 = this.mScene.gatPathMotionArc();
            n5 = 0;
            n3 = 0;
            if (n8 != -1) {
                for (n4 = 0; n4 < n2; ++n4) {
                    motionController = this.mFrameArrayList.get((Object)this.getChildAt(n4));
                    if (motionController == null) continue;
                    motionController.setPathMotionArc(n8);
                }
            }
            for (n4 = 0; n4 < n2; ++n4) {
                motionController = this.mFrameArrayList.get((Object)this.getChildAt(n4));
                if (motionController == null) continue;
                this.mScene.getKeyFrames(motionController);
                motionController.setup(n7, n, this.mTransitionDuration, this.getNanoTime());
            }
            f3 = this.mScene.getStaggered();
            if (f3 == 0.0f) return;
            n4 = (double)f3 < 0.0 ? 1 : 0;
            f5 = Math.abs(f3);
            f6 = -3.4028235E38f;
            f2 = Float.MAX_VALUE;
            f = Float.MAX_VALUE;
            f3 = -3.4028235E38f;
            for (n = 0; n < n2; ++n) {
                motionController = this.mFrameArrayList.get((Object)this.getChildAt(n));
                if (!Float.isNaN(motionController.mMotionStagger)) {
                    n = n6;
                    break block11;
                }
                f4 = motionController.getFinalX();
                float f7 = motionController.getFinalY();
                f4 = n4 != 0 ? f7 - f4 : f7 + f4;
                f = Math.min(f, f4);
                f3 = Math.max(f3, f4);
            }
            n = 0;
        }
        if (n != 0) {
            n5 = 0;
            f = f2;
            f3 = f6;
            do {
                n = n3;
                if (n5 >= n2) break;
                motionController = this.mFrameArrayList.get((Object)this.getChildAt(n5));
                f6 = f3;
                f4 = f;
                if (!Float.isNaN(motionController.mMotionStagger)) {
                    f4 = Math.min(f, motionController.mMotionStagger);
                    f6 = Math.max(f3, motionController.mMotionStagger);
                }
                ++n5;
                f3 = f6;
                f = f4;
            } while (true);
            while (n < n2) {
                motionController = this.mFrameArrayList.get((Object)this.getChildAt(n));
                if (!Float.isNaN(motionController.mMotionStagger)) {
                    motionController.mStaggerScale = 1.0f / (1.0f - f5);
                    motionController.mStaggerOffset = n4 != 0 ? f5 - (f3 - motionController.mMotionStagger) / (f3 - f) * f5 : f5 - (motionController.mMotionStagger - f) * f5 / (f3 - f);
                }
                ++n;
            }
            return;
        }
        while (n5 < n2) {
            motionController = this.mFrameArrayList.get((Object)this.getChildAt(n5));
            f6 = motionController.getFinalX();
            f4 = motionController.getFinalY();
            f4 = n4 != 0 ? (f4 -= f6) : (f4 += f6);
            motionController.mStaggerScale = 1.0f / (1.0f - f5);
            motionController.mStaggerOffset = f5 - (f4 - f) * f5 / (f3 - f);
            ++n5;
        }
    }

    private static boolean willJump(float f, float f2, float f3) {
        boolean bl = true;
        boolean bl2 = true;
        if (f > 0.0f) {
            float f4 = f / f3;
            if (!(f2 + (f * f4 - f3 * f4 * f4 / 2.0f) > 1.0f)) return false;
            return bl2;
        }
        float f5 = -f / f3;
        if (!(f2 + (f * f5 + f3 * f5 * f5 / 2.0f) < 0.0f)) return false;
        return bl;
    }

    public void addTransitionListener(TransitionListener transitionListener) {
        if (this.mTransitionListeners == null) {
            this.mTransitionListeners = new ArrayList();
        }
        this.mTransitionListeners.add(transitionListener);
    }

    void animateTo(float f) {
        if (this.mScene == null) {
            return;
        }
        float f2 = this.mTransitionLastPosition;
        float f3 = this.mTransitionPosition;
        if (f2 != f3 && this.mTransitionInstantly) {
            this.mTransitionLastPosition = f3;
        }
        if ((f3 = this.mTransitionLastPosition) == f) {
            return;
        }
        this.mTemporalInterpolator = false;
        this.mTransitionGoalPosition = f;
        this.mTransitionDuration = (float)this.mScene.getDuration() / 1000.0f;
        this.setProgress(this.mTransitionGoalPosition);
        this.mInterpolator = this.mScene.getInterpolator();
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = this.getNanoTime();
        this.mInTransition = true;
        this.mTransitionPosition = f3;
        this.mTransitionLastPosition = f3;
        this.invalidate();
    }

    void disableAutoTransition(boolean bl) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            return;
        }
        motionScene.disableAutoTransition(bl);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        this.evaluate(false);
        super.dispatchDraw(canvas);
        if (this.mScene == null) {
            return;
        }
        if ((this.mDebugPath & 1) == 1 && !this.isInEditMode()) {
            ++this.mFrames;
            long l = this.getNanoTime();
            long l2 = this.mLastDrawTime;
            if (l2 != -1L) {
                if ((l2 = l - l2) > 200000000L) {
                    this.mLastFps = (float)((int)((float)this.mFrames / ((float)l2 * 1.0E-9f) * 100.0f)) / 100.0f;
                    this.mFrames = 0;
                    this.mLastDrawTime = l;
                }
            } else {
                this.mLastDrawTime = l;
            }
            Paint paint = new Paint();
            paint.setTextSize(42.0f);
            float f = (float)((int)(this.getProgress() * 1000.0f)) / 10.0f;
            CharSequence charSequence = new StringBuilder();
            charSequence.append(this.mLastFps);
            charSequence.append(" fps ");
            charSequence.append(Debug.getState(this, this.mBeginState));
            charSequence.append(" -> ");
            charSequence = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(Debug.getState(this, this.mEndState));
            stringBuilder.append(" (progress: ");
            stringBuilder.append(f);
            stringBuilder.append(" ) state=");
            int n = this.mCurrentState;
            charSequence = n == -1 ? "undefined" : Debug.getState(this, n);
            stringBuilder.append((String)charSequence);
            charSequence = stringBuilder.toString();
            paint.setColor(-16777216);
            canvas.drawText((String)charSequence, 11.0f, (float)(this.getHeight() - 29), paint);
            paint.setColor(-7864184);
            canvas.drawText((String)charSequence, 10.0f, (float)(this.getHeight() - 30), paint);
        }
        if (this.mDebugPath <= 1) return;
        if (this.mDevModeDraw == null) {
            this.mDevModeDraw = new DevModeDraw();
        }
        this.mDevModeDraw.draw(canvas, this.mFrameArrayList, this.mScene.getDuration(), this.mDebugPath);
    }

    public void enableTransition(int n, boolean bl) {
        MotionScene.Transition transition = this.getTransition(n);
        if (bl) {
            transition.setEnable(true);
            return;
        }
        if (transition == this.mScene.mCurrentTransition) {
            for (MotionScene.Transition transition2 : this.mScene.getTransitionsWithState(this.mCurrentState)) {
                if (!transition2.isEnabled()) continue;
                this.mScene.mCurrentTransition = transition2;
                break;
            }
        }
        transition.setEnable(false);
    }

    void evaluate(boolean bl) {
        int n;
        block55 : {
            int n2;
            block54 : {
                int n3;
                float f;
                block53 : {
                    int n4;
                    block49 : {
                        block52 : {
                            float f2;
                            float f3;
                            long l;
                            float f4;
                            Interpolator interpolator2;
                            int n5;
                            block51 : {
                                block50 : {
                                    float f5;
                                    block48 : {
                                        if (this.mTransitionLastTime == -1L) {
                                            this.mTransitionLastTime = this.getNanoTime();
                                        }
                                        if ((f = this.mTransitionLastPosition) > 0.0f && f < 1.0f) {
                                            this.mCurrentState = -1;
                                        }
                                        boolean bl2 = this.mKeepAnimating;
                                        n3 = 1;
                                        n4 = 1;
                                        n5 = 0;
                                        n = 0;
                                        if (bl2) break block48;
                                        n2 = n5;
                                        if (!this.mInTransition) break block49;
                                        if (bl) break block48;
                                        n2 = n5;
                                        if (this.mTransitionGoalPosition == this.mTransitionLastPosition) break block49;
                                    }
                                    f4 = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
                                    l = this.getNanoTime();
                                    if (!(this.mInterpolator instanceof MotionInterpolator)) {
                                        this.mLastVelocity = f5 = (float)(l - this.mTransitionLastTime) * f4 * 1.0E-9f / this.mTransitionDuration;
                                    } else {
                                        f5 = 0.0f;
                                    }
                                    f2 = this.mTransitionLastPosition + f5;
                                    if (this.mTransitionInstantly) {
                                        f2 = this.mTransitionGoalPosition;
                                    }
                                    if ((n5 = (f4 FCMPL 0.0f)) > 0 && f2 >= this.mTransitionGoalPosition || f4 <= 0.0f && f2 <= this.mTransitionGoalPosition) {
                                        f2 = this.mTransitionGoalPosition;
                                        this.mInTransition = false;
                                        n2 = 1;
                                    } else {
                                        n2 = 0;
                                    }
                                    this.mTransitionLastPosition = f2;
                                    this.mTransitionPosition = f2;
                                    this.mTransitionLastTime = l;
                                    interpolator2 = this.mInterpolator;
                                    f = f2;
                                    if (interpolator2 != null) {
                                        f = f2;
                                        if (n2 == 0) {
                                            if (this.mTemporalInterpolator) {
                                                this.mTransitionLastPosition = f5 = interpolator2.getInterpolation((float)(l - this.mAnimationStartTime) * 1.0E-9f);
                                                this.mTransitionLastTime = l;
                                                interpolator2 = this.mInterpolator;
                                                f = f5;
                                                if (interpolator2 instanceof MotionInterpolator) {
                                                    float f6;
                                                    this.mLastVelocity = f6 = ((MotionInterpolator)interpolator2).getVelocity();
                                                    if (Math.abs(f6) * this.mTransitionDuration <= 1.0E-5f) {
                                                        this.mInTransition = false;
                                                    }
                                                    f2 = f5;
                                                    if (f6 > 0.0f) {
                                                        f2 = f5;
                                                        if (f5 >= 1.0f) {
                                                            this.mTransitionLastPosition = 1.0f;
                                                            this.mInTransition = false;
                                                            f2 = 1.0f;
                                                        }
                                                    }
                                                    f = f2;
                                                    if (f6 < 0.0f) {
                                                        f = f2;
                                                        if (f2 <= 0.0f) {
                                                            this.mTransitionLastPosition = 0.0f;
                                                            this.mInTransition = false;
                                                            f = 0.0f;
                                                        }
                                                    }
                                                }
                                            } else {
                                                f = interpolator2.getInterpolation(f2);
                                                interpolator2 = this.mInterpolator;
                                                this.mLastVelocity = interpolator2 instanceof MotionInterpolator ? ((MotionInterpolator)interpolator2).getVelocity() : (interpolator2.getInterpolation(f2 + f5) - f) * f4 / f5;
                                            }
                                        }
                                    }
                                    if (Math.abs(this.mLastVelocity) > 1.0E-5f) {
                                        this.setState(TransitionState.MOVING);
                                    }
                                    if (n5 > 0 && f >= this.mTransitionGoalPosition) break block50;
                                    f2 = f;
                                    if (!(f4 <= 0.0f)) break block51;
                                    f2 = f;
                                    if (!(f <= this.mTransitionGoalPosition)) break block51;
                                }
                                f2 = this.mTransitionGoalPosition;
                                this.mInTransition = false;
                            }
                            if ((f3 = (f2 FCMPL 1.0f)) >= 0 || f2 <= 0.0f) {
                                this.mInTransition = false;
                                this.setState(TransitionState.FINISHED);
                            }
                            int n6 = this.getChildCount();
                            this.mKeepAnimating = false;
                            l = this.getNanoTime();
                            this.mPostInterpolationPosition = f2;
                            for (n2 = 0; n2 < n6; ++n2) {
                                interpolator2 = this.getChildAt(n2);
                                MotionController motionController = this.mFrameArrayList.get((Object)interpolator2);
                                if (motionController == null) continue;
                                bl = this.mKeepAnimating;
                                this.mKeepAnimating = motionController.interpolate((View)interpolator2, f2, l, this.mKeyCache) | bl;
                            }
                            n2 = n5 > 0 && f2 >= this.mTransitionGoalPosition || f4 <= 0.0f && f2 <= this.mTransitionGoalPosition ? 1 : 0;
                            if (!this.mKeepAnimating && !this.mInTransition && n2 != 0) {
                                this.setState(TransitionState.FINISHED);
                            }
                            if (this.mMeasureDuringTransition) {
                                this.requestLayout();
                            }
                            this.mKeepAnimating = n2 ^ 1 | this.mKeepAnimating;
                            n2 = n;
                            if (f2 <= 0.0f) {
                                n6 = this.mBeginState;
                                n2 = n;
                                if (n6 != -1) {
                                    n2 = n;
                                    if (this.mCurrentState != n6) {
                                        this.mCurrentState = n6;
                                        this.mScene.getConstraintSet(n6).applyCustomAttributes(this);
                                        this.setState(TransitionState.FINISHED);
                                        n2 = 1;
                                    }
                                }
                            }
                            n = n2;
                            if ((double)f2 >= 1.0) {
                                int n7 = this.mCurrentState;
                                n6 = this.mEndState;
                                n = n2;
                                if (n7 != n6) {
                                    this.mCurrentState = n6;
                                    this.mScene.getConstraintSet(n6).applyCustomAttributes(this);
                                    this.setState(TransitionState.FINISHED);
                                    n = 1;
                                }
                            }
                            if (!this.mKeepAnimating && !this.mInTransition) {
                                if (n5 > 0 && f3 == false || f4 < 0.0f && f2 == 0.0f) {
                                    this.setState(TransitionState.FINISHED);
                                }
                            } else {
                                this.invalidate();
                            }
                            if (!this.mKeepAnimating && this.mInTransition && n5 > 0 && f3 == false) break block52;
                            n2 = n;
                            if (!(f4 < 0.0f)) break block49;
                            n2 = n;
                            if (f2 != 0.0f) break block49;
                        }
                        this.onNewStateAttachHandlers();
                        n2 = n;
                    }
                    if (!((f = this.mTransitionLastPosition) >= 1.0f)) break block53;
                    if (this.mCurrentState != this.mEndState) {
                        n2 = n4;
                    }
                    this.mCurrentState = this.mEndState;
                    break block54;
                }
                n = n2;
                if (!(f <= 0.0f)) break block55;
                if (this.mCurrentState != this.mBeginState) {
                    n2 = n3;
                }
                this.mCurrentState = this.mBeginState;
            }
            n = n2;
        }
        this.mNeedsFireTransitionCompleted |= n;
        if (n != 0 && !this.mInLayout) {
            this.requestLayout();
        }
        this.mTransitionPosition = this.mTransitionLastPosition;
    }

    protected void fireTransitionCompleted() {
        ArrayList<Object> arrayList;
        if ((this.mTransitionListener != null || (arrayList = this.mTransitionListeners) != null && !arrayList.isEmpty()) && this.mListenerState == -1) {
            int n;
            this.mListenerState = this.mCurrentState;
            if (!this.mTransitionCompleted.isEmpty()) {
                arrayList = this.mTransitionCompleted;
                n = (Integer)arrayList.get(arrayList.size() - 1);
            } else {
                n = -1;
            }
            int n2 = this.mCurrentState;
            if (n != n2 && n2 != -1) {
                this.mTransitionCompleted.add(n2);
            }
        }
        this.processTransitionCompleted();
    }

    public void fireTrigger(int n, boolean bl, float f) {
        Object object = this.mTransitionListener;
        if (object != null) {
            object.onTransitionTrigger(this, n, bl, f);
        }
        if ((object = this.mTransitionListeners) == null) return;
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            ((TransitionListener)object.next()).onTransitionTrigger(this, n, bl, f);
        }
    }

    void getAnchorDpDt(int n, float f, float f2, float f3, float[] object) {
        Object object2 = this.mFrameArrayList;
        Object object3 = this.getViewById(n);
        if ((object2 = ((HashMap)object2).get(object3)) != null) {
            ((MotionController)object2).getDpDt(f, f2, f3, (float[])object);
            f3 = object3.getY();
            f2 = this.lastPos;
            this.lastPos = f;
            this.lastY = f3;
            return;
        }
        if (object3 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("");
            ((StringBuilder)object).append(n);
            object = ((StringBuilder)object).toString();
        } else {
            object = object3.getContext().getResources().getResourceName(n);
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("WARNING could not find view id ");
        ((StringBuilder)object3).append((String)object);
        Log.w((String)TAG, (String)((StringBuilder)object3).toString());
    }

    public ConstraintSet getConstraintSet(int n) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) return motionScene.getConstraintSet(n);
        return null;
    }

    public int[] getConstraintSetIds() {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) return motionScene.getConstraintSetIds();
        return null;
    }

    String getConstraintSetNames(int n) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) return motionScene.lookUpConstraintName(n);
        return null;
    }

    public int getCurrentState() {
        return this.mCurrentState;
    }

    public void getDebugMode(boolean bl) {
        int n = bl ? 2 : 1;
        this.mDebugPath = n;
        this.invalidate();
    }

    public ArrayList<MotionScene.Transition> getDefinedTransitions() {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) return motionScene.getDefinedTransitions();
        return null;
    }

    public DesignTool getDesignTool() {
        if (this.mDesignTool != null) return this.mDesignTool;
        this.mDesignTool = new DesignTool(this);
        return this.mDesignTool;
    }

    public int getEndState() {
        return this.mEndState;
    }

    protected long getNanoTime() {
        return System.nanoTime();
    }

    public float getProgress() {
        return this.mTransitionLastPosition;
    }

    public int getStartState() {
        return this.mBeginState;
    }

    public float getTargetPosition() {
        return this.mTransitionGoalPosition;
    }

    public MotionScene.Transition getTransition(int n) {
        return this.mScene.getTransitionById(n);
    }

    public Bundle getTransitionState() {
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.recordState();
        return this.mStateCache.getTransitionState();
    }

    public long getTransitionTimeMs() {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) return (long)(this.mTransitionDuration * 1000.0f);
        this.mTransitionDuration = (float)motionScene.getDuration() / 1000.0f;
        return (long)(this.mTransitionDuration * 1000.0f);
    }

    public float getVelocity() {
        return this.mLastVelocity;
    }

    public void getViewVelocity(View view, float f, float f2, float[] arrf, int n) {
        Object object;
        float f3 = this.mLastVelocity;
        float f4 = this.mTransitionLastPosition;
        if (this.mInterpolator != null) {
            f3 = Math.signum(this.mTransitionGoalPosition - f4);
            float f5 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition + 1.0E-5f);
            f4 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition);
            f3 = f3 * ((f5 - f4) / 1.0E-5f) / this.mTransitionDuration;
        }
        if ((object = this.mInterpolator) instanceof MotionInterpolator) {
            f3 = ((MotionInterpolator)object).getVelocity();
        }
        object = this.mFrameArrayList.get((Object)view);
        if ((n & 1) == 0) {
            ((MotionController)object).getPostLayoutDvDp(f4, view.getWidth(), view.getHeight(), f, f2, arrf);
        } else {
            ((MotionController)object).getDpDt(f4, f, f2, arrf);
        }
        if (n >= 2) return;
        arrf[0] = arrf[0] * f3;
        arrf[1] = arrf[1] * f3;
    }

    public boolean isAttachedToWindow() {
        if (Build.VERSION.SDK_INT >= 19) {
            return super.isAttachedToWindow();
        }
        if (this.getWindowToken() == null) return false;
        return true;
    }

    public boolean isInteractionEnabled() {
        return this.mInteractionEnabled;
    }

    @Override
    public void loadLayoutDescription(int n) {
        if (n == 0) {
            this.mScene = null;
            return;
        }
        try {
            MotionScene motionScene;
            this.mScene = motionScene = new MotionScene(this.getContext(), this, n);
            if (Build.VERSION.SDK_INT >= 19) {
                if (!this.isAttachedToWindow()) return;
            }
            this.mScene.readFallback(this);
            this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
            this.rebuildScene();
            this.mScene.setRtl(this.isRtl());
            return;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("unable to parse MotionScene file", exception);
        }
    }

    int lookUpConstraintId(String string2) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) return motionScene.lookUpConstraintId(string2);
        return 0;
    }

    protected MotionTracker obtainVelocityTracker() {
        return MyTracker.obtain();
    }

    protected void onAttachedToWindow() {
        int n;
        super.onAttachedToWindow();
        Object object = this.mScene;
        if (object != null && (n = this.mCurrentState) != -1) {
            object = ((MotionScene)object).getConstraintSet(n);
            this.mScene.readFallback(this);
            if (object != null) {
                ((ConstraintSet)object).applyTo(this);
            }
            this.mBeginState = this.mCurrentState;
        }
        this.onNewStateAttachHandlers();
        object = this.mStateCache;
        if (object == null) return;
        ((StateCache)object).apply();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        RectF rectF;
        Object object = this.mScene;
        if (object == null) return false;
        if (!this.mInteractionEnabled) {
            return false;
        }
        object = ((MotionScene)object).mCurrentTransition;
        if (object == null) return false;
        if (!((MotionScene.Transition)object).isEnabled()) return false;
        if ((object = ((MotionScene.Transition)object).getTouchResponse()) == null) return false;
        if (motionEvent.getAction() == 0 && (rectF = ((TouchResponse)object).getTouchRegion(this, new RectF())) != null && !rectF.contains(motionEvent.getX(), motionEvent.getY())) {
            return false;
        }
        int n = ((TouchResponse)object).getTouchRegionId();
        if (n == -1) return false;
        object = this.mRegionView;
        if (object == null || object.getId() != n) {
            this.mRegionView = this.findViewById(n);
        }
        if ((object = this.mRegionView) == null) return false;
        this.mBoundsCheck.set((float)object.getLeft(), (float)this.mRegionView.getTop(), (float)this.mRegionView.getRight(), (float)this.mRegionView.getBottom());
        if (!this.mBoundsCheck.contains(motionEvent.getX(), motionEvent.getY())) return false;
        if (this.handlesTouchEvent(0.0f, 0.0f, this.mRegionView, motionEvent)) return false;
        return this.onTouchEvent(motionEvent);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        block5 : {
            this.mInLayout = true;
            if (this.mScene != null) break block5;
            super.onLayout(bl, n, n2, n3, n4);
            this.mInLayout = false;
            return;
        }
        n = n3 - n;
        n2 = n4 - n2;
        try {
            if (this.mLastLayoutWidth != n || this.mLastLayoutHeight != n2) {
                this.rebuildScene();
                this.evaluate(true);
            }
            this.mLastLayoutWidth = n;
            this.mLastLayoutHeight = n2;
            this.mOldWidth = n;
            this.mOldHeight = n2;
            return;
        }
        finally {
            this.mInLayout = false;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.mScene == null) {
            super.onMeasure(n, n2);
            return;
        }
        int n3 = this.mLastWidthMeasureSpec;
        int n4 = 0;
        n3 = n3 == n && this.mLastHeightMeasureSpec == n2 ? 0 : 1;
        if (this.mNeedsFireTransitionCompleted) {
            this.mNeedsFireTransitionCompleted = false;
            this.onNewStateAttachHandlers();
            this.processTransitionCompleted();
            n3 = 1;
        }
        if (this.mDirtyHierarchy) {
            n3 = 1;
        }
        this.mLastWidthMeasureSpec = n;
        this.mLastHeightMeasureSpec = n2;
        int n5 = this.mScene.getStartId();
        int n6 = this.mScene.getEndId();
        if ((n3 != 0 || this.mModel.isNotConfiguredWith(n5, n6)) && this.mBeginState != -1) {
            super.onMeasure(n, n2);
            this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(n5), this.mScene.getConstraintSet(n6));
            this.mModel.reEvaluateState();
            this.mModel.setMeasuredId(n5, n6);
            n = n4;
        } else {
            n = 1;
        }
        if (this.mMeasureDuringTransition || n != 0) {
            n2 = this.getPaddingTop();
            n3 = this.getPaddingBottom();
            n = this.getPaddingLeft();
            n4 = this.getPaddingRight();
            n = this.mLayoutWidget.getWidth() + (n + n4);
            n2 = this.mLayoutWidget.getHeight() + (n2 + n3);
            n3 = this.mWidthMeasureMode;
            if (n3 == Integer.MIN_VALUE || n3 == 0) {
                n = this.mStartWrapWidth;
                n = (int)((float)n + this.mPostInterpolationPosition * (float)(this.mEndWrapWidth - n));
                this.requestLayout();
            }
            if ((n3 = this.mHeightMeasureMode) == Integer.MIN_VALUE || n3 == 0) {
                n2 = this.mStartWrapHeight;
                n2 = (int)((float)n2 + this.mPostInterpolationPosition * (float)(this.mEndWrapHeight - n2));
                this.requestLayout();
            }
            this.setMeasuredDimension(n, n2);
        }
        this.evaluateLayout();
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override
    public void onNestedPreScroll(final View view, int n, int n2, int[] arrn, int n3) {
        Object object;
        float f;
        float f2;
        Object object2 = this.mScene;
        if (object2 == null) return;
        if (((MotionScene)object2).mCurrentTransition == null) {
            return;
        }
        if (!this.mScene.mCurrentTransition.isEnabled()) {
            return;
        }
        object2 = this.mScene.mCurrentTransition;
        if (object2 != null && ((MotionScene.Transition)object2).isEnabled() && (object = ((MotionScene.Transition)object2).getTouchResponse()) != null && (n3 = ((TouchResponse)object).getTouchRegionId()) != -1 && view.getId() != n3) {
            return;
        }
        object = this.mScene;
        if (object != null && ((MotionScene)object).getMoveWhenScrollAtTop() && ((f = this.mTransitionPosition) == 1.0f || f == 0.0f) && view.canScrollVertically(-1)) {
            return;
        }
        if (((MotionScene.Transition)object2).getTouchResponse() != null && (this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 1) != 0) {
            f = this.mScene.getProgressDirection(n, n2);
            if (this.mTransitionLastPosition <= 0.0f && f < 0.0f || this.mTransitionLastPosition >= 1.0f && f > 0.0f) {
                if (Build.VERSION.SDK_INT < 21) return;
                view.setNestedScrollingEnabled(false);
                view.post(new Runnable(){

                    @Override
                    public void run() {
                        view.setNestedScrollingEnabled(true);
                    }
                });
                return;
            }
        }
        float f3 = this.mTransitionPosition;
        long l = this.getNanoTime();
        this.mScrollTargetDX = f2 = (float)n;
        this.mScrollTargetDY = f = (float)n2;
        this.mScrollTargetDT = (float)((double)(l - this.mScrollTargetTime) * 1.0E-9);
        this.mScrollTargetTime = l;
        this.mScene.processScrollMove(f2, f);
        if (f3 != this.mTransitionPosition) {
            arrn[0] = n;
            arrn[1] = n2;
        }
        this.evaluate(false);
        if (arrn[0] == 0) {
            if (arrn[1] == 0) return;
        }
        this.mUndergoingMotion = true;
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5) {
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5, int[] arrn) {
        if (this.mUndergoingMotion || n != 0 || n2 != 0) {
            arrn[0] = arrn[0] + n3;
            arrn[1] = arrn[1] + n4;
        }
        this.mUndergoingMotion = false;
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n, int n2) {
    }

    public void onRtlPropertiesChanged(int n) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) return;
        motionScene.setRtl(this.isRtl());
    }

    @Override
    public boolean onStartNestedScroll(View object, View view, int n, int n2) {
        object = this.mScene;
        if (object == null) return false;
        if (object.mCurrentTransition == null) return false;
        if (this.mScene.mCurrentTransition.getTouchResponse() == null) return false;
        if ((this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 2) == 0) return true;
        return false;
    }

    @Override
    public void onStopNestedScroll(View object, int n) {
        object = this.mScene;
        if (object == null) {
            return;
        }
        float f = this.mScrollTargetDX;
        float f2 = this.mScrollTargetDT;
        ((MotionScene)object).processScrollUp(f / f2, this.mScrollTargetDY / f2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Object object = this.mScene;
        if (object == null) return super.onTouchEvent(motionEvent);
        if (!this.mInteractionEnabled) return super.onTouchEvent(motionEvent);
        if (!((MotionScene)object).supportTouch()) return super.onTouchEvent(motionEvent);
        object = this.mScene.mCurrentTransition;
        if (object != null && !((MotionScene.Transition)object).isEnabled()) {
            return super.onTouchEvent(motionEvent);
        }
        this.mScene.processTouchEvent(motionEvent, this.getCurrentState(), this);
        return true;
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        if (!(view instanceof MotionHelper)) return;
        view = (MotionHelper)view;
        if (this.mTransitionListeners == null) {
            this.mTransitionListeners = new ArrayList();
        }
        this.mTransitionListeners.add((TransitionListener)view);
        if (view.isUsedOnShow()) {
            if (this.mOnShowHelpers == null) {
                this.mOnShowHelpers = new ArrayList();
            }
            this.mOnShowHelpers.add((MotionHelper)view);
        }
        if (!view.isUseOnHide()) return;
        if (this.mOnHideHelpers == null) {
            this.mOnHideHelpers = new ArrayList();
        }
        this.mOnHideHelpers.add((MotionHelper)view);
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
        if (arrayList != null) {
            arrayList.remove((Object)view);
        }
        if ((arrayList = this.mOnHideHelpers) == null) return;
        arrayList.remove((Object)view);
    }

    @Override
    protected void parseLayoutDescription(int n) {
        this.mConstraintLayoutSpec = null;
    }

    @Deprecated
    public void rebuildMotion() {
        Log.e((String)TAG, (String)"This method is deprecated. Please call rebuildScene() instead.");
        this.rebuildScene();
    }

    public void rebuildScene() {
        this.mModel.reEvaluateState();
        this.invalidate();
    }

    public boolean removeTransitionListener(TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.mTransitionListeners;
        if (arrayList != null) return arrayList.remove(transitionListener);
        return false;
    }

    @Override
    public void requestLayout() {
        MotionScene motionScene;
        if (!this.mMeasureDuringTransition && this.mCurrentState == -1 && (motionScene = this.mScene) != null && motionScene.mCurrentTransition != null && this.mScene.mCurrentTransition.getLayoutDuringTransition() == 0) {
            return;
        }
        super.requestLayout();
    }

    public void setDebugMode(int n) {
        this.mDebugPath = n;
        this.invalidate();
    }

    public void setInteractionEnabled(boolean bl) {
        this.mInteractionEnabled = bl;
    }

    public void setInterpolatedProgress(float f) {
        if (this.mScene != null) {
            this.setState(TransitionState.MOVING);
            Interpolator interpolator2 = this.mScene.getInterpolator();
            if (interpolator2 != null) {
                this.setProgress(interpolator2.getInterpolation(f));
                return;
            }
        }
        this.setProgress(f);
    }

    public void setOnHide(float f) {
        ArrayList<MotionHelper> arrayList = this.mOnHideHelpers;
        if (arrayList == null) return;
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            this.mOnHideHelpers.get(n2).setProgress(f);
            ++n2;
        }
    }

    public void setOnShow(float f) {
        ArrayList<MotionHelper> arrayList = this.mOnShowHelpers;
        if (arrayList == null) return;
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            this.mOnShowHelpers.get(n2).setProgress(f);
            ++n2;
        }
    }

    public void setProgress(float f) {
        if (!this.isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            this.mStateCache.setProgress(f);
            return;
        }
        if (f <= 0.0f) {
            this.mCurrentState = this.mBeginState;
            if (this.mTransitionLastPosition == 0.0f) {
                this.setState(TransitionState.FINISHED);
            }
        } else if (f >= 1.0f) {
            this.mCurrentState = this.mEndState;
            if (this.mTransitionLastPosition == 1.0f) {
                this.setState(TransitionState.FINISHED);
            }
        } else {
            this.mCurrentState = -1;
            this.setState(TransitionState.MOVING);
        }
        if (this.mScene == null) {
            return;
        }
        this.mTransitionInstantly = true;
        this.mTransitionGoalPosition = f;
        this.mTransitionPosition = f;
        this.mTransitionLastTime = -1L;
        this.mAnimationStartTime = -1L;
        this.mInterpolator = null;
        this.mInTransition = true;
        this.invalidate();
    }

    public void setProgress(float f, float f2) {
        if (this.isAttachedToWindow()) {
            this.setProgress(f);
            this.setState(TransitionState.MOVING);
            this.mLastVelocity = f2;
            this.animateTo(1.0f);
            return;
        }
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.setProgress(f);
        this.mStateCache.setVelocity(f2);
    }

    public void setScene(MotionScene motionScene) {
        this.mScene = motionScene;
        motionScene.setRtl(this.isRtl());
        this.rebuildScene();
    }

    @Override
    public void setState(int n, int n2, int n3) {
        this.setState(TransitionState.SETUP);
        this.mCurrentState = n;
        this.mBeginState = -1;
        this.mEndState = -1;
        if (this.mConstraintLayoutSpec != null) {
            this.mConstraintLayoutSpec.updateConstraints(n, n2, n3);
            return;
        }
        MotionScene motionScene = this.mScene;
        if (motionScene == null) return;
        motionScene.getConstraintSet(n).applyTo(this);
    }

    void setState(TransitionState transitionState) {
        int n;
        if (transitionState == TransitionState.FINISHED && this.mCurrentState == -1) {
            return;
        }
        TransitionState transitionState2 = this.mTransitionState;
        this.mTransitionState = transitionState;
        if (transitionState2 == TransitionState.MOVING && transitionState == TransitionState.MOVING) {
            this.fireTransitionChange();
        }
        if ((n = 2.$SwitchMap$androidx$constraintlayout$motion$widget$MotionLayout$TransitionState[transitionState2.ordinal()]) != 1 && n != 2) {
            if (n != 3) {
                return;
            }
            if (transitionState != TransitionState.FINISHED) return;
            this.fireTransitionCompleted();
            return;
        }
        if (transitionState == TransitionState.MOVING) {
            this.fireTransitionChange();
        }
        if (transitionState != TransitionState.FINISHED) return;
        this.fireTransitionCompleted();
    }

    public void setTransition(int n) {
        if (this.mScene == null) return;
        Object object = this.getTransition(n);
        this.mBeginState = ((MotionScene.Transition)object).getStartConstraintSetId();
        this.mEndState = ((MotionScene.Transition)object).getEndConstraintSetId();
        if (!this.isAttachedToWindow()) {
            if (this.mStateCache == null) {
                this.mStateCache = new StateCache();
            }
            this.mStateCache.setStartState(this.mBeginState);
            this.mStateCache.setEndState(this.mEndState);
            return;
        }
        float f = Float.NaN;
        int n2 = this.mCurrentState;
        n = this.mBeginState;
        float f2 = 0.0f;
        if (n2 == n) {
            f = 0.0f;
        } else if (n2 == this.mEndState) {
            f = 1.0f;
        }
        this.mScene.setTransition((MotionScene.Transition)object);
        this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
        this.rebuildScene();
        if (!Float.isNaN(f)) {
            f2 = f;
        }
        this.mTransitionLastPosition = f2;
        if (Float.isNaN(f)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(Debug.getLocation());
            ((StringBuilder)object).append(" transitionToStart ");
            Log.v((String)TAG, (String)((StringBuilder)object).toString());
            this.transitionToStart();
            return;
        }
        this.setProgress(f);
    }

    public void setTransition(int n, int n2) {
        if (this.isAttachedToWindow()) {
            MotionScene motionScene = this.mScene;
            if (motionScene == null) return;
            this.mBeginState = n;
            this.mEndState = n2;
            motionScene.setTransition(n, n2);
            this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(n), this.mScene.getConstraintSet(n2));
            this.rebuildScene();
            this.mTransitionLastPosition = 0.0f;
            this.transitionToStart();
            return;
        }
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.setStartState(n);
        this.mStateCache.setEndState(n2);
    }

    protected void setTransition(MotionScene.Transition transition) {
        this.mScene.setTransition(transition);
        this.setState(TransitionState.SETUP);
        if (this.mCurrentState == this.mScene.getEndId()) {
            this.mTransitionLastPosition = 1.0f;
            this.mTransitionPosition = 1.0f;
            this.mTransitionGoalPosition = 1.0f;
        } else {
            this.mTransitionLastPosition = 0.0f;
            this.mTransitionPosition = 0.0f;
            this.mTransitionGoalPosition = 0.0f;
        }
        long l = transition.isTransitionFlag(1) ? -1L : this.getNanoTime();
        this.mTransitionLastTime = l;
        int n = this.mScene.getStartId();
        int n2 = this.mScene.getEndId();
        if (n == this.mBeginState && n2 == this.mEndState) {
            return;
        }
        this.mBeginState = n;
        this.mEndState = n2;
        this.mScene.setTransition(n, n2);
        this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
        this.mModel.setMeasuredId(this.mBeginState, this.mEndState);
        this.mModel.reEvaluateState();
        this.rebuildScene();
    }

    public void setTransitionDuration(int n) {
        MotionScene motionScene = this.mScene;
        if (motionScene == null) {
            Log.e((String)TAG, (String)"MotionScene not defined");
            return;
        }
        motionScene.setDuration(n);
    }

    public void setTransitionListener(TransitionListener transitionListener) {
        this.mTransitionListener = transitionListener;
    }

    public void setTransitionState(Bundle bundle) {
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.setTransitionState(bundle);
        if (!this.isAttachedToWindow()) return;
        this.mStateCache.apply();
    }

    public String toString() {
        Context context = this.getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Debug.getName(context, this.mBeginState));
        stringBuilder.append("->");
        stringBuilder.append(Debug.getName(context, this.mEndState));
        stringBuilder.append(" (pos:");
        stringBuilder.append(this.mTransitionLastPosition);
        stringBuilder.append(" Dpos/Dt:");
        stringBuilder.append(this.mLastVelocity);
        return stringBuilder.toString();
    }

    public void touchAnimateTo(int n, float f, float f2) {
        if (this.mScene == null) {
            return;
        }
        if (this.mTransitionLastPosition == f) {
            return;
        }
        this.mTemporalInterpolator = true;
        this.mAnimationStartTime = this.getNanoTime();
        this.mTransitionDuration = (float)this.mScene.getDuration() / 1000.0f;
        this.mTransitionGoalPosition = f;
        this.mInTransition = true;
        if (n != 0 && n != 1 && n != 2) {
            if (n != 4) {
                if (n == 5) {
                    if (MotionLayout.willJump(f2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration())) {
                        this.mDecelerateLogic.config(f2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
                        this.mInterpolator = this.mDecelerateLogic;
                    } else {
                        this.mStopLogic.config(this.mTransitionLastPosition, f, f2, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
                        this.mLastVelocity = 0.0f;
                        n = this.mCurrentState;
                        this.mTransitionGoalPosition = f;
                        this.mCurrentState = n;
                        this.mInterpolator = this.mStopLogic;
                    }
                }
            } else {
                this.mDecelerateLogic.config(f2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
                this.mInterpolator = this.mDecelerateLogic;
            }
        } else {
            if (n == 1) {
                f = 0.0f;
            } else if (n == 2) {
                f = 1.0f;
            }
            this.mStopLogic.config(this.mTransitionLastPosition, f, f2, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
            n = this.mCurrentState;
            this.mTransitionGoalPosition = f;
            this.mCurrentState = n;
            this.mInterpolator = this.mStopLogic;
        }
        this.mTransitionInstantly = false;
        this.mAnimationStartTime = this.getNanoTime();
        this.invalidate();
    }

    public void transitionToEnd() {
        this.animateTo(1.0f);
    }

    public void transitionToStart() {
        this.animateTo(0.0f);
    }

    public void transitionToState(int n) {
        if (this.isAttachedToWindow()) {
            this.transitionToState(n, -1, -1);
            return;
        }
        if (this.mStateCache == null) {
            this.mStateCache = new StateCache();
        }
        this.mStateCache.setEndState(n);
    }

    public void transitionToState(int n, int n2, int n3) {
        Object object = this.mScene;
        int n4 = n;
        if (object != null) {
            n4 = n;
            if (((MotionScene)object).mStateSet != null) {
                n2 = this.mScene.mStateSet.convertToConstraintSet(this.mCurrentState, n, n2, n3);
                n4 = n;
                if (n2 != -1) {
                    n4 = n2;
                }
            }
        }
        if ((n = this.mCurrentState) == n4) {
            return;
        }
        if (this.mBeginState == n4) {
            this.animateTo(0.0f);
            return;
        }
        if (this.mEndState == n4) {
            this.animateTo(1.0f);
            return;
        }
        this.mEndState = n4;
        if (n != -1) {
            this.setTransition(n, n4);
            this.animateTo(1.0f);
            this.mTransitionLastPosition = 0.0f;
            this.transitionToEnd();
            return;
        }
        n3 = 0;
        this.mTemporalInterpolator = false;
        this.mTransitionGoalPosition = 1.0f;
        this.mTransitionPosition = 0.0f;
        this.mTransitionLastPosition = 0.0f;
        this.mTransitionLastTime = this.getNanoTime();
        this.mAnimationStartTime = this.getNanoTime();
        this.mTransitionInstantly = false;
        this.mInterpolator = null;
        this.mTransitionDuration = (float)this.mScene.getDuration() / 1000.0f;
        this.mBeginState = -1;
        this.mScene.setTransition(-1, this.mEndState);
        this.mScene.getStartId();
        int n5 = this.getChildCount();
        this.mFrameArrayList.clear();
        for (n = 0; n < n5; ++n) {
            object = this.getChildAt(n);
            MotionController motionController = new MotionController((View)object);
            this.mFrameArrayList.put((View)object, motionController);
        }
        this.mInTransition = true;
        this.mModel.initFrom(this.mLayoutWidget, null, this.mScene.getConstraintSet(n4));
        this.rebuildScene();
        this.mModel.build();
        this.computeCurrentPositions();
        n4 = this.getWidth();
        n2 = this.getHeight();
        for (n = 0; n < n5; ++n) {
            object = this.mFrameArrayList.get((Object)this.getChildAt(n));
            this.mScene.getKeyFrames((MotionController)object);
            ((MotionController)object).setup(n4, n2, this.mTransitionDuration, this.getNanoTime());
        }
        float f = this.mScene.getStaggered();
        if (f != 0.0f) {
            float f2;
            float f3 = Float.MAX_VALUE;
            float f4 = -3.4028235E38f;
            n2 = 0;
            do {
                if (n2 >= n5) break;
                object = this.mFrameArrayList.get((Object)this.getChildAt(n2));
                f2 = ((MotionController)object).getFinalX();
                f2 = ((MotionController)object).getFinalY() + f2;
                f3 = Math.min(f3, f2);
                f4 = Math.max(f4, f2);
                ++n2;
            } while (true);
            for (n = n3; n < n5; ++n) {
                object = this.mFrameArrayList.get((Object)this.getChildAt(n));
                float f5 = ((MotionController)object).getFinalX();
                f2 = ((MotionController)object).getFinalY();
                ((MotionController)object).mStaggerScale = 1.0f / (1.0f - f);
                ((MotionController)object).mStaggerOffset = f - (f5 + f2 - f3) * f / (f4 - f3);
            }
        }
        this.mTransitionPosition = 0.0f;
        this.mTransitionLastPosition = 0.0f;
        this.mInTransition = true;
        this.invalidate();
    }

    public void updateState() {
        this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
        this.rebuildScene();
    }

    public void updateState(int n, ConstraintSet constraintSet) {
        MotionScene motionScene = this.mScene;
        if (motionScene != null) {
            motionScene.setConstraintSet(n, constraintSet);
        }
        this.updateState();
        if (this.mCurrentState != n) return;
        constraintSet.applyTo(this);
    }

    class DecelerateInterpolator
    extends MotionInterpolator {
        float currentP = 0.0f;
        float initalV = 0.0f;
        float maxA;

        DecelerateInterpolator() {
        }

        public void config(float f, float f2, float f3) {
            this.initalV = f;
            this.currentP = f2;
            this.maxA = f3;
        }

        @Override
        public float getInterpolation(float f) {
            float f2;
            float f3 = this.initalV;
            if (f3 > 0.0f) {
                float f4 = this.maxA;
                f2 = f;
                if (f3 / f4 < f) {
                    f2 = f3 / f4;
                }
                MotionLayout.this.mLastVelocity = this.initalV - this.maxA * f2;
                f2 = this.initalV * f2 - this.maxA * f2 * f2 / 2.0f;
                f = this.currentP;
                return f2 + f;
            }
            float f5 = -f3;
            float f6 = this.maxA;
            f2 = f;
            if (f5 / f6 < f) {
                f2 = -f3 / f6;
            }
            MotionLayout.this.mLastVelocity = this.initalV + this.maxA * f2;
            f2 = this.initalV * f2 + this.maxA * f2 * f2 / 2.0f;
            f = this.currentP;
            return f2 + f;
        }

        @Override
        public float getVelocity() {
            return MotionLayout.this.mLastVelocity;
        }
    }

    private class DevModeDraw {
        private static final int DEBUG_PATH_TICKS_PER_MS = 16;
        final int DIAMOND_SIZE;
        final int GRAPH_COLOR;
        final int KEYFRAME_COLOR;
        final int RED_COLOR;
        final int SHADOW_COLOR;
        Rect mBounds = new Rect();
        DashPathEffect mDashPathEffect;
        Paint mFillPaint;
        int mKeyFrameCount;
        float[] mKeyFramePoints;
        Paint mPaint;
        Paint mPaintGraph;
        Paint mPaintKeyframes;
        Path mPath;
        int[] mPathMode;
        float[] mPoints;
        boolean mPresentationMode = false;
        private float[] mRectangle;
        int mShadowTranslate = 1;
        Paint mTextPaint;

        public DevModeDraw() {
            Paint paint;
            this.RED_COLOR = -21965;
            this.KEYFRAME_COLOR = -2067046;
            this.GRAPH_COLOR = -13391360;
            this.SHADOW_COLOR = 1996488704;
            this.DIAMOND_SIZE = 10;
            this.mPaint = paint = new Paint();
            paint.setAntiAlias(true);
            this.mPaint.setColor(-21965);
            this.mPaint.setStrokeWidth(2.0f);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaintKeyframes = paint = new Paint();
            paint.setAntiAlias(true);
            this.mPaintKeyframes.setColor(-2067046);
            this.mPaintKeyframes.setStrokeWidth(2.0f);
            this.mPaintKeyframes.setStyle(Paint.Style.STROKE);
            this.mPaintGraph = paint = new Paint();
            paint.setAntiAlias(true);
            this.mPaintGraph.setColor(-13391360);
            this.mPaintGraph.setStrokeWidth(2.0f);
            this.mPaintGraph.setStyle(Paint.Style.STROKE);
            this.mTextPaint = paint = new Paint();
            paint.setAntiAlias(true);
            this.mTextPaint.setColor(-13391360);
            this.mTextPaint.setTextSize(MotionLayout.this.getContext().getResources().getDisplayMetrics().density * 12.0f);
            this.mRectangle = new float[8];
            MotionLayout.this = new Paint();
            this.mFillPaint = MotionLayout.this;
            MotionLayout.this.setAntiAlias(true);
            MotionLayout.this = new DashPathEffect(new float[]{4.0f, 8.0f}, 0.0f);
            this.mDashPathEffect = MotionLayout.this;
            this.mPaintGraph.setPathEffect((PathEffect)MotionLayout.this);
            this.mKeyFramePoints = new float[100];
            this.mPathMode = new int[50];
            if (!this.mPresentationMode) return;
            this.mPaint.setStrokeWidth(8.0f);
            this.mFillPaint.setStrokeWidth(8.0f);
            this.mPaintKeyframes.setStrokeWidth(8.0f);
            this.mShadowTranslate = 4;
        }

        private void drawBasicPath(Canvas canvas) {
            canvas.drawLines(this.mPoints, this.mPaint);
        }

        private void drawPathAsConfigured(Canvas canvas) {
            boolean bl = false;
            boolean bl2 = false;
            for (int i = 0; i < this.mKeyFrameCount; ++i) {
                if (this.mPathMode[i] == 1) {
                    bl = true;
                }
                if (this.mPathMode[i] != 2) continue;
                bl2 = true;
            }
            if (bl) {
                this.drawPathRelative(canvas);
            }
            if (!bl2) return;
            this.drawPathCartesian(canvas);
        }

        private void drawPathCartesian(Canvas canvas) {
            float[] arrf = this.mPoints;
            float f = arrf[0];
            float f2 = arrf[1];
            float f3 = arrf[arrf.length - 2];
            float f4 = arrf[arrf.length - 1];
            canvas.drawLine(Math.min(f, f3), Math.max(f2, f4), Math.max(f, f3), Math.max(f2, f4), this.mPaintGraph);
            canvas.drawLine(Math.min(f, f3), Math.min(f2, f4), Math.min(f, f3), Math.max(f2, f4), this.mPaintGraph);
        }

        private void drawPathCartesianTicks(Canvas canvas, float f, float f2) {
            Object object = this.mPoints;
            float f3 = object[0];
            float f4 = object[1];
            float f5 = object[((float[])object).length - 2];
            float f6 = object[((float[])object).length - 1];
            float f7 = Math.min(f3, f5);
            float f8 = Math.max(f4, f6);
            float f9 = f - Math.min(f3, f5);
            float f10 = Math.max(f4, f6) - f2;
            object = new StringBuilder();
            ((StringBuilder)object).append("");
            ((StringBuilder)object).append((float)((int)((double)(f9 * 100.0f / Math.abs(f5 - f3)) + 0.5)) / 100.0f);
            object = ((StringBuilder)object).toString();
            this.getTextBounds((String)object, this.mTextPaint);
            canvas.drawText((String)object, f9 / 2.0f - (float)(this.mBounds.width() / 2) + f7, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(f3, f5), f2, this.mPaintGraph);
            object = new StringBuilder();
            ((StringBuilder)object).append("");
            ((StringBuilder)object).append((float)((int)((double)(f10 * 100.0f / Math.abs(f6 - f4)) + 0.5)) / 100.0f);
            object = ((StringBuilder)object).toString();
            this.getTextBounds((String)object, this.mTextPaint);
            canvas.drawText((String)object, f + 5.0f, f8 - (f10 / 2.0f - (float)(this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(f4, f6), this.mPaintGraph);
        }

        private void drawPathRelative(Canvas canvas) {
            float[] arrf = this.mPoints;
            canvas.drawLine(arrf[0], arrf[1], arrf[arrf.length - 2], arrf[arrf.length - 1], this.mPaintGraph);
        }

        private void drawPathRelativeTicks(Canvas canvas, float f, float f2) {
            Path path = this.mPoints;
            float f3 = path[0];
            float f4 = path[1];
            float f5 = path[((float[])path).length - 2];
            float f6 = path[((float[])path).length - 1];
            float f7 = (float)Math.hypot(f3 - f5, f4 - f6);
            float f8 = ((f - f3) * (f5 -= f3) + (f2 - f4) * (f6 -= f4)) / (f7 * f7);
            f3 += f5 * f8;
            f5 = f4 + f8 * f6;
            path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f5);
            f4 = (float)Math.hypot(f3 - f, f5 - f2);
            CharSequence charSequence = new StringBuilder();
            charSequence.append("");
            charSequence.append((float)((int)(f4 * 100.0f / f7)) / 100.0f);
            charSequence = charSequence.toString();
            this.getTextBounds((String)charSequence, this.mTextPaint);
            canvas.drawTextOnPath((String)charSequence, path, f4 / 2.0f - (float)(this.mBounds.width() / 2), -20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, f3, f5, this.mPaintGraph);
        }

        private void drawPathScreenTicks(Canvas canvas, float f, float f2, int n, int n2) {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append((float)((int)((double)((f - (float)(n / 2)) * 100.0f / (float)(MotionLayout.this.getWidth() - n)) + 0.5)) / 100.0f);
            charSequence = ((StringBuilder)charSequence).toString();
            this.getTextBounds((String)charSequence, this.mTextPaint);
            canvas.drawText((String)charSequence, f / 2.0f - (float)(this.mBounds.width() / 2) + 0.0f, f2 - 20.0f, this.mTextPaint);
            canvas.drawLine(f, f2, Math.min(0.0f, 1.0f), f2, this.mPaintGraph);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append((float)((int)((double)((f2 - (float)(n2 / 2)) * 100.0f / (float)(MotionLayout.this.getHeight() - n2)) + 0.5)) / 100.0f);
            charSequence = ((StringBuilder)charSequence).toString();
            this.getTextBounds((String)charSequence, this.mTextPaint);
            canvas.drawText((String)charSequence, f + 5.0f, 0.0f - (f2 / 2.0f - (float)(this.mBounds.height() / 2)), this.mTextPaint);
            canvas.drawLine(f, f2, f, Math.max(0.0f, 1.0f), this.mPaintGraph);
        }

        private void drawRectangle(Canvas canvas, MotionController motionController) {
            this.mPath.reset();
            int n = 0;
            do {
                if (n > 50) {
                    this.mPaint.setColor(1140850688);
                    canvas.translate(2.0f, 2.0f);
                    canvas.drawPath(this.mPath, this.mPaint);
                    canvas.translate(-2.0f, -2.0f);
                    this.mPaint.setColor(-65536);
                    canvas.drawPath(this.mPath, this.mPaint);
                    return;
                }
                motionController.buildRect((float)n / (float)50, this.mRectangle, 0);
                float[] arrf = this.mPath;
                Path path = this.mRectangle;
                arrf.moveTo(path[0], path[1]);
                arrf = this.mPath;
                path = this.mRectangle;
                arrf.lineTo(path[2], path[3]);
                path = this.mPath;
                arrf = this.mRectangle;
                path.lineTo(arrf[4], arrf[5]);
                path = this.mPath;
                arrf = this.mRectangle;
                path.lineTo(arrf[6], arrf[7]);
                this.mPath.close();
                ++n;
            } while (true);
        }

        private void drawTicks(Canvas canvas, int n, int n2, MotionController arrf) {
            int n3;
            int n4;
            if (arrf.mView != null) {
                n4 = arrf.mView.getWidth();
                n3 = arrf.mView.getHeight();
            } else {
                n4 = 0;
                n3 = 0;
            }
            int n5 = 1;
            do {
                if (n5 >= n2 - 1) {
                    arrf = this.mPoints;
                    if (arrf.length <= 1) return;
                    canvas.drawCircle(arrf[0], arrf[1], 8.0f, this.mPaintKeyframes);
                    arrf = this.mPoints;
                    canvas.drawCircle(arrf[arrf.length - 2], arrf[arrf.length - 1], 8.0f, this.mPaintKeyframes);
                    return;
                }
                if (n != 4 || this.mPathMode[n5 - 1] != 0) {
                    float[] arrf2 = this.mKeyFramePoints;
                    int n6 = n5 * 2;
                    float f = arrf2[n6];
                    float f2 = arrf2[n6 + 1];
                    this.mPath.reset();
                    this.mPath.moveTo(f, f2 + 10.0f);
                    this.mPath.lineTo(f + 10.0f, f2);
                    this.mPath.lineTo(f, f2 - 10.0f);
                    this.mPath.lineTo(f - 10.0f, f2);
                    this.mPath.close();
                    n6 = n5 - 1;
                    arrf.getKeyFrame(n6);
                    if (n == 4) {
                        arrf2 = this.mPathMode;
                        if (arrf2[n6] == true) {
                            this.drawPathRelativeTicks(canvas, f - 0.0f, f2 - 0.0f);
                        } else if (arrf2[n6] == 2) {
                            this.drawPathCartesianTicks(canvas, f - 0.0f, f2 - 0.0f);
                        } else if (arrf2[n6] == 3) {
                            this.drawPathScreenTicks(canvas, f - 0.0f, f2 - 0.0f, n4, n3);
                        }
                        canvas.drawPath(this.mPath, this.mFillPaint);
                    }
                    if (n == 2) {
                        this.drawPathRelativeTicks(canvas, f - 0.0f, f2 - 0.0f);
                    }
                    if (n == 3) {
                        this.drawPathCartesianTicks(canvas, f - 0.0f, f2 - 0.0f);
                    }
                    if (n == 6) {
                        this.drawPathScreenTicks(canvas, f - 0.0f, f2 - 0.0f, n4, n3);
                    }
                    canvas.drawPath(this.mPath, this.mFillPaint);
                }
                ++n5;
            } while (true);
        }

        private void drawTranslation(Canvas canvas, float f, float f2, float f3, float f4) {
            canvas.drawRect(f, f2, f3, f4, this.mPaintGraph);
            canvas.drawLine(f, f2, f3, f4, this.mPaintGraph);
        }

        public void draw(Canvas canvas, HashMap<View, MotionController> arrf, int n, int n2) {
            Object object;
            if (arrf == null) return;
            if (arrf.size() == 0) {
                return;
            }
            canvas.save();
            if (!MotionLayout.this.isInEditMode() && (n2 & 1) == 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(MotionLayout.this.getContext().getResources().getResourceName(MotionLayout.this.mEndState));
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(MotionLayout.this.getProgress());
                object = ((StringBuilder)object).toString();
                canvas.drawText((String)object, 10.0f, (float)(MotionLayout.this.getHeight() - 30), this.mTextPaint);
                canvas.drawText((String)object, 11.0f, (float)(MotionLayout.this.getHeight() - 29), this.mPaint);
            }
            object = arrf.values().iterator();
            do {
                int n3;
                if (!object.hasNext()) {
                    canvas.restore();
                    return;
                }
                MotionController motionController = object.next();
                int n4 = n3 = motionController.getDrawPath();
                if (n2 > 0) {
                    n4 = n3;
                    if (n3 == 0) {
                        n4 = 1;
                    }
                }
                if (n4 == 0) continue;
                this.mKeyFrameCount = motionController.buildKeyFrames(this.mKeyFramePoints, this.mPathMode);
                if (n4 < 1) continue;
                n3 = n / 16;
                arrf = this.mPoints;
                if (arrf == null || arrf.length != n3 * 2) {
                    this.mPoints = new float[n3 * 2];
                    this.mPath = new Path();
                }
                int n5 = this.mShadowTranslate;
                canvas.translate((float)n5, (float)n5);
                this.mPaint.setColor(1996488704);
                this.mFillPaint.setColor(1996488704);
                this.mPaintKeyframes.setColor(1996488704);
                this.mPaintGraph.setColor(1996488704);
                motionController.buildPath(this.mPoints, n3);
                this.drawAll(canvas, n4, this.mKeyFrameCount, motionController);
                this.mPaint.setColor(-21965);
                this.mPaintKeyframes.setColor(-2067046);
                this.mFillPaint.setColor(-2067046);
                this.mPaintGraph.setColor(-13391360);
                n3 = this.mShadowTranslate;
                canvas.translate((float)(-n3), (float)(-n3));
                this.drawAll(canvas, n4, this.mKeyFrameCount, motionController);
                if (n4 != 5) continue;
                this.drawRectangle(canvas, motionController);
            } while (true);
        }

        public void drawAll(Canvas canvas, int n, int n2, MotionController motionController) {
            if (n == 4) {
                this.drawPathAsConfigured(canvas);
            }
            if (n == 2) {
                this.drawPathRelative(canvas);
            }
            if (n == 3) {
                this.drawPathCartesian(canvas);
            }
            this.drawBasicPath(canvas);
            this.drawTicks(canvas, n, n2, motionController);
        }

        void getTextBounds(String string2, Paint paint) {
            paint.getTextBounds(string2, 0, string2.length(), this.mBounds);
        }
    }

    class Model {
        ConstraintSet mEnd = null;
        int mEndId;
        ConstraintWidgetContainer mLayoutEnd = new ConstraintWidgetContainer();
        ConstraintWidgetContainer mLayoutStart = new ConstraintWidgetContainer();
        ConstraintSet mStart = null;
        int mStartId;

        Model() {
        }

        private void debugLayout(String object, ConstraintWidgetContainer constraintWidgetContainer) {
            Object object2 = (View)constraintWidgetContainer.getCompanionWidget();
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(Debug.getName(object2));
            object2 = ((StringBuilder)charSequence).toString();
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append("  ========= ");
            ((StringBuilder)object).append(constraintWidgetContainer);
            Log.v((String)MotionLayout.TAG, (String)((StringBuilder)object).toString());
            int n = constraintWidgetContainer.getChildren().size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append(" done. ");
                    Log.v((String)MotionLayout.TAG, (String)((StringBuilder)object).toString());
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append("[");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append("] ");
                String string2 = ((StringBuilder)object).toString();
                ConstraintWidget constraintWidget = constraintWidgetContainer.getChildren().get(n2);
                CharSequence charSequence2 = new StringBuilder();
                charSequence2.append("");
                object = constraintWidget.mTop.mTarget;
                charSequence = "_";
                object = object != null ? "T" : "_";
                charSequence2.append((String)object);
                object = charSequence2.toString();
                charSequence2 = new StringBuilder();
                charSequence2.append((String)object);
                object = constraintWidget.mBottom.mTarget != null ? "B" : "_";
                charSequence2.append((String)object);
                object = charSequence2.toString();
                charSequence2 = new StringBuilder();
                charSequence2.append((String)object);
                object = constraintWidget.mLeft.mTarget != null ? "L" : "_";
                charSequence2.append((String)object);
                object = charSequence2.toString();
                charSequence2 = new StringBuilder();
                charSequence2.append((String)object);
                object = charSequence;
                if (constraintWidget.mRight.mTarget != null) {
                    object = "R";
                }
                charSequence2.append((String)object);
                charSequence2 = charSequence2.toString();
                View view = (View)constraintWidget.getCompanionWidget();
                charSequence = Debug.getName(view);
                object = charSequence;
                if (view instanceof TextView) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append("(");
                    ((StringBuilder)object).append((Object)((TextView)view).getText());
                    ((StringBuilder)object).append(")");
                    object = ((StringBuilder)object).toString();
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append("  ");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(" ");
                ((StringBuilder)charSequence).append(constraintWidget);
                ((StringBuilder)charSequence).append(" ");
                ((StringBuilder)charSequence).append((String)charSequence2);
                Log.v((String)MotionLayout.TAG, (String)((StringBuilder)charSequence).toString());
                ++n2;
            } while (true);
        }

        private void debugLayoutParam(String string2, ConstraintLayout.LayoutParams object) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(" ");
            CharSequence charSequence2 = object.startToStart != -1 ? "SS" : "__";
            charSequence.append((String)charSequence2);
            charSequence2 = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            int n = object.startToEnd;
            charSequence = "|__";
            charSequence2 = n != -1 ? "|SE" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.endToStart != -1 ? "|ES" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.endToEnd != -1 ? "|EE" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.leftToLeft != -1 ? "|LL" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.leftToRight != -1 ? "|LR" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.rightToLeft != -1 ? "|RL" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.rightToRight != -1 ? "|RR" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.topToTop != -1 ? "|TT" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.topToBottom != -1 ? "|TB" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = object.bottomToTop != -1 ? "|BT" : "|__";
            stringBuilder.append((String)charSequence2);
            charSequence2 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence2);
            charSequence2 = charSequence;
            if (object.bottomToBottom != -1) {
                charSequence2 = "|BB";
            }
            stringBuilder.append((String)charSequence2);
            object = stringBuilder.toString();
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(string2);
            ((StringBuilder)charSequence2).append((String)object);
            Log.v((String)MotionLayout.TAG, (String)((StringBuilder)charSequence2).toString());
        }

        private void debugWidget(String string2, ConstraintWidget constraintWidget) {
            StringBuilder stringBuilder;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" ");
            Object object = constraintWidget.mTop.mTarget;
            String string3 = "B";
            CharSequence charSequence = "__";
            if (object != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("T");
                object = constraintWidget.mTop.mTarget.mType == ConstraintAnchor.Type.TOP ? "T" : "B";
                stringBuilder.append((String)object);
                object = stringBuilder.toString();
            } else {
                object = "__";
            }
            stringBuilder2.append((String)object);
            object = stringBuilder2.toString();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)object);
            if (constraintWidget.mBottom.mTarget != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("B");
                object = string3;
                if (constraintWidget.mBottom.mTarget.mType == ConstraintAnchor.Type.TOP) {
                    object = "T";
                }
                stringBuilder.append((String)object);
                object = stringBuilder.toString();
            } else {
                object = "__";
            }
            stringBuilder2.append((String)object);
            object = stringBuilder2.toString();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)object);
            object = constraintWidget.mLeft.mTarget;
            string3 = "R";
            if (object != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("L");
                object = constraintWidget.mLeft.mTarget.mType == ConstraintAnchor.Type.LEFT ? "L" : "R";
                stringBuilder.append((String)object);
                object = stringBuilder.toString();
            } else {
                object = "__";
            }
            stringBuilder2.append((String)object);
            object = stringBuilder2.toString();
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)object);
            object = charSequence;
            if (constraintWidget.mRight.mTarget != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("R");
                object = string3;
                if (constraintWidget.mRight.mTarget.mType == ConstraintAnchor.Type.LEFT) {
                    object = "L";
                }
                ((StringBuilder)charSequence).append((String)object);
                object = ((StringBuilder)charSequence).toString();
            }
            stringBuilder2.append((String)object);
            object = stringBuilder2.toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append(" ---  ");
            ((StringBuilder)charSequence).append(constraintWidget);
            Log.v((String)MotionLayout.TAG, (String)((StringBuilder)charSequence).toString());
        }

        private void setupConstraintWidget(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet object) {
            Object object2;
            SparseArray sparseArray = new SparseArray();
            Object object3 = new Constraints.LayoutParams(-2, -2);
            sparseArray.clear();
            sparseArray.put(0, (Object)constraintWidgetContainer);
            sparseArray.put(MotionLayout.this.getId(), (Object)constraintWidgetContainer);
            for (ConstraintWidget object4 : constraintWidgetContainer.getChildren()) {
                sparseArray.put(((View)object4.getCompanionWidget()).getId(), (Object)object4);
            }
            for (ConstraintWidget constraintWidget : constraintWidgetContainer.getChildren()) {
                object2 = (View)constraintWidget.getCompanionWidget();
                ((ConstraintSet)((Object)object)).applyToLayoutParams(object2.getId(), (ConstraintLayout.LayoutParams)((Object)object3));
                constraintWidget.setWidth(((ConstraintSet)((Object)object)).getWidth(object2.getId()));
                constraintWidget.setHeight(((ConstraintSet)((Object)object)).getHeight(object2.getId()));
                if (object2 instanceof ConstraintHelper) {
                    ((ConstraintSet)((Object)object)).applyToHelper((ConstraintHelper)((Object)object2), constraintWidget, (ConstraintLayout.LayoutParams)((Object)object3), (SparseArray<ConstraintWidget>)sparseArray);
                    if (object2 instanceof Barrier) {
                        ((Barrier)((Object)object2)).validateParams();
                    }
                }
                if (Build.VERSION.SDK_INT >= 17) {
                    ((ConstraintLayout.LayoutParams)((Object)object3)).resolveLayoutDirection(MotionLayout.this.getLayoutDirection());
                } else {
                    ((ConstraintLayout.LayoutParams)((Object)object3)).resolveLayoutDirection(0);
                }
                MotionLayout.this.applyConstraintsFromLayoutParams(false, (View)object2, constraintWidget, (ConstraintLayout.LayoutParams)((Object)object3), (SparseArray<ConstraintWidget>)sparseArray);
                if (((ConstraintSet)((Object)object)).getVisibilityMode(object2.getId()) == 1) {
                    constraintWidget.setVisibility(object2.getVisibility());
                    continue;
                }
                constraintWidget.setVisibility(((ConstraintSet)((Object)object)).getVisibility(object2.getId()));
            }
            object = constraintWidgetContainer.getChildren().iterator();
            while (object.hasNext()) {
                object2 = object.next();
                if (!(object2 instanceof VirtualLayout)) continue;
                object3 = (ConstraintHelper)((Object)((ConstraintWidget)object2).getCompanionWidget());
                object2 = (Helper)object2;
                ((ConstraintHelper)((Object)object3)).updatePreLayout(constraintWidgetContainer, (Helper)object2, (SparseArray<ConstraintWidget>)sparseArray);
                ((VirtualLayout)object2).captureWidgets();
            }
        }

        public void build() {
            int n;
            Object object;
            View view;
            int n2 = MotionLayout.this.getChildCount();
            MotionLayout.this.mFrameArrayList.clear();
            int n3 = 0;
            int n4 = 0;
            do {
                n = n3;
                if (n4 >= n2) break;
                view = MotionLayout.this.getChildAt(n4);
                object = new MotionController(view);
                MotionLayout.this.mFrameArrayList.put(view, (MotionController)object);
                ++n4;
            } while (true);
            while (n < n2) {
                view = MotionLayout.this.getChildAt(n);
                object = MotionLayout.this.mFrameArrayList.get((Object)view);
                if (object != null) {
                    Object object2;
                    if (this.mStart != null) {
                        object2 = this.getWidget(this.mLayoutStart, view);
                        if (object2 != null) {
                            ((MotionController)object).setStartState((ConstraintWidget)object2, this.mStart);
                        } else if (MotionLayout.this.mDebugPath != 0) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append(Debug.getLocation());
                            ((StringBuilder)object2).append("no widget for  ");
                            ((StringBuilder)object2).append(Debug.getName(view));
                            ((StringBuilder)object2).append(" (");
                            ((StringBuilder)object2).append(view.getClass().getName());
                            ((StringBuilder)object2).append(")");
                            Log.e((String)MotionLayout.TAG, (String)((StringBuilder)object2).toString());
                        }
                    }
                    if (this.mEnd != null) {
                        object2 = this.getWidget(this.mLayoutEnd, view);
                        if (object2 != null) {
                            ((MotionController)object).setEndState((ConstraintWidget)object2, this.mEnd);
                        } else if (MotionLayout.this.mDebugPath != 0) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append(Debug.getLocation());
                            ((StringBuilder)object).append("no widget for  ");
                            ((StringBuilder)object).append(Debug.getName(view));
                            ((StringBuilder)object).append(" (");
                            ((StringBuilder)object).append(view.getClass().getName());
                            ((StringBuilder)object).append(")");
                            Log.e((String)MotionLayout.TAG, (String)((StringBuilder)object).toString());
                        }
                    }
                }
                ++n;
            }
        }

        void copy(ConstraintWidgetContainer constraintWidget, ConstraintWidgetContainer object) {
            ArrayList<ConstraintWidget> arrayList = constraintWidget.getChildren();
            HashMap<ConstraintWidget, ConstraintWidget> hashMap = new HashMap<ConstraintWidget, ConstraintWidget>();
            hashMap.put(constraintWidget, (ConstraintWidget)object);
            ((WidgetContainer)object).getChildren().clear();
            ((ConstraintWidget)object).copy(constraintWidget, hashMap);
            for (ConstraintWidget constraintWidget2 : arrayList) {
                constraintWidget = constraintWidget2 instanceof androidx.constraintlayout.solver.widgets.Barrier ? new androidx.constraintlayout.solver.widgets.Barrier() : (constraintWidget2 instanceof Guideline ? new Guideline() : (constraintWidget2 instanceof Flow ? new Flow() : (constraintWidget2 instanceof Helper ? new HelperWidget() : new ConstraintWidget())));
                ((WidgetContainer)object).add(constraintWidget);
                hashMap.put(constraintWidget2, constraintWidget);
            }
            object = arrayList.iterator();
            while (object.hasNext()) {
                constraintWidget = (ConstraintWidget)object.next();
                hashMap.get(constraintWidget).copy(constraintWidget, hashMap);
            }
        }

        ConstraintWidget getWidget(ConstraintWidgetContainer constraintWidget, View view) {
            if (constraintWidget.getCompanionWidget() == view) {
                return constraintWidget;
            }
            ArrayList<ConstraintWidget> arrayList = ((WidgetContainer)constraintWidget).getChildren();
            int n = arrayList.size();
            int n2 = 0;
            while (n2 < n) {
                constraintWidget = arrayList.get(n2);
                if (constraintWidget.getCompanionWidget() == view) {
                    return constraintWidget;
                }
                ++n2;
            }
            return null;
        }

        void initFrom(ConstraintWidgetContainer constraintWidgetContainer, ConstraintSet constraintSet, ConstraintSet constraintSet2) {
            this.mStart = constraintSet;
            this.mEnd = constraintSet2;
            this.mLayoutStart = new ConstraintWidgetContainer();
            this.mLayoutEnd = new ConstraintWidgetContainer();
            this.mLayoutStart.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
            this.mLayoutEnd.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
            this.mLayoutStart.removeAllChildren();
            this.mLayoutEnd.removeAllChildren();
            this.copy(MotionLayout.this.mLayoutWidget, this.mLayoutStart);
            this.copy(MotionLayout.this.mLayoutWidget, this.mLayoutEnd);
            if ((double)MotionLayout.this.mTransitionLastPosition > 0.5) {
                if (constraintSet != null) {
                    this.setupConstraintWidget(this.mLayoutStart, constraintSet);
                }
                this.setupConstraintWidget(this.mLayoutEnd, constraintSet2);
            } else {
                this.setupConstraintWidget(this.mLayoutEnd, constraintSet2);
                if (constraintSet != null) {
                    this.setupConstraintWidget(this.mLayoutStart, constraintSet);
                }
            }
            this.mLayoutStart.setRtl(MotionLayout.this.isRtl());
            this.mLayoutStart.updateHierarchy();
            this.mLayoutEnd.setRtl(MotionLayout.this.isRtl());
            this.mLayoutEnd.updateHierarchy();
            constraintWidgetContainer = MotionLayout.this.getLayoutParams();
            if (constraintWidgetContainer == null) return;
            if (((ViewGroup.LayoutParams)constraintWidgetContainer).width == -2) {
                this.mLayoutStart.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                this.mLayoutEnd.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
            if (((ViewGroup.LayoutParams)constraintWidgetContainer).height != -2) return;
            this.mLayoutStart.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            this.mLayoutEnd.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        }

        public boolean isNotConfiguredWith(int n, int n2) {
            if (n != this.mStartId) return true;
            if (n2 != this.mEndId) return true;
            return false;
        }

        public void measure(int n, int n2) {
            boolean bl;
            int n3;
            int n4 = View.MeasureSpec.getMode((int)n);
            int n5 = View.MeasureSpec.getMode((int)n2);
            MotionLayout.this.mWidthMeasureMode = n4;
            MotionLayout.this.mHeightMeasureMode = n5;
            int n6 = MotionLayout.this.getOptimizationLevel();
            if (MotionLayout.this.mCurrentState == MotionLayout.this.getStartState()) {
                MotionLayout.this.resolveSystem(this.mLayoutEnd, n6, n, n2);
                if (this.mStart != null) {
                    MotionLayout.this.resolveSystem(this.mLayoutStart, n6, n, n2);
                }
            } else {
                if (this.mStart != null) {
                    MotionLayout.this.resolveSystem(this.mLayoutStart, n6, n, n2);
                }
                MotionLayout.this.resolveSystem(this.mLayoutEnd, n6, n, n2);
            }
            if ((n3 = MotionLayout.this.getParent() instanceof MotionLayout && n4 == 1073741824 && n5 == 1073741824 ? 0 : 1) != 0) {
                MotionLayout.this.mWidthMeasureMode = n4;
                MotionLayout.this.mHeightMeasureMode = n5;
                if (MotionLayout.this.mCurrentState == MotionLayout.this.getStartState()) {
                    MotionLayout.this.resolveSystem(this.mLayoutEnd, n6, n, n2);
                    if (this.mStart != null) {
                        MotionLayout.this.resolveSystem(this.mLayoutStart, n6, n, n2);
                    }
                } else {
                    if (this.mStart != null) {
                        MotionLayout.this.resolveSystem(this.mLayoutStart, n6, n, n2);
                    }
                    MotionLayout.this.resolveSystem(this.mLayoutEnd, n6, n, n2);
                }
                MotionLayout.this.mStartWrapWidth = this.mLayoutStart.getWidth();
                MotionLayout.this.mStartWrapHeight = this.mLayoutStart.getHeight();
                MotionLayout.this.mEndWrapWidth = this.mLayoutEnd.getWidth();
                MotionLayout.this.mEndWrapHeight = this.mLayoutEnd.getHeight();
                MotionLayout motionLayout = MotionLayout.this;
                bl = motionLayout.mStartWrapWidth != MotionLayout.this.mEndWrapWidth || MotionLayout.this.mStartWrapHeight != MotionLayout.this.mEndWrapHeight;
                motionLayout.mMeasureDuringTransition = bl;
            }
            n3 = MotionLayout.this.mStartWrapWidth;
            n5 = MotionLayout.this.mStartWrapHeight;
            if (MotionLayout.this.mWidthMeasureMode == Integer.MIN_VALUE || MotionLayout.this.mWidthMeasureMode == 0) {
                n3 = (int)((float)MotionLayout.this.mStartWrapWidth + MotionLayout.this.mPostInterpolationPosition * (float)(MotionLayout.this.mEndWrapWidth - MotionLayout.this.mStartWrapWidth));
            }
            if (MotionLayout.this.mHeightMeasureMode == Integer.MIN_VALUE || MotionLayout.this.mHeightMeasureMode == 0) {
                n5 = (int)((float)MotionLayout.this.mStartWrapHeight + MotionLayout.this.mPostInterpolationPosition * (float)(MotionLayout.this.mEndWrapHeight - MotionLayout.this.mStartWrapHeight));
            }
            bl = this.mLayoutStart.isWidthMeasuredTooSmall() || this.mLayoutEnd.isWidthMeasuredTooSmall();
            boolean bl2 = this.mLayoutStart.isHeightMeasuredTooSmall() || this.mLayoutEnd.isHeightMeasuredTooSmall();
            MotionLayout.this.resolveMeasuredDimension(n, n2, n3, n5, bl, bl2);
        }

        public void reEvaluateState() {
            this.measure(MotionLayout.this.mLastWidthMeasureSpec, MotionLayout.this.mLastHeightMeasureSpec);
            MotionLayout.this.setupMotionViews();
        }

        public void setMeasuredId(int n, int n2) {
            this.mStartId = n;
            this.mEndId = n2;
        }
    }

    protected static interface MotionTracker {
        public void addMovement(MotionEvent var1);

        public void clear();

        public void computeCurrentVelocity(int var1);

        public void computeCurrentVelocity(int var1, float var2);

        public float getXVelocity();

        public float getXVelocity(int var1);

        public float getYVelocity();

        public float getYVelocity(int var1);

        public void recycle();
    }

    private static class MyTracker
    implements MotionTracker {
        private static MyTracker me = new MyTracker();
        VelocityTracker tracker;

        private MyTracker() {
        }

        public static MyTracker obtain() {
            MyTracker.me.tracker = VelocityTracker.obtain();
            return me;
        }

        @Override
        public void addMovement(MotionEvent motionEvent) {
            VelocityTracker velocityTracker = this.tracker;
            if (velocityTracker == null) return;
            velocityTracker.addMovement(motionEvent);
        }

        @Override
        public void clear() {
            this.tracker.clear();
        }

        @Override
        public void computeCurrentVelocity(int n) {
            this.tracker.computeCurrentVelocity(n);
        }

        @Override
        public void computeCurrentVelocity(int n, float f) {
            this.tracker.computeCurrentVelocity(n, f);
        }

        @Override
        public float getXVelocity() {
            return this.tracker.getXVelocity();
        }

        @Override
        public float getXVelocity(int n) {
            return this.tracker.getXVelocity(n);
        }

        @Override
        public float getYVelocity() {
            return this.tracker.getYVelocity();
        }

        @Override
        public float getYVelocity(int n) {
            return this.getYVelocity(n);
        }

        @Override
        public void recycle() {
            this.tracker.recycle();
            this.tracker = null;
        }
    }

    class StateCache {
        final String KeyEndState;
        final String KeyProgress;
        final String KeyStartState;
        final String KeyVelocity;
        int endState = -1;
        float mProgress = Float.NaN;
        float mVelocity = Float.NaN;
        int startState = -1;

        StateCache() {
            this.KeyProgress = "motion.progress";
            this.KeyVelocity = "motion.velocity";
            this.KeyStartState = "motion.StartState";
            this.KeyEndState = "motion.EndState";
        }

        void apply() {
            if (this.startState != -1 || this.endState != -1) {
                int n = this.startState;
                if (n == -1) {
                    MotionLayout.this.transitionToState(this.endState);
                } else {
                    int n2 = this.endState;
                    if (n2 == -1) {
                        MotionLayout.this.setState(n, -1, -1);
                    } else {
                        MotionLayout.this.setTransition(n, n2);
                    }
                }
                MotionLayout.this.setState(TransitionState.SETUP);
            }
            if (!Float.isNaN(this.mVelocity)) {
                MotionLayout.this.setProgress(this.mProgress, this.mVelocity);
                this.mProgress = Float.NaN;
                this.mVelocity = Float.NaN;
                this.startState = -1;
                this.endState = -1;
                return;
            }
            if (Float.isNaN(this.mProgress)) {
                return;
            }
            MotionLayout.this.setProgress(this.mProgress);
        }

        public Bundle getTransitionState() {
            Bundle bundle = new Bundle();
            bundle.putFloat("motion.progress", this.mProgress);
            bundle.putFloat("motion.velocity", this.mVelocity);
            bundle.putInt("motion.StartState", this.startState);
            bundle.putInt("motion.EndState", this.endState);
            return bundle;
        }

        public void recordState() {
            this.endState = MotionLayout.this.mEndState;
            this.startState = MotionLayout.this.mBeginState;
            this.mVelocity = MotionLayout.this.getVelocity();
            this.mProgress = MotionLayout.this.getProgress();
        }

        public void setEndState(int n) {
            this.endState = n;
        }

        public void setProgress(float f) {
            this.mProgress = f;
        }

        public void setStartState(int n) {
            this.startState = n;
        }

        public void setTransitionState(Bundle bundle) {
            this.mProgress = bundle.getFloat("motion.progress");
            this.mVelocity = bundle.getFloat("motion.velocity");
            this.startState = bundle.getInt("motion.StartState");
            this.endState = bundle.getInt("motion.EndState");
        }

        public void setVelocity(float f) {
            this.mVelocity = f;
        }
    }

    public static interface TransitionListener {
        public void onTransitionChange(MotionLayout var1, int var2, int var3, float var4);

        public void onTransitionCompleted(MotionLayout var1, int var2);

        public void onTransitionStarted(MotionLayout var1, int var2, int var3);

        public void onTransitionTrigger(MotionLayout var1, int var2, boolean var3, float var4);
    }

    static final class TransitionState
    extends Enum<TransitionState> {
        private static final /* synthetic */ TransitionState[] $VALUES;
        public static final /* enum */ TransitionState FINISHED;
        public static final /* enum */ TransitionState MOVING;
        public static final /* enum */ TransitionState SETUP;
        public static final /* enum */ TransitionState UNDEFINED;

        static {
            TransitionState transitionState;
            UNDEFINED = new TransitionState();
            SETUP = new TransitionState();
            MOVING = new TransitionState();
            FINISHED = transitionState = new TransitionState();
            $VALUES = new TransitionState[]{UNDEFINED, SETUP, MOVING, transitionState};
        }

        public static TransitionState valueOf(String string2) {
            return Enum.valueOf(TransitionState.class, string2);
        }

        public static TransitionState[] values() {
            return (TransitionState[])$VALUES.clone();
        }
    }

}

