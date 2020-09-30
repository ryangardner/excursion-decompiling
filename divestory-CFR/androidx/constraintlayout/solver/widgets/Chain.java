/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ChainHead;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;

class Chain {
    private static final boolean DEBUG = false;

    Chain() {
    }

    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int n) {
        ChainHead[] arrchainHead;
        int n2;
        int n3;
        int n4 = 0;
        if (n == 0) {
            n2 = constraintWidgetContainer.mHorizontalChainsSize;
            arrchainHead = constraintWidgetContainer.mHorizontalChainsArray;
            n3 = 0;
        } else {
            n3 = 2;
            n2 = constraintWidgetContainer.mVerticalChainsSize;
            arrchainHead = constraintWidgetContainer.mVerticalChainsArray;
        }
        while (n4 < n2) {
            ChainHead chainHead = arrchainHead[n4];
            chainHead.define();
            Chain.applyChainConstraints(constraintWidgetContainer, linearSystem, n, n3, chainHead);
            ++n4;
        }
    }

    /*
     * Unable to fully structure code
     */
    static void applyChainConstraints(ConstraintWidgetContainer var0, LinearSystem var1_1, int var2_2, int var3_3, ChainHead var4_4) {
        block65 : {
            block68 : {
                block67 : {
                    block66 : {
                        block64 : {
                            block63 : {
                                var5_5 = var4_4.mFirst;
                                var6_6 = var4_4.mLast;
                                var7_7 = var4_4.mFirstVisibleWidget;
                                var8_8 = var4_4.mLastVisibleWidget;
                                var9_9 = var4_4.mHead;
                                var10_10 = var4_4.mTotalWeight;
                                var11_11 = var4_4.mFirstMatchConstraintWidget;
                                var11_11 = var4_4.mLastMatchConstraintWidget;
                                var12_12 = var0.mListDimensionBehaviors[var2_2] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 1 : 0;
                                if (var2_2 != 0) break block63;
                                var13_13 = var9_9.mHorizontalChainStyle == 0 ? 1 : 0;
                                var14_14 = var9_9.mHorizontalChainStyle == 1 ? 1 : 0;
                                var15_15 = var13_13;
                                var16_16 = var14_14;
                                if (var9_9.mHorizontalChainStyle != 2) ** GOTO lbl-1000
                                ** GOTO lbl-1000
                            }
                            var13_13 = var9_9.mVerticalChainStyle == 0 ? 1 : 0;
                            var14_14 = var9_9.mVerticalChainStyle == 1 ? 1 : 0;
                            var15_15 = var13_13;
                            var16_16 = var14_14;
                            if (var9_9.mVerticalChainStyle == 2) lbl-1000: // 2 sources:
                            {
                                var17_17 = 1;
                                var15_15 = var13_13;
                            } else lbl-1000: // 2 sources:
                            {
                                var17_17 = 0;
                                var14_14 = var16_16;
                            }
                            var18_18 /* !! */  = var5_5;
                            var16_16 = var15_15;
                            var13_13 = 0;
                            var15_15 = var14_14;
                            do {
                                var19_19 = null;
                                var20_20 = null;
                                if (var13_13 != 0) break;
                                var11_11 = var18_18 /* !! */ .mListAnchors[var3_3];
                                var14_14 = var17_17 != 0 ? 1 : 4;
                                var21_21 = var11_11.getMargin();
                                var22_22 = var18_18 /* !! */ .mListDimensionBehaviors[var2_2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var18_18 /* !! */ .mResolvedMatchConstraintDefault[var2_2] == 0;
                                var23_23 = var21_21;
                                if (var11_11.mTarget != null) {
                                    var23_23 = var21_21;
                                    if (var18_18 /* !! */  != var5_5) {
                                        var23_23 = var21_21 + var11_11.mTarget.getMargin();
                                    }
                                }
                                if (var17_17 != 0 && var18_18 /* !! */  != var5_5 && var18_18 /* !! */  != var7_7) {
                                    var14_14 = 5;
                                }
                                if (var11_11.mTarget != null) {
                                    if (var18_18 /* !! */  == var7_7) {
                                        var1_1.addGreaterThan(var11_11.mSolverVariable, var11_11.mTarget.mSolverVariable, var23_23, 6);
                                    } else {
                                        var1_1.addGreaterThan(var11_11.mSolverVariable, var11_11.mTarget.mSolverVariable, var23_23, 8);
                                    }
                                    if (var22_22 && var17_17 == 0) {
                                        var14_14 = 5;
                                    }
                                    var1_1.addEquality(var11_11.mSolverVariable, var11_11.mTarget.mSolverVariable, var23_23, var14_14);
                                }
                                if (var12_12 != 0) {
                                    if (var18_18 /* !! */ .getVisibility() != 8 && var18_18 /* !! */ .mListDimensionBehaviors[var2_2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                        var1_1.addGreaterThan(var18_18 /* !! */ .mListAnchors[var3_3 + 1].mSolverVariable, var18_18 /* !! */ .mListAnchors[var3_3].mSolverVariable, 0, 5);
                                    }
                                    var1_1.addGreaterThan(var18_18 /* !! */ .mListAnchors[var3_3].mSolverVariable, var0.mListAnchors[var3_3].mSolverVariable, 0, 8);
                                }
                                var24_25 = var18_18 /* !! */ .mListAnchors[var3_3 + 1].mTarget;
                                var11_11 = var20_20;
                                if (var24_25 != null) {
                                    var24_26 = var24_25.mOwner;
                                    var11_11 = var20_20;
                                    if (var24_26.mListAnchors[var3_3].mTarget != null) {
                                        var11_11 = var24_26.mListAnchors[var3_3].mTarget.mOwner != var18_18 /* !! */  ? var20_20 : var24_26;
                                    }
                                }
                                if (var11_11 != null) {
                                    var18_18 /* !! */  = var11_11;
                                    continue;
                                }
                                var13_13 = 1;
                            } while (true);
                            if (var8_8 != null) {
                                var11_11 = var6_6.mListAnchors;
                                var14_14 = var3_3 + 1;
                                if (var11_11[var14_14].mTarget != null) {
                                    var11_11 = var8_8.mListAnchors[var14_14];
                                    var13_13 = var8_8.mListDimensionBehaviors[var2_2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var8_8.mResolvedMatchConstraintDefault[var2_2] == 0 ? 1 : 0;
                                    if (var13_13 != 0 && var17_17 == 0 && var11_11.mTarget.mOwner == var0) {
                                        var1_1.addEquality(var11_11.mSolverVariable, var11_11.mTarget.mSolverVariable, -var11_11.getMargin(), 5);
                                    } else if (var17_17 != 0 && var11_11.mTarget.mOwner == var0) {
                                        var1_1.addEquality(var11_11.mSolverVariable, var11_11.mTarget.mSolverVariable, -var11_11.getMargin(), 4);
                                    }
                                    var1_1.addLowerThan(var11_11.mSolverVariable, var6_6.mListAnchors[var14_14].mTarget.mSolverVariable, -var11_11.getMargin(), 6);
                                }
                            }
                            if (var12_12 != 0) {
                                var0 = var0.mListAnchors;
                                var13_13 = var3_3 + 1;
                                var1_1.addGreaterThan(var0[var13_13].mSolverVariable, var6_6.mListAnchors[var13_13].mSolverVariable, var6_6.mListAnchors[var13_13].getMargin(), 8);
                            }
                            if ((var0 = var4_4.mWeightedMatchConstraintsWidgets) != null && (var14_14 = var0.size()) > 1) {
                                var25_35 = var4_4.mHasUndefinedWeights != false && var4_4.mHasComplexMatchWeights == false ? (float)var4_4.mWidgetsMatchCount : var10_10;
                                var11_11 = null;
                                var26_36 = 0.0f;
                                for (var13_13 = 0; var13_13 < var14_14; ++var13_13) {
                                    var18_18 /* !! */  = (ConstraintWidget)var0.get(var13_13);
                                    var10_10 = var18_18 /* !! */ .mWeight[var2_2];
                                    if (var10_10 < 0.0f) {
                                        if (var4_4.mHasComplexMatchWeights) {
                                            var1_1.addEquality(var18_18 /* !! */ .mListAnchors[var3_3 + 1].mSolverVariable, var18_18 /* !! */ .mListAnchors[var3_3].mSolverVariable, 0, 4);
                                            continue;
                                        }
                                        var10_10 = 1.0f;
                                    }
                                    if (var10_10 == 0.0f) {
                                        var1_1.addEquality(var18_18 /* !! */ .mListAnchors[var3_3 + 1].mSolverVariable, var18_18 /* !! */ .mListAnchors[var3_3].mSolverVariable, 0, 8);
                                        continue;
                                    }
                                    if (var11_11 != null) {
                                        var20_20 = var11_11.mListAnchors[var3_3].mSolverVariable;
                                        var11_11 = var11_11.mListAnchors;
                                        var12_12 = var3_3 + 1;
                                        var11_11 = var11_11[var12_12].mSolverVariable;
                                        var24_28 = var18_18 /* !! */ .mListAnchors[var3_3].mSolverVariable;
                                        var27_37 = var18_18 /* !! */ .mListAnchors[var12_12].mSolverVariable;
                                        var28_38 = var1_1.createRow();
                                        var28_38.createRowEqualMatchDimensions(var26_36, var25_35, var10_10, (SolverVariable)var20_20, (SolverVariable)var11_11, var24_28, var27_37);
                                        var1_1.addConstraint((ArrayRow)var28_38);
                                    }
                                    var11_11 = var18_18 /* !! */ ;
                                    var26_36 = var10_10;
                                }
                            }
                            if (var7_7 == null || var7_7 != var8_8 && var17_17 == 0) break block64;
                            var0 = var5_5.mListAnchors[var3_3];
                            var4_4 = var6_6.mListAnchors;
                            var13_13 = var3_3 + 1;
                            var4_4 = var4_4[var13_13];
                            var0 = var0.mTarget != null ? var0.mTarget.mSolverVariable : null;
                            var4_4 = var4_4.mTarget != null ? var4_4.mTarget.mSolverVariable : null;
                            var18_18 /* !! */  = var7_7.mListAnchors[var3_3];
                            var11_11 = var8_8.mListAnchors[var13_13];
                            if (var0 != null && var4_4 != null) {
                                var10_10 = var2_2 == 0 ? var9_9.mHorizontalBiasPercent : var9_9.mVerticalBiasPercent;
                                var13_13 = var18_18 /* !! */ .getMargin();
                                var2_2 = var11_11.getMargin();
                                var1_1.addCentering(var18_18 /* !! */ .mSolverVariable, (SolverVariable)var0, var13_13, var10_10, (SolverVariable)var4_4, var11_11.mSolverVariable, var2_2, 7);
                            }
                            break block65;
                        }
                        if (var16_16 == 0 || var7_7 == null) break block66;
                        var12_12 = var4_4.mWidgetsMatchCount > 0 && var4_4.mWidgetsCount == var4_4.mWidgetsMatchCount ? 1 : 0;
                        var4_4 = var7_7;
                        var18_18 /* !! */  = var4_4;
                        break block67;
                    }
                    if (var15_15 == 0 || var7_7 == null) break block65;
                    var13_13 = var4_4.mWidgetsMatchCount > 0 && var4_4.mWidgetsCount == var4_4.mWidgetsMatchCount ? 1 : 0;
                    var11_11 = var4_4 = var7_7;
                    break block68;
                }
                while (var4_4 != null) {
                    var11_11 = var4_4.mNextChainWidget[var2_2];
                    while (var11_11 != null && var11_11.getVisibility() == 8) {
                        var11_11 = var11_11.mNextChainWidget[var2_2];
                    }
                    if (var11_11 != null || var4_4 == var8_8) {
                        var20_20 = var4_4.mListAnchors[var3_3];
                        var27_37 = var20_20.mSolverVariable;
                        var9_9 = var20_20.mTarget != null ? var20_20.mTarget.mSolverVariable : null;
                        if (var18_18 /* !! */  != var4_4) {
                            var0 = var18_18 /* !! */ .mListAnchors[var3_3 + 1].mSolverVariable;
                        } else {
                            var0 = var9_9;
                            if (var4_4 == var7_7) {
                                var0 = var9_9;
                                if (var18_18 /* !! */  == var4_4) {
                                    var0 = var5_5.mListAnchors[var3_3].mTarget != null ? var5_5.mListAnchors[var3_3].mTarget.mSolverVariable : null;
                                }
                            }
                        }
                        var17_17 = var20_20.getMargin();
                        var9_9 = var4_4.mListAnchors;
                        var23_23 = var3_3 + 1;
                        var14_14 = var9_9[var23_23].getMargin();
                        if (var11_11 != null) {
                            var9_9 = var11_11.mListAnchors[var3_3];
                            var24_30 = var9_9.mSolverVariable;
                            var20_20 = var4_4.mListAnchors[var23_23].mSolverVariable;
                        } else {
                            var28_38 = var6_6.mListAnchors[var23_23].mTarget;
                            var9_9 = var28_38 != null ? var28_38.mSolverVariable : null;
                            var20_20 = var4_4.mListAnchors[var23_23].mSolverVariable;
                            var24_31 /* !! */  = var9_9;
                            var9_9 = var28_38;
                        }
                        var13_13 = var14_14;
                        if (var9_9 != null) {
                            var13_13 = var14_14 + var9_9.getMargin();
                        }
                        var14_14 = var17_17;
                        if (var18_18 /* !! */  != null) {
                            var14_14 = var17_17 + var18_18 /* !! */ .mListAnchors[var23_23].getMargin();
                        }
                        if (var27_37 != null && var0 != null && var24_32 != null && var20_20 != null) {
                            if (var4_4 == var7_7) {
                                var14_14 = var7_7.mListAnchors[var3_3].getMargin();
                            }
                            if (var4_4 == var8_8) {
                                var13_13 = var8_8.mListAnchors[var23_23].getMargin();
                            }
                            var17_17 = var12_12 != 0 ? 8 : 5;
                            var1_1.addCentering(var27_37, (SolverVariable)var0, var14_14, 0.5f, (SolverVariable)var24_32, (SolverVariable)var20_20, var13_13, var17_17);
                        }
                    }
                    if (var4_4.getVisibility() != 8) {
                        var18_18 /* !! */  = var4_4;
                    }
                    var4_4 = var11_11;
                }
                break block65;
            }
            while (var4_4 != null) {
                var0 = var4_4.mNextChainWidget[var2_2];
                while (var0 != null && var0.getVisibility() == 8) {
                    var0 = var0.mNextChainWidget[var2_2];
                }
                if (var4_4 != var7_7 && var4_4 != var8_8 && var0 != null) {
                    if (var0 == var8_8) {
                        var0 = null;
                    }
                    var9_9 = var4_4.mListAnchors[var3_3];
                    var24_34 = var9_9.mSolverVariable;
                    if (var9_9.mTarget != null) {
                        var18_18 /* !! */  = var9_9.mTarget.mSolverVariable;
                    }
                    var18_18 /* !! */  = var11_11.mListAnchors;
                    var23_23 = var3_3 + 1;
                    var28_38 = var18_18 /* !! */ [var23_23].mSolverVariable;
                    var17_17 = var9_9.getMargin();
                    var12_12 = var4_4.mListAnchors[var23_23].getMargin();
                    if (var0 != null) {
                        var20_20 = var0.mListAnchors[var3_3];
                        var18_18 /* !! */  = var20_20.mSolverVariable;
                        var9_9 = var20_20.mTarget != null ? var20_20.mTarget.mSolverVariable : null;
                    } else {
                        var20_20 = var8_8.mListAnchors[var3_3];
                        var18_18 /* !! */  = var20_20 != null ? var20_20.mSolverVariable : null;
                        var9_9 = var4_4.mListAnchors[var23_23].mSolverVariable;
                    }
                    var14_14 = var12_12;
                    if (var20_20 != null) {
                        var14_14 = var12_12 + var20_20.getMargin();
                    }
                    var12_12 = var17_17;
                    if (var11_11 != null) {
                        var12_12 = var17_17 + var11_11.mListAnchors[var23_23].getMargin();
                    }
                    var17_17 = var13_13 != 0 ? 8 : 4;
                    if (var24_34 != null && var28_38 != null && var18_18 /* !! */  != null && var9_9 != null) {
                        var1_1.addCentering(var24_34, (SolverVariable)var28_38, var12_12, 0.5f, (SolverVariable)var18_18 /* !! */ , (SolverVariable)var9_9, var14_14, var17_17);
                    }
                }
                if (var4_4.getVisibility() == 8) {
                    var4_4 = var11_11;
                }
                var11_11 = var4_4;
                var4_4 = var0;
            }
            var0 = var7_7.mListAnchors[var3_3];
            var4_4 = var5_5.mListAnchors[var3_3].mTarget;
            var11_11 = var8_8.mListAnchors;
            var2_2 = var3_3 + 1;
            var9_9 = var11_11[var2_2];
            var11_11 = var6_6.mListAnchors[var2_2].mTarget;
            if (var4_4 != null) {
                if (var7_7 != var8_8) {
                    var1_1.addEquality(var0.mSolverVariable, var4_4.mSolverVariable, var0.getMargin(), 5);
                } else if (var11_11 != null) {
                    var1_1.addCentering(var0.mSolverVariable, var4_4.mSolverVariable, var0.getMargin(), 0.5f, var9_9.mSolverVariable, var11_11.mSolverVariable, var9_9.getMargin(), 5);
                }
            }
            if (var11_11 != null && var7_7 != var8_8) {
                var1_1.addEquality(var9_9.mSolverVariable, var11_11.mSolverVariable, -var9_9.getMargin(), 5);
            }
        }
        if (var16_16 == 0) {
            if (var15_15 == 0) return;
        }
        if (var7_7 == null) return;
        if (var7_7 == var8_8) return;
        var9_9 = var7_7.mListAnchors[var3_3];
        var0 = var8_8.mListAnchors;
        var13_13 = var3_3 + 1;
        var11_11 = var0[var13_13];
        var4_4 = var9_9.mTarget != null ? var9_9.mTarget.mSolverVariable : null;
        var0 = var11_11.mTarget != null ? var11_11.mTarget.mSolverVariable : null;
        if (var6_6 != var8_8) {
            var18_18 /* !! */  = var6_6.mListAnchors[var13_13];
            var0 = var19_19;
            if (var18_18 /* !! */ .mTarget != null) {
                var0 = var18_18 /* !! */ .mTarget.mSolverVariable;
            }
        }
        if (var7_7 == var8_8) {
            var9_9 = var7_7.mListAnchors[var3_3];
            var11_11 = var7_7.mListAnchors[var13_13];
        }
        if (var4_4 == null) return;
        if (var0 == null) return;
        var2_2 = var9_9.getMargin();
        var18_18 /* !! */  = var8_8 == null ? var6_6 : var8_8;
        var3_3 = var18_18 /* !! */ .mListAnchors[var13_13].getMargin();
        var1_1.addCentering(var9_9.mSolverVariable, (SolverVariable)var4_4, var2_2, 0.5f, (SolverVariable)var0, var11_11.mSolverVariable, var3_3, 5);
    }
}

