package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.ArrayList;

public class HelperReference {
   private HelperWidget mHelperWidget;
   protected ArrayList<Object> mReferences = new ArrayList();
   protected final State mState;
   final State.Helper mType;

   public HelperReference(State var1, State.Helper var2) {
      this.mState = var1;
      this.mType = var2;
   }

   public HelperReference add(Object... var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object var4 = var1[var3];
         this.mReferences.add(var4);
      }

      return this;
   }

   public void apply() {
   }

   public HelperWidget getHelperWidget() {
      return this.mHelperWidget;
   }

   public State.Helper getType() {
      return this.mType;
   }

   public void setHelperWidget(HelperWidget var1) {
      this.mHelperWidget = var1;
   }
}
