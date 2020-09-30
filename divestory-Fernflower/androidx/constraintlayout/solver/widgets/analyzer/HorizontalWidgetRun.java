package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;

public class HorizontalWidgetRun extends WidgetRun {
   private static int[] tempDimensions = new int[2];

   public HorizontalWidgetRun(ConstraintWidget var1) {
      super(var1);
      this.start.type = DependencyNode.Type.LEFT;
      this.end.type = DependencyNode.Type.RIGHT;
      this.orientation = 0;
   }

   private void computeInsetRatio(int[] var1, int var2, int var3, int var4, int var5, float var6, int var7) {
      var2 = var3 - var2;
      var3 = var5 - var4;
      if (var7 != -1) {
         if (var7 != 0) {
            if (var7 == 1) {
               var3 = (int)((float)var2 * var6 + 0.5F);
               var1[0] = var2;
               var1[1] = var3;
            }
         } else {
            var1[0] = (int)((float)var3 * var6 + 0.5F);
            var1[1] = var3;
         }
      } else {
         var4 = (int)((float)var3 * var6 + 0.5F);
         var5 = (int)((float)var2 / var6 + 0.5F);
         if (var4 <= var2) {
            var1[0] = var4;
            var1[1] = var3;
         } else if (var5 <= var3) {
            var1[0] = var2;
            var1[1] = var5;
         }
      }

   }

