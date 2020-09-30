package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;

class GuidelineReference extends WidgetRun {
   public GuidelineReference(ConstraintWidget var1) {
      super(var1);
      var1.horizontalRun.clear();
      var1.verticalRun.clear();
      this.orientation = ((Guideline)var1).getOrientation();
   }

   private void addDependency(DependencyNode var1) {
      this.start.dependencies.add(var1);
      var1.targets.add(this.start);
   }

   void apply() {
      Guideline var1 = (Guideline)this.widget;
      int var2 = var1.getRelativeBegin();
      int var3 = var1.getRelativeEnd();
      var1.getRelativePercent();
      if (var1.getOrientation() == 1) {
         if (var2 != -1) {
            this.start.targets.add(this.widget.mParent.horizontalRun.start);
            this.widget.mParent.horizontalRun.start.dependencies.add(this.start);
            this.start.margin = var2;
         } else if (var3 != -1) {
            this.start.targets.add(this.widget.mParent.horizontalRun.end);
            this.widget.mParent.horizontalRun.end.dependencies.add(this.start);
            this.start.margin = -var3;
         } else {
            this.start.delegateToWidgetRun = true;
            this.start.targets.add(this.widget.mParent.horizontalRun.end);
            this.widget.mParent.horizontalRun.end.dependencies.add(this.start);
         }

         this.addDependency(this.widget.horizontalRun.start);
         this.addDependency(this.widget.horizontalRun.end);
      } else {
         if (var2 != -1) {
            this.start.targets.add(this.widget.mParent.verticalRun.start);
            this.widget.mParent.verticalRun.start.dependencies.add(this.start);
            this.start.margin = var2;
         } else if (var3 != -1) {
            this.start.targets.add(this.widget.mParent.verticalRun.end);
            this.widget.mParent.verticalRun.end.dependencies.add(this.start);
            this.start.margin = -var3;
         } else {
            this.start.delegateToWidgetRun = true;
            this.start.targets.add(this.widget.mParent.verticalRun.end);
            this.widget.mParent.verticalRun.end.dependencies.add(this.start);
         }

         this.addDependency(this.widget.verticalRun.start);
         this.addDependency(this.widget.verticalRun.end);
      }

   }

   public void applyToWidget() {
      if (((Guideline)this.widget).getOrientation() == 1) {
         this.widget.setX(this.start.value);
      } else {
         this.widget.setY(this.start.value);
      }

   }

   void clear() {
      this.start.clear();
   }

   void reset() {
      this.start.resolved = false;
      this.end.resolved = false;
   }

   boolean supportsWrapComputation() {
      return false;
   }

   public void update(Dependency var1) {
      if (this.start.readyToSolve) {
         if (!this.start.resolved) {
            DependencyNode var4 = (DependencyNode)this.start.targets.get(0);
            Guideline var2 = (Guideline)this.widget;
            int var3 = (int)((float)var4.value * var2.getRelativePercent() + 0.5F);
            this.start.resolve(var3);
         }
      }
   }
}
