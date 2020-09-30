package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

public class VerticalChainReference extends ChainReference {
   private Object mBottomToBottom;
   private Object mBottomToTop;
   private Object mTopToBottom;
   private Object mTopToTop;

   public VerticalChainReference(State var1) {
      super(var1, State.Helper.VERTICAL_CHAIN);
   }

   public void apply() {
      Iterator var1 = this.mReferences.iterator();

      while(var1.hasNext()) {
         Object var2 = var1.next();
         this.mState.constraints(var2).clearVertical();
      }

      Iterator var3 = this.mReferences.iterator();
      ConstraintReference var9 = null;

      Object var4;
      ConstraintReference var5;
      ConstraintReference var7;
      for(var7 = null; var3.hasNext(); var7 = var5) {
         var4 = var3.next();
         ConstraintReference var10 = this.mState.constraints(var4);
         var5 = var7;
         if (var7 == null) {
            Object var8 = this.mTopToTop;
            if (var8 != null) {
               var10.topToTop(var8);
            } else {
               var8 = this.mTopToBottom;
               if (var8 != null) {
                  var10.topToBottom(var8);
               } else {
                  var10.topToTop(State.PARENT);
               }
            }

            var5 = var10;
         }

         if (var9 != null) {
            var9.bottomToTop(var10.getKey());
            var10.topToBottom(var9.getKey());
         }

         var9 = var10;
      }

      if (var9 != null) {
         var4 = this.mBottomToTop;
         if (var4 != null) {
            var9.bottomToTop(var4);
         } else {
            var4 = this.mBottomToBottom;
            if (var4 != null) {
               var9.bottomToBottom(var4);
            } else {
               var9.bottomToBottom(State.PARENT);
            }
         }
      }

      if (var7 != null && this.mBias != 0.5F) {
         var7.verticalBias(this.mBias);
      }

      int var6 = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Chain[this.mStyle.ordinal()];
      if (var6 != 1) {
         if (var6 != 2) {
            if (var6 == 3) {
               var7.setVerticalChainStyle(2);
            }
         } else {
            var7.setVerticalChainStyle(1);
         }
      } else {
         var7.setVerticalChainStyle(0);
      }

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
