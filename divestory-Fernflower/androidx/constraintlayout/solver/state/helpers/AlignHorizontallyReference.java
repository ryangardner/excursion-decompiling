package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

public class AlignHorizontallyReference extends HelperReference {
   private float mBias = 0.5F;
   private Object mEndToEnd;
   private Object mEndToStart;
   private Object mStartToEnd;
   private Object mStartToStart;

   public AlignHorizontallyReference(State var1) {
      super(var1, State.Helper.ALIGN_VERTICALLY);
   }

   public void apply() {
      Iterator var1 = this.mReferences.iterator();

      while(var1.hasNext()) {
         Object var2 = var1.next();
         ConstraintReference var5 = this.mState.constraints(var2);
         var5.clearHorizontal();
         Object var3 = this.mStartToStart;
         if (var3 != null) {
            var5.startToStart(var3);
         } else {
            var3 = this.mStartToEnd;
            if (var3 != null) {
               var5.startToEnd(var3);
            } else {
               var5.startToStart(State.PARENT);
            }
         }

         var3 = this.mEndToStart;
         if (var3 != null) {
            var5.endToStart(var3);
         } else {
            var3 = this.mEndToEnd;
            if (var3 != null) {
               var5.endToEnd(var3);
            } else {
               var5.endToEnd(State.PARENT);
            }
         }

         float var4 = this.mBias;
         if (var4 != 0.5F) {
            var5.horizontalBias(var4);
         }
      }

   }

   public void bias(float var1) {
      this.mBias = var1;
   }

   public void endToEnd(Object var1) {
      this.mEndToEnd = var1;
   }

   public void endToStart(Object var1) {
      this.mEndToStart = var1;
   }

   public void startToEnd(Object var1) {
      this.mStartToEnd = var1;
   }

   public void startToStart(Object var1) {
      this.mStartToStart = var1;
   }
}
