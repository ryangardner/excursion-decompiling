package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.Iterator;

class HelperReferences extends WidgetRun {
   public HelperReferences(ConstraintWidget var1) {
      super(var1);
   }

   private void addDependency(DependencyNode var1) {
      this.start.dependencies.add(var1);
      var1.targets.add(this.start);
   }

   void apply() {
      if (this.widget instanceof Barrier) {
         this.start.delegateToWidgetRun = true;
         Barrier var1 = (Barrier)this.widget;
         int var2 = var1.getBarrierType();
         boolean var3 = var1.allowsGoneWidget();
         byte var4 = 0;
         byte var5 = 0;
         byte var6 = 0;
         int var7 = 0;
         ConstraintWidget var8;
         DependencyNode var9;
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 == 3) {
                     for(this.start.type = DependencyNode.Type.BOTTOM; var7 < var1.mWidgetsCount; ++var7) {
                        var8 = var1.mWidgets[var7];
                        if (var3 || var8.getVisibility() != 8) {
                           var9 = var8.verticalRun.end;
                           var9.dependencies.add(this.start);
                           this.start.targets.add(var9);
                        }
                     }

                     this.addDependency(this.widget.verticalRun.start);
                     this.addDependency(this.widget.verticalRun.end);
                  }
               } else {
                  this.start.type = DependencyNode.Type.TOP;

                  for(var7 = var4; var7 < var1.mWidgetsCount; ++var7) {
                     var8 = var1.mWidgets[var7];
                     if (var3 || var8.getVisibility() != 8) {
                        var9 = var8.verticalRun.start;
                        var9.dependencies.add(this.start);
                        this.start.targets.add(var9);
                     }
                  }

                  this.addDependency(this.widget.verticalRun.start);
                  this.addDependency(this.widget.verticalRun.end);
               }
            } else {
               this.start.type = DependencyNode.Type.RIGHT;

               for(var7 = var5; var7 < var1.mWidgetsCount; ++var7) {
                  var8 = var1.mWidgets[var7];
                  if (var3 || var8.getVisibility() != 8) {
                     var9 = var8.horizontalRun.end;
                     var9.dependencies.add(this.start);
                     this.start.targets.add(var9);
                  }
               }

               this.addDependency(this.widget.horizontalRun.start);
               this.addDependency(this.widget.horizontalRun.end);
            }
         } else {
            this.start.type = DependencyNode.Type.LEFT;

            for(var7 = var6; var7 < var1.mWidgetsCount; ++var7) {
               var8 = var1.mWidgets[var7];
               if (var3 || var8.getVisibility() != 8) {
                  var9 = var8.horizontalRun.start;
                  var9.dependencies.add(this.start);
                  this.start.targets.add(var9);
               }
            }

            this.addDependency(this.widget.horizontalRun.start);
            this.addDependency(this.widget.horizontalRun.end);
         }
      }

   }

   public void applyToWidget() {
      if (this.widget instanceof Barrier) {
         int var1 = ((Barrier)this.widget).getBarrierType();
         if (var1 != 0 && var1 != 1) {
            this.widget.setY(this.start.value);
         } else {
            this.widget.setX(this.start.value);
         }
      }

   }

   void clear() {
      this.runGroup = null;
      this.start.clear();
   }

   void reset() {
      this.start.resolved = false;
   }

   boolean supportsWrapComputation() {
      return false;
   }

   public void update(Dependency var1) {
      Barrier var8 = (Barrier)this.widget;
      int var2 = var8.getBarrierType();
      Iterator var3 = this.start.targets.iterator();
      int var4 = 0;
      int var5 = -1;

      while(var3.hasNext()) {
         int var6;
         int var7;
         label27: {
            var6 = ((DependencyNode)var3.next()).value;
            if (var5 != -1) {
               var7 = var5;
               if (var6 >= var5) {
                  break label27;
               }
            }

            var7 = var6;
         }

         var5 = var7;
         if (var4 < var6) {
            var4 = var6;
            var5 = var7;
         }
      }

      if (var2 != 0 && var2 != 2) {
         this.start.resolve(var4 + var8.getMargin());
      } else {
         this.start.resolve(var5 + var8.getMargin());
      }

   }
}
