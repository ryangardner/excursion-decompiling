package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.HelperReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

public class AlignVerticallyReference extends HelperReference {
   private float mBias = 0.5F;
   private Object mBottomToBottom;
   private Object mBottomToTop;
   private Object mTopToBottom;
   private Object mTopToTop;

   public AlignVerticallyReference(State var1) {
      super(var1, State.Helper.ALIGN_VERTICALLY);
   }

   public void apply() {
      Iterator var1 = this.mReferences.iterator();

      while(var1.hasNext()) {
         Object var2 = var1.next();
         ConstraintReference var5 = this.mState.constraints(var2);
         var5.clearVertical();
         Object var3 = this.mTopToTop;
         if (var3 != null) {
            var5.topToTop(var3);
         } else {
            var3 = this.mTopToBottom;
            if (var3 != null) {
               var5.topToBottom(var3);
            } else {
               var5.topToTop(State.PARENT);
            }
         }

         var3 = this.mBottomToTop;
         if (var3 != null) {
            var5.bottomToTop(var3);
         } else {
            var3 = this.mBottomToBottom;
            if (var3 != null) {
               var5.bottomToBottom(var3);
            } else {
               var5.bottomToBottom(State.PARENT);
            }
         }

         float var4 = this.mBias;
         if (var4 != 0.5F) {
            var5.verticalBias(var4);
         }
      }

   }

   public void bias(float var1) {
      this.mBias = var1;
   }

   public void bottomToBottom(Object var1) {
      this.mBottomToBottom = var1;
   }

   public void bottomToTop(Object var1) {
      this.mBottomToTop = var1;
   }

   public void topToBottom(Object var1) {
      this.mTopToBottom = var1;
   }

   public void topToTop(Object var1) {
      this.mTopToTop = var1;
   }
}
