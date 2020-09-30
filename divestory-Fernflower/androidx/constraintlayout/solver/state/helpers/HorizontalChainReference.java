package androidx.constraintlayout.solver.state.helpers;

import androidx.constraintlayout.solver.state.ConstraintReference;
import androidx.constraintlayout.solver.state.State;
import java.util.Iterator;

public class HorizontalChainReference extends ChainReference {
   private Object mEndToEnd;
   private Object mEndToStart;
   private Object mStartToEnd;
   private Object mStartToStart;

   public HorizontalChainReference(State var1) {
      super(var1, State.Helper.HORIZONTAL_CHAIN);
   }

   public void apply() {
      Iterator var1 = this.mReferences.iterator();

      while(var1.hasNext()) {
         Object var2 = var1.next();
         this.mState.constraints(var2).clearHorizontal();
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
            Object var8 = this.mStartToStart;
            if (var8 != null) {
               var10.startToStart(var8);
            } else {
               var8 = this.mStartToEnd;
               if (var8 != null) {
                  var10.startToEnd(var8);
               } else {
                  var10.startToStart(State.PARENT);
               }
            }

            var5 = var10;
         }

         if (var9 != null) {
            var9.endToStart(var10.getKey());
            var10.startToEnd(var9.getKey());
         }

         var9 = var10;
      }

      if (var9 != null) {
         var4 = this.mEndToStart;
         if (var4 != null) {
            var9.endToStart(var4);
         } else {
            var4 = this.mEndToEnd;
            if (var4 != null) {
               var9.endToEnd(var4);
            } else {
               var9.endToEnd(State.PARENT);
            }
         }
      }

      if (var7 != null && this.mBias != 0.5F) {
         var7.horizontalBias(this.mBias);
      }

      int var6 = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Chain[this.mStyle.ordinal()];
      if (var6 != 1) {
         if (var6 != 2) {
            if (var6 == 3) {
               var7.setHorizontalChainStyle(2);
            }
         } else {
            var7.setHorizontalChainStyle(1);
         }
      } else {
         var7.setHorizontalChainStyle(0);
      }

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
