package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.HashMap;

public class Barrier extends HelperWidget {
   public static final int BOTTOM = 3;
   public static final int LEFT = 0;
   public static final int RIGHT = 1;
   public static final int TOP = 2;
   private boolean mAllowsGoneWidget = true;
   private int mBarrierType = 0;
   private int mMargin = 0;

   public void addToSolver(LinearSystem var1) {
      this.mListAnchors[0] = this.mLeft;
      this.mListAnchors[2] = this.mTop;
      this.mListAnchors[1] = this.mRight;
      this.mListAnchors[3] = this.mBottom;

      int var2;
      for(var2 = 0; var2 < this.mListAnchors.length; ++var2) {
         this.mListAnchors[var2].mSolverVariable = var1.createObjectVariable(this.mListAnchors[var2]);
      }

      var2 = this.mBarrierType;
      if (var2 >= 0 && var2 < 4) {
         ConstraintAnchor var3 = this.mListAnchors[this.mBarrierType];
         var2 = 0;

         int var5;
         boolean var6;
         label155: {
            while(true) {
               if (var2 >= this.mWidgetsCount) {
                  var6 = false;
                  break label155;
               }

               ConstraintWidget var4 = this.mWidgets[var2];
               if (this.mAllowsGoneWidget || var4.allowedInBarrier()) {
                  var5 = this.mBarrierType;
                  if ((var5 == 0 || var5 == 1) && var4.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var4.mLeft.mTarget != null && var4.mRight.mTarget != null) {
                     break;
                  }

                  var5 = this.mBarrierType;
                  if ((var5 == 2 || var5 == 3) && var4.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var4.mTop.mTarget != null && var4.mBottom.mTarget != null) {
                     break;
                  }
               }

               ++var2;
            }

            var6 = true;
         }

         boolean var10;
         if (!this.mLeft.hasCenteredDependents() && !this.mRight.hasCenteredDependents()) {
            var10 = false;
         } else {
            var10 = true;
         }

         boolean var13;
         if (!this.mTop.hasCenteredDependents() && !this.mBottom.hasCenteredDependents()) {
            var13 = false;
         } else {
            var13 = true;
         }

         if (var6 || (this.mBarrierType != 0 || !var10) && (this.mBarrierType != 2 || !var13) && (this.mBarrierType != 1 || !var10) && (this.mBarrierType != 3 || !var13)) {
            var13 = false;
         } else {
            var13 = true;
         }

         byte var12 = 5;
         if (!var13) {
            var12 = 4;
         }

         for(var5 = 0; var5 < this.mWidgetsCount; ++var5) {
            ConstraintWidget var7 = this.mWidgets[var5];
            if (this.mAllowsGoneWidget || var7.allowedInBarrier()) {
               SolverVariable var11 = var1.createObjectVariable(var7.mListAnchors[this.mBarrierType]);
               var7.mListAnchors[this.mBarrierType].mSolverVariable = var11;
               int var8;
               if (var7.mListAnchors[this.mBarrierType].mTarget != null && var7.mListAnchors[this.mBarrierType].mTarget.mOwner == this) {
                  var8 = var7.mListAnchors[this.mBarrierType].mMargin + 0;
               } else {
                  var8 = 0;
               }

               int var9 = this.mBarrierType;
               if (var9 != 0 && var9 != 2) {
                  var1.addGreaterBarrier(var3.mSolverVariable, var11, this.mMargin + var8, var6);
               } else {
                  var1.addLowerBarrier(var3.mSolverVariable, var11, this.mMargin - var8, var6);
               }

               var1.addEquality(var3.mSolverVariable, var11, this.mMargin + var8, var12);
            }
         }

         var2 = this.mBarrierType;
         if (var2 == 0) {
            var1.addEquality(this.mRight.mSolverVariable, this.mLeft.mSolverVariable, 0, 8);
            var1.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 4);
            var1.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 0);
         } else if (var2 == 1) {
            var1.addEquality(this.mLeft.mSolverVariable, this.mRight.mSolverVariable, 0, 8);
            var1.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 4);
            var1.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 0);
         } else if (var2 == 2) {
            var1.addEquality(this.mBottom.mSolverVariable, this.mTop.mSolverVariable, 0, 8);
            var1.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 4);
            var1.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 0);
         } else if (var2 == 3) {
            var1.addEquality(this.mTop.mSolverVariable, this.mBottom.mSolverVariable, 0, 8);
            var1.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 4);
            var1.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 0);
         }
      }

   }

   public boolean allowedInBarrier() {
      return true;
   }

   public boolean allowsGoneWidget() {
      return this.mAllowsGoneWidget;
   }

   public void copy(ConstraintWidget var1, HashMap<ConstraintWidget, ConstraintWidget> var2) {
      super.copy(var1, var2);
      Barrier var3 = (Barrier)var1;
      this.mBarrierType = var3.mBarrierType;
      this.mAllowsGoneWidget = var3.mAllowsGoneWidget;
      this.mMargin = var3.mMargin;
   }

   public int getBarrierType() {
      return this.mBarrierType;
   }

   public int getMargin() {
      return this.mMargin;
   }

   protected void markWidgets() {
      for(int var1 = 0; var1 < this.mWidgetsCount; ++var1) {
         ConstraintWidget var2 = this.mWidgets[var1];
         int var3 = this.mBarrierType;
         if (var3 != 0 && var3 != 1) {
            if (var3 == 2 || var3 == 3) {
               var2.setInBarrier(1, true);
            }
         } else {
            var2.setInBarrier(0, true);
         }
      }

   }

   public void setAllowsGoneWidget(boolean var1) {
      this.mAllowsGoneWidget = var1;
   }

   public void setBarrierType(int var1) {
      this.mBarrierType = var1;
   }

   public void setMargin(int var1) {
      this.mMargin = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[Barrier] ");
      var1.append(this.getDebugName());
      var1.append(" {");
      String var5 = var1.toString();

      StringBuilder var6;
      for(int var2 = 0; var2 < this.mWidgetsCount; ++var2) {
         ConstraintWidget var3 = this.mWidgets[var2];
         String var4 = var5;
         if (var2 > 0) {
            var6 = new StringBuilder();
            var6.append(var5);
            var6.append(", ");
            var4 = var6.toString();
         }

         var1 = new StringBuilder();
         var1.append(var4);
         var1.append(var3.getDebugName());
         var5 = var1.toString();
      }

      var6 = new StringBuilder();
      var6.append(var5);
      var6.append("}");
      return var6.toString();
   }
}
