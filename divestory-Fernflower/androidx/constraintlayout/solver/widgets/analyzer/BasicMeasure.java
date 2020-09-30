package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import java.util.ArrayList;

public class BasicMeasure {
   public static final int AT_MOST = Integer.MIN_VALUE;
   private static final boolean DEBUG = false;
   public static final int EXACTLY = 1073741824;
   public static final int FIXED = -3;
   public static final int MATCH_PARENT = -1;
   private static final int MODE_SHIFT = 30;
   public static final int UNSPECIFIED = 0;
   public static final int WRAP_CONTENT = -2;
   private ConstraintWidgetContainer constraintWidgetContainer;
   private BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
   private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList();

   public BasicMeasure(ConstraintWidgetContainer var1) {
      this.constraintWidgetContainer = var1;
   }

   private boolean measure(BasicMeasure.Measurer var1, ConstraintWidget var2, boolean var3) {
      this.mMeasure.horizontalBehavior = var2.getHorizontalDimensionBehaviour();
      this.mMeasure.verticalBehavior = var2.getVerticalDimensionBehaviour();
      this.mMeasure.horizontalDimension = var2.getWidth();
      this.mMeasure.verticalDimension = var2.getHeight();
      this.mMeasure.measuredNeedsSolverPass = false;
      this.mMeasure.useCurrentDimensions = var3;
      boolean var4;
      if (this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5;
      if (this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var4 && var2.mDimensionRatio > 0.0F) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var5 && var2.mDimensionRatio > 0.0F) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var4 && var2.mResolvedMatchConstraintDefault[0] == 4) {
         this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      }

      if (var5 && var2.mResolvedMatchConstraintDefault[1] == 4) {
         this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      }

