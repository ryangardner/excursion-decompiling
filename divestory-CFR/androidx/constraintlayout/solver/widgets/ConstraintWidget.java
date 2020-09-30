/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import androidx.constraintlayout.solver.widgets.analyzer.ChainRun;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
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
    public static float DEFAULT_BIAS = 0.5f;
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
    private boolean hasBaseline = false;
    public ChainRun horizontalChainRun;
    public HorizontalWidgetRun horizontalRun = new HorizontalWidgetRun(this);
    private boolean inPlaceholder;
    public boolean[] isTerminalWidget = new boolean[]{true, true};
    protected ArrayList<ConstraintAnchor> mAnchors;
    ConstraintAnchor mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
    int mBaselineDistance;
    public ConstraintAnchor mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
    boolean mBottomHasCentered;
    ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
    ConstraintAnchor mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
    private float mCircleConstraintAngle = 0.0f;
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
    public int mHorizontalResolution = -1;
    boolean mHorizontalWrapVisited;
    private boolean mInVirtuaLayout = false;
    public boolean mIsHeightWrapContent;
    private boolean[] mIsInBarrier;
    public boolean mIsWidthWrapContent;
    public ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
    boolean mLeftHasCentered;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight = 0;
    public int mMatchConstraintDefaultWidth = 0;
    public int mMatchConstraintMaxHeight = 0;
    public int mMatchConstraintMaxWidth = 0;
    public int mMatchConstraintMinHeight = 0;
    public int mMatchConstraintMinWidth = 0;
    public float mMatchConstraintPercentHeight = 1.0f;
    public float mMatchConstraintPercentWidth = 1.0f;
    private int[] mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
    protected int mMinHeight;
    protected int mMinWidth;
    protected ConstraintWidget[] mNextChainWidget;
    protected int mOffsetX;
    protected int mOffsetY;
    boolean mOptimizerMeasurable;
    public ConstraintWidget mParent;
    int mRelX;
    int mRelY;
    float mResolvedDimensionRatio = 1.0f;
    int mResolvedDimensionRatioSide = -1;
    boolean mResolvedHasRatio = false;
    public int[] mResolvedMatchConstraintDefault = new int[2];
    public ConstraintAnchor mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
    boolean mRightHasCentered;
    public ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution = -1;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    public float[] mWeight;
    int mWidth;
    protected int mX;
    protected int mY;
    public boolean measured = false;
    public WidgetRun[] run = new WidgetRun[2];
    public ChainRun verticalChainRun;
    public VerticalWidgetRun verticalRun = new VerticalWidgetRun(this);
    public int[] wrapMeasure = new int[]{0, 0, 0, 0};

    public ConstraintWidget() {
        float f;
        ConstraintAnchor constraintAnchor;
        this.mCenter = constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = f = DEFAULT_BIAS;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mOptimizerMeasurable = false;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.addAnchors();
    }

    public ConstraintWidget(int n, int n2) {
        this(0, 0, n, n2);
    }

    public ConstraintWidget(int n, int n2, int n3, int n4) {
        float f;
        ConstraintAnchor constraintAnchor;
        this.mCenter = constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = f = DEFAULT_BIAS;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mOptimizerMeasurable = false;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.mX = n;
        this.mY = n2;
        this.mWidth = n3;
        this.mHeight = n4;
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

    /*
     * Unable to fully structure code
     */
    private void applyConstraints(LinearSystem var1_1, boolean var2_2, boolean var3_3, boolean var4_4, boolean var5_5, SolverVariable var6_6, SolverVariable var7_7, DimensionBehaviour var8_8, boolean var9_9, ConstraintAnchor var10_10, ConstraintAnchor var11_11, int var12_12, int var13_13, int var14_14, int var15_15, float var16_16, boolean var17_17, boolean var18_18, boolean var19_19, boolean var20_20, int var21_21, int var22_22, int var23_23, int var24_24, float var25_25, boolean var26_26) {
        block65 : {
            block66 : {
                block82 : {
                    block83 : {
                        block84 : {
                            block70 : {
                                block72 : {
                                    block81 : {
                                        block80 : {
                                            block68 : {
                                                block73 : {
                                                    block78 : {
                                                        block79 : {
                                                            block77 : {
                                                                block75 : {
                                                                    block76 : {
                                                                        block74 : {
                                                                            block71 : {
                                                                                block69 : {
                                                                                    block67 : {
                                                                                        block63 : {
                                                                                            block64 : {
                                                                                                block62 : {
                                                                                                    var27_30 = var1_1.createObjectVariable(var10_13);
                                                                                                    var28_32 = var1_1.createObjectVariable(var11_14);
                                                                                                    var29_33 = var1_1.createObjectVariable(var10_13.getTarget());
                                                                                                    var30_35 = var1_1.createObjectVariable(var11_14.getTarget());
                                                                                                    if (LinearSystem.getMetrics() != null) {
                                                                                                        var31_36 = LinearSystem.getMetrics();
                                                                                                        ++var31_36.nonresolvedWidgets;
                                                                                                    }
                                                                                                    var32_37 = var10_13.isConnected();
                                                                                                    var33_38 = var11_14.isConnected();
                                                                                                    var34_39 = this.mCenter.isConnected();
                                                                                                    var35_40 = var32_37 != false ? 1 : 0;
                                                                                                    var36_41 = var35_40;
                                                                                                    if (var33_38) {
                                                                                                        var36_41 = var35_40 + 1;
                                                                                                    }
                                                                                                    var37_42 = var36_41;
                                                                                                    if (var34_39) {
                                                                                                        var37_42 = var36_41 + 1;
                                                                                                    }
                                                                                                    if (var17_20 != false) {
                                                                                                        var21_24 = 3;
                                                                                                    }
                                                                                                    var36_41 = (var36_41 = 1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour[var8_8.ordinal()]) == 1 || var36_41 == 2 || var36_41 == 3 || var36_41 != 4 || var21_24 == 4 ? 0 : 1;
                                                                                                    var38_43 = var21_24;
                                                                                                    if (this.mVisibility == 8) {
                                                                                                        var21_24 = 0;
                                                                                                        var36_41 = 0;
                                                                                                    } else {
                                                                                                        var21_24 = var13_16;
                                                                                                    }
                                                                                                    if (var26_29 != false) {
                                                                                                        if (!(var32_37 || var33_38 || var34_39)) {
                                                                                                            var1_1.addEquality(var27_30, var12_15);
                                                                                                        } else if (var32_37 && !var33_38) {
                                                                                                            var1_1.addEquality(var27_30, var29_33, var10_13.getMargin(), 8);
                                                                                                        }
                                                                                                    }
                                                                                                    var8_9 = var30_35;
                                                                                                    if (var36_41 != 0) break block62;
                                                                                                    if (var9_12 != false) {
                                                                                                        var1_1.addEquality(var28_32, var27_30, 0, 3);
                                                                                                        if (var14_17 > 0) {
                                                                                                            var1_1.addGreaterThan(var28_32, var27_30, (int)var14_17, 8);
                                                                                                        }
                                                                                                        if (var15_18 < Integer.MAX_VALUE) {
                                                                                                            var1_1.addLowerThan(var28_32, var27_30, var15_18, 8);
                                                                                                        }
                                                                                                    } else {
                                                                                                        var1_1.addEquality(var28_32, var27_30, var21_24, 8);
                                                                                                    }
                                                                                                    var12_15 = var24_27;
                                                                                                    var24_27 = var23_26;
                                                                                                    var35_40 = var36_41;
                                                                                                    break block63;
                                                                                                }
                                                                                                if (var37_42 == 2 || var17_20 != false || var38_43 != 1 && var38_43 != 0) break block64;
                                                                                                var12_15 = var13_16 = Math.max(var23_26, var21_24);
                                                                                                if (var24_27 > 0) {
                                                                                                    var12_15 = Math.min(var24_27, var13_16);
                                                                                                }
                                                                                                var1_1.addEquality(var28_32, var27_30, var12_15, 8);
                                                                                                var13_16 = var23_26;
                                                                                                var12_15 = var24_27;
                                                                                                ** GOTO lbl108
                                                                                            }
                                                                                            var12_15 = var23_26;
                                                                                            if (var23_26 == -2) {
                                                                                                var12_15 = var21_24;
                                                                                            }
                                                                                            var13_16 = var24_27 == -2 ? var21_24 : var24_27;
                                                                                            var23_26 = var21_24;
                                                                                            if (var21_24 > 0) {
                                                                                                var23_26 = var21_24;
                                                                                                if (var38_43 != 1) {
                                                                                                    var23_26 = 0;
                                                                                                }
                                                                                            }
                                                                                            var15_18 = var23_26;
                                                                                            if (var12_15 > 0) {
                                                                                                var1_1.addGreaterThan(var28_32, var27_30, var12_15, 8);
                                                                                                var15_18 = Math.max(var23_26, var12_15);
                                                                                            }
                                                                                            if (var13_16 > 0) {
                                                                                                var21_24 = var3_3 != false && var38_43 == 1 ? 0 : 1;
                                                                                                if (var21_24 != 0) {
                                                                                                    var1_1.addLowerThan(var28_32, var27_30, var13_16, 8);
                                                                                                }
                                                                                                var15_18 = Math.min(var15_18, var13_16);
                                                                                            }
                                                                                            if (var38_43 == 1) {
                                                                                                if (var3_3) {
                                                                                                    var1_1.addEquality(var28_32, var27_30, var15_18, 8);
                                                                                                } else if (var18_21 != false) {
                                                                                                    var1_1.addEquality(var28_32, var27_30, var15_18, 5);
                                                                                                    var1_1.addLowerThan(var28_32, var27_30, var15_18, 8);
                                                                                                } else {
                                                                                                    var1_1.addEquality(var28_32, var27_30, var15_18, 5);
                                                                                                    var1_1.addLowerThan(var28_32, var27_30, var15_18, 8);
                                                                                                }
                                                                                                var24_27 = var12_15;
                                                                                                var12_15 = var13_16;
                                                                                                var35_40 = var36_41;
                                                                                            } else if (var38_43 == 2) {
                                                                                                if (var10_13.getType() != ConstraintAnchor.Type.TOP && var10_13.getType() != ConstraintAnchor.Type.BOTTOM) {
                                                                                                    var30_35 = var1_1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                                                                                                    var31_36 = var1_1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                                                                                                } else {
                                                                                                    var30_35 = var1_1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
                                                                                                    var31_36 = var1_1.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                                                                                                }
                                                                                                var39_44 = var1_1.createRow();
                                                                                                var15_18 = var12_15;
                                                                                                var1_1.addConstraint(var39_44.createRowDimensionRatio(var28_32, var27_30, (SolverVariable)var31_36, (SolverVariable)var30_35, (float)var25_28));
                                                                                                var12_15 = var13_16;
                                                                                                var13_16 = var15_18;
lbl108: // 2 sources:
                                                                                                var35_40 = 0;
                                                                                                var24_27 = var13_16;
                                                                                            } else {
                                                                                                var24_27 = var12_15;
                                                                                                var12_15 = var13_16;
                                                                                                var5_5 = true;
                                                                                                var35_40 = var36_41;
                                                                                            }
                                                                                        }
                                                                                        var36_41 = var38_43;
                                                                                        var39_44 = var8_9;
                                                                                        var8_10 = var29_33;
                                                                                        if (var26_29 == false || var18_21 != false) break block65;
                                                                                        if (!var32_37 && !var33_38 && !var34_39 || var32_37 && !var33_38) break block66;
                                                                                        if (var32_37 || !var33_38) break block67;
                                                                                        var1_1.addEquality(var28_32, (SolverVariable)var39_44, -var11_14.getMargin(), 8);
                                                                                        if (var3_3) {
                                                                                            var1_1.addGreaterThan(var27_30, (SolverVariable)var6_6, 0, 5);
                                                                                        }
                                                                                        break block66;
                                                                                    }
                                                                                    if (!var32_37 || !var33_38) break block66;
                                                                                    var40_45 = var10_13.mTarget.mOwner;
                                                                                    var30_35 = var11_14.mTarget.mOwner;
                                                                                    var41_46 = this.getParent();
                                                                                    var38_43 = 6;
                                                                                    if (var35_40 == 0) break block68;
                                                                                    var13_16 = var36_41;
                                                                                    if (var13_16 != 0) break block69;
                                                                                    if (var12_15 == 0 && var24_27 == 0) {
                                                                                        var23_26 = 0;
                                                                                        var12_15 = 1;
                                                                                        var13_16 = 8;
                                                                                        var15_18 = 8;
                                                                                    } else {
                                                                                        var23_26 = 1;
                                                                                        var12_15 = 0;
                                                                                        var13_16 = 5;
                                                                                        var15_18 = 5;
                                                                                    }
                                                                                    if (var40_45 instanceof Barrier || var30_35 instanceof Barrier) {
                                                                                        var15_18 = 4;
                                                                                    }
                                                                                    var37_42 = var13_16;
                                                                                    var21_24 = 0;
                                                                                    var13_16 = 6;
                                                                                    var22_25 = var12_15;
                                                                                    var12_15 = var37_42;
                                                                                    break block70;
                                                                                }
                                                                                if (var13_16 != 1) break block71;
                                                                                var23_26 = 1;
                                                                                var22_25 = 0;
                                                                                var21_24 = 1;
                                                                                var12_15 = 8;
                                                                                break block72;
                                                                            }
                                                                            if (var13_16 != 3) break block73;
                                                                            if (this.mResolvedDimensionRatioSide != -1) break block74;
                                                                            var12_15 = var19_22 != false ? (var3_3 ? 5 : 4) : 8;
                                                                            var15_18 = 8;
                                                                            break block75;
                                                                        }
                                                                        if (var17_20 == false) break block76;
                                                                        var12_15 = var22_25 != 2 && var22_25 != 1 ? 0 : 1;
                                                                        if (var12_15 == 0) {
                                                                            var13_16 = 8;
                                                                            var12_15 = 5;
                                                                        } else {
                                                                            var13_16 = 5;
                                                                            var12_15 = 4;
                                                                        }
                                                                        var15_18 = var13_16;
                                                                        var37_42 = var12_15;
                                                                        var13_16 = 6;
                                                                        var23_26 = 1;
                                                                        var22_25 = 1;
                                                                        var21_24 = 1;
                                                                        var12_15 = var15_18;
                                                                        var15_18 = var37_42;
                                                                        break block70;
                                                                    }
                                                                    if (var12_15 <= 0) break block77;
                                                                    var12_15 = 6;
                                                                    var15_18 = 5;
                                                                }
                                                                var21_24 = 1;
                                                                var22_25 = 1;
                                                                var23_26 = 1;
                                                                var37_42 = 5;
                                                                var13_16 = var12_15;
                                                                var12_15 = var15_18;
                                                                var15_18 = var37_42;
                                                                break block70;
                                                            }
                                                            if (var12_15 != 0 || var24_27 != 0) break block78;
                                                            if (var19_22 != false) break block79;
                                                            var13_16 = 6;
                                                            var23_26 = 1;
                                                            var22_25 = 1;
                                                            var21_24 = 1;
                                                            var12_15 = 5;
                                                            var15_18 = 8;
                                                            break block70;
                                                        }
                                                        var12_15 = var40_45 != var41_46 && var30_35 != var41_46 ? 4 : 5;
                                                        var23_26 = 1;
                                                        var22_25 = 1;
                                                        var21_24 = 1;
                                                        break block72;
                                                    }
                                                    var22_25 = 1;
                                                    break block80;
                                                }
                                                var23_26 = 0;
                                                var22_25 = 0;
                                                var21_24 = 0;
                                                break block81;
                                            }
                                            var22_25 = 0;
                                        }
                                        var23_26 = 1;
                                        var21_24 = 1;
                                    }
                                    var12_15 = 5;
                                }
                                var13_16 = 6;
                                var15_18 = 4;
                            }
                            var37_42 = var36_41;
                            if (var21_24 != 0 && var8_10 == var39_44 && var40_45 != var41_46) {
                                var21_24 = 0;
                                var36_41 = 0;
                            } else {
                                var36_41 = 1;
                            }
                            if (var23_26 != 0) {
                                if (this.mVisibility == 8) {
                                    var13_16 = 4;
                                }
                                var1_1.addCentering(var27_30, var8_10, var10_13.getMargin(), (float)var16_19, (SolverVariable)var39_44, var28_32, var11_14.getMargin(), var13_16);
                            }
                            var29_34 = var8_10;
                            var31_36 = var27_30;
                            var8_11 = var41_46;
                            var13_16 = 1;
                            if (this.mVisibility == 8) {
                                return;
                            }
                            if (var21_24 != 0) {
                                if (var3_3 && var29_34 != var39_44 && var35_40 == 0 && (var40_45 instanceof Barrier || var30_35 instanceof Barrier)) {
                                    var12_15 = 6;
                                }
                                var1_1.addGreaterThan((SolverVariable)var31_36, var29_34, var10_13.getMargin(), var12_15);
                                var1_1.addLowerThan(var28_32, (SolverVariable)var39_44, -var11_14.getMargin(), var12_15);
                            }
                            if (var3_3 && var20_23 != false && !(var40_45 instanceof Barrier) && !(var30_35 instanceof Barrier)) {
                                var15_18 = 6;
                                var12_15 = 6;
                                var21_24 = var13_16;
                            } else {
                                var13_16 = var12_15;
                                var12_15 = var15_18;
                                var21_24 = var36_41;
                                var15_18 = var13_16;
                            }
                            if (var21_24 == 0) break block82;
                            if (var22_25 != 0 && (var19_22 == false || var4_4)) {
                                var27_31 = var8_11;
                                var13_16 = var38_43;
                                if (var40_45 != var27_31) {
                                    var13_16 = var30_35 == var27_31 ? var38_43 : var12_15;
                                }
                                if (var40_45 instanceof Guideline || var30_35 instanceof Guideline) {
                                    var13_16 = 5;
                                }
                                if (var40_45 instanceof Barrier || var30_35 instanceof Barrier) {
                                    var13_16 = 5;
                                }
                                if (var19_22 != false) {
                                    var13_16 = 5;
                                }
                                var12_15 = Math.max(var13_16, var12_15);
                            }
                            if (!var3_3) break block83;
                            var12_15 = var13_16 = Math.min(var15_18, var12_15);
                            if (var17_20 == false) break block83;
                            var12_15 = var13_16;
                            if (var19_22 != false) break block83;
                            if (var40_45 == var8_11) break block84;
                            var12_15 = var13_16;
                            if (var30_35 != var8_11) break block83;
                        }
                        var12_15 = 4;
                    }
                    var1_1.addEquality((SolverVariable)var31_36, var29_34, var10_13.getMargin(), var12_15);
                    var1_1.addEquality(var28_32, (SolverVariable)var39_44, -var11_14.getMargin(), var12_15);
                }
                if (var3_3) {
                    var12_15 = var6_6 == var29_34 ? var10_13.getMargin() : 0;
                    if (var29_34 != var6_6) {
                        var1_1.addGreaterThan((SolverVariable)var31_36, (SolverVariable)var6_6, var12_15, 5);
                    }
                }
                if (var3_3 && var35_40 != 0 && var14_17 == false && var24_27 == 0) {
                    if (var35_40 != 0 && var37_42 == 3) {
                        var1_1.addGreaterThan(var28_32, (SolverVariable)var31_36, 0, 8);
                    } else {
                        var1_1.addGreaterThan(var28_32, (SolverVariable)var31_36, 0, 5);
                    }
                }
            }
            if (var3_3 == false) return;
            if (var5_5 == false) return;
            var12_15 = var11_14.mTarget != null ? var11_14.getMargin() : 0;
            if (var39_44 == var7_7) return;
            var1_1.addGreaterThan(var7_7, var28_32, var12_15, 5);
            return;
        }
        if (var37_42 >= 2) return;
        if (var3_3 == false) return;
        if (var5_5 == false) return;
        var1_1.addGreaterThan(var27_30, (SolverVariable)var6_6, 0, 8);
        var13_16 = !var2_2 && this.mBaseline.mTarget != null ? 0 : 1;
        var12_15 = var13_16;
        if (!var2_2) {
            var12_15 = var13_16;
            if (this.mBaseline.mTarget != null) {
                var6_6 = this.mBaseline.mTarget.mOwner;
                var12_15 = var6_6.mDimensionRatio != 0.0f && var6_6.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT && var6_6.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT ? 1 : 0;
            }
        }
        if (var12_15 == 0) return;
        var1_1.addGreaterThan(var7_7, var28_32, 0, 8);
    }

    private boolean isChainHead(int n) {
        ConstraintAnchor[] arrconstraintAnchor = this.mListAnchors[n *= 2].mTarget;
        boolean bl = true;
        if (arrconstraintAnchor == null) return false;
        ConstraintAnchor constraintAnchor = this.mListAnchors[n].mTarget.mTarget;
        arrconstraintAnchor = this.mListAnchors;
        if (constraintAnchor == arrconstraintAnchor[n]) return false;
        if (arrconstraintAnchor[++n].mTarget == null) return false;
        if (this.mListAnchors[n].mTarget.mTarget != this.mListAnchors[n]) return false;
        return bl;
    }

    boolean addFirst() {
        if (this instanceof VirtualLayout) return true;
        if (this instanceof Guideline) return true;
        return false;
    }

    /*
     * Unable to fully structure code
     */
    public void addToSolver(LinearSystem var1_1) {
        block51 : {
            block50 : {
                block46 : {
                    block48 : {
                        block49 : {
                            block47 : {
                                var2_2 = this;
                                var3_3 = var1_1.createObjectVariable(var2_2.mLeft);
                                var4_4 = var1_1.createObjectVariable(var2_2.mRight);
                                var5_5 = var1_1.createObjectVariable(var2_2.mTop);
                                var6_6 = var1_1.createObjectVariable(var2_2.mBottom);
                                var7_7 = var1_1.createObjectVariable(var2_2.mBaseline);
                                if (LinearSystem.sMetrics != null) {
                                    var8_11 = LinearSystem.sMetrics;
                                    ++var8_11.widgets;
                                }
                                if (var2_2.horizontalRun.start.resolved && var2_2.horizontalRun.end.resolved && var2_2.verticalRun.start.resolved && var2_2.verticalRun.end.resolved) {
                                    if (LinearSystem.sMetrics != null) {
                                        var8_12 = LinearSystem.sMetrics;
                                        ++var8_12.graphSolved;
                                    }
                                    var1_1.addEquality(var3_3, var2_2.horizontalRun.start.value);
                                    var1_1.addEquality((SolverVariable)var4_4, var2_2.horizontalRun.end.value);
                                    var1_1.addEquality((SolverVariable)var5_5, var2_2.verticalRun.start.value);
                                    var1_1.addEquality(var6_6, var2_2.verticalRun.end.value);
                                    var1_1.addEquality(var7_7, var2_2.verticalRun.baseline.value);
                                    var8_14 = var2_2.mParent;
                                    if (var8_14 == null) return;
                                    var9_34 = var8_14 != null && var8_14.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT;
                                    var8_15 = var2_2.mParent;
                                    var10_36 = var8_15 != null && var8_15.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT;
                                    if (var9_34 && var2_2.isTerminalWidget[0] && !this.isInHorizontalChain()) {
                                        var1_1.addGreaterThan(var1_1.createObjectVariable(var2_2.mParent.mRight), (SolverVariable)var4_4, 0, 8);
                                    }
                                    if (var10_36 == false) return;
                                    if (var2_2.isTerminalWidget[1] == false) return;
                                    if (this.isInVerticalChain() != false) return;
                                    var1_1.addGreaterThan(var1_1.createObjectVariable(var2_2.mParent.mBottom), var6_6, 0, 8);
                                    return;
                                }
                                if (LinearSystem.sMetrics != null) {
                                    var8_16 = LinearSystem.sMetrics;
                                    ++var8_16.linearSolved;
                                }
                                if ((var8_18 = var2_2.mParent) != null) {
                                    var11_38 = var8_18 != null && var8_18.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT;
                                    var8_19 = var2_2.mParent;
                                    var12_39 = var8_19 != null && var8_19.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT;
                                    if (var2_2.isChainHead(0)) {
                                        ((ConstraintWidgetContainer)var2_2.mParent).addChain((ConstraintWidget)var2_2, 0);
                                        var13_40 = true;
                                    } else {
                                        var13_40 = this.isInHorizontalChain();
                                    }
                                    if (var2_2.isChainHead(1)) {
                                        ((ConstraintWidgetContainer)var2_2.mParent).addChain((ConstraintWidget)var2_2, 1);
                                        var14_41 = true;
                                    } else {
                                        var14_41 = this.isInVerticalChain();
                                    }
                                    if (!var13_40 && var11_38 && var2_2.mVisibility != 8 && var2_2.mLeft.mTarget == null && var2_2.mRight.mTarget == null) {
                                        var1_1.addGreaterThan(var1_1.createObjectVariable(var2_2.mParent.mRight), (SolverVariable)var4_4, 0, 1);
                                    }
                                    if (!var14_41 && var12_39 && var2_2.mVisibility != 8 && var2_2.mTop.mTarget == null && var2_2.mBottom.mTarget == null && var2_2.mBaseline == null) {
                                        var1_1.addGreaterThan(var1_1.createObjectVariable(var2_2.mParent.mBottom), var6_6, 0, 1);
                                    }
                                    var15_42 = var14_41;
                                    var14_41 = var11_38;
                                } else {
                                    var12_39 = false;
                                    var14_41 = false;
                                    var15_42 = false;
                                    var13_40 = false;
                                }
                                var10_37 = var2_2.mWidth;
                                var16_43 = var2_2.mMinWidth;
                                var9_35 = var10_37;
                                if (var10_37 < var16_43) {
                                    var9_35 = var16_43;
                                }
                                var16_43 = var2_2.mHeight;
                                var17_44 = var2_2.mMinHeight;
                                var10_37 = var16_43;
                                if (var16_43 < var17_44) {
                                    var10_37 = var17_44;
                                }
                                var11_38 = var2_2.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT;
                                var18_45 = var2_2.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT;
                                var2_2.mResolvedDimensionRatioSide = var2_2.mDimensionRatioSide;
                                var2_2.mResolvedDimensionRatio = var19_46 = var2_2.mDimensionRatio;
                                var17_44 = var2_2.mMatchConstraintDefaultWidth;
                                var20_47 = var2_2.mMatchConstraintDefaultHeight;
                                if (!(var19_46 > 0.0f) || var2_2.mVisibility == 8) break block46;
                                var16_43 = var17_44;
                                if (var2_2.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    var16_43 = var17_44;
                                    if (var17_44 == 0) {
                                        var16_43 = 3;
                                    }
                                }
                                var17_44 = var20_47;
                                if (var2_2.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    var17_44 = var20_47;
                                    if (var20_47 == 0) {
                                        var17_44 = 3;
                                    }
                                }
                                if (var2_2.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT || var2_2.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT || var16_43 != 3 || var17_44 != 3) break block47;
                                var2_2.setupDimensionRatio(var14_41, var12_39, var11_38, var18_45);
                                var20_47 = var10_37;
                                ** GOTO lbl-1000
                            }
                            if (var2_2.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT || var16_43 != 3) break block48;
                            var2_2.mResolvedDimensionRatioSide = 0;
                            var21_48 = (int)(var2_2.mResolvedDimensionRatio * (float)var2_2.mHeight);
                            var8_21 = var2_2.mListDimensionBehaviors[1];
                            var22_49 = DimensionBehaviour.MATCH_CONSTRAINT;
                            var9_35 = var10_37;
                            var20_47 = var17_44;
                            if (var8_21 == var22_49) break block49;
                            var11_38 = false;
                            var17_44 = 4;
                            var10_37 = var21_48;
                            var16_43 = var20_47;
                            break block50;
                        }
                        var10_37 = var21_48;
                        ** GOTO lbl126
                    }
                    var20_47 = var10_37;
                    if (var2_2.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) ** GOTO lbl-1000
                    var20_47 = var10_37;
                    if (var17_44 != 3) ** GOTO lbl-1000
                    var2_2.mResolvedDimensionRatioSide = 1;
                    if (var2_2.mDimensionRatioSide == -1) {
                        var2_2.mResolvedDimensionRatio = 1.0f / var2_2.mResolvedDimensionRatio;
                    }
                    var20_47 = var10_37 = (int)(var2_2.mResolvedDimensionRatio * (float)var2_2.mWidth);
                    if (var2_2.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
                        var20_47 = var10_37;
                        var17_44 = var16_43;
                        var11_38 = false;
                        var16_43 = 4;
                        var10_37 = var9_35;
                        var9_35 = var20_47;
                    } else lbl-1000: // 4 sources:
                    {
                        var10_37 = var9_35;
                        var9_35 = var20_47;
lbl126: // 2 sources:
                        var20_47 = var16_43;
                        var16_43 = var17_44;
                        var11_38 = true;
                        var17_44 = var20_47;
                    }
                    break block50;
                }
                var16_43 = var20_47;
                var20_47 = var9_35;
                var11_38 = false;
                var9_35 = var10_37;
                var10_37 = var20_47;
            }
            var8_23 = var2_2.mResolvedMatchConstraintDefault;
            var8_23[0] = var17_44;
            var8_23[1] = var16_43;
            var2_2.mResolvedHasRatio = var11_38;
            var18_45 = var11_38 != false && ((var20_47 = var2_2.mResolvedDimensionRatioSide) == 0 || var20_47 == -1);
            var23_62 = var2_2.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT && var2_2 instanceof ConstraintWidgetContainer != false;
            if (var23_62) {
                var10_37 = 0;
            }
            var24_63 = var2_2.mCenter.isConnected() ^ true;
            var8_24 = var2_2.mIsInBarrier;
            var25_64 = var8_24[0];
            var26_65 = var8_24[1];
            var20_47 = var2_2.mHorizontalResolution;
            var27_66 = null;
            if (var20_47 != 2) {
                if (var2_2.horizontalRun.start.resolved && var2_2.horizontalRun.end.resolved) {
                    var1_1.addEquality(var3_3, var2_2.horizontalRun.start.value);
                    var1_1.addEquality((SolverVariable)var4_4, var2_2.horizontalRun.end.value);
                    if (var2_2.mParent != null && var14_41 && var2_2.isTerminalWidget[0] && !this.isInHorizontalChain()) {
                        var1_1.addGreaterThan(var1_1.createObjectVariable(var2_2.mParent.mRight), (SolverVariable)var4_4, 0, 8);
                    }
                } else {
                    var8_25 = var2_2.mParent;
                    if (var8_25 != null) {
                        var8_26 = var1_1.createObjectVariable(var8_25.mRight);
                    } else {
                        var8_27 = null;
                    }
                    var22_50 = var2_2.mParent;
                    if (var22_50 != null) {
                        var22_51 = var1_1.createObjectVariable(var22_50.mLeft);
                    } else {
                        var22_52 = null;
                    }
                    this.applyConstraints(var1_1, true, var14_41, var12_39, var2_2.isTerminalWidget[0], (SolverVariable)var22_53, (SolverVariable)var8_28, var2_2.mListDimensionBehaviors[0], var23_62, var2_2.mLeft, var2_2.mRight, var2_2.mX, var10_37, var2_2.mMinWidth, var2_2.mMaxDimension[0], var2_2.mHorizontalBiasPercent, var18_45, var13_40, var15_42, var25_64, var17_44, var16_43, var2_2.mMatchConstraintMinWidth, var2_2.mMatchConstraintMaxWidth, var2_2.mMatchConstraintPercentWidth, var24_63);
                }
            }
            var2_2 = var4_4;
            var22_55 = var5_5;
            var8_30 = var6_6;
            var23_62 = var11_38;
            var6_6 = var7_7;
            var25_64 = var12_39;
            var4_4 = this;
            if (var4_4.verticalRun.start.resolved && var4_4.verticalRun.end.resolved) {
                var10_37 = var4_4.verticalRun.start.value;
                var5_5 = var1_1;
                var5_5.addEquality(var22_55, var10_37);
                var10_37 = var4_4.verticalRun.end.value;
                var7_8 = var8_30;
                var5_5.addEquality(var7_8, var10_37);
                var5_5.addEquality(var6_6, var4_4.verticalRun.baseline.value);
                var28_67 = var4_4.mParent;
                if (var28_67 != null && !var15_42 && var25_64 && var4_4.isTerminalWidget[1]) {
                    var5_5.addGreaterThan(var5_5.createObjectVariable(var28_67.mBottom), var7_8, 0, 8);
                }
                var10_37 = 0;
            } else {
                var10_37 = 1;
            }
            var5_5 = var1_1;
            var7_10 = var22_55;
            var28_67 = var6_6;
            if (var4_4.mVerticalResolution == 2) {
                var10_37 = 0;
            }
            if (var10_37 == 0) break block51;
            var11_38 = var4_4.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT && var4_4 instanceof ConstraintWidgetContainer != false;
            if (var11_38) {
                var9_35 = 0;
            }
            var12_39 = var23_62 != false && ((var10_37 = var4_4.mResolvedDimensionRatioSide) == 1 || var10_37 == -1);
            var22_56 = var4_4.mParent;
            if (var22_56 != null) {
                var22_57 = var5_5.createObjectVariable(var22_56.mBottom);
            } else {
                var22_58 = null;
            }
            var29_68 = var4_4.mParent;
            var6_6 = var27_66;
            if (var29_68 != null) {
                var6_6 = var5_5.createObjectVariable(var29_68.mTop);
            }
            if (var4_4.mBaselineDistance <= 0 && var4_4.mVisibility != 8) ** GOTO lbl223
            var5_5.addEquality((SolverVariable)var28_67, var7_10, this.getBaselineDistance(), 8);
            if (var4_4.mBaseline.mTarget != null) {
                var5_5.addEquality((SolverVariable)var28_67, var5_5.createObjectVariable(var4_4.mBaseline.mTarget), 0, 8);
                if (var25_64) {
                    var5_5.addGreaterThan((SolverVariable)var22_59, var5_5.createObjectVariable(var4_4.mBottom), 0, 5);
                }
                var18_45 = false;
            } else {
                if (var4_4.mVisibility == 8) {
                    var5_5.addEquality((SolverVariable)var28_67, var7_10, 0, 8);
                }
lbl223: // 4 sources:
                var18_45 = var24_63;
            }
            this.applyConstraints(var1_1, false, var25_64, var14_41, var4_4.isTerminalWidget[1], var6_6, (SolverVariable)var22_59, var4_4.mListDimensionBehaviors[1], var11_38, var4_4.mTop, var4_4.mBottom, var4_4.mY, var9_35, var4_4.mMinHeight, var4_4.mMaxDimension[1], var4_4.mVerticalBiasPercent, var12_39, var15_42, var13_40, var26_65, var16_43, var17_44, var4_4.mMatchConstraintMinHeight, var4_4.mMatchConstraintMaxHeight, var4_4.mMatchConstraintPercentHeight, var18_45);
        }
        var22_61 = var8_30;
        if (var23_62) {
            var8_31 = this;
            if (var8_31.mResolvedDimensionRatioSide == 1) {
                var1_1.addRatio(var22_61, var7_10, (SolverVariable)var2_2, var3_3, var8_31.mResolvedDimensionRatio, 8);
            } else {
                var1_1.addRatio((SolverVariable)var2_2, var3_3, var22_61, var7_10, var8_31.mResolvedDimensionRatio, 8);
            }
        }
        var8_33 = this;
        if (var8_33.mCenter.isConnected() == false) return;
        var1_1.addCenterPoint(var8_33, var8_33.mCenter.getTarget().getOwner(), (float)Math.toRadians(var8_33.mCircleConstraintAngle + 90.0f), var8_33.mCenter.getMargin());
    }

    public boolean allowedInBarrier() {
        if (this.mVisibility == 8) return false;
        return true;
    }

    public void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2) {
        this.connect(type, constraintWidget, type2, 0);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public void connect(ConstraintAnchor.Type object, ConstraintWidget object2, ConstraintAnchor.Type object3, int n) {
        int n2;
        ConstraintAnchor constraintAnchor;
        block26 : {
            block27 : {
                block28 : {
                    block25 : {
                        if (object == ConstraintAnchor.Type.CENTER) {
                            if (object3 == ConstraintAnchor.Type.CENTER) {
                                ConstraintAnchor constraintAnchor2 = this.getAnchor(ConstraintAnchor.Type.LEFT);
                                ConstraintAnchor constraintAnchor3 = this.getAnchor(ConstraintAnchor.Type.RIGHT);
                                object = this.getAnchor(ConstraintAnchor.Type.TOP);
                                object3 = this.getAnchor(ConstraintAnchor.Type.BOTTOM);
                                boolean bl = true;
                                if (constraintAnchor2 != null && constraintAnchor2.isConnected() || constraintAnchor3 != null && constraintAnchor3.isConnected()) {
                                    n = 0;
                                } else {
                                    this.connect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)object2, ConstraintAnchor.Type.LEFT, 0);
                                    this.connect(ConstraintAnchor.Type.RIGHT, (ConstraintWidget)object2, ConstraintAnchor.Type.RIGHT, 0);
                                    n = 1;
                                }
                                if (object != null && ((ConstraintAnchor)object).isConnected() || object3 != null && ((ConstraintAnchor)object3).isConnected()) {
                                    bl = false;
                                } else {
                                    this.connect(ConstraintAnchor.Type.TOP, (ConstraintWidget)object2, ConstraintAnchor.Type.TOP, 0);
                                    this.connect(ConstraintAnchor.Type.BOTTOM, (ConstraintWidget)object2, ConstraintAnchor.Type.BOTTOM, 0);
                                }
                                if (n != 0 && bl) {
                                    this.getAnchor(ConstraintAnchor.Type.CENTER).connect(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.CENTER), 0);
                                    return;
                                }
                                if (n != 0) {
                                    this.getAnchor(ConstraintAnchor.Type.CENTER_X).connect(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.CENTER_X), 0);
                                    return;
                                }
                                if (!bl) return;
                                this.getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.CENTER_Y), 0);
                                return;
                            }
                            if (object3 != ConstraintAnchor.Type.LEFT && object3 != ConstraintAnchor.Type.RIGHT) {
                                if (object3 != ConstraintAnchor.Type.TOP) {
                                    if (object3 != ConstraintAnchor.Type.BOTTOM) return;
                                }
                                this.connect(ConstraintAnchor.Type.TOP, (ConstraintWidget)object2, (ConstraintAnchor.Type)((Object)object3), 0);
                                this.connect(ConstraintAnchor.Type.BOTTOM, (ConstraintWidget)object2, (ConstraintAnchor.Type)((Object)object3), 0);
                                this.getAnchor(ConstraintAnchor.Type.CENTER).connect(((ConstraintWidget)object2).getAnchor((ConstraintAnchor.Type)((Object)object3)), 0);
                                return;
                            }
                            this.connect(ConstraintAnchor.Type.LEFT, (ConstraintWidget)object2, (ConstraintAnchor.Type)((Object)object3), 0);
                            object = ConstraintAnchor.Type.RIGHT;
                            this.connect((ConstraintAnchor.Type)((Object)object), (ConstraintWidget)object2, (ConstraintAnchor.Type)((Object)object3), 0);
                            this.getAnchor(ConstraintAnchor.Type.CENTER).connect(((ConstraintWidget)object2).getAnchor((ConstraintAnchor.Type)((Object)object3)), 0);
                            return;
                        }
                        if (object == ConstraintAnchor.Type.CENTER_X && (object3 == ConstraintAnchor.Type.LEFT || object3 == ConstraintAnchor.Type.RIGHT)) {
                            object = this.getAnchor(ConstraintAnchor.Type.LEFT);
                            object2 = ((ConstraintWidget)object2).getAnchor((ConstraintAnchor.Type)((Object)object3));
                            object3 = this.getAnchor(ConstraintAnchor.Type.RIGHT);
                            ((ConstraintAnchor)object).connect((ConstraintAnchor)object2, 0);
                            ((ConstraintAnchor)object3).connect((ConstraintAnchor)object2, 0);
                            this.getAnchor(ConstraintAnchor.Type.CENTER_X).connect((ConstraintAnchor)object2, 0);
                            return;
                        }
                        if (object == ConstraintAnchor.Type.CENTER_Y && (object3 == ConstraintAnchor.Type.TOP || object3 == ConstraintAnchor.Type.BOTTOM)) {
                            object = ((ConstraintWidget)object2).getAnchor((ConstraintAnchor.Type)((Object)object3));
                            this.getAnchor(ConstraintAnchor.Type.TOP).connect((ConstraintAnchor)object, 0);
                            this.getAnchor(ConstraintAnchor.Type.BOTTOM).connect((ConstraintAnchor)object, 0);
                            this.getAnchor(ConstraintAnchor.Type.CENTER_Y).connect((ConstraintAnchor)object, 0);
                            return;
                        }
                        if (object == ConstraintAnchor.Type.CENTER_X && object3 == ConstraintAnchor.Type.CENTER_X) {
                            this.getAnchor(ConstraintAnchor.Type.LEFT).connect(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.LEFT), 0);
                            this.getAnchor(ConstraintAnchor.Type.RIGHT).connect(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.RIGHT), 0);
                            this.getAnchor(ConstraintAnchor.Type.CENTER_X).connect(((ConstraintWidget)object2).getAnchor((ConstraintAnchor.Type)((Object)object3)), 0);
                            return;
                        }
                        if (object == ConstraintAnchor.Type.CENTER_Y && object3 == ConstraintAnchor.Type.CENTER_Y) {
                            this.getAnchor(ConstraintAnchor.Type.TOP).connect(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.TOP), 0);
                            this.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(((ConstraintWidget)object2).getAnchor(ConstraintAnchor.Type.BOTTOM), 0);
                            this.getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(((ConstraintWidget)object2).getAnchor((ConstraintAnchor.Type)((Object)object3)), 0);
                            return;
                        }
                        constraintAnchor = this.getAnchor((ConstraintAnchor.Type)((Object)object));
                        if (!constraintAnchor.isValidConnection((ConstraintAnchor)(object2 = ((ConstraintWidget)object2).getAnchor((ConstraintAnchor.Type)((Object)object3))))) return;
                        if (object != ConstraintAnchor.Type.BASELINE) break block25;
                        object = this.getAnchor(ConstraintAnchor.Type.TOP);
                        object3 = this.getAnchor(ConstraintAnchor.Type.BOTTOM);
                        if (object != null) {
                            ((ConstraintAnchor)object).reset();
                        }
                        if (object3 != null) {
                            ((ConstraintAnchor)object3).reset();
                        }
                        n2 = 0;
                        break block26;
                    }
                    if (object == ConstraintAnchor.Type.TOP || object == ConstraintAnchor.Type.BOTTOM) break block27;
                    if (object == ConstraintAnchor.Type.LEFT) break block28;
                    n2 = n;
                    if (object != ConstraintAnchor.Type.RIGHT) break block26;
                }
                if (((ConstraintAnchor)(object3 = this.getAnchor(ConstraintAnchor.Type.CENTER))).getTarget() != object2) {
                    ((ConstraintAnchor)object3).reset();
                }
                object3 = this.getAnchor((ConstraintAnchor.Type)((Object)object)).getOpposite();
                object = this.getAnchor(ConstraintAnchor.Type.CENTER_X);
                n2 = n;
                if (((ConstraintAnchor)object).isConnected()) {
                    ((ConstraintAnchor)object3).reset();
                    ((ConstraintAnchor)object).reset();
                    n2 = n;
                }
                break block26;
            }
            object3 = this.getAnchor(ConstraintAnchor.Type.BASELINE);
            if (object3 != null) {
                ((ConstraintAnchor)object3).reset();
            }
            if (((ConstraintAnchor)(object3 = this.getAnchor(ConstraintAnchor.Type.CENTER))).getTarget() != object2) {
                ((ConstraintAnchor)object3).reset();
            }
            object = this.getAnchor((ConstraintAnchor.Type)((Object)object)).getOpposite();
            object3 = this.getAnchor(ConstraintAnchor.Type.CENTER_Y);
            n2 = n;
            if (((ConstraintAnchor)object3).isConnected()) {
                ((ConstraintAnchor)object).reset();
                ((ConstraintAnchor)object3).reset();
                n2 = n;
            }
        }
        constraintAnchor.connect((ConstraintAnchor)object2, n2);
        return;
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    public void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int n) {
        if (constraintAnchor.getOwner() != this) return;
        this.connect(constraintAnchor.getType(), constraintAnchor2.getOwner(), constraintAnchor2.getType(), n);
    }

    public void connectCircularConstraint(ConstraintWidget constraintWidget, float f, int n) {
        this.immediateConnect(ConstraintAnchor.Type.CENTER, constraintWidget, ConstraintAnchor.Type.CENTER, n, 0);
        this.mCircleConstraintAngle = f;
    }

    public void copy(ConstraintWidget object, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        this.mHorizontalResolution = object.mHorizontalResolution;
        this.mVerticalResolution = object.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = object.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = object.mMatchConstraintDefaultHeight;
        int[] arrn = this.mResolvedMatchConstraintDefault;
        Object object2 = object.mResolvedMatchConstraintDefault;
        arrn[0] = object2[0];
        arrn[1] = object2[1];
        this.mMatchConstraintMinWidth = object.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = object.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = object.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = object.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = object.mMatchConstraintPercentHeight;
        this.mIsWidthWrapContent = object.mIsWidthWrapContent;
        this.mIsHeightWrapContent = object.mIsHeightWrapContent;
        this.mResolvedDimensionRatioSide = object.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = object.mResolvedDimensionRatio;
        object2 = object.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(object2, ((int[])object2).length);
        this.mCircleConstraintAngle = object.mCircleConstraintAngle;
        this.hasBaseline = object.hasBaseline;
        this.inPlaceholder = object.inPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = Arrays.copyOf(this.mListDimensionBehaviors, 2);
        object2 = this.mParent;
        arrn = null;
        object2 = object2 == null ? null : hashMap.get(object.mParent);
        this.mParent = object2;
        this.mWidth = object.mWidth;
        this.mHeight = object.mHeight;
        this.mDimensionRatio = object.mDimensionRatio;
        this.mDimensionRatioSide = object.mDimensionRatioSide;
        this.mX = object.mX;
        this.mY = object.mY;
        this.mRelX = object.mRelX;
        this.mRelY = object.mRelY;
        this.mOffsetX = object.mOffsetX;
        this.mOffsetY = object.mOffsetY;
        this.mBaselineDistance = object.mBaselineDistance;
        this.mMinWidth = object.mMinWidth;
        this.mMinHeight = object.mMinHeight;
        this.mHorizontalBiasPercent = object.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = object.mVerticalBiasPercent;
        this.mCompanionWidget = object.mCompanionWidget;
        this.mContainerItemSkip = object.mContainerItemSkip;
        this.mVisibility = object.mVisibility;
        this.mDebugName = object.mDebugName;
        this.mType = object.mType;
        this.mDistToTop = object.mDistToTop;
        this.mDistToLeft = object.mDistToLeft;
        this.mDistToRight = object.mDistToRight;
        this.mDistToBottom = object.mDistToBottom;
        this.mLeftHasCentered = object.mLeftHasCentered;
        this.mRightHasCentered = object.mRightHasCentered;
        this.mTopHasCentered = object.mTopHasCentered;
        this.mBottomHasCentered = object.mBottomHasCentered;
        this.mHorizontalWrapVisited = object.mHorizontalWrapVisited;
        this.mVerticalWrapVisited = object.mVerticalWrapVisited;
        this.mOptimizerMeasurable = object.mOptimizerMeasurable;
        this.mGroupsToSolver = object.mGroupsToSolver;
        this.mHorizontalChainStyle = object.mHorizontalChainStyle;
        this.mVerticalChainStyle = object.mVerticalChainStyle;
        this.mHorizontalChainFixedPosition = object.mHorizontalChainFixedPosition;
        this.mVerticalChainFixedPosition = object.mVerticalChainFixedPosition;
        object2 = this.mWeight;
        Object[] arrobject = object.mWeight;
        object2[0] = (int)arrobject[0];
        object2[1] = (int)arrobject[1];
        arrobject = this.mListNextMatchConstraintsWidget;
        object2 = object.mListNextMatchConstraintsWidget;
        arrobject[0] = object2[0];
        arrobject[1] = object2[1];
        arrobject = this.mNextChainWidget;
        object2 = object.mNextChainWidget;
        arrobject[0] = object2[0];
        arrobject[1] = object2[1];
        object2 = object.mHorizontalNextWidget;
        object2 = object2 == null ? null : hashMap.get(object2);
        this.mHorizontalNextWidget = object2;
        object = object.mVerticalNextWidget;
        object = object == null ? arrn : hashMap.get(object);
        this.mVerticalNextWidget = object;
    }

    public void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.mLeft);
        linearSystem.createObjectVariable(this.mTop);
        linearSystem.createObjectVariable(this.mRight);
        linearSystem.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance <= 0) return;
        linearSystem.createObjectVariable(this.mBaseline);
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            default: {
                throw new AssertionError((Object)type.name());
            }
            case 9: {
                return null;
            }
            case 8: {
                return this.mCenterY;
            }
            case 7: {
                return this.mCenterX;
            }
            case 6: {
                return this.mCenter;
            }
            case 5: {
                return this.mBaseline;
            }
            case 4: {
                return this.mBottom;
            }
            case 3: {
                return this.mRight;
            }
            case 2: {
                return this.mTop;
            }
            case 1: 
        }
        return this.mLeft;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public float getBiasPercent(int n) {
        if (n == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (n != 1) return -1.0f;
        return this.mVerticalBiasPercent;
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

    public DimensionBehaviour getDimensionBehaviour(int n) {
        if (n == 0) {
            return this.getHorizontalDimensionBehaviour();
        }
        if (n != 1) return null;
        return this.getVerticalDimensionBehaviour();
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
        if (this.mVisibility != 8) return this.mHeight;
        return 0;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        boolean bl = this.isInHorizontalChain();
        Object object = null;
        if (!bl) return object;
        object = this;
        Object object2 = null;
        while (object2 == null) {
            if (object == null) return object2;
            Object object3 = ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.LEFT);
            object3 = object3 == null ? null : ((ConstraintAnchor)object3).getTarget();
            object3 = object3 == null ? null : ((ConstraintAnchor)object3).getOwner();
            if (object3 == this.getParent()) {
                return object;
            }
            ConstraintAnchor constraintAnchor = object3 == null ? null : ((ConstraintWidget)object3).getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            if (constraintAnchor != null && constraintAnchor.getOwner() != object) {
                object2 = object;
                continue;
            }
            object = object3;
        }
        return object2;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public int getHorizontalMargin() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        int n = 0;
        if (constraintAnchor != null) {
            n = 0 + constraintAnchor.mMargin;
        }
        constraintAnchor = this.mRight;
        int n2 = n;
        if (constraintAnchor == null) return n2;
        return n + constraintAnchor.mMargin;
    }

    public int getLeft() {
        return this.getX();
    }

    public int getLength(int n) {
        if (n == 0) {
            return this.getWidth();
        }
        if (n != 1) return 0;
        return this.getHeight();
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

    public ConstraintWidget getNextChainMember(int n) {
        if (n == 0) {
            if (this.mRight.mTarget == null) return null;
            ConstraintAnchor constraintAnchor = this.mRight.mTarget.mTarget;
            ConstraintAnchor constraintAnchor2 = this.mRight;
            if (constraintAnchor != constraintAnchor2) return null;
            return constraintAnchor2.mTarget.mOwner;
        }
        if (n != 1) return null;
        if (this.mBottom.mTarget == null) return null;
        ConstraintAnchor constraintAnchor = this.mBottom.mTarget.mTarget;
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        if (constraintAnchor != constraintAnchor3) return null;
        return constraintAnchor3.mTarget.mOwner;
    }

    public int getOptimizerWrapHeight() {
        int n;
        int n2 = n = this.mHeight;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) return n2;
        if (this.mMatchConstraintDefaultHeight == 1) {
            n = Math.max(this.mMatchConstraintMinHeight, n);
        } else {
            n = this.mMatchConstraintMinHeight;
            if (n > 0) {
                this.mHeight = n;
            } else {
                n = 0;
            }
        }
        int n3 = this.mMatchConstraintMaxHeight;
        n2 = n;
        if (n3 <= 0) return n2;
        n2 = n;
        if (n3 >= n) return n2;
        return n3;
    }

    public int getOptimizerWrapWidth() {
        int n;
        int n2 = n = this.mWidth;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) return n2;
        if (this.mMatchConstraintDefaultWidth == 1) {
            n = Math.max(this.mMatchConstraintMinWidth, n);
        } else {
            n = this.mMatchConstraintMinWidth;
            if (n > 0) {
                this.mWidth = n;
            } else {
                n = 0;
            }
        }
        int n3 = this.mMatchConstraintMaxWidth;
        n2 = n;
        if (n3 <= 0) return n2;
        n2 = n;
        if (n3 >= n) return n2;
        return n3;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public ConstraintWidget getPreviousChainMember(int n) {
        if (n == 0) {
            if (this.mLeft.mTarget == null) return null;
            ConstraintAnchor constraintAnchor = this.mLeft.mTarget.mTarget;
            ConstraintAnchor constraintAnchor2 = this.mLeft;
            if (constraintAnchor != constraintAnchor2) return null;
            return constraintAnchor2.mTarget.mOwner;
        }
        if (n != 1) return null;
        if (this.mTop.mTarget == null) return null;
        ConstraintAnchor constraintAnchor = this.mTop.mTarget.mTarget;
        ConstraintAnchor constraintAnchor3 = this.mTop;
        if (constraintAnchor != constraintAnchor3) return null;
        return constraintAnchor3.mTarget.mOwner;
    }

    int getRelativePositioning(int n) {
        if (n == 0) {
            return this.mRelX;
        }
        if (n != 1) return 0;
        return this.mRelY;
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

    public WidgetRun getRun(int n) {
        if (n == 0) {
            return this.horizontalRun;
        }
        if (n != 1) return null;
        return this.verticalRun;
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
        boolean bl = this.isInVerticalChain();
        Object object = null;
        if (!bl) return object;
        object = this;
        Object object2 = null;
        while (object2 == null) {
            if (object == null) return object2;
            Object object3 = ((ConstraintWidget)object).getAnchor(ConstraintAnchor.Type.TOP);
            object3 = object3 == null ? null : ((ConstraintAnchor)object3).getTarget();
            object3 = object3 == null ? null : ((ConstraintAnchor)object3).getOwner();
            if (object3 == this.getParent()) {
                return object;
            }
            ConstraintAnchor constraintAnchor = object3 == null ? null : ((ConstraintWidget)object3).getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            if (constraintAnchor != null && constraintAnchor.getOwner() != object) {
                object2 = object;
                continue;
            }
            object = object3;
        }
        return object2;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public int getVerticalMargin() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        int n = 0;
        if (constraintAnchor != null) {
            n = 0 + this.mTop.mMargin;
        }
        int n2 = n;
        if (this.mRight == null) return n2;
        return n + this.mBottom.mMargin;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public int getWidth() {
        if (this.mVisibility != 8) return this.mWidth;
        return 0;
    }

    public int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null) return this.mX;
        if (!(constraintWidget instanceof ConstraintWidgetContainer)) return this.mX;
        return ((ConstraintWidgetContainer)constraintWidget).mPaddingLeft + this.mX;
    }

    public int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null) return this.mY;
        if (!(constraintWidget instanceof ConstraintWidgetContainer)) return this.mY;
        return ((ConstraintWidgetContainer)constraintWidget).mPaddingTop + this.mY;
    }

    public boolean hasBaseline() {
        return this.hasBaseline;
    }

    public void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int n, int n2) {
        this.getAnchor(type).connect(constraintWidget.getAnchor(type2), n, n2, true);
    }

    public boolean isHeightWrapContent() {
        return this.mIsHeightWrapContent;
    }

    public boolean isInHorizontalChain() {
        if (this.mLeft.mTarget != null) {
            if (this.mLeft.mTarget.mTarget == this.mLeft) return true;
        }
        if (this.mRight.mTarget == null) return false;
        if (this.mRight.mTarget.mTarget != this.mRight) return false;
        return true;
    }

    public boolean isInPlaceholder() {
        return this.inPlaceholder;
    }

    public boolean isInVerticalChain() {
        if (this.mTop.mTarget != null) {
            if (this.mTop.mTarget.mTarget == this.mTop) return true;
        }
        if (this.mBottom.mTarget == null) return false;
        if (this.mBottom.mTarget.mTarget != this.mBottom) return false;
        return true;
    }

    public boolean isInVirtualLayout() {
        return this.mInVirtuaLayout;
    }

    public boolean isRoot() {
        if (this.mParent != null) return false;
        return true;
    }

    public boolean isSpreadHeight() {
        int n = this.mMatchConstraintDefaultHeight;
        boolean bl = true;
        if (n != 0) return false;
        if (this.mDimensionRatio != 0.0f) return false;
        if (this.mMatchConstraintMinHeight != 0) return false;
        if (this.mMatchConstraintMaxHeight != 0) return false;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) return false;
        return bl;
    }

    public boolean isSpreadWidth() {
        boolean bl;
        int n = this.mMatchConstraintDefaultWidth;
        boolean bl2 = bl = false;
        if (n != 0) return bl2;
        bl2 = bl;
        if (this.mDimensionRatio != 0.0f) return bl2;
        bl2 = bl;
        if (this.mMatchConstraintMinWidth != 0) return bl2;
        bl2 = bl;
        if (this.mMatchConstraintMaxWidth != 0) return bl2;
        bl2 = bl;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) return bl2;
        return true;
    }

    public boolean isWidthWrapContent() {
        return this.mIsWidthWrapContent;
    }

    public void reset() {
        float f;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mHorizontalBiasPercent = f = DEFAULT_BIAS;
        this.mVerticalBiasPercent = f;
        this.mListDimensionBehaviors[0] = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors[1] = DimensionBehaviour.FIXED;
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
        Object[] arrobject = this.mWeight;
        arrobject[0] = -1.0f;
        arrobject[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        arrobject = this.mMaxDimension;
        arrobject[0] = Integer.MAX_VALUE;
        arrobject[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedHasRatio = false;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mOptimizerMeasurable = false;
        this.mGroupsToSolver = false;
        arrobject = this.isTerminalWidget;
        arrobject[0] = (float)true;
        arrobject[1] = (float)true;
        this.mInVirtuaLayout = false;
        arrobject = this.mIsInBarrier;
        arrobject[0] = (float)false;
        arrobject[1] = (float)false;
    }

    public void resetAllConstraints() {
        this.resetAnchors();
        this.setVerticalBiasPercent(DEFAULT_BIAS);
        this.setHorizontalBiasPercent(DEFAULT_BIAS);
    }

    public void resetAnchor(ConstraintAnchor constraintAnchor) {
        if (this.getParent() != null && this.getParent() instanceof ConstraintWidgetContainer && ((ConstraintWidgetContainer)this.getParent()).handlesInternalConstraints()) {
            return;
        }
        ConstraintAnchor constraintAnchor2 = this.getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor constraintAnchor3 = this.getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintAnchor constraintAnchor4 = this.getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor constraintAnchor5 = this.getAnchor(ConstraintAnchor.Type.BOTTOM);
        ConstraintAnchor constraintAnchor6 = this.getAnchor(ConstraintAnchor.Type.CENTER);
        ConstraintAnchor constraintAnchor7 = this.getAnchor(ConstraintAnchor.Type.CENTER_X);
        ConstraintAnchor constraintAnchor8 = this.getAnchor(ConstraintAnchor.Type.CENTER_Y);
        if (constraintAnchor == constraintAnchor6) {
            if (constraintAnchor2.isConnected() && constraintAnchor3.isConnected() && constraintAnchor2.getTarget() == constraintAnchor3.getTarget()) {
                constraintAnchor2.reset();
                constraintAnchor3.reset();
            }
            if (constraintAnchor4.isConnected() && constraintAnchor5.isConnected() && constraintAnchor4.getTarget() == constraintAnchor5.getTarget()) {
                constraintAnchor4.reset();
                constraintAnchor5.reset();
            }
            this.mHorizontalBiasPercent = 0.5f;
            this.mVerticalBiasPercent = 0.5f;
        } else if (constraintAnchor == constraintAnchor7) {
            if (constraintAnchor2.isConnected() && constraintAnchor3.isConnected() && constraintAnchor2.getTarget().getOwner() == constraintAnchor3.getTarget().getOwner()) {
                constraintAnchor2.reset();
                constraintAnchor3.reset();
            }
            this.mHorizontalBiasPercent = 0.5f;
        } else if (constraintAnchor == constraintAnchor8) {
            if (constraintAnchor4.isConnected() && constraintAnchor5.isConnected() && constraintAnchor4.getTarget().getOwner() == constraintAnchor5.getTarget().getOwner()) {
                constraintAnchor4.reset();
                constraintAnchor5.reset();
            }
            this.mVerticalBiasPercent = 0.5f;
        } else if (constraintAnchor != constraintAnchor2 && constraintAnchor != constraintAnchor3) {
            if ((constraintAnchor == constraintAnchor4 || constraintAnchor == constraintAnchor5) && constraintAnchor4.isConnected() && constraintAnchor4.getTarget() == constraintAnchor5.getTarget()) {
                constraintAnchor6.reset();
            }
        } else if (constraintAnchor2.isConnected() && constraintAnchor2.getTarget() == constraintAnchor3.getTarget()) {
            constraintAnchor6.reset();
        }
        constraintAnchor.reset();
    }

    public void resetAnchors() {
        ConstraintWidget constraintWidget = this.getParent();
        if (constraintWidget != null && constraintWidget instanceof ConstraintWidgetContainer && ((ConstraintWidgetContainer)this.getParent()).handlesInternalConstraints()) {
            return;
        }
        int n = 0;
        int n2 = this.mAnchors.size();
        while (n < n2) {
            this.mAnchors.get(n).reset();
            ++n;
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    public void setBaselineDistance(int n) {
        this.mBaselineDistance = n;
        boolean bl = n > 0;
        this.hasBaseline = bl;
    }

    public void setCompanionWidget(Object object) {
        this.mCompanionWidget = object;
    }

    public void setContainerItemSkip(int n) {
        if (n >= 0) {
            this.mContainerItemSkip = n;
            return;
        }
        this.mContainerItemSkip = 0;
    }

    public void setDebugName(String string2) {
        this.mDebugName = string2;
    }

    public void setDebugSolverName(LinearSystem object, String string2) {
        this.mDebugName = string2;
        SolverVariable solverVariable = ((LinearSystem)object).createObjectVariable(this.mLeft);
        Object object2 = ((LinearSystem)object).createObjectVariable(this.mTop);
        Object object3 = ((LinearSystem)object).createObjectVariable(this.mRight);
        SolverVariable solverVariable2 = ((LinearSystem)object).createObjectVariable(this.mBottom);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".left");
        solverVariable.setName(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".top");
        ((SolverVariable)object2).setName(stringBuilder.toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(".right");
        ((SolverVariable)object3).setName(((StringBuilder)object2).toString());
        object3 = new StringBuilder();
        ((StringBuilder)object3).append(string2);
        ((StringBuilder)object3).append(".bottom");
        solverVariable2.setName(((StringBuilder)object3).toString());
        if (this.mBaselineDistance <= 0) return;
        solverVariable2 = ((LinearSystem)object).createObjectVariable(this.mBaseline);
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(".baseline");
        solverVariable2.setName(((StringBuilder)object).toString());
    }

    public void setDimension(int n, int n2) {
        this.mWidth = n;
        int n3 = this.mMinWidth;
        if (n < n3) {
            this.mWidth = n3;
        }
        this.mHeight = n2;
        n = this.mMinHeight;
        if (n2 >= n) return;
        this.mHeight = n;
    }

    public void setDimensionRatio(float f, int n) {
        this.mDimensionRatio = f;
        this.mDimensionRatioSide = n;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void setDimensionRatio(String var1_1) {
        block9 : {
            block12 : {
                block10 : {
                    block11 : {
                        if (var1_1 == null || var1_1.length() == 0) break block10;
                        var2_3 = -1;
                        var3_4 = var1_1.length();
                        var4_5 = var1_1.indexOf(44);
                        var5_6 = 0;
                        var6_7 = var2_3;
                        var7_8 = var5_6;
                        if (var4_5 > 0) {
                            var6_7 = var2_3;
                            var7_8 = var5_6;
                            if (var4_5 < var3_4 - 1) {
                                var8_9 = var1_1.substring(0, var4_5);
                                if (var8_9.equalsIgnoreCase("W")) {
                                    var6_7 = 0;
                                } else {
                                    var6_7 = var2_3;
                                    if (var8_9.equalsIgnoreCase("H")) {
                                        var6_7 = 1;
                                    }
                                }
                                var7_8 = var4_5 + 1;
                            }
                        }
                        if ((var2_3 = var1_1.indexOf(58)) < 0 || var2_3 >= var3_4 - 1) break block11;
                        var8_9 = var1_1.substring(var7_8, var2_3);
                        var1_1 = var1_1.substring(var2_3 + 1);
                        if (var8_9.length() <= 0 || var1_1.length() <= 0) break block12;
                        try {
                            var9_10 = Float.parseFloat(var8_9);
                            var10_11 = Float.parseFloat(var1_1);
                        }
                        catch (NumberFormatException var1_2) {}
                        if (!(var9_10 > 0.0f) || !(var10_11 > 0.0f)) break block12;
                        if (var6_7 != 1) ** GOTO lbl33
                        var10_11 = Math.abs(var10_11 / var9_10);
                        break block9;
lbl33: // 1 sources:
                        var10_11 = Math.abs(var9_10 / var10_11);
                        break block9;
                    }
                    if ((var1_1 = var1_1.substring(var7_8)).length() <= 0) break block12;
                    var10_11 = Float.parseFloat(var1_1);
                    break block9;
                }
                this.mDimensionRatio = 0.0f;
                return;
            }
            var10_11 = 0.0f;
        }
        if (!(var10_11 > 0.0f)) return;
        this.mDimensionRatio = var10_11;
        this.mDimensionRatioSide = var6_7;
    }

    public void setFrame(int n, int n2, int n3) {
        if (n3 == 0) {
            this.setHorizontalDimension(n, n2);
            return;
        }
        if (n3 != 1) return;
        this.setVerticalDimension(n, n2);
    }

    public void setFrame(int n, int n2, int n3, int n4) {
        int n5 = n3 - n;
        n3 = n4 - n2;
        this.mX = n;
        this.mY = n2;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        n = n5;
        if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED) {
            n2 = this.mWidth;
            n = n5;
            if (n5 < n2) {
                n = n2;
            }
        }
        n2 = n3;
        if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED) {
            n4 = this.mHeight;
            n2 = n3;
            if (n3 < n4) {
                n2 = n4;
            }
        }
        this.mWidth = n;
        this.mHeight = n2;
        n = this.mMinHeight;
        if (n2 < n) {
            this.mHeight = n;
        }
        if ((n = this.mWidth) >= (n2 = this.mMinWidth)) return;
        this.mWidth = n2;
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int n) {
        int n2 = 1.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[type.ordinal()];
        if (n2 == 1) {
            this.mLeft.mGoneMargin = n;
            return;
        }
        if (n2 == 2) {
            this.mTop.mGoneMargin = n;
            return;
        }
        if (n2 == 3) {
            this.mRight.mGoneMargin = n;
            return;
        }
        if (n2 != 4) {
            return;
        }
        this.mBottom.mGoneMargin = n;
    }

    public void setHasBaseline(boolean bl) {
        this.hasBaseline = bl;
    }

    public void setHeight(int n) {
        this.mHeight = n;
        int n2 = this.mMinHeight;
        if (n >= n2) return;
        this.mHeight = n2;
    }

    public void setHeightWrapContent(boolean bl) {
        this.mIsHeightWrapContent = bl;
    }

    public void setHorizontalBiasPercent(float f) {
        this.mHorizontalBiasPercent = f;
    }

    public void setHorizontalChainStyle(int n) {
        this.mHorizontalChainStyle = n;
    }

    public void setHorizontalDimension(int n, int n2) {
        this.mX = n;
        this.mWidth = n2 -= n;
        n = this.mMinWidth;
        if (n2 >= n) return;
        this.mWidth = n;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    public void setHorizontalMatchStyle(int n, int n2, int n3, float f) {
        this.mMatchConstraintDefaultWidth = n;
        this.mMatchConstraintMinWidth = n2;
        n = n3;
        if (n3 == Integer.MAX_VALUE) {
            n = 0;
        }
        this.mMatchConstraintMaxWidth = n;
        this.mMatchConstraintPercentWidth = f;
        if (!(f > 0.0f)) return;
        if (!(f < 1.0f)) return;
        if (this.mMatchConstraintDefaultWidth != 0) return;
        this.mMatchConstraintDefaultWidth = 2;
    }

    public void setHorizontalWeight(float f) {
        this.mWeight[0] = f;
    }

    protected void setInBarrier(int n, boolean bl) {
        this.mIsInBarrier[n] = bl;
    }

    public void setInPlaceholder(boolean bl) {
        this.inPlaceholder = bl;
    }

    public void setInVirtualLayout(boolean bl) {
        this.mInVirtuaLayout = bl;
    }

    public void setLength(int n, int n2) {
        if (n2 == 0) {
            this.setWidth(n);
            return;
        }
        if (n2 != 1) return;
        this.setHeight(n);
    }

    public void setMaxHeight(int n) {
        this.mMaxDimension[1] = n;
    }

    public void setMaxWidth(int n) {
        this.mMaxDimension[0] = n;
    }

    public void setMinHeight(int n) {
        if (n < 0) {
            this.mMinHeight = 0;
            return;
        }
        this.mMinHeight = n;
    }

    public void setMinWidth(int n) {
        if (n < 0) {
            this.mMinWidth = 0;
            return;
        }
        this.mMinWidth = n;
    }

    public void setOffset(int n, int n2) {
        this.mOffsetX = n;
        this.mOffsetY = n2;
    }

    public void setOrigin(int n, int n2) {
        this.mX = n;
        this.mY = n2;
    }

    public void setParent(ConstraintWidget constraintWidget) {
        this.mParent = constraintWidget;
    }

    void setRelativePositioning(int n, int n2) {
        if (n2 == 0) {
            this.mRelX = n;
            return;
        }
        if (n2 != 1) return;
        this.mRelY = n;
    }

    public void setType(String string2) {
        this.mType = string2;
    }

    public void setVerticalBiasPercent(float f) {
        this.mVerticalBiasPercent = f;
    }

    public void setVerticalChainStyle(int n) {
        this.mVerticalChainStyle = n;
    }

    public void setVerticalDimension(int n, int n2) {
        this.mY = n;
        this.mHeight = n2 -= n;
        n = this.mMinHeight;
        if (n2 >= n) return;
        this.mHeight = n;
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public void setVerticalMatchStyle(int n, int n2, int n3, float f) {
        this.mMatchConstraintDefaultHeight = n;
        this.mMatchConstraintMinHeight = n2;
        n = n3;
        if (n3 == Integer.MAX_VALUE) {
            n = 0;
        }
        this.mMatchConstraintMaxHeight = n;
        this.mMatchConstraintPercentHeight = f;
        if (!(f > 0.0f)) return;
        if (!(f < 1.0f)) return;
        if (this.mMatchConstraintDefaultHeight != 0) return;
        this.mMatchConstraintDefaultHeight = 2;
    }

    public void setVerticalWeight(float f) {
        this.mWeight[1] = f;
    }

    public void setVisibility(int n) {
        this.mVisibility = n;
    }

    public void setWidth(int n) {
        this.mWidth = n;
        int n2 = this.mMinWidth;
        if (n >= n2) return;
        this.mWidth = n2;
    }

    public void setWidthWrapContent(boolean bl) {
        this.mIsWidthWrapContent = bl;
    }

    public void setX(int n) {
        this.mX = n;
    }

    public void setY(int n) {
        this.mY = n;
    }

    public void setupDimensionRatio(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (bl3 && !bl4) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!bl3 && bl4) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (!(this.mResolvedDimensionRatioSide != 0 || this.mTop.isConnected() && this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (!(this.mResolvedDimensionRatioSide != 1 || this.mLeft.isConnected() && this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (!(this.mResolvedDimensionRatioSide != -1 || this.mTop.isConnected() && this.mBottom.isConnected() && this.mLeft.isConnected() && this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide != -1) return;
        if (this.mMatchConstraintMinWidth > 0 && this.mMatchConstraintMinHeight == 0) {
            this.mResolvedDimensionRatioSide = 0;
            return;
        }
        if (this.mMatchConstraintMinWidth != 0) return;
        if (this.mMatchConstraintMinHeight <= 0) return;
        this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
        this.mResolvedDimensionRatioSide = 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        CharSequence charSequence = this.mType;
        String string2 = "";
        if (charSequence != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("type: ");
            ((StringBuilder)charSequence).append(this.mType);
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        charSequence = string2;
        if (this.mDebugName != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("id: ");
            ((StringBuilder)charSequence).append(this.mDebugName);
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append("(");
        stringBuilder.append(this.mX);
        stringBuilder.append(", ");
        stringBuilder.append(this.mY);
        stringBuilder.append(") - (");
        stringBuilder.append(this.mWidth);
        stringBuilder.append(" x ");
        stringBuilder.append(this.mHeight);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void updateFromRuns(boolean bl, boolean bl2) {
        boolean bl3;
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl4;
        int n5;
        block13 : {
            block12 : {
                bl4 = bl & this.horizontalRun.isResolved();
                bl3 = bl2 & this.verticalRun.isResolved();
                n4 = this.horizontalRun.start.value;
                n3 = this.verticalRun.start.value;
                n = this.horizontalRun.end.value;
                n5 = this.verticalRun.end.value;
                if (n - n4 < 0 || n5 - n3 < 0 || n4 == Integer.MIN_VALUE || n4 == Integer.MAX_VALUE || n3 == Integer.MIN_VALUE || n3 == Integer.MAX_VALUE || n == Integer.MIN_VALUE || n == Integer.MAX_VALUE || n5 == Integer.MIN_VALUE) break block12;
                n2 = n5;
                if (n5 != Integer.MAX_VALUE) break block13;
            }
            n4 = 0;
            n3 = 0;
            n = 0;
            n2 = 0;
        }
        n5 = n - n4;
        n = n2 - n3;
        if (bl4) {
            this.mX = n4;
        }
        if (bl3) {
            this.mY = n3;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (bl4) {
            n2 = n5;
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED) {
                n4 = this.mWidth;
                n2 = n5;
                if (n5 < n4) {
                    n2 = n4;
                }
            }
            this.mWidth = n2;
            n4 = this.mMinWidth;
            if (n2 < n4) {
                this.mWidth = n4;
            }
        }
        if (!bl3) return;
        n2 = n;
        if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED) {
            n4 = this.mHeight;
            n2 = n;
            if (n < n4) {
                n2 = n4;
            }
        }
        this.mHeight = n2;
        n4 = this.mMinHeight;
        if (n2 >= n4) return;
        this.mHeight = n4;
    }

    public void updateFromSolver(LinearSystem linearSystem) {
        int n;
        int n2;
        int n3;
        int n4;
        block8 : {
            block7 : {
                n2 = linearSystem.getObjectVariableValue(this.mLeft);
                int n5 = linearSystem.getObjectVariableValue(this.mTop);
                int n6 = linearSystem.getObjectVariableValue(this.mRight);
                n4 = linearSystem.getObjectVariableValue(this.mBottom);
                n3 = n2;
                n = n6;
                if (this.horizontalRun.start.resolved) {
                    n3 = n2;
                    n = n6;
                    if (this.horizontalRun.end.resolved) {
                        n3 = this.horizontalRun.start.value;
                        n = this.horizontalRun.end.value;
                    }
                }
                n2 = n5;
                n6 = n4;
                if (this.verticalRun.start.resolved) {
                    n2 = n5;
                    n6 = n4;
                    if (this.verticalRun.end.resolved) {
                        n2 = this.verticalRun.start.value;
                        n6 = this.verticalRun.end.value;
                    }
                }
                if (n - n3 < 0 || n6 - n2 < 0 || n3 == Integer.MIN_VALUE || n3 == Integer.MAX_VALUE || n2 == Integer.MIN_VALUE || n2 == Integer.MAX_VALUE || n == Integer.MIN_VALUE || n == Integer.MAX_VALUE || n6 == Integer.MIN_VALUE) break block7;
                n4 = n3;
                n3 = n;
                n = n6;
                if (n6 != Integer.MAX_VALUE) break block8;
            }
            n = 0;
            n4 = 0;
            n2 = 0;
            n3 = 0;
        }
        this.setFrame(n4, n2, n3, n);
    }

    public static final class DimensionBehaviour
    extends Enum<DimensionBehaviour> {
        private static final /* synthetic */ DimensionBehaviour[] $VALUES;
        public static final /* enum */ DimensionBehaviour FIXED;
        public static final /* enum */ DimensionBehaviour MATCH_CONSTRAINT;
        public static final /* enum */ DimensionBehaviour MATCH_PARENT;
        public static final /* enum */ DimensionBehaviour WRAP_CONTENT;

        static {
            DimensionBehaviour dimensionBehaviour;
            FIXED = new DimensionBehaviour();
            WRAP_CONTENT = new DimensionBehaviour();
            MATCH_CONSTRAINT = new DimensionBehaviour();
            MATCH_PARENT = dimensionBehaviour = new DimensionBehaviour();
            $VALUES = new DimensionBehaviour[]{FIXED, WRAP_CONTENT, MATCH_CONSTRAINT, dimensionBehaviour};
        }

        public static DimensionBehaviour valueOf(String string2) {
            return Enum.valueOf(DimensionBehaviour.class, string2);
        }

        public static DimensionBehaviour[] values() {
            return (DimensionBehaviour[])$VALUES.clone();
        }
    }

}

