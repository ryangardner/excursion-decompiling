package androidx.transition;

import android.graphics.Matrix;
import android.view.View;

class ViewUtilsApi21 extends ViewUtilsApi19 {
   private static boolean sTryHiddenSetAnimationMatrix;
   private static boolean sTryHiddenTransformMatrixToGlobal;
   private static boolean sTryHiddenTransformMatrixToLocal;

   public void setAnimationMatrix(View var1, Matrix var2) {
      if (sTryHiddenSetAnimationMatrix) {
         try {
            var1.setAnimationMatrix(var2);
         } catch (NoSuchMethodError var3) {
            sTryHiddenSetAnimationMatrix = false;
         }
      }

   }

   public void transformMatrixToGlobal(View var1, Matrix var2) {
      if (sTryHiddenTransformMatrixToGlobal) {
         try {
            var1.transformMatrixToGlobal(var2);
         } catch (NoSuchMethodError var3) {
            sTryHiddenTransformMatrixToGlobal = false;
         }
      }

   }

   public void transformMatrixToLocal(View var1, Matrix var2) {
      if (sTryHiddenTransformMatrixToLocal) {
         try {
            var1.transformMatrixToLocal(var2);
         } catch (NoSuchMethodError var3) {
            sTryHiddenTransformMatrixToLocal = false;
         }
      }

   }
}
