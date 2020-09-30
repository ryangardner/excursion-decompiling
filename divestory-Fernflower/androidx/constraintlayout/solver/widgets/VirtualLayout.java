package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;

public class VirtualLayout extends HelperWidget {
   protected BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
   private int mMeasuredHeight = 0;
   private int mMeasuredWidth = 0;
   BasicMeasure.Measurer mMeasurer = null;
   private boolean mNeedsCallFromSolver = false;
   private int mPaddingBottom = 0;
   private int mPaddingEnd = 0;
   private int mPaddingLeft = 0;
   private int mPaddingRight = 0;
   private int mPaddingStart = 0;
   private int mPaddingTop = 0;
   private int mResolvedPaddingLeft = 0;
   private int mResolvedPaddingRight = 0;

   public void applyRtl(boolean var1) {
      if (this.mPaddingStart > 0 || this.mPaddingEnd > 0) {
         if (var1) {
            this.mResolvedPaddingLeft = this.mPaddingEnd;
            this.mResolvedPaddingRight = this.mPaddingStart;
         } else {
            this.mResolvedPaddingLeft = this.mPaddingStart;
            this.mResolvedPaddingRight = this.mPaddingEnd;
         }
      }

   }

   public void captureWidgets() {
      for(int var1 = 0; var1 < this.mWidgetsCount; ++var1) {
         ConstraintWidget var2 = this.mWidgets[var1];
         if (var2 != null) {
            var2.setInVirtualLayout(true);
         }
      }

   }

   public int getMeasuredHeight() {
      return this.mMeasuredHeight;
   }

   public int getMeasuredWidth() {
      return this.mMeasuredWidth;
   }

   public int getPaddingBottom() {
      return this.mPaddingBottom;
   }

   public int getPaddingLeft() {
      return this.mResolvedPaddingLeft;
   }

   public int getPaddingRight() {
      return this.mResolvedPaddingRight;
   }

   public int getPaddingTop() {
      return this.mPaddingTop;
   }

   public void measure(int var1, int var2, int var3, int var4) {
   }

   protected void measure(ConstraintWidget var1, ConstraintWidget.DimensionBehaviour var2, int var3, ConstraintWidget.DimensionBehaviour var4, int var5) {
      while(this.mMeasurer == null && this.getParent() != null) {
         this.mMeasurer = ((ConstraintWidgetContainer)this.getParent()).getMeasurer();
      }

      this.mMeasure.horizontalBehavior = var2;
      this.mMeasure.verticalBehavior = var4;
      this.mMeasure.horizontalDimension = var3;
      this.mMeasure.verticalDimension = var5;
      this.mMeasurer.measure(var1, this.mMeasure);
      var1.setWidth(this.mMeasure.measuredWidth);
      var1.setHeight(this.mMeasure.measuredHeight);
      var1.setHasBaseline(this.mMeasure.measuredHasBaseline);
      var1.setBaselineDistance(this.mMeasure.measuredBaseline);
   }

   protected boolean measureChildren() {
      BasicMeasure.Measurer var1;
      if (this.mParent != null) {
         var1 = ((ConstraintWidgetContainer)this.mParent).getMeasurer();
      } else {
         var1 = null;
      }

      if (var1 == null) {
         return false;
      } else {
         int var2 = 0;

         while(true) {
            int var3 = this.mWidgetsCount;
            boolean var4 = true;
            if (var2 >= var3) {
               return true;
            }

            ConstraintWidget var5 = this.mWidgets[var2];
            if (var5 != null && !(var5 instanceof Guideline)) {
               ConstraintWidget.DimensionBehaviour var6 = var5.getDimensionBehaviour(0);
               ConstraintWidget.DimensionBehaviour var7 = var5.getDimensionBehaviour(1);
               if (var6 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var5.mMatchConstraintDefaultWidth == 1 || var7 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var5.mMatchConstraintDefaultHeight == 1) {
                  var4 = false;
               }

               if (!var4) {
                  ConstraintWidget.DimensionBehaviour var8 = var6;
                  if (var6 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var8 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                  }

                  var6 = var7;
                  if (var7 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var6 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                  }

                  this.mMeasure.horizontalBehavior = var8;
                  this.mMeasure.verticalBehavior = var6;
                  this.mMeasure.horizontalDimension = var5.getWidth();
                  this.mMeasure.verticalDimension = var5.getHeight();
                  var1.measure(var5, this.mMeasure);
                  var5.setWidth(this.mMeasure.measuredWidth);
                  var5.setHeight(this.mMeasure.measuredHeight);
                  var5.setBaselineDistance(this.mMeasure.measuredBaseline);
               }
            }

            ++var2;
         }
      }
   }

   public boolean needSolverPass() {
      return this.mNeedsCallFromSolver;
   }

   protected void needsCallbackFromSolver(boolean var1) {
      this.mNeedsCallFromSolver = var1;
   }

   public void setMeasure(int var1, int var2) {
      this.mMeasuredWidth = var1;
      this.mMeasuredHeight = var2;
   }

   public void setPadding(int var1) {
      this.mPaddingLeft = var1;
      this.mPaddingTop = var1;
      this.mPaddingRight = var1;
      this.mPaddingBottom = var1;
      this.mPaddingStart = var1;
      this.mPaddingEnd = var1;
   }

   public void setPaddingBottom(int var1) {
      this.mPaddingBottom = var1;
   }

   public void setPaddingEnd(int var1) {
      this.mPaddingEnd = var1;
   }

   public void setPaddingLeft(int var1) {
      this.mPaddingLeft = var1;
      this.mResolvedPaddingLeft = var1;
   }

   public void setPaddingRight(int var1) {
      this.mPaddingRight = var1;
      this.mResolvedPaddingRight = var1;
   }

   public void setPaddingStart(int var1) {
      this.mPaddingStart = var1;
      this.mResolvedPaddingLeft = var1;
      this.mResolvedPaddingRight = var1;
   }

   public void setPaddingTop(int var1) {
      this.mPaddingTop = var1;
   }

   public void updateConstraints(ConstraintWidgetContainer var1) {
      this.captureWidgets();
   }
}
