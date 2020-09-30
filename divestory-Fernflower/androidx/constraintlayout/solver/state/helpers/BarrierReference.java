package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.HelperWidget;

public class BarrierReference extends HelperReference {
   private Barrier mBarrierWidget;
   private State.Direction mDirection;
   private int mMargin;

   public BarrierReference(State var1) {
      super(var1, State.Helper.BARRIER);
   }

   public void apply() {
      this.getHelperWidget();
      int var1 = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Direction[this.mDirection.ordinal()];
      byte var2 = 0;
      byte var3 = var2;
      switch(var1) {
      case 1:
      case 2:
         break;
      case 3:
      case 4:
         var3 = 1;
         break;
      case 5:
         var3 = 2;
         break;
      case 6:
         var3 = 3;
         break;
      default:
         var3 = var2;
      }

      this.mBarrierWidget.setBarrierType(var3);
      this.mBarrierWidget.setMargin(this.mMargin);
   }

   public HelperWidget getHelperWidget() {
      if (this.mBarrierWidget == null) {
         this.mBarrierWidget = new Barrier();
      }

      return this.mBarrierWidget;
   }

   public void margin(int var1) {
      this.mMargin = var1;
   }

   public void margin(Object var1) {
      this.margin(this.mState.convertDimension(var1));
   }

   public void setBarrierDirection(State.Direction var1) {
      this.mDirection = var1;
   }
}
