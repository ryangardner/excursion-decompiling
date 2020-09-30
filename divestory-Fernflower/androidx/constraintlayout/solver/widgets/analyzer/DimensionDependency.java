package androidx.constraintlayout.solver.widgets.analyzer;

import java.util.Iterator;

class DimensionDependency extends DependencyNode {
   public int wrapValue;

   public DimensionDependency(WidgetRun var1) {
      super(var1);
      if (var1 instanceof HorizontalWidgetRun) {
         this.type = DependencyNode.Type.HORIZONTAL_DIMENSION;
      } else {
         this.type = DependencyNode.Type.VERTICAL_DIMENSION;
      }

   }

   public void resolve(int var1) {
      if (!this.resolved) {
         this.resolved = true;
         this.value = var1;
         Iterator var2 = this.dependencies.iterator();

         while(var2.hasNext()) {
            Dependency var3 = (Dependency)var2.next();
            var3.update(var3);
         }

      }
   }
}
