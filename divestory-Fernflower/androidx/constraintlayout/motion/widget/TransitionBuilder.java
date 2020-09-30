package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.widget.ConstraintSet;

public class TransitionBuilder {
   private static final String TAG = "TransitionBuilder";

   public static MotionScene.Transition buildTransition(MotionScene var0, int var1, int var2, ConstraintSet var3, int var4, ConstraintSet var5) {
      MotionScene.Transition var6 = new MotionScene.Transition(var1, var0, var2, var4);
      updateConstraintSetInMotionScene(var0, var6, var3, var5);
      return var6;
   }

   private static void updateConstraintSetInMotionScene(MotionScene var0, MotionScene.Transition var1, ConstraintSet var2, ConstraintSet var3) {
      int var4 = var1.getStartConstraintSetId();
      int var5 = var1.getEndConstraintSetId();
      var0.setConstraintSet(var4, var2);
      var0.setConstraintSet(var5, var3);
   }

   public static void validate(MotionLayout var0) {
      if (var0.mScene != null) {
         MotionScene var1 = var0.mScene;
         if (var1.validateLayout(var0)) {
            if (var1.mCurrentTransition == null || var1.getDefinedTransitions().isEmpty()) {
               throw new RuntimeException("Invalid motion layout. Motion Scene doesn't have any transition.");
            }
         } else {
            throw new RuntimeException("MotionLayout doesn't have the right motion scene.");
         }
      } else {
         throw new RuntimeException("Invalid motion layout. Layout missing Motion Scene.");
      }
   }
}