   void apply() {
      if (this.widget.measured) {
         this.dimension.resolve(this.widget.getWidth());
      }

      ConstraintWidget var1;
      int var4;
      if (!this.dimension.resolved) {
         super.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
         if (super.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
               var1 = this.widget.getParent();
               if (var1 != null && var1.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || var1.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                  int var2 = var1.getWidth();
                  int var3 = this.widget.mLeft.getMargin();
                  var4 = this.widget.mRight.getMargin();
                  this.addTarget(this.start, var1.horizontalRun.start, this.widget.mLeft.getMargin());
                  this.addTarget(this.end, var1.horizontalRun.end, -this.widget.mRight.getMargin());
                  this.dimension.resolve(var2 - var3 - var4);
                  return;
               }
            }

            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
               this.dimension.resolve(this.widget.getWidth());
            }
         }
      } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
         var1 = this.widget.getParent();
         if (var1 != null && var1.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || var1.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            this.addTarget(this.start, var1.horizontalRun.start, this.widget.mLeft.getMargin());
            this.addTarget(this.end, var1.horizontalRun.end, -this.widget.mRight.getMargin());
            return;
         }
      }

      DependencyNode var7;
      if (this.dimension.resolved && this.widget.measured) {
         if (this.widget.mListAnchors[0].mTarget != null && this.widget.mListAnchors[1].mTarget != null) {
            if (this.widget.isInHorizontalChain()) {
               this.start.margin = this.widget.mListAnchors[0].getMargin();
               this.end.margin = -this.widget.mListAnchors[1].getMargin();
            } else {
               var7 = this.getTarget(this.widget.mListAnchors[0]);
               if (var7 != null) {
                  this.addTarget(this.start, var7, this.widget.mListAnchors[0].getMargin());
               }

               var7 = this.getTarget(this.widget.mListAnchors[1]);
               if (var7 != null) {
                  this.addTarget(this.end, var7, -this.widget.mListAnchors[1].getMargin());
               }

               this.start.delegateToWidgetRun = true;
               this.end.delegateToWidgetRun = true;
            }
         } else if (this.widget.mListAnchors[0].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[0]);
            if (var7 != null) {
               this.addTarget(this.start, var7, this.widget.mListAnchors[0].getMargin());
               this.addTarget(this.end, this.start, this.dimension.value);
            }
         } else if (this.widget.mListAnchors[1].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[1]);
            if (var7 != null) {
               this.addTarget(this.end, var7, -this.widget.mListAnchors[1].getMargin());
               this.addTarget(this.start, this.end, -this.dimension.value);
            }
         } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
            var7 = this.widget.getParent().horizontalRun.start;
            this.addTarget(this.start, var7, this.widget.getX());
            this.addTarget(this.end, this.start, this.dimension.value);
         }
      } else {
         if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            var4 = this.widget.mMatchConstraintDefaultWidth;
            DimensionDependency var6;
            if (var4 != 2) {
               if (var4 == 3) {
                  if (this.widget.mMatchConstraintDefaultHeight == 3) {
                     this.start.updateDelegate = this;
                     this.end.updateDelegate = this;
                     this.widget.verticalRun.start.updateDelegate = this;
                     this.widget.verticalRun.end.updateDelegate = this;
                     this.dimension.updateDelegate = this;
                     if (this.widget.isInVerticalChain()) {
                        this.dimension.targets.add(this.widget.verticalRun.dimension);
                        this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                        this.widget.verticalRun.dimension.updateDelegate = this;
                        this.dimension.targets.add(this.widget.verticalRun.start);
                        this.dimension.targets.add(this.widget.verticalRun.end);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                     } else if (this.widget.isInHorizontalChain()) {
                        this.widget.verticalRun.dimension.targets.add(this.dimension);
                        this.dimension.dependencies.add(this.widget.verticalRun.dimension);
                     } else {
                        this.widget.verticalRun.dimension.targets.add(this.dimension);
                     }
                  } else {
                     var6 = this.widget.verticalRun.dimension;
                     this.dimension.targets.add(var6);
                     var6.dependencies.add(this.dimension);
                     this.widget.verticalRun.start.dependencies.add(this.dimension);
                     this.widget.verticalRun.end.dependencies.add(this.dimension);
                     this.dimension.delegateToWidgetRun = true;
                     this.dimension.dependencies.add(this.start);
                     this.dimension.dependencies.add(this.end);
                     this.start.targets.add(this.dimension);
                     this.end.targets.add(this.dimension);
                  }
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
         }

         if (this.widget.mListAnchors[0].mTarget != null && this.widget.mListAnchors[1].mTarget != null) {
            if (this.widget.isInHorizontalChain()) {
               this.start.margin = this.widget.mListAnchors[0].getMargin();
               this.end.margin = -this.widget.mListAnchors[1].getMargin();
            } else {
               DependencyNode var5 = this.getTarget(this.widget.mListAnchors[0]);
               var7 = this.getTarget(this.widget.mListAnchors[1]);
               var5.addDependency(this);
               var7.addDependency(this);
               this.mRunType = WidgetRun.RunType.CENTER;
            }
         } else if (this.widget.mListAnchors[0].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[0]);
            if (var7 != null) {
               this.addTarget(this.start, var7, this.widget.mListAnchors[0].getMargin());
               this.addTarget(this.end, this.start, 1, this.dimension);
            }
         } else if (this.widget.mListAnchors[1].mTarget != null) {
            var7 = this.getTarget(this.widget.mListAnchors[1]);
            if (var7 != null) {
               this.addTarget(this.end, var7, -this.widget.mListAnchors[1].getMargin());
               this.addTarget(this.start, this.end, -1, this.dimension);
            }
         } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null) {
            var7 = this.widget.getParent().horizontalRun.start;
            this.addTarget(this.start, var7, this.widget.getX());
            this.addTarget(this.end, this.start, 1, this.dimension);
         }
      }

   }

   public void applyToWidget() {
      if (this.start.resolved) {
         this.widget.setX(this.start.value);
      }

   }

   void clear() {
      this.runGroup = null;
      this.start.clear();
      this.end.clear();
      this.dimension.clear();
      this.resolved = false;
   }

   void reset() {
      this.resolved = false;
      this.start.clear();
      this.start.resolved = false;
      this.end.clear();
      this.end.resolved = false;
      this.dimension.resolved = false;
   }

   boolean supportsWrapComputation() {
      if (super.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         return super.widget.mMatchConstraintDefaultWidth == 0;
      } else {
         return true;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("HorizontalRun ");
      var1.append(this.widget.getDebugName());
      return var1.toString();
   }

   public void update(Dependency var1) {
      int var2 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 == 3) {
               this.updateRunCenter(var1, this.widget.mLeft, this.widget.mRight, 0);
               return;
            }
         } else {
            this.updateRunEnd(var1);
         }
      } else {
         this.updateRunStart(var1);
      }

      float var3;
      DependencyNode var5;
      DependencyNode var15;
      int var17;
      int var18;
      if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         var2 = this.widget.mMatchConstraintDefaultWidth;
         if (var2 != 2) {
            if (var2 == 3) {
               if (this.widget.mMatchConstraintDefaultHeight != 0 && this.widget.mMatchConstraintDefaultHeight != 3) {
                  label195: {
                     label194: {
                        var2 = this.widget.getDimensionRatioSide();
                        float var4;
                        if (var2 != -1) {
                           if (var2 == 0) {
                              var3 = (float)this.widget.verticalRun.dimension.value / this.widget.getDimensionRatio();
                              break label194;
                           }

                           if (var2 != 1) {
                              var2 = 0;
                              break label195;
                           }

                           var3 = (float)this.widget.verticalRun.dimension.value;
                           var4 = this.widget.getDimensionRatio();
                        } else {
                           var3 = (float)this.widget.verticalRun.dimension.value;
                           var4 = this.widget.getDimensionRatio();
                        }

                        var3 *= var4;
                     }

                     var2 = (int)(var3 + 0.5F);
                  }

                  this.dimension.resolve(var2);
               } else {
                  var5 = this.widget.verticalRun.start;
                  var15 = this.widget.verticalRun.end;
                  boolean var16;
                  if (this.widget.mLeft.mTarget != null) {
                     var16 = true;
                  } else {
                     var16 = false;
                  }

                  boolean var6;
                  if (this.widget.mTop.mTarget != null) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  boolean var7;
                  if (this.widget.mRight.mTarget != null) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  boolean var8;
                  if (this.widget.mBottom.mTarget != null) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  int var9 = this.widget.getDimensionRatioSide();
                  if (var16 && var6 && var7 && var8) {
                     var3 = this.widget.getDimensionRatio();
                     int var10;
                     int var11;
                     int var12;
                     int var13;
                     int var19;
                     if (var5.resolved && var15.resolved) {
                        if (this.start.readyToSolve && this.end.readyToSolve) {
                           var10 = ((DependencyNode)this.start.targets.get(0)).value;
                           var19 = this.start.margin;
                           var18 = ((DependencyNode)this.end.targets.get(0)).value;
                           var11 = this.end.margin;
                           var12 = var5.value;
                           var2 = var5.margin;
                           var17 = var15.value;
                           var13 = var15.margin;
                           this.computeInsetRatio(tempDimensions, var10 + var19, var18 - var11, var12 + var2, var17 - var13, var3, var9);
                           this.dimension.resolve(tempDimensions[0]);
                           this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                        }

                        return;
                     }

                     if (this.start.resolved && this.end.resolved) {
                        if (!var5.readyToSolve || !var15.readyToSolve) {
                           return;
                        }

                        var17 = this.start.value;
                        var13 = this.start.margin;
                        var19 = this.end.value;
                        var10 = this.end.margin;
                        var12 = ((DependencyNode)var5.targets.get(0)).value;
                        var2 = var5.margin;
                        var11 = ((DependencyNode)var15.targets.get(0)).value;
                        var18 = var15.margin;
                        this.computeInsetRatio(tempDimensions, var17 + var13, var19 - var10, var12 + var2, var11 - var18, var3, var9);
                        this.dimension.resolve(tempDimensions[0]);
                        this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                     }

                     if (!this.start.readyToSolve || !this.end.readyToSolve || !var5.readyToSolve || !var15.readyToSolve) {
                        return;
                     }

                     var10 = ((DependencyNode)this.start.targets.get(0)).value;
                     var11 = this.start.margin;
                     var18 = ((DependencyNode)this.end.targets.get(0)).value;
                     var17 = this.end.margin;
                     var19 = ((DependencyNode)var5.targets.get(0)).value;
                     var2 = var5.margin;
                     var13 = ((DependencyNode)var15.targets.get(0)).value;
                     var12 = var15.margin;
                     this.computeInsetRatio(tempDimensions, var10 + var11, var18 - var17, var19 + var2, var13 - var12, var3, var9);
                     this.dimension.resolve(tempDimensions[0]);
                     this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                  } else if (var16 && var7) {
                     if (!this.start.readyToSolve || !this.end.readyToSolve) {
                        return;
                     }

                     var3 = this.widget.getDimensionRatio();
                     var2 = ((DependencyNode)this.start.targets.get(0)).value + this.start.margin;
                     var17 = ((DependencyNode)this.end.targets.get(0)).value - this.end.margin;
                     if (var9 != -1 && var9 != 0) {
                        if (var9 == 1) {
                           var2 = this.getLimitedDimension(var17 - var2, 0);
                           var18 = (int)((float)var2 / var3 + 0.5F);
                           var17 = this.getLimitedDimension(var18, 1);
                           if (var18 != var17) {
                              var2 = (int)((float)var17 * var3 + 0.5F);
                           }

                           this.dimension.resolve(var2);
                           this.widget.verticalRun.dimension.resolve(var17);
                        }
                     } else {
                        var2 = this.getLimitedDimension(var17 - var2, 0);
                        var18 = (int)((float)var2 * var3 + 0.5F);
                        var17 = this.getLimitedDimension(var18, 1);
                        if (var18 != var17) {
                           var2 = (int)((float)var17 / var3 + 0.5F);
                        }

                        this.dimension.resolve(var2);
                        this.widget.verticalRun.dimension.resolve(var17);
                     }
                  } else if (var6 && var8) {
                     label267: {
                        if (!var5.readyToSolve || !var15.readyToSolve) {
                           return;
                        }

                        var3 = this.widget.getDimensionRatio();
                        var17 = ((DependencyNode)var5.targets.get(0)).value + var5.margin;
                        var2 = ((DependencyNode)var15.targets.get(0)).value - var15.margin;
                        if (var9 != -1) {
                           if (var9 == 0) {
                              var2 = this.getLimitedDimension(var2 - var17, 1);
                              var18 = (int)((float)var2 * var3 + 0.5F);
                              var17 = this.getLimitedDimension(var18, 0);
                              if (var18 != var17) {
                                 var2 = (int)((float)var17 / var3 + 0.5F);
                              }

                              this.dimension.resolve(var17);
                              this.widget.verticalRun.dimension.resolve(var2);
                              break label267;
                           }

                           if (var9 != 1) {
                              break label267;
                           }
                        }

                        var2 = this.getLimitedDimension(var2 - var17, 1);
                        var18 = (int)((float)var2 / var3 + 0.5F);
                        var17 = this.getLimitedDimension(var18, 0);
                        if (var18 != var17) {
                           var2 = (int)((float)var17 * var3 + 0.5F);
                        }

                        this.dimension.resolve(var17);
                        this.widget.verticalRun.dimension.resolve(var2);
                     }
                  }
               }
            }
         } else {
            ConstraintWidget var14 = this.widget.getParent();
            if (var14 != null && var14.horizontalRun.dimension.resolved) {
               var3 = this.widget.mMatchConstraintPercentWidth;
               var2 = (int)((float)var14.horizontalRun.dimension.value * var3 + 0.5F);
               this.dimension.resolve(var2);
            }
         }
      }

      if (this.start.readyToSolve && this.end.readyToSolve) {
         if (this.start.resolved && this.end.resolved && this.dimension.resolved) {
            return;
         }

         if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.mMatchConstraintDefaultWidth == 0 && !this.widget.isInHorizontalChain()) {
            var15 = (DependencyNode)this.start.targets.get(0);
            var5 = (DependencyNode)this.end.targets.get(0);
            var17 = var15.value + this.start.margin;
            var2 = var5.value + this.end.margin;
            this.start.resolve(var17);
            this.end.resolve(var2);
            this.dimension.resolve(var2 - var17);
            return;
         }

         if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
            var15 = (DependencyNode)this.start.targets.get(0);
            var5 = (DependencyNode)this.end.targets.get(0);
            var2 = var15.value;
            var17 = this.start.margin;
            var2 = Math.min(var5.value + this.end.margin - (var2 + var17), this.dimension.wrapValue);
            var18 = this.widget.mMatchConstraintMaxWidth;
            var17 = Math.max(this.widget.mMatchConstraintMinWidth, var2);
            var2 = var17;
            if (var18 > 0) {
               var2 = Math.min(var18, var17);
            }

            this.dimension.resolve(var2);
         }

         if (!this.dimension.resolved) {
            return;
         }

         var5 = (DependencyNode)this.start.targets.get(0);
         var15 = (DependencyNode)this.end.targets.get(0);
         var2 = var5.value + this.start.margin;
         var17 = var15.value + this.end.margin;
         var3 = this.widget.getHorizontalBiasPercent();
         if (var5 == var15) {
            var2 = var5.value;
            var17 = var15.value;
            var3 = 0.5F;
         }

         var18 = this.dimension.value;
         this.start.resolve((int)((float)var2 + 0.5F + (float)(var17 - var2 - var18) * var3));
         this.end.resolve(this.start.value + this.dimension.value);
      }

   }
}
