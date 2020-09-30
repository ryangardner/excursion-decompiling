package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public abstract class WidgetRun implements Dependency {
   DimensionDependency dimension = new DimensionDependency(this);
   protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
   public DependencyNode end = new DependencyNode(this);
   protected WidgetRun.RunType mRunType;
   public int matchConstraintsType;
   public int orientation = 0;
   boolean resolved = false;
   RunGroup runGroup;
   public DependencyNode start = new DependencyNode(this);
   ConstraintWidget widget;

   public WidgetRun(ConstraintWidget var1) {
      this.mRunType = WidgetRun.RunType.NONE;
      this.widget = var1;
   }

   private void resolveDimension(int var1, int var2) {
      int var3 = this.matchConstraintsType;
      if (var3 != 0) {
         if (var3 != 1) {
            ConstraintWidget var4;
            float var5;
            Object var7;
            if (var3 != 2) {
               if (var3 == 3 && (this.widget.horizontalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.horizontalRun.matchConstraintsType != 3 || this.widget.verticalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.verticalRun.matchConstraintsType != 3)) {
                  var4 = this.widget;
                  if (var1 == 0) {
                     var7 = var4.verticalRun;
                  } else {
                     var7 = var4.horizontalRun;
                  }

                  if (((WidgetRun)var7).dimension.resolved) {
                     var5 = this.widget.getDimensionRatio();
                     if (var1 == 1) {
                        var1 = (int)((float)((WidgetRun)var7).dimension.value / var5 + 0.5F);
                     } else {
                        var1 = (int)(var5 * (float)((WidgetRun)var7).dimension.value + 0.5F);
                     }

                     this.dimension.resolve(var1);
                  }
               }
            } else {
               var4 = this.widget.getParent();
               if (var4 != null) {
                  if (var1 == 0) {
                     var7 = var4.horizontalRun;
                  } else {
                     var7 = var4.verticalRun;
                  }

                  if (((WidgetRun)var7).dimension.resolved) {
                     ConstraintWidget var6 = this.widget;
                     if (var1 == 0) {
                        var5 = var6.mMatchConstraintPercentWidth;
                     } else {
                        var5 = var6.mMatchConstraintPercentHeight;
                     }

                     var2 = (int)((float)((WidgetRun)var7).dimension.value * var5 + 0.5F);
                     this.dimension.resolve(this.getLimitedDimension(var2, var1));
                  }
               }
            }
         } else {
            var1 = this.getLimitedDimension(this.dimension.wrapValue, var1);
            this.dimension.resolve(Math.min(var1, var2));
         }
      } else {
         this.dimension.resolve(this.getLimitedDimension(var2, var1));
      }

   }

   protected final void addTarget(DependencyNode var1, DependencyNode var2, int var3) {
      var1.targets.add(var2);
      var1.margin = var3;
      var2.dependencies.add(var1);
   }

   protected final void addTarget(DependencyNode var1, DependencyNode var2, int var3, DimensionDependency var4) {
      var1.targets.add(var2);
      var1.targets.add(this.dimension);
      var1.marginFactor = var3;
      var1.marginDependency = var4;
      var2.dependencies.add(var1);
      var4.dependencies.add(var1);
   }

   abstract void apply();

   abstract void applyToWidget();

   abstract void clear();

   protected final int getLimitedDimension(int var1, int var2) {
      int var3;
      if (var2 == 0) {
         var3 = this.widget.mMatchConstraintMaxWidth;
         var2 = Math.max(this.widget.mMatchConstraintMinWidth, var1);
         if (var3 > 0) {
            var2 = Math.min(var3, var1);
         }

         var3 = var1;
         if (var2 == var1) {
            return var3;
         }
      } else {
         var3 = this.widget.mMatchConstraintMaxHeight;
         var2 = Math.max(this.widget.mMatchConstraintMinHeight, var1);
         if (var3 > 0) {
            var2 = Math.min(var3, var1);
         }

         var3 = var1;
         if (var2 == var1) {
            return var3;
         }
      }

      var3 = var2;
      return var3;
   }

   protected final DependencyNode getTarget(ConstraintAnchor var1) {
      ConstraintAnchor var2 = var1.mTarget;
      Object var3 = null;
      if (var2 == null) {
         return null;
      } else {
         ConstraintWidget var7 = var1.mTarget.mOwner;
         ConstraintAnchor.Type var5 = var1.mTarget.mType;
         int var4 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[var5.ordinal()];
         DependencyNode var6;
         if (var4 != 1) {
            if (var4 != 2) {
               if (var4 != 3) {
                  if (var4 != 4) {
                     if (var4 != 5) {
                        var6 = (DependencyNode)var3;
                     } else {
                        var6 = var7.verticalRun.end;
                     }
                  } else {
                     var6 = var7.verticalRun.baseline;
                  }
               } else {
                  var6 = var7.verticalRun.start;
               }
            } else {
               var6 = var7.horizontalRun.end;
            }
         } else {
            var6 = var7.horizontalRun.start;
         }

         return var6;
      }
   }

   protected final DependencyNode getTarget(ConstraintAnchor var1, int var2) {
      ConstraintAnchor var3 = var1.mTarget;
      Object var4 = null;
      if (var3 == null) {
         return null;
      } else {
         ConstraintWidget var7 = var1.mTarget.mOwner;
         Object var8;
         if (var2 == 0) {
            var8 = var7.horizontalRun;
         } else {
            var8 = var7.verticalRun;
         }

         DependencyNode var6;
         label40: {
            ConstraintAnchor.Type var5 = var1.mTarget.mType;
            var2 = null.$SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintAnchor$Type[var5.ordinal()];
            if (var2 != 1) {
               if (var2 == 2) {
                  break label40;
               }

               if (var2 != 3) {
                  if (var2 != 5) {
                     var6 = (DependencyNode)var4;
                     return var6;
                  }
                  break label40;
               }
            }

            var6 = ((WidgetRun)var8).start;
            return var6;
         }

         var6 = ((WidgetRun)var8).end;
         return var6;
      }
   }

   public long getWrapDimension() {
      return this.dimension.resolved ? (long)this.dimension.value : 0L;
   }

   public boolean isCenterConnection() {
      int var1 = this.start.targets.size();
      boolean var2 = false;
      int var3 = 0;

      int var4;
      int var5;
      for(var4 = 0; var3 < var1; var4 = var5) {
         var5 = var4;
         if (((DependencyNode)this.start.targets.get(var3)).run != this) {
            var5 = var4 + 1;
         }

         ++var3;
      }

      var1 = this.end.targets.size();

      for(var5 = 0; var5 < var1; var4 = var3) {
         var3 = var4;
         if (((DependencyNode)this.end.targets.get(var5)).run != this) {
            var3 = var4 + 1;
         }

         ++var5;
      }

      if (var4 >= 2) {
         var2 = true;
      }

      return var2;
   }

   public boolean isDimensionResolved() {
      return this.dimension.resolved;
   }

   public boolean isResolved() {
      return this.resolved;
   }

   abstract void reset();

   abstract boolean supportsWrapComputation();

   public void update(Dependency var1) {
   }

   protected void updateRunCenter(Dependency var1, ConstraintAnchor var2, ConstraintAnchor var3, int var4) {
      DependencyNode var5 = this.getTarget(var2);
      DependencyNode var10 = this.getTarget(var3);
      if (var5.resolved && var10.resolved) {
         int var6 = var5.value + var2.getMargin();
         int var7 = var10.value - var3.getMargin();
         int var8 = var7 - var6;
         if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            this.resolveDimension(var4, var8);
         }

         if (!this.dimension.resolved) {
            return;
         }

         if (this.dimension.value == var8) {
            this.start.resolve(var6);
            this.end.resolve(var7);
            return;
         }

         ConstraintWidget var11 = this.widget;
         float var9;
         if (var4 == 0) {
            var9 = var11.getHorizontalBiasPercent();
         } else {
            var9 = var11.getVerticalBiasPercent();
         }

         var4 = var7;
         if (var5 == var10) {
            var6 = var5.value;
            var4 = var10.value;
            var9 = 0.5F;
         }

         var7 = this.dimension.value;
         this.start.resolve((int)((float)var6 + 0.5F + (float)(var4 - var6 - var7) * var9));
         this.end.resolve(this.start.value + this.dimension.value);
      }

   }

   protected void updateRunEnd(Dependency var1) {
   }

   protected void updateRunStart(Dependency var1) {
   }

   public long wrapSize(int var1) {
      if (!this.dimension.resolved) {
         return 0L;
      } else {
         long var2 = (long)this.dimension.value;
         if (this.isCenterConnection()) {
            var1 = this.start.margin - this.end.margin;
         } else {
            if (var1 != 0) {
               var2 -= (long)this.end.margin;
               return var2;
            }

            var1 = this.start.margin;
         }

         var2 += (long)var1;
         return var2;
      }
   }

   static enum RunType {
      CENTER,
      END,
      NONE,
      START;

      static {
         WidgetRun.RunType var0 = new WidgetRun.RunType("CENTER", 3);
         CENTER = var0;
      }
   }
}
