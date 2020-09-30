package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;

public class VerticalWidgetRun extends WidgetRun {
   public DependencyNode baseline = new DependencyNode(this);
   DimensionDependency baselineDimension = null;

   public VerticalWidgetRun(ConstraintWidget var1) {
      super(var1);
      this.start.type = DependencyNode.Type.TOP;
      this.end.type = DependencyNode.Type.BOTTOM;
      this.baseline.type = DependencyNode.Type.BASELINE;
      this.orientation = 1;
   }

   void apply() {
      if (this.widget.measured) {
         this.dimension.resolve(this.widget.getHeight());
      }

      ConstraintWidget var1;
      int var4;
      if (!this.dimension.resolved) {
         super.dimensionBehavior = this.widget.getVerticalDimensionBehaviour();
         if (this.widget.hasBaseline()) {
            this.baselineDimension = new BaselineDimensionDependency(this);
         }

         if (super.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
               var1 = this.widget.getParent();
               if (var1 != null && var1.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
                  int var2 = var1.getHeight();
                  int var3 = this.widget.mTop.getMargin();
                  var4 = this.widget.mBottom.getMargin();
                  this.addTarget(this.start, var1.verticalRun.start, this.widget.mTop.getMargin());
                  this.addTarget(this.end, var1.verticalRun.end, -this.widget.mBottom.getMargin());
                  this.dimension.resolve(var2 - var3 - var4);
                  return;
               }
            }

            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
               this.dimension.resolve(this.widget.getHeight());
            }
         }
      } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
         var1 = this.widget.getParent();
         if (var1 != null && var1.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
            this.addTarget(this.start, var1.verticalRun.start, this.widget.mTop.getMargin());
            this.addTarget(this.end, var1.verticalRun.end, -this.widget.mBottom.getMargin());
            return;
         }
      }

      DependencyNode var7;
      if (this.dimension.resolved && this.widget.measured) {
         if (this.widget.mListAnchors[2].mTarget != null && this.widget.mListAnchors[3].mTarget != null) {
            if (this.widget.isInVerticalChain()) {
               this.start.margin = this.widget.mListAnchors[2].getMargin();
               this.end.margin = -this.widget.mListAnchors[3].getMargin();
            } else {
               var7 = this.getTarget(this.widget.mListAnchors[2]);
               if (var7 != null) {
                  this.addTarget(this.start, var7, this.widget.mListAnchors[2].getMargin());
               }

               var7 = this.getTarget(this.widget.mListAnchors[3]);
               if (var7 != null) {
                  this.addTarget(this.end, var7, -this.widget.mListAnchors[3].getMargin());
               }

               this.start.delegateToWidgetRun = true;
               this.end.delegateToWidgetRun = true;
            }

            if (this.widget.hasBaseline()) {
               this.addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
            }
         } else if (this.widget.mListAnchors[2].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[2]);
            if (var7 != null) {
               this.addTarget(this.start, var7, this.widget.mListAnchors[2].getMargin());
               this.addTarget(this.end, this.start, this.dimension.value);
               if (this.widget.hasBaseline()) {
                  this.addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
               }
            }
         } else if (this.widget.mListAnchors[3].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[3]);
            if (var7 != null) {
               this.addTarget(this.end, var7, -this.widget.mListAnchors[3].getMargin());
               this.addTarget(this.start, this.end, -this.dimension.value);
            }

            if (this.widget.hasBaseline()) {
               this.addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
            }
         } else if (this.widget.mListAnchors[4].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[4]);
            if (var7 != null) {
               this.addTarget(this.baseline, var7, 0);
               this.addTarget(this.start, this.baseline, -this.widget.getBaselineDistance());
               this.addTarget(this.end, this.start, this.dimension.value);
            }
         } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
            var7 = this.widget.getParent().verticalRun.start;
            this.addTarget(this.start, var7, this.widget.getY());
            this.addTarget(this.end, this.start, this.dimension.value);
            if (this.widget.hasBaseline()) {
               this.addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
            }
         }
      } else {
         if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            var4 = this.widget.mMatchConstraintDefaultHeight;
            DimensionDependency var6;
            if (var4 != 2) {
               if (var4 == 3 && !this.widget.isInVerticalChain() && this.widget.mMatchConstraintDefaultWidth != 3) {
                  var6 = this.widget.horizontalRun.dimension;
                  this.dimension.targets.add(var6);
                  var6.dependencies.add(this.dimension);
                  this.dimension.delegateToWidgetRun = true;
                  this.dimension.dependencies.add(this.start);
                  this.dimension.dependencies.add(this.end);
               }
            } else {
               var1 = this.widget.getParent();
               if (var1 != null) {
                  var6 = var1.verticalRun.dimension;
                  this.dimension.targets.add(var6);
                  var6.dependencies.add(this.dimension);
                  this.dimension.delegateToWidgetRun = true;
                  this.dimension.dependencies.add(this.start);
                  this.dimension.dependencies.add(this.end);
               }
            }
         } else {
            this.dimension.addDependency(this);
         }

         if (this.widget.mListAnchors[2].mTarget != null && this.widget.mListAnchors[3].mTarget != null) {
            if (this.widget.isInVerticalChain()) {
               this.start.margin = this.widget.mListAnchors[2].getMargin();
               this.end.margin = -this.widget.mListAnchors[3].getMargin();
            } else {
               var7 = this.getTarget(this.widget.mListAnchors[2]);
               DependencyNode var5 = this.getTarget(this.widget.mListAnchors[3]);
               var7.addDependency(this);
               var5.addDependency(this);
               this.mRunType = WidgetRun.RunType.CENTER;
            }

            if (this.widget.hasBaseline()) {
               this.addTarget(this.baseline, this.start, 1, this.baselineDimension);
            }
         } else if (this.widget.mListAnchors[2].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[2]);
            if (var7 != null) {
               this.addTarget(this.start, var7, this.widget.mListAnchors[2].getMargin());
               this.addTarget(this.end, this.start, 1, this.dimension);
               if (this.widget.hasBaseline()) {
                  this.addTarget(this.baseline, this.start, 1, this.baselineDimension);
               }

               if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.getDimensionRatio() > 0.0F && this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
                  this.dimension.targets.add(this.widget.horizontalRun.dimension);
                  this.dimension.updateDelegate = this;
               }
            }
         } else if (this.widget.mListAnchors[3].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[3]);
            if (var7 != null) {
               this.addTarget(this.end, var7, -this.widget.mListAnchors[3].getMargin());
               this.addTarget(this.start, this.end, -1, this.dimension);
               if (this.widget.hasBaseline()) {
                  this.addTarget(this.baseline, this.start, 1, this.baselineDimension);
               }
            }
         } else if (this.widget.mListAnchors[4].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[4]);
            if (var7 != null) {
               this.addTarget(this.baseline, var7, 0);
               this.addTarget(this.start, this.baseline, -1, this.baselineDimension);
               this.addTarget(this.end, this.start, 1, this.dimension);
            }
         } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null) {
            var7 = this.widget.getParent().verticalRun.start;
            this.addTarget(this.start, var7, this.widget.getY());
            this.addTarget(this.end, this.start, 1, this.dimension);
            if (this.widget.hasBaseline()) {
               this.addTarget(this.baseline, this.start, 1, this.baselineDimension);
            }

            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.getDimensionRatio() > 0.0F && this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
               this.dimension.targets.add(this.widget.horizontalRun.dimension);
               this.dimension.updateDelegate = this;
            }
         }

         if (this.dimension.targets.size() == 0) {
            this.dimension.readyToSolve = true;
         }
      }

   }

   public void applyToWidget() {
      if (this.start.resolved) {
         this.widget.setY(this.start.value);
      }

   }

   void clear() {
      this.runGroup = null;
      this.start.clear();
      this.end.clear();
      this.baseline.clear();
      this.dimension.clear();
      this.resolved = false;
   }

   void reset() {
      this.resolved = false;
      this.start.clear();
      this.start.resolved = false;
      this.end.clear();
      this.end.resolved = false;
      this.baseline.clear();
      this.baseline.resolved = false;
      this.dimension.resolved = false;
   }

   boolean supportsWrapComputation() {
      if (super.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         return super.widget.mMatchConstraintDefaultHeight == 0;
      } else {
         return true;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("VerticalRun ");
      var1.append(this.widget.getDebugName());
      return var1.toString();
   }

   public void update(Dependency var1) {
      int var2 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 == 3) {
               this.updateRunCenter(var1, this.widget.mTop, this.widget.mBottom, 1);
               return;
            }
         } else {
            this.updateRunEnd(var1);
         }
      } else {
         this.updateRunStart(var1);
      }

      float var4;
      if (this.dimension.readyToSolve && !this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         var2 = this.widget.mMatchConstraintDefaultHeight;
         if (var2 != 2) {
            if (var2 == 3 && this.widget.horizontalRun.dimension.resolved) {
               label97: {
                  label96: {
                     var2 = this.widget.getDimensionRatioSide();
                     float var3;
                     if (var2 != -1) {
                        if (var2 == 0) {
                           var4 = (float)this.widget.horizontalRun.dimension.value * this.widget.getDimensionRatio();
                           break label96;
                        }

                        if (var2 != 1) {
                           var2 = 0;
                           break label97;
                        }

                        var3 = (float)this.widget.horizontalRun.dimension.value;
                        var4 = this.widget.getDimensionRatio();
                     } else {
                        var3 = (float)this.widget.horizontalRun.dimension.value;
                        var4 = this.widget.getDimensionRatio();
                     }

                     var4 = var3 / var4;
                  }

                  var2 = (int)(var4 + 0.5F);
               }

               this.dimension.resolve(var2);
            }
         } else {
            ConstraintWidget var8 = this.widget.getParent();
            if (var8 != null && var8.verticalRun.dimension.resolved) {
               var4 = this.widget.mMatchConstraintPercentHeight;
               var2 = (int)((float)var8.verticalRun.dimension.value * var4 + 0.5F);
               this.dimension.resolve(var2);
            }
         }
      }

      if (this.start.readyToSolve && this.end.readyToSolve) {
         if (this.start.resolved && this.end.resolved && this.dimension.resolved) {
            return;
         }

         DependencyNode var5;
         int var6;
         DependencyNode var9;
         if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.mMatchConstraintDefaultWidth == 0 && !this.widget.isInVerticalChain()) {
            var5 = (DependencyNode)this.start.targets.get(0);
            var9 = (DependencyNode)this.end.targets.get(0);
            var2 = var5.value + this.start.margin;
            var6 = var9.value + this.end.margin;
            this.start.resolve(var2);
            this.end.resolve(var6);
            this.dimension.resolve(var6 - var2);
            return;
         }

         if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
            var9 = (DependencyNode)this.start.targets.get(0);
            var5 = (DependencyNode)this.end.targets.get(0);
            var6 = var9.value;
            var2 = this.start.margin;
            var2 = var5.value + this.end.margin - (var6 + var2);
            if (var2 < this.dimension.wrapValue) {
               this.dimension.resolve(var2);
            } else {
               this.dimension.resolve(this.dimension.wrapValue);
            }
         }

         if (!this.dimension.resolved) {
            return;
         }

         if (this.start.targets.size() > 0 && this.end.targets.size() > 0) {
            var9 = (DependencyNode)this.start.targets.get(0);
            var5 = (DependencyNode)this.end.targets.get(0);
            var2 = var9.value + this.start.margin;
            var6 = var5.value + this.end.margin;
            var4 = this.widget.getVerticalBiasPercent();
            if (var9 == var5) {
               var2 = var9.value;
               var6 = var5.value;
               var4 = 0.5F;
            }

            int var7 = this.dimension.value;
            this.start.resolve((int)((float)var2 + 0.5F + (float)(var6 - var2 - var7) * var4));
            this.end.resolve(this.start.value + this.dimension.value);
         }
      }

   }
}
