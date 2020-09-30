package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.ArrayList;

class Chain {
   private static final boolean DEBUG = false;

   static void applyChainConstraints(ConstraintWidgetContainer var0, LinearSystem var1, int var2) {
      int var3 = 0;
      int var4;
      ChainHead[] var5;
      byte var6;
      if (var2 == 0) {
         var4 = var0.mHorizontalChainsSize;
         var5 = var0.mHorizontalChainsArray;
         var6 = 0;
      } else {
         var6 = 2;
         var4 = var0.mVerticalChainsSize;
         var5 = var0.mVerticalChainsArray;
      }

      while(var3 < var4) {
         ChainHead var7 = var5[var3];
         var7.define();
         applyChainConstraints(var0, var1, var2, var6, var7);
         ++var3;
      }

   }

   static void applyChainConstraints(ConstraintWidgetContainer var0, LinearSystem var1, int var2, int var3, ChainHead var4) {
      ConstraintWidget var5 = var4.mFirst;
      ConstraintWidget var6 = var4.mLast;
      ConstraintWidget var7 = var4.mFirstVisibleWidget;
      ConstraintWidget var8 = var4.mLastVisibleWidget;
      ConstraintWidget var9 = var4.mHead;
      float var10 = var4.mTotalWeight;
      ConstraintWidget var11 = var4.mFirstMatchConstraintWidget;
      var11 = var4.mLastMatchConstraintWidget;
      boolean var12;
      if (var0.mListDimensionBehaviors[var2] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
         var12 = true;
      } else {
         var12 = false;
      }

      boolean var13;
      int var14;
      boolean var15;
      boolean var17;
      label474: {
         label473: {
            int var16;
            if (var2 == 0) {
               if (var9.mHorizontalChainStyle == 0) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (var9.mHorizontalChainStyle == 1) {
                  var14 = 1;
               } else {
                  var14 = 0;
               }

               var15 = var13;
               var16 = var14;
               if (var9.mHorizontalChainStyle == 2) {
                  break label473;
               }
            } else {
               if (var9.mVerticalChainStyle == 0) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (var9.mVerticalChainStyle == 1) {
                  var14 = 1;
               } else {
                  var14 = 0;
               }

               var15 = var13;
               var16 = var14;
               if (var9.mVerticalChainStyle == 2) {
                  break label473;
               }
            }

            var17 = false;
            var14 = var16;
            break label474;
         }

         var17 = true;
         var15 = var13;
      }

      ConstraintWidget var18 = var5;
      var13 = false;

      while(true) {
         Object var19 = null;
         SolverVariable var20 = null;
         int var23;
         ConstraintAnchor var40;
         if (var13) {
            ConstraintAnchor[] var43;
            if (var8 != null) {
               var43 = var6.mListAnchors;
               var14 = var3 + 1;
               if (var43[var14].mTarget != null) {
                  var40 = var8.mListAnchors[var14];
                  if (var8.mListDimensionBehaviors[var2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var8.mResolvedMatchConstraintDefault[var2] == 0) {
                     var13 = true;
                  } else {
                     var13 = false;
                  }

                  if (var13 && !var17 && var40.mTarget.mOwner == var0) {
                     var1.addEquality(var40.mSolverVariable, var40.mTarget.mSolverVariable, -var40.getMargin(), 5);
                  } else if (var17 && var40.mTarget.mOwner == var0) {
                     var1.addEquality(var40.mSolverVariable, var40.mTarget.mSolverVariable, -var40.getMargin(), 4);
                  }

                  var1.addLowerThan(var40.mSolverVariable, var6.mListAnchors[var14].mTarget.mSolverVariable, -var40.getMargin(), 6);
               }
            }

            ConstraintAnchor[] var29;
            int var45;
            if (var12) {
               var29 = var0.mListAnchors;
               var45 = var3 + 1;
               var1.addGreaterThan(var29[var45].mSolverVariable, var6.mListAnchors[var45].mSolverVariable, var6.mListAnchors[var45].getMargin(), 8);
            }

            ArrayList var30 = var4.mWeightedMatchConstraintsWidgets;
            SolverVariable var27;
            int var42;
            SolverVariable var53;
            if (var30 != null) {
               var14 = var30.size();
               if (var14 > 1) {
                  float var25;
                  if (var4.mHasUndefinedWeights && !var4.mHasComplexMatchWeights) {
                     var25 = (float)var4.mWidgetsMatchCount;
                  } else {
                     var25 = var10;
                  }

                  var11 = null;
                  var45 = 0;

                  for(float var26 = 0.0F; var45 < var14; ++var45) {
                     var18 = (ConstraintWidget)var30.get(var45);
                     var10 = var18.mWeight[var2];
                     if (var10 < 0.0F) {
                        if (var4.mHasComplexMatchWeights) {
                           var1.addEquality(var18.mListAnchors[var3 + 1].mSolverVariable, var18.mListAnchors[var3].mSolverVariable, 0, 4);
                           continue;
                        }

                        var10 = 1.0F;
                     }

                     if (var10 == 0.0F) {
                        var1.addEquality(var18.mListAnchors[var3 + 1].mSolverVariable, var18.mListAnchors[var3].mSolverVariable, 0, 8);
                     } else {
                        if (var11 != null) {
                           var20 = var11.mListAnchors[var3].mSolverVariable;
                           var43 = var11.mListAnchors;
                           var42 = var3 + 1;
                           SolverVariable var44 = var43[var42].mSolverVariable;
                           var53 = var18.mListAnchors[var3].mSolverVariable;
                           var27 = var18.mListAnchors[var42].mSolverVariable;
                           ArrayRow var28 = var1.createRow();
                           var28.createRowEqualMatchDimensions(var26, var25, var10, var20, var44, var53, var27);
                           var1.addConstraint(var28);
                        }

                        var11 = var18;
                        var26 = var10;
                     }
                  }
               }
            }

            ConstraintAnchor var32;
            SolverVariable var33;
            ConstraintAnchor var35;
            SolverVariable var37;
            ConstraintAnchor var38;
            ConstraintAnchor var51;
            if (var7 != null && (var7 == var8 || var17)) {
               var32 = var5.mListAnchors[var3];
               ConstraintAnchor[] var36 = var6.mListAnchors;
               var45 = var3 + 1;
               var35 = var36[var45];
               if (var32.mTarget != null) {
                  var33 = var32.mTarget.mSolverVariable;
               } else {
                  var33 = null;
               }

               if (var35.mTarget != null) {
                  var37 = var35.mTarget.mSolverVariable;
               } else {
                  var37 = null;
               }

               var51 = var7.mListAnchors[var3];
               var40 = var8.mListAnchors[var45];
               if (var33 != null && var37 != null) {
                  if (var2 == 0) {
                     var10 = var9.mHorizontalBiasPercent;
                  } else {
                     var10 = var9.mVerticalBiasPercent;
                  }

                  var45 = var51.getMargin();
                  var2 = var40.getMargin();
                  var1.addCentering(var51.mSolverVariable, var33, var45, var10, var37, var40.mSolverVariable, var2, 7);
               }
            } else {
               ConstraintWidget var34;
               SolverVariable var39;
               int var47;
               byte var49;
               ConstraintAnchor var50;
               if (var15 && var7 != null) {
                  if (var4.mWidgetsMatchCount > 0 && var4.mWidgetsCount == var4.mWidgetsMatchCount) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  var34 = var7;

                  for(var18 = var7; var34 != null; var34 = var11) {
                     for(var11 = var34.mNextChainWidget[var2]; var11 != null && var11.getVisibility() == 8; var11 = var11.mNextChainWidget[var2]) {
                     }

                     if (var11 != null || var34 == var8) {
                        var50 = var34.mListAnchors[var3];
                        var27 = var50.mSolverVariable;
                        if (var50.mTarget != null) {
                           var39 = var50.mTarget.mSolverVariable;
                        } else {
                           var39 = null;
                        }

                        if (var18 != var34) {
                           var33 = var18.mListAnchors[var3 + 1].mSolverVariable;
                        } else {
                           var33 = var39;
                           if (var34 == var7) {
                              var33 = var39;
                              if (var18 == var34) {
                                 if (var5.mListAnchors[var3].mTarget != null) {
                                    var33 = var5.mListAnchors[var3].mTarget.mSolverVariable;
                                 } else {
                                    var33 = null;
                                 }
                              }
                           }
                        }

                        var47 = var50.getMargin();
                        ConstraintAnchor[] var41 = var34.mListAnchors;
                        var23 = var3 + 1;
                        var14 = var41[var23].getMargin();
                        if (var11 != null) {
                           var38 = var11.mListAnchors[var3];
                           var53 = var38.mSolverVariable;
                           var20 = var34.mListAnchors[var23].mSolverVariable;
                        } else {
                           ConstraintAnchor var55 = var6.mListAnchors[var23].mTarget;
                           if (var55 != null) {
                              var39 = var55.mSolverVariable;
                           } else {
                              var39 = null;
                           }

                           var20 = var34.mListAnchors[var23].mSolverVariable;
                           var53 = var39;
                           var38 = var55;
                        }

                        var45 = var14;
                        if (var38 != null) {
                           var45 = var14 + var38.getMargin();
                        }

                        var14 = var47;
                        if (var18 != null) {
                           var14 = var47 + var18.mListAnchors[var23].getMargin();
                        }

                        if (var27 != null && var33 != null && var53 != null && var20 != null) {
                           if (var34 == var7) {
                              var14 = var7.mListAnchors[var3].getMargin();
                           }

                           if (var34 == var8) {
                              var45 = var8.mListAnchors[var23].getMargin();
                           }

                           if (var12) {
                              var49 = 8;
                           } else {
                              var49 = 5;
                           }

                           var1.addCentering(var27, var33, var14, 0.5F, var53, var20, var45, var49);
                        }
                     }

                     if (var34.getVisibility() != 8) {
                        var18 = var34;
                     }
                  }
               } else if (var14 != 0 && var7 != null) {
                  if (var4.mWidgetsMatchCount > 0 && var4.mWidgetsCount == var4.mWidgetsMatchCount) {
                     var13 = true;
                  } else {
                     var13 = false;
                  }

                  var34 = var7;

                  ConstraintWidget var31;
                  for(var11 = var7; var34 != null; var34 = var31) {
                     for(var31 = var34.mNextChainWidget[var2]; var31 != null && var31.getVisibility() == 8; var31 = var31.mNextChainWidget[var2]) {
                     }

                     if (var34 != var7 && var34 != var8 && var31 != null) {
                        if (var31 == var8) {
                           var31 = null;
                        }

                        var38 = var34.mListAnchors[var3];
                        var53 = var38.mSolverVariable;
                        SolverVariable var46;
                        if (var38.mTarget != null) {
                           var46 = var38.mTarget.mSolverVariable;
                        }

                        ConstraintAnchor[] var48 = var11.mListAnchors;
                        var23 = var3 + 1;
                        SolverVariable var54 = var48[var23].mSolverVariable;
                        var47 = var38.getMargin();
                        var42 = var34.mListAnchors[var23].getMargin();
                        if (var31 != null) {
                           var50 = var31.mListAnchors[var3];
                           var46 = var50.mSolverVariable;
                           if (var50.mTarget != null) {
                              var39 = var50.mTarget.mSolverVariable;
                           } else {
                              var39 = null;
                           }
                        } else {
                           var50 = var8.mListAnchors[var3];
                           if (var50 != null) {
                              var46 = var50.mSolverVariable;
                           } else {
                              var46 = null;
                           }

                           var39 = var34.mListAnchors[var23].mSolverVariable;
                        }

                        var14 = var42;
                        if (var50 != null) {
                           var14 = var42 + var50.getMargin();
                        }

                        var42 = var47;
                        if (var11 != null) {
                           var42 = var47 + var11.mListAnchors[var23].getMargin();
                        }

                        if (var13) {
                           var49 = 8;
                        } else {
                           var49 = 4;
                        }

                        if (var53 != null && var54 != null && var46 != null && var39 != null) {
                           var1.addCentering(var53, var54, var42, 0.5F, var46, var39, var14, var49);
                        }
                     }

                     if (var34.getVisibility() == 8) {
                        var34 = var11;
                     }

                     var11 = var34;
                  }

                  var32 = var7.mListAnchors[var3];
                  var35 = var5.mListAnchors[var3].mTarget;
                  var43 = var8.mListAnchors;
                  var2 = var3 + 1;
                  var38 = var43[var2];
                  var40 = var6.mListAnchors[var2].mTarget;
                  if (var35 != null) {
                     if (var7 != var8) {
                        var1.addEquality(var32.mSolverVariable, var35.mSolverVariable, var32.getMargin(), 5);
                     } else if (var40 != null) {
                        var1.addCentering(var32.mSolverVariable, var35.mSolverVariable, var32.getMargin(), 0.5F, var38.mSolverVariable, var40.mSolverVariable, var38.getMargin(), 5);
                     }
                  }

                  if (var40 != null && var7 != var8) {
                     var1.addEquality(var38.mSolverVariable, var40.mSolverVariable, -var38.getMargin(), 5);
                  }
               }
            }

            if ((var15 || var14 != 0) && var7 != null && var7 != var8) {
               var38 = var7.mListAnchors[var3];
               var29 = var8.mListAnchors;
               var45 = var3 + 1;
               var40 = var29[var45];
               if (var38.mTarget != null) {
                  var37 = var38.mTarget.mSolverVariable;
               } else {
                  var37 = null;
               }

               if (var40.mTarget != null) {
                  var33 = var40.mTarget.mSolverVariable;
               } else {
                  var33 = null;
               }

               if (var6 != var8) {
                  var51 = var6.mListAnchors[var45];
                  var33 = (SolverVariable)var19;
                  if (var51.mTarget != null) {
                     var33 = var51.mTarget.mSolverVariable;
                  }
               }

               if (var7 == var8) {
                  var38 = var7.mListAnchors[var3];
                  var40 = var7.mListAnchors[var45];
               }

               if (var37 != null && var33 != null) {
                  var2 = var38.getMargin();
                  if (var8 == null) {
                     var18 = var6;
                  } else {
                     var18 = var8;
                  }

                  var3 = var18.mListAnchors[var45].getMargin();
                  var1.addCentering(var38.mSolverVariable, var37, var2, 0.5F, var33, var40.mSolverVariable, var3, 5);
               }
            }

            return;
         }

         var40 = var18.mListAnchors[var3];
         if (var17) {
            var14 = 1;
         } else {
            var14 = 4;
         }

         int var21 = var40.getMargin();
         boolean var22;
         if (var18.mListDimensionBehaviors[var2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var18.mResolvedMatchConstraintDefault[var2] == 0) {
            var22 = true;
         } else {
            var22 = false;
         }

         var23 = var21;
         if (var40.mTarget != null) {
            var23 = var21;
            if (var18 != var5) {
               var23 = var21 + var40.mTarget.getMargin();
            }
         }

         if (var17 && var18 != var5 && var18 != var7) {
            var14 = 5;
         }

         if (var40.mTarget != null) {
            if (var18 == var7) {
               var1.addGreaterThan(var40.mSolverVariable, var40.mTarget.mSolverVariable, var23, 6);
            } else {
               var1.addGreaterThan(var40.mSolverVariable, var40.mTarget.mSolverVariable, var23, 8);
            }

            if (var22 && !var17) {
               var14 = 5;
            }

            var1.addEquality(var40.mSolverVariable, var40.mTarget.mSolverVariable, var23, var14);
         }

         if (var12) {
            if (var18.getVisibility() != 8 && var18.mListDimensionBehaviors[var2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               var1.addGreaterThan(var18.mListAnchors[var3 + 1].mSolverVariable, var18.mListAnchors[var3].mSolverVariable, 0, 5);
            }

            var1.addGreaterThan(var18.mListAnchors[var3].mSolverVariable, var0.mListAnchors[var3].mSolverVariable, 0, 8);
         }

         ConstraintAnchor var24 = var18.mListAnchors[var3 + 1].mTarget;
         var11 = var20;
         if (var24 != null) {
            ConstraintWidget var52 = var24.mOwner;
            var11 = var20;
            if (var52.mListAnchors[var3].mTarget != null) {
               if (var52.mListAnchors[var3].mTarget.mOwner != var18) {
                  var11 = var20;
               } else {
                  var11 = var52;
               }
            }
         }

         if (var11 != null) {
            var18 = var11;
         } else {
            var13 = true;
         }
      }
   }
}
