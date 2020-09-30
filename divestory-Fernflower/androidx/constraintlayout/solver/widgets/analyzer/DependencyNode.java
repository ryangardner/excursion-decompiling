package androidx.constraintlayout.solver.widgets.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DependencyNode implements Dependency {
   public boolean delegateToWidgetRun = false;
   List<Dependency> dependencies;
   int margin;
   DimensionDependency marginDependency;
   int marginFactor;
   public boolean readyToSolve = false;
   public boolean resolved;
   WidgetRun run;
   List<DependencyNode> targets;
   DependencyNode.Type type;
   public Dependency updateDelegate = null;
   public int value;

   public DependencyNode(WidgetRun var1) {
      this.type = DependencyNode.Type.UNKNOWN;
      this.marginFactor = 1;
      this.marginDependency = null;
      this.resolved = false;
      this.dependencies = new ArrayList();
      this.targets = new ArrayList();
      this.run = var1;
   }

   public void addDependency(Dependency var1) {
      this.dependencies.add(var1);
      if (this.resolved) {
         var1.update(var1);
      }

   }

   public void clear() {
      this.targets.clear();
      this.dependencies.clear();
      this.resolved = false;
      this.value = 0;
      this.readyToSolve = false;
      this.delegateToWidgetRun = false;
   }

   public String name() {
      String var1 = this.run.widget.getDebugName();
      StringBuilder var2;
      if (this.type != DependencyNode.Type.LEFT && this.type != DependencyNode.Type.RIGHT) {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append("_VERTICAL");
         var1 = var2.toString();
      } else {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append("_HORIZONTAL");
         var1 = var2.toString();
      }

      var2 = new StringBuilder();
      var2.append(var1);
      var2.append(":");
      var2.append(this.type.name());
      return var2.toString();
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

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.run.widget.getDebugName());
      var1.append(":");
      var1.append(this.type);
      var1.append("(");
      Object var2;
      if (this.resolved) {
         var2 = this.value;
      } else {
         var2 = "unresolved";
      }

      var1.append(var2);
      var1.append(") <t=");
      var1.append(this.targets.size());
      var1.append(":d=");
      var1.append(this.dependencies.size());
      var1.append(">");
      return var1.toString();
   }

   public void update(Dependency var1) {
      Iterator var5 = this.targets.iterator();

      do {
         if (!var5.hasNext()) {
            this.readyToSolve = true;
            var1 = this.updateDelegate;
            if (var1 != null) {
               var1.update(this);
            }

            if (this.delegateToWidgetRun) {
               this.run.update(this);
               return;
            }

            DependencyNode var6 = null;
            int var2 = 0;
            Iterator var3 = this.targets.iterator();

            while(var3.hasNext()) {
               DependencyNode var4 = (DependencyNode)var3.next();
               if (!(var4 instanceof DimensionDependency)) {
                  ++var2;
                  var6 = var4;
               }
            }

            if (var6 != null && var2 == 1 && var6.resolved) {
               DimensionDependency var7 = this.marginDependency;
               if (var7 != null) {
                  if (!var7.resolved) {
                     return;
                  }

                  this.margin = this.marginFactor * this.marginDependency.value;
               }

               this.resolve(var6.value + this.margin);
            }

            var1 = this.updateDelegate;
            if (var1 != null) {
               var1.update(this);
            }

            return;
         }
      } while(((DependencyNode)var5.next()).resolved);

   }

   static enum Type {
      BASELINE,
      BOTTOM,
      HORIZONTAL_DIMENSION,
      LEFT,
      RIGHT,
      TOP,
      UNKNOWN,
      VERTICAL_DIMENSION;

      static {
         DependencyNode.Type var0 = new DependencyNode.Type("BASELINE", 7);
         BASELINE = var0;
      }
   }
}
