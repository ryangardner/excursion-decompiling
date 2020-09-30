package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;

public class ChainReference extends HelperReference {
   protected float mBias = 0.5F;
   protected State.Chain mStyle;

   public ChainReference(State var1, State.Helper var2) {
      super(var1, var2);
      this.mStyle = State.Chain.SPREAD;
   }

   public void bias(float var1) {
      this.mBias = var1;
   }

   public float getBias() {
      return this.mBias;
   }

   public State.Chain getStyle() {
      return State.Chain.SPREAD;
   }

   public void style(State.Chain var1) {
      this.mStyle = var1;
   }
}
