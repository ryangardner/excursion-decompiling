package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.widget.TextView;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Flow;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.R;
import androidx.core.view.NestedScrollingParent3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MotionLayout extends ConstraintLayout implements NestedScrollingParent3 {
   private static final boolean DEBUG = false;
   public static final int DEBUG_SHOW_NONE = 0;
   public static final int DEBUG_SHOW_PATH = 2;
   public static final int DEBUG_SHOW_PROGRESS = 1;
   private static final float EPSILON = 1.0E-5F;
   public static boolean IS_IN_EDIT_MODE;
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
   private RectF mBoundsCheck;
   int mCurrentState = -1;
   int mDebugPath = 0;
   private MotionLayout.DecelerateInterpolator mDecelerateLogic = new MotionLayout.DecelerateInterpolator();
   private DesignTool mDesignTool;
   MotionLayout.DevModeDraw mDevModeDraw;
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
   private float mLastFps = 0.0F;
   private int mLastHeightMeasureSpec = 0;
   int mLastLayoutHeight;
   int mLastLayoutWidth;
   float mLastVelocity = 0.0F;
   private int mLastWidthMeasureSpec = 0;
   private float mListenerPosition = 0.0F;
   private int mListenerState = 0;
   protected boolean mMeasureDuringTransition = false;
   MotionLayout.Model mModel;
   private boolean mNeedsFireTransitionCompleted;
   int mOldHeight;
   int mOldWidth;
   private ArrayList<MotionHelper> mOnHideHelpers = null;
   private ArrayList<MotionHelper> mOnShowHelpers = null;
   float mPostInterpolationPosition;
   private View mRegionView;
   MotionScene mScene;
   float mScrollTargetDT;
   float mScrollTargetDX;
   float mScrollTargetDY;
   long mScrollTargetTime;
   int mStartWrapHeight;
   int mStartWrapWidth;
   private MotionLayout.StateCache mStateCache;
   private StopLogic mStopLogic = new StopLogic();
   private boolean mTemporalInterpolator = false;
   ArrayList<Integer> mTransitionCompleted;
   private float mTransitionDuration = 1.0F;
   float mTransitionGoalPosition = 0.0F;
   private boolean mTransitionInstantly;
   float mTransitionLastPosition = 0.0F;
   private long mTransitionLastTime;
   private MotionLayout.TransitionListener mTransitionListener;
   private ArrayList<MotionLayout.TransitionListener> mTransitionListeners = null;
   float mTransitionPosition = 0.0F;
   MotionLayout.TransitionState mTransitionState;
   boolean mUndergoingMotion = false;
   int mWidthMeasureMode;

   public MotionLayout(Context var1) {
      super(var1);
      this.mTransitionState = MotionLayout.TransitionState.UNDEFINED;
      this.mModel = new MotionLayout.Model();
      this.mNeedsFireTransitionCompleted = false;
      this.mBoundsCheck = new RectF();
      this.mRegionView = null;
      this.mTransitionCompleted = new ArrayList();
      this.init((AttributeSet)null);
   }

   public MotionLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mTransitionState = MotionLayout.TransitionState.UNDEFINED;
      this.mModel = new MotionLayout.Model();
      this.mNeedsFireTransitionCompleted = false;
      this.mBoundsCheck = new RectF();
      this.mRegionView = null;
      this.mTransitionCompleted = new ArrayList();
      this.init(var2);
   }

   public MotionLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mTransitionState = MotionLayout.TransitionState.UNDEFINED;
      this.mModel = new MotionLayout.Model();
      this.mNeedsFireTransitionCompleted = false;
      this.mBoundsCheck = new RectF();
      this.mRegionView = null;
      this.mTransitionCompleted = new ArrayList();
      this.init(var2);
   }

   private void checkStructure() {
      MotionScene var1 = this.mScene;
      if (var1 == null) {
         Log.e("MotionLayout", "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
      } else {
         int var2 = var1.getStartId();
         var1 = this.mScene;
         this.checkStructure(var2, var1.getConstraintSet(var1.getStartId()));
         SparseIntArray var9 = new SparseIntArray();
         SparseIntArray var3 = new SparseIntArray();
         Iterator var4 = this.mScene.getDefinedTransitions().iterator();

         while(var4.hasNext()) {
            MotionScene.Transition var5 = (MotionScene.Transition)var4.next();
            if (var5 == this.mScene.mCurrentTransition) {
               Log.v("MotionLayout", "CHECK: CURRENT");
            }

            this.checkStructure(var5);
            var2 = var5.getStartConstraintSetId();
            int var6 = var5.getEndConstraintSetId();
            String var10 = Debug.getName(this.getContext(), var2);
            String var7 = Debug.getName(this.getContext(), var6);
            StringBuilder var8;
            if (var9.get(var2) == var6) {
               var8 = new StringBuilder();
               var8.append("CHECK: two transitions with the same start and end ");
               var8.append(var10);
               var8.append("->");
               var8.append(var7);
               Log.e("MotionLayout", var8.toString());
            }

            if (var3.get(var6) == var2) {
               var8 = new StringBuilder();
               var8.append("CHECK: you can't have reverse transitions");
               var8.append(var10);
               var8.append("->");
               var8.append(var7);
               Log.e("MotionLayout", var8.toString());
            }

            var9.put(var2, var6);
            var3.put(var6, var2);
            StringBuilder var11;
            if (this.mScene.getConstraintSet(var2) == null) {
               var11 = new StringBuilder();
               var11.append(" no such constraintSetStart ");
               var11.append(var10);
               Log.e("MotionLayout", var11.toString());
            }

            if (this.mScene.getConstraintSet(var6) == null) {
               var11 = new StringBuilder();
               var11.append(" no such constraintSetEnd ");
               var11.append(var10);
               Log.e("MotionLayout", var11.toString());
            }
         }

      }
   }

   private void checkStructure(int var1, ConstraintSet var2) {
      String var3 = Debug.getName(this.getContext(), var1);
      int var4 = this.getChildCount();
      byte var5 = 0;

      for(var1 = 0; var1 < var4; ++var1) {
         View var6 = this.getChildAt(var1);
         int var7 = var6.getId();
         StringBuilder var8;
         if (var7 == -1) {
            var8 = new StringBuilder();
            var8.append("CHECK: ");
            var8.append(var3);
            var8.append(" ALL VIEWS SHOULD HAVE ID's ");
            var8.append(var6.getClass().getName());
            var8.append(" does not!");
            Log.w("MotionLayout", var8.toString());
         }

         if (var2.getConstraint(var7) == null) {
            var8 = new StringBuilder();
            var8.append("CHECK: ");
            var8.append(var3);
            var8.append(" NO CONSTRAINTS for ");
            var8.append(Debug.getName(var6));
            Log.w("MotionLayout", var8.toString());
         }
      }

      int[] var11 = var2.getKnownIds();

      for(var1 = var5; var1 < var11.length; ++var1) {
         int var10 = var11[var1];
         String var12 = Debug.getName(this.getContext(), var10);
         StringBuilder var9;
         if (this.findViewById(var11[var1]) == null) {
            var9 = new StringBuilder();
            var9.append("CHECK: ");
            var9.append(var3);
            var9.append(" NO View matches id ");
            var9.append(var12);
            Log.w("MotionLayout", var9.toString());
         }

         if (var2.getHeight(var10) == -1) {
            var9 = new StringBuilder();
            var9.append("CHECK: ");
            var9.append(var3);
            var9.append("(");
            var9.append(var12);
            var9.append(") no LAYOUT_HEIGHT");
            Log.w("MotionLayout", var9.toString());
         }

         if (var2.getWidth(var10) == -1) {
            var9 = new StringBuilder();
            var9.append("CHECK: ");
            var9.append(var3);
            var9.append("(");
            var9.append(var12);
            var9.append(") no LAYOUT_HEIGHT");
            Log.w("MotionLayout", var9.toString());
         }
      }

   }

   private void checkStructure(MotionScene.Transition var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("CHECK: transition = ");
      var2.append(var1.debugString(this.getContext()));
      Log.v("MotionLayout", var2.toString());
      var2 = new StringBuilder();
      var2.append("CHECK: transition.setDuration = ");
      var2.append(var1.getDuration());
      Log.v("MotionLayout", var2.toString());
      if (var1.getStartConstraintSetId() == var1.getEndConstraintSetId()) {
         Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!");
      }

   }

   private void computeCurrentPositions() {
      int var1 = this.getChildCount();

      for(int var2 = 0; var2 < var1; ++var2) {
         View var3 = this.getChildAt(var2);
         MotionController var4 = (MotionController)this.mFrameArrayList.get(var3);
         if (var4 != null) {
            var4.setStartCurrentState(var3);
         }
      }

   }

   private void debugPos() {
      for(int var1 = 0; var1 < this.getChildCount(); ++var1) {
         View var2 = this.getChildAt(var1);
         StringBuilder var3 = new StringBuilder();
         var3.append(" ");
         var3.append(Debug.getLocation());
         var3.append(" ");
         var3.append(Debug.getName(this));
         var3.append(" ");
         var3.append(Debug.getName(this.getContext(), this.mCurrentState));
         var3.append(" ");
         var3.append(Debug.getName(var2));
         var3.append(var2.getLeft());
         var3.append(" ");
         var3.append(var2.getTop());
         Log.v("MotionLayout", var3.toString());
      }

   }

   private void evaluateLayout() {
      float var1 = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
      long var2 = this.getNanoTime();
      float var4;
      if (!(this.mInterpolator instanceof StopLogic)) {
         var4 = (float)(var2 - this.mTransitionLastTime) * var1 * 1.0E-9F / this.mTransitionDuration;
      } else {
         var4 = 0.0F;
      }

      float var5 = this.mTransitionLastPosition + var4;
      if (this.mTransitionInstantly) {
         var5 = this.mTransitionGoalPosition;
      }

      byte var6 = 0;
      float var13;
      int var7 = (var13 = var1 - 0.0F) == 0.0F ? 0 : (var13 < 0.0F ? -1 : 1);
      boolean var8;
      if ((var7 <= 0 || var5 < this.mTransitionGoalPosition) && (var1 > 0.0F || var5 > this.mTransitionGoalPosition)) {
         var8 = false;
      } else {
         var5 = this.mTransitionGoalPosition;
         var8 = true;
      }

      Interpolator var9 = this.mInterpolator;
      var4 = var5;
      if (var9 != null) {
         var4 = var5;
         if (!var8) {
            if (this.mTemporalInterpolator) {
               var4 = var9.getInterpolation((float)(var2 - this.mAnimationStartTime) * 1.0E-9F);
            } else {
               var4 = var9.getInterpolation(var5);
            }
         }
      }

      label76: {
         if (var7 <= 0 || var4 < this.mTransitionGoalPosition) {
            var5 = var4;
            if (var1 > 0.0F) {
               break label76;
            }

            var5 = var4;
            if (var4 > this.mTransitionGoalPosition) {
               break label76;
            }
         }

         var5 = this.mTransitionGoalPosition;
      }

      this.mPostInterpolationPosition = var5;
      var7 = this.getChildCount();
      var2 = this.getNanoTime();

      for(int var11 = var6; var11 < var7; ++var11) {
         View var10 = this.getChildAt(var11);
         MotionController var12 = (MotionController)this.mFrameArrayList.get(var10);
         if (var12 != null) {
            var12.interpolate(var10, var5, var2, this.mKeyCache);
         }
      }

      if (this.mMeasureDuringTransition) {
         this.requestLayout();
      }

   }

   private void fireTransitionChange() {
      ArrayList var1;
      if (this.mTransitionListener == null) {
         var1 = this.mTransitionListeners;
         if (var1 == null || var1.isEmpty()) {
            return;
         }
      }

      if (this.mListenerPosition != this.mTransitionPosition) {
         MotionLayout.TransitionListener var3;
         Iterator var4;
         if (this.mListenerState != -1) {
            var3 = this.mTransitionListener;
            if (var3 != null) {
               var3.onTransitionStarted(this, this.mBeginState, this.mEndState);
            }

            var1 = this.mTransitionListeners;
            if (var1 != null) {
               var4 = var1.iterator();

               while(var4.hasNext()) {
                  ((MotionLayout.TransitionListener)var4.next()).onTransitionStarted(this, this.mBeginState, this.mEndState);
               }
            }

            this.mIsAnimating = true;
         }

         this.mListenerState = -1;
         float var2 = this.mTransitionPosition;
         this.mListenerPosition = var2;
         var3 = this.mTransitionListener;
         if (var3 != null) {
            var3.onTransitionChange(this, this.mBeginState, this.mEndState, var2);
         }

         var1 = this.mTransitionListeners;
         if (var1 != null) {
            var4 = var1.iterator();

            while(var4.hasNext()) {
               ((MotionLayout.TransitionListener)var4.next()).onTransitionChange(this, this.mBeginState, this.mEndState, this.mTransitionPosition);
            }
         }

         this.mIsAnimating = true;
      }

   }

   private void fireTransitionStarted(MotionLayout var1, int var2, int var3) {
      MotionLayout.TransitionListener var4 = this.mTransitionListener;
      if (var4 != null) {
         var4.onTransitionStarted(this, var2, var3);
      }

      ArrayList var5 = this.mTransitionListeners;
      if (var5 != null) {
         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            ((MotionLayout.TransitionListener)var6.next()).onTransitionStarted(var1, var2, var3);
         }
      }

   }

   private boolean handlesTouchEvent(float var1, float var2, View var3, MotionEvent var4) {
      if (var3 instanceof ViewGroup) {
         ViewGroup var5 = (ViewGroup)var3;
         int var6 = var5.getChildCount();

         for(int var7 = 0; var7 < var6; ++var7) {
            View var8 = var5.getChildAt(var7);
            if (this.handlesTouchEvent((float)var3.getLeft() + var1, (float)var3.getTop() + var2, var8, var4)) {
               return true;
            }
         }
      }

      this.mBoundsCheck.set((float)var3.getLeft() + var1, (float)var3.getTop() + var2, var1 + (float)var3.getRight(), var2 + (float)var3.getBottom());
      if (var4.getAction() == 0) {
         if (this.mBoundsCheck.contains(var4.getX(), var4.getY()) && var3.onTouchEvent(var4)) {
            return true;
         }
      } else if (var3.onTouchEvent(var4)) {
         return true;
      }

      return false;
   }

   private void init(AttributeSet var1) {
      IS_IN_EDIT_MODE = this.isInEditMode();
      if (var1 != null) {
         TypedArray var7 = this.getContext().obtainStyledAttributes(var1, R.styleable.MotionLayout);
         int var2 = var7.getIndexCount();
         int var3 = 0;

         boolean var4;
         boolean var6;
         for(var4 = true; var3 < var2; var4 = var6) {
            int var5 = var7.getIndex(var3);
            if (var5 == R.styleable.MotionLayout_layoutDescription) {
               var5 = var7.getResourceId(var5, -1);
               this.mScene = new MotionScene(this.getContext(), this, var5);
               var6 = var4;
            } else if (var5 == R.styleable.MotionLayout_currentState) {
               this.mCurrentState = var7.getResourceId(var5, -1);
               var6 = var4;
            } else if (var5 == R.styleable.MotionLayout_motionProgress) {
               this.mTransitionGoalPosition = var7.getFloat(var5, 0.0F);
               this.mInTransition = true;
               var6 = var4;
            } else if (var5 == R.styleable.MotionLayout_applyMotionScene) {
               var6 = var7.getBoolean(var5, var4);
            } else if (var5 == R.styleable.MotionLayout_showPaths) {
               var6 = var4;
               if (this.mDebugPath == 0) {
                  byte var9;
                  if (var7.getBoolean(var5, false)) {
                     var9 = 2;
                  } else {
                     var9 = 0;
                  }

                  this.mDebugPath = var9;
                  var6 = var4;
               }
            } else {
               var6 = var4;
               if (var5 == R.styleable.MotionLayout_motionDebug) {
                  this.mDebugPath = var7.getInt(var5, 0);
                  var6 = var4;
               }
            }

            ++var3;
         }

         var7.recycle();
         if (this.mScene == null) {
            Log.e("MotionLayout", "WARNING NO app:layoutDescription tag");
         }

         if (!var4) {
            this.mScene = null;
         }
      }

      if (this.mDebugPath != 0) {
         this.checkStructure();
      }

      if (this.mCurrentState == -1) {
         MotionScene var8 = this.mScene;
         if (var8 != null) {
            this.mCurrentState = var8.getStartId();
            this.mBeginState = this.mScene.getStartId();
            this.mEndState = this.mScene.getEndId();
         }
      }

   }

   private void onNewStateAttachHandlers() {
      MotionScene var1 = this.mScene;
      if (var1 != null) {
         if (var1.autoTransition(this, this.mCurrentState)) {
            this.requestLayout();
         } else {
            int var2 = this.mCurrentState;
            if (var2 != -1) {
               this.mScene.addOnClickListeners(this, var2);
            }

            if (this.mScene.supportTouch()) {
               this.mScene.setupTouch();
            }

         }
      }
   }

   private void processTransitionCompleted() {
      if (this.mTransitionListener == null) {
         ArrayList var1 = this.mTransitionListeners;
         if (var1 == null || var1.isEmpty()) {
            return;
         }
      }

      this.mIsAnimating = false;
      Iterator var4 = this.mTransitionCompleted.iterator();

      while(true) {
         Integer var2;
         ArrayList var5;
         do {
            if (!var4.hasNext()) {
               this.mTransitionCompleted.clear();
               return;
            }

            var2 = (Integer)var4.next();
            MotionLayout.TransitionListener var3 = this.mTransitionListener;
            if (var3 != null) {
               var3.onTransitionCompleted(this, var2);
            }

            var5 = this.mTransitionListeners;
         } while(var5 == null);

         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            ((MotionLayout.TransitionListener)var6.next()).onTransitionCompleted(this, var2);
         }
      }
   }

   private void setupMotionViews() {
      int var1 = this.getChildCount();
      this.mModel.build();
      boolean var2 = true;
      this.mInTransition = true;
      int var3 = this.getWidth();
      int var4 = this.getHeight();
      int var5 = this.mScene.gatPathMotionArc();
      int var6 = 0;
      byte var7 = 0;
      int var8;
      MotionController var9;
      if (var5 != -1) {
         for(var8 = 0; var8 < var1; ++var8) {
            var9 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var8));
            if (var9 != null) {
               var9.setPathMotionArc(var5);
            }
         }
      }

      for(var8 = 0; var8 < var1; ++var8) {
         var9 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var8));
         if (var9 != null) {
            this.mScene.getKeyFrames(var9);
            var9.setup(var3, var4, this.mTransitionDuration, this.getNanoTime());
         }
      }

      float var10 = this.mScene.getStaggered();
      if (var10 != 0.0F) {
         boolean var18;
         if ((double)var10 < 0.0D) {
            var18 = true;
         } else {
            var18 = false;
         }

         float var11 = Math.abs(var10);
         float var12 = -3.4028235E38F;
         float var13 = Float.MAX_VALUE;
         var4 = 0;
         float var14 = Float.MAX_VALUE;
         var10 = -3.4028235E38F;

         float var15;
         boolean var17;
         while(true) {
            if (var4 >= var1) {
               var17 = false;
               break;
            }

            var9 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var4));
            if (!Float.isNaN(var9.mMotionStagger)) {
               var17 = var2;
               break;
            }

            var15 = var9.getFinalX();
            float var16 = var9.getFinalY();
            if (var18) {
               var15 = var16 - var15;
            } else {
               var15 += var16;
            }

            var14 = Math.min(var14, var15);
            var10 = Math.max(var10, var15);
            ++var4;
         }

         if (var17) {
            var6 = 0;
            var14 = var13;
            var10 = var12;

            while(true) {
               var4 = var7;
               if (var6 >= var1) {
                  for(; var4 < var1; ++var4) {
                     var9 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var4));
                     if (!Float.isNaN(var9.mMotionStagger)) {
                        var9.mStaggerScale = 1.0F / (1.0F - var11);
                        if (var18) {
                           var9.mStaggerOffset = var11 - (var10 - var9.mMotionStagger) / (var10 - var14) * var11;
                        } else {
                           var9.mStaggerOffset = var11 - (var9.mMotionStagger - var14) * var11 / (var10 - var14);
                        }
                     }
                  }
                  break;
               }

               var9 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var6));
               var12 = var10;
               var15 = var14;
               if (!Float.isNaN(var9.mMotionStagger)) {
                  var15 = Math.min(var14, var9.mMotionStagger);
                  var12 = Math.max(var10, var9.mMotionStagger);
               }

               ++var6;
               var10 = var12;
               var14 = var15;
            }
         } else {
            while(var6 < var1) {
               var9 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var6));
               var12 = var9.getFinalX();
               var15 = var9.getFinalY();
               if (var18) {
                  var15 -= var12;
               } else {
                  var15 += var12;
               }

               var9.mStaggerScale = 1.0F / (1.0F - var11);
               var9.mStaggerOffset = var11 - (var15 - var14) * var11 / (var10 - var14);
               ++var6;
            }
         }
      }

   }

   private static boolean willJump(float var0, float var1, float var2) {
      boolean var3 = true;
      boolean var4 = true;
      float var5;
      if (var0 > 0.0F) {
         var5 = var0 / var2;
         if (var1 + (var0 * var5 - var2 * var5 * var5 / 2.0F) <= 1.0F) {
            var4 = false;
         }

         return var4;
      } else {
         var5 = -var0 / var2;
         if (var1 + var0 * var5 + var2 * var5 * var5 / 2.0F < 0.0F) {
            var4 = var3;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   public void addTransitionListener(MotionLayout.TransitionListener var1) {
      if (this.mTransitionListeners == null) {
         this.mTransitionListeners = new ArrayList();
      }

      this.mTransitionListeners.add(var1);
   }

   void animateTo(float var1) {
      if (this.mScene != null) {
         float var2 = this.mTransitionLastPosition;
         float var3 = this.mTransitionPosition;
         if (var2 != var3 && this.mTransitionInstantly) {
            this.mTransitionLastPosition = var3;
         }

         var3 = this.mTransitionLastPosition;
         if (var3 != var1) {
            this.mTemporalInterpolator = false;
            this.mTransitionGoalPosition = var1;
            this.mTransitionDuration = (float)this.mScene.getDuration() / 1000.0F;
            this.setProgress(this.mTransitionGoalPosition);
            this.mInterpolator = this.mScene.getInterpolator();
            this.mTransitionInstantly = false;
            this.mAnimationStartTime = this.getNanoTime();
            this.mInTransition = true;
            this.mTransitionPosition = var3;
            this.mTransitionLastPosition = var3;
            this.invalidate();
         }
      }
   }

   void disableAutoTransition(boolean var1) {
      MotionScene var2 = this.mScene;
      if (var2 != null) {
         var2.disableAutoTransition(var1);
      }
   }

   protected void dispatchDraw(Canvas var1) {
      this.evaluate(false);
      super.dispatchDraw(var1);
      if (this.mScene != null) {
         if ((this.mDebugPath & 1) == 1 && !this.isInEditMode()) {
            ++this.mFrames;
            long var2 = this.getNanoTime();
            long var4 = this.mLastDrawTime;
            if (var4 != -1L) {
               var4 = var2 - var4;
               if (var4 > 200000000L) {
                  this.mLastFps = (float)((int)((float)this.mFrames / ((float)var4 * 1.0E-9F) * 100.0F)) / 100.0F;
                  this.mFrames = 0;
                  this.mLastDrawTime = var2;
               }
            } else {
               this.mLastDrawTime = var2;
            }

            Paint var6 = new Paint();
            var6.setTextSize(42.0F);
            float var7 = (float)((int)(this.getProgress() * 1000.0F)) / 10.0F;
            StringBuilder var8 = new StringBuilder();
            var8.append(this.mLastFps);
            var8.append(" fps ");
            var8.append(Debug.getState(this, this.mBeginState));
            var8.append(" -> ");
            String var11 = var8.toString();
            StringBuilder var9 = new StringBuilder();
            var9.append(var11);
            var9.append(Debug.getState(this, this.mEndState));
            var9.append(" (progress: ");
            var9.append(var7);
            var9.append(" ) state=");
            int var10 = this.mCurrentState;
            if (var10 == -1) {
               var11 = "undefined";
            } else {
               var11 = Debug.getState(this, var10);
            }

            var9.append(var11);
            var11 = var9.toString();
            var6.setColor(-16777216);
            var1.drawText(var11, 11.0F, (float)(this.getHeight() - 29), var6);
            var6.setColor(-7864184);
            var1.drawText(var11, 10.0F, (float)(this.getHeight() - 30), var6);
         }

         if (this.mDebugPath > 1) {
            if (this.mDevModeDraw == null) {
               this.mDevModeDraw = new MotionLayout.DevModeDraw();
            }

            this.mDevModeDraw.draw(var1, this.mFrameArrayList, this.mScene.getDuration(), this.mDebugPath);
         }

      }
   }

   public void enableTransition(int var1, boolean var2) {
      MotionScene.Transition var3 = this.getTransition(var1);
      if (var2) {
         var3.setEnable(true);
      } else {
         if (var3 == this.mScene.mCurrentTransition) {
            Iterator var4 = this.mScene.getTransitionsWithState(this.mCurrentState).iterator();

            while(var4.hasNext()) {
               MotionScene.Transition var5 = (MotionScene.Transition)var4.next();
               if (var5.isEnabled()) {
                  this.mScene.mCurrentTransition = var5;
                  break;
               }
            }
         }

         var3.setEnable(false);
      }
   }

   void evaluate(boolean var1) {
      if (this.mTransitionLastTime == -1L) {
         this.mTransitionLastTime = this.getNanoTime();
      }

      float var2 = this.mTransitionLastPosition;
      if (var2 > 0.0F && var2 < 1.0F) {
         this.mCurrentState = -1;
      }

      boolean var4;
      boolean var5;
      boolean var7;
      boolean var8;
      label247: {
         boolean var3 = this.mKeepAnimating;
         var4 = true;
         var5 = true;
         boolean var6 = false;
         var7 = false;
         if (!var3) {
            var8 = var6;
            if (!this.mInTransition) {
               break label247;
            }

            if (!var1) {
               var8 = var6;
               if (this.mTransitionGoalPosition == this.mTransitionLastPosition) {
                  break label247;
               }
            }
         }

         float var9 = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
         long var10 = this.getNanoTime();
         float var12;
         if (!(this.mInterpolator instanceof MotionInterpolator)) {
            var12 = (float)(var10 - this.mTransitionLastTime) * var9 * 1.0E-9F / this.mTransitionDuration;
            this.mLastVelocity = var12;
         } else {
            var12 = 0.0F;
         }

         float var13 = this.mTransitionLastPosition + var12;
         if (this.mTransitionInstantly) {
            var13 = this.mTransitionGoalPosition;
         }

         float var23;
         int var20 = (var23 = var9 - 0.0F) == 0.0F ? 0 : (var23 < 0.0F ? -1 : 1);
         if ((var20 <= 0 || var13 < this.mTransitionGoalPosition) && (var9 > 0.0F || var13 > this.mTransitionGoalPosition)) {
            var8 = false;
         } else {
            var13 = this.mTransitionGoalPosition;
            this.mInTransition = false;
            var8 = true;
         }

         this.mTransitionLastPosition = var13;
         this.mTransitionPosition = var13;
         this.mTransitionLastTime = var10;
         Interpolator var14 = this.mInterpolator;
         var2 = var13;
         if (var14 != null) {
            var2 = var13;
            if (!var8) {
               if (this.mTemporalInterpolator) {
                  var12 = var14.getInterpolation((float)(var10 - this.mAnimationStartTime) * 1.0E-9F);
                  this.mTransitionLastPosition = var12;
                  this.mTransitionLastTime = var10;
                  var14 = this.mInterpolator;
                  var2 = var12;
                  if (var14 instanceof MotionInterpolator) {
                     float var15 = ((MotionInterpolator)var14).getVelocity();
                     this.mLastVelocity = var15;
                     if (Math.abs(var15) * this.mTransitionDuration <= 1.0E-5F) {
                        this.mInTransition = false;
                     }

                     var13 = var12;
                     if (var15 > 0.0F) {
                        var13 = var12;
                        if (var12 >= 1.0F) {
                           this.mTransitionLastPosition = 1.0F;
                           this.mInTransition = false;
                           var13 = 1.0F;
                        }
                     }

                     var2 = var13;
                     if (var15 < 0.0F) {
                        var2 = var13;
                        if (var13 <= 0.0F) {
                           this.mTransitionLastPosition = 0.0F;
                           this.mInTransition = false;
                           var2 = 0.0F;
                        }
                     }
                  }
               } else {
                  var2 = var14.getInterpolation(var13);
                  var14 = this.mInterpolator;
                  if (var14 instanceof MotionInterpolator) {
                     this.mLastVelocity = ((MotionInterpolator)var14).getVelocity();
                  } else {
                     this.mLastVelocity = (var14.getInterpolation(var13 + var12) - var2) * var9 / var12;
                  }
               }
            }
         }

         if (Math.abs(this.mLastVelocity) > 1.0E-5F) {
            this.setState(MotionLayout.TransitionState.MOVING);
         }

         label245: {
            if (var20 <= 0 || var2 < this.mTransitionGoalPosition) {
               var13 = var2;
               if (var9 > 0.0F) {
                  break label245;
               }

               var13 = var2;
               if (var2 > this.mTransitionGoalPosition) {
                  break label245;
               }
            }

            var13 = this.mTransitionGoalPosition;
            this.mInTransition = false;
         }

         float var24;
         int var16 = (var24 = var13 - 1.0F) == 0.0F ? 0 : (var24 < 0.0F ? -1 : 1);
         if (var16 >= 0 || var13 <= 0.0F) {
            this.mInTransition = false;
            this.setState(MotionLayout.TransitionState.FINISHED);
         }

         int var17 = this.getChildCount();
         this.mKeepAnimating = false;
         var10 = this.getNanoTime();
         this.mPostInterpolationPosition = var13;

         for(int var21 = 0; var21 < var17; ++var21) {
            View var22 = this.getChildAt(var21);
            MotionController var18 = (MotionController)this.mFrameArrayList.get(var22);
            if (var18 != null) {
               var1 = this.mKeepAnimating;
               this.mKeepAnimating = var18.interpolate(var22, var13, var10, this.mKeyCache) | var1;
            }
         }

         if ((var20 <= 0 || var13 < this.mTransitionGoalPosition) && (var9 > 0.0F || var13 > this.mTransitionGoalPosition)) {
            var8 = false;
         } else {
            var8 = true;
         }

         if (!this.mKeepAnimating && !this.mInTransition && var8) {
            this.setState(MotionLayout.TransitionState.FINISHED);
         }

         if (this.mMeasureDuringTransition) {
            this.requestLayout();
         }

         this.mKeepAnimating |= var8 ^ true;
         var8 = var7;
         if (var13 <= 0.0F) {
            var17 = this.mBeginState;
            var8 = var7;
            if (var17 != -1) {
               var8 = var7;
               if (this.mCurrentState != var17) {
                  this.mCurrentState = var17;
                  this.mScene.getConstraintSet(var17).applyCustomAttributes(this);
                  this.setState(MotionLayout.TransitionState.FINISHED);
                  var8 = true;
               }
            }
         }

         var7 = var8;
         if ((double)var13 >= 1.0D) {
            int var19 = this.mCurrentState;
            var17 = this.mEndState;
            var7 = var8;
            if (var19 != var17) {
               this.mCurrentState = var17;
               this.mScene.getConstraintSet(var17).applyCustomAttributes(this);
               this.setState(MotionLayout.TransitionState.FINISHED);
               var7 = true;
            }
         }

         if (!this.mKeepAnimating && !this.mInTransition) {
            if (var20 > 0 && var16 == 0 || var9 < 0.0F && var13 == 0.0F) {
               this.setState(MotionLayout.TransitionState.FINISHED);
            }
         } else {
            this.invalidate();
         }

         if (this.mKeepAnimating || !this.mInTransition || var20 <= 0 || var16 != 0) {
            var8 = var7;
            if (var9 >= 0.0F) {
               break label247;
            }

            var8 = var7;
            if (var13 != 0.0F) {
               break label247;
            }
         }

         this.onNewStateAttachHandlers();
         var8 = var7;
      }

      label139: {
         var2 = this.mTransitionLastPosition;
         if (var2 >= 1.0F) {
            if (this.mCurrentState != this.mEndState) {
               var8 = var5;
            }

            this.mCurrentState = this.mEndState;
         } else {
            var7 = var8;
            if (var2 > 0.0F) {
               break label139;
            }

            if (this.mCurrentState != this.mBeginState) {
               var8 = var4;
            }

            this.mCurrentState = this.mBeginState;
         }

         var7 = var8;
      }

      this.mNeedsFireTransitionCompleted |= var7;
      if (var7 && !this.mInLayout) {
         this.requestLayout();
      }

      this.mTransitionPosition = this.mTransitionLastPosition;
   }

   protected void fireTransitionCompleted() {
      label25: {
         ArrayList var1;
         if (this.mTransitionListener == null) {
            var1 = this.mTransitionListeners;
            if (var1 == null || var1.isEmpty()) {
               break label25;
            }
         }

         if (this.mListenerState == -1) {
            this.mListenerState = this.mCurrentState;
            int var2;
            if (!this.mTransitionCompleted.isEmpty()) {
               var1 = this.mTransitionCompleted;
               var2 = (Integer)var1.get(var1.size() - 1);
            } else {
               var2 = -1;
            }

            int var3 = this.mCurrentState;
            if (var2 != var3 && var3 != -1) {
               this.mTransitionCompleted.add(var3);
            }
         }
      }

      this.processTransitionCompleted();
   }

   public void fireTrigger(int var1, boolean var2, float var3) {
      MotionLayout.TransitionListener var4 = this.mTransitionListener;
      if (var4 != null) {
         var4.onTransitionTrigger(this, var1, var2, var3);
      }

      ArrayList var5 = this.mTransitionListeners;
      if (var5 != null) {
         Iterator var6 = var5.iterator();

         while(var6.hasNext()) {
            ((MotionLayout.TransitionListener)var6.next()).onTransitionTrigger(this, var1, var2, var3);
         }
      }

   }

   void getAnchorDpDt(int var1, float var2, float var3, float var4, float[] var5) {
      HashMap var6 = this.mFrameArrayList;
      View var7 = this.getViewById(var1);
      MotionController var10 = (MotionController)var6.get(var7);
      if (var10 != null) {
         var10.getDpDt(var2, var3, var4, var5);
         var4 = var7.getY();
         var3 = this.lastPos;
         this.lastPos = var2;
         this.lastY = var4;
      } else {
         String var9;
         if (var7 == null) {
            StringBuilder var8 = new StringBuilder();
            var8.append("");
            var8.append(var1);
            var9 = var8.toString();
         } else {
            var9 = var7.getContext().getResources().getResourceName(var1);
         }

         StringBuilder var11 = new StringBuilder();
         var11.append("WARNING could not find view id ");
         var11.append(var9);
         Log.w("MotionLayout", var11.toString());
      }

   }

   public ConstraintSet getConstraintSet(int var1) {
      MotionScene var2 = this.mScene;
      return var2 == null ? null : var2.getConstraintSet(var1);
   }

   public int[] getConstraintSetIds() {
      MotionScene var1 = this.mScene;
      return var1 == null ? null : var1.getConstraintSetIds();
   }

   String getConstraintSetNames(int var1) {
      MotionScene var2 = this.mScene;
      return var2 == null ? null : var2.lookUpConstraintName(var1);
   }

   public int getCurrentState() {
      return this.mCurrentState;
   }

   public void getDebugMode(boolean var1) {
      byte var2;
      if (var1) {
         var2 = 2;
      } else {
         var2 = 1;
      }

      this.mDebugPath = var2;
      this.invalidate();
   }

   public ArrayList<MotionScene.Transition> getDefinedTransitions() {
      MotionScene var1 = this.mScene;
      return var1 == null ? null : var1.getDefinedTransitions();
   }

   public DesignTool getDesignTool() {
      if (this.mDesignTool == null) {
         this.mDesignTool = new DesignTool(this);
      }

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

   public MotionScene.Transition getTransition(int var1) {
      return this.mScene.getTransitionById(var1);
   }

   public Bundle getTransitionState() {
      if (this.mStateCache == null) {
         this.mStateCache = new MotionLayout.StateCache();
      }

      this.mStateCache.recordState();
      return this.mStateCache.getTransitionState();
   }

   public long getTransitionTimeMs() {
      MotionScene var1 = this.mScene;
      if (var1 != null) {
         this.mTransitionDuration = (float)var1.getDuration() / 1000.0F;
      }

      return (long)(this.mTransitionDuration * 1000.0F);
   }

   public float getVelocity() {
      return this.mLastVelocity;
   }

   public void getViewVelocity(View var1, float var2, float var3, float[] var4, int var5) {
      float var6 = this.mLastVelocity;
      float var7 = this.mTransitionLastPosition;
      if (this.mInterpolator != null) {
         var6 = Math.signum(this.mTransitionGoalPosition - var7);
         float var8 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition + 1.0E-5F);
         var7 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition);
         var6 = var6 * ((var8 - var7) / 1.0E-5F) / this.mTransitionDuration;
      }

      Interpolator var9 = this.mInterpolator;
      if (var9 instanceof MotionInterpolator) {
         var6 = ((MotionInterpolator)var9).getVelocity();
      }

      MotionController var10 = (MotionController)this.mFrameArrayList.get(var1);
      if ((var5 & 1) == 0) {
         var10.getPostLayoutDvDp(var7, var1.getWidth(), var1.getHeight(), var2, var3, var4);
      } else {
         var10.getDpDt(var7, var2, var3, var4);
      }

      if (var5 < 2) {
         var4[0] *= var6;
         var4[1] *= var6;
      }

   }

   public boolean isAttachedToWindow() {
      if (android.os.Build.VERSION.SDK_INT >= 19) {
         return super.isAttachedToWindow();
      } else {
         boolean var1;
         if (this.getWindowToken() != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public boolean isInteractionEnabled() {
      return this.mInteractionEnabled;
   }

   public void loadLayoutDescription(int var1) {
      if (var1 != 0) {
         try {
            MotionScene var2 = new MotionScene(this.getContext(), this, var1);
            this.mScene = var2;
            if (android.os.Build.VERSION.SDK_INT < 19 || this.isAttachedToWindow()) {
               this.mScene.readFallback(this);
               this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
               this.rebuildScene();
               this.mScene.setRtl(this.isRtl());
            }
         } catch (Exception var3) {
            throw new IllegalArgumentException("unable to parse MotionScene file", var3);
         }
      } else {
         this.mScene = null;
      }

   }

   int lookUpConstraintId(String var1) {
      MotionScene var2 = this.mScene;
      return var2 == null ? 0 : var2.lookUpConstraintId(var1);
   }

   protected MotionLayout.MotionTracker obtainVelocityTracker() {
      return MotionLayout.MyTracker.obtain();
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      MotionScene var1 = this.mScene;
      if (var1 != null) {
         int var2 = this.mCurrentState;
         if (var2 != -1) {
            ConstraintSet var3 = var1.getConstraintSet(var2);
            this.mScene.readFallback(this);
            if (var3 != null) {
               var3.applyTo(this);
            }

            this.mBeginState = this.mCurrentState;
         }
      }

      this.onNewStateAttachHandlers();
      MotionLayout.StateCache var4 = this.mStateCache;
      if (var4 != null) {
         var4.apply();
      }

   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      MotionScene var2 = this.mScene;
      if (var2 != null && this.mInteractionEnabled) {
         MotionScene.Transition var5 = var2.mCurrentTransition;
         if (var5 != null && var5.isEnabled()) {
            TouchResponse var6 = var5.getTouchResponse();
            if (var6 != null) {
               if (var1.getAction() == 0) {
                  RectF var3 = var6.getTouchRegion(this, new RectF());
                  if (var3 != null && !var3.contains(var1.getX(), var1.getY())) {
                     return false;
                  }
               }

               int var4 = var6.getTouchRegionId();
               if (var4 != -1) {
                  View var7 = this.mRegionView;
                  if (var7 == null || var7.getId() != var4) {
                     this.mRegionView = this.findViewById(var4);
                  }

                  var7 = this.mRegionView;
                  if (var7 != null) {
                     this.mBoundsCheck.set((float)var7.getLeft(), (float)this.mRegionView.getTop(), (float)this.mRegionView.getRight(), (float)this.mRegionView.getBottom());
                     if (this.mBoundsCheck.contains(var1.getX(), var1.getY()) && !this.handlesTouchEvent(0.0F, 0.0F, this.mRegionView, var1)) {
                        return this.onTouchEvent(var1);
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      this.mInLayout = true;

      Throwable var10000;
      label225: {
         label229: {
            boolean var10001;
            try {
               if (this.mScene == null) {
                  super.onLayout(var1, var2, var3, var4, var5);
                  break label229;
               }
            } catch (Throwable var26) {
               var10000 = var26;
               var10001 = false;
               break label225;
            }

            var2 = var4 - var2;
            var3 = var5 - var3;

            label215: {
               try {
                  if (this.mLastLayoutWidth == var2 && this.mLastLayoutHeight == var3) {
                     break label215;
                  }
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break label225;
               }

               try {
                  this.rebuildScene();
                  this.evaluate(true);
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label225;
               }
            }

            try {
               this.mLastLayoutWidth = var2;
               this.mLastLayoutHeight = var3;
               this.mOldWidth = var2;
               this.mOldHeight = var3;
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label225;
            }

            this.mInLayout = false;
            return;
         }

         this.mInLayout = false;
         return;
      }

      Throwable var6 = var10000;
      this.mInLayout = false;
      throw var6;
   }

   protected void onMeasure(int var1, int var2) {
      if (this.mScene == null) {
         super.onMeasure(var1, var2);
      } else {
         int var3 = this.mLastWidthMeasureSpec;
         boolean var4 = false;
         boolean var8;
         if (var3 == var1 && this.mLastHeightMeasureSpec == var2) {
            var8 = false;
         } else {
            var8 = true;
         }

         if (this.mNeedsFireTransitionCompleted) {
            this.mNeedsFireTransitionCompleted = false;
            this.onNewStateAttachHandlers();
            this.processTransitionCompleted();
            var8 = true;
         }

         if (this.mDirtyHierarchy) {
            var8 = true;
         }

         this.mLastWidthMeasureSpec = var1;
         this.mLastHeightMeasureSpec = var2;
         int var5 = this.mScene.getStartId();
         int var6 = this.mScene.getEndId();
         boolean var7;
         if ((var8 || this.mModel.isNotConfiguredWith(var5, var6)) && this.mBeginState != -1) {
            super.onMeasure(var1, var2);
            this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(var5), this.mScene.getConstraintSet(var6));
            this.mModel.reEvaluateState();
            this.mModel.setMeasuredId(var5, var6);
            var7 = var4;
         } else {
            var7 = true;
         }

         if (this.mMeasureDuringTransition || var7) {
            var2 = this.getPaddingTop();
            var3 = this.getPaddingBottom();
            var1 = this.getPaddingLeft();
            int var9 = this.getPaddingRight();
            var1 = this.mLayoutWidget.getWidth() + var1 + var9;
            var2 = this.mLayoutWidget.getHeight() + var2 + var3;
            var3 = this.mWidthMeasureMode;
            if (var3 == Integer.MIN_VALUE || var3 == 0) {
               var1 = this.mStartWrapWidth;
               var1 = (int)((float)var1 + this.mPostInterpolationPosition * (float)(this.mEndWrapWidth - var1));
               this.requestLayout();
            }

            var3 = this.mHeightMeasureMode;
            if (var3 == Integer.MIN_VALUE || var3 == 0) {
               var2 = this.mStartWrapHeight;
               var2 = (int)((float)var2 + this.mPostInterpolationPosition * (float)(this.mEndWrapHeight - var2));
               this.requestLayout();
            }

            this.setMeasuredDimension(var1, var2);
         }

         this.evaluateLayout();
      }
   }

   public boolean onNestedFling(View var1, float var2, float var3, boolean var4) {
      return false;
   }

   public boolean onNestedPreFling(View var1, float var2, float var3) {
      return false;
   }

   public void onNestedPreScroll(final View var1, int var2, int var3, int[] var4, int var5) {
      MotionScene var6 = this.mScene;
      if (var6 != null && var6.mCurrentTransition != null) {
         if (!this.mScene.mCurrentTransition.isEnabled()) {
            return;
         }

         MotionScene.Transition var13 = this.mScene.mCurrentTransition;
         if (var13 != null && var13.isEnabled()) {
            TouchResponse var7 = var13.getTouchResponse();
            if (var7 != null) {
               var5 = var7.getTouchRegionId();
               if (var5 != -1 && var1.getId() != var5) {
                  return;
               }
            }
         }

         MotionScene var14 = this.mScene;
         float var8;
         if (var14 != null && var14.getMoveWhenScrollAtTop()) {
            var8 = this.mTransitionPosition;
            if ((var8 == 1.0F || var8 == 0.0F) && var1.canScrollVertically(-1)) {
               return;
            }
         }

         if (var13.getTouchResponse() != null && (this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 1) != 0) {
            var8 = this.mScene.getProgressDirection((float)var2, (float)var3);
            if (this.mTransitionLastPosition <= 0.0F && var8 < 0.0F || this.mTransitionLastPosition >= 1.0F && var8 > 0.0F) {
               if (android.os.Build.VERSION.SDK_INT >= 21) {
                  var1.setNestedScrollingEnabled(false);
                  var1.post(new Runnable() {
                     public void run() {
                        var1.setNestedScrollingEnabled(true);
                     }
                  });
               }

               return;
            }
         }

         float var9 = this.mTransitionPosition;
         long var10 = this.getNanoTime();
         float var12 = (float)var2;
         this.mScrollTargetDX = var12;
         var8 = (float)var3;
         this.mScrollTargetDY = var8;
         this.mScrollTargetDT = (float)((double)(var10 - this.mScrollTargetTime) * 1.0E-9D);
         this.mScrollTargetTime = var10;
         this.mScene.processScrollMove(var12, var8);
         if (var9 != this.mTransitionPosition) {
            var4[0] = var2;
            var4[1] = var3;
         }

         this.evaluate(false);
         if (var4[0] != 0 || var4[1] != 0) {
            this.mUndergoingMotion = true;
         }
      }

   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public void onNestedScroll(View var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      if (this.mUndergoingMotion || var2 != 0 || var3 != 0) {
         var7[0] += var4;
         var7[1] += var5;
      }

      this.mUndergoingMotion = false;
   }

   public void onNestedScrollAccepted(View var1, View var2, int var3, int var4) {
   }

   public void onRtlPropertiesChanged(int var1) {
      MotionScene var2 = this.mScene;
      if (var2 != null) {
         var2.setRtl(this.isRtl());
      }

   }

   public boolean onStartNestedScroll(View var1, View var2, int var3, int var4) {
      MotionScene var5 = this.mScene;
      return var5 != null && var5.mCurrentTransition != null && this.mScene.mCurrentTransition.getTouchResponse() != null && (this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 2) == 0;
   }

   public void onStopNestedScroll(View var1, int var2) {
      MotionScene var5 = this.mScene;
      if (var5 != null) {
         float var3 = this.mScrollTargetDX;
         float var4 = this.mScrollTargetDT;
         var5.processScrollUp(var3 / var4, this.mScrollTargetDY / var4);
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      MotionScene var2 = this.mScene;
      if (var2 != null && this.mInteractionEnabled && var2.supportTouch()) {
         MotionScene.Transition var3 = this.mScene.mCurrentTransition;
         if (var3 != null && !var3.isEnabled()) {
            return super.onTouchEvent(var1);
         } else {
            this.mScene.processTouchEvent(var1, this.getCurrentState(), this);
            return true;
         }
      } else {
         return super.onTouchEvent(var1);
      }
   }

   public void onViewAdded(View var1) {
      super.onViewAdded(var1);
      if (var1 instanceof MotionHelper) {
         MotionHelper var2 = (MotionHelper)var1;
         if (this.mTransitionListeners == null) {
            this.mTransitionListeners = new ArrayList();
         }

         this.mTransitionListeners.add(var2);
         if (var2.isUsedOnShow()) {
            if (this.mOnShowHelpers == null) {
               this.mOnShowHelpers = new ArrayList();
            }

            this.mOnShowHelpers.add(var2);
         }

         if (var2.isUseOnHide()) {
            if (this.mOnHideHelpers == null) {
               this.mOnHideHelpers = new ArrayList();
            }

            this.mOnHideHelpers.add(var2);
         }
      }

   }

   public void onViewRemoved(View var1) {
      super.onViewRemoved(var1);
      ArrayList var2 = this.mOnShowHelpers;
      if (var2 != null) {
         var2.remove(var1);
      }

      var2 = this.mOnHideHelpers;
      if (var2 != null) {
         var2.remove(var1);
      }

   }

   protected void parseLayoutDescription(int var1) {
      this.mConstraintLayoutSpec = null;
   }

   @Deprecated
   public void rebuildMotion() {
      Log.e("MotionLayout", "This method is deprecated. Please call rebuildScene() instead.");
      this.rebuildScene();
   }

   public void rebuildScene() {
      this.mModel.reEvaluateState();
      this.invalidate();
   }

   public boolean removeTransitionListener(MotionLayout.TransitionListener var1) {
      ArrayList var2 = this.mTransitionListeners;
      return var2 == null ? false : var2.remove(var1);
   }

   public void requestLayout() {
      if (!this.mMeasureDuringTransition && this.mCurrentState == -1) {
         MotionScene var1 = this.mScene;
         if (var1 != null && var1.mCurrentTransition != null && this.mScene.mCurrentTransition.getLayoutDuringTransition() == 0) {
            return;
         }
      }

      super.requestLayout();
   }

   public void setDebugMode(int var1) {
      this.mDebugPath = var1;
      this.invalidate();
   }

   public void setInteractionEnabled(boolean var1) {
      this.mInteractionEnabled = var1;
   }

   public void setInterpolatedProgress(float var1) {
      if (this.mScene != null) {
         this.setState(MotionLayout.TransitionState.MOVING);
         Interpolator var2 = this.mScene.getInterpolator();
         if (var2 != null) {
            this.setProgress(var2.getInterpolation(var1));
            return;
         }
      }

      this.setProgress(var1);
   }

   public void setOnHide(float var1) {
      ArrayList var2 = this.mOnHideHelpers;
      if (var2 != null) {
         int var3 = var2.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            ((MotionHelper)this.mOnHideHelpers.get(var4)).setProgress(var1);
         }
      }

   }

   public void setOnShow(float var1) {
      ArrayList var2 = this.mOnShowHelpers;
      if (var2 != null) {
         int var3 = var2.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            ((MotionHelper)this.mOnShowHelpers.get(var4)).setProgress(var1);
         }
      }

   }

   public void setProgress(float var1) {
      if (!this.isAttachedToWindow()) {
         if (this.mStateCache == null) {
            this.mStateCache = new MotionLayout.StateCache();
         }

         this.mStateCache.setProgress(var1);
      } else {
         if (var1 <= 0.0F) {
            this.mCurrentState = this.mBeginState;
            if (this.mTransitionLastPosition == 0.0F) {
               this.setState(MotionLayout.TransitionState.FINISHED);
            }
         } else if (var1 >= 1.0F) {
            this.mCurrentState = this.mEndState;
            if (this.mTransitionLastPosition == 1.0F) {
               this.setState(MotionLayout.TransitionState.FINISHED);
            }
         } else {
            this.mCurrentState = -1;
            this.setState(MotionLayout.TransitionState.MOVING);
         }

         if (this.mScene != null) {
            this.mTransitionInstantly = true;
            this.mTransitionGoalPosition = var1;
            this.mTransitionPosition = var1;
            this.mTransitionLastTime = -1L;
            this.mAnimationStartTime = -1L;
            this.mInterpolator = null;
            this.mInTransition = true;
            this.invalidate();
         }
      }
   }

   public void setProgress(float var1, float var2) {
      if (!this.isAttachedToWindow()) {
         if (this.mStateCache == null) {
            this.mStateCache = new MotionLayout.StateCache();
         }

         this.mStateCache.setProgress(var1);
         this.mStateCache.setVelocity(var2);
      } else {
         this.setProgress(var1);
         this.setState(MotionLayout.TransitionState.MOVING);
         this.mLastVelocity = var2;
         this.animateTo(1.0F);
      }
   }

   public void setScene(MotionScene var1) {
      this.mScene = var1;
      var1.setRtl(this.isRtl());
      this.rebuildScene();
   }

   public void setState(int var1, int var2, int var3) {
      this.setState(MotionLayout.TransitionState.SETUP);
      this.mCurrentState = var1;
      this.mBeginState = -1;
      this.mEndState = -1;
      if (this.mConstraintLayoutSpec != null) {
         this.mConstraintLayoutSpec.updateConstraints(var1, (float)var2, (float)var3);
      } else {
         MotionScene var4 = this.mScene;
         if (var4 != null) {
            var4.getConstraintSet(var1).applyTo(this);
         }
      }

   }

   void setState(MotionLayout.TransitionState var1) {
      if (var1 != MotionLayout.TransitionState.FINISHED || this.mCurrentState != -1) {
         MotionLayout.TransitionState var2 = this.mTransitionState;
         this.mTransitionState = var1;
         if (var2 == MotionLayout.TransitionState.MOVING && var1 == MotionLayout.TransitionState.MOVING) {
            this.fireTransitionChange();
         }

         int var3 = null.$SwitchMap$androidx$constraintlayout$motion$widget$MotionLayout$TransitionState[var2.ordinal()];
         if (var3 != 1 && var3 != 2) {
            if (var3 == 3 && var1 == MotionLayout.TransitionState.FINISHED) {
               this.fireTransitionCompleted();
            }
         } else {
            if (var1 == MotionLayout.TransitionState.MOVING) {
               this.fireTransitionChange();
            }

            if (var1 == MotionLayout.TransitionState.FINISHED) {
               this.fireTransitionCompleted();
            }
         }

      }
   }

   public void setTransition(int var1) {
      if (this.mScene != null) {
         MotionScene.Transition var2 = this.getTransition(var1);
         this.mBeginState = var2.getStartConstraintSetId();
         this.mEndState = var2.getEndConstraintSetId();
         if (!this.isAttachedToWindow()) {
            if (this.mStateCache == null) {
               this.mStateCache = new MotionLayout.StateCache();
            }

            this.mStateCache.setStartState(this.mBeginState);
            this.mStateCache.setEndState(this.mEndState);
            return;
         }

         float var3 = Float.NaN;
         int var4 = this.mCurrentState;
         var1 = this.mBeginState;
         float var5 = 0.0F;
         if (var4 == var1) {
            var3 = 0.0F;
         } else if (var4 == this.mEndState) {
            var3 = 1.0F;
         }

         this.mScene.setTransition(var2);
         this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
         this.rebuildScene();
         if (!Float.isNaN(var3)) {
            var5 = var3;
         }

         this.mTransitionLastPosition = var5;
         if (Float.isNaN(var3)) {
            StringBuilder var6 = new StringBuilder();
            var6.append(Debug.getLocation());
            var6.append(" transitionToStart ");
            Log.v("MotionLayout", var6.toString());
            this.transitionToStart();
         } else {
            this.setProgress(var3);
         }
      }

   }

   public void setTransition(int var1, int var2) {
      if (!this.isAttachedToWindow()) {
         if (this.mStateCache == null) {
            this.mStateCache = new MotionLayout.StateCache();
         }

         this.mStateCache.setStartState(var1);
         this.mStateCache.setEndState(var2);
      } else {
         MotionScene var3 = this.mScene;
         if (var3 != null) {
            this.mBeginState = var1;
            this.mEndState = var2;
            var3.setTransition(var1, var2);
            this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(var1), this.mScene.getConstraintSet(var2));
            this.rebuildScene();
            this.mTransitionLastPosition = 0.0F;
            this.transitionToStart();
         }

      }
   }

   protected void setTransition(MotionScene.Transition var1) {
      this.mScene.setTransition(var1);
      this.setState(MotionLayout.TransitionState.SETUP);
      if (this.mCurrentState == this.mScene.getEndId()) {
         this.mTransitionLastPosition = 1.0F;
         this.mTransitionPosition = 1.0F;
         this.mTransitionGoalPosition = 1.0F;
      } else {
         this.mTransitionLastPosition = 0.0F;
         this.mTransitionPosition = 0.0F;
         this.mTransitionGoalPosition = 0.0F;
      }

      long var2;
      if (var1.isTransitionFlag(1)) {
         var2 = -1L;
      } else {
         var2 = this.getNanoTime();
      }

      this.mTransitionLastTime = var2;
      int var4 = this.mScene.getStartId();
      int var5 = this.mScene.getEndId();
      if (var4 != this.mBeginState || var5 != this.mEndState) {
         this.mBeginState = var4;
         this.mEndState = var5;
         this.mScene.setTransition(var4, var5);
         this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
         this.mModel.setMeasuredId(this.mBeginState, this.mEndState);
         this.mModel.reEvaluateState();
         this.rebuildScene();
      }
   }

   public void setTransitionDuration(int var1) {
      MotionScene var2 = this.mScene;
      if (var2 == null) {
         Log.e("MotionLayout", "MotionScene not defined");
      } else {
         var2.setDuration(var1);
      }
   }

   public void setTransitionListener(MotionLayout.TransitionListener var1) {
      this.mTransitionListener = var1;
   }

   public void setTransitionState(Bundle var1) {
      if (this.mStateCache == null) {
         this.mStateCache = new MotionLayout.StateCache();
      }

      this.mStateCache.setTransitionState(var1);
      if (this.isAttachedToWindow()) {
         this.mStateCache.apply();
      }

   }

   public String toString() {
      Context var1 = this.getContext();
      StringBuilder var2 = new StringBuilder();
      var2.append(Debug.getName(var1, this.mBeginState));
      var2.append("->");
      var2.append(Debug.getName(var1, this.mEndState));
      var2.append(" (pos:");
      var2.append(this.mTransitionLastPosition);
      var2.append(" Dpos/Dt:");
      var2.append(this.mLastVelocity);
      return var2.toString();
   }

   public void touchAnimateTo(int var1, float var2, float var3) {
      if (this.mScene != null) {
         if (this.mTransitionLastPosition != var2) {
            this.mTemporalInterpolator = true;
            this.mAnimationStartTime = this.getNanoTime();
            this.mTransitionDuration = (float)this.mScene.getDuration() / 1000.0F;
            this.mTransitionGoalPosition = var2;
            this.mInTransition = true;
            if (var1 != 0 && var1 != 1 && var1 != 2) {
               if (var1 != 4) {
                  if (var1 == 5) {
                     if (willJump(var3, this.mTransitionLastPosition, this.mScene.getMaxAcceleration())) {
                        this.mDecelerateLogic.config(var3, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
                        this.mInterpolator = this.mDecelerateLogic;
                     } else {
                        this.mStopLogic.config(this.mTransitionLastPosition, var2, var3, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
                        this.mLastVelocity = 0.0F;
                        var1 = this.mCurrentState;
                        this.mTransitionGoalPosition = var2;
                        this.mCurrentState = var1;
                        this.mInterpolator = this.mStopLogic;
                     }
                  }
               } else {
                  this.mDecelerateLogic.config(var3, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
                  this.mInterpolator = this.mDecelerateLogic;
               }
            } else {
               if (var1 == 1) {
                  var2 = 0.0F;
               } else if (var1 == 2) {
                  var2 = 1.0F;
               }

               this.mStopLogic.config(this.mTransitionLastPosition, var2, var3, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
               var1 = this.mCurrentState;
               this.mTransitionGoalPosition = var2;
               this.mCurrentState = var1;
               this.mInterpolator = this.mStopLogic;
            }

            this.mTransitionInstantly = false;
            this.mAnimationStartTime = this.getNanoTime();
            this.invalidate();
         }
      }
   }

   public void transitionToEnd() {
      this.animateTo(1.0F);
   }

   public void transitionToStart() {
      this.animateTo(0.0F);
   }

   public void transitionToState(int var1) {
      if (!this.isAttachedToWindow()) {
         if (this.mStateCache == null) {
            this.mStateCache = new MotionLayout.StateCache();
         }

         this.mStateCache.setEndState(var1);
      } else {
         this.transitionToState(var1, -1, -1);
      }
   }

   public void transitionToState(int var1, int var2, int var3) {
      MotionScene var4 = this.mScene;
      int var5 = var1;
      if (var4 != null) {
         var5 = var1;
         if (var4.mStateSet != null) {
            var2 = this.mScene.mStateSet.convertToConstraintSet(this.mCurrentState, var1, (float)var2, (float)var3);
            var5 = var1;
            if (var2 != -1) {
               var5 = var2;
            }
         }
      }

      var1 = this.mCurrentState;
      if (var1 != var5) {
         if (this.mBeginState == var5) {
            this.animateTo(0.0F);
         } else if (this.mEndState == var5) {
            this.animateTo(1.0F);
         } else {
            this.mEndState = var5;
            if (var1 != -1) {
               this.setTransition(var1, var5);
               this.animateTo(1.0F);
               this.mTransitionLastPosition = 0.0F;
               this.transitionToEnd();
            } else {
               byte var13 = 0;
               this.mTemporalInterpolator = false;
               this.mTransitionGoalPosition = 1.0F;
               this.mTransitionPosition = 0.0F;
               this.mTransitionLastPosition = 0.0F;
               this.mTransitionLastTime = this.getNanoTime();
               this.mAnimationStartTime = this.getNanoTime();
               this.mTransitionInstantly = false;
               this.mInterpolator = null;
               this.mTransitionDuration = (float)this.mScene.getDuration() / 1000.0F;
               this.mBeginState = -1;
               this.mScene.setTransition(-1, this.mEndState);
               this.mScene.getStartId();
               int var6 = this.getChildCount();
               this.mFrameArrayList.clear();

               for(var1 = 0; var1 < var6; ++var1) {
                  View var14 = this.getChildAt(var1);
                  MotionController var7 = new MotionController(var14);
                  this.mFrameArrayList.put(var14, var7);
               }

               this.mInTransition = true;
               this.mModel.initFrom(this.mLayoutWidget, (ConstraintSet)null, this.mScene.getConstraintSet(var5));
               this.rebuildScene();
               this.mModel.build();
               this.computeCurrentPositions();
               var5 = this.getWidth();
               var2 = this.getHeight();

               MotionController var15;
               for(var1 = 0; var1 < var6; ++var1) {
                  var15 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var1));
                  this.mScene.getKeyFrames(var15);
                  var15.setup(var5, var2, this.mTransitionDuration, this.getNanoTime());
               }

               float var8 = this.mScene.getStaggered();
               if (var8 != 0.0F) {
                  float var9 = Float.MAX_VALUE;
                  float var10 = -3.4028235E38F;
                  var2 = 0;

                  while(true) {
                     var1 = var13;
                     float var11;
                     if (var2 >= var6) {
                        while(var1 < var6) {
                           var15 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var1));
                           float var12 = var15.getFinalX();
                           var11 = var15.getFinalY();
                           var15.mStaggerScale = 1.0F / (1.0F - var8);
                           var15.mStaggerOffset = var8 - (var12 + var11 - var9) * var8 / (var10 - var9);
                           ++var1;
                        }
                        break;
                     }

                     var15 = (MotionController)this.mFrameArrayList.get(this.getChildAt(var2));
                     var11 = var15.getFinalX();
                     var11 += var15.getFinalY();
                     var9 = Math.min(var9, var11);
                     var10 = Math.max(var10, var11);
                     ++var2;
                  }
               }

               this.mTransitionPosition = 0.0F;
               this.mTransitionLastPosition = 0.0F;
               this.mInTransition = true;
               this.invalidate();
            }
         }
      }
   }

   public void updateState() {
      this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
      this.rebuildScene();
   }

   public void updateState(int var1, ConstraintSet var2) {
      MotionScene var3 = this.mScene;
      if (var3 != null) {
         var3.setConstraintSet(var1, var2);
      }

      this.updateState();
      if (this.mCurrentState == var1) {
         var2.applyTo(this);
      }

   }

   class DecelerateInterpolator extends MotionInterpolator {
      float currentP = 0.0F;
      float initalV = 0.0F;
      float maxA;

      public void config(float var1, float var2, float var3) {
         this.initalV = var1;
         this.currentP = var2;
         this.maxA = var3;
      }

      public float getInterpolation(float var1) {
         float var2 = this.initalV;
         float var3;
         float var4;
         if (var2 > 0.0F) {
            var3 = this.maxA;
            var4 = var1;
            if (var2 / var3 < var1) {
               var4 = var2 / var3;
            }

            MotionLayout.this.mLastVelocity = this.initalV - this.maxA * var4;
            var4 = this.initalV * var4 - this.maxA * var4 * var4 / 2.0F;
            var1 = this.currentP;
         } else {
            float var5 = -var2;
            var3 = this.maxA;
            var4 = var1;
            if (var5 / var3 < var1) {
               var4 = -var2 / var3;
            }

            MotionLayout.this.mLastVelocity = this.initalV + this.maxA * var4;
            var4 = this.initalV * var4 + this.maxA * var4 * var4 / 2.0F;
            var1 = this.currentP;
         }

         return var4 + var1;
      }

      public float getVelocity() {
         return MotionLayout.this.mLastVelocity;
      }
   }

   private class DevModeDraw {
      private static final int DEBUG_PATH_TICKS_PER_MS = 16;
      final int DIAMOND_SIZE = 10;
      final int GRAPH_COLOR = -13391360;
      final int KEYFRAME_COLOR = -2067046;
      final int RED_COLOR = -21965;
      final int SHADOW_COLOR = 1996488704;
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
         Paint var2 = new Paint();
         this.mPaint = var2;
         var2.setAntiAlias(true);
         this.mPaint.setColor(-21965);
         this.mPaint.setStrokeWidth(2.0F);
         this.mPaint.setStyle(Style.STROKE);
         var2 = new Paint();
         this.mPaintKeyframes = var2;
         var2.setAntiAlias(true);
         this.mPaintKeyframes.setColor(-2067046);
         this.mPaintKeyframes.setStrokeWidth(2.0F);
         this.mPaintKeyframes.setStyle(Style.STROKE);
         var2 = new Paint();
         this.mPaintGraph = var2;
         var2.setAntiAlias(true);
         this.mPaintGraph.setColor(-13391360);
         this.mPaintGraph.setStrokeWidth(2.0F);
         this.mPaintGraph.setStyle(Style.STROKE);
         var2 = new Paint();
         this.mTextPaint = var2;
         var2.setAntiAlias(true);
         this.mTextPaint.setColor(-13391360);
         this.mTextPaint.setTextSize(MotionLayout.this.getContext().getResources().getDisplayMetrics().density * 12.0F);
         this.mRectangle = new float[8];
         Paint var3 = new Paint();
         this.mFillPaint = var3;
         var3.setAntiAlias(true);
         DashPathEffect var4 = new DashPathEffect(new float[]{4.0F, 8.0F}, 0.0F);
         this.mDashPathEffect = var4;
         this.mPaintGraph.setPathEffect(var4);
         this.mKeyFramePoints = new float[100];
         this.mPathMode = new int[50];
         if (this.mPresentationMode) {
            this.mPaint.setStrokeWidth(8.0F);
            this.mFillPaint.setStrokeWidth(8.0F);
            this.mPaintKeyframes.setStrokeWidth(8.0F);
            this.mShadowTranslate = 4;
         }

      }

      private void drawBasicPath(Canvas var1) {
         var1.drawLines(this.mPoints, this.mPaint);
      }

      private void drawPathAsConfigured(Canvas var1) {
         int var2 = 0;
         boolean var3 = false;

         boolean var4;
         for(var4 = false; var2 < this.mKeyFrameCount; ++var2) {
            if (this.mPathMode[var2] == 1) {
               var3 = true;
            }

            if (this.mPathMode[var2] == 2) {
               var4 = true;
            }
         }

         if (var3) {
            this.drawPathRelative(var1);
         }

         if (var4) {
            this.drawPathCartesian(var1);
         }

      }

      private void drawPathCartesian(Canvas var1) {
         float[] var2 = this.mPoints;
         float var3 = var2[0];
         float var4 = var2[1];
         float var5 = var2[var2.length - 2];
         float var6 = var2[var2.length - 1];
         var1.drawLine(Math.min(var3, var5), Math.max(var4, var6), Math.max(var3, var5), Math.max(var4, var6), this.mPaintGraph);
         var1.drawLine(Math.min(var3, var5), Math.min(var4, var6), Math.min(var3, var5), Math.max(var4, var6), this.mPaintGraph);
      }

      private void drawPathCartesianTicks(Canvas var1, float var2, float var3) {
         float[] var4 = this.mPoints;
         float var5 = var4[0];
         float var6 = var4[1];
         float var7 = var4[var4.length - 2];
         float var8 = var4[var4.length - 1];
         float var9 = Math.min(var5, var7);
         float var10 = Math.max(var6, var8);
         float var11 = var2 - Math.min(var5, var7);
         float var12 = Math.max(var6, var8) - var3;
         StringBuilder var13 = new StringBuilder();
         var13.append("");
         var13.append((float)((int)((double)(var11 * 100.0F / Math.abs(var7 - var5)) + 0.5D)) / 100.0F);
         String var14 = var13.toString();
         this.getTextBounds(var14, this.mTextPaint);
         var1.drawText(var14, var11 / 2.0F - (float)(this.mBounds.width() / 2) + var9, var3 - 20.0F, this.mTextPaint);
         var1.drawLine(var2, var3, Math.min(var5, var7), var3, this.mPaintGraph);
         var13 = new StringBuilder();
         var13.append("");
         var13.append((float)((int)((double)(var12 * 100.0F / Math.abs(var8 - var6)) + 0.5D)) / 100.0F);
         var14 = var13.toString();
         this.getTextBounds(var14, this.mTextPaint);
         var1.drawText(var14, var2 + 5.0F, var10 - (var12 / 2.0F - (float)(this.mBounds.height() / 2)), this.mTextPaint);
         var1.drawLine(var2, var3, var2, Math.max(var6, var8), this.mPaintGraph);
      }

      private void drawPathRelative(Canvas var1) {
         float[] var2 = this.mPoints;
         var1.drawLine(var2[0], var2[1], var2[var2.length - 2], var2[var2.length - 1], this.mPaintGraph);
      }

      private void drawPathRelativeTicks(Canvas var1, float var2, float var3) {
         float[] var4 = this.mPoints;
         float var5 = var4[0];
         float var6 = var4[1];
         float var7 = var4[var4.length - 2];
         float var8 = var4[var4.length - 1];
         float var9 = (float)Math.hypot((double)(var5 - var7), (double)(var6 - var8));
         var7 -= var5;
         var8 -= var6;
         float var10 = ((var2 - var5) * var7 + (var3 - var6) * var8) / (var9 * var9);
         var5 += var7 * var10;
         var7 = var6 + var10 * var8;
         Path var13 = new Path();
         var13.moveTo(var2, var3);
         var13.lineTo(var5, var7);
         var6 = (float)Math.hypot((double)(var5 - var2), (double)(var7 - var3));
         StringBuilder var11 = new StringBuilder();
         var11.append("");
         var11.append((float)((int)(var6 * 100.0F / var9)) / 100.0F);
         String var12 = var11.toString();
         this.getTextBounds(var12, this.mTextPaint);
         var1.drawTextOnPath(var12, var13, var6 / 2.0F - (float)(this.mBounds.width() / 2), -20.0F, this.mTextPaint);
         var1.drawLine(var2, var3, var5, var7, this.mPaintGraph);
      }

      private void drawPathScreenTicks(Canvas var1, float var2, float var3, int var4, int var5) {
         StringBuilder var6 = new StringBuilder();
         var6.append("");
         var6.append((float)((int)((double)((var2 - (float)(var4 / 2)) * 100.0F / (float)(MotionLayout.this.getWidth() - var4)) + 0.5D)) / 100.0F);
         String var7 = var6.toString();
         this.getTextBounds(var7, this.mTextPaint);
         var1.drawText(var7, var2 / 2.0F - (float)(this.mBounds.width() / 2) + 0.0F, var3 - 20.0F, this.mTextPaint);
         var1.drawLine(var2, var3, Math.min(0.0F, 1.0F), var3, this.mPaintGraph);
         var6 = new StringBuilder();
         var6.append("");
         var6.append((float)((int)((double)((var3 - (float)(var5 / 2)) * 100.0F / (float)(MotionLayout.this.getHeight() - var5)) + 0.5D)) / 100.0F);
         var7 = var6.toString();
         this.getTextBounds(var7, this.mTextPaint);
         var1.drawText(var7, var2 + 5.0F, 0.0F - (var3 / 2.0F - (float)(this.mBounds.height() / 2)), this.mTextPaint);
         var1.drawLine(var2, var3, var2, Math.max(0.0F, 1.0F), this.mPaintGraph);
      }

      private void drawRectangle(Canvas var1, MotionController var2) {
         this.mPath.reset();

         for(int var3 = 0; var3 <= 50; ++var3) {
            var2.buildRect((float)var3 / (float)50, this.mRectangle, 0);
            Path var4 = this.mPath;
            float[] var5 = this.mRectangle;
            var4.moveTo(var5[0], var5[1]);
            var4 = this.mPath;
            var5 = this.mRectangle;
            var4.lineTo(var5[2], var5[3]);
            Path var7 = this.mPath;
            float[] var6 = this.mRectangle;
            var7.lineTo(var6[4], var6[5]);
            var7 = this.mPath;
            var6 = this.mRectangle;
            var7.lineTo(var6[6], var6[7]);
            this.mPath.close();
         }

         this.mPaint.setColor(1140850688);
         var1.translate(2.0F, 2.0F);
         var1.drawPath(this.mPath, this.mPaint);
         var1.translate(-2.0F, -2.0F);
         this.mPaint.setColor(-65536);
         var1.drawPath(this.mPath, this.mPaint);
      }

      private void drawTicks(Canvas var1, int var2, int var3, MotionController var4) {
         int var5;
         int var6;
         if (var4.mView != null) {
            var5 = var4.mView.getWidth();
            var6 = var4.mView.getHeight();
         } else {
            var5 = 0;
            var6 = 0;
         }

         for(int var7 = 1; var7 < var3 - 1; ++var7) {
            if (var2 != 4 || this.mPathMode[var7 - 1] != 0) {
               float[] var8 = this.mKeyFramePoints;
               int var9 = var7 * 2;
               float var10 = var8[var9];
               float var11 = var8[var9 + 1];
               this.mPath.reset();
               this.mPath.moveTo(var10, var11 + 10.0F);
               this.mPath.lineTo(var10 + 10.0F, var11);
               this.mPath.lineTo(var10, var11 - 10.0F);
               this.mPath.lineTo(var10 - 10.0F, var11);
               this.mPath.close();
               var9 = var7 - 1;
               var4.getKeyFrame(var9);
               if (var2 == 4) {
                  int[] var13 = this.mPathMode;
                  if (var13[var9] == 1) {
                     this.drawPathRelativeTicks(var1, var10 - 0.0F, var11 - 0.0F);
                  } else if (var13[var9] == 2) {
                     this.drawPathCartesianTicks(var1, var10 - 0.0F, var11 - 0.0F);
                  } else if (var13[var9] == 3) {
                     this.drawPathScreenTicks(var1, var10 - 0.0F, var11 - 0.0F, var5, var6);
                  }

                  var1.drawPath(this.mPath, this.mFillPaint);
               }

               if (var2 == 2) {
                  this.drawPathRelativeTicks(var1, var10 - 0.0F, var11 - 0.0F);
               }

               if (var2 == 3) {
                  this.drawPathCartesianTicks(var1, var10 - 0.0F, var11 - 0.0F);
               }

               if (var2 == 6) {
                  this.drawPathScreenTicks(var1, var10 - 0.0F, var11 - 0.0F, var5, var6);
               }

               var1.drawPath(this.mPath, this.mFillPaint);
            }
         }

         float[] var12 = this.mPoints;
         if (var12.length > 1) {
            var1.drawCircle(var12[0], var12[1], 8.0F, this.mPaintKeyframes);
            var12 = this.mPoints;
            var1.drawCircle(var12[var12.length - 2], var12[var12.length - 1], 8.0F, this.mPaintKeyframes);
         }

      }

      private void drawTranslation(Canvas var1, float var2, float var3, float var4, float var5) {
         var1.drawRect(var2, var3, var4, var5, this.mPaintGraph);
         var1.drawLine(var2, var3, var4, var5, this.mPaintGraph);
      }

      public void draw(Canvas var1, HashMap<View, MotionController> var2, int var3, int var4) {
         if (var2 != null && var2.size() != 0) {
            var1.save();
            if (!MotionLayout.this.isInEditMode() && (var4 & 1) == 2) {
               StringBuilder var5 = new StringBuilder();
               var5.append(MotionLayout.this.getContext().getResources().getResourceName(MotionLayout.this.mEndState));
               var5.append(":");
               var5.append(MotionLayout.this.getProgress());
               String var11 = var5.toString();
               var1.drawText(var11, 10.0F, (float)(MotionLayout.this.getHeight() - 30), this.mTextPaint);
               var1.drawText(var11, 11.0F, (float)(MotionLayout.this.getHeight() - 29), this.mPaint);
            }

            Iterator var12 = var2.values().iterator();

            while(true) {
               MotionController var6;
               int var7;
               int var8;
               do {
                  do {
                     if (!var12.hasNext()) {
                        var1.restore();
                        return;
                     }

                     var6 = (MotionController)var12.next();
                     var7 = var6.getDrawPath();
                     var8 = var7;
                     if (var4 > 0) {
                        var8 = var7;
                        if (var7 == 0) {
                           var8 = 1;
                        }
                     }
                  } while(var8 == 0);

                  this.mKeyFrameCount = var6.buildKeyFrames(this.mKeyFramePoints, this.mPathMode);
               } while(var8 < 1);

               var7 = var3 / 16;
               float[] var10 = this.mPoints;
               if (var10 == null || var10.length != var7 * 2) {
                  this.mPoints = new float[var7 * 2];
                  this.mPath = new Path();
               }

               int var9 = this.mShadowTranslate;
               var1.translate((float)var9, (float)var9);
               this.mPaint.setColor(1996488704);
               this.mFillPaint.setColor(1996488704);
               this.mPaintKeyframes.setColor(1996488704);
               this.mPaintGraph.setColor(1996488704);
               var6.buildPath(this.mPoints, var7);
               this.drawAll(var1, var8, this.mKeyFrameCount, var6);
               this.mPaint.setColor(-21965);
               this.mPaintKeyframes.setColor(-2067046);
               this.mFillPaint.setColor(-2067046);
               this.mPaintGraph.setColor(-13391360);
               var7 = this.mShadowTranslate;
               var1.translate((float)(-var7), (float)(-var7));
               this.drawAll(var1, var8, this.mKeyFrameCount, var6);
               if (var8 == 5) {
                  this.drawRectangle(var1, var6);
               }
            }
         }
      }

      public void drawAll(Canvas var1, int var2, int var3, MotionController var4) {
         if (var2 == 4) {
            this.drawPathAsConfigured(var1);
         }

         if (var2 == 2) {
            this.drawPathRelative(var1);
         }

         if (var2 == 3) {
            this.drawPathCartesian(var1);
         }

         this.drawBasicPath(var1);
         this.drawTicks(var1, var2, var3, var4);
      }

      void getTextBounds(String var1, Paint var2) {
         var2.getTextBounds(var1, 0, var1.length(), this.mBounds);
      }
   }

   class Model {
      ConstraintSet mEnd = null;
      int mEndId;
      ConstraintWidgetContainer mLayoutEnd = new ConstraintWidgetContainer();
      ConstraintWidgetContainer mLayoutStart = new ConstraintWidgetContainer();
      ConstraintSet mStart = null;
      int mStartId;

      private void debugLayout(String var1, ConstraintWidgetContainer var2) {
         View var3 = (View)var2.getCompanionWidget();
         StringBuilder var4 = new StringBuilder();
         var4.append(var1);
         var4.append(" ");
         var4.append(Debug.getName(var3));
         String var13 = var4.toString();
         StringBuilder var11 = new StringBuilder();
         var11.append(var13);
         var11.append("  ========= ");
         var11.append(var2);
         Log.v("MotionLayout", var11.toString());
         int var5 = var2.getChildren().size();

         for(int var6 = 0; var6 < var5; ++var6) {
            var11 = new StringBuilder();
            var11.append(var13);
            var11.append("[");
            var11.append(var6);
            var11.append("] ");
            String var7 = var11.toString();
            ConstraintWidget var8 = (ConstraintWidget)var2.getChildren().get(var6);
            StringBuilder var9 = new StringBuilder();
            var9.append("");
            ConstraintAnchor var12 = var8.mTop.mTarget;
            String var14 = "_";
            if (var12 != null) {
               var1 = "T";
            } else {
               var1 = "_";
            }

            var9.append(var1);
            var1 = var9.toString();
            var9 = new StringBuilder();
            var9.append(var1);
            if (var8.mBottom.mTarget != null) {
               var1 = "B";
            } else {
               var1 = "_";
            }

            var9.append(var1);
            var1 = var9.toString();
            var9 = new StringBuilder();
            var9.append(var1);
            if (var8.mLeft.mTarget != null) {
               var1 = "L";
            } else {
               var1 = "_";
            }

            var9.append(var1);
            var1 = var9.toString();
            var9 = new StringBuilder();
            var9.append(var1);
            var1 = var14;
            if (var8.mRight.mTarget != null) {
               var1 = "R";
            }

            var9.append(var1);
            String var15 = var9.toString();
            View var10 = (View)var8.getCompanionWidget();
            var14 = Debug.getName(var10);
            var1 = var14;
            if (var10 instanceof TextView) {
               var11 = new StringBuilder();
               var11.append(var14);
               var11.append("(");
               var11.append(((TextView)var10).getText());
               var11.append(")");
               var1 = var11.toString();
            }

            var4 = new StringBuilder();
            var4.append(var7);
            var4.append("  ");
            var4.append(var1);
            var4.append(" ");
            var4.append(var8);
            var4.append(" ");
            var4.append(var15);
            Log.v("MotionLayout", var4.toString());
         }

         var11 = new StringBuilder();
         var11.append(var13);
         var11.append(" done. ");
         Log.v("MotionLayout", var11.toString());
      }

      private void debugLayoutParam(String var1, ConstraintLayout.LayoutParams var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append(" ");
         String var4;
         if (var2.startToStart != -1) {
            var4 = "SS";
         } else {
            var4 = "__";
         }

         var3.append(var4);
         var4 = var3.toString();
         StringBuilder var5 = new StringBuilder();
         var5.append(var4);
         int var6 = var2.startToEnd;
         String var8 = "|__";
         if (var6 != -1) {
            var4 = "|SE";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.endToStart != -1) {
            var4 = "|ES";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.endToEnd != -1) {
            var4 = "|EE";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.leftToLeft != -1) {
            var4 = "|LL";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.leftToRight != -1) {
            var4 = "|LR";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.rightToLeft != -1) {
            var4 = "|RL";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.rightToRight != -1) {
            var4 = "|RR";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.topToTop != -1) {
            var4 = "|TT";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.topToBottom != -1) {
            var4 = "|TB";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         if (var2.bottomToTop != -1) {
            var4 = "|BT";
         } else {
            var4 = "|__";
         }

         var5.append(var4);
         var4 = var5.toString();
         var5 = new StringBuilder();
         var5.append(var4);
         var4 = var8;
         if (var2.bottomToBottom != -1) {
            var4 = "|BB";
         }

         var5.append(var4);
         String var7 = var5.toString();
         StringBuilder var9 = new StringBuilder();
         var9.append(var1);
         var9.append(var7);
         Log.v("MotionLayout", var9.toString());
      }

      private void debugWidget(String var1, ConstraintWidget var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append(" ");
         ConstraintAnchor var4 = var2.mTop.mTarget;
         String var5 = "B";
         String var6 = "__";
         StringBuilder var7;
         String var8;
         if (var4 != null) {
            var7 = new StringBuilder();
            var7.append("T");
            if (var2.mTop.mTarget.mType == ConstraintAnchor.Type.TOP) {
               var8 = "T";
            } else {
               var8 = "B";
            }

            var7.append(var8);
            var8 = var7.toString();
         } else {
            var8 = "__";
         }

         var3.append(var8);
         var8 = var3.toString();
         var3 = new StringBuilder();
         var3.append(var8);
         if (var2.mBottom.mTarget != null) {
            var7 = new StringBuilder();
            var7.append("B");
            var8 = var5;
            if (var2.mBottom.mTarget.mType == ConstraintAnchor.Type.TOP) {
               var8 = "T";
            }

            var7.append(var8);
            var8 = var7.toString();
         } else {
            var8 = "__";
         }

         var3.append(var8);
         var8 = var3.toString();
         var3 = new StringBuilder();
         var3.append(var8);
         var4 = var2.mLeft.mTarget;
         var5 = "R";
         if (var4 != null) {
            var7 = new StringBuilder();
            var7.append("L");
            if (var2.mLeft.mTarget.mType == ConstraintAnchor.Type.LEFT) {
               var8 = "L";
            } else {
               var8 = "R";
            }

            var7.append(var8);
            var8 = var7.toString();
         } else {
            var8 = "__";
         }

         var3.append(var8);
         var8 = var3.toString();
         var3 = new StringBuilder();
         var3.append(var8);
         var8 = var6;
         StringBuilder var9;
         if (var2.mRight.mTarget != null) {
            var9 = new StringBuilder();
            var9.append("R");
            var8 = var5;
            if (var2.mRight.mTarget.mType == ConstraintAnchor.Type.LEFT) {
               var8 = "L";
            }

            var9.append(var8);
            var8 = var9.toString();
         }

         var3.append(var8);
         var8 = var3.toString();
         var9 = new StringBuilder();
         var9.append(var1);
         var9.append(var8);
         var9.append(" ---  ");
         var9.append(var2);
         Log.v("MotionLayout", var9.toString());
      }

      private void setupConstraintWidget(ConstraintWidgetContainer var1, ConstraintSet var2) {
         SparseArray var3 = new SparseArray();
         Constraints.LayoutParams var4 = new Constraints.LayoutParams(-2, -2);
         var3.clear();
         var3.put(0, var1);
         var3.put(MotionLayout.this.getId(), var1);
         Iterator var5 = var1.getChildren().iterator();

         while(var5.hasNext()) {
            ConstraintWidget var6 = (ConstraintWidget)var5.next();
            var3.put(((View)var6.getCompanionWidget()).getId(), var6);
         }

         Iterator var13 = var1.getChildren().iterator();

         while(var13.hasNext()) {
            ConstraintWidget var7 = (ConstraintWidget)var13.next();
            View var10 = (View)var7.getCompanionWidget();
            var2.applyToLayoutParams(var10.getId(), var4);
            var7.setWidth(var2.getWidth(var10.getId()));
            var7.setHeight(var2.getHeight(var10.getId()));
            if (var10 instanceof ConstraintHelper) {
               var2.applyToHelper((ConstraintHelper)var10, var7, var4, var3);
               if (var10 instanceof Barrier) {
                  ((Barrier)var10).validateParams();
               }
            }

            if (android.os.Build.VERSION.SDK_INT >= 17) {
               var4.resolveLayoutDirection(MotionLayout.this.getLayoutDirection());
            } else {
               var4.resolveLayoutDirection(0);
            }

            MotionLayout.this.applyConstraintsFromLayoutParams(false, var10, var7, var4, var3);
            if (var2.getVisibilityMode(var10.getId()) == 1) {
               var7.setVisibility(var10.getVisibility());
            } else {
               var7.setVisibility(var2.getVisibility(var10.getId()));
            }
         }

         Iterator var8 = var1.getChildren().iterator();

         while(var8.hasNext()) {
            ConstraintWidget var11 = (ConstraintWidget)var8.next();
            if (var11 instanceof VirtualLayout) {
               ConstraintHelper var9 = (ConstraintHelper)var11.getCompanionWidget();
               Helper var12 = (Helper)var11;
               var9.updatePreLayout(var1, var12, var3);
               ((VirtualLayout)var12).captureWidgets();
            }
         }

      }

      public void build() {
         int var1 = MotionLayout.this.getChildCount();
         MotionLayout.this.mFrameArrayList.clear();
         byte var2 = 0;
         int var3 = 0;

         while(true) {
            int var4 = var2;
            View var5;
            MotionController var6;
            if (var3 >= var1) {
               for(; var4 < var1; ++var4) {
                  var5 = MotionLayout.this.getChildAt(var4);
                  var6 = (MotionController)MotionLayout.this.mFrameArrayList.get(var5);
                  if (var6 != null) {
                     ConstraintWidget var7;
                     if (this.mStart != null) {
                        var7 = this.getWidget(this.mLayoutStart, var5);
                        if (var7 != null) {
                           var6.setStartState(var7, this.mStart);
                        } else if (MotionLayout.this.mDebugPath != 0) {
                           StringBuilder var9 = new StringBuilder();
                           var9.append(Debug.getLocation());
                           var9.append("no widget for  ");
                           var9.append(Debug.getName(var5));
                           var9.append(" (");
                           var9.append(var5.getClass().getName());
                           var9.append(")");
                           Log.e("MotionLayout", var9.toString());
                        }
                     }

                     if (this.mEnd != null) {
                        var7 = this.getWidget(this.mLayoutEnd, var5);
                        if (var7 != null) {
                           var6.setEndState(var7, this.mEnd);
                        } else if (MotionLayout.this.mDebugPath != 0) {
                           StringBuilder var8 = new StringBuilder();
                           var8.append(Debug.getLocation());
                           var8.append("no widget for  ");
                           var8.append(Debug.getName(var5));
                           var8.append(" (");
                           var8.append(var5.getClass().getName());
                           var8.append(")");
                           Log.e("MotionLayout", var8.toString());
                        }
                     }
                  }
               }

               return;
            }

            var5 = MotionLayout.this.getChildAt(var3);
            var6 = new MotionController(var5);
            MotionLayout.this.mFrameArrayList.put(var5, var6);
            ++var3;
         }
      }

      void copy(ConstraintWidgetContainer var1, ConstraintWidgetContainer var2) {
         ArrayList var3 = var1.getChildren();
         HashMap var4 = new HashMap();
         var4.put(var1, var2);
         var2.getChildren().clear();
         var2.copy(var1, var4);
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            ConstraintWidget var6 = (ConstraintWidget)var5.next();
            Object var7;
            if (var6 instanceof androidx.constraintlayout.solver.widgets.Barrier) {
               var7 = new androidx.constraintlayout.solver.widgets.Barrier();
            } else if (var6 instanceof Guideline) {
               var7 = new Guideline();
            } else if (var6 instanceof Flow) {
               var7 = new Flow();
            } else if (var6 instanceof Helper) {
               var7 = new HelperWidget();
            } else {
               var7 = new ConstraintWidget();
            }

            var2.add((ConstraintWidget)var7);
            var4.put(var6, var7);
         }

         Iterator var8 = var3.iterator();

         while(var8.hasNext()) {
            ConstraintWidget var9 = (ConstraintWidget)var8.next();
            ((ConstraintWidget)var4.get(var9)).copy(var9, var4);
         }

      }

      ConstraintWidget getWidget(ConstraintWidgetContainer var1, View var2) {
         if (var1.getCompanionWidget() == var2) {
            return var1;
         } else {
            ArrayList var3 = var1.getChildren();
            int var4 = var3.size();

            for(int var5 = 0; var5 < var4; ++var5) {
               ConstraintWidget var6 = (ConstraintWidget)var3.get(var5);
               if (var6.getCompanionWidget() == var2) {
                  return var6;
               }
            }

            return null;
         }
      }

      void initFrom(ConstraintWidgetContainer var1, ConstraintSet var2, ConstraintSet var3) {
         this.mStart = var2;
         this.mEnd = var3;
         this.mLayoutStart = new ConstraintWidgetContainer();
         this.mLayoutEnd = new ConstraintWidgetContainer();
         this.mLayoutStart.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
         this.mLayoutEnd.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
         this.mLayoutStart.removeAllChildren();
         this.mLayoutEnd.removeAllChildren();
         this.copy(MotionLayout.this.mLayoutWidget, this.mLayoutStart);
         this.copy(MotionLayout.this.mLayoutWidget, this.mLayoutEnd);
         if ((double)MotionLayout.this.mTransitionLastPosition > 0.5D) {
            if (var2 != null) {
               this.setupConstraintWidget(this.mLayoutStart, var2);
            }

            this.setupConstraintWidget(this.mLayoutEnd, var3);
         } else {
            this.setupConstraintWidget(this.mLayoutEnd, var3);
            if (var2 != null) {
               this.setupConstraintWidget(this.mLayoutStart, var2);
            }
         }

         this.mLayoutStart.setRtl(MotionLayout.this.isRtl());
         this.mLayoutStart.updateHierarchy();
         this.mLayoutEnd.setRtl(MotionLayout.this.isRtl());
         this.mLayoutEnd.updateHierarchy();
         android.view.ViewGroup.LayoutParams var4 = MotionLayout.this.getLayoutParams();
         if (var4 != null) {
            if (var4.width == -2) {
               this.mLayoutStart.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
               this.mLayoutEnd.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }

            if (var4.height == -2) {
               this.mLayoutStart.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
               this.mLayoutEnd.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            }
         }

      }

      public boolean isNotConfiguredWith(int var1, int var2) {
         boolean var3;
         if (var1 == this.mStartId && var2 == this.mEndId) {
            var3 = false;
         } else {
            var3 = true;
         }

         return var3;
      }

      public void measure(int var1, int var2) {
         int var3 = MeasureSpec.getMode(var1);
         int var4 = MeasureSpec.getMode(var2);
         MotionLayout.this.mWidthMeasureMode = var3;
         MotionLayout.this.mHeightMeasureMode = var4;
         int var5 = MotionLayout.this.getOptimizationLevel();
         if (MotionLayout.this.mCurrentState == MotionLayout.this.getStartState()) {
            MotionLayout.this.resolveSystem(this.mLayoutEnd, var5, var1, var2);
            if (this.mStart != null) {
               MotionLayout.this.resolveSystem(this.mLayoutStart, var5, var1, var2);
            }
         } else {
            if (this.mStart != null) {
               MotionLayout.this.resolveSystem(this.mLayoutStart, var5, var1, var2);
            }

            MotionLayout.this.resolveSystem(this.mLayoutEnd, var5, var1, var2);
         }

         boolean var6;
         if (MotionLayout.this.getParent() instanceof MotionLayout && var3 == 1073741824 && var4 == 1073741824) {
            var6 = false;
         } else {
            var6 = true;
         }

         boolean var8;
         if (var6) {
            MotionLayout.this.mWidthMeasureMode = var3;
            MotionLayout.this.mHeightMeasureMode = var4;
            if (MotionLayout.this.mCurrentState == MotionLayout.this.getStartState()) {
               MotionLayout.this.resolveSystem(this.mLayoutEnd, var5, var1, var2);
               if (this.mStart != null) {
                  MotionLayout.this.resolveSystem(this.mLayoutStart, var5, var1, var2);
               }
            } else {
               if (this.mStart != null) {
                  MotionLayout.this.resolveSystem(this.mLayoutStart, var5, var1, var2);
               }

               MotionLayout.this.resolveSystem(this.mLayoutEnd, var5, var1, var2);
            }

            MotionLayout.this.mStartWrapWidth = this.mLayoutStart.getWidth();
            MotionLayout.this.mStartWrapHeight = this.mLayoutStart.getHeight();
            MotionLayout.this.mEndWrapWidth = this.mLayoutEnd.getWidth();
            MotionLayout.this.mEndWrapHeight = this.mLayoutEnd.getHeight();
            MotionLayout var7 = MotionLayout.this;
            if (var7.mStartWrapWidth == MotionLayout.this.mEndWrapWidth && MotionLayout.this.mStartWrapHeight == MotionLayout.this.mEndWrapHeight) {
               var8 = false;
            } else {
               var8 = true;
            }

            var7.mMeasureDuringTransition = var8;
         }

         int var10 = MotionLayout.this.mStartWrapWidth;
         var4 = MotionLayout.this.mStartWrapHeight;
         if (MotionLayout.this.mWidthMeasureMode == Integer.MIN_VALUE || MotionLayout.this.mWidthMeasureMode == 0) {
            var10 = (int)((float)MotionLayout.this.mStartWrapWidth + MotionLayout.this.mPostInterpolationPosition * (float)(MotionLayout.this.mEndWrapWidth - MotionLayout.this.mStartWrapWidth));
         }

         if (MotionLayout.this.mHeightMeasureMode == Integer.MIN_VALUE || MotionLayout.this.mHeightMeasureMode == 0) {
            var4 = (int)((float)MotionLayout.this.mStartWrapHeight + MotionLayout.this.mPostInterpolationPosition * (float)(MotionLayout.this.mEndWrapHeight - MotionLayout.this.mStartWrapHeight));
         }

         if (!this.mLayoutStart.isWidthMeasuredTooSmall() && !this.mLayoutEnd.isWidthMeasuredTooSmall()) {
            var8 = false;
         } else {
            var8 = true;
         }

         boolean var9;
         if (!this.mLayoutStart.isHeightMeasuredTooSmall() && !this.mLayoutEnd.isHeightMeasuredTooSmall()) {
            var9 = false;
         } else {
            var9 = true;
         }

         MotionLayout.this.resolveMeasuredDimension(var1, var2, var10, var4, var8, var9);
      }

      public void reEvaluateState() {
         this.measure(MotionLayout.this.mLastWidthMeasureSpec, MotionLayout.this.mLastHeightMeasureSpec);
         MotionLayout.this.setupMotionViews();
      }

      public void setMeasuredId(int var1, int var2) {
         this.mStartId = var1;
         this.mEndId = var2;
      }
   }

   protected interface MotionTracker {
      void addMovement(MotionEvent var1);

      void clear();

      void computeCurrentVelocity(int var1);

      void computeCurrentVelocity(int var1, float var2);

      float getXVelocity();

      float getXVelocity(int var1);

      float getYVelocity();

      float getYVelocity(int var1);

      void recycle();
   }

   private static class MyTracker implements MotionLayout.MotionTracker {
      private static MotionLayout.MyTracker me = new MotionLayout.MyTracker();
      VelocityTracker tracker;

      public static MotionLayout.MyTracker obtain() {
         me.tracker = VelocityTracker.obtain();
         return me;
      }

      public void addMovement(MotionEvent var1) {
         VelocityTracker var2 = this.tracker;
         if (var2 != null) {
            var2.addMovement(var1);
         }

      }

      public void clear() {
         this.tracker.clear();
      }

      public void computeCurrentVelocity(int var1) {
         this.tracker.computeCurrentVelocity(var1);
      }

      public void computeCurrentVelocity(int var1, float var2) {
         this.tracker.computeCurrentVelocity(var1, var2);
      }

      public float getXVelocity() {
         return this.tracker.getXVelocity();
      }

      public float getXVelocity(int var1) {
         return this.tracker.getXVelocity(var1);
      }

      public float getYVelocity() {
         return this.tracker.getYVelocity();
      }

      public float getYVelocity(int var1) {
         return this.getYVelocity(var1);
      }

      public void recycle() {
         this.tracker.recycle();
         this.tracker = null;
      }
   }

   class StateCache {
      final String KeyEndState = "motion.EndState";
      final String KeyProgress = "motion.progress";
      final String KeyStartState = "motion.StartState";
      final String KeyVelocity = "motion.velocity";
      int endState = -1;
      float mProgress = Float.NaN;
      float mVelocity = Float.NaN;
      int startState = -1;

      void apply() {
         if (this.startState != -1 || this.endState != -1) {
            int var1 = this.startState;
            if (var1 == -1) {
               MotionLayout.this.transitionToState(this.endState);
            } else {
               int var2 = this.endState;
               if (var2 == -1) {
                  MotionLayout.this.setState(var1, -1, -1);
               } else {
                  MotionLayout.this.setTransition(var1, var2);
               }
            }

            MotionLayout.this.setState(MotionLayout.TransitionState.SETUP);
         }

         if (Float.isNaN(this.mVelocity)) {
            if (!Float.isNaN(this.mProgress)) {
               MotionLayout.this.setProgress(this.mProgress);
            }
         } else {
            MotionLayout.this.setProgress(this.mProgress, this.mVelocity);
            this.mProgress = Float.NaN;
            this.mVelocity = Float.NaN;
            this.startState = -1;
            this.endState = -1;
         }
      }

      public Bundle getTransitionState() {
         Bundle var1 = new Bundle();
         var1.putFloat("motion.progress", this.mProgress);
         var1.putFloat("motion.velocity", this.mVelocity);
         var1.putInt("motion.StartState", this.startState);
         var1.putInt("motion.EndState", this.endState);
         return var1;
      }

      public void recordState() {
         this.endState = MotionLayout.this.mEndState;
         this.startState = MotionLayout.this.mBeginState;
         this.mVelocity = MotionLayout.this.getVelocity();
         this.mProgress = MotionLayout.this.getProgress();
      }

      public void setEndState(int var1) {
         this.endState = var1;
      }

      public void setProgress(float var1) {
         this.mProgress = var1;
      }

      public void setStartState(int var1) {
         this.startState = var1;
      }

      public void setTransitionState(Bundle var1) {
         this.mProgress = var1.getFloat("motion.progress");
         this.mVelocity = var1.getFloat("motion.velocity");
         this.startState = var1.getInt("motion.StartState");
         this.endState = var1.getInt("motion.EndState");
      }

      public void setVelocity(float var1) {
         this.mVelocity = var1;
      }
   }

   public interface TransitionListener {
      void onTransitionChange(MotionLayout var1, int var2, int var3, float var4);

      void onTransitionCompleted(MotionLayout var1, int var2);

      void onTransitionStarted(MotionLayout var1, int var2, int var3);

      void onTransitionTrigger(MotionLayout var1, int var2, boolean var3, float var4);
   }

   static enum TransitionState {
      FINISHED,
      MOVING,
      SETUP,
      UNDEFINED;

      static {
         MotionLayout.TransitionState var0 = new MotionLayout.TransitionState("FINISHED", 3);
         FINISHED = var0;
      }
   }
}