      var1.measure(var2, this.mMeasure);
      var2.setWidth(this.mMeasure.measuredWidth);
      var2.setHeight(this.mMeasure.measuredHeight);
      var2.setHasBaseline(this.mMeasure.measuredHasBaseline);
      var2.setBaselineDistance(this.mMeasure.measuredBaseline);
      this.mMeasure.useCurrentDimensions = false;
      return this.mMeasure.measuredNeedsSolverPass;
   }

   private void measureChildren(ConstraintWidgetContainer var1) {
      int var2 = var1.mChildren.size();
      BasicMeasure.Measurer var3 = var1.getMeasurer();

      for(int var4 = 0; var4 < var2; ++var4) {
         ConstraintWidget var5 = (ConstraintWidget)var1.mChildren.get(var4);
         if (!(var5 instanceof Guideline) && (!var5.horizontalRun.dimension.resolved || !var5.verticalRun.dimension.resolved)) {
            ConstraintWidget.DimensionBehaviour var6 = var5.getDimensionBehaviour(0);
            boolean var7 = true;
            ConstraintWidget.DimensionBehaviour var8 = var5.getDimensionBehaviour(1);
            if (var6 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var5.mMatchConstraintDefaultWidth == 1 || var8 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var5.mMatchConstraintDefaultHeight == 1) {
               var7 = false;
            }

            if (!var7) {
               this.measure(var3, var5, false);
               if (var1.mMetrics != null) {
                  Metrics var9 = var1.mMetrics;
                  ++var9.measuredWidgets;
               }
            }
         }
      }

      var3.didMeasures();
   }

   private void solveLinearSystem(ConstraintWidgetContainer var1, String var2, int var3, int var4) {
      int var5 = var1.getMinWidth();
      int var6 = var1.getMinHeight();
      var1.setMinWidth(0);
      var1.setMinHeight(0);
      var1.setWidth(var3);
      var1.setHeight(var4);
      var1.setMinWidth(var5);
      var1.setMinHeight(var6);
      this.constraintWidgetContainer.layout();
   }

   public long solverMeasure(ConstraintWidgetContainer var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      BasicMeasure.Measurer var11 = var1.getMeasurer();
      var10 = var1.mChildren.size();
      int var12 = var1.getWidth();
      int var13 = var1.getHeight();
      boolean var14 = Optimizer.enabled(var2, 128);
      boolean var25;
      if (!var14 && !Optimizer.enabled(var2, 64)) {
         var25 = false;
      } else {
         var25 = true;
      }

      boolean var26 = var25;
      ConstraintWidget var15;
      boolean var27;
      if (var25) {
         var3 = 0;

         while(true) {
            var26 = var25;
            if (var3 >= var10) {
               break;
            }

            var15 = (ConstraintWidget)var1.mChildren.get(var3);
            if (var15.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               var26 = true;
            } else {
               var26 = false;
            }

            if (var15.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               var27 = true;
            } else {
               var27 = false;
            }

            if (var26 && var27 && var15.getDimensionRatio() > 0.0F) {
               var26 = true;
            } else {
               var26 = false;
            }

            if (var15.isInHorizontalChain() && var26 || var15.isInVerticalChain() && var26 || var15 instanceof VirtualLayout || var15.isInHorizontalChain() || var15.isInVerticalChain()) {
               var26 = false;
               break;
            }

            ++var3;
         }
      }

      if (var26 && LinearSystem.sMetrics != null) {
         Metrics var30 = LinearSystem.sMetrics;
         ++var30.measures;
      }

      if ((var5 != 1073741824 || var7 != 1073741824) && !var14) {
         var25 = false;
      } else {
         var25 = true;
      }

      if (var26 & var25) {
         var2 = Math.min(var1.getMaxWidth(), var6);
         var3 = Math.min(var1.getMaxHeight(), var8);
         if (var5 == 1073741824 && var1.getWidth() != var2) {
            var1.setWidth(var2);
            var1.invalidateGraph();
         }

         if (var7 == 1073741824 && var1.getHeight() != var3) {
            var1.setHeight(var3);
            var1.invalidateGraph();
         }

         boolean var16;
         if (var5 == 1073741824 && var7 == 1073741824) {
            var16 = var1.directMeasure(var14);
            var2 = 2;
         } else {
            var16 = var1.directMeasureSetup(var14);
            if (var5 == 1073741824) {
               var16 &= var1.directMeasureWithOrientation(var14, 0);
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var7 == 1073741824) {
               var14 = var1.directMeasureWithOrientation(var14, 1);
               ++var2;
               var16 &= var14;
            }
         }

         var14 = var16;
         var3 = var2;
         if (var16) {
            if (var5 == 1073741824) {
               var14 = true;
            } else {
               var14 = false;
            }

            boolean var17;
            if (var7 == 1073741824) {
               var17 = true;
            } else {
               var17 = false;
            }

            var1.updateFromRuns(var14, var17);
            var14 = var16;
            var3 = var2;
         }
      } else {
         var14 = false;
         var3 = 0;
      }

      if (!var14 || var3 != 2) {
         if (var10 > 0) {
            this.measureChildren(var1);
         }

         var4 = var1.getOptimizationLevel();
         int var18 = this.mVariableDimensionsWidgets.size();
         if (var10 > 0) {
            this.solveLinearSystem(var1, "First pass", var12, var13);
         }

         var2 = var4;
         if (var18 > 0) {
            if (var1.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var27 = true;
            } else {
               var27 = false;
            }

            boolean var28;
            if (var1.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var28 = true;
            } else {
               var28 = false;
            }

            var3 = Math.max(var1.getWidth(), this.constraintWidgetContainer.getMinWidth());
            var2 = Math.max(var1.getHeight(), this.constraintWidgetContainer.getMinHeight());
            int var19 = 0;

            int var20;
            Metrics var21;
            int var22;
            for(var7 = 0; var19 < var18; var7 = var8) {
               var15 = (ConstraintWidget)this.mVariableDimensionsWidgets.get(var19);
               if (!(var15 instanceof VirtualLayout)) {
                  var8 = var7;
               } else {
                  var20 = var15.getWidth();
                  var12 = var15.getHeight();
                  var8 = var7 | this.measure(var11, var15, true);
                  if (var1.mMetrics != null) {
                     var21 = var1.mMetrics;
                     ++var21.measuredMatchWidgets;
                  }

                  var22 = var15.getWidth();
                  var13 = var15.getHeight();
                  var7 = var3;
                  if (var22 != var20) {
                     var15.setWidth(var22);
                     var7 = var3;
                     if (var27) {
                        var7 = var3;
                        if (var15.getRight() > var3) {
                           var7 = Math.max(var3, var15.getRight() + var15.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                        }
                     }

                     var8 = 1;
                  }

                  var3 = var2;
                  if (var13 != var12) {
                     var15.setHeight(var13);
                     var3 = var2;
                     if (var28) {
                        var3 = var2;
                        if (var15.getBottom() > var2) {
                           var3 = Math.max(var2, var15.getBottom() + var15.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                        }
                     }

                     var8 = 1;
                  }

                  var8 |= ((VirtualLayout)var15).needSolverPass();
                  var2 = var3;
                  var3 = var7;
               }

               ++var19;
            }

            byte var29 = 0;
            var19 = var13;
            var12 = var12;
            var6 = var7;
            var8 = var2;
            var5 = var18;
            var7 = var29;

            while(true) {
               if (var7 >= 2) {
                  if (var6 != 0) {
                     this.solveLinearSystem(var1, "2nd pass", var12, var19);
                     if (var1.getWidth() < var3) {
                        var1.setWidth(var3);
                        var25 = true;
                     } else {
                        var25 = false;
                     }

                     if (var1.getHeight() < var8) {
                        var1.setHeight(var8);
                        var25 = true;
                     }

                     if (var25) {
                        this.solveLinearSystem(var1, "3rd pass", var12, var19);
                     }
                  }

                  var2 = var4;
                  break;
               }

               var18 = 0;

               for(var2 = var8; var18 < var5; var6 = var7) {
                  var15 = (ConstraintWidget)this.mVariableDimensionsWidgets.get(var18);
                  if ((!(var15 instanceof Helper) || var15 instanceof VirtualLayout) && !(var15 instanceof Guideline) && var15.getVisibility() != 8 && (!var15.horizontalRun.dimension.resolved || !var15.verticalRun.dimension.resolved) && !(var15 instanceof VirtualLayout)) {
                     var8 = var15.getWidth();
                     var20 = var15.getHeight();
                     var22 = var15.getBaselineDistance();
                     var7 = var6 | this.measure(var11, var15, true);
                     if (var1.mMetrics != null) {
                        var21 = var1.mMetrics;
                        ++var21.measuredMatchWidgets;
                     }

                     int var23 = var15.getWidth();
                     int var24 = var15.getHeight();
                     var6 = var3;
                     if (var23 != var8) {
                        var15.setWidth(var23);
                        var6 = var3;
                        if (var27) {
                           var6 = var3;
                           if (var15.getRight() > var3) {
                              var6 = Math.max(var3, var15.getRight() + var15.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                           }
                        }

                        var7 = 1;
                     }

                     var3 = var2;
                     var8 = var7;
                     if (var24 != var20) {
                        var15.setHeight(var24);
                        var3 = var2;
                        if (var28) {
                           var3 = var2;
                           if (var15.getBottom() > var2) {
                              var3 = Math.max(var2, var15.getBottom() + var15.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                           }
                        }

                        var8 = 1;
                     }

                     var20 = var6;
                     var2 = var3;
                     var7 = var8;
                     if (var15.hasBaseline()) {
                        var20 = var6;
                        var2 = var3;
                        var7 = var8;
                        if (var22 != var15.getBaselineDistance()) {
                           var7 = 1;
                           var2 = var3;
                           var20 = var6;
                        }
                     }
                  } else {
                     var20 = var3;
                     var7 = var6;
                  }

                  ++var18;
                  var3 = var20;
               }

               if (var6 != 0) {
                  this.solveLinearSystem(var1, "intermediate pass", var12, var19);
                  var6 = 0;
               }

               ++var7;
               var8 = var2;
            }
         }

         var1.setOptimizationLevel(var2);
      }

      return 0L;
   }

   public void updateHierarchy(ConstraintWidgetContainer var1) {
      this.mVariableDimensionsWidgets.clear();
      int var2 = var1.mChildren.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         ConstraintWidget var4 = (ConstraintWidget)var1.mChildren.get(var3);
         if (var4.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var4.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT || var4.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var4.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            this.mVariableDimensionsWidgets.add(var4);
         }
      }

      var1.invalidateGraph();
   }

   public static class Measure {
      public ConstraintWidget.DimensionBehaviour horizontalBehavior;
      public int horizontalDimension;
      public int measuredBaseline;
      public boolean measuredHasBaseline;
      public int measuredHeight;
      public boolean measuredNeedsSolverPass;
      public int measuredWidth;
      public boolean useCurrentDimensions;
      public ConstraintWidget.DimensionBehaviour verticalBehavior;
      public int verticalDimension;
   }

   public static enum MeasureType {
   }

   public interface Measurer {
      void didMeasures();

      void measure(ConstraintWidget var1, BasicMeasure.Measure var2);
   }
}
