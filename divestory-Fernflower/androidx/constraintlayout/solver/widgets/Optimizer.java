package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;

public class Optimizer {
   static final int FLAG_CHAIN_DANGLING = 1;
   static final int FLAG_RECOMPUTE_BOUNDS = 2;
   static final int FLAG_USE_OPTIMIZE = 0;
   public static final int OPTIMIZATION_BARRIER = 2;
   public static final int OPTIMIZATION_CHAIN = 4;
   public static final int OPTIMIZATION_DIMENSIONS = 8;
   public static final int OPTIMIZATION_DIRECT = 1;
   public static final int OPTIMIZATION_ENGINE = 256;
   public static final int OPTIMIZATION_GRAPH = 64;
   public static final int OPTIMIZATION_GRAPH_WRAP = 128;
   public static final int OPTIMIZATION_GROUPS = 32;
   public static final int OPTIMIZATION_NONE = 0;
   public static final int OPTIMIZATION_RATIO = 16;
   public static final int OPTIMIZATION_STANDARD = 263;
   static boolean[] flags = new boolean[3];

   static void checkMatchParent(ConstraintWidgetContainer var0, LinearSystem var1, ConstraintWidget var2) {
      var2.mHorizontalResolution = -1;
      var2.mVerticalResolution = -1;
      int var3;
      int var4;
      if (var0.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && var2.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
         var3 = var2.mLeft.mMargin;
         var4 = var0.getWidth() - var2.mRight.mMargin;
         var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
         var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
         var1.addEquality(var2.mLeft.mSolverVariable, var3);
         var1.addEquality(var2.mRight.mSolverVariable, var4);
         var2.mHorizontalResolution = 2;
         var2.setHorizontalDimension(var3, var4);
      }

      if (var0.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && var2.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
         var4 = var2.mTop.mMargin;
         var3 = var0.getHeight() - var2.mBottom.mMargin;
         var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
         var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
         var1.addEquality(var2.mTop.mSolverVariable, var4);
         var1.addEquality(var2.mBottom.mSolverVariable, var3);
         if (var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
            var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
            var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var4);
         }

         var2.mVerticalResolution = 2;
         var2.setVerticalDimension(var4, var3);
      }

   }

   public static final boolean enabled(int var0, int var1) {
      boolean var2;
      if ((var0 & var1) == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
