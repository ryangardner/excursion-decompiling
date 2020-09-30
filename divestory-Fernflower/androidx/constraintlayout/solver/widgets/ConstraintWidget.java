package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.analyzer.ChainRun;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConstraintWidget {
   public static final int ANCHOR_BASELINE = 4;
   public static final int ANCHOR_BOTTOM = 3;
   public static final int ANCHOR_LEFT = 0;
   public static final int ANCHOR_RIGHT = 1;
   public static final int ANCHOR_TOP = 2;
   private static final boolean AUTOTAG_CENTER = false;
   public static final int CHAIN_PACKED = 2;
   public static final int CHAIN_SPREAD = 0;
   public static final int CHAIN_SPREAD_INSIDE = 1;
   public static float DEFAULT_BIAS;
   static final int DIMENSION_HORIZONTAL = 0;
   static final int DIMENSION_VERTICAL = 1;
   protected static final int DIRECT = 2;
   public static final int GONE = 8;
   public static final int HORIZONTAL = 0;
   public static final int INVISIBLE = 4;
   public static final int MATCH_CONSTRAINT_PERCENT = 2;
   public static final int MATCH_CONSTRAINT_RATIO = 3;
   public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
   public static final int MATCH_CONSTRAINT_SPREAD = 0;
   public static final int MATCH_CONSTRAINT_WRAP = 1;
   protected static final int SOLVER = 1;
   public static final int UNKNOWN = -1;
   private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
   public static final int VERTICAL = 1;
   public static final int VISIBLE = 0;
   private static final int WRAP = -2;
   private boolean hasBaseline;
   public ChainRun horizontalChainRun;
   public HorizontalWidgetRun horizontalRun;
   private boolean inPlaceholder;
   public boolean[] isTerminalWidget;
   protected ArrayList<ConstraintAnchor> mAnchors;
   ConstraintAnchor mBaseline;
   int mBaselineDistance;
   public ConstraintAnchor mBottom;
   boolean mBottomHasCentered;
   ConstraintAnchor mCenter;
   ConstraintAnchor mCenterX;
   ConstraintAnchor mCenterY;
   private float mCircleConstraintAngle;
   private Object mCompanionWidget;
   private int mContainerItemSkip;
   private String mDebugName;
   public float mDimensionRatio;
   protected int mDimensionRatioSide;
   int mDistToBottom;
   int mDistToLeft;
   int mDistToRight;
   int mDistToTop;
   boolean mGroupsToSolver;
   int mHeight;
   float mHorizontalBiasPercent;
   boolean mHorizontalChainFixedPosition;
   int mHorizontalChainStyle;
   ConstraintWidget mHorizontalNextWidget;
   public int mHorizontalResolution;
   boolean mHorizontalWrapVisited;
   private boolean mInVirtuaLayout;
   public boolean mIsHeightWrapContent;
   private boolean[] mIsInBarrier;
   public boolean mIsWidthWrapContent;
   public ConstraintAnchor mLeft;
   boolean mLeftHasCentered;
   public ConstraintAnchor[] mListAnchors;
   public ConstraintWidget.DimensionBehaviour[] mListDimensionBehaviors;
   protected ConstraintWidget[] mListNextMatchConstraintsWidget;
   public int mMatchConstraintDefaultHeight;
   public int mMatchConstraintDefaultWidth;
   public int mMatchConstraintMaxHeight;
   public int mMatchConstraintMaxWidth;
   public int mMatchConstraintMinHeight;
   public int mMatchConstraintMinWidth;
   public float mMatchConstraintPercentHeight;
   public float mMatchConstraintPercentWidth;
   private int[] mMaxDimension;
   protected int mMinHeight;
   protected int mMinWidth;
   protected ConstraintWidget[] mNextChainWidget;
   protected int mOffsetX;
   protected int mOffsetY;
   boolean mOptimizerMeasurable;
   public ConstraintWidget mParent;
   int mRelX;
   int mRelY;
   float mResolvedDimensionRatio;
   int mResolvedDimensionRatioSide;
   boolean mResolvedHasRatio;
   public int[] mResolvedMatchConstraintDefault;
   public ConstraintAnchor mRight;
   boolean mRightHasCentered;
   public ConstraintAnchor mTop;
   boolean mTopHasCentered;
   private String mType;
   float mVerticalBiasPercent;
   boolean mVerticalChainFixedPosition;
   int mVerticalChainStyle;
   ConstraintWidget mVerticalNextWidget;
   public int mVerticalResolution;
   boolean mVerticalWrapVisited;
   private int mVisibility;
   public float[] mWeight;
   int mWidth;
   protected int mX;
   protected int mY;
   public boolean measured;
   public WidgetRun[] run;
   public ChainRun verticalChainRun;
   public VerticalWidgetRun verticalRun;
   public int[] wrapMeasure;

   public ConstraintWidget() {
      this.measured = false;
      this.run = new WidgetRun[2];
      this.horizontalRun = new HorizontalWidgetRun(this);
      this.verticalRun = new VerticalWidgetRun(this);
      this.isTerminalWidget = new boolean[]{(boolean)1, (boolean)1};
      this.wrapMeasure = new int[]{0, 0, 0, 0};
      this.mResolvedHasRatio = false;
      this.mHorizontalResolution = -1;
      this.mVerticalResolution = -1;
      this.mMatchConstraintDefaultWidth = 0;
      this.mMatchConstraintDefaultHeight = 0;
      this.mResolvedMatchConstraintDefault = new int[2];
      this.mMatchConstraintMinWidth = 0;
      this.mMatchConstraintMaxWidth = 0;
      this.mMatchConstraintPercentWidth = 1.0F;
      this.mMatchConstraintMinHeight = 0;
      this.mMatchConstraintMaxHeight = 0;
      this.mMatchConstraintPercentHeight = 1.0F;
      this.mResolvedDimensionRatioSide = -1;
      this.mResolvedDimensionRatio = 1.0F;
      this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
      this.mCircleConstraintAngle = 0.0F;
      this.hasBaseline = false;
      this.mInVirtuaLayout = false;
      this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
      this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
      this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
      this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
      this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
      this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
      this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
      ConstraintAnchor var1 = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
      this.mCenter = var1;
      this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, var1};
      this.mAnchors = new ArrayList();
      this.mIsInBarrier = new boolean[2];
      this.mListDimensionBehaviors = new ConstraintWidget.DimensionBehaviour[]{ConstraintWidget.DimensionBehaviour.FIXED, ConstraintWidget.DimensionBehaviour.FIXED};
      this.mParent = null;
      this.mWidth = 0;
      this.mHeight = 0;
      this.mDimensionRatio = 0.0F;
      this.mDimensionRatioSide = -1;
      this.mX = 0;
      this.mY = 0;
      this.mRelX = 0;
      this.mRelY = 0;
      this.mOffsetX = 0;
      this.mOffsetY = 0;
      this.mBaselineDistance = 0;
      float var2 = DEFAULT_BIAS;
      this.mHorizontalBiasPercent = var2;
      this.mVerticalBiasPercent = var2;
      this.mContainerItemSkip = 0;
      this.mVisibility = 0;
      this.mDebugName = null;
      this.mType = null;
      this.mOptimizerMeasurable = false;
      this.mGroupsToSolver = false;
      this.mHorizontalChainStyle = 0;
      this.mVerticalChainStyle = 0;
      this.mWeight = new float[]{-1.0F, -1.0F};
      this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
      this.mNextChainWidget = new ConstraintWidget[]{null, null};
      this.mHorizontalNextWidget = null;
      this.mVerticalNextWidget = null;
      this.addAnchors();
   }

   public ConstraintWidget(int var1, int var2) {
      this(0, 0, var1, var2);
   }

   public ConstraintWidget(int var1, int var2, int var3, int var4) {
      this.measured = false;
      this.run = new WidgetRun[2];
      this.horizontalRun = new HorizontalWidgetRun(this);
      this.verticalRun = new VerticalWidgetRun(this);
      this.isTerminalWidget = new boolean[]{(boolean)1, (boolean)1};
      this.wrapMeasure = new int[]{0, 0, 0, 0};
      this.mResolvedHasRatio = false;
      this.mHorizontalResolution = -1;
      this.mVerticalResolution = -1;
      this.mMatchConstraintDefaultWidth = 0;
      this.mMatchConstraintDefaultHeight = 0;
      this.mResolvedMatchConstraintDefault = new int[2];
      this.mMatchConstraintMinWidth = 0;
      this.mMatchConstraintMaxWidth = 0;
      this.mMatchConstraintPercentWidth = 1.0F;
      this.mMatchConstraintMinHeight = 0;
      this.mMatchConstraintMaxHeight = 0;
      this.mMatchConstraintPercentHeight = 1.0F;
      this.mResolvedDimensionRatioSide = -1;
      this.mResolvedDimensionRatio = 1.0F;
      this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
      this.mCircleConstraintAngle = 0.0F;
      this.hasBaseline = false;
      this.mInVirtuaLayout = false;
      this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
      this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
      this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
      this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
      this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
      this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
      this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
      ConstraintAnchor var5 = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
      this.mCenter = var5;
      this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, var5};
      this.mAnchors = new ArrayList();
      this.mIsInBarrier = new boolean[2];
      this.mListDimensionBehaviors = new ConstraintWidget.DimensionBehaviour[]{ConstraintWidget.DimensionBehaviour.FIXED, ConstraintWidget.DimensionBehaviour.FIXED};
      this.mParent = null;
      this.mWidth = 0;
      this.mHeight = 0;
      this.mDimensionRatio = 0.0F;
      this.mDimensionRatioSide = -1;
      this.mX = 0;
      this.mY = 0;
      this.mRelX = 0;
      this.mRelY = 0;
      this.mOffsetX = 0;
      this.mOffsetY = 0;
      this.mBaselineDistance = 0;
      float var6 = DEFAULT_BIAS;
      this.mHorizontalBiasPercent = var6;
      this.mVerticalBiasPercent = var6;
      this.mContainerItemSkip = 0;
      this.mVisibility = 0;
      this.mDebugName = null;
      this.mType = null;
      this.mOptimizerMeasurable = false;
      this.mGroupsToSolver = false;
      this.mHorizontalChainStyle = 0;
      this.mVerticalChainStyle = 0;
      this.mWeight = new float[]{-1.0F, -1.0F};
      this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
      this.mNextChainWidget = new ConstraintWidget[]{null, null};
      this.mHorizontalNextWidget = null;
      this.mVerticalNextWidget = null;
      this.mX = var1;
      this.mY = var2;
      this.mWidth = var3;
      this.mHeight = var4;
      this.addAnchors();
   }

   private void addAnchors() {
      this.mAnchors.add(this.mLeft);
      this.mAnchors.add(this.mTop);
      this.mAnchors.add(this.mRight);
      this.mAnchors.add(this.mBottom);
      this.mAnchors.add(this.mCenterX);
      this.mAnchors.add(this.mCenterY);
      this.mAnchors.add(this.mCenter);
      this.mAnchors.add(this.mBaseline);
   }

   private void applyConstraints(LinearSystem var1, boolean var2, boolean var3, boolean var4, boolean var5, SolverVariable var6, SolverVariable var7, ConstraintWidget.DimensionBehaviour var8, boolean var9, ConstraintAnchor var10, ConstraintAnchor var11, int var12, int var13, int var14, int var15, float var16, boolean var17, boolean var18, boolean var19, boolean var20, int var21, int var22, int var23, int var24, float var25, boolean var26) {
      SolverVariable var27 = var1.createObjectVariable(var10);
      SolverVariable var28 = var1.createObjectVariable(var11);
      SolverVariable var29 = var1.createObjectVariable(var10.getTarget());
      Object var30 = var1.createObjectVariable(var11.getTarget());
      if (LinearSystem.getMetrics() != null) {
         Metrics var31 = LinearSystem.getMetrics();
         ++var31.nonresolvedWidgets;
      }

      boolean var32 = var10.isConnected();
      boolean var33 = var11.isConnected();
      boolean var34 = this.mCenter.isConnected();
      byte var35;
      if (var32) {
         var35 = 1;
      } else {
         var35 = 0;
      }

      int var36 = var35;
      if (var33) {
         var36 = var35 + 1;
      }

      int var37 = var36;
      if (var34) {
         var37 = var36 + 1;
      }

      if (var17) {
         var21 = 3;
      }

      var36 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[var8.ordinal()];
      boolean var53;
      if (var36 != 1 && var36 != 2 && var36 != 3 && var36 == 4 && var21 != 4) {
         var53 = true;
      } else {
         var53 = false;
      }

      int var38 = var21;
      if (this.mVisibility == 8) {
         var21 = 0;
         var53 = false;
      } else {
         var21 = var13;
      }

      if (var26) {
         if (!var32 && !var33 && !var34) {
            var1.addEquality(var27, var12);
         } else if (var32 && !var33) {
            var1.addEquality(var27, var29, var10.getMargin(), 8);
         }
      }

      boolean var49;
      boolean var52;
      if (!var53) {
         if (var9) {
            var1.addEquality(var28, var27, 0, 3);
            if (var14 > 0) {
               var1.addGreaterThan(var28, var27, var14, 8);
            }

            if (var15 < Integer.MAX_VALUE) {
               var1.addLowerThan(var28, var27, var15, 8);
            }
         } else {
            var1.addEquality(var28, var27, var21, 8);
         }

         var12 = var24;
         var24 = var23;
         var52 = var53;
      } else {
         label536: {
            if (var37 == 2 || var17 || var38 != 1 && var38 != 0) {
               var12 = var23;
               if (var23 == -2) {
                  var12 = var21;
               }

               if (var24 == -2) {
                  var13 = var21;
               } else {
                  var13 = var24;
               }

               var23 = var21;
               if (var21 > 0) {
                  var23 = var21;
                  if (var38 != 1) {
                     var23 = 0;
                  }
               }

               var15 = var23;
               if (var12 > 0) {
                  var1.addGreaterThan(var28, var27, var12, 8);
                  var15 = Math.max(var23, var12);
               }

               if (var13 > 0) {
                  if (var3 && var38 == 1) {
                     var49 = false;
                  } else {
                     var49 = true;
                  }

                  if (var49) {
                     var1.addLowerThan(var28, var27, var13, 8);
                  }

                  var15 = Math.min(var15, var13);
               }

               if (var38 == 1) {
                  if (var3) {
                     var1.addEquality(var28, var27, var15, 8);
                  } else if (var18) {
                     var1.addEquality(var28, var27, var15, 5);
                     var1.addLowerThan(var28, var27, var15, 8);
                  } else {
                     var1.addEquality(var28, var27, var15, 5);
                     var1.addLowerThan(var28, var27, var15, 8);
                  }

                  var24 = var12;
                  var12 = var13;
                  var52 = var53;
                  break label536;
               }

               if (var38 != 2) {
                  var24 = var12;
                  var12 = var13;
                  var5 = true;
                  var52 = var53;
                  break label536;
               }

               SolverVariable var51;
               if (var10.getType() != ConstraintAnchor.Type.TOP && var10.getType() != ConstraintAnchor.Type.BOTTOM) {
                  var30 = var1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                  var51 = var1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
               } else {
                  var30 = var1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
                  var51 = var1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
               }

               ArrayRow var39 = var1.createRow();
               var15 = var12;
               var1.addConstraint(var39.createRowDimensionRatio(var28, var27, var51, (SolverVariable)var30, var25));
               var12 = var13;
               var13 = var15;
            } else {
               var13 = Math.max(var23, var21);
               var12 = var13;
               if (var24 > 0) {
                  var12 = Math.min(var24, var13);
               }

               var1.addEquality(var28, var27, var12, 8);
               var13 = var23;
               var12 = var24;
            }

            var52 = false;
            var24 = var13;
         }
      }

      Object var56 = var30;
      boolean var43;
      boolean var44;
      if (var26 && !var18) {
         if ((var32 || var33 || var34) && (!var32 || var33)) {
            if (!var32 && var33) {
               var1.addEquality(var28, (SolverVariable)var30, -var11.getMargin(), 8);
               if (var3) {
                  var1.addGreaterThan(var27, var6, 0, 5);
               }
            } else if (var32 && var33) {
               ConstraintWidget var40;
               ConstraintWidget var41;
               byte var45;
               byte var46;
               byte var47;
               boolean var48;
               boolean var50;
               byte var54;
               label396: {
                  label395: {
                     byte var55;
                     label394: {
                        label393: {
                           var40 = var10.mTarget.mOwner;
                           var30 = var11.mTarget.mOwner;
                           var41 = this.getParent();
                           var54 = 6;
                           if (var52) {
                              if (var38 == 0) {
                                 if (var12 == 0 && var24 == 0) {
                                    var50 = false;
                                    var44 = true;
                                    var45 = 8;
                                    var47 = 8;
                                 } else {
                                    var50 = true;
                                    var44 = false;
                                    var45 = 5;
                                    var47 = 5;
                                 }

                                 if (var40 instanceof Barrier || var30 instanceof Barrier) {
                                    var47 = 4;
                                 }

                                 var55 = var45;
                                 var49 = false;
                                 var45 = 6;
                                 var48 = var44;
                                 var46 = var55;
                                 break label396;
                              }

                              if (var38 == 1) {
                                 var50 = true;
                                 var48 = false;
                                 var49 = true;
                                 var46 = 8;
                                 break label395;
                              }

                              if (var38 != 3) {
                                 var50 = false;
                                 var48 = false;
                                 var49 = false;
                                 break label393;
                              }

                              if (this.mResolvedDimensionRatioSide == -1) {
                                 if (var19) {
                                    if (var3) {
                                       var46 = 5;
                                    } else {
                                       var46 = 4;
                                    }
                                 } else {
                                    var46 = 8;
                                 }

                                 var47 = 8;
                                 break label394;
                              }

                              if (var17) {
                                 if (var22 != 2 && var22 != 1) {
                                    var44 = false;
                                 } else {
                                    var44 = true;
                                 }

                                 if (!var44) {
                                    var45 = 8;
                                    var46 = 5;
                                 } else {
                                    var45 = 5;
                                    var46 = 4;
                                 }

                                 var47 = var45;
                                 var55 = var46;
                                 var45 = 6;
                                 var50 = true;
                                 var48 = true;
                                 var49 = true;
                                 var46 = var47;
                                 var47 = var55;
                                 break label396;
                              }

                              if (var12 > 0) {
                                 var46 = 6;
                                 var47 = 5;
                                 break label394;
                              }

                              if (var12 == 0 && var24 == 0) {
                                 if (!var19) {
                                    var45 = 6;
                                    var50 = true;
                                    var48 = true;
                                    var49 = true;
                                    var46 = 5;
                                    var47 = 8;
                                    break label396;
                                 }

                                 if (var40 != var41 && var30 != var41) {
                                    var46 = 4;
                                 } else {
                                    var46 = 5;
                                 }

                                 var50 = true;
                                 var48 = true;
                                 var49 = true;
                                 break label395;
                              }

                              var48 = true;
                           } else {
                              var48 = false;
                           }

                           var50 = true;
                           var49 = true;
                        }

                        var46 = 5;
                        break label395;
                     }

                     var49 = true;
                     var48 = true;
                     var50 = true;
                     var55 = 5;
                     var45 = var46;
                     var46 = var47;
                     var47 = var55;
                     break label396;
                  }

                  var45 = 6;
                  var47 = 4;
               }

               if (var49 && var29 == var56 && var40 != var41) {
                  var49 = false;
                  var53 = false;
               } else {
                  var53 = true;
               }

               if (var50) {
                  if (this.mVisibility == 8) {
                     var45 = 4;
                  }

                  var1.addCentering(var27, var29, var10.getMargin(), var16, (SolverVariable)var56, var28, var11.getMargin(), var45);
               }

               var43 = true;
               if (this.mVisibility == 8) {
                  return;
               }

               if (var49) {
                  if (var3 && var29 != var56 && !var52 && (var40 instanceof Barrier || var30 instanceof Barrier)) {
                     var46 = 6;
                  }

                  var1.addGreaterThan(var27, var29, var10.getMargin(), var46);
                  var1.addLowerThan(var28, (SolverVariable)var56, -var11.getMargin(), var46);
               }

               if (var3 && var20 && !(var40 instanceof Barrier) && !(var30 instanceof Barrier)) {
                  var47 = 6;
                  var12 = 6;
                  var49 = var43;
               } else {
                  var12 = var47;
                  var49 = var53;
                  var47 = var46;
               }

               if (var49) {
                  if (var48 && (!var19 || var4)) {
                     var13 = var54;
                     if (var40 != var41) {
                        if (var30 == var41) {
                           var13 = var54;
                        } else {
                           var13 = var12;
                        }
                     }

                     if (var40 instanceof Guideline || var30 instanceof Guideline) {
                        var13 = 5;
                     }

                     if (var40 instanceof Barrier || var30 instanceof Barrier) {
                        var13 = 5;
                     }

                     if (var19) {
                        var13 = 5;
                     }

                     var12 = Math.max(var13, var12);
                  }

                  if (var3) {
                     var13 = Math.min(var47, var12);
                     var12 = var13;
                     if (var17) {
                        var12 = var13;
                        if (!var19) {
                           label299: {
                              if (var40 != var41) {
                                 var12 = var13;
                                 if (var30 != var41) {
                                    break label299;
                                 }
                              }

                              var12 = 4;
                           }
                        }
                     }
                  }

                  var1.addEquality(var27, var29, var10.getMargin(), var12);
                  var1.addEquality(var28, (SolverVariable)var56, -var11.getMargin(), var12);
               }

               if (var3) {
                  if (var6 == var29) {
                     var12 = var10.getMargin();
                  } else {
                     var12 = 0;
                  }

                  if (var29 != var6) {
                     var1.addGreaterThan(var27, var6, var12, 5);
                  }
               }

               if (var3 && var52 && var14 == 0 && var24 == 0) {
                  if (var52 && var38 == 3) {
                     var1.addGreaterThan(var28, var27, 0, 8);
                  } else {
                     var1.addGreaterThan(var28, var27, 0, 5);
                  }
               }
            }
         }

         if (var3 && var5) {
            if (var11.mTarget != null) {
               var12 = var11.getMargin();
            } else {
               var12 = 0;
            }

            if (var30 != var7) {
               var1.addGreaterThan(var7, var28, var12, 5);
            }
         }

      } else {
         if (var37 < 2 && var3 && var5) {
            var1.addGreaterThan(var27, var6, 0, 8);
            if (!var2 && this.mBaseline.mTarget != null) {
               var43 = false;
            } else {
               var43 = true;
            }

            var44 = var43;
            if (!var2) {
               var44 = var43;
               if (this.mBaseline.mTarget != null) {
                  ConstraintWidget var42 = this.mBaseline.mTarget.mOwner;
                  if (var42.mDimensionRatio != 0.0F && var42.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var42.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var44 = true;
                  } else {
                     var44 = false;
                  }
               }
            }

            if (var44) {
               var1.addGreaterThan(var7, var28, 0, 8);
            }
         }

      }
   }

   private boolean isChainHead(int var1) {
      var1 *= 2;
      ConstraintAnchor var2 = this.mListAnchors[var1].mTarget;
      boolean var3 = true;
      if (var2 != null) {
         ConstraintAnchor var4 = this.mListAnchors[var1].mTarget.mTarget;
         ConstraintAnchor[] var5 = this.mListAnchors;
         if (var4 != var5[var1]) {
            ++var1;
            if (var5[var1].mTarget != null && this.mListAnchors[var1].mTarget.mTarget == this.mListAnchors[var1]) {
               return var3;
            }
         }
      }

      var3 = false;
      return var3;
   }

   boolean addFirst() {
      boolean var1;
      if (!(this instanceof VirtualLayout) && !(this instanceof Guideline)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void addToSolver(LinearSystem var1) {
      SolverVariable var3 = var1.createObjectVariable(this.mLeft);
      SolverVariable var4 = var1.createObjectVariable(this.mRight);
      SolverVariable var5 = var1.createObjectVariable(this.mTop);
      SolverVariable var6 = var1.createObjectVariable(this.mBottom);
      SolverVariable var7 = var1.createObjectVariable(this.mBaseline);
      Metrics var8;
      if (LinearSystem.sMetrics != null) {
         var8 = LinearSystem.sMetrics;
         ++var8.widgets;
      }

      ConstraintWidget var30;
      boolean var36;
      if (this.horizontalRun.start.resolved && this.horizontalRun.end.resolved && this.verticalRun.start.resolved && this.verticalRun.end.resolved) {
         if (LinearSystem.sMetrics != null) {
            var8 = LinearSystem.sMetrics;
            ++var8.graphSolved;
         }

         var1.addEquality(var3, this.horizontalRun.start.value);
         var1.addEquality(var4, this.horizontalRun.end.value);
         var1.addEquality(var5, this.verticalRun.start.value);
         var1.addEquality(var6, this.verticalRun.end.value);
         var1.addEquality(var7, this.verticalRun.baseline.value);
         var30 = this.mParent;
         if (var30 != null) {
            boolean var35;
            if (var30 != null && var30.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var35 = true;
            } else {
               var35 = false;
            }

            var30 = this.mParent;
            if (var30 != null && var30.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var36 = true;
            } else {
               var36 = false;
            }

            if (var35 && this.isTerminalWidget[0] && !this.isInHorizontalChain()) {
               var1.addGreaterThan(var1.createObjectVariable(this.mParent.mRight), var4, 0, 8);
            }

            if (var36 && this.isTerminalWidget[1] && !this.isInVerticalChain()) {
               var1.addGreaterThan(var1.createObjectVariable(this.mParent.mBottom), var6, 0, 8);
            }
         }

      } else {
         if (LinearSystem.sMetrics != null) {
            var8 = LinearSystem.sMetrics;
            ++var8.linearSolved;
         }

         var30 = this.mParent;
         boolean var11;
         boolean var12;
         boolean var13;
         boolean var14;
         boolean var15;
         if (var30 != null) {
            if (var30 != null && var30.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var11 = true;
            } else {
               var11 = false;
            }

            var30 = this.mParent;
            if (var30 != null && var30.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var12 = true;
            } else {
               var12 = false;
            }

            if (this.isChainHead(0)) {
               ((ConstraintWidgetContainer)this.mParent).addChain(this, 0);
               var13 = true;
            } else {
               var13 = this.isInHorizontalChain();
            }

            if (this.isChainHead(1)) {
               ((ConstraintWidgetContainer)this.mParent).addChain(this, 1);
               var14 = true;
            } else {
               var14 = this.isInVerticalChain();
            }

            if (!var13 && var11 && this.mVisibility != 8 && this.mLeft.mTarget == null && this.mRight.mTarget == null) {
               var1.addGreaterThan(var1.createObjectVariable(this.mParent.mRight), var4, 0, 1);
            }

            if (!var14 && var12 && this.mVisibility != 8 && this.mTop.mTarget == null && this.mBottom.mTarget == null && this.mBaseline == null) {
               var1.addGreaterThan(var1.createObjectVariable(this.mParent.mBottom), var6, 0, 1);
            }

            var15 = var14;
            var14 = var11;
         } else {
            var12 = false;
            var14 = false;
            var15 = false;
            var13 = false;
         }

         int var10 = this.mWidth;
         int var16 = this.mMinWidth;
         int var9 = var10;
         if (var10 < var16) {
            var9 = var16;
         }

         var16 = this.mHeight;
         int var17 = this.mMinHeight;
         var10 = var16;
         if (var16 < var17) {
            var10 = var17;
         }

         if (this.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            var11 = true;
         } else {
            var11 = false;
         }

         boolean var18;
         if (this.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            var18 = true;
         } else {
            var18 = false;
         }

         this.mResolvedDimensionRatioSide = this.mDimensionRatioSide;
         float var19 = this.mDimensionRatio;
         this.mResolvedDimensionRatio = var19;
         var17 = this.mMatchConstraintDefaultWidth;
         int var20 = this.mMatchConstraintDefaultHeight;
         if (var19 > 0.0F && this.mVisibility != 8) {
            label356: {
               var16 = var17;
               if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var16 = var17;
                  if (var17 == 0) {
                     var16 = 3;
                  }
               }

               var17 = var20;
               if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var17 = var20;
                  if (var20 == 0) {
                     var17 = 3;
                  }
               }

               label370: {
                  if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var16 == 3 && var17 == 3) {
                     this.setupDimensionRatio(var14, var12, var11, var18);
                     var20 = var10;
                  } else {
                     if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var16 == 3) {
                        this.mResolvedDimensionRatioSide = 0;
                        int var21 = (int)(this.mResolvedDimensionRatio * (float)this.mHeight);
                        ConstraintWidget.DimensionBehaviour var31 = this.mListDimensionBehaviors[1];
                        ConstraintWidget.DimensionBehaviour var22 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                        var9 = var10;
                        var20 = var17;
                        if (var31 != var22) {
                           var11 = false;
                           var17 = 4;
                           var10 = var21;
                           var16 = var20;
                           break label356;
                        }

                        var10 = var21;
                        break label370;
                     }

                     var20 = var10;
                     if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        var20 = var10;
                        if (var17 == 3) {
                           this.mResolvedDimensionRatioSide = 1;
                           if (this.mDimensionRatioSide == -1) {
                              this.mResolvedDimensionRatio = 1.0F / this.mResolvedDimensionRatio;
                           }

                           var10 = (int)(this.mResolvedDimensionRatio * (float)this.mWidth);
                           var20 = var10;
                           if (this.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                              var20 = var10;
                              var17 = var16;
                              var11 = false;
                              var16 = 4;
                              var10 = var9;
                              var9 = var20;
                              break label356;
                           }
                        }
                     }
                  }

                  var10 = var9;
                  var9 = var20;
               }

               var20 = var16;
               var16 = var17;
               var11 = true;
               var17 = var20;
            }
         } else {
            var16 = var20;
            var20 = var9;
            var11 = false;
            var9 = var10;
            var10 = var20;
         }

         label302: {
            int[] var32 = this.mResolvedMatchConstraintDefault;
            var32[0] = var17;
            var32[1] = var16;
            this.mResolvedHasRatio = var11;
            if (var11) {
               var20 = this.mResolvedDimensionRatioSide;
               if (var20 == 0 || var20 == -1) {
                  var18 = true;
                  break label302;
               }
            }

            var18 = false;
         }

         boolean var23;
         if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this instanceof ConstraintWidgetContainer) {
            var23 = true;
         } else {
            var23 = false;
         }

         if (var23) {
            var10 = 0;
         }

         boolean var24 = this.mCenter.isConnected() ^ true;
         boolean[] var33 = this.mIsInBarrier;
         boolean var25 = var33[0];
         boolean var26 = var33[1];
         var20 = this.mHorizontalResolution;
         Object var27 = null;
         ConstraintWidget var37;
         SolverVariable var38;
         if (var20 != 2) {
            if (this.horizontalRun.start.resolved && this.horizontalRun.end.resolved) {
               var1.addEquality(var3, this.horizontalRun.start.value);
               var1.addEquality(var4, this.horizontalRun.end.value);
               if (this.mParent != null && var14 && this.isTerminalWidget[0] && !this.isInHorizontalChain()) {
                  var1.addGreaterThan(var1.createObjectVariable(this.mParent.mRight), var4, 0, 8);
               }
            } else {
               var30 = this.mParent;
               SolverVariable var34;
               if (var30 != null) {
                  var34 = var1.createObjectVariable(var30.mRight);
               } else {
                  var34 = null;
               }

               var37 = this.mParent;
               if (var37 != null) {
                  var38 = var1.createObjectVariable(var37.mLeft);
               } else {
                  var38 = null;
               }

               this.applyConstraints(var1, true, var14, var12, this.isTerminalWidget[0], var38, var34, this.mListDimensionBehaviors[0], var23, this.mLeft, this.mRight, this.mX, var10, this.mMinWidth, this.mMaxDimension[0], this.mHorizontalBiasPercent, var18, var13, var15, var25, var17, var16, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, var24);
            }
         }

         var23 = var11;
         var25 = var12;
         if (this.verticalRun.start.resolved && this.verticalRun.end.resolved) {
            var10 = this.verticalRun.start.value;
            var1.addEquality(var5, var10);
            var10 = this.verticalRun.end.value;
            var1.addEquality(var6, var10);
            var1.addEquality(var7, this.verticalRun.baseline.value);
            ConstraintWidget var28 = this.mParent;
            if (var28 != null && !var15 && var12 && this.isTerminalWidget[1]) {
               var1.addGreaterThan(var1.createObjectVariable(var28.mBottom), var6, 0, 8);
            }

            var36 = false;
         } else {
            var36 = true;
         }

         if (this.mVerticalResolution == 2) {
            var36 = false;
         }

         if (var36) {
            if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this instanceof ConstraintWidgetContainer) {
               var11 = true;
            } else {
               var11 = false;
            }

            if (var11) {
               var9 = 0;
            }

            label267: {
               if (var23) {
                  var10 = this.mResolvedDimensionRatioSide;
                  if (var10 == 1 || var10 == -1) {
                     var12 = true;
                     break label267;
                  }
               }

               var12 = false;
            }

            var37 = this.mParent;
            if (var37 != null) {
               var38 = var1.createObjectVariable(var37.mBottom);
            } else {
               var38 = null;
            }

            ConstraintWidget var29 = this.mParent;
            var6 = (SolverVariable)var27;
            if (var29 != null) {
               var6 = var1.createObjectVariable(var29.mTop);
            }

            label366: {
               if (this.mBaselineDistance > 0 || this.mVisibility == 8) {
                  var1.addEquality(var7, var5, this.getBaselineDistance(), 8);
                  if (this.mBaseline.mTarget != null) {
                     var1.addEquality(var7, var1.createObjectVariable(this.mBaseline.mTarget), 0, 8);
                     if (var25) {
                        var1.addGreaterThan(var38, var1.createObjectVariable(this.mBottom), 0, 5);
                     }

                     var18 = false;
                     break label366;
                  }

                  if (this.mVisibility == 8) {
                     var1.addEquality(var7, var5, 0, 8);
                  }
               }

               var18 = var24;
            }

            this.applyConstraints(var1, false, var25, var14, this.isTerminalWidget[1], var6, var38, this.mListDimensionBehaviors[1], var11, this.mTop, this.mBottom, this.mY, var9, this.mMinHeight, this.mMaxDimension[1], this.mVerticalBiasPercent, var12, var15, var13, var26, var16, var17, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight, this.mMatchConstraintPercentHeight, var18);
         }

         if (var11) {
            if (this.mResolvedDimensionRatioSide == 1) {
               var1.addRatio(var6, var5, var4, var3, this.mResolvedDimensionRatio, 8);
            } else {
               var1.addRatio(var4, var3, var6, var5, this.mResolvedDimensionRatio, 8);
            }
         }

         if (this.mCenter.isConnected()) {
            var1.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float)Math.toRadians((double)(this.mCircleConstraintAngle + 90.0F)), this.mCenter.getMargin());
         }

      }
   }

   public boolean allowedInBarrier() {
      boolean var1;
      if (this.mVisibility != 8) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void connect(ConstraintAnchor.Type var1, ConstraintWidget var2, ConstraintAnchor.Type var3) {
      this.connect(var1, var2, var3, 0);
   }

   public void connect(ConstraintAnchor.Type var1, ConstraintWidget var2, ConstraintAnchor.Type var3, int var4) {
      ConstraintAnchor var5;
      ConstraintAnchor var10;
      ConstraintAnchor var12;
      if (var1 == ConstraintAnchor.Type.CENTER) {
         if (var3 == ConstraintAnchor.Type.CENTER) {
            var5 = this.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor var6 = this.getAnchor(ConstraintAnchor.Type.RIGHT);
            var10 = this.getAnchor(ConstraintAnchor.Type.TOP);
            var12 = this.getAnchor(ConstraintAnchor.Type.BOTTOM);
            boolean var7 = true;
            boolean var13;
            if ((var5 == null || !var5.isConnected()) && (var6 == null || !var6.isConnected())) {
               this.connect(ConstraintAnchor.Type.LEFT, var2, ConstraintAnchor.Type.LEFT, 0);
               this.connect(ConstraintAnchor.Type.RIGHT, var2, ConstraintAnchor.Type.RIGHT, 0);
               var13 = true;
            } else {
               var13 = false;
            }

            if ((var10 == null || !var10.isConnected()) && (var12 == null || !var12.isConnected())) {
               this.connect(ConstraintAnchor.Type.TOP, var2, ConstraintAnchor.Type.TOP, 0);
               this.connect(ConstraintAnchor.Type.BOTTOM, var2, ConstraintAnchor.Type.BOTTOM, 0);
            } else {
               var7 = false;
            }

            if (var13 && var7) {
               this.getAnchor(ConstraintAnchor.Type.CENTER).connect(var2.getAnchor(ConstraintAnchor.Type.CENTER), 0);
            } else if (var13) {
               this.getAnchor(ConstraintAnchor.Type.CENTER_X).connect(var2.getAnchor(ConstraintAnchor.Type.CENTER_X), 0);
            } else if (var7) {
               this.getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(var2.getAnchor(ConstraintAnchor.Type.CENTER_Y), 0);
            }
         } else if (var3 != ConstraintAnchor.Type.LEFT && var3 != ConstraintAnchor.Type.RIGHT) {
            if (var3 == ConstraintAnchor.Type.TOP || var3 == ConstraintAnchor.Type.BOTTOM) {
               this.connect(ConstraintAnchor.Type.TOP, var2, var3, 0);
               this.connect(ConstraintAnchor.Type.BOTTOM, var2, var3, 0);
               this.getAnchor(ConstraintAnchor.Type.CENTER).connect(var2.getAnchor(var3), 0);
            }
         } else {
            this.connect(ConstraintAnchor.Type.LEFT, var2, var3, 0);
            var1 = ConstraintAnchor.Type.RIGHT;

            try {
               this.connect(var1, var2, var3, 0);
            } finally {
               ;
            }

            this.getAnchor(ConstraintAnchor.Type.CENTER).connect(var2.getAnchor(var3), 0);
         }
      } else {
         ConstraintAnchor var11;
         if (var1 != ConstraintAnchor.Type.CENTER_X || var3 != ConstraintAnchor.Type.LEFT && var3 != ConstraintAnchor.Type.RIGHT) {
            if (var1 == ConstraintAnchor.Type.CENTER_Y && (var3 == ConstraintAnchor.Type.TOP || var3 == ConstraintAnchor.Type.BOTTOM)) {
               var10 = var2.getAnchor(var3);
               this.getAnchor(ConstraintAnchor.Type.TOP).connect(var10, 0);
               this.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(var10, 0);
               this.getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(var10, 0);
            } else if (var1 == ConstraintAnchor.Type.CENTER_X && var3 == ConstraintAnchor.Type.CENTER_X) {
               this.getAnchor(ConstraintAnchor.Type.LEFT).connect(var2.getAnchor(ConstraintAnchor.Type.LEFT), 0);
               this.getAnchor(ConstraintAnchor.Type.RIGHT).connect(var2.getAnchor(ConstraintAnchor.Type.RIGHT), 0);
               this.getAnchor(ConstraintAnchor.Type.CENTER_X).connect(var2.getAnchor(var3), 0);
            } else if (var1 == ConstraintAnchor.Type.CENTER_Y && var3 == ConstraintAnchor.Type.CENTER_Y) {
               this.getAnchor(ConstraintAnchor.Type.TOP).connect(var2.getAnchor(ConstraintAnchor.Type.TOP), 0);
               this.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(var2.getAnchor(ConstraintAnchor.Type.BOTTOM), 0);
               this.getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(var2.getAnchor(var3), 0);
            } else {
               var5 = this.getAnchor(var1);
               var11 = var2.getAnchor(var3);
               if (var5.isValidConnection(var11)) {
                  int var14;
                  if (var1 == ConstraintAnchor.Type.BASELINE) {
                     var10 = this.getAnchor(ConstraintAnchor.Type.TOP);
                     var12 = this.getAnchor(ConstraintAnchor.Type.BOTTOM);
                     if (var10 != null) {
                        var10.reset();
                     }

                     if (var12 != null) {
                        var12.reset();
                     }

                     var14 = 0;
                  } else if (var1 != ConstraintAnchor.Type.TOP && var1 != ConstraintAnchor.Type.BOTTOM) {
                     label336: {
                        if (var1 != ConstraintAnchor.Type.LEFT) {
                           var14 = var4;
                           if (var1 != ConstraintAnchor.Type.RIGHT) {
                              break label336;
                           }
                        }

                        var12 = this.getAnchor(ConstraintAnchor.Type.CENTER);
                        if (var12.getTarget() != var11) {
                           var12.reset();
                        }

                        var12 = this.getAnchor(var1).getOpposite();
                        var10 = this.getAnchor(ConstraintAnchor.Type.CENTER_X);
                        var14 = var4;
                        if (var10.isConnected()) {
                           var12.reset();
                           var10.reset();
                           var14 = var4;
                        }
                     }
                  } else {
                     var12 = this.getAnchor(ConstraintAnchor.Type.BASELINE);
                     if (var12 != null) {
                        var12.reset();
                     }

                     var12 = this.getAnchor(ConstraintAnchor.Type.CENTER);
                     if (var12.getTarget() != var11) {
                        var12.reset();
                     }

                     var10 = this.getAnchor(var1).getOpposite();
                     var12 = this.getAnchor(ConstraintAnchor.Type.CENTER_Y);
                     var14 = var4;
                     if (var12.isConnected()) {
                        var10.reset();
                        var12.reset();
                        var14 = var4;
                     }
                  }

                  var5.connect(var11, var14);
               }
            }
         } else {
            var10 = this.getAnchor(ConstraintAnchor.Type.LEFT);
            var11 = var2.getAnchor(var3);
            var12 = this.getAnchor(ConstraintAnchor.Type.RIGHT);
            var10.connect(var11, 0);
            var12.connect(var11, 0);
            this.getAnchor(ConstraintAnchor.Type.CENTER_X).connect(var11, 0);
         }
      }

   }

   public void connect(ConstraintAnchor var1, ConstraintAnchor var2, int var3) {
      if (var1.getOwner() == this) {
         this.connect(var1.getType(), var2.getOwner(), var2.getType(), var3);
      }

   }

   public void connectCircularConstraint(ConstraintWidget var1, float var2, int var3) {
      this.immediateConnect(ConstraintAnchor.Type.CENTER, var1, ConstraintAnchor.Type.CENTER, var3, 0);
      this.mCircleConstraintAngle = var2;
   }

   public void copy(ConstraintWidget var1, HashMap<ConstraintWidget, ConstraintWidget> var2) {
      this.mHorizontalResolution = var1.mHorizontalResolution;
      this.mVerticalResolution = var1.mVerticalResolution;
      this.mMatchConstraintDefaultWidth = var1.mMatchConstraintDefaultWidth;
      this.mMatchConstraintDefaultHeight = var1.mMatchConstraintDefaultHeight;
      int[] var3 = this.mResolvedMatchConstraintDefault;
      int[] var4 = var1.mResolvedMatchConstraintDefault;
      var3[0] = var4[0];
      var3[1] = var4[1];
      this.mMatchConstraintMinWidth = var1.mMatchConstraintMinWidth;
      this.mMatchConstraintMaxWidth = var1.mMatchConstraintMaxWidth;
      this.mMatchConstraintMinHeight = var1.mMatchConstraintMinHeight;
      this.mMatchConstraintMaxHeight = var1.mMatchConstraintMaxHeight;
      this.mMatchConstraintPercentHeight = var1.mMatchConstraintPercentHeight;
      this.mIsWidthWrapContent = var1.mIsWidthWrapContent;
      this.mIsHeightWrapContent = var1.mIsHeightWrapContent;
      this.mResolvedDimensionRatioSide = var1.mResolvedDimensionRatioSide;
      this.mResolvedDimensionRatio = var1.mResolvedDimensionRatio;
      var4 = var1.mMaxDimension;
      this.mMaxDimension = Arrays.copyOf(var4, var4.length);
      this.mCircleConstraintAngle = var1.mCircleConstraintAngle;
      this.hasBaseline = var1.hasBaseline;
      this.inPlaceholder = var1.inPlaceholder;
      this.mLeft.reset();
      this.mTop.reset();
      this.mRight.reset();
      this.mBottom.reset();
      this.mBaseline.reset();
      this.mCenterX.reset();
      this.mCenterY.reset();
      this.mCenter.reset();
      this.mListDimensionBehaviors = (ConstraintWidget.DimensionBehaviour[])Arrays.copyOf(this.mListDimensionBehaviors, 2);
      ConstraintWidget var7 = this.mParent;
      Object var6 = null;
      if (var7 == null) {
         var7 = null;
      } else {
         var7 = (ConstraintWidget)var2.get(var1.mParent);
      }

      this.mParent = var7;
      this.mWidth = var1.mWidth;
      this.mHeight = var1.mHeight;
      this.mDimensionRatio = var1.mDimensionRatio;
      this.mDimensionRatioSide = var1.mDimensionRatioSide;
      this.mX = var1.mX;
      this.mY = var1.mY;
      this.mRelX = var1.mRelX;
      this.mRelY = var1.mRelY;
      this.mOffsetX = var1.mOffsetX;
      this.mOffsetY = var1.mOffsetY;
      this.mBaselineDistance = var1.mBaselineDistance;
      this.mMinWidth = var1.mMinWidth;
      this.mMinHeight = var1.mMinHeight;
      this.mHorizontalBiasPercent = var1.mHorizontalBiasPercent;
      this.mVerticalBiasPercent = var1.mVerticalBiasPercent;
      this.mCompanionWidget = var1.mCompanionWidget;
      this.mContainerItemSkip = var1.mContainerItemSkip;
      this.mVisibility = var1.mVisibility;
      this.mDebugName = var1.mDebugName;
      this.mType = var1.mType;
      this.mDistToTop = var1.mDistToTop;
      this.mDistToLeft = var1.mDistToLeft;
      this.mDistToRight = var1.mDistToRight;
      this.mDistToBottom = var1.mDistToBottom;
      this.mLeftHasCentered = var1.mLeftHasCentered;
      this.mRightHasCentered = var1.mRightHasCentered;
      this.mTopHasCentered = var1.mTopHasCentered;
      this.mBottomHasCentered = var1.mBottomHasCentered;
      this.mHorizontalWrapVisited = var1.mHorizontalWrapVisited;
      this.mVerticalWrapVisited = var1.mVerticalWrapVisited;
      this.mOptimizerMeasurable = var1.mOptimizerMeasurable;
      this.mGroupsToSolver = var1.mGroupsToSolver;
      this.mHorizontalChainStyle = var1.mHorizontalChainStyle;
      this.mVerticalChainStyle = var1.mVerticalChainStyle;
      this.mHorizontalChainFixedPosition = var1.mHorizontalChainFixedPosition;
      this.mVerticalChainFixedPosition = var1.mVerticalChainFixedPosition;
      float[] var9 = this.mWeight;
      float[] var5 = var1.mWeight;
      var9[0] = var5[0];
      var9[1] = var5[1];
      ConstraintWidget[] var8 = this.mListNextMatchConstraintsWidget;
      ConstraintWidget[] var10 = var1.mListNextMatchConstraintsWidget;
      var8[0] = var10[0];
      var8[1] = var10[1];
      var8 = this.mNextChainWidget;
      var10 = var1.mNextChainWidget;
      var8[0] = var10[0];
      var8[1] = var10[1];
      var7 = var1.mHorizontalNextWidget;
      if (var7 == null) {
         var7 = null;
      } else {
         var7 = (ConstraintWidget)var2.get(var7);
      }

      this.mHorizontalNextWidget = var7;
      var1 = var1.mVerticalNextWidget;
      if (var1 == null) {
         var1 = (ConstraintWidget)var6;
      } else {
         var1 = (ConstraintWidget)var2.get(var1);
      }

      this.mVerticalNextWidget = var1;
   }

   public void createObjectVariables(LinearSystem var1) {
      var1.createObjectVariable(this.mLeft);
      var1.createObjectVariable(this.mTop);
      var1.createObjectVariable(this.mRight);
      var1.createObjectVariable(this.mBottom);
      if (this.mBaselineDistance > 0) {
         var1.createObjectVariable(this.mBaseline);
      }

   }

   public ConstraintAnchor getAnchor(ConstraintAnchor.Type var1) {
      switch(null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[var1.ordinal()]) {
      case 1:
         return this.mLeft;
      case 2:
         return this.mTop;
      case 3:
         return this.mRight;
      case 4:
         return this.mBottom;
      case 5:
         return this.mBaseline;
      case 6:
         return this.mCenter;
      case 7:
         return this.mCenterX;
      case 8:
         return this.mCenterY;
      case 9:
         return null;
      default:
         throw new AssertionError(var1.name());
      }
   }

   public ArrayList<ConstraintAnchor> getAnchors() {
      return this.mAnchors;
   }

   public int getBaselineDistance() {
      return this.mBaselineDistance;
   }

   public float getBiasPercent(int var1) {
      if (var1 == 0) {
         return this.mHorizontalBiasPercent;
      } else {
         return var1 == 1 ? this.mVerticalBiasPercent : -1.0F;
      }
   }

   public int getBottom() {
      return this.getY() + this.mHeight;
   }

   public Object getCompanionWidget() {
      return this.mCompanionWidget;
   }

   public int getContainerItemSkip() {
      return this.mContainerItemSkip;
   }

   public String getDebugName() {
      return this.mDebugName;
   }

   public ConstraintWidget.DimensionBehaviour getDimensionBehaviour(int var1) {
      if (var1 == 0) {
         return this.getHorizontalDimensionBehaviour();
      } else {
         return var1 == 1 ? this.getVerticalDimensionBehaviour() : null;
      }
   }

   public float getDimensionRatio() {
      return this.mDimensionRatio;
   }

   public int getDimensionRatioSide() {
      return this.mDimensionRatioSide;
   }

   public boolean getHasBaseline() {
      return this.hasBaseline;
   }

   public int getHeight() {
      return this.mVisibility == 8 ? 0 : this.mHeight;
   }

   public float getHorizontalBiasPercent() {
      return this.mHorizontalBiasPercent;
   }

   public ConstraintWidget getHorizontalChainControlWidget() {
      boolean var1 = this.isInHorizontalChain();
      ConstraintWidget var2 = null;
      if (var1) {
         var2 = this;
         ConstraintWidget var3 = null;

         while(var3 == null && var2 != null) {
            ConstraintAnchor var4 = var2.getAnchor(ConstraintAnchor.Type.LEFT);
            if (var4 == null) {
               var4 = null;
            } else {
               var4 = var4.getTarget();
            }

            ConstraintWidget var6;
            if (var4 == null) {
               var6 = null;
            } else {
               var6 = var4.getOwner();
            }

            if (var6 == this.getParent()) {
               return var2;
            }

            ConstraintAnchor var5;
            if (var6 == null) {
               var5 = null;
            } else {
               var5 = var6.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            }

            if (var5 != null && var5.getOwner() != var2) {
               var3 = var2;
            } else {
               var2 = var6;
            }
         }

         var2 = var3;
      }

      return var2;
   }

   public int getHorizontalChainStyle() {
      return this.mHorizontalChainStyle;
   }

   public ConstraintWidget.DimensionBehaviour getHorizontalDimensionBehaviour() {
      return this.mListDimensionBehaviors[0];
   }

   public int getHorizontalMargin() {
      ConstraintAnchor var1 = this.mLeft;
      int var2 = 0;
      if (var1 != null) {
         var2 = 0 + var1.mMargin;
      }

      var1 = this.mRight;
      int var3 = var2;
      if (var1 != null) {
         var3 = var2 + var1.mMargin;
      }

      return var3;
   }

   public int getLeft() {
      return this.getX();
   }

   public int getLength(int var1) {
      if (var1 == 0) {
         return this.getWidth();
      } else {
         return var1 == 1 ? this.getHeight() : 0;
      }
   }

   public int getMaxHeight() {
      return this.mMaxDimension[1];
   }

   public int getMaxWidth() {
      return this.mMaxDimension[0];
   }

   public int getMinHeight() {
      return this.mMinHeight;
   }

   public int getMinWidth() {
      return this.mMinWidth;
   }

   public ConstraintWidget getNextChainMember(int var1) {
      ConstraintAnchor var2;
      ConstraintAnchor var3;
      if (var1 == 0) {
         if (this.mRight.mTarget != null) {
            var2 = this.mRight.mTarget.mTarget;
            var3 = this.mRight;
            if (var2 == var3) {
               return var3.mTarget.mOwner;
            }
         }
      } else if (var1 == 1 && this.mBottom.mTarget != null) {
         var3 = this.mBottom.mTarget.mTarget;
         var2 = this.mBottom;
         if (var3 == var2) {
            return var2.mTarget.mOwner;
         }
      }

      return null;
   }

   public int getOptimizerWrapHeight() {
      int var1 = this.mHeight;
      int var2 = var1;
      if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         if (this.mMatchConstraintDefaultHeight == 1) {
            var1 = Math.max(this.mMatchConstraintMinHeight, var1);
         } else {
            var1 = this.mMatchConstraintMinHeight;
            if (var1 > 0) {
               this.mHeight = var1;
            } else {
               var1 = 0;
            }
         }

         int var3 = this.mMatchConstraintMaxHeight;
         var2 = var1;
         if (var3 > 0) {
            var2 = var1;
            if (var3 < var1) {
               var2 = var3;
            }
         }
      }

      return var2;
   }

   public int getOptimizerWrapWidth() {
      int var1 = this.mWidth;
      int var2 = var1;
      if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         if (this.mMatchConstraintDefaultWidth == 1) {
            var1 = Math.max(this.mMatchConstraintMinWidth, var1);
         } else {
            var1 = this.mMatchConstraintMinWidth;
            if (var1 > 0) {
               this.mWidth = var1;
            } else {
               var1 = 0;
            }
         }

         int var3 = this.mMatchConstraintMaxWidth;
         var2 = var1;
         if (var3 > 0) {
            var2 = var1;
            if (var3 < var1) {
               var2 = var3;
            }
         }
      }

      return var2;
   }

   public ConstraintWidget getParent() {
      return this.mParent;
   }

   public ConstraintWidget getPreviousChainMember(int var1) {
      ConstraintAnchor var2;
      ConstraintAnchor var3;
      if (var1 == 0) {
         if (this.mLeft.mTarget != null) {
            var2 = this.mLeft.mTarget.mTarget;
            var3 = this.mLeft;
            if (var2 == var3) {
               return var3.mTarget.mOwner;
            }
         }
      } else if (var1 == 1 && this.mTop.mTarget != null) {
         var3 = this.mTop.mTarget.mTarget;
         var2 = this.mTop;
         if (var3 == var2) {
            return var2.mTarget.mOwner;
         }
      }

      return null;
   }

   int getRelativePositioning(int var1) {
      if (var1 == 0) {
         return this.mRelX;
      } else {
         return var1 == 1 ? this.mRelY : 0;
      }
   }

   public int getRight() {
      return this.getX() + this.mWidth;
   }

   protected int getRootX() {
      return this.mX + this.mOffsetX;
   }

   protected int getRootY() {
      return this.mY + this.mOffsetY;
   }

   public WidgetRun getRun(int var1) {
      if (var1 == 0) {
         return this.horizontalRun;
      } else {
         return var1 == 1 ? this.verticalRun : null;
      }
   }

   public int getTop() {
      return this.getY();
   }

   public String getType() {
      return this.mType;
   }

   public float getVerticalBiasPercent() {
      return this.mVerticalBiasPercent;
   }

   public ConstraintWidget getVerticalChainControlWidget() {
      boolean var1 = this.isInVerticalChain();
      ConstraintWidget var2 = null;
      if (var1) {
         var2 = this;
         ConstraintWidget var3 = null;

         while(var3 == null && var2 != null) {
            ConstraintAnchor var4 = var2.getAnchor(ConstraintAnchor.Type.TOP);
            if (var4 == null) {
               var4 = null;
            } else {
               var4 = var4.getTarget();
            }

            ConstraintWidget var6;
            if (var4 == null) {
               var6 = null;
            } else {
               var6 = var4.getOwner();
            }

            if (var6 == this.getParent()) {
               return var2;
            }

            ConstraintAnchor var5;
            if (var6 == null) {
               var5 = null;
            } else {
               var5 = var6.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            }

            if (var5 != null && var5.getOwner() != var2) {
               var3 = var2;
            } else {
               var2 = var6;
            }
         }

         var2 = var3;
      }

      return var2;
   }

   public int getVerticalChainStyle() {
      return this.mVerticalChainStyle;
   }

   public ConstraintWidget.DimensionBehaviour getVerticalDimensionBehaviour() {
      return this.mListDimensionBehaviors[1];
   }

   public int getVerticalMargin() {
      ConstraintAnchor var1 = this.mLeft;
      int var2 = 0;
      if (var1 != null) {
         var2 = 0 + this.mTop.mMargin;
      }

      int var3 = var2;
      if (this.mRight != null) {
         var3 = var2 + this.mBottom.mMargin;
      }

      return var3;
   }

   public int getVisibility() {
      return this.mVisibility;
   }

   public int getWidth() {
      return this.mVisibility == 8 ? 0 : this.mWidth;
   }

   public int getX() {
      ConstraintWidget var1 = this.mParent;
      return var1 != null && var1 instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer)var1).mPaddingLeft + this.mX : this.mX;
   }

   public int getY() {
      ConstraintWidget var1 = this.mParent;
      return var1 != null && var1 instanceof ConstraintWidgetContainer ? ((ConstraintWidgetContainer)var1).mPaddingTop + this.mY : this.mY;
   }

   public boolean hasBaseline() {
      return this.hasBaseline;
   }

   public void immediateConnect(ConstraintAnchor.Type var1, ConstraintWidget var2, ConstraintAnchor.Type var3, int var4, int var5) {
      this.getAnchor(var1).connect(var2.getAnchor(var3), var4, var5, true);
   }

   public boolean isHeightWrapContent() {
      return this.mIsHeightWrapContent;
   }

   public boolean isInHorizontalChain() {
      return this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft || this.mRight.mTarget != null && this.mRight.mTarget.mTarget == this.mRight;
   }

   public boolean isInPlaceholder() {
      return this.inPlaceholder;
   }

   public boolean isInVerticalChain() {
      return this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop || this.mBottom.mTarget != null && this.mBottom.mTarget.mTarget == this.mBottom;
   }

   public boolean isInVirtualLayout() {
      return this.mInVirtuaLayout;
   }

   public boolean isRoot() {
      boolean var1;
      if (this.mParent == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isSpreadHeight() {
      int var1 = this.mMatchConstraintDefaultHeight;
      boolean var2 = true;
      if (var1 != 0 || this.mDimensionRatio != 0.0F || this.mMatchConstraintMinHeight != 0 || this.mMatchConstraintMaxHeight != 0 || this.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         var2 = false;
      }

      return var2;
   }

   public boolean isSpreadWidth() {
      int var1 = this.mMatchConstraintDefaultWidth;
      boolean var2 = false;
      boolean var3 = var2;
      if (var1 == 0) {
         var3 = var2;
         if (this.mDimensionRatio == 0.0F) {
            var3 = var2;
            if (this.mMatchConstraintMinWidth == 0) {
               var3 = var2;
               if (this.mMatchConstraintMaxWidth == 0) {
                  var3 = var2;
                  if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var3 = true;
                  }
               }
            }
         }
      }

      return var3;
   }

   public boolean isWidthWrapContent() {
      return this.mIsWidthWrapContent;
   }

   public void reset() {
      this.mLeft.reset();
      this.mTop.reset();
      this.mRight.reset();
      this.mBottom.reset();
      this.mBaseline.reset();
      this.mCenterX.reset();
      this.mCenterY.reset();
      this.mCenter.reset();
      this.mParent = null;
      this.mCircleConstraintAngle = 0.0F;
      this.mWidth = 0;
      this.mHeight = 0;
      this.mDimensionRatio = 0.0F;
      this.mDimensionRatioSide = -1;
      this.mX = 0;
      this.mY = 0;
      this.mOffsetX = 0;
      this.mOffsetY = 0;
      this.mBaselineDistance = 0;
      this.mMinWidth = 0;
      this.mMinHeight = 0;
      float var1 = DEFAULT_BIAS;
      this.mHorizontalBiasPercent = var1;
      this.mVerticalBiasPercent = var1;
      this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
      this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
      this.mCompanionWidget = null;
      this.mContainerItemSkip = 0;
      this.mVisibility = 0;
      this.mType = null;
      this.mHorizontalWrapVisited = false;
      this.mVerticalWrapVisited = false;
      this.mHorizontalChainStyle = 0;
      this.mVerticalChainStyle = 0;
      this.mHorizontalChainFixedPosition = false;
      this.mVerticalChainFixedPosition = false;
      float[] var2 = this.mWeight;
      var2[0] = -1.0F;
      var2[1] = -1.0F;
      this.mHorizontalResolution = -1;
      this.mVerticalResolution = -1;
      int[] var3 = this.mMaxDimension;
      var3[0] = Integer.MAX_VALUE;
      var3[1] = Integer.MAX_VALUE;
      this.mMatchConstraintDefaultWidth = 0;
      this.mMatchConstraintDefaultHeight = 0;
      this.mMatchConstraintPercentWidth = 1.0F;
      this.mMatchConstraintPercentHeight = 1.0F;
      this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
      this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
      this.mMatchConstraintMinWidth = 0;
      this.mMatchConstraintMinHeight = 0;
      this.mResolvedHasRatio = false;
      this.mResolvedDimensionRatioSide = -1;
      this.mResolvedDimensionRatio = 1.0F;
      this.mOptimizerMeasurable = false;
      this.mGroupsToSolver = false;
      boolean[] var4 = this.isTerminalWidget;
      var4[0] = true;
      var4[1] = true;
      this.mInVirtuaLayout = false;
      var4 = this.mIsInBarrier;
      var4[0] = false;
      var4[1] = false;
   }

   public void resetAllConstraints() {
      this.resetAnchors();
      this.setVerticalBiasPercent(DEFAULT_BIAS);
      this.setHorizontalBiasPercent(DEFAULT_BIAS);
   }

   public void resetAnchor(ConstraintAnchor var1) {
      if (this.getParent() == null || !(this.getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer)this.getParent()).handlesInternalConstraints()) {
         ConstraintAnchor var2 = this.getAnchor(ConstraintAnchor.Type.LEFT);
         ConstraintAnchor var3 = this.getAnchor(ConstraintAnchor.Type.RIGHT);
         ConstraintAnchor var4 = this.getAnchor(ConstraintAnchor.Type.TOP);
         ConstraintAnchor var5 = this.getAnchor(ConstraintAnchor.Type.BOTTOM);
         ConstraintAnchor var6 = this.getAnchor(ConstraintAnchor.Type.CENTER);
         ConstraintAnchor var7 = this.getAnchor(ConstraintAnchor.Type.CENTER_X);
         ConstraintAnchor var8 = this.getAnchor(ConstraintAnchor.Type.CENTER_Y);
         if (var1 == var6) {
            if (var2.isConnected() && var3.isConnected() && var2.getTarget() == var3.getTarget()) {
               var2.reset();
               var3.reset();
            }

            if (var4.isConnected() && var5.isConnected() && var4.getTarget() == var5.getTarget()) {
               var4.reset();
               var5.reset();
            }

            this.mHorizontalBiasPercent = 0.5F;
            this.mVerticalBiasPercent = 0.5F;
         } else if (var1 == var7) {
            if (var2.isConnected() && var3.isConnected() && var2.getTarget().getOwner() == var3.getTarget().getOwner()) {
               var2.reset();
               var3.reset();
            }

            this.mHorizontalBiasPercent = 0.5F;
         } else if (var1 == var8) {
            if (var4.isConnected() && var5.isConnected() && var4.getTarget().getOwner() == var5.getTarget().getOwner()) {
               var4.reset();
               var5.reset();
            }

            this.mVerticalBiasPercent = 0.5F;
         } else if (var1 != var2 && var1 != var3) {
            if ((var1 == var4 || var1 == var5) && var4.isConnected() && var4.getTarget() == var5.getTarget()) {
               var6.reset();
            }
         } else if (var2.isConnected() && var2.getTarget() == var3.getTarget()) {
            var6.reset();
         }

         var1.reset();
      }
   }

   public void resetAnchors() {
      ConstraintWidget var1 = this.getParent();
      if (var1 == null || !(var1 instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer)this.getParent()).handlesInternalConstraints()) {
         int var2 = 0;

         for(int var3 = this.mAnchors.size(); var2 < var3; ++var2) {
            ((ConstraintAnchor)this.mAnchors.get(var2)).reset();
         }

      }
   }

   public void resetSolverVariables(Cache var1) {
      this.mLeft.resetSolverVariable(var1);
      this.mTop.resetSolverVariable(var1);
      this.mRight.resetSolverVariable(var1);
      this.mBottom.resetSolverVariable(var1);
      this.mBaseline.resetSolverVariable(var1);
      this.mCenter.resetSolverVariable(var1);
      this.mCenterX.resetSolverVariable(var1);
      this.mCenterY.resetSolverVariable(var1);
   }

   public void setBaselineDistance(int var1) {
      this.mBaselineDistance = var1;
      boolean var2;
      if (var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.hasBaseline = var2;
   }

   public void setCompanionWidget(Object var1) {
      this.mCompanionWidget = var1;
   }

   public void setContainerItemSkip(int var1) {
      if (var1 >= 0) {
         this.mContainerItemSkip = var1;
      } else {
         this.mContainerItemSkip = 0;
      }

   }

   public void setDebugName(String var1) {
      this.mDebugName = var1;
   }

   public void setDebugSolverName(LinearSystem var1, String var2) {
      this.mDebugName = var2;
      SolverVariable var3 = var1.createObjectVariable(this.mLeft);
      SolverVariable var4 = var1.createObjectVariable(this.mTop);
      SolverVariable var5 = var1.createObjectVariable(this.mRight);
      SolverVariable var6 = var1.createObjectVariable(this.mBottom);
      StringBuilder var7 = new StringBuilder();
      var7.append(var2);
      var7.append(".left");
      var3.setName(var7.toString());
      var7 = new StringBuilder();
      var7.append(var2);
      var7.append(".top");
      var4.setName(var7.toString());
      StringBuilder var9 = new StringBuilder();
      var9.append(var2);
      var9.append(".right");
      var5.setName(var9.toString());
      StringBuilder var10 = new StringBuilder();
      var10.append(var2);
      var10.append(".bottom");
      var6.setName(var10.toString());
      if (this.mBaselineDistance > 0) {
         var6 = var1.createObjectVariable(this.mBaseline);
         StringBuilder var8 = new StringBuilder();
         var8.append(var2);
         var8.append(".baseline");
         var6.setName(var8.toString());
      }

   }

   public void setDimension(int var1, int var2) {
      this.mWidth = var1;
      int var3 = this.mMinWidth;
      if (var1 < var3) {
         this.mWidth = var3;
      }

      this.mHeight = var2;
      var1 = this.mMinHeight;
      if (var2 < var1) {
         this.mHeight = var1;
      }

   }

   public void setDimensionRatio(float var1, int var2) {
      this.mDimensionRatio = var1;
      this.mDimensionRatioSide = var2;
   }

   public void setDimensionRatio(String var1) {
      if (var1 != null && var1.length() != 0) {
         byte var2 = -1;
         int var3 = var1.length();
         int var4 = var1.indexOf(44);
         byte var5 = 0;
         byte var6 = var2;
         int var7 = var5;
         String var8;
         if (var4 > 0) {
            var6 = var2;
            var7 = var5;
            if (var4 < var3 - 1) {
               var8 = var1.substring(0, var4);
               if (var8.equalsIgnoreCase("W")) {
                  var6 = 0;
               } else {
                  var6 = var2;
                  if (var8.equalsIgnoreCase("H")) {
                     var6 = 1;
                  }
               }

               var7 = var4 + 1;
            }
         }

         float var10;
         label88: {
            int var15 = var1.indexOf(58);
            boolean var10001;
            if (var15 >= 0 && var15 < var3 - 1) {
               var8 = var1.substring(var7, var15);
               var1 = var1.substring(var15 + 1);
               if (var8.length() > 0 && var1.length() > 0) {
                  label89: {
                     float var9;
                     try {
                        var9 = Float.parseFloat(var8);
                        var10 = Float.parseFloat(var1);
                     } catch (NumberFormatException var14) {
                        var10001 = false;
                        break label89;
                     }

                     if (var9 > 0.0F && var10 > 0.0F) {
                        if (var6 == 1) {
                           try {
                              var10 = Math.abs(var10 / var9);
                              break label88;
                           } catch (NumberFormatException var13) {
                              var10001 = false;
                           }
                        } else {
                           try {
                              var10 = Math.abs(var9 / var10);
                              break label88;
                           } catch (NumberFormatException var12) {
                              var10001 = false;
                           }
                        }
                     }
                  }
               }
            } else {
               var1 = var1.substring(var7);
               if (var1.length() > 0) {
                  try {
                     var10 = Float.parseFloat(var1);
                     break label88;
                  } catch (NumberFormatException var11) {
                     var10001 = false;
                  }
               }
            }

            var10 = 0.0F;
         }

         if (var10 > 0.0F) {
            this.mDimensionRatio = var10;
            this.mDimensionRatioSide = var6;
         }

      } else {
         this.mDimensionRatio = 0.0F;
      }
   }

   public void setFrame(int var1, int var2, int var3) {
      if (var3 == 0) {
         this.setHorizontalDimension(var1, var2);
      } else if (var3 == 1) {
         this.setVerticalDimension(var1, var2);
      }

   }

   public void setFrame(int var1, int var2, int var3, int var4) {
      int var5 = var3 - var1;
      var3 = var4 - var2;
      this.mX = var1;
      this.mY = var2;
      if (this.mVisibility == 8) {
         this.mWidth = 0;
         this.mHeight = 0;
      } else {
         var1 = var5;
         if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED) {
            var2 = this.mWidth;
            var1 = var5;
            if (var5 < var2) {
               var1 = var2;
            }
         }

         var2 = var3;
         if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
            var4 = this.mHeight;
            var2 = var3;
            if (var3 < var4) {
               var2 = var4;
            }
         }

         this.mWidth = var1;
         this.mHeight = var2;
         var1 = this.mMinHeight;
         if (var2 < var1) {
            this.mHeight = var1;
         }

         var1 = this.mWidth;
         var2 = this.mMinWidth;
         if (var1 < var2) {
            this.mWidth = var2;
         }

      }
   }

   public void setGoneMargin(ConstraintAnchor.Type var1, int var2) {
      int var3 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[var1.ordinal()];
      if (var3 != 1) {
         if (var3 != 2) {
            if (var3 != 3) {
               if (var3 == 4) {
                  this.mBottom.mGoneMargin = var2;
               }
            } else {
               this.mRight.mGoneMargin = var2;
            }
         } else {
            this.mTop.mGoneMargin = var2;
         }
      } else {
         this.mLeft.mGoneMargin = var2;
      }

   }

   public void setHasBaseline(boolean var1) {
      this.hasBaseline = var1;
   }

   public void setHeight(int var1) {
      this.mHeight = var1;
      int var2 = this.mMinHeight;
      if (var1 < var2) {
         this.mHeight = var2;
      }

   }

   public void setHeightWrapContent(boolean var1) {
      this.mIsHeightWrapContent = var1;
   }

   public void setHorizontalBiasPercent(float var1) {
      this.mHorizontalBiasPercent = var1;
   }

   public void setHorizontalChainStyle(int var1) {
      this.mHorizontalChainStyle = var1;
   }

   public void setHorizontalDimension(int var1, int var2) {
      this.mX = var1;
      var2 -= var1;
      this.mWidth = var2;
      var1 = this.mMinWidth;
      if (var2 < var1) {
         this.mWidth = var1;
      }

   }

   public void setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour var1) {
      this.mListDimensionBehaviors[0] = var1;
   }

   public void setHorizontalMatchStyle(int var1, int var2, int var3, float var4) {
      this.mMatchConstraintDefaultWidth = var1;
      this.mMatchConstraintMinWidth = var2;
      var1 = var3;
      if (var3 == Integer.MAX_VALUE) {
         var1 = 0;
      }

      this.mMatchConstraintMaxWidth = var1;
      this.mMatchConstraintPercentWidth = var4;
      if (var4 > 0.0F && var4 < 1.0F && this.mMatchConstraintDefaultWidth == 0) {
         this.mMatchConstraintDefaultWidth = 2;
      }

   }

   public void setHorizontalWeight(float var1) {
      this.mWeight[0] = var1;
   }

   protected void setInBarrier(int var1, boolean var2) {
      this.mIsInBarrier[var1] = var2;
   }

   public void setInPlaceholder(boolean var1) {
      this.inPlaceholder = var1;
   }

   public void setInVirtualLayout(boolean var1) {
      this.mInVirtuaLayout = var1;
   }

   public void setLength(int var1, int var2) {
      if (var2 == 0) {
         this.setWidth(var1);
      } else if (var2 == 1) {
         this.setHeight(var1);
      }

   }

   public void setMaxHeight(int var1) {
      this.mMaxDimension[1] = var1;
   }

   public void setMaxWidth(int var1) {
      this.mMaxDimension[0] = var1;
   }

   public void setMinHeight(int var1) {
      if (var1 < 0) {
         this.mMinHeight = 0;
      } else {
         this.mMinHeight = var1;
      }

   }

   public void setMinWidth(int var1) {
      if (var1 < 0) {
         this.mMinWidth = 0;
      } else {
         this.mMinWidth = var1;
      }

   }

   public void setOffset(int var1, int var2) {
      this.mOffsetX = var1;
      this.mOffsetY = var2;
   }

   public void setOrigin(int var1, int var2) {
      this.mX = var1;
      this.mY = var2;
   }

   public void setParent(ConstraintWidget var1) {
      this.mParent = var1;
   }

   void setRelativePositioning(int var1, int var2) {
      if (var2 == 0) {
         this.mRelX = var1;
      } else if (var2 == 1) {
         this.mRelY = var1;
      }

   }

   public void setType(String var1) {
      this.mType = var1;
   }

   public void setVerticalBiasPercent(float var1) {
      this.mVerticalBiasPercent = var1;
   }

   public void setVerticalChainStyle(int var1) {
      this.mVerticalChainStyle = var1;
   }

   public void setVerticalDimension(int var1, int var2) {
      this.mY = var1;
      var2 -= var1;
      this.mHeight = var2;
      var1 = this.mMinHeight;
      if (var2 < var1) {
         this.mHeight = var1;
      }

   }

   public void setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour var1) {
      this.mListDimensionBehaviors[1] = var1;
   }

   public void setVerticalMatchStyle(int var1, int var2, int var3, float var4) {
      this.mMatchConstraintDefaultHeight = var1;
      this.mMatchConstraintMinHeight = var2;
      var1 = var3;
      if (var3 == Integer.MAX_VALUE) {
         var1 = 0;
      }

      this.mMatchConstraintMaxHeight = var1;
      this.mMatchConstraintPercentHeight = var4;
      if (var4 > 0.0F && var4 < 1.0F && this.mMatchConstraintDefaultHeight == 0) {
         this.mMatchConstraintDefaultHeight = 2;
      }

   }

   public void setVerticalWeight(float var1) {
      this.mWeight[1] = var1;
   }

   public void setVisibility(int var1) {
      this.mVisibility = var1;
   }

   public void setWidth(int var1) {
      this.mWidth = var1;
      int var2 = this.mMinWidth;
      if (var1 < var2) {
         this.mWidth = var2;
      }

   }

   public void setWidthWrapContent(boolean var1) {
      this.mIsWidthWrapContent = var1;
   }

   public void setX(int var1) {
      this.mX = var1;
   }

   public void setY(int var1) {
      this.mY = var1;
   }

   public void setupDimensionRatio(boolean var1, boolean var2, boolean var3, boolean var4) {
      if (this.mResolvedDimensionRatioSide == -1) {
         if (var3 && !var4) {
            this.mResolvedDimensionRatioSide = 0;
         } else if (!var3 && var4) {
            this.mResolvedDimensionRatioSide = 1;
            if (this.mDimensionRatioSide == -1) {
               this.mResolvedDimensionRatio = 1.0F / this.mResolvedDimensionRatio;
            }
         }
      }

      if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
         this.mResolvedDimensionRatioSide = 1;
      } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
         this.mResolvedDimensionRatioSide = 0;
      }

      if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
         if (this.mTop.isConnected() && this.mBottom.isConnected()) {
            this.mResolvedDimensionRatioSide = 0;
         } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
            this.mResolvedDimensionRatio = 1.0F / this.mResolvedDimensionRatio;
            this.mResolvedDimensionRatioSide = 1;
         }
      }

      if (this.mResolvedDimensionRatioSide == -1) {
         if (this.mMatchConstraintMinWidth > 0 && this.mMatchConstraintMinHeight == 0) {
            this.mResolvedDimensionRatioSide = 0;
         } else if (this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMinHeight > 0) {
            this.mResolvedDimensionRatio = 1.0F / this.mResolvedDimensionRatio;
            this.mResolvedDimensionRatioSide = 1;
         }
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      String var2 = this.mType;
      String var3 = "";
      StringBuilder var4;
      if (var2 != null) {
         var4 = new StringBuilder();
         var4.append("type: ");
         var4.append(this.mType);
         var4.append(" ");
         var2 = var4.toString();
      } else {
         var2 = "";
      }

      var1.append(var2);
      var2 = var3;
      if (this.mDebugName != null) {
         var4 = new StringBuilder();
         var4.append("id: ");
         var4.append(this.mDebugName);
         var4.append(" ");
         var2 = var4.toString();
      }

      var1.append(var2);
      var1.append("(");
      var1.append(this.mX);
      var1.append(", ");
      var1.append(this.mY);
      var1.append(") - (");
      var1.append(this.mWidth);
      var1.append(" x ");
      var1.append(this.mHeight);
      var1.append(")");
      return var1.toString();
   }

   public void updateFromRuns(boolean var1, boolean var2) {
      boolean var3;
      boolean var4;
      int var5;
      int var6;
      int var7;
      int var8;
      int var9;
      label63: {
         var3 = var1 & this.horizontalRun.isResolved();
         var4 = var2 & this.verticalRun.isResolved();
         var5 = this.horizontalRun.start.value;
         var6 = this.verticalRun.start.value;
         var7 = this.horizontalRun.end.value;
         var8 = this.verticalRun.end.value;
         if (var7 - var5 >= 0 && var8 - var6 >= 0 && var5 != Integer.MIN_VALUE && var5 != Integer.MAX_VALUE && var6 != Integer.MIN_VALUE && var6 != Integer.MAX_VALUE && var7 != Integer.MIN_VALUE && var7 != Integer.MAX_VALUE && var8 != Integer.MIN_VALUE) {
            var9 = var8;
            if (var8 != Integer.MAX_VALUE) {
               break label63;
            }
         }

         var5 = 0;
         var6 = 0;
         var7 = 0;
         var9 = 0;
      }

      var8 = var7 - var5;
      var7 = var9 - var6;
      if (var3) {
         this.mX = var5;
      }

      if (var4) {
         this.mY = var6;
      }

      if (this.mVisibility == 8) {
         this.mWidth = 0;
         this.mHeight = 0;
      } else {
         if (var3) {
            var9 = var8;
            if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED) {
               var5 = this.mWidth;
               var9 = var8;
               if (var8 < var5) {
                  var9 = var5;
               }
            }

            this.mWidth = var9;
            var5 = this.mMinWidth;
            if (var9 < var5) {
               this.mWidth = var5;
            }
         }

         if (var4) {
            var9 = var7;
            if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) {
               var5 = this.mHeight;
               var9 = var7;
               if (var7 < var5) {
                  var9 = var5;
               }
            }

            this.mHeight = var9;
            var5 = this.mMinHeight;
            if (var9 < var5) {
               this.mHeight = var5;
            }
         }

      }
   }

   public void updateFromSolver(LinearSystem var1) {
      int var2 = var1.getObjectVariableValue(this.mLeft);
      int var3 = var1.getObjectVariableValue(this.mTop);
      int var4 = var1.getObjectVariableValue(this.mRight);
      int var5 = var1.getObjectVariableValue(this.mBottom);
      int var6 = var2;
      int var7 = var4;
      if (this.horizontalRun.start.resolved) {
         var6 = var2;
         var7 = var4;
         if (this.horizontalRun.end.resolved) {
            var6 = this.horizontalRun.start.value;
            var7 = this.horizontalRun.end.value;
         }
      }

      var2 = var3;
      var4 = var5;
      if (this.verticalRun.start.resolved) {
         var2 = var3;
         var4 = var5;
         if (this.verticalRun.end.resolved) {
            var2 = this.verticalRun.start.value;
            var4 = this.verticalRun.end.value;
         }
      }

      label36: {
         if (var7 - var6 >= 0 && var4 - var2 >= 0 && var6 != Integer.MIN_VALUE && var6 != Integer.MAX_VALUE && var2 != Integer.MIN_VALUE && var2 != Integer.MAX_VALUE && var7 != Integer.MIN_VALUE && var7 != Integer.MAX_VALUE && var4 != Integer.MIN_VALUE) {
            var5 = var6;
            var6 = var7;
            var7 = var4;
            if (var4 != Integer.MAX_VALUE) {
               break label36;
            }
         }

         var7 = 0;
         var5 = 0;
         var2 = 0;
         var6 = 0;
      }

      this.setFrame(var5, var2, var6, var7);
   }

   public static enum DimensionBehaviour {
      FIXED,
      MATCH_CONSTRAINT,
      MATCH_PARENT,
      WRAP_CONTENT;

      static {
         ConstraintWidget.DimensionBehaviour var0 = new ConstraintWidget.DimensionBehaviour("MATCH_PARENT", 3);
         MATCH_PARENT = var0;
      }
   }
}
