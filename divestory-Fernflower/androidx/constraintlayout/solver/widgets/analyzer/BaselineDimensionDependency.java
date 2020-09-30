package androidx.constraintlayout.solver.widgets.analyzer;

class BaselineDimensionDependency extends DimensionDependency {
   public BaselineDimensionDependency(WidgetRun var1) {
      super(var1);
   }

   public void update(DependencyNode var1) {
      ((VerticalWidgetRun)this.run).baseline.margin = this.run.widget.getBaselineDistance();
      this.resolved = true;
   }
}
