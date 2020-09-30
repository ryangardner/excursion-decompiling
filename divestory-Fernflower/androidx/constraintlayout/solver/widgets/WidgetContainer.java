package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.Cache;
import java.util.ArrayList;

public class WidgetContainer extends ConstraintWidget {
   public ArrayList<ConstraintWidget> mChildren = new ArrayList();

   public WidgetContainer() {
   }

   public WidgetContainer(int var1, int var2) {
      super(var1, var2);
   }

   public WidgetContainer(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public void add(ConstraintWidget var1) {
      this.mChildren.add(var1);
      if (var1.getParent() != null) {
         ((WidgetContainer)var1.getParent()).remove(var1);
      }

      var1.setParent(this);
   }

   public void add(ConstraintWidget... var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         this.add(var1[var3]);
      }

   }

   public ArrayList<ConstraintWidget> getChildren() {
      return this.mChildren;
   }

   public ConstraintWidgetContainer getRootConstraintContainer() {
      ConstraintWidget var1 = this.getParent();
      ConstraintWidgetContainer var2;
      if (this instanceof ConstraintWidgetContainer) {
         var2 = (ConstraintWidgetContainer)this;
      } else {
         var2 = null;
      }

      ConstraintWidget var3;
      for(; var1 != null; var1 = var3) {
         var3 = var1.getParent();
         if (var1 instanceof ConstraintWidgetContainer) {
            var2 = (ConstraintWidgetContainer)var1;
         }
      }

      return var2;
   }

   public void layout() {
      ArrayList var1 = this.mChildren;
      if (var1 != null) {
         int var2 = var1.size();

         for(int var3 = 0; var3 < var2; ++var3) {
            ConstraintWidget var4 = (ConstraintWidget)this.mChildren.get(var3);
            if (var4 instanceof WidgetContainer) {
               ((WidgetContainer)var4).layout();
            }
         }

      }
   }

   public void remove(ConstraintWidget var1) {
      this.mChildren.remove(var1);
      var1.setParent((ConstraintWidget)null);
   }

   public void removeAllChildren() {
      this.mChildren.clear();
   }

   public void reset() {
      this.mChildren.clear();
      super.reset();
   }

   public void resetSolverVariables(Cache var1) {
      super.resetSolverVariables(var1);
      int var2 = this.mChildren.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         ((ConstraintWidget)this.mChildren.get(var3)).resetSolverVariables(var1);
      }

   }

   public void setOffset(int var1, int var2) {
      super.setOffset(var1, var2);
      var2 = this.mChildren.size();

      for(var1 = 0; var1 < var2; ++var1) {
         ((ConstraintWidget)this.mChildren.get(var1)).setOffset(this.getRootX(), this.getRootY());
      }

   }
}
