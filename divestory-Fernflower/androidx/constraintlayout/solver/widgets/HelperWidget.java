package androidx.constraintlayout.solver.widgets;

import java.util.Arrays;
import java.util.HashMap;

public class HelperWidget extends ConstraintWidget implements Helper {
   public ConstraintWidget[] mWidgets = new ConstraintWidget[4];
   public int mWidgetsCount = 0;

   public void add(ConstraintWidget var1) {
      if (var1 != this && var1 != null) {
         int var2 = this.mWidgetsCount;
         ConstraintWidget[] var3 = this.mWidgets;
         if (var2 + 1 > var3.length) {
            this.mWidgets = (ConstraintWidget[])Arrays.copyOf(var3, var3.length * 2);
         }

         var3 = this.mWidgets;
         var2 = this.mWidgetsCount;
         var3[var2] = var1;
         this.mWidgetsCount = var2 + 1;
      }

   }

   public void copy(ConstraintWidget var1, HashMap<ConstraintWidget, ConstraintWidget> var2) {
      super.copy(var1, var2);
      HelperWidget var5 = (HelperWidget)var1;
      int var3 = 0;
      this.mWidgetsCount = 0;

      for(int var4 = var5.mWidgetsCount; var3 < var4; ++var3) {
         this.add((ConstraintWidget)var2.get(var5.mWidgets[var3]));
      }

   }

   public void removeAllIds() {
      this.mWidgetsCount = 0;
      Arrays.fill(this.mWidgets, (Object)null);
   }

   public void updateConstraints(ConstraintWidgetContainer var1) {
   }
}
